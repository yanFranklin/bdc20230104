var id;
var attr;
var names = [];
var attrs = [];
//不需要根据name 进行属性值设置的元素
var unNames = [];
var xmlbBtnAttr = [];
attrs.push("sfxx-delete1", "sfxx-delete2", "sfxmmc2", "sfxmbz2", "sl2", "jedw2", "ysje2", "sfxxup", "sfxxdown", "sfxmmc1", "sfxmbz1", "sl1", "jedw1", "ysje1", "addsfxm", "yh1", "yh2");
names.push("qlrmc", "zjzl", "zjh", "qlrlb", "qlrlx", "dh", "qlbl", "gyfs", "qlrxx", "ywrxx", "ywrdelete", "qlrdelete", "ywrup",
    "qlrup", "ywrdown", "qlrdown", "clmc", "fs", "ys", "sjlx", "sjclsc", "sjcldelete", "sjclup", "sjcldown", "sfxxdelete1", "sfxxdelete2",
    "sfxmdm2", "sfxmbz2", "sl2", "jedw2", "ysje2", "sfxxup", "sfxxdown", "sfxmdm1", "sfxmbz1", "sl1", "jedw1", "ysje1", "zhlc-ywrxx",
    "zhlc-ywrdelete", "zhlc-ywrup", "zhlc-ywrdown", "addSjcl", "zsyl", "qjfj", "fsss", "yqlxx", "dyxx", "qlxx", "qlrrlsb", "ywrrlsb",
    "sqfbcz1", "ycqzh", "sqfbcz2", "bdcdyxxtable", "sqrDiv1", "sqrDiv2");
unNames.push("tdxz", "xz", "dashc", "sh", "daglk", "qjglk", "xxglk");
//表单权限请求结果
var attrCommonData = {};
var moduleCode = getQueryString("moduleCode") || "";
var processInstanceType = $.getUrlParam('processInstanceType');

/**
 * tabId tab 的ID(页面存在多个tab标签页时，区分各个tab页的权限控制
 * readOnly 全局只读
 * formstateid 表单中心id
 * resourcename 页面元素key，一般为*.html
 * tsbs 特殊标识
 */
function getStateById(readOnly, formStateId, resourceName, tabId,tsbs) {
    setElementAttrByFormState(formStateId, resourceName, tabId, tsbs);
    if (readOnly && readOnly === "true") {
        setAllElementDisabled();
    }
    if(moduleCode){
        setElementAttrByModuleAuthority(moduleCode,tabId,resourceName,tsbs);
    }
}

function setAllElementDisabled() {
    // 设置输入框不可编辑
    var input = $('input');
    for (var i = 0; i < input.length; i++) {
        if (input[i].type !== "hidden") {
            input[i].setAttribute('disabled', 'off');
        }
    }
    $('select').attr('disabled', 'off');
    $('textarea').attr('disabled', 'off');
    $('checkbox').attr('disabled', 'off');
    $('radio').attr('disabled', 'off');
    var printValue = getQueryString("print");
    // 设置按钮不可使用
    var button = $('.layui-btn');
    for (var j = 0; j < button.length; j++) {
        if (button[j].classList.contains("bdc-click") || (xmlbBtnAttr.indexOf(button[j].id) > -1)) {
            continue;
        }
        //申请人的详细按钮和权利信息的按钮在readOnly下可点击，里面的内容不可编辑
        if (button[j].value !== "readOnly") {
            if ((button[j].id === "printButton" || $(button[j]).hasClass("sl-print-btn")) && printValue === "true") {
                // 去除打印按钮上添加的 css 样式
                button[j].removeAttribute("style", "display:none;");
                button[j].removeAttribute("disabled", "off");
                button[j].classList.remove("layui-btn-disabled");
                continue;
            }
            //页面上方操作不可编辑按钮直接隐藏
            var titleBtn =$(button[j]).parents(".title-btn-area");
            if(titleBtn.length >0) {
                button[j].setAttribute("style", "display:none;");
            }else {
                button[j].setAttribute('disabled', 'off');
                $(button[j]).addClass('layui-btn-disabled');
                $(button[j]).addClass('bdc-prohibit-btn');
                $(button[j]).removeClass('bdc-major-btn');
                $(button[j]).removeClass('bdc-delete-btn');
            }
        }
    }
    var a = $('a');
    for(var l=0;l<a.length;l++){
        if (a[l].classList.contains("bdc-click") || (xmlbBtnAttr.indexOf(a[l].id) > -1)) {
            continue;
        }
        a[l].removeAttribute('onclick');
        //重新生成附记和权利其他状况不可编辑
        $(a[l]).removeClass('resetqlqtzk');
        $(a[l]).removeClass('resetfj');
    }
    var span = $('span');
    for(var k= 0;k<span.length;k++) {
        if (span[k].classList.contains("bdc-click") || (xmlbBtnAttr.indexOf(span[k].id) > -1)) {
            continue;
        }
        span[k].removeAttribute("onclick")

    }
    $('.nextBtn').addClass('bdc-prohibit-btn');
}


/**
 * @param tabId tab 的ID(页面存在多个tab标签页时，区分各个tab页的权限控制
 * @param formStateId 表单中心id
 * @param resourceName 页面元素key，一般为*.html
 * @param tsbs 特殊标识
 */
function setElementAttrByFormState(formStateId, resourceName, tabId, tsbs) {
    if(attrCommonData && attrCommonData[resourceName] && attrCommonData[resourceName].length>0){
        //已经请求过,不再重复请求
        setAllElementAttr(attrCommonData[resourceName],tabId,resourceName,tsbs);
    }else {
        $.ajax({
            url: getContextPath() + "/formCenter",
            type: 'POST',
            dataType: 'json',
            async: false,
            data: {formStateId: formStateId, resourceName: resourceName},
            success: function (data) {
                setAllElementAttr(data, tabId, resourceName, tsbs);
                if (isNotBlank(data)) {
                    attrCommonData[resourceName] = data;

                    for (var i = 0; i < attrCommonData[resourceName].length; i++) {
                        if (attrCommonData[resourceName][i].formElementType === "4") {
                            // 已办任务不设置xmlbBtnAttr，防止readOnly=true时页面元素被再次启用
                            if (processInstanceType === "done" ) {
                                continue;
                            }
                            xmlbBtnAttr.push(attrCommonData[resourceName][i].formHtmlId);
                        }
                    }
                }
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }
}

//设置页面所有元素权限
function setAllElementAttr(attrData,tabId,resourceName,tsbs){
    //存在js事件的元素权限
    var eventAttrData =[];
    //先执行表单元素权限控制
    if (isNotBlank(attrData)) {
        for (var i = 0; i < attrData.length; i++) {
            id = attrData[i].formHtmlId;
            attr = attrData[i].formElementType;
            var formRegConfigDTOList = attrData[i].formRegConfigDTOList;
            if (formRegConfigDTOList && formRegConfigDTOList.length > 0) {
                eventAttrData.push(attrData[i]);
            }
            var element = null;
            if (isNotBlank(tabId)) {
                //tabId有值时，只获取对应tab的元素
                var $element = $("#" + tabId).find("#" + id);
                if ($element.length > 0) {
                    element = $element[0];
                }
            } else {
                element = document.getElementById(id);
            }
            if (element !== null) {
                //收费信息页面分开权利人和义务人权限控制
                if(resourceName ==="sfxx" && (isNotBlank(tsbs)) && tsbs === "bdcSfxxTable") {
                    // 收费项目分开权利人义务人控制，根据names判断
                    //找到当前设置的属性值
                    var sfxmattr = element.getAttribute("attr");
                    if(isNotBlank(sfxmattr) &&(attrs.indexOf(sfxmattr) > -1)) {
                        //根据这个属性值找到所有相同属性的
                        element = getAttrElement("attr",sfxmattr);
                        if (element.length > 0) {
                            for (var j = element.length -1; j >= 0; j--) {
                                setElementAttr(attr, element[j]);
                            }
                        }
                    } else {

                    }
                } else {
                    //页面所有输入框按照name去增加属性，其他按照id
                    if ((element.tagName === "INPUT" || element.tagName === "SELECT" || element.tagName === "TEXTAREA") && !isNotBlank(tabId) && element.type !== "radio") {
                        if (element.id != "dyhth" &&  element.id.indexOf("jyhth") == -1 && !(unNames.indexOf(element.id) > -1)) {
                            //控制页面表格字段存在相同ID下根据name增加属性
                            if (element.name !== '') {
                                element = document.getElementsByName(element.name);
                                if (element.length > 0) {
                                    for (var k = element.length - 1; k >= 0; k--) {
                                        setElementAttr(attr, element[k]);
                                    }
                                }
                            }
                        } else {
                            element = document.getElementById(element.id);
                            if (element && $(element) && $(element).length > 0) {
                                setElementAttr(attr, $(element)[0]);
                            }
                        }

                    } else if (isNotBlank(element.name) && (names.indexOf(element.name) > -1 || element.type === "button" || element.tagName === "A" || element.type === "radio")) {
                        //控制页面表格字段存在相同ID下根据name增加属性
                        element = document.getElementsByName(element.name);
                        if (element.length > 0) {
                            for (var j = element.length - 1; j >= 0; j--) {
                                setElementAttr(attr, element[j]);
                            }
                        }
                    } else {
                        //1.隐藏2.只读3.必填 4.可用 5.有值只读,无值必填
                        setElementAttr(attr, element);
                    }
                }
            }
        }
    }
    //再执行元素设置js事件
    if (isNotBlank(eventAttrData) &&eventAttrData.length >0) {
        for (var i = 0; i < eventAttrData.length; i++) {
            id = eventAttrData[i].formHtmlId;
            attr = eventAttrData[i].formElementType;
            var formRegConfigDTOList = eventAttrData[i].formRegConfigDTOList;
            if (formRegConfigDTOList && formRegConfigDTOList.length > 0) {
                var element = null;
                if (isNotBlank(tabId)) {
                    //tabId有值时，只获取对应tab的元素
                    var $element = $("#" + tabId).find("#" + id);
                    if ($element.length > 0) {
                        element = $element[0];
                    }
                } else {
                    element = document.getElementById(id);
                }
                if (element !== null) {
                    //页面所有输入框按照name去增加属性，其他按照id
                    if ((element.tagName === "INPUT" || element.tagName === "SELECT") && !isNotBlank(tabId)) {
                        //控制页面表格字段存在相同ID下根据name增加属性
                        if (element.name !== '') {
                            element = document.getElementsByName(element.name);
                            if (element.length > 0) {
                                for (var k = 0; k < element.length; k++) {
                                    //添加事件
                                    setElementEvent(formRegConfigDTOList, element[k]);
                                }
                            }
                        }
                    } else if (isNotBlank(element.name) &&(names.indexOf(element.name) > -1 || element.type === "button" ||element.tagName === "A")) {
                        //控制页面表格字段存在相同ID下根据name增加属性
                        element = document.getElementsByName(element.name);
                        if (element.length > 0) {
                            for (var j = 0; j < element.length; j++) {
                                //添加事件
                                setElementEvent(formRegConfigDTOList, element[j]);
                            }
                        }
                    } else {
                        //添加事件
                        setElementEvent(formRegConfigDTOList, element);

                    }
                }
            }
        }
    }
    changeBtxbjs();

}

function setElementAttr(attr, element) {
    if (attr === "1" &&element != null) {
        if (element.type === "button" || element.type === "submit" || element.tagName === "A" || element.tagName === "SPAN" || element.tagName === "LI" || element.tagName === "DIV") {
            element.setAttribute("style", "display:none;")
        } else if (element.type === "textarea") {
            $(element.parentElement.parentElement).remove();
        } else {
            if (isNotBlank(element.className) && element.className.indexOf("jyxx") > 0) {
                //交易字段设置隐藏即移除字段（因为只是暂时性字段，后续都移到交易信息tab标签页）
                $(element).parents(".layui-inline").remove();
            } else {
                //隐藏输入框时,直接移除
                //标签为td,移除对应的th
                if(element.parentElement.parentElement != null &&element.parentElement.parentElement.tagName ==="TD"){
                    var tdElement =element.parentElement.parentElement;
                    var thName =element.name +"TH";
                    var thElement =$(tdElement).parents("table").find('th[name="' + thName + '"]');
                    //标签为td,移除对应的th
                    $(thElement).remove();

                }
                $(element.parentElement.parentElement).remove();
            }
        }
    } else if (attr === "2" &&element != null) {
        element.setAttribute("disabled", "off");
        if (element.type === "button" || element.type === "submit") {
            element.classList.add("bdc-prohibit-btn");
            element.classList.remove('bdc-major-btn');
            element.classList.remove('bdc-delete-btn');

        } else if (element.type === "checkbox" || element.type === "radio") {
            e = document.getElementsByName(element.name);
            if (e.length > 0) {
                for (var j = 0; j < e.length; j++) {
                    e[j].setAttribute("disabled", "off");
                }
            }
        } else if (element.tagName === "A" || element.tagName === "SPAN") {
            element.removeAttribute("onclick");
            if (element.id === "next") {
                element.classList.add("bdc-prohibit-btn");
            }
            //重新生成按钮不可点击
            if (isNotBlank(element.id) && (element.id.indexOf("resetqlqtzk") > -1 || element.id.indexOf("resetfj") > -1)) {
                element.classList.remove('resetqlqtzk');
                element.classList.remove('resetfj');
            }
        }
        //不可编辑的div 设置当前div下所有元素不可编辑,按钮可使用
        if (element.tagName === "DIV") {
            $(element).find("input").attr("disabled", "off");
            $(element).find("select").attr("disabled", "off");
            $(element).find("textarea").attr("disabled", "off");
        }
    } else if (attr === "3" &&element != null) {
        var attrVal = element.getAttribute("lay-verify");
        //添加必填验证
        if (!isNotBlank(attrVal)) {
            element.setAttribute('lay-verify', 'required');
        } else if (attrVal.indexOf("required") < 0) {
            element.setAttribute('lay-verify', attrVal + "|required");
        }
        var $inline =$(element).parents(".layui-inline");
        if($inline.length >0) {
            addRequiredSpan($inline);
        }else if($(element).parents(".change-textarea-margin").length > 0){
            addRequiredSpan($(element).parents(".change-textarea-margin"));
        } else{
            var $parent =$(element).parent();
            if($parent.length >0) {
                $parent.addClass("bdc-not-null");
            }
        }
        // dom元素为 当前input上层的div 例： .layui-inline元素
        function addRequiredSpan(dom){
            var requiredArr = $(dom).find(".required-span");
            if (requiredArr.length === 0) {
                $(dom).addClass("bdc-not-null");
                $(dom).find("label").prepend("<span class=\"required-span\"><sub>*</sub></span>");
            }
        }
    }
    else if(attr ==="4" &&element != null){
        //去除隐藏不可编辑属性
        if (element.type === "button" || element.type === "submit" || element.tagName === "A" || element.tagName === "SPAN" || element.tagName === "LI" || element.tagName === "DIV") {
            element.removeAttribute("style", "display:none;");
        }else if(element.parentElement != null &&element.parentElement.parentElement != null){
            element.parentElement.parentElement.removeAttribute("style", "display:none;");
            element.removeAttribute("disabled", "off");
        }
    }
    else if(attr ==="5" &&element != null){
        //按钮类只读
        if (element.type === "button" || element.type === "submit") {
            element.setAttribute("disabled", "off");
            element.classList.add("bdc-prohibit-btn");
            element.classList.remove('bdc-major-btn');
            element.classList.remove('bdc-delete-btn');

        } else if (element.type === "checkbox" || element.type === "radio") {
            element.setAttribute("disabled", "off");
            var e = document.getElementsByName(element.name);
            if (e.length > 0) {
                for (var j = 0; j < e.length; j++) {
                    e[j].setAttribute("disabled", "off");
                }
            }
        } else if (element.tagName === "A" || element.tagName === "SPAN") {
            element.setAttribute("disabled", "off");
            element.removeAttribute("onclick");
            if (element.id === "next") {
                element.classList.add("bdc-prohibit-btn");
            }
        }else{
            //获取元素值
            var value = $(element).val();
            if(isNotBlank(value)){
                //有值只读
                element.setAttribute("disabled", "off");
            }
            //不管有没有值，均添加必填验证
            //添加必填
            var attrVal = element.getAttribute("lay-verify");
            //添加必填验证
            if (!isNotBlank(attrVal)) {
                element.setAttribute('lay-verify', 'required');
            } else if (attrVal.indexOf("required") < 0) {
                element.setAttribute('lay-verify', attrVal + "|required");
            }

            var requiredArr = $(element).parents(".layui-inline").find(".required-span");
            if (requiredArr.length === 0) {
                $(element).parents(".layui-inline").addClass("bdc-not-null");
                $(element).parents(".layui-inline").find("label").prepend("<span class=\"required-span\"><sub>*</sub></span>");
            }

        }
    } else if (attr === "6" && element != null) {
        //只读设置
        element.setAttribute("disabled", "off");
        if (element.type === "button" || element.type === "submit") {
            element.classList.add("bdc-prohibit-btn");
            element.classList.remove('bdc-major-btn');
            element.classList.remove('bdc-delete-btn');
        } else if (element.type === "checkbox" || element.type === "radio") {
            e = document.getElementsByName(element.name);
            if (e.length > 0) {
                for (var j = 0; j < e.length; j++) {
                    e[j].setAttribute("disabled", "off");
                }
            }
        } else if (element.tagName === "A" || element.tagName === "SPAN") {
            element.removeAttribute("onclick");
            if (element.id === "next") {
                element.classList.add("bdc-prohibit-btn");
            }
            //重新生成按钮不可点击
            if(isNotBlank(element.id) &&(element.id.indexOf("resetqlqtzk") >-1 ||element.id.indexOf("resetfj") >-1)){
                element.classList.remove('resetqlqtzk');
                element.classList.remove('resetfj');
            }
        }

        // dom元素为 当前input上层的div 例： .layui-inline元素
        function addRequiredSpan(dom){
            var requiredArr = $(dom).find(".required-span");
            if (requiredArr.length === 0) {
                $(dom).addClass("bdc-not-null");
                $(dom).find("label").prepend("<span class=\"required-span\"><sub>*</sub></span>");
            }
        }

        //添加必填验证
        var attrVal = element.getAttribute("lay-verify");
        if (!isNotBlank(attrVal)) {
            element.setAttribute('lay-verify', 'required');
        } else if (attrVal.indexOf("required") < 0) {
            element.setAttribute('lay-verify', attrVal + "|required");
        }
        var $inline =$(element).parents(".layui-inline");
        if($inline.length >0) {
            addRequiredSpan($inline);
        }else if($(element).parents(".change-textarea-margin").length > 0){
            addRequiredSpan($(element).parents(".change-textarea-margin"));
        } else{
            var $parent =$(element).parent();
            if($parent.length >0) {
                $parent.addClass("bdc-not-null");
            }
        }
    }
}

/**
 * 设置事件
 */
function setElementEvent(formRegConfigDTOList, element) {
    if (formRegConfigDTOList && formRegConfigDTOList.length > 0) {
        for (var i = 0; i < formRegConfigDTOList.length; i++) {
            var formRegConfigDTO = formRegConfigDTOList[i];
            //事件类型
            var configEventType = formRegConfigDTO.configEventType;
            //方法名
            var configEventFunc = formRegConfigDTO.configEventFunc;
            if (typeof (eval(configEventFunc)) == "function") {
                //1.初始化事件 2.点击事件 3.失焦事件 4.改变事件
                if (configEventType === 1) {
                    var xmselect = $(element).attr('xm-select');
                    if (isNotBlank(xmselect)) {
                        eval(configEventFunc + "('" + element.value + "','" + xmselect + "','" + element.value + "',"+1+")");
                    } else {
                        eval(configEventFunc + "('" + element.value + "','" + element.id + "','init',"+1+")");
                    }
                } else if (configEventType === 2) {
                    $(element).on('click', function () {
                        eval(configEventFunc + "()");
                    });

                } else if (configEventType === 3) {
                    $(element).on('blur', function () {
                        eval(configEventFunc + "()");
                    })
                } else if (configEventType === 4) {
                    if (element.tagName === "SELECT") {
                        //监听formSelects下拉框内容修改
                        var xmselect = $(element).attr('xm-select');
                        if (isNotBlank(xmselect)) {
                            formSelects.on(xmselect, function (id, vals, val, isAdd, isDisabled) {
                                var yselect = "";
                                if(isNotBlank(vals[0])){
                                    yselect = vals[0].value;
                                }
                                eval(configEventFunc + "('" + val.value + "','" + xmselect + "','" + yselect + "',"+4+")");
                            });
                        } else {
                            //监听select下拉框内容修改
                            form.on('select(' + element.name + ')', function (data) {
                                eval(configEventFunc + "('" + data.value + "','" + data.elem.id + "','change',"+4+")");
                            });
                        }
                    } else {
                        $(element).on('change', function () {
                            eval(configEventFunc + "()");
                        })
                    }
                }

            }

        }
    }

}

/**
 * 设置下拉框不可编辑的样式
 */
function resetSelectDisabledCss() {
    //select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
    $('.layui-select-disabled i').addClass("bdc-hide");
    $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
    changeBtxbjs();
}

function getAttrElement(attr,val){
    var e= document.all;
    var a= [];
    for(var i=0;i<e.length;i++){
        if(e[i].getAttribute(attr)===val){
            a.push(e[i])
        }
    }
    return a;
}


/**
 * 设置input不可编辑的样式
 */
function resetInputDisabledCss() {
    $('i').remove('.fa');
    // 设置不可用的属性
    var img = '<img src="../../static/lib/bdcui/images/lock.png" alt="">';
    $("input[disabled='off']").after(img);
    // 为页面设置焦点
    $('.content-title').click();
}

// 通过填写的权利人名称查询 xt_jg表查询frmc  如果没有值则再调用共享查询接口
// 该方法为表单中心的 权利人详细的 zjh和qlrmc的失焦事件
function queryFrmcByQlrmcAndZjh(){
    var qlrmc = $("input[name='qlrmc']").val();
    var zjh = $("input[name='zjh']").val();

    if(qlrmc == "" || zjh == ""){
        return;
    }

    var queryObj = {};
    queryObj["qlrlb"] = $("select[name='qlrlb']").val();
    queryObj["jgmc"] = $("input[name='qlrmc']").val();
    queryObj["jgzjzl"] = $("select[name='zjzl']").val();
    queryObj["jgzjbh"] = $("input[name='zjh']").val();
    queryObj["qlrlx"] = $("#qlrlx").val();

    $.ajax({
        url: getContextPath() + "/slym/qlr/queryFrmcByQlrmcAndZjh",
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(queryObj),
        success: function (data) {
            if(data != null && data != ""){
                $("#frmc").val(data);
            }
        }
    });
}

