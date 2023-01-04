/**
 * Created by Ypp on 2020/3/23.
 * 第三方日志颜色配置页面js
 */
var pzList = ajaxQuery("../bdcDsfLog/getyspzlist", null, "POST", false);
var qqsl = [];
var dyqs = [];
layui.use(['colorpicker','form','laytpl'], function(){
    var colorpicker = layui.colorpicker,
        $ = layui.jquery,
        form = layui.form,
        laytpl = layui.laytpl;
    var legend = [];
    var colorList = [];
    var getTpl = colorTpl.innerHTML;

    // pzList=[
    //     {
    //         "name":"111",
    //         "mc":"111",
    //         "color":"#135252"
    //     },
    // ]
    laytpl(getTpl).render(pzList, function(html){
        $('.bdc-config-color').html(html);
        pzList.forEach(function (v,i) {
            legend.push(v.mc);
            colorList.push(v.color);
            colorpicker.render({
                elem: '.'+ v.name
                ,color: v.color
                ,done: function(color){
                    pzList.forEach(function(value,index){
                        if(value.name == v.name){
                            value.color = color;
                            colorList[index] = color;
                        }
                    });
                    //重新加载饼图
                    renderQqsl(legend,colorList);
                    //重新加载折线图
                    renderDyqs(legend,colorList);
                }
            });
        });
        // 调整预览窗口高度
        var topheight = $('.bdc-top').height();
        var height = document.body.offsetHeight - topheight - 50;
        $('.bdc-demo').css("height",height+'px');

        renderQqsl(legend,colorList);
        renderDyqs(legend,colorList);

        // 调整预览窗口内容高度
        var contentHeight = $('.bdc-demo-content').height();
        $('.bdc-demo-content').css('height',(contentHeight+30)+'px');
    });

    $('#saveColor').on('click',function () {
        console.log(colorList);
    });

    //饼图
    function renderQqsl(legend,colorList){
        var qqslChart = echarts.init(document.getElementById('qqslYh'));
        var qqslOption = {
            legend: {
                orient: 'horizontal',
                left: 10,
                data: legend
            },
            color: colorList,
            graphic: [{
                type: 'text',
                left: 'center',
                top: '60%',
                style: {
                    text: "共享系统",
                    textAlign: 'center',
                    fill: '#333',
                    width: 30,
                    height: 30,
                    fontSize: 24,
                    color: "#4d4f5c",
                    fontFamily: "Microsoft YaHei"
                }
            }, {
                type: 'text',
                left: 'center',
                top: '67%',
                style: {
                    text: '(单击图形查看子系统)',
                    textAlign: 'center',
                    fill: '#999',
                    width: 30,
                    height: 30,
                    fontSize: 14
                }
            }],
            series: [{
                name: '各系统请求数量',
                type: 'pie',
                radius: ['45%', '60%'],
                center:['50%','65%'],
                label: {
                    show: false
                },
                emphasis: {
                    label: {
                        show: true,
                        formatter: '{b|{b}：}{c}   \n {per|{d}%}  ',
                        backgroundColor: '#f3f4f6',
                        borderColor: '#d0d5da',
                        borderWidth: 1,
                        borderRadius: 4,
                        padding: [0, 10],
                        rich: {
                            a: {
                                color: '#999',
                                lineHeight: 22,
                                align: 'center'
                            },
                            b: {
                                fontSize: 16,
                                color: '#333',
                                lineHeight: 30
                            },
                            c: {
                                align: 'left',
                                color: '#333!important',
                                fontSize: 18
                            },
                            per: {
                                color: '#1d87d1',
                                align: 'left',
                                lineHeight: 20
                            }
                        }
                    }
                },
                data: getQqsl()
            }
            ]
        };
        qqslChart.setOption(qqslOption,true);
    }
    //折线图
    function renderDyqs(legend){
        var dyqsChart = echarts.init(document.getElementById('dyqsZxt'));
        var dyqsOption = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: legend
            },
            grid: {
                left: '3%',
                right: '10%',
                bottom: '3%',
                top: 120,
                left:50,
                containLabel: true
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: ['02-01', '02-02', '02-03', '02-04', '02-05', '02-06', '02-07']
            },
            yAxis: {
                type: 'value',
            },
            color: colorList,
            series: getDyqs()
        };
        dyqsChart.setOption(dyqsOption,true);
    }

    function getQqsl(){
        if(qqsl.length > 0){
            return qqsl;
        }
        for(var i = 0 ;i < pzList.length ;i++){
            var gxbmsl = {name:pzList[i].mc,value:createRandom(1)[0]};
            qqsl.push(gxbmsl);
        }
        return qqsl;
    }

    function getDyqs(){
        if(dyqs.length > 0){
            return dyqs;
        }
        for(var i = 0 ;i < pzList.length ;i++){
            var gxbmsl = {name:pzList[i].mc,type:"line",data:createRandom(7)};
            dyqs.push(gxbmsl);
        }
        return dyqs;
    }

    function createRandom(num){
        var min = 1;
        var max = 200;
        var arr=[];
        for(var i=min;i<=max;i++){
            arr.push(i);
        }
        arr.sort(function(){
            return 0.5-Math.random();
        });
        arr.length=num;
        return arr;
    }

    // 保存颜色按钮
    $("#saveColor").click(function(){
        addModel();
        $.ajax({
            url: "../bdcDsfLog/saveyspz",
            type: "POST",
            async: false,
            data: {yspzJson: JSON.stringify(pzList)},
            success: function (data) {
                layer.msg("保存成功");
                removeModal();
            },
            error: function (err) {
                layer.alert("保存失败");
                removeModal();
            }
        });
    });
});