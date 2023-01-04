/**
 * @author "<a href="mailto:liaoxiang@gtmap.cn>liaoxiang</a>"
 * @version 1.0, 2019/08/28
 * @description 全部版本受理表单操作公共JS
 */
// 字典
var zdList = {};
var taskid = getQueryString("taskId");
var jbxxid = "";
layui.use(['jquery','laydate'], function () {
    var $ = layui.jquery,
        laydate = layui.laydate;
    $(function () {
        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-form-div');
        function defaultStyle() {
            if($cnotentTitle.length == 1){
                $('.bdc-form-div').css('padding-top','68px');
            }
            if($(window).scrollTop() > 68){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','63px');
            }else if($(window).scrollTop() <= 68){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
        }

        $(window).resize(function(){
            defaultStyle();
        });
        $(window).on('scroll',function () {
            if($(this).scrollTop() > 68){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','63px');
            }else if($(this).scrollTop() <= 68){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
        });
        $('.layui-input').on('focus',function () {
            if($(this).hasClass('layui-form-danger')){
                $(this).removeClass('layui-form-danger');
            }
            if($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')){
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
        });
        $('.xm-input').on('click',function () {
            if($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')){
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
        });
    });

    /**
     * 获取字典
     * @param callback 回调函数
     */
    window.getCommonZd = function (callback) {
        var zdList = getZdList();
        if (zdList) {
            callback(zdList);
        }
    };

    /**
     * 获取项目信息,加载基本信息内容
     */
    window.getCommonXmxx = function (gzlslid,zdList) {
        getReturnData("/slym/xm", {processInsId: gzlslid}, 'GET', function (data) {
            var json = {
                bdcxmxx: data,
                zd: zdList
            };
            var tpl = jbxxTpl.innerHTML, view = document.getElementById('sljbXx');
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
        });
    };

    /**
     * 获取登记家庭成员信息
     */
    window.getCommonBdcJtcyxx = function (qlrid,callback) {
        getReturnData("/rest/v1.0/jtcy/qlrid/"+qlrid,{},"GET",function (data) {
            callback(data);
        },function (error) {
            delAjaxErrorMsg(error);

        })
    };

    //表单保存
    window.saveCommonForm = function (submitCallback){
        var isSubmit = true;
        var verifyMsg = "必填项不能为空!";
        //表单校验
        form.verify({
            required: function (value, item) {
                //判断是否为空
                if (value == '' || value == null || value == undefined) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                }
            }
            , identitynew: function (value, item) {
                var msg = checkSfzZjh(value);
                if (isNotBlank(msg)) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = msg;
                }
            }
            //要求正整数，允许为空
            , number: [/^[1-9]+[0-9]*]*$/, '请输入非负整数']
            //数字
            , number2: [/^-?\d*$/, '请输入数字']
            , lxdh: function (value, item) {
                if (!validatePhone($.trim(value))) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = "联系电话格式不正确";
                }
            }
            , zjhlength: function (value, item) {
                var msg = checkZjhLength(value);
                if (isNotBlank(msg)) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = msg;
                }
            }
        });

        //提交操作
        form.on("submit(saveData)", function (data) {
            //提交前验证
            if($.isFunction(checkCommitData)){
                var checkMsg =checkCommitData();
                if (isNotBlank(checkMsg)) {
                    isSubmit = false;
                    verifyMsg = checkMsg;
                }
            }
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                return false;
            } else {
                submitCallback(data);
                //禁止提交后刷新
                return false;
            }

        });



    };


    // 获取字典信息
    function getZdList() {
        getReturnData("/bdczd",{},"POST",function (data) {
            zdList=data;
        },function () {
        },false);
        return zdList;
    }
});

/**
 * 更改出证类型为：生成证明书
 * 提供给楼盘表的js回调方法，用于更新bdc_sl_fwkg_sl中生成证书的数据
 */
function sczmbybdcdyh(bdcdyhList) {
    var sfsczs = 1;
    var zszl = 3;
    updateCshFwkgData(bdcdyhList, sfsczs, zszl);
}

/**
 * 更改出证类型为：生成证书
 */
function sczsbybdcdyh(bdcdyhList) {
    var sfsczs = 1;
    var zszl = 1;
    updateCshFwkgData(bdcdyhList, sfsczs, zszl);
}

/**
 * 更改出证类型为：不发证
 */
function bfzbybdcdyh(bdcdyhList) {
    var sfsczs = 0;
    var zszl = null;
    updateCshFwkgData(bdcdyhList, sfsczs, zszl);
}
function updateCshFwkgData(bdcdyhList, sfsczs, zszl){
    if(bdcdyhList.length == 0){
        ityzl_SHOW_WARN_LAYER("未获取到不动产单元号信息。");
        return;
    }
    var bdcSlCshFwkgDataDTOList = new Array();
    $.each(bdcdyhList, function(i, value){
        bdcSlCshFwkgDataDTOList.push({
            bdcdyh : value.bdcdyh,
            sfsczs : sfsczs,
            zszl : zszl
        });
    });
    addModel();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/slym/updateCshFwkg/"+processInsId,
        type: 'PUT',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcSlCshFwkgDataDTOList),
        success: function () {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("修改成功");
            //刷新楼盘表页面
            var $iframe;
            var iframes = $("iframe");
            if(iframes.length > 0){
                for(var j = 0 ;j<iframes.length ;j++){
                    if($(iframes[j]).attr("src").indexOf("building-ui/lpb/view") > 0){
                        $iframe = $(iframes[j]);
                    }
                }
            }
            if($iframe){
                var child = $iframe[0].contentWindow;
                child.location.reload();
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

function renderBackgroundColor(classname) {
    $.ajax({
        url: '/realestate-accept-ui/bdcdyh/status/color',
        type: "GET",
        async: false,
        success: function(data) {
            var backColor = data[classname];
            $('.' + classname).children('.bdc-td-box td:not(:last-child)').css('background-color', backColor);
            $('.' + classname).find(".bdc-td-box input").css('background-color', backColor);
            $('.' + classname).find(".bdc-td-box select").addClass('tr-select-color');
            $('.' + classname).find(".bdc-td-box select").attr('style','display:none;');
            $('.' + classname).find(".bdc-td-box select").attr('lay-ignore','');
        }
    });
}

// 批量保存项目附表数据
function saveXmfbPl(formClass, djxl) {
    var bdcXmfbData = {};
    var bdcXmfbArray = $(formClass);
    if (bdcXmfbArray !== null && bdcXmfbArray.length > 0) {
        bdcXmfbArray.serializeArray().forEach(function (item, index) {
            bdcXmfbData[item.name] = item.value;
        });
    }

    var sfzhyclh = $($(formClass).find("input[type='radio']:checked")).val();
    if (isNotBlank(sfzhyclh)) {
        bdcXmfbData.sfzhyclh = sfzhyclh;
    }

    if (!$.isEmptyObject(bdcXmfbData)) {
        var bdcDjxxUpdateQO = {};
        var whereMap = {};
        whereMap.gzlslid = processInsId;
        if (isNotBlank(djxl)) {
            whereMap.djxl = djxl;
        }
        bdcDjxxUpdateQO.whereMap = whereMap;
        bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmfbData);
        getReturnData("/slym/xm/plxgxmfb", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
            console.log("批量保存项目附表信息成功");
        }, function (err) {
            throw err;
        });
    }

}

//保存项目附表
function saveXmfb(formClass, index) {
    var bdcXmfbData = {};
    var bdcXmfbArray = $(formClass);
    if (bdcXmfbArray !== null && bdcXmfbArray.length > 0) {
        bdcXmfbArray.serializeArray().forEach(function (item, index) {
            bdcXmfbData[item.name] = item.value;
        });

        var sfzhyclh = $($(formClass).find("input[type='radio']:checked")).val();
        if (isNotBlank(sfzhyclh)) {
            bdcXmfbData.sfzhyclh = sfzhyclh;
        }

        getReturnData("/slym/xm/fb", JSON.stringify(bdcXmfbData), 'PATCH', function (data) {
            console.log("保存项目附表成功")
        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
    }
}

/**
 * @param xmid 项目id
 * @param $qlqtzkfj 当前点击的框子
 * @param mode 2：权力其他状况  3 ： 附记
 * @param sfgx 是否更新权利信息
 * @param hqysj 数据库获取原数据
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 获取权利其他状况或者附记模板信息
 * @date : 2020/8/26 15:49
 */
function queryQlqtzkFjMb(xmid,$qlqtzkfj,mode,sfgx){
    getReturnData("/slym/ql/queryQlqtzkFjMb/nt",{xmid:xmid,mode:mode,hqysj:true,sfgx:sfgx},"GET",function (data) {
        //获取模板数据，不管是否为空
        $qlqtzkfj.val(data);
    },function (error) {
        delAjaxErrorMsg(error);

    })
}

//根据获取到的权利信息数据，渲染内容到页面------buildQlxx调用
function buildQlxxUse(bdcSlQlxxym,$nowTab){
    //定位权利其他状况元素
    var $qlqtzk = $nowTab.find("textarea[name='bfqlqtzk']");

    //定位附记
    var $fj = $nowTab.find(".qlfj");
    dealQlqtzkFj($nowTab,$qlqtzk,$fj,bdcSlQlxxym.bdcXm.xmid);

}

/**
 *  获取权利其他状况或者附记模板信息
 *  没有值获取模板数据
 */
function dealQlqtzkFj($nowTab,$qlqtzk,$fj,xmid){
    //判断是否需要隐藏
    initHideQlqtzkFjByZssl($nowTab,$qlqtzk,$fj);
    //如果权利其他状况没有值,加载模板数据
    if($qlqtzk.length >0 &&!isNotBlank($qlqtzk.val())) {
        //加载权利其他状况
        queryQlqtzkFjMb(xmid, $qlqtzk,"2",true);
    }
    //如果附记没有值,加载模板数据
    if($fj.length >0 &&!isNotBlank($fj.val())) {
        //加载附记
        queryQlqtzkFjMb(xmid, $fj,"3",true);
    }

}

/**
 * 判断流程是否生成一本证,生成多本证隐藏附记和权利其他状况(初始化方法)
 */
function initHideQlqtzkFjByZssl($nowTab,$qlqtzk,$fj){
    var tabDjxl = '';
    if(isNotBlank($nowTab)) {
        tabDjxl = $nowTab.data('djxl');
    }
    getReturnData("/slym/ql/checkPlZhOne",{gzlslid:processInsId,djxl:tabDjxl},"GET",function (data) {
        if(!data){
            //生成多本证移除附记和权利其他状况
            if($qlqtzk.length >0) {
                $qlqtzk.parent().parent().remove();
                $('#qlqtzksdtx').remove();
            }
            if($fj.length >0) {
                $fj.parent().parent().remove();
                $('#fjsdtx').remove();
            }
        }
    },function (error) {
        delAjaxErrorMsg(error);

    });
}


//记录流程第一次加载的业务数据
function addYwxxLog(){
    getReturnData("/rest/v1.0/bgxxdb/es",{gzlslid : processInsId},"GET",function (data) {
    },function (error) {});
}
// 打开变更记录页面
function showBgjl(){
    getReturnData("/slym/xm/getlclx", {gzlslid: processInsId}, "GET", function (data) {
        if (data !== null) {
            // 1:普通  2：组合  3：批量  4:批量组合
            if(data == "1"){
                window.open(getContextPath() + "/view/bgdb/bgxxdb.html?processInsId=" + processInsId +"&lclx="+ data);
            }
            if(data == "2" || data == "3" || data == "4" ){
                window.open(getContextPath() + "/view/bgdb/bgxxdbPlzh.html?processInsId=" + processInsId +"&lclx="+ data);
            }
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    });
}

/**
 * @param mc,zjh,type 名称，证件号，验证类型（公安验证，社会组织验证，企业信息验证）
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 权利人相关接口验证
 * @date : 2020/10/30 14:28
 */
function qlryz(mc, zjh, type) {
    addModel("验证中");
    var url = "";
    if (type === "gayz") {
        url = "/slym/qlr/gayz"
    }
    if (type === "shzzyz") {
        url = "/slym/qlr/shzzyz"
    }
    if (type === "qyyz") {
        url = "/slym/qlr/qyyz"
    }
    getReturnData(url,{qlrmc:mc,qlrzjh:zjh,gzlslid:processInsId},"GET",function (data) {
        removeModal();
        if(data) {
            ityzl_SHOW_SUCCESS_LAYER("校验成功");
        } else {
            ityzl_SHOW_WARN_LAYER("校验失败");
        }
    },function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    })
}

// 展示人证对比
function showRzdb(){
    var slbh = $("#sjbh").val();
    getReturnData('/url/rzdbUrl','','GET',function (data) {
        if(isNotBlank(data)){
            window.open(data + slbh);
        }else{
            ityzl_SHOW_WARN_LAYER("未获取到配置的人证对比页面地址。");
        }
    },function (xhr) {
    });
}
// 获取比对结果
function hqdbjg(){
    var slbh = $("#sjbh").val();
    addModel();
    getReturnData('/slPrint/rzdb', {gzlslid: processInsId, slbh: slbh}, 'GET', function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("生成比对文件成功。");
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}

//组织参数调用评价器
function evaluate() {
    getReturnData("/pjq", {gzlslid: processInsId, taskid: taskid}, "GET", function (data) {
        if (data !== null) {
            pj(data);
        }
    }, function (err) {
        removeModal();
        delAjaxErrorMsg(err);
    })
}

function pj(pjdata) {
    var url = pjdata.url + "?ywbh=" + pjdata.bdcXmDO.slbh + "&jdmc=" + pjdata.jdmc + "&blry=" + pjdata.bdcXmDO.slr + "&sqrxm=" + pjdata.qlrmc + "&sqrlxfs=" + pjdata.qlrlxfs;
    console.log(encodeURI(url));
    window.open(encodeURI(url));
}

function yztfj() {
    window.open("/realestate-accept-ui/view/yzt/gdspxx.html?processInsId=" + processInsId);
}

function tbyzwxx() {
    var url = getContextPath() + "/view/slym/tbyzwxx.html?processInsId=" + processInsId
    layer.open({
        type: 2,
        area: ['960px', '360px'],
        fixed: false, //不固定
        title: "同步一张网信息",
        content: url,
        btnAlign: "c"
    });
}

function closetbyzwxx() {
    layer.closeAll();
    location.reload();
}

/**
 * Html5PostMessage 实现页面之间跨域通信功能
 * accept-ui 页面为子窗口，下述方法用于监听父页面的消息推送，并进行页面附记内容的刷新
 */
if(typeof window.addEventListener!= "undefined"){
    // 非 IE 浏览器监听方式
    window.addEventListener("message", receiveMessage, false);
}else{
    // IE 浏览器的监听方式
    window.attachEvent("onmessage", receiveMessage);
}

function receiveMessage(e){
    console.info(e);
    var data = e.data;
    if(isNotBlank(data) && data.refreshFj){
        doRefreshFj();
    }
}

/**
 * 刷新方式，获取受理页面的 tableUi下面的li 里面的xmid，通过 xmid 刷新附记内容
 */
function doRefreshFj(){
    console.info("量化信息页面通知刷新附记信息内容");
    $('.layui-tab-title li').each(function(index, element){
        var xmid = $(element).find("input[name='xmid']").val();
        if(isNotBlank(xmid)){
            var $fj = $(".layui-tab-content .layui-tab-item:eq("+index+")").find("textarea[name='fj']");
            if($fj.length > 0){
                getReturnData("/slym/ql/queryQlxx", {xmid: xmid, sfyql:false}, 'GET', function (data) {
                    if (isNotBlank(data) && isNotBlank(data.fj)) {
                        $fj.val(data.fj);
                    }
                }, function (err) {
                    delAjaxErrorMsg(err);
                });
            }
        }
    });
}

//新增领证人页面弹出层
function addLzr(xmid, djxl) {
    var index = $(".qlxx.layui-this").find("input[name='liIndex']").val();
    var name1 = 'sqfbcz' + index;
    var sqfbcz = $($('.xmxx' + index).find("input[name=" + name1 + "]:checked")).val();
    if (isNullOrEmpty(sqfbcz) || lclx === "pllc") {
        //如果为空，查找整个页面的数据
        $("input:radio[name='sqfbcz']:checked").each(function () {
            sqfbcz = $(this).val();
        });
    }
    if (lclx === "plzh") {
        name1 = 'sqfbcz' + djxl;
        sqfbcz = $($('.layui-show').find("input[name=" + name1 + "]:checked")).val();
    }
    var url = getContextPath() + "/view/slym/lzr.html?xmid=" + xmid + "&processInsId=" + processInsId +
        "&formStateId=" + formStateId + "&djxl=" + djxl + "&lclx=" + lclx + "&sqfbcz=" + sqfbcz;
    layer.open({
        type: 2,
        area: ['960px', '360px'],
        fixed: false, //不固定
        title: "新增领证人",
        content: url,
        btnAlign: "c"
    });
}