var layer;
//共有方式有问题的，保存结束提示信息
var saveMsg = '';
var ydjyy = '';
var ydjyyArr = {};
var sfchange = false;
var warnLayer;
//当前配置中需要检查是否存在非18位的身份证的工作流定义id
var sfzyzGzldyid = getSfzyzGzldyid();
var isChangeJzmj = false;
$(function () {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var form = layui.form;
        layer = layui.layer;
        //点击提交时不为空的全部标红
        var isSubmit = true;
        var isFirst = true;
        var verifyMsg = "必填项不能为空";

        var btZdItem = [];
        form.verify({
            required: function (value, item) {
                if (value == '' || value == null || value == undefined) {//判断是否为空
                    var bdcdyxx = $("div[name='bdcdyxx']").find($(item));
                    if ((isXndyh &&bdcdyxx.length ===0 && zxlc !== "true") ||(!isXndyh)) {
                        //虚拟单元号如果需要验证的元素不在不动产单元信息模块，且非注销类流程,则进行校验 或者为非虚拟号
                        if (isFirst) {
                            $('.layui-tab-item').removeClass('layui-show');
                            $(item).parents('.layui-tab-item').addClass('layui-show');
                            var liIndex = $(item).parents('.layui-tab-item').index() + 1;
                            $('.layui-tab-title li').removeClass('layui-this');
                            $('.layui-tab-title li:nth-child(' + liIndex + ')').addClass('layui-this');
                            isFirst = false;
                        }
                        btZdItem.push($(item).attr("id"));
                    }
                }
            }
            , identitynew: function (value, item) {
                var msg = checkSfzZjh(value);
                if (isNotBlank(msg)) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = msg;
                }
            }, lxdh: function (value, item) {
                if (!validatePhone($.trim(value))) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = "联系电话格式不正确";
                }
            }
            ,number:function (value, item) {
                //为非负整数,允许为空
                if (isNotBlank(value) && !/^[1-9]+[0-9]*]*$/.test(value)) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = "请输入非负整数";
                }
            }
            //数字
            , number2: [/^-?\d*$/, '请输入数字']
            //允许为空
            ,number3:function (value,item) {
                if(isNotBlank(value) &&!/^[0-9]+.{0,1}[0-9]{0,2}$/.test(value)){
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = "请输入数字";
                }
            }
        });
        //组合流程提交前执行同步对照
        $(".bdc-form-div").on('click', '#saveData', function () {
            ywxxDozer();
        });
        form.on("submit(saveData)", function (data) {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                isFirst = true;
                return false;
            } else {
                addModel();
                setTimeout(function () {
                    try {
                        $.when(saveXmxx(), saveQlxx(), saveSjcl(), saveLzr(), saveQlr(),saveDsQlr(),updateFwfsss(), insertXgLog()).done(function () {
                            //保存收费信息
                            syncSaveSfxx();
                            if (lclx === "zhlc") {
                                refreshQlxx();
                            } else if (lclx === "pllc") {
                                loadBdcdyh();
                            }
                            removeModal();
                            if (saveMsg == '') {
                                ityzl_SHOW_SUCCESS_LAYER("保存成功");

                                if(btZdItem && btZdItem.length > 0){
                                    window.setTimeout(function(){
                                        for(var i=0;i<btZdItem.length;i++){
                                            if($("#"+btZdItem[i]) && $("#"+btZdItem[i]).val() == ""){
                                                $("#"+btZdItem[i]).addClass('layui-form-danger');
                                            }
                                        }
                                        btZdItem = [];
                                    },1500)
                                }
                            } else {
                                ityzl_SHOW_WARN_LAYER(saveMsg);
                            }
                            saveMsg = '';
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
            }

        });

        //  是否最后一幢 单选框选择事件
        form.on('radio(sfzhyclh)', function(data){
            if(data.value == "1"){
                getReturnData('/bdcGzyz/checkWlzs',{gzlslid: processInsId},'GET',function (data) {
                    if(data == false){
                        showTsxxMsg("当前项目未外联证书并注销。");
                    }
                },function (xhr) {
                    delAjaxErrorMsg(xhr);
                });
            }
        });
    });
});

//项目信息保存（单个保存）
function saveBdcXm(formClass, index) {
    var bdcXmData = {};
    var bdcXmArray = $(formClass);
    if (bdcXmArray !== null && bdcXmArray.length > 0) {
        bdcXmArray.serializeArray().forEach(function (item, index) {
            bdcXmData[item.name] = item.value;
        });
        var $jzmj = $("#qllx" + (index)).find("input[name=jzmj]");
        if($jzmj.length >0 &&$jzmj[0].id !=="fdcqxmdz-jzmj"){
            bdcXmData.dzwmj = $jzmj.val();
        }else {
            //同步单元信息的时候会存在dzwmj不可编辑导致获取不到值的情况，这边单独取值保存
            var dzwmj = $("#bdcdyxxForm" + index).find("#dzwmj").val();
            if (dzwmj !== null && dzwmj !== undefined) {
                bdcXmData.dzwmj = dzwmj;
            }
        }
        var dzwyt = $("#bdcdyxxForm" + index).find("#dzwyt").val();
        if (dzwyt !== null && dzwyt !== undefined) {
            bdcXmData.dzwyt = dzwyt;
        }
        var djyy = $("#djyy" + index).val();
        if(djyy !== ydjyyArr['djyy'+index]){
            sfchange =true;
            //防止页面未刷新多次保存
            ydjyyArr['djyy'+index] =djyy;
        }else{
            sfchange =false;
        }
        bdcXmData.djyy = djyy;
        var sqfbcz = $($(formClass).find("input[type='radio']:checked")).val();
        if (isNotBlank(sqfbcz)) {
            bdcXmData.sqfbcz = sqfbcz;
        }
        getReturnData("/slym/xm", JSON.stringify(bdcXmData), 'PATCH', function (data) {
            //保存项目后判断登记原因是否改变过，改变则重新创建收件材料
            if(sfchange && lclx === "zhlc") {
                reCreateSjcl();
            }
        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
    }

}

//更正信息保存
function saveGzxx(classname, index) {
    var bdcGzxxData = {};
    var bdcGzxxArray = $(classname);
    if (bdcGzxxArray !== null && bdcGzxxArray.length > 0) {
        bdcGzxxArray.serializeArray().forEach(function (item, index) {
            bdcGzxxData[item.name] = item.value;
        });
        getReturnData("/rest/v1.0/gzdj", JSON.stringify(bdcGzxxData), 'PUT', function (data) {
        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
    }

}

//交易信息保存(组合页面）
function saveJyxx(formClass, index) {
    var jyxxData = {};
    var jyxxArray = $(formClass);
    if (jyxxArray !== null && jyxxArray.length > 0) {
        jyxxArray.serializeArray().forEach(function (item, index) {
            jyxxData[item.name] = item.value;
        });
        if(isNotBlank(jyxxData.jyjg)){
            //BDC_SL_JYXX 中jyje与fdcq 中jyjg同步
            jyxxData.jyje =jyxxData.jyjg;
        }
        var xmid = $(".xmxx" + index).find("input[name=xmid]").val();
        var jyxxid = $("#xmForm" + index).find("input[name=jyxxid]").val();
        if (isNotBlank(xmid)) {
            jyxxData["xmid"] = xmid;
            jyxxData["jyxxid"] = jyxxid;
            // 2020-3-11 新增用于在登记页面填写了交易合同号后，保存时同步合同编号至受理库中
            if($(".xmxx" + index).find("input[name=jyhth]").length != 0){
                jyxxData["htbh"] = $(".xmxx" + index).find("input[name=jyhth]").val();
            }
            getReturnData("/ycsl/htxx", JSON.stringify(jyxxData), 'PATCH', function (data) {
                if (data) {
                    $("#xmForm" + index).find("input[name=jyxxid]").val(data.jyxxid);
                }
            }, function (err) {
                delAjaxErrorMsg(err);
            }, false);
        }
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
        if($("input[name=jyhth]").length != 0){
            jyxxData["htbh"] = $("input[name=jyhth]").val();
        }
        getReturnData("/ycsl/jyxx/list?processInsId=" + processInsId, JSON.stringify(jyxxData), 'PATCH', function (data) {
            loadJbxx();

        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
    }

}

//项目信息保存（批量保存）
function saveXmxx() {
    var bdcXmData = {};
    var bdcXmArray = $(".bdcxm");
    bdcXmArray.serializeArray().forEach(function (item, index) {
        bdcXmData[item.name] = item.value;
    });
    bdcXmData.bz = $("#bz").val();
    if (lclx === "pllc") {
        var djyy =$("#djyy").val();
        bdcXmData.djyy = djyy;
        if(djyy !== ydjyy){
            sfchange =true;
            //防止页面未刷新多次保存
            ydjyy =djyy;
        }else{
            sfchange =false;
        }
        var sqfbcz = "";
        $("input:radio[name='sqfbcz']:checked").each(function () {
            sqfbcz = $(this).val();
        });
        if (isNotBlank(sqfbcz)) {
            bdcXmData.sqfbcz = sqfbcz;
        }
    }

    //默认为平方米，防止不可编辑冲掉
    var mjdw = '1';
    $("input:checkbox[name='mjdw']:checked").each(function () {
        mjdw = $(this).val();
    });
    if (isNotBlank(mjdw)) {
        bdcXmData.mjdw = mjdw;
    }
    // 转移登记一并申请默认为否
    var zydjybsq = 0;
    $("input:radio[name='zydjybsq']:checked").each(function () {
        zydjybsq = $(this).val();
    });
    bdcXmData.zydjybsq = zydjybsq;

    getReturnData("/slym/xm/list?processInsId=" + processInsId, JSON.stringify(bdcXmData), 'PATCH', function (data) {
        //保存项目后判断登记原因是否改变过，改变则重新创建收件材料,----pllc,组合流程在另外一个保存方法，防止冲突
        if(sfchange && lclx === 'pllc') {
            reCreateSjcl();
        }
    }, function (err) {
        throw err;
    }, false);
}

//权利信息保存（入口方法）
function saveQlxx() {
    saveXmfbPl(".xmfb");
    if (lclx === "zhlc") {

        var index = $("input[name='liIndex']");
        if (index !== null && index.length > 0) {
            for (var i = 0; i < index.length; i++) {
                var c = $(index[i]).val();
                saveXmfb(".xmfb" + c);
                saveQl($(".tdsyq" + c), "cn.gtmap.realestate.common.core.domain.BdcTdsyqDO", c);
                saveQl($(".fdcq" + c), "cn.gtmap.realestate.common.core.domain.BdcFdcqDO", c);
                saveQl($(".dyaq" + c), "cn.gtmap.realestate.common.core.domain.BdcDyaqDO", c);
                saveQl($(".cf" + c), "cn.gtmap.realestate.common.core.domain.BdcCfDO", c);
                saveQl($(".yg" + c), "cn.gtmap.realestate.common.core.domain.BdcYgDO", c);
                saveQl($(".yy" + c), "cn.gtmap.realestate.common.core.domain.BdcYyDO", c);
                saveQl($(".lq" + c), "cn.gtmap.realestate.common.core.domain.BdcLqDO", c);
                saveQl($(".tdcbnydsyq" + c), "cn.gtmap.realestate.common.core.domain.BdcTdcbnydsyqDO", c);
                saveQl($(".dyiq" + c), "cn.gtmap.realestate.common.core.domain.BdcDyiqDO", c);
                saveQl($(".jsydsyq" + c), "cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO", c);
                saveQl($(".gjzwsyq" + c), "cn.gtmap.realestate.common.core.domain.BdcGjzwsyqDO", c);
                saveQl($(".fdcq3" + c), "cn.gtmap.realestate.common.core.domain.BdcFdcq3DO", c);
                saveQl($(".hysyq" + c), "cn.gtmap.realestate.common.core.domain.BdcHysyqDO", c);
                saveQl($(".jzq" + c), "cn.gtmap.realestate.common.core.domain.BdcJzqDO", c);
                saveGzxx(".gzxx" + c, c);
                saveBdcXm(".xmxx" + c, c);
                //保存交易信息，存在即保存
                //盐城暂时不需要交易字段
                if ($(".jyxx" + c).length > 0) {
                    saveJyxx(".jyxx" + c, c);
                }
            }

        }

        //组合流程，处理一些需要同步权利表字段的更新
        var qlData = {};
        var qllx = $($("input[name='qllx']")[0]).val();
        //获取查封机关和解封机关
        var cfjg = document.getElementById("cf-cfjg");
        var jfjg = document.getElementById("cf-jfjg");
        if(isNotBlank(cfjg)){
            qlData['cfjg'] = cfjg.value;
        };
        if(isNotBlank(jfjg)){
            qlData['jfjg'] = jfjg.value;
        };
        qlData['xmid'] = xmid;
        if (!$.isEmptyObject(qlData) && isNotBlank(xmid)) {
            // 同步查封机关和解封机关至权利人
            if(qllx == 98){
                syncCfjg(qlData);
                syncJfjg(qlData);
            }
        }
    } else {
        //批量流程，处理一些需要同步权利表字段的更新
        var qlData = {};
        var qlxxArray = $(".qlxx").serializeArray();
        qlxxArray.forEach(function (item, index) {
            var name = item.name;
            qlData[name] = item.value;
        });

        //同步登记原因或注销登记原因
        var djyy = $("#djyy").val();
        var qllx = $($("input[name='qllx']")[0]).val();
        //抵押权的被担保主债权数额和最高债权额根据抵押类型保存
        if (qllx === commonDyaq_qllx) {
            var dyfs = qlData.dyfs;
            if (dyfs) {
                if (dyfs === "1") {
                }
                if (dyfs === "2") {
                    qlData.zgzqe = qlData.bdbzzqse;

                }
            }
        }
        if (zxlc !== "true" && qllx !== "98" && qllx !== "97" && qllx !== "94") {
            qlData.djyy = djyy;
        } else if (zxlc === "true") {
            if (qllx === commonDyaq_qllx || qllx === "19") {
                //注销抵押原因/注销地役原因
                qlData.zxdyyy = djyy;
            } else if (qllx === "97") {
                //注销异议原因
                qlData.zxyyyy = djyy;
            }
        }
        if (!$.isEmptyObject(qlData) && isNotBlank(xmid)) {
            // 同步查封机关和解封机关至权利人
            if(qllx == 98){
                syncCfjg(qlData);
                syncJfjg(qlData);
            }

            getReturnData("/slym/ql/list?processInsId=" + processInsId + "&onexmid=" + xmid + "&zxlc=" + zxlc, JSON.stringify(qlData), 'PATCH', function (data) {

            }, function (err) {
                delAjaxErrorMsg(err);
            });
        }


        //保存交易信息
        saveJyxxPl();
    }
}

//权利信息保存（具体方法）
function saveQl(formClass, className, index) {
    if (formClass !== null && formClass.length > 0) {
        var qlData = {};
        formClass.serializeArray().forEach(function (item) {
            qlData[item.name] = item.value;
        });
        if (className === "cn.gtmap.realestate.common.core.domain.BdcCfDO") {
            //将中文括号转换为英文括号
            var bracketobj = replaceBracketArray();
            //中文括号转换为英文括号
            Object.keys(bracketobj).forEach(function (key) {
                qlData[key] = bracketobj[key];
            });
            //合肥需求，查封文号中文括号转为英文括号
            if (isNotBlank(qlData.cfwh)) {
                qlData.cfwh = qlData.cfwh.replace('（', '(');
                qlData.cfwh = qlData.cfwh.replace('）', ')');
            }
            if (isNotBlank(qlData.jfwh)) {
                qlData.jfwh = qlData.jfwh.replace('（', '(');
                qlData.jfwh = qlData.jfwh.replace('）', ')');
            }
        }
        //同步登记原因
        var djyy = $("#djyy" + index).val();
        if (zxlc !== "true") {
            qlData.djyy = djyy;
        } else {
            //注销抵押原因/注销地役原因
            qlData.zxdyyy = djyy;
            //注销异议原因
            qlData.zxyyyy = djyy;
        }
        //抵押权的被担保主债权数额和最高债权额根据抵押类型保存
        if (className === "cn.gtmap.realestate.common.core.domain.BdcDyaqDO") {
            var dyfs = qlData.dyfs;
            if (dyfs) {
                if (dyfs === "1") {
                }
                if (dyfs === "2") {
                    //最高额抵押存储 最高债权额字段
                    qlData.zgzqe = qlData.bdbzzqse;
                }
            }
        }
        //坐落同步
        qlData.zl = $("#zl").val();
        //如果formClass包含房地产权，同步规划用途
        var getClassNameUse = formClass.attr('class');
        if(getClassNameUse.indexOf('fdcq') > -1 &&$('.'+getClassNameUse+'[name=ghyt]').length >0){
            qlData['ghyt'] = $('.'+getClassNameUse+'[name=ghyt]').val();
        }
        //如果formClass包含建设用地使用权，同步使用权面积
        if(getClassNameUse.indexOf('jsydsyq') > -1 &&$('.'+getClassNameUse+'[name=syqmj]').length >0){
            qlData['syqmj'] = $('.'+getClassNameUse+'[name=syqmj]').val();
        }

        //如果formClass包含林权，且xmxx有林种的属性
        if(getClassNameUse.indexOf('lq') > -1 &&$('#lz[class=xmxx'+index+']').length >0){
            // 取项目信息里面的lz 同步到权利信息里面
            qlData['lz'] = $('#lz[class=xmxx'+index+']').val();
        }
        if(getClassNameUse.indexOf('jzq') > -1) {
            var zsjz = "";
            $("input:radio[name='zsjz']:checked").each(function () {
                zsjz = $(this).val();
            });
            qlData.zsjz = zsjz;
        }

        if (qlData !== {}) {
            //验证分幢面积之和是否等于建筑面积
            var bdcdyh =$("#bdcdyh").val();
            var zdzhtzm ="";
            if(isNotBlank(bdcdyh)){
              zdzhtzm =bdcdyh.substring(12,14);
            }
            var $fdcqxmJzmj =$("#bdc_fdcq1" + index).find("#fdcqxmdz-zjzmj");
            if($fdcqxmJzmj.length >0 &&(isNullOrEmpty(nosumfzmjZdtzm) ||nosumfzmjZdtzm.indexOf(zdzhtzm) <0)) {
                checkFdcqxmMj($fdcqxmJzmj,$("#fdcqxm").find(".fz-jzmj"));
            }
            savaFdcqxm(qlData.qlid);
            //保存共有部分
            saveFdcq3Gyxx(qlData.qlid);
            //修改土地性质为划拨时，权利的结束时间不可填写
            if ($('.jssj').length > 0) {
                checkJssjGz2($("#qlxz").val(), $('.jssj').val(), false)
            }
            //验证面专有建筑面积和分摊建筑面积之和是否等于建筑面积
            var $fdcq = $("#bdc_fdcq" + index);
            //如果用途为车库，且填写层高小于2.2 米，建筑面积为空
            var ghyt = qlData.ghyt;
            var cg = 0;
            if (isNotBlank(qlData.cg)) {
                cg = parseFloat(qlData.cg);
            }
            if ($fdcq.length > 0 && cg >= 2.2) {
                var jzmj = $fdcq.find("#fdcq-jzmj").val();
                var zyjzmj = $fdcq.find("#fdcq-zyjzmj").val();
                var ftjzmj = $fdcq.find("#fdcq-ftjzmj").val();
                checkMj(ftjzmj, zyjzmj, jzmj);
                // 验证定着物面积和
                var $bdcdyxx = $("#bdcdyxxForm" + index);
                if($bdcdyxx.length > 0){
                    var dzwmj = $("#bdcdyxxForm" + index).find("#dzwmj").val();
                    checkDzwmjAndJzmj(dzwmj, qlData.jzmj);
                }
            }
            if(getClassNameUse.indexOf('jzq') > -1) {
                var $zsjz = $("#zsjz");
                if ($zsjz.length > 0) {
                    //终生居住验证
                    checkJzqsj(qlData.zsjz, qlData.jzqkssj, qlData.jzqjssj);
                }
            }

            var fwlx = qlData.fwlx;
            if(isNotBlank(ghyt) && isNotBlank(fwlx) && qlData.cg) {
                changeJzmjByCg(cg, fwlx, ghyt);
                if (isChangeJzmj) {
                    qlData.jzmj = 0;
                    //循环同步其他TAB权利页数据
                    for (var i = 0; i < qlList.length; i++) {
                        if ((i + 1) !== index) {
                            $("#bdcdyxxForm" + (i + 1)).find("#dzwmj").val(0);
                            var $jzmj = $("#qllx" + (i + 1)).find("input[name=jzmj]");
                            if ($jzmj.length > 0 && $jzmj[0].id !== "fdcqxmdz-jzmj") {
                                $jzmj.val(0);
                            }
                        }
                    }
                }
            }
            // 查封同步查封机关至权利人
            if(getClassNameUse.indexOf("cf") > -1){
                syncCfjg(qlData);
            }
            getReturnData("/slym/ql?className=" + className, JSON.stringify(qlData), 'PATCH', function (data) {

            }, function (err) {
                throw err;
            }, false);
        }

    }
}

//权利人保存
function saveQlr() {
    if (lclx === "pllc") {
        var qlrArrayPllc = $(".qlr").serializeArray();
        saveAllQlr(qlrArrayPllc, "");
    } else {
        var index = $("input[name='liIndex']");
        if (index !== null && index.length > 0) {
            for (var i = 0; i < index.length; i++) {
                var c = $(index[i]).val();
                var qlrArray = $(".qlr" + c).serializeArray();
                saveAllQlr(qlrArray, c);
            }
        }
    }
    loadQlr();
}

function saveAllQlr(qlrArray, c) {
    var qlrList = [];
    var qlr = {};
    var qlrnum = 0;
    var ywrnum = 0;
    var gyfs = "";
    var ywrgyfs = "";
    var qllx = {};
    if ($('.qlxx').find("input[name=liIndex]").val() === c) {
        qllx = $('.qlxx').find("input[name=qllx]").val();
    }

    for (var j = 0; j < qlrArray.length; j++) {
        var name = qlrArray[j].name;
        qlr[name] = qlrArray[j].value;
        // 以xmid为每一组权利人的起始数据，作为分割依据
        if ((j + 1 < qlrArray.length && qlrArray[j + 1].name === 'xmid') || j + 1 == qlrArray.length) {
            if (qlr.qlrlb === "1") {
                qlrnum++;
                gyfs += qlr.gyfs + ",";
            }
            if (qlr.qlrlb == '2') {
                ywrnum++;
                ywrgyfs += qlr.gyfs + ",";
            }
            //将证件号中小写字母改为大写
            toUpperClass(qlr, ["zjh", "dlrzjh", "frzjh", "lzrzjh"]);
            qlrList.push(qlr);
            qlr = {};
        }
    }
    //当前项目ID
    var currxmid = xmid;
    var qlmc = "";
    var tabs = $(".qlxx");
    if (lclx === "zhlc") {
        currxmid = $(".xmxx" + c).find("input[name=xmid]").val();
        if (currxmid !== undefined) {
            qlmc = tabs[c - 1].innerText;
        } else {
            var id = $(tabs[c - 1]).find("input[name=xmid]").val();
            currxmid = id;
            qlmc = tabs[c - 1].innerText;
        }
    }
    if (lclx === "pllc") {
        qlmc = tabs[0].innerText;
    }
    //判断是否存在权利人(当页面的权利人数据设置disabled属性后获取不到值，改为从数据库获取)

    var result = checkQlrsl(processInsId, currxmid);
    if (!result) {
        throw err = new Error(qlmc + '无权利人，请新增！');
    }

    //验证权利人共有方式
    if (!checkGyfs(gyfs, qlrnum)) {
        throw err = new Error('权利人共有方式不正确，请检查！');

    }

    //验证义务人共有方式
    if (!checkGyfs(ywrgyfs, ywrnum)) {
        saveMsg = "义务人共有方式不正确，请检查!";

    }

    if (!checkAddGybl(qlrList)) {
        throw err = new Error('共有比例不正确，请检查！');
    }

    //当前受理页面的工作流定义id
    var currentGzldyid = getQueryString("processDefKey");

    //检查是否存在非18位的身份证；
    if (isInArray(sfzyzGzldyid, currentGzldyid)) {
        checkZjhNumber(qlrList);
    }

    var url = "";
    if (lclx === "zhlc") {
        url = "/slym/qlr/list/zhlc?processInsId=" + processInsId;
    }
    if (lclx === "pllc") {
        url = "/slym/qlr/list/pllc?processInsId=" + processInsId + "&xmid=" + xmid;
    }

    if (isNotBlank(qlrList)) {
        getReturnData(url, JSON.stringify(qlrList), 'PATCH', function (data) {
            // 保存完权利人后 需要检查是否需要同步权利人和领证人
            if (data > 0) {
                syncQlrToLzr(qlrList);
            }
        }, function (err) {
            throw err;
        }, false);
    }

}

//第三权利人保存
function saveDsQlr() {
    var dsqlrList = [];
    $(".dsqlr-tr").each(function () {
        var $dsqlr = $(this);
        var dsqlrArray = $dsqlr.find(".dsQlr").serializeArray();
        if (dsqlrArray !== null && dsqlrArray.length > 0) {
            var dsqlr = {};
            dsqlrArray.forEach(function (item, index) {
                dsqlr[item.name] = item.value;
            });
            dsqlr.xmid =$dsqlr.find("[name=xmid]").val();
            dsqlr.qlrid =$dsqlr.find("[name=qlrid]").val();
            dsqlrList.push(dsqlr);
        }
    });
    var url = "/slym/qlr/list/updateDsQlr?processInsId=" + processInsId;

    if (dsqlrList != null &&dsqlrList.length >0) {
        getReturnData(url, JSON.stringify(dsqlrList), 'PATCH', function (data) {
        }, function (err) {
            throw err;
        }, false);
    }

}

// 领证人保存
function saveLzr() {
    if (lclx === "pllc") {
        var lzrArray = $(".lzxx").serializeArray();
        url = "/slym/lzr/nt/lzrxx/pllc?gzlslid=" + processInsId + "&xmid=" + xmid;
        saveAllLzr(url, lzrArray, "");
    } else {
        var index = $("input[name='liIndex']");
        var url = "/slym/lzr/nt/lzrxx/jdlc?gzlslid=" + processInsId;
        if (index !== null && index.length > 0) {
            $('.layui-tab-item').each(function(i, ele){
                var lzrArray = $(ele).find(".lzxx").serializeArray();
                saveAllLzr(url, lzrArray, i);
            });
        }
    }
}

function saveAllLzr(url, lzrArray, index) {
    if(!isNotBlank(lzrArray)){
        return;
    }
    var lzr = {};
    for(var m = 0; m< lzrArray.length; m++) {
        var value  =  lzrArray[m].value;
        if(isNotBlank(value)){
            lzr[lzrArray[m].name] = value
        }
    }
    if(isNotBlank(lzr.lzrmc)) {
        var lzrList = [];
        lzrList.push(lzr);
        getReturnData(url, JSON.stringify(lzrList),"POST",function (data) {
            loadLzrxx($(".sqr"+index), index, lzr.xmid);
        },function (xhr) {
            delAjaxErrorMsg(xhr)
        },false)
    }
}

/**
 * 权利人，义务人发生改变后同步修改权利表权利人义务人信息
 * @param xmid
 * @param indexnum   渲染模板INDEX
 */
function reloadDyrAndCfjg(xmid, indexnum) {
    if (isNotBlank(xmid)) {
        var dyr = null;
        var gydqlr = null;
        var xydqlr = null;
        if (lclx === "zhlc") {
            if (indexnum === "") {
                var index = $(".layui-this").find("input[name='liIndex']");
                indexnum = $(index[0]).val();
            }
            dyr = $("#qllx" + indexnum).find("#dyaq-dyr");
            gydqlr = $("#qllx" + indexnum).find("#dyiq-gydqlr");
            xydqlr = $("#qllx" + indexnum).find("#dyiq-xydqlr");

        } else {
            xydqlr = $("#dyiq-xydqlr");
        }
        if ( (dyr != null &&dyr.length>0) || (xydqlr != null &&xydqlr.length>0)) {
            var bdcxm;
            getReturnData("/slym/xm/xx", {xmid: xmid}, 'GET', function (data) {
                bdcxm = data
            }, function (err) {
                console.log(err);
            }, false);
            //抵押人
            if (dyr != null) {
                $(dyr).val(bdcxm.ywr);
                dyr.title = bdcxm.ywr;
            }
            if (gydqlr != null) {
                //供役地权利人
                $(gydqlr).val(bdcxm.ywr);
                gydqlr.title = bdcxm.ywr;
            }
            if (xydqlr) {
                //需役地权利人
                $(xydqlr).val(bdcxm.qlr);
                xydqlr.title = bdcxm.qlr;
            }
        }
    }
}

/**
 * 同步查封机关，自动填充至权利人信息
 */
function syncCfjg(qldata){
    if(isNotBlank(qldata.cfjg)){
        var param = {
            jgmc : qldata.cfjg,
            xmid : qldata.xmid,
            processInsId: processInsId
        };
        getReturnData("/slym/qlr/sync/cfjgSqr", param, 'GET', function (data) {
        }, function (err) {
            console.info(err);
        },false);
    }
}

/**
 * 同步解封机关，自动填充至权利人信息
 */
function syncJfjg(qldata){
    if(isNotBlank(qldata.jfjg)){
        var param = {
            jgmc : qldata.jfjg,
            xmid : qldata.xmid,
            processInsId: processInsId
        };
        getReturnData("/slym/qlr/sync/jfjgSqr", param, 'GET', function (data) {
        }, function (err) {
            console.info(err);
        },false);
    }
}

//项目内多幢信息保存
function savaFdcqxm(qlid) {
    var fdcqxmArray = $(".fdcqxmdz").serializeArray();
    if (fdcqxmArray !== null && fdcqxmArray.length > 0) {
        var fdcqxmList = [];
        var fdcqxm = {};
        var bdcXmData = {};
        var bdcXmArray = $(".bdcxm");
        bdcXmArray.serializeArray().forEach(function (item) {
            bdcXmData[item.name] = item.value;
        });
        if (fdcqxmArray !== []) {
            for (var i = 0; i < fdcqxmArray.length; i++) {
                var name = fdcqxmArray[i].name;
                fdcqxm[name] = fdcqxmArray[i].value;
                //保存时同步保存
                if(hasProperty(bdcXmData, "dzwyt")) {
                    fdcqxm.ghyt = bdcXmData.dzwyt;
                }
                // 以fzid为每一组收件材料的起始数据，作为分割依据
                if ((i + 1 < fdcqxmArray.length && fdcqxmArray[i + 1].name === 'fzid') || i + 1 == fdcqxmArray.length) {
                    fdcqxmList.push(fdcqxm);
                    fdcqxm = {};
                }
            }
        }
        //需求52820，JC类型分幢面积之和不同步到建筑面积
        var nosumfzmj =false;
        var bdcdyh =$("#bdcdyh").val();
        if(isNotBlank(bdcdyh)){
            var zdzhtzm =bdcdyh.substring(12,14);
            if(!isNullOrEmpty(nosumfzmjZdtzm) &&nosumfzmjZdtzm.indexOf(zdzhtzm) >-1){
                nosumfzmj =true;
            }
        }
        if (fdcqxmList !== []) {
            getReturnData("/slym/ql/fdcqxm/list?nosumfzmj="+nosumfzmj, JSON.stringify(fdcqxmList), 'PATCH', function (data) {
                if (data > 0) {
                    generateFdcqxm(qlid)
                }
            }, function (err) {
                delAjaxErrorMsg(err)
            }, false);
        }
    }
}

/**
 * 保存共有部分信息内容
 */
function saveFdcq3Gyxx(qlid) {
    var gyxxArray = $(".gyxx").serializeArray();
    if (gyxxArray !== null && gyxxArray.length > 0) {
        var gyxxList = [];
        var gyxx = {};
        if (gyxxArray !== []) {
            for (var i = 0; i < gyxxArray.length; i++) {
                var name = gyxxArray[i].name;
                gyxx[name] = gyxxArray[i].value;
                // 以gyxxid为每一组权利人的起始数据，作为分割依据
                if ((i + 1 < gyxxArray.length && gyxxArray[i + 1].name === 'gyxxid') || i + 1 == gyxxArray.length) {
                    gyxxList.push(gyxx);
                    gyxx = {};
                }
            }
        }
        if (gyxxList !== []) {
            getReturnData("/slym/ql/fdcq3/gyxx", JSON.stringify(gyxxList), 'PATCH', function (data) {
                if (data > 0) {
                    getFdcq3Gyxx(qlid);
                }
            }, function (err) {
                delAjaxErrorMsg(err)
            }, false);
        }
    }

}

//删除权利人
function deleteQlr(qlrid, sxh, qlrlb) {
    var xmid = "";
    var url = "";
    if (lclx == "zhlc") {
        //获取当前权利人对应的项目ID
        xmid = $($(".layui-this").find("input[name='xmid']")[0]).val();
        url = "/slym/qlr/zhlc?xmid=" + xmid + "&qlrid=" + qlrid + "&sxh=" + sxh + "&qlrlb=" + qlrlb + "&processInsId=" + processInsId;
    }
    if (lclx == "pllc") {
        url = "/slym/qlr/pllc?processInsId=" + processInsId + "&qlrid=" + qlrid + "&sxh=" + sxh;
    }
    var deleteIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认删除',
        area: ['320px'],
        content: '是否确认删除？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            addModel();
            //确定操作
            getReturnData(url, {}, 'DELETE', function (data) {
                removeModal();
                loadQlr();
                var qllx = $(".qlxx.layui-this").find("input[name='qllx']").val();
                if (isNotBlank(qllx) && ['4', '6', '8'].indexOf(qllx) > -1) {
                    refreshQlxx();
                }
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
                //    删除权利人后删除权利人对应的义务人
                deleteLzr(qlrid, '', '');
                if (qllx === commonDyaq_qllx) {
                    //抵押权删除对应的第三权利人
                    var xmid = $($(".layui-this").find("input[name='xmid']")[0]).val();
                    var delurl = "/slym/qlr/deleteDsQlr?xmid=" + xmid + "&qlrid=" + qlrid + "&sxh=" + sxh + "&qlrlb=" + qlrlb + "&processInsId=" + processInsId;
                    getReturnData(delurl, {}, 'DELETE', function (data) {
                        refreshQlxx();
                    }, function (err) {
                        delAjaxErrorMsg(err)
                    });
                }
            }, function (err) {
                delAjaxErrorMsg(err)
            });
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
}

//删除义务人列表
function deleteYwr(qlridlist, sxh, xmid, qlrid, otherXmidList, ywrmc, ywrzjh) {
    var xmidList;
    if (isNotBlank(otherXmidList)) {
        xmidList = otherXmidList + ',' + xmid;
    } else {
        xmidList = xmid;
    }
    var url = "";
    var qlrList = [];
    if (isNotBlank(qlridlist)) {
        qlridlist += ',' + qlrid;
    } else {
        qlridlist = qlrid;
    }
    console.log(qlridlist);
    qlrList = qlridlist.split(",");
    var getXmid = xmidList.split(",");
    console.log(qlrList);
    var qlrData = {};
    qlrData.xmids = getXmid;
    qlrData.qlrids = qlrList;
    url = "/slym/qlr/deletegroupywr?gzlslid=" + processInsId + "&sxh=" + sxh + "&xmid=" + xmid;

    var deleteIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认删除',
        area: ['320px'],
        content: '是否确认删除？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            addModel();
            //确定操作
            getReturnData(url, JSON.stringify(qlrData), 'DELETE', function (data) {
                removeModal();
                loadQlr();
                var qllx = $(".qlxx.layui-this").find("input[name='qllx']").val();
                if (qllx === commonDyaq_qllx) {
                    //抵押权删除对应的第三权利人
                    var xmid = $($(".layui-this").find("input[name='xmid']")[0]).val();
                    var delurl = "/slym/qlr/deleteDsQlr/dyaq?dsqlrmc=" + ywrmc + "&dsqlrzjh=" + ywrzjh + "&qlrlb=4" + "&processInsId=" + processInsId;
                    getReturnData(delurl, {}, 'DELETE', function (data) {
                        generateDsQlr(xmid, $('.sqr'), "");
                    }, function (err) {
                        delAjaxErrorMsg(err)
                    });
                }
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
            }, function (err) {
                delAjaxErrorMsg(err)
            });
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
}

function deleteLzr(qlrid, lzrid, djxl) {
    var url = "/slym/lzr/yc/zhlc?lzrid=" + lzrid + "&gzlslid=" + processInsId;
    if (lclx === "pllc") {
        url = "/slym/lzr/yc/pllc?lzrid=" + lzrid + "&qlrid=" + qlrid + "&djxl=" + djxl + "&gzlslid=" + processInsId;
    }
    getReturnData(url, '', 'DELETE', function (data) {
        console.log("删除领证人成功");
        refreshLzrxx();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//关闭panel
function cancelEdit() {
    layer.closeAll();
}

// 证书预览
function createZs(xmid, bdcdyh) {
    if (!isNotBlank(xmid)) {
        xmid = "";
    }
    if (!isNotBlank(bdcdyh)) {
        bdcdyh = "";
    }
    var url = "/realestate-register-ui/rest/v1.0/bdcZs?processInsId=" + processInsId + "&xmid=" + xmid + "&bdcdyh=" + bdcdyh + "&zsmodel=" + zsmodel + "&zsyl=true&time=" + new Date().getTime();

    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: "证书预览",
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true
    });
    layer.full(index);
}

//权利人顺序号修改
function changeQlrSxh(qlrid, czlx) {
    getReturnData("/slym/qlr/sxh", {
        qlrid: qlrid,
        czlx: czlx,
        lclx: lclx,
        processInsId: processInsId
    }, 'GET', function (data) {
        if (data > 0) {
            loadQlr();
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

//更新房屋附属设施
function updateFwfsss() {
    var fwfsssList = [];
    var fwfsss = {};
    var fwfsssFormArray = $(".fwfsss").serializeArray();
    if (fwfsssFormArray.length > 0) {
        for (var i = 0; i < fwfsssFormArray.length; i++) {
            var dataName = fwfsssFormArray[i].name;
            fwfsss[dataName] = fwfsssFormArray[i].value;
            if ((i + 1) % 5 === 0) {
                fwfsssList.push(fwfsss);
                fwfsss = {};
            }
        }
        getReturnData("/slym/fwfsss/list", JSON.stringify(fwfsssList), 'PATCH', function (data) {
            removeModal();
            if (data > 0) {
                ityzl_SHOW_SUCCESS_LAYER("保存成功");
            }
        }, function (err) {
            throw err;
        }, false);
    }

}

//同步权籍数据
function synchLpbDataToLc() {
    getReturnData("/rest/v1.0/slym/synchLpbDataToLc?processInsId=" + processInsId, '', 'PATCH', function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("同步数据成功");
        if (lclx === "zhlc") {
            loadJbxx();
            loadSjcl();
            refreshQlxx();
        } else if (lclx === "pllc") {
            $.when(loadBdcdyh()).done(function () {
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        getStateById(readOnly, formStateId, "slympl");
                        clearInterval(a);
                    }
                }, 50);
            });
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
}


//---------------------从cz.js合并的通用的---------------------------------------
function titleShowUi() {

    //打印点击
    $(".print-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        $("#print").show();
        setUiWidth($(this), $("#print"));
    });

    //获取交易信息点击
    $(".jyxx-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        $("#jyxx").show();
        setUiWidth($(this), $("#jyxx"));
    });

    //更多点击
    $(".more-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#jyxx").hide();
        $("#dzzzxx").hide();
        $("#moreBtn").show();
        setUiWidth($(this), $("#moreBtn"));
    });

    //电子证照点击
    $(".dzzz-button").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").show();
        setUiWidth($(this), $("#dzzzxx"));
    });


    $(".print-btn li").click(function () {
        $("#print").hide();
    });

    $(".jyxx-btn li").click(function () {
        $("#jyxx").hide();
    });

    $(".more-btn li").click(function () {
        $("#moreBtn").hide();
    });
    $(".dzzz-button li").click(function () {
        $("#dzzzxx").hide();
    });

    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && (elem.id == 'dzzzxx' || elem.id == 'print' || elem.id == 'jyxx' || elem.id == 'moreBtn')) {
                return;
            }
            $("#dzzzxx").hide();
            $("#print").hide();
            $("#jyxx").hide();
            $("#moreBtn").hide();
            elem = elem.parentNode;
        }
    });
}

function setUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    var w = buttonElement.innerWidth();
    var scrollH = $(document).scrollTop();
    if(scrollH > 0){
        uiElement.css({top: X + 42 - scrollH, right: $('body').width() - Y - w - 15});
    }else {
        uiElement.css({top: X + 32, right: $('body').width() - Y - w - 15});
    }
    var uiWidth = 0;
    var liArray = uiElement.find("li");
    if (liArray.length > 0) {
        var textNumber = 0;
        for (var i = 0; i < liArray.length; i++) {
            var liEl = liArray[i];
            var liText = $(liEl).text();
            if (textNumber < liText.length) {
                textNumber = liText.length;
            }
        }
        var countWidth = (textNumber+3) * 13;
        if (uiWidth < countWidth) {
            uiWidth = countWidth;
        }
    }
    uiElement.css({"min-width":w,"width": uiWidth + 20});
}

function changeQlrlbMc() {
    var qlrlbzd = zdList.qlrlb;
    for (var j = 0; j < qlrlbzd.length; j++) {
        if (qlrlbzd[j].DM === 1) {
            qlrlbzd[j].MC = "权利人(申请人)";
        }
    }
    zdList.qlrlb = qlrlbzd;
}

/**
 * 插入修改日志
 */
function insertXgLog() {
    var tsxx = $("#updateTips p").text();
    if (isNotBlank(tsxx)) {
        var bdcXxXgDTO = {};
        bdcXxXgDTO.tsxx = tsxx;
        var bdcXxXgZbDTOList = [];
        $(".bdc-change-input").each(function (i) {
            var bdcXxXgZbDTO = {};
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
            var value = $change.val();
            var name = $change.attr('name');
            bdcXxXgZbDTO.value = value;
            bdcXxXgZbDTO.name = name;
            bdcXxXgZbDTOList.push(bdcXxXgZbDTO);
        });
        bdcXxXgDTO.bdcXxXgZbDTOList = bdcXxXgZbDTOList;
        getReturnData("/rest/v1.0/slym/addXgLog?gzlslid=" + processInsId, JSON.stringify(bdcXxXgDTO), "POST", function () {

        }, function (error) {
            delAjaxErrorMsg(error);
        })
    }
}

/**
 * 查询修改日志
 */
function queryXgLog() {
    getReturnData("/rest/v1.0/slym/queryXgLog", {gzlslid: processInsId}, "GET", function (data) {
        if (data && data.value) {
            var modifyData = JSON.parse(data.value).bdcXxXgZbDTOList;
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
            renderChangeTips(JSON.parse(data.value).tsxx);
        }
    }, function (error) {

    })
}

/*
* 业务数据对照同步
*/
function ywxxDozer() {
    //组合流程，如果第2个权利的qlsjly来源为第一个权利，需要把第一个权利数据对照到第二个权利中
    if (lclx === "zhlc") {
        var index = $("input[name='liIndex']");
        if (index !== null && index.length === 2) {
            var list = [];
            for (var i = 0; i < index.length; i++) {
                var c = $(index[i]).val();
                //组织数据进行对照
                var $info = $("#qllx" + c).find(".basic-info");
                if ($info.length > 1) {
                    var qlxxId = $("#qllx" + c).find(".basic-info")[1].id;
                    if (isNotBlank(qlxxId)) {
                        var qlindex = qlxxId.lastIndexOf("\_");
                        var qlTableId = qlxxId.substring(qlindex + 1, qlxxId.length);
                        var qlArray = $("." + qlTableId).serializeArray();
                        if (qlArray.length > 0) {
                            //获取权利数据
                            var qlData = {};
                            qlArray.forEach(function (item) {
                                qlData[item.name] = item.value;
                            });
                            list.push(qlData);
                        }
                    }
                }
            }
            if (list.length === 2) {
                getReturnData("/rest/v1.0/slym/ywxxDozer?processInsId=" + processInsId, JSON.stringify(list), "POST", function (data) {
                    if (data) {
                        //特殊处理，并案流程房屋性质不继承
                        delete data.fwxz;
                        //特殊处理,交易价格不继承
                        delete data.jyjg;
                        //循环处理,判断对照后字段是否在对照前对象中，不存在则不赋值（解决不可编辑字段对照后赋值为空问题）
                        var ydata =list[0];
                        for (var key in data) {
                            if (!(key in ydata)) {
                                delete data[key];
                            }
                        }
                        //表单赋值
                        form.val("qlForm2", data);
                        renderDate();
                        resetSelectDisabledCss();
                    }
                }, function () {

                }, false);
            }
        }


    }


}

/**
 * @param
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 登记原因改变重新创建收件材料
 * @date : 2019/12/12 16:35
 */

function reCreateSjcl(){
    getReturnData("/slym/sjcl/recreate",{gzlslid:processInsId},"GET",function (data) {
        if(data !== null && data !== '' && data !== undefined) {
            loadSjcl();
        }
    },function (xhr) {
        delAjaxErrorMsg(xhr,"重新生成收件材料失败");
    })
}

/**
 * 推送省直房改办
 */
function tsSzfgb(){
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/fgf/tsszfgb/"+processInsId,
        type: 'GET',
        dataType: 'text',
        success: function (data) {
            if(data != null && parseInt(data) > 0){
                ityzl_SHOW_SUCCESS_LAYER("推送成功！");
            }else{
                ityzl_SHOW_WARN_LAYER("推送失败，请重试！")
            }
            removeModal();
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e,"推送失败！");
        }
    });
}

function downloadDzgx() {
    addModel();
    getReturnData('/dzzz/dzgxxz','','GET',function (data) {
        if(data && data>0) {
            ityzl_SHOW_SUCCESS_LAYER('获取成功，需配置对照关系');
        } else {
            ityzl_SHOW_INFO_LAYER('无需重复获取');
        }
        removeModal();
    },function (xhr) {
        delAjaxErrorMsg(xhr);
    })

}

//点击电子证照按钮前先判断是否有对照关系数据
function useDzzz() {
    addModel();
    var count = dzgxsfcz();
    if(count > 0) {
        var width = $(window).width() + "px";
        var height = $(window).height() + "px";
        var index = layer.open({
            title: "电子证照信息",
            type: 2,
            content: getContextPath() + '/view/dzzz/dzzzxx.html?gzlslid=' + processInsId,
            area: [width, height],
            maxmin: true,
            end: function () {
                addModel();
                $.ajax({
                    url: getContextPath() + "/slym/sjcl/ys",
                    type: 'PATCH',
                    dataType: 'json',
                    data: {gzlslid: processInsId},
                    success: function (data) {
                        if (data > 0) {
                            loadSjcl();
                        }
                        removeModal();
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
            }
        });
        layer.full(index);
    }
    removeModal();
}

function dzgxsfcz() {
    //点击按钮先判断收件材料电子证照对照表是否有数据
    var count=0;
    getReturnData('/dzzz/dzgx','','GET',function (data) {
        if(data === 0) {
            ityzl_SHOW_INFO_LAYER('对照表没有数据，请先获取对照关系并配置')
        }
        count = data;
    },function (xhr) {

    },false);
    return count;
}
// 修改收费状态
function tswwjf(){
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/modifySfzt",
        type: 'GET',
        dataType: 'json',
        data: {
            gzlslid : processInsId
        },
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("修改成功！");
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

//获取交易信息
function queryFcjyClfHtxx(fwlx,currxmid) {
    //小弹出层
    var htbhIndex = layer.open({
        title: '获取房产交易合同信息',
        type: 1,
        skin: 'bdc-spf-layer',
        area: ['430px'],
        btn: ['提交', '取消'],
        content: $('#bdc-popup-small')
        , yes: function (index, layero) {
            //提交 的回调
            var contractNo = $('#contractNo').val();
            $('#contractNo').val('');
            layer.confirm("确认导入合同号为"+contractNo+"的信息吗？", {title:'提示'}, function(index){
                addModel();
                generateYcslxx(contractNo, htbhIndex, fwlx, currxmid);
                layer.close(index);
            });
        }
        , btn2: function (index, layero) {
            //取消 的回调
        }
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        , success: function () {
            //清空已有值
            $('#contractNo').val('');
            //自动获取焦点
            $('#contractNo').focus();
        }
    });
}



function generateYcslxx(contractNo, index,fwlx,currxmid) {
    if(!isNotBlank(currxmid)){
        currxmid =xmid;
    }
    layer.close(index);
    $.ajax({
        url: getContextPath() + "/ycsl/fcjyxx",
        type: 'GET',
        data: {htbh: contractNo, xmid: currxmid, fwlx: fwlx, lclx : lclx},
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                //重新组织页面数据
                if(lclx =="zhlc"){
                    refreshQlxx();
                }else if(lclx == "pllc"){ // 批量流程刷新不动产单元信息
                    loadBdcdyh();
                }else{
                    loadBdcdyh($(".layui-show").data('djxl'), $(".layui-show").data('qllx'), $(".layui-show").data('xmid'));
                }
                loadQlr();
                setTimeout(function(){
                    ityzl_SHOW_SUCCESS_LAYER("查询成功");
                    removeModal();
                }, 150);
            } else {
                ityzl_SHOW_INFO_LAYER("未获取到合同信息");
                removeModal();
                layer.close(index);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            layer.close(index);
            delAjaxErrorMsg(xhr);
        }
    });
}

// 展示人证对比
function showRzdb(){
    var slbh = $("#sjbh").val();
    getReturnData('/url/rzdbUrl','','GET',function (data) {
       if(isNotBlank(data)){
            window.open(data + slbh);
       }else{
           ityzl_SHOW_WARN_LAYER("未获取到配置的人证对比页面地址。");
       }
    },function (xhr) {
    });
}
// 获取比对结果
function hqdbjg(){
    var slbh = $("#sjbh").val();
    addModel();
    getReturnData('/slPrint/rzdb',{gzlslid:processInsId, slbh: slbh},'GET',function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("生成比对文件成功。");
    },function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}

//加载第三权利人信息
function generateDsQlr(xmid,$sqr,index){
    getReturnData("/slym/qlr/list/dsQlr", {xmid: xmid}, 'GET', function (data) {
        //渲染模板数据
        var json = {
            index: index,
            xmid: xmid,
            bdcDsQlrDOList: data,
            zd: zdList
        };

        var dsQlrxxTpl = dsQlrTpl.innerHTML;

        if(isNotBlank($sqr)){
            //渲染数据
            if($sqr.parents(".layui-tab-item").find(".dsqlr-basic").length >0){
                $sqr.parents(".layui-tab-item").find(".dsqlr-basic").remove();
            }
            laytpl(dsQlrxxTpl).render(json, function (html) {
                var $lzrTpl = $('.lzr-basic' + index);
                if ($lzrTpl.length > 0) {
                    $lzrTpl.after(html);
                } else {
                    $sqr.after(html);
                }
                form.render();
            });
        }

    }, function (err) {
        console.log(err);
    },false);

}

//删除第三权利人
function deleteDsQlr(qlrid, sxh, qlrlb,elem) {
    //获取当前权利人对应的项目ID
    var xmid = $($(".layui-this").find("input[name='xmid']")[0]).val();
    var url = "/slym/qlr/deleteDsQlr?xmid=" + xmid + "&qlrid=" + qlrid + "&sxh=" + sxh + "&qlrlb=" + qlrlb + "&processInsId=" + processInsId;

    var deleteIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认删除',
        area: ['320px'],
        content: '是否确认删除？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            addModel();
            //确定操作
            getReturnData(url, {}, 'DELETE', function (data) {
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
                $(elem).parents(".dsqlr-tr").remove();
            }, function (err) {
                delAjaxErrorMsg(err)
            });
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
}
//第三方权利人身份核验
function dsQlrhy(gzlslid,xmid) {
    //加载页面上的第三方权利人
    var getData ={
        "gzlslid":processInsId,
        "cxywcs":[]
    };
    var qlrmc = [];
    var qlrzjh = [];
    $("[id=dsqlr-qlrmc]").each(function() {
        qlrmc.push($(this).val());
    });
    $("[id=dsqlr-zjh]").each(function() {
        qlrzjh.push($(this).val());
    });
    for(var i=0; i<qlrmc.length;i++){
        if (qlrmc[i] && qlrzjh[i]){
            getData.cxywcs.push({
                "zjh":qlrzjh[i],
                "xm": qlrmc[i]
            });
        }
    }
    addModel();
    // 核验请求
    $.ajax({
        url:"/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/gasfhc",
        type: "POST",
        data: JSON.stringify(getData),
        contentType: 'application/json',
        success: function (data) {
            removeModal();
            // 结果数据不为空
            if(data.data && data.data!=null && data.data.length>0){
                $("[id=dsqlr-qlrmc]").each(function() {
                    for(var i=0; i<data.data.length; i++){
                        var item = data.data[i];
                        if(item.xm ==  $(this).val()){
                            if(item.CheckCode !="000"){
                                $(this).parent().parent().parent().find("[id=hyjg]").html('<span style="color: #f24b43; background-color: #fff2f1">('+ item.CheckCodeDesc +')</span>');
                            }else {
                                $(this).parent().parent().parent().find("[id=hyjg]").html('<span>(核查一致)</span>');
                            }
                        }
                    }
                });
              //渲染页面
            }else{
                removeModal();
               //没有返回值检测失败
                showAlertDialog("身份核验失败，请稍后重试");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            showAlertDialog("身份核验失败，请稍后重试");
        }
    });
}

//权利人详细
function showDsQlr(qlrid,xmid) {
    var url = getContextPath() + "/view/slym/dsQlr.html?xmid=" + xmid + "&processInsId=" + processInsId + "&formStateId=" + formStateId;
    layer.open({
        type: 2,
        area: ['960px', '360px'],
        fixed: false, //不固定
        title: "新增第三方权利人",
        content: url,
        btnAlign: "c"
    });
    form.render();
    resetSelectDisabledCss();
}

/**
 * 同步权利人和领证人
 * @param qlrList
 */
function syncQlrToLzr(qlrList){
    var lzrArray = $(".lzxx").serializeArray();
    var url = "/slym/qlr/checkSyncQlrLzr?processInsId=" + processInsId;
    if(lzrArray && lzrArray.length > 0){
        getReturnData(url, JSON.stringify(qlrList), 'POST', function (data) {
            // 保存完权利人后 需要检查是否需要同步权利人和领证人
            if (data) {
                window.setTimeout(function(){
                    layer.confirm('是否需要同步权利人到领证人？', {
                        btn: ['是', '否']
                    }, function(index, layero){
                        startSyncQlrToLzr(qlrList);
                        layer.close(index);
                    }, function(index){
                        layer.close(index);
                    });
                },1000)
            }
        }, function (err) {
            throw err;
        }, false);
    }
}

function startSyncQlrToLzr(qlrList) {
    var url = "/slym/qlr/syncQlrLzr?processInsId=" + processInsId;
    getReturnData(url, JSON.stringify(qlrList), 'PATCH', function (data) {
        refreshLzrxx();
    }, function (err) {
        throw err;
    }, false);
}

function syncSaveSfxx() {
    var url = getContextPath() + "/sf/xm/all";
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        data: {gzlslid: processInsId},
        success: function (data) {
            console.log("保存收费信息成功")
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function showTsxxMsg(msg){
    var ignoreCommonBtn = '<a href=\"javascrit:;\" onclick=\"removeIgnore();return false;\">忽略</a>';
    $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">'+ msg + ignoreCommonBtn  +'</p>');
    warnLayer = layer.open({
        type: 1,
        title: '提示信息',
        skin: 'bdc-tips-right',
        shade: [0],
        area: ['600px'],
        offset: 'r', //右下角弹出
        time: 5000000, //2秒后自动关闭
        anim: 2,
        content: $('#tsxx').html(),
        success: function () {
            $("#confirmInfo").html("");
        },
        cancel: function () {
            layer.close(warnLayer);
            $("#confirmInfo").html("");
        }
    });
}

function removeIgnore(){
    $("#confirmInfo").html("");
    layer.close(warnLayer);
}

/**
 * 获取配置的标识
 * @returns {Array}
 */
function getSfzyzGzldyid(){
    $.ajax({
        url: getContextPath() + "/rest/v1.0/slym/querySfzyzGzldyid",
        type: 'GET',
        async: true,
        success: function (data) {
            sfzyzGzldyid = data;
        }, error: function (xhr, status, error) {
            console.log("获取配置的sfzyzGzldyid标识失败");
        }
    });
}

/**
 * 使用jquery的inArray方法判断元素是否存在于数组中
 * @param {Object} arr 数组
 * @param {Object} value 元素值
 */
function isInArray(arr,value){
    var index = $.inArray(value,arr);
    if(index >= 0){
        return true;
    }
    return false;
}

/**
 * 配置的房屋类型和房屋用途判断层高不足2.2米的不保存建筑面积
 */
function changeJzmjByCg(cg, fwlx, fwyt) {

    $.ajax({
        type: "POST",
        url: getContextPath() +  '/slym/ql/changeJzmjByCg',
        data: {cg: cg, fwlx: fwlx, fwyt: fwyt},
        async: false,
        datatype: "json",
        success: function (data) {

            isChangeJzmj = data;

        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}