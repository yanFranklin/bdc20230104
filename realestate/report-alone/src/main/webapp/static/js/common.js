function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

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

/**
 * 判断字符串是否都为 ''
 * @param str
 * @returns {boolean}
 */
function isEmptyStr(str) {
    var flag = true;
    if (str != undefined && str != null && str != "") {
        flag = false;
    }
    return flag;
}

//表格刷新
function renderTable(url, tableId, currentId) {
    layui.use(['jquery', 'table'], function () {
        var table = layui.table;
        table.reload(currentId);
    })
}

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

/**
 * url中的获取参数
 *
 */
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

/**
 * url中的获取参数
 *
 */
function GetQueryStringWithChinese(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURIComponent(r[2]);
    return null;
}

function reverseString(str) {
    if (!isNullOrEmpty(str)) {
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
                if(getTdCell.find('span').length == 0){
                    var tdHtml = reverseString(getTdCell.html());
                    getTdCell.html('<span class="bdc-table-date">'+ tdHtml +'</span>');
                }
            }
        });
    }
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
    return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
}

function add0(time) {
    if (time < 10) {
        return '0' + time;
    }
    return time;
}

function Format(datetime, fmt) {
    if (isNotBlank(datetime)) {
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
}


function warnMsg(msg) {
    layer.msg('<img src="../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}

/**
 * 成功提示
 * @param msg
 */
function successMsg(msg) {
    layer.msg('<img src="../static/lib/bdcui/images/success-small.png" alt="">' + msg, {
            time: 1000
        }
    );
}

function failMsg(msg) {
    layer.msg('<img src="../static/lib/bdcui/images/error-small.png" alt="">' + msg);
}

//字典项代码转名称
function changeDmToMc(dm, zdList) {
    var mc = "";
    if (isNotBlank(zdList)) {
        for (var i = 0; i < zdList.length; i++) {
            var zd = zdList[i];
            if (dm == zd.DM) {
                mc = zd.MC;
                break;
            }
        }
    }
    return mc;

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
 *根据模块管理中的元素配置对对应class元素设置权限
 * @param moduleCode 资源名
 */
function setElementAttrInTableByModuleAuthority(moduleCode) {
    if (isNullOrEmpty(moduleCode)) {
        return;
    }
    $.ajax({
        url: "../authority/" + moduleCode,
        type: 'GET',
        dataType: 'json',
        cache: false,
        async: false,
        success: function (data) {
            if (data && !isEmptyObject(data)) {
                for (var i in data) {
                    id = i;
                    var cla = i.replace('_intable','');
                    attr = data[i];

                    var element = $("." + cla);
                    if (element !== null) {
                        //1.隐藏2.只读3.必填
                        if (attr === "hidden") {
                            if (element.is('span') || element.is('a') || element.is('button')){
                                element.hide();
                            }
                        } else if (attr === "readonly") {
                            element.attr("disabled", "off");
                            if (element.is('span') || element.is('a') || element.is('button')) {
                                // element.setAttribute('style',"background-color:#C7C4C1;color:black");
                                element.attr('style',"color:#999");
                                element.removeAttr("lay-event");
                                element.attr("href","javascript:;");
                            }
                        }
                    }
                }
            }
        }, error: function (xhr, status, error) {
            failMsg("页面权限请求设置失败，请联系管理员！");
        }
    });
}

/**
 * ajax统一封装方法
 * @param _url
 * @param _param
 * @param _type
 * @param _async
 * @param contentType
 * @returns {*}
 */
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
    layer.msg('<img src="../static/images/error-small.png" alt="">' + msg + ' <a class="bdc-show-more-tips" href="javascript:;">更多</a>', {
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




// 设置列表高度(带统计栏)
function setHeightTj(searchHeight) {
    if (isNullOrEmpty(searchHeight)) {
        searchHeight = 131;
    }
    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
        $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
    }else {
        $('.layui-table-body').height($('.bdc-table-box').height() - searchHeight - 40);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - searchHeight - 57);
    }
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

function getMulZdList(zdNames) {
    var zdList = {};
    $.ajax({
        url: getContextPath() + "/rest/v1.0/bdczd/mul",
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