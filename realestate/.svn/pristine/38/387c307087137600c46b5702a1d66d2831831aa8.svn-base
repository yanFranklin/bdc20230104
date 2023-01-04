'use strict';
var form;
var $;
var layer;
var laydate;
var kssj = "";
var jssj = "";
var djlx = "";//登记类型
var qxmc = "";//区县名称
var color = "";//点击获取的颜色
var response;
var version = "";
var geoCoordMap = "";
var dpTitle = "";
$(function () {
    layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer'], function () {
        form = layui.form;
        $ = layui.jquery;
        layer = layui.layer;
        laydate = layui.laydate;
    });
    getBdcDpConfig();
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
    getReturnData("/rest/v1.0/bdcdp/getjrdjlx", JSON.stringify({kssj: kssj, jssj: jssj, qxmc: qxmc}), "POST", function (data) {
        var datas = [];
        var total = 0;
        for (var i = 0; i < data.length; i++) {
            if (isNotBlank(data[i].djlxmc)) {
                datas.push({"name": data[i].djlxmc, "value": data[i].sl})
                total+=data[i].sl;
            }
        }
        $("#djsum").html(total);
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
    getReturnData("/rest/v1.0/bdcdp/getLctjByDjlx", {kssj: kssj, jssj: jssj, djlx: value, qxmc: qxmc}, "get", function (data) {
        var datas = [];
        for (var i = 0; i < data.length; i++) {
            if (isNotBlank(data[i].djlxmc)) {
                datas.push({"name": data[i].djlxmc, "value": data[i].sl})
            }
        }

        if(datas.length>10){
            //排序
            datas.sort(function (a, b) {
                if (a.value < b.value) return 1;//交换位置
                if (a.value > b.value) return -1;// -1 不要交换位置
                return 0;
            });
                var others = 0;
            for (var i = 10; i < datas.length; i++) {
                    others +=datas[i].value;
            }
            //截取10个数据
            datas.splice(9);
            //添加其他的数据
            datas.push({"name": "其他", "value": others});
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
        jssj: jssj,
        qxmc:qxmc
    }), "POST", function (data) {
        var xAxisData = data.xAxisData;
        var zs = data.zsslList;
        var zm = data.zmslList;
        var pj = data.fzlzb;
        var fzsl = data.fzzs;
        $("#fzzs").html(fzsl);
        //计算最大值进行y轴值分配
        var maxFzl = RoundedMax(zs)+RoundedMax(zm);
        var intervalFzl = maxFzl/5;

        echarts_2(xAxisData, zs, zm, pj,maxFzl,intervalFzl);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}

//获取抵押融资统计
function getTyrzTj() {
    //设置年份
    var thisyear = new Date().getFullYear();
    $(".eightrow").prev().find(".today").html(thisyear);
    getReturnData("/rest/v1.0/bdcdp/getTyrzTj", JSON.stringify({
        nf: thisyear,
        kssj: kssj,
        jssj: jssj,
        qxmc: qxmc
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
        if(isNullOrEmpty(data.dyzje)){
            $("#tddyzje").html(0);
            $("#zzdyzje").html(0);
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
                var mc = gxxq[index].MC+"总次数";
                //去除空格
               mc= deleteWhitespace(mc, "all");
                if(mc.length>8){
                   var submc=mc.substr(0, 7) + '...';
                   console.log("<div>" + gxxq[index].ZS + "次</div><div title="+mc+">" + submc + "</div>");
                    $(element).append("<div>" + gxxq[index].ZS + "次</div><div title="+mc+">" + submc + "</div>")

                }else{
                    $(element).append("<div>" + gxxq[index].ZS + "次</div><div>" + gxxq[index].MC + "总次数</div>");
                }

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
        //console.log(geoCoordMap);
        var  colors=["#F1B72F","#FF7925","#8866DC","#39E569","#EE3939","#3DD4FF","#59e8fa","#FBBACB","#58a0f8"];//各区县地图块颜色
        for (var key in geoCoordMap) {
            var obj = {};
            obj.name = key;
            for (var i = 0; i < zzfbqkList.length; i++) {
                if (zzfbqkList[i].qxmc == key) {
                    var value = [];
                    value.push(zzfbqkList[i].mj);
                    value.push(zzfbqkList[i].ts);
                    obj.value = value;
                    obj.itemStyle={ color: colors[i]}
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
function getZztjct() {
    getReturnData("/rest/v1.0/bdcdp/getZztj", JSON.stringify({
        kssj: kssj,
        jssj: jssj,
        qxmc: qxmc
    }), "POST", function (data) {
        //console.log(data);
        var zzfbqkList = data.zzfbqkList;
        var datas = [];
        for (var key in geoCoordMap) {
            var obj = {};
            obj.name = key;
            for (var i = 0; i < zzfbqkList.length; i++) {
                if (zzfbqkList[i].qxmc == qxmc) {
                    var value = [];
                    value.push(zzfbqkList[i].mj);
                    value.push(zzfbqkList[i].ts);
                    obj.value = value;
                    obj.itemStyle= { color: color }
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
        jssj: jssj,
        qxmc: qxmc
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
        //计算最大值进行y轴值分配
        var maxJrl = RoundedMax(jrl);
        var intervalJrl = maxJrl/5;
        echarts_4(xAxisData, jrl, cgl, sbl,maxJrl,intervalJrl);
        $("#jrzl").html(jrzl || 0);
        $("#jrzcgl").html(jrzcgl || 0);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, true);
}

function echarts_1(datas) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart1'));
    //清空图表信息
    myChart.clear();
    var Title = {};
    if(datas.length == 0){
        Title = {
            text: "暂无数据",
            left: "center",
            top: "center",
            textStyle: {
                fontSize: '15',
                color: 'white'
            }
        }
    }
    var option = {
        title:Title,
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
            trigger: 'item',
            textStyle: { // 文字提示样式
                fontSize: '16'
                }

            },
        color: ['#174aeb', '#d93434', '#1292f5', '#f1b72f', '#59e8fa', '#f06e22', '#7c5dc8', '#34d160'],
        series: [{
            type: 'pie',
            radius: '60%',
            center: ['50%', '60%'],
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
                alignTo: 'labelLine',
                fontSize:15,
                formatter: '{b}\n'
            },
            labelLine: {
                length: 3,

            }
        }]

        // 使用刚指定的配置项和数据显示图表。
    };
    //销毁点击事件
    myChart.off('click');
    //点击事件实现穿透效果
    myChart.on("click", function (params) {
        djlx=params.name;
        //显示登记类型和数量
        $("#djlx").html(djlx);
        $("#djsum").html(params.value);
        getctdjlx(djlx);
        $(".goback").show();
    });
    myChart.setOption(option);
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
        color: ['#174aeb', '#d93434', '#1292f5', '#f1b72f', '#59e8fa', '#f06e22', '#7c5dc8', '#34d160', '#2EB7BD', '#3CE1D9', '#FBBACB', '#4695D1'],
        series: [{
            type: 'pie',
            radius: '60%',
            center: ['50%', '60%'],
            colorBy: 'series',
            itemStyle: {
                borderRadius: 8
            },
            data: datas,
            label: {
                color: 'rgba(255, 255, 255, 1)',
                alignTo: 'labelLine',
                fontSize:15,
                formatter: function (data) {
                    var val = "";
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

function echarts_2(xAxisData, zs, zm, pj,maxFzl,intervalFzl) {
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
            trigger: 'axis',
            textStyle: { // 文字提示样式
                fontSize: '15'
            }
        },
        legend: {
            textStyle: {
                color: 'rgba(255, 255, 255, 1)',
                fontSize:15
            },
            left:'center',
            padding:[20,0,0,0]
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
                rotate: 30,
                textStyle: {
                        fontSize : 15
                    }

            },
            splitLine: {
                show: true,
                lineStyle: {
                    // color: 'rgba(106, 41, 41, 1)',
                }
            }
        },
        yAxis: [{
            min: 0,
            max: maxFzl,
            interval: intervalFzl,
            axisLine: {
                lineStyle: {
                    color: 'rgba(255, 255, 255, 1)'
                }
            },
            axisLabel: {
                textStyle: {
                    fontSize : 15
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
            nameTextStyle: {
                fontSize: 15
            },
            min: 0,
            max: 100,
            interval: 20,
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
            },
            axisLabel: {
                textStyle: {
                    fontSize: 15
                },
                formatter: '{value} %',
            }
        }],
        grid: {
            top: '20%',
            bottom: '20%',
            left: '12%',
            right: '11%'
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
            stack: 'two',
            yAxisIndex: 1,  //选择index为1的Y轴作为参考系
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

function echarts_4(xAxisData, jrl, cgl, sbl,maxJrl,intervalJrl) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart4'));

    var xAxisData = xAxisData;
    var data1 = jrl;
    var data2 = cgl;
    var data3 = sbl;
    var option = {
        legend: {
            textStyle: {
                color: 'rgba(255, 255, 255, 1)',
                fontSize: 15
            },
            padding:[0,10,20,0],
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            },
            textStyle: { // 文字提示样式
                fontSize: '15'
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
                rotate: 45,
                fontSize:15
            }
        },
        yAxis: [{
            name: '接入量',
            min: 0,
            max: maxJrl,
            interval: intervalJrl,
            nameTextStyle: {
                padding: [0,40,0,0],
                fontSize:15
            },
            type: 'value',
            alignTicks: true,
            axisLine: {
                lineStyle: {
                    color: 'rgba(255, 255, 255, 1)'
                }
            },
            axisLabel: {
                fontSize:15
            },
            splitLine: {
                show: true,
                lineStyle: {
                    // color: 'rgba(106, 41, 41, 1)',
                }
            }
        }, {
            type: 'value',
            min: 0,
            max: 100,
            interval: 20,
            axisLabel: {
                show: true,
                interval: 'auto',
                formatter: '{value} %',
                fontSize: 15

            },
            alignTicks: true,
            name: '接入成功率',
            nameTextStyle: {
                padding: [0,0,0,70],
                fontSize: 15
            },
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
            top: '20%',
            bottom: '18%',
            left: '12%',
            right: '18%'
            // borderColor: 'rgba(118, 28, 28, 1)',
        },
        series: [{
            name: '接入量',
            type: 'line',
            data: data1,
            yAxisIndex: 0,
            emphasis: {
                focus: 'series'
            },
            // areaStyle: {},
            itemStyle: {
                color: '#3dd4ff'
            }
        }, {
            name: '成功率',
            type: 'line',
            data: data2,
            yAxisIndex: 1,  //选择index为1的Y轴作为参考系
            emphasis: {
                focus: 'series'
            },
            // areaStyle: {},
            itemStyle: {
                color: '#1cc85b'
            }
        }, {
            name: '登簿量',
            type: 'line',
            data: data3,
            yAxisIndex: 0,
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
    var JsonUrl = "../static/js/bdcDp/"+version+"Map/"+version+".json";
    $.get(JsonUrl, function (geoJson) {
        myChart.hideLoading();
        echarts.registerMap('changzhou', geoJson);
        myChart.setOption(option = {
            // visualMap: {
            //     min: 0,
            //     max: 20,
            //     itemWidth: 10,
            //     text: ['', '住宅总面积(万m²)'],
            //     realtime: false,
            //     calculable: true,
            //     orient: 'horizontal',
            //     align: 'top',
            //     right: '0',
            //     inRange: {
            //         color: ['white', '#75d3f7', '#6baaf1', '#4587dd', '#3275d2', '#2165c9', '#155bc3']
            //     },
            //     textStyle: {
            //         color: 'rgba(255, 255, 255, 1)'
            //     },
            //     show: false, // 图注
            //     // calculable: false,
            //     handleSize: '0%'
            // },
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
                    fontFamily: "Microsoft YaHei",
                    fontSize:18

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
                data:data,
                itemStyle: {
                    borderColor: '#3dd4ff',
                    borderWidth: '2',
                    // shadowBlur: 20, //软阴影值
                    shadowColor: '#95d3fd',
                    shadowOffsetX: 0,
                    // shadowOffsetY: 0,
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
                    fontSize:18,
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
    //销毁点击事件
    myChart.off('click');
    myChart.on("click", function (params) {
         qxmc = params.name;
         color =  params.color;
        // 显示返回按钮
        $(".gobackMap").show();
        // 住宅统计
        getZztjct()
        //登记类型穿透
        if(!isNullOrEmpty(djlx)){
            getctdjlx(djlx);
        }else{
            //登记类型
            getjrdjlx()
        }
    //    发证量统计
        getFzsltj();
    //    抵押融资
        getTyrzTj();
        //得分
        getBqdfqk();
    });
}

function echarts_6_ct(data, qxmc) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('map_ct'));
    //清空图表信息 否则会有上一次地图的残影闪烁
    myChart.clear();
    var option;
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
    //判断返回的区县地图 此处调用了pinyin4js.js中文转拼音的插件 qxmc是点击地图获取的区县名称 后面是格式
    var area = PinyinHelper.convertToPinyinString(qxmc,'', PinyinFormat.WITHOUT_TONE)
    // myChart.showLoading();
    $.get('../static/js/bdcDp/'+version+'Map/' + area + '.json', function (geoJson) {
        // myChart.hideLoading();
        //展示地图
        echarts.registerMap('changzhou', geoJson);
        //地图配置
        myChart.setOption(option = {
            // visualMap: {
            //     min: 0,
            //     max: 600,
            //     itemWidth: 10,
            //     text: ['', '住宅总面积(万m²)'],
            //     realtime: false,
            //     calculable: true,
            //     orient: 'horizontal',
            //     align: 'top',
            //     right: '0',
            //     inRange: {
            //         color: ['white']
            //     },
            //     textStyle: {
            //         color: 'rgba(255, 255, 255, 1)'
            //     },
            //     show: false, // 图注
            //     // calculable: false,
            //     handleSize: '0%'
            // },
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
                    fontFamily: "Microsoft YaHei",
                    fontSize:18
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
                    fontSize:18,
                    color: '#1292F5'
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
        if(isNullOrEmpty(data.dyzje)){
            $("#tddyzje").html(0);
            $("#zzdyzje").html(0);
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
        //计算最大值进行y轴值分配
        var maxJrl = RoundedMax(jrl);
        var intervalJrl = maxJrl/5;
        echarts_4(xAxisData, jrl, cgl, sbl,maxJrl,intervalJrl);
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
                color: 'rgba(255, 255, 255, 1)',
                fontSize:15
            },
            left:'20%'
        },
        tooltip: {
            trigger: 'axis',
            textStyle: { // 文字提示样式
                fontSize: '15'
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
                rotate: 35,
                textStyle: {
                        fontSize : 15
                }
            }
        },
        yAxis: [{
            type: 'value',
            min: 0,
            max: maxDytc,
            interval: intervalDytc,
            name: '抵押量（套）',
            nameTextStyle:{
                fontSize:15
            },
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
            },
            axisLabel: {
                textStyle: {
                    fontSize : 15
                }
            }
        }, {
            type: 'value',
            name: '抵押金额（亿元）',
            nameTextStyle:{
                fontSize:15
            },
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
            },
            axisLabel: {
                textStyle: {
                    fontSize : 15
                }
            }
        }],
        grid: {
            top: '16%',
            bottom: '20%',
            left: '11%',
            right: '16%'
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
                    color: '#ffb500',
                    fontSize:15
                }
            }, {
                name: '抵押量',
                type: 'bar',
                stack: 'one',
                data: dytc,
                itemStyle: {
                    color: '#59e8fa',
                    fontSize:15
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
    window.open('/realestate-portal-ui/home/home-page.html?clientId=initialClientId&moduleCode=1&loadHome=true&toHomePage=toHomePage')
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
    window.open('/realestate-inquiry-ui/view/bdcdp/dyrz.html')
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
    if(!isNotBlank(kssj) || !isNotBlank(jssj)){
        layer.msg('<img src="../../static/image/error-small.png" alt="">查询时间不能为空！');
        return false;
    }
    if (!isNullOrEmpty(kssj) || !isNullOrEmpty(jssj)) {
        $(".active").hide()
        $(".today").hide()
    }
    $(".loading").show();
    getjrdjlx();
    getFzsltj();
    loadDyrz();
    getGxcsTj();
    getZztj();
    getsbqk();
    getgzjcgk();
    loadBqdfqk();
    $(".loading").hide();
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
    djlx="";
    $("#djlx").html("登记量");
    getjrdjlx()
    $(".goback").hide();
}
//返回市
function  gobackMap(){
    //住宅统计
    getZztj();
    //区县置空
    qxmc="";
    //登记类型
    if(!isNullOrEmpty(djlx)){
        getctdjlx(djlx);
    }else{
        //登记类型
        getjrdjlx()
    }
    //    发证量统计
    getFzsltj();
    //    抵押融资
    getTyrzTj();
    //得分
    getBqdfqk();
    $(".gobackMap").hide();

}


//数据交互
function getReturnData(_path, _param, _type, _fn, _errorFn, async) {
    if (async === undefined) {
        async = true;
    }
    var _url = getContextPath() + _path;
    $.ajax({
        url: _url,
        type: _type,
        async: async,
        contentType: 'application/json;charset=utf-8',
        data: _param,
        success: function (data) {
            _fn.call(this, data, data);
        },
        error: function (err) {
            _errorFn.call(this, err);
        }
    });
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
//获取配置
function getBdcDpConfig(){
    $.ajax({
        url: getContextPath() + '/rest/v1.0/bdcdp/getBdcDpConfig',
        type: "GET",
        dataType: 'json',
        async: false,
        success: function (data) {
            removeModal();
            if (isNotBlank(data)) {
                version = data.version;
                geoCoordMap = data.geoCoordMap;
                console.info(geoCoordMap);
                dpTitle = data.dpTitle;
                $("#dpTitle").html(dpTitle);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}