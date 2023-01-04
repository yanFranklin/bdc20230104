/**
 * Created by YuanPengpeng on 2019/6/21.
 */
var zxmcParam = decodeURI($.getUrlParam("zxmc"));
layui.use(['jquery', 'laytpl', 'carousel'], function () {
    var $ = layui.$,
        carousel = layui.carousel,
        laytpl = layui.laytpl;

    $(function () {
        var zxmc;
        if(zxmcParam && zxmcParam != "null"){//参数传了 则读取参数的，不传则根据配置查询数据库
            zxmc = zxmcParam;
        }else{
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhxp/zxmc",
                dataType: "text",
                cache: false,
                async: false,
                success: function (data) {
                    zxmc = data;
                }
            });
        }
        if (zxmc !== undefined && zxmc !== '') {
            var title=$("#smallTitle").html();
            if(title!=zxmc){
                $("#smallTitle").html(zxmc);
            }
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhxp/dqhj/" + zxmc,
                dataType: "json",
                cache: false,
                async: false,
                success: function (data) {
                    zhxpdqhj = data;
                }
            });

            var getTpl = zhxpdqhjmb.innerHTML
                , view = document.getElementById('zhxpdqhj');
            laytpl(getTpl).render(zhxpdqhj, function (html) {
                view.innerHTML = html;
            });

            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhxp/ddhj/" + zxmc,
                dataType: "json",
                cache: false,
                async: false,
                success: function (data) {
                    zhxpddhj = data;
                }
            });

            var getTpl = zhxpddhjmb.innerHTML
                , view = document.getElementById('zhxpddhj');
            laytpl(getTpl).render(zhxpddhj, function (html) {
                view.innerHTML = html;
            });
        }

        var outerinterval = 5000;
        //实时显示时间
        showTime();
        setInterval(showTime, 30000);
        //当前呼叫轮播
        carousel.render({
            elem: '#topCarousel'
            , width: '100%'
            , height: '100%'
            , arrow: 'none'
            , indicator: 'none'
            , interval: outerinterval
        });

        //等待叫号轮播
        carousel.render({
            elem: '#centerCarousel'
            , width: '100%'
            , height: '100%'
            , arrow: 'none'
            , indicator: 'none'
            , interval: outerinterval
        });

        setTimeout(function () {
            location.reload();
        }, 20000);
    });
});