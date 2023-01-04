/**
 * config-ui应用公共JS
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
        if (null != r) return unescape(r[2]);
        return null;
    }
})(jQuery);

(function ($) {
    $.getUrlParamWithDecode = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = decodeURI(window.location.search).substr(1).match(reg);
        if (null != r) return unescape(r[2]);
        return null;
    }
})(jQuery);

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

/**
 * 渲染日期
 * @param format 日期格式
 */
function renderDate(format) {
    var laydate = layui.laydate;
    if (isNullOrEmpty(format)) {
        format = 'yyyy-MM-dd HH:mm:ss';
    }

    lay('.date').each(function () {
        laydate.render({
            elem: this
            , trigger: 'click'
            , type: 'datetime'
            , format: format
        });
    });
}

function loadLayer() {
    var index = layer.load(1, {
        shade: [0.5, '##fff'] //0.1透明度的白色背景
    });
    return index;
}

//----------------------------------------------------------系统参数-----------------------------------------------------
var layer = function () {
    layui.use(['layer'], function () {
        return layui.layer;
    });
}();

var inquiryUiUrl="/realestate-inquiry-ui"
//设置IP
function getIP() {
    return " http://" + window.location.host;
}
function ityzl_SHOW_TIP_LAYER(tip){
    layer.msg(tip,{time: 1000});
}
function SHOW_TSXX_LAYER(msg){
    layer.alert(msg);
}
// loading加载
function loading() {
    var loadMsg = layer.msg('正在加载中', {
        icon: 16, shade: 0.01, time: 5000
    });
    return loadMsg;
}
//全局定义layer


//----------------------------------------------------------通用方法-----------------------------------------------------
//等待背景层
function beginAction() {
    var modalHtml = '<div id="waitModalLayer" style="position: fixed;top: 0;bottom: 0;left: 0;right: 0;z-index: 9999999999;background-color: #000;opacity: 0.3;filter:Alpha(opacity=30);"><span style="top: 50%;left: 50%;position: absolute;color: #fff;font-size: 61px;"><i style="font-size:100px;" class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i></span></div>';
    $('body').append(modalHtml);
}

function delAjaxErrorMsg(xhr) {
    layer.closeAll();
    var alertMsg = "请求失败，请检查数据连接"
    if (xhr.responseText && jQuery.parseJSON(xhr.responseText).msg) {
        alertMsg = jQuery.parseJSON(xhr.responseText).msg;
    }
    layer.alert(alertMsg);
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
    for (var i = 0; i < 32; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    //s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}

//----------------------------------------------------------控件方法-----------------------------------------------------
//数据表格
function con_dataTableRender(_domId, _colsPaeam, _data, _pageId, _pageCount, _curPage, _curPageLimit, _fn) {
    var self = this;
    layui.use(['table', 'laypage', 'jquery'], function () {
        var table = layui.table;
        var laypage = layui.laypage;
        var $ = layui.$;
        table.render({
            elem: _domId,
            limit: _curPageLimit,
            cols: [_colsPaeam],
            data: _data,
            done: function () {
                var pageDomeId = _pageId ? _pageId.split("#")[1] : "";
                if (_pageCount > 0 && pageDomeId) {
                    $(_pageId).show();
                    laypage.render({
                        elem: pageDomeId,
                        count: _pageCount,
                        curr: _curPage,
                        limit: _curPageLimit,
                        layout: ['prev', 'page', 'next', 'skip', 'count', 'limit'],
                        jump: function (obj, first) {
                            if (!first) {
                                _fn.call(self, obj);
                            }
                        }
                    })
                } else {
                    $(_pageId).hide();
                }
            }
        });
    });
}
//图片上传
function con_bindFileUpload(_domeId) {
    var fixedUploadId = "con_bindFileUploadInputId";
    var dome = ''
}

//转换-将图片文件转为base64传回。
function con_conertImageToBase64(files, _fn) {
    if (window.FileReader) {
        var _fileCount = files.length;
        var _curReadIndex = 0;
        var hasIllegalFile = false;
        var filesBaseStr = [];
        var reader = new FileReader();
        var readIt = function (_file) {
            if (_file.type.split("/")[0] == "image") {
                reader.readAsDataURL(_file);
            } else {
                _curReadIndex++
                hasIllegalFile = true;
                if (_curReadIndex === _fileCount) {
                    endAction();
                    _fn.call(this, filesBaseStr, hasIllegalFile);
                } else {
                    readIt(files[_curReadIndex]);
                }
            }
        };
        reader.onload = function (e) {
            var base64Str = e.target.result.replace("data:image/png;base64,", "").replace("data:image/gif;base64,", "").replace("data:image/jpeg;base64,", "").replace("data:image/bmp;base64,", "");
            var attach = {
                "createUser": $.cookie("cur_userName"),
                "fileName": files[_curReadIndex].name,
                "base64Str": base64Str,
                "imgUrl": e.target.result
            };
            filesBaseStr.push(attach);
            _curReadIndex++;
            if (_curReadIndex === _fileCount) {
                endAction();
                _fn.call(this, filesBaseStr, hasIllegalFile);
            } else {
                readIt(files[_curReadIndex]);
            }
        }
        beginAction();
        readIt(files[_curReadIndex]);
    } else {
        layer.msg("本浏览器不支持该功能，请升级或使用其他浏览器");
    }

}

//全屏图片展示
var conShowPictrueObj = null;
function con_showPictureFullBrowser(_pictures, _index) {
    conShowPictrueObj = function () {
        var self = {};
        self.images = _pictures;
        self.curIndex = _index || 0;
        self.rate = 0;
        self.showThis = function (_index) {
            (_index > -1) && (self.curIndex = Number(_index));
            $("#pictureFullBrowserCurr").attr("src", self.images[self.curIndex].imgurl);
            var dome = $("#conPictureFullBrowser .preview-part");
            var childDome = dome.children().eq(self.curIndex);
            dome.children().removeClass("small-view-selected");
            childDome.addClass("small-view-selected");
            if ((dome.width()) < (childDome.offset().left + 100)) {
                dome.scrollLeft(dome.scrollLeft() + 100);
            } else if (childDome.offset().left < 100) {
                dome.scrollLeft(dome.scrollLeft() - 100);
            }
        };
        self.showNext = function () {
            if ((self.curIndex + 1) < self.images.length) {
                self.curIndex++;
                self.showThis();
            }
        };
        self.showPrev = function () {
            if (self.curIndex > 0) {
                self.curIndex--;
                self.showThis();
            }
        };
        return self;
    }();
    var html = $("#pictureFullBrowser").html();
    $("body").append(html);
    layui.use([], function () {
        var laytpl = layui.laytpl;
        laytpl($("#pictrueFullBrowserList").html()).render(conShowPictrueObj, function (_html) {
            $("#conPictureFullBrowser .preview-part").html(_html);
            conShowPictrueObj.showThis(self.curIndex);
            //拖拽
            $("#pictureFullBrowserCurr").draggable({
                start: function () {
                    console.log(arguments);
                },
                drag: function () {
                    console.log(arguments);
                },
                containment: "parent"
            });
            //还原：$( "#pictureFullBrowserCurr" ).draggable({ revert: true });
            //注册事件
            //关闭
            $("#conPictureFullBrowser").on("click", ".close-part", function () {
                $("#conPictureFullBrowser").remove();
            });
            //上一张，下一张
            $("#conPictureFullBrowser").on("click", ".left-part", function () {
                conShowPictrueObj.showPrev();
            });
            $("#conPictureFullBrowser").on("click", ".right-part", function () {
                conShowPictrueObj.showNext();
            });
            //缩放
            $("#pictureFullBrowserCurr").on("mousewheel", function (event) {
                var self = $("#pictureFullBrowserCurr");
                self.removeClass("picture-full-browser-curr");
                if ((event.originalEvent.wheelDelta > 0) && (conShowPictrueObj.rate < 12)) {
                    self.width(self.width() * 1.25);
                    conShowPictrueObj.rate++;
                } else if (conShowPictrueObj.rate > -10) {
                    self.width(self.width() * 0.8);
                    conShowPictrueObj.rate--;
                }
                event.stopPropagation();
                event.preventDefault();
            });
        });
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

/**
 * 日期格式化
 * @param timestamp
 * @returns {*}
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

//格式化代码函数,已经用原生方式写好了不需要改动,直接引用就好
function formatJson (json) {
    var formatted = '',     //转换后的json字符串
        padIdx = 0,         //换行后是否增减PADDING的标识
        PADDING = '    ';   //4个空格符
    /**
     * 将对象转化为string
     */
    if (typeof json !== 'string') {
        json = JSON.stringify(json);
    }
    /**
     *利用正则类似将{'name':'ccy','age':18,'info':['address':'wuhan','interest':'playCards']}
     *---> \r\n{\r\n'name':'ccy',\r\n'age':18,\r\n
     *'info':\r\n[\r\n'address':'wuhan',\r\n'interest':'playCards'\r\n]\r\n}\r\n
     */
    json = json.replace(/([\{\}])/g, '\r\n$1\r\n')
        .replace(/([\[\]])/g, '\r\n$1\r\n')
        .replace(/(\,)/g, '$1\r\n')
        .replace(/(\r\n\r\n)/g, '\r\n')
        .replace(/\r\n\,/g, ',');
    /**
     * 根据split生成数据进行遍历，一行行判断是否增减PADDING
     */
    (json.split('\r\n')).forEach(function (node, index) {
        var indent = 0,
            padding = '';
        if (node.match(/\{$/) || node.match(/\[$/)) indent = 1;
        else if (node.match(/\}/) || node.match(/\]/))  padIdx = padIdx !== 0 ? --padIdx : padIdx;
        else    indent = 0;
        for (var i = 0; i < padIdx; i++)    padding += PADDING;
        formatted += padding + node + '\r\n';
        padIdx += indent;
    });
    return formatted;
};

function add0(time) {
    if (time < 10) {
        return '0' + time;
    }
    return time;
}

function alertMsg(msg){
    layer.alert("<div style='text-align: center'>" + msg + "</div>", {title: '提示'});
}

function failMsg(msg){
    layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">' + msg);
}

function warnMsg(msg){
    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}

function delSuccessMsg(){
    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">删除成功');
}

function delFailMsg(){
    layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">删除失败，请重试');
}

function saveSuccessMsg(){
    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功');
}
function successMsg(msg){
    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">' + msg);
}

function saveFailMsg(){
    layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">提交失败，请重试');
}

function getParamType(typeKey) {
    var cstype = typeKey;
    if (typeKey == 1) {
        cstype = "常量";
    } else if (typeKey == 2) {
        cstype = "入参";
    } else if (typeKey == 3) {
        cstype = "动参";
    } else if (typeKey == 4) {
        cstype = "实体";
    }
    return cstype;
}

// 设置列表高度
function setHeight(searchHeight) {
    if (isNullOrEmpty(searchHeight)) {
        searchHeight = 131;
    }
    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
        $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
    }else {
        $('.layui-table-body').height($('.bdc-table-box').height() - searchHeight);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - searchHeight - 17);
    }
}

//把添加遮罩及移除遮罩方法添加在自己的通用js中，或者引入当前js，注释上面两行，调用即可使用
function addModel() {
    var modalHtml = '<div id="waitModalLayer">'+
        '<p class="bdc-wait-content">'+
        '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>'+
        // '<i class="layui-icon layui-icon-loading"></i>'+
        '<span>加载中</span>'+
        '</p>'+
        '</div>';
    $('body').append(modalHtml);
}

function removeModal() {
    $("#waitModalLayer").remove();
}

//---------------------------规则数据验证--------------------------------
/**
 * 校验规则配置项是否全面
 */
function checkConfig(isShowMsg){
    var gzmc = $("#gzmc").val();
    if (isNullOrEmpty(gzmc)) {
        warnMsg("请输入子规则名称！");
        return false;
    }

    var yxj = $("#yxj").val();
    if (isNullOrEmpty(yxj) || !/^[0-9]*$/.test(yxj)) {
        warnMsg("子规则优先级请参照提示输入数字！");
        return false;
    }

    if($.isEmptyObject(bdcGzZgzDTO.bdcGzSjlDTOList)){
        warnMsg("未配置数据流内容！");
        return false;
    }

    if($.isEmptyObject(bdcGzZgzDTO.bdcGzBdsTsxxDOList) && isNullOrEmpty($("#jydm").val())){
        warnMsg("未配置结果校验内容（图形或编码）");
        return false;
    }

    // 验证数据流中的SQL或服务内容格式是否正确
    for(var index = 0; index < bdcGzZgzDTO.bdcGzSjlDTOList.length; index++){
        var isSuccess = bdcGzZgzSjlCheck(bdcGzZgzDTO.bdcGzSjlDTOList[index]);
        if(!isSuccess){
            return false;
        }
    }

    if(isShowMsg){
        successMsg("校验成功！");
    }
}

/**
 * 验证子规则数据流内容是否正确
 * @param sjlDTO 数据流实体
 * @returns {boolean} 成功：true；失败：false
 */
function bdcGzZgzSjlCheck(sjlDTO) {
    var isSuccess = true;
    var sjlmc = sjlDTO.sjlmc;
    var sjly = sjlDTO.sjly;
    var qqyy = sjlDTO.qqyy;
    var fwff = sjlDTO.fwff;
    var sjnr = sjlDTO.sjnr;
    var jgblbs = sjlDTO.jgblbs;

    if(isNullOrEmpty(sjlmc)){
        warnMsg("存在未配置数据流名称情况！");
        return false;
    }

    if(sjlmc.length > 15){
        sjlmc = sjlmc.substr(0, 15) + "...";
    }

    if(isNullOrEmpty(sjly)){
        warnMsg("数据流【" + sjlmc + "】未配置数据来源！");
        return false;
    }
    if(isNullOrEmpty(sjnr)){
        warnMsg("数据流【" + sjlmc + "】未配置数据内容！");
        return false;
    }
    if(isNullOrEmpty(jgblbs)){
        warnMsg("数据流【" + sjlmc + "】未配置保存变量！");
        return false;
    }

    if("2" == sjly){
        if(isNullOrEmpty(qqyy)){
            warnMsg("数据流【" + sjlmc + "】未配置请求应用！");
            return false;
        }
        if(isNullOrEmpty(fwff)){
            warnMsg("数据流【" + sjlmc + "】未配置HTTP方法！");
            return false;
        }
    }

    // 获取数据内容中配置的参数，形式 #{1}  #{2}  &{5}
    var csArr = sjnr.match(/[&#]\{([\S]*)\}/g);
    if(!$.isEmptyObject(csArr)){
        $.each(csArr, function (key, cs) {
            if(cs && !/[&#]\{([1-9][0-9]*)\}/.test(cs)){
                layer.alert("参数格式错误，详细内容<br>* 数据流名称:&nbsp;&nbsp;" + sjlmc + "<br>* 错误参数:&nbsp;&nbsp;" + cs);
                isSuccess = false;
                return false;
            }
        });
    }
    if(!isSuccess) {
        return false;
    }

    var csArr2 = sjnr.match(/[&#]\{([\sa-zA-Z0-9]*)\}/g);
    if(!$.isEmptyObject(csArr2)){
        $.each(csArr2, function (key, cs) {
            if(cs && !/[&#]\{([1-9][0-9]*)\}/.test(cs)){
                layer.alert("参数格式错误，详细内容<br>* 数据流名称:&nbsp;&nbsp;" + sjlmc + "<br>* 错误参数:&nbsp;&nbsp;" + cs);
                isSuccess = false;
                return false;
            }
        });
    }
    if(!isSuccess) {
        return false;
    }

    // 判断有没有设置参数
    // 数据内容中有参数，但是设置的参数和下面参数列表设置不一致
    csArr =  sjnr.match(/[&#]\{[1-9][0-9]*\}/g);
    if(!$.isEmptyObject(csArr)){
        var csLength = 0;
        $.each(sjlDTO.bdcGzSjlCsDOList, function (key, sjlcs) {
            if(sjlcs.sjlcszl != 4) csLength++;
        });

        if(csArr.length != csLength){
            layer.alert("以下数据流参数与数据内容不一致<br>* 数据流名称:&nbsp;&nbsp;" + sjlmc + "<br>* 应设置参数个数:&nbsp;&nbsp;" + csArr.length);
            isSuccess = false;
            return false;
        }

        // 验证数据内容中设置的参数下面是否设置，按照序号对应
        $.each(csArr, function (key, csnr) {
            var exist = false;
            $.each(sjlDTO.bdcGzSjlCsDOList, function (key, sjlcs) {
                var item1 = '#{' + sjlcs.sjlcsxh +'}';
                var item2 = '&{' + sjlcs.sjlcsxh +'}';
                if((csnr == item1 || csnr == item2) &&  sjlcs.sjlcszl != 4){
                    exist = true;
                }
            });

            if(!exist){
                layer.alert("数据内容中 " + csnr + " 未设置对应参数！");
                isSuccess = false;
                return false;
            }
        });
    }
    if(!isSuccess) {
        return false;
    }

    // 数据内容中无参数，但是下面参数列表却设置了参数
    if($.isEmptyObject(csArr) && sjlDTO.bdcGzSjlCsDOList){
        var csLength = 0;
        $.each(sjlDTO.bdcGzSjlCsDOList, function (key, sjlcs) {
            if(sjlcs.sjlcszl == 1 || sjlcs.sjlcszl == 2) csLength++;
        });

        if(csLength > 0){
            layer.alert("以下数据流参数与数据内容不一致<br>* 数据流名称:&nbsp;&nbsp;" + sjlmc);
            isSuccess = false;
            return false;
        }
    }
    if(!isSuccess) {
        return false;
    }

    // 判断参数内容是否设置
    var csDOList = sjlDTO.bdcGzSjlCsDOList;
    if(!$.isEmptyObject(csDOList)){
        $.each(csDOList, function (key, cs) {
            if(isNullOrEmpty(cs.sjlcsxh)){
                layer.alert("以下数据流未设置参数序号<br>* 数据流名称:&nbsp;&nbsp;" + sjlmc);
                isSuccess = false;
                return false;
            }

            if(isNullOrEmpty(cs.sjlcszl)){
                layer.alert("以下数据流未设置参数类型<br>* 数据流名称:&nbsp;&nbsp;" + sjlmc+ "<br>* 对应参数序号:&nbsp;&nbsp;" + cs.sjlcsxh);
                isSuccess = false;
                return false;
            }

            if(isNullOrEmpty(cs.sjlcsmc)){
                layer.alert("以下数据流未设置参数名称<br>* 数据流名称:&nbsp;&nbsp;" + sjlmc+ "<br>* 对应参数序号:&nbsp;&nbsp;" + cs.sjlcsxh);
                isSuccess = false;
                return false;
            }

            if("1" == cs.sjlcszl && isNullOrEmpty(cs.sjlcsz)){
                layer.alert("以下数据流未设置参数默认值<br>* 数据流名称:&nbsp;&nbsp;" + sjlmc+ "<br>* 对应参数名称:&nbsp;&nbsp;" + cs.sjlcsmc);
                isSuccess = false;
                return false;
            }
        });
    }
    if(!isSuccess) {
        return false;
    }
    return true;
}

/**
 * 校验规则表达式
 * @returns {boolean} 成功 true 失败 false
 */
function checkGzBds() {
    if($.isEmptyObject(bdcGzZgzDTO.bdcGzBdsTsxxDOList)) {
        // 没配置表达式不校验
        return true;
    }

    var jyczf1 = [">=", "<=", "==", "!=", "不包含", "开始于", "结束于", "并且", "或"];
    var jyczf2 = ["不为空"];

    var result = true;
    $.each(bdcGzZgzDTO.bdcGzBdsTsxxDOList, function (index, item) {
        $.each(jyczf1, function (index, czf) {
            if(!checkGzBdsCzf(item.gzbds, czf, "1")) {
                result = false;
                return false;
            }
        });

        $.each(jyczf2, function (index, czf) {
            if(!checkGzBdsCzf(item.gzbds, czf, "2")) {
                result = false;
                return false;
            }
        });

        if(!result)  return false;

        result = chechGzBdsCzf2(item.gzbds,">", ">=", 1);  if(!result)  return false;
        result = chechGzBdsCzf2(item.gzbds,"<", "<=", 1);  if(!result)  return false;
        result = chechGzBdsCzf2(item.gzbds,"包含", "不包含", 1); if(!result)  return false;
        result = chechGzBdsCzf2(item.gzbds,"为空", "不为空", 2); if(!result)  return false;
    });
    return result;
}

/**
 * 校验规则表达式中校验符格式是否正确
 * @param bds 规则表达式
 * @param czf 操作符
 * @param type 操作符类型
 * @returns {boolean} 成功 true 失败 false
 */
function checkGzBdsCzf(bds, czf, type) {
    var str = type == "1" ? " " : "";
    var str2 = type == "1" ? "前后" : "前面";

    if(bds.indexOf(czf) > -1) {
        var reg = new RegExp(" " + czf + str, "g");
        bds = bds.replace(reg, "");
        if(bds.indexOf(czf) > -1) {
            warnMsg("规则表达式中  " + czf + " " + str2 + "需要空格分隔！");
            return false;
        }
    }
    return true;
}

/**
 * 校验规则表达式中校验符格式是否正确（czf2包含czf1名称，需要特殊判断）
 * @param bds 规则表达式
 * @param czf1 操作符
 * @param czf2 操作符2
 * @param type 操作符类型
 * @returns {boolean} 成功 true 失败 false
 */
function chechGzBdsCzf2(gzbds, czf1, czf2, type) {
    var bds = gzbds;
    if(gzbds.indexOf(czf1) > -1 && gzbds.indexOf(czf2) > -1) {
        var reg = new RegExp(czf2, "g");
        bds = gzbds.replace(reg, "");
    }
    return checkGzBdsCzf(bds, czf1, type);
}

// 动态代码校验
$("#check-code").click(function () {
    var code = $("#jydm").val();
    if(isNullOrEmpty(code)){
        warnMsg("请输入代码内容！");
        return;
    }

    var param = $("#param").val();
    if(isNullOrEmpty(param)){
        warnMsg("请输入测试参数！");
        return;
    }

    $.ajax({
        url: inquiryUiUrl + '/zgz/code/check',
        type: "POST",
        data: JSON.stringify({"code":code, "param":param}),
        contentType: 'application/json',
        dataType: "text",
        success: function (data) {
            $("#jyjg").val(data);
        },
        error: function (data) {
            $("#jyjg").val(data);
        }
    })
});

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

// 保存记录操作信息
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
        url: inquiryUiUrl + "/rest/v1.0/log/info",
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