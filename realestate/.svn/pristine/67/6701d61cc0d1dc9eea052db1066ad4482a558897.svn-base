function initShowColumn(resource){
    var info = resourceData.info;
    var showList = [];
    var moreList = [];
    for(var key in info){
        // 获取配置类型  判断是否是 展示字段类型
        var type = eval("info." + key + ".type");
        var tabType = eval("info." + key + ".tabType");
        if(type == "SHOWCOLUMN" && resource.checkHasTab(tabType)){
            var showColumn = eval("info." + key);
            // 如果 DESC 不为空 则是 可编辑字段
            if(showColumn.desc){
                resource.showColumn.push({
                    key: showColumn.value,
                    checked: showColumn.defaultShow ? true : false,
                    info: showColumn.refInfo,
                    status: showColumn.refStatus,
                    showFunction: showColumn.showFunction
                });
                if (showColumn.btnMore) {
                    moreList.push(showColumn);
                } else {
                    showList.push(showColumn);
                }
            }
        }
    }
    // 如果展现字段不为空 加载模板
    if(showList || moreList){
        var list = {
            show:showList,
            more:moreList
        };
        layui.use(['laytpl', 'form'], function () {
            var laytpl = layui.laytpl;
            var toolbar = showColumnTpl.innerHTML;
            var toolbarDiv = document.getElementById(resource.toorBarId);
            laytpl(toolbar).render(list, function (html) {
                var yHtml = $(toolbarDiv).html();
                $(toolbarDiv).html(yHtml + html);
            });
        });
    }
}

function checkDefShowColumn(resource){
    var info = resourceData.info;
    for(var key in info){
        // 获取配置类型  判断是否是 展示字段类型
        var type = eval("info." + key + ".type");
        var tabType = eval("info." + key + ".tabType");
        var defaultShow = eval("info." + key + ".defaultShow");
        var desc = eval("info." + key + ".desc");
        var value = eval("info." + key + ".value");
        if(type == "SHOWCOLUMN" && tabType && resource.checkHasTab(tabType)){
            var ishide =  eval("resource.checkedShowColumn."+value)
                && !eval("resource.checkedShowColumn."+value).key;
            if(defaultShow && !ishide){
                resource.tabDocument.find(".bdc-export-tools").find("input[title='"+desc+"']").attr("checked","checked");
                var ref = {
                    key : key,
                    info : eval("info." + key + ".refInfo"),
                    status: eval("info." + key + ".refStatus"),
                    showFunction: eval("info." + key + ".showFunction")
                };
                showColumn(ref,true,resource);
            }
        }
    }
    dealColumn(resource);
    layui.use(['laytpl', 'form'], function () {
        var form = layui.form;
        form.render("checkbox", "showColumForm");
    });
}

layui.use(['laytpl', 'form'], function () {
    var form = layui.form;
    // 监听点击更多中的复选框
    form.on('checkbox(showOthers)', function(data){
        var ref = {
            key : $(data.elem).data('key'),
            info : $(data.elem).data('info'),
            status: $(data.elem).data('status'),
            showFunction: $(data.elem).data('showfunction')
        };
        for(var i=0;i<currentRes.showColumn.length;i++){
            if(currentRes.showColumn[i].key == ref.key){
                currentRes.showColumn[i].checked = data.elem.checked;
            }
        }
        // 重置单元格高度
        countTdHeight(data.elem);
        showColumn(ref,data.elem.checked,currentRes);
        // 单元格高度发生变化 需要重置滚动条
        currentRes.initScrollBar();
        currentRes.yScroll.Refresh(currentRes.yScroll);
        resScrollData(currentRes);
    });
});

function countTdHeight(elem){
    var height = 24;
    if (elem.key == 'qlzt' || elem.key == 'jyzt' || elem.key == 'fzzt') {
        height = 21;
    }
    if(elem.checked){
        currentRes.data.cellHeight = currentRes.data.cellHeight + height;
    }else{
        currentRes.data.cellHeight = currentRes.data.cellHeight - height;
    }
}

function showColumn(ref,checked,resource){
    var refInfo = ref.info;
    var refStatus = ref.status;
    var key = ref.key;
    var parentElement = resource.tabDocument;
    if(checked){
        eval("resource.checkedShowColumn."+ref.key+"=ref");
    } else {
        eval("resource.checkedShowColumn."+ref.key+"={}");
    }
    if(refInfo && refInfo != 'undefined'){
        if(ref.showFunction && ref.showFunction != 'undefined'){
            eval(ref.showFunction+"()");
        }
        var refInfoArr = refInfo.split(",");
        for(var i = 0 ; i < refInfoArr.length ; i++){
            if(checked){
                resource.tabDocument.find(".bdc-describe[mark='"+refInfoArr[i]+"']").removeClass("layui-hide");
            }else{
                resource.tabDocument.find(".bdc-describe[mark='"+refInfoArr[i]+"']").addClass("layui-hide");
            }
        }
    }
    if(key == 'qlzt' && refStatus && refStatus != 'undefined'){
        if(checked){
            parentElement.find(".bdc-ql-state").removeClass("layui-hide");
        }else{
            parentElement.find(".bdc-ql-state").addClass("layui-hide");
        }
    }
    if(key == 'jyzt' && refStatus && refStatus != 'undefined'){
        if(checked){
            parentElement.find(".bdc-jy-state").removeClass("layui-hide");
        }else{
            parentElement.find(".bdc-jy-state").addClass("layui-hide");
        }
    }
    if (key == 'fzzt' && refStatus && refStatus != 'undefined') {
        if (checked) {
            parentElement.find(".bdc-fz-state").removeClass("layui-hide");
        } else {
            parentElement.find(".bdc-fz-state").addClass("layui-hide");
        }
    }
    if(key === 'bazt' && refStatus && refStatus != 'undefined') {
        if (checked) {
            parentElement.find(".bdc-ba-state").removeClass("layui-hide");
        } else {
            parentElement.find(".bdc-ba-state").addClass("layui-hide");
        }
    }

    $('.btn-print').on('click', function (data) {
        var dyh = $(this).attr('bdcdyh');
        dylxArr.push("qjqszm")
        setDypzSession(dylxArr, sessionKey);
        //先判断是否存在现势产权
        var qjqszmModelUrl = getIP() + "/static/printmodel/qjqszm.fr3";
        var fwqszmModelUrl = getIP() + "/static/printmodel/fwqszm.fr3";
        getReturnData("/print/lpb/xscq/" + dyh, {}, "GET", function (data) {
            if (data) {
                printLpbDjbcx('fwqszm', dyh, fwqszmModelUrl);
            } else {
                printLpbDjbcx('qjqszm', dyh, qjqszmModelUrl);
            }
        }, function (xhr) {
        })

    })
    $('.btn-swhst').on('click', function (data) {
        var dyh = $(this).attr('bdcdyh');
        showSwhst(dyh);
    })
    $('.btn-swhst-accept').on('click', function (data) {
        var dyh = $(this).attr('bdcdyh');
        showSwhst(dyh);
    })
    // currentRes.data.cellHeight = tdHeight;
    // currentRes.initScrollBar();
}

function dealColumn(resource){
    var parentElement = resource.tabDocument;
    // 权利状态
    if(parentElement.find(".bdc-state-show p[mark='qlzt'] i[show='true']").length == 0){
        parentElement.find(".bdc-table-state.bdc-ql-state").addClass("layui-hide");
    }else{
        parentElement.find(".bdc-table-state.bdc-ql-state").removeClass("layui-hide");
    }
    consoleWithTime("获取权利状态");
    // 交易状态
    if(parentElement.find(".bdc-state-show p[mark='jyzt'] i[show='true']").length == 0){
        parentElement.find(".bdc-table-state.bdc-jy-state").addClass("layui-hide");
    }else{
        parentElement.find(".bdc-table-state.bdc-jy-state").removeClass("layui-hide");
    }
    // 发证状态
    if (parentElement.find(".bdc-state-show p[mark='fzzt'] i[show='true']").length == 0) {
        parentElement.find(".bdc-table-state.bdc-fz-state").addClass("layui-hide");
    } else {
        parentElement.find(".bdc-table-state.bdc-fz-state").removeClass("layui-hide");
    }
    // 备案状态
    if(parentElement.find(".bdc-state-show p[mark='bazt'] i[show='true']").length == 0){
        parentElement.find(".bdc-table-state.bdc-ba-state").addClass("layui-hide");
    } else {
        parentElement.find(".bdc-table-state.bdc-ba-state").removeClass("layui-hide");
    }
    // 获取当前TAB页表格数据中的QLZT 和 JYZT 是否都是隐藏状态，如果是，就给个文案“无交易状态” “无权利状态”
    parentElement.find(".bdc-table-state.bdc-ql-state").each(function(){
        $(this)[0].innerHTML=$(this).html().replace("无权利状态","");
        if($(this).html() != "无权利状态"&&
            ($(this).find("span").length==0
                || $(this).find("span").length == $(this).find("span.layui-hide").length)){
            $(this)[0].innerHTML="无权利状态"+$(this).html();
        }
    });
    parentElement.find(".bdc-table-state.bdc-jy-state").each(function(){
        $(this)[0].innerHTML=$(this).html().replace("无交易状态","");
        if($(this).html() != "无交易状态" &&
            ($(this).find("span").length==0 ||
                $(this).find("span").length == $(this).find("span.layui-hide").length)){
            $(this)[0].innerHTML="无交易状态"+$(this).html();
        }
    });
    parentElement.find(".bdc-table-state.bdc-fz-state").each(function () {
        $(this)[0].innerHTML = $(this).html().replace("无发证状态", "");
        if ($(this).html() != "无发证状态" &&
            ($(this).find("span").length == 0 ||
            $(this).find("span").length == $(this).find("span.layui-hide").length)) {
            $(this)[0].innerHTML = "无发证状态" + $(this).html();
        }
    });
    //备案状态
    parentElement.find(".bdc-table-state.bdc-ba-state").each(function(){
        $(this)[0].innerHTML=$(this).html().replace("无备案状态","");
        if($(this).html() != "无备案状态"&&
            ($(this).find("span").length==0
                || $(this).find("span").length == $(this).find("span.layui-hide").length)){
            $(this)[0].innerHTML="无备案状态"+$(this).html();
        }
    });
    var tdHeight = parentElement.find('.layui-table-view .bdc-td-show').parent().height()+11;
    parentElement.find('.layui-table-view .layui-table td').css('height',tdHeight);
    parentElement.find('.layui-table-fixed .layui-table tr:first-child td').css('height',tdHeight);
}

// var res_readQlr = {hs:false,ychs:false};
function getXscq(){
    if (currentRes.tabDocument.find("input[data-key='qlr']").parent().find(".layui-form-checked").length > 0) {
        // 单次请求 执行数量
        var pageSize = 40;
        var params = [];
        // 获取 所有需要 请求的 参数
        var pList = currentRes.tabDocument.find("p[mark='bdcdyh']");
        // 循环 发送请求
        for(var i = 0 ;i < pList.length;i++){
            var item = pList[i];
            if(item && $(item).attr("title")){
                $(item).html("");
                params.push($(item).attr("title"));
                if(params.length == pageSize){
                    // 发送请求
                    getXscqAjax(params);
                    params = [];
                }
            }
        }

        if(params.length > 0){
            getXscqAjax(params);
        }
    }
}

function getXscqAjax(params){
    $.ajax({
        url: "../lpb/xmxxbybdcdyh",
        data: "bdcdyhlist=" + encodeURI(params),
        type: "post",
        success: function (data) {
            // 回填
            for(var i = 0 ;i < data.length ; i++){
                var htmlStr = data[i].qlr;
                currentRes.tabDocument.find("p[title='"+data[i].bdcdyh+"']").parent().find("p[mark='qlr']").html(htmlStr);
                currentRes.tabDocument.find("p[title='" + data[i].bdcdyh + "']").parent().find("p[mark='qlr']").attr("title", htmlStr);
            }
        }
    });
}
