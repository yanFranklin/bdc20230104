/*
* 舒城捷宇科技评价器方法js
* */
var websocket;
// 建立webSocket连接
if (getBrowser()!="IE") {
    // 首先判断是否 支持 WebSocket
    websocket = new WebSocket("ws://localhost:1919");
    // 打开连接时
    websocket.onopen = function (event) {
        console.log(" websocket.onopen");
    };
    // 收到消息时
    websocket.onmessage = function (event) {
        onMessage(event);
    };
    websocket.onerror = function (event) {
        console.log("websocket.onerror");
    };
    websocket.onclose = function (event) {
        console.log("websocket.onclose");
    };
}

function onMessage(event)
{
    var ret=event.data;
    var obj = JSON.parse(ret);
    // 人脸识别
    if (obj.type==5) {
        if (obj.ret==0) {
            var json = {};
            json.name=obj.name;
            json.sex=obj.sex;
            json.nation=obj.nation;
            json.addr=obj.addr;
            json.birth=obj.birth;
            json.id_num=obj.id_num;
            json.base64_ID=obj.base64_ID;
            json.base64_Face=obj.base64_Face;
            json.validityTime=obj.validityTime;
            // 签发机关
            json.depart=obj.depart;
            json.passFlag=obj.passFlag;
            // 比对时间
            json.time=obj.time;
            json.score=obj.score;
            // 指纹特征值（二代证有录入指纹才有）
            json.fpdata=obj.fpdata;
            json.FrontImgBase64=obj.FrontImgBase64;
            json.BackImgBase64=obj.BackImgBase64;
            var jsonStr = JSON.stringify(json);
            OnAfterGWQ_StartFace(obj.ret,jsonStr);
        }
        else{
            OnAfterCall(obj.ret);
        }
    }
    // 评价
    if (obj.type==10) {
        if (obj.ret==0) {
            OnAfterGWQ_StartEvaluator(obj.ret,obj.EvaluatorLevel);
        }
        else {
            OnAfterCall(obj.ret);
        }
    }else if(obj.type==3){
        if (obj.ret==0){
                OnAfterGWQ_StartSign(obj.ret,obj.SignPdfBase64,obj.SignNameBase64,obj.FingerPrintBase64,obj.XML);
        }else {
            OnAfterCall(obj.ret);
        }
    }
}

// 获取浏览器类型
function getBrowser(){
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    console.log(userAgent);
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
        return "Opera"
    } //判断是否Opera浏览器
    else if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    } //判断是否Firefox浏览器
    else if (userAgent.indexOf("Edge") > -1) {
        return "Edge";
    } //判断是否Safari浏览器
    else if (userAgent.indexOf("Chrome") > -1){
        return "Chrome";
    }
    else if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    }
    else{
        return "IE";
    } //判断是否IE浏览器
}

function OnAfterCall(ErrorCode) {
    if (ErrorCode==0) {
        alert("成功");
    }
    else if (ErrorCode==-1) {
        alert("参数错误");
    }
    else if (ErrorCode==-2) {
        alert("超时");
    }
    else if (ErrorCode==-3) {
        alert("设备连接错误");
    }
    else if (ErrorCode==-4) {
        alert("发送数据失败");
    }
    else if (ErrorCode==-5) {
        alert("读取数据失败");
    }
    else if (ErrorCode==-6) {
        alert("文件操作失败");
    }
    else if (ErrorCode==-7) {
        alert("设备返回错误信息");
    }
    else if (ErrorCode==-9) {
        alert("取消");
    }
    else {
        alert("失败，返回错误码为"+ErrorCode);
    }
    removeModal();
}

// 发起评价
function GWQ_StartEvaluator(TellerName,TellerNo,TellerPhoto){
    if(getBrowser()=="IE"){
        var ret=GWQ.DoGWQ_StartEvaluator(TellerName,TellerNo,TellerPhoto);
        //OnAfterCall(ret);
    } else {
        var json = {};
        json.type=10;
        json.TellerName=TellerName;
        json.TellerNo=TellerNo;
        json.TellerPhoto=TellerPhoto;
        json.Timeout=60;
        var jsonStr = JSON.stringify(json);
        websocket.send(jsonStr);
    }
}

// 评价回调
function OnAfterGWQ_StartEvaluator(ErrorCode,EvaluatorLevel) {
    var pjjg = "评价成功";
    if(ErrorCode == 0) {
        if (EvaluatorLevel==1) {
            pjjg = pjjg + "，评价结果：非常满意";
        }
        if (EvaluatorLevel==2) {
            pjjg = pjjg + "，评价结果：满意";
        }
        if (EvaluatorLevel==3) {
            pjjg = pjjg + "，评价结果：一般";
        }
        if (EvaluatorLevel==4) {
            pjjg = pjjg + "，评价结果：非常不满意";
        }
        alert(pjjg);
        console.log(pjjg + "，接口返回值：" + ErrorCode);
    } else {
        alert("评价失败！请重新评价！");
        pjjg = "评价失败，接口返回值：" + ErrorCode;
        console.log(pjjg);
        return;
    }

    // 评价结果入库
    jykjPjjg(EvaluatorLevel);
}

function DoGWQ_StartSign(PDFPath,XmlPath,mylocation,VoiceStr,timeout)
{
    if(getBrowser()=="IE")
    {
        var ret=GWQ.DoGWQ_StartSign(PDFPath,XmlPath,mylocation,VoiceStr,timeout);
        //OnAfterCall(ret);
    }
    else {
        var json = {};
        json.PDFPath=PDFPath;
        json.XmlPath=XmlPath;
        json.Location=mylocation;
        json.Retain=VoiceStr;
        json.Timeout=timeout;
        json.type=3;
        var jsonStr = JSON.stringify(json);
        websocket.send(jsonStr);
    }
}

/**评价器签字回调函数*/
function OnAfterGWQ_StartSign(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64)
{
    if(ErrorCode==0)
    {
        if(pdflx === 4 || pdflx === 1 || pdflx === 2) {
            var url = getContextPath()+"/pjq/uploadJypjq";
            var data = {gzlslid:processInsId,djxl:djxl,pdflx:pdflx,pdfBase64:SignPdfBase64}
            $.ajax({
                url: url,
                data : JSON.stringify(data),
                type: 'POST',
                contentType:"application/json;charset=UTF-8",
                success: function (data) {
                    //保存完刷新收件材料
                    loadSjcl();
                    alert("电子签名成功");
                    removeModal();
                    console.log("上传电子合同成功")
                },
                error: function (xhr) {
                    delAjaxErrorMsg(xhr);
                    removeModal();
                }

            })
        }
        removeModal();
    }
    else if(ErrorCode==-9)
    {
        alert("电子签名取消");
    }
    else
    {
        alert("失败，返回错误码为"+ErrorCode);
    }
    removeModal();
}

// 发起人脸识别
function DoGWQ_StartFace() {
    if (getBrowser()=="IE") {
        var ret=GWQ.DoGWQ_StartFace();
        //OnAfterCall(ret);
    } else {
        var json = {};
        json.type=5;
        var jsonStr = JSON.stringify(json);
        websocket.send(jsonStr);
    }
}

// 人脸识别回调
function OnAfterGWQ_StartFace(ErrorCode,JsonData) {
    if (ErrorCode==0) {
        var obj = JSON.parse(JsonData);
        if(obj.passFlag==1) {
            alert("比对通过");
        } else {
            alert("比对失败，请重新比对");
        }
    } else {
        alert("失败，返回错误码为"+ErrorCode);
    }
}


/**
 *函数名称：GWQ_OpenURL
 *功能描述：设备打开url地址（设备需要联网）
 *输入参数：URL----网址
 *输出参数：无
 *返回值：参见：错误代码
 */


var GWQ_OpenURLType=90;
function GWQ_OpenURL(url)
{
    if(getBrowser()=="IE"){
        var ret=GWQ.GWQ_OpenURL(url);
        OnAfterCall(ret);
    }
    else{
        var json = {};
        json.type=GWQ_OpenURLType;
        json.URL=url;
        var jsonStr = JSON.stringify(json);
        websocket.send(jsonStr);
    }
}
function GWQ_ShowPDF(PDFPath,timeout){
    if(getBrowser()=="IE")
    {
        var ret = GWQ.GWQ_ShowPDF(PDFPath,timeout);
        //OnAfterCall(ret);
    }
    else {
        var json = {};
        json.PDFPath=PDFPath;
        json.Timeout=60;
        var jsonStr = JSON.stringify(json);
        websocket.send(jsonStr);
    }
}