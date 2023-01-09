/**
 * author: <a href="mailto:wutao@gtmap.cn>wutao</a>
 * version 1.0.0  2022/12/29
 * describe: 月报统计
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

// 用户IP地址
var ipaddress;
var reverseList = ['zl'];
// 分页重置查询参数
var searchParam =[];

var tableData = [];

var djjgList = [];


// var dcts;
layui.use(['jquery', 'element', 'form', 'table', 'laydate', 'formSelects'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var formSelects = layui.formSelects;
    var laydate = layui.laydate;


    form.render();

    // 当前页数据
    var currentPageData = new Array();
    $(function () {
        lay('.test-item').each(function(){
            //初始化日期控件
            var startDate = laydate.render({
                elem: "#kssj",
                trigger: 'click',
                type: 'datetime',
                min:"2022-10-09 00:00:00",
                done: function(value,date){
                    //选择的结束时间大
                    if($("#jzsj").val() != '' && !completeDate($("#jzsj").val() ,value)){
                        $("#jzsj").val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                        layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                    }
                    endDate.config.min ={
                        year:date.year,
                        month:date.month-1,
                        date: date.date
                    }
                }
            });
            var endDate = laydate.render({
                elem: "#jzsj",
                trigger: 'click',
                type: 'datetime',
                min:"2022-10-09 00:00:00",
            });

        });
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        // 加载Table
        table.render({
            elem: '#bdcdjywzbtjTable',
            toolbar: '#toolbar',
            even: true,
            loadTotal: true,
            title: '不动产业务占比统计',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left',width:'3%'},
                {field: 'djjg', title: '不动产登记机构', width:'10%'},

                {field: 'wsywzb', title: '网上办理业务量占全部业务总量比例',width:'14%', templet: function (d) {
                        return formatSl(d.wsywzb);
                    }
                },
                {field: 'scdjywzb', title: '首次登记：网上办理业务量占本登记业务总量比例',width:'14%', templet: function (d) {
                        return formatSl(d.scdjywzb);
                    }
                },
                {field: 'zydjywzb', title: '转移登记：网上办理业务量占本登记业务总量比例',width:'14%', templet: function (d) {
                        return formatSl(d.zydjywzb);
                    }
                },
                {field: 'dydjywzb', title: '抵押登记：网上办理业务量占本登记业务总量比例', width:'14%', templet: function (d) {
                        return formatSl(d.dydjywzb);
                    }
                },
                {field: 'ygdjywzb', title: '预告登记：网上办理业务量占本登记业务总量比例', width:'16%', templet: function (d) {
                        return formatSl(d.ygdjywzb);
                    }
                },
                {field: 'cfdjywzb', title: '查封登记：网上办理业务量占本登记业务总量比例', width:'16%', templet: function (d) {
                        return formatSl(d.cfdjywzb);
                    }
                }
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if(res.content && res.content.length > 0){
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function(key, value){
                            if(key == v.xmid){
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
                reverseTableCell(reverseList);
                // 控制按钮权限
                if(moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
            }
        });

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //查询
        function search() {
            // 每次查询清除下导出缓存数据
            layui.sessionData("checkedData", null);

            // 获取查询内容
            var obj = {};
            $(".search").each(function (i) {
                var value = $(this).val();
                var name = $(this).attr('name');
                if(!isNullOrEmpty(value)){
                    obj[name] = value;
                }
            });

            var djjg=formSelects.value('selectDjjg','name').join(",");
            // 如果没有勾选登记机构默认勾选全部
            if(!isNotBlank(djjg)){
                formSelects.value("selectDjjg",djjgList)
                obj.djjg=formSelects.value('selectDjjg','name').join(",");
            }else{
                obj.djjg = djjg;
            }
            if(isNotBlank(obj.kssj)){
                obj.kssj = strFormatDate(obj.kssj).Format('yyyyMMddhhmmss');
            }
            if(isNotBlank(obj.jzsj)){
                obj.jzsj = strFormatDate(obj.jzsj).Format('yyyyMMddhhmmss');
            }

            searchParam = obj;
            obj.moduleCode=moduleCode;
            addModel();
            // 重新请求
            table.reload("bdcdjywzbtjTable", {
                url: getContextPath() + "/rest/v1.0/ybtj/bdcywzbtj",
                where: obj,
                method: 'post',
                loadTotal: true,
                even: true,
                page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                },
                done: function (res, curr, count) {
                    currentPageData = res.data;
                    tableData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                    //添加已选条目数
                    var checkedData = layui.sessionData('checkedData');
                    var arrcheck = Object.keys(checkedData);
                    var total = arrcheck.length;
                    $('#choosecount').html("已选"+total+"条");
                }
            });
        }

        //2.默认渲染部门名称，选中名称后，根据名称渲染办理人员
        getDataByAjax('/rest/v1.0/ybtj/listOrgs','','GET',function (data) {
            var bmData = [];
            data.forEach(function (v) {
                bmData.push({"name": v.name, "value": v.id});
                djjgList.push(v.id);
            });
            formSelects.data('selectDjjg', 'local', {
                arr: bmData
            });
        });

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });
    });

    // 监听表格操作栏按钮
    table.on('toolbar(bdcdjywzbtjTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'exportExcel':
                exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                break;
        }
    });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(bdcdjywzbtjTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;
        $.each(data, function (i, v) {
            if (obj.checked) {
                //.增加已选中项
                layui.sessionData('checkedData', {
                    key: v.xmid, value: v
                });
            } else {
                //.删除
                layui.sessionData('checkedData', {
                    key: v.xmid, remove: true
                });
            }
        });
        //添加已选条目数
        var checkedData = layui.sessionData('checkedData');
        var arrcheck = Object.keys(checkedData);
        var total = arrcheck.length;
        $('#choosecount').html("已选"+total+"条");
    });

    function exportExcel(d, cols, type){
        if($.isEmptyObject(d) && type == "checked"){
            // 未选择数据时，询问是否导出全部
            layer.confirm('未勾选数据,将导出全部数据？', {
                title: '提示',
                btn: ['确定', '取消'] //按钮
            }, function (index) {
                if ($.isEmptyObject(tableData)) {
                    warnMsg("未获取到数据，请先查询后在进行导出。");
                    return;
                }
                doExport(tableData, cols, type);
                layer.close(index);
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
            if(cols[index].type != 'checkbox' && !cols[index].toolbar){
                showColsTitle.push(cols[index].title);
                showColsField.push(cols[index].field);
                if(cols[index].width > 0){
                    showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                }else if(cols[index].minWidth > 0){
                    showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
                }else{
                    showColsWidth.push(1000 / 100 * 5);
                }
            }
        }

        // 设置Excel基本信息
        $("#fileName").val('互联网+不动产登记业务占比统计');
        $("#sheetName").val('互联网+不动产登记业务占比统计表');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#data").val(JSON.stringify(d));
        $("#form").submit();
    }


    // 保存记录操作信息
    function saveDetailLog(logType, viewTypeName, data){
        var json = JSON.parse(JSON.stringify(data));
        json.logType = logType;
        json.ipaddress = ipaddress;
        json.viewTypeName = viewTypeName;
        saveLogInfo(json);
    }
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

    function formatSl(data){
        if(isNotBlank(data)){
            return data;
        }else{
            return 0;
        }
    }

    function formatSl1(data){
        if(isNotBlank(data)){
            return data;
        }else{
            return "—";
        }
    }

    //时间比对
    function completeDate(date1, date2) {
        var oDate1 = strFormatDate(date1);
        var oDate2 = strFormatDate(date2);
        if (oDate1.getTime() > oDate2.getTime()) {
            return true;
        } else {
            return false;
        }
    }
});