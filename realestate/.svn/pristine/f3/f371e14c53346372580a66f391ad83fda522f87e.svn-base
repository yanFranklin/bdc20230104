/**
 * @author "<a href="mailto:wangyaqing@gtmap.cn>wangyaqing</a>"
 * @version 1.0, 2020/11/13
 * @description 民政部-社会团体法人登记证书查询
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
            elem: '#shttfrdjzsTable',
            toolbar: '#toolbarDemo',
            title: '社会团体法人登记证书查询',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'RN', title: '序号', width: 60},
                {field: 'ORG_NAME', title: '社会组织名称', width: 120},
                {field: 'USC_CODE', title: '统一社会信用代码', width: 120},
                {field: 'LEGAL_NAME', title: '法定代表人', width: 100},
                {field: 'REGISTERED_CAPITAL', title: '注册资金', width: 100},
                {field: 'MANAGER_DEPT', title: '主管单位', width: 150},
                {field: 'IFCHARITY', title: '是否慈善组织', width: 100},
                {field: 'ISSUE_CERTIFICATE_DEPT', title: '发证机关', width: 150},
                {field: 'REGISTRATION_DATE', title: '发证日期', width: 120},
                {field: 'VALID_FROM', title: '证书有效期起', width: 120},
                {field: 'VALID_TO', title: '证书有效期止', width: 120},
                {field: 'BUSINESS_SCOPE', title: '业务范围', width: 200},
                {field: 'DOMICILE_ADDRES', title: '住所_具体地址', width: 200},
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
            list.org_name = $('#org_name').val();
            list.usc_code = $('#usc_code').val();
            obj.cxywcs.push(list);
            obj.loadpage =true;

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/shttfrdjzs",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    var content =[];
                    if(data){
                        content = data.content;
                    }
                    $.each(content, function (index, item){
                        if(item.IFCHARITY == '1'){
                            item.IFCHARITY = '是';
                        }else{
                            item.IFCHARITY = '否';
                        }
                    })
                    removeModal();
                    table.reload("shttfrdjzsTable", {
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