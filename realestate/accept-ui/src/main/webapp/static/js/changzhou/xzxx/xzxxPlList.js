/*
* 一个流程创建多次修正流程列表展现
*
* */
var zdData;
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
$(function () {
    layui.use(['form', 'table', 'jquery'], function () {
        var table = layui.table;
        //不动产单元号的表头
        var unitTableTitle = [
            {field: 'slbh', title: '受理编号', width: "15%"},
            {field: 'qlr', title: '权利人', width: "10%"},
            {field: 'zl', title: '坐落', width: "20%"},
            {field: 'slr', title: '受理人', width: "10%"},
            {field: 'lcmc', title: '流程名称', width: "15%"},
            {field: 'djyy', title: '登记原因', width: "15%"},
            {field: 'jdmc', title: '节点名称', width: "10%"},
            {field: 'cz', title: '操作', width: "10%", templet: '#bdcdyczTpl', fixed: "right"}
        ];
        var url = getContextPath() + '/xzxx/page/pl';

        var tableConfig = {
            id: 'xzxxid',
            url: url,
            where: {gzlslid: processInsId},
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            done: function () {
                $('td[data-field="zl"]').css({
                    "direction": "rtl"
                });
            }
        };

        //加载表格
        loadDataTablbeByUrl('#xzTable', tableConfig);
        //监听行工具事件
        table.on('tool(xzTable)', function (obj) {
            var data = obj.data;
            var url = "/realestate-accept-ui/rest/v1.0/xzxx?processInsId=" + data.gzlslid + "&readOnly=true";
            layerCommonOpenFull(url, "修正信息", $(window).width() + "px", $(window).height() + "px", $(window).width() + "px", $(window).height() + "px");

        });

    });
});