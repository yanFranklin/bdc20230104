var zdData;
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
            {field: 'bdcdyh', title: '不动产单元号', width: "25%", templet: '#bdcdyhTpl'},
            {field: 'zl', title: '坐落', width: "25%"},
            {field: 'qlrmc', title: '权利人', width: "15%"},
            {field: 'djxl', title: '登记小类', width: "25%", templet: '#djxlTpl'},
            {field: 'cz', title: '操作', width: "10%", templet: '#bdcdyczTpl', fixed: "right"}
        ];
        var url = getContextPath() + '/sf/listsfxxbypage';

        var tableConfig = {
            id: 'xmid',
            url: url,
            where: {gzlslid: processInsId},
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            done: function() {
                $('td[data-field="zl"]').css({
                    "direction": "rtl"
                });
            }
        };

        //加载表格
        loadDataTablbeByUrl('#sfxxTable', tableConfig);
        //监听行工具事件
        table.on('tool(sfxxTable)', function (obj) {
            var data = obj.data;
            layer.open({
                title: ['收费信息', 'font-size:16px;font-weight: 700;'],
                type: 2,
                area: ['100%', '100%'],
                resize: true,
                content: getContextPath() + '/view/sfxx/sfxx.html?xmid=' + data.xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&processInsId=" + processInsId +"&djxl="+data.djxl
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