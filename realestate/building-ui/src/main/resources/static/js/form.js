function saveAndReloadForm(formId, url, postData) {
    if (url && formId) {
        layui.use(['form', 'jquery'], function () {
            var form = layui.form;
            var $ = layui.jquery;
            $.ajax({
                url: url,
                dataType: "json",
                data: postData,
                success: function (data) {
                    layer.closeAll();
                    if (data && data.success) {
                        layer.msg("提交成功");
                        if (data.data) {
                            form.val(formId, data.data)
                        }
                    } else if (data && data.msg) {
                        layer.alert(data.msg)
                    } else {
                        layer.alert("提交失败")
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        })
    } else {
        layer.alert("请求错误，请检查文件")
    }
}