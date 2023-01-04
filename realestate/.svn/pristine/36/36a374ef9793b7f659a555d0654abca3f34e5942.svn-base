// 户室图主键
var bdcdyh = $("#bdcdyh").val();
layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'upload'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var element = layui.element;
    var upload = layui.upload;
    // 查询 户室图
    if (bdcdyh) {
        $.ajax({
            url: "../hst/queryfwhstbybdcdyh",
            dataType: "json",
            data: {
                bdcdyh: bdcdyh
            },
            async: false,
            success: function (data) {
                if (data.src) {
                    $("#hst").html(data.src);
                } else {
                    layer.msg("无户室图");
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }
})
