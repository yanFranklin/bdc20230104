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
    formSelects.config('selectDjxl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectQllx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectDjlx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectSfsczs', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectSfzxyql', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectSfscql', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectZszl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectQlrsjly', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectYwrsjly', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectQlsjly', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectSfscjtcy', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectSfsclhgx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectSfgzdj', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectSfhz', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    // formSelects.config('selectSfdb', {
    //     keyName: 'MC',            //自定义返回数据中name的key, 默认 name
    //     keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    // }, true);

    var gzldyid = $.getUrlParam('gzldyid');
    var djxl = $.getUrlParam('djxl');
    var qllx = $.getUrlParam('qllx');

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 渲染 select
     */
    $.ajax({
        url: BASE_URL+"/lcpz/zdxx",
        type: "POST",
        data:'djxl,qllx,djlx,sf,zslx,qlrsjly,qlsjly',
        contentType: 'application/json',
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                formSelects.data('selectDjxl','local',{arr:data.djxl});
                formSelects.value('selectDjxl', [String(djxl)]);
                formSelects.disabled('selectDjxl');
                formSelects.data('selectQllx','local',{arr:data.qllx});
                formSelects.value('selectQllx', [qllx]);
                formSelects.disabled('selectQllx');
                formSelects.data('selectDjlx','local',{arr:data.djlx});
                formSelects.data('selectSfsczs','local',{arr:data.sf});
                formSelects.data('selectSfzxyql','local',{arr:data.sf});
                formSelects.data('selectSfscql','local',{arr:data.sf});
                formSelects.data('selectZszl','local',{arr:data.zslx});
                formSelects.data('selectQlrsjly','local',{arr:data.qlrsjly});
                formSelects.data('selectYwrsjly','local',{arr:data.qlrsjly});
                formSelects.data('selectQlsjly','local',{arr:data.qlsjly});
                formSelects.data('selectSfscjtcy','local',{arr:data.sf});
                formSelects.data('selectSfsclhgx','local',{arr:data.sf});
                formSelects.data('selectSfgzdj','local',{arr:data.sf});
                formSelects.data('selectSfhz','local',{arr:data.sf});
                // formSelects.data('selectSfdb','local',{arr:data.sf});
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });

    queryBdcDjgx(djxl);

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 提交保存数据
     */
    form.on('submit(submitBtn)', function (data) {
        $.ajax({
            url: BASE_URL + "/lcpz/insert/step6",
            type: "PUT",
            data: {param:JSON.stringify(data.field),
                djxlQllxId:$('#djxlQllxId').val(),
                djxlDjyyId:$('#djxlDjyyId').val(),
                djlxDjxlId:$('#djlxDjxlId').val(),
                cshfwkgId:$('#cshfwkgId').val()},
            success: function (data) {
                queryBdcDjgx(djxl);
                enableNextBtn();
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
                    time: 1000}
                )
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
        if(this.attributes.disabled){
            return false;
        }
        window.location.href='step7.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    $('.beforestep').on('click',function () {
        window.location.href='step5.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取登记小类登记类型关系、登记小类登记原因关系、登记小类权利类型关系、初始化开关
     */
    function queryBdcDjgx(djxl) {
        if(djxl==undefined||djxl==null){
            return false;
        }
        $.ajax({
            url: BASE_URL + '/lcpz/bdcDjgx',
            type: "GET",
            data:{djxl:djxl},
            dataType: "json",
            success: function (data) {
                if(data){
                    var djlxDjxl=data.bdcDjlxDjxlGxDO !== null ? data.bdcDjlxDjxlGxDO : {};
                    var djxlDjyy=data.bdcDjxlDjyyGxDO !== null ? data.bdcDjxlDjyyGxDO : {};
                    var djxlQllx=data.bdcDjxlQllxGxDO !== null ? data.bdcDjxlQllxGxDO : {};
                    var cshFwkg=data.bdcCshFwkgDO !== null ? data.bdcCshFwkgDO : {};
                    if(djlxDjxl!=null){
                        formSelects.value('selectDjlx', [djlxDjxl.djlx]);
                         $('#djlxDjxlId').val(djlxDjxl.id);
                        if(djlxDjxl.id!=null && djxlDjyy.id!=null && djxlQllx.id!=null && cshFwkg.id!=null){
                            enableNextBtn();
                        }
                    }
                    if(djxlQllx!=null){
                        $('#djxlQllxId').val(djxlQllx.id);
                    }
                    if(djxlDjyy!=null){
                      $('#djyy').val(djxlDjyy.djyy);
                      $('#djxlDjyyId').val(djxlDjyy.id);
                    }
                    if(cshFwkg!=null){
                        if(cshFwkg.qlsjly!=null&&cshFwkg.qlsjly!=''){
                            formSelects.value('selectQlsjly', cshFwkg.qlsjly.split(','));
                        }
                        formSelects.value('selectSfsczs', [String(cshFwkg.sfsczs)]);
                        formSelects.value('selectZszl', [cshFwkg.zszl]);
                        formSelects.value('selectSfzxyql', [String(cshFwkg.sfzxyql)]);
                        formSelects.value('selectSfscql', [String(cshFwkg.sfscql)]);
                        formSelects.value('selectYwrsjly', [cshFwkg.ywrsjly]);
                        formSelects.value('selectQlrsjly', [cshFwkg.qlrsjly]);
                        formSelects.value('selectSfscjtcy', [String(cshFwkg.sfscjtcy)]);
                        formSelects.value('selectSfsclhgx', [String(cshFwkg.sfsclhgx)]);
                        formSelects.value('selectSfgzdj', [String(cshFwkg.sfgzdj)]);
                        if(cshFwkg.sfhz ==null){
                            //默认换证
                            cshFwkg.sfhz =1;
                        }
                        formSelects.value('selectSfhz', [String(cshFwkg.sfhz)]);
                        // formSelects.value('selectSfdb', [String(cshFwkg.sfdb)]);
                        $('#cshfwkgId').val(cshFwkg.id);
                    }


                }
            }
        });
    }
});