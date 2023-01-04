/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 初始化tab页及其表格，初始化图表，注：此处需要根据功能页所在包里的位置引入对应的echarts插件-->
 */
/** 定义全局变量*/
var yData = [];
var xData = [];
// 此处填写接口地址
var mxDataUrl = "";
var tbDataUrl = "";

layui.use(['form', 'jquery', 'element', 'table'], function () {

    var $ = layui.jquery,
        form = layui.form,
        element = layui.element;
    table = layui.table;

    // tab页切换事件
    element.on('tab(tabFilter)', function () {
        var tabName = this.getAttribute('lay-id');
        //切换统计
        tabChangeEvent(tabName);
    });
    /**
     * @param tabName 当前tab页的id
     * @description tab页点击事件
     */
    function tabChangeEvent(tabName) {
        tabId = tabName;
        //图表分析
        if (tabName == "sjzs") {
            changeChart();
        } else if (tabName == "xxxx") {
            // 重新请求
            layui.form.render("select")
            table.resize('pageTablemx');
        }
    }
    /**
     * @description 表格重新加载表格明细
     */
    function reloadTjlx() {
        table.reload("pageTablemx", {
            url: mxDataUrl,
            where: {
            },
            method: 'get',
            page: {
                curr: 1  //重新从第 1 页开始
            },

            done: function (res) {
                currentPageData = res.data;
            }
        });
    }
    /**
     * @param queryData 入参
     * @param showEchart 是否显示图表变量 true为显示 ，false则相反
     * @description 初始化图表分析
     */
    function initSjzsTab(queryData, showEchart) {
        //此处需要引用common.js才可使用遮罩，暂时先屏蔽
        // addModel();
        layui.form.render("select");
        //此处填写图表标题
        var barTitle = "交接量";
        $.ajax({
            url: tbDataUrl,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            async: true,
            data: JSON.stringify(queryData),
            success: function (data) {
                //此处需要引用common.js才可使用，暂时先屏蔽
                // removeModal();
                xData=[];//坐标系名称
                yData=[];//具体数值

                data= [{"lxmc":"交接单类型1","sl":"904"},{"lxmc":"交接单类型2","sl":"4",},{"lxmc":"交接单类型3","sl":"20"},{"lxmc":"交接单类型4","sl":"977"},{"lxmc":"交接单类型5","sl":"51"},{"lxmc":"交接单类型6","sl":"2"},{"lxmc":"交接单类型7","sl":"20"},{"lxmc":"交接单类型8","sl":"4"}]
                if(data){
                    //此处填写对于接口返回的数据处理
                    data.forEach(function (d) {
                        if(d.lxmc){
                            yData.push(d.lxmc)
                            xData.push(d.sl)
                        }
                    })
                }
                layui.form.render("select");
                option = {
                    tooltip: {
                        trigger: 'axis'
                    },
                    title: {
                        show: true,
                        text: barTitle,
                        x: 'center',
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
                    //图例样式
                    legend: {
                        show: false,//默认不显示图例
                        data: ["次数"],
                        top: "10",
                        right: "50"
                    },
                    grid: {
                        bottom: '30',
                        top: '50px',
                        containLabel: true,
                        left: 30,
                        right: 110
                    },
                    //横轴的样式
                    xAxis: {
                        type: 'value',
                        name: '单位：（次）',
                        nameTextStyle: {
                            fontSize: 14,
                            color: '#333'
                        },
                        axisLabel: {
                            textStyle: {
                                fontSize: 12,
                                color: '#333'
                            }
                        }
                    },
                    //纵轴的样式
                    yAxis: {
                        type: 'category',//类型
                        data: yData,//数据
                        inverse: true,
                        name: "统计维度",//纵轴标题
                        nameLocation: 'start',
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
                        }
                    },
                    series: [
                        {
                            name: '次数',
                            type: 'bar',
                            stack: 'sum',
                            barWidth : 40, //柱图宽度
                            itemStyle: {
                                normal: {
                                    color: "#58a0f8"
                                }
                            },
                            label: {
                                normal: {
                                    show: true,
                                    position: 'right'
                                }
                            },
                            data: xData
                        }
                    ]
                };
                if(showEchart){
                    changeChart();
                }
                //此处需要引用common.js才可使用遮罩，暂时先屏蔽
                // setTimeout(removeModal, 100);
            },
            error: function (e) {
                //此处需要引用common.js才可使用遮罩，暂时先屏蔽
                // setTimeout(removeModal, 100);
            }
        });
    }
    /**
     * @description 图表计算样式方法
     */
    function changeChart() {
        // 动态设置柱状图y轴的长度
        var setHeight = 60;
        var autoHeight = 0;
        if (yData.length > 2) {
            autoHeight = yData.length* setHeight;
            if(autoHeight < 180) {
                autoHeight = 180;
            }
            $('#chartdemo').height(autoHeight);
        }
        //设置柱状图x轴的长度
        yData.forEach(function(item,index){
            if(item.length>10){
                $('#djlxChart').width(1000);
            }else{
                $('#djlxChart').width(600);
            }
        })

        echarts.init(document.getElementById("chartdemo")).dispose();
        var myChart = echarts.init(document.getElementById("chartdemo"));
        //使用制定的配置项和数据显示图表
        myChart.setOption(option);
        myChart.resize();
    }

    var tableData1 =  [
        {
            "jjdh": '2016006125',
            "jjdlx": "注销交接单",
            "zfr": "刘天香",
            "zfsj": "2016-10-14 19:17",
            "zfks": "发证科",
            "jssj": "2016-10-14 20:15",
            "jsks": "接收科",
            "jjdzt": "已接收"
        },{
            "jjdh": '2016006125',
            "jjdlx": "注销交接单",
            "zfr": "刘天香",
            "zfsj": "2016-10-14 19:17",
            "zfks": "发证科",
            "jssj": "2016-10-14 20:15",
            "jsks": "接收科",
            "jjdzt": "已接收"
        },{
            "jjdh": '2016006125',
            "jjdlx": "注销交接单",
            "zfr": "刘天香",
            "zfsj": "2016-10-14 19:17",
            "zfks": "发证科",
            "jssj": "2016-10-14 20:15",
            "jsks": "接收科",
            "jjdzt": "已接收"
        },{
            "jjdh": '2016006125',
            "jjdlx": "注销交接单",
            "zfr": "刘天香",
            "zfsj": "2016-10-14 19:17",
            "zfks": "发证科",
            "jssj": "2016-10-14 20:15",
            "jsks": "接收科",
            "jjdzt": "已接收"
        },{
            "jjdh": '2016006125',
            "jjdlx": "注销交接单",
            "zfr": "刘天香",
            "zfsj": "2016-10-14 19:17",
            "zfks": "发证科",
            "jssj": "2016-10-14 20:15",
            "jsks": "接收科",
            "jjdzt": "已接收"
        },{
            "jjdh": '2016006125',
            "jjdlx": "注销交接单",
            "zfr": "刘天香",
            "zfsj": "2016-10-14 19:17",
            "zfks": "发证科",
            "jssj": "2016-10-14 20:15",
            "jsks": "接收科",
            "jjdzt": "已接收"
        },{
            "jjdh": '2016006125',
            "jjdlx": "注销交接单",
            "zfr": "刘天香",
            "zfsj": "2016-10-14 19:17",
            "zfks": "发证科",
            "jssj": "2016-10-14 20:15",
            "jsks": "接收科",
            "jjdzt": "已接收"
        },
        {
            "jjdh": '2016006125',
            "jjdlx": "注销交接单",
            "zfr": "刘天香",
            "zfsj": "2016-10-14 19:17",
            "zfks": "发证科",
            "jssj": "2016-10-14 20:15",
            "jsks": "接收科",
            "jjdzt": "已接收"
        },{
            "jjdh": '2016006125',
            "jjdlx": "注销交接单",
            "zfr": "刘天香",
            "zfsj": "2016-10-14 19:17",
            "zfks": "发证科",
            "jssj": "2016-10-14 20:15",
            "jsks": "接收科",
            "jjdzt": "已接收"
        },
        {
            "jjdh": '2016006125',
            "jjdlx": "注销交接单",
            "zfr": "刘天香",
            "zfsj": "2016-10-14 19:17",
            "zfks": "发证科",
            "jssj": "2016-10-14 20:15",
            "jsks": "接收科",
            "jjdzt": "已接收"
        },{
            "jjdh": '2016006125',
            "jjdlx": "注销交接单",
            "zfr": "刘天香",
            "zfsj": "2016-10-14 19:17",
            "zfks": "发证科",
            "jssj": "2016-10-14 20:15",
            "jsks": "接收科",
            "jjdzt": "已接收"
        }];
    var tableData2 =  [
        {
            "slh": 'F1200925925',
            "xdah": "000222222",
            "djlx": "抵押权登记＞抵押权登记＞设立",
            "fwdz": "百花园47栋125室",
            "sqr": "王丽",
            "cqr": "王丽"
        }];
    var tableData3 =  [
        {
            "slh": 'F1200925925',
            "xdah": "000222222",
            "djlx": "抵押权登记＞抵押权登记＞设立",
            "fwdz": "百花园47栋125室",
            "sqr": "王丽",
            "cqr": "王丽"
        },{
            "slh": 'F1200925925',
            "xdah": "000222222",
            "djlx": "抵押权登记＞抵押权登记＞设立",
            "fwdz": "百花园47栋125室",
            "sqr": "王丽",
            "cqr": "王丽"
        },{
            "slh": 'F1200925925',
            "xdah": "000222222",
            "djlx": "抵押权登记＞抵押权登记＞设立",
            "fwdz": "百花园47栋125室",
            "sqr": "王丽",
            "cqr": "王丽"
        },{
            "slh": 'F1200925925',
            "xdah": "000222222",
            "djlx": "抵押权登记＞抵押权登记＞设立",
            "fwdz": "百花园47栋125室",
            "sqr": "王丽",
            "cqr": "王丽"
        },{
            "slh": 'F1200925925',
            "xdah": "000222222",
            "djlx": "抵押权登记＞抵押权登记＞设立",
            "fwdz": "百花园47栋125室",
            "sqr": "王丽",
            "cqr": "王丽"
        }];


    // 查询按钮点击事件
    form.on('submit(search)', function (data) {
        formSubmitEvent(data);
        return false;
    });
    // 初始化查询
    var queryParams = {};
    $(".layui-form").serializeArray().forEach(function (item, index) {
        queryParams[item.name] = item.value;
    });
    initSjzsTab(queryParams,true)
    table.render({
        elem: '#pageTablemx',
        title: '表格demo',
        even: true,
        toolbar: '#toolbarDemo',
        cols: [[
            {type: 'checkbox', width:50, fixed: 'left'},
            {field:'jjdh', title:'交接单号', width:200, sort: true},
            {field:'jjdlx', title:'交接单类型', width:200},
            {field:'zfr', title:'转发人', width:100},
            {field:'zfsj', title:'转发时间',width:200},
            {field:'zfks', title:'转发科室',width:100},
            {field:'jsr', title:'接收人'},
            {field:'jssj', title:'接收时间'},
            {field:'jsks', title:'接收科室'},
            {field:'jjdzt', title:'交接单状态'}
        ]],
        data: tableData1,
        page: true,
        done: function () {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

            if($('.layui-table-body>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
            }
        }
    });
    reloadTjlx();
    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(pageTablemx)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;

        $.each(data, function (i, v) {
            if (obj.checked) {
                //.增加已选中项
                layui.sessionData('checkedData', {
                    key: v.xmid, value: v //此处填写主键id 暂时用xmid作为demo
                });
            } else {
                //.删除
                layui.sessionData('checkedData', {
                    key: v.xmid, remove: true///此处填写主键id 暂时用xmid作为demo
                });
            }
        });
        var checkedData = layui.sessionData('checkedData');
        var arrcheck = Object.keys(checkedData);
    });
    table.on('toolbar(pageTablemx)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        exportUrl(checkStatus.data, obj.config);

    });
});

function exportUrl(data,obj){
    var url = obj.url;
    var paramData = obj.where;
    $.ajax({
        url: url,
        type: "get",
        data: paramData,
        success: function (data) {
            if(data.content){
                exportExcel(data.content,obj);
            }else{
                exportExcel(data,obj);
            }
        }
    });
}
/**
 * @description 导出表格方法
 */
function exportExcel(d, obj){
    var cols = obj.cols[0];
    var checkedData = layui.sessionData('checkedData');
    if ($.isEmptyObject(checkedData)) {
        layer.alert("<div style='text-align: center'>请选择需要导出Excel的数据行！</div>", {title: '提示'});
        return;
    }
    // 标题
    var showColsTitle = [];
    // 列内容
    var showColsField = [];
    // 列宽
    var showColsWidth = [];
    for(var index in cols){
        if(cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar){
            showColsTitle.push(cols[index].title);
            showColsField.push(cols[index].field);
            if(cols[index].width > 0){
                showColsWidth.push(parseInt(cols[index].width / 100 * 15));
            }else if(cols[index].minWidth > 0){
                showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
            }else{
                showColsWidth.push(300 / 100 * 15);
            }
        }
    }

    var data = new Array();
    $.each(checkedData, function(key, value){
        data.push(value);
    })
    for(var i = 0; i < data.length; i++){
        data[i].xh   = i + 1;
    }
    // 设置Excel基本信息
    $("#fileName").val('demo');//此处填写导出的excel文件名
    $("#sheetName").val('demo');//此处填写导出的excel表格名
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#data").val(JSON.stringify(data));
    $("#form").submit();
}