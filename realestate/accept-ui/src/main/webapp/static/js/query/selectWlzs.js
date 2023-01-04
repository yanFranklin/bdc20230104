function initWlzsTable() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;

        //不动产单元号的表头
        var unitTableTitle = [{type: 'checkbox', fixed: 'left'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 280},
            {field: 'bdcqzh', title: '不动产权证（明）号', minWidth: 250},
            {field: 'zl', title: '坐落', minWidth: 200},
            {field: 'qllx', title: '权利类型', width: "22%",hide:'true'},
            {field: 'qlrmc', title: '权利人', minWidth: 110},
            {field: 'zt', title: '状态', minWidth: 130, templet: '#cqzzt', align: "center"},
            {field: 'cz', title: '操作', width: 180, templet: '#wlzscz', align: "center",fixed:'right'}
        ];

        //提交表单
        form.on("submit(queryWlzs)", function (data) {
            var url = getContextPath() + '/bdcdyh/listXmByPageJson?loadpage=true';

            var cxObj = data.field;
            //添加模糊类型
            cxtjAddMhlx(cxObj);
            addModel();
            if(bdcslxztzpz.qxList &&bdcslxztzpz.qxList.length >0) {
                cxObj.qxdmList = bdcslxztzpz.qxList.join(",");
            }
            cxObj.dzwytList = bdcslxztzpz.dzwyt;
            cxObj.gzldyid = processDefKey;
            tableReload('wlzs', cxObj, url);
            return false;
        });

        var tableConfig = {
            id: 'wlzs',
            toolbar: "#toolbarWlzs",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            done: function () {
                var reverseList = ['zl'];
                reverseTableCell(reverseList,'wlzs');
                //无数据时显示内容调整
                if($('.layui-show .layui-table-body>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        };

        //加载表格
        loadDataTablbeByUrl('#wlzsList', tableConfig);
        //表格初始化
        table.init('wlzsList', tableConfig);
        //头工具栏事件
        table.on('toolbar(wlzsList)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent == "addWlzs") { //添加外联证书
                var checkStatus = table.checkStatus(obj.config.id);
                    checkData = checkStatus.data;
                if (checkData !== null && checkData.length > 0) {
                    addModel();
                    yzWlzsXnBdcdyh(checkData);
                }else{
                    layer.alert("未选择数据!");
                }
            }
        });
        table.on('tool(wlzsList)', function (obj) { //wlzsList为table的lay-filter对应的值
            if (obj.event === 'cshWlzs' ||obj.event === 'addBdcdyh' ) {
                checkData =[];
                checkData.push(obj.data);
                if (checkData !== null && checkData.length > 0) {
                    addModel();
                    yzWlzsXnBdcdyh(checkData);
                }
            }
        });
    })
}

function checkWlzs(checkData) {
    var selectDataList = [];
    //定义对象,主要针对一些需要结合所有选择的数据的验证
    var bdcXzWlzsDTO ={};
    bdcXzWlzsDTO.jbxxid =jbxxid;
    var selectxmids =[];
    for (var i = 0; i < checkData.length; i++) {
        selectxmids.push(checkData[i].xmid);
    }
    bdcXzWlzsDTO.wlxmidList =selectxmids;
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.qlrzjh = selectData.qlrzjh;
        bdcGzYzsjDTO.qlrmc = selectData.qlrmc;
        bdcGzYzsjDTO.wlxmid =selectData.xmid;
        bdcXzWlzsDTO.wlxmid =selectData.xmid;
        bdcGzYzsjDTO.bdcXzWlzsDTO=bdcXzWlzsDTO;
        selectDataList.push(bdcGzYzsjDTO);
    }
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = processDefKey + "_WLZS";
    bdcGzYzQO.paramList = selectDataList;
    $.ajax({
            url: getContextPath() + '/bdcGzyz',
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(bdcGzYzQO),
            success: function (data) {
                //过滤注销原权利选择是的规则提示
                var yzxx =filterWlzsTsxx(data);
                if(yzxx.wlzsyz.length >0){
                    removeModal();
                    //展现限制信息
                    showXzxx(yzxx.wlzsyz,"",function (){
                        confirmSfzxyql(yzxx.wlzxyqlyz);
                    });
                }else{
                    removeModal();
                    confirmSfzxyql(yzxx.wlzxyqlyz);
                }
            },error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });

}

//将标记为WLZXYQL的提示信息抽取出来，在外联证书选择注销的时候提示
function filterWlzsTsxx(yzResult){
    var all_yzxx ={};
    var wlzxyql_yzxx =[];
    var wlzs_yzxx =[];
    if(yzResult.length >0) {
        $.each(yzResult, function (index, item) {
            if (isNotBlank(item.msg) && (item.msg.indexOf("WLZXYQL") > -1)) {
                item.msg =item.msg.replace("WLZXYQL","");
                wlzxyql_yzxx.push(item);
            } else {
                wlzs_yzxx.push(item);

            }
        });
    }
    all_yzxx.wlzxyqlyz =wlzxyql_yzxx;
    all_yzxx.wlzsyz =wlzs_yzxx;
    return all_yzxx;

}

function addWlzs(zxyql) {
    var selectDataList = [];
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcSlYwxxDTO = {};
        bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlr;
        bdcSlYwxxDTO.qjid = selectData.qjid;
        bdcSlYwxxDTO.dyhcxlx = selectData.dyhcxlx;
        bdcSlYwxxDTO.ybdcqz = selectData.bdcqzh;
        bdcSlYwxxDTO.xmid = selectData.xmid;
        bdcSlYwxxDTO.qllx = parseInt(selectData.qllx);
        bdcSlYwxxDTO.wlwithdyZs =selectData.wlwithdyZs;
        bdcSlYwxxDTO.zxyql = zxyql;

        selectDataList.push(bdcSlYwxxDTO);
    }

    var bdcCshSlxmDTO = {};
    bdcCshSlxmDTO.bdcSlYwxxDTOList = selectDataList;
    bdcCshSlxmDTO.jbxxid = jbxxid;
    bdcCshSlxmDTO.gzldyid = processDefKey;
    var xmids =sessionStorage.getItem('wlzs_xmids'+jbxxid);
    if(xmids ==null ||xmids =="null" ){
        xmids ="";
    }
    bdcCshSlxmDTO.xmids = xmids;
    $.ajax({
        url: getContextPath() + "/addbdcdyh/addWlzs",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcCshSlxmDTO),
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("添加成功");
            if(isNotBlank(xmids)){
                //外联证书
                closeWin();
                parent.wlzsCallback(ymbz);
            }
            addGwc();
        },error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

function yzWlzsXnBdcdyh(checkData) {
    var selectDataList = [];
    for (var i = 0; i < checkData.length; i++) {
            var selectData = checkData[i];
            var bdcSlYwxxDTO = {};
            bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
            bdcSlYwxxDTO.zl = selectData.zl;
            bdcSlYwxxDTO.qlr = selectData.qlr;
            bdcSlYwxxDTO.qjid = selectData.qjid;
            bdcSlYwxxDTO.dyhcxlx = selectData.dyhcxlx;
            bdcSlYwxxDTO.ybdcqz = selectData.bdcqzh;
            bdcSlYwxxDTO.xmid = selectData.xmid;
            bdcSlYwxxDTO.qjgldm = selectData.qjgldm;
            bdcSlYwxxDTO.qllx = parseInt(selectData.qllx);

            selectDataList.push(bdcSlYwxxDTO);
        }

    var bdcCshSlxmDTO = {};
    bdcCshSlxmDTO.bdcSlYwxxDTOList = selectDataList;
    bdcCshSlxmDTO.jbxxid = jbxxid;
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
                showConfirmDialog("提示","所选不动产单元存在虚拟不动产单元号，是否需要匹配","showMatchData","'"+JSON.stringify(data)+"','"+5+"'","checkSfcjWlzs","'"+JSON.stringify(checkData)+"'");
            }else{
                checkWlzs(checkData);
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });

}

//验证存在虚拟不动产单元号是否可以直接添加外联证书
function checkSfcjWlzs(checkDataJson) {
    $.ajax({
        url: getContextPath() + "/bdcGzyz/sfcjlc",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {gzldyid : processDefKey},
        success: function (data) {
            if(data) {
                checkWlzs(JSON.parse(checkDataJson));
            }  else {
                removeModal();
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function confirmSfzxyql(wlzxyqlyz){
    removeModal();
    var index = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: "提示",
            area: ['320px'],
            content: "选择的外联证书是否需要注销？",
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                if(wlzxyqlyz &&wlzxyqlyz.length >0){
                    showXzxx(wlzxyqlyz,"",function (){
                        addModel();
                        addWlzs(1);
                        layer.close(index);
                    });
                }else{
                    addModel();
                    addWlzs(1);
                    layer.close(index);
                }
            },
            btn2: function () {
                addModel();
                addWlzs(0);
                layer.close(index);
            }
        });

}
