var type = GetQueryString("type");
var moduleCode = GetQueryString("moduleCode");
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
        //初始化日期控件
        laydate.render({
            elem: '#kssj',
            format: 'yyyy-MM-dd'
        });
        //默认今天
        laydate.render({
            elem: '#jssj',
            format: 'yyyy-MM-dd',
            value: new Date()
        });


        var tableConfig = {
            limits: [10, 50, 100, 200, 500]
            , cols: [[
                {field: 'xh', title: '序号', width: 50, fixed: 'left', type: 'numbers'},
                {field: 'dbsj', title: '登簿时间', width: '10%'},
                {field: 'dbsjl', title: '登簿数据量', width: '18%'},
                {field: 'jrsjl', title: '接入数据量', width: '18%'},
                {field: 'dbrzxqsjl', title: '登簿日志详情数据量', width: '18%'},
                {
                    field: 'tsxx', title: '提示信息', templet: function (d) {
                        if (d.tsxx) {
                            return '<p class="bdc-table-state" style="background-color: red"><span class="">' + d.tsxx + '</span></p>';
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'cz', title: '操作', fixed: 'right', align: 'center', toolbar: '#buttonTpl', width: '10%'}
            ]],
            data: [],
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


        var accessLogUrl = "/realestate-exchange/rest/v1.0/dbjrxqdb";
        loadDataTablbeByUrl("#dbjrxqTable", tableConfig);
        //提交表单
        form.on("submit(query)", function (data) {
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            if (!isNotBlank($("#kssj").val()) || !isNotBlank($("#jssj").val())) {
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">请输入开始和结束时间');
                return false;
            }
            var qsTime = new Date($("#kssj").val()).getTime();
            var jsTime = new Date($("#jssj").val()).getTime();
            var time = jsTime - qsTime;
            if (time < 0) {
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">结束时间不能小于开始时间');
                return false;
            }
            if (0 == count) {
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">请输入必要查询条件');
                return false;
            }
            layui.use(['table'], function () {
                var table = layui.table;
                table.reload("dbjrxqTable", {
                    where: data.field
                    , page: {
                        //重新从第 1 页开始
                        curr: 1
                    },
                    url: accessLogUrl
                });
            });
            return false;
        });

        table.on('tool(dbjrxqTable)', function (obj) {
            var data = obj.data;
            if (data) {
                if (obj.event === "dbjrdbmx") {
                    window.open('/realestate-exchange/view/dbjrdbMx.html?djsj=' + data.dbsj);
                }
            }
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