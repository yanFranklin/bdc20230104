layui.use(['jquery', 'element'], function () {

    var $ = layui.jquery,
        element = layui.element;

    var yData = [];
    var xData = [];
    var datas = [];;


    var data = [{"mc": "交接单类型1", "sl": "60"}, {"mc": "交接单类型2", "sl": "40"}, {"mc": "交接单类型3", "sl": "20"}, {"mc": "交接单类型4", "sl": "100"}, {"mc": "交接单类型5", "sl": "51"}, {"mc": "交接单类型6", "sl": "2"}, {"mc": "交接单类型7", "sl": "20",}]


    if (data) {
        //此处填写对于接口返回的数据处理
        for (var i = 0; i < data.length; i++) {
            if (data[i].mc) {
                datas.push({"name": data[i].mc, "value": data[i].sl})
            }
        }
    }

    var option = {
        title : {
            show: true,//显示策略，默认值true,可选为：true（显示） | false（隐藏）
            text: '标题',//主标题文本，'
            x:'center',//水平安放位置，默认为'left'，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
            y: 'top',
        },
        legend: {
            show: true,
            right: '0',
            top: "10",
            right: "40",
            orient: 'vertical',
            textStyle: {
                color: 'rgba(0, 0, 0)',
            }
        },
        tooltip: {
            trigger: 'item'
        },
        color: ['#174aeb', '#d93434', '#1292f5', '#f1b72f', '#59e8fa', '#f06e22', '#7c5dc8', '#34d160'],
        series: [{
            type: 'pie',
            /*radius: ['0%', '85%'],
            center: ['40%', '50%'],
            roseType: 'area',*/
            colorBy: 'series',
            itemStyle: {
                borderRadius: 8
            },
            data: datas,
            label: {
                color: 'rgba(0, 0, 0)',
                position: 'outer',
                alignTo: 'labelLine',
                fontSize:16
            },
            labelLine: {
                length: 3,

            }
        }]

        // 使用刚指定的配置项和数据显示图表。
    };
        $('#chartdemo').height($(window).height()-200);
    myChart = echarts.init(document.getElementById("chartdemo"));
    //使用制定的配置项和数据显示图表
    myChart.setOption(option, true);
    myChart.resize();

})