
function initCfTable() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;
        // 当前页数据
        var currentPageData = [];
        //获取查询总页数
        var totalPages =0;
        //查询参数对象
        var cxObj = {};

        //不动产单元号的表头
        var unitTableTitle = [{type: 'checkbox', fixed: 'left'},
            {field: 'bdcdyh', title: '不动产单元号', width: "23.2%", templet: '#cfbdcdyhTpl'},
            {field: 'bdcqzh', title: '不动产权证', width: "21%", hide: 'true'},
            {field: 'cfwh', title: '查封文号', width: "18%"},
            {field: 'cflx', title: '查封类型', width: "10%", templet: '#cflx'},
            {field: 'cfjg', title: '查封机关', width: "10%"},
            {field: 'ywrmc', title: '被查封人', width: "9%"},
            {
                field: 'pplzzt', title: '匹配落宗状态', width: "10%", align: "center", templet: function (obj) {
                    if (isNotBlank(obj.bdcdyh)) {
                        var data = [];
                        data.push(obj);
                        return getPplzzt(obj.xmly, data, 3);
                    }
                }
            },
            {field: 'zt', title: '限制状态', width: "10%", templet: '#cqzzt', align: "center"},
            {field: 'blzt', title: '办理状态', hide: 'true', align: "center", templet: function (obj) {
                    if(isNotBlank(obj.bdcdyh) && isNotBlank(obj.xmid)) {
                        return getBlzt(obj.bdcdyh,obj.xmid);
                    }
                }},
            {field: 'zl', title: '坐落', width: "18%",sort: true},
            {title: '操作', width: 180, templet: '#bdcdycz', align: "center",fixed:"right"}
        ];

        //提交表单
        form.on("submit(queryCf)", function (data) {
            addModel();
            //查询时清除缓存数据
            layui.sessionData("checkedData", null);
            layui.sessionData("allData", null);
            isSelectAll =false;
            var url = getContextPath() + '/bdcdyh/listCfByPageJson?loadpage=true';
            cxObj = data.field;
            //合肥需求，查封文号中文括号转为英文括号
            if (isNotBlank(cxObj.cfwh)) {
                cxObj.cfwh = cxObj.cfwh.replace('（', '(');
                cxObj.cfwh = cxObj.cfwh.replace('）', ')');
            }
            cxObj.qllx=bdcslxztzpz.qllx;
            cxObj.zdtzm=bdcslxztzpz.zdtzm;
            cxObj.dzwtzm=bdcslxztzpz.dzwtzm;
            cxObj.bdcdyfwlx=bdcslxztzpz.bdcdyfwlx;
            cxObj.dyhcxlx = bdcslxztzpz.dyhcxlx;
            cxObj.dzwytList = bdcslxztzpz.dzwyt;
            tableReload('qlid', cxObj, url);
            return false;
        });

        var tableConfig = {
            id: 'qlid',
            toolbar: "#toolbarCf",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            page: true,  //开启分页
            parseData: function (res) {
                currentPageData = res.content;
                totalPages =res.totalPages;
                // 设置选中行
                var checkedData = layui.sessionData('checkedData');
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
                //无数据时显示内容调整
                if($('.layui-show .layui-table-body>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        };

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(cfList)', function (obj) {
            addModel();
            var checkedData =obj.type == 'one' ? [obj.data] : currentPageData;
            if (obj.type == 'one') {
                $.each(checkedData, function (i, v) {
                    if (obj.checked) {
                        //.增加已选中项
                        layui.sessionData('checkedData', {
                            key: v.xmid, value: v
                        });
                        //新增，选中添加进购物车
                        checkData = [v];
                        checkCf(checkData,  false);
                    } else {
                        //.删除
                        layui.sessionData('checkedData', {
                            key: v.xmid, remove: true
                        });
                        var inListNum = 0;
                        //从数据库删除一条购物车数据，并从页面移除被删除的行
                        gwcData.forEach(function (value, index) {
                            if (value.bdcdyh == v.bdcdyh &&value.yxmid ==v.xmid) {
                                deleteBdcslxm(value.xmid);
                                gwcData.splice(index, 1);
                                generateGwc(gwcData);
                                inListNum++;
                                return;
                            }

                        });
                        if (gwcData.length === 0) {
                            removeModal();
                        }
                        if (inListNum == 0) {
                            removeModal();
                        }
                    }
                    if (isSelectAll) {
                        v.checked = obj.checked;
                        layui.sessionData('allData', {
                            key: v.xmid, value: v
                        });
                        removeModal();
                    }
                });
            } else {
                checkData =[];
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
                            if(value.bdcdyh == v.bdcdyh &&value.yxmid ==v.xmid){
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
                if(checkData.length !== 0) {
                    checkCf(checkData,  false);
                }
            }
        });

        //监听坐落排序
        setColorAfterZl('cfList');

        //监听单元号点击
        $('.bdc-tab-box').on('click','.bdc-cfbdcdyh-click',function () {
            getSelectCfData($(this).data('json'),true);
        });

        //加载表格
        loadDataTablbeByUrl('#cfList', tableConfig);
        //表格初始化
        table.init('cfList', tableConfig);
        //头工具栏事件
        table.on('toolbar(cfList)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            var $this = $(this);
            if (layEvent === "cshCreateCf") { //执行单击购物车表格中的创建
                createCsh($this);
            }else if(layEvent ==="allchooseCf"){ //全选
                var url = getContextPath() + '/bdcdyh/listCfByPageJson?loadpage=false';
                addModel();
                bdcdyhSelectAllFunction(cxObj, url,'qlid','xmid','cf');

            }else if(layEvent ==="allunchooseCf"){//反选
                var url = getContextPath() + '/bdcdyh/listCfByPageJson?loadpage=false';
                addModel();
                bdcdyhInverseSelectFunction(cxObj, url,'qlid','xmid','cf');
            }
        });
        table.on('tool(cfList)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'cshBdcCf'||obj.event === 'addBdcdyh') {
                checkData =[];
                checkData.push(obj.data);
                if (checkData !== null) {
                    addModel();
                    if(obj.event === 'cshBdcCf'){
                        sfcj =true;
                        checkCf(checkData,true);
                    }else{
                        sfcj =false;
                        checkCf(checkData,false);
                    }

                }
            }
        });
    })
}

function checkCf(checkData,sfcj) {
    var selectDataList = [];
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.cfwh =selectData.cfwh;
        bdcGzYzsjDTO.yxmid =selectData.xmid;
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
                addCfShoppingCar(addData,sfcj);
            }else{
                removeModal();
            }
            //展现限制信息
            showXzxx(data);
        },error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function addCfShoppingCar(checkData,sfcj) {
    if(sfcj) {
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
        bdcSlYwxxDTO.cflx = selectData.cflx;
        bdcSlYwxxDTO.dyhcxlx = bdcslxztzpz.dyhcxlx;
        bdcSlYwxxDTO.ybdcqz = selectData.cfwh;
        bdcSlYwxxDTO.bdcWlSlXmLsgxDOList = selectData.bdcWlSlXmLsgxDOList;
        bdcSlYwxxDTO.sfzlcsh = sfzlcsh;
        bdcSlYwxxDTO.yt = selectData.dzwyt;
        bdcSlYwxxDTO.bdclx = selectData.bdclx;
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
            if(sfcj) {
                addGwc();
                yzCfXnBdcdyh(checkData,"",sfcj);
            }else{
                ityzl_SHOW_SUCCESS_LAYER("添加成功");
                addGwc();
                removeModal();
            }
        },error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

function changeCflx(cflx) {
    for (var i = 0; i < zdCflxList.length; i++) {
        if (cflx === zdCflxList[i].DM) {
            cflx = zdCflxList[i].MC;
        }
    }
    return cflx;
}

function yzCfXnBdcdyh(checkData, count,sfcj) {
    var selectDataList = [];
    for (var i = 0; i < checkData.length; i++) {
            var selectData = checkData[i];
        var bdcSlYwxxDTO = {};
            bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
            bdcSlYwxxDTO.zl = selectData.zl;
            bdcSlYwxxDTO.qlr = selectData.qlr;
            bdcSlYwxxDTO.qjid = selectData.qjid;
            bdcSlYwxxDTO.dyhcxlx = selectData.dyhcxlx;
            if(isNotBlank(selectData.cfwh)) {
                bdcSlYwxxDTO.ybdcqz = selectData.cfwh;
            } else {
                bdcSlYwxxDTO.ybdcqz = selectData.ybdcqz;
            }
            bdcSlYwxxDTO.xmid = selectData.xmid;
            selectDataList.push(bdcSlYwxxDTO);
        }
    var bdcCshSlxmDTO = {};
    bdcCshSlxmDTO.bdcSlYwxxDTOList = selectDataList;
    $.ajax({
        url: getContextPath() + "/bdcGzyz/yzxndyh",
        type: 'POST',
        dataType: 'json',
        async: false,
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcCshSlxmDTO),
        success: function (data) {
            if(data.length >0){
                removeModal();
                showConfirmDialog("提示","所选不动产单元存在虚拟不动产单元号，是否需要匹配","checkPp","'"+JSON.stringify(checkData)+"','"+3+"','" + "','" + "" + 2 + "'","checkSfcjCf","");
            } else {
                cshWithCjyz();
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });

}

//验证存在虚拟不动产单元号是否可以直接创建流程，如查封等流程
function checkSfcjCf() {
    $.ajax({
        url: getContextPath() + "/bdcGzyz/sfcjlc",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {gzldyid : processDefKey},
        success: function (data) {
            if(data) {
                addModel();
                cshWithCjyz();

            }  else {
                removeModal();
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function removeCf() {
    //添加到购物车操作
    addCfShoppingCar(checkData,sfcj);
}

getSelectCfData = function(elem,cj) {
    addModel();
    checkData =[];
    checkData.push(elem);
    sfcj = cj;
    checkCf(checkData,sfcj);
};

