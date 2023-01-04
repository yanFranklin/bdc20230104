layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer'], function () {
    var element = layui.element;
    var form = layui.form;
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var screenH = document.documentElement.clientHeight;
    // $(".content-main").css({'min-height': screenH - 70});
    // 获取参数
    var zdzhh = $.getUrlParam('zdzhh');

    function getDjbxx() {
        //获取数据
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/djbxx/"+zdzhh+"/bdcdjb",
            contentType: "application/json;charset=utf-8",
            type: "GET",
            success: function (data) {
                form.val('djbxx',data);
            },
            error: function () {
                layer.alert("获取登记簿信息失败！")
            }
        });
    }

    getDjbxx();

    //模板引擎加载数据
    function processData(data) {
        var getTpl = catalogDiv.innerHTML; //获取自己定义的模板元素
        laytpl(getTpl).render(data, function (html) {
            $("#templateFatherDiv").html(html); //加载到主元素中
            form.render();
        });
    }

});
