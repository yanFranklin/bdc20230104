
layui.use(['form','jquery','element'],function () {
    var $ = layui.jquery,
        form = layui.form;


    $(function () {
        var qlrid = getQueryString("qlrid");
        $.ajax({
            url: "/realestate-etl/wwsq/zd",
            type: "GET",
            async: false,
            dataType: "json",
            success: function (data) {
                if (data) {
                    $.each(data.zjzl, function (index, item) {
                        $('#qlrsfzjzl').append('<option value="' + item.DM + '">' + item.MC + '</option>');
                        $('#qlrdlrzjzl').append('<option value="' + item.DM + '">' + item.MC + '</option>');
                        $('#qlrfddbrzjzl').append('<option value="' + item.DM + '">' + item.MC + '</option>');
                    });
                    $.each(data.gyfs, function (index, item) {
                        $('#gyfs').append('<option value="' + item.DM + '">' + item.MC + '</option>');
                    });
                }
            }
        });
        $.ajax({
            url: "/realestate-etl/wwsq/qlr",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {qlrid:qlrid},
            success: function (data) {
                if(data){
                    form.val("qlrForm",data);
                }
            }
        });
        $('.layui-select-disabled i').removeClass('layui-edge');
        $('.layui-select-disabled .layui-input').attr('disabled', 'true').removeClass('layui-disabled');

    });
});
