window.onload = function () {
    try {
        top.excute(getQueryString("url"));
        top.addTab(getQueryString("url"), getQueryString("name"));
    } catch (e) {
        if (getQueryString("errorTime") == null) {
            location.href = iframeIp() + "/iframe.html?errorTime=1&url=" + encodeURIComponent(getQueryString("url"));
        } else if (getQueryString("errorTime") === 1) {
            location.href = iframeIp() + "/iframe.html?errorTime=2&url=" + encodeURIComponent(getQueryString("url"));
        }
    }

    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return decodeURIComponent(r[2]);
        } else {
            return null;
        }
    }
}