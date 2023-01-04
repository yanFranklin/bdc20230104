var layer;
var dzwmj;
//保存结束后提示信息
var saveMsg = '';
$(function () {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var form = layui.form;
        layer = layui.layer;
        var isFirst = true;
        //点击提交时不为空的全部标红
        var isSubmit = true;
        //验证提示信息
        var verifyMsg = "必填项不能为空";
        form.verify({
            required: function (value, item) {
                if (value == '' && !isXndyh) {
                    if (isFirst) {
                        $('.layui-tab-item').removeClass('layui-show');
                        $(item).parents('.layui-tab-item').addClass('layui-show');
                        var liIndex = $(item).parents('.layui-tab-item').index() + 1;
                        $('.layui-tab-title li').removeClass('layui-this');
                        $('.layui-tab-title li:nth-child(' + liIndex + ')').addClass('layui-this');
                        isFirst = false;
                    }
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                }
            }
            , lxdh: function (value, item) {
                if (!validatePhone($.trim(value))) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    if (isFirst) {
                        $('.layui-tab-item').removeClass('layui-show');
                        $(item).parents('.layui-tab-item').addClass('layui-show');
                        var liIndex = $(item).parents('.layui-tab-item').index() + 1;
                        $('.layui-tab-title li').removeClass('layui-this');
                        $('.layui-tab-title li:nth-child(' + liIndex + ')').addClass('layui-this');
                        isFirst = false;
                    }
                    verifyMsg = "联系电话格式不正确";
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
        form.on("submit(saveData)", function (data) {
            addModel();
            // 调用表单规则验证，调用规则服务验证权籍数据是否一致
            AcceptForm.verifyGz(processInsId).done(function(yzResult){
                removeModal();
                // 当验证数据为空时，直接进行保存。
                if(yzResult.length == 0){
                    doSubmit();
                }else{
                    // 验证存在数据不一致时，进行忽略提示。用户点击忽略时，进行回调保存操作。
                    AcceptForm.showTsxx(yzResult, doSubmit);
                }
            }).fail(function(message){
                console.info(message);
                removeModal();
            });
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
                        $.when(saveXmxx(), saveQlxx(), saveSfhz(), saveSjcl(), saveDsQlr(), saveQlr(), saveLzr(), updateFwfsss()).done(function () {
                            //南通受理页面保存同步保存收费数据
                            saveSf();
                            /**
                             * @param
                             * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
                             * @description 受理页面保存时回写领证日期到平台
                             * @date : 2020/1/15 10:15
                             */
                            setLzrqToPortal();
                            if (lclx === "zhlc") {
                                refreshQlxx();
                            } else if (lclx === "pllc") {
                                loadBdcdyh();
                                loadPlQlxx();
                            }
                            removeModal();
                            if (!isNotBlank(saveMsg)) {
                                ityzl_SHOW_SUCCESS_LAYER("保存成功");
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
        bdcXmData.djyy = $("#djyy" + index).val();
        var sqfbcz = $($(formClass).find("input[type='radio']:checked")).val();
        if (isNotBlank(sqfbcz)) {
            bdcXmData.sqfbcz = sqfbcz;
        }
        //定着物面积与建筑面积同步(防止出现两个面积一个隐藏一个显示)
        var $jzmj = $("#qllx" + (index)).find("input[name=jzmj]");
        bdcXmData.dzwmj = $jzmj.val();
        $("#bdcdyxxForm" + (index)).find("input[name=dzwmj]").val($jzmj.val());
        // }
        //定着物用途与规划用途同步(防止出现两个用途一个隐藏一个显示)
        var $ghyt = $("#qllx" + (index)).find("select[name=ghyt]");
        if ($ghyt.length > 0) {
            bdcXmData.dzwyt = $ghyt.val();
        }
        getReturnData("/slym/xm", JSON.stringify(bdcXmData), 'PATCH', function (data) {

        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);

        if($(".sfzf" + index) && $(".sfzf" + index).length > 0){
            var sfzf = $(".sfzf" + index).val();
            saveSfzfByXmid(sfzf,bdcXmData.xmid);
        }

    }

}

//保存项目附表
function saveXmfb(formClass, index) {
    var bdcXmfbData = {};
    var bdcXmfbArray = $(formClass);
    if (bdcXmfbArray !== null && bdcXmfbArray.length > 0) {
        bdcXmfbArray.serializeArray().forEach(function (item, index) {
            bdcXmfbData[item.name] = item.value;
        });
        getReturnData("/slym/xm/fb", JSON.stringify(bdcXmfbData), 'PATCH', function (data) {
            console.log("保存项目附表成功")
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
        //交易合同号同步交易信息表
        jyxxData.htbh = jyxxData.jyhth;
        var xmid = $(".xmxx" + index).find("input[name=xmid]").val();
        var jyxxid = $("#xmForm" + index).find("input[name=jyxxid]").val();
        if (isNotBlank(xmid)) {
            jyxxData["xmid"] = xmid;
            jyxxData["jyxxid"] = jyxxid;
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
        //交易合同号同步交易信息表
        if (isNotBlank(jyxxData.jyhth)) {
            jyxxData.htbh = jyxxData.jyhth;
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
        bdcXmData.djyy = $('#djyy').val();
        var sqfbcz = "";
        $("input:radio[name='sqfbcz']:checked").each(function () {
            sqfbcz = $(this).val();
        });
        if (isNotBlank(sqfbcz)) {
            bdcXmData.sqfbcz = sqfbcz;
        }
        //  批量流程，项目内多幢情况，权利信息增加可编辑坐落字段，同步更新项目表
        var $zl = $("#qlxxdiv").find("input[name='zl']");
        if($zl.length>0){
            bdcXmData.zl = $zl.val();
        }
    }

    var mjdw = '1';
    $("input:checkbox[name='mjdw']:checked").each(function () {
        mjdw = $(this).val();
    });
    if (isNotBlank(mjdw)) {
        bdcXmData.mjdw = mjdw;
    }

    getReturnData("/slym/xm/list?processInsId=" + processInsId, JSON.stringify(bdcXmData), 'PATCH', function (data) {

    }, function (err) {
        throw err;
    }, false);
}

function saveSfhz(){

    if(lclx == 'pllc'){
        if(!document.getElementById("sfjcybdcqzhDiv")){
            //如果页面不存在该元素，则不加载收费状态
            return ;
        }
        if ($("#sfjcybdcqzhDiv").css("display") =="none"){
            return;
        }
        var sfhz = $("input[name='sfjcybdcqzh']:checked").val();
        var xmid = $("input[name=xmid]").val();
        $.ajax({
            url: '/realestate-accept-ui/slym/xm/sfhz',
            data: {gzlslid: processInsId, sfhz: sfhz, xmid: xmid, lclx: lclx},
            type: 'POST',
            dataType: "json",
            success: function (data) {

            },
            error: function (error) {
                delAjaxErrorMsg(error);

            }
        });
    }
    if(lclx == 'zhlc'){
        var index = $("input[name='liIndex']");

        if (index !== null && index.length > 0) {
            for (var i = 0; i < index.length; i++) {
                var c = $(index[i]).val();
                if(!document.getElementById("sfjcybdcqzhDiv"+c)){
                    //如果页面不存在该元素，则不保存收费状态
                    continue ;
                }
                if ($("#sfjcybdcqzhDiv"+c).css("display") =="none"){
                    continue;
                }
                var sfhz = $("input[name='sfjcybdcqzh"+c+"']:checked").val();
                var xmid = $($(".xmxx" + c).find("input[name=xmid]")).val();

                $.ajax({
                    url: '/realestate-accept-ui/slym/xm/sfhz',
                    data: {gzlslid: processInsId, sfhz: sfhz, xmid: xmid, lclx: lclx},
                    type: 'POST',
                    dataType: "json",
                    success: function (data) {

                    },
                    error: function (error) {
                        delAjaxErrorMsg(error);

                    }
                });
            }
        }
    }
}

//权利信息保存（入口方法）
function saveQlxx() {
    if (lclx === "zhlc") {

        var index = $("input[name='liIndex']");
        if (index !== null && index.length > 0) {
            for (var i = 0; i < index.length; i++) {
                var c = $(index[i]).val();
                saveBdcXm(".xmxx" + c, c);
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
                saveGzxx(".gzxx" + c, c);
                //保存交易信息，存在即保存
                if ($(".jyxx" + c).length > 0) {
                    saveJyxx(".jyxx" + c, c);
                }
                //当权利tab未加载出来时保存
                if(c >0) {
                    var tabQllx = $($('.qlxx')[c - 1]).find("input[name=qllx]").val();
                    if(tabQllx ==="98" &&$("#bz").length >0 &&$(".cf" + c).length=== 0){
                        var tabXmid = $($('.qlxx')[c - 1]).find("input[name=xmid]").val();
                        //查封同步备注
                        var qlData = {};
                        qlData.bz = $("#bz").val();
                        getReturnData("/slym/ql/updateQlRyzd?xmid=" + tabXmid, JSON.stringify(qlData), 'PATCH', function (data) {

                        }, function (err) {
                            throw err;
                        }, false);
                    }

                }


            }

        }

    } else {
        saveXmfbPl(".xmfb",'');
        //批量流程，处理一些需要同步权利表字段的更新
        var qlData = {};
        var qlxxArray = $(".qlxx").serializeArray();
        qlxxArray.forEach(function (item, index) {
            var name = item.name;
            qlData[name] = item.value;
        });
        var djyy = $('#djyy').val();
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
        if (qllx === "98") {
            //查封同步备注
            qlData.bz = $("#bz").val();
        }
        if (!$.isEmptyObject(qlData) && isNotBlank(xmid)) {
            getReturnData("/slym/ql/list?processInsId=" + processInsId + "&onexmid=" + xmid + "&zxlc=" + zxlc, JSON.stringify(qlData), 'PATCH', function (data) {

            }, function (err) {
                delAjaxErrorMsg(err);
            });

        }

        //保存交易信息
        saveJyxxPl();
    }
    saveSdqGhxx();
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

            //查封同步备注
            if($("#bz").length >0) {
                qlData.bz = $("#bz").val();
            }
        }
        //同步登记原因
        var djyy = $('#djyy' + index).val();
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
        //定着物面积与建筑面积同步(防止出现两个面积一个隐藏一个显示)
        var $dzwmj = $("#bdcdyxxForm" + (index)).find("input[name=dzwmj]");
        if ($dzwmj.length > 0) {
            qlData.jzmj = $dzwmj.val();
        }
        //定着物用途与规划用途同步(防止出现两个用途一个隐藏一个显示)
        var $dzwyt = $("#bdcdyxxForm" + (index)).find("select[name=dzwyt]");
        if ($dzwyt.length > 0) {
            qlData.dzwyt = $dzwyt.val();
        }
        var getClassNameUse = formClass.attr('class');
        if(getClassNameUse.indexOf('jzq') > -1) {
            var zsjz = "";
            $("input:radio[name='zsjz']:checked").each(function () {
                zsjz = $(this).val();
            });
            qlData.zsjz = zsjz;
        }

        if (qlData !== {}) {
            savaFdcqxm(qlData.qlid);
            //保存共有部分
            saveFdcq3Gyxx(qlData.qlid);
            //修改土地性质为划拨或租赁时，权利的结束时间不可填写
            if ($('.jssj').length > 0) {
                //配置了规划用途-不验证权力结束时间
                var noyz = false;
                var ghyt = qlData.ghyt;
                // 当规划用途被隐藏时，后台查询一下规划用途即可
                if(!ghyt){
                    ghyt = getGhytByXmid(qlData.xmid);
                }
                console.log("验证规划用途配置和当前规划用途："+noYzGhytList+" "+ghyt);
                // 如果都是空 为了可以保存，则认定为验证通过也可以保存
                if(ghyt && noYzGhytList.indexOf(ghyt) > -1) {
                    noyz = true;
                }
                checkJssjGz2($("#qlxz").val(), $('.jssj').val(), noyz);
                checkJssjGz4($("#qlxz").val(), $('.jssj').val());

            }
            if ($('.jssj').length > 0 && $('.qssj').length > 0) {
                checkJssjGz3($("#qlxz").val(), $('.jssj').val(), $('.qssj').val())
            }
            //验证所在层与总层数
            checkSzcAndZcs(qlData.szc, qlData.zcs);
            if(getClassNameUse.indexOf('jzq') > -1) {
                var $zsjz = $("#zsjz");
                if ($zsjz.length > 0) {
                    //终生居住验证
                    checkJzqsj(qlData.zsjz, qlData.jzqkssj, qlData.jzqjssj);
                }
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
}

function saveLzr() {
    if (lclx === "pllc") {
        var lzrArrayPllc = $(".lzxx").serializeArray();
        saveAllLzr(lzrArrayPllc, "");
    } else {
        var index = $("input[name='liIndex']");
        if (index !== null && index.length > 0) {
            for (var i = 0; i <= $('.layui-tab-item').length; i++) {
                var c = $(index[i-1]).val();
                var lzrArray = $(".layui-tab-item:nth-child(" + i + ") .lzxx").serializeArray();
                if(isNotBlank(lzrArray)) {
                    saveAllLzr(lzrArray, c);
                }
            }
        }
    }
}

function saveAllLzr(lzrArray, c) {
    var lzrList = [];
    var lzr = {};
    for(var m = 0; m< lzrArray.length; m++) {
        var name = lzrArray[m].name;
        lzr[name] = lzrArray[m].value;
        if((m+1) % 6 ===0 ) {
            if(isNotBlank(lzr.lzrmc)) {
                lzrList.push(lzr);
            }
            lzr = {};
        }
    }
    var url = "";
    if (lclx === "zhlc") {
        url = "/slym/lzr/nt/lzrxx/jdlc?gzlslid=" + processInsId;
    }
    if (lclx === "pllc") {
        url = "/slym/lzr/nt/lzrxx/pllc?gzlslid=" + processInsId + "&xmid=" + xmid;
    }
    if(isNotBlank(lzrList)) {
        getReturnData(url,JSON.stringify(lzrList),"POST",function (data) {

        },function (xhr) {
            delAjaxErrorMsg(xhr)
        },false)
    }
}

function saveAllQlr(qlrArray, c) {
    var qlrList = [];
    var qlr = {};
    var qlrnum = 0;
    var gyfs = "";
    var qlrmc = "";
    var ywrmc = "";
    for (var j = 0; j < qlrArray.length; j++) {
        var name = qlrArray[j].name;
        qlr[name] = qlrArray[j].value;
        if ((j + 1 < qlrArray.length && qlrArray[j + 1].name === 'xmid') || j + 1 == qlrArray.length) {

            if (qlr.qlrlb === "1") {
                qlrnum++;
                gyfs += qlr.gyfs + ",";
                qlrmc += qlr.qlrmc + ",";
                //判断是否为小微企业
                getXwqyxx(qlr.qlrmc,function (data) {
                    if(data != null &&isNotBlank(data.jgmc)){
                        //更新权利人类型为小微企业
                        qlr.qlrlx =98;
                        saveMsg+=qlr.qlrmc+"为小微企业，免收登记费 ";
                    }
                })
            } else {
                ywrmc += qlr.qlrmc + ",";
            }
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
    if (!checkGyfs(gyfs, qlrnum, currxmid)) {
        throw err = new Error('共有方式不正确，请检查！');
    }

    var url = "";
    if (lclx === "zhlc") {
        url = "/slym/qlr/list/zhlc?processInsId=" + processInsId;
    }
    if (lclx === "pllc") {
        url = "/slym/qlr/list/pllc?processInsId=" + processInsId + "&xmid=" + xmid;
    }

    // 如果是抵押权的话 则要ywr为产权的权利人
    if (qlmc.indexOf("抵押权") != -1) {
        if (lclx === "zhlc") {
            if ($("#jdlc-qlrmc").length == 0) {// 组合流程需要重新处理权利人的值
                var classStr = "layui-input ywr" + c + " ywrmc";
                for (var m = 0; m < $("input[class='" + classStr + "']").length; m++) {
                    ywrmc += $($("input[class='" + classStr + "']")[m]).val() + ",";
                }
            }
        } else {
            for (var m = 0; m < $('.pllcywr').length; m++) {
                ywrmc += $($(".pllcywr")[m]).html() + ",";
            }
        }
    }

    if (isNotBlank(qlrList)) {
        getReturnData(url, JSON.stringify(qlrList), 'PATCH', function (data) {
            if (data > 0) {
                loadQlr();
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
        if ((dyr !== null &&dyr.length !==0) || (xydqlr != null &&xydqlr.length !==0)) {
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
            if (xydqlr != null) {
                //需役地权利人
                $(xydqlr).val(bdcxm.qlr);
                xydqlr.title = bdcxm.qlr;
            }
        }
        var cfjg = document.getElementById('cf-cfjg');
        var jfjg = document.getElementById('cf-jfjg');
        var bzxr = document.getElementById('cf-bzxr');
        var qllx = $($(".layui-this").find("input[name='qllx']")[0]).val();
        if (lclx === "pllc") {
            qllx = $($("input[name='qllx']")[0]).val();
        }
        if (qllx === "98") {
            var bdcCfjgQO = {};
            bdcCfjgQO.gzlslid = processInsId;
            if (zxlc === "true") {
                bdcCfjgQO.isjf = true;
            } else {
                bdcCfjgQO.iscf = true;
            }
            var bdccf;

            getReturnData("/slym/ql/updateCfjgOrJfjg/nt?xmid=" + xmid, JSON.stringify(bdcCfjgQO), 'POST', function (data) {
                bdccf = data;
                if (zxlc === "true" && jfjg != null) {
                    $(jfjg).val(bdccf.jfjg);
                    jfjg.title = bdccf.jfjg;
                } else if (cfjg != null) {
                    $(cfjg).val(bdccf.cfjg);
                    cfjg.title = bdccf.cfjg;
                }
                //被执行人要读取更新
                if (bzxr !== null) {
                    $(bzxr).val(bdccf.bzxr);
                    bzxr.title = bdccf.bzxr;
                }
            }, function (err) {
                removeModal();
                delAjaxErrorMsg(err);
            });
        }
    }
}

function updateFdcq3Qlr(gzlslid) {
    var bdcFdcq3QO = {};
    bdcFdcq3QO.gzlslid = gzlslid;
    getReturnData("/slym/ql/updateFdcq3Qlr",JSON.stringify(bdcFdcq3QO),"POST",function (data) {

    },function (err) {
        removeModal();
        delAjaxErrorMsg(err);
    })
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
            getReturnData("/slym/ql//fdcq3/gyxx", JSON.stringify(gyxxList), 'PATCH', function (data) {
                if (data > 0) {
                    generateFdcqxm(qlid);
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

//关闭panel
function cancelEdit() {
    layer.closeAll();
}

//附件上传（批量上传)
function scfjqb() {
    getReturnData("/sjcl/folder", {gzlslid: processInsId}, 'GET', function (data) {
        if (isNullOrEmpty(data)) {
            layer.msg("人像采集文件夹生成失败");
        }
    }, function (err) {
        delAjaxErrorMsg(err)
    }, false);
    uploadSjcl("");
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

function layerOpen(title, url) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true
    });
    layer.full(index);
}

// 查看地籍调查表
function djdcb(processInsId) {
    // 判断是否是多个不动单元
    getReturnData("/slym/xm/dcblx", {
        processInsId: processInsId
    }, 'GET', function (data) {
        // 根据返回值判断是海籍调查表还是地籍调查表
        if (data) {
            var tzm = data.tzm;
            var bdcdyh = data.bdcdyh;
            var xmid = data.xmid;
            if ('H' == tzm) {
                // 海籍调查表
                if (bdcdyh) {
                    layerOpen("海籍调查表", "/realestate-register-ui/view/hyxx/hjdcb.html?xmid=" + xmid);
                } else {
                    layerOpen("海籍调查列表", "/realestate-accept-ui/view/query/dcbList.html?processInsId=" + processInsId + '&tzm=' + tzm);
                }
            } else {
                // 地籍调查表
                if (bdcdyh) {
                    layerOpen("地籍调查表", "/building-ui/djdcb/initview?bdcdyh=" + bdcdyh);
                } else {
                    layerOpen("地籍调查列表", "/realestate-accept-ui/view/query/dcbList.html?processInsId=" + processInsId + '&tzm=' + tzm);
                }
            }
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

// 档案调用
function dady(processInsId) {
    // 判断是否是多个不动单元
    getReturnData("/slym/xm/dady", {
        processInsId: processInsId
    }, 'GET', function (data) {
        if (data) {
            if (data === "empty") {
                layer.msg("未查询到当前 bdcdyh 上一手的产权")
            } else {
                var dadyInfo = tellTdFromBdc(data.xmid);
                if (dadyInfo.XMLY == "2") {// 土地
                    var url = dadyInfo.tdDadyUrl;
                    url += "?userName=" + encodeURI(dadyInfo.userName);
                    url += "&qlrmc=" + encodeURI(data.qlr ? data.qlr : "");
                    url += "&tdzl=" + encodeURI(data.zl ? data.zl : "");
                    url += "&tdzh=" + encodeURI(data.ytdzh ? data.ytdzh : "");
                    url += "&djh=" + encodeURI(data.ybdcdyh ? data.ybdcdyh : "");
                    url += "&password=";
                    window.open(url);
                } else {// 不动产
                    var now = new Date();
                    var dateStr = now.getFullYear() + "-" + (now.getMonth() + 1) + '-' + now.getDate();
                    var keycode = data.slbh;
                    var user = dadyInfo.userName;
                    var key = hex_md5((keycode + user + "VIEWPIC" + dateStr).toUpperCase()).toUpperCase();
                    var url = dadyInfo.dadyUrl + "?keycode=" + keycode + "&user=" + user + "&key=" + key;
                    window.open(url);
                }
            }
        } else {
            layerOpen("档案调用列表", "/realestate-accept-ui/view/query/dadyList.html?processInsId=" + processInsId);
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

function tellTdFromBdc(xmid) {
    var dataObj = {};
    $.ajax({
        url: '/realestate-accept-ui/slym/xm/tellTdFromBdc?xmid=' + xmid,
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            dataObj = data;
        }
    });
    return dataObj;
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
                console.log("更新房屋附属设施成功。");
            }
        }, function (err) {
            throw err;
        }, false);
    }

}


//---------------------从cz.js合并的通用的---------------------------------------

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
            var bdcXxXgZbDTO ={};
            var $change = $(this);
            if ($(this).hasClass("xm-select-parent")) {
                $change = $($(".bdc-change-input")[0]).parents(".layui-input-inline").find("select");
            } else if ($(this).hasClass("layui-input-inline")) {
                //select
                $change = $(this).find("select");
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
    getReturnData("/rest/v1.0/slym/queryXgLog", {gzlslid: processInsId}, "GET", function () {

    }, function (error) {


    })
}


function saveDsQlr() {
    if (lclx === "pllc") {
        var qlrArrayPllc = $(".dsQlr").serializeArray();
        saveAllDsQlr(qlrArrayPllc, "");
    } else {
        var index = $("input[name='liIndex']");
        if (index !== null && index.length > 0) {
            for (var i = 0; i < index.length; i++) {
                var c = $(index[i]).val();
                var qlrArray = $(".dsQlr" + c).serializeArray();
                saveAllDsQlr(qlrArray, c);
            }
        }
    }
}

function saveAllDsQlr(qlrArray, c) {
    var qlrList = [];
    var qlr = {};
    for (var j = 0; j < qlrArray.length; j++) {
        var name = qlrArray[j].name;
        qlr[name] = qlrArray[j].value;
        if ((j + 1) % 5 === 0) {
            qlrList.push(qlr);
            qlr = {};
        }
    }
    //当前项目ID
    var currxmid = xmid;
    if (lclx === "zhlc") {
        currxmid = $(".xmxx" + c).find("input[name=xmid]").val();
    }

    var url = "/slym/qlr/list/updateDsQlr?processInsId=" + processInsId + "&xmid=" + currxmid;

    if (isNotBlank(qlrList)) {
        getReturnData(url, JSON.stringify(qlrList), 'PATCH', function (data) {
        }, function (err) {
            throw err;
        }, false);
    }
}

function saveSf() {
    getReturnData("/sf/sfxm/save/nantong", {gzlslid: processInsId}, "GET", function (data) {
        console.log("保存收费信息成功")
    }, function (err) {
        throw err;
    })
}

function setLzrqToPortal() {
    getReturnData("/slym/xm/cnlzrq", {gzlslid: processInsId}, "GET", function (data) {
        if(data){
            console.log("回写领证日期成功")
        } else {
            ityzl_SHOW_WARN_LAYER("回写领证日期异常")
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })

}

function querySwspFj() {
    var htbh =$("#jyhth").val();
    if(!htbh){
        htbh =$("#dyhth").val();
    }
    if(!isNotBlank(htbh) &&bdcXm != null){
        htbh =bdcXm.jyhth;
    }
    getReturnData("/slym/sjcl/list/swWsxx",JSON.stringify({gzlslid:processInsId,htbh:htbh}),"POST",function (data) {
        if(data &&data.length >0){
            ityzl_SHOW_SUCCESS_LAYER("获取税票信息成功");
        }else{
            ityzl_SHOW_WARN_LAYER("未获取到税票信息！");
        }

    },function (error) {
        delAjaxErrorMsg(error);

    })
    
}

function getGhytByXmid(xmid){
    var ghyt = "";
    $.ajax({
        url: '/realestate-accept-ui/slym/ql/getGhytByXmid/'+xmid,
        type: 'GET',
        async: false,
        success: function (data) {
            ghyt = data;
        },
        error: function (e) {
        }
    });
    return ghyt;
}


