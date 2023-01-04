//统一每页条数的选择项
var commonLimits = [10, 20, 50, 100, 200, 500];
layui.use(['jquery', 'table', 'form', 'layer'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        layer = layui.layer;

    $(function () {
        // 获取参数（工作流定义 id）
        var processInsId = $.getUrlParam('processInsId');
        table.render({
            elem: '#listTable',
            title: '合同附件表格',
            method: 'POST',
            contentType: 'application/json',
            dataType: "json",
            even: true,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            defaultToolbar: ['filter'],
            limits: commonLimits,
            cols: [[
                {field: 'htbh', title: '合同编号', width: "20%"},
                {field: 'dzbah', title: '电子备案号', width: "20%"},
                {field: 'xmmc', title: '项目名称', width: "25%"},
                {field: 'zdbh', title: '宗地编号', width: "25%"},
                {title: '操作', width: "10%", templet: '#editBar', align: "center"}
            ]],
            data: [],
            page: true,
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                removeModal();
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        });

        /**
         * 查询点击事件
         */
        $('#search').on('click', function () {
            // 获取查询内容
            var obj = {};
            var searchParamFlag = false;
            $(".search").each(function (i) {
                var value = $(this).val();
                if(!searchParamFlag && value != "" && value != undefined){
                    searchParamFlag = true;
                }
                var name = $(this).attr('name');
                // 此处去除查询条件中的空格
                obj[name] = deleteWhitespace(value);
            });
            if(!searchParamFlag){
                layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请输入至少一个条件');
                return false;
            }
            obj['processInsId'] = processInsId;
            // 查询时间长，添加遮罩
            addModel();
            // 重新请求
            table.reload("listTable", {
                url: getContextPath() + "/htfj/getHtfj",
                where: obj,
                page: {
                    curr: 1  //重新从第 1 页开始
                },
                done: function () {
                    removeModal();
                }
            });
        });


        /**
         * 监听工具栏事件，工具栏进入到编辑页面可以对于数据进行操作
         */
        table.on('tool(listTable)', function (obj) {
            var data = obj.data; //获得当前行数据
            var gzlslid = $.getUrlParam('processInsId');
            data['gzlslid'] = gzlslid;
            if(obj.event === 'hq'){
                $.ajax({
                    url: getContextPath() + "/htfj/schtfj",
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        ityzl_SHOW_SUCCESS_LAYER("获取合同附件成功");
                    },
                    error: function (e) {
                        ityzl_SHOW_WARN_LAYER("获取合同附件失败");
                    },complete:function () {
                    }
                });
            }
        });
    });


});

