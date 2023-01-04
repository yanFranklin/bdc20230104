/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/01/17
 * describe: 新增或者编辑证书模板配置处理JS
 */
layui.use(['jquery','table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;

    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsmbpz/zd',
        type: "GET",
        async: false,
        dataType: "json",
        success: function(data) {
            if(data && data.length > 0){
                // 渲染字典项
                $.each(data, function(index,item) {
                    $('#qllx').append('<option value="'+item.DM +'">'+item.MC +'</option>');
                });
            }

            form.render('select');
            // 下拉选择添加删除按钮
            renderSelectClose(form);
        }
    });
    /**
     * 模板sql验证
     * 1.获取到sql数据中输入数据即#{}内字段，
     * 2.验证结果：如果有则输出结果,如果出错则展示错误信息
     */
    $("#check-btn").click(function () {
        var mbsql = $("textarea[name='mbsql']").val();
        if(mbsql.replace(/(^\s*)|(\s*$)/g,"")===""){
            warnMsg("请输入模板sql！");
        }else {
            // 缓存sql数据
            layui.sessionData('sqlData', {
                key: 'data'
                ,value: JSON.stringify(mbsql)
            });

            parent.layer.open({
                type: 2,
                title: '证书模板sql验证',
                anim: -1,
                shadeClose: true,
                maxmin: true,
                shade: false,
                area: ['960px', '450px'],
                offset: 'auto',
                content: ["checkZsmbsql.html", 'yes'],
                end: function () {
                },
            });
        }
    });


    /**
     * 表单数据提交
     * 1、不管新增还是编辑需要保证：年份和区县代码 组合是唯一的
     * 2、前台验证不可靠，在后台服务逻辑中验证
     */
    form.on('submit(submitBtn)', function(data) {
        //传输过程中对sql加密，防止被拦截
        if(!isNullOrEmpty(data.field.mbsql)){
            data.field.mbsql = Base64.encode(data.field.mbsql);
        }
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zsmbpz/sql",
            type: "POST",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (text) {
                if(true == text){
                    saveData(data.field);
                } else {
                    alertMsg("SQL配置不正确，请检查内容！");
                }
            },
            error:function(data){
                var responseText = JSON.parse(data.responseText);
                layer.alert("SQL配置不正确，请检查内容！<br>[" + responseText.msg.substr(0, 130) + "...]", {title: '提示'});
            }
        });

        // 禁止提交后刷新
        return false;
    });

    /**
     * 提交数据
     */
    window.saveData = function(data){
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zsmbpz",
            type: "PUT",
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                if(data && $.isNumeric(data) && data > 0){
                    success();
                } else {
                    fail(data);
                }
            },
            error:function(data){
                fail(JSON.parse(data.responseText));
            }
        });
    }

    /**
     * 编辑时填充数据
     * @param data
     */
    window.setData = function(data){
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        form.val('zsmbform', JSON.parse(JSON.stringify(data)));

        // 编辑状态下不允许修改权利类型
        if(data && data.zsmbid){
            $("#qllx").attr("disabled", "disabled");
            form.render("select");
        }
    };

    /**
     * 关闭弹出页面
     */
    window.closeWin = function(){
        var index=parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };

    /**
     * 提交成功提示
     */
    window.success = function(){
        saveSuccessMsg();
        setTimeout(function(){
            var index = parent.parent.parent.layer.getFrameIndex(window.name);
            parent.parent.parent.layer.close(index);
        },2000);
    }

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function(data){
        var msg = "提交失败，请检查填写内容!";
        if(data && data.code && data.code == 72){
            msg = "提交失败，指定权利类型证书模板配置已经存在！";
        }

        alertMsg(msg);
    }
});