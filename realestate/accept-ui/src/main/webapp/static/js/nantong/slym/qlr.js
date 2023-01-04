layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var qlrid = getQueryString("qlrid");
var xmid = getQueryString("xmid");
var xmidList = getQueryString("xmidList");
var lclx = getQueryString("lclx");
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var qllx = getQueryString("qllx");
var readOnly = getQueryString("readOnly");
var zxlc = getQueryString("zxlc");
var dydj = getQueryString("dydj");
var cfdqJflc = getQueryString("cfdqJflc");
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

    form.on('select(qlrlx)', function (data) {
        //根据权利人类型获取证件种类
        var qlrlb = $("#qlrlb").val();
        changeSqrLabelMc(qlrlb,data.value);
        getzjzlByQlrlx(data.value);
        form.render('select');
    });

    //监听共有方式
    /*form.on('select(gyfs)', function (data){
        checkQlblSfRequiredByGyfs();
    });*/

    //关闭处理
    window.onunload = function () {
        sessionStorage.removeItem('qlridList');
        sessionStorage.removeItem('xmidList');
    };
    form.render('select');
});

var zdList;
var yhList = listBdcXtJg();
var qllx = getQueryString("qllx");
var djxl = getQueryString("djxl");
var type = getQueryString("type");

function loadData() {

    //非批量流程清除缓存
    if ((lclx === "zhlc" ||lclx ==="jdlc")) {
        sessionStorage.removeItem('qlridList');
        sessionStorage.removeItem('xmidList');
    }

    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                //异议登记修改权利人类型为  （权利人(申请人)）
                if (qllx === "97") {
                    var qlrlbzd = data.qlrlb;
                    for (var j = 0; j < qlrlbzd.length; j++) {
                        if (qlrlbzd[j].DM === 1) {
                            qlrlbzd[j].MC = "权利人(申请人)";
                        }
                    }
                    data.qlrlb = qlrlbzd;
                }
                if (!sfxzywr && isNullOrEmpty(qlrid)) {
                    //不新增义务人
                    var qlrlbzd = data.qlrlb;
                    for (var j = 0; j < qlrlbzd.length; j++) {
                        if (qlrlbzd[j].DM === 2) {
                            qlrlbzd.splice(j, 1)
                        }
                    }
                    data.qlrlb = qlrlbzd;
                }
                zdList = data;

                if (isNotBlank(qlrid)) {
                    loadQlr();
                } else if (isNotBlank(xmid)) {
                    loadAddQlr();
                }
            }
        }
    });
}

function loadQlr() {
    $.ajax({
        url: getContextPath() + "/slym/qlr",
        type: 'GET',
        dataType: 'json',
        data: {qlrid: qlrid},
        success: function (data) {
            if (isNotBlank(data)) {
                generateQlr(data);
            }
        }
    });
}

function loadAddQlr() {
    qlrid ="";
    $.ajax({
        url: getContextPath() + "/slym/qlr/list/xm",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid, qlrlb: "1", lclx: lclx},
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

function changeSqrLabelMc(qlrlb,qlrlx) {
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
    }
    else {
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
                if(qlrlx === "1" && qlrlb === "1"){
                    $(".qlrdiv").hide();
                    //隐藏后去除必填属性
                    $(qlrflag).removeAttr("lay-verify", "required");
                    $(qlrflag).removeAttr("name", "qlrmc");
                    $(".ywrdiv").show();
                    //展现后增加必填属性
                    $(ywrflag).attr("lay-verify", "required");
                    $(ywrflag).attr("name", "qlrmc");
                    dyaqrmc[0].innerHTML = "<span class=\"required-span\"><sub>*</sub></span>抵押权人";
                }
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
                if(qlrlx === "1" && qlrlb === "1"){
                    $(".qlrdiv").hide();
                    //隐藏后去除必填属性
                    $(qlrflag).removeAttr("lay-verify", "required");
                    $(qlrflag).removeAttr("name", "qlrmc");
                    $(".ywrdiv").show();
                    //展现后增加必填属性
                    $(ywrflag).attr("lay-verify", "required");
                    $(ywrflag).attr("name", "qlrmc");
                    dyarmc[0].innerHTML = "<span class=\"required-span\"><sub>*</sub></span>抵押权人";
                }
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

function changeSqrLabelMcByQlrlx(qlrlx) {

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
    //根据权利人类型获取证件种类
    getzjzlByQlrlx($("#qlrlx").val());

    changeQlrtz(qlrlb);
    layui.form.render("select");
}

function changeQlrtz(qlrlb) {
    $.ajax({
        url: getContextPath() + "/slym/qlr/qlrtz/mrz",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {qllx: qllx, qlrlb: qlrlb},
        success: function (data) {
            if (isNotBlank(data)) {
                $('select[name="qlrtz"]').val(data + '');

            }
        }
    });

}

function generateQlr(qlr) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'formSelects'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var formSelects = layui.formSelects;
        var qlrlb= qlr.qlrlb !== undefined ? qlr.qlrlb : "1";
        var qlrlx = qlr.qlrlx;
        var json = {
            qlr: qlr,
            zd: zdList,
            dydj: dydj,
            yhList: yhList,
            qllx: qllx,
            cfdqJflc: cfdqJflc
        };
        // 如果是新增则请求权利人特征
        if (isNullOrEmpty(qlrid)) {
            $.ajax({
                url: getContextPath() + "/slym/qlr/qlrtz/mrz",
                type: 'GET',
                dataType: 'json',
                async: false,
                data: {qllx: qllx, qlrlb: 1},
                success: function (data) {
                    if (isNotBlank(data)) {
                        json.qlr.qlrtz = data + '';
                    }
                }
            });

        }
        var tpl = addQlrTmpl.innerHTML, view = document.getElementById('bdc-popup-large');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        //法院的权利人类型默认为国家机关
        qlrlx = $("#qlrlx").val();
        if (qllx === "98" && (qlrlx === "" || qlrlx === null)) {
            $("#qlrlx").val(4);
        }
        // 抵押的权利人类型默认为企业
        if (qllx === commonDyaq_qllx && (qlrlx === "" || qlrlx === null)) {
            $("#qlrlx").val(2);
            //权利人类型为企业,默认的身份证种类为社会统一信用代码
            $("#zjzl").val('8');
        }
        // 产权的权利人类型默认为个人
        if (qllx !== "19" && qllx !== commonDyaq_qllx && qllx !== "96" && qllx !== "97" && qllx !== "98" && qllx !== "99" && (qlrlx === "" || qlrlx === null)) {
            $("#qlrlx").val(1);
        }
        form.render();
        renderDate(form);
        if (!isNotBlank(qlr.xmid)) {
            $("#xmid").val(xmid);
        }
        qlrlx = $("#qlrlx").val();
        form.on('select(qlrlb)', function (data) {
            qlrlx = $("#qlrlx").val();
            qlrlb = data.value;
            changeSqrLabelMc(data.value,qlrlx);
            changeQlrlb(data.value);
        });
        form.on('select(zjzl)', function (data) {
            addSfzYz(data.value, data.elem);
        });
        var result = verifyform(form);

        getStateById(readOnly, formStateId, "qlr");
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
        if (isNotBlank(yhList)) {
            $.each(yhList, function (index, item) {
                var yhobject = new Object();
                if (item.jgmc === qlr.qlrmc) {
                    yhobject.selected = "selected";
                    isAdd = false;
                }
                yhobject.name = item.jgmc;
                yhobject.value = item.jgmc;
                yharr.push(yhobject);
            });
        }
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
                            resetSelectDisabledCss();
                        }
                    }
                });
            }

        });
        //提交表单
        form.on("submit(updateQlr)", function (data) {
            if (isNotBlank(result) && !result.isSubmit) {
                layer.msg(result.verifyMsg, {icon: 5, time: 3000});
                result.isSubmit = true;
                return false;
            } else {
                addModel();
                if (isNotBlank(data.field.qlrid)) {
                    setTimeout(function () {
                        try {
                            if (sessionStorage.qlridList == undefined) {
                                $.when(updateQlr(data.field)).done(function () {
                                })
                            } else {
                                $.when(updateYwr(data.field)).done(function () {
                                })
                            }

                        } catch (e) {
                            removeModal();
                            ERROR_CONFIRM("保存失败", e.message);
                            return
                        }
                    }, 10);
                } else {
                    setTimeout(function () {
                        try {
                            $.when(insertQlr(data.field)).done(function () {
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
        if (!isNotBlank(qlrid)) {
            //根据权利人类型获取证件种类
            getzjzlByQlrlx($("#qlrlx").val());
        }
        form.on('select(qlrlx)', function (data) {
            //根据权利人类型获取证件种类
            changeSqrLabelMc(qlrlb,data.value);
            getzjzlByQlrlx(data.value);
            form.render('select');
        });
        changeSqrLabelMc(qlrlb,$("#qlrlx").val());
        //根据共有方式判断权利比例是否为必填
        checkQlblSfRequiredByGyfs();
        form.render("select");
        renderSelect();
        disabledAddFa("qlrForm");
    })
}

function updateQlr(bdcQlrData) {
    //将中文括号转换为英文括号
    var obj = replaceBracketArray();
    Object.keys(obj).forEach(function (key) {
        bdcQlrData[key] = obj[key];
    });
    //申请人名称单独处理
    bdcQlrData.qlrmc = replaceBracket(bdcQlrData.qlrmc);
    // 如果是查封到期解封流程 qlrlx默认赋值为1
    if(cfdqJflc == "true"){
        bdcQlrData.qlrlx = 1;
    }

    var qlrData;
    var lzrData = [];
    var url = "";
    if (lclx === "pllc" ||lclx ==="plzh") {
        qlrData = bdcQlrData;
        lzrData.push(bdcQlrData);
        if(lclx ==="pllc") {
            url = getContextPath() + "/slym/qlr/list/pllc?processInsId=" + processInsId + "&xmid=" + xmid;
        }else if(lclx ==="plzh"){
            url = getContextPath() + "/slym/qlr/list/plzh?processInsId=" + processInsId + "&xmid=" + xmid +"&djxl="+djxl;
        }
    } else {
        if (lclx === "jdlc") {
            url = getContextPath() + "/slym/qlr/list/jdlc?processInsId=" + processInsId+"&type="+type+"&xmid="+xmid;
        }
        if (lclx === "zhlc") {
            url = getContextPath() + "/slym/qlr/list/zhlc?processInsId=" + processInsId;
        }
        qlrData = [];
        qlrData.push(bdcQlrData);
        lzrData.push(bdcQlrData);
    }
    $.ajax({
        url: url,
        type: 'PATCH',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(qlrData),
        success: function (data) {
            if (data > 0) {
                loadQlr();
                saveJglzr(lzrData[0]);
                //保存完权利人信息后，更新收费信息表的缴费人信息
                updateJfrxm();
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

//批量流程修改义务人
function updateYwr(bdcQlrData) {
    var qlrData;
    var url = "";
    var qlridStr = sessionStorage.qlridList;
    var qlridList = qlridStr.split(",");
    var xmidStr = sessionStorage.xmidList;
    var getXmid = xmidStr.split(",");
    qlrData = {};
    qlrData.bdcQlrDo = bdcQlrData;
    qlrData.xmids = getXmid;
    qlrData.qlrids = qlridList;
    url = getContextPath() + "/slym/qlr/updategroupywr?gzlslid=" + processInsId + "&xmid=" + xmid;
    $.ajax({
        url: url,
        type: 'PATCH',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(qlrData),
        success: function (data) {
            if (isNotBlank(data)) {
                qlrid = data[0].qlrid;
                loadQlr();
                //权利人ID发生改变，故弹出层默认关闭
                //保存完权利人信息后，更新收费信息表的缴费人信息
                updateJfrxm();
                successCz("ywrupdate");
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


function insertQlr(bdcQlrData) {
    //将中文括号转换为英文括号
    var obj = replaceBracketArray();
    Object.keys(obj).forEach(function (key) {
        bdcQlrData[key] = obj[key];
    });
    //申请人名称单独处理
    bdcQlrData.qlrmc = replaceBracket(bdcQlrData.qlrmc);
    var url = "";
    if (lclx === "jdlc") {
        url = getContextPath() + "/slym/qlr/jdlc?processInsId=" + processInsId+"&type="+type+"&xmid="+xmid;
    }
    if (lclx === "pllc") {
        url = getContextPath() + "/slym/qlr/pllc?processInsId=" + processInsId;
    }
    if (lclx === "zhlc") {
        url = getContextPath() + "/slym/qlr/zhlc?processInsId=" + processInsId;
    }
    if (lclx === "plzh") {
        url = getContextPath() + "/slym/qlr/plzh?processInsId=" + processInsId+"&djxl="+djxl;
    }
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(bdcQlrData),
        success: function (data) {
            if (isNotBlank(data)) {
                qlrid = data.qlrid;
                loadQlr();
                saveJglzr(data);
                //保存完权利人信息后，更新收费信息表的缴费人信息
                updateJfrxm();
                successCz("insert");


            }
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }
    });
}

function windowParentLoad() {
    if (top != self) {
        if (typeof (eval(window.parent.loadQlr)) == "function") {
            window.parent.loadQlr();
        }
        if(typeof (eval(window.parent.refreshQlxx)) == "function"){
            // 此处刷新权利信息作用：更改权利人信息后，权利为房地产权的需同步更新房地产权中的土地使用权人
            var qllx = $(".qlxx.layui-this", window.parent.document).find("input[name='qllx']").val();
            if(['4','6','8'].indexOf(qllx)>-1){
                window.parent.refreshQlxx();
            }
        }
    }
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
        if (isNotBlank(newattr) && newattr.indexOf("zjhlength") < 0 && qllx !=="98") {
            $("#" + zjhid).attr("lay-verify", newattr + "|zjhlength");
        } else if (zjzl !== null && zjzl !== "" && qllx !== "98") {
            $("#" + zjhid).attr("lay-verify", "zjhlength");
        } else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("zjhlength") > -1) {
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
        windowParentLoad();
        if (fun === "ywrupdate" || qlrsfzdgb) {
            closeWindow();
        }
    });
    removeModal();
}


function closeWindow() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);

}

function nextForm() {
    loadAddQlr();
}

//fzklb赋值框的类别，1为formSelects样式赋值
function sqrReadIdCard(element, fzklb) {
    ReadIDCardNewSqr(element, fzklb);
}

function dlrReadIdCard(element) {
    ReadIDCardNewDlr(element);
    //当代理人联系电话为空时查询该代理人已存在的联系电话
    getLxdh($("#dlrmc"));

}

function fddbrReadIdCard(element) {
    ReadIDCardNewFddbr(element);
}

/**
 * @param element
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 从接口读取身份证
 * @date : 2022/6/16 14:07
 */
function sfzjkdq(element) {
    addModel("获取中");
    getReturnData("/slym/qlr/mkjk", {gzlslid: processInsId}, "GET", function (result) {
        removeModal();
        console.log(result);
        if (result) {
            if (result.code == "1") {
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='qlrmc']").val(trimStr(result.data.name));
                $(forms).find("select[name='zjzl']").val("1");
                $(forms).find("input[name='zjh']").val($.trim(result.data.cardNum));
                form.render('select');
            } else {
                ityzl_SHOW_WARN_LAYER("摩科接口获取权利人信息失败" + result.message)
            }
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    });
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

function listBdcXtJg() {
    var yhList;
    //机关类型
    var jglb = 1;
    var qllx = getQueryString("qllx");
    if (qllx === "98") {
        jglb = 2;
    } else {
        jglb = 1;
    }
    $.ajax({
        url: getContextPath() + "/slym/qlr/bdcxtjg",
        type: 'GET',
        dataType: 'json',
        data: {jglb: jglb},
        async: false,
        success: function (data) {
            yhList = data
        }
    });
    return yhList;
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

/**
 * 保存机构对应的lzr
 * @param data
 */
function saveJglzr(data){
    $.ajax({
        url: getContextPath() + '/slym/qlr/saveJglzr/'+xmid,
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(data),
        success: function (data) {
            // 组合流程 保存完后需要刷新
            // 批量和批量组合
            if(lclx == "pllc" || lclx == "plzh"){
                window.parent.loadLzrxx(xmid,djxl,parent.$('.layui-show'));
            }else{
                var liIndex = parent.$("#liTbale").find('.layui-this').find('input[name="liIndex"]').val()
                // 组合流程这里两个权利都重新加载一下lzr
                window.parent.loadLzrxx("lzrxx" + liIndex, liIndex, xmid);
            }

        },
        error: function () {
            console.log("保存机构领证人失败")
        }
    })
}

//更新收费信息的缴费人信息
function updateJfrxm() {
    getReturnData("/sf/jfrxm?gzlslid=" + processInsId, {}, "PUT", function (data) {
        console.log("更新缴费人信息成功");
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}



