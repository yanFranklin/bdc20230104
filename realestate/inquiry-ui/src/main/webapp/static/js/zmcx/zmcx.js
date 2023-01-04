/**
 * 证明查询 js
 */
var table;
layui.use([ 'table'], function () {
    table = layui.table;
});

// 表格加载完成后，执行的回调方法
var loadComplete = function loadComplete() {
    var obj = table.cache.pageTable;
    // 获取原不动产单元号
    var bdcdyhList = [];
    var ybdcdyhMap = [];
    $.each(obj, function(index, value){
        bdcdyhList.push(value.BDCDYH);
    });
    if(bdcdyhList.length > 0){
        queryYbdcdyh(bdcdyhList).done(function(data){
            if(data.length > 0){
                $.each(data, function(index, value){
                    ybdcdyhMap[value.bdcdyh] = value.ybdcdyh;
                    $.each(obj, function(i, v){
                        if(v.BDCDYH == value.bdcdyh) {
                            v.YBDCDYH = value.ybdcdyh;
                        }
                    });
                });
            }
        });
    }


    var getTd = $('.layui-table-main .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        // 格式化权属状态
        if ($(getTd[i]).attr('data-field') == "QSZT") {
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            var key = getTdCell[0].innerText;
            var value = formatQszt(key);
            $(getTd[i]).children('div').empty();
            if (isNotBlank(value)) {
                $(getTd[i]).children('div').append(value);
            }
        }
    }


    // 将原不动产单元号设置到表格中
    var getTr = $('.layui-table-main .layui-table tr');
    for (var i = 0; i < getTr.length; i++) {
        var getTrTd = getTr.eq(i).find("td");
        var ybcddyh = '';
        for (var j = 0; j < getTrTd.length; j++) {
            if ($(getTrTd[j]).attr('data-field') == "BDCDYH") {
                var getTdCell = $(getTrTd[j]).find('.layui-table-cell');
                var key = getTdCell[0].innerHTML.replace(/\s+/g, "");
                if (ybdcdyhMap[key]) {
                    ybcddyh = ybdcdyhMap[key];
                }
            }
        }
        for (var j = 0; j < getTrTd.length; j++) {
            if($(getTrTd[j]).attr('data-field') == "YBDCDYH"){
                var getTdCell = $(getTrTd[j]).find('.layui-table-cell');
                getTdCell[0].innerHTML = ybcddyh;
            }
        }
    }
}

// 查询原不动产单元号
function queryYbdcdyh(bdcdyhList){
    var deferred = $.Deferred();
    $.ajax({
        url:"/realestate-inquiry-ui/rest/v1.0/ybdcdyh/list",
        type: "POST",
        async: false,
        contentType: "application/json",
        data: JSON.stringify(bdcdyhList),
        success: function (data) {
            deferred.resolve(data);
        },
        error: function (xhr, status, error) {
            deferred.reject();
        }
    });
    return deferred;
}

// 格式化权属状态
function formatQszt(qszt){
    if (qszt == '临时') {
        return '<span class="" style="color:#1d87d1;">临时</span>';
    } else if (qszt == '现势'){
        return '<span class="" style="color:#32b032;">现势</span>'
    } else if (qszt == '历史') {
        return '<span class="" style="color:#f28943;">历史</span>'
    } else if (qszt == '终止') {
        return '<span class="" style="color:#f24b43;">终止</span>'
    } else {
        return '';
    }
}