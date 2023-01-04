/**
 * 退费信息
 */
var laydate, form, table;
var currentPageData = [];
layui.use(['form', 'jquery', 'element', 'table', 'laydate'], function () {
    laydate = layui.laydate;
    form = layui.form;
    var layer = layui.layer;
    table = layui.table;

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');

    // 渲染时间
    renderDate("#sfkssj", "#sfjssj");
    renderDate("#sqtfkssj", "#sqtfjssj");

    // 加载树
    loadOrgTree("select_tree", function(event, treeId, treeNode){
        var treeDiv = $("#" + treeId).parents(".layui-inline");
        $(treeDiv).find("input[name=bmmc]").val(treeNode.name);
    });

    $('.reseticon').on('click',function(item){
        $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
        $(this).hide();
        layui.form.render("select");
    })
    $('#reset').on('click',function(item){
        $('.bdc-percentage-container').find('.layui-form')
            .find('select').find("option:eq(0)")
            .attr("selected","selected");
        setTimeout($('.bdc-percentage-container').find('.layui-form')
            .find('select').parent().find('input').val(''),100);
        $('.reseticon').hide();
    });

    var cols = [
        {type: 'checkbox', fixed: 'left'},
        {type: 'numbers', field: 'xh', title: '序号', width: 50, fixed: 'left'},
        {field: 'bmmc',title: '部门名称', width: 180},
        {field: 'slbh', title: '受理编号', width: 150},
        {field: 'jfsbm', title: '缴款书编码', width: 150},
        {field: 'jkrxm', title: '缴款人姓名', width: 130},
        {field: 'djf', title: '不动产登记费',  minWidth: 130},
        {field: 'tdsyqjyfwf',  title: '土地使用权交易服务费', minWidth: 130},
        {field: 'sjsfzje', title: '实际收费总金额', width: 130},
        {field: 'sfsj', title: '收费时间', width: 150,
            templet: function (d) {
                return formatDate(d.sfsj);
            }},
        {field: 'sjtfzje', title: '实际收费总金额', width: 130},
        {field: 'pjlx', title: '票据类型', width: 130,
            templet: function (d) {
                if(d.pjlx == "1"){
                    return "纸质票";
                }else if(d.pjlx == "2"){
                    return "电子票";
                }
            }},
        {field: 'sqtfsj', title: '申请退费时间', width: 150 ,
            templet: function (d) {
                return formatDate(d.sqtfsj);
            }},
        {field: 'sqtfyy', title: '申请退费原因', width: 200},
    ];

    // 加载Table
    table.render({
        elem: '#bdcTfxxTable',
        toolbar: '#toolbar',
        title: '退费信息',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        url: "/realestate-inquiry-ui/rest/v1.0/tfxx/page",
        even: true,
        cols: [cols],
        data: [],
        page: true,
        limits: [10, 20, 50, 100, 200, 500],
        parseData: function (res) {
            // 设置选中行
            if (res.content && res.content.length > 0) {
                var checkedData = layui.sessionData('checkedData');
                res.content.forEach(function (v) {
                    $.each(checkedData, function (key, value) {
                        if (key == v.tfxxid) {
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
            if(moduleCode){
                setElementAttrByModuleAuthority(moduleCode);
            }
        }
    });

    // 监听表单提交事件
    form.on('submit(search)', function (data) {
        if (data.field) {
            addModel();
            var obj = data.field;
            console.log(obj);
            table.reload("bdcTfxxTable", {
                url: '/realestate-inquiry-ui/rest/v1.0/tfxx/page',
                where: obj,
                page: {
                    curr: 1  //重新从第 1 页开始
                },
                done: function (res, curr, count) {
                    currentPageData = res.data;
                    removeModal();
                    setHeight();
                    table.resize('bdcTfxxTable');
                    if(moduleCode){
                        setElementAttrByModuleAuthority(moduleCode);
                    }
                }
            });
        } else {
            layer.msg("请输入查询条件！");
        }
        return false;
    });

    // 监听表格操作栏按钮
    table.on('toolbar(bdcTfxxTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'exportExcel':
                exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                break;
            case 'exportAll':
                exportAll(obj.config);
                break;
            case 'addTfxx':
                addTfxx();
                break;
            case 'deleteTfxx':
                deleteTfxx(checkStatus.data);
                break;
        }
    });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(bdcTfxxTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;
        $.each(data, function (i, v) {
            var keyT = v.tfxxid;
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
});

function addTfxx(){
    layer.open({
        type: 2,
        title: "新增",
        area: ['1150px', '600px'],
        btn: ['保存', '取消'],
        fixed: false,
        maxmin: true, //开启最大化最小化按钮
        content: "../fph/addTfxx.html",
        yes : function (index, layero) {
            var iframeWin = window[layero.find('iframe')[0]['name']];
            iframeWin.saveTfxx().done(function(){
                table.reload('bdcTfxxTable', {page: {curr: 1}});
                layer.close(index);
            });
        },
        cancel : function(index){
            layer.close(index);
        }

    });
}

function exportExcel(d, cols, type) {
    var checkedData = layui.sessionData('checkedData');
    if ($.isEmptyObject(checkedData) && type == "checked") {
        layer.alert("请选择需要导出Excel的数据行！", {title: '提示'});
        return;
    }

    // 标题
    var showColsTitle = [];
    // 列内容
    var showColsField = [];
    // 列宽
    var showColsWidth = [];
    for (var index in cols) {
        if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
            showColsTitle.push(cols[index].title);
            showColsField.push(cols[index].field);
            if (cols[index].width > 0) {
                showColsWidth.push(parseInt(cols[index].width / 100 * 15));
            } else if (cols[index].minWidth > 0) {
                showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
            } else {
                showColsWidth.push(200 / 100 * 15);
            }
        }
    }

    var data = new Array();
    data = d;
    for (var i = 0; i < data.length; i++) {
        data[i].xh = i + 1;
    }

    // 设置Excel基本信息
    $("#fileName").val('退费信息');
    $("#sheetName").val('统计表');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#data").val(JSON.stringify(data));
    $("#form").submit();
}

function exportAll(obj){
    var paramData = obj.where;
    var cols = obj.cols[0]
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/tfxx/list",
        dataType: "json",
        data: paramData,
        success: function (data) {
            if(isNotBlank(data)){
                exportExcel(data, cols, "all");
            }
        }
    });
}

function deleteTfxx(data){
    if(data.length == 0){
        layer.msg("请选择需要的退费信息。");
    }
    console.info(data);
    var tfxxidList = [];
    $.each(data, function(index, val){
        tfxxidList.push(val.tfxxid);
    });
    console.info(tfxxidList);

    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/tfxx/list",
        dataType: "json",
        type: "DELETE",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(tfxxidList),
        success: function (data) {
            table.reload('bdcTfxxTable', {page: {curr: 1}});
        }
    });
}

function delHtmltag(str) {
    var returnStr = "";
    if (str != "" && str != null) {
        returnStr = str.replace(/<\/?.+?>/g, "");
        returnStr = returnStr.replace(/ /g, "");
    }
    return returnStr;
}


function renderDate(kssjElem, jssjElem){
    lay('.test-item').each(function(){
        //初始化日期控件
        var startDate = laydate.render({
            elem: kssjElem,
            trigger: 'click',
            format: 'yyyy-MM-dd HH:mm:ss',
            done: function(value,date){
                //选择的结束时间大
                if($(jssjElem).val() != '' && !completeDate($(jssjElem).val(),value)){
                    $(jssjElem).val('');
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
            elem: jssjElem,
            trigger: 'click',
            format: 'yyyy-MM-dd HH:mm:ss',
        });

    });
}