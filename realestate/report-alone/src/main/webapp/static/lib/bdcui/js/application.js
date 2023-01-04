/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 申请书页面 需要 头部固定时需要引入本js
 */
layui.use(['jquery','form', 'laydate'],function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate;

    $(function () {
        //动态设置title
        var $title = $('.bdc-title');
        for(var i = 0; i <$title.length; i++){
            $($title[i]).attr('title',$($title[i]).val());
        }
        lay('.test-item').each(function(){
            laydate.render({
                elem: '#test1' //指定元素
                ,trigger: 'click'
            });
            //日期不可使用
            laydate.render({
                elem: '#dateDisable',
                value: '2019-01-16'
                ,trigger: 'click'
            });
        });
        //滚动时头部固定
        var $contentTitle = $('.bdc-content-fix:visible');
        console.log($contentTitle);
        if($contentTitle.length != 0){
            console.log('aaa');
            defaultStyle();
        }
        function defaultStyle() {
            if($(window).scrollTop() > 10){
                $contentTitle.css('top','0');
            }else if($(window).scrollTop() <= 10){
                $contentTitle.css('top','10px');
            }
        }

        $(window).resize(function(){
            if($contentTitle.length != 0){
                defaultStyle();
            }
        });
        $(window).on('scroll',function () {
            if($contentTitle.length != 0){
                if($(this).scrollTop() > 10){
                    $contentTitle.css('top','0');
                }else if($(this).scrollTop() <= 10){
                    $contentTitle.css('top','10px');
                }
            }
        });

        //测试复选框使用
        // console.log($('#djLine').length);
        if($('#djLine').length == 1){
            var combotreeData = [
                {id: "100", text: "首次登记"},
                {id: "200", text: "转移登记"},
                {id: "300", text: "变更登记"},
                {id: "400", text: "注销登记"},
                {id: "500", text: "更正登记"},
                {id: "600", text: "异议登记"},
                {id: "700", text: "预告登记"},
                {id: "800", text: "查封登记"}
            ];
            $('#djLine').combotree({
                valueField: "id", //Value字段
                textField: "text", //Text字段
                multiple: true,
                data: combotreeData,
                onCheck: function (record) {
                    console.log(record);
                    console.log(record.checkState);
                }
            });
        }

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
        $('td input').on('focus',function () {
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