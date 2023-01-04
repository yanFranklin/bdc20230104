var isOneWebSource = $.getUrlParam('isOneWebSource');
layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl'], function() {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laytpl = layui.laytpl;
});

/**
 *
 * @param obj
 * @param data
 */
function ck(obj, data){
    if(!isNullOrEmpty(data) && '1' == data.XMLY){
        window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + data.GZLSLID);
    } else {
        window.open('/realestate-register-ui/view/lsgx/new-page.html?xmid=' + data.XMID + '&processInsId=' + data.GZLSLID);
    }
}
/**
 *
 * @param obj
 * @param data
 */
function djls(obj, data){
    window.open('/realestate-register-ui/view/lsgx/bdcdyDjLsgx.html?bdcdyh=' + data.BDCDYH);
}



