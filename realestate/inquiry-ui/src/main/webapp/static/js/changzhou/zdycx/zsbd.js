/**
 * 常州：证书补打自定义查询操作
 */
// 土地承包经营权证书模板地址
var tdcbjyqzsModelUrl = "C:/GTIS/tdcbjyqzs.fr3";
var tdcbjyqzs1ModelUrl = "C:/GTIS/tdcbjyqzs1.fr3";
var tdcbjyqzs2ModelUrl = "C:/GTIS/tdcbjyqzs2.fr3";
var tdcbjyqzs3ModelUrl = "C:/GTIS/tdcbjyqzs3.fr3";
// 农村不动产确权登记证书模板地址
var ncbdcqqdjzsModelUrl = "C:/GTIS/ncbdcqqdjzs.fr3";
var ncbdcqqdjzsModelUrl1 = "C:/GTIS/ncbdcqqdjzs1.fr3";
var ncbdcqqdjzsModelUrl2 = "C:/GTIS/ncbdcqqdjzs2.fr3";
var ncbdcqqdjzsModelUrl3 = "C:/GTIS/ncbdcqqdjzs3.fr3";
// 证书模板地址
var zsModelUrl = "C:/GTIS/zs.fr3";
var zs1ModelUrl = "C:/GTIS/zs1.fr3";
var zs2ModelUrl = "C:/GTIS/zs2.fr3";
var zs3ModelUrl = "C:/GTIS/zs3.fr3";

// 常州土地承包经营权权利类型
var TDCBJYQ_QLLX = 9;
// 常州农村不动产确权登记权利类型
var NCBDCQQDJ_QLLX = 6;
var dylxArr = ["zs", "zsfb", "hst", "zdt", "jtcyfb", "zs1", "zs2", "zs3", "zs1and2", "tdcbjyqzs1and2", "tdcbjyqzs1", "tdcbjyqzs2", "tdcbjyqzs3", "tdcbjyqzs"];
var sessionKey = "bdcZm";
setDypzSession(dylxArr, sessionKey);

// 证书打印
function zsdy(obj, data){
    layer.open({
        title: '证书补打',
        type: 1,
        area: ['500px','250px'],
        btn: ['确定', '取消'],
        content: '<div id="bdc-popup-radio">\n' +
            '    <form class="layui-form" action="">\n' +
            '        <div class="layui-form-item pf-form-item">\n' +
            '            <div class="layui-inline">\n' +
            '                <label class="layui-form-label">打印：</label>\n' +
            '                <div class="layui-input-inline">\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </form>\n' +
            '</div>',
        success: function () {
            var form = layui.form;
            var radio = "";
            radio += "<input type=\"radio\" name=\"zslxRadio\" value=\"zs1\" title=\"第一页\" lay-filter=\"zslxRadio\">";
            radio += "<input type=\"radio\" name=\"zslxRadio\" value=\"zs2\" title=\"第二页\" lay-filter=\"zslxRadio\">";
            radio += "<input type=\"radio\" name=\"zslxRadio\" value=\"zs3\" title=\"第三页\" lay-filter=\"zslxRadio\">";
            radio += "<input type=\"radio\" name=\"zslxRadio\" value=\"zs1and2\" title=\"第一页和第二页\" lay-filter=\"zslxRadio\">";
            radio += "<input type=\"radio\" name=\"zslxRadio\" value=\"zsall\" title=\"全部\" lay-filter=\"zslxRadio\">";
            radio += "<input type=\"radio\" name=\"zslxRadio\" value=\"dzqz\" title=\"电子签章\" lay-filter=\"zslxRadio\">";
            $('#bdc-popup-radio').find(".layui-input-inline").html(radio);
            form.render("radio");
        },
        yes: function (index, layero) {
            var dylx = $("input[name='zslxRadio']:checked").val();
            var zsid = data.ZSID;
            var qllx = data.QLLX;
            if (dylx === "zs1") {
                printZs1(qllx, zsid);
            }
            if (dylx === "zs2") {
                printZs2(qllx, zsid);
            }
            if (dylx === "zs3") {
                printZs3(qllx, zsid);
            }
            if (dylx === "zs1and2") {
                printZs1and2(qllx, zsid);
            }
            if (dylx === "zsall") {
                printZs(qllx, zsid);
            }
            if (dylx === "dzqz"){
                printDzqz(zsid);
            }
            layer.close(index);
        },
        btn2: function (index, layero) {
            layer.close(index);
        } ,
        cancel: function (index, layero) {
            layer.close(index);
        }
    });
}

/**
 * 证书打印全部
 */
function printZs(qllx, zsid) {
    if (qllx == TDCBJYQ_QLLX) {
        zsPrint(tdcbjyqzsModelUrl, zsid, "tdcbjyqzs", sessionKey);
    }else if(qllx == NCBDCQQDJ_QLLX){
        zsPrint(ncbdcqqdjzsModelUrl, zsid, "ncbdcqqdjzs", sessionKey);
    }else {
        zsPrint(zsModelUrl, zsid, "zs", sessionKey);
    }
}
/**
 * 证书打印第一页
 */
function printZs1(qllx, zsid) {
    if (qllx == TDCBJYQ_QLLX) {
        zsPrint(tdcbjyqzs1ModelUrl, zsid, "tdcbjyqzs1", sessionKey);
    }else if(qllx == NCBDCQQDJ_QLLX){
        zsPrint(ncbdcqqdjzsModelUrl1, zsid, "ncbdcqqdjzs1", sessionKey);
    }else {
        zsPrint(zs1ModelUrl, zsid, "zs1", sessionKey);
    }
}
/**
 * 证书打印第二页
 */
function printZs2(qllx, zsid) {
    if (qllx == TDCBJYQ_QLLX) {
        zsPrint(tdcbjyqzs2ModelUrl, zsid, "tdcbjyqzs2", sessionKey);
    } else if(qllx == NCBDCQQDJ_QLLX){
        zsPrint(ncbdcqqdjzsModelUrl2, zsid, "ncbdcqqdjzs2", sessionKey);
    } else {
        zsPrint(zs2ModelUrl, zsid, "zs2", sessionKey);
    }
}
/**
 * 证书打印第三页
 */
function printZs3(qllx, zsid) {
    if (qllx == TDCBJYQ_QLLX) {
        zsPrint(tdcbjyqzs3ModelUrl, zsid, "tdcbjyqzs3", sessionKey);
    } else if (qllx == NCBDCQQDJ_QLLX) {
        zsPrint(ncbdcqqdjzsModelUrl3, zsid, "ncbdcqqdjzs3", sessionKey);
    } else {
        zsPrint(zs3ModelUrl, zsid, "zs3", sessionKey);
    }
}
/**
 * 第一页和第二页
 */
function printZs1and2(qllx, zsid) {
    if (qllx == TDCBJYQ_QLLX) {
        zsPrint(tdcbjyqzs3ModelUrl, zsid, "tdcbjyqzs1and2", sessionKey);
    } else if (qllx == NCBDCQQDJ_QLLX) {
        zsPrint(ncbdcqqdjzsModelUrl3, zsid, "ncbdcqqdjzs1and2", sessionKey);
    } else {
        zsPrint(zs3ModelUrl, zsid, "zs1and2", sessionKey);
    }
}

/**
 * 打印电子签章
 */
function printDzqz(zsid){
    window.open( "/realestate-register-ui/changzhou/dzzz/priviewDzzz.html?zsid=" + zsid);
    // 保存打印数量信息
    saveZszmPrintInfo("zs", null, zsid);
}

/**
 * 证书打印方法
 */
function zsPrint(modelUrl, zsid, zslx, sessionKey) {
    var appName = "realestate-inquiry-ui";
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zs/" + zsid + "/" + zslx + "/singleXml";
    printChoice(zslx, appName, dataUrl, modelUrl, false, sessionKey);
    // 保存打印数量信息
    saveZszmPrintInfo(zslx, null, zsid);
}

/**
 * 保存打印的证书、证明、证明单数量信息
 * @param zslx 证书类型
 * @param gzlslid 工作流实例ID
 * @param zsid 证书ID
 */
function saveZszmPrintInfo(zslx, gzlslid, zsid) {
    var zslxcode;
    if ("zs" == zslx) {
        zslxcode = 1;
    } else if ("zm" == zslx) {
        zslxcode = 2;
    } else if ("sczmd" == zslx) {
        zslxcode = 3;
    }

    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/log/zszm/print",
        type: "POST",
        data: JSON.stringify({"zslx": zslxcode, "gzlslid": gzlslid, "zsid": zsid}),
        contentType: 'application/json',
        success: function (data) {
        }
    });
}