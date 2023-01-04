var table;
var initCount = 0;
/**
 * 移交单查询台账
 */
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        laydate = layui.laydate;
    table = layui.table;
    var BASE_URL = '/realestate-register-ui/rest/v1.0';
    /**
     * 渲染时间
     */
    lay('.test-item').each(function () {
        laydate.render({
            elem: '#yjsj'
            , type: 'date'
            , range: '到'
            , format: 'yyyy-MM-dd'
            , trigger: 'click'
        });
    });
    /**
     * 加载Table数据列表
     * 需求56096 【合肥】 移交单台账修改为进入系统默认不查询数据
     */
    // addModel();
    table.render({
        elem: '#yjdTable',
        id: 'yjdTable',
        toolbar: '#toolbar',
        title: '归档信息',
        defaultToolbar: [],
        // url: BASE_URL + "/yjd/page",
        where: {version: $("#version").val()},
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        limits: commonLimits,
        cols: [[
            {type: 'numbers', fixed: 'left',title:'序号'},
            {field: 'yjdid', title: 'yjdid', hide: true},
            {field: 'yjdbh', title: '移交单编号', minWidth: 120},
            {field: 'yjr', title: '移交人'},
            {
                field: 'yjsj', title: '移交时间',
                templet: function (d) {
                    return format(d.yjsj);
                }
            },
            {field: 'jsr', title: '接收人', edit: 'text'},
            {fixed: 'right', title: '移交清册', toolbar: '#barDemo', width: 130}
        ]],
        data: [],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
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
            var searchHeight = 131;
            setTableHeight(searchHeight);
            removeModel();
        }
    });


    $('.bdc-table-box').on('mouseenter', 'td', function () {
        $('.layui-table-grid-down').on('click', function () {
            setTimeout(function () {
                $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
            }, 20);
        });
    });

    if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
        //监听input点击
        $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
            $(this).siblings('.layui-input').val('');
        });

        $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
            $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
        }).on('blur', function () {
            var $this = $(this);
            setTimeout(function () {
                $this.siblings('.layui-icon-close').addClass('bdc-hide');
            }, 150)
        });
    }
    // 弹框页面头工具栏事件
    table.on('toolbar(viewFilter)', function (obj) {
        switch (obj.event) {
            case 'printYjd':
                var yjdbh = $("#yjdbh_View").html();
                printYjd(yjdbh);
                break;
        }
    });

    //监听页面的行工具事件
    table.on('tool(yjdgxtbale)', function (obj) {
        if (obj.event === 'delgdxx') {
            // 删除归档信息
            delYjd(obj.data.gxid,obj.data.yjdid);
        }
    });

    //监听页面的行工具事件
    table.on('tool(yjdTable)', function (obj) {
        if (obj.event === 'yjdXm') {
            // 查看移交单详情
            printYjd(obj.data.yjdid, obj.data.yjdbh);
        }else if (obj.event === 'yjdJy') {
            // 检验交单详情
            checkYjd(obj.data.yjdid);
        }
    });

    //监听单元格编辑
    table.on('edit(yjdTable)', function (obj) {
        updateYjdJsr(obj);
        // var value = obj.value //得到修改后的值
        //     ,data = obj.data //得到所在行所有键值
        //     ,field = obj.field; //得到字段
        // layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
    });

    /**
     * 监听排序事件
     */
    table.on('sort(yjdTable)', function (obj) {
        table.reload('yjdTable', {
            url: BASE_URL + "/yjd/page",
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });

    });

    document.onkeydown = function (event) {
        var code = event.keyCode;
        if (code == 13) {
            $('#search').click();
        }
    }
    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询内容
        var obj = {};
        var count = 0;
        $(".search").each(function (i) {
            var value = $(this).val();
            if (!isNullOrEmpty(value)) {
                count += 1;
            }
            var name = $(this).attr('name');
            if (name == "yjsj" && !isNullOrEmpty(value)) {
                var yjsj = value.split("到");
                obj["qssj"] = yjsj[0].trim();
                obj["jzsj"] = yjsj[1].trim();
            } else {
                obj[name] = value;
            }
        });

        if (0 == count) {
            layer.alert("请输入查询条件！", {
                title: '提示'
            });
            return false;
        }
        addModel();
        // 重新请求
        table.reload("yjdTable", {
            url: BASE_URL + "/yjd/page",
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });

    /**
     * 点击查询
     */
    $('#yjdqcSearch').on('click', function () {
        // 获取查询内容
        var obj = {};
        var count = 0;
        $(".qcsearch").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });
        // 重新请求
        table.reload("yjdgxtbale", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });

    /**
     * 重置
     */
    $('#reset').on('click', function () {
        $(".search").val('');
    });

    /**
     * 重置
     */
    $('#yjdqcReset').on('click', function () {
        $(".qcsearch").val('');
    });

    /**
     * 重新加载数据
     */
    window.reload = function () {
        table.reload("yjdTable", {
            url: BASE_URL + "/yjd/page",
            where: []
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };
});

function checkYjd(yjdid){
    layer.open({
        type: 1,
        title: "移交单清册",
        content: $('#checkYjd'),
        area: ['960px', '500px'],
        cancel: function () {
            $("#checkYjd").css('display', 'none');
        },
        success: function (layero, index) {
            viewTableRender(yjdid);
        }
    });
}

/**
 * 已选择窗口数据渲染
 */
function viewTableRender(yjdid) {
    table.render({
        elem: '#yjdgxtbale',
        id: 'yjdgxtbale',
        url: BASE_URL + "/yjd/checkYjd/"+yjdid,
        title: '移交单清册',
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        defaultToolbar: [],
        cols: [[
            {field: 'xh',  title: '序号',width: 50,
                templet: function (data) {
                if(data.sxh) {
                    return data.sxh;
                } else {
                    return data.LAY_INDEX;
                }

                }},
            {field: 'xmid', title: 'xmid', hide: true},
            {field: 'gxid', title: 'gdxxid', hide: true},
            {field: 'yjdid', title: 'gzlslid', hide: true},
            {field: 'slbh', title: '受理编号'},
            {field: 'qlr', title: '权利人'},
            {field: 'bdcdyh', title: '不动产单元号'},
            {field: 'bdcqzh', title: '不动产权证号'},
            {field: 'zl', title: '坐落'},
            {fixed: 'right', title: '操作', toolbar: '#delBarDemo', width: 80}
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
        done: function (data) {
            //if(data.data.length == 0){
                //layer.closeAll();
                //layer.alert("暂无数据！");
            //}
            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
        }
    });
    reloadTable(yjdid);
}

function delYjd(gxid,yjdid){
    layui.layer.confirm("删除数据无法恢复，确认删除？", {
        title: "提示",
        btn: ["确认", "取消"],
        btn1: function (index) {
            $.ajax({
                url: BASE_URL + "/yjd/delGdxx/"+gxid,
                type:'delete',
                success: function (data) {
                    layer.closeAll();
                    layer.alert("删除成功！");
                },
                error: function (e) {
                    showErrorInfo();
                }
            })
            layer.close(index);
        },
        btn2: function (index, window) {
            layer.close(index);
        }
    })
}

function reloadTable(yjdid){
    table.reload("yjdgxtbale", {
        url: BASE_URL + "/yjd/checkYjd/"+yjdid,
        page: {
            curr: 1  //重新从第 1 页开始
        }
    });
}

