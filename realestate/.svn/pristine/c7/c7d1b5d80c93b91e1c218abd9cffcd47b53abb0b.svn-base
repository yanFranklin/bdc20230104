layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'layer'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;

    //获取数据
    addModel();
    $.ajax({
        url: "../djdcb/zdqsdc",
        dataType: "json",
        data: {
            bdcdyh: $("#bdcdyh").val(),
            qjgldm:qjgldm
        },
        success: function (data) {
            removeModal();
            layer.closeAll();
            form.val("qsdcForm", data);
            disabledAddFa();
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });
});