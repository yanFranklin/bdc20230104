var zdList = {};
var zdparam = "SZdBdcdyFwlxDO";
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
        tableReload('ljzList', postData)
        return false;
    })

    var tableConfig = {
        url: '../ljz/listbypage' //数据接口
        , cols: [[
            // {type: 'checkbox', fixed: 'left', align: 'center', width: '4%'},
            //要求先空着
            {field: 'syczt', title: '实预测状态', width: '13%'},
            {
                field: 'bdcdyh', title: '不动产单元号', width: '22%'
                , templet: function (d) {
                    return splitBdcdyh(d.bdcdyh);
                }
            },
            {field: 'fwmc', title: '项目名称', width: '13%'},
            {
                field: 'bdcdyfwlx', title: '房屋类型', width: '8%',
                templet: function (d) {
                    return convertZdDmToMc("SZdBdcdyFwlxDO", d.bdcdyfwlx);
                }
            },
            {field: 'dh', title: '幢号', width: '8%'},
            {field: 'zldz', title: '坐落', width: '28%'},
            {field: 'zcs', title: '总层数', width: '8%'},
            {field: 'dscs', title: '地上层数', width: '8%'},
            {field: 'dxcs', title: '地下层数', width: '8%'},
            {field: 'jgrq', title: '竣工时间', width: '10%'},
            //要求先空着
            {field: 'zt', title: '状态', width: '8%'},
            {
                title: '操作',
                // fixed: 'right',
                align: 'center',
                toolbar: '#ljzListToolBarTmpl',
                width: '8%'
            }
        ]]
    };
    //加载表格
    loadDataTablbeByUrl("#ljzList", tableConfig);

    // 获取session中保存的参数
    if (sessionParamObjet) {
        form.val("form", sessionParamObjet);
        $("#query").click();
    }
    if (sessionParamObjet.curpage) {
        table.reload('ljzList', {page: {curr: sessionParamObjet.curpage}});
    }

    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data && data.fw_dcb_index) {
            if (obj.event == "toLpbView") {
                saveSessionParam(getParamObject());
                toView(getIP() + '/lpb/view?code=bdcres&fwDcbIndex=' + data.fw_dcb_index,{tabname:"楼盘表"});
            }
        } else {
            layer.alert("当前数据主键缺失，请检查数据");
            return false
        }
    });

})

function refreshMainPage() {
    $("#query").click();
}

function getParamObject() {
    queryData.curpage = $(".layui-laypage-em").next().html();
    return queryData;
}