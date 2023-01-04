/**
 * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @description 房产批量查询
 */

var zjhs = "";
var qlrmcs = "";
var uploadInst = null;
var upload = null;
layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'upload'], function () {
    var laydate = layui.laydate;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    upload = layui.upload;

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');

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
    })

    var dcts;
    // 获取配置项
    $.ajax({
        url: "/realestate-inquiry-ui/plcx/pz",
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

    cols = [
        {type: 'qlid', title: 'qlid', hide: true},
        {type: 'xmid', title: 'xmid', hide: true},
        {type: 'checkbox', fixed: 'left'},
        {type: 'numbers', field: 'xh', title: '序号', width: 70, fixed: 'left'},
        {field: 'qlr',title: '权利人', width: 100},
        {field: 'zjh', title: '证件号', width: 180},
        {field: 'bdcdyh', title: '不动产单元号', width: 260},
        {field: 'ghyt', title: '用途', width: 180},
        {field: 'bdcqzh', title: '不动产权证号', width: 260},
        {field: 'zl', minWidth: 300, title: '坐落'},
        {field: 'gyrxm', title: '共有人姓名', width: 130},
        {field: 'jzmj', minWidth: 130, title: '建筑面积'},
        {field: 'jyjg', minWidth: 130, title: '交易价格'},
        {field: 'djsj', title: '登记时间', width: 180},
        {field: 'bz', title: '备注', width: 180},
        {
            field: 'qszt', title: '权属状态', fixed: 'right', width: 120, templet: function (d) {
                return formatQszt(d.qszt);
            }
        },
        {
            field: 'bdcdyZtDTO', width: 180, title: '限制状态', fixed: 'right', sort: true,
            templet: function (d) {
                return formatXzzt(d.bdcdyZtDTO);
            }
        },
    ];
// 加载Table
    table.render({
        elem: '#bdcPlcxTable',
        toolbar: '#toolbar',
        title: '房产信息',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        method: "POST",
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
                        if (key == v.xmid) {
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
            //reverseTableCell(reverseList);
            setHeight();
            if(moduleCode){
                setElementAttrByModuleAuthority(moduleCode);
            }
        }
    });

    // 监听表单提交事件
    form.on('submit(search)', function (data) {
        layui.sessionData('checkedData', null);
        if (data.field) {
            if(data.field.singleQlr == "" && data.field.singleZjh == "" && data.field.qszt == ""){
                layer.msg("请输入查询条件！");
                return false;
            }
            addModel();
            // 重新请求
            // 获取查询参数
            var obj = data.field;
            obj.moduleCode = moduleCode;

            console.log(obj);
            table.reload("bdcPlcxTable", {
                url: '/realestate-inquiry-ui/plcx/list'
                , where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    removeModal();
                    setHeight();
                    //reverseTableCell(reverseList);
                    table.resize('bdcPlcxTable');
                    //saveDetailLog("PLCX","批量查询", obj);
                    //initUploadInst();
                    zjhs = "";
                    qlrmcs = "";
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
        } else {
            layer.msg("请输入查询条件！");
        }
        return false;
    });

    // 监听表格操作栏按钮
    table.on('toolbar(bdcPlcxTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'exportExcel':
                exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                break;
            case 'exportAllExcel':
                layer.confirm("是否导出全部数据？",{
                    title: "提示",
                    btn: ["确认","取消"],
                    btn1: function (index) {
                        exportAllExcel(checkStatus.data, obj.config,dcts);
                        layer.close(index);
                    },
                    btn2: function (index) {
                        obj.config.where.type = "";
                        return;
                    }
                })
                break;
            case 'import' :

                $("#importHide").click();
                /*addModel();
                uploadInst.upload();
                removeModal();*/
                break;
            case 'download':
                var url = "/realestate-inquiry-ui/static/js/fcplcx/model/model.xls";
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
        //添加已选条目数
        var checkedData = layui.sessionData('checkedData');
        var arrcheck = Object.keys(checkedData);
        var total = arrcheck.length;
        $('#choosecount').html("已选"+total+"条");
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
    if (type == "checked") {
        $.each(checkedData, function (key, value) {
            data.push(value);
        })
    } else {
        data = d;
    }

    for (var i = 0; i < data.length; i++) {
        data[i].xh = i + 1;
        data[i].qszt = delHtmltag(formatQszt(data[i].qszt));
        data[i].bdcdyZtDTO = formatXzztStr(data[i].bdcdyZtDTO);
    }

    saveDetailLog("PLCX_EXPORT","批量查询-导出excel",{"DCNR":data});

    // 获取统计人和统计时间信息
    var tjr = "";
    var tjsj = new Date().Format("yyyy/MM/dd hh:mm:ss");
    var user = queryCurrentUser();
    if (isNotBlank(user)) {
        tjr = user.alias;
    }
    var totalInfo = '{"统计人":"' + tjr + '", "统计时间":"' + tjsj + '"}'
    // 设置Excel基本信息
    $("#fileName").val('批量信息');
    $("#sheetName").val('统计表');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#totalInfo").val(totalInfo);
    $("#expandServiceName").val('excelExpandLygFcplcxService');
    $("#data").val(JSON.stringify(data));
    $("#form").submit();
}

function exportAllExcel(d, obj,dcts) {
    var cols = obj.cols[0]
    var size = obj.page.count;
    if (dcts > 0) {
        size = dcts;
    }
    var url = obj.url+"?page=0&&size="+size;
    var paramData = obj.where;
    // paramData["type"] = "export";
    $.ajax({
        url: url,
        type:"post",//请求发送
        dataType: "json",
        data: paramData,
        success: function (data) {
            if(data.code == 0){//查询成功
                exportExcel(data.content,cols, "all");
            }
        }
    });
}

function formatXzztStr(data) {
    var value = '';
    if (data != undefined) {
        if (data.cf === true) {
            value += '查封 ';
        }
        if (data.ycf === true) {
            value += '预查封 ';
        }
        if (data.dya === true || data.zjgcdy === true) {
            value += '抵押 ';
        }
        if (data.ydya === true) {
            value += '预抵押 ';
        }
        if (data.yy === true) {
            value += '异议 ';
        }
        if (data.yg === true) {
            value += '预告 ';
        }
        if (data.dyi === true) {
            value += '地役 ';
        }
        if (data.sd === true) {
            value += '锁定 ';
        }
        if (data.jzq === true) {
            value += '居住 ';
        }
        if (value === '') {
            value += '正常';
        }
    }
    return value;
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
        ,url: '/realestate-inquiry-ui/plcx/import/fccx/excel' //上传接口
        ,done: function(res){
            if(res == null || res.length == 0){
                layer.alert('导入失败，请重试！', {title: '提示'});
            }
            if(res.code == 1) {
                layer.alert(res.msg, {title: '提示'});
                return;
            }
            for(var i=0;i<res.length;i++){
                if(res[i].qlrmc == null || res[i].qlrmc == ""){
                    layer.alert('导入数据缺失权利人名称！', {title: '提示'});
                    break;
                }
                if(res[i].zjh == null || res[i].zjh == ""){
                    layer.alert('导入数据缺失权利人证件号！', {title: '提示'});
                    break;
                }
                zjhs += res[i].zjh+",";
                qlrmcs += res[i].qlrmc+",";
            }
            zjhParams = zjhs.substring(0,zjhs.length-1);
            qlrParams = qlrmcs.substring(0,qlrmcs.length-1);
            $("#singleZjh").val(zjhParams);
            $("#singleQlr").val(qlrParams);
            //$("#search").click();
            removeModal();

        }
        ,error: function(e){
            removeModal();
            layer.alert('导入数据解析失败，请检查模板是否正确！', {title: '提示'});
        }
    });

}