/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 查询条件所需的日期初始化、复选框初始化及高级查询
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        table = layui.table,
        element = layui.element,
        form = layui.form,
        laydate = layui.laydate;
    // 婚姻登记信息
    var hydjxxData = [];
    // 社会组织信息
    var shzzxxData = [];
    $(function () {
        initTable();
        //初始化日期控件
        laydate.render({
            elem: '#startTime'
        });
        laydate.render({
            elem: '#endTime'
        });

        // 点击查询
        $('#search').on('click', function () {
            search();
        });


        //监听婚姻登记行工具事件
        table.on('tool(pageTable)', function (obj) {
            var data = obj.data;
            form.val('hydjxxForm', data);
            //小弹出层
            layer.open({
                title: '婚姻登记信息',
                type: 1,
                area: ['450px', '600px'],
                // btn: ['关闭'],
                content: $('#bdc-popup-small')
                , yes: function (index, layero) {
                }
                , cancel: function () {
                    //右上角关闭回调
                    $("#hydjxxForm")[0].reset();
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                , success: function () {

                }
            });

        });

        //社会组织信息登记行工具事件
        table.on('tool(pageTable1)', function (obj) {
            var data = obj.data;
            var ifcharity = data.ifcharity;
            if (ifcharity && (1 == ifcharity || '1' == ifcharity)) {
                data.ifcharity = '是';
            } else {
                data.ifcharity = '否';
            }
            form.val('shzzdjxxForm', data);
            //小弹出层
            layer.open({
                title: '社会组织信息',
                type: 1,
                area: ['450px', '600px'],
                // btn: ['关闭'],
                content: $('#bdc-popup-small-2')
                , yes: function (index, layero) {
                }
                , cancel: function () {
                    //右上角关闭回调
                    $("#shzzdjxxForm")[0].reset();
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                , success: function () {

                }
            });

        });

        function search() {
            var slbh = $("#slbh").val();
            var qlrmc = $("#qlrmc").val();
            var zjh = $("#zjh").val();

            if (isNullOrEmpty(slbh) || isNullOrEmpty(qlrmc) || isNullOrEmpty(zjh)) {
                warnMsg("请输入查询接口完整条件！");
                return;
            }
            addModel();
            var searchData = {"slbh": slbh, "qlrmc": qlrmc, "zjh": zjh, "type": "10"};

            // 查询婚姻登记信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/bjjk/hydjxx",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(searchData),
                success: function (data) {
                    if (data) {
                        hydjxxData = data;
                    }
                }, error: function () {
                }, complete: function () {
                    initTable();
                    removeModal();
                }
            });

            // 查询社会组织信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/bjjk/shzz",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(searchData),
                success: function (data) {
                    if (data) {
                        shzzxxData = data;
                    }
                }, error: function () {
                }, complete: function () {
                    initTable();
                    removeModal();
                }
            });
        }

        // 渲染表格
        function initTable() {
            table.render({
                elem: '#pageTable',
                toolbar: '#toolbarDemo',
                title: '婚姻登记信息',
                defaultToolbar: ['filter', 'print', 'exports'],
                even: true,
                cols: [[
                    {type: 'checkbox', width: 50, fixed: 'left'},
                    {field: 'op_type', title: '婚姻登记业务类型', minwidth: 80},
                    {field: 'dept_name', title: '登记机关名称', minwidth: 80},
                    {field: 'op_date', title: '登记日期', minwidth: 200},
                    {field: 'cert_no', title: '结婚证/离婚证字号', minwidth: 160},
                    {field: 'name_man', title: '男方姓名', minwidth: 80},
                    {field: 'name_woman', title: '女方姓名', minwidth: 80},
                    {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 80}
                ]],
                data: hydjxxData,
                page: true,
                done: function () {
                    setHeight(181);
                }
            });

            var tabTwoIndex = 0;
            element.on('tab(tabFilter)', function (data) {
                setHeight(181);
                if (data.index == 1) {
                    tabTwoIndex++;
                    if (tabTwoIndex == 1) {
                        table.render({
                            elem: '#pageTable1',
                            toolbar: '#toolbarDemo',
                            title: '社会组织信息',
                            defaultToolbar: ['filter', 'print', 'exports'],
                            even: true,
                            cols: [[
                                {type: 'checkbox', width: 50, fixed: 'left'},
                                {field: 'org_name', title: '社会组织名称', minwidth: 80},
                                {field: 'usc_code', title: '统一社会信用代码', minwidth: 80},
                                {field: 'legal_name', title: '法定代表人', minWidth: 100},
                                {field: 'registered_capital', title: '注册资金', minwidth: 100},
                                {
                                    field: 'ifcharity', title: '是否慈善组织', minwidth: 80,
                                    templet: function (d) {
                                        if (d && (1 == d.ifcharity || '1' == d.ifcharity)) {
                                            return '<p style="color:#f54743">是</p>';
                                        } else {
                                            return '<p style="color:#10a54a">否</p>';
                                        }
                                    }
                                },
                                {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 80}
                            ]],
                            data: shzzxxData,
                            page: true,
                            done: function () {
                                setHeight(181);
                            }
                        });
                    }
                }
            });
        }
    });
});