var type = GetQueryString("type");
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl','laydate'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;
    var layer = layui.layer;

    var reverseList = ['bdcdyh', 'zl', 'bdcqzh'];
    $(function () {
        //初始化日期控件
        laydate.render({
            elem: '#kssj'
            , type: 'date'
        });
        laydate.render({
            elem: '#jssj'
            , type: 'date'
        });

        //提交表单，点击lay-filter="query"的按钮提交操作
        form.on("submit(query)", function (data) {
            tableReload('requestTable', data.field);
            return false;
        });
        //请求查询结果表对应的表配置；
        var requestTableConfig = {
             toolbar: "#toolbarSqxx"
            , url: '../cxqq/list/getByPage'
            // , limits: [10, 20, 30, 40, 50, 60, 70, 80, 90]
            , cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},//新增，左侧一列新增复选框
                {type: 'numbers', width: 50, fixed: 'left',title:'序号'},
                {field: 'cxsqdh', title: '查询单号',width: 200},
                {field: 'cxywlb', title: '查询业务类别'},
                {field: 'cxjgbs', title: '查询机构标志'},
                {field: 'cjsj', title: '获取时间',
                    templet: function (d) {
                        return formatDate(d.cjsj);
                    }},
                {field: 'sbsj', title: '反馈时间',
                    templet: function (d) {
                        return formatDate(d.sbsj);
                    }},
                {field: 'zt', title: '状态',
                    templet: function (d) {
                        return qqzt(d.zt);
                    }
                },
                {
                    title: '操作',
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#cz',//加载id="cz"的工具条
                    width: 180
                }
            ]],
            done: function () {
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body').height('60px');
                    $('.layui-table-fixed .layui-table-body').height('60px');
                    $('.layui-table-body .layui-none').html('<img src="../static/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };

        //点击查询人按钮跳出的表格配置；
        var qlrTableConfig = {
            toolbar: "#toolbarCxrSqxx"
            , url: '../cxqq/list/getCxrByPage'
            ,defaultToolbar: ['filter']
            , cols: [[
                {type: 'numbers', width: 50, fixed: 'left',title:'序号'},
                {field: 'cxsqdh', title: '查询单号'},
                {field: 'qlrmc', title: '权利人名称'},
                {field: 'qlrzjh', title: '权利人证件号'},
                {field: 'wsbh', title: '文书编号'},
                {field: 'cxzt', title: '状态',
                    templet: function (d) {
                        return cxrzt(d.cxzt);
                    }
                },
                {
                    title: '操作',
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#cxrcz',
                    width:280
                }
            ]],
            done: function () {
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body').height('60px');
                    $('.layui-table-fixed .layui-table-body').height('60px');
                    $('.layui-table-body .layui-none').html('<img src="../static/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }

                $(window).on('scroll',function () {
                    scrollTop = $(this).scrollTop();
                    scrollLeft = $(this).scrollLeft();

                    if($nowBtnMore != ''){
                        if(tableTop + 26 + $nowBtnMore.height() < document.body.offsetHeight){
                            // $nowBtnMore.css({top: tableTop + 26 -scrollTop,right: 20});
                            $nowBtnMore.css({top: tableTop + 26 -scrollTop,left: tableLeft - 30});
                        }else {
                            // $nowBtnMore.css({top: tableTop -scrollTop - $nowBtnMore.height(),right: 20});
                            $nowBtnMore.css({top: tableTop -scrollTop - $nowBtnMore.height(),left: tableLeft - 30});
                        }
                    }
                });
                $('.bdc-table-box .layui-table-body.layui-table-main').on('scroll',function () {
                    $nowBtnMore.hide();
                });
                // 点击表格中的更多
                $('.bdc-table-box').on('click','.bdc-more-btn',function (event) {
                    tableTop = $(this).offset().top;
                    tableLeft = $(this).offset().left;
                    event.stopPropagation();
                    $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
                    var $btnMore = $(this).siblings('.bdc-table-btn-more');
                    if($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight){
                        // $btnMore.css({top: $(this).offset().top + 26 -scrollTop,right: 20});
                        $btnMore.css({top: tableTop + 26 -scrollTop,left:tableLeft - 30});
                    }else {
                        // $btnMore.css({top: $(this).offset().top -scrollTop - $btnMore.height(),right: 20});
                        $btnMore.css({top: tableTop -scrollTop - $btnMore.height(),left: tableLeft - 30});
                    }
                    $('.bdc-table-btn-more').hide();
                    $btnMore.show();
                });
            }
        };

        var tableConfig = requestTableConfig;

        //通过url来进行加载数据
        loadDataTablbeByUrl("#requestTable", tableConfig);

        //状态，把状态代码在前端以汉字说明显示
        function qqzt(zt) {
            if (zt != null) {
                if (zt == 1) {
                    return "未查询";
                } else if (zt == 2){
                    return "已查询未上报";
                } else if (zt == 3) {
                    return "已上报";
                } else if (zt == 4) {
                    return "部分查询";
                } else if (zt == 5) {
                    return "超时";
                }
            } else {
                return "";
            }
        }

        // 获取请求
        $("#getQuery").click(function () {
            var submitIndex = layer.open({
                type: 1,
                title: '提示',
                skin: 'bdc-small-tips',
                area: ['320px'],
                content: '确认获取请求？',
                btn: ['确定','取消'],
                btnAlign: 'c',
                yes: function(){
                    addModel();
                    $.ajax({
                        url: '../cxqq/query/apply',
                        type: 'post',
                        success: function (data) {
                            layer.msg(data.msg);
                            removeModal();
                            loadDataTablbeByUrl("#requestTable", tableConfig);
                        }
                    });
                    layer.close(submitIndex);
                },
                btn2: function(){
                    //取消
                }
            });
        });

        /**
         *@desc  监听操作列工具条
         */
        table.on('tool(dataTable)', function (obj) {
            if(obj.event != "LAYTABLE_COLS"){
                var data = obj.data;
                var layEvent = obj.event;
                var cxsqdh = data.cxsqdh;
                var zt = data.zt;
                // 查询人
                if (layEvent == "cxr") {
                    //$(".bdc-table-cxrxx").show();
                    layer.open({
                        type:1,
                        title:'查询人信息',
                        area:['1200px','600px'],
                        btnAlign: 'c',
                        shade: 0,
                        id: 'cxrDiv'+1,
                        content: $('#cxrDiv').html(),
                        success: function () {
                            var param = {
                                cxsqdh : data.cxsqdh
                            }
                            loadDataTablbeByParam('#cxrTable', qlrTableConfig, param);

                        },
                    });
                }

                // 上报
                if (layEvent == "sb") {
                    if (zt ==1 || zt ==4) {
                        layer.msg("请先执行查询！");
                    } else if (zt == 2) {
                        var submitIndex = layer.open({
                            type: 1,
                            title: '提示',
                            skin: 'bdc-small-tips',
                            area: ['320px'],
                            content: '确认上报？',
                            btn: ['确定','取消'],
                            btnAlign: 'c',
                            yes: function(){
                                layer.close(submitIndex);
                                executeCommit(cxsqdh);
                                addModel();
                            },
                            btn2: function(){
                                //取消
                            }
                        });
                    } else if (zt == 3) {
                        var submitIndex = layer.open({
                            type: 1,
                            title: '提示',
                            skin: 'bdc-small-tips',
                            area: ['320px'],
                            content: '该数据已上报，确认重复上报？',
                            btn: ['确定','取消'],
                            btnAlign: 'c',
                            yes: function(){
                                layer.close(submitIndex);
                                executeCommit(cxsqdh);
                                addModel();
                            },
                            btn2: function(){
                                //取消
                            }
                        });
                    }
                }

                // 执行查询
                if (layEvent == "zxcx"){
                    // 查询该单号是否保存查询结果
                    if(zt == 1){
                        //执行查询
                        executeQuery(cxsqdh);
                    }else if(zt == 2|| zt == 4){
                        var submitIndex = layer.open({
                            type: 1,
                            title: '提示',
                            skin: 'bdc-small-tips',
                            area: ['320px'],
                            content: '已存在查询结果，是否查询最新数据并覆盖？',
                            btn: ['确定','取消'],
                            btnAlign: 'c',
                            yes: function(){
                                layer.close(submitIndex);
                                executeQuery(cxsqdh);
                                addModel();
                            },
                            btn2: function(){
                                //取消
                            }
                        });
                    }else if(zt=='3'){
                        layer.msg("数据已上报，不允许重复查询！");
                    }else if(zt=='5'){
                        layer.msg("数据已超时，不允许查询！");
                    }
                }

                // 查询结果
                if (layEvent == "cxjg") {
                    localStorage.clear();
                    localStorage.setItem('cxsqdh',cxsqdh);
                    var xmid = data.xmid;
                    if (xmid !=undefined && xmid !=null) {
                        localStorage.setItem('xmid',xmid);
                    }
                    window.open('../view/queryResult.html');
                }
            }
        })

        //监听lay-filter="dataTable"的table的表格头工具栏事件
        table.on('toolbar(dataTable)', function (obj) {
            if(obj.event != "LAYTABLE_COLS"){
                var checkStatus = table.checkStatus(obj.config.id);
                var data = checkStatus.data;
                if (data && data.length > 0) {
                    if (obj.event === "access") {
                        plsb(data);
                    }
                    if (obj.event === "plzxcx") {
                        plcx(data);
                    }
                } else {
                    layer.alert("请至少选择一条数据进行操作")
                }
            }
        });

        // 查询操作
        function executeQuery(cxsqdh) {
            $.ajax({
                type: "post",
                url: "../cxqq/query/executeQuery",
                data:{cxsqdh:cxsqdh},
                dataType: "json",
                success: function (result) {
                    layer.msg(result.msg);
                    removeModal();
                    loadDataTablbeByUrl("#requestTable", tableConfig);
                },
                error: function (result) {
                    layer.msg("查询失败！");
                }
            });
        }

        //单条上报
        function executeCommit(cxsqdh){
            $.ajax({
                type: "post",
                url: "../cxqq/query/commit",
                dataType: "json",
                data:{cxsqdh:cxsqdh},
                success: function (result) {
                    layer.msg(result.msg);
                    removeModal();
                    loadDataTablbeByUrl("#requestTable", tableConfig);
                },
                error: function (result) {
                    layer.msg("上报失败！");
                }
            });
        }

        // 批量上报
        function plsb(data) {
            var cxsqdhList = [];
            for (var i = 0; i < data.length; i++) {
                cxsqdhList.push(data[i].cxsqdh);
            }
            addModel();
            $.ajax({
                type: "post",
                url: "../cxqq/query/commit/cxsqdhList",
                contentType: "application/json",
                dataType: "json",
                data:JSON.stringify(cxsqdhList),
                success: function (data) {
                    layer.msg("上报成功！");
                    removeModal();
                    tableReload("requestTable",form.val('searchForm'));
                },
                error: function (xhr, status, error) {
                    removeModal();
                    layer.msg("上报失败!");
                }
            });
        }

        // 批量查询
        function plcx(data) {
            // 正常执行查询的数据
            var cxsqdhList = [];
            // 已存在查询结果的数据
            var cxsqdhyczList = [];
            // 已存在查询结果的数据
            var yczcxtsxx="";
            // 不允许查询结果的数据
            var byxcxtsxx="";
            for (var i = 0; i < data.length; i++) {
                if(data[i].zt ==1){
                    cxsqdhList.push(data[i].cxsqdh);
                }else if(data[i].zt == 2|| data[i].zt == 4) {
                    cxsqdhyczList.push(data[i].cxsqdh);
                    if(isNotBlank(yczcxtsxx)){
                        yczcxtsxx = yczcxtsxx + ",<br>查询单号" + data[i].cxsqdh;
                    }else{
                        yczcxtsxx = "查询单号" + data[i].cxsqdh;
                    }
                }else if(data[i].zt == 3|| data[i].zt == 5) {
                    if(isNotBlank(byxcxtsxx)){
                        byxcxtsxx = byxcxtsxx + "," + data[i].cxsqdh;
                    }else{
                        byxcxtsxx = byxcxtsxx + data[i].cxsqdh;
                    }
                }
            }
            if(isNotBlank(byxcxtsxx)){
                byxcxtsxx = byxcxtsxx + "数据已上报或已超时，不允许查询！";
            }
            if(isNotBlank(cxsqdhyczList)){
                var submitIndex = layer.open({
                    type: 1,
                    title: '提示',
                    skin: 'bdc-small-tips',
                    content: yczcxtsxx + '<br>' + '已存在查询结果，是否查询最新数据并覆盖？',
                    btn: ['确定','取消'],
                    btnAlign: 'c',
                    yes: function(){
                        layer.close(submitIndex);
                        for (var i = 0; i < cxsqdhyczList.length; i++) {
                            cxsqdhList.push(cxsqdhyczList[i]);
                        }
                        batchSearch(cxsqdhList, byxcxtsxx);
                    },
                    btn2: function () {
                        if (isNotBlank(cxsqdhList)) {
                            batchSearch(cxsqdhList, byxcxtsxx);
                        }
                    }
                });
            } else if (isNotBlank(cxsqdhList)) {
                batchSearch(cxsqdhList, byxcxtsxx);
            }
        }

        // 批量查询
        function batchSearch(cxsqdhList,byxcxtsxx) {
            addModel();
            $.ajax({
                type: "post",
                url: "../cxqq/query/executeQueryList",
                contentType: "application/json",
                dataType: "json",
                data:JSON.stringify(cxsqdhList),
                success: function (result) {
                    removeModal();
                    layer.msg(result.msg  + "<br>" + byxcxtsxx);
                },
                error: function (result) {
                    removeModal();
                    layer.msg("查询失败！");
                }
            });
        }
        // 查询人状态
        function cxrzt(zt) {
            if (zt == "1") {
                return "已查询";
            } else {
                return "未查询";
            }
        }
        /**
         *@desc  监听cxrTable表的操作工具条
         */
        table.on('tool(cxrTable)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            var cxsqdh = data.cxsqdh;
            var wsbh = data.wsbh;
            var cxzt = data.cxzt;
            // 查看文书信息
            if (layEvent == "ckwsxx") {
                $.ajax({
                    type: "post",
                    url: "../cxqq/query/getWsxxList",
                    dataType: "json",
                    data:{cxsqdh:cxsqdh,wsbh:wsbh},
                    success: function (result) {
                        if (result.msg == '') {
                            var submitIndex = layer.open({
                                type: 1,
                                title: '提示',
                                skin: 'bdc-small-tips',
                                area: ['320px'],
                                content: '文书还未获取，是否先获取文书信息！',
                                btn: ['确定','取消'],
                                btnAlign: 'c',
                                yes: function(){
                                    layer.close(submitIndex);
                                    regetWsxx(cxsqdh,wsbh);
                                    addModel();
                                },
                                btn2: function(){
                                    //取消
                                }
                            });
                        } else {
                            layer.open({
                                type:2,
                                title:'文书信息',
                                area:['500px','300px'],
                                btnAlign: 'c',
                                content: result.msg
                            });
                        }
                    },
                    error: function (result) {
                        layer.msg("获取文书失败！");
                    }
                });
            }

            // 重新获取文书信息
            if (layEvent == "cxhqwsxx") {
                var submitIndex = layer.open({
                    type: 1,
                    title: '提示',
                    skin: 'bdc-small-tips',
                    area: ['320px'],
                    content: '确认重新获取文书信息！',
                    btn: ['确定','取消'],
                    btnAlign: 'c',
                    yes: function(){
                        layer.close(submitIndex);
                        regetWsxx(cxsqdh,wsbh);
                        addModel();
                    },
                    btn2: function(){
                        //取消
                    }
                });

            }

            // 查询结果
            if (layEvent == "cxrcxjg"){
                localStorage.clear();
                localStorage.setItem('cxsqdh',cxsqdh);
                var xmid = data.xmid;
                if (xmid !=undefined && xmid !=null) {
                    localStorage.setItem('xmid',xmid);
                }
                window.open('../view/queryResult.html');
            }

            // 执行查询
            if (layEvent == "cxrzxcx"){
                var xmid = data.xmid;
                if (cxzt == 1) {
                    layer.confirm('已存在查询结果，是否查询最新数据并覆盖？',{title: '提示'}, function (index) {
                        layer.close(index);
                        executeQueryCxqqXm(xmid);
                    });
                } else {
                    executeQueryCxqqXm(xmid)
                }
            }

        })

        var scrollTop = 0;
        var tableTop = 0,tableLeft = 0;
        var $nowBtnMore = '';
        //监听滚动事件
        var scrollTop = 0,
            scrollLeft = 0;
        var tableTop = 0,tableLeft = 0;
        var $nowBtnMore = '';
        $(window).on('scroll',function () {
            scrollTop = $(this).scrollTop();
            scrollLeft = $(this).scrollLeft();

            if($nowBtnMore != ''){
                if(tableTop + 26 + $nowBtnMore.height() < document.body.offsetHeight){
                    // $nowBtnMore.css({top: tableTop + 26 -scrollTop,right: 20});
                    $nowBtnMore.css({top: tableTop + 26 -scrollTop,left: tableLeft - 30});
                }else {
                    // $nowBtnMore.css({top: tableTop -scrollTop - $nowBtnMore.height(),right: 20});
                    $nowBtnMore.css({top: tableTop -scrollTop - $nowBtnMore.height(),left: tableLeft - 30});
                }
            }
        });
        $('.bdc-table-box .layui-table-body.layui-table-main').on('scroll',function () {
            $nowBtnMore.hide();
        });
        // 点击表格中的更多
        $('.bdc-table-box').on('click','.bdc-more-btn',function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            event.stopPropagation();
            $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
            var $btnMore = $(this).siblings('.bdc-table-btn-more');
            if($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight){
                // $btnMore.css({top: $(this).offset().top + 26 -scrollTop,right: 20});
                $btnMore.css({top: tableTop + 26 -scrollTop,left:tableLeft - 30});
            }else {
                // $btnMore.css({top: $(this).offset().top -scrollTop - $btnMore.height(),right: 20});
                $btnMore.css({top: tableTop -scrollTop - $btnMore.height(),left: tableLeft - 30});
            }
            $('.bdc-table-btn-more').hide();
            $btnMore.show();
        });

        //点击更多内的任一内容，隐藏
        $('.bdc-table-btn-more a').on('click',function () {
            $(this).parent().hide();
        });
        //点击页面任一空白位置，消失
        $('body').on('click',function () {
            $('.bdc-table-btn-more').hide();
        });

        function executeQueryCxqqXm(xmid) {
            $.ajax({
                type: "post",
                url: "../cxqq/query/executeQueryXm",
                data:{xmid:xmid},
                dataType: "json",
                success: function (result) {
                    layer.msg(result.msg);
                },
                error: function (result) {
                    layer.msg("查询失败！");
                }
            });
        }

        function regetWsxx(cxsqdh,wsbh) {
            //获取文书信息
            $.ajax({
                type: "post",
                url: "../cxqq/query/cxhqWsxx",
                dataType: "json",
                data:{cxsqdh:cxsqdh,wsbh:wsbh},
                success: function (result) {
                    layer.msg(result.msg);
                    removeModal();
                },
                error: function (result) {
                    layer.msg("获取失败！");
                }
            });
        }

        function addModel() {
            var modalHtml = '<div id="waitModalLayer">'+
                '<p class="bdc-wait-content">'+
                '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>'+
                // '<i class="layui-icon layui-icon-loading"></i>'+
                '<span>加载中</span>'+
                '</p>'+
                '</div>';
            $('body').append(modalHtml);
        }

        function removeModal() {
            $("#waitModalLayer").remove();
        }

        function loadDataTablbeByParam(_domId, _tableConfig,_param) {
            layui.use(['table', 'laypage', 'jquery'], function () {
                if (_domId) {
                    var table = layui.table;
                    var $ = layui.jquery;
                    var tableDefaultConfig = {
                        toolbar: true,
                        defaultToolbar: ['filter'],
                        even: true,
                        elem: _domId
                        , cellMinWidth: 80
                        , page: true //开启分页
                        , limit: 10
                        , parseData: function (res) { //res 即为原始返回的数据
                            res.hasContent=true
                            return res;
                        }
                        , request: {
                            limitName: 'size' //每页数据量的参数名，默认：limit
                        }
                        , response: {
                            countName: 'totalElements' //数据总数的字段名称，默认：count
                            , dataName: 'content' //数据列表的字段名称，默认：data
                            , statusName: 'hasContent' //规定数据状态的字段名称，默认：code
                            , statusCode: true //规定成功的状态码，默认：0
                        }
                        ,where : _param
                    };
                    if (!_tableConfig.cols || !_tableConfig.cols[0] || _tableConfig.cols[0].length < 1) {
                        _tableConfig.cols = [[]]
                    }
                    var tableRenderConfig = $.extend({}, tableDefaultConfig, _tableConfig);
                    table.render(tableRenderConfig);
                }
            });
        }

    });
});