layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
//页面入口
var zdList;
var ysfxmPzList;
var sfxmPzList;
var qlrsfxmpzList = [];
var ywrsfxmpzList = [];
var yqlrsfxmpzList = [];
var yywrsfxmpzList = [];
var changegbf = false;
var xmid = getQueryString("xmid");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var printValue = getQueryString("print");
var zxlc = getQueryString("zxlc")
sessionStorage.removeItem("yqlrsfxmpzList");
sessionStorage.removeItem("yywrsfxmpzList");
var sfymlx = getQueryString("sfymlx");
var processInsId = getQueryString("processInsId");
var form;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    addModel();
    setTimeout("loadButtonArea('sfxx')", 10);
    setTimeout(function () {
        try {
            $.when(loadData(), loadSfxmPz()).done(function () {
                $.when(querySfxx(false)).done(function () {
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
                        //保存完之后统一保存合计
                        saveHj();
                        // 保存之后同步收费信息收费状态
                        syncSfxxSfzt();
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

    form.on("select(jkfs)", function (data) {
        var sfxxid = $(data.elem).parents(".bdcsfxxForm").find("input[name='sfxxid']").val();
        var qlrlb = $(data.elem).parents(".bdcsfxxForm").find("input[name='qlrlb']").val();
        if(isNotBlank(data.value) && isNotBlank(sfxxid)){
            if(data.value != '1'){
                // 更新缴款状态为已缴费
                changeJkfsModifySfzt(sfxxid, 2, qlrlb, data.value);
            }else{
                changeJkfsModifySfzt(sfxxid, 1, qlrlb, data.value);
            }
        }else{
            ityzl_SHOW_WARN_LAYER("未获取到收费信息ID.");
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
    var url = getContextPath() + "/sf/xm/pz/jmzc";
    if (sfymlx === "ycsl") {
        url = getContextPath() + "/sf/xm/pz/ycsl";
    }
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {gzlslid: processInsId, xmid: xmid},
        success: function (data) {
            if (isNotBlank(data)) {
                sfxmPzList = data.jmsfxmpz;
                ysfxmPzList = data.ysfxmpz;
                if (ysfxmPzList.length > 0) {
                    for (var j = 0; j < ysfxmPzList.length; j++) {
                        if (ysfxmPzList[j].qlrlb === "1") {
                            yqlrsfxmpzList.push(ysfxmPzList[j]);
                        }
                        if (ysfxmPzList[j].qlrlb === "2") {
                            yywrsfxmpzList.push(ysfxmPzList[j]);
                        }
                    }
                    sessionStorage.setItem('yqlrsfxmpzList', JSON.stringify(yqlrsfxmpzList));
                    sessionStorage.setItem('yywrsfxmpzList', JSON.stringify(yywrsfxmpzList));
                }
                if (sfxmPzList.length > 0) {
                    for (var i = 0; i < sfxmPzList.length; i++) {
                        if (sfxmPzList[i].qlrlb === "1") {
                            qlrsfxmpzList.push(sfxmPzList[i]);
                        }
                        if (sfxmPzList[i].qlrlb === "2") {
                            ywrsfxmpzList.push(sfxmPzList[i]);
                        }
                    }
                } else {
                    qlrsfxmpzList = JSON.parse(sessionStorage.getItem('yqlrsfxmpzList'));
                    ywrsfxmpzList = JSON.parse(sessionStorage.getItem('yywrsfxmpzList'));
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

//退款申请
function sfTksq(sfxxid) {
    addModel();
    getReturnData("/rest/v1.0/cz/sfxx/tksq", {sfxxid: sfxxid, sftdsyj: true}, "GET", function (data) {
        removeModal();
        if (data) {
            if (data.orderStatus === "1") {
                ityzl_SHOW_SUCCESS_LAYER("退款成功");
                querySfxx();
            } else {
                ityzl_SHOW_WARN_LAYER("退款失败");
            }
        } else {
            ityzl_SHOW_WARN_LAYER("退款失败");
        }
    }, function (error) {
        delAjaxErrorMsg(error);

    })
}

var slsfxxid;
var qlrsfxxid;
var ywrsfxxid;
var isFirst;
var qllx;
var qlrsfxmList = [];
var ywrsfxmList = [];

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

                //不存在权利人义务人收费信息默认展示权利人收费信息
                if (data.bdcSlQlrSfxxDTO != null && !isNotBlank(data.bdcSlQlrSfxxDTO.qlrlb) && data.bdcSlYwrSfxxDTO != null && !isNotBlank(data.bdcSlYwrSfxxDTO.qlrlb)) {
                    data.bdcSlQlrSfxxDTO.qlrlb = "1";
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
        //默认缴款方式为1(刷卡)
        if (!isNotBlank(bdcSlSfxxDTO.jkfs)) {
            bdcSlSfxxDTO.jkfs = 1;
        }
        layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
            var form = layui.form;
            var laytpl = layui.laytpl;
            if (bdcSlSfxxDTO) {
                if (isNullOrEmpty(bdcSlSfxxDTO.jfrdh)) {
                    bdcSlSfxxDTO.jfrdh = bdcSlSfxxDTO.lxdh;
                }
            }
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
            if (isNotBlank(view)) {
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
            }

            loadSfxm(bdcSlSfxxDTO.sfxxid, sqrlb, sfcxjs);
            form.render();
            if (bdcSlSfxxDTO.sfxxid === null) {
                $('#addsfxm' + sqrlb).removeAttr('onclick');
                $('#addsfxm' + sqrlb).attr('onclick', 'ityzl_SHOW_INFO_LAYER("未配置相关收费项目无法新增")')
            }
            if (bdcSlSfxxDTO.sfsdjf === 0) {
                //不收登记费不允许点击是否低保和下岗再就业
                var sfdbfilter = "sfdb" + sqrlb;
                var sfxgzjyfilter = "sfxgzjy" + sqrlb;
                $(".bdcsfxxForm").find('select[lay-filter="' + sfdbfilter + '"]').attr('disabled', 'true');
                $(".bdcsfxxForm").find('select[lay-filter="' + sfxgzjyfilter + '"]').attr('disabled', 'true');
            }
            renderSelect();
            form.render('select');
            disabledAddFa();
            getStateById(readOnly, formStateId, "sfxx", "", "");
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
        url: getContextPath() + "/sf/tdsfxm",
        type: 'get',
        dataType: 'json',
        data: {sfxxid: sfxxid, gzlslid: processInsId, xmid: xmid, qlrlb: qlrlb, sfcxjs: sfcxjs},
        // async:false,
        success: function (data) {
            if (qlrlb === "1") {
                qlrsfxmList = data;
            }
            if (qlrlb === "2") {
                ywrsfxmList = data;
            }
            generateSfxm(data, qlrlb);
            // 渲染收费信息数据中，去收费项目里信息的字段
            renderOtherxx(data, qlrlb);
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

/**
 * 渲染收费信息数据中，去收费项目里信息的字段
 */
function renderOtherxx(sfxmxx, qlrlb){
    if(isNotBlank(sfxmxx)){
        //给页面二维码图片赋值
        if (isNotBlank(sfxmxx[0].jfsewmurl)) {
            var url = "/realestate-accept-ui/sf/ewm?ewmnr=" + encodeURIComponent(sfxmxx[0].jfsewmurl);
            $('.bdcsfxxForm').find('.ewm' + qlrlb).attr('src', url);
        }
        //点击二维码图片放大事件
        $(".ewm" + qlrlb).click(function () {
            var _this = $(this);//将当前的pimg元素作为_this传入函数
            imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
        });
        // 收费信息赋值
        $("#sfzt"+ qlrlb).val(sfxmxx[0].sfzt);

        // 缴费流水号赋值
        $("#jfsbm" + qlrlb).val(sfxmxx[0].jfsbm);

        // 发票号赋值
        $("#fph" + qlrlb).val(sfxmxx[0].fph);
        renderSelect();
        form.render('select');
        disabledAddFa();
        getStateById(readOnly, formStateId, "sfxx", "", "");
    }
}

function loadSfxmSync(sfxxid, qlrlb, sfcxjs) {
    $.ajax({
        url: getContextPath() + "/sf/xm",
        type: 'get',
        dataType: 'json',
        data: {sfxxid: sfxxid, gzlslid: processInsId, xmid: xmid, qlrlb: qlrlb, sfcxjs: sfcxjs},
        async: false,
        success: function (data) {
            if (qlrlb === "1") {
                qlrsfxmList = data;
            }
            if (qlrlb === "2") {
                ywrsfxmList = data;
            }
            generateSfxm(data, qlrlb);
            // 渲染收费信息数据中，去收费项目里信息的字段
            renderOtherxx(data, qlrlb);
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
        if (isNotBlank(view)) {
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
        }

        var sfxmdmfilter = "sfxmdm" + qlrlb;
        var sfxmbzfilter = "sfxmbz" + qlrlb;
        form.on('select(' + sfxmdmfilter + ')', function (selectObj) {
            changeSfxmdm(selectObj, qlrlb)
        });
        form.on('select(' + sfxmbzfilter + ')', function (selectObj) {
            changeSfxmbz(selectObj.value, selectObj.elem, qlrlb);
            saveSfxm();
        });
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
    if (sfxmbz === "土地收益金" || sfxmbz.indexOf("土地使用权") > -1) {
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
                        if (bdcSlSfxmSfbz.sfxmbz === sfxmbz) {
                            var sfxmdjEl = $(elem).parent().parent().parent().find("input[name='sfxmdj']")[0];
                            $(sfxmdjEl).val(bdcSlSfxmSfbz.sfxmdj);
                            var slEl = $(elem).parent().parent().parent().find("input[name='sl']")[0];
                            var sl = $(slEl).val();
                            if (sl === null || sl === 0 || sl === "" || sl < 0) {
                                $(slEl).val(0);
                            } else {
                                var ysjeEl = $(elem).parent().parent().parent().find("input[name='ysje']")[0];
                                $(ysjeEl).val(parseFloat(bdcSlSfxmSfbz.sfxmdj) * sl);
                                countMoney(elem);
                            }
                        }
                    }
                }
            }
        }
    }
}

function onloadSfxmdj(qlrlb) {
    var filter = "sfxmbz" + qlrlb;
    var sfxmbzELArray = $("#bdcSfxxTable" + qlrlb).find('select[lay-filter="' + filter + '"]');
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
        $(ysjeEl).val(parseFloat(sfxmdj) * sl);
        $(ssjeEl).val(parseFloat(sfxmdj) * sl);
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
    // saveSfxx(elem);
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
                sfxmtr = "<select name=\"sfxmdm\" lay-filter=\"sfxmdm1\" class=\"sfxm\">";
                sfxmbztr = "<select name=\"sfxmbz\"  lay-filter=\"sfxmbz1\" class=\"sfxm\">";
            }
            if (qlrlb === "2") {
                sfxxidtr = "<input type=\"hidden\"  name=\"sfxxid\" class=\"sfxm\" value='" + ywrsfxxid + "'>";
                sfxmtr = "<select name=\"sfxmdm\" lay-filter=\"sfxmdm2\" class=\"sfxm\">";
                sfxmbztr = "<select name=\"sfxmbz\"  lay-filter=\"sfxmbz2\" class=\"sfxm\">";
            }
            var htmlStr = "<tr>" +
                sfxxidtr +
                "<input type=\"hidden\"  name=\"sfxmid\" class=\"sfxm\" value='" + addsfxmid + "'>" +
                "<input type=\"hidden\"  name=\"\" class=\"sfxm bdc-sfxmdj\" >\n" +
                "<input type=\"hidden\"  name=\"sfxmmc\" class=\"sfxm\" >\n" +
                "<input type=\"hidden\"  name=\"xh\" class=\"sfxm\" value='" + sfxmNumber + "'>" +
                "<input type=\"hidden\"  name=\"qlrlb\" class=\"sfxm\" value='" + qlrlb + "'>" +
                "<input type=\"hidden\"  name=\"cz\" class=\"sfxm\" value='insert'>" +
                "<td>" + sfxmNumber + "</td>" +
                "<td>\n" +
                "<div class=\"bdc-td-box layui-form\" lay-filter=\"sfxmmc"+ qlrlb +"\">\n" +
                sfxmtr +
                "<option value=\"\">请选择</option>\n" +
                sfxmmcOption +
                "</select>\n" +
                "</div>\n" +
                "</td>" +
                "<td>\n" +
                "<div class=\"bdc-td-box layui-form\" lay-filter=\"sfxmbz1"+ qlrlb +"\">\n" +
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
                "<td><div class=\"bdc-td-box\"><input type=\"number\"  name=\"ssje\" class=\"sfxm bdc-ssje\"></div></td>\n" +
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

function saveSfxx(elem) {
    var bdcsfxxForm;
    var qlrsfxx = $('#qlrsfxx');
    var ywrsfxx = $('#ywrsfxx');
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
            bdcsfxx.hj = $(this).find("#hj").val();
            //点击了保存按钮
            bdcsfxx.bczt = 1;
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
        var jfsbm = $(this).find("input[name=jfsbm]").val();
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
                if(isNotBlank(jfsbm)){
                    sfxm.jfsbm = jfsbm;
                }
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
    addModel("正在计算");
    getReturnData("/sf/recount/tdsfxm", {gzlslid: processInsId, xmid:xmid}, "GET", function (data) {
        qlrsfxmpzList = JSON.parse(sessionStorage.getItem('yqlrsfxmpzList'));
        ywrsfxmpzList = JSON.parse(sessionStorage.getItem('yywrsfxmpzList'));
        removeModal();
        querySfxx(false);
    }, function (xhr) {
        removeModal();
        var dealError = false;
        if (xhr.status == 500) {
            var responseText = JSON.parse(xhr.responseText);
            if (responseText.code == 107) {
                var msg = responseText.msg;
                ityzl_SHOW_WARN_LAYER(msg);
                dealError = true;
            }
        }
        if (!check) {
            delAjaxErrorMsg(xhr);

        }
    }, false);
}

//废除二维码
function deleteEwm(sfxxid, qlrlb, sfzt) {
    if (sfzt === "2") {
        ityzl_SHOW_WARN_LAYER('已缴费不可废除二维码,请先退费')
    } else {
        addModel();
        getReturnData("/rest/v1.0/cz/sfxx/ewm/delete", {
            sfxxid: sfxxid,
            gzlslid: processInsId,
            xmid: xmid,
            sftdsyj: true,
        }, "GET", function (data) {
            removeModal();
            querySfxx(false);
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        })
    }
}

//生成二维码
function generateEwm(elem, sfxxid) {
    // 是否区县代码转换，【常州】区县代码为320499时，土地使用权收费信息生成二维码时，按照320402的逻辑推送
    var sfqxdmzh = true;
    var qxdm = $("select[id='qxdm']").val();
    if(isNotBlank(qxdm) && qxdm == "320499" && sfqxdmzh){
        qxdm = "320402";
    }
    addModel();
    getReturnData("/rest/v1.0/cz/sfxx/sfddjh/" + processInsId, {}, "GET", function (data) {
        removeModal();
        if (isNotBlank(data) && data === true) {
            // 如果业务中有多个不同的地籍号，页面提示信息
            layer.confirm('1.多宗土地，请分别判断收费标准<br>2.请判断是否符合“抵押双方不变，同一宗地再次或多次抵押，减按25%收费”', {
                title: '多地籍号提示信息',
                btn: ['继续生成', '取消'],
                btnAlign: 'c',
            }, function (index, layero) {
                // 查询推送登记费信息
                cxtsdjfxx(elem, sfxxid, qxdm);
                layer.close(index);
            }, function (index) {
                layer.close(index);
            });
        } else {
            // 查询推送登记费信息
            cxtsdjfxx(elem, sfxxid, qxdm);
        }
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}

// 查询推送待缴信息
function cxtsdjfxx(elem, sfxxid, qxdm) {
    // 按钮禁用
    $(elem).attr("disabled", true).css("cursor", "not-allowed");
    addModel();
    getReturnData("/rest/v1.0/cz/sfxx/cxtsdjfxx?sfxxid=" + sfxxid + "&tsly=1&pjdm=320301&&sftdsyj=true&qxdm=" + qxdm, {}, "POST", function (data) {
        removeModal();
        $(elem).removeAttr("disabled").css("cursor", "pointer");
        if (!isNotBlank(data)) {
            ityzl_SHOW_WARN_LAYER("验签失败");
            return;
        }
        if (!isNotBlank(data.jfsewmurl)) {
            ityzl_SHOW_WARN_LAYER("验签失败");
            return;
        }
        querySfxx(false);
    }, function (xhr) {
        $(elem).removeAttr("disabled").css("cursor", "pointer");
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}

//缴费查询
function jfztCx(sfxxid) {
    addModel();
    getReturnData("/rest/v1.0/cz/sfxx/querySfzt", {sfxxid: sfxxid,sftdsyj: true}, "GET", function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("查询成功,缴费状态为" + data.sfztMc);
        querySfxx(false);
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
        imgWidth  = windowH-100;
        $(bigimg).css("width", imgWidth);//以最终的宽度对图片缩放

        var w = (windowW - imgWidth) / 2;//计算图片与窗口左边距
        var h = (windowH - imgWidth) / 2;//计算图片与窗口上边距
        var topHeight = $(".bdc-form-div").css("padding-top");
        $(innerdiv).css({"top": topHeight, "left": w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function () {//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}

// 作废发票号
function zffph(elem, sfxxid) {
    var fph = $(elem).parents('.bdcsfxxForm').find("input[name='fph']").val();
    if (!isNotBlank(fph)) {
        ityzl_SHOW_WARN_LAYER("请先获取发票号");
    }
    addModel();
    getReturnData("/rest/v1.0/cz/sfxx/zffph", {sfxxid : sfxxid, fph : fph, sftdsyj: true}, "GET", function (data) {
        removeModal();
        $(elem).parents('.bdcsfxxForm').find("input[name='fph']").val("");
        var qlrlb = $(elem).parents('.bdcsfxxForm').find("input[name='qlrlb']").val();
        // 作废发票号后，更新收费状态为未收费
        modifySfzt(sfxxid, 1, qlrlb);
        ityzl_SHOW_SUCCESS_LAYER("作废成功");
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}

// 领取发票号
function lqfph(elem, sfxxid) {
    var fph = $(elem).parents('.bdcsfxxForm').find("input[name='fph']").val();
    var qlrlb = $(elem).parents('.bdcsfxxForm').find("input[name='qlrlb']").val();
    var requestLqfph = function (sfxxid) {
        addModel();
        getReturnData("/rest/v1.0/cz/sfxx/lqfph", {sfxxid: sfxxid, gzlslid: processInsId, sftdsyj: true},"GET", function (data) {
            removeModal();
            if (isNotBlank(data)) {
                ityzl_SHOW_SUCCESS_LAYER("领取成功");
                modifySfzt(sfxxid, 2, qlrlb);
                $(elem).parents('.bdcsfxxForm').find("input[name='fph']").val(data);
            } else {
                ityzl_SHOW_WARN_LAYER("未获取到发票号");
            }
            removeModal();
        }, function (xhr) {
            removeModal();
            delAjaxErrorMsg(xhr);
        });
    }
    if (isNotBlank(fph)) {
        layer.confirm('当前发票号为：' + fph + '，是否重新获取？', function (index) {
            requestLqfph(sfxxid);
            layer.close(index);
        });
    } else {
        requestLqfph(sfxxid);
    }

}

function changeDjf(selectObj, sqrlb, sfxxid, sfcxjs) {
    var sfxmTableId = "#bdcSfxxTable" + sqrlb + " tr";
    var sfxmArray = $(sfxmTableId);
    if (isNotBlank(sfxmArray) && sfxmArray.length > 0) {
        $.each(sfxmArray, function (index, item) {
            var sfxmmc = "sfxmmc" + sqrlb;
            var sfxmmcSelected = "#sfxmmc" + sqrlb + " option:selected"
            var sfxmdm = ""
            var ssje = "ssje" + sqrlb;
            var ysje = "ysje" + sqrlb;
            var sfxmmcval = $(item).find("#" + sfxmmc).val()
            var sfxmmcText = $(sfxmmcSelected).text();
            if (isNotBlank(sfxmmcval)) {
                if (selectObj.value === "0" && (sfxmmcText.indexOf("登记费") > -1 || sfxmmcval === "1")) {
                    //不收登记费应收金额不变，实收金额变为0；
                    $(item).find("#" + ssje).val(0);
                } else {
                    $(item).find("#" + ssje).val($(item).find("#" + ysje).val());
                }
                saveSfxm();
            }
        });
    }
}

function saveHj() {
    getReturnData("/sf/hj", {gzlslid: processInsId}, "GET", function (data) {
        console.log("计算合计结束");
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

// 同步收费信息收费状态
function syncSfxxSfzt(){
    getReturnData("/rest/v1.0/cz/sfxx/sync/sfzt", {gzlslid: processInsId}, "GET", function (data) {
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    });
}

// 更新缴款状态
function modifySfzt(sfxxid, sfzt, qlrlb){
    var bdcsfxx = {sfxxid : sfxxid, sfzt: sfzt, sftdsyj: true};
    $.ajax({
        url: getContextPath() + "/rest/v1.0/cz/sfxx/sfzt",
        type: 'POST',
        dataType: 'json',
        async: false,
        contentType: "application/json",
        data: JSON.stringify(bdcsfxx),
        success: function (data) {
            $("#sfzt"+ qlrlb).val(sfzt);
            renderSelect();
            form.render('select');
            disabledAddFa();
            getStateById(readOnly, formStateId, "sfxx", "", "");
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}
/**
 * 切换缴款方式，更新缴款状态、缴款时间
 */
function changeJkfsModifySfzt(sfxxid, sfzt, qlrlb, jkfs){
    var bdcsfxx = {sfxxid : sfxxid, sfzt: sfzt, sftdsyj: true, jkfs: jkfs};
    $.ajax({
        url: getContextPath() + "/rest/v1.0/cz/sfxx/jkfs",
        type: 'POST',
        dataType: 'json',
        async: false,
        contentType: "application/json",
        data: JSON.stringify(bdcsfxx),
        success: function (data) {
            $("#sfzt"+ qlrlb).val(sfzt);
            renderSelect();
            form.render('select');
            disabledAddFa();
            getStateById(readOnly, formStateId, "sfxx", "", "");
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

// 取消发票号
function qxfph(elem, sfxxid) {
    var fph = $(elem).parents('.bdcsfxxForm').find("input[name='fph']").val();
    if (!isNotBlank(fph)) {
        ityzl_SHOW_WARN_LAYER("请先获取发票号");
    }
    addModel();
    getReturnData("/rest/v1.0/cz/sfxx/qxfph", {sfxxid: sfxxid, fph: fph, sftdsyj: false}, "GET", function (data) {
        removeModal();
        $(elem).parents('.bdcsfxxForm').find("input[name='fph']").val("");
        var qlrlb = $(elem).parents('.bdcsfxxForm').find("input[name='qlrlb']").val();
        // 作废发票号后，更新收费状态为未收费
        modifySfzt(sfxxid, 1, qlrlb);
        ityzl_SHOW_SUCCESS_LAYER("取消成功");
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}