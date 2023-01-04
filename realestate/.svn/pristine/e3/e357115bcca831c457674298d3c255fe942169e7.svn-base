/**
 * author: <a href="mailto:chenyucheng@gtmap.cn>chenyucheng</a>
 * version 1.0.0  2019/05/28
 * describe: 日志查询
 */
var eventName = '';
// 重现日志记录id
var logid = $.getUrlParam("logid");
// 查询台账重现数据加载url
var replayDataUrl = '/realestate-inquiry-ui/log/query/review';
// 是否重现
var replayFlag = !isNullOrEmpty(logid);
// 分页重置查询参数
var paramD =[];

var tToD = {};

layui.use(['form', 'jquery', 'element', 'table', 'laydate'], function () {
    initEventTypeToDesc();

    var form = layui.form;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var table = layui.table;

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');

    layui.sessionData('checkedData',null);
    // 加载Table
    table.render({
        elem: '#logTable',
        toolbar: '#toolbarDemo',
        title: '日志查询列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        even: true,
        cols: [[
            {type: 'id',title: 'id',hide:true},
            {type: 'checkbox', fixed: 'left'},
            {field: 'xh',  title: '序号',width: 70, type: 'numbers'},
            {field: 'principal', title: '用户名',width: 100},
            {field: 'alias', title: '姓名',width: 100},
            {field: 'ip', title: '操作IP',width: 120,
                templet:function(d) {
                    if(!isNullOrEmpty(d.clientIp)){
                        return d.clientIp;
                    }

                    if(!isNullOrEmpty(d.ip)){
                        return d.ip;
                    }

                    if(!isNullOrEmpty(d.remoteAddr)){
                        return d.remoteAddr;
                    }

                    return "";
                }
            },
            {field: 'eventName', title: '日志类型',width: 120,
                templet:function(d){
                    if(d.eventName){
                        return "<span>"+tToD[d.eventName]+"</span>";
                    }else{
                        return "";
                    }
                }
            },
            {field: 'zyTzName', title: '所属资源',width: 120,hide:true},
            {field: 'modelUrl', title: '打印模板路径',width: 120,hide:true},
            {field: 'dataUrl', title: '打印数据源',width: 120,hide:true},
            {field: 'xmlStr', title: '打印数据源xml',width: 120,hide:true},
            {field: 'request', title: '接口参数',width: 120,hide:true},
            {field: 'response', title: '接口响应',width: 120,hide:true},
            {field: 'viewTypeName', title: '功能名称',
                templet:function(d){
                    var str = $('select[name="eventName"]').find('option:selected').text();
                    if(!d.viewTypeName && str!="请选择"){
                        return "<span>"+str+"</span>";
                    }else if(d.viewTypeName){
                        return "<span>"+d.viewTypeName+"</span>";
                    }else{
                        return "<span>暂无</span>";
                    }
                },width: 240},
            {field: 'slbh', title: '受理编号',width: 120},
            {field: 'zl', title: '坐落',width: 200},
            {field: 'bdcdyh', title: '不动产单元号',width: 180},
            {field: 'qlr', title: '权利人',width: 120},
            {field: 'ywr', title: '义务人',width: 120},
            {field: 'paramCha', title: '操作内容',width: 180,fixed: 'right'},
            {field: 'time', title: '操作时间',width: 170,
                 templet:function(d){
                    return toLocalStringDate(new Date(parseInt(d.time)));
                 },fixed: 'right'
            }
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
            var reverseList = ['zl'];
            reverseTableCell(reverseList);
            //table.resize('logTable');
            setHeight();
            loadComplete();
            if(moduleCode){
                setElementAttrByModuleAuthority(moduleCode);
            }
        }
    });

    // 设置默认查询一周内的日志
    var nowDate = new Date();
    var preDate = new Date(nowDate.getTime() - 7*24*60*60*1000);
    //精确到时分秒日期控件
    laydate.render({
        elem: '#begin',
        type: 'datetime',
        value: preDate,
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
        if($('#logSource').val()!="" && $('#eventName').val() == ""){
            layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + "请选择日志类型");
            return;
        }
        if (data.field) {
            addModel();
            // 查询打印日志时才有此参数
            if(data.field.eventName != 'PRINT') {
                data.field.response = '';
            }
            //tableReload('logTable', data.field, dataUrl);

            paramD = data.field;
            paramD["type"] = "";
            // 重新请求
            table.reload("logTable", {
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
                    table.resize('logTable');
                }
            });
        } else {
            layer.msg("请输入查询条件！");
        }
        return false;
    });

    // 监听日志类型切换
    form.on('select(logSource)', function(data) {
        var sourceType = data.value;
        var optHtml = ""
        if(sourceType === 'bdc') {//业务系统
            $(".cloud").attr("disabled","disabled");
            $(".source").removeAttr("disabled");
            $("#viewTypeName").removeAttr("disabled");
            //$("#alias").removeAttr("disabled", "disabled");
            if(!$('#eventName').find("option:selected").hasClass("source")){
                $('#eventName').find("option").removeAttr("selected");
                $('#eventName').find(".source:eq(0)").attr("selected", "selected");
                $('#eventName').parent().find('.reseticon').show();
            }
        }else if(sourceType === 'cloud'){// 大云
            $("#viewTypeName").val("");
            $("#viewTypeName").attr("disabled", "disabled");
            $(".source").attr("disabled","disabled");
            $(".cloud").removeAttr("disabled");
            //$("#alias").attr("disabled", "disabled");
            if(!$('#eventName').find("option:selected").hasClass("cloud")){
                $('#eventName').find("option").removeAttr("selected");
                $('#eventName').find(".cloud:eq(0)").attr("selected", "selected");
                $('#eventName').parent().find('.reseticon').show();
            }
        }else{
            $('#eventName').parent().find('.reseticon').hide();
            $('#eventName').find("option").removeAttr("selected");
            $('#eventName').find("option:contains('请选择')").attr("selected", "selected");
            $(".cloud").removeAttr("disabled");
            $(".source").removeAttr("disabled");
            $("#alias").removeAttr("disabled", "disabled");
        }
        layui.form.render("select");
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
            case 'replayLog':
                replayLog();
                break;
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
        $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);
        setHeight();
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
        var checkStatus = table.checkStatus('logTable');
        //console.log(checkStatus);
        if(checkStatus.data.length == 0) {
            layer.msg('请选择一条日志记录！');
            return false;
        }
        if(checkStatus.data.length > 1) {
            layer.msg('只可选择一条日志记录重现！');
            return false;
        }
        var checkData = checkStatus.data[0];
        if(checkData.eventName == 'PRINTLOG') {
            // var access_token = getToken();
            // var url = getIp() + '/realestate-inquiry-ui/log/print/review/' + checkData.id;
            // url += '?access_token=' + access_token;
            // url += '&templatName=' + (JSON.parse(checkData.param)).templatName;
            // window.location.href = "eprt://" + encodeURI(encodeURI(url));
            // print(checkData.modelUrl,checkData.dataUrl,false);
            var xmlStrUrl =  getIp() + '/realestate-inquiry-ui/log/openFr3'+ "/" + checkData.id;
            var fr3Url = "v2|designMode=false|frURL=" + checkData.modelUrl
                + "|dataURL=" + xmlStrUrl
                + "|updateUrl=http://oa.gtis.com.cn:80/platform/pluging/update.ini|hiddeMode=" + false;

            window.location.href = "eprt://" + fr3Url;

        }else{
            window.open( getIp() + replayDataUrl + "/" + checkData.id);
        }

        // 验证日志
      /*  if(checkLog(checkData.id)) {
            if(checkData.eventName == 'QUERY') {
                window.open( getIp() + checkData.viewAddress + "?logid=" + checkData.id);
            }else if(checkData.eventName == 'EXPORT' ||
                checkData.eventName == 'DOWNLOAD' ) {
                var file = JSON.parse(checkData.response);
                layer.confirm('是否需要下载该文件？', function(index) {
                    window.open('/realestate-inquiry-ui/file/path/download?filePath=' + file.filePath + "&fileName=" + file.fileName);
                    layer.close(index);
                });
            }else if(checkData.eventName == 'PRINT') {
                var access_token = getToken();
                var url = getIp() + '/realestate-inquiry-ui/log/print/review/' + checkData.id;
                url += '?access_token=' + access_token;
                url += '&templatName=' + (JSON.parse(checkData.param)).templatName;
                window.location.href = "eprt://" + encodeURI(encodeURI(url));

            }
        }*/
    });
}

//设置IP
function getIp() {
    return document.location.protocol + "//" + window.location.host;
}

/**
 * 验证日志是否可以重现
 * @param logid
 * @returns {boolean}
 */
function checkLog(logid) {
    var flag = false;
    addModel();
    $.ajax({
        url: '/realestate-inquiry-ui/log/check/' + logid,
        type: 'GET',
        async: false,
        success: function(data) {
            console.log(data);
            if(data.success) {
                flag = true;
            }else {
                layer.alert('无法重现：' + data.message, {title: '提示'});
            }
            removeModal();
        },
        error: function(e) {
            ajaxError(e);
        }
    });
    return flag;
}

/**
 * 加载完成后工具栏刷新了  上传日志隐藏日志重现按钮
 */
function loadComplete() {
    var btnDom = $('.layui-btn-container');
    // 上传日志无需重现
    if(eventName === 'UPLOAD' && !btnDom.hasClass('bdc-hide')) {
        btnDom.addClass('bdc-hide');
    }else if(btnDom.hasClass('bdc-hide')){
        btnDom.removeClass('bdc-hide');
    }
}

/**
 * 加载完成后工具栏刷新了  上传日志隐藏日志重现按钮
 */
function queryOprationCol(){
    $.ajax({
        url: '/realestate-inquiry-ui/log/config',
        type: 'GET',
        async: false,
        contentType: 'application/json',
        success: function (data) {
            // 日志重现时只加载列 隐藏搜索框
            if(replayFlag) {
                // 隐藏搜索框
                $('.bdc-search-box').addClass('bdc-hide');
                //_tableConfig.url = replayDataUrl + "/" + logid;
            }else {
                // 工具栏
                if (data.toolbar) {
                    globalToolbarList = data.toolbar;
                }
                if (data.tool) {
                    globalTooList = data.tool;
                }
                if (data.query) {
                    globalQueryList = data.query;
                }
            }
        }
    });
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
        if(cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar){
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
        data[i].eventName = tToD[data[i].eventName];
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
                exportExcel(data.content,cols, "all");
            }
        }
    });
    paramD["dctspz"]="";
}

// 重写方法，自定义格式化日期
function toLocalStringDate (date) {
    // 按自定义拼接格式返回
    return date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-" + addZero(date.getDate()) + " " +
        addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
}

// 补0   例如 2018/7/10 14:7:2  补完后为 2018/07/10 14:07:02
function addZero(num) {
    if(num<10)
        return "0" + num;
    return num;
}

/**
 *
 * @param type 日志类型
 * @param desc 描述
 */
function initEventTypeToDesc(type,desc){
    var selectOps = $('#eventName').find('option');
    for(var i = 0;i<selectOps.length;i++){
        if($(selectOps[i]).val() != "" && $(selectOps[i]).text()!=""){
            tToD[$(selectOps[i]).val()] = $(selectOps[i]).text();
        }
    }
    // 手动插入不在日志类型里面，但是不选日志类型，能查出来的无用日志
    tToD["AUTHENTICATION_SUCCESS"] = "请求认证成功"
    tToD["AUTHORIZATION_SUCCESS"] = "请求授权成功"
    tToD["AUTHORIZATION_FAILURE"] = "请求授权失败"
    tToD["AUTHENTICATION_FAILURE"] = "请求认证失败"
    /*tToD["YZHL"] = "验证忽略";
    tToD["PRINTLOG"] = "打印";
    tToD["SDDB"] = "手动登簿";
    tToD["EXPORT"] = "导出";
    tToD["UPLOAD"] = "上传";
    tToD["DOWNLOAD"] = "下载";
    tToD["DELETE"] = "业务系统删除";
    tToD["XXBL"] = "信息补录";
    tToD["CREATE"] = "创建";
    tToD["QUERY"] = "查询";
    tToD["UPDATE"] = "更新";
    tToD["SAVE"] = "保存";
    tToD["OTHER"] = "其他";
    tToD["DSFJK"] = "第三方接口";
    tToD["LOGIN_SUCCESS"] = "系统登录";
    tToD["LOGOUT_SUCCESS"] = "系统登出";
    tToD["FLOWABLE_DELETE"] = "流程删除";
    tToD["FLOWABLE_CLOSE"] = "关闭";
    tToD["FLOWABLE_ABANDONED"] = "废弃";
    tToD["FLOWABLE_HANG"] = "挂起";
    tToD["FLOWABLE_ACTIVATION"] = "激活";
    tToD["FLOWABLE_DISABLED"] = "锁定";
    tToD["FLOWABLE_ENABLED"] = "解除";
    tToD["FLOWABLE_DELEGATION"] = "委托/代理";
    tToD["FLOWABLE_CLAIM"] = "流程认领";
    tToD["FLOWABLE_COMPLETE"] = "流程转发";
    tToD["FLOWABLE_BACK"] = "流程退回";
    tToD["FLOWABLE_FETCH_BACK"] = "流程取回";
    tToD["FLOWABLE_END"] = "流程办结";
    tToD["GATEWAY_EVENT"] = "全量网关";

    tToD["FLOWABLE_UNCLAIM"] = "流程取消认领";
    tToD["GATEWAY_EVENT"] = "全量网关";
    tToD["GZYZ"] = "规则验证";
    tToD["GZCZ"] = "规则操作";
    tToD["YZLW"] = "验证例外";*/
}


function getToken() {
    var token = '';
    var clientId = '';
    var clientSecret = '';
    var oauthUrl = '';
    // 遮罩层
    //var laodIndex = layer.load(2, {shade: [0.1, '#fff']});
    addModel();
    $.ajax({
        url: '/realestate-inquiry-ui/config/security/param',
        type: 'GET',
        async: false,
        success: function (data) {
            if (data && data.oauthUrl && data.clientId && data.clientSecret) {
                clientId = data.clientId;
                clientSecret = data.clientSecret;
                oauthUrl = data.oauthUrl;
            }
        }, error: function () {
            layer.msg("获取token失败，请检查配置信息是否正确！");
        }
    });
    if (clientId && clientSecret && oauthUrl) {
        $.ajax({
            url: oauthUrl,
            data: {
                client_id: clientId,
                client_secret: clientSecret
            },
            type: 'POST',
            async: false,
            success: function (data) {
                if (data != "null" && data != undefined && data != "undefined") {
                    if (data.access_token != undefined) {
                        token = data.access_token;
                    }
                }
            }, error: function () {
                layer.msg("获取token失败，请检查配置信息是否正确！");
            }
        });
    }
    //layer.close(laodIndex);
    removeModal();
    return token;
}

function openFr3(xml,url){
    $.ajax({
        url: '/realestate-inquiry-ui/log/openFr3',
        data: {
            xml: xml,
            url: url
        },
        success: function (data) {

        }
    });
}


