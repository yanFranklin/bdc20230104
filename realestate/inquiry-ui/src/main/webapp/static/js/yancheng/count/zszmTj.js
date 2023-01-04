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
var bmZdList;
layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element;
    formSelectsVar = layui.formSelects;


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

    $.ajax({
        url: '/realestate-inquiry-ui/bdczd/bm/list',
        type: "GET",
        async: false,
        dataType: "json",
        timeout: 10000,
        success: function (data) {
            if (data) {
                bmZdList = data;
                $.each(data, function (key) {
                    $('#djbmdm').append('<option value="' + key + '">' + data[key] + '</option>');
                    bmmcSelList.push({
                        name: data[key],
                        value: key
                    })
                });
                formSelectsVar.data('selectBmmc', 'local', {
                    arr: bmmcSelList
                });
                //form.render('select');
            }
        }
    });

    $('#search').click(function () {
        if (tabName == "szltj") {
            szltj();
        } else if(tabName == "fzltj"){
            fzltj();
        } else if(tabName == "dyltj"){
            dyltj();
        }
    });

    szltj();
    // 缮证量统计
    function szltj() {
        var searchData = {};
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
        initChart(searchData, 'zszmChart', '不动产证书、证明、首次登记证缮证量统计');
    }

    // 发证量统计
    function fzltj() {
        var searchData={};
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
        initChart(searchData, 'fzltjChart', '不动产证书、证明、首次登记证发证量统计');
    }

    // 打印量统计
    var zszmlxs = [];
    function dyltj() {
        zszmlxs = [];
        var searchData = {};
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
            zszmlxs = [{"zmlx":"1", "zmmc":"证书"},{"zmlx":"2", "zmmc":"证明"},{"zmlx":"3", "zmmc":"首次登记证"}];
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
                console.log("统计结果：");
                console.log(zszmData);
                var yData = [];
                var xData = [];
                var zsData = [];
                var zmData = [];
                var zmdData = [];
                var zs = {
                    'name': '证书', 'type': 'bar', barWidth: 30, barGap: 0,
                    label: {
                        normal: {
                            show: true,
                            position: 'right'
                        }
                    },
                    data: zsData
                };
                var zm = {
                    'name': '证明', 'type': 'bar', barWidth: 30, barGap: 0,
                    label: {
                        normal: {
                            show: true,
                            position: 'right'
                        }
                    },
                    data: zmData
                };
                var zmd = {
                    'name': '首次登记证', 'type': 'bar', barWidth: 30, barGap: 0, label: {
                        normal: {
                            show: true,
                            position: 'right'
                        }
                    },
                    data: zmdData
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
                        d['zslxmc'] = "首次登记证"
                    }
                    // d['zslxmc']=convertZdDmToMc('zslx',d.zslx,'zdList');
                });
                // 根据yData长度调整图表高度
                changechartHeight(yData);

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
                        data: ['证书', '证明', '首次登记证'],
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
                        type: 'value',
                        boundaryGap: [0, 0.2]
                    },
                    yAxis: {
                        type: 'category',
                        data: yData,
                        max: function(value) {
                            return value.max = yData.length;
                        },
                    },
                    series: xData
                };
                // 动态设置柱状图y轴的长度
                var setHeight = 50;
                var autoHeight = 0;
                if (yData.length > 1) {
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
                    var orgNameSet = new Array();
                    $.each(res.keySet, function (index, value) {
                        orgNameSet.push(bmZdList[value]);
                    });

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
                            'name': value.zmmc, 'type': 'bar', stack: '总量', barWidth : 40,
                            label: {
                                normal: {
                                    show: true,
                                    position: 'insideRight'
                                }
                            }, data: data
                        })
                    });

                    var array = new Array();
                    $.each(zszmlxs, function (index, item) {
                        array.push(item.zmmc);
                    })

                    var option = {
                        title: {
                            text: "不动产证书、证明、首次登记证打印数量统计",
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'shadow'
                            }
                        },
                        legend: {
                            data: array
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
                            data: orgNameSet,
                            // max: 300,
                        },
                        series: xData
                    };

                    var myChart = echarts.init(document.getElementById('dysltjChart'), "light");
                    myChart.setOption(option, true);
                    removeModal();
                    // 设置缓存，用于导出Excel
                    setZsSessionData(orgNameSet, res);
                } else {
                    sessionStorage.setItem("dyltj", null);
                    warnMsg("未查询到证书、证明、首次登记证打印数据！");
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

        var title = '不动产证书、证明、首次登记证缮证量统计';
        if(tabName == "fzltj"){
            title = '不动产证书、证明、首次登记证发证量统计';
        } else if(tabName == "dyltj"){
            title = '不动产证书、证明、首次登记证打印量统计';
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

    // 根据数据条数调整图表高度
    function changechartHeight(data) {
        var height = data.length * 37 + 25;

        if(height > 400){
            $('.layui-show .chart-item').css({
                "height": height
            });
        }else {
            $('.layui-show .chart-item').css({
                "height": 400
            });
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