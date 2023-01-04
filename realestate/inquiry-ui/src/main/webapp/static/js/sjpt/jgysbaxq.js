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
    if(isNullOrEmpty(certCode)){
        warnMsg("未获取到备案编号");
    }
    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/sjxxgx/jgysbaxqcx",
        type: 'GET',
        data:{"certCode":certCode},
        async: false,
        success: function (data) {
            console.log(data)
            if(data){
                $("#certCode").val(isNotBlank(data.certCode) ? data.certCode: "");
                $("#buildUnit").val(isNotBlank(data.buildUnit) ? data.buildUnit: "");
                $("#projName").val(isNotBlank(data.projName) ? data.projName: "");
                $("#projAddr").val(isNotBlank(data.projAddr) ? data.projAddr: "");
                $("#buildingArea").val(isNotBlank(data.buildingArea) ? data.buildingArea: "");
                $("#structureType").val(isNotBlank(data.structureType) ? data.structureType: "");
                $("#projType").val(isNotBlank(data.projType) ? data.projType: "");
                $("#startDate").val(isNotBlank(data.startDate) ? data.startDate: "");
                $("#completeDate").val(isNotBlank(data.completeDate) ? data.completeDate: "");
                $("#sgxkCertCode").val(isNotBlank(data.sgxkCertCode) ? data.sgxkCertCode: "");
                $("#sgtsOpinion").val(isNotBlank(data.sgtsOpinion) ? data.sgtsOpinion: "");
                $("#surveyUnit").val(isNotBlank(data.surveyUnit) ? data.surveyUnit: "");
                $("#designUnit").val(isNotBlank(data.designUnit) ? data.designUnit: "");
                $("#constructionUnit").val(isNotBlank(data.constructionUnit) ? data.constructionUnit: "");
                $("#supervisoryUnit").val(isNotBlank(data.supervisoryUnit) ? data.supervisoryUnit: "");
                $("#qualitySupervisoryUnit").val(isNotBlank(data.qualitySupervisoryUnit) ? data.qualitySupervisoryUnit: "");
                $("#jgysDate").val(isNotBlank(data.jgysDate) ? data.jgysDate: "");
                $("#handlePerson").val(isNotBlank(data.handlePerson) ? data.handlePerson: "");
                $("#recordOpinion").val(isNotBlank(data.recordOpinion) ? data.recordOpinion: "");
                $("#organOPinion").val(isNotBlank(data.organOPinion) ? data.organOPinion: "");
            }
            removeModal();
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    })
}

function cancel(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}


