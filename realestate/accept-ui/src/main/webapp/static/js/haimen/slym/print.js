//打印登记数据如收件单申请书等

//该方法用于南通组合流程打印不同的模板,单位和个人的
function printDifModelData(dylx,lclx) {
    var dyaxmid = [];
    var cqxmid = [];
    //批量
    if (lclx==="pllc") {
        // 批量流程的没有qlxx，qlxxdata是json
        if (qlxxdata.dydj) {
            dyaxmid.push(qlxxdata.bdcXm.xmid);
        } else {
            cqxmid.push(qlxxdata.bdcXm.xmid);
        }
    }else {
        // 其他
        qlxx.forEach(function (v) {
            if (v.dydj) {
                dyaxmid.push(v.bdcXm.xmid);
            } else {
                cqxmid.push(v.bdcXm.xmid);
            }
        });
    }

    if(dylx === "dyasqspl" || dylx === "dyasqs" || dylx === "dyaxwblpl" || dylx === "dyaxwbl") {
        if(dyzl === "pdf") {
            startPrintPdf(processInsId,"",dylx,"all",dyaxmid);
        } else {
            startPrintToNt(processInsId,dyaxmid,dylx,"all");
        }
    } else {
        startNewPrintmode(dylx,"all",cqxmid[0]);
    }
}

function printData(dylx) {
    // if(dyzl === "pdf") {
    //     startPrintPdf(processInsId,"",dylx,"all",[]);
    // } else {
    //     startPrint(processInsId,"",dylx,"all");
    // }
    startNewPrintmode(dylx,"all","");
}

function printYcsl(dylx) {
    //根据jbxxid去获取xmid
    if(jbxxid != null) {
        getReturnData("/gwc/list/bdcslxm", {jbxxid: jbxxid}, 'GET', function (data) {
            if(isNotBlank(data)) {
                var xmid = data[0].xmid;
                if(dyzl === "pdf") {
                    startPrintPdf(processInsId,xmid,dylx,"all",[]);
                } else {
                    startPrint(processInsId,xmid,dylx,"all");
                }
            }
        }, function (err) {
            console.log(err);
        },false);
    }
}

function printSfd(dylx,qlrlb) {
    if(dylx === "hmsflxd") {
        //海门收费单打印时更新收费状态
        getReturnData("/sf/xx/sfzt",{gzlslid:processInsId},"GET",function (data) {
            if(data && data>0) {
                console.log("更新收费状态成功");
            }
        },function (xhr) {
            delAjaxErrorMsg(xhr);
        })
    }
    startNewPrintmode(dylx,qlrlb,xmid);
}

function startPrint(processInsId, xmid, dylx, qlrlb) {
    var sjclidList = [];
    if(dylx === "sjd" || dylx === "sjdpl" || dylx === "sqs" || dylx === "sqspl") {
        if(sjclids.size === 0 ) {
            sjclidList = sjclidLists;
        } else {
            sjclids.forEach(function (element, sameElement, set) {
                sjclidList.push(sameElement);
            });
        }
    }
    console.log(JSON.stringify(sjclidList));
    var url = getIP() + "/realestate-accept-ui";
    $.ajax({
        url: getContextPath() + "/slPrint",
        type: "GET",
        // dataType: 'json',
        data: {gzlslid: processInsId, xmid: xmid, dylx: dylx, url: url,zxlc:zxlc, qlrlb:qlrlb,sjclids:sjclidList.join()},
        success: function (data) {
            if (data !== null && data !== '') {
                window.location.href = "eprt://" + data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function startPrintToNt(processInsId, xmids, dylx, qlrlb) {
    var url = getIP() + "/realestate-accept-ui";
    $.ajax({
        url: getContextPath() + "/slPrint",
        type: "GET",
        // dataType: 'json',
        data: {gzlslid: processInsId, dylx: dylx, url: url,zxlc:zxlc, qlrlb:qlrlb,xmids:xmids.join()},
        success: function (data) {
            if (data !== null && data !== '') {
                window.location.href = "eprt://" + data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function startPrintPdf(processInsId, xmid, dylx, qlrlb, xmids) {
    addModel();
    var sjclidList = [];
    var pdfUrl = getContextPath() + "/slPrint/pdf?gzlslid=" + processInsId + "&xmid=" + xmid + "&dylx="
        + dylx + "&zxlc=" + zxlc + "&qlrlb=" + qlrlb + "&sjclids=" + sjclidList.join() + "&xmids=" + xmids.join();
    removeModal();
    window.open('/realestate-accept-ui/static/lib/pdf/web/viewer.html?file=' + encodeURIComponent(pdfUrl),"PDF");
}

function generatePrint(dylx,xmid,qlrlb) {
    var url = getIP() + "/realestate-accept-ui";
    var result = {};
    var sjclidList = [];
    if(dylx === "sjd" || dylx === "sjdpl" || dylx === "sqs" || dylx === "sqspl" || dylx === "ycsjd") {
        if(sjclids.size === 0 ) {
            sjclidList = sjclidLists;
        } else {
            sjclids.forEach(function (element, sameElement, set) {
                sjclidList.push(sameElement);
            });
        }
    }
    getReturnData("/slPrint/newmode",{gzlslid: processInsId, xmid: xmid, dylx: dylx,url:url, zxlc:zxlc, qlrlb:qlrlb,sjclids:sjclidList.join()},"GET",function (data) {
        result = data;
    },function (xhr) {
        delAjaxErrorMsg(xhr);
    },false);
    return result;
}

function printXwbl(dylx) {
    startNewPrintmode(dylx,"all","");
}

function startNewPrintmode(dylx,qlrlb,xmid) {
    //1.根据前台传参，后台封装打印类型，打印需要的xml生成地址，是否静默打印hidemodel，接口app的name
    var bdcNewPrintDTO = generatePrint(dylx,xmid,qlrlb);
    var dylxArr = [bdcNewPrintDTO.dylx];
    //2. 把当前打印类型放到session
    var sessionKey = bdcNewPrintDTO.dylx;
    setDypzSession(dylxArr,sessionKey);
    //3.调用打印方法
    printChoice(bdcNewPrintDTO.dylx, bdcNewPrintDTO.appName, bdcNewPrintDTO.dataurl, bdcNewPrintDTO.modelurl, bdcNewPrintDTO.hidemodel, sessionKey)
}

// 获取缴费书编码
function queryJfsbm(){
    var deferred = $.Deferred();
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/queryBdcSfxx",
        type: "GET",
        data: {gzlslid: processInsId},
        success: function (data) {
            removeModal();
            console.log(data);
            if(data.result){
                deferred.resolve(data);
            }else{
                ityzl_SHOW_WARN_LAYER(data.errorMsg);
                deferred.reject();
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
            deferred.reject();
        }
    });
    return deferred;
}

//南通打印电子发票功能
function printDzfp() {
    queryJfsbm().done(function(data){
        addModel();
        var sftsfsxt =false;
        getReturnData("/sf/nantong/sfmode",{gzlslid: processInsId, configName: "fs.config.dzfp.beanName"},"GET",function (data){
            sftsfsxt =data;
        },function (error){
            delAjaxErrorMsg(error);
            return false;
        },false);

        if(sftsfsxt){
            $.each(data.jfxx, function (index, jfxx) {
                downloadDzfp(jfxx.sfxxid);
            });
        }else{
            warnMsg("未配置非税下载电子发票信息接口");
        }
    });
}

// 下载电子票据
function downloadDzfp(param){
    $.ajax({
        url: getContextPath() + "/sf/printDzfpxx",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        data: param,
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                console.log(data);
                showFile(data.base64, "");
            }else{
                ityzl_SHOW_WARN_LAYER("未获取到电子票据信息。");
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
            return false;
        }
    });
}

function showFile(data, code) {
    var dzhtnr = data.replace(/\s*/g,""),
        fileName = "",
        mime = "application/pdf";
    if(isNotBlank(code)){
        fileName = code+".pdf";
    }else{
        fileName = "电子发票.pdf";
    }
    var byte = base64ToByte(dzhtnr); // base64编码字符串转换二进制流
    if(isIE()){ //修复IE10无法使用Blob进行文件下载
        window.navigator.msSaveOrOpenBlob(new Blob([byte], { type: mime }),fileName);
    }else{
        var reader = new FileReader();
        var blob = new Blob([byte]);
        reader.readAsDataURL(blob);
        reader.onload = function (e) {
            // 转换完成，创建一个a标签用于下载
            var a = document.createElement('a');
            a.download = fileName;
            a.href = e.target.result;
            $("body").append(a);    // 修复firefox中无法触发click
            a.click();
            $(a).remove();
        }
    }
}
//判断是否IE浏览器
function isIE() {
    if (!!window.ActiveXObject || "ActiveXObject" in window) {
        return true;
    } else {
        return false;
    }
}

// base64解码转为二进制流
function base64ToByte(base64Str) {
    var decodeStr = atob(base64Str);
    var len = decodeStr.length;
    var byte = new Uint8Array(len);
    while(len--){
        byte[len] = decodeStr.charCodeAt(len);
    }
    return byte;
}
