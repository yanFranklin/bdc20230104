$(function () {
    layui.use(['layer','form', 'table', 'jquery'], function () {
        var form = layui.form;
        form.on('submit(search)', function (data) {
            var jfsewmurl = $("#jfsewmurl").val();
            if(!jfsewmurl){
                layer.msg('请输入查询条件', {icon: 5, time: 3000});
                return false;
            }
            window.open("/realestate-accept-ui/view/sfxx/scan.html?jfsewmurl="+jfsewmurl)
            return;
        })
    });

});
