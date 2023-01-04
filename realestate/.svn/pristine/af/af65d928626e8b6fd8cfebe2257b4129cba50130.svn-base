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

function iframeIp() {
    return "http://192.172.0.101:8087/portal-ui/src/main/webapp/view";
}

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

function closeWin () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
function closeParentWindow () {
    var index = parent.parent.layer.getFrameIndex(window.name);
    parent.parent.layer.close(index);
}
//表格刷新
function renderTable(url, tableId, currentId) {
    layui.use(['jquery', 'table'], function () {
        var table = layui.table;
        table.reload(currentId);
    })
}

//table刷新
function renderCurrentTable() {
    $(".bdc-list-tab .layui-tab-title .layui-this").click();
}

//把添加遮罩及移除遮罩方法添加在自己的通用js中，或者引入当前js，注释上面两行，调用即可使用
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

function removeModel() {
    $("#waitModalLayer").remove();
}

//楼盘表跨域方法
function excute(url) {
    document.getElementById('test2').src = url;
}
//新增tab
function addTab(url,name) {
    var getIframeIndex = $('.layui-body .layadmin-tabsbody-item.layui-show', window.parent.document).index() + 1;

    $('.bdc-outer-tab>.layui-tab-title').find('li').removeClass('layui-this');
    $('.layui-body').find('.layadmin-tabsbody-item').removeClass('layui-show');
    $('.bdc-outer-tab>.layui-tab-title li:nth-child(' + getIframeIndex + ')').after('<li lay-id="dyaZxzm.html" class="layui-this"><i class="layui-icon layui-icon-left bdc-child-img"></i><span>' + name + '</span><i class="layui-icon layui-unselect layui-tab-close bdc-tab-close">ဆ</i></li>');
    $('.layui-body .layadmin-tabsbody-item:nth-child('+ getIframeIndex +')').after('<div class="layadmin-tabsbody-item layui-show">'+
        '<iframe src="'+ url +'" frameborder="0" class="layadmin-iframe"></iframe>'+
        '</div>');
}
//删除tab
function deleteTab() {
    var _this = $('.bdc-outer-tab .layui-tab-title .layui-this');
    var refreshIndex = _this.index()-1;
    var prevIndex = _this.index();
    var nowIndex = _this.index() + 1;

    _this.parent().find('li').removeClass('layui-this');
    _this.parent().find('li:nth-child(' + prevIndex + ')').addClass('layui-this');
    _this.remove();
    $('.layui-body .layadmin-tabsbody-item:nth-child(' + nowIndex + ')').remove();
    $('.layui-body .layadmin-tabsbody-item').removeClass('layui-show');
    $('.layui-body .layadmin-tabsbody-item:nth-child(' + prevIndex + ')').addClass('layui-show');

    var thisLi = $('.bdc-outer-tab .layui-tab-title .layui-this');
    if(thisLi.attr('data-refresh') == 1){
        var $frame = $('.layui-body .layadmin-tabsbody-item.layui-show iframe:first-child');
        $frame.attr('src', $frame.attr('src'));

        thisLi.removeAttr('data-refresh');
    }
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

function reverseString(str) {
    if (!isNotBlank(str)) {
        return str;
    }
    str = str.replace(/&lt;/g, '>');
    str = str.replace(/&gt;/g, '<');
    var RexStr = /\（|\）|\(|\)|\【|\】|\【|\】|\[|\]|\[|\]/g;
            str = str.replace(RexStr, function(MatchStr) {
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
                    case "{":
                        return "}";
                        break;
                    case "}":
                        return "{";
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
                if (getTdCell.find('span').length == 0) {
                    var tdHtml = reverseString(getTdCell.html());
                    getTdCell.html('<span class="bdc-table-date">' + tdHtml + '</span>');
                }
            }
        });
    }
}

/**
 * 查看流程图
 */
function viewLct(gzlslid) {
    layer.open({
        title: '流程详细页面',
        type: 2,
        skin: 'layui-layer-lan',
        area: ['960px', '600px'],
        content: ['/bpm-ui/process/processDetail/' + gzlslid]
    });
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
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 获取当前用户
 */
function queryCurrentUser() {
    var user='';
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/user/info",
        async:false,
        data: {},
        success: function (data) {
           user=data;
        }, error: function (e) {
            response.fail(e,'');
        }
    });
    return user;
}
