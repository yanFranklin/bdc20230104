layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

/** 定义全局变量*/
var xlZdList = []; //小类字典值
var tabId = "sjzs";//当前tab页id
var myChartName = "djlxChart";//当前chart名
var yData = [];
var xData = [];
var option = [];
var currentPageData = [];
var queryObject;


// 登记类型统计明细表格查询
var djlxmxDataUrl = "/realestate-inquiry-ui/rest/v1.0/bdcdp/getDjlxMx";
var djlxtbDataUrl = "/realestate-inquiry-ui/rest/v1.0/bdcdp/getDjlxtjTb";

var formSelects, table;
//登记类型下拉
var djlxSelect =[];
//登记小类下拉
var djxlSelect =[];
// 流程下拉数组
var lcmcSelList = [];



$(function () {
    // 获取页面表单标识，用于权限控制
    var module = $.getUrlParam('module');
    if(module){
        setElementAttrByModuleAuthority(module);
    }
    /**
     * 监听台账查询 input 点击 显示 x 清空 input 中的内容
     */
    $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
        $(this).siblings('.layui-input').val('');
    });
    $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
        $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
    }).on('blur', function () {
        var $this = $(this);
        setTimeout(function () {
            $this.siblings('.layui-icon-close').addClass('bdc-hide');
        }, 150)
    });

    //绑定回车键
    $(document).keydown(function (event) {
        if (event.key == 13) {
            $("#search").click();
        }
    });
})

layui.use(['form', 'jquery', 'laydate', 'element', 'table', 'formSelects'], function () {


    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element;
    table = layui.table;
    formSelects = layui.formSelects;
    // 清空已选缓存
    layui.sessionData("checkedData", null);
    // 日期控件
    laydate.render({
        elem: '#kssj',
        //value: new Date((new Date().getTime() - 90 * 24 * 60 * 60 * 1000)),
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#jssj').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#jssj',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#kssj').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    //小类字典值渲染
    formSelects.value("selectDjxl", []);
    var zdList = getMulZdList("djxl");
    zdList['djxl'].forEach(function (item) {
        djxlSelect.push({
            name: item.MC,
            value: item.DM
        })
    });
    formSelects.data('selectDjlx', 'local', {
        arr: djxlSelect
    });
    //登记类型字典值渲染
    formSelects.value("selectDjlx", []);
    var djlxList = getMulZdList("djlx");
    if(isNotBlank(djlxList['djlx'])) {
        $("#djlx_select").append("<option value=''>请选择</option>");
        djlxList['djlx'].forEach(function (item) {
            $("#djlx_select").append("<option  value='" +  item.DM + "'>" + item.MC + "</option>");
        });
    }


    //流程名称渲染
    formSelects.value("selectLcmc", []);
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/process/gzldymcs",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (data) {
            $('#selectedDefName').append(new Option("请选择", ""));
            $.each(data, function (index, item) {
                lcmcSelList.push({
                        name: item.name,
                        value: item.processDefKey
                    })
            });
            formSelects.data('selectLcmc', 'local', {
                arr: lcmcSelList
            });
            layui.form.render("select");
        }, error: function (e) {
            response.fail(e,'');
        }
    });

    layui.form.render("select");
    // 监听流程名称选择事件
    layui.form.on("select(selectedProcessDefName)",function(data){
        var $jdmc = $(this).parents('.bdc-search-box').find('.bdc-jdmc');
        if($jdmc.length != 0){
            queryTaskNameList($jdmc, data.value,false);
        }
    });

    // tab页切换事件
    element.on('tab(tabFilter)', function () {
        var tabName = this.getAttribute('lay-id');
        //切换统计
        tabChangeEvent(tabName);
    });


    /*下拉框监听事件*/
    form.on('select(djlx_select)', function (data) {
        controlXIcon($(this));
    });
    form.on('select(zstype)', function (data) {
        controlXIcon($(this));
    });
    // 查询按钮点击事件
    form.on('submit(search)', function (data) {
        formSubmitEvent(data);
        return false;
    });

    // 初始化查询
    var queryParams = {};
    $(".layui-form").serializeArray().forEach(function (item, index) {
        queryParams[item.name] = item.value;
    });
    queryParams["tjlx"] = 1;
    initSjzsTab(queryParams,true);
    // 初始化登记类型tab
    table.render({
        elem: '#pageTableDjlx',
        method: 'get',
        toolbar: '#toolbarDemo',
        title: '登记类型',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        where: {},
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {
                field: 'xh', type: 'numbers', title: '序号',
                templet: function (d) {
                    return d.LAY_TABLE_INDEX + 1;
                }
            },
            {field: 'xmid', width: 300, sort: true, hide:true},
            {field: 'bdcdyh', width: 300, sort: true, title: '不动产单元号'},
            {field: 'lcmc', width: 120, sort: true, title: '流程名称',},
            {field: 'qlr', width: 260, sort: true, title: '权利人'},
            {field: 'slbh', width: 120, sort: true, title: '受理编号'},
            {field: 'ywr', width: 200, sort: true, title: '义务人'},
            {field: 'zl', sort: true, title: '坐落'}
        ]],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        data:[],
        text: {
            none: '无数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            // 设置选中行
            if(res.content && res.content.length > 0){
                var checkedData = layui.sessionData('checkedData');
                res.content.forEach(function (v) {
                    $.each(checkedData, function(key, value){
                        if(key == v.xmid){
                            v.LAY_CHECKED = true;
                        }
                    })
                });
            }
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content, //解析数据列表
            }
        },
    })
    reloadTjlx();
    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(pageTableDjlx)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;

        $.each(data, function (i, v) {
            if (obj.checked) {
                //.增加已选中项
                layui.sessionData('checkedData', {
                    key: v.xmid, value: v
                });
            } else {
                //.删除
                layui.sessionData('checkedData', {
                    key: v.xmid, remove: true
                });
            }
        });
        var checkedData = layui.sessionData('checkedData');
        var arrcheck = Object.keys(checkedData);
        console.log(arrcheck)
    });
    table.on('toolbar(pageTableDjlx)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        exportUrl(checkStatus.data, obj.config);

    });
});

// 初始化图表分析
function initSjzsTab(queryData, showEchart) {
    addModel();
    layui.form.render("select");
    var barTitle = "登记量";
    $.ajax({
        url: djlxtbDataUrl,
        type: 'POST',
        contentType: "application/json;charset=utf-8",
        async: true,
        data: JSON.stringify(queryData),
        success: function (data) {
            removeModal();
            xData=[];
            yData=[];
            if(data){
                data.forEach(function (d) {
                    if(isNotBlank(d.lxmc) ){
                        yData.push(d.lxmc)
                        xData.push(d.sl)
                    }
                })
            }
            // 这里需要判断一下，如果下拉框已经选择了，则不能再次初始化，因为初始化话会清空已选的值
            if(formSelects.value('selectLcmc', 'name').join(",").length == 0){
                formSelects.data('selectLcmc', 'local', {
                    arr: lcmcSelList
                });
            }
            // 这里需要判断一下，如果下拉框已经选择了，则不能再次初始化，因为初始化话会清空已选的值
            if(formSelects.value('selectDjxl', 'name').join(",").length == 0){
                formSelects.data('selectDjxl', 'local', {
                    arr: djxlSelect
                });
            }
            layui.form.render("select");
            option = {
                tooltip: {
                    trigger: 'axis'
                },
                title: {
                    show: true,
                    text: barTitle,
                    x: 'center',
                    textStyle: {
                        fontSize: 16,
                        fontWeight: "400",
                        fontFamily: "Microsoft YaHei",
                        color: "#333"
                    },
                    subtextStyle: {
                        fontSize: 12
                    }
                },
                legend: {
                    show: false,
                    data: ["次数"],
                    top: "10",
                    right: "50"
                },
                grid: {
                    bottom: '30',
                    top: '50px',
                    containLabel: true,
                    left: 30,
                    right: 110
                },
                xAxis: {
                    type: 'value',
                    name: '单位：（次）',
                    nameTextStyle: {
                        fontSize: 14,
                        color: '#333'
                    },
                    axisLabel: {
                        textStyle: {
                            fontSize: 12,
                            color: '#333'
                        }
                    }
                },
                yAxis: {
                    type: 'category',
                    data: yData,
                    inverse: true,
                    name: "统计维度",
                    nameLocation: 'start',
                    nameTextStyle: {
                        fontSize: 14,
                        color: '#333'
                    },
                    axisLabel: {
                        interval: 0,
                        textStyle: {
                            fontSize: 12,
                            color: '#333'
                        }
                    }
                },
                series: [
                    {
                        name: '次数',
                        type: 'bar',
                        stack: 'sum',
                        barWidth : 40, //柱图宽度
                        itemStyle: {
                            normal: {
                                color: "#58a0f8"
                            }
                        },
                        label: {
                            normal: {
                                show: true,
                                position: 'right'
                            }
                        },
                        data: xData
                    }
                ]
            };
            if(showEchart){
                changeChart();
            }
            setTimeout(removeModal, 100);
        },
        error: function (e) {
            setTimeout(removeModal, 100);
        }
    });
}

$('#reset').on('click', function (item) {

    $('.bdc-percentage-container').find('.layui-form')
        .find('select').find("option:eq(0)")
        .attr("selected", "selected");
    setTimeout($('.bdc-percentage-container').find('.layui-form')
        .find('select').parent().find('input').val(''), 100);
    $('.reseticon').hide();
})

/**
 * @param tabName 当前tab页的id
 * @description tab页点击事件
 */
function tabChangeEvent(tabName) {
    tabId = tabName;
    //图表分析
    if (tabName == "sjzs") {
        changeChart();
    } else if (tabName == "xxxx") {
        // 重新请求
        // layui.form.render("select")
        table.resize('pageTableDjlx');
    }


}

// 表格重新加载表格明细
function reloadTjlx() {
    table.reload("pageTableDjlx", {
        url: djlxmxDataUrl,
        where: {
            kssj: $("#kssj").val(),
            jssj: $("#jssj").val(),
            djlx: $("#djlx_select").val(),
            djxl: formSelects.value('selectDjxl', 'valStr'),
            tjlx: $("#count-type-select").val(),
        },
        method: 'get',
        page: {
            curr: 1  //重新从第 1 页开始
        },

        done: function (res) {
            currentPageData = res.data;
        }
    });
}

function changeChart() {
// 动态设置柱状图y轴的长度
    var setHeight = 60;
    var autoHeight = 0;
    if (yData.length > 2) {
        autoHeight = (yData.length+1) * setHeight;
        $('#djlxChart').height(autoHeight);
    }
    //设置柱状图x轴的长度
    yData.forEach(function(item){
        if(item.length>14){
            $('#djlxChart').width(1000);
            return ;
        }else{
            $('#djlxChart').width(600);
            return ;
        }
    })

    echarts.init(document.getElementById("djlxChart")).dispose();
    var myChart = echarts.init(document.getElementById("djlxChart"));
    //使用制定的配置项和数据显示图表
    myChart.setOption(option);
    myChart.resize();

}

function queryTaskNameList($this,processDefKey,isDefault) {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/task/taskName",
        data: {processDefKey: processDefKey},
        success: function (data) {
            if(isDefault){
                defaultRenderSelect($this,data);
            }else {
                updateRenderSelect($this,data);
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}


function formSubmitEvent(formData) {

    queryObject = formData.field;
    var djlx = $("#djlx_select").val();
    var jssj = $("#jssj").val();
    var kssj = $("#kssj").val();
    var tjlx = $("#count-type-select").val();
    queryObject["djlx"] = djlx;//登记类型

    queryObject["jssj"] = jssj;// 结束时间

    queryObject["kssj"] = kssj;//开始时间
    queryObject["tjlx"] = tjlx;//图表统计传参：统计类型 1-根据登记类型统计，2-根绝登记小类
    reloadTjlx(queryObject);
    initSjzsTab(queryObject, true);
    changeChart();
    return false;

}


function exportUrl(data,obj){
    var url = obj.url;
    var paramData = obj.where;
    $.ajax({
        url: url,
        type: "get",
        data: paramData,
        success: function (data) {
            if(data.content){
                exportExcel(data.content,obj);
            }else{
                exportExcel(data,obj);
            }
        }
    });
}

function exportExcel(d, obj){
    var cols = obj.cols[0];
    var checkedData = layui.sessionData('checkedData');
    if ($.isEmptyObject(checkedData)) {
        layer.alert("<div style='text-align: center'>请选择需要导出Excel的数据行！</div>", {title: '提示'});
        return;
    }
    // 标题
    var showColsTitle = [];
    // 列内容
    var showColsField = [];
    // 列宽
    var showColsWidth = [];
    for(var index in cols){
        if(cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar){
            showColsTitle.push(cols[index].title);
            showColsField.push(cols[index].field);
            if(cols[index].width > 0){
                showColsWidth.push(parseInt(cols[index].width / 100 * 15));
            }else if(cols[index].minWidth > 0){
                showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
            }else{
                showColsWidth.push(300 / 100 * 15);
            }
        }
    }

    var data = new Array();
        $.each(checkedData, function(key, value){
            data.push(value);
        })
    for(var i = 0; i < data.length; i++){
        data[i].xh   = i + 1;
    }
    // 设置Excel基本信息
    $("#fileName").val('登记量统计');
    $("#sheetName").val('登记量统计');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#data").val(JSON.stringify(data));
    $("#form").submit();
}