var sortedYhsList = [];

function hbCheck(data) {
    var flag = {};
    var hsIndexList = [];
    for (var i = 0; i < data.length; i++) {
        var hsindex = data[i].fw_hs_index;
        hsIndexList.push(hsindex)
    }
    //逻辑调整为后台数据判断
    $.ajax({
            url: "../check/hbCheck",
            // dataType: "json",
            contentType: "application/json",
            // traditional: true,
            type: "POST",
            data: JSON.stringify(hsIndexList),
            async: false,
            success: function (data) {
                console.log(data);
                if (data) {
                    flag = data;
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr, this)
            }
        }
    )
    return flag;
}

function getDataList(data) {
    var itemList = [];
    for (var i = 0; i < data.length; i++) {
        var hsindex = data[i].fw_hs_index;
        var item = {};
        item.checkBoxElem = $("input[data-index='" + hsindex + "']");
        item.tdElem = $("input[data-index='" + hsindex + "']").parents("td");
        item.trElem = $("input[data-index='" + hsindex + "']").parents("tr");
        itemList.push(item);
    }
    return itemList;
}

function checkIsSameDyh(itemList) {
    var dyh = itemList[0].checkBoxElem.attr("data-dyh");
    for (var i = 0; i < itemList.length; i++) {
        if (dyh != itemList[i].checkBoxElem.attr("data-dyh")) {
            return false;
        }
    }
    return true;
}

function checkIsSameTr(itemList) {
    var trIndex = itemList[0].trElem.attr("data-index");
    for (var i = 0; i < itemList.length; i++) {
        if (trIndex != itemList[i].trElem.attr("data-index")) {
            return false;
        }
        if (itemList[i].trElem.attr("rowspan") > 1) {
            return false;
        }
    }
    return true;
}

function checkIsSameTd(itemList) {

    //判断data-key的最后一位是否相同，相同说明在上下列
    var flag = true;
    //按照TR index 排序
    itemList.sort(function (a, b) {
        var aTdDataKey = a.tdElem.attr("data-key");
        var bTdDataKey = b.tdElem.attr("data-key");
        var aLastNum = aTdDataKey.substr(aTdDataKey.lastIndexOf("-") + 1, aTdDataKey.length);
        var bLastNum = bTdDataKey.substr(bTdDataKey.lastIndexOf("-") + 1, bTdDataKey.length);
        return aLastNum - bLastNum;
    })
    sortedYhsList = itemList;
    for (var i = 0; i < itemList.length - 1; i++) {
        var thisTdDataKey = itemList[i].tdElem.attr("data-key");
        var nextTdDataKey = itemList[i + 1].tdElem.attr("data-key");
        var thisTdDataKey = thisTdDataKey.substr(thisTdDataKey.lastIndexOf("-") + 1, thisTdDataKey.length);
        var nextTdDataKey = nextTdDataKey.substr(nextTdDataKey.lastIndexOf("-") + 1, nextTdDataKey.length);
        var lim = nextTdDataKey - thisTdDataKey;
        var colspan = itemList[i].tdElem.attr("colspan");
        if (colspan) {
            lim = lim - colspan + 1;
        }
        if (lim != 0) {
            flag = false;
            break;
        }
    }
    return flag;
}

function checkIsClosedTr(itemList) {
    var flag = true;
    //按照TR index 排序
    itemList.sort(function (a, b) {
        var aTrIndex = a.trElem.attr("data-index");
        var bTrIndex = b.trElem.attr("data-index");
        return aTrIndex - bTrIndex;
    })
    sortedYhsList = itemList;
    for (var i = 0; i < itemList.length - 1; i++) {
        var thisTrIndex = itemList[i].trElem.attr("data-index");
        var nextTrIndex = itemList[i + 1].trElem.attr("data-index");
        // 把合并单元格计算进去
        var rowspan = itemList[i].trElem.attr("rowspan");
        var lim = nextTrIndex - thisTrIndex;
        if (rowspan) {
            lim = lim - rowspan + 1;
        }
        if (lim != 1) {
            flag = false;
            break;
        }
    }
    return flag;
}


function checkIsCloseTd(itemList) {
    var flag = true;
    //按照TR index 排序
    itemList.sort(function (a, b) {
        var aTdDataKey = a.tdElem.attr("data-key");
        var bTdDataKey = b.tdElem.attr("data-key");
        var aLastNum = aTdDataKey.substr(aTdDataKey.lastIndexOf("-") + 1, aTdDataKey.length);
        var bLastNum = bTdDataKey.substr(bTdDataKey.lastIndexOf("-") + 1, bTdDataKey.length);
        return aLastNum - bLastNum;
    })
    sortedYhsList = itemList;
    for (var i = 0; i < itemList.length - 1; i++) {
        var thisTdDataKey = itemList[i].tdElem.attr("data-key");
        var nextTdDataKey = itemList[i + 1].tdElem.attr("data-key");
        var thisTdDataKey = thisTdDataKey.substr(thisTdDataKey.lastIndexOf("-") + 1, thisTdDataKey.length);
        var nextTdDataKey = nextTdDataKey.substr(nextTdDataKey.lastIndexOf("-") + 1, nextTdDataKey.length);
        var lim = nextTdDataKey - thisTdDataKey;
        var colspan = itemList[i].tdElem.attr("colspan");
        if (colspan) {
            lim = lim - colspan + 1;
        }
        if (lim != 1) {
            flag = false;
            break;
        }
    }
    return flag;
}