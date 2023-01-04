layui.use(['layer', 'element', 'form', 'jquery', 'laydate', 'table', 'laytpl'], function () {
    var laypage = layui.laypage;
    var laytpl = layui.laytpl;
    var layer = layui.layer;
    var $ = layui.jquery;

    // 获取参数
    var zdzhh = $.getUrlParam('zdzhh');
    var bdcdyh = $.getUrlParam('bdcdyh');
    if (isNullOrEmpty(bdcdyh)) {
        bdcdyh = '';
    }
    // loading加载
    addModel();

    // 加载数据分页显示
    function flushPage(pageIndex) {
        $.ajax({
            url: '/realestate-register-ui/rest/v1.0/djbxx/' + zdzhh + '/qlml?bdcdyh=' + bdcdyh + '&size=10&page=' + (pageIndex - 1),
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data) {
                    // 填充数据
                    var getTpl = tableTpl.innerHTML
                        , view = document.getElementById('tableView');
                    laytpl(getTpl).render(data.content, function (html) {
                        view.innerHTML = html;
                    });
                    // 渲染分页
                    laypage.render({
                        elem: 'tablePage',
                        count: data.totalElements,
                        limit: 10,
                        curr: data.number + 1,
                        layout: ['prev', 'page', 'next', 'skip'],
                        jump: function (obj, first) {
                            if (!first) {
                                flushPage(obj.curr);
                            }
                        }
                    });
                }
            },
            error: function (data) {
                var response = JSON.parse(data.responseText);
                var mes = response.message == undefined ? response.msg : response.message;
                layer.alert("获取不动产登记不动产单元目录失败！" + (mes == undefined ? "" : mes));
            },
            complete: function () {
                // 关闭loading
                removeModel();
            }
        });
    }

    flushPage(1);

})

