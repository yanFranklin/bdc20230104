var xmid = getQueryString("xmid");
var processInsId = getQueryString("processInsId");
var lclx = getQueryString("lclx");
var zdList = {a:[]};
layui.use(['jquery', 'layer', 'element', 'form', 'laytpl'],function () {
    form = layui.form;
    $ = layui.jquery;
    var laytpl = layui.laytpl,
        element = layui.element;

    $(function () {
        generatePage();

        if(!($('.bdc-content-fix').hasClass('bdc-hide'))){
            //滚动时头部固定
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
        }

        //收起、展开
        $(document).on('click','.bdc-show-more',function () {
            var $this = $(this);
            var $hideList = $this.parents('.inner-basic').find('.bdc-jt-table tr:not(":first,:eq(1)")');
            if($this.html() == '收起'){
                $this.html('展开');
                $.each($hideList,function () {
                    if(!$(this).hasClass('bdc-hide')){
                        $(this).hide();
                    }
                });
            }else {
                $this.html('收起');
                $.each($hideList,function () {
                    if(!$(this).hasClass('bdc-hide')){
                        $(this).show();
                    }
                });
            }
        });
    });

    /**
     * 初始化加载页面数据
     */
    function generatePage(){
        //获取字典项数据
        getReturnData("/bdczd",{},"POST",function (data) {
            zdList = data;
        },function () {
        },false);

        // 获取变更信息
        if(lclx == '1'){
            $('.bdc-content-fix').removeClass('bdc-hide');
            $('.bdc-form-div').css('padding-top','68px');
            getBgxx().done(function(data){
                generateQlrxx(data);
                generateBdcdyxx(data);
                generateQlxx(data);
            });
        }else {
            var ywxxData =JSON.parse(sessionStorage.getItem("ywxxData"));
            generateQlrxx(ywxxData);
            generateBdcdyxx(ywxxData);
            generateQlxx(ywxxData);
        }
    }

    // 获取变更信息
    function getBgxx(){
        var deferred = $.Deferred();
        addModel();
        getReturnData("/rest/v1.0/bgxxdb/dbxx",{xmid : xmid, gzlslid : processInsId},"GET",function (data) {
            removeModal();
            if(isNotBlank(data)){
                deferred.resolve(data[0]);
            }else{
                deferred.resolve({});
            }
        },function (error) {
            removeModal();
            delAjaxErrorMsg(error);
            deferred.reject();
        });
        return deferred;
    }

    /**
     * 加载权利人对比信息
     */
    function generateQlrxx(data){
        var bgxx = [];
        if(isNotBlank(data.bdcBgxxQlrVOList) && data.bdcBgxxQlrVOList.length >0){
            $.each(data.bdcBgxxQlrVOList,function (index, value) {
                var bdcQlr = value.bdcQlrDO;
                var ybdcQlr = value.ybdcQlrDO;
                var type = value.type;
                bgxx.push({
                    bdcQlr : isNotBlank(bdcQlr)?bdcQlr:{},
                    ybdcQlr : isNotBlank(ybdcQlr)?ybdcQlr:{},
                    type : isNotBlank(type)?type:""
                });
            });
        }
        var json = {bgxx: bgxx, zd : zdList};
        var getTpl = qlrxxTpl.innerHTML;
        laytpl(getTpl).render(json, function (html) {
            $('#qlrxx').append(html);
        });
        filterDiff("qlrxx");
    }

    /**
     * 加载不动产单元对比信息
     */
    function generateBdcdyxx(data){
        var bgxx = [];
        if(isNotBlank(data)){
            bgxx.push({
                bdcXm : isNotBlank(data.bdcXm)?data.bdcXm:{},
                yBdcXm : isNotBlank(data.ybdcXm)?data.ybdcXm:{}
            });
        }
        var json = {bgxx: bgxx, zd : zdList};
        var getTpl = bdcdyxxTpl.innerHTML;
        laytpl(getTpl).render(json, function (html) {
            $('#bdcdyxx').append(html);
        });
        filterDiff("bdcdyxx");
    }

    /**
     * 加载权利对比信息
     */
    function generateQlxx(data){
        var bgxx = [];
        if(isNotBlank(data)){
            bgxx.push({
                tableName: data.tableName,
                bdcQl : isNotBlank(data.bdcQl)?data.bdcQl:{},
                ybdcQl : isNotBlank(data.ybdcQl)?data.ybdcQl:{}
            });
        }
        // 加载权利信息标题
        var json = {bgxx: bgxx, zd : zdList};
        var qlxxTitle = qlxxTitleTpl.innerHTML;
        laytpl(qlxxTitle).render(json, function (html) {
            $('#qlxx').append(html);
        });
        // 加载每个权利信息 Table
        if(bgxx.length>0){
            $.each(bgxx,function(index, value){
                var contentJson = {bgxx: value, zd : zdList};
                if(isNotBlank(value.tableName)){
                    var qllxTpl = document.getElementById(value.tableName);
                    var tpl = qllxTpl.innerHTML;
                    console.info(contentJson);
                    laytpl(tpl).render(contentJson, function (html) {
                        $('.qlxx').append(html);
                    });
                }
            });
        }
        filterDiff("qlxx");
    }

    function filterDiff(className){
        var noneDiff = 0;
        $("."+className+" table").each(function (index, table) {
            var diffNum = 0;
            $(table).find('tbody tr').each(function (i, item){
                var $str1 = $(item).find('.str1');
                var $str2 = $(item).find('.str2');

                if($str1.length>0 || $str2.length>0){
                    if($str1.length>0 && $str2.length>0){
                        // 对比信息相同行隐藏
                        if($str1.val() == $str2.val()){
                            $(item).addClass('bdc-hide');
                        }else{
                            diffNum++;
                        }
                    }else {
                        // 新增删除信息无数据行隐藏
                        if($str1.val() == '——' || $str2.val() == '——'){
                            $(item).addClass('bdc-hide');
                        }else{
                            diffNum++;
                        }
                    }
                }
            });
            if(diffNum == 0){
                if(className == 'qlrxx'){
                    $(table).parents('.inner-basic').hide();
                }else {
                    $(table).parent('.bdc-jt-table').hide();
                }
            } else{
                noneDiff++;
            }
        });
        if(noneDiff == 0){
            if(!($('.'+ className).find('.layui-none').length > 0)){
                var tpl = noDataTpl.innerHTML;
                laytpl(tpl).render({}, function (html) {
                    $("."+className).append(html);
                });
            }
        }
    }

    // 分屏验证不一致高亮显示
    function fpyzTable() {
        $('tbody tr').each(function (index,item) {
            var str1 = $(item).find('.str1').val(),
                str2 = $(item).find('.str2').val();
            if(str1 != str2){
                $(item).addClass('bdc-change-color');
            }
        });
    }

});

// 页面展示字典项dm转名称
function getZdMc(category, dm){
    var mc = "";
    if(!isNotBlank(category)){
       return mc;
    }
    if(!isNotBlank(dm) && dm!==0){
        return mc;
    }
    var categoryList = zdList[category];
    if(isNotBlank(categoryList)){
        $.each(categoryList,function(index, value){
            if(value.DM == dm){
                mc = value.MC;
                return false;
            }
        });
        return mc;
    }else{
        return dm;
    }
}

function formatDate(date){
    console.info(date);
    if(!isNotBlank(date)){
        return "";
    }
    return Format(date, "yyyy年MM月dd日");
}