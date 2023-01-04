/**
 * 关闭弹出页面
 */
window.closeWin = function () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};


layui.use(['form', 'jquery', 'upload'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    form.render();

    //提交表单
    form.on("submit(saveForm)", function (data) {
        var postData = data.field;
        var formLayer = layer;
        if (parent.layer) {
            formLayer = parent.layer
        }
        addModel();
        $.ajax({
            url: "../fwhsgl/zzgl",
            dataType: "json",
            data: postData,
            success: function (data) {
                removeModal();
                if (data && data.success) {
                    formLayer.msg("关联成功")
                } else if (data && data.msg) {
                    formLayer.alert(data.msg)
                } else {
                    formLayer.alert("关联失败")
                }
            },
            error: function (xhr, status, error) {
                formLayer.closeAll();
                delAjaxErrorMsg(xhr,this);
            }
        });
        return false;
    })
});