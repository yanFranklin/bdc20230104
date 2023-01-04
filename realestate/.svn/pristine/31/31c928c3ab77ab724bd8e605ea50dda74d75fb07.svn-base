/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 作废发票号
 */
layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;

    /**
     * 表单数据提交
     */
    form.on('submit(submitBtn)', function(data) {
        var fph = $("input[name=fph]").val();

        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            skin: 'bdc-small-tips',
            content: '确定要作废当前证书发票号：' + fph + '？',
            btn: ['确定','取消'],
            btnAlign: 'c',
            yes: function(){
                $.ajax({
                    url: getContextPath() + "/rest/v1.0/fph/zf",
                    type: "DELETE",
                    data: JSON.stringify(data.field),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        saveSuccessMsg();
                        setTimeout(function(){
                            var index = parent.parent.parent.layer.getFrameIndex(window.name);
                            parent.parent.parent.layer.close(index);
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