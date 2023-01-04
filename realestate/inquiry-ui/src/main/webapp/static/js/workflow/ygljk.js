layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var form = layui.form,
        $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;

    var sjid = $.getUrlParam("sjid");
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    var unitTableTitle = [
        {type: 'checkbox', fixed: 'left'},
        {type: 'numbers', fixed: 'left', title: '序号', width: 40},
        {field: 'jkid', title: '接口id', hide: true},
        {field: 'jkmc', sort: true, title: '接口名称', align: 'center', width: 400, style: 'text-align:left'},
        {
            field: 'jklx', title: '接口类型', align: 'center', width: 120,
            templet: function (d) {
                return formatJklx(d.jklx);
            }
        },
        {field: 'qqfs', title: '请求方式', align: 'center', width: 120},
        {field: 'jksm', title: '接口说明', align: 'center', style: 'text-align:left', minWidth: 200},
        {field: 'yymc', title: '应用名称', align: 'center', width: 130},
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
        {field: 'cz', title: '操作', align: 'center', width: 120, toolbar: '#barDemo', fixed: 'right'}
    ]
    table.render({
        elem: '#ygljkTable',
        url: '/realestate-inquiry-ui/rest/v1.0/gzl/jk/page?sjid=' + sjid + "&gljk=1",
        title: '接口列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        even: true,
        cols: [unitTableTitle],
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
            setTableHeight();
        }
    });

    // 设置列表高度
    function setTableHeight(searchHeight) {
        $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

        if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
            $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
        } else {
            $('.layui-table-body').height($('.bdc-table-box').height() - 80);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 97);
        }
    }

    //监听工具条
    table.on('tool(ygljkTable)', function (obj) {
        var data = obj.data;
        if (isNullOrEmpty(data.jkid)) {
            warnMsg("未查询到子规则信息，无法查看！");
            return;
        }
        switch (obj.event) {
            case 'jkxq':
                layer.open({
                    type: 2,
                    area: ['960px', '400px'],
                    fixed: true, //不固定
                    title: "新增工作流接口",
                    maxmin: true,
                    content: getContextPath() + "/view/workflow/editGzljk.html?jkid=" + data.jkid,
                    btnAlign: "c"
                });
                break;
            case 'up':
                //顺序上移
                changeSxh('up', data.jkid);
                break;
            case 'down':
                //顺序下移
                changeSxh('down', data.jkid);
                break;
        }
    });

    function formatJklx(jklx) {
        if (isNullOrEmpty(jklx)) {
            return "";
        } else if (jklx === 1) {
            return '<span class="" style="color:limegreen;">一般接口</span>';
        } else if (jklx === 2) {
            return '<span class="" style="color:red;">登簿接口</span>';
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

    function changeSxh(type, jkid) {
        addModel();
        $.ajax({
            url: BASE_URL + '/gzl/sxh',
            type: "GET",
            data: {sjid: sjid, jkid: jkid, type: type},
            success: function (result) {
                removeModal();
                table.reload("ygljkTable", {
                    url: '/realestate-inquiry-ui/rest/v1.0/gzl/jk/page?sjid=' + sjid + '&gljk=1',
                    page: {
                        curr: 1  //重新从第 1 页开始
                    }
                });
            },
            error: function (xhr) {
                delAjaxErrorMsg(xhr);
            }
        });
    }
});
