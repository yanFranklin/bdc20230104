//页面入口
var zdList;
var $, form, layer, element, table, laytpl, laydate;
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var zxlc = getQueryString('zxlc');
var sjclids = new Set();
//用于存放所有的收件材料id
var sjclidLists = [];
var zdList;
var taskId = getQueryString("taskId");layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
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
        , lxdh: function (value, item) {
            if (!validatePhone($.trim(value))) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "联系电话格式不正确";
            }
         }
    });

    // 保存
    form.on("submit(saveData)", function (data) {
        addModel("正在保存");
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            isFirst = true;
            removeModal();
            return false;
        } else{
            setTimeout(function () {
                $.when(saveStfxx(), saveCdr(),saveBz(),saveSjcl()).done(function () {
                    ityzl_SHOW_SUCCESS_LAYER("保存成功");
                    removeModal();
                    loadStfzmxx();
                    loadSjcl();
                })
            } , 50);
        }
    });

    // 打开补录表单
    $(document).on('click', '#blButton', function () {
        var xmid = $("#xmid").val();
        var index = layer.open({
            type: 2,
            title: "补录台账",
            area: ['1300px', '600px'],
            fixed: false, //不固定
            // maxmin: true, //开启最大化最小化按钮
            content: "/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=cdbltz&xmid=" + xmid,
            cancel: function () {
                //loadStfzmxx();
            }
        });
        layer.full(index);
        return false;
    });


    $(function () {
        addModel();
        //获取字典列表、加载页面信息
        setTimeout(function () {
            try {
                $.when(loadButtonArea(),loadZdData(),loadSjcl(),loadStfzmxx()).done(function () {
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

    })
});

//按钮加载
function loadButtonArea() {
    var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
    //渲染数据
    laytpl(tpl).render({}, function (html) {
        view.innerHTML = html;
    });
    getStateById(readOnly, formStateId, "stfzm", "", "");
    form.render();
    disabledAddFa();
    titleShowUi();
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

// 首套房信息
function loadStfzmxx(){
    $.ajax({
        url: getContextPath() + "/cdlc/getCdxx/" + processInsId,
        type: "GET",
        success: function (data) {
            console.log("加载首套房信息");
            if (isNotBlank(data)) {
                generateStfxx(data);
                generateCdrxx(data.qlrList);
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

//组织首套房信息到页面
function generateStfxx(data) {
    var json = {
        zd: zdList,
        cdxx: data
    };
    var tpl = stfxxTpl.innerHTML, view = document.getElementById('stfxx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    getStateById(readOnly, formStateId, "stfzm", "", "");
    form.render();
    renderDate(form, formStateId);
    form.render('select');
    disabledAddFa();
    renderSelect();
}

//组织查询人信息到页面
function generateCdrxx(data) {
    if(isNotBlank(data)){
        // 申请类型 1： 个人  2： 家庭
        var sqlx = data.length > 1 ? 2: 1;
        $.each(data,function (index, value){
            value.sqlx = sqlx;
        });
    }
    var json = {
        zd: zdList,
        qlrList: data
    };
    var tpl = cxrxxTpl.innerHTML, view = document.getElementById('cxrxx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    getStateById(readOnly, formStateId, "stfzm", "", "");
    form.render();
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
    getStateById(readOnly, formStateId, 'stfzm', 'sjcl');
    form.render('select');
    disabledAddFa("sjclForm");
}


// 保存首套房信息
function saveStfxx(){
    var cdxxData = $('.cdxx').serializeArray();
    var cdxxObj = {};
    if (cdxxData.length > 0) {
        cdxxData.forEach(function (item, index) {
            cdxxObj[item.name] = item.value;
        });
    }
    console.log(cdxxObj);
    $.ajax({
        url: getContextPath() + "/cdlc/saveBdcCdxx?gzlslid=" + processInsId,
        type: "PATCH",
        data: JSON.stringify(cdxxObj),
        contentType: 'application/json',
        dataType: "json",
        async:false,
        success: function (data) {
            console.log("查档信息保存成功")
        },
        error: function (e) {
            ityzl_SHOW_WARN_LAYER("查档信息保存失败");
        }
    });
}

// 保存备注信息
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

// 保存查档人
function saveCdr(){
    var qlrArray = $(".cxr").serializeArray();
    var qlrList = [];
    var qlr = {};
    for (var j = 0; j < qlrArray.length; j++) {
        var name = qlrArray[j].name;
        qlr[name] = qlrArray[j].value;
        // 以sqrid为每一组权利人的起始数据，作为分割依据
        if ((j + 1 < qlrArray.length && qlrArray[j + 1].name === 'sqrid') || j + 1 == qlrArray.length) {
            if(qlr.qlrmc != "" && qlr.zjh != ""){
                qlr.zjzl = "1";
                qlr.sqrlb = "1";
                qlr.xmid = $("#xmid").val();
                qlrList.push(qlr);
                qlr = {};
            }
        }
    }

    var url = "/cdlc/list/qlr?processInsId=" + processInsId;
    console.log(qlrList);
    if (isNotBlank(qlrList)) {
        getReturnData(url, JSON.stringify(qlrList), 'PATCH', function (data) {
            console.log("权利人保存成功")
        }, function (err) {
            throw err;
        }, false);
    }
}


//新增查档人
function addCdr() {
    var appendEl = $("#cxrTable");
    var trELArray = $(appendEl).find("tr");
    if (trELArray.length > 1) {
        $(appendEl).find("tr[class='bdc-table-none']").remove();
        //移除后长度减一
        trELArray = $(appendEl).find("tr");
    }
    var cxrXh = trELArray.length;
    var json = {
        xh:cxrXh,
        zd:zdList
    };
    var getTpl = addCxrTpl.innerHTML;
    laytpl(getTpl).render(json, function (html) {
        appendEl.append(html);
    });
    if(cxrXh > 1){
       $(".sqlxselect").val(2);
    }
    form.render();
}

// 删除查档人
function deleteCxr(qlrid, obj) {
    if (isNotBlank(qlrid)) {
        var url = "/cdlc/list/qlr?processInsId=" + processInsId + "&qlrid=" + qlrid;
        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '是否确认删除？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                addModel("正在删除")
                //确定操作
                getReturnData(url, {}, 'DELETE', function (data) {
                    console.log("权利人删除成功")
                }, function (err) {
                    throw err;
                }, false);
                removeModal();
                layer.close(deleteIndex);
                loadStfzmxx();
                ityzl_SHOW_SUCCESS_LAYER("删除成功");

            },
            btn2: function () {
                //取消
                layer.close(deleteIndex);
            }
        });
    } else {
        $(obj).parent().parent().remove();
        if($("#cxrTable").find("tr").length == 2){
            $(".sqlxselect").val(1);
        }
        form.render();
    }
}


function titleShowUi() {
    $(".print-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").show();
        setUiWidth($(this), $("#print"));
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
        $("#print").hide();
    });
}

function setUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    uiElement.offset({top: X + 40, left: Y - 20});
    var uiWidth = 90;
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
        var countWidth = (textNumber + 3) * 13;
        if (uiWidth < countWidth) {
            uiWidth = countWidth;
        }
    }
    uiElement.width(uiWidth);
}

