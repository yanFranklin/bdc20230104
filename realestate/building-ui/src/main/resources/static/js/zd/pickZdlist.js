var fwDcbIndex = $("#fwDcbIndex").val();
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
        tableReload('zdList', data.field);
        return false;
    });

    var tableConfig = {
        url: '../zd/listbypage' //数据接口
        , limit: 5
        , cols: [[
            // {type: 'numbers', fixed: 'left'},
            {
                field: 'djh', title: '地籍号', width: '25%',
                templet: function (d) {
                    var djh = splitDjh(d.djh);
                    return '<div><a href="javascript:void(0);" onclick="" ' +
                        'lay-event="pickZdxx" class="layui-table-link">'+djh+'</a></div>';
                }

            },
            {field: 'qlr', title: '权利人', width: '24%'},
            {field: 'zl', title: '土地坐落', width: '24%'},
            {field: 'fzmj', title: '发证面积(㎡)', width: '13%'},
            {field: 'scmj', title: '实测面积(㎡)', width: '14%'}
        ]]
    };

    //加载表格
    loadDataTablbeByUrl("#zdList", tableConfig);

    //父页面的该页面索引
    var index = parent.layer.getFrameIndex(window.name);
    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data) {
            if (obj.event === "pickZdxx") {
                if (parent.pickZdlistCallback) {
                    parent.pickZdlistCallback(data.djh)
                    parent.layer.close(index);
                }
            }
        } else {
            layer.alert("当前数据主键缺失，请检查数据");
            return false
        }
    });
})