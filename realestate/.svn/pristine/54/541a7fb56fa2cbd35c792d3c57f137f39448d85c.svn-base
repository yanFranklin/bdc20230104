layui.use(['jquery', 'form', 'laytpl', 'element', 'table'], function () {
    var $ = layui.jquery, laytpl = layui.laytpl;
    var DEFAULT_URL = '';

    $(function () {
        var bdcdyh = getQueryString("bdcdyh");
        var qllx = getQueryString("qllx");
        var moduleCode = getQueryString("moduleCode");
        $.ajax({
            url: '/realestate-register-ui/rest/v1.0/djbpdf/djbPdf?bdcdyh=' + bdcdyh + '&qllx=' + qllx + '&moduleCode=' + moduleCode,
            type: "GET",
            success: function (data) {
                if (data) {
                    if (!!window.ActiveXObject || "ActiveXObject" in window){
                        sessionStorage.setItem("pdfData",data);
                        window.location.href("/realestate-register-ui/static/js/djbxx/web/viewer.html");
                    }else{
                        var src = "data:application/pdf;base64," + data;
                        document.getElementById('printPdf').src = src;
                    }
                } else {
                    failMsg("pdf预览失败！");
                }
            },
            error: function () {
                failMsg("pdf预览失败！");
            }
        })
    })

    //获取URL参数
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (null !== r) {
            if (decodeURI(r[2]) !== "null") {
                return decodeURI(r[2]);
            } else {
                return null
            }
        }
        return null;
    }
})
