/**
 * @author <a href ="mailto:wutao2@gtmap.cn">wutao</a>
 * @description 证书、证明统计 js
 */
layui.extend({
    formSelects: '../../static/lib/form-select/formSelects-v4'
});
// 部门下拉数组
var myChart, tabName = "dyltj";
var BASE_URL = "/realestate-inquiry-ui/rest/v1.0";
var zdList = {bm: ""};// 定义字典信息全局变量
var formSelects;
// 部门下拉数组
var bmmcSelList = [];
var UserCache = new Map();
var zdList = getMulZdList("zslx");
var bmData = [];
layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element;
    formSelects = layui.formSelects;

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if(moduleCode){
        setElementAttrByModuleAuthority(moduleCode);
    }

    //2.默认渲染部门名称，选中名称后，根据名称渲染办理人员
    getDataByAjax('/rest/v1.0/process/listOrgs','','GET',function (data) {
        // var bmData = [];
        data.forEach(function (v) {
            bmData.push({"name": v.name, "value": v.id, "code": v.code});
        });
        formSelects.data('selectBmmc', 'local', {
            arr: bmData
        });
    });
    formSelects.on('selectBmmc', function(id, vals, val){
        getDataByAjax('/rest/v1.0/process/users/'+val.value,'','GET',function (param) {
            console.info(param);
            var ryData = [];
            param.forEach(function (v) {
                ryData.push({"name": v.alias, "value": v.username});
            });
            formSelects.data('selectBjry', 'local', {
                arr: ryData
            });
        });
    });

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

    // 当前页数据
    var currentPageData = new Array();
    var wpage,     //当前页码
        currcount; //每页行数

    // 日期控件
    laydate.render({
        elem: '#kssj',
        format: 'yyyy-MM-dd',
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
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#kssj').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

    //4.定义table的cols，显示默认表格
    renderTable();

    function renderTable(){
        var cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'bdcqzh', minWidth: 110, title: '产权证号'},
            {field: 'yzh', minWidth: 100, title: '印制号'},
            {
                field: 'zslx', minWidth: 200, title: '证书类型',
                templet: function (d) {
                    return convertZdDmToMc("zslx", d.zslx, "zdList");
                }
            },
            {field: 'szr', minWidth: 100, title: '缮证人'},
            {field: 'djjg', minWidth: 160, title: '部门'},
            {field: 'djsj', minWidth: 170, title: '缮证日期'}
        ];

        table.render({
            elem: '#zsdymxtjTable',
            title: '证书证明打印统计',
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size'//每页数据量的参数名，默认：limit

            },
            even: true,
            cols: [cols],
            data: [],
            page: true,
            limits: [10, 50, 100, 200, 500],
            parseData: function (res) {
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            done: function () {
                setHeight();
                if(moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
            }
        });

        // 监听表格操作栏按钮
        table.on('toolbar(zsdymxtjFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                    break;
            }
        });
        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(zsdymxtjFilter)', function(obj){
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;

            $.each(data, function(i, v) {
                var keyT = v.taskKey;
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedDataRy', {
                        key: keyT, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedDataRy', {
                        key: keyT, remove: true
                    });
                }
            });
        });
    }

    function exportExcel(d, cols, type){
        if($.isEmptyObject(d) && type == "checked"){
            // 未选择数据时，询问是否导出全部
            layer.confirm('未勾选数据,将导出全部数据？', {
                title: '提示',
                btn: ['确定', '取消'] //按钮
            }, function (index) {
                addModel();
                var requestConditions = getSearchParam();
                $.ajax({
                    url: '/realestate-inquiry-ui/rest/v1.0/zszm/detail?loadTotal=true&page=1&size=9999',
                    type: "GET",
                    async: true,
                    data: requestConditions,
                    success: function (data) {
                        removeModal();
                        layer.close(index);
                        if(data.content.length>0){
                            doExport(data.content, cols, type);
                        }else{
                            warnMsg("未获取到数据");
                        }
                    },
                    error: function (err) {
                        removeModal();
                        delAjaxErrorMsg(err);
                    }
                });
            });
        }else{
            doExport(d, cols, type);
        }

    }

    function doExport(d, cols, type){
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

        var data = d;
        // 设置Excel基本信息
        $("#fileName").val('统计证书证明');
        $("#sheetName").val('统计表');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#data").val(JSON.stringify(data));
        $("#totalInfo").val('总数,'+d.length);
        $("#expandServiceName").val('excelExpandYcService');
        $("#form").submit();
    }
    // 点击查询
    $('#search').on('click', function () {
        search();
    });

    // 查询处理逻辑
    function search() {
        // 获取查询参数
        var obj = {};
        $(".search").each(function (i) {
            var name = $(this).attr('name');
            var value = $(this).val();
            if("kssj" == name && !isNullOrEmpty(value)){
                value += " 00:00:00";
            }
            if("jzsj" == name && !isNullOrEmpty(value)){
                value += " 23:59:59";
            }
            obj[name] = value;
        });
        var bmid = formSelects.value('selectBmmc', 'val')[0];

        if(isNotBlank(bmid)){
            bmData.forEach(function (v) {
                if(bmid == v.value){
                    obj["djbmdm"] = v.code;
                }
            });
        }
        var szr = getMultiSelectVal('selectBjry','name');
        if(isNotBlank(szr)){
            obj["szr"] = szr;
        }
        var zslx = getMultiSelectVal('selectZslx','name');
        if(isNotBlank(zslx)){
            obj["zslx"] = getMultiSelectVal('selectZslx','value');
        }
        // 重新请求
        table.reload("zsdymxtjTable", {
            url: "/realestate-inquiry-ui/rest/v1.0/zszm/detail"
            , where: obj
            , page: {
                curr: 1, //重新从第 1 页开始
                limits: [10, 50, 100, 200, 500]
            }
            , done: function (res, curr, count) {
                $('#count').text(count);
                currentPageData = res.data;
                wpage = curr;//当前页码
                currcount = res.data.length;//每页行数
                removeModal();
                setHeight();
            }
        });
    }

    //获取查询参数
    function getSearchParam(){
        // 获取查询参数
        var obj = {};
        $(".search").each(function (i) {
            var name = $(this).attr('name');
            var value = $(this).val();
            if("kssj" == name && !isNullOrEmpty(value)){
                value += " 00:00:00";
            }
            if("jzsj" == name && !isNullOrEmpty(value)){
                value += " 23:59:59";
            }
            obj[name] = value;
        });
        var bmid = formSelects.value('selectBmmc', 'val')[0];

        if(isNotBlank(bmid)){
            bmData.forEach(function (v) {
                if(bmid == v.value){
                    obj["djbmdm"] = v.code;
                }
            });
        }
        var szr = getMultiSelectVal('selectBjry','name');
        if(isNotBlank(szr)){
            obj["szr"] = szr;
        }
        var zslx = getMultiSelectVal('selectZslx','name');
        if(isNotBlank(zslx)){
            obj["zslx"] = getMultiSelectVal('selectZslx','value');
        }
        return obj;
    }
});



$(function () {
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
});

function getAjaxData(url){
    var deferred =$.Deferred();
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (data) {
            if(data){
                deferred.resolve(data);
            }else{
                deferred.resolve([]);
            }
        },error:function(e){
            console.info(e);
            deferred.reject(e);
        }
    });
    return deferred;
}

function getUserData(){
    var data = new Array();
    if(UserCache.size !=0 ){
        UserCache.forEach(function(key){
            data.push.apply(data, key);
        });
    }
    var list = uniqueArray(data);
    console.info(list);
    return list;
}

function uniqueArray(data){
    if (data.length == 0) {
        return [];
    }
    var temp = {};
    var newArray = [];
    for (var i = 0; i < data.length; i++) {
        if (!temp[data[i].id]) {
            newArray.push(data[i]);
            temp[data[i].id] = true;
        }
    }
    return newArray;
}

function getMultiSelectVal(selectorName,type){
    var selectArr = formSelects.value(selectorName);
    var selects = "";
    if(selectArr.length!=0){
        var selectList = [];
        $.each(selectArr, function (index, select) {
            if (type == "name") {
                selectList.push(select.name);
            } else {
                selectList.push(select.value);
            }
        });
        selects = selectList.join(",");
    }
    return selects;
}

