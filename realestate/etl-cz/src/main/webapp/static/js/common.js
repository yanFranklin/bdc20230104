var dypzMap;

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

function removeModal() {
    $("#waitModalLayer").remove();
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
