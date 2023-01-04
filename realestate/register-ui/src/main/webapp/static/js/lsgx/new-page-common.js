/**
 * Created by Ypp on 2020/1/16.
 * 任务办理各版本通用js
 */
var $, laytpl;

layui.use(['jquery', 'element', 'form', 'laytpl', 'layer', 'table'], function () {
    $ = layui.jquery;

    var processInstanceId = "";

    var slbh = "";

    $(function(){
        //获取地址栏参数
        var $paramArr = getIpHz();
        if ($paramArr['processInsId']) {
            processInstanceId = $paramArr['processInsId'];
        }
        if ($paramArr['slbh']){
            slbh = $paramArr['slbh'];
        }

        // 点击以窗口或者最大化展示附件弹窗
        $('.bdc-fj-title .bdc-show-popup').on('click', function () {
            window.open('/realestate-portal-ui/view/popup.html?processinsid=' + processInstanceId + '&slbh=' + slbh);
            return;
        });

        $('.bdc-fj-show img').on('click', function () {
            popupLayer = layer.open({
                type: 2
                , title: '<div class="bdc-fj-title-content">' +
                    '<p class="bdc-fj-title">' +
                    '<span class="bdc-fj-word">附件' +
                    '</span>' +
                    '</p>' +
                    '<div class="bdc-operate-icon">' +
                    '<i class="fa fa-expand" aria-hidden="true"></i>' +
                    '<i class="layui-icon layui-icon-close"></i>' +
                    '</div>' +
                    '</div>'
                , shade: 0
                , maxmin: true
                , offset: 'auto'
                // 小窗口展示附件
                , area: ['960px', '350px']
                , content: '/realestate-portal-ui/view/popup.html?processinsid=' + processInstanceId + '&slbh=' + slbh
                , zIndex: layer.zIndex
                , success: function (layero) {
                    layer.setTop(layero);
                },
                cancel: function () {

                },
            });
            layer.full(popupLayer);
        });

    })

});



