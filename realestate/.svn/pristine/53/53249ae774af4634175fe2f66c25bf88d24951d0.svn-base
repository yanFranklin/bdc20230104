//调用控件方法如下，具体返回值参考/lib/idCard/readIdCard.html
function readIdCard(){
    try {
        return new ActiveXObject("GtMap.GxFrameActiveX.IDCard.IDCard");
    } catch(objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}

function trimStr(str){
    if(str !== null && str !== undefined){
        return str.replace(/(^\s*)|(\s*$)/g,"");
    }
}

function ReadIDCardNewSqr(elem) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var jquery = layui.jquery,
                    layer = layui.layer,
                    element = layui.element,
                    form = layui.form,
                    table = layui.table,
                    laytpl = layui.laytpl;

                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var xb = objTest.Sex;

                var forms = elem.parentNode.parentNode.parentNode;
                $(forms).find("input[name='mc']").val(trimStr(pName));
                $(forms).find("select[name='zjzl']").val("1");
                $(forms).find("input[name='zjh']").val($.trim(pCardNo));
                if(isNotBlank(xb)){
                    if(xb.indexOf("男") >-1){
                        xb ="1";
                    }else if(xb.indexOf("女") >-1){
                        xb ="2";
                    }else{
                        xb ="3";
                    }
                    $(forms).find("input[name='xb']").val(trimStr(xb));
                }
                form.render();
            });
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}