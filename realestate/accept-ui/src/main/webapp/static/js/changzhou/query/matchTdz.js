var qllx;
var pptdxmid;
//不存在匹配关系，但与房产证单元号相同
var gltdxmid;
var firstload = true;
var table;
var qjgldm = getQueryString("qjgldm");
var fwxmid = getQueryString("fwxmid");
var xnxmid = getQueryString("xnxmid");
var bdctdzzl = getQueryString("zl");
var jbxxid = getQueryString("jbxxid");
var ppzt = getQueryString("ppzt");
var currentXmxx;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var element = layui.element;
    var laytpl = layui.laytpl;
    table = layui.table;
    element.init();
    loadData();
    if(isNullOrEmpty(ppzt) || (ppzt == 1)){
        $("#queryXm").click();
    }
});

function loadData() {
    //加载项目信息
    $.ajax({
        url: getContextPath() + "/slym/xm/listBdcXm",
        type: 'GET',
        data: {'xmid':xnxmid},
        dataType: 'json',
        async: false,
        success: function (data) {
            if (!isNullOrEmpty(data)){
                currentXmxx = data[0];
            }

        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    $.ajax({
        url: getContextPath() + "/matchData/pz?lx=tdz&fwxmid=" + xnxmid,
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            qllx = data.qllx;
            pptdxmid = data.pptdxmid;
            gltdxmid = data.gltdxmid;

        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    initXmTable();
    renderSearchInput();
}

function initXmTable() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;
        var isSearch = true;
        $(document).keydown(function (event) {
            if (event.keyCode == 13) { //绑定回车
                if (isSearch) {
                    $("#queryXm").click();
                }

            }
        });

        //判断回车操作
        $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });


        //不动产单元号的表头
        var unitTableTitle = [
            {field: 'bdcqzh', title: '不动产权证（明）号', width: "30%"},
            {field: 'zl', title: '坐落', width: "28%"},
            {field: 'qlrmc', title: '权利人', width: "12.2%"},
            {field: 'zt', title: '状态', width: "15%", templet: '#bdcdyzt', align: "center"},
            {
                field: 'cz', title: '操作', width: "15%", fixed: "right", templet: function (d) {
                    if (pptdxmid == d.xmid) {
                        return '<span class="layui-btn layui-btn-danger layui-btn-xs bdc-secondary-btn"  onclick="checkMatchData(' + "'" + d.bdcdyh + "'" + ",'" + d.xmid + "'" + ",'YZQXPPTDZ'" + ')">取消匹配</span>'
                    }
                    if (gltdxmid) {
                        return '<span class="layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn"  onclick="checkMatchData(' + "'" + d.bdcdyh + "'" + ",'" + gltdxmid + "'" + ",'YZJCGLTDZ'" + ')">解除关联</span>';
                    }
                    return '<span class="layui-btn layui-btn-xs bdc-major-btn"  onclick="checkMatchData(' + "'" + d.bdcdyh + "'" + ",'" + d.xmid + "'" + ",'YZPPTDZCZ'" + ",'" + d.qszt + "'" + ')">匹配</span>'

                }
            }
        ];

        //提交表单
        form.on("submit(queryXm)", function (data) {
            addModel();
            var tdzzl = $("#tdzzl");
            var url = getContextPath() + '/bdcdyh/listXmByPageJson?loadpage=true';
            var cxObj = data.field;
            cxObj.qjgldm = qjgldm;
            cxObj.qllx = qllx;
            // 增加不动产类型 纯土地，用于抵押权的是只查出纯土地的抵押数据
            cxObj.bdclx = "1";
            //不添加默认的权属状态查询条件
            cxObj.sftjmrqszt = 0;
            // 不动产单元号支持右模糊搜索
            // cxObj.bdcdyhmh = "3";
            if (isNotBlank(pptdxmid)) {
                //存在匹配土地证只能用tdxmid查询
                if (cxObj.zl === "") {
                    tdzzl.val(bdctdzzl);
                }
                cxObj.xmid = pptdxmid;
                firstload = false;
            } else if (isNotBlank(gltdxmid)) {
                cxObj.xmid = gltdxmid;
                firstload = false;
            } else {
                if (cxObj.zl === "" && firstload) {
                    tdzzl.val(bdctdzzl);
                    cxObj.zl = bdctdzzl;
                    firstload = false;
                } else {
                    tdzzl.val(cxObj.zl);
                    firstload = false;
                }
            }
            tableReload('xmid', cxObj, url);
            return false;
        });

        var tableConfig = {
            id: 'xmid',
            toolbar: "#toolbarXm",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            done: function () {
                var reverseList = ['zl'];
                reverseTableCell(reverseList, 'xmid');
                //无数据时显示内容调整
                if ($('.layui-show .layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        };

        //加载表格
        loadDataTablbeByUrl('#xmList', tableConfig);
        //表格初始化
        table.init('xmList', tableConfig);


    })
}


/*
标准吧匹配和取消匹配逻辑和南通版本保持一致
* */

function matchCzTdz(tdxmid,tdqszt) {
    //校验土地证和房产证的状态,土地现势，房产证历史是提示是否要更新土地证状态
    var confirmZxTdz = false;
    if(!isNullOrEmpty(currentXmxx) && (currentXmxx.qszt == 2) && (tdqszt==1)){
        confirmZxTdz = window.confirm("房产证、土地证状态不一致，是否更新土地证状态?");
    }

    $.ajax({
        url: getContextPath() + "/matchData/matchTdz/nt?fwxmid=" + xnxmid + "&tdxmid=" + tdxmid + "&jbxxid=" + jbxxid + "&confirmZxTdz=" + confirmZxTdz,
        type: 'POST',
        async: false,
        contentType: "application/json",
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("匹配成功");
            pptdxmid = tdxmid;
            $("#queryXm").click();

        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

/*
标准吧匹配和取消匹配逻辑和南通版本保持一致
* */
function dismatchTdz(tdxmid) {
    $.ajax({
        url: getContextPath() + "/matchData/dismatchTdz/nt?fwxmid=" + xnxmid + "&tdxmid=" + tdxmid + "&jbxxid=" + jbxxid,
        type: 'POST',
        dataType: 'json',
        async: false,
        contentType: "application/json",
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("取消匹配成功");
            pptdxmid = "";
            $("#queryXm").click();

        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

function checkTdz(elem, tdxmid) {
    addModel();
    getReturnData("/matchData/sfcjxm", {tdxmid: tdxmid}, "GET", function (data) {
        if (data !== 0) {
            removeModal();
            showAlertDialog("已做过不动产登记，无法重新匹配！")
        } else {
            reaMatch(tdxmid);
        }
    }, function (err) {
        removeModal();
        throw error = new Error(err);
    });
    remove(elem,5);
}

function reaMatch(tdxmid) {
    //校验土地证和房产证的状态,土地现势，房产证历史是提示是否要更新土地证状态
    var confirmZxTdz = false;
    if(!isNullOrEmpty(currentXmxx) && (currentXmxx.qszt == 2) && (tdqszt==1)){
        confirmZxTdz = window.confirm("房产证、土地证状态不一致，是否更新土地证状态?");
    }
    getReturnData("/matchData/rematch", {fwcqxmid: xnxmid, tdxmid: tdxmid, jbxxid: jbxxid, confirmZxTdz: confirmZxTdz}, "GET", function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("匹配成功");
        pptdxmid = tdxmid;
        $("#queryXm").click();
    }, function (err) {
        removeModal();
        throw error = new Error(err);
    })
}

//新增父页面调用 重置表格尺寸
function reloadCurTab() {
    table.reload('xmid', {
        done: function () {
            removeModal();
            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            reverseTableCell(reverseList, 'xmid');
            if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() === 0) {
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');

            } else {
                $('.bdc-form-div .layui-show .layui-table-main.layui-table-body').height($('.bdc-form-div .layui-show .bdc-table-box').height() - 131);
            }
        }
    });
}