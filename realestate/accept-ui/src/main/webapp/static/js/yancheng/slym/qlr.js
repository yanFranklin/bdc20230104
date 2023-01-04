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
//页面入口
var form;
//记录页面一开始加载是该权利人是否为领证人，用于对比更新
var ysflzr;
var sfdrjyxx = getQueryString("sfdrjyxx");
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
            delAjaxErrorMsg(e,"加载失败");
            return
        }
    }, 10);

    form.on('select(qlrlx)', function (data) {
        //根据权利人类型判断必填项
        checkbyqlrlx();
        //权利人类型为企业时,身份证种类默认为“统一社会信用代码”，个人默认身份证
        if (data.value === "2") {
            $("#zjzl").val("8");
            addSfzYz("8", $("#zjzl"));
        } else if (data.value === "1") {
            $("#zjzl").val("1");
            addSfzYz("1", $("#zjzl"));
        }
        if(data.value === "2" || data.value === "3"){
            addRequired($("#frmc"));
        }else{
            removeRequired($("#frmc"));
        }
        form.render('select');
    });


    //监听共有方式
    form.on('select(gyfs)', function (data){
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
    if ((lclx === "zhlc" ||lclx ==="jdlc")) {
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
        url: getContextPath() + "/slym/qlr",
        type: 'GET',
        dataType: 'json',
        data: {qlrid: qlrid},
        success: function (data) {
            if (isNotBlank(data)) {
                generateQlr(data,'true');
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
                    qlr["sfczr"] = sfczr;
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
                qlr["sfczr"] = sfczr;
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

function generateQlr(qlr,jzlzr) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'formSelects'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var formSelects = layui.formSelects;
        var json = {
            qlr: qlr,
            zd: zdList,
            dydj: dydj,
            yhList: yhList,
            qllx: qllx,
            yharr: yhList
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
        }
        form.render();
        renderDate(form);
        if (!isNotBlank(qlr.xmid)) {
            $("#xmid").val(xmid);
        }
        form.on('select(qlrlb)', function (data) {
            changeSqrLabelMc(data.value);
            changeQlrlb(data.value);
            changeQlrtz(data.value);
        });
        form.on('select(zjzl)', function (data) {
            addSfzYz(data.value, data.elem);
            // 改变对应的select
            if (data.value == 1) {
                $("#qlrlx").val(1);
            } else if (data.value == 7) {
                $("#qlrlx").val(2);
            }
            form.render();
        });

        /*// 证件号名称失去焦点要查法人名称 当两者都有值且权利人类型是企业（事业单位）才查询
        $(document).find("input[name='qlrmc']").blur( function () {
            var qlrmc = $("input[name='qlrmc']").val();
            var zjh = $("input[name='zjh']").val();
            var qlrlb = $("select[name='qlrlb']").val();
            if(qlrlb === "1" && qlrmc && zjh && ($("#qlrlx").val() == "2" || $("#qlrlx").val() == "3")){
                queryFrmcByQlrmcAndZjh();
            }
        });
        $(document).find("input[name='zjh']").blur( function () {
            var qlrmc = $("input[name='qlrmc']").val();
            var zjh = $("input[name='zjh']").val();
            var qlrlb = $("select[name='qlrlb']").val();
            if(qlrlb === "1" && qlrmc && zjh && ($("#qlrlx").val() == "2" || $("#qlrlx").val() == "3")){
                queryFrmcByQlrmcAndZjh();
            }
        });*/


        // 新增权利人不加载领证人，默认选否;显示权利人详细信息时加载是否领证人;义务人不是领证人，全部显示否
        if(jzlzr == 'true'){
            var ylzrxx = yzYgLzr(qlr);//没有查到返回""
            if (isNotBlank(ylzrxx) && ylzrxx.length > 0) {
                ysflzr = 1;
                $("#sflzr1").attr("checked", "checked");
                $("#sflzr0").removeAttr("checked");
            } else {
                ysflzr = 0;
                $("#sflzr1").removeAttr("checked");
                $("#sflzr0").attr("checked", "checked");
            }
        }


        var result = verifyform(form);

        getStateById(readOnly, formStateId, "qlr");
        //联系电话加密显示
        toEncryptClass('dhjm');

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
        // 下拉搜索框显示选中值
        var $yhmcparent = $('#bdc-popup-large').find('select[lay-filter="yhmc"]').parent();
        $yhmcparent.find('.xm-dl-input .xm-select-input').val($yhmcparent.find('.xm-select-title span font').text());

        // 监听下拉选择框的值并赋值给前面的input
        layui.formSelects.on('yhmc', function(id, vals, val) {
            $("[name='qlrmc']").val(val.value);
            $(this).parents('.layui-input-inline').find("input[name='qlrmc']").val(val.value);
            $(this).parents('.layui-input-inline').find("dl").css({ "display": "none" });

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
                            if(data.jgid==null){
                                if(qlrlx=='1'){
                                    //权利人类型为个人，默认的身份证种类为身份证
                                    $("#zjzl").val('1');
                                }else if(qlrlx=='2'){
                                    //权利人类型为企业,默认的身份证种类为社会统一信用代码
                                    $("#zjzl").val('8');
                                }
                            }else{
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
                            //var $yhmcparent = $('#bdc-popup-large').find('select[lay-filter="yhmc"]').parent();
                            //$yhmcparent.find('.xm-dl-input .xm-select-input').val($yhmcparent.find('.xm-select-title span font').text());
                        }
                    }
                });
            }


            form.render();
            resetSelectDisabledCss();
        });


        /*formSelects.on('yhmc', function (id, vals, val, isAdd, isDisabled) {
            //id:           点击select的id
            //vals:         当前select已选中的值
            //val:          当前select点击的值
            //isAdd:        当前操作选中or取消
            //isDisabled:   当前选项是否是disabled
            //false为清空不查询
        });*/
        //提交表单
        form.on("submit(updateQlr)", function (data) {
            var flag = checkDlrColLength();

            if(!flag){
                layer.msg("代理人名称和证件号数量不一致", {icon: 5, time: 3000});
                return false;
            }

            if (isNotBlank(result) && !result.isSubmit) {
                layer.msg(result.verifyMsg, {icon: 5, time: 3000});
                result.isSubmit = true;
                return false;
            } else {
                addModel();
                // 新增xtjg，这里为了和权利人保存的逻辑区分开 不在保存权利人的后台方法中加这段逻辑
                //saveXtjg();
                if (isNotBlank(data.field.qlrid)) {
                    // 增加表单修改日志, 批量流程采用人员证件号做日志key信息
                    if(ShowHighLight.checkRequired()){
                        if (data.field.qlrlb == 2 && (lclx === "pllc" ||lclx ==="plzh")) {
                            ShowHighLight.addFormModifyLog(processInsId+data.field.zjh);
                        }else{
                            ShowHighLight.addFormModifyLog(processInsId+data.field.qlrid);
                        }
                    }
                    setTimeout(function () {
                        try {
                            if (sessionStorage.qlridList == undefined) {
                                $.when(updateQlrYz(data.field)).done(function () {

                                })
                            } else {
                                $.when(updateYwr(data.field)).done(function () {
                                })
                            }

                        } catch (e) {
                            removeModal();
                            delAjaxErrorMsg(e,"保存失败");
                            return
                        }
                    }, 10);
                } else {
                    setTimeout(function () {
                        try {
                            $.when(insertQlrYz(data.field)).done(function () {
                            })
                        } catch (e) {
                            removeModal();
                            delAjaxErrorMsg(e,"保存失败");
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
        $("#yzqyxx").click(function () {

                setTimeout(function () {
                    yzqyxx();
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
        if(ShowHighLight.checkRequired()){
            if (lclx === "pllc"  ||lclx ==="plzh") {
                ShowHighLight.queryFormModifyLog(processInsId+qlr.zjh);
            }else{
                ShowHighLight.queryFormModifyLog(processInsId+qlrid);
            }
            //添加申请人表单增加修改后监听
            ShowHighLight.renderChange(formSelects, form, "qlrForm");
        }
        form.render();
    })
}

function updateQlrYz(bdcQlrData) {
    updateQlr(bdcQlrData);
}

function updateQlr(bdcQlrData) {
    //将中文括号转换为英文括号
    var obj = replaceBracketArray();
    Object.keys(obj).forEach(function (key) {
        bdcQlrData[key] = obj[key];
    });
    //申请人名称单独处理
    bdcQlrData.qlrmc = replaceBracket(bdcQlrData.qlrmc);

    //将证件号中小写字母改为大写
    toUpperClass(bdcQlrData,["zjh","dlrzjh","frzjh","lzrzjh"]);
    var qlrData;
    var url = "";
    if (lclx === "pllc"  ||lclx ==="plzh") {
        qlrData = bdcQlrData;
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
    }
    $.ajax({
        url: url,
        type: 'PATCH',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(qlrData),
        success: function (data) {
            if (bdcQlrData.qlrlb == "1") {
                var sflzr = $("input[name='sflzr']:checked").val();
                if (sflzr == 1) {
                    if (ysflzr == 1) {
                        saveHfLzr(bdcQlrData, 'update');
                    } else {
                        saveHfLzr(bdcQlrData, 'insert');
                    }
                } else {
                    if (ysflzr == 1) {
                        deleteLzr(bdcQlrData);
                    }
                }
            }
            //抵押更新义务人的时候同步更新第三权利人
            //新增义务人的时候同时保存进第三权利人为债务人
            if (qllx === commonDyaq_qllx && bdcQlrData.qlrlb === "2") {
                //抵押更新义务人的时候同步更新第三权利人
                //新增义务人的时候同时保存进第三权利人为债务人
                var dsQlr = $.extend({}, bdcQlrData);
                var dsqlrurl = "/slym/qlr/list/updateDsQlr/dyaq?processInsId=" + processInsId;
                if (isNotBlank(dsQlr)) {
                    dsQlr.qlrlb = '4';
                    var xmid = $(".qlxx.layui-this", window.parent.document).find("input[name='xmid']").val();
                    dsQlr.xmid = xmid;
                    getReturnData(dsqlrurl, JSON.stringify(dsQlr), 'PATCH', function (data) {
                    }, function (err) {
                        throw err;
                    }, false);
                }
            }

            //批量组合返回的不是更新数量
            if ((lclx === "plzh" && isNotBlank(data)) || data > 0) {
                loadQlr();
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
    //将证件号中小写字母改为大写
    toUpperClass(bdcQlrData,["zjh","dlrzjh","frzjh","lzrzjh"]);
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
                if (qllx === commonDyaq_qllx && bdcQlrData.qlrlb === "2") {
                    //抵押更新义务人的时候同步更新第三权利人
                    //新增义务人的时候同时保存进第三权利人为债务人
                    var dsQlr = $.extend({}, bdcQlrData);
                    var dsqlrurl = "/slym/qlr/list/updateDsQlr/dyaq?processInsId=" + processInsId;
                    if (isNotBlank(dsQlr)) {
                        dsQlr.qlrlb = '4';
                        var xmid = $(".qlxx.layui-this", window.parent.document).find("input[name='xmid']").val();
                        dsQlr.xmid = xmid;
                        dsQlr.qlrid = bdcQlrData.qlrid;
                        getReturnData(dsqlrurl, JSON.stringify(dsQlr), 'PATCH', function (data) {
                        }, function (err) {
                            throw err;
                        }, false);
                    }
                }
                //权利人ID发生改变，故弹出层默认关闭
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

function insertQlrYz(bdcQlrData) {
    //新增权利人，当是否领证人选 是，则验证是否已有领证人
    // if(bdcQlrData.sflzr == 1 && bdcQlrData.qlrlb == "1"){
    //     //多个权利人只能有一个领证人
    //     var yyLzr = yzYgLzr(bdcQlrData);
    //     if(yyLzr.length > 1) {
    //         removeModal();
    //         warnMsg("该项目有多个领证人，数据有误！请联系管理员！");
    //         return false;
    //     }
    //     if(yyLzr.length == 1 && !isNotBlank(yyLzr[0].qlrid)){
    //         ysflzr = 1;
    //         insertQlr(bdcQlrData);
    //         return;
    //     }
    //     if(yyLzr.length == 1 && yyLzr[0].qlrid != bdcQlrData.qlrid && bdcQlrData.zjzl ==1){
    //         removeModal();
    //         layui.layer.confirm("已有领证人信息，是否替换？", {
    //             title: "提示",
    //             btn: ["是", "否"],
    //             btn1: function (index) {
    //                 ysflzr = 1;
    //                 layer.close(index);
    //             },
    //             btn2: function (index) {
    //                 bdcQlrData.sflzr = 0;
    //                 $("#sflzr1").removeAttr("checked");
    //                 $("#sflzr0").attr("checked","checked");
    //                 layer.close(index);
    //                 return;
    //             },
    //             end: function () {
    //                 insertQlr(bdcQlrData);
    //             }
    //         });
    //     }else {
    //         insertQlr(bdcQlrData)
    //     }
    // }else {
    insertQlr(bdcQlrData);
    // }
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
    toUpperClass(bdcQlrData,["zjh","dlrzjh","frzjh","lzrzjh"]);
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
                // 是否领证人选择 是 则保存领证人
                if (bdcQlrData.sflzr == 1 && bdcQlrData.qlrlb == "1") {
                    // if (ysflzr == 1) {
                    //     saveHfLzr(data, "update");
                    // } else {
                    saveHfLzr(data, "insert");
                    // }

                }
                if (qllx === commonDyaq_qllx && bdcQlrData.qlrlb === "2") {
                    //新增义务人的时候同时保存进第三权利人为债务人
                    var dsQlr = $.extend({}, data);
                    var dsqlrurl = "/slym/qlr/insertDsqlr?processInsId=" + processInsId;
                    if (isNotBlank(dsQlr)) {
                        dsQlr.qlrlb = '4';
                        var xmid = $(".qlxx.layui-this", window.parent.document).find("input[name='xmid']").val();
                        dsQlr.xmid = xmid;
                        getReturnData(dsqlrurl, JSON.stringify(dsQlr), 'POST', function (data) {
                        }, function (err) {
                            throw err;
                        }, false);
                    }
                }
                loadQlr();
                successCz("insert");
            }
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }
    });
}

//验证只有一个领证人
function yzYgLzr(bdcQlrData){
    var lzrxx = [];
    $.ajax({
        url: getContextPath() + '/slym/lzr/yc/lzrxx?qlrid=' + bdcQlrData.qlrid + "&gzlslid=" + processInsId,
        type: 'GET',
        async: false,
        success: function (data) {
            lzrxx = data;
        },
        error: function (xhr) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
    return lzrxx;
}

function deleteLzr(bdcQlrData){
    var url = "";
    if(lclx === "pllc") {
        url = "/slym/lzr/yc/lzrdelete/pl?gzlslid=" + processInsId;
    }else if(lclx === "plzh") {
        url = "/slym/lzr/yc/lzrdelete/pl?gzlslid=" + processInsId + "&djxl=" +  djxl;
    } else {
        url = "/slym/lzr/yc/lzrdelete?qlrid=" + bdcQlrData.qlrid + "&gzlslid=" + processInsId;
    }
    getReturnData(url,null,'DELETE',function (data) {

    },function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    })
}


function saveHfLzr(bdcQlrData,func) {
    // 只有权利人才会保存对应的领证人
    if(isNotBlank(bdcQlrData) && bdcQlrData.qlrlb === "1" && bdcQlrData.zjzl == 1) {
        var lzrList = [];
        var bdclzr = {};
        if (isNotBlank(bdcQlrData.qlrmc)) {
            bdclzr.qlrid = bdcQlrData.qlrid;
            bdclzr.xmid = bdcQlrData.xmid;
            bdclzr.lzrmc = bdcQlrData.qlrmc;
            bdclzr.lzrzjzl = bdcQlrData.zjzl;
            bdclzr.lzrzjh = bdcQlrData.zjh;
            bdclzr.lzrdh = bdcQlrData.dh;
        }
        lzrList.push(bdclzr);
        if(func === "insert") {
            var inserturl = "";
            if(lclx === "pllc") {
                inserturl = "/slym/lzr/hf/lzrxx/pllc?gzlslid=" + processInsId + "&xmid=" +  bdcQlrData.xmid + "&qlrid=" + bdcQlrData.qlrid;
            }else if(lclx === "plzh") {
                inserturl = "/slym/lzr/hf/lzrxx/plzh?gzlslid=" + processInsId + "&qlrid=" + bdcQlrData.qlrid + "&djxl=" +  djxl + "&xmid=" + bdcQlrData.xmid;
            } else {
                //简单流程和组合流程都是传入lzr直接入库，盐城复用合肥接口
                inserturl = "/slym/lzr/hf/lzrxx?gzlslid=" + processInsId + "&qlrid=" + bdcQlrData.qlrid;
            }
            getReturnData(inserturl,JSON.stringify(lzrList),'POST',function (data) {

            },function (xhr) {
                removeModal();
                delAjaxErrorMsg(xhr);
            },false);
        }
        if(func === "update") {
            var updateurl = "";
            if(lclx === "pllc") {
                updateurl = "/slym/lzr/yc/lzrxx/pl?gzlslid=" + processInsId;
            }else if(lclx === "plzh") {
                updateurl = "/slym/lzr/yc/lzrxx/pl?gzlslid=" + processInsId + "&djxl=" +  djxl;
            } else {
                updateurl = "/slym/lzr/yc/lzrxx?gzlslid=" + processInsId + "&qlrid=" + bdcQlrData.qlrid+ "&xmid=" +  bdcQlrData.xmid;
            }
            getReturnData(updateurl,JSON.stringify(bdclzr),'PATCH',function (data) {

            },function (xhr) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }, false);
        }
    }

}

function windowParentLoad() {

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
    //用于信息补录
    if (typeof (eval(window.parent.loadQlr)) == "function" && lclx === "jdlc") {
        window.parent.loadQlr();
    }
}

function cancel() {
    window.parent.cancelEdit();
}

function addSfzYz(zjzl, elem) {
    var zjzlid = $(elem).parent().parent().find("select")[0].id;
    var zjhid = zjzlid.replace("zjzl", "zjh");
    var attrVal = $("#" + zjhid).attr("lay-verify");
    var verify = "identitynew";
    if(zjhid == "dlrzjh"){
        verify = "identitynews";
    }
    if (zjzl === "1" || zjzl === 1) {
        //增加身份证验证
        if (isNotBlank(attrVal)) {
            if (attrVal.indexOf(verify) < 0) {
                $("#" + zjhid).attr("lay-verify", attrVal + "|"+verify);
            }
        } else {
            $("#" + zjhid).attr("lay-verify", verify);
        }
    } else {
        //移除身份证验证
        //增加长度大于五位验证
        if (isNotBlank(attrVal) && attrVal.indexOf(verify) > -1) {
            $("#" + zjhid).attr("lay-verify", attrVal.replace(verify, ""));
        }
        var newattr = $("#" + zjhid).attr("lay-verify");
        if (isNotBlank(newattr) && newattr.indexOf("zjhlength") < 0) {
            $("#" + zjhid).attr("lay-verify", newattr + "|zjhlength");
        } else if (zjzl !== null && zjzl !== "") {
            $("#" + zjhid).attr("lay-verify", "zjhlength");
        } else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("zjhlength") > -1) {
            $("#" + zjhid).attr("lay-verify", attrVal.replace("zjhlength", ""));
        }
        //盐城证件种类为其他去掉长度大于5 位的验证
        attrVal = $("#" + zjhid).attr("lay-verify");
        if ((zjzl === "99" || zjzl === 99) && attrVal && isNotBlank(attrVal) && attrVal.indexOf("zjhlength") > -1) {
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
            if(data &&data.length >0){
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
    if(!isNotBlank(qlrlx)){
        showAlertDialog("请输入权利人类型");
        removeModal();
        return false;
    }
    var qlrmc = $("#qlrmc").val();
    if(!isNotBlank(qlrmc)){
        showAlertDialog("请输入权利人名称");
        removeModal();
        return false;
    }
    var qlrzjh = $("#zjh").val();
    var beanNames ="";
    if (qlrlx === "2") {
        //权利人类型为企业,调用工商接口
        // beanNames="gs_qyxydmcx";
        beanNames = "hf_qyjbxxcx";
    }else if(qlrlx ==="3" ||qlrlx ==="4"){
        //权利人类型为事业单位,调用国家机关,调用社会组织_事业单位_机关团体融合查询接口
        beanNames="hf_acceptRhcx";
    }else if(qlrlx ==="99"){
        //权利人类型为其他,优先调用社会组织_事业单位_机关团体融合查询接口,查询不到查询融资担保_小额贷款公司信息接口
        beanNames="hf_acceptRhcx,hf_acceptRzdbgscx";

    }
    if(!isNotBlank(beanNames)){
        showAlertDialog("当前权利人类型未开放接口");
        removeModal();
        return false;
    }
    $.each(beanNames.split(","), function (index, beanName) {
        //接口返回结果
        var result ={};
        getReturnData("/slym/qlr/getQyxx/yc", {qlrmc:qlrmc,zjh:qlrzjh}, "GET", function (data) {
            result =data;
            if (data && !$.isEmptyObject(data.qyxx)) {
                removeModal();
                var qyzt = data.qyxx[0].qyzt;
                var bdcQlrDO = data.qyxx[0].bdcQlrDO;
                bdcQlrDO.qlrwbzt =qyzt;
                if(isNotBlank(qyzt)){
                    var msg ="当前查询的企业状态为"+qyzt+",是否带入？";
                    var layerindex = layer.open({
                        type: 1,
                        skin: 'bdc-small-tips',
                        title: '提示信息',
                        area: ['320px'],
                        content: msg,
                        btn: ['确定', '取消'],
                        btnAlign: 'c',
                        yes: function () {
                            daoruqyxx(bdcQlrDO,form);
                            layer.close(layerindex);
                            // return false;
                        },
                        btn2: function () {
                            //取消
                            layer.close(layerindex);
                        }
                    });
                }else{
                    //企业状态为空直接带入
                    daoruqyxx(bdcQlrDO,form);
                }
            }else if((index +1) ===beanNames.split(",").length){
                showAlertDialog("未查询到结果");
                removeModal();
            }

        }, function (error) {
            delAjaxErrorMsg(error);

        },false);
        if (result && !$.isEmptyObject(result.qyxx)) {
            //查询出结果，跳出循环
            return false;

        }
    });
}

/**
 * 通过验证企业信息
 */
function yzqyxx() {
    addModel();
    var qlrmc = $("#qlrmc").val();
    if(!isNotBlank(qlrmc)){
        showAlertDialog("请输入权利人名称");
        removeModal();
        return false;
    }
    var qlrzjh = $("#zjh").val();
    if(!isNotBlank(qlrzjh)){
        showAlertDialog("请输入权利人证件号");
        removeModal();
        return false;
    }
    var frmc = $("#frmc").val();

    getReturnData("/slym/qlr/yzQyxx", {entname: qlrmc,uniscid:qlrzjh, name:frmc}, "GET", function (data) {
        removeModal();
        if (data) {
            var msg = "";
            if(data.entchk_checkres_key == "01"){
                msg ="未查询到主体";
            }else if(data.entchk_checkres_key == "02"){
                msg = "输入的企业名称和社会信用代码存在歧义";
            }else if(data.entchk_checkres_key == "04"){
                msg = "存在不一致";
                var diffList = data.diff_list;
                if(diffList && diffList.length >0){
                    var inVal = diffList[0].in_val;
                    var outVal = diffList[0].out_val;
                    $("#in_val").val(inVal);
                    $("#out_val").val(outVal);
                    layer.open({
                        type: 1,
                        title: '不一致信息',
                        area: ['430px', '246px'],
                        content: $('#qyxxPopup'),
                        cancel: function () {
                            $("#qyxxPopup").css('display', 'none');
                        },
                        success: function (layero, index) {
                            disabledAddFa();
                        }

                    });
                }
                return;
            }else if(data.entchk_checkres_key == "05"){
                msg = "缺失必填参数";
            }else if(data.entchk_checkres_key == "06"){
                msg = "查询过程报错";
            }else{
                ityzl_SHOW_SUCCESS_LAYER("验证通过");
                return;
            }
            var layerindex = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: '提示信息',
                area: ['320px'],
                content: msg,
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function () {
                    layer.close(layerindex);
                }
            });
        }else{
            showAlertDialog("未查询到结果");
            removeModal();
        }
    }, function (error) {
        delAjaxErrorMsg(error);

    },false);
}




/**
 * 将接口获取到的企业信息带入表单
 */
function daoruqyxx(bdcQlrDO,form){
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
function changeAuthorityByQlrlx(qlrlxvalue,qlrlxid,eventlx) {
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
        if (!isNotBlank(qlrid) || eventlx ==="change") {
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
        } else  {
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
 * 权利人信息页面修改后高亮展示方法
 */
var ShowHighLight = {
    required : null,
    logCache : null, // 用于记录修改日志缓存，防止修改成功后权利人数据重新渲染过快导致未高亮。
    // 校验是否需要高亮
    checkRequired : function(){
        if(isNotBlank(ShowHighLight.required)){
            return ShowHighLight.required;
        }
        var required = false;
        var xgnrglxs = parent.window.xgnrglxs;
        if(isNotBlank(djxl)){
            if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(djxl) > -1) {
                required = true;
            }
        }else{
            $.ajax({
                url: getContextPath() + "/slym/xm/xx",
                async : false,
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
    addFormModifyLog : function (bs) {
        if($(".bdc-change-input").length == 0){
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
                value : $change.val(),
                name : $change.attr('name')
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
    queryFormModifyLog : function (bs) {
        if(isNotBlank(ShowHighLight.logCache)){
            highLight(ShowHighLight.logCache);
        }else{
            getReturnData("/rest/v1.0/slym/queryXgLog",
                {gzlslid: bs}, "GET",
                function (data) {
                    if (data && data.value) {
                        highLight(JSON.parse(data.value));
                    }
                },
                function (error) {}
            );
        }
        function highLight(value){
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
    renderChange : function(formSelects, form, formIds){
        $.each(formIds.split(","), function (index, formID) {
            if (isNotBlank(formID)) {
                var $input = $("#" + formID).find(".layui-input");
                //监听input修改
                $input.on('change', function () {
                    $(this).addClass('bdc-change-input');
                    var text =$(this).parents(".layui-inline").find("label").text();
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

/**
 * 根据登记原因控制页面权限初始化方法
 */
function initSfdrjyxx(val,id,eventlx,type){
    console.log("当前事件类型为"+eventlx);
    if(sfdrjyxx == "1" && id ){
        if($("#"+id)[0].type == "button"){
            $("#"+id).hide();
        }else{
            $("#"+id).attr('disabled', 'disabled');
        }
        disabledAddFa();
    }
}

function checkDlrColLength(){
    var dlrmc = $("#dlrmc").val();
    var dlrzjh = $("#dlrzjh").val();
    var dlrdh = $("#dlrdh").val();

    if(dlrmc && dlrzjh){
        dlrmc = dlrmc.replace(/，/g,",");
        dlrzjh = dlrzjh.replace(/，/g,",");
        var dlrmcArr = dlrmc.split(",");
        var dlrzjhArr = dlrzjh.split(",");

        if(dlrmcArr.length != dlrzjhArr.length){
            return false
        }
    }

    return true;
}

