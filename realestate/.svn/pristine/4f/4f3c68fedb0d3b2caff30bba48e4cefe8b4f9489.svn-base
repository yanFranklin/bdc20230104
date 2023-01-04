/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 表单填写页面样式效果必须引用form，element，内部js代码根据需要删减
 */
layui.use(['form','jquery','laydate','element','layer','table'],function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        element = layui.element,
        table = layui.table,
        laydate = layui.laydate;
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
    });
});