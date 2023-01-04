/**
 * Created by Administrator on 2019/3/5.
 */
layui.use(['jquery','element','form','response'], function() {
    var $ = layui.jquery,
        element = layui.element,
        response = layui.response,
        form = layui.form;

    String.prototype.replaceAll  = function(s1,s2){
        return this.replace(new RegExp(s1,"gm"),s2);
    };

    $(function () {

        function getParentIpHz() {
            var $paramArr = [];
            var $params = window.parent.location.search.replace('?', '');
            var $paramSplit = $params.split('&');
            for (var i in $paramSplit) {
                var $subParam = $paramSplit[i].split('=');
                $paramArr[$subParam[0]] = $subParam[1];
            }
            return $paramArr;
        }
        //点击确认修改
        form.on('submit(updateFilter)', function(){
            var oldPwd = $('#oldPwd').val();
            var newPwd = $('#newPwd').val();
            var confirmPwd = $('#confirmPwd').val();
            var encryptor = new JSEncrypt();
            // 加密公钥
            encryptor.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIwF/9T0G3xfn0QwSre9L05/0xMLl7svSh/LMGnyotO+WXO79NeuTPq5+gw7l+XkhFv6tZtzB2jfloXOLBTlUBctnyZvEzOIKEvCSfJRU3xJrOk8tA4NHvDSXRL9s+nXCSkoblVF+3VwmwJ2Jz6vLbyZCdKrLOKtXhdveLBaeRxwIDAQAB");
            if (newPwd == confirmPwd && !isNullOrEmpty(oldPwd) && !isNullOrEmpty(newPwd)) {
                if (newPwd == oldPwd){
                    layer.msg('您输入的新密码与当前密码一致');
                    $('#newPwd').val('');
                    $('#confirmPwd').val('');
                    $('#newPwd').focus();
                    return false;
                }else {
                    $.ajax({
                        type: "PUT",
                        url: getContextPath() + "/rest/v1.0/user/newpassword?old=" + encryptor.encrypt(oldPwd).replaceAll("\\+", "%2B") + '&password=' + encryptor.encrypt(newPwd).replaceAll("\\+", "%2B"),
                        data: {},
                        success: function (data) {
                            var $paramArr = getParentIpHz();
                            if (data && (data.code == 0 || data.code == '0')) {
                                $.ajax({
                                    type: "GET",
                                    url: getContextPath() + "/rest/v1.0/user/logout",
                                    success: function (data) {
                                        var path = document.location.protocol + "//" + window.parent.location.host + window.parent.location.pathname;
                                        var search = "?t=" + (new Date()).getTime();
                                        for (var parami in $paramArr) {
                                            //加的时间戳不处理
                                            if (parami == "t") {
                                                continue;
                                            }
                                            search += "&" + parami + "=" + $paramArr[parami]
                                        }
                                        window.parent.location.href = data + path + encodeURIComponent(search);
                                    }, error: function (e) {
                                        response.fail(e, '登出系统失败，请刷新后重试！');
                                    }
                                });
                            } else {
                                warnMsg(JSON.parse(data.msg).message);
                            }
                        }, error: function (e) {
                            response.fail(e, '');
                        }
                    });
                    return false;
                }
            }else {
                layer.msg('您两次输入的新密码不一致');
                $('#newPwd').val('');
                $('#confirmPwd').val('');
                $('#newPwd').focus();
                return false;
            }
        });

    });
});