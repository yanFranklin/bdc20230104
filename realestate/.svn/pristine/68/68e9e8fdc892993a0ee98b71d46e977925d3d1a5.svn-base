var tableName;
var bdcslxx;
var processInsId = getQueryString("processInsId");
var xmid = getQueryString("xmid");
var formStateId = getQueryString("formStateId");
var zxlc = getQueryString("zxlc");
layui.use(['form', 'jquery', 'laydate', 'element', 'layer'], function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate;

    $(function () {

        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-nav-container');

        function defaultStyle() {
            if ($cnotentTitle.length == 1) {
                $('.bdc-form-div').css('padding-top', '68px');
            }
            if ($(window).scrollTop() > 85) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '58px');
            } else if ($(window).scrollTop() <= 85) {
                $cnotentTitle.css('top', '10px');
                $navContainer.css('top', '68px');
            }
        }

        $(window).resize(function () {
            defaultStyle();
        });
        $(window).on('scroll', function () {
            if ($(this).scrollTop() > 85) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '58px');
            } else if ($(this).scrollTop() <= 85) {
                $cnotentTitle.css('top', '10px');
                $navContainer.css('top', '68px');
            }
        });


        form.on("submit(save)", function (data) {
            addModel();
            setTimeout(function () {
                try {
                    $.when(saveXmxx(), saveQlxx()).done(function () {
                        removeModal();
                        ityzl_SHOW_SUCCESS_LAYER("保存成功");
                        loadZdData();
                    })
                } catch (e) {
                    removeModal();
                    if (e.message) {
                        showAlertDialog(e.message);
                    } else {
                        delAjaxErrorMsg(e);

                    }
                    return
                }
            }, 10);
            return false;
        });

        // 点击房屋结构，房屋结构名称做出改变
        form.on('select(fwjg)', function (data) {
            var selected_value = data.value;
            var selected_text = $(data.elem).parents('div.layui-inline').find('dd[lay-value=' + selected_value + ']').text().trim();
            var $this_fwjg_outer_dom = $(data.elem).parents('div.layui-inline');
            var $this_fwjgmc_outer_dom = $this_fwjg_outer_dom.parent().find('[name="fwjgmc"]').parents('div.layui-inline');

            if (selected_value == 6) {
                $this_fwjgmc_outer_dom.css({
                    'display': 'block'
                });
                $this_fwjgmc_outer_dom.find('[name="fwjgmc"]').val('');
                $this_fwjgmc_outer_dom.find('[name="fwjgmc"]').attr('value', '')
            } else {
                $this_fwjgmc_outer_dom.css({
                    'display': 'none'
                });
                $this_fwjgmc_outer_dom.find('[name="fwjgmc"]').val(selected_text);
                $this_fwjgmc_outer_dom.find('[name="fwjgmc"]').attr('value', selected_text);
            }
        });

        // 点击规划用途，规划用途名称做出改变，定着物用途也跟着改变
        form.on('select(fwyt)', function (data) {
            var selected_value = data.value;
            var selected_text = $(data.elem).parents('div.layui-inline').find('dd[lay-value=' + selected_value + ']').text().trim();
            var $this_ghyt_outer_dom = $(data.elem).parents('div.layui-inline');
            var $this_ghytmc_outer_dom = $this_ghyt_outer_dom.parent().find('[name="ytmc"]').parents('div.layui-inline');

            if (selected_value == 80) {
                $this_ghytmc_outer_dom.css({
                    'display': 'block'
                });
                $this_ghytmc_outer_dom.find('[name="ytmc"]').val('');
                $this_ghytmc_outer_dom.find('[name="ytmc"]').attr('value', '')
            } else {
                $this_ghytmc_outer_dom.css({
                    'display': 'none'
                });
                $this_ghytmc_outer_dom.find('[name="ytmc"]').val(selected_text);
                $this_ghytmc_outer_dom.find('[name="ytmc"]').attr('value', selected_text);
            }
            // 定着物用途名称变化
            $("#yt").val(selected_value);
            // 显示输入框
            if (selected_value=="80"){
                var $this_dzwytmc_outer_dom = $("#dzwytmc").parents('div.layui-inline');
                $this_dzwytmc_outer_dom.css({
                    'display': 'block'
                });
            }
            form.render("select");
        });

        // 点击定着物用途，定着物用途名称做出改变
        form.on('select(yt)', function (data) {
            var selected_value = data.value;
            var selected_text = $(data.elem).parents('div.layui-inline').find('dd[lay-value=' + selected_value + ']').text().trim();
            var $this_dzwyt_outer_dom = $(data.elem).parents('div.layui-inline');
            var $this_dzwytmc_outer_dom = $this_dzwyt_outer_dom.parent().find('[name="dzwytmc"]').parents('div.layui-inline');

            if (selected_value == 80) {
                $this_dzwytmc_outer_dom.css({
                    'display': 'block'
                });
                $this_dzwytmc_outer_dom.find('[name="dzwytmc"]').val('');
                $this_dzwytmc_outer_dom.find('[name="dzwytmc"]').attr('value', '')
            } else {
                $this_dzwytmc_outer_dom.css({
                    'display': 'none'
                });
                $this_dzwytmc_outer_dom.find('[name="dzwytmc"]').val(selected_text);
                $this_dzwytmc_outer_dom.find('[name="dzwytmc"]').attr('value', selected_text);
            }
        });

            //全选监听
        form.on('checkbox(checkFilter)', function (data) {
            if (data.elem.checked) {
                $(this).parent().find("input[type='checkbox']").each(function () {
                    $(this).next().addClass("layui-form-checked");
                    $(this).prop("checked", true);
                });

            } else {
                $(this).parent().find("input[type='checkbox']").each(function () {
                    $(this).next().removeClass("layui-form-checked");
                    $(this).prop("checked", false);
                });
            }
        });
        loadZdData();
    });

});
var zdList;

function loadZdData() {
    addModel();
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
                getTableName();
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

}

//判断表名
function getTableName() {
    tableName = "bdc_sl_fwxx";
    removeModal();
    generateXx(tableName);
    //获取当前的项目信息
    getPlxgXx();

}


// 获取批量修改的信息（包含表名，以及当前xmid的信息）
function getPlxgXx() {
    var xmids = sessionStorage.getItem('plxg_xmids');
    $.ajax({
        url: getContextPath() + "/slym/ql/zdjbdb/batch/plxgXx",
        type: 'GET',
        dataType: "json",
        data: {xmids: xmids, processInsId: processInsId},
        async: false,
        success: function (data) {
            bdcslxx = data;
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        //处理不动产单元信息
        $("#zdzhmj").attr("placeholder", getBatchAttr(bdcslxx.bdcSlXmDOList, "zdzhmj"));
        $("#zdzhmj").attr("title", getBatchAttr(bdcslxx.bdcSlXmDOList, "zdzhmj"));

        $("#dzwmj").attr("placeholder", getBatchAttr(bdcslxx.bdcSlXmDOList, "dzwmj"));
        $("#dzwmj").attr("title", getBatchAttr(bdcslxx.bdcSlXmDOList, "dzwmj"));

        $("#zdzhyt").prepend("<option value=\"\">" + getBatchSelectAttr(bdcslxx.bdcSlXmDOList, "zdzhyt", zdList.tdyt) + "</option>");
        $("#zdzhyt").attr("title", getBatchSelectAttr(bdcslxx.bdcSlXmDOList, "zdzhyt", zdList.tdyt));

        $("#yt").prepend("<option value=\"\">" + getBatchSelectAttr(bdcslxx.bdcSlXmDOList, "yt", zdList.fwyt) + "</option>");
        $("#yt").attr("title", getBatchSelectAttr(bdcslxx.bdcSlXmDOList, "yt", zdList.fwyt));
        // 如果定着物用途是其他，展示用途名称
        if ($("#yt").attr("title") == "当前值:其它" || $("#yt").attr("title") == "当前值:其他") {
            var $this_dzwytmc_outer_dom = $("#dzwytmc").parents('div.layui-inline');
            $this_dzwytmc_outer_dom.css({
                'display': 'block'
            });
            $("#dzwytmc").attr("placeholder", getBatchAttr(bdcslxx.bdcSlXmDOList, "dzwytmc"));
            $("#dzwytmc").attr("title", getBatchAttr(bdcslxx.bdcSlXmDOList, "dzwytmc"));
        }


        $("#qlxz").prepend("<option value=\"\">" + getBatchSelectAttr(bdcslxx.bdcSlXmDOList, "qlxz", zdList.qlxz) + "</option>");
        $("#qlxz").attr("title", getBatchSelectAttr(bdcslxx.bdcSlXmDOList, "qlxz", zdList.qlxz));
        //处理权利信息
        if (isNotBlank(tableName)) {
            if (tableName == "bdc_sl_fwxx") {
                $("#tdsyqssj").attr("placeholder", getBatchDataAttr(bdcslxx.bdcSlFwxxDO, "tdsyqssj"));
                $("#tdsyqssj").attr("title", getBatchDataAttr(bdcslxx.bdcSlFwxxDO, "tdsyqssj"));

                $("#tdsyjssj").attr("placeholder", getBatchDataAttr(bdcslxx.bdcSlFwxxDO, "tdsyjssj"));
                $("#tdsyjssj").attr("title", getBatchDataAttr(bdcslxx.bdcSlFwxxDO, "tdsyjssj"));

                $("#fwlx").prepend("<option value=\"\">" + getBatchSelectAttr(bdcslxx.bdcSlFwxxDO, "fwlx", zdList.fwlx) + "</option>");
                $("#fwlx").attr("title", getBatchSelectAttr(bdcslxx.bdcSlFwxxDO, "fwlx", zdList.fwlx));

                $("#fwxz").prepend("<option value=\"\">" + getBatchSelectAttr(bdcslxx.bdcSlFwxxDO, "fwxz", zdList.fwxz) + "</option>");
                $("#fwxz").attr("title", getBatchSelectAttr(bdcslxx.bdcSlFwxxDO, "fwxz", zdList.fwxz));

                // $("#zh").attr("placeholder", getBatchAttr(bdcslxx.bdcSlFwxxDO, "zh"));
                // $("#zh").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "zh"));
                // $("#fjh").attr("placeholder", getBatchAttr(bdcslxx.bdcSlFwxxDO, "fjh"));
                // $("#fjh").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "fjh"));
                // $("#jzmj").attr("placeholder", getBatchAttr(bdcslxx.bdcSlFwxxDO, "jzmj"));
                // $("#jzmj").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "jzmj"));
                // $("#ftjzmj").attr("placeholder", getBatchAttr(bdcslxx.bdcSlFwxxDO, "ftjzmj"));
                // $("#ftjzmj").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "ftjzmj"));

                $("#zcs").attr("placeholder", getBatchAttr(bdcslxx.bdcSlFwxxDO, "zcs"));
                $("#zcs").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "zcs"));

                $("#myzcs").attr("placeholder", getBatchAttr(bdcslxx.bdcSlFwxxDO, "myzcs"));
                $("#myzcs").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "myzcs"));

                $("#szc").attr("placeholder", getBatchAttr(bdcslxx.bdcSlFwxxDO, "szc"));
                $("#szc").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "szc"));


                $("#szmyc").attr("placeholder", getBatchAttr(bdcslxx.bdcSlFwxxDO, "szmyc"));
                $("#szmyc").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "szmyc"));


                // $("#cg").prepend("<option value=\"\">" + getBatchAttr(bdcslxx.bdcSlFwxxDO, "cg") + "</option>");
                // $("#cg").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "cg"));

                $("#fwjg").prepend("<option value=\"\">" + getBatchSelectAttr(bdcslxx.bdcSlFwxxDO, "fwjg", zdList.fwjg) + "</option>");
                $("#fwjg").attr("title", getBatchSelectAttr(bdcslxx.bdcSlFwxxDO, "fwjg", zdList.fwjg));

                $("#fwjgmc").attr("placeholder", getBatchAttr(bdcslxx.bdcSlFwxxDO, "fwjgmc"));
                $("#fwjgmc").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "fwjgmc"));

                $("#fwyt").prepend("<option value=\"\">" + getBatchSelectAttr(bdcslxx.bdcSlFwxxDO, "fwyt", zdList.fwyt) + "</option>");
                $("#fwyt").attr("title", getBatchSelectAttr(bdcslxx.bdcSlFwxxDO, "fwyt", zdList.fwyt));
                // 如果规划用途是其他，展示用途名称
                if ($("#fwyt").attr("title") == "当前值:其它" || $("#fwyt").attr("title") == "当前值:其他") {
                    var $this_ghytmc_outer_dom = $("#ytmc").parents('div.layui-inline');
                    $this_ghytmc_outer_dom.css({
                        'display': 'block'
                    });
                    $("#ytmc").attr("placeholder", getBatchAttr(bdcslxx.bdcSlFwxxDO, "ytmc"));
                    $("#ytmc").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "ytmc"));
                }

                $("#tdsyqr").attr("placeholder", getBatchAttr(bdcslxx.bdcSlFwxxDO, "tdsyqr"));
                $("#tdsyqr").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "tdsyqr"));

                $("#jyjg").attr("placeholder", getBatchAttr(bdcslxx.bdcSlFwxxDO, "jyjg"));
                $("#jyjg").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "jyjg"));

                $("#jgsj").attr("placeholder", getBatchDataAttr(bdcslxx.bdcSlFwxxDO, "jgsj"));
                $("#jgsj").attr("title", getBatchDataAttr(bdcslxx.bdcSlFwxxDO, "jgsj"));

                $("#fj").attr("placeholder", getBatchAttr(bdcslxx.bdcSlFwxxDO, "fj"));
                $("#fj").attr("title", getBatchAttr(bdcslxx.bdcSlFwxxDO, "fj"));
            }
        }
        // 处理房屋结构名称
        initFwjgmc();
        form.render("select");
        form.render("checkbox");
        //给下拉框添加删除图标
        renderSelectClose(form);
        renderDate(form);

        $("select").each(function () {
            var attr = $(this).attr("title");
            $(this).parent().find("input").attr("title",attr);
        })
    })
}


//加载页面数据
function generateXx(tableName) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var tpl = bdcdyxxTpl.innerHTML, view = document.getElementById('bdcdyxx');
        var json = {
            // cglist: parent.cgList,
            zdlist: zdList
        };
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        if (isNotBlank(tableName)) {
            var qllxTpl = document.getElementById(tableName);
            if (qllxTpl != null) {
                //目前暂不支持所有权利的批量修改，不支持不展示
                tpl = qllxTpl.innerHTML, view = document.getElementById('qlxx');
                //渲染数据
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
            }
        }
        form.render("select");
        form.render("checkbox");
        //给下拉框添加删除图标
        renderSelectClose(form);
        renderDate(form);
        getStateById("", formStateId, 'plxg');
    })

}

//权利信息保存
function saveQlxx() {
    var xmids = sessionStorage.getItem('plxg_xmids');
    if (isNotBlank(tableName)) {
        var qlData = {};
        var qlxxArray = $(".qlxx").serializeArray();
        qlxxArray.forEach(function (item, index) {
            var name = item.name;
            if($("input:checkbox[name="+name+"1]").prop("checked")){
                qlData[name] = item.value;
            }
            if (isNotBlank(item.value)) {
                qlData[name] = item.value;
            }
        });
        var className = "";
        if (tableName === "bdc_sl_fwxx") {
            className = "cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO";
        }
        if (!$.isEmptyObject(qlData) && isNotBlank(className)) {
            //验证所在层与总层数
            checkSzcAndZcs(qlData.szc, qlData.zcs);
            var bdcDjxxUpdateQO = {};
            var whereMap = {};
            whereMap.gzlslid = processInsId;
            if (isNotBlank(xmids)) {
                whereMap.xmids = xmids.split(",");
            }

            bdcDjxxUpdateQO.whereMap = whereMap;
            bdcDjxxUpdateQO.className = className;
            bdcDjxxUpdateQO.jsonStr = JSON.stringify(qlData);
            getReturnData("/slym/xm/updateBatchBdcSlFwxx", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {

            }, function (err) {
                throw err;
            });
        }
    }


}

//项目信息保存
function saveXmxx() {
    var xmids = sessionStorage.getItem('plxg_xmids');
    var bdcXmData = {};
    var bdcdyxxArray = $(".bdcdyxx").serializeArray();
    var ghytmc = $("#ytmc").val();
    var ghyt = $("#fwyt").val();
    bdcdyxxArray.forEach(function (item, index) {
        var name = item.name;
        if($("input:checkbox[name="+name+"1]").prop("checked")){
            bdcXmData[name] = item.value;
        }
        if (isNotBlank(item.value)) {
            bdcXmData[name] = item.value;
        }
        // 如果规划用途是其他，定着物用途名称和规划用途名称一致
        if (ghyt=="80" && name=="dzwytmc"){
            bdcXmData[name] = ghytmc
        }

    });
    if (!$.isEmptyObject(bdcXmData)) {
        var bdcDjxxUpdateQO = {};
        var whereMap = {};
        whereMap.gzlslid = processInsId;
        if (isNotBlank(xmids)) {
            whereMap.xmids = xmids.split(",");
        }

        bdcDjxxUpdateQO.whereMap = whereMap;
        bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmData);
        getReturnData("/slym/xm/updateBatchBdcSlXm", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {

        }, function (err) {
            throw err;
        });
    }

}


function getBatchAttr(data, attr) {
    var str = "当前值:";
    var existValue = [];
    var changed = false;
    var flag = false;
    for (let i = 0; i < data.length; i++) {
        if (!isNullOrEmpty(data[i][attr])) {
            if (existValue.indexOf(data[i][attr]) < 0) {
                str += data[i][attr] + ",";
                existValue.push(data[i][attr]);
                changed = true;
            }
        }else {
            flag = true;
        }
    }
    if (!changed) {
        str = "当前无值"
    }
    if(changed && flag){
        str = str + "空值";
    }
    if (str.lastIndexOf(",")===str.length-1) {
        str = str.substring(0, str.length - 1);
    }
    return str;
}

function getBatchDataAttr(data, attr) {
    var str = "当前值:";
    var existValue = [];
    var changed = false;
    var flag = false;
    for (let i = 0; i < data.length; i++) {
        if (!isNullOrEmpty(data[i][attr])) {
            if (existValue.indexOf(data[i][attr]) < 0) {
                if (data[i][attr].indexOf("00:00:00")!=-1) {
                    str += data[i][attr].substring(0, data[i][attr].length - 9) + ",";
                } else {
                    str += data[i][attr] + ",";
                }
                existValue.push(data[i][attr]);
                changed = true;
            }
        }else {
            flag = true;
        }
    }
    if (!changed) {
        str = "当前无值"
    }
    if(changed && flag){
        str = str + "空值";
    }
    if (str.lastIndexOf(",")===str.length-1) {
        str = str.substring(0, str.length - 1);
    }
    return str;
}

function getBatchSelectAttr(data, attr, zdlist) {
    var str = "当前值:";
    var existValue = [];
    var changed = false;
    var flag = false;
    for (let i = 0; i < data.length; i++) {
        if (!isNullOrEmpty(data[i][attr]) || (data[i][attr] >= 0)) {
            for (let j = 0; j < zdlist.length; j++) {
                if (zdlist[j].DM == data[i][attr]) {
                    if (existValue.indexOf(data[i][attr]) < 0) {
                        str += zdlist[j].MC + ",";
                        existValue.push(data[i][attr]);
                        changed = true;
                    }
                }
            }
        }else {
            flag = true;
        }
    }
    if (!changed) {
        str = "当前无值,请选择"
    }
    if(changed && flag){
        str = str + "空值";
    }
    if (str.lastIndexOf(",")===str.length-1) {
        str = str.substring(0, str.length - 1);
    }
    return str;
}


/**
 * 处理房屋结构名称
 */
function initFwjgmc() {
    if ($('[name="fwjgmc"]').length > 0) {
        $.each($('[name="fwjgmc"]'), function (index, item) {
            var fwjg_title = $(item).parents('div.layui-inline').parent().find('select[name="fwjg"]').attr('title');
            if (fwjg_title.indexOf("其它结构") != -1) {
                $(item).parents('div.layui-inline').css({
                    'display': 'block'
                });
            } else {
                $(item).parents('div.layui-inline').css({
                    'display': 'none'
                });
            }

        });
    }
}

// 规划用途输入时同步定着物用途
function tbDzwytmc(value){
    $("#dzwytmc").val(value);
}

/**
 * 验证所在层与总层数,验证规则：所在层不能大于总层数，所在层不能为0
 */
function checkSzcAndZcs(szc, zcs) {
    if (szc === "0") {
        throw error = new Error("所在层不能为0");
    }
    if (isNotBlank(szc) && isNotBlank(zcs) && parseInt(szc) > parseInt(zcs)) {
        throw error = new Error("所在层不能大于总层数");
    }
}