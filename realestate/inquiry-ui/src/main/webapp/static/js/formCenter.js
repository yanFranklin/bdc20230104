var id;
var attr;
var printValue = $.getUrlParam('print');

// 获取页面表单标识，用于权限控制
var moduleCode = $.getUrlParam('moduleCode');
//隐藏权限控制
if (moduleCode){
    setElementAttrByModuleAuthority(moduleCode);
}

function getStateById(readOnly, formStateId, resourceName) {
    setElementAttrByFormState(formStateId, resourceName);
    if (readOnly == true || readOnly == "true") {
        setAllElementDisabled();
    }
}

function getStateByIdQlxx(readOnly, formStateId, resourceName) {
    setElementAttrByFormStateQlxx(formStateId, resourceName);
    if (readOnly == true || readOnly == "true") {
        setAllElementDisabled();
    }
}

/**
 * 限制表单所有元素不可用
 */
function setAllElementDisabled() {
    // 设置输入框不可编辑
    var input = $('input');
    for (var i = 0; i < input.length; i++) {
        if (input[i].type !== "hidden") {
            input[i].setAttribute('disabled', 'off');
            input[i].setAttribute('readonly', 'readonly');
        }
    }
    $('textarea').attr('readonly', 'readonly');
    $('select').attr('disabled', 'off');
    $('textarea').attr('disabled', 'off');
    $('checkbox').attr('disabled', 'off');
    $('radio').attr('disabled', 'off');
    // 设置按钮权限
    var btnArr = $('.layui-btn');
    $.each(btnArr, function (index, item) {
        // 为了好控制权限，建议把相同功能的按钮设置同一个name，免得写多个id去判断
        // 预览的按钮，权限全开。打印、导出的按钮需要在打印参数为true下才可用
        if ('ylBtn' == item.name
            || (('printBtn' == item.id || 'batchPrint' == item.id || 'exportPdf' == item.id || 'zsyl' == item.id || 'printBtn' == item.name) && printValue == 'true')) {
            // 去除打印按钮上添加的 css 样式
            item.removeAttribute("style", "display:none;");
            item.removeAttribute("disabled", "off");
            item.classList.remove("layui-btn-disabled");
            // 重新渲染表格
            $('.laytable-cell-checkbox input').removeAttr('disabled').removeAttr('readOnly');
            return;
        }
        // 为保证详情按钮可以正常查看，设置为a标签
        if (item.type == 'button' || item.type == 'submit' || 'reset' == item.type) {
            item.setAttribute('disabled', 'off');
            item.classList.add('layui-btn-disabled');
            item.setAttribute('disabled', 'off');
            item.classList.add('layui-btn-disabled');
            item.classList.add('bdc-prohibit-btn');
            item.classList.remove('bdc-major-btn');
            item.classList.remove('bdc-delete-btn');
        }
    });
    // 审核信息专用
    $('.shxx_yj p').addClass('bdc-prohibit-text');
    $('.shxx_yj p').removeAttr('contenteditable');
    $('a').removeAttr('onclick');
    $('a').addClass("prohibit_sign");
    layui.use(['form'], function () {
        var form = layui.form;
        form.render('select');
    })
}

/**
 *根据表单中心的配置设置权限
 * @param formStateId
 * @param resourceName
 */
function setElementAttrByFormState(formStateId, resourceName) {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/form/" + formStateId + "/" + resourceName,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data && !isEmptyObject(data)) {
                for (var i in data) {
                    id = data[i].formHtmlId;
                    attr = data[i].formElementType;
                    var element = document.getElementById(id);
                    if (element !== null) {
                        //1.隐藏2.只读3.必填
                        if (attr === "1") {
                            if (element.type === "button" || element.type === "submit" || element.type === "reset" || element.tagName === "DIV") {
                                element.setAttribute("style", "display:none;")
                            } else if (element.type === "textarea") {
                                element.parentElement.parentElement.setAttribute('hidden', 'hidden');
                            } else {
                                element.parentElement.parentElement.setAttribute('style', 'display:none;');
                            }
                        } else if (attr === "2") {
                            element.setAttribute("disabled", "off");
                            if (element.type === "button" || element.type === "submit" || element.type === "reset") {
                                // element.setAttribute('style',"background-color:#C7C4C1;color:black");
                                element.classList.add("layui-btn-disabled");
                            }
                        } else if (attr === "3") {
                            element.setAttribute('lay-verify', 'required');
                        }
                    }
                }
            }
            // 这里对input框进行样式修改，select框需要在各页面渲染处修改
            resetInputDisabledCss();
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

/**
 *根据表单中心的配置设置权限
 * @param formStateId
 * @param resourceName
 */
function setElementAttrByFormStateQlxx(formStateId, resourceName) {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/form/" + formStateId + "/" + resourceName,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data && !isEmptyObject(data)) {
                for (var i in data) {
                    id = data[i].formHtmlId;
                    attr = data[i].formElementType;
                    var element = document.getElementById(id);
                    if (element !== null) {
                        //1.隐藏2.只读3.必填
                        if (attr === "1") {
                            if (element.type === "button" || element.type === "submit" || element.type === "reset") {
                                element.setAttribute("style", "display:none;")
                            } else if (element.type === "textarea") {
                                element.parentElement.parentElement.setAttribute('hidden', 'hidden');
                            } else {
                                element.parentElement.parentElement.setAttribute('style', 'display:none;');
                            }
                        } else if (attr === "2") {
                            element.setAttribute("disabled", "off");
                            if (element.type === "button" || element.type === "submit" || element.type === "reset") {
                                // element.setAttribute('style',"background-color:#C7C4C1;color:black");
                                element.classList.add("layui-btn-disabled");
                            }
                        } else if (attr === "3") {
                            element.setAttribute('lay-verify', 'required');
                            $('#' + id).parents("td").prev().prepend("<span class=\"required-span\"><sub>*</sub></span>");
                        }
                    }
                }
            }
            // 这里对input框进行样式修改，select框需要在各页面渲染处修改
            resetInputDisabledCss();
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

/**
 * 设置下拉框不可编辑的样式
 */
function resetSelectDisabledCss() {
    //1.2 select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
    $('.layui-select-disabled i').addClass("bdc-hide");
    var img = '<img src="../../static/lib/bdcui/images/lock.png" alt="">';
    // $("input[class$='layui-disabled']").after(img);
    $('.layui-select-disabled dl').after(img);
    $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
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

/**
 *设置打印按钮可用，其他按钮隐藏
 **/
function setPrintBtnAbled() {
    // 设置按钮权限
    var btnArr = $('.layui-btn');
    var firstBtn = 0;
    $.each(btnArr, function (index, item) {
        // 为了好控制权限，建议把相同功能的按钮设置同一个name，免得写多个id去判断
        if ('printBtn' == item.name) {
            // 去除打印按钮上添加的 css 样式
            item.removeAttribute("style", "display:none;");
            item.removeAttribute("disabled", "off");
            item.classList.remove("layui-btn-disabled");
            item.classList.remove("bdc-prohibit-btn");
            // 第一个按钮设置为主按钮
            if (0 == firstBtn) {
                item.classList.add("bdc-table-second-btn");
                firstBtn = 1;
            }
            // 重新渲染表格
            $('.laytable-cell-checkbox input').removeAttr('disabled').removeAttr('readOnly');
            return;
        }
        // 为保证详情按钮可以正常查看，设置为a标签
        if (item.id != 'search' && (item.type == 'button' || item.type == 'submit' || 'reset' == item.type)) {
            item.setAttribute("style", "display:none;");
        }
    });
}

/**
 *根据模块管理中的元素配置设置权限
 * @param moduleCode 资源名
 */
function setElementAttrByModuleAuthority(moduleCode) {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/form/moduleAuthority/" + moduleCode,
        type: 'GET',
        dataType: 'json',
        cache: false,
        async: false,
        success: function (data) {
            if (data && !isEmptyObject(data)) {
                for (var i in data) {
                    id = i;
                    attr = data[i];
                    if (attr === "hidden") {
                        if ((document.getElementById(id)) || (document.getElementById(id + "Div")) || (document.getElementById(id + "MoreSearch"))) {
                            if (document.getElementById(id)) {
                                $("."+ id).hide();
                                document.getElementById(id).setAttribute("style", "display:none;");
                            }
                            if (document.getElementById(id + "Div")) {
                                document.getElementById(id + "Div").setAttribute("style", "display:none;");
                                if (document.getElementById(id + "Label")) {
                                    document.getElementById(id + "Label").setAttribute("style", "display:none;");
                                }
                            }
                            if (document.getElementById(id + "MoreSearch")) {
                                document.getElementById(id + "MoreSearch").setAttribute("style", "display:none;");
                            }
                            continue;
                        }
                    }
                    var element = document.getElementById(id);
                    if (element !== null) {
                        //1.隐藏2.只读3.必填
                        if (attr === "hidden") {
                            if (element.type === "button" || element.type === "submit" || element.type === "reset" || element.id === "qtzm") {
                                element.setAttribute("style", "display:none;")
                            } else if (element.type === "textarea") {
                                element.parentElement.parentElement.setAttribute('hidden', 'hidden');
                            } else {
                                element.parentElement.parentElement.setAttribute('style', 'display:none;');
                            }
                        } else if (attr === "readonly") {
                            element.setAttribute("disabled", "off");
                            if (element.type === "button" || element.type === "submit" || element.type === "reset") {
                                // element.setAttribute('style',"background-color:#C7C4C1;color:black");
                                element.classList.add("layui-btn-disabled");
                            }
                            if (element.id === "qtzm") {
                                //点击更多内a标签，隐藏
                                $('.bdc-table-top-more-show a').hide();
                            }
                        } else if (attr === "required") {
                            element.setAttribute('lay-verify', 'required');
                        } else if (attr === "available") {
                            if (element.type === "button" || element.type === "submit" || element.type === "reset" || element.id === "qtzm") {
                                element.setAttribute("style", "display:;")
                            } else if (element.type === "textarea") {
                                element.parentElement.parentElement.setAttribute('hidden', '');
                            } else {
                                element.parentElement.parentElement.setAttribute('style', 'display:;');
                            }
                        }
                    }
                }
            }
            // 这里对input框进行样式修改，select框需要在各页面渲染处修改
            resetInputDisabledCss();
        }, error: function (xhr, status, error) {
            failMsg("页面权限请求设置失败，请联系管理员！");
        }
    });
}

/**
 *根据模块管理中的元素配置设置权限
 * @param moduleCode 资源名
 */
function setElementAttrByModuleAuthorityBeforeRendering(moduleCode,sourceData) {
    if(!isNullOrEmpty(moduleCode)) {
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/form/moduleAuthority/" + moduleCode,
            type: 'GET',
            dataType: 'json',
            cache: false,
            async: false,
            success: function (data) {
                if (data && !isEmptyObject(data) && sourceData ) {
                    //查询条件
                    var cxtjList = sourceData.cxtjDOList;
                    for(var i in data) {
                        if(data[i] === "hidden") {
                            if($("."+i)) {
                                $("."+i).hide();
                            }
                            if($("#"+i)){
                                $("#"+i).hide();
                            }
                        }
                    }
                    var resultList = [];
                    for(var j in cxtjList){
                        if (cxtjList[j].tjzdid && data[cxtjList[j].tjzdid] && data[cxtjList[j].tjzdid]  === "hidden"){
                            continue;
                        }else {
                            resultList.push(cxtjList[j]);
                        }
                    }
                    sourceData.cxtjDOList = resultList;
                    //页面按钮
                    var ymgjList = [];
                    var ymgj = eval('(' + sourceData.ymgj + ')');
                    if (ymgj && ymgj.length>0){
                        for(var x in ymgj){
                            if (ymgj[x].name && data[ymgj[x].name] && data[ymgj[x].name] === "hidden"){
                                continue;
                            }else {
                                ymgjList.push(ymgj[x]);
                            }
                        }
                        sourceData.ymgj = JSON.stringify(ymgjList);
                    }
                    var zdyymgjList = [];
                    var zdyymgj = eval('(' + sourceData.zdyymgj + ')');
                    if (zdyymgj && zdyymgj.length>0){
                        for(var y in zdyymgj){
                            if (zdyymgj[y].name && data[zdyymgj[y].name] && data[zdyymgj[y].name] === "hidden"){
                                continue;
                            }else {
                                zdyymgjList.push(zdyymgj[y]);
                            }
                        }
                        sourceData.zdyymgj = JSON.stringify(zdyymgjList);
                    }
                }
            }, error: function (xhr, status, error) {
                failMsg("页面权限请求设置失败，请联系管理员！");
            }
        });
    }
}

/**
 * (合肥)获取模块管理中的元素配置权限
 * @param moduleCode 资源名
 */
function getElementAuthority(moduleCode) {
    var result;
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/form/moduleAuthority/" + moduleCode,
        type: 'GET',
        dataType: 'json',
        cache: false,
        async: false,
        success: function (data) {
            if (data && !isEmptyObject(data)) {
                result = data;
            }
        }, error: function (xhr, status, error) {
            failMsg("页面权限请求设置失败，请联系管理员！");
        }
    });
    return result;
}