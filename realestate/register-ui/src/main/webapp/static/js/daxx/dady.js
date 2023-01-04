function djbDady(index) {
    console.log(dataArr);
    if (!isNullOrEmpty(index)) {
        var num = parseInt(index);
        dady(dataArr[num]);
    }
}
function dady(data) {
    var dadyInfo = tellTdFromBdc(data.xmid);

    if(dadyInfo.version == "haimen") {
        // 海门版本
        if(!dadyInfo || isNullOrEmpty(dadyInfo.hmDadyUrl)) {
            failMsg("未配置档案调用地址，请联系管理员!");
            return;
        }
        window.open(dadyInfo.hmDadyUrl + data.slbh);
    } else {
        // 南通版本
        if(dadyInfo.kfqFlag){
            var now = new Date();
            var dateStr = now.getFullYear() + "-" + (now.getMonth() + 1) + '-' + now.getDate();
            var keycode = data.slbh;
            var user = dadyInfo.userName;
            var key = hex_md5((keycode + user + "VIEWPIC" + dateStr).toUpperCase()).toUpperCase();
            var url = dadyInfo.kfqDadyUrl + "?keycode=" + keycode + "&user=" + user + "&key=" + key;
            window.open(url);
        }else{
            if (dadyInfo.XMLY == "2") {// 土地
                var url = dadyInfo.tdDadyUrl;
                url += "?userName=" + encodeURI(dadyInfo.userName);
                url += "&qlrmc=" + encodeURI(data.qlr ? data.qlr : "");
                url += "&tdzl=" + encodeURI(data.zl ? data.zl : "");
                url += "&tdzh=" + encodeURI(data.ytdzh ? data.ytdzh : "");
                url += "&djh=" + encodeURI(data.ybdcdyh ? data.ybdcdyh : "");
                url += "&password=";
                window.open(url);
            } else {// 不动产
                var now = new Date();
                var dateStr = now.getFullYear() + "-" + (now.getMonth() + 1) + '-' + now.getDate();
                var keycode = data.slbh;
                var user = dadyInfo.userName;
                var key = hex_md5((keycode + user + "VIEWPIC" + dateStr).toUpperCase()).toUpperCase();
                var url = dadyInfo.dadyUrl + "?keycode=" + keycode + "&user=" + user + "&key=" + key;
                window.open(url);
            }
        }

    }
}


function tellTdFromBdc(xmid) {
    var dataObj = {};
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/bdcdy/tellTdFromBdc?xmid=' + xmid,
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            dataObj = data;
        }
    });
    return dataObj;
}