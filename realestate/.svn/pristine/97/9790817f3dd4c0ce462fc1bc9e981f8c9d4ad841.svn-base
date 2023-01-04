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
        //1.1 使用js渲染下拉框
        var selectData = [
            {dm: '0',mc: '张三'},
            {dm: '1',mc: '李四'},
            {dm: '2',mc: '王五'}
        ];
        var nameHtml = ' ';
        for(var i = 0; i< selectData.length; i++){
            nameHtml += '<option value='+ selectData[i].dm + '>' + selectData[i].mc + '</option>'
        }
        $('#bdcName').append(nameHtml);
        form.render('select');
        //1.2 select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
        $('.layui-select-disabled i').addClass('bdc-hide');
        $('.layui-select-disabled').append('<img src="../images/lock.png" alt="">');
        $('.layui-select-disabled .layui-disabled').attr('disabled','true');

        //2. 初始化日期控件
        laydate.render({
            elem: '#startTime',
            value: '2019-04-01'
        });
        $('.bdc-date-disabled').append('<img src="../images/lock.png" alt="">');
        laydate.render({
            elem: '#endTime'
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
            // 不满8的情况
            var slickIndex = 0;
            var $slickNow = $($('.slick .slick-slide')[0]);
            for (i = 1; i <= slickLength; i++){
                if($("#slick"+i).offset().top - 108 <= wst){
                    slickIndex = i;
                    $('.slick .slick-slide').removeClass('bdc-this');
                    $slickNow = $($('.slick .slick-slide')[slickIndex]);
                    $slickNow.addClass('bdc-this');
                }
                if(wst == 0){
                    $('.slick .slick-slide').removeClass('bdc-this');
                    $slickNow = $($('.slick .slick-slide')[0]);
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
    });
});