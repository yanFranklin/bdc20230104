//加载修正页面表格
function initSfytxzTable() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;
        // 当前页数据
        var currentPageData = [];
        //获取查询总页数
        var totalPages = 0;

        var unitTableTitle = [
            {type: 'checkbox', fixed: 'left'},
            {field: 'slbh', title: '受理编号', width: "10%"},
            {
                field: 'bdcdyh', title: '不动产单元号', width: "24.8%", templet: '#xzbdcdyhTpl'
            },
            {field: 'qllxMc', title: '权利类型名称', hide: 'true'},
            {field: 'zl', title: '坐落', width: "26%"},
            {field: 'zh', title: '幢号', width: 100},
            {field: 'fjh', title: '房间号', width: 100},
            {field: 'dzwmj', title: '建筑面积', width: 120},
            {field: 'qlrmc', title: '权利人', width: "11.4%"},
            {field: 'zt', title: '状态', width: 120, templet: '#cqzzt', align: "center"},
            {field: 'sdzt', title: '锁定类型，锁定原因', minWidth: 200, align: "center"},
            {field: 'cz', title: '操作', width: 130, templet: '#bdcdyXzCz', align: "center", fixed: 'right'}
        ];


        //提交表单
        form.on("submit(querySfytxz)", function (data) {
            addModel();
            //查询时清除缓存数据
            sessionStorage.checkedData = '{}';
            sessionStorage.allData = '{}';
            isSelectAll = false;
            //收费用途修正只修改临时的数据，查询临时数据接口
            var url = getContextPath() + '/bdcdyh/listXmByPageJson?loadpage=true';
            cxObj = data.field;
            //添加模糊类型
            // cxtjAddMhlx(cxObj);
            cxObj.qllx = bdcslxztzpz.qllx;
            cxObj.glfdcq = true;
            cxObj.zdtzm = bdcslxztzpz.zdtzm;
            cxObj.dzwtzm = bdcslxztzpz.dzwtzm;
            cxObj.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
            cxObj.dyhcxlx = bdcslxztzpz.dyhcxlx;
            cxObj.bdclx = bdcslxztzpz.bdclx;
            cxObj.gzldyid = processDefKey;
            cxObj.zslx = bdcslxztzpz.zslx;
            cxObj.ygdjzlList = bdcslxztzpz.ygdjzl;
            if (bdcslxztzpz.qxList && bdcslxztzpz.qxList.length > 0) {
                cxObj.qxdmList = bdcslxztzpz.qxList.join(",");
            }
            //加载锁定原因
            cxObj["sfsdzt"] = "1";
            tableReload('sfytxz', cxObj, url);
            return false;
        });

        var tableConfig = {
            id: 'sfytxz',
            toolbar: "#toolbarXz",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            parseData: function (res) {
                currentPageData = res.content;
                loadTableData = res.content;
                totalPages = res.totalPages;
                // 设置选中行

            },
            done: function (res, curr, count) {
                var reverseList = ['zl'];
                reverseTableCell(reverseList, 'htbh');
                if ($('.layui-show .layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        };

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(sfytxzList)', function (obj) {
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
        $('.bdc-tab-box').on('click', '.bdc-sfytxzbdcdyh-click', function () {
            addModel();
            getSelectSfytxzData($(this).data('json'), true);
        });

        //加载表格
        loadDataTablbeByUrl('#sfytxzList', tableConfig);
        //表格初始化
        table.init('sfytxzList', tableConfig);

        //头工具栏事件
        table.on('toolbar(sfytxzList)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === "createLc") { //添加到购物车

                //优先判断是否勾选了数据创建流程，未勾选数据直接创建
                var checkedData = JSON.parse(sessionStorage.checkedData);
                checkData = [];
                $.each(checkedData, function (key, value) {
                    checkData.push(value);
                });
                if (checkData !== null && checkData.length > 0) {
                    addModel();
                    sfcj = true;
                    xzly = 2;
                    //先添加修正受理项目数据
                    addSfytxzShoppingCar(checkData, jbxxid, processDefKey);
                    lcCsh(jbxxid, processDefKey, false, false, true);
                    // checkCd(checkData, sfcj);
                } else {
                    cdqlr = $('input[name="qlrmc"]').val();
                    cdcqzh = $('input[name="bdcqzh"]').val();
                    cdzl = $('input[name="zl"]').val();
                    xzly = 3;
                    lcCsh(jbxxid, processDefKey, false, false, true, '');
                }
            }
        });


        table.on('tool(sfytxzList)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'addBdcdyh') {
                checkData = [];
                checkData.push(obj.data);
                if (checkData !== null) {
                    addModel();
                    addSfytxzShoppingCar(checkData, jbxxid, processDefKey);
                    xzly = 2;
                    cshSelectedXxSingle(jbxxid, processDefKey, false, true, 2, true);
                }
            }
        });
    })
}

/*
* 点击单元号超链接直接创建
* */
getSelectSfytxzData = function (elem, cj) {
    addModel();
    checkData = [];
    checkData.push(elem);
    sfcj = cj;
    xzly = 2;
    addSfytxzShoppingCar(checkData, jbxxid, processDefKey)
    lcCsh(jbxxid, processDefKey, false, false, true);
};

//添加修正信息到受理购物车数据
function addSfytxzShoppingCar(bdcXmList, jbxxid, gzldyid) {
    var selectDataList = [];
    for (var i = 0; i < bdcXmList.length; i++) {
        var selectData = bdcXmList[i];
        var bdcSlYwxxDTO = {};
        bdcSlYwxxDTO.xmid = selectData.xmid;
        bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
        bdcSlYwxxDTO.ybdcqz = selectData.bdcqzh;
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlrmc;
        bdcSlYwxxDTO.bdcWlSlXmLsgxDOList = selectData.bdcWlSlXmLsgxDOList;
        bdcSlYwxxDTO.bdcSlYgDTOS = selectData.bdcSlYgDTOS;
        bdcSlYwxxDTO.qllx = parseInt(selectData.qllx);
        bdcSlYwxxDTO.sfzlcsh = null;
        bdcSlYwxxDTO.bdclx = selectData.bdclx;
        bdcSlYwxxDTO.fcjyBaxxDTO = selectData.fcjyBaxxDTO;
        bdcSlYwxxDTO.qlrsjly = selectData.qlrsjly;
        bdcSlYwxxDTO.qjgldm = selectData.qjgldm;
        selectDataList.push(bdcSlYwxxDTO);
    }
    var bdcCshSlxmDTO = {};
    bdcCshSlxmDTO.bdcSlYwxxDTOList = selectDataList;
    bdcCshSlxmDTO.gzldyid = gzldyid;
    bdcCshSlxmDTO.jbxxid = jbxxid;
    getReturnData("/addbdcdyh", JSON.stringify(bdcCshSlxmDTO), "POST", function (data) {

    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
}

