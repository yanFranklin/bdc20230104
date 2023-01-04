var bdcdyh=$("#bdcdyh").val() ;
var dzwtzm=$("#dzwtzm").val() ;

if (bdcdyh) {
    getmulushu();
    zdDjdcb();
    if(!dzwtzm){
        $("#fwhs").addClass("layui-hide");
    }
}

//宗地信息
function zdDjdcb() {
    $("#iframe").attr("src", "../djdcb/zdxx?bdcdyh="+bdcdyh);
}
//房屋户室信息
function fwhsInfo() {
    $("#iframe").attr("src", "../djdcb/fwhs?bdcdyh="+bdcdyh);
}
//户室图
function fwhst() {
    $("#iframe").attr("src", "../djdcb/hstview?bdcdyh="+bdcdyh);
}
//权利人
function fwqlr() {
    $("#iframe").attr("src", "../djdcb/qlrview?bdcdyh="+bdcdyh);
}
//房屋调查信息
function fwdcxx() {
    $("#iframe").attr("src", "../djdcb/dcxx?bdcdyh="+bdcdyh);
}
//界址信息
function zdjzxx() {
    $("#iframe").attr("src", "../djdcb/jzxx?bdcdyh="+bdcdyh);
}
