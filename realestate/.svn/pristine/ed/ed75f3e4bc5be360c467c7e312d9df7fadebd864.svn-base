var zdData;
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var xmids = getQueryString("xmids");
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
        var url = getContextPath() + '/bdcdyh/xmxx';

        var tableConfig = {
            id: 'xmid',
            url: url,
            where: {xmids: xmids},
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            done: function () {
                $('td[data-field="zl"]').css({
                    "direction": "rtl"
                });
            }
        };

        //加载表格
        loadDataTablbeByUrl('#sjclTable', tableConfig);
        //监听行工具事件
        table.on('tool(sjclTable)', function (obj) {
            var data = obj.data;
            if (data.xmly === "1" || obj.event === "bccl") {
                //打开文件管理器页面
                var bdcSlWjscDTO = queryBdcSlWjscDTO('', true);
                bdcSlWjscDTO.spaceId = data.gzlslid;
                bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
                // bdcSlWjscDTO.storageUrl = "http://192.168.2.99:8030/storage";
                url = getContextPath() + "/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
                openSjcl(url, false, "附件上传");
                return false;
            } else {
                url = "/realestate-etl/view/wwsq/popup-img.html?processinsid=" + data.gzlslid;
                openSjcl(url, false, "附件上传");
                return false;
            }

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