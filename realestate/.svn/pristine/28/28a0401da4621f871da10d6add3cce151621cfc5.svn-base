$(function () {
    //点击高级查询--4的倍数(超出两行的查询条件，如果不一致，需要再单独分装js)
    $('#seniorSearch').on('click', function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $(this).parent().toggleClass('bdc-button-box-four');
        if ($(this).html() == '高级查询') {
            $(this).html('收起');
        } else {
            $(this).html('高级查询');
        }

        $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);

        if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
            $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
        } else {
            $('.layui-table-body').height($('.bdc-table-box').height() - 131);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
        }
    });
});

/**
 * 生成移交单信息
 */
function generateYjd(idArr) {
    var submitIndex = layer.open({
        type: 1,
        title: '确认提交',
        skin: 'bdc-small-tips',
        area: ['320px'],
        content: '确定当前项目生成移交单？生成以后不允许删除信息！',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            //确定操作
            checkXmYjdExisted(idArr);
            layer.close(submitIndex);
        },
        btn2: function () {
            //取消
        }
    });
}

/**
 *
 * @param xmid
 */
function getXmZslx(xmid) {
    var zslx = "";
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zsxx/zslx?xmid=" + xmid,
        type: "GET",
        contentType: 'application/json',
        // dataType: "json",
        async: false,
        success: function (data) {
            if (data && data[0]) {
                if (data[0] == zsModel) {
                    zslx = "证书";
                }
                if (data[0] == zmModel) {
                    zslx = "证明";
                }
            }
        }, error: function () {
            warnMsg("获取证书类型异常！");
        }
    });
    return zslx;
}

/**
 * 验证当前项目是否已存在移交单
 */
function checkXmYjdExisted(idArr, yjdid, yjdbh) {
    var addNew = 0;
    var param = {};
    param["listXmid"] = idArr;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/yjd/xmYjdList",
        type: "POST",
        data: JSON.stringify(param),
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            if (yjdid && !isNullOrEmpty(yjdid)) {
                // 已存在移交单编号信息，重复添加操作
                addNew = addYjdxx(data, idArr, yjdid, yjdbh);
            } else {
                // 重新生成移交单编号信息
                initYjdxx(data, idArr);
            }
        }
    });
    return addNew;
}

/**
 *
 * @param savedYjdxx
 * @param idArr 新增的项目信息
 * @param dataArr
 * @param yjdid 当前已有的移交单id
 * @param yjdbh 当前已生成的移交单编号
 */
function addYjdxx(savedYjdxx, idArr, yjdid, yjdbh) {
    var addFlag = 0;
    if (savedYjdxx && savedYjdxx.length > 0) {
        var slbh = "";
        $.each(savedYjdxx, function (index, value) {
            slbh = slbh + value.slbh + "，";
        });


        var msg = "项目:" + slbh + "已移交过。是否继续添加？";

        var submitIndex = layer.open({
            type: 1,
            title: '确认提交',
            skin: 'bdc-small-tips',
            area: ['320px'],
            content: msg,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                layer.close(submitIndex);
                //确定操作
                generateAndSaveYjdxx(idArr, yjdid, yjdbh);
                addFlag = idArr.length;
            },
            btn2: function () {
                //取消
                addFlag = -1;
            }
        });
    } else {
        generateAndSaveYjdxx(idArr, yjdid, yjdbh);
        addFlag = idArr.length;
    }
    return addFlag;
}


/**
 *
 * @param savedYjdxx 库中已保存的信息
 * @param idArr 当前页面的信息
 */
function initYjdxx(savedYjdxx, idArr) {
    if (savedYjdxx && savedYjdxx.length > 0) {
        var slbh = "";
        $.each(savedYjdxx, function (index, value) {
            slbh = slbh + value.slbh + "，";
        });


        var msg = "项目:" + slbh + "已移交过。是否继续生成新的移交单编号？";

        var submitIndex = layer.open({
            type: 1,
            title: '确认提交',
            skin: 'bdc-small-tips',
            area: ['320px'],
            content: msg,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                //确定操作
                generateAndSaveYjdxx(idArr);
                layer.close(submitIndex);
            },
            btn2: function () {
                //取消
            }
        });
    } else {
        generateAndSaveYjdxx(idArr);
    }

}
/**
 * 生成并保存移交单信息
 * @param idArr
 * @param yjdid 移交单id已生成，重复添加时，传入此值
 * @param yjdbh 移交单编号已生成，重复添加时，传入此值
 */
function generateAndSaveYjdxx(idArr, yjdid, yjdbh) {
    var param = {};
    param["listXmid"] = idArr;

    param["yjdid"] = yjdid;
    param["yjdbh"] = yjdbh;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/yjd/yjdxx",
        type: "POST",
        data: JSON.stringify(param),
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            if (data && data.yjdbh) {
                generateYjdBtnDisabled();
                $("#yjdid").val(data.yjdid);
                $("#yjdbh").html(data.yjdbh);

                if (yjdid == data.yjdid) {
                    // 本地重复添加，不再提示打印
                    return false;
                }
                var submitIndex = layer.open({
                    type: 1,
                    skin: 'bdc-small-tips',
                    title: '确认打印',
                    area: ['320px'],
                    content: '移交单编号已生成，请打印！',
                    btn: ['确定', '取消'],
                    btnAlign: 'c',
                    yes: function () {
                        //确定操作
                        printYjd(data.yjdid, data.yjdbh);
                        layer.close(submitIndex);
                    },
                    btn2: function () {
                        //取消
                    }
                });
            }
        }
    });
}

/**
 * 更新移交单
 * @param yjdDO
 */
function updateYjdJsr(obj) {
    // 查看移交单详情
    var yjdDO = obj.data;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/yjd",
        type: "PUT",
        data: JSON.stringify(yjdDO),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            if (data) {
                successMsg("接收人更新成功！");
            }
        }, error: function (e) {
            warnMsg("编号为：" + yjdDO.yjdid + "的接收人更新失败");
        }
    });
}

// 存储打印配置的sessionKey，当前页的打印类型
var appName = "realestate-register-ui";
var dylxArr = ["yjd"];
var sessionKey = "yjdCommon";
setDypzSession(dylxArr, sessionKey);
/**
 * 打印移交单
 * @param yjdid
 * @returns {boolean}
 */
function printYjd(yjdid, yjdbh) {
    if (yjdid == null || yjdid == '') {
        warnMsg("没有生成移交单编号！");
        return false;
    }
    var modelUrl = yjdModelUrl;
    var yjdDylx = "yjd";
    if (yjdbh.indexOf('DYZX') > -1) {
        modelUrl = yjdDyzxModelUrl;
        yjdDylx = "yjdDyzx";
        dylxArr.push(yjdDylx);
        setDypzSession(dylxArr, sessionKey);
    }
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/yjd/" + yjdid + "/" + yjdDylx + "/xml";
    printChoice(yjdDylx, appName, dataUrl, modelUrl, false, sessionKey);
}

/**
 *  设置生成移交单编号的按钮不可用
 */
function generateYjdBtnDisabled() {
    // 设置生成移交单编号的按钮不可用
    $("#generateYjd").attr('disabled', true);
    $("#generateYjd").addClass("bdc-prohibit-btn");
    // 设置预览页面的删除按钮不可用
    $(".layui-table-cell .bdc-delete-btn").removeAttr('lay-event');
    $(".layui-table-cell .bdc-delete-btn").addClass("bdc-prohibit-btn");
}

/**
 *  设置生成移交单编号的按钮可用
 */
function resetYjdBtn() {
    // 设置生成移交单编号的按钮不可用
    $("#generateYjd").removeAttr('disabled', true);
    $("#generateYjd").removeClass("bdc-prohibit-btn");
    // 设置预览页面的删除按钮不可用
    $(".layui-table-cell .bdc-delete-btn").attr('lay-event');
    $(".layui-table-cell .bdc-delete-btn").removeClass("bdc-prohibit-btn");
}