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
var qjgldm;
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

    var tdcbjyq = false;
    var dkfbArr = [];
    // var zsfbArr = ["468G2832JIN070ZM"];
    // var zmfbArr = ["47EB4720N42B101E"];
    var zsfbArr = [];
    var zmfbArr = [];


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

            {fixed: 'right', title: '操作', toolbar: '#barDemo',minWidth: 180}
        ]];
        var col = [[
            {checkbox: true, fixed: true},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
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
                field: 'qllx', title: '状态', hide: true,
                templet: function (d) {
                    if (d && 9 === d.qllx) {
                        tdcbjyq = true;
                        dkfbArr.push(d.zsid);
                    } else if (d && zmModel === d.zslx) {
                        zmfbArr.push(d.zsid)
                    } else {
                        zsfbArr.push(d.zsid);
                    }
                }
            },
            {field: 'bdcqzh', title: '不动产权证号', minWidth: 270},
            {field: 'qzysxlh', title: '权证印刷序列号', minWidth: 130},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
            {field: 'zl', title: '坐落', minWidth: 250},
            {field: 'qlr', title: '权利人', minWidth: 90},
            {field: 'djyy', title: '登记原因', minWidth: 90},
            {field: 'mj', title: '面积', minWidth: 80},
            {field: 'zslxmc', title: '证书类型', minWidth: 90},
            {field: 'zsid', title: '证书ID', hide: true},
            {fixed: 'right', title: '操作', toolbar: '#barDemo',minWidth: 180}
        ]];

        // 首次登记证明单不需要印制号
        if (zsmodel == sczmdModel) {
            col = [[
                {checkbox: true, fixed: true},
                {field: 'xh', title: '序号', width: 50, type: 'numbers'},
                {field: 'bdcqzh', title: '不动产权证号', minWidth: 270},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
                {field: 'zl', title: '坐落', minWidth: 250},
                {field: 'qlr', title: '权利人', minWidth: 90},
                {field: 'djyy', title: '登记原因', minWidth: 90},
                {field: 'mj', title: '面积', minWidth: 80},
                {field: 'zslxmc', title: '证书类型', minWidth: 90},
                {field: 'zsid', title: '证书ID', hide: true},
                {field: 'qszt', title: '权属状态', hide: true},
                {fixed: 'right', title: '操作', toolbar: '#barDemo',minWidth: 180}
            ]];
        }

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
            //查询清空附表zsid集合,避免打印重复
            dkfbArr = [];
            zsfbArr = [];
            zmfbArr = [];
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
            } else if (isSelectAll == true && checkStatus.isAll == false) {
                isSelectAll = false;
            }
            var zsidArr = [];
            if (data.length > 0) {
                $.each(data, function (key, val) {
                    zsidArr.push(val.zsid);
                });
            }
            switch (obj.event) {
                case 'getBatchYzh':
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].qzysxlh) {
                            warnMsg("已存在印制号的证书，将不会重复获取！");
                            break;
                        }
                    }
                    if (isSelectAll) {
                        // 校验领证方式
                        if(!jylzfs(zsidArr,processInsId)){
                            break;
                        }
                        getAllZsYzh();
                    } else {
                        // 校验领证方式
                        if(!jylzfs(zsidArr,"")){
                            break;
                        }
                        getBatchYzh(data);
                    }
                    break;
                case 'batchPrint':
                    if (isSelectAll) {
                        // 校验领证方式
                        if(!jylzfs(zsidArr,processInsId)){
                            break;
                        }
                        allPrint();
                    } else {
                        // 校验领证方式
                        if(!jylzfs(zsidArr,"")){
                            break;
                        }
                        batchPrint(data);
                    }
                    break;
                case 'openZsfb':
                    printZsfb(processInsId, isSelectAll, data);
                    break;
                case 'batchPrintZmdLb':
                    printZmdLb(processInsId);
                    break;
                case 'batchPrintZmd':
                    if (isSelectAll) {
                        allLygZmdPrint();
                    } else {
                        batchLygZmdPrint(data);
                    }
                    break;
                case 'exportZsqd':
                    exportZsqdExcel(data,isSelectAll, obj.config.cols[0]);
                    break;
            }
        });


        //监听行工具事件
        table.on('tool(dataTable)', function (obj) {
            switch (obj.event) {
                case "info":
                    // 点击详情默认选中该行
                    $(".bdc-zs-list-table [type='checkbox']").prop("checked",'');
                    var index = $(this).parents('tr').attr('data-index');
                    $(".bdc-zs-list-table tr[data-index="+ index + "]").find("[type='checkbox']").prop("checked", "checked");
                    form.render('checkbox');

                    var data = obj.data;
                    var url = "";
                    var title = '证书详情';
                    var layerModel;
                    var layerShade = 0.3;
                    var layerArea = ['100%', '100%'];
                    var operateWidth = $('.bdc-table-box').width() - 50 + 'px';
                    var operateHeight = $('.bdc-table-box').height() - 20 + 'px';
                    var isZsyl = false;
                    if (!isNullOrEmpty(zsyl) && zsyl == 'true') {
                        url = initZsylUrl(data);
                        layerModel = layer;
                        layerShade = 0;
                        isZsyl = true;
                        layerArea = [operateWidth, operateHeight];
                    } else {
                        url = initZsUrl(data);
                        layerModel = layer;
                    }

                    layerModel.open({
                        title: [title, 'font-size:16px;font-weight: 700;'],
                        type: 2,
                        skin: 'bdc-zs-skin',
                        area: layerArea,
                        offset: 'rb',
                        resize: true,
                        shade: layerShade,
                        content: url,
                        id: 'zsListCs',
                        cancel: function (index, layero) {
                            if (isZsyl) {
                                var getPage = $('.layui-table-page .layui-laypage-default span:nth-child(2) input').val();
                                getPage = parseInt(getPage);
                                zsylTableRender(getPage);
                            } else {
                                refreshTable();
                            }
                            layerModel.close(index);
                            return false;
                        }
                    });
                    break;
                case "zdt":
                    queryZdt(obj.data);
                    break;
                case "hst":
                    queryHst(obj.data);
                    break;
            }

        });
        table.on('checkbox(dataTable)', function (obj) {
            if ($('.bdc-zs-skin').length > 0) {
                $(".bdc-zs-list-table [type='checkbox']").prop("checked", '');
                $(this).prop("checked", "checked");
                form.render('checkbox');
                var newUrl = initZsylUrl(obj.data);
                $('.bdc-zs-skin iframe').attr('src', newUrl);
            }
        });

        // 预览情况下的证书url（此时数据库中未生成数据）
        function initZsylUrl(data) {
            var url;
            var zslx = data.zslx;
            if (zslx == zsModel) {
                url = '/realestate-register-ui/view/zsxx/zsPreview.html?processInsId=' + processInsId + '&zsid=' + data.zsid;
            }
            if (zslx == zmModel) {
                url = '/realestate-register-ui/view/zsxx/zmPreview.html?processInsId=' + processInsId + '&zsid=' + data.zsid;
            }
            if (zslx == zmdModel) {
                var dyh = data.bdcdyh.replace("等", "");
                url = '/realestate-register-ui/view/zsxx/bdcSczmd.html?processInsId=' + processInsId + '&bdcdyh=' + dyh + '&zsyl=true';
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
                url = '/realestate-register-ui/view/zsxx/bdcZs.html?zsid=' + data.zsid + '&formStateId=' + formStateId + '&readOnly=' + readOnly + '&processInsId=' + processInsId + '&print=' + printValue;
            }
            if (zslx == zmModel) {
                url = '/realestate-register-ui/view/zsxx/bdcZm.html?zsid=' + data.zsid + '&formStateId=' + formStateId + '&readOnly=' + readOnly + '&processInsId=' + processInsId + '&print=' + printValue;
            }
            if (zslx == zmdModel) {
                var dyh = data.bdcdyh.replace("等", "");
                url = '/realestate-register-ui/view/zsxx/bdcSczmd.html?zsid=' + data.zsid + '&processInsId=' + processInsId + '&bdcdyh=' + dyh + '&formStateId=' + formStateId + '&readOnly=' + readOnly + '&print=' + printValue;
            }
            return url;
        }

        // 预览情况下的表格渲染
        function zsylTableRender(page) {
            var getPage;
            if (page) {
                getPage = page;
            } else {
                getPage = 1;
            }
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
                data: zsylList,
                page: {
                    curr: getPage
                },
                done: function () {
                    readOnly = true;

                    var searchHeight = 80;
                    setTableHeight(searchHeight);

                    reverseTableCell(reverseList);
                }
            });
            // 自动打开分页详细
            document.getElementById("loadTotalBtn-bdcZsTable").click();
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
                    loadTotal: true // 证书预览列表底部默认展示详细信息需求
                },
                cellMinWidth: 80,
                even: true,
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

                    // 证书列表页面权限控制
                    setZsListBtnAttr(processInsId, lsxm, response);
                    // 获取表单控制权限
                    if (readOnly || !isNullOrEmpty(formStateId)) {
                        getStateById(readOnly, formStateId, resourceName);
                        if ((true == readOnly || "true" == readOnly) && (true == printValue || "true" == printValue)) {
                            // 设置补打时按钮的状态
                            setZsPrintState();
                            // 证书列表页面权限控制
                            setZsListBtnAttr(processInsId, lsxm, response);
                        }
                    }

                    // 首次登记证明单不需要获取印制号、不要手动登簿
                    if (zsmodel == sczmdModel) {
                        $("#getBatchYzh").hide();
                        $("#batchDb").hide();
                    }
                }
            });
        }

        // 存储打印配置的sessionKey，当前页的打印类型
        var appName = "realestate-register-ui";
        var dylxArr = ["zs", "zm", "sczmd", "zmfb", "zsfb","zmdlb","plzmddy","hst"];
        var sessionKey = "bdcZsList";
        setDypzSession(dylxArr, sessionKey);

        function xz(dkfbArr1, zsfbArr1, zmfbArr1) {
            if (dkfbArr1.length > 0 && zsfbArr1.length > 0) {
                return true;
            } else if (dkfbArr1.length > 0 && zmfbArr1.length > 0) {
                return true;
            } else if (zsfbArr1.length > 0 && zmfbArr1.length > 0) {
                return true
            } else {
                return false;
            }
        }

        // 打印证书附表
        function printZsfb(processInsId, isSelectAll, data) {
            // 全选打印
            if (isSelectAll) {
                // dkfbArr = [];
                // if ((dkfbArr.length > 0 && zmfbArr.length >0 )|| zsfbArr.length > 0) {
                // tdcbjyq = false;
                if (xz(dkfbArr, zsfbArr, zmfbArr)) {
                    //单选框弹出层
                    layer.open({
                        title: '打印确认',
                        type: 1,
                        area: ['430px'],
                        btn: ['继续', '取消'],
                        content: $('#bdc-dkfb-radio')
                        , yes: function (index, layero) {
                            if (zslxChecked == zsModel) {
                                if (zsfbArr.length > 0) {
                                    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zsfbArr.join(zf_ywdh) + "/zs/xml";
                                    var modelUrl = zsfbModelUrl;
                                    printChoice("zsfb", appName, dataUrl, modelUrl, false, sessionKey);
                                } else {
                                    warnMsg("无该类型附表，请重新选择！");
                                }

                            } else if (zslxChecked == zmModel) {
                                // processInsId = "3868409";
                                if (zmfbArr.length > 0) {
                                    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/zm/xml";
                                    var modelUrl = zmfbModelUrl;
                                    printChoice("zmfb", appName, dataUrl, modelUrl, false, sessionKey);
                                } else {
                                    warnMsg("无该类型附表，请重新选择!");
                                }

                            } else {
                                if (dkfbArr.length > 0) {
                                    dkfbAll(processInsId);
                                } else {
                                    warnMsg("无该类型附表，请重新选择!");
                                }
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
                    if (zsfbArr.length > 0) {

                        var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zsfbArr.join(zf_ywdh) + "/zs/xml";
                        var modelUrl = zsfbModelUrl;
                        printChoice("zsfb", appName, dataUrl, modelUrl, false, sessionKey);
                    } else if (zmfbArr.length > 0) {
                        var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/zm/xml";
                        var modelUrl = zmfbModelUrl;
                        printChoice("zmfb", appName, dataUrl, modelUrl, false, sessionKey);
                    } else {
                        dkfbAll(processInsId);

                    }

                }
            } else {
                var dkArr = [];
                // 需要打印的证书ID
                var zsArr = [];
                // 需要打印的证明ID
                var zmArr = [];
                if (data.length > 0) {
                    $.each(data, function (key, val) {
                        if (val && 9 === val.qllx) {
                            tdcbjyq = true;
                            dkArr.push(val.zsid);
                        } else if (val && zmModel === val.zslx) {
                            zmArr.push(val.zsid)
                        } else {
                            zsArr.push(val.zsid);
                        }
                    });
                }
                // dkArr=[];
                // zsArr=[];
                // zsArr=["468G2832JIN070ZM"];
                // zmArr=["47EB4720N42B101E"];
                if (xz(dkArr, zsArr, zmArr)) {
                    //单选框弹出层
                    layer.open({
                        title: '打印确认',
                        type: 1,
                        area: ['430px'],
                        btn: ['继续', '取消'],
                        content: $('#bdc-dkfb-radio')
                        , yes: function (index, layero) {
                            if (zslxChecked == zsModel) {
                                if (zsArr.length > 0) {
                                    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zsArr.join(zf_ywdh) + "/zs/xml";
                                    var modelUrl = zsfbModelUrl;
                                    printChoice("zsfb", appName, dataUrl, modelUrl, false, sessionKey);
                                } else {
                                    warnMsg("无该类型附表，请重新选择！");
                                }

                            } else if (zslxChecked == zmModel) {
                                // processInsId = "3868409";
                                if (zmArr.length > 0) {
                                    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/zm/xml";
                                    var modelUrl = zmfbModelUrl;
                                    printChoice("zmfb", appName, dataUrl, modelUrl, false, sessionKey);
                                } else {
                                    warnMsg("无该类型附表，请重新选择！");
                                }

                            } else {
                                if (dkArr.length > 0) {
                                    dkfbByZsid(data);
                                } else {
                                    warnMsg("无该类型附表，请重新选择！");
                                }
                                // processInsId = "3898079";
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
                    if (dkArr.length > 0) {
                        dkfbByZsid(data);
                    } else if (zmArr.length > 0) {
                        zslxChecked = zmModel;
                        // processInsId = "3868409";
                        //printOneZslxFbxx(processInsId);
                        var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zmArr.join(zf_ywdh) + "/zs/xml";
                        var modelUrl = zmfbModelUrl;
                        printChoice("zmfb", appName, dataUrl, modelUrl, false, sessionKey);
                    } else {
                        zslxChecked = zsModel;
                        // processInsId = "3907784";
                        // tdcbjyq = false;
                        var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zsArr.join(zf_ywdh) + "/zs/xml";
                        var modelUrl = zsfbModelUrl;
                        printChoice("zsfb", appName, dataUrl, modelUrl, false, sessionKey);

                    }
                }

            }
        }

        //打印所有地块附表
        function dkfbAll(processInsId) {
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dkfbAll/" + processInsId + "/dkfb/xml";
            print(dkfbModelUrl, dataUrl, false);

        }

        //打印证明单列表
        function printZmdLb(processInsId) {
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zmdlb/" + processInsId + "/xml";
            var modelUrl = zmdlbModelUrl;
            printChoice("zmdlb", appName, dataUrl, modelUrl, false, sessionKey);

        }

        //打印对应证书的地块附表信息
        function dkfbByZsid(data) {
            var zsidArr = [];

            $.each(data, function (key, val) {

                if (val.qllx === 9) {
                    zsidArr.push(val.zsid);
                }
            });

            if (zsidArr.length == 0) {
                warnMsg("没有选择数据信息！");
                return false;
            }
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dkfbByZsid/" + zsidArr + "/dkfb/xml";
            print(dkfbModelUrl, dataUrl, false);
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
                if (!isEmptyObject(zsxx) &&zsxx.zslx !=zmdModel) {
                    warnMsg(zsxx.bdcqzh + "未保存印制号，不允许打印！");
                    return false;
                }
            }
            // 证书证明选择一种打印
            zslxAllPrintConfirm(gzlslid, "batchPrint");

            return false;
        }

        // 打印所有的证明单
        function allLygZmdPrint() {
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
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/allZmdprint/" + processInsId + "/xml";
            var modelUrl = zmdModelUrl;
            printChoice("plzmddy", appName, dataUrl, modelUrl, false, sessionKey);
            return false;
        }


        /**
         * 确认要打印的证书类型
         * @param gzlslid
         */
        function zslxAllPrintConfirm(gzlslid, eventName) {
            var zslxList = getGzlZslx(gzlslid);
            if (zslxList.length == 1) {
                zslxChecked = zslxList[0];
                choicePrintEvent(gzlslid, eventName);
                // printOneZslx(gzlslid);
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
                        choicePrintEvent(gzlslid, eventName);
                        //printOneZslx(gzlslid);
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
                warnMsg("未获取到当前流程的证书类型！");
                return false;
            }
        }

        /**
         * 根据打印事件选择最终打印类型
         * @param gzlslid 工作流实例id
         * @param eventName 操作事件名称
         */
        function choicePrintEvent(processInsId, eventName) {
            if ("batchPrint" === eventName) {
                // 证书打印
                printOneZslx(processInsId);
            } else if ("openZsfb" === eventName) {
                // 证书附表打印
                printOneZslxFbxx(processInsId);
            }
        }

        /**
         * 打印当前流程所有的证书附表，只能选择一种证书类型打印
         * @param gzlslid
         */
        function printOneZslxFbxx(processInsId) {
            // 打印流程的所有证书的附表信息
            if (zslxChecked == zsModel || zslxChecked == zmdModel) {
                var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/zs/xml";
                var modelUrl = zsfbModelUrl;
                printChoice("zsfb", appName, dataUrl, modelUrl, false, sessionKey);
            } else if (zslxChecked == zmModel) {
                var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/zm/xml";
                var modelUrl = zmfbModelUrl;
                printChoice("zmfb", appName, dataUrl, modelUrl, false, sessionKey);
            }
        }

        function zszmfbBatchPrint(processInsId, data) {
            var zsidArr = [];
            // 需要打印的证书ID
            var zsZsidArr = [];
            // 需要打印的证明ID
            var zmZsidArr = [];

            $.each(data, function (key, val) {
                // 首次证明单没有印制号，不需要验证
                if (val.zslx == zsModel || val.zslx == zmdModel) {
                    zsZsidArr.push(val.zsid);
                }
                if (val.zslx == zmModel) {
                    zmZsidArr.push(val.zsid);
                }
                zsidArr.push(val.zsid);
            });

            if (zsidArr.length == 0) {
                warnMsg("没有选择数据信息！");
                return false;
            }
            // 每次只能打印一种类型的证书
            if (zsZsidArr.length > 0 && zmZsidArr.length > 0) {
                //单选框弹出层
                layer.open({
                    title: '打印确认',
                    type: 1,
                    area: ['430px'],
                    btn: ['继续', '取消'],
                    content: $('#bdc-popup-radio')
                    , yes: function (index, layero) {
                        if (zslxChecked == zsModel) {
                            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zsZsidArr.join(zf_ywdh) + "/zs/xml";
                            var modelUrl = zsfbModelUrl;
                            printChoice("zsfb", appName, dataUrl, modelUrl, false, sessionKey);
                        } else {
                            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zmZsidArr.join(zf_ywdh) + "/zm/xml";
                            var modelUrl = zmfbModelUrl;
                            printChoice("zmfb", appName, dataUrl, modelUrl, false, sessionKey);
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
                    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zsZsidArr.join(zf_ywdh) + "/zs/xml";
                    var modelUrl = zsfbModelUrl;
                    printChoice("zsfb", appName, dataUrl, modelUrl, false, sessionKey);
                }
                // 打印证明
                if (zmZsidArr.length > 0) {
                    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zmZsidArr.join(zf_ywdh) + "/zm/xml";
                    var modelUrl = zmfbModelUrl;
                    printChoice("zmfb", appName, dataUrl, modelUrl, false, sessionKey);
                }
            }
            // 禁止刷新页面
            return false;
        }

        /**
         * 打印流程的所有证书
         */
        function printOneZslx(gzlslid) {
            // 打印流程的所有证书
            if (zslxChecked == zsModel) {
                ntAllZsPrint(zsModelUrl, gzlslid, "zs", sessionKey);
            } else if (zslxChecked == zmModel) {
                ntAllZsPrint(zmModelUrl, gzlslid, "zm", sessionKey);
            } else if (zslxChecked == zmdModel) {
                // 打印证明单
                ntAllZsPrint(zmdModelUrl, gzlslid, sczmdModel, sessionKey);
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
                if (zsmodel != sczmdModel &&val.zslx != zmdModel && val.qszt == qsztValid && isNullOrEmpty(val.qzysxlh)) {
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
                ntBatchZsPrint(zmdModelUrl, zsidArr.join(zf_ywdh), sczmdModel);
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
                            ntBatchZsPrint(zsModelUrl, zsZsidArr.join(zf_ywdh), "zs");
                        } else if(zslxChecked == zmModel){
                            ntBatchZsPrint(zmModelUrl, zmZsidArr.join(zf_ywdh), "zm");
                        }else{
                            ntBatchZsPrint(zmdModelUrl, zmdZsidArr.join(zf_ywdh), sczmdModel);
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
                    ntBatchZsPrint(zsModelUrl, zsZsidArr.join(zf_ywdh), "zs", sessionKey);
                }
                // 打印证明
                if (zmZsidArr.length > 0) {
                    ntBatchZsPrint(zmModelUrl, zmZsidArr.join(zf_ywdh), "zm", sessionKey);
                }
                // 打印证明单
                if (zmdZsidArr.length > 0) {
                    ntBatchZsPrint(zmdModelUrl, zmdZsidArr.join(zf_ywdh), sczmdModel, sessionKey);
                }
            }
            // 禁止刷新页面
            return false;
        }


        // 批量打印证明单
        function batchLygZmdPrint(data) {
            //需要打印的证明单ID
            var zmdZsidArr = [];
            $.each(data, function (key, val) {
                zmdZsidArr.push(val.zsid);
            });
            batchZmdPrint("plzmddy",zmdModelUrl, zmdZsidArr.join(zf_ywdh), sessionKey);
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
                        removeModel();
                        warnMsg("未查询到可用的印制号");
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
                //zsYzhQO["qxdm"] = globleQxdm;
                zsYzhQO["zsidList"] = zsZsidArr;

                bdcYzhQOList.push(zsYzhQO);
            }
            // 封装证明获取印制号参数
            if (!isEmptyObject(zmZsidArr)) {
                var zmYzhQO = {};
                zmYzhQO["zslx"] = zmModel;
                zmYzhQO["syqk"] = yly;
                zmYzhQO["qxdm"] = data[0].qxdm;
                //zmYzhQO["qxdm"] = globleQxdm;
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
        function queryHst(checkedData){
            checkedData.bdcdyh = deleteWhitespace(checkedData.bdcdyh, 'all');
            //监听户室图预览按钮
            {
                layer.open({
                    title: ['户室图预览', 'font-size:16px;font-weight: 700;'],
                    type: 2,
                    area: ['100%', '100%'],
                    resize: true,
                    closeBtn: 1,
                    content: '/realestate-register-ui/view/zsxx/hstView.html?bdcdyh=' + checkedData.bdcdyh + "&qjgldm=" + checkedData.qjgldm,
                    scrollbar: false,
                    btn: '打印',
                    yes: function (index, layero) {
                        printZdtHst(checkedData.bdcdyh,"hst",sessionKey);
                    },
                    btnAlign: 'c'
                });
                // 禁止提交后刷新
                return false;
            }
        }
        function queryZdt(checkedData){
            // 监听宗地图预览按钮
            checkedData.bdcdyh = deleteWhitespace(checkedData.bdcdyh, 'all');
            {
                layer.open({
                    title: ['宗地图预览', 'font-size:16px;font-weight: 700;'],
                    type: 2,
                    area: ['100%', '100%'],
                    resize: true,
                    closeBtn: 1,
                    content: '/realestate-register-ui/view/zsxx/zdtView.html?bdcdyh=' + checkedData.bdcdyh + "&qjgldm=" + checkedData.qjgldm,
                    scrollbar: false,
                    btn: '打印',
                    yes: function (index, layero) {
                        printZdtHst(checkedData.bdcdyh,"zdt","bdcZm");
                    },
                    btnAlign: 'c'
                });
                // 禁止提交后刷新
                return false;
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
                    curr: page
                }
            });
        });
    }

    //隐藏查询条件
    function hideSearch() {
        $('.bdc-percentage-container').addClass('bdc-percentage-hide-search');
        $('.bdc-search-content').addClass('bdc-hide');
    }

    // 导出证书清单
    function exportZsqdExcel(checkedData, isSelectAll,obj){
        // 标题
        var showColsTitle = new Array();
        // 列内容
        var showColsField = new Array();
        // 列宽
        var showColsWidth = new Array();

        showColsTitle.push(['序号','权利人名称','坐落','不动产权证号','不动产单元号','用途','权利性质','建筑面积','共有情况']);
        showColsField.push(['xh','qlr','zl','bdcqzh','bdcdyh','yt','qlxz','dzwmj','gyqk']);
        showColsWidth.push([5,10,35,40,35,15,10,40,10]);

        // 设置Excel基本信息
        $("#fileName").val('证书清单导出Excel表');
        $("#sheetName").val('统计表');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);

        if (isSelectAll){
            if (!isNullOrEmpty(processInsId)) {
                $("#gzlslid").val(processInsId);
            }
            $("#downloadExcel").submit();
        }else{
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要导出Excel的数据行！");
                return;
            }

            var listZsid = [];
            $.each(checkedData, function (key, value) {
                listZsid.push(value.zsid);
            })
            $("#zsidList").val(listZsid);
            $("#downloadExcel").submit();
        }
    }
});

