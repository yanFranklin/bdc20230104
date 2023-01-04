/**
 * Created by ypp on 2019/12/16.
 * 邮寄信息相关js
 */
layui.config({
    base: '../../static/lib/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    selectN: 'select-extend/selectN'
});
var gzlslid = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var jjrSelect,sjrSelect;
layui.use(['form','jquery','selectN'],function () {
    var $ = layui.jquery,
        form = layui.form,
        selectN = layui.selectN;
    $(function () {
        //3. 输入框删除图标
        if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click','.layui-icon-close',function () {
                $(this).siblings('.layui-input').val('');
                $(this).siblings().find('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus',function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur',function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                },150)
            });
        }
        //加载表单初始化数据
        var formData  = initFormData();
        if(formData.tszt == 1){
            loadEmsTraceLog(formData.slbh, formData.wlydh);
        }
        form.val('yjxx-form',formData);
        getStateById(readOnly, formStateId, 'yjxx');
        //表单状态控制
        formStateControl(formData);

        // 获取数据字典省市区县数据
        var ssqData = loadSelectData();
        //寄件人三级渲染
        jjrSelect = selectN({
            elem: '#jjrAddress',
            data: ssqData,
            last:true,
            tips: ['请选择省','请选择市','请选择区'],
            delimiter: ',',
            field:{idName:'name',titleName:'name',childName:'children'},
            formFilter: null,
            selected : [formData.jjrszp, formData.jjrszc, formData.jjrszx]
        });
        //收件人三级渲染
        sjrSelect = selectN({
            elem: '#sjrAddress',
            data: ssqData,
            last:true,
            tips: ['请选择省','请选择市','请选择区'],
            delimiter: ',',
            field:{idName:'name',titleName:'name',childName:'children'},
            formFilter: null,
            selected : [formData.sjrszp, formData.sjrszc, formData.sjrszx]
        });

        verifyData(form);
        // 推送方法, 先保存，在进行数据推送EMS
        form.on("submit(tsBtn)",function (data) {
            saveForm().done(function(){
                getReturnData("/slym/yjxx/ems/"+gzlslid, '', 'GET', function (res) {
                    var data = JSON.parse(res);
                    ityzl_SHOW_SUCCESS_LAYER(data.message);
                    form.val('yjxx-form',initFormData());
                    if(data.result){
                        $('.bdc-ts-info').removeClass('bdc-hide');
                    }
                }, function (err) {
                    delAjaxErrorMsg(err);
                });
            });
            return false;
        });
        // 保存方法
        form.on("submit(submitBtn)", function (data) {
            saveForm().done(function(){
                ityzl_SHOW_SUCCESS_LAYER("保存成功");
                form.val('yjxx-form',initFormData());
            });
            return false;
        });
    });
});
function checkSelect(){
    if(jjrSelect.names.length < 2){
        layer.msg("请选择寄件人所在省、市", {icon: 5, time: 3000});
        return true;
    }
    if(sjrSelect.names.length < 2){
        layer.msg("请选择收件人所在省、市", {icon: 5, time: 3000});
        return true;
    }
    return false;
}
/**
 * 保存表单
 */
function saveForm(){
    var deferred = $.Deferred();
    if(checkSelect()){
        deferred.reject();
        return deferred;
    }
    var yjxxData = {};
    $("#yjxx").serializeArray().forEach(function (item, index) {
        yjxxData[item.name] = item.value;
    });
    var jjr =getSelectData(jjrSelect.names);
    $.extend(yjxxData,{
        jjrszp : jjr.province, jjrszc : jjr.city, jjrszx : jjr.area});
    var sjr = getSelectData(sjrSelect.names);
    $.extend(yjxxData,{
        sjrszp : sjr.province, sjrszc : sjr.city, sjrszx : sjr.area});
    if(isNotBlank(yjxxData.yjxxid)){
        //修改邮件信息
        getReturnData("/slym/yjxx", JSON.stringify(yjxxData), 'PATCH', function (data) {
            deferred.resolve();
        }, function (err) {
            deferred.reject();
            delAjaxErrorMsg(err);
        });
    }else{
        yjxxData.gzlslid = gzlslid;
        getReturnData("/slym/yjxx/yj", JSON.stringify(yjxxData), 'POST', function (data) {
            deferred.resolve();
        }, function (err) {
            deferred.reject();
            delAjaxErrorMsg(err);
        });
    }
    return deferred;
}

/**
 * 获取select框方法
 */
function getSelectData(array){
    var data = {province : "",city : "",area : ""};
    switch(array.length){
        case 0:
            data;
            break;
        case 1:
            data.province = array[0];
            break;
        case 2:
            data.province = array[0];
            data.city = array[1];
            break;
        case 3:
            data.province = array[0];
            data.city = array[1];
            data.area = array[2];
            break;
    }
    return data;
}
/**
 * 初始化表单数据
 */
function initFormData(){
    var formData = "";
    getDataByAjax('/slym/yjxx/'+gzlslid,'','GET', function(data){
        console.info(data);
        // 判断受理环节是否进行推送标记，未标记不展示推送按钮
        if(isNotBlank(data.yjxxid)){
            $('#tsBtn').removeClass('bdc-hide');
        }
        if(isNotBlank(data.wlydh)){
            $("#tsztmc").val("已发货");
        }
        formData = data;
    },false);
    return formData;
}

/**
 * 加载物流信息
 */
function loadEmsTraceLog(slbh, wlydh){
    layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
        var laytpl = layui.laytpl;
        if(isNotBlank(slbh) && isNotBlank(wlydh)){
            getDataByAjax('/slym/yjxx/trace',{
                slbh : slbh,
                wlydh : wlydh
            },'GET', function(data){
                var wlxx = JSON.parse("[]");
                if(isNotBlank(data)){
                    wlxx = data;
                }
                if(wlxx.length == 0){
                    wlxx = [{
                        acceptAddress : "无",
                        remark : "暂无物流信息",
                        acceptTime: ""
                    }]
                }
                wlxx.reverse();
                var tpl = wlxxTpl.innerHTML, view = document.getElementById('wlxxul');
                //渲染提示框展示
                laytpl(tpl).render(wlxx, function (html) {
                    view.innerHTML = html;
                });
            },false);
        }

    });
}

/**
 * 初始化加载选择框内容
 */
function loadSelectData() {
    var selectData = "";
    getDataByAjax('/slym/yjxx/init/zd','','GET', function(data){
        selectData = data;
    },false);
    return JSON.parse(selectData);
}
/**
 * 表单数据验证方法
 */
function verifyData(form){
    form.verify({
        required : function (value, item) {
            if (value == '') {
                $(item).addClass('layui-form-danger');
                return "必填项不能为空";;
            }
        },
        phone : function(value, item){
            var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;//电话号码
            var isMob = /^0?1[3|4|5|6|7|8|9][0-9]\d{8}$/;//手机号码
            var validate = false;
            if (!isNotBlank(value) || isMob.test(value) || isPhone.test(value)) {
                validate = true;
            }
            if (!validate) {
                $(item).addClass('layui-form-danger');
                return "联系电话格式不正确";
            }
        },
        postcode : function(value, item){
            var pattern = /^[0-9]{6}$/;
            var validate = pattern.test(value);
            if (!validate) {
                $(item).addClass('layui-form-danger');
                return "请输入正确的邮编号";
            }
        }
    });
}

/**
 * 表单数据展示控制
 */
function formStateControl(data){
    // 设置物流运单号，有值只读，无值必填
    var $wlydh = $('input[name="wlydh"]');
    if (isNotBlank(data.wlydh)){
        $wlydh.attr('disabled','off');
        $wlydh.parent().append("<img src=\"../../static/lib/bdcui/images/lock.png\" alt=\"\">");
        $wlydh.parent().addClass('bdc-date-disabled');
    }else {
        $wlydh.removeAttr('disabled');
        $wlydh.parent().find('img').remove();
        $wlydh.parent().removeClass('bdc-date-disabled');
    }

}
function getDataByAjax(_path, _param,_type, _fn, async) {
    var _url = getContextPath() + _path;
    var getAsync = false;
    if(async){
        getAsync = async
    }
    $.ajax({
        url: _url,
        type: _type,
        async: getAsync,
        contentType: 'application/json;charset=utf-8',
        data: _param,
        success: function (data) {
            _fn.call(this, data, data);
        },
        error: function (err) {
            console.log(err);
        }
    });
}