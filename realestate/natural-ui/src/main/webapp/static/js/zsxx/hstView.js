/**
 * @description 户室图预览页面js
 *
 */

layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer', 'carousel'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;
    $(function () {
        // 查询参数
        var zsid = $.getUrlParam("zsid");
        var qjgldm = $.getUrlParam("qjgldm");
        // 获取数据
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/fwxx/hst",
            contentType: "application/json;charset=utf-8",
            data: {zsid: zsid, qjgldm: qjgldm},
            type: "GET",
            success: function (data) {
                var image = new Image();
                image.src = "data:image/png;base64," + data;
                var viewer = new Viewer(image);
                viewer.show();
            }, error: function (e) {

            }
        });

        //监听图片双击事件
        $('#seeImgView').on('dblclick',function(){
            $('.viewer-reset').click();
        });
    })
});