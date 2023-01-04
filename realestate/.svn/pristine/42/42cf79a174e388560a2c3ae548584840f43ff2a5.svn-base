var gzlslid = getQueryString('processInsId');
var xmid;
var bdcdyh;
layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    $($('select[disabled="disabled"]')[1]).after('<img src="../images/lock.png" alt="">');

    $('.bdc-date-disabled').append('<img src="../images/lock.png" alt="">');

    //3. 滚动时头部固定
    var $cnotentTitle = $('.bdc-content-fix');
    var $navContainer = $('.bdc-nav-container');

    function defaultStyle() {
        if ($cnotentTitle.length == 1) {
            $('.bdc-form-div').css('padding-top', '68px');
        }
        if ($(window).scrollTop() > 85) {
            $cnotentTitle.css('top', '0');
            $navContainer.css('top', '58px');
        } else if ($(window).scrollTop() <= 85) {
            $cnotentTitle.css('top', '10px');
            $navContainer.css('top', '68px');
        }
    }

    $(window).resize(function () {
        defaultStyle();
    });
    var scrollIndex = 0;
    var slickLength;
    $(window).on('scroll', function () {
        if ($(this).scrollTop() > 85) {
            $cnotentTitle.css('top', '0');
            $navContainer.css('top', '58px');
        } else if ($(this).scrollTop() <= 85) {
            $cnotentTitle.css('top', '10px');
            $navContainer.css('top', '68px');
        }

        //为页面添加页面滚动监听事件
        var wst = $(window).scrollTop();
        scrollIndex++;
        if (scrollIndex == 1) {
            slickLength = $('.slick-slide').length - $('.slick-cloned').length;
        }
        //超过8个，需要左右切换的情况
        // slickIndex的值是一屏展示的slick的个数8，
        var slickIndex = 8;
        var $slickNow = $($('.slick .slick-slide')[8]);
        for (i = 1; i <= slickLength; i++) {
            if ($("#slick" + i).offset().top - 108 <= wst) {
                slickIndex = 8 + i;
                $('.slick .slick-slide').removeClass('bdc-this');
                $slickNow = $($('.slick .slick-slide')[slickIndex]);
                $slickNow.addClass('bdc-this');
            }
            if (wst == 0) {
                $('.slick .slick-slide').removeClass('bdc-this');
                $slickNow = $($('.slick .slick-slide')[8]);
                $slickNow.addClass('bdc-this');
            }
        }
        if (!$slickNow.hasClass('slick-active')) {
            $('.slick-next').click();
        }
    });

    //4. 仿锚点连接
    $("#p2").click(function () {
        $("html, body").animate(
            {scrollTop: $("#div2").offset().top},
            {duration: 500, easing: "swing"}
        );
    });

    var $slick = $('.slick');
    if ($slick.children().length > 8) {
        $slick.slick({
            slidesToShow: 8,
            slidesToScroll: 8
        });
    } else {
        $slick.slick({
            arrows: false,
            slidesToShow: 8,
            slidesToScroll: 8
        });
    }
    $('.slick a').on('click', function () {
        var $parent = $(this).parent();
        $parent.addClass('bdc-this').siblings().removeClass('bdc-this');

        var getId = $(this).data('id');
        if ($('#' + getId).parents('.basic-info').offset().top == 118) {
            $("html,body").animate({scrollTop: $('#' + getId).parents('.basic-info').offset().top - 124 + "px"}, 200);
        } else {
            $("html,body").animate({scrollTop: $('#' + getId).parents('.basic-info').offset().top - 108 + "px"}, 200);
        }

    });

    form.verify({
        required: function (value, item) {
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
            }
        }
    });

    $('#submit-btn').on('click', function () {
        var ywxxData = {};
        $('.ywxx').serializeArray().forEach(function (item, index) {
            ywxxData[item.name] = item.value;
        });
        ywxxData.xmid = xmid;
        console.log('保存的信息：');
        console.log(ywxxData);
        addModel();
        $.ajax({
            url: '/realestate-natural-ui/rest/v1.0/ywxx/zrzydjdy',
            type: 'PATCH',
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(ywxxData),
            success: function (data) {
                // if (!isNullOrEmpty(data)) {
                ityzl_SHOW_SUCCESS_LAYER("保存成功");
                // }
                removeModal();
            },
            error: function (xhr, status, error) {
                ityzl_SHOW_WARN_LAYER("保存失败");
                console.log(error);
                removeModal();
            }
        });

        return false;
    });

    $('.layui-input').on('focus', function () {
        if ($(this).hasClass('layui-form-danger')) {
            $(this).removeClass('layui-form-danger');
        }
        if ($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')) {
            $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
        }
    });
    $('.xm-input').on('click', function () {
        if ($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')) {
            $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
        }
    });

    //监听input修改
    $('.layui-input').on('change', function () {
        $(this).addClass('bdc-change-input');
    });

    //layui自带下拉框
    form.on('select(testFilter)', function (data) {
        console.log(data.elem); //得到select原始DOM对象
        console.log(data.value); //得到被选中的值
        console.log(data.othis); //得到美化后的DOM对象
        data.othis.find('.layui-input').addClass('bdc-change-input');
    });
    $('.bdc-city').val('1');
    form.render('select');

    var $titleBtnBox = $('.title-btn-area');
    var thirdIn = false;
    $titleBtnBox.on('mouseenter', '.bdc-test-btn', function () {
        $('.bdc-test').show();
        $('.bdc-test1').hide();
    });
    $titleBtnBox.on('mouseenter', '.bdc-dy-btn', function () {
        $('.bdc-test').hide();
        $('.bdc-test1').show();
    });
    //二级按钮鼠标覆盖
    $titleBtnBox.on('mouseenter', '.bdc-title-secondary-btn', function () {
        $('.bdc-third-btn-box').show();
    }).on('mouseleave', '.bdc-title-secondary-btn', function () {
        setTimeout(function () {
            if (!thirdIn) {
                $('.bdc-third-btn-box').hide();
            }
        }, 100);
    });
    $titleBtnBox.on('mouseenter', '.bdc-third-btn-box', function () {
        thirdIn = true;
    }).on('mouseleave', '.bdc-third-btn-box', function () {
        thirdIn = false;
    });
    $titleBtnBox.on('mouseleave', function () {
        $('.bdc-table-btn-more').hide();
    });
    $('.bdc-table-btn-more a').on('click', function () {
        $(this).parent().hide();
    });

    addModel();
    var pageData = {};
    loadData();
    loadZdList();

    getZd(function (data) {
        // 渲染字典项
        $.each(data.syqztlx, function (index, item) {
            $('#syqzt').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });

        $.each(data.qlxsfs, function (index, item) {
            $('#qlxsfs').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });

        form.render('select');
    });

    function getZd(callback) {
        if (pageData.zdList && pageData.zdList.djlx) {
            callback(pageData.zdList);
        }
    }

    console.log('pageData');
    console.log(pageData);

    // 处理登记时间
    if (!isNullOrEmpty(pageData) && !isNullOrEmpty(pageData.zrzyBdcqlglxxList)) {
        pageData.zrzyBdcqlglxxList.forEach(function(item, index) {
            if (item.djsj) {
                item.djsj = getYearMonthDay(item.djsj);
            }
        });
    }

    // 处理收件时间
    if (pageData.zrzyXm && pageData.zrzyXm.slsj) {
        pageData.zrzyXm.slsj = getYearMonthDay(pageData.zrzyXm.slsj);
    }

    if (!isNullOrEmpty(pageData)) {
        generateJbxx(pageData);
        generateQszkxx(pageData);
        generateZrzkxx(pageData);
        generateZrzkxxForm(pageData);
        generateGlxxGtkj(pageData);
        generateGlxxBdcq(pageData);
        generateGlxxKyq(pageData);
        generateGlxxQs(pageData);
        generateGlxxPw(pageData);
        removeModal();
        //遍历页面所有的input
    } else {
        generateZrzkxxForm({});
        generateGlxxGtkj({});
        generateGlxxBdcq({});
        generateGlxxKyq({});
        generateGlxxQs({});
        generateGlxxPw({});
        removeModal();
    }
    //changeData();
    function changeData(){
        //搞定 type=text, 同时如果checkbox,radio,select>option的值有变化, 也绑定一下, 这里忽略button
        $("input").each(function(){
            if (isNullOrEmpty($(this).val())){
                return;
            }
            if(isInt($(this).val())){
                $(this).val($(this).val() + ".00");
            }
        });
    }


    function isInt(val){
        var regPos = /^\d+$/;
        if(regPos.test(val)){
            return true;
        }else{
            return false;
        }
    }

    function loadData() {
        $.ajax({
            url: '/realestate-natural-ui/rest/v1.0/ywxx/' + gzlslid,
            type: 'GET',
            dataType: 'json',
            async: false,
            success: function (data) {
                console.log('data:');
                console.log(data);
                if (data) {
                    pageData = data;
                    xmid = data.zrzyXm.xmid;
                    bdcdyh = data.zrzyXm.zrzydjdyh;
                }
            },
            error: function (xhr, status, error) {
            }
        });
    }

    function loadZdList() {
        $.ajax({
            url: '/realestate-natural-ui/rest/v1.0/ywxx/zd',
            type: 'GET',
            dataType: 'json',
            async: false,
            success: function (data) {
                console.log('zdList:');
                console.log(data);
                if (data) {
                    pageData.zdList = data;
                }
            },
            error: function (xhr, status, error) {
            }
        });
    }

    function generateJbxx(pageData) {
        if (!isNullOrEmpty(pageData.zrzyXm)) {
            for (let key in pageData.zrzyXm) {
                evalElem('jbxx', key, pageData.zrzyXm[key]);
            }
            form.render();
        }
    }

    function generateQszkxx(pageData) {
        if (!isNullOrEmpty(pageData.zrzyDjdy)) {
            if(pageData.zrzyDjdy.djdyzmj){
                pageData.zrzyDjdy.djdyzmj = pageData.zrzyDjdy.djdyzmj.toFixed(2);
            }
            for (let key in pageData.zrzyDjdy) {
                evalElem('qszkxx', key, pageData.zrzyDjdy[key]);
            }
            form.render();
        }
    }

    function generateZrzkxx(pageData) {
        if (!isNullOrEmpty(pageData.zrzyDjdy)) {
            for (var key in pageData.zrzyDjdy) {
                evalElem('zrzkxx', key, pageData.zrzyDjdy[key]);
            }
            form.render();
        }
    }

    function generateZrzkxxForm(pageData) {
        var json = {};
        if (pageData.zrzyZrzkList) {
            pageData.zrzyZrzkList.forEach(function(it, i) {
                if(it.gymj){
                    it.gymj = it.gymj.toFixed(2);
                }
                if(it.jtmj){
                    it.jtmj = it.jtmj.toFixed(2);
                }
                if(it.zyqmj){
                    it.zyqmj = it.zyqmj.toFixed(2);
                }
            });
            json = pageData.zrzyZrzkList;

        } else {
            json = [];
        }

        console.log('zrzyZrzkList');
        console.log(json);
        var tpl = zrzkxxTpl.innerHTML;
        var view = $('#zrzkxxView').get(0);

        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
    }

    function generateGlxxGtkj(pageData) {
        var json = {};
        if (pageData.zrzyGggzglxxList) {
            pageData.zrzyGggzglxxList.forEach(function(it, i) {
                if(it.mj){
                    it.mj = it.mj.toFixed(2);
                }
                if(it.gdsdsj){
                    it.gdsdsj = getYearMonthDay(it.gdsdsj);
                }

            });
            json = pageData.zrzyGggzglxxList;
        } else {
            json = [];
        }
        var tpl = glxxGtkjTpl.innerHTML;
        var view = $('#glxxGtkjView').get(0);

        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
    }

    function generateGlxxBdcq(pageData) {
        var json = {};
        if (pageData.zrzyBdcqlglxxList) {
            pageData.zrzyBdcqlglxxList.forEach(function(it, i) {
                if(it.mj){
                    it.mj = it.mj.toFixed(2);
                }
            });
            json = pageData.zrzyBdcqlglxxList;
        } else {
            json = [];
        }
        //字典转换
        if(json &&json.length >0){
            for (var i = 0, len = json.length; i < len; i++) {
                for (var j = 0, zdlen = pageData.zdList.qllx.length; j < zdlen; j++) {
                    if (pageData.zdList.qllx[j].DM == json[i].qllx) {
                        json[i].qllxmc = pageData.zdList.qllx[j].MC;
                    }
                }
            }

        }
        var tpl = glxxBdcqTpl.innerHTML;
        var view = $('#glxxBdcqView').get(0);

        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
    }

    function generateGlxxKyq(pageData) {
        var json = {};
        if (pageData.zrzyKyqglxxList) {
            json = pageData.zrzyKyqglxxList;
        } else {
            json = [];
        }
        var tpl = glxxKyqTpl.innerHTML;
        var view = $('#glxxKyqView').get(0);

        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
    }

    function generateGlxxQs(pageData) {
        var json = {};
        if (pageData.zrzyQsqglxxList) {
            json = pageData.zrzyQsqglxxList;
        } else {
            json = [];
        }
        var tpl = glxxQsTpl.innerHTML;
        var view = $('#glxxQsView').get(0);

        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
    }

    function generateGlxxPw(pageData) {
        var json = {};
        if (pageData.zrzyPwqglxxList) {
            json = pageData.zrzyPwqglxxList;
        } else {
            json = [];
        }

        var tpl = glxxPwTpl.innerHTML;
        var view = $('#glxxPwView').get(0);

        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
    }

    function evalElem(containerId, elemId, elemVal) {
        if (containerId === 'jbxx') {
            $('#' + containerId).find('#' + elemId).val(elemVal);
            $('#' + containerId).find('#' + elemId).attr('disabled', 'off');
            // 移除下拉框图标
            $('#' + containerId).find('.layui-edge').remove();
        } else if (containerId === 'qszkxx' || containerId === 'zrzkxx') {
            $('#' + containerId).find('#' + elemId).val(elemVal);
        }

    }

    // 截取时间到年月日
    function getYearMonthDay(time) {
        if (!isNullOrEmpty(time)) {
            var index = time.indexOf(' ');
            return time.substring(0, index);
        }

    }

    window.refreshSlym = function() {
        loadData();
        loadZdList();

        getZd(function (data) {
            // 渲染字典项
            $.each(data.syqztlx, function (index, item) {
                $('#syqzt').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });

            $.each(data.qlxsfs, function (index, item) {
                $('#qlxsfs').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });

            form.render('select');
        });

        // 处理登记时间
        if (!isNullOrEmpty(pageData) && !isNullOrEmpty(pageData.zrzyBdcqlglxxList)) {
            pageData.zrzyBdcqlglxxList.forEach(function(item, index) {
                if (item.djsj) {
                    item.djsj = getYearMonthDay(item.djsj);
                }
            });
        }

        // 处理收件时间
        if (pageData.zrzyXm.slsj) {
            pageData.zrzyXm.slsj = getYearMonthDay(pageData.zrzyXm.slsj);
        }

        if (!isNullOrEmpty(pageData)) {
            generateJbxx(pageData);
            generateQszkxx(pageData);
            generateZrzkxx(pageData);
            generateZrzkxxForm(pageData);
            generateGlxxGtkj(pageData);
            generateGlxxBdcq(pageData);
            generateGlxxKyq(pageData);
            generateGlxxQs(pageData);
            generateGlxxPw(pageData);
            removeModal();
        } else {
            generateZrzkxxForm({});
            generateGlxxGtkj({});
            generateGlxxBdcq({});
            generateGlxxKyq({});
            generateGlxxQs({});
            generateGlxxPw({});
            removeModal();
        }
        console.log('刷新成功');
    }


    $('#bdcswt').on('click', function () {
        addModel();
        $.ajax({
            url: '/realestate-natural-ui/rest/v1.0/ywxx/bdcswt',
            type: 'GET',
            data: {},
            success: function (data) {
                removeModal();
                window.open(data + "?externalFlag=zrzy&zrzydjdyh="+bdcdyh,"_blank")
            },
            error: function (xhr, status, error) {

                removeModal();
            }
        });

        return false;
    });

});

function showDedail(zkxxid, zrzklx) {
    console.log('zkxxid');
    console.log(zkxxid);
    console.log('zrzklx');
    console.log(zrzklx);


    var zrzkType;

    switch (zrzklx) {
        case 'SZY':
            zrzkType = 'szy';
            break;
        case 'HY':
            zrzkType = 'hy';
            break;
        case 'SL':
            zrzkType = 'sl';
            break;
        case 'CY':
            zrzkType = 'cy';
            break;
        case 'HD':
            zrzkType = 'hd';
            break;
        case 'WJMHD':
            zrzkType = 'wjmhd';
            break;
        case 'TMCLKCZY':
            zrzkType = 'tmclkczy';
            break;
        case 'SD':
            zrzkType = 'sd';
            break;
    }

    layer.open({
        type: 2,
        area: ['100%', '100%'],
        fixed: true,
        title: '详情',
        content: '/realestate-natural-ui/view/ywxx/zrzkxx/' + zrzkType + '.html?' + 'zkxxid=' + zkxxid + '&' + 'zrzklx=' + zrzklx,
        btnAlign: "c",
        success: function () {
        }
    });

}