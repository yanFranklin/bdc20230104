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

//----------------------------------------------------------系统参数-----------------------------------------------------
//设置IP
function getIP() {
    return " http://" + window.location.host + "/realestate-e-certificate";
}

function getUrlParameter(name,url){
    name = name.replace(/[]/,"\[").replace(/[]/,"\[").replace(/[]/,"\\\]");
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec(url);
    if( results == null ) return ""; else {
        return results[1];
    }
}

//全局定义layer
var layer = function () {
    layui.use(['layer'], function () {
        return layui.layer;
    });
}();


//----------------------------------------------------------通用方法-----------------------------------------------------
//把添加遮罩及移除遮罩方法添加在自己的通用js中，或者引入当前js，注释上面两行，调用即可使用
function addModel(message) {
    if (isNullOrEmpty(message)) {
        message = '加载中';
    }
    var modalHtml = '<div id="waitModalLayer">' +
        '<p class="bdc-wait-content">' +
        '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>' +
        '<span>' + message + '</span>' +
        '</p>' +
        '</div>';
    $('body').append(modalHtml);
}


/**
 * 判断字符串是否为空
 *
 * @param str  目标字符串
 * @returns {boolean}  为空：true ; 不为空：false
 */
function isNullOrEmpty(str) {
    if (!str || "" == str || "null" == str || undefined == str || "undefined" == str) {
        return true;
    }

    return false;
}

function removeModal() {
    $("#waitModalLayer").remove();
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

// 设置列表高度
function setHeight(searchHeight) {
    if (isNullOrEmpty(searchHeight)) {
        searchHeight = 131;
    }
    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

    if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
        $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
    } else {
        $('.layui-table-body').height($('.bdc-table-box').height() - searchHeight);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - searchHeight - 17);
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

// 翻转字符串
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

// 处理台账中坐落字段
function reverseTableCell(reverseList) {
    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        reverseList.forEach(function (v) {
            if ($(getTd[i]).attr('data-field') == v) {
                var getTdCell = $(getTd[i]).find('.layui-table-cell');
                getTdCell.css('direction', 'rtl');
                if (getTdCell.find('span').length == 0) {
                    var tdHtml = reverseString(getTdCell.html());
                    getTdCell.html('<span class="bdc-table-date">' + tdHtml + '</span>');
                }
            }
        });
    }
}


function successMsg(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">' + msg);
}

function warnMsg(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}

/**
 * 日期格式化
 * @param timestamp
 * @returns {*}
 */
function formatDate(timestamp) {
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

//等待背景层
function showLoad() {
    return layer.msg('拼命执行中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, offset: 'auto', time: 100000});
}

//移除等待背景层
function closeLoad(index) {
    layer.close(index);

}

//处理ajax的错误
function delAjaxErrorMsg(xhr) {
    layer.closeAll();
    var alertMsg = "请求失败，请检查数据连接"
    if (xhr.responseText && jQuery.parseJSON(xhr.responseText).msg) {
        alertMsg = jQuery.parseJSON(xhr.responseText).msg;
    } else if (xhr.responseText && jQuery.parseJSON(xhr.responseText).head.message) {
        alertMsg = jQuery.parseJSON(xhr.responseText).head.message;
    }
    layer.alert(alertMsg);
}

//数据交互 _param参数对象：设置noModal = true关闭蒙层，async = true设置同步请求。
function postDataToServer(_url, _param, _type, _async, contentType) {
    jQuery.support.cors = true;
    var i;
    $.ajax({
        url: _url,
        type: _type,
        async: _async,
        contentType: contentType,
        //xhrFields: {withCredentials: true},
        data: _param,
        beforeSend: function () {
            i=showLoad();
        },
        success: function (res) {
            layer.msg(res.head.message, {time: 2000});
            closeLoad(i);
        },
        error: function (err) {
            console.log(_url, _param, err);
            layer.msg("数据请求失败", {
                time: 1500
            });
            closeLoad(i);
        }
    });
}

function ajaxQuery(_url, _param, _type, _async, contentType) {
    var res;
    $.ajax({
        url: _url,
        type: _type,
        async: _async,
        contentType: contentType,
        data: _param,
        success: function (data) {
            res = data;
        },
        error: function (err) {
            console.log(_url, _param, err);
        }
    });
    return res;
}

function zeroFill(i) {
    if (i >= 0 && i <= 9) {
        return "0" + i;
    } else {
        return i;
    }
}

// 设置最小可选的日期
function minDate(){
    var now = new Date();
    return now.getFullYear() + "-" + zeroFill(now.getMonth()+1) + "-" + zeroFill(now.getDate())
        + " " + zeroFill(now.getHours()) + ":" + zeroFill(now.getMinutes()) + ":" + zeroFill(now.getSeconds());
}

// 判断数据是否为空
function notNull(data){
    if (isNull(data)) {
        return false;
    }
    return true;
}

function isNull(data){
    if (data==null || data=="" || typeof(data)=="undefined") {
        return true;
    }
    return false;
}

function refreshView() {
    $("#query").click();
}
//----------------------------------------------------------JS原生扩展---------------------------------------------------
if (!Array.prototype.forEach) {
    Array.prototype.forEach = function forEach(callback, thisArg) {
        var T, k;
        if (this == null) {
            throw new TypeError("this is null or not defined");
        }
        var O = Object(this);
        var len = O.length >>> 0;
        if (typeof callback !== "function") {
            throw new TypeError(callback + " is not a function");
        }
        if (arguments.length > 1) {
            T = thisArg;
        }
        k = 0;

        while (k < len) {

            var kValue;
            if (k in O) {

                kValue = O[k];
                callback.call(T, kValue, k, O);
            }
            k++;
        }
    };
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
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
//----------------------------------------------------------浏览器相关方法------------------------------------------------