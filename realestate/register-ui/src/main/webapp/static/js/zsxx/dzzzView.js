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
        var url = parent.dzzzUrl;
        if(url.indexOf("ofd") > -1){
            warnMsg("ofd格式暂不支持预览，请在附件材料中下载后用特定阅读器预览！");
            return;
        }
        console.log("当前电子证照地址为："+url);
        //获取base64数据
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/yldzzz?url="+encodeURIComponent(url),
            type: "GET",
            success: function (data) {
                $('#dzzz').attr('src','data:image/png;base64,'+data);
            },error:function (e) {

            }
        });


    })
});