/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 初始化表格，工具栏事件
 */
$(function () {
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
    // 获取参数
    var processInsId = $.getUrlParam('processInsId');

    layui.use(['form', 'table', 'jquery'], function () {
        var form = layui.form;
        var table = layui.table;
        // 当前查询条件
        var searchData;
        // 定义table展示数据列
        var col = [[
            {
                field: 'qszt', title: '状态',
                templet: function (d) {
                    if (d && qsztHistory == d.qszt) {
                        return '<p style="color:#f54743">已注销</p>';
                    } else {
                        return '<p style="color:#10a54a">正常</p>';
                    }
                }
            },
            {
                field: 'bdcqzh', title: '不动产权证', minWidth: 270,
                templet: function (d) {
                    if (isNullOrEmpty(d.bdcqzh)) {
                        verifyBdcqzhNotNull = false;
                        return "";
                    } else {
                        return d.bdcqzh;
                    }
                }
            },
            {field: 'qzysxlh', title: '权证印刷序列号', minWidth: 130},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
            {field: 'zl', title: '坐落', minWidth: 250},
            {field: 'qlr', title: '权利人', minWidth: 200},
            {field: 'djyy', title: '登记原因', minWidth: 120},
            {field: 'zslxmc', title: '证书类型', minWidth: 90},
            {fixed: 'right', title: '操作', toolbar: '#barDemo'}
        ]];

        // 设置字符过多，省略样式
        var reverseList = ['zl'];
        tableRender();

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

        form.on("submit(search)", function (data) {
            searchData = data;
            tableReload("bdcDzqzTable", data.field);
            return false;
        });

        //监听行工具事件
        table.on('tool(bdcDzqzTable)', function (obj) {
            var data = obj.data;
            var url = getContextPath() + "/changzhou/dzzz/priviewDzzz.html?zsid=" + data.zsid;
            var title = '电子签章详情';
            var layerModel;

            layerModel = layer;

            layerModel.open({
                title: [title, 'font-size:16px;font-weight: 700;'],
                type: 2,
                area: ['100%', '100%'],
                resize: true,
                content: url,
                id: 'zsListCs',
                cancel: function (index, layero) {
                    refreshTable();
                    layerModel.close(index);
                    return false;
                }
            });

        });

        //表格渲染
        function tableRender() {
            /**
             * 加载Table数据列表
             */
            var url = '/realestate-register-ui/rest/v1.0/zsxx/list/' + processInsId;
            table.render({
                elem: '#bdcDzqzTable',
                id: 'bdcDzqzTable',
                toolbar: '#toolbarDemo',
                title: '证书列表',
                defaultToolbar: [],
                url: url,
                request: {
                    limitName: 'size', //每页数据量的参数名，默认：limit
                    loadTotal: true,
                    loadTotalBtn: false
                },
                cellMinWidth: 80,
                even: true,
                limits: commonLimits,
                cols: col,
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

                }
            });
        }


        // 重新加载当前页的表格数据
        window.refreshTable = function () {
            console.log("重新加载当前页的表格数据");
            // 当前页码
            var page = $('.layui-laypage-curr em:last-child').html();
            // 关闭时自动刷新列表
            if (isEmptyObject(searchData)) {
                tableReload("bdcDzqzTable", null, page)
            } else {
                tableReload("bdcDzqzTable", searchData.field, page);
            }

        }


    });

    // 表格重载
    function tableReload(table_id, postData, page) {
        layui.use(['table'], function () {
            var table = layui.table;
            table.reload(table_id, {
                where: postData
                , page: {
                    curr: page,
                    limits: [10, 20, 50, 100, 200, 500]
                }
            });
        });
    }

});

