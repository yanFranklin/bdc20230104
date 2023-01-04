/**
 * Created by sly on 2017/10/12.
 */
//时间格式化
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function getBbttonAutho() {
    var zt = $("#zt").val();
    var cxxy=$("#cxxy"); //撤销协议
    var qdsrxx=$("#qdsrxx"); //确定输入信息
    var xysave=$("#updateXyForm"); //协议信息表单保存
    var xyprint=$("#printXyxx"); //协议信息表单打印
    var zhsave=$("#updateZhForm"); //账户信息表单保存
    var zhprint=$("#printZhxx"); //账户信息表单打印
    var readZhxx=$("#readZhxx"); //读取账户信息
    var readCzxx=$("#readCzxx"); //读取账户信息
    var delBuyUser=$(".delBuyUser"); //删除买方信息
    var addBuyUser=$("#addBuyUser"); //添加买方账户按钮
    switch (zt) {
        case '101':
            removeHideClass([xysave,qdsrxx,cxxy,addBuyUser,delBuyUser]);
            break;
        case '109':
            removeHideClass([cxxy,xyprint,zhsave,zhprint,readZhxx])
            break;
        case '201':
            removeHideClass([cxxy,qdsrxx,zhsave,readZhxx,readCzxx])
            break;
        case '209':
            removeHideClass([cxxy,xyprint,zhprint,readCzxx])
            break;
        case '301':
            removeHideClass([readCzxx])
            break;
        case '303':
            removeHideClass([xyprint,zhprint,readCzxx])
            break;
        case '':
            removeHideClass([xysave,addBuyUser,delBuyUser])
            break;
        default:
            removeHideClass([xysave,addBuyUser,delBuyUser,readCzxx])
            return;
    }
}
function removeHideClass(data) {
    if(data && data.length>0) {
        $.each(data, function (index, data) {
            data.removeClass("layui-hide");
        })
    }
}

