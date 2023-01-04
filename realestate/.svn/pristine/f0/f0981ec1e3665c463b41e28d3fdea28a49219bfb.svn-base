/**
 * @author "<a href="mailto:wangyaqing@gtmap.cn>wangyaqing</a>"
 * @version 1.0, 2020/11/13
 * @description 市场监管总局-企业基本信息验证接口
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
            elem: '#qyjbxxTable',
            toolbar: '#toolbarDemo',
            title: '企业基本信息查询',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', title: '序号', type: 'numbers', width: 60},
                {field: 'entname', title: '企业名称', width: 200},
                {field: 'enttypeCn', title: '企业类型', width: 150},
                {field: 'name', title: '法定代表人', width: 100},
                {field: 'estdate', title: '成立日期', width: 100},
                {field: 'uniscid', title: '统一社会信用代码', width: 150},
                {field: 'regno', title: '注册号', width: 150},
                {field: 'regcap', title: '注册资本', width: 120},
                {field: 'regcapcurCn', title: '注册资本币种', width: 120},
                {field: 'opscope', title: '经营范围', width: 150},
                {field: 'opfrom', title: '经营期限自', width: 150},
                {field: 'opto', title: '经营期限至', width: 150},
                {field: 'dom', title: '住所', width: 200},
                {field: 'regorgCn', title: '登记机关', width: 120},
                {field: 'regstateCn', title: '登记状态', width: 200},
                {field: 'apprdate', title: '核准日期', width: 150},
                {field: 'sExtNodenum', title: '节点号', width: 150},
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
            list.entname = $('#entname').val();
            list.uniscid = $('#uniscid').val();
            obj.cxywcs.push(list);
            obj.loadpage=true;

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/qyjbxx",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    var content =[];
                    if(data){
                        content = data.content;
                    }
                    removeModal();
                    table.reload("qyjbxxTable", {
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