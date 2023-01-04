/**
 * 台湾居民申请信息查询
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,  form = layui.form,  table = layui.table;

    // 台湾居民申请信息
    var twjmsqxxData = [];
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
                elem: '#twjmsqxxcxTable',
                toolbar: '#toolbarDemo',
                title: '台湾居民申请信息查询',
                defaultToolbar: ['filter'],
                even: true,
                cols: [[
                    {field: 'zwxm', title: '中文姓名', width: 120},
                    {field: 'twsfzh', title: '台湾身份证号', width: 120},
                    {field: 'gjdq', title: '国家地区', width: 100},
                    {field: 'sqsy', title: '申请事由', width: 120},
                    {field: 'ztzydm', title: '在台职业代码', minWidth: 120},
                    {field: 'jzdpcsmc', title: '居住地派出所名称', minWidth: 150},
                    {field: 'jwrylb', title: '境外人员类别', width: 120},
                    {field: 'rjka', title: '入境口岸', width: 120},
                    {field: 'rjsy', title: '入境事由', width: 120},
                    {field: 'djsy', title: '定居事由', minWidth: 120},
                    {field: 'jwrysf', title: '境外人员身份', width: 120},
                    {field: 'ftzwxm', title: '繁体中文姓名', width: 120},
                    {field: 'rjrq', title: '入境日期', width: 120},
                    {field: 'twjzxz', title: '台湾居住详址', minWidth: 250},
                    {field: 'cjsj', title: '创建时间', width: 150},
                    {field: 'xb', title: '性别', width: 100},
                    {field: 'jzdxxdz', title: '居住详细地址', minWidth: 250},
                    {field: 'jnlxdh', title: '境内联系电话 ', width: 150},
                    {fixed: 'right', title: '操作', toolbar: '#czTpl', width: 80}
                ]],
                data: twjmsqxxData,
                page: true,
                done: function () {
                    setHeight();
                }
            });

            //监听行工具事件
            table.on('tool(twjmsqxxcxTable)', function (obj) {
                var data = obj.data;
                form.val('twjmsqxxForm', data);
                //小弹出层
                layer.open({
                    title: '台湾居民申请信息',
                    type: 1,
                    area: ['960px', '500px'],
                    content: $('#popupTwoRows')
                    , yes: function (index, layero) {
                    }
                    , cancel: function () {
                        //右上角关闭回调
                        $("#twjmsqxxForm")[0].reset();
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
            var zwxm = $("#zwxm").val();
            var twsfzh = $("#twsfzh").val();
            if (isNullOrEmpty(zwxm) || isNullOrEmpty(twsfzh)) {
                warnMsg("请输入查询接口完整条件！");
                return;
            }

            addModel();
            // 查询台湾居民申请信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/gxjkCx/twjmcx",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({"zwxm": zwxm, "twsfzh": twsfzh}),
                success: function (data) {
                    if (data &&data.list) {
                        twjmsqxxData = data.list;
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