var stateColorMap = new Map();
function initState(resource){
    $(".bdc-state-zoom").removeClass("layui-hide");
    var info = resourceData.info;
    var djzt = "";
    var qlzt = "";
    var jyzt = "";
    var fzzt = "";
    var bazt = "";
    for(var key in info){
        var tabType = eval("info." + key + ".tabType");
        if(tabType && resource.checkHasTab(tabType)){
            // 登记状态
            if(key == "djztState"){
                djzt = eval("info." + key + ".constant");
            }
            // 权利状态
            if(key == "qlztState"){
                qlzt = eval("info." + key + ".constant");
            }
            // 交易状态
            if(key == "jyztState"){
                jyzt = eval("info." + key + ".constant");
            }
            // 发证状态
            if (key == "fzztState") {
                fzzt = eval("info." + key + ".constant");
            }
            if(key === "baztState") {
                bazt = eval("info." + key + ".constant");
            }
        }
    }
    var djztHtml = getDjztState(djzt);
    var jyztHtml = getJyztState(jyzt);
    var qlztHtml = getQlztState(qlzt);
    var fzztHtml = getFzztState(fzzt);
    var baztHtml = getBaztState(bazt);
    if (djztHtml || jyztHtml || qlztHtml || fzztHtml || baztHtml) {
        resource.tabDocument.find(".bdc-state-show").html(djztHtml + jyztHtml + fzztHtml + qlztHtml + baztHtml);
        if(resource.tabDocument.find(".layui-icon-up").hasClass("bdc-hide")){
            resource.tabDocument.find(".bdc-state-zoom").click();
        }
    }else{
        // 调整侧边栏 和 表格上方 样式
        removeAside();
        resource.tabDocument.find(".bdc-state-show").addClass("layui-hide");
        resource.tabDocument.find(".bdc-state-zoom").addClass("layui-hide");
    }
}

function getStateColorCss(state){
    if(stateColorMap.get(state)){
        return "background-color:" + stateColorMap.get(state);
    }
    var info = resourceData.info;
    if(eval("info." + state + "_color")){
        var color = eval("info." + state + "_color.constant");
        if(color){
            stateColorMap.set(state,color);
            return "background-color:"+color;
        }
    }
    return "";
}

function getDjztState(djzt){
    var html = "";
    if(djzt){
        var arr = djzt.split(",");
        for(var i = 0 ;i < arr.length ;i++){
            var colorCss = getStateColorCss(arr[i]);
            if(arr[i] == "WDJ"){
                html += "<span><i class=\"bdc-wdj\" style='"+colorCss+"'></i>未登记</span>";
            }
            if(arr[i] == "YG"){
                html += "<span><i class=\"bdc-ywg\" style='"+colorCss+"'></i>已预告</span>";
            }
            if(arr[i] == "DJ"){
                html += "<span><i class=\"bdc-ydj\" style='"+colorCss+"'></i>已登记</span>";
            }
            if(arr[i] == "ZX"){
                html += "<span><i class=\"bdc-yzx\" style='"+colorCss+"'></i>已注销</span>";
            }
        }
    }
    if(html){
        html = "<p>登记状态：" + html +"</p>";
    }
    return html;
}

function getJyztState(jyzt){
    var html = "";
    if (jyzt && showjyzt !== "0") {
        var arr = jyzt.split(",");
        for (var i = 0; i < arr.length; i++) {
            var colorCss = getStateColorCss(arr[i]);
            if (arr[i] == "KS") {
                html += "<span><i mark='" + arr[i] + "' class=\"bdc-ks\" style='" + colorCss + "'></i>可售</span>";
            }
            if (arr[i] == "YS") {
                html += "<span><i mark='" + arr[i] + "' class=\"bdc-ys\" style='" + colorCss + "'></i>已售</span>";
            }
            if (arr[i] == "CQ") {
                html += "<span><i mark='"+arr[i]+"' class=\"bdc-cq\" style='"+colorCss+"'></i>草签</span>";
            }
            if(arr[i] == "WGYF"){
                html += "<span><i mark='"+arr[i]+"' class=\"bdc-wg\" style='"+colorCss+"'></i>物管用房</span>";
            }
            if(arr[i] == "AZF"){
                html += "<span><i mark='"+arr[i]+"' class=\"bdc-az\" style='"+colorCss+"'></i>安置房</span>";
            }
            if(arr[i] == "BLF"){
                html += "<span><i mark='"+arr[i]+"' class=\"bdc-bl\" style='"+colorCss+"'></i>保留房</span>";
            }
            if(arr[i] == "FPTZF"){
                html += "<span><i mark='"+arr[i]+"' class=\"bdc-fpt\" style='"+colorCss+"'></i>非普通住房</span>";
            }
        }
    }
    if(html){
        html = "<p mark='jyzt'>交易状态：" + html +"</p>";
    }
    return html;
}

function getQlztState(qlzt) {
    var html = "";
    if(qlzt){
        var arr = qlzt.split(",");
        for(var i = 0 ;i < arr.length ;i++){
            var colorCss = getStateColorCss(arr[i]);
            if(arr[i] == "ZJGCDY"){
                html += "<span><i mark='"+arr[i]+"' class=\"bdc-zj\"  style='"+colorCss+"'></i>在建工程抵押</span>";
            }
            if(arr[i] == "YDYA"){
                html += "<span><i mark='"+arr[i]+"' class=\"bdc-ydy\"  style='"+colorCss+"'></i>预抵押</span>";
            }
            if (arr[i] == "DYA") {
                html += "<span><i mark='" + arr[i] + "' class=\"bdc-dya\"  style='" + colorCss + "'></i>抵押</span>";
            }
            if (arr[i] == "YCF") {
                html += "<span><i mark='" + arr[i] + "' class=\"bdc-ycf\"  style='" + colorCss + "'></i>预查封</span>";
            }
            if (arr[i] == "CF") {
                html += "<span><i mark='" + arr[i] + "' class=\"bdc-cf\"  style='" + colorCss + "'></i>查封</span>";
            }
            if (arr[i] == "SD") {
                html += "<span><i mark='" + arr[i] + "' class=\"bdc-dj\"  style='" + colorCss + "'></i>锁定</span>";
            }
            if (arr[i] == "YY") {
                html += "<span><i mark='" + arr[i] + "' class=\"bdc-yy\"  style='" + colorCss + "'></i>异议</span>";
            }
            if (arr[i] == "DYI") {
                html += "<span><i mark='" + arr[i] + "' class=\"bdc-dy\"  style='" + colorCss + "'></i>地役</span>";
            }
            if (arr[i] == "JZQ") {
                html += "<span><i mark='" + arr[i] + "' class=\"bdc-jzq\"  style='" + colorCss + "'></i>居住权</span>";
            }
        }
    }
    if(html){
        html = "<p mark='qlzt'>权利状态：" + html +"</p>";
    }
    return html;
}

function getFzztState(fzzt) {
    var html = "";
    if (fzzt) {
        var arr = fzzt.split(",");
        for (var i = 0; i < arr.length; i++) {
            var colorCss = getStateColorCss(arr[i]);
            if (arr[i] == "ZS") {
                html += "<span><i class=\"bdc-zs\" style='" + colorCss + "'></i>生成证书</span>";
            }
            if (arr[i] == "ZM") {
                html += "<span><i class=\"bdc-zm\" style='" + colorCss + "'></i>生成证明书</span>";
            }
            if (arr[i] == "BFZ") {
                html += "<span><i class=\"bdc-bfz\" style='" + colorCss + "'></i>只登簿不发证</span>";
            }
        }
    }
    if (html) {
        html = "<p>发证状态：" + html + "</p>";
    }
    return html;
}

//备案状态展示
function getBaztState(bazt) {
    var html = "";
    if(bazt) {
        var arr = bazt.split(",");
        for (var i = 0; i < arr.length; i++) {
            var colorCss = getStateColorCss(arr[i]);
            if (arr[i] == "BA") {
                html += "<span><i class=\"bdc-ba\" style='" + colorCss + "'></i>备案</span>";
            }
        }
    }
    if (html) {
        html = "<p>备案状态：" + html + "</p>";
    }
    return html;
}


// 调整 收缩按钮高度
function refreshZoomTop(element){
    var height = getStateHeight(element);
    if(height == 0){
        element.find(".bdc-state-zoom").addClass("layui-hide");
    }else{
        element.find(".bdc-state-zoom").animate({"top": (height + 10) + "px"}, 100);
    }
}

function getStateHeight(element){
    var pLength = element.find(".bdc-state-show").find("p").length;
    var height = 0;
    for(var i = 0;i<pLength;i++){
        height += 26;
    }
    return height;
}

function getTableHeight(element){
    return element.find(".bdc-state-show").height();
}
// 表格上方 间距
function removeAside() {
    $('.layui-tab-content').css('padding-top','15px');
}