var zdList;
var form;
var laydate;
var table;
$(function () {
    /**
     * 获取字典
     */
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
        table = layui.table;
        laydate = layui.laydate;
        form = layui.form;
        form.render();
        //不动产单元号的表头
        var unitTableTitle = [
            {field: 'xh', type: 'numbers', width: 60, title: '序号'},
            {field: 'slbh', title: '受理编号', width: "10%", templet: '#slbhTpl'},
            {field: 'qlrmc', title: '缴费人', width: "10%"},
            {field: 'lxdh', title: '联系方式', width: "8%"},
            {field: 'jfsbm', title: '缴费书编码', width: "14%"},
            {field: 'slr', title: '受理人', width: "6%"},
            {field: 'jkfs', title: '缴款方式', width: "6%"},
            {field: 'djf', title: '登记费', width: "6%"},
            {field: 'gbf', title: '工本费', width: "6%"},
            {field: 'tdqlr', title: '土地权', width: "6%"},
            {field: 'tdywr', title: '土地义', width: "6%"},
            {field: 'jmyy', title: '减免原因'},
            {field: 'ajzt', title: '案件状态', width: "6%"},
            {field: 'qxdm', title: '区县代码', width: "6%"}
        ];
        var url = getContextPath() + '/sf/wjf/page?loadTotal=true';

        var tableConfig = {
            id: 'xmid',
            url: url,
            where: {},
            defaultToolbar: ['filter'],
            toolbar: "#toolbar",
            cols: [unitTableTitle],
            done: function () {
                reverseList.push("qlrmc", "jfsbm")
                $('td[data-field="qlrmc"]').css({
                    "direction": "rtl"
                });
                $('td[data-field="jfsbm"]').css({
                    "direction": "rtl"
                });
            },
            limits: [10,50,100,200,500],
            limit:500
        };
        //加载表格
        loadDataTablbeByUrl('#sfxxTable', tableConfig);
        //监听行工具事件
        table.on('toolbar(sfxxTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'plgxjfzt':
                    plgxjfzt();
                    break;
                case 'exportAll':
                    exportAll(checkStatus.data, obj.config);
                    break;

            }
        });

        $.each(zdList.jkfs, function (index, item) {
            $('#jkfs').append('<option value="' + item.MC + '">' + item.MC + '</option>');
        });
        // 日期控件
        laydate.render({
            elem: '#cxqssj',
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss'
        });
        laydate.render({
            elem: '#cxjssj',
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss'
        });

        // 日期控件
        laydate.render({
            elem: '#gxqssj',
            type: 'datetime',
            format: 'yyyy-MM-dd'
        });
        laydate.render({
            elem: '#gxjssj',
            type: 'datetime',
            value: new Date(),
            format: 'yyyy-MM-dd'
        });

        form.render();

        form.on("submit(queryJfxx)", function (data) {
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            if(count == 0){
                ityzl_SHOW_WARN_LAYER("请输入查询条件");
                return false;
            }
            loadTable('xmid', url, data.field);
            return false;
        });

    });
});

function plgxjfzt() {
    //先打开时间选择
    layer.open({
        title: '请选择时间段',
        type: 1,
        area: ['500px', '300px'],
        btn: ['确定', '取消'],
        content: $('#bdc-popup-small'),
        yes: function (index, layero) {
            //传入开始结束时间，查询相关数据并调用缴费查询接口更新缴费状态
            var gxqssj = $("#gxqssj").val();
            var gxjssj = $("#gxjssj").val();
            var qsTime = new Date(gxqssj).getTime();
            var jsTime = new Date(gxjssj).getTime();
            var time = jsTime - qsTime;
            if (time < 0) {
                ityzl_SHOW_WARN_LAYER('结束时间不能小于开始时间');
                return false;
            }
            var day = time / (24 * 3600 * 1000)
            if (day > 185) {
                //如果天数大于180，半年时间，提示只能选择半年内数据
                ityzl_SHOW_WARN_LAYER('最多只能选择半年数据');
                return false;
            }
            getReturnData("/sf/wjf/gxsfzt", {gxqssj: gxqssj, gxjssj: gxjssj}, "GET", function (data) {
                layer.close(index);
                ityzl_SHOW_WARN_LAYER('后台正在更新缴费状态');
            }, function (xhr) {
                showAlertDialog((JSON.parse(xhr.responseText)).msg + "详细查看系统日志")
            })
        } , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
    });
}

// 导出全部
function exportAll(data, obj){
    var paramData = obj.where;
    var cols = obj.cols[0]
    addModel();
    $.ajax({
        url: getContextPath() + '/sf/wjf',
        dataType: "json",
        data: paramData,
        success: function (data) {
            removeModal();
            if(data.length > 0){
                //查询成功
                // 标题
                var showColsTitle = [];
                // 列内容
                var showColsField = [];
                // 列宽
                var showColsWidth = [];
                for (var index in cols) {
                    if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
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
                    data[i].xh = i + 1;
                    if(!data[i].tdywr || data[i].tdywr == 0){
                        data[i].tdywr = "";
                    }
                    if(!data[i].tdqlr  || data[i].tdqlr == 0){
                        data[i].tdqlr = "";
                    }
                }
                // 设置Excel基本信息
                $("#fileName").val('已缴费信息');
                $("#sheetName").val('统计表');
                $("#cellTitle").val(showColsTitle);
                $("#cellWidth").val(showColsWidth);
                $("#cellKey").val(showColsField);
                $("#data").val(JSON.stringify(data));
                $("#form").submit();
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}
function loadTable(tableId, url, param) {
    table.reload(tableId, {
        url: url
        , where: param
        , page: {
            curr: 1, //重新从第 1 页开始
            limits: [10, 50, 100, 200, 500],
            limit: 500
        }
        , done: function (res, curr, count) {
            // reverseTableCell(reverseList, tableId);
            removeModal();
            setHeight();
            //处理合计信息 返回结果求和
            //根据查询条件重新请求求和
            var result = res.data;
            getReturnData("/sf/wjfhj", param, "GET", function (data) {
                if (data) {
                    if (data.HJ) {
                        $("#sumhj").text(data.HJ + "元");
                    } else {
                        $("#sumhj").text("0元");
                    }
                    if (data.JFBS) {
                        $("#lcCount").text(data.JFBS);
                    } else {
                        $("#lcCount").text(0);
                    }
                } else {
                    $("#sumhj").text("0元");
                    $("#lcCount").text(0);
                }
            }, function (xhr) {
                delAjaxErrorMsg(xhr);
            })
            $(".bdc-table-zj").removeClass("bdc-hide");
            $('td[data-field="qlrmc"]').css({
                "direction": "rtl"
            });
            $('td[data-field="jfsbm"]').css({
                "direction": "rtl"
            });
        }
    });
}

//受理编号点击跳转到详情页面
function slbhDetail(processId, xmid) {
    if(isNullOrEmpty(processId) || isNullOrEmpty(xmid)) {
        layer.msg('缺失工作流实例ID和项目ID参数，无法查看。');
        return;
    }
    window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&xmid=' + xmid + '&processinsid=' + processId );
}