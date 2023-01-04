/**
 * 综合查询日志页面JS处理
 */
layui.config({
    base: '../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        formSelects = layui.formSelects;

    $(function () {
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');

        // 当前页数据
        var currentPageData = new Array();
        var wpage,     //当前页码
            currcount; //每页行数

        // 加载Table
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            title: '收件量统计列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', width: '4%', fixed: 'left'},
                {title: '受理编号', Width: '20%', field: 'SLBH',  templet: '#slbhTpl', event: 'openpage'},
                {title: '受理人员', width: '20%', field: 'SLR'},
                {title: '业务名称', width: '26%', field: 'GZLDYMC'},
                {title: '受理时间', width: '30%', field: 'SLSJ',},
            ]],
            data: [],
            page: true,
            limits: [10, 50, 100, 200, 500],
            parseData: function (res,curr,count) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content, //解析数据列表
                }
            },
            done: function () {
                setHeight();
                if(moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
            }
        });


        //2.默认渲染部门名称，选中名称后，根据名称渲染办理人员
        getDataByAjax('/rest/v1.0/process/listOrgs','','GET',function (data) {
            var bmData = [];
            data.forEach(function (v) {
                bmData.push({"name": v.name, "value": v.id});
            });
            formSelects.data('selectBmmc', 'local', {
                arr: bmData
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

        var beginTime = laydate.render({
            elem: '#beginTime',
            type: 'datetime',
            trigger: 'click',
            done: function (value, date) {
                //选择的结束时间大
                if ($('#endTime').val() != '' && !completeDate($('#endTime').val(), value)) {
                    $('#endTime').val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                }
                endTime.config.min = {
                    year: date.year,
                    month: date.month - 1,
                    date: date.date,
                    hours: date.hours,
                    minutes: date.minutes,
                    seconds: date.seconds
                }
            }
        });
        var endTime = laydate.render({
            elem: '#endTime',
            type: 'datetime',
            trigger: 'click'
        });


        //比较起止时间
        function completeDate(date1, date2) {
            var oDate1 = new Date(date1);
            var oDate2 = new Date(date2);
            if (oDate1.getTime() > oDate2.getTime()) {
                return true;
            } else {
                return false;
            }
        }

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(pageTable)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;

            $.each(data, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: v.id, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: v.id, remove: true
                    });
                }
            });
        });

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        // 重置清空查询条件
        $('#reset').on('click', function () {
            $("#slbh").val(null);
            $("#beginTime").val(null);
            $("#endTime").val(null);
            $("#bm").val(null);
        });

        // 监听表格操作栏按钮
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                    break;
            }
        });
        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(pageTable)', function(obj){
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
                        url: '/realestate-inquiry-ui/gzltj/mxSjl?loadTotal=true&page=1&size=9999',
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
            $("#fileName").val('统计收件量');
            $("#sheetName").val('统计表');
            $("#cellTitle").val(showColsTitle);
            $("#cellWidth").val(showColsWidth);
            $("#cellKey").val(showColsField);
            $("#data").val(JSON.stringify(data));
            $("#totalInfo").val('合计,'+d.length);
            $("#expandServiceName").val('excelExpandYcService');
            $("#form").submit();
        }

        //获取查询参数
        function getSearchParam(){
            // 获取查询参数
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value && name != "beginTime" && name != "endTime") {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });
            obj.djjg =  formSelects.value('selectBmmc', 'name')[0];
            return obj;
        }

        // 查询处理逻辑
        function search() {
            // 每次查询清除下导出缓存数据
            layui.sessionData("checkedData", null);

            // 获取查询参数
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value && name != "beginTime" && name != "endTime") {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });
            obj.djjg =  formSelects.value('selectBmmc', 'name')[0];

            addModel();
            table.reload("pageTable", {
                url: '/realestate-inquiry-ui/gzltj/mxSjl'
                ,where: obj
                , page: {
                    curr: 1,  //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                ,done: function (res, curr, count) {
                    $('#count').text(count);
                    currentPageData = res.data;
                    wpage = curr;//当前页码
                    currcount = res.data.length;//每页行数
                    removeModal();
                    setHeight();
                    if(moduleCode){
                        setElementAttrByModuleAuthority(moduleCode);
                    }
                }
            });
        }
    });
});

