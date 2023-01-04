/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 初始化表格，工具栏事件
 */

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

        /**
         * 获取字典
         */
        // var zdData;
        // $.ajax({
        //     url: '/realestate-register-ui/rest/v1.0/qlxx/zd',
        //     type: "GET",
        //     async: false,
        //     dataType: "json",
        //     timeout: 10000,
        //     success: function (data) {
        //         zdData = data;
        //     }
        // });

        //点击高级查询
        $('#seniorSearch').on('click', function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');
            $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);

            $('.layui-table-body').height($('.bdc-table-box').height() - 129);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);
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
                    // $nowBtnMore.css({top: tableTop + 26 -scrollTop,right: 20});
                    $nowBtnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
                } else {
                    // $nowBtnMore.css({top: tableTop -scrollTop - $nowBtnMore.height(),right: 20});
                    $nowBtnMore.css({top: tableTop - scrollTop - $nowBtnMore.height(), left: tableLeft - 30});
                }
            }
        });
        //点击表格中的更多
        $('.bdc-table-box').on('click', '.bdc-more-btn', function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            event.stopPropagation();
            $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
            var $btnMore = $(this).siblings('.bdc-table-btn-more');
            if ($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight) {
                // $btnMore.css({top: $(this).offset().top + 26 -scrollTop,right: 20});
                $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
            } else {
                // $btnMore.css({top: $(this).offset().top -scrollTop - $btnMore.height(),right: 20});
                $btnMore.css({top: tableTop - scrollTop - $btnMore.height(), left: tableLeft - 30});
            }
            $('.bdc-table-btn-more').hide();
            $btnMore.show();
        });
        //点击更多内的任一内容，隐藏
        $('.bdc-table-btn-more a').on('click', function () {
            $(this).parent().hide();
        });
        //点击页面任一空白位置，消失
        $('body').on('click', function () {
            $('.bdc-table-btn-more').hide();
        });


        $('.bdc-table-box').on('mouseenter', 'td', function () {
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                }, 20);
            });
        });

        /**
         * 加载Table数据列表
         */
        table.render({
            elem: '#cxsqsTable',
            id: 'cxsqsTable',
            toolbar: '#toolbarDemo',
            title: '查询申请书',
            defaultToolbar: [],
            url: '/realestate-inquiry-ui/rest/v1.0/cxsqs/page',
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'numbers', fixed: 'left'},
                {field: 'cxsldjbh', title: '查询受理编号', minWidth: 100},
                {field: 'qlrmc', title: '权利人名称', minWidth: 250},
                {field: 'qlrzjh', title: '权利人证件号', minWidth: 250},
                {field: 'cxry', title: '查询受理人', minWidth: 100},
                {
                    field: 'cxrq', title: '查询日期', minWidth: 100,
                    templet: function (d) {
                        return Format(formatDate(d.cxrq), 'yyyy年MM月dd日');
                    }
                },
                {field: 'sqsid', title: '查询申请书ID', hide: true},
                {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 200}
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
                setHeight();
            }
        });


        // 监听按钮事件
        form.on("submit(search)", function (data) {
            tableReload("cxsqsTable", data.field);
            return false;
        });


        //监听行工具事件
        table.on('tool(cxsqsTable)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'cxsqs':
                    cxsqs(data.sqsid);
                    break;
                case 'fczm':
                    fczm(data, '');
                    break;
                case 'fcda':
                    fcda(data, '');
                    break;
            }
        });
    });

});
