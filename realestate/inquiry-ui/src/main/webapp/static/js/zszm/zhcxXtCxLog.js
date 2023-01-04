/**
 * 综合查询日志页面JS处理
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate;

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
            title: '综合查询日志列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh', type: 'numbers', width: 60, title: '序号',hide:true},
                {title: '用户名称', width: 150, field: 'yhm'},
                {title: '用户账号', width: 150, field: 'yhzh'},
                {title: '用户ID', width: 150, field: 'yhid'},
                {title: '所在部门', width: 150, field: 'szbm'},
                {title: '坐落', width: 150, field: 'zl'},
                {title: '查询条件', width: 300, field: 'cxtj'},
                {title: '查询结果', minWidth: 200, field: 'cxjg2',
                    templet: function (d) {
                        return formatCxjg(d.cxjg);
                    }
                },
                {title: '操作时间', width: 200, field: 'czsj',
                    templet:function(d){
                        return formatDate(d.czsj);
                    }
                },
                {title: '所在部门ID', width: 150, field: 'szbmid'},
                {title: '权利人名称', width: 150, field: 'qlrmc'},
                {title: '权利人证件号', width: 150, field: 'qlrzjh'},
                {title: '不动产单元号', width: 150, field: 'bdcdyh'},
                {title: '不动产权证号', width: 150, field: 'bdcqzh'},
                {title: '日志类型', width: 150, field: 'rzlx'},
                {title: 'Excel查询文件', width: 150, field: 'excel'},
                {title: '登录IP',   width: 220, field: 'dlip',
                    templet:function(d) {
                        if(!isNullOrEmpty(d.ipaddress)){
                            return d.ipaddress;
                        }

                        if(!isNullOrEmpty(d.remoteAddr)){
                            return d.remoteAddr;
                        }

                        if(!isNullOrEmpty(d.dlip)){
                            return d.dlip;
                        }

                        return "";
                    }
                },
                {title:'操作', field: 'cz',fixed:'right', toolbar: '#tableLine', width:120}
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

        // 处理查询结果展示
        function formatCxjg(cxjg){
            if(!cxjg || cxjg == "[]"){
                return "未查询到数据";
            } else {
                var item = cxjg.substr(0, 500) + "...";
                return item;
            }
        }
        // 监听表格操作栏按钮
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0]);
                    // postExcel(obj.config.cols[0]);
                    break;
                case 'export':
                    postExcel(obj);
                    break;

            }
        });

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

        // 导出Excel
        // 通过隐藏form提交方式，避免ajax方式不支持下载文件
        function exportExcel(d, cols) {
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要导出Excel的数据行！");
                return;
            }

            // 标题
            var showColsTitle = new Array();
            // 列内容
            var showColsField = new Array();
            // 列宽
            var showColsWidth = new Array();
            for (var index in cols) {
                if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
                    showColsTitle.push(cols[index].title);
                    showColsField.push(cols[index].field);
                    showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                }
            }

            // 设置Excel基本信息
            $("#fileName").val('不动产登记综合查询日志信息导出Excel表');
            $("#sheetName").val('不动产登记综合查询日志信息导出Excel表');
            $("#cellTitle").val(showColsTitle);
            $("#cellWidth").val(showColsWidth);
            $("#cellKey").val(showColsField);

            var data = new Array();
            $.each(checkedData, function (key, value) {
                data.push(value);
            })
            for (var i = 0; i < data.length; i++) {
                data[i].xh = i + 1;
                data[i].czsj = formatDate(data[i].czsj);
                data[i].cxjg2 = data[i].cxjg == "[]" ? "未查询到数据" : data[i].cxjg;
            }
            $("#data").val(JSON.stringify(data));
            $("#form").submit();
        }

        // 为导出excel上传当前页和每页行数
        function postExcel(data){
            // 设置Excel基本信息
            var excelList = new Array();
            var excelName = data.config.cols[0];
            var fieldArray = new Array();
            // 获取excel表格第一列
            var nameList = new Array();
            for (let i = 2; i <excelName.length-1 ; i++) {
                nameList.push(excelName[i].title);
                if(excelName[i].field == 'cxjg2'){
                    fieldArray.push('cxjg');
                    continue;
                }
                fieldArray.push(excelName[i].field);
            }
            excelList.push(nameList);
            excelList.push(fieldArray);
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
            $("#exportCol").val(JSON.stringify(excelList));
            $("#title").val('不动产登记'+ document.title + '信息导出Excel表');
            $("#currcount").val(currcount);
            $("#wpage").val(wpage);
            $("#bdcXtCxLogCxtj").val(JSON.stringify(obj));
            $("#dtcxform").submit();
        }

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        // 重置清空查询条件
        $('#reset').on('click', function () {
            $("#alias").val(null);
            $("#principal").val(null);
            $("#beginTime").val(null);
            $("#endTime").val(null);
            $("#ipaddress").val(null);
        });

        search();

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

            addModel();
            table.reload("pageTable", {
                url: '/realestate-inquiry-ui/log/xtcx/list'
                ,where: {bdcXtCxLogCxtj:JSON.stringify(obj)}
                , page: {
                    curr: 1,  //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                ,done: function (res, curr, count) {
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
        //精确到时分秒日期控件
        laydate.render({
            elem: '#beginTime',
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
            elem: '#endTime',
            type: 'datetime',
            done: function (value, date, endDate) {
                var startDate = new Date(value).getTime();
                var endTime = new Date($('#begin').val()).getTime();
                if (endTime > startDate) {
                    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                }
            }
        });
        //监听单元格行
        table.on('tool(pageTable)', function (obj) {
            var data = obj.data;
            if(obj.event === 'jgcx'){
                var checkData = data;
                if(checkData && checkData.id){
                    layer.open({
                        title: '查询结果重现',
                        type: 2,
                        area: ['100%', '100%'],
                        content: 'zhcxjg.html?xtcx=true&id=' + checkData.id,
                        success: function (lay, index) {
                            var iframe = window['layui-layer-iframe' + index];
                            iframe.setData(checkData.cxtj);
                        },
                        end: function () {
                        }
                    });
                }
            }
        });

    });
});



