/**
 * @author "<a href="mailto:wangyaqing@gtmap.cn>wangyaqing</a>"
 * @version 1.0, 2020/11/13
 * @description 卫健委-死亡医学证明查询
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
            elem: '#swyxzmxxTable',
            toolbar: '#toolbarDemo',
            title: '死亡医学证明查询',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'XM', title: '姓名', width: 150},
                {field: 'GMSFHM', title: '公民身份证号', width: 250},
                {field: 'XBDM', title: '性别代码', width: 150},
                {field: 'MZDM', title: '民族代码', width: 150},
                {field: 'SWRQ', title: '死亡日期', width: 250},
                {field: 'SWYY', title: '死亡原因', width: 305},
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
            list.xm = $('#xm').val();
            list.sfzh = $('#sfzh').val();
            obj.cxywcs.push(list);
            obj.loadpage =true;

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/swyxzm",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    var content =[];
                    if(data){
                        content = data.content;
                    }
                    removeModal();
                    table.reload("swyxzmxxTable", {
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