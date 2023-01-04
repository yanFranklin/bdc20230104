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

    form.on('select(qlrlx)', function (data) {
        //根据权利人类型获取证件种类
        getzjzlByQlrlx(data.value);
        form.render('select');
    });
    form.render('select');
});

var zdList;
var qllx = getQueryString("qllx");

function loadData() {

    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
                loadAddQlr();
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
                generateQlr(data);
            }
        }
    });
}

function loadAddQlr() {
    $.ajax({
        url: getContextPath() + "/slym/qlr/list/dsQlr",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid},
        success: function (data) {
            var qlr = [];
            qlr["xmid"] = xmid;
            if (isNotBlank(data)) {
                if (data.length > 0) {
                    qlr["sfczr"] = "1";
                    if (isNotBlank(data[0].gyfs) && data[0].gyfs !== 0) {
                        qlr["gyfs"] = data[0].gyfs;
                    } else {
                        qlr["gyfs"] = "1";
                    }

                    var sxh = 0;
                    for (var i = 0; i < data.length; i++) {
                        var qlrDo = data[i];
                        if (sxh < qlrDo["sxh"]) {
                            sxh = qlrDo["sxh"];
                        }
                    }
                    qlr["sxh"] = sxh + 1;
                }
            } else {
                qlr["sfczr"] = "1";
                qlr["gyfs"] = "0";
                qlr["sxh"] = 1;
            }
            generateQlr(qlr);
        }
    });
}

function changeSqrLabelMc(qlrlb) {
    //申请人label元素
    var sqrmc = document.getElementsByName("sqrmc");
    var dyaqrmc = document.getElementsByName("dyaqrmc");
    var dyarmc = document.getElementsByName("dyarmc");
    var dyaqrlxdh = document.getElementsByName("dyaqrlxdh");
    var dyarlxdh = document.getElementsByName("dyarlxdh");
    var qlrflag = $(".xm-hide-input")[0];//formselects下拉框特殊需要
    var ywrflag = $(".ywrmc")[0];
    if (qllx === "98") {
        //查封流程
        if (qlrlb === "2") {
            $(".qlrdiv").hide();
            //隐藏后去除必填属性
            $(qlrflag).removeAttr("lay-verify", "required");
            $(qlrflag).removeAttr("name", "qlrmc");
            $(".ywrdiv").show();
            //展现后增加必填属性
            $(ywrflag).attr("lay-verify", "required");
            $(ywrflag).attr("name", "qlrmc");
            //改变label内容
            sqrmc[0].innerHTML = "<span class=\"required-span\"><sub>*</sub></span>被执行人";
        } else {
            $(".ywrdiv").hide();
            //隐藏后去除必填属性
            $(ywrflag).removeAttr("lay-verify", "required");
            //去除name属性防止表单必填控制存在问题
            $(ywrflag).removeAttr("name", "qlrmc");
            $(".qlrdiv").show();
            //展现后增加必填属性
            $(qlrflag).attr("lay-verify", "required");
            $(qlrflag).attr("name", "qlrmc");
            //改变label内容
            sqrmc[0].innerHTML = "<span class=\"required-span\"><sub>*</sub></span>执行人";
        }
    } else {
        if (qlrlb === "2") {
            if (dyaqrmc.length > 0) {
                $(".qlrdiv").hide();
                //隐藏后去除必填属性
                $(qlrflag).removeAttr("lay-verify", "required");
                $(qlrflag).removeAttr("name", "qlrmc");
                $(".ywrdiv").show();
                //展现后增加必填属性
                $(ywrflag).attr("lay-verify", "required");
                $(ywrflag).attr("name", "qlrmc");
                dyaqrmc[0].innerHTML = "<span class=\"required-span\"><sub>*</sub></span>抵押人";
            }
            if (dyarmc.length > 0) {
                $(".qlrdiv").hide();
                //隐藏后去除必填属性
                $(qlrflag).removeAttr("lay-verify", "required");
                $(qlrflag).removeAttr("name", "qlrmc");
                $(".ywrdiv").show();
                //展现后增加必填属性
                $(ywrflag).attr("lay-verify", "required");
                $(ywrflag).attr("name", "qlrmc");
                dyarmc[0].innerHTML = "<span class=\"required-span\"><sub>*</sub></span>抵押人";
            }
            if (dyaqrlxdh.length > 0) {
                dyaqrlxdh[0].innerHTML = "抵押人</br>联系电话";
            }
            if (dyarlxdh.length > 0) {
                dyarlxdh[0].innerHTML = "抵押人</br>联系电话";
            }
        } else {
            if (dyaqrmc.length > 0) {
                $(".ywrdiv").hide();
                //隐藏后去除必填属性
                $(ywrflag).removeAttr("lay-verify", "required");
                //去除name属性防止表单必填控制存在问题
                $(ywrflag).removeAttr("name", "qlrmc");
                $(".qlrdiv").show();
                //展现后增加必填属性
                $(qlrflag).attr("lay-verify", "required");
                $(qlrflag).attr("name", "qlrmc");
                dyaqrmc[0].innerHTML = "<span class=\"required-span\"><sub>*</sub></span>抵押权人";
            }
            if (dyarmc.length > 0) {
                $(".ywrdiv").hide();
                //隐藏后去除必填属性
                $(ywrflag).removeAttr("lay-verify", "required");
                //去除name属性防止表单必填控制存在问题
                $(ywrflag).removeAttr("name", "qlrmc");
                $(".qlrdiv").show();
                //展现后增加必填属性
                $(qlrflag).attr("lay-verify", "required");
                $(qlrflag).attr("name", "qlrmc");
                dyarmc[0].innerHTML = "<span class=\"required-span\"><sub>*</sub></span>抵押权人";
            }
            if (dyaqrlxdh.length > 0) {
                dyaqrlxdh[0].innerHTML = "抵押权人</br>联系电话";
            }
            if (dyarlxdh.length > 0) {
                dyarlxdh[0].innerHTML = "抵押权人</br>联系电话";
            }
        }
    }
}

function changeQlrlb(qlrlb) {
    $.ajax({
        url: getContextPath() + "/slym/qlr/list/xm",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid, qlrlb: qlrlb, lclx: lclx},
        success: function (data) {
            if (isNotBlank(data)) {
                if (data.length > 0) {
                    var sxh = 0;
                    for (var i = 0; i < data.length; i++) {
                        var qlrDo = data[i];
                        if (sxh < qlrDo["sxh"]) {
                            sxh = qlrDo["sxh"];
                        }
                    }
                    $("#sxh").val(sxh + 1);
                }
            } else {
                $("#sxh").val(1);
            }
        }
    });
    //根据权利人顺序号判断必填项
    checkbyqlrsxh();
    //根据权利人类型获取证件种类
    getzjzlByQlrlx($("#qlrlx").val());

    layui.form.render("select");
}

function generateQlr(qlr) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'formSelects'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var formSelects = layui.formSelects;
        var json = {
            qlr: qlr,
            zd: zdList,
            dydj: dydj,
            qllx: qllx
        };
        var tpl = addQlrTmpl.innerHTML, view = document.getElementById('bdc-popup-large');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        //法院的权利人类型默认为国家机关
        var qlrlx = $("#qlrlx").val();
        if (qllx === "98" && (qlrlx === "" || qlrlx === null)) {
            $("#qlrlx").val(4);
        }
        form.render();
        renderDate(form);
        if (!isNotBlank(qlr.xmid)) {
            $("#xmid").val(xmid);
        }
        form.on('select(qlrlb)', function (data) {
            changeSqrLabelMc(data.value);
            changeQlrlb(data.value);
        });
        form.on('select(zjzl)', function (data) {
            addSfzYz(data.value, data.elem);
        });
        var result = verifyform(form);

        getStateById(readOnly, formStateId, "dsqlr");
        //在设置权限后设置判断身份证验证，防止必填冲掉身份证验证
        $("[lay-filter=zjzl]").each(function () {
            addSfzYz($(this).val(), $(this));
        });
        formSelects.render('yhmc', {
            create: function (id, name) {
                console.log(id);    //多选id
                console.log(name);  //创建的标签名称
                return name;  //返回该标签对应的val
            }
        });
        //渲染下拉框数据
        var yharr = [];
        //判断当前值是否存在下拉列表，不存在则新增
        var isAdd = true;
        if (isAdd && isNotBlank(qlr.qlrmc)) {
            yharr.push({"name": qlr.qlrmc, "value": qlr.qlrmc, "selected": "selected"});

        }
        formSelects.data('yhmc', 'local', {
            arr: yharr
        });
        //监听下拉框选择事件
        formSelects.on('yhmc', function (id, vals, val, isAdd, isDisabled) {
            //id:           点击select的id
            //vals:         当前select已选中的值
            //val:          当前select点击的值
            //isAdd:        当前操作选中or取消
            //isDisabled:   当前选项是否是disabled
            //false为清空不查询
            if (isAdd) {
                var jgmc = val.value;
                $.ajax({
                    url: getContextPath() + "/slym/qlr/bdcxtjg/jgxx",
                    type: 'GET',
                    dataType: 'json',
                    data: {jgmc: jgmc},
                    success: function (data) {
                        if (data !== null) {
                            $("#zjzl").val(data.jgzjzl);
                            $("#zjh").val(data.jgzjbh);
                            addSfzYz($("#zjzl").val(), $("#zjzl"));
                            form.render('select');
                        }
                    }
                });
            }

        });
        //提交表单
        form.on("submit(saveDsQlr)", function (data) {
            if (isNotBlank(result) && !result.isSubmit) {
                layer.msg(result.verifyMsg, {icon: 5, time: 3000});
                result.isSubmit = true;
                return false;
            } else {
                addModel();
                setTimeout(function () {
                    try {
                        $.when(insertDsQlr(data.field)).done(function () {

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
        //根据权利人顺序号判断必填项
        checkbyqlrsxh();
        //查看权利人不需要进行默认赋值
        if (!isNotBlank(qlrid)) {
            //根据权利人类型获取证件种类
            getzjzlByQlrlx($("#qlrlx").val());
        }


        if (parent.$('.layui-layer-title').text().indexOf("预购人") != -1) {
            $("#qlrlb").find('option[value="1"]').attr("selected", "selected");
            $("#qlrlb").attr("disabled", "disabled");
        } else if (parent.$('.layui-layer-title').text().indexOf("买受人") != -1) {
            $("#qlrlb").find('option[value="2"]').attr("selected", "selected");
            $("#qlrlb").attr("disabled", "disabled");
        }

        form.render("select");
        renderSelect();
        disabledAddFa("qlrForm");
    })

}

function insertDsQlr(bdcQlrData) {
    //将中文括号转换为英文括号
    var obj = replaceBracketArray();
    Object.keys(obj).forEach(function (key) {
        bdcQlrData[key] = obj[key];
    });
    //申请人名称单独处理
    bdcQlrData.qlrmc = replaceBracket(bdcQlrData.qlrmc);
    var url = getContextPath() + "/slym/qlr/insertDsqlr?processInsId=" + processInsId;

    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(bdcQlrData),
        success: function (data) {
            if (isNotBlank(data)) {
                qlrid = data.qlrid;
                successCz("dsqlrinsert");
            }
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
        if (parent && 'function' === typeof (parent.generateDsQlr)) {
            var $sqr = "";
            var index = parent.$(".layui-this").find("input[name='liIndex']").val();
            if (!isNotBlank(index)) {
                index = "";
                $sqr = parent.$(".layui-show").find(".sqr");
            } else {
                $sqr = parent.$(".sqr" + index);
            }
            parent.generateDsQlr(xmid, $sqr, index);
        } else {
            window.parent.loadQlr();
        }
        if (fun === "dsqlrinsert") {
            closeWin();
        }
        window.parent.form.render("select");
    });
    removeModal();
}

function nextForm() {
    loadAddQlr();
}


//根据权利人顺序号判断必填项
function checkbyqlrsxh() {
    var sxh = $("#sxh").val();
    if (sxh === "1") {
        addRequired($("#dh"));
    } else {
        //权利人顺序号不为1，申请人联系电话去掉必填
        removeRequired($("#dh"));
    }
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



