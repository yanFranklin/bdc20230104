/**
 * 3 查询异常监管（岗位查询异常）
 */
var roles = getAllRoles();
// 当前所在tab
var tabid = "xxlb";
var exportExcelData;
var defaultDate = new Date();
defaultDate.setDate(1);
defaultDate.setHours(0,0,0,0);
var defaultDateStr = defaultDate.Format("yyyy-MM-dd hh:mm:ss");
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element,
        table = layui.table;

    var kssj = laydate.render({
        elem: '#cxsjq',
        type: 'datetime',
        value: defaultDate,
        trigger: 'click',
        done: function (value, date) {
            //选择的结束时间大
            if ($('#cxsjz').val() != '' && !completeDate($('#cxsjz').val(), value)) {
                $('#cxsjz').val('');
                $('.laydate-disabled.layui-this').removeClass('layui-this');
            }
            jssj.config.min = {
                year: date.year,
                month: date.month - 1,
                date: date.date,
                hours: date.hours,
                minutes: date.minutes,
                seconds: date.seconds
            }
        }
    });
    var jssj = laydate.render({
        elem: '#cxsjz',
        type: 'datetime',
        trigger: 'click'
    });


    //比较起止时间
    function completeDate(date1, date2) {
        var oDate1 = new Date(date1);
        var oDate2 = new Date(date2);
        if (oDate1.getTime() > oDate2.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    // 获取综合查询实体字段和汉字名称对照关系
    cxstxx();

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if (moduleCode) {
        setElementAttrByModuleAuthority(moduleCode);
    }

    if(roles) {
        // 使用情况查询条件下拉项
        $.each(roles, function (index, item) {
            $('#ryjs').append('<option value="' + item.id + '">' + item.alias + '</option>');
        });
        form.render('select');
    }

    table.render({
        elem: '#xxlbTable',
        toolbar: '#toolbar',
        title: '岗位查询异常信息',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        where: {
            'cxsjq': defaultDateStr
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {minWidth: 80, sort: false, field: 'yhm', title: '人员名称'},
            {minWidth: 200, sort: false, field: 'yhzh', title: '人员账号'},
            {minWidth: 150, sort: false, field: 'szbm', title: '所在部门'},
            {minWidth: 150, sort: false, field: 'zw', title: '职位'},
            {minWidth: 200, sort: false, field: 'ryjs', title: '角色'},
            {minWidth: 150, sort: false, field: 'cxcs', title: '查询次数'},
            {minWidth: 50, sort: false, templet: '#barAction', title: '操作', fixed: 'right'}
        ]],
        text: {
            none: '未查询到数据'
        },
        data: [],
        autoSort: false,
        page: true,
        parseData: function (res) {
            exportExcelData = res;
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        }
    });

    /**
     * 台账数据查询
     */
    tableReload();
    function tableReload() {
        // 获取查询内容
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });

        if(!isNullOrEmpty(obj["ycbz"]) && !isNumber(obj["ycbz"])) {
            warnMsg("异常标准请输入数值！");
            return false;
        }

        addModel();
        // 重新请求
        table.reload("xxlbTable", {
            url: "/realestate-supervise/rest/v1.0/cxycjg/gwcxyc/table"
            ,where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }

                removeModal();
            }
        });
    }

    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            loadData();
        }
    });

    $('#search').on('click', function () {
        loadData();
        return false;
    });

    /**
     * Tab切换
     */
    element.on('tab(yzhtjTab)', function(data){
        tabid = $(".layui-tab-title .layui-this").attr("id");
        loadData();
    });

    function loadData() {
        if("xxlb" === tabid) {
            tableReload();
        } else if("fhtj" === tabid) {
            chartReload();
        }
    }

    /**
     * 统计图表展示
     */
    function chartReload() {
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });

        addModel();
        $.ajax({
            url: "/realestate-supervise/rest/v1.0/cxycjg/gwcxyc/chart",
            type: 'POST',
            data: JSON.stringify(obj),
            contentType: 'application/json',
            dataType: "json",
            success: function (res) {
                if(res && res.length > 0) {
                    $("#tjfxChart").show();
                    showChart(res);
                } else {
                    failMsg("未查询到同岗位查询异常人员！");
                    $("#tjfxChart").hide();
                }
            },
            error: function (xhr, status, error) {
                failMsg("未查询到同岗位查询异常人员！");
                $("#tjfxChart").hide();
            },
            complete: function () {
                removeModal();
            }
        });
    }

    function showChart(data) {
        var myChart = echarts.init(document.getElementById('tjfxChart'), "macarons");
        myChart.clear();

        var title = new Array();
        if(data && data[0]) {
            $.each(data[0], function(key, value){
                title.push(key);
            })
        }

        var series = new Array();
        for(var i = 0; i < title.length - 1; i++) {
            series.push({
                type: 'bar',
                barMaxWidth: 30,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            position: 'top',
                            textStyle: {
                                color: '#333',
                                fontSize: 16
                            },
                            formatter: function (params) {
                                if(params.value[params.seriesName] > 0) {
                                    return params.value[params.seriesName];
                                } else {
                                    return "";
                                }
                            }
                        }
                    }
                }
            });
        }

        // 指定图表的配置项和数据
        option = {
            title: {
                text: '岗位查询异常人员及查询次数信息',
                left: 'center',
                textStyle: {
                    color: '#333'
                }
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                y: 'center',
                padding: [0, 50, 0 , 0]
            },
            tooltip: {},
            dataset: {
                dimensions: title,
                source: data
            },
            xAxis: {
                name:'角色',
                type: 'category'
            },
            yAxis: {
                name:'查询次数',
            },
            series: series
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }

    //监听工具条
    table.on('tool(xxlbTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'cxxq') {
            cxxq(data);
        }
    });

    //头工具栏事件
    table.on('toolbar(xxlbTable)', function (obj) {
        var checkStatus = table.checkStatus('xxlbTable').data;
        if(obj.event === 'excel'){
            exportExcel(obj,checkStatus);
        }
    });
    /**
     * 查询具体人员查询操作记录
     */
    function cxxq(data) {
        addModel();
        layer.open({
            type: 1,
            title: "用户查询日志列表",
            content: $('#popup'),
            area: ['985px', '550px'],
            cancel: function () {
                $("#popup").css('display', 'none');
            },
            success: function (layero, index) {
                viewTableRender(data);
            }
        });
    }

    /**
     * 查看用户查询日志
     */
    function viewTableRender(data) {
        data.cxsjq = $("#cxsjq").val();
        data.cxsjz = $("#cxsjz").val();

        table.render({
            elem: '#viewTable',
            id: 'viewTable',
            url: "/realestate-supervise/rest/v1.0/cxycjg/query/log",
            where: data,
            title: '用户查询日志列表',
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            cols: [[
                {type: 'numbers', fixed: 'left', width: 60, title: '序号'},
                {title: '用户名称', width: 100, field: 'yhm'},
                {title: '用户账号', width: 100, field: 'yhzh'},
                {title: '所在部门', width: 200, field: 'szbm'},
                {title: '查询条件', width: 300, field: 'cxtj',
                    templet: function (d) {
                        return resolveCxtj(d.cxtj);
                    }
                },
                {title: '查询结果', minWidth: 200, field: 'cxjg',
                    templet: function (d) {
                        return formatCxjg(d.cxjg);
                    }
                },
                {title: '操作时间', width: 200, field: 'czsj',
                    templet:function(d){
                        return formatDate(d.czsj);
                    }
                },
            ]],
            page: true,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                removeModal();
            }
        });
    }

    //点击高级查询--4的倍数
    $('#seniorSearch').on('click', function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $(this).parent().toggleClass('bdc-button-box-four');

        if ($(this).html() == '高级查询') {
            $(this).html('收起');
        } else {
            $(this).html('高级查询');
        }

        $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);
        $('.layui-table-body').height($('.bdc-table-box').height() - 129);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);
    });
});

// 处理查询结果展示
function formatCxjg(cxjg){
    if(!cxjg || cxjg == "[]"){
        return "未查询到数据";
    } else {
        var item = cxjg.substr(0, 500) + "...";
        item = resolveCxjg(item);
        return item;
    }
}



