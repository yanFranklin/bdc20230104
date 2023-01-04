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
layui.use(['form','jquery','laydate','element','layer','table','formSelects'],function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        element = layui.element,
        table = layui.table,
        laydate = layui.laydate,
        formSelects = layui.formSelects;
    $(function () {
        //初始化日期控件
        lay('.test-item').each(function(){
            laydate.render({
                elem: '#startTime',
                trigger: 'click'
            });
            laydate.render({
                elem: '#endTime',
                trigger: 'click'
            });
            laydate.render({
                elem: '#czTime',
                trigger: 'click'
            });
        });

        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-form-div');
        function defaultStyle() {
            if($cnotentTitle.length == 1){
                $('.bdc-form-div').css('padding-top','68px');
            }
            if($(window).scrollTop() > 68){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','63px');
            }else if($(window).scrollTop() <= 68){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
        }

        $(window).resize(function(){
            defaultStyle();
        });
        $(window).on('scroll',function () {
            if($(this).scrollTop() > 68){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','63px');
            }else if($(this).scrollTop() <= 68){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
        });

        //监听tab切换
        element.on('tab(tabFilter)', function(data){
            switch(data.index){
                case 1:
                    table.resize('pageTable');
                    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                    break;
                case 0:
                    // console.log('000');
                    break;
            }
        });

        //点击提交时不为空的全部标红
        var isSubmit = true;
        var isFirst = true;
        form.verify({
            required: function(value,item) {
                if(value == ''){
                    if(isFirst){
                        console.log($(item).parents('.layui-tab-item'));
                        $('.layui-tab-item').removeClass('layui-show');
                        $(item).parents('.layui-tab-item').addClass('layui-show');
                        var liIndex = $(item).parents('.layui-tab-item').index() + 1;
                        console.log(liIndex);
                        console.log($('.layui-tab-title li:nth-child('+ liIndex +')'));
                        $('.layui-tab-title li').removeClass('layui-this');
                        $('.layui-tab-title li:nth-child('+ liIndex +')').addClass('layui-this');
                        isFirst = false;
                    }
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
                isFirst = true;
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
    });
});