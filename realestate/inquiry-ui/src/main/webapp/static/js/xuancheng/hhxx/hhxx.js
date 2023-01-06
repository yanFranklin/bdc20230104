/**
 * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
 * @description 火化信息
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
        {field: 'SZXM', title: '逝者姓名'},
        {field: 'SZZJH', title: '逝者证件号'},
        {field: 'SZXB', title: '逝者性别'},
        {field: 'SZHJDZ', title: '逝者户籍地址'},
        {
            field: 'SWRQ', width: 180, title: '死亡日期',
            templet: function (d) {
                return formatRq(d.SWRQ);
            }
        },
        {
            field: 'HHKSSJ', width: 180, title: '火化日期',
            templet: function (d) {
                return format(d.HHKSSJ);
            }
        }
    ];

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#hhxxTable',
        toolbar: '#toolbar',
        title: '居民身份信息列表',
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



    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询内容
        var xm = $("#xm").val();
        var zjh = $("#zjh").val();
        // if (isNullOrEmpty(xm)) {
        //     warnMsg("请先输入查询条件！");
        //     return;
        // }
        // if (isNullOrEmpty(zjh)) {
        //     warnMsg("请先输入查询条件！");
        //     return;
        // }
        table.reload("hhxxTable", {
            url: BASE_URL + '/hhxxcx',
            where: {xm:xm, zjh:zjh}
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });

})
;