function initCdTable() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;
        // 当前页数据
        var currentPageData = [];
        //获取查询总页数
        var totalPages = 0;

        var totalNum;
        //不动产单元号的表头
        var unitTableTitle =
            [
                {type: 'checkbox', fixed: 'left'},
                {
                    field: 'bdcdyh', title: '不动产单元号', templet: '#cdbdcdyhTpl', minWidth: 260
                },
                {field: 'bdcqzh', title: '不动产权证（明）号', minWidth: 260},
                {
                    field: 'ssxz', title: '乡镇', templet: function (d) {
                        return changeDmToMc(d.ssxz, zdList.jddm);
                    }
                },
                {field: 'zl', title: '坐落'},
                {field: 'qlrmc', title: '权利人'},
                {field: 'qlrzjh', title: '权利人证件号', minWidth: 160},
                {
                    field: 'djsj', title: '登记时间', width: 150,
                    templet: function (d) {
                        if (d.djsj) {
                            return Format(format(d.djsj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }},
                {field: 'zt', title: '状态', width: 120, templet: '#cqzzt', align: "center"},
                {field: 'sdzt', title: '锁定类型，锁定原因', minWidth: 200, align: "center"},
            ];


        //提交表单
        form.on("submit(queryCd)", function (data) {
            addModel();
            //查询时清除缓存数据
            sessionStorage.checkedData = '{}';
            sessionStorage.allData = '{}';
            isSelectAll = false;
            var url = getContextPath() + '/bdcdyh/listXmByPageJson?loadpage=true';

            cxObj = data.field;

            if (isNotBlank(cxObj.bdcdyh)) {
                if (cxObj.bdcdyh.indexOf(",") > -1) {
                    cxObj.bdcdyhList = cxObj.bdcdyh;
                    cxObj.bdcdyh = "";
                }
            }
            //添加模糊类型
            // cxtjAddMhlx(cxObj);
            cxObj.qllx = bdcslxztzpz.qllx;
            cxObj.zdtzm = bdcslxztzpz.zdtzm;
            cxObj.dzwtzm = bdcslxztzpz.dzwtzm;
            cxObj.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
            cxObj.dyhcxlx = bdcslxztzpz.dyhcxlx;
            cxObj.bdclx = bdcslxztzpz.bdclx;
            cxObj.gzldyid = processDefKey;
            cxObj.zslx = bdcslxztzpz.zslx;
            cxObj.sfahdj = 1;
            cxObj.ygdjzlList = bdcslxztzpz.ygdjzl;
            if (bdcslxztzpz.qxList && bdcslxztzpz.qxList.length > 0) {
                cxObj.qxdmList = bdcslxztzpz.qxList.join(",");
            }
            //加载锁定原因
            cxObj["sfsdzt"] = "1";
            tableReload('cdid', cxObj, url);
            if (bysldj) {
                $("#refuse").hide();
            }
            return false;
        });

        var tableConfig = {
            id: 'cdid',
            toolbar: "#toolbarCd",
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
                if (bysldj) {
                    $("#refuse").hide();
                }
                var reverseList = ['zl'];
                reverseTableCell(reverseList, 'cdid');
                if ($('.layui-show .layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
                $($('.bdc-percentage-container')[0]).attr('style', 'padding-top: ' + ($('.bdc-form-div .layui-show .bdc-search-content').height() + 10) + "px");
            }
        };

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(cdList)', function (obj) {
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
        $('.bdc-tab-box').on('click', '.bdc-cdbdcdyh-click', function () {
            getSelectCdData($(this).data('json'), true);
        });

        //加载表格
        loadDataTablbeByUrl('#cdList', tableConfig);
        //表格初始化
        table.init('cdList', tableConfig);
        //头工具栏事件
        table.on('toolbar(cdList)', function (obj) {
            var $this = $(this);
            var layEvent = obj.event; //获得 lay-event 对应的值
            var checkStatus = table.checkStatus(obj.config.id);
            var datas = checkStatus.data;
            if (layEvent === "downloadCdData") {
                //添加模糊类型
                // cxtjAddMhlx(cxObj);
                var cxObjDownload = form.val('bdcdyxxFilter');
                var bdcqzh = $("#bdcdyxxFrom").find("input[name='bdcqzh']").val();
                var dyazmh = $("#bdcdyxxFrom").find("input[name='dyazmh']").val();
                var zl = $("#bdcdyxxFrom").find("input[name='zl']").val();
                var qlrmc = $("#bdcdyxxFrom").find("input[name='qlrmc']").val();
                var qlrzjh = $("#bdcdyxxFrom").find("input[name='qlrzjh']").val();
                var bdcqzhjc = $("#bdcdyxxFrom").find("input[name='bdcqzhjc']").val();
                if(isNullOrEmpty(bdcqzh) && isNullOrEmpty(dyazmh) && isNullOrEmpty(zl)
                    && isNullOrEmpty(qlrmc)&& isNullOrEmpty(qlrzjh)&& isNullOrEmpty(bdcqzhjc)
                    && isNullOrEmpty(datas)
                ){
                    layer.alert("导出时至少输入一个查询条件");
                    return;
                }
                if(!isNullOrEmpty(bdcslxztzpz.qllx)){
                    cxObjDownload.qllx = bdcslxztzpz.qllx.split(",");
                }
                cxObjDownload.zdtzm = bdcslxztzpz.zdtzm;
                if(!isNullOrEmpty(bdcslxztzpz.dzwtzm)){
                    cxObjDownload.dzwtzm = bdcslxztzpz.dzwtzm.split(",");
                }
                cxObjDownload.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
                cxObjDownload.dyhcxlx = bdcslxztzpz.dyhcxlx;
                if(!isNullOrEmpty(bdcslxztzpz.bdclx)){
                    cxObjDownload.bdclx = bdcslxztzpz.bdclx.split(",");
                }
                cxObjDownload.gzldyid = processDefKey;
                cxObjDownload.zslx = bdcslxztzpz.zslx;
                cxObjDownload.sfahdj = 1;
                cxObjDownload.ygdjzlList = bdcslxztzpz.ygdjzl;
                if (bdcslxztzpz.qxList && bdcslxztzpz.qxList.length > 0) {
                    cxObjDownload.qxdmList = bdcslxztzpz.qxList;
                }
                //添加选中的条件
                var xmid = [];
                $.each(datas,function (key,data){
                    xmid.push(data.xmid)
                });
                cxObjDownload.xmid = xmid;
                //加载锁定原因
                cxObjDownload["sfsdzt"] = "1";
                // $.each(cxObj,function (key,val){
                //     if(!isNullOrEmpty(val) && typeof(val)=="string"  && val.indexOf(",")){
                //         cxObj[key] = val.split(",")
                //     }
                // })
                var param = "param="+encodeURI(JSON.stringify(cxObjDownload));
                // 重新请求
                addModel();
                var pageParam = "page=1" + "&size=2000";
                var url = getContextPath() + '/bdcdyh/listXmByPageJson/download/cd?loadpage=true&'+ param + "&" +pageParam;
                window.open(url, "DOWNLOAD");
                removeModal()
            }
            if (layEvent === "addCdShoppingCar") { //添加到购物车
                //避免重复点击
                $this.attr("disabled",true).css("pointer-events","none");
                var checkedData = JSON.parse(sessionStorage.checkedData);
                checkData = [];
                $.each(checkedData, function (key, value) {
                    checkData.push(value);
                });
                if (checkData !== null && checkData.length > 0) {
                    addModel();
                    sfcj = true;
                    cdlb = '1';
                    cdcqzh = cxObj.bdcqzh;
                    cdqlr = cxObj.qlrmc;
                    cdzl = cxObj.zl;
                    cdqlrZjh = cxObj.qlrzjh;
                    checkCd(checkData, sfcj);
                    $this.removeAttr("disabled").css("pointer-events","auto");
                } else {
                    addModel();
                    cdlb = '2';
                    cdcqzh = cxObj.bdcqzh;
                    cdqlr = cxObj.qlrmc;
                    cdzl = cxObj.zl;
                    cdqlrZjh = cxObj.qlrzjh;
                    //查档查询如果未选择数据，也可直接创建
                    setTimeout(function(){
                        lcCsh(jbxxid, processDefKey, false, false, true);
                        $this.removeAttr("disabled").css("pointer-events","auto");
                    },50)

                }
            }
            //不予受理直接创建
            if (layEvent === 'refuse') {
                addModel();
                cdlb = '3';
                //查档查询如果未选择数据，也可直接创建
                lcCsh(jbxxid, processDefKey, false, false, true);
            }
        });
        table.on('tool(cdList)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'cshBdcXm' || obj.event === 'addBdcdyh') {
                checkData = [];
                checkData.push(obj.data);
                if (checkData !== null) {
                    addModel();
                    setTimeout(function () {
                        try {
                            if (obj.event === 'cshBdcXm') {
                                sfcj = true;
                                $.when(checkCd(checkData, sfcj)).done(function () {
                                })
                            } else {
                                sfcj = false;
                                $.when(checkCd(checkData, sfcj)).done(function () {
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
    });
}

function checkCd(checkdData, sfcj) {
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
                addCdShoppingCar(addData, sfcj);
            } else {
                removeModal();
            }
            //展现限制信息
            showXzxx(data,"",function () {
                //添加到购物车操作
                addCdShoppingCar(checkData, sfcj);

            });
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });


}

function addCdShoppingCar(checkData, sfcj) {

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
        bdcSlYwxxDTO.qjgldm = selectData.qjgldm;
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
getSelectCdData = function (elem, cj) {
    addModel();
    checkData = [];
    checkData.push(elem);
    sfcj = cj;
    cdlb = '1';
    cdcqzh = cxObj.bdcqzh;
    cdqlr = cxObj.qlrmc;
    cdzl = cxObj.zl;
    cdqlrZjh = cxObj.qlrzjh;
    //1.第一步进行规则验证
    checkCd(checkData, sfcj);
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
                autoAddToGwc(cxObjNew, url, 'cdid', 'bdcdyh');
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
        if (tableid === "cdid") {
            $("#addCdShoppingCar").click();
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
                // 权利类型为土地或者限制权利时，只显示落宗状态
                if (qllx === "1" || qllx === "2" || qllx === "3" || qllx === "5" || qllx === "7"
                    || qllx === commonDyaq_qllx || qllx === "96" || qllx === "97" || qllx === "98") {
                    if (data.sflz === "true") {
                        pplzzt += '<span class="bdc-ylz">落宗</span>';
                    } else {
                        pplzzt += '<span class="bdc-wlz">未落宗</span>';
                    }
                } else {
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



