/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/01/31
 * describe: 新增或者编辑证书印制号模板配置处理JS
 */
layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;
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
            form.render('select');
            // 下拉选择添加删除按钮
            renderSelectClose(form);
        }
    });
    $("input[name='bhws']").val(11);

    /**
     * 表单校验
     */
    form.verify({
        nf : [/^[0-9]*$/, '请输入正确的年份数字'],
        number : [/^[1-9][0-9]*$/, '请输入大于0的整数'],
        bhws : function(value){
            if(value < 11){
                return '印制号位数一般至少11位';
            }
        },
        qsbh : function(qsbh){
            var jsbh = $("input[name='jsbh']").val();
            if(qsbh && jsbh && parseInt(qsbh) > parseInt(jsbh)){
                return '起始编号不能大于结束编号';
            }

            // 一次生成印制号数量控制在10万
            if(parseInt(jsbh) - parseInt(qsbh) + 1 > 100000){
                return '单次生成印制号数量不能超过10万，请修改起始编号、结束编号！';
            }
        },
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

    //提取
    form.on('submit(submitBtn)', function(data) {
        // data.field.lqbmid = $("input[name='lqbm']").val();
        var loadIndex = loadLayer();
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/extractData",
            type: "POST",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data.code != -1){
                    //closeWin();
                    success();
                } else {
                    alertMsg(data.msg);
                }
            },
            error:function(data){
                fail(data.msg);
            },
            complete: function(){
                layer.close(loadIndex);
            }
        });

        // 禁止提交后刷新
        return false;
    });

    //领取
    form.on('submit(submitBtnLq)', function(data) {
        // data.field.lqbmid = $("input[name='lqbm']").val();
        var loadIndex = loadLayer();
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/extractData",
            type: "POST",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data.code != -1){
                    //closeWin();
                    success();
                } else {
                    alertMsg(data.msg);
                }
            },
            error:function(data){
                fail(data.msg);
            },
            complete: function(){
                layer.close(loadIndex);
            }
        });

        // 禁止提交后刷新
        return false;
    });

    //撤回
    form.on('submit(submitBtnCx)', function(data) {
        var loadIndex = loadLayer();
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/extractData",
            type: "POST",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data.code != -1){
                    //closeWin();
                    success();
                } else {
                    alertMsg(data.msg);
                }
            },
            error:function(data){
                fail(data.msg);
            },
            complete: function(){
                layer.close(loadIndex);
            }
        });

        // 禁止提交后刷新
        return false;
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
            /*      var index = parent.parent.parent.layer.getFrameIndex(window.name);
                  parent.parent.parent.layer.close(index);*/
            parent.layer.closeAll();
        },2000);
    }

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function(data){
        var msg = "提交失败，请检查填写内容!";
        if(data && data.code && data.code == 72){
            msg = "提交失败，指定区间的部分印制号已经存在！";
        }

        alertMsg(msg);
    }
});