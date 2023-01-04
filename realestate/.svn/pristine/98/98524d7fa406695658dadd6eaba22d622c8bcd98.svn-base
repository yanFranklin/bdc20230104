/**
 *  督察质检监管-审核台账-质检审核信息页面
 */
var zjxxid = "";
layui.use(['form', 'jquery', 'element', 'layer', 'laytpl', 'upload'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;

    $(function () {
        addModel();
        // 加载人员机构下拉框
        $.ajax({
            url: "/realestate-supervise/rest/v1.0/user/users_orgs",
            type: "GET",
            async: false,
            dataType: "json",
            success: function (data) {
                if(data){
                    if(data.users) {
                        $.each(data.users, function (index, item) {
                            $('#scryid').append('<option value="' + item.id + '">' + item.alias + '</option>');
                        });
                    }

                    if(data.orgs) {
                        $.each(data.orgs, function (index, item) {
                            $('#scbmid').append('<option value="' + item.id + '">' + item.name + '</option>');
                        });
                    }
                    form.render('select');
                }
            },
            complete: function () {
                removeModal();
            }
        });

        form.on('select(scryid)', function(data){
            $("#scry").val(this.innerText);
        });

        form.on('select(scbmid)', function(data){
            $("#scbm").val(this.innerText);
        });

        zjxxid = $.getUrlParam('zjxxid');
        if(isNullOrEmpty(zjxxid)) {
            warnMsg("未指定质检记录，无法审核！");
            $("#submitBtn").hide();
        }
        $("#id").val(zjxxid);

        // 页面编辑状态
        if(!isNullOrEmpty(zjxxid)) {
            addModel();
            $.ajax({
                url: "/realestate-supervise/rest/v1.0/dczjjg/zjxx/" + zjxxid,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if(data && !isNullOrEmpty(data.id)){
                        form.val('form', data);
                    } else {
                        failMsg("未获取到审核信息");
                    }
                },
                complete: function () {
                    removeModal();
                }
            });
        }

        form.on('submit(submitBtn)', function(data) {
            var formData = data.field;

            if(!formData) {
                warnMsg("请填写审核相关信息！");
                return;
            }

            if(isNullOrEmpty(formData.scryid)) {
                warnMsg("请填写审核人员信息！");
                return;
            }

            if(isNullOrEmpty(formData.scbmid)) {
                warnMsg("请填写审核部门信息！");
                return;
            }

            addModel();
            $.ajax({
                url: "/realestate-supervise/rest/v1.0/dczjjg/zjxx",
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(formData),
                dataType: "text",
                success: function (data) {
                    if(!isNullOrEmpty(data)){
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