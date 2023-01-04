var moduleCode = GetQueryString("moduleCode");
var reverseList = ['zl'];
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl','laydate'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;

    $(function () {
        //初始化日期控件
        laydate.render({
            elem: '#kssj',
        });
        laydate.render({
            elem: '#jssj',
        });

        var tableConfig = {
            toolbar: "#toolbarSqxx" ,
            limits: [10, 50, 100, 200, 500] ,
            cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {type: 'numbers', title: '序号', width: 70},
                {field: 'xzqdm', title: '行政区代码', width: 300},
                {field: 'xzqmc', title: '行政区名称', width: 300},
                {field: 'jrrq', title: '日期', width: 300},
                {field: 'cgbsmc', title: '国家上报是否成功', width: 250},
                {field: 'sjcgbsmc', title: '省级上报是否成功', width: 250},
                {
                    title: '操作',
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#toolBarTmpl'
                }
            ]],
            page: true,
            data:[],
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body').height('60px');
                    $('.layui-table-fixed .layui-table-body').height('60px');
                    $('.layui-table-body .layui-none').html('<img src="../static/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
                setElementAttrInTableByModuleAuthority(moduleCode);
            }
        };

        loadDataTablbeByUrl("#accessTable", tableConfig);
        //提交表单
        form.on("submit(query)", function (data) {
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            if (0 == count) {
                layer.msg('<img src="../static/lib/bdcui/images/success-small.png" alt="">请输入必要查询条件');
                return false;
            }

            layui.use(['table'], function () {
                var table = layui.table;
                table.reload("accessTable", {
                    where: data.field ,
                    page: {
                        //重新从第 1 页开始
                        curr: 1
                    },
                    url: '../registerLog/getRegisterLogPagesJson',
                });
            });
            return false;
        });

        //头工具栏事件
        table.on('toolbar(dataTable)', function (obj) {
            if(obj.event != "LAYTABLE_COLS"){
                var checkStatus = table.checkStatus(obj.config.id);
                var data = checkStatus.data;
                if (obj.event === "exportExcel") {
                    if (checkStatus.data.length  > 0) {
                        exportExcel(checkStatus.data, obj.config.cols[0]);
                    } else {
                        exportAllExcel(checkStatus.data, obj.config);
                    }
                    return;
                }
                if (data && data.length > 0) {

                    if (obj.event === "access") {
                        access(data)
                    }
                    if (obj.event === "accessByTime") {
                        accessByTime(data)
                    }
                } else {
                    layer.alert("请选择一条数据进行操作")
                }
            }
        });

        table.on('tool(dataTable)', function (obj) {
            var data = obj.data;
            if (data) {
                if (obj.event === "moredetail") {
                    var index = layer.open({
                        type: 2,
                        title: "登簿日志明细",
                        area: ['1300px', '600px'],
                        fixed: false, //不固定
                        maxmin: true, //开启最大化最小化按钮
                        content: "../view/dbrzmx.html?id=" + data.id
                    });
                    layer.full(index);
                }
                if (obj.event === "dbxq") {
                    var index = layer.open({
                        type: 2,
                        title: "登簿日志详情",
                        area: ['1300px', '600px'],
                        fixed: false, //不固定
                        maxmin: true, //开启最大化最小化按钮
                        content: "../view/dbrzxq.html?dbrzid=" + data.id
                    });
                    layer.full(index);
                }

            }
        });

        // 导出Excel
        // 通过隐藏form提交方式，避免ajax方式不支持下载文件
        function exportExcel(d, cols) {
            if ($.isEmptyObject(d)) {
                warnMsg("请选择需要导出Excel的数据行！");
                return;
            }
            // 标题
            var showColsTitle = new Array();
            // 列内容
            var showColsField = new Array();
            // 列宽
            var showColsWidth = new Array();
            for (var index in cols) {
                if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
                    showColsTitle.push(cols[index].title);
                    showColsField.push(cols[index].field);
                    if (isNullOrEmpty(cols[index].width)) {
                        showColsWidth.push(30);
                    } else {
                        showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                    }
                }
            }

            // 设置Excel基本信息
            $("#fileName").val('登簿日志查询信息导出Excel表');
            $("#sheetName").val('登簿日志查询信息导出Excel表');
            $("#cellTitle").val(showColsTitle);
            $("#cellWidth").val(showColsWidth);
            $("#cellKey").val(showColsField);

            var data = new Array();
            $.each(d, function (key, value) {
                data.push(value);
            })
            for (var i = 0; i < data.length; i++) {
                data[i].xh = i + 1;
            }
            $("#data").val(JSON.stringify(data));
            $("#form").submit();
        }

        // 导出所有查询结果Excel
        function exportAllExcel(data, obj) {
            layer.confirm("是否导出全部数据？", {
                title: "提示",
                btn: ["确认", "取消"],
                btn1: function (index) {
                    var cols = obj.cols[0]
                    var url = obj.url;
                    var paramData = obj.where;
                    paramData["type"] = "export";

                    $.ajax({
                        url: url,
                        dataType: "json",
                        data: paramData,
                        success: function (data) {
                            obj.where.type = "";
                            if(data.length > 0){//查询成功
                                exportExcel(data,cols);
                            }
                        }
                    });

                    layer.close(index);
                },
                btn2: function (index) {
                    obj.where.type = "";
                    return;
                }
            });
        }

        function access(checkeddata){
            addModelDb();
            for (var i = 0; i < checkeddata.length; i++) {
                if (checkeddata[i]["cgbs"] !== '1' && checkeddata[i]["sjcgbs"] != '1') {
                    $.ajax({
                        url: getContextPath() + '/registerLog/dbrz',
                        dataType: "json",
                        type: "GET",
                        async: false,
                        data: {'date': checkeddata[i].jrrq, 'qxdm': checkeddata[i].xzqdm},
                        success: function (data) {
                        }
                    });
                }
            }
            tableReload("accessTable", form.val('searchForm'));
            removeModalDb();
        }

        function accessByTime(){
            if ($("#kssj").val() && $("#jssj").val()
                && $("#kssj").val() === $("#jssj").val()) {
                addModelDb();
                $.ajax({
                    url: getContextPath() + '/registerLog/dbrz',
                    dataType: "json",
                    type: "GET",
                    async: false,
                    data: {'date': $("#kssj").val(), 'qxdm': $("#xzqdm").val()},
                    success: function (data) {
                        removeModalDb();
                        layer.msg("上报成功");
                        tableReload("accessTable", form.val('searchForm'));
                    },
                    error:function () {
                        removeModalDb();
                        layer.msg("上报失败,请联系管理员");
                    }
                });
            } else {
                layer.alert("请输入正确的开始日期和结束日期");
            }
        }
    });


    var zdList = getMulZdList("qx");

    console.info(zdList);
    if(zdList['qx'].length > 0){
        $.each(zdList['qx'], function(index, value){
            $("#xzqdm").append("<option value='" + value.DM + "'>" + value.MC + "</option>");
        });
        form.render('select');
    }

});

//查询遮罩
function addModelDb() {
    var modalHtml = '<div id="waitModalLayer">'+
        '<p class="bdc-wait-content">'+
        '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>'+
        '<span>操作中</span>'+
        '</p>'+
        '</div>';
    $('body').append(modalHtml);
}

// 去除遮罩
function removeModalDb() {
    $("#waitModalLayer").remove();
}