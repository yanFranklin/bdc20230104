/**
 * 异常情况主动申报
 */
var zdList = getZdList();
var ycbjyy = zdList.ycbjyy;
var sbxxid;
layui.use(['jquery', 'layer', 'element', 'form',  'upload','laytpl'], function () {
    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer,
        upload = layui.upload,
        element = layui.element,
        laydate = layui.laydate;

    var processInsId = $.getUrlParam('processInsId');
    var taskId = $.getUrlParam('taskId');
    var id = $.getUrlParam('id');
    var type = $.getUrlParam('type');

    $("#fjcl").mouseover(function () {
        layer.tips('保存申报信息后可上传附件', this, {
            tips:[1, '#000']
        });
    });

    if("view" === type) {
        $("#saveData").hide();
        $("#fjcl").hide();
    }

    $.ajax({
        url: "/realestate-supervise/rest/v1.0/ycbjyj/ycqksb/xmxx?processInsId=" + processInsId + "&id=" + id,
        type: "GET",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        success: function (data) {
            if(data){
                if(!isNullOrEmpty(data.id)) {
                    sbxxid = data.id;
                }
                form.val('sbxx',data);
            } else {
                failMsg("未获取到项目信息，请重试");
            }
        },
        error: function (xhr, status, error) {
            failMsg("未获取到项目信息，请重试");
        },
        complete: function () {
            removeModal();
        }
    });

    resetInputDisabledCss();

    if(zdList) {
        // 质检类型查询条件下拉框
        if (ycbjyy) {
            $.each(ycbjyy, function (index, item) {
                $('#ycbjyy').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
        }
        form.render('select');
    }

    // 保存
    form.on("submit(saveData)", function (data) {
        var formData = data.field;

        if(!formData) {
            warnMsg("请填写申报信息！");
            return;
        }

        if(isNullOrEmpty(formData.gzlslid)) {
            warnMsg("没有获取到项目信息，请重试！");
            return;
        }

        if(isNullOrEmpty(formData.ycbjyy)) {
            warnMsg("请选择异常办件原因！");
            return;
        }

        addModel();
        $.ajax({
            url: "/realestate-supervise/rest/v1.0/ycbjyj/ycqksb",
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(formData),
            dataType: "text",
            success: function (data) {
                if(!isNullOrEmpty(data)){
                    $("#id").val(data);
                    sbxxid = data;
                    successMsg("保存成功！");
                    fileUpload();
                } else {
                    failMsg("保存申报信息失败，请重试");
                }
                removeModal();
            },
            error: function (xhr, status, error) {
                failMsg("保存申报信息失败，请重试");
            },
            complete: function () {
                removeModal();
            }
        });
    });

    // 授权证明文件上传
    function fileUpload() {
        upload.render({
            elem: '#fjcl'
            , url: "/realestate-supervise/rest/v1.0/files/upload/" + sbxxid
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
                warnMsg("上传失败，请先保存授权信息！");
            }
        });
    }

    /**
     * 附件台账
     */
    $("#fjtz").click(function () {
        if(isNullOrEmpty(sbxxid)) {
            warnMsg("未获取到申报信息，请先保存！");
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
        content: [ "../file/files.html?page=" + type + "&ywid=" + sbxxid, 'yes'],
        success: function (layero, index) {
        },
        end:function(){
        }
        });
    });


});

