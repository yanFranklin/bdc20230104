layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var form = layui.form,
        $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;

    var yzbs = $.getUrlParam("yzbs");

    addModel();
    table.render({
        elem: '#gzyzxqTable',
        url:  '/realestate-inquiry-ui/log/list/gzyz?yzbs=' + yzbs,
        title: '规则验证详情列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        even: true,
        cols: [[
            {type: 'numbers', fixed:'left', title: '序号', width: 50},
            {field: 'rzid', title: '日志ID', hide: true},
            {field: 'yzrzh', title: '验证人账号', align: 'center', width: 80},
            {field: 'gzmc', title: '规则名称', align: 'center', style: 'text-align:left'},
            {field: 'yzcs', title: '验证参数', align: 'center', style: 'text-align:left'},
            {field: 'yzjg', title: '验证结果', align: 'center', width: 200},
            {field: 'sftg', title: '是否通过', align: 'center', width: 100, templet: function(d) {
                    if (d.sftg == 0) {
                        return '<span class="" style="color:red;">不通过</span>';
                    }else if (d.sftg == 2) {
                        return '<span class="" style="color:red;">异常</span>';
                    }else if (d.sftg == 1) {
                        return '<span class="" style="color:limegreen;">通过</span>';
                    }
                    return d.sftg;
                }
            },
            {field: 'yzsj', title: '验证日期', align: 'center', width: 180,
                templet: function (d) {
                    return format(d.yzsj);
                }
            },
        ]],
        data: [],
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
            removeModal();
            setTableHeight();
        }
    });

    // 设置列表高度
    function setTableHeight(searchHeight) {
        $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

        if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
            $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
        }else {
            $('.layui-table-body').height($('.bdc-table-box').height() - 80);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 97);
        }
    }

});
