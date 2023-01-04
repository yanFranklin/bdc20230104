/**
 * 历史关系操作JS
 */
var xmid = $.getUrlParam("xmid");
var bdcdyh = $.getUrlParam("bdcdyh");
var dataAll;
var globalXmid;
var zdList = getMulZdList("qszt");
layui.use(['form','jquery','laydate','element','layer','table'],function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl;

    $(function () {
        // 如果未传bdcdyh参数，需要根据xmid查询bdcdyh
        loadBdcdyh();
        // 加载一级菜单
        loadFirstMenu();

        loadAll();
    });

    //---------------------查询条件开始-------------------------------------------------------
    //按enter键
    $('.bdc-search').bind('keyup', function (event) {
        if (event.keyCode == "13") {
            var getSearchValue = $('.bdc-search').val();
            if (getSearchValue != '') {
                $('#accordion').addClass('bdc-hide');
                $('#searchMenu').removeClass('bdc-hide');
                //回车执行查询
                var asideData = JSON.parse(dataAll);
                asideData.forEach(function (v) {
                    v.childList = [];
                    defaultMlxx(v.lsgxModelDTO);
                    if (v.lsgxXzqlModelDTO != null) {
                        if (v.lsgxXzqlModelDTO.dyLsgxModel.length != 0) {
                            for(var lsgxLen=0; lsgxLen < v.lsgxXzqlModelDTO.dyLsgxModel.length; lsgxLen++){
                                defaultMlxx(v.lsgxXzqlModelDTO.dyLsgxModel[lsgxLen])
                            }
                            v.childList.push({
                                name: '抵押信息',
                                childTree: v.lsgxXzqlModelDTO.dyLsgxModel
                            });
                        }

                        if (v.lsgxXzqlModelDTO.jzLsgxModel.length != 0) {
                            for(var lsgxLen=0; lsgxLen < v.lsgxXzqlModelDTO.jzLsgxModel.length; lsgxLen++){
                                defaultMlxx(v.lsgxXzqlModelDTO.jzLsgxModel[lsgxLen])
                            }
                            v.childList.push({
                                name: '居住权',
                                childTree: v.lsgxXzqlModelDTO.jzLsgxModel
                            });
                        }
                        if (v.lsgxXzqlModelDTO.cfLsgxModel.length != 0) {
                            for(var lsgxLen=0; lsgxLen < v.lsgxXzqlModelDTO.cfLsgxModel.length; lsgxLen++){
                                defaultMlxx(v.lsgxXzqlModelDTO.cfLsgxModel[lsgxLen])
                            }
                            v.childList.push({
                                name: '查封信息',
                                childTree: v.lsgxXzqlModelDTO.cfLsgxModel
                            });
                        }
                        if (v.lsgxXzqlModelDTO.yyLsgxModel.length != 0) {
                            for(var lsgxLen=0; lsgxLen < v.lsgxXzqlModelDTO.yyLsgxModel.length; lsgxLen++){
                                defaultMlxx(v.lsgxXzqlModelDTO.yyLsgxModel[lsgxLen])
                            }
                            v.childList.push({
                                name: '异议信息',
                                childTree: v.lsgxXzqlModelDTO.yyLsgxModel
                            });
                        }
                        if (v.lsgxXzqlModelDTO.ygLsgxModel.length != 0) {
                            for(var lsgxLen=0; lsgxLen < v.lsgxXzqlModelDTO.ygLsgxModel.length; lsgxLen++){
                                defaultMlxx(v.lsgxXzqlModelDTO.ygLsgxModel[lsgxLen])
                            }
                            v.childList.push({
                                name: '预抵押信息',
                                childTree: v.lsgxXzqlModelDTO.ygLsgxModel
                            });
                        }
                        if (v.lsgxXzqlModelDTO.dyiLsgxModel.length != 0) {
                            for(var lsgxLen=0; lsgxLen < v.lsgxXzqlModelDTO.dyiLsgxModel.length; lsgxLen++){
                                defaultMlxx(v.lsgxXzqlModelDTO.dyiLsgxModel[lsgxLen])
                            }
                            v.childList.push({
                                name: '地役信息',
                                childTree: v.lsgxXzqlModelDTO.dyiLsgxModel
                            });
                        }
                        if (v.lsgxXzqlModelDTO.sdLsgxModel.length != 0) {
                            for(var lsgxLen=0; lsgxLen < v.lsgxXzqlModelDTO.sdLsgxModel.length; lsgxLen++){
                                defaultMlxx(v.lsgxXzqlModelDTO.sdLsgxModel[lsgxLen])
                            }
                            v.childList.push({
                                name: '锁定信息',
                                childTree: v.lsgxXzqlModelDTO.sdLsgxModel
                            });
                        }
                    }
                });
                var getAsideData = JSON.parse(JSON.stringify(asideData));
                console.log(asideData);

                for (var i = getAsideData.length - 1; i >= 0; i--) {
                    //一级没有
                    if (getAsideData[i].lsgxModelDTO.bdcqzh != null) {
                        if (getAsideData[i].lsgxModelDTO.bdcqzh.indexOf(getSearchValue) == -1) {
                            //二级存在
                            if (getAsideData[i].childList.length != 0) {
                                for (var index = getAsideData[i].childList.length - 1; index >= 0; index--) {
                                    //二级没有
                                    if (getAsideData[i].childList[index].name.indexOf(getSearchValue) == -1) {
                                        //三级存在
                                        if (getAsideData[i].childList[index].childTree.length != 0) {
                                            for (var thirdIndex = getAsideData[i].childList[index].childTree.length - 1; thirdIndex >= 0; thirdIndex--) {
                                                //三级没有
                                                if (getAsideData[i].childList[index].childTree[thirdIndex].bdcqzh != null) {
                                                    if (getAsideData[i].childList[index].childTree[thirdIndex].bdcqzh.indexOf(getSearchValue) == -1) {
                                                        getAsideData[i].childList[index].childTree.splice(thirdIndex, 1);
                                                    }
                                                } else {
                                                    getAsideData[i].childList[index].childTree.splice(thirdIndex, 1);
                                                }
                                            }
                                            if (getAsideData[i].childList[index].childTree.length == 0) {
                                                getAsideData[i].childList.splice(index, 1);
                                            }
                                        } else {
                                            //三级不存在
                                            getAsideData[i].childList.splice(index, 1);
                                        }
                                    }
                                }
                                if (getAsideData[i].childList == 0) {
                                    getAsideData.splice(i, 1);
                                }
                            } else {
                                //二级不存在
                                getAsideData.splice(i, 1);
                            }
                        }
                    } else {
                        getAsideData.splice(i, 1);
                    }

                }
                var getSearchAsideTpl = searchMenuTpl.innerHTML
                    , asideSearchView = document.getElementById('searchMenu');
                laytpl(getSearchAsideTpl).render(getAsideData, function (html) {
                    asideSearchView.innerHTML = html;
                });
                //默认展开第一个
                $('#searchMenu .bdc-outer-li:first-child').addClass('open').find('.submenu').css('display', 'block');
                $('#searchMenu .bdc-outer-li:first-child .submenu .bdc-inner-li:first-child').addClass('open');
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
    if(bdcdyh.length ===28 &&bdcdyh.substring(19, 20) ==="L"){
        $(".bdc-lc-title>p").html("产权变更图");
        $(".bdc-operate-show").addClass("bdc-cqbg");
        $("#relationMap").css("height","600px");
    }else{
        $(".bdc-lc-title>p").html("户室变更图");

    }
}

/**
 * 加载户室变更图
 * @type {string}
 */
function loadHsbgPic() {
    if(!isNullOrEmpty(bdcdyh)) {
        if(bdcdyh.length ===28 &&bdcdyh.substring(19, 20) ==="L") {
            loadCqbgLsPic();

        }else {
            // 户室变更数据url
            var hsbgUrl = "/realestate-inquiry-ui/history/fwhs?bdcdyh=" + bdcdyh;
            $.ajax({
                url: hsbgUrl,
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data == null || data.length == 0 || isNullOrEmpty(data[0].bdcdyh) || isNullOrEmpty(data[0].zl)) {
                        hasHsbgData = false;
                    } else {
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
                                console.log('点击节点后的回调：', parma);
                                if (parma && parma.bdcdyh) {
                                    bdcdyh = parma.bdcdyh;

                                    // 加载一级菜单
                                    loadFirstMenu();
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
                error: function (e) {
                    delAjaxErrorMsg(e);
                }
            });
        }
    }
}

/**
 * 加载产权变更历史图
 * @type {string}
 */
function loadCqbgLsPic(){
    if(!isNullOrEmpty(bdcdyh)) {
        // 户室变更数据url
        var hsbgUrl = "/realestate-inquiry-ui/history/cqbgls?bdcdyh="+bdcdyh;
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
                    new cqbgFlow('relationMap', {
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

                                // 加载一级菜单
                                loadFirstMenu();
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
        var cqxxUrl = "/realestate-inquiry-ui/history/cqxxAll?bdcdyh=" + bdcdyh;
        $.ajax({
            url: cqxxUrl,
            dataType: 'json',
            success: function (data) {
                if (data != null) {
                    console.log("所有信息", JSON.stringify(data));
                    dataAll = JSON.stringify(data);
                }
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
    }
}

/**
 * 加载一级树菜单数据
 */
function loadFirstMenu() {
    if(!isNullOrEmpty(bdcdyh)) {
         addModel();
        // 左侧菜单栏加载url
        var cqxxUrl = "/realestate-inquiry-ui/history/cqxx?bdcdyh=" + bdcdyh;
        $.ajax({
            url: cqxxUrl,
            dataType: 'json',
            success: function(data) {
               if(data != null && data.length > 0) {
                   for(var i=0; i < data.length; i++){
                       defaultMlxx(data[i])
                   }
                   // 默认加载第一个
                   openQlxx(data[0].id, data[0].qllx, data[0].bdcdyfwlx);

                    refreshFirstMenuView(data);
                } else {
                   // 未查询到产权数据，全部显示空，同时避免缓存显示
                   warnMsg(" 未查询到历史产权数据！");
                   var asideView = document.getElementById('accordion');
                   asideView.innerHTML = null;

                   // 权利信息页面
                   $('#qlxxIframe').attr("src", null);
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
 * 显示一级树菜单
 */
function refreshFirstMenuView(menuData) {
    layui.use(['element', 'laytpl'], function() {
        var laytpl = layui.laytpl;
        var element = layui.element;
        // 初始化
        if(menuData && menuData.length > 0) {
            //渲染一级
            var getAsideTpl = asideTpl.innerHTML
                ,asideView = document.getElementById('accordion');
            laytpl(getAsideTpl).render(menuData, function(html){
                asideView.innerHTML = html;
            });
            // 只执行了下拉 未触发a标签点击事件
            eval($('#accordion a').filter(':first').attr("href"));
        }else {
            layer.msg("未查询到历史产权数据！");

            // 未查询到产权信息
            // 关闭菜单栏
            $('.bdc-aside-close').click();
            // 删除权利信息tab
            element.tabDelete('tabFilter', 'qlxxTab');
        }
    });
}

function lsgx() {
    layui.use(['element', 'laytpl'], function () {
        var laytpl = layui.laytpl;
        var element = layui.element;
        $('#lsgxIframe').attr("src", htmlConfig.lsgxHtml.lsgx + '?bdcdyh=' + bdcdyh +"&moduleCode=bdcdyDjLsgx");
    });
}

/**
 * 获取二级三级菜单数据
 * @param _xmid
 */
function loadOtherMenu(_dom, _xmid) {
    if(!isNullOrEmpty(_xmid)) {
        $.ajax({
            url: "/realestate-inquiry-ui/history/cqxx/xzxx?xmid=" + _xmid,
            dataType: 'json',
            success: function(data) {
                if(data != null) {
                    refreshOtherMenuView(_dom, data);
                }
            },
            error: function(e) {
                delAjaxErrorMsg(e);
            }
        });
    }
}

/**
 * 刷新二、三级菜单
 * @param _dom
 * @param xzql
 */
function refreshOtherMenuView(_dom, xzql) {
    layui.use('laytpl', function() {
        var laytpl = layui.laytpl;
        var newSecondData = [];
        // 初始化
        if(xzql != null) {
            // 抵押
            if(xzql.dyLsgxModel != undefined && xzql.dyLsgxModel.length > 0) {
                for(var i=0; i < xzql.dyLsgxModel.length; i++){
                    defaultMlxx(xzql.dyLsgxModel[i])
                }
                newSecondData.push({
                    name: '抵押信息',
                    child: xzql.dyLsgxModel
                });
            }

            // 居住权
            if(xzql.jzLsgxModel != undefined && xzql.jzLsgxModel.length > 0) {
                for(var i=0; i < xzql.jzLsgxModel.length; i++){
                    defaultMlxx(xzql.jzLsgxModel[i])
                }
                newSecondData.push({
                    name: '居住权',
                    child: xzql.jzLsgxModel
                });
            }

            if(xzql.cfLsgxModel != undefined && xzql.cfLsgxModel.length > 0) {
                for(var i=0; i < xzql.cfLsgxModel.length; i++){
                    defaultMlxx(xzql.cfLsgxModel[i])
                }
                newSecondData.push({
                    name: '查封信息',
                    child: xzql.cfLsgxModel
                });
            }

            if(xzql.yyLsgxModel != undefined && xzql.yyLsgxModel.length > 0) {
                for(var i=0; i < xzql.yyLsgxModel.length; i++){
                    defaultMlxx(xzql.yyLsgxModel[i])
                }
                newSecondData.push({
                    name: '异议信息',
                    child: xzql.yyLsgxModel
                });
            }

            if(xzql.ygLsgxModel != undefined && xzql.ygLsgxModel.length > 0) {
                for(var i=0; i < xzql.ygLsgxModel.length; i++){
                    defaultMlxx(xzql.ygLsgxModel[i])
                }
                newSecondData.push({
                    name: '预抵押信息',
                    child: xzql.ygLsgxModel
                });
            }

            if(xzql.dyiLsgxModel != undefined && xzql.dyiLsgxModel.length > 0) {
                for(var i=0; i < xzql.dyiLsgxModel.length; i++){
                    defaultMlxx(xzql.dyiLsgxModel[i])
                }
                newSecondData.push({
                    name: '地役信息',
                    child: xzql.dyiLsgxModel
                });
            }
            if(xzql.sdLsgxModel != undefined && xzql.sdLsgxModel.length > 0) {
                for(var i=0; i < xzql.sdLsgxModel.length; i++){
                    defaultMlxx(xzql.sdLsgxModel[i])
                }
                newSecondData.push({
                    name: '锁定信息',
                    child: xzql.sdLsgxModel
                });
            }
        }
        var getAsideSecondTpl = asideSecondTpl.innerHTML
            ,asideSecondView = $(_dom).children('.submenu')[0];
        laytpl(getAsideSecondTpl).render(newSecondData, function(html){
            asideSecondView.innerHTML = html;
        });
    });

}



/**
 * 加载权利信息页面和不动产信息页面
 * @param xmid
 * @param qllx
 */
function openQlxx(xmid, qllx, bdcdyfwlx) {
     //addModel();
    layui.use(['jquery','element'],function () {
        var $ = layui.jquery,
            element = layui.element;
        if(isNullOrEmpty(xmid) || isNullOrEmpty(qllx)){
            return;
        }
        if(qllx == "sd") {
            // 锁定信息页面 带上跨域参数给锁定页面
            $('#qlxxIframe').attr("src", htmlConfig.sdxxHtml.sdHtml + "?sdxxid=" + xmid + "&isCrossOri=true&readOnly=true");
        }else {
            var qlxxHtml = getQllxHtml(xmid, qllx, bdcdyfwlx);
            // 权利信息页面 带上跨域参数给权利页面
            $('#qlxxIframe').attr("src", qlxxHtml + "?xmid=" + xmid + "&isCrossOri=true&readOnly=true");
        }

        globalXmid = xmid;
        // 楼盘表信息页面
        // 点击楼盘表时，再加载楼盘表
        if($(".layui-tab-title.bdc-tab-title").find('li[lay-id="lpbTab"]').hasClass('layui-this')){
            openLpb(xmid);
        }
        // 权利信息页面加载完成后关闭遮罩层
        iframeOnload('qlxxIframe', 'removeModal');

    });
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
                case 26:qlxxHtml = htmlConfig.qlxxHtml.gzwsyqHtml; break;
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

//获取目录显示信息
function defaultMlxx(data) {
    data.qsztmc = convertZdDmToMc("qszt", data.qszt, 'zdList');
    if(!isNullOrEmpty(data.sdzt) ||data.sdzt ===0){
        if(data.sdzt ===0){
            data.qsztmc ="历史";
        }else{
            data.qsztmc ="现势";
        }
        data.qllx = "sd";
    }
    var mlxx ="";
    if(isNullOrEmpty(data.qsztmc)){
        mlxx +="无";
    }else{
        mlxx +=data.qsztmc;
    }
    if(isNullOrEmpty(data.bdcqzh)){
        mlxx+="/无证号";
    }else{
        mlxx+="/"+data.bdcqzh;
    }
    data.mlxx =mlxx;
    
}