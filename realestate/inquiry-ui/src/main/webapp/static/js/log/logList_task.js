/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2022/04/25
 * describe: 日志查询-任务列表操作模块
 */
// 分页重置查询参数
var paramD =[];
var cols = [];
// 列表需要展示的列名称
var showElement = {
    //['principalName','remoteAddr','event','processInstanceId','slbh','processDefinitionName','activityName','reson','name','time']
    FLOWABLE_CREATE : ['principalName','remoteAddr','event','processInstanceId','processDefinitionName','activityName','time'],
    FLOWABLE_DELETE : ['principalName','remoteAddr','event','processInstanceId','processDefinitionName','reason','slbh','time',"bdcdyh","zl","qlr","qxdm"],
    FLOWABLE_CLAIM : ['principalName','remoteAddr','event','processInstanceId','processDefinitionName','activityName','time'],
    FLOWABLE_UNCLAIM: ['principalName','remoteAddr','event','processInstanceId','slbh','processDefinitionName','activityName','time'],
    FLOWABLE_COMPLETE: ['principalName','remoteAddr','event','processInstanceId','slbh','processDefinitionName','activityName','time','bdcdyh','zl','qlr','ywr','qxdm','djyy','sply'],
    FLOWABLE_BACK: ['principalName','remoteAddr','event','processInstanceId','slbh','processDefinitionName','activityName','time','bdcdyh','opinion','zl',"qlr",],
    FLOWABLE_END: ['principalName','remoteAddr','event','processInstanceId','slbh','processDefinitionName','activityName','time','bdcdyh','zl',"qlr"],

// ['alias','remoteAddr','viewTypeName','gzlslid','processInstanceId','methodName','methodArgs','methodResponse','reason','name','time']
    PROCESS_HANG: ['alias','ip','viewTypeName','gzlslid','methodName','methodArgs','methodResponse','reason','time'],
    PROCESS_ACTIVE:['alias','ip','viewTypeName','methodName','methodArgs','methodResponse','reason','time'],
    PROCESS_REVOKE: ['alias','ip','viewTypeName','processInstanceId','methodName','methodArgs','reason','time'],
    PROCESS_URGENT:['alias','ip','viewTypeName','gzlslid','methodName','methodArgs','methodResponse','reason','time'],
    PROCESS_CANCEL_URGENT:['alias','ip','viewTypeName','gzlslid','methodName','methodArgs','methodResponse','reason','time'],
    PROCESS_STOP:['alias','ip','viewTypeName','gzlslid','methodName','methodArgs','methodResponse','reason','time'],
    TASK_BACK_CHECK:['alias','ip','viewTypeName','gzlslid','methodName','methodArgs','methodResponse','reason','time'],
    TASK_RETRIEVE:['alias','ip','viewTypeName','methodName','methodArgs','methodResponse','time'],
    TASK_URGENT:['alias','ip','viewTypeName','gzlslid','methodName','methodArgs','methodResponse','time'],
    TASK_CANCEL_URGENT:['alias','ip','viewTypeName','gzlslid','methodName','methodArgs','methodResponse','time'],
    TASK_DELETE:['alias','ip','viewTypeName','processInstanceId','slbh','methodName','methodArgs','reason','time'],
};

var defaultCols = [
    {type: 'checkbox', fixed: 'left'},
    {field: 'xh',  title: '序号',width: 40, type: 'numbers'},
    {field: 'alias', title: '用户名',width: 100, hide:true, templet: function(d){ return fmtData(d.alias);}},
    {field: 'principalName', title: '用户名',width: 100, hide:true, templet: function(d){
            if(isNotBlank(d.principalName)){
                return fmtData(d.principalName);
            }else{
                return fmtData(d.alias);
            }
        }},
    {field: 'remoteAddr', title: '操作IP',width: 150, hide:true},
    {field: 'ip', title: '操作IP',width: 150, hide:true},
    {field: 'viewTypeName', title: '日志类型',width: 150, hide:true},
    {field: 'event', title: '日志类型',width: 150, hide:true, templet: function(d){
            if(d.event){
                return "<span>"+tToD[d.event]+"</span>";
            }else{
                return "";
            }
        }
    },
    {field: 'gzlslid', title: '工作流实例ID', width: 120, hide:true},
    {field: 'processInstanceId', title: '工作流实例ID', width: 130, hide:true},
    {field: 'processDefinitionName', title: '流程名称', minWidth: 180, hide:true},
    {field: 'slbh', title: '受理编号', width: 150, hide:true},
    {field: 'bdcdyh', title: '不动产单元号', width: 220, hide:true},
    {field: 'zl', title: '坐落', width: 180, hide:true},
    {field: 'qlr', title: '权利人', width: 120, hide:true},
    {field: 'ywr', title: '义务人', width: 120, hide:true},
    {field: 'qxdm', title: '区县代码', width: 120, hide:true},
    {field: 'sply', title: '审批来源', width: 120, hide:true},
    {field: 'methodName', title: '方法名', width: 180, hide:true},
    {field: 'methodArgs', title: '请求参数', width: 200, hide:true},
    {field: 'methodResponse', title: '请求返回值', width: 200, hide:true, templet:function(d) {
            if(isNotBlank(d.methodResponse)){
                return  d.methodResponse.replaceAll("\"", "");
            }else{
                return "";
            }}
    },
    {field: 'activityName', title: '节点', width: 120, hide:true, templet: function(d){ return fmtData(d.activityName); }},
    {field: 'reason', title: '原因', width: 200, hide:true, templet: function(d){return fmtReson(d.reason);}},
    {field: 'opinion', title: '原因', width: 200, hide:true, templet: function(d){return fmtReson(d.opinion);}},
    {field: 'name', title: '请求链接', width: 150, hide:true, hide:true},
    {field: 'time', title: '操作时间', width: 170, fixed: 'right', hide:true, templet:function(d){return formatTime(d.time);}},
    {field: 'cz', title: '操作', width: 70, templet: '#barDemo', align: "center", fixed:"right"},
];


var form, laytpl;
layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'laytpl'], function () {
    var layer = layui.layer;
    var table = layui.table;
    form = layui.form;
    laytpl = layui.laytpl;
    layui.sessionData('checkedData',null);

    // 页面下拉等元素初始化
    pageInit();

    // 加载页面日志类型参数
    LogListCommon.initEventTypeToDesc();

    // 加载Table
    table.render({
            elem: '#logTable',
            toolbar: '#toolbarDemo',
            title: '任务列表日志查询列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [defaultCols],
            data: [],
            page: true,
            limits: [10, 50, 100, 200, 500],
            parseData: function (res) {
                // 设置选中行
                if(res.content && res.content.length > 0){
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function(key, value){
                            if(key == v.id){
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
            done: function () {
                setHeight();
            }
        });

    // 监听表单提交事件
    form.on('submit(search)', function (data) {
        if($('#logSource').val()!="" && $('#eventName').val() == ""){
            layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + "请选择日志类型");
            return;
        }
        if (data.field) {
            addModel();
            paramD = data.field;
            paramD = LogListCommon.handlerQueryParam(paramD);
            // 重新请求
            table.reload("logTable", {
                url: '/realestate-inquiry-ui/log/v2/list',
                where: paramD,
                page: {
                    curr: 1  //重新从第 1 页开始
                },
                cols: [cols],
                done: function (res, curr, count) {
                    currentPageData = res.data;
                    removeModal();
                    setHeight();
                    table.resize('logTable');
                }
            });
            // 查询完成后，清除缓存选择数据
            layui.sessionData("checkedData", null);
        } else {
            layer.msg("请输入查询条件！");
        }
        return false;
    });

    // 日志类型下拉选择事件
    form.on('select(eventName)', function(data){
            var className = $(data.elem).find("option:selected").attr("class");
            if(isNotBlank(className)){
                var getTpl = taskSearchTpl.innerHTML;
                var json = {
                    zdList : zdList,
                    currentTime : Format(formatDate(new Date()), "yyyy-MM-dd"),
                    event: data.value,
                    cols: showElement[data.value],
                }
                laytpl(getTpl).render(json, function (html) {
                    $("#task-search-content").html(html);
                });
                form.render();
                LogListCommon.initSearchElement();

                // 设置搜索框区域的高度
                $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height());
                setHeight();

                var $input = $("#principalNameDiv").find("input");
                cols = LogListCommon.showColsByConfig(defaultCols, showElement[data.value]);
                if(className.indexOf("taskList") > -1){
                    $input.attr("name","alias");
                    $input.attr("id","alias");
                }else{
                    $input.attr("name","principalName");
                    $input.attr("id","principalName");
                }
            }
        });

    // 监听表格操作栏按钮
    table.on('toolbar(logTable)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                    break;
                case 'exportAllExcel':
                    exportAllExcel(checkStatus.data, obj.config);
                    break;
            }
        });

    //监听工具条
    table.on('tool(logTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'xq') {
                console.info(data);
                layer.open({
                    type: 2,
                    title: '详情',
                    area: ["640px","600px"],
                    shadeClose: true,
                    content: './logxq.html',
                    success: function(layero, index){
                        var iframeWin = window[layero.find('iframe')[0]['name']];
                        iframeWin.loadXqData(data, zdList);
                    },
                    yes: function(index, layero){
                        layer.close(index);
                    }
                });
            }
        });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(logTable)', function(obj){
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;

            $.each(data, function(i, v) {
                var keyT = v.id;
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: keyT, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: keyT, remove: true
                    });
                }
            });
        });

    //点击高级查询
    $('#seniorSearch').on('click', function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $(this).parent().toggleClass('bdc-button-box-four');
        $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height());
        setHeight();
    });

});

// 页面下拉参数初始化
function pageInit(){
    // 初始化页面流程名称下拉选择框
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/process/gzldymcs",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        async: false,
        success: function (data) {
            $.each(data, function (index, item) {
                $('#processDefinitionName').append(new Option(item.name, item.name));// 下拉菜单里添加元素
            });
            layui.form.render("select");
        }, error: function (e) {
            response.fail(e,'');
        }
    });
    // 初始化节点
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/process/node/config",
        data: {},
        success: function (data) {
            console.info(data);
            $.each(data, function (index, item) {
                $('#activityName').append(new Option(item, item));// 下拉菜单里添加元素
            });
            layui.form.render("select");
        }, error: function (e) {
            response.fail(e, '');
        }
    });

    // 获取字典项内容
    zdList = getMulZdList('eszdmc');

    cols = LogListCommon.showColsByConfig(defaultCols, showElement["FLOWABLE_CREATE"]);
    var getTpl = taskSearchTpl.innerHTML
    var data = {
        zdList : zdList,
        currentTime : Format(formatDate(new Date()), "yyyy-MM-dd"),
        event: "FLOWABLE_CREATE",
        cols: showElement["FLOWABLE_CREATE"],
    }
    laytpl(getTpl).render(data, function (html) {
        $("#task-search-content").html(html);
    });

    form.render();

    LogListCommon.initElement();
    LogListCommon.initSearchElement();

}

/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.24 14:45
 * @author zhuyong
 * @return
 */
function exportExcel(d, cols, type){
    var checkedData = layui.sessionData('checkedData');
    if($.isEmptyObject(checkedData) && type == "checked"){
        layer.alert("请选择需要导出Excel的数据行！", {title: '提示'});
        return;
    }
    // 标题
    var showColsTitle = [];
    // 列内容
    var showColsField = [];
    // 列宽
    var showColsWidth = [];
    for(var index in cols){
        if(cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar && cols[index].field != 'cz'){
            showColsTitle.push(cols[index].title);
            showColsField.push(cols[index].field);
            if(cols[index].width > 0){
                showColsWidth.push(parseInt(cols[index].width / 100 * 15));
            }else if(cols[index].minWidth > 0){
                showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
            }else{
                showColsWidth.push(200 / 100 * 15);
            }
        }
    }

    var data = new Array();
    if(type == "checked"){
        $.each(checkedData, function(key, value){
            data.push(value);
        })
    }else{
        data = d;
    }

    for(var i = 0; i < data.length; i++){
        data[i].xh   = i + 1;
        data[i].time = formatTime(data[i].time);
        data[i].event = tToD[data[i].event];
        if(!data[i].viewTypeName){
            var str = $('select[name="eventName"]').find('option:selected').text();
            if(str!= "登录" && str!= "登出"){
                str+="流程"
            }else{
                str+="系统"
            }
            data[i].viewTypeName = str;
        }
    }
    // 设置Excel基本信息
    $("#fileName").val('日志查询');
    $("#sheetName").val('统计表');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#data").val(JSON.stringify(data));
    $("#form").submit();
}

/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.25 14:45
 * @author chenyucheng
 * @return
 */
function exportAllExcel(data, obj){
    var cols = obj.cols[0]
    var url = obj.url;
    var paramData = obj.where;
    paramData["dctspz"] = true;
    $.ajax({
        url: url,
        dataType: "json",
        data: paramData,
        success: function (data) {
            if(data.code == 0){//查询成功
                exportExcel(data.content, cols, "all");
            }
        }
    });
    paramD["dctspz"]="";
}
