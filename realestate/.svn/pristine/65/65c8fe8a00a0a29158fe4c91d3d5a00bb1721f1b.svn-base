layui.use(['table', 'layer', 'jquery'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;

    var processInsId = $.getUrlParam('processInsId');

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#lsdclTable',
        toolbar: '#toolbar',
        title: '律师调查令信息列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        data: [],
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [[
            {field: 'lsxm', title: '律师姓名', minWidth: 100},
            {field: 'lsxb', title: '律师性别', minWidth: 80},
            {field: 'lszyjg', title: '律师执业机构', minWidth: 150},
            {field: 'lszyzh', title: '律师执业证号', minWidth: 120},
            {field: 'lsdclbh', title: '律师调查令编号', minWidth: 130},
            {field: 'dcsx', title: '调查事项', minWidth: 200},
            {field: 'dclyxjzrq', title: '调查令有效截止日期', minWidth: 160},
            {field: 'fymc', title: '法院名称', minWidth: 160},
            {field: 'hyr', title: '核验人', minWidth: 100},
            {field: 'hysj', title: '核验时间', minWidth: 130},
            {title: '查看', fixed: 'right', toolbar: '#barDemo', width: 100}
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        limits: [10, 50, 100, 200, 500],
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            setHeight();
        }
    });

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询参数
        var obj = {};
        var isNull = true;
        $(".search").each(function (i) {
            var name = $(this).attr('name');
            var value = $(this).val();
            if (!isNullOrEmpty(value)) {
                value = value.replace(/\s*/g, "");
                isNull = false;
            }
            obj[name] = value;
        });
        if (isNull) {
            warnMsg("请先输入查询条件！");
            return;
        }

        table.reload("lsdclTable", {
            url: "/realestate-inquiry-ui/rest/v1.0/sjxxgx/fyxx/xclsdcl"
            , where: obj
            , page: {
                curr: 1,  //重新从第 1 页开始
                limits: [10, 50, 100, 200, 500]
            }
            , done: function (res, curr, count) {
                setHeight();
            }
        });
    });

    //监听工具条
    table.on('tool(lsdclTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'xx') {
            layer.open({
                title: '律师调查令查询核验',
                type: 2,
                area: ['960px', '650px'],
                content: '/realestate-inquiry-ui/xuancheng/lsdcl/lsdclcxhy.html?processInsId=' + processInsId,
                success: function (layero, index) {
                    var iframe = window['layui-layer-iframe' + index];
                    iframe.setData(data);
                },
            });
        }
    });
})