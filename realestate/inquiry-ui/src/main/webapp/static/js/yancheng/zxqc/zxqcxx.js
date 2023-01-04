/**
 * 常州注销清册信息查询台账
 * 需求：43176
 */

var selectedData = new Array();

/**
* 列表加载完成回调事件
* @param res 当前查询结果
*/
function tableHasLoad(res) {
    $("#ytj").text("已添加(" + selectedData.length + ")");
}

/**
 * 多选添加
 */
function tj(obj, data) {
    if ($.isEmptyObject(checkeddata)) {
        warnMsg(" 请选择需要添加的记录！");
        return;
    }

    var dataArray = new Array();
    $.each(checkeddata, function (key, value) {
        dataArray.push(value);
    });

    var notAdded = new Array();
    dataArray.forEach(function (checkedItem) {
        var hasAdded = false;
        selectedData.forEach(function (selectedItem) {
            if(checkedItem.XMID == selectedItem.XMID) {
                hasAdded = true;
            }
        });

        if(!hasAdded) {
            notAdded.push(checkedItem);
        }
    });

    if(notAdded && notAdded.length > 0) {
        notAdded.forEach(function (item) {
            selectedData.push(item);
        });

        if(notAdded.length < dataArray.length) {
            successMsg("未添加记录已添加成功");
        } else {
            successMsg("添加成功");
        }
    } else {
        warnMsg("所选记录都已添加，无需重复添加");
    }
    $("#ytj").text("已添加(" + selectedData.length + ")");
}

/**
 * 添加
 */
function add(obj, data) {

    if(!data) {
        return;
    }

    var hasAdded = false;
    if(selectedData && selectedData.length > 0) {
        selectedData.forEach(function (item) {
            if(item.XMID === data.XMID) {
                warnMsg("当前记录已添加，无需重复添加");
                hasAdded = true;
            }
        });
    }
    if(hasAdded === true) {
        return;
    }

    selectedData.push(data);
    $("#ytj").text("已添加(" + selectedData.length + ")");
    successMsg("添加成功");
}

/**
 * 移除
 */
function remove(obj, data) {
    layer.confirm("是否确认移除当前记录？", {
        btn: ["确认", "取消"],
    }, function (index, layero) {
        selectedData.forEach(function (value, index) {
            if (value.XMID == data.XMID) {
                selectedData.splice(index, 1);
                return;
            }
        });
        $("#ytj").text("已添加(" + selectedData.length + ")");
        successMsg("移除成功！");
    }, function (index) {
        return;
    });
}

/**
 * 查看已添加记录
 */
function ytj(obj, data) {

    var index = layer.open({
        type: 2,
        title: "注销清册",
        area: ['960px','600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: "/realestate-inquiry-ui/changzhou/zxqc/zxqc.html",
        success: function () {
            layui.data('zxqc', {key:"data", value: JSON.stringify(selectedData)});
        },
        end: function(){
        }
    });
}

