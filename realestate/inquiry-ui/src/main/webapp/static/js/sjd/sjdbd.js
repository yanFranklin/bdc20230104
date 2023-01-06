// 审批表模板地址
var sqSpbModelUrl = getIP() + "/realestate-register-ui/static/printModel/bdcSqSpb.fr3";
// 发证记录打印模板地址
var fzjlModelUrl = getIP() + "/realestate-register-ui/static/printModel/fzjl.fr3";
// 抵押物清单打印模板地址
var dyawqdModelUrl = getIP() + "/realestate-register-ui/static/printModel/dyawqd.fr3";
//户室图模板地址
var hstModelUrl = "C:/GTIS/hst.fr3";
// 证书样式-首次证明单
var sczmdModel = "sczmd";
var zslxChecked = 1;
// 证书样式-证书
var zsModel = 1;
// 证书样式-证明
var zmModel = 2;
// 证明单类型
var zmdModel = 3;
// 证书模板地址
var zsModelUrl = "C:/GTIS/zs.fr3";
// 证明模板地址
var zmModelUrl = "C:/GTIS/zm.fr3";
// 证明单模板地址
var zmdModelUrl = "C:/GTIS/zmd.fr3";
// 打印配置
var dypzMap;
// 内网地址
var printIp;

// 打印收件单
function dysjd() {
    var gzlslidArr = jyxz(checkeddata);
    if (gzlslidArr && gzlslidArr.length > 0) {
        standardPrint('sjd', '', '', gzlslidArr.join(','));
    }
}

// 校验选择
function jyxz(datas) {
    if (isNullOrEmpty(datas)) {
        warnMsg('请选择需要打印的数据');
        return;
    }
    // 判断是否为同一流程
    var gzldyid = datas[0].GZLDYID;
    var gzlslidArr = [];
    for (var i = 0; i < datas.length; i++) {
        if (gzldyid !== datas[i].GZLDYID) {
            warnMsg('请选择相同流程进行打印');
            return;
        }
        if (gzlslidArr.indexOf(datas[i].GZLSLID) === -1) {
            gzlslidArr.push(datas[i].GZLSLID);
        }
    }
    return gzlslidArr;
}

// 校验抵押物选择
function jydywxz(datas) {
    var result = {};
    if (isNullOrEmpty(datas)) {
        warnMsg('请选择需要打印的数据');
        return;
    }
    // 判断是否为同一流程
    var gzldyid = datas[0].GZLDYID;
    var qllx = datas[0].QLLX;
    if (qllx !== 37 && qllx !== 96) {
        warnMsg('请选择抵押权项目进行打印');
        return;
    }
    var gzlslidArr = [];
    for (var i = 0; i < datas.length; i++) {
        if (gzldyid !== datas[i].GZLDYID) {
            warnMsg('请选择相同流程进行打印');
            return;
        }
        // 预告抵押
        if (qllx === 96 && (datas[i].QLLX !== 96 || (datas[i].YGDJZL !== 3 && datas[i].YGDJZL !== 4))) {
            warnMsg('请选择抵押权项目进行打印');
            return;
        }
        // 抵押
        if (qllx === 37 && datas[i].QLLX !== 37) {
            warnMsg('请选择抵押权项目进行打印');
            return;
        }
        gzlslidArr.push(datas[i].GZLSLID)
    }
    result.qllx = qllx;
    result.gzlslidArr = gzlslidArr;
    return result;
}

// 打印申请书
function dysqs() {
    var gzlslidArr = jyxz(checkeddata);
    if (gzlslidArr && gzlslidArr.length > 0) {
        standardPrint('sqs', '', '', gzlslidArr.join(','));
    }
}

// 打印审批表
function dyspb() {
    var gzlslidArr = jyxz(checkeddata);
    if (isNullOrEmpty(gzlslidArr)) {
        return false;
    }
    var sessionKey = 'bdcShxx';
    if (gzlslidArr && gzlslidArr.length > 0) {
        var gzlslids = gzlslidArr.join(",");
        var dylx = 'bdcSqSpb';
        var dylxArr = [dylx];
        getDylxDypz(dylxArr);
        var appName = "realestate-inquiry-ui";
        var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/spb/" + gzlslids + "/" + dylx + "/xml/pl";
        bdPrintChoice(dylx, appName, dataUrl, sqSpbModelUrl, false, sessionKey);
    }
    // 禁止提交后刷新
    return false;
}

function getPrintIp() {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/print/getPrintIp",
        async: false,
        success: function (data) {
            removeModal();
            if (isNotBlank(data)) {
                printIp = data;
            }
        }, error: function (e) {
            removeModal();
        }
    });
}

// 打印证书证明
function dyzszm() {
    if (isNullOrEmpty(checkeddata)) {
        warnMsg('请选择需要打印的数据');
        return;
    }
    // 只能选取一条数据进行打印
    if (checkeddata.length > 1) {
        warnMsg('只能选取一条数据进行打印');
        return;
    }
    var zsid = checkeddata[0].ZSID;
    var zslx = checkeddata[0].ZSLX
    if (isNullOrEmpty(zsid)) {
        warnMsg("没有可打印的证书信息！");
        return;
    }
    if ("1" == zslx) {
        window.open("/realestate-register-ui/view/zsxx/bdcZs.html?zsid=" + zsid + "&readOnly=false&sfbd=true");
    } else if ("2" == zslx) {
        window.open("/realestate-register-ui/view/zsxx/bdcZm.html?zsid=" + zsid + "&readOnly=false&sfbd=true");
    } else if ("3" == zslx) {
        window.open("/realestate-register-ui/view/zsxx/bdcSczmd.html?zsid=" + zsid + "&readOnly=false&sfbd=true");
    }

   /*
   // 判断是否为同一流程
    var gzldyid = checkeddata[0].GZLDYID;
    for (var i = 0; i < checkeddata.length; i++) {
        if (gzldyid !== checkeddata[i].GZLDYID) {
            warnMsg('请选择相同流程进行打印');
            return;
        }
    }
    var zszmArr = [];
    for (let i = 0; i < checkeddata.length; i++) {
        if (isNotBlank(checkeddata[i].ZSID) && isNotBlank(checkeddata[i].ZSLX)) {
            var zszm = {};
            zszm.zsid = checkeddata[i].ZSID;
            zszm.zslx = checkeddata[i].ZSLX;
            zszmArr.push(zszm);
        }
    }
    if (isNullOrEmpty(zszmArr)) {
        warnMsg('请选择需要打印的证书证明');
        return;
    }
    // 先获取当前选中的数据
    if (zszmArr.length > 200) {
        warnMsg("为确保打印效率，最多支持" + 200 + "本打印！");
        return false;
    }
    var zsidArr = [];
    // 需要打印的证书ID
    var zsZsidArr = [];
    // 需要打印的证明ID
    var zmZsidArr = [];
    // 需要打印的证明单ID
    var zmdZsidArr = [];
    $.each(zszmArr, function (key, val) {
        if (!isNullOrEmpty(val.zsid.trim()) && (zsidArr.length === 0 || (zsidArr.length > 0 && $.inArray(val.zsid, zsidArr) === -1))) {
            zsidArr.push(val.zsid);
            if (val.zslx === 1) {
                zsZsidArr.push(val.zsid);
            }
            if (val.zslx === 2) {
                zmZsidArr.push(val.zsid);
            }
            if (val.zslx === 3) {
                zmdZsidArr.push(val.zsid);
            }
        }
    });
    if (zsidArr.length === 0) {
        warnMsg("没有可打印的证书信息！");
        return false;
    }
    var content = '<div id="bdc-popup-radio">\n' +
        '    <form class="layui-form" action="">\n' +
        '        <div class="layui-form-item pf-form-item">\n' +
        '            <div class="layui-inline">\n' +
        '                <label class="layui-form-label">打印：</label>\n' +
        '                <div class="layui-input-inline">\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </form>\n' +
        '</div>';
    // 每次只能打印一种类型的证书
    if ((zsZsidArr.length > 0 && zmZsidArr.length > 0) || (zsZsidArr.length > 0 && zmdZsidArr.length > 0) || (zmZsidArr.length > 0 && zmdZsidArr.length > 0)) {
        //单选框弹出层
        layer.open({
            title: '打印确认',
            type: 1,
            area: ['430px'],
            btn: ['继续', '取消'],
            content: content
            , success: function () {
                renderZslxRadio(zsZsidArr.length > 0, zmZsidArr.length > 0, zmdZsidArr.length > 0);
            }
            , yes: function (index, layero) {
                var zslx = $("input[name='zslxRadio']:checked").val();
                if (zslx == zsModel) {
                    batchZsPrint(zsModelUrl, zsZsidArr.join(","), "zs");
                } else if (zslx == zmModel) {
                    batchZsPrint(zmModelUrl, zmZsidArr.join(","), "zm");
                } else {
                    batchZsPrint(zmdModelUrl, zmdZsidArr.join(","), sczmdModel);
                }
            }
            , btn2: function (index, layero) {
                //取消 的回调
            }
            , cancel: function () {
                //右上角关闭回调
                //return false 开启该代码可禁止点击该按钮关闭
            }
        });
    } else {
        // 打印证书
        if (zsZsidArr.length > 0) {
            batchZsPrint(zsModelUrl, zsZsidArr.join(","), "zs");
        }
        // 打印证明
        if (zmZsidArr.length > 0) {
            batchZsPrint(zmModelUrl, zmZsidArr.join(","), "zm");
        }
        // 打印证明单
        if (zmdZsidArr.length > 0) {
            batchZsPrint(zmdModelUrl, zmdZsidArr.join(","), sczmdModel);
        }
    }*/
    return false;
}

// 加载单选选项
function renderZslxRadio(existZs, existZm, existZmd) {
    var radio = "";
    if (existZs) {
        radio += "<input type=\"radio\" name=\"zslxRadio\" value=\"1\" title=\"证书\" lay-filter=\"zslxRadio\" checked>";
        zslxChecked = zsModel;
    }
    if (existZm) {
        if (isNotBlank(radio)) {
            radio += "<input type=\"radio\" name=\"zslxRadio\" value=\"2\" title=\"证明\" lay-filter=\"zslxRadio\">";
        } else {
            radio += "<input type=\"radio\" name=\"zslxRadio\" value=\"2\" title=\"证明\" lay-filter=\"zslxRadio\" checked>";
            zslxChecked = zmModel;
        }
    }
    if (existZmd) {
        radio += "<input type=\"radio\" name=\"zslxRadio\" value=\"3\" title=\"证明单\" lay-filter=\"zslxRadio\">";
    }
    $('#bdc-popup-radio').find(".layui-input-inline").html(radio);
    form.render("radio");
}

/**
 * @param zsidList 需要打印的证书ID
 * @param modelUrl 打印模板地址
 * @param zslx 打印的证书类型
 * 证书批量打印
 */
function batchZsPrint(modelUrl, zsidList, zslx) {
    // 当参数过长时，fr3无法打印，先ajax post方式把参数保存的redis里面，返回key
    // 再根据key取出参数，在后台查询数据
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/print/batchzsprint?listZsidsStr=" + zsidList,
        type: "POST",
        contentType: 'application/json',
        dataType: "text",
        success: function (key) {
            if (key) {
                var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/batchZs/" + zslx + "/" + key;
                console.log('证书打印', dataUrl);
                var dylxArr = [zslx];
                getDylxDypz(dylxArr);
                bdPrintChoice(zslx, "realestate-inquiry-ui", dataUrl, modelUrl, false, zslx);
            } else {
                layer.msg('系统异常，请重试！');
            }
        }
    });
}

// 打印发证记录
function dyfzjl() {
    var gzlslidArr = jyxz(checkeddata);
    if (gzlslidArr && gzlslidArr.length > 0) {
        var gzlslids = gzlslidArr.join(',');
        var fzjlList = "fzjlList";
        var modelUrl = fzjlModelUrl;
        var dylxArr = ["fzjl"];
        getDylxDypz(dylxArr);
        var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/fzjl/xml/pl?dylx=" + fzjlList + "&gzlslids=" + gzlslids;
        bdPrintChoice("fzjl", "realestate-inquiry-ui", dataUrl, modelUrl, false, fzjlList);
    }

}

// 补打抵押物清单
function dydywqd() {
    var result = jydywxz(checkeddata);
    if (isNotBlank(result)) {
        var qllx = result.qllx;
        var gzlslidArr = result.gzlslidArr;
        if (gzlslidArr && gzlslidArr.length > 0) {
            var gzlslids = gzlslidArr.join(',');
            // 根据是否生成权利判断是否打印原项目的抵押信息
            var dylx = qllx === 37 ? "dyawqd" : "ygDyawqd";
            var sessionKey = dylx;
            // 可以保持打印模板一直，只要保证打印配置表中的modelxml的数据库名和模板中的数据集名一致即可
            var modelUrl = dyawqdModelUrl;
            var dylxArr = [dylx];
            getDylxDypz(dylxArr);
            var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/bdcdy/" + gzlslids + "/" + dylx + "/xml/pl";
            var appName = "realestate-inquiry-ui";
            bdPrintChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);
        }
    }
    // 禁止提交后刷新
    return false;
}

// 打印分层分户图printHst
function dyfcfht() {
    if (isNullOrEmpty(checkeddata)) {
        warnMsg('请选择需要打印的数据');
        return;
    }
    // 判断是否为同一流程
    var gzldyid = checkeddata[0].GZLDYID;
    for (var i = 0; i < checkeddata.length; i++) {
        if (gzldyid !== checkeddata[i].GZLDYID) {
            warnMsg('请选择相同流程进行打印');
            return;
        }
    }
    var zsidArr = [];
    for (let i = 0; i < checkeddata.length; i++) {
        if (zsidArr.indexOf(checkeddata[i].ZSID) === -1) {
            zsidArr.push(checkeddata[i].ZSID);
        }
    }
    if (isNullOrEmpty(zsidArr)) {
        warnMsg('选择数据中无分层分户图信息！');
        return;
    }
    var zsids = zsidArr.join(',');
    var sessionKey = "bdcZm";
    var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/zs/image/hst/" + zsids + "/pl";
    var appName = "realestate-inquiry-ui";
    var dylx = "hst";
    var dylxArr = [dylx];
    getDylxDypz(dylxArr);
    bdPrintChoice(dylx, appName, dataUrl, hstModelUrl, false, sessionKey);
}

// 打印收费单
function dysfd() {
    var gzlslidArr = jyxz(checkeddata);
    if (gzlslidArr && gzlslidArr.length > 0) {
        standardPrint('sfd', '', '1', gzlslidArr.join(','));
    }
}

//标准版收件单申请书打印逻辑
function standardPrint(dylx, xmid, qlrlb, gzlslids) {
    //统一从bdc_djxl_pz 表查询
    var bdcNewPrintDTO = queryPrintParam(dylx, xmid, qlrlb, gzlslids);
    var dylxArr = [bdcNewPrintDTO.dylx];
    //2. 把当前打印类型放到session
    var sessionKey = bdcNewPrintDTO.dylx;
    getDylxDypz(dylxArr);
    //3.调用打印方法
    console.log('标准打印入参', bdcNewPrintDTO);
    bdPrintChoice(bdcNewPrintDTO.dylx, bdcNewPrintDTO.appName, bdcNewPrintDTO.dataurl, bdcNewPrintDTO.modelurl, bdcNewPrintDTO.hidemodel, sessionKey, "accept")
}

// 查询打印参数
function queryPrintParam(dylx, xmid, qlrlb, gzlslids) {
    var url = getIP() + "/realestate-inquiry-ui";
    var result = {};
    getReturnData("/rest/v1.0/print/standard/pl", {
        gzlslids: gzlslids,
        xmid: xmid,
        dylx: dylx,
        url: url,
        zxlc: false,
        qlrlb: qlrlb
    }, "GET", function (data) {
        result = data;
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    return result;
}

/**
 * 获取打印类型配置信息
 * @param dylxArr
 */
function getDylxDypz(dylxArr) {
    // 当前页面打印配置信息
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/dypz/common/pzxx",
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify(dylxArr),
        async: false,
        success: function (data) {
            dypzMap = data;
        }, error: function (e) {
            delAjaxErrorMsg(e);
        }
    });
}

/**
 *
 * @param dylx 打印类型
 * @param appName
 * @param dataUrl 获取数据源地址
 * @param modelUrl
 * @param hiddeMode fr3打印是否隐藏控件打印预览
 * @param sessionKey
 * @param xmlSource
 */
function bdPrintChoice(dylx, appName, dataUrl, modelUrl, hiddeMode, sessionKey, xmlSource) {
    getPrintIp();
    console.log("sjdbd打印配置参数", dypzMap);
    console.log("传入xml数据源", dataUrl);
    console.log("printIp", printIp);
    if (dypzMap && !isEmptyObject(dypzMap) && dypzMap[dylx]) {
        var bdcDysjPzDO = dypzMap[dylx];
        var fr3Path = bdcDysjPzDO.fr3path;
        var pdfPath = bdcDysjPzDO.pdfpath;
        // 如果fr3打印路径配置了，则优先选择fr3打印
        if (fr3Path) {
            fr3Path = resolvePath(fr3Path);
            print(fr3Path, dataUrl, hiddeMode);
            return;
        } else if (pdfPath) {
            pdfPath = resolvePath(pdfPath);
            if (typeof printIp != "undefined" && printIp != "" && isNotBlank(dataUrl) && xmlSource !== "accept"){
                var ipAddrass = dataUrl.split("://")[1].split("/")[0];
                var tempUrl = dataUrl.split("://")[1].substring(ipAddrass.length, dataUrl.length - 1);
                dataUrl = printIp + tempUrl;
            }
            console.log("pdf实际xml数据源", dataUrl);
            //设置pdf打印参数
            var bdcPdfDyQO = {};
            bdcPdfDyQO.appName = appName;
            bdcPdfDyQO.dataUrl = dataUrl;
            bdcPdfDyQO.pdfpath = pdfPath;
            bdcPdfDyQO.fileName = sessionKey;
            ajaxPostPdfPrint(bdcPdfDyQO);
            return;
        }
    }
    // 兼容之前的打印配置
    print(modelUrl, dataUrl, hiddeMode);
}

function fjsc(){
    if (isNullOrEmpty(checkeddata)) {
        warnMsg('请选择数据');
        return;
    }
    // 判断是否为同一流程
    var slbh = checkeddata[0].SLBH;
    var gzlslid = checkeddata[0].GZLSLID;
    for (var i = 0; i < checkeddata.length; i++) {
        if (slbh !== checkeddata[i].SLBH) {
            warnMsg('请选择相同受理编号的数据');
            return;
        }
    }
    openWjsc(gzlslid);
}


//打开附件上传页面
function openWjsc(id){
    var sNodeFileCountSet;
    var bdcdjlx;
    var bdcSlWjscDTO = queryBdcSlWjscDTO('', bdcdjlx, id);
    var spaceId = id;

    bdcSlWjscDTO.sNodeFileCountSet = JSON.stringify(sNodeFileCountSet);
    bdcSlWjscDTO.spaceId = spaceId;
    bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
    // bdcSlWjscDTO.storageUrl = "http://192.168.2.99:8030/storage";
    var url = "/realestate-inquiry-ui/view/sjdbd/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
    openSjcl(url, "附件上传");

}

//查询收件材料所需参数（打开附件上传使用）
function queryBdcSlWjscDTO(clmc, djxl, processInsId) {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: getContextPath() +'/dtcx/bdcSlWjscDTO',
        type: 'GET',
        async: false,
        data: {processInsId: processInsId, clmc: clmc, djxl: djxl},
        success: function (data) {
            if (isNotBlank(data)) {
                bdcSlWjscDTO = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return bdcSlWjscDTO;
}