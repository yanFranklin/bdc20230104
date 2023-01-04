/**
 * 不动产证书信息自定义查询 js
 * 用于锁定台账中，锁定不动产证书时查询证书信息
 */
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
function glzs(){
    if (!checkeddata || checkeddata.length == 0) {
        layer.alert("请选择需要关联的证书！", {title: '提示'});
        return;
    }

    if (checkeddata.length > 1) {
        layer.alert("请只选择一条证书！", {title: '提示'});
        return;
    }
    var data = checkeddata[0];
    parent.toNextZsGlPage(data);
}