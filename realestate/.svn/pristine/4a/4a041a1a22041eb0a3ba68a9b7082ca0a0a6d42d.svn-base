//获取地址栏参数
var $paramArr =  getIpHz();

var getProcessInsId = $paramArr['gzlslid'];
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
    var form = layui.form;

    form.on('submit(query)',function (data) {
        addModel();
        data.field['gzlslid'] = getProcessInsId;
        console.log(data.field)
        $.ajax({
            type: "POST",
            url: getContextPath() + "/rest/v1.0/slym/gzccl",
            data: data.field,
            async: false,
            datatype: "json",
            success: function (data) {
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("获取公证处材料保存成功");
            }, error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    })
})