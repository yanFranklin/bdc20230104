layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response',formSelects:'lib/form-select/formSelects-v4'});
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form','response','formSelects'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        response = layui.response,
        formSelects = layui.formSelects,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    formSelects.config('selectDjxlAdd', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectQllxAdd', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    var gzldyid = $.getUrlParam('gzldyid');
    var djxl = $.getUrlParam('djxl');
    var qllx = $.getUrlParam('qllx');

    var zdData;
    $.ajax({
        url: BASE_URL + '/qlqtzkFj/zd',
        type: "GET",
        async: false,
        dataType: "json",
        timeout: 10000,
        success: function (data) {
            zdData = data;
            if(data){
                if(data.djxl){
                    formSelects.data('selectDjxlAdd','local',{arr:data.djxl});
                    if(djxl!=undefined && djxl!=null){
                        formSelects.value('selectDjxlAdd',[djxl]);
                        formSelects.disabled('selectDjxlAdd');
                    }
                }
                if(data.qllx){
                    formSelects.data('selectQllxAdd','local',{arr:data.qllx});
                    if(qllx!=undefined && qllx!=null){
                        formSelects.value('selectQllxAdd',[qllx]);
                        formSelects.disabled('selectQllxAdd');
                    }
                }
            }
        }
    });

    // 新增或者编辑标识
    var action = $.getUrlParam('action');
    /**
     * 权力其他状况验证结果
     */
    var qlqtzkResult = false;
    /**
     * 附记验证结果
     */
    var fjResult = false;

    var zxqlfjResult = false;

    /**
     * 表单数据提交
     * 1、不管新增还是编辑需要保证：年份和区县代码 组合是唯一的
     * 2、前台验证不可靠，在后台服务逻辑中验证
     */
    form.on('submit(submitBtn)', function (data) {
        var zxqlfjsjy=$('#zxqlfjsjy').val();
        var checkResult;
        // 当注销权利附记数据源不填的时候，不判断其验证结果
        if(isNullOrEmpty(zxqlfjsjy)) {
            checkResult = !qlqtzkResult && !fjResult;
        } else{
            checkResult = !qlqtzkResult && !fjResult && !zxqlfjResult;
        }

        if (checkResult) {
            layer.alert("数据源验证不通过！", {title: '提示'});
            return false;
        }
        var required = checkRequired();
        if (!required) {
            layer.alert("必填项不能为空！", {title: '提示'});
            return false;
        }

        var pzid = $('#pzid').val();
        //表单数据中sql加密
        encodeQlqtzkFj(data.field);
        $.ajax({
            url: BASE_URL + "/qlqtzkFj",
            type: (pzid != null && pzid != '') ? "POST" : "PUT",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                if (data && $.isNumeric(data) && data > 0) {
                    success();
                } else {
                    fail(data);
                }
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        // 禁止提交后刷新
        return false;
    });
    /**
     * 验证sql
     */
    form.on('submit(verify)', function (data) {
       return false;
    });


    window.checkRequired = function () {
        var fj_required = true;
        var qlqtzk_required = true;
        /* 注销权利附记非必填 */

        $(".fj_required").each(function (i) {
            var value = $(this).val();
            if (isNullOrEmpty(value)) {
                fj_required = false;
            }
        });
        $(".qlqtzk_required").each(function (i) {
            var value = $(this).val();
            if (isNullOrEmpty(value)) {
                qlqtzk_required = false;
            }
        });
        if (!fj_required && !qlqtzk_required) {
            return false;
        }
        return true;
    };
    form.verify({
        required: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value == '' || value == null) {
                return '必填项不能为空！';
            }
        },
        qlqtzk_required:function (value,item) {
            if(qlqtzkResult && fjResult && zxqlfjResult){
                return false;
            }
            var checkValue=$('#checkValue').val();
            if(checkValue==''||checkValue==null){
                return '请填写测试数据！';
            }
            if (value != '' && value != null) {
                qlqtzkResult = check(value, "权利其他状况");
                if(qlqtzkResult){
                    $('#qlqtzkOk').removeAttr("hidden");
                    $('#qlqtzkClose').attr("hidden",true);
                }else{
                    $('#qlqtzkOk').attr("hidden",true);
                    $('#qlqtzkClose').removeAttr("hidden");
                    return '请检查权利其他状况数据源配置！';
                }
            }
        },
        fj_required:function (value,item) {
            if(qlqtzkResult && fjResult && zxqlfjResult){
                return false;
            }
            var checkValue=$('#checkValue').val();
            if(checkValue==''||checkValue==null){
                return '请填写测试数据！';
            }
            if (value != '' && value != null) {
                fjResult = check(value, "附记");
                if(fjResult){
                    $('#fjOk').removeAttr("hidden");
                    $('#fjClose').attr("hidden",true);
                }else{
                    $('#fjOk').attr("hidden",true);
                    $('#fjClose').removeAttr("hidden");
                    return '请检查附记数据源配置！';
                }
            }
        },
        zxqlfj_required:function (value,item) {
            if(qlqtzkResult && fjResult && zxqlfjResult){
                return false;
            }
            var checkValue=$('#checkValue').val();
            if(checkValue==''||checkValue==null){
                return '请填写测试数据！';
            }
            if (value != '' && value != null) {
                zxqlfjResult = check(value, "注销权利附记");
                if(zxqlfjResult){
                    $('#zxqlfjOk').removeAttr("hidden");
                    $('#zxqlfjClose').attr("hidden",true);
                }else{
                    $('#zxqlfjOk').attr("hidden",true);
                    $('#zxqlfjClose').removeAttr("hidden");
                    return '请检查注销权利附记数据源配置！';
                }
            }
        }
    });
    /**
     * 编辑时填充数据
     * @param data
     */
    window.setData = function (data) {
        //对选中的数据进行解密
        decodeQlqtzkFj(data);
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        form.val('qlqtzkFjForm', JSON.parse(JSON.stringify(data)));
        formSelects.value('selectDjxlAdd', [data.djxl]);
        formSelects.value('selectQllxAdd', [data.qllx]);
        formSelects.disabled('selectDjxlAdd');
        formSelects.disabled('selectQllxAdd');
    };

    window.check = function (sql, type) {
        var result = false;
        $.ajax({
            url: BASE_URL + "/qlqtzkFj/check",
            type: "GET",
            data: {sqls: encodeURI(Base64.encode(sql)), checkValue: $('#checkValue').val()},
            contentType: 'application/json',
            dataType: "json",
            async: false,
            success: function (data) {
                result = true;
            },
            error: function () {
                checkFail(type);
                response.fail();
            }
        });
        return result;
    }
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
     * 验证提示
     */
    window.checkFail = function (type) {
        var msg = "验证" + type + "sql失败，请检查填写内容!";
        layer.alert(msg, {title: '提示'});
    }

    window.verify = function (id1, id2) {
        qlqtzkResult = false;
        fjResult = false;
        $('#' + id1).attr('hidden', 'true');
        $('#' + id2).attr('hidden', 'true');
    }

    //监听 权利信息 内 附记 单击
    $('.bdc-tab-box').on('click', '.bdc-pj-popup', function () {
        var $thisTextarea = $(this).siblings('textarea');
        var fjContent = $thisTextarea.val();
        var title = $(this).parents(".layui-form-item").find("label").text();
        layer.open({
            title: isNotBlank(title) ? title : '附记',
            type: 1,
            area: ['960px'],
            btn: ['保存'],
            content: $('#fjPopup')
            , yes: function (index, layero) {
                //提交 的回调
                $thisTextarea.val($('#fjPopup textarea').val());
                $('#fjPopup textarea').val('');
                layer.close(index);
            }
            , btn2: function (index, layero) {
                //取消 的回调
                $('#fjPopup textarea').val('');
            }
            , cancel: function () {
                //右上角关闭回调
                $('#fjPopup textarea').val('');
            }
            , success: function () {
                $('#fjPopup textarea').val(fjContent);
            }
        });
    });
});