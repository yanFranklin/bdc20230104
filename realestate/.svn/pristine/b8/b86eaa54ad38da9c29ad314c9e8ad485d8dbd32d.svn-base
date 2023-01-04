layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

/** 定义全局变量*/
var zdList = {bm: "", ry: ""};// 定义字典信息全局变量
var tjlx = "allBm";//统计类型：分allBm和bmry allBm柱形图y轴为所有部门，bmry则为所选部门下的所有人员
var tabId = "sjzs";//当前tab页id
var myChartName = "allChart";//当前chart名
var cloudAllBmStr = "";
//var charNameStr = "";
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
var rymxCurrentPageData = [];
var bmmxCurrentPageData = [];
var mxBjlCurrentPageData = [];
var allUsers = [];

var bmCacheData;
var processData;
var nodeData;
var queryObject;
var jdmcZdObj;

//工作量统计所有数据
var allDataUrl="/realestate-inquiry-ui/gzltj/count";
//工作量统计部门统计数据
var bmDataUrl="/realestate-inquiry-ui/gzltj/bmry/count";
//办件量统计所有数据
var allBjlDataUrl="/realestate-inquiry-ui/gzltj/countBjl";
//办件量统计部门统计数据
var bmBjlDataUrl="/realestate-inquiry-ui/gzltj/bmry/countBjl";

//工作量统计部门明细数据
//var bmMxDataUrl="/realestate-inquiry-ui/gzltj/bm/mx?type=print";
var bmMxDataUrl="/realestate-inquiry-ui/gzltj/bm/mx";
//工作量统计人员明细数据
var ryMxDataUrl="/realestate-inquiry-ui/gzltj/ry/mx";
//办件量统计部门明细数据
var bmMxBjlDataUrl="/realestate-inquiry-ui/gzltj/bm/mxBjl";
//办件量统计人员明细数据
var ryMxBjlDataUrl="/realestate-inquiry-ui/gzltj/ry/mxBjl";
//办件量明细数据
var MxBjlDataUrl="/realestate-inquiry-ui/gzltj/mxBjl";
//办件量明细数据打印勾選
var MxBjlPrintDataUrl="/realestate-inquiry-ui/gzltj/mxBjl/print";
//打印fr3路劲
var fr3ModelUrlBm=getIP() + "/realestate-inquiry-ui/static/print/bdc_bm_gzltj.fr3";
var fr3ModelUrlRy=getIP() + "/realestate-inquiry-ui/static/print/bdc_ry_gzltj.fr3";
var fr3ModelUrlBjl=getIP() + "/realestate-inquiry-ui/static/print/bdc_bjl_gzltj.fr3";
var testUrl = getIP() +"/realestate-inquiry-ui/gzltj/test";

var formSelects,table;
// 部门下拉数组
var bmmcSelList = [];
// 人员下拉数组
var bjrySelList = [];
// 节点下拉数组
var jdmcSelList = [];
// 流程下拉数组
var lcmcSelList = [];
// 季度下拉数组
var jdSelList = [];

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

    //addModel();//添加遮罩
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element;
    table = layui.table;
    formSelects = layui.formSelects;
    var mrcx = $.getUrlParam('mrcx');
    //2.2 js渲染复选框
    // formSelects.data('selectJs', 'local', {
    //     arr: [
    //         {"name": "北京", "value": 1},
    //         {"name": "上海", "value": 2,"selected":"selected"},
    //         {"name": "广州", "value": 3},
    //         {"name": "深圳", "value": 0},
    //         {"name": "天津", "value": 5}
    //     ]
    // });
    for(var key in layui.sessionData('checkedData')){
        layui.sessionData('checkedData', {
            key: key, remove: true
        });
    }

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
    //$('#ry-select').attr("disabled","disabled");

    //组织单选
    // formSelects.on('selectBmmc',function(id, vals, val, isAdd, isDisabled){
    //     setTimeout(function () {
    //         getBmryList(formSelects.value('selectBmmc', 'val').join(","),"click");
    //     },1000)
    // });


    // 监听季度
    form.on('select(season)', function (data) {
        controlXIcon($(this));
        var now = new Date()
        var season = $("#season").val();
        var nowYear = now.getFullYear(); //当前年
        var bgt = "";
        var edt = "";
        if(season == "spring"){
            bgt = nowYear+"/01/01";
            edt = nowYear+"/03/31";
        }else if(season == "summer"){
            bgt = nowYear+"/04/01";
            edt = nowYear+"/06/30";
        }else if(season == "autumn"){
            bgt = nowYear+"/07/01";
            edt = nowYear+"/09/30";
        }else if(season == "winter"){
            bgt = nowYear+"/10/01";
            edt = nowYear+"/12/31";
        }


        if(season ==  ""){
            $("#jzsj").val("");
            $("#kssj").val("");
        }else{
            // 日期控件
            layui.laydate.render({
                elem: '#kssj',
                value: new Date(bgt),
                format: 'yyyy-MM-dd HH:mm:ss'
            });
            layui.laydate.render({
                elem: '#jzsj',
                value: new Date(edt),
                format: 'yyyy-MM-dd HH:mm:ss'
            });
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
    table.on('toolbar(pageTableBmmx)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'exportExcel':
                if(checkStatus.data.length  > 0){
                    exportExcel(checkStatus.data, obj.config, "checked");
                }else{
                    warnMsg("请选择需要导出Excel的数据行!");
                }
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
    table.on('toolbar(pageTableRymx)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'exportExcel':
                if(checkStatus.data.length  > 0){
                    exportExcel(checkStatus.data, obj.config,'checked');
                }else{
                    warnMsg("请选择需要导出Excel的数据行!");
                }
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
    table.on('toolbar(pageTableBjlBmmx)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'exportExcel':
                if(checkStatus.data.length  > 0){
                    exportExcel(checkStatus.data, obj.config, "checked");
                }else{
                    warnMsg("请选择需要导出Excel的数据行!");
                }
                // exportExcel(checkStatus.data, obj.config, "checked");
                break;
            case 'exportAllExcel':
                exportAllExcel(checkStatus.data, obj.config);
                break;
            case 'print':
                printData(checkStatus.data, obj.config.cols[0], "checked");
                break;
        }
    });

    // 初始化查询
    var queryParams = {};
    $(".layui-form").serializeArray().forEach(function (item, index) {
        queryParams[item.name] = item.value;
    });
    if(isNullOrEmpty(mrcx) || mrcx=='true') {
        initSjzsTab(queryParams, true);
    }
    // 先隐藏table
    $("#pageTableBjlBmmx-div").hide();
    $("#pageTableRymx-div").hide();
    $("#pageTableBjlRymx-div").hide()

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
            {type: 'checkbox', fixed: 'left'},
            {field: 'xh',type: 'numbers',title: '序号',
                templet: function(d){
                    return d.LAY_TABLE_INDEX+1;
                }
            },
            {field:'orgName',sort:true,title: '部门名称'},
            {field:'procDefName',sort:true,title: '流程名称'},
            {field:'taskName', sort:true,title: '节点'},
            {field:'realCount',sort:true, title: '工作量(次)', sort: true}
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

    //人员明细表格col
    table.render({
        elem: '#pageTableRymx',
        toolbar: '#toolbarDemo',
        title: '人员明细列表',
        method: 'post',
        contentType: "application/json;charset=utf-8",
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size', //每页数据量的参数名，默认：limit
            loadTotal:true,
            loadTotalBtn:false
        },
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'xh',type: 'numbers', title: '序号',
                templet: function(d){
                    return d.LAY_TABLE_INDEX+1;
                }
            },
            {field:'username',sort:true,title:'用户名'},
            {field: 'userAlias',sort:true, title: '姓名'},
            {field: 'orgName',sort:true, title: '所属部门'},
            {field:'procDefName',sort:true,title: '流程名称'},
            {field:'taskName',sort:true, title: '节点名称'},
            {field: 'realCount',sort:true, title: '工作量(次)', sort: true},
        ]],
        data: [],
        page: true,
        parseData: function (res) {
            // 设置选中行
            if(res.content && res.content.length > 0){
                var checkedData = layui.sessionData('checkedData');
                res.content.forEach(function (v) {
                    $.each(checkedData, function(key, value){
                        if(key == v.procDefName+"_"+v.taskName+"_"+v.realCount+"_"+v.username){
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
            rymxCurrentPageData = data.data;
        }
    });
    // 初始化部门明细列表tab
    table.render({
        elem: '#pageTableBjlBmmx',
        toolbar: '#toolbarDemo',
        title: '部门明细列表',
        defaultToolbar: ['filter'],
        method: 'post',
        contentType: "application/json;charset=utf-8",
        request: {
            limitName: 'size', //每页数据量的参数名，默认：limit
            loadTotal: true,
            loadTotalBtn: false
        },
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'xh',type: 'numbers',title: '序号',
                templet: function(d){
                    return d.LAY_TABLE_INDEX+1;
                }
            },
            {field:'ORGNAME',sort:true,title: '部门名称'},
            {field:'PROCDEFNAME',sort:true,title: '流程名称'},
            {field:'USERNAME',sort:true, title: '办件人'},
            {field:'REALCOUNT',sort:true, title: '办件量(次)', sort: true}
        ]],
        data: [],
        page: true,
        parseData: function (res) {
            // 设置选中行
            if(res.content && res.content.length > 0){
                var checkedData = layui.sessionData('checkedData');
                res.content.forEach(function (v) {
                    $.each(checkedData, function(key, value){
                        if(key == v.ORGNAME+"_"+v.PROCDEFNAME+"_"+v.USERNAME){
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
            mxBjlCurrentPageData = data.data;
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

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(pageTableRymx)', function(obj){
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : rymxCurrentPageData;

        $.each(data, function(i, v) {
            var keyT =  v.procDefName+"_"+v.taskName+"_"+v.realCount+"_"+v.username;
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
    table.on('checkbox(pageTableBjlBmmx)', function(obj){
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : mxBjlCurrentPageData;

        $.each(data, function(i, v) {
            var keyT =  v.ORGNAME+"_"+v.PROCDEFNAME+"_"+v.realCount+"_"+v.USERNAME;
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

    // $('.reseticon').on('click',function(item){
    //     $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
    //     $(this).hide();
    //     layui.form.render("select");
    //     if($(this).parent().find('select').attr('id') == "bm-select"){
    //         getBmryList("","click");
    //     }
    // })
    $('#reset').on('click',function(item){

        // $('.bdc-percentage-container').find('.layui-form')
        //     .find('select').find("option:eq(0)")
        //     .attr("selected","selected");
        // setTimeout($('.bdc-percentage-container').find('.layui-form')
        //     .find('select').parent().find('input').val(''),100);
        //改用树结构
        var zTreeObj = $.fn.zTree.getZTreeObj("select_tree");//ztree对象
        zTreeObj.checkAllNodes(false);//设置全部为false,不选中节点
        OrgTreeCheck=new Map();
        $('.org_select_show').text('选择');
        $('.org_select_tree').css('display', 'none');
        $("input[name='bmmc']").val("");//清空input值
        $("input[name='djbmdm']").val("");//清空input值
        $('.reseticon').hide();
        getBmryList("","click");
        formSelects.disabled("selectBjry");
       // $('#ry-select').attr("disabled","disabled");
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
    var tableHead = "维度";
    var barTitle = "工作量";
    var url;

    //判断是否已经初始化了部门和人员字典项
    var initBmZd = false;
    if (bmZdList !=null && bmZdList.length>0) {
        initBmZd = true;
    }
    if(tjlx == "allBm") {
        myChartName = "allChart";
        $("#bmryChart").hide();
        $("#allChart").show();
        $("#bmryBjlChart").hide();
        $("#allBjlChart").hide();
        // echart图表的数据源当选择人员的时候则换接口
        if($("#dimension").val() != "wd-ry"){
            url = allDataUrl;
        }else{
            url = bmDataUrl
        }
        if(!isNotBlank(queryData["statisticsType"])){
            queryData["statisticsType"] = "END_STATISTIC";
        }
    }else if(tjlx == "allBmBlz") {
        myChartName = "allChart";
        $("#bmryChart").hide();
        $("#allChart").show();
        $("#bmryBjlChart").hide();
        $("#allBjlChart").hide();
        // echart图表的数据源当选择人员的时候则换接口
        if($("#dimension").val() != "wd-ry"){
            url = allDataUrl;
        }else{
            url = bmDataUrl
        }
        //办理中工作量
        if(!isNotBlank(queryData["statisticsType"])){
            queryData["statisticsType"] = "RUN_STATISTIC";
        }
    }else if(tjlx == "bjl_allBm"){
        tableHead = "分组项";
        barTitle = "办件量";
        myChartName = "allBjlChart";
        $("#bmryChart").hide();
        $("#allChart").hide();
        $("#bmryBjlChart").hide();
        $("#allBjlChart").show();
        url = allBjlDataUrl;
    }
    $.ajax({
        url: url,
        type: 'POST',
        contentType: "application/json;charset=utf-8",
        async: true,
        data: JSON.stringify(queryData),
        success: function (data) {
            removeModal();
            // 测试数据
            var process = data.map;
            var node = data.node;
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
                name: '办理中',
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
                name: '其他',
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
            $.each(resultData, function (i, d) {
                if (tjlx == "allBm" || tjlx == "allBmBlz") {
                    cloudAllBmStr+=d.orgName;
                    yData.push(d.orgName);
                    if (!initBmZd) {
                        bmZdList.push({
                            MC: d.orgName,
                            DM: d.orgId
                        });
                        bmmcSelList.push({
                            name: d.orgName,
                            value: d.orgId
                        })
                        zdList.bm = bmZdList;
                    }
                    var times = d.realCount?d.realCount:d.REALCOUNT;
                    xData.push(times);
                    zj += parseInt(times);
                }else{
                    var mc = d.DJJG?d.DJJG:d.GZLDYMC;
                    var dm = d.DJJG?d.DJJG:d.GZLDYMC;
                    mc = mc?mc:d.SLR;
                    dm = dm?dm:d.SLR;
                    zdList.bm = bmZdList;
                    var times = d.realCount?d.realCount:d.REALCOUNT;
                    // xData.push(times);
                    // mc要去重，因为有以下那样的数据，部门会重复，所以要去重
                    // a部门 8次 办理中
                    // a部门 9次 办结

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
                                if(t.AJZT == "1"){
                                    doingCase+=parseInt(times);
                                    doingZj+=times;
                                }else{
                                    otherCase+=parseInt(times);
                                    otherZj+=times;
                                }

                                xDataTemp+=times;
                                // 查看列表，表格下方合计
                                allCase+=parseInt(times);
                            }
                        })
                        xData.push(xDataTemp);
                        zbXData.push(doingCase);
                        qtXData.push(otherCase);
                        zj += parseInt(allCase);
                    }
                }
            });

            // 完善部门和员工下拉框
            //console.log(new Date().getTime());
            if (!initBmZd && (bmZdList !=null && bmZdList.length>0)) {
                var listRy = [];
                bmZdList.forEach(function (d) {
                    if(d.DM!="" && d.DM!=undefined){
                        $("#bm-select").append("<option value='" + d.DM + "'>" + d.MC + "</option>")
                        var listRytemp = getBmryList(d.DM,"init");
                        listRy = listRy.concat(listRytemp);
                    }
                });
                allUsers = listRy;
                var objUser = {};
                 //allUsers.push(null);
                var allUsersT = [];
                allUsers.forEach(function(d){
                   if(d != null){
                       allUsersT.push(d);
                   }
                })
                allUsers = allUsersT;
                allUsers = allUsers.reduce(function(item, next) {
                    objUser[next.DM] ? '' : objUser[next.DM] = true && item.push(next);
                    return item;
                }, []);
                allUsers.forEach(function(d){
                    bjrySelList.push({
                        name: d.MC,
                        value: d.DM
                    })
                })
                // 查询出部门后，在查询出这些部门下所有的人员
                //layui.form.render("select");
                zdList.ry = listRy;
                makeSelectFormZdlist("ry-select", "zdList", "ry")

            }
            var newLength = calObjLength(process);
            var oldLength = calObjLength(processData);
            if (process && !processData || newLength>oldLength) {
                $("#process").find('option:not(:eq(0))').remove();
                for(var key in process){
                    $("#process").append("<option value='" + key + "'>" + process[key] + "</option>")
                    lcmcSelList.push({
                        name: process[key],
                        value: key
                    })
                }
                processData = process;
            }
            newLength = calObjLength(node);
            oldLength = calObjLength(nodeData);
            if (node && !nodeData || newLength>oldLength) {
                jdmcZdObj = zdJdmc();
                $("#jdmc").find('option:not(:eq(0))').remove();
                node.forEach(function (d) {
                    //$("#jdmc").append("<option value="+jdmcZdObj[d]+">" + d + "</option>")
                    jdmcSelList.push({
                        name: d,
                        value: jdmcZdObj[d]
                    })
                });
                nodeData = node;
            }
            // 这里需要判断一下，如果下拉框已经选择了，则不能再次初始化，因为初始化话会清空已选的值
            if(formSelects.value('selectBmmc', 'name').join(",").length == 0){
                formSelects.data('selectBmmc', 'local', {
                    arr: bmmcSelList
                });
            }
            // 这里需要判断一下，如果下拉框已经选择了，则不能再次初始化，因为初始化话会清空已选的值
            if(formSelects.value('selectBjry', 'name').join(",").length == 0){
                formSelects.data('selectBjry', 'local', {
                    arr: bjrySelList
                });
            }
            // 这里需要判断一下，如果下拉框已经选择了，则不能再次初始化，因为初始化话会清空已选的值
            if(formSelects.value('selectLcmc', 'name').join(",").length == 0){
                formSelects.data('selectLcmc', 'local', {
                    arr: lcmcSelList
                });
            }
            // 这里需要判断一下，如果下拉框已经选择了，则不能再次初始化，因为初始化话会清空已选的值
            if(formSelects.value('selectJdmc', 'name').join(",").length == 0){
                formSelects.data('selectJdmc', 'local', {
                    arr: jdmcSelList
                });
            }


            layui.form.render("select");
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
                "    <th lay-sort='lay-sort' sort='true'>次数</th>" +
                "    </tr> </thead> <tbody> </tbody> </table>");
            // 完善表格信息
            var tableBody = $("#hz-table").find("tbody");
            var countBmColor = 0;
            /*填写右侧的table*/
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
                    $('.bdc-total').find('i').text(doingZj+otherZj+"次，其中办理中："+doingZj+"次，其他："+otherZj);
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
            // 办件量的时候，echarts为堆叠，所以要特殊处理option
            if (tjlx == "bjl_allBm") {
                seriesContentYb['data'] = zbXData;
                seriesContentQt['data'] = qtXData;
                SumSeriesData['data'] = xData;
                seriesData.push(seriesContentYb);
                seriesData.push(seriesContentQt);
                seriesData.push(SumSeriesData);
                option['series'] = seriesData;
                option['legend'] = {
                  data: ['办理中','其他']
                }
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
    $("input[name='bmmc']").val("");
    formSelects.value("selectJdmc",[]);
    formSelects.value("selectLcmc",[]);
    formSelects.value("selectBjry",[]);

    // $("#bm-select option:contains('请选择')").attr("selected", "selected")
    // $("#jdmc option:contains('请选择')").attr("selected", "selected")
    // $("#process option:contains('请选择')").attr("selected", "selected")
    // $("#ry-select option:contains('请选择')").attr("selected", "selected")
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
 * 获取人员所在部门
 * @date 2019.03.13 21:55
 * @author hanyaning
 * @param null
 * @return
 */
function getRyssbm() {
    var currectBmid = $("#bm-select").val();
    if (currectBmid) {
        return convertZdDmToMc("bm", currectBmid, "zdList")
    } else {
        return false;
    }
}

/**
 *
 * @date 2019.03.13 21:56
 * @author hanyaning
 * @param bmid 部门id
 * @return  动态获取不同部门下人员，名填充人员下拉框
 */
function getBmryList(bmid,type) {
    var rsultData = null;
    if(type == "click"){
        if(bmid == "" || bmid == null){// 没选部门则显示所有用户
            zdList.ry = allUsers;
            makeSelectFormZdlist("ry-select", "zdList", "ry")
            return;
        }
    }

    var url = '/realestate-inquiry-ui/gzltj/bmusers';
    $.ajax({
        url: url,
        type: 'get',
        async: false,
        data: {orgId:bmid},
        success: function (data) {
            if(data.length>0){
                if(type == "click"){
                    layui.form.render("select");
                    zdList.ry = data;
                    makeSelectFormZdlist("ry-select", "zdList", "ry");
                    var tempSelect = [];
                    data.forEach(function(d){
                        tempSelect.push({
                            name: d.MC,
                            value: d.DM
                        })
                    });
                    formSelects.value("selectBjry",[]);
                    formSelects.data('selectBjry', 'local', {
                        arr: tempSelect
                    });
                }else {
                    rsultData = data;
                }
            }
        }
    })
    if(type == "init"){
        return rsultData;
    }
}

/**
 *
 * @date 2019.03.13 21:57
 * @author hanyaning
 * @param selectId 下拉框控件id
 * @param zdListName 字典项list名字（字符串）
 * @param 字段项name，如djlx
 * @return 通过字典list填充下拉框
 */
function makeSelectFormZdlist(selectId, zdListName, zdName) {
    //$("#" + selectId).html('<option value="" selected="selected">请选择</option>');
    var list = eval(zdListName + "." + zdName);
    if (list !=null && list.length>0) {
        var distinctObj = {};
        list.forEach(function (d) {
            if(d && d.DM){
                if(!distinctObj[d.DM]){
                    //$("#" + selectId).append("<option value='" + d.DM + "'>" + d.MC + "</option>")
                    distinctObj[d.DM] = d.MC;
                }
            }
        });
    }
    layui.form.render("select");
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
    var statisticsType  = "";
    if(tjlx == "allBm"){
        statisticsType = "END_STATISTIC"
    }else if(tjlx == "allBmBlz"){
        statisticsType = "RUN_STATISTIC"
    }
    if (tabName == "sjzs") {
        changeChart();
    } else if (tabName == "xxxx") {
        layui.form.render("select");
       if($('#pageTableBmmx').parent().find('tbody').find('tr').length==0){
            // 重新请求
            var bmmxParams = {kssj: $("#kssj").val(), jzsj: $("#jzsj").val(), type: "", statisticsType: statisticsType};
            reloadBmmx(bmmxParams, '');
        }
       if($('#pageTableRymx').parent().find('tbody').find('tr').length==0){
            // 重新请求
            var rymxParams = {kssj: $("#kssj").val(), jzsj: $("#jzsj").val(), type: "", statisticsType: statisticsType};
            reloadRymx(rymxParams, '');
        }
        if($('#pageTableBjlBmmx').parent().find('tbody').find('tr').length==0){
            // 重新请求
            var bjlbmmxParams = {kssj: $("#kssj").val(), jzsj: $("#jzsj").val(), type: ""};
            reloadBjlmx(bjlbmmxParams, '');
        }
    }
    setHeights("change");
    table.resize('pageTableBjlBmmx');
    table.resize('pageTableRymx');
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
    // var djjg = formSelects.value('selectBmmc', 'name').join(",");
    var djjg = $("input[name='bmmc']").val();
    var slrmc = formSelects.value('selectBjry', 'name').join(",");
    var dimension = $("#dimension").find("option:selected").val();
    var processname = formSelects.value('selectLcmc', 'name').join(",");
    queryObject["djjg"] = djjg;
    queryObject["slrmc"] = slrmc;
    queryObject["dimension"] = dimension;
    queryObject["processname"] = processname;

    addModel();
    /*依据情况展示查询台账*/
    if (tjlx == "allBm" && $("#dimension").val() != "wd-ry") {// 工作量统计的全部
        $("#pageTableBmmx-div").show();
        $("#pageTableBjlBmmx-div").hide();
        $("#pageTableRymx-div").hide();
        $("#pageTableBjlRymx-div").hide();
        // 重新请求
        queryObject["type"]="";
        queryObject["statisticsType"]="END_STATISTIC";
        reloadBmmx(queryObject, 'change');
    } else if (tjlx == "allBm" && $("#dimension").val() == "wd-ry") {// 工作量统计的部门人员
        $("#pageTableBmmx-div").hide();
        $("#pageTableRymx-div").show();
        $("#pageTableBjlRymx-div").hide();
        $("#pageTableBjlBmmx-div").hide();
        queryObject["type"]="";
        queryObject["statisticsType"]="END_STATISTIC";
        reloadRymx(queryObject, 'change');
    }else if (tjlx == "allBmBlz" && $("#dimension").val() != "wd-ry") {// 工作量统计的全部
        $("#pageTableBmmx-div").show();
        $("#pageTableBjlBmmx-div").hide();
        $("#pageTableRymx-div").hide();
        $("#pageTableBjlRymx-div").hide();
        // 重新请求
        queryObject["type"]="";
        queryObject["statisticsType"]="RUN_STATISTIC";
        reloadBmmx(queryObject, 'change');
    } else if (tjlx == "allBmBlz" && $("#dimension").val() == "wd-ry") {// 工作量统计的部门人员
        $("#pageTableBmmx-div").hide();
        $("#pageTableRymx-div").show();
        $("#pageTableBjlRymx-div").hide();
        $("#pageTableBjlBmmx-div").hide();
        queryObject["type"]="";
        queryObject["statisticsType"]="RUN_STATISTIC";
        reloadRymx(queryObject, 'change');
    }else if(tjlx == "bjl_allBm"){// 办件量统计的全部
        $("#pageTableBmmx-div").hide();
        $("#pageTableRymx-div").hide();
        $("#pageTableBjlRymx-div").hide();
        $("#pageTableBjlBmmx-div").show();
        queryObject["type"]="";
        reloadBjlmx(queryObject, 'change');
    }else{//办件量统计的部门人员
        $("#pageTableBmmx-div").hide();
        $("#pageTableRymx-div").hide();
        $("#pageTableBjlRymx-div").hide();
        $("#pageTableBjlBmmx-div").show();
    }

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
// 表格重新加载人员明细
function reloadRymx(obj, type){
    table.reload("pageTableRymx", {
        url: ryMxDataUrl,
        where: obj,
        method: 'post',
        contentType: "application/json;charset=utf-8",
        page: {
            curr: 1  //重新从第 1 页开始
        },
        done: function (data) {
            rymxCurrentPageData = data.data;
            setHeights(type);
        }
    });
}
// 表格重新加载办件量明细
function reloadBjlmx(obj, type){
    table.reload("pageTableBjlBmmx", {
        url: MxBjlDataUrl,
        where: obj,
        method: 'post',
        contentType: "application/json;charset=utf-8",
        page: {
            curr: 1  //重新从第 1 页开始
        },
        done: function (data) {
            mxBjlCurrentPageData = data.data;
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
    myChart.on("click", function (param) {
        jumpToTableInfo(param,"");
    });
}

function jumpToTableInfo(param,clickRyid){
    layui.use(['formSelects'], function () {
        if(clickRyid == ""){
            clickRyid = convertZdMcToDm('ry', param.name, 'zdList');
            if($("#dimension").val() == "wd-ry"){
                clickRyid = param.name;
            }
        }
        var dimension = $("#dimension").find("option:selected").val();
        if(dimension=="wd-bm"){
            var valueArr = [];
            valueArr.push($("#bm-select option:contains('"+clickRyid+"')").attr("value"));
            layui.formSelects.value("selectBmmc",valueArr);
            //$("#bm-select option:contains("+clickRyid+")").prop("selected", true);
            //if($("#bm-select option:contains("+clickRyid+")").length > 0){
             //   $("#bm-select").parent().find('.reseticon ').show();
            //}
        }else if(dimension=="wd-jd"){
            var valueArr = [];
            valueArr.push($("#jdmc option:contains('"+clickRyid+"')").attr("value"));
            layui.formSelects.value("selectJdmc",valueArr);
            // $("#jdmc option:contains("+clickRyid+")").prop("selected", true);
            // if($("#jdmc option:contains("+clickRyid+")").length > 0){
            //     $("#jdmc").parent().find('.reseticon ').show();
            // }
        }else if(dimension=="wd-lc"){
            var valueArr = [];
            valueArr.push($("#process option:contains('"+clickRyid+"')").attr("value"));
            layui.formSelects.value("selectLcmc",valueArr);
            // $("#process option:contains("+clickRyid+")").prop("selected", true);
            // if($("#process option:contains("+clickRyid+")").length > 0){
            //     $("#process").parent().find('.reseticon ').show();
            // }
        }else if(dimension=="wd-ry"){
            var valueArr = [];
            valueArr.push($("#ry-select option:contains('"+clickRyid+"')").attr("value"));
            layui.formSelects.value("selectBjry",valueArr);
            // $("#ry-select option:contains("+clickRyid+")").prop("selected", true);
            // if($("#ry-select option:contains("+clickRyid+")").length > 0){
            //     $("#ry-select").parent().find('.reseticon ').show();
            // }
        }
        layui.form.render("select");
        if (tjlx == "allBm" || tjlx == "allBmBlz") {
            seeInfo();
        } else if (tjlx == "bjl_allBm") {
            formSelects.undisabled("selectBjry");
            //$('#ry-select').removeAttr("disabled");
            seeInfo();
        }
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
    if($(item).parent().find('.layui-this').attr('lay-value') == "wd-ry"){
        formSelects.undisabled("selectBjry");
        //$('#ry-select').removeAttr("disabled");
    }else{
        // 禁用人员
        formSelects.disabled("selectBjry");
        //$('#ry-select').attr("disabled","disabled");
    }
    $("#search").click();
}

/**
 * 打印
 * @param data
 * @param configObj
 * @param type
 */
function printData(data, configObj, type){
    if(data.length>0){// 说明是勾选 把勾选的参数作为参数传到后台
        var obj = [];
        var type =  $("#count-type-select").find('option:selected').attr('value');
        if(type == "gzl" || type == "gzlBlz"){
            obj = [];
            for(var i=0;i<data.length;i++){
                var objItem = {};
                objItem["orgId"] = data[i].orgId;
                objItem["procDefKey"] = data[i].procDefKey;
                objItem["taskName"] = jdmcZdObj[data[i].taskName];
                objItem["username"] = data[i].username;
                obj.push(objItem);
            }
        }else{
            obj = [];
            for(var i=0;i<data.length;i++){
                var objItem = {};
                objItem["ORGNAME"] = data[i].ORGNAME;
                objItem["PROCDEFNAME"] = data[i].PROCDEFNAME;
                objItem["USERNAME"] = data[i].USERNAME;
                obj.push(objItem);
            }
            obj = data;
        }
    }
    var paramStr = $(".layui-form").serialize();
    // 时间内容有空，控件不支持，用#+等符号替换，也不支持，故选择AA作为分隔符
    paramStr = decodeURIComponent(paramStr).replace(/\+/g, "AA");
    var djjg = $("#bm-select").find("option:selected").text() == "请选择"?"":$("#bm-select").find("option:selected").text();
    var slrmc = $("#ry-select").find("option:selected").text() == "请选择"?"":$("#ry-select").find("option:selected").text();
    var dimension = $("#dimension").find("option:selected").val();
    var processname = $("#process").find('option:selected').text() == "请选择"?"":$("#process").find('option:selected').text();
    paramStr+="&processname="+encodeURIComponent(encodeURIComponent(processname));
    paramStr+="&dimension="+dimension;
    paramStr+="&type=print";
    paramStr = paramStr.replace(/=/g,"BBB");
    paramStr = paramStr.replace(/&/g,"CCC");
    paramStr = encodeURIComponent(paramStr);
    if(data.length>0){
        var objRestr = encodeURIComponent(encodeURIComponent(JSON.stringify(obj)));
        paramStr+="&printFilterJson="+objRestr;
    }else{
        paramStr+="&printFilterJson="+encodeURIComponent(encodeURIComponent("[]"));
    }
    // 工作量部门打印
    var dataUrl = getIP() +bmMxDataUrl+"?"+paramStr;
    var frUrl = fr3ModelUrlBm;
    if($("#dimension").val() == "wd-ry"){// 工作量部门打印人员打印
        dataUrl = getIP() +ryMxDataUrl+"?"+paramStr;
        frUrl = fr3ModelUrlRy;
    }
    // 办件量
    if($("#count-type-select").find('option:selected').attr('value') == "bjl"){
        if(data.length>0){// 勾选打印
            dataUrl = getIP() +MxBjlPrintDataUrl+"?"+paramStr;
        }else{// 打印全部
            paramStr+="&djjg="+encodeURI(encodeURI(djjg));
            dataUrl = getIP() +MxBjlDataUrl+"?"+paramStr;
        }
        frUrl = fr3ModelUrlBjl;
    }
    // 经定位，fr3打印不支持太长的dataUrl的参数，这里为临时解决方案
    if(dataUrl.length>1814){
        layer.alert("勾选打印参数太长，请勾选尽量少的数据！", {title: '提示'});
        return
    }
    dataUrl = dataUrl.replace(/BBB/g,"=");
    dataUrl = dataUrl.replace(/CCC/g,"&");
    print(frUrl, dataUrl, 'false');
    //savePrintInfo(frUrl,dataUrl,{"zmbh":"552","printType":"工作量统计测试打印"})
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

function jumpToDetail(item){
    jumpToTableInfo(null,$(item).attr("id"));
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


