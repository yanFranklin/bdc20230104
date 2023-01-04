layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;
    form.render();

    /**
     * 加载Table数据列表
     */
    $(function () {

        table.render({
            elem: '#bahtTable',
            toolbar: "#toolbar",
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            cellMinWidth: 80,
            data: [],
            even: true,
            cols: [[
                {field: 'htbh', title: '合同编号'},
                {field: 'msr', title: '姓名'},
                {field: 'fwzl', title: '房屋坐落'},
                {field: 'barq', title: '备案日期'},
                {field: 'msrzjhm', title: '身份证号'}
            ]],
            text: {
                none: '未查询到数据'
            },
            autoSort: false,
            page: {
                curr: 1  //重新从第 1 页开始
            },
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        });


        /**
         * 点击查询
         */
        $('#query').on('click', function () {
            // 获取查询内容
            var htbh = $('input[name="htbh"]').val();
            var bdcdyh = $('input[name="bdcdyh"]').val();
            var qzh = $('input[name="qzh"]').val();
            var zjhm = $('input[name="zjhm"]').val();

            var obj = {
                'htbh': htbh,
                'bdcdyh': bdcdyh,
                'qzh': qzh,
                'zjhm': zjhm
            };
            // 重新请求
            addModel();
            table.reload("bahtTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/bengbu/htxx/baht",
                where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }, done: function (res, curr, count) {
                    removeModal();
                    //saveDetailLog("YYCX","异议查询",paramD);
                }
            });
            return false;
    });

    /**
     * 重新加载数据
     */
    window.reload = function () {
        table.reload("bahtTable", {
            where: []
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };


    });

})