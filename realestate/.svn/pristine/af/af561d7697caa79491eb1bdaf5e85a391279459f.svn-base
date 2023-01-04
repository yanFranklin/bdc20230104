/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 初始化表格，工具栏事件
 */
/**
 * 证书预览的列表所有的数值
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
var zsylListStr;
var resourceName = "bdcZsList";
$(function () {
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
    // 获取参数
    var processInsId = $.getUrlParam('processInsId');
    var gzlslid = processInsId;
    var zsmodel = $.getUrlParam('zsmodel');
    // 表单ID
    var formStateId = $.getUrlParam('formStateId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');
    // 获取是否是证书预览
    var zsyl = $.getUrlParam('zsyl');

    var xmid = $.getUrlParam('xmid');
    // 是否历史项目
    var lsxm = $.getUrlParam('lsxm');

    var maxNum = 200;
    // 打印时选择的打印的证书类型（默认证书）
    var zslxChecked = zsModel;

    // 初始化一些参数值
    checkSfyzws(processInsId);
    // 初始化其它按钮权限
    initQtButtonAtrr(processInsId);

    if (!isNullOrEmpty(zsyl) && zsyl == "true") {
        hideSearch();
    }
    //点击高级查询
    $('#seniorSearch').on('click', function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
    });

    layui.use(['form', 'table', 'jquery', 'response'], function () {
        var form = layui.form;
        var table = layui.table;
        var response = layui.response;
        // 当前查询条件
        var searchData;
        // 缮证时间，默认取查询到的第一条信息
        var szsj;
        // 定义table展示数据列
        var colYl = [[
            {checkbox: true, fixed: true},
            {field: 'bdcqzh', title: '不动产权证号', minWidth: 270},
            {field: 'qzysxlh', title: '权证印刷序列号', minWidth: 130},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
            {field: 'zl', title: '坐落', minWidth: 250},
            {field: 'qlr', title: '权利人', minWidth: 90},
            {field: 'mj', title: '面积', minWidth: 80},
            {field: 'zslxmc', title: '证书类型', minWidth: 80},
            {field: 'zsid', title: '证书ID', hide: true},
            {fixed: 'right', title: '操作', toolbar: '#barDemo'}
        ]];
        var col = [[
            {checkbox: true, fixed: true},
            {
                field: 'dyzt', title: '打印状态', minWidth: 100,
                templet: function (d) {
                    if (d && 1 == d.dyzt) {
                        return '<p style="color:#f54743">已打证</p>';
                    } else {
                        return '<p style="color:#10a54a">未打证</p>';
                    }
                }
            },
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
            {field: 'qlr', title: '权利人', minWidth: 90},
            {field: 'djyy', title: '登记原因', minWidth: 90},
            {field: 'mj', title: '面积', minWidth: 80},
            {field: 'zslxmc', title: '证书类型', minWidth: 90},
            {field: 'zsid', title: '证书ID', hide: true},
            {fixed: 'right', title: '操作', toolbar: '#barDemo'}
        ]];

        // 设置字符过多，省略样式
        var reverseList = ['zl'];
        // 证书预览情况和非证书预览table加载方式分开
        if (!isNullOrEmpty(zsyl) && zsyl == 'true') {
            zsylTableRender();
        } else {
            tableRender();
        }

        //根据选择的数据进行操作时，如果isSelectAll为true，操作包含本页的全部数据
        var isSelectAll = false;
        //监听全选复选鼠标覆盖
        $('.bdc-table-box').on('mouseenter', '.layui-table-header th .laytable-cell-checkbox', function () {
            $('.bdc-select-all-box').removeClass('bdc-hide');
        });
        $('.bdc-select-all-box p').on('click', function (e) {
            e.stopPropagation();
            $('.bdc-select-all-box').addClass('bdc-hide');
            $('.layui-table-header th .layui-form-checkbox').click();

            var checkStatus = table.checkStatus('bdcZsTable');
            if (checkStatus.isAll) {
                $('.layui-table-header th .layui-form-checkbox').addClass('layui-form-checked');
                if ($(this).hasClass('bdc-select-all')) {
                    isSelectAll = true;
                } else {
                    isSelectAll = false;
                }
            } else {
                if ($(this).hasClass('bdc-select-all')) {
                    isSelectAll = false;
                }
            }
        });
        $('.bdc-select-all-box').on('mouseleave', function () {
            $('.bdc-select-all-box').addClass('bdc-hide');
        });
        $('body').on('click', function () {
            $('.bdc-select-all-box').addClass('bdc-hide');
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
            tableReload("bdcZsTable", data.field);
            return false;
        });
        // 监听单选
        form.on('radio(zslxRadio)', function (data) {
            // console.log(data.elem); //得到radio原始DOM对象
            // console.log(data.value); //被点击的radio的value值
            zslxChecked = data.value;
        });

        //主页面头工具栏事件
        table.on('toolbar(dataTable)', function (obj) {
            // 先获取当前选中的数据
            var checkStatus = table.checkStatus('bdcZsTable');
            var data = checkStatus.data;
            if (isEmptyObject(data)) {
                isSelectAll = true;
                $('.layui-table-header th .layui-form-checkbox').click();
                $('.layui-table-header th .layui-form-checkbox').addClass('layui-form-checked');
                // warnMsg("当前没有选中证书，默认选择全部！");
            }
            switch (obj.event) {
                case 'getBatchYzh':
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].qzysxlh) {
                            warnMsg("已存在印制号的证书，将不会重复获取！");
                            break;
                        }
                    }
                    getYzh(data);
                    break;
                case 'batchPrint':
                    if (isSelectAll) {
                        allPrint();
                    } else {
                        batchPrint(data);
                    }
                    setTimeout(function () {
                        // 为了更新打印状态，延缓5秒刷新台账数据
                        refreshTable();
                    }, 5000);
                    break;
                case 'db':
                    sddb(processInsId, response, $('#batchDb'), resourceName).done(function () {
                    });
                    break;
                case 'exportPdf':
                    exportPdf(processInsId);
                    break;
                case 'jhddcx':
                    jhddcx(processInsId);
                    break;
            }
        });


        //监听行工具事件
        table.on('tool(dataTable)', function (obj) {
            // 点击详情默认选中该行
            $(".bdc-zs-list-table [type='checkbox']").prop("checked",'');
            var index = $(this).parents('tr').attr('data-index');
            $(".bdc-zs-list-table tr[data-index="+ index + "]").find("[type='checkbox']").prop("checked", "checked");
            form.render('checkbox');

            var data = obj.data;
            var url = "";
            var title = '证书详情';
            var layerModel;
            if (!isNullOrEmpty(zsyl) && zsyl == 'true') {
                url = initZsylUrl(data);
                layerModel = parent.layer;
            } else {
                url = initZsUrl(data);
                layerModel = layer;
            }

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

        // 预览情况下的证书url（此时数据库中未生成数据）
        function initZsylUrl(data) {
            var url;
            var zslx = data.zslx;
            if (zslx == zsModel) {
                // 先判断首次证明单样式
                if (zsmodel == sczmdModel) {
                    var dyh = data.bdcdyh.replace("等", "");
                    url = '/realestate-register-ui/view/zsxx/bdcSczmd.html?processInsId=' + processInsId + '&bdcdyh=' + dyh + '&zsyl=true';
                } else {
                    url = '/realestate-register-ui/view/zsxx/zsPreview.html?processInsId=' + processInsId + '&zsid=' + data.zsid;
                }
            }
            if (zslx == zmModel) {
                url = '/realestate-register-ui/view/zsxx/zmPreview.html?processInsId=' + processInsId + '&zsid=' + data.zsid;
            }
            // 获取和证书相关的所有的xmid拼接的字符串，用于详细页面的操作
            url = appendZsXmids(url, processInsId, data);
            return url;
        }

        // 正常情况下的证书url（查询数据库）
        function initZsUrl(data) {
            var url;
            var zslx = data.zslx;
            if (zslx == zsModel) {
                // 先判断首次证明单样式
                if (zsmodel == sczmdModel) {
                    var dyh = data.bdcdyh.replace("等", "");
                    url = '/realestate-register-ui/view/zsxx/bdcSczmd.html?zsid=' + data.zsid + '&processInsId=' + processInsId + '&bdcdyh=' + dyh + '&formStateId=' + formStateId + '&readOnly=' + readOnly + '&print=' + printValue;
                } else {
                    url = '/realestate-register-ui/view/zsxx/bdcZs.html?zsid=' + data.zsid + '&formStateId=' + formStateId + '&readOnly=' + readOnly + '&processInsId=' + processInsId + '&print=' + printValue;
                }
            }
            if (zslx == zmModel) {
                url = '/realestate-register-ui/view/zsxx/bdcZm.html?zsid=' + data.zsid + '&formStateId=' + formStateId + '&readOnly=' + readOnly + '&processInsId=' + processInsId + '&print=' + printValue;
            }
            return url;
        }

        // 预览情况下的表格渲染
        function zsylTableRender() {
            var zsylList = getZsylDataList(processInsId);
            zsylListStr = JSON.stringify(zsylList);
            sessionStorage.zsylListStr = zsylListStr;
            table.render({
                elem: '#bdcZsTable',
                // toolbar: '#toolbarDemo',
                id: 'bdcZsTable',
                title: '证书列表',
                defaultToolbar: [],
                cols: colYl,
                limits: [10, 20, 50, 100, 200, 500],
                data: zsylList,
                page: true,
                done: function () {
                    readOnly = true;

                    var searchHeight = 80;
                    setTableHeight(searchHeight);

                    reverseTableCell(reverseList);

                    // 设置导出按钮显示隐藏
                    setPdfExportBtnAttr();
                }
            });
        }

        // 非证书预览时的表格渲染
        function tableRender() {
            /**
             * 加载Table数据列表
             */
            var url;
            if (!isNullOrEmpty(processInsId)) {
                url = '/realestate-register-ui/rest/v1.0/zsxx/list/' + processInsId;
            } else {
                url = '/realestate-register-ui/rest/v1.0/zsxx/list/xm/' + xmid;
            }

            table.render({
                elem: '#bdcZsTable',
                id: 'bdcZsTable',
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
                    if (!isEmptyObject(res.content)) {
                        szsj = res.content[0].szsj;
                    }
                    // 返回空时设置验证证号没有空为false
                    if (isEmptyObject(res.content)) {
                        verifyBdcqzhNotNull = false;
                    }
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
                    /**
                     * 以下权限设置的顺序不可修改，先执行本页面的权限，再执行统一控制权限
                     */
                    // 证书列表页面权限控制
                    setZsListBtnAttr(processInsId, lsxm, response);
                    // 获取表单控制权限
                    if (readOnly || !isNullOrEmpty(formStateId)) {
                        getStateById(readOnly, formStateId, resourceName);
                    }
                    // 设置导出按钮显示隐藏
                    setPdfExportBtnAttr();
                }
            });
        }

        // 设置导出按钮显示隐藏
        function setPdfExportBtnAttr() {
            // 导出按钮只有在首次证明单时候才展示
            if (zsmodel == sczmdModel) {
                $("#exportPdf").show();
            } else {
                $("#exportPdf").hide();
            }
        }

        // 打印所有的证书
        function allPrint() {
            // 验证权属状态，给出提示，后台只打印符合的证书
            var data = verifyZsXmQszt(null, gzlslid);
            if (data) {
                if (data.length > 0) {
                    var msg = data[0].bdcqzh;
                    if (data.length > 1) {
                        msg = msg + "等" + data.length + "本证书";
                    }
                    msg = msg + "不予打印！";
                    warnMsg(msg);
                } else if (data == -1) {
                    // 未登簿或已注销
                    warnMsg("未登簿或已注销，不允许打印！");
                    return false;
                }
            } else {
                return false;
            }

            // 验证是不是所有的已登簿的证书都保存了印制号,首次证明单不需要验证
            if (zsmodel != sczmdModel) {
                var zsxx = allZsGetYzh(gzlslid);
                if (!isEmptyObject(zsxx)) {
                    warnMsg(zsxx.bdcqzh + "未保存印制号，不允许打印！");
                    return false;
                }
            }
            // 证书证明选择一种打印
            zslxAllPrintConfirm(gzlslid);

            return false;
        }

        /**
         * 确认要打印的证书类型
         * @param gzlslid
         */
        function zslxAllPrintConfirm(gzlslid) {
            var zslxList = getGzlZslx(gzlslid);
            if (zslxList.length == 1) {
                zslxChecked = zslxList[0];
                printOneZslx(gzlslid);
            } else if (zslxList.length > 1) {
                //单选框弹出层
                layer.open({
                    title: '打印确认',
                    type: 1,
                    area: ['430px'],
                    btn: ['继续', '取消'],
                    content: $('#bdc-popup-radio')
                    ,success:function(){
                        var existZs =false,existZm =false,existZmd =false;
                        zslxList.forEach(function (v) {
                            if(v ==zsModel){
                                existZs =true;
                            }
                            if(v ==zmModel){
                                existZm =true
                            }
                            if(v ==zmdModel){
                                existZmd=true;
                            }
                        });

                        renderZslxRadio(existZs,existZm,existZmd);
                    }
                    , yes: function (index, layero) {
                        printOneZslx(gzlslid);
                    }
                    , btn2: function (index, layero) {
                        //取消 的回调
                    }
                    , cancel: function () {
                        //右上角关闭回调
                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                });
            } else {
                warnMsg("未获取当当前流程的证书类型！");
                return false;
            }
        }

        /**
         * 打印流程的所有证书
         */
        function printOneZslx(gzlslid) {
            // 打印流程的所有证书
            if (zslxChecked == zsModel) {
                // 打印证明单
                if (zsmodel == sczmdModel) {
                    allZsPrint(zmdModelUrl, gzlslid, sczmdModel,"sczmd");
                } else {
                    allZsPrint(zsModelUrl, gzlslid, "zs");
                }
            } else if (zslxChecked == zmModel) {
                allZsPrint(zmModelUrl, gzlslid, "zm");
            }else if(zslxChecked == zmdModel){
                // 打印证明单
                allZsPrint(zmdModelUrl, gzlslid, sczmdModel);
            }
        }

        // 批量打印
        function batchPrint(data) {
            // 先获取当前选中的数据
            if (data.length > maxNum) {
                warnMsg("为确保打印效率，最多支持" + maxNum + "本打印！");
                return false;
            }
            var zsidArr = [];
            // 需要打印的证书ID
            var zsZsidArr = [];
            // 需要打印的证明ID
            var zmZsidArr = [];
            //需要打印的证明单ID
            var zmdZsidArr = [];

            $.each(data, function (key, val) {
                // 首次证明单没有印制号，不需要验证
                if (zsmodel != sczmdModel  &&val.zslx != zmdModel&& val.qszt == qsztValid && isNullOrEmpty(val.qzysxlh)) {
                    warnMsg(val.bdcqzh + "未保存印制号，不允许打印！");
                    return false;
                }
                if (!isNullOrEmpty(val.zsid.trim()) && val.qszt == qsztValid && (zsidArr.length == 0 || zsidArr.length > 0 && $.inArray(val.zsid, zsidArr) == -1)) {
                    zsidArr.push(val.zsid);
                    if (val.zslx == zsModel) {
                        zsZsidArr.push(val.zsid);
                    }
                    if (val.zslx == zmModel) {
                        zmZsidArr.push(val.zsid);
                    }
                    if (val.zslx == zmdModel) {
                        zmdZsidArr.push(val.zsid);
                    }
                }
            });

            if (zsidArr.length == 0) {
                warnMsg("没有可打印的证书信息！请检查项目是否已登簿或证书是否已获取印制号！");
                return false;
            }
            // 打印证明单
            if (zsmodel == sczmdModel && zsidArr.length > 0) {
                batchZsPrint(zmdModelUrl, zsidArr.join(zf_ywdh), sczmdModel);
                return false;
            }
            // 每次只能打印一种类型的证书
            if ((zsZsidArr.length > 0 && zmZsidArr.length > 0) ||(zsZsidArr.length > 0 && zmdZsidArr.length > 0) ||(zmZsidArr.length > 0 && zmdZsidArr.length > 0)) {
                //单选框弹出层
                layer.open({
                    title: '打印确认',
                    type: 1,
                    area: ['430px'],
                    btn: ['继续', '取消'],
                    content: $('#bdc-popup-radio')
                    ,success:function () {
                        renderZslxRadio(zsZsidArr.length>0,zmZsidArr.length>0,zmdZsidArr.length >0);
                    }
                    , yes: function (index, layero) {
                        if (zslxChecked == zsModel) {
                            batchZsPrint(zsModelUrl, zsZsidArr.join(zf_ywdh), "zs");
                        } else if(zslxChecked ==zmModel){
                            batchZsPrint(zmModelUrl, zmZsidArr.join(zf_ywdh), "zm");
                        }else{
                            batchZsPrint(zmdModelUrl, zmdZsidArr.join(zf_ywdh), sczmdModel);
                        }
                    }
                    , btn2: function (index, layero) {
                        //取消 的回调
                    }
                    , cancel: function () {
                        //右上角关闭回调
                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                });
            } else {
                // 打印证书
                if (zsZsidArr.length > 0) {
                    batchZsPrint(zsModelUrl, zsZsidArr.join(zf_ywdh), "zs");
                }
                // 打印证明
                if (zmZsidArr.length > 0) {
                    batchZsPrint(zmModelUrl, zmZsidArr.join(zf_ywdh), "zm");
                }
                // 打印证明单
                if (zmdZsidArr.length > 0) {
                    ntBatchZsPrint(zmdModelUrl, zmdZsidArr.join(zf_ywdh), sczmdModel);
                }
            }
            // 禁止刷新页面
            return false;
        }

        /**
         * 批量获取印制号
         * @param data 台账选中的数据
         */
        function getYzh(data) {
            // 获取当前流程证书领证方式
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/zs/lzfs?processInsId=" + processInsId,
                type: "GET",
                contentType: 'application/json',
                dataType: "text",
                success: function (lzfs) {
                    if(lzfs && lzfs == 4) {
                        // 领证方式是电子证照，走虚拟印制号处理逻辑
                        getBatchXnyzh(data);
                    } else {
                        // 其它方式正常走印制号处理逻辑，分选中数据、全部数据处理
                        if (isSelectAll) {
                            getAllZsYzh();
                        } else {
                            getBatchYzh(data);
                        }
                    }
                }, error: function () {
                    warnMsg("获取领证方式失败，请重试！");
                }
            });
        }

        /**
         * 批量获取虚拟印制号
         * @param data 台账选中的数据
         */
        function getBatchXnyzh(data) {
            addModel();

            // 证书证明ID集合
            var zsidArr = [];

            if (isSelectAll) {
                // 所有记录数据获取虚拟印制号，直接传参工作流实例ID处理
            } else {
                // 选择部分数据，传递证书证明ID集合
                $.each(data, function (key, val) {
                    // 只获取现势，没有印制号的证书ID
                    if (val.qszt == qsztValid && isNullOrEmpty(val.qzysxlh) && (zsidArr.length >= 0 && $.inArray(val.zsid, zsidArr) == -1)) {
                        zsidArr.push(val.zsid);
                    }
                });

                if(zsidArr.length == 0) {
                    removeModel();
                    return false;
                }
            }

            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/yzh/xnyzh/batch?processInsId=" + processInsId,
                type: "POST",
                data: JSON.stringify({"zsidList": zsidArr}),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    if(data && !isEmptyObject(data) && data.length > 0) {
                        // 印制号获取成功，生成缮证信息
                        asyncSaveSzr([], gzlslid);
                        refreshTable();
                    } else {
                        warnMsg("未生成虚拟印制号");
                    }

                    removeModel();
                    return false;
                }, error: function () {
                    warnMsg("获取印制号失败，请重试！");
                    removeModel();
                }
            });
            // 禁止刷新页面
            return false;
        }

        // 获取当前流程所有证书的印制号
        function getAllZsYzh() {
            addModel();
            // 获取印制号
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/yzh/allZs/" + gzlslid,
                type: "GET",
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    //console.log(data);
                    if (data === 0) {
                        // 所有的证书都已经获取了印制号
                        removeModel();
                        return false;
                    } else if (data && !isEmptyObject(data) && data.length > 0) {
                        // 印制号获取成功，生成缮证信息
                        asyncSaveSzr([], gzlslid);
                        refreshTable();
                        removeModel();
                    } else {
                        warnMsg("未查询到可用的印制号");
                        removeModel();
                    }
                }, error: function () {
                    warnMsg("获取失败，请重试！");
                    removeModel();
                }
            });
            // 禁止刷新页面
            return false;
        }

        // 批量获取印制号（印制号查询条件一致的情况）
        function getBatchYzh(data) {
            addModel();
            var zsidArr = [];
            // 需要获取印制号的证书ID
            var zsZsidArr = [];
            // 需要获取印制号的证明
            var zmZsidArr = [];

            var bdcYzhQOList = [];

            // 只获取现势，没有印制号的证书ID
            $.each(data, function (key, val) {
                if (val.qszt == qsztValid && isNullOrEmpty(val.qzysxlh) && (zsidArr.length == 0 || zsidArr.length > 0 && $.inArray(val.zsid, zsidArr) == -1)) {
                    zsidArr.push(val.zsid);
                    if (val.zslx == zsModel) {
                        zsZsidArr.push(val.zsid);
                    }
                    if (val.zslx == zmModel) {
                        zmZsidArr.push(val.zsid);
                    }
                }
            });

            // 封装证书获取印制号参数
            if (!isEmptyObject(zsZsidArr)) {
                var zsYzhQO = {};
                zsYzhQO["zslx"] = zsModel;
                zsYzhQO["syqk"] = yly;
                zsYzhQO["qxdm"] = data[0].qxdm;
                zsYzhQO["zsidList"] = zsZsidArr;

                bdcYzhQOList.push(zsYzhQO);
            }
            // 封装证明获取印制号参数
            if (!isEmptyObject(zmZsidArr)) {
                var zmYzhQO = {};
                zmYzhQO["zslx"] = zmModel;
                zmYzhQO["syqk"] = yly;
                zmYzhQO["qxdm"] = data[0].qxdm;
                zmYzhQO["zsidList"] = zmZsidArr;

                bdcYzhQOList.push(zmYzhQO);
            }

            if (isEmptyObject(bdcYzhQOList) || bdcYzhQOList.length == 0) {
                warnMsg("没有可获取印制号的证书信息！请检查项目是否已登簿！");
                removeModel();
                return false;
            }
            // 获取印制号
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/yzh/batchZs",
                type: "POST",
                data: JSON.stringify(bdcYzhQOList),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    console.log(data);
                    if (data && !isEmptyObject(data)) {
                        // 印制号获取成功，生成缮证信息
                        asyncSaveSzr([], gzlslid);
                        refreshTable();
                    } else {
                        warnMsg("未查询到可用的印制号");
                    }
                    removeModel();
                }, error: function () {
                    warnMsg("获取失败，请重试！");
                    removeModel();
                }
            });
            // 禁止刷新页面
            return false;
        }

        // 重新加载当前页的表格数据
        window.refreshTable = function () {
            console.log("重新加载当前页的表格数据");
            // 当前页码
            var page = $('.layui-laypage-curr em:last-child').html();
            // 关闭时自动刷新列表
            if (isEmptyObject(searchData)) {
                tableReload("bdcZsTable", null, page)
            } else {
                tableReload("bdcZsTable", searchData.field, page);
            }
            // 刷新表单后，重新设置按钮属性
            setZsListBtnAttr(processInsId, lsxm, response);
        }

        //加载单选选项
        function renderZslxRadio(existZs,existZm,existZmd) {
            var radio ="";
            if(existZs){
                radio+="<input type=\"radio\" name=\"zslxRadio\" value=\"1\" title=\"证书\" lay-filter=\"zslxRadio\" checked>";
                zslxChecked =zsModel;
            }
            if(existZm){
                if(isNotBlank(radio)){
                    radio+="<input type=\"radio\" name=\"zslxRadio\" value=\"2\" title=\"证明\" lay-filter=\"zslxRadio\">";
                }else{
                    radio+="<input type=\"radio\" name=\"zslxRadio\" value=\"2\" title=\"证明\" lay-filter=\"zslxRadio\" checked>";
                    zslxChecked =zmModel;
                }
            }
            if(existZmd){
                radio+="<input type=\"radio\" name=\"zslxRadio\" value=\"3\" title=\"证明单\" lay-filter=\"zslxRadio\">";
            }
            $('#bdc-popup-radio').find(".layui-input-inline").html(radio);
            form.render("radio");

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
                    limits: [10, 20, 50, 100, 200, 500],
                }
            });
        });
    }

    //隐藏查询条件
    function hideSearch() {
        $('.bdc-percentage-container').addClass('bdc-percentage-hide-search');
        $('.bdc-search-content').addClass('bdc-hide');
    }
});

