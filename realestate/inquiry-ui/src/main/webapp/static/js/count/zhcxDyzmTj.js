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

// 获取打印类型
var printTypeList = [];

layui.use(['form', 'jquery', 'laydate', 'element', 'table', 'formSelects','tree'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate;
    formSelectsVar = layui.formSelects;

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if (moduleCode) {
        setElementAttrByModuleAuthority(moduleCode);
    }
    var mrcx = $.getUrlParam('mrcx');
    // 获取部门下拉框数据
    // getTreeData('dept_treedyzm','bmmc','bmmcid',"/rest/v1.0/process/dept/allnew");
    //
    // /**
    //  * 初始化组织机构下拉控件
    //  */
    // $('.org_select_showdyzm').click(function () {
    //     if($('.org_select_showdyzm').text() == '选择'){
    //         $('.org_select_tree').css('display','block');
    //         $('.org_select_showdyzm').text('隐藏')
    //     }else{
    //         $('.org_select_showdyzm').text('选择');
    //         $('.org_select_tree').css('display','none');
    //     }
    // });
    //
    // //下拉面板宽高位置
    // (function(){
    //     var width = $('#bmmc').width + 40;
    //     $('.org_select_tree').css({'width': width, 'height':'200px', 'left':-66, 'top':35});
    // })();

    searchPrintType();
    if(isNullOrEmpty(mrcx) || mrcx=='true'){
        initChart({"tjwd": "organization"});
    }

    function searchPrintType() {
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/search/list/printType",
            type: 'GET',
            contentType: 'application/json',
            dataType: "json",
            async: false,
            success: function (res) {
                printTypeList = res;
            },
            error: function (e) {
                setTimeout(removeModal, 100);
                removeModal();
            }
        });
    };

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
        let param = Object.assign(data, {
            'printTypes': printTypeList
        });
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zhcx/dyzm",
            type: 'POST',
            data: JSON.stringify(param),
            contentType: 'application/json',
            dataType: "json",
            success: function (res) {
                if (res && res.keySet && res.valueMap) {
                    $('.all-content .layui-none').addClass('bdc-hide');
                    $('.all-content #chart').addClass('bdc-hide');
                    $('.all-content #chartRy').addClass('bdc-hide');
                    if (res.valueMap.有房无房证明.length == 0 && res.valueMap.权属证明.length == 0) {
                        // 展示无数据信息
                        $('.all-content .layui-none').removeClass('bdc-hide');
                    } else {
                        if(param.tjwd == "organization"){
                            $('.all-content #chart').removeClass('bdc-hide');
                        }else{
                            $('.all-content #chartRy').removeClass('bdc-hide');
                        }
                        var xData = [];
                        var zmlxs = printTypeList;
                        var colorList = ['#58a0f8','#afc946', '#e88787', '#e23046', '#f37418', '#f9d98f', '#7ad7d3', '#36b754', '#7fbbfd', '#f7bd24'];

                        // 设置要展示的数据
                        $.each(zmlxs, function (index, value) {
                            var data = [];
                            $.each(res.valueMap[value], function (index, value) {
                                if (value == 0) {
                                    data.push("");
                                } else {
                                    data.push(value);
                                }
                            });

                            xData.push({
                                'name': value, 'type': 'bar',barGap:0,barWidth : 30,
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'right'
                                    }
                                }, data: data
                                , itemStyle: {
                                    normal: {color: colorList[index]}
                                }
                            })
                        });

                        // 动态设置柱状图y轴的长度
                        var setHeight = 60;
                        var autoHeight = 0;
                        if (res.keySet.length > 2) {
                            autoHeight = res.keySet.length * setHeight*2;
                            if(param.tjwd == "organization"){
                                $('#chart').height(autoHeight);
                            }else{
                                $('#chartRy').height(autoHeight);
                            }
                        }

                        if(autoHeight < 180) {
                            autoHeight = 180;
                        }

                        var option = {
                            tooltip: {
                                trigger: 'axis',
                                axisPointer: {
                                    type: 'shadow'
                                }
                            },
                            legend: {
                                data: zmlxs
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
                                name: '统计维度',
                                nameTextStyle: {
                                    fontSize: 14,
                                    color: '#333'
                                },
                                axisLabel: {
                                    interval: 0,
                                    textStyle: {
                                        fontSize: 12,
                                        color: '#333'
                                    }
                                },
                                boundaryGap: [0, 0.2],
                                data: res.keySet,
                                axisLabel:{ interval:0 }
                            },
                            series: xData
                        };
                        if(param.tjwd == "organization"){
                            var myChart = echarts.init(document.getElementById('chart'),null, {height: autoHeight});
                            myChart.setOption(option, true);
                        }else{
                            var myChart = echarts.init(document.getElementById('chartRy'),null, {height: autoHeight});
                            myChart.setOption(option, true);
                            myChart.resize({height: autoHeight});
                        }

                    }
                } else {
                    warnMsg("未查询到打印证明日志！");
                }
                removeModal();
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }

    $('#search').click(function () {
        var startDate = new Date($('#kssj').val()).getTime();
        var endTime = new Date($('#jzsj').val()).getTime();
        if (endTime < startDate) {
            warnMsg('结束时间不能小于开始时间');
            return;
        }

        var searchData = {}, count = 0;
        $(".search").each(function (i) {
            if (/*$(this).attr("id") != "bmmc" && */$(this).attr("id") != "rymc") {
                var value = $(this).val();
                var name = $(this).attr('name');
            } else {
                var value = formSelectsVar.value($(this).attr('xm-select'), 'name').join(",");
                var name = $(this).attr("id");
            }
            if (!isNullOrEmpty(value)) {
                count += 1;
            }
            searchData[name] = value;
        });
        if (count == 0) {
            warnMsg("请输入查询条件!");
            return;
        }

        var tjwd = $("#tjwd").val();
        if (isNullOrEmpty(tjwd)) {
            tjwd = "organization";
        }
        searchData.tjwd = tjwd;
        initChart(searchData);
    });

    // 切换不同统计维度
    form.on('select(tjwd)', function (data) {
        var startDate = new Date($('#kssj').val()).getTime();
        var endTime = new Date($('#jzsj').val()).getTime();
        if (endTime < startDate) {
            warnMsg('结束时间不能小于开始时间');
            return;
        }

        var searchData = {};
        $(".search").each(function (i) {
            if ($(this).attr("id") != "rymc") {
                var value = $(this).val();
                var name = $(this).attr('name');
            } else {
                var value = formSelectsVar.value($(this).attr('xm-select'), 'name').join(",");
                var name = $(this).attr("id");
            }
            searchData[name] = value;
        });

        var tjwd = $("#tjwd").val();
        if (isNullOrEmpty(tjwd)) {
            tjwd = "organization";
        }
        searchData.tjwd = tjwd;
        initChart(searchData);
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
    //                     value: item.id
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

    function getTreeData(treeid,inputShowId,inputId,url) {
        layui.use('tree', function(){
            var tree = layui.tree
            tree.render({
                elem: '#'+treeid,
                data: getData(),
                id: treeid,
                showCheckbox: true,     //是否显示复选框
                onlyIconControl: true,
                oncheck: function(obj){
                    var self = this;
                    this.titles = []
                    this.id = [];
                    var treecheckdata = tree.getChecked(treeid);
                    getCheckedId(treecheckdata);
                    // 获取选中节点的id,遍历树形列表去获取每一级的id
                    function getCheckedId(jsonObj) {
                        var this_ = this;
                        jsonObj.forEach(function(item, index,jsonObj){
                            self.titles.push(item.title);
                            self.id.push(item.id);
                            if(item.children!=[]){
                                getCheckedId(item.children);//递归实现遍历每一层级数据
                            }
                        });
                        return self.titles;
                    }
                    $('#'+inputShowId).val(this.titles);
                    $('#'+inputId).val(this.id);
                    //部门人员联动
                    getBmryList(this.id.join(","));
                }
            });
        });

        function getData() {
            var data = [];
            $.ajax({
                url: getContextPath() +url,    //后台数据请求地址
                type: "GET",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                async: false,
                success: function (resut) {
                    data = resut;
                }
            });
            return data;
        }
    }

    //组织单选
    formSelectsVar.on('selectBmmc', function (id, vals, val, isAdd, isDisabled) {
        setTimeout(function () {
            getBmryList(formSelectsVar.value('selectBmmc', 'val').join(","));
        }, 1000)
    });

    function getBmryList(bmid) {
        // 先清空
        $("#rymc").empty();
        form.render("select");

        var url = '/realestate-inquiry-ui/gzltj/bmusers';
        $.ajax({
            url: url,
            type: 'GET',
            data: {orgId: bmid},
            success: function (data) {
                if (data && data.length > 0) {
                    var tempSelect = [];
                    data.forEach(function (d) {
                        tempSelect.push({
                            name: d.MC,
                            value: d.DM
                        })
                    });
                    formSelectsVar.value("selectBjry", []);
                    formSelectsVar.data('selectBjry', 'local', {
                        arr: tempSelect
                    });
                } else {
                    formSelectsVar.data('selectBjry', 'local', {
                        arr: rymcSelList
                    });
                }
            }
        })
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