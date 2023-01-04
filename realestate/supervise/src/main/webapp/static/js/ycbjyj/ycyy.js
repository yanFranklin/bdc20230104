/**
 *  异常原因
 */
var ycyyid = "";
layui.use(['form', 'jquery', 'element', 'layer', 'laytpl', 'upload'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;

    $(function () {
        ycyyid = $.getUrlParam('id');
        // 页面编辑状态
        if(!isNullOrEmpty(ycyyid)) {
            addModel();
            $.ajax({
                url: "/realestate-supervise/rest/v1.0/ycbjyj/ycyy/" + ycyyid,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if(data && !isNullOrEmpty(data.id)){
                        form.val('form', data);
                        fileUpload();
                    } else {
                        failMsg("未获取到异常原因信息");
                    }
                },
                complete: function () {
                    removeModal();
                }
            });
        }

        resetInputDisabledCss();

        // 异常原因报告上传
        function fileUpload() {
            upload.render({
                elem: '#fjcl'
                , url: "/realestate-supervise/rest/v1.0/files/upload/" + ycyyid
                , accept: 'file' //普通文件
                ,before: function(obj){
                    if(isNullOrEmpty(ycyyid)) {
                        warnMsg("请先保存异常原因！");
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
                    warnMsg("上传失败，请先保存异常原因！");
                }
            });
        }

        form.on('submit(submitBtn)', function(data) {
            var formData = data.field;

            if(!formData || isNullOrEmpty(formData.ycbjyy)) {
                warnMsg("请填写异常原因！");
                return;
            }

            if(isNullOrEmpty(formData.slbh)) {
                warnMsg("请关联业务！");
                return;
            }

            addModel();
            $.ajax({
                url: "/realestate-supervise/rest/v1.0/ycbjyj/ycyy",
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(formData),
                dataType: "text",
                success: function (data) {
                    if(!isNullOrEmpty(data)){
                        $("#id").val(data);
                        ycyyid = data;
                        successMsg("保存成功！");
                        fileUpload();
                    } else {
                        failMsg("保存异常原因失败，请重试");
                    }
                },
                error: function (xhr, status, error) {
                    failMsg("保存异常原因失败，请重试");
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
            if(isNullOrEmpty(ycyyid)) {
                warnMsg("请先保存异常原因！");
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
                content: [ "../file/files.html?page=edit&ywid=" + ycyyid, 'yes'],
                success: function (layero, index) {
                },
                end:function(){
                }
            });
        });

        /**
         * 关联业务
         */
        $("#glyw").click(function () {
            parent.layer.open({
                type: 2,
                title: '项目信息',
                anim: -1,
                shadeClose: true,
                maxmin: true,
                shade: false,
                area: ['100%', '100%'],
                offset: 'auto',
                content: [ "xmxxtz.html", 'yes'],
                success: function (layero, index) {
                },
                end:function(){
                    var xmxx = layui.sessionData('xmxx');
                    if(xmxx && xmxx.data && !isNullOrEmpty(xmxx.data.slbh)) {
                        form.val('form', xmxx.data);
                    }
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