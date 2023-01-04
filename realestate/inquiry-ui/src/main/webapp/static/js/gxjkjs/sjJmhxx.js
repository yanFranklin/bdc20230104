/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/17
 * @description 省公安厅-居民户成员信息在线查询
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
            elem: '#jmhxxTable',
            toolbar: '#toolbarDemo',
            title: '居民户成员信息在线查询',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'xm', title: '姓名', width: '15%'},
                {field: 'xbzw', title: '性别', width: '10%'},
                {field: 'xb', title: '性别-字典', width:'10%'},
                {field: 'gmsfhm', title: '公民身份号码', width: '20%'},
                {field: 'yhzgxZw', title: '与户主关系', width: '10%'},
                {field: 'yhzgx', title: '与户主关系字典', width: '15%'},
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
            var param = {
                identityNumber : $('#sfzh').val(),
                loadpage:true
            }

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/provincialPublicSecurityDepartment/policeHouseholdMembers",
                type: "POST",
                data: JSON.stringify(param),
                contentType: 'application/json',
                success: function (data) {
                    var content = [];
                    if(data){
                        content = data.content;
                    }
                    removeModal();
                    table.reload("jmhxxTable", {
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



