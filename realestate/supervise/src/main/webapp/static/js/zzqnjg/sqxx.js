/**
 *  4 职责权能监管-授权信息管理台账-授权信息
 */
layui.config({
    base: '../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});

var sqxxid = "";
layui.use(['form', 'jquery', 'element', 'layer', 'laydate', 'laytpl', 'upload','formSelects'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    var laydate = layui.laydate;
    var formSelects = layui.formSelects;

    laydate.render({
        elem: '#sqsj'
        ,type: 'datetime'
    });

    $("#sqsj").val(formatDate(new Date()));

    $(function () {
        sqxxid = $.getUrlParam('id');
        if(!isNullOrEmpty(sqxxid)) {
            $("#id").val(sqxxid);
        }

        addModel();
        // 加载人员下拉框
        $.ajax({
            url: "/realestate-supervise/rest/v1.0/user/users_orgs",
            type: "GET",
            async: false,
            dataType: "json",
            success: function (data) {
                if(data){
                    if(data.users) {
                        var formSelectData = new Array();
                        $.each(data.users, function (index, item) {
                            formSelectData.push({"name":item.alias, "value":item.id});
                        });
                        formSelects.data('sqryxx', 'local', {arr: formSelectData});
                        form.render('select');
                    }
                    if(data.orgs) {
                        var formSelectData = new Array();
                        $.each(data.orgs, function (index, item) {
                            formSelectData.push({"name":item.name, "value":item.id});
                        });
                        formSelects.data('bmmc', 'local', {arr: formSelectData});
                        form.render('select');
                    }
                }
            },
            complete: function () {
                removeModal();
            }
        });

        // 监听人员选中,根据人员获取部门
        formSelects.on('sqryxx', function(id, vals, val, isAdd, isDisabled){
            //获取已选中授权人员
            var userid = [];
            if (vals.length>0){
                for(var i=0;i<vals.length;i++){
                    userid.push(vals[i].value);
                }
            }
            var userids = userid.toString();
            if (userid.length>0){
                $.ajax({
                    url: "/realestate-supervise/rest/v1.0/user/orgs/" + userids,
                    type: "GET",
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        console.log(data);
                        if (data.length>0){
                            var orgid = [];
                            for(var i=0;i<data.length;i++){
                                orgid.push(data[i].id);
                            }
                            formSelects.value('bmmc', orgid);
                        }

                    }
                });
            }
        }, true);

        // 页面编辑状态
        if(!isNullOrEmpty(sqxxid)) {
            addModel();
            $.ajax({
                url: "/realestate-supervise/rest/v1.0/zzqnjg/sqxx/" + sqxxid,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if(data && !isNullOrEmpty(data.id)){
                        form.val('form', data);
                        fileUpload();

                        getSqryxx(sqxxid);
                    } else {
                        failMsg("未获取到授权信息");
                    }
                },
                complete: function () {
                    removeModal();
                }
            });
        }

        // 获取授权信息关联的授权人员信息
        function getSqryxx(sqxxid) {
            $.ajax({
                url: "/realestate-supervise/rest/v1.0/zzqnjg/sqxx/sqry/" + sqxxid,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if(data && data.length > 0){
                        var selectedData = new Array();
                        var bmselected = new Array();
                        $.each(data, function (index, item) {
                            selectedData.push(item.sqryid);
                            bmselected.push(item.bmid);
                        });
                        formSelects.value('sqryxx', selectedData);
                        formSelects.value('bmmc', bmselected);
                        $("#ks").val(data[0].ks);
                    }
                },
                complete: function () {
                    removeModal();
                }
            });
        }

        // 授权证明文件上传
        function fileUpload() {
            upload.render({
                elem: '#fjcl'
                , url: "/realestate-supervise/rest/v1.0/files/upload/" + sqxxid
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

        form.on('submit(submitBtn)', function(data) {
            var formData = data.field;

            if(!formData) {
                warnMsg("请填写授权信息！");
                return;
            }

            if(isNullOrEmpty(formData.sqryxx)) {
                warnMsg("请选择需要授权的人员！");
                return;
            }

            if(isNullOrEmpty(formData.sqlx)) {
                warnMsg("请选择授权类型！");
                return;
            }

            addModel();
            $.ajax({
                url: "/realestate-supervise/rest/v1.0/zzqnjg/sqxx",
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(formData),
                dataType: "text",
                success: function (data) {
                    if(!isNullOrEmpty(data)){
                        $("#id").val(data);
                        sqxxid = data;
                        successMsg("保存成功！");
                        fileUpload();
                    } else {
                        failMsg("保存授权信息失败，请重试");
                    }
                },
                error: function (xhr, status, error) {
                    failMsg("保存授权信息失败，请重试");
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
            if(isNullOrEmpty(sqxxid)) {
                warnMsg("请先保存授权信息！");
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
                content: [ "../file/files.html?page=edit&ywid=" + sqxxid, 'yes'],
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