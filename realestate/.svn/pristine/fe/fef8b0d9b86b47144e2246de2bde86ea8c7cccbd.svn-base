/**
 * Created by Administrator on 2019/5/29.
 */
layui.config({
    base: '../../../../static/' //静态资源所在路径
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
    formSelects.config('selectGzldymc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'key'            //自定义返回数据中value的key, 默认 value
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

    formSelects.config('selectDjxl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectQllx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    //预告登记种类
    formSelects.config('selectYgdjzl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    //原权利类型
    formSelects.config('selectYqllx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);


    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取工作流定义名称
     */
    queryGzldymc();

    function queryGzldymc(){
        $.ajax({
            url: BASE_URL + '/mryj/gzldymcs',
            type: "GET",
            dataType: "json",
            async:false,
            success: function (data) {
                if(data){
                    formSelects.data('selectGzldymc','local',{arr:data})
                }
            },
            error: function (e) {
                response.fail(e, '获取工作流定义名称失败！');
            }
        });
    }
    queryZd();
    function queryZd(){
        $.ajax({
            url: BASE_URL+"/lcpz/zdxx",
            type: "POST",
            data:'djxl,qllx,sf,bdclx,dyhqllx,ygdjzl',
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                if(data){
                    formSelects.data('selectDjxl','local',{arr:data.djxl});
                    formSelects.data('selectQllx','local',{arr:data.qllx});
                    formSelects.data('selectBdclx','local',{arr:data.bdclx});
                    formSelects.data('selectDyhqllx','local',{arr:data.dyhqllx});
                    formSelects.data('selectSfsf','local',{arr:data.sf});
                    formSelects.data('selectYgdjzl','local',{arr:data.ygdjzl});
                    formSelects.data('selectYqllx','local',{arr:data.qllx});
                    $('#sqsdylx').val(data.sqsdylx);
                    $('#spbdylx').val(data.spbdylx);
                    $('#sjddylx').val(data.sjddylx);
                }

            },
            error: function (e) {
                response.fail(e, '获取字典项失败！');
            }
        });
    }

    //顺序号
    $("#sxh").val(1);

    var action = $.getUrlParam('action');

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
                form.val({
                    "sqsdylx": ""
                    ,"spbdylx": ""
                });
                layer.msg('<img src="../../../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
                        time: 1000},
                    function(){
                        closeWin();
                    }
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
        //要求正整数，允许为空
        , number: [/^[1-9]+[0-9]*]*$/, '请输入非负整数']
    });

    window.setData = function (data) {
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        var datas=JSON.parse(JSON.stringify(data));


        /**
         * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
         * @description 渲染 select
         */
        if(datas){
            formSelects.value('selectGzldymc', [datas.gzldyid]);
            formSelects.value('selectDjxl', [String(datas.djxl)]);
            formSelects.value('selectQllx', [String(datas.qllx)]);
            formSelects.value('selectSfsf', [String(datas.sfsf)]);
            formSelects.value('selectBdclx', [String(datas.bdclx)]);
            formSelects.value('selectDyhqllx', [String(datas.dyhqllx)]);
            form.val('bdcDjxlPzForm', datas);
        }

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