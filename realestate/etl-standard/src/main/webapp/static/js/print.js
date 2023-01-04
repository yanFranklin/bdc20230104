//打印操作js文件
var spfwqhtModel = getIP() + "/realestate-etl/static/printmodel/spfwqht.fr3";
var dypzMap;
function printSpfWqht(dylx) {
    var dylxArr = [dylx];
    //2. 把当前打印类型放到session

    // 当前页面打印配置信息
    setDypzSession(dylxArr,null);

    var dataUrl = getIP() + "/realestate-etl/rest/v1.0/print/spf/" + dylx + "/" + processInsId + "/xml";
    // 同一个展示模板，数据源不同
    printChoice(dylx, 'etl-app', dataUrl, spfwqhtModel, false, dylx);
}


/**
 *
 * @param dylx 打印类型
 * @param dataUrl 获取数据源地址
 * @param hiddeMode fr3打印是否隐藏控件打印预览
 */
function printChoice(dylx, appName, dataUrl, modelUrl, hiddeMode, sessionKey) {
    console.log("打印配置参数", dypzMap);
    console.log("xml数据源", dataUrl);
    if (dypzMap && !isEmptyObject(dypzMap) && dypzMap[dylx]) {
        var bdcDysjPzDO = dypzMap[dylx];
        var fr3Path = bdcDysjPzDO.fr3path;
        var pdfPath = bdcDysjPzDO.pdfpath;
        // 如果fr3打印路径配置了，则优先选择fr3打印
        if (fr3Path) {
            print(fr3Path, dataUrl, hiddeMode);
            return;
        } else if (pdfPath) {
            //设置pdf打印参数
            var bdcPdfDyQO = {};
            bdcPdfDyQO.appName = appName;
            bdcPdfDyQO.dataUrl = dataUrl;
            bdcPdfDyQO.pdfpath = pdfPath;
            bdcPdfDyQO.fileName = sessionKey;
            ajaxPostPdfPrint(bdcPdfDyQO);
            return;
        }
    }
    // 兼容之前的打印配置
    print(modelUrl, dataUrl, hiddeMode);
}

/**
 * post请求打印pdf
 * @param bdcPdfDyQO pdf打印参数
 */
function ajaxPostPdfPrint(bdcPdfDyQO) {
    $.ajax({
        type: "POST",
        url: getContextPath()+"/rest/v1.0/dypz/common/etl/pdf/param/redis",
        contentType: 'application/json',
        data: JSON.stringify(bdcPdfDyQO),
        success: function (data) {
            if (data) {
                var pdfUrl =  getContextPath()+"/rest/v1.0/dypz/common/etl/pdf/" + data;
                window.open(getContextPath() + '/static/lib/pdf/web/viewer.html?file=' + encodeURIComponent(pdfUrl), "PDF");
            }
        }, error: function (e) {
            warnMsg("pdf生成异常！")
        }
    });
}

/**
 * 打印js
 * @returns {*}
 */
function print(modelUrl, dataUrl, hiddeMode) {
    var fr3Url = "v2|designMode=false|frURL=" + modelUrl
        + "|dataURL=" + dataUrl
        + "|updateUrl=http://oa.gtis.com.cn:80/platform/pluging/update.ini|hiddeMode=" + hiddeMode;

    window.location.href = "eprt://" + fr3Url;
}

function setDypzSession(dylxArr, sessionKey) {
    // 当前页面打印配置信息
    $.ajax({
        type: "POST",
        url:  getContextPath()+"/rest/v1.0/dypz/common/etl/pzxx",
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify(dylxArr),
        async: false,
        success: function (data) {
            dypzMap = data;
        }, error: function (e) {
            delAjaxErrorMsg(e);
        }
    });
}