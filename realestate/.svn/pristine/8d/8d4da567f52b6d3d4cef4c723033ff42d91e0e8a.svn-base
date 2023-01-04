/**
 * config应用公共JS
 * @version 1.0, 2019/01/14
 */

/**
 * 获取URL地址中的参数
 * @param name 参数名称
 */
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(null != r) return unescape(r[2]); return null;
    }
})(jQuery);

/**
 * 判断字符串是否为空
 *
 * @param str  目标字符串
 * @returns {boolean}  为空：true ; 不为空：false
 */
function isNullOrEmpty(str){
    if(!str || "" == str || "null" == str || undefined == str || "undefined" == str){
        return true;
    }

    return false;
}

/**
 * 渲染日期
 * @param format 日期格式
 */
function renderDate(format){
    var laydate = layui.laydate;
    if(isNullOrEmpty(format)){
        format = 'yyyy-MM-dd HH:mm:ss';
    }

    lay('.date').each(function(){
        laydate.render({
            elem: this
            ,trigger: 'click'
            ,type: 'datetime'
            ,format: format
        });
    });
}

function loadLayer() {
    var index = layer.load(1, {
        shade: [0.5, '##fff'] //0.1透明度的白色背景
    });
    return index;
}

function successMsg(msg) {
    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">' + msg);
}

function errorsMsg(msg) {
    layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">' + msg);
}

function warnMsg(msg){
    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}

function alertMsg(msg){
    layer.alert("<div style='text-align: center'>" + msg + "</div>", {title: '提示'});
}

//警告小提示
function ityzl_SHOW_WARN_LAYER(msg) {
    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}

function delSuccessMsg(){
    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">删除成功');
}

function delFailMsg(){
    layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">删除失败，请重试');
}

function saveSuccessMsg(){
    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">保存成功');
}

function saveFailMsg(){
    layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">保存失败，请重试');
}

//数据交互
function getReturnData(_path, _param, _type, _fn, _errorFn, async) {
    if (async === undefined) {
        async = true;
    }
    var _url = getContextPath() + _path;
    $.ajax({
        url: _url,
        type: _type,
        async: async,
        contentType: 'application/json;charset=utf-8',
        data: _param,
        success: function (data) {
            _fn.call(this, data, data);
        },
        error: function (err) {
            _errorFn.call(this, err);
        }
    });
}
//处理ajax的错误
function delAjaxErrorMsg(e,message) {
    removeModal();
    layer.closeAll();
    var msg = '请求异常！';
    var detail = '';
    if (message != '' && message != undefined) {
        msg = message;
    }
    if (e.status == 500) {
        var responseText = JSON.parse(e.responseText);
        msg = (message != '' && message != undefined)? msg:responseText.msg;
        detail = responseText.detail;
    }
    //session失效
    // if(isSessionTimeout(e)){
    //     layer.msg('会话超时，即将刷新页面。');
    //     window.location.reload();
    //     return false;
    // }
    layer.msg('<img src="../../../static/image/error-small.png" alt="">' + msg + ' <a class="bdc-show-more-tips" href="javascript:;">更多</a>', {
        time: 4000,
        success: function () {
            if($('#otherTips').length == 0){
                $('body').append('<div class="bdc-other-tips-box bdc-hide">\n' +
                    '    <div class="bdc-other-tips">\n' +
                    '        <p>错误提示：<span class="bdc-close">不再提示</span></p>\n' +
                    '        <div id="otherTips">\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '</div>');
            }
            var exceptionMsg = '';
            $.each(detail, function (key, val) {
                exceptionMsg += '<p>' + val + '</p>';
            });
            if (exceptionMsg == '') {
                exceptionMsg = '<p>暂无详细信息，请查看系统日志</p>'
            }
            $('#otherTips').html(exceptionMsg);
            //点击更多
            $('.bdc-show-more-tips').on('click', function () {
                $('.bdc-other-tips-box').removeClass('bdc-hide');
            });
            //点击 不再提示 ，关闭提示框
            $('.bdc-other-tips-box .bdc-close').on('click', function () {
                $('.bdc-other-tips-box').addClass('bdc-hide');
            });
        }
    });
}

//处理session超时失效
function isSessionTimeout(e){
    if(e.status == 405){
        var responseText = JSON.parse(e.responseText);
        var path =responseText.path;
        if(!isNullOrEmpty(path) &&path.indexOf("/account/login") >-1) {
            return true;
        }
    }else if(e.status ==200){
        var responseText = e.responseText;
        if(!isNullOrEmpty(responseText) &&responseText.indexOf("<!DOCTYPE html>") >-1&&responseText.indexOf("登录") >-1) {
            return true;
        }
    }
    return false;
}

/**
 * 日期格式化
 * @param timestamp
 * @returns {*}
 */
function format(timestamp){
    if(!timestamp){
        return '';
    }

    var time = new Date(timestamp);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
}

function add0(time){
    if(time < 10){
        return '0' + time;
    }
    return time;
}

function addModel(message) {
    if(isNullOrEmpty(message)){
        message='加载中';
    }
    var modalHtml = '<div id="waitModalLayer">' +
        '<p class="bdc-wait-content">' +
        '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>' +
        '<span>'+message+'</span>' +
        '</p>' +
        '</div>';
    $('body').append(modalHtml);
}

function removeModal() {
    $("#waitModalLayer").remove();
}

function enableNextBtn() {
    $(".nextstep").removeAttr('disabled');
    $('.nextstep').removeClass("layui-btn-disabled");
}
function getContextPath(){
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
    // return "http://192.168.50.139:8088/realestate-portal-ui";
}


//下拉框添加删除图标
function renderSelectClose(form) {
    //判断默认情况下下拉框是否需要删除图标(当1.下拉框可编辑 2.内容不为空 3.有清空选项）
    $(".xzq-sele .layui-input-inline select").each(function () {
        if($(this).attr("disabled") !=="disabled" &&isNotBlank($(this).val()) &&$(this).find("option:contains('请选择')").length >0) {
            $(this).parents('.layui-input-inline').find('.layui-icon-close').show();
        }
    });

    //监听select选择
    form.on('select', function (data) {
        if(isNotBlank(data.value) &&$(this).parents(".xzq-sele").find("option:contains('请选择')").length >0) {
            $(this).parents('.layui-input-inline').find('.layui-icon-close').show();
        }
    });
    //监听删除图标点击
    $('.xzq-sele .layui-icon-close').on('click',function(item){
        $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
        $(this).hide();
        form.render("select");
        resetSelectDisabledCss();
    });
}

//空字符串不包括"(空格)",只特指""
function isNotBlank(object) {
    if (typeof object === "object" && !(object instanceof Array)) {
        var hasProp = false;
        for (var prop in object) {
            hasProp = true;
            break;
        }
        if (hasProp) {
            hasProp = [hasProp];
        } else {
            return false;
        }
        return hasProp;
    }
    return typeof object != "undefined" && object != "";
}

/**
 * 设置下拉框不可编辑的样式
 */
function resetSelectDisabledCss() {
    //select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
    $('.layui-select-disabled i').addClass("bdc-hide");
    $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
}

/**
 * 选中消除红框提示
 */
layui.use(['jquery','laydate'], function () {
    var $ = layui.jquery;

    $(function () {
        $('.layui-input').on('focus', function () {
            if ($(this).hasClass('layui-form-danger')) {
                $(this).removeClass('layui-form-danger');
            }
            if ($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')) {
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
        });
        $('.xm-input').on('click', function () {
            if ($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')) {
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
        });
    });
});

/**
 * elem 元素
 * 添加必填验证
 */
function addRequired(elem) {
    var attrVal = elem.attr("lay-verify");
    //添加必填验证
    if (!isNotBlank(attrVal)) {
        elem.attr("lay-verify", "required");
    } else if (attrVal.indexOf("required") < 0) {
        elem.attr("lay-verify", attrVal + "|required");
    }
    //添加必填背景色
    elem.parents(".layui-inline").addClass("bdc-not-null");
    var requiredArr = elem.parents(".layui-inline").find(".required-span");
    if(requiredArr.length ===0) {
        elem.parents(".layui-inline").find("label").prepend("<span class=\"required-span\"><sub>*</sub></span>");
    }
}

/**
 * elem 元素
 * 移除必填验证
 */
function removeRequired(elem) {
    var attrVal = elem.attr("lay-verify");
    //移除必填验证
    if (isNotBlank(attrVal) && attrVal.indexOf("required") > -1) {
        elem.attr("lay-verify", attrVal.replace("required", ""));
    }
    //移除必填背景色
    elem.parents(".layui-inline").removeClass("bdc-not-null");
    elem.parents(".layui-inline").find(".required-span").remove();

}

/**
 *
 * @param str 操作的字符串
 * @param where 去除空格的情况
 * @returns {*} 处理方式，all---所有空格；edge---两边；left——左边；right——右边
 */
function deleteWhitespace(str, where) {
    if (!isNullOrEmpty(str)) {
        switch (where) {
            case "all":
                return str.replace(/\s*/g, "");
            case "edge":
                return str.replace(/^\s*|\s*$/g, "");
            case "left":
                return str.replace(/^\s*/, "");
            case "right":
                return str.replace(/(\s*$)/g, "");
            default :
                return str.replace(/\s*/g, "");
        }
    } else {
        return "";
    }
}

// 表单元素增加 小锁 样式
function disabledAddFa(formid) {
    var disabledElArray = $(":disabled");
    if (isNotBlank(formid)) {
        disabledElArray = $("#" + formid).find(":disabled");
    }
    if (disabledElArray !== null && disabledElArray.length > 0) {
        for (var i = 0; i < disabledElArray.length; i++) {
            var disabledEl = disabledElArray[i];
            if (disabledEl.type !== "checkbox" && disabledEl.type !== "radio") {
                if ($(disabledEl).is("input") || $(disabledEl).is("textarea") || $(disabledEl).is("select")) {
                    var imgArray = $(disabledEl).parent().find("img");
                    if (imgArray.length === 0) {
                        $(disabledEl).parent().append("<img src=\"../../../static/lib/bdcui/images/lock.png\" alt=\"\">");
                        $(disabledEl).parent().addClass('bdc-date-disabled');
                    }
                    if ($(disabledEl).parent().find('.xm-select-dis').length > 0) {
                        $(disabledEl).parent().find('.xm-select-dis .xm-select--suffix + div').attr('title', $(disabledEl).parent().find('.xm-select-dis>.xm-input').attr('title'));
                    }
                }
            }
        }
    }

    $('.layui-select-disabled i').removeClass('layui-edge');
    $('.bdc-date-disabled i').removeClass('layui-icon-close');
    $('.layui-select-disabled i').addClass('bdc-hide');


    $('.layui-select-disabled .layui-input').attr('disabled', 'true').removeClass('layui-disabled');

    $('.layui-select-disabled .layui-input').attr('placeholder', ' ');
}


function enableFinishBtn() {
    $(".finish").removeAttr('disabled');
    $('.finish').removeClass("layui-btn-disabled");
}
/**
 * 数据中sql加密
 * @param bdcXtQlqtzkFjPzDO
 */
function encodeQlqtzkFj(bdcXtQlqtzkFjPzDO){
    if(!isNullOrEmpty(bdcXtQlqtzkFjPzDO.fjsjy)) {
        bdcXtQlqtzkFjPzDO.fjsjy = Base64.encode(bdcXtQlqtzkFjPzDO.fjsjy);
    }
    if(!isNullOrEmpty(bdcXtQlqtzkFjPzDO.qlqtzksjy)){
        bdcXtQlqtzkFjPzDO.qlqtzksjy = Base64.encode(bdcXtQlqtzkFjPzDO.qlqtzksjy);
    }
    if(!isNullOrEmpty(bdcXtQlqtzkFjPzDO.zxqlfjsjy)){
        bdcXtQlqtzkFjPzDO.zxqlfjsjy = Base64.encode(bdcXtQlqtzkFjPzDO.zxqlfjsjy);
    }
    if(!isNullOrEmpty(bdcXtQlqtzkFjPzDO.zxqlfjmb)){
        bdcXtQlqtzkFjPzDO.zxqlfjmb = Base64.encode(bdcXtQlqtzkFjPzDO.zxqlfjmb);
    }
    if(!isNullOrEmpty(bdcXtQlqtzkFjPzDO.qlqtzkmb)){
        bdcXtQlqtzkFjPzDO.qlqtzkmb = Base64.encode(bdcXtQlqtzkFjPzDO.qlqtzkmb);
    }
    if(!isNullOrEmpty(bdcXtQlqtzkFjPzDO.fjmb)){
        bdcXtQlqtzkFjPzDO.fjmb = Base64.encode(bdcXtQlqtzkFjPzDO.fjmb);
    }
}

/**
 * 数据中sql进行解密
 */
function decodeQlqtzkFj(bdcXtQlqtzkFjPzDO){
    if(!isNullOrEmpty(bdcXtQlqtzkFjPzDO.fjsjy)) {
        bdcXtQlqtzkFjPzDO.fjsjy = Base64.decode(bdcXtQlqtzkFjPzDO.fjsjy);
    }
    if(!isNullOrEmpty(bdcXtQlqtzkFjPzDO.qlqtzksjy)){
        bdcXtQlqtzkFjPzDO.qlqtzksjy = Base64.decode(bdcXtQlqtzkFjPzDO.qlqtzksjy);
    }
    if(!isNullOrEmpty(bdcXtQlqtzkFjPzDO.zxqlfjsjy)){
        bdcXtQlqtzkFjPzDO.zxqlfjsjy = Base64.decode(bdcXtQlqtzkFjPzDO.zxqlfjsjy);
    }
    if(!isNullOrEmpty(bdcXtQlqtzkFjPzDO.fjmb)) {
        bdcXtQlqtzkFjPzDO.fjmb = Base64.decode(bdcXtQlqtzkFjPzDO.fjmb);
    }
    if(!isNullOrEmpty(bdcXtQlqtzkFjPzDO.qlqtzkmb)){
        bdcXtQlqtzkFjPzDO.qlqtzkmb = Base64.decode(bdcXtQlqtzkFjPzDO.qlqtzkmb);
    }
    if(!isNullOrEmpty(bdcXtQlqtzkFjPzDO.zxqlfjmb)){
        bdcXtQlqtzkFjPzDO.zxqlfjmb = Base64.decode(bdcXtQlqtzkFjPzDO.zxqlfjmb);
    }
}