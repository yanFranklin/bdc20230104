/**
 * @description 证书预览页面js
 *
 */

layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer', 'carousel'], function () {
    // 轮播组件
    var carousel = layui.carousel;
    //建造组件实例
    carousel.render({
        elem: '#zsView'
        , width: '1349px' //设置容器宽度
        , height: '890px'
        , arrow: 'always' //始终显示箭头
        , autoplay: false
    });

    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;

    $(function () {

        // 查询参数
        var zsid = $.getUrlParam("zsid");

        var zslx;
        var nf;
        var qxdm;
        // 当前证书的已保存的印刷序列号
        var zsQzysxlh;
        var qjgldm ="";
        qjgldm = $.getUrlParam("qjgldm")

        // loading加载
        var index = layer.load(2);

        var screenH = document.documentElement.clientHeight;
        $(".content-main").css({'min-height': screenH - 70});

        //获取数据
        $.ajax({
            url: "/realestate-natural-ui/rest/v1.0/zsxx/" + zsid,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data) {
                    // 为后面印制号的参数赋值
                    zslx = data.zslx;
                    nf = data.nf;
                    qxdm = data.qxdm;
                    zsQzysxlh = data.qzysxlh;
                    qjgldm =data.qjgldm;
                    // 为基本信息赋值
                    form.val('coverform', data);
                    form.val('form', data);
                    // 生成二维码
                   // generateQrCode(data.ewmnr);
                }
                // 关闭加载
                layer.close(index);
            }
        });
    })
});