var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';

var form;
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form;

    $(function () {

        // 流程名称
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/process/gzldymcs",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            async: false,
            success: function (data) {
                $.each(data, function (index, item) {
                    $('#lcmc').append(new Option(item.name, item.name));// 下拉菜单里添加元素
                });
                form.render("select");
            }, error: function (e) {
                delAjaxErrorMsg(e, "加载失败");
            }
        });

    });

});
/**
 * 点击明细 查看项目信息
 * @param obj
 * @param data
 */
function mx(obj, data) {
    window.open("/realestate-portal-ui/view/new-page.html?name=" + data.GZLSLID + "&type=list", data.SLBH);
}

/**
 * 新增按钮 打开创建质检项目页面
 */
function create() {
    var index = layer.open({
        title: '新增质检',
        type: 2,
        content: "/realestate-inquiry-ui/view/zj/zjhc.html",
        fixed: true,
        area: ['960px', '575px'],
        btnAlign: 'c',
        success: function(layero, index) {

        },
        cancel: function(layero, index) {
        },
    });
    layer.full(index);
}