layui.use(['jquery', 'table', 'layer'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;
    $(function () {

        var xmid = $.getUrlParam('xmid');

        /**
         * 渲染补录信息表格
         * @type {string}
         */
        var url = "/realestate-register-ui/rest/v1.0/blxx/log?xmid=" + xmid;
        var tableId = '#blTable';
        var toolbar = '#toolbar';
        renderblTable(url, tableId, toolbar);

        /**
         * 监听工具栏事件，工具栏进入到编辑页面可以对于数据进行操作
         */
        table.on('tool(blTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            var data = obj.data; //获得当前行数据
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if (layEvent === 'back') { //回溯
                layer.confirm('确定回溯该数据吗？', function (index) {
                    layer.close(index);
                    if (isNullOrEmpty(data.id)) {
                        layer.msg("数据异常");
                    }
                    addModel();
                    $.ajax({
                        url: "/realestate-register-ui/rest/v1.0/blxx/log/" + data.id,
                        type: 'GET',
                        async: true,
                        success: function () {
                            removeModel();
                            layer.msg("回溯成功");
                        },
                        error: function (xhr, status, error) {
                            removeModel();
                            delAjaxErrorMsg(xhr);
                        }
                    });
                });
            }
        });
    });

    /**
     * 渲染补录数据
     * @param url 地址
     * @param tableId 表格 id
     * @param toolbar 表格工具栏
     */
    function renderblTable(url, tableId, toolbar) {
        table.render({
            elem: tableId,
            url: url,
            toolbar: toolbar,
            title: '补录任务表格',
            method: 'GET',
            even: true,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            defaultToolbar: ['filter'],
            cols: [[
                // {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'id', minWidth: 100, title: 'id', hide: true},
                {field: 'xmid', minWidth: 100, title: 'xmid'},
                {field: 'change', minWidth: 300, title: '修改内容'},
                {field: 'principal', minWidth: 100, title: '补录人'},
                {field: 'time', title: '补录时间', width: 200, sort: true},
                {fixed: 'right', title: '操作', toolbar: '#editbar', width: 180}
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                res.content.forEach(function (v) {
                    if (v.time) {
                        var blsj = new Date(v.time);
                        v.time = blsj.toLocaleString();
                    }
                });
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            page: true,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        });
    }
});