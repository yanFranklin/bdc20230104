layui.use(['jquery', 'element'], function () {

    var $ = layui.jquery,
        element = layui.element;

    var title = "图表标题";
    var legandDatas = ['数据一', '数据二', '数据三', "数量四"];

    var yData = [];
    var xData = [];
    var firstData = [];
    var secondData = [];
    var thirdData = [];
    var fourthData = [];
    //图例是否显示属性
    var isshow = true;

    var data = [{"mc": "交接单类型1", "sl1": "904", "sl2": "", "sl3": "", "sl4": ""}, {
        "mc": "交接单类型2",
        "sl1": "4",
        "sl2": "",
        "sl3": "",
        "sl4": ""
    }, {"mc": "交接单类型3", "sl1": "20", "sl2": "", "sl3": "", "sl4": ""}, {
        "mc": "交接单类型4",
        "sl1": "977",
        "sl2": "",
        "sl3": "",
        "sl4": ""
    }, {"mc": "交接单类型5", "sl1": "51", "sl2": "", "sl3": "", "sl4": ""}, {
        "mc": "交接单类型6",
        "sl1": "2",
        "sl2": "",
        "sl3": "",
        "sl4": ""
    }, {"mc": "交接单类型7", "sl1": "20", "sl2": "", "sl3": "", "sl4": ""}]


    if (data) {
        //此处填写对于接口返回的数据处理
        data.forEach(function (d) {
            if (d.mc) {
                yData.push(d.mc)
                if (d.sl1) {
                    firstData.push(d.sl1)
                }
                if (d.sl2) {
                    secondData.push(d.sl2)
                }
                if (d.sl3) {
                    thirdData.push(d.sl3)
                }
                if (d.sl4) {
                    thirdData.push(d.sl4)
                }
            }
        })
    }

    var first = {
        'name': legandDatas[0], 'type': 'bar', barWidth: 30, barGap: 0,
        label: {
            normal: {
                show: true,
                position: 'right'
            }
        },
        data: firstData
    };
    var second = {
        'name': legandDatas[1], 'type': 'bar', barWidth: 30, barGap: 0,
        label: {
            normal: {
                show: true,
                position: 'right'
            }
        },
        data: secondData
    };
    var third = {
        'name': legandDatas[2], 'type': 'bar', barWidth: 30, barGap: 0, label: {
            normal: {
                show: true,
                position: 'right'
            }
        },
        data: thirdData
    };
    var fourth = {
        'name': legandDatas[3], 'type': 'bar', barWidth: 30, barGap: 0, label: {
            normal: {
                show: true,
                position: 'right'
            }
        },
        data: fourthData
    };
    if (firstData.length>0) {
        xData.push(first);
    }

    if (secondData.length>0) {
        xData.push(second);
    }
    if (thirdData.length>0) {
        xData.push(third);
    }
    if (fourthData.length>0) {
        xData.push(fourth);
    }
    if (xData.length == 1) {
        isshow = false;
    } else {
        isshow = true;
    }
    var option = {
        color: ['#58a0f8', '#afc946', '#AFEEEE', "#E6B851"],
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
            show: isshow,
            data: legandDatas.slice(0, xData.length + 1),
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
            max: function (value) {
                return value.max = yData.length;
            },
        },
        series: xData
    };
    // 动态设置柱状图y轴的长度
    var setHeight = 50;
    var autoHeight = 0;
    if (yData.length > 1) {
        autoHeight = yData.length * setHeight * (xData.length + 1);
        $('#chartdemo').height(autoHeight);
    } else {
        $('#chartdemo').height(400);
    }
    myChart = echarts.init(document.getElementById("chartdemo"));
    //使用制定的配置项和数据显示图表
    myChart.setOption(option, true);
    myChart.resize();

})