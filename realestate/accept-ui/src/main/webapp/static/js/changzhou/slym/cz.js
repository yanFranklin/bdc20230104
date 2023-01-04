var layer;
//共有方式有问题的，保存结束提示信息
var saveMsg = '';
var ydjyy = '';
var ydjyyArr = {};
var sfchange = false;
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

                        $(item).addClass('layui-form-danger');
                        btZdItem.push($(item).attr("id"));
                        if(sfyzbt){
                            isSubmit = false;
                        }
                    }
                }
            }
            ,qlrmcrequired: function (value, item) {
                //权利人名称必填
                if (value == '') {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = "权利人名称不能为空";
                }
            }
            , identitynew: function (value, item) {
                var msg = checkSfzZjh(value);
                if (isNotBlank(msg)) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = msg;
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
        });
        //组合流程提交前执行同步对照
        $(".bdc-form-div").on('click', '#saveData', function () {
            ywxxDozer();
        });
        form.on("submit(saveData)", function (data) {
            // 规则验证抵押金额是否超过1亿
            var els =document.getElementsByName("bdbzzqse");
            var maxbdbzzqse = 0;
            for (var i = 0, j = els.length; i <j; i++){
                if(els[i].value > maxbdbzzqse){
                    maxbdbzzqse = els[i].value;
                }
            }
            AcceptForm.yzDyje(maxbdbzzqse, doSubmit);
            return false;
        });
        function doSubmit(){
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                isFirst = true;
                return false;
            } else {
                addModel();
                setTimeout(function () {
                    try {
                        $.when(saveXmxx(), saveQlxx(), saveSjcl(),saveDsQlr(), saveZrzcfxx(), updateFwfsss(), insertXgLog(), saveYjxx(),saveZdjbxx()).done(function () {
                            hxYwsj(processInsId);
                            if (lclx === "zhlc") {
                                refreshQlxx();
                            } else if (lclx === "pllc") {
                                loadPlQlxx();
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
                        });
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
        }
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
        //同步单元信息的时候会存在dzwmj不可编辑导致获取不到值的情况，这边单独取值保存
        var dzwmj = $("#bdcdyxxForm" + index).find("#dzwmj").val();
        if (dzwmj !== null && dzwmj !== undefined) {
            bdcXmData.dzwmj = dzwmj;
        }
        var dzwyt = $("#bdcdyxxForm" + index).find("#dzwyt").val();
        if (dzwyt !== null && dzwyt !== undefined) {
            bdcXmData.dzwyt = dzwyt;
        }
        var djyy = $("#djyy" + index).val();
        if (djyy !== ydjyyArr['djyy' + index]) {
            sfchange = true;
            //防止页面未刷新多次保存
            ydjyyArr['djyy' + index] = djyy;
        } else {
            sfchange = false;
        }
        var jyhth = $("#qllx" + index).find("input[name=jyhth]");
        if (jyhth !== null && jyhth !== undefined) {
            bdcXmData.jyhth = $.trim($("#qllx" + index).find("input[name=jyhth]").val());
        }
        bdcXmData.djyy = djyy;
        var sqfbcz = $($("#xmForm" + index).find("input[type='radio']:checked")).val();
        if (isNotBlank(sqfbcz)) {
            bdcXmData.sqfbcz = sqfbcz;
        }
        getReturnData("/slym/xm", JSON.stringify(bdcXmData), 'PATCH', function (data) {
            //保存项目后判断登记原因是否改变过，改变则重新创建收件材料
            if (sfchange && lclx === "zhlc") {
                reCreateSjcl();
            }
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
        var xmid =$(formClass).parents(".layui-tab-item").find("input[name=xmid]").val();
        var jyxxid = $("#xmForm" + index).find("input[name=jyxxid]").val();
        var yhtbh = $.trim($("#xmForm" + index).find("input[name=yhtbh]").val());
        if (isNotBlank(xmid)) {
            jyxxData["xmid"] = xmid;
            jyxxData["jyxxid"] = jyxxid;
            // 2020-3-11 新增用于在登记页面填写了交易合同号后，保存时同步合同编号至受理库中
            if($("#qllx" + index).find("input[name=jyhth]").length != 0){
                jyxxData["htbh"] = $.trim($("#qllx" + index).find("input[name=jyhth]").val());
            }
            getReturnData("/ycsl/htxx", JSON.stringify(jyxxData), 'PATCH', function (data) {
                if (data) {
                    $("#xmForm" + index).find("input[name=jyxxid]").val(data.jyxxid);
                    if(isNotBlank(jyxxData.htbh) &&jyxxData.htbh !=yhtbh) {
                        importJyxx(jyxxData.htbh, xmid);
                    }
                }
            }, function (err) {
                delAjaxErrorMsg(err);
            }, false);
        }
    }

}

//房地产权水电气等过户信息(组合页面)
function saveGhxx(formClass, index) {
    var ghxxData = {};
    var ghxxArray = $(formClass);
    if (ghxxArray !== null && ghxxArray.length > 0) {
        ghxxData["gzlslid"] = processInsId;
        ghxxData["slbh"] = $("#sjbh").val();
        ghxxData["sqsj"] = $("#slsj").val();
        var ghxxDataList = [];

        var map = {"sfblsyw": 1, "sfbldyw": 2, "sfblqyw": 3, "sfblgdyw": 4, "sfblwlyw": 5};
        ghxxArray.serializeArray().forEach(function (item, index) {
            var sfbl = "0";
            if(isNotBlank(item.value)){
                sfbl = item.value;
            }
            ghxxData["ywlx"] = map[item.name];
            ghxxData["sfbl"] = sfbl;
            ghxxDataList.push($.extend({},ghxxData));
        });

        getReturnData("/slym/sdq/sdqxx", JSON.stringify(ghxxDataList), 'POST', function (data) {

        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
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
            jyxxData["htbh"] = $.trim($("input[name=jyhth]").val());
        }
        getReturnData("/ycsl/jyxx/list?processInsId=" + processInsId, JSON.stringify(jyxxData), 'PATCH', function (data) {
            // loadJbxx();

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
        if($("#djyy").length >0) {
            var djyy = $("#djyy").val();
            bdcXmData.djyy = djyy;
            if (djyy !== ydjyy) {
                sfchange = true;
                //防止页面未刷新多次保存
                ydjyy = djyy;
            } else {
                sfchange = false;
            }
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
    saveXmfbPl(".xmfb",'');
    if (lclx === "zhlc") {
        for(var i = 0; i< qlList.length; i++){
            if (qlList[i].qllx === 92){
                var id = qlList[i].bdcXm.xmid;
                saveSlfs(id);
                break;
            }
        }
        var index = $("input[name='liIndex']");
        if (index !== null && index.length > 0) {
            for (var i = 0; i < index.length; i++) {
                var c = $(index[i]).val();
                saveXmfb(".xmfb" + c, c);
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
                saveBdcXm(".xmxx" + c, c);
                //保存交易信息，存在即保存
                if ($(".jyxx" + c).length > 0) {
                    saveJyxx(".jyxx" + c, c);
                }
                if($('#ghxxForm'+c).length > 0){
                    saveGhxx('.ghxx' + c, c)
                }
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
        if (qllx == 92){
            var slfs = $("#slfs").val();
            qlData['slfs'] = slfs;
        }
        if (!$.isEmptyObject(qlData) && isNotBlank(xmid)) {
            // 查封同步查封机关至权利人
            if (qllx == 98) {
                syncCfjg(qlData)
            }
            getReturnData("/slym/ql/list?processInsId=" + processInsId + "&onexmid=" + xmid + "&zxlc=" + zxlc, JSON.stringify(qlData), 'PATCH', function (data) {

            }, function (err) {
                delAjaxErrorMsg(err);
            });

        }

        //保存交易信息
        saveJyxxPl();
        saveGhxx('.ghxx',"");
    }
}

function saveXmfb(formClass,index) {
    var bdcXmfbData = {};
    var bdcXmfbArray = $(formClass);
    if (bdcXmfbArray !== null && bdcXmfbArray.length > 0) {
        bdcXmfbArray.serializeArray().forEach(function (item, index) {
            bdcXmfbData[item.name] = item.value;
        });
        if(Object.keys(bdcXmfbData).length >1 &&isNotBlank(bdcXmfbData.xmid)) {
            getReturnData("/slym/xm/fb", JSON.stringify(bdcXmfbData), 'PATCH', function (data) {
                console.log("保存项目附表成功")
            }, function (err) {
                delAjaxErrorMsg(err);
            }, false);
        }
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
        }
        if (qlData !== {}) {
            savaFdcqxm(qlData.qlid);
            //保存共有部分
            saveFdcq3Gyxx(qlData.qlid);
            //修改土地性质为划拨时，权利的结束时间不可填写
            if ($('.jssj').length > 0) {
                checkJssjGz1($("#qlxz").val(), $('.jssj').val());
            }
            //验证所在层与总层数
            checkSzcAndZcs(qlData.szc, qlData.zcs);

            //验证面专有建筑面积和分摊建筑面积之和是否等于建筑面积
            var $fdcq = $("#bdc_fdcq" + index);
            if ($fdcq.length > 0) {
                var jzmj = $("#bdc_fdcq" + index).find("#fdcq-jzmj").val();
                var zyjzmj = $("#bdc_fdcq" + index).find("#fdcq-zyjzmj").val();
                var ftjzmj = $("#bdc_fdcq" + index).find("#fdcq-ftjzmj").val();
                //checkMj(ftjzmj, zyjzmj, jzmj);
                // 验证定着物面积和
                var $bdcdyxx = $("#bdcdyxxForm" + index);
                if($bdcdyxx.length > 0){
                    var dzwmj = $("#bdcdyxxForm" + index).find("#dzwmj").val();
                    checkDzwmjAndJzmj(dzwmj, qlData.jzmj);
                }
            }
            if (getClassNameUse.indexOf('jzq') > -1) {
                var $zsjz = $("#zsjz");
                if ($zsjz.length > 0) {
                    //终生居住验证
                    checkJzqsj(qlData.zsjz, qlData.jzqkssj, qlData.jzqjssj);
                }
            }
            // 查封同步查封机关至权利人
            if (getClassNameUse.indexOf("cf") > -1) {
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

/**
 * 处理领证人信息
 * @param qlr
 */
function initLzrxx(qlr) {
    var lzrList = [];
    var bdclzr = {};
    //合肥需求，领证人默认代理人
    if (isNotBlank(qlr.dlrmc)) {
        bdclzr.qlrid = qlr.qlrid;
        bdclzr.lzrmc = qlr.dlrmc;
        bdclzr.lzrzjzl = qlr.dlrzjzl;
        bdclzr.lzrzjh = qlr.dlrzjh;
        bdclzr.lzrdh = qlr.dlrdh;
        bdclzr.xmid = qlr.xmid;
    } else if (isNotBlank(qlr.qlrmc)) {
        bdclzr.qlrid = qlr.qlrid;
        bdclzr.lzrmc = qlr.qlrmc;
        bdclzr.lzrzjzl = qlr.zjzl;
        bdclzr.lzrzjh = qlr.zjh;
        bdclzr.lzrdh = qlr.dh;
        bdclzr.xmid = qlr.xmid;
    }else if(isNotBlank(qlr.xmid) && isNotBlank(qlr.qlrid)){
        //xmid和权利人id都不为空,可能受理页面设置了不可编辑,没有获取到名称,根据权利人id去后台查一遍
        getReturnData("/slym/qlr",{qlrid:qlr.qlrid},"GET",function (data) {
            if (data && isNotBlank(data.dlrmc)) {
                bdclzr.qlrid = data.qlrid;
                bdclzr.lzrmc = data.dlrmc;
                bdclzr.lzrzjzl = data.dlrzjzl;
                bdclzr.lzrzjh = data.dlrzjh;
                bdclzr.lzrdh = data.dlrdh;
                bdclzr.xmid = data.xmid;
            } else if (data && isNotBlank(data.qlrmc)) {
                bdclzr.qlrid = data.qlrid;
                bdclzr.lzrmc = data.qlrmc;
                bdclzr.lzrzjzl = data.zjzl;
                bdclzr.lzrzjh = data.zjh;
                bdclzr.lzrdh = data.dh;
                bdclzr.xmid = data.xmid;
            }
        },function (xhr) {
            delAjaxErrorMsg(xhr);
        },false)
    }


    lzrList.push(bdclzr);
    var url = "";
    if(lclx === "pllc") {
        url = "/slym/lzr/hf/lzrxx/pllc?gzlslid=" + processInsId + "&xmid=" +  qlr.xmid + "&qlrid=" + qlr.qlrid;
    } else {
        url = "/slym/lzr/hf/lzrxx?gzlslid=" + processInsId + "&qlrid=" + qlr.qlrid + "&xmid=" + qlr.xmid;
    }
    getReturnData(url,JSON.stringify(lzrList),'PATCH',function (data) {
    },function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    })
}

function saveAllQlr(qlrArray, c) {
    var qlrList = [];
    var qlr = {};
    var qlrnum = 0;
    var ywrnum = 0;
    var gyfs = "";
    var ywrgyfs = "";

    for (var j = 0; j < qlrArray.length; j++) {
        var name = qlrArray[j].name;
        qlr[name] = qlrArray[j].value;
        // 以xmid为每一组权利人的起始数据，作为分割依据
        if ((j + 1 < qlrArray.length && qlrArray[j + 1].name === 'xmid') || j + 1 == qlrArray.length) {
            if (qlr.qlrlb === "1") {
                qlrnum++;
                gyfs += qlr.gyfs + ",";
                // 此处处理合肥领证人信息
                initLzrxx(qlr);
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
    var qllx = $(tabs).find("input[name=qllx]").val();
    if (!isNotBlank(qllx)) {
        qllx = 0;
    }
    //判断是否存在权利人(当页面的权利人数据设置disabled属性后获取不到值，改为从数据库获取)
    var result = checkQlrsl(processInsId, currxmid);
    if (!result && 98 != qllx) {
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
    var url = "";
    if (lclx === "zhlc") {
        url = "/slym/qlr/list/zhlc?processInsId=" + processInsId;
    }
    if (lclx === "pllc") {
        url = "/slym/qlr/list/pllc?processInsId=" + processInsId + "&xmid=" + xmid;
    }


    if (isNotBlank(qlrList)) {
        getReturnData(url, JSON.stringify(qlrList), 'PATCH', function (data) {
            if (data > 0) {
            }
        }, function (err) {
            throw err;
        }, false);
    }


}

/**
 * 权利人，义务人发生改变后同步修改权利表权利人义务人信息
 * @param xmid
 * @param indexnum   渲染模板INDEX
 */
function reloadDyrAndCfjg(xmid, indexnum,loadzwr) {
    if (isNotBlank(xmid)) {
        var dyr = null;
        var gydqlr = null;
        var xydqlr = null;
        var zwr =null;
        var zwrzjzl =null;
        var zwrzjh =null;
        if (lclx === "zhlc") {
            if (indexnum === "") {
                var index = $(".layui-this").find("input[name='liIndex']");
                indexnum = $(index[0]).val();
            }
            dyr = $("#qllx" + indexnum).find("#dyaq-dyr");
            gydqlr = $("#qllx" + indexnum).find("#dyiq-gydqlr");
            xydqlr = $("#qllx" + indexnum).find("#dyiq-xydqlr");
            zwr =$("#qllx" + indexnum).find("#dyaq-zwr");
            zwrzjzl =$("#qllx" + indexnum).find("#dyaq-zwrzjzl");
            zwrzjh =$("#qllx" + indexnum).find("#dyaq-zwrzjh");


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
        //合并流程，产权权利人会同步债务人，需要刷新债务人
        var qlindex = $("input[name='liIndex']");
        if(lclx === "zhlc" &&zwr != null &&zwr.length>0 &&qlindex.length ===2 &&loadzwr){
            getReturnData("/slym/ql/zwr", {xmid: xmid}, 'GET', function (data) {
                if(data &&data.bdcDsQlrDOList &&data.bdcDsQlrDOList.length >0){
                    var $sqr =$("#bdc_dyaq"+ indexnum);
                    $(zwr).val(data.zwr);
                    $(zwr).attr("title",data.zwr);
                    if(zwrzjzl != null &&zwrzjzl.length >0){
                        $(zwrzjzl).val(data.zwrzjzl);
                        $(zwrzjzl).attr("title",data.zwrzjzl);
                    }
                    if(zwrzjh != null &&zwrzjh.length >0){
                        $(zwrzjh).val(data.zwrzjh);
                        $(zwrzjh).attr("title",data.zwrzjh);
                    }
                    generateDsQlr(xmid,$sqr,indexnum,data.bdcDsQlrDOList);
                }
            }, function (err) {
                console.log(err);
            }, false);

        }
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
                if ((i + 1) % 10 === 0) {
                    fdcqxmList.push(fdcqxm);
                    fdcqxm = {};
                }
            }
        }
        if (fdcqxmList !== []) {
            getReturnData("/slym/ql/fdcqxm/list", JSON.stringify(fdcqxmList), 'PATCH', function (data) {
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
                if(isNotBlank(qllx) && ['4','6','8'].indexOf(qllx)>-1){
                    refreshQlxx();
                }
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
                // 删除权利人后删除权利人对应的义务人
                deleteLzr(qlrid,lclx);

                // 刷新税收页面买方卖方信息
                if($("#ssxx").length > 0 && !$("#ssxx").hasClass("bdc-first-click")){
                    loadSsxx();
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
function deleteYwr(qlridlist, sxh, xmid, qlrid, otherXmidList) {
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
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
                // 刷新税收页面买方卖方信息
                if($("#ssxx").length > 0 && !$("#ssxx").hasClass("bdc-first-click")){
                    loadSsxx();
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

function deleteLzr(qlrid,lclx) {
    var url = '/slym/lzr/hf/delete?qlrid='+ qlrid + '&gzlslid=' + processInsId + '&djxl=';
    getReturnData(url,'','DELETE',function (data) {
        console.log("删除领证人成功")
    },function (xhr) {
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

// 一体化审批附件
function ythspfj() {
    layer.open({
        title: '附件信息',
        type: 2,
        area: ['1150px', '600px'],
        content: "/realestate-accept-ui/view/ythsp/ythspfj.html?processInsId="+processInsId
        ,cancel: function(){
        }
        ,success: function () {
        }
        ,end: function (index, layero) {

        }
    });
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
    $(".print-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#print").show();
        setUiWidth($(this), $("#print"));
    });

    $(".jyxx-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#moreBtn").hide();
        $("#jyxx").show();
        setUiWidth($(this), $("#jyxx"));
    });

    $(".more-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#jyxx").hide();
        $("#moreBtn").show();
        setUiWidth($(this), $("#moreBtn"));
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
    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && ( elem.id == 'print' || elem.id == 'jyxx' || elem.id == 'moreBtn')) {
                return;
            }
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
 * 保存一窗税收页签的买方卖方信息
 */
function saveZrzcfxx() {
    // 判断是否拥有 税收tab页面，与税收页面数据是否已经加载，然后在进行申请人数据的保存
    if($("#ssxx").length > 0 && $("#cmfxx").length > 0){ // 判断页面是否拥有税收页面,并已加载税收页面信息
        var sqrlist = [];
        if($(".zrzcf").length != 0){
            var zrfData = $(".zrzcf").serializeArray();
            var zrf= {};
            // 是否直属亲属交易，买方、卖方保持一致
            var sfzxqs = "";
            for (var zrfnum = 0; zrfnum < zrfData.length; zrfnum++) {
                var name = zrfData[zrfnum].name;
                zrf[name] = zrfData[zrfnum].value;
                // 以qlrid为每一组权利人的起始数据，作为分割依据
                if ((zrfnum + 1 < zrfData.length && zrfData[zrfnum + 1].name === 'sqrid') || zrfnum + 1 == zrfData.length) {
                    var bdcSlSqrxx = {};
                    bdcSlSqrxx.sqrid=zrf.sqrid;
                    bdcSlSqrxx.sbfwtc=zrf.sbfwtc;
                    bdcSlSqrxx.hyzk=zrf.hyzk;
                    bdcSlSqrxx.hyxxbdjg=zrf.hyxxbdjg;
                    bdcSlSqrxx.sqrlb=zrf.sqrlb;
                    bdcSlSqrxx.sqrmc=zrf.sqrmc;
                    bdcSlSqrxx.zjzl=zrf.zjzl;
                    bdcSlSqrxx.zjh=zrf.zjh;
                    bdcSlSqrxx.dh=zrf.dh;
                    bdcSlSqrxx.qlbl=zrf.qlbl;
                    if(zrf.sqrlb != "1"){ // 卖方需要记录满五唯一、满二、是否直接亲属交易信息
                        bdcSlSqrxx.jtmwwyzz=zrf.jtmwwyzz;
                        bdcSlSqrxx.sfgmmln=zrf.sfgmmln;
                        bdcSlSqrxx.sfzxqs=zrf.sfzxqs;
                        sfzxqs = zrf.sfzxqs;
                    }
                    sqrlist.push(bdcSlSqrxx);
                }
            }
            if(sqrlist.length>0){
                // 同步买方与卖方之间的sfzxqs是否直系亲属交易字段
                $.each(sqrlist, function(index, value){
                    value.sfzxqs = sfzxqs;
                });

                var url =  getContextPath() + "/slym/sqr/sqrxx/save?gzlslid=" + processInsId;
                $.ajax({
                    url: url,
                    type: 'PATCH',
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(sqrlist),
                    async: false,
                    success: function (data) {
                    },
                    error: function (e) {
                        delAjaxErrorMsg(e);
                    }
                });
            }
        }

        //保存房屋信息
        saveFwxx(".fwxx");

    }
}

function saveFwxx(formClass) {
    var fwxxData = {};
    var fwxxArray = $(formClass);
    if (fwxxArray !== null && fwxxArray.length > 0) {
        fwxxArray.serializeArray().forEach(function (item, index) {
            fwxxData[item.name] = item.value;
        });
        var xqdm =$("#xqdm").val();
        if(xqdm) {
            fwxxData.xqdm=xqdm;
        }
        fwxxData.fwxxid = $(".fwxx-fwxxid").val();
        $.ajax({
            url: getContextPath() + "/ycsl/fwxx",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(fwxxData),
            success: function (data) {
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }

}
/**
 * 身份证证件号验证添加
 * @param zjzl 证件种类
 * @param elem 证件种类dom元素
 */
function addSfzYz(zjzl, elem){
    var zjzlid = $(elem).parent().parent().find("select")[0].id;
    var zjhid = zjzlid.replace("zjzl", "zjh");
    var $zjh = $(elem).parent().parent().next().find("#" + zjhid);
    var zjhVerify =  $zjh.attr("lay-verify");
    if(zjzl == '1'){
        if (isNotBlank(zjhVerify)) {
            if (zjhVerify.indexOf("identitynew") < 0) {
                $zjh.attr("lay-verify", zjhVerify + "|identitynew");
            }
        } else {
            $zjh.attr("lay-verify", "identitynew");
        }
    }else {
        //移除证件号验证
        if (isNotBlank(zjhVerify) && zjhVerify.indexOf("identitynew") > -1) {
            $zjh.attr("lay-verify", zjhVerify.replace("identitynew", ""));
        }
    }
}

//权利人义务人签字:调用签字版进行签字,获取签字图片信息
function qlrywrQz(title,xmid,qzrlb,bdlx) {

    // 记录受理人员操作权利人义务人签字
    $.ajax({
        url: getContextPath() + "/rest/v1.0/slym/qlrywrQz",
        type: 'POST',
        dataType: 'json',
        data: {xmid: xmid, qzrlb: qzrlb, bdlx: bdlx},
        success: function (data) {
        },
        error: function (e) {
            delAjaxErrorMsg(e);
        }
    });

    layer.open({
        title: title,
        type: 2,
        area: ['700px','430px'],
        content: "/realestate-register-ui/view/qzb/qzb.html"
        ,cancel: function(){
        }
        ,success: function () {
            // 初始化打开页面时，将签字信息缓存清空，防止获取上一个签字人员的信息
            layui.data('signStream', {
                key: 'data' ,value: ""
            });
        }
        ,end: function (index, layero) {
            var signStream = layui.data('signStream');
            if(signStream && signStream.data) {
                if(qzrlb ===1){
                    $("#qlrqztp").attr("src","data:image/png;base64," + signStream.data);
                }else if(qzrlb ===3){
                    $("#ywrqztp").attr("src","data:image/png;base64," + signStream.data);
                }
                saveQlrYwrQzxx(xmid,signStream.data,qzrlb,bdlx)
            }

        }
    });


}

/**
 * 保存签字信息
 */
function saveQlrYwrQzxx(xmid,qznr,qzrlb,bdlx) {
    if(!isNotBlank(xmid) || !isNotBlank(qznr)) {
        return;
    }
    $.ajax({
        url: getContextPath()+"/pjq/saveQzxx",
        type: "POST",
        data: JSON.stringify({"xmid": xmid, "qznr": qznr,qzrlb:qzrlb,bdlx:bdlx,gzlslid:processInsId}),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {


        },
        error: function (xhr, status, error) {
            removeModal();
            ityzl_SHOW_WARN_LAYER("保存签字信息失败,请重试");
        }
    });
}

//加载第三权利人信息
function generateDsQlr(xmid,$sqr,index,bdcDsQlrDOList){
    //渲染模板数据
    var json = {
        index: index,
        xmid: xmid,
        bdcDsQlrDOList: bdcDsQlrDOList,
        zd: zdList
    };
    var dsQlrxxTpl = dsQlrTpl.innerHTML;

    if (isNotBlank($sqr)) {
        //渲染数据
        if ($sqr.parents(".layui-tab-item").find(".dsqlr-basic").length > 0) {
            $sqr.parents(".layui-tab-item").find(".dsqlr-basic").remove();
        }
        laytpl(dsQlrxxTpl).render(json, function (html) {
            $sqr.after(html);
            form.render();
        });
    }

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
            if(isNotBlank(qlrid)) {
                addModel();
                //确定操作
                getReturnData(url, {}, 'DELETE', function (data) {
                    removeModal();
                    ityzl_SHOW_SUCCESS_LAYER("删除成功");
                    $(elem).parents(".dsqlr-tr").remove();
                }, function (err) {
                    delAjaxErrorMsg(err)
                });
            }else{
                $(elem).parents(".dsqlr-tr").remove();
            }
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
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

//新增第三权利人
function addDsQlr(element,qlrlb,xmid) {
    var $table = $(element).parents(".dsqlr-basic").find("table");
    var dsQlr = {
        qlrlb: qlrlb,
        xmid:xmid
    };
    if($table.find(".bdc-table-none").length >0){
        $table.find(".bdc-table-none").remove();
    }
    var xh =$table.find(".dsqlr-tr").length;
    var json = {
        dsQlr: dsQlr,
        zd:zdList,
        xh:xh+1
    };

    var getTpl = addDsQlrTpl.innerHTML;
    laytpl(getTpl).render(json, function (html) {
        $table.append(html);
        form.render();
        resetSelectDisabledCss();
    });

}

//第三权利人保存
function saveDsQlr() {
    var dsqlrList = [];
    var sxh = 1;
    $(".dsqlr-tr").each(function () {
        var $dsqlr = $(this);
        var dsqlrArray = $dsqlr.find(".dsQlr").serializeArray();
        if (dsqlrArray !== null && dsqlrArray.length > 0) {
            var dsqlr = {};
            dsqlrArray.forEach(function (item, index) {
                dsqlr[item.name.replace("dsqlr-","")] = item.value;
            });
            dsqlr.xmid =$dsqlr.find("[name=xmid]").val();
            dsqlr.qlrid =$dsqlr.find("[name=qlrid]").val();
            dsqlr.sxh = sxh;
            dsqlrList.push(dsqlr);
            sxh++;
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

//展示第三权利人模块
function showDsQlrTpl(elem) {
    if ($(elem).parents(".layui-tab-item").find(".dsqlr-basic").length > 0) {
        var $dsqlr = $(elem).parents(".layui-tab-item").find(".dsqlr-basic");
        $dsqlr.removeClass("bdc-hide");
        //滚动到对应位置
        $('html,body').animate({scrollTop: $dsqlr.offset().top}, 800);
    }

}

function getYjxx() {
    var yjxx = {};
    getReturnData("/slym/yjxx/cz/" + processInsId, {}, "GET", function (result) {
        yjxx = result;
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    return yjxx;
}

function getCfbh(xmid,qllx) {
    var cfbh ="";
    if(qllx !="98"){
        return cfbh;
    }
    getReturnData("/slym/ql/queryQlxx", {xmid: xmid}, 'GET', function (data) {
        if(data &&isNotBlank(data.cfbh)){
            cfbh =data.cfbh;

        }
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    return cfbh;
}

//保存邮寄信息
function saveYjxx() {
    var yjxxArray = $(".yjxx");
    var yjxx = {};
    if (yjxxArray !== null && yjxxArray.length > 0) {
        yjxx.slbh = $('#sjbh').val();
        yjxx.gzlslid = processInsId;
        yjxxArray.serializeArray().forEach(function (item, index) {
            yjxx[item.name] = item.value;
        });
        getReturnData("/slym/yjxx/yj", JSON.stringify(yjxx), "POST", function (data) {
            $('#yjxxid').val(data);
            console.log("保存邮寄信息成功")
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        })
    }
}


//保存宗地基本信息
function saveZdjbxx(){
    var zdjbxxArray = $(".zdjbxx");
    var zdjbxx ={};
    if (zdjbxxArray !== null && zdjbxxArray.length > 0) {
        zdjbxxArray.serializeArray().forEach(function (item, index) {
            zdjbxx[item.name] = item.value;
        });
    }
    var zddm =$("#zddm").val();
    if(!$.isEmptyObject(zdjbxx) &&isNotBlank(zddm)) {
        zdjbxx.zddm =zddm;
        if(Object.keys(zdjbxx).length >1) {
            getReturnData("/slym/ql/zdjbxx", JSON.stringify(zdjbxx), "PATCH", function () {

            }, function (error) {
                delAjaxErrorMsg(error);

            })
        }

    }
}


//导入合同信息
function importJyxx(htbh,xmid){
    if(!isNotBlank(htbh) ||!isNotBlank(xmid)){
        return false;
    }
    $.ajax({
        url: getContextPath() + "/ycsl/fcjyxx",
        type: 'GET',
        data: {htbh: htbh, xmid: xmid, lclx : lclx,yzhtbh:true,modifyDsQlr:true,splitQlr:true},
        dataType: 'json',
        asyc:false,
        success: function (data) {
            loadQlr();

        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

/**
 * 同步查封机关，自动填充至权利人信息
 */
function syncCfjg(qldata) {
    if (isNotBlank(qldata.cfjg)) {
        var param = {
            jgmc: qldata.cfjg,
            xmid: qldata.xmid,
            processInsId: processInsId
        };
        getReturnData("/slym/qlr/sync/cfjgSqr", param, 'GET', function (data) {
        }, function (err) {
            console.info(err);
        }, false);
    }
}


function uploadQszm() {
    addModel("生成上传中");
    getReturnData("/slPrint/fwqszm", {gzlslid: processInsId}, "GET", function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("上传成功");
        loadSjcl();
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);

    })
}

function saveSlfs(xmid){
    var qlData = {};
    var slfs = $("#slfs").val();
    var jzq = getSlfs(xmid);
    var className = "cn.gtmap.realestate.common.core.domain.BdcJzqDO";
    qlData['slfs'] = slfs;
    qlData['qlid'] = jzq.qlid;
    getReturnData("/slym/ql?className=" + className, JSON.stringify(qlData), 'PATCH', function (data) {

    }, function (err) {
        throw err;
    }, false);
}

