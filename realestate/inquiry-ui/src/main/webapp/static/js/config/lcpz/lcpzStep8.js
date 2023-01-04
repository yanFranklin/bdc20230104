/**
 * Created by Administrator on 2019/5/29.
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response',formSelects:'lib/form-select/formSelects-v4'});
layui.use(['jquery','formSelects','form','response'],function () {
    var $ = layui.jquery,
        formSelects = layui.formSelects,
        response = layui.response,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    $(function () {
        $('.bdc-content').css('min-height',$('body').height()-56);
    });
    formSelects.config('selectQllx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    var gzldyid = $.getUrlParam('gzldyid');
    var djxl = $.getUrlParam('djxl');
    var qllx = $.getUrlParam('qllx');
    queryBdcDjgx(qllx);
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 渲染 select
     */
    $.ajax({
        url: BASE_URL+"/lcpz/zdxx",
        type: "POST",
        data:'qllx',
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            if(data){
                formSelects.data('selectQllx','local',{arr:data.qllx});
                formSelects.value('selectQllx', [qllx]);
                formSelects.disabled('selectQllx');
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });


    form.verify({
        required: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value == '' || value == null) {
                return '必填项不能为空！';
            }
        }
    });

    $('.nextstep').on('click',function () {
        window.location.href='step9.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    $('.beforestep').on('click',function () {
        window.location.href='step7.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    /**
     * 表单数据提交
     * 1、不管新增还是编辑需要保证：年份和区县代码 组合是唯一的
     * 2、前台验证不可靠，在后台服务逻辑中验证
     */
    form.on('submit(submitBtn)', function(data) {
        //传输过程中对sql加密，防止被拦截
        if(!isNullOrEmpty(data.field.mbsql)){
            data.field.mbsql = Base64.encode(data.field.mbsql);
        }
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zsmbpz/sql",
            type: "POST",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (text) {
                if(true == text){
                    saveData(data.field);
                } else {
                    alertMsg("SQL配置不正确，请检查内容！");
                }
            },
            error:function(data){
                var responseText = JSON.parse(data.responseText);
                layer.alert("SQL配置不正确，请检查内容！<br>[" + responseText.msg.substr(0, 130) + "...]", {title: '提示'});
            }
        });

        // 禁止提交后刷新
        return false;
    });

    /**
     * 提交数据
     */
    window.saveData = function(data){
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zsmbpz",
            type: "PUT",
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                if(data && $.isNumeric(data) && data > 0){
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
                        time: 1000}
                    )
                } else {
                    fail(data);
                }
            },
            error:function(data){
                response.fail(data);
            }
        });
    }
    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function(data){
        var msg = "提交失败，请检查填写内容!";
        if(data && data.code && data.code == 72){
            msg = "提交失败，该地区指定年度模板配置已经存在！";
        }

        alertMsg(msg);
    }

    function queryBdcDjgx(qllx) {
        if(qllx==undefined||qllx==null){
            return false;
        }
        $.ajax({
            url: BASE_URL + '/zsmbpz',
            type: "GET",
            contentType: 'application/json',
            data:{qllx:qllx},
            dataType: "json",
            success: function (data) {
                //mbsql数据解密
                if(data && data.content[0]!=null){
                    $('#mbsql').text(Base64.decode(data.content[0].mbsql));
                }
            }
        });
    }
    /**
     * 显示示例
     */
    $('.sl').on('click',function () {
        layer.open({
            title: '证书模板示例',
            type: 2,
            area: ['960px','500px'],
            content: '../zs/zsmbpzsl.html'
        });
    })

});