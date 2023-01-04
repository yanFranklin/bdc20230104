/**
 * Created by Administrator on 2019/5/29.
 */
layui.use(['jquery', 'form'], function () {
    var $ = layui.jquery,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    $(function () {
        $('.bdc-content').css('min-height', $('body').height() - 56);
    });
    var gzldyid = $.getUrlParam('gzldyid');
    var djxl = $.getUrlParam('djxl');
    var qllx = $.getUrlParam('qllx');
    queryQlqtzk(djxl,qllx);
    function queryQlqtzk(djxl,qllx){
        if((djxl==undefined ||djxl==null) || (qllx==undefined || qllx==null)){
            return false;
        }
        $.ajax({
            url: BASE_URL + "/qlqtzkFj/page",
            type: "GET",
            data: {djxl: djxl, qllx: qllx},
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data.content!=null){
                    //数据解密
                    data.content.forEach(function (value){
                        decodeQlqtzkFj(value);
                    })
                    form.val('qlqtzkFjForm', JSON.parse(JSON.stringify(data.content[0])));
                }
            },
            error: function () {
                response.fail();
            }
        });
    }

    $('.nextstep').on('click', function () {
        window.location.href = 'step10.html?gzldyid=' + gzldyid + '&djxl=' + djxl + '&qllx=' + qllx;
    });

    $('.beforestep').on('click', function () {
        window.location.href = 'step8.html?gzldyid=' + gzldyid + '&djxl=' + djxl + '&qllx=' + qllx;
    });
    $('#sl').on('click', function () {
        layer.open({
            type: 2,
            title: '新增权利其他状况、附记配置示例',
            area: ['960px','500px'],
            content: [ "../qlqtzk/qlqtzkSl.html", 'yes'],
            end:function(){
            }
        });
    });
});