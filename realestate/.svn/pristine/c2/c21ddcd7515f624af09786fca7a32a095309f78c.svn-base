function initXmTable() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;
        // 当前页数据
        var currentPageData = [];
        //获取查询总页数
        var totalPages =0;

        //不动产单元号的表头
        var unitTableTitle = [{type: 'checkbox', fixed: 'left'},
            {
                field: 'bdcdyh', title: '不动产单元号', width: "22%", templet: '#xmbdcdyhTpl'
            },
            {field: 'bdcqzh', title: '不动产权证（明）号', width: "22%"},
            {field: 'qllx', title: '权利类型', width: "22%", hide: 'true'},
            {field: 'zl', title: '坐落', width: "16%"},
            {field: 'qlrmc', title: '权利人', width: "11.4%"},
            {field: 'zt', title: '状态', width: "11%", templet: '#cqzzt', align: "center"},
            {
                field: 'pplzzt', title: '匹配状态', width: 120, align: "center", templet: function (obj) {
                if (isNotBlank(obj.bdcdyh)) {
                    var data=[];
                    data.push(obj);
                    return getPplzztBtn(obj.xmly, data);
                }
            }
            },
            {field: 'ycqzh', title: '原产权证号', width: "22%"},
            {field: 'yxtcqzh', title: 'TT系统证号', width: "22%"},
            {field: 'cz', title: '操作', width: 230, templet: '#bdcdycz', align: "center", fixed: 'right'}
        ];

        //提交表单
        form.on("submit(queryXm)", function (data) {
            addModel();
            //查询时清除缓存数据
            sessionStorage.checkedData = '{}';
            sessionStorage.allData = '{}';
            isSelectAll =false;
            var url = getContextPath() + '/bdcdyh/listXmByPageJson?loadpage=true';
            cxObj = data.field;
            if(isNotBlank(cxObj.bdcdyh)){
                if(cxObj.bdcdyh.indexOf(",") >-1) {
                    cxObj.bdcdyhList = cxObj.bdcdyh;
                    cxObj.bdcdyh = "";
                }
            }
            cxObj.qllx = bdcslxztzpz.qllx;
            cxObj.zdtzm = bdcslxztzpz.zdtzm;
            cxObj.dzwtzm = bdcslxztzpz.dzwtzm;
            cxObj.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
            cxObj.dyhcxlx = bdcslxztzpz.dyhcxlx;
            cxObj.bdclx = bdcslxztzpz.bdclx;
            cxObj.ygdjzlList = bdcslxztzpz.ygdjzl;
            tableReload('xmid', cxObj, url);
            return false;
        });

        var tableConfig = {
            id: 'xmid',
            toolbar: "#toolbarXm",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            parseData: function (res) {
                currentPageData = res.content;
                totalPages =res.totalPages;
                // 设置选中行
                var checkedData = JSON.parse(sessionStorage.checkedData);
                if (res.content && res.content.length > 0 && !$.isEmptyObject(checkedData)) {
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.xmid) {
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
            var checkedData =obj.type == 'one' ? [obj.data] : currentPageData;
            var myCheckedData = JSON.parse(sessionStorage.checkedData);
            var myAllData = JSON.parse(sessionStorage.allData);
            $.each(checkedData, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    myCheckedData[v.xmid] = v;
                } else {
                    //.删除
                    myCheckedData = deleteCheckedById(myCheckedData,v.xmid);
                }
                if(isSelectAll) {
                    v.checked = obj.checked;
                    myAllData[v.xmid] = v;
                }
            });
            sessionStorage.checkedData = JSON.stringify(myCheckedData);
            sessionStorage.allData = JSON.stringify(myAllData);
            removeModal();

        });

        //监听单元号点击
        $('.bdc-tab-box').on('click','.bdc-xmbdcdyh-click',function () {
            getSelectXmData($(this).data('json'),true);
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
                    checkXm(checkData,sfcj);
                } else {
                    layer.alert("未选择数据!");
                }
            }else if(layEvent ==="allchooseXm"){ //全选
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
                bdcdyhSelectAllFunction(cxObj, url,'xmid','xmid');

            }else if(layEvent ==="allunchooseXm"){//反选
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
                bdcdyhInverseSelectFunction(cxObj, url, 'xmid', 'xmid');
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
            }
            else if(obj.event === 'lpb'){
                var lpbData = obj.data;//可以获取当前行的数据
                getReturnData("/rest/v1.0/slym/fwbdcdy",{bdcdyh:lpbData.bdcdyh,qjgldm:lpbData.qjgldm},"GET",function (data) {
                    if(data &&isNotBlank(data.fwDcbIndex)) {
                        var index = layer.open({
                            type: 2,
                            title: "选择楼盘表",
                            area: ['1300px', '600px'],
                            fixed: false, //不固定
                            maxmin: true, //开启最大化最小化按钮
                            content: "/building-ui/lpb/view?code=getbdcdyh&fwDcbIndex="+data.fwDcbIndex+"&qjgldm="+lpbData.qjgldm
                        });
                        layer.full(index);
                    }else{
                        showAlertDialog("未找到楼盘表");
                    }

                },function (error) {
                    delAjaxErrorMsg(error)

                })

            }
        });
    })
}

function checkXm(checkData, sfcj) {
    var selectDataList = [];
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.yxmid = selectData.xmid;
        bdcGzYzsjDTO.ysfwbm = selectData.ysfwbm;
        bdcGzYzsjDTO.qjgldm = selectData.qjgldm;
        var obj ={};
        obj.realNo =selectData.bdcqzh;
        obj.cardNo =selectData.qlrzjh;
        obj.xmid = selectData.xmid;
        obj.slbh = selectData.slbh;
        obj.fwbm =selectData.ysfwbm;
        obj.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.obj =obj;
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
            var addData =dealYzResult(data);
            //正常验证通过的数据直接添加
            if(addData.length >0){
                addXmShoppingCar(addData,sfcj);
            }else{
                removeModal();
            }
            //展现限制信息
            showXzxx(data);
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });


}

function addXmShoppingCar(checkData, sfcj) {
    if (sfcj) {
        //如果购物车存在数据，先清除购物车数据
        var xmcount = $(".bdc-car").find("span")[0].textContent;
        if (xmcount !== "0") {
            clearGwc(jbxxid,false);
        }
    }
    var selectDataList = [];
    var sfzlcsh =(zlcsh==="true")?1:null;
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcSlYwxxDTO = {};
        bdcSlYwxxDTO.xmid = selectData.xmid;
        bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
        bdcSlYwxxDTO.ybdcqz = selectData.bdcqzh;
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlrmc;
        bdcSlYwxxDTO.bdcWlSlXmLsgxDOList = selectData.bdcWlSlXmLsgxDOList;
        bdcSlYwxxDTO.bdcSlYgDTOS = selectData.bdcSlYgDTOS;
        bdcSlYwxxDTO.dyhcxlx = bdcslxztzpz.dyhcxlx;
        bdcSlYwxxDTO.qllx = parseInt(selectData.qllx);
        bdcSlYwxxDTO.sfzlcsh =sfzlcsh;
        bdcSlYwxxDTO.bdclx = selectData.bdclx;
        bdcSlYwxxDTO.fcjyBaxxDTO = selectData.fcjyBaxxDTO;
        bdcSlYwxxDTO.qjgldm = selectData.qjgldm;
        bdcSlYwxxDTO.qlrsjly =selectData.qlrsjly;
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

function removeXm() {
    //添加到购物车操作
    addXmShoppingCar(checkData, sfcj);
}

getSelectXmData = function (elem, cj) {
    addModel();
    checkData = [];
    checkData.push(elem);
    sfcj = cj;
    //1.第一步进行规则验证
    checkXm(checkData,sfcj)
};

//用于页面展现状态
function getPplzztBtn(xmly,checkdata) {
    var pplzzt = "";
    var xmid = checkdata[0].xmid;
        getReturnData("/bdcdyh/showppbtn", {xmid:xmid}, "GET", function (data) {
            if (data !== null && data.showBtn) {
                pplzzt += "<span class='layui-btn layui-btn-xs bdc-major-btn' onclick='checkPp(" + JSON.stringify(checkdata) + ",2,"+ '"check"' +")'>查看</span>";
            }
        }, function () {
        }, false);
    return pplzzt;
}


//单击楼盘表，勾选弹窗内的复选框，点击 确定 按钮，调用当前方法
function xzbdcdyhCallBack(data){
    layer.close(layer.index);
    if(data &&data.length >0){
        addModel();
        var bdcdyhStr = '';
        for(var i = 0 ; i < data.length ; i++){
            bdcdyhStr += data[i].bdcdyh + ',';
        }
        var bdcdyhStrNew = bdcdyhStr.substring(0,bdcdyhStr.length-1);
        var tab = $('.layui-this').val();
        var cxObjNew = JSON.parse(JSON.stringify(cxObj));
        if (tab === 2) {
            //全选并添加
            var url = getContextPath() + '/bdcdyh/listQlList?loadpage=false';
            if(isNotBlank(bdcdyhStrNew)){

                if(bdcdyhStrNew.indexOf(",") >-1) {
                    cxObjNew.bdcdyhList = bdcdyhStrNew.split(",");
                    cxObjNew.bdcdyh = "";
                }else{
                    cxObjNew.bdcdyh=bdcdyhStrNew;
                }
                if(isNotBlank(bdcslxztzpz.qllx)) {
                    cxObjNew.qllx = bdcslxztzpz.qllx.split(",");
                }
                if(isNotBlank(bdcslxztzpz.zdtzm)) {
                    cxObjNew.zdtzm = bdcslxztzpz.zdtzm.split(",");
                }
                if(isNotBlank(bdcslxztzpz.dzwtzm)) {
                    cxObjNew.dzwtzm = bdcslxztzpz.dzwtzm.split(",");
                }
                cxObjNew.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
                cxObjNew.dyhcxlx = bdcslxztzpz.dyhcxlx;
                if(isNotBlank(bdcslxztzpz.bdclx)) {
                    cxObjNew.bdclx = bdcslxztzpz.bdclx.split(",");
                }
                if (isNotBlank(bdcslxztzpz.ygdjzl)) {
                    cxObjNew.ygdjzlList = bdcslxztzpz.ygdjzl.split(",");
                }
                autoAddToGwc(cxObjNew, url,'xmid','xmid');
            }else{
                removeModal();
            }

        } else if (tab === 3) {
            //选择查封
            //全选并添加
            var url = getContextPath() + '/bdcdyh/listCfList?loadpage=false';
            if(isNotBlank(bdcdyhStrNew)){
                cxObjNew.bdcdyh =bdcdyhStrNew.split(",");
                autoAddToGwc(cxObjNew, url, 'qlid', 'xmid');
            }else{
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
        if(tableid ==="xmid") {
            $("#addXmShoppingCar").click();
        }else if(tableid ==="qlid"){
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
            delAjaxErrorMsg(xhr,"全选数据失败");

        }

    });
    return alldata;
}


