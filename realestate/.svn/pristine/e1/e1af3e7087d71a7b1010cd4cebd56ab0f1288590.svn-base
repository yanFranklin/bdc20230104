/**
 * 征迁注销3：注销审核台账 、征迁注销4：审核通过申请台账 JS
 */

/**
 * 查看注销申请详细信息
 * @param obj
 * @param data
 */
function detailzxsq(obj,data){
    var index = layer.open({
        type: 2,
        title: "产权信息",
        area: ['960px','400px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: "/realestate-inquiry-ui/yancheng/zq/zxsq.html?state=view&xmid=" + data.XMID + "&sqxxid=" + data.SQXXID,
        success: function () {
        },
        end: function(){
        }
    });
}

/**
 * 查看附件
 * @param obj
 * @param data
 */
function fj(obj,data){
    var sqxxid = data.SQXXID;
    var bdcSlWjscDTO = queryBdcSlWjscDTO();
    bdcSlWjscDTO.spaceId = sqxxid;
    bdcSlWjscDTO.storageUrl =document.location.protocol + "//" + document.location.host + "/storage";
    var url = "/realestate-inquiry-ui/yancheng/zq/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
    openSjcl(url, "附件上传");
}

/**
 * 查询收件材料所需参数（打开附件上传使用）
 */
function queryBdcSlWjscDTO() {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/dwgz/getFileCs',
        type: 'GET',
        async: false,
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            if (isNotBlank(data)) {
                bdcSlWjscDTO = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return bdcSlWjscDTO;
}

/**
 * 打开附件上传
 */
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

/**
 * 提交审核
 */
function shenhe(obj,data){
    if(isNullOrEmpty(data.SHZT) || 1 == data.SHZT){
        warnMsg('注销申请未提交,不能审核！');
        return;
    }

    if(data.SHZT == 3){
        warnMsg('注销申请已审核,不能重复审核！');
        return;
    }

    if(data.SHZT == 4){
        warnMsg('注销申请已删除,不能审核！');
        return;
    }

    if(data.SHZT == 7){
        warnMsg('注销申请已退回,无法审核！');
        return;
    }

    var param = {
        "sqxxid" : data.SQXXID,
        "shzt": 3
    }
    console.log(param);
    layer.confirm("确定通过该注销申请？", {
        title: "提示",
        btn: ["确认", "取消"],
        btn1: function (index2) {
            layer.close(index2);
            // 先锁定不动产单元和证书，避免先审核通过但是没有锁定成功
            sdBdcdyZs(param, data);
        },
        btn2: function (index2) {
            layer.close(index2);
        }
    });
    return false;
}

/**
 * 锁定不动产单元和证书
 */
function sdBdcdyZs(param, data) {
    addModel();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/zq/zxsq/sd",
        type: 'POST',
        data: JSON.stringify({"bdcdyh": data.BDCDYH, "bdcqzh": data.BDCQZH,  "sqxxid" : data.SQXXID}),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            updateShzt(param);
        },
        error: function (xhr, status, error) {
            failMsg("锁定不动产单元、证书失败，请重试！");
        },
        complete: function () {
            removeModal();
        }
    });
}

/**
 * 更新审核状态为审核通过
 * @param param
 */
function updateShzt(param) {
    $.ajax({
        type: 'POST',
        url: getContextPath() + "/rest/v1.0/zq/zxsq/shzt/update",
        data: JSON.stringify(param),
        dataType: "json",
        contentType: "application/json",
        success: function (res) {
            layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">审核成功!');
            setTimeout(function(){parent.layer.closeAll()},1000);
            tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
            removeModal();
        }
    });
}

var alertSize = 0, confirmSize = 0;
var yztsxxLayer;

/**
 * 创建注销流程
 */
function cjlc(obj,data) {
    if(data && !isNullOrEmpty(data.ZXLCSLBH)) {
        warnMsg("注销流程已创建，无需重复创建！");
        return;
    }

    if(!data || isNullOrEmpty(data.XMID) || isNullOrEmpty(data.BDCDYH)) {
        warnMsg("未查找到项目信息，无法创建！");
        return;
    }

    alertSize = 0, confirmSize = 0;
    var dataParam = {"xmid": data.XMID, "bdcdyh": data.BDCDYH, "bdcqzh": data.BDCQZH};

    addModel();
    // 先进行规则验证
    $.ajax({
        url: getContextPath() + "/rest/v1.0/zq/zxsq/gzyz",
        type: 'POST',
        data: JSON.stringify(dataParam),
        dataType: "json",
        contentType: "application/json",
        success: function (res) {
            if(res && res.length > 0) {
                loadTsxx(res, data.XMID);
            } else {
                // 没有验证提示信息，默认验证通过，进行创件
                createProcess(dataParam);
            }
        },
        error: function (xhr, status, error) {
            failMsg("规则验证失败，请重试或联系管理员！");
        }
    });
}

/**
 * 新建注销流程
 * @param dataParam
 */
function createProcess(dataParam) {
    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zq/zxsq/process",
        type: 'POST',
        data: JSON.stringify(dataParam),
        dataType: "json",
        contentType: "application/json",
        success: function (res) {
            removeModel();
            if(res && !isNullOrEmpty(res.slbh)) {
                alertMsg("新建注销流程成功，对应受理编号：" + res.slbh);
                tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
            } else {
                failMsg("新建注销流程失败，请重试！");
            }
        },
        error: function (xhr, status, error) {
            removeModel();
            failMsg("新建注销流程失败，请重试！");
        },
        complete: function () {
            removeModal();
        }
    });
}

/**
 * 加载规则验证提示信息
 */
function loadTsxx(data, xmid) {
    var gztsHtml = '<div lay-filter="tsxx" id="tsxx"><div class="bdc-right-tips-box" id="bottomTips">';
    var confirmInfo = "", alertInfo = "";

    $.each(data, function (index, item) {
        if(item && !isNullOrEmpty(item.msg)) {
            var detail = "";
            if (item.xzxx && item.xzxx.length > 0 && !$.isEmptyObject(item.xzxx[0]) && !isNullOrEmpty(item.xzxx[0].XMID)) {
                detail = '<a href=\"javascrit:;\" onclick=\"openXmxx(\'' + item.xzxx[0].XMID + '\');return false\">查看</a>';
            }

            if (item.yzlx === 'confirm') {
                var ignore = '<img src="../../static/lib/bdcui/images/warn.png" alt="">' + item.msg +
                             '<a href="javascrit:;" onclick="removeTsxx(this, \''+ item.gzid + '\',\'' + item.msg + '\',\'' + xmid + '\');return false" >忽略</a>';
                confirmInfo += '<p>' + ignore + detail + '</p>';
                confirmSize++;
            } else if (item.yzlx === 'alert') {
                var alert = '<img src="../../static/lib/bdcui/images/error.png" alt="">' + item.msg;
                alertInfo += '<p>' + alert + detail + '</p>';
                alertSize++;
            }
        }
    });

    gztsHtml +=  '<div class="tsxx" id="alertInfo">' + alertInfo + '</div>';
    gztsHtml +=  '<div class="tsxx" id="confirmInfo">' + confirmInfo + '</div>';
    gztsHtml +=  '</div></div>';

    //打开弹出层
    yztsxxLayer = layer.open({
        type: 1,
        title: '提示信息',
        skin: 'bdc-tips-right',
        shade: [0],
        area: ['600px'],
        offset: 'r', //右下角弹出
        time: 5000000,
        anim: 2,
        content: gztsHtml,
        success: function () {
            removeModal();
        },
        cancel: function () {
        }
    });
}

//查看项目信息
function openXmxx(xmid) {
    $.ajax({
        url: "/realestate-inquiry-ui/history/queryBdcXmDoByXmid",
        type: 'GET',
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

/**
 * 忽略
 */
function removeTsxx (elem, gzid, msg, xmid) {
    var data = elem.parentElement.innerText.replace(/忽略/g, "").replace(/查看/g, "");

    //点击忽略时增加日志记录，同步处理
    addRemoveLog(gzid, msg);

    var tsxxInfo = $(elem).parents(".tsxx");
    if (tsxxInfo.length > 0) {
        var tsxxID = tsxxInfo.attr("id");
        if (tsxxID === "confirmInfo") {
            confirmSize--;
            $(elem).parent().remove();
        }
    }

    //在没有警告提示下创建
    if (alertSize === 0 && confirmSize === 0) {
        layer.close(yztsxxLayer);
        createProcess({"xmid": xmid});
    }
};

//增加验证忽略日志
function addRemoveLog(gzid, msg) {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zq/gzyz/addIgnoreLog",
        type: 'POST',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({"gzid": gzid, "tsxx": msg}),
        success: function (data) {
        },
        error: function (err) {
        }
    });
}


/**
 * 审核通过台账退回
 */
function th(obj,data) {
    if (!data || isNullOrEmpty(data.SQXXID)) {
        warnMsg("无申请信息ID，无法退回！");
        return;
    }

    if(!isNullOrEmpty(data.ZXLCSLBH)) {
        warnMsg("已创建流程，无法退回！");
        return
    }

    layer.open({
        type: 2,
        title: '退回',
        anim: -1,
        shadeClose: true,
        maxmin: true,
        shade: false,
        area: ['960px', '250px'],
        offset: 'auto',
        content: [ "../../yancheng/zq/th.html?sqxxid=" + data.SQXXID, 'yes'],
        success: function (layero, index) {
        },
        end:function(){
            if("th" == state) {
                tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
            }
        }
    });
}

/**
 * 审核台账 退回
 */
function shth(obj,data) {
    if (!data || isNullOrEmpty(data.SQXXID)) {
        warnMsg("无申请信息ID，无法退回！");
        return;
    }

    if(!isNullOrEmpty(data.ZXLCSLBH)) {
        warnMsg("已创建流程，无法退回！");
        return
    }

    if(data.SHZT == 3){
        warnMsg('注销申请已审核,当前环节不能退回！');
        return;
    }

    if(data.SHZT == 4 || data.SHZT == 5){
        warnMsg('注销申请已删除,当前环节不能退回！');
        return;
    }

    if(data.SHZT == 6 || data.SHZT == 7){
        warnMsg('注销申请已退回,无法重复退回！');
        return;
    }

    layer.open({
        type: 2,
        title: '退回',
        anim: -1,
        shadeClose: true,
        maxmin: true,
        shade: false,
        area: ['960px', '250px'],
        offset: 'auto',
        content: [ "../../yancheng/zq/th.html?sqxxid=" + data.SQXXID, 'yes'],
        success: function (layero, index) {
        },
        end:function(){
            if("th" == state) {
                tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
            }
        }
    });
}

var state = "";
function setState() {
    state = "th";
}