//字典列表
var zdList;
//地址栏获取数据
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var xmid = getQueryString("xmid");
var fwlx = getQueryString("fwlx");
// redis缓存key前缀
var redisKeyPrefix = "YCSL_SWXX_";
var $,laytpl,form,slbh;

layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    var element = layui.element;
    laytpl = layui.laytpl;
    var layer = layui.layer;

    var isSubmit = true;
    var isFirst = true;
    var verifyMsg = "必填项不能为空";
    form.verify({
        qlbl: function (value, item) {
            var gyfs = $(item).parents("tr").find("input[name='gyfs']").val();
            $(item).val(replaceBeforePointZero(value));
            var msg = checkQlbl(gyfs, value,true);
            if (isNotBlank(msg)) {
                $(item).addClass('layui-table-danger');
                isSubmit = false;
                verifyMsg = msg;
            }
        }
    });
    $(function () {
        addModel();
        //获取字典列表、加载基本信息、加载收件材料
        setTimeout(function () {
            try {
                $.when(loadButtonArea(),loadZdData(),loadSwxx()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
                return
            }
        }, 50);

        form.on("submit(saveData)", function (data) {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                isFirst = true;
                return false;
            } else {
                addModel();
                setTimeout(function () {
                    try {
                        $.when(saveZrzcfxx()).done(function () {
                            hxYwsj(processInsId);
                            removeModal();
                            ityzl_SHOW_SUCCESS_LAYER("保存成功");
                        });
                    } catch (e) {
                        removeModal();
                        if (e.message) {
                            showAlertDialog(e.message);
                        } else {
                            delAjaxErrorMsg(e);
                        }
                        return;
                    }
                }, 10);
                return false;
            }
        });

        $('.bdc-form-div').on('focus','.layui-input',function () {
            if($(this).hasClass('layui-form-danger')){
                $(this).removeClass('layui-form-danger');
            }
            if($(this).hasClass('layui-table-danger')){
                $(this).removeClass('layui-table-danger');
            }

            if($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')){
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
            if($(this).parents('.layui-form-select').siblings().hasClass('layui-table-danger')){
                $(this).parents('.layui-form-select').siblings().removeClass('layui-table-danger');
            }
        });
        $('.bdc-form-div').on('click','.xm-input',function () {
            if($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')){
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
            if($(this).parent().siblings('.xm-hide-input').hasClass('layui-table-danger')){
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-table-danger');
            }
        });
    });

    //监听家庭成员 详细 单击
    $('.form-margin-area').on('click','[name="jtcyxx"]',function(){
        var $qlrbasic =$(this).parents("tr");
        var sqrid=$qlrbasic.find("input[name='sqrid']").val();
        var sqrlb=$qlrbasic.find("input[name='sqrlb']").val();
        var hyzk =$qlrbasic.find("[name='hyzk']").val();
        var xmid  = $(this).data("xmid");
        if(hyzk == "已婚"){
            openJtcyYm(sqrid,sqrlb, xmid);
        }else{
            ityzl_SHOW_WARN_LAYER("无配偶子女信息，请检查婚姻状况！");
        }
    });

    //按钮加载
    function loadButtonArea() {
        getSwxxCzCache().done(function(czztxx){
            var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
            //渲染数据
            laytpl(tpl).render({czzt: czztxx}, function (html) {
                view.innerHTML = html;
            });
            form.render();
            getStateById(readOnly, formStateId, "ycym");
            disabledAddFa();

            // 1.推送税务信息
            $("#tsSwxx").click(function () {
                tsSwxx();
            });
            // 2.状态获取
            $("#tsShzt").click(function () {
                tsShzt();
            });
            // 3.申报
            $("#hqSbxx").click(function () {
                hqSbxx();
            });
            // 4.确认申报
            $("#qrsb").click(function () {
                qrsb();
            });
            // 5.获取计税信息
            $("#hqJsxx").click(function () {
                hqJsxx();
            });
            // 6.获取缴款信息
            $("#hqJkxx").click(function () {
                hqJkxx();
            });
            // 7.获取缴款码
            $("#hqJkewm").click(function () {
                hqJkewm();
            });
            // 8.获取缴款状态
            $("#hqJkzt").click(function () {
                hqJkzt();
            });
            // 9.获取凭证
            $("#hqWspz").click(function () {
                hqWspz();
            });
        });
    }

    function loadZdData() {
        $.ajax({
            url: getContextPath() + "/bdczd",
            type: 'POST',
            dataType: 'json',
            async: false,
            success: function (data) {
                if (isNotBlank(data)) {
                    zdList = data;
                }
            }
        });
    }

    window.loadSwxx =  function (){
        if(isNullOrEmpty(xmid)){
            addModel();
            getReturnData("/slym/xm/listBdcXm", {gzlslid: processInsId,xmid:xmid}, 'GET', function (xmxxList) {
                   removeModal();
                   if(xmxxList &&xmxxList.length >0) {
                       xmid = xmxxList[0].xmid;
                       slbh = xmxxList[0].slbh;
                       $("#sjbh").val(slbh);
                   }else{
                       layer.alert("当前未生成项目数据，请确认！");
                       return;
                   }
            }, function (err) {
                removeModal();
                delAjaxErrorMsg(xhr);
            },false);
        }
        getReturnData( "/ycsl", {gzlslid: processInsId, xmid: xmid}, 'GET', function (data) {
            removeModal();
            if(isNotBlank(data)){
                generateSsxxZh(data);
            }
        }, function (err) {
            console.log(err);
        });
    }


    //加载转入方税务信息
    window.generateSwxx = function(bdcSlSwxxList, sqrlb) {
        var swxx = null;
        if(isNotBlank(bdcSlSwxxList)){
            swxx =  bdcSlSwxxList[0];
        }
        var json =  {
            bdcSlSwxxList: swxx,
            zd: zdList,
            sqrlb: sqrlb
        };
        var tpl, view;
            tpl = swxxTpl.innerHTML;
        if (sqrlb === 1) {
            view = document.getElementById('ycslzrfswxx');
        } else if (sqrlb === 2) {
            view = document.getElementById('ycslzcfswxx');
        }
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
    }
});

// 组织一窗税收信息到组合页面
function generateSsxxZh(data) {
    var hsxx = {}, qlrjsewm = "", ywrjsewm ="";
    if(null != data.bdcZrfSwxxList){
        hsxx = data.bdcZrfSwxxList[0].bdcSlHsxxDO;
        if(isNotBlank(data.bdcZrfSwxxList[0].bdcSlHsxxDO.jsewm)){
            qlrjsewm = signImageUrl + qlrjsewm;
        }
    }
    if(null != data.bdcZcfSwxxList){
        if(isNotBlank(data.bdcZcfSwxxList[0].bdcSlHsxxDO.jsewm)){
            ywrjsewm = signImageUrl + ywrjsewm;
        }
    }
    if($.isEmptyObject(hsxx)){
        // 生成核税信息
        addHsxx(data.bdcSlJyxxDO.xmid);
    }
    var fwxx = {};
    if (data.bdcSlFwxxDO) {
        fwxx = data.bdcSlFwxxDO;
    }
    var jyxx = {};
    if(data.bdcSlJyxxDO){
        jyxx =data.bdcSlJyxxDO;
    }
    var json ={
        zd: zdList,
        hsxx: hsxx,
        fwxx: fwxx,
        jyxx: jyxx,
        qlrjsewm: qlrjsewm,
        ywrjsewm: ywrjsewm
    };
    var tpl = ssxxTpl.innerHTML;
    var view = document.getElementById('ycssxx');
    //渲染数据
    laytpl(tpl).render(json, function(html) {
        view.innerHTML = html;
    });

    //加载卖方信息
    generateCmfxx(data.bdcSlZcfDTOList, data.bdcSlJyxxDO.xmid);
    //加载买方信息
    generateMsfxx(data.bdcSlZrfDTOList, data.bdcSlJyxxDO.xmid);
    //加载转入方税务信息
    generateSwxx(data.bdcZrfSwxxList, 1);
    //加载转出方税务信息
    generateSwxx(data.bdcZcfSwxxList, 2);
    form.render();
    renderDate(form, "");
    renderSelect();
    if(isNotBlank(qlrjsewm)){
        $(".qlrjsewm").click(function () {
            var _this = $(this);//将当前的pimg元素作为_this传入函数
            imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
        });
    }
    if(isNotBlank(ywrjsewm)){
        $(".ywrjsewm").click(function () {
            var _this = $(this);//将当前的pimg元素作为_this传入函数
            imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
        });
    }
    getStateById(readOnly, formStateId, 'slymzh');
    disabledAddFa();
}

// 组织卖方信息到组合页面
function generateCmfxx(sqrList, xmid) {
    // 获取房屋类型
    getReturnData( "/ycsl/checkSpfLc", {gzlslid: processInsId}, 'GET', function (data) {
        if(isNotBlank(data)){
            var json ={
                sqrList:sqrList,
                zd: zdList,
                sqrlb:2,
                xmid: xmid
            };
            if(data ==="clf"){
                //二手房卖方模板
                var tpl = zrfzcfxxTpl.innerHTML;

            }else {
                //商品房卖方模板
                var tpl = kfsxxTpl.innerHTML;
            }
            var view = document.getElementById('cmfxx');
            //渲染数据
            laytpl(tpl).render(json, function(html) {
                view.innerHTML = html;
            });
            form.render();
        }
    }, function (err) {
        console.log(err);
    });

}

// 组织买方信息到组合页面
function generateMsfxx(sqrList, xmid) {
    var json ={
        sqrList:sqrList,
        zd: zdList,
        sqrlb:1,
        xmid : xmid
    };
    var tpl = zrfzcfxxTpl.innerHTML;
    var view = document.getElementById('msfxx');
    //渲染数据
    laytpl(tpl).render(json, function(html) {
        view.innerHTML = html;
    });
}

function addHsxx(xmid){
    $.ajax({
        url: getContextPath() + "/slym/sw/hsxx",
        type: 'POST',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({xmid: xmid,sqrlb:1}),
        success: function (data) {
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}
function fmtHsSbzt(zt){
    var mc = "";
    if (isNotBlank(zdList.swsbzt)) {
        for (var i = 0; i < zdList.swsbzt.length; i++) {
            var zd = zdList.swsbzt[i];
            // if (zt == zd.DM && zd.LX == "2") {
            if (zt == zd.DM) {
                mc = zd.MC;
                break;
            }
        }
    }
    return mc;
}
function fmtWszt(ytsswzt, wszt){
    var zt = "";
    if(isNotBlank(ytsswzt)){
        if(ytsswzt == "1"){
            zt = "已提交税务";
        }
    }
    if(isNotBlank(wszt)){
        if(wszt == "1"){
            zt =  "已缴税";
        }else if (wszt == "10"){
            zt = "缴款完成";
        } else{
            zt = "未缴税";
        }
    }
    return zt;
}

//字典项名称转代码
function changeMcToDm(mc, zdList) {
    var dm = "";
    if (isNotBlank(zdList)) {
        for (var i = 0; i < zdList.length; i++) {
            var zd = zdList[i];
            if (mc == zd.MC) {
                dm = zd.DM;
                break;
            }
        }
    }
    return dm;
}
//字典项名称转街道代码
function changeMcToJddm(mc, zdList) {
    var dm = "";
    if (isNotBlank(zdList)) {
        for (var i = 0; i < zdList.length; i++) {
            var zd = zdList[i];
            if (mc == zd.MC) {
                dm = zd.JDDM;
                break;
            }
        }
    }
    return dm;
}

//加载基本信息模块（入口）
function loadJbxx() {
    getReturnData("/slym/xm/queryBdcSlJbxxByGzlslid", {processInsId: processInsId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            getReturnData("/gwc/list/bdcslxm", {jbxxid: data.jbxxid}, 'GET', function (item) {
                if (isNotBlank(item)) {
                    xmid = item[0].xmid;
                }
            }, function (err) {
                delAjaxErrorMsg(err);
            },false);
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    },false);
}

//民政婚姻信息比对
function compareHyxx(elem){
    addModel();
    var slbh =$("#sjbh").val();
    var $qlrbasic =$(elem).parents("tr");
    var qlrmc =$qlrbasic.find("#zrzcf-sqrmc").val();
    var qlrzjh=$qlrbasic.find("#zrzcf-zjh").val();
    var sqrid=$qlrbasic.find("input[name='sqrid']").val();
    var hyzk=$qlrbasic.find("#zrzcf-hyzk").val();
    if(!isNotBlank(qlrmc) ||!isNotBlank(qlrzjh) ||!isNotBlank(sqrid) ||!isNotBlank(hyzk)){
        ityzl_SHOW_WARN_LAYER("申请人名称或证件号或婚姻状况不能为空！");
    }
    getReturnData("/slym/jtcy/compareHyxx",{slbh: slbh, xmid:xmid, sqrid:sqrid, qlrmc:qlrmc, qlrzjh:qlrzjh, hyzk:hyzk},"GET",function (data) {
        removeModal();
        if (data.code ==="0000") {
            ityzl_SHOW_SUCCESS_LAYER(data.msg);
            $(elem).removeClass("bdc-secondary-btn").addClass("bdc-major-btn");
            $qlrbasic.find("input[name='hyxxbdjg']").val("1");
        }else if (data.code ==="0002"){
            //提示
            var confirmIndex = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: '提示信息',
                area: ['320px'],
                content: data.msg,
                btn: ['通过', '取消'],
                btnAlign: 'c',
                yes: function () {
                    //确定操作
                    var sqr ={};
                    sqr.sqrid =sqrid;
                    sqr.hyxxbdjg =1;
                    getReturnData("/slym/sqr",JSON.stringify(sqr),"PATCH",function () {
                        $(elem).removeClass("bdc-secondary-btn").addClass("bdc-major-btn");
                        $qlrbasic.find("input[name='hyxxbdjg']").val("1");
                    },function (error) {
                        $qlrbasic.find("input[name='hyxxbdjg']").val("0");
                        delAjaxErrorMsg(error);
                    });
                    layer.close(confirmIndex);

                },
                btn2: function () {
                    //取消
                    layer.close(confirmIndex);
                    $qlrbasic.find("input[name='hyxxbdjg']").val("0")
                }
            });
        } else {
            //警告
            ityzl_SHOW_WARN_LAYER(data.msg);
            $qlrbasic.find("input[name='hyxxbdjg']").val("0")
        }

    },function (error) {
        delAjaxErrorMsg(error);
    });
}

//打开家庭成员页面
function openJtcyYm(sqrid, sqrlb, xmid) {
    var url = getContextPath() + "/changzhou/slym/jtcy.html?sqrid="+sqrid+"&formStateId="+ formStateId + "&sqrlb="+sqrlb + "&xmid=" + xmid;
    layer.open({
        title: '配偶子女信息',
        type: 2,
        area: ['1300px','600px'],
        content: url,
        cancel: function(){
        },
        success: function () {
        }
    });
}


// 1.推送税务信息
function tsSwxx(){
    addModel();
    //判断流程类型为存量房、商品房
    getReturnData( "/ycsl/checkSpfLc", {gzlslid: processInsId}, 'GET', function (data) {
        if(isNotBlank(data)){
            var beanName;
            if(data ==="spf"){
                beanName = "sw_zlfrwts";
                fwlx = "1";
            }else{
                beanName = "sw_clfrwts";
                fwlx = "2";
            }
            //调用规则验证
            var bdcGzYzQO ={};
            bdcGzYzQO.zhbs = "tsSwxx";
            var gzyzParamMap ={};
            gzyzParamMap.xmid = xmid;
            // gzyzParamMap.htbh =$("#htbh").val();
            gzyzParamMap.gzlslid = processInsId;
            bdcGzYzQO.paramMap = gzyzParamMap;
            gzyzCommon(2, bdcGzYzQO,function (data) {
                console.log("验证通过");
                $.ajax({
                    url: getContextPath() + "/rest/v1.0/yrbj/tsJsxx/"+ processInsId +"/"+beanName,
                    type: "GET",
                    data: {xmid: xmid, fwlx: fwlx},
                    dataType: "json",
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        removeModal();
                        if(isNotBlank(data) && data.code == "1"){
                            ityzl_SHOW_SUCCESS_LAYER("推送成功");
                            setRedisCache("tsSwxx","true");
                        }else{
                            ityzl_SHOW_WARN_LAYER("推送失败：" + data.message);
                        }
                    },
                    error: function (xhr, status, error) {
                        removeModal();
                        delAjaxErrorMsg(xhr);
                    }
                });
            });
        }else{
            removeModal();
            ityzl_SHOW_WARN_LAYER("未查询到流程类型");
        }
    }, function (error) {
        delAjaxErrorMsg(error);
    });
}
// 2.推送审核状态
function tsShzt(){
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/sw/hqsbzt",
        type: "GET",
        dataType: 'json',
        contentType: "application/json",
        data: {gzlslid: processInsId, fwlx: fwlx},
        success: function (data) {
            removeModal();
            if (data && data.success === true){
                var hsxxList = data.data;
                $("#hszt").val(fmtHsSbzt(hsxxList[0].wszt));
                $("#thyy").val(hsxxList[0].thyy);
                ityzl_SHOW_SUCCESS_LAYER("状态获取成功");
                setRedisCache("tsShzt","true");
            }else{
                ityzl_SHOW_WARN_LAYER("状态获取失败");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

// 3.获取申报信息
function hqSbxx(){
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/sw/sbd",
        type: "GET",
        data: {gzlslid: processInsId},
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("获取申报信息成功");
            setRedisCache("hqSbxx","true");
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

// 4.确认申报
function qrsb(){
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/sw/sbdqr",
        type: "GET",
        data: {gzlslid: processInsId, fwlx: fwlx},
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            removeModal();
            if(isNotBlank(data) && data.code =="1"){
                ityzl_SHOW_SUCCESS_LAYER("确认申报成功");
                setRedisCache("qrsb","true");
            }else{
                ityzl_SHOW_SUCCESS_LAYER("确认申报失败");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

// 5.获取计税信息
function hqJsxx(){
    // 验证是否存在二维码，存在二维码则不允许获取计税信息
    var qlrjsewm = $(".qlrjsewm").attr("src");
    var ywrjsewm = $(".ywrjsewm").attr("src");
    if(isNotBlank(qlrjsewm) || isNotBlank(ywrjsewm)){
        layer.confirm('已生成二维码，是否重新获取计税信息。', {
            btn: ['是', '否']
        }, function (index, layero) {
            jsxx();
        }, function (index) {
            layer.close(index);
        });
    } else{
        jsxx();
    }

    function jsxx(){
        getReturnData( "/ycsl/checkSpfLc", {gzlslid: processInsId}, 'GET', function (data) {
            if(isNotBlank(data)){
                var url;
                if(data ==="spf"){
                    url = getContextPath() + "/slym/sw/zlfjsxx";
                    fwlx = "1";
                }else{
                    url = getContextPath() + "/slym/sw/clfjsxx";
                    fwlx = "2";
                }
                addModel();
                $.ajax({
                    url: url,
                    type: "GET",
                    data: {gzlslid: processInsId, fwlx: fwlx},
                    dataType: "json",
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        removeModal();
                        if(isNotBlank(data)){
                            ityzl_SHOW_SUCCESS_LAYER("获取计税信息成功");
                            setRedisCache("hqJsxx","true");
                            loadSwxx();
                        }else{
                            ityzl_SHOW_WARN_LAYER("获取计税信息失败");
                        }
                    },
                    error: function (xhr, status, error) {
                        removeModal();
                        delAjaxErrorMsg(xhr);
                    }
                });
            }else{
                ityzl_SHOW_WARN_LAYER("未查询到流程类型");
            }
        });
    }
}

// 6.获取缴款码
function hqJkewm(){
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/sw/jsewm",
        type: "GET",
        data: {gzlslid: processInsId, fwlx: fwlx},
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            removeModal();
            if (isNotBlank(data)) {
                setRedisCache("hqJkewm","true");

                var qlrjsewm,ywrjsewm;
                $.each(data,function(index,value){
                    if(value.qlrlb == "1"){
                        qlrjsewm = value.jsewm;
                    }
                    if(value.qlrlb == "2"){
                        ywrjsewm = value.jsewm;
                    }
                });

                if(isNotBlank(qlrjsewm)){
                    var qlrewmUrl = "data:image/png;base64," + qlrjsewm;
                    $(".qlrjsewm").attr('src', qlrewmUrl);
                    //点击二维码图片放大事件
                    $(".qlrjsewm").click(function () {
                        var _this = $(this);//将当前的pimg元素作为_this传入函数
                        imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
                    });
                }
                if(isNotBlank(ywrjsewm)){
                    var ywrewmUrl = "data:image/png;base64," + ywrjsewm;
                    $(".ywrjsewm").attr('src', ywrewmUrl);
                    //点击二维码图片放大事件
                    $(".ywrjsewm").click(function () {
                        var _this = $(this);//将当前的pimg元素作为_this传入函数
                        imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
                    });
                }
            }else{
                ityzl_SHOW_WARN_LAYER("获取缴款二维码失败");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

// 7.获取缴款状态
function hqJkzt(){
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/sw/jkzt",
        type: "GET",
        data: {gzlslid: processInsId, fwlx: fwlx},
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                ityzl_SHOW_SUCCESS_LAYER("获取缴款信息成功");
                if(data.jkzt == 0){
                    $(".wszt").val("10");
                    form.render();
                    setRedisCache("hqJkzt","true");
                }
            }else{
                ityzl_SHOW_WARN_LAYER("获取缴款状态失败");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

// 8.获取契税完税凭证
function hqWspz(){
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/sw/getQswspz",
        type: "GET",
        data: {gzlslid: processInsId, fwlx: fwlx,  beanName:"sw_qswspzhq", gxlx: "0"},
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                ityzl_SHOW_SUCCESS_LAYER("获取契税完税凭证成功");
                setRedisCache("hqWspz","true");
            }else{
                ityzl_SHOW_SUCCESS_LAYER("获取契税完税凭证失败");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

/**
* 点击图片放大功能
*/
function imgShow(outerdiv, innerdiv, bigimg, _this) {
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性

    /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function () {
        var windowW = $(window).width();//获取当前窗口宽度
        var windowH = $(window).height();//获取当前窗口高度
        var realWidth = this.width;//获取图片真实宽度
        var realHeight = this.height;//获取图片真实高度
        var imgWidth, imgHeight;
        imgWidth  = windowH-100;
        $(bigimg).css("width", imgWidth);//以最终的宽度对图片缩放

        var w = (windowW - imgWidth) / 2;//计算图片与窗口左边距
        var h = (windowH - imgWidth) / 2;//计算图片与窗口上边距
        var topHeight = $(".bdc-form-div").css("padding-top");
        $(innerdiv).css({"top": topHeight, "left": w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function () {//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}

function setRedisCache(key, value){
    var redisKey = redisKeyPrefix + processInsId;
    var map = {};
    map[key] = value;

    if(value == "true"){
        if($("#"+key).find("img").length == 0){
            $("#"+key).append('<img src=\"../../static/lib/bdcui/images/success-small.png\" alt=\"\">');
        }
    }
    $.ajax({
        url: getContextPath() + "/slym/sw/cz/cache/" + redisKey,
        type: "PUT",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(map),
        success: function (data) {
        },
        error: function (xhr, status, error) {
        }
    });
}

function getSwxxCzCache(){
    var deferred = $.Deferred();
    var redisKey = redisKeyPrefix + processInsId;
    $.ajax({
        url: getContextPath() + "/slym/sw/cz/cache/" + redisKey,
        type: "GET",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            removeModal();
            deferred.resolve(data);
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
            deferred.reject();
        }
    });
    return deferred;
}


/**
 * 保存一窗税收页签的买方卖方信息
 */
function saveZrzcfxx() {
    // 判断是否拥有 税收tab页面，与税收页面数据是否已经加载，然后在进行申请人数据的保存
    if($("#cmfxx").length > 0){ // 判断页面是否拥有税收页面,并已加载税收页面信息
        var sqrlist = [];
        if($(".zrzcf").length != 0){
            var zrfData = $(".zrzcf").serializeArray();
            var zrf= {};
            // 是否直属亲属交易，买方、卖方保持一致
            var sfzxqs = "";
            for (var zrfnum = 0; zrfnum < zrfData.length; zrfnum++) {
                var name = zrfData[zrfnum].name;
                zrf[name] = zrfData[zrfnum].value;
                // 以qlrid为每一组权利人的起始数据，作为分割依据
                if ((zrfnum + 1 < zrfData.length && zrfData[zrfnum + 1].name === 'sqrid') || zrfnum + 1 == zrfData.length) {
                    var bdcSlSqrxx = {};
                    bdcSlSqrxx.sqrid=zrf.sqrid;
                    bdcSlSqrxx.sbfwtc=zrf.sbfwtc;
                    bdcSlSqrxx.hyzk=zrf.hyzk;
                    bdcSlSqrxx.hyxxbdjg=zrf.hyxxbdjg;
                    bdcSlSqrxx.sqrlb=zrf.sqrlb;
                    bdcSlSqrxx.sqrmc=zrf.sqrmc;
                    bdcSlSqrxx.zjzl=zrf.zjzl;
                    bdcSlSqrxx.zjh=zrf.zjh;
                    bdcSlSqrxx.dh=zrf.dh;
                    bdcSlSqrxx.qlbl=zrf.qlbl;
                    if(zrf.sqrlb != "1"){ // 卖方需要记录满五唯一、满二、是否直接亲属交易信息
                        bdcSlSqrxx.jtmwwyzz=zrf.jtmwwyzz;
                        bdcSlSqrxx.sfgmmln=zrf.sfgmmln;
                        bdcSlSqrxx.sfzxqs=zrf.sfzxqs;
                        sfzxqs = zrf.sfzxqs;
                    }
                    sqrlist.push(bdcSlSqrxx);
                }
            }
            if(sqrlist.length>0){
                // 同步买方与卖方之间的sfzxqs是否直系亲属交易字段
                $.each(sqrlist, function(index, value){
                    value.sfzxqs = sfzxqs;
                });

                var url =  getContextPath() + "/slym/sqr/sqrxx/save?gzlslid=" + processInsId;
                $.ajax({
                    url: url,
                    type: 'PATCH',
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(sqrlist),
                    async: false,
                    success: function (data) {
                    },
                    error: function (e) {
                        delAjaxErrorMsg(e);
                    }
                });
            }
        }
        //保存房屋信息
        saveFwxx(".fwxx");

        // 保存交易信息
        saveJyxx(".jyxx");
    }
}

function saveFwxx(formClass) {
    var fwxxData = {};
    var fwxxArray = $(formClass);
    if (fwxxArray !== null && fwxxArray.length > 0) {
        fwxxArray.serializeArray().forEach(function (item, index) {
            fwxxData[item.name] = item.value;
        });
        var xqdm =$("#xqdm").val();
        if(xqdm) {
            fwxxData.xqdm=xqdm;
        }
        fwxxData.fwxxid = $(".fwxx-fwxxid").val();
        $.ajax({
            url: getContextPath() + "/ycsl/fwxx",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(fwxxData),
            success: function (data) {
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }
}

//交易信息保存(组合页面）
function saveJyxx(formClass) {
    var jyxxData = {};
    var jyxxArray = $(formClass);
    if (jyxxArray !== null && jyxxArray.length > 0) {
        jyxxArray.serializeArray().forEach(function (item, index) {
            jyxxData[item.name] = item.value;
        });
        jyxxData.jyxxid = $(".jyxx-jyxxid").val();
        getReturnData("/ycsl/htxx", JSON.stringify(jyxxData), 'PATCH', function (data) {
        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
    }
}

// 小区名称onblur事件，
function changeXqdm(ele){
    var xqmc = $(ele).val();
    if (isNotBlank(zdList) && isNotBlank(zdList.xqxx)) {
        $.each(zdList.xqxx,function(index, value){
            var mc = value.MC;
            if(xqmc.indexOf(mc)>-1){
                $("#xqdm").val(value.DM);
                $("#jddm").val(value.JDDM);
                return false;
            }
        });
    }
}

