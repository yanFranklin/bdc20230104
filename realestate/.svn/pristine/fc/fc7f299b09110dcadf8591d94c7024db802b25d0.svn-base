var zdData;
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var xmid = getQueryString("xmid");
var xzlcymlx = getQueryString("xzlcymlx");
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
            {field: 'bdcdyh', title: '不动产单元号', width: "20%", templet: '#bdcdyhTpl'},
            {field: 'zl', title: '坐落', width: "25%"},
            {field: 'qlrmc', title: '权利人', width: "10%"},
            {field: 'zh', title: '幢号', width: "6%"},
            {field: 'dzwmj', title: '面积', width: "6%"},
            {field: 'djxl', title: '登记小类', width: "20%", templet: '#djxlTpl'},
            {field: 'cz', title: '操作', templet: '#bdcdyczTpl', fixed: "right"}
        ];
        var url = getContextPath() + '/slym/xm/listNowBdcdyByPageJson';

        var tableConfig = {
            id: 'xmid',
            url: url,
            where: {xmid: xmid},
            toolbar: "#plxg",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            done: function () {
                $('td[data-field="zl"]').css({
                    "direction": "rtl"
                });
            }
        };

        //加载表格
        loadDataTablbeByUrl('#xzlcTable', tableConfig);
        //监听行工具事件
        table.on('tool(xzlcTable)', function (obj) {
            var data = obj.data;
            layer.open({
                title: ['受理修正页面'],
                type: 2,
                area: ['100%', '100%'],
                resize: true,
                content: getContextPath() + '/view/xzxx/slymxz.html?xmid=' + data.xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&processInsId=" + (isNotBlank(data.gzlslid) ? data.gzlslid : "") + "&xzlcymlx=" + xzlcymlx,
            });

        });

        //表格头部工具
        table.on('toolbar(xzlcTable)', function (obj) {
            if (obj.event === "cqplxg") {
                //后台判断获取产权的xmid，存入session
                var cqxmidList = [];
                getReturnData("/slym/ql/cqxmid", JSON.stringify(xmid.split(",")), "POST", function (data) {
                    cqxmidList = data;
                }, function (xhr) {
                    delAjaxErrorMsg(xhr)
                }, false);
                if (cqxmidList.length === 0) {
                    ityzl_SHOW_WARN_LAYER("未找到对应的产权项目数据");
                    return false;
                }
                sessionStorage.removeItem("plxg_cqxmids");
                sessionStorage.setItem("plxg_cqxmids", cqxmidList);
                layer.open({
                    title: ['产权批量修改'],
                    type: 2,
                    area: ['100%', '100%'],
                    resize: true,
                    content: getContextPath() + '/view/xzxx/cqplxg.html?formStateId=' + formStateId + "&xmid=" + cqxmidList[0]
                });
            }
        })

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