/**
 * author: <a href="mailto:huangjian@gtmap.cn>huangjian</a>
 * version 1.0.0  2019/05/22
 * describe: 房地产权单元查询
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var reverseList = ['zl'];
layui.use(['jquery', 'element', 'form', 'table', 'laydate', 'formSelects'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var formSelects = layui.formSelects;

    form.render();

    // 当前页数据
    var currentPageData = new Array();
    $(function () {
        // 加载Table
        table.render({
            elem: '#bdcdyTable',
            toolbar: '#toolbarDemo',
            title: '房地产权信息列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'xmid', title: '项目编号', width: 200, align: 'center', hide: true},
                {field: 'zl', minWidth: 250, title: '坐落'},
                {field: 'bdcqzh', minWidth: 280, title: '不动产权证号'},
                {field: 'fjh', title: '房间号', width: 150, align: 'center'},
                {field: 'qlr', title: '权利人', width: 200, align: 'center'},
                {field: 'qlrzjh', title: '权利人证件号', width: 250, align: 'center'},
                {
                    field: 'djsj', width: 180, title: '登记时间',
                    templet: function (d) {
                        return formatDate(d.djsj);
                    }
                },
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                setHeight();
                reverseTableCell(reverseList);
            }
        });

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //查询
        function search() {
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            var key = formSelects.value('select07', 'val');

            if (0 == count) {
                layer.alert("<div style='text-align: center'>请输入查询内容！</div>", {title: '提示'});
                return false;
            }
            if (0 == key.length) {
                layer.alert("<div style='text-align: center'>请选择查询范围！</div>", {title: '提示'});
                return false;
            }

            // 获取查询参数
            var obj = {};
            var keys = "";
            key.forEach(function (value, i) {
                keys = keys + value + ","
            });
            keys = keys.substr(0, keys.length - 1)
            $(".cxtj").each(function (i) {
                var name = $(this).attr('name');
                obj[name] = $(this).val();
                obj['keys'] = keys;
                obj['index'] = "bdc-fdcq";

            });

            addModel();
            // 重新请求
            table.reload("bdcdyTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/es/fdcq"
                , where: obj
                , page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                }
            });
        }

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

    });

});



