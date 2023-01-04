// 字典
var zdMap;
$.ajax({
    url: '/realestate-register-ui/rest/v1.0/djbxx/qlxx/zd',
    type: "GET",
    async: false,
    dataType: "json",
    success: function (data) {
        if (data) {
            zdMap = data;
        }
    }
});


function convertEntityToMc(data) {
    // 证件种类
    if (data.qlrzjzl) {
        for (var i = 0; i < zdMap.zjzl.length; i++) {
            if (zdMap.zjzl[i].DM == data.qlrzjzl) {
                data.qlrzjzl = zdMap.zjzl[i].MC;
                break;
            }
        }
    }
    // 登记类型
    if (data.djlx) {
        for (var i = 0; i < zdMap.djlx.length; i++) {
            if (zdMap.djlx[i].DM == data.djlx) {
                data.djlx = zdMap.djlx[i].MC;
                break;
            }
        }
    }
    // 房屋性质
    if (data.fwxz) {
        for (var i = 0; i < zdMap.fwxz.length; i++) {
            if (zdMap.fwxz[i].DM == data.fwxz) {
                data.fwxz = zdMap.fwxz[i].MC;
                break;
            }
        }
    }
    // 规划用途
    if (data.ghyt) {
        for (var i = 0; i < zdMap.fwyt.length; i++) {
            if (zdMap.fwyt[i].DM == data.ghyt) {
                data.ghyt = zdMap.fwyt[i].MC;
                break;
            }
        }
    }

    // 房屋结构
    if (data.fwjg) {
        for (var i = 0; i < zdMap.fwjg.length; i++) {
            if (zdMap.fwjg[i].DM == data.fwjg) {
                data.fwjg = zdMap.fwjg[i].MC;
                break;
            }
        }
    }
    //预告登记种类
    if (data.ygdjzl) {
        for (var i = 0; i < zdMap.ygdjzl.length; i++) {
            if (zdMap.ygdjzl[i].DM == data.ygdjzl) {
                data.ygdjzl = zdMap.ygdjzl[i].MC;
                break;
            }
        }
    }
}