/*
* 楼盘表页面打印功能
* */
var qjqszmModelUrl = getIP() + "/static/printmodel/qjqszm.fr3";
var fwqszmModelUrl = getIP() + "/static/printmodel/qjqszm.fr3";
var sessionKey = "djdcbdy"

function printLpbDjbcx(dylx, bdcdyh, modelUrl) {
    if (!isNotBlank(bdcdyh)) {
        ityzl_SHOW_INFO_LAYER("单元号异常，请到综合查询打印权属证明");
    } else {
        var appName = "building-ui";
        var dataUrl = getIP() + "/print/lpb/djb/" + dylx + "/" + bdcdyh + "/xml";
        // 同一个展示模板，数据源不同
        printChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);
    }
}

function printData(dylx) {
    if (!isNotBlank(bdcdyh)) {
        ityzl_SHOW_INFO_LAYER("单元号异常");
        return false;
    }
    var modelUrl = getIP() + "/static/printmodel/" + dylx + ".fr3";
    var dylxArr = [];
    dylxArr.push(dylx)
    setDypzSession(dylxArr, sessionKey);
    var appName = "building-ui";
    var dataUrl = getIP() + "/print/djdcb?dylx=" + dylx + "&qjgldm=" + qjgldm + "&bdcdyh=" + bdcdyh;
    printChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);
}