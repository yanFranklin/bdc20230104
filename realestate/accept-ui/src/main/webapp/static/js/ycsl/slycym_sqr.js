layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
//var yhList = listBdcXtJg();
var qllx = getQueryString("qllx");
var djxl = getQueryString("djxl");
//页面入口
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var form = layui.form;
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

    // 监听申请人类型下拉列表
    form.on('select(sqrlx)', function (data) {
        //根据权利人类型获取证件种类
        getzjzlByQlrlx(data.value);
        form.render('select');
    });
    form.render('select');
});

/**
 * 加载数据
 */
function loadData() {
    if (isNotBlank(sqrid)) {
        // 点击详细按钮 打开已有的权利人
        loadSqr();
    } else {
        // 新增权利人
        loadAddSqr();
    }
}

/**
 * 加载申请人
 */
function loadSqr() {
    $.ajax({
        url: getContextPath() + "/slym/sqr/"+sqrid,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                generateQlr(data);
            }
        }
    });
}

/**
 * 添加申请人
 */
function loadAddSqr() {
    $.ajax({
        url: getContextPath() + "/slym/sqr/sqrxx/"+jbxxid+"/1",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var qlr = [];
            if (isNotBlank(data)) {
                if (data.length > 0) {
                    qlr["sfczr"] = "1";
                    qlr["xmid"] = data[0].xmid;
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

// 改变已有申请人的类别的逻辑
function changeSqrlb(sqrlb) {
    $.ajax({
        url: getContextPath() + "/slym/sqr/sqrxx/"+jbxxid+"/"+sqrlb ,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                if (data.length > 0) {
                    var sxh = 0;
                    for (var i = 0; i < data.length; i++) {
                        var sqrDo = data[i];
                        if (sxh < sqrDo["sxh"]) {
                            sxh = sqrDo["sxh"];
                        }
                    }
                    $("#sxh").val(sxh + 1);
                }
            } else {
                $("#sxh").val(1);
            }
        }
    });
    //根据权利人类型获取证件种类
    getzjzlByQlrlx($("#sqrlx").val());
    layui.form.render("select");
}

function generateQlr(sqr) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'formSelects'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var formSelects = layui.formSelects;
        var json = {
            sqr: sqr,
            zd: parent.zdList
        };
        var tpl = addSqrTmpl.innerHTML, view = document.getElementById('bdc-popup-large');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        renderDate(form);
        if (!isNotBlank(sqr.xmid)) {
            $("#xmid").val(xmid);
        }
        form.on('select(sqrlb)', function (data) {
            changeSqrlb(data.value);
        });
        form.on('select(zjzl)', function (data) {
            addSfzYz(data.value, data.elem);
        });
        var result = verifyform(form);
        //getStateById(readOnly, formStateId, "sqr");
        //在设置权限后设置判断身份证验证，防止必填冲掉身份证验证
        $("[lay-filter=zjzl]").each(function () {
            addSfzYz($(this).val(), $(this));
        });

        //提交表单
        form.on("submit(updateSqr)", function (data) {
            if (isNotBlank(result) && !result.isSubmit) {
                layer.msg(result.verifyMsg, {icon: 5, time: 3000});
                result.isSubmit = true;
                return false;
            } else {
                addModel();
                if (isNotBlank(data.field.sqrid)) {
                    setTimeout(function () {
                        try {
                            $.when(updateSqr(data.field)).done(function () {
                            })
                        } catch (e) {
                            removeModal();
                            ERROR_CONFIRM("保存失败", e.message);
                            return
                        }
                    }, 10);
                } else {
                    setTimeout(function () {
                        try {
                            $.when(insertSqr(data.field)).done(function () {
                            })
                        } catch (e) {
                            removeModal();
                            ERROR_CONFIRM("保存失败", e.message);
                            return
                        }
                    }, 10);
                }
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

        //查看权利人不需要进行默认赋值
        if (!isNotBlank(sqrid)) {
            //根据权利人类型获取证件种类
            getzjzlByQlrlx($("#qlrlx").val());
        }
        form.on('select(qlrlx)', function (data) {
            //根据权利人类型获取证件种类
            getzjzlByQlrlx(data.value);
            form.render('select');
        });
        //根据共有方式判断权利比例是否为必填
        checkQlblSfRequiredByGyfs();
        form.render("select");
        renderSelect();
        disabledAddFa("qlrForm");
    })
}

// 更新申请人
function updateSqr(bdcQlrData) {
    //将中文括号转换为英文括号
    var obj = replaceBracketArray();
    Object.keys(obj).forEach(function (key) {
        bdcQlrData[key] = obj[key];
    });
    //申请人名称单独处理
    bdcQlrData.sqrmc = replaceBracket(bdcQlrData.sqrmc);
    var BdcSlSqrDTO = {};
    var bdcSlSqrDTOList =[];
    BdcSlSqrDTO['BdcSlSqrDO'] =bdcQlrData;
    BdcSlSqrDTO['bdcSlJtcyDOList'] =[];
    bdcSlSqrDTOList.push(BdcSlSqrDTO);
    var url =  getContextPath() + "/slym/sqr/sqrxx?gzlslid=" + processInsId;
    $.ajax({
        url: url,
        type: 'PATCH',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(bdcSlSqrDTOList),
        success: function (data) {
            if (data > 0) {
                loadSqr();
                successCz("update");

            } else {
                removeModal();
            }
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }
    });
}

/**
 * 新增申请人
 * @param bdcQlrData
 */
function insertSqr(bdcQlrData) {
    //将中文括号转换为英文括号
    var obj = replaceBracketArray();
    Object.keys(obj).forEach(function (key) {
        bdcQlrData[key] = obj[key];
    });
    //申请人名称单独处理
    bdcQlrData.sqrmc = replaceBracket(bdcQlrData.sqrmc);
    if(bdcQlrData.xmid == null || bdcQlrData.xmid == ""){
        bdcQlrData.xmid = parent.$("#xmid").val();
    }
    var BdcSlSqrDTO = {};
    var bdcSlSqrDTOList =[];
    BdcSlSqrDTO['BdcSlSqrDO'] =bdcQlrData;
    BdcSlSqrDTO['bdcSlJtcyDOList'] =[];
    bdcSlSqrDTOList.push(BdcSlSqrDTO);

    var url =  getContextPath() + "/slym/sqr/sqrxx?gzlslid=" + processInsId;

    $.ajax({
        url: url,
        type: 'PATCH',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(bdcSlSqrDTOList),
        success: function (data) {
            if (isNotBlank(data)) {
                successCz("insert");
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

// 身份证格式验证
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
        if (isNotBlank(newattr) && newattr.indexOf("zjhlength") < 0 && qllx !=="98") {
            $("#" + zjhid).attr("lay-verify", newattr + "|zjhlength");
        } else if (zjzl !== null && zjzl !== "" && qllx !== "98") {
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
        //cancel();
        parent.loadYcslxx();
    });
    removeModal();
}

function nextForm() {
    loadAddSqr();
}

function sqrReadIdCard(element) {
    ReadIDCardNewSqr(element);
}

function dlrReadIdCard(element) {
    ReadIDCardNewDlr(element);
    //当代理人联系电话为空时查询该代理人已存在的联系电话
    getLxdh($("#dlrmc"));

}

function fddbrReadIdCard(element) {
    ReadIDCardNewFddbr(element);
}

//根据名称和证件号查询联系电话
function getLxdh(mcelem) {
    var mcid = $(mcelem)[0].id;
    var zjhid = mcid.replace("mc", "zjh");
    var dhid = mcid.replace("mc", "dh");
    var bdcQlrQO = new Object();
    var dhVal = $("#" + dhid).val();
    var mcVal = $("#" + mcid).val();
    var zjhVal = $("#" + zjhid).val();
    //当联系电话为空时查询其已存在的联系电话
    if (!isNotBlank(dhVal) && isNotBlank(mcVal) && isNotBlank(zjhVal)) {
        bdcQlrQO[mcid] = mcVal;
        bdcQlrQO[zjhid] = zjhVal;
        getReturnData("/slym/qlr/listBdcQlr", JSON.stringify(bdcQlrQO), "POST", function (data) {
            if(data &&data.length >0){
                $("#" + dhid).val(data[0][dhid]);
            }
        }, function (error) {
            delAjaxErrorMsg(error);

        });
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

/**
 * 共有方式为按份共有时,共有比例为必填
 */
function checkQlblSfRequiredByGyfs(){
    var gyfs = $("select[name=gyfs]").val();
    if (gyfs === "2") {
        //共有方式为按份共有时,共有比例为必填
        addRequired($("#qlbl"));
    }else{
        removeRequired($("#qlbl"));
    }

}



