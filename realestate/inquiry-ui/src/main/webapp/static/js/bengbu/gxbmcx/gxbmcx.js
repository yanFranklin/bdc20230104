
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

var reverseList = ['zl'];
// 分页重置查询参数
var searchParam =[];
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
        // 加载Table
        table.render({
            elem: '#gxbmcxTable',
            toolbar: '#toolbarDemo',
            title: '共享部门查询列表',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},

                {field: 'zl', minWidth: 250, title: '坐落'},
                {field: 'bdcdyh', minWidth: 250, title: '不动产单元号'},

                {field: 'djyy',  minWidth: 120,title: '登记原因'},
                {field: 'cqzh',  minWidth: 250, title: '产权证号'},
                {field: 'djsj',  minWidth: 200,title: '登记时间'},
                {field: 'qlrmc', title: '权利人', minWidth: 100, align: 'center'},
                {field: 'qlrzjh', title: '权利人证件号', minWidth: 200, align: 'center'},
                {field: 'ywrmc', title: '义务人', minWidth: 100, align: 'center'},
                {field: 'ywrzjh', title: '义务人证件号', minWidth: 200, align: 'center'},
                {field: 'bdclx',  minWidth: 120,title: '不动产类型'},
                {field: 'slbh',  minWidth: 120,title: '受理编号'},
                {field: 'fwbh',  minWidth: 200,title: '房屋编号'},
                {field: 'ycqzh',  minWidth: 250, title: '原产权证号'},
                {field: 'ytdzh',  minWidth: 250,title: '原土地证号'},
                {field: 'ghyt',  minWidth: 120,title: '规划用途'},
                {field: 'qszt',  minWidth: 120,title: '权属状态',fixed: 'right'},
                {field: 'ajzt',  minWidth: 120,title: '案件状态',fixed: 'right'},
                {
                    field: 'bdcdyZtDTO', width: 100, title: '限制状态', fixed: 'right',
                    templet: function (d) {
                        return formatXzzt(d.bdcdyZtDTO);
                    }
                },
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
                reverseTableCell(reverseList);
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

            searchParam = obj;
            searchParam.cqzhmh = $("#cqzhmh").val();
            searchParam.zjhmh = $("#zjhmh").val();
            addModel();
            // 重新请求
            table.reload("gxbmcxTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/gxbmcx/listGxbmcxByPage"
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
                }
            });
        }


        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

    });

    // 监听表格操作栏按钮
    table.on('toolbar(gxbmcxTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'exportExcel':
                if (searchParam.length == 0){
                    // layer.alert("请先查询数据！", {title: '提示'});
                    warnMsg("请选择需要导出Excel的数据行！");
                    return
                }
                if (checkStatus.data.length  > 0) {
                    exportExcel(checkStatus.data, obj.config.cols[0]);
                } else {
                    exportAllExcel(checkStatus.data, obj.config);
                }
                break;
        }
    });

    //监听工具条
    table.on('tool(gxbmcxTable)', function (obj) {
        var data = obj.data;

        if (obj.event === 'djls') {
            window.open("/realestate-inquiry-ui/view/lsgx/lsgx.html?bdcdyh=" + data.bdcdyh);
            saveDetailLog("BMGXCXLSGX", "部门共享查询-查看登记历史", data);
        }
    });

    // 导出Excel
    // 通过隐藏form提交方式，避免ajax方式不支持下载文件
    function exportExcel(d, cols) {
        if ($.isEmptyObject(d)) {
            warnMsg("请选择需要导出Excel的数据行！");
            return;
        }

        // 标题
        var showColsTitle = new Array();
        // 列内容
        var showColsField = new Array();
        // 列宽
        var showColsWidth = new Array();
        for (var index in cols) {
            if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
                showColsTitle.push(cols[index].title);
                showColsField.push(cols[index].field);
                if (isNullOrEmpty(cols[index].width)) {
                    showColsWidth.push(30);
                } else {
                    showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                }
            }
        }

        // 设置Excel基本信息
        $("#fileName").val('共享部门查询Excel表');
        $("#sheetName").val('共享部门查询Excel表');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);

        var data = new Array();
        $.each(d, function (key, value) {
            data.push(value);
        })
        for (var i = 0; i < data.length; i++) {
            data[i].bdcdyZtDTO = formatXzzt(data[i].bdcdyZtDTO).replace(/<span\s*[^>]*>(.*?)<\/span>/ig,"$1");

            data[i].xh = i + 1;
        }
        $("#data").val(JSON.stringify(data));
        $("#form").submit();
    }

    // 导出所有查询结果Excel
    function exportAllExcel(data, obj) {
        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要导出Excel的数据行!');
    }


})

// 格式化限制状态
function formatXzzt(data) {
    var value = '';
    if (data != undefined) {
        if (data.cf === true) {
            //南通样式
            value += '<span class="bdc_change_red bdc-cf" style="color:#EE0000;">查封 </span>';
            //value += '<span class="" style="color:#EE0000;">查封 </span>';
        }
        if (data.ycf === true) {
            value += '<span class="bdc-ycf" style="color:#ee717a;">预查封 </span>';
        }
        if (data.dya === true || data.zjgcdy === true) {
            value += '<span class="bdc-dya" style="color:#FF00FF;">抵押 </span>';
        }
        if (data.ydya === true) {
            value += '<span class="" style="color:#aacd06;">预抵押 </span>';
        }
        if (data.yy === true) {
            value += '<span class="bdc-yy" style="color:#ef7106;">异议 </span>';
        }
        if (data.yg === true) {
            value += '<span class="" style="color:#ffb60c;">预告 </span>';
        }
        if (data.dyi === true) {
            value += '<span class="" style="color:#855e6e;">地役 </span>';
        }
        if (data.sd === true) {
            value += '<span class="bdc-sd" style="color:#e50971;">锁定 </span>';
        }
        if (data.jzq === true) {
            value += '<span class="bdc-jzq" style="color:#13b1c4;">居住 </span>';
        }
        if (value === '') {
            value += '<span class="" style="color:#32b032;">正常</span>';
        }
    }
    return value;
}





