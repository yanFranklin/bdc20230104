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

//前台文件下载
function fileDownload(url, data, method){    // 获得url和data
    if( url && data ){
        // data 是 string 或者 array/object
        data = typeof data == 'string' ? data : jQuery.param(data);        // 把参数组装成 form的  input
        var inputs = '';
        jQuery.each(data.split('&'), function(){
            var pair = this.split('=');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
        });        // request发送请求
        jQuery('<form action="'+ url +'" method="'+ (method||'post') +'">'+inputs+'</form>')
            .appendTo('body').submit().remove();
    };
};
//onmouseover事件时显示tip
function showTip(object,tipVal){
    if(tipVal && tipVal!=""){
        layer.tips(tipVal, object, {
            tips: 3
        });
    }

}
//onmouseout事件时隐藏tip
function hideTip(object){
    var index = layer.tips();
    layer.close(index);
}
//判断前台object {} 是否为空
function isEmptyObject(obj) {
    for (var key in obj){
        return false;//返回false，不为空对象
    }
    return true;//返回true，为空对象
}