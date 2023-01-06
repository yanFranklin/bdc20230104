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
                        var a = setInterval(function () {
                            if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                                getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                                clearInterval(a);
                            }
                        }, 50);
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
                    if(sfyzbt){
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
            }
        });

        form.on("submit(saveData)", function (data) {
            addModel();
            AcceptForm.verifyGz(processInsId).done(function (yzResult) {
                removeModal();
                // 当验证数据为空时，直接进行保存。
                if (yzResult.length == 0) {
                    // 规则验证抵押金额是否超过1亿
                    var els =document.getElementsByName("bdbzzqse");
                    var maxbdbzzqse = 0;
                    for (var i = 0, j = els.length; i <j; i++){
                        if(els[i].value > maxbdbzzqse){
                            maxbdbzzqse = els[i].value;
                        }
                    }
                    AcceptForm.yzDyje(maxbdbzzqse, doSubmit);
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

            // 判断当前页面是否需要提示税票完税的提示
            showSpWsXx(sply, processInsId);
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    getReturnData("/slym/ql/plzh", {processInsId: processInsId, zxlc: zxlc,dsqlr:true,cxCqxx:true,cxsdq:true,cxYchsCqxx:true}, 'GET', function (data) {
        if (isNotBlank(data)) {
            qlxx = data;
            xmlx =4;
            xmfb = data[0].bdcXmFbDO;
        }
    }, function (err) {
        console.log(err);
    }, false);
    //判断是否是虚拟单元号
    checkXndyhPl();

    var djxldjyyList = {};

    qlxx.forEach(function(item, index) {
        if (isNotBlank(item)) {
            getReturnData("/slym/xm/queryDjxlDjyy", {
                djxl: item.bdcXm.djxl,
                gzldyid: item.bdcXm.gzldyid
            }, "GET", function (data) {
                if (isNotBlank(data)) {
                    djxldjyyList = data;
                    item.djxldjyy = djxldjyyList;
                }
            }, function (error) {
                delAjaxErrorMsg(error);
            }, false);
        }
    });

    var json = {
        qllx: qllx,
        qlxx: qlxx
    };
    json['sfchangeqljbxxTab'] = sfchangeqljbxxTab;


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
    if (sfchangeqljbxxTab) {
        $.when(loadTabQlr(qlxx[0].bdcXm.xmid, qlxx[0].bdcXm.djxl, '.layui-show'), loadPlQlxx(qlxx[0].bdcXm.xmid), loadBdcdyh(qlxx[0].bdcXm.djxl, qlxx[0].bdcXm.qllx, qlxx[0].bdcXm.xmid)).done(function () {
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
    var qllx = bdcxmxx.qllx;
    if (qllx === 97) {
        changeQlrlbMc();
    }
    //常州居住权获取设立方式，默认为0
    var jzq;
    var slfs = 0;
    for(var i = 0; i< qlxx.length; i++){
        if (qlxx[i].qllx === 92){
            qllx = 92;
            jzq = getSlfs(qlxx[i].bdcXm.xmid);
            slfs = jzq.slfs;
            break;
        }
    }
    var yjxx = getYjxx();
    var json = {
        bdcxmxx: bdcxmxx,
        zd: zdList,
        slfs: slfs,
        xmfb: xmfb,
        yjxx: yjxx,
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
    renderSelect();
    disabledAddFa();
}

//获取收件材料信息,渲染到页面
var sjclNumber = 0;

//加载收件材料信息
//用于存放所有的收件材料id
var sjclidLists = [];



function loadSjcl() {
    addModel();
    getReturnData("/slym/sjcl/zhsjcl", {gzlslid: processInsId, taskId: taskId}, 'GET', function (data) {
        removeModal();
        generateZhsjcl(data);
    }, function (err) {
        delAjaxErrorMsg(err);
    });

}

//组织组合收件材料内容
function generateZhsjcl(zhSjclList) {
    if (isNotBlank(zhSjclList)) {
        //加载收件材料前判断页面是否是展开状态，展开状态刷新后还继续展开
        var hideArea = $('.showhide');
        for (var i = 0; i < zhSjclList.length; i++) {
            sjclNumber += zhSjclList[i].length;
            for (var j = 0; j < zhSjclList[i].length; j++) {
                sjclidLists.push(zhSjclList[i][j].sjclid);
            }
        }
        sessionStorage.setItem("sjclidLists", sjclidLists);
        var json = {
            bdcZhSjclList: zhSjclList,
            zd: zdList
        };
        var tpl = zhsjclTpl.innerHTML, view = document.getElementById("sjclxx");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        getStateById(readOnly, formStateId, 'slympl');
        form.render('select');
        disabledAddFa("");
        // 默认展开收件材料
        $('.bdc-show-more').html('收起');
        $('.bdc-show-more').parent().siblings('.bdc-more-item').removeClass('bdc-hide');
        $('.bdc-show-more').parent().siblings('.bdc-more-item').addClass('showhide');

        var $info = $('.basic-info');
        $info.off('click', '.bdc-show-more').on('click', '.bdc-show-more', function () {
            var $this = $(this);
            if ($this.html() === '展开') {
                $this.html('收起');
            } else {
                $this.html('展开');
            }
            $this.parent().siblings('.bdc-more-item').toggleClass('bdc-hide');
            $this.parent().siblings('.bdc-more-item').toggleClass('showhide');
            return false;
        });
        if (isNotBlank(hideArea) && hideArea.length > 0) {
            //不为空说明展现了,再次点击展开
            for (var i = 0; i < hideArea.length; i++) {
                var djxl = $(hideArea[i]).attr("data-djxl");
                $('.showsjcl' + djxl).parents('.bdc-form-add').find('.bdc-form-btn').find(".bdc-show-more").click();
                $('.showsjcl' + djxl).parents('.bdc-form-add').find('.bdc-form-btn').find(".bdc-show-more").click();
            }
        }
    }
}

//第一次单击权利tab，获取申请人信息
function loadTabQlr(xmid, djxl, nowTab,loadzwr) {
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
            reloadDyrAndCfjg(xmid, nowTab,loadzwr);
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
        success: function (data) {
            removeModal();
            generateQlrxx(filterQlrZh(data), "qlrTable", djxl, nowTab);
            //加载权利信息模块部分信息
            reloadQlxxForYwr(data, nowTab);
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
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
    var json = {
        bdcQlrDOList: data,
        zd: zdList,
        qlrLength: 0,
        djxl: djxl
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
    form.render();
    getStateById(readOnly, formStateId, 'slympl');
    //合肥联系电话加密显示
    toEncryptClass('dhjm');
    renderSelect();
    disabledAddFa("sqrForm");
}

/**
 * 处理权利信息中义务人相关信息
 */
function reloadQlxxForYwr(ywrdata, nowTab) {
    //抵押人
    var dyr = $(nowTab + " #dyaq-dyr");
    //供役地权利人
    var gydqlr = $(nowTab + " #dyiq-gydqlr");
    if (dyr != null || gydqlr != null) {
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
function reloadDyrAndCfjg(xmid, nowTab,loadzwr) {
    if (isNotBlank(xmid) && isNotBlank(nowTab)) {
        var xydqlr = $(nowTab + " #dyiq-xydqlr");
        if (xydqlr != null) {
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
        var zwr =$(nowTab + " #dyaq-zwr");
        var zwrzjzl =$(nowTab + " #dyaq-zwrzjzl");
        var zwrzjh =$(nowTab + " #dyaq-zwrzjh");
        if(zwr != null &&zwr.length >0 &&loadzwr){
            getReturnData("/slym/ql/zwr", {xmid: xmid}, 'GET', function (data) {
                if(data &&data.bdcDsQlrDOList &&data.bdcDsQlrDOList.length >0){
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
                    generateDsQlr(xmid, $(nowTab).find(".sqr"),data.bdcDsQlrDOList);
                }
            }, function (err) {
                console.log(err);
            }, false);

        }

    }
}


//第一次单击权利tab，获取权利信息数据
function loadPlQlxx(xmid) {
    var qlxxShow = {};
    qlxx.forEach(function (v) {
        if (v.bdcXm.xmid == xmid) {
            qlxxShow = v;
        }
    });
    className = qlxxShow.className;
    buildQlxx(qlxxShow, $('.layui-show'));
}

//保存后，重新获取权利信息数据
function reloadPlQlxx(xmid, $nowTab) {
    var qlxxShow = {};
    getReturnData("/slym/ql/plzh", {processInsId: processInsId, zxlc: zxlc,dsqlr:true,cxCqxx:true,cxsdq:true,cxYchsCqxx:true}, 'GET', function (data) {
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
            getReturnData("/slym/xm/queryDjxlDjyy", {djxl: bdcSlQlxxym.bdcXm.djxl,gzldyid:bdcSlQlxxym.bdcXm.gzldyid}, "GET", function (data) {
                if (isNotBlank(data)) {
                    djxldjyyList = data;
                }
            }, function (error) {
                delAjaxErrorMsg(error);
            }, false);
        }

        // bdc_qzxx根据bdc_xm中dzwmj最大的xmid来关联，获取dzwmj最大的xmid
        var xmList = bdcSlQlxxym.bdcXmDOList;
        // 签字xmid
        var qzxmid="";
        xmList.sort(function (a, b) {
            return b.dzwmj - a.dzwmj;
        })
        if (xmList && xmList.length > 0) {
            qzxmid = xmList[0].xmid;
        }

        //申请分别持证为空默认为是
        if(bdcSlQlxxym &&bdcSlQlxxym.bdcXm &&bdcSlQlxxym.bdcXm.sqfbcz ==null){
            bdcSlQlxxym.bdcXm.sqfbcz =1;
        }

        //抵押，预抵押，异议，查封，地役权展现在外面的权利字段
        if (qllx === parseInt(commonDyaq_qllx) || sfdydj || qllx === 97 || qllx === 98 || qllx === 19) {
            var json = {
                bdcSlQlxxym: bdcSlQlxxym,
                zd: zdList,
                dkfs: dkfs,
                djxldjyy: djxldjyyList,
                qzxmid: qzxmid
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

                var dyfs = {
                    dyfs: bdcSlQlxxym.bdcQl.dyfs,
                    zd: zdList
                };

                var dyfsTPL = dyfsTpl.innerHTML, dyfsView = document.getElementById('dyfsContainer');
                if (isNotBlank(dyfsView)) {
                    //渲染数据
                    laytpl(dyfsTPL).render(dyfs, function (html) {
                        dyfsView.innerHTML = html;
                    });
                }
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
            //增加水电气内容
            var ghxx = new Map();
            if(bdcSlQlxxym.bdcSdqghDOList){
                $.each(bdcSlQlxxym.bdcSdqghDOList, function(index, value){
                    ghxx.set(value.ywlx, value.sfbl);
                });
            }
            //获取签字信息
            getQzxx(bdcSlQlxxym.bdcXm.gzlslid);
            //展现交易信息字段
            var json = {
                zd: zdList,
                bdcSlQlxxym: bdcSlQlxxym,
                djxldjyy: djxldjyyList,
                ghxx: ghxx,
                qzxmid: qzxmid
            };
            var jyxxTpl = document.getElementById("bdcxxTpl");
            if (isNotBlank(jyxxTpl)) {
                var tpl = jyxxTpl.innerHTML,
                    view = $nowTab.find('#bdcdyqlxx');
                laytpl(tpl).render(json, function (html) {
                    view.html(html);
                });
                if(isNotBlank(bdcSlQlxxym.bdcXm.jyhth)){
                    //加载合同信息模块
                    generateHtxxZh(bdcSlQlxxym.bdcXm,$nowTab);
                }
                renderDate(form, formIds);
                renderSelect();
                //给下拉框添加删除图标
                renderSelectClose(form);
                getStateById(readOnly, formStateId, 'slympl', 'qlxxTab');
                form.render();
                disabledAddFa("bdcxxTpl");

            }
            $nowTab.find(".bdc-table-zj").removeClass("bdc-hide");
            var height = $nowTab.find('.bdc-table-zj').height();
            $nowTab.find(".bdc-line-search-container").css("padding-top", height + 62);
            $nowTab.find('.bdc-table-box').addClass('bdc-table-box-cz');
            //处理合计信息
            $nowTab.find("#sumjzmj").text(isNotBlank(bdcSlQlxxym.jzmjSum) ? bdcSlQlxxym.jzmjSum : 0 + "m²");
            $nowTab.find("#bdcdyCount").text(bdcSlQlxxym.bdcdyCount);

            //判断页面是否存在交易信息字段，显示即查询
            if ($nowTab.find(".jyxx").length > 0 && isNotBlank(bdcSlQlxxym.bdcXm.xmid)) {
                var formFilter = $nowTab.find('.bdc-qlxx-form').attr('lay-filter');
                getReturnData("/ycsl/jyxx", {xmid: bdcSlQlxxym.bdcXm.xmid}, "GET", function (data) {
                    //表单赋值
                    data.yhtbh = data.htbh;
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
        //加载第三权利人
        generateDsQlr(bdcSlQlxxym.bdcXm.xmid, $nowTab.find(".sqr"),null,null);
        sfchange = false;
        //合肥判断登记原因是否变化
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
}

//获取签字信息
function getQzxx(gzlslid) {
    if(isNullOrEmpty(gzlslid)) {
        return;
    }
    getReturnData("/pjq/qznrxx",{gzlslid:gzlslid},"GET",function (data) {
        if(data) {
            $("#qlrqztp").attr("src", data[1]);
            $("#ywrqztp").attr("src", data[3]);
        }

    },function (error) {
        delAjaxErrorMsg(error);

    });

}

//组织合同信息到组合页面
function generateHtxxZh(xmxx, $nowTab) {
    cxHtxx(xmxx).done(function(data){
        var bdchtxxTpl;
        if(isNotBlank(data)){
            if(data.htxx.fwlx == "clf"){
                bdchtxxTpl = document.getElementById("bdcClfHtxxTpl");
            }else {
                bdchtxxTpl = document.getElementById("bdcSpfHtxxTpl");
            }

        }else {
            bdchtxxTpl = document.getElementById("bdcClfHtxxTpl");
        }
        var json = {
            htxx: data,
            zd:zdList
        };
        var tpl = bdchtxxTpl.innerHTML, view = $nowTab.find(".bdchtxx")[0];

        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        disabledAddFa();
    });
}

// 根据xmid和合同编号查询合同信息
function cxHtxx(xmxx) {
    var deferred = $.Deferred();
    addModel();
    $.ajax({
        url: getContextPath()+"/ycsl/jyxx/cz/queryFcjyxxByHtbh",
        type: 'POST',
        dataType: 'json',
        data: {
            xmid: xmxx.xmid,
            htbh: xmxx.jyhth,
            sfck:true
        },
        success: function (data) {
            removeModal();
            if(isNotBlank(data) &&isNotBlank(data.fwlx)){
                if(data.fwlx == "clf"){
                    var clfData = filterClfHtxx(data);
                    deferred.resolve(clfData);
                }else{
                    var spfData = filterSpfHtxx(data);
                    deferred.resolve(spfData);
                }
            } else{
                deferred.resolve(data);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
            deferred.reject();
        }
    });
    return deferred;
}

// 过滤解析存量房合同信息数据
function filterClfHtxx(data){
    var msrxx = [];
    var cmrxx = [];
    if(data.qlrxx.length >0){
        $.each(data.qlrxx, function(index ,val){
            if(val.qlrlb == "1"){
                msrxx.push(val);
            }else{
                cmrxx.push(val);
            }
        });
    }
    return {
        htxx :  data,
        cmrxx : cmrxx,
        msrxx : msrxx
    };
}
// 过滤解析商品房合同信息数据
function filterSpfHtxx(data){
    var cmrxx = [];
    cmrxx.push({
        qlrmc: data.cmr,
        zjzl : data.cmrzjzl,
        zjh : data.cmrzjh
    });
    var msrxx = [];
    msrxx.push({
        qlrmc : data.msrmc,
        zjzl: data.msrzjzl,
        zjh : data.msrzjh
    });
    return {
        htxx : data,
        cmrxx : cmrxx,
        msrxx : msrxx,
        dlrxx : {
            dlrmc: data.wtdlr,
            dlrzjh: data.wtdlrzjh,
            dlrzjzl: data.wtdlrzjzl
        }
    }
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

    //提交表单
    $(".layui-show #searchBdcdy").click(function () {
        var bdcdyArray = $(".layui-show .bdcdyForm").serializeArray();
        bdcdyArray.forEach(function (item) {
            data[item.name] = item.value;
        });
        $.when(tableReload('xmid' + djxl, data, url)).done(function () {
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
    tableConfig = {
        id: 'xmid' + djxl,
        url: url,
        contentType: 'application/json',
        method: 'post',
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
            var reverseList = ['zl'];
            reverseTableCell(reverseList, 'xmid');

            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

            if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
            } else {
                $('.bdc-form-div .layui-show .layui-table-body').height($('.bdc-form-div .layui-show .bdc-table-box').height() - 131);
                $('.bdc-form-div .layui-show .layui-table-fixed .layui-table-body').height($('.bdc-form-div .layui-show .bdc-table-box').height() - 131 - 17);

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
                    clearInterval(a);
                }
            }, 50);
        }
    };

    //加载表格 设置默认显示分页详细信息 取消详细按钮
    function loadDataTablbeByUrl(_domId, _tableConfig) {
        layui.use(['table', 'laypage', 'jquery'], function () {
            if (_domId) {
                var table = layui.table;
                var $ = layui.jquery;
                var tableDefaultConfig = {
                    elem: _domId,
                    toolbar: "#toolbarDemo",
                    even: true,
                    cellMinWidth: 80,
                    page: true,  //开启分页
                    limits: [10, 50, 100, 200, 500],
                    data: [],
                    request: {
                        limitName: 'size', //每页数据量的参数名，默认：limit
                        loadTotal:true,//详细分页属性
                    },
                    response: {
                        countName: 'totalElements',  //数据总数的字段名称，默认：count
                        dataName: 'content' //数据列表的字段名称，默认：data
                    }
                };

                if (!_tableConfig.cols || !_tableConfig.cols[0] || _tableConfig.cols[0].length < 1) {
                    _tableConfig.cols = [[]]
                }
                var tableRenderConfig = $.extend({}, tableDefaultConfig, _tableConfig);
                table.render(tableRenderConfig);
            }
        });
    }
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
    $('.bdc-table-box-dyaq').on('click', '.bdc-more-btn', function (event) {
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
    var djlxArray = $(".layui-this").find("input[name='djlx']");
    var djlxInput = djlxArray[0];
    var djlx = $(djlxInput).val();
    var dydj = false;
    //判断是否是抵押类登记
    getReturnData("/slym/ql/checkDydj", {xmid: xmid}, 'GET', function (data) {
        dydj = data;
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    var url = getContextPath() + "/view/slym/qlr.html?xmid=" + xmid + "&lclx=" + lclx + "&processInsId=" + processInsId + "&formStateId=" + formStateId + "&qllx=" + qllx + "&zxlc=" + zxlc + "&djxl=" + djxl+ "&djlx=" + djlx;
    if (dydj) {
        url = url + "&dydj=true";
    }
    layer.open({
        type: 2,
        area: ['960px', '610px'],
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
    var $this = $(".layui-this");
    var djlx = $($this.find("input[name='djlx']")[0]).val();
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var url = getContextPath() + "/view/slym/qlr.html?processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=" + lclx + "&xmid=" + xmid + "&formStateId=" + formStateId + "&qllx=" + qllx + "&readOnly=" + readOnly + "&zxlc=" + zxlc + "&djxl=" + djxl+"&djlx="+djlx;;
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
            area: ['960px', '610px'],
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
            area: ['960px', '610px'],
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
    layer.open({
        type: 2,
        title: "不动产单元信息",
        area: ['960px', '390px'],
        fixed: false, //不固定
        content: getContextPath() + "/view/slym/bdcdy.html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&zxlc=" + zxlc,
        btnAlign: "c",
        success: function () {
        }
    });
}

//对比权籍按钮
function openDbqj(xmid) {
    var url = getContextPath() + "/view/synlpb/contractLpb.html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly;
    layerOpenFullReload(url, "对比信息");
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
            url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&isCrossOri=false";
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

//单击表格内 更多 登记历史
function showBdcdyXqNew(bdcdyh,tzym) {
    var url = "/realestate-register-ui/view/lsgx/bdcdyDjLsgxNew.html?bdcdyh=" + bdcdyh+"&tzym="+tzym;
    var index = layer.open({
        type: 2,
        title: "登记历史关系",
        area: ['1150px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: url
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
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        $("#print").show();
        setUiWidth($(this), $("#print"));
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
        $("#dzzzxx").show();
        setUiWidth($(this), $("#dzzzxx"));
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

    $(".print-btn li").click(function () {
        $("#print").hide();
    });

    $(".dzzz-button li").click(function () {
        $("#dzzzxx").hide();
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
                deleteLzr(qlrid, djxl);
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

function deleteLzr(qlrid, djxl) {
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
            if (sfchange) {
                reCreateSjcl();
            }
        }, function (err) {
            throw err;
        }, false);
    }
}

//权利人保存
function saveQlr() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        if (isNotBlank(tabXmid)) {
            var qlrArrayPllc = $(".layui-tab-item:nth-child(" + i + ") .qlr").serializeArray();
            if (qlrArrayPllc.length != 0) {
                saveAllQlr(qlrArrayPllc, tabDjxl, tabXmid, tabDefaultQlrList[i - 1], i);
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
}

function saveAllQlr(qlrArray, djxl, xmid, defaultQlrArr, index) {
    var qlrList = [];
    var qlr = defaultQlrArr[0];
    var qlrnum = 0;
    var gyfs = "";
    var ywrnum = 0;
    var ywrgyfs = "";
    var ywrmc = "";

    var trIndex = 0;

    for (var j = 0; j < qlrArray.length; j++) {
        var name = qlrArray[j].name;
        qlr[name] = qlrArray[j].value;
        // 以xmid为每一组权利人的起始数据，作为分割依据
        if ((j + 1 < qlrArray.length && qlrArray[j + 1].name === 'xmid') || j + 1 == qlrArray.length) {
            if (qlr.qlrlb === "1") {
                qlrnum++;
                gyfs += qlr.gyfs + ",";
                savePlzhLzr(qlr, djxl);

            }
            if (qlr.qlrlb == '2') {
                ywrnum++;
                ywrgyfs += qlr.gyfs + ",";
                ywrmc += qlr.qlrmc + ",";
            }
            //将证件号中小写字母改为大写
            toUpperClass(qlr, ["zjh", "dlrzjh", "frzjh", "lzrzjh"]);
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

    if (!checkAddGybl(qlrList)) {
        throw err = new Error('共有比例不正确，请检查！');
    }


    //验证权利人共有方式
    if (!checkGyfs(ywrgyfs, ywrnum)) {
        alert("义务人共有方式不正确，请检查!");
    }

    var url = "/slym/qlr/list/plzh?processInsId=" + processInsId + "&xmid=" + xmid + "&djxl=" + djxl;

    if (isNotBlank(qlrList)) {
        getReturnData(url, JSON.stringify(qlrList), 'PATCH', function (data) {
            loadQlr();
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
                var yhtbh = $.trim($nowTab.find("input[name=yhtbh]").val());
                bdcJyArray.serializeArray().forEach(function (item, index) {
                    if(item.name ==="jyhth"){
                        item.name ="htbh";
                    }
                    bdcJyData[item.name] = $.trim(item.value);
                });

                if (!$.isEmptyObject(bdcJyData)) {
                    getReturnData("/ycsl/jyxx/list?processInsId=" + processInsId + "&djxl=" + tabDjxl + "&xmids=" + tabXmidList.join(","), JSON.stringify(bdcJyData), 'PATCH', function (data) {
                        if (isNotBlank(bdcJyData.htbh) && $.trim(bdcJyData.htbh) != yhtbh) {
                            importJyxx(bdcJyData.htbh, tabXmidList[0]);
                        }
                        console.log("保存交易信息成功")
                    }, function (err) {
                        throw err;
                    }, false);
                }
            }
            //刷新权利信息模块,防止交易信息重复新增
            reloadPlQlxx(tabXmid, $nowTab);
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

        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

//权利信息保存
function saveQlxx() {
    saveXmfbPlzh(".xmfb",'');
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
        if ($djyy.length > 0) {
            var djyy = $djyy.val();
            var tabqllx = $('.layui-tab-item:nth-child(' + i + ')').data('qllx');
            if (zxlc !== "true" && tabqllx !== "98" && tabqllx !== "97" && tabqllx !== "94") {
                bdcQlData.djyy = djyy;
            } else if (zxlc === "true") {
                if (tabqllx === commonDyaq_qllx || tabqllx === "19") {
                    //注销抵押原因/注销地役原因
                    bdcQlData.zxdyyy = djyy;
                } else if (tabqllx === "97") {
                    //注销异议原因
                    bdcQlData.zxyyyy = djyy;
                }
            }
        }
        var tabqllx = $('.layui-tab-item:nth-child(' + i + ')').data('qllx');
        //居住权设立方式特殊处理
        if (tabqllx === 92){
            var slfs = $("#slfs").val();
            bdcQlData['slfs'] = slfs;
        }
        if (bdcQlArray.length !== 0 || isNotBlank(bdcQlData)) {
            bdcQlArray.serializeArray().forEach(function (item, index) {
                bdcQlData[item.name] = item.value;
            });
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

//组织参数调用评价器
function evaluate() {
    pjq.evaluate();
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
function loadQlr(loadzwr) {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        if (isNotBlank(tabXmid)) {
            loadTabQlr(tabXmid, tabDjxl, '.layui-tab-item:nth-child(' + i + ')',loadzwr);
        }

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

//第三权利人详细
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

//加载第三权利人信息
function generateDsQlr(xmid,$sqr,index,bdcDsQlrDOList){

    if(!bdcDsQlrDOList ||bdcDsQlrDOList.length ===0) {
        getReturnData("/slym/qlr/list/dsQlr", {xmid: xmid}, 'GET', function (data) {
            bdcDsQlrDOList = data;
        }, function (err) {
            console.log(err);
        }, false);
    }

    //渲染模板数据
    var json = {
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

//关闭panel
function cancelEdit() {
    layer.closeAll();
}

//同步权籍数据
function synchLpbDataToLc() {
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
9
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
    $.when(saveXmxx(), saveSjcl(), saveQlxx(), saveDsQlr(),saveGhxx(".ghxx"), saveJyxx(), saveYjxx()).done(function () {
        hxYwsj(processInsId);
        removeModal();
        if (!isNotBlank(saveMsg)) {
            ityzl_SHOW_SUCCESS_LAYER("保存成功");
        } else {
            ityzl_SHOW_WARN_LAYER(saveMsg);
        }
        saveMsg = '';
    })
}

//房地产权水电气等过户信息(组合页面)
function saveGhxx(formClass) {
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

//批量保存项目附表
function saveXmfbPlzh() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        if (isNotBlank(tabXmid)) {
            var xmfbArray = $('.layui-tab-item:nth-child(' + i + ') .xmfb').serializeArray();
            var jbxxXmfbArray =[];
            if($('.jbxx-xmfb').length >0) {
                jbxxXmfbArray = $('.jbxx-xmfb').serializeArray();
            }
            //保存项目附表信息
            if (xmfbArray.length !== 0 ||jbxxXmfbArray.length !== 0) {
                saveXmfb(xmfbArray, tabXmid, tabDjxl,jbxxXmfbArray);
            }
        }
    }
}

function saveXmfb(xmfbArray, tabXmid, tabDjxl,jbxxXmfbArray) {
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
    if (jbxxXmfbArray !== null && jbxxXmfbArray.length > 0) {
        jbxxXmfbArray.forEach(function (item, index) {
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

function getYjxx() {
    var yjxx = {};
    getReturnData("/slym/yjxx/cz/" + processInsId, {}, "GET", function (result) {
        yjxx = result;
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    return yjxx;
}


//保存邮寄信息
function saveYjxx() {
    var yjxxArray = $(".yjxx");
    var yjxx = {};
    if (yjxxArray !== null && yjxxArray.length > 0) {
        yjxx.gzlslid = processInsId;
        yjxx.slbh = $('#sjbh').val();
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

//展示第三权利人模块
function showDsQlrTpl(elem) {
    if ($(elem).parents(".layui-tab-item").find(".dsqlr-basic").length > 0) {
        var $dsqlr = $(elem).parents(".layui-tab-item").find(".dsqlr-basic");
        $dsqlr.removeClass("bdc-hide");
        //滚动到对应位置
        $('html,body').animate({scrollTop: $dsqlr.offset().top}, 800);
    }

}

//加载按钮
function loadButtonArea(ymlx) {
    var json = {printBtn: slymPrintBtn};
    var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    getStateById(readOnly, formStateId, ymlx);
    disabledAddFa();
    titleShowUi();

    //证书预览
    $("#createZs").click(function () {
        createZs();
    });

    // 通知外网缴费
    $("#tswwjf").click(function () {
        tswwjf();
    });


    //同步权籍数据
    $("#synchLpbData").click(function () {
        addModel();
        setTimeout(function () {
            synchLpbDataToLc();
        }, 0);

    });

    //查看权籍附件
    $("#showLpbFj").click(function () {
        showLpbFj("");
    });

    //评价器
    $("#evaluate").click(function () {
        evaluate();
    });

    //是否由EMS推送
    $("#emsPush").click(function () {
        markEmsPush();
    });

    // 查看变更记录
    $("#ckbgjl").click(function(){
        showBgjl();
    });

    //获取税票
    $("#getwsfp").click(function () {
        getWsfp();
    });

    // 查询合同信息
    $(document).on('click','#queryHtxx',function () {
        queryHtxx("");
    });

    //一体化审批附件
    $("#ythspfj").click(function () {
        ythspfj();
    });


}

/**
 * 获取完税发票
 */
function getWsfp() {
    addModel();
    $.ajax({
        url: getContextPath()+"/slym/sw/getWsxx",
        type: 'GET',
        data: {gzlslid: processInsId},
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                if(data == "1"){
                    ityzl_SHOW_SUCCESS_LAYER("获取税票成功");
                    $("#wszt").val("已缴税");
                    loadSjcl();
                }else{
                    ityzl_SHOW_WARN_LAYER("未查询到完税信息");
                }
            }else{
                ityzl_SHOW_WARN_LAYER("未查询到完税信息");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });

}

//获取合同信息
function queryHtxx(fwlx){
    addModel();
    getReturnData( "/ycsl/checkSpfLc", {gzlslid: processInsId}, 'GET', function (result) {
        fwlx =result;
        $.ajax({
            url: getContextPath() + "/ycsl/jyxx/queryHtbh",
            type: 'GET',
            data: {gzlslid: processInsId},
            success: function (data) {
                removeModal();
                var url = getContextPath() + "/changzhou/slym/cxhtxx.html?xmid=" + data.xmid + "&fwlx=" + fwlx + "&htbh=" + data.jyhth + "&processInsId=" + processInsId;
                var htIndex = layer.open({
                    type: 2,
                    area: ['1150px', '575px'],
                    fixed: false, //不固定
                    title: "合同信息",
                    content: url,
                    btnAlign: "c"
                });
                layer.full(htIndex);
                form.render();
                resetSelectDisabledCss();
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    },function (err) {
        removeModal();
        delAjaxErrorMsg(xhr)
    });

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

function getSlfs(xmid){
    var jzq;
    getReturnData("/slym/xm/slfs", {xmid: xmid}, "GET", function (result) {
        jzq = result;
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    return jzq;

}
