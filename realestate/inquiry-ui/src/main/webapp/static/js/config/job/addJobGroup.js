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

    /**
     * 表单数据提交
     */
    form.on('submit(submitBtn)', function(data) {
        //这里处理下因为下拉树框数据清除但是隐藏ID值未清理问题
        var loadIndex = loadLayer();
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zsyzh",
            type: "POST",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                if(data && $.isNumeric(data) && data > 0){
                    //closeWin();
                    success();
                } else {
                    fail(data);
                }
            },
            error:function($xhr,textStatus,errorThrown){
                fail(JSON.parse($xhr.responseText));
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