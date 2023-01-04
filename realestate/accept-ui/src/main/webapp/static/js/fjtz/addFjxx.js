layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery, layer = layui.layer, form = layui.form;
    var wjzxid;
    /**
     * 表单数据提交
     */
    form.on('submit(submitBtn)', function (data) {
        $.ajax({
            url: "/realestate-inquiry-ui/fjtz/addfj",
            type: "POST",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {

                if (isNullOrEmpty(data)) {
                    fail(data);
                } else {
                    layer.msg("新增成功")
                    $("#submitBtn").hide();
                    // $("#fjsc").removeClass("layui-btn-disabled").show();
                    wjzxid = data.wjzxid;
                    viewWjglFj(wjzxid, "clientId", null, "附件新增", 2, true);
                }
            },
            error: function ($xhr, textStatus, errorThrown) {
                layer.msg("新增失败")
                // fail(JSON.parse($xhr.responseText));
            }
        });

        // 禁止提交后刷新
        return false;
    });

    form.on('submit(fjsc)', function (data) {
        // 禁止提交后刷新
        return false;
    });
    // $('#fjsc').on('click', function () {
    //     viewWjglFj(wjzxid, "clientId", null, "附件新增", 2, true);
    // });
    /**
     * 提交成功提示
     */
    window.success = function () {
        saveSuccessMsg();
        setTimeout(function () {
            /*      var index = parent.parent.parent.layer.getFrameIndex(window.name);
                  parent.parent.parent.layer.close(index);*/
            parent.layer.closeAll();
        }, 2000);
    }

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function (data) {
        var msg = "提交失败，请检查填写内容!";
        layer.msg(msg);
    }

});

// function fjsc(){
//     //viewWjglFj(wjzxid, "clientId", null, "附件新增", 2, true);
//     return false;
// }