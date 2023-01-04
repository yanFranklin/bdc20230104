layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response',formSelects:'lib/form-select/formSelects-v4'});
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form','response','formSelects'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        response = layui.response,
        formSelects = layui.formSelects,
        form = layui.form;

    formSelects.config('selectGzldyid', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'key'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectGglx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectSply', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    var gzldyid = $.getUrlParam('gzldyid');
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';

    var zdData ={};

    /**
     * 加载字典
     */
    $.ajax({
        url: BASE_URL+"/lcpz/zdxx",
        type: "POST",
        data:'gglx,sply',
        contentType: 'application/json',
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                zdData=data;
                formSelects.data('selectGglx','local',{arr:data.gglx});
                formSelects.data('selectSply','local',{arr:data.sply});
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });
    $.ajax({
        url: BASE_URL + '/mryj/gzldymcs',
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            if(data){
                formSelects.data('selectGzldyid','local',{arr:data});
                if(gzldyid!="" && gzldyid!="null"){
                    formSelects.value('selectGzldyid',[gzldyid]);
                    formSelects.disabled('selectGzldyid');
                }
            }
        }
    });

    // 新增或者编辑标识
    var action = $.getUrlParam('action');


    /**
     * 表单数据提交
     */
    form.on('submit(submitBtn)', function (data) {
        //验证sql正确性
        var result =checkSql(data.field);
        if(result.code ==="0") {
            $.ajax({
                url: BASE_URL + "/xtgg",
                type: "PUT",
                data: JSON.stringify(data.field),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                    if (data && $.isNumeric(data) && data > 0) {
                        success();
                    } else {
                        response.fail(data);
                    }
                },
                error: function (e) {
                    delAjaxErrorMsg(e);
                }
            });
            // 禁止提交后刷新
            return false;
        }else{
            var msg = "验证sql失败，请检查填写内容!";
            layer.alert(isNotBlank(result.msg) ?result.msg :msg, {title: '提示'});
        }
    });

    /**
     * 编辑时填充数据
     * @param data
     */
    window.setData = function (data) {
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        formSelects.value('selectGzldyid', [data.gzldyid]);
        formSelects.value('selectGglx', [data.gglx]);
        formSelects.value('selectSply', [data.sply]);
        form.val('xtggForm', JSON.parse(JSON.stringify(data)));


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

    /**
     * 提交成功提示
     */
    window.success = function () {
        layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
            time: 1000},
            function(){
                closeParentWindow();
            }
        )};

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function () {
        var msg = "提交失败，请检查填写内容!";
        layer.alert(msg, {title: '提示'});
    }

    /**
     * 验证sql正确性
     * @param sql
     */
    function checkSql(bdcXtGg){
        var result ={};
        var msg ="";
        result.code ="1";
        if(!isNotBlank(bdcXtGg.ggnrpz) ||!isNotBlank(bdcXtGg.ggbtpz)){
            msg ="未填写公告标题或公告内容";
            result.msg =msg;
            return result;
        }
        $.ajax({
            url: BASE_URL + "/xtgg/sql?xmid=1&gzlslid=2",
            type: "POST",
            data: JSON.stringify(bdcXtGg),
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                result.code ="0";
                return result;
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        return result;

    }
});


