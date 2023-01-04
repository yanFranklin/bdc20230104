/*
* 接入量登簿量对比明细，受理编号，不动产单元号，产权证号，坐落，原因
* */
var type = GetQueryString("type");
var moduleCode = GetQueryString("moduleCode");
var djsj = GetQueryString("djsj");
var reverseList = ['zl'];
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;
    // var reverseList = ['bdcdyh', 'zl', 'bdcqzh'];
    $(function () {
        var dbjrmx = "/realestate-exchange/rest/v1.0/dbjrxqdb/mx?djsj=" + djsj;
        var tableConfig = {
            url: dbjrmx,
            limits: [10, 50, 100, 200, 500]
            , cols: [[
                {field: 'xh', title: '序号', width: 50, fixed: 'left', type: 'numbers'},
                {field: 'slbh', title: '受理编号', width: '15%'},
                {field: 'bdcdyh', title: '不动产单元号', width: '20%'},
                {field: 'zl', title: '坐落', width: '20%'},
                {field: 'bdcqzh', title: '不动产权证号', width: '20%'},
                {field: 'tsyy', title: '原因'}
            ]],
            data: [],
            done: function () {
                // reverseTableCell(reverseList);
                // $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                // if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                //     $('.layui-table-body').height('60px');
                //     $('.layui-table-fixed .layui-table-body').height('60px');
                //     $('.layui-table-body .layui-none').html('<img src="../static/images/table-none.png" alt="">无数据');
                // } else {
                //     $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                //     $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                // }
                setElementAttrInTableByModuleAuthority(moduleCode);
            }
        };


        loadDataTablbeByUrl("#dbjrmxTable", tableConfig);
        //提交表单
        form.on("submit(query)", function (data) {
            // 判断必填条件
            layui.use(['table'], function () {
                var table = layui.table;
                table.reload("dbjrmxTable", {
                    where: data.field
                    , page: {
                        //重新从第 1 页开始
                        curr: 1
                    },
                    url: dbjrmx
                });
            });
            return false;
        });
    });
});

//空字符串不包括"(空格)",只特指""
function isNotBlank(object) {
    if (typeof object === "object" && !(object instanceof Array)) {
        var hasProp = false;
        for (var prop in object) {
            hasProp = true;
            break;
        }
        if (hasProp) {
            hasProp = [hasProp];
        } else {
            return false;
        }
        return hasProp;
    }
    return typeof object != "undefined" && object != "";
}