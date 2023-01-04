/**
 * @description 证书预览页面js
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


        // loading加载
        var index = layer.load(2);

        var screenH = document.documentElement.clientHeight;
        $(".content-main").css({'min-height': screenH - 70});

        //获取数据
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/zsxx/" + zsid,
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
                    // 为基本信息赋值
                    form.val('coverform', data);

                    if(!isNullOrEmpty(data.qllx) && TDCBJYQ_QLLX == data.qllx) {
                        // 列表信息：列表展示，采用动态读取获取；数据库附记中的地块信息展示时候动态截取掉
                        if(!isNullOrEmpty(data.fj)) {
                            var start = data.fj.indexOf("地块名称");
                            var end = data.fj.indexOf(";;");
                            if (end == -1) {
                                data.fj = data.fj.substring(0, start)
                            } else {
                                if (start >= 0 && start < end) {
                                    data.fj = data.fj.substring(0, start) + data.fj.substring(end + 2);
                                }
                            }
                        }
                    }
                    // if(!isNullOrEmpty(data.qllx) && NCBDCQQDJ_QLLX == data.qllx) {
                    //     // 列表信息：列表展示，采用动态读取获取；数据库附记中的地块信息展示时候动态截取掉
                    //     if(!isNullOrEmpty(data.fj)) {
                    //         var start = data.fj.indexOf("姓名");
                    //         var end = data.fj.indexOf(";;");
                    //         if(end == -1){
                    //             data.fj = data.fj.substring(0, start)
                    //         }else {
                    //             if(start >= 0 && start < end) {
                    //                 data.fj = data.fj.substring(0, start) + data.fj.substring(end+2);
                    //             }
                    //         }
                    //
                    //     }
                    // }
                    form.val('form', data);
                    // 动态展示地块列表
                    showDklb(data, "", 5, zsid);
                    //展示家庭成员
                    // showJtcylb(data, "",zsid);
                    // 生成二维码
                    generateQrCode(data.ewmnr);
                }
                // 关闭加载
                layer.close(index);
            }
        });

        // 获取宗地图base64字符串
        getZdtBase64(zsid);
        // 获取户室图base64字符串
        getHstBase64(zsid);
    })
});