/*
* 主要用于查看和调用匹配落宗状态js*/
//用于跳转到匹配界面

//匹配状态查看存在多条匹配关系
function checkMullzPp(checkdata, xztzlx,lx,type){
    showConfirmDialog("提示", "项目存在多条匹配关系，是否重新匹配？", "reloadMatch", "'" + JSON.stringify(checkdata) + "','" + xztzlx + "','" + lx + "','" + type + "','" + checkdata[0].xmid + "'");


}
/**
 * @param checkdata 查看数据
 * @param xztzlx 选择台账类型
 * @param lx 类型 "check" 查看，此时不展示匹配页面的创建按钮
 * @param type 1 展示单元号和土地证 2 不展示土地证tab页
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 跳转到查看匹配页面
 * @date : 2020/4/15 14:51
 */
function checkPp(checkdata, xztzlx,lx,type) {
    layer.closeAll();
    var data = [];
    var datas = [];
    if (checkdata.constructor === Array) {
        datas = checkdata
    } else {
        datas = JSON.parse(checkdata);
    }
    for (var i = 0; i < datas.length; i++) {
        var yxdata = datas[i];
        var ybdcqz = yxdata.ybdcqz;
        if (ybdcqz === undefined) {
            if(xztzlx == "2") {
                yxdata["ybdcqz"] = yxdata.bdcqzh;
            } else if(xztzlx == "3") {
                yxdata["ybdcqz"] = yxdata.cfwh;
            }
        }
        data.push(yxdata);
    }
    showMatchData(JSON.stringify(data), xztzlx,lx,type);
    removeModal();
}

//存在多条匹配关系,自动取消匹配，跳转匹配界面重新匹配
function reloadMatch(checkdata, xztzlx,lx,type,xnxmid) {
    $.ajax({
        url: getContextPath() + "/matchData/autodismatchDyh?xnxmid=" + xnxmid + "&jbxxid=" + jbxxid,
        type: 'POST',
        async: false,
        contentType: "application/json",
        success: function (data) {
            //跳转匹配界面
            checkPp(checkdata, xztzlx,lx,type);
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });

}

//匹配界面
function showMatchData(data, xztzlx, lx, type) {
    var matchlayer =layer;
    //确定父页面是否为购物车页面
    if (parent && $(parent).find('.bdc-gwc-layer')) {
        matchlayer =parent.layer;
    }
    var index = matchlayer.open({
        type: 2,
        title: "数据匹配",
        area: ['1150px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: getContextPath() + '/nantong/query/matchData.html?xztzlx=' + xztzlx + "&jbxxid=" + jbxxid + "&lx=" + lx + "&type=" + type,
        success: function (layero, index) {
            sessionStorage.setItem('matchData' + jbxxid, data);
        }

    });
    matchlayer.full(index);
}