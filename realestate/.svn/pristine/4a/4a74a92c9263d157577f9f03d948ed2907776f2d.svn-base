var bdcslxztzpz;
//不动产类型字典项
var zdBdclxList = {};
//查封类型字典项
var zdCflxList = {};
//规划用途字典项
var zdGhytList = {};
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
//受理编号
var slbh = getQueryString("slbh");
var table;
var form;
// 合同监管信息
var htjgxx;

/**
 * 一窗受理流程登记流程对照关系，当前流程为一窗流程查到的数据
 */
var ycslDjywDzb = {};
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var $ = layui.jquery;
     table = layui.table;
     form = layui.form;
    addModel();
    setTimeout(function () {
        try {
            $.when(loadBdclxZdbData(), loadBdcSlXztzPz()).done(function () {
                //页面加载时清除缓存数据
                layui.sessionData("checkedData", null);
                layui.sessionData("allData", null);
                removeModal();
            })
        } catch (e) {
            ERROR_CONFIRM("加载错误", e.message);
            return
        }
    }, 10);

    $(function () {


        //关闭处理
        window.onunload = function (e) {
            clear();
        };

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


                if (isNotBlank(data.xztzlx)) {
                    xztzlxList = data.xztzlx.split(",");
                }
                if (isNotBlank(data.bdclx)) {
                    bdclxList = data.bdclx.split(",");
                }
                if (isNotBlank(data.bdcdyfwlx)) {
                    bdcdyfwlxList = data.bdcdyfwlx.split(",");
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
            delAjaxErrorMsg(xhr, '加载受理选择台账配置失败');
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
                    cxtj:cxtjObj
                };
                if (xztzlx === "1") {
                    tpl = dyhTpl.innerHTML;
                }
                if (xztzlx === "2") {
                    tpl = xmTpl.innerHTML;
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
                    tpl = htjgxxTpl.innerHTML;
                }
                if (xztzlx === "7") {
                    tpl = bahtTpl.innerHTML;
                }
                if (xztzlx === "11") {
                    tpl = plyzxTpl.innerHTML;
                }
                //渲染数据
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = view.innerHTML + html;
                });
                form.render();
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
            //合同监管信息
            initHtjgxxTable();
            //备案合同信息
            initBahtTable();
            // 预告信息
            initYgTable();
            //监听第一次单击tab，重构表格尺寸
            var xmIndex = 0,
                cfIndex = 0,
                ljzIndex = 0,
                wlzsIndex = 0,
                bdcdyhIndex = 0,
                htjgxxIndex = 0,
                xm_hthIndex = 0,
                ygIndex = 0;

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
                    case 1:
                        if (bdcdyhIndex === 0) {
                            bdcdyhIndex++;
                            table.resize('bdcdyhList');
                            table.resize('qjid');
                        }
                        break;
                    case 6:
                        if (htjgxxIndex === 0) {
                            htjgxxIndex++;
                            table.resize('htjgxxList');
                            table.resize('htjgxx');
                        }
                    case 7:
                        if (xmIndex === 0) {
                            xm_hthIndex++;
                            table.resize('xmList_hth');
                            table.resize('xmid_hth');
                        }
                        break;
                    case 11:
                        if (ygIndex === 0) {
                            ygIndex++;
                            table.resize('ygList');
                            table.resize('xmid_yg');
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

        //查询参数对象
        var cxObj = {};
        // 当前页数据
        var currentPageData = new Array();
        //获取查询总页数
        var totalPages = 0;


        var isSearch = true;
        $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });
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
                } else if (tab === 6) {
                    $("#queryHtjgxx").click();
                }else if (tab === 7) {
                    $("#queryBaht").click();
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
        var unitTableTitle = [{type: 'checkbox', fixed: 'left'},
            {
                field: 'bdcdyh', title: '不动产单元号', minWidth: 300,templet: '#bdcdyhTpl'
            },
            {field: 'zl', title: '坐落', minWidth: 250,sort: true},
            {field: 'qlr', title: '权利人', minWidth: 124},
            {field: 'bdclx', title: '不动产类型', minWidth: 70, templet: '#bdclx'},
            {field: 'fjh', title: '房间号', minWidth: 70, hide: true},
            {field: 'zt', title: '限制状态', minWidth: 130, templet: '#bdcdyzt', align: "center"},
            {field: 'blzt', title: '办理状态', hide: 'true', templet: '#blzt', align: "center"},
            {title: '操作', width: 140, templet: '#dyhcz', align: "center", fixed: "right"}
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
            if(isNotBlank(cxObj.bdcdyfwlx) &&(cxObj.bdcdyfwlx ==="xmxx" ||cxObj.bdcdyfwlx ==="ljz" ||cxObj.bdcdyfwlx ==="hs" ||cxObj.bdcdyfwlx ==="ychs")){
                //bdcdyfwlx有值,dzwtzm为F
                cxObj.dzwtzm = "F";
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
            // page: true,  //开启分页
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
            addModel();
            var checkedData = obj.type == 'one' ? [obj.data] : currentPageData;
            if (obj.type == 'one'){
            $.each(checkedData, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: v.qjid, value: v
                    });
                    //新增，选中添加进购物车
                    checkData = [v];
                    sfcj = false;
                    checkBdcdyh(checkData, false);
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: v.qjid, remove: true
                    });
                    var inListNum = 0;
                    //从数据库删除一条购物车数据，并从页面移除被删除的行
                    gwcData.forEach(function(value,index){
                        if(value.bdcdyh == v.bdcdyh){
                            deleteBdcslxm(value.xmid);
                            gwcData.splice(index, 1);
                            generateGwc(gwcData);
                            inListNum++;
                            return;
                        }
                    });
                    if(gwcData.length ===0){
                        removeModal();
                    }
                    if(inListNum == 0){
                        removeModal();
                    }
                }
                if (isSelectAll) {
                    v.checked = obj.checked;
                    layui.sessionData('allData', {
                        key: v.qjid, value: v
                    });
                    removeModal();
                }
            });
            } else {
                $.each(checkedData, function (i, v) {
                    if (obj.checked) {
                        //.增加已选中项
                        layui.sessionData('checkedData', {
                            key: v.qjid, value: v
                        });
                        //新增，选中添加进购物车
                        checkData.push(v);
                        sfcj = false;
                    } else {
                        //.删除
                        layui.sessionData('checkedData', {
                            key: v.qjid, remove: true
                        });
                        var inListNum = 0;
                        //从数据库删除一条购物车数据，并从页面移除被删除的行
                        gwcData.forEach(function(value,index){
                            if(value.bdcdyh == v.bdcdyh){
                                deleteBdcslxm(value.xmid);
                                gwcData.splice(index, 1);
                                generateGwc(gwcData);
                                inListNum++;
                                return;
                            }
                        });
                        if(gwcData.length ===0){
                            removeModal();
                        }
                        if(inListNum == 0){
                            removeModal();
                        }
                    }
                    if (isSelectAll) {
                        v.checked = obj.checked;
                        layui.sessionData('allData', {
                            key: v.qjid, value: v
                        });
                        removeModal();
                    }
                });
                if (!obj.checked){
                    checkData = [];
                }
                checkBdcdyh(checkData, false);
            }
        });

        //监听排序事件
        setColorAfterZl('bdcdyhList');

        //监听单元号点击
        $('.bdc-tab-box').on('click','.bdc-bdcdyh-click',function () {
            getSelectBdcdyData($(this).data('json'),true);
        });

        //加载表格
        loadDataTablbeByUrl('#bdcdyhList', tableConfig);
        //表格初始化
        table.init('bdcdyhList', tableConfig);

        //头工具栏事件
        table.on('toolbar(bdcdyhList)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            var $this = $(this);
            if (layEvent === "cshCreate") { //执行单击购物车表格中的创建
                createCsh($this);
            }  else if (layEvent === "allchoosebdcdyh") { //全选
                var url = getContextPath() + '/bdcdyh/listBdcdyhByPageJson?loadpage=false';
                addModel();
                bdcdyhSelectAllFunction(cxObj, url, 'qjid', 'qjid','bdcdyh');

            } else if (layEvent === "allunchoosebdcdyh") {//反选
                var url = getContextPath() + '/bdcdyh/listBdcdyhByPageJson?loadpage=false';
                addModel();
                bdcdyhInverseSelectFunction(cxObj, url, 'qjid', 'qjid','bdcdyh');
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
        if(zdBdclxList &&zdBdclxList.length >0) {
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
            // $("#fjh").attr('disabled', 'off');
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
            delAjaxErrorMsg(xhr);
        }
    });
}

//获取一窗对照数据
function queryYcslDjywDzb(){
    //判断是否为一窗创建,对照表存在数据,即为一窗流程
    getReturnData("/slxxcsh/queryYcslDjywDzb",{gzldyid:processDefKey},"GET",function (data) {
        ycslDjywDzb =data;
    },function (error) {
        delAjaxErrorMsg(error);
    },false);
}


//添加不动产单元
function addBdcdyhShoppingCar(checkData, sfcj) {
    if (sfcj) {
        //如果购物车存在数据，先清除购物车数据
        var xmcount = $(".bdc-car").find("span")[0].textContent;
        if (xmcount !== "0") {
            clearGwc(jbxxid,false);
        }
    }
    var selectDataList = [];
    var sfzlcsh = (zlcsh === "true") ? 1 : null;
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcSlYwxxDTO = {};
        bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlr;
        bdcSlYwxxDTO.qjid = selectData.qjid;
        bdcSlYwxxDTO.qjqlrgxid = selectData.qjqlrgxid;
        bdcSlYwxxDTO.dyhcxlx = getDyhcxlx(selectData.lx);
        bdcSlYwxxDTO.lx = selectData.lx;
        bdcSlYwxxDTO.sfzlcsh = sfzlcsh;
        bdcSlYwxxDTO.bdcWlSlXmLsgxDOList = selectData.bdcWlSlXmLsgxDOList;
        bdcSlYwxxDTO.yt = selectData.yt;
        bdcSlYwxxDTO.qllx = selectData.qsxz;
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
                cshWithCjyz();
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

/**
 * 创建流程时
 * @param jbxxid
 * @param processDefKey
 * @param isFrame 为true时，说明是购物车的创建，创建后需要往上取父页面
 * @param needYz 当needYz 为true的时候，需要验证，否则不需要验证(此处用不到此参数,主要为了与合肥保持统一,规则验证忽略时可以统一调用！！！)
 * @param xztzlx 选择台账类型，1位单元号，2为产权证，3 为查封
 */
function cshSelectedXxSingle(jbxxid, processDefKey, isFrame, needYz, xztzlx) {
    if (zlcsh == "true") {
        zlcshSelectedXxSingle(jbxxid, processDefKey);
    } else {
        //判断是否为一窗创建,对照表存在数据,即为一窗流程
        queryYcslDjywDzb();
        if(!$.isEmptyObject(ycslDjywDzb)){
            //一窗创建
            ycslCsh(jbxxid, processDefKey);
            return false;
        }
        if(sfxglcqx){
            removeModal();
            var cnqxLayer = layer.open({
                title: '配置',
                type: 1,
                area: ['430px'],
                btn: ['创建', '取消'],
                content: '<div id="bdc-popup-small">'+
                '<form class="layui-form" action="">'+
                '<div class="layui-form-item pf-form-item">'+
                '<div class="layui-inline">'+
                '<label class="layui-form-label">承诺期限</label>'+
                '<div class="layui-input-inline">'+
                '<input type="number" id="setCnqx" autocomplete="off" value="9" class="layui-input">'+
                '</div>'+
                '</div>'+
                '</div>'+
                '</form>'+
                '</div>'
                ,yes: function(index, layero){
                    //提交 的回调
                    var cnqx = $('#setCnqx').val();
                    layer.close(cnqxLayer);
                    addModel();
                    setTimeout(function(){
                        lcCsh(jbxxid,processDefKey,cnqx)
                    }, 50);
                }
            });
        } else {
            lcCsh(jbxxid, processDefKey, "")
        }

    }
}

//批量创建流程，根据选择的数据的量，创建多个流程
/**
 * @param jbxxid，processDefKey
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 初始化之前先重新组织受理项目表（购物车数据），根据单元号区分，分别赋值jbxxid 和受理编号并返回页面用于创建流程
 * @date : 2022/1/6 8:43
 */
function lcPlCsh(jbxxid) {
    getReturnData("/slxxcsh/plcsh", {jbxxid: jbxxid, slbh: slbh}, "GET", function (data) {
        if (data && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                slbh = data[i].slbh;
                lcCsh(data[i].jbxxid, processDefKey, "");
            }
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

function lcCsh(jbxxid, processDefKey, cnqx) {
    $.ajax({
        url: getContextPath() + '/slxxcsh',
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {jbxxid: jbxxid, gzldyid: processDefKey, slbh: slbh, cnqx: cnqx},
        success: function (data) {
            removeModal();
            if (plcjlc) {
                //关闭当前页面
                layer.msg('操作成功，即将关闭页面');
                setTimeout(function () {
                    window.close();
                }, 1000);
            } else {
                if (data.msg === "success") {
                    var url = "/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId;
                    window.location.href = url;
                } else {
                    addGwc();
                    showAlertDialog("初始化失败");
                }
            }
        }, error: function (xhr, status, error) {
            addGwc();
            delAjaxErrorMsg(xhr);
            removeModal();
        }
    });
}

function zlcshSelectedXxSingle(jbxxid, processDefKey) {
    var gzlslid = getQueryString("gzlslid");
    $.ajax({
        url: getContextPath() + '/slxxcsh/zlcsh',
        type: 'GET',
        dataType: 'json',
        data: {jbxxid: jbxxid, gzldyid: processDefKey,gzlslid:gzlslid},
        success: function (data) {

            removeModal();
            //关闭当前页面
            closeWin();
            if (zllx === 'single') {
                // 单个流程增加不动产单元号，直接刷新 portal 页面重新加载
                var getUrl = parent.location.href;
                setTimeout(function(){
                    parent.location.href = getUrl;
                },500);

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
function ycslCsh(jbxxid, processDefKey){
    //一窗受理编号加上前缀YTH
    if(isNotBlank(slbh) &&slbh.indexOf('YTH') <0){
        slbh ="YTH"+slbh;
    }
    getReturnData("/slxxcsh/ycslCsh",{jbxxid: jbxxid, gzldyid: processDefKey, slbh: slbh},"GET",function (data) {
        removeModal();
        var url = "/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId;
        window.location.href = url;

    },function (error) {
        addGwc();
        delAjaxErrorMsg(error);

    })

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
                zdGhytList = data.fwyt;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,"加载字典项失败");
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
                delAjaxErrorMsg(xhr,"获取不动产类型失败");
            }
        });
    }

    return bdclxMc;
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
            delAjaxErrorMsg(xhr,"全选数据失败");

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

function showXzxx(yzResult,yzlx, removeCallback) {
    if (yzResult.length > 0) {
        loadTsxx(yzResult,yzlx, removeCallback);
        if (!(alertSize === 0 && confirmSize === 0)) {
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
        } else{
            removeTsxx('', removeCallback);
        }
    }
}

/**
 * 针对于规则中获取合同监管信息验证通过的，特殊处理直接添加到购物车
 * @param yzlx
 */
function removeTsxx(yzlx, removeCallback) {
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
            if (typeof (removeTsxxCallBack) == "function") {
                eval("removeTsxxCallBack()");
            } else {
                removeModal();
            }

        }
    } else {
        removeModal();
    }
}

getSelectBdcdyData = function (elem, cj) {
    addModel();
    checkData = [];
    checkData.push(elem);
    sfcj = cj;
    checkBdcdyh(checkData, sfcj);
};

/**
 * cxObj 查询条件
 * url 不分页查询
 * 全选功能
 */
function bdcdyhSelectAllFunction(cxObj, url, tableid, id,selectName) {
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
        //添加进购物车
        addToGwc(selectName);
        //页面复选框选中
        var checkboxInput = $("[lay-id='" + tableid + "'] input[type=\"checkbox\"]");
        checkboxInput.each(function (index, item) {
            item.checked = true;
        });
        layui.form.render('checkbox');
        removeModal();
    }, 10);


}

/**
 * cxObj 查询条件
 * url 不分页查询
 * 反选功能
 */
function bdcdyhInverseSelectFunction(cxObj, url, tableid, id,selectName) {
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
        //添加进购物车
        addToGwc(selectName);
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

//单击全选、反选，组织数据后，执行addToGwc（）添加数据进购物车
function addToGwc(selectName){
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
        switch (selectName) {
            case 'bdcdyh':
                checkBdcdyh(checkData, false);
                break;
            case 'xm':
                //南通先进行项目验证再进行虚拟号验证
                checkXm(checkData, sfcj);
                break;
            case 'cf':
                //查封先验证项目再进行虚拟号验证
                checkCf(checkData,false);
                break;
        }

    } else {
        layer.alert("未选择数据!");
    }
}

//坐落排序后，表格颜色问题
function setColorAfterZl(tableId){
    table.on('sort('+ tableId +')', function(obj){
        //根据权利状态、办理状态整行显色
        var colorList = [];
        $.ajax({
            url: '/realestate-accept-ui/bdcdyh/status/color',
            type: "GET",
            async: false,
            success: function(data) {
                for(var i in data) {
                    colorList.push({name:i,color:'#333',background:data[i]});
                }
                changeTrBackgroundExceptRight(colorList, true);
            }
        });
    });
}

//页面刷新或者关闭时如果没有创建流程清空所有的冗余数据
function clear() {
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
            cxtjObj ={"dyhCxtjList": [],"xmCxtjList": [],"cfCxtjList": [],"ljzCxtjList": [],"wlzsCxtjList": []};
        }
    }
    var keys =["dyhCxtjList","xmCxtjList","cfCxtjList","ljzCxtjList","wlzsCxtjList"];
    $.each(keys, function (index, key) {
        if(!cxtjObj[key] ||cxtjObj[key].length ===0) {
            cxtjObj[key] = ["all"];
        }
    });
    return cxtjObj;
}


function removeTsxxCallBack(){
    if (sfcj) {
        //如果购物车存在数据，先清除购物车数据
        var xmcount = $(".bdc-car").find("span")[0].textContent;
        if (xmcount !== "0") {
            clearGwc(jbxxid,false);
        }
    }
    var selectDataList = [];
    var sfzlcsh = (zlcsh === "true") ? 1 : null;
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcSlYwxxDTO = {};
        bdcSlYwxxDTO.xmid = selectData.xmid;
        bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
        bdcSlYwxxDTO.ybdcqz = selectData.bdcqzh;
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlrmc;
        bdcSlYwxxDTO.bdcWlSlXmLsgxDOList = selectData.bdcWlSlXmLsgxDOList;
        bdcSlYwxxDTO.dyhcxlx = bdcslxztzpz.dyhcxlx;
        bdcSlYwxxDTO.qllx = parseInt(selectData.qllx);
        bdcSlYwxxDTO.sfzlcsh = sfzlcsh;
        bdcSlYwxxDTO.bdclx = selectData.bdclx;
        bdcSlYwxxDTO.fcjyBaxxDTO = selectData.fcjyBaxxDTO;
        bdcSlYwxxDTO.yt = selectData.dzwyt;
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
                addGwc();
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
