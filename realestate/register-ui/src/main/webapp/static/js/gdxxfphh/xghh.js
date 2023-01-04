/**
 *  修改盒号
 */
var gdxxid = "";
layui.use(['form', 'jquery', 'element', 'layer', 'laytpl', 'upload'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;

    $(function () {
        gdxxid = $.getUrlParam('gdxxid');
        if(!isNullOrEmpty(gdxxid)) {
            $("#gdxxid").val(gdxxid);
        }

        // 页面编辑状态
        if(!isNullOrEmpty(gdxxid)) {
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/gdxxfphh/xghh/" + gdxxid,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if(data && !isNullOrEmpty(data.gdxxid)){
                        form.val('form', data);
                    } else {
                        failMsg("未获取到基础信息");
                    }
                }
            });
        }

        form.on('submit(submitBtn)', function(data) {
            var formData = data.field;

            if(!formData || isNullOrEmpty(formData.sxh)) {
                warnMsg("请填写盒号！");
                return;
            }

            addModel();
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/gdxxfphh/upfphh",
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(formData),
                dataType: "text",
                success: function (data) {
                    if(!isNullOrEmpty(data)){
                        $("#gdxxid").val(data);
                        gdxxid = data;
                        successMsg("保存成功！");
                    } else {
                        failMsg("保存盒号失败，请重试");
                    }
                },
                error: function (xhr, status, error) {
                    failMsg("保存盒号失败，请重试");
                },
                complete: function () {
                    removeModel();
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