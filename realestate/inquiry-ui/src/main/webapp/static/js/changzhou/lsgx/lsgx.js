/**
 * 历史关系操作JS
 */
var xmid = $.getUrlParam("xmid");
var gzlslid = $.getUrlParam("processInsId");
layui.use(['form','jquery','laydate','element','layer','table'],function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //---------------------查询条件开始-------------------------------------------------------

    $(function () {
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbar',
            url: "/realestate-inquiry-ui/history/xm" + "?gzlslid=" + gzlslid,
            title: '项目列表',
            defaultToolbar: ['filter'],
            page: false,
            cellMinWidth: 80,
            even: true,
            cols: [[
                // {type: 'checkbox', fixed: 'left'},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 300,templet: '#cfbdcdyhTpl'},
                {field: 'zl', title: '坐落', minWidth: 300},
                {field: 'slbh', title: '受理编号', minWidth: 300},
                {field: 'djyy', title: '登记原因', width: 100},
                {field: 'slr', title: '受理人', width: 100},
                {field: 'dbr', title: '登簿人', width: 100},
                {field: 'bz', title: '备注', width: 100},
                {field: 'djsj', title: '登记时间', width: 120,
                    templet: function (d) {
                        if (d.djsj) {
                            return Format(d.djsj, "yyyy年MM月dd日");
                        } else {
                            return '';
                        }
                    }
                },
            ]],
            text: {
                none: '未查询到数据'
            },
            data: [],
            autoSort: false,
            parseData: function(res) { //res 即为原始返回的数据
                //console.log(res)
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                }
            },
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
                }else {
                    // $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    // $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        });
    });
    //监听行单击事件
    table.on('row(pageTable)', function(obj){
        xmid = obj.data.xmid;
        generateParents(obj.data.xmid);
    });
    //---------------------查询条件结束-------------------------------------------------------

    //监听内容区选中tab鼠标覆盖事件
    $('.bdc-tab-title li').on('mouseenter',function () {
        $(this).find('.bdc-second-tab').removeClass('bdc-hide');
    }).on('mouseleave',function () {
        $(this).find('.bdc-second-tab').addClass('bdc-hide');
    });
    $('.bdc-tab-title .bdc-second-tab p').on('click',function () {
        $(this).addClass('bdc-second-this').siblings().removeClass('bdc-second-this');
    });

    // 点击上一手（父节点）
    $('#prev').on('click', function() {
        generateParents(xmid);
    });

    // 点击上一手（子节点）
    $('#next').on('click', function() {
        generateChildren(xmid);
    });

    // 处理按钮,默认不能点击
    $('#prev').attr('disabled', 'disabled');
    $('#next').attr('disabled', 'disabled');
});

// 组织父节点
function findParents(obj) {
    for (k in obj) {
        var bdcqzh = obj.bdcXmDO.bdcqzh
        if(isNullOrEmpty(bdcqzh) || (bdcqzh == "null")){
            bdcqzh = "";
        }
        var bdcdyh = obj.bdcdyh;
        if(isNullOrEmpty(bdcdyh) || (bdcdyh == "null")){
            bdcdyh = "";
        }
        var zl = obj.zl;
        if(isNullOrEmpty(zl) || (zl == "null")){
            zl = "";
        }
        if (k === 'parentXm') {
            obj.name = '不动产权证号：' + bdcqzh +
                '\n' + '不动产单元号：' + bdcdyh +
                '\n' + '坐落：' + zl;
            obj.children = obj.parentXm;
        }

        if (obj.children && obj.children.length > 0) {
            obj.children.forEach(function (item, index) {
                findParents(item);
            });
        }

    }

    return obj;
}

// 组织子节点
function findChildren(obj) {
    for (k in obj) {
        var bdcqzh = obj.bdcXmDO.bdcqzh
        if(isNullOrEmpty(bdcqzh) || (bdcqzh == "null")){
            bdcqzh = "";
        }
        var bdcdyh = obj.bdcdyh;
        if(isNullOrEmpty(bdcdyh) || (bdcdyh == "null")){
            bdcdyh = "";
        }
        var zl = obj.zl;
        if(isNullOrEmpty(zl) || (zl == "null")){
            zl = "";
        }
        if (k === 'sonXm') {
            obj.name = '不动产权证号：' + bdcqzh + '\n'
                + '不动产单元号：' + bdcdyh
            + '\n' + '坐落：' + zl;
            obj.children = obj.sonXm;
        }

        if (obj.children && obj.children.length > 0) {
            obj.children.forEach(function (item, index) {
                findChildren(item);
            });
        }

    }

    return obj;
}

// 生成登记历史关系父节点
function generateParents(xmid) {
    addModel();
    // 清除#tree中的canvas
    $('#tree').html('').removeAttr('_echarts_instance_');
    if(! $('#lsgxpic').hasClass("layui-show")){
        $('#lsgxpic').addClass("layui-show");
    }

    // 请求上面的登记历史关系
    var example;

    var chartDom = document.getElementById('tree');
    var myChart = echarts.init(chartDom);
    var option;
    $.ajax({
        url: "/realestate-inquiry-ui/history/xmlsgx" + "?xmid=" + xmid,
        type: "GET",
        dataType: "json",
        async:false,
        success: function (data) {
            // 默认生成登记历史关系父节点
            example = data;
        },
        error: function (e) {
        }
    });
    // 转换父节点
    var parents = findParents(example);
    myChart.setOption(
        (option = {
            tooltip: {
                trigger: 'item',
                triggerOn: 'mousemove'
            },
            series: [
                {
                    type: 'tree',
                    data: [parents],
                    top: '5%',
                    left: '25%',
                    bottom: '5%',
                    right: '25%',
                    symbolSize: 3,
                    orient: 'RL',
                    label: {
                        position: 'right',
                        verticalAlign: 'middle',
                        align: 'left'
                    },
                    leaves: {
                        label: {
                            position: 'left',
                            verticalAlign: 'middle',
                            align: 'right'
                        }
                    },
                    emphasis: {
                        focus: 'descendant'
                    },
                    expandAndCollapse: true,
                    animationDuration: 500,
                    animationDurationUpdate: 500
                }
            ]
        })
    );
    option && myChart.setOption(option);
    // 处理按钮
    $('#prev').attr('disabled', 'disabled');
    $('#next').removeAttr('disabled');
    removeModel();
}


// 生成登记历史关系子节点
function generateChildren(xmid) {
    var example;

    // 清除#tree中的canvas
    $('#tree').html('').removeAttr('_echarts_instance_');
    if(! $('#lsgxpic').hasClass("layui-show")){
        $('#lsgxpic').click();
    }

    var chartDom = document.getElementById('tree');
    var myChart = echarts.init(chartDom);
    var option;

    myChart.hideLoading();
    // 请求上面的登记历史关系
    $.ajax({
        url: "/realestate-inquiry-ui/history/xmlsgx" + "?xmid=" + xmid,
        type: "GET",
        dataType: "json",
        async:false,
        success: function (data) {
            // 默认生成登记历史关系父节点
            example = data;
        },
        error: function (e) {
        }
    });
    // 转换子节点
    var children = findChildren(example);
    myChart.setOption(
        (option = {
            tooltip: {
                trigger: 'item',
                triggerOn: 'mousemove'
            },
            series: [
                {
                    type: 'tree',
                    data: [children],
                    top: '5%',
                    left: '25%',
                    bottom: '5%',
                    right: '25%',
                    symbolSize: 3,
                    label: {
                        position: 'left',
                        verticalAlign: 'middle',
                        align: 'right'
                    },
                    leaves: {
                        label: {
                            position: 'right',
                            verticalAlign: 'middle',
                            align: 'left'
                        }
                    },
                    emphasis: {
                        focus: 'descendant'
                    },
                    expandAndCollapse: true,
                    animationDuration: 500,
                    animationDurationUpdate: 500
                }
            ]
        })
    );

    option && myChart.setOption(option);

    // 处理按钮
    $('#next').attr('disabled', 'disabled');
    $('#prev').removeAttr('disabled');
}