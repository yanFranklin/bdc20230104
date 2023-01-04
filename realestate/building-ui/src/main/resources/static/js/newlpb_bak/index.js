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
            if(currentRes.stateIndex % 2 == 0){
                //展示
                var autoStateHeight = currentRes.tabDocument.find('.bdc-state-show').css('height', 'auto').height();
                currentRes.tabDocument.find('.bdc-state-show').removeClass('bdc-state-hide').height(0).animate({"height": autoStateHeight},100);

                currentRes.tabDocument.find('.layui-table-body').height($('.layui-tab').height() - 106 - autoStateHeight -46);
                currentRes.tabDocument.find('.layui-table-fixed .layui-table-body').height($('.layui-tab').height() - 106 - autoStateHeight -46 - 17);
            }else {
                //隐藏
                currentRes.tabDocument.find('.bdc-state-show').addClass('bdc-state-hide').animate({"height": 0},100);

                currentRes.tabDocument.find('.layui-table-body').height($('.layui-tab').height() - 140);
                currentRes.tabDocument.find('.layui-table-fixed .layui-table-body').height($('.layui-tab').height() - 140 - 17);
            }
            $(this).find('.layui-icon-down').toggleClass('bdc-hide');
            $(this).find('.layui-icon-up').toggleClass('bdc-hide');
            currentRes.tabDocument.find('.bdc-zsxx').toggleClass('bdc-border-bottom');
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

        /**
         * @description 以下为业务JS
         */
        form.on('checkbox(checkFilter)', function (data) {
           if (data.elem.checked) {
               //选中
               currentRes.checkedList.push($(data.elem).data('index'));
           } else {
               // 取消选中
               currentRes.checkedList = $.grep(currentRes.checkedList, function (value) {
                   return value != $(data.elem).data('index');
               });
           }
        });
        //监听checkbox选择修改2019/6/24
        $('.bdc-right-content').on('click','.bdc-checkbox',function () {
            var $this = $(this);
            $this.toggleClass('layui-form-checked');

            if ($this.hasClass('layui-form-checked')) {
                //选中
                currentRes.checkedList.push($this.siblings().data('index'));
            } else {
                // 取消选中
                currentRes.checkedList = $.grep(currentRes.checkedList, function (value) {
                    return value != $this.siblings().data('index');
                });
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
                        ytList = $searchYt.split(',');
                    }
                    var $searchJyzt = $('.bdc-search-jyzt');
                    if ($searchJyzt.length > 0) {
                        searchList[1] = $searchJyzt.attr('title');
                        jyztList = $searchJyzt.split(',');
                    }
                }else {
                    $parent.remove();
                }
                updatePageStyle(searchList, searchClassList, ytList, jyztList);
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
        $.ajax({
            url: resUrl,
            dataType: "json",
            async: false,
            data: {
                fwDcbIndex: fwDcbIndex,
                gzlslid: gzlslid,
                code: code
            },
            success: function (data) {
                resourceData = data;
                if (resourceData
                    && resourceData.info) {
                    //  页面 右侧的 逻辑幢  坐落基本信息
                    if (resourceData.info.ljzTitle
                        && resourceData.info.ljzTitle.refInfo) {
                        var infoKey = resourceData.info.ljzTitle.refInfo;
                        var titleVal = eval("resourceData.info." + infoKey +".value");
                        $("#ljzTitle").html(titleVal);
                    }
                    $("#djh").val(resourceData.info.lszd.value);
                    $("#zrzh").val(resourceData.info.zrzh.value);
                    $("#zrzh").val(resourceData.info.zrzh.value);
                }
                $("#fwDcbIndex").val(fwDcbIndex);
                // 加载各TAB
                // 宗地基本信息
                initZdJbxx();
                // 自然幢基本信息
                initZrzJbxx();
                // 逻辑幢基本信息
                initLjzJbxx();
                // 判断是否展示目录树
                zdTreeShow();
                // 预测楼盘表TAB
                initYchsLpb();
                // 实测楼盘表TAB
                initHsLpb();
                if(currentRes){
                    // 判断 TAB标签是否隐藏
                    var hideLength = $(".layui-tab-title").find(".layui-hide").length;
                    var allLen = $(".layui-tab-title").find("li").length;
                    // 如果没隐藏的TAB标签个数超过1个 则 显示TAB标签栏
                    $(".layui-tab-title").removeClass("layui-hide");
                    currentRes.showThisTab();
                }
            },
            // added by cyc at 2019-08-13
            error:function (data){
                showFwhsTab();
                removeModal();
            }
        });
    });
}

function renderTable(resource,dynamicCol,mainData,mergeData){
    layui.use(['table','form'], function () {
        var table = layui.table;
        var form = layui.form;
        table.render({
            elem: '#'+resource.tableId,
            id: resource.tableResizeId,
            cols: dynamicCol,
            toolbar: '#' + resource.toorBarId,
            defaultToolbar: [],
            limit:100,
            data: mainData,
            done: function () {
                initCartIcon(resource);
                // 表格 checkbox 判断是否隐藏
                if(resource.checkBoxHide){
                    resource.tabDocument.find('.cell-checkbox').addClass('bdc-hide');
                }else{
                    resource.tabDocument.find('.cell-checkbox').removeClass('bdc-hide');
                }

                mergeData.forEach(function (value) {
                    if(value[2].rowspan){
                        $('.layui-table-main tbody tr:nth-child('+value[0]+') td:nth-child('+ value[1] +')').attr('rowspan',value[2].rowspan);
                        for(var i = 1; i < value[2].rowspan; i++){
                            var getInd = value[0] + i;
                            $('.layui-table-main tbody tr:nth-child('+getInd +') td:nth-child('+ value[1] +')').css('display','none');
                        }
                    }else if(value[2].colspan){
                        $('.layui-table-main tbody tr:nth-child('+value[0]+') td:nth-child('+ value[1] +')').attr('colspan',value[2].colspan);
                        for(var n = 1; n < value[2].colspan; n++){
                            var getIndex = value[1] + n;
                            $('.layui-table-main tbody tr:nth-child('+value[0] +') td:nth-child('+ getIndex +')').css('display','none');
                        }
                    }else if(value[2].up){
                        var getHtml = $('.layui-table-main tbody tr:nth-child(' + value[0] + ') td:nth-child(' + value[1] + ')').html();
                        for(var n = 0; n < value[2].up - 1; n++){
                            var getIndex1 = value[0] - n;
                            $('.layui-table-main tbody tr:nth-child('+getIndex1 +') td:nth-child('+ value[1] +')').css('display','none');
                        }
                        var getNum = value[0] - value[2].up +1;
                        $('.layui-table-main tbody tr:nth-child('+getNum+') td:nth-child('+ value[1] +')').html(getHtml).attr('rowspan',value[2].up);
                    }
                });
                //内容扩展高度调整
                resizeTable(resource.tabDocument);
                checkDefShowColumn(resource);
                form.render("checkbox");
            }
        });
    })
}


function resizeTable(element){
    var timestamp1 = (new Date()).valueOf();
    var bodyHeight = $('#tabDiv').height() - getTableHeight(element) - 159;
    element.find('.layui-table-body').css("height",bodyHeight);
    var timestamp2 = (new Date()).valueOf();
    console.log('设置tabel高度'+ (timestamp2-timestamp1));
    timestamp1 = (new Date()).valueOf();
    //rowspan为1的，调整位置
    var $tableCol = element.find('.layui-table-header thead .layui-table-col-special');
    for(var i = 0; i < $tableCol.length; i++){
        var $tableCol = element.find('.layui-table-header thead .layui-table-col-special');
        var colLength = 0;
        var colIndex = 0;
        var colMoreIndex = 0;
        for(var i = 0; i < $tableCol.length; i++){
            if($($tableCol[i]).attr('rowspan') == 2){
                colIndex = i;
                colLength++;
                $($tableCol[i]).find('.layui-table-cell').css({'top':'-16px','text-align': 'center'});
            }else {
                colMoreIndex++;
            }
        }
        if(colLength != 1 && colMoreIndex != 0) {
            for(var i = 0; i < $tableCol.length; i++){
                if($($tableCol[i]).attr('rowspan') == 2){
                    $($tableCol[i]).find('.layui-table-cell').css({'top':'-16px','text-align': 'center'});
                }

            }
        }else if(colLength != 1 || colMoreIndex == 0){
            for(var i = 0; i < $tableCol.length; i++){
                if($($tableCol[i]).attr('rowspan') == 2){
                    $($tableCol[i]).find('.layui-table-cell').css({'top':'0','text-align': 'center'});
                }
            }
        }

    }
    timestamp2 = (new Date()).valueOf();
    console.log('rowspan为1的，调整位置 '+ (timestamp2-timestamp1));
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
            // 选中状态
            for(var key in eval("currentRes.checkedShowColumn")){
                if(eval("currentRes.checkedShowColumn."+key) != null){
                    // 增加选中状态
                    currentRes.tabDocument.find(".bdc-export-tools").find("input[data-key='"+key+"']").next("div[class='layui-unselect layui-form-checkbox']")
                        .addClass("layui-form-checked");
                }
            }
        }
        if($(this).attr("id") == "hsTabLi"){
            currentRes = hsRes;
            // 选中状态
            for(var key in eval("currentRes.checkedShowColumn")){
                if(eval("currentRes.checkedShowColumn."+key) != null){
                    // 增加选中状态
                    currentRes.tabDocument.find(".bdc-export-tools").find("input[data-key='"+key+"']").next("div[class='layui-unselect layui-form-checkbox']")
                        .addClass("layui-form-checked");
                }
            }
        }

        var $bjBorder = $('.bdc-border');
        for(var i = 0,len = $bjBorder.length; i < len; i++){
            var $border = $($bjBorder[i]);
            $border.height($border.parent().innerHeight());
        }
    });
});
