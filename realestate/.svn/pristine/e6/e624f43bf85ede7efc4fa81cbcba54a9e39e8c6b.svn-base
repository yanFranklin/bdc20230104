/**
 * （需求57559）南通“购房奖补专用查询”查询台账自定义JS，对应自定义查询gfjbzycx
 */


// 存储打印配置的sessionKey，当前页的打印类型
var dylxArr = ["gfjbzyzm"];
var sessionKey = "gfjbzyzm";
setDypzSession(dylxArr, sessionKey);

/**
 * 打印证明
 * @param obj
 * @param data
 */
function dyzm(obj,data){
    var djsj = $("#djsj").val();
    var qlrmc = $("#qlrmc").val();
    var qlrzjh = $("#qlrzjh").val();

    if(isNullOrEmpty(djsj)) {
        warnMsg("请选择查询时间！");
        return;
    }

    if(isNullOrEmpty(qlrmc) || isNullOrEmpty(qlrzjh)) {
        warnMsg("请输入权利人名称、证件号！");
        return;
    }

    // 权利人证件号不为空，需要判断和名称数量是否一致
    var qlrmcArray = qlrmc.split(",");
    var qlrzjhArray = qlrzjh.split(",");
    if (qlrmcArray.length > 0 && qlrzjhArray.length > 0 && qlrmcArray.length != qlrzjhArray.length) {
        warnMsg(" 权利人名称、证件号数量应一致！");
        return;
    }

    var qlrxxList = new Array();
    var qlrmcArray = qlrmc.split(",");
    var qlrzjhArray = qlrzjh.split(",");
    if (qlrmcArray.length > 0) {
        for (var i = 0; i < qlrmcArray.length; i++) {
            if (!isNullOrEmpty(qlrmcArray[i])) {
                var qlrzjhItem = "";
                if (qlrzjhArray.length > 0) {
                    qlrzjhItem = qlrzjhArray[i];
                }
                var qlrxx = new Array();
                qlrxx.push(qlrmcArray[i]);
                qlrxx.push(qlrzjhItem);
                qlrxx.push(i);
                qlrxx.push(djsj);
                qlrxxList.push(qlrxx);
            }
        }
    }

    // 缓存打印参数数据
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/print/params",
        type: "POST",
        data: JSON.stringify(qlrxxList),
        contentType: 'application/json',
        success: function (dbkey) {
            if (!isNullOrEmpty(dbkey)) {
                // 执行打印
                var dylx = "gfjbzyzm";
                var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/data/" + dylx + "/" + dbkey + "/xml";
                var mburl = getIP() + "/realestate-register-ui/static/printModel/gfjbzyzm.fr3";
                var appName = "realestate-inquiry-ui";

                printChoice(dylx, appName, dataUrl, mburl, false, sessionKey);
                //保存日志打印留档信息
                setTimeout(function(){
                    var dyldCs = getDyldcs(dbkey);
                    var zmbh = dyldCs.zmbh;
                    savePrintInfo(mburl, dataUrl, {'zmbh': zmbh, "printType": "购房奖补专用查询", "zl":"", "qlr": qlrmc},dyldCs.cxjg);
                },3000);
            } else {
                failMsg("打印出错，请重试！");
            }
        },
        error: function () {
            failMsg("打印出错，请重试！");
        }
    });
}

function submitCheckParam(){
    var msg = "";
    var djsj = $("#djsj").val();
    if(isNullOrEmpty(djsj)){
        msg = "请选择查询时间";
    }
    return msg;
}

function getDyldcs(dbkey){
    var dyldCs;
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/print/getDyldcs?dbkey="+dbkey,
        type: "get",
        contentType: 'application/json',
        async: false,
        success: function (data) {
            if (!isNullOrEmpty(dbkey)) {
                dyldCs = data;
            } else {
                failMsg("获取打印留档参数错误，请重试！");
            }

        },
        error: function () {
            failMsg("获取打印留档参数错误，请重试！");
        }

    });
    return dyldCs;
}