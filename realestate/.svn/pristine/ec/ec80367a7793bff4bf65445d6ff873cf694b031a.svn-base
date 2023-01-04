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
            if (tabid === "swxx") {
                //第一次点击税务交易tab默认加载第一个权利tab
                $this = $tabTitleTwo;
                $nowTab = $tabTwo;
            }

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

    // 监听承受方税款信息新增
    $('.basic-info').on('click', '#addskxx', function () {
        var getTpl = addskxxTpl.innerHTML;
        laytpl(getTpl).render([], function (html) {
            $('.jexj').before(html);
        });
        form.render();
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

    // 交易核验
    $(document).on('click', '#jyhy', function () {
        jyhy();
    });

    // 推送住建
    $(document).on('click', '#tszj', function () {
        tszj();
    });

    //交税
    $(document).on('click', '#payTax', function () {
        if (fwfclx === "clf") {
            ityzl_SHOW_INFO_LAYER("财政接口暂未提供");
        } else {
            payTax();
            // checkBdxx(function (data) {
            //     if (data) {
            //         payTax();
            //     } else {
            //         ityzl_SHOW_WARN_LAYER("婚姻或套次验证不通过,无法操作！");
            //     }
            // })
        }
    });

    //推送计税信息
    $(document).on('click', '#tsjsxx', function () {
        tsjsxx();
        // checkBdxx(function (data) {
        //     if (data) {
        //         tsjsxx();
        //     } else {
        //         ityzl_SHOW_WARN_LAYER("婚姻或套次验证不通过，无法操作！");
        //     }
        //
        // });

    });
    //获取申报单
    $(document).on('click', '#getSbd', function () {
        checkSbWszt(function (data) {
            if (data) {
                getSbd();
            }
        }, "xxzt");

    });

    //确认申报单
    $(document).on('click', '#qrsbd', function () {
        qrsbd();
    });

    //获取缴税二维码
    $(document).on('click', '#hqjsewm', function () {
        checkSbWszt(function (data) {
            if (data) {
                getJsewm();
            }
        }, "sbzt");
    });
    //获取税票
    $(document).on('click', '#hqsp', function () {
        getSp();
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
            laytpl(liTpl).render(json, function (html) {
                $('#liTable #swxx').before(html);
            });
            // //渲染tab内容区
            var tpl = tabContentTpL.innerHTML;
            laytpl(tpl).render(json, function (html) {
                $('.layui-tab-content .swxx').before(html);
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
            if (tabxmid === xmid) {
                // 加载交易与税收tab标签页
                generateJyss(data);
            }

            form.render();
            renderSelect();
            getStateById(readOnly, formStateId, 'ycym');
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
    if (tabid === "swxx") {
        //交易税收tab
        saveBdcSlJyxx(".jyxx", "jyssTab");
        saveBdcSlFwxx(".fwxx", "jyssTab");
        saveZrzcfxx();//保存申请人


    } else {
        //受理信息tab
        saveJbxx();
        saveSjcl();
        saveBdcSlJyxx(".jyxx", "slTab");
        saveBdcSlXm(".slxm");
        saveBdcSlFwxx(".fwxx", "slTab");
        saveBdcSlDyaq(".dyaq");
        saveSqr("yrbj");//保存申请人
        saveBdcSlLzrxx(".lzxx");
    }
}

//线上反馈附件错误
function feedback() {
    $("#feedbackReason").val('');
    layer.open({
        title: '线上反馈',
        type: 1,
        area: ['430px'],
        btn: ['确定', '取消'],
        content: $('#feedback-reason'),
        yes: function (index, layero) {
            var reason = $("#feedbackReason").val();
            if (isNullOrEmpty(reason)) {
                layer.msg('请输入线上反馈原因!');
                return false;
            }
            addModel();
            getReturnData("/ycsl/online/feedback", {gzlslid: processInsId, opinion: reason}, "GET", function (data) {
                removeModal();
                layer.close(index);
                ityzl_SHOW_SUCCESS_LAYER("反馈成功。");
            }, function (err) {
                removeModal();
                delAjaxErrorMsg(err);
            });
        },
        btn2: function (index, layero) {
            layer.close(index);
        }
    });
}

//组织参数调用评价器, groupByQxdm: true 控制评价器的 URL 地址是否根据 qxdm 来分。
function evaluate() {
    getReturnData("/pjq/evaluateFdjlc", {gzlslid: processInsId, xmid: xmid, groupByQxdm: true}, "GET", function (data) {
        if (data !== null) {
            pj(data);
        }
    }, function (err) {
        removeModal();
        delAjaxErrorMsg(err);
    })
}

function pj(pjdata) {
    var url = pjdata.url + "?ywbh=" + pjdata.slbh + "&jdmc=受理" + "&blry=" + pjdata.slr + "&sqrxm=" + pjdata.qlrmc + "&sqrlxfs=" + pjdata.qlrlxfs;
    console.log(encodeURI(url));
    window.open(encodeURI(url));
}

//房屋套次信息比对
function compareFwtcxx(sqrlb, elem) {
    var $qlrbasic = $(elem).parents("tr");
    var hyxxbdjg = $qlrbasic.find("input[name='hyxxbdjg']").val();

    if (hyxxbdjg !== "1" &&hyxxbdjg !== "0") {
        ityzl_SHOW_WARN_LAYER("请先进行婚姻信息比对！");
        return false;
    }

    var sbfwtc = $qlrbasic.find("#zrzcf-sbfwtc").val();
    var sqrid = $qlrbasic.find("input[name='sqrid']").val();
    if (!isNotBlank(sbfwtc) || !isNotBlank(sqrid)) {
        ityzl_SHOW_WARN_LAYER("申报房屋套次不能为空！");
    } else {
        layer.open({
            type: 2,
            title: "房屋套次信息",
            area: ['960px', '550px'],
            content: "/realestate-accept-ui/nantong/ycsl/fwtcxx.html?sqrid=" + sqrid + "&sbfwtc=" + sbfwtc
            , end: function (index, layero) {
                return false;
            }
            , cancel: function (index, layero) {
                var resultcode = layer.getChildFrame('body', index).find("input[name='resultcode']").val();

                //比对通过
                if (resultcode === "0000") {
                    $(elem).removeClass("bdc-secondary-btn").addClass("bdc-major-btn");
                    $qlrbasic.find("input[name='fwtcbdjg']").val("1");
                } else {
                    $(elem).removeClass("bdc-major-btn").addClass("bdc-secondary-btn");
                    $qlrbasic.find("input[name='fwtcbdjg']").val("0");

                }
            }
        });

        // 调用后台生成有房无房证明PDF接口
        generateYfwfzmPdf($qlrbasic);
    }
}

// 调用后台生成有房无房证明PDF接口
function generateYfwfzmPdf($qlrbasic) {
    var qlrmc = $qlrbasic.find("#zrzcf-sqrmc").val();
    var qlrzjh = $qlrbasic.find("#zrzcf-zjh").val();
    var sqrid = $qlrbasic.find("input[name='sqrid']").val();
    var qlrxx = [{qlrmc: qlrmc, qlrzjh: qlrzjh}];
    getReturnData("/slym/jtcy/listBdcSlJtcy", {sqrid: sqrid}, "GET", function (data) {
        if (isNotBlank(data)) {
            $.each(data, function (index, value) {
                qlrxx.push({
                    qlrmc: value.jtcymc,
                    qlrzjh: value.zjh
                });
            });
        }
        console.info(qlrxx);
        $.each(qlrxx, function (index, qlr) {
            getReturnData("/slPrint/nantong/yfwfzm", {
                gzlslid: processInsId,
                qlrmc: qlr.qlrmc,
                qlrzjh: qlr.qlrzjh
            }, "GET", function (data) {
            }, function (err) {
                delAjaxErrorMsg(err);
                warnMsg("生成" + qlr.qlrmc + "有房无房证明失败")
            });
        });
    });
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

//推送一窗创建登记流程
function tsBdcDjxx() {
    addModel();
    // 推送到登记前增加一个验证功能，用于验证一些前必须要有的数据
    var data = checkBeforeTsBdcDj();
    if (data) {
        if (!data.result || data.result == "false") {
            showAlertDialog(data.msg);
            removeModal();
            return false;
        }
    }

    var bdcTsDjxxRequestDTO = {};
    bdcTsDjxxRequestDTO.jbxxid = jbxxid;
    bdcTsDjxxRequestDTO.gzlslid = processInsId;
    var bdcYcslPzDTO = {};
    //自动转发
    bdcYcslPzDTO.autoTurn = false;
    bdcYcslPzDTO.gyslbh = true;
    bdcYcslPzDTO.autoComplete = true;
    bdcTsDjxxRequestDTO.bdcYcslPzDTO = bdcYcslPzDTO;
    getReturnData("/ycsl/tsBdcDjxx", JSON.stringify(bdcTsDjxxRequestDTO), "POST", function (data) {
        removeModal();
        if (data.lczt === "1") {
            showAlertDialog("创建失败" + (isNotBlank(data.msg) ? ",失败原因为：" + data.msg : ""));
        } else if (data.lczt === "2") {
            ityzl_SHOW_SUCCESS_LAYER("创建成功,受理编号:" + data.slbh);
            layer.msg('创建成功');
            setTimeout(function () {
                parent.location.href = "/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId;
            }, 1000);
        } else if (data.lczt === "3") {
            //创建成功,转发失败
            layer.msg('创建成功，自动转发失败' + (isNotBlank(data.msg) ? ",失败原因为：" + data.msg : ""), {
                time: 1000
            }, function () {
                parent.location.href = "/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId;
            });
        } else if (data.lczt === "4") {
            ityzl_SHOW_SUCCESS_LAYER("自动转发成功,受理编号:" + data.slbh);
        } else {
            showAlertDialog("创建失败");
        }
    }, function (error) {
        delAjaxErrorMsg(error);

    })
}

/**
 * 推送到登记前的验证
 */
function checkBeforeTsBdcDj() {
    var resultData = {};
    $.ajax({
        url: getContextPath() + "/ycsl/checkBeforeTsBdcDj/" + xmid,
        type: 'get',
        async: false,
        success: function (data) {
            resultData = data;
        }
    });
    return resultData;
}

// 交税按钮事件
function payTax() {
    /*打开页面之前先判断有没有明细，如果有明细则不允许再次打开
    没有明细的则需要再判断 已推送税务状态 字段，
    0（否）则可以直接打开，并更新 已推送税务状态 字段，1（是）则提示 你已经点击后核税按钮了，是否确认再次点开核税页面？*/
    // 查询明细及交税页面的url
    //规则验证
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = "payTax";
    var gzyzParamMap = {};
    gzyzParamMap.xmid = xmid;
    gzyzParamMap.gzlslid = processInsId;
    bdcGzYzQO.paramMap = gzyzParamMap;
    gzyzCommon(2, bdcGzYzQO, function (data) {
        $.ajax({
            url: getContextPath() + "/ycsl/getTaxInfo/" + xmid,
            type: 'get',
            success: function (data) {
                if (data.hsxxmx && data.hsxxmx.length > 0) {// 有核税信息明细 则不允许再次打开
                    layer.alert('该业务已经点击过交税信息，请勿重复点击！', {title: '提示'});
                    return;
                }
                if (data.ytsswzt == "1") {// 需要提示操作
                    layer.confirm("你已经点击后核税按钮了，是否确认再次点开核税页面？", {
                        title: "提示",
                        btn: ["确认", "取消"],
                        btn1: function (index) {
                            window.open(data.url + "htbh=" + data.htbh);
                            layer.close(index);
                        },
                        btn2: function (index) {
                            return;
                        }
                    });
                } else {// 其余的状态可以直接打开
                    updateYtsswzt(xmid);
                    window.open(data.url + "htbh=" + data.htbh);
                }
            }
        })

    });

}

//推送计税信息
function tsjsxx() {
    var htdjsj = $("#jyss-htdjsj").val();
    if (!isNotBlank(htdjsj)) {
        ityzl_SHOW_WARN_LAYER("合同签订时间不允许为空");
        return false;
    }
    var fwlx = "1", beanName;
    if (fwfclx === "clf") {
        beanName = "sw_clfrwts";
        fwlx = "2";
    } else{
        beanName = "sw_zlfrwts";
        fwlx = "1";
    }
    addModel();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/yrbj/tsJsxx/"+ processInsId +"/" + beanName,
        type: "GET",
        data: {fwlx: fwlx},
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            removeModal();
            if (isNotBlank(result) && result.data.fhm == "0") {
                ityzl_SHOW_SUCCESS_LAYER("推送成功");
            } else {
                ityzl_SHOW_WARN_LAYER("推送失败：" + result.msg);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

// 更新ystswzt成 是
function updateYtsswzt(xmid) {
    $.ajax({
        url: getContextPath() + "/ycsl/updateYtsswzt/" + xmid,
        type: 'get',
        success: function (data) {

        }
    })
}

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

// 刷新页面中保存交易信息和房屋信息数据的模块数据
function refreshJyxxAndFwxx() {
    $.ajax({
        url: getContextPath() + "/ycsl",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {gzlslid: processInsId, xmid: xmid},
        success: function (data) {
            // 权利信息
            generateQlxx(data, bdcSlXm, $("#qlxxTab"));
            //加载房屋交易信息
            generateFwjyxx(data.bdcSlJyxxDO, data.bdcSlFwxxDO, bdcSlXm);
            //加载房屋信息模块
            generateFwxx(data.bdcSlFwxxDO, data.bdcSlJyxxDO);
            //加载发票信息模块
            generateFpxx(data.bdcZrfSwxxList, data.bdcSlJyxxDO);

            form.render();
            renderSelect();
            getStateById(readOnly, formStateId, 'ycym');
            form.render("select");
            disabledAddFa();
            renderDate(form);
            removeModal();
            reloadZjhYz();
        }
    });
}

function dealSpfJyxx(data) {
    console.log("关联信息：" + data);
    getReturnData("/rest/v1.0/yrbj/jyxx?xmid=" + xmid, JSON.stringify(data), "POST", function () {
        removeModal();
    }, function (error) {
        console.info(error);
        removeModal();
        delAjaxErrorMsg(e);
    }, false);
}

//处理生成交易信息
function dealSlJyxx(data) {
    getReturnData("/ycsl/jyxx/dealSpfClfJyxx?xmid=" + xmid, JSON.stringify(data), "POST", function () {
        removeModal();
    }, function (error) {
        removeModal();
        delAjaxErrorMsg(error);
    }, false)
}

// 处理申请人信息, 导入申请人权利人
function dealSqrxx(data) {
    var sqrList = [];
    if (isNotBlank(data) && isNotBlank(data.bdcSlSqr) && data.bdcSlSqr.length > 0 && data.bdcSlFwxxDOList.length > 0) {
        var bdcslFwxx = data.bdcSlFwxxDOList[0];
        if (bdcslFwxx.xzzt == "0" && bdcslFwxx.dyzt == "0") {
            //带入除了填写权利人外的权利人信息
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
            var qlrList = [];
            $.each(sqrList, function (index, slSqr) {
                var result = true;
                for (var i = 0; i < bdcSlSqrListCache.length; i++) {
                    var sqr = bdcSlSqrListCache[i].bdcSlSqrDO;
                    if (sqr.sqrlb == 1 && sqr.sqrmc == slSqr.sqrmc && sqr.zjh == slSqr.zjh) {
                        result = false;
                        break;
                    }
                }
                if (result) {
                    qlrList.push(slSqr);
                }
            })
            if (qlrList) {
                addModel();
                var url = getContextPath() + "/slym/sqr/sqrxx/save?gzlslid=" + processInsId;
                $.ajax({
                    url: url,
                    type: 'PATCH',
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(qlrList),
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
        } else {
            ityzl_SHOW_WARN_LAYER("存在限制或者抵押无法带入权利人信息");
        }
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

// 交易核验
function jyhy(){
    addModel();
    $.ajax({
        url:  getContextPath() + "/rest/v1.0/yrbj/jyhy?gzlslid=" + processInsId + "&xmid="+ xmid,
        type: 'GET',
        dataType: 'json',
        contentType: "application/json",
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("导入成功。");
            if(isNotBlank(data)){
                var sfxz = false;
                $.each(data, function(index, value){
                    if(value.XZZT == 1 || value.DYZT == 1){
                        sfxz = true;
                        return false;
                    }
                });
                if(sfxz){
                    ityzl_SHOW_WARN_LAYER("住建系统有抵押或查封信息，请核实。");
                }else{
                    ityzl_SHOW_SUCCESS_LAYER("住建无限制，请继续交易。");
                }
            }else{
                ityzl_SHOW_WARN_LAYER("调用住建交易核验接口异常，未获取到返回值信息。");
            }
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }
    });
}

// 推送住建信息
function tszj(){
    $.ajax({
        url:  getContextPath() + "/rest/v1.0/yrbj/tszjslxx?gzlslid=" + processInsId,
        type: 'GET',
        dataType: 'json',
        contentType: "application/json",
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("推送成功");
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }
    });
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

//验证比对信息
function checkBdxx(callback) {

    var spflc = false;
    if (fwfclx !== "clf") {
        spflc = true;
    }
    $.ajax({
        type: "get",
        url: getContextPath() + "/ycsl/bdxx",
        data: {xmid: xmid, spflc: spflc},
        success: function (data) {
            callback(data);
        }, error: function (e) {
            delAjaxErrorMsg(e);
        }
    });


}

/*
* 判断申报完税状态
* */
function checkSbWszt(callback, lx) {
    var result = false;

    if (lx === "xxzt") {
        var xxztdm = $("#xxztdm").val();
        //只有信息状态=40 对照字典表后=5 才可以点击获取申报单
        if (xxztdm === "5") {
            result = true;
            callback(result);
        } else {
            ityzl_SHOW_WARN_LAYER("申报失败，请转至线下办理");
            return false;
        }
    } else if (lx === "sbzt") {
        var sbztdm = $("#sbztdm").val();
        //只有申报状态=70 对照字典表后=8 才可以点击获取缴税二维码
        if (sbztdm === "8") {
            result = true;
            callback(result);
        } else {
            ityzl_SHOW_WARN_LAYER("申报失败，不能点击获取缴税信息");
            return false;
        }
    }
    return result;

}

/*
* 获取申报单
* */
function getSbd() {
    var fwlx = "1";
    if (fwfclx === "clf") {
        fwlx = "2";
    }
    var htbh = $("#htbh").val();
    if (isNullOrEmpty(htbh)) {
        ityzl_SHOW_WARN_LAYER("合同编号不可为空");
        return false;
    }
    addModel('');
    getReturnData("/slym/sw/sbdswxx", {gzlslid: processInsId, fwlx: fwlx, htbh: htbh}, "GET", function (data) {
        //获取完之后重新加载税务信息模块
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("获取成功");
        refreshYmxx();
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    })
}

/*
* 确认申报单
* */
function qrsbd() {
    var fwlx = "1";
    if (fwfclx === "clf") {
        fwlx = "2";
    }
    addModel('');
    getReturnData("/slym/sw/sbdqr", {gzlslid: processInsId, fwlx: fwlx}, "GET", function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("申报单确认成功");
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

/*
* 获取缴税二维码
* */
function getJsewm() {
    //返回结果是个二维码的base64
    var fwlx = "1";
    if (fwfclx === "clf") {
        fwlx = "2";
    }
    addModel('');
    getReturnData("/slym/sw/jsewm", {
        gzlslid: processInsId,
        htbh: $("#htbh").val(),
        fwlx: fwlx
    }, "GET", function (data) {
        removeModal()
        if (data) {
            var qlrjsewm,ywrjsewm;
            $.each(data,function(index,value){
                if(value.qlrlb == "1"){
                    qlrjsewm = value.jsewm;
                }
                if(value.qlrlb == "2"){
                    ywrjsewm = value.jsewm;
                }
            });
            if(isNotBlank(qlrjsewm)){
                var qlrewmUrl = "data:image/png;base64," + qlrjsewm;
                $(".qlrjsewm").attr('src', qlrewmUrl);
                //点击二维码图片放大事件
                $(".qlrjsewm").click(function () {
                    var _this = $(this);//将当前的pimg元素作为_this传入函数
                    imgHyzfShow("#qlrjsouterdiv", "#qlrjsinnerdiv", "#qlrjsbigimg", _this, '', $(window).width() * 0.4);
                });
            }
            if(isNotBlank(ywrjsewm)){
                var ywrewmUrl = "data:image/png;base64," + ywrjsewm;
                $(".ywrjsewm").attr('src', ywrewmUrl);
                //点击二维码图片放大事件
                $(".ywrjsewm").click(function () {
                    var _this = $(this);//将当前的pimg元素作为_this传入函数
                    imgHyzfShow("#ywrjsouterdiv", "#ywrjsinnerdiv", "#ywrjsbigimg", _this, '', $(window).width() * 0.4);
                });
            }
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })

}

/*
* 获取税票
* */
function getSp() {
    var fwlx = "1";
    if (fwfclx === "clf") {
        fwlx = "2";
    }
    var jszt = $("#jsztdm").val();
    if (isNullOrEmpty(jszt)) {
        ityzl_SHOW_WARN_LAYER("未获取到缴税状态");
        return false;
    }
    getReturnData("/slym/sw/sw/sp", {
        gzlslid: processInsId,
        jszt: jszt,
        fwlx: fwlx,
        htbh: $("#htbh").val()
    }, "GET", function (data) {
        if(isNotBlank(data) && data.success == true){
            ityzl_SHOW_SUCCESS_LAYER("税票获取成功");
        }else{
            ityzl_SHOW_WARN_LAYER("请至窗口打印税票");
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    })
}

//处理附件信息
function dealFjxx(data) {
    if (isNotBlank(data) && isNotBlank(data.htBase64) > 0) {
        var fjxx = {};
        fjxx.htbh = data.bdcSlJyxx.htbh;
        fjxx.fjnr = data.htBase64;
        fjxx.gzlslid = processInsId;
        getReturnData("/rest/v1.0/yrbj/fjxx", JSON.stringify(fjxx), "POST", function (data) {
            console.log("导入附件成功");
        }, function (xhr) {
            delAjaxErrorMsg(xhr)
        });
    }
}

/*
* 点击图片放大功能
* */

function imgHyzfShow(outerdiv, innerdiv, bigimg, _this, height, width) {
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性

    /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function () {
        var windowW = $(window).width();//获取当前窗口宽度
        var windowH = $(window).height();//获取当前窗口高度
        var realWidth = this.width;//获取图片真实宽度
        var realHeight = this.height;//获取图片真实高度
        var imgWidth, imgHeight;
        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

        if (realHeight > windowH * scale) {//判断图片高度
            imgHeight = windowH * scale;//如大于窗口高度，图片高度进行缩放
            imgWidth = imgHeight / realHeight * realWidth;//等比例缩放宽度
            if (imgWidth > windowW * scale) {//如宽度扔大于窗口宽度
                imgWidth = windowW * scale;//再对宽度进行缩放
            }
        } else if (realWidth > windowW * scale) {//如图片高度合适，判断图片宽度
            imgWidth = windowW * scale;//如大于窗口宽度，图片宽度进行缩放
            imgHeight = imgWidth / realWidth * realHeight;//等比例缩放高度
        } else {//如果图片真实高度和宽度都符合要求，高宽不变
            imgWidth = realWidth;
            imgHeight = realHeight;
        }
        $(bigimg).css("width", imgWidth);//以最终的宽度对图片缩放

        var w = (windowW * 0.4) / 2;//计算图片与窗口左边距
        var h = (windowH * 0.5) / 2;//计算图片与窗口上边距
        if (isNotBlank(width)) {
            w = width;
        }
        if (isNotBlank(height)) {
            h = height;
        }
        $(innerdiv).css({"top": h, "left": w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function () {//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}
