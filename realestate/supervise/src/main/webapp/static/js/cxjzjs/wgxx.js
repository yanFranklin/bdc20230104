/**
 *  7 诚信机制建设：违规台账-违规信息
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
                        fileUpload();
                    } else {
                        failMsg("未获取到违规信息");
                    }
                },
                complete: function () {
                    removeModal();
                }
            });
        }

        // 违规证明文件上传
        function fileUpload() {
            upload.render({
                elem: '#fjcl'
                , url: "/realestate-supervise/rest/v1.0/files/upload/" + wgjlid
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
                    warnMsg("上传失败，请先保存违规信息！");
                }
            });
        }

        form.on('submit(submitBtn)', function(data) {
            var formData = data.field;

            if(!formData || (isNullOrEmpty(formData.wgryjgmc) && isNullOrEmpty(formData.wgryjgzjh))) {
                warnMsg("请填写违规人员机构信息！");
                return;
            }

            addModel();
            $.ajax({
                url: "/realestate-supervise/rest/v1.0/cxjzjs/wgxx",
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(formData),
                dataType: "text",
                success: function (data) {
                    if(!isNullOrEmpty(data)){
                        $("#id").val(data);
                        wgjlid = data;
                        successMsg("保存成功！");
                        fileUpload();
                    } else {
                        failMsg("保存违规信息失败，请重试");
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

        /**
         * 附件台账
         */
        $("#fjtz").click(function () {
            if(isNullOrEmpty(wgjlid)) {
                warnMsg("请先保存违规信息！");
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
                content: [ "../file/files.html?page=edit&ywid=" + wgjlid, 'yes'],
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