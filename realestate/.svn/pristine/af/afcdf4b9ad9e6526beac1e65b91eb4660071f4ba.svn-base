/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 申请书页面 需要 头部固定时需要引入本js
 */
layui.use(['jquery', 'form'], function () {
    var $ = layui.jquery;

    $(function () {
        //滚动时头部固定
        var $cnotentTitle = $('.content-title');
        defaultStyle();
        function defaultStyle() {
            $('.content-div').css('paddingTop', '84px');
            $cnotentTitle.css(
                {
                    "position": 'fixed',
                    "width": $('.content-div').width()
                });
            if ($(window).scrollTop() > 85) {
                $cnotentTitle.css('top', '0');
            } else if ($(window).scrollTop() <= 85) {
                $cnotentTitle.css('top', '15px');
            }
        }

        $(window).resize(function () {
            defaultStyle();
        });
        $(window).on('scroll', function () {
            if ($(this).scrollTop() > 85) {
                $cnotentTitle.css('top', '0');
            } else if ($(this).scrollTop() <= 85) {
                $cnotentTitle.css('top', '15px');
            }
        });

        //测试复选框使用
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

    });
});