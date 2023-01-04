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
        // 表单ID
        var formStateId = $.getUrlParam('formStateId');


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
                        console.log(data);
                        console.log(zdList);
                        // 不动产权利类型字典转换
                        if(data.zrzyBdcqlglxxList) {
                            data.zrzyBdcqlglxxList.forEach(function (it, i) {
                                zdList.qllx.forEach(function (item, index) {
                                    if (it.qllx == item.DM) {
                                        it.qllx = item.MC;
                                    }
                                });
                            });

                            // 截取登记时间
                            // djsj
                            data.zrzyBdcqlglxxList.forEach(function(it, i) {
                                it.djsj = getYearMonthDay(it.djsj);
                            });
                        }

                        // 截取登记时间
                        // djsj
                        if(data.zrzyGggzglxxList) {
                            data.zrzyGggzglxxList.forEach(function (it, i) {
                                it.gdsdsj = getYearMonthDay(it.gdsdsj);
                            });

                            //公共管制关联信息
                            if (data.zrzyGggzglxxList) {
                                data.zrzyGggzglxxList.forEach(function (it, i) {
                                    if (it.mj) {
                                        it.mj = it.mj.toFixed(2);
                                    }
                                });
                            }
                        }
                        var getDataTpl = gzDataTpl.innerHTML
                            ,dataView = document.getElementById('gzContentView');
                        laytpl(getDataTpl).render(data, function(html){
                            $("#gzContentView").after(html);
                        });

                        //bdc
                        getDataTpl = bdcDataTpl.innerHTML;
                        dataView = document.getElementById('bdcContentView');
                        laytpl(getDataTpl).render(data, function(html){

                            $("#bdcContentView").after(html);
                        });

                        //矿业权关联信息
                        getDataTpl = ckDataTpl.innerHTML;
                        dataView = document.getElementById('ckContentView');
                        laytpl(getDataTpl).render(data, function(html){

                            $("#ckContentView").after(html);
                        });

                        //取水、排污权关联信息
                        getDataTpl = qsDataTpl.innerHTML;
                        dataView = document.getElementById('qsContentView');
                        laytpl(getDataTpl).render(data, function(html){

                            $("#qsContentView").after(html);
                        });

                        //排污关联信息
                        getDataTpl = pwDataTpl.innerHTML;
                        dataView = document.getElementById('pwContentView');
                        laytpl(getDataTpl).render(data, function(html){

                            $("#pwContentView").after(html);
                        });

                        //附记
                        $("#hd").val(data.zrzyXm.bz);
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

        // 截取时间到年月日
        function getYearMonthDay(time) {
            if (!isNullOrEmpty(time)) {
                var index = time.indexOf(' ');
                return time.substring(0, index);
            }

        }

        //监听提交
        form.on('submit(submitBtn)', function (data) {

        });

        $('#prev').on('click', function() {
            var index = window.location.href.indexOf('?');
            var param = window.location.href.substr(index);

            window.open('/realestate-natural-ui/view/ywxx/djbZrzkxx.html' + param, '_self');
        });

        $('#next').on('click', function() {
            var index = window.location.href.indexOf('?');
            var param = window.location.href.substr(index);

            window.open('/realestate-natural-ui/view/ywxx/djbFt.html' + param, '_self');
        });
    });
})
;