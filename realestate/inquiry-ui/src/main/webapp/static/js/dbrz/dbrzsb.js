/**
 * 点击明细
 */
function mx(obj, data) {
    var index = layer.open({
        type: 2,
        title: "登簿日志明细",
        area: ['1300px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: "/realestate-inquiry-ui/view/dbrz/dbrzmx.html?id=" + data.ID
    });
    layer.full(index);
}

/*
* 点击登簿详情
* */
function dbxq(obj, data) {
    var index = layer.open({
        type: 2,
        title: "登簿日志详情",
        area: ['1300px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: "/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=dbrzxq&dbrzid=" + data.ID
    });
    layer.full(index);
}

/**
 * 点击上报
 */
function accessUpload() {
    addModelDb();
    if (checkeddata.length < 1) {
        removeModalDb();
        layer.msg("请先勾选数据");
        return false;
    }
    for (var i = 0; i < checkeddata.length; i++) {
        /*
        * 即使登簿日志已经成功上报了也可以继续选择区县上报
        * */
        // if (checkeddata[i]["CGBS"] !== '1' && checkeddata[i]["SJCGBS"] != '1') {
        $.ajax({
            url: '/realestate-inquiry-ui/dbrz/nt',
            dataType: "json",
            type: "GET",
            // async: false,
            data: {'date': checkeddata[i].JRRQ, 'qxdm': checkeddata[i].XZQDM},
            success: function (data) {
                tableReload('pageTable', {data: JSON.stringify(queryObject)}, '/realestate-inquiry-ui/dtcx/list/result');
            }
        });
        // }
    }
    layer.msg("上报成功");
    removeModalDb();
}

/**
 * 点击按日期上报
 */
function accessUploadDate() {
    addModelDb();
    if ($("#ksrq").val() && $("#jsrq").val()
        && $("#ksrq").val() === $("#jsrq").val()) {
        $.ajax({
            url: '/realestate-inquiry-ui/dbrz/nt',
            dataType: "json",
            type: "GET",
            async: false,
            data: {'date': $("#ksrq").val(), 'qxdm': $("#xzqdm").val()},
            success: function (data) {
                tableReload('pageTable', {data: JSON.stringify(queryObject)}, '/realestate-inquiry-ui/dtcx/list/result');
                layer.msg("上报成功");
            },
            error: function () {
                layer.msg("上报失败,请联系管理员");
            }
        });
    } else {
        layer.alert("请输入正确的开始日期和结束日期");
    }
    removeModalDb();
}

/*
* 明细预览--必须选择当天时间和区县才允许查看
* */
function mxyl() {
    addModelDb();
    if ($("#ksrq").val() && isNotBlank($("#xzqdm").val())) {
        var index = layer.open({
            type: 2,
            title: "登簿日志明细",
            area: ['1300px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: "/realestate-inquiry-ui/view/dbrz/dbrzmxyl.html?date=" + $("#ksrq").val() + "&qxdm=" + $("#xzqdm").val()
        });
        layer.full(index);
    } else {
        layer.alert("请输入正确的开始日期以及区县");
    }
    removeModalDb();
}

/*
* 登簿详情预览--必须选择当天时间和区县才允许查看
* */
function dbxqyl() {
    addModelDb();
    if ($("#ksrq").val() && isNotBlank($("#xzqdm").val())) {
        var index = layer.open({
            type: 2,
            title: "登簿日志详情",
            area: ['1300px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: "/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=dbrzxqyl&cxrq=" + Format(formatDate($("#ksrq").val()), "yyyy-MM-dd") + "&qxdm=" + $("#xzqdm").val()
        });
        layer.full(index);
    } else {
        layer.alert("请输入正确的开始日期以及区县");
    }
    removeModalDb();
}


//查询遮罩
function addModelDb() {
    var modalHtml = '<div id="waitModalLayer">' +
        '<p class="bdc-wait-content">' +
        '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>' +
        '<span>操作中</span>' +
        '</p>' +
        '</div>';
    $('body').append(modalHtml);
}

// 去除遮罩
function removeModalDb() {
    $("#waitModalLayer").remove();
}









