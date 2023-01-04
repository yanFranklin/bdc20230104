/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/09
 * @description 民政部-婚姻登记信息查询服务接口台账
 */
layui.use(['jquery','form', 'table', 'laydate', 'laytpl'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    form.render();

    $(function () {
        //表格信息
        table.render({
            elem: '#hydjxxTable',
            toolbar: '#toolbarDemo',
            title: '婚姻登记信息',
            defaultToolbar: ['filter'],
            even: true,
            cols: [[
                {type: 'numbers', title: '序号',width: 50, fixed: 'left'},
                {field: 'nameMan', title: '男方姓名', width: 120},
                {field: 'certNumMan', title: '男方证件号', minWidth: 150},
                {field: 'nameWoman', title: '女方姓名', width: 120},
                {field: 'certNumWoman', title: '女方证件号', minWidth: 150},
                {field: 'certNo', title: '结婚证/离婚证字号', minWidth: 150},
                {field: 'opTypeText', title: '婚姻登记业务类型', width: 160, hide:true},
                {field: 'deptName', title: '登记机关名称', width: 180, hide:true},
                {field: 'opDate', title: '登记日期', width: 120, hide:true},
                {fixed: 'right', title: '操作', toolbar: '#czTpl', width: 80}
            ]],
            data: [],
            page: true,
            done: function () {
                setHeight();
            }
        });

        //监听婚姻登记行工具事件
        table.on('tool(hydjxxTable)', function (obj) {
            var data = obj.data;
            form.val('hydjxxForm', data);
            //小弹出层
            layer.open({
                title: '婚姻登记信息',
                type: 1,
                area: ['960px', '400px'],
                // btn: ['关闭'],
                content: $('#popupTwoRows')
                , yes: function (index, layero) {
                }
                , cancel: function () {
                    //右上角关闭回调
                    $("#hydjxxForm")[0].reset();
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
            obj.paramDTOList=[];
            list.certNum = $('#cert_num').val();
            list.name = $('#name').val();
            obj.paramDTOList.push(list);
            //分页
            obj.loadpage=true;


            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/civil/marriageQuery",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    var content = [];
                    if(data && data.content){
                        content = data.content;
                    }
                    removeModal();
                    table.reload("hydjxxTable", {
                        data: content
                        , done: function () {
                            setHeight();
                        }
                    });
                },
                error: function (xhr, status, error) {
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



