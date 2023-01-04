var id;
var attr;
var printValue = $.getUrlParam('print');
var moduleCode = $.getUrlParam('moduleCode') || "";
function getStateById(readOnly, formStateId, resourceName) {
    setElementAttrByFormState(formStateId, resourceName);
    if (readOnly == true || readOnly == "true") {
        setAllElementDisabled();
    }
    if(moduleCode){
        setElementAttrByModuleAuthority(moduleCode);
    }
}

function getStateByIdQlxx(readOnly, formStateId, resourceName) {
    setElementAttrByFormStateQlxx(formStateId, resourceName);
    if (readOnly == true || readOnly == "true") {
        setAllElementDisabled();
    }
    if(moduleCode){
        setElementAttrByModuleAuthority(moduleCode);
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

        // 对应需求编号28926
        // 南通市区,在审核就提前生成证书的件,在待办任务中打开,无法点击房屋清单和房屋清单(户)按钮,导致无法顺利审核,所以需要放开这两个按钮权限
        if ("nantong_printBtn" == item.name ){
            item.removeAttribute("disabled", "off");
            item.classList.remove('layui-btn-disabled');
            item.classList.remove('bdc-prohibit-btn');
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
        url: "/realestate-register-ui/rest/v1.0/form/" + formStateId + "/" + resourceName,
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
                            } else if (element.tagName === "A") {
                                //页面表格中的权限控制，多条数据循环控制
                                element = document.getElementsByName(element.name);
                                if (element.length > 0) {
                                    for (var k = element.length - 1; k >= 0; k--) {
                                        element[k].setAttribute("style", "display:none;")
                                    }
                                }
                            } else {
                                element.parentElement.parentElement.setAttribute('style', 'display:none;');
                            }
                        } else if (attr === "2") {
                            element.setAttribute("disabled", "off");
                            if (element.type === "button" || element.type === "submit" || element.type === "reset") {
                                element.classList.add("layui-btn-disabled");
                            }
                        } else if (attr === "3") {
                            element.setAttribute('lay-verify', 'required');
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
        url: "/realestate-register-ui/rest/v1.0/form/" + formStateId + "/" + resourceName,
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
        url: "/realestate-register-ui/rest/v1.0/form/moduleAuthority/" + moduleCode,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            var available = 'false';
            console.info(data);
            if (data && !isEmptyObject(data)) {
                for (var i in data) {
                    id = i;
                    attr = data[i];
                    var element = document.getElementById(id);
                    if (element !== null) {
                        //1.隐藏2.只读3.必填
                        if (attr === "hidden") {
                            if (element.type === "button" || element.type === "submit" || element.type === "reset") {
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
                        } else if (attr === "required") {
                            element.setAttribute('lay-verify', 'required');
                        } else if (attr === "available") {
                            available = 'true';
                            removeDiabledById(id);
                        }
                    }
                }
            }
            // 这里对input框进行样式修改，select框需要在各页面渲染处修改
            if (available === 'false') {
                resetInputDisabledCss();
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

/**
 *根据模块管理中的元素配置设置权限 （针对于嵌套页面的处理）
 * @param moduleCode 资源名
 * @param window 窗口
 */
function setWindowElementAttrByModuleAuthority(moduleCode, window) {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/form/moduleAuthority/" + moduleCode,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            var available = 'false';
            console.info(data);
            if (data && !isEmptyObject(data)) {
                for (var i in data) {
                    id = i;
                    attr = data[i];
                    var element = window.document.getElementById(id);
                    if (element !== null) {
                        //1.隐藏2.只读3.必填
                        if (attr === "hidden") {
                            if (element.type === "button" || element.type === "submit" || element.type === "reset") {
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
                        } else if (attr === "required") {
                            element.setAttribute('lay-verify', 'required');
                        } else if (attr === "available") {
                            available = 'true';
                            removeWindowDiabledById(id, window);
                        }
                    }
                }
            }
            // 这里对input框进行样式修改，select框需要在各页面渲染处修改
            if (available === 'false') {
                resetInputDisabledCss();
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function removeDiabledById(id) {
    var disableEles = document.querySelectorAll("#" + id);
    $.each(disableEles, function (k, v) {
        $(v).removeAttr('disabled');
        $(v).removeAttr('readonly');
        $(v).parents('.layui-input-inline').find('img').remove();
        $(v).parents('.layui-input-inline').removeClass('bdc-one-icon');
    });
}

function removeWindowDiabledById(id, window) {
    var disableEles = window.document.querySelectorAll("#" + id);
    $.each(disableEles, function (k, v) {
        $(v).removeAttr('disabled');
        $(v).removeAttr('readonly');
        $(v).parents('.layui-input-inline').find('img').remove();
        $(v).parents('.bdc-td-box').find('img').remove();
        $(v).parents('.layui-input-inline').removeClass('bdc-one-icon');
        $(v).parents('.bdc-td-box').removeClass('bdc-one-icon');
    });
}