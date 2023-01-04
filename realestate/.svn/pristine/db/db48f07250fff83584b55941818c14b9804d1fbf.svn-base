/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/09
 * @description 银保监会-金融许可证查询接口接口台账
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
            elem: '#jrxkzTable',
            toolbar: '#toolbarDemo',
            title: '金融许可证查询列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'id', title: '金融许可证id', width: 200, hide:true},
                {field: 'certFlowNo', title: '流水号', width: 150, hide:true},
                {field: 'fullName', title: '机构全称', minWidth: 200},
                {field: 'certCode', title: '机构编码', width: 180},
                {field: 'simpleName', title: '机构简称', width: 200, hide:true},
                {field: 'englishName', title: '英文全称', width: 200, hide:true},
                {field: 'address', title: '机构地址', width: 200},
                {field: 'setDate', title: '设立日期', width: 180},
                {field: 'printDate', title: '打印日期', width: 150, hide:true},
                {field: 'typeId', title: '查询类型', minWidth: 100, hide:true},
                {field: 'organName', title: '监管机构名称', width: 300, hide:true},
                {field: 'scope', title: '经营范围', minWidth: 280},
                {fixed: 'right', title: '操作', toolbar: '#czTpl', width: 80}
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

        //监听婚姻登记行工具事件
        table.on('tool(jrxkzTable)', function (obj) {
            var data = obj.data;
            form.val('jrxkzForm', data);
            $('#popupTwoRows textarea[name="scope"]').html(data.scope);
            //小弹出层
            layer.open({
                title: '金融许可证信息',
                type: 1,
                area: ['960px', '400px'],
                // btn: ['关闭'],
                content: $('#popupTwoRows')
                , yes: function (index, layero) {
                }
                , cancel: function () {
                    //右上角关闭回调
                    $("#jrxkzForm")[0].reset();
                    closeWin();
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                , success: function () {

                }
            });

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
            obj.paramDTOS=[];
            list.typeId = $('#cxlb').val();
            list.certCode = $('#jgbm').val();
            obj.paramDTOS.push(list);
            obj.loadpage=true;

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/cbirc/financialQuery",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();
                    var content = [];
                    if(data && data.content){
                        content = data.content;
                    }
                    table.reload("jrxkzTable", {
                        data:content
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



