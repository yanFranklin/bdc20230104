layui.use(['form', 'table', 'layer', 'laydate', 'jquery'], function () {
    var table = layui.table;
    var $ = layui.jquery;//绑定回车键
    var isSearch = true;
    /**
     * 回车键模拟 click 事件
     */
    $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
        isSearch = false;
    }).on('blur', '.layui-laypage-skip .layui-input', function () {
        isSearch = true;
    });
    $(document).keydown(function (event) {
        if (event.keyCode == "13") {
            if (isSearch) {
                if ($("#query").length > 0) {
                    $("#query").click();
                }
            }
        }
    });
})