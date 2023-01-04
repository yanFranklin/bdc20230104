// 全局变量
var globalData = {};
var resourceData = {};
var zdList = {};
var param = $("#param").val();
var resUrl = "../lpb/lpbres";
var hsUrl = "";
var sortedYhsList = [];
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
    obj.checkedShowColumn = {};
    obj.tableFilter = resname + "TableLayFilter";
    obj.toorBarId = resname + "Toolbar";
    obj.hasSelectBtn = false;
    obj.checkBoxHide = false;
    obj.dataUrl = "";
    obj.checkHasTab = function (tabType){
        return tabType && (tabType.indexOf(obj.tabname) == 0 || tabType.indexOf(',' + obj.tabname) > 0);
    };
    obj.showThisTab = function(){
        $("#"+obj.tabId).addClass("layui-show");
        $("#"+obj.tabTitleId).addClass("layui-this");
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
                    gzlslid:gzlslid
                },
                success: function (data) {
                    $("#fwDcbIndex").val(fwDcbIndex);
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
                gzlslid:gzlslid
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
    };
    return obj;
};
var ychsRes = new ychsResObj();

/**
 * 默认当前资源为户室资源
 * @type {hsResObj}
 */
var currentRes = hsRes;

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

        var mergeData = [];
        var hasUp = false;
        data.cFwList.forEach(function (value,index) {
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
            });
        });

        // 层反转 处理 向上合并逻辑
        if(hasUp){
            data.cFwList.reverse();
            data.cFwList.forEach(function (value,index) {
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
            });
            // 再反转
            data.cFwList.reverse();
        }
        layui.use(['table','form','laytpl'], function () {
            var table = layui.table;
            var form = layui.form;
            var laytpl = layui.laytpl;
            var tableData = {data:data,resource:resource};

            var getTpl = newTableTpl.innerHTML
                ,tableView = $('#'+resource.tableId)[0];
            laytpl(getTpl).render(tableData, function(html){
                tableView.innerHTML=html;
                html = null;
            });
            var timestamp1 = (new Date()).valueOf();
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

            consoleWithTime("开始init");

            //渲染表格中的数据
            var getTableMainTpl = tableMainTpl.innerHTML;
            laytpl(getTableMainTpl).render(tableData, function(html){
                var obj= resource.tabDocument.find('.layui-table-box .layui-table-main')[0];
                if(obj){
                    consoleWithTime("innerHTML开始");
                    obj.innerHTML=html;
                }
                html = null;
                consoleWithTime("innerHTML结束");
            });
            consoleWithTime("加载主表格");
            var getTableFixedTpl = tableFixedTpl.innerHTML;
            laytpl(getTableFixedTpl).render(data, function(html){
                var obj=resource.tabDocument.find('.layui-table-fixed .layui-table-body tbody')[0];
                if(obj){
                    obj.innerHTML=html;
                }
                html = null;
            });
            consoleWithTime("加载锁定表格");

            // 加载完后 重新获取mergeData
            mergeData = initMergeData(data);
            consoleWithTime("处理合并数据");
            // 重置高度，合并单元格
            init(resource,mergeData);
            consoleWithTime("init整个方法结束");
            mergeData = null;
             // 表格 checkbox 判断是否隐藏
            if(resource.checkBoxHide){
                resource.tabDocument.find('.cell-checkbox').addClass('bdc-hide');
            }else{
                //form.render('checkbox',"tableFilter");
                resource.tabDocument.find('.cell-checkbox').removeClass('bdc-hide');
            }
            consoleWithTime("checkbox 判断是否隐藏");
        })
    }else{
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
    }

    var $bjBorder = $('.bdc-border');
    for(var i = 0,len = $bjBorder.length; i < len; i++){
        var $border = $($bjBorder[i]);
        $border.height($border.parent().innerHeight());
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
    initCartIcon(resource);
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
    // element.find('.layui-table-body').height(element.find('.layui-tab').height() - element.find('.bdc-state-show').height() - 159);
    // //内容扩展高度调整
    // if(element.find('.layui-table-main .layui-table td').height() != 28){
    //     element.find('.layui-table-fixed .layui-table td').height(element.find('.layui-table-main .layui-table td').height());
    //     element.find('.layui-table-fixed .layui-table tr:first-child td').height(element.find('.layui-table-main .layui-table td').height());
    // }
    mergeData.forEach(function (value) {
        var trLength = element.find('.layui-table-main tbody tr').length;
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

    //内容扩展高度调整
    consoleWithTime("高度调整开始……");

    resizeTable(resource.tabDocument);

    consoleWithTime('高度调整');

    checkDefShowColumn(resource);

    consoleWithTime('检查默认值');
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
            if(!value){
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