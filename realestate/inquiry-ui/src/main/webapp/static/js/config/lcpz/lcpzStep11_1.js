/**
 * Created by Administrator on 2020/2/13.
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
    formSelects.config('selectYcslGzldymc', {
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
                formSelects.data('selectPzGzldymc','local',{arr:data})
                if(gzldyid!=undefined&&gzldyid!=null){
                    formSelects.value('selectPzGzldymc',[gzldyid])
                }
            }
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
            url: BASE_URL + "/lcpz/insert/step11",
            type: "PUT",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                $('#pzid').val(data.pzid);
                enableNextBtn();
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
        window.location.href='step1.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    $('.beforestep').on('click',function () {
        window.location.href='step11.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })


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


    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取登记小类配置
     */
    function queryDjxlPz(gzldyid){
        $.ajax({
            url: BASE_URL+"/lcpz/bdcDjxlPz",
            type: "GET",
            data:{gzldyid:gzldyid},
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data){
                    formSelects.value('selectYcslGzldymc',[data.ycslgzlmc]);
                    if(data.pzid!=null){
                        $('#pzid').val(data.pzid);
                        enableNextBtn();
                    }
                }

            },
            error: function (e) {
                response.fail(e, '');
            }
        });
    }

});