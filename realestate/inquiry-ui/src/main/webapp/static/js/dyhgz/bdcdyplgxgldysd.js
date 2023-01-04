//新增
function xz(obj, data){
    if(!isNotBlank(data.DYSDID)){
        layer.alert("缺失必要参数DYSDID");
        return false;
    }
    getReturnData("/rest/v1.0/dyhgz/dysdxx?dysdid=" + data.DYSDID, {}, "GET", function (result) {
       
        var yxidData = JSON.parse(sessionStorage.getItem("dyhgz_yxid"));
        if(yxidData.indexOf(data.DYSDID) >-1){
            layer.alert("新增的单元锁定已存在,请勿重复添加");
            return false;
        }
        var currentDysd =result;
        var xmsdData = JSON.parse(sessionStorage.getItem("dyhgz_yxxmsdxx"));
        if(xmsdData) {
            if (xmsdData.dysdxx && xmsdData.dysdxx.length > 0) {
                for (var i = 0; i < currentDysd.length; i++) {
                    xmsdData.dysdxx.push(currentDysd[i]);
                    yxidData.push(currentDysd[i].dysdid);
                }
            }else{
                xmsdData.dysdxx=currentDysd;
                for (var i = 0; i < currentDysd.length; i++) {
                    yxidData.push(currentDysd[i].dysdid);
                }
            }
            xmsdData.yxcount =xmsdData.xmxx.length+xmsdData.dysdxx.length+xmsdData.ygxx.length;
            sessionStorage.setItem("dyhgz_yxxmsdxx",JSON.stringify(xmsdData));
            sessionStorage.setItem("dyhgz_yxid",JSON.stringify(yxidData));
            successMsg("添加成功");
            parent.layer.closeAll();
            parent.window.location.reload();
        }

    }, function (error) {
        delAjaxErrorMsg(error);
    });

}