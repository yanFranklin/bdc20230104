/**
 * author: <a href="mailto:huangjian@gtmap.cn>huangjian</a>
 * version 1.0.0  2019/05/22
 * describe: 抵押单元信息查询
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
// 用户IP地址
var ipaddress;
// var zdList = getMulZdList("bdclx");
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
            title: '抵押信息列表',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'xmid', title: '项目编号', width: 200, align: 'center', hide: true},
                {field: 'bdcqzh', minWidth: 280, title: '不动产权证号'},
                {field: 'yxtcqzh', minWidth: 250, title: 'TT系统证号'},
                {field: 'bdcdyh', minWidth: 280, title: '不动单元号'},

                {field: 'zl', minWidth: 250, title: '坐落'},
                {field: 'qlr', title: '权利人', width: 200, align: 'center'},
                {field: 'qlrzjh', title: '权利人证件号', width: 250, align: 'center'},
                {field: 'ywr', title: '义务人', width: 200, align: 'center'},
                {field: 'ywrzjh', title: '义务人证件号', width: 250, align: 'center'},
                {
                    field: 'djsj', width: 180, title: '登记时间',
                    templet: function (d) {
                        return formatDate(d.djsj);
                    }
                },
                {
                    field: 'qszt', width: 100, title: '权属状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatQszt(d.qszt);
                    }
                },
                {
                    field: 'bdcdyZtDTO', width: 100, title: '限制状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatXzzt(d.bdcdyZtDTO);
                    }
                }

                // {title: '查看', fixed: 'right', renderer: 'detailView', toolbar: '#barDemo', width: 100}
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
                url: "/realestate-inquiry-ui/rest/v1.0/dya/listDyaByPage"
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

    });

    // 保存记录操作信息
    function saveDetailLog(logType, viewTypeName, data){
        var json = JSON.parse(JSON.stringify(data));
        json.logType = logType;
        json.ipaddress = ipaddress;
        json.viewTypeName = viewTypeName;
        saveLogInfo(json);
    }

});



