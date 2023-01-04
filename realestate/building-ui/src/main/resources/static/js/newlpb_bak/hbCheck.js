var sortedYhsList = [];
function hbCheckFun(data){
    var flag = false;
    // 判断 TR 是否为同一个
    var itemList = getDataList(data);
    var sameTr = checkIsSameTr(itemList);
    // 判断是否是同一行
    if(sameTr){
        // 同一行   是否是相邻列
        var closedTd = checkIsCloseTd(itemList);
        // 判断是否连续列
        if(closedTd){
            // 连续列 判断是否 属于同一单元
            var sameDy = checkIsSameDyh(itemList);
            if(sameDy){
                flag = true;
                $.hsbg.hbfx = "1";
                // alert("横向连续数据");
            }else{
                // alert("false,同一行，连续,非同一单元数据");
            }
        }else{
            // alert("false,同一行，非连续列数据");
        }
    }else{
        // 不同行
        // 判断是否是同一列
        var sameTd = checkIsSameTd(itemList);
        if(sameTd){
            // 同一列 判断是否连续行
            var closedTr = checkIsClosedTr(itemList);
            if(closedTr){
                flag = true;
                $.hsbg.hbfx = "3";
                // alert("纵向连续数据");
            }else{
                // alert("false,纵向不连续数据");
            }
        }else{
            // alert("false,不同行，不同列数据");
        }
    }
    return flag;
}

function getDataList(data){
    var itemList = [];
    for(var i = 0 ; i < data.length ; i++){
        var hsindex = data[i].fw_hs_index;
        var item = {};
        item.checkBoxElem = $("input[data-index='"+hsindex+"']");
        item.tdElem = $("input[data-index='"+hsindex+"']").parents("td");
        item .trElem = $("input[data-index='"+hsindex+"']").parents("tr");
        itemList.push(item);
    }
    return itemList;
}

function checkIsSameDyh(itemList){
    var dyh = itemList[0].checkBoxElem.attr("data-dyh");
    for(var i = 0;i < itemList.length ;i++){
        if(dyh != itemList[i].checkBoxElem.attr("data-dyh")){
            return false;
        }
    }
    return true;
}

function checkIsSameTr(itemList){
    var trIndex = itemList[0].trElem.attr("data-index");
    for(var i = 0;i < itemList.length ;i++){
        if(trIndex != itemList[i].trElem.attr("data-index")){
            return false;
        }
        if(itemList[i].trElem.attr("rowspan") > 1){
            return false;
        }
    }
    return true;
}

function checkIsSameTd(itemList){
    var tdKey = itemList[0].tdElem.attr("data-key");
    for(var i = 0;i < itemList.length ;i++){
        if(tdKey != itemList[i].tdElem.attr("data-key")){
            return false;
        }
        if(itemList[i].tdElem.attr("colspan") > 1){
            return false;
        }
    }
    return true;
}

function checkIsClosedTr(itemList){
    var flag = true;
    //按照TR index 排序
    itemList.sort(function(a,b){
        var aTrIndex = a.trElem.attr("data-index");
        var bTrIndex = b.trElem.attr("data-index");
        return aTrIndex - bTrIndex;
    })
    sortedYhsList = itemList;
    for(var i = 0 ; i < itemList.length - 1 ; i++){
        var thisTrIndex = itemList[i].trElem.attr("data-index");
        var nextTrIndex = itemList[i+1].trElem.attr("data-index");
        // 把合并单元格计算进去
        var rowspan = itemList[i].trElem.attr("rowspan");
        var lim = nextTrIndex - thisTrIndex;
        if(rowspan){
            lim = lim - rowspan + 1;
        }
        if(lim != 1){
            flag = false;
            break;
        }
    }
    return flag;
}


function checkIsCloseTd(itemList){
    var flag = true;
    //按照TR index 排序
    itemList.sort(function(a,b){
        var aTdDataKey = a.tdElem.attr("data-key");
        var bTdDataKey = b.tdElem.attr("data-key");
        var aLastNum = aTdDataKey.substr(aTdDataKey.lastIndexOf("-")+1,aTdDataKey.length);
        var bLastNum = bTdDataKey.substr(bTdDataKey.lastIndexOf("-")+1,bTdDataKey.length);
        return aLastNum - bLastNum;
    })
    sortedYhsList = itemList;
    for(var i = 0 ; i < itemList.length - 1 ; i++){
        var thisTdDataKey = itemList[i].tdElem.attr("data-key");
        var nextTdDataKey = itemList[i+1].tdElem.attr("data-key");
        var thisTdDataKey = thisTdDataKey.substr(thisTdDataKey.lastIndexOf("-")+1,thisTdDataKey.length);
        var nextTdDataKey = nextTdDataKey.substr(nextTdDataKey.lastIndexOf("-")+1,nextTdDataKey.length);
        var lim = nextTdDataKey - thisTdDataKey;
        var colspan = itemList[i].tdElem.attr("colspan");
        if(colspan){
            lim = lim - colspan + 1;
        }
        if(lim != 1){
            flag = false;
            break;
        }
    }
    return flag;
}