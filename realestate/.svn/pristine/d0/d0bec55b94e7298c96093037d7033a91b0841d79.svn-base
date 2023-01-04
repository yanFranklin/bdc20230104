layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
var zdList = {};
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

        // 载入字典
        loadZdList();
        renderData();
        /**
         * 正式生成证书时，后台查询证书信息展示
         */
        function renderData() {
            var url = "/realestate-natural-ui/rest/v1.0/ywxx/" + processInsId ;
            //获取数据
            $.ajax({
                url: url,
                contentType: "application/json;charset=utf-8",
                type: "GET",
                dataType: "json",
                success: function (data) {
                    console.log(zdList);
                    if (data) {
                        console.log(data.zrzyXm.djlx);
                        $('#djdymc').text(data.zrzyXm.djdymc);
                        $('#zrzydjdyh').text(data.zrzyXm.zrzydjdyh);
                        if(data.zrzyDjdy.djdylx){
                            zdList.zrzydjdylx.forEach(function(item, index) {
                                if (item.DM == data.zrzyDjdy.djdylx) {
                                    $('#djdylx').text(item.MC);
                                }
                            });
                        }
                        if(data.zrzyXm.djjg){
                            $('#djjg').text(data.zrzyXm.djjg);
                        }
                        // form.val('form', data.zrzyXm);
                    }
                    // 关闭加载
                    //layer.close(index);
                }
            });
        }

        /**
         * 获取字典
         */
        function loadZdList() {
            $.ajax({
                url: '/realestate-natural-ui/rest/v1.0/ywxx/zd',
                type: 'GET',
                dataType: 'json',
                async: false,
                success: function (data) {
                    // console.log('zdList:');
                    // console.log(data);
                    if (data) {
                        zdList = data;
                    }
                },
                error: function (xhr, status, error) {
                }
            });
        }

        $('#next').on('click', function() {
            var index = window.location.href.indexOf('?');
            var param = window.location.href.substr(index);

            window.open('/realestate-natural-ui/view/ywxx/djbQszkxx.html' + param, '_self');
        });

    });
})
;