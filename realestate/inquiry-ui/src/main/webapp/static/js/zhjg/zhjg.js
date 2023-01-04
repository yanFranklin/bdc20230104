/**
 * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @description 综合监管
 */
// 分页重置查询参数
var paramD =[];
layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'upload'], function () {
    var laydate = layui.laydate;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;

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
    })

    //判断查询起始时间与结束时间关系
    laydate.render({
        elem: '#startTime',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#endTime').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({ //结束时间
        elem: '#endTime',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date($('#startTime').val()).getTime();
            var endTime = new Date(value).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    cols = [
        {type: 'xmid', title: 'xmid', hide: true},
        //{type: 'checkbox', fixed: 'left'},
        {type: 'numbers', field: 'xh', title: '序号', width: 70, fixed: 'left'},
        {field: 'qlr',title: '权利人', width: 200},
        {field: 'ywr', title: '义务人', width: 200},
        {field: 'ssqx', minWidth: 100, title: '所属区县'},
        {field: 'zl', title: '坐落', width: 300},
        {field: 'qlxz', title: '权利性质', width: 130},
        {field: 'bdcdyh', minWidth: 300, title: '不动产单元号'},
        {field: 'zdzhyt', title: '宗地宗海用途', width: 160},
        {field: 'zdzhmj', title: '宗地宗海面积（平方米）', width: 160},
        {field: 'jzmj', minWidth: 160, title: '房屋建筑面积（平方米）'},
        {field: 'ghyt', title: '房屋用途', width: 120},
        {field: 'jyjg', minWidth: 160, title: '房屋价格（万元）'},
        {field: 'bdcqzh', minWidth: 180, title: '不动产权证号'},
        {field: 'fzsj', title: '发证时间', width: 180}
    ];
// 加载Table
    table.render({
        elem: '#bdcZhjgTable',
        toolbar: '#toolbar',
        title: '综合监管',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        even: true,
        cols: [cols],
        data: [],
        page: true,
        limits: [10, 50, 200, 500, 1000, 3000],
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
            //reverseTableCell(reverseList);
            setHeight();
        }
    });

    // 监听表单提交事件
    form.on('submit(search)', function (data) {
        if (data.field) {
            // if(data.field.startTime == "" || data.field.endTime == ""){
            //     layer.msg("请输入查询条件！");
            //     return false;
            // }

            addModel();
            // 重新请求
            // 获取查询参数
            var obj = data.field;
            paramD = obj;
            paramD["type"] = "";
            console.log(obj);
            table.reload("bdcZhjgTable", {
                url: '/realestate-inquiry-ui/zhjg/list'
                , where: paramD
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    removeModal();
                    setHeight();
                    //reverseTableCell(reverseList);
                    table.resize('bdcZhjgTable');
                }
            });
        } else {
            layer.msg("请输入查询条件！");
        }
        return false;
    });

    // 监听表格操作栏按钮
    table.on('toolbar(bdcZhjgTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'exportExcel':
                paramD["type"]="export";
                if(paramD.length == 0){
                    layer.alert("请先查询数据！", {title: '提示'});
                    return
                }
                exportExcel(obj.config);
                break;
        }
    });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(bdcZhjgTable)', function (obj) {
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

})


/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.24 14:45
 * @author zhuyong
 * @return
 */
function exportExcel(obj) {
     console.log(obj);
     console.log(obj.cols[0]);


    layer.confirm("是否导出全部数据？", {
        title: "提示",
        btn: ["确认", "取消"],
        btn1: function (index) {
            var cols = obj.cols[0]
            var url = obj.url;
            var paramData = obj.where;
            paramData["type"] = "export";

            $.ajax({
                url: url,
                dataType: "json",
                data: paramData,
                success: function (data) {
                    obj.where.type = "";
                    if(data.length > 0){//查询成功
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

                        for (var i = 0; i < data.length; i++) {
                            data[i].xh = i + 1;
                        }

                        // 设置Excel基本信息
                        $("#fileName").val('综合监管');
                        $("#sheetName").val('统计表');
                        $("#cellTitle").val(showColsTitle);
                        $("#cellWidth").val(showColsWidth);
                        $("#cellKey").val(showColsField);
                        $("#data").val(JSON.stringify(data));
                        $("#form").submit();
                    }
                }
            });

            layer.close(index);
        },
        btn2: function (index) {
            obj.where.type = "";
            return;
        }
    });
     return;
}
