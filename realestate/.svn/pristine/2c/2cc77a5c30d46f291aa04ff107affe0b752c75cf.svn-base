/**
 * 南通特有js,用于区分南通和合肥一样的页面操作，有不一样的逻辑
 */

var printValue = $.getUrlParam('print');

var processInsId = $.getUrlParam('processInsId');

var ckzmd = $.getUrlParam('ckzmd');

/**
 * 根据processInsId查询项目dzwyt,判断：车库/车位不动产登记证明单 ？ 不动产权首次登记证明单
 * ---调整逻辑,存在一个流程发证书和车库车位证明单的情况
 */
//列表打开
if(ckzmd && ckzmd == "true") {
    sczmdModel = "ckzmd";
    document.title = "不动产登记证明单";
    var pElement = document.querySelector("#car");
    pElement.innerText = "不动产登记证明单";

    var zmdTitleElement = document.querySelector("#zmdTitle");
    zmdTitleElement.innerText = "不动产登记证明单";
    var trElementList = document.querySelectorAll(".tr-car");
    trElementList.forEach(function (element) {
        element.style.display = 'none';
    })
    // 字段调整
    changeCkzmdZd();
} else {
    if($("#zmdTitle").length >0) {
        //正常单个打开
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/judgeZmdType/" + processInsId,
            type: "POST",
            contentType: 'application/json',
            dataType: "json",
            async: false,
            success: function (data) {
                if (data) {
                    sczmdModel = "ckzmd";
                    document.title = "不动产登记证明单";
                    var pElement = document.querySelector("#car");
                    pElement.innerText = "不动产登记证明单";

                    var zmdTitleElement = document.querySelector("#zmdTitle");
                    zmdTitleElement.innerText = "不动产登记证明单";
                    var trElementList = document.querySelectorAll(".tr-car");
                    trElementList.forEach(function (element) {
                        element.style.display = 'none';
                    })
                    // 字段调整
                    changeCkzmdZd();
                }
            },
            error: function () {
                warnMsg("");
            }
        });
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

                // 修改原来老的印制号的状态，手动改过印制号后，要把修改前的印制号
                // 状态更新成 可使用（对应数据库的已领用状态6）
                updateOldYzhSyqkAndSymx(getQzysxlh, getYzhid);
                var bdcYzhSyqkQO = {};
                bdcYzhSyqkQO["zsid"] = zsid;
                // 这里要求验证信息返回印制号的ID，方便印制号使用情况的更新
                bdcYzhSyqkQO["yzhid"] = data.yzxsjid;
                bdcYzhSyqkQO["qzysxlh"] = bdcYzhQO.qzysxlh;
                bdcYzhSyqkQO["syyy"] = ysyyy;
                bdcYzhSyqkQO["syqk"] = ysy;
                result = updateBdcYzhSyqk(bdcYzhSyqkQO);
                getQzysxlh = bdcYzhQO.qzysxlh;
                getYzhid = data.yzxsjid;
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

function updateOldYzhSyqkAndSymx(getQzysxlh, getYzhid) {
    var bdcYzhSyqkQO = {};
    bdcYzhSyqkQO["yzhid"] = getYzhid;
    bdcYzhSyqkQO["qzysxlh"] = getQzysxlh;
    bdcYzhSyqkQO["syyy"] = yzhhy;
    bdcYzhSyqkQO["syqk"] = yly;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/hyYzhAndSyqk",
        type: "POST",
        data: JSON.stringify(bdcYzhSyqkQO),
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
        }
    });
}

/**
 * 设置印制号的输入框可用
 */
function setQzysxlhAable() {
    var $qzysxlh = $("#qzysxlh");
    $qzysxlh.removeAttr('disabled');
    $qzysxlh.removeAttr('readonly');
    $qzysxlh.removeClass("bdc-prohibit-text");
}

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
 * 针对证书设置打印状态下的权限
 **/
function setZsPrintState() {
    // 设置按钮权限
    var btnArr = $('.layui-btn');
    $.each(btnArr, function (index, item) {
        // 为了好控制权限，建议把相同功能的按钮设置同一个name，免得写多个id去判断
        if ('printBtn' == item.name || 'yzhBtn' == item.name) {
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
}


/**
 * 海门版本车库/车位不动产登记证明单字段动态调整
 **/

function changeCkzmdZd() {
    // 调整终止日期字段长度
    $('input[name="tdsyjssj"]').parent().attr('colspan','4');
    var tdElementList = document.querySelectorAll(".change-text");
    //字段名称修改
    tdElementList.forEach(function (element ){
        var text = $(element).text();
        if(text === '土地用途'){
            $(element).text('宗地用途');
        }
        if(text === '房屋面积'){
            $(element).text('建筑面积');
        }
        if(text === '房屋用途') {
            $(element).text('规划用途');
        }
    }
    )
}
