/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description  公共方法 js
 */
function getContextPath(){
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
    // return "http://192.168.50.139:8088/realestate-portal-ui";
}

(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = encodeURI(window.location.search).substr(1).match(reg);
        if(null != r) return unescape(r[2]); return null;
    }
})(jQuery);


function getIpHz() {
    var $paramArr = [];
    var $params = window.location.search.replace('?','');
    var $paramSplit = $params.split('&');
    for(var i in $paramSplit)
    {
        var $subParam = $paramSplit[i].split('=');
        $paramArr[$subParam[0]] = $subParam[1];
    }
    return $paramArr;
}

function GetQueryString(url,name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = url.match(reg);//匹配正则
    if (r != null) return unescape(r[2]);
    return null;
}

//时间戳转换
Date.prototype.toLocaleString = function() {
    var month,date,hours,minutes,seconds;
    if(this.getMonth()+1<10){
        month = "0"+( this.getMonth()+1 );
    } else {
        month = ''+ (this.getMonth()+1);
    }
    if(this.getDate()<10){
        date = "0"+this.getDate();
    } else {
        date = this.getDate();
    }
    if(this.getHours()<10){
        hours = "0"+this.getHours();
    } else {
        hours = this.getHours();
    }
    if(this.getMinutes()<10){
        minutes = "0"+this.getMinutes();
    } else {
        minutes = this.getMinutes();
    }
    if(this.getSeconds()<10){
        seconds = "0"+this.getSeconds();
    } else {
        seconds = this.getSeconds()
    }
    return this.getFullYear() + "-" + month + "-" + date+" "+hours+":"+minutes+":"+seconds;
};

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
 * 警告提示
 * @param msg
 */
function warnMsg(msg) {
    layer.msg('<img src="../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}

function closeWin () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
function closeParentWindow () {
    var index = parent.parent.layer.getFrameIndex(window.name);
    parent.parent.layer.close(index);
}
//表格刷新
function renderTable(currentId) {
    layui.use(['jquery', 'table'], function () {
        var table = layui.table;
        table.reload(currentId);
    })
}


//把添加遮罩及移除遮罩方法添加在自己的通用js中，或者引入当前js，注释上面两行，调用即可使用
function addModel(bord,message) {
    if(isNullOrEmpty(message)){
        message='加载中';
    }
    var modalHtml = '<div id="waitModalLayer">' +
        '<p class="bdc-wait-content">' +
        '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>' +
        '<span>'+message+'</span>' +
        '</p>' +
        '</div>';
    if(isNullOrEmpty(bord)){
        $('body').append(modalHtml);
    }else{
        $('#'+bord).append(modalHtml);
    }

}

function removeModal() {
    $("#waitModalLayer").remove();
}



//根据url刷新指定iframe
function refreshIframe(url,isRefresh) {
    // console.log(url);
    var iframeList = $('.layui-body .layadmin-tabsbody-item iframe:first-child');
    var index = 9999;
    for(var i = 0; i < iframeList.length; i++){
//            console.log(iframeList[i].src);
        if(iframeList[i].src == url){
            index = i;
        }
    }
       // console.log(index);
    if(index != 9999){
        var newIndex = index + 2;
        if(isRefresh){
            var $tab = $('.bdc-outer-tab .layui-tab-title li:nth-child('+ newIndex +')');
            $tab.attr('data-refresh','1');
        }else {
            var $frame = $('.layui-body .layadmin-tabsbody-item:nth-child('+ newIndex +') iframe:first-child');
            // console.log($frame);
            $frame.attr('src', $frame.attr('src'));
        }
    }
}
//根据url替换指定iframe的url
function changeIframeUrl(oldUrl,newUrl) {
    // console.log(url);
    var iframeList = $('.layui-body .layadmin-tabsbody-item iframe:first-child');
    var changeIndex = 9999;
    for(var i = 0; i < iframeList.length; i++){
//            console.log(iframeList[i].src);
        if(iframeList[i].src == oldUrl){
            changeIndex = i;
        }
    }
    // console.log(index);
    if(changeIndex != 9999){
        var newIndex = changeIndex + 2;
        var $frame = $('.layui-body .layadmin-tabsbody-item:nth-child('+ newIndex +') iframe:first-child');
        // console.log($frame);
        $frame.attr('src', newUrl);
    }
}

/**
 * 不动产单元号分层显示
 */
function queryBdcdyh(bdcdyh) {
    bdcdyh.replace(/\s/g, "*");
    var newBdcdyh = bdcdyh.substring(0, 6) + " " + bdcdyh.substring(6, 12) + " " + bdcdyh.substring(12, 19) + " " + bdcdyh.substring(19, bdcdyh.length);
    return newBdcdyh;
}

//验证是否是虚拟单元号
function checkXn(bdcdyh){
    if(bdcdyh && bdcdyh.length==28 && bdcdyh.slice(6,12)=='000000' && bdcdyh.slice(13,14)!='G' && bdcdyh.slice(13,14)!='H'){
        return "是";
    }else{
        return "否";
    }
}

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
    for (var i = 0; i < getTd.length; i++) {
        reverseList.forEach(function (v) {
            if ($(getTd[i]).attr('data-field') == v) {
                var getTdCell = $(getTd[i]).find('.layui-table-cell');
                getTdCell.css('direction', 'rtl');
                if(getTdCell.find('span').length == 0){
                    var tdHtml = reverseString(getTdCell.html());
                    getTdCell.html('<span class="bdc-table-date">' + tdHtml + '</span>');
                }
            }
        });
    }
}



//数据交互
function getReturnData(_path, _param,_type, _fn, async) {
    layui.use(['jquery', 'response'], function () {
        var $ = layui.jquery,
            response = layui.response,
            _url = getContextPath() + _path;
        $.ajax({
            url: _url,
            type: _type,
            async: async ? false : true,
            contentType: 'application/json;charset=utf-8',
            data: _param,
            success: function (data) {
                _fn.call(this, data, data);
            },
            error: function (err) {
                response.fail(err, '');
            }
        });
    });
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

$(document).ajaxError(
    //所有ajax请求异常的统一处理函数，处理无登陆权限
    function(event,xhr,options,exc ){
        if(!isNullOrEmpty(xhr.responseText) && xhr.responseText.indexOf("登录") > -1
        && xhr.responseText.indexOf("用户名") > -1 && xhr.responseText.indexOf("密码") > -1){
            window.top.location.reload();
        }
    }
);

//前台的字典转换，代码转换为名称
function convertZdDmToMc(zdname, zdDm, zdListName) {
    if (zdDm) {
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
    return "";
}
