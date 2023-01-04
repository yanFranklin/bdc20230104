// 渲染页面的数据
var dataArr;
//需要变色的下标
var index = 0;
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
            url: '/realestate-register-ui/rest/v1.0/djbxx/qlxx/dyaq?size=4&page=' + (pageIndex - 1) + "&bdcdyh=" + bdcdyh,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data) {
                    // 设置抵押不动产类型
                    if (data.content && data.content.length > 0) {
                        $.each($("[name='dybdclx']"), function (index, item) {
                            item.checked = false;
                            if (item.value == data.content[data.content.length - 1].dybdclx) {
                                item.checked = true;
                            }
                        });
                    }

                    dataArr = data.content;
                    // 填充数据
                    laytpl(qlTpl.innerHTML).render({"title": "", "list": data.content}, function (html) {
                        $("#tableInfoDiv").html(html);
                    });

                    // 复选框等需要渲染
                    form.render();

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
        removeModel();
    }

    // 页面首次触发
    flushPage(1);
});




