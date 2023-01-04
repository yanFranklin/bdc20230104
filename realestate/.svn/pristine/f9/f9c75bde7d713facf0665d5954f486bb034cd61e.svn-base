'use strict';
var form;
var $;
var layer;
var laydate;
var kssj = "";
var jssj = "";
var djlx = "";
$(function () {
    layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer'], function () {
        form = layui.form;
        $ = layui.jquery;
        layer = layui.layer;
        laydate = layui.laydate;

    });
    laydate.render({
        elem: '#startTime',
        type: 'date',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#endTime').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/image/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#endTime',
        type: 'date',
        format: 'yyyy-MM-dd',
        value: new Date((new Date().getTime())),
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#startTime').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/image/error-small.png" alt="">结束时间不能小于开始时间');
            }
            // //设置默认值遗留的清空问题
            // this.value = data;
            // this.elem.val(data)

        }
    });
    getjrdjlx();
    new Slideicon($("#timelist"), {
        index: 0, cover: $("#timebg"), callback: function (data) {
            var sjfw = $("#timelist").find("li[class='active']").attr("value");
            //console.log(sjfw);
            getReturnData("/rest/v1.0/bdcdp/getFzsltj", JSON.stringify({
                sjfw: sjfw,
                kssj: kssj,
                jssj: jssj
            }), "POST", function (data) {
                var xAxisData = data.xAxisData;
                var zs = data.zsslList;
                var zm = data.zmslList;
                var pj = data.fzlzb;
                var fzsl = data.fzzs;
                $("#fzzs").html(fzsl);
                echarts_2(xAxisData, zs, zm, pj);
            }, function (xhr) {
                delAjaxErrorMsg(xhr);
            }, true);
        }
    });
    getFzsltj();
    getTyrzTj();
    getGxcsTj();
    getZztj();
    getsbqk();
    getgzjcgk();
    getBqdfqk();
    setSelect();
    setBqdfSelect();
    setInterval(reloadDate, 30 * 60 * 1000);

});


//获取今日登记类型
function getjrdjlx() {
    getReturnData("/rest/v1.0/bdcdp/getjrdjlx", JSON.stringify({kssj: kssj, jssj: jssj}), "POST", function (data) {
        var datas = [];
        for (var i = 0; i < data.length; i++) {
            if (isNotBlank(data[i].djlxmc)) {
                datas.push({"name": data[i].djlxmc, "value": data[i].sl})
            }
        }
        //隐藏穿透后的的图表
        $("#echart1").show();
        $("#echart1_ct").hide();
        echarts_1(datas);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}

//获取登记类型穿透数据
function getctdjlx(value) {
    getReturnData("/rest/v1.0/bdcdp/getLctjByDjlx", {kssj: kssj, jssj: jssj, djlx: value}, "get", function (data) {
        var datas = [];
        for (var i = 0; i < data.length; i++) {
            if (isNotBlank(data[i].djlxmc)) {
                datas.push({"name": data[i].djlxmc, "value": data[i].sl})
            }
        }
        //隐藏穿透前的图表
        $("#echart1").hide();
        $("#echart1_ct").show();
        echarts_1_ct(datas);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}


//获取发证数量统计
function getFzsltj() {
    getReturnData("/rest/v1.0/bdcdp/getFzsltj", JSON.stringify({
        sjfw: $("#timelist").find("li[class='active']").attr("value"),
        kssj: kssj,
        jssj: jssj
    }), "POST", function (data) {
        var xAxisData = data.xAxisData;
        var zs = data.zsslList;
        var zm = data.zmslList;
        var pj = data.fzlzb;
        var fzsl = data.fzzs;
        $("#fzzs").html(fzsl);
        echarts_2(xAxisData, zs, zm, pj);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}

//获取抵押融资统计
function getTyrzTj() {
    var thisyaer = new Date().getFullYear();
    getReturnData("/rest/v1.0/bdcdp/getTyrzTj", JSON.stringify({
        nf: thisyaer,
        kssj: kssj,
        jssj: jssj
    }), "POST", function (data) {
        //1 土地 ，2，房屋
        for (var i = 0; i < data.dyzje.length; i++) {
            if (data.dyzje[i].dybdclx == 1) {
                $("#tddyzje").html(data.dyzje[i].je);
            }
            if (data.dyzje[i].dybdclx == 2) {
                $("#zzdyzje").html(data.dyzje[i].je);
            }
        }

        var xAxisData = [];
        var dytc = [];

        var dyje = [];

        for (var i = 0; i < data.zztsdyjeList.length; i++) {
            xAxisData.push(data.zztsdyjeList[i].YF);
            dytc.push(data.zztsdyjeList[i].DYTS)
            dyje.push(data.zztsdyjeList[i].DYJE)

        }
        var maxDytc = RoundedMax(dytc);
        var maxDyje = RoundedMax(dyje);
        var intervalDytc = maxDytc/5;
        var intervalDyje = maxDyje/5;
        echarts_3(xAxisData, dytc, dyje,maxDytc,maxDyje,intervalDytc,intervalDyje);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}

//获取共享次数统计
function getGxcsTj() {
    getReturnData("/rest/v1.0/bdcdp/getGxcsTj", JSON.stringify({kssj: kssj, jssj: jssj}), "POST", function (data) {
        $("#gxzcs").html(data.gxzcs);
        var gxxq = data.gxxq;
        $(".m3-item .m3-item-div").each(function (index, element) {
            if (isNotBlank(gxxq[index])) {
                $(element).html("");
                $(element).append("<div>" + gxxq[index].ZS + "次</div><div>" + gxxq[index].MC + "总次数</div>")
            } else {
                $(element).parent().css("display", "none")
            }
        });
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}

//获取住宅统计
function getZztj() {
    getReturnData("/rest/v1.0/bdcdp/getZztj", JSON.stringify({kssj: kssj, jssj: jssj}), "POST", function (data) {
        //console.log(data);
        var zzfbqkList = data.zzfbqkList;
        var datas = [];
        var geoCoordMap = {
            天宁区: [120.11, 31.84],
            新北区: [119.9, 31.92],
            钟楼区: [119.85, 31.81],
            金坛区: [119.5, 31.74],
            武进区: [119.91, 31.6],
            溧阳市: [119.39, 31.46]
        };
        //console.log(geoCoordMap);
        for (var key in geoCoordMap) {
            var obj = {};
            obj.name = key;
            for (var i = 0; i < zzfbqkList.length; i++) {
                if (zzfbqkList[i].qxmc == key) {
                    var value = [];
                    value.push(zzfbqkList[i].mj);
                    value.push(zzfbqkList[i].ts);
                    obj.value = value;
                }
            }
            datas.push(obj);
        }
        $("#zzzts").html(data.zzzts);
        $("#zzzmj").html(data.zzzmj);
        $("#zzjtmj").html(data.zzjtmj);
        $("#dyzje").html(data.dyzje);
        //隐藏穿透后的的图表
        $("#map_ct").hide();
        $("#map").show();
        echarts_6(datas);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}

//获取住宅统计穿透
function getZztjct(qxmc) {
    getReturnData("/rest/v1.0/bdcdp/getZztj", JSON.stringify({
        kssj: kssj,
        jssj: jssj,
        qxmc: qxmc
    }), "POST", function (data) {
        //console.log(data);
        var zzfbqkList = data.zzfbqkList;
        var datas = [];
        var geoCoordMap = {
            天宁区: [120.11, 31.84],
            新北区: [119.9, 31.92],
            钟楼区: [119.85, 31.81],
            金坛区: [119.5, 31.74],
            武进区: [119.91, 31.6],
            溧阳市: [119.39, 31.46]
        };
        for (var key in geoCoordMap) {
            var obj = {};
            obj.name = key;
            for (var i = 0; i < zzfbqkList.length; i++) {
                if (zzfbqkList[i].qxmc == qxmc) {
                    var value = [];
                    value.push(zzfbqkList[i].mj);
                    value.push(zzfbqkList[i].ts);
                    obj.value = value;
                }
            }
            datas.push(obj);
        }
        $("#zzzts").html(data.zzzts);
        $("#zzzmj").html(data.zzzmj);
        $("#zzjtmj").html(data.zzjtmj);
        $("#dyzje").html(data.dyzje);
        //显示穿透后的的图表
        $("#map_ct").show();
        $("#map").hide();
        echarts_6_ct(datas, qxmc);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}

//获取上报情况
function getsbqk() {

    getReturnData("/rest/v1.0/bdcdp/getsbqk", JSON.stringify({kssj: kssj, jssj: jssj}), "POST", function (data) {
        $("#jr").html(data.jrl || 0);
        $("#jrsb").html(data.jrsb || 0);
        $("#sb").html(data.sbl || 0);
        $("#sbsb").html(data.sbsbl || 0);
        $("#yxy").html(data.yxysl || 0);
        $("#wxy").html(data.wxysl || 0);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}

//获取规则检查情况
function getgzjcgk() {
    getReturnData("/rest/v1.0/bdcdp/getgzjcgk", JSON.stringify({kssj: kssj, jssj: jssj}), "POST", function (data) {
        $("#jczs").html(data.jczs || 0);
        $("#zmcws").html(data.zmcws || 0);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}

//获取本期得分情况
function getBqdfqk() {
    var thisMonth = new Date().getMonth() + 1;
    getReturnData("/rest/v1.0/bdcdp/getBqdfqk", JSON.stringify({
        yf: thisMonth,
        kssj: kssj,
        jssj: jssj
    }), "POST", function (data) {
        //console.log(data);
        var xAxisData = data.xAxisData;
        var jrl = data.jrl;
        var cgl = data.cgl;
        var sbl = data.sbl;
        var jrzl = data.jrzl;
        var jrzcgl = data.jrzcgl;
        var qsmdfqk = data.qsmdfqk;
        if (isNotBlank(qsmdfqk)) {
            for (var i = 0; i < qsmdfqk.length; i++) {
                $("#jrzlmc" + i).html(qsmdfqk[i].qxmc || "");
                $("#jrzldf" + i).html(qsmdfqk[i].df || "");
            }
        }
        echarts_4(xAxisData, jrl, cgl, sbl);
        $("#jrzl").html(jrzl || 0);
        $("#jrzcgl").html(jrzcgl || 0);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}

function echarts_1(datas) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart1'));

    var option = {
        legend: {
            show: false,
            right: '0',
            top: 'middle',
            orient: 'vertical',
            textStyle: {
                color: 'rgba(255, 255, 255, 1)'
            }
        },
        tooltip: {
            trigger: 'item'
        },
        color: ['#174aeb', '#d93434', '#1292f5', '#f1b72f', '#59e8fa', '#f06e22', '#7c5dc8', '#34d160'],
        series: [{
            type: 'pie',
            radius: '75%',
            center: ['40%', '50%'],
            /*radius: ['0%', '85%'],
            center: ['40%', '50%'],
            roseType: 'area',*/
            colorBy: 'series',
            itemStyle: {
                borderRadius: 8
            },
            data: datas,
            label: {
                color: 'rgba(255, 255, 255, 1)',
                position: 'outer',
                alignTo: 'labelLine'
            },
            labelLine: {
                length: 3,

            }
        }]

        // 使用刚指定的配置项和数据显示图表。
    };
    myChart.setOption(option);
    //点击事件实现穿透效果
    myChart.on("click", function (params) {
        getctdjlx(params.name)
    });
    setTimeout(function () {
        myChart.resize();
    }, 200)
    window.addEventListener('resize', function () {
        myChart.resize();
    });
}

//穿透后的登记类型图表
function echarts_1_ct(datas) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart1_ct'));
    var option = {
        legend: {
            show: false,
        },
        clickable: false,
        tooltip: {
            trigger: 'item',
            //设置hover位置
            position: function (point, params, dom, rect, size) {
                var x = 0; // x坐标位置
                var y = 0; // y坐标位置
                // 当前鼠标位置
                var pointX = point[0];
                var pointY = point[1];
                // 提示框大小
                var boxWidth = size.contentSize[0];
                var boxHeight = size.contentSize[1];

                // boxWidth > pointX 说明鼠标左边放不下提示框
                if (boxWidth > pointX) {
                    x = pointX + 10;
                } else { // 左边放的下
                    x = pointX - boxWidth - 10;
                }

                // boxHeight > pointY 说明鼠标上边放不下提示框
                if (boxHeight > pointY) {
                    y = 5;
                } else { // 上边放得下
                    y = pointY - boxHeight;
                }
                return [x, y];
            },
        },
        color: ['#174aeb', '#d93434', '#1292f5', '#f1b72f', '#59e8fa', '#f06e22', '#7c5dc8', '#34d160', '#2EB7BD', '#3CE1D9', '#FBBACB', '#4695D1', 'FFFFCC', '#FFCCCC', '#CCFFFF’', '#CCFFCC', '#CCCCFF', '#FF9999', '#FFCC99', '#99CC99', '#99CCFF', '#99CCCC', '#CCFF99', '#99CCFF', '#66CCCC', '#FFFFCC'],
        series: [{
            type: 'pie',
            radius: '75%',
            center: ['40%', '50%'],
            colorBy: 'series',
            itemStyle: {
                borderRadius: 8
            },
            data: datas,
            label: {
                color: 'rgba(255, 255, 255, 1)',
                position: 'outer',
                alignTo: 'labelLine',
                formatter: function (data) {
                    var val = "";
                    console.log(data['name'])
                    if (data['name'].length > 8) {
                        val = data['name'].substr(0, 8) + '...';
                        return val;
                    } else {
                        return data['name'];
                    }
                },
            },
            labelLine: {
                length: 3,

            }
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

function echarts_2(xAxisData, zs, zm, pj) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart2'));

    var xAxisData = xAxisData;
    var data1 = zs;
    var data2 = zm;
    var data3 = pj;

    var emphasisStyle = {
        itemStyle: {
            shadowBlur: 10,
            shadowColor: 'rgba(0,0,0,0.3)'
        }
    };
    var option = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            textStyle: {
                color: 'rgba(255, 255, 255, 1)'
            }
        },
        xAxis: {
            data: xAxisData,
            axisLine: {
                onZero: true,
                lineStyle: {
                    color: 'rgba(255, 255, 255, 1)'
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
            },
            splitLine: {
                show: true,
                lineStyle: {
                    // color: 'rgba(106, 41, 41, 1)',
                }
            }
        },
        yAxis: {
            axisLine: {
                lineStyle: {
                    color: 'rgba(255, 255, 255, 1)'
                }
            },
            splitLine: {
                show: true,
                lineStyle: {
                    // color: 'rgba(106, 41, 41, 1)',
                }
            }
        },
        grid: {
            top: '13%',
            bottom: '14%',
            left: '7%',
            right: '7%'
            // borderColor: 'rgba(118, 28, 28, 1)',
        },
        series: [{
            name: '不动产权证书',
            type: 'bar',
            stack: 'one',
            // emphasis: emphasisStyle,
            data: data1,
            itemStyle: {
                color: '#1867ce'
            },
            barWidth: '29%'
        }, {
            name: '不动产登记证明',
            type: 'bar',
            stack: 'one',
            // emphasis: emphasisStyle,
            data: data2,
            itemStyle: {
                color: '#3dd4ff'
            },
            barWidth: '29%'
        }, {
            name: '发证量占比',
            type: 'line',
            stack: 'one',
            // emphasis: emphasisStyle,
            data: data3,
            itemStyle: {
                color: '#fffb8b'
            },
            barWidth: '29%'
        }]
        // 使用刚指定的配置项和数据显示图表。
    };
    myChart.setOption(option);
    myChart.on('click', function (params) {
        if (params.color == "#1867ce") {
            myChart.setOption({
                legand: {
                    selected: {
                        '不动产权证书': true,
                        '不动产登记证明': false,
                        '发证量占比': false
                    },
                }
            });
        }

    });
    setTimeout(function () {
        myChart.resize();
    }, 200)
    window.addEventListener('resize', function () {
        myChart.resize();
    });
}

function echarts_4(xAxisData, jrl, cgl, sbl) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart4'));

    var xAxisData = xAxisData;
    var data1 = jrl;
    var data2 = cgl;
    var data3 = sbl;
    var option = {
        legend: {
            textStyle: {
                color: 'rgba(255, 255, 255, 1)'
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        xAxis: {
            data: xAxisData,
            axisLine: {
                onZero: true,
                lineStyle: {
                    color: 'rgba(255, 255, 255, 1)'
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
            name: '接入量',
            type: 'value',
            alignTicks: true,
            axisLine: {
                lineStyle: {
                    color: 'rgba(255, 255, 255, 1)'
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
            axisLabel: {
                show: true,
                interval: 'auto',
                formatter: '{value} %'
            },
            alignTicks: true,
            name: '接入成功率',
            axisLine: {
                lineStyle: {
                    color: 'rgba(255, 255, 255, 1)'
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
            bottom: '25%',
            left: '9%',
            right: '10%'
            // borderColor: 'rgba(118, 28, 28, 1)',
        },
        series: [{
            name: '接入量',
            type: 'line',
            stack: 'Total',
            data: data1,
            yAxisIndex: 0,
            emphasis: {
                focus: 'series'
            },
            areaStyle: {},
            itemStyle: {
                color: '#3dd4ff'
            }
        }, {
            name: '成功率',
            type: 'line',
            stack: 'Total',
            data: data2,
            yAxisIndex: 0,
            emphasis: {
                focus: 'series'
            },
            // areaStyle: {},
            itemStyle: {
                color: '#1cc85b'
            }
        }, {
            name: '上报量',
            type: 'line',
            stack: 'Total',
            data: data3,
            yAxisIndex: 1,
            emphasis: {
                focus: 'series'
            },
            areaStyle: {},
            itemStyle: {
                color: '#fffb8b'
            }
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

function echarts_6(data) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('map'));

    var option;


    var geoCoordMap = {
        天宁区: [120.11, 31.84],
        新北区: [119.9, 31.92],
        钟楼区: [119.85, 31.81],
        金坛区: [119.5, 31.74],
        武进区: [119.91, 31.6],
        溧阳市: [119.39, 31.46]
    };

    var convertData = function convertData(data) {
        var res = [];
        for (var i = 0; i < data.length; i++) {
            var geoCoord = geoCoordMap[data[i].name];
            if (geoCoord) {
                res.push({
                    name: data[i].name,
                    // value: geoCoord.concat(0,0).concat(data[i].value),
                    value: geoCoord,
                    itemStyle: {
                        color: 'white'
                    }
                });
            }
        }
        return res;
    };

    myChart.showLoading();
    $.get('../../static/js/bdcDp/changzhou.json', function (geoJson) {
        myChart.hideLoading();
        echarts.registerMap('changzhou', geoJson);
        myChart.setOption(option = {
            visualMap: {
                min: 0,
                max: 600,
                itemWidth: 10,
                text: ['', '住宅总面积(万m²)'],
                realtime: false,
                calculable: true,
                orient: 'horizontal',
                align: 'top',
                right: '0',
                inRange: {
                    color: ['white', '#75d3f7', '#6baaf1', '#4587dd', '#3275d2', '#2165c9', '#155bc3']
                },
                textStyle: {
                    color: 'rgba(255, 255, 255, 1)'
                },
                show: false, // 图注
                // calculable: false,
                handleSize: '0%'
            },
            tooltip: {
                trigger: 'item',
                formatter: function formatter(params) {
                    var res = params.name + '<br/>';
                    var myseries = params.data.value;
                    if (isNotBlank(myseries)) {
                        res += '总面积:' + myseries[0] + '万㎡' + '<br/>' + '套数:' + myseries[1] + '<br/>';
                    } else {
                        res += '总面积:' + 0 + '万㎡' + '<br/>' + '套数:' + 0 + '<br/>';
                    }
                    return res;
                },
                textStyle: {
                    fontFamily: "Microsoft YaHei"
                }
            },
            geo: {
                map: 'changzhou',
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
                map: 'changzhou',
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
                // encode: {
                // 	value: 2
                // },
                // symbolSize: function(val) {
                // 	return val[2] / 10;
                // },
                label: {
                    formatter: '{b}',
                    position: 'right',
                    show: true,
                    fontSize:16,
                    color: '#43f9fb'
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
    setTimeout(function () {
        myChart.resize();
    }, 200)
    window.addEventListener('resize', function () {
        myChart.resize();
    });
    myChart.on("click", function (params) {
        // 调用穿透接口
        getZztjct(params.name)
    });
}

function echarts_6_ct(data, qxmc) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('map_ct'));
    //清空图表信息 否则会有上一次地图的残影闪烁
    myChart.clear();
    var option;
    var geoCoordMap = {
        天宁区: [120.11, 31.84],
        新北区: [119.9, 31.92],
        钟楼区: [119.85, 31.81],
        金坛区: [119.5, 31.74],
        武进区: [119.91, 31.6],
        溧阳市: [119.39, 31.46]
    };
    //匹配地图坐标值
    var convertData = function convertData(data) {
        var res = [];
        for (var i = 0; i < data.length; i++) {
            var geoCoord = geoCoordMap[data[i].name];
            //取传入的区县值
            if ( !isNullOrEmpty(geoCoord) &&data[i].name==qxmc) {
                res.push({
                    name: data[i].name,
                    value: geoCoord,
                    itemStyle: {
                        color: 'white'
                    }
                });
            }
        }
        return res;
    };
    //判断返回的区县地图
    var area = "";
    if (qxmc == "金坛区") {
        area = "jintanqu";
    } else if (qxmc == "天宁区") {
        area = "tianningqu"
    } else if (qxmc == "新北区") {
        area = "xinbeiqu"
    } else if (qxmc == "钟楼区") {
        area = "zhonglouqu"
    } else if (qxmc == "武进区") {
        area = "wujinqu"
    } else if (qxmc == "溧阳市") {
        area = "liyangshi"
    }
    // myChart.showLoading();
    $.get('../../static/js/bdcDp/mapJson/' + area + '.json', function (geoJson) {
        // myChart.hideLoading();
        //展示地图
        echarts.registerMap('changzhou', geoJson);
        //地图配置
        myChart.setOption(option = {
            visualMap: {
                min: 0,
                max: 600,
                itemWidth: 10,
                text: ['', '住宅总面积(万m²)'],
                realtime: false,
                calculable: true,
                orient: 'horizontal',
                align: 'top',
                right: '0',
                inRange: {
                    color: ['white', '#75d3f7', '#6baaf1', '#4587dd', '#3275d2', '#2165c9', '#155bc3']
                },
                textStyle: {
                    color: 'rgba(255, 255, 255, 1)'
                },
                show: false, // 图注
                // calculable: false,
                handleSize: '0%'
            },
            tooltip: {
                trigger: 'item',
                formatter: function formatter(params) {
                    var res = params.name + '<br/>';
                    var myseries = params.data.value;
                    if (isNotBlank(myseries)) {
                        res += '总面积:' + myseries[0] + '万㎡' + '<br/>' + '套数:' + myseries[1] + '<br/>';
                    } else {
                        res += '总面积:' + 0 + '万㎡' + '<br/>' + '套数:' + 0 + '<br/>';
                    }
                    return res;
                },
                textStyle: {
                    fontFamily: "Microsoft YaHei"
                }
            },
            geo: {
                map: 'changzhou',
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
                map: 'changzhou',
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
                // encode: {
                // 	value: 2
                // },
                // symbolSize: function(val) {
                // 	return val[2] / 10;
                // },
                label: {
                    formatter: '{b}',
                    position: 'right',
                    show: true,
                    fontSize:16,
                    color: '#43f9fb'
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
    setTimeout(function () {
        myChart.resize();
    }, 200)
    window.addEventListener('resize', function () {
        myChart.resize();
    });
}

function loadDyrz() {
    getReturnData("/rest/v1.0/bdcdp/getTyrzTj", JSON.stringify({
        nf: $("#dyrzsj").val(),
        kssj: kssj,
        jssj: jssj
    }), "POST", function (data) {
        //1 土地 ，2，房屋
        for (var i = 0; i < data.dyzje.length; i++) {
            if (data.dyzje[i].dybdclx == 1) {
                $("#tddyzje").html(data.dyzje[i].je);
            }
            if (data.dyzje[i].dybdclx == 2) {
                $("#zzdyzje").html(data.dyzje[i].je);
            }
        }

        var xAxisData = [];
        var dytc = [];

        var dyje = [];

        for (var i = 0; i < data.zztsdyjeList.length; i++) {
            xAxisData.push(data.zztsdyjeList[i].YF);
            dytc.push(data.zztsdyjeList[i].DYTS)
            dyje.push(data.zztsdyjeList[i].DYJE)

        }
        var maxDytc = RoundedMax(dytc);
        var maxDyje = RoundedMax(dyje);
        var intervalDytc = maxDytc/5;
        var intervalDyje = maxDyje/5;
        echarts_3(xAxisData, dytc, dyje,maxDytc,maxDyje,intervalDytc,intervalDyje);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}

function setSelect() {
    var year = new Date().getFullYear();
    for (var i = 0; i < 5; i++) {
        var selectValue = year - i;
        $("#dyrzsj").append("<option value='" + selectValue + "'>" + selectValue + "</option>")
    }

}

function setBqdfSelect() {
    var month = new Date().getMonth() + 1;
    for (var i = 1; i <= month; i++) {
        var selectValue = i;
        var selectText = i + "月";
        $("#dfqksj").append("<option value='" + selectValue + "'>" + selectText + "</option>");

    }
    $("#dfqksj").val(month);
}

function loadBqdfqk() {
    getReturnData("/rest/v1.0/bdcdp/getBqdfqk", JSON.stringify({
        yf: $("#dfqksj").val(),
        kssj: kssj,
        jssj: jssj
    }), "POST", function (data) {
        //console.log(data);
        var xAxisData = data.xAxisData;
        var jrl = data.jrl;
        var cgl = data.cgl;
        var sbl = data.sbl;
        var jrzl = data.jrzl;
        var jrzcgl = data.jrzcgl;
        var qsmdfqk = data.qsmdfqk;
        for (var i = 0; i < qsmdfqk.length; i++) {
            $("#jrzlmc" + i).html(qsmdfqk[i].qxmc || "");
            $("#jrzldf" + i).html(qsmdfqk[i].df || "");

        }
        echarts_4(xAxisData, jrl, cgl, sbl);
        $("#jrzl").html(jrzl || 0);
        $("#jrzcgl").html(jrzcgl || 0);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}

function echarts_3(xAxisData, dytc, dyje,maxDytc,maxDyje,intervalDytc,intervalDyje) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart3'));
    var option = {
        legend: {
            textStyle: {
                color: 'rgba(255, 255, 255, 1)'
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
                    color: 'rgba(255, 255, 255, 1)'
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
                    color: 'rgba(255, 255, 255, 1)'
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
                    color: 'rgba(255, 255, 255, 1)'
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
                    color: '#ffb500'
                }
            }, {
                name: '抵押量',
                type: 'bar',
                stack: 'one',
                data: dytc,
                itemStyle: {
                    color: '#59e8fa'
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

function toGxxq() {
    window.open('/realestate-exchange/view/dsfrz.html?type=logines');
}

function toDjHome() {
    window.open('/realestate-portal-ui/home/home-page.html?clientId=initialClientId&moduleCode=1&loadHome=true')
}

function toGzjcgk() {
    window.open('/realestate-check/view/zj.html')
}

function toScore() {
    window.open('/realestate-inquiry-ui/view/bdcdp/jrzltj.html')
}

function toJrsb() {
    window.open('/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=dbrzcx')
}

function toDyrz() {
    window.open('/realestate-inquiry-ui/standard/dyaCx/dyacx.html')
}

function toDjlx() {
    window.open('/realestate-inquiry-ui/view/bdcdp/djlx.html')
}

function toFzl() {
    window.open('/realestate-inquiry-ui/view/count/zszmTj.html')
}

function search() {
    kssj = $("#startTime").val();
    jssj = $("#endTime").val();
    if (!isNullOrEmpty(kssj) || !isNullOrEmpty(jssj)) {
        $(".active").hide()
        $(".today").hide()
    }
    getjrdjlx();
    getFzsltj();
    loadDyrz();
    getGxcsTj();
    getZztj();
    getsbqk();
    getgzjcgk();
    loadBqdfqk();


}

function reloadDate() {
    getjrdjlx();
    getFzsltj();
    loadDyrz();
    getGxcsTj();
    getZztj();
    getsbqk();
    getgzjcgk();
    loadBqdfqk();
}
//登记类型返回
function  goback(){
    $("#echart1").show();
    $("#echart1_ct").hide();
}
//返回市
function  gobackMap(){
    getReturnData("/rest/v1.0/bdcdp/getZztj", JSON.stringify({kssj: kssj, jssj: jssj}), "POST", function (data) {
        //console.log(data);
        var zzfbqkList = data.zzfbqkList;
        var datas = [];
        var geoCoordMap = {
            天宁区: [120.11, 31.84],
            新北区: [119.9, 31.92],
            钟楼区: [119.85, 31.81],
            金坛区: [119.5, 31.74],
            武进区: [119.91, 31.6],
            溧阳市: [119.39, 31.46]
        };
        //console.log(geoCoordMap);
        for (var key in geoCoordMap) {
            var obj = {};
            obj.name = key;
            for (var i = 0; i < zzfbqkList.length; i++) {
                if (zzfbqkList[i].qxmc == key) {
                    var value = [];
                    value.push(zzfbqkList[i].mj);
                    value.push(zzfbqkList[i].ts);
                    obj.value = value;
                }
            }
            datas.push(obj);
        }
        $("#zzzts").html(data.zzzts);
        $("#zzzmj").html(data.zzzmj);
        $("#zzjtmj").html(data.zzjtmj);
        $("#dyzje").html(data.dyzje);
        //隐藏穿透后的的图表
        $("#map_ct").hide();
        $("#map").show();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
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