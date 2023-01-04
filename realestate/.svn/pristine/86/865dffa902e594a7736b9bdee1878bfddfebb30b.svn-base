/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 初始化表格，工具栏事件
 */

layui.use(['form', 'table', 'jquery'], function () {
    var form = layui.form;
    var table = layui.table;
    var $ = layui.jquery;

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

        // 获取所有的数据
        var allData;
        $.ajax({
            url: BASE_URL + '/bdcdy/list/' + processInsId + '/all',
            type: "GET",
            dataType: "json",
            timeout: 10000,
            success: function (data) {
                allData = data;
            }
        });

        getXmxx();
        /**
         * 获取字典
         */
        // var zdData;
        // $.ajax({
        //     url: BASE_URL + '/qlxx/zd',
        //     type: "GET",
        //     async: false,
        //     dataType: "json",
        //     timeout: 10000,
        //     success: function (data) {
        //         zdData = data;
        //     }
        // });

        /**
         * 加载Table数据列表
         */
            // 设置字符过多，省略样式
        var reverseList = ['zl'];
        table.render({
            elem: '#pageTable',
            id: 'pageTable',
            toolbar: '#toolbarDemo',
            title: '抵押物清单',
            defaultToolbar: [],
            url: BASE_URL + '/bdcdy/list/' + processInsId,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            cellMinWidth: 80,
            even: true,
            cols: [[
                {type: 'numbers', fixed: 'left'},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
                {field: 'zl', title: '坐落', minWidth: 250},
                {field: 'zh', title: '幢号', minWidth: 40},
                {field: 'jzmj', title: '建筑面积', minWidth: 80},
                {
                    field: 'jgsj', title: '竣工时间', minWidth: 80,
                    templet: function (d) {
                        return Format(format(d.jgsj), 'yyyy年MM月dd日');
                    }
                },
                {field: 'zcs', title: '总层数', minWidth: 60},
                {field: 'szc', title: '所在层', minWidth: 60},
                {field: 'ghytMc', title: '规划用途', minWidth: 110}
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
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                reverseTableCell(reverseList);

                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }

                // 获取表单控制权限
                if (readOnly || !isNullOrEmpty(formStateId)) {
                    getStateById(readOnly, formStateId, "bdcdyList");
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


        //主页面头工具栏事件
        table.on('toolbar(pageTable)', function (obj) {
            switch (obj.event) {
                case 'print':
                    printBdcdyList();
                    break;
                case 'export':
                    exportExcel(allData, obj.config.cols[0]);
                    break;
            }
        });


        // 导出Excel
        // 通过隐藏form提交方式，避免ajax方式不支持下载文件
        function exportExcel(d, cols) {
            if ($.isEmptyObject(d)) {
                warnMsg("没有可以导出的数据！");
                return;
            }

            // 标题
            var showColsTitle = new Array();
            // 列内容
            var showColsField = new Array();
            // 列宽
            var showColsWidth = new Array();
            for (var index in cols) {
                if (cols[index].hide == false && cols[index].type != 'numbers' && !cols[index].toolbar) {
                    showColsTitle.push(cols[index].title);

                    showColsField.push(cols[index].field);

                    // if('zl' == cols[index].field){
                    //     showColsWidth.push(60);
                    // } else {
                    //     showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                    // }
                    showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
                }
            }

            // 设置Excel基本信息
            $("#fileName").val('不动产单元列表导出Excel表');
            $("#sheetName").val('不动产单元列表');
            $("#cellTitle").val(showColsTitle);
            $("#cellWidth").val(showColsWidth);
            $("#cellKey").val(showColsField);

            $("#data").val(JSON.stringify(allData));
            $("#form").submit();
        }

        /**
         * 打印抵押物清单
         * @returns {boolean}
         */
        function printBdcdyList() {
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print//bdcdy/" + processInsId + "/bdcdyList/xml";
            var modelUrl = bdcdyListModelUrl;
            print(modelUrl, dataUrl, false);

            // 禁止提交后刷新
            return false;
        }

        /**
         * 获取项目信息
         */
        function getXmxx() {
            // 获取当前权利的项目信息
            $.ajax({
                url: BASE_URL + '/qlxx/oneXm/' + processInsId,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if (data) {
                        $("#slbh").html(data.slbh);
                        $("#qlr").html(data.qlr);
                    }
                }
            });
        }
    });

});
