var $, form, layer, element, table, laytpl, laydate, formSelects;
var jfsbm = getQueryString("jfsbm");
var hsje = getQueryString("hsje");
var processInsId = getQueryString("processInsId");
$(function () {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        layer = layui.layer;
        form = layui.form;
        $ = layui.jquery;
        element = layui.element;
        table = layui.table;
        laytpl = layui.laytpl;

        // 初始化按钮点击事件
        loadButton();
        // 加载缴费信息
        loadPosxx();

        disabledAddFa();
    });
});

/**
 * 加载页面按钮
 */
function loadButton(){
    $("#yhkjf").click(function(){
        PosInterface.yhkjf();
    });
    $("#jhzf").click(function(){
        PosInterface.jhzf();
    });
    $("#cxye").click(function(){
        PosInterface.cxye();
    });
    $("#yhkjfcx").click(function(){
        PosInterface.yhkjfcx();
    });
    $("#jhzfth").click(function(){
        PosInterface.jhzfth();
    });
    $("#cdy").click(function(){
        PosInterface.cdy();
    });
    $("#bdjy").click(function(){
        PosInterface.bdjy();
    });
    //结算交易
    $(document).on("click", "#jsjy", function () {
        PosInterface.jsjy();
    });

    // 扫码补打
    $(document).on("click", "#smbd", function () {
        PosInterface.smbd();
    });
}

/**
 * 加载POS缴费信息
 */
function loadPosxx(){
    var renderData = {
        transAmount : hsje,
        totalAmount : hsje,
        taxNum : jfsbm,
    }
    form.val('form', renderData);
}
//补足对应位数的空白字符串长度
function MyAppend(str, n) {
    var newStr = '';
    if(str.length >= n){
        return str.substring(0, n);
    }
    for (var i = str.length; i <= n; i++) {
        if (i == n) {
            newStr = str+newStr;
        } else {
            newStr += ' ';
        }
    };
    return newStr;
}
//将金额补0补足位数  num需要补足的内容；n为补足的位数
function PrefixInteger(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}

function dealMoney(money){
    var num = String(money).split(".");
    if(num.length == 1){ //获取的金额不存在小数点后两位时，给与补充两位到分
        return num + "00";
    }else {
        var decimal = num[1];
        if (decimal.length > 2) {
            decimal = decimal.substring(0, 2); //小数位取两位
        }
        if (decimal.length < 2) {
            decimal = decimal + "0";
        }
        return num[0] + decimal;
    }
}

//调用POS机指令接口
var PosInterface = (function(){
    var getPosxx = function(){
        var transAmount = dealMoney($("#transAmount").val());
        var totalAmount = dealMoney($("#totalAmount").val());
        var oldPostrace = $("#oldPostrace").val();
        if(isNotBlank(oldPostrace)){
            oldPostrace = PrefixInteger(oldPostrace,6);
        }
        return {
            transAmount : transAmount,
            totalAmount : totalAmount,
            taxNum : $("#taxNum").val(),
            oldPostrace : oldPostrace,
            longQRPay : $("#longQRPay").val(),
            merOrderID : $("#merOrderID").val(),
        }
    };
    // 格式化POS接口返回值，获取对应数据
    // merchantNum商户编号取15位；terminalNum终端号取8位；hostTrace参考号12位
    var fmtResult = function(ret){
        return  {
            respCode : ret.substring(2,4),        //返回码【2】
            merchantNum : ret.substring(4,29),    //商户编号【25】
            terminalNum : ret.substring(29,37),   //终端编号【8】
            posTraceNum : ret.substring(79,85),  //交易凭证号【6】
            hostTrace : ret.substring(105,117),   //交易系统检索号【12】
            merOrderId : jfsbm,
            respMessage: ret.substring(160,190),  //返回信息【30】
        };
    };

    var requestPos = function(pos){
        addModel();
        console.log(pos);
        var request = new Array();
        request.push(pos.transType);
        if(isNotBlank(pos.transAmount)){
            request.push(PrefixInteger(pos.transAmount,12));
        }else{
            request.push(MyAppend(pos.transAmount,12));
        }
        request.push(MyAppend(pos.taxNum,30));
        if(isNotBlank(pos.totalAmount)){
            request.push(PrefixInteger(pos.totalAmount,12));
        }else{
            request.push(MyAppend(pos.totalAmount,12));
        }
        if(isNotBlank(pos.oldPostrace)){
            request.push(PrefixInteger(pos.oldPostrace,6));
        }else{
            request.push(MyAppend(pos.oldPostrace,6));
        }

        request.push(MyAppend(pos.oldHostTrace,12));
        request.push(MyAppend(pos.oldTransDate,4));
        request.push(MyAppend(pos.longQRPay,37));
        request.push(MyAppend(pos.merOrderID,30));
        request.push(MyAppend(pos.reverse,60));
        console.log(request);
        try{
            var input = request.join("");
            console.log(input);
            var ret = AhCcbTaxMis.misPosTrans(input);
            // var ret = "0100123456789012345          888888888888888888888888888                 1234562009071101                88888888                                        ";
            console.log(ret);
            var result = fmtResult(ret);
            console.log(result);
            removeModal();
            return result;
        }catch(err){
            removeModal(); console.info(err);
            delAjaxErrorMsg(err);
        }
        return {respCode : "11", respMessage: ""};
    };
    // 支付接口
    var pospay = function (pos) {
        var result = requestPos(pos);
        if(isNotBlank(result) && "00" == result.respCode){
            // 使用订单号通知政融平台支付成功
            noticeOthers(result, "交易成功 ");
        }else{
            ityzl_SHOW_WARN_LAYER("交易失败 "+ result.respMessage);
        }
    };

    // 校验调用POS机指令的必要参数（交易金额、缴款书总金额、缴款书编号）
    var checkParam = function(pos){
        if("" == pos.transAmount || null == pos.transAmount){
            layer.msg("未获取到交易金额。", {icon: 5, time: 3000});
            return false;
        }
        if("" == pos.totalAmount || null == pos.totalAmount){
            layer.msg("未获取到缴款书总金额。", {icon: 5, time: 3000});
            return false;
        }
        if("" == pos.taxNum || null == pos.taxNum){
            layer.msg("未获取到缴款书编号。", {icon: 5, time: 3000});
            return false;
        }
        return true;
    };

    // 校验调用指令必要参数和付款码
    var checkParamWithFkm = function(pos){
        if(checkParam(pos)){
            if("" == pos.longQRPay || null == pos.longQRPay){
                layer.msg("未获取到付款码。", {icon: 5, time: 3000});
                return false;
            }
            return true;
        }
        return false;
    };

    // 定义POS机交易接口需要传递的参数
    var getDefaultParam = function(transType){
        return {
            // 1、交易指令    2、交易金额    3、缴款书编码    4、缴款书中金额    5、原交易流水号
            transType : transType, transAmount : "", taxNum : "", totalAmount: "", oldPostrace: "",
            // 6、原交易系统检索号    7、原交易日期    8、付款码    9、订单号    10、预留备用
            oldHostTrace: "", oldTransDate:"", longQRPay: "", merOrderID: "", reverse: "",
        };
    };

    return {
        //银行卡缴费
        yhkjf : function(){
            var defaultParam = getDefaultParam("01");
            var postForm = getPosxx();
            var pos = $.extend(defaultParam, {
                transAmount: postForm.transAmount,
                taxNum : postForm.taxNum,
                totalAmount : postForm.totalAmount,
            });
            if(checkParam(pos)){
               pospay(pos);
            }
        },
        //聚合支付
        jhzf : function(){
            var defaultParam = getDefaultParam("92");
            var postForm = getPosxx();
            var pos = $.extend(defaultParam, {
                transAmount: postForm.transAmount,
                taxNum : postForm.taxNum,
                totalAmount : postForm.totalAmount,
                longQRPay : postForm.longQRPay,
            });
            if(checkParamWithFkm(pos)){
                pospay(pos);
            }
        },
        //查询余额
        cxye : function(){
            var transtype="04";
            var amt = "" ;
            var taxnum="" ;
            var totalamt="" ;
            var OldPostrace="" ;
            var OldHostTrace="";
            var OldTransDate="";
            var LongQRPay="";
            var MerOrderID="";
            var Reverse="";
            var input=transtype+MyAppend(amt,12)+MyAppend(taxnum,30)+MyAppend(totalamt,12)+MyAppend(OldPostrace,6)+MyAppend(OldHostTrace,12)+MyAppend(OldTransDate,4)+MyAppend(LongQRPay,37)+MyAppend(MerOrderID,30)+MyAppend(Reverse,60);
            console.log(input);
            try{
                var ret = AhCcbTaxMis.misPosTrans(input);
                console.log("result:"+ret);
                var result = {
                    respCode : ret.substring(2,4),
                    respMessage: ret.substring(160,190),
                }
                if("00" == result.respCode){
                    ityzl_SHOW_SUCCESS_LAYER("查询成功："+result.respMessage);
                }else{
                    ityzl_SHOW_WARN_LAYER("查询失败。");
                }
            }catch(err){
                console.info(err);
                delAjaxErrorMsg(err);
            }
        },

        // 银行卡缴费撤销
        yhkjfcx : function(){
            var defaultParam = getDefaultParam("02");
            var postForm = getPosxx();
            var pos = $.extend(defaultParam, {
                transAmount: postForm.transAmount,
                taxNum : postForm.taxNum,
                totalAmount : postForm.totalAmount,
                oldPostrace : postForm.oldPostrace,
            });
            var result = requestPos(pos);
            if("00" == result.respCode){
                ityzl_SHOW_SUCCESS_LAYER("缴费撤销成功。");
            }else{
                ityzl_SHOW_WARN_LAYER("缴费撤销失败 "+ result.respMessage);
            }
        },

        //聚合支付退货
        jhzfth : function(){
            var defaultParam = getDefaultParam("94");
            var postForm = getPosxx();
            var pos = $.extend(defaultParam, {
                transAmount: postForm.transAmount,
                taxNum : postForm.taxNum,
                merOrderID : postForm.merOrderID,
                totalAmount : postForm.totalAmount
            });
            var result = requestPos(pos);
            if("00" == result.respCode){
                ityzl_SHOW_SUCCESS_LAYER("聚合支付退货成功。");
            }else{
                ityzl_SHOW_WARN_LAYER("聚合支付退货失败 "+ result.respMessage);
            }
        },

        //重打印
        cdy : function(){
            var defaultParam = getDefaultParam("61");
            var postForm = getPosxx();
            var pos = $.extend(defaultParam, {oldPostrace: postForm.oldPostrace});
            var result = requestPos(pos);
            if("00" == result.respCode){
                ityzl_SHOW_SUCCESS_LAYER("重打印交易成功。");
            }else{
                ityzl_SHOW_WARN_LAYER("重打印交易失败 "+ result.respMessage);
            }
        },

        //补登交易
        bdjy : function(){
            var oldPostrace = $("#oldPostrace").val();
            if(!isNotBlank(oldPostrace)){
                layer.msg("请输入原交易流水号", {icon: 5, time: 3000});
                return;
            }
            checkPosFeedback().done(function(data){
                var defaultParam = getDefaultParam("61");
                var postForm = getPosxx();
                var pos = $.extend(defaultParam, {oldPostrace: postForm.oldPostrace});
                var result = requestPos(pos);
                if("00" == result.respCode){
                    ityzl_SHOW_SUCCESS_LAYER("补登交易成功。");
                    noticeOthers(result, "补登交易成功 ");
                }else{
                    ityzl_SHOW_WARN_LAYER("补登交易失败 "+ result.respMessage);
                }
            }).fail(function(data){
                ityzl_SHOW_WARN_LAYER("补登失败 " + data);
            });
        },

        //结算交易
        jsjy : function(){
            var transtype= "52";
            var amt = "" ;
            var taxnum="" ;
            var totalamt="" ;
            var OldPostrace="" ;
            var OldHostTrace="";
            var OldTransDate="";
            var LongQRPay="";
            var MerOrderID="";
            var Reverse="";
            var input=transtype+MyAppend(amt,12)+MyAppend(taxnum,30)+MyAppend(totalamt,12)+MyAppend(OldPostrace,6)+MyAppend(OldHostTrace,12)+MyAppend(OldTransDate,4)+MyAppend(LongQRPay,37)+MyAppend(MerOrderID,30)+MyAppend(Reverse,60);
            console.log(input);
            try{
                var ret = AhCcbTaxMis.misPosTrans(input);
                console.log("result:"+ret);
                var result = {
                    respCode : ret.substring(2,4),
                    respMessage: ret.substring(160,190)
                }
                if("00" == result.respCode){
                    ityzl_SHOW_SUCCESS_LAYER("结算成功："+result.respMessage);
                }else{
                    ityzl_SHOW_WARN_LAYER("结算失败。");
                }
            }catch(err){
                console.info(err);
                delAjaxErrorMsg(err);
            }
        },

        //扫码补单
        smbd : function(){
            var dsfddbh = $("#taxNum").val();
            if(!isNotBlank(dsfddbh)){
                layer.msg("订单编号为空", {icon: 5, time: 3000});
                return;
            }
            var defaultParam = getDefaultParam("09");
            var pos = $.extend(defaultParam, {taxNum : dsfddbh});
            var result = requestPos(pos,$div);
            if("00" == result.respCode){
                ityzl_SHOW_SUCCESS_LAYER("扫码补单成功。");
            }else{
                ityzl_SHOW_WARN_LAYER("扫码补单失败 "+ result.respMessage);
            }
        }
    }
})();


// 通知政融平台支付成功
function noticeOthers(result, msg){
    addModel();
    $.ajax({
        url: getContextPath() + "/sf/noticeZrpt",
        type: 'GET',
        dataType: 'json',
        contentType: "application/json",
        data: {
            ddbh : result.merOrderId,
            ckh : result.hostTrace,
            shdm : result.merchantNum,
            zdh : result.terminalNum,
            jypzh : result.posTraceNum,
            gzlslid : processInsId,
        },
        success: function (data) {
            removeModal();
            console.info(data);
            if(data && data.resultCode == "1"){
                ityzl_SHOW_SUCCESS_LAYER(msg + "通知政融平台已支付成功。");
            }else{
                ityzl_SHOW_WARN_LAYER(msg + "通知政融平台已支付失败。订单号："+ result.merOrderId);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

//校验POS机缴费是否有推送交易信息
function checkPosFeedback(){
    var deferred = $.Deferred();
    $.ajax({
        url: getContextPath() + "/sf/checkSfzt",
        type: 'GET',
        dataType: 'json',
        contentType: "application/json",
        data: {gzlslid : processInsId},
        success: function (data) {
            if(data){
                deferred.reject("已缴费成功。");
            }else{
                deferred.resolve(data);
            }
        },
        error: function (xhr, status, error) {
            deferred.reject();
        }
    });
    return deferred;
}