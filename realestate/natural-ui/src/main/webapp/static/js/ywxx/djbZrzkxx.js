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

        // 载入字典
        loadZdList();

        // 渲染数据
        renderData();



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
                    if (data) {
                        zdList = data;
                    }
                },
                error: function (xhr, status, error) {
                }
            });
        }

        /**
         * 渲染数据
         */
        function renderData() {
            var url = "/realestate-natural-ui/rest/v1.0/ywxx/zrzkxx/" + processInsId ;

            //获取数据
            $.ajax({
                url: url,
                contentType: "application/json;charset=utf-8",
                type: "GET",
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data) {
                        data.zdList = zdList;

                        // 草原类型代码转为名称
                        data.zrzyCyDOList.forEach(function(it, i){
                            data.zdList.cdlx.forEach(function(item, index){
                                if (item.DM == it.cdlx) {
                                    it.cdlx = item.MC;
                                }
                            });
                        });

                        // 矿产组合代码转为名称
                        data.zrzyTmclkczyDOList.forEach(function(it, i){
                            data.zdList.kczhfs.forEach(function(item, index){
                                if (item.DM == it.gtkckzzyl) {
                                    it.gtkckzzyl = item.MC;
                                }
                            });
                        });
                        if(data.zrzyDjdy.djdyzmj){
                            data.zrzyDjdy.djdyzmj = data.zrzyDjdy.djdyzmj.toFixed(2);
                        }else {
                            data.zrzyDjdy.djdyzmj = "0.00";
                        }

                        if(data.zrzyDjdy.dynzrzyzmj){
                            data.zrzyDjdy.dynzrzyzmj = data.zrzyDjdy.dynzrzyzmj.toFixed(2);
                        }else {
                            data.zrzyDjdy.dynzrzyzmj = "0.00";
                        }
                        if(data.zrzyDjdy.szymj){
                            data.zrzyDjdy.szymj = data.zrzyDjdy.szymj.toFixed(2);
                        }else {
                            data.zrzyDjdy.szymj = "0.00";
                        }
                        if(data.zrzyDjdy.fzrzyzmj){
                            data.zrzyDjdy.fzrzyzmj = data.zrzyDjdy.fzrzyzmj.toFixed(2);
                        }else {
                            data.zrzyDjdy.fzrzyzmj = "0.00";
                        }
                        if(data.zrzyDjdy.sdmj){
                            data.zrzyDjdy.sdmj = data.zrzyDjdy.sdmj.toFixed(2);
                        }else {
                            data.zrzyDjdy.sdmj = "0.00";
                        }

                        if(data.zrzyDjdy.slmj){
                            data.zrzyDjdy.slmj = data.zrzyDjdy.slmj.toFixed(2);
                        }else {
                            data.zrzyDjdy.slmj = "0.00";
                        }

                        if(data.zrzyDjdy.cymj){
                            data.zrzyDjdy.cymj = data.zrzyDjdy.cymj.toFixed(2);
                        }else {
                            data.zrzyDjdy.cymj = "0.00";
                        }

                        if(data.zrzyDjdy.hdmj){
                            data.zrzyDjdy.hdmj = data.zrzyDjdy.hdmj.toFixed(2);
                        }else {
                            data.zrzyDjdy.hdmj = "0.00";
                        }

                        if(data.zrzyDjdy.qtmj){
                            data.zrzyDjdy.qtmj = data.zrzyDjdy.qtmj.toFixed(2);
                        }else {
                            data.zrzyDjdy.qtmj = "0.00";
                        }


                        //基础信息
                        form.val('form', data.zrzyDjdy);

                        //海域
                        var getDataTpl = hyDataTpl.innerHTML;
                        laytpl(getDataTpl).render(data, function(html){
                            $("#hytitle").after(html);
                        });

                        if (data.zrzyHyDOList.length === 0) {
                            $("#hynum").attr("rowspan",2);
                        } else {
                            $("#hynum").attr("rowspan",data.zrzyHyDOList.length + 1);
                        }

                        //无居民海岛
                        getDataTpl = wjmDataTpl.innerHTML;
                        laytpl(getDataTpl).render(data, function(html){
                            $("#wjmTitle").after(html);
                        });

                        if (data.zrzyWjmhdDOList.length === 0) {
                            $("#wjmnum").attr("rowspan",2);
                        } else {
                            $("#wjmnum").attr("rowspan",data.zrzyHyDOList.length + 1);
                        }

                        //水资源
                        if (data.zrzySzyDOList) {
                            data.zrzySzyDOList.forEach(function(it, i) {
                                if(it.smmj){
                                    it.smmj = it.smmj.toFixed(2);
                                }
                            });
                        }
                        getDataTpl = syDataTpl.innerHTML;
                        laytpl(getDataTpl).render(data, function(html){
                            $("#syTitle").after(html);
                        });

                        if (data.zrzySzyDOList.length === 0) {
                            $("#syNum").attr("rowspan",3);
                        } else {
                            $("#syNum").attr("rowspan",data.zrzySzyDOList.length + 2);
                        }

                        //湿地
                        if (data.zrzySdDOList) {
                            data.zrzySdDOList.forEach(function(it, i) {
                                if(it.zmj){
                                    it.zmj = it.zmj.toFixed(2);
                                }
                                if(it.zbmj){
                                    it.zbmj = it.zbmj.toFixed(2);
                                }
                            });
                        }
                        getDataTpl = sdDataTpl.innerHTML;
                        laytpl(getDataTpl).render(data, function(html){
                            $("#sdTitle").after(html);
                        });

                        if (data.zrzySdDOList.length === 0) {
                            $("#sdNum").attr("rowspan",2);
                        } else {
                            $("#sdNum").attr("rowspan",data.zrzySdDOList.length + 1);
                        }

                        //森林
                        if (data.zrzySlDOList) {
                            data.zrzySlDOList.forEach(function(it, i) {
                                if(it.mj){
                                    it.mj = it.mj.toFixed(2);
                                }
                                if(it.zxjl){
                                    it.zxjl = Number(it.zxjl).toFixed(2);
                                }
                            });
                        }
                        getDataTpl = slDataTpl.innerHTML;
                        laytpl(getDataTpl).render(data, function(html){
                            $("#slTitle").after(html);
                        });

                        if (data.zrzySlDOList.length === 0) {
                            $("#slNum").attr("rowspan",2);
                        } else {
                            $("#slNum").attr("rowspan",data.zrzySlDOList.length + 1);
                        }

                        //草原
                        if (data.zrzyCyDOList) {
                            data.zrzyCyDOList.forEach(function(it, i) {
                                if(it.mj){
                                    it.mj = it.mj.toFixed(2);
                                }
                            });
                        }
                        getDataTpl = cyDataTpl.innerHTML;
                        laytpl(getDataTpl).render(data, function(html){
                            $("#cyTitle").after(html);
                        });


                        if (data.zrzyCyDOList.length === 0) {
                            $("#cynum").attr("rowspan",2);
                        } else {
                            $("#cynum").attr("rowspan",data.zrzyCyDOList.length + 1);
                        }


                        //荒地
                        getDataTpl = hdDataTpl.innerHTML;
                        laytpl(getDataTpl).render(data, function(html){
                            $("#hdTitle").after(html);
                        });

                        if (data.zrzyHdDOList.length === 0) {
                            $("#hdNum").attr("rowspan",2);
                        } else {
                            $("#hdNum").attr("rowspan",data.zrzyHdDOList.length + 1);
                        }

                        //探明储量的矿产资源
                        getDataTpl = kcDataTpl.innerHTML;
                        laytpl(getDataTpl).render(data, function(html){
                            $("#kcTitle").after(html);
                        });

                        if (data.zrzyTmclkczyDOList.length === 0) {
                            $("#kcNum").attr("rowspan",3);
                        } else {
                            $("#kcNum").attr("rowspan",data.zrzyTmclkczyDOList.length + 2);
                        }


                        form.render();
                    }
                    // 关闭加载
                    layer.close(index);

                    $.fn.autoHeight = function(){
                        function autoHeight(elem){
                            elem.style.height = 'auto';
                            elem.scrollTop = 0; //防抖动
                            elem.style.height = elem.scrollHeight + 'px';
                        }
                        this.each(function(){
                            autoHeight(this);
                            $(this).on('keyup', function(){
                                autoHeight(this);
                            });
                        });
                    }
                    $('textarea[autoHeight]').autoHeight();
                }
            });
        }

        //监听提交
        form.on('submit(submitBtn)', function (data) {

        });

        $('#prev').on('click', function() {
            var index = window.location.href.indexOf('?');
            var param = window.location.href.substr(index);

            window.open('/realestate-natural-ui/view/ywxx/djbQszkxx.html' + param, '_self');
        });

        $('#next').on('click', function() {
            var index = window.location.href.indexOf('?');
            var param = window.location.href.substr(index);

            window.open('/realestate-natural-ui/view/ywxx/djbGlxx.html' + param, '_self');
        });
    });
})
;