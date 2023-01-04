/**
 * 登记簿权利封面JS代码
 */
layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer'], function () {
    var element = layui.element;
    var form = layui.form;
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    var layer = layui.layer;
    var laydate = layui.laydate;
    // 查询参数
    var bdcdyh = $.getUrlParam("bdcdyh");

    var screenH = document.documentElement.clientHeight;
    $(".content-main").css({'min-height': screenH - 70});

    // loading加载
    addModel();
    //获取数据
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/djbxx/bsys/" + bdcdyh,
        contentType: "application/json;charset=utf-8",
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                // 为基本信息赋值
                form.val('form', data);
                form.render();
                // 为房屋产权页码重新赋值
                if (bdcdyh.substring(19, 20) == "F") {
                    if (data.djym != null && data.djym.length > 0 && data.yzgydjym != null && data.yzgydjym.length > 0) {
                        $("#djym").val(data.djym + "、" + data.yzgydjym);
                    }
                    if (data.djym == null || data.djym.length == 0) {
                        $("#djym").val(data.yzgydjym);
                    }
                }
                if(data.jyqdjym != null &&data.jyqdjym.length >0){
                    $("#jyqdjym").parents("tr").removeClass("bdc-hide");

                }
            }
        }, complete: function (XMLHttpRequest, textStatus) {
            // 关闭loading
            removeModel();
        }
    });
})