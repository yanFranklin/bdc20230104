/**
 * @version 1.0, 2020/10/22
 * @description 证书页面js
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer', 'response'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;
    var response = layui.response;
    var laytpl = layui.laytpl;
    $(function () {


        var processInsId = $.getUrlParam('processInsId');
        // 表单ID
        var formStateId = $.getUrlParam('formStateId');

        var screenH = document.documentElement.clientHeight;
        $(".content-main").css({'min-height': screenH - 70});

        // 滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-nav-container');

        function defaultStyle() {
            if ($cnotentTitle.length == 1) {
                $('.bdc-form-div').css('padding-top', '84px');
            }
            if ($(window).scrollTop() > 85) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '69px');
            } else if ($(window).scrollTop() <= 85) {
                $cnotentTitle.css('top', '15px');
                $navContainer.css('top', '84px');
            }
        }

        $(window).resize(function () {
            defaultStyle();
        });

        $(window).on('scroll', function () {
            if ($(this).scrollTop() > 85) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '69px');
            } else if ($(this).scrollTop() <= 85) {
                $cnotentTitle.css('top', '15px');
                $navContainer.css('top', '84px');
            }
        });

        //监听提交
        form.on('submit(submitBtn)', function (data) {

        });

        $('#prev').on('click', function() {
            var index = window.location.href.indexOf('?');
            var param = window.location.href.substr(index);

            window.open('/realestate-natural-ui/view/ywxx/djbGlxx.html' + param, '_self');
        });


        // 渲染数据
        renderData();


        /**
         * 渲染数据
         */
        function renderData() {
            var url = "/realestate-natural-ui/rest/v1.0/ywxx/ft/" + processInsId ;
            $("#ft").attr("src",url)
        }
    });
})
;