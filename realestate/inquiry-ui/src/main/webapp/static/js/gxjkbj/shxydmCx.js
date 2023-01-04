/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/24
 * @description 社会组织统一社会信用代码信息查询
 */
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    form.render();

    // 查询参数
    var searchObj = {};
    $(function () {
        // 加载Table
        table.render({
            elem: '#shxydmTable',
            toolbar: '#toolbarDemo',
            title: '社会信用代码查询列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'org_name', title: '社会组织名称', width: 150},
                {field: 'usc_code', title: '统一社会信用代码', width: 220},
                {field: 'business_scope', title: '业务范围', width: 150, hide:true},
                {field: 'legal_name', title: '法定代表人', width: 150},
                {field: 'registered_capital', title: '注册资金', width: 150, hide:true},
                {field: 'domicile_addres', title: '住所_具体地址', width: 320},
                {field: 'valid_from', title: '证书有效期起', width: 150, hide:true},
                {field: 'valid_to', title: '证书有效期止', width: 150, hide:true},
                {field: 'issue_certificate_dept', title: '发证机关', width: 150, hide:true},
                {field: 'registration_date', title: '发证日期', width: 150, hide:true},
                {field: 'manager_dept', title: '主管单位', width: 150, hide:true},
                {field: 'ifcharity', title: '是否慈善组织', width: 150,
                    templet: function (d) {
                        if (d.ifcharity ==1) {
                            return '是';
                        } else if (d.ifcharity ==2) {
                            return '否';
                        }else {
                            return '';
                        }
                    }
                },
                {field: 'cz', title: '操作', minWidth: 80, templet: '#czTpl', fixed: 'right'}
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

        //监听社会信用代码行工具事件
        table.on('tool(shxydmTable)', function (obj) {
            var data = obj.data;
            form.val('shxydmForm', data);
            //小弹出层
            layer.open({
                title: '社会组织信息',
                type: 1,
                area: ['450px', '550px'],
                // btn: ['关闭'],
                content: $('#bdc-popup-small')
                , yes: function (index, layero) {
                }
                , cancel: function () {
                    //右上角关闭回调
                    $("#shxydmForm")[0].reset();
                    closeWin();
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                , success: function () {
                    $('#bdc-popup-small div.business_scope').html(data.business_scope);
                    // 只有社会团体法人登记证书信息显示活动地域字段
                    if(searchObj.cxywcs ==[] || searchObj.cxywcs.search_type!='20'){
                        $('#bdc-popup-small input[name="activity_range"]').parent().parent().hide();
                    }
                }
            });

        });
        /**
         * 点击查询
         */
        $('#search').on('click',function () {
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
            searchObj = {},list ={};
            searchObj.cxywcs=[];
            list.tyshxydm = $('#tyshxydm').val();
            list.name = $('#name').val();
            list.search_type = $('#search_type').val();
            searchObj.cxywcs.push(list);
            //分页
            searchObj.loadpage=true;

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/shzztyxxcx",
                type: "POST",
                data: JSON.stringify(searchObj),
                contentType: 'application/json',
                success: function (data) {
                    var content = [];
                    if(data && data.content){
                        content = data.content;
                    }
                    removeModal();
                    table.reload("shxydmTable", {
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

        /**
         * 关闭弹出页面
         */
        window.closeWin = function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        };

        window.closeParentWindow = function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        };
    });

});



