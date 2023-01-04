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
        {field: 'mC', title: '单位名称'},
        {field: 'tYSHXYDM', title: '统一社会信用代码'},
        {field: 'dJGLJG', title: '登记管理机构'},
        {field: 'fDDBR', title: '法定代表人'},
        {field: 'jBDW', title: '举办单位名称'},
        {field: 'jFLY', title: '经费来源'},
        {field: 'kBZJ', title: '开办资金'},
        {field: 'zSYXQSRQ', title: '证书有效起始日期'},
        {field: 'zSYXJSRQ', title: '证书有效截止日期'},
        {field: 'zZHYWFW', title: '宗旨和业务范围'},
        {field: 'zS', title: '住所'}
    ];

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#dwfrcxTable',
        toolbar: '#toolbar',
        title: '单位法人信息列表',
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
    table.on('sort(dwfrcxTable)', function (obj) {
        table.reload('dwfrcxTable', {
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
        var MC = $("#MC").val();
        var TYSHXYDM = $("#TYSHXYDM").val();
        if (isNullOrEmpty(MC) && isNullOrEmpty(TYSHXYDM)) {
            warnMsg("请输入名称！");
            return;
        }
        if (isNullOrEmpty(MC) && isNullOrEmpty(TYSHXYDM)) {
            warnMsg("请输入社会信用代码！");
            return;
        }

        // 重新请求
        table.reload("dwfrcxTable", {
            url: BASE_URL + '/dwfrcx',
            where: {MC:MC,TYSHXYDM:TYSHXYDM}
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
        table.reload("dwfrcxTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };


});