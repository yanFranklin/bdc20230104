/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 上报销账台账js
 * @date : 2022/6/21 8:59
 */

var form, $, table, layer;
var type = GetQueryString("type");
var moduleCode = GetQueryString("moduleCode");
var reverseList = ['zl'];
var zdList = [];
var dbrzid = GetQueryString("dbrzid");
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
    form = layui.form;
    $ = layui.jquery;
    table = layui.table;
    layer = layui.layer;

    var element = layui.element;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;

    $(function () {


        var tableConfig = {
            toolbar: "#toolbarDemo"
            , url: '../registerLog/dbrzxq/page?dbrzid=' + dbrzid
            , limits: [10, 50, 100, 200, 500]
            , cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'xh', title: '序号', width: 60},
                {field: 'slbh', title: '受理编号', width: 200},
                {field: 'bdcdyh', title: '不动产单元号', width: 250},
                {field: 'bdcqzh', title: '不动产权证号'},
                {field: 'xmid', title: '项目id', width: 200},
                {field: 'bwid', title: '报文id', width: 200},
                {field: 'dbrzid', title: '登簿日志id', width: 300}
            ]],
            data: [],
            page: true,
            done: function () {
                // reverseTableCell(reverseList);
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body').height('60px');
                    $('.layui-table-fixed .layui-table-body').height('60px');
                    $('.layui-table-body .layui-none').html('<img src="../static/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
                setElementAttrInTableByModuleAuthority(moduleCode);
            }
        };

        loadDataTablbeByUrl("#dbrzxqTable", tableConfig);
        //提交表单
        form.on("submit(query)", function (data) {
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            if (0 === count) {
                layer.msg('<img src="../static/lib/bdcui/images/success-small.png" alt="">请输入必要查询条件');
                return false;
            }
            layui.use(['table'], function () {
                var table = layui.table;
                table.reload("dbrzxqTable", {
                    where: data.field,
                    page: {
                        curr: 1, //重新从第 1 页开始
                        limits: [10, 50, 100, 200, 500]
                    },
                    url: '../registerLog/dbrzxq/page?dbrzid=' + dbrzid
                });
            });
            return false;
        });

    });
});
