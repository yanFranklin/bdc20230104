/**
 * author: <a href="mailto:chenyucheng@gtmap.cn>chenyucheng</a>
 * version 1.0.0  2019/05/28
 * describe: 打印留档台账
 */

layui.use(['form', 'jquery', 'element', 'table', 'laydate'], function () {
    var form = layui.form;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var table = layui.table;
    layui.sessionData('checkedData',null);
    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');

    // 加载Table
    table.render({
        elem: '#dyldTable',
        toolbar: '#toolbarDemo',
        title: '打印留档列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        even: true,
        cols: [[
            {type: 'id',title: 'id',hide:true},
            {type: 'checkbox', fixed: 'left'},
            {field: 'principal', title: '账号',width: 80},
            {field: 'alias', title: '姓名',width: 80},
            {field: 'organization', title: '所在部门',width: 150},
            {field: 'printType', title: '打印类型',width: 180},
            {field: 'zmbh', title: '打印编号',width: 270},
            {field: 'xmlStr', title: '打印数据内容',width: 400},
            {field: 'time', title: '操作时间',width: 170,
                templet:function(d){
                    return toLocalStringDate(new Date(parseInt(d.time)));
                }
            },
            {field: 'modelUrl', title: '打印模板路径',minWidth: 230},
            {field: 'dataUrl', title: '打印数据源',minWidth: 230},
            {field: 'zl', title: '坐落',minWidth: 100},
            {field: 'qlr', title: '权利人',minWidth: 100},
            {title:'操作', fixed:'right', toolbar: '#barDemoOp', width:90}
        ]],
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
            //table.resize('dyldTable');
            setHeight();
            if(moduleCode){
                setElementAttrByModuleAuthority(moduleCode);
            }
            //loadComplete();
        }
    });

    //精确到时分秒日期控件
    laydate.render({
        elem: '#begin',
        type: 'datetime',
        //value: preDate,
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#end').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#end',
        type: 'datetime',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#begin').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    // 监听表单提交事件
    form.on('submit(search)', function (data) {
        // 每次查询清除下导出缓存数据
        layui.sessionData("checkedData", null);

        if (data.field) {
            addModel();
            //tableReload('dyldTable', data.field, dataUrl);
            paramD = data.field;
            paramD["eventName"] = "PRINTLOG";
            // 重新请求
            table.reload("dyldTable", {
                url: '/realestate-inquiry-ui/log/list'
                ,where: paramD
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
                ,done: function (res, curr, count) {
                    currentPageData = res.data;
                    removeModal();
                    setHeight();
                    if(moduleCode){
                        setElementAttrByModuleAuthority(moduleCode);
                    }
                    table.resize('dyldTable');
                    //添加已选条目数
                    var checkedData = layui.sessionData('checkedData');
                    var arrcheck = Object.keys(checkedData);
                    var total = arrcheck.length;
                    $('#choosecount').html("已选"+total+"条");
                }
            });
        } else {
            layer.msg("请输入查询条件！");
        }
        return false;
    });

    //监听行工具事件，单据重现
    table.on('tool(dyldTable)', function (obj) {
        var data = obj.data;
        if(obj.event === 'djcx'){
            var checkData = data;
            var xmlStrUrl =  getIp() + '/realestate-inquiry-ui/log/openFr3'+ "/" + checkData.id;
            var fr3Url = "v2|designMode=false|frURL=" + checkData.modelUrl
                + "|dataURL=" + xmlStrUrl
                + "|updateUrl=http://oa.gtis.com.cn:80/platform/pluging/update.ini|hiddeMode=" + false;
            window.location.href = "eprt://" + fr3Url;
        }
    });

    // 监听表格操作栏按钮，预留，防止后面提出要批量重现
    table.on('toolbar(dyldTable)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'replayLog':
                replayLog();
                break;
            case 'exportExcel':
                exportExcel(checkStatus.data, obj.config.cols[0],"checked");
                break;
            case 'exportAllExcel':
                layer.confirm("是否导出全部数据？",{
                    title: "提示",
                    btn: ["确认","取消"],
                    btn1: function (index) {
                        exportAllExcel(checkStatus.data, obj.config);
                        layer.close(index);
                    },
                    btn2: function (index) {
                        obj.config.where.type = "";
                        return;
                    }
                })
                break;
        }
    });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(dyldTable)', function(obj){
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
        //添加已选条目数
        var checkedData = layui.sessionData('checkedData');
        var arrcheck = Object.keys(checkedData);
        var total = arrcheck.length;
        $('#choosecount').html("已选"+total+"条");
    });

    //x 的事件
    form.on('select', function (data) {
        if($(this).text() == "请选择"){
            $(data.elem).parent().find('.reseticon').hide();
        }else{
            $(data.elem).parent().find('.reseticon').show()
        }
    });
    $('.reseticon').on('click',function(item){
        $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
        $(this).hide();
        layui.form.render("select");
    })
    $('#reset').on('click',function(item){
        $('#eventName').find("option").removeAttr("selected");
        $('.bdc-percentage-container').find('.layui-form')
            .find('select').find("option:eq(0)")
            .attr("selected","selected");
        setTimeout($('.bdc-percentage-container').find('.layui-form')
            .find('select').parent().find('input').val(''),100);
        $('.reseticon').hide();
    })
});

/**
 * 日志重现
 */
function replayLog() {
    layui.use(['layer', 'table'], function() {
        var table = layui.table;
        var layer = layui.layer;
        var checkStatus = table.checkStatus('dyldTable');
        //console.log(checkStatus);
        if(checkStatus.data.length == 0) {
            warnMsg('请选择一条日志记录！');
            return false;
        }
        if(checkStatus.data.length > 1) {
            warnMsg('只可选择一条日志记录重现！');
            return false;
        }
        var checkData = checkStatus.data[0];
        var xmlStrUrl =  getIp() + '/realestate-inquiry-ui/log/openFr3'+ "/" + checkData.id;
        var fr3Url = "v2|designMode=false|frURL=" + checkData.modelUrl
            + "|dataURL=" + xmlStrUrl
            + "|updateUrl=http://oa.gtis.com.cn:80/platform/pluging/update.ini|hiddeMode=" + false;

        window.location.href = "eprt://" + fr3Url;
    });
}

//设置IP
function getIp() {
    return document.location.protocol + "//" + window.location.host;
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
        if(cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar && cols[index].field != "modelUrl" && cols[index].field != "dataUrl"){
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
        data[i].time = toLocalStringDate(new Date(data[i].time));
    }
    // 设置Excel基本信息
    $("#fileName").val('打印留档');
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
    var size = obj.page.count;
    var url = obj.url+"?page=0&&size="+size;//"/realestate-inquiry-ui/log/list"
    var paramData = obj.where;
    paramData["type"] = "export";
    paramData["dctspz"] = true;
    $.ajax({
        url: url,
        dataType: "json",
        data: paramData,
        success: function (data) {
            if(data.code == 0){//查询成功
                exportExcel(data.content,cols, "all");
            }
        }
    });
    paramD["type"]="";
}

// 重写方法，自定义格式化日期
function toLocalStringDate (date) {
    // 按自定义拼接格式返回
    return date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-" + addZero(date.getDate()) + " " +
        addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
}

// 补0   例如 2018/7/10 14:7:2  补完后为 2018/07/10 14:07:02
function addZero(num) {
    if(num<10){
        return "0" + num;
    }
    return num;
}


