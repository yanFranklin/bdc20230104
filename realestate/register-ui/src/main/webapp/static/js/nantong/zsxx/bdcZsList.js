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
                field: 'qszt', title: '状态',
                templet: function (d) {
                    if (d && qsztHistory == d.qszt) {
                        return '<p style="color:#f54743">已注销</p>';
                    } else {
                        return '<p style="color:#10a54a">正常</p>';
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
            {fixed: 'right', title: '操作', toolbar: '#barDemo'}
        ]];

        // 首次登记证明单不需要印制号
        if (zsmodel == sczmdModel) {
            col = [[
                {checkbox: true, fixed: true},
                {field: 'bdcqzh', title: '不动产权证号', minWidth: 270},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
                {field: 'zl', title: '坐落', minWidth: 250},
                {field: 'qlr', title: '权利人', minWidth: 90},
                {field: 'djyy', title: '登记原因', minWidth: 90},
                {field: 'mj', title: '面积', minWidth: 80},
                {field: 'zslxmc', title: '证书类型', minWidth: 90},
                {field: 'zsid', title: '证书ID', hide: true},
                {field: 'qszt', title: '权属状态', hide: true},
                {fixed: 'right', title: '操作', toolbar: '#barDemo'}
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
                    var sfdz = null;
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].zslx == 2) {
                            var jgmcList = [];
                            jgmc = data[i].qlr;
                            if(isNotBlank(data[i].qlr)){
                                jgmcList = data[i].qlr.split(" ");
                            }
                            for (var j = 0; j < jgmcList.length; j++) {
                                sfdz=getSfdz(jgmcList[j]);
                                if (sfdz == 0 || sfdz == "0") {
                                    break;
                                }
                            }
                            if (sfdz == 0 || sfdz == "0") {
                                break;
                            }
                        }
                    }
                    if (sfdz == 0 || sfdz == "0") {
                        var alertIndex = layer.open({
                            type: 1,
                            skin: 'bdc-small-tips',
                            title: '信息',
                            area: ['320px'],
                            content: jgmc+"已接入互联网+，<br/>禁止打印纸质证明",
                            btn: ['确定'],
                            btnAlign: 'c',
                            yes: function () {
                                layer.close(alertIndex);
                            }
                        });
                        break;
                    }
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].qzysxlh) {
                            warnMsg("已存在印制号的证书，将不会重复获取！");
                            break;
                        }
                    }
                    if (isSelectAll) {
                        getAllZsYzh();
                    } else {
                        getBatchYzh(data);
                    }
                    break;
                case 'batchPrint':
                    if (isSelectAll) {
                        allPrint();
                    } else {
                        batchPrint(data);
                    }
                    break;
                case 'fwqd':
                    printFwqd(processInsId, '', false);
                    break;
                case 'fwqdh':
                    printFwqd(processInsId, '', true);
                    break;
            }
        });

        /**
         * 是否打证
         */
        function getSfdz(jgmc){
            var sfdz = null;
            getSfdzUrl = "/realestate-register-ui/rest/v1.0/zsxx/xtjg/" + jgmc;
            $.ajax({
                url: getSfdzUrl,
                contentType: "application/json;charset=utf-8",
                type: "GET",
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data) {
                        sfdz = data.sfdz;
                    }
                },
                error: function () {
                    warnMsg("获取机构信息失败，请重试！");
                }

            });
            return sfdz;
        }
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
            var layerModel = layer;
            var layerShade = 0;
            // var layerArea = ['100%', '100%'];
            var operateWidth = $('.bdc-table-box').width() - 50 + 'px';
            var operateHeight = $('.bdc-table-box').height() - 20 + 'px';
            var isZsyl = false;
            var layerArea = [operateWidth,operateHeight];
            if (!isNullOrEmpty(zsyl) && zsyl == 'true') {
                url = initZsylUrl(data);
                isZsyl = true;

            } else {
                url = initZsUrl(data);
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
                    var getPage = $('.layui-table-page .layui-laypage-default span:nth-child(2) input').val();
                    if(isZsyl){
                        getPage = parseInt(getPage);
                        zsylTableRender(getPage);
                    }
                    // else {
                    //     refreshTable();
                    // }
                    layerModel.close(index);
                    return false;
                }
            });
        });
        table.on('checkbox(dataTable)', function(obj){
           if($('.bdc-zs-skin').length > 0){
                $(".bdc-zs-list-table [type='checkbox']").prop("checked",'');
                $(this).prop("checked", "checked");
                form.render('checkbox');
               var isZsyl = false;
               var newUrl="";
               if (!isNullOrEmpty(zsyl) && zsyl == 'true') {
                   newUrl = initZsylUrl(obj.data);
                   isZsyl = true;

               } else {
                   newUrl = initZsUrl(obj.data);
               }
                $('.bdc-zs-skin iframe').attr('src',newUrl);
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
                var ckzmd =checkZsIsCkzmd(data);
                var dyh = data.bdcdyh.replace("等", "");
                url = '/realestate-register-ui/view/zsxx/bdcSczmd.html?processInsId=' + processInsId + '&bdcdyh=' + dyh + '&zsyl=true&ckzmd=' + ckzmd;
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
                //海门需求29824,如果车库单独发证,生成车库/车位证明单
                var ckzmd =checkZsIsCkzmd(data);
                var dyh = data.bdcdyh.replace("等", "");
                url = '/realestate-register-ui/view/zsxx/bdcSczmd.html?processInsId=' + processInsId + '&bdcdyh=' + dyh + '&formStateId=' + formStateId + '&readOnly=' + readOnly + '&print=' + printValue + '&ckzmd=' + ckzmd;
            }
            return url;
        }

        // 预览情况下的表格渲染
        function zsylTableRender(page) {
            var getPage;
            if(page){
                getPage = page;
            }else {
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
                ntAllZsPrint(zsModelUrl, gzlslid, "zs");
            } else if (zslxChecked == zmModel) {
                ntAllZsPrint(zmModelUrl, gzlslid, "zm");
            } else if(zslxChecked == zmdModel){
                // 打印证明单
                var ckzmd =checkCkzmdByGzlslid(gzlslid);
                if(ckzmd){
                    ntAllZsPrint(ckzmdModelUrl, gzlslid, sczmdModel);
                }else {
                    ntAllZsPrint(zmdModelUrl, gzlslid, sczmdModel);
                }
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
            var zmdZsidArr =[];
            //打印类型种类
            var zsdylxZlArr =[];
            //区分首次证明单和车库证明单
            var isCkzmd =false;

            $.each(data, function (key, val) {
                // 首次证明单没有印制号，不需要验证
                if (zsmodel != sczmdModel &&val.zslx != zmdModel&& val.qszt == qsztValid && isNullOrEmpty(val.qzysxlh)) {
                    warnMsg(val.bdcqzh + "未保存印制号，不允许打印！");
                    return false;
                }
                if (!isNullOrEmpty(val.zsid.trim()) && val.qszt == qsztValid && (zsidArr.length == 0 || zsidArr.length > 0 && $.inArray(val.zsid, zsidArr) == -1)) {
                    zsidArr.push(val.zsid);
                    if (val.zslx == zsModel) {
                        if(!zsdylxZlArr.indexOf("zs") >-1){
                            zsdylxZlArr.push("zs");
                        }
                        zsZsidArr.push(val.zsid);

                    }
                    if (val.zslx == zmModel) {
                        if(!zsdylxZlArr.indexOf("zm") >-1){
                            zsdylxZlArr.push("zm");
                        }
                        zmZsidArr.push(val.zsid);
                    }
                    if (val.zslx == zmdModel) {
                        if(!zsdylxZlArr.indexOf("zmd") >-1){
                            zsdylxZlArr.push("zmd");
                        }
                        var ckzmd =checkZsIsCkzmd(val);
                        if(ckzmd){
                            isCkzmd =true;
                        }
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
            if (zsdylxZlArr.length >1) {
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
                            if(isCkzmd){
                                ntBatchZsPrint(ckzmdModelUrl, zmdZsidArr.join(zf_ywdh), sczmdModel);
                            }else {
                                ntBatchZsPrint(zmdModelUrl, zmdZsidArr.join(zf_ywdh), sczmdModel);
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
                // 打印证书
                if (zsZsidArr.length > 0) {
                    ntBatchZsPrint(zsModelUrl, zsZsidArr.join(zf_ywdh), "zs");
                }
                // 打印证明
                if (zmZsidArr.length > 0) {
                    ntBatchZsPrint(zmModelUrl, zmZsidArr.join(zf_ywdh), "zm");
                }
                // 打印证明单
                if (zmdZsidArr.length > 0) {
                    if(isCkzmd){
                        ntBatchZsPrint(ckzmdModelUrl, zmdZsidArr.join(zf_ywdh), sczmdModel);
                    }else {
                        ntBatchZsPrint(zmdModelUrl, zmdZsidArr.join(zf_ywdh), sczmdModel);
                    }
                }
            }
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

        // 校验证书是否为车库车位证明单
        function checkZsIsCkzmd(zsData){
            var ckzmd =false;
            var isZsyl =false;
            if (!isNullOrEmpty(zsyl) && zsyl == 'true') {
                isZsyl =true;
            }
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/checkZsIsCkzmd?gzlslid="+processInsId+"&zsyl="+isZsyl,
                type: "POST",
                contentType: 'application/json',
                dataType: "json",
                async: false,
                data: JSON.stringify(zsData),
                success: function (data) {
                    ckzmd =data;
                },
                error: function () {
                    warnMsg("校验证书是否为车库车位证明单异常");
                }
            });
            return ckzmd;
        }

        function checkCkzmdByGzlslid(gzlslid){
            var ckzmd =false;
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/judgeZmdType/" + gzlslid +"?filterZslx=true",
                type: "POST",
                contentType: 'application/json',
                dataType: "json",
                async: false,
                success: function (data) {
                    ckzmd =data;
                },
                error: function () {
                    warnMsg("校验证书是否为车库车位证明单异常");
                }
            });
            return ckzmd;

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
});

