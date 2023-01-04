/*
* 接入业务校验js
* */
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
var form;
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form;

    $(function () {
        // 流程名称
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/process/gzldymcs",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            async: false,
            success: function (data) {
                $.each(data, function (index, item) {
                    $('#lcmc').append(new Option(item.name, item.name));// 下拉菜单里添加元素
                });
                form.render("select");
            }, error: function (e) {
                delAjaxErrorMsg(e, "加载失败");
            }
        });

    });

});

function xq(obj, data) {
    window.open("/realestate-inquiry-ui/view/config/lcpz/step13.html?gzldyid=" + data.GZLDYID + "&djxl=" + data.DJXL + "&qllx=" + data.QLLX);
}