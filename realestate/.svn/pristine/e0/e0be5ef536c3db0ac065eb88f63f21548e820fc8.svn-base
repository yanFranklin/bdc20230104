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

    formSelects.config('selectSjlxAdd', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    var gzldyid = $.getUrlParam('gzldyid');
    var qllx = $.getUrlParam('qllx');
    var djxl = $.getUrlParam('djxl');
    var action = $.getUrlParam('action');
    if(action=='add'){
        /**
         * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
         * @description 渲染 select
         */
        $.ajax({
            url: BASE_URL+"/lcpz/zdxx",
            type: "POST",
            data:'sjlx',
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data){
                    formSelects.data('selectSjlxAdd','local',{arr:data.sjlx});
                }
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
    }

    if(djxl!=undefined && djxl!=null ){
        $('#djxl').val(djxl);
    }

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 提交保存数据
     */
    form.on('submit(submitBtn)', function (data) {
        var obj=data.field;
        obj.clmc=replaceBracket(obj.clmc);
        $.ajax({
            url: BASE_URL + "/lcpz/sjclpz",
            type: "PUT",
            data: JSON.stringify(obj),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
                        time: 1000},
                    function(){
                        form.val("sjclpzForm", {
                            "clmc": ""
                            ,"mrfs": "1"
                            ,"djyy":""
                        })
                        formSelects.value('selectSjlxAdd',[]);
                    }
                );
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        // 禁止提交后刷新
        return false;
    });

    form.verify({
        required: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value == '' || value == null) {
                return '必填项不能为空！';
            }
        }
    });
    $('.nextstep').on('click',function () {
        window.location.href='step6.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    $('.beforestep').on('click',function () {
        window.location.href='step4.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    window.setData = function (data) {
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        var datas=JSON.parse(JSON.stringify(data));
        form.val('sjclpzForm', datas);
        /**
         * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
         * @description 渲染 select
         */
        $.ajax({
            url: BASE_URL+"/lcpz/zdxx",
            type: "POST",
            data:'sjlx',
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data){
                    formSelects.data('selectSjlxAdd','local',{arr:data.sjlx});
                    formSelects.value('selectSjlxAdd', [String(datas.sjlx)]);
                }
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
    };
    /**
     * 关闭弹出页面
     */
    window.closeWin = function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };

    window.closeParentWindow = function () {
        var index = parent.parent.layer.getFrameIndex(window.name);
        parent.parent.layer.close(index);
    };

});