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