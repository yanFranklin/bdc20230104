var queryData = {};
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
    //提交表单
    form.on("submit(query)", function (data) {
        queryData = data.field;
        tableReload('xmxxList', data.field);
        return false;
    });

    var tableConfig = {
        toolbar: "#toolbarDemo"
        , url: '../xmxx/listbypage' //数据接口
        , cols: [[
            {type: 'checkbox', fixed: 'left', align: 'center', width: '4%'},
            {field: 'xmmc', title: '项目名称', width: '20%'
                , templet: function (d) {
                    var xmmc = d.xmmc;
                    if(!xmmc){
                        xmmc = "查看";
                    }
                    return '<div><a href="javascript:void(0);" ' +
                        'lay-event="editXmxx" class="layui-table-link">'+ xmmc +'</a></div>';
                }
            },
            {
                field: 'bdcdyh', title: '不动产单元号', width: '27%'
                , templet: function (d) {
                    return splitBdcdyh(d.bdcdyh);
                }
            },
            {field: 'zl', title: '坐落', width: '25%'},
            {
                field: 'bdczt', title: '状态', width: '10%',
                templet: function (d) {
                    var returnVal = "";
                    if (d.bdczt == "0") {
                        returnVal = "不可用"
                    } else if (d.bdczt == "1") {
                        returnVal = "可用"
                    } else if (d.bdczt) {
                        returnVal = d.bdczt
                    }
                    return returnVal;
                }
            },
            {title: '操作', align: 'center', fixed: 'right', toolbar: '#xmxxListToolBarTmpl', width: '15%'}
        ]]
    }

    //加载表格
    loadDataTablbeByUrl("#xmxxList", tableConfig);
    // 获取session中保存的参数
    if(sessionParamObjet){
        form.val("form", sessionParamObjet);
        $("#query").click();
    }
    if(sessionParamObjet.curpage){
        table.reload('xmxxList',{page: {curr: sessionParamObjet.curpage}});
    }


    //头工具栏事件
    table.on('toolbar(dataTable)', function (obj) {
        //获取选中行状态
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;
        if (obj.event === "mergeXmxx") {
            if (data && data.length > 1) {
                $.xmbg._xmxxHb(data);
            } else {
                layer.alert("请选择多条数据进行操作");
            }
        }
        if (obj.event === "delete") {
            if (data && data.length > 0) {
                layer.confirm('是否删除图层?', {
                    btn: ['是', '否','取消']
                }, function(index, layero){
                    deleteXmxx(data,true);
                }, function(index){
                    deleteXmxx(data,false);
                }, function(index){
                    return false;
                });
            } else {
                layer.alert("请选择至少一条数据进行操作");
            }
        }
    });
    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data && data.fwXmxxIndex) {
            if (obj.event === "editXmxx") { //修改
                saveSessionParam(getParamObject());
                toView(getIP()+'/xmxx/form?fwXmxxIndex=' + data.fwXmxxIndex,{tabname:"项目信息"});
            }
            if (obj.event === "xmxxBg") { //变更
                saveSessionParam(getParamObject());
                $.xmbg._xmxxBg(data);
            }
            if (obj.event === "xmxxMs") { //灭失
                $.xmbg._xmxxMsView(data);
            }
            if (obj.event === "queryBgjl") { //变更记录查看
                layer.alert("该功能正在开发哟");
            }
        } else {
            layer.alert("当前数据主键缺失，请检查数据");
            return false
        }
    });

    function deleteXmxx(data,delFwK) {
        var fwXmxxIndexList = [];
        for (var i = 0; i < data.length; i++) {
            fwXmxxIndexList.push(data[i].fwXmxxIndex);
        }
        addModel();
        $.ajax({
            url: "../xmxx/delbyindex",
            type: "POST",
            data: "delFwK="+delFwK+"&fwXmxxIndexList=" + encodeURI(fwXmxxIndexList),
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

function getParamObject(){
    queryData.curpage = $(".layui-laypage-em").next().html();
    return queryData;
}