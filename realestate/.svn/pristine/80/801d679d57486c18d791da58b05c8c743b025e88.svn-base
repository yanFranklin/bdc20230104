/*获取字典数据*/

var table;
var form;
var laydate;
/*table数据加载*/
$(function () {

    layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
        table = layui.table;
        form = layui.form;
        laydate = layui.laydate;
        //不动产单元号的表头
        var unitTableTitle = [
            {field: 'fw_dcb_index', title: '', hide: true},
            {field: 'lszd', title: '隶属宗地', width: "30%"},
            {field: 'fwmc', title: '房屋名称', width: "30%"},
            {field: 'zldz', title: '坐落', width: "30%"},
            {field: 'cz', title: '操作', width: "10%", templet: '#lpbczTpl', fixed: "right"}
        ];
        var url = getContextPath() + '/rest/v1.0/lpb';
        var ljzQO = form.val("searchform");
        var tableConfig = {
            id: 'fwdcbindex',
            url: url,
            where: {ljzQO: JSON.stringify(ljzQO)},
            defaultToolbar: ['filter'],
            page: true,
            toolbar: '#toolbarDemo',
            cols: [unitTableTitle],
            done: function (res, curr, count) {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() === 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
                }
                var reverseList = ['zldz'];
                reverseTableCell(reverseList, "fwdcbindex");
            }
        };

        //加载表格
        loadDataTablbeByUrl('#lpbTable', tableConfig);
        //监听行工具事件，表格操作栏内部按钮
        table.on('tool(lpbTable)', function (obj) {
            var ljzxx = obj.data;
            switch (obj.event) {
                case 'showlpb':
                    showlpb(ljzxx.fw_dcb_index);
                    break;
            }
        });

        //提交表单
        form.on("submit(querylpb)", function (data) {
            tableReloadNew('fwdcbindex', data.field,url);
            return false;
        });

    });

});

function showlpb(fwdcbindex) {
    saveSessionParam({});
    toView(getIP() + '/building-ui/lpb/view?code=htba&fwDcbIndex=' + fwdcbindex, {tabname: "楼盘表"});
}