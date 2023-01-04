/**
 * @author "<a href="mailto:wangyaqing@gtmap.cn>wangyaqing</a>"
 * @version 1.0, 2020/11/13
 * @description 中编办-党群机关信息查询
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
            elem: '#dqjgxxTable',
            toolbar: '#toolbarDemo',
            title: '党群机关信息查询',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'jgmc', title: '机构名称'},
                {field: 'tyshxydm', title: '统一社会信用代码'},
                {field: 'jgxz', title: '机构性质'},
                {field: 'fzr', title: '负责人'},
                {field: 'bfrq', title: '颁发日期'},
                {field: 'yxqz', title: '有效期至'},
                {field: 'dz', title: '地址'},
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
            $('.required-warn').each(function(){
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
            var obj = {},list ={};
            obj.cxywcs=[];
            list.tyshxydm = $('#tyshxydm').val();
            obj.cxywcs.push(list);
            obj.loadpage=true;

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/dqjg",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    var content =[];
                    if(data){
                        content = data.content;
                    }
                    removeModal();
                    table.reload("dqjgxxTable", {
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