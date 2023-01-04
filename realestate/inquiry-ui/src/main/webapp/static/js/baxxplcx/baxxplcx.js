/**
 * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @description 备案信息批量查询
 */

var zjhs = "";
var qlrmcs = "";
//var zdyxh = "";
var uploadInst = null;
var upload = null;
layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'upload'], function () {
    var laydate = layui.laydate;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    upload = layui.upload;

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
        //zdyxh = "";
    })

    cols = [

        {type: 'bdcdyh', title: 'bdcdyh', hide: true},
        {type: 'checkbox', fixed: 'left'},
        {field: 'zdyxh',  title: '序号', width: 70, fixed: 'left'},
        {field: 'msr',title: '姓名', width: 200},
        {field: 'gmrzjhm', title: '身份证号', width: 360},
        {field: 'jzmj', title: '建筑面积', minWidth: 150},
        {field: 'xmldfh', title: '坐落',minWidth: 300},
        {field: 'fwyt', title: '规划用途', width: 160},
        {field: 'basj', title: '备案时间', width: 160}
    ];
    // 加载Table
    table.render({
        elem: '#bdcPlcxTable',
        toolbar: '#toolbar',
        title: '备案信息',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        even: true,
        cols: [cols],
        data: [],
        page: true,
        limits: [10, 50, 100, 200, 500,2000],
        parseData: function (res) {
            // 设置选中行
            if (res.content && res.content.length > 0) {
                var checkedData = layui.sessionData('checkedData');
                res.content.forEach(function (v) {
                    $.each(checkedData, function (key, value) {
                        if (key == v.bdcdyh) {
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
        }
    });

    // 监听表单提交事件
    form.on('submit(search)', function (data) {
        if (data.field) {
            if(data.field.singleMsr == "" ){
                layer.msg("请输入姓名！");
                return false;
            }

            if(data.field.singleGmrzjhm == "" ){
                layer.msg("请输入证件号码！");
                return false;
            }
            //data.field.zdyxhs = $("#zdyxhs").val();
            addModel();
            // 重新请求
            // 获取查询参数
            var obj = data.field;

            console.log(obj);
            obj.export = "";
            //批量查询修改为post请求，避免get请求头过大问题
            table.reload("bdcPlcxTable", {
                url: '/realestate-inquiry-ui/plcx/baxx/list'
                , method: 'post'
                , where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    removeModal();
                    setHeight();
                    table.resize('bdcPlcxTable');
                    //saveDetailLog("PLCX","批量查询", obj);
                    //initUploadInst();
                    zjhs = "";
                    qlrmcs = "";
                    //zdyxh = "";
                    //$("#zdyxhs").val("");
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
                exportAllExcel(checkStatus.data, obj.config);
                break;
            case 'import' :
                $("#importHide").click();
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
    });

    initUploadInst();

})


/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.24 14:45
 * @author zhuyong
 * @return
 */
function exportExcel(d, cols) {

    var checkedData = d;

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

    var data = d


    //saveDetailLog("BAXXPLCX_EXPORT","备案信息批量查询-导出excel",{"DCNR":data});
    // 设置Excel基本信息
    $("#fileName").val('批量信息');
    $("#sheetName").val('统计表');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#data").val(JSON.stringify(data));
    $("#form").submit();
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
                    layer.alert('导入数据缺失姓名！', {title: '提示'});
                    break;
                }
                if(res[i].zjh == null || res[i].zjh == ""){
                    layer.alert('导入数据缺失证件号！', {title: '提示'});
                    break;
                }
                zjhs += res[i].zjh+",";
                qlrmcs += res[i].qlrmc+",";
                //zdyxh += res[i].xh+",";
            }
            var zjhParams = zjhs.substring(0,zjhs.length-1);
            var qlrParams = qlrmcs.substring(0,qlrmcs.length-1);
            //var zdyxhs = zdyxh.substring(0,zdyxh.length-1);
            $("#singleGmrzjhm").val(zjhParams);
            $("#singleMsr").val(qlrParams);
            //$("#zdyxhs").val(zdyxhs);

            $("#search").click();
            //removeModal();

        }
        ,error: function(e){
            removeModal();
            layer.alert('导入数据解析失败，请检查模板是否正确！', {title: '提示'});
        }
    });

}


/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.25 14:45
 * @author chenyucheng
 * @return
 */
function exportAllExcel(data, obj){
    layer.confirm("是否导出全部数据？", {
        title: "提示",
        btn: ["确认", "取消"],
        btn1: function (index) {
            addModel()
            var cols = obj.cols[0]
            var url = obj.url;
            var paramData = obj.where;
            paramData["export"] = "export";

            $.ajax({
                url: url,
                type: 'post',
                dataType: "json",
                data: paramData,
                success: function (data) {
                    removeModal();
                    obj.where.export = "";
                    if(data.length > 0){//查询成功
                        exportExcel(data,cols);
                    }
                }
            });

            layer.close(index);
        },
        btn2: function (index) {
            obj.where.export = "";
            removeModal()
            return;
        }
    });

}