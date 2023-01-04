//绑定回车键
$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $("#query").click();
    }
});

var zdList = {};
var zdparam = "SZdFwlxDO,SZdFwytDO";
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

layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    //提交表单
    form.on("submit(query)", function (data) {
        queryData = data.field;
        tableReload('fwhsList', data.field);
        return false;
    });

    var tableConfig = {
        toolbar: "#toolbarDemo"
        , url: '../fwhs/listbypage' //数据接口
        , cols: [[
            {type: 'checkbox', fixed: 'left', align: 'center', width: '50'},
            {field: 'fjh', title: '房间号', width: '80'
                , templet: function (d) {
                    var view = d.fjh;
                    if(!view){
                        view = "查看";
                    }
                    return '<div><a href="javascript:void(0);" ' +
                        'lay-event="edit" class="layui-table-link">'+ view +'</a></div>';
                }
            },
            {field: 'dyh', title: '单元号', width: '80'},
            {field: 'wlcs', title: '物理层数', width: '100'},
            {
                field: 'bdcdyh', title: '不动产单元号', width: '300'
                , templet: function (d) {
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
            {field: 'jzmj', title: '实测建筑面积(单位:㎡)', width: '110'},
            {field: 'scftjzmj', title: '实测分摊建筑面积(单位:㎡)', width: '110'},
            {field: 'sctnjzmj', title: '实测套内建筑面积(单位:㎡)', width: '110'},
            {field: 'ycjzmj', title: '预测建筑面积(单位:㎡)', width: '110'},
            {field: 'yctnjzmj', title: '预测套内建筑面积(单位:㎡)', width: '110'},
            {field: 'ycftjzmj', title: '预测分摊建筑面积(单位:㎡)', width: '110'},

            {title: '操作', align: 'center', fixed: 'right', width: '100', toolbar: '#fwhsListToolBarTmpl'}
        ]]
    };

    //加载表格
    loadDataTablbeByUrl("#fwhsList", tableConfig);

    // 获取session中保存的参数
    if(sessionParamObjet){
        form.val("form", sessionParamObjet);
        $("#query").click();
    }
    if(sessionParamObjet.curpage){
        table.reload('fwhsList',{page: {curr: sessionParamObjet.curpage}});
    }

    //头工具栏事件
    table.on('toolbar(dataTable)', function (obj) {
        //获取选中行状态
        var checkStatus = table.checkStatus(obj.config.id);
        //获取选中行数据,目前只为单选，不考虑多条数据情况
        var data = checkStatus.data;
        if (data && data.length > 0) {
            if (obj.event === "hshb") { // 合并户室
                if (data.length > 1) {
                    saveSessionParam(getParamObject());
                    $.hsgb._fwhsHb(data);
                } else {
                    layer.alert("请选择多条数据进行合并操作");
                }
                return false;
            }
            if (obj.event == "plgxmj") { // 批量更新面积
                plgxmjFun(data);
                return false
            }
            if (obj.event == "plgxsx") { // 批量更新属性
                plgxsxFun(data);
                return false;
            }
            if (obj.event == "uploadHst") { // 上传户室图
                hstToManyHsView(data);
                return false;
            }
            if (obj.event === "delete") { //删除
                layer.confirm('确定删除？', function (index) {
                    deletefwhs(data);
                    return false;
                });
            }
        } else {
            if (obj.event == "uploadHst") { // 户室图上传（文件夹）
                layer.msg("挂文件夹");
                return false;
            } else if (obj.event.indexOf("LAYTABLE") < 0) {
                layer.msg("请选择一条数据进行操作");
            }
        }
    });
    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data && data.fw_hs_index) {
            if (obj.event === "edit") { //修改
                fwhsUpdate(data);
                return false;
            }
            if (obj.event === "hscf") { //户室拆分
                saveSessionParam(getParamObject());
                $.hsbg._fwhsCf(data);
            }
            if (obj.event === "hsms") { //户室灭失
                saveSessionParam(getParamObject());
                $.hsbg._fwhsMs(data);
            }
            if (obj.event === "hsbg") { //户室变更
                // 户室变更 填写变更信息
                saveSessionParam(getParamObject());
                $.hsbg._fwhsBg(data);
            }
            if (obj.event === "pickLjz") { //修改逻辑幢
                updateLjzView(data);
                return false
            }
            if (obj.event == "fwhsHistory") {// 历史记录
                fwHsHistory(data);
                return false
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

function getParamObject(){
    queryData.curpage = $(".layui-laypage-em").next().html();
    return queryData;
}