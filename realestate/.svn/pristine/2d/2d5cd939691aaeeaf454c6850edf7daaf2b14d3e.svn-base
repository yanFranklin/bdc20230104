var certCode = $.getUrlParam('certCode');
var form;
layui.use(['form', 'jquery', 'element', 'table', 'laydate','upload'], function () {
    form = layui.form;
    addModel();
    setTimeout(function () {
        try {
            $.when(generateGgxx()).done(function () {
                removeModal();
            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e,"加载失败");
            return
        }
    }, 10);
})

/**
 * 渲染竣工备案信息数据
 */
function generateGgxx(){
    var data = parent.rkdata
    if(isNullOrEmpty(data)){
        warnMsg("未获取到人口信息");
    }
    addModel();
    $("#xm").val(isNotBlank(data.xM) ? data.xM: "");
    $("#xb").val(isNotBlank(data.xB) ? data.xB: "");
    $("#zjh").val(isNotBlank(data.zJH) ? data.zJH: "");
    $("#mz").val(isNotBlank(data.mZ) ? data.mZ: "");
    $("#gj").val(isNotBlank(data.gJ) ? data.gJ: "");
    $("#sg").val(isNotBlank(data.sG) ? data.sG: "");
    $("#hjdz").val(isNotBlank(data.hJDZ) ? data.hJDZ: "");
    $("#csrq").val(isNotBlank(data.cSRQ) ? data.cSRQ: "");
    $("#whcd").val(isNotBlank(data.wHCD) ? data.wHCD: "");
    $("#yhzgx").val(isNotBlank(data.yHZGX) ? data.yHZGX: "");
    $("#ssxq").val(isNotBlank(data.sSXQ) ? data.sSXQ: "");
    $("#hslbz").val(isNotBlank(data.hSLBZ) ? data.hSLBZ: "");
    $("#hxzlbz").val(isNotBlank(data.hXZLBZ) ? data.hXZLBZ: "");
    $("#hyzk").val(isNotBlank(data.hYZK) ? data.hYZK: "");
    $("#jhrq").val(isNotBlank(data.jHRQ) ? data.jHRQ: "");
    $("#nvxm").val(isNotBlank(data.nVXM) ? data.nVXM: "");
    $("#nvzjh").val(isNotBlank(data.nVZJH) ? data.nVZJH: "");
    $("#nxm").val(isNotBlank(data.nXM) ? data.nXM: "");
    $("#nzjh").val(isNotBlank(data.nZJH) ? data.nZJH: "");
    $("#qfjg").val(isNotBlank(data.qFJG) ? data.qFJG: "");
    $("#yxqxqsrq").val(isNotBlank(data.yxqxqsrq) ? data.yxqxqsrq: "");
    removeModal();
}

function cancel(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}


