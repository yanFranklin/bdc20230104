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
    return document.location.protocol + "//" + window.location.host + "/building-ui";
}

function getPortalUrl() {
    if (portalUrl) {
        return portalUrl;
    }
    return document.location.protocol + "//" + window.location.host + "/realestate-portal-ui";
}


function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);//search,查询？后面的参数，并匹配正则
    if (r != null) return unescape(r[2]);
    return null;
}

//全局定义layer
var layer = function () {
    layui.use(['layer'], function () {
        return layui.layer;
    });
}(jQuery);

/**
 * 跨域嵌套
 * @param url
 */
function crossFrame(url) {
    if (!document.getElementById('crossFrame')) {
        var iframe = document.createElement('iframe');
        // iframe.setAttribute('id','crossFrame');
        iframe.setAttribute('style', 'position:absolute;top:-9999px;left:-9999px;');
        iframe.setAttribute('src', url);
        document.body.appendChild(iframe);
    } else {
        document.getElementById('crossFrame').src = url;
    }
}

//----------------------------------------------------------通用方法-----------------------------------------------------
//等待背景层
function beginAction() {
    var modalHtml = '<div id="waitModalLayer" style="position: fixed;top: 0;bottom: 0;left: 0;right: 0;z-index: 9999999999;background-color: #000;opacity: 0.3;filter:Alpha(opacity=30);"><span style="top: 50%;left: 50%;position: absolute;color: #fff;font-size: 61px;"><i style="font-size:100px;" class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i></span></div>';
    $('body').append(modalHtml);
}

//移除等待背景层
function endAction() {
    $("#waitModalLayer").remove();
}

function disabledAddFa() {
    var disabledElArray = $(":disabled");
    if (disabledElArray != null && disabledElArray.length > 0) {
        for (var i = 0; i < disabledElArray.length; i++) {
            var disabledEl = disabledElArray[i];
            if (disabledEl.type !== "checkbox" && disabledEl.type !== "radio") {
                if ($(disabledEl).is("input") || $(disabledEl).is("textarea") || $(disabledEl).is("select")) {
                    var imgArray = $(disabledEl).parent().find("img");
                    if (imgArray.length == 0) {
                        $(disabledEl).parent().append("<img src=\"../static/lib/bdcui/images/lock.png\" alt=\"\">");
                        $(disabledEl).parent().addClass('bdc-date-disabled');
                    }
                }
            }
        }
    }
    // $('.layui-select-disabled i').addClass('bdc-hide');
    // var img = '<img src="../../static/lib/bdcui/images/lock.png" alt="">';
    // $('.layui-select-disabled dl').after(img);
    $('.layui-select-disabled i').removeClass('layui-edge');
    $('.layui-select-disabled i').addClass('bdc-hide');
    $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
}

//处理ajax的错误
function delAjaxErrorMsg(e, ajaxDO) {
    removeModal();
    var responseText = JSON.parse(e.responseText);
    if (responseText.code === 101) {
        var errorMsg = $.parseJSON(responseText.msg);
        if (errorMsg.method === "alert") {
            layer.msg(errorMsg.errorMsg);
        } else if (errorMsg.method === "confirm") {
            if (ajaxDO.url) {
                var ajaxUrl = ajaxDO.url;
                if (ajaxUrl.indexOf("?") > 0) {
                    ajaxUrl = ajaxUrl + "&omit=omit"
                } else {
                    ajaxUrl = ajaxUrl + "?omit=omit"
                }
                layer.open({
                    content: errorMsg.errorMsg + ';是否忽略？'
                    , btn: ['确定', '取消']
                    , yes: function (index, layero) {
                        ajaxDO.url = ajaxUrl;
                        $.ajax(ajaxDO);
                        layer.close(index);
                    }
                });
            }
        }
    } else {
        layer.msg(responseText.msg);
    }
}

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

//加上单位
function andUnit(target, unit) {
    var result = "";
    if (target && unit) {
        result = target + unit;
    }
    return result;
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

function splitDjh(djh) {
    if (djh && djh.length == 19) {
        return djh.substring(0, 6) + " "
            + djh.substring(6, 12) + " "
            + djh.substring(12, 19);
    } else {
        return djh;
    }
}

function splitBdcdyh(bdcdyh) {
    if (bdcdyh) {
        if (bdcdyh.length == 28) {
            return bdcdyh.substring(0, 6) + " "
                + bdcdyh.substring(6, 12) + " "
                + bdcdyh.substring(12, 19) + " "
                + bdcdyh.substring(19, 28);
        } else {
            return bdcdyh;
        }
    } else {
        return "";
    }
}

function formSubmitDealJson(postData) {
    var info = {};
    var qlrList = [];
    var dataJson = {
        info: info,
        qlrList: qlrList
    };
    for (var key in postData) {
        if (key.indexOf("qlrList") == 0) {
            var qlrIndex = key.substr(key.indexOf("[") + 1, key.indexOf("]") - key.indexOf("[") - 1);
            var qlrName = key.substr(key.indexOf(".") + 1)
            if (!qlrList[qlrIndex]) {
                qlrList.push({});
            }
            eval("qlrList[qlrIndex]." + qlrName + " = postData[key]");
        } else {
            eval("info." + key + " = postData[key]");
        }
    }
    return dataJson;
}

//----------------------------------------------------------控件方法-----------------------------------------------------
//数据表格
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
                    // console.log(arguments);
                },
                drag: function () {
                    // console.log(arguments);
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

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param
 * @return
 * @description 获取定作物特征码
 */
function getDzwTzmByBdcdyh(bdcdyh) {
    if (bdcdyh.length == 28) {
        var dzwtzm = bdcdyh.substr(19, 1);
        return dzwtzm;
    }
    return "";
}

//----------------------------------------------------------浏览器相关方法------------------------------------------------

/**
 *根据模块管理中的元素配置设置权限
 * @param moduleCode 资源名
 */
function setElementAttrByModuleAuthority(moduleCode) {
    $.ajax({
        url: "/building-ui/ljz/moduleAuthority/" + moduleCode,
        type: 'GET',
        dataType: 'json',
        cache: false,
        async: false,
        success: function (data) {
            if (data && !isEmptyObject(data)) {
                for (var i in data) {
                    id = i;
                    attr = data[i];
                    var element = document.getElementById(id);
                    if (element !== null) {
                        //1.隐藏2.只读3.必填
                        if (attr === "hidden") {
                            if (element.type === "button" || element.type === "submit" || element.type === "reset" || element.id === "qtzm") {
                                element.setAttribute("style", "display:none;")
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

                            if (element.id === "qtzm") {
                                //点击更多内a标签，隐藏
                                $('.bdc-table-top-more-show a').hide();
                            }
                        } else if (attr === "required") {
                            element.setAttribute('lay-verify', 'required');
                        } else if (attr === "available") {
                            if (element.type === "button" || element.type === "submit" || element.type === "reset" || element.id === "qtzm") {
                                element.setAttribute("style", "display:;")
                            } else if (element.type === "textarea") {
                                element.parentElement.parentElement.setAttribute('hidden', '');
                            } else {
                                element.parentElement.parentElement.setAttribute('style', 'display:;');
                            }
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

/**
 *根据模块管理中的元素配置对对应class元素设置权限
 * @param moduleCode 资源名
 */
function setElementAttrInTableByModuleAuthority(moduleCode) {
    $.ajax({
        url: "/building-ui/ljz/moduleAuthority/" + moduleCode,
        type: 'GET',
        dataType: 'json',
        cache: false,
        async: false,
        success: function (data) {
            if (data && !isEmptyObject(data)) {
                for (var i in data) {
                    id = i;
                    if (id.indexOf('_intable') > 0) {
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
            }
        }, error: function (xhr, status, error) {
            failMsg("页面权限请求设置失败，请联系管理员！");
        }
    });
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

function failMsg(msg){
    layer.msg('<img src="../static/lib/bdcui/images/error-small.png" alt="">' + msg);
}

function unique(arr) {
    return arr.filter(function(item, index, arr) {
        //当前元素，在原始数组中的第一个索引==当前索引值，否则返回当前元素
        return arr.indexOf(item, 0) === index;
    });
}
var begin = (new Date()).valueOf();
var end = (new Date()).valueOf();
var consoleFlag = false;
function consoleWithTime(info){
    if(consoleFlag){
        end = (new Date()).valueOf();
        console.info(info +":"+ (end-begin));
        begin = (new Date()).valueOf();
    }
}


/*
ajax统一调用处理
* */
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

function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

//操作成功提示
function ityzl_SHOW_SUCCESS_LAYER(msg) {
    layer.msg('<img src="../static/lib/bdcui/images/success-small.png" alt="">' + msg);
}

//信息小提示
function ityzl_SHOW_INFO_LAYER(msg) {
    layer.msg('<img src="../static/lib/bdcui/images/info-small.png" alt="">' + msg);
}

//警告小提示
function ityzl_SHOW_WARN_LAYER(msg) {
    layer.msg('<img src="../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}