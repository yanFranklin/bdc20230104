/**
 * @author "<a href="mailto:wangyaqing@gtmap.cn>wangyaqing</a>"
 * @version 1.0, 2020/11/13
 * @description 卫健委-出生医学证明查询
 */
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    form.render();

    // 当前页数据
    var currentPageData = new Array();
    $(function () {
        // 加载Table
        table.render({
            elem: '#csyxzmxxTable',
            toolbar: '#toolbarDemo',
            title: '出生医学证明查询',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'babyName', title: '新生儿姓名', width: 120},
                {field: 'babySex', title: '新生儿性别', width: 100},
                {field: 'babySexCode', title: '新生儿性别编码', width: 100},
                {field: 'birthArea', title: '出生地区', width: 180},
                {field: 'birthCode', title: '出生医学证明编号', width: 150},
                {field: 'birthTime', title: '出生时间', width: 200},
                {field: 'momName', title: '母亲姓名', width: 120},
                {field: 'momIdCode', title: '母亲证件编号', width: 200},
                {field: 'dadName', title: '父亲姓名', width: 120},
                {field: 'dadIdCode', title: '父亲证件编号', width: 200},
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
            // 获取查询参数
            var obj = {},list ={};
            obj.cxywcs=[];
            list.cszmbh = $('#cszmbh').val();
            list.mqxm = $('#mqxm').val();
            list.mqzjbm = $('#mqzjbm').val();
            obj.cxywcs.push(list);
            obj.loadpage =true;

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/csyxzm",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    var content =[];
                    if(data){
                        content = JSON.parse(data.content).data.cxjg;
                    }
                    removeModal();
                    table.reload("csyxzmxxTable", {
                        data: content
                        , done: function () {
                            setHeight();
                        }
                    });
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
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