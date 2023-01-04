layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

var rolename;
var roleid;
var exportdata = [];
layui.use(['jquery', 'element', 'form', 'table', 'laydate', 'formSelects'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;
    var formSelects = layui.formSelects;

   var width= $(document).width()/2;
   $(".none").css({"padding-left":width})
    laydate.render({
        elem: '#startTime',
        type: 'date',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#endTime').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/image/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#endTime',
        type: 'date',
        format: 'yyyy-MM-dd',
        value: new Date((new Date().getTime())),
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#startTime').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/image/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

    //获取当前登录的用户名是否为管理员

    $.ajax({
        //调用查询角色id和角色名的接口的地址
        url: '/realestate-inquiry-ui/bdczd/bm/list',
        type: "GET",
        dataType: "json",
        success: function (data) {
            rolename = data.username
            roleid = data.roleid
        }
    })


    //展示部门数据
    $.ajax({
        //此处调用部门接口
        url: '/realestate-inquiry-ui/rest/v1.0/lqcx/getQxdmByJs',
        type: "post",
        dataType: "json",
        success: function (data) {
            if (data) {
                var listData = new Array();
                for (var key in data) {
                    var item = {"name": data[key].MC, "value": data[key].DM}
                    listData.push(item);
                }
                formSelects.data('selectBmmc', 'local', {
                    arr: listData
                });
            }
        }
    });

    $("#search").click(function () {
        search();
    });
    $("#export").click(function () {
        exportTjsj();
    });
var LqJson={};
    function  search(){
        if(isNullOrEmpty($("#startTime").val()) || isNullOrEmpty($("#endTime").val())|| isNullOrEmpty(formSelects.value('selectBmmc', 'val'))){
            warnMsg("请输入开始时间、结束时间和区县代码");
            return;
        }
        addModel();
        $.ajax({
            //此处调用查询接口
            url: '/realestate-inquiry-ui/rest/v1.0/lqcx/tjcx',
            type: "post",
            // dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({kssj: $("#startTime").val(), jzsj: $("#endTime").val(),qxdmList:formSelects.value('selectBmmc', 'val')}),
            success: function (data) {
                if (data) {
                    LqJson= {data:data};
                    exportdata = data;
                    $("#lqtjtpl").css({"height":"600px","overflow":"scroll"});
                    renderTpl(LqJson, 'lqtjtpl', lqxxTpl.innerHTML);
                    removeModal()
                }
            }
        });

    }

    //渲染模块数据
    function renderTpl(json, mkid, getTpl) {
        var dataView = document.getElementById(mkid);
        laytpl(getTpl).render(json, function (html) {
            dataView.innerHTML = html;
            console.log(dataView)
        });
    }

    // 导出excel
    function exportTjsj(){
        if (exportdata.length == 0) {
            warnMsg("请先查询数据");
            return;
        }
        var showColsTitle = ['登记类型','集体林地数','集体林地面积','国有林地数','国有林地面积','合计','','其中','','','','','','','','','','抵押金额','办件量','发证数'];

        // 列内容
        var showColsField = ['djlxmc','jtlds','jtldmj','gylds','gyldmj','ldzs','ldzmj','lds1','ldmj1','lds2','ldmj2','lds3','ldmj3','lds4','ldmj4','lds5','ldmj5','dyje','bjl','fzs'];
        // 列宽
        var showColsWidth = [15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15];
        $.each(exportdata, function (index, item) {
            if (null ==item.jtlds) {
                item.jtlds = "—";
            }
            if (null ==item.jtldmj) {
                item.jtldmj = "—";
            }
            if (null ==item.gylds) {
                item.gylds = "—";
            }
            if (null ==item.gyldmj) {
                item.gyldmj = "—";
            }
            if (null ==item.ldzs) {
                item.ldzs = "—";
            }
            if (null ==item.ldzmj) {
                item.ldzmj = "—";
            }
            if (null ==item.lds1) {
                item.lds1 = "—";
            }
            if (null ==item.ldmj1) {
                item.ldmj1 = "—";
            }
            if (null ==item.lds2) {
                item.lds2 = "—";
            }
            if (null ==item.ldmj2) {
                item.ldmj2 = "—";
            }
            if (null ==item.lds3) {
                item.lds3 = "—";
            }
            if (null ==item.ldmj3) {
                item.ldmj3 = "—";
            }
            if (null ==item.lds4) {
                item.lds4 = "—";
            }
            if (null ==item.ldmj4) {
                item.ldmj4 = "—";
            }
            if (null ==item.lds5) {
                item.lds5 = "—";
            }
            if (null ==item.ldmj5) {
                item.ldmj5 = "—";
            }
            if (null ==item.dyje) {
                item.dyje = "—";
            }
            if (null ==item.bjl) {
                item.bjl = "—";
            }
            if (null ==item.fzs) {
                item.fzs = "—";
            }
        });
        var data = JSON.stringify(exportdata);
        doExport(showColsTitle, showColsField, showColsWidth, data);
    }


    function doExport(colsTitle, colsField, colsWidth, data){
        var tempForm = $("<form>");
        tempForm.attr("id", "exportForm");
        tempForm.attr("style", "display:none");
        tempForm.attr("target", "export");
        tempForm.attr("method", "post");
        tempForm.attr("action", '/realestate-inquiry-ui/excel/export');

        var fileNameInput = $("<input>");
        fileNameInput.attr("type", "hidden");
        fileNameInput.attr("name", "fileName");
        fileNameInput.attr("value", "宣州区林权类不动产登记业务统计表");
        tempForm.append(fileNameInput);

        var sheetNameInput = $("<input>");
        sheetNameInput.attr("type", "hidden");
        sheetNameInput.attr("name", "sheetName");
        sheetNameInput.attr("value", "宣州区林权类不动产登记业务统计表");
        tempForm.append(sheetNameInput);

        var cellTitleInput = $("<input>");
        cellTitleInput.attr("type", "hidden");
        cellTitleInput.attr("name", "cellTitle");
        cellTitleInput.attr("value", colsTitle);
        tempForm.append(cellTitleInput);

        var cellWidthInput = $("<input>");
        cellWidthInput.attr("type", "hidden");
        cellWidthInput.attr("name", "cellWidth");
        cellWidthInput.attr("value", colsWidth);
        tempForm.append(cellWidthInput);

        var cellKeyInput = $("<input>");
        cellKeyInput.attr("type", "hidden");
        cellKeyInput.attr("name", "cellKey");
        cellKeyInput.attr("value", colsField);
        tempForm.append(cellKeyInput);

        var dataInputSelect = $("<input>");
        dataInputSelect.attr("type", "hidden");
        dataInputSelect.attr("name", "data");
        dataInputSelect.attr("value", data);
        tempForm.append(dataInputSelect);


        var addBorder = $("<input>");
        addBorder.attr("type", "hidden");
        addBorder.attr("name", "addBorder");
        addBorder.attr("value", "true");
        tempForm.append(addBorder);

        // 保存Excel导出日志
        // saveDetailLog(rzlx + "_EXPORT", rzmc + "-导出Excel", {"DCNR": data});
        tempForm.on("submit", function () {});
        tempForm.trigger("submit");
        $("body").append(tempForm);
        tempForm.submit();
        $("tempForm1").remove();
    }
});



