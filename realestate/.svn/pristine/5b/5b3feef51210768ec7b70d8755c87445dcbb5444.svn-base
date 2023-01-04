var xmid = GetQueryString("xmid");
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;

    $(function () {
        addModel();
        if (xmid) {
            $.ajax({
                url: "/realestate-report-alone/accessLog/sbsjx",
                dataType: "json",
                data: {xmid: xmid},
                success: function (data) {
                    removeModal();
                    if (data && data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            data[i].czsj = Format(formatDate(data[i].czsj), 'yyyy年MM月dd日 hh时mm分ss秒')
                        }
                        var json = {
                            bdcJrCzrzList: data
                        };
                        var tpl = sbsjxTpl.innerHTML, view = document.getElementById('sbsjx');
                        //渲染数据
                        laytpl(tpl).render(json, function (html) {
                            view.innerHTML = html;
                        });
                        form.render();
                    } else {
                        layer.msg("无接入操作日志!");
                    }
                },
                error: function (xhr, status, error) {
                    layer.msg("查询失败!");
                    removeModal()
                }
            });
        }
    })

});

