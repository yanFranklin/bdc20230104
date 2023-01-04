var hbYxmxx = [];


var fwXmxxIndex = "";
var lszd = "";

var xmxxHbMainFwXmxxIndex = "";

//项目信息合并页面
function xmxxHb(data) {
    // 先弹变更基本信息的窗
    hbYxmxx = [];
    for (var i = 0; i < data.length; i++) {
        hbYxmxx.push(data[i].fwXmxxIndex);
    }
    $.ajax({
        url: "../check/xmxx/bdcdyh",
        dataType: "json",
        traditional: true,
        data: {
            indexList: hbYxmxx
        },
        success: function (data) {
            if (data.success) {
                // 打开合并页面
                layer.open({
                    type: 2,
                    title: "项目信息合并",
                    area: ['960px', '300px'],
                    content: "../xmxxbg/inithb?fwXmxxIndexList=" + encodeURI(hbYxmxx) + ""
                });
            } else {
                layer.msg(data.msg)
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });

}


function submitBgWithBgbh(bglx, postData, layerIndex) {
    layer.close(layerIndex);
    var bgbh = postData.bgbh;
    var bglx = bglx;
    if (bglx == "变更") {
        xmjbxxBgView(bgbh);
    }
    if (bglx == "灭失") {
        xmxxMs(bgbh)
    }
    if (bglx == "合并") {
        xmxxHbMain(bgbh)
    }
}

//项目信息合并提交
function xmxxHbMain(bgbh) {
    if (xmxxHbMainFwXmxxIndex) {

        addModel();
        $.ajax({
            url: "../xmxxbg/xmxxhb",
            traditional: true,
            data: {
                bgbh: bgbh,
                mainIndex: xmxxHbMainFwXmxxIndex,
                yXmxxIndexList: hbYxmxx
            },
            dataType: "json",
            async: false,
            success: function (data) {
                removeModal();
                layer.msg(data.msg);
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this);
            }
        });


    }
}

//项目信息合并转变更基本信息页面
function xmxxHbSetMainIndex(fwXmxxIndex, zl, layerIndex) {
    xmxxHbMainFwXmxxIndex = fwXmxxIndex;
    var bglx = "合并";
    layer.close(layerIndex);
    if (!zl || zl == 'null') {
        zl = '';
    }
    layer.open({
        type: 2,
        title: "项目信息合并基本信息",
        area: ['100%', '100%'],
        content: "../bgxx/infobgxx?zl=" + zl + "&bglx=" + encodeURI(bglx)
        , end: function (index, layero) {
            $("#query").click();
            return false;
        }
    });

}

//项目内多幢变更页面
function xmjbxxBgView(bgbh) {
    var fwIndexList = [];
    fwIndexList.push(fwXmxxIndex);
    $.ajax({
        url: "../check/xmxx/bdcdyh",
        dataType: "json",
        traditional: true,
        data: {
            indexList: fwIndexList
        },
        success: function (data) {
            if (data.success) {
                // 打开变更页面
                layer.open({
                    type: 2,
                    title: "项目信息变更",
                    area: ['100%', '100%'],
                    content: "../xmxxbg/jbxxbgview?fwXmxxIndex=" + fwXmxxIndex + "&bgbh=" + bgbh + "&lszd=" + lszd
                    , end: function (index, layero) {
                        $("#query").click();
                        return false;
                    }
                });
            } else {
                layer.msg(data.msg)
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });

}

//项目内多幢变更操作
function xmxxJbxxBg(data, bgbh) {
    var postData = data.field;
    postData.bgbh = bgbh;
    var index = parent.layer.getFrameIndex(window.name);

    var fwIndexList = [];
    fwIndexList.push(postData.fwXmxxIndex);

    addModel();
    $.ajax({
        url: "../xmxxbg/xmjbxxbg",
        dataType: "json",
        data: postData,
        success: function (data) {
            removeModal();
            if (data && data.success) {
                parent.layer.msg(data.msg);
                parent.layer.close(index);
            } else {
                layer.alert("变更失败");
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });


    return false;
}

//进入项目内多幢变更    变更信息页面
function xmxxBg(data) {
    var bglx = "变更";
    var zl = '';
    if (data.zl) {
        zl = data.zl;
    }
    fwXmxxIndex = data.fwXmxxIndex;
    lszd = data.lszd;
    // 打开合并页面
    var fwIndexList = [];
    fwIndexList.push(data.fwXmxxIndex);

    $.ajax({
        url: "../check/xmxx/bdcdyh",
        dataType: "json",
        traditional: true,
        data: {
            indexList: fwIndexList
        },
        success: function (data) {
            if (data.success) {
                // 打开拆分页面
                layer.open({
                    type: 2,
                    title: "项目内多幢变更",
                    area: ['100%', '100%'],
                    content: "../bgxx/infobgxx?zl=" + zl + "&bglx=" + encodeURI(bglx)
                    , end: function (index, layero) {
                        $("#query").click();
                        return false;
                    }
                });
            } else {
                layer.msg(data.msg)
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });

}


//进入项目内多幢灭失    变更信息页面
function xmxxMs(bgbh) {
    var index = parent.layer.getFrameIndex(window.name);

    addModel();
    $.ajax({
        url: "../xmxxbg/ms",
        dataType: "json",
        data: {
            yFwXmxxIndex: fwXmxxIndex,
            bgbh: bgbh
        },
        success: function (data) {
            removeModal();
            if (data && data.success) {
                parent.layer.msg(data.msg);
                parent.layer.close(index);
            } else {
                layer.alert(data.msg)
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });


}


//进入项目内多幢灭失    变更信息页面
function xmxxMsView(data) {
    var bglx = "灭失";
    var zl = '';
    if (data.zl) {
        zl = data.zl;
    }
    fwXmxxIndex = data.fwXmxxIndex;
    // 打开合并页面

    var fwIndexList = [];
    fwIndexList.push(data.fwXmxxIndex);

    $.ajax({
        url: "../check/xmxx/bdcdyh",
        dataType: "json",
        traditional: true,
        data: {
            indexList: fwIndexList
        },
        success: function (data) {
            if (data.success) {
                layer.open({
                    type: 2,
                    title: "项目内多幢灭失",
                    area: ['100%', '100%'],
                    content: "../bgxx/infobgxx?zl=" + zl + "&bglx=" + encodeURI(bglx)
                    , end: function (index, layero) {
                        $("#query").click();
                        return false;
                    }
                });
            } else {
                layer.msg(data.msg)
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });

}


