$(function () {
    var tableName;
    var xmids = sessionStorage.getItem('plxg_xmids');
    // 用于标识是否要将查询的数据展示在表单中（指定xmid展示，否则默认都为空）
    var renderFormVal = false;
    layui.use(['form', 'jquery', 'laydate', 'element', 'layer'], function () {
        var $ = layui.jquery,
            form = layui.form,
            layer = layui.layer,
            laydate = layui.laydate;


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
                    $.when(saveXmxx(), saveQlxx(), saveJyxxPl(),saveXmfb()).done(function () {
                        removeModal();
                        ityzl_SHOW_SUCCESS_LAYER("保存成功");
                    })
                } catch (e) {
                    removeModal();
                    delAjaxErrorMsg(e, "保存失败");
                    return
                }
            }, 10);
            return false;
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
        if (isNotBlank(xmids)) {
            var xmidArr = xmids.split(",");
            if (xmidArr.length == 1 && isNotBlank(xmidArr[0])) {
                xmid = xmidArr[0];
                renderFormVal = true;
            }
        }
        loadZdData();


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
                    getPlxgXx();

                }
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });

    }

//加载页面数据
    function generateXx(data) {
        layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
            var form = layui.form;
            var laytpl = layui.laytpl;
            var tpl = bdcdyxxTpl.innerHTML, view = document.getElementById('bdcdyxx');
            //渲染数据
            laytpl(tpl).render(zdList, function (html) {
                view.innerHTML = html;
            });
            if (isNotBlank(tableName)) {
                var qllxTpl = document.getElementById(tableName);
                if (qllxTpl != null) {
                    //目前暂不支持所有权利的批量修改，不支持不展示
                    tpl = qllxTpl.innerHTML, view = document.getElementById('qlxx');
                    //渲染数据
                    laytpl(tpl).render(zdList, function (html) {
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


            if (renderFormVal) {
                form.val('form', data.bdcXmDO);
                form.val('form', data.bdcQl);
            }
            // 交易信息要赋值和主页面保存一致
            form.val('form', data.bdcSlJyxxDO);
            var $fj = $("textarea[name='fj']");
            $fj.val(data.bdcQl.fj);

        });
    }

     //权利信息保存
    function saveQlxx() {
        if (isNotBlank(tableName)) {
            var qlData = {};
            var qlxxArray = $(".qlxx").serializeArray();
            qlxxArray.forEach(function (item, index) {
                var name = item.name;
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
            }else if(tableName ==="bdc_yy"){
                className = "cn.gtmap.realestate.common.core.domain.BdcYyDO";
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
        var bdcXmData = {};
        var bdcdyxxArray = $(".bdcdyxx").serializeArray();
        bdcdyxxArray.forEach(function (item, index) {
            var name = item.name;
            if (isNotBlank(item.value)) {
                bdcXmData[name] = item.value;
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


    //保存项目附表信息
    function saveXmfb() {
        var bdcXmfbData = {};
        var bdcXmfbArray = $(".xmfb").serializeArray();
        bdcXmfbArray.forEach(function (item, index) {
            var name = item.name;
            //权力其他状况或者附记页面加载以赋值，可以修改为清空
            if (isNotBlank(item.value) || name === "qlqtzk" || name === "fj") {
                bdcXmfbData[name] = item.value;
            }
        });

        if (!$.isEmptyObject(bdcXmfbData)) {
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
            bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmfbData);
            getReturnData("/slym/xm/plxgxmfb", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
            }, function (err) {
                throw err;
            });
        }
    }

    //交易信息保存(批量页面）
    function saveJyxxPl() {
        var jyxxData = {};
        var jyxxArray = $(".jyxx");
        if (jyxxArray !== null && jyxxArray.length > 0) {
            jyxxArray.serializeArray().forEach(function (item, index) {
                jyxxData[item.name] = item.value;
            });
            //交易合同号同步交易信息表
            if (isNotBlank(jyxxData.jyhth)) {
                jyxxData.htbah = jyxxData.jyhth;
            }
            var url;
            if (renderFormVal) {
                url = "/ycsl/jyxx/xm?xmid=" + xmid;
            } else {
                url = "/ycsl/jyxx/list?processInsId=" + processInsId;
            }
            getReturnData(url, JSON.stringify(jyxxData), 'PATCH', function (data) {

            }, function (err) {
                throw err;
            });
        }

    }

// 获取批量修改的信息（包含表名，以及当前xmid的信息）
    function getPlxgXx() {
        $.ajax({
            url: getContextPath() + "/slym/ql/plxgXx",
            type: 'GET',
            dataType: "json",
            data: {xmid: xmid, zxlc: zxlc},
            success: function (data) {
                tableName = data.tableName;
                removeModal();
                generateXx(data);
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }
});

