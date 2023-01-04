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
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl','laydate'], function () {
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laytpl = layui.laytpl;
    laydate = layui.laydate;

    // 页面验证
    var isSubmit = true;
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '' || value == null || value == undefined) {//判断是否为空
                $(item).addClass('layui-form-danger');
                isSubmit = false;
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
    });
    // 保存
    form.on("submit(saveData)", function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            isFirst = true;
            removeModal();
            return false;
        } else {
            // addModel();
            setTimeout(function () {
                $.when(saveCdxx(), saveBz(), saveSjcl()).done(function () {
                    ityzl_SHOW_SUCCESS_LAYER("保存成功");
                    removeModal();
                    loadCdxx();
                    loadSjcl();
                })
            } , 50);
        }

    });

    $(function () {
        addModel();
        //获取字典列表、加载页面信息
        setTimeout(function () {
            try {
                $.when(loadButtonArea(),loadZdData(),loadSjcl(),loadCdxx()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
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
            if(sjclids.size >0) {
                var idList = [];
                sjclids.forEach(function (element, sameElement, set) {
                    idList.push(sameElement);
                });
                sessionStorage.setItem("yxsjclids",idList);
            } else {
                sessionStorage.setItem("yxsjclids",[]);
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
            if(sjclids.size >0) {
                var idList = [];
                sjclids.forEach(function (element, sameElement, set) {
                    idList.push(sameElement);
                });
                sessionStorage.setItem("yxsjclids",idList);
            } else {
                sessionStorage.setItem("yxsjclids",[]);
            }
        });

        //监听证件种类选择
        form.on('select(cdrzjzl)', function(data){
            addSfzYz(data.value, data.elem);
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
    form.render();
    disabledAddFa();
}

// 获取字典
function loadZdData(){
    $.ajax({
        url: getContextPath()+"/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        },error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}
//加载查档信息
function loadCdxx() {
    $.ajax({
        url: getContextPath() + "/cdlc/getCdxx/" + processInsId,
        type: "GET",
        success: function (data) {
            if (isNotBlank(data)) {
                generateCdxx(data);
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
    var tpl = cdxxTpl.innerHTML, view = document.getElementById('cdxx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    renderDate(form, formStateId);
    form.render('select');
    disabledAddFa();
    renderSelect();
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
    if(data !== null && data.length >0) {
        for(var i=0;i<data.length;i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    sessionStorage.setItem("sjclidLists",sjclidLists);
    var json = {
        xmid: xmid,
        bdcSlSjclDOList: data,
        zd: zdList
    };
    var tpl = sjclTpl.innerHTML, view = document.getElementById("sjcl");
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    //renderForm();
    getStateById(readOnly, formStateId, 'slymzh', 'sjcl');
    form.render('select');
    disabledAddFa("sjclForm");
}

// 保存查档信息
function saveCdxx(){
    var cdxxData = $('.cdxx').serializeArray();
    var cdxxObj = {};
    if (cdxxData.length > 0) {
        cdxxData.forEach(function (item, index) {
            cdxxObj[item.name] = item.value;
        });
    }
    console.log(cdxxObj);
    $.ajax({
        url: getContextPath() + "/cdlc/saveBdcCdxx",
        type: "PATCH",
        data: JSON.stringify(cdxxObj),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            console.log("查档信息保存成功")
        },
        error: function (e) {
            ityzl_SHOW_WARN_LAYER("查档信息保存失败");
        }
    });
}


// 保存查档信息
function saveBz(){
    var bz = $('#bz').val();
    var xmid = $("#xmid").val();
    // $.ajax({
    //     url: getContextPath() + "/cdlc/saveBz?xmid=" + xmid + "&bz=" + bz,
    //     type: "GET",
    //     dataType: "json",
    //     async: false,
    //     success: function (data) {
    //         console.log("备注保存成功")
    //     },
    //     error: function (e) {
    //         ityzl_SHOW_WARN_LAYER("备注保存失败");
    //     }
    // });
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


