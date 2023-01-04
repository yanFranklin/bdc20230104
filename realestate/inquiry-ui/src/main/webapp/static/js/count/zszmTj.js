/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 证书、证明统计 js
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var formSelectsVar;
// 部门下拉数组
var bmmcSelList = [];

var myChart, tabName = "szltj";
var BASE_URL = "/realestate-inquiry-ui/rest/v1.0";
var zdList = {bm: ""};// 定义字典信息全局变量
var bmZdList = [];
layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element;
    formSelectsVar = layui.formSelects;
    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if(moduleCode){
        setElementAttrByModuleAuthority(moduleCode);
    }
    var mrcx = $.getUrlParam('mrcx');
    // 加载所有组织
    loadAllOrgs();

    // 日期控件
    laydate.render({
        elem: '#kssj',
        format: 'yyyy-MM-dd',
        value: new Date(),
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
        format: 'yyyy-MM-dd',
        value: new Date(),
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#kssj').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    //采用树结构获取部门
    // $.ajax({
    //     url: '/realestate-inquiry-ui/bdczd/bm/list',
    //     type: "GET",
    //     async: false,
    //     dataType: "json",
    //     timeout: 10000,
    //     success: function (data) {
    //         if (data) {
    //             bmZdList = data;
    //             $.each(data, function (key) {
    //                 $('#djbmdm').append('<option value="' + key + '">' + data[key] + '</option>');
    //                 bmmcSelList.push({
    //                     name: data[key],
    //                     value: key
    //                 })
    //             });
    //             formSelectsVar.data('selectBmmc', 'local', {
    //                 arr: bmmcSelList
    //             });
    //             //form.render('select');
    //         }
    //     }
    // });

    $('#search').click(function () {
        if (tabName == "szltj") {
            szltj();
        } else if(tabName == "fzltj"){
            fzltj();
        } else if(tabName == "dyltj"){
            dyltj();
        }
    });
    if(isNullOrEmpty(mrcx) || mrcx=='true') {
        szltj();
    }
    // 缮证量统计
    function szltj() {
        var searchData = {};
        //赋值code
        $("input[name=djbmdm]").val($("input[name=djbmdm]").data('code'))
        $(".search").each(function (i) {
            if($(this).attr("id") != "djbmdm" && $(this).attr("id") != "zslx"){
                var value = $(this).val();
                var name = $(this).attr('name');
                if("kssj" == name && !isNullOrEmpty(value)){
                    value += " 00:00:00";
                }
                if("jzsj" == name && !isNullOrEmpty(value)){
                    value += " 23:59:59";
                }
            }else{
                var value = formSelectsVar.value($(this).attr('xm-select'), 'val').join(",");
                var name = $(this).attr("id");
            }
            searchData[name] =value;
        });
        initChart(searchData, 'zszmChart', '不动产证书、证明、证明单缮证量统计');
    }

    // 发证量统计
    function fzltj() {
        var searchData={};
        //赋值code
        $("input[name=djbmdm]").val($("input[name=djbmdm]").data('code'))
        $(".search").each(function (i) {
            if($(this).attr("id") != "djbmdm" && $(this).attr("id") != "zslx"){
                var value = $(this).val();
                var name = $(this).attr('name');
                if("kssj" == name && !isNullOrEmpty(value)){
                    value += " 00:00:00";
                }
                if("jzsj" == name && !isNullOrEmpty(value)){
                    value += " 23:59:59";
                }
            }else{
                var value = formSelectsVar.value($(this).attr('xm-select'), 'val').join(",");
                var name = $(this).attr("id");
            }
            searchData[name] =value;
        });
        // 设置发证量统计标识
        searchData["fzltj"] = "fzltj";
        initChart(searchData, 'fzltjChart', '不动产证书、证明、证明单发证量统计');
    }

    // 打印量统计
    var zszmlxs = [];
    function dyltj() {
        zszmlxs = [];
        var searchData = {};
        //赋值code
        $("input[name=djbmdm]").val($("input[name=djbmdm]").data('code'))
        $(".search").each(function (i) {
            if($(this).attr("id") != "djbmdm" && $(this).attr("id") != "zslx"){
                var value = $(this).val();
                var name = $(this).attr('name');
            }else{
                var value = formSelectsVar.value($(this).attr('xm-select'), 'val').join(",");
                var name = $(this).attr("id");
            }
            searchData[name] =value;
        });

        for(var i=0;i<formSelectsVar.value('selectZslx', 'value').length;i++){
            var item = formSelectsVar.value('selectZslx', 'value')[i];
            var obj = {"zmlx":item.value, "zmmc":item.name};
            zszmlxs.push(obj);
        }
        if(zszmlxs.length == 0){
            zszmlxs = [{"zmlx":"1", "zmmc":"证书"},{"zmlx":"2", "zmmc":"证明"},{"zmlx":"3", "zmmc":"证明单"}];
        }

        initDytjChart(searchData);
    }

    function initChart(queryData, chartid, title) {
        addModel();
        $.ajax({
            url: BASE_URL + "/zszm/count",
            type: 'post',
            async: true,
            data: JSON.stringify(queryData),
            contentType: 'application/json',
            dataType: "json",
            success: function (zszmData) {

                var yData = [];
                var xData = [];
                var zsData = [];
                var zmData = [];
                var zmdData = [];
                var zs = {
                    'name': '证书',
                    'type': 'bar',
                    'barGap':'0',
                    'barWidth' : '30', //柱图宽度
                    'label': {
                        'normal': {
                            'show': 'true',
                            'position': 'right'
                        }
                    },
                    data: zsData
                };
                var zm = {
                    'name': '证明',
                    'type': 'bar',
                    'barGap':'0',
                    'barWidth' : '30',
                    'label': {
                        'normal': {
                            'show': 'true',
                            'position': 'right'
                        }
                    },
                    data: zmData
                };
                var zmd = {
                    'name': '证明单',
                    'type': 'bar',
                    'barGap':'0',
                    'barWidth' : '30',
                    'label': {
                        'normal': {
                            'show': 'true',
                            'position': 'right'
                        }
                    }, data: zmdData
                };
                $.each(zszmData, function (i, d) {
                    if(yData.indexOf(d.djjg)===-1){
                        yData.push(d.djjg);
                    }
                    if(d.zslx == 1){
                        d['zslxmc'] = "证书"
                    }
                    if(d.zslx == 2){
                        d['zslxmc'] = "证明"
                    }
                    if(d.zslx == 3){
                        d['zslxmc'] = "证明单"
                    }
                    // d['zslxmc']=convertZdDmToMc('zslx',d.zslx,'zdList');
                });

                // 获取登记部门
                var djbmArray = new Array();
                $.each(zszmData, function (index, data) {
                    if(djbmArray.indexOf(data.djbmdm) === -1){
                        djbmArray.push(data.djbmdm);
                    }
                });

                // 获取证书对应的数量
                $.each(djbmArray, function (i, bm) {
                    var exist = false;
                    $.each(zszmData, function (index, data) {
                        if (data.zslx === '1' && data.djbmdm === bm) {
                            zsData.push(data.sl);
                            exist = true;
                        }
                    });
                    if(!exist){
                        zsData.push(0);
                    }
                });
                // 获取证明对应的数量
                $.each(djbmArray, function (i, bm) {
                    var exist = false;
                    $.each(zszmData, function (index, data) {
                        if (data.zslx === '2' && data.djbmdm === bm) {
                            zmData.push(data.sl);
                            exist = true;
                        }
                    });
                    if(!exist){
                        zmData.push(0);
                    }
                });
                // 获取证明单对应的数量
                $.each(djbmArray, function (i, bm) {
                    var exist = false;
                    $.each(zszmData, function (index, data) {
                        if (data.zslx === '3' && data.djbmdm === bm) {
                            zmdData.push(data.sl);
                            exist = true;
                        }
                    });
                    if(!exist){
                        zmdData.push(0);
                    }
                });

                sessionStorage.setItem(tabName, JSON.stringify(zszmData));
                if(zs.data.length==0){
                    zs.data=[0]
                }
                if(zm.data.length==0){
                    zm.data=[0]
                }
                if(zmd.data.length==0){
                    zmd.data=[0]
                }
                xData.push(zs);
                xData.push(zm);
                xData.push(zmd);

                var option = {
                    color:['#58a0f8','#afc946','#AFEEEE'],
                    title: {
                        text: title,
                        x: 'center',
                        textStyle: {
                            fontSize: 16,
                            fontWeight: "400",
                            fontWeight: 'bold',
                            fontFamily: "Microsoft YaHei",
                            color: "#333"
                        },
                        subtextStyle: {
                            fontSize: 12
                        }
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['证书', '证明', '证明单'],
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
                    },
                    yAxis: {
                        type: 'category',//类型
                        data: yData,//数据
                        inverse: true,
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
                    series: xData
                };

                // 动态设置柱状图y轴的长度
                var setHeight = 40;
                var autoHeight = 0;
                if (yData.length > 2) {
                    autoHeight = yData.length* setHeight*3;
                    $('#'+chartid).height(autoHeight);
                }else{
                    $('#'+chartid).height(400);
                }
                myChart = echarts.init(document.getElementById(chartid));
                //使用制定的配置项和数据显示图表
                myChart.setOption(option,true);
                myChart.resize();
                removeModal();
            },
            error: function (e) {
                setTimeout(removeModal, 100);
                removeModal();
            }
        });
    }

    // 打印量统计
    function initDytjChart(searchData){
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zszm/print/count",
            type: 'POST',
            data: JSON.stringify(searchData),
            contentType: 'application/json',
            dataType: "json",
            success: function (res) {
                if(res && res.keySet && res.valueMap) {
                    var orgNameSet = getOrgName(res.keySet)
                    // 设置要展示的数据
                    var xData = [];
                    $.each(zszmlxs, function (index, value) {
                        var data = [];
                        $.each(res.valueMap[value.zmlx], function (index, value) {
                            if (value == 0) {
                                data.push("");
                            } else {
                                data.push(value);
                            }
                        });

                        xData.push({
                            'name': value.zmmc, 'type': 'bar', barWidth : 30,barGap:0,
                            label: {
                                normal: {
                                    show: true,
                                    position: 'right'
                                }
                            }, data: data
                        })
                    });

                    var array = new Array();
                    $.each(zszmlxs, function (index, item) {
                        array.push(item.zmmc);
                    })

                    var option = {
                        color:['#58a0f8','#afc946','#AFEEEE'],
                        title: {
                            text: "不动产证书、证明、证明单打印数量统计",
                            x: 'center',
                            textStyle: {
                                fontSize: 16,
                                fontWeight: "400",
                                fontWeight: 'bold',
                                fontFamily: "Microsoft YaHei",
                                color: "#333"
                            },
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'shadow'
                            }
                        },
                        legend: {
                            data: array,
                            top: "10",
                            right: "50"
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis: {
                            type: 'value'
                        },
                        yAxis: {
                            type: 'category',
                            boundaryGap: [0, 0.2],
                            data: orgNameSet
                        },
                        series: xData
                    };
                    // 动态设置柱状图y轴的长度
                    var setHeight = 40;
                    var autoHeight = 0;
                    if (orgNameSet.length > 2) {
                        autoHeight = orgNameSet.length * setHeight * 3;
                        $('#dysltjChart').height(autoHeight);
                    } else {
                        $('#dysltjChart').height(400);
                    }
                    var myChart = echarts.init(document.getElementById('dysltjChart'), "light");
                    myChart.setOption(option, true);
                    myChart.resize()
                    removeModal();
                    // 设置缓存，用于导出Excel
                    setZsSessionData(orgNameSet, res);
                } else {
                    sessionStorage.setItem("dyltj", null);
                    warnMsg("未查询到证书、证明、证明单打印数据！");
                }
            },
            error: function (e) {
                removeModal();
            }
        });
    }

    // 设置打印量缓存数据
    function setZsSessionData(orgNameSet, res){
        var data = new Array();
        $.each(orgNameSet, function (index, value) {
            $.each(zszmlxs, function (idx, val) {
                data.push({"djjg": value, "zslxmc": val.zmmc, "sl": getCount(res.valueMap[val.zmlx], index)});
            });
        });
        sessionStorage.setItem("dyltj", JSON.stringify(data));
    }

    function getCount(zsValueMap, index){
        var zscount = 0;
        $.each(zsValueMap, function (idx, val) {
            if(index == idx){
                zscount = val;
            }
        });
        return zscount;
    }

    $('#export').click(function () {
        var expdata = sessionStorage.getItem(tabName);

        exportExcel(sessionStorage.getItem(tabName));
    });
    function exportExcel(data){
        // 标题
        var showColsTitle = ["部门","证书类型","数量"];
        // 列内容
        var showColsField = ["djjg","zslxmc","sl"];
        // 列宽
        var showColsWidth = ["50","30","30"];

        var title = '不动产证书、证明、证明单缮证量统计';
        if(tabName == "fzltj"){
            title = '不动产证书、证明、证明单发证量统计';
        } else if(tabName == "dyltj"){
            title = '不动产证书、证明、证明单打印量统计';
        }

        // 设置Excel基本信息
        $("#fileName").val(title);
        $("#sheetName").val(title);
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#data").val(data);
        $("#form").submit();
    }

    // tab页切换事件
    element.on('tab(tabFilter)', function () {
        tabName = this.getAttribute('lay-id');
        tabChangeEvent(tabName,table);
    });

    /**
     * Tab页切换
     */
    function tabChangeEvent(tab, table) {
       if (tab == "szltj") {
           szltj();
       } else if(tab == "fzltj"){
            fzltj();
       } else if(tab == "dyltj"){
           dyltj();
       }
    }

});

$(function () {
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
});

//树结构获取值重置
$('#reset').on('click', function () {
    var zTreeObj = $.fn.zTree.getZTreeObj("select_tree");//ztree对象
    zTreeObj.checkAllNodes(false);//设置全部为false,不选中节点
    OrgTreeCheck=new Map();
    $("input[name=djbmdm]").data("code", "");
    $("input[name=bmmc]").val("");
});

function changeChart() {


}
// 渲染部门
function loadAllOrgs(){
    var deferred = $.Deferred();
    $.ajax({
        url: '/realestate-inquiry-ui/bdczd/org/list',
        type: "GET",
        async: false,
        dataType: "json",
        timeout: 10000,
        success: function (data) {
            if (data) {
                $.each(data, function (index, value) {
                    bmZdList[value.code] = value.name;
                });
                deferred.resolve();
            }
        }
    });
    return deferred;
}

function getOrgName(key){
    var orgNameSet = new Array();
    if(bmZdList.length == 0){
        loadAllOrgs().done(function(){
            $.each(res.keySet, function (index, value) {
                orgNameSet.push(bmZdList[value]);
            });
        });
    }else{
        $.each(key, function (index, value) {
            orgNameSet.push(bmZdList[value]);
        });
    }
    return orgNameSet;
}