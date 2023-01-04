/**
 * author: caolu
 * date: 2022/4/22
 * version 3.0.0
 * describe: 分层分户图js
 */
//获取页面版本
var version = getQueryString("version");

layui.use(['form', 'table', 'jquery'], function () {
    var form = layui.form;
    var table = layui.table;

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

        /**
         * 加载Table数据列表
         */
        table.render({
            elem: '#djbTable',
            id: 'djbTable',
            toolbar: '#toolbarDemo',
            title: '登记簿台账',
            defaultToolbar: [],
            url: '/realestate-register-ui/rest/v1.0/bdcdy/xmOrYxm/' + processInsId,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            // cellMinWidth: 80,
            even: true,
            cols: [[
                {type: 'numbers', fixed: 'left'},
                {field: 'xmid', title: 'xmid', minWidth: 100, hide: true},
                {field: 'ytdzh', title: '原土地证号', minWidth: 100, hide: true},
                {field: 'ybdcdyh', title: '原不动产单元号', minWidth: 100, hide: true},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
                {field: 'zl', title: '坐落', minWidth: 250},
                {
                    field: 'qllx', title: '权利类型', minWidth: 150,
                    templet: function (d) {
                        if (zdData && zdData.qllx) {
                            for (var index in zdData.qllx) {
                                if (zdData.qllx[index].DM == d.qllx) {
                                    return zdData.qllx[index].MC;
                                }
                            }
                        } else {
                            return d.qllx;
                        }
                    }, hide: true
                },
                {field: 'bdcqzh', title: '不动产权证号', minWidth: 250, hide: true},
                {field: 'bdclx', title: '不动产类型', hide: true},
                {field: 'gzlslid', title: 'gzlslid', hide: true},
                {field: 'qlrzjh', title: '权利人证件号', hide: true},
                {field:'cz', title: '操作', width: 200, templet: '#barDemo', fixed: 'right',}
            ]],
            text: {
                none: '未查询到数据'
            },
            autoSort: false,
            page: true,
            limits: commonLimits,
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

                // 设置字符过多，省略样式
                var reverseList = ['zl'];
                reverseTableCell(reverseList);


                // 获取表单控制权限
                if (readOnly || !isNullOrEmpty(formStateId)) {
                    getStateById(readOnly, formStateId, "bdcDjbList");
                }
            }
        });

        $('.bdc-table-box').on('mouseenter', 'td', function () {
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                }, 20);
            });
        });

        //监听滚动事件
        var scrollTop = 0,
            scrollLeft = 0;
        var tableTop = 0, tableLeft = 0;
        var $nowBtnMore = '';
        $(window).on('scroll', function () {
            scrollTop = $(this).scrollTop();
            scrollLeft = $(this).scrollLeft();

            if ($nowBtnMore != '') {
                if (tableTop + 26 + $nowBtnMore.height() < document.body.offsetHeight) {
                    $nowBtnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
                } else {
                    $nowBtnMore.css({top: tableTop - scrollTop - $nowBtnMore.height(), left: tableLeft - 30});
                }
            }
        });
        // 监听按钮事件
        form.on("submit(search)", function (data) {
            tableReload("djbTable", data.field);
            return false;
        });

        //监听行工具事件
        table.on('tool(dataTable)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'hst':
                    window.open(getContextPath() +"/view/djbxx/bdcDjbHst.html?bdcdywybh=" + data.bdcdywybh);
                    break;
            }
        });
    });

});
