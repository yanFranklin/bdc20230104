/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 查询条件所需的日期初始化、复选框初始化及高级查询
 */
layui.config({
    base: '../../' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'form-select/formSelects-v4'
});
layui.use(['form','jquery','laydate','element','formSelects'],function () {
    var $ = layui.jquery,
        form = layui.form,
        formSelects = layui.formSelects,
        laydate = layui.laydate;
    $(function () {        
        //1. 日期
        lay('.test-item').each(function(){
            //精确到时分秒日期控件
            laydate.render({
                elem: '#exact1',
                type: 'datetime'
                ,trigger: 'click'
            });
            //初始化日期控件
            var startDate = laydate.render({
                elem: '#startTime'
                ,type: 'datetime'
                ,trigger: 'click',
                done: function(value,date){
                    //选择的结束时间大
                    if($('#endTime').val() != '' && !completeDate($('#endTime').val(),value)){
                        $('#endTime').val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                    }
                    endDate.config.min ={
                        year:date.year,
                        month:date.month-1,
                        date: date.date
                        ,hours:date.hours,
                        minutes:date.minutes,
                        seconds:date.seconds
                    }
                }
            });
            var endDate = laydate.render({
                elem: '#endTime'
                ,trigger: 'click'
                ,type: 'datetime'
            });

        });
        $('#endTime').on('change',function () {
            if($('#endTime').val() != '' && !completeDate($('#endTime').val(),$('#startTime').val())){
                $('#endTime').val('');
                $('.laydate-disabled.layui-this').removeClass('layui-this');
            }
        });
        function completeDate(date1,date2){
            var oDate1 = new Date(date1);
            var oDate2 = new Date(date2);
            if(oDate1.getTime() > oDate2.getTime()){
                return true;
            } else {
                return false;
            }
        }

        //2. 下拉选择
        //2.2 js渲染复选框
        formSelects.data('selectJs', 'local', {
            arr: [
                {"name": "北京北京北京北京北京北京北京北京", "value": 1},
                {"name": "上海", "value": 2,"selected":"selected"},
                {"name": "广州", "value": 3},
                {"name": "深圳", "value": 0},
                {"name": "天津", "value": 5}
            ]
        });

        //3. 输入框删除图标
        if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9."))
        {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click','.layui-icon-close',function () {
                $(this).siblings('.layui-input').val('');
                $(this).siblings().find('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus',function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur',function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                },150)
            });
        }

        //4.高级查询
        // 点击高级查询--4的倍数
        $('#seniorSearch').on('click',function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            // $(this).parent().toggleClass('bdc-button-box-four');

            if($(this).html() == '高级查询'){
                $(this).html('收起');
            }else {
                $(this).html('高级查询');
            }
        });
    });
});