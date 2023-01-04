
/**
 * 保存打印信息
 * @param modelUrl 模板路径
 * @param dataSourceUrl 模板数据源
 * @param privateAttrMap 私有信息
 */
function savePrintInfo(modelUrl,dataSourceUrl,privateAttrMap, xmlStr){
    // 为了保存数据的准确性，保存的时候就即时查询出打印的xml了，保存至大云
    var xmlStr = xmlStr;
    if (isNullOrEmpty(xmlStr)) {
        xmlStr = getXmlStr(dataSourceUrl);
    }

    $.ajax({
        url: '/realestate-inquiry-ui/log/savePrintInfo',
        dataType: "json",
        type:"POST",
        data: {'modelUrl':modelUrl,'dataUrl':dataSourceUrl,'xmlStr':xmlStr,'privateAttrMap':privateAttrMap},
        success: function (data) {
        }
    });
}

/**
 * 请求保存的xml
 * @param dataSourceUrl
 * @returns {string}
 */
function getXmlStr(dataSourceUrl){
    var str = "";
    $.ajax({
        url: dataSourceUrl,
        async:false,
        success: function (data) {
            str = data;
        }
    });
    return str;
}
