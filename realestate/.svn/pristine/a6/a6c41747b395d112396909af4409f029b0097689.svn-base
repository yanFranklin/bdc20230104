/**
 * Created by YuanPengpeng on 2019/6/21.
 */
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = encodeURI(window.location.search).substr(1).match(reg);
        if(null != r) return unescape(r[2]); return null;
    }
})(jQuery);

var $
layui.use(['jquery'], function(){
    $ = layui.$;
});
function showTime() {
    var date = new Date();
    var year = date.getFullYear();
    var month,day,hours,minutes;
    if(date.getMonth()+1<10){
        month = "0"+( date.getMonth()+1 );
    } else {
        month = ''+ (date.getMonth()+1);
    }
    if(date.getDate()<10){
        day = "0"+date.getDate();
    } else {
        day = date.getDate();
    }
    if(date.getHours()<10){
        hours = "0"+date.getHours();
    } else {
        hours = date.getHours();
    }
    if(date.getMinutes()<10){
        minutes = "0"+date.getMinutes();
    } else {
        minutes = date.getMinutes();
    }

    $('.bdc-time-date').html(year + '-' + month + '-' + day);
    $('.bdc-time-hour').html(hours + ':' + minutes);
    $('.bdc-time-week').html(getWeek(date));
}
function getWeek (d) {
    var weekday = new Array(7);
    weekday[0] = "星期天";
    weekday[1] = "星期一";
    weekday[2] = "星期二";
    weekday[3] = "星期三";
    weekday[4] = "星期四";
    weekday[5] = "星期五";
    weekday[6] = "星期六";

    return weekday[d.getDay()];
}