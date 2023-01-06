/**
 * Created by Ypp on 2020/1/16.
 * 任务办理各版本通用js
 */
var $, response, laytpl,
    processInstanceType = '',
    sourceType = '',
    print = false;
// 存储已读附记id
var fjIdList = [];
// 受理编号
var slbh = '';
var timer2;
layui.use(['jquery', 'element', 'form', 'laytpl', 'layer', 'table','workflow', 'moduleAuthority','response'], function () {
    $ = layui.jquery;
    response = layui.response;
    laytpl = layui.laytpl;
    var layer = layui.layer,
        element = layui.element,
        form = layui.form,
        taskId = '',
        formKey = '',
        processDefName = '',
        processKey = 'none',
        processInstanceId = '',
        claimStatus = '',
        isForceEnd = '',
        workflow = layui.workflow,
        moduleAuthority = layui.moduleAuthority,
        checkData;

    // 清空存储的附件id
    sessionStorage.removeItem("fjIdList");
    $(function(){
        //初始化判断离线定时器
        initTimer();
        //获取地址栏参数
        var $paramArr = getIpHz();
        if ($paramArr['taskId']) {
            // 受理创建成功传递 taskId
            taskId = $paramArr['taskId'];
            processInstanceType = 'todo';
        } else if ($paramArr['processinsid']) {
            // 历史关系查看传递 processinsid
            processInstanceId = $paramArr['processinsid'];
            processInstanceType = 'list';
            sourceType = $paramArr['type'];
            queryInfo(processInstanceId);
        } else {
            //获取首页传递过来的数据
            var param = $paramArr['name'];
            processInstanceType = $paramArr['type'];
            if (processInstanceType == 'list') {
                processInstanceId = param;
                //项目列表，param 参数是 gzlslid
                queryInfo(param);
            } else {
                taskId = param;
            }
        }
        queryCheckProcessDefKey();

        /**
         * 根据 processInstanceId 获取信息
         * @param processId 工作流实例 id
         */
        function queryInfo(processId) {
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/task/process/last",
                data: {
                    processInstanceId: processId
                },
                async: false,
                success: function (data) {
                    taskId = data.taskId;
                    formKey = data.processInstanceData.formKey;
                    queryForm(processInstanceType);
                    // if (lcPageEdition == "hf") {
                    //     var user = queryCurrentUser();
                    //     if (user != undefined && user.username == data.startUserId) {
                    //         print = true;
                    //     }
                    // }
                    // 用户配置了补打角色则获得打印权限
                    queryPrintRoles();
                }, error: function (e) {
                    response.fail(e, '');
                }
            });
        }

        //关闭处理
        window.onunload = function () {
            top.opener.renderCurrentTable();
        };

        if (lcPageEdition == 'hf') {
            //待办增加websocket
            if (processInstanceType == 'todo') {
                getReturnData("/rest/v1.0/user/info", {}, "GET", function (data) {
                    //消息中心推送
                    webSocket(data.username);
                });
            }
        }

        //获取配置信息
        $.ajax({
            url: getContextPath() + '/rest/v1.0/config/newpage/pzxx',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                xsyqlfj = data.xsyqlfj;
                if (xsyqlfj) {
                    //根据配置显示原权利附件按钮
                    $(".bdc-yqlfj-default-content").removeAttr("style");
                    // $(".bdc-yqlfj-default-content").hide();
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });

        //异步处理 获取操作参数
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/task/info/" + taskId,
            data: {},
            async: false,
            success: function (data) {
                checkData = data;
                if (data.taskName == null) {
                    data.taskName = '';
                }
                taskName = data.taskName;
                if (data.taskDueDate == null) {
                    data.newTaskDueDate = '';
                } else {
                    data.newTaskDueDate = data.taskDueDate;
                }
                if (data.startTime == null) {
                    data.newStartTime = '';
                } else {
                    data.newStartTime = data.startTime;
                }
                if (data.taskAssigin == null) {
                    data.taskAssigin = '';
                }
                claimStatus = data.claimStatus;
                claimStatusName = data.claimStatusName;
                if (!isNullOrEmpty(data.formKey) && processInstanceType != "list") {
                    formKey = data.formKey;
                    queryForm(processInstanceType);
                }
                if (!isNullOrEmpty(data.processInstanceId)) {
                    processInstanceId = data.processInstanceId;
                }

                if (lcPageEdition == 'hf' || lcPageEdition == 'bengbu') {
                    isAbandon(data.processInstanceId);
                    // 判断节点是否显示
                    if (showAandonTaskNames.indexOf(data.taskName) > -1 && iSabandon == "show") {
                        $("#bdc-revoke-btn").css('display', 'inline-block').addClass('bdc-show-cs-js');
                    } else {
                        iSabandon = "hide";
                        $("#bdc-revoke-btn").css('display', 'none').removeClass('bdc-show-cs-js');
                    }
                }
                if (lcPageEdition == "changzhou") {
                    isAbandon(data.processInstanceId);
                    if (iSabandon == "show") {
                        $("#bdc-revoke-btn").css('display', 'inline-block').addClass('bdc-show-cs-js');
                    } else {
                        iSabandon = "hide";
                        $("#bdc-revoke-btn").css('display', 'none').removeClass('bdc-show-cs-js');
                    }
                }
                if (lcPageEdition == 'standard') {
                    isAbandon(data.processInstanceId);
                    if (showAandonTaskNames.indexOf(data.taskName) > -1 && iSabandon == "show") {
                        $("#bdc-revoke-btn").css('display', 'inline-block');
                    } else {
                        iSabandon = "hide";
                        $("#bdc-revoke-btn").css('display', 'none');
                    }
                }
                // 海门版本退回件显示退回意见
                if (lcPageEdition == 'haimen' || lcPageEdition == "changzhou") {
                    showThyj(processInstanceId, taskId);
                }
                // 淮安和标准版展示例外审核和本节点退回意见
                if (lcPageEdition == 'huaian' || lcPageEdition == 'standard') {
                    showLwsh(processInstanceId);
                    showBjdThyj(processInstanceId, taskId);
                }
                // 南通版本提示 接入互联网+
                if (lcPageEdition == 'nt' ||lcPageEdition == 'haimen') {
                    showSfjrhlw(taskId);
                }
                // 淮安展示退回互联网原因
                if (lcPageEdition == 'huaian' || lcPageEdition == 'standard') {
                    showThhlwyy(processInstanceId);
                }
                processKey = data.processKey;
                checkBackBtn();
                data.opinion = workflow.queryOpinion(processInstanceId, taskId, 'back_opinion');
                processDefName = data.processDefName;
                $('.bdc_processDefName').text(processDefName);

                var getMenuTpl = menuTipsTpl.innerHTML
                    , menuView = document.getElementById('bdcMenuTipsView');
                laytpl(getMenuTpl).render(data, function (html) {
                    menuView.innerHTML = html;
                });
            }, error: function (e) {
                response.fail(e,'');
            }
        });

        //按钮权限
        authorityNewPage('', moduleAuthority);

        function authorityNewPage(moduleCode, moduleAuthority) {
            // 用户角色 按钮控制
            var roleAuthority = $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/module/authority",
                async:false,
                data: {
                    moduleCode: moduleCode,
                },
                success: function (data) {
                    console.log(data);
                    // 根据 实例类型 控制按钮
                    if (processInstanceType == 'done') {
                        showDoneButton(data);
                    } else if (processInstanceType == 'todo' || processInstanceType == 'rl') {
                        showTodoButton(data);
                        showRlButton();
                    } else if (iSabandon == 'show') {
                        resizeOprateBtn(0, 80, 40, 0);
                    }
                }, error: function (e) {
                    response.fail(e,'');
                }
            });
            // 表单中心按钮控制
            var formCenterAuthority = $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/task/form-center/portalBtn/authority",
                data: {
                    taskId: taskId,
                    resourceName: "portal-new-page",
                },
                async:false,
                success: function (data) {
                    console.info(data);
                    if(isNotBlank(data) && data.length > 0){
                        // 对表单中心的按钮进行隐藏
                        $.each(data, function(index, value){
                            if(value.formElementType == "1"){
                                $("#"+value.formHtmlId).hide();
                            }else if(value.formElementType == "4"){
                                $("#"+value.formHtmlId).show();
                            }
                        });
                    }
                }, error: function (e) {
                    response.fail(e,'');
                }
            });

            $.when(roleAuthority, formCenterAuthority).done(function(){
                // 动态刷新按钮
                resizeBtnDynamic();
            });
        }

        function showDoneButton(authorityData) {
            if (authorityData.indexOf('fetch') > 0) {
                resizeOprateBtn(60, 60 + 80, 60 + 40, 60);
                $("#bdc-take-btn").css('display', 'inline-block');
                $("#bdc-back-btn").css('display', 'none');
                $("#bdc-delete-btn").css('display', 'none');
                $("#bdc-stop-btn").css('display', 'none');
                $("#bdc-allow-delete-btn").css('display', 'none');
                $("#bdc-apply-delete-btn").css('display', 'none');
            } else if (iSabandon == "show") {
                resizeOprateBtn(0, 80, 40, 0);
            }
        }

        function showTodoButton(authorityData) {
            if (authorityData.indexOf('delete') > 0) {
                $("#bdc-delete-btn").css('display', 'inline-block');
            }
            if (lcPageEdition == "changzhou") {
                if (iSabandon !== "show" && authorityData.indexOf('back') > 0) {
                    $("#bdc-back-btn").css('display', 'inline-block');
                }
            } else {
                if (authorityData.indexOf('back') > 0) {
                    $("#bdc-back-btn").css('display', 'inline-block');
                }
            }
            if (authorityData.indexOf('stop') > 0) {
                $("#bdc-stop-btn").css('display', 'inline-block');
            }
            if(authorityData.indexOf('allowDelete') > 0){
                $("#bdc-allow-delete-btn").css('display', 'inline-block');
            }
            if(authorityData.indexOf('applyDelete') > 0){
                $("#bdc-apply-delete-btn").css('display', 'inline-block');
            }
            // 判断下一个结点是否是办结
            var isEnd;
            var isForward;
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/forward/isEnd",
                data: {taskId: taskId},
                async: false,
                success: function (data) {
                    // 判断是否节点结束，节点结束转发就不显示，否则转发显示
                    if(lcPageEdition=="changzhou"){
                        //常州强制办结显示转发按钮
                        isForward = data.transmit || data.forceEnd;
                        isEnd = data.end ;
                    }else{
                        isForward = data.transmit;
                        isEnd = data.end || data.forceEnd;
                    }
                    isForceEnd = data.forceEnd;
                    var ntSize = 4;
                    var size = 3;
                    if (isForward && isEnd) {
                        size++;
                        ntSize++;
                    }
                    if ((lcPageEdition == 'nt' || lcPageEdition == 'haimen')&& authorityData.indexOf('stop') > 0) { //南通增加终止按钮
                        resizeOprateBtn(ntSize * 60, ntSize * 60 + 80, ntSize * 60 + 40, ntSize * 60);
                    } else {
                        if (lcPageEdition == "changzhou" && iSabandon == "show") {
                            size--;
                            resizeOprateBtn(size * 60, size * 60 + 80, size * 60 + 40, size * 60);
                        } else {
                            resizeOprateBtn(size * 60, size * 60 + 80, size * 60 + 40, size * 60);
                        }
                    }
                    if (authorityData.indexOf('forward') > 0) {
                        if (isForward) {
                            $("#bdc-forward-btn").css('display', 'inline-block');
                        } else {
                            $("#bdc-forward-btn").css('display', 'none');
                        }
                        if (isEnd) {
                            $("#bdc-end-btn").css('display', 'inline-block');
                        } else {
                            $("#bdc-end-btn").css('display', 'none');
                        }
                    }

                }, error: function (e) {
                    resizeOprateBtn(2 * 60, 2 * 60 + 80, 2 * 60 + 40, 2 * 60);
                    response.fail(e,'');
                }
            });
        }

        function showRlButton() {
            if(claimStatus!=0){
                if (lcPageEdition == 'nt' || lcPageEdition == 'haimen') {
                    resizeOprateBtn(5 * 60, 5 * 60 + 80, 5 * 60 + 40, 5 * 60);
                } else {
                    resizeOprateBtn(4 * 60, 4 * 60 + 80, 4 * 60 + 40, 4 * 60);
                }
                $("#bdc-cancel-claim-btn").css('display', 'inline-block');
            }
            checkBackBtn();
        }

        function queryCheckProcessDefKey() {
            if (lcPageEdition === 'nt' && check_processkey == '') {
                getReturnData("/rest/v1.0/config/xm/check/processkey", {}, "GET", function (data) {
                    check_processkey = data;
                    checkBackBtn();
                }, false);
            }
        }

        function checkBackBtn() {
            if (lcPageEdition === 'nt' && check_processkey != 'hide') {
                if (check_processkey.indexOf(processKey) > -1 && claimStatus != 0) {
                    resizeOprateBtn(6 * 60, 6 * 60 + 80, 6 * 60 + 40, 6 * 60);
                    $("#bdc-check-back-btn").css('display', 'inline-block');
                }
            }
        }
        //展示更多意见信息弹窗
        function showMoreInfo(){
            var url ="/realestate-portal-ui/view/more-info.html?processInstanceId=" + processInstanceId + "&taskId="
                + taskId;

            layer.open({
                type: 2,
                title: '更多意见',
                anim: -1,
                shadeClose: true,
                maxmin: true,
                shade: false,
                area: ['800px', '575px'],
                offset: 'auto',
                content:url,
                end: function () {

                }
            })
        }
        //展开
        function expand(){
            $(".bdc-tips-left-box").css({"max-height":$(".layui-nav-tree").height()});

            $("#expand").addClass('bdc-hide').siblings().removeClass('bdc-hide');
        }
        //收起
        function shrink(){
            $(".bdc-tips-left-box").css({"max-height":"136px"});
            $("#shrink").addClass('bdc-hide').siblings().removeClass('bdc-hide');
        }

        //判断退回消息内容，此处因为接口调用需要延时加载才能完整获取两个退回意见的总高度
        setTimeout(function () {
            //判断溢出
            if ($(".bdc-tips-left-box").prop('scrollHeight') > $(".bdc-tips-left-box").prop('clientHeight')) {
                addModel()
                $('.bdc-tips-left-box').after("<br> <div class='more'><a  id='expand' style='cursor:pointer;color: #1d87d1'>...&nbsp;</a><a  id='shrink'  class='bdc-hide' style='cursor:pointer;color: #1d87d1'>收起-&nbsp;</a><a class='layui-btn bdc-major-btn layui-btn-xs' id='showMoreInfo'>更多意见</a></div>")
                $("#expand").click(function () {
                    expand();
                });
                $("#shrink").click(function () {
                    shrink();
                });
                $("#showMoreInfo").click(function () {
                    showMoreInfo();
                });

            }else{
                $(".bdc-tips-left-box").css({"border-bottom":"1px solid #8bc7ff"})
            }
            removeModal();
        },100);


        listFiles();

        // 0.1 渲染附件侧边栏
        //0. 侧边栏动态渲染
        var imgList,
            imgListLength,
            imgIndex;
        //0.1 tab点击，改变iframe的高度
        $('.layui-side-menu .layui-nav-item a').on('click', function () {
            setTimeout(function () {
                $('.layadmin-iframe').height($('.layui-layout-admin .layui-body .layadmin-tabsbody-item').height());
            }, 100);
        });

        //鼠标覆盖在顶部 info 图标，展示相关信息
        $('.bdc-aside-title img').on('mouseenter', function () {
            $('.bdc-aside-title .bdc-title-tips').removeClass('bdc-hide');
        });
        $('.bdc-aside-title .bdc-title-tips').on('mouseleave', function () {
            $('.bdc-aside-title .bdc-title-tips').addClass('bdc-hide');
        });
        //点击附件一级、二级按钮
        $('#fjLeftTree').on('click', '.bdc-fj-file', function () {
            var src = $(this).data('src');
            var type = $(this).data('type');
            var typedm = $(this).data('typedm');
            if(typedm != 0 &&$(this).parent().hasClass("layui-nav-item")){
                //根目录为文件
                imgIndex = $(this).index() + 1;
                imgListLength = 1;
                if($('#seeImgView .viewer-container').length > 0){
                    viewer.destroy();
                }else {
                    $('#seeImgView').html('');
                }
                renderImg(src,type);
            }else {
                imgIndex = 0;
                imgList = $(this).siblings('.layui-nav-child').children();
                if (imgList.length != 0) {
                    imgListLength = imgList.length;
                }
                $(this).parent().siblings().removeClass('layui-nav-itemed');
                $(this).parent().siblings().find('dd').removeClass('layui-nav-itemed');
                $('.fl-list-box').find('dd').removeClass('layui-this');
                if ($('#seeImgView .viewer-container').length > 0) {
                    viewer.destroy();
                } else {
                    $('#seeImgView').html('');
                }
                var src = '';
                renderImg(src, '');
            }
        });

        //------------------附件相关动效----------------------
        //默认展示图片
        var image = new Image();
        var viewer;
        renderImg('','');

        //点击展示小箭头
        var isShow = false,
            clickTimes = 0;
        $('.bdc-fj-show img').on('click', function () {
            isShow = !isShow;
            clickTimes++;
            if (isShow) {
                $('.bdc-fj-default-content').addClass('bdc-hide');
                $('.bdc-fj-list').removeClass('bdc-hide');
                $('.bdc-fj-show img').css('transform', 'rotate(180deg)');
                listFiles();

                $('.bdc-fj-content').removeClass('bdc-hide');
                $('.layui-layout-admin .layui-body .layadmin-tabsbody-item').css('padding-bottom', '284px');
                $('.layui-side-menu .layui-side-scroll').css('padding-bottom', '300px');

            } else {
                $('.bdc-fj-show img').css('transform', 'rotate(0)');
                $('.bdc-fj-content').addClass('bdc-hide');
                $('.layui-layout-admin .layui-body .layadmin-tabsbody-item').css('padding-bottom', '0');
                $('.layui-side-menu .layui-side-scroll').css('padding-bottom', '35px');
                $('.bdc-fj-list').addClass('bdc-hide');
                $('.bdc-fj-default-content').removeClass('bdc-hide');
            }

        });


        // 点击分屏
        var fpShow = false ;
        $('.bdc-fj-title .bdc-show-two').on('click', function (){
            fpShow = !fpShow;
            if (fpShow) {
                //如果已经展开附件了先收起附件再去分屏 判断点开附件
                if(isShow){
                    //此处是收起附件的方法
                    $('.bdc-fj-show img').css('transform', 'rotate(0)');
                    $('.bdc-fj-content').addClass('bdc-hide');
                    $('.layui-layout-admin .layui-body .layadmin-tabsbody-item').css('padding-bottom', '0');
                    $('.layui-side-menu .layui-side-scroll').css('padding-bottom', '35px');
                    $('.bdc-fj-list').addClass('bdc-hide');
                    $('.bdc-fj-default-content').removeClass('bdc-hide');
                }
                //隐藏收缩附件图片
                $(".bdc-fj-show img").hide()
                $(".bdc-aside-close").click();
                //左移分屏
                $("#LAY_app_body").addClass("bdc-fp-left")
                //隐藏默认附件框
                $('.bdc-fj-default-content').addClass('bdc-hide');
                //展示附件
                $('.bdc-fj-list').removeClass('bdc-hide');
                //去除初始默认高度
                $(".bdc-fj-list").removeAttr("style");
                //右移分屏
                $(".bdc-fj-list").addClass("bdc-fp-right")
                //拷贝左侧高度 防止拖拽过形成内置高度
                $(".bdc-fp-right").height( $(".bdc-fp-left"))
                //展开附件实际内容
                $('.bdc-fj-content').removeClass('bdc-hide');
                //设置附件内容高度
                $(".bdc-fj-content").height($(".layadmin-tabsbody-item").height() - 38-61)

                //图标旋转
                $('.bdc-fj-title .bdc-show-two').addClass('bdc-normal');
                //判断是否显示原权利附件，如果配置显示则隐藏原权利附件
                if(xsyqlfj){
                    $(".bdc-yqlfj-default-content").hide()
                }
            } else {
                //展示收缩附件图片
                $(".bdc-fj-show img").show()
                //旋转90度
                $('.bdc-fj-title .bdc-show-two').removeClass('bdc-normal');
                //展开左侧收缩栏
                $(".bdc-aside-open").click();
                // 去除分屏样式
                $("#LAY_app_body").removeClass("bdc-fp-left")
                //恢复默认附件样式
                $('.bdc-fj-default-content').removeClass('bdc-hide');
                //隐藏附件实际内容
                $('.bdc-fj-list').addClass('bdc-hide');
                //右移分屏
                $(".bdc-fj-list").removeClass("bdc-fp-right")

                //还原附件内容高度
                $(".bdc-fj-content").removeAttr("style");

                //    恢复主窗口宽度大小
                $("#LAY_app_body").removeAttr("style");
                //恢复附件大小
                $(".bdc-fj-list").removeAttr("style");
                //展示原权利附件
                if(xsyqlfj){
                    $(".bdc-yqlfj-default-content").show()
                }
                //判断是否展开附件
                if(isShow){
                    $(".bdc-fj-show img").click();
                }
                //判断是否设置了图片分屏样式，恢复附件初始样式
                if(document.querySelector("fp-img")){
                        $("#seeImgView img").removeClass('fp-img')
                }

            }
        });


        // 点击以窗口或者最大化展示附件弹窗
        var popupLayer;
        $('.bdc-fj-title .bdc-show-popup').on('click', function () {
            if("standard" == lcPageEdition || "yancheng" == lcPageEdition) {
                window.open('./popup.html?processinsid=' + processInstanceId);
                return;
            }
            if("changzhou" == lcPageEdition) {
                window.open('./popup-img.html?processinsid=' + processInstanceId,"附件查看");
                return;
            }


            $('.bdc-fj-default-content').addClass('bdc-hide');
            $('.bdc-fj-list').addClass('bdc-hide');

            $('.layui-side-menu .layui-side-scroll').css('padding-bottom', 0);
            $('.layui-layout-admin .layui-body .layadmin-tabsbody-item').css('padding-bottom', 0);
            $('.bdc-fj-content').height('203px');
            // $('.bdc-fj-list').addClass('bdc-hide');

            isShow = false;
            clickTimes = 0;
            $('.bdc-fj-show img').css('transform', 'rotate(0)');
            popupLayer = layer.open({
                type: 2
                ,skin: 'bdc-add-fj-layer'
                , title: '<div class="bdc-fj-title-content">' +
                    '<p class="bdc-fj-title">' +
                    '<span class="bdc-fj-word">附件' +
                    '</span>' +
                    '</p>' +
                    '<div class="bdc-operate-icon">' +
                    '<i class="fa fa-expand" aria-hidden="true"></i>' +
                    '<i class="layui-icon layui-icon-close"></i>' +
                    '</div>' +
                    '</div>'
                // 小窗口展示附件
                , area: ['960px', '350px']
                , shade: 0
                , maxmin: true
                , content: './popup-img.html?processinsid=' + processInstanceId
                , zIndex: layer.zIndex //重点1
                , success: function (layero) {
                    layer.setTop(layero); //重点2
                },
                cancel: function () {
                    $('.bdc-fj-content').addClass('bdc-hide');
                    $('.layui-side-menu .layui-side-scroll').css('padding-bottom', '35px');

                    $('.bdc-fj-default-content').removeClass('bdc-hide');
                },
            });
            // if (lcPageEdition == 'nt') {
            layer.full(popupLayer);
            // }
        });

        $('.bdc-yqlfj-title .bdc-showyql-popup').on('click', function (){
            //查询原权利信息，根据原权利数量选择展示附件方式
            var yqlurl = "/realestate-accept-ui" + '/slym/ql/listyqlxxbypage';
            $.ajax({
                url: yqlurl,
                type: "GET",
                async: false,
                contentType: 'application/json;charset=utf-8',
                data: {gzlslid: processInstanceId},
                success: function (data) {
                    console.log(data);
                    var qlxx = data.content;
                    if (qlxx.length < 1) { //无原权利信息，弹窗提示；
                        warnMsg("没有原权利附件!");
                    } else if (qlxx.length == 1) { //只有一条原权利，直接打开附件展示页面
                            var yqlgzlslid = qlxx[0].gzlslid;
                            var yqlslbh = qlxx[0].slbh;
                            window.open('/realestate-portal-ui/view/popup.html?processinsid=' + yqlgzlslid + "&slbh=" + yqlslbh);
                    } else if (qlxx.length > 1) { //打开原权利列表页面，根据具体原权利信息打开对应附件展示页面
                        window.open('/realestate-accept-ui/rest/v1.0/yqlxx?processInsId=' + processInstanceId + "&yqlfj=true");
                    }
                }
            });
        });
        $(document).on('mousedown', '.bdc-fj-title .bdc-show-bottom', function () {
            layer.close(popupLayer);
            $('.bdc-fj-list').removeClass('bdc-hide');
            $('.bdc-fj-content').addClass('bdc-hide');
            $('.layui-side-menu .layui-side-scroll').css('padding-bottom', '35px');
        });

        //1. 复选框相关动效
        preventBubbling();

        //1.1 监听 文件夹 复选框选择
        form.on('checkbox(folderFilter)', function (data) {
            if (!data.elem.checked) {
                $(this).parent().siblings().find('.layui-form-checkbox').removeClass('layui-form-checked')
            }
        });

        //1.2 监听 文件 复选框选择
        form.on('checkbox(fileFilter)', function (data) {
            if (data.elem.checked) {
                //选中，父元素选中
                $(this).parents('.layui-nav-child').siblings().find('.layui-form-checkbox').addClass('layui-form-checked');
            } else {
                // 没选中，看其他兄弟元素是否选中
                if ($(this).parent().parent().siblings().find('.layui-form-checkbox').hasClass('layui-form-checked')) {
                    $(this).parents('.layui-nav-child').siblings().find('.layui-form-checkbox').addClass('layui-form-checked');
                } else {
                    $(this).parents('.layui-nav-child').siblings().find('.layui-form-checkbox').removeClass('layui-form-checked');
                }
            }
        });


        //阻止点击复选框向上冒泡
        var $checkBox = $('.layui-form-checkbox');

        function preventBubbling() {
            $checkBox = $('.layui-form-checkbox');
            //阻止复选框向上冒泡
            $checkBox.on('click', function (event) {
                event.stopPropagation();
            });
        }

        //2. 监听图片侧边栏单击
        $('.fl-list-box').on('click', '.layui-nav-child dd>a', function () {
            $(this).parent().addClass('layui-this');
            // 常州版本未查看附件名称默认显示蓝色，已查看显示灰色，正在查看显示红色
            if($(this).parent().hasClass('default-span')){
                $(this).parent().removeClass('default-span');
                var thisId = $(this).data('id')
                // 继承附件查看已读id
                if(isNotBlank(JSON.parse(sessionStorage.getItem("fjIdList")))){
                    fjIdList = JSON.parse(sessionStorage.getItem("fjIdList"));
                }
                fjIdList.push({id:thisId});
                sessionStorage.setItem("fjIdList",JSON.stringify(fjIdList));
            }
            imgIndex = $(this).index() + 1;
            imgList = $(this).parent().parent().children();
            imgListLength = imgList.length;
            if($('#seeImgView .viewer-container').length > 0){
                viewer.destroy();
            }else {
                $('#seeImgView').html('');
            }
            var src = $(this).data('src');
            var type = $(this).data('type');
            renderImg(src,type);
        });

        //2.1 监听上一个 下一个
        $('.seeLeftBtn').on('click', function () {
            imgIndex--;
            if (imgIndex >= 0) {
                if ($(imgList[imgIndex]).children().length == 1) {
                    $('.fl-list-box').find('dd').removeClass('layui-this');
                    $(imgList[imgIndex]).addClass('layui-this');
                    if ($('#seeImgView .viewer-container').length > 0) {
                        viewer.destroy();
                    } else {
                        $('#seeImgView').html('');
                    }
                    var src = $(imgList[imgIndex]).children('a').data('src');
                    var type = $(imgList[imgIndex]).children('a').data('type');
                    renderImg(src, type);
                } else {
                    $(imgList[imgIndex]).siblings().removeClass('layui-nav-itemed');
                    $(imgList[imgIndex]).siblings().find('dd').removeClass('layui-nav-itemed');
                    $(imgList[imgIndex]).addClass('layui-nav-itemed');

                    imgList = $(imgList[imgIndex]).children('dl').children();
                    if (imgList.length != 0) {
                        imgListLength = imgList.length;
                    }
                    if($('#seeImgView .viewer-container').length > 0){
                        viewer.destroy();
                    }else {
                        $('#seeImgView').html('');
                    }
                    var src = '';
                    var type = '';
                    renderImg(src,type);
                    imgIndex = 0;
                }
            } else {

                imgIndex = 1;
                layer.msg('已经是第一张了');
            }
        });

        $('.seeRightBtn').on('click', function () {
            // console.log(imgIndex);
            // imgIndex--;
            if (imgIndex <= imgListLength - 1) {
                if ($(imgList[imgIndex]).children().length == 1) {
                    $('.fl-list-box').find('dd').removeClass('layui-this');
                    $(imgList[imgIndex]).addClass('layui-this');
                    if ($('#seeImgView .viewer-container').length > 0) {
                        viewer.destroy();
                    } else {
                        $('#seeImgView').html('');
                    }
                    var src = $(imgList[imgIndex]).children('a').data('src');
                    var type = $(imgList[imgIndex]).children('a').data('type');
                    renderImg(src, type);

                    imgIndex++;
                } else {
                    $(imgList[imgIndex]).siblings().removeClass('layui-nav-itemed');
                    $(imgList[imgIndex]).siblings().find('dd').removeClass('layui-nav-itemed');
                    $(imgList[imgIndex]).addClass('layui-nav-itemed');

                    // console.log($(imgList[imgIndex]));
                    imgList = $(imgList[imgIndex]).children('dl').children();
                    if (imgList.length != 0) {
                        imgListLength = imgList.length;
                    }
                    if($('#seeImgView .viewer-container').length > 0){
                        viewer.destroy();
                    }else {
                        $('#seeImgView').html('');
                    }
                    var src = '';
                    renderImg(src,'');
                    imgIndex = 0;
                }
            } else {
                imgIndex = imgListLength - 1;
                layer.msg('已经是最后一张了');
            }
        });

        //渲染指定图片
        function renderImg(src,type) {
            if(type =='docx'){
                //WORD 转换成PDF查看
                src =getContextPath() + "/rest/v1.0/files/wordToPdf/download?downUrl="+encodeURIComponent(src)+"&type="+type;
                type ="pdf";
            }
            if(type == 'pdf' ){
                //PDF引用pdf.js 预览,不用借助插件
                $('#seeImgView').html('<iframe src="' + getContextPath() + "/static/lib/pdf/web/viewer.html?file=" + encodeURIComponent(src) +'"></iframe>');
            }else if(type == 'lsx' || type == 'xls' || type == 'doc' || type == 'docx'){
                $('#seeImgView').html('<iframe src="'+ src +'"></iframe>');

            } else if( type == 'txt'){
                $('#seeImgView').html('<iframe src="'+ src +'"></iframe>');
                var fileNameArray = src.split('/');
                var id = fileNameArray[fileNameArray.length - 1];
                var pdfUrl = getContextPath() + "/rest/v1.0/files/xz?zipfile=" + id;
                window.open(pdfUrl,"TXTDOWNLOAD");
            }else if(type == 'mp4' || type == 'webm' || type == 'ogg'){
                var loadvideo = '<video width="100%" height="100%"  controls="controls" autobuffer="autobuffer"  controls="controls" loop="loop"><source src="'+ src + '"></source></video>';
                $('#seeImgView').html(loadvideo);
            }else if(type == 'ofd'){
                getReturnData("/rest/v1.0/config/pz", {}, "GET", function (data) {
                    if(data && data.ofdSfzxyl == false){
                        $('#seeImgView').html('<iframe src="'+ src +'"></iframe>');
                    }else {
                        if(isNotBlank(src)){
                            var tmp = src.split('/');
                            var storageid = tmp[tmp.length-1];
                            var url = getContextPath() + "/view/popup-ofd.html?storageid="+storageid+"&url="+src;
                            $('#seeImgView').html('<iframe src="'+ url +'"></iframe>');
                        }
                    }
                },true);
            } else {
                image.src = src;
                viewer = new Viewer(image, {
                    hidden: function () {
                        viewer.destroy();
                    }
                });
                viewer.show();
                // 定时器判断获取到img dom
                var timer = setInterval(function(){

                    if(document.querySelector("#seeImgView img")){
                        //判断是否分屏 分屏 则调整图片位置
                        if(fpShow){
                            $("#seeImgView img").addClass('fp-img')
                        }else{
                            $("#seeImgView img").removeClass('fp-img')
                        }
                        //关闭定时器
                        clearInterval(timer);
                    }
                },200);

            }
        }

        //3. 点击左侧操作按钮
        //3.1 全选
        $('.bdc-select-all').on('click', function () {
            $checkBox.addClass('layui-form-checked');
        });
        //3.2 新增
        $('.bdc-add-btn').on('click', function (event) {
            event.stopPropagation();
            $('.bdc-add-details').toggleClass('bdc-hide');
        });
        $('body').on('click', function () {
            $('.bdc-add-details').addClass('bdc-hide');
            $('.layui-nav .bdc-prepend-li a .layui-icon-close').click();
            $('.bdc-aside-title .bdc-title-tips').addClass('bdc-hide');
        });
        // 3.2.1 新增文件夹
        $('.bdc-add-wjj').on('click', function (event) {
            event.stopPropagation();
            $('.fl-list-box>.layui-nav').prepend('<li class="layui-nav-item bdc-prepend-li">' +
                '<a href="javascript:;">' +
                '<input type="checkbox" name="bdcFolder" lay-filter="folderFilter" lay-skin="primary">' +
                '<img src="../image/file.png" alt="">' +
                '<input type="text" autocomplete="off" class="layui-input bdc-new-folder-name" value="新建文件夹">' +
                '<i class="layui-icon bdc-icon-add layui-icon-ok"></i>' +
                '<i class="layui-icon bdc-icon-add layui-icon-close"></i>' +
                '</a>' +
                '</li>');
            form.render('checkbox');
            $('.bdc-new-folder-name').focusEnd();
            preventBubbling();
        });

        // 3.2.1.1 点击√
        $('.pf-fl-list').on('click', '.layui-nav .bdc-prepend-li a .layui-icon-ok', function (event) {
            event.stopPropagation();
            var folderName = $('.bdc-new-folder-name').val();
            $(this).parent().append('<span>' + folderName + '</span>');
            $('.bdc-new-folder-name').remove();
            $('.bdc-icon-add').remove();
        });
        // 3.2.1.2 点击×
        $('.pf-fl-list').on('click', '.layui-nav .bdc-prepend-li a .layui-icon-close', function (event) {
            event.stopPropagation();
            $(this).parents('.bdc-prepend-li').remove();
            // $('.bdc-prepend-li').remove();
        });
        //3.2.1.3 点击输入框
        $('.pf-fl-list').on('click', '.bdc-prepend-li .bdc-new-folder-name', function (event) {
            event.stopPropagation();
        });

        // 3.3 删除
        $('.bdc-fj-delete').on('click', function () {
            layer.msg('您确认要删除吗？', {
                skin: 'bdc-del-bg',
                time: 0 //不自动关闭
                , shade: [0.4, '#000']
                , btn: ['确定', '取消']
                , yes: function (index) {
                    layer.close(index);
                    layer.msg('删除成功');
                }
            });
        });

        //拖拽调整 附件 高度
        var maxHeight = $('body').height() - 40;
        var src_posi_Y = 0, dest_posi_Y = 0, move_Y = 0, is_mouse_down = false, destHeight = 299;
        $(".bdc-drag-line").mousedown(function (e) {
            src_posi_Y = e.pageY;
            is_mouse_down = true;

            return false;
        });
        $(document).find('.bdc-fj-list').bind("mouseup click",function (e) {
            // if(is_mouse_down){
            //     is_mouse_down = false;
            // }
            is_mouse_down = false;
        }).mousemove(function (e) {
            dest_posi_Y = e.pageY;
            move_Y = src_posi_Y - dest_posi_Y;
            src_posi_Y = dest_posi_Y;
            destHeight = $(".bdc-fj-list").height() + move_Y;
            if (is_mouse_down) {
                if (destHeight < maxHeight) {
                    $(".bdc-fj-content").css("height", destHeight > 299 ? destHeight - 35 : 264);
                } else {
                    $(".bdc-fj-content").css("height", maxHeight - 35);
                }
                // 防止主体内容被遮挡
                var height = $(".bdc-fj-content").css("height");
                $('.layui-layout-admin .layui-body .layadmin-tabsbody-item').css('padding-bottom', height);
            }
        });

        //点击转发
        $('.bdc-send').on('click', function () {
            addModel();
            var checkStatus = {};
            var datas = [];
            datas.push(checkData);
            checkStatus.data = datas;
            setTimeout(function () {
                if(lcPageEdition == 'changzhou'){
                    workflow.forwardauto(checkStatus, '', '', 'dbTable', false, processInstanceId, isForceEnd);
                }else{
                    workflow.forwardauto(checkStatus, '', '', 'dbTable', false, processInstanceId);
                }
            }, 50);
        });

        //点击办结
        $('.bdc-end').on('click', function () {
            addModel();
            var checkStatus = {};
            var datas = [];
            datas.push(checkData);
            checkStatus.data = datas;
            setTimeout(function () {
                if(lcPageEdition == 'changzhou'){
                    workflow.forwardauto(checkStatus, '', '', 'dbTable', false, processInstanceId);
                }else{
                    workflow.forwardauto(checkStatus, '', '', 'dbTable', false, processInstanceId, isForceEnd);
                }
            }, 50);
        });

        // 点击退回
        $('#bdc-back-btn').on('click', function () {
            // 56777 【南通大市】关于对互联网+受理的件审核时点退回给予提示的需求
            if ((lcPageEdition == 'nt' || lcPageEdition == 'haimen') && checkData.taskName == "审核") {
                // 根据工作流实例id获取受理编号
                getSlbhByProcInsId(processInstanceId);
                // 判断受理编号前缀是否配置
                var flag = isSlbhStartwithPz(slbh);
                if(slbh != null && flag) {
                    layer.msg("此为互联网+办件，退回请点击“审核不通过”");
                } else {
                    workflow.backProcess(checkData, "", "", "dbTable", false);
                }
            } else {
                workflow.backProcess(checkData, "", "", "dbTable", false);
            }
        });


        // 点击取回
        $('#bdc-take-btn').on('click', function () {
            workflow.fetchProcess(checkData, "", "", "doneTableId", false);
        });
        // 取消认领
        $('#bdc-cancel-claim-btn').on('click', function () {
            workflow.cancelClaimProcess(checkData, "", "", "dbTable", false);
        });

        // 审核不通过
        $('#bdc-check-back-btn').on('click', function () {
            layer.open({
                title: '审核',
                type: 1,
                area: ['430px'],
                btn: ['确定', '取消'],
                content: $('#check-back-reason')
                , yes: function (index, layero) {
                    var reason = $("#checkbackreason").val();
                    if (isNullOrEmpty(reason)) {
                        layer.msg('请输入审核不通过原因!');
                        return false;
                    }
                    //提交 的回调
                    addModel();
                    layer.close(index);
                    var gzlslid = checkData.processInstanceId;

                    $.ajax({
                        type: "post",
                        url: getContextPath() + "/rest/v1.0/workflow/process-instances/task/check/back",
                        traditional: true,
                        data: {taskId: checkData.taskId, gzlslid: gzlslid, reason: reason},
                        success: function () {
                            layer.msg('成功');
                            renderTable("", "", "dbTable");
                            $.ajax({
                                type: "post",
                                url: getContextPath() + "/rest/v1.0/task/todo",
                                traditional: true,
                                success: function (data) {
                                    if (data.totalElements > 99) {
                                        $('.bdc-list-num-tips').html('99+');
                                    } else {
                                        $('.bdc-list-num-tips').html(data.totalElements);
                                    }
                                }
                            });
                        }, error: function (e) {
                            response.fail(e, '');
                        }, complete: function () {
                            removeModal();
                            window.renderTable('', '', 'dbTable');
                            window.parent.close();
                        }
                    });
                }
                , btn2: function (index, layero) {
                    //取消 的回调
                    layer.close(index);
                }
            });
        });

        // 点击删除
        $('#bdc-delete-btn').on('click', function () {
            var data = [];
            data.push(checkData);
            var allData = {'data': data};
            workflow.deleteProcess(allData, '', '', 'dbTable', false);
        });

        // 点击申请删除
        $('#bdc-apply-delete-btn').on('click', function(){
            var data = [];
            data.push(checkData);
            var allData = {'data': data};
            workflow.applyDelete(allData, '', '', 'dbTable', false, processInstanceId);
        });

        // 点击允许删除
        $('#bdc-allow-delete-btn').on('click', function () {
            var data = [];
            data.push(checkData);
            var allData = {'data': data};
            workflow.allowDelete(allData, '', '', 'dbTable', false, processInstanceId);
        });

        // 点击终止
        $('#bdc-stop-btn').on('click', function () {
            var data = [];
            data.push(checkData);
            var allData = {'data': data};
            workflow.stopProcess(allData, '', '', 'dbTable', false);
        });

        //显示撤销
        $('#revokeShow').on('click',function(){
            var controlWidth = $('.layadmin-pagetabs .bdc-control-icon').width();
            $('.layadmin-pagetabs .bdc-control-icon').width(controlWidth + 23 + 'px');
            $('.layadmin-pagetabs .layui-icon-next').css('right', controlWidth + 21 + 'px');
            $('.layadmin-pagetabs .layui-icon-set-sm').css('right', controlWidth + 21 + 'px');
            $(this).css('display','none');
            $('#bdc-revoke-btn').css('display','inline-block');
        });
        // 点击撤销
        $('#bdc-revoke-btn').on('click', function () {
            workflow.abandonProcess(checkData, "", "", "dbTable", false);
        });
        // 点击侧边的收缩按钮
        // 隐藏
        $('.bdc-aside-close').on('click', function () {
            $('.layui-layout-admin .layui-side').animate({left: '-220px'}, 100);
            $('.layui-layout-admin .layui-layout-left').animate({left: 0}, 100);
            $('.layui-layout-admin .layui-footer').animate({left: 0}, 100);
            $('.layui-layout-admin .layui-body').animate({left: 0}, 100);
            $('.layadmin-pagetabs').animate({left: 0}, 100);
            $('.bdc-aside-zoom').toggleClass('bdc-hide').animate({left: 0}, 300);
            // $('.bdc-aside-open').animate({left: 0},300);
        });
        // 显示
        $('.bdc-aside-open').on('click', function () {
            $('.layui-layout-admin .layui-side').animate({left: '0'}, 100);

            $('.layui-layout-admin .layui-layout-left').animate({left: '220px'}, 100);
            $('.layui-layout-admin .layui-footer').animate({left: '220px'}, 100);
            $('.layui-layout-admin .layui-body').animate({left: '220px'}, 100);
            $('.layadmin-pagetabs').animate({left: '220px'}, 100);

            $('.bdc-aside-zoom').toggleClass('bdc-hide').animate({left: '206px'}, 300);
            // $('.bdc-aside-open').animate({left: '208px'},300);
        });

        //默认显示的第一个tab 删除
        $('.bdc-aside-tab li .bdc-tab-close').on('click', function () {
            var _this = $(this).parent();
            if (_this.hasClass('layui-this')) {
                if($('.bdc-aside-tab .layui-tab-title li').length > 2){
                    _this.parent().find('li:nth-child(3)').addClass('layui-this');
                    $('.layui-body .layadmin-tabsbody-item:nth-child(3)').addClass('layui-show');
                    var layId = $('.bdc-aside-tab .layui-tab-title li:nth-child(3)').attr('lay-id');
                    var $sideLi = $('.layui-side-scroll .layui-nav-tree li');
                    for(var i = 1; i< $sideLi.length; i++){
                        if($($sideLi[i]).find('a').attr('lay-href') == layId){
                            $($sideLi[i]).addClass('layui-this');
                        }
                    }

                    _this.remove();
                    $('.layui-body .layadmin-tabsbody-item:nth-child(2)').remove();
                    $('.layui-side-scroll .layui-nav-tree li:first-child').removeClass('layui-this');
                }else {
                    _this.remove();
                    $('.layui-body .layadmin-tabsbody-item:nth-child(2)').remove();
                    $('.layui-side-scroll .layui-nav-tree li:first-child').removeClass('layui-this');
                }


            } else {
                _this.remove();
                $('.layui-body .layadmin-tabsbody-item:nth-child(2)').remove();
            }

        });

        //焦点在input文字最后
        $.fn.setCursorPosition = function (position) {
            if (this.lengh == 0) return this;
            return $(this).setSelection(position, position);
        };

        $.fn.setSelection = function (selectionStart, selectionEnd) {
            if (this.lengh == 0) return this;
            input = this[0];

            if (input.createTextRange) {
                var range = input.createTextRange();
                range.collapse(true);
                range.moveEnd('character', selectionEnd);
                range.moveStart('character', selectionStart);
                range.select();
            } else if (input.setSelectionRange) {
                input.focus();
                input.setSelectionRange(selectionStart, selectionEnd);
            }

            return this;
        };

        $.fn.focusEnd = function () {
            if (this.val() != undefined) {
                this.setCursorPosition(this.val().length);
            }
        };

    });
    function resizeOprateBtn(controlWidth, pageTabs, nextIcon, setIcon) {
        if (iSabandon == "show") {
            controlWidth += 60;
            pageTabs += 60;
            nextIcon += 60;
            setIcon += 60;
        }
        if($('#revokeShow').hasClass('bdc-show-cs-js')){
            controlWidth -= 22;
            nextIcon -= 22;
            setIcon -= 22;
        }
        $('.layadmin-pagetabs .bdc-control-icon').width(controlWidth + 'px');
        $('.layadmin-pagetabs').css('padding-right', pageTabs + 'px');
        $('.layadmin-pagetabs .layui-icon-next').css('right', nextIcon -40 + 'px');
        $('.layadmin-pagetabs .layui-icon-set-sm').css('right', setIcon + 'px');
    }

    /**
     * 动态计算按钮宽度，动态设置按钮栏宽度
     */
    function resizeBtnDynamic(){
        var controlWidth = 0;
        $('.layadmin-pagetabs .bdc-control-icon').find("a").each(function(index, ele){
            if($(ele).css("display")!="none"){
                var eleWidth = $(ele).outerWidth();
                controlWidth += eleWidth;
            }
        });
        controlWidth = Math.ceil(controlWidth);
        // 按钮行宽度
        $('.layadmin-pagetabs .bdc-control-icon').width(controlWidth + 'px');
        var pageTabs = controlWidth + 80;
        $('.layadmin-pagetabs').css('padding-right', pageTabs + 'px');
        var nextIcon = controlWidth ;
        $('.layadmin-pagetabs .layui-icon-next').css('right', nextIcon + 'px');
        $('.layadmin-pagetabs .layui-icon-set-sm').css('right', controlWidth + 'px');
    }

    /**
     * 渲染侧边菜单，渲染前 formkey 需要先赋值
     * @param processInstanceType
     */
    function queryForm(processInstanceType) {
        var readOnly = true;
        /**
         * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
         * @description 添加  processInstanceType == 'rl'，认领列表打开页面的同时已认领，只读设置为false
         */
        // lixin 项目列表的表单权限从大云中的流程表单单独控制，所以放开 readOnly 的限制
        if (processInstanceType == 'todo' || processInstanceType == 'rl' || processInstanceType == 'list') {
            readOnly = false;
        }
        // if (processInstanceType == 'list' && lcPageEdition === "hf") {
        //     //项目列表除了管理员打开可编辑，其他人不可编辑，readonly=true
        //     var usr = queryCurrentUser();
        //     if (usr && usr.admin !== 1) {
        //         readOnly = true;
        //     }
        // }
        if (sourceType == 'lsgx') {
            readOnly = true;
        }
        //0. 侧边栏动态渲染
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/task/form-center/" + formKey,
            data: {},
            success: function (data) {
                // 未配置地址项
                var noPathList='';
                data.forEach(function (v) {
                    if(isNotBlank(v.thirdPath)){
                        // 有地址
                        if (v.thirdPath.indexOf("?") != -1) {
                            // 有参数
                            var newPath = v.thirdPath + '&taskId=' + taskId + '&processInsId=' + processInstanceId + '&formStateId=' + v.formStateId + '&readOnly=' + readOnly + '&print=' + print + '&time=' + new Date().getTime() + '&processInstanceType=' + processInstanceType;
                            if(isNotBlank(processKey)){
                                newPath +="&processDefKey="+processKey;
                            }
                            v.thirdPath = newPath;
                        } else {
                            //没有参数
                            var newPath = v.thirdPath + '?taskId=' + taskId + '&processInsId=' + processInstanceId + '&formStateId=' + v.formStateId + '&readOnly=' + readOnly +'&print=' + print + '&time=' + new Date().getTime() + '&processInstanceType=' + processInstanceType;
                            if(isNotBlank(processKey)){
                                newPath +="&processDefKey="+processKey;
                            }
                            v.thirdPath = newPath;
                        }
                    }else {
                        // 没有地址
                        noPathList= noPathList + v.formStateName + ',';
                    }
                });

                // 默认选中第一个表单资源
                if (data && data.length > 0) {
                    $('.layui-body .layadmin-tabsbody-item.layui-show iframe').attr('src', data[0].thirdPath);
                    $('.bdc-aside-tab .layui-tab-title li.layui-this').attr('lay-id', data[0].thirdPath).attr('lay-attr', data[0].thirdPath);
                    $('.bdc-aside-tab .layui-tab-title li.layui-this>span').html(data[0].formStateName);
                }
                var getAsideTpl = asideTpl.innerHTML
                    , getAsideView = document.getElementById('LAY-system-side-menu');
                laytpl(getAsideTpl).render(data, function (html) {
                    getAsideView.innerHTML = html;
                });
                element.render('nav', 'layadmin-system-side-menu');

                if(isNotBlank(noPathList)){
                    $('#otherTips').html( noPathList + '上述项未配置URL地址');
                    $('.bdc-other-tips-box').removeClass('bdc-hide');
                }
            }, error: function (e) {
                response.fail(e,'');
            }
        });
        //点击 不再提示 ，关闭提示框
        $('.bdc-other-tips-box .bdc-close').on('click', function () {
            $('.bdc-other-tips-box').addClass('bdc-hide');
        });
    }

    function listFiles() {
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/files/all",
            data: {
                clientId: "clientId",
                spaceId: processInstanceId
            },
            success: function (data) {
                data.forEach(function (value) {
                    var  index = value.name.lastIndexOf( "." );
                    value.picType= value.name.substring(index + 1, value.name.length);
                    //value.picType = value.name.substring(value.name.length - 3);
                    //格式一律转换为小写
                    value.picType = value.picType.toLowerCase();
                    if (value.children && value.children.length > 0) {
                        value.children.forEach(function (v) {
                            // 常州附件新增是否已读字段
                            if (lcPageEdition == "changzhou") {
                                addSfyd(v);
                            }
                            var  index = v.name.lastIndexOf( "." );
                            v.picType= v.name.substring(index + 1, v.name.length);
                            //v.picType = v.name.substring(v.name.length - 3);
                            //格式一律转换为小写
                            v.picType = v.picType.toLowerCase();
                            if (v.children && v.children.length > 0) {
                                v.children.forEach(function (childV) {
                                    // 常州附件新增是否已读字段
                                    if (lcPageEdition == "changzhou") {
                                        addSfyd(v);
                                    }
                                    var  index = childV.name.lastIndexOf( "." );
                                    childV.picType= childV.name.substring(index + 1, childV.name.length);
                                    //childV.picType = childV.name.substring(childV.name.length - 3);
                                });
                            }
                        });
                    }
                });
                var getFjAsideTpl = fjAsideTpl.innerHTML
                    , getFjAsideView = document.getElementById('fjLeftTree');
                laytpl(getFjAsideTpl).render(data, function (html) {
                    getFjAsideView.innerHTML = html;
                });
                element.render('nav', 'fjTreeFilter');
            }, error: function (e) {
                response.fail(e,'');
            }
        });
    }

    /**
     * 新增sfyd字段并根据fjIdList判断是否已读
     */
    function addSfyd(v) {
        var idList = JSON.parse(sessionStorage.getItem("fjIdList"));
        var isSame = false;
        if(isNotBlank(idList)){
            for(var i = 0;i< idList.length; i++){
                if(v.id == idList[i].id){
                    isSame = true;
                }
            }
        }
        if(isSame){
            v.sfyd = "1";
        }else {
            v.sfyd = "0";
        }
        return v;
    }
});

/**
 * 查询补打角色
 */
function queryPrintRoles() {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/user/isbd",
        async: false,
        success: function (data) {
            if (data) {
                print = true;
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}


/**
 * Html5PostMessage 实现页面之间跨域通信功能
 * portal 页面为主窗口，下述方法用于监听子页面的消息推送，并向其他子页面进行消息推送
 */
if(typeof window.addEventListener!= "undefined"){
    // 非 IE 浏览器监听方式
    window.addEventListener("message", receiveMessage, false);
}else{
    // IE 浏览器的监听方式
    window.attachEvent("onmessage", receiveMessage);
}

function receiveMessage(e) {
    console.info(e);
    var data = e.data;
    var iframes = document.getElementsByTagName("iframe");
    // 向 iframe 窗体发送消息
    iframes[0].contentWindow.postMessage(data, "*");
}

/**
 * 根据工作流实例ID查询受理编号
 */
function getSlbhByProcInsId(gzlslid) {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/task/xm",
        data: {
            gzlslid: gzlslid
        },
        async: false,
        success: function (data) {
            if(data != null && data.length > 0) {
                slbh = data[0].slbh;
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}

/**
 * 判断受理编号前缀是否配置, 受理编号前缀配置，多个字符串 逗号隔开「英文逗号」,HLW,NTCCB,NSH,YH
 */
function isSlbhStartwithPz(slbh) {
    var flag = false;
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/workflow/process-instances/getShjdthtsSlbhStartwith",
        async: false,
        success: function (data) {
            if(data != null && data.length > 0) {
                var slbhStartwith = data;
                for (var i = 0; i < slbhStartwith.length; i++) {
                    if(slbhStartwith[i] != null && slbhStartwith[i].length > 0 && slbh.indexOf(slbhStartwith[i]) == 0) {
                        flag = true;
                        break;
                    }
                }
                console.log("getShjdthtsSlbhStartwith:" + data)
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
    return flag;
}

function initTimer() {
    $.ajax({
        url: "/realestate-portal-ui/rest/v1.0/config/getTimerConfig",
        type: "GET",
        data:{},
        success: function (res) {
            if (res.switch){
                timer2 = window.setInterval("isOffline(2);",res.time);
            }
        }
    })
}