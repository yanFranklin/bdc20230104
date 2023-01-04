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
                if(showColumn.btnMore){
                    moreList.push(showColumn);
                }else{
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
    consoleWithTime('showColumn');
    dealColumn(resource);
    consoleWithTime('dealColumn');
    layui.use(['laytpl', 'form'], function () {
        var form = layui.form;
        form.render("checkbox", "showColumForm");
    });
    consoleWithTime('form.render');
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
        showColumn(ref,data.elem.checked,currentRes);
        dealColumn(currentRes);
    });
});

function showColumn(ref,checked,resource){
    var refInfo = ref.info;
    var refStatus = ref.status;
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
    if(refStatus && refStatus != 'undefined'){
        var refStatusArr = refStatus.split(",");
        for(var i = 0 ; i < refStatusArr.length ; i++){
            var stateElement = resource.tabDocument.find("span[mark='"+refStatusArr[i]+"']");
            if(checked){
                parentElement.find(".bdc-state-show i[mark='"+refStatusArr[i]+"']").attr("show","true");
                stateElement.removeClass("layui-hide");
            }else{
                parentElement.find(".bdc-state-show i[mark='"+refStatusArr[i]+"']").removeAttr("show");
                stateElement.addClass("layui-hide");
            }
        }
    }
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
    consoleWithTime("获取交易状态");
    // 获取当前TAB页表格数据中的QLZT 和 JYZT 是否都是隐藏状态，如果是，就给个文案“无交易状态” “无权利状态”
    parentElement.find(".bdc-table-state.bdc-ql-state").each(function(){
        $(this)[0].innerHTML=$(this).html().replace("无权利状态","");
        if($(this).html() != "无权利状态"&&
            ($(this).find("span").length==0
                || $(this).find("span").length == $(this).find("span.layui-hide").length)){
            $(this)[0].innerHTML="无权利状态"+$(this).html();
        }
    });
    consoleWithTime("循环qlztElems");
    parentElement.find(".bdc-table-state.bdc-jy-state").each(function(){
        $(this)[0].innerHTML=$(this).html().replace("无交易状态","");
        if($(this).html() != "无交易状态" &&
            ($(this).find("span").length==0 ||
                $(this).find("span").length == $(this).find("span.layui-hide").length)){
            $(this)[0].innerHTML="无交易状态"+$(this).html();
        }
    });
    consoleWithTime("循环jyztElems");
    var tdHeight = parentElement.find('.layui-table-view .bdc-td-show').parent().height()+11;
    parentElement.find('.layui-table-view .layui-table td').css('height',tdHeight);
    consoleWithTime("重置单元格高度 b ");
    parentElement.find('.layui-table-fixed .layui-table tr:first-child td').css('height',tdHeight);
    consoleWithTime("重置单元格高度 c ");
}

var res_readjyzt = {hs:false,ychs:false};
/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param
 * @return
 * @description 获取异步方法的参数
 */
function getHfJyzt(){
    if(!eval("res_readjyzt."+currentRes.tabname)){
        eval("res_readjyzt."+currentRes.tabname+"=true");
        // 单次请求 执行数量
        var pageSize = 40;
        var params = [];
        // 获取 所有需要 请求的 参数
        var pList = currentRes.tabDocument.find("p[mark='ysfwbm']");
        // 循环 发送请求
        for(var i = 0 ;i < pList.length;i++){
            var item = pList[i];
            if(item && $(item).attr("title")){
                $(item).html("");
                params.push($(item).attr("title"));
                if(params.length == pageSize){
                    // 发送请求
                    getHfJyztAjax(params);
                    params = [];
                }
            }
        }

        if(params.length > 0){
            getHfJyztAjax(params);
        }
    }
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param
 * @return
 * @description 发送请求
 */
function getHfJyztAjax(params){
    $.ajax({
        url: "../hf/jyzt/byysfwbm",
        data: "ysfwbmList=" + encodeURI(params),
        success: function (data) {
            // 回填
            for(var i = 0 ;i < data.length ; i++){
                var htmlStr=data[i].jyzt;
                if(data[i].color){
                    htmlStr='<span style="color:#'+data[i].color+'">'+data[i].jyzt+'</span>';
                }
                currentRes.tabDocument.find("p[title='"+data[i].ysfwbm+"']").html(htmlStr);
            }
        }
    });
}



var res_readQlr = {hs:false,ychs:false};
function getXscq(){
    if(!eval("res_readQlr."+currentRes.tabname)){
        eval("res_readQlr."+currentRes.tabname+"=true");
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
            }
        }
    });
}
