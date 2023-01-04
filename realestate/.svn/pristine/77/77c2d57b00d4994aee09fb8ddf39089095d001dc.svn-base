/**
 * Created by Administrator on 2019/5/5.
 */
layui.use(['jquery', 'element', 'table', 'laytpl'], function () {
    var $ = layui.jquery,
        element = layui.element,
        table = layui.table,
        laytpl = layui.laytpl;
    $(function () {
        //点击退出
        /*$('.bdc-quit').on('click', function () {
            var logoutIndex = layer.confirm('您确定要退出吗？', {
                title: '提示',
                btn: ['确定', '取消'] //按钮
            }, function () {
                layer.close(logoutIndex);
                $.post("/realestate-e-certificate/logout",function(){
                });
                var userAgent = navigator.userAgent;
                if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") !=-1) {
                    window.location.href="about:blank";
                } else {
                    window.opener = null;
                    window.open("", "_self");
                    window.close();
                }
            });
        });*/

        //修改密码
        /*$('.bdc-update').on('click', function () {
            var logoutIndex = layer.confirm('您确定要修改密码吗？', {
                title: '提示',
                btn: ['确定', '取消'] //按钮
            }, function () {
                layer.close(logoutIndex);
                var update = layer.open({
                    type: 2,
                    title: "修改密码",
                    maxmin: true,
                    area: ['70%', '100%'],
                    fixed: false, //不固定
                    content: '/realestate-e-certificate/toUpdatePassword'
                    , end: function (index, layero) {
                        //refreshView();
                        return false;
                    }
                });
            });
        });*/

        // 侧边栏动态渲染

       /*var asideData = [
           {
               name: '证照管理',
               childTree: [
                   {
                       childTree: [],
                       name: '证照台账管理',
                       mark: 'basic',
                       url: '/realestate-e-certificate/toZzxxList'
                   },
                   {
                       childTree: [],
                       name: '证照秘钥管理',
                       mark: 'basic',
                       url: '/realestate-e-certificate/toDzzzTokenList'
                   },
                   {
                       childTree: [],
                       name: '证照同步管理',
                       mark: 'basic',
                       url: '/realestate-e-certificate/js/toZzxxSyncList'
                   },
                   {
                       childTree: [],
                       name: '证照日志管理',
                       mark: 'basic',
                       url: '/realestate-e-certificate/log/toZzxxLogList'
                   },
                   {
                       childTree: [],
                       name: '南通证照管理_银行',
                       mark: 'basic',
                       url: '/realestate-e-certificate/Nt/toZzxxNtYhList'
                   }
               ]
           }
       ];*/
        /*$.ajax({
            url: "/realestate-e-certificate/getMenusByUserInfo",
            type: "GET",
            async: false,
            success: function (newData) {
                if (newData) {
                    asideData = newData;
                }
            },
            error: function (xhr, status, error) {
                //delAjaxErrorMsg(xhr)
            }
        });*/
        /*var getAsideTpl = asideTpl.innerHTML
            , getAsideView = document.getElementById('LAY-system-side-menu');
        laytpl(getAsideTpl).render(asideData, function (html) {
            getAsideView.innerHTML = html;
        });
        element.render('nav', 'layadmin-system-side-menu');

        //1. 监听侧边栏点击
        $('.layui-side-menu .layui-nav-item .layui-nav-child').on('click', function () {
            $('.bdc-four-list').hide();
        });
        $('.layui-side-menu .bdc-mime').on('click', function () {
            $('.bdc-four-list').hide();
        });
        //1.1 监听三级菜单点击
        $('.layui-side-menu .layui-nav').on('click', '.layui-nav-child .layui-nav-child a', function (e) {
            $('.bdc-four-list').hide();
            $(this).siblings().css('top', $(this).offset().top).toggle();
        });
        //1.2 点击四级菜单点击
        $('.layui-side').on('click', '.bdc-four-list p', function () {
            $(this).parent().css('display', 'none');
        });

        //2. 调整左侧滑动条的位置
        $('.layui-side-menu .layui-nav-item>a').on('click', function () {
            $('.bdc-four-list').hide();
            $('.layui-nav-bar').css({'height': '50px', 'opacity': 1, 'top': $(this).parent().index() * 50});
        });

        $('#flexible').click(function () {
            setTimeout(function () {
                renderCurrentTable();
            },300);
        });

        //3. 消息列表相关
        //3.1 消息列表高度
        $('.bdc-msg-child').height($('.layui-body').height());
        $('.bdc-msg-child .layui-tab-item').height($('.layui-body').height() - 116);
        //3.2 点击消息列表中的‘×’，关闭
        $('.bdc-msg-title .layui-icon-close').on('click', function () {
            $('.bdc-msg-child').removeClass('layui-show');
        });


        //点击刷新图标
        $('.bdc-refresh-frame').on('click', function () {
            var nowTabIndex = $('.layui-body .layadmin-tabsbody-item.layui-show').index();
            var $frame = $('.layui-body .layadmin-tabsbody-item.layui-show iframe');
            if (nowTabIndex != 0) {
                if (nowTabIndex == 1) {
                    window.location.reload();
                } else {
                    $frame.attr('src', $frame.attr('src'));
                }
            }
        });

        // 监听tab切换
        $('.bdc-outer-tab').on('click','.layui-tab-title li',function () {
            if($(this).attr('data-refresh') == 1){
                setTimeout(function () {
                    $('.bdc-refresh-frame').click();
                },500);
                $(this).removeAttr('data-refresh');
            }
        });*/

        //证照库数量统计
        $.ajax({
            url: "/realestate-e-certificate/countDzzzZzslZzk",
            type: "POST",
            success: function (newData) {
                if (null != newData && "" != newData && undefined != newData) {
                    $("#zzkZj").html(newData.zzkZj);
                    $("#zzkZs").html(newData.zzkZs);
                    $("#zzkZms").html(newData.zzkZms);
                }
            },
            error: function (xhr, status, error) {
                //delAjaxErrorMsg(xhr)
            }
        });

        //目录库数量统计
        $.ajax({
            url: "/realestate-e-certificate/countDzzzZzslMlk",
            type: "POST",
            success: function (newData) {
                if (null != newData && "" != newData && undefined != newData) {
                    $("#mlkZj").html(newData.mlkZj);
                    $("#mlkZs").html(newData.mlkZs);
                    $("#mlkZms").html(newData.mlkZms);
                }
            },
            error: function (xhr, status, error) {
                //delAjaxErrorMsg(xhr)
            }
        });

        //渲染证照使用情况
        //证照使用次数
        //默认渲染周
        var xDataDay = [];
        var yDataDay = [];
        var xDataMonth = [];
        var yDataMonth = [];
        var xDataYear = [];
        var yDataYear = [];
        //renderChar('syCharWeek',xDataDay,yDataDay);
        getCharUseData('syCharWeek');

        //监听选项卡切换
        var monthIndex = 0,yearIndex = 0;
        element.on('tab(dateChangeFilter)', function(data){
            switch (data.index){
                case 1:
                    //月
                    monthIndex++;
                    if(monthIndex == 1){
                        getCharUseData('syCharMonth');
                        //renderChar('syCharMonth',xDataMonth,yDataMonth);
                    }
                    break;
                case 2:
                    //年
                    yearIndex++;
                    if(yearIndex == 1){
                        getCharUseData('syCharYear');
                        //renderChar('syCharYear',xDataYear,yDataYear);
                    }
                    break;
            }
        });
        //折线图
        function renderChar(id,xData,yData) {
            var myChart = echarts.init(document.getElementById(id));
            var option = {
                title: {
                    text: '使用次数',
                    textStyle: {
                        color: '#333',
                        fontWeight: '400',
                        fontSize: '14'
                    },
                    x: 'center',
                    top: '5px'
                },
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    top: '40px',
                    left: '20px',
                    right: '20px',
                    bottom: '20px',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    splitLine:{
                        show: true
                    },
                    data: xData
                },
                yAxis: {
                    type: 'value',
                    splitLine:{
                        show: true
                    }
                },
                series: [
                    {
                        name:'使用次数',
                        type:'line',
                        data: yData,
                        itemStyle : {
                            normal : {
                                color:'#69aaf9',
                                lineStyle:{
                                    color:'#69aaf9'
                                }
                            }
                        }
                    }
                ]
            };
            myChart.setOption(option);
        }

        function getCharUseData(id){
            $.ajax({
                url: "/realestate-e-certificate/countDzzzJzjxxUse",
                type: "POST",
                data: {"way" :id},
                success: function (newData) {
                    if (null != newData && "" != newData) {
                        var obj = JSON.parse(newData);
                        if (null != obj) {
                            var xData = "";
                            var yData = "";
                            if ("syCharWeek" == id) {
                                xData = obj.xDataDay;
                                yData = obj.yDataDay;
                            } else if ("syCharYear" == id) {
                                xData = obj.xDataYear;
                                yData = obj.yDataYear;
                            } else if ("syCharMonth" == id) {
                                xData = obj.xDataMonth;
                                yData = obj.yDataMonth;
                            }
                            renderChar(id,xData,yData);
                        }
                    }
                },
                error: function (xhr, status, error) {
                    //delAjaxErrorMsg(xhr)
                }
            });
        }

        //证照使用部门
        var syBar = echarts.init(document.getElementById('syBar'));
        var xDataUseDepartment = [];
        var yDataUseDepartment = [];
        renderBar(xDataUseDepartment,yDataUseDepartment);
        function renderBar(xData,yData) {
            //var myBar = echarts.init(document.getElementById(id));
            var barOption = {
                title: {
                    text: '使用部门TOP5',
                    textStyle: {
                        color: '#333',
                        fontWeight: '400',
                        fontSize: '14'
                    },
                    x: 'center',
                    top: '5px'
                },
                grid: {
                    top: '40px',
                    left: '20px',
                    right: '40px',
                    bottom: '20px',
                    containLabel: true
                },
                xAxis: {
                    type: 'value',
                    boundaryGap: [0, 0.01]
                },
                yAxis: {
                    type: 'category',
                    data: xData,
                    splitLine:{
                        show: true
                    }
                },
                series: [
                    {
                        name: '2019年',
                        type: 'bar',
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true
                                },
                                color: function(params) {
                                    var colorList = ['#9fd9ea','#afcacd','#c8c4b9','#e5b362','#f1965d'];
                                    return colorList[params.dataIndex]
                                }
                            }
                        },
                        data: yData
                    }
                ]
            };
            syBar.setOption(barOption);
        }
        $.ajax({
            url: "/realestate-e-certificate/countDzzzJzjxx",
            type: "POST",
            success: function (newData) {
                if (null != newData && "" != newData) {
                    var obj = JSON.parse(newData);
                    if (null != obj) {
                        xDataUseDepartment = obj.xDataUseDepartment;
                        yDataUseDepartment = obj.yDataUseDepartment;
                        renderBar(xDataUseDepartment,yDataUseDepartment);
                    }

                }
            },
            error: function (xhr, status, error) {
                //delAjaxErrorMsg(xhr)
            }
        });

        //各地区证照数量分布图
        var areaBar = echarts.init(document.getElementById('areaBar'));
        var xAreaData = [];
        var yZmData = [];
        var yZsData = [];
        var data = [];

        renderAreaBar(xAreaData,yZmData,yZsData);
        function renderAreaBar(xData,yZmData,yZsData) {

            var areaOption = {
                title: {
                    text: '各地区证照数量分布',
                    textStyle: {
                        color: '#333',
                        fontWeight: '400',
                        fontSize: '14'
                    },
                    x: 'center',
                    top: '5px'
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    data:['证书','证明'],
                    right: '20'
                },
                grid: {
                    top: '40px',
                    left: '20px',
                    right: '20px',
                    bottom: '20px',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        splitLine: {
                            show: true
                        },
                        axisLabel : {
                            interval:0,
                            rotate:"45"
                        },
                        data : xData
                    }
                ],
                yAxis : [
                    {
                        type: 'value'
                    }
                ],
                series : [
                    {
                        name:'证明',
                        type:'bar',
                        stack: '数量',
                        itemStyle: {
                            normal: {
                                color: '#58a0f8'
                            }
                        },
                        data: yZmData
                    },
                    {
                        name:'证书',
                        type:'bar',
                        stack: '数量',
                        itemStyle: {
                            normal: {
                                color: '#44d6b5'
                            }
                        },
                        data: yZsData
                    }
                ]
            };
            areaBar.setOption(areaOption);
        }

        //各地区证照数量表格展示
        //第一个实例
        areaTable(data);
        function areaTable(data){
            table.render({
                elem: '#areaTable',
                limit: 13
                ,cols: [[
                    {field: 'id', title: '序号', width:80,align: 'center'}
                    ,{field: 'areaName', title: '行政区名称',align: 'center'}
                    ,{field: 'zs', title: '证书',align: 'center'}
                    ,{field: 'zm', title: '证明',align: 'center'}
                ]],
                data: data
            });
        }

        $.ajax({
            url: "/realestate-e-certificate/dzzzQuantitativeDistribution",
            type: "POST",
            success: function (newData) {
                if (null != newData && "" != newData) {
                    var obj = JSON.parse(newData);
                    if (null != obj) {
                        xAreaData = obj.xAreaData;
                        yZmData = obj.yZmData;
                        yZsData = obj.yZsData;
                        data = obj.data;
                        renderAreaBar(xAreaData,yZmData,yZsData);
                        areaTable(data);
                    }
                }
            },
            error: function (xhr, status, error) {
                //delAjaxErrorMsg(xhr)
            }
        });

    });
});