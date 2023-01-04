/**
 *  异常办件：审核信息
 */
var id = "", type = "";
layui.use(['form', 'jquery', 'element', 'layer', 'laytpl'], function () {
    var form = layui.form;
    var $ = layui.jquery;

    $(function () {
        id = $.getUrlParam('id');
        type = $.getUrlParam('type');

        if(!isNullOrEmpty(id)) {
            $("#id").val(id);
        }

        // 页面编辑状态
        if(!isNullOrEmpty(id)) {
            addModel();
            $.ajax({
                url: "/realestate-supervise/rest/v1.0/ycbjyj/" + type + "/" + id,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if(data && !isNullOrEmpty(data.id)){
                        form.val('form', data);
                    } else {
                        failMsg("未获取到异常信息");
                    }
                },
                complete: function () {
                    removeModal();
                }
            });
        }

        form.on('submit(submitBtn)', function(data) {
            var formData = data.field;

            if(!formData || isNullOrEmpty(formData.shyj)) {
                warnMsg("请填写审核意见！");
                return;
            }

            addModel();
            $.ajax({
                url: "/realestate-supervise/rest/v1.0/ycbjyj/" + type + "/shxx",
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(formData),
                dataType: "text",
                success: function (data) {
                    if(!isNullOrEmpty(data)){
                        $("#id").val(data);
                        id = data;
                        successMsg("保存成功！");
                    } else {
                        failMsg("保存审核信息失败，请重试");
                    }
                },
                error: function (xhr, status, error) {
                    failMsg("保存审核信息失败，请重试");
                },
                complete: function () {
                    removeModal();
                }
            });
        });

        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#submitBtn").click();
            }
        });
    });
});

/**
 * 关闭弹出页面
 */
window.closeWin = function(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

window.closeParentWindow = function(){
    var index = parent.parent.layer.getFrameIndex(window.name);
    parent.parent.layer.close(index);
};