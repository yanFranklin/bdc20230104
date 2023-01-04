function initYchsLpb(){
    if (resourceData.links) {
        for (var i = 0; i < resourceData.links.length; i++) {
            var link = resourceData.links[i];
            if (link.rel == "ychsDataUrl") {
                ychsRes.dataUrl = link.href;
                currentRes = ychsRes;
            }
        }
    }
    if(ychsRes.dataUrl){
        showYchsTab();
        // initYchsBtns();
        // if(ychsRes.tabDocument.find(".bdc-state-show").find("p").length == 0){
        //     initState(ychsRes);
        // }
        // initShowColumn(ychsRes);
    }else{
        // 隐藏宗地TAB
        hideYchsTab();
    }
}

function showYchsTab(){
    $("#ychsTabLi").removeClass("layui-hide");
    $("#ychsTab").removeClass("layui-hide");
}

function hideYchsTab(){
    $("#ychsTabLi").addClass("layui-hide");
    $("#ychsTab").addClass("layui-hide");
}