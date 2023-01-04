/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/02/01
 * describe: 编辑证书印制号模板配置处理JS
 */
layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;

    // 获取区县代码字典
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsbhmb/zd/qxdm',
        type: "GET",
        dataType: "json",
        timeout: 10000,
        success: function (data) {
            if (data && data.length > 0) {
                // 渲染字典项
                $.each(data, function (index, item) {
                    $('#qxdm').append('<option value="' + item.XZDM + '">' + item.XZMC + '</option>');
                });
            }
            form.render('select');

            // 设置区县代码下拉选中
            if (qxdm) {
                form.val("yzhform", {"qxdm": qxdm});
            }
        }
    });


    // 获取字典
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsyzh/zd',
        type: "GET",
        async: false,
        dataType: "json",
        success: function(data) {
            if(data && data.zslx){
                $.each(data.zslx, function(index,item) {
                    $('#zslx').append('<option value="'+item.DM +'">' + item.MC + '</option>');
                });
            }

            // 下拉选择添加删除按钮
            renderSelectClose(form);
        }
    });

    /**
     * 表单校验
     */
    form.verify({
        nf : [/^[0-9]*$/, '请输入正确的年份数字'],
        number : [/^[1-9][0-9]*$/, '请输入大于0的整数'],
        lqr : function (lqr) {
            if(isNullOrEmpty(lqr) && $("#lqr_div").is(":visible")){
                return '请设置领取人员！';
            }
        },
        lqbm : function (lqbm) {
            if(isNullOrEmpty(lqbm) && $("#lqbm_div").is(":visible")){
                return '请设置领取部门！';
            }
        }
    });

    /**
     * 表单数据提交
     */
    form.on('submit(submitBtn)', function(data) {
        //这里处理下因为下拉树框数据清除但是隐藏ID值未清理问题
        if(isNullOrEmpty($("input[name='lqbm']").val())){
            data.field.lqbmid = null;
        }
        if(isNullOrEmpty($("input[name='lqr']").val())){
            data.field.lqrid = null;
        }

        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zsyzh",
            type: "PUT",
            data: JSON.stringify(data.field),
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
            error:function($xhr,textStatus,errorThrown){
                fail(JSON.parse($xhr.responseText));
            }
        });

        // 禁止提交后刷新
        return false;
    });

    // 默认隐藏印制号领取方式配置项
    $("#lqbm_div").hide();
    $("#lqr_div").hide();

    /**
     * 编辑时填充数据
     * @param data
     */
    var qxdm, lqfs;
    window.setData = function(data){
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        form.val('yzhform', JSON.parse(JSON.stringify(data)));
        qxdm = data.qxdm;

        // 获取印制号领取方式
        $.ajax({
            url: '/realestate-inquiry-ui/rest/v1.0/zsyzh/lqfs',
            type: "GET",
            dataType: "json",
            success: function(text) {
                lqfs = text;
                if(lqfs && data.qxdm){
                    for(var key in lqfs){
                        if(key == data.qxdm){
                            if('0' == lqfs[key]){        //按照部门领取
                                $("#lqbm_div").show();
                                $("#lqr_div").hide();
                            }else if('1' == lqfs[key]){  //按照人员领取
                                $("#lqr_div").show();
                                $("#lqbm_div").hide();
                            }else{                       //默认按照区县代码
                                $("#lqr_div").hide();
                                $("#lqbm_div").hide();
                            }
                        }
                    }
                }
            }
        });
    };

    // 根据选择的区划设置对应的印制号领取方式配置项
    form.on('select(qxdm)', function(data){
        $("input[name='lqbmid']").val(null);
        $("input[name='lqbm']").val(null);
        $("input[name='lqrid']").val(null);
        $("input[name='lqr']").val(null);

        if(lqfs && data && data.value){
            for(var key in lqfs){
                if(key == data.value){
                    if('0' == lqfs[key]){        //按照部门领取
                        $("#lqbm_div").show();
                        $("#lqr_div").hide();
                    }else if('1' == lqfs[key]){  //按照人员领取
                        $("#lqr_div").show();
                        $("#lqbm_div").hide();
                    }else{                       //默认按照区县代码
                        $("#lqr_div").hide();
                        $("#lqbm_div").hide();
                    }
                }
            }
        }
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
            msg = "提交失败，指定证书印制号已经存在！";
        }

        alertMsg(msg, {title: '提示'});
    }
});