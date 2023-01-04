layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    var element = layui.element;
    laytpl = layui.laytpl;
    var layer = layui.layer;

    $(function() {

        addModel();
        //加载组合规则验证结果信息
        setTimeout(function () {
            try {
                $.when(generateNav(),generateGlzgz(),generateLcpz()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
                return
            }
        }, 50);

        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-nav-container');
        function defaultStyle() {
            if($cnotentTitle.length == 1){
                $('.bdc-form-div').css('padding-top','68px');
            }
            if($(window).scrollTop() > 85){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','58px');
            }else if($(window).scrollTop() <= 85){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
        }

        $(window).resize(function(){
            defaultStyle();
        });

        var scrollIndex = 0;
        var slickLength;
        $(window).on('scroll',function () {

            if($(this).scrollTop() > 85){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','58px');
            }else if($(this).scrollTop() <= 85){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
            // 为页面添加页面滚动监听事件
            var wst =  $(window).scrollTop();
            scrollIndex++;
            if(scrollIndex == 1){
                slickLength = $(document).find('.slick-slide').length - $('.slick-cloned').length;
            }
            // 超过8个，需要左右切换的情况
            // slickIndex的值是一屏展示的slick的个数8，
            var slickIndex = 8;
            var $slickNow = $($('.slick .slick-slide')[8]);
            for (i = 1; i <= slickLength; i++){
                if($("#slick"+i).offset().top - 108 <= wst){
                    slickIndex = 8 + i;
                    $('.slick .slick-slide').removeClass('bdc-this');
                    $slickNow = $($('.slick .slick-slide')[slickIndex]);
                    $slickNow.addClass('bdc-this');
                }
                if(wst == 0){
                    $('.slick .slick-slide').removeClass('bdc-this');
                    $slickNow = $($('.slick .slick-slide')[8]);
                    $slickNow.addClass('bdc-this');
                }
            }
            if(!$slickNow.hasClass('slick-active')){
                $('.slick-next').click();
            }
        });

        // 点击导航链接到指定位置
        $(document).on('click','.slick a', function() {
            var $parent = $(this).parent();
            $parent.addClass('bdc-this').siblings().removeClass('bdc-this');

            var getId = $(this).data('id');
            if ($('#' + getId).parents('.basic-info').offset().top == 118) {
                $("html,body").animate({ scrollTop: $('#' + getId).parents('.bdc-form-add').offset().top - 124 + "px" }, 200);
            } else {
                $("html,body").animate({ scrollTop: $('#' + getId).parents('.bdc-form-add').offset().top - 108 + "px" }, 200);
            }

        });

        // 收起展开全部
        $(document).on('click','#pack-up-all',function () {
            if($(this).text() == '收起全部'){
                $('.bdc-classify-container').addClass('bdc-hide');
                $('.triangle-up').addClass('bdc-hide');
                $('.triangle-down').removeClass('bdc-hide');
                $(this).text('展开全部');
            }else {
                $('.bdc-classify-container').removeClass('bdc-hide');
                $('.triangle-up').removeClass('bdc-hide');
                $('.triangle-down').addClass('bdc-hide');
                $(this).text('收起全部');
            }

        })

        // 展开收起单个表单
        $(document).on('click', '.bdc-form-btn', function() {
            $contain = $(this).parents('.bdc-form-add').find('.bdc-classify-container');
            $up = $(this).find('.triangle-up');
            $down = $(this).find('.triangle-down');

            if ($contain.hasClass('bdc-hide')) {
                $contain.removeClass('bdc-hide');
                $up.removeClass('bdc-hide');
                $down.addClass('bdc-hide');
            } else {
                $contain.addClass('bdc-hide');
                $up.addClass('bdc-hide');
                $down.removeClass('bdc-hide');
            }
        });

        var processDefData = JSON.parse(sessionStorage.getItem("processDefData"));
        var zhgzData = JSON.parse(sessionStorage.getItem("zhgzData"));
        // 导航栏
        function generateNav() {
            var json = {
                navList:processDefData
            };
            var tpl = navTpl.innerHTML,
                view = document.getElementById('nav')
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });

            var $slick = $('.slick');
            if($slick.children().length > 8){
                $slick.slick({
                    slidesToShow: 8,
                    slidesToScroll: 8
                });
            }else {
                $slick.slick({
                    arrows: false,
                    slidesToShow: 8,
                    slidesToScroll: 8
                });
            }
        }

        // 未关联子规则
        function generateGlzgz() {
            var json = {
                zhgzData :zhgzData
            };
            var tpl = zhgzTpl.innerHTML,
                view = document.getElementById('result')
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
        };

        // 组合规则未配置
        function generateLcpz() {
            var json = {
                lcpzList:processDefData
            };
            var tpl = lcpzTpl.innerHTML;
                // view = document.getElementById('result');
            laytpl(tpl).render(json, function (html) {
                $('#result').append(html);
            });
        }


    });
})