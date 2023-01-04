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
        //初始化日期控件
        laydate.render({
            elem: '#startTime'
        });
        laydate.render({
            elem: '#endTime'
        });

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
        $(window).on('scroll',function () {
            if($(this).scrollTop() > 85){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','58px');
            }else if($(this).scrollTop() <= 85){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
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
                layer.msg('必填项不能为空', {icon: 5,time:3000});
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

        // removeDiabledById('disabledInput');
        // removeDiabledById('disabledSelect');
        // removeDiabledById('startTime');
        function removeDiabledById(id) {
            var $disabledEle = $('#' + id);
            $disabledEle.removeAttr('disabled');
            $disabledEle.parents('.layui-input-inline').find('img').remove();
            $disabledEle.parents('.layui-input-inline').removeClass('bdc-one-icon');
            form.render();
        }

    });
});