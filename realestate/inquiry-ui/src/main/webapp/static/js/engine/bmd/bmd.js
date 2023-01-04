/**
 * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
 * @description 白名单js
 */
var czryid = "${czryid}";
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;
    window.layer = layui.layer;
    var BASE_URL = '/realestate-inquiry-ui';

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#bmdTable',
        toolbar: '#toolbar',
        title: '白名单人员列表',
        defaultToolbar: [],
        url: BASE_URL + "/bdcBmd/listBmdByPageJson",
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {title: '序号', type: 'numbers', fixed: 'left', width: 80},
            {field: 'czryid', title: 'czryid', hide: true},
            {field: 'czrymm', title: 'czrymm', hide: true},
            {field: 'czry', title: '人员名称'}
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
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    //头工具栏事件
    table.on('toolbar(bmdTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addBmdry();
                break;
            case 'edit':
                editBmdry(checkStatus.data);
                break;
            case 'delete':
                deleteBmdry(checkStatus.data);
                break;
        }
        ;
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(bmdTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);
        editBmdry(array);
    });

    /**
     * 监听排序事件
     */
    table.on('sort(bmdTable)', function (obj) {
        table.reload('bmdTable', {
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
        var count = 0;
        $(".search").each(function (i) {
            var value = $(this).val();
            if (!isNullOrEmpty(value)) {
                count += 1;
            }
            var name = $(this).attr('name');
            obj[name] = value;
        });

        if (0 == count) {
            layer.alert("请输入查询条件！", {
                title: '提示'
            });
            return false;
        }

        //获取创建人id
        var czry = $('#czry').val();


        // 重新请求
        table.reload("bmdTable", {
            where: obj
            , page: {
                curr: 1,  //重新从第 1 页开始
                limits: [10, 50, 100, 200, 500]
            }
        });
    });

    /**
     * 重置
     */
    $('#reset').on('click', function () {
        reload();
    });

    /**
     * 重新加载数据
     */
    window.reload = function () {
        table.reload("bmdTable", {
            where: []
            , page: {
                curr: 1, //重新从第 1 页开始
                limits: [10, 50, 100, 200, 500]
            }
        });
    };

    /**
     * 新增
     */
    function addBmdry() {
        layer.open({
            title: '新增人员信息',
            type: 2,
            area: ['430px','200px'],
            content: 'addOrEditBmd.html?action=add',
            end: function () {
                table.reload('bmdTable');
            }
        });
    }

    /**
     * 编辑
     * @param data
     */
    function editBmdry(data) {
        if (!data || data.length != 1) {
            layer.alert("<div style='text-align: center'>请选择需要编辑的某一条记录！</div>", {title: '提示'});
            return;
        }
        czryid = data[0].id;
        layer.open({
            type: 2,
            title: '编辑人员信息',
            anim: -1,
            shadeClose: true,
            shade: false,

            area: ['430px', '200px'],
            offset: 'auto',
            content: ["addOrEditBmd.html?action=edit", 'yes'],
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data[0]);
            },
            end: function () {
                table.reload('bmdTable');
            }
        });
    }

    /**
     * 删除
     * @param data
     */
    function deleteBmdry(data) {
        if (!data || data.length == 0) {
            layer.alert("<div style='text-align: center'>请选择需要删除的记录！</div>", {title: '提示'});
            return;
        }

        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '确定要删除所选人员？',
            btn: ['确定','取消'],
            btnAlign: 'c',
            yes: function(){
                $.ajax({
                    url: BASE_URL + "/bdcBmd",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data && $.isNumeric(data) && data > 0) {
                            layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">删除成功');
                            reload();
                        } else {
                            layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">删除失败，请重试');
                        }
                    },
                    error: function () {
                        layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">删除失败，请重试');
                    }
                });

                layer.close(deleteIndex);
            },
            btn2: function(){
                //取消
            }
        });
    }
});