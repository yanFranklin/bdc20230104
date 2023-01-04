var zdData;
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
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
                zdData = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

    layui.use(['form', 'table', 'jquery'], function () {
        var table = layui.table;
        //不动产单元号的表头
        var unitTableTitle = [
            {field: 'slbh', title: '受理编号', width: "25%"},
            {field: 'qlr', title: '权利人', width: "25%"},
            {field: 'bdcqzh', title: '不动产权证号', width: "15%"},
            {field: 'zl', title: '坐落', width: "25%"},
            {field: 'cz', title: '操作', width: "10%", templet: '#bdcdyczTpl', fixed: "right"}
        ];
        var url = getContextPath() + '/xzxx/page';

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
            layer.open({
                title: ['修正信息'],
                type: 2,
                area: ['100%', '100%'],
                resize: true,
                content: getContextPath() + '/view/xzxx/xzxx.html?xzxxid=' + data.xzxxid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&processInsId=" + data.gzlslid,
            });

        });

    });

});


function changeDjxl(djxl) {
    for (var i = 0; i < zdData.djxl.length; i++) {
        if (djxl == zdData.djxl[i].DM) {
            djxl = zdData.djxl[i].MC
        }
    }
    return djxl;
}