/** 收费信息id */
var sfxxid = $.getUrlParam("sfxxid");

layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/fph/edit';
    /**
     * 点击保存
     */
    $('#save').on('click', function () {
        addModel();
        // 验证发票号是否可用
        $.ajax({
            url: BASE_URL + "/check?sfxxid=" + sfxxid + "&fph=" + $("#fph").val(),
            type: "GET",
            async: false,
            success: function (data) {
                if (data.isKy == false || data.syzt == '2') {
                    warnMsg("该发票号不可用！")
                    return false;
                } else if (data.syzt == '3') {
                    layer.confirm('该发票号已使用，是否修改？', function (index) {
                        updateSfxx(sfxxid, $("#fph").val());
                        layer.close(index);
                        return false;
                    });
                } else {
                    updateSfxx(sfxxid, $("#fph").val());
                }
            },
            error: function (e) {
                showErrorInfo(e, "验证服务异常");
                removeModal();
            },complete: function (XMLHttpRequest, textStatus) {
                removeModal();
            }
        });
    });

    /**
     * 更新收费信息
     */
    function updateSfxx(sfxxid, fph) {
        addModel();
        $.ajax({
            url: BASE_URL + "/save?sfxxid=" + sfxxid + "&fph=" + fph,
            type: "GET",
            success: function (data) {
                layer.msg("更新成功");
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                window.parent.$("#search").click();
            },
            error: function (e) {
                showErrorInfo(e, "更新服务异常");
                removeModal();
            }, complete: function (XMLHttpRequest, textStatus) {
                // 关闭loading
                removeModal();
            }
        });
    }
});