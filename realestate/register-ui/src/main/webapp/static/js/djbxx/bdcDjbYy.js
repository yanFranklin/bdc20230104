// 渲染页面的数据
var dataArr;
layui.use(['jquery', 'table', 'laypage', 'laytpl', 'layer', 'form'], function () {
    var $ = layui.$,
        table = layui.table,
        laypage = layui.laypage,
        laytpl = layui.laytpl,
        form = layui.form,
        layer = layui.layer;

    // 获取参数
    var bdcdyh = $.getUrlParam('bdcdyh');
    $("#bdcdyh").html("不动产单元号：" + formatBdcdyh(bdcdyh));

    // 加载数据分页显示
    function flushPage(pageIndex) {
        addModel();
        $.ajax({
            url: '/realestate-register-ui/rest/v1.0/djbxx/qlxx/bdcYy?size=4&page=' + (pageIndex - 1) + "&bdcdyh=" + bdcdyh + "&qllx=" + 97,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data) {
                    // 填充数据
                    dataArr = data.content;
                    laytpl(qlTpl.innerHTML).render({"title": "", "list": data.content}, function (html) {
                        $("#form").html(html);
                    });
                    form.render();
                    // 设置附记栏高度
                    changeFjHeight();

                    // 渲染分页
                    laypage.render({
                        elem: 'tablePage',
                        count: data.totalElements,
                        limit: 4,
                        curr: data.number + 1,
                        layout: ['prev', 'page', 'next', 'skip'],
                        jump: function (obj, first) {
                            if (!first) {
                                flushPage(obj.curr);
                            }
                        }
                    });
                    // 设置背景色
                    changeQsztColor(dataArr);
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                // 关闭loading
                removeModel();
            }
        });
    }

    // 页面首次触发
    flushPage(1);
});