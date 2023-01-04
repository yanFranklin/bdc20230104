/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 打印数据源配置js
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response'}).use('response');

layui.use(['table', 'laytpl', 'laydate', 'layer', 'form','response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        response = layui.response,
        form = layui.form;
    var BASE_URL = getContextPath() + '/rest/v1.0';
    var dylx = $.getUrlParam('dylx');
    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#dysjZbTable',
        toolbar: '#toolbar',
        title: '默认意见配置列表',
        defaultToolbar: ['filter'],
        url: BASE_URL + "/dysjpz/dysjzb/page?dylx=" + dylx,
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'id', hide: true},
            {field: 'dyzbid', title: '子表唯一值', width: 200},
            {field: 'dylx', title: '打印类型', width: 200},
            {field: 'cs', title: '参数'},
            {field: 'dyzbsjy', title: '打印子表数据源'}
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    //头工具栏事件
    table.on('toolbar(dysjZbTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addBdcDysjZbPz(dylx);
                break;
            case 'edit':
                updateBdcDysjZbPz(checkStatus.data);
                break;
            case 'delete':
                deleteBdcDysjZbPz(checkStatus.data);
                break;
        }
        ;
    });

    /**
     * 监听排序事件
     */
    table.on('sort(dysjZbTable)', function (obj) {
        table.reload('dysjZbTable', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });
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
            obj[name] = value;
        });
        // 重新请求
        table.reload("dysjZbTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });

    /**
     * 重新加载数据
     */
    window.reload = function () {
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });
        table.reload("dysjZbTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

    /**
     * 新增
     */
    function addBdcDysjZbPz(dylx) {
        layer.open({
            type: 2,
            title: '新增打印子表数据配置',
            area: ['980px', '460px'],
            content: ["addOrEditBdcDysjZbPz.html?action=add&dylx=" + dylx, 'yes'],
            end: function () {
                table.reload('dysjZbTable');
            }
        });
    }

    /**
     * 编辑
     * @param data
     */
    function updateBdcDysjZbPz(data) {
        if (!data || data.length != 1) {
            layer.alert("请选择需要编辑的某一条记录！", {title: '提示'});
            return;
        }
        layer.open({
            type: 2,
            title: '编辑打印子表数据配置',
            area: ['980px', '460px'],
            content: ["addOrEditBdcDysjZbPz.html?action=edit&id="+data[0].id, 'yes'],
            success: function (layer, index) {
            },
            end: function () {
                table.reload('dysjZbTable');
            }
        });
    }

    /**
     * 删除
     * @param data
     */
    function deleteBdcDysjZbPz(data) {
        if (!data || data.length == 0) {
            layer.alert("请选择需要删除的记录！", {title: '提示'});
            return;
        }
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
                    url: getContextPath() + "/rest/v1.0/dysjpz/dysjzb",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data && $.isNumeric(data) && data > 0) {
                            layer.msg('删除成功！', {time: 2000});
                            reload();
                        } else {
                            layer.alert("删除失败，请重试！", {title: '提示'});
                        }
                    },
                    error: function (e) {
                        response.fail(e,'');
                    }
                });
                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
            }
        });

    }


    /**
     * 行双击编辑
     */
    table.on('rowDouble(dysjZbTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);
        updateBdcDysjZbPz(array);
    });

});