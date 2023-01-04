var id = $.getUrlParam('id');
layui.use(['table', 'laytpl', 'layer'], function () {
    var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;
    var laytpl = layui.laytpl;

    $(function () {
        $.ajax({
            url: getContextPath() + '/registerLog/dbrz/mx',
            dataType: "json",
            type: "GET",
            async: false,
            data: {'id': id},
            success: function (data) {
                data["bdcJrDbrzjlDO"]["jrrq"] = formatDate(data["bdcJrDbrzjlDO"]["jrrq"]);
                var json = {
                    bdcJrDbrzjlDO: data["bdcJrDbrzjlDO"],
                    zd: data["qx"],
                    accessLog: data["accessLog"]
                }
                var tpl = dbrzmxTpl.innerHTML, view = document.getElementById('dbrzmx');
                //渲染数据
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });

            }
        });
    });
});