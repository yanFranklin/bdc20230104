// 渲染页面的数据
var dataArr;
layui.use(['layer', 'element', 'form', 'jquery', 'laydate', 'table', 'laytpl'], function () {
    var laypage = layui.laypage;
    var laytpl = layui.laytpl;
    var layer = layui.layer;
    var $ = layui.jquery;

    // 获取参数
    var bdcdyh = $.getUrlParam('bdcdyh');

    // 加载数据分页显示
    function flushPage(pageIndex) {
        addModel();
        $.ajax({
            url: '/realestate-register-ui/rest/v1.0/djbxx/qlxx/gyxx/' + bdcdyh + '?size=10&page=' + (pageIndex - 1),
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data) {
                    // 填充数据
                    dataArr = data.content;
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
                        theme: '#1d87d1',
                        curr: data.number + 1,
                        layout: ['prev', 'page', 'next', 'skip'],
                        jump: function (obj, first) {
                            if (!first) {
                                flushPage(obj.curr);
                            }
                        }
                    });
                    //获取权利人
                    queryBdcQlr(data);

                    // 设置背景色
                    changeQsztColor(dataArr);
                }
            },
            error: function (data) {
                var response = JSON.parse(data.responseText);
                var mes = response.message == undefined ? response.msg : response.message;
                layer.alert("建筑物区分所有权业主共有部分登记信息！" + (mes == undefined ? "" : mes));
            },
            complete: function () {
                // 关闭loading
                removeModel();
            }
        });
    }

    flushPage(1);

    /**
     *  设置建筑物区分所有权业主共有部分登记信息_共有部分信息内容
     */
    function queryBdcQlr(data) {
        if (!data.content || data.content.length <= 0 || !data.content[0]) {
            return false;
        }
        $.ajax({
            url: '/realestate-register-ui/rest/v1.0/djbxx/qlxx/fdcq3/' + data.content[0].xmid,
            type: "GET",
            success: function (data) {
                $('#qlr').html(data);
            },
            error: function () {
                layer.alert("建筑物区分所有权业主共有部分权利人失败！")
            }
        });
    }

})

