var zdList = {};
var zdparam = "SZdBdcdyFwlxDO";
var moduleCode = $("#moduleCode").val();
var queryData = {};
$.ajax({
    url: "../zd/mul",
    data: "zdDoNames=" + zdparam,
    dataType: "json",
    async: false,
    success: function (data) {
        zdList = $.extend({}, data);
    }
});

//绑定回车键
$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        refreshMainPage();
    }
});
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'upload'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var upload = layui.upload;
    //提交表单
    form.on("submit(query)", function (data) {
        var postData = data.field;
        queryData = data.field;
        tableReload('tdlpbList', postData);
        setElementAttrByModuleAuthority(moduleCode);
        return false;
    })

    var tableConfig = {
        toolbar: "#toolbarDemo"
        , url: '../tdlpb/listbypage?' //数据接口
        , cols: [[
            {type: 'checkbox', fixed: 'left', align: 'center', width: '4%'},
            {field: 'DJH', title: '地籍号', width: '23%'},
            {field: 'QLRMC', title: '权利人', width: '8%'},
            {field: 'ZL', title: '坐落', width: '33%'},
            {field: 'SCMJ', title: '实测面积', width: '12%'},
            {field: 'FZMJ', title: '发证面积', width: '12%'},
            {
                title: '操作',
                fixed: 'right',
                align: 'center',
                toolbar: '#tdlpbListToolBarTmpl',
                width: '8%'
            }


        ]],
        done: function () {

            setElementAttrInTableByModuleAuthority(moduleCode);
        }
    };
    //加载表格
    loadDataTablbeByUrl("#tdlpbList", tableConfig);

    // 获取session中保存的参数
   /* if (sessionParamObjet) {
        form.val("form", sessionParamObjet);
        $("#query").click();
    }
    if (sessionParamObjet.curpage) {
        table.reload('tdlpbList', {page: {curr: sessionParamObjet.curpage}});
    }*/

    //头工具栏事件
    table.on('toolbar(dataTable)', function (obj) {
    });
    setElementAttrByModuleAuthority(moduleCode);
    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data && data.DJH) {
            if (obj.event == "toLpbView") {
               // saveSessionParam(getParamObject());
                toView(getIP() + '/tdlpb/view?code=bdcres&djh=' + data.DJH, {tabname: "土地楼盘表"});
            }
            if (obj.event === "fwlxBg") { //房屋类型变更
                var index = layer.open({
                    type: 2,
                    title: "房屋类型变更",
                    area: ['400px', '310px'],
                    fixed: false, //不固定
                    content: '../bdcdyfwlxbg/view?fwDcbIndex=' + data.fw_dcb_index
                    , end: function (index, layero) {
                        refreshMainPage();
                        return false;
                    }
                });
            }

        } else {
            layer.alert("当前数据主键缺失，请检查数据");
            return false
        }
    });

})

function refreshMainPage() {
    $("#query").click();
    setElementAttrByModuleAuthority(moduleCode);
}

function getParamObject() {
    queryData.curpage = $(".layui-laypage-em").next().html();
    return queryData;
}