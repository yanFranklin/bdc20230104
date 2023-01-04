var layerIndex = "";
layui.use(['jquery', 'layer', 'laytpl', 'table'], function () {
    var laytpl = layui.laytpl;
    var table = layui.table;
    layerIndex = parent.layer.getFrameIndex(window.name);

    //第一个实例
    var tableConfig = {
        url: '../xmxxbg/hblist?fwXmxxIndexList=' + encodeURI(fwXmxxIndexList)
        , page: false //开启分页
        , cols: [[ //表头
            {
                field: 'xmmc',
                title: '项目名称',
                width: '25%',
                fixed: 'left'
                ,
                templet: '<div><a href="javascript:void(0);" onclick="" lay-event="getMain" class="layui-table-link">{{d.xmmc}}</a></div>'
            },
            {field: 'bdcdyh', title: '不动产单元号', width: '30%'
                ,templet: function (d) {
                    return splitBdcdyh(d.bdcdyh);
                }
            },
            {field: 'zl', title: '坐落', width: '45%'}
        ]]
    }

    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data) {
            if (obj.event === "getMain") {
                parent.$.xmbg._xmxxHbSetMainIndex(data.fwXmxxIndex, data.zl);
            }
        } else {
            layer.alert("当前数据主键缺失，请检查数据");
            return false
        }
    });

    //加载表格
    loadDataTablbeByUrl("#hbList", tableConfig);
})