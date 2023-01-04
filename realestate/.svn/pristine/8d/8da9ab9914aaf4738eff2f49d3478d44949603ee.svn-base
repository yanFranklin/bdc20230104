// 初始化证书锁定表格
function initZsSdTable() {
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
            {field: 'bdcdyh', title: '不动产单元号', width: "21.8%", templet: '#zssdXmbdcdyhTpl'},
            {field: 'cqzh', title: '不动产权证（明）号', width: "22%"},
            {field: 'zl', title: '坐落', width: "16%"},
            {field: 'qlrmc', title: '权利人', width: "11.4%", hide: 'true'},
            {field: 'sdzt', title: '锁定状态', width: "11.4%", templet: function (obj) {
                    var sdzt = obj.sdzt;
                    if (isNotBlank(sdzt) && sdzt == 1) {
                        return "锁定"
                    }
                    return "正常"
                }
            },
            {field: 'sdyy', title: '锁定原因', width: "16%"},
            {field: 'sdr', title: '锁定人', width: "11.4%"},
            {field: 'cz', title: '操作', width: 230, templet: '#sdcz', align: "center", fixed: 'right'}
        ];

        //提交表单
        form.on("submit(queryZsSd)", function (data) {
            addModel();
            //查询时清除缓存数据
            sessionStorage.checkedData = '{}';
            sessionStorage.allData = '{}';
            isSelectAll = false;
            var url = getContextPath() + '/bdcdyh/listZsSdByPageJson?loadpage=true';
            cxObj = data.field;
            //加载锁定原因
            cxObj["sfsdzt"] = "1";
            cxObj.gzldyid = processDefKey;
            //添加模糊类型
            // cxtjAddMhlx(cxObj);
            tableReload('zssd', cxObj, url);
            return false;
        });

        var tableConfig = {
            id: 'zssd',
            toolbar: "#toolbarSd",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            parseData: function (res) {
                currentPageData = res.content;
                totalPages = res.totalPages;
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
        table.on('checkbox(zssdList)', function (obj) {
            var checkedData = obj.type == 'one' ? [obj.data] : currentPageData;
            var myCheckedData = JSON.parse(sessionStorage.checkedData);
            var myAllData = JSON.parse(sessionStorage.allData);
            $.each(checkedData, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    myCheckedData[v.zssdid] = v;
                } else {
                    //.删除
                    myCheckedData = deleteCheckedById(myCheckedData, v.zssdid);
                }
                if (isSelectAll) {
                    v.checked = obj.checked;
                    myAllData[v.zssdid] = v;
                }
            });
            sessionStorage.checkedData = JSON.stringify(myCheckedData);
            sessionStorage.allData = JSON.stringify(myAllData);
            removeModal();

        });

        //监听单元号点击
        $('.bdc-tab-box').on('click', '.bdc-zssdXmbdcdyh-click', function () {
            getSelectZsSdData($(this).data('json'), true);
        });

        //加载表格
        loadDataTablbeByUrl('#zssdList', tableConfig);
        //表格初始化
        table.init('zssdList', tableConfig);
        //头工具栏事件
        table.on('toolbar(zssdList)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === "addSdShoppingCar") { //添加到购物车
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
                    checkSd(checkData, sfcj);
                } else {
                    layer.alert("未选择数据!");
                }
            } else if (layEvent === "allchooseSd") { //全选
                var url = getContextPath() + '/bdcdyh/listZsSdByPageJson?loadpage=false';
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
                bdcdyhSelectAllFunction(cxObj, url, 'zssd', 'zssdid');
            } else if (layEvent === "allunchooseSd") {//反选
                var url = getContextPath() + '/bdcdyh/listZsSdByPageJson?loadpage=false';
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
                bdcdyhInverseSelectFunction(cxObj, url, 'zssd', 'zssdid');
            }
        });
        table.on('tool(zssdList)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'addBdcdyh') {
                checkData = [];
                checkData.push(obj.data);
                if (checkData !== null) {
                    addModel();
                    setTimeout(function () {
                        try {
                            if (obj.event === 'cshBdcXm') {
                                sfcj = true;
                                $.when(checkSd(checkData, sfcj)).done(function () {
                                })
                            } else {
                                sfcj = false;
                                $.when(checkSd(checkData, sfcj)).done(function () {
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
        });
    })
}

function checkSd(checkdData, sfcj) {
    var selectDataList = [];
    for (var i = 0; i < checkdData.length; i++) {
        var selectData = checkdData[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcqzh = selectData.cqzh;
        bdcGzYzsjDTO.yxmid = selectData.xmid;
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.qjgldm =selectData.qjgldm;
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
                addSdShoppingCar(addData, sfcj);
            } else {
                removeModal();
            }
            //展现限制信息
            showXzxx(data,"",function () {
                addSdShoppingCar(checkData, sfcj);
                
            });
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });


}

function addSdShoppingCar(checkData, sfcj) {
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
        bdcSlYwxxDTO.ybdcqz = selectData.cqzh;
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlrmc;
        bdcSlYwxxDTO.bdclx = selectData.bdclx;
        bdcSlYwxxDTO.qjgldm =selectData.qjgldm;
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
getSelectZsSdData = function (elem, cj) {
    addModel();
    checkData = [];
    checkData.push(elem);
    sfcj = cj;
    //1.第一步进行规则验证
    checkSd(checkData, sfcj);
};

// 初始化单元锁定表格
function initDySdTable() {
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
            {field: 'bdcdyh', title: '不动产单元号', width: "21.8%", templet: '#dysdXmbdcdyhTpl'},
            {field: 'zl', title: '坐落', width: "16%"},
            {field: 'qlrmc', title: '权利人', width: "11.4%"},
            {
                field: 'sdzt', title: '锁定状态', width: "11.4%", templet: function (obj) {
                    var sdzt = obj.sdzt;
                    if (isNotBlank(sdzt) && sdzt == 1) {
                        return "锁定"
                    }
                    return "正常"
                }
            },
            {field: 'sdyy', title: '锁定原因', width: "16%"},
            {field: 'sdr', title: '锁定人', width: "11.4%"},
            {field: 'cz', title: '操作', templet: '#sdcz', align: "center", fixed: 'right'}
        ];

        //提交表单
        form.on("submit(queryDySd)", function (data) {
            addModel();
            //查询时清除缓存数据
            sessionStorage.checkedData = '{}';
            sessionStorage.allData = '{}';
            isSelectAll = false;
            var url = getContextPath() + '/bdcdyh/listDySdByPageJson?loadpage=true';
            cxObj = data.field;
            //加载锁定原因
            cxObj["sfsdzt"] = "1";
            cxObj["sdlx"] = 8;
            //撤销登记查询锁定类型为9 的数据
            if (sfcxdj) {
                cxObj["sdlx"] = 9;
            }
            cxObj.gzldyid = processDefKey;
            //添加模糊类型
            // cxtjAddMhlx(cxObj);
            tableReload('dysd', cxObj, url);
            return false;
        });

        var tableConfig = {
            id: 'dysd',
            toolbar: "#toolbarDySd",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            parseData: function (res) {
                currentPageData = res.content;
                totalPages = res.totalPages;
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
        table.on('checkbox(dysdList)', function (obj) {
            var checkedData = obj.type == 'one' ? [obj.data] : currentPageData;
            var myCheckedData = JSON.parse(sessionStorage.checkedData);
            var myAllData = JSON.parse(sessionStorage.allData);
            $.each(checkedData, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    myCheckedData[v.dysdid] = v;
                } else {
                    //.删除
                    myCheckedData = deleteCheckedById(myCheckedData, v.dysdid);
                }
                if (isSelectAll) {
                    v.checked = obj.checked;
                    myAllData[v.dysdid] = v;
                }
            });
            sessionStorage.checkedData = JSON.stringify(myCheckedData);
            sessionStorage.allData = JSON.stringify(myAllData);
            removeModal();

        });

        //监听单元号点击
        $('.bdc-tab-box').on('click', '.bdc-dysdXmbdcdyh-click', function () {
            getSelectDySdData($(this).data('json'), true);
        });

        //加载表格
        loadDataTablbeByUrl('#dysdList', tableConfig);
        //表格初始化
        table.init('dysdList', tableConfig);
        //头工具栏事件
        table.on('toolbar(dysdList)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === "addDySdShoppingCar") { //添加到购物车
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
                    checkDySd(checkData, sfcj);
                } else {
                    layer.alert("未选择数据!");
                }
            } else if (layEvent === "allchooseDySd") { //全选
                var url = getContextPath() + '/bdcdyh/listDySdByPageJson?loadpage=false';
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
                bdcdyhSelectAllFunction(cxObj, url, 'dysd', 'dysdid');
            } else if (layEvent === "allunchooseDySd") {//反选
                var url = getContextPath() + '/bdcdyh/listDySdByPageJson?loadpage=false';
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
                bdcdyhInverseSelectFunction(cxObj, url, 'dysd', 'dysdid');
            }
        });
        table.on('tool(dysdList)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'addBdcdyh') {
                checkData = [];
                checkData.push(obj.data);
                if (checkData !== null) {
                    addModel();
                    setTimeout(function () {
                        try {
                            if (obj.event === 'cshBdcXm') {
                                sfcj = true;
                                $.when(checkDySd(checkData, sfcj)).done(function () {
                                })
                            } else {
                                sfcj = false;
                                $.when(checkDySd(checkData, sfcj)).done(function () {
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
        });
    })
}

function checkDySd(checkdData, sfcj) {
    var selectDataList = [];
    for (var i = 0; i < checkdData.length; i++) {
        var selectData = checkdData[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.yxmid = selectData.xmid;
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.qjgldm =selectData.qjgldm;
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
                addDySdShoppingCar(addData, sfcj);
            } else {
                removeModal();
            }
            //展现限制信息
            showXzxx(data,"",function () {
                addDySdShoppingCar(checkData, sfcj);

            });
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

function addDySdShoppingCar(checkData, sfcj) {
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
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlrmc;
        bdcSlYwxxDTO.bdclx = selectData.bdclx;
        bdcSlYwxxDTO.qjgldm =selectData.qjgldm;
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
getSelectDySdData = function (elem, cj) {
    addModel();
    checkData = [];
    checkData.push(elem);
    sfcj = cj;
    //1.第一步进行规则验证
    checkDySd(checkData, sfcj);
};