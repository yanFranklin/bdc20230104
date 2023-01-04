function initCfTable() {
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
            {field: 'bdcdyh', title: '不动产单元号', width: "23.2%",templet: '#cfbdcdyhTpl'
            },
            {field: 'cfwh', title: '查封文号', width: "18%"},
            {field: 'cflx', title: '查封类型', width: "10%", templet: '#cflx'},
            {field: 'cfjg', title: '查封机关', width: "10%"},
            {field: 'ywrmc', title: '被查封人', width: "9%"},
            {field: 'cfqssj', sort: true, title: '查封起始时间', width: "9%",
                templet: function (d) {
                    if (d.cfqssj) {
                        return Format(format(d.cfqssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }},
            {field: 'cfjssj', sort: true, title: '查封结束时间', width: "9%",
                templet: function (d) {
                    if (d.cfjssj) {
                        return Format(format(d.cfjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }},
            {field: 'zt', title: '状态', width: "10%", templet: '#cqzzt', align: "center"},
            {field: 'zl', title: '坐落', width: "18%"},
            {field: 'cz', title: '操作', width: 230, templet: '#bdcdycz', align: "center",fixed:"right"}
        ];

        //提交表单
        form.on("submit(queryCf)", function (data) {
            addModel();
            //查询时清除缓存数据
            sessionStorage.checkedData = '{}';
            sessionStorage.allData = '{}';
            isSelectAll =false;
            var url = getContextPath() + '/bdcdyh/listCfByPageJson?loadpage=true';
            cxObj = data.field;
            //合肥需求，查封文号中文括号转为英文括号
            if (isNotBlank(cxObj.cfwh)) {
                cxObj.cfwh = cxObj.cfwh.replace('（', '(');
                cxObj.cfwh = cxObj.cfwh.replace('）', ')');
            }
            cxObj.bdcdyh =deleteWhitespace(cxObj.bdcdyh);
            cxObj.qllx=bdcslxztzpz.qllx;
            cxObj.zdtzm=bdcslxztzpz.zdtzm;
            cxObj.dzwtzm=bdcslxztzpz.dzwtzm;
            cxObj.bdcdyfwlx=bdcslxztzpz.bdcdyfwlx;
            cxObj.dyhcxlx=bdcslxztzpz.dyhcxlx;
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
                //无数据时显示内容调整
                if($('.layui-show .layui-table-body>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        };

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(cfList)', function (obj) {
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

        /**
         * 监听排序事件
         */
        table.on('sort(cfList)', function (obj) {
            addModel();
            //查询时清除缓存数据
            sessionStorage.checkedData= '{}';
            sessionStorage.allData= '{}';
            isSelectAll =false;
            cxObj.sort=obj.field + "," + obj.type;
            table.reload('qlid', {
                initSort: obj
                , where: cxObj
            });
        });

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
            if (layEvent == "addCfShoppingCar") { //添加到购物车
                var checkedData = JSON.parse(sessionStorage.checkedData);
                if ($.isEmptyObject(checkedData)) {
                    layer.alert("未选择数据!");
                }
                checkData =[];
                $.each(checkedData, function (key, value) {
                    checkData.push(value);
                });
                if (checkData != null && checkData.length > 0) {
                    addModel();
                    sfcj =false;
                    yzCfXnBdcdyh(checkData,"",false);
                }else{
                    layer.alert("未选择数据!");
                }
            }else if(layEvent ==="allchooseCf"){ //全选
                var url = getContextPath() + '/bdcdyh/listCfByPageJson?loadpage=false';
                addModel();
                if(cxObj.bdcdyh !== "" || cxObj.cfwh !== "" || cxObj.ywrmc !== "" || cxObj.cfjg !== "" || cxObj.slbh !== "" || cxObj.ycqzh !== "" || cxObj.zl !== "") {
                    bdcdyhSelectAllFunction(cxObj, url, 'qlid', 'xmid');
                } else {
                    removeModal();
                    showAlertDialog("请先输入查询条件再全选")
                }

            }else if(layEvent ==="allunchooseCf"){//反选
                var url = getContextPath() + '/bdcdyh/listCfByPageJson?loadpage=false';
                addModel();
                if(cxObj.bdcdyh !== "" || cxObj.cfwh !== "" || cxObj.ywrmc !== "" || cxObj.cfjg !== "" || cxObj.slbh !== "" || cxObj.ycqzh !== "" || cxObj.zl !== "") {
                    bdcdyhInverseSelectFunction(cxObj, url, 'qlid', 'xmid');
                }  else {
                    removeModal();
                    showAlertDialog("请先输入查询条件再反选")
                }
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
                        yzCfXnBdcdyh(checkData,1,true);
                    }else{
                        sfcj =false;
                        yzCfXnBdcdyh(checkData,1,false);
                    }

                }
            }else if(obj.event === 'lpb'){
                var lpbData = obj.data;//可以获取当前行的数据
                //获取逻辑幢主键
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

function checkCf(checkData,sfcj) {
    var selectDataList = [];
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.cfwh =selectData.cfwh;
        bdcGzYzsjDTO.yxmid =selectData.xmid;
        bdcGzYzsjDTO.ysfwbm = selectData.ysfwbm;
        bdcGzYzsjDTO.qjgldm = selectData.qjgldm;
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
        bdcSlYwxxDTO.qjgldm = selectData.qjgldm;
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
                if (processDefKey !== "" && jbxxid !== "") {
                    cshSelectedXxSingle(jbxxid,processDefKey, false, true, 3);
                }
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
            bdcSlYwxxDTO.ybdcqz = selectData.cfwh;
            bdcSlYwxxDTO.xmid = selectData.xmid;
            bdcSlYwxxDTO.qjgldm = selectData.qjgldm;
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
                showConfirmDialog("提示","所选不动产单元存在虚拟不动产单元号，是否需要匹配","showMatchData","'"+JSON.stringify(data)+"','"+3+"'","checkSfcjCf","");

            } else{
                checkCf(checkData,sfcj);
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
                checkCf(checkData,sfcj);
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
    yzCfXnBdcdyh(checkData,1,sfcj);
};



