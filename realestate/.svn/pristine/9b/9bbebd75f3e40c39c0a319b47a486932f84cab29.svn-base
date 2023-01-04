/**
 * @author "<a href="mailto:liaoxiang@gtmap.cn>liaoxiang</a>"
 * @version 1.0, 2019/08/28
 * @description 全部版本受理表单操作公共JS
 */
// 字典
var zdList = {};
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
            ,number:function (value, item) {
                //为非负整数,允许为空
                if (isNotBlank(value) && !/^[1-9]+[0-9]*]*$/.test(value)) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = "请输入非负整数";
                }
            }
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

// 南通海门公用的方法
function saveXmfbPl(formClass,djxl) {
    var bdcXmfbData = {};
    var bdcXmfbArray = $(formClass);
    if (bdcXmfbArray !== null && bdcXmfbArray.length > 0) {
        bdcXmfbArray.serializeArray().forEach(function (item, index) {
            bdcXmfbData[item.name] = item.value;
        });
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
            if(isNotBlank(bdcXmfbData.sfsftg) && bdcXmfbData.sfsftg== 1){
                // 添加税费托管业务展示说明标签
                $("#sfsftg-tips").show();
            }
            if(isNotBlank(bdcXmfbData.sfsftg) && bdcXmfbData.sfsftg== 0){
                // 添加税费托管业务展示说明标签
                $("#sfsftg-tips").hide();
            }
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
        if(Object.keys(bdcXmfbData).length >1) {
            getReturnData("/slym/xm/fb", JSON.stringify(bdcXmfbData), 'PATCH', function (data) {
                console.log("保存项目附表成功")
            }, function (err) {
                delAjaxErrorMsg(err);
            }, false);
        }
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
function dealQlqtzkFj($nowTab,$qlqtzk,$fj,xmid, djxl){
    //判断是否需要隐藏
    initHideQlqtzkFjByZssl($nowTab,$qlqtzk,$fj, djxl);
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
function initHideQlqtzkFjByZssl($nowTab,$qlqtzk,$fj, djxl){
    var tabDjxl = '';
    if(djxl){
        tabDjxl = djxl;
    }
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
        if (data) {
            ityzl_SHOW_SUCCESS_LAYER("校验成功");
        } else {
            ityzl_SHOW_WARN_LAYER("校验失败");
        }
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    })
}

/**
 * @param gzlslid
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 购房资格验证
 * @date : 2021/4/21 14:33
 */
function gfzgyz() {
    addModel("查询中");
    getReturnData("/slym/qlr/xgxx", {gzlslid: processInsId}, "GET", function (data) {
        removeModal();
        if (data) {
            var json = {
                xgxxList: data
            }
            var tpl = xgxxTpl.innerHTML, view = document.getElementById('xgxx');
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            layer.open({
                title: '查询限购信息结果',
                type: 1,
                area: ['960px', '600px'],
                content: $("#xgxx")
            });
        } else {
            ityzl_SHOW_INFO_LAYER("未获取到限购信息");
        }
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr)
    });
}

function queryShuChengJyxx(fwlx,xmid) {
    //小弹出层
    var htbhIndex = layer.open({
        title: '获取房产交易合同信息',
        type: 1,
        skin: 'bdc-spf-layer',
        area: ['430px'],
        btn: ['提交', '取消'],
        content: $('#bdc-popup-small')
        , yes: function (index, layero) {
            //提交 的回调
            var contractNo = $('#contractNo').val();
            $('#contractNo').val('');
            var currentXmid = $("#liTbale").find(".qlxx.layui-this").find("input[name='xmid']").val();
            if (!isNullOrEmpty(xmid)) {
                currentXmid = xmid;
            }
            importShuChengJyxx(contractNo, htbhIndex, currentXmid,fwlx)
        }
        , btn2: function (index, layero) {
            //取消 的回调
        }
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        , success: function () {
            // //清空已有值
            var htbhDefaultVal = getHtbhDefaultVal(fwlx);
            $('#contractNo').val(htbhDefaultVal);
            // //自动获取焦点
            $('#contractNo').focus();
        }
    });
}

// 获取配置的合同编号默认值内容
function getHtbhDefaultVal(type){
    var htbhValue = "";
    $.ajax({
        url: getContextPath() + "/rest/v1.0/slym/htbh/pz",
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
           if(type == "clf" || type == "1"){
               htbhValue = data.clfHtbh;
           }else{
               htbhValue = data.spfHtbh;
           }
        }
    });
    return htbhValue;
}

function importShuChengJyxx(htbh, htbhIndex, currentXmid,fwlx) {
    addModel("获取中");
    if (isNullOrEmpty(htbh)) {
        ityzl_SHOW_WARN_LAYER("请输入合同编号")
    }
    if (isNullOrEmpty(currentXmid)) {
        currentXmid = xmid;
    }
    getReturnData("/slym/jyxx/shucheng", {
        htbh: htbh,
        xmid: currentXmid,
        gzlslid: processInsId,
        fwlx: fwlx
    }, "GET", function (data) {
        //刷新权利信息页
        //重新组织页面数据
        layer.close(htbhIndex);
        if (lclx == "zhlc") {
            refreshQlxx();
        } else if (lclx == "pllc") { // 批量流程刷新不动产单元信息
            loadBdcdyh();
        } else {
            loadBdcdyh($(".layui-show").data('djxl'), $(".layui-show").data('qllx'), $(".layui-show").data('xmid'));
        }
        loadQlr();
        setTimeout(function () {
            ityzl_SHOW_SUCCESS_LAYER("获取成功");
            removeModal();
        }, 150);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 合肥获取共享交易电子合同
 * @date : 2022/4/13 9:33
 */
function queryJydzht(type, fwlx) {
    //小弹出层
    var htbhIndex = layer.open({
        title: '获取交易电子合同',
        type: 1,
        skin: 'bdc-spf-layer',
        area: ['430px'],
        btn: ['提交', '取消'],
        content: $('#bdc-popup-small')
        , yes: function (index, layero) {
            //提交 的回调
            var contractNo = $('#contractNo').val();
            $('#contractNo').val('');
            importJydzht(contractNo, htbhIndex, type);
        }
        , btn2: function (index, layero) {
            //取消 的回调
        }
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        , success: function () {
            // //清空已有值
            var htbhDefaultVal = getHtbhDefaultVal(fwlx);
            $('#contractNo').val(htbhDefaultVal);
            // //自动获取焦点
            $('#contractNo').focus();
        }
    });
}

function importJydzht(htbh, htbhIndex, type) {
    addModel();
    getReturnData("/slym/jyxx/dzht", {htbh: htbh, gzlslid: processInsId, type: type}, "GET", function (data) {
        removeModal();
        layer.close(htbhIndex);
        ityzl_SHOW_SUCCESS_LAYER("导入成功");
        //刷新收件材料
        loadSjcl();
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    })
}

function dealWithUrl(url, sply, splyList) {
    if(sply && splyList && splyList.indexOf(sply) > -1){
        return url.replace("readOnly=false","readOnly=true")
    }
    return url;
}
/**
 * 根据审批来源禁用申请人
 * @param sply 审批来源
 * @param splyList 配置的splyList值
 * @param classdiv 需要改变来源属性的父div的class名
 */
function disabledSqrxx(sply, splyList,classdiv) {
    if(sply && splyList && splyList.indexOf(sply) > -1){
        $(classdiv).prop("disabled","off");
        $(classdiv).find(":input").prop("disabled", "off");
        $(classdiv).find(":button[name='qlrdelete']").removeAttr("disabled");
        $(classdiv).find(":button[name='qlrxx']").removeAttr("disabled");
        $(classdiv).find(":button[name='ywrxx']").removeAttr("disabled");
        $(classdiv).find(":button[name='qlrdelete']").hide();
        $(".bdc-info").hide();
        form.render("select");
        disabledAddFa();
    }
}


function querySjcltjfs() {
    getReturnData("/slym/xm/sjcltjfs", {gzlslid: processInsId}, "GET", function (data) {
        if (data) {
            $("#sqcltjfs").val(data.sqcltjfs);
            $("#jbxxid").val(data.jbxxid);
            form.render('select')
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//获取内部共享材料
function getNbgxcl() {
    var url = "/realestate-inquiry-ui/shucheng/gxxt/bdcGxxt.html?processInsId=" + processInsId;
    layerCommonOpenFull(url, "共享系统", '1150px', '600px');
}

// 舒城，安徽省好差评，调用省好差评的评价页面到评价器
function getPjqHcp(){
    var taskId = getQueryString("taskId");
    getReturnData("/pjq/hcp/pjymurl", {gzlslid: processInsId,taskId:taskId}, "GET", function (data) {
        if (data !== null) {
            // 发送到评价器
            var url = encodeURI(data.res);
            console.log(url);
            GWQ_OpenURL(url);
        }
    }, function (err) {
        removeModal();
        delAjaxErrorMsg(err);
    })


}

// 合肥，安徽省好差评，调用省好差评的评价页面到评价器
function getHfPjqHcp(){
    var taskId = getQueryString("taskId");
    getReturnData("/pjq/hcp/pjymurl", {gzlslid: processInsId,taskId:taskId}, "GET", function (data) {
        if (data !== null) {
            // 发送到评价器
            var url = encodeURI(data.res);
            console.log(url);
            hfHcp(url);
        }
    }, function (err) {
        removeModal();
        delAjaxErrorMsg(err);
    })
}

// 蚌埠好差评，发地址给讯飞小程序
function getBbPjqHcp(){
    var taskId = getQueryString("taskId");
    getReturnData("/pjq/hcp/pjymurl", {gzlslid: processInsId,taskId:taskId}, "GET", function (data) {
        if (data !== null) {
            // 发送到评价器
            if (data.flag){
                ityzl_SHOW_SUCCESS_LAYER("发送成功");
            }else{
                ityzl_SHOW_WARN_LAYER("发送失败");
            }
        }
    }, function (err) {
        removeModal();
        delAjaxErrorMsg(err);
    })
}

/**
 * 蚌埠、合肥判断是否显示确认对话框
 * @param id 按钮id名
 * @param msg 提示信息
 * @param yesFun 选择确定执行的方法
 */
function isConfirm(id, msg, yesFun) {
    if (isNullOrEmpty(sfecqrids)) {    //二次确认配置的id值
        addModel();
        //执行方法
        eval(yesFun + "()");
    } else if (sfecqrids.indexOf(id) > -1) {//读取配置值
        //显示确认框
        showConfirmDialog("提示", msg, yesFun);
    }
}