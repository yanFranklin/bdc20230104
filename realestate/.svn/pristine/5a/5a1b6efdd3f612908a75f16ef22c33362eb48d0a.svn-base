layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
//页面入口
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
var form;
var laytpl;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    laytpl = layui.laytpl;
    addModel();
    setTimeout(function () {
        try {
            $.when(loadData()).done(function () {
                removeModal();
            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e, "加载失败");
            return
        }
    }, 10);

    form.on('select(qlrlx)', function (data) {
        //根据权利人类型判断必填项
        checkbyqlrlx();
        changeZjzlByQlrlx(data.value);
        //权利人类型为企业时,身份证种类默认为“统一社会信用代码”，个人默认身份证
        if (data.value === "2") {
            $("#zjzl").val("8");
            addSfzYz("8", $("#zjzl"));
        } else if (data.value === "1") {
            $("#zjzl").val("1");
            addSfzYz("1", $("#zjzl"));
        } else if (data.value === "4") {
            // 权利人类型为国家机关时，身份证种类默认为“组织机构代码”，证件号默认“123456”
            $("#zjzl").val("6");
            $("#zjh").val("123456");
        }
        form.render('select');
    });

    //监听共有方式
    form.on('select(gyfs)', function (data) {
        checkQlblSfRequiredByGyfs();
    });

    //关闭处理
    window.onunload = function () {
        sessionStorage.removeItem('qlridList');
        sessionStorage.removeItem('xmidList');
    };

    form.render('select');
});

var zdList = {qlrlb: []};
var yhList = listBdcXtJg();
var djxl = getQueryString("djxl");
var type = getQueryString("type");

function loadData() {
    //非批量流程清除缓存
    if ((lclx === "zhlc" || lclx === "jdlc")) {
        sessionStorage.removeItem('qlridList');
        sessionStorage.removeItem('xmidList');
    }
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
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
                zdList = data;
            }
        }
    });
    if (isNotBlank(qlrid)) {
        loadQlr();
    } else if (isNotBlank(xmid)) {
        loadAddQlr();
    }
}

function loadQlr() {
    $.ajax({
        url: getContextPath() + "/slym/qlr/qlrdlr",
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
            var qlrAndDlr = {};
            var dlr = {};
            var dlrList = [];
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
            dlrList.push(dlr);
            qlrAndDlr.bdcQlrDO = qlr;
            qlrAndDlr.bdcDlrDOList = dlrList;
            generateQlr(qlrAndDlr);
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
    //根据权利人类型判断必填项
    checkbyqlrlx();

    //权利人类型为企业时,身份证种类默认为“统一社会信用代码”，个人默认身份证
    var qlrlx = $("#qlrlx").val();
    if (qlrlx === "2") {
        $("#zjzl").val("8");
        addSfzYz("8", $("#zjzl"))
    } else if (qlrlx === "1") {
        $("#zjzl").val("1");
        addSfzYz("1", $("#zjzl"))
    }
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
                form.render();
                resetSelectDisabledCss();

            }
        }
    });

}

function generateQlr(qlr) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'formSelects'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var formSelects = layui.formSelects;
        var json = {
            qlr: qlr.bdcQlrDO,
            dlrList: qlr.bdcDlrDOList,
            zd: zdList,
            dydj: dydj,
            yhList: yhList,
            qllx: qllx
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
        var qlrlx = $("#qlrlx").val();
        if (qllx === "98" && (qlrlx === "" || qlrlx === null)) {
            $("#qlrlx").val(4);
            // 身份证种类默认为“组织机构代码”，证件号默认“123456”
            $("#zjzl").val("6");
            $("#zjh").val("123456");
        }
        form.render();
        renderDate(form);
        if (!isNotBlank(qlr.bdcQlrDO.xmid)) {
            $("#xmid").val(xmid);
        }
        form.on('select(qlrlb)', function (data) {
            changeSqrLabelMc(data.value);
            changeQlrlb(data.value);
            changeQlrtz(data.value);
        });
        form.on('select(zjzl)', function (data) {
            addSfzYz(data.value, data.elem);
            changeqlrlxByZjzl();
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
                if (item.jgmc === qlr.bdcQlrDO.qlrmc) {
                    yhobject.selected = "selected";
                    isAdd = false;
                }
                yhobject.name = item.jgmc;
                yhobject.value = item.jgmc;
                yharr.push(yhobject);
            });
        }
        if (isAdd && isNotBlank(qlr.bdcQlrDO.qlrmc)) {
            yharr.push({"name": qlr.bdcQlrDO.qlrmc, "value": qlr.bdcQlrDO.qlrmc, "selected": "selected"});

        }
        formSelects.data('yhmc', 'local', {
            arr: yharr
        });
        // 下拉搜索框显示选中值
        var $yhmcparent = $('#bdc-popup-large').find('select[lay-filter="yhmc"]').parent();
        $yhmcparent.find('.xm-dl-input .xm-select-input').val($yhmcparent.find('.xm-select-title span font').text());
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
                            var qlrlx = $("#qlrlx").val();
                            //根据jgid区分出自定义的抵押权人
                            if (data.jgid == null) {
                                if (qlrlx == '1') {
                                    //权利人类型为个人，默认的身份证种类为身份证
                                    $("#zjzl").val('1');
                                } else if (qlrlx == '2') {
                                    //权利人类型为企业,默认的身份证种类为社会统一信用代码
                                    $("#zjzl").val('8');
                                }
                            } else {
                                $("#zjzl").val(data.jgzjzl);
                                $("#zjh").val(data.jgzjbh);
                                //联带代理人信息
                                $("#dlrmc").val(data.dlrmc);
                                $("#dlrdh").val(data.dlrdh);
                                $("#dlrzjh").val(data.dlrzjh);
                                $("#dlrzjzl").val(data.dlrzjzl);

                                $("#frmc").val(data.frmc);
                                $("#frzjzl").val(data.frzjzl);
                                $("#frzjh").val(data.frzjh);
                                $("#frdh").val(data.frdh);
                            }
                            addSfzYz($("#zjzl").val(), $("#zjzl"));
                            form.render('select');
                            resetSelectDisabledCss();
                            // 下拉搜索框显示选中值
                            var $yhmcparent = $('#bdc-popup-large').find('select[lay-filter="yhmc"]').parent();
                            $yhmcparent.find('.xm-dl-input .xm-select-input').val($yhmcparent.find('.xm-select-title span font').text());
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
                //特殊验证需要组合查询验证
                var res = checkZhQlrxx(data.field);
                if (!res) {
                    removeModal();
                    $("#zjh").addClass('layui-form-danger')
                    //证件号加提示框
                    ityzl_SHOW_WARN_LAYER("证件号长度必须为18位");
                    return false;
                }
                if (isNotBlank(data.field.qlrid)) {
                    // 增加表单修改日志, 批量流程采用人员证件号做日志key信息
                    if (ShowHighLight.checkRequired()) {
                        if (data.field.qlrlb == 2 && (lclx === "pllc" || lclx === "plzh")) {
                            ShowHighLight.addFormModifyLog(processInsId + data.field.zjh);
                        } else {
                            ShowHighLight.addFormModifyLog(processInsId + data.field.qlrid);
                        }
                    }
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
                            delAjaxErrorMsg(e, "保存失败:"+e.message);
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
                            delAjaxErrorMsg(e, "保存失败:"+e.message);
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
        $("#getQyxx").click(function () {
                addModel();
                setTimeout(function () {
                    getQyxx();
                }, 10);
            }
        );
        //监听代理人证件号输入完离开
        $('#dlrzjh').on('blur', function () {
            //当代理人联系电话为空时查询该代理人已存在的联系电话
            getLxdh($("#dlrmc"));

        });
        //根据权利人类型判断必填项
        checkbyqlrlx();
        //根据共有方式判断权利比例是否为必填
        checkQlblSfRequiredByGyfs();
        form.render("select");
        renderSelect();
        disabledAddFa("qlrForm");

        // 查询表单修改过的参数，并进行高亮展示
        if (ShowHighLight.checkRequired()) {
            if (lclx === "pllc" || lclx === "plzh") {
                ShowHighLight.queryFormModifyLog(processInsId + qlr.bdcQlrDO.zjh);
            } else {
                ShowHighLight.queryFormModifyLog(processInsId + qlrid);
            }
            //添加申请人表单增加修改后监听
            ShowHighLight.renderChange(formSelects, form, "qlrForm");
        }
    })
}

function updateQlr(bdcQlrData) {
    //将中文括号转换为英文括号
    var obj = replaceBracketArray();
    Object.keys(obj).forEach(function (key) {
        bdcQlrData[key] = obj[key];
    });
    // 对于权利人名称和证件号删除空格处理
    if (!isNullOrEmpty(bdcQlrData.qlrmc)) {
        bdcQlrData.qlrmc = deleteWhitespace(bdcQlrData.qlrmc, 'all');
    }
    if (!isNullOrEmpty(bdcQlrData.zjh)) {
        bdcQlrData.zjh = deleteWhitespace(bdcQlrData.zjh, 'all');
    }
    //蚌埠转换权利比例小数转化为百分比
    if (bdcQlrData.qlbl && bdcQlrData.qlbl.indexOf("/") !== -1) {
        throw err = new Error('共有比例不正确，请检查！');
    } else if (bdcQlrData.qlbl && /.*[\u4e00-\u9fa5]+.*$/.test(bdcQlrData.qlbl)) {
        throw err = new Error('共有比例不能含有汉字，请检查！');
    }
    if (bdcQlrData.qlbl && bdcQlrData.qlbl.indexOf(".") !== -1 && bdcQlrData.qlbl.indexOf("%") === -1) {
        bdcQlrData.qlbl = parseFloat(bdcQlrData.qlbl) * 100 + '%';
    }
    //申请人名称单独处理
    bdcQlrData.qlrmc = replaceBracket(bdcQlrData.qlrmc);
    //合肥需求，领证人默认代理人
    saveHfLzr(bdcQlrData, "update");
    saveDlr(bdcQlrData.qlrid, "");
    //将证件号中小写字母改为大写
    toUpperClass(bdcQlrData, ["zjh", "dlrzjh", "frzjh", "lzrzjh"]);

    var qlrData;
    var url = "";
    if (lclx === "pllc" || lclx === "plzh") {
        qlrData = bdcQlrData;
        if (lclx === "pllc") {
            url = getContextPath() + "/slym/qlr/list/pllc?processInsId=" + processInsId + "&xmid=" + xmid;
        } else if (lclx === "plzh") {
            url = getContextPath() + "/slym/qlr/list/plzh?processInsId=" + processInsId + "&xmid=" + xmid + "&djxl=" + djxl;
        }
    } else {
        if (lclx === "jdlc") {
            url = getContextPath() + "/slym/qlr/list/jdlc?processInsId=" + processInsId + "&type=" + type + "&xmid=" + xmid;
        }
        if (lclx === "zhlc") {
            url = getContextPath() + "/slym/qlr/list/zhlc?processInsId=" + processInsId;
        }
        qlrData = [];
        qlrData.push(bdcQlrData);
    }
    $.ajax({
        url: url,
        type: 'PATCH',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(qlrData),
        success: function (data) {
            //批量组合返回的不是更新数量
            if ((lclx === "plzh" && isNotBlank(data)) || data > 0) {
                loadQlr();
                successCz();
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
    // 对于权利人名称和证件号删除空格处理
    if (!isNullOrEmpty(bdcQlrData.qlrmc)) {
        bdcQlrData.qlrmc = deleteWhitespace(bdcQlrData.qlrmc, 'all');
    }
    if (!isNullOrEmpty(bdcQlrData.zjh)) {
        bdcQlrData.zjh = deleteWhitespace(bdcQlrData.zjh, 'all');
    }
    //将证件号中小写字母改为大写
    toUpperClass(bdcQlrData, ["zjh", "dlrzjh", "frzjh", "lzrzjh"]);
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
                saveDlr(qlrid, qlridStr);
                // loadQlr();
                successCz();
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
    //合肥需求，领证人默认代理人
    //将证件号中小写字母改为大写
    toUpperClass(bdcQlrData, ["zjh", "dlrzjh", "frzjh", "lzrzjh"]);
    //蚌埠转换权利比例小数转化为百分比
    if (bdcQlrData.qlbl && bdcQlrData.qlbl.indexOf("/") !== -1) {
        throw err = new Error('共有比例不正确，请检查！');
    } else if (bdcQlrData.qlbl && /.*[\u4e00-\u9fa5]+.*$/.test(bdcQlrData.qlbl)) {
        throw err = new Error('共有比例不能含有汉字，请检查！');
    }
    // 对于权利人名称和证件号删除空格处理
    if (!isNullOrEmpty(bdcQlrData.qlrmc)) {
        bdcQlrData.qlrmc = deleteWhitespace(bdcQlrData.qlrmc, 'all');
    }
    if (!isNullOrEmpty(bdcQlrData.zjh)) {
        bdcQlrData.zjh = deleteWhitespace(bdcQlrData.zjh, 'all');
    }
    if (bdcQlrData.qlbl && bdcQlrData.qlbl.indexOf(".") !== -1 && bdcQlrData.qlbl.indexOf("%") === -1) {
        bdcQlrData.qlbl = parseFloat(bdcQlrData.qlbl) * 100 + '%';
    }
    var url = "";
    if (lclx === "jdlc") {
        url = getContextPath() + "/slym/qlr/jdlc?processInsId=" + processInsId + "&type=" + type + "&xmid=" + xmid;
    }
    if (lclx === "pllc") {
        url = getContextPath() + "/slym/qlr/pllc?processInsId=" + processInsId;
    }
    if (lclx === "zhlc") {
        url = getContextPath() + "/slym/qlr/zhlc?processInsId=" + processInsId;
    }
    if (lclx === "plzh") {
        url = getContextPath() + "/slym/qlr/plzh?processInsId=" + processInsId + "&djxl=" + djxl;
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
                saveHfLzr(data, "insert");
                //保存代理人信息
                saveDlr(qlrid, "");
                loadQlr();
                successCz();


            }
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }
    });
}

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 保存代理人相关信息
 * @date : 2022/3/21 15:22
 */
function saveDlr(qlrid, yqlrid) {
    var dlrArray = $(".dlr").serializeArray();
    if (dlrArray && dlrArray.length > 0) {
        var dlrList = [];
        var dlr = {};
        for (var i = 0; i < dlrArray.length; i++) {
            var name = dlrArray[i].name;
            dlr[name] = dlrArray[i].value;
            dlr["qlrid"] = qlrid;
            dlr["zjzl"] = dlr.dlrzjzl;
            dlr["zjh"] = dlr.dlrzjh;
            dlr["dh"] = dlr.dlrdh;
            dlr["ywrid"] = yqlrid;
            if ((i + 1 < dlrArray.length && dlrArray[i + 1].name === 'dlrid') || i + 1 === dlrArray.length) {
                var wtsx1 = "";
                var wtsx2 = "";
                if (dlr.wtsx1) {
                    wtsx1 = dlr.wtsx1;
                }
                if (dlr.wtsx2) {
                    wtsx2 = dlr.wtsx2;
                }
                dlr["wtsx"] = wtsx1 + (isNotBlank(wtsx2) && isNotBlank(wtsx1) ? "," + wtsx2 : wtsx2);
                dlrList.push(dlr);
                dlr = {};
            }
        }
        if (dlrList.length > 0) {
            var url = "/slym/dlr?gzlslid=" + processInsId + "&djxl=" + djxl;
            getReturnData(url, JSON.stringify(dlrList), 'POST', function (data) {

            }, function (xhr) {
                removeModal();
                delAjaxErrorMsg(xhr);
            })
        }
    }
}

/**
 * @param null
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 新增代理人模块
 * @date : 2022/3/22 11:43
 */
function addDlr(elem) {
    var $parent = $(elem).parents('.dlrItem');
    var addTpl = addDlrTpl.innerHTML;
    var json = {
        zd: zdList,
        qlrid: qlrid,
        xmid: xmid
    };
    laytpl(addTpl).render(json, function (html) {
        $parent.append(html);
        form.render();
    });
}

function delDlr(elem, dlrid, qlrlb) {
    $(elem).parents('.dlrItem')[0].remove();

    //删除后重新加载权利人信息
    var url = "";
    if (lclx === "zhlc") {
        //直接根据dlrid 删除
        url = "/slym/dlr/zhlc?dlrid=" + dlrid;
    }
    if (lclx === "pllc" || lclx === "plzh") {
        url = "/slym/dlr/pllc?dlrid=" + dlrid + "&gzlslid=" + processInsId + "&djxl=" + djxl + "&qlrlb=" + qlrlb;
    }
    getReturnData(url, {}, "DELETE", function (data) {
        loadQlr();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    });
}

function saveHfLzr(bdcQlrData, func) {
    // 只有权利人才会保存对应的领证人
    if (isNotBlank(bdcQlrData) && bdcQlrData.qlrlb === "1") {
        var lzrList = [];
        var bdclzr = {};
        //存储逻辑，默认代理人当代理人只有一个时默认代理人，当代理人多个时，取委托事项为领取证书的，否则取权利人
        var dlrArray = $(".dlr").serializeArray();
        var dlrList = [];
        if (dlrArray && dlrArray.length > 0) {
            var dlr = {};
            for (var i = 0; i < dlrArray.length; i++) {
                var name = dlrArray[i].name;
                dlr[name] = dlrArray[i].value;
                dlr["qlrid"] = qlrid;
                dlr["zjzl"] = dlr.dlrzjzl;
                dlr["zjh"] = dlr.dlrzjh;
                dlr["dh"] = dlr.dlrdh;
                if ((i + 1 < dlrArray.length && dlrArray[i + 1].name === 'dlrid') || i + 1 === dlrArray.length) {
                    var wtsx1 = "";
                    var wtsx2 = "";
                    if (dlr.wtsx1) {
                        wtsx1 = dlr.wtsx1;
                    }
                    if (dlr.wtsx2) {
                        wtsx2 = dlr.wtsx2;
                    }
                    dlr["wtsx"] = wtsx1 + (isNotBlank(wtsx2) && isNotBlank(wtsx1) ? "," + wtsx2 : wtsx2);
                    dlrList.push(dlr);
                    dlr = {};
                }
            }
        }
        if (dlrList.length > 0) {
            if (dlrList.length === 1 && dlrList[0].dlrmc) {
                bdclzr.qlrid = bdcQlrData.qlrid;
                bdclzr.xmid = bdcQlrData.xmid;
                bdclzr.lzrmc = dlrList[0].dlrmc;
                bdclzr.lzrzjzl = dlrList[0].zjzl;
                bdclzr.lzrzjh = dlrList[0].zjh;
                bdclzr.lzrdh = dlrList[0].dh;
            } else {
                for (var j = 0; j < dlrList.length; j++) {
                    if (dlrList[j].dlrmc && dlrList[j].wtsx.indexOf("领取证书") > -1) {
                        bdclzr.qlrid = bdcQlrData.qlrid;
                        bdclzr.xmid = bdcQlrData.xmid;
                        bdclzr.lzrmc = dlrList[j].dlrmc;
                        bdclzr.lzrzjzl = dlrList[j].zjzl;
                        bdclzr.lzrzjh = dlrList[j].zjh;
                        bdclzr.lzrdh = dlrList[j].dh;
                        break;
                    }
                }
            }
        }
        if (isNullOrEmpty(bdclzr.lzrmc) && isNotBlank(bdcQlrData.qlrmc)) {
            bdclzr.qlrid = bdcQlrData.qlrid;
            bdclzr.xmid = bdcQlrData.xmid;
            bdclzr.lzrmc = bdcQlrData.qlrmc;
            bdclzr.lzrzjzl = bdcQlrData.zjzl;
            bdclzr.lzrzjh = bdcQlrData.zjh;
            bdclzr.lzrdh = bdcQlrData.dh;
        }
        lzrList.push(bdclzr);
        var url = "";
        if (lclx === "pllc") {
            url = "/slym/lzr/hf/lzrxx/pllc?gzlslid=" + processInsId + "&xmid=" + bdcQlrData.xmid + "&qlrid=" + bdcQlrData.qlrid;
        } else if (lclx === "plzh") {
            url = "/slym/lzr/hf/lzrxx/plzh?gzlslid=" + processInsId + "&qlrid=" + bdcQlrData.qlrid + "&djxl=" + djxl + "&xmid=" + bdcQlrData.xmid;
        } else {
            url = "/slym/lzr/hf/lzrxx?gzlslid=" + processInsId + "&qlrid=" + bdcQlrData.qlrid;
        }
        if (func === "insert") {
            getReturnData(url, JSON.stringify(lzrList), 'POST', function (data) {

            }, function (xhr) {
                removeModal();
                delAjaxErrorMsg(xhr);
            })
        }
        if (func === "update") {
            getReturnData(url, JSON.stringify(lzrList), 'PATCH', function (data) {

            }, function (xhr) {
                removeModal();
                delAjaxErrorMsg(xhr);
            })
        }
    }

}

function windowParentLoad() {
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
        } else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("zjhlength") > -1) {
            $("#" + zjhid).attr("lay-verify", attrVal.replace("zjhlength", ""));
        }
    }
    if(isNotBlank(yzsfhytsfhzjzl)) {
        attrVal = $("#" + zjhid).attr("lay-verify");
        if (yzsfhytsfhzjzl.indexOf(zjzl) > -1) {
            var newattr = $("#" + zjhid).attr("lay-verify");
            if (isNotBlank(newattr) && newattr.indexOf("sfhytsfh") < 0) {
                $("#" + zjhid).attr("lay-verify", newattr + "|sfhytsfh");
            } else if (zjzl !== null && zjzl !== "") {
                $("#" + zjhid).attr("lay-verify", "sfhytsfh");
            } else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("sfhytsfh") > -1) {
                $("#" + zjhid).attr("lay-verify", attrVal.replace("sfhytsfh", ""));
            }
        } else {
            if (isNotBlank(attrVal)) {
                $("#" + zjhid).attr("lay-verify", attrVal.replace("sfhytsfh", ""));
            }
        }
    }
}

/**
 * 提交成功操作
 *
 */
function successCz() {
    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">保存成功', {
        time: 1000
    }, function () {
        windowParentLoad();
        closeWindow();
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
            if (data && data.length > 0) {
                $("#" + dhid).val(data[0][dhid]);

            }
        }, function (error) {
            delAjaxErrorMsg(error);

        });

    }


}


/**
 * 通过接口获取企业信息
 */
function getQyxx() {
    // addModel();
    var form = layui.form;
    var qlrlx = $("#qlrlx").val();
    if (!isNotBlank(qlrlx)) {
        showAlertDialog("请输入权利人类型");
        removeModal();
        return false;
    }
    var qlrmc = $("#qlrmc").val();
    if (!isNotBlank(qlrmc)) {
        showAlertDialog("请输入权利人名称");
        removeModal();
        return false;
    }
    var qlrzjh = $("#zjh").val();
    var beanNames = "";
    if (qlrlx === "2") {
        //权利人类型为企业,调用工商接口
        beanNames = "gs_qyxydmcx";
    } else if (qlrlx === "3" || qlrlx === "4") {
        //权利人类型为事业单位,调用国家机关,调用社会组织_事业单位_机关团体融合查询接口
        beanNames = "hf_acceptRhcx";
    } else if (qlrlx === "99") {
        //权利人类型为其他,优先调用社会组织_事业单位_机关团体融合查询接口,查询不到查询融资担保_小额贷款公司信息接口
        beanNames = "hf_acceptRhcx,hf_acceptRzdbgscx";

    }
    if (!isNotBlank(beanNames)) {
        showAlertDialog("当前权利人类型未开放接口");
        removeModal();
        return false;
    }
    $.each(beanNames.split(","), function (index, beanName) {
        //接口返回结果
        var result ={};
        getReturnData("/slym/qlr/getQyxx", {qlrmc: qlrmc,zjh:qlrzjh,beanName:beanName,xmid:xmid}, "GET", function (data) {
            result =data;
            if (data && !$.isEmptyObject(data.qyxx)) {
                removeModal();
                var qyzt = data.qyxx[0].qyzt;
                var bdcQlrDO = data.qyxx[0].bdcQlrDO;
                bdcQlrDO.qlrwbzt = qyzt;
                if (isNotBlank(qyzt)) {
                    var msg = "当前查询的企业状态为" + qyzt + ",是否带入？";
                    var layerindex = layer.open({
                        type: 1,
                        skin: 'bdc-small-tips',
                        title: '提示信息',
                        area: ['320px'],
                        content: msg,
                        btn: ['确定', '取消'],
                        btnAlign: 'c',
                        yes: function () {
                            daoruqyxx(bdcQlrDO, form);
                            layer.close(layerindex);
                            // return false;
                        },
                        btn2: function () {
                            //取消
                            layer.close(layerindex);
                        }
                    });
                } else {
                    //企业状态为空直接带入
                    daoruqyxx(bdcQlrDO, form);
                }
            } else if ((index + 1) === beanNames.split(",").length) {
                showAlertDialog("未查询到结果");
                removeModal();
            }

        }, function (error) {
            delAjaxErrorMsg(error);

        }, false);
        if (result && !$.isEmptyObject(result.qyxx)) {
            //查询出结果，跳出循环
            return false;

        }
    });


}

/**
 * 将接口获取到的企业信息带入表单
 */
function daoruqyxx(bdcQlrDO, form) {
    var newbdcQlrDO = new Object();
    //循环,空值不更新
    for (var key in bdcQlrDO) {
        if (isNotBlank(bdcQlrDO[key])) {
            newbdcQlrDO[key] = bdcQlrDO[key];
        }
    }
    //表单赋值
    form.val("qlrForm", newbdcQlrDO);
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

//根据权利人类型判断必填项
function checkbyqlrlx() {
    var qlrlb = $("#qlrlb").val();
    if (qlrlb === "1") {
        var qlrlx = $("#qlrlx").val();
        //权利人类型为个人，申请人联系电话必填
        checkSfRequiredByqlrlx(qlrlx, "1", $("#dh"));
    } else {
        removeRequired($("#dh"));
    }
}

/**
 * 根据权利人类型判断是否元素必填
 * 调用方法：通过大云配置进行调用
 * eventlx 事件类型
 */
function changeAuthorityByQlrlx(qlrlxvalue, qlrlxid, eventlx) {
    var qlrlb = $("#qlrlb").val();
    if (qlrlb === "1") {
        var qlrlx = $("#qlrlx").val();
        //权利人类型为个人，申请人联系电话必填
        checkSfRequiredByqlrlx(qlrlx, "1", $("#dh"));
        //权利人类型非空且非个人时，代理人名称和代理人联系电话必填
        if (isNotBlank(qlrlx) && qlrlx !== "1") {
            addRequired($("#dlrmc"));
            addRequired($("#dlrdh"));
        } else {
            removeRequired($("#dlrmc"));
            removeRequired($("#dlrdh"));
        }
        //查看权利人不需要进行默认赋值
        if (!isNotBlank(qlrid) || eventlx === "change") {
            //权利人类型为企业时,身份证种类默认为“统一社会信用代码”，个人默认身份证
            if (qlrlx === "2") {
                $("#zjzl").val("8");
                addSfzYz("8", $("#zjzl"));
                form.render("select");
                resetSelectDisabledCss();
            } else if (qlrlx === "1") {
                $("#zjzl").val("1");
                addSfzYz("1", $("#zjzl"));
                form.render("select");
                resetSelectDisabledCss();
            }
        }
        if (qlrlx === "2") {
            addRequired($("#dlrzjzl"));
            addRequired($("#dlrzjh"));
        } else {
            removeRequired($("#dlrzjzl"));
            removeRequired($("#dlrzjh"));
        }
    } else {
        removeRequired($("#dh"));
        removeRequired($("#dlrmc"));
        removeRequired($("#dlrdh"));
        removeRequired($("#dlrzjzl"));
        removeRequired($("#dlrzjh"));
    }
    changeZjzlByQlrlx(qlrlxvalue);
}

/**
 * 共有方式为按份共有时,共有比例为必填
 */
function checkQlblSfRequiredByGyfs() {
    var gyfs = $("select[name=gyfs]").val();
    if (gyfs === "2") {
        //共有方式为按份共有时,共有比例为必填
        addRequired($("#qlbl"));
    } else {
        removeRequired($("#qlbl"));
    }

}

/**
 * 权利人信息页面修改后高亮展示方法
 */
var ShowHighLight = {
    required: null,
    logCache: null, // 用于记录修改日志缓存，防止修改成功后权利人数据重新渲染过快导致未高亮。
    // 校验是否需要高亮
    checkRequired: function () {
        if (isNotBlank(ShowHighLight.required)) {
            return ShowHighLight.required;
        }
        var required = false;
        var xgnrglxs = parent.window.xgnrglxs;
        if (isNotBlank(djxl)) {
            if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(djxl) > -1) {
                required = true;
            }
        } else {
            $.ajax({
                url: getContextPath() + "/slym/xm/xx",
                async: false,
                type: 'GET',
                dataType: 'json',
                data: {xmid: xmid},
                success: function (data) {
                    if (isNotBlank(data.djxl) && isNotBlank(xgnrglxs) && xgnrglxs.indexOf(data.djxl) > -1) {
                        required = true;
                    }
                }
            });
        }
        ShowHighLight.required = required;
        return required;
    },
    // 添加申请人修改后增加高亮日志信息
    addFormModifyLog: function (bs) {
        if ($(".bdc-change-input").length == 0) {
            return;
        }
        var bdcXxXgDTO = new Object();
        var bdcXxXgZbDTOList = [];
        $(".bdc-change-input").each(function (i) {
            var bdcXxXgZbDTO = new Object();
            var $change = $(this);
            if ($(this).hasClass("layui-input-inline")) {
                if ($(this).find("select").length > 0) {
                    $change = $(this).find("select");
                } else if ($(this).find("textarea").length > 0) {
                    $change = $(this).find("textarea");
                } else {
                    $change = $(this).find("input");
                }
            }
            bdcXxXgZbDTOList.push({
                value: $change.val(),
                name: $change.attr('name')
            });
        });
        bdcXxXgDTO.bdcXxXgZbDTOList = bdcXxXgZbDTOList;
        ShowHighLight.logCache = bdcXxXgDTO;
        getReturnData("/rest/v1.0/slym/addXgLog?gzlslid=" + bs,
            JSON.stringify(bdcXxXgDTO), "POST", function () {
            }, function (error) {
                delAjaxErrorMsg(error);
            });
    },
    // 查询表单修改日志信息，并高亮表单内容
    queryFormModifyLog: function (bs) {
        if (isNotBlank(ShowHighLight.logCache)) {
            highLight(ShowHighLight.logCache);
        } else {
            getReturnData("/rest/v1.0/slym/queryXgLog",
                {gzlslid: bs}, "GET",
                function (data) {
                    if (data && data.value) {
                        highLight(JSON.parse(data.value));
                    }
                },
                function (error) {
                }
            );
        }

        function highLight(value) {
            var modifyData = value.bdcXxXgZbDTOList;
            for (var i = 0; i < modifyData.length; i++) {
                var name = modifyData[i].name;
                if ($('input[name="' + name + '"]').length > 0) {
                    $('input[name="' + name + '"]').parents('.layui-input-inline').addClass('bdc-change-input');
                    $('input[name="' + name + '"]').css({"background-color": "#eaf4fe"})
                } else if ($('select[name="' + name + '"]').length > 0) {
                    $('select[name="' + name + '"]').parents('.layui-input-inline').addClass('bdc-change-input');
                    $('select[name="' + name + '"]').css({"background-color": "#eaf4fe"})
                } else if ($('textarea[name="' + name + '"]').length > 0) {
                    $('textarea[name="' + name + '"]').parents('.layui-input-inline').addClass('bdc-change-input');
                    $('textarea[name="' + name + '"]').css({"background-color": "#eaf4fe"})
                }
            }
        }
    },
    // 添加表单元素监听事件
    renderChange: function (formSelects, form, formIds) {
        $.each(formIds.split(","), function (index, formID) {
            if (isNotBlank(formID)) {
                var $input = $("#" + formID).find(".layui-input");
                //监听input修改
                $input.on('change', function () {
                    $(this).addClass('bdc-change-input');
                    var text = $(this).parents(".layui-inline").find("label").text();
                    window.parent.renderChangeTips(text);
                });
                var $textarea = $("#" + formID).find(".change-textarea-width");
                //监听textarea修改
                $textarea.on('change', function () {
                    $(this).addClass('bdc-change-input');
                    var text = $(this).parents(".change-textarea-margin").find("label").text();
                    window.parent.renderChangeTips(text);
                });
            }
        });

        //监听select下拉框内容修改
        form.on('select', function (data) {
            $.each(formIds.split(","), function (index, formID) {
                var $select = $("#" + formID).find(data.othis).find("input");
                if ($select.length > 0) {
                    $(data.elem).parents(".layui-input-inline").addClass('bdc-change-input');
                    var text = $(data.elem).parents(".layui-inline").find("label").text();
                    window.parent.renderChangeTips(text);
                }
            });
        });
    }
}

//组合验证权利人信息
function checkZhQlrxx(qlrData) {
    //1.权利人是企业，证件种类选择统一社会信用代码、营业执照、组织机构代码，验证证件号长度是否是18位
    var qlrmc = qlrData.qlrmc;
    var zjzl = $("#zjzl").val();
    var qlrlx = $("#qlrlx").val();
    var result = true;
    if ((qlrlx === "2" || qlrmc.indexOf("公司") > -1) && (zjzl === "6" || zjzl === "7" || zjzl === "8")) {
        //判断证件号长度18位
        var zjh = $("#zjh").val();
        var reg = /^\S{18}$/;
        result = reg.test(zjh.replace(/\s*/g, ""));
    }
    return result;
}

function changeZjzlByQlrlx(qlrlx) {
    if(!isNullOrEmpty(qlrlxZjzlDzMap)) {
        var zjzl = "";
        var zjzlZd = zdList.zjzl;
        for (var key in qlrlxZjzlDzMap) {
            if (qlrlx == key) {
                zjzl = qlrlxZjzlDzMap[key];
            }
        }
        if (isNotBlank(zjzl)) {
            var zjzlList = [];
            for (var i = 0; i < zjzlZd.length; i++) {
                if (zjzl.indexOf(zjzlZd[i].DM) > -1) {
                    zjzlList.push(zjzlZd[i]);
                }
            }
            zjzlList = zjzlList.length > 0 ? zjzlList : zjzlZd;
            $("#zjzl").empty();
            $.each(zjzlList, function (index, item) {
                $('#zjzl').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            form.render('select');
        } else {
            $("#zjzl").empty();
            $.each(zjzlZd, function (index, item) {
                $('#zjzl').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            form.render('select');
        }
    }
}

function changeqlrlxByZjzl(){
    if(!isNullOrEmpty(qlrlxZjzlDzMap)) {
        var zjzl = $("#zjzl").val();
        for (var key in qlrlxZjzlDzMap) {
            if (qlrlxZjzlDzMap[key].indexOf(zjzl) > -1) {
                $("#qlrlx").val(key);
                break;
            }
        }
        form.render('select');
    }
}