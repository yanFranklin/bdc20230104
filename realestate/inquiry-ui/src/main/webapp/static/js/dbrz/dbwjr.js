/*
* 登簿未接入台账展现
* */

function plsb() {
    if (checkeddata.length === 0) {
        warnMsg('请勾选需要上报的数据！');
        return;
    }
    addModel();

    var xmidList = [];
    for (var i = 0; i < checkeddata.length; i++) {
        xmidList.push(checkeddata[i].XMID);
    }
    if (isNotBlank(xmidList)) {
        getReturnData("/dbrz/dbwjr/plsb", JSON.stringify(xmidList), "POST", function (data) {
            removeModal();
            successMsg("已重新上报")
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        })
    }
}