/**
 * Created by ypp on 2019/12/6.
 * 权利信息与单元信息合并
 */
var isQlError = '';
layui.use(['jquery','form'], function(){
    var $ = layui.jquery,
        form = layui.form;

    //获取url参数
    var xmid = getQueryString("xmid");
    var qllx = getQueryString("qllx");
    var bdcdyfwlx = getQueryString("bdcdyfwlx");
    var sfyql = getQueryString("sfyql");
    var formStateId = getQueryString("formStateId");
    var readOnly = getQueryString("readOnly");
    var zxlc = getQueryString("zxlc");
    var processInsId = getQueryString("processInsId");
    //加载权利信息
    if (xmid !== "" && xmid !== null) {
        qllx = parseInt(qllx);
        bdcdyfwlx = parseInt(bdcdyfwlx);
        var qllxym = getQlxxYm(qllx, bdcdyfwlx);
        var url;
        //展示原权利，不可编辑
        if (sfyql == 'true') {
            url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=true" + "&isCrossOri=false";
        } else {
            url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&isCrossOri=false";
        }
        $('.bdc-qlxx').attr('src',url);
    }
    //加载单元信息
    $('.bdc-dyxx').attr('src',getContextPath() + "/view/slym/bdcdy.html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&zxlc=" + zxlc + "&qlhb=" + true+"&processInsId=" + processInsId);

    var $cnotentTitle = $('.bdc-content-fix');
    var $navContainer = $('.bdc-nav-container');
    function defaultStyle() {
        if($cnotentTitle.length == 1){
            $('.bdc-form-div').css('padding-top','68px');
        }
        if($(window).scrollTop() > 85){
            $cnotentTitle.css('top','0');
            $navContainer.css('top','58px');
        }else if($(window).scrollTop() <= 85){
            $cnotentTitle.css('top','10px');
            $navContainer.css('top','68px');
        }
    }

    $(window).resize(function(){
        defaultStyle();
    });
    $(window).on('scroll',function () {
        if($(this).scrollTop() > 85){
            $cnotentTitle.css('top','0');
            $navContainer.css('top','58px');
        }else if($(this).scrollTop() <= 85){
            $cnotentTitle.css('top','10px');
            $navContainer.css('top','68px');
        }
    });

    var hideIndex1 = 0;
    var hideIndex2 = 0;
    var hideBtnInterval = setInterval(function(){
        if($(".bdc-qlxx").contents().find(".bdc-content-fix").length > 0){
            hideIndex1++;
            if(hideIndex1 == 1){
                $(".bdc-qlxx").contents().find(".bdc-content-fix").addClass('bdc-hide');
                $('.bdc-add-title>p').html($(".bdc-qlxx").contents().find(".content-main .table-name").html());
            }
        }
        if($(".bdc-dyxx").contents().find(".pannel-form-btns").length > 0){
            hideIndex2++;
            if(hideIndex2 == 1){
                $(".bdc-dyxx").contents().find(".pannel-form-btns").addClass('bdc-hide');
            }
        }

        if(hideIndex1 > 0 && hideIndex2 > 0){
            clearInterval(hideBtnInterval);
        }
    },100);
    var heightIndex = 0;
    var heightInterval = setInterval(function(){
        if($(".bdc-qlxx").contents().find(".bdc-content-box").length > 0){
            heightIndex++;
            $('.bdc-first-container').height($(".bdc-qlxx").contents().find(".bdc-content-box").height()+10);
            if(heightIndex == 50){
                clearInterval(heightInterval);
            }
        }
    },100);

    $('#submitBtn').on('click',function(){
         addModel('保存中');
        setTimeout(function(){
            $(".bdc-qlxx").contents().find("#submitBtn").click();
            $('iframe').contents().find('#waitModalLayer').css('display','none');
            var qlyzInterval = setInterval(function(){
                if(isQlError == 'tg'){
                    clearInterval(qlyzInterval);
                    isQlError = '';
                    $(".bdc-dyxx").contents().find("#updateBdcdy").click();
                    $('iframe').contents().find('#waitModalLayer').css('display','none');
                    removeModal();
                }else if(isQlError == 'wtg'){
                    clearInterval(qlyzInterval);
                    isQlError = '';
                    removeModal();
                }
            },50);
        },50);
        var layerIndex = 0;
        var setLayerTopInterval = setInterval(function(){
            if($(".bdc-qlxx").contents().find(".layui-layer").length > 0){
                layerIndex++;
                if($(document).scrollTop() < 70){
                    $(".bdc-qlxx").contents().find(".layui-layer").css('top','70px');
                }else {
                    $(".bdc-qlxx").contents().find(".layui-layer").css('top',$(document).scrollTop() + 'px');
                }
            }
            if(layerIndex == 15){
                clearInterval(setLayerTopInterval);
            }
        },100);
    });
});

function saveHtxxAfter(qllx,djxl){
// 保存受理页面的收费信息
    if (parent && 'function' === typeof (parent.saveSf)) {
        parent.saveSf();
    }
    ityzl_SHOW_SUCCESS_LAYER("单元信息保存成功");

    if (parent && 'function' === typeof (parent.getAllBdcdy)) {
        //修改完成后，外面表格刷新一次。
        //处理头部的面积等信息
        if (parent && 'function' === typeof (parent.dealHjxx)) {
            parent.dealHjxx();
        }
    }else{
        //处理头部的面积等信息
        if (parent && 'function' === typeof (parent.dealHjxx)) {
            parent.dealHjxx(qllx,djxl);
        }
    }

    var config = parent.tableConfig;
    var pageNum = parent.$('.layui-laypage-default').find('span:eq(0)').find('input').val();
    // 分页情况下，非第一页，重载后需要翻到当前页
    if(pageNum && parseInt(pageNum)>1){
        config['page'] = {'curr':parseInt(pageNum)}
    }
    parent.loadDataTablbeByUrl('#bdcdyTable', config);

    if(window.parent.$('.layui-laypage-btn')[0] != undefined){
        window.parent.$('.layui-laypage-btn')[0].click();
    }
    removeModal();
}

//权利信息与单元信息内部某些值改变联动
function getSelectData(name,data){
    var $dyxx = $(".bdc-dyxx");
    //权利信息修改 规划用途--同步单元信息 定着物用途
    if(name == 'ghyt'){
        $dyxx.contents().find("[name='dzwyt']").val(data.value);
        $dyxx[0].contentWindow.layui.form.render('select');
    }
    //权利信息修改 权利性质--同步单元信息 权利性质
    if(name == 'qlxz'){
        $dyxx.contents().find("[name='qlxz']").val(data.value);
        $dyxx[0].contentWindow.layui.form.render('select');
    }
    //如果权利性质是input
    if (name == 'qlxzInput') {
        $dyxx.contents().find("[name='qlxz']").val(data);
        $dyxx[0].contentWindow.layui.form.render('select');
    }
    //权利信息修改 建筑面积--同步单元信息 定着物面积
    if (name == 'jzmj') {
        $dyxx.contents().find("[name='dzwmj']").val(data);
    }
    if (name == 'jyhthql') {
        $dyxx.contents().find("[name='jyhth']").val(data);
    }

    var $qlxx = $(".bdc-qlxx");
    //单元信息修改 定着物用途--同步权利信息 规划用途
    if (name == 'dzwytFilter') {
        $qlxx.contents().find("[name='ghyt']").val(data.value);
        $qlxx[0].contentWindow.layui.form.render('select');
    }
    //单元信息修改 权利性质--同步权利信息 权利性质
    if (name == 'qlxzFilter') {
        $qlxx.contents().find("[name='qlxz']").val(data.value);
        $qlxx[0].contentWindow.layui.form.render('select');
    }
    //单元信息修改 定着物面积--同步权利信息 建筑面积
    if (name == 'dzwmj') {
        $qlxx.contents().find("[name='jzmj']").val(data);
    }
    if (name == 'jyhthdy') {
        $qlxx.contents().find("[name='jyhth']").val(data);
    }
}

//判断权利信息页面是否执行验证结束
function qlxxIsYzEnd(isError){
    isQlError = isError;
}