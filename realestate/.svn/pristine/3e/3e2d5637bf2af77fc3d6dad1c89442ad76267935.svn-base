/**
 * 持件信息查询自定义查询 js
 */

// 新增持件信息
function addCjxx() {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/cjxx/init",
        type: 'GET',
        dataType: 'json',
        success: function (result) {
            var obj = {
                cjr : result.cjr,
                lsh : result.lsh,
                cjrid: result.cjrid,
            };
            layer.open({
                title: '持件信息',
                type: 2,
                content: "/realestate-inquiry-ui/view/zdycx/cjxxEdit.html",
                area: ['800px', '450px'],
                success: function(layero,index){
                    var iframeWin = window[layero.find('iframe')[0]['name']];
                    iframeWin.renderForm(obj);
                },
                end: function(){
                    layui.table.reload('pageTable', {page: {curr: 1}});
                }
            });
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
        }
    });
}

// 修改持件信息
function updateCjxx(){
    var checkedStatus = layui.table.checkStatus('pageTable');
    if(isNullOrEmpty(checkedStatus) || checkedStatus.data.length ==  0 ||checkedStatus.data.length > 1){
        warnMsg("请选择一条进行关联");
        return false;
    }
    var data = checkedStatus.data[0];
    var result = {
        cjxxid : data.ID,
        cjr : data.CJR,
        cjsj: data.CJSJ,
        qlr: data.QLR,
        lsh: data.LSH,
        slbh: data.SLBH,
        bz: data.BZ,
        zl: data.ZL
    };
    layer.open({
        title: '修改持件信息',
        type: 2,
        content: "/realestate-inquiry-ui/view/zdycx/cjxxEdit.html",
        area: ['800px', '450px'],
        btn: ["保存","取消"],
        yes: function(index, layero){
            var iframeWin = window[layero.find('iframe')[0]['name']];
            var bdccjxx = iframeWin.getBdcCjxx();
            if(isNullOrEmpty(bdccjxx.cjxxid)){
                warnMsg("未获取到当前持件信息的ID内容")
                return;
            }
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/cjxx/save",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(bdccjxx),
                success: function (data) {
                    ityzl_SHOW_SUCCESS_LAYER("修改成功");
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                }
            });
        },
        btn2: function(index,layero){
            layer.close(index);
        },
        success: function(layero,index){
            var iframeWin = window[layero.find('iframe')[0]['name']];
            iframeWin.renderForm(result);
        },
        end: function(){
            layui.table.reload('pageTable', {page: {curr: 1}});
        }
    });
}


//  删除持件信息
function deleteCjxx(){
    var checkedStatus = layui.table.checkStatus('pageTable');
    if(isNullOrEmpty(checkedStatus) || checkedStatus.data.length ==  0){
        warnMsg("至少选中一条数据进行删除");
        return false;
    }
    var ids = [];
    $.each(checkedStatus.data, function(index, value){
        if(isNotBlank(value.ID)){
            ids.push(value.ID);
        }
    });
    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/cjxx/delete",
        type: 'DELETE',
        dataType: 'json',
        data: JSON.stringify(ids),
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("删除成功");
            layui.table.reload('pageTable', {page: {curr: 1}});
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

// 导出excel
function exportCjxx(){
    var cols = tableConfig.cols[0];

    var showColsTitle = [];
    // 列内容
    var showColsField = [];
    // 列宽
    var showColsWidth = [];
    for(var index in cols){
        if(cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar){
            // 去除列表中展示的持件时间、备注
            if(["CJSJ","BZ"].indexOf(cols[index].field) < 0){
                showColsTitle.push(cols[index].title);
                showColsField.push(cols[index].field);
                if(cols[index].width > 0 && !isNullOrEmpty(cols[index].dcwidth)){
                    showColsWidth.push(parseInt(cols[index].dcwidth / 100 * 15));
                }else if(cols[index].width > 0){
                    showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                }else if(cols[index].minWidth > 0){
                    showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
                }else{
                    showColsWidth.push(200 / 100 * 15);
                }
            }
        }
    }

    var queryObjectCopy = JSON.parse(JSON.stringify(queryObject));
    delete queryObjectCopy.cxid;
    var dataStringCopy = JSON.stringify(queryObjectCopy);
    if (dataStringCopy === '{}') {
        warnMsg("请先查询数据");
        return;
    }
    var checkedDataAll = layui.sessionData('allPageCheckedData');//跨页选中；
    var dataAll = new Array();
    $.each(checkedDataAll, function(key, value){
        dataAll.push(value);
    })

    if (dataAll.length === 0) {
        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要导出Excel的数据行!');
    } else {
        var data = JSON.stringify(dataAll);
        doExport(showColsTitle, showColsField, showColsWidth, data);
    }
}


function doExport(colsTitle, colsField, colsWidth, data){
    var tempForm = $("<form>");
    tempForm.attr("id", "exportForm");
    tempForm.attr("style", "display:none");
    tempForm.attr("target", "export");
    tempForm.attr("method", "post");
    tempForm.attr("action", '/realestate-inquiry-ui/excel/export');

    var fileNameInput = $("<input>");
    fileNameInput.attr("type", "hidden");
    fileNameInput.attr("name", "fileName");
    fileNameInput.attr("value", "持件信息");
    tempForm.append(fileNameInput);

    var sheetNameInput = $("<input>");
    sheetNameInput.attr("type", "hidden");
    sheetNameInput.attr("name", "sheetName");
    sheetNameInput.attr("value", "移交单");
    tempForm.append(sheetNameInput);

    var cellTitleInput = $("<input>");
    cellTitleInput.attr("type", "hidden");
    cellTitleInput.attr("name", "cellTitle");
    cellTitleInput.attr("value", colsTitle);
    tempForm.append(cellTitleInput);

    var cellWidthInput = $("<input>");
    cellWidthInput.attr("type", "hidden");
    cellWidthInput.attr("name", "cellWidth");
    cellWidthInput.attr("value", colsWidth);
    tempForm.append(cellWidthInput);

    var cellKeyInput = $("<input>");
    cellKeyInput.attr("type", "hidden");
    cellKeyInput.attr("name", "cellKey");
    cellKeyInput.attr("value", colsField);
    tempForm.append(cellKeyInput);

    var dataInputSelect = $("<input>");
    dataInputSelect.attr("type", "hidden");
    dataInputSelect.attr("name", "data");
    dataInputSelect.attr("value", data);
    tempForm.append(dataInputSelect);


    var addBorder = $("<input>");
    addBorder.attr("type", "hidden");
    addBorder.attr("name", "addBorder");
    addBorder.attr("value", "true");
    tempForm.append(addBorder);

    // 保存Excel导出日志
    saveDetailLog(rzlx + "_EXPORT", rzmc + "-导出Excel", {"DCNR": data});
    tempForm.on("submit", function () {});
    tempForm.trigger("submit");
    $("body").append(tempForm);
    tempForm.submit();
    $("tempForm1").remove();
}

// 导出全部
function exportAllCjxx(){
    addModel();
    var params ={
        data: JSON.stringify(queryObject),
        page: 1,
        size: 999,
    };
    $.ajax({
        url: '/realestate-inquiry-ui/dtcx/list/result',
        type: "POST",
        async: true,
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(params),
        success: function (data) {
            removeModal();
            if(data.content.length>0){
                var cols = tableConfig.cols[0];

                var showColsTitle = [];
                // 列内容
                var showColsField = [];
                // 列宽
                var showColsWidth = [];
                for(var index in cols){
                    if(cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar){
                        // 去除列表中展示的持件时间、备注
                        if(["CJSJ","BZ"].indexOf(cols[index].field) < 0){
                            showColsTitle.push(cols[index].title);
                            showColsField.push(cols[index].field);
                            if(cols[index].width > 0 && !isNullOrEmpty(cols[index].dcwidth)){
                                showColsWidth.push(parseInt(cols[index].dcwidth / 100 * 15));
                            }else if(cols[index].width > 0){
                                showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                            }else if(cols[index].minWidth > 0){
                                showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
                            }else{
                                showColsWidth.push(200 / 100 * 15);
                            }
                        }
                    }
                }
                doExport(showColsTitle, showColsField, showColsWidth, JSON.stringify(data.content));
            }else{
                warnMsg("未获取到数据");
            }
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}
