/**
 * Created by Administrator on 2018/12/21.
 */
layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table,
        form = layui.form;
    $(function () {
        /**
         * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
         * @param 左侧目录树事件
         * @return
         * @description
         */
        // 手风琴点击效果
        $(document).on('click', '.link', function () {
            var $this = $(this);
            $this.parents('.accordion').find('.submenu').not($this.next()).slideUp().parent().removeClass('open');
            $this.next().slideToggle();
            $this.parent().toggleClass('open');
        });
        // 默认第一个逻辑栋高亮
        $('.accordion>li').first().addClass('open').find('.submenu').show().find('a').first().addClass('active');

        // 对于逻辑栋点击高亮
        $(document).on('click', '.submenu a', function () {
            $('.submenu a').removeClass('active');
            $(this).addClass('active');
        });

        //点击侧边栏收缩按钮
        var $asideClose = $('.bdc-aside-close'),
            $asideOpen = $('.bdc-aside-open'),
            $zoom = $('.bdc-aside-zoom'),
            $menuAside = $('.bdc-menu-aside'),
            $container = $('.bdc-container');
        $asideClose.on('click', function () {
            $(this).toggleClass('bdc-hide');
            $asideOpen.toggleClass('bdc-hide');
            $zoom.animate({'left': '-10px'}, 100);
            $menuAside.animate({'left': '-300px'}, 100);
            $container.animate({'padding-left': '15px'}, 100, function () {
                table.resize(currentRes.tableResizeId);
            });
            $('.bdc-btn-list').animate({"width": $('.bdc-table-box').width() + 280}, 100);
        });
        $asideOpen.on('click', function () {
            $(this).toggleClass('bdc-hide');
            $asideClose.toggleClass('bdc-hide');
            $zoom.animate({'left': '259px'}, 100);
            $menuAside.animate({'left': '0'}, 100);
            $container.animate({'padding-left': '275px'}, 100, function () {
                $('.bdc-btn-list').width($('.layui-table').width() - 31);
                table.resize(currentRes.tableResizeId);
            });
        });


        /**
         * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
         * @param 状态栏事件
         * @return
         * @description
         */
            //状态展示收缩按钮
        $('.bdc-state-zoom').on('click',function () {
            currentRes.stateIndex++;
            // 避免宽度改变
            var width = currentRes.tabDocument.find('.layui-show').width();
            var xScrollHeight = $("#"+currentRes.xScrollId).height();
            var tabHeight = $('.layui-tab').height();
            var tabTitleHeight = $(".layui-tab-title").height();
            var zsxxHeight = currentRes.tabDocument.find(".bdc-zsxx").height();
            var toolHeight = 50+38;// 50为 tool行  38为header
            if(zsxxHeight > 0){
                zsxxHeight += 4; // 上间距
            }
            if(currentRes.stateIndex % 2 == 0){
                //展示
                var autoStateHeight = currentRes.tabDocument.find('.bdc-state-show').css('height', 'auto').height();
                currentRes.tabDocument.find('.bdc-state-show').removeClass('bdc-state-hide').height(0).height(autoStateHeight);
                var showBoxHeight = currentRes.tabDocument.find(".bdc-state-show-box").height();
                var tableBodyHeigth = tabHeight - tabTitleHeight-zsxxHeight- showBoxHeight-toolHeight-xScrollHeight;
                currentRes.tabDocument.find('.layui-table-body').height(tableBodyHeigth);
            }else {
                //隐藏
                currentRes.tabDocument.find('.bdc-state-show').addClass('bdc-state-hide').height(0);
                var tableBodyHeight = tabHeight - tabTitleHeight-zsxxHeight-toolHeight-xScrollHeight;
                currentRes.tabDocument.find('.layui-table-body').height(tableBodyHeight);
            }
            currentRes.tabDocument.find('.layui-show').width(width);
            $(this).find('.layui-icon-down').toggleClass('bdc-hide');
            $(this).find('.layui-icon-up').toggleClass('bdc-hide');
            currentRes.tabDocument.find('.bdc-zsxx').toggleClass('bdc-border-bottom');

            // todo 重新计算滚动条
            // currentRes.refreshScrollBar();
        });
        //监听滚动事件
        var scrollTop = 0,
            scrollLeft = 0;
        $(window).on('scroll', function () {
            scrollTop = $(this).scrollTop();
            scrollLeft = $(this).scrollLeft();
        });

        //点击详细
        var $details = $('.bdc-details');
        $('.bdc-right-content').on('click', '.bdc-details-btn', function (event) {
            event.stopPropagation();
            var top = $(this).offset().top - 215 - scrollTop;
            var left = $(this).offset().left - scrollLeft - 354;
            $details.css({top: top, left: left}).removeClass('bdc-hide');
        });

        // 点击图标展示收起顶部信息栏
        $('.bdc-lc-container').on('click', '.bdc-operate-show', function () {
            $(this).find('.layui-icon-down').toggleClass('bdc-hide');
            $(this).find('.layui-icon-up').toggleClass('bdc-hide');

            if($('.bdc-container').hasClass('show-map')){
                //收起
                $('.bdc-container').removeClass('show-map');
                $('.bdc-container').addClass('hide-map');
            }else {
                //展开
                $('.bdc-container').addClass('show-map');
                $('.bdc-container').removeClass('hide-map');

            }
            // 调整备注高度
            changeBzHeight();

            if($(this).hasClass('first-click')){
                $(this).removeClass('first-click');
            }else {
                var height= $('.bdc-lc-container').height() + $('.layui-show')[1].scrollHeight + 85;
                $('body').css('height',height);
            }

        });

        /**
         * @description 以下为业务JS
         */
        form.on('checkbox(checkFilter)', function (data) {
            if ($(data.elem).data('index')) {
                if (data.elem.checked) {
                    //选中
                    currentRes.checkedList.push($(data.elem).data('index') + "");
                } else {
                    // 取消选中
                    currentRes.checkedList = $.grep(currentRes.checkedList, function (value) {
                        return value != ($(data.elem).data('index') + "");
                    });
                }
            }
        });
        //监听checkbox选择修改2019/6/24
        $('.bdc-right-content').on('click','.bdc-checkbox',function () {
            var $this = $(this);
            $this.toggleClass('layui-form-checked');
            if ($this.siblings().data('index')) {
                if ($this.hasClass('layui-form-checked')) {
                    //选中
                    currentRes.checkboxcheckedList.push($this.siblings().data('index') + "");
                    currentRes.checkedList.push($this.siblings().data('index') + "");
                } else {
                    // 取消选中
                    currentRes.checkboxcheckedList = $.grep(currentRes.checkboxcheckedList, function (value) {
                        return value != ($this.siblings().data('index') + "");
                    });
                    currentRes.checkedList = $.grep(currentRes.checkedList, function (value) {
                        return value != ($this.siblings().data('index') + "");
                    });
                }
            }
        });

        //头工具栏事件
        table.on('toolbar(hsTableLayFilter)', function (obj) {
            eval(obj.event + "Function()");
            return;
        });

        //头工具栏事件
        table.on('toolbar(ychsTableLayFilter)', function (obj) {
            eval(obj.event + "Function()");
            return;
        });
        // 加载主页面数据
        loadResource();
        currentRes.loadTableData();

        $('.bdc-aside-open').on('click', function (event) {
            initZdTree();
        });
        //点击选择按钮，显示或隐藏复选框
        // $('.bdc-select').on('click',function () {
        //     $('.layui-form-checkbox').toggleClass('bdc-hide');
        // });
        //点击更多
        $('body').on('click', '.bdc-more-box', function (event) {
            event.stopPropagation();
            $(this).find('.bdc-table-btn-more').toggle();
        });
        $('.bdc-right-content').on('click', '.bdc-export-tools .bdc-table-btn-more>span', function (event) {
            event.stopPropagation();
        });
        $('.bdc-table-btn-more a').on('click', function (event) {
            event.stopPropagation();
            $(this).parent().hide();
        });
        //点击页面任一空白位置，详情弹窗及更多操作按钮弹窗消失
        $('body').on('click', function () {
            $('.bdc-container').find('.bdc-table-btn-more').hide();
            $details.addClass('bdc-hide');
        });

        //点击 筛选内容 ，清空当前筛选条件
        $('.bdc-right-content').on('click','.bdc-search-content>span',function () {
            addModel();
            var $this = $(this);
            var $parent = $this.parent();
            $this.remove();
            setTimeout(function () {
                var searchList = ['',''];//与searchClassList对应关系
                var searchClassList = ['bdc-search-yt', 'bdc-search-jyzt'];
                var ytList = [];
                var jyztList = [];
                if($parent.find('span').length > 0){
                    var $searchYt = $('.bdc-search-yt');
                    if($searchYt.length > 0){
                        searchList[0] = $searchYt.attr('title');
                        ytList = $searchYt.attr('title').split(',');
                    }
                    var $searchJyzt = $('.bdc-search-jyzt');
                    if ($searchJyzt.length > 0) {
                        searchList[1] = $searchJyzt.attr('title');
                        jyztList = $searchJyzt.attr('title').split(',');
                    }
                } else {
                    $parent.remove();
                }
                updatePageStyle(searchList, searchClassList, ytList, jyztList);
                //取消选中按钮状态
                var checkedHs = $('.layui-show').find('.layui-form-checked');
                if (checkedHs) {
                    for (var i = 0; i < checkedHs.length; i++) {
                        checkedHs[i].classList.remove('layui-form-checked');
                    }
                }
                currentRes.checkboxcheckedList = [];
            },10);
        });
    });
});

function treeClickLjz(fwDcbIndex){
    $("#fwDcbIndex").val(fwDcbIndex);
    loadResource();
    $("#hsTabLi").click();
    currentRes.loadTableData();
}
// 重新加载 主数据TABLE 和 各Tab
function loadResource() {
    var fwDcbIndex = $("#fwDcbIndex").val();
    var code = $("#code").val();
    layui.use(['laytpl', 'form','table'], function () {
        var table = layui.table;
        initHsLpb();
        showFwhsTab();
    });
}

/**
 * 给 table 固定 高度
 * @param resource
 */
function resizeTable(resource){
    var element = resource.tabDocument;
    var bodyHeight = $('#tabDiv').height() - getTableHeight(element) - 179;
    element.find('.layui-table-body').css("height",bodyHeight);
}

// 点击自然幢加载逻辑幢列表
function showLjz(lszd, zrzh) {
    if ($("#" + lszd + "_" + zrzh).find("li").length == 0) {
        var url = "";
        if (resourceData.links) {
            for (var i = 0; i < resourceData.links.length; i++) {
                var link = resourceData.links[i];
                if (link.rel == "zdtreelistljz") {
                    url = link.href;
                }
            }
        }
        if (url) {
            $.ajax({
                url: url,
                data: {
                    djh: lszd,
                    zrzh: zrzh
                },
                dataType: "json",  //指定服务器返回的数据类型为jsonp
                success: function (data) {
                    var jsonData = {
                        data: data
                    }
                    layui.use(['laytpl'], function () {
                        var laytpl = layui.laytpl;
                        var ljzTpl = zdTreeLjzListTpl.innerHTML
                            , zrzUl = document.getElementById(lszd + "_" + zrzh);
                        laytpl(ljzTpl).render(jsonData, function (html) {
                            zrzUl.innerHTML = html;
                        });
                    });
                }
            });
        }
    }
}

// 刷新主页面方法
function refreshMainPage() {
    layer.closeAll();
    hsRes.loadTableData();
    ychsRes.loadTableData();
}

/**
 * TAB 点击 监听事件
 */
layui.use(['element'], function () {
    var element = layui.element;
    element.on('tab(tabFilter)', function(data){
        if($(this).attr("id") == "ychsTabLi"){
            currentRes = ychsRes;
            var ycDcbIndex = $("#ycfwDcbIndex").val();
            if(ycDcbIndex != $("#fwDcbIndex").val()){
                currentRes.loadTableData();
            }
        }
        if($(this).attr("id") == "hsTabLi"){
            currentRes = hsRes;
        }

        var $bjBorder = $('.bdc-border');
        for(var i = 0,len = $bjBorder.length; i < len; i++){
            var $border = $($bjBorder[i]);
            $border.height($border.parent().innerHeight());
        }
    });
});

// 展示顶部信息栏
function showLcTitle(data) {
    $('.bdc-lc-container').removeClass('bdc-hide');
    $('.bdc-container').addClass('show-map');
    // 隐藏页面坐落，备注
    $('#ljzTitle').addClass('bdc-hide');
    $('#bzTitle').addClass('bdc-hide');

    if (data.fwmc && data.fwmc.value) {
        $(".bdc-lc-title #fwmc span").html(data.fwmc.value);
    }
    if (data.dh && data.dh.value) {
        $(".bdc-lc-title #dh span").html(data.dh.value);
    }

    if (data.lszd && data.lszd.value) {
        $("#relationMap p[name='lszd']").html(data.lszd.value);
    }
    if (data.scjzmj && data.scjzmj.value) {
        $("#relationMap p[name='scjzmj']").html(data.scjzmj.value + ' m<sup>2</sup>');
    }
    if (data.ycjzmj && data.ycjzmj.value) {
        $("#relationMap p[name='ycjzmj']").html(data.ycjzmj.value + ' m<sup>2</sup>');
    }
    if (data.fwjg && data.fwjg.value) {
        $("#relationMap p[name='fwjg']").html(data.fwjg.value);
    }
    if (data.zldz && data.zldz.value) {
        $("#relationMap p[name='zldz']").html(data.zldz.value);
    }
    if (data.bz && data.bz.value) {
        $("#relationMap p[name='bz'] span").html(data.bz.value);
    }
}

// 调整备注高度
function changeBzHeight() {
    var bzHeight = $("#relationMap p[name='bz'] span").height();
    $("#relationMap .bz-item").css('max-height', 10 + bzHeight);
    if($('.bdc-container').hasClass('show-map')){
        $('.bdc-container').css('padding-top',130 + bzHeight);
        $('.bdc-container .bdc-lc-container').css('height',120 + bzHeight);

    }else if($('.bdc-container').hasClass('hide-map')){
        $('.bdc-container').css('padding-top',50);
        $('.bdc-container .bdc-lc-container').css('height',40);
    }
}
