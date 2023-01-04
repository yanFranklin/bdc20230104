var xmid = getQueryString("xmid");
var lclx = getQueryString("lclx");
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var sqfbcz = getQueryString("sqfbcz");
var djxl = getQueryString("djxl");
var zdList;
var form;
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
//页面入口
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    addModel();
    setTimeout(function () {
        try {
            $.when(loadData()).done(function () {
                removeModal();
            })
        } catch (e) {
            removeModal();
            ERROR_CONFIRM("加载失败", e.message);
            return
        }
    }, 10);
    form.render('select');
});


function loadData() {

    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
                loadAddLzr();
            }
        }
    });

}

function loadQlr() {
    $.ajax({
        url: getContextPath() + "/slym/qlr/list/dsQlr",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid},
        success: function (data) {
            if (isNotBlank(data)) {
                generateLzr(data);
            }
        }
    });
}

function loadAddLzr() {
    var lzr = [];
    lzr["xmid"] = xmid;
    generateLzr(lzr);
}

function generateLzr(lzr) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'formSelects'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var formSelects = layui.formSelects;
        var json = {
            lzr: lzr,
            zd: zdList
        };
        var tpl = addLzrTpl.innerHTML, view = document.getElementById('bdc-popup-large');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        if (!isNotBlank(lzr.xmid)) {
            $("#xmid").val(xmid);
        }
        form.on('select(zjzl)', function (data) {
            addSfzYz(data.value, data.elem);
        });
        var result = verifyform(form);

        getStateById(readOnly, formStateId, "lzr");
        //在设置权限后设置判断身份证验证，防止必填冲掉身份证验证
        $("[lay-filter=zjzl]").each(function () {
            addSfzYz($(this).val(), $(this));
        });
        formSelects.render('qlrmc', {
            create: function (id, name) {
                // console.log(id);    //多选id
                // console.log(name);  //创建的标签名称
                return name;  //返回该标签对应的val
            }
        });
        //渲染下拉框数据
        var qlrarr = [];
        //判断当前值是否存在下拉列表，不存在则新增
        if (isNotBlank(sqfbcz) && sqfbcz === "1") {
            $("#qlrxx")[0].classList.remove('layui-hide');
            //查询权利人数据带入
            var qlrArr = getqlrxx();
            for (var i = 0; i < qlrArr.length; i++) {
                qlrarr.push({"name": qlrArr[i].qlrmc, "value": qlrArr[i].qlrmc, "qlrid": qlrArr[i].qlrid});
            }
        } else {
            $($("#qlrxx")[0]).removeAttr('lay-verify', 'required');
        }
        formSelects.data('qlrmc', 'local', {
            arr: qlrarr
        });
        layui.formSelects.on('qlrmc', function (id, vals, val) {
            $("[name='qlrmc']").val(val.value);
            $("#qlrid").val(val.qlrid);
            form.render();
            resetSelectDisabledCss();
        });
        //提交表单
        form.on("submit(saveLzr)", function (data) {
            if (isNotBlank(result) && !result.isSubmit) {
                layer.msg(result.verifyMsg, {icon: 5, time: 3000});
                result.isSubmit = true;
                return false;
            } else {
                addModel();
                setTimeout(function () {
                    try {
                        $.when(insertLzr(data.field)).done(function () {

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
        $('.layui-input').on('focus', function () {
            if ($(this).hasClass('layui-form-danger')) {
                $(this).removeClass('layui-form-danger');
            }
            if ($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')) {
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
        });
        $('.xm-input').on('click', function () {
            if ($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')) {
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
        });

        form.render("select");
        renderSelect();
        disabledAddFa("qlrForm");
    })

}

function insertLzr(bdcLzrData) {
    var lzrList = [];
    //将中文括号转换为英文括号
    var obj = replaceBracketArray();
    Object.keys(obj).forEach(function (key) {
        bdcLzrData[key] = obj[key];
    });
    //申请人名称单独处理
    bdcLzrData.qlrmc = replaceBracket(bdcLzrData.qlrmc);
    if (sqfbcz === "1" && isNullOrEmpty(bdcLzrData.qlrid)) {
        removeModal();
        ityzl_SHOW_WARN_LAYER("分别持证必须选择权利人");
        return false;
    }
    var url = getContextPath() + "/slym/lzr/nt/lzrxx/jdlc?gzlslid=" + processInsId;
    if (lclx === "pllc") {
        url = getContextPath() + "/slym/lzr/nt/lzrxx/pllc?gzlslid=" + processInsId + "&xmid=" + xmid;
    }
    if (lclx === "plzh") {
        url = getContextPath() + "/slym/lzr/nt/lzrxx/plzh?gzlslid=" + processInsId + "&djxl=" + djxl;
    }
    lzrList.push(bdcLzrData);
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(lzrList),
        success: function (data) {
            successCz("");
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }
    });
}


function cancel() {
    window.parent.cancelEdit();
}

function addSfzYz(zjzl, elem) {
    var zjzlid = $(elem).parent().parent().find("select")[0].id;
    var zjhid = zjzlid.replace("zjzl", "zjh");
    var attrVal = $("#" + zjhid).attr("lay-verify");
    if (zjzl === "1" || zjzl === 1) {
        //增加身份证验证
        if (isNotBlank(attrVal)) {
            if (attrVal.indexOf("identitynew") < 0) {
                $("#" + zjhid).attr("lay-verify", attrVal + "|identitynew");
            }
        } else {
            $("#" + zjhid).attr("lay-verify", "identitynew");
        }
    } else {
        //移除身份证验证
        //增加长度大于五位验证
        if (isNotBlank(attrVal) && attrVal.indexOf("identitynew") > -1) {
            $("#" + zjhid).attr("lay-verify", attrVal.replace("identitynew", ""));
        }
        var newattr = $("#" + zjhid).attr("lay-verify");
        if (isNotBlank(newattr) && newattr.indexOf("zjhlength") < 0) {
            $("#" + zjhid).attr("lay-verify", newattr + "|zjhlength");
        } else if (zjzl !== null && zjzl !== "") {
            $("#" + zjhid).attr("lay-verify", "zjhlength");
        } else if (zjzl === null || zjzl === "" && isNotBlank(newattr) && newattr.indexOf("zjhlength") > -1) {
            $("#" + zjhid).attr("lay-verify", attrVal.replace("zjhlength", ""));
        }
    }
}

/**
 * 提交成功操作
 */
function successCz(fun) {
    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">保存成功', {
        time: 1000
    }, function () {
        if (typeof (eval(window.parent.refreshQlxx)) == "function" && lclx === "zhlc") {
            window.parent.addModel();
            window.parent.refreshQlxx();
        }
        if (typeof (eval(window.parent.loadPlQlxx)) == "function" && lclx === "pllc") {
            window.parent.addModel();
            window.parent.loadPlQlxx();
            window.parent.loadQlr();
        }
        if (typeof (eval(window.parent.loadLzr)) == "function" && lclx === "plzh") {
            window.parent.loadLzr();
            window.parent.loadQlr();
        }
        window.parent.cancelEdit();
    });
    removeModal();
}

function nextForm() {
    loadAddLzr();
}

/*
* 根据权利人类型默认证件种类
*/
function getzjzlByQlrlx(qlrlx) {
    //权利人类型为个人时，证件种类默认身份证；权利人类型为企业时，证件种类默认营业执照；其他默认统一社会信息代码
    var qlrlxArr = {
        "1": "1",
        "2": "7",
        "99": "99"
    };
    var zjzlValue = $("#zjzl").val();
    var zjzjMrz = getZjzlMrzByQlrlx(qlrlxArr, qlrlx, "8");
    //个人证件种类
    var grzjzl = ['1', '2', '3', '4', '5'];
    if (isNotBlank(zjzjMrz)) {
        //如果证件种类本身有值，且默认值和原始值均为个人证件种类，则不覆盖
        if (!(isNotBlank(zjzlValue) && grzjzl.indexOf(zjzjMrz) > -1 && grzjzl.indexOf(zjzlValue) > -1)) {
            $("#zjzl").val(zjzjMrz);
            //身份验证
            addSfzYz(zjzjMrz, $("#zjzl"));
        }
    }

}

function getqlrxx() {
    var qlrxx = [];
    getReturnData("/slym/qlr/listBdcQlr", JSON.stringify({xmid: xmid, qlrlb: 1}), "POST", function (data) {
        qlrxx = data;
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    }, false);
    return qlrxx;
}



