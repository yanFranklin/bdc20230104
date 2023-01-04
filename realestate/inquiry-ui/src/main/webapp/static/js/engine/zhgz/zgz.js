layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var form = layui.form,
        $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;

    var zhid = $.getUrlParam("zhid");

    table.render({
        elem: '#zgzTable',
        url: '/realestate-inquiry-ui/bdcZhGz/zgz?zhid=' + zhid,
        title: '子规则列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        even: true,
        cols: [[
            {type: 'numbers', fixed:'left', title: '序号', width: 50 },
            {field: 'gzid', title: '规则id', hide: true},
            {field: 'yxj', title: '验证级别', align: 'center', width: 90,
                templet: function (d) {
                    return formatYxj(d.yxj);
                }
            },
            {field: 'gzmc', sort: true, title: '规则名称', align: 'center', width: 300, style: 'text-align:left'},
            {field: 'ytsm', title: '用途说明', align: 'center', style: 'text-align:left'},
            {field: 'pzr', sort: true, title: '配置人', align: 'center', width: 100},
            {field: 'pzrq', sort: true, title: '日期', align: 'center', width: 100,
                templet: function (d) {
                    return format(d.pzrq);
                }
            },
            {title: '查看', fixed: 'right', toolbar: '#barDemo', width: 80}
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

    //监听工具条
    table.on('tool(zgzTable)', function (obj) {
        var data = obj.data;
        if (isNullOrEmpty(data.gzid)) {
            warnMsg("未查询到子规则信息，无法查看！");
            return;
        }

        if (obj.event === 'zgz') {
            window.open("/realestate-inquiry-ui/view/engine/zgz/editBdcZgz.html?gzid=" + data.gzid);
        }
    });

    function formatYxj(yxj){
        if(isNullOrEmpty(yxj)){
            return "";
        } else if(yxj == 1) {
            return '<span class="" style="color:blue;">忽略</span>';
        } else if(yxj == 3) {
            return '<span class="" style="color:red;">警告</span>';
        } else if(yxj == 4) {
            return '<span class="" style="color:limegreen;">例外</span>';
        }
    }
});
