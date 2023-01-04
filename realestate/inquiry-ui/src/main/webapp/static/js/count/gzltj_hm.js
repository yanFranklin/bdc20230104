layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

/** 定义全局变量*/

var queryObject;

//人员下拉框
var ryselecList="/realestate-inquiry-ui/slqktj/ry/select";
//流程下拉框
var lcselecList="/realestate-inquiry-ui/slqktj/lc/select";


var formSelects;
// 人员下拉数组
var bjrySelList = [];
// 流程下拉数组
var lcmcSelList = [];
// 获取打印类型
var printTypeList = "";

$(function(){
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
})

layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {

    //addModel();//添加遮罩
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        table = layui.table;
    formSelects = layui.formSelects;

    // 查询出证的范围
    searchPrintType();
    // 初始化下拉框
    initFormSelect(formSelects);

    table.render({
        elem: '#pageTable',
        toolbar: '#toolbarDemo',
        title: '工作量统计',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size', //每页数据量的参数名，默认：limit
            loadTotal:true,
            loadTotalBtn:false
        },
        even: true,
        cols: [[
            {field: 'userAlias', title: '姓名'},
            {field:'taskName',title: '节点名称'},
            {field:'procDefName',title: '流程名称'},
            {field: 'realCount', title: '工作量(次)'},
        ]],
        data: [],
        page: true,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function (data) {
            currentPageData = data.data;
            setHeight();
        }
    });


    // 日期控件
    laydate.render({
        elem: '#kssj',
        //value: new Date((new Date().getTime() - 90 * 24 * 60 * 60 * 1000)),
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


    // 监听表格操作栏按钮
    table.on('toolbar(pageTable)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'exportExcel':
                exportExcel();
                break;
        }
    });

    // 查询按钮点击事件
    form.on('submit(search)', function (data) {

        queryObject = data.field;
        var slrmc = formSelects.value('selectBjry', 'name').join(",");
        var processname = formSelects.value('selectLcmc', 'name').join(",");

        queryObject["slrmc"] = slrmc;// 受理人名称
        queryObject["processname"] = processname;// 流程
        queryObject["type"] = "";
        queryObject["printTypeList"] = printTypeList;
        addModel();
        // 重新请求
        table.reload("pageTable", {
            url: "/realestate-inquiry-ui/gzltj/ry/mx/all"
            ,where: queryObject
            , page: {
                curr: 1  //重新从第 1 页开始
            }
            ,done: function (res, curr, count) {
                currentPageData = res.data;
                removeModal();
                setHeight();
                table.resize('pageTable');
            }
        });
        return false;
    });

    $('.reseticon').on('click',function(item){
        $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
        $(this).hide();
        layui.form.render("select");
    })

});


/**
 * 加载下拉框
 */
function initFormSelect(formSelects){
    ryselect(formSelects);//人员
    lcselect(formSelects);//流程
}

function ryselect (formSelects){
    $.ajax({
        url: ryselecList,
        dataType: "json",
        success: function (data) {
            data.forEach(function(item){
                bjrySelList.push({
                    name: item.alias,
                    value: item.username
                })
            })
            formSelects.data('selectBjry', 'local', {
                arr: bjrySelList
            });
        }
    });
}

function lcselect (formSelects){
    $.ajax({
        url: lcselecList,
        dataType: "json",
        success: function (data) {
            data.forEach(function(item){
                lcmcSelList.push({
                    name: item.name,
                    value: item.key
                })
            })
            lcmcSelList.push({
                name: "查询出证",
                value: "cxcz"
            })
            formSelects.data('selectLcmc', 'local', {
                arr: lcmcSelList
            });
        }
    });
}

function exportExcel(){
    queryObject["type"] = "exportAll";
    queryObject["printTypeList"] = printTypeList;

    $.ajax({
        url: "/realestate-inquiry-ui/gzltj/ry/mx/all",
        type: 'GET',
        contentType: 'application/json',
        dataType: "json",
        data: queryObject,
        success: function (res) {
            exportAllData(res);
        },
        error: function (e) {
            setTimeout(removeModal, 100);
            removeModal();
        }
    });
    queryObject["type"] = "";
}

function searchPrintType(){
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/search/list/printType",
        type: 'GET',
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (res) {
            if(res){
                for(var i=0;i<res.length;i++){
                    printTypeList += res[i]+","
                }
            }
            if(printTypeList.length > 0){
                printTypeList = printTypeList.substring(0,printTypeList.length-1);
            }
        },
        error: function (e) {
            setTimeout(removeModal, 100);
            removeModal();
        }
    });
};


function exportAllData(data){
    if(data){
        var nameObj = {};
        var nameAndTaskObj = {};

        // 每个人每个节点的统计
        for(var i = 0;i<data.length;i++){
            var name = data[i].userAlias;
            var nameAndTask = data[i].userAlias + data[i].taskName;
            if(nameObj[name]){// 包含
                var currentObj = nameObj[name];
                var currentCount = parseInt(data[i].realCount);
                var counted = currentObj.realCount;
                currentObj['realCount'] = currentCount + counted;
                currentObj['procDefName'] = "";
                nameObj[name] = currentObj;
            }else{
                var nameObjCountObj = {};
                nameObjCountObj.userAlias = data[i].userAlias;
                nameObjCountObj.taskName = "合计";
                nameObjCountObj.procDefName = parseInt(data[i].realCount);
                nameObjCountObj.realCount = parseInt(data[i].realCount);
                nameObj[name] = nameObjCountObj;
            }

            if(nameAndTaskObj[nameAndTask]){// 包含
                var currentObj = nameAndTaskObj[nameAndTask];
                var currentCount = parseInt(data[i].realCount);
                var counted = currentObj.realCount;
                currentObj['realCount'] = currentCount + counted;
                currentObj['procDefName'] = "小计";
                nameAndTaskObj[nameAndTask] = currentObj;
            }else{
                var nameAndTaskObjCountObj = {};
                nameAndTaskObjCountObj.userAlias = data[i].userAlias;
                nameAndTaskObjCountObj.taskName = data[i].taskName;
                nameAndTaskObjCountObj.procDefName = "小计";
                nameAndTaskObjCountObj.realCount = parseInt(data[i].realCount);
                nameAndTaskObj[nameAndTask] = nameAndTaskObjCountObj;
            }
        }

        // 人合计
        var dataN = data;
        for(var key in nameObj){
            var inserted = "";
            for(var i = 0 ;i < data.length;i++){
                var name = data[i].userAlias;
                if(key == name && inserted.indexOf(name) == -1){
                    dataN.splice(i, 0, nameObj[key]);
                    inserted += name;
                }
            }
        }

        // 人和节点合计
        var dataR = dataN;
        for(var key in nameAndTaskObj){
            var inserted = "";
            for(var i = 0 ;i < dataN.length;i++){
                var nameAndTask = dataN[i].userAlias + dataN[i].taskName;
                if(key == nameAndTask && inserted.indexOf(nameAndTask) == -1){
                    dataR.splice(i, 0, nameAndTaskObj[key]);
                    inserted += nameAndTask;
                }
            }
        }

        // 标题
        var showColsTitle = ["姓名,节点名称,流程名称,工作量（次）"];
        // 列内容
        var showColsField = ["userAlias","taskName","procDefName","realCount"];
        // 列宽
        var showColsWidth = ["20","20","80","20"];

        // 设置Excel基本信息
        $("#fileName").val('工作量');

        var kssj = $("#kssj").val();
        var jzsj = $("#jzsj").val();
        if(kssj && jzsj){
            kssj = kssj.substring(0,10);
            jzsj = jzsj.substring(0,10);
            $("#sheetName").val(kssj+"~"+jzsj+'办件统计');
        }else{
            $("#sheetName").val('办件统计');
        }


        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#data").val(JSON.stringify(dataR));
        $("#mergeCellKey").val("userAlias,taskName");
        $("#mergeSameCell").val(true);
        $("#addBorder").val(true);

        // $("#mergeColumnCellKeyList").val(JSON.stringify(arrKey));
        $("#form").submit();
    }

}


