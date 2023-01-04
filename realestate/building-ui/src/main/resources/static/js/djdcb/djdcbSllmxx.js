$("#sllmxx").click(function(){
    if($("#sllmxxDjh").val()){
        return;
    }
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var bdcdyh = $("#bdcdyh").val();
        // loading加载
        addModel();
        $.ajax({
            url: "../djdcb/lqdcb",
            dataType: "json",
            data: {
                bdcdyh: bdcdyh,
                qjgldm:qjgldm
            },
            success: function (data) {
                removeModal();
                // layer.closeAll();
                form.val("sllmxxForm", data);
                initZd($("#sllmxxForm"));
                disabledAddFa();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    });
})

