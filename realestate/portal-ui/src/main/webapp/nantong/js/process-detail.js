/**
 * Created by ysy on 2020/10/13.
 */
layui.use(['jquery','element','form','table','response'], function() {

    var  $ = layui.jquery,
        response = layui.response,
        element = layui.element,
        form = layui.form,
        table = layui.table;

    var processInsId = $.getUrlParam('processInsId');
    var taskId = $.getUrlParam('taskId');
    var url ="/realestate-portal-ui/rest/v1.0/task/opinions?processInsId=" + processInsId + "&taskId=" + taskId;
    $.ajax({
        type: "GET",
        url: url,
        async:false,
        dataType:"json",
        success: function (data) {
            var tableIns = table.render({
                elem: '#process_detail_list',
                width:1057,
                data: data,
                cols: [[
                    {field:'taskName',  width:261,title: '转发活动',
                        templet: function (d) {

                            var shTime = d.time;
                            //ie兼容
                            if(shTime){
                                shTime = shTime.replace(/-/g,"/");
                            }else {
                                shTime='';
                            }

                            var activityNameHtml = '<p>审核时间：' + shTime +'</p>';
                            return activityNameHtml;
                        }
                    },
                    {field:'', width: 300, title: '办理状态',
                        templet: function (d) {
                            var handleOpinionHtml ='<p><strong>'+ (d.userAlisa == null ? "" : d.userAlisa) +'</strong> 挂起</p>';
                            return handleOpinionHtml;
                        }
                    },
                    {field:'', title: '挂起原因',
                        templet: function (d) {
                            var handleOpinionHtml = '';

                            if (d.opinion != null && d.opinion != undefined) {
                                handleOpinionHtml = '<p>' + getNewline(d.opinion) +'</p>';
                            }

                            return handleOpinionHtml;
                        }
                    }
                ]] ,
                page: false
            });
        }, error: function (e) {
            response.fail(e, '');
        }
    });



});

function getNewline(val) {
    var str = new String(val);
    var bytesCount = 0;
    var s="";
    for (var i = 0 ,n = str.length; i < n; i++) {
        var c = str.charCodeAt(i);
        //统计字符串的字符长度
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
            bytesCount += 1;
        } else {
            bytesCount += 2;
        }
        //换行
        s += str.charAt(i);
        if(bytesCount>=50){
            s = '<ul >'+s + '</ul>';
            //重置
            bytesCount=0;
        }
    }
    return s;
}

/**
 * 获得办理天数
 * @param millisecond
 */
function getDays(millisecond) {

    var days    = millisecond / 1000 / 60 / 60 / 24;
    var daysRound = Math.floor(days);
    var hours = millisecond / 1000 / 60 / 60 - (24 * daysRound);
    var hoursRound = Math.floor(hours);
    var minutes = millisecond / 1000 / 60 - (24 * 60 * daysRound) - (60 * hoursRound);
    var minutesRound = Math.floor(minutes);
    var seconds = millisecond / 1000 - (24 * 60 * 60 * daysRound) - (60 * 60 * hoursRound) - (60 * minutesRound);
    var secondsRound = Math.floor(seconds);
    console.log('转换时间:', daysRound + '天', hoursRound + '时', minutesRound + '分', seconds + '秒');
    var time = daysRound + '天' + hoursRound + '时' + minutesRound + '分' + secondsRound + '秒';
    return time;
}