/**
 * 海门档案移交单 移交单查看项目页面 处理JS
 */
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;

    // 设置字符过多，省略样式
    var reverseList = ['zl'];

    // 获取参数
    var yjdid = $.getUrlParam('yjdid');

    /**
     * 加载Table数据列表
     */
    table.render({
        url:  "/realestate-register-ui/rest/v1.0/yjd/xmxx?yjdid=" + yjdid,
        elem: '#yjdTable',
        id: 'yjdTable',
        data: [],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        limits: commonLimits,
        cols: [[
            {field: 'xh',       title: '序号',        width: 50, type: 'numbers'},
            {field: 'slbh',     title: '受理编号',     width: 130},
            {field: 'gzldymc',  title: '流程名称',     width: 200},
            {field: 'qlr',      title: '权利人',       width: 150},
            {field: 'bdcdyh',   title: '不动产单元号',  width: 250},
            {field: 'bdcqzh',   title: '不动产权证号',  width: 220},
            {field: 'zl',       title: '坐落',         miWidth: 300},
            {field: 'djsj',     title: '登记时间',      width: 160,
                templet: function (d) {
                    return format(d.djsj);
                }
            },
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
            var searchHeight = 131;
            setTableHeight(searchHeight);
            reverseTableCell(reverseList);
        }
    });
});