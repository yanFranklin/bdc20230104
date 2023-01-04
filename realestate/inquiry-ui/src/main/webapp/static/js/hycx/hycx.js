/**
 * author: <a href="mailto:huangjian@gtmap.cn>huangjian</a>
 * version 1.0.0  2019/05/22
 * describe: 婚姻信息查询
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
// var zdList = getMulZdList("bdclx");
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
            elem: '#hyTable',
            toolbar: '#toolbarDemo',
            title: '婚姻信息列表',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},

                {field: 'jtcyname', title: '配偶姓名', width: 200, align: 'center'},
                // 这样不行？
                {field: 'zjh', title: '配偶证件号', width: 250, align: 'center'},
                {field: 'hyzt', title: '婚姻状态', width: 200, align: 'center'},

                // {title: '查看', fixed: 'right', renderer: 'detailView', toolbar: '#barDemo', width: 100}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                //不是在这儿这样写呢
                for (var i = 0; i < res.content.length; i++) {
                    res.content[i].jtcyname = res.content[i].jtcy.jtcymc;
                    res.content[i].zjh = res.content[i].jtcy.zjh;
                }
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                setHeight();
            }
        });

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
        });

        //查询
        function search() {
            // 判断必填条件
            // 获取查询参数
            var qlrmc =$('#qlrmc').val()
            var qlrzjh =$('#qlrzjh').val()
            if(null ==qlrmc){
                layer.alert("<div style='text-align: center'>请输入姓名条件！</div>", {title: '提示'});
                return false;
            }
            if(null ==qlrzjh){
                layer.alert("<div style='text-align: center'>请输入身份证条件！</div>", {title: '提示'});
                return false;
            }

            addModel();
            // 重新请求
            table.reload("hyTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/hycx/hefei/hyxx?qlrmc="+qlrmc+"&qlrzjh="+qlrzjh+"&beanName=acceptHyxx"
                , type: "GET",
                dataType: "json"
                , page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
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



