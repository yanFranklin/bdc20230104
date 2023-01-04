/**
 * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @description 房产批量查询
 */

var zjhs = "";
var qlrmcs = "";
// 导入Excel查询中的行序号
var xhs = "";
var uploadInst = null;
var upload = null;
var reverseList = ['zl'];
layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'upload'], function () {
    var laydate = layui.laydate;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    upload = layui.upload;

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');

    layui.sessionData('checkedData', null);

    layui.sessionData('checkedData', null);

    //x 的事件
    form.on('select', function (data) {
        if($(this).text() == "请选择"){
            $(this).parents('.layui-input-inline').find('.reseticon').hide();
        }else{
            $(this).parents('.layui-input-inline').find('.reseticon').show();
        }
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
        zjhs = "";
        qlrmcs = "";
        xhs = "";
    })

    cols = [
        {type: 'qlid', title: 'qlid', hide: true},
        {type: 'xmid', title: 'xmid', hide: true},
        {field: 'xh',title: '序号', width: 60},
        {field: 'qlr',title: '权利人', width: 150},
        {field: 'zjh', title: '身份证号', width: 200},
        // {field: 'cxjg', title: '查询结果（套）', width: 100},
        {field: 'jzmj', minWidth: 130, title: '建筑面积'},
        {field: 'zl', minWidth: 250, title: '坐落'},
        {field: 'ghyt', title: '规划用途', width: 130},
        {field: 'djsj', title: '登记时间', width: 130},
        {field: 'zxdjsj', title: '注销时间', width: 130},
        {field: 'bdcqzh', title: '不动产权证号', width: 250},
        {field: 'bz', title: '备注',width: 180},
        {
            field: 'qszt', title: '权属状态', fixed: 'right', width: 120, templet: function (d) {
                return formatQszt(d.qszt);
            }
        }
    ];
// 加载Table
    table.render({
        elem: '#bdcPlcxTable',
        toolbar: '#toolbar',
        title: '房产信息',
        method: "POST",
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
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
                        if (key == v.qlid) {
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
            setHeight();
            if(moduleCode){
                setElementAttrByModuleAuthority(moduleCode);
            }
        }
    });

    // 监听表单提交事件
    form.on('submit(search)', function (data) {
        if (data.field) {
            if(data.field.singleQlr == "" && data.field.singleZjh == ""){
                warnMsg("请输入查询条件！");
                return false;
            }

            addModel();
            // 重新请求
            // 获取查询参数
            var obj = data.field;

            console.log(obj);
            table.reload("bdcPlcxTable", {
                url: '/realestate-inquiry-ui/plcx/yancheng/list'
                , where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    removeModal();
                    setHeight();
                    reverseTableCell(reverseList);

                    table.resize('bdcPlcxTable');
                    //initUploadInst();
                    zjhs = "";
                    qlrmcs = "";
                    xhs = "";
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
    table.on('toolbar(bdcPlcxTable)', function (obj) {
        switch (obj.event) {
            case 'exportExcel':
                exportExcel(obj.config.cols[0], obj.config.where);
                break;
            case 'import' :
                $("#importHide").click();
                break;
            case 'download':
                var url = "/realestate-inquiry-ui/static/js/yancheng/fcplcx/model/model.xls";
                window.location.href = url;
                break;
        }
    });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(bdcPlcxTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;
        $.each(data, function (i, v) {
            var keyT = v.xmid;
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

    initUploadInst();

})

// 格式化权属状态
function formatQszt(qszt) {
    if (qszt == 0) {
        return '<span class="" style="color:#1d87d1;">临时</span>';
    } else if (qszt == 1) {
        return '<span class="" style="color:#32b032;">现势</span>'
    } else if (qszt == 2) {
        return '<span class="" style="color:#f28943;">历史</span>'
    } else if (qszt == 3) {
        return '<span class="" style="color:#f24b43;">终止</span>'
    } else {
        return '';
    }
}

/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.24 14:45
 * @author zhuyong
 * @return
 */
function exportExcel(cols, bdcPlcxQO) {
    if(isEmptyObject(bdcPlcxQO)){
        warnMsg("请先查询数据！");
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

    // 设置Excel基本信息
    $("#fileName").val('批量信息');
    $("#sheetName").val('统计表');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#exportSingleQlr").val(bdcPlcxQO.singleQlr);
    $("#exportSingleZjh").val(bdcPlcxQO.singleZjh);
    $("#exportXh").val($("#xh").val());
    $("#exportQszt").val(bdcPlcxQO.qszt);
    $("#exportBdclx").val(bdcPlcxQO.bdclx);
    $("#form").submit();
}

function delHtmltag(str) {
    var returnStr = "";
    if (str != "" && str != null) {
        returnStr = str.replace(/<\/?.+?>/g, "");
        returnStr = returnStr.replace(/ /g, "");
    }
    return returnStr;
}

function initUploadInst(){

    uploadInst = upload.render({
        elem: '#importHide' //绑定元素
        ,accept: 'file'
        ,url: '/realestate-inquiry-ui/plcx/import/fccx/excel?parameter=any' //上传接口
        ,done: function(res){
            if(res == null || res.length == 0){
                layer.alert('导入失败，请修改补全数据！', {title: '提示'});
                return;
            }
            if(res.code == 1) {
                layer.alert(res.msg, {title: '提示'});
                return;
            }
            var success = true;
            for(var i=0;i<res.length;i++){
                if(isNullOrEmpty(res[i].qlrmc) && isNullOrEmpty(res[i].zjh)){
                    layer.alert('存在同时缺失权利人名称、证号记录！', {title: '提示'});
                    success = false;
                    break;
                }

                if(!isNullOrEmpty(res[i].zjh)) {
                    zjhs += res[i].zjh+",";
                }

                if(!isNullOrEmpty(res[i].qlrmc)) {
                    qlrmcs += res[i].qlrmc+",";
                }

                if(!isNullOrEmpty(res[i].xh)) {
                    xhs += res[i].xh + ",";
                }
            }
            if(!success) return;

            var zjhParams = zjhs.substring(0, zjhs.length - 1);
            var qlrParams = qlrmcs.substring(0,qlrmcs.length-1);
            var xhParams = xhs.substring(0, xhs.length-1);

            // 判断查询方式
            var cxfs = $("#cxfs").val();
            if(!isNullOrEmpty(cxfs)) {
                if("xm" == cxfs) {
                    // 只按照姓名查询
                    zjhParams = "";
                } else if("sfz" == cxfs) {
                    // 只按照身份证号查询
                    qlrParams = "";
                } else {
                    // 同时按照名称证号查询
                }
            }

            $("#singleZjh").val(zjhParams);
            $("#singleQlr").val(qlrParams);
            $("#xh").val(xhParams);
            removeModal();
            $("#search").click();
        }
        ,error: function(e){
            removeModal();
            layer.alert('导入数据解析失败，请检查模板是否正确！', {title: '提示'});
        }
    });

}