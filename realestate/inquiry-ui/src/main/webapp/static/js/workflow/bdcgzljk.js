/**
 * @param null
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 工作流接口台账，新增删除查询操作
 * @date : 2022/4/7 14:26
 */
var form;
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'upload'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        upload = layui.upload;
    form = layui.form;

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    var url = BASE_URL + "/gzl/jk/page";

    //接口表格
    var unitTableTitle = [
        {type: 'checkbox', fixed: 'left'},
        {type: 'numbers', fixed: 'left', title: '序号', width: 60},
        {field: 'jkid', title: '接口id', hide: true},
        {field: 'jkmc', sort: true, title: '接口地址', align: 'center', width: 500, style: 'text-align:left'},
        {field: 'jksm', title: '接口说明', align: 'center', style: 'text-align:left', minWidth: 200},
        {
            field: 'jklx', title: '接口类型', align: 'center', width: 130,
            templet: function (d) {
                return formatJklx(d.jklx);
            }
        },
        {field: 'yymc', title: '应用名称', align: 'center', width: 130},
        {field: 'qqfs', title: '请求方式', align: 'center', width: 130},
        {
            field: 'sftb', title: '是否同步', align: 'center', width: 130,
            templet: function (d) {
                return formatSf(d.sftb);
            }
        },
        {
            field: 'sfhlyc', title: '是否忽略异常', align: 'center', width: 130,
            templet: function (d) {
                return formatSf(d.sfhlyc);
            }
        },
        {field: 'cjr', title: '配置人', align: 'center', width: 120}
    ]

    function formatJklx(jklx) {
        if (isNullOrEmpty(jklx)) {
            return "";
        } else if (jklx === 1) {
            return '<span class="" style="color:limegreen;">一般接口</span>';
        } else if (jklx === 2) {
            return '<span class="" style="color:orange;">登簿接口</span>';
        } else if (jklx === 3) {
            return '<span class="" style="color:blue;">办结接口</span>';
        } else if (jklx === 4) {
            return '<span class="" style="color:#d000ff;">退回接口</span>';
        } else if (jklx === 5) {
            return '<span class="" style="color:red;">删除接口</span>';
        } else {
            return jklx;
        }
    }

    function formatSf(data) {
        if (data && data === 1) {
            return '是';
        } else {
            return '否';
        }
    }

    var tableConfig = {
        id: 'gzid',
        toolbar: "#toolbar",
        defaultToolbar: ["filter"],
        cols: [unitTableTitle]
    }

    var reverseList = [''];
    table.render({
        elem: '#jkTable',
        toolbar: '#toolbar',
        title: '接口查询列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        data: [],
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [unitTableTitle],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        limits: [10, 50, 100, 200, 500],
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            reverseTableCell(reverseList);
            setHeight();
        }
    });
    //加载表格
    table.reload("jkTable", {
        url: url,
        page: {
            curr: 1  //重新从第 1 页开始
        }
    });

    //查询表单信息
    form.on("submit(search)", function (data) {
        // 重新请求
        table.reload("jkTable", {
            url: url,
            where: data.field
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        return false;
    });

    //头工具栏事件
    table.on('toolbar(jkTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addGzljk();
                break;
            case 'edit':
                editGzljk(checkStatus.data);
                break;
            case 'delete':
                deleteGzljk(checkStatus.data);
                break;
        }
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(jkTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);

        editGzljk(array);
    });

    //回车事件
    $("body").keydown(function () {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $('#search').click();
        }
    });
    //渲染应用名称下拉框
    renderYymc();

    function addGzljk() {
        layer.open({
            type: 2,
            area: ['960px', '500px'],
            fixed: false, //不固定
            title: "新增工作流接口",
            content: getContextPath() + "/view/workflow/editGzljk.html",
            btnAlign: "c"
        });
    }

    function editGzljk(jkdata) {
        if (!jkdata || jkdata.length === 0) {
            layer.alert("<div style='text-align: center'>请选择需要编辑的记录！</div>", {title: '提示'});
            return;
        }
        if (jkdata.length > 1) {
            layer.alert("<div style='text-align: center'>只能选择一条数据编辑！</div>", {title: '提示'});
            return;
        }
        layer.open({
            type: 2,
            area: ['960px', '500px'],
            fixed: false, //不固定
            title: "新增工作流接口",
            content: getContextPath() + "/view/workflow/editGzljk.html?jkid=" + jkdata[0].jkid,
            btnAlign: "c"
        });
    }

    function deleteGzljk(jkdata) {
        if (!jkdata || jkdata.length === 0) {
            layer.alert("<div style='text-align: center'>请选择需要删除的记录！</div>", {title: '提示'});
            return;
        }
        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            content: '确定要删除所选接口？',
            btn: ['确定', '取消'],
            skin: 'bdc-small-tips',
            btnAlign: 'c',
            yes: function () {
                addModel();
                var jkidList = [];
                $.each(jkdata, function (key, value) {
                    jkidList.push(value.jkid);
                });
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/gzl/jk",
                    type: 'DELETE',
                    data: JSON.stringify(jkidList),
                    contentType: 'application/json',
                    success: function (data) {
                        layer.close(deleteIndex);
                        removeModal();
                        ityzl_SHOW_SUCCESS_LAYER("删除成功");
                        $("#search").click();
                    },
                    error: function (xhr, status, error) {
                        removeModal();
                        delAjaxErrorMsg(xhr)
                    }
                })
            },
            btn2: function () {
                //取消
            }
        });


    }
});

function renderYymc() {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/gzl/app",
        type: 'GET',
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#yymc').append('<option value="' + item + '">' + item + '</option>');
                });
                form.render('select');
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    })
}