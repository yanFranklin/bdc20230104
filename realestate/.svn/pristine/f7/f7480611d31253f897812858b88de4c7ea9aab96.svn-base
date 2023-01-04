/**
 * register-ui应用公共JS
 * @version 1.0, 2018/12/13
 */

// 判断当前流程是否生成权利
var sfscql;

//初始化层高
var cgList = [];
/**
 * 所需url地址
 * @type {string}
 */
var certificateUrl;
var registerUrl;
var registerUiUrl;
var printIp;
var zxdjProcessId;
var hstHttpUrl;
//请求后台Controller路径公共前缀
var BASE_URL = '/realestate-register-ui/rest/v1.0';

//统一每页条数的选择项
var commonLimits = [10, 20, 50, 100, 200, 500];
// 字典
var zdList = {};
/**
 * 贷款方式（作为常量，各地区自行维护）
 * @type {string[]}
 */
var dkfs = ['公积金贷款', '组合贷款', '商业贷款', '其它'];
// 宗地宗海号的长度
var zdzhhLength = 19;
// 不动产单元号的长度
var bdcdyhLength = 28;
//金额单位
var commonJedw = "";


/**
 * 打印模板地址
 */
// 证书模板地址
var zsModelUrl = "C:/GTIS/zs.fr3";
// 土地承包经营权证书模板地址
var tdcbjyqzsModelUrl = "C:/GTIS/tdcbjyqzs.fr3";
// 农村不动产确权登记证书模板地址
var ncbdcqqdjzsModelUrl = "C:/GTIS/ncbdcqqdjzs.fr3";
/*
* 常州证书打印分页数打印，定义页数的打印类型
* */
// 证书模板地址
var zs1ModelUrl = "C:/GTIS/zs1.fr3";
// 土地承包经营权证书模板地址
var tdcbjyqzs1ModelUrl = "C:/GTIS/tdcbjyqzs1.fr3";
// 农村不动产确权登记证书模板地址
var ncbdcqqdjzsModelUrl1 = "C:/GTIS/ncbdcqqdjzs1.fr3";
// 证书模板地址
var zs2ModelUrl = "C:/GTIS/zs2.fr3";
// 土地承包经营权证书模板地址
var tdcbjyqzs2ModelUrl = "C:/GTIS/tdcbjyqzs2.fr3";
// 农村不动产确权登记证书模板地址
var ncbdcqqdjzsModelUrl2 = "C:/GTIS/ncbdcqqdjzs2.fr3";
// 证书模板地址
var zs3ModelUrl = "C:/GTIS/zs3.fr3";
// 土地承包经营权证书模板地址
var tdcbjyqzs3ModelUrl = "C:/GTIS/tdcbjyqzs3.fr3";
// 农村不动产确权登记证书模板地址
var ncbdcqqdjzsModelUrl3 = "C:/GTIS/ncbdcqqdjzs3.fr3";
// 证明模板地址
var zmModelUrl = "C:/GTIS/zm.fr3";
// 证明单模板地址
var zmdModelUrl = "C:/GTIS/zmd.fr3";
// 证明单模板地址
var ckzmdModelUrl = "C:/GTIS/ckzmd.fr3";
//宗地图模板地址
var zdtModelUrl = "C:/GTIS/zdt.fr3";
//户室图模板地址
var hstModelUrl = "C:/GTIS/hst.fr3";
// 房屋清单模板地址
var fwqdModelUrl = getIP() + "/realestate-register-ui/static/printModel/fwqd.fr3";
// 证书附表模板地址
var zsfbModelUrl = getIP() + "/realestate-register-ui/static/printModel/zsfb.fr3";
// 证书单列表模板地址
var zmdlbModelUrl = getIP() + "/realestate-register-ui/static/printModel/zmdlb.fr3";
// 家庭成员附表模板地址
var jtcyfbModelUrl = getIP() + "/realestate-register-ui/static/printModel/jtcyfb.fr3";
// 地块附表模板地址
var dkfbModelUrl = getIP() + "/realestate-register-ui/static/printModel/dkfb.fr3";

// 证明附表模板地址
var zmfbModelUrl = getIP() + "/realestate-register-ui/static/printModel/zmfb.fr3";
// 证书附表预览模板地址
var zsfbylModelUrl = getIP() + "/realestate-register-ui/static/printModel/zsfbyl.fr3";
// 证明附表预览模板地址
var zmfbylModelUrl = getIP() + "/realestate-register-ui/static/printModel/zmfbyl.fr3";
// 审批表模板地址
var sqSpbModelUrl = getIP() + "/realestate-register-ui/static/printModel/bdcSqSpb.fr3";
// 审批表模板地址
var dyaSqSpbModelUrl = getIP() + "/realestate-register-ui/static/printModel/dyabdcSqSpb.fr3";
// 审批表二审模板地址
var sqSpbTwoModelUrl = getIP() + "/realestate-register-ui/static/printModel/bdcSqSpbTwo.fr3";
var dyaSqSpbTwoModelUrl = getIP() + "/realestate-register-ui/static/printModel/dyabdcSqSpbTwo.fr3";
// 移交单打印模板地址
var yjdModelUrl = getIP() + "/realestate-register-ui/static/printModel/yjd.fr3";
// 抵押注销移交单打印模板地址
var yjdDyzxModelUrl = getIP() + "/realestate-register-ui/static/printModel/yjdDyzx.fr3";
// 交接单打印模板地址
var jjdModelUrl = getIP() + "/realestate-register-ui/static/printModel/jjd.fr3";
// 发证记录打印模板地址
var fzjlModelUrl = getIP() + "/realestate-register-ui/static/printModel/fzjl.fr3";
// 抵押物清单打印模板地址
var dyawqdModelUrl = getIP() + "/realestate-register-ui/static/printModel/dyawqd.fr3";
// 抵押物清单打印模板地址
var dyawqdlqModelUrl = getIP() + "/realestate-register-ui/static/printModel/dyawqdlq.fr3";
// 原抵押物清单打印模板地址
var ydyawqdModelUrl = getIP() + "/realestate-register-ui/static/printModel/ydyawqd.fr3";
// 预告抵押物清单打印模板地址
var ygDyawqdModelUrl = getIP() + "/realestate-register-ui/static/printModel/ygdyawqd.fr3";
// 原预告抵押物清单打印模板地址
var ygYdyawqdModelUrl = getIP() + "/realestate-register-ui/static/printModel/ygYdyawqd.fr3";
// 抵押注销证明打印模板地址
var dyzxzmModelUrl = getIP() + "/realestate-register-ui/static/printModel/dyzxzm.fr3";
// 预告注销证明打印模板地址
var ygdjzxzmModelUrl = getIP() + "/realestate-register-ui/static/printModel/ygdjzxzm.fr3";

// 不动产单元列表打印模板地址
var bdcdyListModelUrl = getIP() + "/realestate-register-ui/static/printModel/bdcdyList.fr3";
//档案目录信息图片打印模板地址
var daxxImageModelUrl = getIP() + "/realestate-register-ui/static/printModel/daxx.fr3";
// var daxxImageModelUrl = "C:/GTIS/daxx.fr3";

var zxqdModelUrl = getIP() + "/realestate-register-ui/static/printModel/zxqd.fr3";
// 权属清册打印模板地址
var qsqcMpdelUrl = getIP() + "/realestate-register-ui/static/printModel/qsqc.fr3";
// 清册打印模板地址
var qcMpdelUrl = getIP() + "/realestate-register-ui/static/printModel/dwsfqc.fr3";

var hnmlMpdelUrl = getIP() + "/realestate-register-ui/static/printModel/hnml.fr3";
/*
* 审批表打印地址前缀
* */
var spbModelPerfix = getIP() + "/realestate-register-ui/static/printModel/";

// 时间格式：'yyyy-MM-dd'
var dateFormat = 'yyyy-MM-dd';
// 证书样式-首次证明单
var sczmdModel = "sczmd";
// 证书样式-证明单
var zmdModel = "zmd";
// 证书样式-证书
var zsModel = 1;
// 证书样式-证明
var zmModel = 2;
// 证明单类型
var zmdModel = 3;
//地块附表
var dkfbModel = 4;
// 项目权属状态-现势
var qsztValid = 1;
// 权属状态-临时
var qsztTempory = 0;
// 权属状态-历史
var qsztHistory = 2;

// 发证记录类型——批量打印
var fzjlList = "fzjlList";
// 发证记录类型——按项目打印
var fzjl = "fzjl";
// 发证记录类型——按流程合并打印
var fzjlHb = "fzjlHb";

// 字符，英文逗号
var zf_ywdh = ',';

//项目内多幢,不需要同步分幢面积之和到建筑面积的宗地特征码
var nosumfzmjZdtzm="";

//登记簿返回的色值
var djbxssjys = "#1";
//登记簿需要改变颜色的下标
var qsztindex;
/**
 * 获取URL地址中的参数
 * @param name 参数名称
 */
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (null != r) return unescape(r[2]);
        return null;
    }
})(jQuery);

$(function () {
    //获取配置url地址
    queryUrl();

    var $cnotentTitle = $('.bdc-content-fix');
    var $titleBox = $('.content-title-box');
    var $navContainer = $('.bdc-nav-container');
    $(window).resize(function () {
        defaultStyle();
    });
    $(window).on('scroll', function () {
        if ($(this).scrollTop() > 85) {
            $cnotentTitle.css('top', '0');
            $titleBox.css('top', '0');
            $navContainer.css('top', '69px');
        } else if ($(this).scrollTop() <= 85) {
            $cnotentTitle.css('top', '15px');
            $titleBox.css('top', '15px');
            $navContainer.css('top', '84px');
        }
    });
});

$(document).ajaxError(
    //所有ajax请求异常的统一处理函数，处理无登陆权限
    function (event, xhr, options, exc) {
        if (!isNullOrEmpty(xhr.responseText) && xhr.responseText.indexOf("登录") > -1
            && xhr.responseText.indexOf("用户名") > -1 && xhr.responseText.indexOf("密码") > -1) {
            window.top.location.reload();
        }
    }
);

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

// 获取当前项目或者流程是否生成权利(该方法同步去请求，避免页面加载慢)

function checkSfscql(gzlslid, xmid) {
    if (isNullOrEmpty(gzlslid)) {
        gzlslid = '';
    }
    if (isNullOrEmpty(xmid)) {
        xmid = '';
    }
    $.ajax({
        url: BASE_URL + '/bdcdy/sfscql?gzlslid=' + gzlslid + '&xmid=' + xmid,
        type: "GET",
        dataType: "json",
        success: function (data) {
            sfscql = data;
        }
    });
}

// 获取字典信息
function getZdList() {
    $.ajax({
        url: BASE_URL + '/qlxx/zd',
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            if (data) {
                zdList = data;
            }
        }
    });
    return zdList;
}

// 获取页面配置Url地址
function queryUrl() {
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/url',
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data) {
                registerUrl = data.registerUrl;
                registerUiUrl = data.registerUiUrl;
                certificateUrl = data.certificateUrl;
                commonJedw=data.jedw;
                // 获取cgList数组
                cgList = data.cgList;
                printIp = data.printIp;
                zxdjProcessId = data.zxdjProcessId;
                nosumfzmjZdtzm =data.nosumfzmjZdtzm;
                hstHttpUrl = data.hstHttpUrl;
                djbxssjys = data.djbxssjys;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

// 获取当前系统的请求路径
function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

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

//设置IP
function getIP() {
    return document.location.protocol + "//" + window.location.host;
}

// 常州的特殊处理（由于金坛采用映射出去的ip访问），所以需要采用市区配置 ip:port
function getPrintIp(){
    if(isNotBlank(printIp)){
        return printIp;
    }
    return document.location.protocol + "//" + window.location.host;
}

function defaultStyle() {
    var $cnotentTitle = $('.bdc-content-fix');
    var $navContainer = $('.bdc-nav-container');
    if ($cnotentTitle.length == 1) {
        $('.bdc-form-div').css('padding-top', '84px');
    }
    if ($(window).scrollTop() > 85) {
        $cnotentTitle.css('top', '0');
        $navContainer.css('top', '69px');
    } else if ($(window).scrollTop() <= 85) {
        $cnotentTitle.css('top', '15px');
        $navContainer.css('top', '84px');
    }
}

/**
 * bdcdyh 不动产单元号
 * 验证是否为虚拟单元号(定着物特征码前5位为00000，且权利类型非海域)
 */
function checkXndyh(bdcdyh, qllx) {
    if (isNotBlank(bdcdyh) && bdcdyh.length === 28) {
        var zdzhsxh = bdcdyh.substr(6, 6);
        var hyArr = [15, 16, 27];
        if (zdzhsxh === "000000" && hyArr.indexOf(qllx) < 0) {
            return true;
        } else {
            return false;
        }
    } else {
        warnMsg("不动产单元号格式错误！");
        return false;
    }
}

/**
 * 格式化 bdcdyh
 * @param bdcdyh 不动产单元号
 * @return {string} 返回格式化的不动产单元号字符串
 */
function formatBdcdyh(bdcdyh) {
    var result;
    if (!isNullOrEmpty(bdcdyh) && bdcdyh.length == 28) {
        result = bdcdyh.substring(0, 6) + ' '
            + bdcdyh.substring(6, 12) + ' '
            + bdcdyh.substring(12, 19) + ' '
            + bdcdyh.substring(19, 28);
    } else {
        result = bdcdyh;
    }
    return result;
}

/**
 * 权属状态格式化
 * @param cellValue
 * @returns {string}
 */
function formatQszt(qszt) {
    if (qszt == 0) {
        return '<span class="" style="color:orange;">临时</span>';
    } else if (qszt == 1) {
        return '<span class="" style="color:green;">现势</span>';
    } else if (qszt == 2) {
        return '<span class="" style="color:gray;">历史</span>';
    } else if (qszt == 3) {
        return '<span class="" style="color:black;">终止</span>';
    } else {
        return '';
    }
}

/**
 * (历史关系页面)权属状态格式化
 * @param cellValue
 * @returns {string}
 */
function formatLsgxQszt(qszt, ajzt) {
    if (qszt == 0) {
        return '<span class="" style="color:orange;">临时</span>';
    } else if (qszt == 1) {
        return '<span class="" style="color:green;">现势</span>';
    } else if (qszt == 2) {
        return '<span class="" style="color:gray;">历史</span>';
    } else if (qszt == 3) {
        if (ajzt == 5) {
            return '<span class="" style="color:black;">撤回</span>';
        } else {
            return '<span class="" style="color:black;">终止</span>';
        }
    } else {
        return '';
    }
}

/**
 * 判断字符串是否为空
 *
 * @param str  目标字符串
 * @returns {boolean}  为空：true ; 不为空：false
 */
function isNullOrEmpty(str) {
    if (!str || "" == str || "null" == str || undefined == str || "undefined" == str || str.length == 0) {
        return true;
    }

    return false;
}

/**判断数组对象为空
 *
 * @param e
 * @returns {boolean}
 */
function isEmptyObject(e) {
    var t;
    for (t in e)
        return !1;
    return !0
}

/**
 * 渲染日期
 * @param format 日期格式
 */
function renderDate(format) {
    var laydate = layui.laydate;
    if (isNullOrEmpty(format)) {
        format = 'yyyy-MM-dd';
    }

    lay('.date').each(function () {
        // 先格式化已有数据的时间格式
        if (!isNullOrEmpty(this.value)) {
            this.value = Format(this.value, format);
        }
        laydate.render({
            elem: this
            , trigger: 'click'
            , format: format
        });
    });
}

/**
 * 渲染日期，表单有土地使用期限的使用
 * 回调方法不放上一个，避免input同名问题
 *  @param format 日期格式
 */
function renderDateOfTdsyqx(format, xmid) {
    var laydate = layui.laydate;
    if (isNullOrEmpty(format)) {
        format = 'yyyy-MM-dd';
    }
    var syqx = getTdsyqx(xmid);

    if (syqx > 0) {
        lay('.date').each(function () {
            // 先格式化已有数据的时间格式
            if (!isNullOrEmpty(this.value)) {
                this.value = Format(this.value, format);
            }

            laydate.render({
                elem: this
                , trigger: 'click'
                , format: format
                , done: function (value, date, endDate) {
                    // 如果选择土地使用期限开始时间，自动计算结束时间
                    // 土地使用期限：syqx
                    if ('tdsyqssj' == this.elem[0].id && syqx > 0) {
                        var year = date.year + syqx;
                        var month = date.month < 10 ? '0' + date.month : date.month;
                        var day = date.date < 10 ? '0' + date.date : date.date;
                        $("#tdsyjssj").val(year + '-' + month + '-' + day);
                    }
                }
            });
        });
    }
}

/**
 *
 * @param xmid
 */
function getTdsyqx(xmid) {
    var syqx = 0;
    $.ajax({
        url: BASE_URL + '/qlxx/tdsyqx/' + xmid,
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            if (data && data.syqx) {
                syqx = data.syqx;
            }
        }
    });
    return syqx;
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
        datetime = new Date(datetime);
    } else {
        // 非数字类型的替换-成/,经测试
        // 2019-10-10和2019/10/10都可以转换，这里替换是什么原因
        // by cyc
        datetime = new Date(datetime.replace(/-/g, "/"));
    }
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
 * 处理长整形时间
 * @param timestamp
 * @returns {string}
 */
function format(timestamp) {
    if (!timestamp) {
        return '';
    }

    var time = new Date(timestamp);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
}

function add0(time) {
    if (time < 10) {
        return '0' + time;
    }
    return time;
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

/**
 * 统一加载遮罩
 */
//把添加遮罩及移除遮罩方法添加在自己的通用js中，或者引入当前js，注释上面两行，调用即可使用
function addModel() {
    var modalHtml = '<div id="waitModalLayer">' +
        '<p class="bdc-wait-content">' +
        '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>' +
        '<span>加载中</span>' +
        '</p>' +
        '</div>';
    $('body').append(modalHtml);
}

/**
 * 遮罩移除
 */
function removeModel() {
    $("#waitModalLayer").remove();
}

//警告框
function showAlertDialog(alertMsg) {
    var alertIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '信息',
        area: ['320px'],
        content: alertMsg,
        btn: ['确定'],
        btnAlign: 'c',
        yes: function () {
            layer.close(alertIndex);
        }
    });

}

/**
 * 警告提示
 * @param msg
 */
function warnMsg(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}

/**
 * 成功提示
 * @param msg
 */
function successMsg(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">' + msg, {
            time: 1000
        }
    );
}

function failMsg(msg){
    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">' + msg);
}

function ERROR_CONFIRM(title, msg) {
    layer.confirm(msg, {icon: 5, btn: '确认', title: title}, function (index) {
        layer.close(index);
    });
}

//处理ajax的错误
function delAjaxErrorMsg(e, message) {
    layer.closeAll();
    var msg = '请求异常！';
    var detail = '';
    if (message != '' && message != undefined) {
        msg = message;
    }
    if (e.status == 500) {
        var responseText = JSON.parse(e.responseText);
        msg = (message != '' && message != undefined) ? msg : responseText.msg;
        detail = responseText.detail;
    }
    //session失效
    // if(isSessionTimeout(e)){
    //     layer.msg('会话超时，即将刷新页面。');
    //     window.location.reload();
    //     return false;
    // }
    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">' + msg + ' <a class="bdc-show-more-tips" href="javascript:;">更多</a>', {
        time: 4000,
        success: function () {
            if ($('#otherTips').length == 0) {
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
 * 验证联系电话,包括手机号码，固话,为空时直接验证通过
 */
function validatePhone(lxdh) {
    var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;//电话号码
    var isMob = /^0?1[3|4|5|6|7|8|9][0-9]\d{8}$/;//手机号码
    if (!isNotBlank(lxdh) || isMob.test(lxdh) || isPhone.test(lxdh)) {
        return true;
    } else {
        return false;
    }
}

/**
 * 对传入的字符串进行去空格处理
 * @date 2019.03.14 18:43
 * @author hanyaning
 * @param str 要处理的字符串
 * @param where 处理方式，all---所有空格；edge---两边；left——左边；right——右边
 * @return
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
 * 验证身份证号码格式
 * @param value
 * @param item
 */
function verifyIdNumber(value, item) {
    var isSubmit;
    var verifyMsg;
    var yzxx = {};
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

    if (!value || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(value)) {
        //$(item).addClass('layui-form-danger');
        yzxx.isSubmit = false;
        yzxx.verifyMsg = "身份证号格式错误";
    } else if (!city[value.substr(0, 2)]) {
        // $(item).addClass('layui-form-danger');
        yzxx.isSubmit = false;
        yzxx.verifyMsg = "地址编码错误";
    } else {
        //18位身份证需要验证最后一位校验位
        if (value !== null && value !== "" && value.length === 18) {
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
                //$(item).addClass('layui-form-danger');
                yzxx.isSubmit = false;
                yzxx.verifyMsg = "校验位错误";
            }
        }
    }
    return yzxx;
}

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
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

//下拉框添加删除图标
function renderSelectClose(form) {
    //判断默认情况下下拉框是否需要删除图标(当1.下拉框可编辑 2.内容不为空 3.有清空选项）
    $(".xzq-sele .layui-input-inline select").each(function () {
        if ($(this).attr("disabled") !== "disabled" && isNotBlank($(this).val()) && $(this).find("option:contains('请选择')").length > 0) {
            $(this).parents('.layui-input-inline').find('.layui-icon-close').show();
        }
    });

    //监听select选择
    form.on('select', function (data) {
        if (isNotBlank(data.value) && $(this).parents(".xzq-sele").find("option:contains('请选择')").length > 0) {
            $(this).parents('.layui-input-inline').find('.layui-icon-close').show();
        }
    });
    //监听删除图标点击
    $('.xzq-sele .layui-icon-close').on('click', function (item) {
        $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
        $(this).hide();
        form.render("select");
        resetSelectDisabledCss();
    });
}

/**
 * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
 * @description 替换中文 括号 为英文括号
 */
function replaceBracket(str) {
    if (str == '' || str == undefined) {
        return str;
    }
    if (str.indexOf("（") != -1) {
        str = str.replace(new RegExp("（", "gm"), "(");
    }
    if (str.indexOf("）") != -1) {
        str = str.replace(new RegExp("）", "gm"), ")");
    }
    return str;
}


// 用户IP地址
var ipaddress;

// 获取用户IP地址
try {
    ipaddress = $("#ipaddress").val();
    if (isNullOrEmpty(ipaddress)) {
        getUserIP(function (ip) {
            if (ip != null && ip != undefined) {
                ipaddress = ip;
            }
        });
    }
} catch (err) {
    console.info("获取IP地址出错：" + err.toString());
}

// 保存记录操作信息
function saveDetailLog(logType, viewTypeName, data) {
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
        url: getContextPath() + "/rest/v1.0/log/info",
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

if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function (elt /*, from*/) {
        var len = this.length >>> 0;

        var from = Number(arguments[1]) || 0;
        from = (from < 0)
            ? Math.ceil(from)
            : Math.floor(from);
        if (from < 0)
            from += len;

        for (; from < len; from++) {
            if (from in this && this[from] === elt)
                return from;
        }
        return -1;
    };
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
 * 获取到 不动产单元的状态
 * @param bdcdyh
 * @return {string}
 */
function getBdcdyZt(bdcdyh,qjgldm) {
    var bdcdyZt = '<p class="bdc-table-state bdc-ql-state">';
    $.ajax({
        url: getContextPath() + '/rest/v1.0/blxx/bdcdyzt?bdcdyh=' + bdcdyh +"&qjgldm="+qjgldm,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data !== null) {
                if (data.yg) {
                    bdcdyZt += '<span class="bdc-yg">已预告</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.yg) {
                    bdcdyZt += '<span class="bdc-yg">宗地预告</span>';
                }
                if (data.ycf) {
                    bdcdyZt += '<span class="bdc-ycf">预查封</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.ycf) {
                    bdcdyZt += '<span class="bdc-ycf">宗地预查封</span>';
                }
                if (data.ydya) {
                    bdcdyZt += '<span class="bdc-ydy">预抵押</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.ydya) {
                    bdcdyZt += '<span class="bdc-ydy">宗地预抵押</span>';
                }
                if (data.cf) {
                    bdcdyZt += '<span class="bdc-cf">查封</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.cf) {
                    bdcdyZt += '<span class="bdc-cf">宗地查封</span>';
                }
                if (data.dya) {
                    bdcdyZt += '<span class="bdc-dya">抵押</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.dya) {
                    bdcdyZt += '<span class="bdc-dya">宗地抵押</span>';
                }
                if (data.yy) {
                    bdcdyZt += '<span class="bdc-yy">异议</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.yy) {
                    bdcdyZt += '<span class="bdc-yy">宗地异议</span>';
                }
                if (data.sd) {
                    bdcdyZt += '<span class="bdc-sd">锁定</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.sd) {
                    bdcdyZt += '<span class="bdc-sd">宗地锁定</span>';
                }
                if (data.dyi) {
                    bdcdyZt += '<span class="bdc-dy">地役</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.dyi) {
                    bdcdyZt += '<span class="bdc-dy">宗地地役</span>';
                }
                if (data.zjgcdy) {
                    bdcdyZt += '<span class="bdc-zjgcdy">在建工程抵押</span>';
                }
                if (data.ks) {
                    bdcdyZt += '<span class="bdc-ks">可售</span>';
                }
                if (data.ys) {
                    bdcdyZt += '<span class="bdc-ys">已售</span>';
                }
                if (data.xjspfks) {
                    bdcdyZt += '<span class="bdc-xjspfks">新建商品房可售</span>';
                }
                if (data.xjspfys) {
                    bdcdyZt += '<span class="bdc-xjspfys">新建商品房已售</span>';
                }
                if (data.clfks) {
                    bdcdyZt += '<span class="bdc-clfks">存量房可售</span>';
                }
                if (data.clfys) {
                    bdcdyZt += '<span class="bdc-clfys">存量房已售</span>';
                }
                if (bdcdyZt.indexOf("span") < 0) {
                    bdcdyZt += '<span class="bdc-normal">正常</span>';
                }

                bdcdyZt += '</p>';
            }
        }, error: function (xhr, status, error) {
            // delAjaxErrorMsg(xhr)
        }
    });
    return bdcdyZt
}

/**
 * 获取权利信息页面
 */
function getQlxxYm(qllx, bdcdyfwlx) {
    var qllxym;
    qllx =parseInt(qllx);
    if(!isNullOrEmpty(bdcdyfwlx)) {
        bdcdyfwlx = parseInt(bdcdyfwlx);
    }
    var qlArr = {
        1: "tdsyq",
        2: "tdsyq",
        3: "jsydsyq",
        5: "jsydsyq",
        7: "jsydsyq",
        9: "tdcbjyqNyddqtsyq",
        11: "lq",
        12: "lq",
        13: "tdcbjyqNyddqtsyq",
        14: "tdcbjyqNyddqtsyq",
        15: "hysyq",
        16: "gzwsyq",
        17: "hysyq",
        18: "gzwsyq",
        19: "dyiq",
        23: "tdcbjyqNyddqtsyq",
        24: "gzwsyq",
        25: "gzwsyq",
        26: "gzwsyq",
        30:"tdcbjyqNyddqtsyq",
        31:"lq",
        33:"lq",
        34:"lq",
        35:"lq",
        36:"lq",
        50: "tdjyq",
        37: "dyaq",
        92: "jzq",
        94: "jzwqfsyqyzgybf",
        95: "dyaq",
        96: "ygdj",
        97: "yydj",
        98: "cfdj",
        99: "qtxgql"
    };
    if (qlArr[qllx] != undefined) {
        qllxym = qlArr[qllx];
    }
    switch (qllx) {
        case 4:
            if (bdcdyfwlx === 1) {
                qllxym = "fdcqXmndzfw";
            } else {
                qllxym = "fdcq";
            }
            break;
        case 6:
            if (bdcdyfwlx === 1) {
                qllxym = "fdcqXmndzfw";
            } else {
                qllxym = "fdcq";
            }
            break;
        case 8:
            if (bdcdyfwlx === 1) {
                qllxym = "fdcqXmndzfw";
            } else {
                qllxym = "fdcq";
            }
            break;
        default:
    }

    return qllxym;

}

//调用评价器方法
function commonPj(gzlslid,taskid){
    addModel();
    getReturnData("/rest/v1.0/pjq/commonpj",{gzlslid:gzlslid,taskid:taskid},"GET",function (data){
        if(data.pjqbb ==="MK_L"){
            removeModal();
            window.open(encodeURI(data.url));

        }else if(data.pjqbb ==="MK_N"){
            removeModal();
            if (data.pjjg && data.pjjg.code == "1") {
                successMsg("评价成功");
            } else {
                failMsg(data.pjjg.message);
            }
        } else if(data.pjqbb ==="QDWS"){
            // var qdwsData ={
            //     "success": true,
            //     "message": null,
            //     "data": {}
            // };
            $.ajax({
                url: data.url+"&s="+new Date().getTime(),
                type: "GET",
                contentType:"text/plain",
                // dataType: "jsonp",
                success: function (qdwsData) {
                    removeModal();
                    if(qdwsData.success ==true){
                        //成功
                        ityzl_SHOW_SUCCESS_LAYER("评价成功");
                    }else {
                        ityzl_SHOW_WARN_LAYER(qdwsData.message);
                    }
                },error: function (xhr, status, error) {
                    console.log(xhr)
                    delAjaxErrorMsg(xhr);
                }
            });
        }
        saveDetailLog("PJQPJ", "评价器评价操作",{"PJNR": JSON.stringify(data)});

    },function (error){
        delAjaxErrorMsg(error);
    });

}

//人证对比
function commonRzdb(qlrmc,zjh,processInsId){
    addModel();
    var slbh =$("#sjbh").val();
    var rzdbResult ={};
    getReturnData("/rest/v1.0/pjq/commonrzdb",{gzlslid:processInsId,qlrmc:qlrmc,qlrzjh:zjh},"GET",function (result){
        if(result.rzdbbb ==="MK_L"){
            removeModal();
            window.open(encodeURI(result.url));
        }else if(result.rzdbbb ==="MK_N"){
            removeModal();
            rzdbResult =result;
            if (result &&result.mkPjqDTO&& result.mkPjqDTO.code == "1") {
                if (result.mkPjqDTO.data.status == "0") {
                    showAlertDialog("姓名:" + result.mkPjqDTO.data.name + "身份证号:" + result.mkPjqDTO.data.cardNum + result.mkPjqDTO.data.reason);
                } else {
                    failMsg(result.mkPjqDTO.data.reason)
                }
            } else {
                failMsg(result.message);
            }
        } else if(result.rzdbbb ==="QDWS"){
            // var qdwsData={
            //     "data": {
            //         "name": "王一民",
            //         "sex": "男",
            //         "nation": "汉",
            //         "birth": "1990年12月12日",
            //         "address": "南京市建邺区奥体大街71号",
            //         "idNo": "320115199012121214",
            //         "police": "南京市公安局建邺分局",
            //         "validPeriod": "2015.11.29-2025.11.29",
            //         "photoBase64": "xxxxxxxxxxxx……",
            //         "cameraPhotoBase64": "xxxxxxxxxxxx……",
            //         "serviceId": "123",
            //         "score": 0.9251431,
            //         "result": 1
            //     },
            //     "message": "人证核验结束",
            //     "success": true
            // };
            $.ajax({
                url: result.url,
                type: "GET",
                contentType: 'application/json;charset=utf-8',
                success: function (qdwsData) {
                    removeModal();
                    rzdbResult =qdwsData;
                    if(qdwsData.success ==true){
                        if(qdwsData.data.result ==1){
                            successMsg("比对成功");
                            uploadFile("data:image/jpg;base64,"+qdwsData.data.cameraPhotoBase64,processInsId,"人证对比结果",qdwsData.data.idNo+"人证对比结果",".jpg");
                        }else{
                            failMsg("比对失败");
                        }
                    }else{
                        failMsg(qdwsData.message);
                    }
                },error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                }
            });
        }
        saveDetailLog("rzdb", "人证对比",{"RZDBNR": JSON.stringify(rzdbResult)});

    },function (error){
        delAjaxErrorMsg(error);
    });

}


//摩科签字
function mkqz(qlrmc,zjh,processInsId){
    addModel();
    debugger;
    var slbh =$("#sjbh").val();
    var qzResult ={};
    getReturnData("/rest/v1.0/pjq/mkqz",{gzlslid:processInsId,qlrmc:qlrmc,qlrzjh:zjh},"GET",function (result){
        removeModal();
        qzResult =result;
        if (result &&result.info == "1") {
            successMsg("签字成功");
        } else {
            failMsg(result.message);
        }
        saveDetailLog("mkqz", "摩科签字",{"MKQZ": JSON.stringify(qzResult)});

    },function (error){
        delAjaxErrorMsg(error);
    });

}

//上传人证对比结果
function uploadFile(base64,gzlslid,wjjmc,fjmc,wjhz){
    $.ajax({
        url: getContextPath()+"/rest/v1.0/pjq/upload/base64",
        type: "POST",
        data: JSON.stringify({"gzlslid": gzlslid, "base64str": base64,foldName:wjjmc,pdffjmc:fjmc,fileSuffix:wjhz}),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
        },
        error: function (xhr, status, error) {
            removeModal();
            failMsg("上传附件失败");
        }
    });

}
// 字符串的像素值
function getLenPx(str, font_size) {
    var str_leng = str.replace(/[^\x00-\xff]/gi, 'aa').length;
    return str_leng * font_size / 2
}
