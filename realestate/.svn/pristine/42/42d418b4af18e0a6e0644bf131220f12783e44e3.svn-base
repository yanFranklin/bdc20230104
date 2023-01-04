/**
 * 匹配台账js
 */
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;
});



//预览
function yl(obj, objdata) {
    var pdfUrl = document.location.protocol + "//" + document.location.host + "/realestate-inquiry-ui/rest/v1.0/gzcgz/gzs/pdf/" + objdata.GZSBH;
    if(pdfUrl.indexOf(".pdf")==-1){
        layer.alert("只能预览pdf文件");
        return;
    }
    window.open('/realestate-accept-ui/static/lib/pdf/web/viewer.html?file=' + pdfUrl,"PDFPREW");
}

//下载
function xz(obj, objdata) {
    var pdfUrl = document.location.protocol + "//" + document.location.host + "/realestate-inquiry-ui/rest/v1.0/gzcgz/gzs/pdf/download?gzsbh=" + objdata.GZSBH;
    window.open(pdfUrl,"PDFDOWNLOAD");
}
