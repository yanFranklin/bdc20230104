$(function () {
    layui.use(['form', 'table', 'jquery'], function () {
        var table = layui.table;
        //不动产单元号的表头
        var unitTableTitle = [
            {field: 'sfxxid', hide:true},
            {field: 'jfrxm', title: '缴费人姓名', width: "25%"},
            {field: 'sfrxm', title: '收费人姓名', width: "25%"},
            {field: 'jfsewmurl', title: '缴款识别码', width: "10%"},
            {field: 'hj', title: '合计(单位：元)', width: "25%"},
            {field: 'cz', title: '操作', width: "15%", templet: '#bdcdyczTpl', fixed: "right"}
        ];
        var url = getContextPath() + '/sf/listFskpBypage';

        var tableConfig = {
            id: 'sfxxid',
            url: url,
            where: {gzlslid: processInsId},
            defaultToolbar: ['filter'],
            cols: [unitTableTitle]
        };

        //加载表格
        loadDataTablbeByUrl('#sfxxTable', tableConfig);
        //监听行工具事件
        table.on('tool(sfxxTable)', function (obj) {
            var data = obj.data;
            window.open("/realestate-accept-ui/view/sfxx/scan.html?processInsId=" + processInsId+"&jfsewmurl="+data.jfsewmurl)
        });

    });

});
