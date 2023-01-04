/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 表单填写页面样式效果必须引用form，element，内部js代码根据需要删减
 */
layui.config({
    base: '../../form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['form','jquery','laydate','element','layer','formSelects'],function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        formSelects = layui.formSelects;
    $(function () {
        // var getTestJson = JSON.parse(sessionStorage.testJson);
        // console.log(getTestJson);
        //找到不可选的动态添加img
        // console.log($('select[disabled="disabled"]'));
        $($('select[disabled="disabled"]')[1]).after('<img src="../images/lock.png" alt="">');

        //2. 初始化日期控件
        laydate.render({
            elem: '#startTime',
            value: '2019-04-01'
        });
        $('.bdc-date-disabled').append('<img src="../images/lock.png" alt="">');
        laydate.render({
            elem: '#endTime'
            ,done: function(value, date, endDate){
                console.log(value); //得到日期生成的值，如：2017-08-18
                $(this)[0].elem.addClass('bdc-change-input');
            }
        });

        //3. 滚动时头部固定
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

            //为页面添加页面滚动监听事件
            var wst =  $(window).scrollTop();
            scrollIndex++;
            if(scrollIndex == 1){
                slickLength = $('.slick-slide').length - $('.slick-cloned').length;
            }
            //超过8个，需要左右切换的情况
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

        //4. 仿锚点连接
        $("#p2").click(function() {
            $("html, body").animate(
                {scrollTop: $("#div2").offset().top },
                {duration: 500,easing: "swing"}
            );
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
        $('.slick a').on('click',function () {
            var $parent = $(this).parent();
            $parent.addClass('bdc-this').siblings().removeClass('bdc-this');

            var getId = $(this).data('id');
            if($('#' + getId).parents('.basic-info').offset().top == 118){
                $("html,body").animate({scrollTop: $('#' + getId).parents('.basic-info').offset().top - 124 + "px"}, 200);
            }else {
                $("html,body").animate({scrollTop: $('#' + getId).parents('.basic-info').offset().top - 108 + "px"}, 200);
            }

        });

        //点击提交时不为空的全部标红
        var isSubmit = true;
        form.verify({
            required: function(value,item) {
                if(value == ''){
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                }
            }
        });
        
        $('#submit-btn').on('click',function () {
            test();
        });
        
        form.on('submit', function(data){
            // console.log(isSubmit);
            if(!isSubmit){
                layer.msg('必填项不能为空', {icon: 5});
                isSubmit = true;
                return false;
            }else {
                console.log('提交操作');
                return false;
            }
        });
        function test() {
            $('.bdc-zts').val('测试');
        }
        $('.layui-input').on('focus',function () {
            if($(this).hasClass('layui-form-danger')){
                $(this).removeClass('layui-form-danger');
            }
            if($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')){
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
        });
        $('.xm-input').on('click',function () {
            if($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')){
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
        });

        //监听input修改
        $('.layui-input').on('change',function () {
            $(this).addClass('bdc-change-input');
        });
        formSelects.on(function(id, vals, val, isAdd, isDisabled){
            console.log(id);
            console.log(vals);
            console.log(val);
            $('.xm-select-parent[fs_id="' + id + '"]').addClass('bdc-change-input');
        });
        //layui自带下拉框
        form.on('select(testFilter)', function(data){
            console.log(data.elem); //得到select原始DOM对象
            console.log(data.value); //得到被选中的值
            console.log(data.othis); //得到美化后的DOM对象
            data.othis.find('.layui-input').addClass('bdc-change-input');
        });
        $('.bdc-city').val('1');
        form.render('select');

        var $titleBtnBox = $('.title-btn-area');
        var thirdIn = false;
        $titleBtnBox.on('mouseenter','.bdc-test-btn',function () {
            $('.bdc-test').show();
            $('.bdc-test1').hide();
        });
        $titleBtnBox.on('mouseenter','.bdc-dy-btn',function () {
            $('.bdc-test').hide();
            $('.bdc-test1').show();
        });
        //二级按钮鼠标覆盖
        $titleBtnBox.on('mouseenter','.bdc-title-secondary-btn',function () {
            $('.bdc-third-btn-box').show();
        }).on('mouseleave','.bdc-title-secondary-btn',function () {
            setTimeout(function () {
                if(!thirdIn){
                    $('.bdc-third-btn-box').hide();
                }
            },100);
        });
        $titleBtnBox.on('mouseenter','.bdc-third-btn-box',function () {
            thirdIn = true;
        }).on('mouseleave','.bdc-third-btn-box',function () {
            thirdIn = false;
        });
        $titleBtnBox.on('mouseleave',function () {
            $('.bdc-table-btn-more').hide();
        });
        $('.bdc-table-btn-more a').on('click',function () {
            $(this).parent().hide();
        });
    });
});