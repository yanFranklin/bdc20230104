
var layerIndex = "";
var reverseList = ['bdcdyh', 'zl'];
layui.use(['jquery', 'layer', 'laytpl','table'], function () {
    var laytpl = layui.laytpl;
    var table  = layui.table;
    //第一个实例
    var tableConfig = {
        url: '../fwhsbg/hblist?fwHsIndexList='+ encodeURI(fwhsIndexList)
        ,page: false //开启分页
        ,cols: [[ //表头
            {field: 'fjh', title: '房间号', width:80, fixed: 'left'
                ,templet: '<div><a href="javascript:void(0);" onclick="" lay-event="getMain" class="layui-table-link">{{d.fjh}}</a></div>'
            }
            ,{field: 'dyh', title: '单元号', width:80, fixed: 'left'}
            ,{field: 'wlcs', title: '物理层数', width:80, fixed: 'left'}
            ,{field: 'bdcdyh', title: '不动产单元号', width:200, fixed: 'left'
                ,templet: function (d) {
                    return splitBdcdyh(d.bdcdyh);
                }
            }
            , {field: 'zl', title: '坐落', width: 200, fixed: 'left'}
            , {field: 'scjzmj', title: '实测建筑面积', width: 150}
            , {field: 'sctnjzmj', title: '实测套内建筑面积', width: 150}
            , {field: 'scftjzmj', title: '实测分摊建筑面积', width: 150}
            , {field: 'ycjzmj', title: '预测建筑面积', width: 150}
            , {field: 'yctnjzmj', title: '预测套内建筑面积', width: 150}
            , {field: 'ycftjzmj', title: '预测分摊建筑面积', width: 150}
        ]]
        , done: function () {
            reverseTableCell(reverseList, "hbList");
        }
    }

    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data) {
            if (obj.event === "getMain") {
                parent.$.hsbg._fwhsHbSetMainIndex(data.fwHsIndex, data.zl, data.wlcs);
            }
        } else {
            layer.alert("当前数据主键缺失，请检查数据");
            return false
        }
    });

    //加载表格
    loadDataTablbeByUrl("#hbList", tableConfig);
})