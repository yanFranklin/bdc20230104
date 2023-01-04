var state = "";

/**
 * 注销申请
 */
function zx(obj, data) {
    state = "";
    var index = layer.open({
        type: 2,
        title: "产权信息",
        area: ['960px','400px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: "/realestate-inquiry-ui/yancheng/zq/zxsq.html?xmid=" + data.XMID,
        success: function () {
        },
        end: function(){
            if(!isNullOrEmpty(state)) {
                tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
            }
        }
    });
}

function setStateOfSave(stateInfo) {
    state = stateInfo;
}

//打开附件上传
function openSjcl(url, title, xmid) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true
    });
    layer.full(index);
}

// (土地)权属证明打印模板
var tdQszmDymb = getIP() + "/realestate-inquiry-ui/static/printModel/tdqszm.fr3";
// (房屋)权属证明打印模板
var fwQszmDymb = getIP() + "/realestate-inquiry-ui/static/printModel/fwqszm.fr3";

// 打印权属证明 （土地和房屋合成一个按钮，根据不动产类型调用对应的模板、数据源）
// 逻辑和综合查询一致
function qszm(obj, data) {
    // 进行规则验证
    checkApplyRegistration(data, "ZHCX_QSZM_ZHGZ", 'QSZM').fail(function () {
        removeModal();
        return;
    }).done(function () {
        // 格式示例：[{"bdcdyh":"1","qszt":"1","gzlslid":"1"},{"bdcdyh":"1","qszt":"1","gzlslid":"1"}]
        var paramList = new Array();
        var param = new Object();
        if (data.QSZT != 2) {//非历史状态，一律只查现势的信息
            param.qszt = 1;
        } else {
            param.qszt = data.QSZT;
        }
        param.bdcdyh = data.BDCDYH;
        param.gzlslid = data.GZLSLID;
        param.bdcqzh = data.BDCQZH;
        paramList.push(param);

        // 缓存要打印的权属证明参数信息
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zhcx/bzb/qszm",
            type: "POST",
            data: JSON.stringify(paramList),
            contentType: 'application/json',
            dataType: 'json',
            success: function (res) {
                if (res && res.redisKey) {
                    // 根据土地还是房屋类型选择对应的打印模板
                    var mburl = data.BDCLX == "1" ? tdQszmDymb : fwQszmDymb;
                    var dylx = data.BDCLX == "1" ? "tdqszm" : "fwqszm";

                    // 执行打印
                    var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/zhcx/bzb/qszm/" + res.redisKey + "/" + data.BDCLX + "/xml";
                    var appName = "realestate-inquiry-ui";
                    printChoice(dylx, appName, dataUrl, mburl, false, "qszm");

                    // 保存日志
                    savePrintInfo(mburl, dataUrl, {'zmbh': res.cxh, "printType": "权属证明"});
                } else {
                    failMsg("房产档案打印出错，请重试！");
                }
            },
            error: function () {
                failMsg("房产档案打印出错，请重试！");
            },
            complete: function () {
                removeModal();
            }
        });
    });
}

// 出证明时候进行相关验证
// yzbs 为了规则验证时候标识，比如验证信息补录抵押，房产证明不要验证，权属要验证
function checkApplyRegistration(data, zhbs, yzbs) {
    if (isNullOrEmpty(zhbs)) {
        warnMsg("未配置验证规则，请联系管理员！");
    }

    addModel();
    var deferred = $.Deferred();
    if ($.isEmptyObject(data)) {
        return deferred.resolve();
    }
    var bdcGzYzsjDTOList = new Array();
    bdcGzYzsjDTOList.push({
        'xmid': data.XMID,
        'bdcdyh': data.BDCDYH,
        'djh': yzbs
    });
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zhcx/dyzm/gzyz/" + zhbs,
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            bdcGzYzsjDTOList: bdcGzYzsjDTOList
        }),
        success: function (data) {
            if (data.length == 0) {
                return deferred.resolve();
            }
            var content = new Array();
            var containsWarning = false;
            $.each(data, function (i, value) {
                var xmid = getXmidOfGzyzxx(value);
                var action = "";
                if (!isNullOrEmpty(xmid)) {
                    action = "查看";
                }
                // 获取规则中是否存在不可忽略的规则，存在则无法进行打印
                if (isNotBlank(value.yxj) && 3 == value.yxj) {
                    containsWarning = true;
                }
                content.push("<img src=\"../../static/lib/bdcui/images/warn.png\" style='width: 16px;' alt=\"\"> ");
                content.push(value.tsxx);
                content.push('&nbsp;&nbsp;<a style=" color: blue;text-decoration: none;" href="javascrit:;" onclick="openXmxx(\'' + xmid + '\');return false">' + action + '</a>');
                content.push("<br/>");
            });
            if (containsWarning) {
                layer.open({
                    title: '信息',
                    area: '530px',
                    btn: [],
                    content: content.join("")
                });
            } else {
                layer.confirm(content.join(""), {
                    btn: ["继续打印", "否"],
                    area: '530px'
                }, function (index, layero) {
                    deferred.resolve();
                    layer.close(index);
                }, function (index) {
                    deferred.reject();
                    layer.close(index);
                });
            }
        },
        error: function (xhr, status, error) {
            failMsg("系统验证发生异常，请重试或联系管理员！");
        },
        complete: function () {
            removeModal();
        }
    });
    return deferred;
}

// 从规则验证结果获取XMID信息
function getXmidOfGzyzxx(data) {
    var xmid = "";
    if(data && data.xzxx){
        var xzxx = data.xzxx;
        $.each(xzxx, function (key, value) {
            if(key != "RULERESULT"){
                // 包含XMID、BDCDYH这些信息的验证结果，不同规则验证设置的数据量变量不一样，所以动态获取
                if(value && value[0] && value[0].XMID){
                    xmid = value[0].XMID;
                }
            }
        })
    }
    return xmid;
}

//查看项目信息
function openXmxx(xmid) {
    $.ajax({
        url: "/realestate-inquiry-ui/history/queryBdcXmDoByXmid",
        type: 'GET',
        async: true,
        contentType: 'application/json;charset=utf-8',
        data: {xmid: xmid},
        success: function (data) {
            if(isNotBlank(data) && isNotBlank(data[0])){
                window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + data[0].gzlslid);
            }else{
                warnMsg(" 请求失败，未获取到项目信息！");
            }

        },
        error: function (err) {
            removeModal();
            layer.closeAll();
            warnMsg(" 请求失败，请检查数据连接！");
        }
    });
}

/**
 * 指定区域不动产信息汇总统计功能
 */
function zdqybdcxxhz(obj, data) {
    if ($.isEmptyObject(currentTablePageData)) {
        warnMsg("未查询到产权数据！");
        return;
    }

    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: "指定区域不动产信息",
        type: 2,
        content: "/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=zqxxhz",
        area: [width, height],
        maxmin: true,
        success: function () {
            layui.data('zqxxhz', {key:"data", value: JSON.stringify(queryObject)});
        },
    });
    layer.full(index);
}