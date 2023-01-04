$(function () {
    var gzlslid = getQueryString("gzlslid");
    var qjgldm = getQueryString("qjgldm");

    layui.use(['form', 'table', 'jquery'], function () {
        var table = layui.table;
        //不动产单元号的表头
        var unitTableTitle = [
            {field: 'lszd', title: '隶属宗地'},
            {field: 'fwmc', title: '房屋名称'},
            {field: 'zldz', title: '坐落'},
            {field: 'cz', title: '操作', width: 100, templet: '#bdcdyczTpl', fixed: "right"}
        ];
        var url = getContextPath() + '/slym/lpb/listFwLjzByPageJson';

        var tableConfig = {
            id: 'bdcdyh',
            url: url,
            where: {gzlslid: gzlslid,qjgldm:qjgldm},
            defaultToolbar: ['filter'],
            cols: [unitTableTitle]
        };

        //加载表格
        loadDataTablbeByUrl('#lpbTable', tableConfig);
        //监听行工具事件
        table.on('tool(lpbTable)', function (obj) {
            showLpb(obj.data.fwDcbIndex);
        });

    });

    function showLpb(fwDcbIndex) {
        var url = "/building-ui/lpb/view?code=getbdcdyh&fwDcbIndex=" + fwDcbIndex + "&gzlslid=" + gzlslid+"&qjgldm="+qjgldm;
        var index = parent.layer.open({
            type: 2,
            title: "楼盘表",
            area: ['1150px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: url
        });
        parent.layer.full(index);


    }

});