var $, form, layer, element, table, laytpl, laydate;
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var taskId = getQueryString("taskId");
var zxlc = getQueryString('zxlc');
var sjclids = new Set();
//用于存放所有的收件材料id
var sjclidLists = [];
var zdList;
var sqrId = "";
var showBcxrDiv = true;
var globalBcxr = [];

var signStreamData = "";

layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response', formSelects: 'lib/form-select/formSelects-v4'});

layui.use(['jquery', 'layer', 'element', 'form', 'laytpl', 'laydate', 'formSelects'], function () {
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
    element = layui.element;
    laytpl = layui.laytpl;
    laydate = layui.laydate;
    formSelects = layui.formSelects;

    // 页面验证
    var isSubmit = true;
    var verifyMsg = "";
    form.verify({
        required: function (value, item) {
            if (value == '' || value == null || value == undefined) {//判断是否为空
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "必填项不能为空";
            }
        }
        , identitynew: function (value, item) {
            // 涉及到sfz验证 以为申请人信息要同步到被查询人的本人（不能改变）里面，所以保存前，先把sfz值赋值过去
            // $("#addBcxrTable").find('tr:eq(1)').find('#zjh').val($("#cdrzjh").val());
            // var msg = checkSfzZjh(value);
            // if (isNotBlank(msg)) {
            //     $(item).addClass('layui-form-danger');
            //     isSubmit = false;
            //     verifyMsg = msg;
            // }
        }
    });
    // 保存
    form.on("submit(saveData)", function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            isFirst = true;
            verifyMsg = "";
            removeModal();
            return false;
        } else {
            addModel();
            setTimeout(function () {
                try {

                    $.when(saveCdxx(data), saveBcxr(), saveBz(), saveSjcl(), saveSign()).done(function () {
                        ityzl_SHOW_SUCCESS_LAYER("保存成功");
                        removeModal();
                        loadCdxx();
                        loadSjcl();
                        loadBcxr();
                        loadSign();
                    })
                } catch (e) {
                    removeModal();
                    ityzl_SHOW_WARN_LAYER(e.message);
                }
            }, 50);
        }

    });

    $(function () {
        addModel();
        //获取字典列表、加载页面信息
        setTimeout(function () {
            try {
                $.when(loadButtonArea(), loadZdData(), loadSjcl(), loadCdxx(), loadBcxr(), loadSign()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e, "加载失败");
                return
            }
        }, 50);

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

        //监听证件种类选择
        form.on('select(cdrzjzl)', function (data) {
            addSfzYz(data.value, data.elem);
        });

        //监听证件种类选择
        form.on('select(cxmdhyt)', function (data) {
            addSfzYz(data.value, data.elem);
        });
        formSelects.on('cxmdhyt', function (id, vals, val, isAdd, isDisabled) {
            //id:           点击select的id
            //vals:         当前select已选中的值
            //val:          当前select点击的值
            //isAdd:        当前操作选中or取消
            //isDisabled:   当前选项是否是disabled
            var $cxmdhytqt = $("#cxmdhytqt").parents('.layui-inline');
            if (val.DM == "99" || val.value == "99") {
                if (isAdd) {
                    $cxmdhytqt.show();
                    $("#cxmdhytqt").removeAttr("disabled");
                } else {
                    $cxmdhytqt.hide();
                }
            }
        });

        //监听 查询目的或用途 选择
        // form.on('select(cxmdhyt)', function(data){
        //     var $cxmdhytqt = $("#cxmdhytqt").parents('.layui-inline');
        //     if(data.value == "99"){
        //         $cxmdhytqt.show();
        //         $("#cxmdhytqt").removeAttr("disabled");
        //     }else if( !$cxmdhytqt.hasClass('bdc-hide')){
        //
        //     }
        //     //重新渲染select
        //     form.render("select");
        //
        // });


        // 权利人查询
        form.on('submit(queryDaQlrcx)', function (data) {
            var queryParam = getQueryParam();
            var dylx = 'qlrcdjg';
        });

        // 关系人查询
        form.on('submit(queryDaGxrcx)', function (data) {
            var queryParam = getQueryParam();
            var dylx = 'lhgxcdjg';
        });

        // 准关系人查询
        form.on('submit(queryDaZGxrcx)', function (data) {
            var queryParam = getQueryParam();
            var dylx = 'zlhgxcdjg';
        });

    })
});

//按钮加载
function loadButtonArea() {
    var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
    //渲染数据
    laytpl(tpl).render({}, function (html) {
        view.innerHTML = html;
    });
    // 展示人证对比
    $("#showRzdb").click(function(){
        showRzdb();
    });

    // 获取比对结果
    $("#hqdbjg").click(function(){
        hqdbjg();
    });
    form.render();
    disabledAddFa();
}

// 获取字典
function loadZdData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

//加载查档信息
function loadCdxx() {
    $.ajax({
        url: getContextPath() + "/cdlc/getCdxx/" + processInsId,
        type: "GET",
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                var qlrcxGzldyid = data.qlrcxGzldyid;
                var currentLcGzldyid = data.bdcSlJbxxDO.gzldyid;
                // 判断当前流程是否是权利人查询  来控制是否显示被查询人模块；
                if (qlrcxGzldyid != currentLcGzldyid) {
                    showBcxrDiv = false;
                }

                generateCdxx(data);
                sqrId = data.sqrid;

                renderformSelects(data);

                // 查询目的或用途选中值含其他，显示其他输入框
                if (isNotBlank(data.bdcCdxxDO.cxmdhyt)) {
                    var cxmdhytArr = data.bdcCdxxDO.cxmdhyt.split(',');
                    for (var i = 0; i < cxmdhytArr.length; i++) {
                        if (cxmdhytArr[i] == "99") {
                            $("#cxmdhytqt").parents('.layui-inline').show();
                        }
                    }
                }

            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

//组织基本信息到页面
function generateCdxx(data) {
    var json = {
        zd: zdList,
        cdxx: data
    };
    var cdtpl = cdxxTpl.innerHTML, cdview = document.getElementById('cdxx'),
        cxtpl = cxxxTpl.innerHTML, cxview = document.getElementById('cxContent');
    //渲染数据
    laytpl(cdtpl).render(json, function (html) {
        cdview.innerHTML = html;
    });
    //渲染数据
    laytpl(cxtpl).render(json, function (html) {
        cxview.innerHTML = html;
    });
    form.render();
    renderDate(form, formStateId);
    getStateById(readOnly, formStateId, 'cdym');
    form.render('select');
    disabledAddFa();
    renderSelect();
    // 权利人查询需要隐藏 查询信息块块
    if (!showBcxrDiv) {
        $("#bcxr").hide();
    }
}

// 加载收件材料
function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        generateSjcl(data, "");
    }, function (err) {
        console.log(err);
    });
}

//组织收件材料到页面
function generateSjcl(data, xmid) {
    sjclNumber = data.length;
    sjclidLists = [];
    if (data !== null && data.length > 0) {
        for (var i = 0; i < data.length; i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    sessionStorage.setItem("sjclidLists", sjclidLists);
    var json = {
        xmid: xmid,
        bdcSlSjclDOList: data,
        zd: zdList
    };
    var tpl = sjclTpl.innerHTML,
        view = document.getElementById("sjcl");
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    //renderForm();
    getStateById(readOnly, formStateId, 'cdym', 'sjcl');
    form.render('select');
    disabledAddFa("sjclForm");
}


// 加载被查询人
function loadBcxr() {
    if (showBcxrDiv) {
        getReturnData("/slym/jtcy/listBdcSlJtcy", {xmid: $("#xmid").val()}, 'GET', function (data) {
            generateBcxr(data, $("#xmid").val());
        }, function (err) {
            console.log(err);
        });
    }
}

//组织收件材料到页面
function generateBcxr(data, xmid) {

    if (!data || data.length == 0) {
        data = [{"ysqrgx": "本人", "xmid": $("#xmid").val(), "zjh": "", "jtcymc": ""}]
    }
    var json = {
        xmid: xmid,
        bdcBcxrDOList: data,
        zd: zdList
    };
    globalBcxr = data;
    var tpl = bcxrTpl.innerHTML, view = document.getElementById("bcxr");
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

    if ($("#addBcxrTable").find('tr:eq(1)').find('#jtcymc').val() != "" &&
        $("#addBcxrTable").find('tr:eq(1)').find('#zjh').val() != "") {
        $("#addBcxrTable").find('tr:eq(1)').find('#ysqrgx').attr("disabled", "off");
        $("#addBcxrTable").find('tr:eq(1)').find('#jtcymc').attr("disabled", "off");
        $("#addBcxrTable").find('tr:eq(1)').find('#zjh').attr("disabled", "off");
        $("#addBcxrTable").find('tr:eq(1)').find('#bcxrzjzl').attr("disabled", "off");
    }

    //重新渲染select
    form.render();
    form.render('select');
    disabledAddFa();
    renderSelect();

}

//渲染多选框
function renderformSelects(data) {
    formSelects.config('cxmdhyt', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('djbjzxx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('djyspz', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('cxjgyq', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    // 渲染字典表下拉
    formSelects.data('cxmdhyt', 'local', {arr: zdList.cxmdhyt});
    formSelects.data('djbjzxx', 'local', {arr: zdList.djbjzxx});
    formSelects.data('djyspz', 'local', {arr: zdList.djyspz});
    formSelects.data('cxjgyq', 'local', {arr: zdList.cxjgyq});

    // 渲染已选信息
    if (isNotBlank(data.bdcCdxxDO.cxmdhyt)) {
        formSelects.value('cxmdhyt', data.bdcCdxxDO.cxmdhyt.split(','));
    }
    if (isNotBlank(data.bdcCdxxDO.djbjzxx)) {
        formSelects.value('djbjzxx', data.bdcCdxxDO.djbjzxx.split(','));
    }
    if (isNotBlank(data.bdcCdxxDO.djyspz)) {
        formSelects.value('djyspz', data.bdcCdxxDO.djyspz.split(','));
    }
    if (isNotBlank(data.bdcCdxxDO.cxjgyq)) {
        formSelects.value('cxjgyq', data.bdcCdxxDO.cxjgyq.split(','));
    }
}

//收件材料保存
function saveBcxr() {
    if (showBcxrDiv) {
        if (sqrId) {
            var bcxrArray = $(".bcxr").serializeArray();
            saveAllBcxr(bcxrArray);
        } else {
            ityzl_SHOW_WARN_LAYER("未保存申请人，无法保存被查询人")
        }
    }
}

function saveAllBcxr(bcxrArray) {
    var bcxrList = [];
    var bcxr = {};
    var flag = 1;
    for (var j = 0; j < bcxrArray.length; j++) {
        var name = bcxrArray[j].name;
        if (name == 'bcxrzjzl') {
            name = 'zjzl';
        }
        bcxr[name] = bcxrArray[j].value;
        if (bcxr.jtcymc === "") {
            flag = 0;
        }
        bcxr.sqrid = sqrId;
        //bcxr.zjzl = 1;
        // 以jtcyid为每一组收件材料的起始数据，作为分割依据
        if ((j + 1 < bcxrArray.length && bcxrArray[j + 1].name === 'jtcyid') || j + 1 == bcxrArray.length) {
            bcxrList.push(bcxr);
            bcxr = {};
        }
    }
    if (flag === 1) {
        // 判断本人和配偶是否超过两个
        var countBr = 0;
        var countPo = 0;
        for (var i = 0; i < bcxrList.length; i++) {
            if (bcxrList[i].ysqrgx == "配偶") {
                countPo++;
            }
            if (bcxrList[i].ysqrgx == "本人") {
                countBr++;
            }
        }
        /* if(countBr != 1){
             throw err = new Error('被查询人中无本人');
         }*/
        if (countPo > 1) {
            throw err = new Error('关系为配偶不能超过1个!');
        }

        getReturnData("/cdlc/bcxr/list", JSON.stringify(bcxrList), 'PATCH', function (data) {

            if (data > 0) {
                loadBcxr();
            }
        }, function (err) {
            throw err;
        }, false);
    } else {
        layer.alert("材料名称为空,无法上传!");
    }
}


// 保存查档信息
function saveCdxx(data) {
    var cdxxData = $('.cdxx').serializeArray();
    var cdxxObj = {};
    if (cdxxData.length > 0) {
        cdxxData.forEach(function (item, index) {
            cdxxObj[item.name] = item.value;
        });
    }
    // 多选保存处理
    cdxxObj.cxmdhyt = data.field.cxmdhyt;
    cdxxObj.djbjzxx = data.field.djbjzxx;
    cdxxObj.djyspz = data.field.djyspz;
    cdxxObj.cxjgyq = data.field.cxjgyq;

    console.log(cdxxObj);
    $.ajax({
        url: getContextPath() + "/cdlc/saveBdcCdxx?gzlslid=" + processInsId,
        type: "PATCH",
        data: JSON.stringify(cdxxObj),
        async: false,
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            console.log("查档信息保存成功");
            if (showBcxrDiv) {
                saveSqr(cdxxObj);
            }
        },
        error: function (e) {
            ityzl_SHOW_WARN_LAYER("查档信息保存失败");
        }
    });
}


// 保存查档信息
function saveBz(){
    var bz = $('#bz').val();
    var jbxxid = $("#jbxxid").val();
    var bzObj = {};
    bzObj.jbxxid =jbxxid;
    bzObj.bz =bz;
    $.ajax({
        url: getContextPath() + "/cdlc/saveBz",
        type: "PATCH",
        data: JSON.stringify(bzObj),
        contentType: 'application/json',
        dataType: "json",
        async:false,
        success: function (data) {
            console.log("备注保存成功")
        },
        error: function (e) {
            ityzl_SHOW_WARN_LAYER("备注保存失败");
        }
    });
}

/**
 * 身份证证件号验证添加
 * @param zjzl 证件种类
 * @param elem 证件种类dom元素
 */
function addSfzYz(zjzl, elem) {
    var zjzlid = $(elem).parent().parent().find("select")[0].id;
    var zjhid = zjzlid.replace("zjzl", "zjh");
    var $zjh = $(elem).parent().parent().next().find("#" + zjhid);
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

/**
 * 保存成功后要保存申请人到 bdc_sl_sqr表中
 */
function saveSqr(cdxxObj) {
    var sqrObj = {};
    sqrObj.xmid = cdxxObj.xmid;
    sqrObj.sqrmc = cdxxObj.cdr;
    sqrObj.zjzl = cdxxObj.cdrzjzl;
    sqrObj.zjh = cdxxObj.cdrzjh;
    sqrObj.dh = cdxxObj.cdrdh;
    sqrObj.sqrid = sqrId;
    var url = getContextPath() + "/cdlc/sqrxx/save";
    $.ajax({
        url: url,
        type: 'PATCH',
        contentType: "application/json",
        data: JSON.stringify(sqrObj),
        async: false,
        success: function (data) {
            sqrId = data;
            if (showBcxrDiv) {
                $("#addBcxrTable").find('tr:eq(1)').find('#jtcymc').val(cdxxObj.cdr);
                $("#addBcxrTable").find('tr:eq(1)').find('#zjh').val(cdxxObj.cdrzjh);
                $("#addBcxrTable").find('tr:eq(1)').find('#bcxrzjzl').val(cdxxObj.cdrzjzl);

                form.render("select");
                form.render();
                disabledAddFa();
                renderSelect();
            }
        },
        error: function (e) {
            delAjaxErrorMsg(e);
        }
    });

}


//新增被查询人
function addBcxrEle() {
    var appendEl = $("#addBcxrTable");
    var trELArray = $(appendEl).find("tr");
    if (trELArray.length > 1) {
        $(appendEl).find("tr[class='bdc-table-none']").remove();
        //移除后长度减一
        trELArray = $(appendEl).find("tr");
    }
    var bcxrNumber = trELArray.length;

    var addBcxrxx = {
        bcxrNumber: bcxrNumber,
        zd: zdList,
    };
    var getTpl = addBcxrTpl.innerHTML;
    laytpl(getTpl).render(addBcxrxx, function (html) {
        appendEl.append(html);
        form.render();
    });
    //控制权限
    resetSelectDisabledCss();
}

//删除被查询人(单个删除)
function deleteBcxr(jtcyid, obj) {
    var jtcyid = $(obj).parents('tr').find('input[name="jtcyid"]').val();
    //var ysqrgx =  $(obj).parents('tr').find('input[name="jtcymc"]').val();

    if (isNotBlank(jtcyid)) {
        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '是否确认删除？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                var url = getContextPath() + "/slym/jtcy?jtcyid=" + jtcyid;

                //确定操作
                $.ajax({
                    url: url,
                    type: 'DELETE',
                    success: function (data) {
                        loadBcxr();
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
                layer.close(deleteIndex);
            }
        });
    } else {
        $(obj).parent().parent().remove();
    }
}

function cdrReadIdCard(element) {
    cdrReadIdCardFun(element);
}

function dlrReadIdCard(element) {
    dlrReadIdCardFun(element);
}

function zjhReadIdCard(element) {
    zjhReadIdCardFun(element);
}

/**
 * 查询本流程的签字信息
 */
function loadSign() {
    var url = getContextPath() + "/cdlc/sign/query";
    $.ajax({
        url: url,
        type: 'get',
        contentType: 'application/json',
        dataType: "text",
        data: {gzlslid: processInsId},
        success: function (data) {
            $("#photo").attr("src", data);
            $("#photo").show();
        },
        error: function (e) {
            //delAjaxErrorMsg(e);
        }
    });
}

// 获取查询查询参数
function getQueryParam() {
    var param = {};
    param["zl"] = $("#zl").val();
    param["bdcqzh"] = $("#cqzh").val();
    param["qlrzjh"] = '';
    param["qlrmc"] = '';
    param["qlrzjzl"] = '';
    var zjh = "";
    var mc = "";
    var zjzl = "";
    if (globalBcxr.length > 0) {
        for (var i = 0; i < globalBcxr.length; i++) {
            zjh += globalBcxr[i].zjh + ",";
            mc += globalBcxr[i].jtcymc + ",";
            zjzl += globalBcxr[i].zjzl + ",";
        }
        if (zjh.length > 0) {
            zjh = zjh.substring(0, zjh.length - 1);
            param['qlrzjh'] = zjh;
        }
        if (mc.length > 0) {
            mc = mc.substring(0, mc.length - 1);
            param['qlrmc'] = mc;
        }
        if (zjzl.length > 0) {
            zjzl = zjzl.substring(0, zjzl.length - 1);
            param['qlrzjzl'] = zjzl;
        }
    }
    return param;
}


/**
 * 领证人签字:调用签字版进行签字,获取签字图片信息
 */
function sign() {
    layer.open({
        title: '申请人签字',
        type: 2,
        area: ['700px', '430px'],
        content: "/realestate-accept-ui/yancheng/cdym/qzb.html"
        , cancel: function () {
        }
        , success: function () {
        }
        , end: function (index, layero) {
            var signStream = layui.data('signStream');
            if (signStream && signStream.data) {
                signStreamData = "data:image/png;base64," + signStream.data;
                $("#photo").attr("src", "data:image/png;base64," + signStream.data);
                $("#photo").show();
            }
        }
    });

}

/**
 * 保存签字图片流
 * @returns {boolean}
 */
function saveSign() {

    if (signStreamData) {
        var url = getContextPath() + "/cdlc/sign/save";
        var obj = {};
        obj["gzlslid"] = processInsId;
        obj["signStreamData"] = signStreamData;
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(obj),
            success: function (data) {
                console.log("保存签名信息成功");
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
    } else {
        ityzl_SHOW_WARN_LAYER("未获取到签字版图片信息，请检查");
        return false;
    }
}

/**
 * 跳转查询台账页面
 * @param dylx
 */
function jumpQuery(dylx) {
    var url = "/realestate-inquiry-ui/view/cdym/cdcx.html?dylx=" + dylx;
    var param = getQueryParam();
    if (dylx == "zlhgxcdjg" || dylx == "lhgxcdjg") {
        // 厉害人 准厉害人查询需要验证cqzh与zl
        if (param.zl && param.bdcqzh) {
            url = url + "&zl=" + param.zl + "&bdcqzh=" + param.bdcqzh
            window.open(url);
        } else {
            ityzl_SHOW_WARN_LAYER("缺失查询参数，请检查");
        }
    } else {
        // 权利人人查询需要验证qlrmc和qlrzjh
        if (param.qlrmc && param.qlrzjh) {
            url = url + "&qlrmc=" + param.qlrmc + "&qlrzjh=" + param.qlrzjh + "&qlrzjzl=" + param.qlrzjzl
            window.open(encodeURI(url));
        } else {
            ityzl_SHOW_WARN_LAYER("缺失查询参数，请检查");
        }
    }

}

// 展示人证对比
function showRzdb(){
    var slbh = $("#slbh").val();
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
    var slbh = $("#slbh").val();
    addModel();
    getReturnData('/slPrint/rzdb',{gzlslid:processInsId, slbh: slbh},'GET',function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("生成比对文件成功。");
    },function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}
