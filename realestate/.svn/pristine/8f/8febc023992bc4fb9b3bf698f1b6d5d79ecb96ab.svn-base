var type = $.getUrlParam("type");

function access() {
    if (checkeddata && checkeddata.length > 0) {
        plsb(checkeddata);
    } else {
        layer.alert("请选择一条数据进行操作")
    }
}

function getBw() {
    if (checkeddata && checkeddata.length > 0) {
        plGetResponse(checkeddata)
    } else {
        layer.alert("请选择一条数据进行操作")
    }
}

function loadComplete() {
    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        if ($(getTd[i]).attr('data-field') == "XMID") {
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            if (isNotBlank(getTdCell[0].innerText)) {
                $.ajax({
                    url: "/realestate-inquiry-ui/accessLog/queryxyxx/" + getTdCell[0].innerText,
                    data: {
                        logType: type
                    },
                    type: "get",
                    async: false,
                    success: function (data) {
                        if (isNotBlank(data)) {
                            var tdList = getTdCell.parent().parent().children();
                            for (var j = 0; j < tdList.length; j++) {
                                if ($(tdList[j]).attr('data-field') == "XYXX") {
                                    var tdCell = $(tdList[j]).find('.layui-table-cell');
                                    tdCell[0].innerHTML = data;
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}
function ywbw(obj, data) {
    if (data.XMID) {
        $.ajax({
            url: "/realestate-inquiry-ui/accessLog/queryJrbw",
            data: {
                ywh: data.XMID,
                logType: type
            },
            success: function (data) {
                if (isNotBlank(data)) {
                    layer.open({
                        title: "响应报文",
                        area: ['960px', '450px'],
                        content: '<xmp style="white-space: pre-wrap">' + data.replace(/\\n/g, "\n") + '</xmp>'
                    });
                } else {
                    layer.msg("无业务报文")
                }
            }
        });
    } else {
        layer.msg("项目主键为空")
    }
}

function xybw(obj, data) {
    if (data.XMID) {
        $.ajax({
            url: "/realestate-inquiry-ui/accessLog/queryXybw",
            data: {
                ywh: data.XMID,
                logType: type
            },
            success: function (data) {

                layer.open({
                    title: "响应报文",
                    area: ['960px', '450px'],
                    content: '<xmp style="white-space: pre-wrap">' + data.replace(/\\n/g, "\n") + '</xmp>'
                });
            }
        });
    } else {
        layer.msg("项目主键为空")
    }
}

function sjjy(obj, data) {
    if (data.XMID) {
        $.ajax({
            url: "/realestate-inquiry-ui/accessLog/checkDataByXmid",
            dataType: "json",
            data: {
                xmid: data.XMID
            },
            success: function (data) {
                if (data) {
                    layer.msg("校验成功");
                } else {
                    layer.msg("校验失败");
                }
            }
        });
    } else {
        layer.msg("项目主键为空");
    }
}

function plsb(data) {
    var xmidList = [];
    for (var i = 0; i < data.length; i++) {
        xmidList.push(data[i].XMID);
    }
    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/accessLog/xmidlist",
        dataType: "json",
        data: "xmidList=" + encodeURI(xmidList),
        success: function (data) {
            layer.msg("请求结束");
            removeModal();
            layui.table.reload('pageTable', {page: {curr: 1}});
        },
        error: function (xhr, status, error) {
            layer.msg("上报失败!");
            removeModal()
        }
    });
}

function plGetResponse(data) {
    var xmidList = [];
    var wsbSlbhList = [];
    var cgSlbhList = [];
    for (var i = 0; i < data.length; i++) {
        if (data[i].YWBWID == null
            || data[i].CGBS == '-1'
            || data[i].CGBS == null) {
            wsbSlbhList.push(data[i].SLBH);
        } else if (data[i].CGBS == '1') {
            cgSlbhList.push(data[i].SLBH);
        } else {
            xmidList.push(data[i].XMID);
        }
    }

    // 未上报的受理编号
    if (wsbSlbhList.length > 0) {
        layer.alert("选中记录存在未上报记录，受理编号：" + wsbSlbhList);
        return;
    }
    // 成功上报的受理编号
    if (cgSlbhList.length > 0) {
        layer.alert("选中记录存在已成功上报记录，受理编号：" + cgSlbhList);
        return;
    }
    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/accessLog/getAccessResponse",
        data: "logType=" + type + "&xmidList=" + xmidList,
        success: function (data) {
            layer.msg(data);
            removeModal();
            layui.table.reload('pageTable', {page: {curr: 1}});
        },
        error: function (xhr, status, error) {
            layer.msg("获取失败!");
            removeModal()
        }
    });
}
