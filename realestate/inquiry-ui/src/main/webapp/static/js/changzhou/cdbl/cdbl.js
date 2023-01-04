/**
 * 查档补录台账 js
 */

var xmid = getUrlParam("xmid");


$(".bdc-search-box").hide();
$(".bdc-percentage-container").css("padding-top", "0");

/**
 * 将父页面的查询条件传递进来进行查询
 */
var queryParam = {};

setTimeout(function () {
    if (xmid) {
        queryParam.xmid = xmid;
        // $.ajax({
        //     url: "/realestate-inquiry-ui/dtcx/get/cdbltz",
        //     type: "get",
        //     success: function (data) {
        //         if(!data || isNullOrEmpty(data.cxid)) {
        //             warnMsg("未配置qlrfc自定义查询，请联系管理员");
        //         } else {
        //             queryParam.cxdh = data.cxdh;
        //             queryParam.cxid = data.cxid;
        //             // tableReload('pageTable', {data: JSON.stringify(queryParam)}, dataUrl);
        //         }
        //     }
        // });
    }
}, 500);

/**
 * 档案调用
 */
function addblxx(obj, data) {
    console.info(data);
    var index = layer.open({
        type: 2,
        title: "补录信息",
        area: ['960px', '700px'],
        fixed: false, //不固定
        btnAlign: "c",
        // maxmin: true, //开启最大化最小化按钮
        content: "/realestate-accept-ui/view/cdym/blbd.html?xmid=" + xmid + "&type=add",
        cancel: function () {
            $.ajax({
                url: "/realestate-inquiry-ui/dtcx/get/cdbltz",
                type: "get",
                success: function (data) {
                    if (!data || isNullOrEmpty(data.cxid)) {
                        warnMsg("未配置qlrfc自定义查询，请联系管理员");
                    } else {
                        queryParam.cxdh = data.cxdh;
                        queryParam.cxid = data.cxid;
                        tableReload('pageTable', {data: JSON.stringify(queryParam)}, dataUrl);
                    }
                }
            });
        }
    });
    // layer.full(index);
}

//表格加载完操作
function loadComplete() {
    //翻转
    var reverseList = ['ZL'];
    reverseTableCell(reverseList);
}

function viewblxx(obj, data) {
    var index = layer.open({
        type: 2,
        title: "补录信息",
        area: ['960px', '700px'],
        fixed: false, //不固定
        btnAlign: "c",
        // maxmin: true, //开启最大化最小化按钮
        content: "/realestate-accept-ui/view/cdym/blbd.html?blxxid=" + data.BLXXID + "&type=view&xmid=" + xmid,
        cancel: function () {
            reloadTable();
        }
    });
}

function deleteblxx(obj, data) {
    layer.confirm('是否确认删除', {
        btn: ['是', '否']
    }, function (index) {
        $.ajax({
            url: "/realestate-accept-ui/cdlc/delete",
            type: "get",
            data: {blxxid: data.BLXXID},
            success: function (data) {
                reloadTable();
                layer.close(index);
            }
        });
    }, function (index) {
        layer.close(index);
    });
}

function reloadTable() {
    $.ajax({
        url: "/realestate-inquiry-ui/dtcx/get/cdbltz",
        type: "get",
        success: function (data) {
            if (!data || isNullOrEmpty(data.cxid)) {
                warnMsg("未配置qlrfc自定义查询，请联系管理员");
            } else {
                queryParam.cxdh = data.cxdh;
                queryParam.cxid = data.cxid;
                tableReload('pageTable', {data: JSON.stringify(queryParam)}, dataUrl);
            }
        }
    });
}
