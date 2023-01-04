layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
    var form = layui.form;
    var laytpl = layui.laytpl;
    var $ = layui.jquery;
    $('.layui-form').removeAttr('style');
    //获取数据
    addModel();
    $.ajax({
        url: "../djdcb/fwbdcdyinfo",
        dataType: "json",
        data: {
            bdcdyh: bdcdyh,
            qjgldm:qjgldm
        },
        success: function (data) {
            removeModal();
            var qlrlist = [];
            if(data){
                qlrlist = data.qlrList;
                form.val("fwhsform", data);
                form.render();
                initZd($("#fwhsForm"));
                disabledAddFa();
            }
            var json = {
                data: qlrlist
            }
            //获取模板
            var tpl = $("#fwQlrTpl").html();
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                $("#fwQlrList").html(html);
            });
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });
});

