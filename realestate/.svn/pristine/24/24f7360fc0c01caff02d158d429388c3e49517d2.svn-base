var processDefKey;
/**
 * 选择的登记流程对象
 */
var processs;
/**
 * 错误信息提示框
 */
var warnLayer;
/**
 * 需要被初始化的信息
 */
var checkData = [];
// 下拉框选择的工作流定义 id
var gzldyid;
// 选择台账类型
var xztzlx;
var scxnbdcdyh = false;
// 显示生成虚拟不动产单元号的特殊流程
var xnbdcdyhlckeys = [];
var xxbllcyz = false;
var xxblcqzhbt = true;
//信息补录参数集合
var plXxblParamsList = [];
var cqxzbdcdyh;
var sessionKey = "xxbl_" + Date.now();
layui.use(['jquery', 'selectN', 'form', 'layer', 'laytpl'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        laytpl = layui.laytpl,
        selectN = layui.selectN,
        form = layui.form;
    $(function () {
        processDefKey = $.getUrlParam('processDefKey');

        // 渲染登记流程的联动下拉框
        listCategoryProcess();

        //判断是否走信息补录的流程验证还是走选择的流程的验证规则
        getYzfs();
        // 是否显示虚拟不动产单元号
        queryScXnbdcdyh();

        // 渲染提示信息
        generate();

        //规则验证不通过的数据，点击忽略后加入到缓存中
        setAddHlxxToSession(true);
        // 生成虚拟不动产单元号
        form.on("submit(xnbdcdyh)", function () {
            cleanSessionStorageData();
            cxreset();
            gzldyid = $("#lcxl_select").val();
            rendSelect();
            layer.open({
                title: '虚拟不动产单元号',
                type: 1,
                area: ['960px', '500px'],
                btn: ['添加', '取消'],
                content: $('#bdc-popup-large'),
                yes: function (index, layero) {
                    //  单位代码（6位）+ 000000 （地籍区） + 土地所有权类型 + 宗地特征码 + 00000 （宗地宗海顺序号） + F/W + 0000000X（X 表示顺序号）
                    var dwdm = $("#dwdm").val();
                    var zdtzm = $("#zdtzm").val();
                    var dzwtzm = $("#dzwtzm").val();
                    var xnhqjgldm = $("#xnhqjgldm").val();
                    $("#qjgldm").val(xnhqjgldm);
                    var bdcdyhPrefix = dwdm + "000000" + zdtzm + "00000" + dzwtzm;
                    document.getElementsByName("bdcdyh")[0].value = bdcdyhPrefix;
                    //添加数据到缓存中
                    var bdcBlxxDTO = {};
                    bdcBlxxDTO["bdcdyh"] = bdcdyhPrefix;
                    bdcBlxxDTO["qjgldm"] = xnhqjgldm;
                    bdcBlxxDTO["ptgzldyid"] = processDefKey;
                    bdcBlxxDTO["gzldyid"] =$("#lcxl_select" ).val();
                    bdcBlxxDTO["lcmc"] = $("#lcxl_select option:selected " ).text();
                    if (choosebdcdyh.indexOf($("#lcdl_select option:selected " ).text()) != -1) {
                        bdcBlxxDTO["qlsjly"] = "1";
                    }
                    bdcBlxxDTO["blshlx"] = -1;
                    var xnbdcdyhList = [];
                    xnbdcdyhList.push(bdcBlxxDTO);
                    sessionStorage.setItem(sessionKey,JSON.stringify(xnbdcdyhList));

                    layer.closeAll();
                }
            });
        });

        $('#reset').on('click', function () {
            $("#lcxl_select").empty();
        });
        // 选择不动产单元号
        form.on("submit(bdcdyh)", function () {
            cleanSessionStorageData();
            cxreset();
            if (!isNullOrEmpty($("#lcxl_select").val()) && !isNullOrEmpty($("#lcxl_select option:selected " ).text())) {
                // 未重新选择流程
                if (gzldyid == $("#lcxl_select").val()) {
                    //checkIsOpen("1", '../xxbl/xxbl_selectbdcdyh.html?gzldyid=' + gzldyid);
                    checkIsOpen("1",'loadBdcdyhTable',gzldyid)
                } else {
                    // 重新选择了流程
                    gzldyid = $("#lcxl_select").val();
                    // 判断该流程是否可以选择不动产单元号
                    $.ajax({
                        url: getContextPath() + "/rest/v1.0/blxx/blxz/xztzpz",
                        type: 'GET',
                        async: false,
                        data: {gzldyid: gzldyid},
                        success: function (data) {
                            if (!isNullOrEmpty(data)) {
                                xztzlx = data.xztzlx;
                                //checkIsOpen("1", '../xxbl/xxbl_selectbdcdyh.html?gzldyid=' + gzldyid);
                                checkIsOpen("1", 'loadBdcdyhTable',gzldyid);

                            } else {
                                warnMsg("选择台账未配置，请检查！")
                            }
                        }, error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }
            } else {
                warnMsg("请选择需要办理的流程！");
            }
        });

        // 选择不动产权
        form.on("submit(bdcq)", function () {
            cleanSessionStorageData();
            cxreset();
            if (!isNullOrEmpty($("#lcxl_select").val()) && !isNullOrEmpty($("#lcxl_select option:selected " ).text())) {
                // 未重新选择流程
                if (gzldyid == $("#lcxl_select").val()) {
                    //checkIsOpen("2", '../xxbl/xxbl_selectbdcq.html?gzldyid=' + gzldyid);
                    checkIsOpen("2",'loadCqTable',gzldyid);

                } else {
                    // 重新选择了流程
                    gzldyid = $("#lcxl_select").val();
                    $.ajax({
                        url: getContextPath() + "/rest/v1.0/blxx/blxz/xztzpz",
                        type: 'GET',
                        async: false,
                        data: {gzldyid: gzldyid},
                        success: function (data) {
                            if (!isNullOrEmpty(data)) {
                                xztzlx = data.xztzlx;
                                //checkIsOpen("2", '../xxbl/xxbl_selectbdcq.html?gzldyid=' + gzldyid);
                                checkIsOpen("2",'loadCqTable',gzldyid);

                            } else {
                                warnMsg("选择台账未配置，请检查！")
                            }
                        }, error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }

            } else {
                warnMsg("请选择需要办理的流程！");
            }
        });

        // 选择查封
        form.on("submit(cfwh)", function () {
            cleanSessionStorageData();
            cxreset();
            if (!isNullOrEmpty($("#lcxl_select").val()) && !isNullOrEmpty($("#lcxl_select option:selected " ).text())) {
                // 未重新选择流程
                if (gzldyid == $("#lcxl_select").val()) {
                    //checkIsOpen("3", '../xxbl/xxbl_selectcf.html?gzldyid=' + gzldyid);
                    checkIsOpen("3",'loadCfTable',gzldyid);

                } else {
                    // 重新选择了流程
                    gzldyid = $("#lcxl_select").val();
                    $.ajax({
                        url: getContextPath() + "/rest/v1.0/blxx/blxz/xztzpz",
                        type: 'GET',
                        async: false,
                        data: {gzldyid: gzldyid},
                        success: function (data) {
                            if (!isNullOrEmpty(data)) {
                                xztzlx = data.xztzlx;
                                //checkIsOpen("3", '../xxbl/xxbl_selectcf.html?gzldyid=' + gzldyid);
                                checkIsOpen("3",'loadCfTable',gzldyid);

                            } else {
                                warnMsg("选择台账未配置，请检查！")
                            }
                        }, error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }

            } else {
                warnMsg("请选择需要办理的流程！");
            }
        });


        // 确认初始化数据
        form.on("submit(csh)", function () {
            var ycqzh = $("#ycqzh").val();
            var bdcdyh = $("#bdcdyh").val();
            /*if (!isNullOrEmpty(processs.lastValue) && !isNullOrEmpty(processs.lastName)) {
            }*/
            if (!isNullOrEmpty($("#lcxl_select").val()) && !isNullOrEmpty($("#lcxl_select option:selected " ).text())) {
                if (!isNullOrEmpty(ycqzh) || !isNullOrEmpty(bdcdyh)) {
                    var Index = layer.open({
                        type: 1,
                        skin: 'bdc-small-tips',
                        title: '新增数据',
                        area: ['320px'],
                        content: '是否确认新增数据？',
                        btn: ['确认', '取消'],
                        btnAlign: 'c',
                        yes: function () {
                            addModel();
                            // 如果有选择原产权证号说明存在现势产权，而不是选择的不动产单元号
                            confirmCsh(checkData);
                            layer.close(Index);
                        },
                        btn2: function () {
                            //取消
                            layer.close(Index);
                        }
                    });
                } else {
                    layer.msg("原产权证号或单元号至少输入一个条件！");
                }
            } else {
                layer.msg("请选择需要办理的流程！");
            }
        });

        //重置操作
        $("#reset").on('click', function () {
            $('.xzbdcdyh').hide();
            $('.xzcq').hide();
            $('.xzcf').hide();
        });
    });

    /**
     * 渲染 单位代码 和 宗地特征码
     */
    function rendSelect() {
        zdList = getZdList();

        // 单位代码
        $("#dwdm").empty();
        // 渲染下拉框
        $('#dwdm').append(new Option("请输入", ""));
        $.each(zdList.qx, function (index, item) {
            $('#dwdm').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
        });
        if(isNotBlank(gzldyid)){
            xnhLoadQjgldm(gzldyid);
        }
        // 宗地特征码
        $("#zdtzm").empty();
        // 渲染下拉框
        $('#zdtzm').append(new Option("请输入", ""));
        $.each(zdList.zdzhtzm, function (index, item) {
            $('#zdtzm').append(new Option(item.DM, item.DM));// 下拉菜单里添加元素
        });
        form.render("select");
    }


    /**
     * 0. 是否生成虚拟不动产单元号
     */
    function queryScXnbdcdyh() {
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/blxx/blxz/xnbdcdyh",
            async: false,
            success: function (data) {
                if (data.scxnbdcdyh === true) {
                    scxnbdcdyh = true;
                    $('#xnbdcdyh').removeClass('bdc-hide');
                    // 渲染虚拟不动产单元号的部分下拉框
                    rendSelect();
                } else {
                    xnbdcdyhlckeys = data.tslckeys;
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }

    /*
    * 查询信息补录相关配置
    * */
    function queryXxblPz(gzldyid) {
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/blxx/blxz/config",
            async: false,
            data: {gzldyid: gzldyid},
            success: function (data) {
                xxblcqzhbt = data.cqzhbt;
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断验证方式是否走信息补录的验证
     * @date : 2022/4/20 16:00
     */
    function getYzfs() {
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/blxx/xxblyz",
            success: function (data) {
                xxbllcyz = data;
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }



});

/**
 * 2. 确认是否初始化
 * @param postData 页面填写的通过验证的数据
 */
function confirmCsh(postData) {
    csh();
}

/**
 * 3. 初始化填写的信息
 * @param postData 页面填写的通过验证的数据
 */
function csh(postData) {
    var bdcqzh = $("#bdcqzh").val();
    var sessionData =  JSON.parse(sessionStorage.getItem(sessionKey));

    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/blxx/bllc/plXxblcsh",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(sessionData),
        async: true,
        success: function (data) {
            if (isNullOrEmpty(data)) {
                removeModel();
                warnMsg("创建数据失败！");
            } else {
                // 将 bdcqzh 保存在 session 中
                sessionStorage.setItem("xxbl" + data.processInstanceId, bdcqzh);
                // 跳转到 portal 页面
                parent.window.location.href =  "/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId;
            }
        },
        error: function (xhr, status, error) {
            removeModel();
            delAjaxErrorMsg(xhr);
        }
    });
}

//关闭 panel (新增申请人弹窗关闭方法)
function cancelEdit() {
    layui.use(['layer'], function () {
        var layer = layui.layer;
        layer.closeAll();
    });
}

/**
 * 产权选择不动产单元号验证
 * @param bdcdyh
 */
function cqXzBdcdyhYz(bdcdyh, _fn, _errorFn) {
    addModel();
    var selectDataList = [];
    if(plXxblParamsList.length == 0){
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = bdcdyh;
        selectDataList.push(bdcGzYzsjDTO);
    }else{
        for(var i = 0; i<plXxblParamsList.length;i++) {
            var bdcGzYzsjDTO = {};
            bdcGzYzsjDTO.bdcdyh = plXxblParamsList[i].bdcdyh;
            selectDataList.push(bdcGzYzsjDTO);
        }
    }
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = "blcqxzbdcdyh";
    bdcGzYzQO.paramList = selectDataList;
    $.ajax({
        url: getContextPath() + '/rest/v1.0/blxx/bdcGzyz',
        type: 'POST',
        async: false,
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcGzYzQO),
        success: function (data) {
            removeModel();
            _fn.call(this, data, data);
        }, error: function (xhr, status, error) {
            removeModel();
            delAjaxErrorMsg(xhr);
            _errorFn.call(this);
        }
    });
}

var choosebdcdyh = ["转移登记","更正登记"];
/**
 * 判断是否可以打开选择不动产单元台账
 */
function checkIsOpen(tzlx, content,gzldyid) {
    if (choosebdcdyh.indexOf($("#lcdl_select option:selected " ).text()) != -1 || xztzlx.indexOf(tzlx) != -1) {
        if(content == "loadBdcdyhTable"){
            cqxzbdcdyh = "cq";
            loadBdcdyhTable(gzldyid);
        }else if(content == "loadCfTable"){
            cqxzbdcdyh = "";
            loadCfTable(gzldyid);
        }else if(content == "loadCqTable"){
            cqxzbdcdyh = "";
            loadCqTable(gzldyid);
        }
    } else if (tzlx === "1") {
        warnMsg("此流程不可以选择不动产单元！");
    } else if (tzlx === "2") {
        warnMsg("此流程不可以选择不动产权证");
    } else if (tzlx === "3") {
        warnMsg("此流程不可以选择查封");
    } else {
        warnMsg("选择台账未配置，请手动输入信息！");
    }
}





// 房屋类型字典项
var zdbdcdyfwlxList = [{"DM": "xmxx", "MC": "项目内多幢"}, {"DM": "ljz", "MC": "逻辑幢"}, {
    "DM": "hs",
    "MC": "实测户室"
}, {"DM": "ychs", "MC": "预测户室"}];
/**
 * 渲染页面的全部下拉框
 */
// 字典不动产类型
var zdBdclxList = [];
// 不动产类型
var bdclxList = [];
// 不动产房屋类型
var bdcdyfwlxList = [];
var reverseList = [];
reverseList.push('zl');
function renderSelect() {
    zdList = getZdList();
    zdBdclxList = zdList.bdclx;
    $('#selectedBdclx').empty();
    $('#bdcdyfwlx').empty();

    //渲染 bdclx 下拉框，bdclx 在选择台账配置表中未配置就默认显示 zd 项中的内容
    if (bdclxList.length === 0) {
        $('#selectedBdclx').append(new Option("请选择", ""));
    }
    $.each(zdBdclxList, function (index, item) {
        if (bdclxList.length === 0 || bdclxList.indexOf(item.DM + "") > -1) {
            $('#selectedBdclx').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
        }
    });
    //渲染bdcdyfwlx下拉框
    if (bdcdyfwlxList.length !== 1) {
        $('#bdcdyfwlx').append(new Option("请选择", ""));
    }
    $.each(zdbdcdyfwlxList, function (index, item) {
        if (bdcdyfwlxList.length === 0 || bdcdyfwlxList.indexOf(item.DM + "") > -1) {
            $('#bdcdyfwlx').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
        }
    });
    layui.form.render("select");
}


/**
 * 重新加载台账
 * @param table_id 表格 id
 * @param postData 查询参数
 * @param url   查询url
 */
function tableReload(table_id, postData, url) {
    layui.use(['table'], function () {
        var table = layui.table;
        addModel();
        table.reload(table_id, {
            url: url,
            where: postData,
            page: {
                //重新从第 1 页开始
                curr: 1,
                limits: [10, 50, 100, 200, 500]
            },
            done: function () {
                removeModel();
                //重新计算table高度
                $('.bdc-table-box').height( $(".bdc-container").height()-220)
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
                reverseTableCell(reverseList);
            }
        });
    });
}
/**
 * 加载选择台账配置，渲染下拉框
 * @param gzldyid 工作流定义 id
 */
function loadXztzpz(gzldyid) {
    $.ajax({
        url: getContextPath() + "/rest/v1.0/blxx/blxz/xztzpz",
        type: 'GET',
        async: false,
        data: {gzldyid: gzldyid},
        success: function (data) {
            if (isNullOrEmpty(data)) {
                warnMsg("选择台账未配置，请检查！");
            } else {
                var xztzlxList = [];
                if (!isNullOrEmpty(data.xztzlx)) {
                    xztzlxList = data.xztzlx.split(",");
                }
                if (!isNullOrEmpty(data.bdclx)) {
                    bdclxList = data.bdclx.split(",");
                }
                if (!isNullOrEmpty(data.bdcdyfwlx)) {
                    bdcdyfwlxList = data.bdcdyfwlx.split(",");
                }
                data.xztzlxList = xztzlxList;
                bdcslxztzpz = data;
                if(bdcslxztzpz.qjgldmList &&bdcslxztzpz.qjgldmList.length >0){
                    $(".qjgldm").show();
                    $("#dyh-qjgldm").empty();
                    $.each(bdcslxztzpz.qjgldmList, function (index, item) {
                        $('#dyh-qjgldm').append(new Option(item.mc, item.dm));// 下拉菜单里添加元素
                        $(".bdc-button-box-four").removeClass("bdc-button-box-four").addClass("bdc-button-box")
                    });
                }
                renderSelect();
            }
        }, error: function (xhr, status, error) {
            warnMsg("加载选择台账配置失败");
        }
    });
}

/**
 * 添加单元号调用规则验证
 */
function addDyhGzyz(){
    /*checkData.bdcdyh = $("#bdcdyh").val();
    checkData.bdcqzh = $("#bdcqzh").val();
    checkData.ycqzh = $("#ycqzh").val();
    checkData.cfwh = $("#cfwh").val();
    checkData.yxmid = $("#yxmid").val();
    var qjgldm =$("#qjgldm").val();
    if(!isNullOrEmpty(qjgldm)) {
        checkData.qjgldm = qjgldm;
    }
    var yqllx =$("#yqllx").val();
    if(!isNullOrEmpty(yqllx)) {
        checkData.yqllx = yqllx;
    }*/
    var bdcdyh = $("#bdcdyh").val();
    var ycqzh = $("#ycqzh").val();
    if (!isNullOrEmpty($("#lcxl_select").val()) && !isNullOrEmpty($("#lcxl_select option:selected " ).text())) {
        var bdcqzh = $('#bdcqzh').val();
        // 需要生成的虚拟不动产单元号跳过验证
        var flag = isXn(bdcdyh);
        if (flag === true) {
            return false;
        } else if (!isNullOrEmpty(ycqzh) || !isNullOrEmpty(bdcdyh)) {
            addModel();
            // 如果有选择原产权证号说明存在现势产权，而不是选择的不动产单元号
            if (choosebdcdyh.indexOf($("#lcdl_select").val()) != -1 && isNullOrEmpty(checkData.ycqzh)) {
                cqXzBdcdyhYz(bdcdyh, function (data) {
                    if (data.length > 0) {
                        removeModel();
                        warnMsg(data[0].msg);

                    } else {
                        checkBdcdyh();
                    }
                }, function (err) {

                });
            } else {
                checkBdcdyh();
            }
        } else {
            layer.msg("请选择数据！");
        }
    }
}

/**
 * 1. 调用规则验证不动产单元号
 */
function checkBdcdyh() {
    // 去除 页面 中的空格
   /* checkData.bdcdyh = deleteWhitespace(checkData.bdcdyh);
    checkData.bdcqzh = deleteWhitespace(checkData.bdcqzh);
    checkData.cfwh = deleteWhitespace(checkData.cfwh);
    checkData.yxmid = deleteWhitespace(checkData.yxmid);*/
    // 组织验证数据
    var selectDataList = [];
    for(var i = 0; i<plXxblParamsList.length;i++){
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = plXxblParamsList[i].bdcdyh;
        bdcGzYzsjDTO.cfwh = plXxblParamsList[i].cfwh;
        bdcGzYzsjDTO.yxmid = plXxblParamsList[i].yxmid;
        bdcGzYzsjDTO.qjgldm = plXxblParamsList[i].qjgldm;
        selectDataList.push(bdcGzYzsjDTO);
    }
    var bdcGzYzQO = {};
    //读取配置，判断是走信息补录的规则验证还是走选择的流程的验证
    if (xxbllcyz) {
        bdcGzYzQO.zhbs = processDefKey + "_XXBL";
    } else {
        bdcGzYzQO.zhbs = $("#lcxl_select").val() + "_XZBDCDY";
    }
    bdcGzYzQO.paramList = selectDataList;
    $.ajax({
        url: getContextPath() + '/rest/v1.0/blxx/bdcGzyz',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcGzYzQO),
        success: function (data) {
            if (data.length > 0) {
                removeModel();
                var addData = dealYzResult(data);
                //正常验证通过的数据直接添加
                if (addData.length > 0) {
                    addsessionStorage(addData);
                } else {
                    removeModal();
                }
                loadTsxx(data);
                gzyztsxx();
            } else {
                addsessionStorage(plXxblParamsList);
                removeModel();
                //confirmCsh(checkData);
            }
        }, error: function (xhr, status, error) {
            removeModel();
            delAjaxErrorMsg(xhr);
        }
    });
}

/**
 * 判断是否是需要生成的虚拟不动产单元号
 * @param bdcdyh 不动产单元号
 * @return {boolean} true or false
 */
function isXn(bdcdyh) {
    var zdzhsxh = bdcdyh.substring(14, 19);
    var dzwtzm = bdcdyh.substring(19, 20);
    return zdzhsxh === "00000" && dzwtzm !== "H" && bdcdyh.length === 20;
}

//重置查询条件
function cxreset(){
    $(".cxtj input").val("");
}



//处理验证结果
function dealYzResult(yzResult) {
    var yzbdcdyhArr = [];
    //验证通过的数据
    var addData = [];
    //去掉验证通过后的数据
    var newcheckData = [];
    if (yzResult.length > 0) {
        //重新组织数据，验证通过放入addData直接添加，不通过的保留
        for (var i = 0; i < yzResult.length; i++) {
            if (isNotBlank(yzResult[i].bdcdyh)) {
                yzbdcdyhArr.push(yzResult[i].bdcdyh);
            }
        }
        for (var i = 0; i < plXxblParamsList.length; i++) {
            //验证通过放入addData
            if (yzbdcdyhArr.indexOf(plXxblParamsList[i].bdcdyh) < 0) {
                addData.push(plXxblParamsList[i]);
            } else {
                newcheckData.push(plXxblParamsList[i]);
            }
        }
        checkData = newcheckData;
    } else {
        addData = checkData;
    }
    return addData;
}
