/**
 * @author "<a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2022/12/19
 * @description 立案文书信息
 */
layui.use(['table', 'layer', 'jquery'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;

    var processInsId = $.getUrlParam('processInsId');
    if(isNullOrEmpty(processInsId)) {
        processInsId = $.getUrlParam('gzlslid');
    }

    var jkname = $.getUrlParam("jkname");

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#lawsTable',
        toolbar: '#toolbar',
        title: '立案文书信息列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        data: [],
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [[
            {field: 'ah', title: '案号', minWidth: 100},
            {field: 'dsr', title: '当事人', minWidth: 120},
            {field: 'ay', title: '案由', minWidth: 150},
            {field: 'slfy', title: '审理法院', minWidth: 80},
            {field: 'cbbm', title: '承办部门', minWidth: 120},
            {field: 'cbr', title: '承办人', minWidth: 100},
            {field: 'sjy', title: '书记员', minWidth: 100},
            {field: 'larq', title: '立案日期', minWidth: 100},
            {field: 'sar', title: '收案人', minWidth: 100},
            {field: 'labd', title: '立案标的', minWidth: 100},
            {field: 'sycx', title: '适用程序', minWidth: 100},
            {field: 'ajly', title: '案件来源', minWidth: 100},
            {field: 'hyr', title: '核验人', minWidth: 100},
            {field: 'hysj', title: '核验时间', minWidth: 120},
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

        table.reload("lawsTable", {
            url: "/realestate-inquiry-ui/rest/v1.0/sjxxgx/fyxx/laws"
            , where: obj
            , page: {
                curr: 1,  //重新从第 1 页开始
                limits: [10, 50, 100, 200, 500]
            }
            , done: function (res, curr, count) {
                setHeight();
                dealCxjgxx(res && res === "error" ? "fail" : "success", jkname);
            }
        });
    });

    //监听工具条
    table.on('tool(lawsTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'xx') {
            layer.open({
                title: '立案文书信息',
                type: 2,
                area: ['960px', '500px'],
                content: '/realestate-inquiry-ui/xuancheng/laws/lawshy.html?processInsId=' + processInsId,
                success: function (layero, index) {
                    var iframe = window['layui-layer-iframe' + index];
                    iframe.setData(data);
                },
            });
        }
    });
})