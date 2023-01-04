/**
 * 常州摩科评价器方法
 */
// 用户IP地址
var ipaddress = "";
var pno = "001";
var userInfo;
$(function(){

    // 获取用户IP地址
    try {
        getUserIP(function (ip) {
            if (ip != null && ip != undefined) {
                ipaddress = ip;
            }
        });
    } catch(err){
        console.info("获取IP地址出错：" + err.toString());
    }

    // 获取当前登录用户信息
    getReturnData("/rest/v1.0/user/info",{},"GET",function (data) {
        console.info(data);
        if(data) {
            userInfo = data;
        }
    },function (xhr, status, error) {
        delAjaxErrorMsg(xhr)
    },false);

    // 加载portal页面时，自动执行评价器签到操作
    setTimeout(function(){
        try {
            pjq.signin();
        } catch (e) {
            console.info(e);
        }
    },2000);

});

var pjq = (function(){
    return {
        signin: function () {
            var opts={"sysIp": ipaddress, "name": userInfo.alias, "jobNum": userInfo.jobNumber};
            $.ajax({
                url: getContextPath() + "/rest/v1.0/pjq/mk/mk_sendUserMes",
                type: 'POST',
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(opts),
                success: function (data) {
                    if(isNotBlank(data)){
                        if(data.code == "1"){
                            console.log("评价器登录成功。");
                        }else{
                            ityzl_SHOW_WARN_LAYER("评价器登录失败，"+ data.message);
                        }
                    }else{
                        ityzl_SHOW_WARN_LAYER("登录评价器失败，未连接到摩科评价器");
                    }
                }
            });
        },

        signout: function(){
            var opts={"sysIp": ipaddress};
            $.ajax({
                url: getContextPath() + "/rest/v1.0/pjq/mk/mk_closeCurrent",
                type: 'POST',
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(opts),
                success: function (data) {
                    if(isNotBlank(data)){
                        if(data.code == "1"){
                            console.log("评价器关闭成功。");
                        }else{
                            ityzl_SHOW_WARN_LAYER("评价器关闭失败，"+ data.message);
                        }
                    }else{
                        ityzl_SHOW_WARN_LAYER("评价器关闭失败，未获取到摩科评价器反馈信息");
                    }
                }
            });
        },

    }
})();


// 监听页面关闭方法，页面重新加载也会执行
window.onbeforeunload = function(e){
    pjq.signout();
}