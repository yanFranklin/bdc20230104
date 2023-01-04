var date = $.getUrlParam('date');
var qxdm = $.getUrlParam('qxdm');
layui.use(['table', 'laytpl', 'layer'], function () {
    var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;
    var laytpl = layui.laytpl;

    $(function () {
        $.ajax({
            url: '/realestate-inquiry-ui/dbrz/dbmxyl',
            dataType: "json",
            type: "GET",
            async: false,
            data: {'date': date, 'qxdm': qxdm},
            success: function (data) {
                var json = {
                    zd: data["qx"],
                    accessLog: data["accessLog"],
                    accessDate: date,
                    xzqdm: data.qxdm
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