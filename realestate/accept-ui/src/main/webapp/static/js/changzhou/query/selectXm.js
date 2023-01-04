function initXmTable() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;
        // 当前页数据
        var currentPageData = [];
        //获取查询总页数
        var totalPages = 0;

        //不动产单元号的表头
        var unitTableTitle = [{type: 'checkbox', fixed: 'left'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 260, templet: '#xmbdcdyhTpl'},
            {field: 'zt', title: '状态', minWidth: 100, templet: '#cqzzt', align: "center"},
            {field: 'zszmlx', title: '证明类型', minWidth: 100,
                templet: function (d) {
                    if (d.zszmlx ==="dzzz") {
                        return '电子证';
                    } else if(d.zszmlx ==="zzzm") {
                        return '纸质证';
                    }else{
                        return '';
                    }
                }},
            {field: 'bdcqzh', title: '不动产权证（明）号', minWidth: 280},
            {field: 'qlrmc', title: '权利人', minWidth: 150},
            {field: 'qlrzjh', title: '权利人证件号', minWidth: 160},
            {field: 'zl', title: '坐落', minWidth: 280},
            {field: 'zh', title: '幢号', width: 100},
            {field: 'fjh', title: '房间号', width: 100},
            {field: 'dzwmj', title: '建筑面积', width: 120},
            {field: 'qllxMc', title: '权利类型名称', hide: 'true', minWidth: 150},
            {field: 'sdsqwh', title: '锁定申请文号', minWidth: 120},
            {field: 'tdzh', title: '土地证号', minWidth: 200},
            {field: 'tdzzdmj', title: '宗地面积', width: 120},
            {field: 'tdzfttdmj', title: '分摊土地面积', width: 120},
            {field: 'tdzdytdmj', title: '独用土地面积', width: 120},
            {field: 'tdzsyqx', title: '土地使用期限', minWidth: 120},
            {field: 'tdztdsyqssj', title: '土地使用起始时间', minWidth: 150,
                templet: function (d) {
                    if (d.tdztdsyqssj) {
                        return Format(d.tdztdsyqssj, "yyyy年MM月dd日");
                    } else {
                        return '';
                    }
                }},
            {field: 'tdztdsyjssj', title: '土地使用结束时间', minWidth: 150,
                templet: function (d) {
                    if (d.tdztdsyjssj) {
                        return Format(d.tdztdsyjssj, "yyyy年MM月dd日");
                    } else {
                        return '';
                    }
                }},
            {field: 'tdztdsyqssj2', title: '土地使用起始时间2', minWidth: 150,
                templet: function (d) {
                    if (d.tdztdsyqssj2) {
                        return Format(d.tdztdsyqssj2, "yyyy年MM月dd日");
                    } else {
                        return '';
                    }
                }},
            {field: 'tdztdsyjssj2', title: '土地使用结束时间2', minWidth: 150,
                templet: function (d) {
                    if (d.tdztdsyjssj2) {
                        return Format(d.tdztdsyjssj2, "yyyy年MM月dd日");
                    } else {
                        return '';
                    }
                }},
            {field: 'tdztdsyqssj3', title: '土地使用起始时间3', minWidth: 160,
                templet: function (d) {
                    if (d.tdztdsyqssj3) {
                        return Format(d.tdztdsyqssj3, "yyyy年MM月dd日");
                    } else {
                        return '';
                    }
                }},
            {field: 'tdztdsyjssj3', title: '土地使用结束时间3', minWidth: 160,
                templet: function (d) {
                    if (d.tdztdsyjssj3) {
                        return Format(d.tdztdsyjssj3, "yyyy年MM月dd日");
                    } else {
                        return '';
                    }
                }},
            {
                field: 'pplzzt', title: '匹配落宗状态', width: 150, align: "center", templet: function (obj) {
                    if (isNotBlank(obj.bdcdyh)) {
                        var data = [];
                        data.push(obj);
                        return getPplzzt(obj.xmly, data, 2);
                    }
                }
            },
            {field: 'sdzt', title: '锁定类型，锁定原因', minWidth: 200, align: "center"},
            {
                field: 'cz', title: '操作', width: 280, templet: function (obj) {
                    if (isNotBlank(obj.bdcdyh)) {
                        return bdcdyczTpl(obj.bdcdyh, obj.xmid);
                    }
                }, align: "center", fixed: 'right'
            }
        ];

        if(queryType == "1"){
            unitTableTitle = [{type: 'checkbox', fixed: 'left'},
                {
                    field: 'bdcdyh', title: '不动产单元号', templet: '#xmbdcdyhTpl'
                },
                {field: 'cqzh', title: '不动产权证（明）号'},
                {field: 'zl', title: '坐落'},
                {field: 'qlrmc', title: '权利人'},
                {
                    field: 'cz', title: '操作', width: 180, templet: function (obj) {
                        if (isNotBlank(obj.bdcdyh)) {
                            return bdcdyczTpl(obj.bdcdyh, obj.xmid);
                        }
                    }, align: "center", fixed: 'right'
                }
            ];
        }


        //提交表单
        form.on("submit(queryXm)", function (data) {
            addModel();
            //查询时清除缓存数据
            sessionStorage.checkedData = '{}';
            sessionStorage.allData = '{}';
            isSelectAll = false;
            var url = getContextPath() + '/bdcdyh/listXmByPageJson?loadpage=true';
            if(queryType == "1"){
                url = getContextPath() + '/bdcdyh/listYxmByPageJson?loadpage=true';
            }
            cxObj = data.field;
            if(queryType == "1"){
                if($("input[name='bdcqzh']").val() == ""){
                    layer.alert("请输入产权证号!");
                    removeModal();
                    return
                }
            }

            if (isNotBlank(cxObj.bdcdyh)) {
                if (cxObj.bdcdyh.indexOf(",") > -1) {
                    cxObj.bdcdyhList = cxObj.bdcdyh;
                    cxObj.bdcdyh = "";
                }
            }
            //添加模糊类型
            // cxtjAddMhlx(cxObj);
            cxObj.glfdcq=true;
            cxObj.gltdz=true;
            cxObj.sfahdj=1;
            cxObj.qllx = bdcslxztzpz.qllx;
            cxObj.zdtzm = bdcslxztzpz.zdtzm;
            cxObj.dzwtzm = bdcslxztzpz.dzwtzm;
            cxObj.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
            cxObj.dyhcxlx = bdcslxztzpz.dyhcxlx;
            cxObj.bdclx = bdcslxztzpz.bdclx;
            cxObj.gzldyid = processDefKey;
            cxObj.zslx = bdcslxztzpz.zslx;
            cxObj.ygdjzlList = bdcslxztzpz.ygdjzl;
            cxObj.xmly = bdcslxztzpz.xmly;
            if(bdcslxztzpz.qxList &&bdcslxztzpz.qxList.length >0) {
                cxObj.qxdmList = bdcslxztzpz.qxList.join(",");
            }
            //加载锁定原因
            cxObj["sfsdzt"] = "1";
            tableReload('xmid', cxObj, url);
            return false;
        });

        var tableConfig = {
            id: 'xmid',
            toolbar: "#toolbarXm",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            parseData: function (res) {
                loadTableData = res.content;
                currentPageData = res.content;
                totalPages = res.totalPages;
                // 设置选中行
                var checkedData = JSON.parse(sessionStorage.checkedData);
                if (res.content && res.content.length > 0 && !$.isEmptyObject(checkedData)) {
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.bdcdyh) {
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }
            },
            done: function (res, curr, count) {
                var reverseList = ['zl'];
                reverseTableCell(reverseList, 'xmid');
                if ($('.layui-show .layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        };

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(xmList)', function (obj) {
            var checkedData = obj.type == 'one' ? [obj.data] : currentPageData;
            var myCheckedData = JSON.parse(sessionStorage.checkedData);
            var myAllData = JSON.parse(sessionStorage.allData);
            $.each(checkedData, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    myCheckedData[v.bdcdyh] = v;
                } else {
                    //.删除
                    myCheckedData = deleteCheckedById(myCheckedData, v.bdcdyh);
                }
                if (isSelectAll) {
                    v.checked = obj.checked;
                    myAllData[v.bdcdyh] = v;
                }
            });
            sessionStorage.checkedData = JSON.stringify(myCheckedData);
            sessionStorage.allData = JSON.stringify(myAllData);
            removeModal();

        });

        //监听单元号点击
        $('.bdc-tab-box').on('click', '.bdc-xmbdcdyh-click', function () {
            getSelectXmData($(this).data('json'), true);
        });

        //加载表格
        loadDataTablbeByUrl('#xmList', tableConfig);
        //表格初始化
        table.init('xmList', tableConfig);
        //头工具栏事件
        table.on('toolbar(xmList)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === "addXmShoppingCar") { //添加到购物车
                var checkedData = JSON.parse(sessionStorage.checkedData);
                if ($.isEmptyObject(checkedData)) {
                    layer.alert("未选择数据!");
                }
                checkData = [];
                $.each(checkedData, function (key, value) {
                    checkData.push(value);
                });
                if (checkData !== null && checkData.length > 0) {
                    addModel();
                    sfcj = false;
                    checkXm(checkData, sfcj);
                } else {
                    layer.alert("未选择数据!");
                }
            } else if (layEvent === "allchooseXm") { //全选
                var url = getContextPath() + '/bdcdyh/listXmByPageJson?loadpage=false';
                addModel();
                // 判断必填条件
                var count = 0;
                $(".required").each(function (i) {
                    if (isNotBlank($(this).val())) {
                        count += 1;
                    }
                });
                if (0 === count) {
                    removeModal();
                    showAlertDialog(" 请先输入必要查询条件再全选");
                    return false;
                }
                bdcdyhSelectAllFunction(cxObj, url, 'xmid', 'xmid');

            } else if (layEvent === "allunchooseXm") {//反选
                var url = getContextPath() + '/bdcdyh/listXmByPageJson?loadpage=false';
                addModel();
                // 判断必填条件
                var count = 0;
                $(".required").each(function (i) {
                    if (isNotBlank($(this).val())) {
                        count += 1;
                    }
                });
                if (0 === count) {
                    removeModal();
                    showAlertDialog(" 请先输入必要查询条件再反选");
                    return false;
                }
                bdcdyhInverseSelectFunction(cxObj, url, 'xmid', 'bdcdyh');
            } else if (layEvent === "createNoXm") {
                //无数据创建
                addModel();
                cdcqzh = cxObj.bdcqzh;
                cdqlr = cxObj.qlrmc;
                cdzl = cxObj.zl;
                cdqlrZjh = cxObj.qlrzjh;
                //查档查询如果未选择数据，也可直接创建
                setTimeout(function () {
                    lcCsh(jbxxid, processDefKey, false, false, true);
                    $this.removeAttr("disabled").css("pointer-events", "auto");
                }, 50)
            }
        });
        table.on('tool(xmList)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'cshBdcXm' || obj.event === 'addBdcdyh') {
                checkData = [];
                checkData.push(obj.data);
                if (checkData !== null) {
                    addModel();
                    setTimeout(function () {
                        try {
                            if (obj.event === 'cshBdcXm') {
                                sfcj = true;
                                $.when(checkXm(checkData, sfcj)).done(function () {
                                })
                            } else {
                                sfcj = false;
                                $.when(checkXm(checkData, sfcj)).done(function () {
                                })
                            }
                        } catch (e) {
                            removeModal();
                            ERROR_CONFIRM("加载失败", e.message);
                            return
                        }
                    }, 1);
                }
            } else if (obj.event === 'lpb') {
                var data = obj.data;//可以获取当前行的数据
                //获取逻辑幢主键
                getReturnData("/rest/v1.0/slym/fwbdcdy", {bdcdyh: data.bdcdyh}, "GET", function (data) {
                    if (data && isNotBlank(data.fwDcbIndex)) {
                        var index = layer.open({
                            type: 2,
                            title: "选择楼盘表",
                            area: ['1300px', '600px'],
                            fixed: false, //不固定
                            maxmin: true, //开启最大化最小化按钮
                            content: "/building-ui/lpb/view?code=getbdcdyh&fwDcbIndex=" + data.fwDcbIndex
                        });
                        layer.full(index);
                    } else {
                        showAlertDialog("未找到楼盘表");
                    }

                }, function (error) {
                    delAjaxErrorMsg(error)

                })

            }
        });
        //继承公告业务可以无数据创建项目
        if (jcywlz) {
            $("#createNoXm")[0].classList.remove('bdc-hide');
        }
    })
}

function checkXm(checkdData, sfcj) {
    var selectDataList = [];
    for (var i = 0; i < checkdData.length; i++) {
        var selectData = checkdData[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.yxmid = selectData.xmid;
        bdcGzYzsjDTO.ysfwbm = selectData.ysfwbm;
        bdcGzYzsjDTO.qjgldm = selectData.qjgldm;
        var obj = {};
        obj.realNo = selectData.bdcqzh;
        obj.cardNo = selectData.qlrzjh;
        obj.xmid = selectData.xmid;
        obj.slbh = selectData.slbh;
        obj.fwbm = selectData.ysfwbm;
        obj.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.obj = obj;
        selectDataList.push(bdcGzYzsjDTO);
    }
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = processDefKey + "_XZBDCDY";
    bdcGzYzQO.paramList = selectDataList;
    console.log("选择不动产单元验证参数：");
    console.log(bdcGzYzQO);

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
                addXmShoppingCar(addData, sfcj);
            } else {
                removeModal();
            }
            //展现限制信息
            showXzxx(data,"",function () {
                addXmShoppingCar(checkData, sfcj);
            });
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });


}

function addXmShoppingCar(checkData, sfcj) {
    var insertYxmid = "";
    if(queryType == "1"){
        // 创建项目之前需要执行一下手动插入原项目的数据
        insertYxmid = insertYxmxx();
    }
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
        if(insertYxmid != ""){
            bdcSlYwxxDTO.xmid = insertYxmid;
        }else{
            bdcSlYwxxDTO.xmid = selectData.xmid;
        }
        bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
        bdcSlYwxxDTO.ybdcqz = selectData.bdcqzh;
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlrmc;
        bdcSlYwxxDTO.bdcWlSlXmLsgxDOList = selectData.bdcWlSlXmLsgxDOList;
        bdcSlYwxxDTO.bdcSlYgDTOS = selectData.bdcSlYgDTOS;
        bdcSlYwxxDTO.dyhcxlx = bdcslxztzpz.dyhcxlx;
        bdcSlYwxxDTO.qllx = parseInt(selectData.qllx);
        bdcSlYwxxDTO.sfzlcsh = sfzlcsh;
        bdcSlYwxxDTO.bdclx = selectData.bdclx;
        bdcSlYwxxDTO.fcjyBaxxDTO = selectData.fcjyBaxxDTO;
        bdcSlYwxxDTO.qlrsjly =selectData.qlrsjly;
        bdcSlYwxxDTO.qjgldm = selectData.qjgldm;
        bdcSlYwxxDTO.jzmj = selectData.dzwmj;
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
                if (isNotBlank(processDefKey) && isNotBlank(jbxxid)) {
                    addGwc();
                    cshSelectedXxSingle(jbxxid, processDefKey, false, true, 2);
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

/*
* 点击单元号超链接直接创建
* */
getSelectXmData = function (elem, cj) {
    addModel();
    checkData = [];
    checkData.push(elem);
    sfcj = cj;
    //1.第一步进行规则验证
    checkXm(checkData, sfcj);
};


//单击楼盘表，勾选弹窗内的复选框，点击 确定 按钮，调用当前方法
function xzbdcdyhCallBack(data) {
    layer.close(layer.index);
    if (data && data.length > 0) {
        addModel();
        var bdcdyhStr = '';
        for (var i = 0; i < data.length; i++) {
            bdcdyhStr += data[i].bdcdyh + ',';
        }
        var bdcdyhStrNew = bdcdyhStr.substring(0, bdcdyhStr.length - 1);
        var tab = $('.layui-this').val();
        var cxObjNew = JSON.parse(JSON.stringify(cxObj));
        if (tab === 2) {
            //全选并添加
            var url = getContextPath() + '/bdcdyh/listQlList?loadpage=false';
            if (isNotBlank(bdcdyhStrNew)) {

                if (bdcdyhStrNew.indexOf(",") > -1) {
                    cxObjNew.bdcdyhList = bdcdyhStrNew.split(",");
                    cxObjNew.bdcdyh = "";
                } else {
                    cxObjNew.bdcdyh = bdcdyhStrNew;
                }
                if (isNotBlank(bdcslxztzpz.qllx)) {
                    cxObjNew.qllx = bdcslxztzpz.qllx.split(",");
                }
                if (isNotBlank(bdcslxztzpz.zdtzm)) {
                    cxObjNew.zdtzm = bdcslxztzpz.zdtzm.split(",");
                }
                if (isNotBlank(bdcslxztzpz.dzwtzm)) {
                    cxObjNew.dzwtzm = bdcslxztzpz.dzwtzm.split(",");
                }
                cxObjNew.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
                cxObjNew.dyhcxlx = bdcslxztzpz.dyhcxlx;
                if (isNotBlank(bdcslxztzpz.bdclx)) {
                    cxObjNew.bdclx = bdcslxztzpz.bdclx.split(",");
                }
                if (isNotBlank(bdcslxztzpz.ygdjzl)) {
                    cxObjNew.ygdjzlList = bdcslxztzpz.ygdjzl.split(",");
                }
                autoAddToGwc(cxObjNew, url, 'xmid', 'bdcdyh');
            } else {
                removeModal();
            }

        } else if (tab === 3) {
            //选择查封
            //全选并添加
            var url = getContextPath() + '/bdcdyh/listCfList?loadpage=false';
            if (isNotBlank(bdcdyhStrNew)) {
                cxObjNew.bdcdyh = bdcdyhStrNew.split(",");
                autoAddToGwc(cxObjNew, url, 'qlid', 'bdcdyh');
            } else {
                removeModal();
            }

        }


    }
}

/**
 * cxObj 查询条件
 * 根据条件自动添加到购物车
 */
function autoAddToGwc(cxObj, url, tableid, id) {
    setTimeout(function () {
        var allData = getCxData(cxObj, url);
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
        //自动添加
        if (tableid === "xmid") {
            $("#addXmShoppingCar").click();
        } else if (tableid === "qlid") {
            $("#addCfShoppingCar").click();
        }
        removeModal();
    }, 10);


}

//查询
function getCxData(cxObj, url) {
    var alldata = [];
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        async: false,
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(cxObj),
        success: function (data) {
            alldata = data;
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr, "全选数据失败");

        }

    });
    return alldata;
}

//用于页面展现状态
function getPplzzt(xmly, checkdata, xztzlx) {
    var pplzzt = "";
    var qllx = checkdata[0].qllx;
    var bdcqzh = checkdata[0].bdcqzh;
    var bdcdyh = checkdata[0].bdcdyh;
    //证号不包含"不动产",才判断是否匹配和是否落宗
    // bug 20628 【南通】选择不动产单元时房产他项证不显示匹配落宗状态bug
    if ((isNotBlank(bdcqzh) && bdcqzh.indexOf("不动产") < 0 && isNotBlank(bdcdyh) && xztzlx === 2)
        || (isNotBlank(bdcdyh) && xztzlx === 3)) {

        getReturnData("/bdcdyh/pplzzt?xztzlx=" + xztzlx, JSON.stringify(checkdata), "POST", function (data) {
            if (data !== null) {
                // 权利类型房屋,显示匹配落宗，其他仅显示落宗状态
                if (qllx === "4" || qllx === "6" || qllx === "8") {
                    if (data.sfpp === "true") {
                        pplzzt += '<span class="bdc-ypp">匹配</span>';
                    } else {
                        pplzzt += '<span class="bdc-wpp">未匹配</span>';
                    }
                    if (data.sflz === "true") {
                        pplzzt += '<span class="bdc-ylz">落宗</span>';
                    } else {
                        pplzzt += '<span class="bdc-wlz">未落宗</span>';
                    }

                } else {
                    if (data.sflz === "true") {
                        pplzzt += '<span class="bdc-ylz">落宗</span>';
                    } else {
                        pplzzt += '<span class="bdc-wlz">未落宗</span>';
                    }

                }

                if (qllx === commonDyaq_qllx || qllx === "96" || qllx === "97" || qllx === "98") {
                    if (isNotBlank(data.mullz)) {
                        pplzzt += "<span class='layui-btn layui-btn-xs bdc-major-btn' onclick='checkMullzPp(" + JSON.stringify(checkdata) + "," + xztzlx + "," + '"check"' + ")'>查看</span>";

                    } else {
                        pplzzt += "<span class='layui-btn layui-btn-xs bdc-major-btn' onclick='checkPp(" + JSON.stringify(checkdata) + "," + xztzlx + "," + '"check"' + ")'>查看</span>";

                    }


                } else {
                    if (isNotBlank(data.mullz)) {
                        pplzzt += "<span class='layui-btn layui-btn-xs bdc-major-btn' onclick='checkMullzPp(" + JSON.stringify(checkdata) + "," + xztzlx + "," + '"check"' + ")'>查看</span>";

                    } else {
                        pplzzt += "<span class='layui-btn layui-btn-xs bdc-major-btn' onclick='checkPp(" + JSON.stringify(checkdata) + "," + xztzlx + "," + '"check"' + ")'>查看</span>";

                    }

                }
            }
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        }, false);
    }
    return pplzzt;
}

function bdcdyczTpl(bdcdyh, cqxmid) {
    var bdcdyczTpl = '';
    if (queryType == "1") {
        bdcdyczTpl += "<span class='layui-btn layui-btn-danger layui-btn-xs bdc-secondary-btn' lay-event='addBdcdyh'>添加</span>";
    } else {
        bdcdyczTpl += "  <span class='layui-btn layui-btn-xs bdc-major-btn' onclick=\"showBdcdyXqNew('" + bdcdyh + "','selectBdcdyh.html')\">详情</span>\n" +
            "        <span class='layui-btn layui-btn-danger layui-btn-xs bdc-secondary-btn' lay-event='addBdcdyh'>添加</span>";
    }
    if (showZjjgBtn) {
        bdcdyczTpl += "<span class='layui-btn layui-btn-xs bdc-secondary-btn' onclick=\"showZjjg('" + cqxmid + "')\">资金监管</span>";
    }
    if (sessionStorage.getItem('bdcZt')== 'true') {
        sessionStorage.removeItem("bdcZt");
        bdcdyczTpl += "<span class='layui-btn layui-btn-xs bdc-secondary-btn' onclick=\"showCfdjls('" + bdcdyh + "')\">查封历史</span>";
    }
    return bdcdyczTpl;
}

//查看资金监管
function showZjjg(cqxmid) {
    var gzlslid = "";
    getReturnData("/slym/zjjg/cqxmid", {cqxmid: cqxmid}, "GET", function (data) {
        if (data) {
            gzlslid = data.gzlslid;
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    }, false);
    if (isNotBlank(gzlslid)) {
        var index = layer.open({
            type: 2,
            title: "查看资金监管信息",
            area: ['1300px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: "/realestate-portal-ui/view/popup-img.html?processinsid="+gzlslid
        });
        layer.full(index);
    } else {
        ityzl_SHOW_WARN_LAYER("未获取该产权到对应的资金监管信息");
    }

}


//查看资金监管
function showYthZjjg(cqxmid,htbh) {
    //检测当前是否有一体化资金监管信息
    var json = {};
    var zjjgUrl = "/slym/zjjg/yth";
    getReturnData(zjjgUrl, {processInsId: "processInsId", htbh:htbh}, "GET", function (data) {
        removeModal();
        if(data && data.bdcSlZjjgxyDO){
            json.zjjg = {};
            json.zjjg =data;
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false)
    //没有资金监管信息
    if(isNullOrEmpty(json.zjjg)){
        showZjjg(cqxmid);
        return;
    }

    //有资金监管信息
    if (isNotBlank(htbh)) {
        var index = layer.open({
            type: 2,
            title: "查看一体化资金监管信息",
            area: ['1300px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: "/realestate-accept-ui/view/slym/ythZjjg.html?cqxmid="+cqxmid + "&htbh=" + htbh
        });
        layer.full(index);
    } else {
        ityzl_SHOW_WARN_LAYER("未获取该产权到对应的资金监管信息");
    }

}


