/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 申请书页面 需要 头部固定时需要引入本js
 */
layui.use(['jquery','form'],function () {
    var $ = layui.jquery,
        form = layui.form;

    $(function () {
        //动态设置title
        var $title = $('.bdc-title');
        for(var i = 0; i <$title.length; i++){
            $($title[i]).attr('title',$($title[i]).val());
        }
        //滚动时头部固定
        var $contentTitle = $('.bdc-content-fix:visible');
        console.log($contentTitle);
        if($contentTitle.length != 0){
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

        form.on('submit', function(data){
           return false;
        });

        initZbsj();
    });
});

// 打印功能
function printQxsqb(){
    var data = layui.form.val("qxsqbForm");
    console.info(data);
    var checkboxMap = {};
    $('input[type=checkbox]:checked').each(function() {
        var name = $(this).attr("name");
        var val =  $(this).val();
        if(isNotBlank(checkboxMap[name])){
            var array = checkboxMap[name];
            array.push(val);
            checkboxMap[name] = array;
        }else{
            var array = [val];
            checkboxMap[name] = array;
        }
    });
    console.info(checkboxMap);
    if(isNotBlank(checkboxMap)){
        for(var key in checkboxMap){
            data[key] = checkboxMap[key].join(",");
        }
    }
    var tbsj = $("#tbsjn").val() + "年" +$("#tbsjy").val()+ "月"+ $("#tbsjr").val()+"日";
    data.tbsj = tbsj;
    console.info(data);

    printData(data);
}

function printData(data){
    $.ajax({
        url: "/realestate-accept-ui/slPrint/qxsqb/redisKey",
        type: "POST",
        data: JSON.stringify(data),
        contentType: 'application/json',
        dataType: "text",
        success: function (key) {
            if (key) {
                var pdfUrl = "/realestate-accept-ui/slPrint/qxsqb/print/" + key;
                window.open('/realestate-accept-ui/static/lib/pdf/web/viewer.html?file=' + encodeURIComponent(pdfUrl), "PDF");
            } else {
                warnMsg("打印失败，请重试！");
            }
        },
        error: function () {
            warnMsg("打印失败，请重试！");
        }
    });
    return false;
}

function initZbsj(){
    var date = new Date();
    var year =  date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
    $("#zbsjn").val(year);
    $("#zbsjy").val(month);
    $("#zbsjr").val(day);
    var zbsj = year+ "年"+ month +"月"+ day+"日";
    $("#zbsj").val(zbsj);
}

function isNotBlank(object) {
    if (typeof object === "object" && !(object instanceof Array)) {
        var hasProp = false;
        for (var prop in object) {
            hasProp = true;
            break;
        }
        if (hasProp) {
            hasProp = [hasProp];
        } else {
            return false;
        }
        return hasProp;
    }
    return typeof object != "undefined" && object != "";
}

function warnMsg(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}
