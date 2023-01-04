/**
 * 任务台账资源的js
 */
var newWin = [];
/**
 * 判断是否可以批量转发
 */
var isPlzf = '';

/**
 * 二审流程初审的名称
 */
var erlcCsmc = "审核"
/**
 * 审批来源字典值
 */
var splyzdx = [];
/**
 * 页面版本
 */
var version = '';

/**
 * 项目列表是否显示删除按钮
 */
var xm_delete = '';
layui.use(['jquery', 'table', 'element', 'carousel', 'form', 'laytpl', 'laydate', 'layer','response','workflow', 'moduleAuthority'], function () {
    var $ = layui.jquery,
        table = layui.table,
        element = layui.element,
        laytpl = layui.laytpl,
        layer = layui.layer,
        laydate = layui.laydate,
        response = layui.response,
        workflow = layui.workflow,
        carousel = layui.carousel,
        moduleAuthority = layui.moduleAuthority,
        formSelects = layui.formSelects;
    version=$('#version').val();
    $(function () {
        var $paramArr = getIpHz();
        var viewType = $paramArr['viewType'];
        var authorityCode = '';

        //4. 轮播图
        //4.0 渲染常用收藏内容
        var commonConnection = false;
        var connectionList = false;
        //监听收藏tab切
        var taskTabClick = 0;
        var taskTabListClick = 0;
        var $buildTask = $('.bdc-build-task');
        //4.2 轮播图内鼠标覆盖事件
        var $carouselTask = $('#buildTask');
        //监听收藏任务tab切换
        element.on('tab(task)', function (data) {
            if (taskTabListClick == 0 && data.index == 0) {
                taskTabListClick++;
                renderLbList();
            }
            if ($('.bdc-task-content').hasClass('bdc-hide')) {
                $('.bdc-operate-show>.layui-icon').toggleClass('bdc-hide');
                $('.bdc-task-content').toggleClass('bdc-hide');
            }
            changeTableHeight();
        });

        //4.1 轮播图 内容、一屏展示几个，可配置
        //默认一屏展示5个，大数组里面嵌套数组，展示几个，嵌套数组中存储几条数据

        function renderLbList() {
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/workflow/zrzy/process-instances/task/list",
                data: {},
                success: function (data) {
                    if (data && data.content) {
                        var carouselData = [];
                        for (var i = 0; i < Math.ceil(data.content.length / carouselCount); i++) {
                            carouselData.push([]);
                        }
                        data.content.forEach(function (v, i) {
                            var getIndex = i + 1;
                            carouselData[Math.ceil(getIndex / carouselCount) - 1].push(v);
                        });
                        var getCarouselTpl = carouselTpl.innerHTML
                            , getCarouselView = document.getElementById('carouselView');
                        laytpl(getCarouselTpl).render(carouselData, function (html) {
                            getCarouselView.innerHTML = html;
                        });
                        carousel.render({
                            elem: '#buildTask',
                            width: '100%',
                            height: carouselHeight,
                            arrow: 'always',
                            indicator: 'outside',
                            autoplay: false
                        });
                    }
                }, error: function (e) {
                    response.fail(e,'');
                }
            });
        }

        //4.3 轮播图内单击事件
        $buildTask.on('click', '.bdc-carousel-son', function (event) {
            event.stopPropagation();
            // console.log($(this).width());
            $('.bdc-carousel-son').removeClass('bdc-carousel-son-this');
            $(this).addClass('bdc-carousel-son-this');
            $('.bdc-carousel-details').addClass('bdc-hide');
            var lbIndex = Math.ceil(($(this).index()+1)/carouselCount);
            var lineIndex = Math.ceil(($(this).index())%carouselline);
            $(this).find('.bdc-carousel-details').css('left', -lbIndex * (lineIndex * $(this).width() + lineIndex * 10) + 'px').removeClass('bdc-hide');
        });
        //4.5 轮播图详情页
        //4.5.1 单击 × 按钮，关闭详情
        $carouselTask.on('click', '.bdc-carousel-close', function (event) {
            event.stopPropagation();
            $(this).parents('.bdc-carousel-son').removeClass('bdc-carousel-son-this');
            $(this).parent().addClass('bdc-hide');
        });

        //点击页面空白处 隐藏新建任务详情弹出层
        $('body').on('click', function () {
            $('.bdc-carousel-details').addClass('bdc-hide');
            $('.bdc-carousel-son').removeClass('bdc-carousel-son-this');
        });
        //4.5.4 单击 收起 或 展开
        $carouselTask.on('click', '.bdc-details-type-tool>.bdc-retract', function (event) {
            event.stopPropagation();
            $(this).addClass('bdc-hide').siblings().removeClass('bdc-hide').parents('.bdc-details-type').find('.bdc-details-type-content').height('30px');
        });
        $buildTask.on('click', '.bdc-details-type-tool>.bdc-open', function (event) {
            event.stopPropagation();
            $(this).addClass('bdc-hide').siblings().removeClass('bdc-hide').parents('.bdc-details-type').find('.bdc-details-type-content').height('auto');
        });
        //4.5.5 单击二级小类
        $buildTask.on('click', '.bdc-details-type-content>a', function (event) {
            event.stopPropagation();
            var useData = {
                processDefKey: $(this).find('.layui-icon').data('code'),
                name: $(this).find('.layui-icon').data('name')
            };
            startUpProcess(useData);
        });
        /**
         * 创建流程
         */
        function startUpProcess(useData){
            workflow.startUpProcess(useData);
        }


        //按顺序加载tab
        var tabList = ["rlContent","ybContent","dbContent","xmContent"];
        tabList.forEach(function(v){
            var getTpl;
            switch (v){
                case "xmContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li>项目列表</li>');
                    getTpl = xmContent.innerHTML;
                    break;
                case "ybContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li>已办任务</li>');
                    getTpl = ybContent.innerHTML;
                    break;
                case "dbContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li id="todoTab">待办任务<span class="bdc-list-num-tips"></span></li>');
                    getTpl = dbContent.innerHTML;
                    break;
                case "rlContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li class="bdc-word-li">认领列表</li>');
                    getTpl = rlContent.innerHTML;
                    break;

            }
            laytpl(getTpl).render([], function(html){
                $('.bdc-list-tab .layui-tab-content').append(html);
            });
        });
        $('.bdc-list-tab .layui-tab-title li:first-child').addClass('layui-this');
        $('.bdc-list-tab .layui-tab-content .layui-tab-item:first-child').addClass('layui-show');

        //6. 渲染表格
        // 6.1 待办表格
        var waitUrl = getContextPath() + "/rest/v1.0/task/zrzy/todo";
        var waitTableId = '#waitTable';
        var waitCurrentId = 'dbTable';
        var waitToolbar = '#toolbarDemo';
        renderWaitTable(waitUrl, waitTableId, waitCurrentId, waitToolbar);

        // 6.2 已办表格
        var doneUrl = getContextPath() + "/rest/v1.0/task/zrzy/complete";
        var doneTableId = '#doneTable';
        var doneCurrentId = 'doneTableId';
        var doneToolbar = '#toolbarDoneDemo';
        renderDoneTable(doneTableId, doneCurrentId, doneToolbar);
        // 6.3 项目列表表格
        var listUrl = getContextPath() + "/rest/v1.0/task/zrzy/all";
        var listTableId = '#listTable';
        var listCurrentId = 'listTableId';
        var allToolbar = '#toolbarAllDemo';
        renderListTable(listTableId, listCurrentId, allToolbar);
        // 6.4 认领列表表格
        var rlUrl = getContextPath() + "/rest/v1.0/task/zrzy/claim/list";
        var rlTableId = '#rlTable';
        var rlCurrentId = 'rlTableId';
        var rlTableToolbar = '#rlTableToolbar';
        renderRlTable(rlTableId, rlCurrentId, rlTableToolbar);

        //重置第一个
        switch (tabList[0]) {
            case "xmContent":
                refreshTab(listUrl, listCurrentId, authorityCode);
                break;
            case "ybContent":
                refreshTab(doneUrl, doneCurrentId, authorityCode);
                break;
            case "rlContent":
                refreshTab(rlUrl, rlCurrentId, authorityCode);
                break;
            case "grContent":
                refreshTab(grListUrl, grListCurrentId, authorityCode);
                break;
        }

        var doneIndex = 0,
            listIndex = 0,
            rlindex = 0,
            waitIndex = 0;

        //监听第一次单击任务栏tab，重构表格尺寸
        element.on('tab(listFilter)', function (data) {
            if (data.index == 0) {
                switch (tabList[0]){
                    case "dbContent":
                        refreshTab(waitUrl, waitCurrentId, authorityCode);
                        break;
                    case "xmContent":
                        table.resize(listCurrentId);
                        refreshTab(listUrl, listCurrentId, authorityCode);
                        break;
                    case "ybContent":
                        table.resize(doneCurrentId);
                        refreshTab(doneUrl, doneCurrentId, authorityCode);
                        break;
                    case "rlContent":
                        table.resize(rlCurrentId);
                        refreshTab(rlUrl, rlCurrentId, authorityCode);
                        break;
                }
            }else {
                switch (tabList[data.index]){
                    case "dbContent":
                        if (waitIndex == 0) {
                            waitIndex++;
                            table.resize(waitCurrentId);
                        }
                        refreshTab(waitUrl, waitCurrentId, authorityCode);
                        break;
                    case "xmContent":
                        if (listIndex == 0) {
                            listIndex++;
                            table.resize(listCurrentId);
                        }
                        refreshTab(listUrl, listCurrentId, authorityCode);
                        break;
                    case "ybContent":
                        if (doneIndex == 0) {
                            doneIndex++;
                            table.resize(doneCurrentId);
                        }
                        refreshTab(doneUrl, doneCurrentId, authorityCode);
                        break;
                    case "rlContent":
                        if (rlindex == 0) {
                            rlindex++;
                            table.resize(rlCurrentId);
                        }
                        refreshTab(rlUrl, rlCurrentId, authorityCode);
                        break;
                }
            }
        });

        // 首页选择已办跳转模拟点击操作
        if (viewType === "complete") {
            $(".bdc-list-tab li:nth-child(2)").click();
        }

        //6.4 待办任务头工具栏事件
        table.on('toolbar(waitTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            if (obj.event != "LAYTABLE_COLS") {
                var selectedNum = checkStatus.data.length;
                var deleteEvent = (obj.event == "delete-process" || obj.event == "hang-up-process" || obj.event == "active-process"
                    || obj.event == "cancel-process" || obj.event == "back-process");

                if (obj.event == 'forward-process' && selectedNum > 1) {
                    if (isPlzf !== 0 || isPlzf !== 1) {
                        // 判断是否配置了自动转发
                        $.ajax({
                            url: getContextPath() + "/rest/v1.0/workflow/process-instances/isPlZf",
                            dataType: "json",
                            async: false,
                            success: function (data) {
                                isPlzf = data;
                            }
                        });
                    }
                    if (isPlzf === 1) {
                        obj.event = "forward-pl-process";
                    }
                    if (selectedNum != 1 && obj.event != "forward-pl-process"){
                        layer.msg('请选择一条数据进行转发！');
                        return false;
                    }
                } else {
                    if ((selectedNum != 1 && !deleteEvent) || (selectedNum == 0 && deleteEvent)) {
                        layer.msg('请选择一条数据进行操作！');
                        return false;
                    }
                }
            }

            var checkData = checkStatus.data[0];
            switch (obj.event) {
                case 'forward-process':
                    addModel();
                    setTimeout(function () {
                        workflow.forwardauto(checkStatus, waitUrl, waitTableId, waitCurrentId, true, checkData.processInstanceId);
                    }, 50);

                    break;
                case 'back-process':
                    if (selectedNum == 1) {
                        workflow.backProcess(checkData, waitUrl, waitTableId, waitCurrentId, true);
                    } else {
                        workflow.backPlProcess(checkStatus, waitUrl, waitTableId, waitCurrentId, true);
                    }
                    break;
                case 'active-process':
                    // 激活流程
                    workflow.activeProcess(checkStatus, waitUrl, waitTableId, waitCurrentId);
                    break;
                case 'delete-process':
                    // 删除当前任务
                    workflow.deleteProcess(checkStatus, waitUrl, waitTableId, waitCurrentId, true);
                    break;
                case 'hang-up-process':
                    workflow.hangUpProcess(checkStatus, waitUrl, waitTableId, waitCurrentId);
                    break;
                case 'bind-forward':
                    //绑定转发事件
                    if (selectedNum !== 1) {
                        layer.msg('请选择一条数据进行转发！');
                        return false;
                    }
                    workflow.forwardPlProcess(checkStatus, waitUrl, waitTableId, waitCurrentId, true);
                    break;
            }
        });

        //认领工具栏
        table.on('tool(rlTableFilter)', function (obj) {
            switch (obj.event) {
                case 'openpage':
                    // var hasSameShr = checkHasSameShr(checkStatus);
                    // if (hasSameShr) {
                    //     layer.msg('存在同一审核人，不可认领!');
                    //     return false;
                    // }
                    if(isConfirmRl){
                        rlLayerIndex = layer.open({
                            type: 1,
                            title: '认领',
                            skin: 'bdc-small-tips bdc-zf-tips',
                            area: ['320px'],
                            content: '您确定要认领吗？',
                            btn: ['确定','取消'],
                            btnAlign: 'c',
                            yes: function(){
                                layer.close(rlLayerIndex);
                                //确定操作
                                workflow.claimProcess(obj, rlUrl, rlTableId, rlCurrentId, false);
                            }
                        });
                    } else {
                        workflow.claimProcess(obj, rlUrl, rlTableId, rlCurrentId, false);
                    }
                    break;
            }
        });

        //项目列表工具栏
        table.on('toolbar(listTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var selectedNum = checkStatus.data.length;
            if (selectedNum == 0) {
                layer.msg('请选择一条数据进行操作！');
                return false;
            }
            switch (obj.event) {
                case 'delete-process':
                    // 删除当前任务
                    workflow.deleteProcess(checkStatus, listUrl, listTableId, listCurrentId, true);
                    break;
            }
        });

        //已办 工具类
        table.on('toolbar(doneTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            if (obj.event != "LAYTABLE_COLS" && obj.event != "yjd") {
                if (checkStatus.data.length == 0) {
                    layer.msg('请选择一条数据进行操作！');
                    return false;
                }
            }
            switch (obj.event) {
                case 'fetch-process':
                    workflow.fetchPlProcess(checkStatus, doneUrl, doneTableId, doneCurrentId, true);
                    break;
            }
        });

        //监听待办 行双击
        table.on('rowDouble(waitTableFilter)', function (obj) {
            if (!workflow.showHangReson(obj.data)) {
                return false;
            }
            workflow.lockProcess(obj.data);
            var waitCzTimer = setInterval(function () {
                clearInterval(waitCzTimer);
                var index = window.open("./zrzy-new-page.html?name=" + obj.data.taskId + "&type=todo", "待办任务");
                newWin.push(index);
            }, 50);

        });

        table.on('tool(waitTableFilter)', function (obj) {
            if (obj.event === 'openpage') {
                if (!workflow.showHangReson(obj.data)) {
                    return false;
                }
                //锁定任务
                workflow.lockProcess(obj.data);

                var waitCzTimer = setInterval(function () {
                    clearInterval(waitCzTimer);
                    var index = window.open("./zrzy-new-page.html?name=" + obj.data.taskId + "&type=todo", "待办任务");
                    newWin.push(index);
                },50);
            }
        });

        // 监听已办 行双击
        table.on('rowDouble(doneTableFilter)', function (obj) {
            if (!workflow.showHangReson(obj.data)) {
                return false;
            }
            var ybCzTimer = setInterval(function() {
                clearInterval(ybCzTimer);
                var index = window.open("./zrzy-new-page.html?name=" + obj.data.taskId + "&type=done", obj.data.processDefName);
                newWin.push(index);
            },50);
        });

        table.on('tool(doneTableFilter)', function (obj) {
            if (obj.event === 'openpage') {
                if (!workflow.showHangReson(obj.data)) {
                    return false;
                }
                var ybCzTimer = setInterval(function() {
                    clearInterval(ybCzTimer);
                    var index = window.open("./zrzy-new-page.html?name=" + obj.data.taskId + "&type=done", obj.data.slbh);
                    newWin.push(index);
                },50);
            }
        });
        // 监听项目列表 行双击
        table.on('rowDouble(listTableFilter)', function (obj) {
            if (!workflow.showHangReson(obj.data)) {
                return false;
            }
            if (lcPageEdition === 'hf') {
                var index = $(obj.tr).attr("data-index");
                obj.data = listCacheData[index];
            }
            var xmCzTimer = setInterval(function () {
                clearInterval(xmCzTimer);
                var index = window.open(getContextPath() + "/view/zrzy-new-page.html?name=" + obj.data.processInstanceId + "&type=list", obj.data.slbh);
                newWin.push(index);
            }, 50);
        });

        table.on('tool(listTableFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'openpage') {
                if (lcPageEdition === 'hf') {
                    var index = $(obj.tr).attr("data-index");
                    obj.data = listCacheData[index];
                }
                if (!workflow.showHangReson(obj.data)) {
                    return false;
                }
                var xmCzTimer = setInterval(function () {
                    clearInterval(xmCzTimer);
                    var index = window.open("./zrzy-new-page.html?name=" + obj.data.processInstanceId + "&type=list", obj.data.slbh);
                    newWin.push(index);
                }, 50);
            }
        });


        //点击高级查询
        $('#seniorSearch').on('click', function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            if (dbSearch == "center") {
                $(this).parent().toggleClass('bdc-button-box-four');
            }
            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }

            if(version=='nantong'){
                // 获取待办部门下拉框数据
                getTreeData('dept_treedb','selectedDeptNameShow','selectedDeptName',"/rest/v1.0/task/dept/allnew");
            }

            /**
             * 初始化组织机构下拉控件
             */
            $('.org_select_showdb').click(function () {
                if($('.org_select_showdb').text() == '选择'){
                    $('.org_select_tree').css('display','block');
                    $('.org_select_showdb').text('隐藏')
                }else{
                    $('.org_select_showdb').text('选择');
                    $('.org_select_tree').css('display','none');
                }
            });

            //下拉面板宽高位置
            (function(){
                var width = $('#selectedDeptNameShow').width + 40;
                $('.org_select_tree').css({'width': width, 'height':'200px', 'left':-66, 'top':35});
            })();
            changeTableHeight();
        });
        $('#seniorybSearch').on('click', function () {
            $('.pf-senior-yb-show').toggleClass('bdc-hide');
            if (ybSearch == "center") {
                $(this).parent().toggleClass('bdc-button-box-four');
            }
            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }

            if(version=='nantong') {
                // 获取已办部门下拉框数据
                getTreeData('dept_treeyb', 'selectedDoneDeptNameShow', 'selectedDoneDeptName', "/rest/v1.0/task/dept/allnew");
            }
            /**
             * 初始化组织机构下拉控件
             */
            $('.org_select_showyb').click(function () {
                if($('.org_select_showyb').text() == '选择'){
                    $('.org_select_tree').css('display','block');
                    $('.org_select_showyb').text('隐藏')
                }else{
                    $('.org_select_showyb').text('选择');
                    $('.org_select_tree').css('display','none');
                }
            });

            //下拉面板宽高位置
            (function(){
                var width = $('#selectedDoneDeptNameShow').width + 40;
                $('.org_select_tree').css({'width': width, 'height':'200px', 'left':-66, 'top':35});
            })();

            changeTableHeight();
        });
        $('#seniorxmSearch').on('click', function () {
            $('.pf-senior-xm-show').toggleClass('bdc-hide');
            if (xmSearch == "center") {
                $(this).parent().toggleClass('bdc-button-box-four');
            }
            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }

            if(version=='nantong') {
                // 获取待办部门下拉框数据
                getTreeData('dept_treexm', 'selectedXmDeptNameShow', 'selectedXmDeptName', "/rest/v1.0/task/dept/allnew");
            }
            /**
             * 初始化组织机构下拉控件
             */
            $('.org_select_showxm').click(function () {
                if($('.org_select_showxm').text() == '选择'){
                    $('.org_select_tree').css('display','block');
                    $('.org_select_showxm').text('隐藏')
                }else{
                    $('.org_select_showxm').text('选择');
                    $('.org_select_tree').css('display','none');
                }
            });

            //下拉面板宽高位置
            (function(){
                var width = $('#selectedXmDeptNameShow').width + 40;
                $('.org_select_tree').css({'width': width, 'height':'200px', 'left':-66, 'top':35});
            })();
            changeTableHeight();

        });
        $('#seniorrlSearch').on('click', function () {
            $('.pf-senior-rl-show').toggleClass('bdc-hide');
            if (rlSearch == "center") {
                $(this).parent().toggleClass('bdc-button-box-four');
            }

            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }

            if(version=='nantong') {
                // 获取待办部门下拉框数据
                getTreeData('dept_treerl', 'selectedRlDeptNameShow', 'selectedRlDeptName', "/rest/v1.0/task/dept/allnew");
            }
            /**
             * 初始化组织机构下拉控件
             */
            $('.org_select_showrl').click(function () {
                if($('.org_select_showrl').text() == '选择'){
                    $('.org_select_tree').css('display','block');
                    $('.org_select_showrl').text('隐藏')
                }else{
                    $('.org_select_showrl').text('选择');
                    $('.org_select_tree').css('display','none');
                }
            });

            //下拉面板宽高位置
            (function(){
                var width = $('#selectedRlDeptNameShow').width + 40;
                $('.org_select_tree').css({'width': width, 'height':'200px', 'left':-66, 'top':35});
            })();
            changeTableHeight();
        });


        //查询功能
        var searchObj = {
            "dbSearch": "dbTable",
            "ybSearch": "doneTableId",
            "xmSearch": "listTableId",
            "rlSearch": "rlTableId",
        };
        $('.searchBtn').on('click', function () {
            var id = this.id;
            // 获取查询内容
            var obj = getSearchParam(id);

            // 重新请求
            table.reload(searchObj[id], {
                where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
            moduleAuthority.load({
                authorityCode: authorityCode
            });
        });
        // 获取查询参数
        function getSearchParam(id){
            var obj = {};
            $("." + id).each(function (i) {
                if (this.tagName === 'SELECT') {
                    // 根据selectId获取下拉框选取的值
                    if (isNullOrEmpty(this.attributes.getNamedItem("xm-select"))) {
                        // 未获取的 xm-select 说明不是多选
                        var selectVal = $(this).find("option:selected").val();
                        let name = $(this).attr('name');
                        if (!isNullOrEmpty(selectVal)) {
                            obj[name] = selectVal;
                        }
                    } else {
                        let xmSelectId = this.attributes.getNamedItem("xm-select").value;
                        let selectValArr = formSelects.value(xmSelectId, 'valueStr');
                        let valueArr = [];
                        for (let i = 0; i < selectValArr.length; ++i) {
                            valueArr.push(selectValArr[i].value);
                        }
                        let selectVal = valueArr.join(",");
                        let name = $(this).attr('_name');

                        if (!isNullOrEmpty(selectVal)) {
                            obj[name] = selectVal;
                        }
                    }
                }else{
                    let value = $(this).val();
                    let name = $(this).attr('name');
                    if ($(this).hasClass("test-item")) {
                        //日期型不需要去除空格处理
                        obj[name] = value;

                    } else {
                        obj[name] = deleteWhitespace(value);
                    }

                }
            });
            return obj;
        }
        //表格行颜色
        $('.bdc-table-box').on('mouseenter', 'td', function () {
            if ($(this).text() && $(this).attr("data-field") == "zl") {
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>')
            }
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                }, 20);
            });
        });

        //查询条件格式化
        //下拉框，登记原因，节点名称
        var $jdmcDom = $('.bdc-jdmc');
        queryTaskNameList($jdmcDom,'',true);
        var $djyyDom = $('.bdc-djyy');
        //queryDjyyList('',true,$djyyDom);
        // 初始化日期控件
        loadDate();
        function loadDate() {
            lay('.test-item').each(function () {
                var kssjdy = laydate.render({
                    elem: '#kssjdy',
                    type: 'datetime',
                    trigger: 'click',
                    done: function (value, date) {
                        //选择的结束时间大
                        if ($('#kssjxy').val() != '' && !completeDate($('#kssjxy').val(), value)) {
                            $('#kssjxy').val('');
                            $('.laydate-disabled.layui-this').removeClass('layui-this');
                        }
                        kssjxy.config.min = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date,
                            hours: date.hours,
                            minutes: date.minutes,
                            seconds: date.seconds
                        }
                    }
                });
                var kssjxy = laydate.render({
                    elem: '#kssjxy',
                    type: 'datetime',
                    trigger: 'click'
                });

                var ybkssjdy = laydate.render({
                    elem: '#ybkssjdy',
                    type: 'datetime',
                    trigger: 'click',
                    done: function (value, date) {
                        //选择的结束时间大
                        if ($('#ybkssjxy').val() != '' && !completeDate($('#ybkssjxy').val(), value)) {
                            $('#ybkssjxy').val('');
                            $('.laydate-disabled.layui-this').removeClass('layui-this');
                        }
                        ybkssjxy.config.min = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date,
                            hours: date.hours,
                            minutes: date.minutes,
                            seconds: date.seconds
                        }
                    }
                });
                var ybkssjxy = laydate.render({
                    elem: '#ybkssjxy',
                    type: 'datetime',
                    trigger: 'click'
                });

                var xmkssjdy = laydate.render({
                    elem: '#xmkssjdy',
                    type: 'datetime',
                    trigger: 'click',
                    done: function (value, date) {
                        //选择的结束时间大
                        if ($('#xmkssjxy').val() != '' && !completeDate($('#xmkssjxy').val(), value)) {
                            $('#xmkssjxy').val('');
                            $('.laydate-disabled.layui-this').removeClass('layui-this');
                        }
                        xmkssjxy.config.min = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date,
                            hours: date.hours,
                            minutes: date.minutes,
                            seconds: date.seconds
                        }
                    }
                });
                var xmkssjxy = laydate.render({
                    elem: '#xmkssjxy',
                    type: 'datetime',
                    trigger: 'click'
                });

                var rlkssjdy = laydate.render({
                    elem: '#rlkssjdy',
                    type: 'datetime',
                    trigger: 'click',
                    done: function (value, date) {
                        //选择的结束时间大
                        if ($('#rlkssjxy').val() != '' && !completeDate($('#rlkssjxy').val(), value)) {
                            $('#rlkssjxy').val('');
                            $('.laydate-disabled.layui-this').removeClass('layui-this');
                        }
                        rlkssjxy.config.min = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date,
                            hours: date.hours,
                            minutes: date.minutes,
                            seconds: date.seconds
                        }
                    }
                });
                var rlkssjxy = laydate.render({
                    elem: '#rlkssjxy',
                    type: 'datetime',
                    trigger: 'click'
                });
            });
        }
        function completeDate(date1, date2) {
            var oDate1 = new Date(date1);
            var oDate2 = new Date(date2);
            if (oDate1.getTime() > oDate2.getTime()) {
                return true;
            } else {
                return false;
            }
        }
        form.render("select");

        //按钮权限
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/module/authority",
            data: {
                moduleCode: '',
            },
            success: function (data) {
                console.log(data);
                authorityCode = data;
                moduleAuthority.load({
                    authorityCode: data
                });
            }, error: function (e) {
                response.fail(e, '');
            }
        });
    });

    //刷新表格
    function refreshTab(url, id, authorityCode) {
        table.reload(id, {
            url: url,
            done: function () {
                changeTableHeight();
                var reverseList = ['zl'];
                reverseTableCell(reverseList);
            }
        });
        moduleAuthority.load({
            authorityCode: authorityCode
        });
    }
});

function queryXmDeleteButton() {
    if (xm_delete == '') {
        getReturnData("/rest/v1.0/config/xm/delete", {}, "GET", function (data) {
            xm_delete = data;
            return data;
        }, false);
    } else {
        return xm_delete;
    }
}

//表格高度自适应
function changeTableHeight() {
    if ($('.bdc-list-tab .layui-tab-content .layui-show .layui-table-main>.layui-table').height() == 0) {
        $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
        //$('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body').height('56px');
        //$('.bdc-list-tab .layui-tab-content .layui-show .layui-table-fixed .layui-table-body').height('56px');
    } else {
        //$('.bdc-list-tab .layui-tab-content .layui-show .layui-table-main.layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height());
        $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height());
        $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-fixed .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height() - 17);
    }
}

/**
 * 验证是否初审和审核人是否一致，一致为false,不一致为true;
 */
function checkHasSameShr(checkStatus) {
    var flag = false;
    var gzlslidids = [];
    checkStatus.data.forEach(function (v) {
        var taskName = v.taskName;
        // 只取初审，非初审的不做为判断的条件
        if (gzlslidids.indexOf(v.processInstanceId) == -1 && taskName == erlcCsmc) {
            gzlslidids.push(v.processInstanceId);
        }
    });
    if(gzlslidids.length > 0){
        $.ajax({
            type: "post",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/task/claim/checkHasSameShr",
            async: false,
            traditional: true,
            data: {'gzlslids': gzlslidids},
            success: function (data) {
                if(data == "true" || data == true){
                    flag = true;
                }
            }, error: function (e) {
                // 系统内部异常了，则认定为 不一致，让认领操作继续下去，不影响使用
                flag = false;
            }
        });
    }
    return flag;
}



