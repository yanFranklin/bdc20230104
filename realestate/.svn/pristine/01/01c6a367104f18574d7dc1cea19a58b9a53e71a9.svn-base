$(function () {
    // 下载模板
    $("#downtemp").click(function(){
        window.open("../import/downtemp");
    })
});

layui.use(['form', 'jquery', 'upload'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    form.render();
    //选完文件后不自动上传
    upload.render({
        elem: '#fileUpload'
        , url: '../import/importlpb'
        , data: {
            fwDcbIndex: $("#fwDcbIndex").val(),
            fgyyhs: function () {
                return $(".layui-form-radioed").prev().val()
            },
            qjgldm:$("#qjgldm").val()
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
            layer.msg("导入成功");
        }
        , error: function (index, upload) {
            removeModal();
            layer.alert("导入失败")
        }
    });
});
