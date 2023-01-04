/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 打印数据源配置js
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});

layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'response', 'upload'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        upload = layui.upload,
        form = layui.form,
        response = layui.response;
    laydate = layui.laydate;
    var BASE_URL = getContextPath() + '/rest/v1.0';
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
    $(function () {
        importDypzYz();
    });
    /**
     * 加载Table数据列表 加载table数据列表
     */
    table.render({
        elem: '#dysjTable',
        toolbar: '#toolbar',
        title: '默认意见配置列表',
        defaultToolbar: ['filter'],
        url: BASE_URL + "/dysjpz/dysj/page",
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        limits: commonLimits,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'id', hide: true},
            {field: 'dylx', title: '打印类型', minWidth: 90},
            {field: 'cs', title: '参数'},
            {field: 'dyzd', title: '打印字段', minWidth: 100},
            {field: 'ytmc', title: '用途/名称', minWidth: 100},
            {
                field: 'pzrq', title: '配置日期', sort: true,
                templet: function (d) {
                    return format(d.pzrq);
                },
                minWidth: 150
            }
        ]],
        text: {
            none: '未查询到数据'
        },
        // autoSort: false,
        page: true,
        parseData: function (res) {
            if(isNotBlank(res.content)){
                //数据解密
                res.content.forEach(function (value,index){
                    if(!isNullOrEmpty(value.dysjy)){
                        res.content[index].dysjy = Base64.decode(value.dysjy);
                    }
                })
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content, //解析数据列表
                }
            }else{
                return {
                    code: 0, //解析接口状态
                    msg: "成功", //解析提示文本
                    count: 0, //解析数据长度
                    data: [] //解析数据列表
                }
            }
        },
        done: function () {
            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../../static/lib/bdcui/images/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });
    importDypzYz();

    //头工具栏事件
    table.on('toolbar(dysjTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addBdcDysjPz();
                break;
            case 'delete':
                deleteBdcDysjPz(checkStatus.data);
                break;
            case 'edit':
                updateBdcDysjPz(checkStatus.data);
                break;
            case 'export':
                exportDypz(checkStatus.data);
                break;
            case 'copy':
                copyBdcDysjPz(checkStatus.data);

        }
    });
    /**
     * 行双击编辑
     */
    table.on('rowDouble(dysjTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);
        updateBdcDysjPz(array);
    });

    /**
     * 监听排序事件
     */
    table.on('sort(dysjTable)', function (obj) {
        table.reload('dysjTable', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });
        importDypzYz();
    });
    //判断查询起始时间与结束时间关系
    laydate.render({
        elem: '#djqsrq',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#djjsrq').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({ //结束时间

        elem: '#djjsrq',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date($('#djqsrq').val()).getTime();
            var endTime = new Date(value).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询内容
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = replaceBracket(value);
        });
        // 重新请求
        table.reload("dysjTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        importDypzYz();
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        // 获取查询内容
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val("");
        });
        table.reload("dysjTable", {
            where: []
            , page: {
                curr: 1 //从第一页开始
            }
        });
        importDypzYz();
    });

    /**
     * 重新加载数据
     */
    window.reload = function () {
        table.reload("dysjTable", {
            where: []
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        importDypzYz();
    };

    /**
     * 新增
     */
    function addBdcDysjPz() {
        window.open("editDypz.html?");
    }

    /**
     * 编辑
     * @param data
     */
    function updateBdcDysjPz(data) {
        if (!data || data.length != 1) {
            layer.alert("请选择需要编辑的某一条记录！", {title: '提示'});
            return;
        }
        window.open("editDypz.html?edit=true&dylx=" + data[0].dylx);
    }

    /**
     * 删除
     * @param data
     */
    function deleteBdcDysjPz(data) {
        if (!data || data.length == 0) {
            layer.alert("请选择需要删除的记录！", {title: '提示'});
            return;
        }
        //对选择数据中SQL加密处理
        data.forEach(function (value){
            if(!isNullOrEmpty(value.dysjy))
                value.dysjy = Base64.encode(value.dysjy);
        })
        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            skin: 'bdc-small-tips',
            content: '确定要删除所选打印数据配置？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                $.ajax({
                    url: getContextPath()+ "/rest/v1.0/dysjpz/dysj",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data && $.isNumeric(data) && data > 0) {
                            successMsg("删除成功！")
                            $("#search").click();
                        } else {
                            errorsMsg("删除失败，请重试！")
                        }
                    },
                    error: function (e) {
                        response.fail(e, '');
                    }
                });
                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
            }
        });
        return false;// 避免刷新
    }

    /**
     * 导出文件
     * @param data
     */
    function exportDypz(data) {
        if (!data || data.length == 0) {
            layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要导出的记录');
            return;
        }
        //对选择数据中SQL加密处理
        data.forEach(function (value){
            if(!isNullOrEmpty(value.dysjy))
                value.dysjy = Base64.encode(value.dysjy);
        })
        $("#filedata").val(JSON.stringify(data));
        $("#dypzfile").submit();
    }

    function importDypzYz() {
        upload.render({
            elem: '#import', //绑定元素,
            url: getContextPath()+ '/rest/v1.0/dysjpz/importYz', //上传接口
            accept: 'file',
            acceptMime: 'file/txt',
            exts: 'txt',
            before: function (obj) {
                addModel();
            },

            done: function (res) {
                removeModal();
                console.log("res:" + res);
                if (res.code == 2) {
                    var imporezsmb = layer.open({
                        type: 1,
                        area: ['320px'],
                        title: '是否覆盖',
                        content: '已存在打印类型为:' + res.message + '是否导入并覆盖',
                        btn: ['确定', '取消'],
                        skin: 'bdc-small-tips',
                        btnAlign: 'c',
                        yes: function () {
                            importAfert(res.savedata);
                        }
                    });
                } else {
                    importAfert(res.savedata);
                }
                //上传完毕回调
            }
            , error: function () {
                //请求异常回调
                removeModal();
                layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">导入失败，请选择正确文件！');
            }
        });
    }


    function importAfert(filedata) {
        $.ajax({
            url: getContextPath() + "/rest/v1.0/dysjpz/import",
            type: "POST",
            data: JSON.stringify(filedata),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data.code == 1) {
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">导入成功,即将刷新页面');

                    setTimeout(function () {
                        location.reload();
                    }, 500);
                } else {
                    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">导入失败，请选择正确文件！');
                }

            },
            error: function ($xhr, textStatus, errorThrown) {
                removeModal();
                layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">导入失败，请选择正确文件！');

            }
        })
    }

    function copyBdcDysjPz(data) {
        if (!data || data.length != 1) {
            layer.alert("请选择需要复制的某一条记录！", {title: '提示'});
            return;
        }
        window.open("editDypz.html?edit=true&dylx=" + data[0].dylx + "&copy=true");
    }

});