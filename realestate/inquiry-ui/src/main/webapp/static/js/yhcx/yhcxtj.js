layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

/** 定义全局变量*/

var myChartName = "yhcxChart";//当前chart名
var yData = [];
var xData = [];
var option = [];
var queryObject;

//受理情况统计-受理人员统计
var yhcxLogUrl="/realestate-inquiry-ui/rest/v1.0/yhcx/countLog";
//部门下拉框
var bmselecList="/realestate-inquiry-ui/slqktj/bm/select";
//人员下拉框
var ryselecList="/realestate-inquiry-ui/slqktj/ry/select";

var formSelects;
// 部门下拉数组
var bmmcSelList = [];
// 人员下拉数组
var bjrySelList = [];

var countObject = {};

$(function(){
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
})

layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects','upload','tree'], function () {

    //addModel();//添加遮罩
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element;
    var upload = layui.upload;
    var uploadInst = null;
    formSelects = layui.formSelects;

    // 初始化下拉框
    initFormSelect(formSelects);

    // 获取部门下拉框数据
    getTreeData('dept_treeyhcx','selectedyhcxDeptName','selectedyhcxDeptid',"/rest/v1.0/process/dept/allnew");

    /**
     * 初始化组织机构下拉控件
     */
    $('.org_select_showyhcx').click(function () {
        if($('.org_select_showyhcx').text() == '选择'){
            $('.org_select_tree').css('display','block');
            $('.org_select_showyhcx').text('隐藏')
        }else{
            $('.org_select_showyhcx').text('选择');
            $('.org_select_tree').css('display','none');
        }
    });

    //下拉面板宽高位置
    (function(){
        var width = $('#selectedyhcxDeptName').width + 40;
        $('.org_select_tree').css({'width': width, 'height':'200px', 'left':-66, 'top':35});
    })();

    // 日期控件
    laydate.render({
        elem: '#beginTime',
        //value: new Date((new Date().getTime() - 90 * 24 * 60 * 60 * 1000)),
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#endTime').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#endTime',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#beginTime').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });



    // 查询按钮点击事件
    form.on('submit(search)', function (data) {
        for(var key in layui.sessionData('checkedData')){
            layui.sessionData('checkedData', {
                key: key, remove: true
            });
        }

        formSubmitEvent(data);
        return false;
    });

    // 导出按钮点击事件
    $("#export").click(function () {
        if(countObject.length == 0 ){
            warnMsg("没有数据，无法导出！");
            return;
        }
        exportExcel(countObject)
        return false;
    })

    // 下载按钮点击事件
    $("#download").click(function () {
        var url = "/realestate-inquiry-ui/static/js/yhcx/model/model.xls";
        window.location.href = url;
        return false;
    })


    // 導入按鈕
    uploadInst = upload.render({
        elem: '#import' //绑定元素
        ,accept: 'file'
        ,url: '/realestate-inquiry-ui/rest/v1.0/yhcx/excel' //上传接口
        ,done: function(res){
            if(res == null || res.length == 0){
                layer.alert('导入失败，请重试！', {title: '提示'});
            }
            var valueArr = [];
            for(var i=0;i<res.length;i++){
                valueArr.push(res[i].alias)
            }
            layui.formSelects.value("selectBjry",valueArr);
            $("#search").click();
        }
        ,error: function(e){
            layer.alert('导入数据解析失败，请检查模板是否正确！', {title: '提示'});
        }
    });


    // 導入按钮点击事件
    $("#import").click(function () {
        uploadInst.upload();
        return;
    })


    // 初始化查询
    //initSjzsTab($(".layui-form").serialize(),false);

    $('.reseticon').on('click',function(item){
        $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
        $(this).hide();
        layui.form.render("select");
    })


});

/**
 * @date 2019.03.15 14:46
 * @author chenyucheng
 * @param queryData
 * @return
 */
function initSjzsTab(queryData) {
    /*当前tab也默认不可用部门和人员下拉框*/
    layui.form.render("select");
    var tableHead = "维度";
    var barTitle = "次数";
    var url = yhcxLogUrl;
    myChartName = "yhcxChart";

    $.ajax({
        url: url,
        type: 'post',
        data: queryData,
        dataType:'json',
        success: function (data) {
            // 数据置空
            yData = [];
            xData = [];
            var zj = 0;
            for(var key in data){
                yData.push(key);
                xData.push(data[key]);
            }
            countObject = data;

            option = {
                tooltip : {
                    trigger: 'axis'
                },
                title: {
                    show: true,
                    //text: barTitle,
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
                    show: false,
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
                yAxis: {
                    type: 'category',
                    data: yData,
                    inverse: true,
                    name: tableHead,
                    nameLocation: 'start',
                    nameTextStyle: {
                        fontSize: 14,
                        color: '#333'
                    },
                    axisLabel: {
                        interval:0,
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

            changeChart(yData.length);

            setTimeout(removeModal, 100);
        },
        error: function (e) {
            setTimeout(removeModal, 100);
        }
    });
}


/**
 * 查询按钮点击事件
 * @date 2019.03.15 14:45
 * @author chenyucheng
 * @param form监听事件的data原封不动的传入
 * @return
 */
function formSubmitEvent(formData) {
    queryObject = formData.field;
    // var organization = formSelects.value('selectBmmc', 'name').join(",");
    var organization = $('#selectedyhcxDeptName').val();
    var alias = formSelects.value('selectBjry', 'name').join(",");
    if(!organization && !alias){
        warnMsg(" 请至少输入一个查询统计的维度（部门名称，查询人员）！");
        return;
    }

    queryObject["organization"] = organization;// 部门
    queryObject["alias"] = alias;// 受理人名称

    addModel();
    initSjzsTab(queryObject,true);
    return false;
}

// index为图表y轴项目数量
function changeChart(index) {
    /*计算柱形图的高度，以右侧table的高度为参照
     * 该table每行高度为38.6px*/
    var height = index * 38.6 + 77.2;
    $("#" + myChartName).css({
        "height": height + 25
    });
    $("#sjfx-area").css({
        "height": height
    });

    echarts.init(document.getElementById("yhcxChart")).dispose();
    var myChart = echarts.init(document.getElementById(myChartName));

    //使用制定的配置项和数据显示图表
    myChart.setOption(option);
    myChart.resize();

}


/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.24 14:45
 * @author zhuyong
 * @return
 */
function exportExcel(obj){
    var cols = new Array();
    var colsObj = {};
    colsObj.field = "mc";
    colsObj.title = "名称";
    cols.push(colsObj);

    colsObj = {};
    colsObj.field = "val";
    colsObj.title = "次数";
    cols.push(colsObj);

    var checkedData = new Array();
    for(var key in obj){
        var objT = {};
        objT.val = obj[key];
        objT.mc = key;
        checkedData.push(objT);
    }
    // 标题
    var showColsTitle = [];
    // 列内容
    var showColsField = [];
    // 列宽
    var showColsWidth = [];
    for(var index in cols){
        showColsTitle.push(cols[index].title);
        showColsField.push(cols[index].field);
        showColsWidth.push(200 / 100 * 15);
    }


    for(var i = 0; i < checkedData.length; i++){
        checkedData[i].xh   = i + 1;
    }
    // 设置Excel基本信息
    $("#fileName").val('银行查询日志统计表');
    $("#sheetName").val('统计表');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#data").val(JSON.stringify(checkedData));
    $("#form").submit();
}

/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.25 14:45
 * @author chenyucheng
 * @return
 */
function exportAllExcel(data, obj){
    var cols = obj.cols[0];
    var url = obj.url;
    var paramData = obj.where;
    paramData["type"] = "exportAll";
    $.ajax({
        url: url,
        dataType: "json",
        data: paramData,
        success: function (data) {
          if(data.content){
              exportExcel(data.content,obj, "all");
          }else{
              exportExcel(data,obj, "all");
          }
        }
    });
}


/**
 * 加载下拉框
 */
function initFormSelect(formSelects){
    bmselect(formSelects);//部门
    ryselect(formSelects);//人员
}

function bmselect (formSelects){
    $.ajax({
        url: bmselecList,
        dataType: "json",
        success: function (data) {
            data.forEach(function(item){
                bmmcSelList.push({
                    name: item.name,
                    value: item.id
                })
            })
            formSelects.data('selectBmmc', 'local', {
                arr: bmmcSelList
            });
        }
    });
}

function ryselect (formSelects){
    $.ajax({
        url: ryselecList,
        dataType: "json",
        success: function (data) {
            data.forEach(function(item){
                bjrySelList.push({
                    name: item.alias,
                    value: item.alias
                })
            })
            formSelects.data('selectBjry', 'local', {
                arr: bjrySelList
            });
        }
    });
}




