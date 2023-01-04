/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/01/14
 * describe: 新增或者编辑证书编号模板配置处理JS
 */
layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        form = layui.form;

    // 表单样式调整
    $("form").css("margin-top","10px")
    $("div[name='inline']").css("width","350")
    $("div[name='inline1']").css("width","600")
    $("label").css("width","125")

    // 新增或者编辑标识
    var action = $.getUrlParam('action');

    // 根据标识符更改新增或者编辑的url地址
    if("edit" === action){
        var actionUrl = "/realestate-inquiry-ui/config/v1.0/rzxz/edit"
        // 编辑操作默认主机号不可更改
        $("input[name='host']").attr("readonly","readonly");
        // 编辑操作主机号输入框变灰
        $("input[name='host']").css("background","#EEEEEE");
        // 编辑操作主机号前加*号提示符
        $("sub").text("*");
    }else {
        var actionUrl = "/realestate-inquiry-ui/config/v1.0/rzxz/add"
    }

    /**
     * 表单数据提交
     */
    form.on('submit(submitBtn)', function(data) {
        rsaEncode(data);
        $.ajax({
            url: actionUrl,
            type: "PUT",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回结果，失败会返回状态信息JSON
                if(data!== 0){
                    success();
                } else {
                    fail(data);
                }
            },
            error:function(data){
                fail(JSON.parse(data.responseText));
            }
        });

        // 禁止提交后刷新
        return false;
    });

    /**
     * 编辑时填充数据
     * @param data
     */
    window.setData = function(data){
        data.password = "";
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        form.val('rzxzform', JSON.parse(JSON.stringify(data)));
    };

    /**
     * 关闭弹出页面
     */
    window.closeWin = function(){
        var index=parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };

    /**
     * 提交成功提示
     */
    window.success = function(){
        saveSuccessMsg();
        setTimeout(function(){
            var index = parent.parent.parent.layer.getFrameIndex(window.name);
            parent.parent.parent.layer.close(index);
        },2000);
        closeWin();
    }

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function(data){
        var msg = "提交失败，请检查填写内容!";

        if (data == 0) {
            msg = "提交失败，该主机配置已经存在！";
        }
        alertMsg(msg);
    }



    //公钥
    var PUBLIC_KEY =  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8HMr2CBpoZPm3t9tCVlrKtTmI4jNJc7/HhxjIEiDjC8czP4PV+44LjXvLYcSV0fwi6nE4LH2c5PBPEnPfqp0g8TZeX+bYGvd70cXee9d8wHgBqi4k0J0X33c0ZnW7JruftPyvJo9OelYSofBXQTcwI+3uIl/YvrgQRv6A5mW01QIDAQAB";
    //私钥
    var PRIVATE_KEY = 'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALwcyvYIGmhk+be320JWWsq1OYjiM0lzv8eHGMgSIOMLxzM/g9X7jguNe8thxJXR/CLqcTgsfZzk8E8Sc9+qnSDxNl5f5tga93vRxd5713zAeAGqLiTQnRffdzRmdbsmu5+0/K8mj056VhKh8FdBNzAj7e4iX9i+uBBG/oDmZbTVAgMBAAECgYEAmgNU5NTDkj9B+Pnt6UU8doSjw3+3j+bV2K2yS3QUOvAUus/Ax7x6ktjWxzCXvDY9IfUil2RNv9vtKEAqYLCWjc+lf8PV/yH1b7NEgyeAPBXtAJRoOnmYL2bdPW92kP9KgxJruF6Dz/C5AmMOncsvq8ABD+9Darn4p8dwj2ZC4O0CQQDf/AHmZsQokEItfCy4mHS9UbxbfIhEUv1ApPh/+Sr7NkJkHWYCtBQo+8jKO6zurAZQgWBPD1XX2UE4R+VIiZazAkEA1wAqtMvGhccyRZr+6kpkpDIa8+9jOE+nGUzqTDvgCID6as8AzOONFVVK6m/UUqkhcJ8Qu1pF36BGojy5BX2KVwJBAJSFpbji0hXXupowqfLp3RcgmNbNWAp+QUJZYhJx5cdYbmO2fssyH+AhPT6knYJR/YnqkDM8hv6vKCkqu2YDHjMCQAOA8TE5EOclM+CGghj3VWSHnIDVKdzFD4gOBNNxNlltIKeU8AJmwunSFgJ0CBXAw9a+ANvMwM7AIeaK7sj0HskCQAvxfDCq7gaNx+pfu0FHG8Gix08A/A6foggBl1fVu+L9sr9ZuOQ3HbXnl28F9ewuB9xdjnLUDjp7W7U0pB+vKoQ=';

    /**
     * 使用rsa加密日志配置信息
     * @param data
     */
    function rsaEncode(data){
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(PUBLIC_KEY);
        data.field.password = encrypt.encrypt(data.field.password);
    }
});