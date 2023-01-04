/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 上报销账台账js
 * @date : 2022/6/21 8:59
 */

var form, $, table, layer;
layui.use(['jquery', 'formSelects', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    table = layui.table;
    layer = layui.layer;
});

/*
* 上报按钮操作
* */
debugger;
function sb(data) {

    addModel();
    if (checkeddata.length === 0) {
        removeModal();
        warnMsg("请先勾选需要上报的数据");
        return false;
    }

    // //获取配置信息
    // $.ajax({
    //     url: "/realestate-inquiry-ui/accessLog/pz",
    //     type: "POST",
    //     async: false,
    //     dataType: "json",
    //     success: function (data) {
    //         if (data === 1) {
    //             warnMsg("当天销账定时任务还未执行，定时任务执行后允许上报");
    //             removeModal();
    //             throw new Error("当天销账定时任务还未执行，定时任务执行后允许上报");
    //         }
    //     },
    // });

    //获取配置信息
    $.ajax({
        url: "/realestate-inquiry-ui/accessLog/redisVal?redisKey=SBXZPLSB",
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            if (data) {
                warnMsg("后台正在执行销账任务，请勿重复执行");
                removeModal();
                throw new Error("后台正在执行销账任务，请勿重复执行");
            }
        },
        error: function (xhr) {
            delAjaxErrorMsg(xhr)
        }
    });


    var xmidList = [];
    var zzxzList = [];
    var sbxzVOList = [];
    //判断xzzt 是否为2 ，如果是2 直接页面提示，不上报
    for (var i = 0; i < checkeddata.length; i++) {
        if (checkeddata[i].YWH && xmidList.indexOf(checkeddata[i].YWH) === -1) {
            if (checkeddata[i].XZZTDM !== "2") {
                var sbxzVO = {};
                sbxzVO.xmid = checkeddata[i].YWH;
                sbxzVO.sbxzid = checkeddata[i].ID;
                xmidList.push(checkeddata[i].YWH);
                sbxzVOList.push(sbxzVO);
            } else {
                zzxzList.push(checkeddata[i].SLBH);
            }
        }
    }
    if (isNotBlank(zzxzList) && zzxzList.length > 0) {
        removeModal();
        warnMsg("以下受理编号正在销账，不允许上报<br>" + zzxzList);
        return false;
    }
    $.ajax({
        url: "/realestate-inquiry-ui/accessLog/sb",
        type: "POST",
        data: JSON.stringify(sbxzVOList),
        contentType: 'application/json',
        // async: false,
        dataType: "json",
        success: function (data) {
            removeModal();
            successMsg("上报成功！");
            layui.table.reload('pageTable', {page: {curr: 1}});
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err)
        }
    });
}

function lwsb(data) {
    if (checkeddata.length === 0) {
        removeModal();
        warnMsg("请先勾选需要上报的数据");
        return false;
    }
    var xmidList = [];
    var zzxzList = [];
    //主键集合
    var idList = [];
    var sbxzDTO = {};
    //判断xzzt 是否为2 ，如果是2 直接页面提示，不上报
    for (var i = 0; i < checkeddata.length; i++) {
        if (xmidList.indexOf(checkeddata[i].YWH) === -1) {
            if (checkeddata[i].XZZTDM !== "2") {
                xmidList.push(checkeddata[i].YWH);
                idList.push(checkeddata[i].ID);
            } else {
                zzxzList.push(checkeddata[i].SLBH);
            }
        }
    }
    sbxzDTO.xmidList = xmidList;
    sbxzDTO.idList = idList;
    if (isNotBlank(zzxzList) && zzxzList.length > 0) {
        removeModal();
        warnMsg("以下受理编号正在销账，不允许上报<br>" + zzxzList);
        return false;
    }
    var content = '<div id="sh-yj" class="bdc-layer-textarea">\n' +
        '    <form class="layui-form" action="">\n' +
        '        <div class="layui-form-item pf-form-item">\n' +
        '            <div class="layui-inline" style="width: 100%">\n' +
        '                <label class="layui-form-label"><span class="required-span"><sub style="color: red">*</sub></span>例外原因：</label>\n' +
        '                <div class="layui-input-inline bdc-end-time-box">\n' +
        '                    <textarea name="lwyy" id="lwyy" placeholder="请输入内容" style="resize: none" class="layui-textarea"></textarea>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </form>\n' +
        '</div>';
    var acceptIndex = layer.open({
        title: '例外原因',
        type: 1,
        area: ['430px'],
        btn: ['确定', '取消'],
        content: content,
        yes: function () {
            addModel();
            sbxzDTO.lwyy = $("#lwyy").val();
            $.ajax({
                url: "/realestate-inquiry-ui/accessLog/lwsb",
                type: "POST",
                data: JSON.stringify(sbxzDTO),
                contentType: 'application/json',
                async: false,
                dataType: "json",
                success: function (data) {
                    removeModal();
                    layer.close(acceptIndex);
                    if (data && data > 0) {
                        successMsg("上报成功！");
                        layui.table.reload('pageTable', {page: {curr: 1}});
                    } else {
                        warnMsg("上报失败");
                    }
                },
                error: function (err) {
                    removeModal();
                    delAjaxErrorMsg(err)
                }
            });
        },
        btn2: function () {
            //取消
        }
    });
}


/*
* 全部上报按钮操作
* */
function qbsb(data) {
    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/accessLog/qbsb",
        type: "POST",
        contentType: 'application/json',
        async: false,
        dataType: "json",
        success: function (data) {
            removeModal();
            successMsg("上报成功！");
            layui.table.reload('pageTable', {page: {curr: 1}});
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err)
        }
    });
}

/*
* 手动更新销账状态，和定时任务的比对逻辑一致
* */
function gxxzzt() {
    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/accessLog/gxXzzt",
        type: "GET",
        dataType: "json",
        success: function (data) {
            removeModal();
            successMsg("更新成功");
            layui.table.reload('pageTable', {page: {curr: 1}});
        },
        error: function (xhr) {
            delAjaxErrorMsg(xhr)
        }
    });
}