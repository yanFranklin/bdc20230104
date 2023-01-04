/**
 * @description 宗地图预览页面js
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
        //获取数据
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/zdxx/zdt",
            contentType: "application/json;charset=utf-8",
            data: {zsid: zsid, qjgldm: qjgldm},
            type: "GET",
            success: function (data) {
                $('#zdt').attr('src','data:image/png;base64,'+data);
            },error:function (e) {

            }
        });
    })
});