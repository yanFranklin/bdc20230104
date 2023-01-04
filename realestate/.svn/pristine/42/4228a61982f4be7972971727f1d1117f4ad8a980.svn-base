/**
 * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
 * @description 婚姻信息
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
layui.use(['table', 'layer', 'jquery', 'response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        response = layui.response;

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/sjpt';

    cols = [
        {field: 'xm', title: '姓名'},
        {field: 'sfzh', title: '身份证号'},
        {field: 'xb', title: '性别'},
        {field: 'mz', title: '民族'},
        {
            field: 'csrq', title: '出生日期',
            templet: function (d) {
                if (!isNullOrEmpty(d.csrq)) {
                    return d.csrq.substr(0, 4) + '年' + d.csrq.substr(4, 2) + '月' + d.csrq.substr(6, 2) + '日';
                } else {
                    return '';
                }
            }
        },
        {field: 'hjdxqpcsmc', title: '户籍地辖区派出所名称'}
    ];

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#jmsflistTable',
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
    table.on('sort(jmsflistTable)', function (obj) {
        table.reload('jmsflistTable', {
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
        var qlrzjh = $("#qlrzjh").val();
        if (isNullOrEmpty(qlrzjh)) {
            warnMsg("请先输入查询条件！");
            return;
        }
        $.ajax({
            url: BASE_URL + '/jmsf?qlrzjh=' + qlrzjh,
            type: 'GET',
            dataType: 'json',
            async: false,
            success: function (data) {
                if (data) {
                    // 只有首次
                    if (data.head.code == '0000') {
                        addModel();
                        // 调用 3 次
                        for (let i = 0; i < 3; i++) {
                            // 公安的接口要求，先调用查询，过 1 分钟，再调用查询反馈接口才能获取到数据
                            setTimeout(function () {
                                // 重新请求
                                table.reload("jmsflistTable", {
                                    url: BASE_URL + '/jmsfsqfk',
                                    where: {qlrzjh: qlrzjh}
                                    , page: {
                                        curr: 1  //重新从第 1 页开始
                                    }
                                });
                                removeModal();
                            }, i*60000);
                        }
                    } else {
                        warnMsg(data.head.msg);
                    }
                }
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
        table.reload("jmsflistTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };


})
;