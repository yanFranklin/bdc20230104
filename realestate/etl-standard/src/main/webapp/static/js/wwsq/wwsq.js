//需要翻转的字段
var currreverseList = ['ZL'];
//流程字典数据
var gzldymcs =[];
//申请来源
var sqly = getQueryString("sqly");

layui.use(['jquery', 'element', 'form', 'table', 'laydate', 'layer'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var layer = layui.layer;
    var laydate = layui.laydate;

    form.render();

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');

    //判断查询起始时间与结束时间关系
    laydate.render({
        elem: '#djcjkssj',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#djcjjssj').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({ //结束时间
        elem: '#djcjjssj',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date($('#djcjkssj').val()).getTime();
            var endTime = new Date(value).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

    //监听流程名称选择
    form.on('select(lcmc)', function (data) {
        queryDjyyList(data.value);
    });




    $(function () {
        //获取流程名称下拉框
        getGzldymcs();

        var isSearch = true;
        //监听回车事件
        $(document).keydown(function (event) {
            if (event.keyCode == 13 && isSearch) { //绑定回车
                $(".bdc-search-box #search").click();
            }
        });
        //判断回车操作
        $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });


        // 加载Table
        table.render({
            elem: '#wwsqTable',
            id: 'wwsqTableId',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            even: true,
            cols: [[
                {field: 'WWSLBH', title: '外网受理编号', minWidth: 180,event: 'sqxx',templet: function (d) {
                    return '<span style="color:#1d87d1;cursor:pointer">'+d.WWSLBH+'</span>'
                }},
                {field: 'DJSLBH', title: '登记受理编号', minWidth: 160},
                {field: 'SQZT', title: '申请状态', minWidth: 120,
                    templet: function (d) {
                        if(d.SHZT ==2){
                            return '<div style="color: #EE0000">已退回</div>';
                        }else if(d.SFCJ ==1){
                            return '<div style="color: #32b032">已创建</div>';
                        }else{
                            return '<div style="color:  #f7bc25">未创建</div>';
                        }
                    }
                },
                {field: 'BYSLYY', title: '退回（失败）原因', minWidth: 150},
                {field: 'SQLX', title: '流程名称', minWidth: 210,
                    templet:function (d) {
                        return getLcmcById(d.SQLX);
                    }
                },
                {field: 'SQR', title: '申请人', minWidth: 80},
                {field: 'QLR', title: '权利人', minWidth: 80},
                {field: 'YWR', title: '义务人', minWidth: 80},
                {field: 'ZL', title: '坐落', minWidth:150},
                {field: 'BDCDYH', title: '不动产单元号', minWidth: 250},
                {field: 'DJCJSJ', title: '登记创建时间', minWidth: 200},
                {field: 'DJBDCDYH', title: '实际不动产单元号', minWidth: 250},
                {field: 'SFPP', title: '是否匹配', minWidth: 120,
                    templet: function (d) {
                        if(d.SFPP ===1){
                            return '<div style="color: #32b032">是</div>';
                        }else if(d.SFPP ===0){
                            return '<div style="color:  #f7bc25">否</div>';
                        }else{
                            return '';
                        }
                    }},
                {field: 'cz', title:'操作', width: 200, fixed: 'right', renderer: 'caozuo', toolbar: '#czBar'}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                if(isNotBlank(res)){
                    return {
                        code: res.code, //解析接口状态
                        msg: res.msg, //解析提示文本
                        count: res.totalElements, //解析数据长度
                        data: res.content //解析数据列表
                    }
                }else{
                    return {
                        code: 0, //解析接口状态
                        msg: "成功", //解析提示文本
                        count: 0, //解析数据长度
                        data: [] //解析数据列表
                    }
                }
            },
            done: function () {
                setHeight();
                reverseTableCell(currreverseList,"wwsqTableId");
                //无数据时显示内容调整
                if ($('.layui-show .layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
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

        $('.bdc-table-box').on('mouseenter', 'td', function () {
            if ($(this).text() && currreverseList.indexOf($(this).attr("data-field")) !== -1) {
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
            }
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                }, 20);
            });
        });

        //监听删除图标点击
        $('.xzq-sele .layui-icon-close').on('click', function (item) {
            $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
            $(this).hide();
            form.render("select");
            resetSelectDisabledCss();
        });


        //查询
        function search() {

            // 获取查询参数
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });
            obj.sqly =sqly;
            //状态处理
            if(!isNullOrEmpty(obj.sqzt)){
                if (obj.sqzt ==2) {
                    obj.shzt = 2;
                } else {
                    obj.sfcj = obj.sqzt;
                    // 审核状态过滤
                    obj.shztgl = 2
                }
            }
            obj.moduleCode=moduleCode;

            addModel();
            // 重新请求
            table.reload("wwsqTableId", {
                url:  "/realestate-etl/wwsq/page/role"
                , where: obj
                , page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
                    reverseTableCell(currreverseList,"wwsqTableId");
                    removeModal();
                    setHeight();
                }
            });
        }


        //监听工具条
        table.on('tool(wwsqTable)', function (obj) {
            var data = obj.data;
            if(obj.event ==="sqxx"){
                window.open("/realestate-etl/view/wwsq/new-page.html?hlwxmid=" + data.XMID +"&gzldyid="+data.SQLX+"&wwslbh="+data.WWSLBH);

            }else if (obj.event === 'cjBdcXm') {
                //创建登记项目
                cjBdcXm(data);
            } else if (obj.event === 'fjxx'){
                fjxx(data);
            }else if (obj.event === 'export') {
                //导出
                exportWwsqxx(data);
            }else if (obj.event === 'exportFj') {
                //导出附件
                exportWwsqFjxx(data);
            }else if(obj.event ==="th"){
                //退回
                thSqxx(data);
            } if(obj.event ==="sqxx"){
                window.open("/realestate-etl/view/wwsq/new-page.html?hlwxmid=" + data.XMID +"&gzldyid="+data.SQLX+"&wwslbh="+data.WWSLBH);

            }if(obj.event ==="delete"){
                deleteSqxx(data);
            }
        });



        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

        if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click','.layui-icon-close',function () {
                $(this).siblings('.layui-input').val('');
                $(this).siblings().find('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus',function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur',function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                },150)
            });
        }

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

        //点击表格中的更多
        $('.bdc-table-box').on('click', '.bdc-more-btn', function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            event.stopPropagation();
            $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
            var $btnMore = $(this).siblings('.bdc-table-btn-more');
            if ($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight) {
                $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
            } else {
                $btnMore.css({top: tableTop - scrollTop - $btnMore.height(), left: tableLeft - 30});
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
    });

    //获取工作流名称
    function getGzldymcs() {
        $.ajax({
            url: '/realestate-etl/wwsq/gzldymcs',
            type: "GET",
            dataType: "json",
            async:false,
            success: function (data) {
                if (data) {
                    $.each(data, function (index, item) {
                        $('#lcmc').append('<option value="' + item.processDefKey + '">' + item.name + '</option>');
                    });
                    form.render('select');
                    gzldymcs =data;
                }
            },
            error: function (e) {
                console.log("获取流程字典异常"+e);
            }
        });
    }

    //根据工作流定义ID获取流程名称
    function getLcmcById(gzldyid){
        var lcmc ="";
        if(isNotBlank(gzldyid)){
            if (isNotBlank(gzldymcs)) {
                for (var i = 0; i < gzldymcs.length; i++) {
                    var lc = gzldymcs[i];
                    if (gzldyid == lc.processDefKey) {
                        lcmc = lc.name;
                        break;
                    }
                }
            }
        }
        return lcmc;
    }

    //导出外网申请信息
    function exportWwsqxx(data){
        $("#dylx").val("wwsq");
        $("#wwslbh").val(data.WWSLBH);
        $("#wwxmid").val(data.XMID);
        $("#wwsqdoc").submit();
    }

    //导出外网申请附件信息
    function exportWwsqFjxx(data){
        $("#fj_wwslbh").val(data.WWSLBH);
        $("#fj_wwxmid").val(data.XMID);
        $("#wwsqfj").submit();
    }

    //创建
    function cjBdcXm(data){
        if(data.SHZT ==2 &&isNotBlank(data.BYSLYY)){
            layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">当前申请件已退回,不允许创建');
            return false;
        }
        addModel();
        $.ajax({
            url: '/realestate-etl/wwsq/cjBdcXm',
            type: "PUT",
            dataType: "json",
            data:{hlwxmid:data.XMID,wwslbh:data.WWSLBH},
            success: function (data) {
                removeModal();
                console.log(data);
                if(!data.gzlslid){
                    if(!data.msg){
                        msg ="创建失败";
                    }
                    layer.alert("<div style='text-align: center'>"+data.msg+"</div>", {title: '提示'});
                    return false;
                }else{
                    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">创建成功');
                    $(".bdc-search-box #search").click();
                    if(!isNullOrEmpty(data.xmtype) &&data.xmtype ==="todo"){
                        window.open('/realestate-portal-ui/view/new-page.html?name=' + data.taskid+ "&type=todo");
                    }else {
                        window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + data.gzlslid);
                    }
                }

            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
    }

    //退回
    function thSqxx(data){
        if(data.SHZT ==2 &&isNotBlank(data.BYSLYY)){
            layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">当前申请件已退回,请勿重复退回');
            return false;
        }
        $('#backreason').val("");
        layer.open({
            title: '退回原因',
            type: 1,
            area: ['430px'],
            btn: ['确认', '取消'],
            content: $('#back-reason')
            ,yes: function(index, layero){
                var opinion = $('#backreason').val();
                if(isNullOrEmpty(opinion)){
                    layer.msg('请输入退回意见');
                    return;
                }
                layer.close(index);
                addModel('退回中');
                $.ajax({
                    url: '/realestate-etl/wwsq/th',
                    type: "POST",
                    dataType: "json",
                    data:{hlwxmid:data.XMID,thyy:opinion},
                    success: function (data) {
                        removeModal();
                        layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">退回成功');
                        $(".bdc-search-box #search").click();
                    },
                    error: function (e) {
                        delAjaxErrorMsg(e);
                    }
                });
            }
            ,btn2: function(index, layero){
                layer.close(index);
            }
        });
    }

    //删除
    function deleteSqxx(data){
        if(data.SFCJ == 1){
            layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">当前申请件已创建,不能删除!');
            return false;
        }
        addModel('退回中');
        $.ajax({
            url: '/realestate-etl/wwsq/delete',
            type: "POST",
            dataType: "json",
            data:{hlwxmid:data.XMID},
            success: function (data) {
                removeModal();
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">删除成功');
                $(".bdc-search-box #search").click();
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        removeModal();
    }

    function fjxx(data){
        window.open( "/realestate-etl/view/wwsq/fjxx.html?hlwxmid="+ data.XMID + "&wwslbh=" + data.WWSLBH);
    }
    // 查看附件信息
    function viewFjXx(data) {
        var url = "/realestate-etl/view/wwsq/fjxx.html?hlwxmid="+ data.XMID + "&wwslbh=" + data.WWSLBH;
        layer.open({
            type: 2,
            // area: ['960px', '575px'],
            area: ['100%', '100%'],
            fixed: false, //不固定
            title: "附件信息",
            content: url,
            btnAlign: "c"
        });
    }

    //查询登记原因列表
    function queryDjyyList(gzldyid) {
        $.ajax({
            type: "GET",
            url: "/realestate-etl/wwsq/djyy",
            data: {gzldyid: gzldyid},
            success: function (data) {
                if(data &&data.length >0){
                    var jdOption = '<option value="">请选择</option>';
                    data.forEach(function (v) {
                        jdOption += '<option value="' + v.djyy + '">' + v.djyy + '</option>';
                    });
                    $("#djyy").html(jdOption);
                    form.render("select");
                }

            }, error: function (e) {
                response.fail(e, '');
            }
        });
    }
});


