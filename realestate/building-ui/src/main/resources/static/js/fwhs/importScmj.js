/**
 * 关闭弹出页面
 */
window.closeWin = function(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

layui.use(['form', 'jquery', 'upload'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    form.render();

    //选完文件后不自动上传
    upload.render({
        elem: '#fileUpload'
        , url: '../import/importScmj'
        , data: {
            fwDcbIndex: $("#fwDcbIndex").val(),
            qjgldm: $("#qjgldm").val()
        }
        , auto: false
        , accept: 'file'
        , exts: 'xls|xlsx'
        , acceptMime: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
        , bindAction: '#submit'
        , before: function (obj) {
            addModel();
        }
        , done: function (res, index, upload) {
            removeModal();
            if (parent && parent.$("#importScjzmj").length > 0) {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            }
        }
        , error: function (index, upload) {
            removeModal();
        }
    });
});
