/**
 * Created by Administrator on 2018/12/21.
 */
layui.use(['jquery', 'form', 'laytpl','element','table'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
    $(function () {
        //动态渲染侧边栏
        var asideData = {
            "djbfmUrl":"bdcDjbfm.html?zdzhh=320802001009GB00001",
            "zdzhxxUrl":"bdcDjbZdjbxx.html?bdcdyh=320802001009GB00001W00000000",
            "qlmlUrl":"bdcDjbQlml.html?zdzhh=320802001009GB00001",
            "bdcDjbQlMlList":[
                {
                    "bdcdyh":"320802001009GB00001W00100000",
                    "bdcdyUrl":"bdcDjbQlfm.html?bdcdyh=320802001009GB00001W00100000",
                    "bdcDjbQlVO":{
                        "djbQlCfMc":null,
                        "djbQlCfUrl":null,
                        "djbQlDyaMc":null,
                        "djbQlDyaUrl":null,
                        "djbQlDyiMc":null,
                        "djbQlDyiUrl":null,
                        "djbQlYyMc":null,
                        "djbQlYyUrl":null,
                        "djbQlYgMc":null,
                        "djbQlYgUrl":null,
                        "djbQlCqMc":null,
                        "djbQlCqUrl":null
                    }
                },
                {
                    "bdcdyh":"320802001009GB00001F00010003",
                    "bdcdyUrl":"bdcDjbQlfm.html?bdcdyh=320802001009GB00001F00010003",
                    "bdcDjbQlVO":{
                        "djbQlCfMc":null,
                        "djbQlCfUrl":null,
                        "djbQlDyaMc":null,
                        "djbQlDyaUrl":null,
                        "djbQlDyiMc":null,
                        "djbQlDyiUrl":null,
                        "djbQlYyMc":"异议登记信息",
                        "djbQlYyUrl":"bdcDjbYy.html?bdcdyh=320802001009GB00001F00010003",
                        "djbQlYgMc":null,
                        "djbQlYgUrl":null,
                        "djbQlCqMc":null,
                        "djbQlCqUrl":null
                    }
                },
                {
                    "bdcdyh":"320802001009GB00001F00010009",
                    "bdcdyUrl":"bdcDjbQlfm.html?bdcdyh=320802001009GB00001F00010009",
                    "bdcDjbQlVO":{
                        "djbQlCfMc":"查封登记信息",
                        "djbQlCfUrl":"bdcDjbCf.html?bdcdyh=320802001009GB00001F00010009",
                        "djbQlDyaMc":null,
                        "djbQlDyaUrl":null,
                        "djbQlDyiMc":null,
                        "djbQlDyiUrl":null,
                        "djbQlYyMc":"异议登记信息",
                        "djbQlYyUrl":"bdcDjbYy.html?bdcdyh=320802001009GB00001F00010009",
                        "djbQlYgMc":null,
                        "djbQlYgUrl":null,
                        "djbQlCqMc":null,
                        "djbQlCqUrl":null
                    }
                },
                {
                    "bdcdyh":"320802001009GB00001F00010004",
                    "bdcdyUrl":"bdcDjbQlfm.html?bdcdyh=320802001009GB00001F00010004",
                    "bdcDjbQlVO":{
                        "djbQlCfMc":null,
                        "djbQlCfUrl":null,
                        "djbQlDyaMc":null,
                        "djbQlDyaUrl":null,
                        "djbQlDyiMc":null,
                        "djbQlDyiUrl":null,
                        "djbQlYyMc":null,
                        "djbQlYyUrl":null,
                        "djbQlYgMc":null,
                        "djbQlYgUrl":null,
                        "djbQlCqMc":null,
                        "djbQlCqUrl":null
                    }
                },
                {
                    "bdcdyh":"320802001009GB00001F00010006",
                    "bdcdyUrl":"bdcDjbQlfm.html?bdcdyh=320802001009GB00001F00010006",
                    "bdcDjbQlVO":{
                        "djbQlCfMc":null,
                        "djbQlCfUrl":null,
                        "djbQlDyaMc":null,
                        "djbQlDyaUrl":null,
                        "djbQlDyiMc":null,
                        "djbQlDyiUrl":null,
                        "djbQlYyMc":null,
                        "djbQlYyUrl":null,
                        "djbQlYgMc":null,
                        "djbQlYgUrl":null,
                        "djbQlCqMc":null,
                        "djbQlCqUrl":null
                    }
                }
            ]
        };
        asideData.bdcDjbQlMlList.forEach(function (value) {
            value.childList = [];
            if(value.bdcDjbQlVO.djbQlCfMc != null){
                value.childList.push({
                    name: value.bdcDjbQlVO.djbQlCfMc,
                    url: value.bdcDjbQlVO.djbQlCfUrl
                });
            }
            if(value.bdcDjbQlVO.djbQlDyaMc != null){
                value.childList.push({
                    name: value.bdcDjbQlVO.djbQlDyaMc,
                    url: value.bdcDjbQlVO.djbQlDyaUrl
                });
            }
            if(value.bdcDjbQlVO.djbQlDyiMc != null){
                value.childList.push({
                    name: value.bdcDjbQlVO.djbQlDyiMc,
                    url: value.bdcDjbQlVO.djbQlDyiUrl
                });
            }
            if(value.bdcDjbQlVO.djbQlYyMc != null){
                value.childList.push({
                    name: value.bdcDjbQlVO.djbQlYyMc,
                    url: value.bdcDjbQlVO.djbQlYyUrl
                });
            }
            if(value.bdcDjbQlVO.djbQlYgMc != null){
                value.childList.push({
                    name: value.bdcDjbQlVO.djbQlYgMc,
                    url: value.bdcDjbQlVO.djbQlYgUrl
                });
            }
            if(value.bdcDjbQlVO.djbQlCqMc != null){
                value.childList.push({
                    name: value.bdcDjbQlVO.djbQlCqMc,
                    url: value.bdcDjbQlVO.djbQlCqUrl
                });
            }
        });
        renderAside(asideData);
        function renderAside(asideData) {
            var getAsideTpl = djbQlTpl.innerHTML
                ,asideView = document.getElementById('accordion');
            laytpl(getAsideTpl).render(asideData, function(html){
                asideView.innerHTML = html;
            });
        }
        //获取最外层元素的padding-left的值
        var containerLeft = 300;

        //侧边栏渲染完成获取总共的个数
        var liIndex = 0;
        var liLength = $('.accordion .bdc-invented-li').length;
        // console.log(liLength);

        //新增，根据地址栏参数，默认展示指定内容
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)return  unescape(r[2]); return null;
        }
        var getNum = GetQueryString('num');
        if(getNum != null && getNum.toString().length > 1){
            liIndex = 0;
            for(var i = 0; i < $('.accordion .bdc-outer-li').length; i++){
                var getUl = $('.accordion>.bdc-invented-li:nth-child('+i+') .submenu');
                // console.log(getUl);
                if(getUl.length > 0){
                    // console.log(getUl.find('li').length);
                    liIndex += getUl.find('.bdc-inner-li').length+1;
                }else {
                    liIndex++;
                }

                if($($('.accordion .bdc-outer-li')[i]).data('num') == getNum){
                    var nowI = i + 1;
                    $('.accordion .bdc-outer-li:nth-child('+ nowI +')').addClass('open bdc-this-li').find('.submenu').show();
                    $('.content-div iframe').attr('src',$('.accordion .bdc-outer-li:nth-child('+ nowI +') .link').data('src'));
                    break;
                }
            }
            // console.log(liIndex);
        }

        // 查询条件获取焦点
        $('.bdc-search').on('focus',function () {
            $(this).addClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size','14px');
            $('.bdc-search-box .layui-icon-close').css('font-size','14px');
        }).on('blur',function () {
            $(this).removeClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size','12px');
            $('.bdc-search-box .layui-icon-close').css('font-size','12px');
        });
        //按enter键
        $('.bdc-search').bind('keyup', function(event) {
            if (event.keyCode == "13") {
                //回车执行查询
                var getAsideData = JSON.parse(JSON.stringify(asideData));
                var getSearchValue = $('.bdc-search').val();
                for(var i = getAsideData.bdcDjbQlMlList.length-1; i >= 0; i--){
                    if(getAsideData.bdcDjbQlMlList[i].bdcdyh.indexOf(getSearchValue) == -1){
                        if(getAsideData.bdcDjbQlMlList[i].childList != 0){
                            for(var index = getAsideData.bdcDjbQlMlList[i].childList.length-1; index >= 0; index--){
                                if(getAsideData.bdcDjbQlMlList[i].childList[index].name.indexOf(getSearchValue) == -1){
                                    getAsideData.bdcDjbQlMlList[i].childList.splice(index, 1);
                                }
                            }
                            if(getAsideData.bdcDjbQlMlList[i].childList == 0){
                                getAsideData.bdcDjbQlMlList.splice(i,1);
                            }
                        }else {
                            getAsideData.bdcDjbQlMlList.splice(i,1);
                        }
                    }
                }
                renderAside(getAsideData);
            }
        });

        //点击删除
        $('.bdc-search-box .layui-icon-close').on('click',function () {
            console.log('删除操作');
        });

        //1. 侧边栏点击效果
        //1.1  手风琴点击效果
        $(document).on('click', '.link', function () {
            console.log('bbb');
            var $this = $(this);
            $this.parents('.accordion').find('.submenu a').removeClass('active');
            $this.parents('.accordion').find('.submenu').not($this.next()).slideUp();
            $this.parent().siblings().removeClass('open');
            $this.next().slideToggle();
            $this.parent().toggleClass('open');

            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $this.parent().addClass('bdc-this-li');

            $('.content-div iframe').attr('src',$this.data('src'));
            liIndex = 0;
            for(var i = $this.parent().index(); i >= 0; i--){
                var getUl = $('.accordion>.bdc-invented-li:nth-child('+i+') .submenu');
                // console.log(getUl);
                if(getUl.length > 0){
                    // console.log(getUl.find('li').length);
                    liIndex += getUl.find('.bdc-inner-li').length+1;
                }else {
                    liIndex++;
                }
            }
            // console.log(liIndex);
        });
        //1.2  点击箭头收起效果
        $(document).on('click', '.link>.fa-caret-right', function (ev) {
            var event = ev||event;
            event.stopPropagation();
            console.log('aaa');
            var $this = $(this).parent();
            $this.parents('.accordion').find('.submenu').not($this.next()).slideUp();
            $this.parent().siblings().removeClass('open');
            $this.next().slideToggle();
            $this.parent().toggleClass('open');
        });
        //1.3  对于逻辑栋点击高亮
        $(document).on('click', '.submenu a', function () {
            var $this = $(this);
            $('.submenu a').removeClass('active');
            $this.addClass('active');
            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $('.content-div iframe').attr('src',$(this).parent('.bdc-inner-li').data('src'));

            // console.log($this.parents('.bdc-outer-li'));
            liIndex = 0;
            for(var i = $this.parents('.bdc-outer-li').index(); i >= 0; i--){
                var getUl = $('.accordion>.bdc-outer-li:nth-child('+i+') .submenu');
                // console.log(getUl);
                if(getUl.length > 0){
                    // console.log(getUl.find('li').length);
                    liIndex += getUl.find('.bdc-inner-li').length + 1;
                }else {
                    liIndex++;
                }
            }
            liIndex += $this.parent().index() + 1;
            // console.log(liIndex);
        });

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
            $container.css('padding-left','0');

            $('.bdc-right-content').css('padding-top',0);
            $('.bdc-normal-btn').removeClass('bdc-hide');
        });

        var iframe = document.getElementById('childFrame');
        iframe.onload = function () {
            iframe.contentDocument.onkeydown = function (e) {
                if(e.keyCode == 27){
                    if(isZoom){
                        returnNormal();
                    }
                }
                //下一个39
                //上一个37
                if(e.keyCode == 39){
                    goDown();
                }
                if(e.keyCode == 37){
                    goUp();
                }
            };
        };

        //3.2  点击取消最大化按钮
        $('.bdc-btn-normal').on('click',returnNormal);
        //3.3  点击esc曲表最大化
        $(document).on('keydown',function (e) {
            if(e.keyCode == 27){
                if(isZoom){
                    returnNormal();
                }
            }
            //下一个39
            //上一个37
            if(e.keyCode == 39){
                goDown();
            }
            if(e.keyCode == 37){
                goUp();
            }
        });
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

        //----------------------------关于上下一页开始--------------------------------
        //监听内容区滚轮事件
        var timer;
        $('.content-div').on('mousewheel', function(event) {
            console.log($(this).scrollTop());
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
                    // console.log($this);
                    $this.parents('.accordion').find('.submenu a').removeClass('active');
                    $this.parents('.accordion').find('.submenu').not($this.next()).slideUp();
                    $this.parent().siblings().removeClass('open');
                    $this.next().slideToggle();
                    $this.parent().toggleClass('open');

                    $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
                    $this.parent().addClass('bdc-this-li');

                    $('.content-div iframe').attr('src',$this.data('src'));
                }else {
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
            }else {
                layer.msg('已经是第一个了');
            }
        }
        function goDown() {
            if(liIndex < liLength){
                liIndex++;
                // console.log($('.accordion .bdc-invented-li')[liIndex - 1]);
                var nowLi = $('.accordion .bdc-invented-li')[liIndex - 1];
                console.log($(nowLi).hasClass('bdc-outer-li'));
                if($(nowLi).hasClass('bdc-outer-li')){
                    var $this = $(nowLi).find('.link');
                    console.log($this);
                    $this.parents('.accordion').find('.submenu a').removeClass('active');
                    $this.parents('.accordion').find('.submenu').not($this.next()).slideUp();
                    $this.parent().siblings().removeClass('open');
                    $this.next().slideToggle();
                    $this.parent().toggleClass('open');

                    $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
                    $this.parent().addClass('bdc-this-li');

                    $('.content-div iframe').attr('src',$this.data('src'));
                }else {
                    var $this = $(nowLi).parents('.bdc-outer-li');
                    console.log($this);
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