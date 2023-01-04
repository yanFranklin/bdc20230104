
/**
 * 单次增加
 * @type {Array}
 */
var everyAdd = [];
/**
 * 单次移除
 * @type {Array}
 */
var everyRemove = [];

var globalMsg = "";

var constant_nomsg = "nomsg";
var indexElementArr = [];
var indexElementIndexArr = [];
$(function(){

    /**
     * 购物车图标点击事件
     */
    //单个确认点击
    $("body").on("click","a.one-icon",function () {
        var thisElement = this;
        addModel();
        setTimeout(function(){
            clearGlob();
            //获取单元号以及物理层数
            var dyh = $(thisElement).parent().find("span").attr("data-dyh");
            var wlcs = $(thisElement).parent().find("span").attr("data-wlcs");
            if($(thisElement).hasClass("one-no-select")){
                onSelect(thisElement);
                addCartFunction(everyAdd);
                selWlc();
                selDyh();
            }else{
                cancelSelect(thisElement);
                $("a.hang-icon").each(function () {
                    if($(thisElement).parent().text() == wlcs){
                        $(thisElement).removeClass("hang-onselected");
                        $(thisElement).addClass("hang-no-select");
                    }
                });
                $("a.lie-icon").each(function () {
                    if($(thisElement).parent().text() == dyh){
                        $(thisElement).removeClass("lie-onselected");
                        $(thisElement).addClass("lie-no-select");
                    }
                });
                removeCartFuntion(everyRemove);
                cancelWlc();
                cancelDyh();
            }
        },50);
    });
    //物理层确认点击
    $("body").on("click","a.hang-icon",function () {
        var thisElement = this;
        addModel();
        setTimeout(function() {
            clearGlob();
            //获取当前物理层(锁定)
            var fixTrElement = $(thisElement).parents("tr");
            var trIndex = fixTrElement.index();
            //获取主表上的当前层 trIndex+1
            var mainTrElement = currentRes.tabDocument.find('.layui-table-main tbody tr:nth-child('+ (trIndex+1) +')');
            if ($(thisElement).hasClass("hang-no-select")) {
                // //整行选中
                selectTrAllTd(mainTrElement);
                addCartFunction(everyAdd);
                selDyh();
            } else {
                $(thisElement).removeClass("hang-onselected");
                $(thisElement).addClass("hang-no-select");
                //物理层取消选择
                noselectTrAllTd(mainTrElement);
                //console.info("everyremove:"+everyRemove);
                removeCartFuntion(everyRemove);
                cancelDyh();
            }
        },50);
    });
    //单元号确认点击
    $("body").on("click","a.lie-icon",function () {
        addModel();
        var thisElement = this;
        setTimeout(function() {
            clearGlob();
            // 获取当前单元号
            var lie = $(thisElement).parent().text();
            if($(thisElement).hasClass("lie-no-select")){
                $(thisElement).removeClass("lie-no-select");
                $(thisElement).addClass("lie-onselected");
                //整列选中
                selectalllie(lie);
                addCartFunction(everyAdd);
                selWlc();
            }else{
                $(thisElement).removeClass("lie-onselected");
                $(thisElement).addClass("lie-no-select");
                //单元号取消选择
                noselectlie(lie);
                removeCartFuntion(everyRemove);
                cancelWlc();
            }
        },50);
    });
});
function initCartIcon(resource){
    if ($("#code").val() == 'accept') {
        var timestamp1 = (new Date()).valueOf();
        hsRes.checkBoxHide = true;
        ychsRes.checkBoxHide = true;
        // 初始化 单元号后面的 购物车图标
        var dyhElems = resource.tabDocument.find(".layui-table-header thead th");
        if(dyhElems.length > 0){
            dyhElems.each(function () {
                var dyh = $(this).find("span").html();
                if(dyh != '物理层数' && dyh != '定义层数' && dyh){
                    $(this).find("span")[0].innerHTML=dyh+"<a href='javascript:void(0);' class='lie-no-select lie-icon "+dyh+"'></a>";
                }
            })
        }
        // 初始化 物理层数后面的 购物车图标
        var wlcsElems = resource.tabDocument.find("tbody td[data-field='wlcs']");
        if(wlcsElems.length >0){
            wlcsElems.each(function () {
                var wlcs = $(this).find("div").html();
                if(wlcs){
                    $(this).find("div")[0].innerHTML=wlcs+"<a href='javascript:void(0);' class='hang-no-select hang-icon "+wlcs+"'></a>";
                }
            })
        }
        // 初始化已选户室
        initChoosedHs();
    }
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param 初始化已选户室
 * @return
 * @description 
 */
function initChoosedHs(){
    var timestamp1 = (new Date()).valueOf();
    $.ajax({
        url: "../lpb/cart/lisths",
        dataType: "json",
        data: {
            paramJson: param
        },
        async: false,
        success: function (data) {
            var timestamp2 = (new Date()).valueOf();
            console.log('ajax lisths '+ (timestamp2-timestamp1));
            timestamp1 = (new Date()).valueOf();
            if(data && data.length > 0){
                for(var i=0 ; i < data.length ; i++){
                    var index = data[i];
                    var aElem = getAElemByFwHsIndex(index,'off');
                    if(aElem){
                        onSelect(aElem);
                    }
                }
            }
            timestamp2 = (new Date()).valueOf();
            console.log('循环 onSelect '+ (timestamp2-timestamp1));
        },
        error: function(e){
        }
    });
}

// 选择 整个TR中的所有的TD
function selectTrAllTd(trElement){
    var tdList = $(trElement).find("td");
    // var y=parseFloat(x)+1;
    tdList.each(function () {
        plSelect($(this));
    })
}

function noselectTrAllTd(trElement){
    var tdList = $(trElement).find("td");
    //全部清除
    tdList.each(function () {
        plCancelSelect($(this));
    });
    var hadslectedlie=[];
    //找到已经选择的单元号
    currentRes.tabDocument.find(".layui-table-header thead th a.lie-icon").parent().each(function () {
        if($(this).find("a.lie-icon").hasClass("lie-onselected")){
            hadslectedlie.push($(this).text());
        }
    });
    //再次添加
    if(hadslectedlie.length>0){
        for(var i=0;i<hadslectedlie.length;i++){
            var obj=hadslectedlie[i];
            tdList.each(function () {
                if($(this).find("p.bdc-fh span").attr("data-dyh")==obj){
                    plSelect($(this));
                }
            });
        }
    }
}

//整列选中 x为第几列
function selectalllie(x) {
    currentRes.tabDocument.find(".layui-table-main tbody tr td").each(function () {
        if($(this).find("p.bdc-fh span").attr("data-dyh")==x){
            plSelect($(this));
        }
    })
}

//单元号取消选择
function noselectlie(x) {
    //全部清除
    currentRes.tabDocument.find(".layui-table-main tbody tr td").each(function () {
        if($(this).find("p.bdc-fh span").attr("data-dyh")==x){
            plCancelSelect($(this));
        }
    });
    var hadslectedhang=[];
    //找到已经选择的单元号
    currentRes.tabDocument.find(".layui-table-fixed .layui-table-body tbody tr a.hang-icon").parent().each(function () {
        if($(this).find("a.hang-icon").hasClass("hang-onselected")){
            hadslectedhang.push($(this).text());
        }
    });
    //再次添加
    if(hadslectedhang.length>0){
        for(var i=0;i<hadslectedhang.length;i++){
            var obj=hadslectedhang[i];
            currentRes.tabDocument.find(".layui-table-main tbody tr td").each(function () {
                if($(this).find("p.bdc-fh span").attr("data-wlcs")==obj){
                    plSelect($(this));
                }
            });
        }
    }
}

function plSelect(element){
    if(element.find("a.one-icon").length > 0){
        for(var i = 0;i < element.find("a.one-icon").length ; i++){
            onSelect(element.find("a.one-icon")[i]);
        }
    }
}

function plCancelSelect(element){
    if(element.find("a.one-icon").length > 0){
        for(var i = 0;i < element.find("a.one-icon").length ; i++){
            cancelSelect(element.find("a.one-icon")[i]);
        }
    }
}
var selWlcArr =[];
var selDyhArr = [];
function onSelect(elem){
    setSelectedToAElement(elem);
    //选中
    var index = $(elem).attr("hsindex");
    if(index && currentRes.checkedList.indexOf(index) < 0){
        currentRes.checkedList.push(index);
        everyAdd.push(index);
        if(everyRemove.indexOf(index) >= 0){
            everyRemove = $.grep(everyRemove, function (value) {
                return value != index;
            });
        }
    }
}

function selWlc(){
    if(selWlcArr){
        selWlcArr = unique(selWlcArr);
        for(var i = 0 ; i < selWlcArr.length ; i++){
            var mainTrElement = currentRes.tabDocument.find(".layui-table-main tr[wlcs='"+selWlcArr[i]+"']");
            var iconNum = mainTrElement.find(".one-icon").length;
            var selNum = mainTrElement.find(".one-onselected").length;
            // 全部为选中状态
            if(iconNum == selNum){
                var fixTrElement = currentRes.tabDocument.find(".layui-table-fixed tr[wlcs='"+selWlcArr[i]+"']");
                var hangIcon = $($(fixTrElement).find("td")[0]).find(".hang-icon");
                hangIcon.addClass("hang-onselected");
                hangIcon.removeClass("hang-no-select");
            }
        }
    }
}

function selDyh(){
    if(selDyhArr){
        selDyhArr = unique(selDyhArr);
        for(var i = 0 ; i < selDyhArr.length ; i++){
            if(selDyhArr[i].length > 0){
                var iconNum = currentRes.tabDocument.find(".one-icon[dyh='"+selDyhArr[i]+"']").length;
                var selNum = currentRes.tabDocument.find(".one-onselected[dyh='"+selDyhArr[i]+"']").length;
                if(selNum == iconNum){
                    var dyhElem =  currentRes.tabDocument.find(".lie-onselected." + selDyhArr[i]);
                    if(dyhElem){
                        dyhElem.addClass("lie-onselected");
                        dyhElem.removeClass("lie-no-select");
                    }
                }
            }
        }
    }
}

function selectLieAndC(elem){
    var trIndex = $(elem).parents('tr').index() + 1;
    var tdlength = $(elem).parents('tr').find('td .one-icon').length;
    var tdSelectLength = $(elem).parents('tr').find('.one-onselected').length;
    if(tdlength == tdSelectLength && tdlength > 0){
        //选中物理层数
        currentRes.tabDocument.find('.layui-table-fixed .layui-table-body tr:nth-child('+ trIndex +') .hang-icon').addClass("hang-onselected").removeClass("hang-no-select");
        $(elem).parents('tr').find(' td:first-child .hang-icon').addClass("hang-onselected").removeClass("hang-no-select");
    }
}

var cancelWlcArr =[];
var cancelDyhArr = [];
function cancelSelect(elem){
    removeSelectedToAElement(elem);
    // 取消选中
    var index = $(elem).attr('hsindex');
    currentRes.checkedList = $.grep(currentRes.checkedList, function (value) {
        return value != index;
    });
    everyRemove.push(index);
    if(everyAdd.indexOf(index)>=0){
        everyAdd = $.grep(everyAdd, function (value) {
            return value != index;
        });
    }
}

function cancelWlc(){
    if(cancelWlcArr){
        cancelWlcArr = unique(cancelWlcArr);
        for(var i = 0 ; i < cancelWlcArr.length ; i++){
            var fixTrElement = currentRes.tabDocument.find(".layui-table-fixed tr[wlcs='"+cancelWlcArr[i]+"']");
            var hangIcon = $($(fixTrElement).find("td")[0]).find(".hang-icon");
            hangIcon.removeClass("hang-onselected");
            hangIcon.addClass("hang-no-select");
        }
    }
}

function cancelDyh(){
    if(cancelDyhArr){
        cancelDyhArr = unique(cancelDyhArr);
        for(var i = 0 ; i < cancelDyhArr.length ; i++){
            if(cancelDyhArr[i].length > 0){
                var dyhElem =  currentRes.tabDocument.find(".lie-onselected." + cancelDyhArr[i]);
                if(dyhElem){
                    dyhElem.removeClass("lie-onselected");
                    dyhElem.addClass("lie-no-select");
                }
            }
        }
    }
}

/**
 * 移除购物车
 * @param removeList
 */
function removeCartFuntion(removeList){
    var fwHsIndexs = '';
    // eval中有用到 勿删
    $.each(removeList, function (index, v) {
        fwHsIndexs += v + ",";
    });
    if(fwHsIndexs){
        fwHsIndexs = fwHsIndexs.substr(0,fwHsIndexs.length-1);
    }
    // console.info("移除："+fwHsIndexs);
    // removeModal();
    // return;
    $.ajax({
        url: "../lpb/cart/remove",
        type:"post",
        // async: false,
        data: {
            paramJson : param,
            fwHsIndexs: fwHsIndexs
        },
        success: function () {
            removeModal();
            cartActionMsg("移除成功");
        },
        error: function (e) {
            delAjaxErrorMsg(e,this);
        }
    });
}

function addAllCartWithSelectedClass(){
    clearGlob();
    var timestamp1 = (new Date()).valueOf();
    var total = false;
    // 针对筛选结果做全选，不使用传递fw_dcb_index的方式
    var nonFilterElements = currentRes.tabDocument.find(".bdc-td-bg").length;
    if(currentRes.checkedList == 0 && nonFilterElements == 0){
        total = true;
    }
    currentRes.tabDocument.find("a.one-no-select").each(function(){
        onSelect(this);
    });
    var timestamp2 = (new Date()).valueOf();
    console.log('点亮所有A标签 '+ (timestamp2-timestamp1));
    timestamp1 = (new Date()).valueOf();
    if(nonFilterElements == 0){
        currentRes.tabDocument.find("a.hang-no-select").each(function(){
            $(this).removeClass("hang-no-select");
            $(this).addClass("hang-onselected");
        });
        currentRes.tabDocument.find("a.lie-no-select").each(function(){
            $(this).removeClass("lie-no-select");
            $(this).addClass("lie-onselected");
        });
    }
    var timestamp2 = (new Date()).valueOf();
    console.log('hang lie：'+ (timestamp2-timestamp1));
    timestamp1 = (new Date()).valueOf();
    if(total){
        addAllCartFunction();
    }else{
        addCartFunction(everyAdd);
    }
    timestamp2 = (new Date()).valueOf();
    console.log('添加完购物车：'+ (timestamp2-timestamp1));
    timestamp1 = (new Date()).valueOf();
    selWlc();
    selDyh();
    timestamp2 = (new Date()).valueOf();
    console.log('处理完行列图标：'+ (timestamp2-timestamp1));
}
function addAllCartFunction(){
    var fwDcbIndex = $("#fwDcbIndex").val();
    // console.info("全选");
    var timestamp1 = (new Date()).valueOf();
    $.ajax({
        url: "../lpb/cart/alladd",
        traditional: true,
        type:"post",
        async: false,
        data: {
            hslx:currentRes.tabname,
            paramJson : param,
            fwDcbIndex: fwDcbIndex
        },
        success: function (result) {
            var timestamp2 = (new Date()).valueOf();
            console.log('ajax alladd：'+ (timestamp2-timestamp1));
            timestamp1 = (new Date()).valueOf();
            // 如果存在验证不通过的BDCDYH
            if(result.length >0){
                // 从已选列表中去掉验证不通过的BDCDYH
                removeCheckedList(result);
                timestamp2 = (new Date()).valueOf();
                console.log('规则验证不通过 移除选中：'+ (timestamp2-timestamp1));
                timestamp1 = (new Date()).valueOf();
                // 验证不通过的提示
                yzTip(result);
                var timestamp2 = (new Date()).valueOf();
                console.log('规则验证不通过 给出提示：'+ (timestamp2-timestamp1));
                timestamp1 = (new Date()).valueOf();
            } else {
                cartActionMsg("添加成功");
            }
            removeModal();
        },
        error: function (e) {
            delAjaxErrorMsg(e,this);
        }
    });
}
/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param
 * @return
 * @description 根据户室主键 获取 户室属性
 */
function getDataInfoByIndex(fwHsIndex){
    var inputElement = $("input[data-index='"+fwHsIndex+"']");
    var info = {fwHsIndex:fwHsIndex
        ,bdcdyh:inputElement.data("bdcdyh")
        ,ghyt:inputElement.data("ghyt")
        ,zl:inputElement.data("zl")};
    return info;
}

function addCartWithSelect(checkedList,noGzyz,bdcWlSlXmLsgxDO){
    addCartFunction(checkedList,noGzyz,bdcWlSlXmLsgxDO);
    for(var i = 0 ; i < currentRes.checkedList.length ;i++){
        var fwHsIndex = currentRes.checkedList[i];
        var aElem = getAElemByFwHsIndex(fwHsIndex,'off');
        setSelectedToAElement(aElem);
    }
    selWlc();
    selDyh();
}

/**
 * 添加购物车
 * @param checkedList
 */
function addCartFunction(checkedList,noGzyz,bdcWlSlXmLsgxDO){
    var data = [];
    var timestamp1 = (new Date()).valueOf();
    checkedList = unique(checkedList);
    // eval中有用到 勿删
    $.each(checkedList, function (index, v) {
        var dataInfo = getDataInfoByIndex(v);
        if(dataInfo.bdcdyh){
            data.push(dataInfo);
        }
    });
    var timestamp2 = (new Date()).valueOf();
    console.log('循环checkedList '+ (timestamp2-timestamp1));
    timestamp1 = (new Date()).valueOf();
    // console.info(data);
    // removeModal();
    // return ;
    $.ajax({
        url: "../lpb/cart/add",
        traditional: true,
        type:"post",
        async: false,
        data: {
            hslx:currentRes.tabname,
            paramJson : param,
            fwHsDOListJson: JSON.stringify(data),
            noGzyz: noGzyz,
            tdzWlxmJson: JSON.stringify(bdcWlSlXmLsgxDO)
        },
        success: function (result) {
            // 如果存在验证不通过的BDCDYH
            if(result.length >0){
                // 从已选列表中去掉验证不通过的BDCDYH
                removeCheckedList(result);
                // 验证不通过的提示
                yzTip(result);
            } else {
                cartActionMsg("添加成功");
            }
            removeModal();
        },
        error: function (e) {
            delAjaxErrorMsg(e,this);
            removeModal();
        }
    });
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param
 * @return
 * @description 从已选列表中 去掉 验证没通过的BDCDYH
 */
function removeCheckedList(yzResultList){
    setTimeout(function(){
        for(var i = 0 ; i < yzResultList.length;i++){
            var yzResult = yzResultList[i];
            var fwHsIndex = yzResult.fwHsIndex;
            // 去掉选中状态
            cancelSelect(getAElemByFwHsIndex(fwHsIndex,'on'));
        }
        cancelWlc();
        cancelDyh();
    },0);
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param
 * @return
 * @description 验证不通过的提示
 */
function yzTip(yzResultList){
    if(parent.length > 0){
        parent.addGwc();
    }
    // 加载推送信息模板
    loadTsxx(yzResultList);
    // 弹窗提示
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery,
            layer = layui.layer;
        warnLayer = layer.open({
            type: 1,
            title: '提示信息',
            skin: 'bdc-tips-right',
            // closeBtn: 0, //不显示关闭按钮
            shade: [0],
            area: ['600px'],
            offset: 'r', //右下角弹出
            time: 5000000, //2秒后自动关闭
            anim: 2,
            content: $('#tsxx').html(),
            success: function () {
                $('.layui-icon.layui-icon-close').on('click', function () {
                    layer.close(warnLayer);
                    generate();
                })
            },
            cancel: function () {
                layer.close(warnLayer);
                generate();
            }
        });
    });
}

/**
 * 根据户室主键 获取购物车图标标签
 * @param fwHsIndex
 */
function getAElemByFwHsIndex(fwHsIndex,flag){
    var elem;
    if(indexElementIndexArr.indexOf(fwHsIndex) >= 0){
        for(var i = 0 ;i < indexElementArr.length ; i++){
            if(indexElementArr[i].fwHsIndex == fwHsIndex){
                elem = indexElementArr[i].aElem;
                break;
            }
        }
    } else {
        if(flag == 'on'){
            elem =  $(".one-onselected[hsindex='"+fwHsIndex+"']");
        }else if(flag == 'off'){
            elem = $(".one-no-select[hsindex='"+fwHsIndex+"']");
        }else{
            elem = $(".one-icon[hsindex='"+fwHsIndex+"']");
        }
        indexElementArr.push({fwHsIndex:fwHsIndex,aElem:elem});
        indexElementIndexArr.push(fwHsIndex);
    }
    return elem;
}

/**
 * 点亮购物车图标
 * @param aElem
 */
function setSelectedToAElement(aElem){
    $(aElem).removeClass("one-no-select");
    $(aElem).addClass("one-onselected");
    var wlcs = $(aElem).attr("wlcs");
    var dyh = $(aElem).attr("dyh");
    var index = $(aElem).attr("hsindex");
    selWlcArr.push(wlcs);
    if(dyh && dyh != 'undefined'){
        selDyhArr.push(dyh);
    }
    if(indexElementIndexArr.indexOf(index) < 0){
        indexElementArr.push({fwHsIndex:index,aElem:aElem});
        indexElementIndexArr.push(index);
    }
}

function removeSelectedToAElement(aElem){
    $(aElem).addClass("one-no-select");
    $(aElem).removeClass("one-onselected");
    // 取消当前单元 和 列的选中状态样式
    var wlcs = $(aElem).attr("wlcs");
    var dyh = $(aElem).attr("dyh");
    cancelWlcArr.push(wlcs);
    if(dyh && dyh != 'undefined'){
        cancelDyhArr.push(dyh);
    }
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param
 * @return
 * @description 购物车操作 提示
 */
function cartActionMsg(defaultMsg){
    if(globalMsg != constant_nomsg){
        layer.msg(globalMsg?globalMsg:defaultMsg);
        // console.info("测试");
        // 获取父页面 方法（刷新购物车）
        if(parent.length > 0){
            parent.addGwc();
        }
    }
    globalMsg = "";
}

/**
 * 为外围提供，刷新购物车按钮
 */
function refreshCartIcon(){
    currentRes.checkedList=[];
    currentRes.tabDocument.find("a.one-onselected").each(function(){
        $(this).removeClass("one-onselected");
        $(this).addClass("one-no-select");
    });
    currentRes.tabDocument.find("a.hang-onselected").each(function(){
        $(this).removeClass("hang-onselected");
        $(this).addClass("hang-no-select");
    });
    currentRes.tabDocument.find("a.lie-onselected").each(function(){
        $(this).removeClass("lie-onselected");
        $(this).addClass("lie-no-select");
    });
    initChoosedHs();
}

function clearGlob(){
    everyAdd = [];
    everyRemove = [];
    cancelDyhArr = [];
    cancelWlcArr = [];
    selDyhArr = [];
    selWlcArr = [];
}