layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    generate();
});

var alertSize = 0;
var confirmSize = 0;
//例外
var lwsize =0;
//忽略验证回调函数
var removeCallback;
//所有例外审核信息
var lwshxxData= [];

function loadTsxx(data,yzlx,callback) {
    removeCallback =callback;
    // 验证类型不传的话 则赋值为空值
    if(!yzlx){
        yzlx = "";
    }
    sessionStorage.warnSurnMsg = '{}';
    //关联土地证提示数量
    var gltdzNum=0;
    //存储关联土地证与历史关系对照
    var gltdzDTOList = [];
    $.each(data, function (index, item) {
        var xmid = "";
        var qlid = "";
        var xzgzlslid="";
        var fcjyBaxxDTO = {};
        //获取参数
        var bdcdyhAndZgzid =(isNotBlank(item.bdcdyh) ? item.bdcdyh:"")+"|"+item.gzid;
        if (item.xzxx !== null && item.xzxx != undefined) {
            if (isArray(item.xzxx) &&item.xzxx.length >0 &&!$.isEmptyObject(item.xzxx[0])) {
                xmid = item.xzxx[0].XMID;
                qlid = item.xzxx[0].QLID;
            } else if (!$.isEmptyObject(item.xzxx.bdcSlJyxx)) {
                fcjyBaxxDTO = item.xzxx;
                if (isArray(item.xzxx.bdcSlJyxx) &&item.xzxx.bdcSlJyxx.length >0) {
                    fcjyBaxxDTO.bdcSlJyxx = item.xzxx.bdcSlJyxx[0];
                }
                if (isArray(item.xzxx.bdcSlFwxx) &&item.xzxx.bdcSlFwxx.length >0) {
                    fcjyBaxxDTO.bdcSlFwxx = item.xzxx.bdcSlFwxx[0];
                }

            }else if(!$.isEmptyObject(item.xzxx.bdcSlSqr)){
                if (isArray(item.xzxx.bdcSlSqr) && item.xzxx.bdcSlSqr.length >0) {
                    fcjyBaxxDTO.bdcSlSqr = item.xzxx.bdcSlSqr;
                }
            }else if(typeof item.xzxx === "object"){
                xzgzlslid= item.xzxx.gzlslid;
            }
        }
        //忽略按钮
        var ignoreCommonBtn ="";
        //确定按钮
        var sureCommonBtn ="";
        //验证信息
        var yzmsg =item.msg;
        if (item.yzlx === "confirm") {
            //忽略按钮
            ignoreCommonBtn = '<a yzlx=\'' + yzlx + '\' name=\'' + item.gzid + '\' yzdyh=\'' + item.bdcdyh + '\'  href=\"javascrit:;\" onclick=\"remove(this,1,\'' + yzlx + '\',\'' + "" + '\',\'' + "" + '\',\'' + bdcdyhAndZgzid + '\');return false;\">忽略</a>';
        }
        if(isNotBlank(item.msg) &&(item.msg.indexOf("WLTDZWITHDY") >-1 ||(item.msg.indexOf("WLZSWITHDY") >-1))){
            //外联带抵押证书特殊处理,复制原抵押生成新抵押
            yzmsg =item.msg.replace("WLTDZWITHDY", "").replace("WLZSWITHDY", "");
            sureCommonBtn =getWlzsWithDyBtn(item);
            alertSize++;
            $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">'+ yzmsg + sureCommonBtn  +'</p>');

        }else if(isNotBlank(item.msg) && item.msg.indexOf("WITHDY") > -1){
            yzmsg = item.msg.replace("WITHDY", "");
            sureCommonBtn =getWithDyBtn(item);
            if (item.yzlx === "confirm") {
                confirmSize++;
                $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">'+ yzmsg + sureCommonBtn + ignoreCommonBtn +'</p>');
            }else{
                alertSize++;
                $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">'+ yzmsg + sureCommonBtn  +'</p>');
            }
        }else if(isNotBlank(item.msg) && item.msg.indexOf("WLZS") > -1){
            //提示信息包含WLZS或YGWLZS
            sureCommonBtn =getWlzsSureBtn(item);
            var wlzsCount = item.xzxx.length;
            yzmsg = item.msg.replace("YGWLZS","").replace("WLZS", "").replace("n", wlzsCount);
            if (item.yzlx === "confirm") {
                confirmSize++;
                $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + yzmsg + sureCommonBtn+ignoreCommonBtn+'</p>');
            }else{
                alertSize++;
                $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + yzmsg + sureCommonBtn+'</p>');
            }

        } else if (item.yzlx === "confirm") {
            if (isNotBlank(item.msg) && item.msg.indexOf("XF") > -1) {
                //是否存在续封验证的特殊处理
                    confirmSize++;
                    yzmsg = item.msg.replace("XF", "");
                    sureCommonBtn =getXfSureBtn(item);
                    $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + yzmsg + sureCommonBtn+ignoreCommonBtn+'</p>');

            } else if (isNotBlank(item.msg) && item.msg.indexOf("GLTDZ") > -1) {
                gltdzNum++;

                //关联土地证验证的特殊处理
                yzmsg = item.msg.replace("GLTDZ", "");
                var bdcdyh = item.bdcdyh;
                var gltdzDTO ={};
                gltdzDTO.bdcdyh =bdcdyh;
                gltdzDTO.wlxmid =xmid;
                gltdzDTO.msg =yzmsg;
                gltdzDTO.bdcdyhAndZgzid=bdcdyhAndZgzid;
                // 对已注销的土地证特殊处理，在确定后不提示是否注销，直接创建项目
                if(yzmsg.indexOf("YZX") > -1){
                    gltdzDTO.msg = yzmsg.replace("YZX", "");
                    gltdzDTO.yzx = true;
                }else{
                    gltdzDTO.yzx = false;
                }
                gltdzDTOList.push(gltdzDTO);
                if(gltdzNum ===1) {
                    confirmSize++;
                }

            } else if(isNotBlank(item.msg) && item.msg.indexOf("BDCDYCSHYZ") > -1){
                //不动产单元初始化验证是否已生成权利特殊处理提示信息
                confirmSize++;
                var msg = item.msg.replace("BDCDYCSHYZ", "");
                var bdcdyhAndXmid = item.xzxx[0].XMID+"|"+item.xzxx[0].BDCDYH;
                var alertMsg = '<img src=\"../../static/lib/bdcui/images/warn.png\" alt=\"\">' + msg;
                var enterBtn = '<a href=\"javascrit:;\" onclick=\"addBdcdyXm(this,\'' + bdcdyhAndXmid + '\');return false\">确定</a>';
                var qlxxBtn = '<a href=\"javascrit:;\" onclick=\"openQlxx(\'' + item.xzxx[0].XMID + '\');return false\">查看</a>';
                $("#confirmInfo").append('<p>'+ alertMsg + enterBtn + qlxxBtn +'</p>');
            } else if(isNotBlank(item.msg) && item.msg.indexOf("YZDF") > -1) {
                confirmSize++;
                var msg = item.msg.replace("YZDF", "");
                var yzdfdata = [];
                if (item.xzxx) {
                    $.each(item.xzxx, function (index, xzxxitem) {
                        yzdfdata.push(xzxxitem);
                    });
                }
                $("#confirmInfo").append("<p><img src='../../static/lib/bdcui/images/warn.png' alt=''>" + msg + "<a href='javascrit:;'  onclick='addYzdfData(this," + JSON.stringify(yzdfdata) + ");return false'>确定</a>"+ignoreCommonBtn+"</p>");
            } else if(isNotBlank(item.msg) && item.msg.indexOf("SFPP") > -1) {
                confirmSize++;
                var msg = item.msg.replace("SFPP", "");
                var jbxxid = item.jbxxid;
                var xztzlx = item.xztzlx;
                $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + msg + '<a yzlx='+yzlx+' href="javascrit:;" onclick="showMatchPage(this,\'' + jbxxid + '\',\'' + xztzlx + '\');return false">是</a><a href="javascrit:;" onclick="remove(this,6,\''+yzlx+'\',\''+""+'\',\''+""+'\',\''+bdcdyhAndZgzid+'\');return false" >否</a></p>');
            } else if(isNotBlank(item.msg) && item.msg.indexOf("SFLZ") > -1) {
                confirmSize++;
                var msg = item.msg.replace("SFLZ", "");
                var jbxxid = item.jbxxid;
                var xztzlx = item.xztzlx;
                $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + msg + '<a yzlx='+yzlx+' sfxnhcj=true href="javascrit:;" onclick="showMatchPage(this,\'' + jbxxid + '\',\'' + xztzlx + '\');return false">是</a><a href="javascrit:;" onclick="remove(this,6,\''+yzlx+'\',\''+ "" +'\',true,\''+bdcdyhAndZgzid+'\');return false" >否</a></p>');
            } else if(isNotBlank(item.msg) && item.msg.indexOf("MULLZ") > -1) {
                confirmSize++;
                var msg = item.msg.replace("MULLZ", "");
                var xmxx = item.xzxx;
                var jbxxid = item.jbxxid;
                var xztzlx = item.xztzlx;
                var qjgldm = xmxx.qjgldm;
                var xnxmid = xmxx.mullz;
                $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + msg + '<a yzlx='+yzlx+' href="javascrit:;" onclick="reloadMatch(this,\'' + jbxxid + '\',\'' + xztzlx + '\',\'' + xnxmid + '\',\'' + qjgldm + '\');return false">是</a><a href="javascrit:;" onclick="remove(this,6,\''+yzlx+'\',\''+ "" +'\',true,\''+bdcdyhAndZgzid+'\');return false" >否</a></p>');
            } else {
                confirmSize++;
                if (isNotBlank(qlid) && isNotBlank(xmid)) {
                    //权利ID有值，展现权利信息接口
                    $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + item.msg + '<a href="javascrit:;" onclick="openQlxx(\'' + xmid + '\');return false">查看</a>'+ignoreCommonBtn+'</p>');
                } else if (isNotBlank(xmid) ||isNotBlank(xzgzlslid)) {
                    //项目ID有值，展现流程信息接口
                    $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + item.msg + '<a href="javascrit:;" onclick="openXmxx(\'' + xmid + '\',\'' + xzgzlslid + '\');return false">查看</a>'+ignoreCommonBtn+'</p>');
                } else if (!$.isEmptyObject(fcjyBaxxDTO)) {
                    var getJson = JSON.parse(sessionStorage.warnSurnMsg);
                    getJson[item.bdcdyh] = fcjyBaxxDTO;
                    sessionStorage.warnSurnMsg = JSON.stringify(getJson);
                    $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + item.msg + '<a href="javascrit:;" onclick="addJyxx(this,\'' + item.bdcdyh + '\');return false">确定</a>'+ignoreCommonBtn+'</p>');
                }
                else {
                    $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + item.msg + ignoreCommonBtn+'</p>');

                }
            }
        } else if (item.yzlx === "confirmAndCreate") {
            confirmSize++;
            var bdcdyh = item.bdcdyh;
            var xmidArr = [];
            if (item.xzxx) {
                $.each(item.xzxx, function (index, xzxxitem) {
                    var dyaxmid = xzxxitem.XMID;
                    xmidArr.push(dyaxmid);
                });
            }
            $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + item.msg + '<a href="javascrit:;" onclick="wlzsAndDyaxx(this,\'' + bdcdyh + '\',\'' + xmidArr.join() + '\',true,3);return false">确定</a><a href="javascrit:;" onclick="wlzsAndDyaxx(this,\'' + bdcdyh + '\',\'' + '' + '\',false,4);return false">取消</a></p>');
        } else if (item.yzlx === "alert") {
            //提示信息存在标识JYYZBTG不自动带入,直接进行限制
            if (!$.isEmptyObject(fcjyBaxxDTO) &&isNotBlank(item.msg) && item.msg.indexOf("JYYZBTG") <0) { // 商品房交易获取交易信息，为警告提醒不允许忽略
                for (var i = 0; i < checkData.length; i++) {
                    var selectData = checkData[i];
                    if (selectData.bdcdyh === item.bdcdyh) {
                        selectData.fcjyBaxxDTO = fcjyBaxxDTO;
                    }
                }
            }else if(isNotBlank(item.msg) && item.msg.indexOf("HTJG") > -1){
                var msg = "";
                if($.isEmptyObject(htjgxx)){
                    msg = "需进行合同监管信息查询";
                }else{
                    var bdcqzhArray = item.xzxx[0].BDCQZH.split(",");
                    var match = true;
                    // 验证不动产产权号对应的合同备案信息中的合同状态，是否为已备案。
                    // 验证规则：多个不动产权证号与备案查询信息（htjgxx）完全匹配，并且合同状态不为"03"
                    $.each(bdcqzhArray, function(index, val){
                        $.each(htjgxx, function(i, htjg){
                            if(htjg.bdcqzh.indexOf(val) > -1 && htjg.htzt != "03"){
                                match = true;
                                return false;
                            }
                            match = false;
                        });
                    });
                    if(!match){
                        msg = "合同未备案，不动产单元" + item.bdcdyh + "不允许创建";
                    }
                }
                if(isNotBlank(msg)){
                    alertSize++;
                    $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + msg);
                }
            }else {
                alertSize++;
                if(isNotBlank(item.msg)){
                    item.msg =item.msg.replace("JYYZBTG", "");
                }
                if (isNotBlank(qlid) && isNotBlank(xmid)) {
                    //权利ID有值，展现权利信息接口
                    $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + item.msg + '<a href="javascrit:;" onclick="openQlxx(\'' + xmid + '\');return false">查看</a></p>');

                } else if (isNotBlank(xmid) ||isNotBlank(xzgzlslid)) {
                    $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + item.msg + '<a href="javascrit:;" onclick="openXmxx(\'' + xmid + '\',\'' + xzgzlslid + '\');return false">查看</a></p>');

                } else {
                    // 提示虚拟单元号是否匹配等情况下可能必须匹配，没有否选项
                    if(isNotBlank(item.msg) && item.msg.indexOf("SFLZ_S") > -1) {
                        var newMsg = item.msg.replace("SFLZ_S", "");
                        var jbxxid = item.jbxxid;
                        var xztzlx = item.xztzlx;
                        $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + newMsg + '<a yzlx='+yzlx+' sfxnhcj=true href="javascrit:;" onclick="showMatchPage(this,\'' + jbxxid + '\',\'' + xztzlx + '\');return false">确定</a></p>');
                    } else if(isNotBlank(item.msg) && item.msg.indexOf("SFPP_S") > -1) {
                        var newMsg = item.msg.replace("SFPP_S", "");
                        var jbxxid = item.jbxxid;
                        var xztzlx = item.xztzlx;
                        $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + newMsg + '<a yzlx='+yzlx+' href="javascrit:;" onclick="showMatchPage(this,\'' + jbxxid + '\',\'' + xztzlx + '\');return false">确定</a></p>');
                    } else if(isNotBlank(item.msg) && item.msg.indexOf("MULLZ_S") > -1) {
                        var newMsg = item.msg.replace("MULLZ_S", "");
                        var xmxx = item.xzxx;
                        var jbxxid = item.jbxxid;
                        var xztzlx = item.xztzlx;
                        var qjgldm = xmxx.qjgldm;
                        var xnxmid = xmxx.mullz;
                        $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + newMsg + '<a yzlx='+yzlx+' href="javascrit:;" onclick="reloadMatch(this,\'' + jbxxid + '\',\'' + xztzlx + '\',\'' + xnxmid + '\',\'' + qjgldm + '\');return false">确定</a></p>');
                    } else {
                        $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + item.msg + '</p>');
                    }
                }
            }

        } else if (item.yzlx === "alert-exclude") {
            lwsize++;
            alertSize++;
            var xmidArr = [];
            var count = 0;
            //循环获取所有需要外联证书
            if (item.xzxx) {
                $.each(item.xzxx, function (index, xzxxitem) {
                    var xzxmid = xzxxitem.XMID;
                    var lwshObj = JSON.parse(JSON.stringify(item));
                    lwshObj.xmid =xzxmid;
                    lwshxxData.push(lwshObj);
                    xmidArr.push(xzxmid);
                    count++;
                });
            }

            if (isNotBlank(item.msg) && item.msg.indexOf("SDLW") > -1) {
                var msg = item.msg.replace("SDLW", "");
                $("#alertInfo").append('<p class="bdc-lwtsxx-p"><img src="../../static/lib/bdcui/images/error.png" alt="">' + msg + '<a href="javascrit:;"  onclick="removeAlert(\'' + item.gzid + '\',\'' + item.gzmc + '\',\'' + item.bdcdyh + '\',\'' + xmidArr.join(",") + '\',this);return false">例外</a></p>');
            } else if (isNotBlank(qlid) && isNotBlank(xmid)) {
                //权利ID有值，展现权利信息接口
                $("#alertInfo").append('<p class="bdc-lwtsxx-p"><img src="../../static/lib/bdcui/images/error.png" alt="">' + item.msg + '<a href="javascrit:;"  onclick="openQlxx(\'' + xmid + '\');return false">查看</a><a href="javascrit:;" onclick="removeAlert(\'' + item.gzid + '\',\'' + item.gzmc + '\',\'' + item.bdcdyh + '\',\'' + xmidArr.join(",") + '\',this);return false">例外</a></p>');

            } else if (isNotBlank(xmid)) {
                $("#alertInfo").append('<p class="bdc-lwtsxx-p"><img src="../../static/lib/bdcui/images/error.png" alt="">' + item.msg + '<a href="javascrit:;"  onclick="openXmxx(\'' + xmid + '\');return false">查看</a><a href="javascrit:;" onclick="removeAlert(\'' + item.gzid + '\',\'' + item.gzmc + '\',\'' + item.bdcdyh + '\',\'' + xmidArr.join(",") + '\',this);return false">例外</a></p>');

            } else {
                $("#alertInfo").append('<p class="bdc-lwtsxx-p"><img src="../../static/lib/bdcui/images/error.png" alt="">' + item.msg + '<a href="javascrit:;"  onclick="removeAlert(\'' + item.gzid + '\',\'' + item.gzmc + '\',\'' + item.bdcdyh + '\',\'' + xmidArr.join(",") + '\',this);return false">例外</a></p>');

            }
        }

        //当只存在提示信息时展现全部忽略按钮，存在不可例外警告不展示忽略按钮
        if (alertSize > 0) {
            $("#ignoreAll").remove();
        }
    });
    //当警告全部可例外展示例外全部按钮,存在不可例外警告不展示
    if(lwsize ===0 ||alertSize >lwsize){
        $("#lwAll").remove();
    }
    if(gltdzNum >0 &&gltdzDTOList != null &&gltdzDTOList.length >0) {
        var tdzbdcdyhAndZgzid =gltdzDTOList[0].bdcdyhAndZgzid;
        sessionStorage.glTdzxx = JSON.stringify(gltdzDTOList);
        var tdzSureBtn =getGltdzSureBtn(gltdzDTOList);
        $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + gltdzDTOList[0].msg +tdzSureBtn+ '<a href="javascrit:;" onclick="remove(this,1,\''+""+'\',\''+""+'\',\''+""+'\',\''+tdzbdcdyhAndZgzid+'\');return false">忽略</a></p>');
    }

}

//组织外联产权证书带抵押确定按钮参数
function getWlzsWithDyBtn(item){

    //确定按钮
    return '<a href=\"javascrit:;\" onclick=\"wlZsWithdy(this,\''+item.bdcdyh+'\');return false\">确定</a>';

}

//组织带抵押确定按钮参数
function getWithDyBtn(item){
    var dyaxmids =[];
    if (item.xzxx) {
        $.each(item.xzxx, function (index, xzxxitem) {
            var dyaxmid = xzxxitem.XMID;
            dyaxmids.push(dyaxmid);
        });
    }
    //确定按钮
    return '<a href=\"javascrit:;\" onclick=\"addDyxmData(this,\''+item.bdcdyh+'\',\'' + dyaxmids.join(",") + '\');return false\">确定</a>';

}

//组织外联证书确定按钮参数
function getWlzsSureBtn(item){
    //自动外联证书的特殊处理
    var bdcdyhAndXmidArr = [];
    var count = 0;
    //循环获取所有需要外联证书
    if (item.xzxx) {
        $.each(item.xzxx, function (index, xzxxitem) {
            //xmid,bdcdyh,ygdjzl拼接组成
            var bdcdyhAndXmid = xzxxitem.XMID+"|"+(isNotBlank(xzxxitem.WLBDCDYH) ? xzxxitem.WLBDCDYH:"") +"|"+(isNotBlank(xzxxitem.YGDJZL) ? xzxxitem.YGDJZL:"");
            bdcdyhAndXmidArr.push(bdcdyhAndXmid);
            count++;
        });
    }
    var wlYg =false;
    if(item.msg.indexOf("YGWLZS") > -1){
        wlYg =true;
    }
    //外联确定按钮
    return '<a href="javascrit:;" onclick="wlzs(this,\'' + bdcdyhAndXmidArr.join(",") + '\',\''+wlYg+'\');return false">确定</a>';

}

//组织续封确定按钮参数
function getXfSureBtn(item){
    var xmidArr = [];
    //循环获取所有需要自动添加证书
    if (item.xzxx) {
        $.each(item.xzxx, function (index, xzxxitem) {
            var zsxmid = xzxxitem.XMID;
            xmidArr.push(zsxmid);
        });
    }
    return '<a href="javascrit:;" onclick="addCfXm(this,\'' + xmidArr + '\');return false">确定</a>';
}

//组织关联土地证确定按钮参数
function getGltdzSureBtn(gltdzDTOList){
    sessionStorage.glTdzxx = JSON.stringify(gltdzDTOList);
    var needConfirm = false;
    $.each(gltdzDTOList,function(index,val){
        if(val.yzx == false){
            needConfirm = true;
            return false;
        }
    });
    return '<a href="javascrit:;" onclick="glTdzConfirm(this,\'' + needConfirm + '\');return false">确定</a>';
}

function generate() {
    alertSize = 0;
    confirmSize = 0;
    lwsize =0;
    lwshxxData =[];
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {};
        var tpl = tsxxTpl.innerHTML, view = document.getElementById("tsxx");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
    })
}

//查看项目信息
function openXmxx(xmid,gzlslid) {
    if(isNotBlank(gzlslid)){
        window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + gzlslid);
    }else {
        getReturnData("/slym/xm/xx", {xmid: xmid}, 'GET', function (data) {
            //项目来源为1,不动产登记数据
            if (isNotBlank(data.xmly) && data.xmly == "1") {
                window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + data.gzlslid);
            } else {
                window.open('/realestate-register-ui/view/lsgx/new-page.html?xmid=' + data.xmid + '&processInsId=' + data.gzlslid);
            }
        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
    }

}

//查看权利信息
function openQlxx(xmid) {
    getReturnData("/slym/xm/xx", {xmid: xmid}, 'GET', function (data) {
        var qlxxym = getQlxxYm(data.qllx, data.bdcdyfwlx);
        window.open('/realestate-register-ui/view/qlxx/' + qlxxym + '.html?xmid=' + xmid + "&readOnly=true&isCrossOri=false");
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);

}

//关联土地证提示是否注销
function glTdzConfirm(elem, needConfirm) {
    var bdcWlSlXmLsgxDO ={};
    if(needConfirm){
        layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: "提示",
            area: ['320px'],
            content: "是否需要注销？",
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function (index, layero) {
                // 判断土地证的权利
                var flag = yztdzQl();
                if(flag){
                    showAlertDialog("土地证包含限制权利，不能注销！")
                }else{
                    bdcWlSlXmLsgxDO.zxyql =1;
                    glTdz(elem,bdcWlSlXmLsgxDO);
                    layer.close(index);
                }
            },
            btn2: function (index, layero) {
                bdcWlSlXmLsgxDO.zxyql =0;
                glTdz(elem, bdcWlSlXmLsgxDO);
                layer.close(index);
            }
        });
    }else{
        bdcWlSlXmLsgxDO.zxyql =0;
        glTdz(elem, bdcWlSlXmLsgxDO);
    }
}

//关联土地证
function glTdz(elem, bdcWlSlXmLsgxDO) {
    var glTdzxx = JSON.parse(sessionStorage.glTdzxx);
    if(glTdzxx != null &&glTdzxx.length >0){
        for (var k = 0; k < glTdzxx.length; k++){
            var bdcdyh =glTdzxx[k].bdcdyh;
            var wlxmid =glTdzxx[k].wlxmid;
            bdcWlSlXmLsgxDO.yxmid =wlxmid;
            for (var i = 0; i < checkData.length; i++) {
                var selectData = checkData[i];
                if (selectData.bdcdyh === bdcdyh) {
                    var bdcWlSlXmLsgxDOList =selectData.bdcWlSlXmLsgxDOList;
                    if(selectData.bdcWlSlXmLsgxDOList ==null){
                        bdcWlSlXmLsgxDOList =[];
                    }
                    bdcWlSlXmLsgxDOList.push(bdcWlSlXmLsgxDO);
                    selectData.bdcWlSlXmLsgxDOList =bdcWlSlXmLsgxDOList;
                }
            }

        }

    }

    remove(elem,3);
}

/**
 * @param elem 当前元素信息
 * @param czlx 1.忽略，2.查看，3.确定，4.取消，5.是，6.否,7.忽略全部
 * @param yzlx cjyz:创建验证，当提示为创建验证的时候，点击忽略需要调用创建的方法
 * @param nocj 是否需要创建，为false不创建
 * @param sfxnhcj 是否虚拟号创建
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description
 * @date : 2019/12/16 15:28
 */
function remove(elem,czlx,yzlx, nocj,sfxnhcj,bdcdyhAndZgzid) {
    //点击忽略时增加日志记录，同步处理
    //增加具体操作类型
    var msg = czlxArr[czlx];
    var data = elem.parentElement.innerText.replace(/忽略/g, "").replace(/查看/g, "").replace(/确定/g, "").replace(/是/g, "").replace(/否/g, "");
    var cznr = data+" "+msg;

    // 只有忽略操作记录日志 其他不记录
    if(cznr.indexOf("忽略") != -1){
        //记录日志信息
        var bdcdyh ="";
        if(isNotBlank(bdcdyhAndZgzid)){
            bdcdyh =bdcdyhAndZgzid.split("|")[0];
        }
        var slbh = $.getUrlParam('slbh');
        if(!isNotBlank(slbh)){
            slbh =$("#sjbh").val();
        }
        saveDetailLog("YZHL", "不动产单元规则验证操作", bdcdyh,slbh,{"HLNR": JSON.stringify(cznr)});
    }
    addModel();
    var val = $('.layui-this').val();
    var isParent = false;
    if(val == ""){
        val = parent.$('.layui-this').val();
        isParent = true;
    }
    var tsxxInfo =$(elem).parents(".tsxx");
    if(tsxxInfo.length >0) {
        var tsxxID = tsxxInfo.attr("id");
        if (tsxxID === "alertInfo") {
            alertSize--;
        } else {
            confirmSize--;
        }
    } else {
        confirmSize--;
    }
    $(elem).parent().remove();
    //移除后判断是否需要一并移除相同子规则的提示信息
    if (gzyzHlxtZgz) {
        //根据name=gzid 找到所有的提示信息，点击remove
        var gzid = $(elem).attr("name");
        var yzdyh = $(elem).attr("yzdyh");
        if (isNotBlank(gzid)) {
            var elements = $("#confirmInfo").find('a[name="' + gzid + '"]');
            for (var i = 0; i < elements.length; i++) {
                //不包含当前单元号
                if (yzdyh !== $(elements[i]).attr("yzdyh")) {
                    removeSameTsxx(elements[i], 1, "", "", "", "");
                }
            }
        }
    }

    //在没有警告提示下创建
    if (alertSize === 0 && confirmSize === 0) {
        if (!nocj) {
            generate();
            if (warnLayer !== undefined) {
                layer.close(warnLayer);
            } else {
                layer.close(layer.index);
            }
            //如果传入忽略执行回调方法,以传的为准
            if(removeCallback){
                removeModal();
                removeCallback();
                return false;
            }

            // 创建验证 忽略后直接创建流程
            if(yzlx === "cjyz"){
                if(sfxnhcj) {
                    setTimeout(function () {
                        $.ajax({
                            url: getContextPath() + "/bdcGzyz/sfcjlc",
                            type: 'GET',
                            dataType: 'json',
                            async: false,
                            data: {gzldyid: processDefKey},
                            success: function (data) {
                                if (data) {
                                    if(isParent){
                                        parent.cshSelectedXxSingle(jbxxid,processDefKey,false,false);
                                    }else{
                                        cshSelectedXxSingle(jbxxid,processDefKey,false,false);
                                    }
                                } else {
                                    removeModal();
                                }
                            }, error: function (xhr, status, error) {
                                delAjaxErrorMsg(xhr)
                            }
                        });
                    },100)
                } else {
                    if(isParent){
                        parent.cshSelectedXxSingle(jbxxid,processDefKey,false,false);
                    }else{
                        cshSelectedXxSingle(jbxxid,processDefKey,false,false);
                    }
                }
                return;
            }

            if (val === 1) {
                removeBdcdy(elem);
            } else if (val === 2) {
                if(isParent){
                    parent.removeXm(elem);
                }else{
                    removeXm(elem);
                }

            } else if (val === 3) {
                removeCf(elem);
            } else if (val === 4) {
                removeLjz(elem);
            } else if (val === 5) {
                if(eval("confirmSfzxyql")){
                    confirmSfzxyql();
                }else{
                    addWlzs(checkData);
                }

            }else{
                //其余统一命名回调函数
                if(eval("removeTsxxCallBack")){
                    eval("removeTsxxCallBack()");
                }else{
                    removeModal();
                }

            }
        } else {
            generate();
            removeModal();
        }
    } else {
        removeModal();
    }

}

function removeSameTsxx(elem, czlx, yzlx, nocj, sfxnhcj, bdcdyhAndZgzid) {
    //点击忽略时增加日志记录，同步处理
    //增加具体操作类型
    var msg = czlxArr[czlx];
    var data = elem.parentElement.innerText.replace(/忽略/g, "").replace(/查看/g, "").replace(/确定/g, "").replace(/是/g, "").replace(/否/g, "");
    var cznr = data + " " + msg;

    // 只有忽略操作记录日志 其他不记录
    if (cznr.indexOf("忽略") != -1) {
        //记录日志信息
        var bdcdyh = "";
        if (isNotBlank(bdcdyhAndZgzid)) {
            bdcdyh = bdcdyhAndZgzid.split("|")[0];
        }
        var slbh = $.getUrlParam('slbh');
        if (!isNotBlank(slbh)) {
            slbh = $("#sjbh").val();
        }
        saveDetailLog("YZHL", "不动产单元规则验证操作", bdcdyh, slbh, {"HLNR": JSON.stringify(cznr)});
    }
    var val = $('.layui-this').val();
    var isParent = false;
    if (val == "") {
        val = parent.$('.layui-this').val();
        isParent = true;
    }
    var tsxxInfo = $(elem).parents(".tsxx");
    if (tsxxInfo.length > 0) {
        var tsxxID = tsxxInfo.attr("id");
        if (tsxxID === "alertInfo") {
            alertSize--;
        } else {
            confirmSize--;
        }
    } else {
        confirmSize--;
    }
    $(elem).parent().remove();
}

//例外增加日志记录
function addLwLog(data) {
    var slbh = $.getUrlParam('slbh');
    if (!slbh || "" == slbh) {
        slbh = $("#sjbh").val();
    }
    getReturnData("/bdcGzyz/addLwLog", JSON.stringify({data: data, slbh: slbh}), "POST", function () {
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

function removeAlert(gzid, gzmc, bdcdyh, xzxx, elem) {
    if (alertSize > lwsize) {
        ityzl_SHOW_WARN_LAYER("存在其他警告验证,不允许例外");
        return false;
    } else if (confirmSize > 0) {
        ityzl_SHOW_WARN_LAYER("请先忽略其他提示验证,再进行例外");
        return false;
    }
    // 例外原因
    $("#exceptionReason").val('');
    var htmlStr = '<div id="exception-reason" class="bdc-layer-textarea">\n' +
        '        <form class="layui-form" action="">\n' +
        '            <div class="layui-form-item pf-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span class="required-span"><sub>*</sub></span>例外原因</label>\n' +
        '                    <div class="layui-input-inline bdc-end-time-box">\n' +
        '                        <textarea name="exceptionReason" id="exceptionReason" placeholder="请输入内容" class="layui-textarea"></textarea>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </form>\n' +
        '    </div>';
    layer.open({
        title: '例外原因',
        type: 1,
        area: ['430px'],
        btn: ['确定', '取消'],
        content: htmlStr,
        yes: function(index, layero) {
            var lwyy = $("#exceptionReason").val();
            if (isNullOrEmpty(lwyy)) {
                layer.msg('请输入例外原因!');
                return false;
            }
            // 增加规则例外审核信息
            var slbh = $.getUrlParam('slbh');
            if(!slbh || "" == slbh){
                slbh = $("#sjbh").val();
            }
            addModel();
            var elemInfo = elem.parentElement.innerText.replace(/例外/g, "").replace(/查看/g, "");
            addLwLog(elemInfo);
            try {
                var flag =addShxx(gzid, gzmc, bdcdyh, xzxx, lwyy, null);
                if(flag) {
                    removeYzTsxx(elem, "lw", index);
                }

            } catch (e) {
                removeModal();
            }
            layer.close(index);
        },
        btn2: function(index, layero){
            layer.close(index);
        }
    });
}

/**
 * 移除提示信息
 * elem 提示信息元素
 * yzlx 验证类型 lw:例外
 */
function removeYzTsxx(elem,yzlx,layerIndex){
    var val = $('.layui-this').val();
    $(elem).parent().remove();
    alertSize--;
    if(yzlx =="lw") {
        lwsize--;
    }
    //在没有警告提示下创建
    if (alertSize === 0 && confirmSize === 0) {
        if(warnLayer != undefined) {
            layer.close(warnLayer);
        }else {
            layer.close(layer.index);
        }
        generate();
        layer.close(layerIndex);
        //如果传入忽略执行回调方法,以传的为准
        if(removeCallback){
            removeModal();
            removeCallback();
            return false;
        }

        if (val === 1) {
            removeBdcdy(elem);
        } else if (val === 2) {
            removeXm(elem);
        } else if (val === 3) {
            removeCf(elem);
        } else if (val === 4) {
            removeLjz(elem);
        } else if(val === 5) {
            if(eval("confirmSfzxyql")){
                confirmSfzxyql();
            }else{
                addWlzs(checkData);
            }
        } else{
            //其余统一命名回调函数
            if(eval("removeTsxxCallBack")){
                eval("removeTsxxCallBack()");
            }else{
                removeModal();
            }

        }
    } else {
        removeModal();
    }
    removeModal();

}

function addShxx(gzid, gzmc, bdcdyh, xzxx, lwyy, crzl) {
    var flag =true;
    var xmids = xzxx.split(',');
    var zl;
    if (crzl) {
        zl = crzl;
    }
    var bdcqzh;
    if (checkData && checkData.length > 0) {
        for (var i = 0; i < checkData.length; i++) {
            var selectData = checkData[i];
            if (bdcdyh === selectData.bdcdyh) {
                zl = selectData.zl;
                bdcqzh = selectData.bdcqzh;
                break;
            }
        }
    }

    var sjbh = $.getUrlParam('slbh');
    if(!sjbh || "" == sjbh){
        sjbh = $("#sjbh").val();
    }

    // 循环限制信息
    var addlwshxxData=[];
    for (var i = 0; i < xmids.length; i++) {
        var addlwshxx={};
        addlwshxx.xmid=xmids[i];
        addlwshxx.gzid=gzid;
        addlwshxx.gzmc=gzmc;
        addlwshxx.bdcdyh=bdcdyh;
        addlwshxx.zl=zl;
        addlwshxx.bdcqzh=bdcqzh;
        addlwshxxData.push(addlwshxx);

    }
    $.ajax({
        url: getContextPath() + '/bdcGzyz/addShxxDataPl?slbh=' + sjbh + '&lwyy=' + encodeURI(lwyy),
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        async: false,
        data: JSON.stringify(addlwshxxData),
        success: function (data) {
            if(data &&isNotBlank(data.slbh)) {
                saveGzlwJbxx(data.slbh);
            }
        },
        error:function (error){
            delAjaxErrorMsg(error);
            flag= false;
        }
    });
    return flag;
}

function addShxxDataAll(addlwshxxData, lwyy) {
    var flag =true;
    if(addlwshxxData !=null &&addlwshxxData.length >0){
        var sjbh = $.getUrlParam('slbh');
        if(!sjbh || "" == sjbh){
            sjbh = $("#sjbh").val();
        }
        if (checkData && checkData.length > 0) {
            for (var i = 0; i < addlwshxxData.length; i++) {
                var lwshxx = addlwshxxData[i];
                for (var j = 0; j < checkData.length; j++) {
                    var selectData = checkData[j];
                    if (lwshxx.bdcdyh === selectData.bdcdyh) {
                        lwshxx.zl = selectData.zl;
                        lwshxx.bdcqzh = selectData.bdcqzh;
                        break;
                    }
                }
            }
        }
        $.ajax({
            url: getContextPath() + '/bdcGzyz/addShxxDataPl?slbh=' + sjbh + '&lwyy=' + encodeURI(lwyy),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            async: false,
            data: JSON.stringify(addlwshxxData),
            success: function (data) {
                if(data &&isNotBlank(data.slbh)) {
                    saveGzlwJbxx(data.slbh);
                }
            },
            error:function (error){
                delAjaxErrorMsg(error);
                flag= false;
            }
        });

    }
    return flag;
}

function saveGzlwJbxx(slbh){
    var processDefKey = getQueryString("processDefKey");
    var jbxxid = getQueryString("jbxxid");
    $.ajax({
        url: getContextPath() + '/slxxcsh/gzlw/jbxx',
        type: 'GET',
        dataType: 'json',
        data: {jbxxid: jbxxid, gzldyid: processDefKey,slbh:slbh},
        success: function (data) {
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

//规则验证提示框
function gzyztsxx() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery,
            layer = layui.layer;
        warnLayer = layer.open({
            type: 1,
            title: '提示信息',
            skin: 'bdc-tips-right',
            // closeBtn: 0, //不显示关闭按钮
            shade: [0],
            area: ['600px'],
            offset: 'r', //右下角弹出
            time: 5000000, //2秒后自动关闭
            anim: 2,
            content: $('#tsxx').html(),
            success: function () {
                $('.bdc-tips-right .bdc-right-tips-box .layui-icon-close').on('click', function () {
                    layer.close(warnLayer);
                    generate();
                })
            },
            cancel: function () {
                layer.close(warnLayer);
                generate();
            }
        });
    });

}

//忽略增加日志记录
function addRemoveLog(data,bdcdyh) {
    getReturnData("/bdcGzyz/addIgnoreLog", JSON.stringify({data: data,gzldyid:processDefKey,bdcdyh:bdcdyh}), "POST", function () {
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

// 保存记录操作信息
function saveDetailLog(logType, viewTypeName,bdcdyh,slbh, data){
    var json = JSON.parse(JSON.stringify(data));
    json.logType = logType;
    json.viewTypeName = viewTypeName;
    json.bdcdyh = bdcdyh;
    json.viewType = "accept-ui";
    json.slbh = slbh;
    json.gzldyid = processDefKey;
    saveLogInfo(json);
}

//忽略全部
function removeAll(elem) {

    //点击忽略时增加日志记录，同步处理
    var msg = czlxArr[7];
    var data = elem.nextElementSibling.innerText.replace(/忽略/g, "").replace(/查看/g, "");
    var cznr = data+" "+msg;
    // 只有忽略才记录日志，其他操作不记录
    var slbh = $.getUrlParam('slbh');
    if(!isNotBlank(slbh)){
        slbh =$("#sjbh").val();
    }
    if(cznr.indexOf("忽略") != -1){
        saveDetailLog("YZHL", "不动产单元规则验证操作", "",slbh,{"HLNR": JSON.stringify(cznr)});
    }

    var val = $('.layui-this').val();
    var isParent = false;
    if(val == ""){
        val = parent.$('.layui-this').val();
        isParent = true;
    }
    //在没有警告提示下创建
    if(warnLayer != undefined) {
        layer.close(warnLayer);
    }else {
        layer.close(layer.index);
    }
    generate();
    //如果传入忽略执行回调方法,以传的为准
    if(removeCallback){
        removeModal();
        removeCallback();
        return false;
    }
    addModel();
    if($(elem).parent().find('#confirmInfo').find('a').attr('yzlx') === "cjyz"){
        if($(elem).parent().find('#confirmInfo').find('a').attr('sfxnhcj')){
            setTimeout(function () {
                $.ajax({
                    url: getContextPath() + "/bdcGzyz/sfcjlc",
                    type: 'GET',
                    dataType: 'json',
                    async: false,
                    data: {gzldyid: processDefKey},
                    success: function (data) {
                        if (data) {
                            if(isParent){
                                parent.cshSelectedXxSingle(jbxxid,processDefKey,false,false);
                            }else{
                                cshSelectedXxSingle(jbxxid,processDefKey,false,false);
                            }
                        } else {
                            removeModal();
                        }
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
            },20);
        } else {
            if(isParent){
                parent.cshSelectedXxSingle(jbxxid,processDefKey,false,false);
            }else{
                cshSelectedXxSingle(jbxxid,processDefKey,false,false);
            }
        }
        return;
    }
    if (val === 1) {
        removeBdcdy();
    } else if (val === 2) {
        if(isParent){
            parent.removeXm();
        }else{
            removeXm();
        }
    } else if (val === 3) {
        removeCf();
    } else if (val === 4) {
        removeLjz();
    }else if (val === 5) {
        if(eval("confirmSfzxyql")){
            confirmSfzxyql();
        }else{
            addWlzs(checkData);
        }
    } else{
        //其余统一命名回调函数
        if(eval("removeTsxxCallBack")){
            eval("removeTsxxCallBack()");
        }else{
            removeModal();
        }

    }
}

//全部例外
function lwAll(elem) {
    // 例外原因
    $("#exceptionReason").val('');
    var htmlStr = '<div id="exception-reason" class="bdc-layer-textarea">\n' +
        '        <form class="layui-form" action="">\n' +
        '            <div class="layui-form-item pf-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span class="required-span"><sub>*</sub></span>例外原因</label>\n' +
        '                    <div class="layui-input-inline bdc-end-time-box">\n' +
        '                        <textarea name="exceptionReason" id="exceptionReason" placeholder="请输入内容" class="layui-textarea"></textarea>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </form>\n' +
        '    </div>';
    layer.open({
        title: '例外原因',
        type: 1,
        area: ['430px'],
        btn: ['确定', '取消'],
        content: htmlStr,
        yes: function(index, layero) {
            var lwyy = $("#exceptionReason").val();
            if (isNullOrEmpty(lwyy)) {
                layer.msg('请输入例外原因!');
                return false;
            }
            // 增加规则例外审核信息
            var slbh = $.getUrlParam('slbh');
            if(!slbh || "" == slbh){
                slbh = $("#sjbh").val();
            }

            addModel();
            //记录日志
            var msg = czlxArr[8];
            var data = $(".bdc-lwtsxx-p").text().replace(/例外/g, "").replace(/查看/g, "");
            var cznr = data+" "+msg;
            addLwLog(cznr);
            try {
                if(lwshxxData ==null ||lwshxxData.length ===0){
                    ityzl_SHOW_WARN_LAYER("未获取到例外数据");
                    removeModal();
                    return false;
                }
                var flag =addShxxDataAll(lwshxxData, lwyy);
                if(flag) {
                    var val = $('.layui-this').val();
                    $(".bdc-lwtsxx-p").remove();
                    alertSize = alertSize - lwsize;
                    lwsize = 0;
                    //在没有警告提示下创建
                    if (alertSize === 0 && confirmSize === 0) {
                        if (warnLayer != undefined) {
                            layer.close(warnLayer);
                        } else {
                            layer.close(layer.index);
                        }
                        generate();
                        layer.close(index);
                        //如果传入忽略执行回调方法,以传的为准
                        if (removeCallback) {
                            removeModal();
                            removeCallback();
                            return false;
                        }
                        if (val === 1) {
                            removeBdcdy(elem);
                        } else if (val === 2) {
                            removeXm(elem);
                        } else if (val === 3) {
                            removeCf(elem);
                        } else if (val === 4) {
                            removeLjz(elem);
                        } else if (val === 5) {
                            if (eval("confirmSfzxyql")) {
                                confirmSfzxyql();
                            } else {
                                addWlzs(checkData);
                            }
                        } else {
                            //其余统一命名回调函数
                            if (eval("removeTsxxCallBack")) {
                                eval("removeTsxxCallBack()");
                            } else {
                                removeModal();
                            }

                        }
                    } else {
                        removeModal();
                    }
                    removeModal();
                    //存在提示一并忽略
                    if (confirmSize > 0) {
                        removeAll(elem);
                    }
                }
            } catch (e) {
                removeModal();
            }
            layer.close(index);
        },
        btn2: function(index, layero){
            layer.close(index);
        }
    });
}

/**
 * wlxmid 外联项目ID(多个用逗号隔开)
 * elem 提示信息
 * wlYg 是否外联预告
 * 外联证书
 */
function wlzs(elem, bdcdyhAndXmids,wlYg) {
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        var bdcWlSlXmLsgxDOList =selectData.bdcWlSlXmLsgxDOList;
        if(selectData.bdcWlSlXmLsgxDOList ==null){
            bdcWlSlXmLsgxDOList =[];
        }
        var bdcSlYgDTOS =selectData.bdcSlYgDTOS;
        if(wlYg &&selectData.bdcSlYgDTOS ==null){
            bdcSlYgDTOS =[];
        }
        $.each(bdcdyhAndXmids.split(","), function (index, bdcdyhAndXmid) {
            var splitArry = bdcdyhAndXmid.split("|");
            var wlxmid = splitArry[0];
            var bdcdyh = splitArry[1];
            var ygdjzl = splitArry[2];
            if (selectData.bdcdyh === bdcdyh ||!isNotBlank(bdcdyh)) {
                if(wlYg &&isNotBlank(ygdjzl)) {
                    //外联预告需要处理预告权利人等
                    var bdcSlYgDTO ={};
                    bdcSlYgDTO.xmid =wlxmid;
                    bdcSlYgDTO.ygdjzl =ygdjzl;
                    bdcSlYgDTOS.push(bdcSlYgDTO);
                }else {
                    var bdcWlSlXmLsgxDO ={};
                    bdcWlSlXmLsgxDO.yxmid = wlxmid;
                    bdcWlSlXmLsgxDOList.push(bdcWlSlXmLsgxDO);
                }
            }

        });
        selectData.bdcWlSlXmLsgxDOList =bdcWlSlXmLsgxDOList;
        selectData.bdcSlYgDTOS =bdcSlYgDTOS;

    }
    remove(elem,3);
}

/**
 * fcjyBaxxJson 房产交易备案信息对象JSON字符串
 * elem 提示信息
 * 添加交易信息
 */
function addJyxx(elem, bdcdyh) {
    var getTest = JSON.parse(sessionStorage.warnSurnMsg);
    var fcjyBaxxDTO = getTest[bdcdyh];
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        if (selectData.bdcdyh === bdcdyh) {
            selectData.fcjyBaxxDTO = fcjyBaxxDTO;
        }
    }

    remove(elem,3);
}

/**
 * xmids 项目ID(多个用逗号隔开)
 * elem 提示信息
 * 添加查封项目
 */
function addCfXm(elem, xmids) {
    var xmidArr = [];
    for (var i = 0; i < checkData.length; i++) {
        if (isNotBlank(checkData[i].xmid)) {
            xmidArr.push(checkData[i].xmid);
        }
    }
    var cxObj = {};
    cxObj.xmid = xmids;
    getReturnData("/bdcdyh/listCfByPageJson?loadpage=false", cxObj, "GET", function (data) {
        if (data && data.length > 0) {
            $.each(data, function (key, value) {
                if (xmidArr.indexOf(value.xmid) < 0) {
                    checkData.push(value);
                }
            });
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    remove(elem,3);
}

/**
 * @param elem 当前提示信息元素
 * @param bdcdyhAndXmid 项目ID和不动产单元号采用"|"分隔，例子（0TBTND4M24NQGCNW|340111381003GB00962F00090055）
 * @author yaoyi@gtmap.com.cn
 * @description <p>不动产单元页面获取业务数据缺失，通过不动产单元号调用listXmByPageJson接口，补足获取所需xmid等业务字段。</p>
 *              <p>获取到的项目数据可能存在多条，采用xmid进行匹配，获取对应的项目数据。</p>
 *
 */
function addBdcdyXm(elem, bdcdyhAndXmid) {
    var splitArry = bdcdyhAndXmid.split("|");
    var xmid = splitArry[0];
    var bdcdyh = splitArry[1];
    getReturnData('/bdcdyh/listXmByPageJson?loadpage=false', {bdcdyh : bdcdyh}, "GET", function (data) {
        if(data.length>0){
            $.each(data,function (index,xmxx) {
                if(xmid == xmxx.xmid){
                    for (var i = 0; i < checkData.length; i++) {
                        if(checkData[i].bdcdyh == bdcdyh){
                            checkData[i] = xmxx;
                        }
                    }
                }
            });

            addModel();
            $(elem).parent().remove();
            confirmSize--;
            if (alertSize === 0 && confirmSize === 0) {
                if(warnLayer != undefined) {
                    layer.close(warnLayer);
                }else {
                    layer.close(layer.index);
                }
                generate();
                //如果传入忽略执行回调方法,以传的为准
                if(removeCallback){
                    removeModal();
                    removeCallback();
                    return false;
                }
                removeXm(elem,3);
            }else{
                removeModal();
            }

        }else{
            showAlertDialog("添加失败，获取不动产单元项目信息失败！");
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
}

/*
* 添加一证多房信息
* */
function addYzdfData(elem,yzdfdata) {
    addModel();
    if(alertSize > 0) {
        showAlertDialog("存在其他限制信息,不可操作");
    } else {
        var selectdata = checkData;
        //属性大写转小写
        lowerPropertyNames(yzdfdata);
        $.each(selectdata, function (index, data) {
                    for(var i=0; i<yzdfdata.length;i++) {
                        if (yzdfdata[i].xmid !== data.xmid ) {
                            checkData.push(yzdfdata[i]);
                        }
                    }
        });
        remove(elem,3);
    }
    removeModal();
}

/**
 * 查询限制权利
 * @returns {boolean}
 */
function yztdzQl(){
    var xzQlFlag = false;
    var glTdzxx = JSON.parse(sessionStorage.glTdzxx);
    if(null != glTdzxx && glTdzxx.length >0){
        var xmids = [];
        $.each(glTdzxx, function(index, value){
            xmids.push(value.wlxmid);
        });
        $.ajax({
            url: getContextPath() + "/bdcdyh/plQueryXzQl?processDefKey=" + processDefKey,
            data:JSON.stringify(xmids),
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            async: false,
            success: function (data) {
                if (isNotBlank(data)) {
                    xzQlFlag = data;
                }
            },
            error: function (xhr, status, error) {
                xzQlFlag = false;
            }
        })
    }
    return xzQlFlag;
}

/**
 * 添加抵押项目（带抵押）
 * @returns
 */
function addDyxmData(elem,bdcdyh,dyxmids) {
    if(isNotBlank(dyxmids)) {
        for (var i = 0; i < checkData.length; i++) {
            var selectData = checkData[i];
            if (selectData.bdcdyh === bdcdyh) {
                selectData.dyxmidList = dyxmids.split(",");
            }
        }
    }
    remove(elem,3);
}

/**
 * 外联带抵押产权证书
 * @returns
 */
function wlZsWithdy(elem,bdcdyh){
    for (var i = 0; i < checkData.length; i++) {
        var selectData = checkData[i];
        if (selectData.bdcdyh === bdcdyh) {
            selectData.wlwithdyZs =1;
        }
    }
    remove(elem,3);

}