var uploadInstDah = null;
var uploadDah = null;
$(function(){
    layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'upload'], function () {
        var form = layui.form;
        uploadDah = layui.upload;
        uploadInstDah = uploadDah.render({
            elem: '.importdah' //绑定元素
            , accept: 'file'
            , url: '/realestate-inquiry-ui/rest/v1.0/dah/import/excel' //上传接口
            , done: function (res) {
                if (res == null || res.length == 0) {
                    ityzl_SHOW_INFO_LAYER("导入失败,请检查数据格式");
                } else {
                    ityzl_SHOW_SUCCESS_LAYER("导入成功")
                }
                removeModal();
            }
            , error: function (e) {
                removeModal();
                layer.alert('导入数据解析失败，请检查模板是否正确！', {title: '提示'});
            }
        });
        form.render();
    })
})

function importexceldah() {
    uploadInstDah.upload();
}