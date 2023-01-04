/**
 * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
 * @description 婚姻信息
 */
/**
 * 身份证件种类 字典
 */
var sfzjzl = [{"DM": "1", "MC": "内地居民身份证"}, {"DM": "2", "MC": "香港居民身份证"},
    {"DM": "3", "MC": "澳门居民身份证"}, {"DM": "4", "MC": "台湾居民身份证"},
    {"DM": "5", "MC": "护照"}, {"DM": "6", "MC": "军官证"},
    {"DM": "7", "MC": "士兵证"}, {"DM": "8", "MC": "其他有效国籍旅行证件"}];

/**
 * 配偶身份类型 字典
 */
var posflx = [{"DM": "1", "MC": "内地居民"}, {"DM": "2", "MC": "香港居民"},
    {"DM": "3", "MC": "澳门居民"}, {"DM": "4", "MC": "台湾居民"},
    {"DM": "5", "MC": "华侨或出国人员"}, {"DM": "6", "MC": "外国人"}];

/**
 * 婚姻登记类型 字典
 */
var hydjlx = [{"DM": "IA", "MC": "结婚登记类"}, {"DM": "IB", "MC": "离婚登记类"},
    {"DM": "ID", "MC": "撤销婚姻类"}, {"DM": "ICA", "MC": "补发结婚登记证类"},
    {"DM": "ICB", "MC": "补发离婚登记证类"}];
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
        {field: 'RN', title: '序号', width: 80},
        {field: 'NAME', title: '姓名', minWidth: 100},
        {field: 'CERT_NUM', title: '身份证件号', minWidth: 120},
        {
            field: 'ID_TYPE', title: '身份类型', minWidth: 100,
            templet: function (d) {
                if (sfzjzl && d.ID_TYPE) {
                    for (var index in sfzjzl) {
                        if (sfzjzl[index].DM == d.ID_TYPE) {
                            return sfzjzl[index].MC;
                        }
                    }
                    return '';
                } else {
                    return '';
                }
            }
        },
        {field: 'CERT_NO', title: '离/结婚证字号', minWidth: 120},
        {field: 'SPOUSE_NAME', title: '配偶姓名', minWidth: 100},
        {
            field: 'SEX', title: '配偶性别',
            templet: function (d) {
                if (d.SEX == '01') {
                    return '男';
                } else {
                    return '女';
                }
            },
            minWidth: 100
        },
        {
            field: 'SPOUSE_CERT_TYPE', title: '配偶证件类型',
            templet: function (d) {
                if (sfzjzl && d.SPOUSE_CERT_TYPE) {
                    for (var index in sfzjzl) {
                        if (sfzjzl[index].DM == d.SPOUSE_CERT_TYPE) {
                            return sfzjzl[index].MC;
                        }
                    }
                    return '';
                } else {
                    return '';
                }
            },
            minWidth: 120
        },
        {field: 'SPOUSE_CERT_NUM', title: '配偶证件号', minWidth: 120},
        {
            field: 'SPOUSE_ID_TYPE', title: '配偶身份类型',
            templet: function (d) {
                if (posflx && d.SPOUSE_ID_TYPE) {
                    for (var index in posflx) {
                        if (posflx[index].DM == d.SPOUSE_ID_TYPE) {
                            return posflx[index].MC;
                        }
                    }
                    return '';
                } else {
                    return '';
                }
            },
            minWidth: 120
        },
        {field: 'OP_DATE', title: '登记日期', minWidth: 100},
        {
            field: 'OP_TYPE', title: '登记类型',
            templet: function (d) {
                if (hydjlx && d.OP_TYPE) {
                    for (var index in hydjlx) {
                        if (hydjlx[index].DM == d.OP_TYPE) {
                            return hydjlx[index].MC;
                        }
                    }
                    return '';
                } else {
                    return '';
                }
            },
            minWidth: 100
        },
        {field: 'REG_DETAIL', title: '常驻户口所在地', minWidth: 120},
        {field: 'DEPT_NAME', title: '登记机关', minWidth: 100}
    ];

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#hyxxlistTable',
        toolbar: '#toolbar',
        title: '婚姻信息列表',
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
    table.on('sort(hyxxlistTable)', function (obj) {
        table.reload('hyxxlistTable', {
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

        // 重新请求
        table.reload("hyxxlistTable", {
            url: BASE_URL + '/hyxx',
            where: {qlrzjh: qlrzjh}
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
        table.reload("hyxxlistTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };


});