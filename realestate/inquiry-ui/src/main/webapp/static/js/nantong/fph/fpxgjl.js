/**
 * 发票修改记录
 */
var laydate, form, table;
var currentPageData = [];
// 获取字典
layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'upload'], function () {
    var layer = layui.layer;
    laydate = layui.laydate;
    form = layui.form;
    table = layui.table;

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');

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
        {field: 'fph',title: '发票号', width: 150},
        {field: 'slbh', title: '受理编号', width: 150},
        {field: 'zl', minWidth: 250, title: '坐落'},
        {field: 'xgsj', title: '修改时间', width: 180, templet: function (d) {
                return formatDate(d.xgsj);
            }},
        {field: 'xgqzd', title: '修改前字段', width: 200, templet: function (d) {
                return fmtXgxx(d.xgqzd, "<br>");
            }},
        {field: 'xghzd', title: '修改后字段', width: 200, templet: function (d) {
                return fmtXgxx(d.xghzd, "<br>");
            }},
        {field: 'xgr', title: '修改人', width: 100},
    ];

    // 加载Table
    table.render({
        elem: '#bdcFpXgjlTable',
        toolbar: '#toolbar',
        title: '发票修改记录',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        url: "/realestate-inquiry-ui/rest/v1.0/fpxgjl/page",
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
                        if (key == v.xgjlid) {
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
            table.reload("bdcFpXgjlTable", {
                url: '/realestate-inquiry-ui/rest/v1.0/fpxgjl/page',
                where: obj,
                page: {
                    curr: 1  //重新从第 1 页开始
                },
                done: function (res, curr, count) {
                    currentPageData = res.data;
                    removeModal();
                    setHeight();
                    table.resize('bdcFpXgjlTable');
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
    table.on('toolbar(bdcFpXgjlTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'exportExcel':
                exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                break;
            case 'exportAll':
                exportAll(obj.config);
        }
    });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(bdcFpXgjlTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;
        $.each(data, function (i, v) {
            var keyT = v.xgjlid;
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

function fmtXgxx(data, linesign){
    if(isNotBlank(data)){
        var obj = JSON.parse(data);
        var xgxx = [];
        $.each(obj, function(index, val){
            if(val.key == "sfzt"){
                var sfzt = val.value == 2? "已收费" : "未收费" ;
                xgxx.push(val.name + "：" + sfzt);
            }else if(val.key == "sfxsjf"){
                var sfxsjf = val.value == 1? "是" : "否" ;
                xgxx.push(val.name + "：" + sfxsjf);
            }else{
                xgxx.push(val.name + "：" + val.value);
            }
        });
        return xgxx.join(linesign);
    }else{
        return "";
    }
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

    var data = [];
    data = d;
    for (var i = 0; i < data.length; i++) {
        data[i].xh = i + 1;
        data[i].xgqzd = fmtXgxx(data[i].xgqzd, "\n");
        data[i].xghzd = fmtXgxx(data[i].xghzd, "\n");
    }

    // 设置Excel基本信息
    $("#fileName").val('发票修改信息记录');
    $("#sheetName").val('统计表');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#data").val(JSON.stringify(data));
    $("#addBorder").val(true);
    $("#form").submit();
}

function exportAll(obj){
    var paramData = obj.where;
    var cols = obj.cols[0]
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/fpxgjl/list",
        dataType: "json",
        data: paramData,
        success: function (data) {
            if(isNotBlank(data)){
                exportExcel(data, cols, "all");
            }
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