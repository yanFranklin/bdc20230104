/**
 *  督察质检监管-质检台账-质检信息页面JS
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
                            $('#zjryid').append('<option value="' + item.id + '">' + item.alias + '</option>');
                        });
                    }

                    if(data.orgs) {
                        $.each(data.orgs, function (index, item) {
                            $('#zjbmid').append('<option value="' + item.id + '">' + item.name + '</option>');
                        });
                    }
                    form.render('select');
                }
            },
            complete: function () {
                removeModal();
            }
        });

        form.on('select(zjryid)', function(data){
            $("#zjry").val(this.innerText);
        });

        form.on('select(zjbmid)', function(data){
            $("#zjbm").val(this.innerText);
        });

        zjxxid = $.getUrlParam('id');
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
                        fileUpload();
                    } else {
                        failMsg("未获取到质检信息");
                    }
                },
                complete: function () {
                    removeModal();
                }
            });
        }

        // 质检报告上传
        function fileUpload() {
            upload.render({
                elem: '#fjcl'
                , url: "/realestate-supervise/rest/v1.0/files/upload/" + zjxxid
                , accept: 'file' //普通文件
                ,before: function(obj){
                    if(isNullOrEmpty(zjxxid)) {
                        warnMsg("请先保存质检信息！");
                        return false;
                    }
                }
                , done: function (res) {
                    console.log(res);
                    if (res && res.downUrl) {
                        successMsg('上传成功');
                    } else {
                        failMsg("上传失败！");
                    }
                }
                ,error: function(index, upload) {
                    warnMsg("上传失败，请先保存质检信息！");
                }
            });
        }

        form.on('submit(submitBtn)', function(data) {
            var formData = data.field;

            if(!formData || (isNullOrEmpty(formData.zjryid) && isNullOrEmpty(formData.zjbmid) && isNullOrEmpty(formData.zjqkbz)
                && isNullOrEmpty(formData.zjlx))) {
                warnMsg("请填写质检相关信息！");
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
                        $("#id").val(data);
                        zjxxid = data;
                        successMsg("保存成功！");
                        fileUpload();
                    } else {
                        failMsg("保存质检信息失败，请重试");
                    }
                },
                error: function (xhr, status, error) {
                    failMsg("保存质检信息失败，请重试");
                },
                complete: function () {
                    removeModal();
                }
            });
        });

        /**
         * 附件台账
         */
        $("#fjtz").click(function () {
            if(isNullOrEmpty(zjxxid)) {
                warnMsg("请先保存质检信息！");
                return;
            }

            parent.layer.open({
                type: 2,
                title: '附件信息',
                anim: -1,
                shadeClose: true,
                maxmin: true,
                shade: false,
                area: ['960px', '500px'],
                offset: 'auto',
                content: [ "../file/files.html?page=edit&ywid=" + zjxxid, 'yes'],
                success: function (layero, index) {
                },
                end:function(){
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