/**
 * 信息补录办理流程展示配置（配置xxblSelectbdcdy.html可以下拉展示哪些流程）
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,  form = layui.form,  table = layui.table;

    $(function () {
        var showProcess;
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/blxx/blxz/showprocess",
            type: 'GET',
            async: false,
            dataType: 'json',
            success: function (data) {
                if (data && data.length > 0) {
                    showProcess = data;
                }
            }, error: function () {
                failMsg("获取展示流程配置失败！");
            }
        });

        table.render({
            elem: '#bllcpzTable',
            toolbar: '#toolbar',
            title: '信息补录展示流程',
            defaultToolbar: ['filter', 'print', 'exports'],
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left', width: 60},
                {field: 'category', title: '目录名称', minWidth: 120},
                {field: 'processName', title: '流程名称', minWidth: 120},
                {field: 'processId', title: '流程ID', minWidth: 120},
            ]],
            data: [],
            page: false,
            limit: Number.MAX_VALUE,
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
                }
            }
        });


        search();

        // 表格信息
        function init() {

        }

        function search() {
            addModel();
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/blxz/process",
                type: 'GET',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data && data.length > 0) {
                        var processData = [];
                        data.forEach(function(category) {
                            if(category && category.processList && category.processList.length > 0) {
                                category.processList.forEach(function (process) {
                                    var checked = false;
                                    if (showProcess && showProcess.length > 0) {
                                        showProcess.forEach(function (value) {
                                            if (value == process.id) {
                                                checked = true;
                                            }
                                        })
                                    }

                                    processData.push({
                                        "category": process.category,
                                        "processName": process.name,
                                        "processId": process.id,
                                        "LAY_CHECKED": checked
                                    });
                                });
                            }
                        });

                        table.reload('bllcpzTable', {
                            data: processData
                        });
                    }
                }, error: function () {
                }, complete: function () {
                    removeModel();
                }
            });
        }
    });

    //头工具栏事件
    table.on('toolbar(bllcpzTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'save':
                save(checkStatus.data);
                break;
        }
    });

    function save(data) {
        if(!data || data.length == 0) {
            warnMsg("请选择需要展示的流程！");
            return;
        }

        var process = [];
        data.forEach(function (item) {
            process.push(item.processId);
        });

        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/blxx/blxz/showprocess",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(process),
            contentType: "application/json;charset=UTF-8",
            success: function () {
                successMsg("保存成功！");
            }, error: function () {
                failMsg("保存失败，请重试！");
            }
        });
    }
});