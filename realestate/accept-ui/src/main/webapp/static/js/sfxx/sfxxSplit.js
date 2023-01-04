var processInsId = getQueryString("processInsId");
var xmid = getQueryString("xmid");
var jkfs = getQueryString("jkfs");
var layer;
layui.use(['form','jquery','element','layer','laytpl'],function () {
     $ = layui.jquery;
     form = layui.form;
     layer = layui.layer;
     laytpl = layui.laytpl;

    var isSubmit = true;
    var verifyMsg = "订单金额不能为0或空";
    form.verify({
        required: function (value, item) {
            if (value == '' || value == null || value == undefined || value == '0') {//判断是否为空
                $(item).addClass('layui-form-danger');
                isSubmit = false;
            }
        }
        //要求正整数，允许为空
        , number: [/^[1-9]+[0-9]*]*$/, '请输入非负整数']
        //数字
        , number2: [/^-?\d*$/, '请输入数字']
    });

    $('#popupTwoRows').on('focus','.layui-input',function () {
        if($(this).hasClass('layui-form-danger')){
            $(this).removeClass('layui-form-danger');
        }
        if($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')){
            $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
        }
    });
    $('#popupTwoRows').on('click','.xm-input',function () {
        if($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')){
            $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
        }
    });

    if(!isNotBlank(xmid)){
        getReturnData("/slym/xm/listBdcXm", {gzlslid: processInsId}, 'GET', function (xmxxList) {
            if(xmxxList &&xmxxList.length >0) {
                xmid = xmxxList[0].xmid;
            }

        },function (error) {
            
        },false);
    }
    loadQlrAndYwrZje();

    //提交保存数据
    form.on('submit(submitBtn)', function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            if(verifyDdje()){
                saveDdxx()
            }
            // 禁止提交后刷新
            return false;
        }
    });


    // 保存拆分订单信息
    function saveDdxx(){
        var ddxx = new Array();
        ddxx.push(getDdxxData("1", $("#qlr-sfmxze").val(), "#qlrsfxx .split-line"));
        ddxx.push(getDdxxData("2", $("#ywr-sfmxze").val(), "#ywrsfxx .split-line"));
        addModel();
        $.ajax({
            url: getContextPath() + "/sfss/ddxx/scddxx?jkfs=" + jkfs,
            type: 'POST',
            contentType:"application/json",
            dataType: 'json',
            data: JSON.stringify(ddxx),
            success: function (data) {
                removeModal();
                //当缴款方式为合一支付时
                if (jkfs === "2") {
                    if (isNotBlank(data)) {
                        ityzl_SHOW_SUCCESS_LAYER( "创建成功。");
                        $.each(data,function(index, value){
                            if(value.qlrlb == "1" && value.jfurl){
                                var jfurl = "/realestate-accept-ui/sf/ewm?ewmnr=" + encodeURIComponent(value.jfurl);
                                $(".qlrzfewm").attr('src', jfurl);
                                $(".qlrzfewm").click(function () {
                                    var _this = $(this);//将当前的pimg元素作为_this传入函数
                                    imgShow("#qlrouterdiv", "#qlrinnerdiv", "#qlrbigimg", _this);
                                });
                            }
                            if(value.qlrlb == "2" && value.jfurl){
                                var jfurl = "/realestate-accept-ui/sf/ewm?ewmnr=" + encodeURIComponent(value.jfurl);
                                $(".ywrzfewm").attr('src', jfurl);
                                $(".ywrzfewm").click(function () {
                                    var _this = $(this);//将当前的pimg元素作为_this传入函数
                                    imgShow("#ywrouterdiv", "#ywrinnerdiv", "#ywrbigimg", _this);
                                });
                            }
                        });
                        $("#ewmForm").show();
                    }
                    return;
                }
                ityzl_SHOW_SUCCESS_LAYER( "创建成功。");
                var url="/realestate-accept-ui/view/slym/posjf.html?processInsId="+processInsId+"&xmid="+xmid;
                closeWin();
                parent.layerCommonOpenFull(url, "POS缴费页面", '1150px', '600px');
            },
            error: function (err) {
                removeModal();
                delAjaxErrorMsg(err);
            }
        });
    }

    // 获取订单内容
    function getDdxxData(qlrlb, ze, element){

        var cfddxxList =[];

        if($(element).length>0){
            $(element).find("input").each(function (i,ele) {

                var cfddxx={
                    ddje:$(ele).val()
                };
                cfddxxList.push(cfddxx);
            });
        }else{
            var cfddxx={
                ddje:ze
            };
            cfddxxList.push(cfddxx);
        }
        return {
            xmid : xmid,
            gzlslid : processInsId,
            qlrlb : qlrlb,
            ze : ze,
            cfddxxList : cfddxxList
        };
    }

    // 拆分订单
    $('.split a').on('click',function () {
        var index = $(this).parents('.layui-form-item').find('.bdc-delete').length;
        var json,tpl,view;
        view = $(this).parents('.split');
        json = {
            index:index,
        };

        if(index == '0'){
            // 订单数为0时点击一次拆分2条
            tpl = addDoubleTpl.innerHTML;
        }else{
            tpl = addTpl.innerHTML;
        }
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.before(html);
        });
    });

    // 删除订单
    $(document).on('click','.bdc-delete',function () {
        var index = $(this).parents('.layui-form-item').find('.bdc-delete').length;

        if(index <=2 ){
            //订单数为2时点击一次删除全部
            $(this).parents('.layui-form-item').find('.split-line').remove();
        }else {
            $(this).parents('.split-line').remove();
        }
    });

});

// 加载权利人与义务人税费总金额
function loadQlrAndYwrZje(){
    addModel();
    $.ajax({
        url: getContextPath() + "/sfss/ddxx/getSfzje",
        type: 'GET',
        dataType: 'json',
        data: {
            xmid : xmid,
            gzlslid : processInsId
        },
        success: function (data) {
            removeModal();
            if(data){
                $("#qlr-sfmxze").val(data.qlrzje);
                $("#ywr-sfmxze").val(data.ywrzje);
            }else{
                ityzl_SHOW_WARN_LAYER("未获取到权利人和义务人税费总金额。");
            }
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

function verifyDdje(){
    var qlrzje = $("#qlr-sfmxze").val();
    if($("#qlrsfxx .split-line").length > 0){
        if(compareJe(qlrzje,"#qlrsfxx .split-line" )){
            layer.msg("权利人税费总金额与拆单后的金额总和不一致！", {icon: 5, time: 3000});
            return false;
        }
    }
    var ywrzje = $("#ywr-sfmxze").val();
    if($("#ywrsfxx .split-line").length > 0){
        if(compareJe(ywrzje,"#ywrsfxx .split-line" )){
            layer.msg("义务人税费总金额与拆单后的金额总和不一致！", {icon: 5, time: 3000});
            return false;
        }
    }
    return true;
}

function compareJe(zje, element){
    var cdje = 0;
    $(element).find("input").each(function (i,ele) {
        cdje += parseFloat($(ele).val());
    });
    return parseFloat(zje).toFixed(2) != parseFloat(cdje).toFixed(2);
}
/*
* 点击图片放大功能
* */

function imgHyzfShow(outerdiv, innerdiv, bigimg, _this, height, width) {
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性

    /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function () {
        var windowW = $(window).width();//获取当前窗口宽度
        var windowH = $(window).height();//获取当前窗口高度
        var realWidth = this.width;//获取图片真实宽度
        var realHeight = this.height;//获取图片真实高度
        var imgWidth, imgHeight;
        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

        if (realHeight > windowH * scale) {//判断图片高度
            imgHeight = windowH * scale;//如大于窗口高度，图片高度进行缩放
            imgWidth = imgHeight / realHeight * realWidth;//等比例缩放宽度
            if (imgWidth > windowW * scale) {//如宽度扔大于窗口宽度
                imgWidth = windowW * scale;//再对宽度进行缩放
            }
        } else if (realWidth > windowW * scale) {//如图片高度合适，判断图片宽度
            imgWidth = windowW * scale;//如大于窗口宽度，图片宽度进行缩放
            imgHeight = imgWidth / realWidth * realHeight;//等比例缩放高度
        } else {//如果图片真实高度和宽度都符合要求，高宽不变
            imgWidth = realWidth;
            imgHeight = realHeight;
        }
        $(bigimg).css("width", imgWidth);//以最终的宽度对图片缩放

        var w = (windowW * 0.4) / 2;//计算图片与窗口左边距
        var h = (windowH * 0.5) / 2;//计算图片与窗口上边距
        if (isNotBlank(width)) {
            w = width;
        }
        if (isNotBlank(height)) {
            h = height;
        }
        $(innerdiv).css({"top": h, "left": w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function () {//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}