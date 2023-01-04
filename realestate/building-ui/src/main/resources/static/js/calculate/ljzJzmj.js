layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    //form初始化
    form.render();
    form.on('radio(jsfw)', function (data) {
        if (data.elem.title == "宗地") {
            $("#chooseZd").removeClass("layui-hide")
            $("#chooseLjz").addClass("layui-hide")
        } else if (data.elem.title == "逻辑幢") {
            $("#chooseLjz").removeClass("layui-hide")
            $("#chooseZd").addClass("layui-hide")
        }
    });
    form.on("submit(calculated)", function (data) {
        var postData = data.field
        if(postData.zhs){
            postData.zhs=true
        }
        if(postData.dxs){
            postData.dxs=true
        }
        addModel();
        $.ajax({
            url: "../calculated/ljzzjmj",
            dataType: "json",
            data: postData,
            success: function (data) {
                removeModal()
                if(data&&data.success){
                    layer.msg(data.msg)
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
        return false;
    })


    //通过配置计算（先保留）
    /*form.on("submit(calculateConfig)", function (data) {
        var postData = data.field
        $.ajax({
            url: "../calculated/ljzzjmj/byconfig",
            dataType: "json",
            data: {
                fwDcbIndex:postData.fwDcbIndex
            },
            success: function (data) {

            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
        return false;
    })*/



});

$("#chooseZd").click(function () {
    var index = layer.open({
        type: 2,
        title: "选择宗地",
        area: ['960px', '437px'],
        fixed: false, //不固定
        content: '../zd/picklist'
    });
});

$("#chooseLjz").click(function () {
    var index = layer.open({
        type: 2,
        title: "选择逻辑幢",
        area: ['960px', '420px'],
        fixed: false, //不固定
        content: '../ljz/picklist?showQuery=lszd,ljzh'
    });
});

function pickLjzCallback(data) {
    $("#zdh").val(data.lszd);
    $("#fwDcbIndex").val(data.fw_dcb_index);
    $("#fwmc").val(data.fwmc);
    layui.use(['layer', 'form'], function () {
        var form = layui.form;
        form.render();
        layer.closeAll();
    });
}
// 宗地列表回调函数
function pickZdlistCallback(djh){
    $("#zdh").val(djh);
    $("#fwDcbIndex").val("");
    $("#fwmc").val("");
    layui.use(['layer','form'], function () {
        var form = layui.form;
        form.render();
    });
}