var qllx;
var pptdxmid;
var firstload = true;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var element = layui.element;
    var laytpl = layui.laytpl;
    element.init();
    loadData();
});

function loadData() {
    $.ajax({
        url: getContextPath() + "/matchData/pz?lx=tdz&fwxmid=" + xnxmid,
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            qllx = data.qllx;
            pptdxmid = data.pptdxmid;

        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    initXmTable();
    renderSearchInput();
    $("#queryXm").click();
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
                return '<span class="layui-btn layui-btn-xs bdc-major-btn"  onclick="checkMatchData(' + "'" + d.bdcdyh + "'" + ",'" + d.xmid + "'" + ",'YZPPTDZ'" + ')">匹配</span>'

            }
            }
        ];

        //提交表单
        form.on("submit(queryXm)", function (data) {
            addModel();
            var tdzzl = $("#tdzzl");
            var url = getContextPath() + '/bdcdyh/listXmByPageJson?loadpage=true';
            var cxObj = data.field;
            cxObj.qllx = qllx;
            cxObj.bdclx = "1";
            if (isNotBlank(pptdxmid)) {
                if (cxObj.zl === "" && firstload) {
                    tdzzl.val(bdctdzzl);
                }
                cxObj.xmid = pptdxmid;
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

function matchTdz(tdxmid) {
    $.ajax({
        url: getContextPath() + "/matchData/matchTdz/nt?fwxmid=" + xnxmid + "&tdxmid=" + tdxmid + "&jbxxid=" + jbxxid,
        type: 'POST',
        async: false,
        contentType: "application/json",
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("匹配成功");
            pptdxmid = tdxmid;
            $("#queryXm").click();

        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

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
    getReturnData("/matchData/rematch", {fwcqxmid: xnxmid, tdxmid: tdxmid, jbxxid: jbxxid}, "GET", function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("匹配成功");
        pptdxmid = tdxmid;
        $("#queryXm").click();
    }, function (err) {
        removeModal();
        throw error = new Error(err);
    })
}