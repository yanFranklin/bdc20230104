function deletefwychs(data) {
    deleteFun(data,"../fwychs/delbyindex");
}
function deletefwhs(data) {
    deleteFun(data,"../fwhs/delbyindex");
}
function deleteFun(data,url){
    var fwHsIndexList = [];
    for (var i = 0; i < data.length; i++) {
        fwHsIndexList.push(data[i].fw_hs_index);
    }
    addModel();
    $.ajax({
        url: url,
        dataType: "json",
        data: "fwHsIndexList=" + encodeURI(fwHsIndexList),
        success: function (data) {
            layer.closeAll();
            removeModal();
            if (!data || !data.success) {
                layer.alert("删除失败");
            }
            refreshMainPage();
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });
}

// 户室属性批量修改
function plgxsxFun(data,fwDcbIndex) {
    var paramJson = [];
    for (var i = 0; i < data.length; i++) {
        paramJson.push(data[i].fw_hs_index);
    }
    layer.open({
        type: 2,
        title: "户室属性批量修改",
        area: ['960px', '500px'],
        content: "../fwhs/plgxsx?fwHsIndexList=" + encodeURI(paramJson) + "&fwDcbIndex="+fwDcbIndex
    });
}

// 户室面积批量修改
function plgxmjFun(data,fwDcbIndex) {
    var paramJson = [];
    for (var i = 0; i < data.length; i++) {
        paramJson.push(data[i].fw_hs_index);
    }
    layer.open({
        type: 2,
        title: "户室面积批量修改",
        area: ['960px', '500px'],
        content: "../fwhs/plgxmj?fwHsIndexList=" + encodeURI(paramJson) + "&fwDcbIndex="+fwDcbIndex
    });
}

// 批量更新frame 更新成功后的回调方法
function plgxCallBack(layerIndex) {
    layer.close(layerIndex);
    refreshMainPage();
    layer.msg("更新成功");
}

var updateLjzParamJson = [];

//修改逻辑幢页面
function updateLjzView(data) {
    var fwDcbIndex = data.fw_dcb_index;
    updateLjzParamJson.push(data.fw_hs_index);
    // loading加载
    var loadIndex = layer.load(2, {shade: [0.1, '#fff']});
    $.ajax({
        url: "../ljz/infoljz",
        type: "GET",
        data: {
            fwDcbIndex: fwDcbIndex
        },
        success: function (data) {
            layer.close(loadIndex)
            if (data && data.lszd && data.zrzh) {
                layer.open({
                    type: 2,
                    title: "逻辑幢列表",
                    area: ['960px', '400px'],
                    content: "../fwhs/updateLjzView?&fwDcbIndex=" + fwDcbIndex + "&lszd=" + data.lszd + "&zrzh=" + data.zrzh
                    , end: function (index, layero) {
                        $("#query").click();
                        return false;
                    }
                });
            } else {
                layer.msg("户室原所在逻辑幢不存在");
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });
}


// 修改逻辑幢
function pickLjzCallback(data) {
    if (data.fw_dcb_index == data.yFwDcbIndex) {
        layer.msg("修改前后逻辑幢相同，请重新选择")
    } else {
        var fwDcbIndex = data.fw_dcb_index;
        // loading加载
        var loadIndex = layer.load(2, {shade: [0.1, '#fff']});
        $.ajax({
            url: "../fwhs/updateLjz",
            type: "post",
            traditional: true,
            data: {
                fwHsIndexList: updateLjzParamJson,
                fwDcbIndex: fwDcbIndex
            },
            success: function (data) {
                layer.close(loadIndex);
                if (data && data.success) {
                    var index = parent.layer.getFrameIndex(window.name);
                    layer.closeAll();
                    parent.layer.msg(data.msg);
                    parent.layer.close(index);
                } else {
                    layer.alert("修改失败")
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }
}

function addFwhs(fwDcbIndex){
    toView(getIP()+'/fwhs/info?fwDcbIndex=' + fwDcbIndex,{tabname:"户室信息"});
}

function addYcfwhs(fwDcbIndex){
    toView(getIP()+'/fwychs/info?fwDcbIndex=' + fwDcbIndex,{tabname:"预测户室信息"});
}

function fwhsUpdate(data) {
    toView(getIP()+'/fwhs/info?fwHsIndex=' + data.fw_hs_index + "&fwDcbIndex=" + data.fw_dcb_index,{tabname:"户室信息"});
}

function fwychsUpdate(data){
    toView(getIP()+'/fwychs/info?fwDcbIndex=' + data.fw_dcb_index + "&fwHsIndex=" + data.fw_hs_index,{tabname:"预测户室信息"});
}

//户室变更历史关系展示
function fwHsHistory(data) {
    $.ajax({
        url: "../fwhs/checkbgjl",
        type: "post",
        traditional: true,
        data: {
            fwHsIndex: data.fw_hs_index
        },
        success: function (newData) {
            if (newData && newData.success) {
                layer.open({
                    type: 2,
                    title: "户室变更历史记录",
                    area: ['960px', '500px'],
                    content: "../fwhs/hsbghistoryview?bdcdyh=" + data.bdcdyh+"&fwHsIndex="+data.fw_hs_index
                    , end: function (index, layero) {
                        refreshMainPage();
                    }
                });
            } else {
                layer.msg("此户室没有进行过变更");
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });
}


// 批量上传户室图
function hstToManyHsView(data) {
    var fwHsIndexList = [];
    for (var i = 0; i < data.length; i++) {
        fwHsIndexList.push(data[i].fw_hs_index);
    }
    // 打开合并页面
    layer.open({
        type: 2,
        title: "户室图上传",
        area: ['960px', '450px'],
        content: "../hst/fwhsttomanyhsview?fwHsIndexList=" + fwHsIndexList
    });
}