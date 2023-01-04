/**
 * 历史关系操作JS
 */
var xmid = $.getUrlParam("xmid");
var bdcdyh = $.getUrlParam("bdcdyh");
var dataList=[];
var globalXmid;
var zdList = getMulZdList("qszt,djlx");
// 户室变更图是否已加载flag
var loadHsbg = false;
var hasHsbgData = true;
layui.use(['form','jquery','laydate','element','layer','table'],function () {
    var $ = layui.jquery,
        form = layui.form,
        laytpl = layui.laytpl,
        element = layui.element;

    $(function () {
        // 如果未传bdcdyh参数，需要根据xmid查询bdcdyh
        loadBdcdyh();
        //加载所有数据
        loadAll();

        //---------------------查询条件开始-------------------------------------------------------
        //按enter键
        $('.bdc-search').bind('keyup', function (event) {
            if (event.keyCode == "13") {
                var getSearchValue = $('.bdc-search').val();
                if (getSearchValue != '') {
                    // $('.accordion').toggleClass('bdc-hide');
                    $('#accordion').addClass('bdc-hide');
                    $('#searchMenu').removeClass('bdc-hide');
                    var getAsideData =  JSON.parse(JSON.stringify(dataList));

                    for (var i = getAsideData.length - 1; i >= 0; i--) {
                        //一级没有
                        if(getAsideData[i].name != null){
                            if (getAsideData[i].name.indexOf(getSearchValue) == -1){
                                //二级存在
                                if(getAsideData[i].lsgxModel.length != 0){
                                    for (var index = getAsideData[i].lsgxModel.length - 1; index >= 0; index--) {
                                        //二级没有
                                        if (getAsideData[i].lsgxModel[index].xsxx.indexOf(getSearchValue) == -1) {
                                            getAsideData[i].lsgxModel.splice(index, 1);
                                        }
                                        if(getAsideData[i].lsgxModel.length == 0){
                                            getAsideData.splice(i, 1);
                                        }
                                    }
                                }else {
                                    // 二级不存在
                                    getAsideData.splice(i, 1);
                                }
                            }
                        }else {
                            getAsideData.splice(i, 1);
                        }
                    }

                    // 查询筛选有数据
                    if(getAsideData.length > 0){
                        var getSearchAsideTpl = asideTpl.innerHTML
                            , asideSearchView = document.getElementById('searchMenu');
                        laytpl(getSearchAsideTpl).render(getAsideData, function (html) {
                            asideSearchView.innerHTML = html;
                        });
                        //默认展开所有
                        $('#searchMenu .bdc-outer-li').addClass('open').find('.submenu').css('display', 'block');
                        $('#searchMenu .bdc-outer-li .submenu .bdc-inner-li').addClass('open');
                        form.render();
                    }

                } else {
                    $('#searchMenu').addClass('bdc-hide');
                    $('#searchMenu').html("");
                    $('#accordion').removeClass('bdc-hide');
                }


            }
        });
        //---------------------查询条件结束-------------------------------------------------------

        //监听内容区选中tab鼠标覆盖事件
        $('.bdc-tab-title li').on('mouseenter',function () {
            $(this).find('.bdc-second-tab').removeClass('bdc-hide');
        }).on('mouseleave',function () {
            $(this).find('.bdc-second-tab').addClass('bdc-hide');
        });
        $('.bdc-tab-title .bdc-second-tab p').on('click',function () {
            $(this).addClass('bdc-second-this').siblings().removeClass('bdc-second-this');
        });

        //获取最外层元素的padding-left的值
        var containerLeft = 300;

        //侧边栏渲染完成获取总共的个数
        var liIndex = 0;
        var liLength = $('.accordion .bdc-invented-li').length;
        // console.log(liLength);

        //监听 一级 第一次点击
        $(document).on('click', '.bdc-outer-li', function(){
            // $('.accordion .bdc-invented-li a').removeClass('active');
            $(this).find('a').filter(':first').addClass('active');
            $(this).siblings('.submenu').find('.link').click;
        });

        //监听 二级菜单点击
        $(document).on('click', '.bdc-outer-li .bdc-inner-li', function(e){
            e.stopPropagation();
        });

        //监听 三级菜单点击
        $(document).on('click','.bdc-inner-li .bdc-inner-li', function(e){
            e.stopPropagation();
            $('.accordion .bdc-invented-li a').removeClass('active');
        });
        //1. 侧边栏点击效果
        //1.1  一级菜单点击展示其下内容
        $(document).on('click', '.link', function () {
            var $this = $(this);
            $this.parent().toggleClass('open');
            $(this).siblings('.submenu').slideToggle(function () {

            });
        });
        //1.2  对于逻辑栋点击高亮
        $(document).on('click', '.submenu a.bdc-last-child', function () {
            var $this = $(this);
            $('.submenu a').removeClass('active');
            $this.addClass('active');
            $('#accordion').find(':radio').removeAttr('checked');
            $this.parent().find(':radio').attr('checked', 'checked');
            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $('.content-div iframe').attr('src',$(this).parent('.bdc-inner-li').data('src'));

            openQlxx($this.data('id'),$this.data('qllx'),$this.data('bdcdyfwlx'));
            form.render();
        });
        //1.3 点击箭头
        $('.accordion').on('click','.bdc-outer-li i',function (event) {
            event.stopPropagation();
            var $this = $(this).parent();
            $this.parent().toggleClass('open');
            $this.siblings('.submenu').slideToggle();
        });

        // 1.4 点击单选框
        form.on('radio(screenCq)', function(data){
            console.log(data.elem); //得到radio原始DOM对象
            console.log(data.value); //被点击的radio的value值

            $('li.qllx-outer-li').each(function (index, item) {

                $(item).find('li.bdc-inner-li').each(function (i, v) {
                    // 根据cqid筛选权利目录
                    if($(v).data('cqid')!= data.value){
                        $(v).addClass('bdc-hide');
                    }else{
                        if($(v).hasClass('bdc-hide')){
                            $(v).removeClass('bdc-hide');
                        }
                        if(!$(item).hasClass('open')){
                            //默认展开
                            $(item).addClass('open').find('.submenu').css('display', 'block');
                        }
                    }
                });
            });

            openQlxx(data.value,$(data.elem).attr("data-qllx"),$(data.elem).attr("data-bdcdyfwlx"))

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

                }else {
                    //展示
                    $('.bdc-lc-container').animate({"height": '300px'},100);
                    $('.bdc-outer-box').animate({'padding-top':'315px'},100);
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

    /**
     * 只传入xmid的话根据项目id查询bdcdyh
     */
    function loadBdcdyh() {
        if(isNullOrEmpty(bdcdyh) && isNullOrEmpty(xmid)){
            layer.msg("传入参数为xmid或bdcdyh", {title: '提示'});
            return;
        }
        if(isNullOrEmpty(bdcdyh) && !isNullOrEmpty(xmid)) {
            // 加载遮罩层
            addModel();
            $.ajax({
                url: '/realestate-inquiry-ui/history/bdcdyh',
                type: 'GET',
                async: false,
                data: {
                    xmid: xmid
                },
                success: function(data) {
                    if(data != null) {
                        bdcdyh = data;
                    }else {
                        layer.msg("未查询到不动产单元号，请检查传入数据是否正确！");
                    }
                    removeModal();
                },
                error: function(e) {
                    delAjaxErrorMsg(e);
                }
            });
        }
    }

    /**
     * 加载户室变更图
     * @type {string}
     */
    function loadHsbgPic() {
        if(!isNullOrEmpty(bdcdyh)) {
            // 户室变更数据url
            var hsbgUrl = "/realestate-inquiry-ui/history/fwhs?bdcdyh=" + bdcdyh;
            $.ajax({
                url: hsbgUrl,
                dataType: 'json',
                async: false,
                success: function(data) {
                    if(data == null || data.length == 0 || isNullOrEmpty(data[0].bdcdyh) || isNullOrEmpty(data[0].zl)) {
                        hasHsbgData = false;
                    }else {
                        addModel();
                        console.log(data);
                        // 加载房屋户室变更图
                        //initRelation(data);
                        new Flow('relationMap', {
                            options: {
                                autoResize: false,
                                layout: {
                                    hierarchical: {
                                        levelSeparation: 500,
                                        direction: 'RL' // UD、DU、RL、LR
                                    }
                                }
                            },
                            nodeClick: function (parma) {
                                console.log('点击节点后的回调：',parma);
                                if(parma && parma.bdcdyh){
                                    bdcdyh = parma.bdcdyh;

                                    // 加载菜单
                                    loadAll();
                                }
                            },
                            data: data,
                            nodesImage: {
                                color: {
                                    unselectedColor: {
                                        nowColor: {
                                            fillColor: '#abcdf7',
                                            backgroundColor: '#eaf4fe'
                                        },
                                        preColor: {
                                            fillColor: '#d0d5da',
                                            backgroundColor: '#eaedf1'
                                        }
                                    },
                                    selectedColor: {
                                        fillColor: '#1d87d1',
                                        backgroundColor: '#eaf4fe'
                                    }
                                }
                            }
                        });
                        removeModal();
                    }
                },
                error: function(e) {
                    delAjaxErrorMsg(e);
                }
            });
        }
    }


    function loadAll() {
        if (!isNullOrEmpty(bdcdyh)) {
            addModel();
            var cqxxUrl = "/realestate-inquiry-ui/history/cqxxAll/yc?bdcdyh=" + bdcdyh;
            $.ajax({
                url: cqxxUrl,
                dataType: 'json',
                success: function (data) {
                    console.log("历史关系模型数据：");
                    console.log(data);
                    removeModal();
                    if (!isEmptyObject(data)) {
                        // 重新组织数据
                        if(data.allCqLsgxModel.length > 0){
                            for(var i=0; i < data.allCqLsgxModel.length; i++){
                                dmToMc(data.allCqLsgxModel[i]);
                            }
                            dataList.push({
                                name: '产权历史及现状数据',
                                lsgxModel: data.allCqLsgxModel
                            });
                        }

                        if(data.dyLsgxModel.length > 0){
                            for(var i=0; i < data.dyLsgxModel.length; i++){
                                dmToMc(data.dyLsgxModel[i]);
                            }
                            dataList.push({
                                name: '抵押信息',
                                lsgxModel: data.dyLsgxModel
                            });
                        }

                        if(data.cfLsgxModel.length > 0){
                            for(var i=0; i < data.cfLsgxModel.length; i++){
                                dmToMc(data.cfLsgxModel[i]);
                            }
                            dataList.push({
                                name: '查封信息(不含预查封)',
                                lsgxModel: data.cfLsgxModel
                            });
                        }

                        if(data.ydyLsgxModel.length > 0){
                            for(var i=0; i < data.ydyLsgxModel.length; i++){
                                dmToMc(data.ydyLsgxModel[i]);
                            }
                            dataList.push({
                                name: '预抵押信息',
                                lsgxModel: data.ydyLsgxModel
                            });
                        }

                        if(data.ycfLsgxModel.length > 0){
                            for(var i=0; i < data.ycfLsgxModel.length; i++){
                                dmToMc(data.ycfLsgxModel[i]);
                            }
                            dataList.push({
                                name: '预查封信息',
                                lsgxModel: data.ycfLsgxModel
                            });
                        }

                        if(data.yyLsgxModel.length > 0){
                            for(var i=0; i < data.yyLsgxModel.length; i++){
                                dmToMc(data.yyLsgxModel[i]);
                            }
                            dataList.push({
                                name: '异议信息',
                                lsgxModel: data.yyLsgxModel
                            });
                        }

                        if(data.sdLsgxModel.length > 0){
                            for(var i=0; i < data.sdLsgxModel.length; i++){

                                if(data.sdLsgxModel[i].qszt == 0){
                                    data.sdLsgxModel[i].qsztmc = "正常";
                                }else{
                                    data.sdLsgxModel[i].qsztmc = "锁定";
                                }
                                data.sdLsgxModel[i].qllx = "sd";
                            }
                            dataList.push({
                                name: '锁定信息',
                                lsgxModel: data.sdLsgxModel
                            });
                        }
                    }

                    // 加载目录
                    loadMenu();

                },
                error: function (e) {
                    removeModal();
                    delAjaxErrorMsg(e);
                }
            });
        }
    }


    /**
     * 加载菜单数据
     */
    function loadMenu() {
        if(dataList != null && dataList.length > 0) {
            addModel();
            refreshMenuView(dataList);
            // 默认加载第一个
            // openQlxx(dataList[0].lsgxModel[0].id,dataList[0].lsgxModel[0].qllx);

            // 根据xmid选中左边对应的数据
            $('.submenu').find('a[data-id='+xmid +']').click();
            removeModal();
        } else {
            // 未查询到产权数据，全部显示空，同时避免缓存显示
            warnMsg(" 未查询到历史产权数据！");
            var asideView = document.getElementById('accordion');
            asideView.innerHTML = null;

            // 权利信息页面
            $('#qlxxIframe').attr("src", null);
        }
    }

    /**
     * 显示树菜单
     */
    function refreshMenuView(menuData) {
        // 初始化
        if(menuData && menuData.length > 0) {
            //渲染菜单
            var getAsideTpl = asideTpl.innerHTML
                ,asideView = document.getElementById('accordion');
            laytpl(getAsideTpl).render(menuData, function(html){
                asideView.innerHTML = html;
            });
            // 只执行了下拉 未触发a标签点击事件
            eval($('#accordion a').filter(':first').attr("href"));
            //默认全部展开
            $('.submenu').css('display','block');
            // $('.submenu li').toggleClass('open');
            $('.accordion li').toggleClass('open');
            form.render();

        }else {
            layer.msg("未查询到历史产权数据！");

            // 未查询到产权信息
            // 关闭菜单栏
            $('.bdc-aside-close').click();
            // 删除权利信息tab
            element.tabDelete('tabFilter', 'qlxxTab');
        }
    }

    function lsgx() {
        $('#lsgxIframe').attr("src", htmlConfig.lsgxHtml.lsgx + '?bdcdyh=' + bdcdyh);
    }


    /**
     * 加载权利信息页面和不动产信息页面
     * @param xmid
     * @param qllx sd
     */
    function openQlxx(xmid, qllx, bdcdyfwlx) {
        if(isNullOrEmpty(xmid) || isNullOrEmpty(qllx)){
            return;
        }else if(qllx == "sd"){
            console.log("锁定信息id："+xmid);
            // 锁定信息页面 带上跨域参数给锁定页面
            $('#qlxxIframe').attr("src", htmlConfig.sdxxHtml.sdHtml + "?sdxxid=" + xmid+"&isCrossOri=true&readOnly=true");
        }else {
            var qlxxHtml = getQllxHtml(xmid, qllx, bdcdyfwlx);
            // 权利信息页面 带上跨域参数给权利页面
            $('#qlxxIframe').attr("src", qlxxHtml + "?xmid=" + xmid+"&isCrossOri=true&readOnly=true");
            globalXmid = xmid;
            // 楼盘表信息页面
            // 点击楼盘表时，再加载楼盘表
            if($(".layui-tab-title.bdc-tab-title").find('li[lay-id="lpbTab"]').hasClass('layui-this')){
                openLpb(xmid);
            }
            // 权利信息页面加载完成后关闭遮罩层
            iframeOnload('qlxxIframe', 'removeModal');
        }
    }

    /**
     * 获取权利信息页面地址
     * @param xmid
     * @param qllx
     * @returns {*}
     */
    function getQllxHtml(xmid, qllx, bdcdyfwlx) {
        if(!isNullOrEmpty(xmid) && !isNullOrEmpty(qllx)) {
            if(htmlConfig.qlxxHtml != null) {
                switch (parseInt(qllx)){
                    // 土地所有权登记信息
                    case 1:
                    case 2: qlxxHtml = htmlConfig.qlxxHtml.tdsyqHtml; break;
                    // 建设用地使用权、宅基地使用权登记信息
                    case 3:
                    case 5:
                    case 7: qlxxHtml = htmlConfig.qlxxHtml.jsydsyqHtml; break;

                    case 4:
                    case 6:
                    case 8: if(bdcdyfwlx == 1){
                        // 房地产权登记信息（项目内多幢房屋）
                        qlxxHtml = htmlConfig.qlxxHtml.fdcqXmndzfwHtml;break;
                    }else {
                        // 房地产权登记信息（独幢、层、套、间房屋）
                        qlxxHtml = htmlConfig.qlxxHtml.fdcqHtml;break;
                    }
                    // 建筑物区分所有权业主共有部分登记信息
                    case 94: qlxxHtml = htmlConfig.qlxxHtml.jzwqfsyqyzgybfHtml; break;
                    // 构（建）筑物所有权登记信息
                    case 16:
                    case 18:
                    case 24:
                    case 25:
                    case 26: qlxxHtml = htmlConfig.qlxxHtml.gzwsyqHtml; break;
                    // 海域（含无居民海岛）使用权登记信息
                    case 15:
                    case 17: qlxxHtml = htmlConfig.qlxxHtml.hysyqHtml; break;
                    // 土地承包经营权、农用地的其他使用权登记信息（非林地）
                    case 9:
                    case 13:
                    case 14:
                    case 23:
                    case 30: qlxxHtml = htmlConfig.qlxxHtml.tdcbjyqNyddqtsyqHtml; break;
                    // 林权登记信息
                    case 11:
                    case 12:
                    case 31:
                    case 33:
                    case 34:
                    case 35:
                    case 36: qlxxHtml = htmlConfig.qlxxHtml.lqHtml; break;
                    // 地役权登记信息
                    case 19: qlxxHtml = htmlConfig.qlxxHtml.dyiqHtml; break;
                    // 抵押权登记信息
                    case 95: qlxxHtml = htmlConfig.qlxxHtml.dyaqHtml; break;
                    case 37: qlxxHtml = htmlConfig.qlxxHtml.dyaqHtml; break;
                    // 经营权
                    case 50: qlxxHtml = htmlConfig.qlxxHtml.jzqHtml; break;
                    // 预告登记信息
                    case 96: qlxxHtml = htmlConfig.qlxxHtml.ygdjHtml; break;
                    // 异议登记信息
                    case 97: qlxxHtml = htmlConfig.qlxxHtml.yydjHtml; break;
                    // 查封登记信息
                    case 98: qlxxHtml = htmlConfig.qlxxHtml.cfdjHtml; break;
                    // 其他相关权利登记信息（取水权、探矿权、采矿权等）
                    case 20:
                    case 21:
                    case 22:
                    case 99: qlxxHtml = htmlConfig.qlxxHtml.qtxgqlHtml; break;
                    case 92: qlxxHtml = htmlConfig.qlxxHtml.jzqHtml; break;
                    // 房地产权登记信息（项目内多幢房屋）
                    default: qlxxHtml = htmlConfig.qlxxHtml.fdcqXmndzfwHtml; break;
                }
            }
        }
        return qlxxHtml;
    }

    /**
     * 权属状态，登记类型代码转名称
     * data:单条权利信息
     */
    function dmToMc(data) {
        data.qsztmc = convertZdDmToMc("qszt", data.qszt, 'zdList');
        data.djlxmc = convertZdDmToMc("djlx", data.djlx, 'zdList');
    }
});
