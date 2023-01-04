/**
 * @author "<a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2019/09/05
 * @description 南通 房产证明编辑页面JS
 */
var zmmb = getIP() + "/realestate-inquiry-ui/static/printModel/";
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form;

    // 页面跳转类型: 1 综合查询页面bdcZszm.html单个操作直接跳转；2 多个记录台账fczmList.html编辑跳转过来
    var pageType = $.getUrlParam('pageType');
    // 打印的证明类型
    var type = $.getUrlParam('type');

    if("2" == pageType){
        $("#saveBtn").show();
        $("#printBtn").removeClass("bdc-major-btn");
        $("#printBtn").addClass("bdc-secondary-btn");
    } else {
        $("#saveBtn").hide();
        $("#printBtn").removeClass("bdc-secondary-btn");
        $("#printBtn").addClass("bdc-major-btn");
    }

    // 获取缓存信息
    var fczm = JSON.parse($.cookie('fczm'));
    if(fczm){
        fczm.cxsd = getDate();
        console.log(fczm.cxsd);
        form.val('fczmform', fczm);
    }

    // 获取配置项
    var zhcxpz;
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zhcx/pz",
        type: "GET",
        dataType: 'json',
        async: false,
        success: function (res) {
            if(res){
                zhcxpz = res;
                setZmnr(type);
            }else{
                failMsg("发现页面未配置正确，请联系管理员解决！");
            }
        },
        error: function () {
            failMsg("发现页面未配置正确，请联系管理员解决！");
        }
    });

    // 获取当前登录用户
    var userName = "";
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/fczm/userinfo",
        type: "GET",
        dataType: "text",
        success: function (data) {
            userName = data;
        }
    });

    form.on('submit(saveBtn)', function(){
        // 获取缓存列表数据
        var fczmListData = layui.data('fczmListData');
        var data = JSON.parse(fczmListData.data);
        if(!data){
            warnMsg("保存失败，请重新打开页面重试！");
            return;
        }

        // 更新指定记录证明内容
        for(var index in data){
            if(data[index].bh == fczm.bh){
                data[index].zmnr = $("#zmnr").val();
                // 重新保存缓存内容
                layui.data('fczmListData', {key:"data", value: JSON.stringify(data)});
                break;
            }
        }
        // 更新当前页面缓存
        fczm.zmnr = $("#zmnr").val();
        fczm.cxsqr = $("#cxsqr").val();
        fczm.cxsd = $("#cxsd").val();
        fczm.sfzh = $("#sfzh").val();
        fczm.zmsj = $("#zmsj").val();
        $.cookie('fczm', JSON.stringify(fczm));

        successMsg("保存成功！");
    });

    form.on('submit(printBtn)', function(data){
        fczm.zmnr = $("#zmnr").val();
        fczm.jbr = userName;

        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/print/nantong/fcda",
            type: "POST",
            data: JSON.stringify(fczm),
            contentType: 'application/json',
            dataType: "text",
            success: function (key) {
                if (key) {
                    // 获取证明模板名称
                    var zmmbmc = getZmmbmc(type);

                    var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/nt/fczm/" + key + "/xml";
                    // 请求打印
                    print(zmmbmc, dataUrl, false);
                    // 保存打印日志
                    savePrintInfo(zmmbmc, dataUrl, {'zmbh': fczm.bh, "printType": getTpyeName(type),"zl": fczm.zl,
                        "qlr": fczm.qlrmc});
                } else {
                    failMsg("打印失败，请重试！");
                }
            },
            error: function () {
                failMsg("打印失败，请重试！");
            }
        });
        return false;
    });

    // 获取打印证明类型名称
    function getTpyeName(type){
        switch (type) {
            case "bwsqrsyzm":   return "不为申请人所有证明";
            case "ycszm":       return "已出售证明";
            case "wbdcdjzlzm":  return "无不动产登记资料证明";
            case "dkzm":        return "贷款证明";
            case "zlbgzmyzl":   return "座落变更证明（原座落）";
            case "zlbgzmxzl":   return "座落变更证明（现座落）";
            case "qtzm":        return "其他证明";
        }
    }

    // 设置证明内容
    function setZmnr(type) {
        if("bwsqrsyzm" == type){
            $("#zmnr1").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr1Bwsqrsyzm);
            $("#zmnr2").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr2Bwsqrsyzm);
        } else if("ycszm" == type){
            $("#zmnr1").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr1Ycszm);
            $("#zmnr2").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr2Ycszm);
        } else if("wbdcdjzlzm" == type){
            $("#zmnr1").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr1Wbdcdjzlzm);
            $("#zmnr2").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr2Wbdcdjzlzm);
        } else if("dkzm" == type){
            $("#zmnr1").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr1Dkzm);
            $("#zmnr2").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr2Dkzm);
        } else if("zlbgzmyzl" == type){
            $("#zmnr1").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr1Zlbgzmyzl);
            $("#zmnr2").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr2Zlbgzmyzl);
        } else if("zlbgzmxzl" == type){
            $("#zmnr1").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr1Zlbgzmxzl);
            $("#zmnr2").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr2Zlbgzmxzl);
        } else if("qtzm" == type){
            $("#zmnr1").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr1Qtzm);
            $("#zmnr2").html("&nbsp;&nbsp;&nbsp;&nbsp;" + zhcxpz.zmnr2Qtzm);
        }
    }

    // 获取证明模板名称
    function getZmmbmc(type) {
        switch (type) {
            case "bwsqrsyzm":   return zmmb + zhcxpz.zhcxzmmbBwsqrsyzm;
            case "ycszm":       return zmmb + zhcxpz.zhcxzmmbYcszm;
            case "wbdcdjzlzm":  return zmmb + zhcxpz.zhcxzmmbWbdcdjzlzm;
            case "dkzm":        return zmmb + zhcxpz.zhcxzmmbDkzm;
            case "zlbgzmyzl":   return zmmb + zhcxpz.zhcxzmmbZlbgzmyzl;
            case "zlbgzmxzl":   return zmmb + zhcxpz.zhcxzmmbZlbgzmxzl;
            case "qtzm":        return zmmb + zhcxpz.zhcxzmmbQtzm;
        }
    }

    function getDate() {
        var date = new Date();

        var year = date.getFullYear();        //年 ,从 Date 对象以四位数字返回年份
        var month = date.getMonth() + 1;      //月 ,从 Date 对象返回月份 (0 ~ 11) ,date.getMonth()比实际月份少 1 个月
        var day = date.getDate();             //日 ,从 Date 对象返回一个月中的某一天 (1 ~ 31)

        var hours = date.getHours();          //小时 ,返回 Date 对象的小时 (0 ~ 23)
        var minutes = date.getMinutes();      //分钟 ,返回 Date 对象的分钟 (0 ~ 59)
        var seconds = date.getSeconds();      //秒 ,返回 Date 对象的秒数 (0 ~ 59)

        //修改月份格式
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }

        //修改日期格式
        if (day >= 0 && day <= 9) {
            day = "0" + day;
        }

        //修改小时格式
        if (hours >= 0 && hours <= 9) {
            hours = "0" + hours;
        }

        //修改分钟格式
        if (minutes >= 0 && minutes <= 9) {
            minutes = "0" + minutes;
        }

        //修改秒格式
        if (seconds >= 0 && seconds <= 9) {
            seconds = "0" + seconds;
        }

        //获取当前系统时间  格式(yyyy-mm-dd hh:mm:ss)
        var currentFormatDate = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;

        return currentFormatDate;

    }

});