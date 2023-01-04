var bdcslxztzpz = {};
bdcslxztzpz.xztzlxList = ["1","4"];
//不动产类型字典项
var zdBdclxList = {};
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
//判断是否例外初始化
var lwcsh = getQueryString("lwcsh");
//隶属宗地
var lszd = getQueryString("lszd");
//判断是否例外初始化
var qllx = getQueryString("qllx");
//受理编号
var slbh = getQueryString("slbh");
//是否展示幢号
var showZh = false;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    addModel();
    setTimeout(function () {
        try {
            $.when(loadBdclxZdbData(), addGwc(), loadBdcSlXztzPz()).done(function () {
                removeModal();
            })
        } catch (e) {
            ERROR_CONFIRM("加载错误", e.message);
            return;
        }
    }, 10);

    $(function () {
        //单击高级查询
        $('.bdc-form-div').on('click', '#seniorSearch', function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');
            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }
            $('.bdc-percentage-container').css('padding-top', $('.layui-show .bdc-search-content').height() + 10);

            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        });
    });
});

function loadBdcSlXztzPz() {
    $.ajax({
        url: getContextPath() + "/bdcdyh/",
        type: 'GET',
        async: false,
        data: {processDefKey: processDefKey},
        success: function (data) {
            if (isNotBlank(data)) {
                var xztzlxList = [];
                xztzlxList.push("1");
                xztzlxList.push("4");
                data.bdclx = "2";
                if (isNotBlank(data.bdclx)) {
                    bdclxList = data.bdclx.split(",");
                    bdclxList.forEach(function (v) {
                        if (v == '2' || v == '4' || v == '10') {
                            showZh = true;
                        }
                    });
                }
                if (isNotBlank(data.dzwtzm)) {
                    if (data.dzwtzm.indexOf('F') != -1) {
                        showZh = true;
                    }
                }
                if (isNotBlank(data.bdcdyfwlx)) {
                    bdcdyfwlxList = data.bdcdyfwlx.split(",");
                }
                data.xztzlxList = xztzlxList;
                bdcslxztzpz = data;
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
                var json = {
                    isFirst: isFirst,
                    qjgldmList:bdcslxztzpz.qjgldmList
                };
                if (xztzlx === "1") {
                    tpl = dyhTpl.innerHTML;
                }
                if (xztzlx === "4") {
                    tpl = ljzTpl.innerHTML;
                }
                //渲染数据
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = view.innerHTML + html;
                });
            }
            renderDate(form);
            //不动产单元
            initBdcdyhTable();
            //逻辑幢
            initLjzTable();
            //监听第一次单击tab，重构表格尺寸
            var bdcdyhIndex = 0,
                ljzIndex = 0;

            element.on('tab(tabFilter)', function (data) {
                $('.content-title').width($('.layui-tab').width() + 30);
                if (bdcdyhIndex === 0) {
                    bdcdyhIndex++;
                    table.resize('bdcdyhList');
                    table.resize('qjid');
                }
                if (ljzIndex === 0) {
                    ljzIndex++;
                    table.resize('ljzList');
                    table.resize('fwDcbIndex');
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

        //查询参数对象
        var cxObj = {};
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
                } else if (tab === 4) {
                    $("#queryLjz").click();
                }
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
                field: 'bdcdyh', title: '不动产单元号', minWidth: 300, templet: function (obj) {
                    if (isNotBlank(obj.bdcdyh)) {
                        var checkdata = JSON.stringify(obj).replace(/"/g, '&quot;');
                        return '<span style="color:#1d87d1; cursor:pointer" title="' + obj.bdcdyh + '" onclick="getSelectBdcdyData(' + "'" + checkdata + "'" + ",true" + ')">' + queryBdcdyh(obj.bdcdyh) + '</span>'
                    } else {
                        return '<span></span>';
                    }
                }
            },
            {field: 'zl', title: '坐落', minWidth: 250},
            {field: 'qlr', title: '权利人', minWidth: 124},
            {field: 'bdclx', title: '不动产类型', minWidth: 70, templet: '#bdclx'},
            {field: 'fjh', title: '房间号', minWidth: 70, hide: true},
            {field: 'zt', title: '状态', minWidth: 130, templet: '#bdcdyzt', align: "center"},
            {field: 'cz', title: '操作', minWidth: 120, templet: '#dyhcz', align: "center", fixed: "right"}
        ];

        //提交表单
        form.on("submit(queryBdcdyh)", function (data) {
            addModel();
            //查询时清除缓存数据
            layui.sessionData("checkedData", null);
            layui.sessionData("allData", null);
            isSelectAll = false;
            var url = getContextPath() + '/bdcdyh/listBdcdyhByPageJson?loadpage=true';
            cxObj = data.field;
            cxObj.zl = deleteWhitespace(cxObj.zl, "edge");
            if (isNotBlank(cxObj.zl)) {
                cxObj.zl = cxObj.zl.replace(/,/g, '%').replace(/ /g, "%").replace(/，/g, "%");
            }
            cxObj.qllx = bdcslxztzpz.qllx;
            cxObj.zdtzm = bdcslxztzpz.zdtzm;
            cxObj.dzwtzm = bdcslxztzpz.dzwtzm;
            if (!isNotBlank(cxObj.bdcdyfwlx)) {
                cxObj.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
            }
            if (isNotBlank(lszd)) {
                // 增加对地下地籍号的查询
                if (lszd.indexOf("GB") > 0){
                    var lszdArr = [];
                    lszdArr.push(lszd);
                    lszdArr.push(lszd.replace("GB", "GX"));
                    cxObj.djhList = lszd + ',' + lszd.replace("GB", "GX");
                } else {
                    cxObj.djh = lszd;
                }
            }
            cxObj.dyhcxlx = bdcslxztzpz.dyhcxlx;
            tableReload('qjid', cxObj, url);
            return false;
        });

        //监听不动产单元房屋类型下拉框
        form.on('select(bdcdyfwlx)', function (data) {
            if (isNotBlank(data.value) && data.value.indexOf("hs") < 0) {
                $("#fjh").val("");
                $("#fjh").parents(".layui-inline").hide();
            } else {
                $("#fjh").parents(".layui-inline").show();

            }
        });
        //监听不动产类型下拉框
        form.on('select(selectedBdclx)', function (data) {
            if (isNotBlank(data.value) && data.value !== "2") {
                $("#fjh").val("");
                $("#fjh").parents(".layui-inline").hide();
            } else {
                $("#fjh").parents(".layui-inline").show();
            }
        });


        var tableConfig = {
            id: 'qjid',
            toolbar: "#toolbarBdcdyh",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            parseData: function (res) {
                currentPageData = res.content;
                totalPages = res.totalPages;
                // 设置选中行
                var checkedData = layui.sessionData('checkedData');
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
            $.each(checkedData, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: v.qjid, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: v.qjid, remove: true
                    });
                }
                if (isSelectAll) {
                    v.checked = obj.checked;
                    layui.sessionData('allData', {
                        key: v.qjid, value: v
                    });
                }
            });
            removeModal();

        });

        //加载表格
        loadDataTablbeByUrl('#bdcdyhList', tableConfig);
        //表格初始化
        table.init('bdcdyhList', tableConfig);

        //头工具栏事件
        table.on('toolbar(bdcdyhList)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === "addBdcdyhShoppingCar") { //添加到购物车
                var checkedData = layui.sessionData('checkedData');
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
            if (obj.event === 'addBdcdyh') {
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
        //渲染bdcdyfwlx下拉框
        if (bdcdyfwlxList.length !== 1) {
            $('#bdcdyfwlx').append(new Option("请选择", ""));
        }
        $.each(zdbdcdyfwlxList, function (index, item) {
            if (bdcdyfwlxList.length === 0 || bdcdyfwlxList.indexOf(item.DM + "") > -1) {
                $('#bdcdyfwlx').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
            }
        });
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

function checkBdcdyh(checkData, sfcj) {
    var selectDataList = [];
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
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
            delAjaxErrorMsg(xhr, "规则验证失败");
        }
    });


}

//添加不动产单元
function addBdcdyhShoppingCar(checkData, sfcj) {
    var selectDataList = [];
    bdclx = $('#selectedBdclx').val();
    if (bdclx === "请选择") {
        bdclx = '';
    }
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcSlYwxxDTO = {};
        bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
        bdcSlYwxxDTO.zl = selectData.zl;
        selectDataList.push(bdcSlYwxxDTO);
    }
    var bdcCshSlxmDTO = {};
    bdcCshSlxmDTO.bdcSlYwxxDTOList = selectDataList;
    bdcCshSlxmDTO.gzldyid = processDefKey;
    bdcCshSlxmDTO.jbxxid = jbxxid;
    if (getQueryString("gzlslid")) {
        bdcCshSlxmDTO.gzlslid = getQueryString("gzlslid");
    }
    $.ajax({
        url: getContextPath() + "/addbdcdyh/addGzlw",
        type: 'POST',
        data: {
            data: JSON.stringify(bdcCshSlxmDTO),
            qllx: qllx
        },
        success: function (data) {
            var index = parent.layer.getFrameIndex(window.name);
            if (index) {
                parent.ityzl_SHOW_SUCCESS_LAYER("选择成功");
                parent.layer.close(index);
            }
            removeModal();
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr, "添加不动产单元失败");
        }
    });
}

function loadBdclxZdbData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data !== null && data.bdclx !== null && data.cflx !== null) {
                zdBdclxList = data.bdclx;
                zdCflxList = data.cflx;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr, "加载字典项失败");
        }
    });
}

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

function showXzxx(yzResult) {
    if (yzResult.length > 0) {
        loadTsxx(yzResult);
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
    }

}


getSelectBdcdyData = function (elem, cj) {
    addModel();
    checkData = [];
    checkData.push(JSON.parse(elem));
    sfcj = cj;
    checkBdcdyh(checkData, sfcj);
};

/**
 * cxObj 查询条件
 * url 不分页查询
 * 全选功能
 */
function bdcdyhSelectAllFunction(cxObj, url, tableid, id) {
    setTimeout(function () {
        if (isSelectAll) {
            //已经全选，直接获取缓存数据
            //全选缓存数据
            var allData = layui.sessionData('allData');

        } else {
            isSelectAll = true;
            //第一次全选，获取全部数据缓存
            allData = getAllData(cxObj, url);
        }
        layui.sessionData('checkedData', null);
        $.each(allData, function (i, v) {
            v.checked = true;
            //.增加已选中项
            layui.sessionData('checkedData', {
                key: v[id], value: v
            });
            //缓存全选数据
            layui.sessionData('allData', {
                key: v[id], value: v
            });
        });
        //页面复选框选中
        var checkboxInput = $("[lay-id='" + tableid + "'] input[type=\"checkbox\"]");
        checkboxInput.each(function (index, item) {
            item.checked = true;
        });
        layui.form.render('checkbox');
        removeModal();
    }, 10);


}

function addGwc(isFirst) {
    getReturnData("/bdcdyh/queryParams", {gzlslid: gzlslid}, "GET", function (data) {
        processDefKey = data.processDefKey;
        jbxxid = data.jbxxid;

    }, function (error) {
        delAjaxErrorMsg(error);
    }, false);
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
            var allData = layui.sessionData('allData');
        } else {
            isSelectAll = true;
            //第一次全选，获取全部数据缓存
            allData = getAllData(cxObj, url);
        }
        layui.sessionData('checkedData', null);
        $.each(allData, function (i, v) {
            v.checked = !v.checked;
            if (v.checked) {
                //.增加已选中项
                layui.sessionData('checkedData', {
                    key: v[id], value: v
                });

            } else {
                //删除
                layui.sessionData('checkedData', {
                    key: v[id], remove: true
                });
            }
            //缓存全选数据
            layui.sessionData('allData', {
                key: v[id], value: v
            });

        });
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






