// 全局变量
var globalData = {};
var resourceData = {};
var zdList = {};
var param = $("#param").val();
var resUrl = "../lpb/lpbres";
var hsUrl = "";
var sortedYhsList = [];
var dylxArr = ["fwqszm"];
var sessionKey = "fwqszm";
$(function () {
    $.ajax({
        url: "../zd/mul",
        dataType: "json",
        data: {
            zdDoNames: "SZdMjdwDO,SZdQsxzDO,SZdTdsyqlxDO,SZdGmjjhydmDO," +
            "SZdDldmDO,SZdQlsdfsDO,SZdTddjDO,SZdFwjgDO,SDmDwxxDO,SZdFwytDO,SZdQtgsDO,SZdJzwztDO,SZdBdcdyFwlxDO"
        },
        async: false,
        success: function (data) {
            zdList = $.extend({}, data)
        }
    });

    //鼠标悬浮框定位展示
    $(document).on('mouseenter', 'td', function () {
        var $showTip = $(this).find('.bdc-title-tips');
        if($showTip.length > 0){
            var tablewidth =  $('.bdc-right-content').width();
            var left = $(this).offset().left + 100;
            var top = $(this).offset().top - 180;

            if(left + 280 < tablewidth){
                $showTip.css({top:top +'px', 'margin-left':left+'px'});
            }else {
                $showTip.css({top:top +'px', 'margin-left':left - 280 - 110 +'px'});
            }

            $showTip.removeClass('bdc-hide');
        }
    });
    $(document).on('mouseleave', 'td', function () {
        window.parent.document.execCommand('RespectVisibilityInDesign', false, "true");
        // window.document.execCommand('RespectVisibilityInDesign', false, "true");
        if ($(this).find('.bdc-title-tips').length > 0) {
            $(this).find('.bdc-title-tips').addClass('bdc-hide');
        }
    });
});

var tabResource = function(resname){
    var obj = new Object();
    obj.filterList = {};
    obj.tabname = resname;
    obj.tabId = resname + "Tab";
    obj.tabTitleId = resname + "TabLi";
    obj.tabDocument = $("#" + obj.tabId);
    obj.tableId = resname + "TableView";
    obj.tableResizeId = resname + "Id";
    obj.stateIndex = 0;
    obj.checkedList = [];
    obj.checkboxcheckedList = [];
    obj.checkedShowColumn = {};
    obj.tableFilter = resname + "TableLayFilter";
    obj.toorBarId = resname + "Toolbar";
    obj.hasSelectBtn = false;
    obj.checkBoxHide = false;
    obj.dataUrl = "";
    obj.yScrollId = resname + "YScroll"; // 竖向滚动条
    obj.xScrollId = resname + "XScroll"; // 横向滚动条
    obj.yScroll = new Scrollbar();
    obj.xScroll = new Scrollbar();
    obj.yPos=0; // X轴滚动位置
    obj.xPos=0; // y轴滚动位置
    obj.data={};// 全部资源数据
    obj.showColumn=[];
    obj.checkedDyh=[];
    obj.checkedWlc=[];
    obj.checkHasTab = function (tabType){
        return tabType && (tabType.indexOf(obj.tabname) == 0 || tabType.indexOf(',' + obj.tabname) > 0);
    };
    obj.showThisTab = function(){
        $("#"+obj.tabId).addClass("layui-show");
        $("#"+obj.tabTitleId).addClass("layui-this");
    };
    obj.initScrollBar = function () {
        var _this = this;
        // 生成 Y 轴 滚动条
        var height = _this.tabDocument.find(".layui-table-box").height() - _this.tabDocument.find(".layui-table-header").height();
        var itemSize = _this.data.cellHeight;
        var pagesize = parseInt(height / itemSize)+1;
        var size = pagesize * itemSize;
        _this.yScroll.setOptions({
            total: _this.data.cFwList.length,
            length: height,
            size: height,
            itemSize: itemSize
        });

        if(_this.tabDocument.find("#"+_this.yScrollId).find("div").length ==0 ){
            // 不存在 新建
            _this.yScroll.CreateAt(_this.yScrollId);
        }
        _this.yScroll.windowAddMouseWheel(_this,_this.yScrollId);

        _this.yScroll.onScroll = function (pos) {
            scrollShowData(_this, pos, _this.xScroll.getPos(), resname);
        };

        // 生成 X 轴滚动条
        var width = _this.tabDocument.find(".layui-table-main").width();
        // wlc dyc 列宽为 90px 单元格 列宽110px
        var dataSize = width - 180; // 减去wlc列和dyc列
        _this.xScroll.setOptions({
            total: _this.data.maxHs,
            position: 'X',
            length: dataSize,
            size: dataSize,
            itemSize: 110
        });
        if(_this.tabDocument.find("#"+_this.xScrollId).find("div").length ==0 ){
            // 不存在 新建
            _this.xScroll.CreateAt(this.xScrollId);
        }
        _this.xScroll.onScroll = function (pos) {
            scrollShowData(_this, _this.yScroll.getPos(), pos, resname);
        }
        scrollShowData(_this, _this.yPos, _this.xPos, resname);
    };
    return obj;
};
/**
 * 户室资源
 * @returns {tabResource}
 */
var hsResObj = function(){
    var obj = new tabResource("hs");
    obj.delete = function (data){
        deletefwhs(data);
    };
    obj.add = function(fwDcbIndex){
        addFwhs(fwDcbIndex);
    };
    obj.update = function (data) {
        fwhsUpdate(data);
    };
    obj.loadTableData = function(){
        addModel();
        var fwDcbIndex = $("#fwDcbIndex").val();
        var code = $("#code").val();
        layui.use(['laytpl', 'form'], function () {
            $.ajax({
                url: hsRes.dataUrl,
                dataType: "json",
                data: {
                    fwDcbIndex: fwDcbIndex,
                    code: code,
                    gzlslid:gzlslid,
                    paramJson: param,
                    qjgldm:qjgldm
                },
                success: function (data) {
                    $("#fwDcbIndex").val(fwDcbIndex);
                    // 如果是 购物车类型页面 初始化已选户室
                    initChoosedHs();
                    tplTable(data,hsRes);
                    removeModal();
                    hsRes.filterList = data.filterList;
                },
                // added by cyc at 2019-08-13
                error:function(data){
                    removeModal();
                    var errorMsg = "查不到幢信息";
                    if(data.responseText){
                        var errorJson = $.parseJSON(data.responseText);
                        errorMsg = errorJson.msg;
                    }
                    var noData = '<span style="position:absolute;top: 200%;left: 50%;transform: translateX(-50%);"><img src="../static/lib/registerui/image/table-none.png"' +
                        ' alt="">'+errorMsg+'</span>';
                    hsRes.tabDocument.find(".layui-show").append(noData)
                }
            });
        });
        // 清空所有选框
        hsRes.checkedList = [];
        hsRes.yxcheckedList = [];
    };
    return obj;
};
var hsRes = new hsResObj();

/**
 * 预测户室资源
 * @returns {tabResource}
 */
var ychsResObj = function(){
    var obj = new tabResource("ychs");
    obj.delete = function (data){
        deletefwychs(data);
    };
    obj.add = function(fwDcbIndex){
        addYcfwhs(fwDcbIndex);
    };
    obj.update = function (data) {
        fwychsUpdate(data);
    };
    obj.loadTableData = function(){
        // 加载主表格
        addModel();
        var fwDcbIndex = $("#fwDcbIndex").val();
        var code = $("#code").val();
        $.ajax({
            url: ychsRes.dataUrl,
            dataType: "json",
            data: {
                fwDcbIndex: fwDcbIndex,
                code: code,
                gzlslid:gzlslid,
                paramJson: param,
                qjgldm:qjgldm
            },
            success: function (data) {
                initYchsBtns();
                initZsxx(ychsRes);
                if(ychsRes.tabDocument.find(".bdc-state-show").find("p").length == 0){
                    initState(ychsRes);
                }
                initShowColumn(ychsRes);
                // 加载主表格
                tplTable(data,ychsRes);

                $("#ycfwDcbIndex").val(fwDcbIndex);
                removeModal();
                ychsRes.filterList = data.filterList;
            },
            error:function (data) {
                removeModal();
                var errorMsg = "查不到幢信息";
                if(data.responseText){
                    var errorJson = $.parseJSON(data.responseText);
                    errorMsg = errorJson.msg;
                }
                var noData = '<span style="position:absolute;top: 200%;left: 50%;transform: translateX(-50%);"><img src="../static/lib/registerui/image/table-none.png"' +
                    ' alt="">'+errorMsg+'</span>';
                ychsRes.tabDocument.find(".layui-show").append(noData)
            }
        });
        // 清空所有选框
        ychsRes.checkedList = [];
        ychsRes.yxcheckedList = [];
    };
    return obj;
};
var ychsRes = new ychsResObj();

/**
 * 默认当前资源为户室资源
 * @type {hsResObj}
 */
var currentRes = hsRes;
function dealWithData(data){
    // 循环层  处理每一层所有data
    var allHsCells = [];
    var cHsCells = [];
    var dyHsCells = [];
    for(var i = 0; i < data.cFwList.length;i++){
        var cellElem = [];
        cHsCells.push({wlcs:data.cFwList[i].wlcs,cells:[]});
        // 循环单元列表
        for(var j = 0 ; j < data.cFwList[i].dyFwList.length ; j++){
            // 循环单元中的每户列表 如果不符合最大户室 则 置空
            var dyMaxHs = data.cFwList[i].dyFwList[j].maxHs;
            for(var k = 0 ; k< dyMaxHs; k ++){
                var temp = data.cFwList[i].dyFwList[j].fwhsResourceDTOList[k];
                if(!temp){
                    data.cFwList[i].dyFwList[j].fwhsResourceDTOList[k] = {};
                }else if(temp.info && temp.info.fwHsIndex){
                    allHsCells.push(temp);
                    cHsCells[i].cells.push(temp);
                    var dyh = temp.info.dyh?temp.info.dyh.value:"";
                    var initDyAlready = false;
                    for(var m = 0 ; m < dyHsCells.length ; m++){
                        if(dyHsCells[m].dyh == dyh){
                            initDyAlready = true;
                            dyHsCells[m].cells.push(temp);
                        }
                    }
                    if(!initDyAlready){
                        dyHsCells.push({dyh:dyh,cells:[temp]})
                    }
                }
            }
            cellElem = cellElem.concat(data.cFwList[i].dyFwList[j].fwhsResourceDTOList);
        }
        data.cFwList[i].cellElem = cellElem;
    }
    data.allHsCells = allHsCells;
    data.cHsCells = cHsCells;
    data.dyHsCells = dyHsCells;
}

function tplTable(data,resource){
    if(data && data.cFwList) {
        data.cFwList.forEach(function (v) {
            if(v.dyFwList.length != data.dyList.length){
                data.dyList.forEach(function (value,index) {
                    if(!v.dyFwList[index]){
                        v.dyFwList.splice(index, 0, {
                            "dyh": value.dyh,
                            "maxHs": value.colspan,
                            "fwhsResourceDTOList": [
                            ]});
                    }else {
                        if(value.dyh != v.dyFwList[index].dyh){
                            v.dyFwList.splice(index, 0, {
                                "dyh": value.dyh,
                                "maxHs": value.colspan,
                                "fwhsResourceDTOList": [
                                ]});
                        }
                    }
                })
            }
        });

        var hasUp = false;
        data.cFwList.forEach(function (value,index) {
            var thisCMaxHs = 0;
            value.dyFwList.forEach(function (val,ind) {
                for(var n = 0; n < val.maxHs; n++){
                    if(!$.isEmptyObject(val.fwhsResourceDTOList[n])){
                        if(val.fwhsResourceDTOList[n].info && val.fwhsResourceDTOList[n].info.hbhs
                            && val.fwhsResourceDTOList[n].info.hbhs.value){
                            switch(val.fwhsResourceDTOList[n].info.hbfx.value)
                            {
                                case '1':case '2':
                                    for(var v = 1; v <= val.fwhsResourceDTOList[n].info.hbhs.value; v++){
                                        val.fwhsResourceDTOList.splice(n+v,0,{});
                                    }
                                    break;
                                case '3':
                                    hasUp = true;
                                    break;
                                case '4':
                                    for(var v = 1; v <= val.fwhsResourceDTOList[n].info.hbhs.value; v++){
                                        if(data.cFwList[index+v].dyFwList[ind]) {
                                            data.cFwList[index + v].dyFwList[ind].fwhsResourceDTOList.splice(n, 0, {});
                                            dealColspanWithHb(data.dyList,data.cFwList[index + v].dyFwList[ind]);
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                }
                thisCMaxHs += val.fwhsResourceDTOList.length;
            });

            // 由于 左右合并 或向上合并 处理，所以最长户室可能会有变化 需要计算后重新赋值
            if(thisCMaxHs.length > data.maxHs){
                data.maxHs = thisCMaxHs;
            }
        });

        // 层反转 处理 向上合并逻辑
        if(hasUp){
            data.cFwList.reverse();
            data.cFwList.forEach(function (value,index) {
                var thisCMaxHs = 0;
                value.dyFwList.forEach(function (val,ind) {
                    for(var n = 0; n < val.maxHs; n++){
                        if(!$.isEmptyObject(val.fwhsResourceDTOList[n])){
                            if(val.fwhsResourceDTOList[n].info && val.fwhsResourceDTOList[n].info.hbhs
                                && val.fwhsResourceDTOList[n].info.hbhs.value){
                                switch(val.fwhsResourceDTOList[n].info.hbfx.value)
                                {
                                    case '3':
                                        for(var v = 1; v <= val.fwhsResourceDTOList[n].info.hbhs.value; v++){
                                            var trIndex = index+v;
                                            if(data.cFwList[trIndex] && data.cFwList[trIndex].dyFwList[ind]){
                                                var sxh = val.fwhsResourceDTOList[n].info.sxh.value;
                                                // console.info(val.fwhsResourceDTOList[n].info.fjh.value);
                                                // console.info("层:"+trIndex);
                                                // console.info("列:"+n);
                                                if(val.fwhsResourceDTOList[n].info.fjh.value == '202/202上'){
                                                    console.info(1);
                                                }
                                                data.cFwList[trIndex].dyFwList[ind].fwhsResourceDTOList.splice(n,0,{});
                                                // dealColspanWithHb(data.dyList,data.cFwList[trIndex].dyFwList[ind]);
                                            }
                                            // console.info(val.fwhsResourceDTOList[n].info.fjh.value);
                                            // console.info(data.cFwList[3].dyFwList[ind].fwhsResourceDTOList.length);
                                        }
                                        break;
                                }
                            }
                        }
                    }
                });
                // 由于 左右合并 或向上合并 处理，所以最长户室可能会有变化 需要计算后重新赋值
                if(thisCMaxHs.length > data.maxHs){
                    data.maxHs = thisCMaxHs;
                }
            });
            // 再反转
            data.cFwList.reverse();
        }
        // 把数据 处理成 后期容易使用的结构
        dealWithData(data);
        resource.data = data;
        // 加载表格 ToolBar
        initTableToolBar(resource);
        // 移除“无数据”DIV
        if (data && data.cFwList.length > 0) {
            resource.tabDocument.find('.layui-table-main .layui-none').remove();
        }
        // 初始化滚动条
        resizeTable(resource);
        resource.initScrollBar(data);
        resizeTable(resource);
    }else{
        initTableToolBar(resource);
    }

    var $bjBorder = $('.bdc-border');
    for(var i = 0,len = $bjBorder.length; i < len; i++){
        var $border = $($bjBorder[i]);
        $border.height($border.parent().innerHeight());
    }
}

function resScrollData(resource){
    scrollShowData(resource,resource.yPos,resource.xPos, resource.tabname);
}
/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param resname 户室类型
 * @return
 * @description 滚动显示数据
 */
function scrollShowData(resource,pos,xPos,resname){
    // var y = resource.yScroll.getPageItems();
    var y = 35;
    // var x = resource.xScroll.getPageItems();
    var x = 15;
    var hasCartIcon = $("#code").val() == 'accept';
    var tableData = {
        cFwList: [],
        maxHs: resource.data.maxHs,
        cellHeight: resource.data.cellHeight,
        hasCartIcon: hasCartIcon,
        checkBoxHide:resource.checkBoxHide
    };

    for (var i = 0; i < y; i++) {
        // 获取这一层
        var cFwList = $.extend({},resource.data.cFwList[i+pos]);
        if(cFwList.cellElem){
            var newCellElemArr = [];
            // 行数据
            for (var j = 0; j < x; j++) {
                // 取列数据
                var cellElem = cFwList.cellElem[j+xPos];
                if(cellElem){
                    newCellElemArr.push(cellElem);
                }
            }
            cFwList.cellElem = newCellElemArr;
            tableData.cFwList.push(cFwList);
        }
    }
    if(isNotBlank(resname)){
        tableData.hslx = resname;
    }

    layui.use(['table','form','laytpl'], function () {
        var laytpl = layui.laytpl;
        var mainTrTplHtml = mainTrTpl.innerHTML;
        laytpl(mainTrTplHtml).render(tableData, function(html){
            var obj= resource.tabDocument.find('.layui-table-box .layui-table-main tbody')[0];
            if(obj){
                obj.innerHTML=html;
            }
        });

        // 横向滚动条不动时 不用处理header
        if(xPos ==0 || xPos != resource.xPos){
            // header单元号的加载
            var dyhData = dealHeaderDyh(xPos,x);
            var headerTrTplHtml = headerTpl.innerHTML;
            laytpl(headerTrTplHtml).render({dyhData:dyhData,cellNum:x,hasCartIcon:hasCartIcon}, function(html){
                var obj= resource.tabDocument.find('.layui-table-box .layui-table-header thead')[0];
                if(obj){
                    obj.innerHTML=html;
                }
            });
        }

        // 纵向滚动条不动时 不用处理锁定列
        // 锁定列
        var fixedTrTplHtml = fixedTrTpl.innerHTML;
        var obj= resource.tabDocument.find('.layui-table-box');
        var inited = true;
        if(obj.find(".layui-table-fixed").length == 0){
            inited = false;
        }
        tableData.inited = inited;
        laytpl(fixedTrTplHtml).render(tableData, function(html){
            if(inited){
                obj.find(".layui-table-fixed tbody")[0].innerHTML = html;
            }else{
                obj.append(html);
            }
        });
    });
    // 检查是否是最后一页 解决最后一列或一行显示不全问题
    checkLastPage(resource,pos,xPos);
    // 显示属性
    // if(pos != 0 || xPos != 0){
    scrollWithShowColumn();
    // }
    // 筛选效果
    scrollWithFilter();
    // 增加合并单元格处理
    scrollWithHbCell(tableData);
    refreshBorderHeight();
    resource.yPos = pos;
    resource.xPos = xPos;
    $('.layui-table-cell').css('width', lpbtdwidth + 'px');
    $('.bdc-fh span').css('max-width', lpbtdwidth - 40 + 'px');
}


function scrollWithHbCell(tableData){
    // 加载完后 重新获取mergeData
    var mergeData = initMergeData(tableData);
    // 重置高度，合并单元格
    init(currentRes,mergeData);
    // 重置表格内容宽度
    // var $table = $('.layui-show .layui-table-main').find('table');
    // $table.css('width',$table.find('tr:first-child td').length * 120 + 120 + 'px' );
}

function dealHeaderDyh(xPos,pageSize){
    var dyhList = currentRes.data.dyList;
    var dyhArr = [];
    var lim = xPos;
    var lim2 = 0;
    for(var i =0;i<dyhList.length;i++){
        if(lim != -999){
            if(lim < dyhList[i].colspan){
                // {dyh:dyhArr[i],colspan:1}
                var dyhCol = dyhList[i].colspan-lim;
                lim2 += dyhCol;
                lim = 0;
                if(lim2 >= pageSize){
                    dyhCol = dyhCol-(pageSize-lim2);
                    lim = -999;
                }
                dyhArr.push({dyh:dyhList[i].dyh,colspan:dyhCol});
            }else{
                lim = lim - dyhList[i].colspan;
            }
        }
    }
    return dyhArr;
}

function scrollWithFilter(){
    var searchList = [];
    var searchClassList = [];
    var getYtList = [];
    var jyztList = [];
    var searchSpan = currentRes.tabDocument.find(".bdc-search-content>span");
    if(searchSpan.length > 0){
        for(var i =0 ;i<searchSpan.length;i++){
            var title = $(searchSpan[i]).attr("title");
            var clasz = $(searchSpan[i]).attr("class");
            searchList.push(title);
            searchClassList.push(clasz);
            if (clasz == "bdc-search-yt") {
                getYtList = getYtList.concat(title.split(","));
            } else if (clasz == "bdc-search-jyzt") {
                jyztList = jyztList.concat(title.split(","));
            }
        }
    }
    updatePageStyle(searchList, searchClassList, getYtList, jyztList);
}

function scrollWithShowColumn(){
    for(var i=0;i<currentRes.showColumn.length;i++){
        showColumn(currentRes.showColumn[i],currentRes.showColumn[i].checked,currentRes);
    }
}

function checkLastPage(resource,pos,xPos){
    // 判断竖向是否是最后一页
    var yScroll = resource.yScroll;
    // 用当前滚动位置的起始下标值 + 当页页容量 计算 是否已达到最后一帧
    if(pos+ yScroll.getPageItems() >= yScroll.options.total){
        // 如果最后一帧 为 第一帧 则隐藏当前滚动条 显示原有滚动条
        if(pos == 0){
            //如果最后一行展示的数据不够3/4 则仍旧可以滚动
            if (((yScroll.options.total * yScroll.options.itemSize - yScroll.options.size) / yScroll.options.itemSize) >= 0.15) {
                //reload的时候，要先把原有滚动条去掉重新计算
                $("#" + resource.yScrollId).removeClass("layui-hide");
                resource.tabDocument.find(".layui-table-main").css("overflow-y", "hidden");
                resource.tabDocument.find(".layui-table-main").scrollTop(0);
                resource.tabDocument.find(".layui-table-fixed .layui-table-body").scrollTop(0);
            } else {
                $("#" + resource.yScrollId).addClass("layui-hide");
                resource.tabDocument.find(".layui-table-main").css("overflow-y", "auto");
            }
        }else{
            // 如果已达到最后一帧 则滚动到最底部
            var mainTable = $("#"+resource.tabId).find(".layui-table-main");
            var fixTable = $("#"+resource.tabId).find(".layui-table-fixed .layui-table-body");
            var height = mainTable[0].scrollHeight;
            mainTable[0].scrollTop = height;
            fixTable[0].scrollTop = height;
        }
    }else{
        resource.tabDocument.find(".layui-table-main").scrollTop(0);
        resource.tabDocument.find(".layui-table-fixed .layui-table-body").scrollTop(0);
    }
    var xScroll = resource.xScroll;
    if((xPos+xScroll.getPageItems()) >= xScroll.options.total){
        // 如果最后一帧 为 第一帧 则隐藏当前滚动条 显示原有滚动条
        if(xPos == 0){
            $("#" + resource.xScrollId).addClass("layui-hide");
            resource.tabDocument.find(".layui-table-main").css("overflow-x","auto");
            resource.tabDocument.find(".layui-table-fixed").css("display","none");
        }else{
            var length = xScroll.getPageItems() * xScroll.options.itemSize + 40;
            resource.tabDocument.find(".layui-table-main").scrollLeft(length);
        }
    }else{
        resource.tabDocument.find(".layui-table-main").scrollLeft(0);
    }
}
/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param
 * @return
 * @description 渲染表格头部
 */
function initTableToolBar(resource){
    consoleWithTime("开始init");
    layui.use(['table','form','laytpl'], function () {
        var table = layui.table;
        table.init(resource.tableFilter,{
            id: resource.tableResizeId,
            toolbar: '#' + resource.toorBarId,
            defaultToolbar: [],
            limit:10000,
            done: function () {
                //清空渲染在表格中的内容，避免后期选中之类的有问题
                $('#' + resource.tableId).html('');
            }
        });
    });
    consoleWithTime("结束init");
}

function refreshBorderHeight(){
    var borders = currentRes.tabDocument.find(".bdc-border");
    for(var i = 0 ; i < borders.length; i++){
        $(borders[i]).height($(borders[i]).parent().height());
    }
}

function initMergeData(data){
    var mergeData = [];
    data.cFwList.forEach(function (value,index) {
        value.dyFwList.forEach(function (val,ind) {
            for(var n = 0; n < val.maxHs; n++){
                if(!$.isEmptyObject(val.fwhsResourceDTOList[n])){
                    if(val.fwhsResourceDTOList[n].info && val.fwhsResourceDTOList[n].info.hbhs
                        && val.fwhsResourceDTOList[n].info.hbhs.value){
                        var num = 0;
                        for(var m = 0; m < ind; m++){
                            num += data.cFwList[index].dyFwList[m].maxHs;
                        }
                        var curHsDiv = $("input[data-index='"+val.fwhsResourceDTOList[n].info.fwHsIndex.value+"']");
                        // 下标从0开始所以+1
                        var trIndex = curHsDiv.parents("tr").index()+1;
                        var tdIndex = curHsDiv.parents("td").index()+1;
                        switch(val.fwhsResourceDTOList[n].info.hbfx.value)
                        {
                            case '1':case '2':
                            mergeData.push([trIndex,tdIndex,{colspan:val.fwhsResourceDTOList[n].info.hbhs.value + 1}]);
                            break;
                            case '3':
                                mergeData.push([trIndex,tdIndex,{up:val.fwhsResourceDTOList[n].info.hbhs.value + 1}]);
                                break;
                            case '4':
                                mergeData.push([trIndex,tdIndex,{rowspan:val.fwhsResourceDTOList[n].info.hbhs.value + 1}]);
                                break;
                        }
                    }
                }
            }
        });
    });
    return mergeData;
}

function init(resource,mergeData) {
    // initCartIcon(resource);
    var element = resource.tabDocument;
    // 根据 td 和 tr 补齐长度
    var trList = element.find('.layui-table-main tbody tr');
    // 获取最大的 td长度
    var maxTdSize = 0;
    for(var i = 0 ;i<trList.length;i++){
        var tdList = $(trList[i]).find("td");
        if(tdList.length > maxTdSize){
            maxTdSize = tdList.length;
        }
    }

    for(var i = 0 ;i < trList.length;i++){
        var tdList = $(trList[i]).find("td");
        var tdSize = tdList.length;
        var j = maxTdSize - tdSize;
        if(j > 0){
            for(var k = 0 ; k < j ; k++){
                $(trList[i]).append("<td ><div class=\"layui-table-cell\"></div></td>")
            }
        }
    }
    mergeData.forEach(function (value) {
        if(value[2].rowspan){
            element.find('.layui-table-main tbody tr:nth-child('+value[0]+') td:nth-child('+ value[1] +')').attr('rowspan',value[2].rowspan);
            for(var i = 1; i < value[2].rowspan; i++){
                var getInd = value[0] + i;
                element.find('.layui-table-main tbody tr:nth-child('+getInd +') td:nth-child('+ value[1] +')').css('display','none');
            }
        }else if(value[2].colspan){
            element.find('.layui-table-main tbody tr:nth-child('+value[0]+') td:nth-child('+ value[1] +')').attr('colspan',value[2].colspan);
            for(var n = 1; n < value[2].colspan; n++){
                var getIndex = value[1] + n;
                element.find('.layui-table-main tbody tr:nth-child('+value[0] +') td:nth-child('+ getIndex +')').css('display','none');
            }
        }else if(value[2].up){
            // 获取合并的 单元格html
            var getHtml = element.find('.layui-table-main tbody tr:nth-child(' + value[0] + ') td:nth-child(' + value[1] + ')').html();
            for(var n = 0; n < value[2].up - 1; n++){
                // console.info("up循环" + n);
                var getIndex1 = value[0] - n;
                element.find('.layui-table-main tbody tr:nth-child('+getIndex1 +') td:nth-child('+ value[1] +')').css('display','none');
            }
            var getNum = value[0] - value[2].up +1;
            if(getNum < 1){
                getNum = 1;
                value[2].up = value[0];
            }
            //console.info("test " + element.find('.layui-table-main tbody tr:nth-child('+getNum+') td:nth-child('+ value[1] +')').length);
            var curTd = element.find('.layui-table-main tbody tr:nth-child('+getNum+') td:nth-child('+ value[1] +')');
            curTd.html(getHtml).attr('rowspan',value[2].up);
            curTd.removeAttr("style");
        }
    });
}


function reverseTableCell() {
    var reverseList=["zl"];
    var getTd = $('.layui-table-view .layui-table .bdc-td-show');
    for(var i = 0; i < getTd.length; i++){
        reverseList.forEach(function (v) {
            var markP = $(getTd[i]).find('p[mark='+v+']');
            if(markP){
                markP.css('direction','rtl');
                if(markP.html()){
                    var tdHtml = reverseString(markP.html());
                    markP[0].innerHTML='<span class="bdc-table-date">'+ tdHtml +'</span>';
                }
            }
        });
    }
}

/**
 * 上下合并时处理 colspan
 */
function dealColspanWithHb(dylist,curDyFw){
    var curSize = curDyFw.fwhsResourceDTOList.length;
    var curDyh = curDyFw.dyh;
    var masHs = curDyFw.maxHs;
    dylist.forEach(function(value){
       if(value.dyh == curDyh){
           var colspan = value.colspan;
           if(colspan < curSize){
               value.colspan = colspan+1;
               return;
           }
       }
    });
    if(masHs < curSize){
        curDyFw.maxHs = masHs+1;
    }
}


function initZsxx(resource){
    var info = resourceData.info;
    var zsxxList = [];
    for(var key in info){
        // 获取配置类型  判断是否是 展示字段类型
        var type = eval("info." + key + ".type");
        var tabType = eval("info." + key + ".tabType");
        var desc = eval("info." + key + ".desc");
        var value = eval("info." + key + ".value");
        if((!type || type == "COLUMN" || type=='JOINCOLUMN')
            && desc && resource.checkHasTab(tabType)){
            if(!value &&value !=0){
                value = "";
            }
            zsxxList.push({"desc":desc,"value":value})
        }
    }
    if(zsxxList.length > 0){
        layui.use(['laytpl', 'form'], function () {
            var laytpl = layui.laytpl;
            var zsxxTplHtml = zsxxTpl.innerHTML;
            laytpl(zsxxTplHtml).render(zsxxList, function (html) {
                $("#" + resource.tabId).prepend(html);
            });
        });
    }
}