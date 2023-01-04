/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2022/04/25
 * describe: 日志查询-规则模块
 */
// 分页重置查询参数
var paramD =[];
var tToD = {};
var gzldymcMap = {};
var zdList = [];
var reverseList = ['methodName'];
var table,laydate,form;

//规则例外类型
var gzhlType = ['YZHL','YZHL_ZF'];
var gzlwType = ['YZLW','GZLW_SH'];
// 规则验证场景
var gzyzcjMap = [
    ["_CJYZ", "创建验证"],["_QZYZ", "强制验证"],["_LCZF","流程转发"],["_LCZFH","流程转发后"],
    ["_XZBDCDY","新建项目"],["_DBYZ","登簿验证"],["_LCTH","流程退回"],
    ["_WWSQCJ","外网相关"],["_SLYMBCYZ","受理页面保存"]];

layui.use(['form', 'jquery', 'element', 'table', 'laydate'], function () {
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    table = layui.table;
    laydate = layui.laydate;
    form = layui.form;

    layui.sessionData('checkedData',null);

    // 加载页面日志类型参数
    pageInit();

    // 加载页面日志类型参数
    LogListCommon.initEventTypeToDesc();

    // 表格初始化
    DefaultTable.tableInit();

    // 表格按钮事件初始化
    DefaultTable.tableEvent();
    GzHlTable.tableEvent();
    GzYzTable.tableEvent();
    GzLwTable.tableEvent();

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
            if(paramD.eventName == "GZYZ"){
                if(isNullOrEmpty(paramD.yzrq)){
                    warnMsg("请选择验证日期");
                    return;
                }
                paramD['yzkssj'] = paramD.yzrq + " 00:00:00";
                paramD['yzjssj'] = paramD.yzrq + " 23:59:59";
                if(isNotBlank(paramD.yzcj)){
                    paramD['zhbs'] = paramD.yzcj;
                }
                GzYzTable.tableReloadData(paramD);
            }else if(paramD.eventName == "GZCZ"){
                DefaultTable.tableReloadData(paramD);
            }else if(gzlwType.indexOf(paramD.eventName) > -1){
                GzLwTable.tableReloadData(paramD);
            }else if(gzhlType.indexOf(paramD.eventName) > -1){
                GzHlTable.tableReloadData(paramD);
            }
            // 查询完成后，清除缓存选择数据
            layui.sessionData("checkedData", null);
        } else {
            layer.msg("请输入查询条件！");
        }
        return false;
    });

    form.on('select(eventName)', function(data){
        $("#gz-search-module").html("");
        var getTpl = null;
        if(data.value == "GZCZ"){
            getTpl = gzczSearchTpl.innerHTML;
            $("#search").parent(".layui-inline").removeClass("bdc-button-box-four").addClass("bdc-button-box");
            DefaultTable.tableInit();
        }
        if(data.value == "YZHL" || data.value == "YZHL_ZF" ){
            getTpl = gzhlSearchTpl.innerHTML;
            $("#search").parent(".layui-inline").removeClass("bdc-button-box").addClass("bdc-button-box-four");
            GzHlTable.tableInit();
        }
        if(data.value == "YZLW" || data.value == "GZLW_SH"){
            getTpl = gzhlSearchTpl.innerHTML;
            $("#search").parent(".layui-inline").removeClass("bdc-button-box").addClass("bdc-button-box-four");
            GzLwTable.tableInit(data.value);
        }
        if(data.value == "GZYZ"){
            getTpl = gzyzSearchTpl.innerHTML;
            $("#search").parent(".layui-inline").removeClass("bdc-button-box").addClass("bdc-button-box-four");
            GzYzTable.tableInit();
        }
        if(null != getTpl){
            var data = {
                gzldymcMap : gzldymcMap,
                currentTime : Format(formatDate(new Date()), "yyyy-MM-dd"),
            }
            laytpl(getTpl).render(data, function (html) {
                $("#gz-search-module").html(html);
            });
        }
        form.render();
        LogListCommon.initElement();
        LogListCommon.initSearchElement();
    });

    form.on('select(gzldymc)',function(data){
        var gzldyid = data.value;
        $("#yzcj").html("");
        $.each(gzyzcjMap,function(index, item){
            $("#yzcj").append('<option value="' + gzldyid + item[0]+ '">' + item[1] + '</option>')
        });
        form.render("select");
    });

    //点击高级查询
    $('#seniorSearch').on('click', function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $(this).parent().toggleClass('bdc-button-box-four');
        $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height());
        setHeight();
    });
});

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
        data[i].yzsj = formatTime(data[i].yzsj);
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
                exportExcel(data.content,cols, "all");
            }
        }
    });
    paramD["dctspz"]="";
}

// 页面下拉参数初始化
function pageInit(){
    // 获取字典项内容
    zdList = getMulZdList('eszdmc');
    // 初始化页面流程名称下拉选择框
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/process/gzldymcs",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        async: false,
        success: function (data) {
            $.each(data, function (index, item) {
                gzldymcMap[item.processDefKey] = item.name;
            });
        }, error: function (e) {
            response.fail(e,'');
        }
    });

    LogListCommon.initElement();
    LogListCommon.initSearchElement();
}

var DefaultTable = {
    tableInit : function (){
      $("#logGzhlTable-div").hide();
      $("#logGzyzTable-div").hide();
      $("#logTable-div").show();
      $("#logGzlwTable-div").hide();
        // 加载Table
      table.render({
          elem: '#logTable',
          toolbar: '#toolbarDemo',
          title: '规则日志查询列表',
          defaultToolbar: ['filter'],
          request: {
              limitName: 'size' //每页数据量的参数名，默认：limit
          },
          even: true,
          cols: [[
              {type: 'checkbox', fixed: 'left'},
              {field: 'xh',  title: '序号',width: 40, type: 'numbers'},
              {field: 'alias', title: '用户名',width: 100, },
              {field: 'ip', title: '操作IP',width: 120},
              {field: 'event', title: '日志类型',width: 130, templet: function(d){
                      if(d.event){
                          return "<span>"+tToD[d.event]+"</span>";
                      }else{
                          return "";
                      }
                  }
              },
              {field: 'viewTypeName', title: '操作名称', width: 200},
              {field: 'methodName', title: '方法名', width: 180},
              {field: 'methodArgs', title: '请求参数', width: 200},
              {field: 'methodResponse', title: '请求返回值', width: 200},
              {field: 'name', title: '请求链接', width: 150, hide:true},
              {field: 'time', title: '操作时间', width: 170, fixed: 'right', templet:function(d){return formatTime(d.time);}},
              {field: 'cz', title: '操作', width: 70, templet: '#barDemo', align: "center",fixed:"right"}
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
              reverseTableCell(reverseList);
              setHeightById("#logTable-div");
          }
      });
    },
    tableEvent : function (){
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
    },
    tableReloadData : function(param){
        // 重新请求
        table.reload("logTable", {
            url: '/realestate-inquiry-ui/log/v2/list',
            where: param,
            page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function (res, curr, count) {
                currentPageData = res.data;
                reverseTableCell(reverseList);
                removeModal();
                setHeightById("#logTable-div");
                table.resize('logTable');
            }
        });
    }
}

var GzHlTable = {
    tableInit : function(){
        $("#logGzhlTable-div").show();
        $("#logGzyzTable-div").hide();
        $("#logTable-div").hide();
        $("#logGzlwTable-div").hide();
        // 加载Table
        table.render({
            elem: '#logGzhlTable',
            toolbar: '#toolbarDemo',
            title: '规则日志查询列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [ [
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh',  title: '序号',width: 40, type: 'numbers'},
                {field: 'alias', title: '用户名',width: 100, },
                {field: 'remoteAddr', title: '操作IP',width: 120},
                {field: 'viewTypeName', title: '日志类型',width: 130},
                {field: 'slbh', title: '受理编号', width: 120},
                {field: 'bdcdyh', title: '不动产单元号', width: 250},
                {field: 'gzldyid', title: '流程名称', width: 180, templet:function(d){
                        if(isNotBlank(d.gzldyid) && isNotBlank(gzldymcMap[d.gzldyid])){
                            return gzldymcMap[d.gzldyid];
                        }
                        return "";}
                },
                {field: 'HLNR', title: '忽略内容', width: 250},
                {field: 'name', title: '请求链接', width: 150, hide:true},
                {field: 'time', title: '操作时间', width: 170, fixed: 'right', templet:function(d){return formatTime(d.time);}},
                {field: 'cz', title: '操作', width: 70, templet: '#barDemo', align: "center",fixed:"right"}
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
                reverseTableCell(reverseList);
                setHeightById("#logGzhlTable-div");
            }
        });
    },
    tableEvent : function (){
        // 监听表格操作栏按钮
        table.on('toolbar(logGzhlTable)', function(obj){
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
        table.on('tool(logGzhlTable)', function (obj) {
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
        table.on('checkbox(logGzhlTable)', function(obj){
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
    },
    tableReloadData : function(param){
        // 重新请求
        table.reload("logGzhlTable", {
            url: '/realestate-inquiry-ui/log/v2/list',
            where: param,
            page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function (res, curr, count) {
                currentPageData = res.data;
                reverseTableCell(reverseList);
                removeModal();
                setHeightById("#logGzhlTable-div");
                table.resize('logTable');
            }
        });
    }
}

var GzLwTable = {
    tableInit : function(type){
        $("#logGzlwTable-div").show();
        $("#logGzhlTable-div").hide();
        $("#logGzyzTable-div").hide();
        $("#logTable-div").hide();
        var lwCols = null;
        if(type == "YZLW"){
            lwCols = [
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh',  title: '序号',width: 40, type: 'numbers'},
                {field: 'alias', title: '用户名',width: 100, },
                {field: 'remoteAddr', title: '操作IP',width: 120},
                {field: 'viewTypeName', title: '日志类型',width: 130},
                {field: 'slbh', title: '受理编号', width: 120},
                {field: 'bdcdyh', title: '不动产单元号', width: 250},
                {field: 'LWNR', title: '例外内容', width: 250},
                {field: 'time', title: '操作时间', width: 170, fixed: 'right', templet:function(d){return formatTime(d.time);}},
                {field: 'cz', title: '操作', width: 70, templet: '#barDemo', align: "center",fixed:"right"}
            ];
        }else{
            lwCols = [
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh',  title: '序号',width: 40, type: 'numbers'},
                {field: 'principal', title: '用户名',width: 100, },
                {field: 'remoteAddr', title: '操作IP',width: 120},
                {field: 'viewTypeName', title: '日志类型',width: 130, templet:function(d){ return "规则例外审核";}},
                {field: 'CZ', title: '审核内容', width: 250,  templet:function(d){
                    var result = decryptMsg(d.CZ);
                    return result;
                }},
                {field: 'time', title: '操作时间', width: 170, fixed: 'right', templet:function(d){return formatTime(d.time);}},
                {field: 'cz', title: '操作', width: 70, templet: '#barDemo', align: "center",fixed:"right"}
            ];
        }

        // 加载Table
        table.render({
            elem: '#logGzlwTable',
            toolbar: '#toolbarDemo',
            title: '规则日志查询列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [lwCols],
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
                reverseTableCell(reverseList);
                setHeightById("#logGzlwTable-div");
            }
        });
    },
    tableEvent : function (){
        // 监听表格操作栏按钮
        table.on('toolbar(logGzlwTable)', function(obj){
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
        table.on('tool(logGzlwTable)', function (obj) {
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
        table.on('checkbox(logGzlwTable)', function(obj){
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
    },
    tableReloadData : function(param){
        // 重新请求
        table.reload("logGzlwTable", {
            url: '/realestate-inquiry-ui/log/v2/list',
            where: param,
            page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function (res, curr, count) {
                currentPageData = res.data;
                reverseTableCell(reverseList);
                removeModal();
                setHeightById("#logGzhlTable-div");
                table.resize('logTable');
            }
        });
    }
}

var GzYzTable = {
    tableInit : function(){
        $("#logGzhlTable-div").hide();
        $("#logGzyzTable-div").show();
        $("#logTable-div").hide();
        $("#logGzlwTable-div").hide();
        // 加载Table
        table.render({
            elem: '#logGzyzTable',
            toolbar: '#toolbarDemo',
            title: '规则日志查询列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh',  title: '序号',width: 50, type: 'numbers'},
                {field: 'yzbs', title: '验证标识',width: 150, hide:true},
                {field: 'zhbs', title: '组合标识',width: 250},
                {field: 'zhmc', title: '组合规则名称'},
                {field: 'yzrzh', title: '验证人', width: 120},
                {field: 'yzzgzsl', title: '子规则数量', width: 120, templet: function(d) {
                        if (d.yzzgzsl == d.yzcgsl) {
                            return '<span class="" style="color:limegreen;">'+d.yzzgzsl+'</span>';
                        }else {
                            return '<span class="" style="color:red;">'+d.yzzgzsl+'</span>';
                        }
                    }
                },
                {field: 'yzcgsl', title: '验证成功数量', width: 120, templet: function(d) {
                        if (d.yzzgzsl == d.yzcgsl) {
                            return '<span class="" style="color:limegreen;">'+ d.yzcgsl+'</span>';
                        }else {
                            return '<span class="" style="color:red;">'+ d.yzcgsl+'</span>';
                        }
                    }
                },
                {field: 'yzsj', title: '验证时间', width: 180, fixed: 'right', templet:function(d){return formatTime(d.yzsj);}},
                {field: 'cz', title: '操作', width: 130, fixed:"right", templet: '#gzyzBarDemo', align: "center"}
            ]],
            data: [],
            page: true,
            limits: [10, 50, 100],
            parseData: function (res) {
                // 设置选中行
                if(res.content && res.content.length > 0){
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function(key, value){
                            if(key == v.yzbs){
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
                reverseTableCell(reverseList);
                setHeightById("#logGzyzTable-div");
            }
        });
    },
    tableEvent : function (){
        // 监听表格操作栏按钮
        table.on('toolbar(logGzyzTable)', function(obj){
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
        table.on('tool(logGzyzTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'gzyzxq') {
                if(isNullOrEmpty(data.yzbs)){
                    warnMsg("未获取到验证标识，无法查看规则验证详情");
                    return;
                }
                layer.open({
                    title: '规则验证详情',
                    type: 2,
                    area: ['1150px','600px'],
                    content: '../log/gzyz/gzyzxq.html?yzbs=' + data.yzbs,
                    cancel: function(){
                    },
                    success: function () {
                    }
                });
            }
        });

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(logGzyzTable)', function(obj){
            var data = obj.type == 'one' ? [obj.data] : currentPageData;
            $.each(data, function(i, v) {
                var keyT = v.yzbs;
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
    },
    tableReloadData : function(param){
        // 重新请求
        table.reload("logGzyzTable", {
            url: '/realestate-inquiry-ui/log/list/group/gzyz',
            where: param,
            page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function (res, curr, count) {
                currentPageData = res.data;
                reverseTableCell(reverseList);
                removeModal();
                setHeightById("#logGzyzTable-div");
                table.resize('logTable');
            }
        });
    }
}

// 设置列表高度
function setHeightById(id) {
    var searchHeight = 131;
    $(id).find('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
    if($('.bdc-table-box').find(id + ' .layui-table-main>.layui-table').height() == 0){
        $(id).find('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
    }else {
        $(id).find('.layui-table-body').height($('.bdc-table-box').height() - searchHeight);
        $(id).find('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - searchHeight - 17);
    }
}

function decryptMsg(data){
    var msg = "";
    $.ajax({
        url: "/realestate-inquiry-ui/log/list/decrypt",
        type: "POST",
        data: data,
        async: false,
        success: function (res) {
            if(isNotBlank(res)){
                msg = res;
            }
        }, error: function (xhr, status, error) {
        }
    });
    return msg;
}
