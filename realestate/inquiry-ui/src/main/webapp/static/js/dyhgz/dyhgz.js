//表格加载
function loadComplete() {
    sessionStorage.removeItem("dyhgz_yxxmsdxx");
    sessionStorage.removeItem("dyhgz_yxid");
    sessionStorage.removeItem("dyhgz_cqxmid");
}
//自动关联
function zdgl(obj, data){
    //打开历史关系查看页面
    var url="/realestate-inquiry-ui/view/dyhgz/xmlsgx.html?xmid="+data.XMID+"&xmtype=1";
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        btn:['一键带入','取消'],
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
}

//新增权利
function xzql(obj, data) {
    var cqxmid =sessionStorage.getItem("dyhgz_cqxmid");
    if(!isNotBlank(cqxmid) ||cqxmid !==data.XMID){
        layer.alert("请先点击对应产权的自动关联按钮");
        return false;
    }
    var url="/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=bdcdyplgxxzql";
    var layerIndex = layer.open({
        title: '不动产权利列表',
        type: 2,
        area: ['960px', '450px'],
        content: url
        , cancel: function (index, layero) {
            parent.layer.close(index);
        }
        , success: function () {

        }
    });
    layer.full(layerIndex);
}

//替换单元号
function thdyh() {
    var xmsdData ={};
    if(sessionStorage.getItem("dyhgz_yxxmsdxx") &&sessionStorage.getItem("dyhgz_yxxmsdxx") !="undefined") {
        xmsdData = JSON.parse(sessionStorage.getItem("dyhgz_yxxmsdxx"));
    }
    if(xmsdData ==null ||((xmsdData.xmxx ==null ||xmsdData.xmxx.length ===0) && (xmsdData.dysdxx ==null ||xmsdData.dysdxx.length ===0))){
        layer.alert("请选择需要更新的数据");
        return false;
    }
    layer.open({
        title: '不动产单元列表',
        type: 2,
        area: ['960px', '450px'],
        content: '../dyhgz/bdcdyList.html'
        , cancel: function (index, layero) {
            parent.layer.close(index);
        }
        , success: function () {

        }
    });
}

//已选项目
function yxxm(){
    var url="/realestate-inquiry-ui/view/dyhgz/yxtmxx.html";
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        fixed: false, //不固定
        title: "已选信息",
        content: url,
        btnAlign: "c"
    });
}

//新增
function xz(obj, data){
    //打开历史关系查看页面
    var url="/realestate-inquiry-ui/view/dyhgz/xmlsgx.html?xmid="+data.XMID+"&xmtype=2"+"&dysdid="+data.DYSDID;
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        btn:['一键带入','取消'],
        fixed: false, //不固定
        title: "自动关联页面",
        content: url,
        btnAlign: "c",
        yes: function (index, layero) {
            var iframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
            iframeWin.addXmxx(2);//调用子页面的方法，得到子页面返回的ids
        },
        cancel: function (index, layero) {
            parent.layer.close(index);
        }
    });
}

