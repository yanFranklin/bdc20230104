/**
 * author: sunyuzhaung
 * describe: 对比页面列表
 */
var reverseList = ['zl'];
$(function () {
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
    // 获取参数
    var processInsId = $.getUrlParam('processInsId');
    // 表单ID
    var formStateId = $.getUrlParam('formStateId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');

    /**
     * 获取字典
     */
    var zdData;
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/qlxx/zd',
        type: "GET",
        async: false,
        dataType: "json",
        timeout: 10000,
        success: function (data) {
            zdData = data;
        }
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


    layui.config({
        base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
    }).extend({
        formSelects: 'formSelects-v4'
    });
    layui.use(['form', 'table', 'jquery', 'layer','formSelects'], function () {
        var form = layui.form;
        var table = layui.table;
        var layer = layui.layer;
        /**
         * 加载Table数据列表
         */
        table.render({
            elem: '#dbxxTable',
            id: 'dbxxTable',
            title: '对比信息列表',
            defaultToolbar: [],
            url: '/realestate-register-ui/rest/v1.0/plblxx/getQlxxList/' + processInsId,
            // request: {
            //     limitName: 'size' //每页数据量的参数名，默认：limit
            // },
            cellMinWidth: 80,
            even: true,
            limits: commonLimits,// 每页数据量
            cols: [[
                {checkbox: true, fixed: true},
                {field: 'slbh', title: '受理编号', minWidth: 250},

                {field: 'bdcdyh', title: '不动产单元号', minWidth: 350},
                {field: 'zl', title: '坐落', minWidth: 350},
                {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 65}
            ]],
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

                reverseTableCell(reverseList);

                // 获取表单控制权限
                if (readOnly || !isNullOrEmpty(formStateId)) {
                    getStateById(readOnly, formStateId, "bdcFzjlList");
                }
            }
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



        // 监听按钮事件
        form.on("submit(search)", function (data) {
            tableReload("dbxxTable", data.field);
            return false;
        });



        //监听行工具事件
        table.on('tool(dataTable)', function (obj) {
            var data = obj.data;
            var url = "/realestate-register-ui/view/xxbl/dbym.html?processInsId="+ processInsId+"&type=xxbl&xmid=" + data.xmid;

            var index = layer.open({
                title: ['', 'font-size:16px;font-weight: 700;'],
                type: 2,
                area: ['100%', '100%'],
                content: url,
                cancel: function () {
                    //刷新页面

                }
            });
            layer.full(index);
        });
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });

        tableReload("dbxxTable", obj);

    });
});



