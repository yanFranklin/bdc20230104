/**
 * 应用公共JS
 */

// 英文逗号
var zf_yw_dh = ',';

// 鼠标悬停到单元格显示下拉按钮
// $('.bdc-table-box').on('mouseenter', 'td', function () {
//     if ($(this).text()) {
//         $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>')
//     }
//     $('.layui-table-grid-down').on('click', function () {
//         setTimeout(function () {
//             $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
//         }, 20);
//     });
// });

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
function bjRedColor(flag,data){
    if (data == undefined){
        return '';
    }
    if (flag == "false") {
        return '<span style="color: red">' + data + '</span>';
    }else{
        return data;
    }
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
function addModel() {
    var modalHtml = '<div id="waitModalLayer">'+
        '<p class="bdc-wait-content">'+
        '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>'+
        '<span>请稍等</span>'+
        '</p>'+
        '</div>';
    $('body').append(modalHtml);
}

// 去除遮罩
function removeModal() {
    $("#waitModalLayer").remove();
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
                    console.log(tdHtml);
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
        if (zdDm !== '' && zdDm !== 'undefined' && zdDm !== null) {
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
 * 获取所有字典
 */
function getZdList() {
    var zdList = {};
    $.ajax({
        url: "/realestate-supervise/rest/v1.0/bdcZd/list",
        dataType: "json",
        async: false,
        success: function (data) {
            zdList = $.extend({}, data);
        },
        error: function (e) {
            failMsg("获取字典表服务异常");
        }
    });
    return zdList;
}

/**
 * 获取系统所有的角色
 * @returns {{}}
 */
function getAllRoles() {
    var roleList = {};
    $.ajax({
        url: "/realestate-supervise/rest/v1.0/user/roles",
        dataType: "json",
        async: false,
        success: function (data) {
            roleList = $.extend({}, data);
        },
        error: function (e) {
            failMsg("获取角色信息异常");
        }
    });
    return roleList;
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


//设置IP
function getIP() {
    return document.location.protocol + "//" + window.location.host;
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

/**
 * 设置下拉框不可编辑的样式
 */
function resetSelectDisabledCss() {
    //select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
    $('.layui-select-disabled i').addClass("bdc-hide");
    $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
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
    debugger;
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

function isNumber(value) {
    var regPos = /^[0-9]+$/;
    if(regPos.test(value)){
        return true;
    }else{
        return false;
    }
}

function exportExcel(data,checkStatus){
    // 设置Excel基本信息
    var excelList = new Array();
    var excelName = data.config.cols[0];
    var fieldArray = new Array();
    // 获取excel表格第一列
    var nameList = new Array();
    for (let i = 1; i <excelName.length-1 ; i++) {
        nameList.push(excelName[i].title);
        fieldArray.push(excelName[i].field);
    }
    excelList.push(nameList);
    // 获取表格实际数据

    var excelData = checkStatus;
    if(checkStatus.length == 0){
        excelData = exportExcelData.content;
    }
    for (let i = 0; i < excelData.length; i++) {
        var dataList = new Array();
        for (let j = 0; j < fieldArray.length; j++) {
            dataList.push(excelData[i][fieldArray[j]]);
        }
        excelList.push(dataList);
    }


    $("#data").val(JSON.stringify(excelList));
    $("#title").val(document.title);
    $("#form").submit();
}

/**
 * 获取综合查询实体字段和汉字名称对照关系
 */
var stxx = new Array();
function cxstxx() {
    $.ajax({
        url: "/realestate-supervise/rest/v1.0/cxycjg/cxstxx",
        type: "GET",
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            if (data) {
                stxx = data;
            }
        }
    });
}

/**
 * 处理查询条件：将查询条件中的字段都转为中文含义展示
 * @param cxtj
 */
function resolveCxtj(cxtj) {
    if(isNullOrEmpty(cxtj) || !stxx) {
        return cxtj;
    }

    if(stxx && stxx.BdcZszmQO) {
        for(var key in stxx.BdcZszmQO) {
            var v = '"' + key + '"';
            cxtj = cxtj.replace(v, '"' + stxx.BdcZszmQO[key] + '"');
        }

        cxtj = cxtj.replace(/模糊类型":"0"/g, '模糊类型":"精确"');
        cxtj = cxtj.replace(/模糊类型":"1"/g, '模糊类型":"右模糊"');
        cxtj = cxtj.replace(/模糊类型":"2"/g, '模糊类型":"左模糊"');
        cxtj = cxtj.replace(/模糊类型":"3"/g, '模糊类型":"全模糊"');
    }

    return cxtj;
}

function resolveCxjg(cxjg) {
    if(isNullOrEmpty(cxjg) || !stxx) {
        return cxjg;
    }

    if(stxx && stxx.BdcZszmDTO) {
        for(var key in stxx.BdcZszmDTO) {
            var v = '"' + key + '"';
            cxjg = cxjg.replace(v, '"' + stxx.BdcZszmDTO[key] + '"');
        }
    }

    return cxjg;
}

function toHourMinute(minutes) {
    if (!minutes) return minutes;

    if (minutes < 60) {
        return minutes + "分钟";
    } else {
        if (minutes % 60 === 0) {
            return Math.floor(minutes / 60) + "小时";
        } else {
            return Math.floor(minutes / 60) + "小时" + (minutes % 60) + "分钟";
        }
    }
}

