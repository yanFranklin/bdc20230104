layui.config({
    base: '../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'jquery', 'laytpl', 'response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        laytpl = layui.laytpl,
        response = layui.response,
        form = layui.form;
    // 当前页数据
    var currentPageData = [];
    // 保存当前选中的taskId
    var jsonArray = [];

    var BASE_URL = '/realestate-portal-ui/rest/v1.0/task/bdcYjd';

    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode === 13) {
            $("#search").click();
        }
    });

    /**
     * 加载Table数据列表
     */
    var reverseList = ['bdcdyh', 'zl'];
    table.render({
        elem: '#bdcYjdTable',
        title: '不动产移交单列表',
        defaultToolbar: ['filter'],
        url: BASE_URL + '/yjdTaskData',
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [[
            // {type: 'checkbox', fixed: 'left', width: 60},
            {field: 'id', title: 'id', hide: true},
            {field: 'processInstanceId', title: '工作流实例ID', hide: true},
            {field: 'yjdh', title: '移交单编号'},
            {field: 'slbh', title: '受理编号'},
            {field: 'qlr', title: '权利人'},
            {field: 'zl', title: '坐落'},
            {field: 'taskAssName', title: '操作人'}
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            // 设置选中行
            if (res.content && res.content.length > 0 && jsonArray && jsonArray.length > 0) {
                res.content.forEach(function (v) {
                    $.each(jsonArray, function (key, value) {
                        if (value.taskId == v.taskId) {
                            v.LAY_CHECKED = true;
                        }
                    })
                });
            }

            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function (res, curr, count) {
            currentPageData = res.data;

            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            reverseTableCell(reverseList);
            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    // 记录选中事件
    table.on('checkbox(bdcYjdTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;

        if (obj.checked) {
            //.增加已选中项
            for (var index in data) {
                jsonArray.push(data[index]);
            }
        } else {
            //.删除
            for (var index in jsonArray) {
                data.forEach(function (v) {
                    if (jsonArray[index].taskId == v.taskId) {
                        jsonArray.splice(index, 1);
                    }
                });
            }
        }

        jsonArray = $.unique(jsonArray);
    });

    $('.bdc-table-box').on('mouseenter', 'td', function () {
        if ($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1) {
            $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
        }
        $('.layui-table-grid-down').on('click', function () {
            setTimeout(function () {
                $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
            }, 20);
        });
    });

    /**
     * 点击查询新方法
     */
    $('#search').on('click', function () {
        // 清空选择记录
        jsonArray = [];

        var obj = {};
        obj["dyzt"] = 1;
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });
        console.info(obj);
        // 重新请求
        table.reload("bdcYjdTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });


    /**
     * 重新加载数据
     */
    window.reload = function () {
        var obj = {};
        obj["dyzt"] = 1;
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });
        table.reload("bdcYjdTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

});