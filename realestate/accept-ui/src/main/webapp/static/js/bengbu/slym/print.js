//打印登记数据如收件单申请书等

function printData(dylx,sfzhlc) {
    /**
     * @param dylx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 全部采用新的打印方式
     * @date : 2020/4/24 10:14
     */

    // 税费通知单需要保存登记费用先
    // added by cyc at 2020-3-5
    if(dylx === "ycsftzd" ) {
        var url =getContextPath() + "/sf/xx/new";
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            data: {xmid: xmid, processInsId: processInsId},
            async: false,
            success: function (data) {
                yxloadSfxm(data.bdcSlQlrSfxxDTO.sfxxid,"1",xmid,processInsId);
                yxloadSfxm(data.bdcSlYwrSfxxDTO.sfxxid,"2",xmid,processInsId);
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });

    }
    if (dylx === "sqs" || dylx === "sqspl" || dylx === "dyasqs" || dylx === "dyasqspl") {
        var dyaxmid = [];
        var cqxmid = [];
        // 默认产权xmid
        var mrcqxmid = "";
        for (var i = 0; i < qlxxForPrint.length; i++) {
            if (qlxxForPrint[i].dydj) {
                if (!sfzhlc) {
                    if (dylx === "sqs") {
                        dylx = "dyasqs";
                    } else {
                        dylx = "dyasqspl";
                    }
                }
                dyaxmid.push(qlxxForPrint[i].bdcXm.xmid);
            } else {
                // 优先取qllx=4，国有建设用地使用权/房屋所有权的xmid
                if (qlxxForPrint[i].bdcXm.qllx === 4 && isNullOrEmpty(mrcqxmid)) {
                    mrcqxmid = qlxxForPrint[i].bdcXm.xmid;
                }
                cqxmid.push(qlxxForPrint[i].bdcXm.xmid);
            }
        }
        if (dylx === "sqs" || dylx === "sqspl") {
            if (isNullOrEmpty(mrcqxmid)) {
                startNewPrintmode(dylx, "all", cqxmid[0]);
            } else {
                startNewPrintmode(dylx, "all", mrcqxmid);
            }
        }
        if (dylx === "dyasqs" || dylx === "dyasqspl") {
            startNewPrintmode(dylx, "all", dyaxmid[0]);
        }
        return;
    }
    startNewPrintmode(dylx,"all","");
}

function printYcsl(dylx,xmid) {
    //一窗申请单要求子表嵌套子表只能用pdf打印
    if(dyzl === "pdf" || dylx === "ycslgrsqd" || dylx === "ycsldwsqd" || dylx === "rzdb") {
        satrtPrintPdf(processInsId,xmid,dylx,"all");
    } else {
        startPrint(processInsId,xmid,dylx,"all");
    }
}

function printSfd(dylx,qlrlb) {
    /**
     * @param dylx,qlrlb
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 开启新的打印模式
     * @date : 2020/4/24 10:15
     */
    startNewPrintmode(dylx,qlrlb,xmid);
}

// 一窗受理申请单打印入口。先判断权利人类型，在进行打印
function printYcslsqd(){
    //存在单个和组合的情况，当未获取到当前tab页面上的xmid，则取默认的xmid信息
    var tabXmid = $(".layui-this").find("input[name='xmid']").val();
    if(!isNotBlank(tabXmid)){
        tabXmid = xmid;
    }
    $.ajax({
        url: getContextPath() + "/slym/sqr/sqrxx",
        type: 'GET',
        dataType: 'json',
        contentType: "application/json",
        data: {
            xmid : tabXmid,
            sqrlb : "1"
        },
        success: function (data) {
            if(data.length == 0){
                delAjaxErrorMsg("未获取到申请人信息。")
            }else{
                if(data[0].sqrlx == 1) {
                    // 打印个人申请单
                    printYcsl("ycslgrsqd",tabXmid);
                }else{
                    // 打印单位申请单
                    printYcsl("ycsldwsqd",tabXmid);
                }
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

}

function startPrint(processInsId, xmid, dylx, qlrlb) {
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
    // 税费通知单需要保存登记费用先
    // added by cyc at 2020-3-5
    if(dylx === "ycsftzd" ) {
        var url =getContextPath() + "/sf/xx/new";
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            data: {xmid: xmid, processInsId: processInsId},
            async: false,
            success: function (data) {
                yxloadSfxm(data.bdcSlQlrSfxxDTO.sfxxid,"1",xmid,processInsId);
                yxloadSfxm(data.bdcSlYwrSfxxDTO.sfxxid,"2",xmid,processInsId);
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });

    }

    console.log(JSON.stringify(sjclidList));
    var url = getIP() + "/realestate-accept-ui";
    $.ajax({
        url: getContextPath() + "/slPrint/hf",
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

/**
 * 打印税费通知单前预更新收费信息表
 * @param sfxxid
 * @param qlrlb
 */
function yxloadSfxm(sfxxid,qlrlb,xmid,processInsId){
    $.ajax({
        url: getContextPath() + "/sf/xm",
        type: 'get',
        dataType: 'json',
        async:false,
        data: {sfxxid: sfxxid, gzlslid:processInsId, xmid:xmid, qlrlb:qlrlb},
        success: function (data) {
            //console.log("预更新收费信息成功！")
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function satrtPrintPdf(processInsId, xmid, dylx, qlrlb) {
    addModel();
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
    var pdfUrl = getContextPath() + "/slPrint/pdf?gzlslid=" + processInsId + "&xmid=" + xmid + "&dylx=" + dylx + "&zxlc=" + zxlc + "&qlrlb=" + qlrlb + "&sjclids=" + sjclidList.join();
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