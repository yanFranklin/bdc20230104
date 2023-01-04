
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var tableConfig = {
        url: '../djdcb/jzxx/listbypage?bdcdyh='+bdcdyh+"&qjgldm="+qjgldm //数据接口
        , cols: [[
            {field: 'jzdh', title: '界址点号'},
            {field: 'xzbz', title: 'X轴坐标'},
            {field: 'yzbz', title: 'Y轴坐标'}
        ]],
        parseData: function (res) { //res 即为原始返回的数据
            if(res.content.length == 1 && res.content[0] == null){
                res.content = [];
            }
            return res;
        },
        done: function(res, curr, count){
            if (!res.hasContent) {
                $("#zdjzxxTable .layui-table-page").addClass("layui-hide");
                $('#zdjzxxTable .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
            }
        }
    };
    //加载表格
    loadDataTablbeByUrl("#zdjzxxList", tableConfig);
})