var $, form, layer, element, table, laytpl, laydate;
var zdList = {a: []};
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var zsmodel = getQueryString("zsmodel");
var readOnly = getQueryString("readOnly");
var isSubmit = true;
var zxlc = getQueryString("zxlc");
var printValue = getQueryString("print");
var taskId = getQueryString("taskId");
// 审批表模板地址
var sqSpbModelUrl = getIP() + "/realestate-accept-ui/static/printmodel/bdcSqSpb.fr3";
var sqsDamlModelUrl = getIP() + "/realestate-accept-ui/static/printmodel/spbDaml.fr3";
var damlModelUrl = getIP() + "/realestate-accept-ui/static/printmodel/daml.fr3";
var dylxArr = ["bdcSqSpb", "daml","dafm"];
var sessionKey = "bdcShxx";
setDypzSession(dylxArr, sessionKey);
var yxmGzlslid = "";
//页面入口
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laytpl = layui.laytpl;
    laydate = layui.laydate;
    addModel();
    //加载页面上方按钮模块
    // setTimeout("loadButtonArea('dajj')", 10);
    setTimeout(function () {
        try {
            $.when(loadData()).done(function () {
                removeModal();
            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e, "加载失败");
            return false;
        }
    }, 10);
    titleShowUi();
})

//加载页面数据（入口）
function loadData() {
    //获取字典
    getCommonZd(function (data) {
        if (isNotBlank(data)) {
            zdList = data;
        }
    });
    //加载档案交接信息
    loadDajjxx();
}

function loadDajjxx() {
    getReturnData("/slym/dajj", {gzlslid: processInsId, taskid: taskId}, "GET", function (data) {
        if (data) {
            var json = {
                zd: zdList,
                daxx: data
            };
            var tpl = jbxxTpl.innerHTML;
            var view = document.getElementById('dajbXx');
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            yxmGzlslid = data.yxmGzlslid;
            //加载收件材料数据
            loadDaSjcl(data.bdcSlSjclDOList);
            renderDate();
            getStateById(false, formStateId, "dajj", "", "")
            form.render();
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

function loadDaSjcl(sjclList) {

    var json = {
        bdcSlSjclDOList: sjclList,
        zd: zdList
    };
    var tpl = sjclTpl.innerHTML, view = document.getElementById('sjcl');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
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


function printDajj(dylx) {
    var modelUrl = "";
    var dataUrl = "";
    var gzlslids = [];
    gzlslids.push(yxmGzlslid);
    var dypz = "";
    //先从后台获取到打印类型
    getReturnData("/slPrint/damldylx", {gzlslids: gzlslids.join(",")}, "GET", function (data) {
        if (data) {
            dypz = data;
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    if (dylx === "bdcSqSpb") {
        modelUrl = sqSpbModelUrl;
        if (dypz.spbdylx) {
            dylx = dypz.spbdylx;
        }
        dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslids[0] + "/" + dylx + "/xml?zxlc=" + false;
    } else if (dylx === "daml") {
        if (dypz.damldylx) {
            dylx = dypz.damldylx;
        }
        modelUrl = damlModelUrl;
        dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/" + dylx + "/" + gzlslids.join(",");
    } else if (dylx === "spbdamlWJ") {
        if (dypz.spbdylx && dypz.damldylx && dypz.dafmdylx) {
            dylx = dypz.spbdylx + dypz.damldylx + dypz.dafmdylx;
        }
        modelUrl = sqsDamlModelUrl;
        dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/spbdaml/" + dylx + "/" + gzlslids.join(",");
    }else if (dylx === "spbdaml") {
        if (dypz.spbdylx && dypz.damldylx) {
            dylx = dypz.spbdylx + dypz.damldylx;
        }
        modelUrl = sqsDamlModelUrl;
        dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/spbdaml/" + dylx + "/" + gzlslids.join(",");
    }else if(dylx === "dafm"){
        if (dypz.dafmdylx) {
            dylx = dypz.dafmdylx;
        }
        modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
        dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/dafm/" + dylx + "/" + gzlslids.join(",");
    }
    dylxArr.push(dylx);
    setDypzSession(dylxArr, sessionKey);
    console.log("打印类型：" + dylx + "工作流实例id：" + gzlslids);
    printChoice(dylx, "realestate-register-ui", dataUrl, modelUrl, false, sessionKey);
}

function showSjcl() {
    var bdcSlWjscDTO = queryBdcSlWjscDTO('', true);
    bdcSlWjscDTO.spaceId = yxmGzlslid;
    bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
    var url = getContextPath() + "/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
    openSjcl(url, false, "附件上传");
}