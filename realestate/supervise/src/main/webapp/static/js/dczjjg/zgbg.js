/**
 *  督察质检监管-质检台账-整改情况录入页面JS
 */
var zgqkid = "";
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
                            $('#zgbmid').append('<option value="' + item.id + '">' + item.name + '</option>');
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

        form.on('select(zgbmid)', function(data){
            $("#zgbm").val(this.innerText);
        });

        zjxxid = $.getUrlParam('zjxxid');
        $("#zjxxid").val(zjxxid);

        // 整改报告上传
        function fileUpload() {
            upload.render({
                elem: '#fjcl'
                , url: "/realestate-supervise/rest/v1.0/dczjjg/zgbg/file/" + zgqkid
                , accept: 'file' //普通文件
                , done: function (res) {
                    console.log(res);
                    if (res && res.downUrl) {
                        successMsg('上传成功');
                    } else {
                        failMsg("上传失败！");
                    }
                }
                ,error: function(index, upload) {
                    warnMsg("上传失败，请先保存整改信息！");
                }
            });
        }

        form.on('submit(submitBtn)', function(data) {
            var formData = data.field;

            if(!formData || (isNullOrEmpty(formData.scryid) && isNullOrEmpty(formData.zgbmid) && isNullOrEmpty(formData.zgqksm))) {
                warnMsg("请填写整改情况！");
                return;
            }

            addModel();
            $.ajax({
                url: "/realestate-supervise/rest/v1.0/dczjjg/zgqk",
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(formData),
                dataType: "text",
                success: function (data) {
                    if(!isNullOrEmpty(data)){
                        $("#id").val(data);
                        zgqkid = data;
                        successMsg("保存成功！");
                        fileUpload();
                    } else {
                        failMsg("保存整改信息失败，请重试");
                    }
                },
                error: function (xhr, status, error) {
                    failMsg("保存整改信息失败，请重试");
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