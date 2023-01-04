//数据交互
function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

function getDataByAjax(_path, _param, _type, _fn, async) {
    var _url = getContextPath() + _path;
    var getAsync = false;
    if (async) {
        getAsync = async
    }
    $.ajax({
        url: _url,
        type: _type,
        async: getAsync,
        contentType: 'application/json;charset=utf-8',
        data: _param,
        success: function (data) {
            _fn.call(this, data, data);
        },
        error: function (err) {
            console.log(err);
        }
    });
}


//比较起止时间
function completeDate(date1, date2) {
    var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    if (oDate1.getTime() > oDate2.getTime()) {
        return true;
    } else {
        return false;
    }
}

var dyatjDayUrl = getIP() + "/realestate-inquiry-ui/static/printModel/dyatjDay.fr3";
var dyatjMonthUrl = getIP() + "/realestate-inquiry-ui/static/printModel/dyatjMonth.fr3";

// 打印抵押统计结果
function printTjxx(tjxxParam, dylx, fr3Url) {
    // 先保存打印统计信息到redis
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/dya/tjxx/redis/" + dylx,
        type: "POST",
        data: JSON.stringify(tjxxParam),
        contentType: 'application/json',
        success: function (data) {
            if (data) {
                var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/dyaTj/" + dylx + "/xml?redisKey=" + data;
                print(fr3Url, dataUrl, false);
            } else {
                failMsg("打印出错，请重试！");
            }
        },
        error: function () {
            failMsg("打印出错，请重试！");
        }
    });
}

// 处理数据信息，保留小数后四位
function dealData(data) {
    $.each(data, function (index, item) {
        if (item.dyaMjSum) {
            item.dyaMjSum = item.dyaMjSum.toFixed(4);
        }
        if (item.dyaJeSum) {
            item.dyaJeSum = item.dyaJeSum.toFixed(4);
        }
        if (item.validDyaMjSum) {
            item.validDyaMjSum = item.validDyaMjSum.toFixed(4);
        }
        if (item.validDyaJeSum) {
            item.validDyaJeSum = item.validDyaJeSum.toFixed(4);
        }
        if (item.yearDyaMjSum) {
            item.yearDyaMjSum = item.yearDyaMjSum.toFixed(4);
        }
        if (item.yearDyaJeSum) {
            item.yearDyaJeSum = item.yearDyaJeSum.toFixed(4);
        }
        if (item.monthDyaMjSum) {
            item.monthDyaMjSum = item.monthDyaMjSum.toFixed(4);
        }
        if (item.monthDyaJeSum) {
            item.monthDyaJeSum = item.monthDyaJeSum.toFixed(4);
        }
        if (item.dayDyaMjSum) {
            item.dayDyaMjSum = item.dayDyaMjSum.toFixed(4);
        }
        if (item.dayDyaJeSum) {
            item.dayDyaJeSum = item.dayDyaJeSum.toFixed(4);
        }
    });
}