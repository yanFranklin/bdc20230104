layui.define("form", function (exports) {
    var $ = layui.$;
    var response = {
        fail: function (e,message) {
            failTip(e, message);
        },
        success:function () {
            successTip();
        }
    };
//成功小提示
    function successTip() {
        layer.msg('<img src="../static/lib/bdcui/images/success-small.png" alt="">请求成功');
    }

//失败小提示
    function failTip(e, message) {
        var msg = '请求异常！';
        var detail = '';
        if (message != '' && message != undefined) {
            msg = message;
        }
        if (e.status == 500) {
            var responseText = JSON.parse(e.responseText);
            msg = (message != '' && message != undefined)? msg:responseText.msg;
            detail = responseText.detail;
        }
        layer.msg('<img src="../static/lib/bdcui/images/error-small.png" alt="">' + msg + ' <a class="bdc-show-more-tips" href="javascript:;">更多</a>', {
            time: 4000,
            success: function () {
                var exceptionMsg = '';
                $.each(detail, function (key, val) {
                    exceptionMsg += '<p>' + val + '</p>';
                });
                if (exceptionMsg == '') {
                    exceptionMsg = '<p>暂无详细信息，请查看系统日志</p>'
                }
                $('#otherTips').html(exceptionMsg);
                //点击更多
                $('.bdc-show-more-tips').on('click', function () {
                    $('.bdc-other-tips-box').removeClass('bdc-hide');
                });
                //点击 不再提示 ，关闭提示框
                $('.bdc-other-tips-box .bdc-close').on('click', function () {
                    $('.bdc-other-tips-box').addClass('bdc-hide');
                });
            }
        });
    }
    exports("response", response);
});





