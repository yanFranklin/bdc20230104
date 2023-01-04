/**
* @author "<a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
* @version 1.0, 2022/12/16
* @description 律师调查令查询核验
*/
var form, $, formdata;
layui.use(['layer', 'form', 'jquery'], function () {
    form = layui.form;
    $ = layui.jquery;

    form.on('submit(lsdclcxhyForm)', function(data){
        var processInsId = $.getUrlParam('processInsId');
        if(isNullOrEmpty(processInsId)) {
            warnMsg("未获取到项目信息，无法保存！");
            return false;
        }

        addModel();
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/sjxxgx/fyxx/pdf/lsdclcxhy/" + processInsId,
            type: 'POST',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(formdata),
            success: function (data) {
                removeModel();
                successMsg("保存成功！");
            },
            error: function (xhr, status, error) {
                removeModel();
                errorsMsg("保存失败，请重试");
            }
        });
        return false;
    });

});

window.setData = function (data) {
    formdata = data;
    layui.form.val('lsdclcxhyForm', JSON.parse(JSON.stringify(data)));
}