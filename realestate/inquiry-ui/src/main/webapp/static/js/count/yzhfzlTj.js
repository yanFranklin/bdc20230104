/**
 * 综合查询界面各种证明打印统计js
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var formSelectsVar;
// 部门下拉数组
var bmmcSelList = [];
// 人员下拉数组
var rymcSelList = [];

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
            url: "/realestate-inquiry-ui/rest/v1.0/yzh/fjltj",
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json',
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
                    var tjwd = data.tjwd;
                    var yData = new Array();
                    var fzlData = new Array();
                    $.each(res, function(index, value){
                        if(tjwd == "user"){
                            yData.push(value.syr);
                        }else{
                            yData.push(value.sybmmc);
                        }
                        fzlData.push(isNotBlank(value.zf)? value.zf:0);
                    });
                    // 动态设置柱状图y轴的长度
                    var setHeight = 40;
                    var autoHeight = res.length * setHeight;
                    if(autoHeight < 180) {
                        autoHeight = 180;
                    }
                        $('#chart').height(autoHeight);

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
                            barWidth : 30,
                            data: fzlData,
                            itemStyle:{
                                normal:{color: '#58a0f8'}
                            }
                        }]
                    };
                    var myChart = echarts.init(document.getElementById('chart'));
                    myChart.setOption(option, true);
                    myChart.resize();
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
                url: "/realestate-inquiry-ui/rest/v1.0/yzh/fjltj",
                type: 'POST',
                data: JSON.stringify(searchData),
                contentType: 'application/json',
                dataType: "json",
                success: function (res) {
                    console.info(res);
                    if(isNotBlank(res)){
                        // 标题
                        var showColsTitle = ["序号","领用量","名称","作废量","已使用","作废率"];
                        // 列内容
                        var showColsField = ["xh","lyl","syr","zf","ysy","zfl"];
                        // 列宽
                        var showColsWidth = [7,20,20,20,20,20];
                        var exportData = new Array();
                        for (var i = 0,j = 1 ; i < res.length; i++,j++) {
                            exportData[i] = res[i];
                            exportData[i].xh = j;
                            if(isNotBlank(res[i].zf) && isNotBlank(res[i].ysy)){
                                exportData[i].zfl = ((res[i].zf/res[i].ysy)*100).toFixed(2) + "%";
                            }else{
                                exportData[i].zfl = 0;
                            }
                        }
                        $("#sheetName").val("废证量统计信息");
                        $("#fileName").val("废证量统计");
                        $("#cellTitle").val(showColsTitle);
                        $("#cellWidth").val(showColsWidth);
                        $("#cellKey").val(showColsField);

                        $("#data").val(JSON.stringify(exportData));
                        $("#addBorder").val(true);
                        $("#form").submit();
                    }else{
                        warnMsg("未查询到废证量统计信息");
                    }
                    removeModal();
                }, error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                }
            });
        }
    });

    // 切换不同统计维度
    form.on('select(tjwd)', function(data){
        var searchData = getSearchData();
        if(isNotBlank(searchData)){
            initChart(searchData);
        }
    });

    // 日期控件
    laydate.render({
        elem: '#kssj',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#jzsj').val()).getTime();
            if (endTime < startDate) {
                warnMsg('结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#jzsj',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#kssj').val()).getTime();
            if (endTime > startDate) {
                warnMsg('结束时间不能小于开始时间');
            }
        }
    });

    // 获取部门
    // var bmList = [];
    // $.ajax({
    //     url: '/realestate-inquiry-ui/bdczd/org/list',
    //     type: "GET",
    //     dataType: "json",
    //     success: function (data) {
    //         if (data) {
    //             bmList = data;
    //             $.each(data, function (key, item) {
    //                 //$('#bmmc').append('<option value="' + item.name + '">' + item.name + '</option>');
    //                 bmmcSelList.push({
    //                     name: item.name,
    //                     value: item.name
    //                 })
    //             });
    //             formSelectsVar.data('selectBmmc', 'local', {
    //                 arr: bmmcSelList
    //             });
    //         }
    //     }
    // });

    // 默认获取所有用户
    $.ajax({
        url: '/realestate-inquiry-ui/bdczd/user/list',
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#rymc').append('<option value="' + item.alias + '">' + item.alias + '</option>');
                    rymcSelList.push({
                        name: item.alias,
                        value: item.alias
                    })
                });
                formSelectsVar.data('selectBjry', 'local', {
                    arr: rymcSelList
                });
            }
        }
    });

    // 选择部门时候动态选择对应人员
    // 改用多选，暂时先不做该功能
    // form.on('select(bmselect)', function(data){
    //     if(bmList){
    //         $.each(bmList, function (index, item) {
    //             if(item.name == data.value){
    //                 getBmryList(item.id);
    //             }
    //         });
    //     }
    // });

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
    var startDate = new Date($('#kssj').val()).getTime();
    var endTime = new Date($('#jzsj').val()).getTime();
    if (endTime < startDate) {
        warnMsg('结束时间不能小于开始时间');
        return;
    }

    var searchData = {}, count = 0;
    $(".search").each(function (i) {
        if($(this).attr("id") != "bmmc" && $(this).attr("id") != "rymc"){
            var value = $(this).val();
            var name = $(this).attr('name');
        }else{
            var value = formSelectsVar.value($(this).attr('xm-select'), 'val').join(",");
            var name = $(this).attr("id");
        }
        if(!isNullOrEmpty(value)){
            count += 1;
        }
        searchData[name] =value;
    });
    if(count == 0){
        warnMsg("请输入查询条件!");
        return;
    }

    var tjwd = $("#tjwd").val();
    if(isNullOrEmpty(tjwd)){
        tjwd = "user";
    }
    searchData.tjwd = tjwd;
    return searchData;
}