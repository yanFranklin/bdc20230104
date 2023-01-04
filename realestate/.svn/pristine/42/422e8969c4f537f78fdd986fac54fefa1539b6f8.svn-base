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
var cxjgyq = ['出具查询结果证明','复制','查阅','抄录','出具查询结果证明及复制'];

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
        $(".zjh").each(function () {
            var zjh =$(this).val();
            if(isNotBlank(zjh)){
                var zjhid =$(this)[0].id;
                if(isNotBlank(zjhid)) {
                    var zjzlid = zjhid.replace("zjh", "zjzl");
                    if ($("#" + zjzlid).val() == 1) {
                        var msg = checkSfzZjh(zjh);
                        if (isNotBlank(msg)) {
                            $(this).addClass('layui-form-danger');
                            isSubmit = false;
                            verifyMsg = msg;
                        }
                    }
                }
            }
        });
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            isFirst = true;
            removeModal();
            return false;
        } else {
            addModel();
            setTimeout(function () {
                $.when(saveCdxx(), saveBz(), saveSjcl()).done(function () {
                    console.log("保存查档信息完毕");
                    ityzl_SHOW_SUCCESS_LAYER("保存成功");
                    removeModal();
                    loadCdxx();
                    //loadSjcl();
                })
            }, 50);
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
        // form.on('select(cdrzjzl)', function(data){
        //     addSfzYz(data.value, data.elem);
        // });

        //查封机关
        form.on('select(cdlx)', function (data) {   //选择移交单位 赋值给input框
            $(this).parents('.layui-input-inline').find("input[name='cdlx']").val(data.value);
            $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
            form.render();
            resetSelectDisabledCss();
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
    //原流程收件材料
    $("#ylcsjcl").click(function () {
        ylcsjcl();
    });
    //补充收件材料查看
    $("#bcsjcl").click(function () {
        bcclck();
    });
    getStateById(readOnly, formStateId, "cdym", "", "");
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
//加载查档信息
function loadCdxx() {
    $.ajax({
        url: getContextPath() + "/cdlc/getCdxx/" + processInsId,
        type: "GET",
        success: function (data) {
            if (isNotBlank(data)) {
                console.log("查档信息加载完毕");
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
        pllc: false,
        cdxx: data,
        cxjgyq: cxjgyq

    };
    if (data.length && data.length > 1) {
        //批量查档流程
        json = {
            zd: zdList,
            pllc: true,
            cdxx: data[0],
            cdxxList: data
        }
        $('#singaldjb').hide();
    }else {
        $('#singaldjb').show();
        //console.log(data[0])
        $('#singaldjb').click(function (e) {
            detailView(data.yBdcXmDO.gzlslid);
        })
    }
    var tpl = cdxxTpl.innerHTML, view = document.getElementById('jbcdxx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    renderDate(form, formStateId);
    form.render('select');
    getStateById(readOnly, formStateId, 'cdym', "", "");
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
    getStateById(readOnly, formStateId, 'cdym', 'sjcl');
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
    cdxxObj.xcxr = $('#xcxr').val();
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
    } else {
        //移除证件号验证
        if (isNotBlank(zjhVerify) && zjhVerify.indexOf("identitynew") > -1) {
            $zjh.attr("lay-verify", zjhVerify.replace("identitynew", ""));
        }
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
    uiElement.offset({top: X + 40, left: Y - 70});
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

//展现原流程的收件材料
function ylcsjcl() {
    getReturnData("/cdlc/yxm", {gzlslid: processInsId}, "GET", function (data) {
        var url = "";
        if (data && data.pllc) {
            //打开收件材料列表
            url = getContextPath() + "/view/cdym/fjclList.html?xmids=" + data.xmidList.join(",");
        } else if (data.xmly === 1) {
            //打开文件管理器页面
            var bdcSlWjscDTO = queryBdcSlWjscDTO('', true);
            bdcSlWjscDTO.spaceId = data.gzlslid;
            bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
            // bdcSlWjscDTO.storageUrl = "http://192.168.2.99:8030/storage";
            url = getContextPath() + "/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
            openSjcl(url, false, "附件上传");
            return false;
        } else if (data.xmly !== 1) {
            //打开etl-cz 的老系统文件访问页面
            url = "/realestate-etl/view/lsfj/popup-img.html?processinsid=" + data.gzlslid;
        } else {
            ityzl_SHOW_WARN_LAYER("未找到原项目信息");
            return false;
        }
        var width = $(window).width() + "px";
        var height = $(window).height() + "px";
        var index = layer.open({
            title: "附件信息",
            type: 2,
            content: url,
            area: [width, height],
            maxmin: true
        });
        layer.full(index);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//补充材料查看--不管上一手数据是老数据还是不动产数据，统一查大云的信息，因为所有补充的都是上传到大云的
function bcclck() {
    getReturnData("/cdlc/yxm", {gzlslid: processInsId}, "GET", function (data) {
        var url = "";
        if (data && data.pllc) {
            //打开收件材料列表
            url = getContextPath() + "/view/cdym/fjclList.html?xmids=" + data.xmidList.join(",");
        } else {
            //打开文件管理器页面
            var bdcSlWjscDTO = queryBdcSlWjscDTO('', true);
            bdcSlWjscDTO.spaceId = data.gzlslid;
            bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
            // bdcSlWjscDTO.storageUrl = "http://192.168.2.99:8030/storage";
            url = getContextPath() + "/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
            openSjcl(url, false, "附件上传");
            return false;
        }
        var width = $(window).width() + "px";
        var height = $(window).height() + "px";
        var index = layer.open({
            title: "附件信息",
            type: 2,
            content: url,
            area: [width, height],
            maxmin: true
        });
        layer.full(index);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })

}

//查看登记簿
function detailView(processInsId) {
    window.open('/realestate-register-ui/view/djbxx/bdcDjbList.html?processInsId=' + processInsId);
}

