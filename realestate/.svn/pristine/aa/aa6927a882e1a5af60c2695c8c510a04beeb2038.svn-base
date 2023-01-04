/**
 * 证书补打 js
 */
// 证书模板地址
var zsModelUrl = "C:/GTIS/zs.fr3";
// 证明模板地址
var zmModelUrl = "C:/GTIS/zm.fr3";
// 证明单模板地址
var zmdModelUrl = "C:/GTIS/zmd.fr3";
// 存储打印配置的sessionKey
var sessionKey = "bdcZsZm";
$(function () {
// 当前页的打印类型
    var dylxArr = ["zs", "zm", "sczmd"];
    setDypzSession(dylxArr, sessionKey);
});
/**
 * 补打
 */
function bd(obj, data) {
    if(!isNotBlank(data.GZLSLID) ||!isNotBlank(data.ZSID) ||!isNotBlank(data.ZSLX)){
        failMsg("工作流实例ID或证书ID或证书类型为空，无法打印！");
        return false;
    }
    //获取缴费状态
    addModel();
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsbd/sfzt',
        dataType: "json",
        type: "GET",
        async: false,
        data: {'gzlslid': data.GZLSLID},
        success: function (result) {
            removeModal();
            if(result !="2"){
                failMsg("未缴费,请缴费后再打印！");
                return false;
            }
            if(!isNotBlank(data.QZYSXLH)){
                layer.prompt({
                    formType: 0,
                    value: ' ',// 这是一个空格字符串，请不要删除
                    title: '请输入印制号',
                    area: ['500px', '350px'], //自定义文本域宽高
                    btn: ['确定','取消'],
                    cancel: function (index, layero) {
                        layer.close(index);
                    }
                }, function (value, index, elem) {
                    // 去除空格
                    var ysxlh = value.trim();
                    if (isNullOrEmpty(ysxlh)) {
                        warnMsg("印制号不能为空!");
                        return;
                    }
                    //校验是否可用
                    var bdcYzhQO = {};
                    bdcYzhQO["syqk"] = 6;
                    bdcYzhQO["zsid"] = data.ZSID;
                    // 验证qzysxlh是否可用
                    bdcYzhQO["qzysxlh"] = ysxlh.trim();
                    var result =checkYzhSyqk(bdcYzhQO);
                    if(result){
                        //打印
                        printZszm(data.ZSID,data.ZSLX);
                    }
                    layer.close(index);

                });
            }else{
                //打印
                printZszm(data.ZSID,data.ZSLX);
            }


        },
        error:function (xhr) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });

}

/**
 * 更新印制号使用情况
 */
function checkYzhSyqk(bdcYzhQO) {
    var result = false;
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zsbd/yzh/syqk",
        type: "POST",
        data: JSON.stringify(bdcYzhQO),
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            if (data && data.code) {
                warnMsg(data.msg);
            } else if (data && !isNullOrEmpty(data.yzxsjid)) {
                var bdcYzhSyqkQO = {};
                bdcYzhSyqkQO["zsid"] = bdcYzhQO.zsid;
                // 这里要求验证信息返回印制号的ID，方便印制号使用情况的更新
                bdcYzhSyqkQO["yzhid"] = data.yzxsjid;
                bdcYzhSyqkQO["qzysxlh"] = bdcYzhQO.qzysxlh;
                bdcYzhSyqkQO["syyy"] = "证书印制号获取";
                bdcYzhSyqkQO["syqk"] = 3;
                result = updateBdcYzhSyqk(bdcYzhSyqkQO);
            } else if (data) {
                warnMsg(data.tsxx);
            } else {
                warnMsg("权证印刷序列号验证不通过，请分析输入的印制号是否已被使用或当前用户没有权限获取该印制号！");
            }
        },
        error: function () {
            warnMsg("验证权证印刷序列号异常！");
        }
    });
    return result;
}

/**
 * 打印
 */
function printZszm(zsid,zslx){
    if(1== zslx){
        zsPrint(zsModelUrl, zsid, "zs",sessionKey);
    }else if(2== zslx){
        zsPrint(zmModelUrl, zsid, "zm",sessionKey);
    }else if(3== zslx){
        zsPrint(zmdModelUrl, zsid, "sczmd",sessionKey);
    }
}

/**
 * 单个证书打印
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

/**
 * 更新印制号使用情况
 */
function updateBdcYzhSyqk(bdcYzhSyqkQO) {
    var result = false;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/yzhsyqk",
        type: "POST",
        data: JSON.stringify(bdcYzhSyqkQO),
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            if (data && data.code) {
                warnMsg(data.msg);
            } else if (data) {
                successMsg("印制号更新成功！");
                result = true;
            } else {
                warnMsg("印制号更新异常！");
            }
        },
        error: function () {
            warnMsg("印制保存异常！");
        }
    });
    return result;
}

/**
 * 流程查看
 */
function lcck(obj, data) {
    if(!isNotBlank(data.GZLSLID)){
        failMsg("工作流实例ID为空，无法查看！");
        return false;
    }
    if (isNotBlank(data.XMLY) && data.XMLY == "1") {
        window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + data.GZLSLID);
    } else {
        window.open('/realestate-register-ui/view/lsgx/new-page.html?xmid=' + data.XMID + '&processInsId=' + data.GZLSLID +'&print=true&firstAside=zsxx');
    }

}

/**
 *  结果字段EMSDH增加颜色
 */
function loadComplete() {
    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        if ($(getTd[i]).attr('data-field') == "EMSDH") {
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            if (getTdCell[0].innerText != '') {
                var text = getTdCell[0].innerText;
                $(getTd[i]).children('div').empty();
                $(getTd[i]).children('div').append('<span class="bdc-emsdh">'+ text +'</span>');
            }
        }
    }
    changeTrBackgroundExceptRight([
        {name: 'bdc-emsdh', color: '#333'}, true]);
}
