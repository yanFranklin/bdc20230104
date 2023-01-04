/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 初始化表格，工具栏事件
 */

var qllx;
/**
 * 蚌埠抵押首次时假如有10个不动产单元，现在需要解压其中三个不动产单元，现场这边的做法是走流程解压但是不生成新的证明，
 * 只打印新的抵押物清单，也就是部分解压不出证的情况。现在的做法的走抵押注销登记，选择要注销的不动产单元 来实现，
 * 但是我们系统现在的抵押物清单显示的是选择的单元号，也就得要注销的单元号，现场想要显示除了被注销的，
 * 剩余的单元号打印成抵押物清单
 * @type {string}
 */

// 抵押物面积信息
var bdcDyawMjDTO;
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
        // 抵押变更查询类型
        var dyabg = $.getUrlParam('dyabg');
        if (isNullOrEmpty(dyabg)) {
            dyabg = 0;
        }

        var toolbar = '#toolbarDemo';
        if (xsdy == "n") {
            toolbar = '#toolbarDemo2';
        }
        // 不获取所有数据了，改为后台获取
        var url;
        if (!isNullOrEmpty(processInsId)) {
            url = BASE_URL + '/bdcdy/dyawqd/' + processInsId + '/qllxandmj?dyabg=' + dyabg;
        } else if (!isNullOrEmpty(xmid)) {
            url = BASE_URL + '/bdcdy/dyawqd/xmxx/qllxandmj?xmid=' + xmid + '&dyabg=' + dyabg;
        }

        // 增加遮罩
        addModel();
        $.ajax({
            url: url,
            type: "GET",
            dataType: "json",
            async:true,
            timeout: 10000,
            success: function (data) {
                qllx = data.qllx;
                bdcDyawMjDTO = data.bdcDyawMjDTO;
                getXmxx();
                var taburl;
                if (!isNullOrEmpty(processInsId)) {
                    taburl = BASE_URL + '/bdcdy/dyawqd/' + processInsId + '?dyabg=' + dyabg;
                } else if (!isNullOrEmpty(xmid)) {
                    taburl = BASE_URL + '/bdcdy/dyawqd/xmxx/' + xmid + '?dyabg=' + dyabg;
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
                        {field: 'zl', title: '坐落', minWidth: 250},
                        {field: 'zdzhmj', title: '宗地面积', minWidth: 90},
                        {field: 'zdzhytMc', title: '宗地用途', minWidth: 90},
                        {field: 'qlxzMc', title: '宗地权利性质', minWidth: 120},
                        {field: 'dzwytMc', title: '定着物用途', minWidth: 110},
                        {field: 'dzwmj', title: '定着物面积', minWidth: 110},
                        {field: 'fwxzmc', title: '房屋性质', minWidth: 110, templet: '#fwxzTpl'},
                        // {field: 'dyje', title: '抵押金额', minWidth: 110},
                        {field: 'dyar', title: '抵押人', minWidth: 120},
                        {field: 'ybdcqzh', title: '原不动产权证号', minWidth: 250}
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
                    done: function (res, curr, count) {
                        setTableHeight(131);

                        reverseTableCell(reverseList);

                        // 获取表单控制权限
                        if (xsdy != "n" && (readOnly || !isNullOrEmpty(formStateId))) {
                            getStateById(readOnly, formStateId, "dyawqd");
                        }

                        removeModel();
                        // 判断是否生成权利
                        checkSfscql(processInsId, xmid);

                        // 查询房屋性质
                        getFwxz(res.data);

                        //渲染抵押物清单头金额字段
                        if (res.data && res.data[0].dyje) {
                            $("#dywzje").html(parseFloat(res.data[0].dyje));
                        }

                        if (res.data && res.data.length>0 && res.data[0].bdcdyh) {
                            //房屋显示房屋面积
                            var fw = false;
                            var td = false;
                            res.data.forEach(function (item, index) {
                                if (item.bdcdyh.substring(19, 20) === 'F') {
                                    fw = true;
                                }
                                if (item.bdcdyh.substring(19, 20) === 'W') {
                                    td = true;
                                }
                                if (td && fw) {
                                    return;
                                }
                            });
                            if (fw && !td) {
                                $("#dyfcmj").html(parseFloat(bdcDyawMjDTO.fwdymj).toFixed(2));
                                bdcDyawMjDTO.tddymj = 0;
                                $("#dytdmj").html(0);
                            }
                            //土地显示土地面
                            if (!fw && td) {
                                $("#dyfcmj").html(0);
                                bdcDyawMjDTO.fwdymj = 0;
                                $("#dytdmj").html(parseFloat(bdcDyawMjDTO.tddymj).toFixed(2));
                            }
                            if (fw && td) {
                                $("#dyfcmj").html(parseFloat(bdcDyawMjDTO.fwdymj).toFixed(2));
                                $("#dytdmj").html(parseFloat(bdcDyawMjDTO.tddymj).toFixed(2));
                            }
                            $("#dywzmj").html((parseFloat(bdcDyawMjDTO.fwdymj) + parseFloat(bdcDyawMjDTO.tddymj)).toFixed(2));
                        }
                    }
                });
                //获取关联项目的不动产权证号
                $.ajax({
                    url: BASE_URL + "/bdcdy/old/bdcqzh?gzlslid=" + processInsId,
                    type: "GET",
                    dataType: "text",
                    success: function (data) {
                        if (data) {
                            $("#ybdcqzh").html(data);
                        }else {
                            $("#ybdcqzhTr").hide();
                        }
                    }
                });
            }
        });

        // 设置字符过多，省略样式
        var reverseList = ['zl'];



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

        // 存储打印配置的sessionKey，当前页的打印类型
        var dylxArr = ["dyawqd", "ydyawqd", "ydyawqd_1", "ygDyawqd", "ygYdyawqd"];
        var sessionKey = "dyawqd";
        setDypzSession(dylxArr, sessionKey);

        /**
         * 打印抵押物清单
         * @returns {boolean}
         */
        function printDyawqd() {
            // 根据是否生成权利判断是否打印原项目的抵押信息
            var dylx;
            if (sfscql && qllx == commonDyaq_qllx) {// 抵押物清单
                dylx = "dyawqd";
                //modelUrl = dyawqdModelUrl;
            } else if (!sfscql && qllx == commonDyaq_qllx) {// 原抵押物清单
                dylx = "ydyawqd";
                if (dyabg && dyabg > 0) {
                    dylx = "ydyawqd_" + dyabg;
                }
            } else if (sfscql && qllx == "96") {// 预告抵押物清单
                dylx = "ygDyawqd";
                //modelUrl = ygDyawqdModelUrl;
            } else if (!sfscql && qllx == "96") {// 原预告抵押物清单
                dylx = "ygYdyawqd";
                //modelUrl = ygYdyawqdModelUrl;
            }
            // 可以保持打印模板一直，只要保证打印配置表中的modelxml的数据库名和模板中的数据集名一致即可
            var modelUrl = dyawqdModelUrl;
            var dyaqr = $("#dyaqr")[0].innerText;
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/bdcdy/" + processInsId + "/" + dylx + "/xml?qlr=" + encodeURI(encodeURI(dyaqr));
            var appName = "realestate-register-ui";

            printChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);


            // 禁止提交后刷新
            return false;
        }

        /**
         * 渲染页面的公共信息
         * @param
         */
        function getXmxx() {
            var url;
            // 获取当前权利的项目信息
            if (!qllx) {
                qllx = 37;
            }
            if (!isNullOrEmpty(processInsId)) {
                url = BASE_URL + '/qlxx/oneXm/dywqd/' + processInsId + "/" + qllx;
            } else if (!isNullOrEmpty(xmid)) {
                url = BASE_URL + '/qlxx/oneXm/dywqd/xmxx/' + xmid + "/" + qllx;
            }
            $.ajax({
                url: url,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if (data) {
                        $("#slbh").html(data.slbh);
                        $("#dyaqr").html(data.qlr);
                    }
                }
            });
        }

        /**
         * 获取房屋性质
         */
        function getFwxz(pageData) {
            if (pageData && pageData.length > 0) {
                var bdcdyhArray = new Array();
                $.each(pageData, function (index, item) {
                    bdcdyhArray.push(item.bdcdyh);
                });

                $.ajax({
                    url: "/realestate-register-ui/rest/v1.0/bdcdy/dyawqd/fwxx",
                    type: "POST",
                    data: JSON.stringify(bdcdyhArray),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data && data.length > 0) {
                            var xsData = data[0];
                            //$.each(data, function (index, item) {
                            var fwxzmc = isNullOrEmpty(xsData.fwxzmc) ? "" : xsData.fwxzmc;
                            $("." + xsData.bdcdyh + "_fwxz").html('<p class="bdc-table-state-th">' + fwxzmc + '</p>');
                            //});
                        }
                    }
                });
            }
        }
    });
});
