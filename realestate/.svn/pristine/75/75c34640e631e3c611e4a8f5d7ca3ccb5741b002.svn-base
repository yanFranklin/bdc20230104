/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/17
 * @description 省公安厅-公民基本信息在线比对
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
            elem: '#gmjbxxTable',
            toolbar: '#toolbarDemo',
            title: '公民基本信息在线比对',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'exist', title: '是否存在', width: '25%'},
                {field: 'xm', title: '姓名', width: '25%'},
                {field: 'gmsfhm', title: '身份证号', width: "45%"}
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
            var obj = {};
            obj.sfzhList=[];
            obj.slbh = $('#slbh').val();
            if(isNotBlank($('#sfzh').val())){
                obj.sfzhList.push($('#sfzh').val());
            }
            obj.loadpage=true;

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/provincialPublicSecurityDepartment/policeRealName",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    var content =[];
                    if(data && data.content){
                        // datalist = JSON.parse(data.content[0]).data.cxjg.datalist;
                        content = data.content;
                    }
                    removeModal();
                    table.reload("gmjbxxTable", {
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



