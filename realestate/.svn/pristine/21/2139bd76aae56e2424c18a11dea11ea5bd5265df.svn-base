var ipaddress;
// 当前登录用户中文名
var useralias;

layui.use(['form', 'jquery', 'element', 'table'], function () {
    var $ = layui.jquery, form = layui.form, table = layui.table;

});

$("#submit").on('click',function () {
    var num = $("#num").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var start = new Date(startDate);
    var end = new Date(endDate)
    if (num<=0 || num > 200){
        warnMsg("导出范围在1-200！！");
        return;
    }
    if ((startDate===""||startDate==null) && (endDate!=="" || endDate ==null)){
        warnMsg("请输入起始时间！！");
        return;
    }
    if (startDate !== "" && endDate !== ""){
        if (start.getTime()-end.getTime()>0){
            warnMsg("结束时间需大于起始时间！！");
            return;
        }
    }
    $.ajax({
        url : "/realestate-inquiry-ui/rest/v1.0/export",
        type: "GET",
        data: {
            num:num,
            startDate:startDate,
            endDate:endDate
        },
        dataType : "json",
        success:function (res) {
            if (res.data===null){
                warnMsg("未找到可导出数据!");
                return;
            }
            exportDatas(res);
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        error: function () {
            errorsMsg("系统异常");
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    });
})


function exportDatas(res) {
    var width = new Array();
    width.push(25);
    width.push(40);
    width.push(30);
    width.push(40);
    width.push(40);
    width.push(20);
    var showColsField = new Array();
    showColsField.push("service");
    showColsField.push("method");
    showColsField.push("duration");
    showColsField.push("name");
    showColsField.push("url");
    showColsField.push("count");
    var showColsTitle = new Array();
    var colName = res.data.colNameList;
    for (var i =0;i<colName.length;i++){
        showColsTitle.push(colName[i]);
    }
    $("#fileName").val(res.data.filedName);
    $("#sheetName").val(res.data.filedName);
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(width);
    $("#cellKey").val(showColsField);
    var dataExport = new Array();
    var valList = res.data.valList;
    for (var i=0;i<valList.length;i++){
        var list = {};
        list.service = valList[i][0];
        list.method = valList[i][1];
        list.duration = valList[i][2];
        list.name = valList[i][3]
        list.url = valList[i][4];
        list.count = valList[i][5];
        dataExport.push(list);
    }
    $("#data").val(JSON.stringify(dataExport));
    $("#form").submit();
    setTimeout(function () {
        removeModal();
    }, 2000);
}

function exportDuration() {
    layer.open({
        title: '导出数据量',
        type: 2,
        content: "/realestate-inquiry-ui/view/zipkin/zipKin.html",
        area: ['400px', '280px'],
        success: function(index, layero){
            removeModal();
            layer.close(index)
        },
        cancel: function (index, layero){
            removeModal();
            layer.close(index);
        }
    });
}

