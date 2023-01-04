/**
 * 综合查询公共js
 * @type {string}
 */
var dyaCfFr3Url = getIP() + "/realestate-inquiry-ui/static/printModel/bdcDyaCfZm.fr3";
var zfxxFr3Url = getIP() + "/realestate-inquiry-ui/static/printModel/bdcZfxx.fr3";
var zfxxDaUrl = getIP() + "/realestate-inquiry-ui/static/printModel/bdcZfxxDa.fr3";
var cxsqsFr3Url = getIP() + "/realestate-inquiry-ui/static/printModel/bdcCxsqs.fr3";

/**
 * 打印查询受理申请书
 * @param yjdid
 * @returns {boolean}
 */
function cxsqs(sqsid) {
    if (sqsid == null || sqsid == '') {
        warnMsg("没有生成查询受理编号！");
        return false;
    }
    print(cxsqsFr3Url, getIP() + "/realestate-inquiry-ui/rest/v1.0/print/cxsqs/" + sqsid + "/xml", false);
}