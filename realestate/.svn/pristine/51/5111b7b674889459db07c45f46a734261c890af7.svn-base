// 有无土地信息证明证明模板
var ywtdxxzmFr3Url = getIP() + "/realestate-inquiry-ui/static/printModel/ywtdxxzm.fr3";
// var ywtdxxzmFr3Url = getIP() + "/realestate-inquiry-ui/static/printModel/ywtdxxzm.docx";
// var ywtdxxzmFr3Url = "/Users/wahaha-zhanshi/Desktop/sczmd.docx";

/**
 * 盐城综合查询：有无土地信息证明
 */
function ywtdxxzm(d, cols, searchObjData,jtcyData) {
    var qlrmc = $("#qlrmc").val();
    var qlrzjh = $("#qlrzjh").val();
    var zl;
    if (d.length == 0) {
        zl = $("#zl").val();
    }else{
        zl = d[0].zl;
    }

    if (isNullOrEmpty(qlrmc)) {
        warnMsg("请输入户主权利人名称！");
        return;
    }

    //拼接曾用名
    if(searchData &&!isNullOrEmpty(searchData.qlrcym)) {
        qlrmc = isNullOrEmpty(qlrmc) ? searchData.qlrcym : qlrmc + "," + searchData.qlrcym;
    }
    if(searchData &&!isNullOrEmpty(searchData.qlrcymzjh)) {
        qlrzjh = isNullOrEmpty(qlrzjh) ? searchData.qlrcymzjh : qlrzjh + "," + searchData.qlrcymzjh;
    }

    // 出具有房无房时，姓名必填,配置证件号是否必填
    if (1 == zhcxpz.zmzjhbt) {
        if (isNullOrEmpty(qlrmc) || isNullOrEmpty(qlrzjh)) {
            warnMsg(" 请输入权利人名称和证件号！");
            return;
        }
    }

    if (1 != zhcxpz.zmzjhbt && isNullOrEmpty(qlrmc)) {
        warnMsg(" 请输入权利人名称！");
        return;
    }

    if (!isNullOrEmpty(qlrzjh)) {
        // 权利人证件号不为空，需要判断和名称数量是否一致
        var qlrmcArray = qlrmc.split(",");
        var qlrzjhArray = qlrzjh.split(",");
        if (qlrmcArray.length > 0 && qlrzjhArray.length > 0 && qlrmcArray.length != qlrzjhArray.length) {
            warnMsg(" 已输入权利人名称、证件号数量应一致！");
            return;
        }
    }

    var jtcyList = [];
    if(jtcyData && jtcyData.length) {
        for(var i = 0; i < jtcyData.length; i++) {
            var jtcyObj = {};
            jtcyObj.jtcymc = jtcyData[i].jtcymc;
            if (!isNullOrEmpty(jtcyData[i].jtcycym)) {
                jtcyObj.jtcymc = isNullOrEmpty(jtcyObj.jtcymc) ? jtcyData[i].jtcycym : jtcyObj.jtcymc + "（" + jtcyData[i].jtcycym + "）";
            }
            jtcyObj.jtcyzjh = jtcyData[i].jtcyzjh;
            jtcyObj.yhzgx = jtcyData[i].yhzgx;
            jtcyList.push(jtcyObj);
        }
    }

    var param = {};
    param.qlrmc = qlrmc;
    param.zjh = qlrzjh;
    param.jtcyDOList = jtcyList;

    // 缓存打印参数数据
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zhcx/ywtdxxzm/param",
        type: "POST",
        data: JSON.stringify(param),
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            removeModal();
            if (data && data.redisKey) {
                // var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/yancheng/ywtdxxzm/" + data.redisKey + "/xml";
                var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/zszm/print/dacx/" + data.redisKey + "/ywtdxxzm/xml?bdclx=";
                // print(ywtdxxzmFr3Url, dataUrl, false);
                printForDacx('ywtdxxzm', null, "有无土地信息证明", data.redisKey, dataUrl, false, ywtdxxzmFr3Url, false);
                // var pdfUrl = getContextPath() + '/rest/v1.0/zszm/print/dacx/pdf?dylx=ywtdxxzm&fileName=有无土地信息证明&modelWordUrl=' + ywtdxxzmFr3Url +'&bdclx='+ bdclx + '&xmlKey=' + data.redisKey + '&dacxFlag=true';
                // window.open(getContextPath() + '/static/lib/pdf/web/viewer.html?file=' + encodeURIComponent(pdfUrl), "PDF");
                var xmlStr = getXmlStr(dataUrl); //获得打印内容 xml
                console.log(xmlStr);
                var printType = 3;
                saveZhcxLogToDataBase(searchObjData, xmlStr, 1, "ywtdxxzm");
                savePrintInfo(ywtdxxzmFr3Url, dataUrl, {'zmbh': data.cxbh, "printType": "有无土地信息证明","zl":zl, "qlr": qlrmc});
            } else {
                failMsg("有无土地信息证明打印出错，请重试！");
            }
        },
        error: function () {
            removeModal();
            failMsg("有无土地信息证明打印出错，请重试！");
        }
    });

}