var fwHsIndex = $("#fwHsIndex").val();
var zdList = {};
$.ajax({
    url: "../zd/mul",
    data: "zdDoNames=SZdFwlxDO,SZdHxjgDO",
    dataType: "json",
    async: false,
    success: function (data) {
        zdList = $.extend({}, data);
    }
});

// 编辑页面回调函数
function reloadTable(layerIndex){
    layer.close(layerIndex);
    layer.msg("提交成功");
    tableReload('fwzhsList');
}

layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    var tableConfig = {
        toolbar: "#toolbar"
        , url: '../fwzhs/listbypage?fwHsIndex=' + fwHsIndex //数据接口
        , cols: [[
            {type: 'checkbox', fixed: 'left', align: 'center', width: '4%'},
            {field: 'fjh', title: '房间号', width: '7%'},
            {field: 'dyh', title: '单元号', width: '7%'},
            {field: 'wlcs', title: '物理层数', width: '7%'},
            {field: 'scjzmj', title: '实测建筑面积(㎡)', width: '10%'},
            {field: 'sctnjzmj', title: '实测套内建筑面积(㎡)', width: '12%'},
            {field: 'scftjzmj', title: '实测分摊建筑面积(㎡)', width: '12%'},
            {field: 'ycjzmj', title: '预测建筑面积(㎡)', width: '10%'},
            {field: 'yctnjzmj', title: '预测套内建筑面积(㎡)', width: '12%'},
            {field: 'ycftjzmj', title: '预测分摊建筑面积(㎡)', width: '12%'},
            {
                field: 'fwlx', title: '房屋类型', width: '10%',
                templet: function (d) {
                    return convertZdDmToMc("SZdFwlxDO", d.fwlx);
                }
            },
            {
                field: 'hxjg', title: '户型结构', width: '10%',
                templet: function (d) {
                    return convertZdDmToMc("SZdHxjgDO", d.hxjg);
                }
            },
            {title: '操作', align: 'center', fixed: 'right', width: '5%', toolbar: '#fwzhsListToolBarTmpl'}
        ]]
    }

    //加载表格
    loadDataTablbeByUrl("#fwzhsList", tableConfig);

    //头工具栏事件
    table.on('toolbar(dataTable)', function (obj) {
        //获取选中行状态
        var checkStatus = table.checkStatus(obj.config.id);
        //获取选中行数据,目前只为单选，不考虑多条数据情况
        var data = checkStatus.data;
        // 删除子户室
        if (obj.event === "delzhs") {
            if (data && data.length >= 1) {
                layer.confirm('确认删除？', function (index) {
                    var fwZhsIndexList = [];
                    for (var i = 0; i < data.length; i++) {
                        fwZhsIndexList.push(data[i].fwZhsIndex);
                    }
                    deleteFwzhsFun(fwZhsIndexList);
                });
            }else{
                layer.msg("至少选择一条数据");
            }
        }
        // 新增子户室
        if (obj.event === "addzhs") {
            // 打开合并页面
            layer.open({
                type: 2,
                title: "新增子户室",
                area: ['1000px', '500px'],
                content: "../fwzhs/info?fwHsIndex="+fwHsIndex
                , end: function (index, layero) {
                    tableReload('fwzhsList');
                    return false;
                }
            });
        }
    });

    // 修改子户室
    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (obj.event === "updatezhs") {
            if (data && data.fwZhsIndex) {
                var zhsIndex = data.fwZhsIndex;
                layer.open({
                    type: 2,
                    title: "修改子户室",
                    area: ['100%', '100%'],
                    content: "../fwzhs/info?fwHsIndex=" + fwHsIndex + "&fwZhsIndex=" + zhsIndex
                    , end: function (index, layero) {
                        tableReload('fwzhsList');
                        return false;
                    }
                });
            }else{
                layer.msg("子户室主键缺失");
            }
        }
    })

    function deleteFwzhsFun(fwZhsIndexList) {
        addModel();
        $.ajax({
            url: "../fwzhs/batchdel",
            type: "post",
            data: {
                fwZhsIndexListJson: JSON.stringify(fwZhsIndexList)
            },
            success: function (data) {
                removeModal();
                layer.closeAll();
                if (!data || !data.success) {
                    layer.alert("删除失败");
                }
                tableReload('fwzhsList');
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }
})