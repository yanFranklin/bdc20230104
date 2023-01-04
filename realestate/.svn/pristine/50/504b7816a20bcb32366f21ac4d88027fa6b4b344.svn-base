/**
 *  获取竣工验收备案附件信息列表
 */
var gzlslid = $.getUrlParam("processInsId");
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var reverseList = ['zl'];
layui.use(['jquery', 'element', 'form', 'table', 'laydate', 'formSelects'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var formSelects = layui.formSelects;

    form.render();

    // 当前页数据
    var currentPageData = new Array();
    $(function () {
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        // 加载Table
        table.render({
            elem: '#bdcdyTable',
            toolbar: '#toolbarDemo',
            title: '获取竣工验收备案附件信息列表',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'MODEL_NAME', title: '模板名称', width: 180, align: 'center'},
                {field: 'CREDIT_CODE', minWidth: 200, title: '企业统一信用代码'},
                {field: 'PROJECT_CODE', minWidth: 250, title: '项目代码'},
                {field: 'CONTRACTOR', minWidth: 200, title: '企业名称'},
                {field: 'BSNUM', minWidth: 250, title: '业务名称'},
                {field: 'PROJECT_NAME', title: '项目名称', width: 200, align: 'center'},
                {fixed: 'right', title:'操作', templet:"#barAction2", width: 150}
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
                reverseTableCell(reverseList);
                // 控制按钮权限
                if(moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
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
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            var key = formSelects.value('select07', 'val');

            if (0 == count) {
                layer.alert("<div style='text-align: center'>请输入必要查询条件！</div>", {title: '提示'});
                return false;
            }

            // 获取查询参数
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });

            addModel();
            // 重新请求
            table.reload("bdcdyTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/gx/shucheng/queryJgys/list"
                , where: obj
                , page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                    //saveDetailLog("DYCX","抵押查询",obj);
                }
            });
        }

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

        //监听工具条
        table.on('tool(bdcdyTable)', function (obj) {
            var data = obj.data;

            if (obj.event === 'xxck') {
                var downUrl = data.DOC_ID;
                window.open(getContextPath() + "/rest/v1.0/gx/shucheng/download/file?downUrl="+downUrl);
            }
            if (obj.event === 'fjxz') {
                fjxz(data);
            }
        });

    });

    function fjxz(data){
        addModel();
        $.ajax({
            url:"/realestate-inquiry-ui/rest/v1.0/gx/shucheng/Savefjxx",
            type: "GET",
            data: {gzlslid: gzlslid, downUrl: data.DOC_ID},
            contentType: 'application/json',
            success: function (data) {
                if (data.code == '1') {
                    successMsg("下载成功");
                } else {
                    warnMsg(data.fail.message);
                }
                removeModal();
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }
});



