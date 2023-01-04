/**
 * 关闭弹出页面
 */
window.closeWin = function () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};


var fwDcbIndex = $("#fwDcbIndex").val();
layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    //form验证
    form.render();
    //提交表单
    form.on("submit(updateFwlx)", function (data) {
        var postData = data.field
        postData.fwDcbIndex = fwDcbIndex;
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        if (fwDcbIndex) {
            addModel();
            $.ajax({
                url: "../xmxx/cancelrelevanceljz",
                dataType: "json",
                data: postData,
                success: function (data) {
                    removeModal();
                    if (data && data.success) {
                        parent.layer.closeAll()
                        parent.layer.msg(data.msg)
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        } else {
            layer.alert("逻辑幢主键为空，无法修改")
        }
        return false;
    })
});