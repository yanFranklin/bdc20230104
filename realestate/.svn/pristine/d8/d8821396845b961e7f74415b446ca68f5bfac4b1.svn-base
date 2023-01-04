layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
//页面入口
var zdList;
var sfxmPzList;
var qlrsfxmpzList = [];
var ywrsfxmpzList = [];
//收费页面类型
var sfymlx = getQueryString("sfymlx");
var processInsId = getQueryString("processInsId");
var xmid = getQueryString("xmid");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var printValue = getQueryString("print");
var zxlc = getQueryString("zxlc")
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var form = layui.form;
    addModel();
    setTimeout("loadButtonArea('sfxx')", 10);
    setTsjkBtnState();
    setTimeout(function () {
        try {
            $.when(loadData(), loadSfxmPz()).done(function () {
                $.when(querySfxx()).done(function () {
                    removeModal();
                })
            })
        } catch (e) {
            removeModal();
            ERROR_CONFIRM("请求失败", e.message);
            return
        }
    }, 10);
    var isSubmit = true;
    form.on("submit(saveData)", function (data) {
        if (!isSubmit) {
            layer.msg('必填项不能为空', {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            addModel();
            setTimeout(function () {
                try {
                    $.when(saveSfxx(), saveSfxm()).done(function () {
                        removeModal();
                        ityzl_SHOW_SUCCESS_LAYER("保存成功");
                    })
                } catch (e) {
                    removeModal();
                    ERROR_CONFIRM("保存失败", e.message);
                    return
                }
            }, 10);
            return false;
        }

    });

    //缴费途径改变事件
    form.on('select(hyzfjftj)', function (data) {
        if (data.value === "1") {
            //如果选择商业银行，显示通知政融、退款结果查询、退款申请、推送缴库、获取缴费状态、pos缴费按钮才显示出来，合一支付、合一支付状态查询按钮隐藏。
            $("#tzzr").show();
            $("#tksq").show();
            $("#tkjgcx").show();
            $("#sftgTsjk").show();
            $("#hqjfzt").show();
            $("#scddh").show();
            $("#hyzf").hide();
            $("#hyzfztcx").hide();
        } else if (data.value === "2") {
            $("#scddh").show();
            $("#hqjfzt").show();
            $("#hyzf").show();
            $("#hyzfztcx").show();
            $("#tzzr").hide();
            $("#tksq").hide();
            $("#tkjgcx").hide();
            $("#sftgTsjk").hide();
        }
    });
});

function loadBtn() {
    if (qlrsfxxid === null && ywrsfxxid === null) {
        setAllElementDisabled()
    } else {
        getStateById(readOnly, formStateId, "sfxx", "", "bdcSfxxTable");
    }
}

function loadSfxmPz() {
    var url = getContextPath() + "/sf/xm/pz";
    if (sfymlx === "ycsl") {
        url = getContextPath() + "/sf/xm/pz/ycsl";
    }
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {processInsId: processInsId, xmid: xmid},
        success: function (data) {
            if (isNotBlank(data)) {
                sfxmPzList = data;
                if (sfxmPzList.length > 0) {
                    for (var i = 0; i < sfxmPzList.length; i++) {
                        if (sfxmPzList[i].qlrlb === "1") {
                            qlrsfxmpzList.push(sfxmPzList[i]);
                        }
                        if (sfxmPzList[i].qlrlb === "2") {
                            ywrsfxmpzList.push(sfxmPzList[i]);
                        }
                    }
                }
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function loadData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

// 查询缴库情况，设置推送缴库按钮是否可用
function setTsjkBtnState() {
    setTimeout(function () {
        $.ajax({
            url: getContextPath() + "/sf/checkJkzt",
            type: 'get',
            dataType: 'json',
            data: {gzlslid: processInsId},
            success: function (data) {
                if (data) {
                    $("#tsjk").addClass("bdc-prohibit-btn");
                    $("#tsjk").removeAttr("onclick");
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }, 15);
}

// 调用接口推送收费缴库
function tsSfjk() {
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/tsjk",
        type: 'get',
        dataType: 'json',
        data: {gzlslid: processInsId, sfyj: 0},
        success: function (data) {
            removeModal();
            console.log(data);
            if (data && data.statusCode === "0000") {
                ityzl_SHOW_SUCCESS_LAYER("推送成功");
            } else {
                ityzl_SHOW_SUCCESS_LAYER("推送失败" + data.msg);
            }

            querySfxx();
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

function tsSfjkBak() {
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/tsjk",
        type: 'get',
        dataType: 'json',
        data: {gzlslid: processInsId},
        success: function (data) {
            removeModal();
            console.log(data);

            if (data) {
                var errors = 0;
                $.each(data.bdcSlSfxxDOList, function (index, sfxx) {
                    if (sfxx.yhjkrkzt != 1) {
                        errors++;
                    }
                });
                $.each(data.bdcSlHsxxDOList, function (index, hsxx) {
                    if (hsxx.yhjkrkzt != 1) {
                        errors++;
                    }
                });
                if (errors == 0) {
                    ityzl_SHOW_SUCCESS_LAYER("推送成功");
                } else {
                    ityzl_SHOW_WARN_LAYER("推送失败");
                }
            } else {
                ityzl_SHOW_WARN_LAYER("推送失败");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

function queryJfzt() {
    addModel();
    $.ajax({
        url: getContextPath() + "/sfss/ddxx/sfzt",
        type: 'get',
        dataType: 'json',
        data: {gzlslid: processInsId},
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("获取缴费状态成功");
            querySfxx();
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

function queryJfztBak() {
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/queryJfzt",
        type: 'get',
        dataType: 'json',
        data: {gzlslid: processInsId},
        success: function (data) {
            removeModal();
            if (isNotBlank(data)) {
                ityzl_SHOW_SUCCESS_LAYER("缴费状态：" + data.jfzt);
            } else {
                ityzl_SHOW_WARN_LAYER("缴费失败");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

//退款申请
function sfTksq() {
    addModel();
    //先查询订单信息
    getReturnData("/sfss/ddxx/ykq/ddxx", {gzlslid: processInsId}, "GET", function (data) {
        if (data && data.length > 0) {
            var bdcGzYzQO = {};
            bdcGzYzQO.zhbs = "SFXX_TKSQ";
            var gzyzParamMap = {};
            gzyzParamMap.gzlslid = processInsId;
            bdcGzYzQO.paramMap = gzyzParamMap;
            gzyzCommon(2, bdcGzYzQO, function (data) {
                console.log("验证通过");
                $.ajax({
                    url: getContextPath() + "/sfss/ddxx/tksq",
                    type: 'get',
                    dataType: 'json',
                    data: {gzlslid: processInsId},
                    success: function (data) {
                        removeModal();
                        if (data && data[0] != null && isNotBlank(data[0].tkdh)) {
                            ityzl_SHOW_SUCCESS_LAYER("操作成功");
                            querySfxx();
                        } else {
                            ityzl_SHOW_WARN_LAYER("操作失败");
                        }
                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });

            });
        } else {
            removeModal();
            ityzl_SHOW_WARN_LAYER("未查询到订单信息");
        }

    }, function (error) {
        delAjaxErrorMsg(error);

    })


}

//退款申请原版本
function sfTksqBak() {
    addModel();
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = "SFXX_TKSQ";
    var gzyzParamMap = {};
    gzyzParamMap.gzlslid = processInsId;
    bdcGzYzQO.paramMap = gzyzParamMap;
    gzyzCommon(2, bdcGzYzQO, function (data) {
        console.log("验证通过");
        $.ajax({
            url: getContextPath() + "/sf/tksq",
            type: 'get',
            dataType: 'json',
            data: {gzlslid: processInsId},
            success: function (data) {
                removeModal();
                if (data && isNotBlank(data.tkdh)) {
                    ityzl_SHOW_SUCCESS_LAYER("操作成功，退款单号：" + data.tkdh);
                    querySfxx();
                } else {
                    ityzl_SHOW_WARN_LAYER("操作失败");
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });

    });

}

//查询退款结果
function queryTkjg() {
    addModel();
    $.ajax({
        url: getContextPath() + "/sfss/ddxx/querySfxxTkqk",
        type: 'get',
        dataType: 'json',
        data: {gzlslid: processInsId},
        success: function (data) {
            if (data && isNotBlank(data.sfzt)) {
                var sfztMc = changeDmToMc(data.sfzt, zdList.sfzt);
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("查询成功,退款状态为" + sfztMc);
                querySfxx();
            } else {
                removeModal();
                ityzl_SHOW_WARN_LAYER("未查询到结果");
            }

        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function queryTkjgBak() {
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/querySfxxTkqk",
        type: 'get',
        dataType: 'json',
        data: {gzlslid: processInsId},
        success: function (data) {
            if (data && isNotBlank(data.sfzt)) {
                var sfztMc = changeDmToMc(data.sfzt, zdList.sfzt);
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("查询成功,退款状态为" + sfztMc);
                querySfxx();
            } else {
                removeModal();
                ityzl_SHOW_WARN_LAYER("未查询到结果");
            }

        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

var slsfxxid;
var qlrsfxxid;
var ywrsfxxid;
var isFirst;
var qllx;

function querySfxx(sfcxjs) {
    addModel();
    var url = getContextPath() + "/sf/xx/new";
    if (sfymlx === "ycsl") {
        url = getContextPath() + "/sf/xx/ycsl";
    }
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid, processInsId: processInsId},
        // async: false,
        success: function (data) {
            if (data !== null) {
                qllx = data.qllx;
                if (data.bdcSlQlrSfxxDTO !== null) {
                    qlrsfxxid = data.bdcSlQlrSfxxDTO.sfxxid;
                }
                if (data.bdcSlYwrSfxxDTO !== null) {
                    ywrsfxxid = data.bdcSlYwrSfxxDTO.sfxxid;
                }
                generateSfJbxx(data);


                if (data.bdcSlQlrSfxxDTO == null && data.bdcSlYwrSfxxDTO == null) {
                    removeModal();
                } else
                    //不存在权利人义务人收费信息默认展示权利人收费信息
                if (data.bdcSlQlrSfxxDTO != null && !isNotBlank(data.bdcSlQlrSfxxDTO.qlrlb) && data.bdcSlYwrSfxxDTO != null && !isNotBlank(data.bdcSlYwrSfxxDTO.qlrlb)) {
                    data.bdcSlQlrSfxxDTO.qlrlb = "1";
                } else if (data.bdcSlYwrSfxxDTO != null && !isNotBlank(data.bdcSlYwrSfxxDTO.qlrlb)) {
                    data.bdcSlYwrSfxxDTO.qlrlb = "2";
                }
                generateSfxx(data.bdcSlQlrSfxxDTO, "1", sfcxjs);

                generateSfxx(data.bdcSlYwrSfxxDTO, "2", sfcxjs);
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function generateSfxx(bdcSlSfxxDTO, sqrlb, sfcxjs) {
    if (bdcSlSfxxDTO !== null) {
        //默认缴费状态为未缴费
        if (!isNotBlank(bdcSlSfxxDTO.sfzt)) {
            bdcSlSfxxDTO.sfzt = 1;
        }
        //默认缴库状态为否
        if (!isNotBlank(bdcSlSfxxDTO.yhjkrkzt)) {
            bdcSlSfxxDTO.yhjkrkzt = 0;
        }
        layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
            var form = layui.form;
            var laytpl = layui.laytpl;

            var json = {
                bdcSlSfxxQO: bdcSlSfxxDTO,
                zd: zdList,
                qlrlb: sqrlb,
                qllx: qllx
            };
            var tpl, view;
            tpl = sfxxTpl.innerHTML;
            if (bdcSlSfxxDTO.qlrlb === "1") {
                view = document.getElementById('qlrsfxx');
                $('#qlrsfxx').addClass('bdc-qlrsfxx-info');
            } else if (bdcSlSfxxDTO.qlrlb === "2") {
                view = document.getElementById('ywrsfxx');
            }
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            //给页面二维码图片赋值
            if (isNotBlank(bdcSlSfxxDTO.jfsewmurl)) {
                var url = "/realestate-accept-ui/sf/ewm?ewmnr=" + encodeURIComponent(bdcSlSfxxDTO.jfsewmurl);
                $('.bdcsfxxForm').find('.ewm' + sqrlb).attr('src', url);
            }
            //点击二维码图片放大事件
            $(".ewm" + sqrlb).click(function () {
                var _this = $(this);//将当前的pimg元素作为_this传入函数
                imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
            });
            loadSfxm(bdcSlSfxxDTO.sfxxid, sqrlb, sfcxjs);
            form.render();
            if (bdcSlSfxxDTO.sfxxid === null) {
                $('#addsfxm' + sqrlb).removeAttr('onclick');
                $('#addsfxm' + sqrlb).attr('onclick', 'ityzl_SHOW_INFO_LAYER("未配置相关收费项目无法新增")')
            }

            var sfjmfilter = "sfjm" + sqrlb;
            form.on('select(' + sfjmfilter + ')', function (selectObj) {
                var sfjm = selectObj.value;
                changeSfjm(sfjm, sqrlb);

            });

            if (bdcSlSfxxDTO.hyzfjftj == "1") {
                //如果选择商业银行，显示通知政融、退款结果查询、退款申请、推送缴库、获取缴费状态、pos缴费按钮才显示出来，合一支付、合一支付状态查询按钮隐藏。
                $("#tzzr").show();
                $("#tksq").show();
                $("#tkjgcx").show();
                $("#sftgTsjk").show();
                $("#hqjfzt").show();
                $("#scddh").show();
                $("#hyzf").hide();
                $("#hyzfztcx").hide();
            } else if (bdcSlSfxxDTO.hyzfjftj == "2") {
                $("#scddh").show();
                $("#hqjfzt").show();
                $("#hyzf").show();
                $("#hyzfztcx").show();
                $("#tzzr").hide();
                $("#tksq").hide();
                $("#tkjgcx").hide();
                $("#sftgTsjk").hide();
            }

            renderSelect();
            form.render('select');
            disabledAddFa();
        })
    } else {
        if (sqrlb === "1") {
            $('#qlrsfxx').remove();
        } else if (sqrlb === "2") {
            $('#ywrsfxx').remove();
        }
    }
}

function loadSfxm(sfxxid, qlrlb, sfcxjs) {
    $.ajax({
        url: getContextPath() + "/sf/xm",
        type: 'get',
        dataType: 'json',
        data: {sfxxid: sfxxid, gzlslid: processInsId, xmid: xmid, qlrlb: qlrlb, sfcxjs: sfcxjs},
        success: function (data) {
            generateSfxm(data, qlrlb);
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function generateSfxm(bdcSlSfxmDOList, qlrlb) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json;
        if (qlrlb === "1") {
            json = {
                bdcSlSfxmDOList: bdcSlSfxmDOList,
                zd: zdList,
                sfxmPzList: qlrsfxmpzList,
                qlrlb: qlrlb
            };
        } else {
            json = {
                bdcSlSfxmDOList: bdcSlSfxmDOList,
                zd: zdList,
                sfxmPzList: ywrsfxmpzList,
                qlrlb: qlrlb
            };
        }
        var view;
        var tpl = sfxmTpl.innerHTML;
        if (qlrlb === "1") {
            view = document.getElementById('qlrsfxm');
        } else {
            view = document.getElementById('ywrsfxm');
        }

        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });

        var sfxmdmfilter = "sfxmdm" + qlrlb;
        var sfxmbzfilter = "sfxmbz" + qlrlb;
        form.on('select(' + sfxmdmfilter + ')', function (selectObj) {
            changeSfxmdm(selectObj, qlrlb)
        });
        form.on('select(' + sfxmbzfilter + ')', function (selectObj) {
            changeSfxmbz(selectObj.value, selectObj.elem, qlrlb);
            saveSfxm();
        });
        changeSfjm($("#sfjm" + qlrlb).val(), qlrlb);
        loadBtn();
        form.render();
        form.render('select');
        renderSelect();
        disabledAddFa();
        onloadSfxmdj(qlrlb);
        countMoney('');
        removeModal();
    })
}

//是否减免改变事件
function changeSfjm(sfjm, qlrlb) {
    var jmjeElArray = [];
    if (qlrlb == "1") {
        jmjeElArray = $("#qlrsfxm").find(".bdcSfxxTable").find("input[name='jmje']");
    }
    if (qlrlb == "2") {
        jmjeElArray = $("#ywrsfxm").find(".bdcSfxxTable").find("input[name='jmje']");
    }
    if (sfjm == "1") {
        if (jmjeElArray !== null && jmjeElArray.length > 0) {
            for (var i = 0; i < jmjeElArray.length; i++) {
                $(jmjeElArray[i]).removeAttr("disabled", true);
            }
        }
    } else {
        if (jmjeElArray !== null && jmjeElArray.length > 0) {
            for (var i = 0; i < jmjeElArray.length; i++) {
                $(jmjeElArray[i]).attr("disabled", true);
            }
        }
    }
}

function changeSfxmdm(selectObj, qlrlb) {
    var pzList;
    var sfxmdm = selectObj.value;
    if (qlrlb === "1") {
        pzList = qlrsfxmpzList;
    }
    if (qlrlb === "2") {
        pzList = ywrsfxmpzList;
    }
    if (pzList !== null && pzList.length > 0) {
        for (var i = 0; i < pzList.length; i++) {
            var sfxmPz = pzList[i];
            if (sfxmPz.sfxmdm === sfxmdm) {
                layui.use(['laytpl', 'form'], function () {
                    var sfxmmcEl = $(selectObj.elem).parent().parent().parent().find("input[name='sfxmmc']")[0];
                    var slEl = $(selectObj.elem).parent().parent().parent().find("input[name='sl']")[0];
                    var jedwEl = $(selectObj.elem).parent().parent().parent().find("select[name='jedw']")[0];
                    $(sfxmmcEl).val(sfxmPz.sfxmmc);
                    var sl = $(slEl).val();
                    var jedw = $(jedwEl).val();
                    if (sl === null || sl === 0 || sl === "") {
                        sl = sfxmPz.mrsl;
                        $(slEl).val(sl)
                    }
                    if (jedw === null || jedw === 0 || jedw === "") {
                        jedw = sfxmPz.mrjedw;
                        $(jedwEl).val(jedw);
                    }
                    $(sfxmmcEl).val(sfxmPz.sfxmmc);
                    if (sfxmPz.bdcSlSfxmSfbzList !== null && sfxmPz.bdcSlSfxmSfbzList.length > 0) {
                        var sfxmbzEl = $(selectObj.elem).parent().parent().parent().find("select[name='sfxmbz']")[0];
                        var form = layui.form;
                        var laytpl = layui.laytpl, view = sfxmbzEl;
                        var json = {
                            bdcSlSfxmSfbzList: sfxmPz.bdcSlSfxmSfbzList
                        };
                        var tpl = sfxmbzTpl.innerHTML;
                        laytpl(tpl).render(json, function (html) {
                            view.innerHTML = html;
                        });
                    }
                    form.render('select', 'sfxmbz1' + qlrlb);
                    form.render('select', 'sfxmmc' + qlrlb);
                })
            }
        }
    }
}

function changeSfxmbz(sfxmbz, elem, qlrlb) {
    if (sfxmbz === "土地收益金") {
        return;
    }
    var pzList = [];
    var sfxmdmEl = $(elem).parent().parent().parent().find("select[name='sfxmdm']")[0];
    var sfxmdm = $(sfxmdmEl).val();
    if (qlrlb === "1") {
        pzList = qlrsfxmpzList;
    }
    if (qlrlb === "2") {
        pzList = ywrsfxmpzList;
    }
    if (pzList !== null && pzList.length > 0) {
        for (var i = 0; i < pzList.length; i++) {
            var sfxmPz = pzList[i];
            if (sfxmPz.sfxmdm === sfxmdm) {
                if (sfxmPz.bdcSlSfxmSfbzList !== null && sfxmPz.bdcSlSfxmSfbzList.length > 0) {
                    for (var j = 0; j < sfxmPz.bdcSlSfxmSfbzList.length; j++) {
                        var bdcSlSfxmSfbz = sfxmPz.bdcSlSfxmSfbzList[j];
                        // 收费项目dm为维修资金时，不更新为配置的金额
                        if (bdcSlSfxmSfbz.sfxmdm === "89") {
                            continue;
                        }
                        if (bdcSlSfxmSfbz.sfxmbz === sfxmbz) {
                            var sfxmdjEl = $(elem).parent().parent().parent().find("input[name='sfxmdj']")[0];
                            $(sfxmdjEl).val(bdcSlSfxmSfbz.sfxmdj);
                            var slEl = $(elem).parent().parent().parent().find("input[name='sl']")[0];
                            var sl = $(slEl).val();
                            if (sl === null || sl === 0 || sl === "") {
                                $(slEl).val(0);
                            }
                            sl = $(slEl).val();
                            var ysjeEl = $(elem).parent().parent().parent().find("input[name='ysje']")[0];
                            $(ysjeEl).val(parseFloat(bdcSlSfxmSfbz.sfxmdj) * sl);
                            var jmjeEl = $(elem).parent().parent().parent().find("input[name='jmje']")[0];
                            var jmje = $(jmjeEl).val();
                            var ssjeEL = $(elem).parent().parent().parent().find("input[name='ssje']")[0];
                            $(ssjeEL).val(Subtr($(ysjeEl).val(), jmje));
                            countMoney(elem);

                        }
                    }
                }
            }
        }
    }
}

function onloadSfxmdj(qlrlb) {
    var sfxmbzELArray = $("#bdcSfxxTable").find("select[name='sfxmbz']");
    if (sfxmbzELArray !== null && sfxmbzELArray.length > 0) {
        for (var i = 0; i < sfxmbzELArray.length; i++) {
            changeSfxmbz($(sfxmbzELArray[i]).val(), sfxmbzELArray[i], qlrlb);
        }
    }
}

function changeSl(elem) {
    var sl = $(elem).val();
    if (sl === null || sl === "") {
        ityzl_SHOW_INFO_LAYER("请填写数量");
    } else {
        var $parentTr = $(elem).parents('tr');
        var sfxmdjEl = $parentTr.find(".bdc-sfxmdj");
        var sfxmdj = sfxmdjEl.val();
        var ysjeEl = $parentTr.find(".bdc-ysje");
        var ssjeEl = $parentTr.find(".bdc-ssje");
        var jmjeEl = $parentTr.find(".bdc-jmje");
        var jmje = jmjeEl.val();
        var ysje = parseFloat(sfxmdj) * sl;
        $(ysjeEl).val(ysje);
        $(ssjeEl).val(Subtr(ysje, jmje));
        countMoney(elem);
    }
}

function countMoney(elem) {
    var sfxxTable;
    if (elem) {
        sfxxTable = $(elem).parents('.bdcSfxxTable');
    } else {
        sfxxTable = $(".bdcSfxxTable");
    }
    sfxxTable.each(function () {
        var ysjeELArray = $(this).find("input[name='ssje']");
        if (ysjeELArray !== null && ysjeELArray.length > 0) {
            var ze = 0;
            for (var i = 0; i < ysjeELArray.length; i++) {
                var ysje = $(ysjeELArray[i]).val();
                if (ysje === "" || ysje === null) {
                    ysje = 0;
                }
                ze += parseFloat(ysje);
            }
            $(this).find("#hj").val(ze);
        }
    });
    saveSfxm(elem, true);
    saveSfxx(elem);
}

function addSfxm(elem, qlrlb) {
    $.ajax({
        url: getContextPath() + "/uuid",
        success: function (data) {
            var addsfxmid = data;
            var trELArray = $(elem).parents('.bdcsfxxForm').find(".bdcSfxxTable").find("tr");
            if (trELArray.length > 1 && $(elem).parents('.bdcsfxxForm').find(".bdcSfxxTable").find("tr[class='bdc-table-none']").length > 0) {
                isFirst = true;
                $(elem).parents('.bdcsfxxForm').find(".bdcSfxxTable").find("tr[class='bdc-table-none']").remove();
            }
            var sfxmNumber = $(elem).parents('.bdcsfxxForm').find(".bdcSfxxTable").find("tr").length - 1;
            var sfxmmcOption = "";
            if (qlrlb === "1") {
                if (qlrsfxmpzList !== null && qlrsfxmpzList.length > 0) {
                    for (var i = 0; i < qlrsfxmpzList.length; i++) {
                        var bdcsfxmPz = qlrsfxmpzList[i];
                        sfxmmcOption += '<option value="' + bdcsfxmPz.sfxmdm + '">' + bdcsfxmPz.sfxmmc + '</option>';
                    }
                }
            }
            if (qlrlb === "2") {
                if (ywrsfxmpzList !== null && ywrsfxmpzList.length > 0) {
                    for (var i = 0; i < ywrsfxmpzList.length; i++) {
                        var bdcsfxmPz = ywrsfxmpzList[i];
                        sfxmmcOption += '<option value="' + bdcsfxmPz.sfxmdm + '">' + bdcsfxmPz.sfxmmc + '</option>';
                    }
                }
            }
            var jedwOption = "";
            if (zdList.jedw !== null && zdList.jedw.length > 0) {
                for (var i = 0; i < zdList.jedw.length; i++) {
                    var jedw = zdList.jedw[i];
                    if (jedw.MC === "元") {
                        jedwOption += '<option value="' + jedw.DM + '" selected="selected">' + jedw.MC + '</option>';
                    } else {
                        jedwOption += '<option value="' + jedw.DM + '">' + jedw.MC + '</option>';
                    }
                }
            }
            var sfxxidtr;
            var sfxmtr;
            var sfxmbztr;
            if (qlrlb === "1") {
                sfxxidtr = "<input type=\"hidden\"  name=\"sfxxid\" class=\"sfxm\" value='" + qlrsfxxid + "'>";
                sfxmtr = "<div class=\"bdc-td-box layui-form\" lay-filter=\"sfxmmc1\"><select name=\"sfxmdm\" lay-filter=\"sfxmdm1\" class=\"sfxm\">";
                sfxmbztr = "<div class=\"bdc-td-box layui-form\" lay-filter=\"sfxmbz11\"><select name=\"sfxmbz\"  lay-filter=\"sfxmbz1\" class=\"sfxm\">";
            }
            if (qlrlb === "2") {
                sfxxidtr = "<input type=\"hidden\"  name=\"sfxxid\" class=\"sfxm\" value='" + ywrsfxxid + "'>";
                sfxmtr = "<div class=\"bdc-td-box layui-form\" lay-filter=\"sfxmmc2\"><select name=\"sfxmdm\" lay-filter=\"sfxmdm2\" class=\"sfxm\">";
                sfxmbztr = "<div class=\"bdc-td-box layui-form\" lay-filter=\"sfxmbz12\"><select name=\"sfxmbz\"  lay-filter=\"sfxmbz2\" class=\"sfxm\">";
            }
            var htmlStr = "<tr>" +
                sfxxidtr +
                "<input type=\"hidden\"  name=\"sfxmid\" class=\"sfxm\" value='" + addsfxmid + "'>" +
                "<input type=\"hidden\"  name=\"sfxmdj\" class=\"sfxm bdc-sfxmdj\" >\n" +
                "<input type=\"hidden\"  name=\"sfxmmc\" class=\"sfxm\" >\n" +
                "<input type=\"hidden\"  name=\"xh\" class=\"sfxm\" value='" + sfxmNumber + "'>" +
                "<input type=\"hidden\"  name=\"qlrlb\" class=\"sfxm\" value='" + qlrlb + "'>" +
                "<input type=\"hidden\"  name=\"cz\" class=\"sfxm\" value='insert'>" +
                "<td>" + sfxmNumber + "</td>" +
                "<td>\n" +
                "\n" +
                sfxmtr +
                "<option value=\"\">请选择</option>\n" +
                sfxmmcOption +
                "</select>\n" +
                "</div>\n" +
                "</td>" +
                "<td>\n" +
                "\n" +
                sfxmbztr +
                "<option value=\"\">请选择</option>\n" +
                "</select>\n" +
                "</div>\n" +
                "</td>" +
                "<td><div class=\"bdc-td-box\"><input type=\"number\" lay-verify=\"\" onkeyup=\"value=zhzs(this.value)\"  name=\"sl\" oninput=\"changeSl(this)\" class=\"sfxm\"></div></td>\n" +
                "<td>\n" +
                "<div class=\"bdc-td-box\">\n" +
                "<select  name=\"jedw\" class=\"sfxm\">\n" +
                "<option value=\"\"></option>\n" +
                jedwOption +
                "</select>\n" +
                "</div>\n" +
                "</td>" +
                "<td><div class=\"bdc-td-box\"><input type=\"number\" lay-verify=\"\"  name=\"ysje\" oninput=\"countMoney(this)\" class=\"sfxm bdc-ysje\"></div></td>\n" +

                "<td><div class=\"bdc-td-box\"><input type=\"text\" lay-verify=\"\"  name=\"jmje\" onkeyup=\"value=value.replace(/[^\\d{1,}\\.\\d{1,}|\\d{1,}]/g,'')\" oninput=\"changeJmje(this)\" class=\"layui-input bdc-jmje sfxm edit-sfxx\"></div></td>\n" +
                "<td><div class=\"bdc-td-box bdc-one-icon\"><input type=\"text\" name=\"ssje\" value=\"\" autocomplete=\"off\" class=\"layui-input bdc-ssje sfxm\" disabled=\"off\">\n</div></td>\n" +

                "<td align=\"center\">\n" +
                "<span class=\"layui-btn layui-btn-xs layui-btn-danger bdc-delete-btn\" onclick=\"saveAndDeleteSfxm('" + addsfxmid + "','" + qlrlb + "')\">删除</span>\n" +
                "</td></tr>";
            layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
                var form = layui.form;
                if (isFirst) {
                    $(elem).parents('.bdcsfxxForm').find(".bdcSfxxTable").find("thead").append(htmlStr);
                } else {
                    $(elem).parents('.bdcsfxxForm').find(".bdcSfxxTable").append(htmlStr);
                }
                form.render();
                disabledAddFa();
            })
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

//查询收费状态
function querySfzt(sfxxid) {
    addModel();
    // 触发三要素查询
    querySwsys(processInsId);
}

function saveSfxx(elem) {
    var bdcsfxxForm;
    var qlrsfxx = $('#qlrsfxx');
    var ywrsfxx = $('#ywrsfxx');
    var hyzfjftj = $("#hyzfjftj").val();
    var hyzfjflx = $("#hyzfjflx").val();
    if (qlrsfxx.length > 0 || ywrsfxx.length > 0) {
        if (elem) {
            bdcsfxxForm = $(elem).parents('.bdcsfxxForm');
        } else {
            bdcsfxxForm = $(".bdcsfxxForm");
        }
        bdcsfxxForm.each(function () {
            var bdcsfxx = {};
            var bdcSfxxArray = $(this).find(".sfxx").serializeArray();
            if (bdcSfxxArray !== null && bdcSfxxArray.length > 0) {
                bdcSfxxArray.forEach(function (item, index) {
                    bdcsfxx[item.name] = item.value;
                })
            }
            bdcsfxx.hyzfjftj = hyzfjftj;
            bdcsfxx.hyzfjflx = hyzfjflx;
            bdcsfxx.hj = $(this).find("#hj").val();
            if (isNotBlank(bdcsfxx.sfxxid)) {
                //保存收费信息
                $.ajax({
                    url: getContextPath() + "/sf/xx",
                    type: 'PATCH',
                    dataType: 'json',
                    async: false,
                    contentType: "application/json",
                    data: JSON.stringify(bdcsfxx),
                    success: function (data) {
                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
            }
        });
    }
}

/**
 * @param elem -当前点击的输入框
 * @param reload - 是否重新加载
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description
 * @date : 2020/7/9 11:21
 */
function saveSfxm(elem, reload) {
    var sfxmList = [];
    var bdcsfxxForm;
    if (elem) {
        bdcsfxxForm = $(elem).parents('.bdcsfxxForm');
    } else {
        bdcsfxxForm = $(".bdcsfxxForm");
    }
    bdcsfxxForm.each(function () {
        //收费项目保存
        $(this).find(".bdcSfxxTable tr").each(function (index, sfxmtr) {
            var sfxmArray = $(this).find(".sfxm").serializeArray();
            if (sfxmArray !== null && sfxmArray.length > 0) {
                var sfxm = {};
                sfxmArray.forEach(function (item, index) {
                    sfxm[item.name] = item.value;
                });
                //应收金额在页面控制不可编辑单独处理
                sfxm.ysje = $(sfxmtr).find("input[name=ysje]").val();
                sfxm.ssje = $(sfxmtr).find("input[name=ssje]").val();
                if (sfxm.sfxmmc !== null && sfxm.sfxmmc !== "") {
                    sfxmList.push(sfxm);
                }
            }
        });
    });
    if (isNotBlank(sfxmList)) {
        $.ajax({
            url: getContextPath() + "/sf/xm",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(sfxmList),
            success: function (data) {
                isFirst = false;
                if (!reload) {
                    loadSfxm(qlrsfxxid, "1", false);
                    loadSfxm(ywrsfxxid, "2", false);
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }
}

function saveAndDeleteSfxm(sfxmid, qlrlb) {
    if (isNotBlank(sfxmid)) {
        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '是否确认删除？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                //确定操作
                addModel();
                setTimeout(function () {
                    try {
                        $.when(saveSfxm()).done(function () {
                            setTimeout(function () {
                                try {
                                    $.when(deletesfxm(sfxmid, qlrlb)).done(function () {
                                        removeModal();
                                        ityzl_SHOW_SUCCESS_LAYER("删除成功");
                                    })
                                } catch (e) {
                                    removeModal();
                                    ERROR_CONFIRM("删除失败", e.message);
                                    return
                                }
                            }, 10);
                        })
                    } catch (e) {
                        removeModal();
                        ERROR_CONFIRM("删除失败", e.message);
                        return
                    }
                }, 10);
                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
                layer.close(deleteIndex);
            }
        });
    }
}

function deletesfxm(sfxmid, qlrlb) {
    $.ajax({
        url: getContextPath() + "/sf/xm?sfxmid=" + sfxmid,
        async: false,
        type: 'DELETE',
        success: function (data) {
            if (qlrlb === "1") {
                loadSfxm(qlrsfxxid, qlrlb, false);
            }
            if (qlrlb === "2") {
                loadSfxm(ywrsfxxid, qlrlb, false);
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
            return;
        }
    });
}

function saveAndchangeSfxmSxh(sfxmid, czlx) {
    addModel();
    setTimeout(function () {
        try {
            $.when(saveSfxm()).done(function () {
                setTimeout(function () {
                    try {
                        $.when(changeSfxmSxh(sfxmid, czlx)).done(function () {
                            removeModal();
                        })
                    } catch (e) {
                        ERROR_CONFIRM("更换失败", e.message);
                        removeModal();
                        return
                    }
                }, 10);
            })
        } catch (e) {
            removeModal();
            ERROR_CONFIRM("更换失败", e.message);
            return
        }
    }, 10);
}

function changeSfxmSxh(sfxmid, czlx) {
    $.ajax({
        url: getContextPath() + "/sf/sxh",
        type: 'get',
        async: false,
        dataType: 'json',
        data: {sfxmid: sfxmid, czlx: czlx},
        success: function (data) {
            if (data > 0) {
                loadSfxm(slsfxxid);
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function generateSfJbxx(bdcSlSfxxDTO) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {
            bdcSlSfxxDTO: bdcSlSfxxDTO,
            zd: zdList
        };
        var jbxxtpl = jbxxTpl.innerHTML, jbxxview = document.getElementById('jbxx');
        laytpl(jbxxtpl).render(json, function (html) {
            jbxxview.innerHTML = html;
        });
        form.render();
        renderSelect();
        form.render('select');
        disabledAddFa();
    });
}

/**
 * 加载收费信息时，一窗查询税务三要素信息
 * @param gzlslid
 */
function querySwsys(gzlslid) {
    $.ajax({
        url: getContextPath() + "/slym/sw/swsys/hsxx",
        type: 'get',
        data: {gzlslid: gzlslid},
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("查询税务三要素信息，查询结果：" + data);
            console.log("查询税务三要素信息，查询结果：" + data);
        }, error: function (xhr, status, error) {
            removeModal();
            console.log("查询税务三要素信息，查询异常！" + xhr);
        }
    });
}

function titleShowUiSfxx() {
    $(".print-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#moreBtn").hide();
        $("#print").show();
        setUiWidth($(this), $("#print"));
    });

    $(".more-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#moreBtn").show();
        setUiWidth($(this), $("#more-btn"));
    });

    $(".print-btn li").click(function () {
        $("#print").hide();
    });

    $(".more-btn li").click(function () {
        $("#moreBtn").hide();
    });
    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && (elem.id == 'print' || elem.id == 'moreBtn')) {
                return;
            }
            $("#print").hide();
            $("#moreBtn").hide();
            elem = elem.parentNode;
        }
    });
}

function setUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    uiElement.offset({top: X + 40, left: Y - 27});
    var uiWidth = 90;
    var liArray = uiElement.find("li");
    if (liArray.length > 0) {
        var textNumber = 0;
        for (var i = 0; i < liArray.length; i++) {
            var liEl = liArray[i];
            var liText = $(liEl).text();
            if (textNumber < liText.length) {
                textNumber = liText.length;
            }
        }
        var countWidth = (textNumber + 3) * 13;
        if (uiWidth < countWidth) {
            uiWidth = countWidth;
        }
    }
    uiElement.width(uiWidth);
}

function recount() {
//重新计算，不管数据库是否有数据，根据当前项目信息重新计算
    getReturnData("/sf/recount", {gzlslid: processInsId, xmid: xmid}, "GET", function (data) {
        querySfxx(false);
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    }, false);
    removeModal();
}

function queryXwlx(sfxxid, qlrlb) {
    addModel('查询中');
    getReturnData("/sf/xwlx", {
        sfxxid: sfxxid,
        gzlslid: processInsId,
        qlrlb: qlrlb,
        xmid: xmid
    }, "GET", function (data) {
        removeModal();
        if (data) {
            $('#xwlx' + qlrlb).val(data);
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

// 修改收费状态
function tswwjf() {
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/modifySfzt",
        type: 'GET',
        dataType: 'json',
        data: {
            gzlslid: processInsId
        },
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("修改成功！");
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

// 维修资金查询
function wxzjcx(xmid, gzlslid) {
    addModel();
    $.ajax({
        url: getContextPath() + "/wxzj/wxzjcx",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid},
        success: function (data) {
            removeModal();
            if (isNotBlank(data) && data.code == "00000") {
                var ddxx = data.data;
                ddxx.xmid = xmid;
                ddxx.gzlslid = gzlslid;
                showWxzjxx(ddxx);
            } else {
                ityzl_SHOW_WARN_LAYER("未查询到维修资金信息。");
            }

        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

// 展示维修资金信息
function showWxzjxx(data) {
    layer.open({
        title: '维修资金',
        type: 1,
        skin: 'bdc-spf-layer',
        area: ['430px', '250px'],
        btn: ['确认缴费', '取消'],
        content: $('#bdc-popup-small'),
        yes: function (index, layero) {
            var param = {
                xmid: data.xmid,
                gzlslid: data.gzlslid,
                hj: data.yyje,
                ddbh: data.ddbh
            }
            scddxx(param).done(function () {
                layer.close(index);
                querySfxx();
            });
        }, btn2: function (index, layero) {
            //取消 的回调
        }, success: function () {
            $("#ddbh").val(data.ddbh);
            $("#ysje").val(data.yyje);
            $("#ddsxsj").val(data.ddsxsj);
        }
    });
}

// 确认缴费，创建订单
function scddxx(data) {
    var deferred = $.Deferred();
    addModel();
    $.ajax({
        url: getContextPath() + "/wxzj/scwxzjxx",
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(data),
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("已添加维修资金缴费信息！");
            deferred.resolve();
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
            deferred.reject();
        }
    });
    return deferred;
}

// 通知维修资金
function tzwxzj() {
    addModel();
    $.ajax({
        url: getContextPath() + "/wxzj/tzwxzj",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid, gzlslid: processInsId},
        success: function (data) {
            removeModal();
            if (isNotBlank(data) && data.code == "00000") {
                ityzl_SHOW_SUCCESS_LAYER("通知维修资金成功");
            } else {
                ityzl_SHOW_WARN_LAYER("通知维修资金失败," + data.msg);
            }
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

/**
 * 非税开票
 */
function fskpFun() {
    layer.open({
        title: '收费信息列表',
        type: 2,
        area: ['80%', '80%'],
        content: '/realestate-accept-ui/view/sfxx/fskpList.html?processInsId=' + processInsId
        , cancel: function () {
        }
        , success: function (layero, index) {
        }
        , end: function () {
        }
    });

}

//生成二维码
function generateEwm(sfxxid, jfsewmurl) {
    if (isNotBlank(jfsewmurl)) {
        ityzl_SHOW_WARN_LAYER("已生成收费二维码");
        return false;
    }
    addModel();
    getReturnData("/sf/xx/tsdjfxx?sfxxid=" + sfxxid, {}, "POST", function (data) {
        removeModal();
        querySfxx();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

function imgShow(outerdiv, innerdiv, bigimg, _this) {
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性

    /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function () {
        var windowW = $(window).width();//获取当前窗口宽度
        var windowH = $(window).height();//获取当前窗口高度
        var realWidth = this.width;//获取图片真实宽度
        var realHeight = this.height;//获取图片真实高度
        var imgWidth, imgHeight;
        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

        if (realHeight > windowH * scale) {//判断图片高度
            imgHeight = windowH * scale;//如大于窗口高度，图片高度进行缩放
            imgWidth = imgHeight / realHeight * realWidth;//等比例缩放宽度
            if (imgWidth > windowW * scale) {//如宽度扔大于窗口宽度
                imgWidth = windowW * scale;//再对宽度进行缩放
            }
        } else if (realWidth > windowW * scale) {//如图片高度合适，判断图片宽度
            imgWidth = windowW * scale;//如大于窗口宽度，图片宽度进行缩放
            imgHeight = imgWidth / realWidth * realHeight;//等比例缩放高度
        } else {//如果图片真实高度和宽度都符合要求，高宽不变
            imgWidth = realWidth;
            imgHeight = realHeight;
        }
        $(bigimg).css("width", imgWidth);//以最终的宽度对图片缩放

        var w = (windowW - imgWidth) / 2;//计算图片与窗口左边距
        var h = (windowH - imgHeight) / 2;//计算图片与窗口上边距
        $(innerdiv).css({"top": h, "left": w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function () {//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}

//按钮加载
function loadButtonArea(ymlx) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var gzlslid = getQueryString("processInsId");
        var zxlc = getQueryString("zxlc");

        var qlxx = null;
        getReturnData("/slym/ql/qlmc", {gzlslid: gzlslid, zxlc: zxlc}, 'GET', function (data) {
            if (isNotBlank(data)) {
                qlxx = data;
            }
        }, function (err) {
            console.log(err);
        }, false);

        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {qlxx: qlxx, printBtn: slymPrintBtn};
        var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        getStateById(readOnly, formStateId, ymlx);
        disabledAddFa();

        titleShowUiSfxx();
        loadBtn();

    });
}


/*
* 一下内容为蚌埠版本 参照合肥一卡清接口修改 ----------------------------------------------------------------------------------------------------------------------
*
*
* */

// 通知政融平台
function tzzrpt() {
    checkPosJfxx().done(function () {
        requestZrpt();
    }).fail(function (data) {
        if (isNotBlank(data)) {
            $("#bdc-pos-bcxx").html("");
            var laytpl = layui.laytpl, form = layui.form;
            var tpl = posBcxxTpl.innerHTML, view = document.getElementById('bdc-pos-bcxx');
            laytpl(tpl).render(data, function (html) {
                view.innerHTML = html;
            });
            form.render();
            layer.open({
                title: '补录订单信息',
                type: 1,
                skin: 'bdc-spf-layer',
                area: ['960px', '360px'],
                btn: ['确认', '取消'],
                content: $('#bdc-pos-bcxx'),
                yes: function (index, layero) {
                    var data = $(".bcxx").serializeArray();
                    var ddxxList = [], ddxx = {}, i = 1;
                    var verify = true;
                    $.each(data, function (index, val) {
                        if (isNullOrEmpty(val.value)) {
                            verify = false;
                            ityzl_SHOW_WARN_LAYER('存在数据为空,请补充完整订单信息。');
                            return false;
                        }
                        ddxx[val.name] = val.value;
                        if (i % 4 == 0) {
                            ddxxList.push(ddxx);
                            ddxx = {};
                        }
                        i++;
                    });
                    if (verify) {
                        console.info(ddxxList);
                        $.ajax({
                            url: getContextPath() + "/sfss/ddxx/batchModify",
                            type: 'POST',
                            dataType: 'json',
                            contentType: "application/json",
                            data: JSON.stringify(ddxxList),
                            success: function (data) {
                                layer.close(index);
                                requestZrpt();
                            },
                            error: function (xhr, status, error) {
                                delAjaxErrorMsg(xhr)
                            }
                        });
                    }
                },
                btn2: function (index, layero) {
                    //取消 的回调
                }
            });
        }
    });
}


// 验证Pos缴费终端号、参考号、商户代码信息
function checkPosJfxx() {
    var deferred = $.Deferred();
    addModel();
    $.ajax({
        url: getContextPath() + "/sfss/ddxx?gzlslid=" + processInsId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            removeModal();
            if (isNotBlank(data)) {
                var vaildData = [];
                $.each(data, function (index, val) {
                    if (isNotBlank(val.zdh) && isNotBlank(val.ckh) && isNotBlank(val.shdm)) {
                        return true;
                    }
                    vaildData.push(val);
                });
                if (isNotBlank(vaildData)) {
                    deferred.reject(vaildData);
                } else {
                    deferred.resolve();
                }
            } else {
                ityzl_SHOW_WARN_LAYER("未获取到订单信息");
                deferred.reject();
            }
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
            deferred.reject();
        }
    });
    return deferred;
}

// 请求通知政融平台接口
function requestZrpt() {
    var url = getContextPath() + "/sfss/ddxx/gxDdxxByGzlslid";
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        contentType: "application/json",
        data: {
            gzlslid: processInsId
        },
        success: function (data) {
            console.info(data);
            if (data && data.resultCode == "1") {
                ityzl_SHOW_SUCCESS_LAYER("通知政融平台已支付成功。");
            } else {
                ityzl_SHOW_WARN_LAYER("通知政融平台已支付失败。");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}


//查询退款结果
function queryTkjg() {
    addModel();
    $.ajax({
        url: getContextPath() + "/sfss/ddxx/querySfxxTkqk",
        type: 'get',
        dataType: 'json',
        data: {gzlslid: processInsId},
        success: function (data) {
            if (data && isNotBlank(data.sfzt)) {
                var sfztMc = changeDmToMc(data.sfzt, zdList.sfzt);
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("查询成功,退款状态为" + sfztMc);
                querySfxx();
            } else {
                removeModal();
                ityzl_SHOW_WARN_LAYER("未查询到结果");
            }

        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}


//退款申请
function sfTksq() {
    // showAlertDialog("确认退款");
    var index = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: "退款申请",
        area: ['320px'],
        content: "是否确认退款？",
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            addModel();
            //先查询订单信息
            getReturnData("/sfss/ddxx/ykq/ddxx", {gzlslid: processInsId}, "GET", function (data) {
                if (data && data.length > 0) {
                    var bdcGzYzQO = {};
                    bdcGzYzQO.zhbs = "SFXX_TKSQ";
                    var gzyzParamMap = {};
                    gzyzParamMap.gzlslid = processInsId;
                    bdcGzYzQO.paramMap = gzyzParamMap;
                    gzyzCommon(2, bdcGzYzQO, function (data) {
                        console.log("验证通过");
                        $.ajax({
                            url: getContextPath() + "/sfss/ddxx/tksq",
                            type: 'get',
                            dataType: 'json',
                            data: {gzlslid: processInsId},
                            success: function (data) {
                                removeModal();
                                if (data && data[0] != null && isNotBlank(data[0].tkdh)) {
                                    ityzl_SHOW_SUCCESS_LAYER("操作成功");
                                    querySfxx();
                                } else {
                                    ityzl_SHOW_WARN_LAYER("操作失败");
                                }
                            },
                            error: function (xhr, status, error) {
                                delAjaxErrorMsg(xhr)
                            }
                        });

                    });
                } else {
                    removeModal();
                    ityzl_SHOW_WARN_LAYER("未查询到订单信息");
                }

            }, function (error) {
                delAjaxErrorMsg(error);

            })
            layer.close(index);
        },
        btn2: function () {
            //取消
            layer.close(index);
        }
    });

}


// 调用接口推送收费缴库
function tsSfjk() {
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/tsjk",
        type: 'get',
        dataType: 'json',
        data: {gzlslid: processInsId, sfyj: 0},
        success: function (data) {
            removeModal();
            console.log(data);
            if (data && data.statusCode === "0000") {
                ityzl_SHOW_SUCCESS_LAYER("推送成功");
            } else {
                ityzl_SHOW_SUCCESS_LAYER("推送失败" + data.msg);
            }

            querySfxx();
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

function queryJfzt() {
    addModel();
    $.ajax({
        url: getContextPath() + "/sfss/ddxx/sfzt",
        type: 'get',
        dataType: 'json',
        data: {gzlslid: processInsId},
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("获取缴费状态成功");
            querySfxx();
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}


/**
 * POS缴费拆分页面弹窗
 */
function posjf() {
    var jkfs = $("#hyzfjftj").val();
    getReturnData("/sfss/ddxx", {gzlslid: processInsId}, "GET", function (data) {
        if (!isNotBlank(data)) {
            var index = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: "POS缴费",
                area: ['320px'],
                content: "是否要拆单缴费？",
                btn: ['合并缴费', '拆单缴费'],
                btnAlign: 'c',
                yes: function () {
                    hbjf(jkfs);
                    layer.close(index);
                },
                btn2: function () {
                    //取消
                    cfddjf(jkfs);
                    layer.close(index);
                }
            });

        } else {
            //当缴款方式为合一支付时
            if (jkfs === "2") {
                if (isNotBlank(data)) {
                    if (data[0].jfurl) {
                        var url = "/realestate-accept-ui/sf/ewm?ewmnr=" + encodeURIComponent(data[0].jfurl);
                        $(".qlrywrzfewm").attr('src', url);
                        //点击二维码图片放大事件
                        $("#hyzf-ewm").show();
                        $(".qlrywrzfewm").click(function () {
                            var _this = $(this);//将当前的pimg元素作为_this传入函数
                            imgHyzfShow("#hyzfouterdiv", "#hyzfinnerdiv", "#hyzfbigimg", _this, $(window).width() * 0.25, $(window).width() * 0.5);
                        });
                        $(".qlrywrzfewm").click();
                    }
                } else {
                    ityzl_SHOW_WARN_LAYER("已生成缴费订单信息")
                }
                return;
            }
            var url = "/realestate-accept-ui/view/slym/posjf.html?processInsId=" + processInsId + "&xmid=" + xmid;
            layerCommonOpenFull(url, "POS缴费页面", '1150px', '600px');

        }


    }, function (error) {
        delAjaxErrorMsg(error);
    });

}

//合并缴费
function hbjf(jkfs) {
    addModel();
    getReturnData("/sfss/ddxx/scddxx/hbjf?jkfs=" + jkfs, {gzlslid: processInsId}, "GET", function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("创建成功。");
        //当缴款方式为合一支付时
        if (jkfs === "2") {
            if (isNotBlank(data)) {
                if (data[0].jfurl) {
                    var jfurl = "/realestate-accept-ui/sf/ewm?ewmnr=" + encodeURIComponent(data[0].jfurl);
                    $(".qlrywrzfewm").attr('src', jfurl);
                    //点击二维码图片放大事件
                    $("#hyzf-ewm").show();
                    $(".qlrywrzfewm").click(function () {
                        var _this = $(this);//将当前的pimg元素作为_this传入函数
                        imgHyzfShow("#hyzfouterdiv", "#hyzfinnerdiv", "#hyzfbigimg", _this, $(window).width() * 0.25, $(window).width() * 0.5);
                    });
                    $(".qlrywrzfewm").click();
                } else {
                    ityzl_SHOW_WARN_LAYER("已生成缴费订单信息");
                }
            }
            return;
        }
        var url = "/realestate-accept-ui/view/slym/posjf.html?processInsId=" + processInsId;
        layerCommonOpenFull(url, "POS缴费页面", '1150px', '600px');

    }, function (error) {
        removeModal();
        delAjaxErrorMsg(error);
    })


}

//拆单缴费
function cfddjf(jkfs) {
    var index = layer.open({
        type: 2,
        area: ['960px', '600px'],
        fixed: false, //不固定
        title: "缴费明细总额拆分",
        maxmin: true,
        content: "/realestate-accept-ui/view/sfxx/sfxxSplit.html?processInsId=" + processInsId + "&xmid=" + xmid + "&jkfs=" + jkfs,
        btnAlign: "c",
        success: function () {
        },
        cancel: function () {
        }
    });

}

//合一支付
function hyzfFun() {
//返回值生成二维码展现
    addModel();
    //支持规则验证
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = "SFXX_HYZF";
    var gzyzParamMap = {};
    gzyzParamMap.gzlslid = processInsId;
    bdcGzYzQO.paramMap = gzyzParamMap;
    gzyzCommon(2, bdcGzYzQO, function (data) {
        getReturnData("/sf/hyzf/ewm", {gzlslid: processInsId, xmid: xmid, djxl: djxl}, "GET", function (data) {
            removeModal();
            //展现返回的二维码
            if (data) {
                console.log(data);
                if (data.msg) {
                    ityzl_SHOW_WARN_LAYER(data.msg);
                    return false;
                }
                if (data.qlrHyzfUrl) {
                    var url = "/realestate-accept-ui/sf/ewm?ewmnr=" + encodeURIComponent(data.qlrHyzfUrl);
                    $(".qlrzfewm").attr('src', url);
                    //点击二维码图片放大事件
                    $("#hyzf-ewm").show();
                    $(".qlrzfewm").click(function () {
                        var _this = $(this);//将当前的pimg元素作为_this传入函数
                        imgHyzfShow("#hyzfqlrouterdiv", "#hyzfqlrinnerdiv", "#hyzfqlrbigimg", _this);
                    });
                    $(".qlrzfewm").click();
                }
                if (data.ywrHyzfUrl) {
                    var url = "/realestate-accept-ui/sf/ewm?ewmnr=" + encodeURIComponent(data.ywrHyzfUrl);
                    $(".ywrzfewm").attr('src', url);
                    //点击二维码图片放大事件
                    $("#hyzf-ewm").show();
                    $(".ywrzfewm").click(function () {
                        var _this = $(this);//将当前的pimg元素作为_this传入函数
                        imgHyzfShow("#hyzfywrouterdiv", "#hyzfywrinnerdiv", "#hyzfywrbigimg", _this, "", $(window).width() * 0.6);
                    });
                    $(".ywrzfewm").click();
                }
                if (data.hyzfUrl) {
                    var url = "/realestate-accept-ui/sf/ewm?ewmnr=" + encodeURIComponent(data.hyzfUrl);
                    $(".qlrywrzfewm").attr('src', url);
                    //点击二维码图片放大事件
                    $("#hyzf-ewm").show();
                    $(".qlrywrzfewm").click(function () {
                        var _this = $(this);//将当前的pimg元素作为_this传入函数
                        imgHyzfShow("#hyzfouterdiv", "#hyzfinnerdiv", "#hyzfbigimg", _this, $(window).width() * 0.25, $(window).width() * 0.5);
                    });
                    $(".qlrywrzfewm").click();
                }
            }
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        })
    });
}

//合一支付状态查询
function hyzfztcxFun() {
    addModel();
    getReturnData("/sf/hyzf/zfzt", {gzlslid: processInsId, xmid: xmid}, "GET", function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("查询成功");
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

/*
* 点击图片放大功能
* */

function imgHyzfShow(outerdiv, innerdiv, bigimg, _this, height, width) {
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性

    /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function () {
        var windowW = $(window).width();//获取当前窗口宽度
        var windowH = $(window).height();//获取当前窗口高度
        var realWidth = this.width;//获取图片真实宽度
        var realHeight = this.height;//获取图片真实高度
        var imgWidth, imgHeight;
        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

        if (realHeight > windowH * scale) {//判断图片高度
            imgHeight = windowH * scale;//如大于窗口高度，图片高度进行缩放
            imgWidth = imgHeight / realHeight * realWidth;//等比例缩放宽度
            if (imgWidth > windowW * scale) {//如宽度扔大于窗口宽度
                imgWidth = windowW * scale;//再对宽度进行缩放
            }
        } else if (realWidth > windowW * scale) {//如图片高度合适，判断图片宽度
            imgWidth = windowW * scale;//如大于窗口宽度，图片宽度进行缩放
            imgHeight = imgWidth / realWidth * realHeight;//等比例缩放高度
        } else {//如果图片真实高度和宽度都符合要求，高宽不变
            imgWidth = realWidth;
            imgHeight = realHeight;
        }
        $(bigimg).css("width", imgWidth);//以最终的宽度对图片缩放

        var w = (windowW * 0.4) / 2;//计算图片与窗口左边距
        var h = (windowH * 0.5) / 2;//计算图片与窗口上边距
        if (isNotBlank(width)) {
            w = width;
        }
        if (isNotBlank(height)) {
            h = height;
        }
        $(innerdiv).css({"top": h, "left": w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function () {//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}

//修改减免金额
function changeJmje(elem) {
    var $parentTr = $(elem).parents('tr');
    var $parentTable = $(elem).parents('table');
    var getYsje = $parentTr.find('.bdc-ysje').val();
    $parentTr.find('.bdc-ysje-input').val(getYsje);
    var jmje = $(elem).val();
    //实收金额
    var $ssjeEl = $parentTr.find(".bdc-ssje");
    $ssjeEl.val(Subtr(parseFloat(getYsje), jmje));
    $parentTr.find('.bdc-ssje-input').val(Subtr(parseFloat(getYsje), jmje));
    //合计
    computeTotal($parentTable);
}

//获取总计
function computeTotal($table) {
    var hj = 0;
    var $ssjeList = $table.find('.bdc-ssje');
    $ssjeList.each(function (i) {
        hj += parseFloat($($ssjeList[i]).val());
    });
    $table.find('.bdc-hj').val(calculateFloat(hj));
}

//减法
function Subtr(arg1, arg2) {
    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length
    } catch (e) {
        r1 = 0
    }
    try {
        r2 = arg2.toString().split(".")[1].length
    } catch (e) {
        r2 = 0
    }
    m = Math.pow(10, Math.max(r1, r2));
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}
