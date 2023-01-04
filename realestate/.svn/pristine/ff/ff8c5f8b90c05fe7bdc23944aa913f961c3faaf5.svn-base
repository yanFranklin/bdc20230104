/**
 * 建设工程竣工验收信息查询
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,  form = layui.form,  table = layui.table;

    // 建设工程竣工验收信息
    var jsgcjgysxxData = [];
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
                elem: '#jsgcjgysxxTable',
                toolbar: '#toolbarDemo',
                title: '建设工程竣工验收信息查询',
                defaultToolbar: ['filter'],
                even: true,
                cols: [[
                    {field: 'proj_code', title: '项目编码', width: 160},
                    {field: 'proj_name', title: '备案项目名称', width: 190},
                    {field: 'proj_jgba_code', title: '备案编号', width: 240},
                    {field: 'proj_jgba_date', title: '备案日期', width: 160},
                    {field: 'proj_jgba_orgname', title: '备案机构', minWidth: 200},
                    {field: 'proj_jgba_personname', title: '备案核准人', minWidth: 200},
                    {field: 'proj_censor_code', title: '施工许可证编', width: 180},
                    {field: 'proj_factcode', title: '实际造价(万元)', width: 130},
                    {field: 'proj_factarea', title: '实际面积(m<sup>2</sup>)', width: 120},
                    {field: 'proj_factsize', title: '市级建设规模', minWidth: 120},
                    {field: 'proj_structsyscode', title: '结构体系名称', width: 120},
                    {field: 'proj_bdate', title: '实际开工日期', width: 160},
                    {field: 'proj_edate', title: '实际竣工验收日期', width: 160},
                    {field: 'proj_jgba_idea', title: '竣工备案验收意见', minWidth: 160},
                    {field: 'cons_name', title: '建设单位', width: 150},
                    {field: 'proj_jgba_address', title: '坐落', width: 100},
                    {fixed: 'right', title: '操作', toolbar: '#czTpl', width: 80}
                ]],
                data: jsgcjgysxxData,
                page: true,
                done: function () {
                    // 修正实际面积title样式问题
                    $('th[data-field="proj_factarea"] span').css({
                        'line-height': '0'
                    });
                    setHeight();
                }
            });

            //监听行工具事件
            table.on('tool(jsgcjgysxxTable)', function (obj) {
                var data = obj.data;
                form.val('jsgcjgysxxForm', data);
                //小弹出层
                layer.open({
                    title: '建设工程竣工验收信息',
                    type: 1,
                    area: ['960px', '550px'],
                    content: $('#popupTwoRows')
                    , yes: function (index, layero) {
                    }
                    , cancel: function () {
                        //右上角关闭回调
                        $("#jsgcjgysxxForm")[0].reset();
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
            var jsdw = $("#jsdw").val();
            var babh = $("#babh").val();
            if (isNullOrEmpty(jsdw) && isNullOrEmpty(babh)) {
                warnMsg("请至少输入一个查询条件！");
                return;
            }

            addModel();
            // 查询建设工程竣工验收信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/gxjkCx/jsgcjg",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({"cons_name": jsdw, "proj_jgba_code": babh}),
                success: function (data) {
                    if (data && data.list) {
                        jsgcjgysxxData = data.list;
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