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
        tableReload('ljzList', data.field);
        return false;
    });

    var tableConfig = {
        url: '../ljz/listbypage',
        limit: 5,
        cols:
            [[
                // {type: 'numbers', fixed: 'left'},
                {
                    field: 'ljzh', title: '逻辑幢号',
                    templet: '<div><a href="javascript:void(0);" onclick="" lay-event="pickLjz" class="layui-table-link">{{d.ljzh||"选择"}}</a></div>'
                },
                {field: 'lszd', title: '隶属宗地', width: '20%'
                    ,templet: function (d) {
                        return splitDjh(d.lszd || '');
                    }
                },
                {field: 'zrzh', title: '自然幢号', width: '10%'},
                {field: 'zldz', title: '坐落地址', width: '20%'},
                {field: 'fwmc', title: '房屋名称', width: '18%'}
            ]]
    }

    //加载表格
    loadDataTablbeByUrl("#ljzList", tableConfig);

    $("#query").click();

    //父页面的该页面索引
    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data) {
            if (obj.event === "pickLjz") {
                //选择逻辑幢信息
                parent.relevanceLjz(data);
            }
        } else {
            layer.alert("当前数据主键缺失，请检查数据");
            return false
        }
    });
})