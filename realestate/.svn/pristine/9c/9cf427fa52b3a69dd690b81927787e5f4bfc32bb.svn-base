//打印登记数据如收件单申请书等

//该方法用于南通组合流程打印不同的模板,单位和个人的
function printDifModelData(dylx) {
    var dyaxmid = [];
    var cqxmid = [];
    qlxx.forEach(function (v) {
        if(v.dydj) {
            dyaxmid.push(v.bdcXm.xmid);
        } else {
            cqxmid.push(v.bdcXm.xmid);
        }
    });
    if(dylx === "dyasqspl" || dylx === "dyasqs" || dylx === "dyaxwblpl" || dylx === "dyaxwbl") {
        if(dyzl === "pdf") {
            startPrintPdf(processInsId,"",dylx,"all",dyaxmid);
        } else {
            startPrintToNt(processInsId,dyaxmid,dylx,"all");
        }
    } else {
        // if(dyzl === "pdf") {
        //     startPrintPdf(processInsId,"",dylx,"all",cqxmid);
        // } else {
        //     startPrintToNt(processInsId,cqxmid,dylx,"all");
        // }
        startNewPrintmode(dylx,"all",cqxmid[0]);
    }
}

function printData(dylx) {
    if(dyzl === "pdf") {
        startPrintPdf(processInsId,"",dylx,"all",[]);
    } else {
        startPrint(processInsId,"",dylx,"all");
    }
}

function printYcsl(dylx,xmid) {
    //根据jbxxid去获取xmid
    if(jbxxid != null &&!isNotBlank(xmid)) {
        getReturnData("/gwc/list/bdcslxm", {jbxxid: jbxxid}, 'GET', function (data) {
            if(isNotBlank(data)) {
                xmid = data[0].xmid;

            }
        }, function (err) {
            console.log(err);
        },false);
    }
    if(isNotBlank(xmid)) {
        if (dyzl === "pdf" || dylx === "ythclfht") {
            startPrintPdf(processInsId, xmid, dylx, "all", []);
        } else {
            startPrint(processInsId, xmid, dylx, "all");
        }
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
    if(dylx === "sjd" || dylx === "sjdpl" || dylx === "sqs" || dylx === "sqspl" || dylx === "ycsjd") {
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

//南通打印电子发票功能
function printDzfp() {
    queryJfsbm().done(function(data){
        addModel();
        // sessionId为随意指定的uuid内容
        var billFormElectInvoiceTool = BillFormElectInvoiceTool.getInstance({
            url: '127.0.0.1:31018', svrRandom: '', sessionId: processInsId
        });
        if(!billFormElectInvoiceTool.isConnect()){
            billFormElectInvoiceTool.connect();
        }
        $.each(data.jfxx, function(index, jfxx){
            var param = {
                jfsbm: jfxx.jfsbm,
                dh: jfxx.dh,
                userName : data.userName
            };
            if(jfxx.sfkp == "1"){
                downInvoice({billno: jfxx.jfsbm});
            }else{
                // 判断当前websocket连接状态
                if(billFormElectInvoiceTool.isConnect()){
                    // 请求获取CA序列号
                    getCA(billFormElectInvoiceTool, param);
                }else{
                    billFormElectInvoiceTool.onopen = function () {
                        getCA(billFormElectInvoiceTool, param);
                    };
                    billFormElectInvoiceTool.connect();
                }
            }
        });
    });
}

var serialNum = "";

function getCA(billFormElectInvoiceTool, param){
    if(isNotBlank(serialNum)){
        doAfterGetCA(billFormElectInvoiceTool, serialNum, param.jfsbm, param.dh);
    }else{
        // 请求获取CA序列号
        billFormElectInvoiceTool.takeCert(function(data){
            console.info("CA序列号："+ JSON.stringify(data));
            var cert = JSON.parse(data);
            if(cert["result"] == "failed"){
                removeModal();
                ityzl_SHOW_WARN_LAYER("请插入CA后，在进行发票打印。");
                return;
            }
            if(cert["IdeaBank cKey 0"].length == 0){
                removeModal();
                ityzl_SHOW_WARN_LAYER("未获取到CA序列号内容。");
                return;
            }
            serialNum =  cert["IdeaBank cKey 0"][0].Items.serialNumber;
            var ckeyUser = cert["IdeaBank cKey 0"][0].Items.CN;
            if(param.userName == ckeyUser){
                if(isNotBlank(serialNum)){
                    doAfterGetCA(billFormElectInvoiceTool, serialNum, param.jfsbm, param.dh);
                }else{
                    removeModal();
                    ityzl_SHOW_WARN_LAYER("未获取到CA序列号内容。");
                }
            }else{
                removeModal();
                ityzl_SHOW_WARN_LAYER("当前ca账户与登录账户不一致。");
            }
        });
    }
}
// 拥有证书号后的操作
function doAfterGetCA(billFormElectInvoiceTool, serialNum, jfsbm, mobile){
    getInvoiceInfo(jfsbm, serialNum, mobile).done(function (value) {
        // 对返回的票据信息进行签名加密
        billFormElectInvoiceTool.signData(value.invoiceData, function(signData){
            console.info("CA签名加密的invoiceData："+ JSON.stringify(signData));
            var signJson = JSON.parse(signData);
            if(isNotBlank(signJson.SignData)){
                value.signData = signJson.SignData;
                value.mobile = value.payerMobilePhone;
                value.billno = value.billNo;
                value.serialNumber = serialNum;
                // 生成电子发票
                generateInvoice(value).done(function(fpxx){
                    // 下载电子发票
                    removeModal();
                    downInvoice(value);
                });
            }else{
                removeModal();
                ityzl_SHOW_WARN_LAYER("未获取到签名信息。");
            }
        });
    });
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
// 获取票据信息
function getInvoiceInfo(jfsbm, caNo, mobile){
    var deferred = $.Deferred();
    $.ajax({
        url: getContextPath() + "/sf/getDzfpxx?beanName=invoiceBeforIssue",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({billno : jfsbm, serialNumber : caNo, mobile: mobile}),
        success: function (data) {
            if(isNotBlank(data) && isNotBlank(data.invoiceData)){
                deferred.resolve(data);
            }else{
                ityzl_SHOW_WARN_LAYER("未获取到电子票据信息。");
                deferred.reject();
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
            deferred.reject();
        }
    });
    return deferred;
}
// 生成票据
function generateInvoice(param){
    var deferred = $.Deferred();
    $.ajax({
        url: getContextPath() + "/sf/getDzfpxx?beanName=invoiceHandleIssue",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(param),
        success: function (data) {
            if(isNotBlank(data)){
                deferred.resolve(data);
            }else{
                ityzl_SHOW_WARN_LAYER("未获取到电子票据信息。");
                deferred.reject();
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
            deferred.reject();
        }
    });
    return deferred;
}
// 下载电子票据
function downInvoice(param){
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/getDzfpxx?beanName=invoiceDownload",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(param),
        success: function (data) {
            removeModal();
            if(isNotBlank(data) && isNotBlank(data.invoiceData)){
                showFile(data.invoiceData, data.invoiceCode);
            }else{
                ityzl_SHOW_WARN_LAYER("未获取到电子票据信息。");
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
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

// 通过websocket调用，获取CA序列号
var BillFormElectInvoiceTool = (function(){
    function Singleton(options) {
        options = options || {};
        this.name = "BillFormElectInvoiceTool";
        this.url = options.url || '';
        this.sessionId = options.sessionId || '';
        this.socket = options.socket;
        this.isReconnect = false;
        this.messageSession = {};
    }

    Singleton.prototype.connect = function() {
        if (this.socket !== undefined) {
            return this;
        }
        var protocol = (window.location.protocol == 'http:') ? 'ws://' : 'ws://';
        this.host = protocol + this.url;

        window.WebSocket = window.WebSocket || window.MozWebSocket;
        if(!window.WebSocket) { // 检测浏览器支持
            this.error('浏览器不支持WebSocket，请使用谷歌4.0以上的浏览器');
            return;
        }
        var that = this;
        try {
            this.socket = new WebSocket(this.host); // 创建连接并注册响应函数
            this.socket.onopen = function(e) {
                that.onopen(e);
            };
            this.socket.onmessage = function(e) {
                that.onmessage(e);
            };
            this.socket.onclose = function(e) {
                that.onclose(e);
                that.socket = null; // 清理
            };
            this.socket.onerror = function(e) {
                that.onerror(e);
            };
            return this;
        } catch (e) {
            this.error('连接WebSocket失败!')
        }
    };

    Singleton.prototype.onopen = function (e) {};

    Singleton.prototype.onmessage = function (message) {
        var callback = this.messageSession[this.sessionId];
        if (callback) {
            callback(message.data);
        }
    };

    Singleton.prototype.onclose = function (e) {
        if(this.socket !== undefined && this.socket!= null) {
            this.socket.close();
        } else {
            this.error("WebSocket链接已失效");
        }
    };

    Singleton.prototype.onerror = function (e) {
        removeModal();
        console.log(e);
        ityzl_SHOW_WARN_LAYER("连接Websocket服务器异常");
    };

    Singleton.prototype.error = function (errorMsg) {
        this.onerror(errorMsg);
    };

    Singleton.prototype.isConnect = function () {
        return this.socket && this.socket.readyState === 1;
    };

    Singleton.prototype.reconnect = function () {
        if (this.isReconnect) {
            return;
        }
        this.isReconnect = true;
        var that = this;
        setTimeout(function() {
            that.connect();
            that.isReconnect = false;
        }, 2000)
    };

    Singleton.prototype.send = function (message) {
        if (this.socket.readyState !== 1) {
            this.reconnect();
        }
        if(this.socket.readyState === 1) {
            this.socket.send(message);
            return true;
        }
        this.error('请先开启Websocket连接服务器');
        return false;
    };

    Singleton.prototype.signData = function (message, callback) {
        var data = '{"FuncName":"SignData_P7","Parames" :{"SignAlgType":"", "OriginalData":"'+message+'","OriginalDataType":""}}';
        this.send(data);
        this.messageSession[this.sessionId] = callback;
    };

    Singleton.prototype.takeCert = function (callback) {
        this.send('{"FuncName":"GetKeyCert"}');
        this.messageSession[this.sessionId] = callback;
    };

    var instance;
    return {
        name: "BillFormElectInvoiceTool",
        getInstance: function (options) {
            if (instance === undefined) {
                instance = new Singleton(options);
            } else {
                instance.connect(options);
            }
            return instance;
        }
    };
})();