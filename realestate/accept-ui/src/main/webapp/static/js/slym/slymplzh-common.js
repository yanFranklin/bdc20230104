/**
 * Created by Ypp on 2019/9/23.
 * 批量组合页面通用部分js
 */

var layer, laytpl, form, table;
//初始化tab获取的内容，权利信息需要使用
var qlxx;
//页面入口
var sjclids = new Set();
//是否虚拟号
var isXndyh = false;

//第一次单击tab获取的到的权利人信息组合
var tabDefaultQlrList = [];

//判断登记原因是否变化
var sfchange = false;
var formIds = "";
var djyyforywr;
var qlrCache;
//增量初始化需要的参数
var jbxxid = "";
var processDefKey = "";
var warnLayer = "";
var tableConfig;
//判断是否首次登记，从而允许楼盘表新增
var sfscdj = false;
//保存结束后提示信息
var saveMsg = '';
//用于判断权利tab页
var className;
var sply;
// 用于判断当前页面是否需要提示税票完税的提示
var isShowYwsxx = false;
var bdcdyhList = [];

// 获取processInstanceType
var processInstanceType = $.getUrlParam('processInstanceType');
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var element = layui.element;
    form = layui.form;
    table = layui.table;
    var $ = layui.jquery;
    layer = layui.layer;
    laytpl = layui.laytpl;

    addModel();
    setTimeout("loadButtonArea('slympl')", 10);
    setTimeout(function () {
        try {
            //获取字典
            getReturnData("/bdczd", '', 'POST', function (data) {
                removeModal();
                if (isNotBlank(data)) {
                    zdList = data;
                }
            }, function (err) {
                delAjaxErrorMsg(err);
            }, false);
            //初始化
            generateTabContent();
        } catch (e) {
            removeModal();
            ERROR_CONFIRM("加载失败", e.message);
            return;
        }
    }, 20);
    $(function () {
        //监听面积单位复选框选择，单选
        form.on('checkbox(mjdw)', function () {
            $("[name='mjdw']").prop("checked", "");
            $(this).prop("checked", "checked");
            form.render('checkbox');
        });
        //cz.js按钮操作相关js
        titleShowUi();

        //监听第一次单击tab
        element.on('tab(tabFilter)', function (data) {
            if ($(this).hasClass('bdc-first-click')) {
                $(this).removeClass('bdc-first-click');
                var tabXmid = $(this).data('xmid');
                var tabDjxl = $(this).data('djxl');
                var tabQllx = $(this).data('qllx');
                // 给sfdydj赋值 控制tab展示字段  ccx 2019-10-03
                sfdydj = $(this).data('dydj');
                addModel();
                setTimeout(function () {
                    $.when(loadTabQlr(tabXmid, tabDjxl, '.layui-show'), loadPlQlxx(tabXmid), loadBdcdyh(tabDjxl, tabQllx, tabXmid)).done(function () {
                        if (edition === "nt" ||edition === "hm") {
                            loadLzrxx(tabXmid, tabDjxl, $('.layui-show'));
                        }
                        var a = setInterval(function () {
                            if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                                getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                                clearInterval(a);
                            }
                        }, 50);
                        // 根据审批来源禁用申请人
                        disabledSqrxx(sply,splyList,".sqrxx");
                    });
                }, 0);
            }
        });


        //点击提交时不为空的全部标红
        var isSubmit = true;
        var isFirst = true;
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
                    if(sfyzbt) {
                        isSubmit = false;
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
            },sfhytsfh: function (value,item) {
            var msg = checkZjhsfhytsfh(value);
            if (isNotBlank(msg)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = msg;
            }
        }
        });


        form.on("submit(saveData)", function (data) {
            addModel();
            AcceptForm.verifyGz(processInsId).done(function (yzResult) {
                removeModal();
                // 当验证数据为空时，直接进行保存。
                if (yzResult.length == 0) {
                    doSubmit();
                } else {
                    // 验证存在数据不一致时，进行忽略提示。用户点击忽略时，进行回调保存操作。
                    AcceptForm.showTsxx(yzResult, doSubmit);
                }
            }).fail(function (message) {
                console.info(message);
                showAlertDialog(message);
                removeModal();
            });
        });

        // 表单保存方法
        function doSubmit() {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                isFirst = true;
                return false;
            } else {
                addModel();
                setTimeout(function () {
                    try {
                        saveSlym();
                    } catch (e) {
                        removeModal();
                        if (e.message) {
                            showAlertDialog(e.message);
                        } else {
                            delAjaxErrorMsg(e);
                        }
                        return;
                    }
                }, 10);
                return false;
            }
        }
    });


    //监听复选框选择
    //全选
    form.on('checkbox(qxCheckbox)', function (data) {
        $('td input[name=yxTable]').each(function (index, item) {
            item.checked = data.elem.checked;
            var qxData = item.value;
            //如果是选中的则添加，否则全部删除
            if (item.checked) {
                sjclids.add(qxData)
            } else {
                sjclids.delete(qxData);
            }
        });
        form.render('checkbox');
    });
    //单个的
    form.on('checkbox(yxCheckbox)', function (data) {
        var checkedLength = $('td .yx+.layui-form-checked[lay-skin=primary]').length;
        var checkBoxLength = $('td .yx+.layui-form-checkbox[lay-skin=primary]').length;
        var qxCheckBox = $('.gray-tr input[name=qxTable]')[0];
        var sjclid = data.value;
        if (sjclids.has(sjclid)) {
            sjclids.delete(sjclid);
        } else {
            sjclids.add(sjclid);
        }
        //判断是否全选，全选的时候勾选最上面的复选框
        if (checkedLength == checkBoxLength) {
            qxCheckBox.checked = true;
        } else {
            qxCheckBox.checked = false;
        }
        form.render('checkbox');
    });

    //监听权利tab修改证件类型
    form.on('select(zjzl)', function (data) {
        if (data.value == '1') {
            $(data.elem).parents('td').next().find('input').attr('lay-verify', 'identitynew');
        } else {
            $(data.elem).parents('td').next().find('input').attr('lay-verify', '');
        }
    });

    //监听领证人修改证件类型
    form.on('select(lzrzjhFilter)', function (data) {
        var forms = data.elem.parentNode.parentNode.parentNode;
        var attrVal = $(forms).find("input[name=lzrzjh]").attr("lay-verify");
        if (data.value == '1') {
            if (isNotBlank(attrVal)) {
                if (attrVal.indexOf("identitynew") < 0) {
                    $(forms).find("input[name=lzrzjh]").attr("lay-verify", attrVal + "|identitynew");
                }
            } else {
                $(forms).find("input[name=lzrzjh]").attr("lay-verify", "identitynew");
            }
        } else {
            //移除身份证验证
            if (isNotBlank(attrVal) && attrVal.indexOf("identitynew") > -1) {
                $(forms).find("input[name=lzrzjh]").attr("lay-verify", attrVal.replace("identitynew", ""));
            }
        }
    });

    //监听抵押方式
    form.on('select(dyfs)', function (data) {
        //抵押方式为最高额抵押时，贷款方式为商业贷款
        if (data.value === "2") {
            //定位贷款方式字段
            var $dkfs = $("select[name =dkfs]");
            if ($dkfs.length > 0) {
                $dkfs.val("商业贷款");
                form.render("select");
                resetSelectDisabledCss();
            }
        }

        //监听抵押类型修改 zgzqe 和 bdbzzqse 的lay-verify属性值;
        //获取当前tab页的zgzqe和bdbzzqse对象；
        var currentTab = document.getElementsByClassName("layui-show");
        $currentTab = $(currentTab);
        var $currentZgzqe = $currentTab.find("#dyaq-zgzqe");
        var $currentBdbzzqse = $currentTab.find("#dyaq-bdbzzqse");

        //一般抵押时，zgzqe不必填，bdbzzqse必填
        if (data.value == '1' && $currentZgzqe.length > 0 && $currentBdbzzqse.length > 0) {
            $currentZgzqe[0].setAttribute("lay-verify","");
            //移除dyaq-zgzqe必填背景色和标志*
            $currentZgzqe[0].setAttribute("style","");
            $currentZgzqe.parents(".layui-inline").removeClass("bdc-not-null");
            $currentZgzqe.parents(".layui-inline").find(".required-span").remove();

            $currentBdbzzqse[0].setAttribute("lay-verify","required");
            //添加dyaq-bdbzzqse必填背景色和标志*
            $currentBdbzzqse.parents(".layui-inline").addClass("bdc-not-null");
            var requiredArr = $currentBdbzzqse.parents(".layui-inline").find(".required-span");
            if(requiredArr.length ===0) {
                $currentBdbzzqse.parents(".layui-inline").find("label").prepend("<span class=\"required-span\"><sub>*</sub></span>");
            }
        } else if ($currentZgzqe.length > 0 && $currentBdbzzqse.length > 0){
            //清空lay-verify属性值和背景，再根据表单中心配置重新设置；
            $currentZgzqe[0].setAttribute("lay-verify","");
            $currentZgzqe.parents(".layui-inline").removeClass("bdc-not-null");
            $currentZgzqe.parents(".layui-inline").find(".required-span").remove();
            $currentBdbzzqse[0].setAttribute("lay-verify","");
            $currentBdbzzqse.parents(".layui-inline").removeClass("bdc-not-null");
            $currentBdbzzqse.parents(".layui-inline").find(".required-span").remove();

            getStateById(readOnly, formStateId, 'slympl');
            fixBgm();
        }
        // form.render();

    });

    //房产交易信息合同编号字段绑定事件
    table.on('tool(bdcHtbhTable)', function (obj) {
        if (obj.event === 'openpage') {
            Fcjyxx.linkCheck(obj.data.wqxx);
        }
    });
});

//初始化tab及tab下的内容
function generateTabContent() {
    var xmxx;
    var qllx;
    var xmfb = {};
    getReturnData("/slym/xm", {processInsId: processInsId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            qllx = data.qllx;
            //面积单位为空时默认为平方米
            if (data.mjdw === null || data.mjdw === '') {
                data.mjdw = '1'
            }

            xmxx = data;
            sply = data.sply;
            if(edition ==="yc") {
                // 判断当前页面是否需要提示税票完税的提示
                showSpWsXx(sply, processInsId);
            }
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    getReturnData("/slym/ql/plzh", {processInsId: processInsId, zxlc: zxlc,cxexistDyaq:true}, 'GET', function (data) {
        if (isNotBlank(data)) {
            qlxx = data;
            bdcdyhList = qlxx[0].groupBdcdyhList;
            xmfb = data[0].bdcXmFbDO;
        }
    }, function (err) {
        console.log(err);
    }, false);
    //判断是否是虚拟单元号
    checkXndyhPl();

    var json = {
        qllx: qllx,
        qlxx: qlxx,
        zd:zdList
    };
    json['sfchangeqljbxxTab'] = sfchangeqljbxxTab;
    if (edition == 'nt' ||edition == 'hm') {
        getJdNameByTaskId();
        json['sljd'] = isSljd;
        json['qtjd'] = isQtjd;
    }

    //渲染tab页
    var liTpl = liTableTpl.innerHTML,
        liView = $('#tableUi');
    laytpl(liTpl).render(json, function (html) {
        liView.append(html);
    });
    //渲染tab内容区
    var tpl = tabContentTpL.innerHTML,
        view = document.getElementById("tabContent");
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

    //南通除受理节点，默认加载第一个权利信息
    if (sfchangeqljbxxTab || ((edition == 'nt' ||edition == 'hm') && isQtjd)) {
        $.when(loadTabQlr(qlxx[0].bdcXm.xmid, qlxx[0].bdcXm.djxl, '.layui-show'), loadPlQlxx(qlxx[0].bdcXm.xmid), loadBdcdyh(qlxx[0].bdcXm.djxl, qlxx[0].bdcXm.qllx, qlxx[0].bdcXm.xmid)).done(function () {
            loadLzrxx(qlxx[0].bdcXm.xmid, qlxx[0].bdcXm.djxl, $('.layui-show'));
            var a = setInterval(function () {
                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                    getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                    clearInterval(a);
                }
            }, 50);
        });
    }

    //日期相关
    renderDate(form, formIds);
    //给下拉框增加title属性
    renderSelect();

    //加载基本信息
    generateJbxx(xmxx, xmfb);
    //加载收件材料
    loadSjcl();
    // 记录表单第一次加载的业务数据至ES中
    addYwxxLog();
    // 更改受理页面slymplzh content高度,按钮遮盖调优
    if ($(".title-btn-area").height() > 48) {
        $(".bdc-tab-box").css({'padding-top': '66px'})
    }
    //不满足换行条件时，又遮盖了
    if (($(".content-title").width() - $(".title-btn-area").width()) < 179 && $(".title-btn-area").height() < 49){
        $(".title-btn-area .layui-btn").css("padding","0 10px");
    }

}

//判断是否是虚拟单元号
function checkXndyhPl() {
    getReturnData("/slym/xm/checkXndyh", {gzlslid: processInsId}, "GET", function (data) {
        isXndyh = data;
    }, function (error) {
        delAjaxErrorMsg(error);

    });
}

//渲染基本信息
function generateJbxx(bdcxmxx, xmfb) {
    var json = {
        bdcxmxx: bdcxmxx,
        zd: zdList,
        xmfb: xmfb,
        commonJedw: commonJedw
    };
    var tpl = jbxxTpl.innerHTML,
        view = document.getElementById('sljbXx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    renderDate(form, formIds);
    //给下拉框添加删除图标
    renderSelectClose(form);
    getStateById(readOnly, formStateId, 'slympl');
    //监听修改字段
    if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(bdcxmxx.djxl) > -1) {
        formIds = "tabContent";
        renderChange("", form, formIds);
    }
    if(isNotBlank(xmfb.sfsftg) && xmfb.sfsftg == 1){
        // 添加税费托管业务展示说明标签
        $("#sfsftg-tips").show();
    }
    renderSelect();
    disabledAddFa();
}

//获取收件材料信息,渲染到页面
var sjclNumber = 0;

/*
 * 加载领证人信息
 */
function loadLzrxx(xmid, djxl, $nowTab) {
    getReturnData("/slym/lzr/nt/lzrxx", {xmid: xmid}, 'GET', function (lzrdata) {
        var json = {
            bdclzrList: lzrdata,
            djxl: djxl,
            zd: zdList
        };
        var tpl = lzrxxTpl.innerHTML, view = $nowTab.find('#lzrxx')[0];
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        getStateById(readOnly, formStateId, 'slympl', "lzrxx");
        resetSelectDisabledCss();
    }, function (err) {
        console.log(err);
    });
}

//加载收件材料信息
//用于存放所有的收件材料id
var sjclidLists = [];

function loadSjcl() {
    addModel();
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        removeModal();
        var json = {
            bdcSlSjclDOList: data,
            zd: zdList
        };
        if (data !== null && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                sjclidLists.push(data[i].sjclid);
            }
        }
        sjclNumber = data.length;
        var tpl = sjclTpl.innerHTML,
            view = document.getElementById('sjclxx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
            form.render('checkbox');
        });

        // 判断当前页面是否需要提示税票完税的提示
        if (isShowYwsxx) {
            var spxxTip = ' <div class="qlxx-tips">提示信息：已接收到一体化推送的税票信息</div>';
            $('.sjcl-sign-btn').before(spxxTip);
        }
        form.render('select');
        getStateById(readOnly, formStateId, "slympl");
        renderSelect();
        form.render('select');
        disabledAddFa("sjclForm");
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
}

//第一次单击权利tab，获取申请人信息
function loadTabQlr(xmid, djxl, nowTab) {
    var tabXmid = $(nowTab).attr('data-xmid');
    var tabXmidList = [];
    qlxx.forEach(function (v) {
        if (v.bdcXm.xmid == tabXmid) {
            tabXmidList = v.groupXmidList;
        }
    });
    addModel();
    var param = {
        xmidList:tabXmidList.toString()
    };

    //权利人
    $.ajax({
        url: getContextPath() + "/slym/qlr/groupQlr?gzlslid="+processInsId+"&djxl="+djxl+"&qlrlb=1",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(param),
        async: false,
        success: function (data) {
            var qlrxx = [];
            if (data && data.length > 0) {
                $.each(data, function(index, value){
                    qlrxx.push(value.bdcQlrDO);
                });
                var tabIndex = $(nowTab).index();
                tabDefaultQlrList[tabIndex] = qlrxx;
                qlrCache = qlrxx;
            }
            removeModal();
            generateQlrxx(qlrxx, "sqrxx", djxl, nowTab);
            //重新加载抵押人查封机关信息
            reloadDyrAndCfjg(xmid, nowTab);
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

    //义务人
    $.ajax({
        url: getContextPath() + "/slym/qlr/groupywr?gzlslid="+processInsId+"&djxl="+djxl,
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(param),
        async: false,
        success: function (data) {
            removeModal();
            generateQlrxx(filterQlrZh(data), "qlrTable", djxl, nowTab);
            //加载权利信息模块部分信息
            reloadQlxxForYwr(data, nowTab);
            form.render();
            getStateById(readOnly, formStateId, 'slympl');
            fixBgm();
            //合肥联系电话加密显示
            toEncryptClass('dhjm');
            renderSelect();
            disabledAddFa("sqrForm");
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    if (edition === 'hm') {
        renderBackgroundColor('bdc-qlr');
    }
    fixBgm();
}

//1、义务人为企业时：相同名称视为同一企业只保留一个 2、义务人为个人时：按名称+证件号过滤，保留新证件号的
function filterQlrZh(data) {
    if (data.length == 0) {
        return data;
    }
    var filteQlrArray = [];
    //清理相同义务人缓存数据
    var cacheMap = {};
    $.each(data, function (index, val) {
        var bdcQlrDO = val.bdcQlrDO;
        // 人员类别不是义务人时，不进行过滤
        if (bdcQlrDO.qlrlb != "2") {
            filteQlrArray.push(val);
            return true;
        }
        // 对义务人进行数据过滤
        if ("2" == bdcQlrDO.qlrlb) {
            // 义务人类型不为企业和个人时，不过滤
            if (2 != bdcQlrDO.qlrlx && 1 != bdcQlrDO.qlrlx) {
                filteQlrArray.push(val);
                return true;
            }
            // 义务人类型为企业
            if (bdcQlrDO.qlrlx == 2) {
                var isRepeat = bdcQlrDO.qlrmc in cacheMap;
                if (!isRepeat) {
                    cacheMap[bdcQlrDO.qlrmc] = val;
                }
            }
            // 义务人类型为个人
            if (bdcQlrDO.qlrlx == 1 && !compareQlrRepeat(bdcQlrDO, cacheMap)) {
                var key = bdcQlrDO.qlrmc + convertIdCard18To15(bdcQlrDO.zjh);
                cacheMap[key] = val;
            }
        }
    });
    // 将去重的人员数据缓存数据添加到过滤数组中
    $.each(cacheMap, function (key, value) {
        filteQlrArray.push(value);
    });
    return filteQlrArray;
}

// 比较义务人是否重复, 重复返回true，不重复返回false
function compareQlrRepeat(bdcQlrDO, cacheMap) {
    // 将18位转15位进行重复校验,若存在则重复，不存在则不重复。
    if (isNotBlank(bdcQlrDO.zjh) && bdcQlrDO.zjh.length == 18) {
        var mapKey15 = bdcQlrDO.qlrmc + convertIdCard18To15(bdcQlrDO.zjh);
        if (!(mapKey15 in cacheMap)) {
            return false;
        }
    } else {
        var mapKey = bdcQlrDO.qlrmc + bdcQlrDO.zjh;
        if (!(mapKey in cacheMap)) {
            return false;
        }
    }
    return true;
}

//根据传递的数据，组织申请人信息到页面
function generateQlrxx(data, id, djxl, nowTab) {
    var tabXmid = $(".layui-show").data('xmid');
    qlxx.forEach(function (v) {
        if (v.bdcXm.xmid == tabXmid) {
            djyyforywr = v.bdcXm.djyy;
            if (djyyforywr != null && djyyforywr != undefined) {
                djyyforywr = djyyforywr.indexOf('继承') > -1;
            }
        }
    });
    var json = {
        bdcQlrDOList: data,
        zd: zdList,
        qlrLength: 0,
        djxl: djxl,
        djyyforywr: djyyforywr
    };
    if (id == 'qlrTable') {
        //无数据隐藏
        if (data && data.length > 0) {
            $(nowTab + " #qlrTable .bdc-table-none").remove();
        }
        $(nowTab).find('.bdc-ywr-tr').remove();
        var tabQlrLength = $(nowTab + ' #' + id).find('tbody tr').length - 1;
        json.qlrLength = tabQlrLength;
        //渲染义务人
        var tpl = ywrTpl.innerHTML,
            view = $(nowTab + ' #' + id).find('tbody');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.append(html);
        });
    } else {
        var tpl = sqrTpl.innerHTML,
            view = $(nowTab + ' #' + id);
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.html(html);
        });
    }
    form.on('select(zjzl)', function (data) {
        changeqlrlxByZjzl(data);
    })
    form.on('select(qlrlx)', function (data) {
        changeZjzlByQlrlx(data);
    })
    addzjhYz();

}

/**
 * 处理权利信息中义务人相关信息
 */
function reloadQlxxForYwr(ywrdata, nowTab) {
    //抵押人
    var dyr = $(nowTab + " #dyaq-dyr");
    //供役地权利人
    var gydqlr = $(nowTab + " #dyiq-gydqlr");
    if ((dyr != null &&dyr.length !==0) || (gydqlr != null &&gydqlr.length !==0)) {
        //权利表需要展现义务人的字段，需要拼接所有义务人
        var ywr = dealYwr(ywrdata);
        if (dyr !== undefined && dyr != null) {
            $(dyr).val(ywr);
            dyr.title = ywr;
        }
        if (gydqlr !== undefined && gydqlr != null) {
            $(gydqlr).val(ywr);
            gydqlr.title = ywr;
        }
    }
}

/**
 * 权利人，义务人发生改变后同步修改权利表权利人义务人信息
 * @param xmid
 * @param
 */
function reloadDyrAndCfjg(xmid, nowTab) {
    if (isNotBlank(xmid) && isNotBlank(nowTab)) {
        var xydqlr = $(nowTab + " #dyiq-xydqlr");
        if (xydqlr != null &&xydqlr.length !==0) {
            var bdcxm;
            getReturnData("/slym/xm/xx", {xmid: xmid}, 'GET', function (data) {
                bdcxm = data
            }, function (err) {
                console.log(err);
            }, false);

            //需役地权利人
            $(xydqlr).val(bdcxm.qlr);
            xydqlr.title = bdcxm.qlr;
        }
        var cfjg = document.getElementById('cf-cfjg');
        var jfjg = document.getElementById('cf-jfjg');
        var qllx = $($(".layui-this").find("input[name='qllx']")[0]).val();
        if (qllx === "98") {
            var bdcCfjgQO = {};
            bdcCfjgQO.gzlslid = processInsId;
            if (zxlc === "true") {
                bdcCfjgQO.isjf = true;
            } else {
                bdcCfjgQO.iscf = true;
            }
            var bdccf;

            getReturnData("/slym/ql/updateCfjgOrJfjg?xmid=" + xmid, JSON.stringify(bdcCfjgQO), 'POST', function (data) {
                bdccf = data;
                if (zxlc === "true" && jfjg != null) {
                    $(jfjg).val(bdccf.jfjg);
                    jfjg.title = bdccf.jfjg;
                } else if (cfjg != null) {
                    $(cfjg).val(bdccf.cfjg);
                    cfjg.title = bdccf.cfjg;
                }
            }, function (err) {
                removeModal();
                delAjaxErrorMsg(err);
            });
        }
    }
}


//第一次单击权利tab，获取权利信息数据
function loadPlQlxx(xmid, $nowTab) {
    var qlxxShow = {};
    qlxx.forEach(function (v) {
        if (v.bdcXm.xmid == xmid) {
            qlxxShow = v;
        }
    });
    className = qlxxShow.className;
    if(isNotBlank($nowTab)){
        buildQlxx(qlxxShow, $nowTab);
    }else {
        buildQlxx(qlxxShow, $('.layui-show'));
    }
}

//保存后，重新获取权利信息数据
function reloadPlQlxx(xmid, $nowTab) {
    var qlxxShow = {};
    getReturnData("/slym/ql/plzh", {processInsId: processInsId, zxlc: zxlc,cxexistDyaq:true}, 'GET', function (data) {
        if (isNotBlank(data)) {
            qlxx = data;
            qlxx.forEach(function (v) {
                if (v.bdcXm.xmid == xmid) {
                    qlxxShow = v;
                }
            });
            buildQlxx(qlxxShow, $nowTab);
        }
    }, function (err) {
        console.log(err);
    }, false);
}


//重新加载权利信息
function commonReloadQlxx() {
    loadPlQlxx(qlxx[0].bdcXm.xmid)
}

/**
 * ywrgroup 义务人分组对象
 * 处理义务人
 */
function dealYwr(ywrgroup) {
    var ywr = "";
    if (ywrgroup != null && ywrgroup.length > 0) {
        for (var i = 0; i < ywrgroup.length; i++) {
            var ywrObj = ywrgroup[i];
            if (ywrObj && isNotBlank(ywrObj.bdcQlrDO.qlrmc)) {
                ywr += ywrObj.bdcQlrDO.qlrmc + " ";
            }
        }

    }
    return ywr;
}

//根据获取到的权利信息数据，渲染内容到页面
function buildQlxx(bdcSlQlxxym, $nowTab) {
    if (isNotBlank(bdcSlQlxxym)) {
        var qllx = bdcSlQlxxym.qllx;
        var djxldjyyList = {};
        //获取登记原因list
        if (isNotBlank(bdcSlQlxxym.bdcXm.djxl)) {
            //在权利信息最上面添加登记原因、是否分别持证、原产权证号
            getReturnData("/slym/xm/queryDjxlDjyy", {djxl: bdcSlQlxxym.bdcXm.djxl}, "GET", function (data) {
                if (isNotBlank(data)) {
                    djxldjyyList = data;
                }
            }, function (error) {
                delAjaxErrorMsg(error);
            }, false);
        }

        //申请分别持证为空默认为是
        if(bdcSlQlxxym &&bdcSlQlxxym.bdcXm &&bdcSlQlxxym.bdcXm.sqfbcz ==null){
            bdcSlQlxxym.bdcXm.sqfbcz =1;
        }
        // 处理存在多次抵押提示
        if (bdcSlQlxxym) {
            var dyaBdcdyh = bdcSlQlxxym.dyaBdcdyh;
            if (dyaBdcdyh) {
                // 用于展示
                var dyaBdcdyhZs = dyaBdcdyh;
                if (dyaBdcdyh.indexOf(',') !== -1) {
                    dyaBdcdyhZs = dyaBdcdyh.split(',')[0] + '等';
                }
                bdcSlQlxxym.dyaBdcdyhZs = dyaBdcdyhZs;
            }
        }
        var qlIndex = $nowTab.index();
        //抵押，预抵押，异议，查封，地役权展现在外面的权利字段
        if (qllx === parseInt(commonDyaq_qllx) || bdcSlQlxxym.dydj || qllx === 97 || qllx === 98 || qllx === 19) {
            var json = {
                bdcSlQlxxym: bdcSlQlxxym,
                zd: zdList,
                dkfs: dkfs,
                djxldjyy: djxldjyyList,
                qlIndex:qlIndex
            };
            var bdcdyfwlx = "";
            if (isNotBlank(bdcSlQlxxym.bdcQlP) && isNotBlank(bdcSlQlxxym.bdcQl.bdcdyfwlx) && bdcSlQlxxym.bdcQl.bdcdyfwlx === 1) {
                bdcdyfwlx = bdcSlQlxxym.bdcQl.bdcdyfwlx;
            }
            var qllxTpl = document.getElementById(bdcSlQlxxym.tableName + bdcdyfwlx);
            if (isNotBlank(qllxTpl)) {
                var tpl = qllxTpl.innerHTML, view = $nowTab.find('#bdcdyqlxx');
                laytpl(tpl).render(json, function (html) {
                    view.html(html);
                });
                renderDate(form, formIds);
                form.render();
                renderSelect();
                //给下拉框添加删除图标
                renderSelectClose(form);
                getStateById(readOnly, formStateId, 'slympl', 'qlxxTab');
                form.render();
                disabledAddFa('qlxxTab');
            }


            //监听修改字段
            if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(bdcSlQlxxym.bdcXm.djxl) > -1) {
                formIds = "tabContent";
                renderChange("", form, formIds);
            }
        } else if (qllx === 96 || qllx === 4 || qllx === 6 || qllx === 8) {
            var tabXmidList =bdcSlQlxxym.groupXmidList;
            var tpl =null;
            if(tabXmidList &&tabXmidList.length ===1){
                var bdcdyfwlx = "";
                if (isNotBlank(bdcSlQlxxym.bdcQlP) && isNotBlank(bdcSlQlxxym.bdcQl.bdcdyfwlx) && bdcSlQlxxym.bdcQl.bdcdyfwlx === 1) {
                    bdcdyfwlx = bdcSlQlxxym.bdcQl.bdcdyfwlx;
                }
                var qllxTpl = document.getElementById(bdcSlQlxxym.tableName + bdcdyfwlx);
                if(isNotBlank(qllxTpl)) {
                    tpl = qllxTpl.innerHTML;
                }
            }
            if(tpl ==null){
                var jyxxTpl = document.getElementById("jyxxTpl");
                tpl =jyxxTpl.innerHTML;
            }
            //展现交易信息字段
            var json = {
                zd: zdList,
                bdcSlQlxxym: bdcSlQlxxym,
                djxldjyy: djxldjyyList,
                qlIndex:qlIndex
            };

            if (isNotBlank(tpl)) {
                var view = $nowTab.find('#bdcdyqlxx');
                laytpl(tpl).render(json, function (html) {
                    view.html(html);
                });
                renderDate(form, formIds);
                renderSelect();
                //给下拉框添加删除图标
                renderSelectClose(form);
                getStateById(readOnly, formStateId, 'slympl', 'qlxxTab');
                form.render();
                disabledAddFa("jyxxTpl");

            }

            //判断页面是否存在交易信息字段，显示即查询
            if ($nowTab.find(".jyxx").length > 0 && isNotBlank(bdcSlQlxxym.bdcXm.xmid)) {
                var formFilter = $nowTab.find('.bdc-qlxx-form').attr('lay-filter');
                getReturnData("/ycsl/jyxx", {xmid: bdcSlQlxxym.bdcXm.xmid}, "GET", function (data) {
                    //表单赋值
                    form.val(formFilter, data);
                    renderDate(form, formIds);
                }, function (error) {
                    delAjaxErrorMsg(error);
                });
            }
        } else {
            //其余产权不展现权利信息详细
            $nowTab.find("#qlxxdiv").remove();
        }
        sfchange = false;
        //合肥判断登记原因是否变化
        if (edition !== 'nt' &&edition !== 'hm') {
            form.on('select(djyy)', function (data) {
                console.log($(data.elem).data('djyy')); //得到select原始DOM对象
                console.log(data.value); //得到被选中的值
                console.log(data.othis); //得到美化后的DOM对象
                var ydjyy = $(data.elem).data('djyy');
                var xdjyy = data.value;
                if (ydjyy === xdjyy) {
                    sfchange = false;
                } else {
                    sfchange = true;
                }
            })
        }
        if (edition == 'nt' ||edition == 'hm') {
            buildQlxxUse(bdcSlQlxxym, $nowTab);
            getSfhz(bdcSlQlxxym.bdcXm,$nowTab);
            if (sdqghDjxlList.indexOf(bdcSlQlxxym.bdcXm.djxl) > -1) {
                loadSqdghxx(bdcSlQlxxym.bdcSdqghDOList);
            }
        }
    }
}

//加载bdc_Csh_fwkg_sl 表的sfhz（是否换证）字段
<!--bdc_Csh_fwkg_sl 表的sfhz（是否换证）字段  sfhz  0: 否（不换证 继承） 1：是 （换证 不继承）-->
function getSfhz(xmxx,$nowTab){
    if($nowTab.find("#sfjcybdcqzhDiv").length ===0 ||$nowTab.find("#sfjcybdcqzhDiv").css("style") =="none"){
        //如果页面不存在该元素，则不加载收费状态
        return ;
    }

    $.ajax({
        url: '/realestate-accept-ui/slym/xm/getBdcCshFwkgSlDO?xmid=' + xmxx.xmid,
        type: 'GET',
        dataType: "json",
        success: function (data) {
            if (data && data.sfhz !== null && data.sfhz !== undefined) {
                $("input[name='sfjcybdcqzh" + xmxx.djxl + "'][value=" + data.sfhz + "]").prop("checked", "checked");
                form.render();
            }
        },
        error: function (error) {
            delAjaxErrorMsg(error);

        }
    });
}


//第一次单击权利tab，获取不动产单元信息表格
function loadBdcdyh(djxl, tabQllx, tabXmid) {
    var tabXmxx = {};
    var tabQszt = "";
    var tabXmidList = [];
    qlxx.forEach(function (v) {
        if (v.bdcXm.xmid == tabXmid) {
            tabXmxx = v.bdcXm;
            tabQszt = tabXmxx.qszt;
            tabXmidList = v.groupXmidList;

        }
    });
    var isSearch = true;
    $(document).keydown(function (event) {
        if (event.keyCode == 13 && isSearch) { //绑定回车
            $(".layui-show #searchBdcdy").click();
        }
    });
    // //判断回车操作
    $('.bdc-tab-box').on('focus', '.layui-laypage-skip .layui-input', function () {
        isSearch = false;
    }).on('blur', '.layui-laypage-skip .layui-input', function () {
        isSearch = true;
    });
    //获取流程对应的规则用途作为选择项
    getReturnData("/slym/xm/queryZdFwytByGzlslid", {gzlslid: processInsId}, "GET", function (data) {
        //清空
        $('.layui-show .bdc-ghyt').empty();
        $('.layui-show .bdc-ghyt').append(new Option("请选择", ""));
        $.each(data, function (index, item) {
            //防止出现对比权籍后，规划用途字典项与登记不一致出现空的情况
            if (item !== null) {
                $('.layui-show .bdc-ghyt').append(new Option(item.mc, item.dm));// 下拉菜单里添加元素
            }
        });
    }, function (error) {
        console.log("用途下拉框获取失败")
    }, false);
    form.render("select");
    //获取权利信息表头
    var unitTableTitle = getQlCols(tabQllx);
    var url = getContextPath() + '/slym/ql/listQlByPageJson';

    // 查询参数
    var data = {
        sortParameter: "xmid ASC"
    };
    if (zxlc === "true") {
        //注销流程
        data["sfyql"] = true;
    }

    // 根据当前查询参数，获取所有的单元信息，用于数据导出
    data["gzlslid"] = processInsId;
    data["qllx"] = tabQllx;
    data["xmidList"] = tabXmidList;

    if (edition == 'nt' ||edition == 'hm') {
        //根据权利类型，判断是否要处理合计信息
        dealHjxx(tabQllx, djxl);
        qlxx.forEach(function (v) {
            if (v.bdcXm.xmid == tabXmid) {
                //不动产单元总数
                $(".layui-show #bdcdyCount").text(v.bdcdyCount);

            }
        });
    }

    //提交表单
    $(".layui-show #searchBdcdy").click(function () {
        var bdcdyArray = $(".layui-show .bdcdyForm").serializeArray();
        bdcdyArray.forEach(function (item) {
            data[item.name] = item.value;
        });
        $.when(tableReload('xmid' + djxl, data, url)).done(function () {
            if ((tabQllx === 4 || tabQllx === 6 || tabQllx === 8)) {
                if (edition == 'nt' ||edition == 'hm') {
                    //根据权利类型，判断是否要处理合计信息
                    dealHjxx(tabQllx, djxl);
                    qlxx.forEach(function (v) {
                        if (v.bdcXm.xmid == tabXmid) {
                            //不动产单元总数
                            $(".layui-show #bdcdyCount").text(v.bdcdyCount);

                        }
                    });
                }
            }
            var a = setInterval(function () {
                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                    getStateById(readOnly, formStateId, "slympl");
                    clearInterval(a);
                }
            }, 50);
        });
        return false;
    });
    //重置操作
    $(".layui-show #reset").on('click', function () {
        $('.layui-show .bdc-form-search input').val('');
        $('.layui-show .bdc-form-search select').val('');
        form.render('select');
    });

    var bdcdyhLimit = 10;
    if (edition == 'nt' ||edition == 'hm') {
        bdcdyhLimit = 50;
    }
    tableConfig = {
        id: 'xmid' + djxl,
        url: url,
        method: 'post',
        contentType: 'application/json',
        where: data,
        limit: bdcdyhLimit,
        toolbar: "#toolbarBdcdyh",
        defaultToolbar: ['filter'],
        cols: [unitTableTitle]
        , parseData: function (res) {
            res.content.forEach(function (v) {
                v.yxmid = queryYxmid(v.xmid);
            });
        }
        , done: function () {
            if (edition == 'nt' ||edition == 'hm') {
                //附属设施复选框标蓝
                changeCheckboxBackground1([{name: 'bdc-change-blue', color: '#1D87D1'}]);
            }
            var reverseList = ['zl'];
            reverseTableCell(reverseList, 'xmid');

            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

            if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
            } else {
                if (edition === "nt" ||edition === "hm") {
                } else {
                    $('.bdc-form-div .layui-show .layui-table-body').height($('.bdc-form-div .layui-show .bdc-table-box').height() - 131);
                    $('.bdc-form-div .layui-show .layui-table-fixed .layui-table-body').height($('.bdc-form-div .layui-show .bdc-table-box').height() - 131 - 17);
                }

            }
            $('.bdc-table-box').on('mouseenter', 'td', function () {
                if ($(this).text() && ($(this).attr("data-field") === "zl" || $(this).attr("data-field") === "zldz")) {
                    $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>')
                }
                $('.layui-table-grid-down').on('click', function () {
                    setTimeout(function () {
                        $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                    }, 20);
                });
            });
            var a = setInterval(function () {
                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                    getStateById(readOnly, formStateId, "slympl");
                    fixBgm();
                    clearInterval(a);
                }
            }, 50);
        }
    };

    //加载表格
    loadDataTablbeByUrl('.layui-show #bdcdyTable', tableConfig);


    renderSearchInput();
    //头工具栏事件
    table.on('toolbar(bdcdyTable' + djxl + ')', function (obj) {
        var layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === "plxg") { //批量修改
            var checkStatus = table.checkStatus(obj.config.id);
            var checkData = checkStatus.data;
            var xmids = [];
            if (checkData.length === 0) {
                if (edition === "nt" ||edition === "hm") {
                    xmids = tabXmidList;
                }
                showConfirmDialog("提示", "未选择数据，默认修改全部数据?", "openPlxg", "'" + xmids + "'", "", "");
            } else {
                for (var i = 0; i < checkData.length; i++) {
                    var xmid = checkData[i].xmid;
                    xmids.push(xmid);
                }
                openPlxg(xmids);
            }

        } else if (layEvent === 'export') {
            exportExcel(obj.config.cols[0]);
        } else if (layEvent === "add") {
            addBdcdy();
        } else if (layEvent === "delete") {
            var bdcGzYzQO = {};
            bdcGzYzQO.zhbs = "SLYM_SCBDCDY";
            var gzyzParamMap = {};
            gzyzParamMap.gzlslid = processInsId;
            bdcGzYzQO.paramMap = gzyzParamMap;
            gzyzCommon(2, bdcGzYzQO, function (data) {
                //删除不动产单元
                var checkStatus = table.checkStatus(obj.config.id);
                var checkData = checkStatus.data;
                var xmids = [];
                if (checkData.length === 0) {
                    showAlertDialog("未选择数据")
                } else {
                    for (var i = 0; i < checkData.length; i++) {
                        var xmid = checkData[i].xmid;
                        xmids.push(xmid);
                    }
                    getReturnData("/slxxcsh/deleteBdcdy?xmids=" + xmids+"&gzlslid="+processInsId, {}, 'DELETE', function (data) {
                        var djxl = $(".layui-show").data('djxl');
                        //删除项目后检查收费信息数据
                        getReturnData("/sf/checksfxx?xmids=" + xmids, {
                            gzlslid: processInsId,
                            djxl: djxl
                        }, 'GET', function (data) {

                        }, function (err) {
                            delAjaxErrorMsg(err);
                        });
                        ityzl_SHOW_SUCCESS_LAYER("删除成功");
                        parent.parent.location.reload();
                    }, function (err) {
                        delAjaxErrorMsg(err);
                    }, false);
                }
            });
        } else if (layEvent === "lpb") {
            if ((tabQllx === 4 || tabQllx === 6 || tabQllx === 8)) {
                var tabDjlx = $(".layui-show").data('djlx');
                if (tabDjlx === 100) {
                    //房屋首次允许新增
                    sfscdj = true;
                } else {
                    sfscdj = false;
                }
            } else {
                sfscdj = false;
            }
            //楼盘表
            lpb();

        } else if (layEvent === "pldjls") { //批量登记历史
            var tabQllx = $(".layui-show").data('qllx');
            window.open("/realestate-register-ui/view/lsgx/pldjls.html?gzlslid=" + processInsId + "&qllx=" + tabQllx);
        } else if (layEvent === "gwc") {
            openSlymGwc();
        }


        return false;


    });
    //监听单元格事件
    table.on('tool(bdcdyTable' + djxl + ')', function (obj) {
        var data = {},
            $this = $(obj.tr[0]).find('td[data-field="fj"] .layui-table-cell');
        data.qlid = obj.data.qlid;
        if (obj.event === 'editFj') {
            layer.prompt({
                formType: 2
                , title: '附记'
                , value: obj.data.fj
                , btn: ['保存', '取消']
            }, function (value, index) {
                layer.close(index);
                data.fj = value;
                //中文括号转为英文括号
                if (isNotBlank(data.fj)) {
                    data.fj = data.fj.replace('（', '(');
                    data.fj = data.fj.replace('）', ')');
                }
                //这里一般是发送修改的Ajax请求
                if (isNotBlank(className) && isNotBlank(data.qlid)) {
                    getReturnData("/slym/ql?className=" + className, JSON.stringify(data), 'PATCH', function (data) {
                        ityzl_SHOW_SUCCESS_LAYER("附记保存成功！");
                    }, function (err) {
                        delAjaxErrorMsg(err);
                    }, false);
                } else {
                    ityzl_SHOW_WARN_LAYER("未获取到权利！");
                }
                //同步更新表格和缓存对应的值
                if (isNotBlank(obj.data.fj)) {
                    obj.update({
                        fj: data.fj
                    });
                } else {
                    $this.html('<span>' + data.fj + '</span>');
                }
            });
        }
    });
    //监听滚动事件
    var scrollTop = 0,
        scrollLeft = 0;
    var tableTop = 0, tableLeft = 0;
    var $nowBtnMore = '';
    $(window).on('scroll', function () {
        scrollTop = $(this).scrollTop();
        scrollLeft = $(this).scrollLeft();

        if ($nowBtnMore != '') {
            if (tableTop + 26 + $nowBtnMore.height() < document.body.offsetHeight) {
                $nowBtnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
            } else {
                $nowBtnMore.css({top: tableTop - scrollTop - $nowBtnMore.height(), left: tableLeft - 30});
            }
        }
    });

    //点击表格中的更多
    $('.bdc-table-box').on('click', '.bdc-more-btn', function (event) {
        tableTop = $(this).offset().top;
        tableLeft = $(this).offset().left;
        event.stopPropagation();
        $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
        var $btnMore = $(this).siblings('.bdc-table-btn-more');
        if ($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight) {
            $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
        } else {
            $btnMore.css({top: tableTop - scrollTop - $btnMore.height(), left: tableLeft - 30});
        }
        $('.bdc-table-btn-more').hide();
        $btnMore.show();
    });
    //点击更多内的任一内容，隐藏
    $('.bdc-table-btn-more a').on('click', function () {
        $(this).parent().hide();
    });
    //点击页面任一空白位置，消失
    $('body').on('click', function () {
        $('.bdc-table-btn-more').hide();
    });
}

// 导出Excel
// 通过隐藏form提交方式，避免ajax方式不支持下载文件
function exportExcel(cols, allData) {
    var exportCol = {};
    for (var index in cols) {
        if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar && cols[index].field != 'cz') {
            exportCol[cols[index].title] = cols[index].field
        }
    }
    var tempForm = $("<form>");
    tempForm.attr("id", "exportFrom");
    tempForm.attr("style", "display:none");
    tempForm.attr("target", "export");
    tempForm.attr("method", "post");
    tempForm.attr("action", '/realestate-accept-ui/slym/ql/exportBdcdyXxByGzlslid');

    var dataInput = $("<input>");
    dataInput.attr("type", "hidden");
    dataInput.attr("name", "exportCol");
    dataInput.attr("value", encodeURIComponent(JSON.stringify(exportCol)));
    tempForm.append(dataInput);
    var dataInputGzlslid = $("<input>");
    dataInputGzlslid.attr("type", "hidden");
    dataInputGzlslid.attr("name", "gzlslid");
    dataInputGzlslid.attr("value", processInsId);
    tempForm.append(dataInputGzlslid);

    var djxl = $(".layui-this").data("djxl");
    if (isNotBlank(djxl)) {
        var djxlInput = "<input type='hidden' name='djxl' value='" + djxl + "'/>";
        tempForm.append($(djxlInput));
    }

    tempForm.on("submit", function () {
    });
    tempForm.trigger("submit");
    $("body").append(tempForm);
    tempForm.submit();
    $("exportFrom").remove();
    return false;
}

//查看原项目ID
function queryYxmid(xmid) {
    var yxmid = "";
    $.ajax({
        url: getContextPath() + "/slym/ql/queryYxmid",
        type: 'GET',
        data: {xmid: xmid},
        async: false,
        success: function (data) {
            yxmid = data;
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return yxmid;
}

//打开批量修改页面
function openPlxg(xmids) {
    //打开前先清除缓存数据
    sessionStorage.removeItem('plxg_xmids');
    //数据过多,存入缓存
    sessionStorage.setItem('plxg_xmids', xmids);
    var tabXmid = $(".layui-show").data('xmid');
    var tabDjxl = $(".layui-show").data('djxl');
    var tabQllx = $(".layui-show").data('qllx');
    var index = layer.open({
        type: 2,
        title: "批量修改",
        area: ['1300px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: getContextPath() + "/view/slym/plxg.html?processInsId=" + processInsId + "&xmid=" + tabXmid + "&formStateId=" + formStateId + "&zxlc=" + zxlc + "&djxl=" + tabDjxl,
        cancel: function () {
            $.when(loadBdcdyh(tabDjxl, tabQllx, tabXmid)).done(function () {
                reloadPlQlxx(tabXmid, $(".layui-show"));
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                        clearInterval(a);
                    }
                }, 50);
            });
        }
    });
    layer.full(index);
}


//新增权利人展示
function addQlr(qllx, xmid, djxl) {
    var dydj = false;
    //判断是否是抵押类登记
    getReturnData("/slym/ql/checkDydj", {xmid: xmid}, 'GET', function (data) {
        dydj = data;
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    var url = getContextPath() + "/view/slym/qlr.html?xmid=" + xmid + "&lclx=" + lclx + "&processInsId=" + processInsId + "&formStateId=" + formStateId + "&qllx=" + qllx + "&zxlc=" + zxlc + "&djxl=" + djxl;
    if (dydj) {
        url = url + "&dydj=true";
    }
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        fixed: false, //不固定
        title: "新增申请人",
        content: url,
        btnAlign: "c"
    });
    form.render();
    disabledAddFa();
}

//权利人详情展示
function showQlr(qlrid, xmid, qlrlb, djxl) {
    sessionStorage.removeItem('qlridList');
    sessionStorage.removeItem('xmidList');
    var dydj = false;
    //判断是否是抵押类登记
    getReturnData("/slym/ql/checkDydj", {xmid: xmid}, 'GET', function (data) {
        dydj = data;
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    addModel();
    var qllx = $($("input[name='qllx']")[0]).val();
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var url = getContextPath() + "/view/slym/qlr.html?processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=" + lclx + "&xmid=" + xmid + "&formStateId=" + formStateId + "&qllx=" + qllx + "&readOnly=" + readOnly + "&zxlc=" + zxlc + "&djxl=" + djxl;
        url=dealWithUrl(url,sply,splyList);
        //标题
        var title;
        if (dydj) {
            url = url + "&dydj=true";
            if (qlrlb === "1") {
                title = "抵押权人详细信息";
            } else {
                title = "抵押人详细信息";
            }

        } else {
            title = "申请人详细信息";
        }
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: title,
            content: url,
            btnAlign: "c"
        });
        removeModal();
        form.render();
        renderDate(form);
        disabledAddFa();
    })
}

//义务人详情展示
function showYwr(qlrid, xmid, qlrlb, otherqlridList, otherXmidList, djxl) {
    if (otherqlridList != undefined) {
        if (isNotBlank(otherqlridList)) {
            sessionStorage.qlridList = otherqlridList + ',' + qlrid;
        } else {
            sessionStorage.qlridList = qlrid;
        }
        if (isNotBlank(otherXmidList)) {
            sessionStorage.xmidList = otherXmidList + ',' + xmid;
        } else {
            sessionStorage.xmidList = xmid;
        }
    } else {
        sessionStorage.removeItem('qlridList');
        sessionStorage.removeItem('xmidList');
    }
    var dydj = false;
    //判断是否是抵押类登记
    getReturnData("/slym/ql/checkDydj", {xmid: xmid}, 'GET', function (data) {
        dydj = data;
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    addModel();
    var qllx = $($("input[name='qllx']")[0]).val();
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var url = getContextPath() + "/view/slym/qlr.html?processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=" + lclx + "&xmid=" + xmid + "&formStateId=" + formStateId + "&qllx=" + qllx + "&readOnly=" + readOnly + "&zxlc=" + zxlc + "&djxl=" + djxl;
        url = dealWithUrl(url,sply,splyList);
        //标题
        var title;
        if (dydj) {
            url = url + "&dydj=true";
            if (qlrlb === "1") {
                title = "抵押权人详细信息";
            } else {
                title = "抵押人详细信息";
            }

        } else {
            title = "申请人详细信息";
        }
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: title,
            content: url,
            btnAlign: "c"
        });
        removeModal();
        form.render();
        renderDate(form);
        disabledAddFa();
    })
}

//单击表格内 单元信息  不动产单元信息详情页面
function showBdcdy(xmid) {
    var index = layer.open({
        type: 2,
        title: "不动产单元信息",
        area: ['960px', '390px'],
        fixed: false, //不固定
        content: getContextPath() + "/view/slym/bdcdy.html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&zxlc=" + zxlc + "&processInstanceType=" + processInstanceType,
        btnAlign: "c",
        success: function () {
        }
    });
    layer.full(index);
}

//单击表格内 权利信息
function showQl(xmid, qllx, bdcdyfwlx, sfyql) {
    if (xmid !== "" && xmid !== null) {
        qllx = parseInt(qllx);
        bdcdyfwlx = parseInt(bdcdyfwlx);
        var qllxym = getQlxxYm(qllx, bdcdyfwlx);
        var url;
        //展示原权利，不可编辑
        if (sfyql) {
            url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=true" + "&isCrossOri=false";
        } else {
            url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&isCrossOri=false" + "&processInstanceType=" + processInstanceType;
        }
        var index = layer.open({
            type: 2,
            area: ['1150px', '600px'],
            fixed: false, //不固定
            title: "权利信息",
            maxmin: true,
            content: url,
            btnAlign: "c",
            zIndex: 2147483647,
            success: function () {
            },
            cancel: function () {
                //页面关闭刷新单元信息列表和批量权利信息页面
                var $nowTab = $(".layui-show");
                var tabDjxl = $(".layui-show").data('djxl');
                var tabQllx = $(".layui-show").data('qllx');
                var tabXmid = $(".layui-show").data('xmid');
                $.when(loadBdcdyh(tabDjxl, tabQllx, tabXmid), reloadPlQlxx(tabXmid, $nowTab)).done(function () {
                    var a = setInterval(function () {
                        if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                            getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                            clearInterval(a);
                        }
                    }, 50);
                });

            }
        });
        layer.full(index);
    } else {
        ityzl_SHOW_INFO_LAYER("无原权利信息")
    }
}

//单击表格内 更多 原权利信息
//根据当前项目的xmid找到上一手原权利并展示
function openYqlxx(xmid) {
    $.ajax({
        url: getContextPath() + "/slym/ql/yql",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid},
        success: function (data) {
            if (data !== null) {
                var bdcXm = data.bdcXm;
                showQl(bdcXm.xmid, bdcXm.qllx, bdcXm.bdcdyfwlx, true);
            } else {
                ityzl_SHOW_INFO_LAYER("无原权利信息");
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

}

//单击表格内 更多 附属设施
function fwfsss(xmid) {
    $.ajax({
        url: getContextPath() + "/slym/fwfsss/list/xm",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid},
        success: function (data) {
            if (data.length > 0) {
                showFwfsss(xmid);
            } else {
                ityzl_SHOW_INFO_LAYER("无房屋附属设施");
            }


        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

//打开房屋附属设施页面
function showFwfsss(xmid) {
    layer.open({
        type: 2,
        title: "房屋附属设施信息",
        area: ['960px', '600px'],
        fixed: false, //不固定
        content: getContextPath() + "/view/slym/fwfsss.html?xmid=" + xmid,
        btnAlign: "c",
        success: function () {
        }
    });

}

//单击表格内 更多 权籍附件
//查看权籍附件
function showLpbFj() {
    getReturnData("/rest/v1.0/slym/lpbFjUrlByLc", {processInsId: processInsId}, "GET", function (data) {
        var index = layer.open({
            type: 2,
            title: "权籍附件",
            area: ['1150px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: data
        });
        layer.full(index);
    }, function (error) {
        delAjaxErrorMsg(error);

    });


}

//单击表格内 更多 证书预览
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
        $("#dzzzxx").hide();
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
        $("#dzzzxx").hide();
        $("#jyxx").show();
        setUiWidth($(this), $("#jyxx"));
    });


    $(".dzzz-button").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#moreBtn").hide();
        $("#jyxx").hide();
        $("#dzzzxx").show();
        setUiWidth($(this), $("#dzzzxx"));
    });

    //共享接口点击
    $(".gxjk-button").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#signXx").hide();
        $("#dzzzxx").hide();
        $("#gxjkxx").show();
        setUiWidth($(this), $("#gxjkxx"));
    });

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取商品房交易信息
     */
    $('#querySpfJyxx').click(function (e) {
        getSpfJyxx();
    });
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取存量房交易信息
     */
    $('#queryClfbaJyxx').click(function (e) {
        getClfJyxx();
    });

    /**
     * @author <a href ="mailto:caolu@gtmap.cn">caolu</a>
     * @description 获取存量房备案信息,海安
     */
    $('#queryNtClfbaJyxx').click(function (e) {
        console.log("获取存量房备案信息(南通)");
        getNtClfJyxx();
    });

    /**
     * @author <a href ="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 获取商品房房备案信息(南通)
     */
    $('#queryNtSpfbaJyxx').click(function (e) {
        console.log("获取商品房房备案信息(南通)");
        getNtSpfBaJyxx();
    });

    $(".print-btn li").click(function () {
        $("#print").hide();
    });

    $(".jyxx-btn li").click(function () {
        $("#jyxx").hide();
    });

    $(".dzzz-button li").click(function () {
        $("#dzzzxx").hide();
    });

    $(".gxjk-button li").click(function () {
        $("#gxjkxx").hide();
    });
    $(".sqs-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $(".title-ui").hide();
        $("#sqs").show();
        setUiWidth($(this), $("#sqs"));
    });

    $(".xwbl-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $(".title-ui").hide();
        if (qlxx) {
            var dyaq = false, same = true;
            $.each(qlxx, function (index, value) {
                if (index == 0) {
                    dyaq = value.dydj;
                    return true;
                }
                if (dyaq != value.dydj) {
                    same = false;
                }
            });
            if (same) {
                if (dyaq) {
                    printData('dyaxwblpl');
                    console.log("打印抵押询问笔录")
                } else {
                    printData('grxwblpl');
                    console.log("打印产权询问笔录");
                }
            } else {
                $("#xwbl").show();
                setUiWidth($(this), $("#xwbl"));
            }
        } else {
            $("#xwbl").show();
            setUiWidth($(this), $("#xwbl"));
        }
    });

    $(".more-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $(".title-ui").hide();
        $("#moreBtn").show();
        setUiWidth($(this), $("#moreBtn"));
    });

    $(".sqs-btn li").click(function () {
        $("#sqs").hide();
    });
    $(".xwbl-btn li").click(function () {
        $("#xwbl").hide();
    });
    $(".more-btn li").click(function () {
        $("#moreBtn").hide();
    });

    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'print') {
                return;
            }
            elem = elem.parentNode;
        }
        $(".title-ui").hide();
    });

    //获取税票
    $('#querySwspFj').click(function (e) {
        console.log("获取税票");
        querySwspFj();
    });
}

function setUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    var w = buttonElement.innerWidth();
    uiElement.css({top: X + 32, right: $('body').width() - Y - w - 15});
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
        var countWidth = (textNumber + 2) * 13;
        if (uiWidth < countWidth) {
            uiWidth = countWidth;
        }
    }
    uiElement.css({"min-width": w, "width": uiWidth + 20});
}

//附件上传(批量上传,新增人像采集）
function scfjqb() {
    getReturnData("/sjcl/folder", {gzlslid: processInsId}, 'GET', function (data) {
        if (isNullOrEmpty(data)) {
            layer.msg("人像采集文件夹生成失败");
        }
    }, function (err) {
        delAjaxErrorMsg(err)
    }, false);
    var sjclArrayPllc = $(".sjcl").serializeArray();
    updateAllSjcl(sjclArrayPllc, "");

}

//删除权利人
function deleteQlr(qlrid, sxh, qlrlb, djxl, xmid) {
    var url = "/slym/qlr/plzh?processInsId=" + processInsId + "&qlrid=" + qlrid + "&sxh=" + sxh + "&djxl=" + djxl;
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
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
                deleteQlrLzr(qlrid, djxl);
                loadLzrxx(xmid, djxl, $('.layui-show'));
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
function deleteYwr(qlridlist, sxh, xmid, qlrid, otherXmidList, djxl) {
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
    qlrList = qlridlist.split(",");
    var getXmid = xmidList.split(",");
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
                loadLzrxx(xmid,djxl,$('.layui-show'));
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

function deleteQlrLzr(qlrid, djxl) {
    var url = '/slym/lzr/hf/delete?qlrid=' + qlrid + '&gzlslid=' + processInsId + '&djxl=' + djxl;
    getReturnData(url, '', 'DELETE', function (data) {
        console.log('删除领证人成功');
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

function queryQllx(qllx) {
    if (isNotBlank(zdList.qllx)) {
        for (var i = 0; i < zdList.qllx.length; i++) {
            var qllxZd = zdList.qllx[i];
            if (qllx == qllxZd.DM) {
                qllx = qllxZd.MC;
                break;
            }
        }
    }
    return qllx;
}

function queryZdzhyt(zdzhyt) {
    if (isNotBlank(zdList)) {
        for (var i = 0; i < zdList.tdyt.length; i++) {
            var tdytZd = zdList.tdyt[i];
            if (zdzhyt == tdytZd.DM) {
                zdzhyt = tdytZd.MC;
                break;
            }
        }
    }
    return zdzhyt;
}

function queryDzwyt(dzwyt) {
    if (isNotBlank(zdList.fwyt)) {
        for (var i = 0; i < zdList.fwyt.length; i++) {
            var dzwytZd = zdList.fwyt[i];
            if (dzwyt == dzwytZd.DM) {
                dzwyt = dzwytZd.MC;
                break;
            }
        }
    }
    return dzwyt;
}

function queryGhyt(ghyt) {
    if (isNotBlank(zdList.fwyt)) {
        for (var i = 0; i < zdList.fwyt.length; i++) {
            var ghytZd = zdList.fwyt[i];
            if (ghyt == ghytZd.DM) {
                ghyt = ghytZd.MC;
                break;
            }
        }
    }
    return ghyt;
}

//保存基本信息中 备注、面积单位   权利信息里面含有class为xmxx的内容
function saveXmxx() {
    var bz = $("#bz").val();

    var mjdw = '1';
    $("input:checkbox[name='mjdw']:checked").each(function () {
        mjdw = $(this).val();
    });
    // 转移登记一并申请默认为否
    var zydjybsq = 0;
    $("input:radio[name='zydjybsq']:checked").each(function () {
        zydjybsq = $(this).val();
    });

    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        var tabXmidList = [];
        qlxx.forEach(function (v) {
            if (v.bdcXm.xmid == tabXmid) {
                tabXmidList = v.groupXmidList;
            }
        });
        if (!isNotBlank(tabDjxl)) {
            continue;
        }
        var bdcXmData = {};
        var bdcXmArray = $('.layui-tab-item:nth-child(' + i + ') .bdcxm');
        bdcXmArray.serializeArray().forEach(function (item, index) {
            if (item.name.indexOf('sqfbcz') != -1) {
                bdcXmData['sqfbcz'] = item.value;
            } else {
                bdcXmData[item.name] = item.value;
            }
        });
        bdcXmData.bz = bz;
        if (isNotBlank(mjdw)) {
            bdcXmData.mjdw = mjdw;
        }
        //  批量流程，项目内多幢情况，权利信息增加可编辑坐落字段，同步更新项目表
        var $zl = $('.layui-tab-item:nth-child(' + i + ') #qlxxdiv').find("input[name='zl']");
        if ($zl.length > 0) {
            bdcXmData.zl = $zl.val();
        }
        bdcXmData.zydjybsq = zydjybsq;
        var bdcDjxxUpdateQO = {};
        var whereMap = {};
        whereMap.gzlslid = processInsId;
        whereMap.xmids = tabXmidList;
        bdcDjxxUpdateQO.whereMap = whereMap;
        bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmData);

        getReturnData("/slym/xm/updateBatchBdcXm", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
            if ((edition !== 'nt' &&edition !== 'hm')&& sfchange) {
                reCreateSjcl();
            }
        }, function (err) {
            throw err;
        }, false);
    }
}

//权利人保存
function saveQlr() {
    //是否已执行保存权利人
    var saveQlr =false;
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        if (isNotBlank(tabXmid)) {
            var qlrArrayPllc = $(".layui-tab-item:nth-child(" + i + ") .qlr").serializeArray();
            if (qlrArrayPllc.length != 0) {
                saveAllQlr(qlrArrayPllc, tabDjxl, tabXmid, tabDefaultQlrList[i - 1], i);
                saveQlr=true;
            } else {
                //页面获取的权利人数据为空可能是设置了不可编辑，或者是没有数据，验证数据库中是否存在权利人
                var qlmc = $('.layui-tab-title li')[i - 1].innerText;
                var result = checkQlrsl(processInsId, tabXmid);
                if (!result) {
                    throw err = new Error(qlmc + '无权利人，请新增！');
                }
            }
        }
    }
    if(saveQlr) {
        loadQlr();
    }
}

function saveNtLzr() {
    if (edition === "nt" ||edition === "hm") {
        for (var i = 1; i <= $('.layui-tab-item').length; i++) {
            var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
            var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
            if (isNotBlank(tabXmid)) {
                var lzrArrayPllc = $(".layui-tab-item:nth-child(" + i + ") .lzxx").serializeArray();
                //南通保存lzrxx
                if (lzrArrayPllc.length !== 0) {
                    saveLzr(lzrArrayPllc, tabDjxl, tabXmid);
                }
            }
        }
        //刷新领证人，防止重复新增
        loadLzr();
    }
}

// 南通 保存是否换证
function saveSfhz() {
    if (edition === "nt" ||edition === "hm") {
        for (var i = 1; i <= $('.layui-tab-item').length; i++) {
            var $nowTab = $('.layui-tab-item:nth-child(' + i + ')');
            if($nowTab.find("#sfjcybdcqzhDiv").length ===0 ||$nowTab.find("#sfjcybdcqzhDiv").css("style") =="none"){
                //如果页面不存在该元素，则不加载收费状态
                continue ;
            }
            var tabXmid = $nowTab.data('xmid');
            qlxx.forEach(function (v) {
                if (v.bdcXm.xmid == tabXmid) {
                    var sfhz = $("input[name='sfjcybdcqzh"+v.bdcXm.djxl+"']:checked").val();
                    if (sfhz) {
                        $.ajax({
                            url: '/realestate-accept-ui/slym/xm/sfhz',
                            data: {gzlslid: processInsId, sfhz: sfhz, xmid: v.bdcXm.xmid, lclx: lclx},
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
            });
        }

    }
}

function saveLzr(lzrArray, tabDjxl, tabXmid) {
    var lzrList = [];
    var lzr = {};
    for (var m = 0; m < lzrArray.length; m++) {
        var name = lzrArray[m].name;
        lzr[name] = lzrArray[m].value;
        if ((m + 1) % 6 === 0) {
            lzrList.push(lzr);
            lzr = {};
        }
    }
    if (isNotBlank(lzrList)) {
        var url = "/slym/lzr/nt/lzrxx/plzh?gzlslid=" + processInsId + "&djxl=" + tabDjxl;
        getReturnData(url, JSON.stringify(lzrList), "POST", function (data) {
        }, function (xhr) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }, false)
    }
}

function saveAllQlr(qlrArray, djxl, xmid, defaultQlrArr, index) {
    var qlrList = [];
    var qlr = defaultQlrArr[0];
    var qlrnum = 0;
    var gyfs = "";
    var ywrnum = 0;
    var ywrgyfs = "";
    var qlrmc = "";
    var ywrmc = "";

    var trIndex = 0;

    for (var j = 0; j < qlrArray.length; j++) {
        var name = qlrArray[j].name;
        qlr[name] = qlrArray[j].value;

        // 如果是合肥版本就对申请人中的名称和证件号进行删除空格的处理
        if(edition === "hefei") {
            if (name == 'qlrmc' || name == 'zjh') {
                if(!isNullOrEmpty(qlr.qlrmc)) {
                    qlr.qlrmc = deleteWhitespace(qlr.qlrmc, 'all');
                }
                if (!isNullOrEmpty(qlr.zjh)) {
                    qlr.zjh = deleteWhitespace(qlr.zjh, 'all');
                }
            }
        }

        // 以xmid为每一组权利人的起始数据，作为分割依据
        if ((j + 1 < qlrArray.length && qlrArray[j + 1].name === 'xmid') || j + 1 == qlrArray.length) {
            if (qlr.qlrlb === "1") {
                qlrnum++;
                gyfs += qlr.gyfs + ",";
                if (edition == 'nt' ||edition == 'hm') {
                    qlrmc += qlr.qlrmc + ",";
                    //判断是否为小微企业
                    getXwqyxx(qlr.qlrmc, function (data) {
                        if (data != null && isNotBlank(data.jgmc)) {
                            //更新权利人类型为小微企业
                            qlr.qlrlx = 98;
                            saveMsg += qlr.qlrmc + "为小微企业，免收登记费 ";
                        }
                    })
                } else {
                    savePlzhLzr(qlr, djxl);
                }

            }
            if (qlr.qlrlb == '2') {
                ywrnum++;
                ywrgyfs += qlr.gyfs + ",";
                ywrmc += qlr.qlrmc + ",";
            }
            if (edition == 'common') {
                //将证件号中小写字母改为大写
                toUpperClass(qlr, ["zjh", "dlrzjh", "frzjh", "lzrzjh"]);
            }
            qlrList.push(qlr);
            qlr = {};
            trIndex++;
            qlr = defaultQlrArr[trIndex];
        }
    }

    //判断是否存在权利人(当页面的权利人数据设置disabled属性后获取不到值，改为从数据库获取)
    var result = checkQlrsl(processInsId, xmid);
    if (!result) {
        throw err = new Error('无权利人，请新增！');
    }
    //验证权利人共有方式
    if (!checkGyfs(gyfs, qlrnum, xmid)) {
        throw err = new Error('权利人共有方式不正确，请检查！');
    }

    if (edition != 'nt' &&edition != 'hm') {
        // 处理权利比例数据，去除小数点之前多余的 0
        $.each(qlrList, function(index, value){
            value.qlbl = replaceBeforePointZero(value.qlbl);
        });

        if (!checkAddGybl(qlrList)) {
            throw err = new Error('共有比例不正确，请检查！');
        }
    }


    //验证权利人共有方式
    if (!checkGyfs(ywrgyfs, ywrnum)) {
        alert("义务人共有方式不正确，请检查!");
    }

    var url = "/slym/qlr/list/plzh?processInsId=" + processInsId + "&xmid=" + xmid + "&djxl=" + djxl;

    if (isNotBlank(qlrList)) {
        getReturnData(url, JSON.stringify(qlrList), 'PATCH', function (data) {
        }, function (err) {
            throw err;
        }, false);
    }
}

function savePlzhLzr(bdcQlrData, djxl) {
    var lzrList = [];
    var bdclzr = {};
    if (isNotBlank(bdcQlrData.dlrmc)) {
        bdclzr.qlrid = bdcQlrData.qlrid;
        bdclzr.xmid = bdcQlrData.xmid;
        bdclzr.lzrmc = bdcQlrData.dlrmc;
        bdclzr.lzrzjzl = bdcQlrData.dlrzjzl;
        bdclzr.lzrzjh = bdcQlrData.dlrzjh;
        bdclzr.lzrdh = bdcQlrData.dlrdh;
    } else if (isNotBlank(bdcQlrData.qlrmc)) {
        bdclzr.qlrid = bdcQlrData.qlrid;
        bdclzr.xmid = bdcQlrData.xmid;
        bdclzr.lzrmc = bdcQlrData.qlrmc;
        bdclzr.lzrzjzl = bdcQlrData.zjzl;
        bdclzr.lzrzjh = bdcQlrData.zjh;
        bdclzr.lzrdh = bdcQlrData.dh;
    }
    lzrList.push(bdclzr);
    var url = "/slym/lzr/hf/lzrxx/plzh?gzlslid=" + processInsId + "&qlrid=" + bdcQlrData.qlrid + "&djxl=" + djxl + "&xmid=" + bdcQlrData.xmid;
    getReturnData(url, JSON.stringify(lzrList), 'PATCH', function (data) {

    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    })
}

//交易信息保存
function saveJyxx() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var $nowTab = $('.layui-tab-item:nth-child(' + i + ')');
        var tabDjxl = $nowTab.data('djxl');
        var tabXmid = $nowTab.data('xmid');
        if (isNotBlank(tabXmid)) {
            var tabXmidList = [];
            qlxx.forEach(function (v) {
                if (v.bdcXm.xmid == tabXmid) {
                    tabXmidList = v.groupXmidList;
                }
            });
            var bdcJyData = {};
            var bdcJyArray = $('.layui-tab-item:nth-child(' + i + ') .jyxx');
            if (bdcJyArray.length != 0) {
                bdcJyArray.serializeArray().forEach(function (item, index) {
                    bdcJyData[item.name] = item.value;
                });
                if (!$.isEmptyObject(bdcJyData)) {
                    getReturnData("/ycsl/jyxx/list?processInsId=" + processInsId + "&djxl=" + tabDjxl + "&xmids=" + tabXmidList.join(","), JSON.stringify(bdcJyData), 'PATCH', function (data) {
                        console.log("保存交易信息成功")
                    }, function (err) {
                        throw err;
                    }, false);
                }
                //刷新权利信息模块,防止交易信息重复新增
                reloadPlQlxx(tabXmid, $nowTab);
            }

        }
    }
}

//权利信息保存
function saveQlxx() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabClassName = $('.layui-tab-item:nth-child(' + i + ')').data('classname');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        if (!isNotBlank(tabClassName)) {
            continue;
        }
        var bdcDjxxUpdateQO = {};
        var whereMap = {};
        whereMap.gzlslid = processInsId;
        var tabXmidList = [];
        qlxx.forEach(function (v) {
            if (v.bdcXm.xmid == tabXmid) {
                tabXmidList = v.groupXmidList;
            }
        });
        whereMap.xmids = tabXmidList;
        if (zxlc === "true") {
            whereMap.sfyql = true;
        }
        bdcDjxxUpdateQO.whereMap = whereMap;
        bdcDjxxUpdateQO.className = tabClassName;

        var bdcQlData = {};
        var bdcQlArray = $('.layui-tab-item:nth-child(' + i + ') .qlxx');
        //需要同步项目表与权利表登记原因
        var $djyy = $('.layui-tab-item:nth-child(' + i + ')').find("select[name='djyy']");
        var tabqllx = $('.layui-tab-item:nth-child(' + i + ')').data('qllx');
        if ($djyy.length > 0) {
            var djyy = $djyy.val();
            if (zxlc !== "true" && tabqllx !== 98 && tabqllx !== 97 && tabqllx !== 94) {
                bdcQlData.djyy = djyy;
            } else if (zxlc === "true") {
                if (tabqllx === parseInt(commonDyaq_qllx) || tabqllx === 19) {
                    //注销抵押原因/注销地役原因
                    bdcQlData.zxdyyy = djyy;
                } else if (tabqllx === 97) {
                    //注销异议原因
                    bdcQlData.zxyyyy = djyy;
                }
            }
        }
        if (bdcQlArray.length !== 0 || isNotBlank(bdcQlData)) {
            bdcQlArray.serializeArray().forEach(function (item, index) {
                bdcQlData[item.name] = item.value;
            });
            //合肥版本抵押权 bdbzzqse和 zgzqe 保存逻辑
            if (edition === "hefei") {
                if (tabqllx === parseInt(commonDyaq_qllx)) {
                    var dyfs = bdcQlData.dyfs;
                    if (dyfs) {
                        if (dyfs === "1") {
                            //一般抵押存储 被担保主债权额字段
                            bdcQlData.zgzqe = '';
                        }
                        if (dyfs === "2") {
                            //最高额抵押存储 最高债权额字段
                            bdcQlData.zgzqe = bdcQlData.bdbzzqse;
                            bdcQlData.bdbzzqse = '';
                        }
                    }
                }
                if (tabqllx === parseInt(commonDyaq_qllx) || tabqllx === 96) {
                    //预告和抵押权 担保范围处理
                    if (isNullOrEmpty(bdcQlData.dbfw)) {
                        bdcQlData.dbfw = '/';
                    }
                }
            }
            if (!$.isEmptyObject(bdcQlData)) {
                bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcQlData);
                getReturnData("/slym/ql/updateBatchBdcQl", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {

                }, function (err) {
                    throw err;
                }, false);
            }
        }
    }
}


/**
 * 打印不动产单元列表
 * @returns {boolean}
 */
function printBdcdyList() {
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/bdcdy/" + processInsId + "/bdcdyList/xml";
    var modelUrl = bdcdyListModelUrl;
    print(modelUrl, dataUrl, false);

    // 禁止提交后刷新
    return false;
}

//记录由EMS推送
function markEmsPush() {
    getReturnData("/slym/yjxx/mark", {
        gzlslid: processInsId,
        slbh: $("#sjbh").val()
    }, 'GET', function (data) {
        console.info(data);
        ityzl_SHOW_SUCCESS_LAYER(data);
    }, function (err) {
        throw err;
    });
}

// 修改收费状态为已核验
function tswwjf() {
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/modifySfzt",
        type: 'GET',
        dataType: 'json',
        data: {
            gzlslid: processInsId
        },
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("修改成功！");
        },
        error: function (err) {
            delAjaxErrorMsg(err);
        }
    });
}

//刷新受理页面权利人列表--主要用于权利人新增，删除，修改后刷新页面上所有的权利人列表
function loadQlr() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        if (isNotBlank(tabXmid)) {
            getReturnData("/slym/qlr/checkAndchangeGyfs?lclx=plzh&xmid=" + tabXmid + "&processInsId=" + processInsId, {}, "POST", function () {
                loadTabQlr(tabXmid, tabDjxl, '.layui-tab-item:nth-child(' + i + ')');
            }, function (error) {
                delAjaxErrorMsg(error);
            }, false);
        }

    }
}

//刷新领证人信息
function loadLzr() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        var $nowTab = $('.layui-tab-item:nth-child(' + i + ')');
        if (isNotBlank(tabXmid)) {
            loadLzrxx(tabXmid, tabDjxl, $nowTab);
        }
    }
}

//关闭panel
function cancelEdit() {
    layer.closeAll();
}

function getQlrswxx(zjh, qlrmc, xmid) {

    getReturnData("/slym/qlr/getQlrswxx", {
        "zjh": zjh,
        "qlrmc": qlrmc,
        slbh: $("#sjbh").val(),
        xmid: xmid
    }, 'GET', function (data) {
        if (data) {
            if (data.length === 0) {
                ityzl_SHOW_INFO_LAYER("未死亡");
            } else {
                ityzl_SHOW_INFO_LAYER("确认死亡");
            }
        } else {
            ityzl_SHOW_INFO_LAYER("未查询到相关信息")
        }

    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

//同步权籍数据
function realsynchLpbDataToLc() {
//规则验证权籍状态，是否可以同步
    //批量组合流程 同步权籍数据按钮
    //合肥：验证权籍库fw_ljz\fw_hs表的lczt，若lczt=0则不允许同步权籍
    getReturnData("/rest/v1.0/slym/synchLpbDataToLc?processInsId=" + processInsId, '', 'PATCH', function (data) {
        for (var i = 2; i <= $('.layui-tab-item').length; i++) {
            var $nowTab = $('.layui-tab-item:nth-child(' + i + ')');
            var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
            reloadPlQlxx(tabXmid, $nowTab);

        }
        ityzl_SHOW_SUCCESS_LAYER("同步数据成功");
        removeModal();


    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
}

function synchLpbDataToLc() {
    tbGzyz();
}

function tbGzyz() {
    var paramMap = {};
    paramMap.bdcdyhList = bdcdyhList;
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = "XYZQJTBSJ";
    bdcGzYzQO.paramMap = paramMap;

    gzyzCommon(2,bdcGzYzQO,function (data) {
        console.log("验证通过");
        realsynchLpbDataToLc();
    });
}

//合肥 规则验证，验证权籍数据是否可以同步到不动产
function tbqjGzyz() {
    var sftb = true;
    var paramMap = {};
    paramMap.bdcdyhList = bdcdyhList;
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = "XYZQJTBSJ";
    bdcGzYzQO.paramMap = paramMap;

    $.ajax({
        url: getContextPath() + '/bdcGzyz/qtyz',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcGzYzQO),
        async: false,
        success: function (tsdata) {
            if (tsdata.length > 0) {
                // showTsxx(tsdata, ppbs, bdcdyh, tdxmid);
                sftb = false;
            }

        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
            removeModal();
        }
    });
    return sftb;

}

function getQlrdyswjcr(cardNo, xmid) {
    getReturnData("/slym/qlr/getQlrdyswjcr", {
        "cardNo": cardNo,
        slbh: $("#sjbh").val(),
        xmid: xmid
    }, 'GET', function (data) {
        if (data != null && data != undefined) {
            var json = {
                jcrs: data
            };
            generateQlrdyswjcr(json)
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

//生成第一继承人
function generateQlrdyswjcr(json) {
    var tpl = dyswjcrTpl.innerHTML;
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        layer.open({
            type: 1
            , offset: 'auto'
            , id: 'layerDemoAuto'
            , content: "<div style='padding: 20px;'>" + html + "</div>"
            , btn: '关闭'
            , btnAlign: 'c' //按钮居中
            , shade: 0 //不显示遮罩
            , title: '继承人'
            , area: ['800px', '400px']
            , yes: function () {
                layer.closeAll();
            }
        });
    });
}

//楼盘表
function lpb() {
    addModel();
    var qjgldm ="";
    if(qlxx &&qlxx.length >0 &&qlxx[0].bdcXmFbDO != null){
        qjgldm =qlxx[0].bdcXmFbDO.qjgldm;
    }
    getReturnData("/slym/lpb", {processInsId: processInsId,qjgldm:qjgldm}, "GET", function (data) {
        removeModal();
        if (data && data.length > 0) {
            var url;
            if (data.length === 1) {
                url = "/building-ui/lpb/view?code=getbdcdyh&fwDcbIndex=" + data[0].fwDcbIndex + "&gzlslid=" + processInsId+"&qjgldm="+qjgldm;
            } else {
                url = "/realestate-accept-ui/view/lpb/lpbList.html?gzlslid=" + processInsId+"&qjgldm="+qjgldm;
            }
            var index = layer.open({
                type: 2,
                title: "楼盘表",
                area: ['1150px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content: url
            });
            layer.full(index);
        } else {
            showAlertDialog("未找到楼盘表");
        }

    }, function (error) {
        delAjaxErrorMsg(error);

    })
}

//单击楼盘表，勾选弹窗内的复选框，点击 确定 按钮，调用当前方法
function xzbdcdyhCallBack(data) {
    if (!sfscdj) {
        //非首次登记不允许新增
        ityzl_SHOW_WARN_LAYER("当前流程不允许选择楼盘表新增！");
        return false;

    }
    layer.closeAll();
    if (data && data.length > 0) {
        addModel();
        //获取创建所需参数
        getReturnData("/bdcdyh/queryParams", {gzlslid: processInsId}, "GET", function (paramdata) {
            processDefKey = paramdata.processDefKey;
            jbxxid = paramdata.jbxxid;
            //验证并增量初始化
            checkBdcdyh(data, paramdata.processDefKey, paramdata.jbxxid);

        }, function (error) {
            delAjaxErrorMsg(error);
        }, false);
    }
}

//规则验证
function checkBdcdyh(bdcdydata, processDefKey, jbxxid) {
    //组织规则验证参数
    var selectDataList = [];
    for (var i = 0; i < bdcdydata.length; i++) {
        var selectData = bdcdydata[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.processDefKey = processDefKey;
        selectDataList.push(bdcGzYzsjDTO);
    }
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = processDefKey + "_XZBDCDY";
    bdcGzYzQO.paramList = selectDataList;
    $.ajax({
        url: getContextPath() + '/bdcGzyz',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcGzYzQO),
        success: function (data) {
            if (data.length > 0) {
                //展示限制信息
                showXzxx(data);
                //将选择不动产单元data放入，方便后续获取
                sessionStorage.bdcdydata = JSON.stringify(bdcdydata);
            } else {
                //添加不动产单元
                addBdcdyh(bdcdydata, processDefKey, jbxxid);
            }

        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });


}

//添加不动产单元
function addBdcdyh(bdcdydata, processDefKey, jbxxid) {
    var selectDataList = [];
    for (var i = 0; i < bdcdydata.length; i++) {
        var selectData = bdcdydata[i];
        var bdcSlYwxxDTO = {};
        bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlr;
        bdcSlYwxxDTO.qjid = selectData.qjid;
        bdcSlYwxxDTO.dyhcxlx = "1";
        bdcSlYwxxDTO.lx = "HS";
        bdcSlYwxxDTO.sfzlcsh = 1;
        selectDataList.push(bdcSlYwxxDTO);
    }
    var bdcCshSlxmDTO = {};
    bdcCshSlxmDTO.bdcSlYwxxDTOList = selectDataList;
    bdcCshSlxmDTO.gzldyid = processDefKey;
    bdcCshSlxmDTO.jbxxid = jbxxid;
    $.ajax({
        url: getContextPath() + "/addbdcdyh",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcCshSlxmDTO),
        success: function (data) {
            zlcshSelectedXxSingle(jbxxid, processDefKey);
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

//增量初始化
function zlcshSelectedXxSingle(jbxxid, processDefKey) {
    $.ajax({
        url: getContextPath() + '/slxxcsh/zlcsh',
        type: 'GET',
        dataType: 'json',
        data: {jbxxid: jbxxid, gzldyid: processDefKey,gzlslid:processInsId},
        success: function (data) {
            removeModal();
            var tabXmid = $(".layui-show").data('xmid');
            var tabDjxl = $(".layui-show").data('djxl');
            var tabQllx = $(".layui-show").data('qllx');
            loadBdcdyh(tabDjxl, tabQllx, tabXmid);
            loadTabQlr(tabXmid, tabDjxl, '.layui-show');
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });

}

//展现限制信息
function showXzxx(yzResult) {
    removeModal();
    if (yzResult.length > 0) {
        loadTsxx(yzResult);
        layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
            var $ = layui.jquery,
                layer = layui.layer;
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
                    $('.layui-layer-ico .layui-layer-close .layui-layer-close1').on('click', function () {
                        layer.close(warnLayer);
                        generate();
                    })
                },
                cancel: function () {
                    layer.close(warnLayer);
                    generate();
                }
            });
        });
    }

}

//验证信息忽略回调函数
function removeTsxxCallBack() {
    if (sessionStorage.bdcdydata != undefined) {
        var bdcdydata = JSON.parse(sessionStorage.bdcdydata);
        addBdcdyh(bdcdydata, processDefKey, jbxxid);
    }
}

//受理页面-点击保存
function saveSlym() {
    $.when(saveSdqGhxx(), saveXmxx(), saveFbPlzh(),saveSjcl(), saveNtLzr(), saveQlxx(), saveSfhz(), saveQlr(), saveJyxx()).done(function () {
        if (edition == "nt" || edition == 'hm') {
            //南通受理页面保存同步保存收费数据
            saveSf();
            /**
             * @param
             * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
             * @description 受理页面保存时回写领证日期到平台
             * @date : 2020/1/15 10:15
             */
            setLzrqToPortal();
        }
        removeModal();
        if (!isNotBlank(saveMsg)) {
            ityzl_SHOW_SUCCESS_LAYER("保存成功");
        } else {
            ityzl_SHOW_WARN_LAYER(saveMsg);
        }
        saveMsg = '';
    })
}


function saveFbPlzh() {
    if (edition == "nt" || edition == 'hm') {
        saveXmfbPl(".xmfb", '');
    }else {
        for (var i = 1; i <= $('.layui-tab-item').length; i++) {
            var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
            var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
            var tabXmidList = [];
            qlxx.forEach(function (v) {
                if (v.bdcXm.xmid == tabXmid) {
                    tabXmidList = v.groupXmidList;
                }
            });
            if (!isNotBlank(tabDjxl)) {
                continue;
            }
            var bdcXmfbData = {};
            var bdcXmFbArray = $('.layui-tab-item:nth-child(' + i + ') .xmfb');
            var bdcXmFbxxArray = $('.xmfbxx');
            bdcXmFbArray.serializeArray().forEach(function (item, index) {
                bdcXmfbData[item.name] = item.value;
            });
            bdcXmFbxxArray.serializeArray().forEach(function (item, index) {
                bdcXmfbData[item.name] = item.value;
            });
            if (!$.isEmptyObject(bdcXmfbData)) {
                var bdcDjxxUpdateQO = {};
                var whereMap = {};
                whereMap.gzlslid = processInsId;
                whereMap.xmids = tabXmidList;
                bdcDjxxUpdateQO.whereMap = whereMap;
                bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmfbData);
                getReturnData("/slym/xm/plxgxmfb", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
                    if(isNotBlank(bdcXmfbData.sfsftg) && bdcXmfbData.sfsftg== 1){
                        // 添加税费托管业务展示说明标签
                        $("#sfsftg-tips").show();
                    }
                    if(isNotBlank(bdcXmfbData.sfsftg) && bdcXmfbData.sfsftg== 0){
                        // 添加税费托管业务展示说明标签
                        $("#sfsftg-tips").hide();
                    }
                    console.log("批量保存项目附表信息成功");
                }, function (err) {
                    throw err;
                });
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

function reCreateSjcl() {
    getReturnData("/slym/sjcl/recreate", {gzlslid: processInsId}, "GET", function (data) {
        if (data !== null && data !== '' && data !== undefined) {
            loadSjcl();
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr, "重新生成收件材料失败");
    })
}

/**
 * 推送省直房改办
 */
function tsSzfgb() {
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/fgf/tsszfgb/" + processInsId,
        type: 'GET',
        dataType: 'text',
        success: function (data) {
            if (data != null && parseInt(data) > 0) {
                ityzl_SHOW_SUCCESS_LAYER("推送成功！");
            } else {
                ityzl_SHOW_WARN_LAYER("推送失败，请重试！")
            }
            removeModal();
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e, "推送失败！");
        }
    });
}


function downloadDzgx() {
    addModel();
    getReturnData('/dzzz/dzgxxz', '', 'GET', function (data) {
        if (data && data > 0) {
            ityzl_SHOW_SUCCESS_LAYER('获取成功，需配置对照关系');
        } else {
            ityzl_SHOW_INFO_LAYER('无需重复获取');
        }
        removeModal();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })

}

//点击电子证照按钮前先判断是否有对照关系数据
function useDzzz() {
    addModel();
    var count = dzgxsfcz();
    if (count > 0) {
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
    var count = 0;
    getReturnData('/dzzz/dzgx', '', 'GET', function (data) {
        if (data === 0) {
            ityzl_SHOW_INFO_LAYER('对照表没有数据，请先获取对照关系并配置')
        }
        count = data;
    }, function (xhr) {

    }, false);
    return count;
}

function saveXmfb(xmfbArray, tabXmid, tabDjxl) {
    var tabXmidList = [];
    qlxx.forEach(function (v) {
        if (v.bdcXm.xmid == tabXmid) {
            tabXmidList = v.groupXmidList;
        }
    });

    var bdcXmfbData = {};
    if (xmfbArray !== null && xmfbArray.length > 0) {
        xmfbArray.forEach(function (item, index) {
            bdcXmfbData[item.name] = item.value;
        });
    }
    if (!$.isEmptyObject(bdcXmfbData)) {
        var bdcDjxxUpdateQO = {};
        var whereMap = {};
        whereMap.gzlslid = processInsId;
        if (isNotBlank(tabDjxl)) {
            whereMap.djxl = tabDjxl;
        }
        whereMap.xmids = tabXmidList;
        bdcDjxxUpdateQO.whereMap = whereMap;
        bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmfbData);
        getReturnData("/slym/xm/plxgxmfb", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
            console.log("批量组合保存项目附表信息成功")
        }, function (err) {
            throw err;
        }, false);
    }
}

//分屏验证
function showFpyz(xmid) {
    var url = getContextPath() + "/hefei/fpyz/fpyz.html?xmid=" + xmid;
    var index = layer.open({
        type: 2,
        title: "分屏验证",
        area: ['1150px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: url
    });
    layer.full(index);
}

/**
 * 根据sply和processInsId判断当前流程是否完税
 * 完税则需要给出提示 "已接收到税票信息"
 */
function showSpWsXx(sply, processInsId) {
    $.ajax({
        url: '/realestate-accept-ui/slym/sw/showSpWsXx',
        type: "GET",
        async: false,
        data: {sply: sply, processInsId: processInsId},
        success: function (data) {
            isShowYwsxx = data;
        }
    });
}

function saveSdqGhxx() {
    if (!document.getElementById("sdqghDiv")) {
        //如果页面不存在该元素，则不保存收费状态
        return;
    }
    var sdqghArray = [];
    //电过户
    if(!isNullOrEmpty($('#sdqgh-d').val())){
        var sdqgh = {};
        sdqgh.gzlslid = processInsId;
        sdqgh.slbh = $('#sjbh').val();
        sdqgh.consno = $('#sdqgh-d').val();
        sdqgh.sffgyd = $("input[name='d-sffgyd']:checked").val();
        sdqgh.ywlx = 2;
        sdqgh.sfbl = 0;
        sdqghArray.push(sdqgh)
    }

    //气过户
    if(!isNullOrEmpty($('#sdqgh-q').val())){
        var sdqQigh = {};
        sdqQigh.gzlslid = processInsId;
        sdqQigh.slbh = $('#sjbh').val();
        sdqQigh.consno = $('#sdqgh-q').val();
        sdqQigh.rqfwbsm = $('#sdqgh-q-lx').val();
        sdqQigh.xhzmc = $('#qihz').val();
        sdqQigh.ywlx = 3;
        sdqQigh.sfbl = 0;
        sdqghArray.push(sdqQigh)
    }

    if(!isNullOrEmpty($('#sdqgh-s').val())) {
        //水过户
        var sdqShuigh = {};
        sdqShuigh.gzlslid = processInsId;
        sdqShuigh.slbh = $('#sjbh').val();
        sdqShuigh.consno = $('#sdqgh-s').val();
        sdqShuigh.rqfwbsm = $('#sdqgh-s-lx').val();
        sdqShuigh.xhzmc = $('#shuihz').val();
        sdqShuigh.ywlx = 1;
        sdqShuigh.sfbl = 0;
        sdqghArray.push(sdqShuigh)
    };
    if (sdqghArray.length > 0){
        getReturnData("/slym/sdq/sdqxx", JSON.stringify(sdqghArray), "POST", function (data) {
            console.log('更新水电气信息成功');
        }, function (xhr) {
            delAjaxErrorMsg(xhr)
        })
    }
}

//从受理页面打开购物车
function openSlymGwc() {
    var index = layer.open({
        type: 2,
        title: "购物车",
        area: ['960px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: '/realestate-accept-ui/hefei/slym/slymgwc.html?gzlslid=' + processInsId
    });
    // layer.full(index);
}

function  addBdcdy(){
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = "SLYM_ADDBDCDY";
    var gzyzParamMap = {};
    gzyzParamMap.gzlslid = processInsId;
    bdcGzYzQO.paramMap = gzyzParamMap;
    gzyzCommon(2, bdcGzYzQO, function (data) {
        //新增不动产单元
        var url = getContextPath() + "/view/query/selectBdcdyh.html?gzlslid=" + processInsId + "&zlcsh=true";
        var index = layer.open({
            type: 2,
            title: "选择不动产单元",
            area: ['1300px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: url
        });
        layer.full(index);
    });
}





//获取税票 方法
function querySwspFj() {
    //合同编号
    var htbh =$("#jyhth").val();

    addModel();
    getReturnData("/slym/sjcl/list/swWsxx",JSON.stringify({gzlslid:processInsId,htbh:htbh}),"POST",function (data) {
        removeModal();
        if(data &&data.length >0){
            ityzl_SHOW_SUCCESS_LAYER("获取税票信息成功");
        }else{
            ityzl_SHOW_WARN_LAYER("未获取到税票信息！");
        }

    },function (error) {
        removeModal();
        delAjaxErrorMsg(error);

    })

}
function changeZjzlByQlrlx(data) {
    if(!isNullOrEmpty(qlrlxZjzlDzMap)) {
        var qlrlx = data.value;
        var $qlrlx = $(data.elem).parents('tr').find('select[name=qlrlx]');
        var $zjzl = $(data.elem).parents('tr').find('select[name=zjzl]');
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
            $zjzl.empty();
            $zjzl.append('<option value="">请选择</option>');

            $.each(zjzlList, function (index, item) {
                $zjzl.append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            form.render('select');
        } else {
            $zjzl.empty();
            $zjzl.append('<option value="">请选择</option>');
            $.each(zjzlZd, function (index, item) {
                $zjzl.append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            form.render('select');
        }
    }
}

function changeqlrlxByZjzl(data){
    if(!isNullOrEmpty(qlrlxZjzlDzMap)) {
        var zjzl = data.value;
        for (var key in qlrlxZjzlDzMap) {
            if (qlrlxZjzlDzMap[key].indexOf(zjzl) > -1) {
                var $qlrlx = $(data.elem).parents('tr').find('select[name=qlrlx]');
                $qlrlx.val(key);
                break;
            }
        }
        if (isNotBlank(yzsfhytsfhzjzl)) {
            var $zjh = $(data.elem).parents('tr').find('input[name=zjh]');
            var newattr = $zjh.attr("lay-verify");
            if (yzsfhytsfhzjzl.indexOf(zjzl) > -1) {
                if (isNotBlank(newattr) && newattr.indexOf("sfhytsfh") < 0) {
                    $zjh.attr("lay-verify", newattr + "|sfhytsfh");
                } else if (zjzl !== null && zjzl !== "") {
                    $zjh.attr("lay-verify", "sfhytsfh");
                } else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("sfhytsfh") > -1) {
                    $zjh.attr("lay-verify", newattr.replace("sfhytsfh", ""));
                }
            } else {
                if (isNotBlank(newattr)) {
                    $zjh.attr("lay-verify", newattr.replace("sfhytsfh", ""));
                }
            }
        }
        form.render('select');
    }
}

/*
* 验证申请人的证件号是否有特殊符号
*/
function checkZjhsfhytsfh(zjh) {
    var reg = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？ ]");
    var verifyMsg = "";
    if (reg.test(zjh.replace(/\s*/g, ""))) {
        verifyMsg = "证件号存在符号不符合要求";
    }
    return verifyMsg;
}

/**
 * 添加证件号是否含有特殊符号验证
 */
function addzjhYz(){
    $.each($("select[name=zjzl]"), function (i) {
        if(isNotBlank(yzsfhytsfhzjzl)){
            var zjzl = $(this).val();
            if(yzsfhytsfhzjzl.indexOf(zjzl) > -1){
                var $zjh = $(this).parents('tr').find('input[name=zjh]');
                if($zjh.length > 0 ){
                    var newattr  = $zjh.attr("lay-verify");
                    if (isNotBlank(newattr) && newattr.indexOf("sfhytsfh") < 0) {
                        $zjh.attr("lay-verify", newattr + "|sfhytsfh");
                    } else if (zjzl !== null && zjzl !== "") {
                        $zjh.attr("lay-verify", "sfhytsfh");
                    }else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("sfhytsfh") > -1) {
                        $zjh.attr("lay-verify", newattr.replace("sfhytsfh", ""));
                    }
                }
            }
        }

    });
}