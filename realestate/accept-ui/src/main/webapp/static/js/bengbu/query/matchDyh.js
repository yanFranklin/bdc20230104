layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var element = layui.element;
    element.init();
    var xnxmid = getQueryString("xnxmid");
    loadData(xnxmid);
});
var zdList;
var xmid;
var ppbdcdyh;
var firstload = true;
var showppbtn = true;

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
                    if(!showppbtn) {
                        return '<span></span>' ;
                    }
                    return '<span class="layui-btn layui-btn-xs bdc-major-btn"  onclick="checkMatchData(' + "'" + d.bdcdyh + "'" + ",''" + ",'YZPPDYH'" + ')">匹配</span>'

                }
            }

        ];

        //提交表单
        form.on("submit(queryBdcdyh)", function (data) {
            addModel();
            var dyhzl = $("#bdcdyzl");
            var url = getContextPath() + '/bdcdyh/listBdcdyhByPageJson?loadpage=true';
            var cxObj = data.field;
            cxObj.zdtzm = xnbdcdyh.substring(12, 14);
            cxObj.dzwtzm = xnbdcdyh.substring(19, 20);
            cxObj.bdcdyfwlx = "";
            if (isNotBlank(ppbdcdyh)) {
                //存在匹配单元号，只能用匹配单元号查询
                if(cxObj.zl === "") {
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
                    //第一次加载时判断是否虚拟号，虚拟号查询展示所有的单元号，非虚拟号查询当前单元号
                    var zdzhsxh = xnbdcdyh.substring(6,12);
                    var qslxdm = xnbdcdyh.substring(13,14);
                    if(zdzhsxh === "000000" && (qslxdm !== "G" || qslxdm !== "H")) {
                        //虚拟号根据bdcdywybh=fwbh去权籍找
                        dyhzl.val(bdcdyhzl);
                        cxObj.zl = bdcdyhzl;
                        firstload = false;
                        url = getContextPath() + '/bdcdyh/fwhs/page?fwbm='+ bdcdywybh;
                        tableReload('qjid', cxObj, url);
                        return false;
                    } else {
                        cxObj.bdcdyh = xnbdcdyh;
                    }
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

function matchDyh(bdcdyh,gxbdcdyfwlx) {
    $.ajax({
        url: getContextPath() + "/matchData/matchDyh?lsdyh=" + xnbdcdyh + "&bdcdyh=" + bdcdyh,
        type: 'POST',
        async: false,
        contentType: "application/json",
        success: function (data) {
            if(isNotBlank(xztzlx)) {
                //刷新父页面
                window.parent.parent.loadSearch(bdcdyh, xnbdcdyh, "match", xztzlx);
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











