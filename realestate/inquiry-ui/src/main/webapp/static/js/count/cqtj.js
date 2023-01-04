layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});


var tjlx = "allBm";//统计类型：分allBm和bmry allBm柱形图y轴为所有部门，bmry则为所选部门下的所有人员
var tabId = "sjzs";//当前tab页id
var myChartName = "allChart";//当前chart名
var changeCount = 0;
var isEnter = false;
var tableBoxheight = 0;

var bmZdList = [];
var ryZdList = [];
var yData = [];
var xData = [];
var zbXData = [];
var qtXData = [];
var sumSeriesData = [];
var option = [];
var currentPageData = [];
var bmmxCurrentPageData = [];


var bmCacheData;
var processData;
var nodeData;
var queryObject;



var bmMxDataUrl="/realestate-inquiry-ui/bdccqtj/cqtjmx";
var formSelects,table;
// 部门下拉数组
var bmmcSelList = [];
var zdList = getZdList();
$(function(){
    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if(moduleCode){
        setElementAttrByModuleAuthority(moduleCode);
    }
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
})

layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element;
    table = layui.table;
    formSelects = layui.formSelects;
    // 工作流定义名称
    getDataByAjax('/rest/v1.0/process/gzldymcs', '', 'GET', function (data) {
        var lcmcData = [];
        data.forEach(function (v) {
            lcmcData.push({"name": v.name, "value": v.processDefKey});
        });
        formSelects.data('selectLcmc', 'local', {
            arr: lcmcData
        });
    });
    for(var key in layui.sessionData('checkedData')){
        layui.sessionData('checkedData', {
            key: key, remove: true
        });
    }
    // 日期控件
    laydate.render({
        elem: '#kssj',
        type: 'datetime',
        value:new Date(),
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
        type: 'datetime',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#kssj').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

    // tab页切换事件
    element.on('tab(tabFilter)', function () {
        var tabName = this.getAttribute('lay-id');
        tabChangeEvent(tabName,table);
    });
    form.on('select(dimension)', function (data) {
        changeFun(this);
    });
    form.on('select(count-type-select)', function (data) {
        changeCountType(this)
    });


    /*下拉框监听事件*/
    form.on('select(jdmc)', function (data) {
        controlXIcon($(this));
    });
    form.on('select(process)', function (data) {
        controlXIcon($(this));
    });
    form.on('select(ry-select)', function (data) {
        controlXIcon($(this));
    });
    // 禁用人员
    formSelects.disabled("selectBjry");
    // 查询按钮点击事件
    form.on('submit(search)', function (data) {
        for(var key in layui.sessionData('checkedData')){
            layui.sessionData('checkedData', {
                key: key, remove: true
            });
        }

        changeCount = 0;
        formSubmitEvent(data);
        return false;
    });
    // 初始化查询
    var queryParams = {};
    $(".layui-form").serializeArray().forEach(function (item, index) {
        queryParams[item.name] = item.value;
    });
    initSjzsTab(queryParams,true);
    // 初始化部门明细列表tab
    table.render({
        elem: '#pageTableBmmx',
        method: 'post',
        contentType: "application/json;charset=utf-8",
        toolbar: '#toolbarDemo',
        title: '部门明细列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size', //每页数据量的参数名，默认：limit
            loadTotal: true,
            loadTotalBtn: false
        },
        even: true,
        cols: [[
            {field:'slbh',width:120,title: '受理编号'},
            {field:'procTimeoutStatus',title: '超期状态', width:100,templet: function (d) {
                    return formatProcTimeoutStatus(d.procTimeoutStatus);
                }},
            {field:'procDefName', title: '流程名称', width:200},
            {field:'qlr',title: '权利人', width:150},
            {field:'ywr', title: '义务人', width:150},
            {field:'zl', title: '坐落',minwidth:300},
            {field:'bdcdyh', title: '不动产单元号', width:280},
            {field:'startTime', title: '开始时间', width:180},
            {field:'endTime', title: '结束时间', width:180}
        ]],
        data: [],
        page: true,
        parseData: function (res) {
            // 设置选中行
            if(res.content && res.content.length > 0){
                var checkedData = layui.sessionData('checkedData');
                res.content.forEach(function (v) {
                    $.each(checkedData, function(key, value){
                        if(key == v.orgId+"_"+v.taskName+"_"+v.realCount+"_"+v.procDefName){
                            v.LAY_CHECKED = true;
                        }
                    })
                });
            }
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function (data) {
            bmmxCurrentPageData = data.data;
        }
    });
    // 初始化加载部门明细数据
    table.reload("pageTableBmmx", {
        url: bmMxDataUrl,
        where: {kssj: $("#kssj").val(), jzsj: $("#jzsj").val(),type:"",statisticsType:"END_STATISTIC"},
        method: 'post',
        contentType: "application/json;charset=utf-8",
        page: {
            curr: 1  //重新从第 1 页开始
        },
        done: function (data) {
            bmmxCurrentPageData = data.data;
        }
    });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(pageTableBmmx)', function(obj){
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : bmmxCurrentPageData;

        $.each(data, function(i, v) {
            var keyT = v.orgId+"_"+v.taskName+"_"+v.realCount+"_"+v.procDefName;
            if (obj.checked) {
                //.增加已选中项
                layui.sessionData('checkedData', {
                    key: keyT, value: v
                });
            } else {
                //.删除
                layui.sessionData('checkedData', {
                    key: keyT, remove: true
                });
            }
        });
    });
    //新增 点击查看
    $('.bdc-change-show').on('click',function(){
        var $this = $(this);
        $('.chart-box-item').toggleClass('bdc-hide');
        if($this.html() == '查看列表'){
            $this.html('返回图表');
        }else {
            $this.html('查看列表');
            changeChart();
        }
    })

});

/**
 * @date 2019.03.15 14:46
 * @author chenyucheng
 * @param queryData
 * @return
 */
// 初始化图表分析
function initSjzsTab(queryData, showEchart) {
    addModel();
    /*当前tab也默认不可用部门和人员下拉框*/
    layui.form.render("select");
    var tableHead = "分组项";
    var barTitle = "工作量";
    var url = "/realestate-inquiry-ui/bdccqtj/cqtjCount";


    if(tjlx == "allBm") {
        myChartName = "allChart";
        $("#bmryChart").hide();
        $("#allChart").show();
        $("#bmryBjlChart").hide();
        $("#allBjlChart").hide();
        if($("#dimension").val() == "wd-bm"){
            queryData["tjlx"] = "xzqh";
        }else{
            queryData["tjlx"] = "gzldyid";
        }
    }

    $.ajax({
        url: url,
        type: 'POST',
        contentType: "application/json;charset=utf-8",
        async: true,
        data: JSON.stringify(queryData),
        success: function (data) {

            removeModal();
            bmCacheData = data.list;
            // 数据置空
            yData = [];
            xData = [];
            zbXData = [];
            qtXData = [];
            sumSeriesData = [];

            ryZdList = [];
            var zj = 0;
            var doingZj = 0;
            var otherZj = 0;
            var resultData = [];
            if(bmCacheData){
                resultData = data.list.content?data.list.content:data.list;
            }else{
                resultData = data;
            }

            //  做堆叠柱状图，这里先定义好series量
            var seriesData = [];
            var seriesContentYb = {
                name: '未超期',
                type: 'bar',
                stack: 'sum',
                itemStyle: {
                    normal: {
                        color: "#44d6b5"
                    }
                },
                label: {
                    normal: {
                        show: true
                        //position: 'right'
                    }
                }
            };
            var seriesContentQt = {
                name: '已超期',
                type: 'bar',
                stack: 'sum',
                itemStyle: {
                    normal: {
                        color: "#58a0f8"
                    }
                },
                label: {
                    normal: {
                        show: true,
                        //position: 'right'
                    }
                }
            };

            var SumSeriesData = {
                name: '总计',
                type: 'line',
                showAllSymbol: true,
                //stack: 'sum',
                //barGap: '-100%',
                label: {
                    normal: {
                        offset: ['10', '50'],
                        show: true,
                        position: 'right',
                        textStyle: { color: '#333' },
                        formatter:'{c}'
                    }
                },
                //symbol: 'roundRect',
                itemStyle: {
                    normal: {
                        color: 'rgba(128, 128, 128, 0)'
                    }
                }
            };

            var distinctMcFlag = '';
            if($("#dimension").val() == "wd-bm"){
                $.each(resultData, function (i, d) {
                    var SLR = convertZdDmToMc("qjgldm",d.SLR,"zdList");
                    d.SLR = SLR;
                    console.log(SLR);
                });
            }
            $.each(resultData, function (i, d) {
                    var mc = d.DJJG?d.DJJG:d.GZLDYMC;
                    var dm = d.DJJG?d.DJJG:d.GZLDYMC;
                    mc = mc?mc:d.SLR;
                    dm = dm?dm:d.SLR;
                    zdList.bm = bmZdList;
                    var times = d.realCount?d.realCount:d.REALCOUNT;
                    if(distinctMcFlag.indexOf(mc) == -1){
                        distinctMcFlag+=mc;
                        yData.push(mc);
                        // 当放入一个新的统计维度的时候，需要把办理中为其他的数据放入xData
                        var doingCase = 0;
                        var otherCase = 0;
                        var allCase = 0;
                        var xDataTemp = 0;//查看列表用到的总数，不论类型，取和
                        $.each(resultData, function (j, t) {
                            var mcT = t.DJJG?t.DJJG:t.GZLDYMC;
                            var dmT = t.DJJG?t.DJJG:t.GZLDYMC;
                            var times = t.realCount?t.realCount:t.REALCOUNT;
                            mcT = mcT?mcT:t.SLR;
                            dmT = dmT?dmT:t.SLR;
                            if(mc == mcT){
                                if(t.AJZT == "0"){
                                    doingCase+=times;
                                    doingZj+=times;
                                }else{
                                    otherCase+=times;
                                    otherZj+=times;
                                }

                                xDataTemp+=times;
                                // 查看列表，表格下方合计
                                allCase+=times;
                            }
                        })
                        xData.push(xDataTemp);
                        zbXData.push(doingCase);
                        qtXData.push(otherCase);
                        zj += allCase;
                    }
            });
            // 新建table放到chart-table容器中
            $(".chart-table").html(
                "<table class='layui-table' id='hz-table'>" +
                "<colgroup>" +
                "<col width='200'>" +
                "    <col width='150'>" +
                "    </colgroup>" +
                "    <thead>" +
                "    <tr>" +
                "    <th>" + tableHead + "</th>" +
                "    <th lay-sort='lay-sort' sort='true'>数量</th>" +
                "    </tr> </thead> <tbody> </tbody> </table>");
            // 完善表格信息
            var tableBody = $("#hz-table").find("tbody");
            var countBmColor = 0;
            /!*填写右侧的table*!/
            $.each(yData, function (i, d) {
                if(countBmColor%2==0) {
                    tableBody.append("<tr onclick='jumpToDetail(this)' class='tr-dbclick' id='" + d + "'>" +
                        "<td class='td-dm' id='" + d + "'>" + d + "</td>" +
                        "<td class='td-js'>" + parseInt(xData[i]) + "</td>" +
                        "</tr>")
                }else{
                    tableBody.append("<tr onclick='jumpToDetail(this)' class='tr-dbclick bmColor' id='" + d + "'>" +
                        "<td class='td-dm' id='" + d + "'>" + d + "</td>" +
                        "<td class='td-js'>" + parseInt(xData[i]) + "</td>" +
                        "</tr>")
                }
                countBmColor++
            });

            if(showEchart){
                tableBody.append("<tr class='tr-zj' id='trhj'>" +
                    "<td>合计</td>" +
                    "<td class='td-js'>" + zj + "</td>" +
                    "</tr>");
                if (tjlx != "bjl_allBm") {
                    $('.bdc-total').find('i').text(zj);
                }else{
                    $('.bdc-total').find('i').text(doingZj+otherZj+"次，其中未超期："+doingZj+"次，超期："+otherZj);
                }
            }
            option = {
                tooltip : {
                    trigger: 'axis'
                },
                title: {
                    show: true,
                    //text: barTitle,
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
                    name: '单位：（件）',
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
                    name: tableHead,
                    nameLocation: 'start',
                    nameTextStyle: {
                        fontSize: 14,
                        color: '#333'
                    },
                    axisLabel: {
                        interval:0,
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
            seriesContentYb['data'] = zbXData;
            seriesContentQt['data'] = qtXData;
            SumSeriesData['data'] = xData;
            seriesData.push(seriesContentYb);
            seriesData.push(seriesContentQt);
            seriesData.push(SumSeriesData);
            option['series'] = seriesData;
            option['legend'] = {
                data: ['未超期','已超期']
            }
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


function tabChangeEvent(tabName,table) {
    tabId = tabName;
    if (tabName == "sjzs") {
        changeChart();
    } else if (tabName == "xxxx") {
        layui.form.render("select");
       if($('#pageTableBmmx').parent().find('tbody').find('tr').length==0){
            // 重新请求
           var kssj =  $("#kssj").val();
           var jzsj =  $("#jzsj").val();
           var processDefKey =  $("#process").val();
           var bmmxParams = {kssj:kssj, jzsj:jzsj, type: "", processDefKey: processDefKey};
           if(!isNotBlank(processDefKey) && !isNotBlank(kssj) && !isNotBlank(jzsj)){
               removeModel();
               layer.msg("请输入查询条件！");
               return false;
           }
            reloadBmmx(bmmxParams, '');
        }
    }
    setHeights("change");
    table.resize('pageTableBmmx');


}
/**
 * 查询按钮点击事件
 * @date 2019.03.15 14:45
 * @author chenyucheng
 * @param form监听事件的data原封不动的传入
 * @return
 */
function formSubmitEvent(formData) {
    queryObject = formData.field;
    var dimension = $("#dimension").find("option:selected").val();
    var processname = formSelects.value('selectLcmc', 'name').join(",");
    var kssj = $("input[name='kssj']").val();
    var jzsj = $("input[name='jzsj']").val();
    queryObject["dimension"] = dimension;
    queryObject["processname"] = processname;
    queryObject["kssj"] = kssj;
    queryObject["jzsj"] = jzsj;
    addModel();
    if(!isNotBlank(processname) && !isNotBlank(kssj) && !isNotBlank(jzsj)){
        removeModel();
        layer.msg("请输入查询条件！");
        return false;
    }
    reloadBmmx(queryObject, 'change');
    initSjzsTab(queryObject,true);
    return false;
}
// 表格重新加载部门明细
function reloadBmmx(obj, type){
    table.reload("pageTableBmmx", {
        url: bmMxDataUrl,
        where: obj,
        method: 'post',
        contentType: "application/json;charset=utf-8",
        page: {
            curr: 1  //重新从第 1 页开始
        },
        done: function (data) {
            bmmxCurrentPageData = data.data;
            setHeights(type);
        }
    });
}


function changeChart() {
    /*计算柱形图的高度，以右侧table的高度为参照
     * 该table每行高度为38.6px*/
    var tableHeight = $("#hz-table").height();

    var contentHeight = $('#tjbg-area').height();
    $("#" + myChartName).css({
        "height": contentHeight + 25
    });
    $("#sjfx-area").css({
        "height": contentHeight
    });

    echarts.init(document.getElementById("allChart")).dispose();
    echarts.init(document.getElementById("bmryChart")).dispose();
    echarts.init(document.getElementById("allBjlChart")).dispose();
    echarts.init(document.getElementById("bmryBjlChart")).dispose();
    myChart = echarts.init(document.getElementById(myChartName));

    //使用制定的配置项和数据显示图表
    myChart.setOption(option);
    myChart.resize();

}

/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.24 14:45
 * @author zhuyong
 * @return
 */
function exportExcel(d, obj, type){
    var cols = obj.cols[0];
    var checkedData = layui.sessionData('checkedData');
    if($.isEmptyObject(checkedData) && type == "checked"){
        exportAllExcel(d,obj);
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
            var currectBmid = $("#bm-select").val();
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
    if(type == "checked"){
        $.each(checkedData, function(key, value){
            data.push(value);
        })
    }else{
        data = d;
    }

    for(var i = 0; i < data.length; i++){
        data[i].xh   = i + 1;
        // if(currectBmid != ""){
        //   data[i].orgName = convertZdDmToMc("bm", currectBmid, "zdList");
        // }
    }
    // 设置Excel基本信息
    $("#fileName").val('工作量');
    $("#sheetName").val('统计表');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#data").val(JSON.stringify(data));
    $("#form").submit();
}

/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.25 14:45
 * @author chenyucheng
 * @return
 */
function exportAllExcel(data, obj){
    var cols = obj.cols[0];
    var url = obj.url;
    var paramData = obj.where;
    paramData["type"] = "exportAll";
    $.ajax({
        url: url,
        type: "POST",
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify(paramData),
        success: function (data) {
          if(data.content){
              exportExcel(data.content,obj, "all");
          }else{
              exportExcel(data,obj, "all");
          }
        }
    });
}

/**
 * 切换统计维度 办件量 工作量
 * @param item
 */
function changeCountType(item){
    var type = $(item).parent().find('.layui-this').attr('lay-value');
    if(type == "gzl"){//已办结工作量
        tjlx = "allBm";
        $("#jdmc-wd").removeAttr("disabled");
        formSelects.undisabled("selectJdmc");
        //$('#jdmc').removeAttr("disabled");
    }else if(type == "gzlblz"){//办理中工作量
        tjlx = "allBmBlz";
        $("#jdmc-wd").removeAttr("disabled");
        formSelects.undisabled("selectJdmc");
        //$('#jdmc').removeAttr("disabled");
    }else{//办件量
        if($("#dimension").val() == "wd-jd"){
            $("#dimension").find('option:eq(0)').attr("selected","selected")
        }
        $("#jdmc-wd").attr("disabled","disabled");
        formSelects.disabled("selectJdmc");
        //$('#jdmc').attr("disabled", "disabled");

        tjlx = "bjl_allBm";
        tabId = "sjzs";
    }
    layui.form.render("select");
    $("#search").click();
    table.resize("pageTableRymx");
}

/**
 * 切换统计维度 节点，流程，部门，人员
 * @param item
 */
function changeFun(item){
    $("#search").click();
}

/**
 * 计算对象长度
 * @param obj
 * @returns {number}
 */
function calObjLength(obj){
    if(!obj){
        return 0;
    }
    var count = 0;
    for (var key in obj){
        count++
    };
    return count;
}

/**
 * 打印 ie不支持中文，做一个反向字典解决兼容问题
 * @returns {{}}
 * 请勿改动，如要改动请联系开发人员：陈玉城
 */
function zdJdmc(){
    var obj = {};
    obj["受理"] = "sl";
    obj["登簿制证"] = "dbzz";
    obj["登簿"] = "db";
    obj["审核"] = "sh";
    obj["缮证"] = "sz";
    obj["初审"] = "cs";
    obj["核定"] = "hd";
    obj["复审"] = "fs";
    obj["发证"] = "fz";
    obj["复核"] = "fh";
    obj["登薄制证"] = "dbzz";
    return obj;
}

/**
 * 下拉列表 X 号控制显隐
 * @param $item
 */
function controlXIcon($item){
    if($item.text() == "请选择"){
        $item.parents('.layui-input-inline').find('.reseticon').hide();
    }else{
        $item.parents('.layui-input-inline').find('.reseticon').show();
    }
}

/**
 * 设置当前两个表格的高度
 * @date 2019.05.25 14:45
 * @author chenyucheng
 * @return
 */
function setHeights(type){
    if(type=="change" && changeCount > 0){
        return;
    }
    if(isEnter){//回车查询
        isEnter = false;
        return;
    }
    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
    var tableBoxheight = $('.bdc-table-box').height();
    if($('.layui-tab-content').find('.layui-tab-item:visible').find('.bdc-table-box').find('div:eq(0)').is(":visible")){
        if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
            $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
        }else {
            if(changeCount == 0){
                $('.layui-table-body').height($('.bdc-table-box').height() -$('.layui-tab-title').height()-95);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height()- $('.layui-tab-title').height()-95 - 17);
            }else{
                $('.layui-table-body').height(tableBoxheight -$('.layui-tab-title').height()-95);
                $('.layui-table-fixed .layui-table-body').height(tableBoxheight- $('.layui-tab-title').height()-95 - 17);
            }
        }
    }else{
        if($('.bdc-table-box .layui-table-main>.layui-table:eq(1)').height() == 0){
            $('.layui-table-body .layui-none:eq(1)').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
        }else {
            if(changeCount == 0){
                $('.layui-table-body:eq(2)').height($('.bdc-table-box').height() -$('.layui-tab-title').height()-95);
                $('.layui-table-fixed .layui-table-body:eq(2)').height($('.bdc-table-box').height()- $('.layui-tab-title').height()-95 - 17);
            }else{
                $('.layui-table-body:eq(2)').height(tableBoxheight -$('.layui-tab-title').height()-95);
                $('.layui-table-fixed .layui-table-body:eq(2)').height(tableBoxheight- $('.layui-tab-title').height()-95 - 17);
            }
        }
    }

    if(type=="change" &&  (!$('.layui-tab-title').find('li:eq(0)').hasClass('layui-this'))){
        changeCount++;
    }
}


function formatProcTimeoutStatus(procTimeoutStatus){
    var status = "";
    if(procTimeoutStatus == 0){
        status = '未超期';
    }else if(procTimeoutStatus == 1){
        status = '已超期';
    }
    return status;
}


function getDataByAjax(_path, _param, _type, _fn, async) {
    var _url = getContextPath() + _path;
    var getAsync = false;
    if (async) {
        getAsync = async
    }
    $.ajax({
        url: _url,
        type: _type,
        async: getAsync,
        contentType: 'application/json;charset=utf-8',
        data: _param,
        success: function (data) {
            _fn.call(this, data, data);
        },
        error: function (err) {
            console.log(err);
        }
    });
}
