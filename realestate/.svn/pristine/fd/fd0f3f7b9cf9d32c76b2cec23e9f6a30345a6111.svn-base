/**
 * 合肥特有js,用于区分南通和合肥一样的页面操作，有不一样的逻辑
 */

/**
 * 单个证书页面设置按钮属性（依据qszt和印制号）
 * @param qszt
 * @param zsQzysxlh
 * 以下三个方法的执行顺序不可改变
 */
function setZsBtnAttr(qszt, zsQzysxlh) {
    // 设置和权属状态相关的按钮属性
    setQsztBtnAttr(qszt);
    // 设置和印制号相关的按钮的属性
    setYzhBtnAttr(zsQzysxlh);
    // 设置和权属状态，印制号都相关的属性
    setQsztAndYzhBtnAttr(qszt, zsQzysxlh);
}

/**
 * 单个证书设置和qszt，yzh均有关的按钮的属性
 * @param qszt
 * @param zsQzysxlh
 */
function setQsztAndYzhBtnAttr(qszt, zsQzysxlh) {
    var $zsPrint = $("#printBtn");
    // 印制号输入框
    var $qzysxlh = $("#qzysxlh");
    // 获取印制号的按钮
    var $getYzh = $("#getYzh");

    // 证书打印——有印制号，并且权属状态为现势时才可用，其他条件均不可用
    if (qszt == qsztValid && !isNullOrEmpty(zsQzysxlh) && !isNullOrEmpty(zsQzysxlh.trim())) {
        $zsPrint.removeAttr('disabled');
        $zsPrint.removeClass("layui-btn-disabled");
    } else {
        $zsPrint.attr('disabled', 'off');
        $zsPrint.addClass("layui-btn-disabled");
    }
    // 证书已登簿且为现势，并且输入框中没有值时，印制号编辑框才可用
    if (qszt == qsztValid && (isNullOrEmpty(zsQzysxlh) || (!isNullOrEmpty(zsQzysxlh) && isNullOrEmpty(zsQzysxlh.trim())))) {
        $getYzh.removeAttr('disabled');
        $getYzh.removeClass("layui-btn-disabled");

        $qzysxlh.removeAttr('disabled');
        $qzysxlh.removeClass("bdc-prohibit-text");
    } else {
        $getYzh.attr('disabled', 'off');
        $getYzh.addClass("layui-btn-disabled");

        $qzysxlh.attr('disabled', 'off');
        $qzysxlh.addClass("bdc-prohibit-text");
    }
}

/**
 * 单个证书，设置和印制号相关的按钮
 * @param zsQzysxlh
 */
function setYzhBtnAttr(zsQzysxlh) {
    var $zfYzh = $("#zfYzh");
    var $hyYzh = $("#hyYzh");
    var $getYzh = $("#getYzh");
    var $qzysxlh = $("#qzysxlh");
    var $zsPrint = $("#printBtn");

    // 页面根据当前证书有没有印制号控制印制号获取和作废按钮
    if (isNullOrEmpty(zsQzysxlh) || (!isNullOrEmpty(zsQzysxlh) && isNullOrEmpty(zsQzysxlh.trim()))) {
        $zfYzh.attr('disabled', 'off');
        $zfYzh.addClass("layui-btn-disabled");

        $hyYzh.attr('disabled', 'off');
        $hyYzh.addClass("layui-btn-disabled");

        $zsPrint.attr('disabled', 'off');
        $zsPrint.addClass("layui-btn-disabled");

        // 设置获取印制号按钮可用，输入框可编辑
        $getYzh.removeAttr('disabled');
        $getYzh.removeClass("layui-btn-disabled");

        $qzysxlh.removeAttr('disabled');
        $qzysxlh.removeClass("bdc-prohibit-text");
    } else {
        $getYzh.attr('disabled', 'off');
        $getYzh.addClass("layui-btn-disabled");

        $qzysxlh.attr('disabled', 'off');
        $qzysxlh.addClass("bdc-prohibit-text");

        // 设置作废印制号按钮可用
        $zfYzh.removeAttr('disabled');
        $zfYzh.removeClass("layui-btn-disabled");

        $hyYzh.removeAttr('disabled');
        $hyYzh.removeClass("layui-btn-disabled");

        $zsPrint.removeAttr('disabled');
        $zsPrint.removeClass("layui-btn-disabled");
    }
}

/**
 * 单个证书设置和qszt相关的按钮
 * @param qszt
 */
function setQsztBtnAttr(qszt) {
    // 显示注销标识
    if (qsztHistory == qszt) {
        $("#yzx").removeAttr("style");
    }

    if (qszt || qszt == qsztTempory) {
        var $db = $('#db');
        var $zsPrint = $("#printBtn");
        // 印制号大按钮（该按钮设置为不可用时，下拉按钮不会展示）
        var $yzh = $("#yzh");
        // 获取印制号
        var $getYzh = $("#getYzh");
        // 作废印制号
        // var $zfYzh = $("#zfYzh");
        var $qzysxlh = $("#qzysxlh");
        // 证号信息
        var bdcqzh = $("#bdcqzh").val();
        // 权属状态为临时并且证号已生成时登簿才可用
        if (qszt == qsztTempory && !isNullOrEmpty(bdcqzh)) {
            // 未登簿时，登簿按钮可用
            $db.removeAttr('disabled');
            $db.removeClass("layui-btn-disabled");
        } else {
            // 设置登簿按钮不可用
            $db.addClass("layui-btn-disabled");
            $db.attr("disabled", "disabled");
        }
        // 权属状态为为现势时，打印和获取印制号才可用
        if (qszt == qsztValid) {
            // 印制号可用
            $yzh.removeAttr('disabled');
            $yzh.removeClass("layui-btn-disabled");

            $getYzh.removeAttr('disabled');
            $getYzh.removeClass("layui-btn-disabled");

            // $zfYzh.removeAttr('disabled');
            // $zfYzh.removeClass("layui-btn-disabled");

            $qzysxlh.removeAttr('disabled');
            $qzysxlh.removeClass("bdc-prohibit-text");

            $zsPrint.removeAttr('disabled');
            $zsPrint.removeClass("layui-btn-disabled");
        } else {
            // 注销或者临时，印制号不可用
            $yzh.attr('disabled', 'disabled');
            $yzh.addClass("layui-btn-disabled");

            $getYzh.attr('disabled', 'disabled');
            $getYzh.addClass("layui-btn-disabled");

            // $zfYzh.attr('disabled', 'disabled');
            // $zfYzh.addClass("layui-btn-disabled");

            $qzysxlh.attr('disabled', 'off');
            $qzysxlh.addClass("bdc-prohibit-text");

            $zsPrint.attr('disabled', 'disabled');
            $zsPrint.addClass("layui-btn-disabled");
        }
    }
}


/**
 * 验证印制号使用情况
 */
function checkYzhSyqk(bdcYzhQO) {
    var result = false;
    var zsid = bdcYzhQO.zsid;
    // 用于验证印制号的对象,要清空证书ID
    var yzYzhQO = bdcYzhQO;
    yzYzhQO["zsid"] = null;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/yzh/yzxx",
        type: "POST",
        data: JSON.stringify(yzYzhQO),
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            if (data && data.code) {
                warnMsg(data.msg);
            } else if (data && !isNullOrEmpty(data.yzxsjid)) {
                var bdcYzhSyqkQO = {};
                bdcYzhSyqkQO["zsid"] = zsid;
                // 这里要求验证信息返回印制号的ID，方便印制号使用情况的更新
                bdcYzhSyqkQO["yzhid"] = data.yzxsjid;
                bdcYzhSyqkQO["qzysxlh"] = bdcYzhQO.qzysxlh;
                bdcYzhSyqkQO["syyy"] = ysyyy;
                bdcYzhSyqkQO["syqk"] = ysy;
                result = updateBdcYzhSyqk(bdcYzhSyqkQO);
            } else if (data) {
                warnMsg(data.tsxx);
            } else {
                warnMsg("权证印刷序列号验证不通过，请分析输入的印制号是否已被使用或当前用户没有权限获取该印制号！");
            }
        },
        error: function () {
            warnMsg("验证权证印刷序列号异常！");
        }
    });
    return result;
}

/**
 * 针对证书设置打印状态下的权限
 **/
function setZsPrintState() {
    // 设置按钮权限
    var btnArr = $('.layui-btn');
    $.each(btnArr, function (index, item) {
        // 为了好控制权限，建议把相同功能的按钮设置同一个name，免得写多个id去判断
        if ('printBtn' == item.name || 'yzhBtn' == item.name || 'ylBtn' == item.name) {
            // 去除打印按钮上添加的 css 样式
            item.removeAttribute("style", "display:none;");
            item.removeAttribute("disabled", "off");
            item.classList.remove("layui-btn-disabled");
            item.classList.remove("bdc-prohibit-btn");

            if ('submitBtn' == item.id) {
                item.classList.add("bdc-major-btn");
            }
            // 重新渲染表格
            $('.laytable-cell-checkbox input').removeAttr('disabled').removeAttr('readOnly');
            return;
        }
        // 为保证详情按钮可以正常查看，设置为a标签.除打印按钮外，其他按钮设置为隐藏
        if (item.id != 'search' && (item.type == 'button' || item.type == 'submit' || 'reset' == item.type)) {
            item.setAttribute("style", "display:none;");
        }
    });
    // 去除印制号输入框的权限
    // 印制号输入框
    var $qzysxlh = $("#qzysxlh");
    $qzysxlh.removeAttr('readonly');
}

// 是否验证完税
function checkSfyzws(gzlslid) {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/bdcdy/sfyzws/" + gzlslid + "?time=" + new Date().getTime(),
        contentType: "application/json;charset=utf-8",
        type: "GET",
        success: function (data) {
            if (data && data == true) {
                yzws = true;
            }
        },
        error: function () {
            warnMsg("判断当前流程是否验证完税异常！");
        }
    });
}

// 是否查询建设银行支付状态
function initQtButtonAtrr(gzlslid) {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/bdcdy/xmxx/" + gzlslid + "?time=" + new Date().getTime(),
        contentType: "application/json;charset=utf-8",
        type: "GET",
        success: function (data) {
            if (data && data.length == 1) {
                var xzQllx = [95, 96, 97, 98, 19,37];
                var bdcXmDO = data[0];
                if (xzQllx.indexOf(bdcXmDO.qllx) == -1 && $("#jhddcx").length > 0) {
                    $("#jhddcx")[0].removeAttribute("style", "display:none;");
                }
            }
        },
        error: function () {
            warnMsg("判断当前流程是否验证完税异常！");
        }
    });
}

// 建行订单查询
function jhddcx(gzlslid) {
    $.ajax({
        url: BASE_URL + '/bdcdy/sfws/jhddcx/' + gzlslid,
        type: "GET",
        dataType: "json",
        async: false,
        success: function (data) {
            if (data) {
                if (data.Py_Ordr_No) {
                    showAlertDialog("已支付，支付订单号：" + data.Py_Ordr_No);
                } else if (data.yhjkrkzt === 1) {
                    showAlertDialog("未查询到支付信息，已补推成功！请重新查询。");
                } else if (data.yhjkrkzt === 0) {
                    showAlertDialog("未查询到支付信息，补推不成功！");
                } else {
                    warnMsg("未查询到订单信息！");
                }
            }
        }, error: function () {
            warnMsg("订单信息查询异常！");
        }
    });
}

/**
 * 作废印制号
 * @param bdcYzhQO
 */
function zfYzh_hf(bdcYzhQO) {
    var result = false;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/yzh/zfyzh_hf",
        type: "POST",
        data: JSON.stringify(bdcYzhQO),
        contentType: 'application/json',
        async: false,
        success: function (data) {
            if (data && data == true) {
                successMsg("印制号作废成功！");
                result = data;

                if(isXnyzh) {
                    // 如果当前领证方式是电子证照，印制号采用虚拟印制号情况下，用户可能作废印制号手动录入印制号，
                    // 这里将isXnyzh标识设置为false，这样页面保存时候会验证并保存印制号，避免点击保存但是印制号未入库
                    isXnyzh = false;
                }
            } else if (data && data.code) {
                warnMsg(data.msg);
            } else {
                warnMsg(data);
            }
        },
        error: function () {
            warnMsg("作废印制号异常！");
        }
    });
    return result;
}

/**
 * 需求32762 当领证方式为电子证照时候，自动生成虚拟印制号
 * 这里设计成在证书、证明页面加载的时候，判断领证方式并生成虚拟印制号
 *
 * @param processInsId 工作流实例ID
 * @param zsid 证书ID
 */
var isXnyzh = false;
function getXnyzh(processInsId, zsid) {
    if(isNullOrEmpty(processInsId) && isNullOrEmpty(zsid)) {
        return ;
    }
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/yzh/xnyzh?processInsId=" + processInsId + "&zsid=" + zsid,
        type: "GET",
        contentType: 'application/json',
        dataType: "text",
        success: function (data) {
            if(!isNullOrEmpty(data) && data.indexOf("HF") > -1) {
                // 虚拟印制号设值，其它正常印制号不设值，正常查询证书会查询到
                $("#qzysxlh").val(data);
                isXnyzh = true;
            }
        }
    });
}