/**
 * 存量房转移抵押统计 js
 */
var table;
layui.use([ 'table'], function () {
    table = layui.table;
});

function dcqb(){
    var dataString = JSON.stringify(queryObject);

    if (dataString === '{}') {
        warnMsg("请先查询数据");
        return;
    }

    if (!queryObject || isNullOrEmpty(queryObject.cxdh)) {
        warnMsg("请先查询数据");
        return;
    }


    var tempForm = $("<form>");
    tempForm.attr("id", "tempForm1");
    tempForm.attr("style", "display:none");
    tempForm.attr("target", "export");
    tempForm.attr("method", "post");
    tempForm.attr("action", '/realestate-inquiry-ui/rest/v1.0/tjjg/szsc/export');

    var dataInput = $("<input>");
    dataInput.attr("type", "hidden");
    dataInput.attr("name", "dataString");
    dataInput.attr("value", dataString);
    tempForm.append(dataInput);

    var dataInputSelect = $("<input>");
    dataInputSelect.attr("type", "hidden");
    dataInputSelect.attr("name", "data");
    dataInputSelect.attr("value", "");
    tempForm.append(dataInputSelect);

    // 保存Excel导出日志
    saveDetailLog(rzlx + "_EXPORT", rzmc + "-导出Excel", {"DCNR": "全部数据"});

    tempForm.on("submit", function () {
    });
    tempForm.trigger("submit");
    $("body").append(tempForm);
    tempForm.submit();
    $("tempForm1").remove();
}

//表格加载完操作
function loadComplete() {
    //翻转
    var reverseList = ['ZL'];
    reverseTableCell(reverseList);

    var obj = table.cache.pageTable;
    var szscMap = {}, dyszscMap = {};

    var zyItemList = [], dyItemList = [];
    $.each(obj, function(index, value){
        if(isNotBlank(value.CJSJ) && isNotBlank(value.SZSJ)){
            zyItemList.push({
                kssj: $("#kssj").val(), xmid: value.XMID, cjsj: value.CJSJ, szsj: value.SZSJ,
            });
        }
        if(isNotBlank(value.DYCJSJ) && isNotBlank(value.DYSZSJ)){
            dyItemList.push({
                kssj: $("#kssj").val(), xmid: value.XMID, cjsj: value.DYCJSJ, szsj: value.DYSZSJ,
            });
        }
    });

    if(zyItemList.length > 0){
        computeSzsc(zyItemList).done(function(data){
            szscMap = data;
        });
    }

    if(dyItemList.length > 0){
        computeSzsc(dyItemList).done(function(data){
            dyszscMap = data;
        });
    }

    // 将缮证时长回写缓存中
    $.each(obj, function(index, value){
        if(isNotBlank(value.CJSJ) && isNotBlank(value.SZSJ)){
            var szsc = szscMap[value.XMID];
            console.info(szsc);
            value.XMID = isNotBlank(szsc) ? szsc : "";
        }else{
            value.XMID = "";
        }
        if(isNotBlank(value.DYCJSJ) && isNotBlank(value.DYSZSJ)){
            var szsc = dyszscMap[value.DYSZSC];
            console.info(szsc);
            value.DYSZSC = isNotBlank(szsc) ? szsc : "";
        }else{
            value.DYSZSC = "";
        }
    });
    table.cache.pageTable = obj;
    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        if ($(getTd[i]).attr('data-field') == "XMID") {
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            var key = getTdCell[0].innerText;
            var value = szscMap[key];
            $(getTd[i]).children('div').empty();
            if (isNotBlank(value)) {
                $(getTd[i]).children('div').append(value);
            }
        }
        if($(getTd[i]).attr('data-field') == "DYSZSC"){
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            var key = getTdCell[0].innerText;
            var value = dyszscMap[key];
            $(getTd[i]).children('div').empty();
            if (isNotBlank(value)) {
                $(getTd[i]).children('div').append(value);
            }
        }
    }
}

function computeSzsc(computeItemList){
    var deferred = $.Deferred();
    if(computeItemList.length > 0){
        $.ajax({
            url:"/realestate-inquiry-ui/rest/v1.0/tjjg/count/szsc/list",
            type: "POST",
            async: false,
            contentType: "application/json",
            data: JSON.stringify(computeItemList),
            success: function (data) {
                deferred.resolve(data);
            },
            error: function (xhr, status, error) {
                deferred.reject();
            }
        });
    }else{
        deferred.resolve("");
    }
    return deferred;
}