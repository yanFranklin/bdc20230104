var dypzMap;

var storageUrl;
//是否修改流程期限
var sfxglcqx;
var dyzl;
var printmode;
//页面逻辑版本
var ymVersion;
var noYzGhytList;

//全局定义layer,laydate
var laydate,layer;
var zdList = {};
layui.use(['laydate','layer'], function () {
    laydate = layui.laydate;
    layer = layui.layer;
    /**
     * 获取字典
     * @param callback 回调函数
     */
    $(function () {
        window.getCommonZd = function (callback) {
            var zdList = getZdList();
            if (zdList) {
                callback(zdList);
            }
        };
    })
});

/**
 * 贷款方式（作为常量，各地区自行维护）
 * @type {string[]}
 */
var dkfs = ['公积金贷款', '组合贷款', '商业贷款', '其它'];

var zdbdcdyfwlxList = [{"DM": "xmxx", "MC": "项目内多幢"}, {"DM": "ljz", "MC": "逻辑幢"}, {
    "DM": "hs",
    "MC": "实测户室"
}, {"DM": "ychs", "MC": "预测户室"}];

var czlxArr = {1: "点击忽略", 2: "点击查看",3: "点击确定", 4: "点击取消",5: "点击是", 6: "点击否",7:"点击忽略全部"};


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
    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
        $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
    }else {
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

//添加遮罩
function addModel(message) {
    if(!isNotBlank(message)){
        message='加载中';
    }
    var modalHtml = '<div id="waitModalLayer" style="z-index: 9999999999">' +
        '<p class="bdc-wait-content">' +
        '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>' +
        '<span>'+message+'</span>' +
        '</p>' +
        '</div>';
    $('body').append(modalHtml);
}

//移除遮罩
function removeModal() {
    $("#waitModalLayer").remove();
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
 * 渲染日期
 */
function renderDate() {
    var laydate = layui.laydate;
    var format = 'yyyy-MM-dd';
    lay('.test-item').each(function () {
        if (isNotBlank(this.value)) {
            this.value = Format(this.value, format);
        }
        var id = this.id;
        laydate.render({
            elem: this,
            format: format,
            trigger: 'click'
        });
    });

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
 *根据表单中心的配置设置权限
 * @param formStateId
 * @param resourceName
 */
function setElementAttrByFormState(formStateId, resourceName,formClass) {
    $.ajax({
        url: "/realestate-etl/form/" + formStateId + "/" + resourceName,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data && !isEmptyObject(data)) {
                for (var i in data) {
                    var id = data[i].formHtmlId;
                    var attr = data[i].formElementType;
                    var $element =$("." + formClass).find("[name="+id+"]");
                    if ($element.length > 0) {
                        for (var k = $element.length - 1; k >= 0; k--) {
                            var element=$element[k];
                            //1.隐藏2.只读3.必填
                            if (attr === "1") {
                                if (element.type === "button" || element.type === "submit" || element.type === "reset") {
                                    element.setAttribute("style", "display:none;")
                                } else if (element.type === "textarea") {
                                    element.parentElement.parentElement.setAttribute('hidden', 'hidden');
                                } else if (element.tagName === "A" ||element.tagName ==="DIV") {
                                    element.setAttribute("style", "display:none;");
                                } else {
                                    element.parentElement.parentElement.setAttribute('style', 'display:none;');
                                }
                            } else if (attr === "2") {
                                element.setAttribute("disabled", "off");
                                if (element.type === "button" || element.type === "submit" || element.type === "reset") {
                                    element.classList.add("layui-btn-disabled");
                                }
                            } else if (attr === "3") {
                                element.setAttribute('lay-verify', 'required');
                            }
                        }
                    }
                }
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

/**
 * 限制表单所有元素不可用
 */
function setAllElementDisabled() {
    // 设置输入框不可编辑
    $('input').attr('disabled', 'off');
    $('select').attr('disabled', 'off');
    $('textarea').attr('disabled', 'off');
    $('checkbox').attr('disabled', 'off');
    $('radio').attr('disabled', 'off');
    layui.use(['form'], function () {
        var form = layui.form;
        form.render('select');
    })
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
function iframeIp() {
    return "http://192.172.0.101:8087/portal-ui/src/main/webapp/view";
}

function getIpHz() {
    var $paramArr = [];
    var $params = window.location.search.replace('?','');
    var $paramSplit = $params.split('&');
    for(var i in $paramSplit) {
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

/**
 * 警告提示
 * @param msg
 */
function warnMsg(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}

function successMsg(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">' + msg);
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

//叫号特定遮罩
function jhAddModel(message) {
    if(isNullOrEmpty(message)){
        message='加载中';
    }
    var modalHtml = '<div id="waitModalLayer" class="bdc-jh-model">' +
        '<p class="bdc-wait-content">' +
        '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>' +
        '<span>'+message+'</span>' +
        '</p>' +
        '</div>';
    $('.bdc-jh-layer').append(modalHtml);
}

function removeJhModal() {
    $(".bdc-jh-model").remove();
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
    $('.bdc-outer-tab>.layui-tab-title li:nth-child(' + getIframeIndex + ')').after('<li lay-id="test.html" class="layui-this"><i class="layui-icon layui-icon-left bdc-child-img"></i><span>' + name + '</span><i class="layui-icon layui-unselect layui-tab-close bdc-tab-close">ဆ</i></li>');
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

/**
 * 查看流程图
 */
function viewLct(gzlslid) {
    var index = layer.open({
        title: '流程详细页面',
        type: 2,
        area: ['1150px', '600px'],
        content: ['/bpm-ui/process/processDetail/' + gzlslid]
    });
    layer.full(index);
}

//数据交互
function getReturnData(_path, _param, _type, _fn, async) {
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

/**
 * 格式化 bdcdyh
 * @param bdcdyh 不动产单元号
 * @return {string} 返回格式化的不动产单元号字符串
 */
function formatBdcdyh(bdcdyh) {
    var result="";
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

function strFormatDate(str) {
    return new Date(Date.parse(str.replace(/-/g, "/")));
}

function add0(time) {
    if (time < 10) {
        return '0' + time;
    }
    return time;
}

function completeDate(date1, date2) {
    var oDate1 = new Date(Date.parse(date1.replace(/-/g, "/")));
    var oDate2 = new Date(Date.parse(date2.replace(/-/g, "/")));
    return oDate1.getTime() > oDate2.getTime();
}

/**
 *根据模块管理中的元素配置设置权限
 * @param moduleCode 资源名
 */
function setElementAttrByModuleAuthority(moduleCode) {
    $.ajax({
        url: getContextPath()+"/rest/v1.0/workflow/process-instances/moduleAuthority/" + moduleCode,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data) {
                for (var i in data) {
                    id = i;
                    attr = data[i];
                    var element = document.getElementById(id);
                    if (element !== null) {
                        //1.隐藏2.只读3.必填
                        if (attr === "hidden") {
                            if (element.type === "button" || element.type === "submit" || element.type === "reset") {
                                element.setAttribute("style", "display:none;");
                            } else if (element.type === "textarea") {
                                element.parentElement.parentElement.setAttribute('hidden', 'hidden');
                            } else {
                                element.parentElement.parentElement.setAttribute('style', 'display:none;');
                            }
                        } else if (attr === "readonly") {
                            element.setAttribute("disabled", "off");
                            if (element.type === "button" || element.type === "submit" || element.type === "reset") {
                                // element.setAttribute('style',"background-color:#C7C4C1;color:black");
                                element.classList.add("layui-btn-disabled");
                            }
                        } else if (attr === "required") {
                            element.setAttribute('lay-verify', 'required');
                        }
                    }
                }
            }
            // 这里对input框进行样式修改，select框需要在各页面渲染处修改
            resetInputDisabledCss();
        }, error: function (xhr, status, error) {
            failMsg("页面权限请求设置失败，请联系管理员！");
        }
    });
}


//右下角弹出提示内容
function bottomShowTips(msg) {
    var bottomLayer  = layer.open({
        type: 1,
        title: false,
        skin: 'bdc-tips-shadow',
        closeBtn: 0, //不显示关闭按钮
        shade: 0,
        area: ['240px'],
        offset: 'rb', //右下角弹出
        time: 5000,
        anim: 2,
        content: '<div class="bdc-bottom-tips" id="bottomTips" style="background-color:#fff;border-left: 4px solid #1d87d1;padding:10px 15px 15px;">'+
            '<h3 style="font-size: 16px;line-height: 30px;position: relative;margin-bottom: 6px;">您有一条新的信息:<i class="layui-icon layui-icon-close" style="font-size:20px;position: absolute;top: 50%;right: 0;transform: translateY(-50%);"></i></h3>'+
            '<p style="font-size: 14px;line-height: 24px;">'+msg+'</p>'+
            '</div>',
        success: function () {
            $('.bdc-tips-shadow .bdc-bottom-tips .layui-icon-close').on('click',function () {
                layer.close(bottomLayer);
            })
        }
    });
}

//右下角弹出强提示内容
function strongTips(msg,_fn) {
    var strongTipsHtml = '';
    if(msg){
        msg.forEach(function (v) {
            strongTipsHtml += '<p class="bdc-zf-tips-p"><img src="../static/lib/bdcui/images/info-small.png" alt="">'+ v +'</p>';
        });
    }
    var submitIndex = layer.open({
        type: 1,
        title: '提示',
        skin: 'bdc-strong-tips',
        area: ['320px'],
        content: strongTipsHtml,
        btn: ['确定'],
        btnAlign: 'c',
        closeBtn: 0,
        yes: function(){
            if(_fn){
                _fn.call(this);
            }
            layer.close(submitIndex);
        }
    });
}

function failMsg(msg){
    layer.msg('<img src="../static/lib/bdcui/images/error-small.png" alt="">' + msg);
}

/**
 * 设置input不可编辑的样式
 */
function resetInputDisabledCss() {
    $('i').remove('.fa');
    // 设置不可用的属性
    var img = '<img src="../static/lib/bdcui/images/lock.png" alt="">';
    $("input[disabled='off']").after(img);
    // 为页面设置焦点
    $('.content-title').click();
}

/**
 * 对传入的字符串进行去空格处理
 * @date 2019.03.14 18:43
 * @author
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

$(document).ajaxError(
    //所有ajax请求异常的统一处理函数，处理无登陆权限
    function (event, xhr, options, exc) {
        if (!isNullOrEmpty(xhr.responseText) && xhr.responseText.indexOf("登录") > -1
            && xhr.responseText.indexOf("用户名") > -1 && xhr.responseText.indexOf("密码") > -1) {
            window.top.location.reload();
        }
    }
);

//设置IP
function getIP() {
    return document.location.protocol + "//" + window.location.host;
}


function setDypzSessionEtl(dylxArr, sessionKey) {
    // 当前页面打印配置信息
    $.ajax({
        type: "POST",
        url: "/realestate-etl/rest/v1.0/old/system/dypz",
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify(dylxArr),
        async: false,
        success: function (data) {
            dypzMap = data;
        }, error: function (e) {
            delAjaxErrorMsg(e);
        }
    });
}

/**
 *
 * @param dylx 打印类型
 * @param dataUrl 获取数据源地址
 * @param hiddeMode fr3打印是否隐藏控件打印预览
 */
function printChoice(dylx, appName, dataUrl, modelUrl, hiddeMode, sessionKey) {
    console.log("打印配置参数", dypzMap);
    console.log("xml数据源", dataUrl);
    if (dypzMap && !isEmptyObject(dypzMap) && dypzMap[dylx]) {
        var bdcDysjPzDO = dypzMap[dylx];
        var fr3Path = bdcDysjPzDO.fr3path;
        var pdfPath = bdcDysjPzDO.pdfpath;
        // 如果fr3打印路径配置了，则优先选择fr3打印
        if (fr3Path) {
            fr3Path = resolvePath(fr3Path);
            print(fr3Path, dataUrl, hiddeMode);
            return;
        } else if (pdfPath) {
            pdfPath = resolvePath(pdfPath);
            //设置pdf打印参数
            var bdcPdfDyQO = {};
            bdcPdfDyQO.appName = appName;
            bdcPdfDyQO.dataUrl = dataUrl;
            bdcPdfDyQO.pdfpath = pdfPath;
            bdcPdfDyQO.fileName = sessionKey;
            ajaxPostPdfPrint(bdcPdfDyQO);
            return;
        }
    }
    // 兼容之前的打印配置
    print(modelUrl, dataUrl, hiddeMode);
}

/**
 * post请求打印pdf
 * @param bdcPdfDyQO pdf打印参数
 */
function ajaxPostPdfPrint(bdcPdfDyQO) {
    $.ajax({
        type: "POST",
        url: "/realestate-etl/rest/v1.0/old/system/pdf/param/redis",
        contentType: 'application/json',
        data: JSON.stringify(bdcPdfDyQO),
        success: function (data) {
            if (data) {
                var pdfUrl = "/realestate-etl/rest/v1.0/old/system/pdf/" + data;
                window.open(getContextPath() + '/static/lib/pdf/web/viewer.html?file=' + encodeURIComponent(pdfUrl), "PDF");
            }
        }, error: function (e) {
            warnMsg("pdf生成异常！")
        }
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
}

function resolvePath(path) {
    try {
        return appendIpPort(path);
    } catch (e) {
        return path;
    }
}

function appendIpPort(path) {
    if (isNullOrEmpty(path)) {
        return "";
    }

    if (path.toLowerCase().indexOf("/storage") == 0) {
        // 以/storage开头的加上缺省的IP、端口
        return document.location.protocol + "//" + window.location.host + path;
    } else {
        return path;
    }
}

function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

//关闭当前页面
function closeWin() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

//关闭父页面
function closeParentWindow() {
    var index = parent.parent.layer.getFrameIndex(window.name);
    parent.parent.layer.close(index);
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
    if (isNotBlank(str)) {
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

//----------------------------------------------------------不可编辑添加图标-----------------------------------------------------
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

//----------------------------------------------------------系统参数-----------------------------------------------------
//设置IP
function getIP() {
    return document.location.protocol + "//" + window.location.host;
}

function zhzs(value) {
    var regu = /^[1-9]+[0-9]*]*$/;
    if (isNotBlank(value)) {
        if (!regu.test(value)) {
            ityzl_SHOW_INFO_LAYER("请输入正整数");

        } else {
            return value;
        }
    }
}

//给下拉框增加title属性
function renderSelect() {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var $ = layui.$;
        $('.layui-form').on('mouseenter', '.layui-form-select .layui-select-title', function (d) {
            var that = this,
                elem = $(that),
                title = elem.context.firstChild.value;
            if (title !== null && title !== "") {
                elem.attr("title", title);
            }
        })
    })
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
 * formIds 支持多个，用英文逗号隔开
 * 监听修改字段
 */
function renderChange(formSelects, form, formIds) {
    $.each(formIds.split(","), function (index, formID) {
        if (isNotBlank(formID)) {
            var $input = $("#" + formID).find(".layui-input");
            //监听input修改
            $input.on('change', function () {
                $(this).addClass('bdc-change-input');
                var text =$(this).parents(".layui-inline").find("label").text();
                renderChangeTips(text);
            });
            var $textarea = $("#" + formID).find(".change-textarea-width");
            //监听textarea修改
            $textarea.on('change', function () {
                $(this).addClass('bdc-change-input');
                var text = $(this).parents(".change-textarea-margin").find("label").text();
                renderChangeTips(text);
            });
        }
    });

    //监听select下拉框内容修改
    form.on('select', function (data) {
        console.log(data.elem); //得到select原始DOM对象
        console.log(data.value); //得到被选中的值
        console.log(data.othis); //得到美化后的DOM对象
        $.each(formIds.split(","), function (index, formID) {
            var $select = $("#" + formID).find(data.othis).find("input");
            if ($select.length > 0) {
                $(data.elem).parents(".layui-input-inline").addClass('bdc-change-input');
                var text = $(data.elem).parents(".layui-inline").find("label").text();
                renderChangeTips(text);
            }
        });
    });
}

/**
 * text 修改字段名称
 * 修改字段时同步显示下方提示
 */
function renderChangeTips(text) {
    if (isNotBlank(text)) {
        text = text.replace("*", "");
        text = " "+text+" ";
        //显示下方提示
        $(".bdc-update-tips-box").removeClass("bdc-hide");
        //查询提示框内容，判断是否包含修改字段，不包含则增加
        //添加受理页面中打开的子页面中修改了内容，在父页面中增加提示展示
        var tiptext = "";
        if($("#updateTips p").length != 0){
            tiptext = $("#updateTips p").text();
        }else{ // 当为获取到更新tips的内容时，采用调用父页面dom来获取。
            tiptext = parent.$("#updateTips p").text();
        }
        var textArr = text.split(" ");
        textArr.forEach(function(v){
            if(tiptext.indexOf(v) < 0){
                var addText = " " + v;
                if($("#updateTips p").length != 0){
                    $("#updateTips p").append(addText);
                }else{
                    parent.$("#updateTips p").append(addText);
                }
            }
        });
        //关闭提示
        $('.bdc-update-tips-box .bdc-close').on('click', function () {
            $('.bdc-update-tips-box').addClass('bdc-hide');
        });
    }

}

function layerOpenFull(url, title) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true,
        zIndex: 2147483647,
        cancel: function () {
            refreshQlxx();
        }
    });
    layer.full(index);
}


function layerOpenFullReload(url, title) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true,
        zIndex: 2147483647,
        end: function () {
            if (lclx === "zhlc") {
                loadJbxx();
                loadSjcl();
                refreshQlxx();
            } else if (lclx === "pllc") {
                $.when(loadBdcdyh()).done(function () {
                    var a = setInterval(function () {
                        if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                            getStateById(readOnly, formStateId, "slympl");
                            clearInterval(a);
                        }
                    }, 50);
                });
            }
        }
    });
    layer.full(index);
}

/**
 * @param url url地址
 * @param title 标题
 * @param width
 * @param height
 * layer弹出层弹出全屏
 */
function layerCommonOpenFull(url, title,width, height) {
    var index = layer.open({
        type: 2,
        title: title,
        area: [width, height],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: url
    });
    layer.full(index);
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
 * 显示提示对话框
 * @param title 提示框标题
 * @param msg 提示信息
 * @param yesFun 选择确定
 * @param yesParm
 * @param noFun 选择取消
 * @param noParm
 * @param isAddmodel 是否增加遮罩
 * @return
 */
function showConfirmDialog(title, msg, yesFun, yesParm, noFun, noParm,isAddmodel) {
    var btnName = "取消";
    if("checkSfcjWlzs" == noFun){
        btnName = "忽略";
    }
    var index = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: title,
        area: ['320px'],
        content: msg,
        btn: ['确定', btnName],
        btnAlign: 'c',
        yes: function () {
            if (yesFun != null && yesFun != "") {
                if(isAddmodel) {
                    addModel();
                    setTimeout(function () {
                        eval(yesFun + "(" + yesParm + ")");
                    }, 10);
                }else{
                    eval(yesFun + "(" + yesParm + ")");

                }

            }
            layer.close(index);
        },
        btn2: function () {
            //取消
            if (noFun != null && noFun != "") {
                eval(noFun + "(" + noParm + ")");
            }
            layer.close(index);
        }
    });
}

function ERROR_CONFIRM(title, msg) {
    layer.confirm(msg, {icon: 5, btn: '确认', title: title}, function (index) {
        layer.close(index);
    });
}


//----------------------------------------------------------通用方法-----------------------------------------------------
//等待背景层
function beginAction() {
    var modalHtml = '<div id="waitModalLayer" style="position: fixed;top: 0;bottom: 0;left: 0;right: 0;z-index: 9999999999;background-color: #000;opacity: 0.3;filter:Alpha(opacity=30);"><span style="top: 50%;left: 50%;position: absolute;color: #fff;font-size: 61px;"><i style="font-size:100px;" class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i></span></div>';
    $('body').append(modalHtml);
}

/**
 * 不动产单元号分层显示
 */
function queryBdcdyh(bdcdyh) {
    if (bdcdyh.length === 28) {
        return bdcdyh.substring(0, 6) + " " + bdcdyh.substring(6, 12) + " " + bdcdyh.substring(12, 19) + " " + bdcdyh.substring(19, 28);
    } else {
        return bdcdyh;
    }
}

/**
 * 渲染日期
 */
function renderDate(form, formIds, format) {
    var laydate = layui.laydate;
    if (isEmptyObject(format)) {
        format = 'yyyy-MM-dd';
    }
    lay('.test-item').each(function () {
        if (isNotBlank(this.value)) {
            this.value = Format(this.value, format);
        }
        var id = this.id;
        laydate.render({
            elem: this,
            format: format,
            trigger: 'click'
            , done: function (value, date, endDate) {
                // var id = this.elem[0].id;
                var currentTime = new Date(value).getTime();
                if (isNotBlank(id) &&id.indexOf("qssj") > -1) {
                    var jssjid = $(this)[0].elem[0].id.replace("qssj", "jssj");
                    var endTime = new Date($('#' + jssjid).val()).getTime();
                    if (endTime < currentTime) {
                        ityzl_SHOW_WARN_LAYER('结束时间不能小于开始时间');
                        $('#' + jssjid).val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                    }
                    //查封期限默认三年
                    if(id ==="cf-cfqssj"){
                        //不为续封的默认三年 lst
                        if($("#cf-cflx").val()!=5){
                            var jssj =DateAdd("d",-1,DateAdd("y",3,new Date(value)));
                            if(jssj != null){
                                $("#cf-cfjssj").val(Format(formatChinaTime(jssj), "yyyy-MM-dd"));
                            }
                        }
                    }
                }
                else if (isNotBlank(id) &&id.indexOf("jssj") > -1) {
                    var qssjid = $(this)[0].elem[0].id.replace("jssj", "qssj");
                    var startTime = new Date($('#' + qssjid).val()).getTime();
                    if (currentTime < startTime) {
                        ityzl_SHOW_WARN_LAYER('结束时间不能小于开始时间');
                        $('#' + qssjid).val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                    }

                }
                //抵押权选择完起始时间后自动选择结束时间
                if (id === "dyaq-zwlxqssj") {
                    if($('.layui-show').length > 0){
                        $(".layui-show #dyaq-zwlxjssj").focus();
                        $(".layui-show #dyaq-zwlxjssj").click();
                    }else {
                        $("#dyaq-zwlxjssj").focus();
                        $("#dyaq-zwlxjssj").click();
                    }
                }
                //监听修改
                var $date = this.elem;
                if (formIds != undefined && isNotBlank(formIds)) {
                    $.each(formIds.split(","), function (index, formID) {
                        if (isNotBlank(formID)) {
                            var $date1 = $("#" + formID).find($date);
                            if ($date1.length > 0) {
                                $date.addClass('bdc-change-input');
                                var text = $($date).parents(".layui-inline").find("label").text();
                                renderChangeTips(text);
                            }
                        }
                    });
                }
            }
        });
        //监听开始时间,结束时间手动修改
        if (isNotBlank(id) &&id.indexOf("qssj") > -1) {
            var $qssj =$(this);
            //监听开始时间
            $qssj.on('change', function () {
                var jssjid = id.replace("qssj", "jssj");
                var currentTime = new Date($qssj.val()).getTime();
                var endTime = new Date($('#' + jssjid).val()).getTime();
                if (endTime < currentTime) {
                    ityzl_SHOW_WARN_LAYER('结束时间不能小于开始时间');
                    $qssj.val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                }
            });
        }else if (isNotBlank(id) &&id.indexOf("jssj") > -1) {
            var $jssj =$(this);
            //监听开始时间
            $jssj.on('change', function () {
                var qssjid = id.replace("jssj", "qssj");
                var currentTime = new Date($jssj.val()).getTime();
                var startTime = new Date($('#' + qssjid).val()).getTime();
                if (currentTime < startTime) {
                    ityzl_SHOW_WARN_LAYER('结束时间不能小于开始时间');
                    $jssj.val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                }
            });
        }
    });

}

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
 * y(年),m(月）,d(日）
 * 日期增加
 */
function DateAdd(interval, number, date) {
    switch (interval) {
        case "y": {
            date.setFullYear(date.getFullYear() + number);
            return date;
            break;
        }

        case "m": {
            date.setMonth(date.getMonth() + number);
            return date;
            break;
        }

        case "d": {
            date.setDate(date.getDate() + number);
            return date;
            break;
        }
        default: {
            return null;
            break;
        }
    }
}

//格式化中国标准时间
function formatChinaTime(datetime) {
    return datetime.getFullYear() + '-' + (datetime.getMonth() + 1) + '-' + datetime.getDate() + ' ' + datetime.getHours() + ':' + datetime.getMinutes() + ':' + datetime.getSeconds();
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

//移除等待背景层
function endAction() {
    $("#waitModalLayer").remove();
}

//获取Token
function getToken(_path, _param, _successfn, _failFn) {
    var _tokenParam = {
        "head": {
            "origin": "2"
        },
        "data": {
            "userName": "web",
            "userPwd": "123456"
        }
    }
    jQuery.support.cors = true;
    $.ajax({
        url: getIP() + "/tokenModel/getToken",
        contentType: "application/json;charset=utf-8",
        type: "post",
        data: JSON.stringify(_tokenParam),
        success: function (data) {
            if (data && data.data && data.data.token) {
                $.cookie("myToken", data['data']['token'], {path: "/"});
                postDataToServer(_path, _param, _successfn, _failFn);
            }
        }
    });
}

//数据交互 _param参数对象：设置noModal = true关闭蒙层，async = true设置同步请求。
function postDataToServer(_path, _param, _successfn, _failfn) {
    var _token = $.cookie("myToken");
    if (!_token) {
        getToken(_path, _param, _successfn, _failfn);
        return;
    }
    if (!_path) {
        return;
    }
    var _url = getIP() + _path;
    !_param.noModal && beginAction();
    jQuery.support.cors = true;
    $.ajax({
        url: _url,
        type: 'post',
        async: _param.async || false,
        contentType: 'application/json;charset=utf-8',
        xhrFields: {withCredentials: true},
        data: JSON.stringify({
            "head": {
                origin: "2",
                sign: "",
                token: _token
            },
            "data": _param
        }),
        success: function (res) {
            endAction();
            if (res.head.code == "0000") {
                _successfn.call(this, res.data, res)
            } else if (_failfn) {
                _failfn.call(this, res);
            } else {
                layer.msg(res.head.msg, {time: 3000});
            }
        },
        error: function (err) {
            endAction();
            console.log(_path, _param, err);
            layer.msg("数据请求失败", {
                time: 1500
            });
        }
    });
}

//生成Id
function createUUID() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}

/**
 * @param object 对象
 * @param key 属性名称
 * @return boolean 存在属性 true  不存在 false
 * @description 判断当前对象是否包含某个属性
 */
function hasProperty(object, key){
    if(!$.isPlainObject(object)){
        return false;
    }
    if($.isEmptyObject(object)){
        return false
    }
    if(undefined == key || null == key || "" == key){
        return false;
    }
    if(object.hasOwnProperty(key)){
        return true;
    }
    return false;
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
//----------------------------------------------------------控件方法-----------------------------------------------------


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
 * @date 2019.10.31
 * @author liaoxiang
 * attrs 需要转换大写的key
 * @description 将小写字母转为大写
 */
function toUpperClass(obj,attrs){
    $.each(attrs, function (index, item) {
        if(isNotBlank(obj[item])){
            obj[item] =deleteWhitespace(obj[item], "edge").toUpperCase();
        }
    })
}

/**
 * @date 2020.09.17
 * @author yousiyi
 * class 需要加密的类
 * @description 受理页面电话加密显示(****)
 */
function toEncryptClass(className) {
    var isPhone = /^\d{3}-\d{7,8}|\d{4}-\d{7,8}$/;//电话号码
    var isMob = /^0?1[3|4|5|6|7|8|9][0-9]\d{8}$/;//手机号码

    $.each($('.'+className), function (i) {
        // 状态为不可编辑时进行加密
        if($(this).prop("disabled")){
            var phone = $(this).val(),resultPhone;
            if(isNotBlank(phone)){
                if(isMob.test(phone)){
                    // 手机号替换中间四位
                    resultPhone = phone.replace(/^(\d{3})\d{4}(\d+)/, '$1****$2');
                    $(this).val(resultPhone);
                }else if(isPhone.test(phone)){
                    // 固定电话替换区号后四位
                    var dhArr = phone.split('-');
                    var dh = dhArr[1].replace(/\d{1,4}/, '****');;
                    resultPhone = phone.replace(dhArr[1],dh);
                    $(this).val(resultPhone);
                }
                $(this).attr('lay-verify','');
            }
        }
    })
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

function isArray(obj) {
    return Object.prototype.toString.call(obj) == "[object Array]";
}
//----------------------------------------------------------浏览器相关方法------------------------------------------------

//---------------------------------------------新增2019/7/5 苑    开始------------------------------------------------------
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

//---------------------------------------------新增2019/7/5 苑    结束------------------------------------------------------

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
 * 处理小数计算问题，四舍五入保留两位小数
 * 需要引用math.js
 * @param equation  计算公式
 */
function calculateFloat(equation) {
    var result = math.format(math.evaluate(equation), 14);
    return formatFloat(parseFloat(result));
}

//处理小数问题
function formatFloat(f) {
    if((f + '').indexOf('.') != -1){
        var d, carryD, i,
            ds = f.toString().split('.'),
            len = ds[1].length,
            b0 = '', k = 0;

        if (len > 2) {
            while(k < 2 && ds[1].substring(0, ++k) == '0') {
                b0 += '0';
            }
            d = Number(ds[1].substring(0, 2));
            // 判断保留位数后一位是否需要进1
            carryD = Math.round(Number('0.' + ds[1].substring(2, len)));
            len = (d + carryD).toString().length;
            if (len > 2) {
                return Number(ds[0]) + 1;
            } else if (len == 2) {
                return Number(ds[0] + '.' + (d + carryD));
            }
            return Number(ds[0] + '.' + b0 + (d + carryD));
        }
    }
    return f;
}

/**
 * @param obj
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description js对象属性大写转小写
 * @date : 2019/12/18 11:09
 */
function lowerPropertyNames(obj) {

    if (obj == null) {
        return;
    }
    if (typeof obj != 'object') {
        return;
    }
    if (isArray(obj) && obj.length > 0) {
        // iterate over array obj
        for (var index in obj) {
            lowerPropertyNames(obj[index]);
        }
    } else {
        // iterate over object obj
        var props = Object.keys(obj);
        var propNums = props.length;
        if (propNums == 0) {
            return;
        }
        for (var index = 0; index < propNums; index++) {
            var prop = props[index];
            var prop_lowercase = prop.toLowerCase();
            if (prop_lowercase !== prop) {
                obj[prop_lowercase] = obj[prop];
                delete obj[prop];
            }
            if (typeof obj[prop_lowercase] == 'object') {
                lowerPropertyNames(obj[prop_lowercase]);
            }
        }
    }
    function isArray(o) {
        return typeof o === "object" &&
            Object.prototype.toString.call(o) === "[object Array]";
    }
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
 * 判断当前url是否有参数
 * @param url
 * @returns {*}
 */
function urlHasParam(url){
    if(url && url.indexOf("?") > -1){
        return true;
    }
    return false;
}

/**
 * 拼接ur参数
 * @param uri
 * @param par
 * @param par_value
 * @returns {*}
 */
function changeURLPar(uri, par, par_value) {
    try {
        var pattern = par + '=([^&]*)';
        var replaceText = par + '=' + par_value;
        if (uri.match(pattern)) {//如果连接中带这个参数
            var tmp = '/\\' + par + '=[^&]*/';
            tmp = uri.replace(eval(tmp), replaceText);
            return (tmp);
        }
        else {
            if (uri.match('[\?]')) {//如果链接中不带这个参数但是有其他参数
                return uri + '&' + replaceText;
            }
            else {//如果链接中没有带任何参数
                return uri + '?' + replaceText;
            }
        }
        return uri + '\n' + par + '\n' + par_value;
    }catch (e) {
        return uri
    }
}

/**
 * 保存日志信息
 * @param data 需要保存的日志数据
 */
function saveLogInfo(data) {
    $.ajax({
        url: "/realestate-accept-ui/rest/v1.0/log/info",
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

function getSfzfByXmid(xmid){
    var sfzf = null;
    $.ajax({
        url: getContextPath()+ "/slym/ql/fwkg/sfzf/"+xmid,
        type: "GET",
        async: false,
        success: function (data) {
            if(data !== ""){
                sfzf = data;
            }
        },
        error: function (e) {
        }
    });
    return sfzf;
}

function saveSfzfByXmid(sfzf,xmid){
    $.ajax({
        type: 'PUT',
        url: '/realestate-accept-ui/slym/ql/saveSfzfByXmid',
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify({sfzf: sfzf,id: xmid}),
        success: function (data) {

        }
    });
}

//字典项名称转代码
function changeMcToDm(mc, zdList) {
    var dm = "";
    if (isNotBlank(zdList)) {
        for (var i = 0; i < zdList.length; i++) {
            var zd = zdList[i];
            if (mc == zd.MC) {
                dm = zd.DM;
                break;
            }
        }
    }
    return dm;

}

// 获取字典信息
function getZdList() {
    getReturnData("/rest/v1.0/bdczd",{},"POST",function (data) {
        zdList=data;
    },function () {
    },false);
    return zdList;
}

function verifyform(form) {
    var result = {};
    //点击提交时不为空的全部标红
    var isSubmit = true;
    //验证提示信息
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "必填项不能为空";
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = verifyMsg;
            }
        }
        , addrequired: function (value, item) {
            //动态添加的必填属性，与layui自带必填区分开来
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "必填项不能为空";
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = verifyMsg;
            }
        }
        , identitynew: function (value, item) {
            var msg = checkSfzZjh(value);
            if (isNotBlank(msg)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = msg;
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = verifyMsg;
            }
        }
        , lxdh: function (value, item) {
            if (!validatePhone($.trim(value))) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "联系电话格式不正确";
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = verifyMsg;
            }
        }
    });
    return result;

}

/**
 * 验证联系电话,包括手机号码，固话,为空时直接验证通过
 */
function validatePhone(lxdh) {
    var isPhone = /^\d{3}-\d{7,8}|\d{4}-\d{7,8}$/;//电话号码
    var isMob = /^0?1[3|4|5|6|7|8|9][0-9]\d{8}$/;//手机号码
    if (!isNotBlank(lxdh) || isMob.test(lxdh) || isPhone.test(lxdh)) {
        //固定电话判断区号长度
        if(isPhone.test(lxdh)) {
            var dhArr = lxdh.split('-');
            if(dhArr != null) {
                var qh = dhArr[0];
                var regExp = /^\s*\S{2,5}\s*$/;
                if(!regExp.test(qh)) {
                    return false;
                }
            }
        }
        return true;
    }
    else {
        return false;
    }
}

/**
 * value 证件号内容
 * 返回提示信息：verifyMsg
 * 验证身份证证件号码:15位身份证，18位身份证
 */
function checkSfzZjh(value) {
    //证号先去除空格处理
    value =deleteWhitespace(value, "edge");
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

function istime(time) {
    var regExp = /^([0-9]{4})-([0-1][0-9])-([0-3][0-9])$/;
    if (isEmptyObject(time.match(regExp))) {
        return false;
    }
    return true;
}

//查询条件框添加删除图标
function renderSearchInput() {
    if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
        //监听input点击
        $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
            $(this).siblings('.layui-input').val('');
        });

        $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
            $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
        }).on('blur', function () {
            var $this = $(this);
            setTimeout(function () {
                $this.siblings('.layui-icon-close').addClass('bdc-hide');
            }, 150)
        });
    }
}