$(function () {

    layui.use(['form', 'table', 'jquery'], function () {
        var table = layui.table;
        //不动产单元号的表头
        var unitTableTitle = [
            {field: 'bdcdyh', title: '权籍附件单元号', templet: '#bdcdyhTpl'},
            {field: 'slbh', title: '权籍受理编号'},
            {field: 'fwmc', title: '房屋名称'},
            {field: 'lszd', title: '隶属宗地'},
            {field: 'zl', title: '坐落'},
            {field: 'cz', title: '操作', width: 100, templet: '#bdcdyczTpl', fixed: "right"}
        ];
        var url = getContextPath() + '/rest/v1.0/slym/listQjfjByPageJson';

        var tableConfig = {
            id: 'bdcdyh',
            url: url,
            where: {gzlslid: gzlslid},
            defaultToolbar: ['filter'],
            cols: [unitTableTitle]
        };

        //加载表格
        loadDataTablbeByUrl('#qjfjTable', tableConfig);
        //监听行工具事件
        table.on('tool(qjfjTable)', function (obj) {
            showFj(obj.data.slbh, obj.data.archiveFjUrl);
        });

    });

    function showFj(slbh, archiveFjUrl) {
        if (!isNotBlank(slbh) || !isNotBlank(archiveFjUrl)) {
            layer.alert("缺失受理编号或者档案配置地址");
            return false;

        }
        var url = archiveFjUrl + slbh;
        var index = layer.open({
            type: 2,
            title: "权籍附件",
            area: ['1150px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: url
        });
        layer.full(index);


    }

});

