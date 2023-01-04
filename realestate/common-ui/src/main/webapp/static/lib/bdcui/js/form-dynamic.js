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
layui.use(['form','jquery','laydate','element','layer','formSelects','laytpl'],function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        formSelects = layui.formSelects,
        laytpl = layui.laytpl;
    $(function () {
        //找到不可选的动态添加img
        // console.log($('select[disabled="disabled"]'));
        $($('select[disabled="disabled"]')[1]).after('<img src="../images/lock.png" alt="">');

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
        form.on('submit(test)', function(data){
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

        //新增相关功能
        //新增
        $('.bdc-item-add').on('click',function () {
             var $parent = $(this).parent().parent();
            var getTpl = addItemTpl.innerHTML;
            laytpl(getTpl).render([], function(html){
                $parent.append(html);
                form.render();
            });
        });
        //收起、展开
        var $info = $('.basic-info');
        $info.on('click','.bdc-show-more',function () {
            var $this = $(this);
            if($this.html() == '收起'){
                $this.html('展开');
            }else {
                $this.html('收起');
            }
            $this.parent().siblings('.bdc-more-item').toggleClass('bdc-hide');
        });
        //删除
        $info.on('click','.bdc-item-delete',function () {
            $(this).parent().parent().remove();
        });
        //上移
        $info.on('click','.bdc-item-up',function () {
            var $parent = $(this).parent().parent();
            if($parent.index() == 1){
                layer.msg('<img src="../images/info-small.png" alt="">已经是第一个了');
                return false;
            }else {
                var prev = $parent.prev();
                $parent.insertBefore(prev);
            }
        });
        //下移
        $info.on('click','.bdc-item-down',function () {
            var $parent = $(this).parent().parent();
            var len = $parent.parent().children().length;
            if($parent.index() == len - 1){
                layer.msg('<img src="../images/info-small.png" alt="">已经是最后一个了');
                return false;
            }else {
                var next = $parent.next();
                $parent.insertAfter(next);
            }
        });
    });
});