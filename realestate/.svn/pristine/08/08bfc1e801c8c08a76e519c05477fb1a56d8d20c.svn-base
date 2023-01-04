/*获取字典数据*/

var table;
var form;
var laydate;
/*table数据加载*/
$(function () {

    layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
        table = layui.table;
        form = layui.form;
        laydate = layui.laydate;
        laydate.render({
            elem: '#basjq',
            format: 'yyyy-MM-dd',
            done: function (value, date, endDate) {
                var startDate = new Date(value).getTime();
                var endTime = new Date($('#basjz').val()).getTime();
                if (endTime < startDate) {
                    layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">结束时间不能小于开始时间');
                }
            }
        });
        laydate.render({
            elem: '#basjz',
            format: 'yyyy-MM-dd',
            done: function (value, date, endDate) {
                var endTime = new Date(value).getTime();
                var startDate = new Date($('#basjq').val()).getTime();
                if (endTime < startDate) {
                    layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">结束时间不能小于开始时间');
                }
            }
        });

        //不动产单元号的表头
        var unitTableTitle = [
            {type: 'checkbox', fixed: 'left'},
            {field: 'htbabm', title: '合同编号', width: "9%"},
            {field: 'msr', title: '买受人', width: "14%"},
            {
                field: 'basj', title: '备案日期', width: "10%",
                templet: function (d) {
                    return Format(d.basj, "yyyy年MM月dd日");
                }
            },
            {field: 'bdcdyh', title: '不动产单元号', width: "25%"},
            {field: 'xmldfh', title: '坐落', width: "25%"},
            {
                field: 'tbzt', title: '同步状态', width: "5%", templet: function (d) {
                    return getTbzt(d.bdcdyh);
                }
            },
            {field: 'CZ', title: '操作', width: "9%", templet: '#htbaczTpl', fixed: "right"}
        ];
        var url = getContextPath() + '/rest/v1.0/htbaxx/synctable';
        var htbaxxQO = form.val("searchform");
        var tableConfig = {
            id: 'baid',
            url: "",
            where: {htbaxxQO: JSON.stringify(htbaxxQO)},
            defaultToolbar: ['filter'],
            page: true,
            toolbar: '#toolbarDemo',
            cols: [unitTableTitle],
            done: function (res, curr, count) {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() === 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
                }
                var reverseList = ['ZL'];
                reverseTableCell(reverseList, "baid");
            }
        };

        //加载表格
        loadDataTablbeByUrl('#baxxTable', tableConfig);
        //监听行工具事件，表格操作栏内部按钮
        table.on('tool(baxxTable)', function (obj) {
            var baxx = obj.data;
            switch (obj.event) {
                case 'sync':
                    var baxxList = [];
                    baxxList.push(baxx);
                    syncBaxx(baxxList);
                    break;
            }
        });

        // 监听表格头部按钮
        table.on('toolbar(baxxTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                //勾选后批量同步
                case 'syncpl':
                    if (checkStatus.data.length > 0) {
                        var baxxList = [];
                        checkStatus.data.forEach(function (element, sameElement, set) {
                            baxxList.push(element)
                        });
                        syncBaxx(baxxList);
                    } else {
                        ityzl_SHOW_INFO_LAYER("请先勾选数据再操作");
                    }
                    break;
                //根据查询条件同步所有数据
                case 'syncall':
                    var htbaxxQO = form.val("searchform");
                    if (isEmptyObject(htbaxxQO.htbh) && isEmptyObject(htbaxxQO.basjq) && isEmptyObject(htbaxxQO.basjz) && isEmptyObject(htbaxxQO.bdcdyh) && isEmptyObject(htbaxxQO.tbzt)) {
                        ityzl_SHOW_WARN_LAYER("请输入至少一个查询条件!")
                        return false
                    }
                    syncAll(htbaxxQO);
            }
        });

        //提交表单
        form.on("submit(queryBaxx)", function (data) {
            var baxxQO = data.field;
            debugger
            if (isEmptyObject(baxxQO.htbh) && isEmptyObject(baxxQO.basjq) && isEmptyObject(baxxQO.basjz) && isEmptyObject(baxxQO.bdcdyh)&& isEmptyObject(baxxQO.tbzt)) {
                ityzl_SHOW_WARN_LAYER("请输入至少一个查询条件!")
                return false
            }
            tableReloadNew('baid', data.field, url);
            return false;
        });

        //回车操作
        $(document).keydown(function (event) {
            if (event.keyCode === 13) {
                $('#queryBaxx').click();
            }
        });
        renderSearchInput();
    });

});

//同步单条备案信息数据
function syncBaxx(baxxList) {
    addModel("同步中...");
    getReturnData("/rest/v1.0/htbaxx/sync", JSON.stringify(baxxList), "POST", function (data) {
        removeModal();
        showAlertDialog("成功同步数据量:" + data + "条");
        if (data > 0) {
            $('#queryBaxx').click();
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    });
}

function syncAll(htbaxxQO) {
    addModel("同步中...");
    getReturnData("/rest/v1.0/htbaxx/syncall", {
        htbh: htbaxxQO.htbh,
        bdcdyh: htbaxxQO.bdcdyh,
        basjq: htbaxxQO.basjq,
        basjz: htbaxxQO.basjz
    }, "GET", function (data) {
        removeModal();
        showAlertDialog("成功同步数据量:" + data + "条");
        if (data > 0) {
            $('#queryBaxx').click();
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    });
}

function getTbzt(bdcdyh) {
    var result = "";
    getReturnData("/rest/v1.0/htbaxx/synczt", {bdcdyh: bdcdyh}, "GET", function (data) {
        if (data === 0) {
            result = '<span class="bdc-table-state-wtb" style="color: #f54743">未同步</span>';
        } else {
            result = '<span class="bdc-table-state-ytb" style="color: #10a54a">已同步</span>';
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    return result;
}