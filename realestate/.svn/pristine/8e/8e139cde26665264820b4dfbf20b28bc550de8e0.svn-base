/**
 * @author "<a href="mailto:yaoyi@gtmap.cn>yaoyi</a>"
 * @version 1.0, 2020/6/4
 * @description 常州专用--受理表单操作公共JS
 */
//规则验证入参
var gzyzParamMap ={};
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
    table = layui.table;
    laytpl = layui.laytpl;
    laydate = layui.laydate;


    // 查询存量房合同信息
    $(document).on('click','#queryClfHtxx',function () {
        queryHtxx("clf");
    });

    // 查询商品房合同信息
    $(document).on('click','#querySpfHtxx',function () {
        queryHtxx("spf")
    });

    // 获取税票信息
    $(document).on('click','#getwsfp',function () {
        getWsfp();
    });

    // 推送税务信息
    $(document).on('click','.tsSwxx',function () {
       tsSwxx();
    });
});

function queryHtxx(fwlx){
    addModel();
    $.ajax({
        url: getContextPath()+"/ycsl/jyxx/queryHtbh",
        type: 'GET',
        data: {xmid: xmid},
        success: function (data) {
            removeModal();
            var url = getContextPath()+"/changzhou/slym/cxhtxx.html?xmid=" + xmid + "&fwlx=" + fwlx +"&htbh=" + data.jyhth +"&processInsId=" + processInsId;
            var htIndex =layer.open({
                type: 2,
                area: ['1150px', '575px'],
                fixed: false, //不固定
                title: "合同信息",
                content: url,
                btnAlign: "c"
            });
            layer.full(htIndex);
            form.render();
            resetSelectDisabledCss();
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });

}

function queryFcjyClfHtxx(fwlx, xmid) {
    addModel();
    $.ajax({
        url: getContextPath() + "/ycsl/jyxx/queryHtbh",
        type: 'GET',
        data: {xmid: xmid},
        success: function (data) {
            removeModal();
            var url = getContextPath() + "/changzhou/slym/cxhtxx.html?xmid=" + xmid + "&fwlx=" + fwlx + "&htbh=" + data.jyhth + "&processInsId=" + processInsId;
            var htIndex = layer.open({
                type: 2,
                area: ['1150px', '575px'],
                fixed: false, //不固定
                title: "合同信息",
                content: url,
                btnAlign: "c"
            });
            layer.full(htIndex);
            form.render();
            resetSelectDisabledCss();
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

/**
 * 获取完税发票
 */
function getWsfp() {
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/sw/getWsxx",
        type: 'GET',
        data: {gzlslid: processInsId},
        success: function (data) {
            removeModal();
            if (isNotBlank(data)) {
                if (data == "1") {
                    ityzl_SHOW_SUCCESS_LAYER("获取税票成功");
                    $("#wszt").val("已缴税");
                    //获取税票后会新增文件夹，上传文件中心，重新加载收件材料信息
                    loadSjcl();
                } else {
                    ityzl_SHOW_WARN_LAYER("未查询到完税信息");
                }
            }else{
                ityzl_SHOW_WARN_LAYER("未查询到完税信息");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

/**
 * 推送税务信息
 */
function tsSwxx(){
    addModel();
    //判断流程类型为存量房、商品房
    getReturnData( "/ycsl/checkSpfLc", {gzlslid: processInsId}, 'GET', function (data) {
        if(isNotBlank(data)){
            var beanName;
            if(data ==="clf"){
                beanName = "tsSwxxClf";
            }else{
                beanName = "tsSwxxSpf";
            }
            //调用规则验证
            var bdcGzYzQO ={};
            bdcGzYzQO.zhbs = "tsSwxx";
            gzyzParamMap.xmid =xmid;
            gzyzParamMap.htbh =$("#htbh").val();
            gzyzParamMap.gzlslid = processInsId;
            bdcGzYzQO.paramMap = gzyzParamMap;
            gzyzCommon(2,bdcGzYzQO,function (data) {
                console.log("验证通过");
                $.ajax({
                    url: getContextPath() + "/slym/sw/tsJsxx/"+processInsId+"/"+ beanName,
                    type: "GET",
                    data: {},
                    dataType: "json",
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        removeModal();
                        if(isNotBlank(data) && data.success == "1"){
                            ityzl_SHOW_SUCCESS_LAYER("推送成功");
                            $("#wszt").val("已提交税务");
                        }else{
                            ityzl_SHOW_WARN_LAYER("推送失败：" + data.msg);
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
    },function (err) {
        removeModal()
        console.log(err);
    });
}


/**
 * 不良记录
 */
function bljl(){
    layer.open({
        title: '新增警示记录',
        type: 2,
        content: "/realestate-inquiry-ui/changzhou/sd/bljlEdit.html?gzlslid=" + processInsId,
        area: ['960px', '360px'],
        cancel: function () {
            //执行流程删除方法
            window.parent.$("#bdc-delete-btn",window.parent.document).click();
        }
    });
}

/**
 * 补充上一手材料
 */
function bcYclxx(){
    //获取上一手
    getReturnData("/slym/xm/yxm",{gzlslid:processInsId},"GET",function (data) {
        if(data &&isNotBlank(data.gzlslid)){
            scwjj(data.gzlslid,"房改优惠售房补充材料");
        }else{
            ityzl_SHOW_WARN_LAYER("缺失参数,请检查数据");
        }
        
    },function () {
        
    });
}

/**
 * 创建修正流程
 */
var xzly = '';
function xzlc(){
    getReturnData("/slxxcsh/process/param",{processInsId:processInsId},"GET",function (data) {
        var bdcXmList = data.bdcXmList;
        //验证结束添加修正信息受理购物车数据
        xzly = 2;
        addXzXmShoppingCar(bdcXmList, data.jbxxid, data.xzlcGzldyid);
        lcCsh(data.jbxxid, data.xzlcGzldyid, data.slbh, true, false, true);
    },function () {
        ityzl_SHOW_WARN_LAYER("创建修正流程失败");
    });

}

/*
* 正常流程创建
* */
function lcCsh(jbxxid, processDefKey,slbh,isFrame, needYz,tslc) {
    $.ajax({
        url: getContextPath() + '/slxxcsh',
        type: 'GET',
        dataType: 'json',
        async: false,
        cache: false,
        data: {
            jbxxid: jbxxid,
            gzldyid: processDefKey,
            slbh: slbh,
            tslc: tslc,
            cdlb: '0',
            cdqlr: '',
            cdcqzh: '',
            cdzl: '',
            xzly: xzly,
            jdmc: jdmc
        },
        success: function (data) {
            removeModal();
            if (data.msg === "success") {
                if (isFrame) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    var url = "/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId;
                    setTimeout(function () {
                        parent.location.href = url;
                    }, 500);
                    parent.location.href = url + "&timestamp=" + new Date().getTime();
                } else {
                    var url = "/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId;
                    window.location.href = url;
                }
                removeModal();
            } else {
                removeModal();
                ityzl_SHOW_WARN_LAYER("初始化失败");
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

//添加修正信息到受理购物车数据
function addXzXmShoppingCar(bdcXmList, jbxxid, gzldyid) {
    var selectDataList = [];
    for (var i = 0; i < bdcXmList.length; i++) {
        var selectData = bdcXmList[i];
        var bdcSlYwxxDTO = {};
        bdcSlYwxxDTO.xmid = selectData.xmid;
        bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
        bdcSlYwxxDTO.ybdcqz = selectData.bdcqzh;
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlr;
        bdcSlYwxxDTO.bdcWlSlXmLsgxDOList = selectData.bdcWlSlXmLsgxDOList;
        bdcSlYwxxDTO.bdcSlYgDTOS = selectData.bdcSlYgDTOS;
        bdcSlYwxxDTO.qllx = parseInt(selectData.qllx);
        bdcSlYwxxDTO.sfzlcsh = null;
        bdcSlYwxxDTO.bdclx = selectData.bdclx;
        bdcSlYwxxDTO.fcjyBaxxDTO = selectData.fcjyBaxxDTO;
        bdcSlYwxxDTO.qlrsjly = selectData.qlrsjly;
        selectDataList.push(bdcSlYwxxDTO);
    }
    var bdcCshSlxmDTO = {};
    bdcCshSlxmDTO.bdcSlYwxxDTOList = selectDataList;
    bdcCshSlxmDTO.gzldyid = gzldyid;
    bdcCshSlxmDTO.jbxxid = jbxxid;
    getReturnData("/addbdcdyh", JSON.stringify(bdcCshSlxmDTO), "POST", function (data) {

    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
}

function showCfLc(gzlslid, xmly, xmid) {
    if (!isNullOrEmpty(xmly) && '1' == xmly) {
        window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + gzlslid);
    } else {
        window.open('/realestate-register-ui/view/lsgx/new-page.html?xmid=' + xmid + '&processInsId=' + gzlslid + '&version=changzhou');
    }
}

function showJflc(gzlslid, xmid) {
    getReturnData("/slym/ql/jfxx", {xmid: xmid}, "GET", function (data) {
        if (data) {
            showCfLc(data.gzlslid, data.xmly, data.xmid);
        } else {
            ityzl_SHOW_WARN_LAYER("当前数据无解封信息");
        }
    }, function (xhr) {
    })

}

function showXzlc() {
    addModel();
    getReturnData("/xzxx/xzlc", {gzlslid: processInsId}, "GET", function (data) {
        removeModal();
        //如果查询数量大于1，说明创建了多个修正，打开列表
        if (data) {
            if (data.length > 1) {
                var gzlslidList = [];
                for (var i = 0; i < data.length; i++) {
                    gzlslidList.push(data[i].gzlslid);
                }
                var url = "/realestate-accept-ui/view/xzxx/xzxxPlList.html?processInsId=" + gzlslidList.join(",");
                layerCommonOpenFull(url, "修正信息列表", $(window).width() + "px", $(window).height() + "px", $(window).width() + "px", $(window).height() + "px");
            } else {
                var url = "/realestate-accept-ui/rest/v1.0/xzxx?processInsId=" + data[0].gzlslid + "&readOnly=true";
                layerCommonOpenFull(url, "修正信息", $(window).width() + "px", $(window).height() + "px", $(window).width() + "px", $(window).height() + "px");
            }
        } else {
            ityzl_SHOW_WARN_LAYER("当前流程尚未办理修正业务！");
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })

}


