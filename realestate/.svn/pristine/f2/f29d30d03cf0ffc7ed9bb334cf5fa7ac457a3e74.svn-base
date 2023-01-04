function initXmTable() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;
        // 当前页数据
        var currentPageData = [];
        //获取查询总页数
        var totalPages = 0;

        //查询参数对象
        var cxObj = {};

        //不动产单元号的表头
        var unitTableTitle = [{type: 'checkbox', fixed: 'left'},
            {
                field: 'bdcdyh', title: '不动产单元号', width: 262, templet: '#xmbdcdyhTpl'
            },
            {field: 'bdcqzh', title: '不动产权证号', width: 225},
            {field: 'qllx', title: '权利类型', hide: 'true'},
            {field: 'qllxMc', title: '权利类型名称', hide: 'true'},
            {field: 'zl', title: '坐落',sort: true},
            {field: 'qlrmc', title: '权利人'},
            {field: 'qlrzjh', title: '权利人证件号'},
            {
                field: 'pplzzt', title: '匹配落宗状态', width: 142, align: "center", templet: function (obj) {
                if (isNotBlank(obj.bdcdyh)) {
                    var data=[];
                    data.push(obj);
                    return getPplzzt(obj.xmly, data, 2);
                }
                }
            },
            {field: 'zt', title: '限制状态', width: 86, templet: '#cqzzt', align: "center"},
            {
                field: 'blzt', title: '办理状态', hide: 'true', align: "center", templet: function (obj) {
                    if (isNotBlank(obj.bdcdyh) && isNotBlank(obj.xmid)) {
                        return getBlzt(obj.bdcdyh, obj.xmid);
                    }
                }
            },
            {
                field: 'xgyy', width: 225, title: '坐落变更记录', align: "center", templet: function (obj) {
                    if (isNotBlank(obj.bdcdyh)) {
                        return getXgyy(obj.bdcdyh);
                    }
                }
            },
            {title: '操作', width: 180, templet: '#bdcdycz', align: "center", fixed: 'right'}
        ];

        //提交表单
        form.on("submit(queryXm)", function (data) {
            addModel();
            //查询时清除缓存数据
            layui.sessionData("checkedData", null);
            layui.sessionData("allData", null);
            isSelectAll = false;
            var url = getContextPath() + '/bdcdyh/listXmByPageJson?loadpage=true';
            cxObj = data.field;
            cxObj.qllx = bdcslxztzpz.qllx;
            cxObj.zdtzm = bdcslxztzpz.zdtzm;
            cxObj.dzwtzm = bdcslxztzpz.dzwtzm;
            cxObj.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
            cxObj.dyhcxlx = bdcslxztzpz.dyhcxlx;
            cxObj.bdclx = bdcslxztzpz.bdclx;
            cxObj.ygdjzlList = bdcslxztzpz.ygdjzl;
            cxObj.dzwytList = bdcslxztzpz.dzwyt;
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
                totalPages = res.totalPages;
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
                var reverseList = ['zl'];
                reverseTableCell(reverseList, 'xmid');
                if ($('.layui-show .layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        };

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(xmList)', function (obj) {
            addModel();
            var checkedData = obj.type == 'one' ? [obj.data] : currentPageData;
            if (obj.type == 'one') {
                $.each(checkedData, function (i, v) {
                    if (obj.checked) {
                        //.增加已选中项
                        layui.sessionData('checkedData', {
                            key: v.xmid, value: v
                        });
                        //新增，选中添加进购物车
                        checkData = [v];
                        sfcj = false;
                        checkXm(checkData, sfcj)
                    } else {
                        //.删除
                        layui.sessionData('checkedData', {
                            key: v.xmid, remove: true
                        });
                        var inListNum = 0;
                        //从数据库删除一条购物车数据，并从页面移除被删除的行
                        gwcData.forEach(function (value, index) {
                            if (value.bdcdyh == v.bdcdyh) {
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
                checkXm(checkData, false);
            }
        });


        //监听排序事件
        setColorAfterZl('xmList');

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
            var $this = $(this);
            if (layEvent === "cshCreateXm") { //执行单击购物车表格中的创建
                createCsh($this);
            } else if (layEvent === "allchooseXm") { //全选
                var url = getContextPath() + '/bdcdyh/listXmByPageJson?loadpage=false';
                addModel();
                bdcdyhSelectAllFunction(cxObj, url, 'xmid', 'xmid','xm');


            } else if (layEvent === "allunchooseXm") {//反选
                var url = getContextPath() + '/bdcdyh/listXmByPageJson?loadpage=false';
                addModel();
                bdcdyhInverseSelectFunction(cxObj, url, 'xmid', 'xmid','xm');
            }
        });
        table.on('tool(xmList)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'addBdcdyh') {
                checkData = [];
                checkData.push(obj.data);
                if (checkData !== null) {
                    addModel();
                    setTimeout(function () {
                        try {
                            sfcj = false;
                            //添加按钮先验证项目后直接添加购物车，最后直接创建验证是否虚拟
                            $.when(checkXm(checkData, sfcj)).done(function () {

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
    })
}

function checkXm(checkData, sfcj) {
    var selectDataList = [];
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.yxmid = selectData.xmid;
        bdcGzYzsjDTO.bdcqzh = selectData.bdcqzh;
        var obj = {};
        obj.realNo = selectData.bdcqzh;
        obj.cardNo = selectData.qlrzjh;
        obj.xmid = selectData.xmid;
        obj.slbh = selectData.slbh;
        obj.fwbm = selectData.bdcdywybh;
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
        bdcSlYwxxDTO.bdcSlYgDTOS = selectData.bdcSlYgDTOS;
        bdcSlYwxxDTO.dyhcxlx = bdcslxztzpz.dyhcxlx;
        bdcSlYwxxDTO.qllx = parseInt(selectData.qllx);
        bdcSlYwxxDTO.sfzlcsh = sfzlcsh;
        bdcSlYwxxDTO.bdclx = selectData.bdclx;
        bdcSlYwxxDTO.fcjyBaxxDTO = selectData.fcjyBaxxDTO;
        bdcSlYwxxDTO.yt = selectData.dzwyt;
        bdcSlYwxxDTO.qlrsjly =selectData.qlrsjly;
        // 添加合同监管的合同号，到受理交易信息合同号中
        addHtbhToFcjyBaxx(bdcSlYwxxDTO);
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
                checkPplzzt(checkData,sfcj,2);
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

// 添加核验通过的合同号
function addHtbhToFcjyBaxx(bdcSlYwxxDTO){
    var htbh = getHtjgHtbh(bdcSlYwxxDTO.ybdcqz);
    if(isNotBlank(htbh)){
        if(isNotBlank(bdcSlYwxxDTO.fcjyBaxxDTO) && isNotBlank(bdcSlYwxxDTO.fcjyBaxxDTO.bdcSlJyxx)){
            if(isNullOrEmpty(bdcSlYwxxDTO.fcjyBaxxDTO.bdcSlJyxx.htbh)){
                bdcSlYwxxDTO.fcjyBaxxDTO.bdcSlJyxx.htbh = htbh;
            }
        }else{
            bdcSlYwxxDTO.fcjyBaxxDTO = {bdcSlJyxx : {htbh : htbh}};
        }
    }
}
// 获取合同监管的合同编号信息
function getHtjgHtbh(bdcqzh){
    var htbh = "";
    if(!$.isEmptyObject(htjgxx) && isNotBlank(bdcqzh)){
        $.each(htjgxx, function(i, htjg){
            if(htjg.bdcqzh.indexOf(bdcqzh) > -1 && htjg.htzt != "03"){
                htbh = htjg.htbh;
            }
        });
    }
    return htbh;
}

//验证存在虚拟不动产单元号是否可以直接创建流程，如查封等流程
function checksfcjXm() {
    addModel();
    $.ajax({
        url: getContextPath() + "/bdcGzyz/sfcjlc",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {gzldyid: processDefKey},
        success: function (data) {
            if (data) {
                cshSelectedXxSingle(jbxxid,processDefKey);
            } else {
                removeModal();
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function getBlzt(bdcdyh,xmid){
    var blzt = '<p class="bdc-table-state bdc-ql-state">';
    getReturnData("/bdcdyh/ajzt",{bdcdyh:bdcdyh,xmid:xmid},"GET",function (data) {
        if(data >0) {
            blzt += '<span class="bdc-zzbl">正在办理</span>';
            blzt += '</p>';
        } else {
            blzt += '</p>';
        }
    }, function (err) {
        throw err;
    }, false);
    return blzt;
}

/**
 * @param bdcdyh
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 查询户室表中的修改原因字段
 * @date : 2021/1/4 16:10
 */
function getXgyy(bdcdyh) {
    var xgyy = "";
    getReturnData("/bdcdyh/fwhs", {bdcdyh: bdcdyh}, "GET", function (data) {
        if (data && data.xgyy) {
            xgyy = data.xgyy;
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    return xgyy;
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
    checkXm(checkData,sfcj);
};

//创建项目前的验证
function checkPplzzt(checkData, sfcj,xztzlx) {
    getReturnData("/bdcdyh/pplzzt?xztzlx=" + xztzlx, JSON.stringify(checkData), "POST", function (data) {
        removeModal();
        if (data !== null) {
            if(isNotBlank(data.mullz)){
                //存在多条匹配关系
                removeModal();
                showConfirmDialog("提示", "项目存在多条匹配关系，是否重新匹配？", "reloadMatch", "'" + JSON.stringify(checkData) + "','" + xztzlx + "','" +"','"+"','"+ data.mullz + "'");
            }else if (data.sflz === "true") {
                if (data.sfpp === "false") {
                    //已落宗未匹配提示
                    showConfirmDialog("提示", "未匹配土地证号，是否创建流程", "cshWithCjyz", "", "checkPp", "'" + JSON.stringify(checkData) + "','" + xztzlx + "'",true);
                } else if (data.sfpp === "true" ||data.sfpp ===null) {
                    addModel();
                    cshWithCjyz();
                }
            } else if (data.sflz === null && (data.sfpp === null || data.sfpp === "true")) {
                addModel();
                cshWithCjyz();
            } else if(data.sflz === "false"){
                showConfirmDialog("提示", "存在虚拟单元号是否匹配再创建", "checkPp", "'" + JSON.stringify(checkData) + "','" + xztzlx + "'","checksfcjXm");
            }
        }
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}
//用于页面展现状态
function getPplzzt(xmly,checkdata,xztzlx) {
    var pplzzt = "";
    var qllx =checkdata[0].qllx;
    var bdcqzh=checkdata[0].bdcqzh;
    var bdcdyh = checkdata[0].bdcdyh;
    //证号不包含"不动产",才判断是否匹配和是否落宗
    // bug 20628 【南通】选择不动产单元时房产他项证不显示匹配落宗状态bug
    if((isNotBlank(bdcqzh) &&bdcqzh.indexOf("不动产") <0 && isNotBlank(bdcdyh) && xztzlx===2)
        || (isNotBlank(bdcdyh) && xztzlx===3)) {

        getReturnData("/bdcdyh/pplzzt?xztzlx=" + xztzlx, JSON.stringify(checkdata), "POST", function (data) {
            if (data !== null) {
                // 权利类型房屋,显示匹配落宗，其他仅显示落宗状态
                if (qllx ==="4"||qllx ==="6"||qllx ==="8"){
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

                if (qllx ===commonDyaq_qllx||qllx ==="96"||qllx ==="97"||qllx ==="98"){
                    if(isNotBlank(data.mullz)){
                        pplzzt += "<span class='layui-btn layui-btn-xs bdc-major-btn' onclick='checkMullzPp("+ JSON.stringify(checkdata) + ","+xztzlx+","+ '"check"' +",2)'>查看</span>";

                    }else{
                        pplzzt += "<span class='layui-btn layui-btn-xs bdc-major-btn' onclick='checkPp(" + JSON.stringify(checkdata) + ","+xztzlx+","+ '"check"' +",2)'>查看</span>";

                    }


                } else {
                    if(isNotBlank(data.mullz)){
                        pplzzt += "<span class='layui-btn layui-btn-xs bdc-major-btn' onclick='checkMullzPp(" + JSON.stringify(checkdata) + ","+xztzlx+","+ '"check"' +",1)'>查看</span>";

                    }else{
                        pplzzt += "<span class='layui-btn layui-btn-xs bdc-major-btn' onclick='checkPp(" + JSON.stringify(checkdata) + ","+xztzlx+","+ '"check"' +",1)'>查看</span>";

                    }

                }
            }
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        }, false);
    } else if(isNotBlank(bdcqzh) &&bdcqzh.indexOf("不动产") >0 && isNotBlank(bdcdyh)) {
        getReturnData("/bdcdyh/pplzzt?xztzlx=" + xztzlx, JSON.stringify(checkdata), "POST", function (data) {
            if(data.sfcxgl) {
                pplzzt += "<span class='layui-btn layui-btn-xs bdc-major-btn' onclick='checkPp(" + JSON.stringify(checkdata) + ","+xztzlx+","+ '""' +",1)'>重新关联</span>";
            }
        },function (xhr) {
            delAjaxErrorMsg(xhr);
        },false);
    }
    return pplzzt;
}




