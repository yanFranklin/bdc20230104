/**
 * Created by Administrator on 2019/5/29.
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
layui.use(['jquery', 'form', 'response'], function () {
    var $ = layui.jquery,
        response = layui.response,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    $(function () {
        $('.bdc-content').css('min-height', $('body').height() - 56);
    });
    var gzldyid = $.getUrlParam('gzldyid');
    var djxl = $.getUrlParam('djxl');
    var qllx = $.getUrlParam('qllx');
    $('.beforestep').on('click', function () {
        window.location.href = 'step11.html?gzldyid=' + gzldyid + '&djxl=' + djxl + '&qllx=' + qllx;
    });
    $('.nextstep').on('click', function () {
        window.location.href = 'step13.html?gzldyid=' + gzldyid + '&djxl=' + djxl + '&qllx=' + qllx;
    });
    $('.finish').on('click', function () {
        window.parent.close();
        window.opener.location.href = window.opener.location.href;
    })

});