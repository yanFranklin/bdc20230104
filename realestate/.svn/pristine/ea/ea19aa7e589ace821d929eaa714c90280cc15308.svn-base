var tableName;
var bdcxx;
var processInsId = getQueryString("processInsId");
var xmid = getQueryString("xmid");
var formStateId = getQueryString("formStateId");
var zxlc = getQueryString("zxlc");
var sfdydj = getQueryString("sfdydj");
var djxl = getQueryString("djxl");
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

        form.verify({
            //要求正整数，允许为空
            number: function (value, item) {
                if (isNotBlank(value) && !/^[1-9]+[0-9]*]*$/.test(value)) {
                    $(item).addClass('layui-form-danger');
                    return "请输入非负整数";
                }
            },
            required: function (value, item) {
                //批量修改有必填样式，但不验证必填
                return "";
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
        form.on('select(ghyt)', function (data) {
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
            $("#dzwyt").val(selected_value);
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
        form.on('select(dzwyt)', function (data) {
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


        // 点击房屋性质，房屋性质名称做出改变
        form.on('select(fwxz)', function (data) {
            var selected_value = data.value;
            var selected_text = $(data.elem).parents('div.layui-inline').find('dd[lay-value=' + selected_value + ']').text().trim();
            var $this_fwxz_outer_dom = $(data.elem).parents('div.layui-inline');
            var $this_fwxzmc_outer_dom = $this_fwxz_outer_dom.parent().find('[name="fwxzmc"]').parents('div.layui-inline');

            if (selected_value == 99) {
                $this_fwxzmc_outer_dom.css({
                    'display': 'block'
                });
                $this_fwxzmc_outer_dom.find('[name="fwxzmc"]').val('');
                $this_fwxzmc_outer_dom.find('[name="fwxzmc"]').attr('value', '')
            } else {
                $this_fwxzmc_outer_dom.css({
                    'display': 'none'
                });
                $this_fwxzmc_outer_dom.find('[name="fwxzmc"]').val(selected_text);
                $this_fwxzmc_outer_dom.find('[name="fwxzmc"]').attr('value', selected_text);
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

//加载页面数据
function generateXx(tableName) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var tpl = bdcdyxxTpl.innerHTML, view = document.getElementById('bdcdyxx');
        var json = {
            cglist: parent.cgList,
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
        if (tableName === "bdc_fdcq") {
            className = "cn.gtmap.realestate.common.core.domain.BdcFdcqDO";
        } else if (tableName === "bdc_dyaq") {
            className = "cn.gtmap.realestate.common.core.domain.BdcDyaqDO";
        } else if (tableName === "bdc_jsydsyq") {
            className = "cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO";
        } else if (tableName === "bdc_cf") {
            className = "cn.gtmap.realestate.common.core.domain.BdcCfDO";
        } else if (tableName === "bdc_yg") {
            className = "cn.gtmap.realestate.common.core.domain.BdcYgDO";
        } else if (tableName === "bdc_fdcq3") { // 同步更新共有信息数据
            className = "cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO";
            qlData.jgzwmj = $("input[name='dzwmj']").val();
        } else if (tableName === "bdc_jzq") {
            className = "cn.gtmap.realestate.common.core.domain.BdcJzqDO";
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
            if (isNotBlank(djxl)) {
                whereMap.djxl = djxl;
            }
            bdcDjxxUpdateQO.whereMap = whereMap;
            bdcDjxxUpdateQO.className = className;
            bdcDjxxUpdateQO.jsonStr = JSON.stringify(qlData);
            getReturnData("/slym/ql/updateBatchBdcQl", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {

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
    var ghyt = $("#ghyt").val();
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
        if (isNotBlank(djxl)) {
            whereMap.djxl = djxl;
        }
        bdcDjxxUpdateQO.whereMap = whereMap;
        bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmData);
        getReturnData("/slym/xm/updateBatchBdcXm", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {

        }, function (err) {
            throw err;
        });
    }

}

//判断表名
function getTableName() {
    $.ajax({
        url: getContextPath() + "/slym/ql/tableName",
        type: 'GET',
        data: {xmid: xmid, zxlc: zxlc},
        success: function (data) {
            tableName = data;
            removeModal();
            generateXx(data);
            //获取当前的项目信息
            getPlxgXx();
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}


// 获取批量修改的信息（包含表名，以及当前xmid的信息）
function getPlxgXx() {
    var xmids = sessionStorage.getItem('plxg_xmids');
    $.ajax({
        url: getContextPath() + "/slym/ql/batch/plxgXx",
        type: 'GET',
        dataType: "json",
        data: {xmids: xmids, zxlc: zxlc, processInsId: processInsId},
        async: false,
        success: function (data) {
            bdcxx = data;
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        //处理不动产单元信息
        $("#zdzhmj").attr("placeholder", getBatchAttr(bdcxx.bdcXmDOs, "zdzhmj"));
        $("#zdzhmj").attr("title", getBatchAttr(bdcxx.bdcXmDOs, "zdzhmj"));

        $("#dzwmj").attr("placeholder", getBatchAttr(bdcxx.bdcXmDOs, "dzwmj"));
        $("#dzwmj").attr("title", getBatchAttr(bdcxx.bdcXmDOs, "dzwmj"));

        $("#zdzhyt").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcXmDOs, "zdzhyt", zdList.tdyt) + "</option>");
        $("#zdzhyt").attr("title", getBatchSelectAttr(bdcxx.bdcXmDOs, "zdzhyt", zdList.tdyt));

        $("#dzwyt").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcXmDOs, "dzwyt", zdList.fwyt) + "</option>");
        $("#dzwyt").attr("title", getBatchSelectAttr(bdcxx.bdcXmDOs, "dzwyt", zdList.fwyt));
        // 如果定着物用途是其他，展示用途名称
        if ($("#dzwyt").attr("title") == "当前值:其它" || $("#dzwyt").attr("title") == "当前值:其他") {
            var $this_dzwytmc_outer_dom = $("#dzwytmc").parents('div.layui-inline');
            $this_dzwytmc_outer_dom.css({
                'display': 'block'
            });
            $("#dzwytmc").attr("placeholder", getBatchAttr(bdcxx.bdcXmDOs, "dzwytmc"));
            $("#dzwytmc").attr("title", getBatchAttr(bdcxx.bdcXmDOs, "dzwytmc"));
        }


        $("#qlxz").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcXmDOs, "qlxz", zdList.qlxz) + "</option>");
        $("#qlxz").attr("title", getBatchSelectAttr(bdcxx.bdcXmDOs, "qlxz", zdList.qlxz));
        //处理权利信息
        if (isNotBlank(tableName)) {
            if (tableName == "bdc_fdcq") {
                $("#tdsyqssj").attr("placeholder", getBatchDataAttr(bdcxx.bdcQls, "tdsyqssj"));
                $("#tdsyqssj").attr("title", getBatchDataAttr(bdcxx.bdcQls, "tdsyqssj"));

                $("#tdsyjssj").attr("placeholder", getBatchDataAttr(bdcxx.bdcQls, "tdsyjssj"));
                $("#tdsyjssj").attr("title", getBatchDataAttr(bdcxx.bdcQls, "tdsyjssj"));

                $("#fwlx").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcQls, "fwlx", zdList.fwlx) + "</option>");
                $("#fwlx").attr("title", getBatchSelectAttr(bdcxx.bdcQls, "fwlx", zdList.fwlx));

                $("#fwxz").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcQls, "fwxz", zdList.fwxz) + "</option>");
                $("#fwxz").attr("title", getBatchSelectAttr(bdcxx.bdcQls, "fwxz", zdList.fwxz));

                $("#fwxzmc").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "fwxzmc"));
                $("#fwxzmc").attr("title", getBatchAttr(bdcxx.bdcQls, "fwxzmc"));

                $("#zcs").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "zcs"));
                $("#zcs").attr("title", getBatchAttr(bdcxx.bdcQls, "zcs"));

                $("#myzcs").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "myzcs"));
                $("#myzcs").attr("title", getBatchAttr(bdcxx.bdcQls, "myzcs"));

                $("#szc").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "szc"));
                $("#szc").attr("title", getBatchAttr(bdcxx.bdcQls, "szc"));


                $("#szmyc").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "szmyc"));
                $("#szmyc").attr("title", getBatchAttr(bdcxx.bdcQls, "szmyc"));


                $("#cg").prepend("<option value=\"\">" + getBatchAttr(bdcxx.bdcQls, "cg") + "</option>");
                $("#cg").attr("title", getBatchAttr(bdcxx.bdcQls, "cg"));

                $("#fwjg").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcQls, "fwjg", zdList.fwjg) + "</option>");
                $("#fwjg").attr("title", getBatchSelectAttr(bdcxx.bdcQls, "fwjg", zdList.fwjg));

                $("#fwjgmc").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "fwjgmc"));
                $("#fwjgmc").attr("title", getBatchAttr(bdcxx.bdcQls, "fwjgmc"));

                $("#ghyt").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcQls, "ghyt", zdList.fwyt) + "</option>");
                $("#ghyt").attr("title", getBatchSelectAttr(bdcxx.bdcQls, "ghyt", zdList.fwyt));
                // 如果规划用途是其他，展示用途名称
                if ($("#ghyt").attr("title") == "当前值:其它" || $("#ghyt").attr("title") == "当前值:其他") {
                    var $this_ghytmc_outer_dom = $("#ytmc").parents('div.layui-inline');
                    $this_ghytmc_outer_dom.css({
                        'display': 'block'
                    });
                    $("#ytmc").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "ytmc"));
                    $("#ytmc").attr("title", getBatchAttr(bdcxx.bdcQls, "ytmc"));
                }

                $("#tdsyqr").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "tdsyqr"));
                $("#tdsyqr").attr("title", getBatchAttr(bdcxx.bdcQls, "tdsyqr"));

                $("#jyjg").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "jyjg"));
                $("#jyjg").attr("title", getBatchAttr(bdcxx.bdcQls, "jyjg"));

                $("#jgsj").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "jgsj"));
                $("#jgsj").attr("title", getBatchAttr(bdcxx.bdcQls, "jgsj"));

                $("#fj").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "fj"));
                $("#fj").attr("title", getBatchAttr(bdcxx.bdcQls, "fj"));
            } else if (tableName == "bdc_fdcq3") {
                $("#fj").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "fj"));
                $("#fj").attr("title", getBatchAttr(bdcxx.bdcQls, "fj"));
            } else if (tableName == "bdc_dyaq") {
                $("#zwlxqssj").attr("placeholder", getBatchDataAttr(bdcxx.bdcQls, "zwlxqssj"));
                $("#zwlxqssj").attr("title", getBatchDataAttr(bdcxx.bdcQls, "zwlxqssj"));

                $("#zwlxjssj").attr("placeholder", getBatchDataAttr(bdcxx.bdcQls, "zwlxjssj"));
                $("#zwlxjssj").attr("title", getBatchDataAttr(bdcxx.bdcQls, "zwlxjssj"));

                $("#bdbzzqse").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "bdbzzqse"));
                $("#bdbzzqse").attr("title", getBatchAttr(bdcxx.bdcQls, "bdbzzqse"));

                $("#jyhth").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "jyhth"));
                $("#jyhth").attr("title", getBatchAttr(bdcxx.bdcQls, "jyhth"));

                $("#zjjzwdyfw").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "zjjzwdyfw"));
                $("#zjjzwdyfw").attr("title", getBatchAttr(bdcxx.bdcQls, "zjjzwdyfw"));

                $("#zjjzwzl").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "zjjzwzl"));
                $("#zjjzwzl").attr("title", getBatchAttr(bdcxx.bdcQls, "zjjzwzl"));

                $("#tddymj").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "tddymj"));
                $("#tddymj").attr("title", getBatchAttr(bdcxx.bdcQls, "tddymj"));

                $("#fwdymj").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "fwdymj"));
                $("#fwdymj").attr("title", getBatchAttr(bdcxx.bdcQls, "fwdymj"));

                $("#dysw").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "dysw"));
                $("#dysw").attr("title", getBatchAttr(bdcxx.bdcQls, "dysw"));

                $("#dyaq-fj").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "fj"));
                $("#dyaq-fj").attr("title", getBatchAttr(bdcxx.bdcQls, "fj"));

                $("#dyfs").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcQls, "dyfs", zdList.dyfs) + "</option>");
                $("#dyfs").attr("title", getBatchSelectAttr(bdcxx.bdcQls, "dyfs", zdList.dyfs));

                $("#dkfs").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcQls, "dkfs", zdList.dkfs) + "</option>");
                $("#dkfs").attr("title", getBatchSelectAttr(bdcxx.bdcQls, "dkfs", zdList.dkfs));

                $("#jzzr").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcQls, "jzzr", zdList.sf) + "</option>");
                $("#jzzr").attr("title", getBatchSelectAttr(bdcxx.bdcQls, "jzzr", zdList.sf));
            } else if (tableName == "bdc_jsydsyq") {
                $("#syqqssj").attr("placeholder", getBatchDataAttr(bdcxx.bdcQls, "syqqssj"));
                $("#syqqssj").attr("title", getBatchDataAttr(bdcxx.bdcQls, "syqqssj"));

                $("#syqjssj").attr("placeholder", getBatchDataAttr(bdcxx.bdcQls, "syqjssj"));
                $("#syqjssj").attr("title", getBatchDataAttr(bdcxx.bdcQls, "syqjssj"));
            } else if (tableName == "bdc_cf") {
                $("#cfwh").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "cfwh"));
                $("#cfwh").attr("title", getBatchAttr(bdcxx.bdcQls, "cfwh"));

                $("#cfjg").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "cfjg"));
                $("#cfjg").attr("title", getBatchAttr(bdcxx.bdcQls, "cfjg"));

                $("#cfwj").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "cfwj"));
                $("#cfwj").attr("title", getBatchAttr(bdcxx.bdcQls, "cfwj"));

                $("#cfqssj").attr("placeholder", getBatchDataAttr(bdcxx.bdcQls, "cfqssj"));
                $("#cfqssj").attr("title", getBatchDataAttr(bdcxx.bdcQls, "cfqssj"));

                $("#cfjssj").attr("placeholder", getBatchDataAttr(bdcxx.bdcQls, "cfjssj"));
                $("#cfjssj").attr("title", getBatchDataAttr(bdcxx.bdcQls, "cfjssj"));

                $("#cffw").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "cffw"));
                $("#cffw").attr("title", getBatchAttr(bdcxx.bdcQls, "cffw"));

                $("#jfwh").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "jfwh"));
                $("#jfwh").attr("title", getBatchAttr(bdcxx.bdcQls, "jfwh"));

                $("#jfjg").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "jfjg"));
                $("#jfjg").attr("title", getBatchAttr(bdcxx.bdcQls, "jfjg"));

                $("#jfwj").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "jfwj"));
                $("#jfwj").attr("title", getBatchAttr(bdcxx.bdcQls, "jfwj"));

                $("#cflx").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcQls, "cflx", zdList.cflx) + "</option>");
                $("#cflx").attr("title", getBatchSelectAttr(bdcxx.bdcQls, "cflx", zdList.cflx));

            } else if (tableName == "bdc_yg") {
                $("#qdjg").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "qdjg"));
                $("#qdjg").attr("title", getBatchAttr(bdcxx.bdcQls, "qdjg"));

                $("#yg-zwlxqssj").attr("placeholder", getBatchDataAttr(bdcxx.bdcQls, "zwlxqssj"));
                $("#yg-zwlxqssj").attr("title", getBatchDataAttr(bdcxx.bdcQls, "zwlxqssj"));

                $("#yg-zwlxjssj").attr("placeholder", getBatchDataAttr(bdcxx.bdcQls, "zwlxjssj"));
                $("#yg-zwlxjssj").attr("title", getBatchDataAttr(bdcxx.bdcQls, "zwlxjssj"));

                $("#yg-myzcs").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "myzcs"));
                $("#yg-myzcs").attr("title", getBatchAttr(bdcxx.bdcQls, "myzcs"));

                $("#yg-tdsyqr").attr("placeholder", getBatchAttr(bdcxx.bdcQls, "tdsyqr"));
                $("#yg-tdsyqr").attr("title", getBatchAttr(bdcxx.bdcQls, "tdsyqr"));

                $("#ygdjzl").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcQls, "ygdjzl", zdList.ygdjzl) + "</option>");
                $("#ygdjzl").attr("title", getBatchSelectAttr(bdcxx.bdcQls, "ygdjzl", zdList.ygdjzl));

                $("#yg-dyfs").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcQls, "dyfs", zdList.dyfs) + "</option>");
                $("#yg-dyfs").attr("title", getBatchSelectAttr(bdcxx.bdcQls, "dyfs", zdList.dyfs));

                $("#yg-fwxz").prepend("<option value=\"\">" + getBatchSelectAttr(bdcxx.bdcQls, "fwxz", zdList.fwxz) + "</option>");
                $("#yg-fwxz").attr("title", getBatchSelectAttr(bdcxx.bdcQls, "fwxz", zdList.fwxz));
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
