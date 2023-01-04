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
 *  获取权利其他状况或者附记模板信息
 */
function queryQlqtzkFjMb(xmid, $qlqtzkfjmb, mode) {
    getReturnData("/slym/ql/queryQlqtzkFjMb", {xmid: xmid, mode: mode}, "GET", function (data) {
        if (isNotBlank(data)) {
            //赋值权利其他状况或者附记
            $qlqtzkfjmb.val(data);

        }
    }, function (error) {
        delAjaxErrorMsg(error);
    })
}

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

// 顶部按钮操作事件-蚌埠受理页面统用
function titleShowUi() {
    $(".print-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        $("#third-prinbtn").hide();
        $("#print").show();
        setUiWidth($(this), $("#print"));
    });
    $(".secondary-printbtn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        $("#print").show();
        $("#third-prinbtn").show();
        setThirdUiWidth($(this), $("#third-prinbtn"));
    });

    $(".jyxx-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        $("#third-prinbtn").hide();
        $("#jyxx").show();
        setUiWidth($(this), $("#jyxx"));
    });

    $(".more-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#jyxx").hide();
        $("#dzzzxx").hide();
        $("#third-prinbtn").hide();
        $("#moreBtn").show();
        setUiWidth($(this), $("#moreBtn"));
    });
    $(".dzzz-button").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#third-prinbtn").hide();
        $("#dzzzxx").show();
        setUiWidth($(this), $("#dzzzxx"));
    });
    $(".sqs-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#xwbl").hide();
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#third-prinbtn").hide();
        $("#sqs").show();
        setUiWidth($(this), $("#sqs"));
    });

    $(".xwbl-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#sqs").hide();
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#third-prinbtn").hide();
        if(qlxx){
            var dyaq = false, same = true;
            $.each(qlxx,function(index, value){
                if(index == 0){
                    dyaq = value.dydj;
                    return true;
                }
                if(dyaq != value.dydj ){
                    same = false;
                }
            });
            if(same){
                if(dyaq){
                    printData('dyaxwblpl');
                    console.log("打印抵押询问笔录")
                } else {
                    printData('grxwblpl');
                    console.log("打印产权询问笔录");
                }
            }else{
                $("#xwbl").show();
                setUiWidth($(this), $("#xwbl"));
            }
        }else{
            $("#xwbl").show();
            setUiWidth($(this), $("#xwbl"));
        }
    });

    $(".print-btn li").click(function () {
        $("#print").hide();
    });

    $(".jyxx-btn li").click(function () {
        $("#jyxx").hide();
    });

    $(".more-btn li").click(function () {
        $("#moreBtn").hide();
    });
    $(".dzzz-button li").click(function () {
        $("#dzzzxx").hide();
    });
    $(".sqs-btn li").click(function () {
        $("#sqs").hide();
    });
    $(".xwbl-btn li").click(function () {
        $("#xwbl").hide();
    });
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取商品房交易信息
     */
    $('#querySpfJyxx').click(function (e) {
        getSpfJyxx();
    });
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取存量房交易信息
     */
    $('#queryClfbaJyxx').click(function (e) {
        getClfJyxx();
    });
    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && (elem.id == 'dzzzxx' || elem.id == 'print' || elem.id == 'jyxx' || elem.id == 'moreBtn' || elem.id == "sqs" || elem.id == "xwbl") || elem.id == 'third-prinbtn') {
                return;
            }
            $("#dzzzxx").hide();
            $("#print").hide();
            $("#jyxx").hide();
            $("#moreBtn").hide();
            $("#sqs").hide();
            $("#xwbl").hide();
            $("#third-prinbtn").hide();
            elem = elem.parentNode;
        }
    });

}

function setUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    var w = buttonElement.innerWidth();
    var scrollH = $(document).scrollTop();
    if(scrollH > 0){
        uiElement.css({top: X + 42 - scrollH, right: $('body').width() - Y - w - 15});
    }else {
        uiElement.css({top: X + 32, right: $('body').width() - Y - w - 15});
    }
    var uiWidth = 0;
    var liArray = uiElement.find("li");
    if (liArray.length > 0) {
        var textNumber = 0;
        for (var i = 0; i < liArray.length; i++) {
            var liEl = liArray[i];
            var liText = $(liEl).text();
            if (textNumber < liText.length) {
                textNumber = liText.length;
            }
        }
        var countWidth = (textNumber+3) * 13;
        if (uiWidth < countWidth) {
            uiWidth = countWidth;
        }
    }
    uiElement.css({"min-width":w,"width": uiWidth + 20});
}


//设置第三级ului标签位置
function setThirdUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    var w = buttonElement.innerWidth();
    var scrollH = $(document).scrollTop();
    var uiWidth = 0;
    var liArray = uiElement.find("li");
    if (liArray.length > 0) {
        var textNumber = 0;
        for (var i = 0; i < liArray.length; i++) {
            var liEl = liArray[i];
            var liText = $(liEl).text();
            if (textNumber < liText.length) {
                textNumber = liText.length;
            }
        }
        var countWidth = (textNumber+3) * 13;
        if (uiWidth < countWidth) {
            uiWidth = countWidth;
        }
    }
    var top = X;
    if(scrollH > 0){
        top = top - scrollH;
    }
    // 当右边空间不足时，向左展示
    var RightY = $('body').width()- Y - w;
    if(RightY > uiWidth){
        uiElement.css({top: top, right: $('body').width() - Y - w - 15 - uiWidth});
    }else{
        uiElement.css({top: top, left: Y - uiWidth});
    }
    uiElement.css({"min-width":uiWidth,"width": uiWidth});
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
                window.open(getContextPath() + "/view/bgdb/bgxxdb.html?processInsId=" + processInsId + "&lclx=" + data);
            }
            if (data == "2" || data == "3" || data == "4") {
                window.open(getContextPath() + "/view/bgdb/bgxxdbPlzh.html?processInsId=" + processInsId + "&lclx=" + data);
            }
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    });
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

// 安徽省好差评，调用省好差评的评价页面到评价器
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
            } else{
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

function changeZjzlByQlrlx(data) {
    if(!isNullOrEmpty(qlrlxZjzlDzMap)) {
        var qlrlx = data.value;
        var $qlrlx = $(data.elem).parents('tr').find('select[name=qlrlx]');
        var $zjzl = $(data.elem).parents('tr').find('select[name=zjzl]');
        var zjzl = "";
        var zjzlZd = zdList.zjzl;
        for (var key in qlrlxZjzlDzMap) {
            if (qlrlx == key) {
                zjzl = qlrlxZjzlDzMap[key];
            }
        }
        if (isNotBlank(zjzl)) {
            var zjzlList = [];
            for (var i = 0; i < zjzlZd.length; i++) {
                if (zjzl.indexOf(zjzlZd[i].DM) > -1) {
                    zjzlList.push(zjzlZd[i]);
                }
            }
            zjzlList = zjzlList.length > 0 ? zjzlList : zjzlZd;
            $zjzl.empty();
            $zjzl.append('<option value="">请选择</option>');
            $.each(zjzlList, function (index, item) {
                $zjzl.append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            form.render('select');
        } else {
            $zjzl.empty();
            $zjzl.append('<option value="">请选择</option>');
            $.each(zjzlZd, function (index, item) {
                $zjzl.append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            form.render('select');
        }
    }
}

function changeqlrlxByZjzl(data){
    if(!isNullOrEmpty(qlrlxZjzlDzMap)) {
        var zjzl = data.value;
        if (!isNullOrEmpty(qlrlxZjzlDzMap)) {
            for (var key in qlrlxZjzlDzMap) {
                if (qlrlxZjzlDzMap[key].indexOf(zjzl) > -1) {
                    var $qlrlx = $(data.elem).parents('tr').find('select[name=qlrlx]');
                    $qlrlx.val(key);
                    break;
                }
            }
        }
        if (isNotBlank(yzsfhytsfhzjzl)) {
            var $zjh = $(data.elem).parents('tr').find('input[name=zjh]');
            var newattr = $zjh.attr("lay-verify");
            if (yzsfhytsfhzjzl.indexOf(zjzl) > -1) {
                if (isNotBlank(newattr) && newattr.indexOf("sfhytsfh") < 0) {
                    $zjh.attr("lay-verify", newattr + "|sfhytsfh");
                } else if (zjzl !== null && zjzl !== "") {
                    $zjh.attr("lay-verify", "sfhytsfh");
                } else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("sfhytsfh") > -1) {
                    $zjh.attr("lay-verify", newattr.replace("sfhytsfh", ""));
                }
            } else {
                if (isNotBlank(newattr)) {
                    $zjh.attr("lay-verify", newattr.replace("sfhytsfh", ""));
                }
            }
        }
        form.render('select');
    }
}

/**
 * 添加证件号是否含有特殊符号验证
 */
function addzjhYz(){
    $.each($("select[name=zjzl]"), function (i) {
        if(isNotBlank(yzsfhytsfhzjzl)){
            var zjzl = $(this).val();
            if(yzsfhytsfhzjzl.indexOf(zjzl) > -1){
                var $zjh = $(this).parents('tr').find('input[name=zjh]');
                if($zjh.length > 0 ){
                    var newattr  = $zjh.attr("lay-verify");
                    if (isNotBlank(newattr) && newattr.indexOf("sfhytsfh") < 0) {
                        $zjh.attr("lay-verify", newattr + "|sfhytsfh");
                    } else if (zjzl !== null && zjzl !== "") {
                        $zjh.attr("lay-verify", "sfhytsfh");
                    }else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("sfhytsfh") > -1) {
                        $zjh.attr("lay-verify", newattr.replace("sfhytsfh", ""));
                    }
                }
            }
        }

    });
}