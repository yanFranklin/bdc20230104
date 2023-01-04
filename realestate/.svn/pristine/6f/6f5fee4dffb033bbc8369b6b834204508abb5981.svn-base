layui.use(['jquery','layer'], function(){
    var layer = layui.layer;
    var $ = layui.jquery;
    //获取不动产单元号
    var bdcdyh = $.getUrlParam('bdcdyh');
    //获取本数页数
    function queryBsYs(){
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/djbxx/bsys/"+bdcdyh,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            success: function (data) {
                $('#bs').html(data.bs);
                $('#ys').html(data.djym);
                $('#yzgyys').html(data.yzgydjym);
                $('#dyays').html(data.dyadjym);
                $('#dyys').html(data.dyidjym);
                $('#ygys').html(data.ygdjym);
                $('#yyys').html(data.yydjym);
                $('#cfys').html(data.cfdjym);
                $('#jyqys').html(data.jyqdjym);
                $('#jzqys').html(data.jzqdjym);
                //金额字段增加单位
                $(".jedw").html("（"+commonJedw+"）");
            },
            error: function (data) {
                var response=JSON.parse(data.responseText);
                var mes=response.message==undefined?response.msg:response.message;
                layer.alert("获取登记簿本数，页码信息失败！"+(mes==undefined?"":mes));
            }
        });
    }

    queryBsYs();


})