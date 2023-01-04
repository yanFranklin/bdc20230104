/**
 * 左侧目录树操作
 */
// 户室变更图是否已加载flag
var loadHsbg = false;
var hasHsbgData = true;

layui.use(['jquery', 'form', 'laytpl','element','table'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        element = layui.element,
        table = layui.table;
    $(function () {
        //获取最外层元素的padding-left的值
        var containerLeft = 300;

        //侧边栏渲染完成获取总共的个数
        var liIndex = 0;
        var liLength = $('.accordion .bdc-invented-li').length;
        // console.log(liLength);

        //监听 一级 第一次点击
        $(document).on('click', '.bdc-outer-li', function(){
            if(!$(this).hasClass('bdc-render-child')){
                $(this).addClass('bdc-render-child');
                var id = $(this).attr("key");
                loadOtherMenu(this, id);
            }
            $('.accordion .bdc-invented-li a').removeClass('active');
            $(this).find('a').filter(':first').addClass('active');
        });

        //监听 二级菜单点击
        $(document).on('click', '.bdc-outer-li .bdc-inner-li', function(e){
            e.stopPropagation();
        });

        //监听 三级菜单点击
        $(document).on('click','.bdc-inner-li .bdc-inner-li', function(e){
            e.stopPropagation();
            $('.accordion .bdc-invented-li a').removeClass('active');
            $(this).find('a').filter(':first').addClass('active');
        });



        //1. 侧边栏点击效果
        //1.1  手风琴点击效果
        $(document).on('click', '.link', function () {
            var $this = $(this);
            $this.parent().toggleClass('open');
            $this.parent().siblings().removeClass('open');
            $this.parent().siblings().find('.bdc-inner-li').removeClass('open');
            $this.parent().siblings().find('.submenu').slideUp();
            $(this).siblings('.submenu').slideToggle();

            //$('.content-div iframe').attr('src',$this.data('src'));
			openQlxx($this.data('id'),$this.data('qllx'),$this.data('bdcdyfwlx'));
        });
        //1.2  对于逻辑栋点击高亮
        $(document).on('click', '.submenu a.bdc-last-child', function () {
            var $this = $(this);
            $('.submenu a').removeClass('active');
            $this.addClass('active');
            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $('.content-div iframe').attr('src',$(this).parent('.bdc-inner-li').data('src'));
			
			openQlxx($this.data('id'),$this.data('qllx'),$this.data('bdcdyfwlx'));
        });
        //1.3 点击箭头
        $('.accordion').on('click','.bdc-outer-li i',function (event) {
            event.stopPropagation();
            if($(this).hasClass("fa-caret-right")){
                //点击箭头,自动加载目录
                if(!$(this).parents(".bdc-outer-li").hasClass('bdc-render-child')){
                    var $outer =$(this).parents(".bdc-outer-li");
                    $outer.addClass('bdc-render-child');
                    var id = $outer.attr("key");
                    loadOtherMenu($outer[0], id);

                }
            }
            var $this = $(this).parent();
            $this.parent().toggleClass('open');
            $this.parent().siblings().removeClass('open');
            $this.parent().siblings().find('.bdc-inner-li').removeClass('open');
            $this.parent().siblings().find('.submenu').slideUp();
            $this.siblings('.submenu').slideToggle();
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

        //3. 查询条件获取焦点
        $('.bdc-search').on('focus', function () {
            $(this).addClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size', '14px');
        }).on('blur', function () {
            $(this).removeClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size', '12px');
        });
        //4.监听tab切换
        element.on('tab(menuChangeFilter)', function (data) {
            $('.bdc-menu-tab .border-up-empty').addClass('bdc-hide');
            $(this).children('.border-up-empty').removeClass('bdc-hide');

            if (data.index == 0) {
                $('#qlls').removeClass('bdc-hide');
                $('#lsgx').addClass('bdc-hide');
            }
            if (data.index == 1) {
                $('#qlls').addClass('bdc-hide');
                $('#lsgx').removeClass('bdc-hide');
                lsgx();
            }

        });

        $('.bdc-search').on('focus', function () {
            $(this).addClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size', '14px');
            $('.bdc-search-box .layui-icon-close').css('font-size', '14px');
        }).on('blur', function () {
            $(this).removeClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size', '12px');
            $('.bdc-search-box .layui-icon-close').css('font-size', '12px');
        });
        //3.1 点击删除
        $('.bdc-search-box .layui-icon-close').on('click', function () {
            // $('.bdc-search-box').val("");
            console.log('删除操作');
            $('#qllxSearch').val("");
            $('#searchMenu').addClass('bdc-hide');
            $('#searchMenu').html("");
            $('#accordion').removeClass('bdc-hide');

        });

        //上下 状态展示 收缩按钮
        //上下 状态展示 收缩按钮
        var stateIndex = 0;
        $('.bdc-operate-show').on('click',function () {
            if(!loadHsbg) {
                // 加载户室变更图
                loadHsbgPic();
                loadHsbg = true;
            }
            if(hasHsbgData) {
                stateIndex++;
                if(stateIndex % 2 == 0){
                    //隐藏
                    $('.bdc-lc-container').animate({"height": '40px'},100);
                    $('.bdc-outer-box').animate({'padding-top':'55px'},100);
                    if($(this).hasClass("bdc-cqbg")){
                        $(".bdc-container-box").show();
                    }

                }else {
                    //展示
                    if( $(this).hasClass("bdc-cqbg")){
                        $(".bdc-lc-container").animate({"height":'100%'},100);
                        $(".bdc-container-box").hide();
                    }else{
                        $('.bdc-lc-container').animate({"height": '300px'},100);
                        $('.bdc-outer-box').animate({'padding-top':'315px'},100);

                    }
                }
                $(this).find('.layui-icon-down').toggleClass('bdc-hide');
                $(this).find('.layui-icon-up').toggleClass('bdc-hide');
            }else {
                failMsg("未查询到户室变更数据！");
            }
        });
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