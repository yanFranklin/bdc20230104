/**
 * 常州摩科评价器方法
 */
// 评价器地址
var pjqUrl;
// 用户IP地址
var ipaddress="";
var userInfo;
$(function(){
    // 获取用户IP地址
    try {
        getUserIP(function (ip) {
            if (ip != null && ip != undefined) {
                ipaddress = ip;
            }
        });
    } catch(err){
        console.info("获取IP地址出错：" + err.toString());
    }

    // 获取评价器地址
    getReturnData("/pjq/url",{},"GET",function (data) {
        pjqUrl = data;
    },function (xhr, status, error) {
        delAjaxErrorMsg(xhr)
    },false);

    // 获取当前登录用户信息
    getReturnData("/pjq/userinfo",{},"GET",function (data) {
        if(data) {
            userInfo = data;
        }
    },function (xhr, status, error) {
        delAjaxErrorMsg(xhr)
    },false);

});

var pjq = (function(){
    return {
        evaluate: function () {
            var qxdm = userInfo.qxdm;
            var evaluateImpl =  evaluateFactory(qxdm);
            evaluateImpl.evaluate();
        },
    }
})();
// 工厂模式，生产对应qxdm的评价器实现
function evaluateFactory(qxdm){
    if(qxdm == "320481"){
        return new PjqLiyang();
    }
    return new PjqDefault();
}

function PjqDefault(){
    this.evaluate = function(){
        if(checkSfpj()){
            warnMsg("已进行评价，请勿进行重复评价");
            return;
        }
        getReturnData("/pjq",{gzlslid: processInsId, groupByQxdm: false, taskid: taskId},"GET",function (data) {
            var opts={"sysIp": ipaddress, "serviceId": data.bdcXmDO.slbh, "name": userInfo.alias, "jobNum": userInfo.jobNumber};
            $.ajax({
                url: getContextPath() + "/pjq/mk/mk_pj",
                type: 'POST',
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(opts),
                success: function (val) {
                    // myd 字典项：0（未评价）1（非常满意）2（满意）3（基本满意）4（一般般）5（不满意）
                    // bmyyy 字典项： 0（无）11（等待时间长）12（服务态度差）13（环境不好）14（办事效率低）15（其他原因）
                    console.info("评价返回值：" + JSON.stringify(val));
                    if(isNotBlank(val)){
                        if(val.code == "1"){
                            var res = {"data":{"re": val.data.myd, "extra":  val.data.bmyyy}};
                            tspjjg(res);
                        }else{
                            warnMsg("评价失败，" + val.message);
                        }
                    }else{
                        warnMsg("评价失败，未获取到评价信息");
                    }
                }
            });
        },function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        },false);
    }
}

function PjqLiyang(){
    this.evaluate = function(){
        getReturnData("/pjq",{gzlslid: processInsId, groupByQxdm: true, taskid: taskId},"GET",function (data) {
            window.open(data.url + "?ywbh=" + data.bdcXmDO.slbh +"&jdmc="+ data.jdmc + "&blry=" + data.bdcXmDO.slr + "&sqrxm=" + data.qlrmc + "&sqrlxfs=" + data.qlrlxfs);
        },function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        },false);
    }
}

PjqLiyang.prototype = new PjqDefault();

/**
 * 保存评价结果并推送评教结果至省厅
 * @param data
 */
function tspjjg(val){
    var pjjg = {
        slbh : $("#sjbh").val(),
        gzlslid : processInsId,
        myd : val.data.re,
        bmyyy : val.data.extra,
        taskId : taskId
    };
    addModel();
    $.ajax({
        url: getContextPath() + "/pjq/saveAndTspjjg",
        type: 'POST',
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify(pjjg),
        success: function (data) {
            removeModal();
            if(data.head.code == "0000"){
                ityzl_SHOW_SUCCESS_LAYER("评价成功");
            }else{
                ityzl_SHOW_WARN_LAYER("评价失败，"+data.head.msg);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

function checkSfpj(){
    var sfpj = false;
    $.ajax({
        type: 'GET',
        async:false,
        url: getContextPath() + '/pjq/isPj?taskId='+taskId+ '&ywbh=' + $("#sjbh").val(),
        success: function (data) {
            if(data == 1){
                sfpj = true;
            } else {
                sfpj = false;
            }
        }
    });
    return sfpj;
}