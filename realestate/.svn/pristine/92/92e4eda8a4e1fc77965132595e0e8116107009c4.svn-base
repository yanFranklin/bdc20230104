/**
 * 淮安评价器方法
 */
// 评价器地址
var pjqUrl;
// 用户信息
var userInfo;

$(function(){
    // 获取评价器地址
    getReturnData("/rest/v1.0/user/pjq",{},"GET",function (data) {
        if(isNotBlank(data)){
            pjqUrl = data;
        }
    },function (xhr, status, error) {
        delAjaxErrorMsg(xhr)
    },false);

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
            $.ajax({
                url: pjqUrl + encodeURI(userInfo.alias),
                type: 'GET',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    console.log("评价器登录：" + data);
                }
            });
        }
    }
})();
