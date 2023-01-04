layui.use(['jquery', 'form', 'laytpl', 'element', 'table'], function () {
    var $ = layui.jquery, laytpl = layui.laytpl;
    var DEFAULT_URL = '';

    $(function () {
        var gzlslid = getQueryString("processInsId");
        var zsid = getQueryString("zsid");
        $.ajax({
            url: '/realestate-register-ui/rest/v1.0/dzzz/pdf?gzlslid=' + gzlslid + "&zsid=" + zsid,
            type: "GET",
            dataType: 'json',
            success: function (res) {
                if (res && !isNullOrEmpty(res.pdfFilePath)) {
                    if (!!window.ActiveXObject || "ActiveXObject" in window){
                        // 统一走PDF预览功能页面
                        var pdfUrl = '/realestate-register-ui/office/download?fileName=' + res.pdfFilePath;
                        window.location.href('/realestate-register-ui/static/lib/pdf/web/viewer.html?file=' + encodeURIComponent(pdfUrl));
                    }else{
                        var src = "data:application/pdf;base64," + res.data;
                        document.getElementById('printPdf').src = src;
                    }
                    saveZszmPrintInfo("zs", null, zsid);
                } else {
                    if(res && !isNullOrEmpty(res.msg)) {
                        showAlertDialog(res.msg);
                    } else {
                        failMsg("签章获取失败，请联系管理员或使用手工印章");
                    }
                }
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
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

    // 电子签章， 记录证书打印量
    function saveZszmPrintInfo(zslx, gzlslid, zsid) {
        var zslxcode;
        if ("zs" == zslx  || "zs1" == zslx || "zs2" == zslx || "zs3" == zslx
            || "tdcbjyqzs" == zslx || "tdcbjyqzs1" == zslx || "tdcbjyqzs2" == zslx || "tdcbjyqzs3" == zslx) {
            zslxcode = 1;
        } else if ("zm" == zslx) {
            zslxcode = 2;
        } else if ("sczmd" == zslx) {
            zslxcode = 3;
        }

        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/log/zszm/print",
            type: "POST",
            data: JSON.stringify({"zslx": zslxcode, "gzlslid": gzlslid, "zsid": zsid}),
            contentType: 'application/json',
            success: function (data) {
            }
        });
    }
})
