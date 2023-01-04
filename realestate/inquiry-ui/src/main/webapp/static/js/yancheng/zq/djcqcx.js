/**
 * 征迁冻结产权查询功能
 */
var urlParam = $.getUrlParam('param');

// 被标记移除的记录
var ycjlArray = new Array();

/**
 * 列表加载完成回调事件
 * @param res 当前查询结果
 */
function tableHasLoad(res) {

    // 已移除记录背景标红
    if(res && res.content && ycjlArray && ycjlArray.length > 0) {
        res.content.forEach(function (v, index) {
            ycjlArray.forEach(function (value, i) {
                if (value.bdcdyh == v.BDCDYH && value.bdcqzh == v.BDCQZH && value.id == v.ID) {
                    $(".layui-table-body tr[data-index='" + index + "']").css("background", "red");
                }
            });
        });
    }
}

/**
 * 将指定记录移除，不放在冻结名单中
 */
function yc(obj, data) {
    layer.confirm("是否确认标记当前记录不冻结？<br>（说明：只针对全部冻结操作）", {
        btn: ["确认", "取消"],
    }, function (index, layero) {
        var item = {};
        item.id = data.ID;
        item.bdcqzh = data.BDCQZH;
        item.bdcdyh = data.BDCDYH;
        ycjlArray.push(item);
        $(obj.tr.selector).css("background", "red");
        successMsg("当前记录已标记不冻结！");
    }, function (index) {
        return;
    });
}

/**
 * 将移除的记录还原取消移除
 */
function hy(obj, data) {
    layer.confirm("是否确认还原当前记录标记？", {
        btn: ["确认", "取消"],
    }, function (index, layero) {
        ycjlArray.forEach(function (value, index) {
            if (value.bdcdyh == data.BDCDYH && value.bdcqzh == data.BDCQZH && value.id == data.ID) {
                ycjlArray.splice(index, 1);
                return;
            }
        });
        $(obj.tr.selector).css("background", "#fff");
        successMsg("当前记录已取消标记！");
    }, function (index) {
        return;
    });
}

/**
 * 单个冻结
 */
function dj(obj, data) {
    if(data.DJZT && data.DJZT == 1) {
        warnMsg("当前单元已冻结，无需重复冻结！");
        return;
    }

    var dataArray = new Array();
    dataArray.push(data);
    zxdj(dataArray);
}

/**
 * 批量冻结
 */
function pldj(obj, data) {
    var checkedData = layui.sessionData('allPageCheckedData');
    if ($.isEmptyObject(checkedData)) {
        warnMsg(" 请选择需要冻结的数据行！");
        return;
    }

    var dataArray = new Array();
    $.each(checkedData, function (key, value) {
        dataArray.push(value);
    });

    for (var i = 0; i < dataArray.length; i++) {
        if (1 == dataArray[i].DJZT) {
            warnMsg("勾选记录存在已冻结记录！");
            return;
        }
    }
    zxdj(dataArray);
}

/**
 * 执行冻结操作公共方法
 */
function zxdj(data) {
    djstate = ""
    var index = layer.open({
        type: 2,
        title: "单元冻结",
        area: ['960px','480px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: "/realestate-inquiry-ui/yancheng/zq/dydj.html",
        success: function () {
            var iframe = window['layui-layer-iframe' + index];
            iframe.setData(data);
        },
        end: function(){
            if("dj" == djstate) {
                tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
            }
        }
    });
}

// 操作状态标识
var state = "", djstate = "";

/**
 * 每次列表查询清空下全部冻结标识
 */
function beforeTableReload() {
    state = "";
}

/**
 * 全部冻结
 */
function qbdj(obj, data) {
    if ($.isEmptyObject(currentTablePageData)) {
        warnMsg("未查询到产权数据，无法冻结！");
        return;
    }

    if("qbdj" == state) {
        warnMsg("查询记录已全部冻结，无需重复冻结！");
        return;
    }

    state = "";
    var index = layer.open({
        type: 2,
        title: "单元冻结",
        area: ['960px','480px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: "/realestate-inquiry-ui/yancheng/zq/dydj.html?djlx=qbdj",
        success: function () {
            var iframe = window['layui-layer-iframe' + index];
            iframe.setData(ycjlArray, queryObject);
        },
        end: function(){
            if("qbdj" == state) {
                tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
            }
        }
    });
}

function setState(data) {
    state = data;
}

function setDjState(data) {
    djstate = data;
}

