layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;

    var BASE_URL = '/realestate-inquiry-ui';

    // 新增或者编辑标识
    var action = $.getUrlParam('action');
    // 编辑时传递的证书编号模板ID
    var czryid = $.getUrlParam('czryid');

    /**
     * 表单数据提交
     */
    form.on('submit(submitBtn)', function (data) {
        var czryid = $('#czryid').val();

        $.ajax({
            url: BASE_URL + "/bdcBmd",
            type: (czryid != null && czryid != '') ? "POST" : "PUT",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                if (data && $.isNumeric(data) && data > 0) {
                    success();
                } else {
                    fail(data);
                }
            },
            error: function () {
                fail();
            }
        });
        // 禁止提交后刷新
        return false;
    });


    /**
     * 编辑时填充数据
     * @param data
     */
    window.setData = function (data) {
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        form.val('bmdForm', JSON.parse(JSON.stringify(data)));
    };

    /**
     * 关闭弹出页面
     */
    window.closeWin = function () {
         var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
     };

    window.closeParentWindow = function () {
        var index = parent.parent.layer.getFrameIndex(window.name);
        parent.parent.layer.close(index);
    };

    /**
     * 提交成功提示
     */
    window.success = function () {
        layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功');
        setTimeout(function(){
            closeParentWindow();
        }, 500);
    };

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function () {
        layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">提交失败，请重试');
    }
});