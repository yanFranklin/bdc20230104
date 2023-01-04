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
            url: '/realestate-register-ui/rest/v1.0/djbxx/qlxx/jzq?size=4&page=' + (pageIndex - 1) + "&bdcdyh=" + bdcdyh,
            type: "GET",
            dataType: "json",
            success: function (data) {
                //依据现势状态获取最新数据，如果无现势状态则采用原逻辑
                if(data){
                    var zl="房地坐落:";
                    var zlmc;
                    var fwzl;
                    if(data.content && data.content.length>0) {
                        for (var i = 0; i<data.content.length; i++){
                            if ($("#zl").length > 0 && data.content[i].qszt == 1) {
                                fwzl = data.content[i].zl;
                                zlmc=zl + fwzl;
                            }
                        }
                        if(fwzl == undefined){
                            fwzl = data.content[data.content.length - 1].zl;
                            zlmc = zl + fwzl;
                        }
                    }
                    $("#zl").html(zlmc);


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