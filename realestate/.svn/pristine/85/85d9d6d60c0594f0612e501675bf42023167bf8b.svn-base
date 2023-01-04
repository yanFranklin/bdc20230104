var processInsId = $.getUrlParam('processInsId');
layui.use(['jquery'], function () {
    var $ = layui.jquery;

    $(function () {
        // 默认显示layui-this所在的tab页
        var defaultId = $('#liTable li.layui-this').attr('id');
        openTabIframe(defaultId);

        // 监听点击tab，切换页面
        $('#liTable li').on('click', function () {
            if (!$(this).hasClass('layui-this')) {
                var currentId = $(this).attr('id');
                openTabIframe(currentId);
            }
            $('#pageNum').text($(this).attr('pageNum'));
        });

        // 打开指定id的页面
        function openTabIframe(id) {
            $('#djbFrame').attr('src', '/realestate-natural-ui/view/ywxx/djb' + id + '.html' + '?processInsId=' + processInsId);
            // 标记iframe
            $('#djbFrame').attr('tab', id);
        }

    });

});
