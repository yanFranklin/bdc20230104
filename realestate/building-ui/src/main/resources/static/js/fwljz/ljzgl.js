/**
 * 导入楼盘表
 * @param fwDcbIndex
 */
function importLpb(fwDcbIndex,qjgldm) {
    var index = layer.open({
        type: 2,
        title: "导入楼盘表",
        area: ['410px', '310px'],
        fixed: false, //不固定
        content: '../import/lpb?fwDcbIndex=' + fwDcbIndex+"&qjgldm="+qjgldm
        , end: function (index, layero) {
            refreshMainPage();
            return false;
        }
    });
}

/**
 * 导入楼盘表
 * @param fwDcbIndex
 */
function importYcLpb(fwDcbIndex,qjgldm) {
    var index = layer.open({
        type: 2,
        title: "导入楼盘表",
        area: ['410px', '310px'],
        fixed: false, //不固定
        content: '../import/yclpb?fwDcbIndex=' + fwDcbIndex+"&qjgldm="+qjgldm
        , end: function (index, layero) {
            refreshMainPage();
            return false;
        }
    });
}

/**
 * 导出楼盘表
 * @param fwDcbIndex
 */
function exportLpb(fwDcbIndex,qjgldm) {
    window.location.href = '../export/lpb?fwDcbIndex=' + fwDcbIndex+"&qjgldm="+qjgldm;
}

/**
 * 导入实测建筑面积
 * @param fwDcbIndex
 */
function importScjzmj(fwDcbIndex,qjgldm) {
    var index = layer.open({
        type: 2,
        title: "导入实测面积",
        area: ['410px', '210px'],
        fixed: false, //不固定
        content: '../import/scmj?fwDcbIndex=' + fwDcbIndex+"&qjgldm="+qjgldm
        , end: function (index, layero) {
            refreshMainPage();
            return false;
        }
    });
}

/**
 * 构建楼盘表
 */
function buildLpb(data) {
    var index = layer.open({
        type: 2,
        title: "构建楼盘表",
        area: ['960px', '360px'],
        fixed: false, //不固定
        content: '../ljz/buildlpbview?fwDcbIndex=' + data.fw_dcb_index + '&bdcdyfwlx=' + data.bdcdyfwlx+"&qjgldm="+data.qjgldm
        , end: function (index, layero) {
            refreshMainPage();
            return false;
        }
    });
}

function yczsc(fwDcbIndex,qjgldm) {
    addModel();
    $.ajax({
        url: "../yctosc/ychstoschs",
        contentType: "application/json;charset=utf-8",
        type: "GET",
        data: {
            fwDcbIndex: fwDcbIndex,
            qjgldm:qjgldm
        },
        success: function (data) {
            refreshMainPage();
            removeModal();
            layer.msg(data.msg);
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr, this)
        }
    });
}

/**
 * 预测实测关联
 * @param fwDcbIndex
 */
function ycscgl(fwDcbIndex,qjgldm) {
    toView(getIP() + '/fwhsgl/ycsc?fwDcbIndex=' + fwDcbIndex+"&qjgldm="+qjgldm, {tabname: "预测实测关联"});
}

/**
 * 坐落刷新
 * @param fwDcbIndex
 */
function zlsx(fwDcbIndex,qjgldm) {
    addModel();
    $.ajax({
        url: "../zlsx/ljz/byconfig",
        type: "GET",
        data: {
            fwDcbIndex: fwDcbIndex,
            qjgldm:qjgldm
        },
        success: function (data) {
            removeModal();
            layer.msg(data.msg);
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr, this)
        }
    });
}

/**
 * 计算逻辑幢建筑面积
 * @param fwDcbIndex
 */
function jsLjzJzmj(fwDcbIndex,qjgldm) {
    addModel();
    $.ajax({
        url: "../calculated/ljzzjmj/byconfig",
        type: "GET",
        data: {
            fwDcbIndex: fwDcbIndex,
            qjgldm:qjgldm
        },
        success: function (data) {
            removeModal();
            layer.msg(data.msg);
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr, this)
        }
    });
}

/**
 * 计算逻辑幢分摊土地面积
 * @param fwDcbIndex
 */
function jsLjzFttdmj(fwDcbIndex,qjgldm) {
    var index = layer.open({
        type: 2,
        title: "分摊土地面积计算",
        area: ['960px', '500px'],
        fixed: false, //不固定
        content: '../calculated/lpbfttdview?fwDcbIndex=' + fwDcbIndex+"&qjgldm="+qjgldm
        , end: function (index, layero) {
            refreshMainPage();
            return false;
        }
    });
}

//推送预测逻辑幢给一体化
function tsycLjz(fwDcbIndex) {
    getReturnData("/ljz/tsycljz", {fwDcbIndex: fwDcbIndex}, "GET", function (data) {
        removeModal();
        if (data) {
            if (data.success) {
                ityzl_SHOW_SUCCESS_LAYER("已推送");
            } else {
                ityzl_SHOW_WARN_LAYER("推送失败" + data.fail.message);
            }
        } else {
            ityzl_SHOW_WARN_LAYER("推送失败");
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}