/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2022/04/25
 * describe: 日志查询-电子证照日志模块
 */
var zdList = [];
// 分页重置查询参数
var paramD =[];
var cols = [];
// 列表需要展示的列名称
var showElement = {
    DZZZCX : ['alias','remoteAddr','viewTypeName','slbh','zl','cqzh','qszt','time'],
    DZZZCJ : ['alias','remoteAddr','viewTypeName','data','time'],
    DZZZZX : ['alias','remoteAddr','viewTypeName','data','time'],
    DZZZZF : ['alias','remoteAddr','viewTypeName','data','time'],
};

var defaultCols = [
    {type: 'checkbox', fixed: 'left'},
    {field: 'xh',  title: '序号',width: 40, type: 'numbers'},
    {field: 'alias', title: '用户名',width: 100, hide:true, templet: function(d){ return fmtData(d.alias);}},
    {field: 'remoteAddr', title: '操作IP',width: 150, hide:true},
    {field: 'viewTypeName', title: '日志类型',width: 150, hide:true},
    {field: 'slbh', title: '受理编号', width: 150, hide:true},
    {field: 'zl', title: '坐落', width: 280, hide:true},
    {field: 'cqzh', title: '产权证号', width: 250, hide:true},
    {field: 'qszt', title: '权属状态', width: 150, hide:true, templet: function (d) {return convertZdDmToMc("qst", d.qszt, "zdList");}},
    {field: 'data', title: '内容', width: 350, hide:true},
    {field: 'time', title: '操作时间', width: 170, fixed: 'right', hide:true, templet:function(d){return formatTime(d.time);}},
    {field: 'cz', title: '操作', width: 70, templet: '#barDemo', align: "center", fixed:"right"},
];

var laydate,form,laytpl;
layui.use(['form', 'jquery', 'element', 'table', 'layer','laydate', 'laytpl'], function () {
    var layer = layui.layer;
    var table = layui.table;
    laytpl = layui.laytpl;
    laydate = layui.laydate;
    form = layui.form;

    layui.sessionData('checkedData',null);

    // 页面下拉等元素初始化
    pageInit();

    // 加载Table
    table.render({
        elem: '#logTable',
        toolbar: '#toolbarDemo',
        title: '电子证照日志查询列表',
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
        if($('#eventName').val() == ""){
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
        var dzzzcxElement = showElement[data.value];
        cols = LogListCommon.showColsByConfig(defaultCols, dzzzcxElement);
        if(isNotBlank(zdList)){
            $("#conditionKey").html("");
            $("#conditionKey").append('<option value="">请选择</option>');
            layui.each(zdList.eszdmc, function(index, item){
                if(dzzzcxElement.indexOf(item.DM) > -1){
                    var optionItem = '<option value="'+item.DM+'">'+ item.MC +'</option>';
                    $("#conditionKey").append(optionItem);
                }
            });
            form.render("select");
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
    // 获取字典项内容
    zdList = getMulZdList('qszt,eszdmc');

    var dzzzcxElement = showElement["DZZZCX"];
    cols = LogListCommon.showColsByConfig(defaultCols, dzzzcxElement);

    LogListCommon.initElement();
    LogListCommon.initSearchElement();

    if(isNotBlank(zdList) && isNotBlank(dzzzcxElement)){
        $("#conditionKey").append('<option value="">请选择</option>');
        layui.each(zdList.eszdmc, function(index, item){
            if(dzzzcxElement.indexOf(item.DM) > -1){
                var optionItem = '<option value="'+item.DM+'">'+ item.MC +'</option>';
                $("#conditionKey").append(optionItem);
            }

        });
        form.render("select");
    }
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
