/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 初始化表格，工具栏事件
 */
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

        var url;
        if (!isNullOrEmpty(processInsId)) {
            url = BASE_URL + '/bdcdy/dyawqd/' + processInsId + '/qllxandmj';
        } else if (!isNullOrEmpty(xmid)) {
            url = BASE_URL + '/bdcdy/dyawqd/xmxx/qllxandmj?xmid=' + xmid;
        }
        $.ajax({
            url: url,
            type: "GET",
            dataType: "json",
            timeout: 10000,
            success: function (data) {
                qllx = data.qllx;
                tddymj = parseFloat(data.bdcDyawMjDTO.tddymj).toFixed(2);
                fwdymj = parseFloat(data.bdcDyawMjDTO.fwdymj).toFixed(2);
                $("#dyawqkhz").html("共" + data.allDataLength + "条记录，其中土地抵押面积为：" + tddymj + "㎡，房屋抵押面积为：" + fwdymj + "㎡");
            }
        });
        // 获取公共项目信息
        getXmxx();

        // 设置字符过多，省略样式
        var reverseList = ['zl'];

        var taburl;
        if (!isNullOrEmpty(processInsId)) {
            taburl = BASE_URL + '/bdcdy/dyawqd/' + processInsId;
        } else if (!isNullOrEmpty(xmid)) {
            taburl = BASE_URL + '/bdcdy/dyawqd/xmxx/' + xmid;
        }

        /**
         * 加载Table数据列表
         */
        table.render({
            elem: '#pageTable',
            id: 'pageTable',
            toolbar: toolbar,
            title: '抵押物清单',
            defaultToolbar: [],
            url: taburl,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            cellMinWidth: 80,
            even: true,
            limits: commonLimits,// 每页数据量
            cols: [[
                {type: 'numbers', fixed: 'left'},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
                {field: 'zl', title: '不动产坐落', minWidth: 250},
                {field: 'tddymj', title: '土地抵押面积（㎡）', minWidth: 80},
                {field: 'fwdymj', title: '房屋抵押面积（㎡）', minWidth: 80},
                {field: 'dzwytMc', title: '定着物用途', minWidth: 110},
                {field: 'ybdcqzh', title: '不动产权证号', minWidth: 250}
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
                    getStateById(readOnly, formStateId, "dyawqd");
                }
                // 移除遮罩
                removeModel();
                // 判断是否生成权利
                checkSfscql(processInsId, xmid);
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
                    printDyawqd();
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
            tempForm.attr("action", '/realestate-register-ui//rest/v1.0/bdcdy/exportBdcDyawqd');

            var dataInput = $("<input>");
            dataInput.attr("type", "hidden").attr("name", "exportCol").attr("value", encodeURIComponent(JSON.stringify(exportCol)));
            tempForm.append(dataInput);
            if (!isNullOrEmpty(processInsId)) {
                var dataInputGzlslid = $("<input>");
                dataInputGzlslid.attr("type", "hidden").attr("name", "gzlslid").attr("value", processInsId);
                tempForm.append(dataInputGzlslid);
            } else if (!isNullOrEmpty(xmid)) {
                var dataInputXmid = $("<input>");
                dataInputXmid.attr("type", "hidden").attr("name", "xmid").attr("value", xmid);
                tempForm.append(dataInputXmid);
            }
            var dataInputQllx = $("<input>");
            dataInputQllx.attr("type", "hidden").attr("name", "qllx").attr("value", qllx);
            tempForm.append(dataInputQllx);

            tempForm.on("submit", function () {
            });
            tempForm.trigger("submit");
            $("body").append(tempForm);
            tempForm.submit();
            $("exportFrom").remove();
            return false;
        }

        /**
         * 打印抵押物清单
         * @returns {boolean}
         */
        function printDyawqd() {
            // 根据是否生成权利判断是否打印原项目的抵押信息
            var dylx;
            if (sfscql && qllx == commonDyaq_qllx) {// 抵押物清单
                dylx = "dyawqd";
            } else if (!sfscql && qllx == commonDyaq_qllx) {// 原抵押物清单
                dylx = "ydyawqd";
            } else if (sfscql && qllx == "96") {// 预告抵押物清单
                dylx = "ygDyawqd";
            } else if (!sfscql && qllx == "96") {// 原预告抵押物清单
                dylx = "ygYdyawqd";
            }
            // 可以保持打印模板一直，只要保证打印配置表中的modelxml的数据库名和模板中的数据集名一致即可
            var modelUrl = dyawqdModelUrl;
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/bdcdy/" + processInsId + "/" + dylx + "/xml";
            print(modelUrl, dataUrl, false);

            // 禁止提交后刷新
            return false;
        }

        /**
         * 渲染页面的公共信息
         * @param data
         */
        function getXmxx() {
            // 获取当前权利的项目信息
            var url;
            if(!qllx){
                qllx = parseInt(commonDyaq_qllx);
            }
            if (!isNullOrEmpty(processInsId)) {
                url = BASE_URL + '/qlxx/oneXm/dywqd/' + processInsId+"/"+qllx;
            } else if (!isNullOrEmpty(xmid)) {
                url = BASE_URL + '/qlxx/oneXm/dywqd/xmxx/' + xmid+"/"+qllx;
            }
            $.ajax({
                url: url,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if (data) {
                        $("#slbh").html(data.slbh);
                        $("#dyaqr").html(data.qlr);
                        $("#dyar").html(data.ywr);
                    }
                }
            });
        }
    });

});
