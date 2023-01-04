layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

/** 定义全局变量*/
var xlZdList = []; //小类字典值
var tabId = "sjzs";//当前tab页id
var myChartName = "djlxChart";//当前chart名
var yData = [];
var xData = [];
var option = [];
var exportData = [];
var queryObject;


// 登记类型统计明细表格查询
var djlxmxDataUrl = "/realestate-inquiry-ui/rest/v1.0/bdcdp/getDjlxMx";
var djlxtbDataUrl = "/realestate-inquiry-ui/rest/v1.0/bdcdp/getDjlxtjTb";

var formSelects, table;
//登记类型下拉
var djlxSelect = [];
//登记小类下拉
var djxlSelect = [];
// 流程下拉数组
var lcmcSelList = [];
//区县代码
var qxdmSelList = [];


$(function () {
    // 获取页面表单标识，用于权限控制
    var module = $.getUrlParam('module');
    if (module) {
        setElementAttrByModuleAuthority(module);
    }
    /**
     * 监听台账查询 input 点击 显示 x 清空 input 中的内容
     */
    $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
        $(this).siblings('.layui-input').val('');
    });
    $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
        $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
    }).on('blur', function () {
        var $this = $(this);
        setTimeout(function () {
            $this.siblings('.layui-icon-close').addClass('bdc-hide');
        }, 150)
    });

    //绑定回车键
    $(document).keydown(function (event) {
        if (event.key == 13) {
            $("#search").click();
        }
    });
})

layui.use(['form', 'jquery', 'laydate', 'element', 'table', 'formSelects'], function () {


    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        table = layui.table;
    formSelects = layui.formSelects;

    // 日期控件
    laydate.render({
        elem: '#kssj',
        value: new Date((new Date().getTime())),
        type: 'date',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#jssj').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#jssj',
        value: new Date((new Date().getTime())),
        type: 'date',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#kssj').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    // 初始化查询
    var queryParams = {};
    $(".layui-form").serializeArray().forEach(function (item, index) {
        queryParams[item.name] = item.value;
    });
    $('.layui-none', $(this.elem).next()).width($('.layui-table-header table', $(this.elem).next()).width() + 'px');
    // 初始化登记类型tab
    table.render({
        elem: '#pageTableTjmx',
        method: 'get',
        toolbar: '#toolbarDemo',
        title: '登记类型',
        defaultToolbar: ['filter'],
        even: true,
        cols: [
            [
                {
                    field: 'xh', type: 'numbers', title: '序号', rowspan: 5,
                    templet: function (d) {
                        return d.LAY_TABLE_INDEX + 1;
                    },

                },
                {field: 'qhmc', width: 90, sort: true, rowspan: 5, title: '行政区划'},
                , {title: '增量数据接入情况', align:'center', colspan: 20},],

            [{width: 120, sort: true, colspan: 19,title: '本期接入质量评价' ,}, ],
            [
                {field: 'jrywl', width: 100, sort: true, rowspan: 3,title: '本期接入业务量（笔）',},
                {field: 'jrdbl', width: 100, sort: true, rowspan: 3,title: '本期接入登簿量（次）'},
                {field: 'dbrzdbl', width: 100, sort: true, rowspan: 3,title: '本期登簿日志反映出的登簿量（条）'},
                { width: 120, sort: true,title: '本期未成功接入登簿量及在登簿日志量中的占比',colspan: 4},
                {title: '分时段接入登簿里及与本期接入登簿里的占比', colspan: 3, align: 'left'},
                { rowspan:3, width: 200, sort: true, title: '网络环境异常情况',},
                { rowspan:3, width: 200, sort: true, title: '非登簿环节接入的区县数',},
                { rowspan:3,width: 200, sort: true, title: '非自动方式接入的县区数'},
                {field: 'df', rowspan:3, sort: true, title: '综合评分'},
            ],
            [
                {field: 'wscdbqxsl', width: 100, sort: true, rowspan: 3,title: '未上报登簿日志县区数（个）'},
                {field: 'wscsjsl', width: 100, sort: true, rowspan: 3, title: '未上报数据县区数（个）'},
                {field: 'wcgjrdbl', width: 100, sort: true, title: '未成功接入登薄量(条)'},
                {field: 'wcgjrzb', width: 60, sort: true, title: '占比'},
                {field: 'jrdbl1zb', width: 100, sort: true, title: '24小时接入登薄量(占比%)'},
                {field: 'jrdbl2zb', width: 100, sort: true, title: '48小时接入登薄量(占比%)'},

                {field: 'jrdbl3zb', width: 120, sort: true, title: '超过48小时接入登薄量(占比%)'},


            ]],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        text: {
            none: '无数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content, //解析数据列表
            }
        },
    })
    table.on('toolbar(pageTableTjmx)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
                exportAllExcel(checkStatus.data, obj.config);

    });

    //初始化加载表格
    function  loadData(){
        addModel();
        table.reload("pageTableTjmx", {
            url: getContextPath() + "/rest/v1.0/bdcdp/getQualityScore",
            where: {
                kssj: $("#kssj").val(),
                jssj: $("#jssj").val(),
            },
            method: 'get',
            page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function (res) {
                exportData = res.data;
            }
        });
        removeModal();
    }
    loadData();
    form.on('submit(search)', function () {
        loadData();
    });
});

$('#reset').on('click', function (item) {

    $('.bdc-percentage-container').find('.layui-form')
        .find('select').find("option:eq(0)")
        .attr("selected", "selected");
    setTimeout($('.bdc-percentage-container').find('.layui-form')
        .find('select').parent().find('input').val(''), 100);
    $('.reseticon').hide();
})


function exportAllExcel(data,obj){
    var cols = obj.cols[0];
    var url = obj.url;
    $.ajax({
        url: url,
        type: "get",
        data: {
            kssj: $("#kssj").val(),
            jssj: $("#jssj").val(),
            type:"exportAll",
        },
        success: function (data) {
            if(data.content){
                exportExcel(data.content,obj);
            }else{
                exportExcel(data,obj);
            }
        }
    });
}
function exportExcel(d, obj){
    var cols = obj.cols[0];
    var cols1 = obj.cols[1];
    var cols2= obj.cols[2];
    var cols3= obj.cols[3];
    var checkedData = layui.sessionData('checkedData');
    // 标题
    var showColsTitle = [];
    // 列内容
    var showColsField = [];
    // 列宽
    var showColsWidth = [];
    concatexcel(showColsTitle,showColsField,showColsWidth,cols);
    concatexcel(showColsTitle,showColsField,showColsWidth,cols1);
    concatexcel(showColsTitle,showColsField,showColsWidth,cols2);
    concatexcel(showColsTitle,showColsField,showColsWidth,cols3);
    //网络环境异常情况 非登簿环节接入的区县数 非自动方式接入的县区数 此处无值暂时置空
    showColsTitle.push(cols2[5].title);
    showColsTitle.push(cols2[6].title);
    showColsTitle.push(cols2[7].title);
    showColsField.push("");
    showColsField.push("");
    showColsField.push("");
    //得分放最后
    showColsTitle.push(cols2[8].title);
    showColsField.push(cols2[8].field);
    var data = new Array();
        data = d;

    for(var i = 0; i < data.length; i++){
        data[i].xh   = i + 1;
    }
    // 设置Excel基本信息
    $("#fileName").val('接入质量统计');
    $("#sheetName").val('增量数据接入情况');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#data").val(JSON.stringify(data));
    $("#form").submit();
}

//用excel表格数据处理
function concatexcel(showColsTitle,showColsField,showColsWidth,cols){
    for(var index in cols){
        if(cols[index].hide == false &&!cols[index].toolbar&&cols[index].field!=undefined&&cols[index].field!="df"){
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
}