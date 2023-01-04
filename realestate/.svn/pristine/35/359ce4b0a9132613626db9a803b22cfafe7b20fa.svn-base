layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
//页面入口
var zdList;
var sfxmPzList;
var sfxmBzList;
var qlrsfxmpzList = [];
var ywrsfxmpzList = [];
var sfxxObj = {};
var getRows;
//收费页面类型
var sfymlx = getQueryString("sfymlx");
var xgjfyy = getQueryString("xgjfyy");
var slbh = getQueryString("slbh");
var processInsId = getQueryString("processInsId");
var xmid = getQueryString("xmid");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var printValue = getQueryString("print");
var zxlc = getQueryString("zxlc");
//权利人是否优惠
var qlryh = false;
//义务人是否优惠
var ywryh = false;

layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var form = layui.form;
    addModel();
    setTimeout("loadButtonArea('sfxx')", 10);
    setTimeout(function () {
        try {
            $.when(loadData(), loadSfxmPz(), loadSfxmBz()).done(function () {
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
            var success = checkBz();
            if (!success) {
                removeModal();
                ityzl_SHOW_WARN_LAYER("减半、全免或者合计为0时,备注不可为空");
                return false;
            }
            setTimeout(function () {
                try {
                    $.when(saveSfxx(), saveSfxm()).done(function () {
                        removeModal();
                        ityzl_SHOW_SUCCESS_LAYER("保存成功");
                        // 保存成功后 如果xgjfyy有值，则需要保存一份sfxx日志
                        if (xgjfyy) {
                            saveSfxxCzrz(sfxxObj);
                        }
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

    form.on('select(bzselect)', function (data) {   //选择移交单位 赋值给input框
        $(this).parents('.layui-input-inline').find("input[name='bz']").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({ "display": "none" });
        form.render();
    });

    //收起、展开
    var $info = $('.layui-tab-content');
    $info.on('click', '.bdc-show-more', function () {
        var $this = $(this);
        if ($this.html() == '收起') {
            $this.html('展开');
        } else {
            $this.html('收起');
        }
        $this.parents('.jkqk-info').find('.bdc-more-item').toggleClass('bdc-hide');
    });

});

// 校验是否线上缴款
function checSfXsJk(){
    $.ajax({
        url: getContextPath()+ "/bdcGzyz/sfxsjk",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid, gzlslid: processInsId},
        success: function (data) {
            if (!data) {
                ityzl_SHOW_WARN_LAYER("抵押权人属于月结，无需线上缴费！");
                $(".fs-btn").addClass("layui-btn-disabled");
                $(".fs-btn").attr('disabled','off');
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function loadBtn() {
    if (qlrsfxxid === null && ywrsfxxid === null) {
        setAllElementDisabled()
    } else {
        getStateById(readOnly, formStateId, "sfxx","","bdcSfxxTable");
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


function loadSfxmBz() {
    var url = getContextPath() + "/sf/xm/bz";
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                sfxmBzList = data;
                for(var i=0;i<qlrsfxmpzList.length;i++){
                    qlrsfxmpzList[i].bdcSlSfxmSfbzList = sfxmBzList;
                }
                for(var i=0;i<ywrsfxmpzList.length;i++){
                    ywrsfxmpzList[i].bdcSlSfxmSfbzList = sfxmBzList;
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


                if(data.bdcSlQlrSfxxDTO ==null &&data.bdcSlYwrSfxxDTO ==null){
                    removeModal();
                } else
                    //不存在权利人义务人收费信息默认展示权利人收费信息
                if (data.bdcSlQlrSfxxDTO != null && !isNotBlank(data.bdcSlQlrSfxxDTO.qlrlb) && data.bdcSlYwrSfxxDTO != null && !isNotBlank(data.bdcSlYwrSfxxDTO.qlrlb)) {
                    data.bdcSlQlrSfxxDTO.qlrlb = "1";
                } else if (data.bdcSlYwrSfxxDTO != null && !isNotBlank(data.bdcSlYwrSfxxDTO.qlrlb)) {
                    data.bdcSlYwrSfxxDTO.qlrlb = "2";
                }

                if(null != data.bdcSlYwrSfxxDTO && isNotBlank(data.bdcSlYwrSfxxDTO.mc) ){
                    data.bdcSlQlrSfxxDTO.ywrmc = data.bdcSlYwrSfxxDTO.mc;
                }
                generateSfxx(data.bdcSlQlrSfxxDTO, "1", sfcxjs);

                generateSfxx(data.bdcSlYwrSfxxDTO, "2", sfcxjs);

                if (data.tsxx) {
                    showAlertDialog(data.tsxx);
                }
                if (!data.sfsf) {
                    //不收费时设置新增按钮不可点击
                    $('.addsfxm').attr('disabled', 'true');
                    $('.addsfxm').addClass('bdc-prohibit-btn');
                }

                checSfXsJk();
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function generateSfxx(bdcSlSfxxDTO, sqrlb,sfcxjs) {
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
            if (bdcSlSfxxDTO.hj == 0) {
                if (sqrlb === "1") {
                    qlryh = true;
                } else {
                    ywryh = true;
                }
            }
            var tpl, view;
            tpl = sfxxTpl.innerHTML;
            if (bdcSlSfxxDTO.qlrlb === "1") {
                view = document.getElementById('qlrsfxx');
            } else {
                view = document.getElementById('ywrsfxx');
            }
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            generatrFsjkqk(bdcSlSfxxDTO);
            loadSfxm(bdcSlSfxxDTO.sfxxid, sqrlb,sfcxjs);
            form.render();
            if (bdcSlSfxxDTO.sfxxid === null) {
                $('#addsfxm' + sqrlb).removeAttr('onclick');
                $('#addsfxm' + sqrlb).attr('onclick', 'ityzl_SHOW_INFO_LAYER("未配置相关收费项目无法新增")')
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

function generatrFsjkqk(bdcSlSfxxDTO){
    $.ajax({
        url: getContextPath() + "/fpxx/getFpxx/" + bdcSlSfxxDTO.sfxxid,
        type: 'get',
        dataType: 'json',
        success: function (fpxx) {
            layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
                var form = layui.form;
                var laytpl = layui.laytpl;
                var json = {
                    bdcSlSfxxQO: bdcSlSfxxDTO,
                    zd: zdList,
                    fpxx: isNotBlank(fpxx) ? fpxx[0] :{},
                };
                var tpl, view;
                tpl = fsjkqkTpl.innerHTML;
                if (bdcSlSfxxDTO.qlrlb === "1") {
                    view = document.getElementById('qlrfsjkqk');
                    $('#qlrfsjkqk').addClass('bdc-qlrjkqk-info');
                } else {
                    view = document.getElementById('ywrfsjkqk');
                }
                //渲染数据
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
                getStateById(readOnly, formStateId, "fsSfxx");
                setBtnDisabled(bdcSlSfxxDTO);
                form.render();
                form.render('select');
                renderSelect();
                disabledAddFa();
            });
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function setBtnDisabled(sfxx){
    if(isNotBlank(sfxx.fph)){
        // 存在发票号时，设置获取电子发票按钮不可用
        $("#queryDzph"+sfxx.qlrlb).addClass("layui-btn-disabled");
        $("#queryDzph"+sfxx.qlrlb).attr('disabled','off');
    }
    if(isNotBlank(sfxx.sfkp) && sfxx.sfkp==1){
        // 已开具发票时，设置获取电子发票按钮不可用
        $("#invocie"+sfxx.qlrlb).addClass("layui-btn-disabled");
        $("#invocie"+sfxx.qlrlb).attr('disabled','off');
    }
}

function loadSfxm(sfxxid, qlrlb,sfcxjs) {
    $.ajax({
        url: getContextPath() + "/sf/xm",
        type: 'get',
        dataType: 'json',
        async: false,
        data: {sfxxid: sfxxid, gzlslid: processInsId, xmid: xmid, qlrlb: qlrlb, sfcxjs:sfcxjs},
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
                qlrlb: qlrlb,
                sfxmbz:sfxmBzList
            };
        } else {
            json = {
                bdcSlSfxmDOList: bdcSlSfxmDOList,
                zd: zdList,
                sfxmPzList: ywrsfxmpzList,
                qlrlb: qlrlb,
                sfxmbz: sfxmBzList
            };
        }
        if (isNotBlank(bdcSlSfxmDOList) && bdcSlSfxmDOList.length > 0) {
            for (var i = 0; i < bdcSlSfxmDOList.length; i++) {
                if ("2" == bdcSlSfxmDOList[i].yh || "3" == bdcSlSfxmDOList[i].yh) {
                    if (qlrlb === "1") {
                        qlryh = true;
                    } else {
                        ywryh = true;
                    }
                    break;
                }
            }
        }

        if (!isNullOrEmpty(json.bdcSlSfxmDOList)) {
            getRows = json.bdcSlSfxmDOList;
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
        var sfxmyhfilter = "yh"+qlrlb;
        form.on('select(' + sfxmdmfilter + ')', function (selectObj) {
            changeSfxmdm(selectObj, qlrlb)
        });
        form.on('select(' + sfxmbzfilter + ')', function (selectObj) {
            changeSfxmbz(selectObj.value, selectObj.elem, qlrlb,'',selectObj);
            saveSfxm();
        });
        form.on('select('+ sfxmyhfilter +')',function(selectObj){
            //判断备注是否为空
            var bz = $('#bz' + qlrlb).val();
            var yhname = "yhzc" +qlrlb
            var yhzc = $(selectObj.elem).parents().find("#"+ yhname)[0];
            if(isNullOrEmpty(bz) && (selectObj.value === "2" || selectObj.value === "3")) {
                ityzl_SHOW_WARN_LAYER("减半、全免或者合计为0时,备注不可为空");
                selectObj.value = $(yhzc).val();
                $(selectObj.elem).val($(yhzc).val());
                form.render();
            } else {
                if ((selectObj.value === "2" || selectObj.value === "3") && isNotBlank(bz)) {
                    $('#bz' + qlrlb).attr('lay-verify', "required");
                } else {
                    $('#bz' + qlrlb).removeAttr('lay-verify', "required");
                }
                if (selectObj.value === "1" || selectObj.value === "") {
                    if (qlrlb === "1") {
                        qlryh = false;
                    } else {
                        ywryh = false;
                    }
                }
                var sfxmbzEl = $(selectObj.elem).parent().parent().parent().find("select[name='sfxmbz']")[0];
                $(yhzc).val(selectObj.value);
                changeSfxmbz($(sfxmbzEl).val(), selectObj.elem, qlrlb, selectObj.value, null);
            }
        });
        loadBtn();
        form.render();
        form.render('select');
        renderSelect();
        disabledAddFa();
        window.setTimeout(function () {
            onloadSfxmdj(qlrlb);
            countMoney('');
        },500)


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
                    // var sfxmmcEl = $(selectObj.elem).parent().parent().parent().find("input[name='sfxmmc']")[0];
                    var sfxmmcEl = $(selectObj.elem).parents('.sfxmtr').find("input[name='sfxmmc']")[0];
                    // var slEl = $(selectObj.elem).parent().parent().parent().find("input[name='sl']")[0];
                    var slEl = $(selectObj.elem).parents('.sfxmtr').find("input[name='sl']")[0];
                    $(sfxmmcEl).val(sfxmPz.sfxmmc);
                    var sl = $(slEl).val();
                    if (sl === null || sl === 0 || sl === "") {
                        sl = sfxmPz.mrsl;
                        $(slEl).val(sl)
                    }
                    $(sfxmmcEl).val(sfxmPz.sfxmmc);
                    if (sfxmPz.bdcSlSfxmSfbzList !== null && sfxmPz.bdcSlSfxmSfbzList.length > 0) {
                        var sfxmbzEl = $(selectObj.elem).parents('.sfxmtr').find("select[name='sfxmbz']")[0];
                        var form = layui.form;
                        var laytpl = layui.laytpl, view = sfxmbzEl;
                        var json = {
                            bdcSlSfxmSfbzList: sfxmPz.bdcSlSfxmSfbzList
                        };
                        var tpl = sfxmbzTpl.innerHTML;
                        laytpl(tpl).render(json, function (html) {
                            view.innerHTML = html;
                        });
                        form.render();
                    }
                    form.render('select','sfxmbz'+qlrlb);
                    form.render('select','sfxmmc' + qlrlb);
                })
            }
        }
    }
}

function changeSfxmbz(sfxmbz, elem, qlrlb,yhzc,selectObj) {

    if(sfxmbz === "土地收益金") {
        return;
    }
    var pzList = [];
    if(null != selectObj){
        var dj = $(selectObj.elem).find('option:selected').attr('dj');
        var sfxmdjEl = $(elem).parents('.sfxmtr').find("input[name='sfxmdj']")[0];
        $(sfxmdjEl).val(dj);
       // return;
    }

    /*if (qlrlb === "1") {
        pzList = qlrsfxmpzList;
    }
    if (qlrlb === "2") {
        pzList = ywrsfxmpzList;
    }*/
    //if (pzList !== null && pzList.length > 0) {
     //   for (var i = 0; i < pzList.length; i++) {
     //       var sfxmPz = pzList[i];
      //      if (sfxmPz.sfxmdm === sfxmdm) {
                if (sfxmBzList !== null && sfxmBzList.length > 0) {
                    for (var j = 0; j < sfxmBzList.length; j++) {
                        var bdcSlSfxmSfbz = sfxmBzList[j];
                        // 收费项目dm为维修资金时，不更新为配置的金额
                        if (bdcSlSfxmSfbz.sfxmdm === "89") {
                            continue;
                        }
                        if (bdcSlSfxmSfbz.sfxmbz === sfxmbz) {
                            var sfxmdjEl = $(elem).parents('.sfxmtr').find("input[name='sfxmdj']")[0];
                            //sfxmdm 和 sfxmbz 保持一致
                            var sfxmdm = $(elem).parents('.sfxmtr').find("input[name='sfxmdm']")[0];
                            $(sfxmdm).val(bdcSlSfxmSfbz.sfxmdm);
                            $(sfxmdjEl).val(bdcSlSfxmSfbz.sfxmdj);
                            var slEl = $(elem).parents('.sfxmtr').find("input[name='sl']")[0];
                            var sl = $(slEl).val();
                            if (sl === null || sl === 0 || sl === "") {
                                $(slEl).val(1);
                            } else {
                                var ssjeEl = $(elem).parents('.sfxmtr').find("input[name='ssje']")[0];
                                var ysjeEl = $(elem).parents('.sfxmtr').find("input[name='ysje']")[0];
                                if (yhzc) {
                                    if (yhzc === '1') {
                                        $(ssjeEl).val(parseFloat(bdcSlSfxmSfbz.sfxmdj) * sl);
                                    } else if(yhzc === '2') {
                                        $(ssjeEl).val(parseFloat(bdcSlSfxmSfbz.sfxmdj) * sl / 2);
                                    } else if(yhzc === '3') {
                                        $(ssjeEl).val(0);
                                    }
                                    $(ysjeEl).val(parseFloat(bdcSlSfxmSfbz.sfxmdj) * sl);
                                } else {
                                    $(ysjeEl).val(parseFloat(bdcSlSfxmSfbz.sfxmdj) * sl);
                                    $(ssjeEl).val(parseFloat(bdcSlSfxmSfbz.sfxmdj) * sl);
                                }
                                countMoney(elem);
                            }
                        }
                    }
                }
    /*        }
        }
    }*/
}

function onloadSfxmdj(qlrlb) {
    //var sfxmbzELArray = $("#bdcSfxxTable").find("select[name='sfxmbz']");
    var sfxmbzELArray = $($(".bdcSfxxTable")[parseInt(qlrlb)-1]).find("select[name='sfxmbz']");

    if (sfxmbzELArray !== null && sfxmbzELArray.length > 0) {
        for (var i = 0; i < sfxmbzELArray.length; i++) {
            var yhzc = $(sfxmbzELArray[i]).parents().find("select[name='yh']")[0]
            changeSfxmbz($(sfxmbzELArray[i]).val(), sfxmbzELArray[i], qlrlb,$(yhzc).val(),null);
        }
    }
}

function changeSl(elem) {
    var sl = $(elem).val();
    if (sl == 0) {
        ityzl_SHOW_INFO_LAYER("数量不能为0");
        $(elem).val(1);
    } else if (sl === null || sl === "") {
        ityzl_SHOW_INFO_LAYER("请填写数量");
    } else {
        var $parentTr = $(elem).parents('tr');
        var sfxmdjEl = $parentTr.find(".bdc-sfxmdj");
        var sfxmdj = sfxmdjEl.val();
        var ysjeEl = $parentTr.find(".bdc-ysje");
        $(ysjeEl).val(parseFloat(sfxmdj) * sl);
        //
        var ssjeEl = $parentTr.find(".bdc-ssje");
        var yhzc = $($parentTr.find(".yhzc")).val();
        if (yhzc === "1") {
            $(ssjeEl).val(parseFloat(sfxmdj) * sl);
        }
        if (yhzc === "2") {
            $(ssjeEl).val(parseFloat(sfxmdj) * sl / 2);
        } else if (yhzc === "3") {
            $(ssjeEl).val(0);
        }
        countMoney(elem);
    }
}

function countMoney(elem) {
    var sfxxTable;
    if(elem) {
        sfxxTable = $(elem).parents('.bdcSfxxTable');
    } else {
        sfxxTable = $(".bdcSfxxTable");
    }
    sfxxTable.each(function () {
        var ssjeELArray = $(this).find("input[name='ssje']");
        if (ssjeELArray !== null && ssjeELArray.length > 0) {
            var ze = 0;
            for (var i = 0; i < ssjeELArray.length; i++) {
                var ssje = $(ssjeELArray[i]).val();
                if (ssje === "" || ssje === null) {
                    ssje = 0;
                }
                ze += parseFloat(ssje);
            }
            $(this).find("#hj").val(ze);
        }
    });
    saveSfxm(elem,true);
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
            /*if (qlrlb === "1") {
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
            }*/
            var sfxmBzOption = '';
            if (sfxmBzList !== null && sfxmBzList.length > 0) {
                for (var i = 0; i < sfxmBzList.length; i++) {
                    var bdcsfxmBz = sfxmBzList[i];
                    sfxmBzOption += '<option dj = "'+bdcsfxmBz.sfxmdj+'" dm = "'+bdcsfxmBz.sfxmdm+'" value="' + bdcsfxmBz.sfxmbz + '">' + bdcsfxmBz.sfxmbz + '</option>';
                }
            }

            //组织优惠下拉框
            var yhOption = "";
            yhOption += '<option value="1">全费</option>';
            yhOption += '<option value="2">减半</option>';
            yhOption += '<option value="3">全免</option>';
            var sfxxidtr;
            var sfxmtr;
            var sfxmbztr;
            var yhtr;
            if (qlrlb === "1") {
                sfxxidtr = "<input type=\"hidden\"  name=\"sfxxid\" class=\"sfxm\" value='" + qlrsfxxid + "'>";
                sfxmtr = "<select name=\"sfxmdm\" lay-filter=\"sfxmdm1\" class=\"sfxm\">";
                sfxmbztr = "<select name=\"sfxmbz\"  lay-filter=\"sfxmbz1\" class=\"sfxm\">";
                yhtr = "<select name=\"yh\"  lay-filter=\"yh1\" class=\"sfxm\">";
            }
            if (qlrlb === "2") {
                sfxxidtr = "<input type=\"hidden\"  name=\"sfxxid\" class=\"sfxm\" value='" + ywrsfxxid + "'>";
                sfxmtr = "<select name=\"sfxmdm\" lay-filter=\"sfxmdm2\" class=\"sfxm\">";
                sfxmbztr = "<select name=\"sfxmbz\"  lay-filter=\"sfxmbz2\" class=\"sfxm\">";
                yhtr = "<select name=\"yh\"  lay-filter=\"yh2\" class=\"sfxm\">";
            }
            var htmlStr = "<tr class='sfxmtr'>" +
                sfxxidtr +
                "<input type=\"hidden\"  name=\"sfxmid\" class=\"sfxm\" value='" + addsfxmid + "'>" +
                "<input type=\"hidden\"  name=\"sfxmdj\" class=\"sfxm bdc-sfxmdj\" id=\"sfxmdj\"+qlrlb>\n" +
                "<input type=\"hidden\"  name=\"sfxmmc\" class=\"sfxm\" >\n" +
                "<input type=\"hidden\"  name=\"xh\" class=\"sfxm\" value='" + sfxmNumber + "'>" +
                "<input type=\"hidden\"  name=\"qlrlb\" class=\"sfxm\" value='" + qlrlb + "'>" +
                "<input type=\"hidden\"  name=\"cz\" class=\"sfxm\" value='insert'>" +
                "<td>" + sfxmNumber + "</td>" +
                "<td>\n" +
                "<div class=\"bdc-td-box layui-form\" lay-filter=\"sfxmmc\"+qlrlb>\n" +
                //"<input type=\"text\"   name=\"sfxmdm\"  class=\"sfxm\">"+
                "<input type=\"text\"  name=\"sfxmmc\"  id=\"sfxmmc\"+qlrlb class=\"layui-input sfxm\">"+

                /*sfxmtr +
                "<option value=\"\">请选择</option>\n" +
                sfxmmcOption +
                "</select>\n" +*/
                "</div>\n" +
                "</td>" +
                "<td>\n" +
                "<div class=\"bdc-td-box\">\n" +
                sfxmbztr +
                "<option value=\"\">请选择</option>\n" +
                sfxmBzOption+
                "</select>\n" +
                "</div>\n" +
                "</td>" +
                "<td><div class=\"bdc-td-box\"><input type=\"number\" lay-verify=\"\" onkeyup=\"value=zhzs(this.value)\"  name=\"sl\" oninput=\"changeSl(this)\" class=\"sfxm\"></div></td>\n" +
                "<td>\n" +
                "<div class=\"bdc-td-box\">\n" +
                yhtr +
                "<option value=\"\">请选择</option>\n" +
                yhOption +
                "</select>\n" +
                "</div>\n" +
                "</td>" +
                "<td><div class=\"bdc-td-box\"><input type=\"number\" lay-verify=\"\"  name=\"ysje\" oninput=\"countMoney(this)\" class=\"sfxm bdc-ysje\" disabled=\"off\"></div></td>\n" +
                "<td><div class=\"bdc-td-box\"><input type=\"number\" lay-verify=\"\"  name=\"ssje\" oninput=\"countMoney(this)\" class=\"sfxm bdc-ssje\" disabled=\"off\"></div></td>\n" +
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


                var sfxmdmfilter = "sfxmdm" + qlrlb;
                var sfxmbzfilter = "sfxmbz" + qlrlb;
                var sfxmyhfilter = "yh"+qlrlb;
                form.on('select(' + sfxmdmfilter + ')', function (selectObj) {
                    changeSfxmdm(selectObj, qlrlb)
                });
                form.on('select(' + sfxmbzfilter + ')', function (selectObj) {
                    changeSfxmbz(selectObj.value, selectObj.elem, qlrlb,null,selectObj);
                    saveSfxm();
                });
                form.on('select('+ sfxmyhfilter +')',function(selectObj){
                    //判断备注是否为空
                    var bz = $('#bz' + qlrlb).val();
                    var yhname = "yhzc" +qlrlb
                    var yhzc = $(selectObj.elem).parents().find("#"+ yhname)[0];
                    if(isNullOrEmpty(bz) && (selectObj.value === "2" || selectObj.value === "3")) {
                        ityzl_SHOW_WARN_LAYER("减半、全免或者合计为0时,备注不可为空");
                        selectObj.value = $(yhzc).val();
                        $(selectObj.elem).val($(yhzc).val());
                        form.render();
                    } else {
                        var sfxmbzEl = $(selectObj.elem).parent().parent().parent().find("select[name='sfxmbz']")[0];
                        $(yhzc).val(selectObj.value);
                        changeSfxmbz($(sfxmbzEl).val(), selectObj.elem, qlrlb,selectObj.value,null);
                    }
                });
                form.render("select");
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
    if (qlrsfxx.length > 0 || ywrsfxx.length > 0) {
        if(elem) {
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

            if (isNotBlank(bdcsfxx.sfxxid)) {
                //保存收费信息
                $.ajax({
                    url: getContextPath() + "/sf/xx",
                    type: 'PATCH',
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(bdcsfxx),
                    success: function (data) {
                        sfxxObj = bdcsfxx;
                        sfxxObj["gzldyid"] = processInsId;
                        sfxxObj["slbh"] = slbh;
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
function saveSfxm(elem,reload) {
    var sfxmList = [];
    var bdcsfxxForm;
    if(elem) {
        bdcsfxxForm = $(elem).parents('.bdcsfxxForm');
    } else {
        bdcsfxxForm = $(".bdcsfxxForm");
    }
    bdcsfxxForm.each(function () {
        //收费项目保存
        $(this).find(".bdcSfxxTable tr").each(function (index,sfxmtr) {
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
                if(!reload) {
                    loadSfxm(qlrsfxxid, "1",false);
                    loadSfxm(ywrsfxxid, "2",false);
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }
}

function saveAndDeleteSfxm(sfxmid, qlrlb) {
    if (getRows.length > 1) {
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
    } else {
        ityzl_SHOW_WARN_LAYER("不能删除！如果无需收费，请划价为0，并备注原因！");
    }
}

function deletesfxm(sfxmid, qlrlb) {
    $.ajax({
        url: getContextPath() + "/sf/xm?sfxmid=" + sfxmid,
        async: false,
        type: 'DELETE',
        success: function (data) {
            if (qlrlb === "1") {
                loadSfxm(qlrsfxxid, qlrlb,false);
            }
            if (qlrlb === "2") {
                loadSfxm(ywrsfxxid, qlrlb,false);
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
    addModel('重新计算中');
//重新计算，不管数据库是否有数据，根据当前项目信息重新计算
    getReturnData("/sf/recount", {gzlslid: processInsId, xmid:xmid}, "GET", function (data) {
        querySfxx(false);
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    }, false);
    removeModal();
}

function queryXwlx(sfxxid,qlrlb) {
    addModel('查询中');
    getReturnData("/sf/xwlx", {sfxxid:sfxxid,gzlslid: processInsId,qlrlb:qlrlb,xmid:xmid}, "GET", function (data) {
        removeModal();
        if(data) {
            $('#xwlx' + qlrlb).val(data);
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

// 修改收费状态
function tswwjf(){
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/modifySfzt",
        type: 'GET',
        dataType: 'json',
        data: {
            gzlslid : processInsId
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


function saveSfxxCzrz(bdcsfxx){

    bdcsfxx["xgjfyy"] = xgjfyy;
    var jfList = [];
    jfList.push(bdcsfxx);
    $.ajax({
        url: "/realestate-accept-ui/sf/jfcz?type=xgjf",
        type: 'POST',
        data: JSON.stringify(jfList),
        contentType: 'application/json',
        success: function (result) {

        },
        error: function () {


        }
    });
}

// 推送一体化信息
function tsythsfxx(){
    addModel();
    var bdcGzYzQO = {
        zhbs : "SFXX_TS_YTHXX",
        paramMap : { gzlslid : processInsId}
    };
    gzyzCommon(2, bdcGzYzQO, function (data) {
        console.log("验证通过");
        $.ajax({
            url: getContextPath() + "/sf/tsythslsfxx",
            type: 'GET',
            dataType: 'json',
            data: {gzlslid: processInsId},
            success: function (data) {
                removeModal();
                console.info(data);
                if(data && data.success == true){
                    ityzl_SHOW_SUCCESS_LAYER("推送成功。");
                }else{
                    ityzl_SHOW_WARN_LAYER("推送失败 "+ data.fail.message);
                }
            },
            error: function (err) {
                removeModal();
                delAjaxErrorMsg(err);
            }
        });
    });
}

/**
 * 推送收费信息，生成缴款书编码
 */
function tsSfxx(qlrlb, sfxxid){
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/xx/yancheng/ts",
        type: 'POST',
        dataType: 'json',
        data: {
            sfxxid: sfxxid,
            qlrlb: qlrlb,
            gzlslid: processInsId
        },
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("生成缴款通知书成功");
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

/**
 * 查询缴款情况
 * @param qlrlb 权利人类别
 */
function queryJkqk(qlrlb, sfxxid){
    var param = {
        sfxxid: sfxxid,
        qlrlb: qlrlb
    };
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/xx/jkqk",
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(param),
        contentType: 'application/json',
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                if(isNotBlank(data.fph)){
                    // 存在发票号则不进行自动执行操作
                    generatrFsjkqk(data);
                }else{
                    // 自动执行操作
                    autoQueryDzfpxx(sfxxid, qlrlb);
                }
            }else{
                ityzl_SHOW_WARN_LAYER("未获取到缴款信息");
            }
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

// 自动执行电子票号获取、开具发票、获取发票信息的操作
function autoQueryDzfpxx(sfxxid, qlrlb){
    addModel();
    $.ajax({
        url: getContextPath() + "/fpxx/auto",
        type: 'GET',
        dataType: 'json',
        data: {sfxxid: sfxxid, qlrlb: qlrlb, gzlslid: processInsId},
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("自动执行获取电子票号、开具发票、获取电子发票信息成功");
            generatrFsjkqk(data);
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

// 获取电子票号
function queryDzph(elem, sfxxid){
    addModel();
    $.ajax({
        url: getContextPath() + "/fpxx/queryDzph",
        type: 'GET',
        data:{sfxxid: sfxxid},
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                ityzl_SHOW_SUCCESS_LAYER("获取成功");
                $(elem).parents(".jkqk-info").find("input[name='fph']").val(data);
                // 设置获取电子发票按钮不可用
                $(elem).addClass("layui-btn-disabled");
                $(elem).attr('disabled','off');
            }else{
                ityzl_SHOW_WARN_LAYER("未获取电子发票号");
            }
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

// 开具发票
function invocie(elem, qlrlb, sfxxid){
    addModel();
    $.ajax({
        url: getContextPath() + "/fpxx/invoice",
        type: 'GET',
        dataType: 'json',
        data: {sfxxid: sfxxid,qlrlb:qlrlb, gzlslid: processInsId},
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("查询成功");
            //queryJkqk(qlrlb, sfxxid);
            // 设置开具发票按钮不可用
            $(elem).addClass("layui-btn-disabled");
            $(elem).attr('disabled','off');
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

// 获取电子发票信息
function queryDzfpxx(sfxxid){
    addModel();
    $.ajax({
        url: getContextPath() + "/fpxx/getDzfpAndUpload",
        type: 'GET',
        dataType: 'json',
        data: {sfxxid: sfxxid, gzlslid:processInsId},
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("查询成功");
            generatrFsjkqk(data);
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

// 作废电子缴款书
function invalidate(sfxxid){
    addModel();
    $.ajax({
        url: getContextPath() + "/fpxx/invalidate",
        type: 'GET',
        dataType: 'json',
        data: {sfxxid: sfxxid},
        success: function () {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("作废成功");
            querySfxx();
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

function convertFpzt(fpzt) {
    if (isNotBlank(fpzt)) {
        if (fpzt == 1) {
            return "正常";
        } else {
            return "冲红";
        }
    }
    return "";
}

function tsjkm(qlrlb, sfxxid) {
    addModel("推送中");
    getReturnData("/sf/tsjkm", {sfxxid: sfxxid}, "GET", function (data) {
        removeModal();
        if (data && data.code == "1") {
            ityzl_SHOW_SUCCESS_LAYER("推送成功");
        } else {
            ityzl_SHOW_WARN_LAYER("推送失败" + data.message);
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

function checkBz() {
    if (qlryh) {
        var bz = $('#bz1').val();
        if (isNullOrEmpty(bz)) {
            return false;
        }
    }
    if (ywryh) {
        var bz = $('#bz2').val();
        if (isNullOrEmpty(bz)) {
            return false;
        }
    }
    return true;
}