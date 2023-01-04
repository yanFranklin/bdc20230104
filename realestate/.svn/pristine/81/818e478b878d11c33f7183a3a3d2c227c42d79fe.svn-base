var bglx =$("#bglx").val();
var fwHsIndex = $("#fwHsIndex").val();
var bgbh=$("#bgbh").val();
layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    if(bgbh){
        var formLayer = layer;
        addModel();
        $.ajax({
            url: "../bgxx/getbgxx",
            dataType: "json",
            data: {
                bgbh: bgbh
            },
            success: function (data) {
                removeModal();
                if (data) {
                    form.val("form", data)
                }
            },
            error: function (e) {
                delAjaxErrorMsg(e,this)
            }
        });
    }

    //form初始化
    form.render();
    form.on("submit(saveBgxx)", function (data) {
        var postData = data.field;
        postData.bglx = bglx;
        var formLayer = layer;
        // 更新场景
        if($("#hsBgIndex").val()){
            // loading加载
            addModel();
            // 保存变更信息
            $.ajax({
                url: "../bgxx/update",
                data: {jsonData:JSON.stringify(postData)},
                success: function () {
                    removeModal();
                    //变更结束 关闭此弹窗，向父页面写BGBH
                    // 根据回退页面判断 是 项目变更 还是 户室变更
                    if(sessionParamObjet && sessionParamObjet.fwXmxxIndex){
                        parent.$.xmbg._submitBgWithBgbh(bglx, bgbh);
                    }else{
                        parent.$.hsbg._submitBgWithBgbh(bglx, bgbh);
                    }
                },
                error: function (e) {
                    delAjaxErrorMsg(e,this);
                }
            });
        }else{
            // 遮罩
            addModel();
            $.ajax({
                url: "../fwhsbg/getbgbh",
                dataType: "json",
                success: function (data) {
                    removeModal();
                    if (data) {
                        bgbh = data;
                        postData.bgbh = data;
                        // loading加载
                        addModel();
                        $.ajax({
                            url: "../bgxx/insertbgxx",
                            data: postData,
                            success: function () {
                                removeModal();
                                //变更结束 关闭此弹窗，向父页面写BGBH
                                // 根据回退页面判断 是 项目变更 还是 户室变更
                                if(sessionParamObjet
                                    && (sessionParamObjet.fwXmxxIndex
                                        || sessionParamObjet.xmxxHbMainFwXmxxIndex)){
                                    parent.$.xmbg._submitBgWithBgbh(bglx, bgbh);
                                }else{
                                    parent.$.hsbg._submitBgWithBgbh(bglx, bgbh);
                                }
                            },
                            error: function (e) {
                                delAjaxErrorMsg(e,this);
                            }
                        });
                    } else {
                        formLayer.alert("获取变更编号失败")
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        }

    });
});

function getBackUrl(parent){
    if(parent){
        return sessionParamObjet.fromurl;
    }else{
        var backUrl = window.location.href;
        if(!GetQueryString("bgbh")){
            backUrl += "&bgbh="+bgbh;
        }
        return backUrl;
    }
}
