/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/02/01
 * describe: 作废证书印制号模板配置处理JS
 */
layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;

    /**
     * 获取所有字典
     */
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsyzh/zd',
        type: "GET",
        async: false,
        dataType: "json",
        timeout: 10000,
        success: function (data) {
            zdData = data;

            if (data) {
                if (data.yzhzfyy) {
                    $.each(data.yzhzfyy, function (index, item) {
                        $('#zfyy').append('<option value="' + item.DM + '">' + item.MC + '</option>');
                    });
                }
                form.render('select');

                // 下拉选择添加删除按钮
                renderSelectClose(form);
            }
        }
    });


    form.on('submit(submitBtn)', function(data) {
        var qzysxlh = $("input[name=qzysxlh]").val();

        var deleteIndex = layer.open({
            type: 1,
            title: '确认作废',
            area: ['320px'],
            skin: 'bdc-small-tips',
            content: '确定要作废当前证书印制号：' + qzysxlh + '？',
            btn: ['确定','取消'],
            btnAlign: 'c',
            yes: function(){
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/zsyzh/zf",
                    type: "DELETE",
                    data: JSON.stringify(data.field),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        saveSuccessMsg();
                        setTimeout(function(){
                            console.log("关闭父窗口！");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        },2000);
                    },
                    error:function($xhr,textStatus,errorThrown){
                        saveFailMsg();
                    }
                });
                layer.close(deleteIndex);
            },
            btn2: function(){
                //取消
            }
        });

        // 禁止提交后刷新
        return false;
    });

    /**
     * 编辑时填充数据
     * @param data
     */
    window.setData = function(data){
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        form.val('yzhform', JSON.parse(JSON.stringify(data)));
    };

    /**
     * 关闭弹出页面
     */
    window.closeWin = function(){
        var index=parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };
});