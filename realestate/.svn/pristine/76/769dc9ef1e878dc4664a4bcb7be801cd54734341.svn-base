/**
 * @author "<a href="mailto:zedeqiang@gtmap.cn>zedq</a>"
 * @version 1.0, 2021/08/05
 * @description 公安部-人口库身份核查接口
 */
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    form.render();
    $(function () {
        // 加载Table
        table.render({
            elem: '#idCheckInfoTable',
            // toolbar: '#toolbarDemo',
            title: '人口库基准信息查询列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            /*
                "gmsfzh": "32098506185815",
                "gmsfzh_ppddm": "1",
                "swbs": "0",
                "xm": "朱军",
                "xm_ppddm": "1"
             */
            even: true,
            cols: [[
                {field: 'gmsfzh', title: '公民身份证号'},
                {field: 'gmsfzh_ppddm', title: '公民身份证号匹配度', templet: '#gmsfzhppddmDemo'},
                {field: 'swbs', title: '死亡标识', templet: '#swbsDemo'},
                {field: 'xm', title: '公民姓名'},
                {field: 'xm_ppddm', title: '姓名匹配度', templet: '#xmppddmDemo'}
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
            var count = 0;
            $('.required-warn').each(function () {
                if ($(this).val() === '') {
                    count++;
                }
            });
            if (count > 0) {
                warnMsg('请填写完整的查询条件');
                return false;
            }
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
            var obj = [], list = {};
            list.xm = $('#xm').val();
            list.sfzh = $('#sfzh').val();
            obj.push(list);
            //分页
            obj.loadpage = true;

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/id/check",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    var content = [];
                    if (data && data.content) {
                        content = data.content;
                    }
                    removeModal();
                    table.reload("idCheckInfoTable", {
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



