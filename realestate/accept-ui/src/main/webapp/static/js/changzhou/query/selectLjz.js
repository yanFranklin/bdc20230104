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
            {field: 'zldz', title: '坐落', width: "28%",sort: true},
            {field: 'bdcdyh', title: '不动产单元号', templet:function (obj) {
                if(isNotBlank(obj.bdcdyh)) {
                    return '<span title="'+obj.bdcdyh+'">'+queryBdcdyh(obj.bdcdyh)+'</span>'
                } else {
                    return '<span></span>';
                }
            }},
            {field: 'sdzt', title: '锁定类型，锁定原因', minWidth: 200, align: "center"},
            {title: '操作', width: 140, templet: '#ljzcz', align: "center", fixed:"right"}
        ];

        //提交表单
        form.on("submit(queryLjz)", function (data) {
            addModel();
            var url = getContextPath() + '/bdcdyh/listLjzByPageJson';
            var cxObj = data.field;
            //添加模糊类型
            // cxtjAddMhlx(cxObj);
            cxObj.qllx = bdcslxztzpz.qllx;
            cxObj.zdtzm = bdcslxztzpz.zdtzm;
            cxObj.dzwtzm = bdcslxztzpz.dzwtzm;
            cxObj.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
            cxObj.dyhcxlx = bdcslxztzpz.dyhcxlx;
            //加载锁定原因
            cxObj["sfsdzt"] = "1";
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
            }else if(layEvent === "swlpb"){
                getReturnData("/slym/lpb/url/swlpb",{jbxxid: jbxxid, gzldyid: processDefKey, slbh: slbh},"GET",function (data) {
                    if(isNotBlank(data) &&isNotBlank(data.url)) {
                        window.open(data.url);
                    }else{
                        ityzl_SHOW_WARN_LAYER("未获取三维楼盘表地址");
                    }

                },function (error) {
                    delAjaxErrorMsg(error);

                })

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
        bdcSlYwxxDTO.dyhcxlx = bdcslxztzpz.dyhcxlx;
        bdcSlYwxxDTO.yt = selectData.ghyt;
        bdcSlYwxxDTO.fwDcbIndex = selectData.fw_dcb_index;
        bdcSlYwxxDTO.qjgldm = selectData.qjgldm;
        bdcSlYwxxDTO.sfzlcsh = sfzlcsh;
        bdcSlYwxxDTO.bdcWlSlXmLsgxDOList = selectData.bdcWlSlXmLsgxDOList;
        bdcSlYwxxDTO.dyxmidList = selectData.dyxmidList;
        //主要用于楼盘表回调时
        if(!isNotBlank(selectData.fw_dcb_index)) {
            bdcSlYwxxDTO.qjid = selectData.qjid;
            if (isNotBlank(selectData.qlr)) {
                bdcSlYwxxDTO.qlr = selectData.qlr;
            }
            bdcSlYwxxDTO.zszl = selectData.zszl;
            bdcSlYwxxDTO.sfsczs = selectData.sfsczs;
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
               if (isNotBlank(jbxxid) && isNotBlank(processDefKey)) {
                    cshSelectedXxSingle(jbxxid,processDefKey);
                }
            } else {
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("添加成功");
                addGwc();
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

//选择楼盘表
function showLpb(fwDcbIndex,qjgldm) {
    var result = sfdz(fwDcbIndex,qjgldm);
    if(result) {
        ityzl_SHOW_INFO_LAYER("独幢,未找到楼盘表")
    }else{
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
            content: '/building-ui/lpb/view?code=accept&fwDcbIndex=' + fwDcbIndex + "&qjgldm=" + qjgldm + "&zlcsh=" + zlcsh + '&paramJson=' + encodeURI(JSON.stringify(param)),
            cancel: function () {
                addGwc();
            }

        });
        layer.full(index);
    }
}

function sfdz(fwDcbIndex,qjgldm) {
    var result = false;
    if(isNotBlank(fwDcbIndex)) {
        //先判断是否为独幢，独幢提示未找到楼盘表
        getReturnData("/bdcdyh/sfdz",{fwDcbIndex:fwDcbIndex,qjgldm:qjgldm},"GET",function (data) {
            result = data
        },function (err) {
            throw err;
        },false)
    }
    return result;
}

//忽略提示
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
                for (var i = 0; i < checkData.length; i++) {
                    checkData[i].qjgldm =qjgldm;
                }
            }

        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }

    });
    return checkData;


}

//直接通过超链接添加并创建
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
