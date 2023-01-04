
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
        addModel();
        var elem = this;
        setTimeout(function() {

            // if (version != 'standard' && version != 'bengbu') {
                if ($(elem).hasClass("one-no-select")) {
                    // 标准版、蚌埠版本点击不添加进购物车
                    if (version != 'bengbu') {
                        addCartWithElem(elem);
                    } else {
                        var index = $(elem).attr("hsindex");
                        if (index && currentRes.checkedList.indexOf(index) < 0) {
                            currentRes.checkedList.push(index);
                        }
                    }
                } else {
                    var remove = [];
                    remove.push($(elem).attr("hsindex"));
                    // 标准版、蚌埠版本点击不添加进购物车
                    if (version != 'bengbu') {
                        removeCartFuntion(remove);
                    } else {
                        $.each(remove, function (index, v) {
                            removeCheckedWlcAndLie(v);
                        });
                    }
                }
            // }
            selWlc();
            selDyh();
            resScrollData(currentRes);
        },50);
    });
    //物理层确认点击
    $("body").on("click","a.hang-icon",function () {
        var elem = this;
        addModel();
        setTimeout(function() {
            //获取当前物理层(锁定)
            var wlcs = $(elem).attr("wlcs");

                // 获取所有wlcs为的当前选中层的数据
                if ($(elem).hasClass("hang-no-select")) {
                    var wlcDatas = getToCartDatas(currentRes,currentRes.data.allHsCells,true,true,wlcs);
                    // 标准版、蚌埠版本点击不添加进购物车
                    if(version != 'bengbu'){
                        //整行选中
                        addCartFunction(wlcDatas);
                    }

                } else {
                    var wlcDatas = getToCartDatas(currentRes,currentRes.data.allHsCells,false,false,wlcs);
                    var indexArr = [];
                    for(var i = 0 ;i < wlcDatas.length;i++){
                        indexArr.push(wlcDatas[i].fwHsIndex);
                    }
                    // 标准版、蚌埠版本点击不添加进购物车
                    if( version != 'bengbu'){
                        removeCartFuntion(indexArr);
                    }else {
                        $.each(indexArr, function (index, v) {
                            removeCheckedWlcAndLie(v);
                        });
                    }
                }
            selWlc();
            selDyh();
            resScrollData(currentRes);
        },50);
    });
    //单元号确认点击
    $("body").on("click","a.lie-icon",function () {
        addModel();
        var elem = this;
        setTimeout(function() {
            clearGlob();
            // 获取当前单元号
            var dyh = $(elem).attr("dyh");
            if($(elem).hasClass("lie-no-select")){
                var dyhDatas = getToCartDatas(currentRes,currentRes.data.allHsCells,true,true,null,dyh);
                //整行选中
                addCartFunction(dyhDatas);
            }else{
                var dyhDatas = getToCartDatas(currentRes,currentRes.data.allHsCells,false,false,null,dyh)
                var indexArr = [];
                for(var i = 0 ;i < dyhDatas.length;i++){
                    indexArr.push(dyhDatas[i].fwHsIndex);
                }
                removeCartFuntion(indexArr);
            }
            selDyh();
            resScrollData(currentRes);
        },50);
    });
});

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param 初始化已选户室
 * @return
 * @description 
 */
function initChoosedHs(){
    $.ajax({
        url: "../lpb/cart/lisths",
        dataType: "json",
        data: {
            paramJson: param
        },
        async: false,
        success: function (data) {
            if(data && data.length > 0){
                for(var i=0 ; i < data.length ; i++){
                    var index = data[i];
                    currentRes.checkedList.push(index);
                }
            }
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
        if($(this).find("p.bdc-fh").attr("dyh")==x){
            plSelect($(this));
        }
    })
}

//单元号取消选择
function noselectlie(x) {
    //全部清除
    currentRes.tabDocument.find(".layui-table-main tbody tr td").each(function () {
        if($(this).find("p.bdc-fh").attr("dyh")==x){
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
        wlcFor: for(var i = 0 ; i < selWlcArr.length ; i++){
            // 循环物理层里的数据 判断是否都在checkedList里面
            cFor: for(var j = 0 ;j < currentRes.data.cHsCells.length ;j++){
                var cHs = currentRes.data.cHsCells[j];
                if(cHs.wlcs == selWlcArr[i]){
                    hsFor: for(var k= 0 ; k < cHs.cells.length ; k++){
                        if(currentRes.checkedList.indexOf(cHs.cells[k].info.fwHsIndex.value) < 0){
                            break cFor;
                        }
                    }
                    currentRes.checkedWlc.push(cHs.wlcs);
                }
            }
        }
    }
}

function selDyh(){
    if(selDyhArr){
        selDyhArr = unique(selDyhArr);
        dyhFor: for(var i = 0 ; i < selDyhArr.length ; i++){
            dyFor: for(var j = 0 ;j < currentRes.data.dyHsCells.length ;j++){
                var dyHs = currentRes.data.dyHsCells[j];
                if(dyHs.dyh == selDyhArr[i]){
                    hsFor: for(var k= 0 ; k < dyHs.cells.length ; k++){
                        if(currentRes.checkedList.indexOf(dyHs.cells[k].info.fwHsIndex.value) < 0){
                            break dyFor;
                        }
                    }
                    currentRes.checkedDyh.push(dyHs.dyh);
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
        checkWlc(true,currentRes.tabDocument.find('.layui-table-fixed .layui-table-body tr:nth-child('+ trIndex +') .hang-icon'));
        checkWlc(currentRes,true,$(elem).parents('tr').find(' td:first-child .hang-icon'));
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
            checkWlc(currentRes,false,hangIcon);
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
                    checkDyh(currentRes,false,dyhElem);
                }
            }
        }
    }
}


function removeCheckedWlcAndLie(fwHsIndex,addCart){
    if (addCart === undefined) {
        addCart = true;
    }
    if(addCart) {
        // 从选中中移除
        currentRes.checkedList = $.grep(currentRes.checkedList, function (value) {
            return value != fwHsIndex;
        });
    }
    currentRes.checkboxcheckedList = $.grep(currentRes.checkboxcheckedList, function (value) {
        return value != fwHsIndex;
    });
    for(var i = 0 ;i < currentRes.data.allHsCells.length;i++){
        // 每个户室数据
        var temp = currentRes.data.allHsCells[i];
        if(temp.info.fwHsIndex.value == fwHsIndex){
            var wlcs = temp.info.wlcs?temp.info.wlcs.value:"";
            currentRes.checkedWlc = $.grep(currentRes.checkedWlc, function (value) {
                return value != wlcs;
            });
            var dyh = temp.info.dyh?temp.info.dyh.value:"";
            currentRes.checkedDyh = $.grep(currentRes.checkedDyh, function (value) {
                return value != dyh;
            });
        }
    }
}

/**
 * 移除购物车
 * @param removeList
 */
function removeCartFuntion(removeList){
    var fwHsIndexs = '';
    $.each(removeList, function (index, v) {
        removeCheckedWlcAndLie(v);
        fwHsIndexs += v + ",";
    });
    if(fwHsIndexs){
        fwHsIndexs = fwHsIndexs.substr(0,fwHsIndexs.length-1);
    }
    $.ajax({
        url: "../lpb/cart/remove",
        type:"post",
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

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param
 * @return
 * @description 全选按钮函数
 */
function addAllCartWithSelectedClass(){
    clearGlob();
    var total = false;
    // 针对筛选结果做全选，不使用传递fw_dcb_index的方式
    var searchSpan = currentRes.tabDocument.find(".bdc-search-content>span").length;
    if(currentRes.checkedList == 0 && searchSpan == 0){
        total = true;
    }
    if(total){
        getToCartDatas(currentRes,currentRes.data.allHsCells,true,true);
        addAllCartFunction();
    }else{
        // 全选获取 数据
        var datas = getToCartDatas(currentRes,currentRes.data.allHsCells,true,true);
        addCartFunction(datas);
    }
    selWlc();
    selDyh();
    resScrollData(currentRes);
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param resource 当前资源
 * @param cells 需要获取数据的 户室数据集合
 * @param toCheckedList 是否添加进checkedList true false
 * @param checkedFilter 是否过滤已选中的数据 true false
 * @param wlcsFilter 需要筛选的wlcs
 * @param dyhFilter 需要筛选的单元号
 * @param addCart 是否添加购物车
 * @return
 * @description
 */
function getToCartDatas(resource,cells,toCheckedList,
                        checkedFilter,wlcsFilter,dyhFilter,addCart){
    if (addCart === undefined) {
        addCart = true;
    }
    // 循环层
    var dataInfo = [];
    var ytFilter = [];
    var jyztFilter = [];
    var searchSpan = currentRes.tabDocument.find(".bdc-search-content>span");
    if (searchSpan.length > 0) {
        for (var i = 0; i < searchSpan.length; i++) {
            var title = $(searchSpan[i]).attr("title");
            var clasz = $(searchSpan[i]).attr("class");
            if (clasz == "bdc-search-yt") {
                ytFilter = ytFilter.concat(title.split(","));
            } else if (clasz == "bdc-search-jyzt") {
                jyztFilter = jyztFilter.concat(title.split(","));
            }
        }
    }
    for(var i = 0 ;i < cells.length;i++){
        // 每个户室数据
        var temp = cells[i].info;
        var info = {
            fwHsIndex:temp.fwHsIndex.value
            ,bdcdyh:temp.bdcdyh.value
            ,ghyt:temp.ghyt.dm
            ,zl:temp.zl.value
            ,dyh:temp.dyh.value
            ,wlcs:temp.wlcs.value
            , qlr: temp.qlr.value
            ,qjgldm:qjgldm
        }
        var jyzt = "temp";
        if (temp.jyztHtml.split("title=\"").length > 1) {
            jyzt = temp.jyztHtml.split("title=\"")[1].split("\"")[0];
        }
        // 用途过滤
        if ((ytFilter.length == 0 || ytFilter.indexOf(temp.ghyt.value) >= 0) && (jyztFilter.length == 0 || jyztFilter.indexOf(jyzt) >= 0)) {
            // 已选过滤
            if(!checkedFilter || resource.checkedList.indexOf(info.fwHsIndex) < 0){
                // 物理层过滤
                if(!wlcsFilter || temp.wlcs.value == wlcsFilter){
                    // 单元号过滤
                    if(!dyhFilter || temp.dyh.value == dyhFilter){
                        dataInfo.push(info);
                        if(toCheckedList){
                            if(addCart) {
                                resource.checkedList.push(info.fwHsIndex);
                            }
                            resource.checkboxcheckedList.push(info.fwHsIndex);
                            // 处理dyh 和 wlcs
                            if(info.dyh && info.dyh != 'undefined'){
                                selDyhArr.push(info.dyh);
                            }
                            if(info.wlcs && info.wlcs != 'undefined'){
                                selWlcArr.push(info.wlcs);
                            }
                        }
                    }
                }
            }
        }
    }
    return dataInfo;
}

function addAllCartFunction(){
    var fwDcbIndex = $("#fwDcbIndex").val();
    $.ajax({
        url: "../lpb/cart/alladd",
        traditional: true,
        type:"post",
        async: false,
        data: {
            hslx: currentRes.tabname,
            paramJson: param,
            fwDcbIndex: fwDcbIndex,
            qjgldm: qjgldm,
            zlcsh: zlcsh,
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
        , qlr: inputElement.data("qlr")
        ,zl:inputElement.data("zl")
        ,qjgldm:qjgldm};
    return info;
}

function addCartWithFwhsIndexArr(fwHsIndexArr,noGzyz,bdcWlSlXmLsgxDO){
    var cells = [];
    for(var i = 0 ; i < currentRes.data.allHsCells.length;i++){
        var temp = currentRes.data.allHsCells[i];
        if(fwHsIndexArr.indexOf(temp.info.fwHsIndex.value) >= 0){
            cells.push(temp);
        }
    }
    var data = getToCartDatas(currentRes,cells,true,true);
    addCartFunction(data,noGzyz,bdcWlSlXmLsgxDO);
    selWlc();
    selDyh();
    resScrollData(currentRes);
}

function addCartWithElem(elem){
    var index = $(elem).attr("hsindex");
    if(index && currentRes.checkedList.indexOf(index) < 0){
        currentRes.checkedList.push(index);
    }
    var data = [];
    data.push(getDataInfoByIndex(index));
    addCartFunction(data);
}

/**
 * 添加购物车
 * @param checkedList
 */
function addCartFunction(datas,noGzyz,bdcWlSlXmLsgxDO){
    if (datas && datas.length > 0) {
        $.ajax({
            url: "../lpb/cart/add",
            traditional: true,
            type: "post",
            async: false,
            data: {
                hslx: currentRes.tabname,
                paramJson: param,
                fwHsDOListJson: JSON.stringify(datas),
                noGzyz: noGzyz,
                tdzWlxmJson: JSON.stringify(bdcWlSlXmLsgxDO),
                zlcsh: zlcsh
            },
            success: function (result) {
                // 如果存在验证不通过的BDCDYH
                if (result.length > 0) {
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
                delAjaxErrorMsg(e, this);
                removeModal();
            }
        });
    } else {
        cartActionMsg("无可添加数据");
    }
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param
 * @return
 * @description 从已选列表中 去掉 验证没通过的BDCDYH
 */
function removeCheckedList(yzResultList){
    for(var i = 0 ; i < yzResultList.length;i++){
        var yzResult = yzResultList[i];
        var fwHsIndex = yzResult.fwHsIndex;
        removeCheckedWlcAndLie(fwHsIndex);
    }
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param
 * @return
 * @description 验证不通过的提示
 */
function yzTip(yzResultList){
    if(parent.length > 0){
        parent.addGwc('', true);
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
        checkWlc(currentRes,false,$(this))
    });
    currentRes.tabDocument.find("a.lie-onselected").each(function(){
        checkDyh(currentRes,false,$(this));
    });
    initChoosedHs();
}

function checkWlc(resource,checked,elem){
    if(checked){
        resource.checkedWlc.push(elem.attr("wlcs"));
        elem.addClass("hang-onselected");
        elem.removeClass("hang-no-select");
    }else{
        resource.checkedWlc = $.grep(resource.checkedWlc, function (value) {
            return value != elem.attr("wlcs");
        });
        elem.removeClass("hang-onselected");
        elem.addClass("hang-no-select");
    }
}

function checkDyh(resource,checked,elem){
    if(checked){
        resource.checkedDyh.push(elem.attr("dyh"));
        elem.addClass("lie-onselected");
        elem.removeClass("lie-no-select");
    }else{
        resource.checkedDyh = $.grep(resource.checkedDyh, function (value) {
            return value != elem.attr("dyh");
        });
        elem.removeClass("lie-onselected");
        elem.addClass("lie-no-select");
    }
}

function clearGlob(){
    everyAdd = [];
    everyRemove = [];
    cancelDyhArr = [];
    cancelWlcArr = [];
    selDyhArr = [];
    selWlcArr = [];
}