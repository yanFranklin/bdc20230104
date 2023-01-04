/**
 * 常住人口信息查询
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,  form = layui.form,  table = layui.table;

    // 常住人口信息
    var czrkxxData = [];
    $(function () {
        // 初始化
        init();

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        // 表格信息
        function init() {
            table.render({
                elem: '#czrkxxcxTable',
                toolbar: '#toolbarDemo',
                title: '常住人口信息查询',
                defaultToolbar: ['filter'],
                even: true,
                cols: [[
                    {field: 'xm', title: '姓名', width: 120},
                    {field: 'zym', title: '曾用名', width: 120},
                    {field: 'gmsfhm', title: '公民身份号码', width: 200},
                    {field: 'xb', title: '性别', width: 120},
                    {field: 'hyzk', title: '婚姻状况', minWidth: 120},
                    {field: 'yhzgx', title: '与户主关系', minWidth: 120},
                    {field: 'qfjg', title: '签发机关', width: 180},
                    {field: 'hlx', title: '户类型', width: 120},
                    {field: 'whcd', title: '文化程度', width: 120},
                    {field: 'hh', title: '户号', minWidth: 120},
                    {field: 'byzk', title: '兵役状况', width: 120},
                    {field: 'csrq', title: '出生日期', width: 120},
                    {field: 'jgssxq', title: '籍贯省市县（区）', width: 150},
                    {field: 'csdssxq', title: '出生地省市县（区）', minWidth: 200},
                    {field: 'zy', title: '职业', width: 150},
                    {field: 'mz', title: '民族', width: 100},
                    {field: 'hxzlbz', title: '何祥址来本址', minWidth: 250},
                    {field: 'hslbz', title: '何时来本址 ', width: 150},
                    {field: 'yxqxqsrq', title: '有效期限起始日期 ', width: 150},
                    {field: 'yxqxjzrq', title: '有效期限截止日期 ', width: 150},
                    {field: 'gxsj', title: '更新时间 ', width: 150},
                    {field: 'qysj', title: '起用时间 ', width: 150},
                    {fixed: 'right', title: '操作', toolbar: '#czTpl', width: 80}
                ]],
                data: czrkxxData,
                page: true,
                done: function () {
                    setHeight();
                }
            });

            //监听行工具事件
            table.on('tool(czrkxxcxTable)', function (obj) {
                var data = obj.data;
                form.val('czrkxxForm', data);
                //小弹出层
                layer.open({
                    title: '常住人口信息',
                    type: 1,
                    area: ['960px', '550px'],
                    content: $('#popupTwoRows')
                    , yes: function (index, layero) {
                    }
                    , cancel: function () {
                        //右上角关闭回调
                        $("#czrkxxForm")[0].reset();
                        closeWin();
                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                    , success: function () {

                    }
                });

            });
        }

        // 查询事件
        function search() {
            var xm = $("#xm").val();
            var gmsfhm = $("#gmsfhm").val();
            if (isNullOrEmpty(xm) || isNullOrEmpty(gmsfhm)) {
                warnMsg("请输入查询接口完整条件！");
                return;
            }

            addModel();
            // 查询常住人口信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/gxjkCx/czrkcx",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({"xm": xm, "gmsfhm": gmsfhm}),
                success: function (data) {
                    if (data &&data.czrkList) {
                        czrkxxData = data.czrkList;
                    }
                }, error: function () {
                }, complete: function () {
                    init();
                    removeModal();
                }
            });
        }
    });

    /**
     * 关闭弹出页面
     */
    window.closeWin = function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };
});