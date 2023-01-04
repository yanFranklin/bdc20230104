/**
 * Created by Administrator on 2019/5/29.
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response',formSelects:'lib/form-select/formSelects-v4'});
layui.use(['jquery','element','formSelects','form','response'],function () {
    var $ = layui.jquery,
        formSelects = layui.formSelects,
        response = layui.response,
        element = layui.element,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    $(function () {
        $('.bdc-content').css('min-height',$('body').height()-56);
    });
    formSelects.config('selectPzGzldymc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'key'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectDjxl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectQllx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectBdclx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectDyhqllx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectSfsf', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);


    var gzldyid = $.getUrlParam('gzldyid');
    var djxl = $.getUrlParam('djxl');
    var qllx = $.getUrlParam('qllx');

    //关闭处理
    window.onbeforeunload = function () {
        window.opener.location.reload();
    };
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取工作流定义名称
     */
    $.ajax({
        url: BASE_URL + '/mryj/gzldymcs',
        type: "GET",
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                formSelects.data('selectPzGzldymc','local',{arr:data});
                formSelects.disabled('selectPzGzldymc');
                if(gzldyid!=undefined&&gzldyid!=null){
                    formSelects.value('selectPzGzldymc',[gzldyid]);
                }
            }
        }
    });
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 渲染 select
     */
    $.ajax({
        url: BASE_URL+"/lcpz/zdxx",
        type: "POST",
        data:'qllx,bdclx,sf,dyhqllx,djxl',
        contentType: 'application/json',
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                formSelects.data('selectQllx','local',{arr:data.qllx});
                formSelects.data('selectDyhqllx','local',{arr:data.dyhqllx});
                formSelects.data('selectBdclx','local',{arr:data.bdclx});
                formSelects.data('selectSfsf','local',{arr:data.sf});
                formSelects.data('selectDjxl','local',{arr:data.djxl});
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });

    if(gzldyid!=undefined&&gzldyid!=null && djxl!=undefined && djxl!=null){
        queryDjxlPz(gzldyid,djxl);
        $('#gzldyid').val(gzldyid);
        formSelects.value('selectPzGzldymc',[gzldyid])
        formSelects.value('selectDjxl',[djxl])
    }
    if(qllx !=undefined && qllx!=null ){
        formSelects.value('selectQllx',[qllx])
    }



    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 提交保存数据
     */
    form.on('submit(submitBtn)', function (data) {
        $.ajax({
            url: BASE_URL + "/lcpz/bdcDjxlPz",
            type: "PUT",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                $('#pzid').val(data.pzid);
                form.val({
                    "sqsdylx": ""
                    ,"spbdylx": ""
                });
                enableNextBtn();
                layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
                    time: 1000}
                )
            },
            error: function (e) {
                response.fail(e);
            }
        });
        // 禁止提交后刷新
        return false;
    });

    form.verify({
        required: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value == '' || value == null || value == undefined) {
                return '必填项不能为空！';
            }
        }
        //要求正整数，允许为空
        , number: [/^[1-9]+[0-9]*]*$/, '请输入非负整数']
    });

    formSelects.on('selectPzGzldymc', function(id, vals, val, isAdd, isDisabled){
        //id:           点击select的id
        //vals:         当前select已选中的值
        //val:          当前select点击的值
        //isAdd:        当前操作选中or取消
        //isDisabled:   当前选项是否是disabled
        $('#gzldymc').val(val.name);
        sessionStorage.setItem('gzldymc',val.name);
        $('#gzldyid').val(val.key);
    });
    formSelects.on('selectDjxl', function(id, vals, val, isAdd, isDisabled){
        //id:           点击select的id
        //vals:         当前select已选中的值
        //val:          当前select点击的值
        //isAdd:        当前操作选中or取消
        //isDisabled:   当前选项是否是disabled
        var djxl=val.value;
        if((gzldyid==null ||gzldyid=='') || (djxl==null || djxl=='')){
            return;
        }
        queryDjxlPz(gzldyid,djxl);
    });

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取登记小类配置
     */
    function queryDjxlPz(gzldyid,djxl){
        $.ajax({
            url: BASE_URL+"/lcpz/bdcDjxlPz",
            type: "GET",
            data:{gzldyid:gzldyid,djxl:djxl},
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data){
                    formSelects.value('selectQllx',[data.qllx]);
                    formSelects.value('selectBdclx',[data.bdclx]);
                    formSelects.value('selectSfsf',[String(data.sfsf)]);
                    formSelects.value('selectDyhqllx',[data.dyhqllx]);
                    if(data.pzid!=null){
                        $('#pzid').val(data.pzid);
                        enableNextBtn();
                    }
                    $('#sqsdylx').val(data.sqsdylx);
                    $('#spbdylx').val(data.spbdylx);
                    $('#sjddylx').val(data.sjddylx);
                    $('#damldylx').val(data.damldylx);
                    $('#sxh').val(data.sxh);
                    $('#dafmdylx').val(data.dafmdylx);
                    $('#jtdafmdylx').val(data.jtdafmdylx);
                    $('#lydafmdylx').val(data.lydafmdylx);
                }

            },
            error: function (e) {
                response.fail(e, '');
            }
        });
    }


    $('.nextstep').on('click',function () {
        if(this.attributes.disabled){
            return false;
        }
        window.location.href='step2.html?gzldyid='+$('#gzldyid').val()+'&djxl='+formSelects.value('selectDjxl')[0].DM+'&qllx='+formSelects.value('selectQllx')[0].DM;
    })



});