//字典列表
var zdList = loadZdData();
//地址栏获取数据
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var zxlc = getQueryString("zxlc");
var xmid = getQueryString("xmid");
var slbh,bdcSlXm,bdcSlSqrListCache; // bdcSlXm通过页面隐藏域jbxxId调用接口获取
var $,laytpl,form,table;
var allYcDataObj = {};
//规则验证入参
var gzyzParamMap ={};
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function() {
    form = layui.form;
    $ = layui.jquery;
    var element = layui.element;
    laytpl = layui.laytpl;
    var layer = layui.layer;

    addModel();
    // 加载受理页面按钮
    setTimeout(function () {
        try {
            $.when(loadButtonArea()).done(function () {
                titleShowUi();
                removeModal();
            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e,"加载失败");
        }
    }, 50);

    // 监听3个页面的点击事件
    // 第一次单击tab，时查询受理信息
    // 非第一次不用再查
    var fcjyIndex = 0;
    element.on('tab(tabFilter)', function (data) {
        var tabid = $(".layui-tab-title .layui-this").attr("id");
        $(this).removeAttr("onclick");
        if (tabid === "qlxxTab" || tabid ==="swxx") {
            if (fcjyIndex === 0) {
                fcjyIndex++;
                addModel();
                setTimeout(function () {
                    loadYcslxx();
                }, 0);
            }
        }
    });

    // 监听承受方税款信息新增
    $('.basic-info').on('click', '#addskxx', function() {
        var getTpl = addskxxTpl.innerHTML;
        laytpl(getTpl).render([], function(html) {
            $('.jexj').before(html);
        });
        form.render();
    });
    form.render();


    //点击提交时不为空的全部标红
    var isSubmit = true;
    var isFirst = true;
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '' || value == null || value == undefined) {//判断是否为空
                if (isFirst) {
                    //定位第一个为空的必填项
                    var target = $(item).parents('.bdc-not-null').offset().top;
                    $("body,html").animate({scrollTop:target},0);
                    isFirst = false;
                }
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
        ,lxdh: function (value, item) {
            if (!validatePhone(value)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "联系电话格式不正确";
            }
        }
    });

    // 表单保存方法
    form.on("submit(saveData)", function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            isFirst = true;
            return false;
        } else {
            addModel();
            setTimeout(function () {
            try{
                $.when(saveYcslXx()).done(function () {
                    removeModal();
                    ityzl_SHOW_SUCCESS_LAYER("保存成功");
                    getFwtcxx();

                })
            } catch (e) {
                removeModal();
                if (e.message) {
                    showAlertDialog(e.message);
                } else {
                    delAjaxErrorMsg(e);
                }
            }
            }, 10);
            return false;
        }
    });

    //推送一窗创建登记流程
    $(document).on('click', '#tsBdcDjxx', function () {
        tsBdcDjxx();
    });

    // 权利信息区域，重新生成按钮初始化事件绑定
    qlxxModel.initEvent();
});

// 按钮加载
function loadButtonArea() {
    var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
    //渲染数据
    laytpl(tpl).render({}, function (html) {
        view.innerHTML = html;
    });
    form.render();
    getStateById(readOnly, formStateId, "ycym");
    disabledAddFa();
}

/**
 * 查询一窗的所有信息对象，包括
 * 房屋信息
 * 合同信息
 * 权利人信息
 * 权利人税务信息
 * */
function loadYcslxx (){
    $.ajax({
        url: getContextPath() + "/ycsl",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {gzlslid: processInsId, xmid: xmid},
        success: function (data) {

            allYcDataObj = data;
            // 初始化申请人信息
            generateSqrxx(data);
            // 初始化加载权利信息和不动产单元信息
            generateQlxxAndBdcdyxx(data);
            // 加载交易与税收tab标签页
            generateJyss(data);
            form.render();
            renderSelect();
            getStateById(readOnly, formStateId, 'ycym');
            form.render("select");
            disabledAddFa();
            renderDate(form);
            removeModal();
            reloadZjhYz();
        }
    });
}

//加载申请人信息
function generateSqrxx(data) {
    //新增默认值
    var bdcSlSqr = {};
    bdcSlSqr["xmid"] = xmid;
    bdcSlSqr["sxh"] = 1;
    var bdcSlSqrList =[];
    var zcfList = data.bdcSlZcfDTOList;
    var zrfList = data.bdcSlZrfDTOList;
    if(zrfList){
        for(var i=0;i<zrfList.length;i++){
            bdcSlSqrList.push(zrfList[i]);
        }
    }
    if(zcfList){
        for(var i=0;i<zcfList.length;i++){
            bdcSlSqrList.push(zcfList[i]);
        }
    }
    bdcSlSqrListCache = bdcSlSqrList;
    var json = {
        bdcSlSqrDTOList: bdcSlSqrList,
        zd: zdList,
        bdcSlSqr: bdcSlSqr
    };

    var tpl = sqrTpl.innerHTML,
        view = document.getElementById('sqrxx');
    //渲染数据
    laytpl(tpl).render(json, function(html) {
        view.innerHTML = html;
    });
}

/**
 *  通过基本信息ID查询不动产受理项目信息
 * @param jbxxId 基本信息ID
 */
function loadBdcSlXm(jbxxId){
    if(isNotBlank(jbxxId)){
        getReturnData("/gwc/list/bdcslxm", {jbxxid: jbxxId}, 'GET', function (item) {
            if (isNotBlank(item)) {
                bdcSlXm =item[0]; // 将查询到不动产受理项目信息设置到全局对象
                xmid =item[0].xmid;
            }
        }, function (err) {
            console.log(err);
        },false);
    }
}

/**
 * 身份证证件号验证添加
 * @param zjzl 证件种类
 * @param $zjh 需要添加身份证校验的dom元素
 */
function addSfzYz(zjzl, $zjh){
    var zjhVerify =  $zjh.attr("lay-verify");
    if(zjzl == '1'){
        if (isNotBlank(zjhVerify)) {
            if (zjhVerify.indexOf("identitynew") < 0) {
                $zjh.attr("lay-verify", zjhVerify + "|identitynew");
            }
        } else {
            $zjh.attr("lay-verify", "identitynew");
        }
    }else {
        //移除证件号验证
        if (isNotBlank(zjhVerify) && zjhVerify.indexOf("identitynew") > -1) {
            $zjh.attr("lay-verify", zjhVerify.replace("identitynew", ""));
        }
    }
}

// 身份证验证初始化
function reloadZjhYz() {
    $('.form-margin-area .basic-info').each(function (index, item) {
        // 申请人、转入转出方身份证验证
        $(item).find('[lay-filter="zjzl"]').each(function(){
            var zjzlid = $(this).parent().parent().find("select")[0].id;
            var zjhid = zjzlid.replace("zjzl", "zjh");
            addSfzYz($(this).val(), $("#"+zjhid));
        });
        // 添加领证人验证
        $(item).find('[lay-filter="lzrzjhFilter"]').each(function(){
            var $zjh = $(this).parents(".layui-inline").next().find("input");
            addSfzYz($(this).val(), $zjh);
        });
    });

}

//保存一窗受理信息
function saveYcslXx() {
    saveJbxx();
    saveSjcl();
    saveBdcSlJyxx(".jyxx");
    saveBdcSlXm(".slxm");
    saveBdcSlFwxx(".fwxx");
    saveSqr();//保存申请人
    saveBdcSlLzrxx(".lzxx");
    saveBdcSlZrfZcf('.zrf');
    saveBdcSlZrfZcf('.zcf');

}

//组织参数调用评价器
function evaluate() {
    getReturnData("/pjq/evaluateFdjlc", {gzlslid: processInsId,xmid:xmid}, "GET", function (data) {
        if (data !== null) {
            pj(data);
        }
    }, function (err) {
        removeModal();
        delAjaxErrorMsg(err);
    })
}

function pj(pjdata) {
    var url = pjdata.url + "?ywbh=" + pjdata.slbh + "&jdmc=受理" + "&blry=" + pjdata.slr + "&sqrxm=" + pjdata.qlrmc + "&sqrlxfs=" + pjdata.qlrlxfs;
    console.log(encodeURI(url));
    window.open(encodeURI(url));
}

//获取房屋套次信息
function getFwtcxx(){
    addModel("校验房屋套次信息");
    getReturnData("/ycsl/getFwtcxx?xmid="+xmid,{},"POST",function (data) {
        removeModal();
        //调取评价接口
        evaluate();
    },function (error) {
        removeModal();
        var responseText = JSON.parse(error.responseText);
        ityzl_SHOW_WARN_LAYER(responseText.msg);
    },false);
}

// 扩展表单serializeArray方法，将表单序列对象
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

//推送一窗创建登记流程
function tsBdcDjxx(){
    addModel();
    var bdcTsDjxxRequestDTO ={};
    bdcTsDjxxRequestDTO.jbxxid =jbxxid;
    bdcTsDjxxRequestDTO.gzlslid =processInsId;
    var bdcYcslPzDTO ={};
    //自动转发
    bdcYcslPzDTO.autoTurn =true;
    bdcYcslPzDTO.gyslbh =true;
    bdcYcslPzDTO.autoComplete =true;
    bdcTsDjxxRequestDTO.bdcYcslPzDTO =bdcYcslPzDTO;
    getReturnData("/ycsl/tsBdcDjxx",JSON.stringify(bdcTsDjxxRequestDTO),"POST",function (data) {
        removeModal();
        if(data.lczt ==="1"){
            showAlertDialog("创建失败"+ (isNotBlank(data.msg)? ",失败原因为："+data.msg:""));
        }else if(data.lczt ==="2"){
            ityzl_SHOW_SUCCESS_LAYER("创建成功,受理编号:"+data.slbh);
            layer.msg('创建成功');
            setTimeout(function () {
                window.open("/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId);
            }, 1000);
        }else if(data.lczt ==="3"){
            //创建成功,转发失败
            layer.msg('创建成功，自动转发失败'+ (isNotBlank(data.msg)? ",失败原因为："+data.msg:""), {
                time: 1000
            }, function () {
                window.open("/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId);
            });
        }else if(data.lczt ==="4"){
            ityzl_SHOW_SUCCESS_LAYER("自动转发成功,受理编号:"+data.slbh);
        }else{
            showAlertDialog("创建失败");
        }
    },function (error) {
        delAjaxErrorMsg(error);

    })
}

function saveJbxx(){
    var bdcXmData = {};
    var bdcXmArray = $(".bdcxm");
    bdcXmArray.serializeArray().forEach(function (item, index) {
        bdcXmData[item.name] = item.value;
    });
    bdcXmData.bz = $("#bz").val();

    //默认为平方米，防止不可编辑冲掉
    var mjdw = '1';
    $("input:checkbox[name='mjdw']:checked").each(function () {
        mjdw = $(this).val();
    });
    if (isNotBlank(mjdw)) {
        bdcXmData.mjdw = mjdw;
    }
    bdcXmData.jbxxid = jbxxid;

    getReturnData("/slym/xm/jbxx", JSON.stringify(bdcXmData), 'PATCH', function (data) {
    }, function (err) {
        throw err;
    }, false);

}

// 刷新页面中保存交易信息和房屋信息数据的模块数据
function refreshJyxxAndFwxx(){
    $.ajax({
        url: getContextPath() + "/ycsl",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {gzlslid: processInsId, xmid: xmid},
        success: function (data) {
            // 权利信息
            generateQlxx(data,bdcSlXm);
            //加载房屋交易信息
            generateFwjyxx(data.bdcSlJyxxDO,data.bdcSlFwxxDO,bdcSlXm);
            //加载房屋信息模块
            generateFwxx(data.bdcSlFwxxDO);
            //加载发票信息模块
            generateFpxx(data.bdcZrfSwxxList,data.bdcSlJyxxDO);
            form.render();
            renderSelect();
            getStateById(readOnly, formStateId, 'ycym');
            form.render("select");
            disabledAddFa();
            renderDate(form);
            removeModal();
            reloadZjhYz();
        }
    });
}
function dealSpfJyxx(data) {
    getReturnData("/ycsl/jyxx/dealSpfClfJyxx?xmid=" + xmid, JSON.stringify(data), "POST", function () {
        removeModal();
        refreshJyxxAndFwxx();
        ityzl_SHOW_SUCCESS_LAYER("导入成功。");
    }, function (error) {
        console.info(error);
        removeModal();
        ityzl_SHOW_WARN_LAYER("导入失败。");
    }, false)
}
