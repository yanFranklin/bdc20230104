layui.config({
    base: '../../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
var endflag = getQueryString("endflag");
var sourceType = getQueryString("sourceType");
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'element', 'table', 'formSelects'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        element = layui.element,
        table = layui.table,
        formSelects = layui.formSelects,
        form = layui.form;

    /**
     * 获取url后面参数
     * @type {string}
     */
    var apiId = getQueryString("apiId");
    var editType = getQueryString("editType");
    var parentTableIndex = getQueryString("parentTableIndex");

    // 新增、编辑、查看页面元素控制
    elementShow(editType);
    if (sourceType && sourceType == "request"){
        $("#addParamSqlDiv").hide();
    }else {
        $("#addParamSqlDiv").show();
        var editSql = parent.layui.table.cache["returnTable"][parentTableIndex].sql;
    }
    $("#insertChildParam").click(function() {
        //按钮【确定】的回调
        if (sourceType === "request"){
            var objectParamList = layui.table.cache["returnTable"];
            parent.layui.table.cache["paramTable"][parentTableIndex].childParamInfo = objectParamList;
            parent.layui.table.cache["paramTable"][parentTableIndex].childParamId = guid();
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);//关闭当前页
        }else {
            var objectParamList = layui.table.cache["returnTable"];
            var sqlValue = $("#sql").val();
            if (sqlValue){
                var param = {
                    "sql": sqlValue,
                    "paramList" : objectParamList
                }
                $.ajax({
                    url: getContextPath() + "/rest/v1.0/apiManagement/check/sql",
                    type: 'POST',
                    async: false,
                    dataType: "json",
                    data: JSON.stringify(param),
                    contentType: "application/json",
                    success: function (data) {
                        parent.layui.table.cache["returnTable"][parentTableIndex].childParamInfo = objectParamList;
                        parent.layui.table.cache["returnTable"][parentTableIndex].childParamId = guid();
                        parent.layui.table.cache["returnTable"][parentTableIndex].sql = sqlValue;
                        parent.layui.table.reload('returnTable',{
                            sql: sqlValue
                        });
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);//关闭当前页
                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr);
                    }
                });
            }else {
                layer.msg("请填写sql", {icon: 5, time: 3000});
            }
        }

    });
    $("#closeChildHtml").click(function() {
        //按钮【取消】的回调
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);//关闭当前页
    });

    if (typeof editType == "undefined" || editType == null || editType === "") {
        return;
    }
    if (editType !== "0" && (typeof apiId == "undefined" || apiId == null || apiId === "")) {
        return
    }

    var apiData = getApiInfo(apiId);
    // 接口数据
    var returnTypeParamList = [];
    if (!apiData.returnTypeParamList || apiData.returnTypeParamList.length == 0){
        if (sourceType && sourceType == "request"){
            if (parent.layui.table.cache["paramTable"][parentTableIndex].childParamInfo && parent.layui.table.cache["paramTable"][parentTableIndex].childParamInfo!= 0){
                returnTypeParamList = JSON.parse(JSON.stringify(parent.layui.table.cache["paramTable"][parentTableIndex].childParamInfo));
                for (var i = 0; i < returnTypeParamList.length; i++) {
                    delete returnTypeParamList[i].LAY_TABLE_INDEX;
                }
            }
        }else {
            if (parent.layui.table.cache["returnTable"][parentTableIndex].childParamInfo && parent.layui.table.cache["returnTable"][parentTableIndex].childParamInfo!= 0){
                returnTypeParamList = JSON.parse(JSON.stringify(parent.layui.table.cache["returnTable"][parentTableIndex].childParamInfo));
                for (var i = 0; i < returnTypeParamList.length; i++) {
                    delete returnTypeParamList[i].LAY_TABLE_INDEX;
                }
            }
        }
    }else {
        returnTypeParamList = apiData.returnTypeParamList;
    }
    // if (apiId && parent.layui.table.cache["returnTable"][parentTableIndex].childParamInfo){
    //     returnTypeParamList = parent.layui.table.cache["returnTable"][parentTableIndex].childParamInfo;
    // }
    // var returnTypeParamList = apiData.returnTypeParamList ? apiData.returnTypeParamList : [];
    // tab页默认选中
    var defaultIndex = 0;

    form.render('select');
    // 编辑、查询页面表单基本数据渲染
    dataEcho(apiData);
    // 渲染请求参数、返回参数table
    initTable();

    /**
     * 初始化渲染table数据
     */
    function initTable() {
        renderTable(returnTypeParamList, 3);
    }

    /**
     * 回显sql
     */
    function showSql() {
        $("#sql").html(editSql);
    }
    /**
     * 渲染table
     * @param paramData
     * @param paramType
     */
    function renderTable(paramData, paramType) {
        showSql();
        var cols = getTableHead(paramType, editType);
        if (editType === "0" || editType === "1") {
            if (paramType === 3) {
                table.render({
                    elem: "#returnTable",
                    toolbar: "#toolbar",
                    data: paramData,
                    limit: 50,
                    defaultToolbar: [],
                    cols: cols,
                    done: function (obj, curr, count) {
                        form.on('switch(switchIsListFlagFilter)', function(data){
                            var _index = $(data.elem).parents("tr").attr('data-index')||0;
                            if (!data.elem.checked){
                                obj.data[_index].isListFlag = "off"
                                obj.data[_index].fieldType = "java.util.String";
                            }else {
                                obj.data[_index].isListFlag = "on";
                                obj.data[_index].fieldType = "java.util.List";
                            }
                        });
                        for (var i = 0; i < obj.data.length; i++) {
                            var item = obj.data[i];
                            if (item.isListFlag === "on"){
                                $("tr[data-index='"+i+"'] input[name='isList']").next().addClass('layui-form-onswitch');
                                $("tr[data-index='"+i+"'] input[name='isList']").next().find("em").text("是");
                            }
                            // layui.each($("select[name='return_fieldType']", ""), function (i, item) {
                            //     var elem = $(item);
                            //     elem.next().children().children()[0].defaultValue = fieldType;
                            // });
                        }
                        if (obj.length === 0) {
                            $(".layui-table-main").html('<div class="layui-none">暂无数据</div>');
                        }
                        $(".layui-table-body, .layui-table-box, .layui-table-cell").css('overflow', 'visible');
                    }
                });
            }
        }
        if (editType === "2") {
            if (paramType === 3) {
                table.render({
                    elem: "#returnTable",
                    data: paramData,
                    defaultToolbar: [],
                    limit: 50,
                    cols: cols,
                    done: function (obj, curr, count) {
                        form.on('switch(switchIsListFlagFilter)', function(data){
                            var _index = $(data.elem).parents("tr").attr('data-index')||0;
                            if (!data.elem.checked){
                                obj.data[_index].isListFlag = "off"
                                obj.data[_index].fieldType = "java.util.String";
                            }else {
                                obj.data[_index].isListFlag = "on";
                                obj.data[_index].fieldType = "java.util.List";
                            }
                        });
                        for (var i = 0; i < obj.data.length; i++) {
                            var item = obj.data[i];
                            if (item.isListFlag === "on"){
                                $("tr[data-index='"+i+"'] input[name='isList']").next().addClass('layui-form-onswitch');
                                $("tr[data-index='"+i+"'] input[name='isList']").next().find("em").text("是");
                            }else {
                                $("tr[data-index='"+i+"'] a[name='showParam']").hide();
                            }
                            // layui.each($("select[name='return_fieldType']", ""), function (i, item) {
                            //     var elem = $(item);
                            //     elem.next().children().children()[0].defaultValue = fieldType;
                            // });
                        }
                        if (obj.length === 0) {
                            $(".layui-table-main").html('<div class="layui-none">暂无数据</div>');
                        }
                        $(".layui-table-body, .layui-table-box, .layui-table-cell").css('overflow', 'visible');
                    }
                });
            }
        }

        form.render('select');
    }


    table.on('toolbar(returnTable)', function (obj) {
        switch (obj.event) {
            case 'addParam':
                addParamRow(3);
                break;
        }
    });

    table.on('tool(returnTable)', function (obj) {
        switch (obj.event) {
            case 'addAloneSql':
                // $("#aloneSqlText").prop("readonly",true);
                var defaultValue;
                if(obj.data.sql){
                    defaultValue = obj.data.sql;
                }else{
                    defaultValue = '';
                }
                layer.prompt({
                    formType: 2,
                    value: defaultValue,
                    title: '自定sql',
                    area: ['400px', '200px'] //自定义文本域宽高
                }, function (val, index) {
                    // $(".layui-table-box").find("[data-field='sql']").attr('data-edit',true);
                    console.log(obj.data);
                    obj.data.sql = val;
                    obj.update(obj.data);
                    renderTable(returnTypeParamList, 3);
                    layer.close(index);
                });
                break;
            case 'addObjectParam':
                var index = layer.open({
                    type: 2,
                    title: "新增参数页面",
                    area: ['1000px', '500px'],
                    fixed: false, //不固定
                    maxmin: true, //开启最大化最小化按钮
                    content: getContextPath() + "/view/config/jkgl/ApiParam.html?editType=0&parentTableIndex="+obj.tr[0].dataset.index+"&apiId="+obj.data.childParamId+"&endflag=true"+"&editSql="+obj.data.sql
                });
                layer.full(index);
                break;
            case 'showObjectParam':
                var index = layer.open({
                    type: 2,
                    title: "查看参数页面",
                    area: ['1150px', '600px'],
                    fixed: false, //不固定
                    maxmin: true, //开启最大化最小化按钮
                    content: getContextPath() + "/view/config/jkgl/ApiParam.html?editType=2&parentTableIndex=" + obj.tr[0].dataset.index + "&apiId=" + obj.data.childParamId
                });
                layer.full(index);
                break;
            case 'deleteRow':
                var $this = $(this);
                var tr = $this.parents('tr');
                var index = tr.data('index');
                deleteRow(3, index);
                break;
        }
    });

    // form.on('switch(switchIsAloneSqlFlagFilter)', function(obj){
    //     // layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
    //     if(this.value === 'on'){
    //         $(".layui-table-box").find("[data-field='sql']").attr('data-edit',true);
    //     }else {
    //         $(".layui-table-box").find("[data-field='sql']").removeAttr('data-edit');
    //     }
    //     // tableIns.reload(dataArray);
    // });

    /**
     * 添加一行元素
     * @param paramType
     */
    function addParamRow(paramType) {
        if (paramType === 3) {
            var obj = {
                "fieldName": "",
                "parentFieldName": "",
                "fieldType": "java.lang.String",
                "paramType": paramType,
                "fieldRemark": "",
                "sql": "",
                "isListFlag":"",
                "cszdmc":"",
                "cspj":"",
                "childParamInfo":[],
                "childParamId":""
            };
            returnTypeParamList.push(obj);
            renderTable(returnTypeParamList, paramType);
        }
    }

    /**
     * 删除一行元素
     * @param paramType
     * @param index
     */
    function deleteRow(paramType, index) {
        if (paramType === 3) {
            returnTypeParamList.splice(index, 1);
            renderTable(returnTypeParamList, paramType);
        }
    }

    // 点击提交时不为空的全部标红
    var isSubmit = true;
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value === '' || value == null) {//判断是否为空
                $(item).addClass('layui-form-danger');
                console.log(item);
                isSubmit = false;
            }
        },
        codeUrl: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value && value !== '' && value != null) {
                if (!new RegExp("^[A-Za-z]+$").test(value)) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    console.log(item);
                    verifyMsg = "接口地址代码只能输入英文字符";
                }
            }
        },
        checkSql: function (value, item) {
            if (value === '' || value == null) {
                $(item).addClass("sql");
            } else {
                $(item).removeClass("sql");
            }
        }
    });


    form.on("submit(save)", function (data) {
        var returnTypeParamList = layui.table.cache["returnTable"];

        var apiParam = returnTypeParamList.concat(requestParamList);

        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            addModel();
            var param = {
                "id": apiId,
                "requestMethod": $("#requestMethod").val(),
                "name": $("#name").val(),
                "description": $("#description").val().replace(/\n/g, ""),
                "url": $("#codeUrl").val(),
                "type": $("#type").val(),
                "sql": $("#sql").val().replace(/\n/g, ""),
                "clientId": $("#clientId").val(),
                "consumer": $("#consumer").val(),
                "logFlag": $("#loyFlag").val(),
                "logType": $("#logType").val(),
                "releaseStatus": $("#releaseStatus").val(),
                "paramList": apiParam,
                "packageResponse": $("#packageResponse").val()
            };
            console.log(param);

            var url = editType === "1" ? "/rest/v1.0/apiManagement/update" : "/rest/v1.0/apiManagement/save";

            $.ajax({
                url: getContextPath() + url,
                type: 'POST',
                async: false,
                dataType: "json",
                data: JSON.stringify(param),
                contentType: "application/json",
                success: function (data) {
                    apiId = data ? data : apiId;
                    var apiInfo = getApiInfo(apiId);
                    $("#url").val(apiInfo.url);
                    saveSuccessMsg();
                    removeModal();
                    document.getElementById('test').style.display = 'inherit';
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                    var responseText = JSON.parse(xhr.responseText);
                    var detail = responseText.msg;
                    if (detail.indexOf("表或视图") !== -1 || detail.indexOf("sql") !== -1) {
                        $("#sql").addClass("sql");
                    }
                    removeModal();
                }
            });
            // 禁止提交后刷新
            return false;
        }
    });
});

function guid() {
    return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}


/**
 * 页面基本元素数据回显
 * @param data
 */
function dataEcho(data) {
}


/**
 * 获取url后的参数
 * @param name
 * @returns {string|null}
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (null !== r) {
        if (decodeURI(r[2]) !== "null") {
            return decodeURI(r[2]);
        } else {
            return null
        }
    }
    return null;
}

/**
 * 获取api接口详细信息
 * @param apiId
 * @returns {*}
 */
function getApiInfo(apiId) {
    var apiData = {};
    if (typeof apiId == "undefined" || apiId == null || apiId === "") {
        return apiData;
    }
    $.ajax({
        url: getContextPath() + "/rest/v1.0/apiManagement/searchById?apiId=" + apiId,
        type: 'GET',
        async: false,
        success: function (data) {
            if (data) {
                apiData = data;
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
    return apiData;
}


/**
 * 获取表头
 * @param paramType
 * @param editType
 * @returns {({field: string, edit: string, title: string, align: string}|{field: string, edit: string, title: string, align: string}|{field: string, templet: (function(*): string), title: string, align: string}|{field: string, templet: (function(*): string), title: string, align: string}|{field: string, edit: string, title: string, align: string})[][]}
 */
function getTableHead(paramType, editType) {
    var cols;
    if (paramType === 3) {
        if (editType === "0" || editType === "1") {
            var toolsStr;
            if (endflag && sourceType === "request"){
                toolsStr = '<a class="layui-btn layui-btn-xs bdc-delete-btn" lay-event="deleteRow">删除</a>';
            }else if (endflag && sourceType != "request"){
                toolsStr = '<a class="layui-btn layui-btn-xs bdc-delete-btn" lay-event="deleteRow">删除</a><a class="layui-btn layui-btn-xs bdc-major-btn" lay-event="addAloneSql">添加sql</a>';
            }else if (!endflag){
                toolsStr = '<a class="layui-btn layui-btn-xs bdc-delete-btn" lay-event="deleteRow">删除</a><a class="layui-btn layui-btn-xs bdc-major-btn" lay-event="addAloneSql">添加sql</a>'
                    + '<a class="layui-btn layui-btn-xs bdc-major-btnn" lay-event="addObjectParam">添加参数</a>'
            }
            if (endflag && sourceType === "request"){
                cols = [[
                    {field: 'fieldName', title: '字段名', align: 'center', edit: 'text'},
                    {field: 'fieldRemark', title: '字段说明', align: 'center', edit: 'text'},
                    {field: 'fieldType', title: '字段类型', align: 'center', hide:true},
                    // {field: 'childParamInfo', title: '子参数信息', align: 'center',hide:true},
                    // {
                    //     field: 'isListFlag',
                    //     title: '是否list',
                    //     align: 'center',
                    //     templet: '#switchIsListFlag',
                    //     unresize: true
                    // },
                    // {field: 'sql', title: 'sql', align: 'center',edit: 'text'},
                    {
                        fixed: 'right', title: '操作', align: "center",
                        templet: function (d) {
                            return toolsStr;
                        }
                    }
                ]]
            }else {
                cols = [[
                    {field: 'fieldName', title: '字段名', align: 'center', edit: 'text'},
                    {field: 'fieldRemark', title: '字段说明', align: 'center', edit: 'text'},
                    {field: 'childParamInfo', title: '子参数信息', align: 'center',hide:true},
                    {
                        field: 'isListFlag',
                        title: '是否list',
                        align: 'center',
                        templet: '#switchIsListFlag',
                        unresize: true
                    },
                    {field: 'cszdmc', title: '参数转换字典项配置', align: 'center', edit: 'text'},
                    {field: 'cspj', title: '参数拼接配置', align: 'center', edit: 'text'},
                    {field: 'sql', title: 'sql', align: 'center',edit: 'text'},
                    {
                        fixed: 'right', title: '操作', align: "center",
                        templet: function (d) {
                            return toolsStr;
                        }
                    }
                ]]
            }

        }
        if (editType === "2") {
            var toolsStr = '<a class="layui-btn layui-btn-xs bdc-major-btnn" name="showParam" lay-event="showObjectParam">查看参数</a>';
            if (endflag && sourceType === "request"){
                cols = [[
                    {field: 'fieldName', title: '字段名', align: 'center', edit: 'text'},
                    {field: 'fieldRemark', title: '字段说明', align: 'center', edit: 'text'},
                    {field: 'fieldType', title: '字段类型', align: 'center', hide:true},
                    // {field: 'childParamInfo', title: '子参数信息', align: 'center',hide:true},
                    // {
                    //     field: 'isListFlag',
                    //     title: '是否list',
                    //     align: 'center',
                    //     templet: '#switchIsListFlag',
                    //     unresize: true
                    // },
                    // {field: 'sql', title: 'sql', align: 'center',edit: 'text'},
                    {
                        fixed: 'right', title: '操作', align: "center",
                        templet: function (d) {
                            return toolsStr;
                        }
                    }
                ]]
            }else {
                cols = [[
                    {field: 'fieldName', title: '字段名', align: 'center'},
                    {field: 'parentFieldName', title: '父字段名', align: 'center'},
                    {field: 'fieldRemark', title: '字段说明', align: 'center'},
                    {
                        field: 'isListFlag',
                        title: '是否list',
                        align: 'center',
                        templet: '#switchIsListFlag',
                        unresize: true
                    },
                    {field: 'cszdmc', title: '参数转换字典项配置', align: 'center', edit: 'text'},
                    {field: 'cspj', title: '参数拼接配置', align: 'center', edit: 'text'},
                    {
                        fixed: 'right', title: '操作', align: "center",
                        templet: function (d) {
                            return toolsStr;
                        }
                    }
                ]]
            }
        }
    }
    return cols;
}

/**
 * 页面元素渲染
 * @param editType
 */
function elementShow(editType) {
    var insertButton = document.getElementById('insertChildParam');
    if (editType === "2") {
        // 查看页面 表单只读,测试和报错按钮隐藏
        insertButton.style.display = 'none';
        $('select[name=consumer]').attr("disabled", "disabled");
        $('select[name=clientId]').attr("disabled", "disabled");
        $('select[name=logFlag]').attr("disabled", "disabled");
        $('select[name=logType]').attr("disabled", "disabled");
        $('select[name=type]').attr("disabled", "disabled");
        $('input[name=name]').attr("readonly", "readonly");
        $('select[name=requestMethod]').attr("disabled", "disabled");
        $('select[name=releaseStatus]').attr("disabled", "disabled");
        $('input[name=url]').attr("readonly", "readonly");
        $('textarea[name=sql]').attr("readonly", "readonly");
        $('textarea[name=description]').attr("readonly", "readonly");
    }
}

