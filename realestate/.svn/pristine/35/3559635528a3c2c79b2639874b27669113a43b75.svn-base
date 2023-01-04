var HidHelperUtils;
var log;
var isInit = false;

function init() {
    if (!isInit) {

        isInit = true;
        HidHelperUtils = document.getElementById('myPad');
        // log = document.getElementById('log');
        if (HidHelperUtils) {
            //HidHelperUtils.attachEvent("OnEvaluateCompleted", OnEvaluateCompleted);
            //HidHelperUtils.attachEvent("OnSignCompleted", OnSignCompleted);
            //HidHelperUtils.attachEvent("OnFaceValidateWithIdCardCompleted", OnFaceValidateWithIdCardCompleted);
            //HidHelperUtils.attachEvent("OnInteractionCompleted", OnInteractionCompleted);
            //HidHelperUtils.attachEvent("OnFingerprintCompleted", OnFingerprintCompleted);
            //HidHelperUtils.attachEvent("OnGetIdcardCompleted", OnGetIdcardAsyncCompleted);
            //HidHelperUtils.attachEvent("SignDeclarationCompleted", SignDeclarationCompleted);
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
    // log.value = log.value + "\r\n" + operation + ":" + message;
}

function LogText(operation, message) {
    // log.value = log.value + "\r\n" + operation + ":" + message;
}

function CleanLog() {
    init();
    log.value = "";
}

function Start() {
    if (init()) {
        var result = HidHelperUtils.Start();
        // Log("Start", result);
    }
}

function OneMeter() {
    var result = HidHelperUtils.OneMeter();
    // Log("OneMeter", result);
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
    var key = '3';

    var timeout = '60';

    var result = HidHelperUtils.Evaluate(key, timeout);
    Log("Evaluate", result);
    var evaluateValue = HidHelperUtils.GetEvaluateValue();
    OnEvaluateCompleted(result, evaluateValue);
}

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
    LogText("流水号|评价值", evaluateValueRunningNumber);
}

function CancelEvaluate() {
    var result = HidHelperUtils.CancelEvaluate();
    Log("CancelEvaluate", result);
}

function OnEvaluateCompleted(status, evaluateValue, runningNumber) {
    /*Log("OnEvaluateCompleted", status);
    LogText("OnEvaluateCompleted", " 评价结果：" + evaluateValue);
    LogText("OnEvaluateCompleted", " 流水号：" + runningNumber);*/
}

function PushMedia() {
    var pathTextBox = document.getElementById("tbPushMediaPath");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.PushMedia(path);
        Log("PushMedia", result);
    } else {
        LogText("PushMedia", "请输入路径");
    }
}

function PushMediaRename() {
    var pathTextBox = document.getElementById("tbPushMediaPath");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.PushMediaRename(path);
        Log("PushMediaRename", result);
    } else {
        LogText("PushMediaRename", "请输入路径");
    }
}

function PushUserImage() {
    var pathTextBox = document.getElementById("tbPushUserImage");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.PushUserImage(path);
        Log("PushUserImage", result);
    } else {
        LogText("PushUserImage", "请输入路径");
    }
}

function PushStyle() {
    var pathTextBox = document.getElementById("tbPushStyle");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.PushStyle(path);
        Log("PushStyle", result);
    } else {
        LogText("PushStyle", "请输入路径");
    }
}

function PushSound() {
    var pathTextBox = document.getElementById("tbPushSound");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.PushSound(path);
        Log("PushSound", result);
    } else {
        LogText("PushSound", "请输入路径");
    }
}

function UpdateAPK() {
    var pathTextBox = document.getElementById("tbUpdateAPK");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.UpdateAPK(path);
        Log("UpdateAPK", result);
    } else {
        LogText("UpdateAPK", "请输入APK路径");
    }
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

function Interactive() {
    var htmlTextBox = document.getElementById("tbInteractive");
    if (htmlTextBox && htmlTextBox.value) {
        var html = htmlTextBox.value;
        var numInteractiveTimeout = document.getElementById("numInteractiveTimeout");
        var cbIsUrl = document.getElementById("cbIsUrl");

        var timeout = Number(numInteractiveTimeout.value);
        var isUrl = cbIsUrl.checked ? 1 : 0;
        var result = HidHelperUtils.Interactive(timeout, html, isUrl);
        Log("Interactive", result);
        var interactionResult = HidHelperUtils.GetInteractiveResult();
        OnInteractionCompleted(result, interactionResult);
    } else {
        LogText("Interactive", "请输入路径");
    }
}

function InteractiveAsync() {
    var htmlTextBox = document.getElementById("tbInteractive");
    if (htmlTextBox && htmlTextBox.value) {
        var html = htmlTextBox.value;
        var numInteractiveTimeout = document.getElementById("numInteractiveTimeout");
        var cbIsUrl = document.getElementById("cbIsUrl");

        var timeout = Number(numInteractiveTimeout.value);
        var isUrl = cbIsUrl.checked ? 1 : 0;
        var tbRunningNumberInteractiveAsync = document.getElementById("tbRunningNumberInteractiveAsync");
        var runningNumber = tbRunningNumberInteractiveAsync.value;
        var result = HidHelperUtils.InteractiveAsync(timeout, html, isUrl, runningNumber);
        Log("InteractiveAsync", result);
        var interactionResult = HidHelperUtils.GetInteractiveResult();
    } else {
        LogText("InteractiveAsync", "请输入html");
    }
}

function GetInteractiveAsyncResult() {
    var interactionResult = HidHelperUtils.GetInteractiveResult();
    LogText("GetInteractiveResult", " 按钮值" + interactionResult);
}

function Cancel() {
    var result = HidHelperUtils.Cancel();
    Log("Cancel", result);
}

function ShowInformation() {
    var htmlTextBox = document.getElementById("tbShowInformation");
    if (htmlTextBox && htmlTextBox.value) {
        var html = htmlTextBox.value;
        var numInteractiveTimeout = document.getElementById("numShowInformationTimeout");
        var cbIsUrl = document.getElementById("cbIsUrlShowInformation");

        var timeout = Number(numInteractiveTimeout.value);
        var isUrl = cbIsUrl.checked ? 1 : 0;
        var result = HidHelperUtils.ShowInformation(timeout, html, isUrl);
        Log("ShowInformation", result);
    } else {
        LogText("ShowInformation", "请输入内容");
    }
}
// 信息交互-显示网页
function ShowWebview() {
    var ShowWebview_url = document.getElementById("ShowWebview_url");
    if (ShowWebview_url && ShowWebview_url.value) {
        var url = ShowWebview_url.value;
        var ShowWebview_tts = document.getElementById("ShowWebview_tts");
        var tts = ShowWebview_tts.value;

        var result = HidHelperUtils.ShowWebview(999, url, tts);
        Log("ShowWebview", result);
    }
    else {
        LogText("ShowWebview", "请输入URL");
    }
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

function PDFInteractiveBase64() {
    var tbPDFInteractiveBase64 = document.getElementById("tbPDFInteractiveBase64");
    var tbPDFInteractiveBase64TTS = document.getElementById("tbPDFInteractiveBase64TTS");
    if (tbPDFInteractiveBase64 && tbPDFInteractiveBase64.value) {
        var pdfData = tbPDFInteractiveBase64.value;
        var tts = tbPDFInteractiveBase64TTS.value;
        var result = HidHelperUtils.PDFInteractiveBase64(0, tts, pdfData);
        Log("PDFInteractiveBase64", result);
    } else {
        LogText("PDFInteractiveBase64", "请输入路径");
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

function OnFaceValidateWithIdCardCompleted(result, validate, score, photoBase64, jsonData, frontSideBase64, backSideBase64, sceneBase64, runningNumber) {
    LogText("OnFaceValidateWithIdCardCompleted", " result：" + result);
    LogText("OnFaceValidateWithIdCardCompleted", " validate：" + validate);
    console.log("rzdbresult=" + validate);
    var bdjg = "";
    if(validate === 0) {
        bdjg = "人证比对失败，接口返回值：" + validate;
        ityzl_SHOW_WARN_LAYER("比对失败！请重新比对！")
    } else if (validate === 1) {
        bdjg = "人证比对成功，接口返回值：" + validate;
        ityzl_SHOW_SUCCESS_LAYER("比对成功")
    }
    LogText("OnFaceValidateWithIdCardCompleted", " score：" + score);
    LogText("OnFaceValidateWithIdCardCompleted", " photoBase64：" + photoBase64);
    LogText("OnFaceValidateWithIdCardCompleted", " jsonData：" + jsonData);
    LogText("OnFaceValidateWithIdCardCompleted", " frontSideBase64：" + frontSideBase64);
    LogText("OnFaceValidateWithIdCardCompleted", " backSideBase64：" + backSideBase64);
    LogText("OnFaceValidateWithIdCardCompleted", " sceneBase64：" + sceneBase64);
    LogText("OnFaceValidateWithIdCardCompleted", " runningnumber：" + runningNumber);
    addFaceLog(bdjg,runningNumber);
}

function GetIdcardAsync() {
    var numIdCardTimeout = document.getElementById("numIdCardTimeout");
    var timeout = Number(numIdCardTimeout.value);
    var tbIdCardRunningNumber = document.getElementById("tbIdCardRunningNumber");
    var runningNumber = tbIdCardRunningNumber.value;
    var result = HidHelperUtils.GetIdcardAsync(timeout, runningNumber);
}

function OnGetIdcardAsyncCompleted(result, confrimResult, photoBase64, jsonData, frontSideBase64, backSideBase64, runningNumber) {
    LogText("OnGetIdcardAsyncCompleted", " result：" + result);
    LogText("OnGetIdcardAsyncCompleted", " confrimResult：" + confrimResult);
    LogText("OnGetIdcardAsyncCompleted", " phtotBase64：" + photoBase64);
    LogText("OnGetIdcardAsyncCompleted", " jsonInfo：" + jsonData);
    LogText("OnGetIdcardAsyncCompleted", " frontSideBase64：" + frontSideBase64);
    LogText("OnGetIdcardAsyncCompleted", " backSideBase64：" + backSideBase64);
    LogText("OnGetIdcardAsyncCompleted", " runningnumber：" + runningNumber);
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

function UpdateApkOnline() {
    var tbUpdateApkOnlineUrl = document.getElementById("tbUpdateApkOnlineUrl");
    var url = tbUpdateApkOnlineUrl.value;
    var tbUpdateApkOnlineMd5 = document.getElementById("tbUpdateApkOnlineMd5");
    var md5 = tbUpdateApkOnlineMd5.value;

    if (tbUpdateApkOnlineUrl && tbUpdateApkOnlineUrl.value) {
        var ret = HidHelperUtils.UpdateApkOnline(url, md5);
        Log("UpdateApkOnline", ret);
    } else {
        LogText("UpdateApkOnline", "请输入URL");
    }
}

function NeedUpdateApk() {
    var tbVersionCode = document.getElementById("tbVersionCode");
    if (tbVersionCode && tbVersionCode.value) {
        var versionCode = tbVersionCode.value;
        var ret = HidHelperUtils.NeedUpdateApk(versionCode);
        if (ret <= 0) {
            LogText("NeedUpdateApk", "不需要升级");
        } else {
            LogText("NeedUpdateApk", "需要升级");
        }
    } else {
        LogText("NeedUpdateApk", "请输入Version Code");
    }
}

function GetFingerAsync() {
    var numGetFingerTimeout = document.getElementById("numGetFingerTimeout");
    var timeout = Number(numGetFingerTimeout.value);
    var tbGetFingerTts = document.getElementById("tbGetFingerTts");
    var tts = tbGetFingerTts.value;
    var tbRunningNumberGetFinger = document.getElementById("tbRunningNumberGetFinger");
    var runningNumber = tbRunningNumberGetFinger.value;

    var result = HidHelperUtils.FingerprintAsync(timeout, tts, runningNumber);
}

function OnFingerprintCompleted(status, confirmResult, fingerImageBase64, fingerFeatureBase64, runningNumber) {
    Log("OnFingerprintCompleted", status);
    LogText("OnFingerprintCompleted", " 按钮值：" + confirmResult);
    LogText("OnFingerprintCompleted", " 流水号：" + runningNumber);
    LogText("OnFingerprintCompleted", " 指纹特征base64：" + fingerFeatureBase64);
    LogText("OnFingerprintCompleted", " 指纹图片base64：" + fingerImageBase64);
    var imgGetFinger = document.getElementById("imgGetFinger");
    if (imgGetFinger) {
        imgGetFinger.src = "data:image/png;base64," + fingerImageBase64;
    }
}

function Tts() {
    var tbTts = document.getElementById("tbTts");
    if (tbTts && tbTts.value) {
        var tts = tbTts.value;
        var ret = HidHelperUtils.Tts(tts);
        LogText("Tts", ret);
    } else {
        LogText("Tts", "请输入Tts内容");
    }
}

function Tooltip() {
    var tbInfo = document.getElementById("tbTooltipInfo");
    var tbTts = document.getElementById("tbTooltipTts");
    var info;
    var tts;
    if (tbInfo && tbInfo.value) {
        info = tbInfo.value;
    } else {
        LogText("Tts", "请输入Tts内容");
        return;
    }
    if (tbTts && tbTts.value) {
        tts = tbTts.value;
    } else {
        LogText("Tts", "请输入Tts内容");
        return;
    }
    var numTooltipTimeout = document.getElementById("numTooltipTimeout");
    var timeout = Number(numTooltipTimeout.value);
    var ret = HidHelperUtils.Tooltip(info, tts, timeout);
    LogText("Tooltip", ret);
}

function GetMainboardSN() {
    var ret = HidHelperUtils.GetMainboardSN();
    LogText("GetMainboardSN", ret);
}


//同步评价
function pj(pjqData) {
    var ispj = sfpj(pjqData);
    if(ispj){
        ityzl_SHOW_WARN_LAYER("此件已完成评价，不允许再次评价！");
    } else {
        //启动设备
        Start();
        Welcome();
        //开始评价，获取评价按钮数量key；超时时间timeout；流水号runningNumber
        var key = "3";
        //超时时间
        var timeout = "60";
        //受理编号
        var runningNumber = pjqData.bdcXmDO.slbh;
        var result = HidHelperUtils.Evaluate(key, timeout);
        if(result != 0 ){
            layer.msg("评价失败！请检查！");
            return;
        }
        Log("Evaluate", result);
        var evaluateValue = HidHelperUtils.GetEvaluateValue();
        console.log("evaluateValue=-=-=-" + evaluateValue);
        OnEvaluateCompleted(result, evaluateValue);
        var bmyArray = new Array();
        var mydNum = evaluateValue;
        var pjbmy = "";
        if (evaluateValue.length > 1) {
            bmyArray = evaluateValue.split("|")
            mydNum = bmyArray[0];
            pjbmy = bmyArray[1];
        }
        if(!isNotBlank(mydNum)){
            mydNum = "5";  // 未评价
        }
        var pjsj = JSON.stringify({
            ywbh: runningNumber,
            ywmc: pjqData.bdcXmDO.gzldymc,
            jdmc: "",
            myd: mydNum,
            bmyyy: pjbmy,
            pjsj: format(new Date()),
            blry: pjqData.bdcXmDO.slr,
            blryid : pjqData.bdcXmDO.slrid,
            sqrxm: pjqData.qlrmc,
            sqrlxfs: pjqData.qlrlxfs,
            ywblsj: pjqData.bdcXmDO.slsj,
            dlrmc: pjqData.dlrmc,
            dlrlxfs: pjqData.dlrlxfs,
            djbmdm:pjqData.bdcXmDO.djbmdm,
            djbmmc:pjqData.bdcXmDO.djjg,
            qmxx: "",
            df: getDf(mydNum),
        });
        console.log(pjsj);

        $.ajax({
            type: 'POST',
            url: getContextPath() + '/pjq/evaluate?taskId='+taskId,
            data:  pjsj,
            contentType: 'application/json',
            success: function (data) {
                if (data == 1) {
                    ityzl_SHOW_WARN_LAYER("此件已完成评价，不允许再次评价！")
                } else if(data == 2){
                    ityzl_SHOW_SUCCESS_LAYER("评价成功")
                } else {
                    ityzl_SHOW_WARN_LAYER("评价失败请重新评价")
                }


                /* if (data !== null) {
                     ityzl_SHOW_SUCCESS_LAYER("评价成功")
                 } else {
                     ityzl_SHOW_WARN_LAYER("评价失败请重新评价")
                 }*/
                // //json数据转换成对象
                // var result = (new Function("", "return " + data))();
                // if (result.head.code === "0000") {
                //     ityzl_SHOW_SUCCESS_LAYER(result.head.msg)
                // }
                // if (result.head.code === "1001") {
                //     ityzl_SHOW_WARN_LAYER(result.head.msg)
                // }
            },
            error: function (xhr) {
                delAjaxErrorMsg(xhr);
            }
        });
    }


}

var pjqData;
//捷宇科技评价
function jykjPj(data) {
    pjqData = data
    var ispj = sfpj(pjqData);
    if(ispj){
        ityzl_SHOW_WARN_LAYER("此件已完成评价，不允许再次评价！");
    } else {
        // 建立webSocket连接，向评价器发送工号、姓名和头像数据
        GWQ_StartEvaluator(pjqData.username || '', pjqData.jobNumber || '', pjqData.pictureId || '');
    }

}

//捷宇科技评价结果入库
function jykjPjjg(EvaluatorLevel) {
    // 受理编号
    var runningNumber = pjqData.bdcXmDO.slbh;
    // 评价满意度
    var mydNum = EvaluatorLevel;
    if(!isNotBlank(mydNum)){
        mydNum = "5";  // 未评价
    }
    var pjsj = JSON.stringify({
        ywbh: runningNumber,
        ywmc: pjqData.bdcXmDO.gzldymc,
        jdmc: "",
        myd: mydNum,
        bmyyy: "",
        pjsj: format(new Date()),
        blry: pjqData.bdcXmDO.slr,
        blryid : pjqData.bdcXmDO.slrid,
        sqrxm: pjqData.qlrmc,
        sqrlxfs: pjqData.qlrlxfs,
        ywblsj: pjqData.bdcXmDO.slsj,
        dlrmc: pjqData.dlrmc,
        dlrlxfs: pjqData.dlrlxfs,
        djbmdm:pjqData.bdcXmDO.djbmdm,
        djbmmc:pjqData.bdcXmDO.djjg,
        qmxx: "",
        df: getDf(mydNum),
    });
    console.log(pjsj);

    $.ajax({
        type: 'POST',
        url: getContextPath() + '/pjq/evaluate?taskId='+taskId,
        data:  pjsj,
        contentType: 'application/json',
        success: function (data) {
            if (data == 1) {
                ityzl_SHOW_WARN_LAYER("此件已完成评价，不允许再次评价！")
            } else if(data == 2){
                ityzl_SHOW_SUCCESS_LAYER("评价成功")
            } else {
                ityzl_SHOW_WARN_LAYER("评价失败请重新评价")
            }
        },
        error: function (xhr) {
            delAjaxErrorMsg(xhr);
        }
    });
}

//判断是否已经评价
function sfpj(pjqData) {
    var ywbh = pjqData.bdcXmDO.slbh;
    var sfpj = false;
    $.ajax({
        type: 'GET',
        async:false,
        url: getContextPath() + '/pjq/isPj?taskId='+taskId+ '&ywbh=' + ywbh,
        success: function (data) {
            if(data == 1){
                sfpj = true;
            } else {
                sfpj = false;
            }
        }

    })

    return sfpj;
}

function getDf(myd){
    var df =0;
    if(isNotBlank(myd)){
        switch (myd) {
            case '1':
                //好评
                df = 100;
                break;
            case '2':
                //中评
                df = 80;
                break;
            case '3':
                //差评
                df = 60;
                break;
            case 'node':5
                //未评
                df = 0;
                break;
        }
    }
    return df;
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

/**
 * 人脸识别
 * @param slbh
 */
function Face(slbh) {
    Start();
    Welcome();
    //超时时间
    var timeout = "60";
    // var runningNumber = slbh;
    //返回0为成功，非0 为失败
    HidHelperUtils.FaceValidateWithIdCardAsync(timeout, slbh);

}

function addFaceLog(bdjg, slbh){
    var json = JSON.parse(JSON.stringify({"RESPONSE": bdjg}));
    json.logType = "PJQ_RZBD";
    json.viewTypeName = "人证比对结果";
    json.viewType = "accept-ui";
    json.slbh = slbh;
    saveLogInfo(json);
}


/**
 * 触发评价器签字功能
 * @returns {*}
 */
function pjqsign(pdfpath) {
    //启动评价器
    Start();
    // var dylx = "fzjl" ;
    /**
     * 获取pdf路径，调用永哥接口进行生产
     * @type {string}
     */
    // var pdfPath = "C:\\Users\\huangjian\\Desktop\\cfOne.pdf";
    var timeout = Number(120);
    //slbh
    var runningNumber = "123456";

    $.ajax({
        url: getContextPath()+"/pjq/getPdfBase64",
        type: 'GET',
        data: {pdf: pdfpath},
        async:false,
        success: function (data) {
            if(data){
                //调用评价器签字功能
                var result = HidHelperUtils.ShowPdfSignAsync(timeout, data, 0, 0, 0, 0, 0, runningNumber);

            }
        },
        error:function (xhr) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }

    })
}

//展示pdf
function showPdf(pdfpath) {
    var result = 1;
    //启动评价器
    Start();
    var timeout = Number(120);
    getReturnData("/pjq/getPdfBase64",{pdf:pdfpath},"GET",function (data) {
        if(data) {
            result = 1;
            //调用评价器签字功能
            var res = HidHelperUtils.PDFInteractiveBase64(timeout,"请扫描二维码",data);
        }
    },function (xhr) {
        delAjaxErrorMsg(xhr);
    },false);
    result = HidHelperUtils.GetInteractiveResult();
    return result;
}

/**
 * 签字回调函数，会返回签字数据，相关保存操作在里面完成
 * @param result
 * @param confirmResult
 * @param signPicBase64
 * @param signData
 * @param runningnumber
 * @constructor
 */
var bdcQzxx = {};
var pdflx = 1;
var qzrlb = 1;
var xmid = '';
var djxl = '';
var path = '';
function OnSignCompleted(result, confirmResult, signPicBase64, signData, runningnumber) {
    /* 输出参数：
       result：返回0成功  非0：失败
       confirmResult：1: 确认按钮，2：取消按钮
       signPicBase64：签名图片base64
       signData：签名数据base64
       runningNumber：流水号*/

    //OnSignCompleted为硬件回调函数，参数不可动，
    //以下参数，建议在函数外赋好值再组织
    // var bdcQzxx = new Object();
    // bdcQzxx.xmid = "1";
    // bdcQzxx.gzlslid = "2";
    // bdcQzxx.slbh = "3";
    // bdcQzxx.bdlx = "1";
    // bdcQzxx.qznr = signPicBase64;
    // bdcQzxx.qzrlb = "1";
    getReturnData("/pjq/bdcxm",{gzlslid:processInsId,xmid:xmid,djxl:djxl},"GET",function (data) {
        if(data && data.length>0) {
            for(var i=0;i<data.length;i++) {
                bdcQzxx.xmid = data[i].xmid;
                bdcQzxx.gzlslid=data[i].gzlslid;
                bdcQzxx.slbh = data[i].slbh;
                bdcQzxx.bdlx = pdflx;
                bdcQzxx.qznr = signPicBase64;
                bdcQzxx.qzrlb = qzrlb;
                saveQzxx(bdcQzxx);
            }
            //保存完签字信息后判断是否需要上传到文件中心
            // 存量房买卖合同和确认书
            if(pdflx === 4 || pdflx === 5) {
                getReturnData("/pjq/upload",{gzlslid:processInsId,djxl:djxl,pdflx:pdflx},"GET",function (data) {
                    //pdf类型为4 clfmmht,保存完刷新收件材料
                    loadSjcl();
                    console.log("上传电子合同成功")
                },function (xhr) {
                    delAjaxErrorMsg(xhr);
                })
            }
        }

    },function (xhr) {
        delAjaxErrorMsg(xhr);
    });
}

function saveQzxx(bdcQzxx) {
    $.ajax({
        url: getContextPath() + "/pjq/save?djxl=" + djxl,
        type: "POST",
        datatype: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcQzxx),
        async: false,
        success: function (data) {
            bdcQzxx = {};
            removeModal();
            if(data){
                ityzl_SHOW_SUCCESS_LAYER("签字保存成功！");
            } else {
                ityzl_SHOW_WARN_LAYER("签字保存失败！")
            }
        }
    })
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
/**
 * @param dylx-sjd,sqs...
 * @param pdf -bdc_zd_bdlx
 * @param lb bdc_zd_qzrlb
 * @param id -xmid
 * @param xl -djxl
 * @param qlrlb bdc_zd_qlrlb
 * @param onlyshow 是否仅展示还是需要签字
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description
 * @date : 2020/7/3 8:49
 */
function signXx(dylx,pdf,lb,id,xl,qlrlb,onlyshow,sfxxid) {
    addModel();
    var url = getIP() + "/realestate-accept-ui";
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
    if(!qlrlb){
        qlrlb = "all";
    }
    getReturnData('/pjq/pdfpath',{gzlslid: processInsId,xmid:id, dylx: dylx, pdflx: pdf,zxlc:zxlc,url:url, qlrlb:qlrlb,qzrlb:qzrlb,sjclids:sjclidList.join(),sfxxid:sfxxid},
        "GET",function (data) {
//返回pdf路径
        if(data) {
            var location;
            path = data;
            pdflx = pdf;
            qzrlb = lb;
            xmid = id;
            djxl = xl;
            if (dylx === 'sjd') {
                location = sjdqzwz;
            } else if (dylx === 'sqs') {
                location = sqsqzwz;
            } else if (dylx === 'qrs') {
                location = qrsqzwz;
            } else if (dylx === 'clfmmht') {
                location = clfmmht
            }
            if (dylx === 'ewmtspjq' || dylx === 'hyzfdy') {
                removeModal();
                location = "1,1,100,100";
                var VoiceStr = "请扫码";
                DoGWQ_StartSign(data, '', location, VoiceStr, 60);
            } else {
                if (sfscwjzx) {
                    DoGWQ_StartSign(data, '', location, '', 60);
                } else {
                    if (onlyshow) { //判断是否签字。默认为签字，true时仅展示pdf不签字
                        removeModal();
                        showPdf(data);
                    } else {
                        pjqsign(data);
                    }
                }
            }
        }
    },function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    })
}
// 合肥好差评
function hfHcp(url){
    Start();
    Welcome();
    console.log("开始调好差评页面");
    var result = HidHelperUtils.ShowWebview(999, url, "");
    Log("ShowWebview", result);
}