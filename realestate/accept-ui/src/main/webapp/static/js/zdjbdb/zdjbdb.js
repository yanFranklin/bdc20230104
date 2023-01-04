//字典列表
var zdList = loadZdData();
//地址栏获取数据
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var zxlc = getQueryString("zxlc");
var xmid = getQueryString("xmid");
var printValue = getQueryString("print");

var slbh, bdcSlXm, bdcSlSqrListCache; // bdcSlXm通过页面隐藏域jbxxId调用接口获取
var $, laytpl, form, table;
var bdcSlXmList = [];
var bdcqllxSlXm = {};
//规则验证入参
var gzyzParamMap = {};
//区分一手房二手房类型(二手房：clf,商品房：spf)
var fwfclx = getQueryString("fwfclx");
var includeDyaq = false;
var pllc = false;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    var element = layui.element;
    laytpl = layui.laytpl;
    var layer = layui.layer;

    addModel();
    // 加载受理页面按钮
    setTimeout(function () {
        try {
            $.when(generateTabContent()).done(function () {
                titleShowUi();
                removeModal();
            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e, "加载失败");
        }
    }, 50);

    // 监听3个页面的点击事件
    // 第一次单击tab，时查询受理信息
    // 非第一次不用再查
    element.on('tab(tabFilter)', function (data) {
        var tabid = $(".layui-tab-title .layui-this").attr("id");
        var $tabTitleTwo = $('.layui-tab-title li:nth-child(' + 2 + ')');
        var $nowTab = $('.layui-show');
        var $tabTwo = $('.layui-tab-item:nth-child(' + 2 + ')');
        var $this = $(this);
        if ($(this).hasClass('bdc-first-click') || (tabid === "swxx" && $tabTitleTwo.hasClass('bdc-first-click'))) {
            //判断是否第一次加载
            $this.removeClass('bdc-first-click');
            var tabXmid = $this.data('xmid');
            if (!isNotBlank(tabXmid)) {
                tabXmid = xmid;
            }
            addModel();
            setTimeout(function () {
                loadYcslxx(tabXmid, $nowTab);
            }, 0);
        }
    });
    form.render();


    //点击提交时不为空的全部标红
    var isSubmit = true;
    var isFirst = true;
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            var tabid = $(".layui-tab-title .layui-this").attr("id");
            //分开保存只验证需要保存部分的必填项
            var formVerify = false;
            if (($(item).parents(".swxx").length !== 0 && tabid === "swxx") || ($(item).parents(".swxx").length === 0 && tabid !== "swxx")) {
                formVerify = true;
            }
            if (!formVerify) {
                return;
            }
            if ((value == '' || value == null || value == undefined)) {//判断是否为空
                if (isFirst) {
                    //定位第一个为空的必填项
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
        , identitynew: function (value, item) {
            var tabid = $(".layui-tab-title .layui-this").attr("id");
            //分开保存只验证需要保存部分的必填项
            var formVerify = false;
            if (($(item).parents(".swxx").length !== 0 && tabid === "swxx") || ($(item).parents(".swxx").length === 0 && tabid !== "swxx")) {
                formVerify = true;
            }
            if (!formVerify) {
                return;
            }
            var msg = checkSfzZjh(value);
            if (isNotBlank(msg)) {
                if (isFirst) {
                    //定位第一个为空的必填项
                    $('.layui-tab-item').removeClass('layui-show');
                    $(item).parents('.layui-tab-item').addClass('layui-show');
                    var liIndex = $(item).parents('.layui-tab-item').index() + 1;
                    $('.layui-tab-title li').removeClass('layui-this');
                    $('.layui-tab-title li:nth-child(' + liIndex + ')').addClass('layui-this');
                    isFirst = false;
                }
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = msg;
            }
        }
        //要求正整数，允许为空
        , number: [/^[1-9]+[0-9]*]*$/, '请输入非负整数']
        //数字
        , number2: [/^-?\d*$/, '请输入数字']
        , lxdh: function (value, item) {
            var tabid = $(".layui-tab-title .layui-this").attr("id");
            //分开保存只验证需要保存部分的必填项
            var formVerify = false;
            if (($(item).parents(".swxx").length !== 0 && tabid === "swxx") || ($(item).parents(".swxx").length === 0 && tabid !== "swxx")) {
                formVerify = true;
            }
            if (!formVerify) {
                return;
            }
            if (!validatePhone(value)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "联系电话格式不正确";
            }
        }
    });

    // 表单保存方法
    form.on("submit(saveData)", function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            isFirst = true;
            return false;
        } else {
            addModel();
            //规则验证
            var bdcGzYzQO = {};
            bdcGzYzQO.zhbs = "saveYcslData";
            var gzyzParamMap = {};
            gzyzParamMap.xmid = xmid;
            gzyzParamMap.gzlslid = processInsId;
            bdcGzYzQO.paramMap = gzyzParamMap;
            gzyzCommon(2, bdcGzYzQO, function (data) {
                setTimeout(function () {
                    try {
                        $.when(saveYcslXx()).done(function () {
                            refreshYmxx();
                            removeModal();
                            ityzl_SHOW_SUCCESS_LAYER("保存成功");

                        })
                    } catch (e) {
                        removeModal();
                        if (e.message) {
                            showAlertDialog(e.message);
                        } else {
                            delAjaxErrorMsg(e);
                        }
                    }
                }, 10);

            });
            return false;
        }
    });

    //推送一窗创建登记流程
    $(document).on('click', '#tsBdcDjxx', function () {
        tsBdcDjxx();
    });

    //交税
    $(document).on('click', '#payTax', function () {
        if (fwfclx === "clf") {
            ityzl_SHOW_INFO_LAYER("财政接口暂未提供");
        } else {
            checkBdxx(function (data) {
                if (data) {
                    payTax();
                } else {
                    ityzl_SHOW_WARN_LAYER("婚姻或套次验证不通过,无法操作！");
                }
            })
        }
    });

    //推送计税信息
    $(document).on('click', '#tsjsxx', function () {
        checkBdxx(function (data) {
            if (data) {
                tsjsxx();
            } else {
                ityzl_SHOW_WARN_LAYER("婚姻或套次验证不通过，无法操作！");
            }

        });

    });
    //评价
    $(document).on('click', '#evaluate', function () {
        evaluate();
    });

    //线上反馈
    $(document).on('click', '#feedback', function () {
        feedback();
    });

    //备案
    $(document).on('click', '#jybaTg', function () {
        htba(1);
        return false;

    });

    //审核不通过,无法备案
    $(document).on('click', '#jybaBtg', function () {
        htba(0);
        return false;
    });

    //电子签名
    $(document).on('click', '#sign', function () {
        ityzl_SHOW_INFO_LAYER("电子签名接口暂未提供");
        return false;
    });


    // 权利信息区域，重新生成按钮初始化事件绑定
    qlxxModel.initEvent();
});

// 按钮加载
function loadButtonArea() {
    var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
    //渲染数据
    laytpl(tpl).render({fwfclx: fwfclx, xmid: xmid}, function (html) {
        view.innerHTML = html;
    });
    form.render();
    getStateById(readOnly, formStateId, "ycym");
    disabledAddFa();
}

//初始化tab及tab下的内容
function generateTabContent() {
    loadXmxx(function () {
        //加载按钮
        loadButtonArea();

        if (bdcSlXmList != null && bdcSlXmList.length > 0) {
            var json = {
                bdcSlxmList: bdcSlXmList
            };
            // //渲染tab页
            var liTpl = liTableTpl.innerHTML;
            var qlxxTab = document.getElementById('liTable');
            var qlxxContent = document.getElementById('contentTable');
            laytpl(liTpl).render(json, function (html) {
                qlxxTab.innerHTML += html;
            });
            // //渲染tab内容区
            var tpl = tabContentTpL.innerHTML;
            laytpl(tpl).render(json, function (html) {
                qlxxContent.innerHTML += html;
            });
        } else {
            layer.alert("当前未生成受理项目数据，请确认！");
        }
    });

}

function loadXmxx(callback) {
    //加载受理项目
    getReturnData("/ycsl/list/bdcslxm", {gzlslid: processInsId}, 'GET', function (item) {
        bdcSlXmList = [];
        bdcqllxSlXm = {};
        if (isNotBlank(item)) {
            for (var i = 0; i < item.length; i++) {
                item[i].qlmc = changeDmToMc(item[i].qllx, item[i].qlmc, zdList.qllx);
                if (bdcqllxSlXm[item[i].djxl] && bdcqllxSlXm[item[i].djxl].length > 0) {
                    var xmlist = bdcqllxSlXm[item[i].djxl];
                    xmlist.push(item[i]);
                    bdcqllxSlXm[item[i].djxl] = xmlist;
                } else {
                    var xmlist = [];
                    xmlist.push(item[i]);
                    bdcqllxSlXm[item[i].djxl] = xmlist;
                }
                if ((item[i].qllx === 4 || item[i].qllx === 6 || item[i].qllx === 8) && item[i].sfzf === 1) {
                    //获取主房项目
                    bdcSlXm = item[i];
                    xmid = item[i].xmid;
                }

            }
            //确定每个tab的tabxmid,优先读取主房xmid,不存在则读取任意一个
            for (var djxl in bdcqllxSlXm) {
                if (isNotBlank(bdcqllxSlXm[djxl])) {
                    var djxlXmList = bdcqllxSlXm[djxl];
                    for (var i = 0; i < djxlXmList.length; i++) {
                        if (djxlXmList[i].sfzf === 1 || i === (djxlXmList.length - 1)) {
                            bdcSlXmList.push(djxlXmList[i]);
                            break;
                        }

                    }
                }
            }
            if (!isNotBlank(xmid)) {
                bdcSlXm = bdcSlXmList[0];
                xmid = bdcSlXmList[0].xmid;
            }

        }
        callback();
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);

}

/**
 * 查询一窗的所有信息对象，包括
 * 房屋信息
 * 合同信息
 * 权利人信息
 * 权利人税务信息
 * */
function loadYcslxx(tabxmid, $nowTab) {
    $.ajax({
        url: getContextPath() + "/ycsl",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {gzlslid: processInsId, xmid: tabxmid},
        success: function (data) {
            //获取当前受理项目
            var tabSlxm = {};
            var tabSlxmList = [];
            bdcSlXmList.forEach(function (v) {
                if (v.xmid == tabxmid) {
                    tabSlxm = v;
                    tabSlxmList.push(tabSlxm);
                }
            });

            // 初始化申请人信息
            generateSqrxx(data, $nowTab);
            // 初始化加载权利信息和不动产单元信息
            generateQlxxAndBdcdyxx(data, $nowTab, tabSlxm, bdcqllxSlXm);

            form.render();
            renderSelect();
            getStateById(readOnly, formStateId, 'zdjbdb');
            form.render("select");
            disabledAddFa();
            renderDate(form);
            removeModal();
            reloadZjhYz();
        }
    });
}

function refreshYmxx() {
    loadXmxx(function () {
        for (var i = 1; i <= $('.layui-tab-item').length; i++) {
            var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
            if (isNotBlank(tabXmid)) {
                loadYcslxx(tabXmid, $('.layui-tab-item:nth-child(' + i + ')'));
            }

        }

    });

}

//加载申请人信息
function generateSqrxx(data, $nowTab) {
    var tabXmid = $nowTab.data('xmid');
    var tabQllx = $nowTab.data('qllx');
    var tabDjxl = $nowTab.data('djxl');
    //新增默认值
    var bdcSlSqr = {};
    bdcSlSqr["xmid"] = tabXmid;
    bdcSlSqr["sxh"] = 1;
    bdcSlSqr["qllx"] = tabQllx;
    var bdcSlSqrList = [];
    var zcfList = data.bdcSlZcfDTOList;
    var zrfList = data.bdcSlZrfDTOList;
    if (zrfList) {
        for (var i = 0; i < zrfList.length; i++) {
            bdcSlSqrList.push(zrfList[i]);
        }
    }
    if (zcfList) {
        for (var i = 0; i < zcfList.length; i++) {
            bdcSlSqrList.push(zcfList[i]);
        }
    }
    var zhlc = false;
    if (tabQllx === parseInt(commonDyaq_qllx)) {
        zhlc = true;
    }
    //只记录主房产权的申请人信息，用于获取交易
    if (tabXmid === xmid) {
        bdcSlSqrListCache = bdcSlSqrList;
    }
    var json = {
        bdcSlSqrDTOList: bdcSlSqrList,
        zd: zdList,
        bdcSlSqr: bdcSlSqr,
        zhlc: zhlc,
        djxl: tabDjxl
    };
    if ($nowTab.find('#sqrxx').length > 0) {
        var tpl = sqrTpl.innerHTML,
            view = $nowTab.find('#sqrxx')[0];
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
    }

}


/**
 * 身份证证件号验证添加
 * @param zjzl 证件种类
 * @param $zjh 需要添加身份证校验的dom元素
 */
function addSfzYz(zjzl, $zjh) {
    var zjhVerify = $zjh.attr("lay-verify");
    if (zjzl == '1') {
        if (isNotBlank(zjhVerify)) {
            if (zjhVerify.indexOf("identitynew") < 0) {
                $zjh.attr("lay-verify", zjhVerify + "|identitynew");
            }
        } else {
            $zjh.attr("lay-verify", "identitynew");
        }
    } else {
        //移除证件号验证
        if (isNotBlank(zjhVerify) && zjhVerify.indexOf("identitynew") > -1) {
            $zjh.attr("lay-verify", zjhVerify.replace("identitynew", ""));
        }
    }
}

// 身份证验证初始化
function reloadZjhYz() {
    $(document).find('.basic-info').each(function (index, item) {
        // 申请人、转入转出方身份证验证
        $(item).find('[lay-filter="zjzl"]').each(function () {
            var zjzlid = $(this).parent().parent().find("select")[0].id;
            var zjhid = zjzlid.replace("zjzl", "zjh");
            var $zjh = $(this).parent().parent().parent().find("#" + zjhid);
            addSfzYz($(this).val(), $zjh);
        });
        // 添加领证人验证
        $(item).find('[lay-filter="lzrzjhFilter"]').each(function () {
            var $zjh = $(this).parents(".layui-inline").next().find("input");
            addSfzYz($(this).val(), $zjh);
        });
    });

}

//保存一窗受理信息
function saveYcslXx() {
    var tabid = $(".layui-tab-title .layui-this").attr("id");
    //判断保存按钮，权利信息tab交易税收tab保存
    saveJbxx();
    saveSjcl();
    if (pllc) {
        saveJyxxPl();
    } else {
        saveBdcSlJyxx(".jyxx", "slTab");
        saveBdcSlFwxx(".fwxx", "slTab");
    }
    saveBdcSlXm(".slxm");
    saveSqr();//保存申请人

}

// 扩展表单serializeArray方法，将表单序列对象
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

function saveJbxx() {
    var bdcXmData = {};
    var bdcXmArray = $(".bdcxm");
    bdcXmArray.serializeArray().forEach(function (item, index) {
        bdcXmData[item.name] = item.value;
    });
    bdcXmData.bz = $("#bz").val();

    //默认为平方米，防止不可编辑冲掉
    var mjdw = '1';
    $("input:checkbox[name='mjdw']:checked").each(function () {
        mjdw = $(this).val();
    });
    if (isNotBlank(mjdw)) {
        bdcXmData.mjdw = mjdw;
    }
    bdcXmData.jbxxid = jbxxid;

    getReturnData("/slym/xm/jbxx", JSON.stringify(bdcXmData), 'PATCH', function (data) {
    }, function (err) {
        throw err;
    }, false);

}


// 处理申请人信息, 导入申请人权利人
function dealSqrxx(data) {
    var sqrList = [];
    if (isNotBlank(data) && isNotBlank(data.bdcSlSqr) && data.bdcSlSqr.length > 0) {
        $.each(data.bdcSlSqr, function (index, value) {
            if ("1" == value.sqrlb) {
                value.sxh = index;
                if (isNullOrEmpty(value.sqrlx)) {
                    value.sqrlx = 1;
                }
                if (isNullOrEmpty(value.zjzl)) {
                    value.zjzl = 1;
                }
                sqrList.push(value);
            }
        });
        addModel();
        var url = getContextPath() + "/slym/sqr/sqrxx/save?gzlslid=" + processInsId;
        $.ajax({
            url: url,
            type: 'PATCH',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(sqrList),
            success: function (data) {
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("导入成功。");
                refreshYmxx();
            },
            error: function (e) {
                removeModal();
                delAjaxErrorMsg(e);
            }
        });
    }
}

//字典项代码转名称
function changeDmToMc(dm, mc, zdList) {
    if (isNotBlank(zdList)) {
        for (var i = 0; i < zdList.length; i++) {
            var zd = zdList[i];
            if (dm == zd.DM) {
                mc = zd.MC;
                break;
            }
        }
    }
    return mc;
}


// 判断申请书打印类型，分为: 个人、单位、抵押
function printSqs(xmid) {
    if ($(".qlxx").length == 1) {
        if (includeDyaq) {
            printYcsl('ycdyasqs', xmid);
            console.log("打印一窗抵押申请书")
        } else {
            printYcsl('ycgrsqs', xmid);
            console.log("打印一窗个人申请书")
        }
    } else {
        // 组合申请书
        startNewPrintmode('yczhsqs', "all", xmid);
        console.log("打印一窗组合申请书")
    }
}
