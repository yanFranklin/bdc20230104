layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

var formSelects;
var bmjdmap = {};
var sjdpz = [];
var currentData = {};
$(function(){
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });

    //树结构下拉收起重置
    $('#reset').on('click', function () {

        $('.org_select_show').text('选择');
        $('.org_select_tree').css('display', 'none');
        $('.org_select_show2').text('选择');
        $('.org_select_tree2').css('display', 'none');
    });
})

layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {

    //addModel();//添加遮罩
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element;
        table = layui.table;
    formSelects = layui.formSelects;
    // 初始化下拉框
    initFormSelect(formSelects);
    // 获取时间段配置
    getSjdpz();

    // 日期控件
    laydate.render({
        elem: '#kssj',
        //value: new Date((new Date().getTime() - 90 * 24 * 60 * 60 * 1000)),
        //type: 'datetime',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#jzsj').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#jzsj',
        //type: 'datetime',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#kssj').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

    // 处理字段表头
    var cols = [
        {field: 'bmmc',  title: '部门名称',rowspan:"2", width: 200,
            templet: function (d) {
                return bmjdmap[d.bmmc];
            }
        },
        {field: 'bjywl',title: '办结业务量(次)', width: 140,rowspan:"2",},
        {field: 'bjsc', title: '办件总时长(天)', width: 140,rowspan:"2",
            templet: function (d) {
                return d.bjsc.toFixed(2);
            }
         },
        {field: 'avgBjsc', title: '平均工作时长(天)', width: 160,rowspan:"2",
            templet: function (d) {
                return d.avgBjsc.toFixed(2);
            }
        },
    ];
    var secondCols = [];

    // 解析时间段的配置
    for(var i= 0 ;i<sjdpz.length;i++){
        var per = sjdpz[i];
        var objCount = {field: '', title: '', width: 160};
        var objPercent = {field: '', title: '', width: 160};
        var rowSpan = {field: '', title: '', width: 320,colspan:"2"}

        // <3 类型
        if(per.indexOf("<") != -1){//小于时间段
            var lessCountDay = per.substring(1,2);
            rowSpan["title"] = "小于"+lessCountDay+"天";
        }

        // 3-5类型
        if(per.indexOf("-") != -1){
            var lessCountDay = per.split("-")[0];
            var moreCountDay = per.split("-")[1];
            rowSpan["title"] = lessCountDay + "到"+moreCountDay+"天";;
        }

        // >5类型
        if(per.indexOf(">") != -1){
            var moreCountDay = per.substring(1,2);
            rowSpan["title"] = "大于"+moreCountDay+"天";
        }
        objCount["title"] = "数量(次)";
        objPercent["title"] = "占比";
        objCount["field"] = per + "_count";
        objPercent["field"] = per + "_percent";

        secondCols.push(objCount);
        secondCols.push(objPercent);
        cols.push(rowSpan);

    }


    // 加载Table
    table.render({
        elem: '#bdcBjscTable',
        toolbar: '#toolbar',
        title: '办件时长统计',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        even: true,
        cols: [cols,secondCols],
        data: [],
        page: true,
        limits: [20, 40, 50, 100, 200, 500],
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            //reverseTableCell(reverseList);
            setHeight();
        }
    });


    $('.reseticon').on('click',function(item){
        $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
        $(this).hide();
        layui.form.render("select");
    })

    // 监听表格操作栏按钮
    table.on('toolbar(bdcBjscTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'exportExcel':
                exportExcel(currentData, obj.config.cols, "checked");
                break;
        }
    });

    // 查询按钮点击事件
    form.on('submit(search)', function (data) {
        var queryObject = data.field;
        // var djjg = formSelects.value('selectBmmc', 'val').join(",");
        var djjg=$("input[name='bmmc']").val("");
        var processname = formSelects.value('selectLcmc', 'val').join(",");
        var djyy = formSelects.value('selectDjyy', 'name').join(",");
        var sply = formSelects.value('selectSply', 'val').join(",");
        queryObject["djjg"] = djjg;// 部门
        queryObject["dimension"] = $("#count-type-select").val();// 维度
        queryObject["processname"] = processname;// 流程
        queryObject["djyy"] = djyy;// 登记原因
        queryObject["sply"] = sply;// 审批来源
        addModel();

        table.reload("bdcBjscTable", {
            url: "/realestate-inquiry-ui/bjsctj/count"
            , where: queryObject
            , page: {
                curr: 1  //重新从第 1 页开始
            }
            , done: function (res, curr, count) {
                currentData = res.data;
                removeModal();
                setHeight();
                table.resize('bdcBjscTable');
            }
        });

        return false;
    });
});


/**
 * 加载下拉框
 */
function initFormSelect(formSelects){
    bmselect(formSelects);//部门
    lcselect(formSelects);//流程
    djyyselect(formSelects,'');
}


function bmselect (formSelects){
    $.ajax({
        url: "/realestate-inquiry-ui/slqktj/bm/select",
        dataType: "json",
        success: function (data) {
            // 部门下拉数组
            var bmmcSelList = [];
            data.forEach(function(item){
                bmmcSelList.push({
                    name: item.name,
                    value: item.id
                })
                bmjdmap[item.id] = item.name;
            })
            formSelects.data('selectBmmc', 'local', {
                arr: bmmcSelList
            });
        }
    });
}

function lcselect (formSelects){
    $.ajax({
        url: "/realestate-inquiry-ui/slqktj/lc/select",
        dataType: "json",
        success: function (data) {
            var lcmcSelList = [];
            data.forEach(function(item){
                lcmcSelList.push({
                    name: item.name,
                    value: item.key
                })
            })
            formSelects.data('selectLcmc', 'local', {
                arr: lcmcSelList
            });
        }
    });
}

// 统计的时间段配置
function getSjdpz(){
    $.ajax({
        url: "/realestate-inquiry-ui/bjsctj/sjdpz",
        type: "get",
        async: false,
        success: function (data) {
            sjdpz = data.split(",");
        }
    });
}

function djyyselect (formSelects,djxl){
    $.ajax({
        url: "/realestate-inquiry-ui/slqktj/djyy/select",
        dataType: "json",
        data:{djxl:djxl},
        success: function (data) {
            djyySelList = [];
            data.forEach(function(item){
                djyySelList.push({
                    name: item.DJYY,
                    value: item.ID
                })
            })
            formSelects.data('selectDjyy', 'local', {
                arr: djyySelList
            });
        }
    });
}

/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.24 14:45
 * @author zhuyong
 * @return
 */
function exportExcel(d, cols, type) {
    if(!d || d.length == 0){
        layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">请先统计数据');
        return;
    }
    // 处理多重表格头
    var firstCols = cols[0];
    var secondCols = cols[1];
    var resultCols = firstCols.slice(0,4);
    console.log(resultCols);
    for(var i=0; i<secondCols.length; i++){
        var field = secondCols[i].field;
        var tip = "";
        // <3 类型
        if(field.indexOf("<") != -1){//小于时间段
            var lessCountDay = field.substring(1,2);
            tip = "小于"+lessCountDay+"天";
        }

        // 3-5类型
        if(field.indexOf("-") != -1){
            var lessCountDay = field.split("_")[0].split("-")[0];
            var moreCountDay = field.split("_")[0].split("-")[1];
            tip = lessCountDay + "到"+moreCountDay+"天";
        }

        // >5类型
        if(field.indexOf(">") != -1){
            var moreCountDay = field.substring(1,2);
            tip = "大于"+moreCountDay+"天";
        }
        secondCols[i].title = tip + secondCols[i].title;
        resultCols.push(secondCols[i]);
    }

    cols = resultCols;
    // 标题
    var showColsTitle = [];
    // 列内容
    var showColsField = [];
    // 列宽
    var showColsWidth = [];
    for (var index in cols) {
        if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
            showColsTitle.push(cols[index].title);
            showColsField.push(cols[index].field);
            if (cols[index].width > 0) {
                showColsWidth.push(parseInt(cols[index].width / 100 * 15));
            } else if (cols[index].minWidth > 0) {
                showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
            } else {
                showColsWidth.push(200 / 100 * 15);
            }
        }
    }

    var data = d;
    for (var i = 0; i < data.length; i++) {
        data[i].xh = i + 1;
        data[i].bjsc = parseFloat(data[i].bjsc).toFixed(2);
        data[i].avgBjsc = parseFloat(data[i].avgBjsc).toFixed(2);
        data[i].bmmc = bmjdmap[data[i].bmmc];
    }

    // 设置Excel基本信息
    $("#fileName").val('办件时长统计');
    $("#addBorder").val(true);
    $("#sheetName").val("登记业务量及办理时长统计汇总表");
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#data").val(JSON.stringify(data));
    $("#form").submit();
}





