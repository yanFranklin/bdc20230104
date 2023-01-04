/*
* 主要用于查看和调用匹配落宗状态js*/
//用于跳转到匹配界面
/**
 * @param checkdata 查看数据
 * @param xztzlx 选择台账类型
 * @param lx 类型 "check" 查看，此时不展示匹配页面的创建按钮
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 跳转到查看匹配页面
 * @date : 2020/4/15 14:51
 */
function checkPp(checkdata, xztzlx,lx) {
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
        console.log(datas[i]);
        console.log(datas[i].bdcqzh);
        var ybdcqz = yxdata.ybdcqz;
        if (ybdcqz === undefined) {
            yxdata["ybdcqz"] = yxdata.bdcqzh;
        }
        data.push(yxdata);
    }
    console.log(JSON.stringify(data));
    removeModal();
    showMatchData(JSON.stringify(data), xztzlx,lx);
}

//匹配界面
function showMatchData(data, xztzlx, lx) {
    var index = parent.layer.open({
        type: 2,
        title: "数据匹配",
        area: ['1150px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: getContextPath() + '/hefei/query/matchData.html?xztzlx=' + xztzlx + "&jbxxid=" + jbxxid + "&lx=" + lx,
        success: function (layero, index) {
            sessionStorage.setItem('matchData' + jbxxid, data);
        }

    });
    parent.layer.full(index);
}

//展现匹配页面
function showMatchPage(elem,jbxxid,xztzlx) {
    getReturnData("/gwc/listGwcData", {jbxxid: jbxxid}, "GET", function (data) {
        checkPp(data,xztzlx,'');
    }, function (xhr) {
        removeModal();
        addGwc();
        delAjaxErrorMsg(xhr);
    });
    remove(elem,5,'',true);
}

function reloadMatch(elem,jbxxid,xztzlx,qjgldm) {
    $.ajax({
        url: getContextPath() + "/matchData/autodismatchDyh?xnxmid=" + xnxmid + "&jbxxid=" + jbxxid +"&qjgldm="+qjgldm,
        type: 'POST',
        async: false,
        contentType: "application/json",
        success: function (data) {
            //跳转匹配界面
            showMatchPage(elem,jbxxid,xztzlx)
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}