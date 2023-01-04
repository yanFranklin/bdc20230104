function initHthTable() {
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
            {field: 'htbh', title: '合同编号', minWidth:180, templet: '#cjhtbhTpl'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 260, templet: '#htbdcdyhTpl'},
            {field: 'zt', title: '状态', minWidth: 100, templet: '#cqzzt', align: "center"},
            {field: 'bdcqzh', title: '不动产权证（明）号', minWidth: 280},
            {field: 'qlrmc', title: '权利人', minWidth: 150},
            {field: 'qlrzjh', title: '权利人证件号', minWidth: 160},
            {field: 'zl', title: '坐落', minWidth: 280},
            {field: 'zh', title: '幢号', width: 100},
            {field: 'fjh', title: '房间号', width: 100},
            {field: 'dzwmj', title: '建筑面积', width: 120},
            {field: 'qllxMc', title: '权利类型名称', hide: 'true', minWidth: 150},
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
            // {field: 'cz', title: '操作', width: 130, templet: '#hthcz', align: "center", fixed: 'right'}
            {field: 'cz', title: '操作', width: 250, templet: '#hthcz', align: "center", fixed: 'right'}
        ];

        //提交表单
        form.on("submit(queryHth)", function (data) {
            if($("#htbh").val() == ""){
                layer.alert("请输入合同编号!");
                return;
            }

            // 用合同编号查询到jyxx缓存到页面
            var jyxxObj = getHtxxByHtbh();
            if(!jyxxObj || jyxxObj.code != "00000000"){
                layer.alert("通过合同编号未查询到相应的合同信息!");
                return;
            }

            addModel();
            //查询时清除缓存数据
            sessionStorage.checkedData = '{}';
            sessionStorage.allData = '{}';
            isSelectAll = false;
            var url = getContextPath() + '/bdcdyh/listYxmByPageJson?loadpage=true&queryType='+queryType;
            cxObj = data.field;

            cxObj.gzldyid = processDefKey;
            cxObj.qllx = bdcslxztzpz.qllx;
            cxObj.zdtzm = bdcslxztzpz.zdtzm;
            cxObj.dzwtzm = bdcslxztzpz.dzwtzm;
            cxObj.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
            cxObj.bdclx = bdcslxztzpz.bdclx;
            cxObj.zslx = bdcslxztzpz.zslx;
            cxObj.ygdjzlList = bdcslxztzpz.ygdjzl;
            if(bdcslxztzpz.qxList &&bdcslxztzpz.qxList.length >0) {
                cxObj.qxdmList = bdcslxztzpz.qxList.join(",");
            }
            //加载锁定原因
            cxObj["sfsdzt"] = "1";
            tableReload('htbh', cxObj, url);
            return false;
        });

        var tableConfig = {
            id: 'htbh',
            toolbar: "#toolbarHth",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            parseData: function (res) {
                currentPageData = res.content;
                if(currentPageData.length == 0){
                    layer.alert("通过合同编号未查询到相应的产权!");
                }
                loadTableData = res.content;
                totalPages = res.totalPages;
                // 设置选中行
                var checkedData = JSON.parse(sessionStorage.checkedData);
                if (res.content && res.content.length > 0 && !$.isEmptyObject(checkedData)) {
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.htbh) {
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }
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
        table.on('checkbox(hthList)', function (obj) {
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

        //监听合同编号点击
        $('.bdc-tab-box').on('click', '.bdc-cjhth-click', function () {
            getSelectHtData($(this).data('json'), true);
        });
        //监听合同不动产单元号点击
        $('.bdc-tab-box').on('click', '.bdc-htbdcdyh-click', function () {
            getSelectHtData($(this).data('json'), true);
        });

        //加载表格
        loadDataTablbeByUrl('#hthList', tableConfig);
        //表格初始化
        table.init('hthList', tableConfig);
        //头工具栏事件
        table.on('toolbar(hthList)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === "addHthShoppingCar") { //添加到购物车
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
                    checkHth(checkData, sfcj);
                } else {
                    layer.alert("未选择数据!");
                }
            }
        });
        table.on('tool(hthList)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'addBdcdyh') {
                checkData = [];
                checkData.push(obj.data);
                if (checkData !== null) {
                    addModel();
                    setTimeout(function () {
                        try {
                            if (obj.event === 'cshBdcXm') {
                                sfcj = true;
                                $.when(checkHth(checkData, sfcj)).done(function () {
                                })
                            } else {
                                sfcj = false;
                                $.when(checkHth(checkData, sfcj)).done(function () {
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

function checkHth(checkdData, sfcj) {
    var selectDataList = [];
    for (var i = 0; i < checkdData.length; i++) {
        var selectData = checkdData[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcqzh = selectData.bdcqzh;
        bdcGzYzsjDTO.yxmid = selectData.xmid;
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.htbh = selectData.htbh;
        bdcGzYzsjDTO.qjgldm =selectData.qjgldm;
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
                addHthShoppingCar(addData, sfcj);
            } else {
                removeModal();
            }
            //展现限制信息
            showXzxx(data,"",function () {
                addHthShoppingCar(checkData, sfcj);
            });
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });


}

function addHthShoppingCar(checkData, sfcj) {

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
        bdcSlYwxxDTO.bdclx = selectData.bdclx;
        bdcSlYwxxDTO.qjgldm =selectData.qjgldm;

        /**
         *  添加不动产单元的时候，要不用合同号查到的jyxx一并初始化了
         *  这里合同编号查到的只有一条 只能是单条创建，这里不处理循环的问题
         */
        if(htxxObj && htxxObj.bdcSlJyxx){
            var fcjyBaxxDTO = {};
            fcjyBaxxDTO["bdcSlJyxx"] = htxxObj.bdcSlJyxx;
            var bdcSlSqrList =[];
            var bdcQlrList =[];
            //常州接口权利人未拆分,这里需要进行拆分
            if(htxxObj.bdcSlSqr &&htxxObj.bdcSlSqr.length >0){
                for(var s = 0 ;s<htxxObj.bdcSlSqr.length; s++){
                    var sqrmcList =htxxObj.bdcSlSqr[s].sqrmc.split(",");
                    var qlrsxh =0;
                    var ywrsxh =0;
                    for(var sqrlength = 0 ;sqrlength<sqrmcList.length; sqrlength++){
                        var sqr ={};

                        sqr.sqrmc =sqrmcList[sqrlength];
                        sqr.zjzl =htxxObj.bdcSlSqr[s].zjzl;
                        if(isNotBlank(htxxObj.bdcSlSqr[s].zjh) &&htxxObj.bdcSlSqr[s].zjh.split(",").length ===sqrmcList.length) {
                            sqr.zjh = htxxObj.bdcSlSqr[s].zjh.split(",")[sqrlength];
                        }
                        sqr.sqrlb =htxxObj.bdcSlSqr[s].sqrlb;
                        //处理顺序号
                        if(!isNotBlank(htxxObj.bdcSlSqr[s].sxh)) {
                            if (sqr.sqrlb == "1") {
                                qlrsxh++;
                                sqr.sxh = qlrsxh;
                            } else {
                                ywrsxh++;
                                sqr.sxh = ywrsxh;
                            }
                        }else{
                            sqr.sxh =htxxObj.bdcSlSqr[s].sxh;
                        }
                        if(sqr.sqrlb == '1'){
                            sqr.jyfe = 100;
                        }

                        sqr.dh =htxxObj.bdcSlSqr[s].dh;
                        sqr.txdz =htxxObj.bdcSlSqr[s].txdz;
                        sqr.dlrmc =htxxObj.bdcSlSqr[s].dlrmc;
                        sqr.dlrzjzl =htxxObj.bdcSlSqr[s].dlrzjzl;
                        sqr.dlrzjh =htxxObj.bdcSlSqr[s].dlrzjh;
                        sqr.dlrzjh =htxxObj.bdcSlSqr[s].dlrdh;
                        //赋值权利人来源为住建
                        sqr.qlrly =6;
                        bdcSlSqrList.push(sqr);
                        var qlr = JSON.parse(JSON.stringify(sqr));
                        qlr.qlrmc =sqr.sqrmc;
                        qlr.qlrlb =sqr.sqrlb;
                        bdcQlrList.push(qlr);
                    }
                }

            }
            fcjyBaxxDTO["bdcSlSqr"] = bdcSlSqrList;
            fcjyBaxxDTO["bdcQlr"] = bdcQlrList;
            fcjyBaxxDTO.modifyDsQlr =true;
            bdcSlYwxxDTO["fcjyBaxxDTO"] = fcjyBaxxDTO;
        }
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
getSelectHtData = function (elem, cj) {
    addModel();
    checkData = [];
    checkData.push(elem);
    sfcj = cj;
    //1.第一步进行规则验证
    checkHth(checkData, sfcj);
};


/**
 * 缓存合同信息
 */
function getHtxxByHtbh(){
    var htbh = $("#htbh").val();
    $.ajax({
        url: getContextPath() + '/bdcdyh/htxxByHtbh/' + htbh + '/' + processDefKey,
        type: 'POST',
        async: false,
        success: function (data) {
            htxxObj = data;
            htxxObj.htbh = htbh;
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
    return htxxObj;
}




