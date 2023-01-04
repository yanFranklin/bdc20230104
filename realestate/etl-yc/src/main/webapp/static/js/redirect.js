
function getPortalIframe(){
    // return "http://192.172.0.101:8087/portal-ui/src/main/webapp/view/iframe.html";
    return getPortalUrl() + "/view/iframe.html";
}

function getPortalRefreshIframe(){
    // return "http://192.172.0.101:8087/portal-ui/src/main/webapp/view/iframe-refresh.html"
    return getPortalUrl() + "/view/iframe-refresh.html";
}

function getPortalDelIframe(){
    // return "http://192.172.0.101:8087/portal-ui/src/main/webapp/view/iframe-delete.html"
    return getPortalUrl() + "/view/iframe-delete.html";
}

function getPortalChangeUrlIframe(){
    return getPortalUrl() + "/view/iframe-change.html";
}

/**
 * 返回
 * @param url
 */
function backToView(url){
    window.location.href = url;
}

/**
 * 向外跳转
 * @param url
 *
 */
function toView(url,paramObject){
    if(!paramObject){
        paramObject = {};
    }
    if(!paramObject.fromurl){
        paramObject.fromurl = window.location.href;
    }
    var parser = document.createElement('a');
    parser.href = url;
    // var urlObject = new URL(url);
    // $.session.remove(urlObject.pathname);
    // $.session.set(urlObject.pathname, JSON.stringify(paramObject));
    // var url = urlObject.href;
    $.session.remove(parser.pathname);
    $.session.set(parser.pathname, JSON.stringify(paramObject));
    var url = parser.href;
    if(url.indexOf("?") > 0){
        url += "&";
    }else{
        url += "?";
    }
    url += "lastsessionid="+$.session._id;
    addModel();
    if(paramObject.tabname){
        $.ajax({
            url: url,
            success: function (data) {
                removeModal();
                crossFrame(getPortalIframe() + "?name="+encodeURIComponent(paramObject.tabname)+"&url="+encodeURIComponent(url));
            },
            error: function (e) {
                delAjaxErrorMsg(e,this);
            }
        });
    }else{
        $.ajax({
            url: url,
            success: function (data) {
                removeModal();
                window.location.href = url;
            },
            error: function (e) {
                delAjaxErrorMsg(e,this);
            }
        });
    }
}


/**
 * 向外跳转
 * @param url

 function toView(url,paramObject){
    var zh = "子页面测试";
    crossFrame("http://192.168.3.171:8086/bdcui/home-page/view/iframe.html?name="+encodeURI(zh)+"&url="+encodeURI(url));
}*/


/**
 * 为当前页面 保存 session 参数
 * @param paramObj
 */
function saveSessionParam(paramObj){
    var thisUrl = window.location.pathname;
    var yParamObjStr = $.session.get(thisUrl);
    var yParamObj = {};
    if(yParamObjStr){
        yParamObj = $.parseJSON(yParamObjStr);
    }
    $.session.remove(thisUrl);
    $.session.set(thisUrl, JSON.stringify($.extend({}, yParamObj, paramObj)));
}

/**
 * 获取 session中的参数
 * @returns {{}}
function getParamObject(){
    var url = window.location.pathname;
    var paramObjectStr = $.session.get(url);
    if(paramObjectStr){
        return $.parseJSON(paramObjectStr);
    }else{
        return {};
    }
} */

var sessionParamObjet = {};

function setSessionParamObject(){
    var url = window.parent.location.pathname;
    var thisParam = {};
    var thisParamObjStr = $.session.get(url);
    if(thisParamObjStr){
        thisParam = $.parseJSON(thisParamObjStr);
    }
    var lastsessionid = GetQueryString("lastsessionid");
    var paramObjectStr = '';
    if(lastsessionid){
        paramObjectStr = $.session.getWithId(lastsessionid,url);
    }
    if(paramObjectStr){
        sessionParamObjet = $.extend({},thisParam,$.parseJSON(paramObjectStr));
        var fromUrl = sessionParamObjet.fromurl;
        $(".lpb-back-btn").attr("onclick","backToView('"+fromUrl+"')");
    }
    return sessionParamObjet;
}
/**
 * 设置返回按钮URL
 */
$(function () {
    setSessionParamObject();
});

/**
 * 刷新TAB
 * @param url
 */
function refreshIframe(url,closeCurTab,refreshLater) {
    var param = "?url="+encodeURIComponent(url);
    if(closeCurTab){
        param += "&isDelete=true";
    }
    if(refreshLater){
        param += "&isRefresh=true";
    }
    crossFrame(getPortalRefreshIframe()+param);
}

function refreshAndDelete(msg,refreshLater){
    refreshIframe(sessionParamObjet.fromurl,false,refreshLater);
    layer.msg(msg, {
        time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
    }, function(){
        deleteIframe();
    });
}

function refreshAndDeleteLater(msg,refreshLater,deleteLater){
    refreshIframe(sessionParamObjet.fromurl,false,refreshLater);
    if(deleteLater){
        layer.msg(msg, {
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        }, function(){
            deleteIframe();
        });
    }else{
        layer.msg(msg);
    }
}

/**
 * 关闭当前TAB
 */
function deleteIframe(){
    crossFrame(getPortalDelIframe());
}

function addParamToFrameUrl(param){
    var oldUrl = window.location.href;
    var newUrl = "";
    if(oldUrl.indexOf("?") > 0){
        newUrl = oldUrl + "&" + param;
    }else{
        newUrl = oldUrl + "?" + param;
    }
    changeIframe(oldUrl,newUrl);
}

function changeIframe(oldUrl,newUrl) {
    crossFrame(getPortalChangeUrlIframe() + "?oldUrl="+ encodeURIComponent(oldUrl) +"&newUrl="+encodeURIComponent(newUrl));
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);//search,查询？后面的参数，并匹配正则
    if (r != null) return unescape(r[2]);
    return null;
}
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
function getPortalUrl() {
    return document.location.protocol + "//" + window.location.host + "/realestate-portal-ui";
}