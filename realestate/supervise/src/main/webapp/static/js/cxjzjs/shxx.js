/**
 *  7 诚信机制建设：审核信息
 */
var wgjlid = "";
layui.use(['form', 'jquery', 'element', 'layer', 'laytpl', 'upload'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;

    $(function () {
        wgjlid = $.getUrlParam('id');
        if(!isNullOrEmpty(wgjlid)) {
            $("#id").val(wgjlid);
        }

        // 页面编辑状态
        if(!isNullOrEmpty(wgjlid)) {
            addModel();
            $.ajax({
                url: "/realestate-supervise/rest/v1.0/cxjzjs/wgxx/" + wgjlid,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if(data && !isNullOrEmpty(data.id)){
                        form.val('form', data);
                    } else {
                        failMsg("未获取到违规信息");
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
                url: "/realestate-supervise/rest/v1.0/cxjzjs/wgxx/shxx",
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(formData),
                dataType: "text",
                success: function (data) {
                    if(!isNullOrEmpty(data)){
                        $("#id").val(data);
                        wgjlid = data;
                        successMsg("保存成功！");

                        // 关闭layer
                        setTimeout(function() {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        }, 1000);

                    } else {
                        failMsg("保存审核信息失败，请重试");
                    }
                },
                error: function (xhr, status, error) {
                    failMsg("保存违规信息失败，请重试");
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