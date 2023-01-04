// 获取查询参数
var searchData = [];
layui.use(['jquery', 'layer', 'element', 'form', 'table','laydate'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        element = layui.element,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate;

    $(function () {
        var BASE_URL ="/realestate-exchange/yzw";
        //初始化日期控件
        laydate.render({
            elem: '#tskssj'
            , type: 'date'
        });
        laydate.render({
            elem: '#tsjssj'
            , type: 'date'
        });

        // 加载Table
        table.render({
            elem: '#yzwTsztTable',
            toolbar: '#toolbarDemo',
            url: BASE_URL+"/listYzwTsxxByPageJson?loadpage=true",
            title: '一张网推送状态查询列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', width:50, fixed: 'left'},
                {field: 'YZWBH', title: '一张网编号', width: '15%'},
                {field: 'YWSLBH', title: '受理编号', width: '16%'},
                {field: 'CASEPROMISEDATE', title: '承诺日期', width: '13%'
                    ,templet: function (d) {
                        return formatDate(d.CASEPROMISEDATE);
                    }},
                {field: 'CASEENDDATETIME', title: '办结日期', width: '13%'
                    ,templet: function (d) {
                        return formatDate(d.CASEENDDATETIME);
                    }},
                {field: 'TSSJ', title: '推送日期', width: '13%'
                    ,templet: function (d) {
                        return formatDate(d.TSSJ);
                    }},
                {field: 'TSZT', title: '推送状态', width: '8%'
                    ,templet: function (d) {
                        if(d.TSZT ==1){
                            return '<div style="color: #32b032">成功</div>';
                        }else if(d.TSZT ==0) {
                            return '<div style="color: #f24b43">失败</div>';
                        }else if(d.TSZT ==2) {
                            return '<div style="color:  #f7bc25">未推送</div>';
                        }
                    }},
                {field: 'zbmx', title: '指标明细', width: '8%'
                    ,templet: function (d) {
                        if(d.SBLX == '1'){
                            return '<div><a href="javascript:void(0);"lay-event="ycxx" class="layui-table-link" style="color: #f24b43">异常</a></div>';
                        }else {
                            return '<div><a href="javascript:void(0);"lay-event="zbmx" class="layui-table-link">指标明细</a></div>';
                        }
                    }
                },
                {field: 'cz', title: '操作', width: 120, templet: '#czTpl', fixed: 'right'}
            ]],
            page: true,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                setHeightTj();
                // 统计推送状态
                tjTszt();
            }
        });
        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //查询
        function search() {
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });
            if (!isNullOrEmpty(obj.jjcqsj) && isNaN(obj.jjcqsj)) {
                layer.alert("请在即将超期时间内输入数字内容!");
                return false;
            }
            searchData = obj;

            addModel();
            reloadTable(obj);
        }

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

        //头工具栏事件
        table.on('toolbar(yzwTsztTable)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            var checkData = checkStatus.data;
            switch(obj.event){

               //批量验证
                case 'plyz':
                    if (checkData.length === 0) {
                        layer.alert("未选择数据!");
                        return false;
                    }
                    yzwCheck(checkData,false);
                    break;
               //全部验证
                case 'qbyz':
                    yzwCheck("",true);
                    break;
                //批量推送
                case 'plts':
                    if (checkData.length === 0) {
                        layer.alert("未选择数据!");
                        return false;
                    }
                    yzwTs(checkData,false,false);
                    break;
                //全部推送
                case 'qbts':
                    yzwTs("",true,false);
                    break;
                //今日推送
                case 'jrts':
                    yzwTs("",false,true);
                    break;
                //统计
                case 'tjTszt':
                   showTj();
                    break;
                //导出
                case 'export':
                    exportExcelTsjl(obj.config.cols[0],checkData);
                    break;
            }
        });
        //监听行内操作
        table.on('tool(yzwTsztTable)', function(obj){
            var data = obj.data;
            var $tr = $(obj.tr[0]);
            var $trHeader = $(obj.tr[1]);
            switch(obj.event){
                //异常信息
                case 'ycxx':
                    layer.alert(data.SBYCXX);
                    break;
                //指标明细
                case 'zbmx':
                    showZbmx(data.TSXXID,data.YZWBH);
                    break;
                case 'yz':
                    var checkData=[];
                    checkData.push(data);
                    yzwCheck(checkData,false);
                    break;
                case 'ts':
                    var checkData=[];
                    checkData.push(data);
                    yzwTs(checkData,false,false);
                    break;
            }
        });

        //监听滚动事件
        var scrollTop = 0,
            scrollLeft = 0;
        var tableTop = 0, tableLeft = 0;
        var $nowBtnMore = '';
        $(window).on('scroll', function () {
            scrollTop = $(this).scrollTop();
            scrollLeft = $(this).scrollLeft();

            if ($nowBtnMore != '') {
                if (tableTop + 26 + $nowBtnMore.height() < document.body.offsetHeight) {
                    $nowBtnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
                } else {
                    $nowBtnMore.css({top: tableTop - scrollTop - $nowBtnMore.height(), left: tableLeft - 30});
                }
            }
        });

        //点击表头验证推送展示下拉
        $('.layui-btn-container').on('click', '.bdc-more-btn', function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            scrollTop = $(document).scrollTop();
            event.stopPropagation();
            var $btnMore='';
            if($(this).hasClass('moreYzButton')){
                $nowBtnMore = $(this).parent().find('#moreyz');
                $btnMore = $(this).parent().find('#moreyz');
            }else if($(this).hasClass('moreTsButton')){
                $nowBtnMore = $(this).parent().find('#morets');
                $btnMore = $(this).parent().find('#morets');
            }

            if ($(this).offset().top + 30 + $btnMore.height() < document.body.offsetHeight) {
                $btnMore.css({top: tableTop + 30 - scrollTop, left: tableLeft});
            } else {
                $btnMore.css({top: tableTop - scrollTop - $btnMore.height(), left: tableLeft});
            }
            $('.bdc-table-btn-more').hide();
            $btnMore.show();
        });
        //点击更多内的任一内容，隐藏
        $('.bdc-table-btn-more a').on('click', function () {
            $(this).parent().hide();
        });
        //点击页面任一空白位置，消失
        $('body').on('click', function () {
            $('.bdc-table-btn-more').hide();
        });

        // 重新加载表格
        function reloadTable(obj) {
            // 重新请求
            table.reload("yzwTsztTable", {
                url: BASE_URL+"/listYzwTsxxByPageJson?loadpage=true"
                , where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    removeModal();
                    setHeightTj();
                    // 统计推送状态
                    tjTszt();
                }
            });
        }

        // 统计图
        function showTj() {
            var index = layer.open({
                type: 2,
                title: "推送统计",
                area: ['1300px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content: "../yancheng/yzwtsTj.html",
                success: function () {

                }
            });
            layer.full(index);
        }
        // 指标明细弹窗
        function showZbmx(tsxxid,yzwbh) {
            var index = layer.open({
                type: 2,
                title: '指标明细',
                area: ['960px','550px'],
                content:"../yancheng/zbmx.html?tsxxid="+ tsxxid +'&yzwbh='+ yzwbh,
                success: function () {
                }
            });
            layer.full(index);
        }



        //一张网验证
        function yzwCheck(checkData,isAll){
            addModel();
            var yzwbhList=[];
            for (var i = 0; i < checkData.length; i++) {
                var yzwbh = checkData[i].YZWBH;
                yzwbhList.push(yzwbh);
            }
            $.ajax({
                url: BASE_URL+"/check?yzwbhList="+yzwbhList+"&isAll="+isAll,
                type: "POST",
                async: false,
                success: function (data) {
                    removeModal();
                    var msg ="验证成功";
                    if(data &&data.length >0){
                        msg ="验证失败,共有"+data.length+"条验证不通过,具体一张网编号为"+data.join(",");
                    }
                    layer.msg('<img src="../static/lib/bdcui/images/info-small.png" alt="">' + msg);
                    // 刷新表格
                    reloadTable(searchData);
                },
                error: function (err) {
                    delAjaxErrorMsg(err);
                }
            });





        }

        //一张网推送
        function yzwTs(checkData,isAll,isToday) {
            addModel();
            var yzwbhList=[];
            for (var i = 0; i < checkData.length; i++) {
                var yzwbh = checkData[i].YZWBH;
                yzwbhList.push(yzwbh);
            }
            $.ajax({
                url: BASE_URL+"/ts?yzwbhList="+yzwbhList+"&isAll="+isAll+"&isToday="+isToday,
                type: "POST",
                async: false,
                success: function (data) {
                    removeModal();
                    var msg ="推送成功";
                    if(data &&data.length >0){
                        msg ="推送失败,共有"+data.length+"条推送失败,具体一张网编号为"+data.join(",");
                    }
                    layer.msg('<img src="../static/lib/bdcui/images/info-small.png" alt="">' + msg);
                    // 刷新表格
                    reloadTable(searchData);
                },
                error: function (err) {
                    delAjaxErrorMsg(err);
                }
            });
            
        }

        // 统计功能
        function tjTszt(){
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });

            $.ajax({
                url: BASE_URL + "/tj",
                type: "GET",
                data: obj,
                success: function (data) {
                    // 清空统计内容
                    $('.bdc-table-zj div').remove();
                    if(data && data.length >0){
                        data.forEach(function (v,i) {
                            if(v.TSZT=='0'){
                                $('.bdc-table-zj').append(' <div><span>失败:<i id="sb">'+ v.COUNT +'</i></span></div>');
                            } else if(v.TSZT=='1'){
                                $('.bdc-table-zj').append(' <div><span>成功:<i id="cg">'+ v.COUNT +'</i></span></div>');
                            } else if(v.TSZT=='2'){
                                $('.bdc-table-zj').append(' <div><span>未推送:<i id="wts">'+ v.COUNT +'</i></span></div>');
                            }
                        });
                    }else {
                        layer.msg('<img src="../static/lib/bdcui/images/info-small.png" alt="">无统计结果数据');
                    }
                },
                error: function (e) {
                }
            });

        }

        function getTsztMc(tsztDm){
            if(tsztDm ==1){
                return '成功';
            }else if(tsztDm ==0) {
                return '失败';
            }else if(tsztDm ==2) {
                return '未推送';
            }

        }

        //导出功能
        function exportExcelTsjl(cols,data){
            if ($.isEmptyObject(data)) {
                var obj = {};
                $(".search").each(function (i) {
                    var name = $(this).attr('name');
                    var value = $(this).val();
                    if (value) {
                        value = value.replace(/\s*/g, "");
                    }
                    obj[name] = value;
                });
                $.ajax({
                    url: BASE_URL+"/listYzwTsxxByPageJson?loadpage=false",
                    type: 'GET',
                    dataType: 'json',
                    async: false,
                    contentType: "application/json;charset=UTF-8",
                    data: obj,
                    success: function (response) {
                        data =response;
                    }, error: function (xhr, status, error) {
                        removeModal();
                        delAjaxErrorMsg(xhr, "查询数据失败");
                    }

                });
            }
            if(data.length ===0){
                layer.alert("请选择需要导出Excel的数据行！", {title: '提示'});
                return;

            }

            // 标题
            var showColsTitle = [];
            // 列内容
            var showColsField = [];
            // 列宽
            var showColsWidth = [];
            for (var index in cols) {
                if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar && cols[index].field != 'cz' && cols[index].field != 'zbmx') {
                    showColsTitle.push(cols[index].title);
                    showColsField.push(cols[index].field);
                    if (cols[index].width > 0) {
                        showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                    } else if (cols[index].minWidth > 0) {
                        showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
                    } else {
                        showColsWidth.push(200 / 100 * 15);
                    }
                }
            }

            for (var i = 0; i < data.length; i++) {
                data[i].CASEPROMISEDATE = formatDate(data[i].CASEPROMISEDATE);
                data[i].CASEENDDATETIME = formatDate(data[i].CASEENDDATETIME);
                data[i].TSSJ = formatDate(data[i].TSSJ);
                data[i].TSZT = getTsztMc(data[i].TSZT);
            }

            // 设置Excel基本信息
            $("#fileName").val('一张网超期信息');
            $("#sheetName").val('超期信息');
            $("#cellTitle").val(showColsTitle);
            $("#cellWidth").val(showColsWidth);
            $("#cellKey").val(showColsField);
            $("#data").val(JSON.stringify(data));
            $("#form").submit();


        }

    });
});