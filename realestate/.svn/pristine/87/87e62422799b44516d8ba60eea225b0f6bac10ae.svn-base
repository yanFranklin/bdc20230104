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
var dyjetjmxDataUrl = "/realestate-inquiry-ui/rest/v1.0/bdcdp/getDyjetjMx";
var dyjeDataUrl = "/realestate-inquiry-ui/rest/v1.0/bdcdp/getDyjetjTb";


var formSelects, table;
//登记小类下拉
var djxlSelect =[];
// 流程下拉数组
var lcmcSelList = [];
//区县代码
var qxdmSelList = [];



$(function () {
    // 获取页面表单标识，用于权限控制
    var module = $.getUrlParam('module');
    if(module){
        setElementAttrByModuleAuthority(module);
    }
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

    // 日期控件
    laydate.render({
        elem: '#kssj',
        value: new Date(),
        type: 'date',
        format: 'yyyy-MM-dd',
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
        value: new Date(),
        type: 'date',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#kssj').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
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

    //小类字典值渲染
    formSelects.value("selectDjxl", []);
    var zdList = getMulZdList("djxl");
    zdList['djxl'].forEach(function (item) {
        djxlSelect.push({
            name: item.MC,
            value: item.DM
        })
    });
    formSelects.data('selectDjxl', 'local', {
        arr: djxlSelect
    });
    //区县代码字典值渲染
    formSelects.value("selectQxdm", []);
    var qxdmList = getMulZdList("qjgldm");
    qxdmList.qjgldm.forEach(function (item) {
        qxdmSelList.push({
            name: item.MC,
            value: item.DM
        })
    });
    formSelects.data('selectQxdm', 'local', {
        arr: qxdmSelList
    });

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
            // {fixed: 'left'},
            {
                field: 'xh', type: 'numbers', title: '序号',
                templet: function (d) {
                    return d.LAY_TABLE_INDEX + 1;
                }
            },
            {field: 'bdcdyh', width: 260, sort: true, title: '不动产单元号'},
            {field: 'qlr', width: 200, sort: true, title: '权利人'},
            {field: 'ywr', width: 90, sort: true, title: '义务人'},
            {field: 'slbh', width: 180, sort: true, title: '受理编号'},
            {field: 'zl', width: 140, sort: true, title: '坐落'},
            {field: 'bdbzzqse', width: 150, sort: true, title: '被担保主债权数额'},
            {field: 'zwlxjssj', width: 160, sort: true, title: '债务履行结束时间 '},
            {field: 'zwlxqssj', width: 160, sort: true, title: '债务履行起始时间'},
            {field: 'dyfs', width: 120, sort: true, title: '抵押方式'},
            {field: 'qszt',  width: 120,sort: true, title: '权属状态'}
        ]],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
    })
});

// 初始化图表分析
function initSjzsTab(queryData, showEchart) {
    addModel();
    var barTitle = "抵押量";
    $.ajax({
        url: dyjeDataUrl,
        type: 'POST',
        contentType: "application/json;charset=utf-8",
        async: true,
        data: JSON.stringify(queryData),
        success: function (data) {
            removeModal();


            var xAxisData = [];
            var dytc = [];

            var dyje = [];

            for (var i = 0; i < data.length; i++) {
                xAxisData.push(data[i].YF);
                dytc.push(data[i].DYTS);
                dyje.push(data[i].DYJE);

            }
            var maxDytc = RoundedMax(dytc);
            var maxDyje = RoundedMax(dyje);
            var intervalDytc = maxDytc/5;
            var intervalDyje = maxDyje/5;
            echarts_3(xAxisData, dytc, dyje,maxDytc,maxDyje,intervalDytc,intervalDyje);
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
        layui.form.render("select")
        reloadTjlx()
    }
    table.resize('pageTableDjlx');

}

// 表格重新加载表格明细
function reloadTjlx() {
    table.reload("pageTableDjlx", {
        url: dyjetjmxDataUrl,
        where: {
            kssj: $("#kssj").val(),
            jssj: $("#jssj").val(),
            qxdm: formSelects.value('selectQxdm', 'valStr'),
            tjlx: $("#count-type-select").val(),
            dybdclx:$("#dybdclx").val()
        },
        method: 'get',
        page: {
            curr: 1  //重新从第 1 页开始
        },
        done: function (data) {
            currentPageData = data.data;
        }
    });
}

function changeChart() {
    if( $("#count-type-select").val()==2){
        // 根据返回的数值计算所需的图表高度
        $("#djlxChart").css({
            "height": yData.length*20,
            "width":1000
        });
    }else{
        $("#djlxChart").css({
            "height": 400,
            "width":600
        });
    }
    echarts.init(document.getElementById("djlxChart")).dispose();
    var myChart = echarts.init(document.getElementById("djlxChart"));
    //使用制定的配置项和数据显示图表
    myChart.setOption(option);
    myChart.resize();

}


/**
 * 下拉列表 X 号控制显隐
 * @param $item
 */
function controlXIcon($item) {
    if ($item.text() == "请选择") {
        $item.parents('.layui-input-inline').find('.reseticon').hide();
    } else {
        $item.parents('.layui-input-inline').find('.reseticon').show();
    }
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

/**
 * 默认渲染下拉框       节点名称---登记原因
 * @param $select       select的calss
 * @param data          渲染数据
 */
function defaultRenderSelect($select,data){
    for(var i = 0,len = $select.length; i < len; i++){
        var jdOption = '<option value="">请选择</option>';
        data.forEach(function(v){
            jdOption += '<option value="'+ v +'">'+ v +'</option>';
        });
        $($select[i]).html(jdOption);
    }
    formSelects.render();
}

// 修改流程名称时 重新渲染节点名称,登记原因
function updateRenderSelect($this,data){
    var jdOption = '<option value="">请选择</option>';
    data.forEach(function(v){
        jdOption += '<option value="'+ v +'">'+ v +'</option>';
    });
    $this.html(jdOption);
    formSelects.render();
}

function formSubmitEvent(formData) {
    reloadTjlx();
    queryObject = formData.field;
    var djlx = $("#djlx_select").val();
    var jssj = $("#jssj").val();
    var kssj = $("#kssj").val();

    //var processDefKey =formSelects.value('selectLcmc', 'valStr');
    var tjlx = $("#count-type-select").val();
    var qxdm = formSelects.value('selectQxdm', 'valStr');

    queryObject["djlx"] = djlx;//登记类型

    queryObject["jssj"] = jssj;// 结束时间

    queryObject["kssj"] = kssj;//开始时间
    queryObject["qxdm"] = qxdm;//区县代码
    //queryObject["processDefKey"] = processDefKey;//工作流定义ID
    initSjzsTab(queryObject, true);
    return false;

}


function echarts_3(xAxisData, dytc, dyje,maxDytc,maxDyje,intervalDytc,intervalDyje) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('djlxChart'));
    var option = {
        legend: {
            textStyle: {
                color: 'rgba(0, 0, 0, 1)'
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            data: xAxisData,
            axisLine: {
                onZero: true,
                lineStyle: {
                    color: 'rgba(0, 0, 0, 1)'
                }
            },
            axisTick: {
                show: true,
                alignWithLabel: true
            },
            axisLabel: {
                show: true,
                interval: 0,
                rotate: 45
            }
        },
        yAxis: [{
            type: 'value',
            min: 0,
            max: maxDytc,
            interval: intervalDytc,
            name: '抵押量（套）',
            axisLine: {
                lineStyle: {
                    color: 'rgba(0, 0, 0, 1)'
                }
            },
            splitLine: {
                show: true,
                lineStyle: {
                    // color: 'rgba(106, 41, 41, 1)',
                }
            }
        }, {
            type: 'value',
            name: '抵押金额（亿元）',
            min: 0,
            max: maxDyje,
            interval: intervalDyje,
            axisLine: {
                lineStyle: {
                    color: 'rgba(0, 0, 0, 1)'
                }
            },
            splitLine: {
                show: true,
                lineStyle: {
                    // color: 'rgba(106, 41, 41, 1)',
                }
            }
        }],
        grid: {
            top: '16%',
            bottom: '20%',
            left: '8%',
            right: '8%'
            // borderColor: 'rgba(118, 28, 28, 1)',
        },
        series: [
            {
                name: '抵押金额',
                type: 'line',
                stack: 'one',
                yAxisIndex: 1,  //选择index为1的Y轴作为参考系
                data: dyje,
                itemStyle: {
                    color: '#9370DB'//#008080','#AFEEEE','#9370DB'
                }
            }, {
                name: '抵押量',
                type: 'bar',
                stack: 'one',
                data: dytc,
                itemStyle: {
                    color: '#58a0f8'
                },
                barWidth: '29%'
            }]
        // 使用刚指定的配置项和数据显示图表。
    };
    myChart.setOption(option);
    setTimeout(function () {
        myChart.resize();
    }, 200)
    window.addEventListener('resize', function () {
        myChart.resize();
    });
}


function RoundedMax(a) {
    var mn = Math.max.apply(Math, a);

    if (mn == 0) {return 100};
    if (mn <= 10){return 10};

    var size = Math.floor(Math.log(Math.abs(mn)) / Math.LN10);

    var magnitude = Math.pow(10, size);

    var yMin = (Math.floor(mn / magnitude) +1) * magnitude;

    return yMin;

}