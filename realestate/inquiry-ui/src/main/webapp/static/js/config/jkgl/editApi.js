layui.config({
    base: '../../../static/js/config/thirdControl/layui/formSelects/'
}).extend({
    formSelects: 'formSelects-v4.min'
});
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
    if (typeof editType == "undefined" || editType == null || editType === "") {
        return;
    }
    if (editType !== "0" && (typeof apiId == "undefined" || apiId == null || apiId === "")) {
        return
    }

    table.render({
        elem: '#responseHead'
        , cols: [[
            {field: 'headParamCode', title: '响应头编码', align: 'center', hide: true},
            {field: 'headParamName', title: '响应头名称', align: 'center', edit: 'text'},
            {field: 'headParamMessage', title: '响应头返回信息', align: 'center', edit: 'text'},
            {field: 'headParamDesc', title: '响应头返回描述', align: 'center'}
        ]], data: [{
            "headParamCode": "success",
            "headParamName": "message",
            "headParamMessage": "响应成功",
            "headParamDesc": "响应成功情况"
        }, {
            "headParamCode": "withoutRequiredParam",
            "headParamName": "message",
            "headParamMessage": "必填未填写",
            "headParamDesc": "必填未填写情况"
        }, {
            "headParamName": "message",
            "headParamCode": "failParamType",
            "headParamMessage": "请求参数格式错误",
            "headParamDesc": "请求参数格式错误情况"
        },
        ]
    });

    table.render({
        elem: '#responseDetail'
        , cols: [[
            {field: 'respCode', title: '响应编码key', align: 'center', edit: 'text'},
            {field: 'respCodeValue', title: '响应编码value', align: 'center', edit: 'text'},
            {field: 'respMsg', title: '响应描述key', align: 'center', edit: 'text'},
            {field: 'respMsgValue', title: '响应描述value', align: 'center',edit: 'text'},
            {field: 'respDesc', title: '响应返回描述', align: 'center'}
        ]], data: [{
            "respCode": "code",
            "respCodeValue": "0000",
            "respMsg": "msg",
            "respMsgValue": "响应成功",
            "respDesc": "响应成功情况",
            "respDetailCode": "success"
        }, {
            "respCode": "code",
            "respCodeValue": "0001",
            "respMsg": "msg",
            "respMsgValue": "必填未填写",
            "respDesc": "必填未填写情况",
            "respDetailCode": "withoutRequiredParam"
        }, {
            "respCode": "code",
            "respCodeValue": "0002",
            "respMsg": "msg",
            "respMsgValue": "请求参数格式错误",
            "respDesc": "请求参数格式错误情况",
            "respDetailCode": "failParamType"
        }]
    });

    if (editType === "2") {
        $(function () {
            //监听 配置sql 弹窗显示 单击
            $('.bdc-form-div').on('click', '.bdc-pj-popup', function () {
                var $thisTextarea = $(this).siblings('textarea');
                var fjContent = $thisTextarea.val();
                $("#desc").prop("readonly", true);
                layer.open({
                    title: '配置sql',
                    type: 1,
                    area: ['960px'],
                    content: $('#fjPopup')
                    , yes: function (index, layero) {
                        //提交 的回调
                        $thisTextarea.val($('#fjPopup textarea').val());
                        $('#fjPopup textarea').val('');
                        layer.close(index);
                    }
                    , btn2: function (index, layero) {
                        //取消 的回调
                        $('#fjPopup textarea').val('');
                    }
                    , cancel: function () {
                        //右上角关闭回调
                        $('#fjPopup textarea').val('');
                    }
                    , success: function () {
                        $('#fjPopup textarea').val(fjContent);
                    }
                });
            });
        })
    } else {
        $(function () {
            //监听 配置sql 弹窗显示 单击
            $('.bdc-form-div').on('click', '.bdc-pj-popup', function () {
                var $thisTextarea = $(this).siblings('textarea');
                var fjContent = $thisTextarea.val();
                layer.open({
                    title: '配置sql',
                    type: 1,
                    area: ['960px'],
                    btn: ['保存'],
                    content: $('#fjPopup')
                    , yes: function (index, layero) {
                        //提交 的回调
                        $thisTextarea.val($('#fjPopup textarea').val());
                        $('#fjPopup textarea').val('');
                        layer.close(index);
                    }
                    , btn2: function (index, layero) {
                        //取消 的回调
                        $('#fjPopup textarea').val('');
                    }
                    , cancel: function () {
                        //右上角关闭回调
                        $('#fjPopup textarea').val('');
                    }
                    , success: function () {
                        $('#fjPopup textarea').val(fjContent);
                    }
                });
            });
        })
    }

    // 接口数据
    var apiData = getApiInfo(apiId);
    var requestBodyParamList = apiData.requestBodyParamList ? apiData.requestBodyParamList : [];
    var queryParamList = apiData.queryParamList ? apiData.queryParamList : [];
    var restParamList = apiData.restParamList ? apiData.restParamList : [];
    var returnTypeParamList = apiData.returnTypeParamList ? apiData.returnTypeParamList : [];
    var responseHead = apiData.responseHead ? eval('(' + apiData.responseHead + ')') : [];
    var responseDetail = apiData.responseDetail ? eval('(' + apiData.responseDetail + ')') : [];
    var responseBodyDefaultParamList = apiData.responseBodyDefaultValueList ? apiData.responseBodyDefaultValueList : [];

    // tab页默认选中
    var defaultIndex = 0;

    // 新增、编辑、查看页面元素控制
    elementShow(editType);
    // 新增、编辑、查看页面下拉框渲染
    renderSelect();
    form.render('select');
    // 编辑、查询页面表单基本数据渲染
    dataEcho(apiData);
    // 渲染请求参数、返回参数table
    initTable();

    if (apiData.responseHead) {
        table.render({
            elem: '#responseHead'
            , cols: [[
                {field: 'headParamCode', title: '响应头编码', align: 'center', hide: true},
                {field: 'headParamName', title: '响应头名称', align: 'center', edit: 'text'},
                {field: 'headParamMessage', title: '响应头返回信息', align: 'center', edit: 'text'},
                {field: 'headParamDesc', title: '响应头返回描述', align: 'center'}
            ]], data: responseHead
        });
    } else {
        var responseHeadSelect = document.getElementById("packageResponseHead");
        for (var i = 0; i < responseHeadSelect.options.length; i++) {
            if (responseHeadSelect.options[i].value == "0") {
                responseHeadSelect.options[i].selected = true;
            }
        }
        form.render('select');
        $("#responseSettingDiv").hide();
    }

    if (apiData.responseDetail) {
        table.render({
            elem: '#responseDetail'
            , cols: [[
                {field: 'respCode', title: '响应编码key', align: 'center', edit: 'text'},
                {field: 'respCodeValue', title: '响应编码value', align: 'center', edit: 'text'},
                {field: 'respMsg', title: '响应描述key', align: 'center', edit: 'text'},
                {field: 'respMsgValue', title: '响应描述value', align: 'center',edit: 'text'},
                {field: 'respDesc', title: '响应返回描述', align: 'center'},
                {field: 'respDetailCode', title: '响应情况编码', align: 'center', hide: true}
            ]], data: responseDetail
        });
    } else {
        var responseDetailSelect = document.getElementById("packageResponseDetail");
        for (var i = 0; i < responseDetailSelect.options.length; i++) {
            if (responseDetailSelect.options[i].value == "0") {
                responseDetailSelect.options[i].selected = true;
            }
        }
        form.render('select');
        $("#responseDetailSettingDiv").hide();
    }

    /**
     * 初始化渲染table数据
     */
    function initTable() {
        if (requestBodyParamList && requestBodyParamList.length > 0) {
            defaultIndex = 0;
            renderTable(requestBodyParamList, defaultIndex);
        } else if (queryParamList && queryParamList.length > 0) {
            defaultIndex = 1;
            renderTable(queryParamList, defaultIndex);
        } else if (restParamList && restParamList.length > 0) {
            defaultIndex = 2;
            renderTable(restParamList, defaultIndex);
        } else {
            defaultIndex = 0;
            renderTable(requestBodyParamList, defaultIndex);
        }

        $('.layui-tab-title li').eq(defaultIndex).addClass('layui-this').siblings().removeClass('layui-this');
        $('.layui-tab-item ').eq(defaultIndex).addClass('layui-show').siblings().removeClass('layui-show');

        renderTable(returnTypeParamList, 3);
        renderTable(responseBodyDefaultParamList, 4);
    }

    /**
     * 监听 tab切换
     */
    element.on('tab(param_tab)', function (data) {
        defaultIndex = data.index
        if (defaultIndex === 0) {
            // 请求体
            renderTable(requestBodyParamList, 0);
        }
        if (defaultIndex === 1) {
            // query参数
            renderTable(queryParamList, 1);
        }
        if (defaultIndex === 2) {
            // rest参数
            renderTable(restParamList, 2);
        }
        // 重装表格大小
        table.resize();
    });

    /**
     * 监听是否包装请求头事件
     */
    form.on('select(packageResponseHead)', function (data) {
        // console.log(data);
        if (data.value === "1") {
            $("#responseSettingDiv").show();
        } else {
            $("#responseSettingDiv").hide();
        }

    });
    /**
     * 监听是否包装响应详情事件
     */
    form.on('select(packageResponseDetail)', function (data) {
        // console.log(data);
        if (data.value === "1") {
            $("#responseDetailSettingDiv").show();
        } else {
            $("#responseDetailSettingDiv").hide();
        }

    });

    /**
     * 渲染table
     * @param paramData
     * @param paramType
     */
    function renderTable(paramData, paramType) {
        var cols = getTableHead(paramType, editType);
        if (editType === "0" || editType === "1") {
            if (paramType === 0 || paramType === 1 || paramType === 2) {
                table.render({
                    elem: "#paramTable",
                    data: paramData,
                    defaultToolbar: [],
                    cols: cols,
                    limit: 50,
                    toolbar: "#toolbar",
                    done: function (data, curr, count) {
                        for (var i = 0; i < data.data.length; i++) {
                            var item = data.data[i];
                            $("#required").each(function () {
                                $(this).children("option").each(function () {
                                    // 判断需要对那个选项进行回显
                                    if (this.value == item.required) {
                                        console.log($(this).text());
                                        // 进行回显
                                        $(this).attr("selected", "selected");
                                    }
                                });
                            });
                            $("tr[data-index='" + i + "'] select[id='requestParam_fieldType']").find('option[value="java.lang.String"]').removeAttr("selected");
                            $("tr[data-index='" + i + "'] select[id='requestParam_fieldType']").find('option[value="' + item.fieldType + '"]').attr("selected", "selected");
                            // $("#requestParam_fieldType").each(function () {
                            //     $(this).find('option[value="java.lang.String"]').removeAttr("selected");
                            //     $(this).find('option[value="' + item.fieldType + '"]').attr("selected", "selected");
                            // });
                        }
                        if (data.length === 0) {
                            $(".layui-table-main").html('<div class="layui-none">暂无数据</div>');
                        }
                        $(".layui-table-body, .layui-table-box, .layui-table-cell").css('overflow', 'visible');
                        layui.form.render("select");
                    }
                });
            }
            if (paramType === 3) {
                table.render({
                    elem: "#returnTable",
                    toolbar: "#toolbar",
                    data: paramData,
                    limit: 50,
                    defaultToolbar: [],
                    cols: cols,
                    done: function (obj, curr, count) {
                        form.on('switch(switchIsListFlagFilter)', function (data) {
                            var _index = $(data.elem).parents("tr").attr('data-index') || 0;
                            if (!data.elem.checked) {
                                obj.data[_index].isListFlag = "off"
                                obj.data[_index].fieldType = "java.util.String";
                            } else {
                                obj.data[_index].isListFlag = "on";
                                obj.data[_index].fieldType = "java.util.List";
                            }

                        });
                        for (var i = 0; i < obj.data.length; i++) {
                            var item = obj.data[i];
                            if (item.isListFlag === "on") {
                                $("tr[data-index='" + i + "'] input[name='isList']").next().addClass('layui-form-onswitch');
                                $("tr[data-index='" + i + "'] input[name='isList']").next().find("em").text("是");
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
            if (paramType === 4) {
                table.render({
                    elem: "#returnDeafultParamTable",
                    toolbar: "#toolbar",
                    data: paramData,
                    limit: 50,
                    defaultToolbar: [],
                    cols: cols,
                    done: function (obj, curr, count) {
                        if (obj.length === 0) {
                            $(".layui-table-main").html('<div class="layui-none">暂无数据</div>');
                        }
                        $(".layui-table-body, .layui-table-box, .layui-table-cell").css('overflow', 'visible');
                    }
                });
            }
        }
        if (editType === "2") {
            if (paramType === 0 || paramType === 1 || paramType === 2) {
                table.render({
                    elem: "#paramTable",
                    data: paramData,
                    defaultToolbar: [],
                    limit: 50,
                    cols: cols,
                    done: function (data, curr, count) {
                        for (var i = 0; i < data.data.length; i++) {
                            var item = data.data[i];
                            $("#required").attr("disabled", "disabled");
                            $("#required").each(function () {
                                $(this).children("option").each(function () {
                                    // 判断需要对那个选项进行回显
                                    if (this.value == item.required) {
                                        console.log($(this).text());
                                        // 进行回显
                                        $(this).attr("selected", "selected");
                                    }
                                });
                            })
                            $("#requestParam_fieldType").attr("disabled", "disabled");
                            $("#requestParam_fieldType").each(function () {
                                $(this).children("option").each(function () {
                                    // 判断需要对那个选项进行回显
                                    if (this.value == item.fieldType) {
                                        console.log($(this).text());
                                        // 进行回显
                                        $(this).attr("selected", "selected");
                                    }
                                });
                            })
                        }
                        if (data.length === 0) {
                            $(".layui-table-main").html('<div class="layui-none">暂无数据</div>');
                        }
                        $(".layui-table-body, .layui-table-box, .layui-table-cell").css('overflow', 'visible');
                    }
                });
            }

            if (paramType === 3) {
                table.render({
                    elem: "#returnTable",
                    data: paramData,
                    limit: 50,
                    defaultToolbar: [],
                    cols: cols,
                    done: function (data, curr, count) {
                        for (var i = 0; i < data.data.length; i++) {
                            var apiData = data.data[i];
                            $("#return_fieldType").attr("disabled", "disabled");
                            $("#return_fieldType").each(function () {
                                $(this).children("option").each(function () {
                                    // 判断需要对那个选项进行回显
                                    if (this.value == item.fieldType) {
                                        console.log($(this).text());
                                        // 进行回显
                                        $(this).attr("selected", "selected");
                                    }
                                });
                            })
                            form.on('switch(switchIsListFlagFilter)', function (data) {
                                var _index = $(data.elem).parents("tr").attr('data-index') || 0;
                                if (!data.elem.checked) {
                                    data.data[_index].isListFlag = "off"
                                    data.data[_index].fieldType = "java.util.String";
                                } else {
                                    data.data[_index].isListFlag = "on";
                                    data.data[_index].fieldType = "java.util.List";
                                }
                            });
                            for (var i = 0; i < data.data.length; i++) {
                                var item = data.data[i];
                                if (item.isListFlag === "on") {
                                    $("tr[data-index='" + i + "'] input[name='isList']").next().addClass('layui-form-onswitch');
                                    $("tr[data-index='" + i + "'] input[name='isList']").next().find("em").text("是");
                                } else {
                                    $("tr[data-index='" + i + "'] a[name='showParam']").hide();
                                }
                                // layui.each($("select[name='return_fieldType']", ""), function (i, item) {
                                //     var elem = $(item);
                                //     elem.next().children().children()[0].defaultValue = fieldType;
                                // });
                            }

                        }
                        if (data.length === 0) {
                            $(".layui-table-main").html('<div class="layui-none">暂无数据</div>');
                        }
                        $(".layui-table-body, .layui-table-box, .layui-table-cell").css('overflow', 'visible');
                    }
                });
            }
            if (paramType === 4) {
                table.render({
                    elem: "#returnDeafultParamTable",
                    toolbar: "#toolbar",
                    data: paramData,
                    limit: 50,
                    defaultToolbar: [],
                    cols: cols,
                    done: function (obj, curr, count) {
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

    table.on('toolbar(paramTable)', function (obj) {
        switch (obj.event) {
            case 'addParam':
                var flag = true;
                requestBodyParamList.forEach(function (item){
                    if (!item.fieldName){
                        layer.msg("空字段名请填写完整", {icon: 5, time: 3000});
                        flag = false;
                        return ;
                    }
                });
                if (flag){
                    addParamRow(defaultIndex);
                }
                break;
        }
    });

    table.on('toolbar(returnTable)', function (obj) {
        switch (obj.event) {
            case 'addParam':
                addParamRow(3);
                break;
        }
    });

    table.on('toolbar(returnDeafultParamTable)', function (obj) {
        switch (obj.event) {
            case 'addParam':
                addParamRow(4);
                break;
        }
    });

    table.on('tool(paramTable)', function (obj) {
        switch (obj.event) {
            case 'deleteRow':
                var $this = $(this);
                var tr = $this.parents('tr');
                var index = tr.data('index');
                deleteRow(defaultIndex, index);
                break;
            case 'showObjectParam':
                var index = layer.open({
                    type: 2,
                    title: "查看参数页面",
                    area: ['1150px', '600px'],
                    fixed: false, //不固定
                    maxmin: true, //开启最大化最小化按钮
                    content: getContextPath() + "/view/config/jkgl/ApiParam.html?editType=2&parentTableIndex=" + obj.tr[0].dataset.index + "&apiId=" + obj.data.childParamId + "&endflag=true&sourceType=request"
                });
                layer.full(index);
                break;
            case 'addObjectParam':
                if (obj.data && (obj.data.fieldType == null || obj.data.fieldType != "java.util.List")) {
                    layer.msg("请选择字段类型为List", {icon: 5, time: 3000});
                } else {
                    var index = layer.open({
                        type: 2,
                        title: "新增参数页面",
                        area: ['1150px', '600px'],
                        fixed: false, //不固定
                        maxmin: true, //开启最大化最小化按钮
                        content: getContextPath() + "/view/config/jkgl/ApiParam.html?editType=0&parentTableIndex=" + obj.tr[0].dataset.index + "&apiId=" + obj.data.childParamId + "&endflag=true&sourceType=request"
                    });
                    layer.full(index);
                }
                break;
        }
    });

    table.on('tool(returnTable)', function (obj) {
        switch (obj.event) {
            case 'addAloneSql':
                // $("#aloneSqlText").prop("readonly",true);
                var defaultValue;
                if (obj.data.sql) {
                    defaultValue = obj.data.sql;
                } else {
                    defaultValue = '';
                }
                layer.prompt({
                    formType: 2,
                    value: defaultValue,
                    title: '自定义sql',
                    area: ['400px', '200px'] //自定义文本域宽高
                }, function (val, index) {
                    // $(".layui-table-box").find("[data-field='sql']").attr('data-edit',true);
                    // console.log(obj.data);
                    if (val) {
                        var param = {
                            "sql": val,
                            "paramList": obj.data.childParamInfo
                        }
                        $.ajax({
                            url: getContextPath() + "/rest/v1.0/apiManagement/check/sql",
                            type: 'POST',
                            async: false,
                            dataType: "json",
                            data: JSON.stringify(param),
                            contentType: "application/json",
                            success: function (data) {
                                obj.data.sql = val;
                                obj.update(obj.data);
                                renderTable(returnTypeParamList, 3);
                                layer.close(index);
                            },
                            error: function (xhr, status, error) {
                                delAjaxErrorMsg(xhr);
                            }
                        });
                    } else {
                        layer.msg("请填写sql", {icon: 5, time: 3000});
                    }
                });
                break;
            case 'addObjectParam':
                // console.log("data--"+obj.data.sql);
                var index = layer.open({
                    type: 2,
                    title: "新增参数页面",
                    area: ['1150px', '600px'],
                    fixed: false, //不固定
                    maxmin: true, //开启最大化最小化按钮
                    content: getContextPath() + "/view/config/jkgl/ApiParam.html?editType=0&parentTableIndex=" + obj.tr[0].dataset.index + "&apiId=" + obj.data.childParamId
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

    table.on('tool(returnDeafultParamTable)', function (obj) {
        switch (obj.event) {
            case 'deleteRow':
                var $this = $(this);
                var tr = $this.parents('tr');
                var index = tr.data('index');
                deleteRow(4, index);
                break;
        }
    });

    //监听下拉框编辑
    form.on('select(requestParam_fieldType)', function (data) {
        var selectValue = data.value;
        var tdArray = $(data.othis.parents('tr')).find('td');
        var td = tdArray[0];
        var fieldName = td.textContent;
        var requestParamList = layui.table.cache["paramTable"];
        for (i = 0; i < requestParamList.length; i++) {
            if (requestParamList[i].fieldName === fieldName) {
                requestParamList[i].fieldType = selectValue; //更新被修改的行数据
            }
        }
    });
    form.on('select(return_fieldType)', function (data) {
        var selectValue = data.value;
        var tdArray = $(data.othis.parents('tr')).find('td');
        var td = tdArray[0];
        var fieldName = td.textContent;
        var returnTypeParamList = layui.table.cache["returnTable"];
        for (i = 0; i < returnTypeParamList.length; i++) {
            if (returnTypeParamList[i].fieldName === fieldName) {
                returnTypeParamList[i].fieldType = selectValue; //更新被修改的行数据
            }
        }
    });
    form.on('select(required)', function (data) {
        var selectValue = data.value;
        var tdArray = $(data.othis.parents('tr')).find('td');
        var td = tdArray[0];
        var fieldName = td.textContent;
        var requestParamList = layui.table.cache["paramTable"];
        for (i = 0; i < requestParamList.length; i++) {
            if (requestParamList[i].fieldName === fieldName) {
                requestParamList[i].required = selectValue; //更新被修改的行数据
            }
        }
    });
    // form.on('switch(switchIsListFlagFilter)', function(obj){
    //     // layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
    //
    //     // tableIns.reload(dataArray);
    // });

    /**
     * 添加一行元素
     * @param paramType
     */
    function addParamRow(paramType) {
        if (paramType === 0) {
            var obj = {
                "fieldName": "",
                "paramType": paramType,
                "fieldType": "String",
                "required": 0,
                "defaultValue": "",
                "fieldRemark": "",
                "childParamInfo": [],
                "childParamId": ""
            };
            requestBodyParamList.push(obj);
            renderTable(requestBodyParamList, paramType);
        }
        if (paramType === 1) {
            var obj = {
                "fieldName": "",
                "paramType": paramType,
                "fieldType": "String",
                "required": 0,
                "defaultValue": "",
                "fieldRemark": "",
                "childParamInfo": [],
                "childParamId": ""
            };
            queryParamList.push(obj);
            renderTable(queryParamList, paramType);
        }
        if (paramType === 2) {
            var obj = {
                "fieldName": "",
                "paramType": paramType,
                "fieldType": "String",
                "required": 0,
                "defaultValue": "",
                "fieldRemark": "",
                "childParamInfo": [],
                "childParamId": ""
            };
            restParamList.push(obj);
            renderTable(restParamList, paramType);
        }
        if (paramType === 3) {
            var obj = {
                "fieldName": "",
                "fieldType": "String",
                "paramType": paramType,
                "fieldRemark": "",
                "sql": "",
                "isListFlag": "",
                "cszdmc": "",
                "cspj": "",
                "childParamInfo": [],
                "childParamId": ""
            };
            returnTypeParamList.push(obj);
            renderTable(returnTypeParamList, paramType);
        }
        if (paramType === 4) {
            var obj = {
                "fieldName": "",
                "fieldRemark": "",
                "defaultValue": "",
            };
            responseBodyDefaultParamList.push(obj);
            renderTable(responseBodyDefaultParamList, paramType);
        }
    }

    /**
     * 删除一行元素
     * @param paramType
     * @param index
     */
    function deleteRow(paramType, index) {
        if (paramType === 0) {
            requestBodyParamList.splice(index, 1);
            renderTable(requestBodyParamList, paramType);
        }
        if (paramType === 1) {
            queryParamList.splice(index, 1);
            renderTable(queryParamList, paramType);
        }
        if (paramType === 2) {
            restParamList.splice(index, 1);
            renderTable(restParamList, paramType);
        }
        if (paramType === 3) {
            returnTypeParamList.splice(index, 1);
            renderTable(returnTypeParamList, paramType);
        }
        if (paramType === 4) {
            responseBodyDefaultParamList.splice(index, 1);
            renderTable(responseBodyDefaultParamList, paramType);
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
        // 请求体
        var requestParamList = [];
        if (requestBodyParamList && requestBodyParamList.length > 0) {
            requestParamList = requestParamList.concat(requestBodyParamList);
        }
        if (queryParamList && queryParamList.length > 0 && restParamList && restParamList.length > 0) {
            layer.msg("query参数和rest参数不支持同时使用", {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        }
        if (queryParamList && queryParamList.length > 0) {
            requestParamList = requestParamList.concat(queryParamList);
        }
        if (restParamList && restParamList.length > 0) {
            requestParamList = requestParamList.concat(restParamList);
        }
        var returnTypeParamList = layui.table.cache["returnTable"];
        if ($("#packageResponseHead").val() === "1") {
            var responseHeadParamList = layui.table.cache["responseHead"];
        } else {
            var responseHeadParamList = [];
        }
        if ($("#packageResponseDetail").val() === "1") {
            var responseDetailParamList = layui.table.cache["responseDetail"];
        } else {
            var responseDetailParamList = [];
        }
        var responseBodyDefaultParamList = layui.table.cache["returnDeafultParamTable"];

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
                // "consumer": $("#consumer").val(),
                "consumer": formSelects.value('consumer', 'valStr'),
                "logFlag": $("#loyFlag").val(),
                "logType": $("#logType").val(),
                "releaseStatus": $("#releaseStatus").val(),
                "paramList": apiParam,
                "packageResponse": $("#packageResponse").val(),
                "packageResponseHead": $("#packageResponseHead").val(),
                "responseHead": responseHeadParamList,
                "packageResponseDetail": $("#packageResponseDetail").val(),
                "responseDetail": responseDetailParamList,
                "dataIsList":$("#dataIsList").val(),
                "responseBodyDefaultParamList": responseBodyDefaultParamList
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

    form.on("submit(test)", function (data) {
        window.open("/realestate-inquiry-ui/view/config/jkgl/apiTest.html?apiId=" + apiId)
    });

    /**
     * 页面下拉框统一渲染
     */
    function renderSelect() {
        // 消费方(共享部门)下拉框
        var consumer = getConsumer("gxbmbz");
        // var consumerOption = "<option value=''>" + '请选择' + "</option>";
        // if (consumer.length > 0) {
        //     consumer.forEach((v, i) => {
        //         consumerOption += "<option value='" + v.value + "'>" + v.name + "</option>";
        //     });
        // }
        // $("#consumer").append(consumerOption);
        formSelects.data('consumer', 'local', {
            arr: consumer
        });

        // 应用
        var clientIdOption = "<option value='exchange-app'>exchange-app</option>";
        $("#clientId").append(clientIdOption);

        // 是否记录日志
        var logFlagOption = "<option value='0'>是</option>";
        $("#logFlag").append(logFlagOption);

        // 日志记录方式
        var logTypeOption = "<option value='1'>ES</option><option value='0'>数据库</option>";
        $("#logType").append(logTypeOption);

        // 接口类型
        var typeOption = "<option value='0'>自定义接口</option>";
        $("#type").append(typeOption);

        // 请求方式
        var requestMethodOption = "<option value='POST'>POST</option><option value='GET'>GET</option>";
        $("#requestMethod").append(requestMethodOption);

        // 发布状态
        var releaseStatusOption = "<option value='0'>未发布</option><option value='1'>已发布</option>";
        $("#releaseStatus").append(releaseStatusOption);

        // 发布状态
        var packageResponseOption = "<option value='0'>不返回</option><option value='1'>返回</option>";
        $("#packageResponse").append(packageResponseOption);

        //是否包装请求头
        var packageResponseHeadOption = "<option value='1'>返回</option><option value='0'>不返回</option>";
        $("#packageResponseHead").append(packageResponseHeadOption);

        //是否返回响应情况
        var packageResponseDetailOption = "<option value='1'>返回</option><option value='0'>不返回</option>";
        $("#packageResponseDetail").append(packageResponseDetailOption);

        //响应data是否list
        var dataIsListOption = "<option value='1'>是</option><option value='0'>否</option>";
        $("#dataIsList").append(dataIsListOption);

    }

    /**
     * 页面基本元素数据回显
     * @param data
     */
    function dataEcho(data) {
        // $("#consumer").each(function () {
        //     $(this).children("option").each(function () {
        //         if (this.value == data.consumer) {
        //             // 进行回显
        //             $(this).attr("selected", "selected");
        //         }
        //     });
        // })
        if (data.consumer) {
            formSelects.value('consumer', data.consumer.split(','));
        }
        $("#logType").each(function () {
            $(this).children("option").each(function () {
                if (this.value == data.logType) {
                    $(this).attr("selected", "selected");
                }
            });
        })
        $("#name").val(data.name);
        $("#requestMethod").each(function () {
            $(this).children("option").each(function () {
                if (this.value == data.requestMethod) {
                    $(this).attr("selected", "selected");
                }
            });
        })
        $("#releaseStatus").each(function () {
            $(this).children("option").each(function () {
                if (this.value == data.releaseStatus) {
                    $(this).attr("selected", "selected");
                }
            });
        })
        $("#packageResponse").each(function () {
            $(this).children("option").each(function () {
                if (this.value == data.packageResponse) {
                    $(this).attr("selected", "selected");
                }
            });
        })
        $("#packageResponseHead").each(function () {
            $(this).children("option").each(function () {
                if (data.packageResponseHead && this.value === "1") {
                    $(this).attr("selected", "selected");
                }
                if (data.packageResponseHead && this.value === "0") {
                    $(this).attr("selected", "selected");
                }
            });
        })
        $("#packageResponseDetail").each(function () {
            $(this).children("option").each(function () {
                if (data.packageResponseDetail && this.value === "1") {
                    $(this).attr("selected", "selected");
                }
                if (data.packageResponseDetail && this.value === "0") {
                    $(this).attr("selected", "selected");
                }
            });
        })
        $("#dataIsList").each(function () {
            $(this).children("option").each(function () {
                if (data.dataIsList && this.value === "1") {
                    $(this).attr("selected", "selected");
                }
                if (data.dataIsList && this.value === "0") {
                    $(this).attr("selected", "selected");
                }
            });
        })

        if (data.url) {
            var url = data.url;
            var codeUrl = url.replace("/realestate-exchange/rest/v1.0/apiManagement/simple/", "");
            if (codeUrl.indexOf("/") !== -1) {
                codeUrl = codeUrl.substring(0, codeUrl.indexOf("/"));
            }
            if (codeUrl.indexOf("?") !== -1) {
                codeUrl = codeUrl.substring(0, codeUrl.indexOf("?"));
            }
            $("#codeUrl").val(codeUrl);
        }
        $("#url").val(data.url);
        $("#sql").val(data.sql);
        $("#description").val(data.description);
    }

    /**
     * 页面元素渲染
     * @param editType
     */
    function elementShow(editType) {
        var testButton = document.getElementById('test');
        var saveButton = document.getElementById('save');
        if (editType === "0") {
            // 新增页面 url 输入框不显示
            $('input[name=url]').attr("readonly", "readonly")
            testButton.style.display = 'none';
        }
        if (editType === "1") {
            // 编辑页面 url 只读
            testButton.style.display = 'none';
            $('input[name=url]').attr("readonly", "readonly")
        }
        if (editType === "2") {
            // 查看页面 表单只读,测试和报错按钮隐藏
            testButton.style.display = 'none';
            saveButton.style.display = 'none';
            // $('select[name=consumer]').attr("disabled", "disabled");
            formSelects.disabled('consumer');
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
});


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
 * 获取消费方字典项
 * @param dictionary
 */
function getConsumer(dictionary) {
    var consumer = [];
    $.ajax({
        url: getContextPath() + "/rest/v1.0/apiManagement/zdMul",
        dataType: "json",
        data: {
            zdNames: dictionary
        },
        async: false,
        success: function (data) {
            var gxbmbz = data.gxbmbz;
            if (typeof gxbmbz == "undefined" || gxbmbz == null) {
                return consumer;
            }
            for (var i = 0; i < gxbmbz.length; i++) {
                var data = {
                    "name": gxbmbz[i].MC,
                    "value": gxbmbz[i].DM
                };
                consumer.push(data);
            }
        },
        error: function (e) {
        }
    });
    return consumer;
}

function guid() {
    return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

function setParentIndexId(indexId) {
    $("#parentTableIndex").val(indexId);
}


/**
 * 转换字典值
 * @param key
 * @param dictionaryList
 * @returns {string|string}
 */
function convertConsumerDictionary(key, dictionaryList) {
    if (key) {
        for (var i = 0; i < dictionaryList.length; i++) {
            var dictionary = dictionaryList[i];
            if (key === dictionary.value) {
                return dictionary.name;
            }
        }
    }
    return key;
}

/**
 * 获取表头
 * @param paramType
 * @param editType
 * @returns {({field: string, edit: string, title: string, align: string}|{field: string, edit: string, title: string, align: string}|{field: string, templet: (function(*): string), title: string, align: string}|{field: string, templet: (function(*): string), title: string, align: string}|{field: string, edit: string, title: string, align: string})[][]}
 */
function getTableHead(paramType, editType) {
    var cols;
    if (paramType === 0) {
        // 请求体 基本表头
        if (editType === "0" || editType === "1") {
            cols = [[
                {field: 'fieldName', title: '字段名', align: 'center', edit: 'text'},
                {field: 'fieldType', title: '字段类型', align: 'center', templet: '#fieldTypeRequestParamTel'},
                {field: 'required', title: '是否必填', align: 'center', templet: '#requiredTel'},
                // {field: 'defaultValue', title: '字段默认值', align: 'center', edit: 'text'},
                {field: 'fieldRemark', title: '字段说明', align: 'center', edit: 'text'},
                {
                    fixed: 'right', title: '操作', width: 300, align: "center",
                    templet: function (d) {
                        return '<a class="layui-btn layui-btn-xs bdc-delete-btn" lay-event="deleteRow">删除</a><a class="layui-btn layui-btn-xs bdc-major-btnn" lay-event="addObjectParam">添加参数</a>'
                    }
                }
            ]]
        }
        if (editType === "2") {
            cols = [[
                {field: 'fieldName', title: '字段名', align: 'center'},
                {field: 'fieldType', title: '字段类型', align: 'center', templet: '#fieldTypeRequestParamTel'},
                {field: 'required', title: '是否必填', align: 'center', templet: '#requiredTel'},
                // {field: 'defaultValue', title: '字段默认值', align: 'center'},
                {field: 'fieldRemark', title: '字段说明', align: 'center'},
                {
                    fixed: 'right', title: '操作', width: 300, align: "center",
                    templet: function (d) {
                        return '<a class="layui-btn layui-btn-xs bdc-major-btnn" name="showParam" lay-event="showObjectParam">查看参数</a>'
                    }
                }
            ]]
        }

    }
    if (paramType === 1 || paramType === 2) {
        // rest、query参数 基本表头
        if (editType === "0" || editType === "1") {
            cols = [[
                {field: 'fieldName', title: '字段名', align: 'center', edit: 'text'},
                {field: 'required', title: '是否必填', align: 'center', templet: '#requiredTel'},
                // {field: 'defaultValue', title: '字段默认值', align: 'center', edit: 'text'},
                {field: 'fieldRemark', title: '字段说明', align: 'center', edit: 'text'},
                {
                    fixed: 'right', title: '操作', width: 300, align: "center",
                    templet: function (d) {
                        return '<a class="layui-btn layui-btn-xs bdc-delete-btn" lay-event="deleteRow">删除</a>'
                    }
                }
            ]]
        }
        if (editType === "2") {
            cols = [[
                {field: 'fieldName', title: '字段名', align: 'center'},
                {field: 'required', title: '是否必填', align: 'center', templet: '#requiredTel'},
                // {field: 'defaultValue', title: '字段默认值', align: 'center'},
                {field: 'fieldRemark', title: '字段说明', align: 'center'}
            ]]
        }

    }
    if (paramType === 3) {
        if (editType === "0" || editType === "1") {
            cols = [[
                {field: 'fieldName', title: '字段名', align: 'center', edit: 'text'},
                {field: 'fieldRemark', title: '字段说明', align: 'center', edit: 'text'},
                {
                    field: 'isListFlag',
                    title: '是否list',
                    align: 'center',
                    templet: '#switchIsListFlag',
                    unresize: true
                },
                {field: 'cszdmc', title: '参数转换字典项配置', align: 'center', edit: 'text'},
                {field: 'cspj', title: '参数拼接配置', align: 'center', edit: 'text'},
                {field: 'sql', title: 'sql', align: 'center'},
                {
                    fixed: 'right', title: '操作', align: "center",
                    templet: function (d) {
                        return '<a class="layui-btn layui-btn-xs bdc-delete-btn" lay-event="deleteRow">删除</a><a class="layui-btn layui-btn-xs bdc-major-btn" lay-event="addAloneSql">添加sql</a><a class="layui-btn layui-btn-xs bdc-major-btnn" lay-event="addObjectParam">添加参数</a>'
                    }
                }
            ]]
        }
        if (editType === "2") {
            cols = [[
                {field: 'fieldName', title: '字段名', align: 'center'},
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
                    fixed: 'right', title: '操作', width: 300, align: "center",
                    templet: function (d) {
                        return '<a class="layui-btn layui-btn-xs bdc-major-btnn" name="showParam" lay-event="showObjectParam">查看参数</a>'
                    }
                }
            ]]
        }
    }
    if (paramType === 4) {
        if (editType === "0" || editType === "1") {
            cols = [[
                {field: 'fieldName', title: '字段名', align: 'center', edit: 'text'},
                {field: 'fieldRemark', title: '字段说明', align: 'center', edit: 'text'},
                {field: 'defaultValue', title: '默认值', align: 'center', edit: 'text'},
                {
                    fixed: 'right', title: '操作', align: "center",
                    templet: function (d) {
                        return '<a class="layui-btn layui-btn-xs bdc-delete-btn" lay-event="deleteRow">删除</a>'
                    }
                }
            ]]
        }
        if (editType === "2") {
            cols = [[
                {field: 'fieldName', title: '字段名', align: 'center'},
                {field: 'fieldRemark', title: '字段说明', align: 'center'},
                {field: 'defaultValue', title: '默认值', align: 'center'}
            ]]
        }
    }
    return cols;
}

