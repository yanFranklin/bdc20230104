/**
 * 综合查询日志页面JS处理
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate;

    var linshi = [];
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
                {title: '用户名称', width: 150, field: 'alias'},
                {title: '用户账号', width: 150, field: 'principal'},
                {title: '登录IP',   width: 220, field: 'ipaddress',
                    templet:function(d) {
                        if(!isNullOrEmpty(d.ipaddress)){
                            return d.ipaddress;
                        }

                        if(!isNullOrEmpty(d.remoteAddr)){
                            return d.remoteAddr;
                        }

                        if(!isNullOrEmpty(d.ip)){
                            return d.ip;
                        }

                        return "";
                    }
                },
                {title: '查询条件', width: 300, field: 'cxtj'},
                {title: '查询结果', minWidth: 200, field: 'cxjg2',
                    templet: function (d) {
                        return formatCxjg(d.cxjg);
                    }
                },
                {title: '操作时间', width: 200, field: 'time',
                    templet:function(d){
                        return formatDate(d.time);
                    }
                },
                {title:'操作', field: 'cz',fixed:'right', toolbar: '#tableLine', width:120}
            ]],
            data: [],
            page: true,
            limits: [10, 50, 100, 200, 500],
            parseData: function (res,curr,count) {
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

        // 获取配置项
        var dcts;
        $.ajax({
            url: "/realestate-inquiry-ui/log/zhcxLog/pz",
            type: "GET",
            dataType: 'json',
            async: false,
            success: function (res) {
                if (res) {
                    dcts = res;
                } else {
                    failMsg("发现页面未配置正确，请联系管理员解决！");
                }
            },
            error: function () {
                failMsg("发现页面未配置正确，请联系管理员解决！");
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



        // 监听表格操作栏按钮
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0],"checked");
                    // postExcel(obj.config.cols[0]);
                    break;
                case 'export':
                    postExcel(obj.config.cols[0]);
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
        table.on('checkbox(pageTable)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;
            // console.log(layui.sessionData('checkedData'));
            $.each(data, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: v.id, value: v.id
                    });
                    //数据量过大，不放到sessionStorage，放数组中
                    linshi.push(v);
                } else {
                    //.删除
                    var idDel = v.id;
                    layui.sessionData('checkedData', {
                        key: v.id, remove: true
                    });
                    //遍历数组，删除操作
                    for (var k = 0; k < linshi.length; k++) {
                        var value = linshi[k];
                        if (value) {
                            var id = value.id;
                            if (id == idDel) {
                                linshi[k] = null;
                            }
                        }
                    }
                }
            });
            //添加已选条目数
            var checkedData = layui.sessionData('checkedData');
            var arrcheck = Object.keys(checkedData);
            var total = arrcheck.length;
            $('#choosecount').html("已选"+total+"条");
        });

        // 导出Excel
        // 通过隐藏form提交方式，避免ajax方式不支持下载文件
        function exportExcel(d, cols,type) {
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData) && type == "checked") {
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
            if (type == "checked") {
                // $.each(checkedData, function (key, value) {
                //     data.push(value);
                // })

                for(var j = 0; j <linshi.length; j++) {
                    if (linshi[j]) {
                        data.push(linshi[j]);
                    }
                }
            }

            if (type == "all") {
                data = d;
            }


            for (var i = 0; i < data.length; i++) {
                data[i].xh = i + 1;
                data[i].time = formatDate(data[i].time);
                data[i].cxjg2 = data[i].cxjg == "[]" ? "未查询到数据" : data[i].cxjg;
            }
            $("#data").val(JSON.stringify(data));
            $("#form").submit();
        }

        function exportAllExcel(data, obj){
            var cols = obj.cols[0];
            // postExcel(cols,dcts);
            var url = obj.url;
            var paramData = obj.where;
            if (dcts > 0) {
                url = obj.url+"?page=0&&size="+dcts;
            } else {
                // paramData["type"] = "export";
            }

            $.ajax({
                url: url,
                dataType: "json",
                data: paramData,
                success: function (data) {
                    obj.where.type = "";
                    if (dcts > 0) {
                        if(data.code == 0){//查询成功
                            exportExcel(data.content,cols, "all");
                        }
                    } else {
                        if(data.length > 0){//查询成功
                            exportExcel(data,cols, "all");
                        }
                    }

                }
            });
        }


        // 为导出excel上传当前页和每页行数
        function postExcel(cols){
            var exportCol = {};
            for (var index in cols) {
                if (cols[index].type != 'checkbox' && !cols[index].toolbar && cols[index].field != 'cz') {
                    exportCol[cols[index].title] = cols[index].field
                }
            }
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

            // 设置日志类型
            obj.eventName = "ZHCXLOG";
            // 模糊查询
            obj.cxfs = "like";
            obj.wpage = wpage;
            obj.currcount = currcount;

            obj.exportCol = encodeURIComponent(JSON.stringify(exportCol));

            var tempForm = $("<form>");
            tempForm.attr("id", "exportFrom");
            tempForm.attr("style", "display:none");
            tempForm.attr("target", "export");
            tempForm.attr("method", "get");
            tempForm.attr("action", '/realestate-inquiry-ui/log/export');

            for(key in obj){
                var dataInput= $("<input>");
                dataInput.attr("type", "hidden");
                dataInput.attr("name", key);
                dataInput.attr("value",obj[key]);
                tempForm.append(dataInput);
            }
            tempForm.on("submit", function () {});
            tempForm.trigger("submit");
            $("body").append(tempForm);
            tempForm.submit();
            $("exportFrom").remove();
            return false;

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

            // 设置日志类型
            obj.eventName = "ZHCXLOG";
            // 模糊查询
            obj.cxfs = "like";

            addModel();
            table.reload("pageTable", {
                url: '/realestate-inquiry-ui/log/list'
                ,where: obj
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
                    //添加已选条目数
                    var checkedData = layui.sessionData('checkedData');
                    var arrcheck = Object.keys(checkedData);
                    var total = arrcheck.length;
                    $('#choosecount').html("已选"+total+"条");
                }
            });
        }

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
                        content: 'zhcxjg.html?id=' + checkData.id,
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

    //点击高级查询-一般情况（非4的倍数）
    $('#seniorSearch').on('click',function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);

        if($(this).html() == '高级查询'){
            $(this).html('收起');
        }else {
            $(this).html('高级查询');
        }

        if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
            $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
        }else {
            $('.layui-table-body').height($('.bdc-table-box').height() - 131);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
        }
    });
});

