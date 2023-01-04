//新增
function xz(obj, data){
    if(!isNotBlank(data.XMID)){
        layer.alert("缺失必要参数XMID");
        return false;
    }
    getReturnData("/rest/v1.0/dyhgz/xmxx?xmid=" + data.XMID + "&xmtype=2", {}, "GET", function (result) {
        var msg ="当前预告共关联了"+result.bdcXmDOList.length+"条数据,是否一并带入";
        layer.confirm(msg, {
            title: '提示',
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var yxidData = JSON.parse(sessionStorage.getItem("dyhgz_yxid"));
            if(yxidData.indexOf(data.XMID) >-1){
                layer.alert("新增的预告已存在,请勿重复添加");
                return false;
            }
            var currentXmxx =result.bdcXmDOList;
            var xmsdData = JSON.parse(sessionStorage.getItem("dyhgz_yxxmsdxx"));
            if(xmsdData) {
                if (xmsdData.ygxx && xmsdData.ygxx.length > 0) {
                    for (var i = 0; i < currentXmxx.length; i++) {
                        xmsdData.ygxx.push(currentXmxx[i]);
                        yxidData.push(currentXmxx[i].xmid);
                    }
                } else {
                    xmsdData.ygxx = currentXmxx;
                    for (var i = 0; i < currentXmxx.length; i++) {
                        yxidData.push(currentXmxx[i].xmid);
                    }
                }
                xmsdData.yxcount =xmsdData.xmxx.length+xmsdData.dysdxx.length+xmsdData.ygxx.length;
                sessionStorage.setItem("dyhgz_yxxmsdxx", JSON.stringify(xmsdData));
                sessionStorage.setItem("dyhgz_yxid", JSON.stringify(yxidData));
                successMsg("添加成功");
                parent.layer.closeAll();
                parent.window.location.reload();
            }
        });
    }, function (error) {
        delAjaxErrorMsg(error);
    });

}