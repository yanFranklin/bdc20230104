var fwDcbIndex = $("#fwDcbIndex").val();
//绑定回车键
$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $("#query").click();
    }
});
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    //父页面的该页面索引
    var index = parent.layer.getFrameIndex(window.name);
    //提交表单
    form.on("submit(query)", function (data) {
        var postData = data.field;
        postData.fwDcbIndex = fwDcbIndex;
        tableReload('tableList', data.field);
        return false;
    })

    var tableConfig = {
        url: '../fwychs/listbypage'
        , where: {
            fwDcbIndex: fwDcbIndex
        }
        , limit: 7
        , limits: [7, 10, 15, 20]
        , cols: [[
            {type: 'numbers', fixed: 'left', width: '4%'},
            {
                field: 'fjh', title: '房间号',
                templet: '<div><a href="javascript:void(0);" onclick="" lay-event="pickFwhs" class="layui-table-link">{{d.fjh}}</a></div>',
                width: '9%'
            },
            {field: 'dycs', title: '定义层数', width: '9%'},
            {field: 'sxh', title: '室序号', width: '9%'},
            {field: 'dyh', title: '单元号', width: '9%'},
            {field: 'bdcdyh', title: '不动产单元号', width: '25%'},
            {field: 'fwbm', title: '房屋编码', width: '10%'},
            {field: 'zl', title: '坐落', width: '25%'}
        ]]
    }
    //加载表格
    loadDataTablbeByUrl("#tableList", tableConfig);

    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data) {
            if (obj.event === "pickFwhs") {
                //选择房屋户室
                delete data.fwHsIndex;
                delete data.bdcdyh;
                delete data.fwbm;
                if (parent.jchsxx) {
                    parent.jchsxx(data);
                    parent.layer.close(index);
                }
            }
        } else {
            layer.alert("当前数据主键缺失，请检查数据");
            return false
        }
    });

})
