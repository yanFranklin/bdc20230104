/**
 *  历史数据审核信息展示页面处理JS
 */
layui.use(['element','form','table'], function() {
    var element = layui.element,
        form = layui.form,
        table = layui.table;

    // 获取参数
    var xmid = $.getUrlParam('xmid');
    var processInsId = $.getUrlParam('processInsId');

    table.render({
        elem: '#process',
        width: 1590,
        url: "/realestate-register-ui/rest/v1.0/bdcdy/lsxx/lcxx?xmid=" + xmid + "&gzlslid=" + processInsId,
        cols: [[
            {field:'d.shjssj',  title: '<strong>状态</strong>',width:80,align: 'center',
                templet: function (d) {
                    return '<img src="../../static/image/process-approved.png">';
                }},
            {field:'jdmc',  width:260,title: '<strong>转发活动</strong>',
                templet: function (d) {
                    var activityNameHtml = '<strong>'+d.jdmc+'</strong>' + '<br>开始时间：' + format(d.shkssj) ;
                    if(d.shjssj != null && d.shjssj != ""){
                        activityNameHtml += '<br>结束时间：' + format(d.shjssj);
                        activityNameHtml += '<br>办理耗时：' + getDays(d.shjssj - d.shkssj) + '</p>';
                    }
                    return activityNameHtml;
                }
            },
            {field:'', width: 350, title: '<strong>办理状态</strong>',
                templet: function (d) {
                    return format(d.shjssj) + '&nbsp;&nbsp; <strong>'+ d.shryxm +'</strong>&nbsp;&nbsp;' + '办结';
                }
            },
            {field:'shyj', width: 400, title: '<strong>审核意见</strong>'},
            {field:'sfth', width: 100, title: '<strong>是否退回</strong>'},
            {field:'thyj', width: 400, title: '<strong>退回意见</strong>'}
        ]] ,
        page: false,
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
    });

});

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