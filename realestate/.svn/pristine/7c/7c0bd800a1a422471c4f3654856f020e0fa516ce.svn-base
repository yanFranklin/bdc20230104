layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var element = layui.element;
    element.init();
    var xnxmid = getQueryString("xnxmid");
    //如果xnxmid有值,获取最新的单元号
    if(isNotBlank(xnxmid)) {
        getReturnData("/slym/xm/xx", {xmid: xnxmid}, 'GET', function (data) {
            xnbdcdyh =data.bdcdyh;
        },function () {
            
        },false);
    }
    if (isNotBlank(xnbdcdyh)) {
        var tzm = xnbdcdyh.substring(19, 20);
        if (tzm == "W") {
            $($("#dyhcxlx option")[0]).attr("selected", true);
        } else if (tzm == "F") {
            $($("#dyhcxlx option")[1]).attr("selected", true);
        }
    }
    loadData(xnxmid);
});
var zdList;
var xmid;
var ppbdcdyh;
var firstload = true;
var processDefKey = getProcessDefKey();

function getProcessDefKey(){
    var reg = new RegExp("(^|&)processDefKey=([^&]*)(&|$)");
    var r = parent.parent.window.location.search.substr(1).match(reg);
    if (null !== r) {
        if (decodeURI(r[2]) !== "null") {
            return decodeURI(r[2]);
        } else {
            return null
        }
    }
    return null;
}

function loadData(xnxmid) {
    $.ajax({
        url: getContextPath() + "/matchData/pz?lx=bdcdyh&xnxmid=" + xnxmid,
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            ppbdcdyh = data.ppbdcdyh;

        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    initBdcdyhTable();
    renderSearchInput();
    $("#queryBdcdyh").click();
}

function initBdcdyhTable() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;

        var isSearch = true;
        $(document).keydown(function (event) {
            if (event.keyCode == 13) { //绑定回车
                if (isSearch) {
                    $("#queryBdcdyh").click();
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
            {field: 'bdcdyh', title: '不动产单元号', width: "30%"},
            {field: 'zl', title: '坐落', width: "30%"},
            {field: 'qlr', title: '权利人', width: "15%"},
            {field: 'zt', title: '状态', width: "15%", templet: '#bdcdyzt', align: "center"},
            {
                field: 'cz', title: '操作', width: "10%", fixed: "right", templet: function (d) {
                    if (isNotBlank(ppbdcdyh) &&ppbdcdyh==d.bdcdyh) {
                        return '<span class="layui-btn layui-btn-danger layui-btn-xs bdc-secondary-btn"  onclick="checkMatchData(' + "'" + d.bdcdyh + "'" + ",''" + ",'YZQXPPDYH'" + ')">取消匹配</span>'
                    }
                    //正常的单元号且不存在匹配关系的不显示按钮
                    if (xnbdcdyh.substr(6, 6) !== "000000" && !isNotBlank(ppbdcdyh)) {
                        return '<span class="layui-btn layui-btn-xs bdc-major-btn"  onclick="checkMatchData(' + "'" + d.bdcdyh + "'" + ",''" + ",'YZPPDYH'" + ')">重新关联</span>'
                    } else {
                        return '<span class="layui-btn layui-btn-xs bdc-major-btn"  onclick="checkMatchData(' + "'" + d.bdcdyh + "'" + ",''" + ",'YZPPDYH'" + ')">匹配</span>'
                    }

                }
            }

        ];

        //提交表单
        form.on("submit(queryBdcdyh)", function (data) {
            addModel();
            var dyhzl = $("#bdcdyzl");
            var url = getContextPath() + '/bdcdyh/listBdcdyhByPageJson?loadpage=true';
            var cxObj = data.field;
            cxObj.zdtzm = "";
            cxObj.dzwtzm = getDzwtzm();
            cxObj.bdcdyfwlx = "";
            if (isNotBlank(ppbdcdyh)) {
                if(cxObj.zl === "" && firstload) {
                    dyhzl.val(bdcdyhzl);
                }
                if(isNotBlank(cxObj.bdcdyh)&& ppbdcdyh != cxObj.bdcdyh ){
                    // 用于控制已匹配的数据，通过其他条件搜索数据时哦，展示无数据。
                    cxObj.zl = ppbdcdyh;
                }
                cxObj.bdcdyh = ppbdcdyh;
                firstload = false;
            } else {
                if (cxObj.zl === "" && firstload) {
                    dyhzl.val(bdcdyhzl);
                    cxObj.zl = bdcdyhzl;
                    firstload = false;
                } else {
                    dyhzl.val(cxObj.zl);
                    firstload = false;
                }
            }
            tableReload('qjid', cxObj, url);
            return false;
        });

        var tableConfig = {
            id: 'qjid',
            toolbar: "#toolbarBdcdyh",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            done: function () {
                var reverseList = ['zl'];
                reverseTableCell(reverseList, 'qjid');
                //无数据时显示内容调整
                if ($('.layui-show .layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        };

        //加载表格
        loadDataTablbeByUrl('#bdcdyhList', tableConfig);
        //表格初始化
        table.init('bdcdyhList', tableConfig);
    })
}
function getDzwtzm(){
    var dzwtzm = xnbdcdyh.substring(19, 20);
    var match = false;
    $.ajax({
        url: getContextPath() + '/bdcdyh/tdppfw/'+processDefKey,
        type: "GET",
        async: false,
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            match = data;
        },
        error: function (err) {
            console.log(err);
        }
    });
    if(match){
        if($('#dyhcxlx').parent().find(".layui-this").text() == "土地"){
            dzwtzm = "W";
        }else if($('#dyhcxlx').parent().find(".layui-this").text() == "土地和房屋"){
            dzwtzm = "F";
        }
    }
    return dzwtzm;
}
function matchDyh(bdcdyh,gxbdcdyfwlx) {
    $.ajax({
        url: getContextPath() + "/matchData/matchDyh?lsdyh=" + xnbdcdyh + "&bdcdyh=" + bdcdyh,
        type: 'POST',
        async: false,
        contentType: "application/json",
        success: function (data) {
            if(isNotBlank(xztzlx)) {
                //刷新父页面
                window.parent.parent.loadSearch(bdcdyh, xnbdcdyh, "dismatch", xztzlx);
                removeModal();
            }
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("匹配成功");
            ppbdcdyh = bdcdyh;
            $("#queryBdcdyh").click();

        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

function dismatchDyh(bdcdyh) {
    $.ajax({
        url: getContextPath() + "/matchData/dismatchDyh?bdcdyh=" + bdcdyh + "&xnxmid=" + xnxmid,
        type: 'POST',
        async: false,
        contentType: "application/json",
        success: function (data) {
            if(isNotBlank(xztzlx)) {
                //刷新父页面
                window.parent.parent.loadSearch(bdcdyh, xnbdcdyh, "dismatch", xztzlx);
                removeModal();
            }
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("取消匹配成功");
            ppbdcdyh = "";
            $("#queryBdcdyh").click();

        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}











