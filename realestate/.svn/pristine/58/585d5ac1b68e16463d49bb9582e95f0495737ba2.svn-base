// 渲染页面的数据
var dataArr;
layui.use(['jquery','table','laypage','laytpl'], function(){
    var $ = layui.$,
        table = layui.table,
        laypage = layui.laypage,
        laytpl = layui.laytpl;
    // 获取参数
    var bdcdyh = $.getUrlParam('bdcdyh');
    var qllx=document.getElementById("qllx").value;

    $("#bdcdyh").html("不动产单元号：" + formatBdcdyh(bdcdyh));
    $(function () {
        page(1);

    });

    function page(pageIndex) {
        addModel();
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/djbxx/qlxx/bdcQlDjb?size=4&page=" + (pageIndex - 1) + "&bdcdyh=" + bdcdyh+"&qllx="+qllx,
            type: "GET",
            dataType: "json",
            success: function(data) {
                if(data){

                    /***权利信息div***/
                    dataArr = data.content;
                    var getTpl = qlxxDiv.innerHTML; //获取自己定义的模板元素
                    laytpl(getTpl).render({"bdcQlList": data.content}, function (html) {
                        $("#tableInfoDiv").html(html); //加载到主元素中

                    });
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
                            if(!first) {
                                page(obj.curr);
                            }
                        }
                    });
                    // 设置背景色
                    changeQsztColor(dataArr);

                }

            },
            error: function () {
                layer.alert("查询登记簿权利失败！")
            },
            complete: function(XMLHttpRequest, textStatus){
                // 关闭loading
                removeModel();
            }
        });
    }

});