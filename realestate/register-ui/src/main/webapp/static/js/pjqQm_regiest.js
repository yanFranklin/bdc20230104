
var HidHelperUtils;
var log;
var isInit = false;
// 不动产签字信息，建议在函数外赋好值再组织
var bdcQzxx = new Object();

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
        }
        else {
            return false;
        }
    }
}

function Log(operation, value) {
    var message = "";
    switch (value) {
        case 0: message = "成功"; break;
        case -1: message = "信道错误,请重新开启服务"; break;
        case -2: message = "设备执行失败"; break;
        case -3: message = "设备响应报文长度错误"; break;
        case -4: message = "软件卸载失败"; break;
        case -5: message = "指令执行超时"; break;
        case -6: message = "用户撤消"; break;
        case -60: message = "http下载失败"; break;
        case -80: message = "无需升级"; break;
        case -81: message = "Apk验证失败"; break;
        case 1: message = "内部错误"; break;
        case 2: message = "设备不在线"; break;
        case 3: message = "参数错误"; break;
        case 4: message = "软件没有运行"; break;
        case 9: message = "设备未授权"; break;
        case 10: message = "文件不存在"; break;
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
        // alert("result"+result);
        // Log("Start", result);
    }
}

function OneMeter() {
    var result = HidHelperUtils.OneMeter();
    Log("OneMeter", result);
}

function Welcome() {
    var result = HidHelperUtils.Welcome();
    Log("Welcome", result);
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
    // Log("Logout", result);
}

/**
 * 暂停服务
 * @constructor
 */
function StopService() {
    var result = HidHelperUtils.StopService();
    // Log("StopService", result);
}

/**
 * 重启服务
 * @constructor
 */
function RestoreService() {
    var result = HidHelperUtils.RestoreService();
    // Log("RestoreService", result);
}

function Evaluate() {
    var lbEvaluateButton = document.getElementById("lbEvaluateButton");
    var key = lbEvaluateButton.value;

    var numEvaluateTimeout = document.getElementById("numEvaluateTimeout");
    var timeout = Number(numEvaluateTimeout.value);

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
    Log("OnEvaluateCompleted", status);
    LogText("OnEvaluateCompleted", " 评价结果：" + evaluateValue);
    LogText("OnEvaluateCompleted", " 流水号：" + runningNumber);
}

function PushMedia() {
    var pathTextBox = document.getElementById("tbPushMediaPath");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.PushMedia(path);
        Log("PushMedia", result);
    }
    else {
        LogText("PushMedia", "请输入路径");
    }
}

function PushMediaRename() {
    var pathTextBox = document.getElementById("tbPushMediaPath");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.PushMediaRename(path);
        Log("PushMediaRename", result);
    }
    else {
        LogText("PushMediaRename", "请输入路径");
    }
}

function PushUserImage() {
    var pathTextBox = document.getElementById("tbPushUserImage");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.PushUserImage(path);
        Log("PushUserImage", result);
    }
    else {
        LogText("PushUserImage", "请输入路径");
    }
}

function PushStyle() {
    var pathTextBox = document.getElementById("tbPushStyle");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.PushStyle(path);
        Log("PushStyle", result);
    }
    else {
        LogText("PushStyle", "请输入路径");
    }
}

function PushSound() {
    var pathTextBox = document.getElementById("tbPushSound");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.PushSound(path);
        Log("PushSound", result);
    }
    else {
        LogText("PushSound", "请输入路径");
    }
}

function UpdateAPK() {
    var pathTextBox = document.getElementById("tbUpdateAPK");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.UpdateAPK(path);
        Log("UpdateAPK", result);
    }
    else {
        LogText("UpdateAPK", "请输入APK路径");
    }
}

function Install() {
    var pathTextBox = document.getElementById("tbInstall");
    if (pathTextBox && pathTextBox.value) {
        var path = pathTextBox.value;
        var result = HidHelperUtils.Install(path);

        Log("Install", result);
    }
    else {
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
    }
    else {
        LogText("PlaySound", "请输入文件名");
    }
}

function MargueeNotice() {
    var noticeTextBox = document.getElementById("tbMargueeNotice");
    if (noticeTextBox) {
        var noticeText = noticeTextBox.value;
        var result = HidHelperUtils.MargueeNotice(noticeText);
        Log("MargueeNotice", result);
    }
    else {
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
    }
    else {
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
    }
    else {
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
    }
    else {
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
    }
    else {
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
    }
    else {
        LogText("ShowInformation", "请输入内容");
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
    }
    else {
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
    }
    else {
        LogText("ShowPdfSign", "请输入路径");
    }
}

function ShowPdfSignEx() {
    var pdfBase64 = document.getElementById("tbShowPdfSignEx");
    if (pdfBase64 && pdfBase64.value) {
        var pdfData = pdfBase64.value;
        var numShowPdfSignExTimeout = document.getElementById("numShowPdfSignExTimeout");
        var timeout = Number(numShowPdfSignExTimeout.value);
        var selShowPdfSignExSignType = document.getElementById("selShowPdfSignExSignType");
        var signtype = Number(selShowPdfSignExSignType.value);
        var level = Number(lbLoginLevel.value);
        var tbRunningNumberShowPdfSign = document.getElementById("tbRunningNumberShowPdfSignEx");
        var runningNumber = tbRunningNumberShowPdfSign.value;
        var tbTtsShowPdfSignExAsync = document.getElementById("tbTtsShowPdfSignExAsync");
        var voice = tbTtsShowPdfSignExAsync.value;
        var result = HidHelperUtils.ShowPdfSignExAsync(timeout, voice, pdfData, signtype, 0, 0, 0, 0, 0, runningNumber);
    }
    else {
        LogText("ShowPdfSign", "请输入路径");
    }
}

function CapactiveWriteAsync() {
    var ttsTextBox = document.getElementById("tbTtsCapactiveWrite");
    var tts = ttsTextBox.value;
    var numCapactiveWriteTimeout = document.getElementById("numCapactiveWriteTimeout");
    var timeout = Number(numCapactiveWriteTimeout.value);
    var tbRunningNumberCapactiveWrite = document.getElementById("tbRunningNumberCapactiveWrite");
    var runningNumber = tbRunningNumberCapactiveWrite.value;
    var result = HidHelperUtils.CapactiveWriteAsync(timeout, tts, runningNumber);
}

function PDFInteractiveBase64() {
    var tbPDFInteractiveBase64 = document.getElementById("tbPDFInteractiveBase64");
    var tbPDFInteractiveBase64TTS = document.getElementById("tbPDFInteractiveBase64TTS");
    if (tbPDFInteractiveBase64 && tbPDFInteractiveBase64.value) {
        var pdfData = tbPDFInteractiveBase64.value;
        var tts = tbPDFInteractiveBase64TTS.value;
        var result = HidHelperUtils.PDFInteractiveBase64(0, tts, pdfData);
        Log("PDFInteractiveBase64", result);
    }
    else {
        LogText("PDFInteractiveBase64", "请输入路径");
    }
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
    LogText("OnFaceValidateWithIdCardCompleted", " score：" + score);
    LogText("OnFaceValidateWithIdCardCompleted", " photoBase64：" + photoBase64);
    LogText("OnFaceValidateWithIdCardCompleted", " jsonData：" + jsonData);
    LogText("OnFaceValidateWithIdCardCompleted", " frontSideBase64：" + frontSideBase64);
    LogText("OnFaceValidateWithIdCardCompleted", " backSideBase64：" + backSideBase64);
    LogText("OnFaceValidateWithIdCardCompleted", " sceneBase64：" + sceneBase64);
    LogText("OnFaceValidateWithIdCardCompleted", " runningnumber：" + runningNumber);
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

function OnCapactiveWriteCompleted(result, confirmResult, signPicBase64, runningnumber) {
    LogText("OnCapactiveWriteCompleted", " result：" + result);
    LogText("OnCapactiveWriteCompleted", " confirmResult：" + confirmResult);
    LogText("OnCapactiveWriteCompleted", " signPicBase64：" + signPicBase64);
    LogText("OnCapactiveWriteCompleted", " runningnumber：" + runningnumber);
}

function OnShowPdfSignExCompleted(result, confirmResult, iSignType, signPicBase64, signData, fingerImageBase64, fingerFeatureBase64, message, runningnumber) {
    LogText("OnShowPdfSignExCompleted", " result：" + result);
    LogText("OnShowPdfSignExCompleted", " confirmResult：" + confirmResult);
    LogText("OnShowPdfSignExCompleted", " iSignType：" + iSignType);
    LogText("OnShowPdfSignExCompleted", " signPicBase64：" + signPicBase64);
    LogText("OnShowPdfSignExCompleted", " signData：" + signData);
    LogText("OnShowPdfSignExCompleted", " fingerImageBase64：" + fingerImageBase64);
    LogText("OnShowPdfSignExCompleted", " fingerFeatureBase64：" + fingerFeatureBase64);
    LogText("OnShowPdfSignExCompleted", " message：" + message);
    LogText("OnShowPdfSignExCompleted", " runningnumber：" + runningnumber);
}

function UpdateApkOnline() {
    var tbUpdateApkOnlineUrl = document.getElementById("tbUpdateApkOnlineUrl");
    var url = tbUpdateApkOnlineUrl.value;
    var tbUpdateApkOnlineMd5 = document.getElementById("tbUpdateApkOnlineMd5");
    var md5 = tbUpdateApkOnlineMd5.value;

    if (tbUpdateApkOnlineUrl && tbUpdateApkOnlineUrl.value) {
        var ret = HidHelperUtils.UpdateApkOnline(url, md5);
        Log("UpdateApkOnline", ret);
    }
    else {
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
        }
        else {
            LogText("NeedUpdateApk", "需要升级");
        }
    }
    else {
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
    }
    else {
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
    }
    else {
        LogText("Tts", "请输入Tts内容");
        return;
    }
    if (tbTts && tbTts.value) {
        tts = tbTts.value;
    }
    else {
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

function DeleteMedia() {
    var remotePath = "/sdcard/JSHJ/EvMS/mediaData/*.*";
    var ret = HidHelperUtils.Delete(remotePath);
    LogText("Delete", ret);
}

function DeletePhoto() {
    var remotePath = "/sdcard/JSHJ/EvMS/userImg/*.*";
    var ret = HidHelperUtils.Delete(remotePath);
    LogText("Delete", ret);
}

function DeleteSound() {
    var remotePath = "/sdcard/JSHJ/EvMS/soundData/*.*";
    var ret = HidHelperUtils.Delete(remotePath);
    LogText("Delete", ret);
}

function DeleteStyle() {
    var remotePath = "/sdcard/JSHJ/EvMS/style/*.*";
    var ret = HidHelperUtils.Delete(remotePath);
    LogText("Delete", ret);
}

function GetFileInfos(remotePath) {
    var files = HidHelperUtils.GetFileInfos(remotePath);
    LogText("GetFileInfos", files);
}

function DeleteByType(type, fileName) {
    var ret = HidHelperUtils.DeleteByType(type, fileName);
    // LogText("DeleteByType", ret);
}

function DeleteByTypeMedia() {
    var fileName = document.getElementById("tbPushMediaPath_Delete");
    DeleteByType(1, fileName.value);
}

function DeleteByTypeUserImage() {
    var fileName = document.getElementById("tbPushUserImage_Delete");
    DeleteByType(2, fileName.value);
}

function DeleteByTypeSound() {
    var fileName = document.getElementById("tbPushSound_Delete");
    DeleteByType(3, fileName.value);
}

function DeleteByTypeStyle() {
    var fileName = document.getElementById("tbPushStyle_Delete");
    DeleteByType(4, fileName.value);
}

function GetFileInfosByType(type) {
    var files = HidHelperUtils.GetFileInfosByType(type);
    LogText("GetFileInfosByType", files);
}





/**
 * 暂停服务
 * @constructor
 */
function StopServicePjq() {
    //启动设备
    Start();
    HidHelperUtils.StopService();
}

/**
 * 恢复服务
 * @constructor
 */
function RestoreServicePjq() {
    //启动设备
    Start();
    HidHelperUtils.RestoreService();
}

/**
 * 退出登录
 * @constructor
 */
function LogoutPjq() {
    //启动设备
    Start();
    HidHelperUtils.Logout();
}





/**
 * 展示叫号信息
 * @constructor
 */
function ShowInformationPjq(jhxx) {

    //启动设备
    Start();
    if (jhxx) {
        var html = jhxx;
        var timeout = Number(20);
        var isUrl = 1;
        HidHelperUtils.ShowInformation(timeout, html, isUrl);
    }
    else {
        layer.msg('无叫号信息！');
    }
}

/**
 * 推送广告图片到评价器
 */
function deleteAndPush() {

    //启动评价器
    Start();
    //先清除评价器中的图片，在将文件夹中的图片推送到评价器
    var fileName = "*.*";
    DeleteByType(1, fileName);

    var path = "c:\\pjq";
    var res = HidHelperUtils.PushMedia(path);
    if(res == 0){
        ityzl_SHOW_SUCCESS_LAYER("推送成功！");
    } else {
        ityzl_SHOW_WARN_LAYER("推送失败！");
    }
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
function OnSignCompleted(result, confirmResult, signPicBase64, signData, runningnumber) {
   /* 输出参数：
    result：返回0成功  非0：失败
    confirmResult：1: 确认按钮，2：取消按钮
    signPicBase64：签名图片base64
    signData：签名数据base64
    runningNumber：流水号*/

    //OnSignCompleted为硬件回调函数，参数不可动，

    bdcQzxx.qznr = signPicBase64;
    saveQzxx();
}
/**
 * 触发评价器签字功能
 * @returns {*}
 */
function pjqsign(pdfPath, slbh) {
    //启动评价器
    Start();
    var timeout = Number(120);
    //slbh
    var runningNumber = slbh;

    $.ajax({
        url: getIP()+"/realestate-register-ui/rest/v1.0/qzxx/getPdfBase64",
        type: 'GET',
        data: {pdf: pdfPath},
        success: function (data) {
            console.log(data);
            if(data){
                // 测试用 todo
                // bdcQzxx.qznr = data;
                // saveQzxx();

                //调用评价器签字功能
                var result = HidHelperUtils.ShowPdfSignAsync(timeout, data, 0, 0, 0, 0, 0, runningNumber);
                //会调用OnSignCompleted（）函数，后续操作在其中执行，数据组织也在其中
                if(result){
                    console.log(result);
                }
            }
        }

    })
}

/**
 * 保存签字信息
 */
function saveQzxx() {
    $.ajax({
        url: getIP() + "/realestate-register-ui/rest/v1.0/qzxx/saveQzxx",
        type: "POST",
        datatype: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcQzxx),
        success: function (data) {
            if (data) {
                $('.pjq-qm').attr('src', 'data:image/png;base64,' + signPicBase64);
                ityzl_SHOW_SUCCESS_LAYER("签字保存成功！");
            } else {
                ityzl_SHOW_WARN_LAYER("签字保存失败！")
            }
        }
    })
}

/**
 *
 */
function queryPjqQmnr() {
    $.ajax({
        url: getIP() + "/realestate-register-ui/rest/v1.0/qzxx/queryQzxxFile",
        type: "POST",
        datatype: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcQzxx),
        success: function (data) {
            if (data) {
                $('.pjq-qm').attr('src', data);
            }
        }
    })
}


//检测设备是否在线返回0成功/在线;  非0：失败/不在线
function isOnline() {
    var result = HidHelperUtils.IsDeviceOnLine();
    return result;
}
//操作成功提示
function ityzl_SHOW_SUCCESS_LAYER(msg) {
    layer.msg( msg);
}
//警告小提示
function ityzl_SHOW_WARN_LAYER(msg) {
    layer.msg(msg);
}
