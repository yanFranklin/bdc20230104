layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var bdcdyh = $("#bdcdyh").val();

    // loading加载
    addModel();
    $.ajax({
        url: "../fwhs/bdcdyh",
        dataType: "json",
        data: {
            bdcdyh: bdcdyh
        },
        success: function (data) {
            removeModal();
            layer.closeAll();
            form.val("form", data)
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });

});