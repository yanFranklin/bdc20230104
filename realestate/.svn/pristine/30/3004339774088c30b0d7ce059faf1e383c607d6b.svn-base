/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/17
 * @description 中编办-事业单位信息查询
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
            elem: '#sydwxxTable',
            toolbar: '#toolbarDemo',
            title: '事业单位信息查询列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'dz', title: '地址', width: 250},
                {field: 'fddbr', title: '法定代表人', width: 150},
                {field: 'jbdwmc', title: '举办单位名称', width: 200},
                {field: 'jfly', title: '经费来源', width: 150},
                {field: 'kbzj', title: '开办资金', width: 150},
                {field: 'sydwmc', title: '事业单位名称', width: 200},
                {field: 'tyshxydm', title: '统一社会信用代码', width: 200},
                {field: 'zsyxqsrq', title: '证书有效起始日期', width: 150},
                {field: 'zsyxjzrq', title: '证书有效截止日期', width: 150},
                {field: 'zzhywfw', title: '宗旨和业务范围', width: 250}
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
            list.tyshxydm = $('#tyshxydm').val();
            obj.cxywcs.push(list);
            obj.loadpage=true;




            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/sydw",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    var content =[];
                    if(data){
                        content = data.content;
                    }
                    removeModal();
                    table.reload("sydwxxTable", {
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



