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
        tableReload('ljzList', postData);
        setElementAttrByModuleAuthority(moduleCode);
        return false;
    })

    var tableConfig = {
        toolbar: "#toolbarDemo"
        , url: '../ljz/listbypage?sfyc=' + sfyc //数据接口
        , cols: [[
            {type: 'checkbox', fixed: 'left', align: 'center', width: '4%'},
            {field: 'fwmc', title: '房屋名称', width: '23%'},
            {field: 'zrzh', title: '自然幢号', width: '8%'},
            {
                field: 'bdcdyfwlx', title: '房屋类型', width: '8%',
                templet: function (d) {
                    return convertZdDmToMc("SZdBdcdyFwlxDO", d.bdcdyfwlx);
                }
            },
            {field: 'zldz', title: '坐落', width: '43%'},
            {
                title: '操作',
                fixed: 'right',
                align: 'center',
                toolbar: '#ljzListToolBarTmpl',
                width: '14%'
            }
        ]],
        done: function () {
            if (!sfyc) {
                //不是预测不展现推送预测楼盘表按钮
                $('.tsycljz').addClass('layui-hide');
            }
            setElementAttrInTableByModuleAuthority(moduleCode);
        }
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

    //头工具栏事件
    table.on('toolbar(dataTable)', function (obj) {
        //获取选中行状态
        var checkStatus = table.checkStatus(obj.config.id);
        //获取选中行数据,目前只为单选，不考虑多条数据情况
        var data = checkStatus.data;
        if (data && data.length === 1) {
            if (obj.event === "addHs") { //新增户室
                saveSessionParam(getParamObject());
                addFwhs(data[0].fw_dcb_index);
            }
            if (obj.event === "addYchs") { //新增预测户室
                saveSessionParam(getParamObject());
                addYcfwhs(data[0].fw_dcb_index);
            }
            if (obj.event === "importLpb") {
                importLpb(data[0].fw_dcb_index);
                return false;
            }
            if (obj.event === "importYcLpb") {
                importYcLpb(data[0].fw_dcb_index);
                return false;
            }
            if (obj.event === "exportLpb") {
                exportLpb(data[0].fw_dcb_index);
                return false;
            }
            if (obj.event === "importScjzmj") {
                importScjzmj(data[0].fw_dcb_index);
                return false;
            }
            if (obj.event === "delEvent") {
                layer.confirm('确定删除？', function (index) {
                    deleteLjzFun(data);
                });
                return false;
            }
        } else if (obj.event === "delEvent") {
            layer.confirm('确定删除？', function (index) {
                deleteLjzFun(data);
            });
            return false;
        } else if (obj.event.indexOf("LAYTABLE") < 0 && obj.event.indexOf("LAYTABLE_COLS") < 0) {
            layer.alert("请选择一条数据进行操作");
        } else if (obj.event.indexOf("LAYTABLE_COLS") >= 0) {
        } else {
            layer.alert("请选择一条数据进行操作");
        }
    });
    setElementAttrByModuleAuthority(moduleCode);
    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data && data.fw_dcb_index) {
            if (obj.event === "editLjz") { //修改
                saveSessionParam(getParamObject());
                toView(getIP() + '/ljz/saveorupdateljz?fwDcbIndex=' + data.fw_dcb_index, {tabname: "逻辑幢信息"});
            }
            if (obj.event === "buildLpb") { //构建楼盘表
                buildLpb(data);
            }
            if (obj.event == "toLpbView") {
                saveSessionParam(getParamObject());
                toView(getIP() + '/lpb/view?code=bdcres&fwDcbIndex=' + data.fw_dcb_index, {tabname: "楼盘表"});
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
            if (obj.event === "ycToSc") {//预测转实测
                yczsc(data.fw_dcb_index);
            }
            if (obj.event === "ycScGl") {
                saveSessionParam(getParamObject());
                ycscgl(data.fw_dcb_index);
            }
            if (obj.event == "zlsx") {
                zlsx(data.fw_dcb_index);
            }
            if (obj.event === "jsjzmj") { // 计算建筑面积
                jsLjzJzmj(data.fw_dcb_index);
            }
            if (obj.event === "jsfttdmj") { // 计算分摊土地面积
                jsLjzFttdmj(data.fw_dcb_index);
            }
            if (obj.event === "txdw") { // 图形定位
                if (data.bdcdyh) {
                    //从逻辑幢页面打开的数据bdclx为2
                    window.open('/building-ui/omp/redirect/' + data.bdcdyh + '/2');
                } else {
                    layer.alert("不动产单元号为空无法查看图形定位");
                }
            }
            if (obj.event === "djdcb") { // 地籍调查表
                if (data.bdcdyh) {
                    window.open('/building-ui/djdcb/fromljz?bdcdyh=' + data.bdcdyh);
                } else {
                    layer.alert("不动产单元号为空无法查看地籍调查表");
                }
            }
            if (obj.event === "tsycljz") {
                addModel();
                tsycLjz(data.fw_dcb_index);
            }
            if (obj.event === "hbfh") {
                // window.open(getIP() + '/lpb/view?code=hslist&fwDcbIndex=' + data.fw_dcb_index);
                toView(getIP() + '/lpb/view?code=hslist&fwDcbIndex=' + data.fw_dcb_index, {tabname: "楼盘表"});
            }
        } else {
            layer.alert("当前数据主键缺失，请检查数据");
            return false
        }
    });

    function deleteLjzFun(data) {
        addModel();
        var fwDcbIndexList = [];
        for (var i = 0; i < data.length; i++) {
            fwDcbIndexList.push(data[i].fw_dcb_index);
        }
        $.ajax({
            url: "../ljz/delbyindex",
            dataType: "json",
            data: "fwDcbIndexList=" + encodeURI(fwDcbIndexList),
            success: function (data) {
                layer.closeAll();
                removeModal();
                if (!data || !data.success) {
                    layer.alert("删除失败");
                }
                refreshMainPage();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr, this)
            }
        });
    }
})

function refreshMainPage() {
    $("#query").click();
    setElementAttrByModuleAuthority(moduleCode);
}

function getParamObject() {
    queryData.curpage = $(".layui-laypage-em").next().html();
    return queryData;
}