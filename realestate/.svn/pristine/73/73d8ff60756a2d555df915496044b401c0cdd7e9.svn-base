/**
 * 综合查询界面各种证明打印统计js
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var formSelectsVar;

layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate;
    formSelectsVar = layui.formSelects;

    $('.all-content .layui-none').removeClass('bdc-hide');
    function initChart(data) {
        addModel();
        if (typeof Object.assign != 'function') {
            Object.assign = function (target) {
                'use strict';
                if (target == null) {
                    throw new TypeError('Cannot convert undefined or null to object');
                }

                target = Object(target);
                for (var index = 1; index < arguments.length; index++) {
                    var source = arguments[index];
                    if (source != null) {
                        for (var key in source) {
                            if (Object.prototype.hasOwnProperty.call(source, key)) {
                                target[key] = source[key];
                            }
                        }
                    }
                }
                return target;
            };
        }

        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/sjpt/xxgxzlpf",
            type: 'POST',
            data: {"kssj":data.kssj , "jssj":data.jzsj},
            dataType: "json",
            success: function (res) {
                console.info(res);
                $('.all-content .layui-none').addClass('bdc-hide');
                $('.all-content #chart').addClass('bdc-hide');
                if(!isNotBlank(res)){
                    $('.all-content .layui-none').removeClass('bdc-hide');
                }else{
                    $('.all-content #chart').removeClass('bdc-hide');
                    // 设置要展示的数据
                    var yData = new Array();
                    var fzlData = new Array();
                    yData.push('查询量');
                    yData.push('下发量');
                    yData.push('上报量');
                    fzlData.push(isNotBlank(res.cxl)? res.cxl:0);
                    fzlData.push(isNotBlank(res.xfl)? res.xfl:0);
                    fzlData.push(isNotBlank(res.sbl)? res.sbl:0);

                    // 动态设置柱状图y轴的长度
                    var setHeight = 400;
                    $('#chart').height(setHeight);

                    var option = {
                        tooltip : {
                            trigger: 'axis',
                            axisPointer : {
                                type : 'shadow'
                            }
                        },
                        legend: {
                            show: false,
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis:  {
                            type: 'value'
                        },
                        yAxis: {
                            type: 'category',
                            boundaryGap: [0, 0.2],
                            data: yData
                        },
                        series: [{
                            'type':'bar',stack: 'sum',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'right'
                                }
                            },
                            data: fzlData,
                            itemStyle:{
                                normal:{color: '#2e7ce9'}
                            }
                        }]
                    };
                    var myChart = echarts.init(document.getElementById('chart'));
                    myChart.setOption(option, true);
                }
                removeModal();
            }, error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }

    $('#search').click(function () {
        var searchData = getSearchData();
        if(isNotBlank(searchData)){
            initChart(searchData);
        }
    });

    $('#exportData').click(function(){
        var searchData = getSearchData();
        if(isNotBlank(searchData)){
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/sjpt/xxgxzlpf",
                type: 'POST',
                data: {"kssj":searchData.kssj , "jssj":searchData.jzsj},
                dataType: "json",
                success: function (res) {
                    console.info(res);
                    if(isNotBlank(res)){
                        // 标题
                        var showColsTitle = ["上报量","下发量","查询量"];
                        // 列内容
                        var showColsField = ["sbl","xfl","cxl"];
                        // 列宽
                        var showColsWidth = [20,20,20];
                        var exportData = new Array();
                        // for (var i = 0,j = 1 ; i < res.length; i++,j++) {
                        //     exportData[i] = res[i];
                        //     exportData[i].xh = j;
                        //     if(isNotBlank(res[i].zf) && isNotBlank(res[i].ysy)){
                        //         exportData[i].zfl = ((res[i].zf/res[i].ysy)*100).toFixed(2) + "%";
                        //     }else{
                        //         exportData[i].zfl = 0;
                        //     }
                        // }
                        exportData[0] = res;
                        exportData[0].xfl  = isNotBlank(res.xfl)? res.xfl:0;
                        exportData[0].cxl  = isNotBlank(res.cxl)? res.cxl:0;
                        exportData[0].sbl  = isNotBlank(res.sbl)? res.sbl:0;


                        $("#sheetName").val("省级查询数据量统计");
                        $("#fileName").val("省级查询数据量统计");
                        $("#cellTitle").val(showColsTitle);
                        $("#cellWidth").val(showColsWidth);
                        $("#cellKey").val(showColsField);

                        $("#data").val(JSON.stringify(exportData));
                        $("#addBorder").val(true);
                        $("#form").submit();
                    }else{
                        warnMsg("未查询到省级查询数据量统计信息");
                    }
                    removeModal();
                }, error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                }
            });
        }
    });

    // 日期控件
    laydate.render({
        elem: '#kssj',
        type: 'date',
        format: 'yyyy-MM-dd',
        done: function (value) {
            var startDate = new Date(value).getDate();
            var endDate = new Date($('#jzsj').val()).getDate();
            if (endDate < startDate) {
                warnMsg('结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#jzsj',
        type: 'date',
        format: 'yyyy-MM-dd',
        done: function (value) {
            var startDate = new Date(value).getDate();
            var endDate = new Date($('#kssj').val()).getDate();
            if (endDate > startDate) {
                warnMsg('结束时间不能小于开始时间');
            }
        }
    });


});

$(function () {
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
});


function getSearchData(){
    // 省厅接口开始时间和结束时间为必传
    if (!isNotBlank($('#kssj').val())) {
        warnMsg('开始时间不能为空');
        return;
    }
    if (!isNotBlank($('#jzsj').val())) {
        warnMsg('结束时间不能为空');
        return;
    }
    var startDate = new Date($('#kssj').val()).getTime();
    var endDate = new Date($('#jzsj').val()).getTime();
    if (endDate < startDate) {
        warnMsg('结束时间不能小于开始时间');
        return;
    }
    var searchData = {};
    $(".search").each(function (i) {
        var value = $(this).val();
        var name = $(this).attr('name');
        searchData[name] =value;
    });
    return searchData;
}