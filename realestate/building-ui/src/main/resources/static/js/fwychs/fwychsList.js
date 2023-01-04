var zdList = {};
var zdparam = "SZdFwlxDO,SZdFwytDO";
$.ajax({
    url: "../zd/mul",
    data: "zdDoNames=" + zdparam,
    dataType: "json",
    async: false,
    success: function (data) {
        zdList = $.extend({}, data);
    }
});
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    //提交表单
    form.on("submit(query)", function (data) {
        tableReload('fwychsList', data.field);
        return false;
    });

    var tableConfig = {
        toolbar: "#toolbarDemo"
        , url: '../fwychs/listbypage' //数据接口
        , cols: [[
            {type: 'checkbox', fixed: 'left', align: 'center', width: '50'},
            {field: 'fjh', title: '房间号', width: '80'
                , templet: function (d) {
                    var view = d.fjh;
                    if(!view){
                        view = "查看";
                    }
                    return '<div><a href="javascript:void(0);" ' +
                        'lay-event="editYchs" class="layui-table-link">'+ view +'</a></div>';
                }
            },
            {field: 'dyh', title: '单元号', width: '80'},
            {field: 'wlcs', title: '物理层数', width: '100'},
            {field: 'bdcdyh', title: '不动产单元号', width: '300'
                ,templet: function (d) {
                    return splitBdcdyh(d.bdcdyh);
                }
            },
            {field: 'zl', title: '坐落', width: '300'},
            {field: 'qlr', title: '权利人', width: '100'},
            {
                field: 'ghyt', title: '规划用途', width: '100',
                templet: function (d) {
                    return convertZdDmToMc("SZdFwytDO", d.ghyt);
                }
            },
            {
                field: 'fwlx', title: '房屋类型', width: '100',
                templet: function (d) {
                    return convertZdDmToMc("SZdFwlxDO", d.fwlx);
                }
            },
            {field: 'scjzmj', title: '实测建筑面积(㎡)', width: '110'},
            {field: 'scftjzmj', title: '实测分摊建筑面积(㎡)', width: '110'},
            {field: 'sctnjzmj', title: '实测套内建筑面积(㎡)', width: '110'},
            {field: 'ycjzmj', title: '预测建筑面积(㎡)', width: '110'},
            {field: 'yctnjzmj', title: '预测套内建筑面积(㎡)', width: '110'},
            {field: 'ycftjzmj', title: '预测分摊建筑面积(㎡)', width: '110'}
            // {title: '操作', align: 'center', fixed: 'right', width: '100', toolbar: '#fwychsListToolBarTmpl'}
        ]]
    };

    //加载表格
    loadDataTablbeByUrl("#fwychsList", tableConfig);

    //头工具栏事件
    table.on('toolbar(dataTable)', function (obj) {
        //获取选中行状态
        var checkStatus = table.checkStatus(obj.config.id);
        //获取选中行数据,目前只为单选，不考虑多条数据情况
        var data = checkStatus.data;
        if (data && data.length > 0) {
            if (obj.event === "delete") { //删除
                layer.confirm('确定删除？', function (index) {
                    deleteYcfwhs(data);
                    return false
                });
            }
        }

    });
    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data && data.fw_hs_index) {
            if (obj.event === "editYchs") {
                fwychsUpdate(data);
                // var index = layer.open({
                //     type: 2,
                //     title: "编辑预测户室",
                //     maxmin: true,
                //     area: ['100%', '100%'],
                //     fixed: false, //不固定
                //     content: '../fwychs/info?fwDcbIndex=' + data.fw_dcb_index + "&fwHsIndex=" + data.fw_hs_index
                //     , end: function (index, layero) {
                //         refreshView();
                //         return false;
                //     }
                // });
                // layer.full(index)
                return false
            }
        } else {
            layer.alert("当前数据主键缺失，请检查数据");
            return false
        }
    });

    function deleteYcfwhs(data) {
        var fwHsIndexList = [];
        for (var i = 0; i < data.length; i++) {
            fwHsIndexList.push(data[i].fw_hs_index);
        }
        // loading加载
        addModel();
        $.ajax({
            url: "../fwychs/delbyindex",
            type: "GET",
            data: "fwHsIndexList=" + encodeURI(fwHsIndexList),
            success: function (data) {
                removeModal();
                layer.closeAll();
                if (!data || !data.success) {
                    layer.alert("删除失败");
                }
                $("#query").click();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }
})