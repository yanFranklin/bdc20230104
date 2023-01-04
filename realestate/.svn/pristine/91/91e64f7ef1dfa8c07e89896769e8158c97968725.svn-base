/**
 * @version 1.0, 2020/10/22
 * @description 证书页面js
 */
var zdList = {};
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

        // loading加载
        var index = layer.load(2);

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

        // 加载字典
        loadZdList();

        // 渲染数据
        renderData();


        /**
         * 渲染数据
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
                    if (data) {
                        if(data.zrzyDjdy.dlxszt != null && (data.zrzyDjdy.qlxsfs == 2)){
                            data.zrzyDjdy.dlxsnr = data.zrzyDjdy.xsnr;
                            data.zrzyDjdy.xsnr = "";
                        }

                        // 所有权人代码转为名称
                        var syqzt = data.zrzyDjdy.syqzt;
                        zdList.syqztlx.forEach(function(item, index){
                            if (item.DM == syqzt) {
                                data.zrzyDjdy.syqzt = item.MC;
                            }
                        });

                        if(data.zrzyDjdy.djdyzmj){
                            data.zrzyDjdy.djdyzmj = data.zrzyDjdy.djdyzmj.toFixed(2);
                        }else {
                            data.zrzyDjdy.djdyzmj = "0.00";
                        }
                        if(data.zrzyDjdy.jtmj){
                            data.zrzyDjdy.jtmj = data.zrzyDjdy.jtmj.toFixed(2);
                        }else {
                            data.zrzyDjdy.jtmj = "0.00";
                        }
                        if(data.zrzyDjdy.zyqmj){
                            data.zrzyDjdy.zyqmj = data.zrzyDjdy.zyqmj.toFixed(2);
                        }else {
                            data.zrzyDjdy.zyqmj = "0.00";
                        }

                        // 权利行使方式代码转为名称
                        // var qlxsfszj = data.zrzyDjdy.qlxsfszj;
                        // zdList.qlxsfs.forEach(function(item, index){
                        //     if (item.DM == qlxsfszj) {
                        //         data.zrzyDjdy.qlxsfszj = item.MC;
                        //     }
                        // });

                        // 根据权利行使方式勾选
                        var qlxsfs = data.zrzyDjdy.qlxsfs;
                        if (qlxsfs == 1) {
                            // 直接行使
                            $('#qlxsfszj').val('直接履行（√）');
                        } else {
                            // 间接行使
                            $('#qlxsfsjj').val('代理履行（√）');
                        }

                        // 登记单元类型代码转为名称
                        var djlx = data.zrzyXm.djlx;
                        zdList.djlx.forEach(function(item, index){
                            if (item.DM == djlx) {
                                data.zrzyXm.djlx = item.MC;
                            }
                        });

                        data.zrzyDjdy.zl = "坐落：" + data.zrzyDjdy.zl
                            + "。 \n四至：北至：" +  data.zrzyDjdy.dyszb
                            + "；东至：" +  data.zrzyDjdy.dyszd
                            + "；南至：" +  data.zrzyDjdy.dyszn
                            + "；西至：" +  data.zrzyDjdy.dyszx+ "。";
                        data.zrzyXm.zl = data.zrzyDjdy.zl;
                        form.val('form', data.zrzyDjdy);
                        form.val('form', data.zrzyXm);
                    }
                    // 关闭加载
                    layer.close(index);
                }
            });
        }

        function loadZdList() {
            $.ajax({
                url: '/realestate-natural-ui/rest/v1.0/ywxx/zd',
                type: 'GET',
                dataType: 'json',
                async: false,
                success: function (data) {
                    console.log('zdList:');
                    console.log(data);
                    if (data) {
                        zdList = data;
                    }
                },
                error: function (xhr, status, error) {
                }
            });
        }

        //监听提交
        form.on('submit(submitBtn)', function (data) {

        });

        $('#prev').on('click', function() {
            var index = window.location.href.indexOf('?');
            var param = window.location.href.substr(index);

            window.open('/realestate-natural-ui/view/ywxx/djbfm.html' + param, '_self');
        });

        $('#next').on('click', function() {
            var index = window.location.href.indexOf('?');
            var param = window.location.href.substr(index);

            window.open('/realestate-natural-ui/view/ywxx/djbZrzkxx.html' + param, '_self');
        });
    });
})
;