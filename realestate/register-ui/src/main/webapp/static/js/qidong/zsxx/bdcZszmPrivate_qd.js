/**
 * 获取印制号
 */
$(document).on('click', '#zzdz', function () {
    // a标签的样式是不可用时,不执行后面的事件
    if ($("#zzdz").hasClass("layui-btn-disabled")) {
        return false;
    }
    // loading加载
    addModel();

    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/lzrxx/lzfs",
        type: "GET",
        data: {'lzfs': 3, "gzlslid": processInsId},
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            successMsg("更新领证方式成功！");
            removeModel();
        }, error: function () {
            warnMsg("操作失败，请重试！");
            removeModel();
        }
    });
    // 禁止刷新页面
    return false;
});