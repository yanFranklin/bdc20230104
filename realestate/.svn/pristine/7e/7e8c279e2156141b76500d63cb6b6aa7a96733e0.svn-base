var bdcslxztzpz;
//不动产类型字典项
var zdBdclxList = {};
//规划用途字典项
var zdGhytList = {};
//查封类型字典项
var zdCflxList = {};
var bdclx;
var bdclxList = [];
var bdcdyfwlxList = [];
//已选信息
var checkData = [];
//是否创建标志
var sfcj;
var warnLayer;
//根据选择的数据进行操作时，如果isSelectAll为true，操作包含本页的全部数据
var isSelectAll = false;
//判断是否增量初始化
var zlcsh = getQueryString("zlcsh");
//判断是否组合或者单个进行增量初始化
var zllx = getQueryString("zllx");
//判断是否例外初始化
var qllx = getQueryString("qllx");
//受理编号
var slbh = getQueryString("slbh");
//是否展示幢号
var showZh = false;
//查询参数对象
var cxObj = {};
//字典集合
var zdList = {a: []};
/**
 * 一窗受理流程登记流程对照关系，当前流程为一窗流程查到的数据
 */
var ycslDjywDzb = {};

//是否重新加载楼盘表
var sfcxjzlpb = false;

/**
 *合同信息对象
 */
var htxxObj = {};

// 防止偶尔页面上获取不到值报错
var processDefKey = getQueryString("processDefKey");
var jbxxid = getQueryString("jbxxid");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
//选择台账功能类型
var xztztype = getQueryString("xztztype");

// 查询方式
// 0：查登记库 适用非于常州
// 1：查原登记库 适用于常州
var queryType = "0";

var loadTableData = [];
//以下是有关查档信息全局变量
var cdlb = '0';
var cdqlr = '';
var cdcqzh = '';
var cdzl = '';
var cdqlrZjh = '';
var xzly = '';
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    addModel();
    setTimeout(function () {
        try {
            $.when(loadSlymZdbData(), loadBdcSlXztzPz()).done(function () {
                //页面加载时清除缓存数据
                sessionStorage.checkedData = '{}';
                sessionStorage.allData = '{}';
                removeModal();
                form.render();
            })
        } catch (e) {
            removeModal();
            ERROR_CONFIRM("加载错误", e.message);
            return
        }
    }, 10);

    $(function () {
        //关闭处理
        window.onunload = function (e) {
            clear()
        };

        //外联证书,隐藏购物车图标
        if(xztztype ==="wlzs"){
            $(".bdc-car").remove();
        }

        //单击高级查询
        $('.bdc-form-div').on('click', '.seniorSearch', function () {
            $('.layui-show .pf-senior-show').toggleClass('bdc-hide');
            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }
            $('.layui-show .bdc-percentage-container').css('padding-top', $('.layui-show .bdc-search-content').height() + 10);

            if ($('.layui-show .bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-show .layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
            } else {
                $('.layui-show .layui-table-body').height($('.layui-show .bdc-table-box').height() - 131);
                $('.layui-show .layui-table-fixed .layui-table-body').height($('.layui-show .bdc-table-box').height() - 131 - 17);
            }
        });
    });
});

/*
受理选择台账根据配置加载
* */
function loadBdcSlXztzPz() {
    $.ajax({
        url: getContextPath() + "/bdcdyh/",
        type: 'GET',
        async: false,
        data: {processDefKey: processDefKey},
        success: function (data) {
            if (isNotBlank(data)) {
                var xztzlxList = [];
                if (isNotBlank(data.xztzlx)) {
                    xztzlxList = data.xztzlx.split(",");
                }
                if (isNotBlank(data.xmsjly)) {
                    queryType = data.xmsjly;
                }
                if (isNotBlank(data.bdclx)) {
                    bdclxList = data.bdclx.split(",");
                    bdclxList.forEach(function (v) {

                        if ((v == '2' || v == '4' || v == '10') && queryType == "0") {
                            showZh = true;
                        }
                    });
                }
                if (isNotBlank(data.dzwtzm)) {
                    if (data.dzwtzm.indexOf('F') != -1 && queryType == "0") {
                        showZh = true;
                    }
                }
                if (isNotBlank(data.bdcdyfwlx)) {
                    bdcdyfwlxList = data.bdcdyfwlx.split(",");
                }
                if (xztztype === "wlzs") {
                    xztzlxList = ["5"];
                }
                data.xztzlxList = xztzlxList;
                bdcslxztzpz = data;
                if (zlcsh === "true") {
                    //增量初始化如果是自定义配置的读取配置数据
                    if (isNotBlank(zdyZlcshXztzPz) && isNotBlank(zdyZlcshXztzPz[processDefKey])) {
                        var zdypz = zdyZlcshXztzPz[processDefKey];
                        for (var i in zdypz) {
                            bdcslxztzpz[i] = zdypz[i];
                        }
                        if (isNotBlank(bdcslxztzpz.xztzlx)) {
                            bdcslxztzpz.xztzlxList = bdcslxztzpz.xztzlx.split(",");
                        }
                    }
                }
                generateTableUi();
                generateTableContent();
            } else {
                showAlertDialog("选择台账未配置，请检查！")
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr, "加载受理选择台账配置失败");
        }
    });
}

function generateTableUi() {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {
            bdcslxztzpzdo: bdcslxztzpz
        };
        var tpl = tableUiTpL.innerHTML, view = document.getElementById("tableUi");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        renderDate(form);
    })
}

function generateTableContent() {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'table'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var element = layui.element;
        var table = layui.table;
        if (bdcslxztzpz.xztzlxList !== null && bdcslxztzpz.xztzlxList.length > 0) {
            for (var i = 0; i < bdcslxztzpz.xztzlxList.length; i++) {
                var xztzlx = bdcslxztzpz.xztzlxList[i];
                var view = document.getElementById("tableContent");
                var isFirst = false;
                var tpl;
                if (i === 0) {
                    isFirst = true;
                }
                var cxtjObj =initCxtj(bdcslxztzpz.cxtj);
                var json = {
                    isFirst: isFirst,
                    cxtj:cxtjObj,
                    qjgldmList:bdcslxztzpz.qjgldmList
                };
                if (xztzlx === "1") {
                    tpl = dyhTpl.innerHTML;
                }
                if (xztzlx === "2") {
                    tpl = xmTpl.innerHTML;
                    json['showZh'] = showZh;
                }
                if (xztzlx === "3") {
                    tpl = cfTpl.innerHTML;
                }
                if (xztzlx === "4") {
                    tpl = ljzTpl.innerHTML;
                }
                if (xztzlx === "5") {
                    tpl = wlzsTpl.innerHTML;
                }
                if (xztzlx === "6") {
                    tpl = hthTpl.innerHTML;
                }
                if (xztzlx === "7") {
                    tpl = zssdTpl.innerHTML;
                }
                if (xztzlx === "8") {
                    tpl = cdTpl.innerHTML;
                    json['showZh'] = showZh;
                }
                if (xztzlx === "9") {
                    tpl = xzTpl.innerHTML;
                }
                if (xztzlx === "10") {
                    tpl = dysdTpl.innerHTML;
                }
                if (xztzlx === "12") {
                    tpl = sfytxzTpl.innerHTML;
                }
                //渲染数据
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = view.innerHTML + html;
                });
            }
            renderDate(form);
            //不动产单元
            initBdcdyhTable();
            //项目
            initXmTable();
            //查封
            initCfTable();
            //逻辑幢
            initLjzTable();
            //外联证书
            initWlzsTable();

            //证书锁定
            initZsSdTable();

            //单元锁定
            initDySdTable();

            //合同号
            initHthTable();

            //查档
            initCdTable();

            //修正
            initXzTable();
            //收费用途修正
            initSfytxzTable();

            //监听第一次单击tab，重构表格尺寸
            var xmIndex = 0,
                cfIndex = 0,
                ljzIndex = 0,
                wlzsIndex = 0,
                zssdIndex = 0,
                dysdIndex = 0,
                hthIndex = 0,
                bdcdyhIndex = 0,
                cdIndex = 0,
                xzIndex = 0;

            element.on('tab(tabFilter)', function (data) {
                $('.content-title').width($('.layui-tab').width() + 30);
                switch (data.elem.context.value) {
                    case 2:
                        if (xmIndex === 0) {
                            xmIndex++;
                            table.resize('xmList');
                            table.resize('xmid');
                        }

                        break;
                    case 3:
                        if (cfIndex === 0) {
                            cfIndex++;
                            table.resize('cfList');
                            table.resize('qlid');
                        }

                        break;
                    case 4:
                        if (ljzIndex === 0) {
                            ljzIndex++;
                            table.resize('ljzList');
                            table.resize('fwDcbIndex');
                        }

                        break;
                    case 5:
                        if (wlzsIndex === 0) {
                            wlzsIndex++;
                            table.resize('wlzsList');
                            table.resize('wlzs');
                        }

                        break;

                    case 6:
                        if (hthIndex === 0) {
                            hthIndex++;
                            table.resize('hthList');
                            table.resize('htbh');
                        }

                        break;
                    case 7:
                        if (zssdIndex === 0) {
                            zssdIndex++;
                            table.resize('zssdList');
                            table.resize('zssd');
                        }
                        break;

                    case 1:
                        if (bdcdyhIndex === 0) {
                            bdcdyhIndex++;
                            table.resize('bdcdyhList');
                            table.resize('qjid');
                        }
                        break;
                    case 8:
                        if (bdcdyhIndex === 0) {
                            cdIndex++;
                            table.resize('cdList');
                            table.resize('cdid');
                        }
                        break;
                    case 9:
                        if (bdcdyhIndex === 0) {
                            xzIndex++;
                            table.resize('xzList');
                            table.resize('xz');
                        }
                        break;
                    case 10:
                        if (dysdIndex === 0) {
                            dysdIndex++;
                            table.resize('dysdList');
                            table.resize('dysd');
                        }
                        break;
                }
            });
            renderSearchInput();
            //给下拉框添加删除图标
            renderSelectClose(form);

        } else {
            layer.alert("选择台账未配置！")
        }
    })
}

function initBdcdyhTable() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;


        // 当前页数据
        var currentPageData = [];
        //获取查询总页数
        var totalPages = 0;


        var isSearch = true;
        //监听回车事件
        $(document).keydown(function (event) {
            var tab = $('.layui-this').val();
            if (event.keyCode == 13 && isSearch) { //绑定回车
                if (tab === 1) {
                    $("#queryBdcdyh").click();
                } else if (tab === 2) {
                    $("#queryXm").click();
                } else if (tab === 3) {
                    $("#queryCf").click();
                } else if (tab === 4) {
                    $("#queryLjz").click();
                } else if (tab === 5) {
                    $("#queryWlzs").click();
                }else if (tab === 6) {
                    $("#queryHth").click();
                }else if (tab === 7) {
                    $("#queryZsSd").click();
                }else if (tab === 8) {
                    $("#queryCd").click();
                }else if (tab === 9) {
                    $("#queryXz").click();
                }else if(tab === 10) {
                    $("#queryDySd").click();
                }
                return false;
            }
        });
        //判断回车操作
        $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });


        //不动产单元号的表头
        var unitTableTitle = [{type: 'checkbox', fixed: 'left', width: 50},
            {
                field: 'bdcdyh', title: '不动产单元号', minWidth: 300, templet: '#bdcdyhTpl'

            },
            {field: 'zl', title: '坐落', minWidth: 250},
            {field: 'qlr', title: '权利人', minWidth: 124},
            {field: 'bdclx', title: '不动产类型', minWidth: 70, templet: '#bdclx'},
            {field: 'fjh', title: '房间号', minWidth: 70, hide: true},
            {field: 'zt', title: '状态', minWidth: 130, templet: '#bdcdyzt', align: "center"},
            {field: 'sdzt', title: '锁定类型，锁定原因', minWidth: 200, align: "center"},
            {field: 'yt', title: '用途', hide: true},
            {field: 'cz', title: '操作', width: 200, templet: '#dyhcz', align: "center", fixed: "right"}
        ];

        //提交表单
        form.on("submit(queryBdcdyh)", function (data) {
            addModel();
            //查询时清除缓存数据
            sessionStorage.checkedData = '{}';
            sessionStorage.allData = '{}';
            isSelectAll = false;
            var url = getContextPath() + '/bdcdyh/listBdcdyhByPageJson?loadpage=true';
            cxObj = data.field;
            cxObj.zl = deleteWhitespace(cxObj.zl, "edge");
            if (isNotBlank(cxObj.zl)) {
                cxObj.zl = cxObj.zl.replace(/,/g, '%').replace(/ /g, "%").replace(/，/g, "%");
            }
            //添加模糊类型
            // cxtjAddMhlx(cxObj);
            cxObj.qllx = bdcslxztzpz.qllx;
            cxObj.zdtzm = bdcslxztzpz.zdtzm;
            cxObj.dzwtzm = bdcslxztzpz.dzwtzm;
            if (!isNotBlank(cxObj.bdcdyfwlx)) {
                cxObj.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
            }
            if (isNotBlank(cxObj.bdcdyfwlx) && (cxObj.bdcdyfwlx === "xmxx" || cxObj.bdcdyfwlx === "ljz" || cxObj.bdcdyfwlx === "hs" || cxObj.bdcdyfwlx === "ychs")) {
                //bdcdyfwlx有值,dzwtzm为F
                cxObj.dzwtzm = "F";
            }
            cxObj.dyhcxlx = bdcslxztzpz.dyhcxlx;

            if(bdcslxztzpz.qxList &&bdcslxztzpz.qxList.length >0) {
                cxObj.qxdmList = bdcslxztzpz.qxList.join(",");
            }
            //加载锁定原因
            cxObj["sfsdzt"] = "1";
            tableReload('qjid', cxObj, url);
            return false;
        });

        //监听不动产单元房屋类型下拉框
        // form.on('select(bdcdyfwlx)', function (data) {
        //     if (isNotBlank(data.value) && data.value.indexOf("hs") < 0) {
        //         $("#fjh").val("");
        //         $("#fjh").parents(".layui-inline").hide();
        //     } else {
        //         $("#fjh").parents(".layui-inline").show();
        //
        //     }
        // });
        //监听不动产类型下拉框
        // form.on('select(selectedBdclx)', function (data) {
        //     if (isNotBlank(data.value) && data.value !== "2") {
        //         $("#fjh").val("");
        //         $("#fjh").parents(".layui-inline").hide();
        //     } else {
        //         $("#fjh").parents(".layui-inline").show();
        //     }
        // });


        var tableConfig = {
            id: 'qjid',
            toolbar: "#toolbarBdcdyh",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            // page: true,  //开启分页
            parseData: function (res) {
                currentPageData = res.content;
                totalPages = res.totalPages;
                // 设置选中行
                var checkedData = JSON.parse(sessionStorage.checkedData);
                if (res.content && res.content.length > 0 && !$.isEmptyObject(checkedData)) {
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.qjid) {
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }
            },
            done: function (res, curr, count) {
                var reverseList = ['zl'];
                reverseTableCell(reverseList, 'qjid');
                //无数据时显示内容调整
                if ($('.layui-show .layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        };

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(bdcdyhList)', function (obj) {
            var checkedData = obj.type == 'one' ? [obj.data] : currentPageData;
            var myCheckedData = JSON.parse(sessionStorage.checkedData);
            var myAllData = JSON.parse(sessionStorage.allData);
            $.each(checkedData, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    myCheckedData[v.qjid] = v;
                } else {
                    //.删除
                    myCheckedData = deleteCheckedById(myCheckedData, v.qjid);
                }
                if (isSelectAll) {
                    v.checked = obj.checked;
                    myAllData[v.qjid] = v;
                }
            });
            sessionStorage.checkedData = JSON.stringify(myCheckedData);
            sessionStorage.allData = JSON.stringify(myAllData);
            removeModal();

        });

        //监听单元号点击
        $('.bdc-tab-box').on('click', '.bdc-bdcdyh-click', function () {
            getSelectBdcdyData($(this).data('json'), true);
        });

        //加载表格
        loadDataTablbeByUrl('#bdcdyhList', tableConfig);
        //表格初始化
        table.init('bdcdyhList', tableConfig);

        //头工具栏事件
        table.on('toolbar(bdcdyhList)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === "addBdcdyhShoppingCar") { //添加到购物车
                var checkedData = JSON.parse(sessionStorage.checkedData);
                if ($.isEmptyObject(checkedData)) {
                    layer.alert("未选择数据!");
                }
                checkData = [];
                $.each(checkedData, function (key, value) {
                    checkData.push(value);
                });
                if (checkData !== null && checkData.length > 0) {
                    sfcj = false;
                    addModel();
                    checkBdcdyh(checkData, false);
                } else {
                    layer.alert("未选择数据!");
                }
            } else if (layEvent === "allchoosebdcdyh") { //全选
                var url = getContextPath() + '/bdcdyh/listBdcdyhByPageJson?loadpage=false';
                addModel();
                if (cxObj.zl !== "" || cxObj.qlr !== "" || cxObj.bdcdyh !== "" || cxObj.fjh !== "") {
                    bdcdyhSelectAllFunction(cxObj, url, 'qjid', 'qjid');
                } else {
                    removeModal();
                    showAlertDialog("请先输入查询条件再全选")
                }


            } else if (layEvent === "allunchoosebdcdyh") {//反选
                var url = getContextPath() + '/bdcdyh/listBdcdyhByPageJson?loadpage=false';
                addModel();
                if (cxObj.zl !== "" || cxObj.qlr !== "" || cxObj.bdcdyh !== "" || cxObj.fjh !== "") {
                    bdcdyhInverseSelectFunction(cxObj, url, 'qjid', 'qjid');
                } else {
                    removeModal();
                    showAlertDialog("请先输入查询条件再反选")
                }
            }
        });
        table.on('tool(bdcdyhList)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'cshBdcdyh') {
                checkData = [];
                checkData.push(obj.data);
                sfcj = true;
                if (checkData !== null) {
                    addModel();
                    setTimeout(function () {
                        try {
                            $.when(checkBdcdyh(checkData, sfcj)).done(function () {
                            })
                        } catch (e) {
                            removeModal();
                            ERROR_CONFIRM("加载失败", e.message);
                            return
                        }
                    }, 1);
                }
            } else if (obj.event === 'addBdcdyh') {
                checkData = [];
                checkData.push(obj.data);
                sfcj = false;
                if (checkData !== null) {
                    addModel();
                    setTimeout(function () {
                        try {
                            $.when(checkBdcdyh(checkData, sfcj)).done(function () {
                            })
                        } catch (e) {
                            removeModal();
                            ERROR_CONFIRM("加载失败", e.message);
                            return
                        }
                    }, 1);
                }

            }
        });
        //渲染bdclx下拉框
        if (bdclxList.length === 0) {
            $('#selectedBdclx').append(new Option("请选择", ""));
        }
        if (zdBdclxList && zdBdclxList.length > 0) {
            $.each(zdBdclxList, function (index, item) {
                if (bdclxList.length === 0 || bdclxList.indexOf(item.DM + "") > -1) {
                    $('#selectedBdclx').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
                }
            });
        }
        if (zdGhytList && zdGhytList.length > 0) {
            $('#selectedGhyt').append(new Option("请选择", ""));
            $.each(zdGhytList, function (index, item) {
                $('#selectedGhyt').append(new Option(item.MC, item.DM));
            });
        }
        //渲染bdcdyfwlx下拉框
        if (bdcdyfwlxList.length !== 1) {
            $('#bdcdyfwlx').append(new Option("请选择", ""));
        }
        if (zdbdcdyfwlxList.length > 0) {
            $.each(zdbdcdyfwlxList, function (index, item) {
                if (bdcdyfwlxList.length === 0 || bdcdyfwlxList.indexOf(item.DM + "") > -1) {
                    $('#bdcdyfwlx').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
                }
            });
        }
        //房间号不可编辑
        if (bdcdyfwlxList.length > 0 && bdcdyfwlxList.indexOf("hs") < 0) {
            $("#fjh").val("");
            $("#fjh").parents(".layui-inline").hide();
        }
        var bdcdyfwlx = $("#bdcdyfwlx").val();
        if (isNotBlank(bdcdyfwlx) && bdcdyfwlx.indexOf("hs") < 0) {
            $("#fjh").val("");
            $("#fjh").parents(".layui-inline").hide();
        }


        layui.form.render("select");
    })
}

//获取一窗对照数据
function queryYcslDjywDzb() {
    //判断是否为一窗创建,对照表存在数据,即为一窗流程
    getReturnData("/slxxcsh/queryYcslDjywDzb", {gzldyid: processDefKey}, "GET", function (data) {
        ycslDjywDzb = data;
    }, function (error) {
        delAjaxErrorMsg(error);
    }, false);
}

function addBdcdyh(bdcCshSlxmDTO) {
    $.ajax({
        url: getContextPath() + "/addbdcdyh",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcCshSlxmDTO),
        success: function (data) {
            if (sfcj) {
                if (processDefKey !== "" && jbxxid !== "") {
                    cshSelectedXxSingle(jbxxid, processDefKey);
                }
            } else {
                ityzl_SHOW_SUCCESS_LAYER("添加成功");
                addGwc();
                removeModal();
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
            removeModal();
        }
    });
}

/*
* 加载受理页面字典项
* */
function loadSlymZdbData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (JSON.stringify(data) != "{}" && data !== null && data.bdclx !== null && data.cflx !== null) {
                zdBdclxList = data.bdclx;
                zdCflxList = data.cflx;
                zdGhytList = data.fwyt;
            }
            zdList = data;
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr, "加载字典项失败");
        }
    });
}


/*
* 不动产类型代码转名称方法
* */
function changeBdclx(bdcdyh, lx) {
    var bdclx = document.getElementById("selectedBdclx");
    var bdclxMc = "";
    if (bdclx.selectedIndex > -1) {
        bdclxMc = bdclx.options[bdclx.selectedIndex].innerText;
        if (bdclxMc === "请选择") {
            bdclxMc = "";
        }
    }
    if (bdclxMc === "") {
        $.ajax({
            url: getContextPath() + "/bdcdyh/queryBdclxByBdcdyh",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {bdcdyh: bdcdyh, lx: lx, gzldyid: processDefKey},
            success: function (data) {
                if (data.bdclx !== null) {
                    for (var i in zdBdclxList) {
                        if (data.bdclx === zdBdclxList[i].DM) {
                            bdclxMc = zdBdclxList[i].MC;
                            return bdclxMc;
                        }
                    }
                }
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr, "获取不动产类型失败");
            }
        });
    }

    return bdclxMc;
}

/*
* 展现不动产单元详情登记历史关系
*/
function showBdcdyXqNew(bdcdyh, tzym) {
    var url = "/realestate-register-ui/view/lsgx/bdcdyDjLsgxNew.html?bdcdyh=" + bdcdyh + "&tzym=" + tzym;
    layerCommonOpenFull(url, "登记历史关系", '1150px', '600px');
}

// 查封查封登记历史
function showCfdjls(bdcdyh) {
    var url = "/realestate-register-ui/view/lsgx/cfdjls.html?bdcdyh=" + bdcdyh;
    layerCommonOpenFull(url, "查封登记历史", '1150px', '600px');
}

function showYlc(gzlslid, xmly, xmid) {
    if (!isNullOrEmpty(gzlslid) && '1' == xmly) {
        window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + gzlslid);
    } else {
        window.open('/realestate-register-ui/view/lsgx/new-page.html?xmid=' + xmid + '&processInsId=' + gzlslid + '&version=changzhou');
    }
}

/*
* 展现匹配页面
* */
function showMatchData(data, xztzlx) {
    var index = layer.open({
        type: 2,
        title: "数据匹配",
        area: ['1150px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: getContextPath() + '/view/query/matchData.html?xztzlx=' + xztzlx + "&jbxxid=" + jbxxid,
        success: function (layero, index) {
            sessionStorage.setItem('matchData' + jbxxid, data);
        }

    });
    layer.full(index);
}

//用于匹配之后台账页面的刷新
function loadSearch(bdcdyh, lsdyh, fs, xztzlx) {
    var ybdcdyh = $(".layui-show [name=bdcdyh]").val();
    if (isNotBlank(ybdcdyh) && fs === "match") {
        //用匹配后不动产单元号替换原虚拟不动产单元号搜索条件
        $($(".layui-show [name=bdcdyh]")).val(bdcdyh);
    } else if (ybdcdyh === bdcdyh && fs === "dismatch") {
        //取消匹配还原
        $($(".layui-show [name=bdcdyh]")).val(lsdyh);
    }
    if (xztzlx === "2") {
        $("#queryXm").click();
    } else if (xztzlx === "3") {
        $("#queryCf").click();
    } else if (xztzlx === "5") {
        $("#queryWlzs").click();
    }
    removeModal();
}

/*
* 获取单元号查询类型
* */
function getDyhcxlx(lx) {
    var dyhcxlx = "";
    if (lx === "GZW") {
        dyhcxlx = "3";
    } else if (lx === "ZH") {
        dyhcxlx = "2";
    } else {
        dyhcxlx = "1";
    }
    return dyhcxlx;

}

/*
* 规则验证点击取消或者忽略等按钮添加到购物车
* */
function removeBdcdy() {
    //添加到购物车操作
    addBdcdyhShoppingCar(checkData, sfcj);
}

//全选查询
function getAllData(cxObj, url) {
    var alldata = [];
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        async: false,
        contentType: "application/json;charset=UTF-8",
        data: cxObj,
        success: function (data) {
            alldata = data;
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr, "全选数据失败");

        }

    });
    return alldata;
}

//处理验证结果
function dealYzResult(yzResult) {
    var yzbdcdyhArr = [];
    //验证通过的数据
    var addData = [];
    //去掉验证通过后的数据
    var newcheckData = [];
    if (yzResult.length > 0) {
        //重新组织数据，验证通过放入addData直接添加，不通过的保留
        for (var i = 0; i < yzResult.length; i++) {
            if (isNotBlank(yzResult[i].bdcdyh)) {
                yzbdcdyhArr.push(yzResult[i].bdcdyh);
            }
        }
        for (var i = 0; i < checkData.length; i++) {
            //验证通过放入addData
            if (yzbdcdyhArr.indexOf(checkData[i].bdcdyh) < 0) {
                addData.push(checkData[i]);
            } else {
                newcheckData.push(checkData[i]);
            }
        }
        checkData = newcheckData;
    } else {
        addData = checkData;
    }
    return addData;
}

/**
 * @param yzResult, yzlx 验证结果，验证类型
 * @param removeCallback 忽略后执行方法
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 展现规则验证后的提示信息
 * @date : 2020/5/8 9:41
 */
function showXzxx(yzResult, yzlx,removeCallback) {
    if (yzResult.length > 0) {
        // 规则验证只存在备案信息，不提示信息直接带入数据做特殊处理
        var tsgz = false;
        var tsgz_size = 0;
        $.each(yzResult, function (index, item) {
            var fcjyBaxxDTO = {};
            if (item.xzxx !== null && item.xzxx != undefined) {
                if (isArray(item.xzxx) && item.xzxx.length > 0 && !$.isEmptyObject(item.xzxx[0])) {
                    xmid = item.xzxx[0].XMID;
                    qlid = item.xzxx[0].QLID;
                } else if (!$.isEmptyObject(item.xzxx.bdcSlJyxx)) {
                    fcjyBaxxDTO = item.xzxx;
                    if (isArray(item.xzxx.bdcSlJyxx) && item.xzxx.bdcSlJyxx.length > 0) {
                        fcjyBaxxDTO.bdcSlJyxx = item.xzxx.bdcSlJyxx[0];
                    }
                    if (isArray(item.xzxx.bdcSlFwxx) && item.xzxx.bdcSlFwxx.length > 0) {
                        fcjyBaxxDTO.bdcSlFwxx = item.xzxx.bdcSlFwxx[0];
                    }
                } else if (typeof item.xzxx === "object") {
                    xzgzlslid = item.xzxx.gzlslid;
                }
            }
            if (item.yzlx === "alert") {
                if (!$.isEmptyObject(fcjyBaxxDTO)) { // 商品房交易获取交易信息，为警告提醒不允许忽略
                    for (var i = 0; i < checkData.length; i++) {
                        var selectData = checkData[i];
                        if (selectData.bdcdyh === item.bdcdyh) {
                            selectData.fcjyBaxxDTO = fcjyBaxxDTO;
                        }
                    }
                    tsgz_size++;
                }
            }
        });
        if (tsgz_size == yzResult.length) {
            tsgz = true;
        }
        if (!tsgz) {
            loadTsxx(yzResult, yzlx,removeCallback);
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var $ = layui.jquery,
                    layer = layui.layer;
                warnLayer = layer.open({
                    type: 1,
                    title: '提示信息',
                    skin: 'bdc-tips-right',
                    shade: [0],
                    area: ['600px'],
                    offset: 'r', //右下角弹出
                    time: 5000000, //2秒后自动关闭
                    anim: 2,
                    content: $('#tsxx').html(),
                    success: function () {
                        $('.layui-layer-ico .layui-layer-close .layui-layer-close1').on('click', function () {
                            layer.close(warnLayer);
                            generate();
                        })
                    },
                    cancel: function () {
                        layer.close(warnLayer);
                        generate();
                    }
                });
            });
        } else {
            removeTsxx(yzlx,removeCallback);
        }
    }

}

/**
 * 针对于规则中获取交易信息不显示，直接带入数据特殊处理
 * @param yzlx
 */
function removeTsxx(yzlx,removeCallback) {
    addModel();
    var val = $('.layui-this').val();
    var isParent = false;
    if (val == "") {
        val = parent.$('.layui-this').val();
        isParent = true;
    }

    //在没有警告提示下创建
    if (alertSize === 0 && confirmSize === 0) {

        // 创建验证 忽略后直接创建流程
        if (yzlx == "cjyz") {
            if (isParent) {
                parent.cshSelectedXxSingle(jbxxid, processDefKey, false, false);
            } else {
                cshSelectedXxSingle(jbxxid, processDefKey, false, false);
            }
            return;
        }
        if (warnLayer != undefined) {
            layer.close(warnLayer);
        } else {
            layer.close(layer.index);
        }
        generate();
        //如果传入忽略执行回调方法,以传的为准
        if(removeCallback){
            removeModal();
            removeCallback();
            return false;
        }
        if (val === 1) {
            removeBdcdy();
        } else if (val === 2) {
            if (isParent) {
                parent.removeXm();
            } else {
                removeXm();
            }

        } else if (val === 3) {
            removeCf();
        } else if (val === 4) {
            removeLjz();
        } else if (val === 5) {
            if (typeof (eval("confirmSfzxyql")) == "function") {
                confirmSfzxyql();
            } else {
                addWlzs(checkData);
            }

        } else {
            //其余统一命名回调函数
            if (eval("removeTsxxCallBack")) {
                eval("removeTsxxCallBack()");
            } else {
                removeModal();
            }

        }
    } else {
        removeModal();
    }
}

//直接点击单元号超链接创建
getSelectBdcdyData = function (elem, cj) {
    addModel();
    checkData = [];
    checkData.push(elem);
    sfcj = cj;
    //验证单元号，验证通过加入购物车，不通过给出提示信息
    checkBdcdyh(checkData, sfcj);
};

/**
 * @param checkdData, sfcj 选中的数据， 是否创建
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 验证不动产单元号，通过加入购物车，不通过提示
 * @date : 2020/5/8 9:43
 */
function checkBdcdyh(checkdData, sfcj) {
    var selectDataList = [];
    for (var i = 0; i < checkdData.length; i++) {
        var selectData = checkdData[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.ysfwbm = selectData.ysfwbm;
        bdcGzYzsjDTO.qjgldm = selectData.qjgldm;
        if (hasProperty(bdcslxztzpz, "qllx")) {
            bdcGzYzsjDTO.qllx = bdcslxztzpz.qllx;
        }
        bdcGzYzsjDTO.processDefKey = processDefKey;
        //用于交易信息获取参数
        var obj = {};
        obj.fwbm = selectData.ysfwbm;
        obj.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.obj = obj;
        selectDataList.push(bdcGzYzsjDTO);
    }
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = processDefKey + "_XZBDCDY";
    bdcGzYzQO.paramList = selectDataList;
    $.ajax({
        url: getContextPath() + '/bdcGzyz',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcGzYzQO),
        success: function (data) {
            var addData = dealYzResult(data);
            //正常验证通过的数据直接添加
            if (addData.length > 0) {
                addBdcdyhShoppingCar(addData, sfcj);
            } else {
                removeModal();
            }
            //展现限制信息
            showXzxx(data);
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });


}

//添加不动产单元
function addBdcdyhShoppingCar(checkdData, sfcj) {
    if (sfcj) {
        //如果购物车存在数据，先清除购物车数据
        var xmcount = $(".bdc-car").find("span")[0].textContent;
        if (xmcount !== "0") {
            clearGwc(jbxxid,false);
        }
    }
    var selectDataList = [];
    var sfzlcsh = (zlcsh === "true") ? 1 : null;
    for (var i = 0; i < checkdData.length; i++) {
        var selectData = checkdData[i];
        var bdcSlYwxxDTO = {};
        bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlr;
        bdcSlYwxxDTO.qjid = selectData.qjid;
        bdcSlYwxxDTO.qjqlrgxid = selectData.qjqlrgxid;
        bdcSlYwxxDTO.dyhcxlx = getDyhcxlx(selectData.lx);
        bdcSlYwxxDTO.lx = selectData.lx;
        bdcSlYwxxDTO.sfzlcsh = sfzlcsh;
        bdcSlYwxxDTO.fcjyBaxxDTO = selectData.fcjyBaxxDTO;
        bdcSlYwxxDTO.bdcWlSlXmLsgxDOList = selectData.bdcWlSlXmLsgxDOList;
        bdcSlYwxxDTO.dyxmidList = selectData.dyxmidList;
        bdcSlYwxxDTO.qjgldm = selectData.qjgldm;
        bdcSlYwxxDTO.qllx = selectData.qsxz;
        bdcSlYwxxDTO.yt = selectData.yt;
        bdcSlYwxxDTO.jzmj = selectData.jzmj;
        selectDataList.push(bdcSlYwxxDTO);
    }
    var bdcCshSlxmDTO = {};
    bdcCshSlxmDTO.bdcSlYwxxDTOList = selectDataList;
    bdcCshSlxmDTO.gzldyid = processDefKey;
    bdcCshSlxmDTO.jbxxid = jbxxid;
    $.ajax({
        url: getContextPath() + "/addbdcdyh",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcCshSlxmDTO),
        success: function (data) {
            if (sfcj) {
                if (processDefKey !== "" && jbxxid !== "") {
                    cshSelectedXxSingle(jbxxid, processDefKey, false, true, 1);
                }
            } else {
                ityzl_SHOW_SUCCESS_LAYER("添加成功");
                addGwc();
                removeModal();
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

/**
 * 创建流程时的创建验证
 * @param jbxxid
 * @param processDefKey
 * @param isFrame 为true时，说明是购物车的创建，创建后需要往上取父页面
 * @param needYz 当needYz 为true的时候，需要验证，否则不需要验证
 * @param xztzlx 选择台账类型，1位单元号，2为产权证，3 为查封
 * @param tslc 特殊流程
 * @description 初始化购物车中的数据，初始化前进行创建验证
 */
function cshSelectedXxSingle(jbxxid, processDefKey, isFrame, needYz, xztzlx,tslc) {
    if (!needYz) {
        initLc(jbxxid, processDefKey, isFrame, needYz,tslc);
        return;
    }
    var bdcGzYzQO = {};
    var bdcGzYzsjDTO = {};
    var paramList = [];
    bdcGzYzsjDTO.jbxxid = jbxxid;
    if (xztzlx !== undefined) {
        bdcGzYzsjDTO.xztzlx = xztzlx;
    }
    paramList.push(bdcGzYzsjDTO);
    //创建前验证
    bdcGzYzQO.zhbs = processDefKey + "_CJYZ";
    bdcGzYzQO.paramList = paramList;
    if (isFrame) {
        parent.sfcj = true;
    } else {
        sfcj = true;
    }
    console.log("创建流程验证参数：");
    console.log(bdcGzYzQO);
    getReturnData("/bdcGzyz", JSON.stringify(bdcGzYzQO), "POST", function (data) {
        if (data.length === 0) {
            initLc(jbxxid, processDefKey, isFrame, needYz, tslc);
        }else{
            removeModal();
        }
        showXzxx(data, 'cjyz');
    }, function (xhr, status, error) {
        delAjaxErrorMsg(xhr)
    })
}

/**
 * 创建流程最终方法
 */
function initLc(jbxxid, processDefKey, isFrame, needYz, tslc) {
    if (zlcsh == "true") {
        lczlcsh(jbxxid, processDefKey);
    } else {
        //判断是否为一窗创建,对照表存在数据,即为一窗流程
        queryYcslDjywDzb();
        if (!$.isEmptyObject(ycslDjywDzb)) {
            //一窗创建
            ycslCsh(jbxxid, processDefKey);
        } else {
            setTimeout(function () {
                lcCsh(jbxxid, processDefKey, isFrame, needYz, tslc)
            }, 50);
        }
    }
}

/*
* 正常流程创建
* */
function lcCsh(jbxxid, processDefKey, isFrame, needYz, tslc) {
    $.ajax({
        url: getContextPath() + '/slxxcsh',
        type: 'GET',
        dataType: 'json',
        async: false,
        cache: false,
        data: {
            jbxxid: jbxxid,
            gzldyid: processDefKey,
            slbh: slbh,
            tslc: tslc,
            cdlb: cdlb,
            cdqlr: cdqlr,
            cdcqzh: cdcqzh,
            cdzl: cdzl,
            cdqlrZjh: cdqlrZjh,
            xzly: xzly,
            htbh: isNotBlank(htxxObj) && isNotBlank(htxxObj.htbh) ? htxxObj.htbh : '',
        },
        success: function (data) {
            removeModal();
            if (data.msg === "success") {
                if (isFrame) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    var url = "/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId;
                    setTimeout(function () {
                        parent.location.href = url;
                    }, 500);
                    parent.location.href = url + "&timestamp=" + new Date().getTime();
                } else {
                    var url = "/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId;
                    window.location.href = url;
                }
                removeModal();
            } else {
                if (isFrame) {
                    $("#queryBdcdyh").click();
                    parent.addGwc();
                } else {
                    addGwc();
                }
                removeModal();
                ityzl_SHOW_WARN_LAYER("初始化失败");
            }
        }, error: function (xhr, status, error) {
            removeModal();
            if (isFrame) {
                $("#queryBdcdyh").click();
                parent.addGwc();
            } else {
                addGwc();
            }
            delAjaxErrorMsg(xhr);
        }
    });
}

/*
增量初始化方法
* */
function lczlcsh(jbxxid, processDefKey) {
    var gzlslid = getQueryString("gzlslid");
    $.ajax({
        url: getContextPath() + '/slxxcsh/zlcsh',
        type: 'GET',
        dataType: 'json',
        cache: false,
        data: {jbxxid: jbxxid, gzldyid: processDefKey,gzlslid:gzlslid},
        success: function (data) {
            removeModal();
            //关闭当前页面
            closeWin();
            if (zllx === 'single') {
                // 单个流程增加不动产单元号，直接刷新 portal 页面重新加载
                var getUrl = parent.location.href;
                setTimeout(function () {
                    parent.location.href = getUrl;
                }, 500);

                var newUlr = changeURLPar(getUrl,'timestamp',new Date().getTime());
                parent.location.href = newUlr;
            } else {
                parent.loadBdcdyh();
                parent.loadQlr();
            }
        }, error: function (xhr, status, error) {
            removeModal();
            addGwc();
            delAjaxErrorMsg(xhr)
        }
    });

}

// 创建一窗流程
function ycslCsh(jbxxid, processDefKey) {
    getReturnData("/slxxcsh/ycslCsh", {jbxxid: jbxxid, gzldyid: processDefKey, slbh: slbh}, "GET", function (data) {
        removeModal();
        var url = "/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId;
        window.location.href = url;

    }, function (error) {
        addGwc();
        delAjaxErrorMsg(error);

    })


}

/**
 * cxObj 查询条件
 * url 不分页查询
 * isAdd 为true自动添加
 * 全选功能
 */
function bdcdyhSelectAllFunction(cxObj, url, tableid, id, isAdd) {
    setTimeout(function () {
        if (isSelectAll) {
            //已经全选，直接获取缓存数据
            //全选缓存数据
            var allData = JSON.parse(sessionStorage.allData);

        } else {
            isSelectAll = true;
            //第一次全选，获取全部数据缓存
            allData = getAllData(cxObj, url);
        }
        var myCheckedData = {};
        var myAllData = {};
        $.each(allData, function (key, v) {
            v.checked = true;
            //.增加已选中项
            myCheckedData[v[id]] = v;
            //缓存全选数据
            myAllData[v[id]] = v;
        });
        sessionStorage.checkedData = JSON.stringify(myCheckedData);
        sessionStorage.allData = JSON.stringify(myAllData);
        //页面复选框选中
        var checkboxInput = $("[lay-id='" + tableid + "'] input[type=\"checkbox\"]");
        checkboxInput.each(function (index, item) {
            item.checked = true;
        });
        layui.form.render('checkbox');
        //自动添加
        if (isAdd) {
            if (tableid === "xmid") {
                $("#addXmShoppingCar").click();
            } else if (tableid === "qlid") {
                $("#addCfShoppingCar").click();
            }
        }
        removeModal();
    }, 10);


}

/**
 * cxObj 查询条件
 * url 不分页查询
 * 反选功能
 */
function bdcdyhInverseSelectFunction(cxObj, url, tableid, id) {
    setTimeout(function () {
        if (isSelectAll) {
            //已经全选，直接获取缓存数据
            //全选缓存数据
            var allData = JSON.parse(sessionStorage.allData);
        } else {
            isSelectAll = true;
            //第一次全选，获取全部数据缓存
            allData = getAllData(cxObj, url);
        }
        var myCheckedData = {};
        var myAllData = {};
        $.each(allData, function (key, v) {
            v.checked = !v.checked;
            if (v.checked) {
                //.增加已选中项
                myCheckedData[v[id]] = v;
            }
            //缓存全选数据
            myAllData[v[id]] = v;
        });
        sessionStorage.checkedData = JSON.stringify(myCheckedData);
        sessionStorage.allData = JSON.stringify(myAllData);
        //页面复选框选中
        var qxcheckboxInput = $("[lay-id='" + tableid + "'] .layui-table-header input[type=\"checkbox\"]");
        //全选复选框
        qxcheckboxInput.each(function (index, item) {
            if (item.checked) {
                item.checked = !item.checked;
            }
        });
        var checkboxInput = $("[lay-id='" + tableid + "'] .layui-table-body input[type=\"checkbox\"]");
        checkboxInput.each(function (index, item) {
            item.checked = !item.checked;
        });
        layui.form.render('checkbox');
        removeModal();
    }, 10);
}

//删除选中数据
function deleteCheckedById(checkedData, id) {
    var myCheckedData = {};
    $.each(checkedData, function (key, value) {
        if (key != id) {
            myCheckedData[key] = value;
        }
    });
    return myCheckedData;
}

//页面刷新或者关闭时如果没有创建流程清空所有的冗余数据
function clear() {
    //外联证书,关闭不执行清空操作
    if(xztztype ==="wlzs"){
        return false;
    }
    clearGwc(jbxxid,true,1);
    addGwc();
}

//组织自定义查询对象
function initCxtj(cxtj){
    var cxtjObj ={"dyhCxtjList": [],"xmCxtjList": [],"cfCxtjList": [],"ljzCxtjList": [],"wlzsCxtjList": []};
    //配置为空,默认展示所有
    if(isNotBlank(cxtj)){
        try{
            cxtjObj =JSON.parse(cxtj);
        }catch (e) {
            ityzl_SHOW_WARN_LAYER("选择台账自定义查询条件配置异常,请检查配置");
            cxtjObj ={"dyhCxtjList": [],"xmCxtjList": [],"cfCxtjList": [],"ljzCxtjList": [],"wlzsCxtjList": [],"cdCxtjList":[]};
        }
    }
    var keys =["dyhCxtjList","xmCxtjList","cfCxtjList","ljzCxtjList","wlzsCxtjList","cdCxtjList"];
    $.each(keys, function (index, key) {
        if(!cxtjObj[key] ||cxtjObj[key].length ===0) {
            cxtjObj[key] = ["all"];
        }else {
            cxtjObj[key].push("cxfs");
        }
    });
    return cxtjObj;
}

/**
 * 手动插入原项目信息
 */
function insertYxmxx(){
    var insertYxmid = "";
    if(loadTableData){
        for(var i = 0 ;i<loadTableData.length; i++){
            delete loadTableData[i].qlrmc;
            delete loadTableData[i].htbh;
            delete loadTableData[i].zl;
        }
        $.ajax({
            url: getContextPath() + '/bdcdyh/insertYxmxx',
            type: 'POST',
            contentType: "application/json;charset=UTF-8",
            async: false,
            data: JSON.stringify(loadTableData),
            success: function (data) {
                insertYxmid = data;
                console.log("插入原项目信息返回项目id：" + data);
            }, error: function (xhr, status, error) {
                console.log("插入原项目信息异常");
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }
    return insertYxmid;

}

// 创建流程成功后 新增一条修正信息数据
function addXzxx(processInsId){

    var bdcSlXzxxDO = {};

    if(checkData && checkData.length == 1){
        bdcSlXzxxDO.yxmid = checkData[0].xmid;
        bdcSlXzxxDO.bdcqzh = checkData[0].bdcqzh;
        bdcSlXzxxDO.zl = checkData[0].zl;
        bdcSlXzxxDO.qlr = checkData[0].qlrmc;
        bdcSlXzxxDO.blfs = 2;
    }else{
        bdcSlXzxxDO.bdcqzh = $('input[name="bdcqzh"]').val();
        bdcSlXzxxDO.zl = $('input[name="zl"]').val();
        bdcSlXzxxDO.qlr = $('input[name="qlrmc"]').val();
        bdcSlXzxxDO.blfs = 1;
    }
    bdcSlXzxxDO.gzlslid = processInsId;
    console.log("开始插入修正信息：");
    console.log(bdcSlXzxxDO);
    $.ajax({
        url: getContextPath() + "/xzxx/saveBdcSlxzxx",
        type: 'PATCH',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcSlXzxxDO),
        success: function (data) {
            console.log("插入修正信息成功！")
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
        }
    });


}

function getBdcdyZtAndSetSession(bdcdyh,qjgldm) {
    var bdcdyZt = '<p class="bdc-table-state bdc-ql-state">';
    $.ajax({
        url: getContextPath() + "/bdcdyh/queryBdcdyZt?bdcdyh=" + bdcdyh +"&qjgldm="+qjgldm,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            var showCfDjlsBtn = false;
            if (data !== null) {
                if (data.yg) {
                    bdcdyZt += '<span class="bdc-yg">已预告</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.yg) {
                    bdcdyZt += '<span class="bdc-yg">宗地预告</span>';
                }
                if (data.ycf) {
                    showCfDjlsBtn = true;
                    bdcdyZt += '<span class="bdc-ycf">预查封</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.ycf) {
                    showCfDjlsBtn = true;
                    bdcdyZt += '<span class="bdc-ycf">宗地预查封</span>';
                }
                if (data.ydya) {
                    bdcdyZt += '<span class="bdc-ydy">预抵押</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.ydya) {
                    bdcdyZt += '<span class="bdc-ydy">宗地预抵押</span>';
                }
                if (data.cf) {
                    showCfDjlsBtn = true;
                    bdcdyZt += '<span class="bdc-cf">查封</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.cf) {
                    showCfDjlsBtn = true;
                    bdcdyZt += '<span class="bdc-cf">宗地查封</span>';
                }
                if (data.dya) {
                    bdcdyZt += '<span class="bdc-dya">抵押</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.dya) {
                    bdcdyZt += '<span class="bdc-dya">宗地抵押</span>';
                }
                if (data.yy) {
                    bdcdyZt += '<span class="bdc-yy">异议</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.yy) {
                    bdcdyZt += '<span class="bdc-yy">宗地异议</span>';
                }
                if (data.sd) {
                    bdcdyZt += '<span class="bdc-sd">锁定</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.sd) {
                    bdcdyZt += '<span class="bdc-sd">宗地锁定</span>';
                }
                if (data.dyi) {
                    bdcdyZt += '<span class="bdc-dy">地役</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.dyi) {
                    bdcdyZt += '<span class="bdc-dy">宗地地役</span>';
                }
                if (data.zjgcdy) {
                    bdcdyZt += '<span class="bdc-zjgcdy">在建工程抵押</span>';
                }
                if (data.jzq) {
                    bdcdyZt += '<span class="bdc-jzq">居住</span>';
                }
                if (data.fwcq) {
                    bdcdyZt += '<span class="bdc-fwcq">房屋拆迁</span>';
                }
                if (data.ks) {
                    bdcdyZt += '<span class="bdc-ks">可售</span>';
                }
                if (data.ys) {
                    bdcdyZt += '<span class="bdc-ys">已售</span>';
                }
                if (data.xjspfks) {
                    bdcdyZt += '<span class="bdc-xjspfks">新建商品房可售</span>';
                }
                if (data.xjspfys) {
                    bdcdyZt += '<span class="bdc-xjspfys">新建商品房已售</span>';
                }
                if (data.clfks) {
                    bdcdyZt += '<span class="bdc-clfks">存量房可售</span>';
                }
                if (data.clfys) {
                    bdcdyZt += '<span class="bdc-clfys">存量房已售</span>';
                }
                if (bdcdyZt.indexOf("span") < 0) {
                    bdcdyZt += '<span class="bdc-normal">正常</span>';
                }

                bdcdyZt += '</p>';
            }
            sessionStorage.setItem('bdcZt', showCfDjlsBtn);

        }, error: function (xhr, status, error) {
        }
    });
    return bdcdyZt
}

/*
* 获取产权证状态
* */
function getCqzZtAndSetSession(bdcdyh, qllx,zsxmid,qjgldm) {
    var bdcdyZt = '<p class="bdc-table-state bdc-ql-state">';
    $.ajax({
        url: getContextPath() + "/bdcdyh/queryBdcqzZt?bdcdyh=" + bdcdyh + "&qllx=" + qllx+"&zsxmid="+zsxmid +"&qjgldm="+qjgldm,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            var showCfDjlsBtn = false;

            if (data !== null) {
                if (data.yg) {
                    bdcdyZt += '<span class="bdc-yg">已预告</span>';
                }
                if (data.ycf) {
                    showCfDjlsBtn = true;
                    bdcdyZt += '<span class="bdc-ycf">预查封</span>';
                }
                if (data.ydya) {
                    bdcdyZt += '<span class="bdc-ydy">预抵押</span>';
                }
                if (data.cf) {
                    showCfDjlsBtn = true;
                    bdcdyZt += '<span class="bdc-cf">查封</span>';
                }
                if (data.dya) {
                    bdcdyZt += '<span class="bdc-dya">抵押</span>';
                }
                if (data.yy) {
                    bdcdyZt += '<span class="bdc-yy">异议</span>';
                }
                if (data.sd) {
                    bdcdyZt += '<span class="bdc-sd">锁定</span>';
                }
                if (data.dyi) {
                    bdcdyZt += '<span class="bdc-dy">地役</span>';
                }
                if (data.zjgcdy) {
                    bdcdyZt += '<span class="bdc-zjgcdy">在建工程抵押</span>';
                }
                if (data.jzq) {
                    bdcdyZt += '<span class="bdc-jzq">居住</span>';
                }
                if (data.fwcq) {
                    bdcdyZt += '<span class="bdc-fwcq">房屋拆迁</span>';
                }
                if (data.ks) {
                    bdcdyZt += '<span class="bdc-ks">可售</span>';
                }
                if (data.ys) {
                    bdcdyZt += '<span class="bdc-ys">已售</span>';
                }
                if (data.xjspfks) {
                    bdcdyZt += '<span class="bdc-xjspfks">新建商品房可售</span>';
                }
                if (data.xjspfys) {
                    bdcdyZt += '<span class="bdc-xjspfys">新建商品房已售</span>';
                }
                if (data.clfks) {
                    bdcdyZt += '<span class="bdc-clfks">存量房可售</span>';
                }
                if (data.clfys) {
                    bdcdyZt += '<span class="bdc-clfys" >存量房已售</span>';
                }
                if (!data.yg && !data.ycf && !data.ydya && !data.cf && !data.dya && !data.yy
                    && !data.sd && !data.dyi && !data.zjgcdy && !data.ks && !data.ys && !data.xjspfks && !data.xjspfys && !data.clfks && !data.clfys && !data.jzq) {
                    bdcdyZt += '<span class="bdc-normal">正常</span>';
                }
                bdcdyZt += '</p>';

            } else {
                bdcdyZt += '<span class="bdc-normal">正常</span>';
            }
            sessionStorage.setItem('bdcZt', showCfDjlsBtn);

        }, error: function (xhr, status, error) {
        }
    });
    return bdcdyZt
}

