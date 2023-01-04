/**
 * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
 * @description 社会组织
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
layui.use(['table', 'layer', 'jquery', 'response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        response = layui.response;

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/sjxxgx';
    cols = [
        {
            field: 'xh', type: 'numbers', title: '序号',
            templet: function (d) {
                return d.LAY_TABLE_INDEX + 1;
            }
        },
        {field: 'qYMC', title: '企业名称'},
        {field: 'sHTYXYDM', title: '统一社会信用代码'},
        {field: 'qYZT', title: '企业状态'},
        {field: 'zCH', title: '注册号'},
        {field: 'zCZB', title: '注册资本'},
        {field: 'qYLXZL', title: '企业类型种类'},
        {field: 'cSDGJDQ', title: '出生地国籍地区'},
        {field: 'cJRQ', title: '创建日期'},
        {field: 'dJBZ', title: '注册资本币种'},
        {field: 'dJJG', title: '登记机构'},
        {field: 'fDDBR', title: '法定代表人'},
        {field: 'hZSJ', title: '核准日期'},
        {field: 'jYDZ', title: '经营地址'},
        {field: 'jYFW', title: '经营范围'},
        {field: 'jYQXQ', title: '经营期限起'},
        {field: 'jYQXZ', title: '经营期限止'}
    ];

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#qyxxcxTable',
        toolbar: '#toolbar',
        title: '企业信息列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        data: [],
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [cols],
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

    $('.bdc-table-box').on('mouseenter', 'td', function () {
        $('.layui-table-grid-down').on('click', function () {
            setTimeout(function () {
                $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
            }, 20);
        });
    });

    /**
     * 监听排序事件
     */
    table.on('sort(qyxxcxTable)', function (obj) {
        table.reload('qyxxcxTable', {
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
        var QYMC = $("#QYMC").val();
        var SHTYXYDM = $("#SHTYXYDM").val();
        if (isNullOrEmpty(QYMC)) {
            warnMsg("请输入企业名称！");
            return;
        }
        if (isNullOrEmpty(SHTYXYDM)) {
            warnMsg("请输入社会信用代码！");
            return;
        }

        // 重新请求
        table.reload("qyxxcxTable", {
            url: BASE_URL + '/qyxxcx',
            where: {QYMC:QYMC,SHTYXYDM:SHTYXYDM}
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
        table.reload("qyxxcxTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };


});