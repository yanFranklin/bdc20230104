var zdList = {}
$.ajax({
    url: "../zd/mul",
    dataType: "json",
    data: {
        zdDoNames: "SZdFwhxDO,SZdJczxcdDO,SZdFwytDO,SZdDldmDO,SZdTdsyqlxDO,SZdFwlxDO,SZdFwxzDO,SZdQtgsDO,SZdBoolDO,SZdHxjgDO"
    },
    async: false,
    success: function (data) {
        zdList = $.extend({}, data)
    }
});
layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var element = layui.element;

    //处理列表选择
    var tpl = $("#DmMcTpl").html();
    $.each(zdList, function (key, value) {
        laytpl(tpl).render(value, function (html) {
            $("." + key).append(html);
        });
    })

    laydate.render({
        elem: '#qsrq'
    });
    laydate.render({
        elem: '#zzrq'
    });

    //form初始化
    form.render();

    if ($("#fwHsIndex").val()) {
        loadFwhs();
    }
    //页面入口
    function loadFwhs() {
        //获取数据
        addModel();
        var last = $("#last").val();
        $.ajax({
            url: "../fwhs/infohistoryfwhs",
            dataType: "json",
            data: {
                fwHsIndex: $("#fwHsIndex").val(),
            },
            success: function (data) {
                removeModal();
                form.val("form", data)
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }
});
