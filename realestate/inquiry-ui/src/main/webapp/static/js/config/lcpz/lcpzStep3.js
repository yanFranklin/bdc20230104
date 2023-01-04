/**
 * Created by Administrator on 2019/5/29.
 */
var djxl = $.getUrlParam('djxl');

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
    formSelects.config('selectPzGzldymc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'key'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectSfxszssl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectPzDjxl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    var gzldyid = $.getUrlParam('gzldyid');
    var qllx = $.getUrlParam('qllx');

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 渲染 select
     */
    $.ajax({
        url: BASE_URL + '/mryj/gzldymcs',
        type: "GET",
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                formSelects.data('selectPzGzldymc','local',{arr:data});
                formSelects.value('selectPzGzldymc', [gzldyid]);
                formSelects.disabled('selectPzGzldymc');
            }
        }
    });
    $.ajax({
        url: BASE_URL+"/lcpz/zdxx",
        type: "POST",
        data:'sf',
        contentType: 'application/json',
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                formSelects.data('selectSfxszssl','local',{arr:data.sf});
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });

    $.ajax({
        url: BASE_URL+"/lcpz/zdxx",
        type: "POST",
        data:'djxl',
        contentType: 'application/json',
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                for(var i=0;i<data.djxl.length;i++){
                    if(data.djxl[i].DM == djxl){
                        formSelects.data('selectPzDjxl','local',{arr:data.djxl});
                        formSelects.value('selectPzDjxl', [data.djxl[i].DM]);
                        formSelects.disabled('selectPzDjxl');
                        break;
                    }
                }
        }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });


    if(gzldyid!=undefined&&gzldyid!=null){
        queryBdcYxbdcdykg(gzldyid)
    }

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 提交保存数据
     */
    form.on('submit(submitBtn)', function (data) {
        $.ajax({
            url: BASE_URL + "/lcpz/insert/step3",
            type: "PUT",
            data: {param:JSON.stringify(data.field)},
            success: function (data) {
                $('#pzid').val(data.pzid);
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
                    time: 1000}
                )
                enableNextBtn();
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
        },zssl_required:function (value,item) {
            var sfxszssl=formSelects.value('selectSfxszssl');
            var dm=0;
            if(sfxszssl.length!=0){
                dm=sfxszssl[0].DM;
            }
            if(dm==1 && (value == '' || value == null)){
                return '必填项不能为空！';
            }
        }
    });

    $('.nextstep').on('click',function () {
        if(this.attributes.disabled){
            return false;
        }
        window.location.href='step4.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    $('.beforestep').on('click',function () {
        window.location.href='step2.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    function queryBdcYxbdcdykg(gzldyid) {
        if(gzldyid==undefined||gzldyid==null){
            return false;
        }
        $.ajax({
            url: BASE_URL + '/lcpz/bdcYxbdcdykg',
            type: "GET",
            data:{gzldyid:gzldyid,djxl:djxl},
            dataType: "json",
            success: function (data) {
                if(data){
                    formSelects.value('selectSfxszssl',[String(data.zssl)]);
                    formSelects.value('selectZsslmrz',[String(data.zsslmrz)]);
                    if(data.pzid!=null && data.pzid!=''){
                        $('#pzid').val(data.pzid);
                        enableNextBtn();
                    }
                }
            }
        });
    }
});