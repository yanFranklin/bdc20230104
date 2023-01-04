/**
 *    YEAR = "1";
 *    MONTH = "2";
 *    WEEK = "3";
 *    DAY = "4";
 */
var YEAR = 1;
var MONTH = 2;
var WEEK = 3;
var DAY = 4;
var TENYEAR = 5;

/**
 * @param str
 * @returns {boolean}
 */
function isNullOrEmpty(str) {
    if (!str || "" == str || "null" == str || undefined == str || "undefined" == str) {
        return true;
    }
    return false;
}

/**
 * 林权登记数量图表
 * @param type
 */
function echarts_1(type) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart1'));

    var option;
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            textStyle: {
                color: 'rgba(255, 255, 255, 1)'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: ['2022-01', '2022-02', '2022-03', '2022-04', '2022-05', '2022-06', '2022-07'],
                axisLine: {
                    onZero: true,
                    lineStyle: {
                        color: 'rgba(255, 255, 255, 1)'
                    }
                },
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLine: {
                    lineStyle: {
                        color: 'rgba(255, 255, 255, 1)'
                    }
                },
                splitLine: {
                    show: true,
                    lineStyle: {}
                }
            }
        ],
        series: [
            {
                name: '抵押',
                type: 'bar',
                emphasis: {
                    focus: 'series'
                },
                data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
                name: '查封',
                type: 'bar',
                emphasis: {
                    focus: 'series'
                },
                data: [220, 182, 191, 234, 290, 330, 310]
            },
            {
                name: '林权',
                type: 'bar',
                data: [862, 1018, 964, 1026, 1679, 1600, 1570],
                emphasis: {
                    focus: 'series'
                },
                markLine: {
                    lineStyle: {
                        type: 'dashed'
                    },
                    data: [[{type: 'min'}, {type: 'max'}]]
                }
            },
        ]
    };
    //获取数据
   
    var summaryDimension = 'MONTH';
    if (type == WEEK) {
        summaryDimension = 'DAY';
    }
    if (type == YEAR) {
        summaryDimension = 'MONTH';
    }
    if (type == TENYEAR) {
        summaryDimension = 'YEAR';
    }
    var dataTypes = ["查封", "林权", "抵押"];
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/jsc/xuancheng/queryDjsl',
        type: 'POST',
        async: false,
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            timeFrame: type,
            summaryDimension: summaryDimension
        }),
        success: function (data) {
            //处理图表结构
            var xAxis = [];
            var yAxis = [];
            var series = [];
            $.each(data, function (k, items) {
               
                xAxis.push(k);
                $.each(dataTypes, function (k, currentDataType) {
                   
                    if (isNullOrEmpty(yAxis[currentDataType])) {
                        yAxis[currentDataType] = {
                            name: currentDataType,
                            type: 'bar',
                            emphasis: {
                                focus: 'series'
                            },
                            data: []
                        };
                    }
                    //直接没的数据
                    if (isNullOrEmpty(items)) {
                        yAxis[currentDataType].data.push(0);
                    } else {
                        //从当前维度的数据中查找当前类型的数据
                        var find = false;
                        $.each(items, function (index, item) {
                            var dataType = item.type;
                            if (dataType == currentDataType) {
                                find = true;
                                yAxis[currentDataType].data.push(item.num);
                            }
                        });
                        //如果没有找到则认为没有数据
                        if (!find) {
                            yAxis[currentDataType].data.push(0);
                        }
                    }
                });
            })
           
            $.each(dataTypes, function (k, currentDataType) {
                series.push(yAxis[currentDataType]);
            });
            option.xAxis[0].data = xAxis;
            option.series = series;
        }, error: function (e) {
        }
    });
   
    myChart.setOption(option);
    window.addEventListener('resize', function () {
        myChart.resize();
    });
}

/**
 * 登记趋势图表
 */
function echarts_2() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart2'));
    var option;
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/jsc/xuancheng/queryTrend',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        async: false,
        data: JSON.stringify({
            // timeFrame: YEAR,
            summaryDimension: "MONTH"
        }),
        success: function (data) {
            //处理图表结构
            option = {
                textStyle: {
                    fontFamily: "sans-serif",
                    fontStyle: 'normal',
                    fontWeight: "normal"
                },
                tooltip: {
                    trigger: 'axis', axisPointer: {
                        type: 'shadow'
                    },
                    formatter: function (params) {
                        let tar;
                        let total = null;
                        if (params[1].value !== '-') {
                            tar = params[1];
                        } else {
                            tar = params[2];
                            total = params[0];
                        }
                        if (total === null) {
                            return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value + '<br/>';
                        } else {
                            return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value + '<br/>' +
                                total.seriesName + ' : ' + total.value + '<br/>'
                                ;
                        }

                    }
                }, legend: {
                    data: ['增加', '减少'], textStyle: {
                        color: 'rgba(255, 255, 255, 1)'
                    }
                }, grid: {
                    left: '3%', right: '4%', bottom: '3%', containLabel: true
                }, xAxis: {
                    type: 'category', axisLine: {
                        lineStyle: {
                            color: 'rgba(255, 255, 255, 1)'
                        }
                    }, data: data.title
                }, yAxis: {
                    type: 'value', axisLine: {
                        lineStyle: {
                            color: 'rgba(255, 255, 255, 1)'
                        }
                    }
                }, series: [
                    {
                        name: '总计',
                        type: 'bar',
                        stack: 'Total',
                        itemStyle: {
                            borderColor: 'transparent',
                            color: 'transparent'
                        },
                        emphasis: {
                            itemStyle: {
                                borderColor: 'transparent',
                                color: 'transparent'
                            }
                        },
                        data: data.total
                    }, {
                        name: '增加',
                        type: 'bar',
                        stack: 'Total',
                        itemStyle: {

                        },
                        emphasis: {
                            itemStyle: {

                            }
                        },
                        label: {
                            show: true,
                            position: 'inside',
                            fontStyle: 'normal',
                            fontFamily: "sans-serif",
                            fontWeight: "normal",
                            borderType: 'dashed',
                            color: "#fff",
                        },
                        data: data.add
                    }, {
                        name: '减少',
                        type: 'bar',
                        stack: 'Total',
                        itemStyle: {

                        },
                        emphasis: {

                        },
                        label: {
                            show: true,
                            position: 'insideBottom',
                            fontStyle: 'normal',
                            fontFamily: "sans-serif",
                            fontWeight: "normal",
                            borderType: 'dashed',
                            color: "#fff",
                        },
                        data: data.de
                    }]
            };
        }, error: function (e) {
        }
    });
    myChart.setOption(option);
    window.addEventListener('resize', function () {
        myChart.resize();
    });
}

/**
 * 林权权利情况图表
 */
function echarts_3(type) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart3'));
    var option;
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                // Use axis to trigger tooltip
                type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
            },
        },
        legend: {
            textStyle: {
                color: 'rgba(255, 255, 255, 1)'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            axisLine: {
                lineStyle: {
                    color: 'rgba(255, 255, 255, 1)'
                }
            },
        },
        yAxis: {
            type: 'category',
            data: ['宣州区', '郎溪县', '泾县', '绩溪县', '旌德县', '宁国市', '广德市'],
            axisLine: {
                onZero: true,
                lineStyle: {
                    color: 'rgba(255, 255, 255, 1)'
                }
            },
        },
        series: [
            {
                name: '使用权',
                type: 'bar',
                stack: 'total',
                label: {
                    show: true
                },
                emphasis: {
                    focus: 'series'
                },
                data: [320, 302, 301, 334, 390, 330, 320]
            },
            {
                name: '承包经营权',
                type: 'bar',
                stack: 'total',
                label: {
                    show: true
                },
                emphasis: {
                    focus: 'series'
                },
                data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
                name: '经营权',
                type: 'bar',
                stack: 'total',
                label: {
                    show: true
                },
                emphasis: {
                    focus: 'series'
                },
                data: [220, 182, 191, 234, 290, 330, 310]
            },
        ]
    };
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/jsc/xuancheng/queryQl',
        type: 'POST',
        async: false,
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            timeFrame: type,
        }),
        success: function (data) {
            //处理图表结构
            var xAxis = [];
            var yAxis = [];
            var series = [];
            var allQlmc = [];
            var limitQx = ['宣州区', '郎溪县', '泾县', '绩溪县', '旌德县', '宁国市', '广德市'];
           
            $.each(data, function (k, v) {
                $.each(v, function (index, item) {
                    allQlmc.push(item.type);
                })
            });
            allQlmc = Array.from(new Set(allQlmc));
           
            $.each(data, function (k, items) {
                if(limitQx.indexOf(k) < 0){
                    //return;
                }
                //区县名称
                xAxis.push(k);
                $.each(allQlmc, function (k, currentQl) {
                   
                    if (isNullOrEmpty(yAxis[currentQl])) {
                        yAxis[currentQl] = {
                            name: currentQl,
                            type: 'bar',
                            stack: 'total',
                            label: {
                                show: true
                            },
                            emphasis: {
                                focus: 'series'
                            },
                            data: []
                        };
                    }
                    //直接没的数据
                    if (isNullOrEmpty(items)) {
                        yAxis[currentQl].data.push(0);
                    } else {
                        //从当前维度的数据中查找当前类型的数据
                        var find = false;
                        $.each(items, function (index, item) {
                            var dataType = item.type;
                            if (dataType == currentQl) {
                                find = true;
                                yAxis[currentQl].data.push(item.num);
                            }
                        });
                        //如果没有找到则认为没有数据
                        if (!find) {
                            yAxis[currentQl].data.push(0);
                        }
                    }
                });
            });
            $.each(allQlmc, function (k, currentDataType) {
                series.push(yAxis[currentDataType]);
            });
            option.yAxis.data = xAxis;
            option.series = series;
        }, error: function (e) {
        }
    });
   
    myChart.setOption(option);
    window.addEventListener('resize', function () {
        myChart.resize();
    });
}

/**
 * 林权交易情况饼图
 */
function echarts_4() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart4'));

    var option;
    //默认配置
    option = {
        title: {
            // text: 'Referer of a Website',
            // subtext: 'Fake Data',
            left: 'center',
            textStyle: {
                color: 'rgba(255, 255, 255, 1)'
            }
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            textStyle: {
                color: 'rgba(255, 255, 255, 1)'
            }
        },
        series: [
            {
                name: '林权交易情况',
                type: 'pie',
                radius: '50%',
                data: [
                    { value: 1048, name: '广德市' },
                    { value: 735, name: '宁国市' },
                    { value: 580, name: '旌德县' },
                    { value: 484, name: '绩溪县' },
                    { value: 300, name: '泾县' },
                    { value: 300, name: '郎溪县' },
                    { value: 300, name: '宣州区' }
                ],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    //实际读取配置
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/jsc/xuancheng/queryTransaction',
        type: 'POST',
        async: false,
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            // timeFrame: 1,
            // djlx:"200"
        }),
        success: function (data) {
            //处理图表结构
            var series = [];
            $.each(data, function (k, v) {
                series.push({
                    value: v.jysl, name: v.qxdm
                });
            })
            option = {
                title: {
                    left: 'center', textStyle: {
                        color: 'rgba(255, 255, 255, 1)'
                    }
                }, tooltip: {
                    trigger: 'item'
                }, legend: {
                    orient: 'vertical', left: 'left', textStyle: {
                        color: 'rgba(255, 255, 255, 1)'
                    }
                }, series: [{
                    name: '林权交易情况', type: 'pie', radius: '50%',
                    data: series,
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },
                    label: {
                        show: true,
                        position: 'top',
                        fontStyle: 'normal',
                        fontFamily: "sans-serif",
                        fontWeight: "normal",
                        borderType: 'dashed',
                        color: "#fff",
                    },
                }]
            };
        }, error: function (e) {
        }
    });

    myChart.setOption(option);
    window.addEventListener('resize', function () {
        myChart.resize();
    });
}

/**
 * 地图
 */
function echarts_6() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('map'));

    var option;

    var data = [];

    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/jsc/xuancheng/queryQxdjsl',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        async: false,
        data: JSON.stringify({}), success: function (d) {
            //处理图表结构
            $.each(d, function (k, v) {
                data.push({
                    value: [v.jysl,v.mj,v.zds,v.ldmj],
                    name: k
                });
            })
        }, error: function (e) {
        }
    });
    //测试数据
    for (var i = 1; i < data.length; i++) {
        //data[i].value = [1, 2, 3, 4]
    }
    //地理位置
    var geoCoordMap = {
        宣州区: [118.758412, 30.946003],
        郎溪县: [119.185024, 31.127834],
        泾县: [118.412397, 30.685975],
        绩溪县: [118.594705, 30.065267],
        旌德县: [118.543081, 30.288057],
        宁国市: [118.983407, 30.626529],
        广德市: [119.417521, 30.893116],
    };

    var convertData = function convertData(data) {
        var res = [];
        for (var i = 0; i < data.length; i++) {
            var geoCoord = geoCoordMap[data[i].name];
            if (geoCoord) {
                res.push({
                    name: data[i].name, value: geoCoord, itemStyle: {
                        color: 'white'
                    }
                });
            }
        }
        return res;
    };

    myChart.showLoading();
    $.get('/realestate-inquiry-ui/static/js/jsc/shucheng.json', function (geoJson) {
        myChart.hideLoading();
        echarts.registerMap('xuancheng', geoJson);
        myChart.setOption(option = {
            visualMap: {
                left: 'right',
                right: '0',
                min: 0,
                max: 1000,
                inRange: {
                    color: [ '#75d3f7', '#6baaf1', '#4587dd', '#3275d2', '#2165c9', '#155bc3']
                },
                text: ['登记数量', ''],
                calculable: true,
                textStyle: {
                    color: 'rgba(255, 255, 255, 1)'
                },
                orient: 'horizontal'
                // show:false
            },
            tooltip: {
                trigger: 'item',
                formatter: function formatter(params) {
                    var res = params.name + '<br/>';

                    var myseries = params.data.value[0] + '<br/>';
                    var djarea = '登记面积:' + params.data.value[1] + '<br/>';
                    var zdnum = '宗地数:' + params.data.value[2] + '<br/>';
                    var ldarea = '林地总面积:' + params.data.value[3] + '<br/>';
                    res += '登记数量:' + myseries + djarea + zdnum + ldarea;
                    return res;
                },
                textStyle: {
                    fontFamily: "Microsoft YaHei"
                }
            },
            geo: {
                map: 'xuancheng',
                roam: false, // 不开启缩放和平移
                zoom: 1, // 视角缩放比例
                top: 50,
                aspectScale: 0.8,
                label: {
                    normal: {
                        show: false,
                        fontSize: '10',
                        color: 'rgba(0,0,0,0.7)'
                    }
                },
                itemStyle: {
                    normal: {
                        areaColor: '#2282d6',
                        borderColor: '#2282d6',
                        borderWidth: 1,
                        borderType: 'solid',
                        opacity: 0.8,
                        shadowBlur: 10,
                        shadowColor: '#080D15',
                        shadowOffsetX: 5,
                        shadowOffsetY: 5
                    },
                    emphasis: {
                        areaColor: {
                            type: 'linear',
                            x: 0,
                            y: 0,
                            x2: 0,
                            y2: 1,
                            colorStops: [{
                                offset: 0,
                                color: '#fff' // 0% 处的颜色
                            }, {
                                offset: 1,
                                color: '#fff' // 100% 处的颜色
                            }],
                            globalCoord: false // 缺省为 false
                        }
                    }
                }
            },
            series: [{
                type: 'map',
                geoIndex: 1,
                aspectScale: 0.8,
                map: 'xuancheng',
                zoom: 1,
                top: 42,
                roam: false,
                nameProperty: 'name',
                colorBy: 'series',
                data: data,
                itemStyle: {
                    borderColor: '#3dd4ff',
                    borderWidth: '2',
                    shadowBlur: 20,
                    shadowColor: '#95d3fd',
                    shadowOffsetX: 0,
                    shadowOffsetY: 0,
                    emphasis: {
                        areaColor: {
                            type: 'linear',
                            x: 0,
                            y: 0,
                            x2: 0,
                            y2: 1,
                            colorStops: [{
                                offset: 0,
                                color: '#2332c5' // 0% 处的颜色
                            }, {
                                offset: 1,
                                color: '#2332c5' // 100% 处的颜色
                            }],
                            globalCoord: false // 缺省为 false
                        },
                        label: {
                            show: false
                        }
                    }
                }
            }, {
                name: 'city',
                type: 'effectScatter',
                rippleEffect: {
                    // 涟漪特效相关配置。
                    scale: 4 // 控制涟漪大小
                },
                colorBy: 'series',
                coordinateSystem: 'geo',
                data: convertData(data),
                label: {
                    formatter: '{b}',
                    position: 'right',
                    show: true,
                    color: '#ffffff'
                },
                itemStyle: {
                    color: '#ffffff',
                    shadowBlur: 10,
                    shadowColor: '#333'
                },
                tooltip: {
                    formatter: '{b0}'
                }
            }]
        });
    });

    // 使用刚指定的配置项和数据显示图表。
    // myChart.setOption(option)
    window.addEventListener('resize', function () {
        myChart.resize();
    });
}

/**
 * 概况
 */
function echarts_7(type) {
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/jsc/xuancheng/querySummary',
        type: 'POST',
        async: false,
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            timeFrame: type,
        }),
        success: function (data) {
           
            $("#ybl").html(data[0]);
            $("#blz").html(data[1]);
            $("#wtj").html(data[2]);
        }, error: function (e) {
        }
    });
}

/**
 * 总计
 */
function echarts_8() {
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/jsc/xuancheng/queryZmj',
        type: 'POST',
        async: false,
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            timeFrame: 1,
        }),
        success: function (data) {
            $("#totalnum").html(data[0] / 10000);
            var totalmj = data[1] / 10000 ;
            $("#totalmj").html(948.65);
            //    联调处 林地总面积
            var ldarea = data[2] / 10000 ;
            $("#ldarea").html(1130.74);
            //    联调处 宗地数量
            $("#zdnum").html(93.7682);
        },
        error: function (e) {
        }
    });
}

/**
 * 分享数据
 */
function shareDataSw() {
    $.ajax({
        url: '/realestate-exchange/bdcDsfLog/getBdcDsfLogPagesJson?type=logines',
        type: 'POST',
        async: true,
        // dataType: 'json',
        // contentType: "application/json;charset=UTF-8",
        data: {
            loadTotal: false,
            page: 1,
            size: 1,
            gxbmbz: "SW",
        },
        success: function (data) {
            $("#swgxcs").html(0);
        },
        error: function (e) {
        }
    });
}

function shareDataSjpt() {
    $.ajax({
        url: '/realestate-exchange/bdcDsfLog/getBdcDsfLogPagesJson?type=logines',
        type: 'POST',
        async: true,
        // dataType: 'json',
        // contentType: "application/json;charset=UTF-8",
        data: {
            loadTotal: false,
            page: 1,
            size: 1,
            gxbmbz: "SJPT",
        },
        success: function (data) {
            $("#sjptcs").html(0);
        },
        error: function (e) {
        }
    });
}

function shareDataHlw() {
    $.ajax({
        url: '/realestate-exchange/bdcDsfLog/getBdcDsfLogPagesJson?type=logines',
        type: 'POST',
        async: true,
        // dataType: 'json',
        // contentType: "application/json;charset=UTF-8",
        data: {
            loadTotal: false,
            page: 1,
            size: 1,
            gxbmbz: "WWSQ",
        },
        success: function (data) {
            $("#wwsqcs").html(0);
        },
        error: function (e) {
        }
    });
}

function shareDataSdq() {
    $.ajax({
        url: '/realestate-exchange/bdcDsfLog/getBdcDsfLogPagesJson?type=logines',
        type: 'POST',
        async: true,
        // dataType: 'json',
        // contentType: "application/json;charset=UTF-8",
        data: {
            loadTotal: false,
            page: 1,
            size: 1,
            gxbmbz: "SDQ",
        },
        success: function (data) {
            $("#sdqcs").html(0);
        },
        error: function (e) {
        }
    });
}

function allShareData() {
    $("#allgxcs").html(
        Number($("#sdqcs").html()) +
        Number($("#wwsqcs").html()) +
        Number($("#sjptcs").html()) +
        Number($("#swgxcs").html())
    )
}

$(document).ready(function () {
    try {
        echarts_1(TENYEAR);
    }catch (e){

    }
    try {
        echarts_7(YEAR);
    }catch (e){

    }
    try {
        echarts_8();
    }catch (e){

    }
    try {
        echarts_4();
    }catch (e){

    }
    try {
        echarts_3(YEAR);
    }catch (e){

    }
    try {
        echarts_2();
    }catch (e){

    }
    try {
        echarts_6();
    }catch (e){

    }
    try {
        shareDataSw();
        shareDataSjpt();
        shareDataHlw();
        shareDataSdq();
        allShareData();
    }catch (e){
    }

    new Slideicon($("#timelist"), {
        index: 0, cover: $("#timebg"), callback: function (data) {
            var attr = $("#timelist").find("li[class='active']").attr("value");
            echarts_3(attr);
            console.log(data);
        }
    });
    new Slideicon($("#gklist"), {
        index: 0, cover: $("#gkbg"), callback: function (data) {
            var attr = $("#gklist").find("li[class='active']").attr("value");
            echarts_7(attr);
        }
    });
    new Slideicon($("#hourlist"), {
        index: 0, cover: $("#thirdbg"), callback: function (data) {
            var attr = $("#hourlist").find("li[class='active']").attr("value");
            echarts_1(attr);
            console.log(attr)
        }
    });
    $('.demo').fSelect({
        placeholder: '选择区县', numDisplayed: 3, overflowText: '{n} 已选', searchText: '搜索', showSearch: false
    });
    $('.demoSelect').fSelect({
        placeholder: '选择区县', numDisplayed: 3, overflowText: '{n} 已选', searchText: '搜索', showSearch: false
    });
})

function goBar(){
    window.location.replace('/realestate-inquiry-ui/standard/jsc/lsylwt.html');
}