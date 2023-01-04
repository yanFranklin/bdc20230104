var $, form, layer, element, table, laytpl, laydate;
//页面入口
var sjclids = new Set();
var qlrLength = 0;
//是否虚拟号
var isXndyh = false;
//页面重新加载清空存储的收件材料id session
sessionStorage.removeItem("yxsjclids");
sessionStorage.removeItem("sjclidLists");
//增量初始化需要的参数
var jbxxid ="";
var processDefKey="";
var warnLayer ="";
// 批量的字段在权利信息页面，这里记录全局变量
var qlxxChangeIds = "";
var authorityMapByDjyy = {};
//存放当前登记原因可编辑字段集合(主要用于根据登记原因控制字段可编辑)
var editElementForDjyy = "";
// 审批来源
var sply;
//用于打印判断产权还是抵押信息
var qlxxForPrint;
//用于判断权利tab页
var className;
// 获取processInstanceType
var processInstanceType = $.getUrlParam('processInstanceType');
//共有方式有问题的，保存结束提示信息
var saveMsg = '';
var ydjyy = '';
var ydjyyArr = {};
var sfchange = false;
var zdList = {a: []};
var xmid;
var qlList = [];
// 登记原因
var djxldjyyList = {};
var formIds = "";
var qllx;
var qszt;
var sfdydj;
//判断是否首次登记，从而允许楼盘表新增
var sfscdj =false;
var sjclNumber = 0;
//用于存放所有的收件材料id
var sjclidLists = [];
var processInsId = getQueryString("processInsId");
var lclx = "pllc";
var formStateId = getQueryString("formStateId");
var zsmodel = getQueryString("zsmodel");
var readOnly = getQueryString("readOnly");
var zxlc = getQueryString("zxlc");
var printValue = getQueryString("print");
var taskId = getQueryString("taskId");
// 领证方式
var lzfs = {};
//档案归属地
var dagsd = '';
var xmfbList={};
var flag=true;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laytpl = layui.laytpl;
    laydate = layui.laydate;

    form.on('select(selected)', function (data) {   //选择移交单位 赋值给input框
        $("#cf-jfwj").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
        form.render();
        resetSelectDisabledCss();
    });

    form.on('select(cfwj)', function (data) {   //选择移交单位 赋值给input框
        $(this).parents('.layui-input-inline').find("input[name='cfwj']").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
        form.render();
        resetSelectDisabledCss();
    });


    //滚动时头部固定
    var $cnotentTitle = $('.bdc-content-fix');
    var $navContainer = $('.bdc-form-div');
    function defaultStyle() {
        if($cnotentTitle.length == 1){
            $('.bdc-form-div').css('padding-top','68px');
        }
        if($(window).scrollTop() > 68){
            $cnotentTitle.css('top','0');
            $navContainer.css('top','63px');
        }else if($(window).scrollTop() <= 68){
            $cnotentTitle.css('top','10px');
            $navContainer.css('top','68px');
        }
    }

    $(window).resize(function(){
        defaultStyle();
    });
    $(window).on('scroll',function () {
        if($(this).scrollTop() > 68){
            $cnotentTitle.css('top','0');
            $navContainer.css('top','63px');
        }else if($(this).scrollTop() <= 68){
            $cnotentTitle.css('top','10px');
            $navContainer.css('top','68px');
        }
    });
    $('.layui-input').on('focus',function () {
        if($(this).hasClass('layui-form-danger')){
            $(this).removeClass('layui-form-danger');
        }
        if($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')){
            $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
        }
    });
    $('.xm-input').on('click',function () {
        if($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')){
            $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
        }
    });

    //点击提交时不为空的全部标红
    var isSubmit = true;
    var isFirst = true;
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '' || value == null || value == undefined) {//判断是否为空
                var bdcdyxx = $("div[name='bdcdyxx']").find($(item));
                if ((isXndyh &&bdcdyxx.length ===0 && zxlc !== "true") ||(!isXndyh)) {
                    //虚拟单元号如果需要验证的元素不在不动产单元信息模块，且非注销类流程,则进行校验 或者为非虚拟号
                    if (isFirst && flag) {
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
                    $.when(saveXmxx(), saveQlxx(), saveSjcl(), saveQlr(), saveDsQlr(), saveLzr(), updateFwfsss(), insertXgLog()).done(function () {
                        loadBdcdyh();
                        removeModal();
                        if (saveMsg == '') {
                            dagsd='';
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

    });

    //form验证
    form.on('checkbox(mjdw)', function () {
        $("[name='mjdw']").prop("checked", "");
        $(this).prop("checked", "checked");
        form.render('checkbox');
    });
    form.render();
    var element = layui.element;
    element.init();
    titleShowUi();
    addModel();
    setTimeout("loadButtonArea('slympl')", 10);
    setTimeout(function () {
        try {
            $.when(generateTabContent()).done(function () {

            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e, "加载失败");
            return
        }
    }, 10);

    //获取登记原因与相应字段的的对应关系
    authorityMapByDjyyFun();

    $(function () {
        //监听第一次单击tab，
        var bdcdyhIndex = 0;
        element.on('tab(tabFilter)', function (data) {
            var tabid = $(".layui-tab-title .layui-this").attr("id");
            if (tabid === "bdcdyh") {
                if (bdcdyhIndex === 0 && !sfchangeqljbxxTab) {
                    bdcdyhIndex++;
                    addModel();
                    setTimeout(function () {
                        $.when(loadPlQlxx(), loadBdcdyh()).done(function () {
                            var a = setInterval(function () {
                                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                                    renderForm();
                                    getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                                    clearInterval(a);
                                }
                            }, 50);
                        });
                    }, 0);

                }
            }
        });

        //监听 权利信息 内 附记 单击
        $('.bdc-tab-box').on('click', '.bdc-pj-popup', function () {
            // var $thisTextarea = $(this).siblings('textarea');
            // var fjContent = $thisTextarea.val();
            // var title = $(this).parents(".layui-form-item").find("label").text();
            // layer.open({
            //     title: isNotBlank(title)? title : '附记',
            //     type: 1,
            //     area: ['960px'],
            //     btn: ['保存'],
            //     content: $('#fjPopup')
            //     , yes: function (index, layero) {
            //         //提交 的回调
            //         $thisTextarea.val($('#fjPopup textarea').val());
            //         $('#fjPopup textarea').val('');
            //         layer.close(index);
            //     }
            //     , btn2: function (index, layero) {
            //         //取消 的回调
            //         $('#fjPopup textarea').val('');
            //     }
            //     , cancel: function () {
            //         //右上角关闭回调
            //         $('#fjPopup textarea').val('');
            //     }
            //     , success: function () {
            //         $('#fjPopup textarea').val(fjContent);
            //     }
            // });
        });

        //监听 权利信息 申请人信息表格内容的input focus
        $('.bdc-tab-box').on('focus', 'td input', function () {
            if ($(this).hasClass('layui-form-danger')) {
                $(this).removeClass('layui-form-danger');
            }
            if ($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')) {
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
        });

        //监听回车事件
        $(document).keydown(function (event) {
            if($('.bdc-spf-layer').length > 0){
                var code = event.keyCode;
                if (code == 13) {
                    $('.layui-layer-btn0').click();
                    return false;
                }
            }

        });

    });
    //保存档案归属地
    form.on("submit(saveBtn)",function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            isFirst = true;
            flag = true;
            return false;
        }else{
            dagsd = $("#dagsd option:selected").val();
            saveDagsd(dagsd,processInsId);
            layer.closeAll();
            return false;
        }

    })

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
        if (sjclids.size > 0) {
            var idList = [];
            sjclids.forEach(function (element, sameElement, set) {
                idList.push(sameElement);
            });
            sessionStorage.setItem("yxsjclids", idList);
        } else {
            sessionStorage.setItem("yxsjclids", []);
        }
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
        if (sjclids.size > 0) {
            var idList = [];
            sjclids.forEach(function (element, sameElement, set) {
                idList.push(sameElement);
            });
            sessionStorage.setItem("yxsjclids", idList);
        } else {
            sessionStorage.setItem("yxsjclids", []);
        }
    });

    //监听权利tab修改证件类型
    form.on('select(zjzl)', function (data) {
        if (data.value == '1') {
            $(data.elem).parents('td').next().find('input').attr('lay-verify', 'identitynew');
        } else {
            $(data.elem).parents('td').next().find('input').attr('lay-verify', '');
        }
    });

    //监听抵押方式
    form.on('select(dyfs)', function (data) {
        var bdbzzqse =$(data.elem).parents(".basic-info").find("input[name=bdbzzqse]");
        var zgzqe =$(data.elem).parents(".basic-info").find("input[name=zgzqe]");
        //抵押方式为最高额抵押时，贷款方式为商业贷款
        if (data.value === "2") {
            addRequired(zgzqe);
            removeRequired(bdbzzqse);
            //定位贷款方式字段
            var $dkfs = $("select[name =dkfs]");
            if ($dkfs.length > 0) {
                $dkfs.val("商业贷款");
                form.render("select");
                resetSelectDisabledCss();
            }
        }else {
            addRequired(bdbzzqse);
            removeRequired(zgzqe);
        }
    });

    //监听查询契税税票
    $('#queryQssp').click(function (e) {
        queryQssp();
    });

    //监听领证方式，领证方式为ems邮寄时，展示邮寄信息
    form.on('select(lzfs)', function(data){
        var id = data.elem.getAttribute("id");
        var index = id.substr(id.indexOf("lzfs")+4,id.length);
        if (data.value === "1"){
            // 生成邮寄信息
            $("#showYjxx"+index).attr("style", "display:off");
        }else{
            // 隐藏邮寄信息
            $("#showYjxx"+index).attr("style", "display:none");
        }
        var xmfb = {
            lzfs: data.value
        };
        // 更新领证方式
        saveLzfsPl(processInsId, data.value);
        // 如果选择电子证照，清空领证人信息
        if (data.value === "4") {
            deleteLzrPl(xmfb);

        } else {
            var qlrList = [];
            // 获取sxh为1的权利人
            getReturnData("/slym/qlr/list", {processInsId: processInsId, xmid: xmid, qlrlb: 1}, 'GET', function (data) {
                // 查询qlr有根据sxh排序,取第一个
                if (data && data.length > 0) {
                    qlrList.push(data[0]);
                }
                // 先删除，后插入领证人
                if (qlrList.length > 0) {
                    delAndSaveLzr(xmfb, qlrList[0]);

                }
            }, function (err) {
                console.log(err);
            }, false);


        }
    });

    //重新生成权利其他状况
    $(".bdc-form-div").on('click','.resetqlqtzk', function () {
        addModel();
        var xmid =$(this).data("xmid");
        reGenerateQlqtzk(xmid,"2",$(this));
    });

    /**
     * 重新生成权利其他状况
     */
    function reGenerateQlqtzk(xmid,mode,btn){
        var $nowTab =$(btn).parents(".layui-tab-item");
        var $qlqtzk = $nowTab.find("textarea[name='bfqlqtzk']");
        if($qlqtzk.length >0) {
            $qlqtzk.val("");
            //重新加载模板数据
            queryQlqtzkFjMb(xmid, $qlqtzk, mode,false);
        }
    }

    //重新生成附记
    $(".bdc-form-div").on('click','.resetfj', function () {
        var xmid =$(this).data("xmid");
        reGenerateFj(xmid,"3",$(this));
    });

    /**
     * 重新生成附记
     */
    function reGenerateFj(xmid,mode,btn){
        var $nowTab =$(btn).parents(".layui-tab-item");
        var $fj = $nowTab.find("textarea[name='fj']");
        if($fj.length >0) {
            $fj.val("");
            //重新加载模板数据
            queryQlqtzkFjMb(xmid, $fj, mode,false);
        }
    }
});

function loadJbxx() {
    $.ajax({
        url: getContextPath() + "/slym/xm",
        type: 'GET',
        dataType: 'json',
        data: {processInsId: processInsId},
        success: function (data) {
            removeModal();
            if (isNotBlank(data)) {
                xmid = data.xmid;

                getReturnData("/slym/xm/fb", {xmid: xmid}, "GET", function (result) {

                    slbh = data.slbh;
                    //面积单位为空时默认为平方米
                    if (data.mjdw === null || data.mjdw === '') {
                        data.mjdw = '1'
                    }
                    if (isNotBlank(data.djxl)) {
                        getDjyyList(data.djxl);
                    }
                    generateJbxx(data,result);

                }, function (xhr) {
                    delAjaxErrorMsg(xhr);
                });

            }
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
    var filteQlrArray = new Array();
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
    if (isNotBlank(bdcQlrDO.zjh) &&bdcQlrDO.zjh.length == 18) {
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

// 导出Excel
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


    tempForm.on("submit", function () {});
    tempForm.trigger("submit");
    $("body").append(tempForm);
    tempForm.submit();
    $("exportFrom").remove();
    return false;
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



function getQlCols() {
    var cols = [];
    //根据权利类型与数据列对应关系获取对应数据列
    if (qllx === 4 || qllx === 6 || qllx === 8) {

        /**
         * 房地产权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'ycqzh', title: '原产权证号', minWidth: 280},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },
            {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {
                field: 'tdsyjssj', title: '土地使用结束期限', minWidth: 150,
                templet: function (d) {
                    if (d.tdsyjssj) {
                        return Format(format(d.tdsyjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'fwlx', title: '房屋类型', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.fwlx, zdList.fwlx);
                }
            },
            {
                field: 'fwxz', title: '房屋性质', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.fwxz, zdList.fwxz);
                }
            },
            {field: 'zcs', title: '总层数', minWidth: 100},
            {field: 'szc', title: '所在物理层', minWidth: 100},
            {field: 'szmyc', title: '所在名义层', minWidth: 100},
            {
                field: 'fwjg', title: '房屋结构', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.fwjg, zdList.fwjg);
                }
            },
            {
                field: 'fwjgmc', title: '房屋结构名称', minWidth: 150,
            },
            {
                field: 'ghyt', title: '规划用途', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.ghyt, zdList.fwyt);
                }
            },
            {
                field: 'ytmc', title: '用途名称', minWidth: 150,
            },
            {field: 'jyjg', title: '房地产交易价格', minWidth: 180},
            {
                field: 'jgsj', title: '竣工时间', minWidth: 150
            },
            {field: 'jzmj', title: '建筑面积', minWidth: 100},
            {field: 'zyjzmj', title: '专有建筑面积', minWidth: 120},
            {field: 'ftjzmj', title: '分摊建筑面积', minWidth: 120},
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === parseInt(commonDyaq_qllx)) {
        /**
         * 抵押权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'ycqzh', title: '原产权证号', minWidth: 250},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },
            {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {
                field: 'dyfs', title: '抵押方式', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.dyfs, zdList.dyfs);
                }
            },
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === 3 || qllx === 7) {

        /**
         * 建设用地使用权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'ycqzh', title: '原产权证号', minWidth: 280},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            }, {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {
                field: 'syqqssj', title: '使用权起始时间', minWidth: 150,
                templet: function (d) {
                    if (d.syqqssj) {
                        return Format(format(d.syqqssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'syqjssj', title: '使用权结束时间', minWidth: 150,
                templet: function (d) {
                    if (d.syqjssj) {
                        return Format(format(d.syqjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === 19) {
        /**
         * 地役权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'ycqzh', title: '原产权证号', minWidth: 250},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            }, {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}

        ];

    }
    else if (qllx === 97) {
        /**
         * 异议数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'ycqzh', title: '原产权证号', minWidth: 250},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            }, {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}

        ];

    }
    else if (qllx === 98) {

        /**
         * 查封数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {
                field: 'cflx', title: '查封类型', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.cflx, zdList.cflx);
                }
            },
            {field: 'ycqzh', title: '原产权证号', minWidth: 250},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            }, {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'cfwh', title: '查封文号', minWidth: 200},
            {field: 'cfjg', title: '查封机关', minWidth: 150},
            {field: 'cfwj', title: '查封文件', minWidth: 150},
            {
                field: 'cfqssj', title: '查封起始时间', minWidth: 150,
                templet: function (d) {
                    if (d.cfqssj) {
                        return Format(format(d.cfqssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'cfjssj', title: '查封结束时间', minWidth: 150,
                templet: function (d) {
                    if (d.cfjssj) {
                        return Format(format(d.cfjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {field: 'cffw', title: '查封范围', minWidth: 200},
            {field: 'jfwh', title: '解封文号', minWidth: 200},
            {field: 'jfjg', title: '解封机关', minWidth: 150},
            {field: 'jfwj', title: '解封文件', minWidth: 150},
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === 96) {
        if (sfdydj) {
            /**
             * 预抵押数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'xh', title: '序号', width: 50, type: 'numbers'},
                {field: 'ycqzh', title: '原产权证号', minWidth: 250},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'dzwmj', title: '定着物面积', minWidth: 240},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {
                    field: 'qlxz', title: '权利性质', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.qlxz, zdList.qlxz);
                    }
                }, {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
                {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
                {field: 'zh', title: '幢号', minWidth: 100},
                {
                    field: 'ygdjzl', title: '预告登记种类', minWidth: 200,
                    templet: function (d) {
                        return changeDmToMc(d.ygdjzl, zdList.ygdjzl);
                    }
                },
                {
                    field: 'dyfs', title: '抵押方式', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.dyfs, zdList.dyfs);
                    }
                },
                {field: 'qdjg', title: '被担保主债权数额', minWidth: 200},
                {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];

        } else {

            /**
             * 预告数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'xh', title: '序号', width: 50, type: 'numbers'},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'dzwmj', title: '定着物面积', minWidth: 240},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {
                    field: 'qlxz', title: '权利性质', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.qlxz, zdList.qlxz);
                    }
                }, {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
                {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
                {field: 'zh', title: '幢号', minWidth: 100},
                {
                    field: 'ygdjzl', title: '预告登记种类', minWidth: 200,
                    templet: function (d) {
                        return changeDmToMc(d.ygdjzl, zdList.ygdjzl);
                    }
                },
                {field: 'zcs', title: '总层数', minWidth: 100},
                {field: 'szc', title: '所在物理层', minWidth: 100},
                {field: 'szmyc', title: '所在名义层', minWidth: 100},
                {field: 'jzmj', title: '建筑面积', minWidth: 100},
                {
                    field: 'fwxz', title: '房屋性质', minWidth: 150,
                    templet: function (d) {
                        return changeDmToMc(d.fwxz, zdList.fwxz);
                    }
                },
                {
                    field: 'ghyt', title: '规划用途', minWidth: 150,
                    templet: function (d) {
                        return changeDmToMc(d.ghyt, zdList.fwyt);
                    }
                },
                {
                    field: 'ytmc', title: '用途名称', minWidth: 150,
                },
                {field: 'tdsyqr', title: '土地使用权人', minWidth: 200},
                {field: 'jyje', title: '交易金额', minWidth: 200},
                {
                    field: 'tdsyqssj', title: '土地使用起始时间', minWidth: 150,
                    templet: function (d) {
                        if (d.tdsyqssj) {
                            return Format(format(d.tdsyqssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'tdsyjssj', title: '土地使用结束时间', minWidth: 150,
                    templet: function (d) {
                        if (d.tdsyjssj) {
                            return Format(format(d.tdsyjssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        }
    } else if (qllx === 92) {
        /**
         * 居住权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'ycqzh', title: '原产权证号', minWidth: 250},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },
            {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'jztjhyq', title: '居住条件和要求', minWidth: 140},
            {
                field: 'jzqkssj', title: '居住权起始时间', minWidth: 150,
                templet: function (d) {
                    if (d.jzqkssj) {
                        return Format(format(d.jzqkssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'jzqjssj', title: '居住权结束时间', minWidth: 150,
                templet: function (d) {
                    if (d.jzqjssj) {
                        return Format(format(d.jzqjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else {
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            }, {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}

        ];

    }
    return cols;
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

function checkXndyhPl() {
    getReturnData("/slym/xm/checkXndyh", {gzlslid: processInsId}, "GET", function (data) {
        isXndyh = data;
    }, function (error) {
        delAjaxErrorMsg(error);

    });
}

/**
 * 根据登记原因控制页面权限初始化方法
 */
function initAuthorityByDjyy(djyyVal,djyyId,eventlx,type) {
    changeAuthorityByDjyy(djyyVal,djyyId,eventlx,type);
}

/**
 * 根据登记原因控制页面权限
 */
function changeAuthorityByDjyy(djyyVal,djyyId,eventlx,type) {
    qlxxChangeIds = "";
    if (eventlx ==="change"&&isNotBlank(editElementForDjyy)) {
        var editElement =editElementForDjyy;
        //设置原有元素不可编辑
        $.each(editElement.split(","), function (index, elementName) {
            //设置不可编辑
            var $element = $("[name='" + elementName + "']");
            if ($element.length > 0) {
                $element.attr("disabled", "off");
                var tagName = $element[0].tagName;
                if (tagName === "SELECT") {
                    form.render("select");
                    resetSelectDisabledCss();
                }
                //加锁
                var imgArray = $element.parent().find("img");
                if (imgArray.length === 0) {
                    $element.parent().append('<img src="../../static/lib/bdcui/images/lock.png" alt="">');
                }
            }
        });

    }
    if (isNotBlank(djyyVal)) {
        getReturnData("/slym/xm/getAuthorityByDjyy", {djyy: djyyVal}, "GET", function (data) {
            if (isNotBlank(data)) {
                editElementForDjyy=data;
                $.each(data.split(","), function (index, elementName) {
                    //设置可编辑
                    var $element = $("[name='" + elementName + "']");
                    if ($element.length > 0) {
                        var tagName = $element[0].tagName;
                        $element.parents(".layui-input-inline").removeClass("bdc-one-icon");
                        var img = $element.parents(".layui-input-inline").find("img");
                        if (img.length > 0) {
                            $(img).remove();
                        }
                        $element.removeAttr("disabled");
                        if (tagName === "SELECT") {
                            form.render("select");
                            resetSelectDisabledCss();
                        }
                    }else{
                        qlxxChangeIds = data;
                    }
                });
            }
        }, function (error) {
            delAjaxErrorMsg(error);
        });
    }
}



/**
 * 获取登记原因与相应字段的对应关系
 */
function authorityMapByDjyyFun(){
    var obj = {};
    $.ajax({
        url: getContextPath() + '/slym/xm/authorityMapByDjyy',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            authorityMapByDjyy = data;
        }
    });
}

//根据审批来源控制按钮隐藏
function setSplyBtnAttr(){
    //互联网
    if(sply ===3 || sply ===17|| sply ===18 || sply ===19 || sply ===15 || sply ===22
        || sply ===5 || sply ===11){
        // 涉税流程按钮不可编辑
        $('.sslc').css({'color':'#666','pointer-events':'none'});
    }
    //互联网涉税
    if(sply ===5 || sply ===11){
        //获取交易信息不可编辑
        $(".queryJyxx").removeAttr("onclick");
        $(".queryJyxx").addClass("bdc-prohibit-btn");
    }
}

//对表单的一系列渲染操作
function renderForm(){
    //表单权限控制
    setSplyBtnAttr();
}



//关闭panel
function cancelEdit() {
    layer.closeAll();
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



function changeQlrlbMc(qllx) {
    var qlrlbzd = zdList.qlrlb;
    if (qllx===97){
        for (var j = 0; j < qlrlbzd.length; j++) {
            if (qlrlbzd[j].DM === 1) {
                qlrlbzd[j].MC = "权利人(申请人)";
            }
        }
    }
    if (qllx===98){
        for (var j = 0; j < qlrlbzd.length; j++) {
            if (qlrlbzd[j].DM === 1) {
                qlrlbzd[j].MC = "执行人";
            }
            if (qlrlbzd[j].DM === 2){
                qlrlbzd[j].MC = "被执行人";
            }
        }
    }
    zdList.qlrlb = qlrlbzd;
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

//---------------------页面加载---------------------------------------
//按钮加载
function loadButtonArea(ymlx) {
    var printDTO = queryPrintQlxx(processInsId, zxlc);
    var json = {lclx: printDTO[0].lclx, qlxx: printDTO, printBtn: slymPrintBtn, fkdy: zhlcfkdy};
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

    $("#dagsdBtn").click(function () {
        var datas = {
            zd: zdList,
            xmfb: xmfbList
        }
        if (xmfbList == null || isNullOrEmpty(xmfbList.dagsd)){
            if (dagsd != '' && dagsd != null){
                datas.xmfb.dagsd = dagsd;
            }
        }
        if (datas.xmfb.dagsd != dagsd && (dagsd != '' || dagsd != null)){
            datas.xmfb.dagsd = dagsd;
        }
        var gsdtpl = dagsdTpl.innerHTML,view1 = document.getElementById("dagsdDiv");
        laytpl(gsdtpl).render(datas,function (html) {
            view1.innerHTML = html;
        });
        flag = false;
        getStateById(readOnly, formStateId, 'slympl');
        form.render();
        layer.open({
            title:"档案归属地",
            type: 1,
            // skin: 'bdc-spf-layer',
            area: ['360px','460px'],
            // btn: ['确定', '取消'],
            content:$("#bdc-dagsd"),
            // yes:function (index, layero) {
            //     dagsd = $("#dagsd option:selected").val();
            //     saveDagsd(dagsd,processInsId);
            //     layer.closeAll();
            // }
        });
    });

    // 地籍调查表/海籍调查表
    $("#djdcb").click(function () {
        djdcb(processInsId);
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

    //一窗办理
    $("#tsYcsl").click(function () {
        syncYcsl();
    });

    // 查看变更记录
    $("#ckbgjl").click(function(){
        showBgjl();
    });

    //评价器
    $("#evaluate").click(function () {
        commonPj(processInsId,taskId);
    });

    //人证对比
    $("#rzdb").click(function () {
        commonRzdb("","",processInsId);
    });

    //外联证书
    $("#glzs").click(function () {
        glzs();
    });
}

function titleShowUi() {

    //打印点击
    $(".print-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#moreBtn").hide();
        $("#query").hide();
        $("#print").show();
        setUiWidth($(this), $("#print"));
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
        $("#query").hide();
        $("#moreBtn").show();
        setUiWidth($(this), $("#moreBtn"));
    });

    // 查询点击
    $(".query-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#moreBtn").hide();
        $("#query").show();
        setUiWidth($(this), $("#query"));
    });

    $(".print-btn li").click(function () {
        $("#print").hide();
    });

    $(".more-btn li").click(function () {
        $("#moreBtn").hide();
    });

    $(".query-btn li").click(function () {
        $("#query").hide();
    });

    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && (elem.id == 'print' || elem.id == 'moreBtn' || elem.id == 'query')) {
                return;
            }
            $("#print").hide();
            $("#jyxx").hide();
            $("#moreBtn").hide();
            $("#query").hide();
            elem = elem.parentNode;
        }
    });
}


//获取权力信息打印按钮需要使用的参数
function queryPrintQlxx(gzlslid, zxlc) {
    var printDTO = {};
    getReturnData("/slym/ql/print", {gzlslid: gzlslid, zxlc: zxlc}, "GET", function (data) {
        if (data) {
            printDTO = data;
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    }, false)
    return printDTO;
}

function setUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    var w = buttonElement.innerWidth();
    var scrollH = $(document).scrollTop();
    if (scrollH > 0) {
        uiElement.css({top: X + 42 - scrollH, right: $('body').width() - Y - w - 15});
    } else {
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

//加载tab页
function generateTabContent() {
    var xmxx;
    var qlxx;
    var xmfb ={};
    $.ajax({
        url: getContextPath() + "/slym/xm",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {processInsId: processInsId},
        success: function (data) {
            if (isNotBlank(data)) {
                qllx = data.qllx;
                xmid = data.xmid;
                qszt =data.qszt;
                sply = data.sply;
                ydjyy =data.djyy;

                getReturnData("/slym/xm/fb", {xmid: xmid}, "GET", function (result) {
                    xmfb =result;
                    dagsd = result.dagsd;
                    xmfbList = result;
                },function (xhr) {
                    delAjaxErrorMsg(xhr);
                },false);
                //面积单位为空时默认为平方米
                if (data.mjdw === null || data.mjdw === '') {
                    data.mjdw = '1'
                }
                if(data.djlx ===100 &&(data.qllx ===4 ||data.qllx ===6 ||data.qllx ===8)){
                    sfscdj =true;
                }
                if (isNotBlank(data.djxl)) {
                    getDjyyList(data.djxl);
                }
                xmxx = data;

                getReturnData("/slym/ql/pllc", {processInsId: processInsId, sfcxql: false}, 'GET', function (data) {
                    if (isNotBlank(data)) {
                        qlxxForPrint = data;
                        qlxx = data[0];
                        sfdydj = data[0].dydj;
                    }
                }, function (err) {
                    console.log(err);
                }, false);
                //判断是否是虚拟单元号
                checkXndyhPl();
            }

        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {
            qllx: qllx,
            qlxx: qlxx
        };
        json['sfchangeqljbxxTab'] = sfchangeqljbxxTab;
        var liTpl = liTableTpl.innerHTML, liView = document.getElementById('tableUi');
        //渲染数据
        laytpl(liTpl).render(json, function (html) {
            liView.innerHTML = liView.innerHTML + html;
        });
        tpl = tabContentTpL.innerHTML, view = document.getElementById("tabContent");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        renderDate(form, formIds);
        renderSelect();
        loadData(xmxx,xmfb);

        if (sfchangeqljbxxTab) {
            $.when(loadPlQlxx(), loadBdcdyh()).done(function () {
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                        clearInterval(a);
                    }
                }, 50);
            });
        }
    });

    // 记录表单第一次加载的业务数据至ES中
    addYwxxLog();
}

//获取登记原因
function getDjyyList(djxl) {
    $.ajax({
        url: getContextPath() + "/slym/xm/queryDjxlDjyy",
        type: 'GET',
        dataType: 'json',
        data: {djxl: djxl},
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                djxldjyyList = data;
            }
        }
    });
}

//加载页面
function loadData(xmxx,xmfb) {
    getReturnData("/bdczd", '', 'POST', function (data) {
        removeModal();
        if (isNotBlank(data)) {
            zdList = data;
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    //加载基本信息
    generateJbxx(xmxx,xmfb);
    //加载申请人
    loadQlr();
    //加载收件材料
    loadSjcl();
}

//基本信息加载
function generateJbxx(bdcxmxx,xmfb) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var qllx = bdcxmxx.qllx;
        if (qllx === 97) {
            changeQlrlbMc(qllx);
        }
        if (qllx === 98) {
            changeQlrlbMc(qllx);
        }
        //申请分别持证为空默认为是
        if(bdcxmxx &&bdcxmxx.sqfbcz ==null){
            bdcxmxx.sqfbcz =1;
        }
        var json = {
            bdcxmxx: bdcxmxx,
            zd: zdList,
            djxldjyy: djxldjyyList,
            commonJedw:commonJedw,
            xmfb:xmfb
        };
        var tpl = jbxxTpl.innerHTML, view = document.getElementById('sljbXx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        //抵押,查封,预抵押,异议,地役的登记原因和是否分别持证在权利页面不需要再次加载
        //zsmodel有值的话说明是商品房首次登记
        if (qllx !== parseInt(commonDyaq_qllx) && !sfdydj && qllx !== 97 && qllx !== 98 && qllx !== 19) {
            var xmxxtpl = xmxxTpl.innerHTML, xmxxview = document.getElementById('xmxx');
            //渲染数据
            laytpl(xmxxtpl).render(json, function (html) {
                xmxxview.innerHTML = html;
            });

        }
        form.render();
        renderDate(form, formIds);
        //给下拉框添加删除图标
        renderSelectClose(form);
        renderForm();
        getStateById(readOnly, formStateId, 'slympl');
        //监听修改字段
        if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(bdcxmxx.djxl) > -1) {
            formIds = "tabContent";
            renderChange("", form, formIds);
        }
        renderSelect();
        disabledAddFa();
    })
}

//加载权利人
function loadQlr() {
    addModel();
    //权利人
    $.ajax({
        url: getContextPath() + "/slym/qlr/list",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {processInsId: processInsId, xmid: xmid, qlrlb: 1},
        success: function (data) {
            if (data) {
                qlrLength = data.length;
            }
            removeModal();
            generateQlrxx(data, "sqrxx");
            //重新加载抵押人查封机关信息
            reloadDyrAndCfjg(xmid, "");
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    //义务人
    $.ajax({
        url: getContextPath() + "/slym/qlr/groupywr?gzlslid="+processInsId,
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: {},
        success: function (data) {
            removeModal();
            generateQlrxx(filterQlrZh(data), "qlrTable");
            //加载权利信息模块部分信息
            reloadQlxxForYwr(data);
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function generateQlrxx(data, id) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var showJtcyBtn =false;
        if(showJtcyQllxList &&showJtcyQllxList.length>0 &&showJtcyQllxList.indexOf(qllx) >-1){
            showJtcyBtn =true;
        }
        var json = {
            bdcQlrDOList: data,
            zd: zdList,
            qlrLength: qlrLength,
            showJtcyBtn:showJtcyBtn
        };

        if (id == 'qlrTable') {
            //无数据隐藏
            if (data && data.length > 0) {
                $("#qlrTable .bdc-table-none").hide();
            }
            //渲染义务人
            var tpl = ywrTpl.innerHTML, view = $('#' + id).find('tbody');
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.append(html);
            });
        } else {
            var tpl = sqrTpl.innerHTML, view = document.getElementById(id);
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
        }
        form.render();
        renderForm();
        getStateById(readOnly, formStateId, 'slympl', id);
        //合肥联系电话加密显示
        toEncryptClass('dhjm');
        renderSelect();
        disabledAddFa(id);
    })
}

function loadPlQlxx() {
    getReturnData("/slym/ql/pllc", {processInsId: processInsId, xmid: xmid, sfcxql: true}, 'GET', function (data) {
        removeModal();
        if (isNotBlank(data)) {
            buildQlxx(data);
            className = data[0].className;
        }
        dyfsbt(data);
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}
//抵押方式联动必填
function dyfsbt(data) {
    if (isNotBlank(data)) {
        var dyfs = $("#dyaq-dyfs").val();
        if (isNotBlank(dyfs)) {
            var qllx = data[0].qllx;
            if (qllx === 37) {
                //抵押方式为最高额抵押是控制必填
                if (data[0].bdcQl.dyfs === 2) {
                    addRequired($("#dyaq-zgzqe"));
                    removeRequired($("#dyaq-bdbzzqse"));
                } else {
                    addRequired($("#dyaq-bdbzzqse"));
                    removeRequired($("#dyaq-zgzqe"));
                }
            }
        }else {
            removeRequired($("#dyaq-bdbzzqse"));
            removeRequired($("#dyaq-zgzqe"));
        }
    }

}

function buildQlxx(bdcSlQlxxymDTOList) {
    //批量流程组织权利信息到页面，只需要展示其中一个权利即可
    if (isNotBlank(bdcSlQlxxymDTOList)) {
        var bdcSlQlxxym = bdcSlQlxxymDTOList[0];
        var qllx = bdcSlQlxxym.qllx;
        sfdydj = bdcSlQlxxym.dydj;
        ydjyy = bdcSlQlxxym.bdcXm.djyy;
        //申请分别持证为空默认为是
        if(bdcSlQlxxym &&bdcSlQlxxym.bdcXm &&bdcSlQlxxym.bdcXm.sqfbcz ==null){
            bdcSlQlxxym.bdcXm.sqfbcz =1;
        }
        //抵押，预抵押，异议，查封，地役权展现在外面的权利字段 +++ 预告注销展现字段
        if (qllx === parseInt(commonDyaq_qllx) || bdcSlQlxxym.dydj || qllx === 97 || qllx === 98 || qllx === 19 || (qllx === 96 && zxlc === "true")) {
            var json = {
                bdcSlQlxxym: bdcSlQlxxym,
                zd: zdList,
                djxldjyy: djxldjyyList,
                dkfs: dkfs,
                zxlc: zxlc
            };
            var bdcdyfwlx = "";
            if (isNotBlank(bdcSlQlxxym.bdcQl.bdcdyfwlx) && bdcSlQlxxym.bdcQl.bdcdyfwlx === 1) {
                bdcdyfwlx = bdcSlQlxxym.bdcQl.bdcdyfwlx;
            }
            var qllxTpl = document.getElementById(bdcSlQlxxym.tableName + bdcdyfwlx);
            if (isNotBlank(qllxTpl)) {
                var tpl = qllxTpl.innerHTML, view = document.getElementById('bdcdyqlxx');
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
                renderDate(form, formIds);
                form.render();
                renderSelect();
                //给下拉框添加删除图标
                renderSelectClose(form);
                renderForm();
                getStateById(readOnly, formStateId, 'slympl', 'qlxxTab');
                form.render();
                disabledAddFa('qlxxTab');

            }


            //监听修改字段
            if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(bdcSlQlxxym.bdcXm.djxl) > -1) {
                formIds = "tabContent";
                renderChange("", form, formIds);
            }
        } else
        {
            //展现交易信息字段
            var json = {
                zxlc: zxlc,
                zd: zdList,
                wlzs: bdcSlQlxxym.wlzs,
                xmfb: bdcSlQlxxym.bdcXmFbDO
            };
            var jyxxTpl = document.getElementById("jyxxTpl");
            if (isNotBlank(jyxxTpl)) {
                var tpl = jyxxTpl.innerHTML, view = document.getElementById('bdcdyqlxx');
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
                renderDate(form, formIds);
                form.render();
                renderSelect();
                //给下拉框添加删除图标
                renderSelectClose(form);
                renderForm();
                getStateById(readOnly, formStateId, 'slympl', 'qlxxTab');
                form.render();
                disabledAddFa("jyxxTpl");

            }

            //判断页面是否存在交易信息字段，显示即查询
            if ($(".jyxx").length > 0 && isNotBlank(bdcSlQlxxym.bdcXm.xmid)) {
                getReturnData("/ycsl/jyxx", {xmid: bdcSlQlxxym.bdcXm.xmid}, "GET", function (data) {
                    //表单赋值
                    if (data) {
                        form.val("jyxx", data);
                    }
                    renderDate(form, formIds);
                }, function (error) {
                    delAjaxErrorMsg(error);
                });
            }
        }
        //加载领证人
        //判断权利类型是预告，且预告等级种类为抵押
        if (qllx == 96 && isNotBlank(bdcSlQlxxym.bdcQl) && isNotBlank(bdcSlQlxxym.bdcQl.ygdjzl)) {
            var ygdjzl = bdcSlQlxxym.bdcQl.ygdjzl;
            loadLzrxx(bdcSlQlxxym.bdcXmFbDO, ygdjzl);
        } else {
            loadLzrxx(bdcSlQlxxym.bdcXmFbDO);
        }

        //加载第三权利人
        generateDsQlr(bdcSlQlxxym.bdcXm.xmid, $(".sqr"), "");

        getStateById(readOnly, formStateId, 'slympl');
        form.render();
        resetSelectDisabledCss();
    }
}

function loadBdcdyh() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var table = layui.table;

        var isSearch = true;
        $(document).keydown(function (event) {
            if (event.keyCode == 13 && isSearch) { //绑定回车
                $("#searchBdcdy").click();
            }
        });
        // //判断回车操作
        $('.bdc-tab-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });

        $('body').on('focus', 'textarea', function () {
            isSearch = false;
        }).on('blur', 'textarea', function () {
            isSearch = true;
        });

        //获取权利信息表头
        var unitTableTitle = getQlCols();
        var url = getContextPath() + '/slym/ql/listQlByPageJson';

        // 查询参数
        var data = {
            sortParameter: "xmid ASC"
        };
        if (zxlc === "true") {
            //注销流程
            data["sfyql"] = true;
        }
        data["qllx"] = qllx;

        // 根据当前查询参数，获取所有的单元信息，用于数据导出
        data["gzlslid"] = processInsId;
        //提交表单
        $("#searchBdcdy").click(function () {
            var bdcdyArray = $(".bdcdyForm").serializeArray();
            bdcdyArray.forEach(function (item) {
                data[item.name] = item.value;
            });
            $.when(tableReload('xmid', data, url)).done(function () {
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        renderForm();
                        getStateById(readOnly, formStateId, "slympl");
                        clearInterval(a);
                    }
                }, 50);
            });
            return false;
        });
        //重置操作
        $("#reset").on('click', function () {
            $('.bdc-form-search input').val('');
            $('.bdc-form-search select').val('');
            form.render('select');
        });

        //获取流程对应的规则用途作为选择项
        getReturnData("/slym/xm/queryZdFwytByGzlslid",{gzlslid:processInsId},"GET",function (data) {
            //清空
            $("#ghyt").empty();
            $('#ghyt').append(new Option("请选择", ""));
            $.each(data, function (index, item) {
                //防止出现对比权籍后，规划用途字典项与登记不一致出现空的情况
                if(item !== null) {
                    $('#ghyt').append(new Option(item.mc, item.dm));// 下拉菜单里添加元素
                }
            });
        },function (error) {
            console.log("用途下拉框获取失败")
        },false);


        var tableConfig = {
            id: 'xmid',
            url: url,
            contentType: 'application/json',
            method: 'post',
            where: data,
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
                        renderForm();
                        getStateById(readOnly, formStateId, "slympl");
                        clearInterval(a);
                    }
                }, 50);
            }
        };

        //加载表格
        loadDataTablbeByUrl('#bdcdyTable', tableConfig);
        renderSearchInput();

        //头工具栏事件
        table.on('toolbar(bdcdyTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === "plxg") { //批量修改
                var checkStatus = table.checkStatus(obj.config.id);
                var checkData = checkStatus.data;
                var xmids = [];
                if (checkData.length === 0) {
                    showConfirmDialog("提示", "未选择数据，默认修改全部数据?", "openPlxg", "''", "", "");
                } else {
                    for (var i = 0; i < checkData.length; i++) {
                        var xmid = checkData[i].xmid;
                        xmids.push(xmid);
                    }
                    openPlxg(xmids);
                }

            } else if (layEvent === 'export') {
                exportExcel(obj.config.cols[0]);
            }  else if (layEvent === "add") {
                var bdcGzYzQO ={};
                bdcGzYzQO.zhbs = "SLYM_ADDBDCDY";
                var gzyzParamMap={};
                gzyzParamMap.gzlslid =processInsId;
                bdcGzYzQO.paramMap = gzyzParamMap;
                gzyzCommon(2,bdcGzYzQO,function (data) {
                    console.log("验证通过");
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
                var bdcGzYzQO ={};
                bdcGzYzQO.zhbs = "SLYM_SCBDCDY";
                var gzyzParamMap={};
                gzyzParamMap.gzlslid =processInsId;
                bdcGzYzQO.paramMap = gzyzParamMap;
                gzyzCommon(2,bdcGzYzQO,function (data) {
                    console.log("验证通过");
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
                            ityzl_SHOW_SUCCESS_LAYER("删除成功");
                            addModel();
                            // 先查询不动产单元数量
                            var sfyql = false;
                            if (zxlc === "true") {
                                sfyql = true;
                            }
                            var obj = {};
                            obj.sfyql = sfyql;
                            obj.qllx = qllx;
                            obj.loadTotal = false;
                            obj.page = 1;
                            obj.size = 10;
                            obj.gzlslid = processInsId;
                            getReturnData("/slym/ql/listQlByPageJson", JSON.stringify(obj), 'post', function (data) {
                                removeModal();
                                // 如果删除后只剩下单个的不动产单元页面，刷新 portal 页面
                                if (data.totalElements === 1) {
                                    var getUrl = parent.window.location.href;
                                    setTimeout(function () {
                                        parent.window.location.href = getUrl;
                                    }, 100);

                                    var newUlr = changeURLPar(getUrl, 'timestamp', new Date().getTime());
                                    parent.parent.location.href = newUlr;
                                } else {
                                    loadBdcdyh();
                                    loadQlr();
                                }
                            });
                        }, function (err) {
                            delAjaxErrorMsg(err);
                        });
                    }

                });


            } else if (layEvent === "pldjls") { //批量登记历史
                window.open("/realestate-register-ui/view/lsgx/pldjls.html?gzlslid=" + processInsId);
            } else if (layEvent === "lpb") {
                //楼盘表
                lpb();

            } else if(layEvent === "gwc") {
                openSlymGwc();
            }

            return false;
        });
        //监听单元格事件
        table.on('tool(bdcdyTable)', function(obj){
            var data={},
                $this = $(obj.tr[0]).find('td[data-field="fj"] .layui-table-cell');
            data.qlid = obj.data.qlid;
            if(obj.event === 'editFj'){
                layer.prompt({
                    formType: 2
                    ,title: '附记'
                    ,value: obj.data.fj
                    ,btn: ['保存', '取消']
                }, function(value, index){
                    layer.close(index);
                    data.fj = value;
                    //中文括号转为英文括号
                    if (isNotBlank(data.fj)) {
                        data.fj =  data.fj.replace('（', '(');
                        data.fj =  data.fj.replace('）', ')');
                    }
                    //这里一般是发送修改的Ajax请求
                    if (isNotBlank(className)&&isNotBlank(data.qlid)) {
                        getReturnData("/slym/ql?className=" + className, JSON.stringify(data), 'PATCH', function (data) {
                            ityzl_SHOW_SUCCESS_LAYER("附记保存成功！");
                        }, function (err) {
                            delAjaxErrorMsg(err);
                        }, false);
                    }else {
                        ityzl_SHOW_WARN_LAYER("未获取到权利！");
                    }
                    //同步更新表格和缓存对应的值
                    if(isNotBlank(obj.data.fj)){
                        obj.update({
                            fj: data.fj
                        });
                    }else {
                        $this.html('<span>'+ data.fj + '</span>');
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
            scrollTop = $(document).scrollTop();
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

    })

}

//记录流程第一次加载的业务数据
function addYwxxLog(){
    getReturnData("/rest/v1.0/bgxxdb/es",{gzlslid : processInsId},"GET",function (data) {
    },function (error) {});
}

//权利人，义务人发生改变后同步修改权利表权利人义务人信息
function reloadDyrAndCfjg(xmid, indexnum) {
    if (isNotBlank(xmid)) {
        var xydqlr = $("#dyiq-xydqlr");
        if ((xydqlr != null &&xydqlr.length>0)) {
            var bdcxm;
            getReturnData("/slym/xm/xx", {xmid: xmid}, 'GET', function (data) {
                bdcxm = data
            }, function (err) {
                console.log(err);
            }, false);

            if (xydqlr) {
                //需役地权利人
                $(xydqlr).val(bdcxm.qlr);
                xydqlr.title = bdcxm.qlr;
            }
        }
        var cfjg = document.getElementById('cf-cfjg');
        var jfjg = document.getElementById('cf-jfjg');

        var qllx = qllx = $($("input[name='qllx']")[0]).val();
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

//处理权利信息中义务人相关信息
function reloadQlxxForYwr(ywrdata) {
    //抵押人
    var dyr = $("#dyaq-dyr");
    //供役地权利人
    var gydqlr = $("#dyiq-gydqlr");
    if ((dyr != null &&dyr.length >0)|| (gydqlr != null &&gydqlr.length >0)) {
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

//加载收件材料
function loadSjcl() {
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/sjcl/list/pl",
        type: 'GET',
        dataType: 'json',
        data: {processInsId: processInsId},
        success: function (data) {
            removeModal();
            generateSjcl(data);
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function generateSjcl(data) {
    sjclidLists = [];
    if (data !== null && data.length > 0) {
        for (var i = 0; i < data.length; i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    sessionStorage.setItem("sjclidLists", sjclidLists);
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {
            bdcSlSjclDOList: data,
            zd: zdList
        };
        sjclNumber = data.length;
        var tpl = sjclTpl.innerHTML, view = document.getElementById('sjclxx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
            form.render('checkbox');

        });
        form.render('select');
        renderForm();
        getStateById(readOnly, formStateId, "slympl");
        renderSelect();
        form.render('select');
        disabledAddFa("sjclForm");
    })
}

//---------------------保存---------------------------------------
//项目信息保存（批量保存）
function saveXmxx() {
    var bdcXmData = {};
    var bdcXmArray = $(".bdcxm");
    bdcXmArray.serializeArray().forEach(function (item, index) {
        bdcXmData[item.name] = item.value;
    });
    bdcXmData.bz = $("#bz").val();
    var djyy = $("#djyy").val();
    if (djyy !== undefined) {
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
        if(sfchange) {
            reCreateSjcl();
        }
    }, function (err) {
        throw err;
    }, false);
}

//权利信息保存（项目附表,权利信息,交易信息）
function saveQlxx() {
    saveXmfbPl(".xmfb");
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
    if (!$.isEmptyObject(qlData) && isNotBlank(xmid)) {
        getReturnData("/slym/ql/list?processInsId=" + processInsId + "&onexmid=" + xmid + "&zxlc=" + zxlc, JSON.stringify(qlData), 'PATCH', function (data) {

        }, function (err) {
            delAjaxErrorMsg(err);
        });

    }

    //保存交易信息
    saveJyxxPl();
}

// 项目附表保存
function saveXmfbPl(formClass,djxl) {
    var bdcXmfbData = {};
    var bdcXmfbArray = $(formClass);
    if (bdcXmfbArray !== null && bdcXmfbArray.length > 0) {
        bdcXmfbArray.serializeArray().forEach(function (item, index) {
            bdcXmfbData[item.name] = item.value;
        });
    }
    if (!$.isEmptyObject(bdcXmfbData)) {
        var bdcDjxxUpdateQO = {};
        var whereMap = {};
        whereMap.gzlslid = processInsId;
        if (isNotBlank(djxl)) {
            whereMap.djxl = djxl;
        }
        bdcDjxxUpdateQO.whereMap = whereMap;
        bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmfbData);
        getReturnData("/slym/xm/plxgxmfb", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
            console.log("批量保存项目附表信息成功");
        }, function (err) {
            throw err;
        });
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

//登记原因改变重新创建收件材料
function reCreateSjcl() {
    getReturnData("/slym/sjcl/recreate", {gzlslid: processInsId}, "GET", function (data) {
        if (data !== null && data !== '' && data !== undefined) {
            loadSjcl();
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr, "重新生成收件材料失败");
    })
}


/*
* 领证人保存
* */
function saveLzr() {
    var lzrArrayPllc = $(".lzxx").serializeArray();
    saveAllLzr(lzrArrayPllc, "");
}


//领证人保存
function saveAllLzr(lzrArray, c) {
    var lzrList = [];
    var lzr = {};
    for (var m = 0; m < lzrArray.length; m++) {
        var name = lzrArray[m].name;
        lzr[name] = lzrArray[m].value;
        if ((m + 1) % 7 === 0) {
            if (isNotBlank(lzr.lzrmc)) {
                lzrList.push(lzr);
            }
            lzr = {};
        }
    }
    var url = "";
    if (lclx === "pllc") {
        url = "/slym/lzr/nt/lzrxx/pllc?gzlslid=" + processInsId + "&xmid=" + xmid;
    }
    if (isNotBlank(lzrList)) {
        getReturnData(url, JSON.stringify(lzrList), "POST", function (data) {

        }, function (xhr) {
            delAjaxErrorMsg(xhr)
        }, false)
    }
}

//权利人保存
function saveQlr() {
    var qlrArrayPllc = $(".qlr").serializeArray();
    saveAllQlr(qlrArrayPllc, "");
    loadQlr();
}

//权利人保存
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
                // initLzrxx(qlr);
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
    qlmc = tabs[0].innerText;
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

    // 处理权利比例数据，去除小数点之前多余的 0
    $.each(qlrList, function(index, value){
        value.qlbl = replaceBeforePointZero(value.qlbl);
    });

    if (!checkAddGybl(qlrList)) {
        throw err = new Error('共有比例不正确，请检查！');
    }
    var url = "/slym/qlr/list/pllc?processInsId=" + processInsId + "&xmid=" + xmid;
    if (isNotBlank(qlrList)) {
        getReturnData(url, JSON.stringify(qlrList), 'PATCH', function (data) {
            if (data > 0) {
            }
        }, function (err) {
            throw err;
        }, false);
    }


}

//处理领证人信息
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
    var url = "/slym/lzr/hf/lzrxx/pllc?gzlslid=" + processInsId + "&xmid=" +  qlr.xmid + "&qlrid=" + qlr.qlrid;
    getReturnData(url,JSON.stringify(lzrList),'PATCH',function (data) {

    },function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    })
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

//修改日志保存
function insertXgLog() {
    var tsxx = $("#updateTips p").text();
    if (isNotBlank(tsxx)) {
        var bdcXxXgDTO = new Object();
        bdcXxXgDTO.tsxx = tsxx;
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

//---------------------按钮操作---------------------------------------
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

//同步权籍数据
function synchLpbDataToLc() {
    getReturnData("/rest/v1.0/slym/synchLpbDataToLc?processInsId=" + processInsId, '', 'PATCH', function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("同步数据成功");
        $.when(loadBdcdyh()).done(function () {
            var a = setInterval(function () {
                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                    getStateById(readOnly, formStateId, "slympl");
                    clearInterval(a);
                }
            }, 50);
        });
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
}

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

// 打开变更记录页面
function showBgjl(){
    getReturnData("/slym/xm/getlclx", {gzlslid: processInsId}, "GET", function (data) {
        if (data !== null) {
            // 1:普通  2：组合  3：批量  4:批量组合
            if(data == "1"){
                window.open(getContextPath() + "/view/bgdb/bgxxdb.html?processInsId=" + processInsId +"&lclx="+ data);
            }
            if(data == "2" || data == "3" || data == "4" ){
                window.open(getContextPath() + "/view/bgdb/bgxxdbPlzh.html?processInsId=" + processInsId +"&lclx="+ data);
            }
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    });
}

//同步一窗信息
function syncYcsl(xmid){
    addModel();
    getReturnData("/slxxcsh/syncYcslxx", {
        gzlslid : processInsId,
        xmid:xmid
    }, 'GET', function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("操作成功");
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

//删除权利人
function deleteQlr(qlrid, sxh, qlrlb) {
    var url = "/slym/qlr/pllc?processInsId=" + processInsId + "&qlrid=" + qlrid + "&sxh=" + sxh;
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
            //先获取删除的权利人的所有数据，根据名称证件号权利人类别djxl
            var qlridList;
            if (qlrlb === "1") {
                qlridList = getDelQlridList(qlrid, 1);
            }
            getReturnData(url, {}, 'DELETE', function (data) {
                removeModal();
                loadQlr();
                var qllx = $(".qlxx.layui-this").find("input[name='qllx']").val();
                if (isNotBlank(qllx) && ['4', '6', '8'].indexOf(qllx) > -1) {
                    refreshQlxx();
                }
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
                //    删除权利人后删除权利人对应的义务人
                deleteLzr(qlrid);
                deleteDlr(qlridList);
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

function getDelQlridList(qlrid, qlrlb) {
    var idList = [];
    getReturnData("/slym/qlr/allXmQlrid", {
        gzlslid: processInsId,
        qlrid: qlrid,
        djxl: $(".layui-this").find('input[name="djxl"]').val()
    }, "GET", function (data) {
        if (data) {
            idList = data;
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    return idList;
}

//删除代理人（嘱托人） 数据
function deleteDlr(qlridList) {
    //批量根据qlrid 批量删除
    var url = "/slym/dlr/delqlr?gzlslid=" + processInsId + "&djxl=" + $(".layui-this").find('input[name="djxl"]').val();
    getReturnData(url, JSON.stringify(qlridList), "DELETE", function (data) {

    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
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
                reloadDsqlr();
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

//删除领证人
function deleteLzr(qlrid) {
    var url = '/slym/lzr/hf/delete?qlrid=' + qlrid + '&gzlslid=' + processInsId + '&djxl=';
    getReturnData(url, '', 'DELETE', function (data) {
        console.log("删除领证人成功");
        //重新加载领证人
        removeModal();
        loadLzrxx();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//删除领证人
function deleteLzrPl(xmfb) {
    var djxl = $(".layui-this").find('input[name="djxl"]').val();
    var url = '/slym/lzr/yc/lzrdelete/pl?gzlslid=' + processInsId + '&djxl=' + djxl;
    getReturnData(url, '', 'DELETE', function (data) {
        console.log("批量删除领证人成功");
        loadLzrxx(xmfb);
        removeModal();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//删除领证人,再新增
function delAndSaveLzr(xmfb, qlrData) {
    var djxl = $(".layui-this").find('input[name="djxl"]').val();
    var dydj = $(".layui-this").find("input[name='dydj']");
    var url = '/slym/lzr/yc/lzrdelete/pl?gzlslid=' + processInsId + '&djxl=' + djxl;
    getReturnData(url, '', 'DELETE', function (data) {
        console.log("批量删除领证人成功");
        // 插入
        saveHfLzr(qlrData, "insert", "pllc", dydj, null, xmfb);
        removeModal();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}


// 查看产权信息
function showCqxx(qlrmc, qlrzjh){
    if(isNullOrEmpty(qlrmc) || isNullOrEmpty(qlrzjh)){
        warnMsg("姓名或证件号信息为空，无法查看产权信息。");
        return;
    }
    var qlrxx = [{
        qlrmc : qlrmc,
        zjh: qlrzjh
    }];
    window.open("/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=qlrfc&qlrxx="+encodeURI(JSON.stringify(qlrxx)));
}

/*
* 获取领证人信息
*/
function loadLzrxx(xmfb,ygdjzl) {
    getReturnData("/slym/lzr/nt/lzrxx", {xmid: xmid}, 'GET', function (data) {
        generateLzrxx(data, xmfb,ygdjzl);
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}


/*
* 加载领证人信息
*/
function generateLzrxx(lzrdata, xmfb,ygdjzl) {
    var qllx = $($("input[name='qllx']")[0]).val();
    // 传了副本才渲染领证方式
    if (xmfb) {
        lzfs[xmid] = xmfb.lzfs;
        //当前领证方式为空时，设置领证方式默认值
        var lzfs0 = xmfb.lzfs;
        setLzfs(lzfs0,ygdjzl,qllx);
    }

    if ("98" !== qllx && "true" !== zxlc) {
        var json = {
            bdclzrList: lzrdata,
            xmid: xmid,
            zd: zdList,
            lzfs: lzfs[xmid]
        };
        var tpl = lzrxxTpl.innerHTML, view = document.getElementById("lzrxx");
        //渲染数据
        if (view !== null) {
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            form.render();
            getStateById(readOnly, formStateId, 'slympl', "lzrxx");
            form.render('select');
            resetSelectDisabledCss();
        }
    }
}

function setLzfs(lzfs0,ygdjzl,qllx) {
    if (!lzfs0) {
        //如果预告权利，判断预告权利登记种类，预告等级种类是抵押权预告时，默认电子证照
        if (isNotBlank(ygdjzl) && (ygdjzl == 3 || ygdjzl == 4)) {
            lzfs[xmid] = 4;
            return;
        }

        var qllxStr = qllx + "";
        for (var lzfsKey in lzfsQldmMap) {
            var qldmArray = lzfsQldmMap[lzfsKey];
            for (var index = 0; index < qldmArray.length; index++) {
                if (qldmArray[index] == qllx) {
                    lzfs[xmid] = lzfsKey;
                    return;
                }
            }
        }
    }
}

//获取交易信息
function queryFcjyClfHtxx(fwlx, currxmid) {
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

// 查询契税税票
function queryQssp() {
    var htbh = $('#htbh').val();
    var dzspNo = $('#dzspNo').val();
    var slfType = $('#slfType').val();
    if (!htbh) {
        layer.confirm("请输入合同编号", {title:'提示'}, function(index){
            layer.close(index);
        });
        return;
    }
    if ('' === slfType || !slfType) {
        layer.confirm("请选择是否存量房", {title:'提示'}, function(index) {
            layer.close(index);
        });
        return;
    }
    const wsxx = {gzlslid: processInsId, htbh: htbh, dzsphm: dzspNo, zlfclfbz: slfType};
    $('#htbh').val('');
    $('#dzspNo').val('');
    $('#slfType').val('');
    form.render("select");
    $('#htbh').focus();
    getReturnData("/slym/sjcl/list/swWsxx", JSON.stringify(wsxx),"POST",function (data) {
        if (data && data.length > 0) {
            ityzl_SHOW_SUCCESS_LAYER("查询成功");
        } else {
            ityzl_SHOW_WARN_LAYER("未查询到相关信息！");
        }
    },function (error) {
        delAjaxErrorMsg(error);
    })
}

// 获取契税税票信息
function queryXsspxx() {
    // 小弹出层
    layer.open({
        title: '契税税票查询',
        type: 1,
        area: ['430px', '300px'],
        skin: 'layui-layer-lan',
        content: $('#bdc-popup-qssp')
        , cancel: function () {
            $('#htbh').val('');
            $('#dzspNo').val('');
            $('#slfType').val('');
            form.render("select");
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
    });
}


// 获取互联网缴费状态
function queryHlwJfzt(){
    $.ajax({
        url: getContextPath() + "/sf/hlw/jfzt/" + processInsId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            ityzl_SHOW_SUCCESS_LAYER("查询互联网缴费状态成功");
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}


//交易信息导入
function generateYcslxx(contractNo, index,fwlx,currxmid) {
    if(!isNotBlank(currxmid)){
        currxmid =xmid;
    }
    layer.close(index);
    $.ajax({
        url: getContextPath() + "/ycsl/jyxx/queryHaFcjyxx",
        type: 'GET',
        data: {htbh: contractNo, xmid: currxmid, gzlslid: processInsId, fwlx: fwlx, lclx : lclx},
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                //重新组织页面数据
                loadBdcdyh();
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

//不动产单元信息详情页面
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

//查询房屋附属设施
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

//查看房屋是否签售
function queryFcjyFwsfqs() {
    var json = {
        zd: zdList
    };
    //小弹出层
    var htbhIndex = layer.open({
        title: '获取签售信息',
        type: 1,
        skin: 'bdc-spf-layer',
        area: ['450px', '450px'],
        btn: ['提交', '取消'],
        content: $('#bdc-popup-fwsfqs')
        , yes: function (index, layero) {
            //提交 的回调
            var fwbh = $('#fwbh').val();
            var xmmc = $('#xmmc').val();
            var ysxkzh = $('#ysxkzh').val();
            var fh = $('#fh').val();
            var qsdm = $('#qsdm').val();
            // fwbh与xmmc、ysxkzh、qsdm不能同时存在
            if (fwbh && (xmmc || ysxkzh || fh || qsdm)) {
                ityzl_SHOW_WARN_LAYER("房屋编号与项目名称、预售许可证号、房号、区属代码不能同时输入");
                return;
            }
            var beanName;
            if (fwbh) {
                beanName = 'fcjyFwsfqs';
            } else {
                if (!((xmmc || ysxkzh) && fh && qsdm)) {
                    ityzl_SHOW_WARN_LAYER("项目名称或预售许可证号填写时，房号、区属代码必填");
                    return;
                }
                beanName = 'fcjyLpxx';
            }
            addModel();
            $.ajax({
                url: getContextPath() + "/ycsl/jyxx/queryHaFcjyFwsfqs",
                type: 'GET',
                data: {fwbh: fwbh, xmmc: xmmc, ysxkzh: ysxkzh, fh: fh, qsdm: qsdm, beanName: beanName},
                dataType: 'json',
                success: function (data) {
                    if (data != null) {
                        if(data) {
                            setTimeout(function(){
                                ityzl_SHOW_SUCCESS_LAYER("已签售");
                                removeModal();
                            }, 150);
                        } else {
                            setTimeout(function(){
                                ityzl_SHOW_SUCCESS_LAYER("未签售");
                                removeModal();
                            }, 150);
                        }
                    } else {
                        ityzl_SHOW_INFO_LAYER("未获取到签售信息");
                        removeModal();
                    }
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                }
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
            $('#fwbh').val('');
            $('#xmmc').val('');
            $('#ysxkzh').val('');
            $('#fh').val('');
            $('#qsdm').val('');
            //自动获取焦点
            $('#fwbh').focus();

            // 加载区属代码下拉框
            $.each(zdList.qx, function (index, item) {
                $('#qsdm').append(new Option(item.MC, item.DM));
            });
            form.render('select');
        }
    });
}

//展现权利信息(受理批量页面）
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
        // url = "http://localhost:8671/realestate-register-ui/view/qlxx/fdcq.html?xmid=54NA2912DLI40010&formStateId=27642dab2c2a48a6a508e048e609e171&readOnly=false&isCrossOri=false";
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
                $.when(loadBdcdyh(),loadPlQlxx()).done(function () {
                    var a = setInterval(function () {
                        if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                            renderForm();
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

//新增权利人展示(受理批量页面)
function addQlr(qllx) {
    var $this = $(".layui-this");
    var djxl = $($this.find("input[name='djxl']")[0]).val();
    var lzfs = $("#lzfs").val();
    var dydj = false;
    //判断是否是抵押类登记
    getReturnData("/slym/ql/checkDydj", {xmid: xmid}, 'GET', function (data) {
        dydj = data;
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var url = getContextPath() + "/view/slym/qlr.html?xmid=" + xmid + "&lclx=" + lclx + "&processInsId=" + processInsId + "&formStateId=" + formStateId + "&qllx=" + qllx + "&zxlc=" + zxlc + "&djxl=" + djxl +"&lzfs=" + lzfs;
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
    })


}

//权利人详情展示
function showQlr(qlrid, xmid, qlrlb) {
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
    var djxl = $($this.find("input[name='djxl']")[0]).val();
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
function showYwr(qlrid, xmid, qlrlb, otherqlridList, otherXmidList) {
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
        var url = getContextPath() + "/view/slym/qlr.html?processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=" + lclx + "&xmid=" + xmid + "&formStateId=" + formStateId + "&qllx=" + qllx + "&readOnly=" + readOnly + "&zxlc=" + zxlc;
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

//原权利信息详情,根据当前项目的xmid找到上一手原权利并展示
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

//楼盘表
function lpb() {
    addModel();
    var qjgldm ="";
    if(qlxxForPrint &&qlxxForPrint.length >0 &&qlxxForPrint[0].bdcXmFbDO != null){
        qjgldm =qlxxForPrint[0].bdcXmFbDO.qjgldm;
    }

    getReturnData("/slym/lpb", {processInsId: processInsId,qjgldm:qjgldm}, "GET", function (data) {
        removeModal();
        if (data && data.length > 0) {
            var url;
            if (data.length === 1) {
                url = "/building-ui/lpb/view?code=getbdcdyh&fwDcbIndex=" + data[0].fwDcbIndex + "&gzlslid=" + processInsId+"&qjgldm="+qjgldm;
            }else{
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
    if(!sfscdj){
        //非首次登记不允许新增
        ityzl_SHOW_WARN_LAYER("当前流程不允许选择楼盘表新增！");
        return false;

    }
    layer.closeAll();
    if (data && data.length > 0) {
        addModel();
        //获取创建所需参数
        getReturnData("/bdcdyh/queryParams", {gzlslid: processInsId}, "GET", function (paramdata) {
            processDefKey =paramdata.processDefKey;
            jbxxid =paramdata.jbxxid;
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
                sessionStorage.bdcdydata =  JSON.stringify(bdcdydata);
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
            loadBdcdyh();
            loadQlr();
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
function removeTsxxCallBack(){
    if(sessionStorage.bdcdydata != undefined) {
        var bdcdydata = JSON.parse(sessionStorage.bdcdydata);
        addBdcdyh(bdcdydata,processDefKey, jbxxid);
    }
}

//打开批量修改页面
function openPlxg(xmids) {
    //打开前先清除缓存数据
    sessionStorage.removeItem('plxg_xmids');
    //数据过多,存入缓存
    sessionStorage.setItem('plxg_xmids', xmids);
    var index = layer.open({
        type: 2,
        title: "批量修改",
        area: ['1300px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: getContextPath() + "/view/slym/plxg.html?processInsId=" + processInsId + "&xmid=" + xmid + "&formStateId=" + formStateId + "&zxlc=" + zxlc + "&sfdydj=" + sfdydj,
        cancel: function () {
            $.when(loadBdcdyh()).done(function () {
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        renderForm();
                        getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                        clearInterval(a);
                    }
                }, 50);
            });
        }
    });
    layer.full(index);
}


//领证信息操作列 删除
function deleteLzxxTr(qlrid, elem, djxl) {
    if (isNotBlank(qlrid)) {
        showConfirmDialog("提示", "是否确认删除", "deleteLzr", "'" + qlrid + "','" + djxl + "'", "", "", true);
    } else {
        var $this = $(elem);
        $this.parents('.bdc-lzxx-tr').remove();
    }
}

function reloadDsqlr(){
    generateDsQlr(xmid, $(".sqr"), "");
}
//加载第三权利人信息
function generateDsQlr(xmid, $sqr, index) {
    getReturnData("/slym/qlr/list/dsQlr", {xmid: xmid}, 'GET', function (data) {
        //渲染模板数据
        var json = {
            index: index,
            xmid: xmid,
            bdcDsQlrDOList: data,
            zd: zdList
        };

        var dsQlrxxTpl = dsQlrTpl.innerHTML;

        if (isNotBlank($sqr)) {
            //渲染数据
            if ($sqr.parents(".layui-tab-item").find(".dsqlr-basic").length > 0) {
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
    }, false);

}

//删除第三权利人
function deleteDsQlr(qlrid, sxh, qlrlb, elem) {
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
            dsqlr.xmid = $dsqlr.find("[name=xmid]").val();
            dsqlr.qlrid = $dsqlr.find("[name=qlrid]").val();
            dsqlrList.push(dsqlr);
        }
    });
    var url = "/slym/qlr/list/updateDsQlr?processInsId=" + processInsId;

    if (dsqlrList != null && dsqlrList.length > 0) {
        getReturnData(url, JSON.stringify(dsqlrList), 'PATCH', function (data) {
        }, function (err) {
            throw err;
        }, false);
    }

}

//权利人详细
function showDsQlr(qlrid, xmid) {
    var url = getContextPath() + "/view/slym/dsQlr.html?xmid=" + xmid + "&processInsId=" + processInsId + "&formStateId=" + formStateId + "&qlrlb=5";
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

function showCbfJtcy(qlrid,qlrlb){
    var url = getContextPath() + "/view/slym/cbfjtcy.html?qlrid=" + qlrid + "&formStateId=" + formStateId+"&readOnly="+readOnly+"&processInsId="+processInsId+"&qlrlb="+qlrlb;
    layer.open({
        title: '家庭成员信息',
        type: 2,
        area: ['960px', '400px'],
        content: url
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        , success: function () {

        }
    });

}

function cancel(){
    layer.closeAll();
}

function loadYjxx(){
    console.log(processInsId);
    // 选中权利页面的xmid，djxl
    var xmid = $("#bdcdyh input[name='xmid']").val();
    var djxl = $("#bdcdyh input[name='djxl']").val();

    console.log(xmid);
    console.log(djxl);
    var slbh = $('#sjbh').val();
    var url = getContextPath() + "/huaian/slym/yjxx.html?processInsId=" + processInsId + "&djxl=" + djxl + "&slbh=" + slbh + "&xmid=" +xmid +"&lclx=pl";
    layer.open({
        type: 2,
        area: ['960px', '300px'],
        fixed: false, //不固定
        title: "邮寄信息",
        content: url,
        btnAlign: "c"
    });
}

function resetSelectDisabledCss() {
    //select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
    $('.layui-select-disabled i').addClass("bdc-hide");
    $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
    changeBtxbjs();
}