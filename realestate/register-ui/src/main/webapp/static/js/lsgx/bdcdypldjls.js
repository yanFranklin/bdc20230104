//获取url链接上面的工作流实例id
var gzlslid = $.getUrlParam("gzlslid");
var qllx = $.getUrlParam("qllx");

layui.use(['jquery', 'form', 'laytpl','element','table'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        element = layui.element,
        table = layui.table;

    // 目录树加载方法
    var DirectoryTree = {
        directoryCache : null,
        secondNodeCache : new Array(),
        //目录树数据加载方法
        loadTreeNode: function(gzlslid, fn){
            addModel();
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/bdcdy/lsgx/ybdcqzh/"+ gzlslid,
                type: "GET",
                dataType: "json",
                data:{qllx: qllx},
                success: function(data) {
                    console.info(data);
                    if(data){
                        //设置缓存数据
                        DirectoryTree.directoryCache = data;
                        // 渲染目录树数据
                        DirectoryTree.renderDirectoryAside(data);
                        if(typeof fn === "function"){
                            fn();
                        }
                    }
                },
                error: function () {
                    showAlertDialog("加载失败。");
                },
                complete: function(XMLHttpRequest, textStatus){
                    removeModel();
                }
            });
        },
        // 渲染目录数据
        renderDirectoryAside: (function(asideData){
            return function(asideData){
                var getDjlsTpl = dirctoryTpl.innerHTML,
                    asideView = document.getElementById('accordion');
                laytpl(getDjlsTpl).render(asideData, function (html) {
                    asideView.innerHTML = html;
                });
                // 初始化目录事件
                DirectoryTree.initTreeNodeEvent();
            }
        })(),
        //加载二级目录内容
        loadSecondNode : function(zsid, ele, fn){
            addModel();
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/bdcdy/lsgx/bdcxm",
                type: "GET",
                data: {
                    gzlslid : gzlslid,
                    zsid : zsid,
                    qllx: qllx
                },
                dataType: "json",
                success: function(data) {
                    if(data){
                        //设置缓存数据
                        DirectoryTree.secondNodeCache = DirectoryTree.secondNodeCache.concat(data);
                        // 渲染目录树数据
                        DirectoryTree.renderNodeAside(data,ele);
                        if(typeof fn === "function"){
                            fn();
                        }
                    }
                },
                error: function () {
                    showAlertDialog("加载失败。");
                },
                complete: function(XMLHttpRequest, textStatus){
                    removeModel();
                }
            });
        },
        // 渲染目录节点数据
        renderNodeAside: (function(asideData,ele){
            var prefixUrl = "bdcdyDjLsgx.html?bdcdyh=";
            var filterData = function(data){
                $.each(data,function(i,value){
                    var zl = value.zl;
                    if(zl.length > 16){
                        zl = "..." + zl.substring(zl.length-10);
                    }
                    value.zlTxt = zl;
                    value.djlsUrl = prefixUrl + value.bdcdyh;
                    data[i] = value;
                });
                return data;
            }
            return function(asideData, ele){
                var getNodeTpl = asideNodeTpl.innerHTML
                    ,asideSecondView = ele;

                laytpl(getNodeTpl).render(filterData(asideData), function (html) {
                    asideSecondView.innerHTML = html;
                });
            }
        })(),

        // 重置目录树数据
        resetTree : function(){
            if(isNotBlank(DirectoryTree.directoryCache)){
                DirectoryTree.renderDirectoryAside(DirectoryTree.directoryCache);
            }
        },

        // 初始化树节点事件
        initTreeNodeEvent : function(){
            $('#accordion .bdc-outer-li').on('click',function(){
                if(!$(this).hasClass('bdc-render-child')){
                    $(this).addClass('bdc-render-child');
                    var zsid = $(this).data("zsid");
                    DirectoryTree.loadSecondNode(zsid, $(this).children('.submenu')[0]);
                }
            });
        }
    }

    //查询服务
    var Search= function(){
        var getSearchValue = $('.bdc-search').val();
        if(!isNotBlank(getSearchValue)){
            return;
        }
        var getAsideNodeData = DirectoryTree.secondNodeCache;
        console.info(getAsideNodeData);
        if(isNotBlank(getAsideNodeData)){
            addModel();
            var newAsideNodeData = new Array();
            $.each(getAsideNodeData,function(index,value){
                if(value.zl.indexOf(getSearchValue) > -1){
                    newAsideNodeData.push(value);
                }
            });
            DirectoryTree.renderNodeAside(newAsideNodeData,$("#searchMenu")[0]);
            removeModel();
            // 默认选中搜索出来的第一个节点
            $('#searchMenu .bdc-inner-li:first-child').children('.bdc-last-child').click();
        }
    }

    $(function () {
        //侧边栏渲染完成获取总共的个数
        var liIndex = 0;
        var liLength = $('.accordion .bdc-invented-li').length;

        // 初始化加载目录数据
        DirectoryTree.loadTreeNode(gzlslid, function(){
            // 默认加载第一个元素
            var firstDir = $('#accordion').find('.bdc-outer-li:first-child');
            $('#accordion').find('.bdc-outer-li:first-child').addClass('bdc-render-child');
            shrik($('#accordion').find('.bdc-outer-li:first-child'));
            var zsid = $(firstDir).data("zsid");
            DirectoryTree.loadSecondNode(zsid, $(firstDir).children('.submenu')[0], function(){
                $(firstDir).find('li.bdc-inner-li:first').children('.bdc-last-child').click();
            });

        });

        //获取最外层元素的padding-left的值
        var containerLeft = 300;

        //1. 侧边栏点击效果
        //1.1  左侧点击事件
        $(document).on('click', '.link', function () {
            var $this = $(this);
            $this.parent().toggleClass('open');
            $this.parent().siblings().removeClass('open');
            $this.parent().siblings().find('.bdc-inner-li').removeClass('open');
            $this.parent().siblings().find('.submenu').slideUp();
            $(this).siblings('.submenu').slideToggle();
        });
        //1.2  左侧二级节点点击事件，替换右侧的iframe页面地址
        $(document).on('click', 'a.bdc-last-child', function () {
            var $this;
            $this = $(this);
            $('.submenu a').removeClass('active');
            $('#searchMenu a').removeClass('active');
            $this.addClass('active');
            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $('.bdc-tab-content iframe').attr('src',$(this).parent('.bdc-inner-li').data('src'));
        });
        //1.3 一级目录箭头的展开事件
        $('.accordion').on('click','.bdc-outer-li i',function (event) {
            event.stopPropagation();
            var $this = $(this).parent();
            $this.parent().toggleClass('open');
            $this.parent().siblings().removeClass('open');
            $this.parent().siblings().find('.bdc-inner-li').removeClass('open');
            $this.parent().siblings().find('.submenu').slideUp();
            $this.siblings('.submenu').slideToggle();
        });

        //2.1 点击侧边栏收缩按钮
        var $asideClose = $('.bdc-aside-close'),
            $asideOpen = $('.bdc-aside-open'),
            $zoom = $('.bdc-aside-zoom'),
            $menuAside = $('.bdc-menu-aside'),
            $container = $('.bdc-container'),
            $zoomLine = $('#asideLine');
        //2.2 左侧栏收缩按钮，收缩事件
        $asideClose.on('click',function () {
            containerLeft = 20;
            $(this).toggleClass('bdc-hide');
            $asideOpen.toggleClass('bdc-hide');
            $zoom.animate({'left':'-5px'},100);
            $menuAside.animate({'left': -($menuAside.width() + 40) + 'px'},100);
            $zoomLine.animate({'left': '-24px'},100);
            $container.animate({'padding-left':'20px'},100);
        });
        //2.3 左侧栏收缩按钮，打开事件
        $asideOpen.on('click',function () {
            containerLeft = 300;
            $(this).toggleClass('bdc-hide');
            $asideClose.toggleClass('bdc-hide');
            $zoom.animate({'left': $menuAside.width() - 1 + 'px'},100);
            $menuAside.animate({'left':'0'},100);
            $zoomLine.animate({'left': $menuAside.width() - 4 + 'px'},100);
            $container.animate({'padding-left':'300px'},100);
        });

        //3. 左侧查询框，鼠标获取焦点事件：搜索框体变大。 鼠标失去焦点事件：搜索框体变小。
        $('.bdc-search').on('focus',function () {
            $(this).addClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size','14px');
            $('.bdc-search-box .layui-icon-close').css('font-size','14px');
        }).on('blur',function () {
            $(this).removeClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size','12px');
            $('.bdc-search-box .layui-icon-close').css('font-size','12px');
        });
        //3.1 搜索框点击删除按钮事件
        $('.bdc-search-box .layui-icon-close').on('click', function () {
            $('.bdc-search').val("");
            $('#searchMenu').addClass('bdc-hide');
            $('#searchMenu').html("");
            $('#accordion').removeClass('bdc-hide');
        });

        // 4.1 查询方法
        $('.bdc-search').bind('keyup', function(event) {
            if (event.keyCode == "13") {
                $('#accordion').addClass('bdc-hide');
                $('#searchMenu').removeClass('bdc-hide');
                Search();
            }
        });

        // 5.1 最大化 正常转换
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

        //5.2  点击取消最大化按钮
        $('.bdc-btn-normal').on('click',returnNormal);
        //5.3  点击esc曲表最大化
        var iframe = document.getElementById('childFrame');
        iframe.onload = function () {
            iframe.contentDocument.onkeydown = function (e) {
                if (e.keyCode == 27) {
                    if (isZoom) {
                        returnNormal();
                    }
                }
                //下一个39 上一个37
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
        //5.4 监听上一个箭头
        $('.bdc-btn-up').on('click',goUp);
        //5.5 监听下一个箭头
        $('.bdc-btn-down').on('click',goDown);

        //前一条事件
        function goUp() {
            var prevElement = $('a.active').parents('li.bdc-inner-li').prev();
            // 当前节点存在前节点时，选中前节点。否则找到当前目录的前一目录，选中该目录下的最后一个节点
            if(prevElement.length>0){
                $(prevElement).children('.bdc-last-child').click();
            }else{
                // 找到当前目录的前一个目录
                var dirElement = $('a.active').parents('li.bdc-outer-li').prev();
                if(dirElement.length > 0){
                    shrik(dirElement);
                    if($(dirElement).hasClass('bdc-render-child')){
                        $(dirElement).find('li.bdc-inner-li:last').children('.bdc-last-child').click();
                    }else{
                        // 当前目录下节点为加载时，先加载节点，在选中目录下最后一个节点
                        $(dirElement).addClass('bdc-render-child');
                        var zsid = $(dirElement).data("zsid");
                        DirectoryTree.loadSecondNode(zsid, $(dirElement).children('.submenu')[0], function(){
                            $(dirElement).find('li.bdc-inner-li:last').children('.bdc-last-child').click();
                        });
                    }
                }else{
                    // 没有前一目录时，找当前最后一个目录的最后的一个节点
                    var lastDirElement = $('a.active').parents('ul.accordion').children('.bdc-outer-li:last');
                    if(lastDirElement.length == 0){
                        $('a.active').parents('ul.accordion').children('.bdc-inner-li:last').find('.bdc-last-child').click();
                    }else{
                        shrik(lastDirElement);
                        if($(lastDirElement).hasClass('bdc-render-child')){
                            $(lastDirElement).find('li.bdc-inner-li:last').children('.bdc-last-child').click();
                        }else{
                            $(lastDirElement).addClass('bdc-render-child');
                            var zsid = $(lastDirElement).data("zsid");
                            DirectoryTree.loadSecondNode(zsid, $(lastDirElement).children('.submenu')[0], function(){
                                $(lastDirElement).find('li.bdc-inner-li:last').children('.bdc-last-child').click();
                            });
                        }
                    }
                }
            }
            // setTips();
        }
        //后一条事件
        function goDown() {
            var nextElement = $('a.active').parents('li.bdc-inner-li').next();
            // 当前节点存在前节点时，选中前节点。否则找到当前目录的前一目录，选中该目录下的最后一个节点
            if(nextElement.length>0){
                $(nextElement).children('.bdc-last-child').click();
            }else{
                // 找到当前目录的前一个目录
                var dirElement = $('a.active').parents('li.bdc-outer-li').next();
                if(dirElement.length > 0){
                    // $(dirElement).children('a.link').click();
                    shrik(dirElement);
                    if($(dirElement).hasClass('bdc-render-child')){
                        $(dirElement).find('li.bdc-inner-li:first').children('.bdc-last-child').click();
                    }else{
                        // 当前目录下节点为加载时，先加载节点，在选中目录下最后一个节点
                        $(dirElement).addClass('bdc-render-child');
                        var zsid = $(dirElement).data("zsid");
                        DirectoryTree.loadSecondNode(zsid, $(dirElement).children('.submenu')[0], function(){
                            $(dirElement).find('li.bdc-inner-li:first').children('.bdc-last-child').click();
                        });
                    }
                }else{
                    // 没有前一目录时，找当前最后一个目录的最后的一个节点
                    var firstDirElement = $('a.active').parents('ul.accordion').children('.bdc-outer-li:first');
                    if(firstDirElement.length ==0){
                        $('a.active').parents('ul.accordion').children('.bdc-inner-li:first').find('.bdc-last-child').click();
                    }else{
                        shrik(firstDirElement);
                        if($(firstDirElement).hasClass('bdc-render-child')){
                            $(firstDirElement).find('li.bdc-inner-li:first').children('.bdc-last-child').click();
                        }else{
                            $(firstDirElement).addClass('bdc-render-child');
                            var zsid = $(firstDirElement).data("zsid");
                            DirectoryTree.loadSecondNode(zsid, $(firstDirElement).children('.submenu')[0], function(){
                                $(firstDirElement).find('li.bdc-inner-li:first').children('.bdc-last-child').click();
                            });
                        }
                    }
                }
            }
        }
        // 展开收缩目录方法
        function shrik(element){
            $(element).parent().find('.bdc-outer-li').removeClass('open');
            $(element).addClass('open');
            $(element).parent().find('.submenu').slideUp();
            $(element).find('.submenu').slideToggle();
        }

    });
});