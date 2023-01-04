layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
//页面入口
var zdList;
var sfxmBzList;
//收费页面类型
var gzlslids = getQueryString("gzlslids");
var form,table,laytpl;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    laytpl = layui.laytpl;
    addModel();

    setTimeout(function () {
        try {
            $.when(loadData(), loadSfxmBz()).done(function () {
                removeModal();
            })
        } catch (e) {
            removeModal();
            ERROR_CONFIRM("请求失败", e.message);
            return;
        }
    }, 10);

    form.on('select(sfxmbz)', function (selectObj) {
        changeSfxmbz(selectObj);
    });

    form.on('select(yh)',function(selectObj){
        var bz = $("#bz").val();
        if(isNullOrEmpty(bz) && (selectObj.value === "2" || selectObj.value === "3")) {
            ityzl_SHOW_WARN_LAYER("减半、全免或者合计为0时,备注不可为空");
            selectObj.value = "";
            $(selectObj.elem).val("");
            form.render();
        } else {
            var sfxmbzEl =  $(selectObj.elem).parents(".sfxmTr").find("select[name='sfxmbz']");
            changeSfxmbz({value: $(sfxmbzEl).val(), elem: sfxmbzEl});
        };
    });

    form.on('select(bzselect)', function (data) {
        $(this).parents('.layui-input-inline').find("input[name='bz']").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({ "display": "none" });
        form.render();
    });

});

function addSfxm() {
    var xh = $(".xh").length + 1;
    var json = {
        xh : xh,
        sfxmbzList: sfxmBzList,
    };
    var tpl = sfxmTpl.innerHTML;
    laytpl(tpl).render(json, function (html) {
        if($(".bdc-table-none").length>0){
            $(".bdc-table-none").remove();
        }
        $("#hjTr").before(html);
    });

    form.render();
}

// 删除收费项目
function deleteSfxm(elem){
    // 删除收费项目
    $(elem).parents(".sfxmTr").remove();
    if($(".xh").length > 0){
        // 刷新页面上的序号
        var xh = 1;
        $(".xh").each(function(index, item){
            $(item).html(xh);
            $(item).parents(".sfxmTr").find("input[name='xh']").val(xh);
            xh++;
        });
        countMoney( $(".xh")[0])
    }
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
                // 渲染字典项
                $.each(data.sfxxbz, function(index,item) {
                    $('#bzselect').append('<option value="'+item.MC +'">'+item.MC +'</option>');
                });
                form.render('select');
                // 下拉选择添加删除按钮
                renderSelectClose(form);
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
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function changeSfxmbz(selectObj) {
    var sfxmbz = selectObj.value;
    var elem = selectObj.elem;
    if(sfxmbz === "土地收益金") {
        return;
    }
    // 赋值 sfxmdj
    var dj = $(selectObj.elem).find('option:selected').attr('dj');
    $(elem).parents('.sfxmTr').find("input[name='sfxmdj']").val(dj);

    if (sfxmBzList !== null && sfxmBzList.length > 0) {
        for (var j = 0; j < sfxmBzList.length; j++) {
            var bdcSlSfxmSfbz = sfxmBzList[j];
            // 收费项目dm为维修资金时，不更新为配置的金额
            if (bdcSlSfxmSfbz.sfxmdm === "89") {
                continue;
            }
            if (bdcSlSfxmSfbz.sfxmbz === sfxmbz) {
                //sfxmdm 和 sfxmbz 保持一致
                $(elem).parents('.sfxmTr').find("input[name='sfxmdj']").val(bdcSlSfxmSfbz.sfxmdj);
                $(elem).parents('.sfxmTr').find("input[name='sfxmdm']").val(bdcSlSfxmSfbz.sfxmdm);

                countYsje(elem, bdcSlSfxmSfbz.sfxmdj);
                countMoney(elem);
            }
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
        $(ysjeEl).val(parseFloat(sfxmdj) * sl);
        $parentTr.find("input[name='ysje']").val(parseFloat(sfxmdj) * sl);
        countMoney(elem);
    }
}

// 计算应收金额
function countYsje(elem, sfxmdj) {
    var slEl = $(elem).parents('.sfxmTr').find("input[name='sl']")[0];
    var sl = $(slEl).val();
    if (sl === null || sl === 0 || sl === "") {
        sl = 1;
        $(slEl).val(1);
    }
    var yh = $(elem).parents('.sfxmTr').find(".yh").val();
    var ysjeEl = $(elem).parents('.sfxmTr').find(".bdc-ysje");
    var ysjeInput = $(elem).parents('.sfxmTr').find("input[name='ysje']");
    if(yh){
        if (yh === '1') {
            $(ysjeEl).val(parseFloat(sfxmdj) * sl);
            $(ysjeInput).val(parseFloat(sfxmdj) * sl);
        } else if(yh === '2') {
            $(ysjeEl).val(parseFloat(sfxmdj) * sl/2);
            $(ysjeInput).val(parseFloat(sfxmdj) * sl/2);
        } else if(yh === '3') {
            $(ysjeEl).val(0);
            $(ysjeInput).val(0);
        }
    }else{
        $(ysjeEl).val(parseFloat(sfxmdj) * sl);
        $(ysjeInput).val(parseFloat(sfxmdj) * sl);
    }
}

// 计算合计
function countMoney(elem) {
    if($(elem).parents(".bdcSfxxTable").find(".sfxmTr").length > 0){
        var ze = 0;
        $(elem).parents(".bdcSfxxTable").find(".sfxmTr").each(function(index, sfxmTr){
            var ysjeELArray = $(sfxmTr).find("input[name='ysje']");
            if (ysjeELArray !== null && ysjeELArray.length > 0) {
                for (var i = 0; i < ysjeELArray.length; i++) {
                    var ysje = $(ysjeELArray[i]).val();
                    if (ysje === "" || ysje === null) {
                        ysje = 0;
                    }
                    ze += parseFloat(ysje);
                }
            }
        });
        $(elem).parents(".bdcSfxxTable").find("#hj").val(ze);
    }
}

function saveSfxm(){
    if($(".sfxmTr").length == 0){
        ityzl_SHOW_WARN_LAYER("请新增收费项目后，在进行保存");
        return;
    }
    var gzlslidList = [];
    if(!isNotBlank(gzlslids)){
        ityzl_SHOW_WARN_LAYER("未获取到工作流实例ID信息");
        return;
    }
    gzlslidList = gzlslids.split(",");

    addModel();

    var sfxmxxList = [];
    $(".sfxmTr").each(function(index, ele){
        var sfxmArray = $(ele).find(".sfxm").serializeArray();
        var sfxmxx = {};
        if (sfxmArray.length > 0) {
            sfxmArray.forEach(function (item, index) {
                sfxmxx[item.name] = item.value;
            });
            sfxmxx.ssje = sfxmxx.ysje;
            sfxmxx.qlrlb = "1";
        }
        sfxmxxList.push(sfxmxx);
    });
    console.info(sfxmxxList);

    var plxgxx = {
        gzlslidList : gzlslidList,
        bdcSlSfxmDOList : sfxmxxList,
        xgjfyy : $("#xgjfyy").val(),
        bz:  $("#bz").val()
    }
    $.ajax({
        url: getContextPath() + "/sf/plxg/sfxm",
        type: 'PATCH',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(plxgxx),
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("修改成功");
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}
