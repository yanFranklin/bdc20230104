//表格加载
function loadComplete() {
    sessionStorage.removeItem("dyhgz_yxxmsdxx");
    sessionStorage.removeItem("dyhgz_yxid");
    sessionStorage.removeItem("dyhgz_cqxmid");
}
//自动关联
function zdgl(obj, data){
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = "DYHGZ_ZDGL";
    var paramMap = {};
    paramMap.cqxmid = data.XMID;
    paramMap.bdcdyh = data.BDCDYH;
    bdcGzYzQO.paramMap = paramMap;
    gzyzCommon(bdcGzYzQO, function (result) {
        //打开历史关系查看页面
        var url = "/realestate-inquiry-ui/view/dyhgz/xmlsgx.html?xmid=" + data.XMID + "&xmtype=1";
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            btn: ['一键带入', '取消'],
            fixed: false, //不固定
            title: "自动关联页面",
            content: url,
            btnAlign: "c",
            yes: function (index, layero) {
                var iframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
                iframeWin.addXmxx(1);//调用子页面的方法，得到子页面返回的ids
            },
            cancel: function (index, layero) {
                parent.layer.close(index);
            }
        });
    });
}

//已选信息
function yxxx(obj, data){
    var xmsdData ={};
    if(sessionStorage.getItem("dyhgz_yxxmsdxx") &&sessionStorage.getItem("dyhgz_yxxmsdxx") !="undefined") {
        xmsdData = JSON.parse(sessionStorage.getItem("dyhgz_yxxmsdxx"));
    }
    if(xmsdData ==null ||!xmsdData.yxcount||xmsdData.yxcount ===0){
        layer.alert("请先点击自动关联按钮");
        return false;
    }
    var url="/realestate-inquiry-ui/view/dyhgz/yxtmxx.html?cqxmid="+data.XMID;
    var index=layer.open({
        type: 2,
        area: ['960px', '575px'],
        fixed: false, //不固定
        title: "已选信息",
        content: url,
        btnAlign: "c"
    });
    layer.full(index);
}