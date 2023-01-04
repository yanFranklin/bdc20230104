var lszd = $("#lszd").val();
//绑定回车键
$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $("#query").click();
    }
});
layui.use(['jquery', 'element', 'form', 'table', 'laytpl'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    //提交表单
    form.on("submit(query)", function (data) {
        tableReload('zrzList', data.field);
        return false;
    });

    var tableConfig = {
        url: '../zrz/listbypage'
        , limit: 5
        , cols: [[
            {field: 'zrzh', title: '自然幢号',
                templet: function (d) {
                    return '<div><a href="javascript:void(0);" onclick="" ' +
                        'lay-event="pickZrz" class="layui-table-link">'+d.zrzh+'</a></div>';
                }
            },
            {
                field: 'lszd', title: '隶属宗地',
                templet: function (d) {
                    return splitDjh(d.lszd || '');
                }
            },
            {field: 'zldz', title: '坐落地址'},
            {field: 'fwmc', title: '房屋名称'},
            {field: 'fwcs', title: '房屋层数'}
        ]]
    }

    //加载表格
    loadDataTablbeByUrl("#zrzList", tableConfig);
    $("#query").click();

    //父页面的该页面索引
    var index = parent.layer.getFrameIndex(window.name);
    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data) {
            if (obj.event === "pickZrz") {
                if (parent.pickZrzCallBack) {
                    parent.pickZrzCallBack(data)
                    parent.layer.close(index);
                }
            }
        } else {
            layer.alert("当前数据主键缺失，请检查数据");
            return false
        }
    });

})