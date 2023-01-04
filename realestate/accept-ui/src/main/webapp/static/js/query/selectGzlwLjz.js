
function initLjzTable() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;


        //不动产单元号的表头
        var unitTableTitle = [{type: 'checkbox', fixed: 'left'},
            {field: 'dh', title: '幢号', width: "7%"},
            {field: 'lszd', title: '隶属宗地', width: "15%",templet:function (obj) {
                    if(isNotBlank(obj.lszd)) {
                        var checkdata = JSON.stringify(obj).replace(/"/g, '&quot;');
                        return '<span style="color:#1d87d1; cursor:pointer" title="'+obj.lszd+'" onclick="getSelectLjzData('+"'"+checkdata+"'"+",true" +')">'+obj.lszd+'</span>'
                    } else {
                        return '<span></span>';
                    }
                }},
            {field: 'fwmc', title: '房屋名称', width: "12%"},
            {field: 'zldz', title: '坐落', width: "28%"},
            {field: 'bdcdyh', title: '不动产单元号', templet:function (obj) {
                    if(isNotBlank(obj.bdcdyh)) {
                        return '<span title="'+obj.bdcdyh+'">'+queryBdcdyh(obj.bdcdyh)+'</span>'
                    } else {
                        return '<span></span>';
                    }
                }},
            {title: '操作', width: 140, templet: '#ljzcz', align: "center", fixed:"right"}
        ];

        //提交表单
        form.on("submit(queryLjz)", function (data) {
            addModel();
            var url = getContextPath() + '/bdcdyh/listLjzByPageJson';
            var cxObj = data.field;
            cxObj.qllx = bdcslxztzpz.qllx;
            cxObj.zdtzm = bdcslxztzpz.zdtzm;
            cxObj.dzwtzm = bdcslxztzpz.dzwtzm;
            cxObj.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
            cxObj.dyhcxlx = bdcslxztzpz.dyhcxlx;
            if (isNotBlank(lszd)) {
                if (lszd.indexOf("GB") > 0){
                    var lszdArr = [];
                    lszdArr.push(lszd);
                    lszdArr.push(lszd.replace("GB", "GX"));
                    cxObj.lszdList = lszd + ',' + lszd.replace("GB", "GX");
                } else {
                    cxObj.lszd = lszd;
                }
            }
            tableReload('fwDcbIndex', cxObj, url);
            return false;
        });

        var tableConfig = {
            id: 'fwDcbIndex',
            toolbar: "#toolbarLjz",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            done: function () {
                var reverseList = ['zldz'];
                reverseTableCell(reverseList,'fwDcbIndex');
                //无数据时显示内容调整
                if($('.layui-show .layui-table-body>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        };

        //加载表格
        loadDataTablbeByUrl('#ljzList', tableConfig);
        //表格初始化
        table.init('ljzList', tableConfig);
        //头工具栏事件
        table.on('toolbar(ljzList)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === "addLjzShoppingCar") { //添加到购物车
                var checkStatus = table.checkStatus(obj.config.id);
                checkData = checkStatus.data;
                if (checkData !== null && checkData.length > 0) {
                    addModel();
                    //添加setTimeout,防止遮罩不及时加载
                    setTimeout(function () {
                        sfcj = false;
                        checkLjz(checkData,sfcj);
                    },10);

                } else {
                    layer.alert("未选择数据!");
                }
            }
        });
        table.on('tool(ljzList)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'cshLjz' || obj.event === 'addLjz') {
                checkData =[];
                checkData.push(obj.data);
                addModel();
                //添加setTimeout,防止遮罩不及时加载
                setTimeout(function () {
                    if (obj.event === 'cshLjz') {
                        sfcj = true;
                        checkLjz(checkData, sfcj);
                    } else {
                        sfcj = false;
                        checkLjz(checkData, sfcj);
                    }
                },10);

            }
        });
    })
}

function checkLjz(checkData,sfcj) {
    //根据逻辑幢获取所有户室数据
    checkData =getAllHsByFwdcbIndex();

    var selectDataList = [];
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcGzYzsjDTO = {};
        if(isNotBlank(selectData.bdcdyh)) {
            bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
            bdcGzYzsjDTO.qjgldm = selectData.qjgldm;
            selectDataList.push(bdcGzYzsjDTO);
        }
    }
    if(selectDataList.length ===0){
        removeModal();
        showAlertDialog("所选逻辑幢未找到有效的户室不动产单元号，请检查数据");
        return false;
    }
    var bdcGzYzQO = {};
    bdcGzYzQO.paramList = selectDataList;
    bdcGzYzQO.zhbs = processDefKey + "_XZBDCDY";
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
                //部分存在验证信息，其他正常单元号可以添加但不允许直接创建
                if(data.length >0){
                    sfcj =false;
                }
                addLjzShoppingCar(addData,sfcj);
            }else{
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



function addLjzShoppingCar(checkData,sfcj) {
    var selectDataList = [];
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

function showLpb(fwDcbIndex,qjgldm) {
    var param = {
        gzldyid: processDefKey,
        jbxxid: jbxxid,
        slbh:slbh
    };
    var index = layer.open({
        type: 2,
        title: "选择楼盘表",
        area: ['1300px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content:  '/building-ui/lpb/view?code=getbdcdyh&fwDcbIndex=' + fwDcbIndex + '&gzlslid=' + gzlslid+"&qjgldm="+qjgldm,
        cancel: function () {
            addGwc();
        }

    });
    layer.full(index);
}

function removeLjz() {
    //添加到购物车操作
    addLjzShoppingCar(checkData,sfcj);
}

//根据已选数据获取对应所有的户室信息
function getAllHsByFwdcbIndex(){
    var fwdcbIndexs = [];
    for (var i = 0; i < checkData.length; i++) {
        fwdcbIndexs.push(checkData[i].fw_dcb_index);
    }
    var qjgldm =checkData[0].qjgldm;
    checkData =[];
    $.ajax({
        url: getContextPath()+"/bdcGzyz/getAllHsByFwdcbIndex",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {fwdcbIndexs:fwdcbIndexs.join(","),bdcdyfwlx:bdcslxztzpz.bdcdyfwlx,qjgldm:qjgldm},
        success: function (data) {
            if(data.length >0){
                checkData =data;
            }

        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }

    });
    return checkData;


}

getSelectLjzData = function(elem,cj) {
    addModel();
    //添加setTimeout,防止遮罩不及时加载
    setTimeout(function () {
        checkData = [];
        checkData.push(JSON.parse(elem));
        sfcj = cj;
        checkLjz(checkData, sfcj);
    },10);
};

//单击楼盘表，勾选弹窗内的复选框，点击 确定 按钮，调用当前方法
function xzbdcdyhCallBack(data) {
    layer.closeAll();
    if (data && data.length > 0) {
        addModel();
        //获取创建所需参数
        getReturnData("/bdcdyh/queryParams", {gzlslid: gzlslid}, "GET", function (paramdata) {
            processDefKey =paramdata.processDefKey;
            jbxxid =paramdata.jbxxid;
            //验证并增量初始化
            checkBdcdyhLjz(data, paramdata.processDefKey, paramdata.jbxxid);

        }, function (error) {
            delAjaxErrorMsg(error);
        }, false);
    }
}

//规则验证
function checkBdcdyhLjz(bdcdydata, processDefKey, jbxxid) {
    //组织规则验证参数
    var selectDataList = [];
    for (var i = 0; i < bdcdydata.length; i++) {
        var selectData = bdcdydata[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.processDefKey = processDefKey;
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
            if (data.length > 0) {
                //展示限制信息
                showXzxx(data);
                //将选择不动产单元data放入，方便后续获取
                sessionStorage.bdcdydata =  JSON.stringify(bdcdydata);
            } else {
                //添加不动产单元
                addBdcdyh(bdcdydata, processDefKey, jbxxid);
            }

        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

//添加不动产单元
function addBdcdyh(bdcdydata, processDefKey, jbxxid) {
    var selectDataList = [];
    for (var i = 0; i < bdcdydata.length; i++) {
        var selectData = bdcdydata[i];
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
            delAjaxErrorMsg(xhr);
        }
    });
}