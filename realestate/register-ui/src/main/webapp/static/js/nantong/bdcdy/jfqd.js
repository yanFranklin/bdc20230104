//统一每页条数的选择项
var commonLimits = [10, 20, 50, 100, 200, 500];
zdList;
layui.use(['jquery', 'table', 'form', 'layer'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        layer = layui.layer;

    $(function () {
        // 获取参数（工作流定义 id）
        var processInsId = $.getUrlParam('processInsId');

        var url = getContextPath() +"/rest/v1.0/bdcdy/jfqd?gzlslid="+ processInsId;
        var tableId = '#listTable';
        renderListTable(url, tableId);

        /**
         * 渲染表格
         * @param url 地址
         * @param tableId 表格 id
         */
        function renderListTable(url, tableId) {
            addModel();
            table.render({
                elem: tableId,
                url: url,
                title: '解封清单',
                method: 'GET',
                even: true,
                request: {
                    limitName: 'size' //每页数据量的参数名，默认：limit
                },
                defaultToolbar: ['filter'],
                limits: commonLimits,
                cols: [[
                    {
                        field: 'bdcdyh', title: '不动产单元号', minWidth: 270
                        , templet: function (obj) {
                            if (!isNullOrEmpty(obj.bdcdyh)) {
                                return '<span>'+ formatBdcdyh(obj.bdcdyh) + '</span>';
                            } else {
                                return '<span></span>';
                            }
                        }
                    },
                    {field: 'zl', title: '坐落', minWidth: 240},
                    {field: 'cfwh', title: '查封文号', minWidth: 200},
                    {field: 'cfjg', title: '查封机关', minWidth: 150},
                    {field: 'cflxMc', title: '查封类型', minWidth: 100},
                    {field: 'cfwj', title: '查封文件', minWidth: 150},
                    {
                        field: 'cfqssj', title: '查封起始时间', minWidth: 150,
                        templet: function (d) {
                            if (d.cfqssj) {
                                return Format(format(d.cfqssj), 'yyyy年MM月dd日');
                            } else {
                                return '';
                            }
                        }
                    },
                    {
                        field: 'cfjssj', title: '查封结束时间', minWidth: 150,
                        templet: function (d) {
                            if (d.cfjssj) {
                                return Format(format(d.cfjssj), 'yyyy年MM月dd日');
                            } else {
                                return '';
                            }
                        }
                    },
                    {field: 'cffw', title: '查封范围', minWidth: 200}
                ]],
                parseData: function (res) { //res 即为原始返回的数据
                    return {
                        code: res.code, //解析接口状态
                        msg: res.msg, //解析提示文本
                        count: res.totalElements, //解析数据长度
                        data: res.content //解析数据列表
                    }
                },
                page: true,
                done: function () {
                    removeModel();
                    // $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                    if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                    }
                }
            });
        }
    });


});

/**
 * 格式化 bdcdyh
 * @param bdcdyh 不动产单元号
 * @return {string} 返回格式化的不动产单元号字符串
 */
function formatBdcdyh(bdcdyh) {
    var result;
    if (!isNullOrEmpty(bdcdyh) && bdcdyh.length == 28) {
        result = bdcdyh.substring(0, 6) + ' '
            + bdcdyh.substring(6, 12) + ' '
            + bdcdyh.substring(12, 19) + ' '
            + bdcdyh.substring(19, 28);
    } else {
        result = bdcdyh;
    }
    return result;
}