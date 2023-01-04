/**
 * @param str
 * @returns {boolean}
 */

layui.use(['element'], function () {

    var element = layui.element;
    element.render();
});

function isNullOrEmpty(str) {
    if (!str || "" == str || "null" == str || undefined == str || "undefined" == str) {
        return true;
    }
    return false;
}

/**
 * 历史遗留问题概况
 * @param
 */
function echarts_1() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart1'))
    var option;
    //测试数据
    var yData1 = [120, 132, 101, 134, 90, 230, 210];
    var yData2 = [862, 1018, 964, 1026, 1679, 1600, 1570];
    var yData3 = [220, 182, 191, 234, 290, 330, 310];
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        title: {
            show: true,
            text: "历史遗留问题概况",
            x: 'left',
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
            textStyle: {
                color: 'rgb(0, 0, 0)'
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
                data: [],
            }
        ],
        yAxis: [
            {
                type: 'value',
            }
        ],
        series: [
            {
                name: '问题总量',
                type: 'bar',
                stack: 'two',
                emphasis: {
                    focus: 'series'
                },
                data: yData1,
            },
            {
                name: '未解决总量',
                stack: 'one',
                type: 'bar',
                emphasis: {
                    focus: 'series'
                },
                data: yData2,
            },
            {
                name: '解决总量',
                stack: 'one',
                type: 'bar',
                data: yData3,
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
    var dataTypes = ["问题总量", "未解决总量", "解决总量"];
    //测试数据
    var data = {
        "宣州区": [
            {
                "num": 124,
                "type": "问题总量",
            },
            {
                "num": 30,
                "type": "未解决总量",
            }
        ],
        "郎溪县": [
            {
                "num": 43,
                "type": "问题总量",
            },
            {
                "num": 57,
                "type": "未解决总量",
            }
        ],
        "泾县": [
            {
                "num": 70,
                "type": "问题总量",
            },
            {
                "num": 57,
                "type": "未解决总量",
            },
            {
                "num": 11,
                "type": "解决总量",
            }
        ],
        "绩溪县": [
            {
                "num": 47,
                "type": "问题总量",
            },
            {
                "num": 17,
                "type": "未解决总量",
            },
            {
                "num": 2,
                "type": "解决总量",
            }
        ],
        "旌德县": [
            {
                "num": 128,
                "type": "问题总量",
            },
            {
                "num": 4,
                "type": "未解决总量",
            },
            {
                "num": 1,
                "type": "解决总量",
            }
        ],
        "宁国市": [
            {
                "num": 128,
                "type": "问题总量",
            },
            {
                "num": 4,
                "type": "未解决总量",
            },
            {
                "num": 1,
                "type": "解决总量",
            }
        ],
        "广德市": [
            {
                "num": 128,
                "type": "问题总量",
            },
            {
                "num": 4,
                "type": "未解决总量",
            },
            {
                "num": 1,
                "type": "解决总量",
            }
        ]
    }
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/jsc/xuancheng/queryLsylwt',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        async: false,
        data: JSON.stringify({
            summaryDimension: "MONTH"
        }),
        success: function (result) {
            //处理图表结构
            data = result;
        }, error: function (e) {
        }
    });


    //处理图表结构
    var xAxis = [];
    var yAxis = [];
    var series = [];
    console.log(data)
    $.each(data, function (k, items) {
        xAxis.push(k);
        $.each(dataTypes, function (k, currentType) {
            if (isNullOrEmpty(yAxis[currentType])) {
                yAxis[currentType] = {
                    name: currentType,
                    type: 'bar',
                    emphasis: {
                        focus: 'series'
                    },
                    data: []
                };
            }
            //直接没的数据
            if(currentType == "问题总量"){
                yAxis[currentType].stack = "two";
            }else {
                yAxis[currentType].stack = "one";
            }
            if (isNullOrEmpty(items)) {
                yAxis[currentType].data.push(0);
            } else {
                //从当前维度的数据中查找当前类型的数据
                var find = false;
                $.each(items, function (index, item) {
                    var dataType = item.lsylwtlx;
                    if (dataType == currentType) {
                        find = true;
                        yAxis[currentType].data.push(item.wtsl);
                    }
                });
                //如果没有找到则认为没有数据
                if (!find) {
                    yAxis[currentType].data.push(0);
                }
            }
        });
    })
    ;
    $.each(dataTypes, function (k, currentType) {
        series.push(yAxis[currentType]);
    });
    option.xAxis[0].data = xAxis;
    option.series = series;
    myChart.setOption(option);
    window.addEventListener('resize', function () {
        myChart.resize();
    });
}

/**
 * 历史遗留问题未解决概况
 * @param
 */
function echarts_2() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart2'));

    var option;
    //默认配置
    option = {
        title: {
            text: "历史遗留问题未解决概况",
            x: 'top',
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
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            padding: [60, 0, 0, 10],
            textStyle: {
                color: 'rgb(0, 0, 0)'
            }
        },
        series: [
            {
                name: '历史遗留问题未解决概况',
                type: 'pie',
                center: ["60%", "50%"],
                radius: '70%',
                data: [
                    {value: 1048, name: '广德市'},
                    {value: 735, name: '宁国市'},
                    {value: 580, name: '旌德县'},
                    {value: 484, name: '绩溪县'},
                    {value: 300, name: '泾县'},
                    {value: 300, name: '郎溪县'},
                    {value: 300, name: '宣州区'}
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
        url: '/realestate-inquiry-ui/rest/v1.0/jsc/xuancheng/queryLsylwtWjjQxzb',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        async: false,
        data: JSON.stringify({
            summaryDimension: "MONTH"
        }),
        success: function (result) {
            //处理图表结构
            var series = [];
            $.each(result, function (k, v) {
                series.push({
                    value: v.wtsl, name: v.qxdm
                });
            })
            option = {
                title: {
                    text: "历史遗留问题未解决概况",
                    x: 'top',
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
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    padding: [60, 0, 0, 0],
                    textStyle: {
                        color: 'rgb(0, 0, 0)'
                    }
                },
                series: [
                    {
                        name: '历史遗留问题未解决概况',
                        type: 'pie',
                        center: ["60%", "50%"],
                        radius: '70%',
                        data: series,
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
        }, error: function (e) {
        }
    });

    myChart.setOption(option);
    myChart.on("click", function (params) {
        //name为区县名称
        echarts_3(params.name)
    });
    window.addEventListener('resize', function () {
        myChart.resize();
    });

}

/**
 * 历史遗留问题类别
 * @param type
 */
function echarts_3(qxmc) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart3'));

    var option;
    if (isNullOrEmpty(qxmc)) {
        qxmc = "广德市"
    }
    var qxdmByQxmc = getQxdmByQxmc(qxmc);
    var qxdmList = [qxdmByQxmc];
    //测试数据
    var xData = ['登记信息缺失', '重证', '地类冲突', '登记程序问题'];
    var yData = [120, 132, 101, 134];
    //实际数据
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/jsc/xuancheng/queryLsylwtQxWtlxzb',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        async: false,
        data: JSON.stringify({
            qxdmList: qxdmList
        }),
        success: function (result) {
            //处理图表结构
            xData = [];
            yData = [];
            $.each(result, function (k, v) {
                //以防万一出现重复的
                if(xData.indexOf(v.lsylwtlx) < 0){
                    xData.push(v.lsylwtlx);
                    yData.push(v.wtsl);
                }
            })
        }, error: function (e) {
        }
    });
    option = {
        title: {
            text: qxmc + "历史遗留问题类别",
            x: 'top',
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

        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },

        legend: {
            data: ["次数"],
            textStyle: {
                color: 'rgb(0, 0, 0)'
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
                data: xData,
                // name:"问题类型",
                // nameLocation:"center",
                nameTextStyle:{
                    fontSize:12,
                    align:'center',
                    verticalAlign:'middle',
                    overflow:'break',
                    width:12,
                },
                axisLabel: {
                    //x轴文字的配置
                    show: true,
                    interval: 0,//使x轴文字显示全
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
            }
        ],
        series: [
            {
                // name: '问题类别',
                type: 'bar',
                emphasis: {
                    focus: 'series'
                },
                data: yData,
            },
        ]
    };
    myChart.setOption(option);
    window.addEventListener('resize', function () {
        myChart.resize();
    });

}

$(document).ready(function () {
    try {
        echarts_1();
    } catch (e) {

    }
    try {
        echarts_2();
    } catch (e) {

    }
    try {
        echarts_3("");
    } catch (e) {

    }
});

function goback() {
    window.location.replace('/realestate-inquiry-ui/standard/jsc/index.html');
}


function getQxdmByQxmc(qxmc){
    if(qxmc == "宣州区"){
        return "341802";
    } else if(qxmc == "郎溪县"){
        return "341821";
    }else if(qxmc == "泾县"){
        return "341823";
    }else if(qxmc == "绩溪县"){
        return "341824";
    }else if(qxmc == "旌德县"){
        return "341825";
    }else if(qxmc == "宁国市"){
        return "341881";
    }else if(qxmc == "广德市"){
        return "341882";
    }
}