
layui.use(['jquery', 'form', 'laytpl','element','table'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl;
    $(function () {

        // 获取参数
        var param = $.getUrlParam('param');
        var bdcdyh = '';
        if (param.length > zdzhhLength) {
            bdcdyh = param;
        } else {
            var zdzhsxh = param.substr(6, 6);
            if (zdzhsxh === "000000") {
                warnMsg("不允许只用虚拟地籍号查询登记簿！");
                return false;
            }
        }
        var zdzhh = param.substring(0, zdzhhLength);
        $("#zdzhh").html("宗地宗海号：" + zdzhh);
        // 目录树数据
        var asideData;


        //获取最外层元素的padding-left的值
        var containerLeft = 300;

        // 查询条件获取焦点
        $('.bdc-search').on('focus', function () {
            $(this).addClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size', '14px');
            $('.bdc-search-box .layui-icon-close').css('font-size', '14px');
        }).on('blur', function () {
            $(this).removeClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size', '12px');
            $('.bdc-search-box .layui-icon-close').css('font-size', '12px');
        })

        //按enter键
        $('.bdc-search').bind('keyup', function (event) {
            if (event.keyCode == "13") {
                //回车执行查询
                var getAsideData = JSON.parse(JSON.stringify(asideData));
                var getSearchValue = $('.bdc-search').val();
                for (var i = getAsideData.bdcDjbQlMlList.length - 1; i >= 0; i--) {
                    if (getAsideData.bdcDjbQlMlList[i].bdcdyh.indexOf(getSearchValue) == -1) {
                        if (getAsideData.bdcDjbQlMlList[i].bdcQlList && getAsideData.bdcDjbQlMlList[i].bdcQlList != 0) {
                            for (var index = getAsideData.bdcDjbQlMlList[i].bdcQlList.length - 1; index >= 0; index--) {
                                if (getAsideData.bdcDjbQlMlList[i].bdcQlList[index].name.indexOf(getSearchValue) == -1) {
                                    getAsideData.bdcDjbQlMlList[i].bdcQlList.splice(index, 1);
                                }
                            }
                            if (getAsideData.bdcDjbQlMlList[i].bdcQlList == 0) {
                                getAsideData.bdcDjbQlMlList.splice(i, 1);
                            }
                        } else {
                            getAsideData.bdcDjbQlMlList.splice(i, 1);
                        }
                    }
                }
                renderAside(getAsideData);
                // 默认打开第一个查询结果
                //判断改变色值
                if (dataArr.length != 0) {
                    for (var i = 0; i < dataArr.length; i++) {
                        if (dataArr[i].qszt == 1) {
                            qsztindex = i;
                            //djbxssjys为common.js传过来的十六进制色值
                            if (qsztindex != undefined && djbxssjys != "") {
                                var changediv = ".bdc-tr-width-dz:nth-child(" + (qsztindex + 1) + ")";
                                $(changediv).css("background-color", djbxssjys);
                            }
                        }

                    }

                }

                if (getAsideData.bdcDjbQlMlList.length > 0) {
                    liIndex = 4;
                    var nowI = 4;
                    // 设置第一个高亮
                    $('.accordion .bdc-outer-li:nth-child(' + nowI + ')').addClass('open bdc-this-li').find('.submenu').show();
                    $('.content-div iframe').attr('src', $('.accordion .bdc-outer-li:nth-child(' + nowI + ') .link').data('src'));
                }
            }
        });

        //点击删除
        $('.bdc-search-box .layui-icon-close').on('click', function () {
            //console.log('删除操作');
            $('.bdc-search').val(null);
            renderAside(asideData);
            // 根据参数定位
            indexPage();
        });

        //1. 侧边栏点击效果
        //1.1  手风琴点击效果
        $(document).on('click', '.link', function () {
            var $this = $(this);
            //判断是否要加载二级列表
            hasSecond($this);

            $this.parents('.accordion').find('.submenu a').removeClass('active');
            $this.parents('.accordion').find('.submenu').not($this.next()).slideUp();
            $this.parent().siblings().removeClass('open');
            $this.next().slideToggle();
            $this.parent().toggleClass('open');

            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $this.parent().addClass('bdc-this-li');

            $('.content-div iframe').attr('src',$this.data('src'));

            liLength = $('.accordion .bdc-invented-li').length + $('#bdcdyQlMlDiv').children().length;
            liIndex = $this.parent().prevAll().length + 1;
            liIndex += $this.parent().prevAll().find('.bdc-inner-li').length;

            //隐藏打印图标
            $('.bdc-btn-print').addClass('bdc-hide');
        });
        //判断当前一级是否存在二级
        function hasSecond($this){
            if($this.hasClass('bdc-show-second')){
                $this.removeClass('bdc-show-second');
                var getBdcdyh = $this.parent().data('num');
                $.ajax({
                    url: "/realestate-register-ui/rest/v1.0/djbxx/bdcDjbQlMl/" + getBdcdyh,
                    type: "GET",
                    async: false,
                    dataType: "json",
                    success: function(data) {
                        // console.log(data);
                        if(data.bdcQlList.length > 0){
                            //一级的三角箭头展示
                            $this.children('.fa-caret-right').removeClass('bdc-hide');
                            //渲染二级
                            renderSecondAside(data.bdcQlList,$this);
                            //console.log(liLength);
                        }
                    },
                    error: function () {
                        layer.alert("查询二级登记簿权利失败！")
                    },
                    complete: function(XMLHttpRequest, textStatus){
                        removeModel();
                    }
                });
            }
        }
        // 渲染二级模板数据
        function renderSecondAside(secondData,$view) {
            // 每渲染一次模板，就重置为0
            liLength = 0;
            var getSecondTpl = djbSecondTpl.innerHTML;
            laytpl(getSecondTpl).render(secondData, function (html) {
                // console.log(secondData);
                $view.after(html);
                liLength = $('.accordion .bdc-invented-li').length + $('#bdcdyQlMlDiv').children().length;
            });
        }

        //1.2  点击箭头收起效果
        $(document).on('click', '.link>.fa-caret-right', function (ev) {
            var event = ev||event;
            event.stopPropagation();
            var $this = $(this).parent();
            $this.parents('.accordion').find('.submenu').not($this.next()).slideUp();
            $this.parent().siblings().removeClass('open');
            $this.next().slideToggle();
            $this.parent().toggleClass('open');
        });

        //1.2  对于逻辑栋点击高亮
        $(document).on('click', '.submenu a', function () {
            //显示打印图标
            $('.bdc-btn-print').removeClass('bdc-hide');

            var $this = $(this);
            // 当前点击的连接的权利类型
            var qllx = $this.attr("data-qllx");
            $('.submenu a').removeClass('active');
            $this.addClass('active');
            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $('.content-div iframe').attr('src',$(this).parent('.bdc-inner-li').data('src'));

            // console.log($this.parents('.bdc-outer-li'));
            liLength = $('.accordion .bdc-invented-li').length + $('#bdcdyQlMlDiv').children().length;
            if($this.parent().hasClass('bdc-inner-li')){
                liIndex = $this.parent().prevAll().length + 1 + $this.parents('.bdc-outer-li').prevAll().length + 1;
                liIndex += $this.parents('.bdc-invented-li').prevAll().find('.bdc-inner-li').length;
            }else {
                liIndex = $this.parent().prevAll().length + 1;
                liIndex += $this.parent().prevAll().find('.bdc-inner-li').length;
            }

        });



        //加载目录树
        addModel();
        var liLength = 0;
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/djbxx/bdcDjbMl/" + zdzhh + "?bdcdyh=" + bdcdyh,
            type: "GET",
            dataType: "json",
            success: function(data) {
                //console.log(data);
                if(data){
                    asideData = data;
                    renderAside(asideData);
                }
                // 为登记簿封面的 data-num 赋值
                $("ul[id='accordion'] li:eq(0)").attr("data-num", zdzhh);

                // 根据参数定位
                indexPage();
            },
            error: function () {
                layer.alert("查询登记簿权利失败！")
            },
            complete: function(XMLHttpRequest, textStatus){
                removeModel();
            }
        });

        // 渲染模板数据
        function renderAside(asideData) {
            // 每渲染一次模板，就重置为0
            liLength = 0;
            var getAsideTpl = djbQlTpl.innerHTML
                , asideView = document.getElementById('accordion');
            laytpl(getAsideTpl).render(asideData, function (html) {
                asideView.innerHTML = html;
                liLength = $('.accordion .bdc-invented-li').length + $('#bdcdyQlMlDiv').children().length;
            });
        }

        //侧边栏渲染完成获取总共的个数
        var liIndex = 1;
        //var liLength = $('.accordion .bdc-invented-li').length+$('#bdcdyQlMlDiv').children().length;

        // console.log(liLength);
        // 默认第1个单元高亮
        // $('.accordion>.bdc-invented-li').first().addClass('open bdc-this-li').find('.submenu').show();
        // 默认第n个单元高亮
        // $('.accordion>li:nth-child(4)').addClass('open').find('.submenu').show().find('a').first().addClass('active');
        // $('.content-div iframe').attr('src', $('.accordion .bdc-invented-li:first-child .link').data('src'));

        function indexPage() {
            var getNum = param;
            if (getNum != null && getNum.toString().length > 1) {
                liIndex = 0;
                for (var i = 0; i < $('.accordion .bdc-outer-li').length; i++) {
                    var getUl = $('.accordion>.bdc-invented-li:nth-child(' + i + ') .submenu');

                    if (getUl.length > 0) {
                        liIndex += getUl.find('.bdc-inner-li').length + 1;
                    } else {
                        liIndex++;
                    }

                    if ($($('.accordion .bdc-outer-li')[i]).data('num') == getNum) {
                        //console.log($('.accordion .bdc-outer-li'));
                        var nowI = i + 1;
                        hasSecond($('.accordion .bdc-outer-li:nth-child(' + nowI + ') .link'));
                        if ($('.accordion .bdc-outer-li:nth-child(' + nowI + ')').addClass('open bdc-this-li').find('.submenu').length <= 0) {
                            $('.accordion .bdc-outer-li:nth-child(' + nowI + ')').addClass('open bdc-this-li').find('.submenu').show();
                            $('.content-div iframe').attr('src', $('.accordion .bdc-outer-li:nth-child(' + nowI + ') .link').data('src'));
                        } else {
                            $('.accordion .bdc-outer-li:nth-child(' + nowI + ')').find('.submenu').show();
                            //如果有qllx，则打开对应权利类型的第一个
                            var qllx = $.getUrlParam('qllx');
                            if (qllx) {
                                var src = $($('.accordion .bdc-outer-li:nth-child(' + nowI + ')').find(".submenu  a[data-qllx='" + qllx + "']")).parent().data('src')
                                $($('.accordion .bdc-outer-li:nth-child(' + nowI + ')').find('.submenu a[data-qllx=' + qllx + ']')[0]).addClass('active');
                                $('.content-div iframe').attr('src', src);
                            } else {
                                //如果没有有qllx，则打开第一个
                                var src = $($('.accordion .bdc-outer-li:nth-child(' + nowI + ')').find('.submenu li')[0]).data('src')
                                $($('.accordion .bdc-outer-li:nth-child(' + nowI + ')').find('.submenu a')[0]).addClass('active');
                                $('.content-div iframe').attr('src', src);
                            }
                            liIndex++;
                            //显示打印图标
                            $('.bdc-btn-print').removeClass('bdc-hide');
                        }
                        break;
                    }
                }
            }
        }

        //2. 点击侧边栏收缩按钮
        var $asideClose = $('.bdc-aside-close'),
            $asideOpen = $('.bdc-aside-open'),
            $zoom = $('.bdc-aside-zoom'),
            $menuAside = $('.bdc-menu-aside'),
            $container = $('.bdc-container'),
            $zoomLine = $('#asideLine');
        $asideClose.on('click',function () {
            containerLeft = 20;
            $(this).toggleClass('bdc-hide');
            $asideOpen.toggleClass('bdc-hide');
            $zoom.animate({'left':'-5px'},100);
            $menuAside.animate({'left': -($menuAside.width() + 20) + 'px'},100);
            $zoomLine.animate({'left': '-24px'},100);
            $container.animate({'padding-left':'20px'},100);
        });
        $asideOpen.on('click',function () {
            containerLeft = 300;
            $(this).toggleClass('bdc-hide');
            $asideClose.toggleClass('bdc-hide');
            $zoom.animate({'left': $menuAside.width() - 1 + 'px'},100);
            $menuAside.animate({'left':'0'},100);
            $zoomLine.animate({'left': $menuAside.width() - 4 + 'px'},100);
            $container.animate({'padding-left':'300px'},100);
        });

        //3.最大化 正常转换
        //3.1  监听 最大化箭头单击事件
        var isZoom = false;
        $('.bdc-btn-zoom').on('click',function () {
            isZoom = true;
            $menuAside.toggleClass('bdc-hide');
            $('.bdc-title').toggleClass('bdc-hide');
            $zoom.addClass('bdc-hide');
            $container.css('padding-left', '0');

            $('.bdc-right-content').css('padding-top',0);
            $('.bdc-normal-btn').removeClass('bdc-hide');
        });

        //3.2  点击取消最大化按钮
        $('.bdc-btn-normal').on('click',returnNormal);
        //3.3  点击esc曲表最大化
        var iframe = document.getElementById('childFrame');
        iframe.onload = function () {
            iframe.contentDocument.onkeydown = function (e) {
                if (e.keyCode == 27) {
                    if (isZoom) {
                        returnNormal();
                    }
                }
                //下一个39
                //上一个37
                if (e.keyCode == 39) {
                    goDown();
                }
                if (e.keyCode == 37) {
                    goUp();
                }
            };
        };
        function returnNormal() {
            isZoom = false;
            $menuAside.toggleClass('bdc-hide');
            $('.bdc-title').toggleClass('bdc-hide');
            $zoom.removeClass('bdc-hide');
            if(containerLeft == 20){
                containerLeft = 20;
                $container.css('padding-left','20px');
            }else {
                containerLeft = 300;
                $container.css('padding-left','300px');
            }
            $('.bdc-right-content').css('padding-top','50px');
            $('.bdc-normal-btn').addClass('bdc-hide');
        }

        //4. 单击打印
        $('.bdc-btn-print').on('click',function(){
            var qllx = $('.active').data('qllx');
            var moduleCode = "bdcDjb";
            $.ajax({
                //判断是否有现势权利信息
                url: "/realestate-register-ui/rest/v1.0/djbpdf/qlxx?bdcdyh="+ bdcdyh + '&qllx=' + qllx + '&moduleCode=' + moduleCode,
                type: "GET",
                contentType: 'application/json',
                dataType: 'json',
                success: function (data) {
                    if(data){
                        window.open("/realestate-register-ui/view/djbxx/priviewPdf.html?bdcdyh=" + bdcdyh + '&qllx=' + qllx + '&moduleCode=' + moduleCode);

                        // 保存打印登记簿日志
                        var searchData = {"bdcdyh": bdcdyh, "qllx": qllx};
                        savePrintLogToDataBase(searchData, "", 1, "bdcdjb");
                    } else {
                        failMsg("未查询到该现势权利信息，无法打印！");
                    }
                },
                error: function () {
                    failMsg("未查询到该现势权利信息，无法打印！");
                }
                }
            )
        });

        //----------------------------关于上下一页开始--------------------------------
        //监听内容区滚轮事件
        var timer;
        $('.content-div').on('mousewheel', function(event) {
            // console.log($(this).scrollTop());
            var getScrollTop = $(this).scrollTop();
            clearInterval(timer);
            timer = setInterval(downEvent, 300);
            var second = 1;
            function downEvent(){
                if(second == 0)
                {
                    if(getScrollTop == $('.content-main').height() - $('.content-div').height() + 20){
                        // console.log('滚动到底部了');
                        goDown();
                    }else if(getScrollTop == 0){
                        goUp();
                        // console.log('滚动到顶部了');
                    }
                    clearInterval(timer);
                }
                second--;
            }
        });
        //监听上一个箭头
        $('.bdc-btn-up').on('click',goUp);
        //监听下一个箭头
        $('.bdc-btn-down').on('click',goDown);

        function goUp() {
            if(liIndex > 1){
                liIndex--;
                // console.log($('.accordion .bdc-invented-li')[liIndex - 1]);
                var nowLi = $('.accordion .bdc-invented-li')[liIndex - 1];
                // console.log($(nowLi).hasClass('bdc-outer-li'));

                if($(nowLi).hasClass('bdc-outer-li')){
                    var $this = $(nowLi).find('.link');
                    if($this.hasClass('bdc-show-second')){
                        //判断是否要渲染二级
                        hasSecond($this);
                        liIndex += $this.parent().find('.bdc-inner-li').length;
                        var secondLi = $('.accordion .bdc-invented-li')[liIndex-1];
                        console.log(secondLi);

                        $('.accordion .open .submenu li a').removeClass('active');
                        $(secondLi).parents('.accordion').find('.submenu a').removeClass('active');
                        $(secondLi).find('a').addClass('active');

                        $('.content-div iframe').attr('src',$(secondLi).data('src'));

                        $this.parents('.accordion').find('.submenu').not($this.next()).slideUp();
                        $this.parent().siblings().removeClass('open');
                        $this.next().slideToggle();
                        $this.parent().toggleClass('open');

                        $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
                    }else {
                        // console.log($this);
                        $this.parents('.accordion').find('.submenu a').removeClass('active');
                        $this.parents('.accordion').find('.submenu').not($this.next()).slideUp();
                        $this.parent().siblings().removeClass('open');
                        $this.next().slideToggle();
                        $this.parent().toggleClass('open');

                        $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
                        $this.parent().addClass('bdc-this-li');

                        $('.content-div iframe').attr('src',$this.data('src'));
                    }
                    //隐藏打印图标
                    $('.bdc-btn-print').addClass('bdc-hide');
                }else {
                    //显示打印图标
                    $('.bdc-btn-print').removeClass('bdc-hide');

                    var $this = $(nowLi).parents('.bdc-outer-li');
                    // console.log($this);
                    $('.accordion .open .submenu li a').removeClass('active');
                    $(nowLi).parents('.accordion').find('.submenu a').removeClass('active');
                    $(nowLi).find('a').addClass('active');
                    $this.siblings().removeClass('open');
                    $this.find('.submenu').slideDown();
                    $this.addClass('open');

                    $('.accordion .bdc-invented-li').removeClass('bdc-this-li');

                    $('.content-div iframe').attr('src',$(nowLi).data('src'));
                }
                //console.log(liIndex);
            }else {
                layer.msg('已经是第一个了');
            }
        }
        function goDown() {
            if(liIndex < liLength){
                liIndex++;
                // console.log($('.accordion .bdc-invented-li')[liIndex - 1]);
                var nowLi = $('.accordion .bdc-invented-li')[liIndex - 1];
                //console.log($(nowLi).hasClass('bdc-outer-li'));
                if($(nowLi).hasClass('bdc-outer-li')){
                    var $this = $(nowLi).find('.link');

                    //隐藏打印图标
                    $('.bdc-btn-print').addClass('bdc-hide');

                    //判断是否要渲染二级
                    hasSecond($this);

                    //console.log($this);
                    $this.parents('.accordion').find('.submenu a').removeClass('active');
                    $this.parents('.accordion').find('.submenu').not($this.next()).slideUp();
                    $this.parent().siblings().removeClass('open');
                    $this.next().slideToggle();
                    $this.parent().toggleClass('open');

                    $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
                    $this.parent().addClass('bdc-this-li');

                    $('.content-div iframe').attr('src',$this.data('src'));
                }else {
                    //显示打印图标
                    $('.bdc-btn-print').removeClass('bdc-hide');

                    var $this = $(nowLi).parents('.bdc-outer-li');
                    //console.log($this);
                    $('.accordion .open .submenu li a').removeClass('active');
                    $(nowLi).parents('.accordion').find('.submenu a').removeClass('active');
                    $(nowLi).find('a').addClass('active');
                    $this.siblings().removeClass('open');
                    $this.find('.submenu').slideDown();
                    $this.addClass('open');

                    $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
                    $this.parent().addClass('bdc-this-li');

                    $('.content-div iframe').attr('src',$(nowLi).data('src'));
                }
                // $('.accordion .open .submenu li a').removeClass('active');
                // $('.accordion .open .submenu').find('li:nth-child('+ liIndex +') a').addClass('active');
                // console.log($('.accordion .open .submenu').find('li:nth-child(' + liIndex + ') a').data('src'));
                //
                // window.frames["childFrame"].src = $('.accordion .open .submenu').find('li:nth-child(' + liIndex + ') a').data('src');
            }else {
                layer.msg('已经是最后一个了');
            }
        }
        //----------------------------关于上下一页结束--------------------------------

        //绑定需要拖拽改变大小的元素对象
        var oBox = document.getElementById('asideBox');
        var oLine = document.getElementById('asideLine');
        oLine.onmousedown = function(ev){
            document.onmousemove=function(ev){
                var iEvent = ev||event;
                oBox.style.width =  iEvent.clientX + 'px';
                oLine.style.left = iEvent.clientX - 4 + 'px';
                $zoom.css('left',iEvent.clientX - 1);
                if(oBox.offsetWidth <= 280){
                    oBox.style.width = '280px';
                    oLine.style.left = oBox.offsetWidth - 4 + 'px';
                    $zoom.css('left','280px');
                }

            };
            document.onmouseup=function(){
                document.onmousedown=null;
                document.onmousemove=null;
            };
            return false;
        };
    });
});

function failMsg(msg){
    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">' + msg);
}

/**
 * 保存打印记录操作的日志，存档到数据库
 * @param searchData 查询条件
 * @param pageData 查询结果
 * @param logType 查询类型
 */
function savePrintLogToDataBase(searchData, pageData, logType, printType) {
    var logData = {};
    logData.cxtj   = JSON.stringify(searchData);
    logData.czjg   = JSON.stringify(pageData);
    logData.rzlx   = logType;
    logData.dylx   = printType;

    $.ajax({
        url: getContextPath() + "/rest/v1.0/log/print",
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(logData)
    });
}