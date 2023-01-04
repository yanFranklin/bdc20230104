/**
 * Created by Administrator on 2018/12/19.
 */
layui.use(['jquery'], function () {
    var $ = layui.$;

    $(function () {
        var $cnotentTitle = $('.content-title-box');

        $(window).on('scroll', function () {
            if ($(this).scrollTop() > 85) {
                $cnotentTitle.css('top', '0');
            } else if ($(this).scrollTop() <= 85) {
                $cnotentTitle.css('top', '15px');
            }
        });

        // 设置二维码
        var ewmnr = "皖(2018)合肥市不动产权第0000121号";
        $("#qrcode").qrcode({
            render: "canvas", // 渲染方式有table方式和canvas方式
            width: 88,   //默认宽度
            height: 88, //默认高度
            text: utf16to8(ewmnr), //二维码内容
            typeNumber: -1,   //计算模式一般默认为-1
            correctLevel: 2, //二维码纠错级别
            // background: "#ffffff",  //背景颜色
            // foreground: "#1E90FF"  //二维码颜色
        });

        function utf16to8(str) {
            var out, i, len, c;
            out = "";
            len = str.length;
            for (i = 0; i < len; i++) {
                c = str.charCodeAt(i);
                if ((c >= 0x0001) && (c <= 0x007F)) {
                    out += str.charAt(i);
                } else if (c > 0x07FF) {
                    out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                    out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                    out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
                } else {
                    out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                    out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
                }
            }
            return out;
        }
    });

});