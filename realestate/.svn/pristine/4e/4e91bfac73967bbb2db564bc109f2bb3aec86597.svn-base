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
        if ($showTip.length > 0) {
            var tablewidth = $('.bdc-right-content').width();
            var table_cell_width = $(this).width();
            var table_cell_offset = parseInt(table_cell_width) - 110;
            var left = $(this).offset().left + 100 + table_cell_offset;
            var top = $(this).offset().top - 180;

            // 默认右侧展示
            if (left + 280 < tablewidth) {
                $showTip.css({top: top + 'px', 'margin-left': left + 'px'});
            } else {
                // 右侧空间不足 左侧展示
                $showTip.css({top: top + 'px', 'margin-left': left - 280 - 110 + table_cell_offset + 'px'});
            }

            $showTip.removeClass('bdc-hide');
        }
    });
    // 鼠标移出隐藏悬浮框
    $(document).on('mouseleave', 'td', function () {
        window.parent.document.execCommand('RespectVisibilityInDesign', false, "true");
        if( $(this).find('.bdc-title-tips').length > 0){
            $(this).find('.bdc-title-tips').addClass('bdc-hide');
        }
    });
});

// 页面初始化
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
    obj.yScroll = new Scrollbar();
    obj.xScroll = new Scrollbar();
    obj.yPos=0; // x轴滚动位置
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
        // 表格内容可见区高度
        var height = _this.tabDocument.find(".layui-table-box").height() - _this.tabDocument.find(".layui-table-header").height();
        // 单元格高度
        var itemSize = _this.data.cellHeight;
        // 可见区容纳行数
        var pagesize = parseInt(height / itemSize)+1;
        // 容纳行数实际高度
        var totalSize = pagesize * itemSize;
        _this.yScroll.setOptions({
            total: _this.data.totalRow + 1.5, //总行数
            length: height + 38, // 滚动条的样式尺寸
            itemSize: itemSize,  // 滚动条每滚动一下的尺寸
            pagesize: pagesize,  // 可见区容纳行数
            totalSize: totalSize, // 可容纳行实际高度
            size: height + 38,   //滚动条尺寸
        });

        if(_this.tabDocument.find("#"+_this.yScrollId).find("div").length ==0 ){
            // 不存在 新建
            _this.yScroll.CreateAt(_this.yScrollId);
        }
        _this.yScroll.windowAddMouseWheel(_this,_this.yScrollId);

        _this.yScroll.onScroll = function (pos) {
            scrollShowData(_this, pos, resname);
        };

        scrollShowData(_this, _this.yPos, resname);
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

// 1.楼盘表加载
// 1.1 初始化楼盘表
function tplTable(data,resource){
    if(data && data.cFwList) {
        data.cFwList.forEach(function (value,index) {
            // 一层存在多个单元号 根据每行户室个数重新分组拼接
            if(value.dyFwList.length > 1){
                // 重新获取该层全部户室数据
                var fwhsResourceDTOList=[];
                value.dyFwList.forEach(function (val,ind) {
                    val.fwhsResourceDTOList.forEach(function (v) {
                        fwhsResourceDTOList.push(v);
                    })
                });

                // 根据每行户室个数分组
                var groupfwhsResourceDTOList=[];
                // 全部户室所占行数
                var rowlen = Math.ceil(fwhsResourceDTOList.length/chscount);
                // 单行数据
                for(var i=0; i<rowlen; i++){
                    var list=[];
                    for(var j=0; j<chscount; j++){
                        if(fwhsResourceDTOList[i*chscount +j]){
                            list.push(fwhsResourceDTOList[i*chscount +j]);
                        }
                    }
                    groupfwhsResourceDTOList.push(list);
                }
                // 重组数据替换原单元号划分数据
                value.dyFwList = [{
                    "fwhsResourceDTOList": fwhsResourceDTOList,
                    "groupfwhsResourceDTOList": groupfwhsResourceDTOList,
                }];
            }
        });
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

// 1.2 处理数据 便于后期使用
// 1.2.1 处理数据 置空
function dealWithData(data){
    // 循环层  处理每一层所有data
    var allHsCells = [];
    var cHsCells = [];
    // 总行数
    var totalRow = 0;

    data.cFwList.forEach(function (value,index) {
        var cellElem = [];

        // 重新组织后的数据不区分单元号
        cHsCells.push({wlcs:value.wlcs,cells:[]});

        // 获取每层最后一行
        var last;
        if(value.dyFwList[0].groupfwhsResourceDTOList){

            last = value.dyFwList[0].groupfwhsResourceDTOList.length - 1

            value.dyFwList[0].groupfwhsResourceDTOList.forEach(function (val) {
                val.forEach(function (v){
                    if (v.info && v.info.fwHsIndex) {
                        // 获取全部户室
                        allHsCells.push(v);
                        cHsCells[index].cells.push(v);
                        // 增加每格户室状态显色
                        addTdColor(v);
                    }
                });
            });
        }else {
            last =0;
            // 空层处理
            value.dyFwList[0].groupfwhsResourceDTOList=[[]];
            value.dyFwList[0].groupfwhsResourceDTOList[0].push(value.dyFwList[0].fwhsResourceDTOList[0]);
        }

        // 每层户室最后一行小于设置户室个数 则置空
        for (var i = 0; i < chscount; i++) {
            var temp = value.dyFwList[0].groupfwhsResourceDTOList[last][i];
            if (!temp) {
                value.dyFwList[0].groupfwhsResourceDTOList[last][i] = {};
            }
        }

        cellElem = cellElem.concat(value.dyFwList[0].groupfwhsResourceDTOList);

        value.cellElem = cellElem;
        // 该层在总行数中的起始位置，便于懒加载定位
        value.rowlen = totalRow;
        // 计算总行数
        totalRow += last + 1;

    });

    data.allHsCells = allHsCells;
    data.cHsCells = cHsCells;
    data.totalRow = totalRow;
}

// 1.2.2 单元格增加状态显色
function  addTdColor(value) {
    // 获取备案状态配色
    var bacolor="";
    if(value.info.baztMap.class =="bdc-ba"){
        bacolor = value.info.baztMap.color;
    }
    // 单元格显色优先级： 已注销>预告 > 已登记 > 备案 > 未登记
    if(isNotBlank(value.info.fjh.color)){
        // 获取登记状态
        var state = value.info.fjh.color
        if (state === "ZX") {
            value.backcolor = getStateColorCss(state);

        } else if (state === "YG") {
            value.backcolor = getStateColorCss(state);
        } else if (state === "DJ") {
            value.backcolor = getStateColorCss(state);

        } else if (state === "WDJ") {

            if (isNotBlank(bacolor)) {
                value.backcolor = "background-color:" + bacolor;

            } else {
                value.backcolor = getStateColorCss(state);
            }

        } else if (isNotBlank(bacolor)) {
            value.backcolor = "background-color:" + bacolor;
        }

    }else if(isNotBlank(bacolor)) {
        value.backcolor = "background-color:" + bacolor;
    }
}

// 1.3 渲染表格头部
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

// 1.4 楼盘表滚动条加载
// 1.4.1 滚动显示数据
/**
 * @author <a href="mailto:yousiyi@gtmap.cn">yousiyi</a>
 * @param resname 户室类型
 * @return
 */
function scrollShowData(resource,ypos,resname){
    // 懒加载每次最多加载35行
    var y = 35;
    // var y = resource.yScroll.getPageItems();
    var hasCartIcon = $("#code").val() == 'accept';
    var tableData = {
        cFwList: [],
        // totalRow: resource.data.totalRow,
        cellHeight: resource.data.cellHeight,
        hasCartIcon: hasCartIcon,
        checkBoxHide:resource.checkBoxHide,
        chscount: chscount
    };

    // 定位可视区起始层
    var startRow,row=0;
    for(var i=0; i<resource.data.cFwList.length; i++){
        // 根据ypos确定显示中第一行所在层位置
        if(resource.data.cFwList.length==1){
            // 只有一行
            startRow = 0;
        }else if(ypos >= resource.data.cFwList[i].rowlen){
            if(!resource.data.cFwList[i+1]){
                // 所在层为最后一层
                startRow = i;
            }else if(ypos <= resource.data.cFwList[i+1].rowlen){
                startRow = i;
            }
        }


        // 确定起始层后开始截取数据
        if(startRow != undefined){
            // 获取这一层
            var cFwList = $.extend({},resource.data.cFwList[i]);
            var newCellElemArr = [];

            if(startRow == i){
                for(var j= ypos - resource.data.cFwList[i].rowlen; j< resource.data.cFwList[i].cellElem.length; j++){
                    if(row <= y){
                        newCellElemArr.push(cFwList.cellElem[j]);
                        row++;
                    }
                }
            }else {
                for(var j=0; j< resource.data.cFwList[i].cellElem.length; j++){
                    if(row <= y){
                        newCellElemArr.push(cFwList.cellElem[j]);
                        row++;
                    }
                }
            }
            // 重新组织展示区户室数据
            cFwList.cellElem = newCellElemArr;
            tableData.cFwList.push(cFwList);
        }
    }
    if(isNotBlank(resname)){
        tableData.hslx = resname;
    }

    // 加载楼盘表内容
    layui.use(['table','form','laytpl'], function () {
        var laytpl = layui.laytpl;
        var mainTrTplHtml = mainTrTpl.innerHTML;
        laytpl(mainTrTplHtml).render(tableData, function(html){
            var obj= resource.tabDocument.find('.layui-table-box .layui-table-main tbody')[0];
            if(obj){
                obj.innerHTML=html;
            }
        });

        var headerTrTplHtml = headerTpl.innerHTML;
        laytpl(headerTrTplHtml).render([], function(html){
            var obj= resource.tabDocument.find('.layui-table-box .layui-table-header thead')[0];
            if(obj){
                obj.innerHTML=html;
            }
        });

    });
    // 检查是否是最后一页 解决最后一列或一行显示不全问题
    checkLastPage(resource,ypos);
    // 显示属性
    scrollWithShowColumn();
    // 筛选效果
    scrollWithFilter();
    // 增加合并单元格处理
    // scrollWithHbCell(tableData);
    refreshBorderHeight();
    resource.yPos = ypos;
    $('.layui-table-cell').css('width', lpbtdwidth + 'px');
    $('.bdc-fh span').css('max-width', lpbtdwidth - 40 + 'px');
}

// 1.4.2 重新加载楼盘表
function resScrollData(resource){
    scrollShowData(resource,resource.yPos, resource.tabname);
}

// 1.5 属性筛选显示
// 1.5.1 筛选效果
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

// 1.5.2 显示属性
function scrollWithShowColumn(){
    for(var i=0;i<currentRes.showColumn.length;i++){
        showColumn(currentRes.showColumn[i],currentRes.showColumn[i].checked,currentRes);
    }
}

// 1.6 检查是否是最后一页 解决最后一行显示不全问题
function checkLastPage(resource,ypos){
    // 判断竖向是否是最后一页
    var yScroll = resource.yScroll;

    // 若容纳行数实际高度 大于 可见区高度
    // 总行数 大于 当页页容量
    // 则需处理显示不全问题
    if(yScroll.options.totalSize > (yScroll.options.length - 38) && yScroll.options.total >= yScroll.options.pagesize ){
        // 用当前滚动位置的起始下标值 + 当页页容量
        // 计算 是否已达到最后一帧
        // 滚动条在顶部时除外
        if(ypos + 1 + yScroll.options.pagesize >= yScroll.options.total && yScroll.bar.scrollTop != '0'){
            // 如果已达到最后一帧 则滚动到最底部
            var mainTable = $("#"+resource.tabId).find(".layui-table-main");
            var height = mainTable[0].scrollHeight;
            mainTable[0].scrollTop = height + 38;
        }else {
            resource.tabDocument.find(".layui-table-main").scrollTop(0);
            resource.tabDocument.find(".layui-table-fixed .layui-table-body").scrollTop(0);
        }
    }else{
        resource.tabDocument.find(".layui-table-main").scrollTop(0);
        resource.tabDocument.find(".layui-table-fixed .layui-table-body").scrollTop(0);
    }
}

// 初始化证书信息
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

// 翻转显示坐落 省略符号在前
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




