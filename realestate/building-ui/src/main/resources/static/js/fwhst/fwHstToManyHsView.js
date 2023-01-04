// 文档中心地址
var storageUrl = $("#storageUrl").val();
renderTpl({});
function renderTpl(json) {
    layui.use('laytpl', function () {
        //获取模板
        var tpl = $("#uploadTpl").html();
        var laytpl = layui.laytpl;
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            $("#hst").html(html);
        });
    })
}

layui.use('upload', function () {
    var $ = layui.jquery;
    var upload = layui.upload;
    //拖拽上传
    upload.render({
        elem: '#hst'
        , url: '../hst/uploadonehsttomanyhs?fwHsIndexList=' + fwhsIndexList
        , accept: 'file'
        , auto: true
        , exts: 'jpg|png|jpeg'
        , done: function (res) {
            if (res.success && res.imgId && res.fwHstIndex) {
//                    fwHstIndex = res.fwHstIndex;
//                    var imgUrl = storageUrl + "/rest/files/download/" + res.imgId;
//                    renderTpl({srcUrl:imgUrl});
//                    layer.msg("上传成功");
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index)
                parent.layer.msg("提交成功")
            } else {
                layer.alert("上传失败");
            }
        }
    });
});
