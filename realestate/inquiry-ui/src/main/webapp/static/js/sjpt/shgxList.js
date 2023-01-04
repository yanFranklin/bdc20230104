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

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/sjpt';

    cols = [
        {field: 'RN', title: '序号'},
        {
            field: 'SORG_TYPE', title: '组织类型',
            templet: function (d) {
                if (d.SORG_TYPE == 'S') {
                    return '社会团体';
                } else if (d.SORG_TYPE == 'M') {
                    return '民办非企业';
                } else if (d.SORG_TYPE == 'J') {
                    return '基金会';
                }
            }
        },
        {
            field: 'SORG_KIND', title: '社会组织类型',
            templet: function (d) {
                if (d.SORG_TYPE == 'S') {
                    switch (d.SORG_KIND) {
                        case '1':
                            return '学术性';
                        case '2':
                            return '行业性';
                        case '3':
                            return '专业性';
                        case '4':
                            return '联合性';
                        default:
                            return '';
                    }
                } else if (d.SORG_TYPE == 'M') {
                    switch (d.SORG_KIND) {
                        case '1':
                            return '法人';
                        case '2':
                            return '合伙';
                        case '3':
                            return '个体';
                        default:
                            return '';
                    }
                } else if (d.SORG_TYPE == 'J') {
                    switch (d.SORG_KIND) {
                        case '1':
                            return '公募';
                        case '2':
                            return '非公募';
                        default:
                            return '';
                    }
                }
            }
        },
        {field: 'CN_NAME', title: '社会组织名称'},
        {field: 'SORG_PHONE', title: '联系电话'},
        {field: 'RESIDENCE', title: '地址'},
        {field: 'REG_MON', title: '注册资金(万元)'}
    ];

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#shgxlistTable',
        toolbar: '#toolbar',
        title: '社会组织列表',
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
    table.on('sort(shgxlistTable)', function (obj) {
        table.reload('shgxlistTable', {
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
        var zzmc = $("#zzmc").val();
        if (isNullOrEmpty(zzmc)) {
            warnMsg("请先输入查询条件！");
            return;
        }

        // 重新请求
        table.reload("shgxlistTable", {
            url: BASE_URL + '/shgx',
            where: {zzmc: zzmc}
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
        table.reload("shgxlistTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };


});