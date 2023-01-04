var qllx;
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
        // xmid
        var xmid = $.getUrlParam('xmid');
        // 是否显示打印
        var xsdy = $.getUrlParam('xsdy');

        var toolbar = '#toolbarDemo';
        if (xsdy == "n") {
            toolbar = '#toolbarDemo2';
        }

        var tddymj;
        var fwdymj;
        // 遮罩
        addModel();

        $.ajax({
            url: BASE_URL + '/zxqd/listZxQdAll/' + processInsId,
            type: "POST",
            dataType: "json",
            timeout: 10000,
            success: function (data) {
                $("#zxqkhz").html("共" + data.total + "条记录，其中土地面积为：" + data.totalTdMj + "㎡，房屋面积为：" + data.totalFwMj + "㎡");
                $("#qlr").html(data.qlr);
                $("#slbh").html(data.slbh);
            }
        });

        // 设置字符过多，省略样式
        var reverseList = ['zl'];

        /**
         * 加载Table数据列表
         */
        var data;
        let tdmjTotal;
        let fwmjTotal;
        let qlr;
        const tableUrl = BASE_URL + '/zxqd/listZxQdByPage/' + processInsId;
        table.render({
            elem: '#pageTable',
            id: 'pageTable',
            toolbar: toolbar,
            title: '注销清单',
            defaultToolbar: [],
            url: tableUrl,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            method:"post",
            cellMinWidth: 80,
            even: true,
            limits: commonLimits,// 每页数据量
            cols: [[
                {type: 'numbers', fixed: 'left'},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
                {field: 'zl', title: '不动产坐落', minWidth: 250},
                {field: 'tdmj', title: '土地面积（㎡）', minWidth: 80},
                {field: 'fwmj', title: '房屋面积（㎡）', minWidth: 80},
                {field: 'dzwytMc', title: '定着物用途', minWidth: 110},
                {field: 'bdcqzh', title: '不动产权证号', minWidth: 250}
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
                setTableHeight(131);
                reverseTableCell(reverseList);

                // 获取表单控制权限
                if (xsdy != "n" && (readOnly || !isNullOrEmpty(formStateId))) {
                    getStateById(readOnly, formStateId, "zxqd");
                }
                // 移除遮罩
                removeModel();

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
                    printZxqd();
                    break;
                case 'export':
                    exportExcel(obj.config.cols[0]);
                    break;
            }
        });

        // 导出Excel
        // 通过隐藏form提交方式，避免ajax方式不支持下载文件
        function exportExcel(cols) {
            var exportCol = {};
            for (var index in cols) {
                if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
                    exportCol[cols[index].title] = cols[index].field
                }
            }
            var tempForm = $("<form>");
            tempForm.attr("id", "exportFrom").attr("style", "display:none").attr("target", "export").attr("method", "post");
            tempForm.attr("action", '/realestate-register-ui//rest/v1.0/zxqd/export');
            var dataInput = $("<input>");
            dataInput.attr("type", "hidden").attr("name", "exportCol").attr("value", encodeURIComponent(JSON.stringify(exportCol)));
            tempForm.append(dataInput);

            var dataInputGzlslid = $("<input>");
            dataInputGzlslid.attr("type", "hidden").attr("name", "gzlslid").attr("value", processInsId);
            tempForm.append(dataInputGzlslid);

            tempForm.on("submit", function () {
            });
            tempForm.trigger("submit");
            $("body").append(tempForm);
            tempForm.submit();
            $("exportFrom").remove();
            return false;
        }


        var dylxArr = ["zxqd"];
        var sessionKey = "zxqd";
        setDypzSession(dylxArr, sessionKey);

        /**
         * 打印抵押物清单
         * @returns {boolean}
         */
        function printZxqd() {
            // 根据是否生成权利判断是否打印原项目的抵押信息
            var dylx = "zxqd"
            var modelUrl = zxqdModelUrl;
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/bdcdy/" + processInsId + "/" + dylx + "/xml";
            printChoice(dylx, "realestate-register-ui", dataUrl, modelUrl, false, sessionKey);
            // 禁止提交后刷新
            return false;
        }
    });

});
