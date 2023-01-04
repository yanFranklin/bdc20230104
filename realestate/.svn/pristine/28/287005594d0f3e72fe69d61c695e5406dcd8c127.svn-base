/**
 * inquiry-ui应用公共JS
 * @version 1.0, 2019/01/14
 */


// 英文逗号
var zf_yw_dh = ',';

//获取URL参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (null !== r) {
        if (decodeURI(r[2]) !== "null") {
            return decodeURI(r[2]);
        } else {
            return null
        }
    }
    return null;
}

/**
 *
 * @returns {string}
 */
function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}
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

/**
 * 格式化时间
 * @param datetime
 * @param fmt
 * @returns {*}
 * @constructor
 */
function Format(datetime, fmt) {
    if (isEmptyObject(datetime)) {
        return "";
    }
    if (parseInt(datetime) == datetime) {
        if (datetime.length == 10) {
            datetime = parseInt(datetime) * 1000;
        } else if (datetime.length == 13) {
            datetime = parseInt(datetime);
        }
    }
    datetime = new Date(datetime.replace(/-/g, "/"));
    var o = {
        "M+": datetime.getMonth() + 1,                 //月份
        "d+": datetime.getDate(),                    //日
        "h+": datetime.getHours(),                   //小时
        "m+": datetime.getMinutes(),                 //分
        "s+": datetime.getSeconds(),                 //秒
        "q+": Math.floor((datetime.getMonth() + 3) / 3), //季度
        "S": datetime.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (datetime.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 日期格式化
 * @param timestamp
 * @returns {*}
 */
function formatDate(timestamp){
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

/**
 * 日期格式化 年月日
 * @param timestamp
 * @returns {*}
 */
function formatRq(timestamp){
    if(!timestamp){
        return '';
    }

    var time = new Date(timestamp);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    return y+'-'+add0(m)+'-'+add0(d)+' ';
}

/**
 * 日期格式化（兼容合并过来的config-ui中使用的方法）
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

// 输入框删除图标
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

//点击高级查询-一般情况（非4的倍数）
$('#seniorSearchNormal').on('click',function () {
    $('.pf-senior-show-normal').toggleClass('bdc-hide');
    $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);

    if($(this).html() == '高级查询'){
        $(this).html('收起');
    }else {
        $(this).html('高级查询');
    }

    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
        $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
    }else {
        $('.layui-table-body').height($('.bdc-table-box').height() - 131);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
    }
});

//查询遮罩
function addModel(message) {
    if(isNullOrEmpty(message)){
        message='加载中';
    }
    var modalHtml = '<div id="waitModalLayer">'+
        '<p class="bdc-wait-content">'+
        '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>'+
        '<span>'+message+'</span>' +
        '</p>'+
        '</div>';
    $('body').append(modalHtml);
}

// 去除遮罩
function removeModal() {
    $("#waitModalLayer").remove();
}

function enableNextBtn() {
    $(".nextstep").removeAttr('disabled');
    $('.nextstep').removeClass("layui-btn-disabled");
}

// 设置列表高度
function setHeight(searchHeight) {
    if (isNullOrEmpty(searchHeight)) {
        searchHeight = 131;
    }
    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
        $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
    }else {
        $('.layui-table-body').height($('.bdc-table-box').height() - searchHeight);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - searchHeight - 17);
    }
}

$('.bdc-table-box').on('mouseenter','td',function () {
    if(typeof(reverseList) == "undefined"){
        return
    }
    if($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1){
        $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
    }
    $('.layui-table-grid-down').on('click',function () {
        setTimeout(function () {
            $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
        },20);
    });
});

// 设置列表高度（没有操作栏）
function setHeightWithNoToolbar(){
    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
        $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
    }else {
        $('.layui-table-body').height($('.bdc-table-box').height() - 81);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 81 - 17);
    }
}

// 列表字段翻转显示（长度过长前面省略号）
function reverseString(str) {
    if (!isNotBlank(str)) {
        return str;
    }
    str = str.replace(/&lt;/g, '>');
    str = str.replace(/&gt;/g, '<');
    var RexStr = /\（|\）|\(|\)|\【|\】|\【|\】|\[|\]|\[|\]/g;
    str = str.replace(RexStr, function (MatchStr) {
        switch (MatchStr) {
            case "(":
                return ")";
                break;
            case ")":
                return "(";
                break;
            case "（":
                return '）';
                break;
            case "）":
                return "（";
                break;
            case "[":
                return "]";
                break;
            case "]":
                return "[";
                break;
            case "【":
                return "】";
                break;
            case "】":
                return "【";
                break;
        }
    });
    return str.split("").reverse().join("");
}

function reverseTableCell(reverseList) {
    var getTd = $('.layui-table-view .layui-table td');
    for(var i = 0; i < getTd.length; i++){
        reverseList.forEach(function (v) {
            if($(getTd[i]).attr('data-field') == v){
                var getTdCell = $(getTd[i]).find('.layui-table-cell');
                getTdCell.css('direction','rtl');
                if(getTdCell.find('span').length == 0) {
                    var tdHtml = reverseString(getTdCell.html());
                    // console.log(tdHtml);
                    getTdCell.html('<span class="bdc-table-date">'+ tdHtml +'</span>');
                }
                // getTdCell.html('<span class="bdc-table-date">'+ getTdCell.html() +'</span>');
            }
        });
    }
}

/**
 * iframe加载完成触发事件
 * @param _domId
 * @param callback 函数名
 */
function iframeOnload(_domId, _callback) {
    if (_callback) {
        var iframe = document.getElementById(_domId);
        if (iframe) {
            if (iframe.attachEvent) {
                iframe.attachEvent('onload', function () {
                    eval(_callback + "()");
                });
            } else {
                iframe.onload = function () {
                    eval(_callback + "()");
                };
            }
        }
    }
}

/**
 * 默认值
 * @param value
 * @param defaultValue
 * @returns {*}
 */
function defaultValue(value, defaultValue) {
    if (isNullOrEmpty(value)) {
        if (isNullOrEmpty(defaultValue)) {
            defaultValue = '';
        }
        value = defaultValue;
    }
    return value;
}

/**
 * 判断对象是否属性是否都为 ''
 * @param obj
 * @returns {boolean}
 */
function isEmptyObject(obj) {
    var flag = true;
    if (obj != undefined && obj != null) {
        for (prop in obj) {
            if (obj[prop] != '') {
                flag = false;
            }
        }
    }
    return flag;
}

// 格式化不动产单元号
function formatBdcdyh(bdcdyh) {
    var result;
    if (!isNullOrEmpty(bdcdyh) && bdcdyh.length == 28) {
        result = bdcdyh.substring(0, 6) + ' '
            + bdcdyh.substring(6, 12) + ' '
            + bdcdyh.substring(12, 19) + ' '
            + bdcdyh.substring(19, 28);
    } else if(isNullOrEmpty(bdcdyh)){
        result = "";
    } else {
        result = bdcdyh;
    }
    return result;
}

/**
 * 前台的字典转换，代码转换为名称
 */
/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param zdname 字典名 bdclx
 * @param zdDm 字典代码 1
 * @param zdListName JS中保存字典数据的变量名 默认为zdList
 * @return
 * @description
 */
function convertZdDmToMc(zdname, zdDm, zdListName) {
    try {
        if (zdDm !== '' && zdDm !== 'undefined' && zdDm !== null && zdDm != undefined) {
            if (!zdListName) {
                zdListName = "zdList"
            }
            var zdVals = eval(zdListName + "." + zdname);
            if (zdVals) {
                for (var i = 0; i < zdVals.length; i++) {
                    var zdVal = zdVals[i];
                    if (zdDm == zdVal.DM) {
                        return zdVal.MC;
                    }
                }
            }
            return zdDm;
        }
        return '';
    } catch (e) {
        return "";
    }
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param zdname 字典名 bdclx
 * @param zdMc 字典mc,如首次登记
 * @param zdListName JS中保存字典数据的变量名 默认为zdList
 * @return
 * @description
 */
function convertZdMcToDm(zdname, zdMc, zdListName) {
    if (zdMc) {
        if (!zdListName) {
            zdListName = "zdList"
        }
        var zdVals = eval(zdListName + "." + zdname);
        if (zdVals) {
            for (var i = 0; i < zdVals.length; i++) {
                var zdVal = zdVals[i];
                if(zdVal) {
                    if (zdMc == zdVal.MC) {
                        return zdVal.DM;
                    }
                }
            }
        }
        return zdMc;
    }
    return "";
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param zdNames 字典名 逗号隔开 例："bdclx,bdcdyzt"
 * @return
 * @description
 */
function getMulZdList(zdNames) {
    var zdList = {};
    $.ajax({
        url: "/realestate-inquiry-ui/bdczd/mul",
        dataType: "json",
        data: {
            zdNames: zdNames
        },
        async: false,
        success: function (data) {
            zdList = $.extend({}, data);
        },
        error: function (e) {
            showErrorInfo(e, "获取字典表服务异常");
        }
    });
    return zdList;
}

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @param zdNames 字典名 逗号隔开 例："bdclx,bdcdyzt"
 * @return
 * @description
 */
function getSlMulZdList(zdNames) {
    var zdList = {};
    $.ajax({
        url: "/realestate-inquiry-ui/bdczd/sl/mul",
        dataType: "json",
        data: {
            zdNames: zdNames
        },
        async: false,
        success: function (data) {
            zdList = $.extend({}, data);
        },
        error: function (e) {
            showErrorInfo(e, "获取字典表服务异常");
        }
    });
    return zdList;
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param zdNames 字典名 逗号隔开 例："bdclx,bdcdyzt"
 * @return
 * @description 支持受理字典项,登记字典项,大云字典项
 */
function getAllMulZdList(zdNames,zdly) {
    var zdList = {};
    $.ajax({
        url: "/realestate-inquiry-ui/bdczd/all/mul",
        dataType: "json",
        data: {
            zdNames: zdNames,
            zdly:zdly
        },
        async: false,
        success: function (data) {
            zdList = $.extend({}, data);
        },
        error: function (e) {
            showErrorInfo(e, "获取字典表服务异常");
        }
    });
    return zdList;
}

/**
 * 依照返回的code，统一返回错误信息给用户
 * @date 2019.03.14 15:09
 * @author hanyaning
 * @param e ajax等请求的返回值
 * @param otherInfo 其它错误信息
 * @return
 */
function showErrorInfo(e, otherInfo) {
    var responseText = JSON.parse(e.responseText);
    var msg = "";
    var re4 = new RegExp("^4.*", "g")
    var re5 = new RegExp("^5.*", "g")
    if (re4.test(responseText.status)) {
        msg = "客户端异常";
    } else if (re5.test(responseText.status)) {
        msg = "服务器异常";
    } else {
        msg = responseText.msg;
    }
    if (!isNullOrEmpty(otherInfo)) {
        msg = msg + "，" + otherInfo;
    }
    layui.use(['layer'], function () {
        var layer = layui.layer;
        layer.msg(msg);
    });
    /*如果有遮罩，进行去除*/
    setTimeout(removeModal, 100);
    return true;
}

function alertMsg(msg){
    layer.alert("<div style='text-align: center'>" + msg + "</div>", {title: '提示'});
}

function failMsg(msg){
    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">' + msg);
}

function warnMsg(msg){
    layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}

function delSuccessMsg(){
    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">删除成功');
}

function delFailMsg(){
    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">删除失败，请重试');
}

function saveSuccessMsg(){
    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">提交成功');
}
function successMsg(msg){
    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">' + msg);
}

function saveFailMsg(){
    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">提交失败，请重试');
}

function errorsMsg(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">' + msg);
}

//设置IP
function getIP() {
    return document.location.protocol + "//" + window.location.host;
}

/**
 * 其它系统页面配置
 */
var htmlConfig = {
    /**
     * 权利信息页面
     */
    qlxxHtml: {
        //土地所有权登记信息
        tdsyqHtml: '/realestate-register-ui/view/qlxx/tdsyq.html',
        //建设用地使用权、宅基地使用权登记信息
        jsydsyqHtml: '/realestate-register-ui/view/qlxx/jsydsyq.html',
        //房地产权登记信息（独幢、层、套、间房屋）
        fdcqHtml: '/realestate-register-ui/view/qlxx/fdcq.html',
        //房地产权登记信息（项目内多幢房屋）
        fdcqXmndzfwHtml: '/realestate-register-ui/view/qlxx/fdcqXmndzfw.html',
        //建筑物区分所有权业主共有部分登记信息
        jzwqfsyqyzgybfHtml: '/realestate-register-ui/view/qlxx/jzwqfsyqyzgybf.html',
        //构（建）筑物所有权登记信息
        gzwsyqHtml: '/realestate-register-ui/view/qlxx/gzwsyq.html',
        //海域（含无居民海岛）使用权登记信息
        hysyqHtml: '/realestate-register-ui/view/qlxx/hysyq.html',
        //土地承包经营权、农用地的其他使用权登记信息（非林地）
        tdcbjyqNyddqtsyqHtml: '/realestate-register-ui/view/qlxx/tdcbjyqNyddqtsyq.html',
        //林权登记信息
        lqHtml: '/realestate-register-ui/view/qlxx/lq.html',
        //地役权登记信息
        dyiqHtml: '/realestate-register-ui/view/qlxx/dyiq.html',
        //抵押权登记信息
        dyaqHtml: '/realestate-register-ui/view/qlxx/dyaq.html',
        //预告登记信息
        ygdjHtml: '/realestate-register-ui/view/qlxx/ygdj.html',
        //异议登记信息
        yydjHtml: '/realestate-register-ui/view/qlxx/yydj.html',
        //查封登记信息
        cfdjHtml: '/realestate-register-ui/view/qlxx/cfdj.html',
        //其他相关权利登记信息（取水权、探矿权、采矿权等）
        qtxgqlHtml: '/realestate-register-ui/view/qlxx/qtxgql.html',
        //居住权
        jzqHtml: '/realestate-register-ui/view/qlxx/jzq.html'
    },

    /**
     * 历史关系展现台账
     */
    lsgxHtml: {
        lsgx: '/realestate-register-ui/view/lsgx/bdcdyDjLsgx.html'
    },
    /**
     * 登记簿
     */
    djbHtml:{
        bdcYyHtml:'/realestate-register-ui/view/djbxx/bdcDjbYy.html'
    },

    sdxxHtml:{
        //不动产锁定信息
        sdHtml:'/realestate-register-ui/view/sdxx/sd.html'
    },

}

/**
 * 检查用户是否输入了查询条件
 * 仅适用于对form组件的检查
 * @date 2019.03.19 18:48
 * @author hanyaning
 * @param cxtjClassName 标识查询条件控件统一的class属性，默认为”cxtj“
 * @return  true表示用户已输入
 */
function checkCxtjNotNull(cxtjClassName) {
    if (isNullOrEmpty(cxtjClassName)) {
        cxtjClassName = "cxtj";
    }
    var formItems = $("." + cxtjClassName);
    var result = false;
    $.each(formItems, function (i, data) {
        if (!isNullOrEmpty($(data).val())) {
            result = true;
            return false;
        }
    });
    return result;
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
 * 复选框背景设置颜色
 * @param colorList [{name:'bdc-change-red',color: '#ff0000'},{name:'bdc-change-green',color: 'green'}]
 * name: class名称
 * color：颜色值
 */
function changeCheckboxBackground(colorList) {
    colorList.forEach(function (v) {
        var $changeCheckbox = $('.'+ v.name);
        $changeCheckbox.each(function (i) {
            var getIndex = $($changeCheckbox[i]).parents('tr').index() + 1;
            $('.layui-table-fixed .layui-table-body tr:nth-child('+ getIndex +') .laytable-cell-checkbox').parent().css('background-color',v.color);
        });
    });
}

/**
 * 打印js
 * @returns {*}
 */
function print(modelUrl, dataUrl, hiddeMode) {
    var fr3Url = "v2|designMode=false|frURL=" + modelUrl
        + "|dataURL=" + dataUrl
        + "|updateUrl=http://oa.gtis.com.cn:80/platform/pluging/update.ini|hiddeMode=" + hiddeMode;

    window.location.href = "eprt://" + fr3Url;

    // 打印后存储打印数据源和打印
    //savePrintInfo(modelUrl,dataUrl,{'zmbh':'123456'});
}

//出生日期的年月日验证
/**
 * @return {boolean}
 */
function YearMonthDayValidate(year, month, day) {
    year = parseInt(year); //年
    month = parseInt(month);//月
    day = parseInt(day);//日
    //判断年，月,日是否为空
    if (isNaN(year) || isNaN(month) || isNaN(day)) {
        return false;
    }
    //判断月是否是在1-12月之间
    if (month < 1 || month > 12) {
        return false;
    }
    //返回当月的最后一天
    var date = new Date(year, month, 0);
    //判断是否超过天数范围
    return !(day < 1 || day > date.getDate());


}

/**
 * value 证件号内容
 * 返回提示信息：verifyMsg
 * 验证身份证证件号码:15位身份证，18位身份证
 */
function checkSfzZjh(value) {
    //验证提示信息
    var verifyMsg = "";
    var city = {
        11: "北京",
        12: "天津",
        13: "河北",
        14: "山西",
        15: "内蒙古",
        21: "辽宁",
        22: "吉林",
        23: "黑龙江 ",
        31: "上海",
        32: "江苏",
        33: "浙江",
        34: "安徽",
        35: "福建",
        36: "江西",
        37: "山东",
        41: "河南",
        42: "湖北 ",
        43: "湖南",
        44: "广东",
        45: "广西",
        46: "海南",
        50: "重庆",
        51: "四川",
        52: "贵州",
        53: "云南",
        54: "西藏 ",
        61: "陕西",
        62: "甘肃",
        63: "青海",
        64: "宁夏",
        65: "新疆",
        71: "台湾",
        81: "香港",
        82: "澳门",
        91: "国外 "
    };

    if (!value || !/(^\d{15}$)|(^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$)/i.test(value) || (value.length !== 15 && value.length !== 18)) {
        verifyMsg = "身份证号格式错误";
    }
    else if (!city[value.substr(0, 2)]) {
        verifyMsg = "地址编码错误";
    }
    else if (value !== null && value !== "") {
        //18位身份证需要验证最后一位校验位
        if (value.length === 18) {
            value = value.split('');
            //∑(ai×Wi)(mod 11)
            //加权因子
            var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
            //校验位
            var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            for (var i = 0; i < 17; i++) {
                ai = value[i];
                wi = factor[i];
                sum += ai * wi;
            }
            if (parity[sum % 11] != value[17].toUpperCase()) {
                verifyMsg = "校验位错误";
            }
        } else if (value.length === 15) {
            value = value.toString();
            var re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
            var arrSplit = value.match(re); //检查出生日期是否正确
            if (arrSplit !== null) {
                if (parseInt(arrSplit[2].substr(1)) > 0) {
                    arrSplit[2] = "19" + arrSplit[2];
                } else {
                    arrSplit[2] = "20" + arrSplit[2]
                }
                if (!YearMonthDayValidate(arrSplit[2], arrSplit[3], arrSplit[4])) {
                    verifyMsg = "出生日期不正确";

                }
            } else {
                verifyMsg = "出生日期不正确";
            }
        }
    }

    return verifyMsg;

}


/**
 * 整行tr背景设置颜色,最右侧操作列保持不变
 * @param colorList [{name:'bdc-change-red',color: '#000',background:'red'},{name:'bdc-change-green',color: 'green',background:'#fff'}]
 * @param tabTable 正常表格可不传值，tab下面的表格传值 true
 * name: class名称
 * color：字体颜色值，不传值默认白色
 * background：背景颜色值
 */
function changeTrBackgroundExceptRight(colorList,tabTable) {
    colorList.forEach(function (v) {
        var $changeCheckbox;
        if(tabTable){
            $changeCheckbox = $('.layui-show .'+ v.name);
        }else {
            $changeCheckbox = $('.'+ v.name);
        }
        $changeCheckbox.each(function (i) {
            var getIndex = $($changeCheckbox[i]).parents('tr').index() + 1;
            var getColor = v.color?v.color:"#fff";
            $('.layui-table-main tr:nth-child('+ getIndex +') td:not(.layui-table-col-special:last-child)').addClass('bdc-special-bdcdyh-color').css({'color': getColor,'background-color':v.background}).find('span').css('color', getColor);
            $('.layui-table-main tr:nth-child('+ getIndex +') .layui-table-col-special:last-child').css('background-color','transparent');
            $('.layui-table-fixed-l .layui-table-body tr:nth-child('+ getIndex +')').css('background-color',v.background).find('td').css('color', getColor);
            $('.layui-table-fixed-r .layui-table-body tr:nth-child('+ getIndex +') td:not(.layui-table-col-special:last-child)').addClass('bdc-special-bdcdyh-color').css({'color': getColor,'background-color':v.background}).find('span').css('color', getColor);

        });
    });
}

//信息小提示
function ityzl_SHOW_INFO_LAYER(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/info-small.png" alt="">' + msg);
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
    if (e.status == 302 || e.status == 401 || e.status == 403) {
        msg = "已离线，请刷新";
    }
    //session失效
    // if(isSessionTimeout(e)){
    //     layer.msg('会话超时，即将刷新页面。');
    //     window.location.reload();
    //     return false;
    // }
    layer.msg('<img src="../../static/image/error-small.png" alt="">' + msg + ' <a class="bdc-show-more-tips" href="javascript:;">更多</a>', {
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
 * 获取字典
 * @param callback 回调函数
 */
window.getCommonZd = function (callback) {
    var zdList = getZdList();
    if (zdList) {
        callback(zdList);
    }
};

// 获取字典信息
function getZdList() {
    var zdList = {};
    getReturnData("/bdczd", {}, "POST", function (data) {
        zdList = data;
    }, function () {
    }, false);
    return zdList;
}

//操作成功提示
function ityzl_SHOW_SUCCESS_LAYER(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">' + msg);
}


//信息小提示
function ityzl_SHOW_INFO_LAYER(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/info-small.png" alt="">' + msg);
}

//警告小提示
function ityzl_SHOW_WARN_LAYER(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}

function ERROR_CONFIRM(title, msg) {
    layer.confirm(msg, {icon: 5, btn: '确认', title: title}, function (index) {
        layer.close(index);
    });
}

/**
 * 兼容ie的日期转换
 * @param str
 * @returns {Date}
 */
function strFormatDate(str){
    return new Date(Date.parse(str.replace(/-/g, "/")));
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
//使用例子
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") //输出结果： 2017-01-23 09:36:10.400
// (new Date()).Format("yyyy-M-d h:m:s.S")      //输出结果： 2017-1-23 9:36:35.572
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

// 用户IP地址
var ipaddress;
// 获取用户IP地址
try {
    ipaddress = $("#ipaddress").val();
    if(isNullOrEmpty(ipaddress)) {
        getUserIP(function (ip) {
            if (ip != null && ip != undefined) {
                ipaddress = ip;
            }
        });
    }
} catch(err){
    console.info("获取IP地址出错：" + err.toString());
}

/**
 * 保存操作日志
 * @param logType 日志类型，这个和 logList.html 页面日志类型对应
 * @param viewTypeName 日志信息描述
 * @param data 附加日志信息内容
 */
function saveDetailLog(logType, viewTypeName, data){
    var json = JSON.parse(JSON.stringify(data));
    json.logType = logType;
    json.ipaddress = ipaddress;
    json.viewTypeName = viewTypeName;
    saveLogInfo(json);
}

/**
 * 保存日志信息
 * @param data 需要保存的日志数据
 */
function saveLogInfo(data) {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/log/info",
        type: "POST",
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (key) {
        },
        error: function () {
            console.debug("保存日志出错！");
        }
    });
}

/**
 * 延迟加载查询内容
 * @param cxtj 查询条件字段
 * @param cxjg 查询结果字段
 * @param url  查询字段接口url
 */
function ycjz(cxtj, cxjg, url) {
    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        if ($(getTd[i]).attr('data-field') == cxtj) {
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            if (isNotBlank(getTdCell[0].innerText)) {
                $.ajax({
                    url: url + "?" + cxtj + "=" +  getTdCell[0].innerText,
                    type: "get",
                    async: false,
                    success: function (data) {
                        if (isNotBlank(data)) {
                            var tdList = getTdCell.parent().parent().children();
                            for (var j = 0; j < tdList.length; j++) {
                                if ($(tdList[j]).attr('data-field') == cxjg) {
                                    var tdCell = $(tdList[j]).find('.layui-table-cell');
                                    tdCell[0].innerHTML = data;
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}

/**
 * 不动产登记档案
 * @param data
 */
function openBdcDjDa(data){
    var xmid = data.xmid;
    if(xmid){
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/url/common/bdcda?xmid="+xmid+"&gzlslid=" ,
            type: 'get',
            success: function (data) {
                console.log("不动产档案url:"+data);
                if(data){
                    window.open(data);
                }
            },
            error: function (err) {
                removeModal();
                layer.closeAll();
            }
        });
    }else{
        warnMsg("请求失败，该数据xmid为空！");
    }
}

/**
 * 页面电话加密显示(****)
 * @param value 需要加密的值
 */
function toEncryptValue(value) {
    var isPhone = /^\d{3}-\d{7,8}|\d{4}-\d{7,8}$/;//电话号码
    var isMob = /^0?1[3|4|5|6|7|8|9][0-9]\d{8}$/;//手机号码

    var resultPhone = value;
    if(isNotBlank(value)){
        if(isMob.test(value)){
            // 手机号替换中间四位
            resultPhone = value.replace(/^(\d{3})\d{4}(\d+)/, '$1****$2');
        }else if(isPhone.test(value)){
            // 固定电话替换区号后四位
            var dhArr = value.split('-');
            var dh = dhArr[1].replace(/\d{1,4}/, '****');;
            resultPhone = value.replace(dhArr[1],dh);
        }
    }
    return resultPhone;
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
                        $(disabledEl).parent().append("<img src=\"../../static/lib/bdcui/images/lock.png\" alt=\"\">");
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

/**
 * 保存查询日志到数据库（补充说明：综合查询等逻辑会有AOP拦截保存日志到ES）
 * @param searchData 查询条件
 * @param pageData 查询结果
 * @param logType 查询类型
 */
function saveQueryLogToDataBase(searchData, pageData, logType) {
    var logData = {};
    logData.cxtj   = JSON.stringify(searchData);
    logData.cxjg   = JSON.stringify(pageData);
    logData.qlrmc  = isNullOrEmpty(searchData.qlrmc) ? "" : searchData.qlrmc;
    logData.qlrzjh = isNullOrEmpty(searchData.qlrzjh) ? "" : searchData.qlrzjh;
    logData.zl     = isNullOrEmpty(searchData.zl) ? "" : searchData.zl;
    logData.bdcdyh = isNullOrEmpty(searchData.bdcdyh) ? "" : searchData.bdcdyh;
    logData.bdcqzh = isNullOrEmpty(searchData.bdcqzh) ? "" : searchData.bdcqzh;
    logData.rzlx   = logType;
    logData.cxjgsl = $.isEmptyObject(pageData) ? 0 : pageData.length;


    $.ajax({
        url: "/realestate-inquiry-ui/log/xtcx",
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(logData)
    });
}
/**
 * 保存综合查询台账页面综合查询以及打印记录操作的日志，存档到数据库（补充说明：综合查询等逻辑会有AOP拦截保存日志到ES）
 * @param searchData 查询条件
 * @param pageData 查询结果
 * @param logType 查询类型
 */
function saveZhcxLogToDataBase(searchData, pageData, logType, printType) {
    var logData = {};
    logData.cxtj   = JSON.stringify(searchData);
    logData.czjg   = JSON.stringify(pageData);
    logData.rzlx   = logType;
    logData.dylx   = printType;

    $.ajax({
        url: "/realestate-inquiry-ui/log/zhcx",
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(logData)
    });
}

/**
 * 表单table加载高度设置
 */
function setTableHeight(searchHeight) {
    if (isNullOrEmpty(searchHeight)) {
        searchHeight = 131;
    }
    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
        $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
    }else {
        $('.layui-table-body').height($('.bdc-table-box').height() - searchHeight);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - searchHeight - 17);
    }
}

/**
 * 遮罩移除
 */
function removeModel() {
    $("#waitModalLayer").remove();
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

/**
 * @date 2019.10.28
 * @author yaoyi
 * @description 将18位身份证号转换为15位身份证号
 */
function convertIdCard18To15(idCard){
    if(!isNotBlank(idCard)){
        return idCard;
    }
    if(idCard.length != 18){
        return idCard
    }
    return idCard.substring(0, 6) + idCard.substring(8, 17);
}

/**
 * 查看流程图
 * @param data 项目数据
 */
function lct(data) {
    var url = "";
    if(data && '1' === data.xmly){
        url = '/bpm-ui/process/processDetail/' + data.gzlslid;
    } else {
        url = '/realestate-register-ui/view/lsgx/lct.html?xmid=' + data.xmid + "&gzlslid=" + data.gzlslid
    }

    var index = layer.open({
        title: '流程详细页面',
        type: 2,
        area: ['1150px', '600px'],
        content: url
    });
    layer.full(index);
}

// arr是原数组，N是想分成多少个
function fenge(arr, N) {
    console.log(arr, N)
    var result = [];
    for (var i = 0; i < arr.length; i += N) {
        result.push(arr.slice(i, i + N));
    }
    return result
}

/**
 * @author <a href ="mailto:jinfei@gtmap.cn">jinfei</a>
 * @description 获取当前用户
 */
function queryCurrentUser() {
    var user = '';
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/user/info",
        async: false,
        data: {},
        success: function (data) {
            user = data;
        }, error: function (e) {
            response.fail(e, '');
        }
    });
    return user;
}