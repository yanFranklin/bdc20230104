
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
        tableReload('zdList', data.field);
        return false;
    });
    var tableConfig = {
        toolbar: "#toolbarDemo"
        , url: '../zd/listbypage' //数据接口
        , cols: [[
            {type: 'radio', fixed: 'left', align: 'center'},
            {
                field: 'bdcdyh', title: '不动产单元号', width: '26%'
                , templet: function (d) {
                    return splitBdcdyh(d.bdcdyh);
                }
            },
            {field: 'qlr', title: '权利人', width: '8%'},
            {field: 'zl', title: '土地坐落', width: '25%'},
            {field: 'scmj', title: '实测面积', width: '10%'},
            {field: 'fzmj', title: '发证面积', width: '10%'},
            {
                title: '操作',
                fixed: 'right',
                align: 'center',
                toolbar: '#zdListToolBarTmpl',
                width:'16%'
            }
        ]]
    };
    //加载表格
    loadDataTablbeByUrl("#zdList", tableConfig);

    // 获取session中保存的参数
    if(sessionParamObjet){
        form.val("form", sessionParamObjet);
        $("#query").click();
    }
    if(sessionParamObjet.curpage){
        table.reload('zdList',{page: {curr: sessionParamObjet.curpage}});
    }

    //头工具栏事件
    table.on('toolbar(dataTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;
        if (data && data.length === 1) {
            if (obj.event === "addLjz") {
                saveSessionParam(getParamObject());
                toView(getIP()+'/ljz/saveorupdateljz?djh=' + data[0].djh,{tabname:"逻辑幢"});
            }
            if (obj.event === "addXmxx") {
                saveSessionParam(getParamObject());
                toView(getIP()+'/xmxx/form?lszd=' + data[0].djh,{tabname:"项目信息"});
            }
        } else if (obj.event.indexOf("LAYTABLE") < 0) {
            layer.alert("请选择一条数据进行操作")
        }
    });

    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data && data.djh) {
            if (obj.event === "viewLpb") { //修改
                var djh = data.djh.trim();
                if(djh){
                    $.ajax({
                        url: "../ljz/listbypage",
                        dataType: "json",
                        data: "lszd=" + djh,
                        success: function (data) {
                            if(data && data.content.length > 0){
                                saveSessionParam(getParamObject());
                                toView(getIP() + '/lpb/view?code=default&djh=' + djh,{tabname:"楼盘表"});
                            }else{
                                layer.alert("宗地下找不到逻辑幢！");
                            }
                        },
                        error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr,this)
                        }
                    });
                }
            }
            if(obj.event === "xmxxList"){
                var djh = data.djh.trim();
                saveSessionParam(getParamObject());
                toView(getIP() + '/xmxx/list?lszd=' + djh,{tabname:"项目信息列表"});
            }
        } else {
            layer.alert("当前数据主键缺失，请检查数据");
            return false
        }
    });
})

function getParamObject(){
    queryData.curpage = $(".layui-laypage-em").next().html();
    return queryData;
}