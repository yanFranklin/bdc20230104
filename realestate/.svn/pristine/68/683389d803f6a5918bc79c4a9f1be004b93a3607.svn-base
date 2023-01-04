/**
 * 权利人房查 js
 */
var table;
layui.use([ 'table'], function () {
    table = layui.table;
});
var qlrxx = getUrlParam("qlrxx");
var cxdh = getUrlParam("cxdh");
// 权属证明
var ZHCX_QSZM_ZHGZ = "ZHCX_QSZM_ZHGZ";
// (土地)权属证明打印模板
var tdQszmDymb = getIP() + "/realestate-inquiry-ui/static/printModel/tdqszm.fr3";
// (房屋)权属证明打印模板
var fwQszmDymb = getIP() + "/realestate-inquiry-ui/static/printModel/fwqszm.fr3";

$(".bdc-search-box").hide();
$(".bdc-percentage-container").css("padding-top", "0");

/**
 * 将父页面的查询条件传递进来进行查询
 */
var queryParam= {};
var sqlModel = "select distinct xmid from bdc_qlr where qlrmc=#{qlrmc} and zjh=#{zjh} and qlrlb=1";

// 表格加载完成后，执行的回调方法
var loadComplete = function loadComplete() {
    var obj = table.cache.pageTable;
    // 获取不动产单元号限制状态
    var bdcdyhList = [];
    var bdcdyhXzztMap ={};
    $.each(obj, function(index, value){
        if(isNotBlank(value.XZZT)){
            bdcdyhList.push({
                bdcdyh: value.BDCDYH,
                qjgldm: value.QJGLDM
            });
        }
    });
    if(bdcdyhList.length > 0){
        queryBdcdyhXzzt(bdcdyhList).done(function(data){
            console.info(data);
            if(data.length > 0){
                $.each(data, function(index, value){
                    bdcdyhXzztMap[value.bdcdyh] = value;
                });
            }
        });
    }

    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        if ($(getTd[i]).attr('data-field') == "QSZT") {
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            var key = getTdCell[0].innerText;
            var value = formatQszt(key);
            $(getTd[i]).children('div').empty();
            if (isNotBlank(value)) {
                $(getTd[i]).children('div').append(value);
            }
        }
        if($(getTd[i]).attr('data-field') == "XZZT"){
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            var key = getTdCell[0].innerText;
            var value = formatXzzt(bdcdyhXzztMap[key]);
            $(getTd[i]).children('div').empty();
            if (isNotBlank(value)) {
                $(getTd[i]).children('div').append(value);
            }
        }
    }
}

setTimeout(function () {
    if(qlrxx) {
        var sqlList = generateSql(qlrxx);
        if(sqlList.length >0 ){
            var sql = sqlList.join(" UNION ALL ");
            console.info(sql);
            queryParam.executeSql = Base64.encode(sql);
            $.ajax({
                url: "/realestate-inquiry-ui/dtcx/get/"+cxdh,
                type: "get",
                success: function (data) {
                    if(!data || isNullOrEmpty(data.cxid)) {
                        warnMsg("未配置qlrfc自定义查询，请联系管理员");
                    } else {
                        queryParam.cxdh = data.cxdh;
                        queryParam.cxid = data.cxid;
                        tableReload('pageTable', {data: JSON.stringify(queryParam)}, dataUrl, loadComplete);
                    }
                }
            });
        }
    }
}, 100);

/**
 * 档案调用
 */
function dady(obj, data) {
    console.info(data);
    if(!isNotBlank(data)){
        warnMsg("为获取到项目信息");
    }
    var url = "";
    if(data.XMLY == 1){
        url = "/realestate-portal-ui/view/popup-img.html?processinsid=" + data.GZLSLID;
    }else{
        url = "/realestate-etl/view/wwsq/popup-img.html?processinsid=" + data.GZLSLID;
    }
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: "附件信息",
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true
    });
    layer.full(index);
}
// 查询不动产单元限制状态
function queryBdcdyhXzzt(bdcdyhList){
    var deferred = $.Deferred();
    $.ajax({
        url:"/realestate-inquiry-ui/rest/v1.0/zszm/bdcdyh/xzzt",
        type: "POST",
        async: false,
        contentType: "application/json",
        data: JSON.stringify(bdcdyhList),
        success: function (data) {
            deferred.resolve(data);
        },
        error: function (xhr, status, error) {
            deferred.reject();
        }
    });
    return deferred;
}

// 格式化权属状态
function formatQszt(qszt){
    if (qszt == 0) {
        return '<span class="" style="color:#1d87d1;">临时</span>';
    } else if (qszt == 1){
        return '<span class="" style="color:#32b032;">现势</span>'
    } else if (qszt == 2) {
        return '<span class="" style="color:#f28943;">历史</span>'
    } else if (qszt == 3) {
        return '<span class="" style="color:#f24b43;">终止</span>'
    } else {
        return '';
    }
}

// 格式化限制状态
function formatXzzt(data) {
    var value = '';
    if (data != undefined) {
        if (data.cf === true) {
            value += '<span class="" style="color:#EE0000;">查封 </span>';
        }
        if (data.ycf === true) {
            value += '<span class="" style="color:#ee717a;">预查封 </span>';
        }
        if (data.dya === true || data.zjgcdy === true) {
            value += '<span class="" style="color:#FF00FF;">抵押 </span>';
        }
        if (data.ydya === true) {
            value += '<span class="" style="color:#aacd06;">预抵押 </span>';
        }
        if (data.yy === true) {
            value += '<span class="" style="color:#ef7106;">异议 </span>';
        }
        if (data.yg === true) {
            value += '<span class="" style="color:#ffb60c;">预告 </span>';
        }
        if (data.dyi === true) {
            value += '<span class="" style="color:#855e6e;">地役 </span>';
        }
        if (data.sd === true) {
            value += '<span class="" style="color:#e50971;">锁定 </span>';
        }
        if (data.jzq === true) {
            value += '<span class="" style="color:#13b1c4;">居住 </span>';
        }
        if (value === '') {
            value += '<span class="" style="color:#32b032;">正常</span>';
        }
    }
    return value;
}

function qszm(){
    var qlrData = JSON.parse(qlrxx);
    var qlrmc;
    if(isNotBlank(qlrData) && qlrData.length > 0){
        qlrmc = qlrData[0].qlrmc;
    }

    var checkStatus = table.checkStatus('pageTable');
    var checkedData = checkStatus.data;
    if ($.isEmptyObject(checkedData)) {
        warnMsg(" 请选择需要打印的记录！");
        return;
    }
    // 判断是否包含临时状态
    var isLszt = false;
    // 判断选择的不动产类型是否一致
    var isSameBdclx = true, bdclx;
    $.each(checkedData, function (key, value) {
        bdclx = value.BDCLX;
        return false;
    });

    $.each(checkedData, function (key, value) {
        if (0 == value.QSZT) {
            isLszt = true;
        }

        if (value.BDCLX != bdclx) {
            isSameBdclx = false;
        }
    });

    if (isLszt == true) {
        warnMsg("该权证为临时状态，无法出具相关证明！");
        return;
    }
    if (isSameBdclx == false) {
        warnMsg("请选择同一种不动产类型记录出具证明！");
        return;
    }

    // 进行规则验证
    checkApplyRegistration(checkedData, ZHCX_QSZM_ZHGZ, 'QSZM').fail(function () {
        removeModal();
        return;
    }).done(function () {
        // 格式示例：[{"bdcdyh":"1","qszt":"1","gzlslid":"1"},{"bdcdyh":"1","qszt":"1","gzlslid":"1"}]
        var paramList = new Array();
        $.each(checkedData, function (key, value) {
            var param = new Object();
            if (value.QSZT != 2) {//非历史状态，一律只查现势的信息
                param.qszt = 1;
            } else {
                param.qszt = value.QSZT;
            }
            param.bdcdyh = value.BDCDYH;
            param.gzlslid = value.GZLSLID;
            param.bdcqzh = value.BDCQZH;
            param.xmid = value.XMID;
            param.bdclx = value.BDCLX;
            paramList.push(param);
        });
        // 缓存要打印的权属证明参数信息
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zhcx/bzb/qszm",
            type: "POST",
            data: JSON.stringify(paramList),
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                if (data && data.redisKey) {
                    // 根据土地还是房屋类型选择对应的打印模板
                    var mburl = bdclx == "1" ? tdQszmDymb : fwQszmDymb;
                    var dylx = bdclx == "1" ? "tdqszm" : "fwqszm";

                    // 执行打印
                    var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/zhcx/bzb/qszm/" + data.redisKey + "/" + bdclx + "/xml";
                    var appName = "realestate-inquiry-ui";

                    printChoice(dylx, appName, dataUrl, mburl, false, "cqxxcx");

                    // 保存日志
                    savePrintInfo(mburl, dataUrl, {'zmbh': data.cxh, "printType": "权属证明", "zl": "", "qlr": qlrmc});
                } else {
                    failMsg("房产档案打印出错，请重试！");
                }
            },
            error: function () {
                failMsg("房产档案打印出错，请重试！");
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
    var deferred = $.Deferred();
    if ($.isEmptyObject(data)) {
        return deferred.resolve();
    }
    var bdcGzYzsjDTOList = new Array();

    $.each(data, function (index, value) {
        // 这里参数 djh 只是为了匹配现有的参数实体属性，本质上是为了起到标识综合查询作用
        bdcGzYzsjDTOList.push({
            xmid: value.XMID,
            bdcdyh: value.BDCDYH,
            djh: yzbs
        });
    });
    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zhcx/dyzm/gzyz/" + zhbs,
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            bdcGzYzsjDTOList: bdcGzYzsjDTOList
        }),
        success: function (data) {
            removeModal();
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
                content.push('&nbsp;&nbsp;<a class="viewbdcxm" href="javascrit:;" onclick="openXmxx(\'' + xmid + '\');return false">' + action + '</a>');
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
            deferred.reject();
            removeModal();
            failMsg("系统验证发生异常，请重试或联系管理员！");
        }
    });
    return deferred;
}

// 从规则验证结果获取XMID信息
function getXmidOfGzyzxx(data) {
    var xmid = "";
    if (data && data.xzxx) {
        var xzxx = data.xzxx;
        $.each(xzxx, function (key, value) {
            if (key != "RULERESULT") {
                // 包含XMID、BDCDYH这些信息的验证结果，不同规则验证设置的数据量变量不一样，所以动态获取
                if (value && value[0] && value[0].XMID) {
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
            if (isNotBlank(data) && isNotBlank(data[0])) {
                window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + data[0].gzlslid);
            } else {
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

function generateSql(qlr){
    var qlrData = JSON.parse(qlr);
    var sqlList = [];
    $.each(qlrData, function(index, value){
        if(isNotBlank(value.qlrmc) && isNotBlank(value.zjh)){
                if(value.zjh.length == 18){
                    var zjh15 = convertIdCard18To15(value.zjh);
                    var zjhSql = "'" + value.zjh + "','"+ zjh15 +"'";
                    sqlList.push(sqlModel.replace("#{qlrmc}", "'"+value.qlrmc+"'").replace("zjh=#{zjh}",
                        "zjh in ( "+ zjhSql +" )"));
                    // 查询 zjh长度不为 18 和 15的数据
                    sqlList.push("select distinct xmid from bdc_qlr where qlrmc="+"'"+value.qlrmc+ "'"+" and qlrlb=1 and length(zjh)!=15 and length(zjh)!=18");
                }else{
                    sqlList.push(sqlModel.replace("#{qlrmc}", "'"+value.qlrmc+"'").replace("#{zjh}", "'"+value.zjh+"'"));
                    sqlList.push("select distinct xmid from bdc_qlr where qlrmc="+"'"+value.qlrmc+ "'"+" and qlrlb=1 and length(zjh)!=15 and length(zjh)!=18");
                }
        }
    });
    return sqlList;
}
