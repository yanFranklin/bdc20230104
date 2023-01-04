/**
 * 证明预览JS代码
 */

layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;

    $(function () {
        // 查询参数
        var zsid = $.getUrlParam("zsid");
        var zslx;

        // loading加载
        var index = layer.load(2);

        //获取数据
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/zsxx/" + zsid,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data) {
                    zsid = data.zsid;
                    zslx = data.zslx;
                    // 为基本信息赋值
                    form.val('form', data);
                    // 生成二维码
                    generateQrCode(data.ewmnr);
                }
                // 关闭加载
                layer.close(index);
            }
        });
    });
});