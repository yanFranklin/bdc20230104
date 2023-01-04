var type = GetQueryString("type");
var interfaceIds = GetQueryString("interfaceIds");
var showjkmcdx = GetQueryString("showjkmcdx");
/**
 * Created by Ypp on 2020/3/16.
 * 第三方日志查询统计js
 */
//页面标识 是一级页面还是二级页面
var pageLevel = "1";
var gxbmbzZdMap = {};
var gxbmbzColorMap = {};
var zdNames = "gxjkmc";
layui.extend({
    formSelects: '../static/lib/form-select/formSelects-v4'
});
layui.use(['form','table','laydate', 'laytpl','formSelects'], function(){
    var form = layui.form,
        $ = layui.jquery,
        table = layui.table;
        var laydate = layui.laydate;
        var laytpl = layui.laytpl;
        var formSelects = layui.formSelects;
        //初始化日期控件
        laydate.render({
            elem: '#kssj'
            , type: 'datetime'
        });
        laydate.render({
            elem: '#jssj'
            , type: 'datetime'
        });

    $(function(){
        initDiv();
        //共享部门下拉框信息获取
        var result = ajaxQuery("../bdcDsfLog/getyspzlist", null, "POST", false, "application/x-www-form-urlencoded;charset=utf-8");
        if (!isEmptyObject(result)) {
            var selectGxbmbzHtml = "";
            $.each(result,function(index,item){
                //$('#gxbmbz').append(new Option(item.DM,item.MC));
                selectGxbmbzHtml += "<option value='" + item.name + "'>" + item.mc + "</option>";
                gxbmbzZdMap[item.name] = item.mc;
                gxbmbzColorMap[item.name] = item.color;
            });
            $("select[name='gxbmbz']").append(selectGxbmbzHtml);
            form.render();
        }
        //分中心下拉框信息获取
        var result = ajaxQuery("../bdcDsfLog/queryOrgList", {}, "POST", false, "application/x-www-form-urlencoded;charset=utf-8");
        if (!isEmptyObject(result)) {
            var selectGxbmbzHtml = "";
            $.each(result,function(index,item){
                selectGxbmbzHtml += "<option value='" + item.code + "'>" + item.name + "</option>";
            });
            $("select[name='fzxmc']").append(selectGxbmbzHtml);
            form.render();
        }
        //分中心下拉框信息获取
        var result = ajaxQuery("../bdcDsfLog/queryGzldymcList", {}, "POST", false, "application/x-www-form-urlencoded;charset=utf-8");
        if (!isEmptyObject(result)) {
            var selectGzldymcHtml = "";
            $.each(result, function (index, item) {
                selectGzldymcHtml += "<option value='" + item.name + "'>" + item.name + "</option>";
            });
            $("select[name='gzldymc']").append(selectGzldymcHtml);
            form.render();
        }
        //
        function getMulZdList() {
            $.ajax({
                url: "../bdcDsfLog/zdMul",
                dataType: "json",
                data: {
                    zdNames: zdNames
                },
                async: false,
                success: function (data) {
                    zdList = $.extend({}, data);
                },
                error: function (e) {}
            });
        }
        getMulZdList();
        if (!isNullOrEmpty(zdList['gxjkmc'])) {
            var tpl = $("#DmMcTpl").html();
            laytpl(tpl).render(zdList['gxjkmc'], function (html) {
                $(".gxjkmcs").append(html);
            });
        }

        if(showjkmcdx){
            formSelects.render("selectjkmc");
            var keys = [];
            for (var i = 0; i < zdList['gxjkmc'].length; i++) {
                keys.push(zdList['gxjkmc'][i].DM)
            }
            formSelects.value("selectjkmc",keys)
            $("#jkmcsDiv").show();
        }
        //单击 上箭头 隐藏查询条件
        $('.bdc-operate-btn .layui-icon-up').on('click',function(){
            $(this).addClass('bdc-hide');
            $('.bdc-operate-btn .layui-icon-down').removeClass('bdc-hide');
            $('.bdc-search-box .layui-form').addClass('bdc-hide');
        });
        //单击 下箭头 显示查询条件
        $('.bdc-operate-btn .layui-icon-down').on('click',function(){
            $(this).addClass('bdc-hide');
            $('.bdc-operate-btn .layui-icon-up').removeClass('bdc-hide');
            $('.bdc-search-box .layui-form').removeClass('bdc-hide');
        });

        //单击二级中返回上一级
        $('.bdc-back').on('click', function () {
            pageLevel = "1";
            $('.bdc-first-main').removeClass('bdc-hide');
            $('.bdc-second-main').addClass('bdc-hide');
        });

        var qqslOption;
        var qqslChart = echarts.init(document.getElementById('qqslYh'));
        var dyqsChart = echarts.init(document.getElementById('dyqsZxt'));

        //初始化入参
        if (interfaceIds) {
            //
            $.ajax({
                url: '/realestate-exchange/bdcDsfLog/listInterfaceLogMode?interfaceIds=' + interfaceIds,
                type: "GET",
                async: false,
                dataType: "json",
                success: function (data) {
                    if (data && data.success && data.data) {
                        var interfaceNames = [];
                        data.data.forEach(function (item, index) {
                            if (item.jkms) {
                                interfaceNames.push(item.jkms);
                            } else {
                                interfaceNames.push(item.jkmc);
                            }
                        })
                        $('#jkmc').val(interfaceNames.join(","));
                        //初始化数据
                        init();
                    } else {
                        layer.confirm("当前选择接口获取对应接口信息失败，请联系管理员！", {
                            icon: 5,
                            btn: '确认',
                            title: '提示'
                        }, function (index) {
                            window.open("about:blank", "_self").close();
                        });
                    }
                },
                error: function (e) {
                }
            });
        } else {
            //初始化数据
            init();
        }


        //查询
        $("#query").click(function () {
            init();
        });

        //导出
        $("#exportExcel").click(function () {
            window.location.href = '../bdcDsfLog/exportExcelDsfrz?' + $('form').serialize();

        });

        // 页面执行相应级别的初始化方法
        function init(){
            if (pageLevel == "1") {
                countZj();
                initDyqs();
                initQqsl();
                initMx();
            } else if (pageLevel == "2") {
                initSecond();
            }
        }

        //各系统请求数量
        function initQqsl(){
            var legendData = [];
            var seriesData = [];
            // var result = ajaxQuery("../bdcDsfLog/countQqsl?type="+type, $('form').serialize(), "POST", true
            //     , "application/x-www-form-urlencoded;charset=utf-8");
            $.ajax({
                url: "../bdcDsfLog/countQqsl?type="+type,
                type: "POST",
                async: true,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                data: $('form').serialize(),
                success: function (result) {
                    if (!isEmptyObject(result)) {
                        legendData = result.legendData;
                        seriesData = result.seriesData;
                    }
                    // 对 legendData 做 字典转换
                    renderQqsl(legendData,seriesData);
                },
                error: function (err) {
                    console.log(err);
                }
            });
        }
        //渲染请求数量饼图
        function renderQqsl(legendData,seriesData){
            qqslOption = {
                //tooltip: {
                //    trigger: 'item',
                //    formatter: '{a} <br/>{b}: {c} ({d}%)'
                //},
                legend: {
                    orient: 'vertical',
                    left: 10,
                    data: getGxbmmcByDmArr(legendData)
                },
                color: getGxbmColorByDmArr(legendData),
                graphic: [{
                    type: 'text',
                    left: 'center',
                    top: '45%',
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
                    top: '52%',
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
                    label: {
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
                    },
                    data: getGxbmmcByDmArr(seriesData,"name")
                }
                ]
            };
            qqslChart.setOption(qqslOption,true);
            qqslChart.on("click", eConsole);
        }
        //环状图点击相应查询
        function everyClick(param,i,txt){
            //console.log(param);
            //console.log(i);
            //console.log(txt);//点击的名称
            $('.bdc-first-main').addClass('bdc-hide');
            $('.bdc-second-main').removeClass('bdc-hide');
            selectCheck(txt);
            //根据点击内容获取数据，加载二级页面内容
            renderSecond(txt);
        }
        //选中下拉框text为xxx的选项
        function selectCheck(text){
            //$("select[name='gxbmbz']").find("option[text="+text+"]").prop("selected", 'selected');
            $("select[name='gxbmbz'] option:contains("+text+")").prop("selected", 'selected');
            /*$.each($("[name=gxbmbz] option"), function (keys, vales) {
                if ($(this).text() === text) {
                    $(this).attr("selected",true);
                    return true;
                }
            });*/
            form.render();
        }
        //增加监听事件
        function eConsole(param) {
            if (typeof param.seriesIndex != 'undefined') {
                if (param.type == 'click') {
                    var peiLenght= qqslOption.legend.data.length;
                    for(var i = 0;i < peiLenght;i++){
                        if(param.name == qqslOption.legend.data[i]){
                            everyClick(param, i, qqslOption.legend.data[i]);
                        }
                    }
                }else{
                    document.getElementById('hover-console').innerHTML = 'Event Console : ' + param.dataIndex;
                }

            }
        }

        //近7天调用情况
        function initDyqs(){
            //['邮件营销', '联盟广告', '视频广告', '直接访问', '搜索引擎']
            var legendData = [];
            var xAxisData = [];
            var seriesData = [];
            // var result = ajaxQuery("../bdcDsfLog/countDyqs?type="+type, $('form').serialize(), "POST", false
            //     , "application/x-www-form-urlencoded;charset=utf-8");
            $.ajax({
                url: "../bdcDsfLog/countDyqs?type="+type,
                type: "POST",
                async: true,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                data: $('form').serialize(),
                success: function (result) {
                    if (!isEmptyObject(result)) {
                        legendData = result.legendData;
                        xAxisData = result.xAxisData;
                        seriesData = result.seriesData;
                    }
                    renderDyqs(legendData,xAxisData,seriesData);
                },
                error: function (err) {
                    console.log(err);
                }
            });

        }
        //渲染调用趋势折线图
        function renderDyqs(legendData,xAxisData,seriesData){
            var dyqsOption = {
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: getGxbmmcByDmArr(legendData)
                },
                grid: {
                    left: '3%',
                    right: '10%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: xAxisData
                },
                yAxis: {
                    type: 'value'
                },
                color: getGxbmColorByDmArr(legendData),
                series: getGxbmmcByDmArr(seriesData,"name")
            };
            dyqsChart.setOption(dyqsOption,true);
        }

        //初始化明细表格
        function initMx(){
            var tableData = [];
            var result = ajaxQuery("../bdcDsfLog/countMx?type="+type, $('form').serialize(), "POST", false, "application/x-www-form-urlencoded;charset=utf-8");
            $.ajax({
                url: "../bdcDsfLog/countMx?type="+type,
                type: "POST",
                async: true,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                data: $('form').serialize(),
                success: function (result) {
                    if (!isEmptyObject(result)) {
                        tableData = result;
                    }
                    renderMx(tableData)
                },
                error: function (err) {
                    console.log(err);
                }
            });
        }
        //渲染明细表格
        function renderMx(tableData){
            table.render({
                elem: '#mxTable',
                //id: 'tableId',
                title: '详情',
                even: true,
                cols: [[
                    {field:'MC', title:'共享系统',templet:function(d){
                            if (d.GXBMBZ == null)
                            {
                                return "";
                            }
                            return getGxbmmcByDm(d.GXBMBZ);
                        }},
                    {field:'CGZS', title:'成功'},
                    {field:'SBZS', title:'失败'},
                    {field:'ZS', title:'总数'}
                    ,{field:'PJXYSJ', title:'平均耗时',templet:function(d){
                            if (d.PJXYSJ == null)
                            {
                                return "";
                            }
                            return d.PJXYSJ/1000;
                        }}
                    // ,{title:'操作',width: 100, toolbar: '#mxFilterBar'}
                ]],
                data: tableData,
                limit: 1000,
                done: function () {
                    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                        $('.layui-table-body .layui-none').html('<img src="../image/table-none.png" alt="">无数据');
                    }
                }
            });

            table.on('tool(mxFilter)', function(obj){
                //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data; //获得当前行数据
                var layEvent = obj.event;

                if(layEvent === 'detail'){
                    $('.bdc-first-main').addClass('bdc-hide');
                    $('.bdc-second-main').removeClass('bdc-hide');
                    selectCheck(data.MC);
                    renderSecond(data.MC);
                }
            });
        }

        //总计
        function countZj(){
            // var result = ajaxQuery("../bdcDsfLog/countZj?type="+type, $('form').serialize(), "POST", false,  "application/x-www-form-urlencoded;charset=utf-8");
            $.ajax({
                url: "../bdcDsfLog/countZj?type="+type,
                type: "POST",
                async: true,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                data: $('form').serialize(),
                success: function (data) {
                    if (!isEmptyObject(data)) {
                        if(isEmptyStr(data.gxbmgs)){
                            $("#gxbmgs").text(0);
                        } else {
                            $("#gxbmgs").text(data.gxbmgs);
                        }

                        if(isEmptyStr(data.jkzs)){
                            $("#jkzs").text(0);
                        } else {
                            $("#jkzs").text(data.jkzs);
                        }

                        if(isEmptyStr(data.cgzs)){
                            $("#cgzs").text(0);
                        } else {
                            $("#cgzs").text(data.cgzs);
                        }

                        if(isEmptyStr(data.pjxysj)){
                            $("#pjxysj").html(0 + '<span>（ms）</span>');
                        } else {
                            var pjsjSec = data.pjxysj/1000;
                            $("#pjxysj").html(pjsjSec + '<span>（s）</span>');
                        }

                        if(isEmptyStr(data.sbzs)){
                            $("#sbzs").text(0);
                        } else {
                            $("#sbzs").text(data.sbzs);
                        }
                    }
                },
                error: function (err) {
                    console.log(err);
                }
            });

        }

        //初始化二级页面 查询
        function initSecond(){
            //['税务','互联网','其他','其他','其他','其他','其他','其他','其他','其他','其他','其他','其他']
            var yAxisData = [];
            //[320, 302, 301, 334, 390, 330, 320, 320, 320, 320, 320, 320, 320]
            var seriesDataCg = [];
            //[120, 132, 101, 134, 90, 230, 210, 210, 210, 210, 210, 210, 210]
            var seriesDataSb = [];
            var tableData=[];
            var result = ajaxQuery("../bdcDsfLog/countGxtDymx?type="+type
                , $('form').serialize(), "POST", false, "application/x-www-form-urlencoded;charset=utf-8 ");
            if (!isEmptyObject(result)) {
                yAxisData = result.yAxisData;
                seriesDataCg = result.seriesDataCg;
                seriesDataSb = result.seriesDataSb;
                tableData = result.tableData;
            }
            var title = "";
            if(!isEmptyStr($("select[name='gxbmbz'] option:selected").val())){
                title = $("select[name='gxbmbz'] option:selected").text();
            }
            //根据点击内容获取制定数据，加载二级页面内容
            renderSecondZxt(yAxisData,seriesDataCg,seriesDataSb,title);
            renderHzTable(tableData);
        }

        //初始化二级页面 环状图 明细表格详情 跳转
        function renderSecond(gxbmmc){
            pageLevel = "2";
            //['税务','互联网','其他','其他','其他','其他','其他','其他','其他','其他','其他','其他','其他']
            var yAxisData = [];
            //[320, 302, 301, 334, 390, 330, 320, 320, 320, 320, 320, 320, 320]
            var seriesDataCg = [];
            //[120, 132, 101, 134, 90, 230, 210, 210, 210, 210, 210, 210, 210]
            var seriesDataSb = [];
            var tableData = [];
            var initSecondParam;
            if (interfaceIds) {
                initSecondParam = {"gxbmmc": gxbmmc, "gxbmbz": getGxbmdmByMc(gxbmmc), "jkmc": $('#jkmc').val()};
            } else {
                initSecondParam = {"gxbmmc": gxbmmc, "gxbmbz": getGxbmdmByMc(gxbmmc)};
            }
            var result = ajaxQuery("../bdcDsfLog/countGxtDymx?type=" + type
                , initSecondParam, "POST", false, "application/x-www-form-urlencoded;charset=utf-8 ");
            if (!isEmptyObject(result)) {
                yAxisData = result.yAxisData;
                seriesDataCg = result.seriesDataCg;
                seriesDataSb = result.seriesDataSb;
                tableData = result.tableData;
            }
            //根据点击内容获取制定数据，加载二级页面内容
            renderSecondZxt(yAxisData, seriesDataCg, seriesDataSb, gxbmmc);
            renderHzTable(tableData);
        }

        //渲染二级柱形图
        function renderSecondZxt(yAxisData,seriesDataCg,seriesDataSb,title){
            //表格条数 * 表格行高
            $('#dylZxt').html('').removeAttr('_echarts_instance_').height(38 * (yAxisData.length + 1));
            var getYclArr = [];
            yAxisData.forEach(function(v){
                var endStr = '...' +  v.substring(v.length - 6);
                getYclArr.push(endStr);
            });
            var zxtChar = echarts.init(document.getElementById('dylZxt'));
            var zxtOption = {
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                title: {
                    show: true,
                    text: title,
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
                legend: {
                    x: 'center',
                    y: '26px',
                    data: ['成功次数', '失败次数']
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
                    data: getYclArr,
                    axisLabel: {
                        interval: 0,
                        rotate: 30
                    }
                    ,inverse:true
                },
                series: [
                    {
                        name: '成功次数',
                        type: 'bar',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true,
                                position: 'insideRight',
                                formatter: function(params){
                                    if (params.value > 0) {
                                        return params.value;
                                    }else {
                                        return '';
                                    }
                                }
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#55a4f1'
                            }
                        },
                        data: seriesDataCg
                    },
                    {
                        name: '失败次数',
                        type: 'bar',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true,
                                position: 'insideRight',
                                formatter: function(params){
                                    if (params.value > 0) {
                                        return params.value;
                                    }else {
                                        return '';
                                    }
                                }
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#e75260'
                            }
                        },
                        data: seriesDataSb
                    }
                    //,{
                    //    name: '消费合计总量',
                    //    type: 'line',
                    //    label: {
                    //        normal: {
                    //            show: true,
                    //            position: 'right',
                    //            offset: ['10','50'],
                    //            formatter: '{c}',
                    //            textStyle: {
                    //                color: '#333'
                    //            }
                    //        }
                    //    },
                    //    symbol: 'roundRect',
                    //    itemStyle: {
                    //        normal: {
                    //            color: 'rgba(128, 128, 128, 0)'
                    //        }
                    //    },
                    //    data: [440, 432, 402, 468, 480, 560, 530, 530, 530, 530, 530, 530, 530]
                    //}
                ]
            };
            //使用制定的配置项和数据显示图表
            zxtChar.setOption(zxtOption,true);
        }
        //渲染二级汇总表格
        function renderHzTable(tableData){
            table.render({
                elem: '#hzTable',
                //id: 'tableId',
                title: '汇总',
                even: true,
                cols: [[
                    {field:'MC', title:'接口名称',width: '35%'},
                    {field:'CGZS', title:'成功',width: '15%'},
                    {field:'SBZS', title:'失败',width: '15%'},
                    {field:'ZS', title:'总数',width: '15%'}
                    ,{field:'PJXYSJ', title:'平均耗时',templet:function(d){
                            if (d.PJXYSJ == null)
                            {
                                return "";
                            }
                            return d.PJXYSJ/1000;
                        }}
                ]],
                data: tableData,
                limit: 1000,
                done: function () {
                    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                        $('.layui-table-body .layui-none').html('<img src="../image/table-none.png" alt="">无数据');
                    }
                }
            });
        }


        // 用来 在初始化时  根据类型显示部分查询条件
        function initDiv(){
            if(type == 'logindb'){
                // 查询数据库时  可以显示的查询条件：
                // 分中心
                $("#fzxmcDiv").removeClass("layui-hide");
                // 流程名称
                $("#gzldymcDiv").removeClass("layui-hide");
                // 导出excel
                $("#exportExcel").show();
            } else {
                $("#exportExcel").hide();
            }
            if (interfaceIds) {
                // $("#gxbmDiv").hide();
                $("#jkmcDiv").hide();
                $("#jkmcsDiv").hide();
            }
            // 重置 按钮位置
            resetBtn();
        }
        function resetBtn(){
            if($('.pf-form-item .layui-inline:not(.layui-hide):not(.bdc-button-box)').length == 4){
                $(".bdc-button-box").addClass("bdc-button-box-four");
            }
        }

        function getGxbmdmByMc(mc) {
            var dm;
            $.each(gxbmbzZdMap, function (k, v) {
                if(v == mc){
                    dm = k;
                }
            })
            return dm;
        }
        function getGxbmmcByDm(dm){
            var mc = gxbmbzZdMap[dm];
            if(mc){
                return mc;
            }
            return dm;
        }
        function getGxbmmcByDmArr(dmArr,key){
            var arr = [];
            for(var i=0; i<dmArr.length; i++){
                if(key){
                    var temp = dmArr[i];
                    temp[key] = getGxbmmcByDm(temp[key]);
                    arr.push(temp);
                }else{
                    arr.push(getGxbmmcByDm(dmArr[i]));
                }
            }
            return arr;
        }

        function getGxbmColorByDmArr(dmArr,key){
            var arr = [];
            for(var i=0; i<dmArr.length; i++){
                if(key){
                    var temp = dmArr[i];
                    temp[key] = getGxbmColorByDm(temp[key]);
                    arr.push(temp);
                }else{
                    arr.push(getGxbmColorByDm(dmArr[i]));
                }
            }
            return arr;
        }

        function getGxbmColorByDm(dm){
            var color = gxbmbzColorMap[dm];
            if(color){
                return color;
            }else{
                return "#000000"
            }
        }
    })
});