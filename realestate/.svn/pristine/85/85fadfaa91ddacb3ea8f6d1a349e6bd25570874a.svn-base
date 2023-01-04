layui.use(['layer', 'table', 'jquery', 'form', 'element'], function () {

    var layer = layui.layer;
    var $ = layui.jquery;
    var table = layui.table;
    var form = layui.form;
    var element = layui.element;

});

/**
 * 加密 密码
 * @date 2019.12.10
 * @author hanyi
 * @return
 */
function encryptMm() {
    var mm = $("#mm").val();
    if (isNullOrEmpty(mm)) {
        layer.msg("请先输入密码");
        return false;
    }
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/mmcl/encrypt/" + mm,
        type: "get",
        async: true,
        success: function (data) {
            $("#jmjg").val(data);
        },
        error: function (e) {
            showErrorInfo(e);
        }
    });
}

/**
 * 解密 密码
 * @date 2019.12.10
 * @author hanyi
 * @return
 */
function decryptMm() {
    var mw = $("#mw").val();
    if (isNullOrEmpty(mw)) {
        layer.msg("请先输入密文");
        return false;
    }
    mw = encodeURIComponent(mw);
    var gy = $("#gy").val();
    if (isNullOrEmpty(gy)) {
        layer.msg("请先输入公钥");
        return false;
    }
    gy = encodeURIComponent(gy);
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/mmcl/decrypt?mw=" + mw + "&publickey=" + gy,
        type: "get",
        async: true,
        success: function (data) {
            $("#jmmm").val(data);
        },
        error: function (e) {
            showErrorInfo(e);
        }
    });
}