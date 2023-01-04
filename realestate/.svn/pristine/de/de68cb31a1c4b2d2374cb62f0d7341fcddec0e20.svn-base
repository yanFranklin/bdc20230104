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
                if (ishsTab()) {
                    table.resize('hsId');
                } else {
                    table.resize('ychsId');
                }
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
                if (ishsTab()) {
                    table.resize('hsId');
                } else {
                    table.resize('ychsId');
                }
            });
        });


        /**
         * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
         * @param 状态栏事件
         * @return
         * @description
         */
            //状态展示收缩按钮
        var stateIndexMap = {ychs: 0, hs: 0};
        $('.bdc-state-zoom').on('click', function () {
            var stateElement = $("#hsTab");
            var stateIndex = 0;
            var tableId = "hsId";
            var checkList = hsCheckList;
            var tabFlag = "hs";
            if (!ishsTab()) {
                stateIndex = stateIndexMap.ychs;
                stateIndexMap.ychs++;
                stateElement = $("#ychsTab");
                tableId = "ychsId";
                stateIndex = stateIndexMap.ychs;
                checkList = ychsCheckList;
                tabFlag = "ychs";
            } else {
                stateIndexMap.hs++;
                stateIndex = stateIndexMap.hs;
            }
            if (stateIndex % 2 == 0) {
                //展示
                stateElement.find('.bdc-state-show').removeClass('bdc-state-hide').animate({"height": getStateHeight(stateElement) + "px"}, 100);
                refreshZoomTop(stateElement);
                var tableHeight = getTableHeight(stateElement);
                table.reload(tableId, {height: 'full-' + tableHeight});
                // 重新给hsCheckList 赋值
                $.each(checkList, function (index, v) {
                    $("input[data-index='" + v + "']")
                        .next("div[class='layui-unselect layui-form-checkbox']")
                        .addClass("layui-form-checked");
                });
            } else {
                //隐藏
                stateElement.find('.bdc-state-show').addClass('bdc-state-hide').animate({"height": 0}, 100);
                $(this).animate({"top": '0'}, 100);
                table.reload(tableId, {height: 'full-107'});
                // 重新给hsCheckList 赋值
                $.each(checkList, function (index, v) {
                    $("input[data-index='" + v + "']")
                        .next("div[class='layui-unselect layui-form-checkbox']")
                        .addClass("layui-form-checked");
                });
            }
            // 选中状态
            for (var key in eval(tabFlag + "_checkedShowColumn")) {
                if (eval(tabFlag + "_checkedShowColumn." + key).key) {
                    // 增加选中状态
                    stateElement.find(".bdc-export-tools").find("input[data-key='" + key + "']").next("div[class='layui-unselect layui-form-checkbox']")
                        .addClass("layui-form-checked");
                    // 单元格中显示选中效果
                    showColumn(eval(tabFlag + "_checkedShowColumn." + key), true, stateElement, tabFlag);
                } else {
                    showColumn(eval(tabFlag + "_checkedShowColumn." + key), false, stateElement, tabFlag);
                }
            }
            $(this).find('.layui-icon-down').toggleClass('bdc-hide');
            $(this).find('.layui-icon-up').toggleClass('bdc-hide');
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

        //监听事件 TODO 区分预测实测
        table.on('toolbar(lpbTable)', function(obj){
            switch(obj.event){
                case 'bdc-select':
                    $('.layui-table-box .layui-form-checkbox').toggleClass('bdc-hide');
                    break;
                case 'bdc-allselect':
                    $("i.one-no-select").each(function(){
                        $(this).removeClass("one-no-select");
                        $(this).addClass("one-onselected");
                    });
                    $("i.hang-no-select").each(function(){
                        $(this).removeClass("hang-no-select");
                        $(this).addClass("hang-onselected");
                    });
                    $("i.lie-no-select").each(function(){
                        $(this).removeClass("lie-no-select");
                        $(this).addClass("lie-onselected");
                    });
            }
        });

        //整行选中 x为物理层
        function selectallhang(x) {
            // var y=parseFloat(x)+1;
            $(".layui-table-main tbody td").each(function () {
                if($(this).find("p.bdc-fh span").attr("data-wlcs")==x){
                    $(this).find("i.one-icon").removeClass("one-no-select");
                    $(this).find("i.one-icon").addClass("one-onselected");
                }
            })
        }

        //物理层数取消选择
        function noselecthang(x) {
            //全部清除
            $(".layui-table-main tbody tr td").each(function () {
                if($(this).find("p.bdc-fh span").attr("data-wlcs")==x){
                    $(this).find("i.one-icon").addClass("one-no-select");
                    $(this).find("i.one-icon").removeClass("one-onselected");
                }
            });
            var hadslectedlie=[];
            //找到已经选择的单元号
            $(".layui-table-header thead th i.lie-icon").parent().each(function () {
                if($(this).find("i.lie-icon").hasClass("lie-onselected")){
                    hadslectedlie.push($(this).text());
                }
            });
            //再次添加
            if(hadslectedlie.length>0){
                for(var i=0;i<hadslectedlie.length;i++){
                    var obj=hadslectedlie[i];
                    $(".layui-table-main tbody tr td").each(function () {
                        if($(this).find("p.bdc-fh span").attr("data-dyh")==obj){
                            $(this).find("i.one-icon").removeClass("one-no-select");
                            $(this).find("i.one-icon").addClass("one-onselected");
                        }
                    });
                }
            }

        }

        //整列选中 x为第几列
        function selectalllie(x) {
            $(".layui-table-main tbody tr td").each(function () {
                if($(this).find("p.bdc-fh span").attr("data-dyh")==x){
                    $(this).find("i.one-icon").removeClass("one-no-select");
                    $(this).find("i.one-icon").addClass("one-onselected");
                }
            })
        }

        //单元号取消选择
        function noselectlie(x) {
            //全部清除
            $(".layui-table-main tbody tr td").each(function () {
                if($(this).find("p.bdc-fh span").attr("data-dyh")==x){
                    $(this).find("i.one-icon").addClass("one-no-select");
                    $(this).find("i.one-icon").removeClass("one-onselected");
                }
            });
            var hadslectedhang=[];
            //找到已经选择的单元号
            $(".layui-table-fixed .layui-table-body tbody tr i.hang-icon").parent().each(function () {
                if($(this).find("i.hang-icon").hasClass("hang-onselected")){
                    hadslectedhang.push($(this).text());
                }
            });
            //再次添加
            if(hadslectedhang.length>0){
                for(var i=0;i<hadslectedhang.length;i++){
                    var obj=hadslectedhang[i];
                    $(".layui-table-main tbody tr td").each(function () {
                        if($(this).find("p.bdc-fh span").attr("data-wlcs")==obj){
                            $(this).find("i.one-icon").removeClass("one-no-select");
                            $(this).find("i.one-icon").addClass("one-onselected");
                        }
                    });
                }
            }
        }

        /**
         * @description 以下为业务JS
         */
        form.on('checkbox(checkFilter)', function (data) {
            var hsTab = $(".layui-tab-title").find(".layui-this").attr("id") == "hsTabLi";
            if (data.elem.checked) {
                //选中
                if (hsTab) {
                    hsCheckList.push($(data.elem).data('index'));
                } else {
                    ychsCheckList.push($(data.elem).data('index'));
                }
            } else {
                // 取消选中
                if (hsTab) {
                    hsCheckList = $.grep(hsCheckList, function (value) {
                        return value != $(data.elem).data('index');
                    });
                } else {
                    ychsCheckList = $.grep(ychsCheckList, function (value) {
                        return value != $(data.elem).data('index');
                    });
                }
            }
        });

        //头工具栏事件
        table.on('toolbar(lpbTable)', function (obj) {
            eval(obj.event + "Function()");
            return;
        });

        //头工具栏事件
        table.on('toolbar(ycLpbTable)', function (obj) {
            eval(obj.event + "Function()");
            return;
        });
        // 加载主页面数据
        loadTable();

        $('.bdc-aside-open').on('click', function (event) {
            initZdTree();
        });
        //点击选择按钮，显示或隐藏复选框
        // $('.bdc-select').on('click',function () {
        //     $('.layui-form-checkbox').toggleClass('bdc-hide');
        // });
        //点击更多
        $('.bdc-container').on('click', '.bdc-more-box', function (event) {
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

        //单个确认点击
        $("body").on("click","i.one-icon",function () {
            //获取单元号以及物理层数
            var dyh=$(this).siblings("span").attr("data-dyh");
            var wlcs=$(this).siblings("span").attr("data-wlcs");
            if($(this).hasClass("one-no-select")){
                $(this).removeClass("one-no-select");
                $(this).addClass("one-onselected");
            }else{
                $(this).removeClass("one-onselected");
                $(this).addClass("one-no-select");
                $("i.hang-icon").each(function () {
                    if($(this).parent().text()==wlcs){
                        $(this).removeClass("hang-onselected");
                        $(this).addClass("hang-no-select");
                    }
                });

                $("i.lie-icon").each(function () {
                    if($(this).parent().text()==dyh){
                        $(this).removeClass("lie-onselected");
                        $(this).addClass("lie-no-select");
                    }
                })
            }
        });
        //物理层确认点击
        $("body").on("click","i.hang-icon",function () {
            //获取当前物理层
            var hang=$(this).parent().text();
            if($(this).hasClass("hang-no-select")){
                $(this).removeClass("hang-no-select");
                $(this).addClass("hang-onselected");
                //整行选中
                selectallhang(hang);
            }else{
                $(this).removeClass("hang-onselected");
                $(this).addClass("hang-no-select");
                //物理层取消选择
                noselecthang(hang)
            }
        });
        //单元号确认点击
        $("body").on("click","i.lie-icon",function () {
            // 获取当前单元号
            var lie=$(this).parent().text();
            if($(this).hasClass("lie-no-select")){
                $(this).removeClass("lie-no-select");
                $(this).addClass("lie-onselected");
                //整列选中
                selectalllie(lie)
            }else{
                $(this).removeClass("lie-onselected");
                $(this).addClass("lie-no-select");
                //单元号取消选择
                noselectlie(lie);
            }
        });
    });
});

function treeClickLjz(fwDcbIndex){
    $("#fwDcbIndex").val(fwDcbIndex);
    loadTableOnly();
}

function loadTableOnly(){
    var fwDcbIndex = $("#fwDcbIndex").val();
    var code = $("#code").val();
    layui.use(['laytpl', 'form'], function () {
        $.ajax({
            url: hsUrl,
            dataType: "json",
            async: false,
            data: {
                fwDcbIndex: fwDcbIndex,
                code: code
            },
            success: function (data) {
                globalData = data;
                if (globalData
                    && globalData.resource
                    && globalData.resource.info) {
                    if (globalData.resource.info.zrzh && globalData.resource.info.zrzh.value) {
                        $("#zrzh").val(globalData.resource.info.zrzh.value);
                    }
                }
                $("#fwDcbIndex").val(fwDcbIndex);
                // 加载主表格
                initMainTable(data,"hs");
                // 加载各主TAB
                var liList = $(".layui-tab-title").find("li");
                $.each(liList, function () {
                    $(this).click();
                })
            }
        });
    });
    // 清空所有选框
    hsCheckList = [];
}


// 重新加载 主数据TABLE 和 各Tab
function loadTable() {
    var fwDcbIndex = $("#fwDcbIndex").val();
    var code = $("#code").val();
    layui.use(['laytpl', 'form'], function () {
        $.ajax({
            url: hsUrl,
            dataType: "json",
            async: false,
            data: {
                fwDcbIndex: fwDcbIndex,
                code: code
            },
            success: function (data) {
                globalData = data;
                if (globalData
                    && globalData.resource
                    && globalData.resource.info) {
                    if (globalData.resource.info.lszd && globalData.resource.info.lszd.value) {
                        $("#djh").val(globalData.resource.info.lszd.value);
                        showZdTab();
                        showZdTree();
                    } else {
                        hideZdTab();
                        hideZdTree();
                    }
                    if (globalData.resource.info.zrzh && globalData.resource.info.zrzh.value) {
                        $("#zrzh").val(globalData.resource.info.zrzh.value);
                        showZrzTab();
                    } else {
                        hideZrzTab();
                    }
                }
                $("#fwDcbIndex").val(fwDcbIndex);
                // 加载各TAB
                initZdJbxx();
                initZrzJbxx();
                initLjzJbxx();
                // 判断是否展示目录树
                zdTreeShow();
                initYchsLpb();
                // 初始化状态色块
                initState("hs");
                initHsBtns();
                initShowColumn("hs");
                // 加载主表格
                initMainTable(data,"hs");
                // 加载各主TAB
                var liList = $(".layui-tab-title").find("li");
                $.each(liList, function () {
                    $(this).click();
                })
            }
        });
    });
    // 清空所有选框
    hsCheckList = [];
}
function initMainTable(data,flag) {
    var dynamicCol = [
        [{field: 'wlcs', unresize: true, align: 'center', title: '物理层数', fixed: 'left', width: 90, rowspan: 2}
            , {field: 'dycs', unresize: true, align: 'center', title: '定义层数', fixed: 'left', width: 90, rowspan: 2}
        ],
        []
    ]
    var mainData = [];
    var mergeData = [];

    // 第一次循环 处理mergeData
    data.cFwList.forEach(function (value, index) {

        value.dyFwList.forEach(function (val,ind) {
            // 循环每层 每单元
            // console.log(val);
            // console.log(val.fwhsResourceDTOList);
            for(var n = 0; n < val.maxHs; n++){
                // console.log(val.fwhsResourceDTOList[n]);
                if(!$.isEmptyObject(val.fwhsResourceDTOList[n]) && val.fwhsResourceDTOList[n].info.hbhs){
                    if(val.fwhsResourceDTOList[n].info.hbhs.value){
                        // console.log(va.info.hbfx.value);
                        var num = 0;
                        for(var m = 0; m < ind; m++){
                            num += data.cFwList[index].dyFwList[m].maxHs;
                        }
                        switch(val.fwhsResourceDTOList[n].info.hbfx.value)
                        {
                            case '1':case '2':
                            mergeData.push([index+1,n+num+3,{colspan:val.fwhsResourceDTOList[n].info.hbhs.value + 1}]);
                            for(var v = 1; v <= val.fwhsResourceDTOList[n].info.hbhs.value; v++){
                                val.fwhsResourceDTOList.splice(n+v,0,{});
                            }
                            break;
                            case '3':
                                for(var v = 1; v <= val.fwhsResourceDTOList[n].info.hbhs.value; v++){
                                    // 如果向上 超出层高  合并户数-1
                                    var lim = index-v;
                                    if(lim < 0){
                                        val.fwhsResourceDTOList[n].info.hbhs.value
                                            = val.fwhsResourceDTOList[n].info.hbhs.value - 1;
                                    }else{
                                        // 循环上一层的 单元
                                        data.cFwList[index-v].dyFwList.forEach(function (lastVal,lastInd) {
                                            if(lastVal.dyh == val.dyh){
                                                // 判断正上方是否有户室 如果有户室 则 +1 如果没有 不需要处理
                                                var lastFwhs = lastVal.fwhsResourceDTOList[n];
                                                if(lastFwhs){
                                                    lastVal.fwhsResourceDTOList.splice(n,0,{});
                                                    // 循环单元号 给 当前单元cospan +1
                                                    data.dyList.forEach(function(dyVal){
                                                        if(dyVal.dyh == val.dyh){
                                                            dyVal.cospan += 1;
                                                        }
                                                    })
                                                }
                                            }
                                        });
                                    }
                                }
                                mergeData.push([index +1,n+num+3,{up:val.fwhsResourceDTOList[n].info.hbhs.value + 1}]);
                                break;
                            case '4':
                                for(var v = 1; v <= val.fwhsResourceDTOList[n].info.hbhs.value; v++){
                                    // 如果向下 超出最低层数 合并户数-1
                                    var lim = index+v;
                                    if(data.cFwList[lim]){
                                        // 循环下一层的 单元
                                        data.cFwList[index+v].dyFwList.forEach(function (nextVal,lastInd) {
                                            if(nextVal.dyh == val.dyh){
                                                var nextHs = nextVal.fwhsResourceDTOList[n];
                                                // 判断正下方是否有户室 如果有户室 则 +1 如果没有 不需要处理
                                                if(nextHs){
                                                    nextVal.fwhsResourceDTOList.splice(n,0,{});
                                                    // 循环单元号 给 当前单元cospan +1
                                                    data.dyList.forEach(function(dyVal){
                                                        if(dyVal.dyh == val.dyh){
                                                            dyVal.cospan += 1;
                                                        }
                                                    })
                                                }
                                            }
                                        });
                                    }else{
                                        val.fwhsResourceDTOList[n].info.hbhs.value
                                            = val.fwhsResourceDTOList[n].info.hbhs.value - 1;
                                    }
                                }
                                mergeData.push([index+1,n+num+3,{rowspan:val.fwhsResourceDTOList[n].info.hbhs.value + 1}]);
                                break;
                        }
                    }
                }
            }
        });
    });

    // 循环层
    data.cFwList.forEach(function (value, index) {
        mainData.push({
            wlcs: value.wlcs+'<i class="hang-no-select hang-icon"></i>',
            dycs: value.dycs,
            dyFwList: [[], []]
        });
        for (var k = 0; k < data.dyList.length; k++) {
            // 循环每层 每单元
            var dyItem = {};
            for (var a = 0; a < value.dyFwList.length; a++) {
                if (value.dyFwList[a].dyh == data.dyList[k].dyh) {
                    dyItem = value.dyFwList[a];
                }
            }
            if (dyItem && dyItem.fwhsResourceDTOList) {
                for (var j = 0; j < data.dyList[k].colspan; j++) {
                    if (dyItem.fwhsResourceDTOList[j]) {
                        var va = dyItem.fwhsResourceDTOList[j];
                        if (dyItem.maxHs == 1) {
                            mainData[index].dyFwList[0].push(va);
                        } else {
                            mainData[index].dyFwList[1].push(va);
                        }
                    } else {
                        mainData[index].dyFwList[1].push({});
                    }
                }
            } else {
                for (var j = 0; j < data.dyList[k].colspan; j++) {
                    mainData[index].dyFwList[1].push({});
                }
            }
        }
    });
    var dynamicIndex = 0;
    var colspan1Num = 0;
    data.dyList.forEach(function (v) {
        var nowNum = colspan1Num;
        if (v.colspan == 1) {
            dynamicCol[0].push({
                field: '', unresize: true, title: v.dyh+"<i class='lie-no-select lie-icon'></i>", rowspan: 2, width: 110, templet: function (d) {
                    var colList = d.dyFwList[0][nowNum];
                    if (colList && colList.info) {
                        if (!colList.info.fjh.value) {
                            colList.info.fjh.value = '';
                        }
                        var dataInfo = JSON.stringify(colList.info);
                        var div = "<div class='layui-form cell-checkbox'>" +
                            "<input type='checkbox' name='house' lay-skin='primary'" +
                            "data-dyh='"+ v.dyh +"'" +
                            "data-info='"+ dataInfo +"'" +
                            "data-index='" + colList.info.fwHsIndex.value + "' lay-filter='checkFilter' >" +
                            "</div>";
                        var fh = getFjh(colList);
                        var desc = getDescHtml(colList);
                        var jyzt = getJyztHtml(colList);
                        var qlzt = getQlztHtml(colList);
                        return div + fh + desc + jyzt + qlzt;
                    } else {
                        return '';
                    }
                }
            });
            colspan1Num++;
        } else {
            dynamicIndex += v.colspan;
            dynamicCol[0].push({align: "center", title: v.dyh+"<i class='lie-no-select lie-icon'></i>", colspan: v.colspan});
        }
    });
    // 循环处理动态列
    forDynamicCol(dynamicIndex,dynamicCol);

    // 循环处理车库
    if(data.lpbCkVo && data.lpbCkVo.ckList){
        var maxnum = data.lpbCkVo.maxnum;
        var rowspan = data.lpbCkVo.rowspan;
        var wlcs = data.lpbCkVo.wlcs;
        var dycs = data.lpbCkVo.dycs;
        var ckList = data.lpbCkVo.ckList;
        var count = 0;
        for(var i = 0 ; i < rowspan ; i++){
            mainData.push({
                wlcs: wlcs,
                dycs: dycs,
                dyFwList: [[], []]
            });
            for(var j = 0 ;j < maxnum ; j++){
                if(ckList[count]){
                    mainData[mainData.length-1].dyFwList[1].push(ckList[count]);
                }else{
                    mainData[mainData.length-1].dyFwList[1].push({});
                }
                count++;
            }
        }
        if(dynamicCol[1].length == 0){
            dynamicCol[0].push({align: "center", title: "", colspan: maxnum});
            // 循环处理动态列
            forDynamicCol(maxnum,dynamicCol);
        }
    }
    if(flag == "hs"){
        var height = getTableHeight($("#hsTab"));
        renderTable("tableView","hsToolbar","hsId","full-"+height,dynamicCol,mainData,mergeData);
    }else{
        var height = getTableHeight($("#ychsTab"));
        renderTable("ycTableView","ychsToolbar","ychsId","full-"+height,dynamicCol,mainData,mergeData);
    }
}

function renderTable(tableId,toolbarId,resizeId,height,dynamicCol,mainData,mergeData){
    layui.use(['table','form'], function () {
        var table = layui.table;
        var form = layui.form;
        table.render({
            elem: '#'+tableId,
            height: height,
            id: resizeId,
            cols: dynamicCol,
            toolbar: '#' + toolbarId,
            defaultToolbar: [],
            data: mainData,
            done: function () {
                // 表格 checkbox 判断是否隐藏
                if(tableId == "tableView"){
                    if(hsCheckboxHide){
                        $("#hsTab").find('.cell-checkbox').addClass('bdc-hide');
                    }else{
                        $("#hsTab").find('.cell-checkbox').removeClass('bdc-hide');
                    }
                    //内容扩展高度调整
                    resizeTable($("#hsTab"));
                }else{
                    if(ychsCheckboxHide){
                        $("#ychsTab").find('.cell-checkbox').addClass('bdc-hide');
                    }else{
                        $("#ychsTab").find('.cell-checkbox').removeClass('bdc-hide');
                    }
                    //内容扩展高度调整
                    resizeTable($("#ychsTab"));
                }

                // 判断 TAB标签是否隐藏
                var hideLength = $(".layui-tab-title").find(".layui-hide").length;
                var allLen = $(".layui-tab-title").find("li").length;
                // 如果没隐藏的TAB标签个数超过1个 则 显示TAB标签栏
                if(allLen - 1 > hideLength){
                    $(".layui-tab-title").removeClass("layui-hide");
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

                if(tableId == "tableView"){
                    checkDefShowColumn("hs");
                }else{
                    checkDefShowColumn("ychs");
                }
                form.render("checkbox");
            }
        });
    })
}

function resizeTable(element){
    var mainHeight = element.find('.layui-table-main .layui-table td').height();
    if( mainHeight != 139){
        element.find('.layui-table-fixed .layui-table td').height(mainHeight+1);
        element.find('.layui-table-fixed .layui-table tr:first-child td').height(mainHeight);
    }
    //rowspan为1的，调整位置
    var $tableCol = element.find('.layui-table-header thead .layui-table-col-special');
    for(var i = 0; i < $tableCol.length; i++){
        if($($tableCol[i]).attr('rowspan') == 2){
            $($tableCol[i]).find('.layui-table-cell').css({'top':'2px','text-align': 'center'});
        }
    }
}


// 循环处理动态列
function forDynamicCol(length,dynamicCol){
    for (var i = 0; i < length; i++) {
        (function (n) {
            dynamicCol[1].push({
                field: '', title: n, width: 110, templet: function (d) {
                    var setList = d.dyFwList[1][n];
                    if (setList && setList.info) {
                        if (!setList.info.fjh.value) {
                            setList.info.fjh.value = '';
                        }
                        var div = "<div class='layui-form cell-checkbox'>" +
                            "<input type='checkbox' name='house' lay-skin='primary'" +
                            "data-dyh='"+ setList.info.dyh.value +"' " +
                            "data-info='"+ JSON.stringify(setList.info) +"' " +
                            "data-index='" + setList.info.fwHsIndex.value + "' lay-filter='checkFilter' >" +
                            "</div>";
                        var fh = getFjh(setList);
                        var desc = getDescHtml(setList);
                        var jyzt = getJyztHtml(setList);
                        var qlzt = getQlztHtml(setList);
                        return div + fh + desc + jyzt + qlzt;
                    } else {
                        return '';
                    }
                }
            });
        })(i);
    }
}

function getFjh(setList){
    var wlcs = setList.info.wlcs.value;
    var dyh = setList.info.dyh.value.substr(0,1);
    var fjh = setList.info.fjh.value;
    var fjhHtml = "<p class=\"bdc-fh\">房号：";
    if (setList.status.yg) { // 预告
        fjhHtml += '<span class="bdc-ywg-word" data-wlcs="'+wlcs+'" data-dyh="'+dyh+'">';
    } else if (setList.status.zx) { // 注销
        fjhHtml += '<span class="bdc-yzx-word" data-wlcs="'+wlcs+'" data-dyh="'+dyh+'">';
    } else if (setList.status.dj) { // 登记
        fjhHtml += '<span class="bdc-ydj-word" data-wlcs="'+wlcs+'" data-dyh="'+dyh+'">';
    } else {
        fjhHtml += '<span class="bdc-wdj-word" data-wlcs="'+wlcs+'" data-dyh="'+dyh+'">';
    }
    fjhHtml += fjh +"</span><i class=\"one-no-select one-icon\"></i></p>";
    return fjhHtml;
}

function getDescHtml(setList) {
    var desc = '';
    var info = setList.info;
    if (info) {
        for(var key in info){
            var thisInfo = eval("info."+key);
            if (thisInfo.desc || thisInfo.desc == '') {
                var descInfo = thisInfo.desc == '' ? '' : thisInfo.desc + ":";
                var descValue = thisInfo.value;
                if (!descValue) {
                    descValue = '';
                }
                desc += '<p class="bdc-describe layui-hide" mark="'+key+'" title="' + descValue + '">' + descInfo + descValue + '</p>';
            }
        }
    }
    return desc;
}


function getJyztHtml(setList) {
    var jyztnum = 0;
    var jyzt = '<p class="bdc-table-state bdc-jy-state layui-hide">';
    if (setList.status.ks) {
        jyztnum++;
        jyzt += '<span mark="KS" class="bdc-ks layui-hide"></span>';
    }
    if (setList.status.ys) {
        jyztnum++;
        jyzt += '<span mark="YS" class="bdc-ys layui-hide"></span>';
    }
    if (setList.status.cq) {
        jyztnum++;
        jyzt += '<span mark="CQ" class="bdc-cq layui-hide"></span>';
    }
    if (setList.status.wg) {
        jyztnum++;
        jyzt += '<span mark="WG" class="bdc-wg layui-hide"></span>';
    }
    if (setList.status.az) {
        jyztnum++;
        jyzt += '<span mark="AZ" class="bdc-az layui-hide"></span>';
    }
    if (setList.status.bl) {
        jyztnum++;
        jyzt += '<span mark="BL" class="bdc-bl layui-hide"></span>';
    }
    if (setList.status.fpt) {
        jyztnum++;
        jyzt += '<span mark="FPT" class="bdc-fpt layui-hide"></span>';
    }
    jyzt += '</p>';
    // if(jyztnum == 0){
    //     jyzt = '<p class="bdc-table-state bdc-jy-state">无交易状态</p>';
    // }
    return jyzt;
}

function getQlztHtml(setList) {
    var qlztnum = 0;
    var qlzt = '<p class="bdc-table-state bdc-ql-state layui-hide">';
    if (setList.status.zjgcdy) {
        qlztnum++;
        qlzt += '<span mark="ZJGCDY" class="bdc-zj layui-hide"></span>';
    }
    if (setList.status.ydya) {
        qlztnum++;
        qlzt += '<span mark="YDYA" class="bdc-ydy layui-hide"></span>';
    }
    if (setList.status.dya) {
        qlztnum++;
        qlzt += '<span mark="DYA" class="bdc-dya layui-hide"></span>';
    }
    if (setList.status.ycf) {
        qlztnum++;
        qlzt += '<span mark="YCF" class="bdc-ycf layui-hide"></span>';
    }
    if (setList.status.cf) {
        qlztnum++;
        qlzt += '<span mark="CF" class="bdc-cf layui-hide"></span>';
    }
    if (setList.status.sd) {
        qlztnum++;
        qlzt += '<span mark="SD" class="bdc-dj layui-hide"></span>';
    }
    if (setList.status.yy) {
        qlztnum++;
        qlzt += '<span mark="YY" class="bdc-yy layui-hide"></span>';
    }
    if (setList.status.dyi) {
        qlztnum++;
        qlzt += '<span mark="DYI" class="bdc-dy layui-hide"></span>';
    }
    qlzt += '</p>';
    // if(qlztnum == 0){
    //     qlzt = '<p class="bdc-table-state bdc-ql-state">无权利状态</p>';
    // }
    return qlzt;
}


// 点击自然幢加载逻辑幢列表
function showLjz(lszd, zrzh) {
    if ($("#" + lszd + "_" + zrzh).find("li").length == 0) {
        var url = "";
        if (globalData.resource.links) {
            for (var i = 0; i < globalData.resource.links.length; i++) {
                var link = globalData.resource.links[i];
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
    parent.layer.closeAll();
    if(ishsTab()){
        loadTableOnly();
    }else{
        loadYchsTable();
    }
}

function ishsTab(){
    return $(".layui-tab-title").find(".layui-this").attr("id") == "hsTabLi";
}

