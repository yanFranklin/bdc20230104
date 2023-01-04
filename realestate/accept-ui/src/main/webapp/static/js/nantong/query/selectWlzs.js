function initWlzsTable() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;

        //不动产单元号的表头
        var unitTableTitle = [{type: 'checkbox', fixed: 'left'},
            {field: 'bdcdyh', title: '不动产单元号', width: "22%"},
            {field: 'bdcqzh', title: '不动产权证号', width: "21.3%"},
            {field: 'zl', title: '坐落', width: "16%",sort: true},
            {field: 'qllx', title: '权利类型', width: "15%",hide:'true'},
            {field: 'qllxMc', title: '权利类型名称', width: "15%",hide:'true'},
            {field: 'qlrmc', title: '权利人'},
            {field: 'qlrzjh', title: '权利人证件号'},
            {field: 'zt', title: '限制状态', width: "11%", templet: '#cqzzt', align: "center"},
            {title: '操作', width: 180, templet: '#bdcdycz', align: "center",fixed:'right'}
        ];

        //提交表单
        form.on("submit(queryWlzs)", function (data) {
            var url = getContextPath() + '/bdcdyh/listXmByPageJson?loadpage=true';
            addModel();
            tableReload('wlzs', data.field, url);
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

        //监听排序事件
        setColorAfterZl('wlzsList');
    })
}

function checkWlzs(checkData) {
    var selectDataList = [];
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.bdcqzh = selectData.bdcqzh;
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
                if(data.length >0){
                    removeModal();
                    //展现限制信息
                    showXzxx(data);
                }else{
                    addWlzs(checkData);

                }
            },error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });

}

//外联证书存在抵押信息带入
function wlzsAndDyaxx(elem,bdcdyh,xmids,sfdrdyaxx,czlx) {
    if(sfdrdyaxx) {
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
            bdcSlYwxxDTO.yt = selectData.dzwyt;
            selectDataList.push(bdcSlYwxxDTO);
        }
        var bdcCshSlxmDTO = {};
        bdcCshSlxmDTO.bdcSlYwxxDTOList = selectDataList;
        bdcCshSlxmDTO.jbxxid = jbxxid;
        bdcCshSlxmDTO.gzldyid = processDefKey;
        $.ajax({
            url: getContextPath() + "/addbdcdyh/addWlzs/dyaxx?xmids=" + xmids,
            type: 'POST',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(bdcCshSlxmDTO),
            success: function (data) {
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("添加成功");
                addGwc();
            },error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    } else {
        addWlzs(checkData);
    }
    remove(elem,czlx);
}


function addWlzs(checkData) {
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
        bdcSlYwxxDTO.yt = selectData.dzwyt;
        selectDataList.push(bdcSlYwxxDTO);
    }

    var bdcCshSlxmDTO = {};
    bdcCshSlxmDTO.bdcSlYwxxDTOList = selectDataList;
    bdcCshSlxmDTO.jbxxid = jbxxid;
    bdcCshSlxmDTO.gzldyid = processDefKey;
    $.ajax({
        url: getContextPath() + "/addbdcdyh/addWlzs",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcCshSlxmDTO),
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("添加成功");
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
                addModel();
                checkWlzs(JSON.parse(checkDataJson));
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function confirmSfzxyql(){
    addWlzs(checkData);
}
