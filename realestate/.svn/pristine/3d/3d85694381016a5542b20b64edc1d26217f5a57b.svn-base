var HidHelperUtils;
var log;
var isInit = false;

function init() {
    if (!isInit) {

        isInit = true;
        HidHelperUtils = document.getElementById('myPad');
        log = document.getElementById('log');
        if (HidHelperUtils && log) {
            HidHelperUtils.attachEvent("OnEvaluateCompleted", OnEvaluateCompleted);
            HidHelperUtils.attachEvent("OnSignCompleted", OnSignCompleted);
            HidHelperUtils.attachEvent("OnFaceValidateWithIdCardCompleted", OnFaceValidateWithIdCardCompleted);
            HidHelperUtils.attachEvent("OnInteractionCompleted", OnInteractionCompleted);
            HidHelperUtils.attachEvent("GetIdCardCompleted", GetIdCardCompleted);
            HidHelperUtils.attachEvent("SignDeclarationCompleted", SignDeclarationCompleted);
            return true;
        } else {
            return false;
        }
    }
}

function Log(operation, value) {
    var message = "";
    switch (value) {
        case 0:
            message = "成功";
            break;
        case -1:
            message = "信道错误,请重新开启服务";
            break;
        case -2:
            message = "设备执行失败";
            break;
        case -3:
            message = "设备响应报文长度错误";
            break;
        case -4:
            message = "软件卸载失败";
            break;
        case -5:
            message = "指令执行超时";
            break;
        case -6:
            message = "用户撤消";
            break;
        case -60:
            message = "http下载失败";
            break;
        case -80:
            message = "无需升级";
            break;
        case -81:
            message = "Apk验证失败";
            break;
        case 1:
            message = "内部错误";
            break;
        case 2:
            message = "设备不在线";
            break;
        case 3:
            message = "参数错误";
            break;
        case 4:
            message = "软件没有运行";
            break;
        case 9:
            message = "设备未授权";
            break;
        case 10:
            message = "文件不存在";
            break;
        default:
            message = value;
            break
    }
    log.value = log.value + "\r\n" + operation + ":" + message;
}

function LogText(operation, message) {
    log.value = log.value + "\r\n" + operation + ":" + message;
}

function Start() {
    if (init()) {
        var result = HidHelperUtils.Start();
        // Log("Start", result);
    }
}

function OneMeter() {
    var result = HidHelperUtils.OneMeter();
    Log("OneMeter", result);
}

function Welcome() {
    var result = HidHelperUtils.Welcome();
    // Log("Welcome", result);
}

function Login() {
    var tbLoginName = document.getElementById("tbLoginName");
    var name = tbLoginName.value;
    if (name == "") {
        LogText("Login", "请输入姓名");
        return;
    }

    var tbLoginNo = document.getElementById("tbLoginNo");
    var no = tbLoginNo.value;
    if (no == "") {
        LogText("Login", "请输入工号");
        return;
    }

    var tbLoginPlaceName = document.getElementById("tbLoginPlaceName");
    var placename = tbLoginPlaceName.value;
    if (placename == "") {
        LogText("Login", "请输入机构名称");
        return;
    }

    var tbLoginPlaceNo = document.getElementById("tbLoginPlaceNo");
    var placeNo = tbLoginPlaceNo.value;
    if (placeNo == "") {
        LogText("Login", "请输入机构号");
        return;
    }

    var lbLoginLevel = document.getElementById("lbLoginLevel");
    var level = Number(lbLoginLevel.value);
    if (level < 0 || level > 5) {
        LogText("Login", "请输入正确的星级");
        return;
    }

    var tbLoginMotto = document.getElementById("tbLoginMotto");
    var motto = tbLoginMotto.value;

    var numLoginPicPlayTime = document.getElementById("numLoginPicPlayTime");
    var playtime = Number(numLoginPicPlayTime.value);
    if (playtime < 0 || playtime > 99) {
        LogText("Login", "请输入正确的播放时间");
        return;
    }

    var result = HidHelperUtils.Login(name, no, placename, placeNo, level, motto, playtime);
    Log("Login", result);
    Log("Login", "Login(\"" + name + "\", \"" + no + "\", \"" + placename + "\", \"" + placeNo + "\", " + level + ", \"" + motto + "\")");
}


function LoginWithPhoto() {
    var tbLoginName = document.getElementById("tbLoginName");
    var name = tbLoginName.value;
    if (name == "") {
        LogText("Login", "请输入姓名");
        return;
    }

    var tbLoginNo = document.getElementById("tbLoginNo");
    var no = tbLoginNo.value;
    if (no == "") {
        LogText("Login", "请输入工号");
        return;
    }

    var tbLoginPlaceName = document.getElementById("tbLoginPlaceName");
    var placename = tbLoginPlaceName.value;
    if (placename == "") {
        LogText("Login", "请输入机构名称");
        return;
    }

    var tbLoginPlaceNo = document.getElementById("tbLoginPlaceNo");
    var placeNo = tbLoginPlaceNo.value;
    if (placeNo == "") {
        LogText("Login", "请输入机构号");
        return;
    }

    var lbLoginLevel = document.getElementById("lbLoginLevel");
    var level = Number(lbLoginLevel.value);
    if (level < 0 || level > 5) {
        LogText("Login", "请输入正确的星级");
        return;
    }

    var tbLoginMotto = document.getElementById("tbLoginMotto");
    var motto = tbLoginMotto.value;

    var numLoginPicPlayTime = document.getElementById("numLoginPicPlayTime");
    var playtime = Number(numLoginPicPlayTime.value);
    if (playtime < 0 || playtime > 99) {
        LogText("Login", "请输入正确的播放时间");
        return;
    }

    var tbUserLoginPhoto = document.getElementById("tbUserLoginPhoto");
    var photoData = tbUserLoginPhoto.value;
    if (photoData == "") {
        LogText("LoginWithPhoto", "请输入Base64照片数据");
    }

    var result = HidHelperUtils.LoginWithPhoto(name, no, placename, placeNo, level, motto, playtime, photoData);
    Log("LoginWithPhoto", result);
    Log("LoginWithPhoto", "LoginWithPhoto(\"" + name + "\", \"" + no + "\", \"" + placename + "\", \"" + placeNo + "\", " + level + ", \"" + motto + "\"," + playtime + "\"," + photoData + "\"" + ")");
}

function Logout() {
    var result = HidHelperUtils.Logout();
    Log("Logout", result);
}

function StopService() {
    var result = HidHelperUtils.StopService();
    Log("StopService", result);
}

function RestoreService() {
    var result = HidHelperUtils.RestoreService();
    Log("RestoreService", result);
}

function Evaluate() {

    var key = "3";

    var numEvaluateTimeout = document.getElementById("numEvaluateTimeout");
    var timeout = Number(numEvaluateTimeout.value);

    var result = HidHelperUtils.Evaluate(key, timeout);
    Log("Evaluate", result);
    var evaluateValue = HidHelperUtils.GetEvaluateValue();
    OnEvaluateCompleted(result, evaluateValue);
}

//异步评价
function EvaluateAsync() {
    var lbEvaluateButton = document.getElementById("lbEvaluateButton");
    var key = lbEvaluateButton.value;

    var numEvaluateTimeout = document.getElementById("numEvaluateTimeout");
    var timeout = Number(numEvaluateTimeout.value);

    var tbRunningNumberEvaluateAsync = document.getElementById("tbRunningNumberEvaluateAsync");
    var runningNumber = tbRunningNumberEvaluateAsync.value;

    var result = HidHelperUtils.EvaluateAsync(key, timeout, runningNumber);
    Log("EvaluateAsync", result);
}

function GetEvaluateAsyncResult() {
    var evaluateValue = HidHelperUtils.GetEvaluateValue();
    var evaluateValueRunningNumber = HidHelperUtils.GetEvaluateValueRunningNumber();
    OnEvaluateCompleted(0, evaluateValue);
    LogText("流水号2|评价值", evaluateValueRunningNumber);
}

function CancelEvaluate() {
    var result = HidHelperUtils.CancelEvaluate();
    Log("CancelEvaluate", result);
}

//异步评价回调
function OnEvaluateCompleted(status, evaluateValue, runningNumber) {
    Log("OnEvaluateCompleted", status);
    LogText("evaluateValue", " 评价结果1：" + evaluateValue);
    LogText("runningNumber", " 流水号：" + runningNumber);
}


function Install() {
    var pathTextBox = document.getElementById("tbInstall");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.Install(path);

        Log("Install", result);
    } else {
        LogText("Install", "请输入APK路径");
    }
}

function UnInstall() {
    var result = HidHelperUtils.UnInstall();
    Log("UnInstall", result);
}

function Reboot() {
    var result = HidHelperUtils.Reboot();
    Log("Reboot", result);
}

function IsDeviceOnLine() {
    var result = HidHelperUtils.IsDeviceOnLine();
    Log("IsDeviceOnLine", result);
}

function PlaySound() {
    var pathTextBox = document.getElementById("tbPlaySound");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.PlaySound(path);
        Log("PlaySound", result);
    } else {
        LogText("PlaySound", "请输入文件名");
    }
}

function MargueeNotice() {
    var noticeTextBox = document.getElementById("tbMargueeNotice");
    if (noticeTextBox) {
        var noticeText = noticeTextBox.value;
        var result = HidHelperUtils.MargueeNotice(noticeText);
        Log("MargueeNotice", result);
    } else {
        LogText("MargueeNotice", "输入控件不存在");
    }
}

function PDFInteractive() {
    var pathTextBox = document.getElementById("tbPDFInteractive");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var numPDFInteractiveTimeout = document.getElementById("numPDFInteractiveTimeout");
        var timeout = Number(numPDFInteractiveTimeout.value);
        var result = HidHelperUtils.PDFInteractive(timeout, path);
        Log("PDFInteractive", result);
        var interactionResult = HidHelperUtils.GetInteractiveResult();
        OnInteractionCompleted(result, interactionResult);
    } else {
        LogText("PDFInteractive", "请输入路径");
    }
}

function PDFInteractiveAsync() {
    var pathTextBox = document.getElementById("tbPDFInteractive");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var numPDFInteractiveTimeout = document.getElementById("numPDFInteractiveTimeout");
        var timeout = Number(numPDFInteractiveTimeout.value);
        var tbRunningNumberPDFInteractiveAsync = document.getElementById("tbRunningNumberPDFInteractiveAsync");
        var runningNumber = tbRunningNumberPDFInteractiveAsync.value;
        var result = HidHelperUtils.PDFInteractiveAsync(timeout, path, runningNumber);
        Log("PDFInteractiveAsync", result);
    } else {
        LogText("PDFInteractiveAsync", "请输入路径");
    }
}

function GetPDFInteractiveAsyncResult() {
    var interactionResult = HidHelperUtils.GetInteractiveResult();
    LogText("GetInteractiveResult", " 按钮值" + interactionResult);
    var interactionResultRunningNumber = HidHelperUtils.GetInteractiveResultRunningNumber();
    LogText("流水号|按钮值", interactionResultRunningNumber);
}

function OnInteractionCompleted(status, interactionResult, runningNumber) {
    Log("OnInteractionCompleted", status);
    LogText("OnInteractionCompleted", " 按钮值：" + interactionResult);
    LogText("OnInteractionCompleted", " 流水号：" + runningNumber);
}


function Cancel() {
    var result = HidHelperUtils.Cancel();
    Log("Cancel", result);
}


function ReturnToMain() {
    var result = HidHelperUtils.ReturnToMain();
    Log("ReturnToMain", result);
}

function InputVerifyCode() {
    var numInputVerifyCodeTimeout = document.getElementById("numInputVerifyCodeTimeout");
    var numInputVerifyCodeMin = document.getElementById("numInputVerifyCodeMin");
    var numInputVerifyCodeMax = document.getElementById("numInputVerifyCodeMax");

    var timeout = Number(numInputVerifyCodeTimeout.value);
    var minlen = Number(numInputVerifyCodeMin.value);
    var maxlen = Number(numInputVerifyCodeMax.value);

    var result = HidHelperUtils.InputVerifyCode(timeout, minlen, maxlen);
    Log("InputVerifyCode", result);
    var interactionResult = HidHelperUtils.GetInteractiveResult();
    var text = HidHelperUtils.GetInteractiveText();
    OnInputTextInteractionCompleted(interactionResult, text);
}

function InputVerifyCodeAsync() {
    var numInputVerifyCodeTimeout = document.getElementById("numInputVerifyCodeTimeout");
    var numInputVerifyCodeMin = document.getElementById("numInputVerifyCodeMin");
    var numInputVerifyCodeMax = document.getElementById("numInputVerifyCodeMax");

    var timeout = Number(numInputVerifyCodeTimeout.value);
    var minlen = Number(numInputVerifyCodeMin.value);
    var maxlen = Number(numInputVerifyCodeMax.value);

    var tbRunningNumberInputVerifyCodeAsync = document.getElementById("tbRunningNumberInputVerifyCodeAsync");
    var runningNumber = tbRunningNumberInputVerifyCodeAsync.value;

    var result = HidHelperUtils.InputVerifyCodeAsync(timeout, minlen, maxlen, runningNumber);
    Log("InputVerifyCodeAsync", result);
    //var interactionResult = HidHelperUtils.GetInteractiveResult();
    //var text = HidHelperUtils.GetInteractiveText();
    //OnInputTextInteractionCompleted(interactionResult, text);
}

function GetInputInteractiveAsyncResult() {
    var interactionResult = HidHelperUtils.GetInteractiveResult();
    LogText("GetInteractiveResult", " 按钮值" + interactionResult);
    var interactionResultRunningNumber = HidHelperUtils.GetInteractiveResultRunningNumber();
    LogText("流水号|按钮值", interactionResultRunningNumber);
    var text = HidHelperUtils.GetInteractiveText();
    LogText("输入内容", text);
}


function InputPassword() {
    var numInputPasswordTimeout = document.getElementById("numInputPasswordTimeout");
    var numInputPasswordMin = document.getElementById("numInputPasswordMin");
    var numInputPasswordMax = document.getElementById("numInputPasswordMax");

    var timeout = Number(numInputPasswordTimeout.value);
    var minlen = Number(numInputPasswordMin.value);
    var maxlen = Number(numInputPasswordMax.value);

    var result = HidHelperUtils.InputPassword(timeout, minlen, maxlen);
    Log("InputPassword", result);
    var interactionResult = HidHelperUtils.GetInteractiveResult();
    var text = HidHelperUtils.GetInteractiveText();
    OnInputTextInteractionCompleted(interactionResult, text);
}

function InputPasswordAsync() {
    var numInputPasswordTimeout = document.getElementById("numInputPasswordTimeout");
    var numInputPasswordMin = document.getElementById("numInputPasswordMin");
    var numInputPasswordMax = document.getElementById("numInputPasswordMax");

    var timeout = Number(numInputPasswordTimeout.value);
    var minlen = Number(numInputPasswordMin.value);
    var maxlen = Number(numInputPasswordMax.value);

    var tbRunningNumberInputPasswordAsync = document.getElementById("tbRunningNumberInputPasswordAsync");
    var runningNumber = tbRunningNumberInputPasswordAsync.value;

    var result = HidHelperUtils.InputPasswordAsync(timeout, minlen, maxlen, runningNumber);
    Log("InputPasswordAsync", result);
    //var interactionResult = HidHelperUtils.GetInteractiveResult();
    //var text = HidHelperUtils.GetInteractiveText();
    //OnInputTextInteractionCompleted(interactionResult, text);
}

function InputMobilePhone() {
    var numInputMobilePhoneTimeout = document.getElementById("numInputMobilePhoneTimeout");
    var timeout = Number(numInputMobilePhoneTimeout.value);

    var result = HidHelperUtils.InputMobilePhone(timeout);
    Log("InputMobilePhone", result);
    var interactionResult = HidHelperUtils.GetInteractiveResult();
    var text = HidHelperUtils.GetInteractiveText();
    OnInputTextInteractionCompleted(interactionResult, text);
}

function InputMobilePhoneAsync() {
    var numInputMobilePhoneTimeout = document.getElementById("numInputMobilePhoneTimeout");
    var timeout = Number(numInputMobilePhoneTimeout.value);


    var tbRunningNumberInputMobilePhoneAsync = document.getElementById("tbRunningNumberInputMobilePhoneAsync");
    var runningNumber = tbRunningNumberInputMobilePhoneAsync.value;

    var result = HidHelperUtils.InputMobilePhoneAsync(timeout, runningNumber);
    Log("InputMobilePhoneAsync", result);
    //var interactionResult = HidHelperUtils.GetInteractiveResult();
    //var text = HidHelperUtils.GetInteractiveText();
    //OnInputTextInteractionCompleted(interactionResult, text);
}

function OnInputTextInteractionCompleted(status, text) {
    LogText("OnInputTextInteractionCompleted", " 按钮值：" + status);
    LogText("OnInputTextInteractionCompleted", " 结果：" + text);
}

function GetDeviceInfo() {
    var result = HidHelperUtils.GetDeviceInfo("");
    Log("GetDeviceInfo", result);
}

function PushSingleUserImage() {
    var tbPushSingleUserImagePath = document.getElementById("tbPushSingleUserImagePath");
    var tbPushSingleUserImageNo = document.getElementById("tbPushSingleUserImageNo");
    if (tbPushSingleUserImagePath && tbPushSingleUserImagePath.value && tbPushSingleUserImageNo && tbPushSingleUserImageNo.value) {
        var path = tbPushSingleUserImagePath.value;
        var no = tbPushSingleUserImageNo.value;
        var result = HidHelperUtils.PushSingleUserImage(path, no);
        Log("PushSingleUserImage", result);
    } else {
        LogText("PushSingleUserImage", "请输入路径或工号");
    }
}

function ShowPdfSign() {
    var pathTextBox = document.getElementById("tbShowPdfSign");
    if (pathTextBox && pathTextBox.value) {
        var pdfData = pathTextBox.value;
        var numShowPdfSignTimeout = document.getElementById("numShowPdfSignTimeout");
        var timeout = Number(numShowPdfSignTimeout.value);
        var tbRunningNumberShowPdfSign = document.getElementById("tbRunningNumberShowPdfSign");
        var runningNumber = tbRunningNumberShowPdfSign.value;
        var result = HidHelperUtils.ShowPdfSignAsync(timeout, pdfData, 0, 0, 0, 0, 0, runningNumber);
    } else {
        LogText("ShowPdfSign", "请输入路径");
    }
}

function OnSignCompleted(result, confirmResult, signPicBase64, signData, runningnumber) {
    LogText("OnSignCompleted", " result：" + result);
    LogText("OnSignCompleted", " confirmResult：" + confirmResult);
    LogText("OnSignCompleted", " signPicBase64：" + signPicBase64);
    LogText("OnSignCompleted", " signData：" + signData);
    LogText("OnSignCompleted", " runningnumber：" + runningnumber);
}

function FaceValidateWithIdCardAsync() {
    var numFaceValidateWithIdCardTimeout = document.getElementById("numFaceValidateWithIdCardTimeout");
    var timeout = Number(numFaceValidateWithIdCardTimeout.value);
    var tbRunningNumberFaceValidateWithIdCard = document.getElementById("tbRunningNumberFaceValidateWithIdCard");
    var runningNumber = tbRunningNumberFaceValidateWithIdCard.value;
    var result = HidHelperUtils.FaceValidateWithIdCardAsync(timeout, runningNumber);
}

function OnFaceValidateWithIdCardCompleted(result, validate, score, photoBase64, jsonData, frontSideBase64, backSideBase64, runningNumber) {
    LogText("OnFaceValidateWithIdCardCompleted", " result：" + result);
    LogText("OnFaceValidateWithIdCardCompleted", " validate：" + validate);
    LogText("OnFaceValidateWithIdCardCompleted", " score：" + score);
    LogText("OnFaceValidateWithIdCardCompleted", " photoBase64：" + photoBase64);
    LogText("OnFaceValidateWithIdCardCompleted", " jsonData：" + jsonData);
    LogText("OnFaceValidateWithIdCardCompleted", " frontSideBase64：" + frontSideBase64);
    LogText("OnFaceValidateWithIdCardCompleted", " backSideBase64：" + backSideBase64);
    LogText("OnFaceValidateWithIdCardCompleted", " runningnumber：" + runningnumber);
}

function IdCardAsync() {
    var numIdCardTimeout = document.getElementById("numIdCardTimeout");
    var timeout = Number(numIdCardTimeout.value);
    var tbIdCardRunningNumber = document.getElementById("tbIdCardRunningNumber");
    var runningNumber = tbIdCardRunningNumber.value;
    var result = HidHelperUtils.GetIdCardAsync(timeout, runningNumber);
}

function GetIdCardCompleted(result, photoBase64, jsonData, frontSideBase64, backSideBase64, runningNumber) {
    LogText("GetIdCardCompleted", " result：" + result);
    LogText("GetIdCardCompleted", " phtotBase64：" + photoBase64);
    LogText("GetIdCardCompleted", " jsonInfo：" + jsonData);
    LogText("GetIdCardCompleted", " frontSideBase64：" + frontSideBase64);
    LogText("GetIdCardCompleted", " backSideBase64：" + backSideBase64);
    LogText("GetIdCardCompleted", " runningnumber：" + runningNumber);
}

function SignDeclarationAsync() {
    var numTimeoutSignDeclarationAsync = document.getElementById("numTimeoutSignDeclarationAsync");
    var timeout = Number(numTimeoutSignDeclarationAsync.value);
    var tbRunningNumberSignDeclarationAsync = document.getElementById("tbRunningNumberSignDeclarationAsync");
    var runningNumber = tbRunningNumberSignDeclarationAsync.value;
    var tbVoiceSignDeclarationAsyncn = document.getElementById("tbVoiceSignDeclarationAsyncn");
    var voice = tbVoiceSignDeclarationAsyncn.value;
    var tbTextSignDeclarationAsync = document.getElementById("tbTextSignDeclarationAsync");
    var text = tbTextSignDeclarationAsync.value;
    var result = HidHelperUtils.SignDeclarationAsync(voice, text, timeout, runningNumber);
}

function SignDeclarationCompleted(result, confirmResult, signPicBase64, signData, runningnumber) {
    LogText("SignDeclarationCompleted", " result：" + result);
    LogText("SignDeclarationCompleted", " confirmResult：" + confirmResult);
    LogText("SignDeclarationCompleted", " signPicBase64：" + signPicBase64);
    LogText("SignDeclarationCompleted", " signData：" + signData);
    LogText("SignDeclarationCompleted", " runningnumber：" + runningnumber);
}

//同步评价
function pj() {
    //启动设备
    Start();
    Welcome();
    //开始评价，获取评价按钮数量key；超时时间timeout；流水号runningNumber
    var key = "3";
    //超时时间
    var timeout = "60";
    //受理编号
    var runningNumber = '123456';

    var result = HidHelperUtils.Evaluate(key, timeout);
    Log("Evaluate", result);
    var evaluateValue = HidHelperUtils.GetEvaluateValue();
    console.log("evaluateValue=-=-=-" + evaluateValue);
    OnEvaluateCompleted(result, evaluateValue);

    var pjsj = JSON.stringify({
        head: {
            xzqdm: "321101",
            wdbs: "321101001"
        },
        data: {
            ywbh: runningNumber,
            jdmc: "受理",
            myd: evaluateValue,
            bmyyy: "",
            pjsj: "2019-03-26 09:11:12",
            blry: "韩梅梅",
            sqrxm: "浙商银行股份有限公司深圳分行",
            sqrlxfs: "15902983762",
            ywh: "089F2452Q43A16LF",
        }
    })
    console.log(pjsj);

    $.ajax({
        type: 'POST',
        url: '/realestate-analysis-ui/pjq/pjjg',
        data: pjsj,
        contentType: 'application/json',

    });

}

//异步评价
function ybpj() {
    //启动设备
    Start();
    Welcome();
    //开始评价，获取评价按钮数量key；超时时间timeout；流水号runningNumber
    var key = "4";
    //超时时间
    var timeout = "60";
    //受理编号
    var runningNumber = '123456';

    var result = HidHelperUtils.EvaluateAsync(key, timeout, runningNumber);
    Log("EvaluateAsync", result);

    var evaluateValue = HidHelperUtils.GetEvaluateValue();
    console.log("evaluateValue=-=-=-" + evaluateValue);
    OnEvaluateCompleted(result, evaluateValue);

    var pjsj = JSON.stringify({
        head: {
            xzqdm: "321101",
            wdbs: "321101001"
        },
        data: {
            ywbh: runningNumber,
            jdmc: "受理",
            myd: evaluateValue,
            bmyyy: "",
            pjsj: "2019-03-26 09:11:12",
            blry: "韩梅梅",
            sqrxm: "浙商银行股份有限公司深圳分行",
            sqrlxfs: "15902983762",
            ywh: "089F2452Q43A16LF",
        }
    })
    console.log(pjsj);

}

/*
//异步评价回调
function OnEvaluateCompleted(status, evaluateValue,runningNumber) {
    Log("OnEvaluateCompleted", status);
    LogText("evaluateValue", " 评价结果1：" + evaluateValue);
    LogText("runningNumber", " 流水号：" + runningNumber);
}
function OnEvaluateCompleted(status, evaluateValue,runningNumber) {
    Log("OnEvaluateCompleted", status);
    LogText("OnEvaluateCompleted", " 评价结果：" + evaluateValue);
    LogText("OnEvaluateCompleted", " 流水号：" + runningNumber);
}
function GetEvaluateAsyncResult() {
    var evaluateValue = HidHelperUtils.GetEvaluateValue();
    var evaluateValueRunningNumber = HidHelperUtils.GetEvaluateValueRunningNumber();
    OnEvaluateCompleted(0, evaluateValue);
    LogText("流水号|评价值", evaluateValueRunningNumber);
}*/
