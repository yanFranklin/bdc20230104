/**
 * 黑名单编辑页面
 */
var form;
var isSubmit = true;
layui.use(['layer', 'table', 'jquery', 'form','laydate'], function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var table = layui.table;
    form = layui.form;
    // 渲染锁定时间
    laydate.render({
        elem: '#cjsj',
        format: 'yyyy-MM-dd HH:mm:ss',
        //type: 'datetime',
    });

    $(function () {
        var verifyMsg = "必填项不能为空";
        form.on("submit(saveHmd)", function (data) {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                return false;
            } else {
                saveHmd();
                return false;
            }
        });
    });
});



function saveHmd(){
    var obj = {};
    $(".search").each(function (i) {
        var name = $(this).attr('name');
        var value = $(this).val();
        obj[name] = value;
    });
    addModel();
    $.ajax({
        url: getContextPath() + '/rest/v1.0/hmd/save',
        type: "POST",
        data: JSON.stringify(obj),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            if(isNotBlank(data)){
                $("#hmdid").val(data.hmdid);
            }
            removeModal();
            successMsg("提交成功，即将关闭当前窗口。");
            setTimeout(function(){
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(index); //再执行关闭
            }, 1000);
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }
    });
}

function cancelHmd(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

function renderForm(data){
    $("#hmdid").val(data.hmdid);
    $("#cjr").val(data.cjr);
    $("#cjsj").val(data.cjsj);
    $("#cjyy").val(data.cjyy);
    $("#ztmc").val(data.ztmc);
    $("#ztzjh").val(data.ztzjh);
    $("#bz").val(data.bz);
    $("#hmdztlb").val(data.hmdztlb);
    $("#hmdzt").val(data.hmdzt);
    if(data.hmdztlb == 1){
        $("#ztzjh").parents(".layui-inline").show();
        $("#ztzjh").attr("lay-verify", "required");
        $("#ztmc").removeAttr("disabled")
    }
    layui.form.render();
    disabledAddFa();
}

//身份证读卡器设置
window.onReadIdCard = function () {
    try {
        var cardInfo = readIdCard();
        if (cardInfo.ReadCard()) {
            var name = cardInfo.Name;
            var number = cardInfo.ID;

            $('input[name="ztmc"]').val(trimStr(name));
            $('input[name="ztzjh"]').val(trimStr(number));
        } else {
            warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
        }
    } catch (objError) {
        warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
    }
}