var lszd = $("#lszd").val();

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
        tableReload('tableList', data.field);
        return false;
    });
    var param = "";
    if (lszd) {
        param = "?lszd=" + lszd;
    }
    var tableConfig = {
        toolbar: "#toolbarDemo"
        , url: '../xmxx/listbypage' + param  //数据接口
        , cols: [[
            {type: 'radio', fixed: 'left', align: 'center', width: '5%'},
            {field: 'xmmc', title: '项目名称', width: '20%'},
            {
                field: 'bdcdyh', title: '不动产单元号', width: '26%'
                , templet: function (d) {
                    return splitBdcdyh(d.bdcdyh);
                }
            },
            {field: 'zl', title: '坐落', width: '40%'},
            {
                field: 'bdczt', title: '状态', width: '10%',
                templet: function (d) {
                    var returnVal = ""
                    if (d.bdczt == "0") {
                        returnVal = "不可用"
                    } else if (d.bdczt == "1") {
                        returnVal = "可用"
                    } else if (d.bdczt) {
                        returnVal = d.bdczt
                    }
                    return returnVal;
                }
            }
        ]]
    }

    //加载表格
    loadDataTablbeByUrl("#tableList", tableConfig);

    //头工具栏事件
    table.on('toolbar(dataTable)', function (obj) {
        //获取选中行状态
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;
        if (obj.event === "chooseXmxx" && data.length === 1) {
            layer.confirm('确定变更不动产房屋类型并挂接到当前项目吗？', function (index) {
                pickThisFwXmxx(data[0].fwXmxxIndex);
            });
        } else if (obj.event === "addXmxx") {
            toView(getIP() + '/xmxx/form?lszd=' + lszd + '&bdcdyhfwlxBg=bdcdyhfwlxBg', {tabname: "新增项目"});
        }else {
            layer.msg("请选择项目")
        }
    });
});

function pickThisFwXmxx(fwXmxxIndex) {
    var postData = $.extend({}, bginfo);
    if (postData.fwDcbIndex) {
        postData.fwXmxxIndex = fwXmxxIndex;
        // loading加载
        addModel();
        $.ajax({
            url: "../bdcdyfwlxbg/fwlxbg",
            dataType: "json",
            data: postData,
            success: function (data) {
                removeModal();
                layer.alert("修改成功");
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    } else {
        layer.alert("无法获取变更逻辑幢信息");
    }
}