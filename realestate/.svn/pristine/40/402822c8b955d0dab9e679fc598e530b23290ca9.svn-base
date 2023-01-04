function initHsLpb(){
    if (resourceData.links) {
        for (var i = 0; i < resourceData.links.length; i++) {
            var link = resourceData.links[i];
            if (link.rel == "fwhsDataUrl") {
                hsRes.dataUrl = link.href;
                currentRes = hsRes;
            }
        }
    }
    if(hsRes.dataUrl){
        showFwhsTab();
        initZsxx(hsRes);
        initState(hsRes);
        initBtns(hsRes);
        initShowColumn(hsRes);
    }else{
        initBtns(hsRes);
        // 隐藏户室TAB
        hideYFwhsTab();
    }
}

function showFwhsTab(){
    $("#hsTabLi").removeClass("layui-hide");
    $("#hsTab").removeClass("layui-hide");
}

function hideYFwhsTab(){
    $("#hsTabLi").addClass("layui-hide");
    $("#hsTab").addClass("layui-hide");
}