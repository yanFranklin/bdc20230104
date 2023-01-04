layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4',
});
// 组织树多选值
var OrgTreeCheck = new Map();
var formSelectsVar, table;
// 部门下拉数组
var bmmcSelList = [];
// 流程下拉数组
var lcmcSelList = [];
var reverseList = ['zl'];
layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element;
    formSelectsVar = layui.formSelects;
    table = layui.table;

    $('.all-content .layui-none').removeClass('bdc-hide');
    // 日期控件
    laydate.render({
        elem: '#kssj',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#jzsj').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
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
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

    //流程名称渲染
    formSelectsVar.value("selectLcmc", []);
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/process/gzldymcs",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (data) {
            $('#selectedDefName').append(new Option("请选择", ""));
            $.each(data, function (index, item) {
                lcmcSelList.push({
                    name: item.name,
                    value: item.processDefKey
                })
            });
            formSelectsVar.data('selectLcmc', 'local', {
                arr: lcmcSelList
            });
            layui.form.render("select");
        }, error: function (e) {
            response.fail(e,'');
        }
    });

    // 初始化超时详细统计tab
    table.render({
        elem: '#pageTableTjmx',
        title: '流程超期详细列表',
        method: 'GET',
        // url: "/realestate-inquiry-ui/rest/v1.0/tjjg/lc/page",
        // toolbar: '#toolbarTjmx',
        defaultToolbar: ['filter'],
        where: {},
        request: {
            limitName: 'size', //每页数据量的参数名，默认：limit
            loadTotal: true,
            loadTotalBtn: false
        },
        even: true,
        data:[],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {
                field: 'procTimeoutCount', minWidth: 110, title: '超期状态', templet: function (d) {
                    var procTimeoutCount = d.procTimeoutCount;
                    procTimeoutCount = procTimeoutCount.toString().replace("-", "");
                    return '<span class="bdc-table-state-th">超期' + procTimeoutCount + '天</span>';
                }
            },
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'procDefName', minWidth: 160, title: '流程名称'},
            {field: 'startUserDepName', minWidth: 160, title: '所属登记中心'},
            {field: 'startUserName', minWidth: 100, title: '受理人'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'cz', title: '操作', width: 70, templet: '#barDemo', align: "center",fixed:"right"}
        ]],
        page: true,
        parseData: function (res) {
            // 设置选中行
            if(res.content && res.content.length > 0){
                var checkedData = layui.sessionData('checkedData');
                res.content.forEach(function (v) {
                    $.each(checkedData, function(key, value){
                        if(key == v.orgId+"_"+v.taskName+"_"+v.procDefName){
                            v.LAY_CHECKED = true;
                        }
                    })
                });
            }
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function (data) {
        }
    });

    //监听工具条
    table.on('tool(pageTableTjmx)', function (obj) {
        var data = obj.data;
        if (obj.event === 'xq') {
            layer.open({
                type: 2,
                title: '详情',
                area: ["590px","450px"],
                shadeClose: true,
                content: './tjjglcxq.html?gzlslid='+ data.processInstanceId,
                success: function(layero, index){
                },
                yes: function(index, layero){
                    layer.close(index);
                }
            });
        }
        if (obj.event === 'openpage') {
            //得到当前行数据
            var listArray = {
                processInstanceId: obj.data.processInstanceId,
                processDefName: obj.data.processDefName,
                claimStatus: obj.data.claimStatus,
                claimStatusName: obj.data.claimStatusName,
                taskName: obj.data.taskName,
                processInstanceType: 'list',
                taskId : obj.data.taskId
            };
            sessionStorage.setItem('listArray' + obj.data.processInstanceId, JSON.stringify(listArray));
            window.open("/realestate-portal-ui/view/new-page.html?name=" + obj.data.processInstanceId+"&type=list", obj.data.slbh);
        }
    });

    // tab页切换事件
    element.on('tab(tabFilter)', function () {
        var tabName = this.getAttribute('lay-id');
        tabChangeEvent(tabName);
    });

    $('#search').click(function () {
        var tabName = $(".layui-tab-title").find(".layui-this").attr('lay-id');
        tabChangeEvent(tabName);
        return false;
    });

    initChart();

});

$(function () {
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
});

function initChart(data) {
    if(isNotBlank(data)){
        addModel();
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/tjjg/lc/cstj",
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            success: function (res) {
                loadEcharts(res);
                removeModal();
            }, error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }else{
        loadEcharts();
    }

    function loadEcharts(res){
        var yData = new Array();
        var fzlData = new Array();
        // 动态设置柱状图y轴的长度
        var setHeight = 40;
        var autoHeight = 180;
        if(isNotBlank(res)){
            autoHeight = res.length * setHeight;
            $.each(res, function(index, value){
                if(yData.indexOf(value.bmmc)===-1){
                    yData.push(value.bmmc);
                }
                fzlData.push(isNotBlank(value.csjsl)? value.csjsl:0);
            });
        }
        if(autoHeight < 180) {
            autoHeight = 180;
        }
        $('#allChart').height(autoHeight);

        // 根据yData长度调整图表高度
        changechartHeight(yData);
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
                type: 'value',
                name: '数量',
            },
            yAxis: {
                name: "部门名称",
                type: 'category',
                boundaryGap: [0, 0.2],
                data: yData
            },
            series: [{
                name: '数量',
                'type':'bar', stack: 'sum',
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
        var myChart = echarts.init(document.getElementById('allChart'));
        myChart.setOption(option, true);
        myChart.resize();
    }

}

/**
 *
 * @param tabName 当前tab页的id
 * @description tab页点击事件
 */
function tabChangeEvent(tabName) {
    var param = this.getSearchData();
    if(isNotBlank(param)){
        if (tabName == "sjzs") {
            initChart(param);
        } else if (tabName == "csxxtj") {
            reloadTableMx({
                paramJson: JSON.stringify(param)
            }, "/realestate-inquiry-ui/rest/v1.0/tjjg/lc/page");
            table.resize('pageTableTjmx');
        }
    }
    return false;

}

// 根据数据条数调整图表高度
function changechartHeight(data) {
    var height = data.length * 37 + 25;
    if(height > 400){
        $('.layui-show .chart-item').css({
            "height": height
        });
    }else {
        $('.layui-show .chart-item').css({
            "height": 400
        });
    }
}

function getSearchData(){
    var startDate = new Date($('#kssj').val()).getTime();
    var endTime = new Date($('#jzsj').val()).getTime();
    if (endTime < startDate) {
        warnMsg('结束时间不能小于开始时间');
        return null;
    }
    var count = 0;
    $(".search").each(function (i) {
        if($(this).attr("id") != "bm-select" && $(this).attr("id") != "process"){
            var value = $(this).val();
        }else{
            var value = formSelectsVar.value($(this).attr('xm-select'), 'val').join(",");
        }
        if(!isNullOrEmpty(value)){
            count += 1;
        }
    });
    if(count == 0){
        warnMsg("请输入查询条件!");
        return null;
    }
    // var bmid = formSelectsVar.value('selectBmmc', 'val');
    var lcmcKey = formSelectsVar.value('selectLcmc', 'val');
    var requestConditions = [];
    requestConditions.push(condition("processKey", "eq", lcmcKey.join(",")));
    requestConditions.push(condition("startUserDepId", "in", $("input[name=bmid]").val()));
    requestConditions.push(condition("startTime", "egt", $("#kssj").val()));
    requestConditions.push(condition("startTime", "elt", $("#jzsj").val()));
    requestConditions.push(condition("procTimeoutCount", "egt" , $("#cqsj").val()));
    requestConditions.push(condition("procSameRegionComplete","eq", $("#sfydbl").val()))
    return requestConditions;
}

function condition(key,judge,value){
    return {
        requestKey : key,
        requestJudge : judge,
        requestValue : value == undefined? "" : value,
    }
}

function getDataByAjax(_path, _param,_type, _fn, async) {
    var _url = getContextPath() + _path;
    var getAsync = false;
    if(async){
        getAsync = async
    }
    $.ajax({
        url: _url,
        type: _type,
        async: getAsync,
        contentType: 'application/json;charset=utf-8',
        data: _param,
        success: function (data) {
            _fn.call(this, data, data);
        },
        error: function (err) {
            console.log(err);
        }
    });
}

// 表格重新加载
function reloadTableMx(whereObj, url){
    addModel();
    layui.use(['table'], function () {
        var table = layui.table;
        table.reload('pageTableTjmx', {
            url: url,
            where: whereObj,
            page: {
                curr: 1,  //重新从第 1 页开始
                limits: [10, 50, 100]
            },
            done: function (res, curr, count) {
                removeModal();
                setHeight();
                reverseTableCell(reverseList);
                table.resize('pageTableTjmx');
            }
        });
    });
}
