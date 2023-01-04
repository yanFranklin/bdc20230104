layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

/** 定义全局变量*/
var tjlx = "djxl";//统计类型：分allBm和bmry allBm柱形图y轴为所有部门，bmry则为所选部门下的所有人员
var tabId = "sjzs";//当前tab页id
var myChartName = "slryChart";//当前chart名
var changeCount = 0;
var isEnter = false;
var yData = [];
var xData = [];
var option = [];
var slryMxCurrentPageData = [];
var slqkMxCurrentPageData = [];
var queryObject;

//受理情况统计-受理人员统计
var slryUrl="/realestate-inquiry-ui/slqktj/slry/count";
//受理情况统计-受理情况统计
var slqkUrl="/realestate-inquiry-ui/slqktj/slqk/count";

// 受理人员明细
var slryMxDataUrl="/realestate-inquiry-ui/slqktj/slry/mx";
// 受理情况明细
var slqkMxDataUrl="/realestate-inquiry-ui/slqktj/slqk/mx";

//部门下拉框
var bmselecList="/realestate-inquiry-ui/slqktj/bm/select";
//人员下拉框
var ryselecList="/realestate-inquiry-ui/slqktj/ry/select";
//流程下拉框
var lcselecList="/realestate-inquiry-ui/slqktj/lc/select";
//登记类型和登记小类下拉框
var djlxAndDjxlselecList="/realestate-inquiry-ui/slqktj/djlxAndDjxl/select";
//部门下拉框
var djyySelecList="/realestate-inquiry-ui/slqktj/djyy/select";

//统计维度，默认人员
var tjwdStr = "wd_slry";

var formSelects;
// 部门下拉数组
var bmmcSelList = [];
// 人员下拉数组
var bjrySelList = [];
// 流程下拉数组
var lcmcSelList = [];
// djyy下拉数组
var djyySelList = [];

$(function(){
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
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

    formSelects.disabled("selectDjxl");
    formSelects.disabled("selectDjlx");
    formSelects.disabled("selectDjyy");

    for(var key in layui.sessionData('checkedData')){
        layui.sessionData('checkedData', {
            key: key, remove: true
        });
    }
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

    formSelects.on('selectDjxl', function(id, vals, val){
        setTimeout(function () {
            var djxl = formSelects.value('selectDjxl', 'val').join(",");
            djyyselect(formSelects,djxl);
        }, 100);
    });


    // 日期控件
    laydate.render({
        elem: '#kssj',
        //value: new Date((new Date().getTime() - 90 * 24 * 60 * 60 * 1000)),
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
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
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#kssj').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

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

    // 监听表格操作栏按钮
    table.on('toolbar(pageTableSlry)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'exportExcel':
                exportExcel(checkStatus.data, obj.config, "checked");
                break;
            case 'exportAllExcel':
                exportAllExcel(checkStatus.data, obj.config);
                break;
            case 'print':
                printData(checkStatus.data, obj.config.cols[0], "checked");
                break;
        }
    });

    // 监听表格操作栏按钮
    table.on('toolbar(pageTableSlqk)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'exportExcel':
                exportExcel(checkStatus.data, obj.config,'checked');
                break;
            case 'exportAllExcel':
                exportAllExcel(checkStatus.data, obj.config);
                break;
            case 'print':
                printData(checkStatus.data, obj.config.cols[0], "checked");
                break;
        }
    });

    //点击高级查询--4的倍数
    $('#seniorSearch').on('click',function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        //$(this).parent().toggleClass('bdc-button-box-four');

        if ($(this).html() == '高级查询') {
            $(this).html('收起');
        } else {
            $(this).html('高级查询');
        }

        $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);
        $('.layui-table-body').height($('.bdc-table-box').height() - 129);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);
    });

    // 初始化查询
    //initSjzsTab($(".layui-form").serialize(),false);
    // 先隐藏table
    $("#pageTableSlqk-div").hide();

    // 初始化部门明细列表tab
    table.render({
        elem: '#pageTableSlry',
        toolbar: '#toolbarDemo',
        title: '部门明细列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size', //每页数据量的参数名，默认：limit
            loadTotal:true,
            loadTotalBtn:false
        },
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'xh',type: 'numbers',title: '序号',
                templet: function(d){
                    return d.LAY_TABLE_INDEX+1;
                }
            },
            {field:'orgName',sort:true,title: '部门名称'},
            {field:'procDefName',sort:true,title: '流程名称'},
            {field:'taskName',sort:true,title: '节点名称'},
            {field:'avg',sort:true,title: '平均时长（小时）',
                templet: function (d) {
                    if(d.avg == null){
                        return 0;
                    }
                    var res = (parseFloat(d.avg)/60).toFixed(2)+"";
                    return res == "0.00"?0:res;
                }
            },
            {field:'sum',sort:true,title: '总时长（小时）',
                templet: function (d) {
                    if(d.sum == null){
                        return 0;
                    }
                    var res = (parseFloat(d.sum)/60).toFixed(2)+"";
                    return res == "0.00"?0:res;
                 }
            },
            {field:'count',sort:true, title: '办件量(次)', sort: true}
        ]],
        data: [],
        page: true,
        parseData: function (res) {
            // 设置选中行
            if(res.content && res.content.length > 0){
                var checkedData = layui.sessionData('checkedData');
                res.content.forEach(function (v) {
                    $.each(checkedData, function(key, value){
                        if(key == v.orgName+"_"+v.procDefKey+"_"+v.count+"_"+v.username+"_"+v.taskName){
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
            slryMxCurrentPageData = data.data;
        }
    });

    // 初始化部门明细列表tab
    table.render({
        elem: '#pageTableSlqk',
        toolbar: '#toolbarDemo',
        title: '部门明细列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size', //每页数据量的参数名，默认：limit
            loadTotal:true,
            loadTotalBtn:false
        },
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'xh',type: 'numbers',title: '序号',
                templet: function(d){
                    return d.LAY_TABLE_INDEX+1;
                }
            },
            {field:'xmid',sort:true,title: 'xmid',hide:true},
            {field:'gzldymc',sort:true,title: '类型'},
            {field:'ywxl',sort:true,title: '业务细类'},
            {field:'qlr',sort:true, title: '权利人'},
            {field:'bdcdyh',sort:true, title: '不动产单元号'},
            {field:'zl',sort:true,title: '坐落'},
            {field:'cjr',sort:true, title: '创建人'},
            {field:'slsj',sort:true, title: '受理时间'},
            {field:'ajzt',sort:true, title: '状态'}
        ]],
        data: [],
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
                data: res.content //解析数据列表
            }
        },
        done: function (data) {
            slqkMxCurrentPageData = data.data;
        }
    });

    // 初始化加载部门明细数据
    table.reload("pageTableSlry", {
        url: slryMxDataUrl
        ,where: {kssj: $("#kssj").val(), jzsj: $("#jzsj").val(),type:""}
        , page: {
            curr: 1  //重新从第 1 页开始
        }
        ,done: function (data) {
            slryMxCurrentPageData = data.data;
        }
    });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(pageTableSlry)', function(obj){
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : slryMxCurrentPageData;

        $.each(data, function(i, v) {
            var keyT =  v.orgName+"_"+v.procDefKey+"_"+v.count+"_"+v.username+"_"+v.taskName;
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

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(pageTableSlqk)', function(obj){
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : slqkMxCurrentPageData;

        $.each(data, function(i, v) {
            var keyT =  v.xmid;
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

    $('.reseticon').on('click',function(item){
        $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
        $(this).hide();
        layui.form.render("select");
    })
    // $('#reset').on('click',function(item){
    //     $('.bdc-percentage-container').find('.layui-form')
    //         .find('select').find("option:eq(0)")
    //         .attr("selected","selected");
    //     setTimeout($('.bdc-percentage-container').find('.layui-form')
    //         .find('select').parent().find('input').val(''),100);
    //     $('.reseticon').hide();
    //    // formSelects.disabled("selectBjry");
    // })
    //重置树结构
    $('#reset').on('click', function () {
        var zTreeObj = $.fn.zTree.getZTreeObj("select_tree");//ztree对象
        zTreeObj.checkAllNodes(false);//设置全部为false,不选中节点
        $('.org_select_show').text('选择');
        $('.org_select_tree').css('display', 'none');
        $("input[name='bmmc']").val("");//清空input值
        $("input[name='djbmdm']").val("");//清空input值
    });
});

/**
 * @date 2019.03.15 14:46
 * @author chenyucheng
 * @param queryData
 * @return
 */
function initSjzsTab(queryData,showEchart) {
    /*当前tab也默认不可用部门和人员下拉框*/
    layui.form.render("select");
    var tableHead = "维度";
    var barTitle = "工作量";
    var url;


    //if(tjlx == "djxl") {
    myChartName = "slryChart";
    $("#slryChart").show();
    $("#skqkChart").hide();
    // echart图表的数据源当选择人员的时候则换接口
    if(tjwdStr == "wd_slry"){
        url = slryUrl;
    }else{
        url = slqkUrl
    }
    $.ajax({
        url: url,
        type: 'get',
        data: queryData,
        success: function (data) {
            // 数据置空
            yData = [];
            xData = [];
            var avgInfodata = [];
            var mj = [];
            
            var zj = 0;
            resultData = data;
            $.each(resultData, function (i, d) {
                if (tjwdStr == "wd_slry") {
                    yData.push(d.orgName);
                    var times = d.count;
                    xData.push(times);
                    avgInfodata.push(d.sum)
                    zj += parseInt(times);
                }else{

                    var mc = d.DJXLMC?d.DJXLMC:d.DJYYMC;
                    var dm = d.DJXL?d.DJXL:d.DJXL;
                    var times = d.REALCOUNT;
                    yData.push(mc);
                    xData.push(times);
                    zj += parseInt(times);
                    mj.push(d.MJ)
                    // xData.push(times);
                    // mc要去重，因为有以下那样的数据，部门会重复，所以要去重
                    // a部门 8次 办理中
                    // a部门 9次 办结
                }
            });
            layui.form.render("select");
            // 新建table放到chart-table容器中

            if(tjwdStr == "wd_slry"){
                $(".chart-table").html(
                    "<table class='layui-table' id='hz-table'>" +
                    "<colgroup>" +
                    "<col width='200'>" +
                    "    <col width='150'>" +
                    "    </colgroup>" +
                    "    <thead>" +
                    "    <tr>" +
                    "    <th>" + tableHead + "</th>" +
                    "    <th lay-sort='lay-sort' sort='true'>次数</th>" +
                    "    <th lay-sort='lay-sort' sort='true'>总时长(小时)</th>" +
                    "    <th lay-sort='lay-sort' sort='true'>平均时长(小时)</th>" +
                    "    </tr> </thead> <tbody> </tbody> </table>");
            }else{
                $(".chart-table").html(
                    "<table class='layui-table' id='hz-table'>" +
                    "<colgroup>" +
                    "<col width='200'>" +
                    "    <col width='150'>" +
                    "    </colgroup>" +
                    "    <thead>" +
                    "    <tr>" +
                    "    <th>" + tableHead + "</th>" +
                    "    <th lay-sort='lay-sort' sort='true'>总面积(平方米)</th>" +
                    "    <th lay-sort='lay-sort' sort='true'>次数</th>" +
                    "    </tr> </thead> <tbody> </tbody> </table>");
            }

            // 完善表格信息
            var tableBody = $("#hz-table").find("tbody");
            var countBmColor = 0;
            /*填写右侧的table*/
            var zsc = 0;
            //var zavg = 0;
            var mjzh = 0;
            if(tjwdStr == "wd_slry"){
                $.each(yData, function (i, d) {
                    zsc+=parseInt(avgInfodata[i])/60;
                    //zavg+=parseInt(avgInfodata[i])/parseInt(xData[i]);
                    var hj = avgInfodata[i]==0?0:(parseInt(avgInfodata[i])/parseInt(xData[i])/60).toFixed(2);
                    if(countBmColor%2==0) {
                        tableBody.append("<tr onclick='jumpToDetail(this)' class='tr-dbclick' id='" + d + "'>" +
                            "<td class='td-dm' id='" + d + "'>" + d + "</td>" +
                            "<td class='td-js'>" + parseInt(xData[i]) + "</td>" +
                            "<td class='td-js'>" + (parseInt(avgInfodata[i])/60).toFixed(2) + "</td>" +
                            "<td class='td-js'>" + hj + "</td>" +
                            "</tr>")
                    }else{
                        tableBody.append("<tr onclick='jumpToDetail(this)' class='tr-dbclick bmColor' id='" + d + "'>" +
                            "<td class='td-dm' id='" + d + "'>" + d + "</td>" +
                            "<td class='td-js'>" + parseInt(xData[i]) + "</td>" +
                            "<td class='td-js'>" + (parseInt(avgInfodata[i])/60).toFixed(2) + "</td>" +
                            "<td class='td-js'>" + hj + "</td>" +
                            "</tr>")
                    }
                    countBmColor++
                });
            }else{
                $.each(yData, function (i, d) {
                    mjzh+=parseFloat(mj[i])
                    if(countBmColor%2==0) {
                        tableBody.append("<tr onclick='jumpToDetail(this)' class='tr-dbclick' id='" + d + "'>" +
                            "<td class='td-dm' id='" + d + "'>" + d + "</td>" +
                            "<td class='td-js'>" + mj[i] + "</td>" +
                            "<td class='td-js'>" + parseInt(xData[i]) + "</td>" +
                            "</tr>")
                    }else{
                        tableBody.append("<tr onclick='jumpToDetail(this)' class='tr-dbclick bmColor' id='" + d + "'>" +
                            "<td class='td-dm' id='" + d + "'>" + d + "</td>" +
                            "<td class='td-js'>" + mj[i] + "</td>" +
                            "<td class='td-js'>" + parseInt(xData[i]) + "</td>" +
                            "</tr>")
                    }
                    countBmColor++
                });
            }


            if(showEchart){
                if(tjwdStr == "wd_slry"){
                    tableBody.append("<tr class='tr-zj' id='trhj'>" +
                        "<td>合计</td>" +
                        "<td class='td-js'>" + zj + "</td>" +
                        "<td class='td-js'>" + zsc.toFixed(2) + "</td>" +
                        "<td class='td-js'>" + (zsc/zj).toFixed(2) + "</td>" +
                        "</tr>");
                    $('.bdc-total').find('i').text(zj);
                }else{
                    tableBody.append("<tr class='tr-zj' id='trhj'>" +
                        "<td>合计</td>" +
                        "<td class='td-js'>" + mjzh.toFixed(2) + "</td>" +
                        "<td class='td-js'>" + zj + "</td>" +
                        "</tr>");
                    $('.bdc-total').find('i').text(zj);
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

/**
 *
 * @date 2019.07.11 14:19
 * @author 陈玉城
 * @return
 */
function seeInfo() {
    tabId = "xxxx";
    element = layui.element;
    element.tabChange('tabFilter', tabId);
    $("#search").click();
    return true;
}

/**
 * 返回上一级
 * @date 2019.07.13 21:55
 * @author chenyucheng
 * @return
 */
function backAllBm() {
    //返回需要将部门和人员下拉框置空
    // formSelects.value("selectBmmc",[]);
    $("input[name=bmmc]").val("");
    $("input[name=djbmdm]").val("");
    formSelects.value("selectLcmc",[]);
    formSelects.value("selectBjry",[]);
    layui.form.render("select");
    tabId = "sjzs";
    $("#search").click();
    for(var key in layui.sessionData('checkedData')){
        layui.sessionData('checkedData', {
            key: key, remove: true
        });
    }
    $(".reseticon").hide();
    $("#back").hide();
    $("#step-back").hide();
}

/**
 *
 * @date 2019.03.15 14:35
 * @author hanyaning
 * @param tabName 当前tab页的id
 * @description tab页点击事件
 */
function tabChangeEvent(tabName,table) {
    tabId = tabName;
    if (tabName == "sjzs") {
        changeChart();
    } else if (tabName == "xxxx") {
        layui.form.render("select");
       if($('#pageTableSlry').parent().find('tbody').find('tr').length==0){
            // 重新请求
            table.reload("pageTableSlry", {
                url: slryMxDataUrl
                ,where: {kssj: $("#kssj").val(), jzsj: $("#jzsj").val(),type:""}
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
                ,done: function (data) {
                    setHeights();
                    slryMxCurrentPageData = data.data;
                }
            });
        }

        if($('#pageTableSlqk').parent().find('tbody').find('tr').length==0){
            // 重新请求
            var obj = {};
            // var djjg = formSelects.value('selectBmmc', 'name').join(",");
            var djjg = $("input[name=bmmc]").val();
            var slrmc = formSelects.value('selectBjry', 'name').join(",");
            var dimension = $("#dimension").find("option:selected").val();
            var processname = formSelects.value('selectLcmc', 'name').join(",");
            obj["djjg"] = djjg;
            obj["slrmc"] = slrmc;
            obj["dimension"] = dimension;
            obj["processname"] = processname;
            obj["slqklx"] = tjlx;
            obj["kssj"] = $("#kssj").val();
            obj["jzsj"] = $("#jzsj").val();
            obj["type"] = '';
            table.reload("pageTableSlqk", {
                url: slqkMxDataUrl
                ,where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
                ,done: function (data) {
                    setHeights();
                    slqkMxCurrentPageData = data.data;
                }
            });
        }
    }

    setHeights("change");
    table.resize('pageTableSlry');
    table.resize('pageTableSlqk');

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
    // var djjg = formSelects.value('selectBmmc', 'name').join(",");
    var djjg = $("input[name=djbmdm]").val();
    var slrmc = formSelects.value('selectBjry', 'name').join(",");
    var djxl = formSelects.value('selectDjxl', 'val').join(",");
    var djlx = formSelects.value('selectDjlx', 'val').join(",");
    var djyy = formSelects.value('selectDjyy', 'name').join(",");
    var jdmc = formSelects.value('selectJdmc', 'val').join(",");
    var processname = formSelects.value('selectLcmc', 'name').join(",");
    var dimension = $("#dimension").find("option:selected").val();
    queryObject["djjg"] = djjg;// 部门
    queryObject["slrmc"] = slrmc;// 受理人名称
    queryObject["jdmc"] = jdmc;// 节点名称
    queryObject["djxl"] = djxl;// 登记小类
    queryObject["djlx"] = djlx;// 登记原因
    queryObject["djyy"] = djyy;// 登记类型
    queryObject["dimension"] = dimension;// 维度
    queryObject["processname"] = processname;// 流程
    queryObject["slqklx"] = tjlx;
    addModel();
    if (tjwdStr == "wd_slry") {// 工作量统计的部门人员
        $("#pageTableSlry-div").show();
        $("#pageTableSlqk-div").hide();

        queryObject["type"]="";
        table.reload("pageTableSlry", {
            url: slryMxDataUrl
            ,where: queryObject
            , page: {
                curr: 1  //重新从第 1 页开始
            }
            ,done: function (data) {
                slryMxCurrentPageData = data.data;
                //alert("g")
                removeModal();
                setHeights('change');
            }
        });
    }else {// 工作量统计的全部
        $("#pageTableSlry-div").hide();
        $("#pageTableSlqk-div").show();

        // 重新请求
        queryObject["type"]="";
        table.reload("pageTableSlqk", {
            url: slqkMxDataUrl
            ,where: queryObject
            , page: {
                curr: 1  //重新从第 1 页开始
            }
            ,done: function (data) {
                slqkMxCurrentPageData = data.data;
                removeModal();
                setHeights('change');
            }
        });
    }
    initSjzsTab(queryObject,true);

    return false;
}

function changeChart() {
    /*计算柱形图的高度，以右侧table的高度为参照
     * 该table每行高度为38.6px*/
    var contentHeight = $('#tjbg-area').height();
    $("#" + myChartName).css({
        "height": contentHeight + 25
    });
    $("#sjfx-area").css({
        "height": contentHeight
    });

    echarts.init(document.getElementById("slryChart")).dispose();
    echarts.init(document.getElementById("slqkChart")).dispose();
    myChart = echarts.init(document.getElementById(myChartName));

    //使用制定的配置项和数据显示图表
    myChart.setOption(option);
    myChart.resize();
    myChart.on("click", function (param) {
        jumpToTableInfo(param,"");
    });
}

function jumpToTableInfo(param,clickRyid){
    layui.use(['formSelects'], function () {
        if(clickRyid == ""){
            clickRyid = param.name;
            if(tjwdStr == "wd_slry"){
                clickRyid = param.name;
            }
        }
        var dimension = $("#dimension").find("option:selected").val();
        if(dimension=="wd_slry"){
            var valueArr = [];
            valueArr.push(clickRyid);
            layui.formSelects.value("selectBjry",valueArr);
        }else {

            if(tjlx == "djxl"){
                var valueArr = [];
                var value = $('dl[xid="selectDjxl"]').find('span:contains("'+clickRyid+'")').parents('dd').attr("lay-value");
                valueArr.push(value)
                layui.formSelects.value("selectDjxl",valueArr);
                setTimeout(function () {
                    var djxl = formSelects.value('selectDjxl', 'val').join(",");
                    djyyselect(formSelects,djxl);
                }, 100);
            }
            if(tjlx == "djyy"){
                var valueArr = [];
                var value = $('dl[xid="selectDjyy"]').find('span:contains("'+clickRyid+'")').parents('dd').attr("lay-value");
                valueArr.push(value)
                layui.formSelects.value("selectDjyy",valueArr);
            }
        }

        seeInfo();
        $("#back").show();
        $("#step-back").show();
    })

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
        if(isNotBlank(currectBmid)){
          data[i].orgName = convertZdDmToMc("bm", currectBmid, "zdList");
        }
        data[i].avg = dealFloat(data[i].avg);
        data[i].sum = dealFloat(data[i].sum);
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

function dealFloat(data){
    if(data == null){
        return 0;
    }
    var res = (parseFloat(data)/60).toFixed(2)+"";
    return res == "0.00"?0:res;
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
        dataType: "json",
        data: paramData,
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
    tjlx = type;
    layui.form.render("select");
    //$("#search").click();
}

/**
 * 切换统计维度 受理人员 受力情况
 * @param item
 */
function changeFun(item){
    if($(item).parent().find('.layui-this').attr('lay-value') == "wd_slry"){
        $("#count-type-select").attr("disabled","disabled");
        tjwdStr = "wd_slry";
        formSelects.disabled("selectDjxl");
        formSelects.disabled("selectDjlx");
        formSelects.disabled("selectDjyy");
        formSelects.undisabled("selectJdmc");

    }else{
        $("#count-type-select").removeAttr("disabled");
        tjwdStr = "wd_slqk";
        formSelects.undisabled("selectDjxl");
        formSelects.undisabled("selectDjlx");
        formSelects.undisabled("selectDjyy");
        formSelects.disabled("selectJdmc");

    }
    layui.form.render("select");
    //$("#search").click();
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

function jumpToDetail(item){
    jumpToTableInfo(null,$(item).attr("id"));
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

/**
 * 加载下拉框
 */
function initFormSelect(formSelects){
    // bmselect(formSelects);//部门
    ryselect(formSelects);//人员
    lcselect(formSelects);//流程
    djxlAndDjlxselect(formSelects);//登记小类
    djyyselect(formSelects,'');
}

function djyyselect (formSelects,djxl){
    $.ajax({
        url: djyySelecList,
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
//选用树结构接口
// function bmselect (formSelects){
//     $.ajax({
//         url: bmselecList,
//         dataType: "json",
//         success: function (data) {
//             data.forEach(function(item){
//                 bmmcSelList.push({
//                     name: item.name,
//                     value: item.id
//                 })
//             })
//             formSelects.data('selectBmmc', 'local', {
//                 arr: bmmcSelList
//             });
//         }
//     });
// }

function ryselect (formSelects){
    $.ajax({
        url: ryselecList,
        dataType: "json",
        success: function (data) {
            data.forEach(function(item){
                bjrySelList.push({
                    name: item.alias,
                    value: item.alias
                })
            })
            formSelects.data('selectBjry', 'local', {
                arr: bjrySelList
            });
        }
    });
}

function lcselect (formSelects){
    $.ajax({
        url: lcselecList,
        dataType: "json",
        success: function (data) {
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

function djxlAndDjlxselect (formSelects){
    $.ajax({
        url: djlxAndDjxlselecList,
        dataType: "json",
        success: function (data) {
            var djxllist = [];
            var djlxlist = [];
            for (var key in data){
                if(key =="djxl"){
                    data[key].forEach(function(item){
                        djxllist.push({
                            name: item.MC,
                            value: item.DM
                        })
                    })
                }
                if(key =="djlx"){
                    data[key].forEach(function(item){
                        djlxlist.push({
                            name: item.MC,
                            value: item.DM
                        })
                    })
                }
            }

            formSelects.data('selectDjxl', 'local', {
                arr: djxllist
            });
            formSelects.data('selectDjlx', 'local', {
                arr: djlxlist
            });
        }
    });
}




