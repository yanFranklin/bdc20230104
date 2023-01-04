/**
 * 任务台账资源的js
 */
var newWin = [];
/**
 * 判断是否可以批量转发
 */
var isPlzf = '';
/**
 * 非登记业务流程，未配置后台返回字符串 'empty'，区分是否已经获取到配置
 */
var fdjywlc = '';
/**
 * 特殊流程，跳转到指定的 url
 */
var tslcList = [];
/**
 * 二审流程初审的名称
 */
var erlcCsmc = "审核"
/**
 * 项目列表是否显示删除按钮
 */
var xm_delete = '';
/**
 * 待办权限配置
 */
var todoPermission = false;
/**
 * 审批来源字典值
 */
var splyzdx = [];

//认领列表数据缓存
var rlTableCache = [];
//待办
var dbTableCache = [];
//已办
var ybTableCache = [];
//项目列表
var xmTableCache = [];
//个人项目列表
var grTableCache = [];
var ywlbXmly = false;

var rwlbSplyZdx =[];
/**
 * 页面版本
 */
var version = '';
var tableData;
var categoryProcess = {};
var categoryProcessDefNames = {};
//回车触发事件状态
var flag = false;
//监听受理编号
var jtslbh = false;
//默认代办事项查询的流程id
var lcid
var authorityCode = '';
authorityLoad();
layui.use(['jquery', 'table', 'element', 'carousel', 'form', 'laytpl', 'laydate', 'layer', 'response', 'workflow', 'moduleAuthority', 'formSelects'], function () {
    var $ = layui.jquery,
        table = layui.table,
        element = layui.element,
        laytpl = layui.laytpl,
        layer = layui.layer,
        laydate = layui.laydate,
        carousel = layui.carousel,
        response = layui.response,
        workflow = layui.workflow,
        moduleAuthority = layui.moduleAuthority,
        formSelects = layui.formSelects;

    formSelects.config('dbSplySelect', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('ybSplySelect', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('rlSplySelect', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('xmSplySelect', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('grSplySelect', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    version=$('#version').val();
    $(function () {
        var $ntTask = $('.bdc-build-task');
        //自定义模糊类型
        var zdymhlx =false;
        //新建任务三级显示与隐藏
        $ntTask.on('click', '.bdc-show-third', function () {
            var $this = $(this);
            if ($this.html() == '展开') {
                $this.html('收起');
            } else {
                $this.html('展开');
            }
            $this.siblings('.bdc-third-rw').toggleClass('bdc-hide');
        });
        $ntTask.on('click', '.bdc-details-second-title', function () {
            var $this = $(this);
            if ($this.siblings('.bdc-show-third').html() == '展开') {
                $this.siblings('.bdc-show-third').html('收起');
            } else {
                $this.siblings('.bdc-show-third').html('展开');
            }
            $this.siblings('.bdc-third-rw').toggleClass('bdc-hide');
        });

        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/task/mrpzx",
            data: {},
            async: false,
            success: function (data) {
                if(data) {
                    todoPermission = data.todoPermission;
                    zdymhlx=data.zdymhlx;
                    ywlbXmly = data.ywlbXmly;
                    rwlbSplyZdx=data.rwlbSplyZdx;
                }
            }, error: function (e) {
                response.fail(e, '');
            }
        });
        //按顺序加载tab
        var tabList;
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/user/order",
            data: {},
            async: false,
            success: function (data) {
                tabList = data;
            }, error: function (e) {
                response.fail(e, '');
            }
        });
        tabList.forEach(function(v){
            var getTpl, rwlblx = "";
            switch (v){
                case "xmContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li id="xmTab">项目列表</li>');
                    getTpl = xmContent.innerHTML;
                    rwlblx = "xm";
                    break;
                case "ybContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li id="ybTab">已办任务</li>');
                    getTpl = ybContent.innerHTML;
                    rwlblx = "yb";
                    break;
                case "dbContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li id="todoTab">待办任务<span class="bdc-list-num-tips"></span></li>');
                    getTpl = dbContent.innerHTML;
                    rwlblx = "db";
                    break;
                case "rlContent":
                    $('.bdc-list-tab .layui-tab-title').append(rlContentLi);
                    getTpl = rlContent.innerHTML;
                    rwlblx = "rl";
                    break;
                case "grContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li id="grTab">个人项目列表</li>');
                    getTpl = grContent.innerHTML;
                    rwlblx = "gr";
                    break;
                case "wwjContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li id="wwjTab">外网件</li>');
                    getTpl = wwjContent.innerHTML;
                    rwlblx = "wwj";
                    break;
                case "wsywContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li id="wsywTab">网上业务</li>');
                    getTpl = wsywContent.innerHTML;
                    rwlblx = "wsyw";
                    break;
                case "wwsqContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li id="wwsqTab">外网申请<span class="bdc-rl-num-tips bdc-wwsq-num-js"></span></li>');
                    getTpl = wwsqContent.innerHTML;
                    rwlblx = "wwsq";
                    break;
                case "dajjdbContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li id="dajjdbTab">档案交接待办</li>');
                    getTpl = dajjdbContent.innerHTML;
                    rwlblx = "dajjdb";
                    break;
                case "dajjybContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li id="dajjybTab">档案交接已办</li>');
                    getTpl = dajjybContent.innerHTML;
                    rwlblx = "dajjyb";
                    break;
                case "dajjrlContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li id="dajjrlTab">档案交接认领</li>');
                    getTpl = dajjrlContent.innerHTML;
                    rwlblx = "dajjrl";
                    break;
                case "fcwqrlContent":
                    $('.bdc-list-tab .layui-tab-title').append('<li id="fcwqrlTab">房产网签认领</li>');
                    getTpl = fcwqrlContent.innerHTML;
                    rwlblx = "fcwqrl";
                    break;

            }
            laytpl(getTpl).render([], function(html){
                $('.bdc-list-tab .layui-tab-content').append(html);
            });

            try {
                if (renderPlcx && typeof (renderPlcx) == "function" && !isNullOrEmpty(rwlblx)) {
                    renderPlcx(rwlblx);
                }

                if (resetFun && typeof (resetFun) == "function" && !isNullOrEmpty(rwlblx)) {
                    resetFun(rwlblx);
                }
            } catch (e) {
                //
            }
            if(zdymhlx ===true) {
                resetMhlx(rwlblx);
            }
        });

        // 模块编码
        var $paramArr = getIpHz();
        var moduleCode = $paramArr['moduleCode'];
        if(isNotBlank(moduleCode)){
            setPortalTabByModuleAuthority(moduleCode);
        }

        $('.bdc-list-tab .layui-tab-title li:first-child').addClass('layui-this');
        $('.bdc-list-tab .layui-tab-content .layui-tab-item:first-child').addClass('layui-show');

        if(lcPageEdition == 'hf'){
            //合肥默认加载节点下拉框
            var $jdmcDom = $('.bdc-jdmc');
            queryTaskNameList($jdmcDom,'',true);
            var $djyyDom = $('.bdc-djyy');
            queryDjyyList('',true,$djyyDom);
            $('.bdc-reset').on('click',function(){
                if($(this).data('zt') != 'rl'){
                    queryTaskNameList($(this).parents('.bdc-search-box').find('.bdc-jdmc'),'',false);
                }
                queryDjyyList('',false,$(this).parents('.bdc-search-box').find('.bdc-djyy'));
            });
        }
        if (lcPageEdition == 'bengbu' ||lcPageEdition =='yancheng') {
            // 蚌埠新增节点名称查询条件
            var $jdmcDom = $('.bdc-jdmc');
            queryTaskNameList($jdmcDom, '', true);
            $('.bdc-reset').on('click', function () {
                if ($(this).data('zt') != 'rl') {
                    queryTaskNameList($(this).parents('.bdc-search-box').find('.bdc-jdmc'), '', false);
                }
            });
        }
        // 初始化日期控件
        if (lcPageEdition == 'hf') {
            loadDateHf();
        } else {
            loadDate();
        }

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

                var grkssjdy = laydate.render({
                    elem: '#grkssjdy',
                    type: 'datetime',
                    trigger: 'click',
                    done: function (value, date) {
                        //选择的结束时间大
                        if ($('#grkssjxy').val() != '' && !completeDate($('#grkssjxy').val(), value)) {
                            $('#grkssjxy').val('');
                            $('.laydate-disabled.layui-this').removeClass('layui-this');
                        }
                        grkssjxy.config.min = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date,
                            hours: date.hours,
                            minutes: date.minutes,
                            seconds: date.seconds
                        }
                    }
                });
                var grkssjxy = laydate.render({
                    elem: '#grkssjxy',
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

                var wwsqkssjdy = laydate.render({
                    elem: '#wwsqkssjdy',
                    type: 'datetime',
                    trigger: 'click',
                    done: function (value, date) {
                        //选择的结束时间大
                        if ($('#wwsqkssjxy').val() != '' && !completeDate($('#wwsqkssjxy').val(), value)) {
                            $('#wwsqkssjxy').val('');
                            $('.laydate-disabled.layui-this').removeClass('layui-this');
                        }
                        wwsqkssjxy.config.min = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date,
                            hours: date.hours,
                            minutes: date.minutes,
                            seconds: date.seconds
                        }
                    }
                });
                var wwsqkssjxy = laydate.render({
                    elem: '#wwsqkssjxy',
                    type: 'datetime',
                    trigger: 'click'
                });

                if (lcPageEdition == 'nt') {
                    var cnlzkssjdy = laydate.render({
                        elem: '#cnlzkssjdy',
                        trigger: 'click',
                        done: function (value, date) {
                            //选择的结束时间大
                            if ($('#cnlzkssjxy').val() != '' && !completeDate($('#cnlzkssjxy').val(), value)) {
                                $('#cnlzkssjxy').val('');
                                $('.laydate-disabled.layui-this').removeClass('layui-this');
                            }
                            cnlzkssjxy.config.min = {
                                year: date.year,
                                month: date.month - 1,
                                date: date.date
                            }
                        }
                    });
                    var cnlzkssjxy = laydate.render({
                        elem: '#cnlzkssjxy',
                        trigger: 'click'
                    });

                    var ybcnlzkssjdy = laydate.render({
                        elem: '#ybcnlzkssjdy',
                        trigger: 'click',
                        done: function (value, date) {
                            //选择的结束时间大
                            if ($('#cnlzkssjxy').val() != '' && !completeDate($('#cnlzkssjxy').val(), value)) {
                                $('#cnlzkssjxy').val('');
                                $('.laydate-disabled.layui-this').removeClass('layui-this');
                            }
                            ybcnlzkssjxy.config.min = {
                                year: date.year,
                                month: date.month - 1,
                                date: date.date
                            }
                        }
                    });
                    var ybcnlzkssjxy = laydate.render({
                        elem: '#ybcnlzkssjxy',
                        trigger: 'click'
                    });

                    var xmcnlzkssjdy = laydate.render({
                        elem: '#xmcnlzkssjdy',
                        trigger: 'click',
                        done: function (value, date) {
                            //选择的结束时间大
                            if ($('#cnlzkssjxy').val() != '' && !completeDate($('#cnlzkssjxy').val(), value)) {
                                $('#cnlzkssjxy').val('');
                                $('.laydate-disabled.layui-this').removeClass('layui-this');
                            }
                            xmcnlzkssjxy.config.min = {
                                year: date.year,
                                month: date.month - 1,
                                date: date.date
                            }
                        }
                    });
                    var xmcnlzkssjxy = laydate.render({
                        elem: '#xmcnlzkssjxy',
                        trigger: 'click'
                    });

                    var rlcnlzkssjdy = laydate.render({
                        elem: '#rlcnlzkssjdy',
                        trigger: 'click',
                        done: function (value, date) {
                            //选择的结束时间大
                            if ($('#cnlzkssjxy').val() != '' && !completeDate($('#cnlzkssjxy').val(), value)) {
                                $('#cnlzkssjxy').val('');
                                $('.laydate-disabled.layui-this').removeClass('layui-this');
                            }
                            rlcnlzkssjxy.config.min = {
                                year: date.year,
                                month: date.month - 1,
                                date: date.date
                            }
                        }
                    });
                    var rlcnlzkssjxy = laydate.render({
                        elem: '#rlcnlzkssjxy',
                        trigger: 'click'
                    });

                    var wwsqcnlzkssjdy = laydate.render({
                        elem: '#wwsqcnlzkssjdy',
                        trigger: 'click',
                        done: function (value, date) {
                            //选择的结束时间大
                            if ($('#wwsqcnlzkssjxy').val() != '' && !completeDate($('#wwsqcnlzkssjxy').val(), value)) {
                                $('#wwsqcnlzkssjxy').val('');
                                $('.laydate-disabled.layui-this').removeClass('layui-this');
                            }
                            wwsqcnlzkssjxy.config.min = {
                                year: date.year,
                                month: date.month - 1,
                                date: date.date
                            }
                        }
                    });
                    var wwsqcnlzkssjxy = laydate.render({
                        elem: '#wwsqcnlzkssjxy',
                        trigger: 'click'
                    });
                }

                if (lcPageEdition == 'changzhou') {
                    var dajjrlkssjdy = laydate.render({
                        elem: '#dajjrlkssjdy',
                        type: 'datetime',
                        trigger: 'click',
                        done: function (value, date) {
                            //选择的结束时间大
                            if ($('#dajjrlkssjxy').val() != '' && !completeDate($('#dajjrlkssjxy').val(), value)) {
                                $('#dajjrlkssjxy').val('');
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
                    var dajjrlkssjxy = laydate.render({
                        elem: '#dajjrlkssjxy',
                        type: 'datetime',
                        trigger: 'click'
                    });

                    var dajjybkssjdy = laydate.render({
                        elem: '#dajjybkssjdy',
                        type: 'datetime',
                        trigger: 'click',
                        done: function (value, date) {
                            //选择的结束时间大
                            if ($('#dajjybkssjxy').val() != '' && !completeDate($('#dajjybkssjxy').val(), value)) {
                                $('#dajjybkssjxy').val('');
                                $('.laydate-disabled.layui-this').removeClass('layui-this');
                            }
                            dajjybkssjxy.config.min = {
                                year: date.year,
                                month: date.month - 1,
                                date: date.date,
                                hours: date.hours,
                                minutes: date.minutes,
                                seconds: date.seconds
                            }
                        }
                    });
                    var dajjybkssjxy = laydate.render({
                        elem: '#dajjybkssjxy',
                        type: 'datetime',
                        trigger: 'click'
                    });
                }

                if (lcPageEdition == 'bengbu') {
                    var fcwqrlkssjdy = laydate.render({
                        elem: '#fcwqrlkssjdy',
                        type: 'datetime',
                        trigger: 'click',
                        done: function (value, date) {
                            //选择的结束时间大
                            if ($('#fcwqrlkssjxy').val() != '' && !completeDate($('#fcwqrlkssjxy').val(), value)) {
                                $('#fcwqrlkssjxy').val('');
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
                    var fcwqrlkssjxy = laydate.render({
                        elem: '#fcwqrlkssjxy',
                        type: 'datetime',
                        trigger: 'click'
                    });
                }
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

        var clientId = $paramArr['clientId'];
        // 加载登记首页
        var loadHome = $paramArr['loadHome'];
        var viewType = $paramArr['viewType'];

        //按钮权限
        if (isNullOrEmpty(authorityCode)) {
            authorityLoad(moduleAuthorityLoad(authorityCode));
        } else {
            moduleAuthority.load({
                authorityCode: authorityCode
            });
        }

        //监听台账查询 input 点击
        $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
            $(this).siblings('.layui-input').val('');
        });

        $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
            $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
        }).on('blur', function () {
            var $this = $(this);
            setTimeout(function () {
                $this.siblings('.layui-icon-close').addClass('bdc-hide');
            }, 150)
        });

        //4. 轮播图
        //4.0 渲染常用收藏内容
        var commonConnection = false;
        var connectionList = false;
        //监听收藏tab切
        var taskTabClick = 0;
        var taskTabListClick = 0;
        //监听收藏任务tab切换
        element.on('tab(task)', function (data) {
            if (taskTabClick == 0 && data.index == 0) {
                taskTabClick++;
                renderCommonCollection();
            }
            if (taskTabListClick == 0 && data.index == 1) {
                taskTabListClick++;
                renderLbList();
            }
            if ($('.bdc-task-content').hasClass('bdc-hide')) {
                $('.bdc-operate-show>.layui-icon').toggleClass('bdc-hide');
                $('.bdc-task-content').toggleClass('bdc-hide');
                // $('.bdc-task-search-box').toggleClass('bdc-hide');
            }

            switch (data.index) {
                case 0:
                    if (connectionList) {
                        connectionList = false;
                        renderCommonCollection();
                    }
                    break;
                case 1:
                    if (commonConnection) {
                        commonConnection = false;
                        renderLbList();
                    }
                    break;
            }

            changeTableHeight();
        });
        //renderCommonCollection();

        var hasMove = false, isStarClick = false;
        function renderCommonCollection() {
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/collect",
                dataType: "json",
                contentType: "application/json",
                data: {},
                success: function (data) {
                    var getCollectionTpl = collectionTpl.innerHTML
                        , collectionView = document.getElementById('collectionView');
                    laytpl(getCollectionTpl).render(data, function (html) {
                        collectionView.innerHTML = html;
                    });
                    //收藏页面渲染后页面会重新刷新。判断之前的展开状态进行再次展开
                    if(isShowMoreCellection){
                        $('.bdc-show-more-collection span').click()
                        $('.bdc-show-more-collection span').html('收起+');
                    }
                    changeTableHeight();

                    var oUl = document.getElementById("collectionContent");
                    if (!isNullOrEmpty(oUl)) {
                        var aLi = oUl.getElementsByTagName("li");
                        var disX = 0;
                        var disY = 0;
                        var minZindex = 1;
                        var aPos = [];
                        for (var i = 0; i < aLi.length; i++) {
                            var t = aLi[i].offsetTop;
                            var l = aLi[i].offsetLeft;
                            aLi[i].style.top = t + "px";
                            aLi[i].style.left = l + "px";
                            aPos[i] = {left: l, top: t};
                            aLi[i].index = i;
                        }
                        for (var i = 0; i < aLi.length; i++) {
                            aLi[i].style.position = "absolute";
                            aLi[i].style.margin = 0;
                            setDrag(aLi[i]);
                        }
                    }
                    var moveL = 99999;
                    var moveT = 99999;
                    var objL = 0;
                    var objT = 0;

                    function setDrag(obj) {
                        obj.onmousedown = function (event) {
                            hasMove = false;
                            var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
                            var scrollLeft = document.documentElement.scrollLeft || document.body.scrollLeft;
                            obj.style.zIndex = minZindex++;
                            //当鼠标按下时计算鼠标与拖拽对象的距离
                            disX = event.clientX + scrollLeft - obj.offsetLeft;
                            disY = event.clientY + scrollTop - obj.offsetTop;

                            objL = obj.style.left;
                            objL = objL.substring(0, objL.length - 2);

                            objT = obj.style.top;
                            objT = objT.substring(0, objT.length - 2);
                            document.onmousemove = function (event) {
                                hasMove = true;
                                //当鼠标拖动时计算div的位置
                                var l = event.clientX - disX + scrollLeft;
                                var t = event.clientY - disY + scrollTop;
                                obj.style.left = l + "px";
                                obj.style.top = t + "px";
                                // console.log('l:'+l+'t:'+t);

                                moveL = event.clientX - disX + scrollLeft;
                                // console.log(moveL);
                                moveT = event.clientY - disY + scrollTop;
                                for (var i = 0; i < aLi.length; i++) {
                                    aLi[i].className = "";
                                }
                                var oNear = findMin(obj);
                                if (oNear) {
                                    oNear.className = "active";
                                }
                            };
                            document.onmouseup = function () {
                                document.onmousemove = null;//当鼠标弹起时移出移动事件
                                document.onmouseup = null;//移出up事件，清空内存
                                if (hasMove) {
                                    //console.log('moveL:'+moveL,"objL:"+objL);
                                    //console.log('moveT:'+moveT,"objT:"+objT);
                                    if (((moveL != 99999 || moveT != 99999) && (moveL != objL && moveL > objL + 10)) || ((moveL != 99999 || moveT != 99999) && (moveT != objT && moveT > objT - 10)) || ((moveL != 99999 || moveT != 99999) && (moveT != objT && moveT < objT + 10)) || ((moveL != 99999 || moveT != 99999) && (moveL != objL && moveL < objL - 10))) {
                                        //console.log('aaa');
                                        //检测是否普碰上，在交换位置
                                        var oNear = findMin(obj);
                                        if (oNear) {
                                            //console.log(oNear);//被移动的
                                            //console.log(obj);//移动的
                                            moveCollection(oNear, obj);
                                            oNear.className = "";
                                            oNear.style.zIndex = minZindex++;
                                            obj.style.zIndex = minZindex++;
                                            startMove(oNear, aPos[obj.index]);
                                            startMove(obj, aPos[oNear.index]);
                                            //交换index
                                            oNear.index += obj.index;
                                            obj.index = oNear.index - obj.index;
                                            oNear.index = oNear.index - obj.index;
                                        } else {

                                            startMove(obj, aPos[obj.index]);
                                        }
                                    } else {
                                        obj.style.left = objL + "px";
                                        obj.style.top = objT + "px";
                                        $('#collectionContent').find('li').removeClass('active');
                                    }
                                } else {
                                    //console.log("没有移动鼠标松开事件,模拟click");
                                    setTimeout(function () {
                                        if (!isStarClick) {
                                            var useData = {
                                                processDefKey: $(obj).find('a .layui-icon').data('code'),
                                                name: $(obj).find('a .layui-icon').data('name')
                                            };
                                            startUpProcess(useData);
                                        }
                                        isStarClick = false;
                                    },200);
                                }
                                hasMove = false;
                            };
                            clearInterval(obj.timer);
                            return false;//低版本出现禁止符号
                        }
                    }
                    //碰撞检测
                    function colTest(obj1, obj2) {
                        var t1 = obj1.offsetTop;
                        var r1 = obj1.offsetWidth + obj1.offsetLeft;
                        var b1 = obj1.offsetHeight + obj1.offsetTop;
                        var l1 = obj1.offsetLeft;

                        var t2 = obj2.offsetTop;
                        var r2 = obj2.offsetWidth + obj2.offsetLeft;
                        var b2 = obj2.offsetHeight + obj2.offsetTop;
                        var l2 = obj2.offsetLeft;

                        if (t1 > b2 || r1 < l2 || b1 < t2 || l1 > r2) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                    //勾股定理求距离
                    function getDis(obj1, obj2) {
                        var a = obj1.offsetLeft - obj2.offsetLeft;
                        var b = obj1.offsetTop - obj2.offsetTop;
                        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
                    }
                    //找到距离最近的
                    function findMin(obj) {
                        var minDis = 999999999;
                        var minIndex = -1;
                        for (var i = 0; i < aLi.length; i++) {
                            if (obj == aLi[i]) continue;
                            if (colTest(obj, aLi[i])) {
                                var dis = getDis(obj, aLi[i]);
                                if (dis < minDis) {
                                    minDis = dis;
                                    minIndex = i;
                                }
                            }
                        }
                        if (minIndex == -1) {
                            return null;
                        } else {
                            return aLi[minIndex];
                        }
                    }
                },
                error: function (e) {
                    response.fail(e,'');
                }
            });
        }

        /**
         * 拖拽收藏
         * @param target 目标位置上原有的对象
         * @param moved 移动的对象
         */
        function moveCollection(target, moved) {
            target = target.dataset;
            moved = moved.dataset;
            console.info(target.sequencenumber);
            console.info(moved.sequencenumber);
            $.ajax({
                type: "PUT",
                url: getContextPath() + "/rest/v1.0/collect/update",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify([
                    {
                        "id": target.id,
                        "code": target.code,
                        "name": target.name,
                        "content": target.content,
                        "type": target.type,
                        "typeName": target.typename,
                        "sequenceNumber": target.sequencenumber,
                        "createAt": target.createat
                    },
                    {
                        "id": moved.id,
                        "code": moved.code,
                        "name": moved.name,
                        "content": moved.content,
                        "type": moved.type,
                        "typeName": moved.typename,
                        "sequenceNumber": moved.sequencenumber,
                        "createAt": moved.createat
                    }]
                ),
                success: function (data) {
                    renderCommonCollection();
                },
                error: function (e) {
                    response.fail(e,'');
                }
            });
        }

        //监听常用收藏中的收起，展示
        var isShowMoreCellection = false;
        $('.bdc-task-content').on('click', '.bdc-show-more-collection span', function () {
            isShowMoreCellection = !isShowMoreCellection;
            $('.bdc-show-more-collection span').toggleClass('bdc-hide');
            if (isShowMoreCellection) {
                // 点击展示
                var newHeight = Math.ceil($('.bdc-collection-content li').length / 4) * 36;
                $('.bdc-collection-content').css("height", newHeight + 'px');
            } else {
                // 点击收起
                $('.bdc-collection-content').css('height', '72px');
            }
        });

        //4.0.1 点击收藏中的星星图标，取消收藏
        $('.bdc-collection-tab').on('click', '.bdc-collection-content .layui-icon-rate-solid', function (e) {
            e.stopPropagation();
            e.preventDefault();
            isStarClick = true;
            commonConnection = true;
            var code = $(this).data('code');
            $.ajax({
                type: "post",
                url: getContextPath() + "/rest/v1.0/collect",
                data: {
                    _method: "DELETE",
                    "code": code
                },
                success: function (data) {
                    layer.msg('取消收藏成功');
                    renderCommonCollection();
                }, error: function (e) {
                    response.fail(e,'');
                }
            });
        });

        //4.1 轮播图 内容、一屏展示几个，可配置
        //默认一屏展示5个，大数组里面嵌套数组，展示几个，嵌套数组中存储几条数据
        renderLbList();

        function renderLbList() {
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/task/list",
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
                        //console.log(carouselData);
                        if (data.three && data.xjrwsjcdky) {
                            var getCarouselTpl = carouselThreeTpl.innerHTML
                                , getCarouselView = document.getElementById('carouselView');
                            laytpl(getCarouselTpl).render(carouselData, function (html) {
                                getCarouselView.innerHTML = html;
                            });
                        } else {
                            var getCarouselTpl = carouselTpl.innerHTML
                                , getCarouselView = document.getElementById('carouselView');
                            laytpl(getCarouselTpl).render(carouselData, function (html) {
                                getCarouselView.innerHTML = html;
                            });
                        }
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

        //4.2 轮播图内鼠标覆盖事件
        var $carouselTask = $('#buildTask');

        //4.3 轮播图内单击事件
        var $buildTask = $('.bdc-build-task');
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
        //4.4 控制tab切内容显示与隐藏
        var taskSearchState = true;//在tab展示的时候点击的查询
        $('.bdc-operate-show>.layui-icon').on('click', function () {
            if($('.bdc-search-content').hasClass('bdc-hide')){
                $('.bdc-operate-show>.layui-icon').toggleClass('bdc-hide');
                $('.bdc-task-content').toggleClass('bdc-hide');

                if(taskTabClick == 0){
                    if($('.bdc-task-tab .layui-tab-title li:first-child').hasClass('layui-this')){
                        renderCommonCollection();
                    }else {
                        renderLbList();
                    }
                }

                if($('.bdc-task-tab .layui-tab-title li:first-child').hasClass('layui-this')){
                    if (connectionList) {
                        connectionList = false;
                        renderCommonCollection();
                    }
                }else {
                    if (commonConnection) {
                        commonConnection = false;
                        renderLbList();
                    }
                }
            }else {
                $('.bdc-task-tab .bdc-task-tools').css('width',taskToolsWidth);
                $('.bdc-task-tab .layui-tab-title li').css('visibility','visible');

                $('.bdc-task-content').addClass('bdc-hide');
                $('.bdc-operate-show>.layui-icon').toggleClass('bdc-hide');
                $('.bdc-search-content').addClass('bdc-hide');
            }
            changeTableHeight();
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
        //4.5.2 ☆ 图标显示与隐藏
        var $buildCollectionTask = $('#collectionView');
        var $buildTaskTab = $('.bdc-task-tab');
        var $searchContent = $('.bdc-search-content');
        $buildTaskTab.on('mouseenter', '.bdc-details-type-content>a', function () {
            if (!$(this).find('.layui-icon').hasClass('layui-icon-rate-solid')) {
                $(this).find('.layui-icon').toggleClass('bdc-visible');
            }
        }).on('mouseleave', '.bdc-details-type-content>a', function () {
            if (!$(this).find('.layui-icon').hasClass('layui-icon-rate-solid')) {
                $(this).find('.layui-icon').toggleClass('bdc-visible');
            }
        });
        //4.5.3 单击☆图标，收藏或取消收藏
        $buildTask.on('click', '.bdc-details-type-content>a>.layui-icon', function (event) {
            event.stopPropagation();
            connectionList = true;
            var _this = $(this);
            var type = $(this).data('type');
            var code = $(this).data('code');
            var name = $(this).data('name');
            var id = $(this).data('id');
            var date = (new Date()).Format("yyyy-MM-dd hh:mm:ss.S");
            if ($(this).hasClass('layui-icon-rate')) {
                //未收藏，收藏
                $.ajax({
                    type: "PUT",
                    url: getContextPath() + "/rest/v1.0/collect/user",
                    dataType: "json",
                    contentType: "application/json",
                    data: JSON.stringify({
                        "code": code,
                        "content": "",
                        "createAt": date,
                        "id": id,
                        "name": name,
                        "typeName": name
                    }),
                    success: function (data) {
                        layer.msg('收藏成功');
                        _this.removeClass('layui-icon-rate bdc-visible').addClass('layui-icon-rate-solid');
                    }, error: function (e) {
                        response.fail(e,'');
                    }
                });
            } else {
                //已收藏 取消收藏
                $.ajax({
                    type: "post",
                    url: getContextPath() + "/rest/v1.0/collect",
                    data: {
                        _method: "DELETE",
                        "code": code
                    },
                    success: function (data) {
                        // console.log(data);
                        layer.msg('取消收藏成功');
                        _this.removeClass('layui-icon-rate-solid bdc-visible').addClass('layui-icon-rate');
                    }, error: function (e) {
                        response.fail(e,'');
                    }
                });
            }
        });
        $searchContent.on('click', '.bdc-details-type-content>a', function () {
            var useData = {
                processDefKey: $(this).find('.layui-icon').data('code'),
                name: $(this).find('.layui-icon').data('name')
            };
            startUpProcess(useData);
        });
        $searchContent.on('click', '.bdc-details-type-content>a>.layui-icon', function (e) {
            e.stopPropagation();
            commonConnection = true;
            connectionList = true;
            var _this = $(this);
            var type = $(this).data('type');
            var code = $(this).data('code');
            var name = $(this).data('name');
            var id = $(this).data('id');
            var date = (new Date()).Format("yyyy-MM-dd hh:mm:ss.S");
            if ($(this).hasClass('layui-icon-rate')) {
                //未收藏，收藏
                $.ajax({
                    type: "PUT",
                    url: getContextPath() + "/rest/v1.0/collect/user",
                    dataType: "json",
                    contentType: "application/json",
                    data: JSON.stringify({
                        "code": code,
                        "content": "",
                        "createAt": date,
                        "id": id,
                        "name": name,
                        "typeName": name
                    }),
                    success: function (data) {
                        // console.log(data);
                        layer.msg('收藏成功');
                        _this.removeClass('layui-icon-rate bdc-visible').addClass('layui-icon-rate-solid');
                    }, error: function (e) {
                        response.fail(e,'');
                    }
                });
            } else {
                //已收藏 取消收藏
                $.ajax({
                    type: "post",
                    url: getContextPath() + "/rest/v1.0/collect",
                    data: {
                        _method: "DELETE",
                        "code": code
                    },
                    success: function (data) {
                        layer.msg('取消收藏成功');
                        _this.removeClass('layui-icon-rate-solid bdc-visible').addClass('layui-icon-rate');
                    }, error: function (e) {
                        response.fail(e,'');
                    }
                });
            }
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
            // 如果 fdjywlc 为空，则获取配置信息 （未配置默认返回 'empty' 字符串）
            if (isNullOrEmpty(fdjywlc) || tslcList == null) {
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/workflow/process-instances/getFdjywlc",
                    data: {},
                    async: false,
                    success: function (data) {
                        fdjywlc = data.fdjywlc;
                        tslcList = data.tslcList;
                    }, error: function (e) {
                        response.fail(e, '');
                    }
                });
            }
            // 有值则不去后台获取
            var fdjywlcArray = fdjywlc.split(",");
            if(fdjywlcArray.indexOf(useData.processDefKey)>-1){
                workflow.startUpFdjywProcess(useData);
            } else {
                var flag = "";
                // 特殊流程不为空
                if (!isNullOrEmpty(tslcList)) {
                    $.each(tslcList, function (k, v) {
                        if (v.processDefKey == useData.processDefKey) {
                            flag = "true";
                            workflow.startUpTslcProcess(useData.processDefKey, v.url);
                        }
                    });
                }
               if (isNullOrEmpty(flag)) {
                   workflow.startUpProcess(useData);
               }
            }
        }

        // $buildCollectionTask.on('click', '.bdc-collection-content>li>a', function () {
        //     var useData = {
        //         processDefKey: $(this).find('.layui-icon').data('code'),
        //         name: $(this).find('.layui-icon').data('name')
        //     };
        //     startUpProcess(useData);
        // });

        //4.5.6 点击搜索 input框
        $('.bdc-task-search-box .layui-input').on('focus', function () {
            if ($('.bdc-task-content').hasClass('bdc-hide')) {
                taskSearchState = false;
                $('.bdc-operate-show .layui-icon').toggleClass('bdc-hide');
            }

            $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
            $('.bdc-task-tab .bdc-task-tools').animate({'width': taskToolsClickWidth});
            $('.bdc-task-tab>.layui-tab-title>li').css('visibility', 'hidden');
            $('.bdc-task-content').addClass('bdc-hide');
            $('.bdc-search-content').removeClass('bdc-hide');

            changeTableHeight();
        });

        //收藏监听回车事件
        var isSearch = true;
        $('.bdc-task-search-box .layui-input').bind('keyup', function (event) {
            if (event.keyCode == "13") {
                //回车执行查询
                $('.bdc-search-content').html('');
                var inputText = $(this).val();
                if (!isNullOrEmpty(inputText)) {
                    renderSearchList(inputText);
                }
            }
        });
        $('.bdc-task-search-box .layui-input').on('focus', function () {
            isSearch = false;
        }).on('blur', function () {
            isSearch = true;
        });
        $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });
        //监听受理编号
        $('.bdc-search-db').on('focus', '.slbh', function () {
            jtslbh = true;
        }).on('blur', '.slbh', function () {
            jtslbh = false;
        });

        //表格监听回车事件
        // $('.layui-input-inline .layui-input').bind('keyup', function (event) {
        //     if (event.keyCode == "13") {
        //         $(".layui-show .bdc-search-box .searchBtn").click();
        //     }
        // });
        var rlLayerIndex;
        $(document).keydown(function (event) {
            if (event.keyCode == "13") {
                if (isSearch) {
                    if($('.bdc-zf-tips').length > 0){
                        $('.layui-layer-btn0').click();
                        layer.close(rlLayerIndex);
                    }else {
                        flag = true;
                        $(".layui-show .bdc-search-box .searchBtn").click();
                    }
                }
            }
        });

        //监听上一个下一个
        var upTime;
        $('.bdc-content-box').on('mousewheel DOMMouseScroll', '.bdc-build-task', function (e) {
            clearInterval(upTime);
            var delta = -e.originalEvent.wheelDelta || e.originalEvent.detail;//firefox使用detail:下3上-3,其他浏览器使用wheelDelta:下-120上120//下滚
            if (delta > 0) {
                upTime = setTimeout(function () {
                    //console.log('下滚');
                    $('.layui-carousel-arrow[lay-type=add]').click();
                }, 300);
            }
            //上滚
            if (delta < 0) {
                upTime = setTimeout(function () {
                    //console.log('上滚');
                    $('.layui-carousel-arrow[lay-type=sub]').click();
                }, 300);
            }
        });


        //监听 搜索
        /*$('.bdc-task-search-box .layui-input').bind('input propertychange', function (event) {
         var inputText = $(this).val();
         if (inputText != '' && inputText != null) {
         renderSearchList(inputText);
         }
         });*/

        //4.5.7 点击搜索框的删除
        $('.bdc-task-search-box .layui-icon-close').on('click', function () {
            $('.bdc-task-search-box .layui-input').val('');
            $(this).addClass('bdc-hide');
            $('.bdc-search-content').addClass('bdc-hide').html('');

            if (!($('.bdc-task-tools').css('width') == taskToolsWidth)) {
                $('.bdc-task-tab .bdc-task-tools').animate({'width': taskToolsWidth});
                $('.bdc-task-tab>.layui-tab-title>li').css('visibility', 'visible');
                if (taskSearchState) {
                    $('.bdc-task-content').removeClass('bdc-hide');
                } else {
                    $('.bdc-operate-show .layui-icon').toggleClass('bdc-hide');
                }
            }

            var $collectionTabHtml = $('.bdc-task-tab .layui-this').html();
            if (commonConnection && $collectionTabHtml == '新建任务') {
                commonConnection = false;
                renderLbList();
            }
            if (connectionList && $collectionTabHtml == '常用收藏') {
                connectionList = false;
                renderCommonCollection();
            }

            changeTableHeight();
        });


        //6. 渲染表格
        // 6.1 待办表格
        var waitUrl = getContextPath() + "/rest/v1.0/task/todo";
        var waitTableId = '#waitTable';
        var waitCurrentId = 'dbTable';
        var waitToolbar = '#toolbarDemo';
        renderWaitTable(waitUrl, waitTableId, waitCurrentId, waitToolbar);

        // 6.2 已办表格
        var doneUrl = getContextPath() + "/rest/v1.0/task/complete";
        var doneTableId = '#doneTable';
        var doneCurrentId = 'doneTableId';
        var doneToolbar = '#toolbarDoneDemo';
        renderDoneTable(doneTableId, doneCurrentId, doneToolbar);
        // 6.3 项目列表表格
        var listUrl = getContextPath() + "/rest/v1.0/task/all";
        var listTableId = '#listTable';
        var listCurrentId = 'listTableId';
        var allToolbar = '#toolbarAllDemo';
        renderListTable(listTableId, listCurrentId, allToolbar);
        // 6.4 认领列表表格
        var rlUrl = getContextPath() + "/rest/v1.0/task/claim/list";
        var rlTableId = '#rlTable';
        var rlCurrentId = 'rlTableId';
        var rlTableToolbar = '#rlTableToolbar';
        renderRlTable(rlTableId, rlCurrentId, rlTableToolbar);
        // 6.5 个人项目列表表格
        var grListUrl = getContextPath() + "/rest/v1.0/task/person";
        var grListTableId = '#grListTable';
        var grListCurrentId = 'grListTableId';
        var grToolbar = '#toolbarAllDemo';
        renderGrListTable(grListTableId, grListCurrentId, grToolbar);
        // 6.6 外网件列表
        var wwjUrl = getContextPath() + "/rest/v1.0/task/ww";
        var wwjTableId = '#wwjTable';
        var wwjCurrentId = 'wwjTableId';
        var wwjToolbar = '#wwjTableToolbar';
        if (lcPageEdition == "bengbu") {
            renderWwjTable(wwjTableId, wwjCurrentId, wwjToolbar);
        }
        // 6.7 外网申请
        var wwsqUrl = getContextPath() + "/rest/v1.0/task/claim/wwsq";
        var wwsqTableId = '#wwsqTable';
        var wwsqCurrentId = 'wwsqTableId';
        var wwsqTableToolbar = '#rlTableToolbar';
        if (lcPageEdition == 'nt') {
            renderWwsqTable(wwsqUrl, wwsqTableId, wwsqCurrentId, wwsqTableToolbar);
        }
        // 6.8 线上业务
        var wsywUrl = getContextPath() + "/rest/v1.0/task/claim/wsyw";
        var wsywTableId = '#wsywTable';
        var wsywCurrentId = 'wsywTableId';
        var wsywTableToolbar = '#wsywTableToolbar';
        if (lcPageEdition == 'yancheng') {
            renderWsywTable(wsywTableId, wsywCurrentId, wsywTableToolbar);
        }
        // 6.9 档案交接待办
        var dajjdbUrl = getContextPath() + "/rest/v1.0/task/todo";
        var dajjdbTableId = '#dajjdbTable';
        var dajjdbCurrentId = 'dajjdbTable';
        var dajjdbToolbar = '#dajjdbTableToolbar';
        if (lcPageEdition == 'changzhou') {
            renderDajjDbTable(dajjdbUrl, dajjdbTableId, dajjdbCurrentId, dajjdbToolbar);
        }
        // 6.10 档案交接已办
        var dajjybUrl = getContextPath() + "/rest/v1.0/task/complete";
        var dajjybTableId = '#dajjybTable';
        var dajjybCurrentId = 'dajjybTable';
        var dajjybToolbar = '#dajjybTableToolbar';
        if (lcPageEdition == 'changzhou') {
            renderDajjYbTable(dajjybUrl, dajjybTableId, dajjybCurrentId, dajjybToolbar);
        }
        // 6.11 档案交接认领
        var dajjrlUrl = getContextPath() + "/rest/v1.0/task/claim/list";
        var dajjrlTableId = '#dajjrlTable';
        var dajjrlCurrentId = 'dajjrlTable';
        var dajjrlToolbar = '#dajjrlTableToolbar';
        if (lcPageEdition == 'changzhou') {
            renderDajjRlTable(dajjrlUrl, dajjrlTableId, dajjrlCurrentId, dajjrlToolbar);
        }

        //  房产网签认领列表表格
        var fcwqrlUrl = getContextPath() + "/rest/v1.0/task/claim/fcwq";
        var fcwqrlTableId = '#fcwqrlTable';
        var fcwqrlCurrentId = 'fcwqrlTableId';
        var fcwqrlTableToolbar = '#fcwqrlTableToolbar';
        if (lcPageEdition == 'hf' || lcPageEdition == 'bengbu') {
            renderFcwqRlTable(fcwqrlTableId, fcwqrlCurrentId, fcwqrlTableToolbar);
        }

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
            case "wwjContent":
                refreshTab(wwjUrl, wwjCurrentId, authorityCode);
                break;
            case "wwsqContent":
                refreshTab(wwsqUrl, wwsqCurrentId, authorityCode);
                break;
            case "wsywContent":
                refreshTab(wsywUrl, wsywCurrentId, authorityCode);
                break;
            case "dajjdbContent":
                refreshTab(dajjdbUrl, dajjdbCurrentId, authorityCode);
                break;
            case "dajjybContent":
                refreshTab(dajjybUrl, dajjybCurrentId, authorityCode);
                break;
            case "dajjrlContent":
                refreshTab(dajjrlUrl, dajjrlCurrentId, authorityCode);
                break;
            case "fcwqrlContent":
                refreshTab(fcwqrlUrl, fcwqrlCurrentId, authorityCode);
                break;
        }

        var doneIndex = 0,
            listIndex = 0,
            rlindex = 0,
            waitIndex = 0,
            grListIndex = 0,
            wwjIndex = 0,
            wwsqindex = 0,
            wsywindex = 0,
            dajjdbIndex = 0,
            dajjybIndex = 0,
            dajjrlIndex = 0,
            fcwqrlIndex = 0
        ;

        //监听第一次单击任务栏tab，重构表格尺寸
        element.on('tab(listFilter)', function (data) {
            if (data.index == 0) {
                switch (tabList[0]){
                    case "dbContent":
                        var obj = getSearchParam("dbSearch");
                        refreshTab(waitUrl, waitCurrentId, authorityCode, obj);
                        recordLog("", "", "", "待办任务列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "xmContent":
                        table.resize(listCurrentId);
                        var obj = getSearchParam("xmSearch");
                        refreshTab(listUrl, listCurrentId, authorityCode, obj);
                        recordLog("", "", "", "项目列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "ybContent":
                        table.resize(doneCurrentId);
                        var obj = getSearchParam("ybSearch");
                        refreshTab(doneUrl, doneCurrentId, authorityCode, obj);
                        recordLog("", "", "", "已办任务列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "rlContent":
                        table.resize(rlCurrentId);
                        var obj = getSearchParam("rlSearch");
                        refreshTab(rlUrl, rlCurrentId, authorityCode, obj);
                        recordLog("", "", "", "认领任务列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "grContent":
                        var obj = getSearchParam("grSearch");
                        refreshTab(grListUrl, grListCurrentId, authorityCode, obj);
                        recordLog("", "", "", "个人任务列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "wwjContent":
                        var obj = getSearchParam("wwjSearch");
                        refreshTab(wwjUrl, wwjCurrentId, authorityCode, obj);
                        recordLog("", "", "", "wwj列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "wwsqContent":
                        table.resize(wwsqCurrentId);
                        var obj = getSearchParam("wwsqSearch");
                        refreshTab(wwsqUrl, wwsqCurrentId, authorityCode, obj);
                        recordLog("", "", "", "wwsq列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "wsywContent":
                        table.resize(wsywCurrentId);
                        var obj = getSearchParam("wsywSearch");
                        refreshTab(wsywUrl, wsywCurrentId, authorityCode, obj);
                        recordLog("", "", "", "wsyw列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "dajjdbContent":
                        table.resize(dajjdbCurrentId);
                        var obj = getDajjDbSearchParam("dajjdbSearch");
                        refreshTab(dajjdbUrl, dajjdbCurrentId, authorityCode, obj);
                        recordLog("", "", "", "档案交接待办列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "dajjybContent":
                        table.resize(dajjybCurrentId);
                        var obj = getDajjDbSearchParam("dajjybSearch");
                        refreshTab(dajjybUrl, dajjybCurrentId, authorityCode, obj);
                        recordLog("", "", "", "档案交接已办列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "dajjrlContent":
                        table.resize(dajjrlCurrentId);
                        var obj = getDajjRlSearchParam("dajjrlSearch");
                        refreshTab(dajjrlUrl, dajjrlCurrentId, authorityCode, obj);
                        recordLog("", "", "", "档案交接认领列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "fcwqrlContent":
                        table.resize(fcwqrlCurrentId);
                        var obj = getSearchParam("fcwqrlSearch");
                        refreshTab(fcwqrlUrl, fcwqrlCurrentId, authorityCode, obj);
                        recordLog("", "", "", "房产网签认领列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                }
            }else {
                switch (tabList[data.index]){
                    case "dbContent":
                        if (waitIndex == 0) {
                            waitIndex++;
                            table.resize(waitCurrentId);
                        }
                        var obj = getSearchParam("dbSearch");
                        refreshTab(waitUrl, waitCurrentId, authorityCode, obj);
                        recordLog("", "", "", "待办任务列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "xmContent":
                        if (listIndex == 0) {
                            listIndex++;
                            table.resize(listCurrentId);
                        }
                        var obj = getSearchParam("xmSearch");
                        refreshTab(listUrl, listCurrentId, authorityCode, obj);
                        recordLog("", "", "", "项目列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "ybContent":
                        if (doneIndex == 0) {
                            doneIndex++;
                            table.resize(doneCurrentId);
                        }
                        var obj = getSearchParam("ybSearch");
                        refreshTab(doneUrl, doneCurrentId, authorityCode, obj);
                        recordLog("", "", "", "已办任务列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "rlContent":
                        if (rlindex == 0) {
                            rlindex++;
                            table.resize(rlCurrentId);
                        }
                        var obj = getSearchParam("rlSearch");
                        refreshTab(rlUrl, rlCurrentId, authorityCode, obj);
                        recordLog("", "", "", "认领任务列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "grContent":
                        if (grListIndex == 0) {
                            grListIndex++;
                            table.resize(grListCurrentId);
                        }
                        var obj = getSearchParam("grSearch");
                        refreshTab(grListUrl, grListCurrentId, authorityCode, obj);
                        recordLog("", "", "", "个人任务列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "wwjContent":
                        if (wwjIndex == 0) {
                            wwjIndex++;
                            table.resize(wwjCurrentId);
                        }
                        var obj = getSearchParam("wwjSearch");
                        refreshTab(wwjUrl, wwjCurrentId, authorityCode, obj);
                        recordLog("", "", "", "wwj列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "wwsqContent":
                        if (wwsqindex == 0) {
                            wwsqindex++;
                            table.resize(wwsqCurrentId);
                        }
                        var obj = getSearchParam("wwsqSearch");
                        refreshTab(wwsqUrl, wwsqCurrentId, authorityCode, obj);
                        recordLog("", "", "", "wwsq列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "wsywContent":
                        if (wsywindex == 0) {
                            wsywindex++;
                            table.resize(wsywCurrentId);
                        }
                        var obj = getSearchParam("wsywSearch");
                        refreshTab(wsywUrl, wsywCurrentId, authorityCode, obj);
                        recordLog("", "", "", "wsyw列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "dajjdbContent":
                        if (dajjdbIndex == 0) {
                            dajjdbIndex++;
                            table.resize(dajjdbCurrentId);
                        }
                        var obj = getDajjDbSearchParam("dajjdbSearch");
                        refreshTab(dajjdbUrl, dajjdbCurrentId, authorityCode, obj);
                        recordLog("", "", "", "档案交接待办列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "dajjybContent":
                        if (dajjybIndex == 0) {
                            dajjybIndex++;
                            table.resize(dajjybCurrentId);
                        }
                        var obj = getDajjDbSearchParam("dajjybSearch");
                        refreshTab(dajjybUrl, dajjybCurrentId, authorityCode, obj);
                        recordLog("", "", "", "档案交接已办列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "dajjrlContent":
                        if (dajjrlIndex == 0) {
                            dajjrlIndex++;
                            table.resize(dajjrlCurrentId);
                        }
                        var obj = getDajjRlSearchParam("dajjrlSearch");
                        refreshTab(dajjrlUrl, dajjrlCurrentId, authorityCode, obj);
                        recordLog("", "", "", "档案交接认领列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                    case "fcwqrlContent":
                        if (fcwqrlCurrentId == 0) {
                            fcwqrlCurrentId++;
                            table.resize(fcwqrlCurrentId);
                        }
                        var obj = getSearchParam("fcwqrlSearch");
                        refreshTab(fcwqrlUrl, fcwqrlCurrentId, authorityCode, obj);
                        recordLog("", "", "", "房产网签认领列表重新刷新,查询条件" + JSON.stringify(obj))
                        break;
                }
            }
        });

        // 首页选择已办跳转模拟点击操作
        if (viewType === "complete") {
            formSelects.render();
            if($(".bdc-list-tab #ybTab") && $(".bdc-list-tab #ybTab").length >0){
                $(".bdc-list-tab #ybTab").click();
            }else{
                $(".bdc-list-tab li:nth-child(3)").click();
            }
        }
        // 首页选择待办量和超期量跳转模拟点击操作
        if(viewType === "todo" || viewType === "out"){
            formSelects.render();
            if($(".bdc-list-tab #todoTab") && $(".bdc-list-tab #todoTab").length >0){
                $(".bdc-list-tab #todoTab").click();
            }else{
                $(".bdc-list-tab li:nth-child(2)").click();
            }
        }

        // 首页选择待办量跳转到认领列表，模拟点击操作
        if(viewType === "claim"){
            formSelects.render();
            if($(".bdc-list-tab #rlTab") && $(".bdc-list-tab #rlTab").length >0){
                $(".bdc-list-tab #rlTab").click();
            }else{
                $(".bdc-list-tab li:nth-child(1)").click();
            }
        }

        //6.4 待办任务头工具栏事件
        table.on('toolbar(waitTableFilter)', function (obj) {
            if(obj.event == 'db-download') {
                download(obj.event);
                return ;
            }
            var checkStatus = table.checkStatus(obj.config.id);
            if (todoPermission === true) {
                if (checkStatus.data.length != 1) {
                    warnMsg("只能办理首条任务，请重新选择数据！");
                    return;
                }
                if (checkStatus.data[0].ROW_ID != 1) {
                    var nodePermission = getNodePermission(checkStatus.data[0]);
                    if(nodePermission){
                        warnMsg("当前任务不允许操作，请按序办理业务！");
                        return;
                    }
                }
            }
            if (obj.event != "LAYTABLE_COLS") {
                var selectedNum = checkStatus.data.length;
                var deleteEvent = (obj.event == "delete-process" || obj.event == "hang-up-process" || obj.event == "active-process"
                    || obj.event == "cancel-process" || obj.event == "back-process");
                if (obj.event == 'forward-process' && selectedNum > 1) {
                    if (isPlzf !== 0 || isPlzf !== 1) {
                        // 判断是否配置了批量转发
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
                case 'set-priority':
                    if(checkData.stateName == "挂起"){
                        warnMsg("挂起任务无法加急");
                    }else{
                        workflow.setPriority(checkStatus, waitUrl, waitTableId, waitCurrentId, false);
                    }
                    break;
                case 'cancle-priority':
                    workflow.canclePriority(checkStatus, waitUrl, waitTableId, waitCurrentId, false);
                    break;
                case 'forward-process':
                    addModel();
                    setTimeout(function () {
                        workflow.forwardauto(checkStatus, waitUrl, waitTableId, waitCurrentId, true, checkData.processInstanceId);
                    }, 50);

                    break;
                case 'cancel-process':
                    if (selectedNum == 1) {
                        workflow.cancelClaimProcess(checkData, waitUrl, waitTableId, waitCurrentId, true);
                    } else {
                        workflow.cancelClaimPlProcess(checkStatus.data, waitUrl, waitTableId, waitCurrentId, true);
                    }
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
                    if(lcPageEdition == "changzhou"){
                        workflow.applyDelete(checkStatus, waitUrl, waitTableId, waitCurrentId, true, checkData.processInstanceId);
                    }else{
                        workflow.deleteProcess(checkStatus, waitUrl, waitTableId, waitCurrentId, true);
                    }
                    break;
                case 'hang-up-process':
                    workflow.hangUpProcess(checkStatus, waitUrl, waitTableId, waitCurrentId);
                    break;
                case 'forward-pl-process':
                    if(lcPageEdition == "changzhou" && sfytlc(checkStatus) == false){
                        layer.msg('请选择相同的流程办理批量转发！');
                        return false;
                    }
                    //批量转发
                    workflow.forwardPlProcess(checkStatus, waitUrl, waitTableId, waitCurrentId);
                    break;
                case 'bind-forward':
                    //绑定转发事件
                    if (selectedNum !== 1) {
                        layer.msg('请选择一条数据进行转发！');
                        return false;
                    }
                    workflow.forwardPlProcess(checkStatus, waitUrl, waitTableId, waitCurrentId, true);
                    break;
                case 'allow-delete-process':
                    if (selectedNum !== 1) {
                        layer.msg('请选择一条数据进行删除！');
                        return false;
                    }
                    workflow.allowDelete(checkStatus, waitUrl, waitTableId, waitCurrentId, true, checkStatus.data[0].processInstanceId);
                    break;
            }
        });
        //认领工具栏
        table.on('toolbar(rlTableFilter)', function (obj) {
            if(obj.event == 'rl-download') {
                download(obj.event);
                return ;
            }
            var checkStatus = table.checkStatus(obj.config.id);
            var selectedNum = checkStatus.data.length;
            if (selectedNum == 0) {
                layer.msg('请选择一条数据进行操作！');
                return false;
            }
            switch (obj.event) {
                case 'claim-process':
                    var hasSameShr = checkHasSameShr(checkStatus);
                    if (hasSameShr) {
                        layer.msg('存在同一审核人，不可认领!');
                        return false;
                    }
                    // alert("测试完毕");
                    // return false;
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
                                workflow.claimProcess(checkStatus, rlUrl, rlTableId, rlCurrentId, false);
                            }
                        });
                    } else {
                        workflow.claimProcess(checkStatus, rlUrl, rlTableId, rlCurrentId, false);
                    }
                    break;
            }
        });

        table.on('toolbar(wsywTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var selectedNum = checkStatus.data.length;
            if (selectedNum == 0) {
                layer.msg('请选择一条数据进行操作！');
                return false;
            }
            switch (obj.event) {
                case 'claim-process':
                    var hasSameShr = checkHasSameShr(checkStatus);
                    if (hasSameShr) {
                        layer.msg('存在同一审核人，不可认领!');
                        return false;
                    }
                    if (isConfirmRl) {
                        rlLayerIndex = layer.open({
                            type: 1,
                            title: '认领',
                            skin: 'bdc-small-tips bdc-zf-tips',
                            area: ['320px'],
                            content: '您确定要认领吗？',
                            btn: ['确定', '取消'],
                            btnAlign: 'c',
                            yes: function () {
                                //确定操作
                                workflow.claimProcess(checkStatus, wsywUrl, wsywTableId, wsywCurrentId, false);
                                layer.close(rlLayerIndex);
                            }
                        });
                    } else {
                        workflow.claimProcess(checkStatus, wsywUrl, wsywTableId, wsywCurrentId, false);
                    }
                    break;
                case 'delete-process':
                    workflow.deleteProcess(checkStatus, wsywUrl, wsywTableId, wsywCurrentId, true, "back");
                    break;
            }
        });

        // 在认领列表中打开任务
        table.on('tool(wsywTableFilter)', function (obj) {
            if (obj.event === 'openpage') {
                var data = [];
                data.push(obj.data);
                obj.data = data;

                var hasSameShr = checkHasSameShr(obj);
                if (hasSameShr) {
                    layer.msg('存在同一审核人，不可认领!');
                    return false;
                }
                if (isConfirmRl) {
                    rlLayerIndex = layer.open({
                        type: 1,
                        title: '认领',
                        skin: 'bdc-small-tips',
                        area: ['320px'],
                        content: '您确定要认领吗？',
                        btn: ['确定', '取消'],
                        btnAlign: 'c',
                        yes: function () {
                            //确定操作
                            workflow.claimProcess(obj, wsywUrl, wsywTableId, wsywCurrentId, true);
                            layer.close(rlLayerIndex);
                        }
                    });
                } else {
                    workflow.claimProcess(obj, wsywUrl, wsywTableId, wsywCurrentId, true);
                }
            }
        });

        // 在认领列表中打开任务
        table.on('tool(rlTableFilter)', function (obj) {
            if (obj.event === 'openpage') {
                var data = [];
                data.push(obj.data);
                obj.data = data;

                var hasSameShr = checkHasSameShr(obj);
                if (hasSameShr) {
                    layer.msg('存在同一审核人，不可认领!');
                    return false;
                }
                // alert("测试完毕");
                // return false;
                if(isConfirmRl){
                    rlLayerIndex = layer.open({
                        type: 1,
                        title: '认领',
                        skin: 'bdc-small-tips',
                        area: ['320px'],
                        content: '您确定要认领吗？',
                        btn: ['确定','取消'],
                        btnAlign: 'c',
                        yes: function(){
                            //确定操作
                            workflow.claimProcess(obj, rlUrl, rlTableId, rlCurrentId,true);
                            layer.close(rlLayerIndex);
                        }
                    });
                }else {
                    workflow.claimProcess(obj, rlUrl, rlTableId, rlCurrentId,true);
                }
            }
        });

        //外网申请工具栏
        table.on('toolbar(wwsqTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var selectedNum = checkStatus.data.length;
            if (selectedNum == 0) {
                layer.msg('请选择一条数据进行操作！');
                return false;
            }
            switch (obj.event) {
                case 'claim-process':
                    var hasSameShr = checkHasSameShr(checkStatus);
                    if (hasSameShr) {
                        layer.msg('存在同一审核人，不可认领!');
                        return false;
                    }
                    // alert("测试完毕");
                    // return false;
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
                                //确定操作
                                workflow.claimProcess(checkStatus, wwsqUrl, wwsqTableId, wwsqCurrentId,false);
                                layer.close(rlLayerIndex);
                            }
                        });
                    }else {
                        workflow.claimProcess(checkStatus, wwsqUrl, wwsqTableId, wwsqCurrentId,false);
                    }
                    break;
            }
        });
        // 在外网申请列表中打开任务
        table.on('tool(wwsqTableFilter)', function (obj) {
            if (obj.event === 'openpage') {
                var data = [];
                data.push(obj.data);
                obj.data = data;

                var hasSameShr = checkHasSameShr(obj);
                if (hasSameShr) {
                    layer.msg('存在同一审核人，不可认领!');
                    return false;
                }
                // alert("测试完毕");
                // return false;
                if(isConfirmRl){
                    rlLayerIndex = layer.open({
                        type: 1,
                        title: '认领',
                        skin: 'bdc-small-tips',
                        area: ['320px'],
                        content: '您确定要认领吗？',
                        btn: ['确定','取消'],
                        btnAlign: 'c',
                        yes: function(){
                            //确定操作
                            workflow.claimProcess(obj, wwsqUrl, wwsqTableId, wwsqCurrentId,true);
                            layer.close(rlLayerIndex);
                        }
                    });
                }else {
                    workflow.claimProcess(obj, wwsqUrl, wwsqTableId, wwsqCurrentId,true);
                }
            }
        });


        //项目列表工具栏
        table.on('toolbar(listTableFilter)', function (obj) {
            if(obj.event == 'xm-download') {
                download(obj.event);
                return ;
            }
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
                case 'set-priority':
                    if (checkStatus.data[0].procStatus === 2) {
                        warnMsg("项目已办结，无法加急！");
                    } else {
                        workflow.setPriority(checkStatus, listUrl, listTableId, listCurrentId, true);
                    }
                    break;
                case 'cancle-priority':
                    workflow.canclePriority(checkStatus, listUrl, listTableId, listCurrentId, true);
                    break;
                case 'allow-delete-process':
                    if (selectedNum !== 1) {
                        layer.msg('请选择一条数据进行删除！');
                        return false;
                    }
                    workflow.allowDelete(checkStatus, waitUrl, waitTableId, waitCurrentId, true, checkStatus.data[0].processInstanceId);
                    break;
            }
        });

        //个人项目列表工具栏
        table.on('toolbar(grListTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var selectedNum = checkStatus.data.length;
            if (selectedNum == 0) {
                layer.msg('请选择一条数据进行操作！');
                return false;
            }
            switch (obj.event) {
                case 'delete-process':
                    // 删除当前任务
                    workflow.deleteProcess(checkStatus, grListUrl, grListTableId, grListCurrentId, true);
                    break;
                case 'set-priority':
                    if (checkStatus.data[0].procStatus === 2) {
                        warnMsg("项目已办结，无法加急！");
                    } else {
                        //批量转发
                        workflow.setPriority(checkStatus, grListUrl, grListTableId, grListCurrentId, true);
                    }
                    break;
                case 'cancle-priority':
                    workflow.canclePriority(checkStatus, grListUrl, grListTableId, grListCurrentId, true);
                    break;
            }
        });
        //已办 工具类
        table.on('toolbar(doneTableFilter)', function (obj) {
            if(obj.event == 'yb-download') {
                download(obj.event);
                return ;
            }
            var checkStatus = table.checkStatus(obj.config.id);
            if (obj.event != "LAYTABLE_COLS" && obj.event != "yjd") {
                if (checkStatus.data.length == 0) {
                    layer.msg('请选择一条数据进行操作！');
                    return false;
                }
            }
            // var checkData = checkStatus.data[0];
            switch (obj.event) {
                case 'fetch-process':
                    // workflow.fetchProcess(checkData, doneUrl, doneTableId, doneCurrentId, true);
                    workflow.fetchPlProcess(checkStatus, doneUrl, doneTableId, doneCurrentId, true);
                    break;
                case 'yjd':
                    layer.open({
                        title: '移交单列表',
                        type: 2,
                        area: ['100%','100%'],
                        content: 'bdcYjd.html'
                        ,cancel: function(){

                        }
                        , success: function () {

                        }
                    });
                    break;
            }
        });

        //外网件工具栏事件
        table.on('toolbar(wwjTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            if (obj.event != "LAYTABLE_COLS") {
                var selectedNum = checkStatus.data.length;
                if (selectedNum != 1 && obj.event == "create-process") {
                    layer.msg('请选择一条数据进行创建！');
                    return false;
                }
            }

            var checkData = checkStatus.data[0];
            if (obj.event == "create-process") {
                $.ajax({
                    url: getContextPath() + "/rest/v1.0/workflow/process-instances/wwyw?hlwxmid=" + checkData.XMID,
                    type: "PUT",
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        var waitCzTimer = setInterval(function () {
                            clearInterval(waitCzTimer);
                            var index = window.open("./new-page.html?name=" + data.taskId + "&type=wwj", "外网件");
                            newWin.push(index);
                        }, 50);
                    }, error: function (e) {
                        response.fail(e, '');
                    }
                });
            }
        });

        //档案交接待办头工具栏事件
        table.on('toolbar(dajjdbTableFilter)', function (obj) {
            console.log('--------------'+obj.config.id);
            var selectedData = [];
            $(this).parent().parent().parent().parent().find(".layui-table-fixed .layui-table-body .laytable-cell-checkbox").each(function(i,item){
                if($(item).find(".layui-form-checked").length>0){
                    selectedData.push(tableData[i]);
                }
            })

            var checkStatus = table.checkStatus(obj.config.id);
            if (obj.event != "LAYTABLE_COLS") {
                var selectedNum = checkStatus.data.length;
                var deleteEvent = obj.event == "back-process" || obj.event == "printDajj" || obj.event == "exportDajj" || obj.event == "printDajjWJ"|| obj.event == "printDajjJT"|| obj.event == "printDajjLY";
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
                        workflow.forwardauto(checkStatus, dajjdbUrl, dajjdbTableId, dajjdbCurrentId, true, checkData.processInstanceId);
                    }, 50);

                    break;
                case 'back-process':
                    if (selectedNum == 1) {
                        workflow.backProcess(checkData, dajjdbUrl, dajjdbTableId, dajjdbCurrentId, true);
                    } else {
                        workflow.backPlProcess(checkStatus, dajjdbUrl, dajjdbTableId, dajjdbCurrentId, true);
                    }
                    break;
                case 'forward-pl-process':
                    //批量转发
                    workflow.forwardPlProcess(checkStatus, dajjdbUrl, dajjdbTableId, dajjdbCurrentId);
                    break;
                case 'printDajj':
                    console.info("selectedData----------------")
                    console.info(selectedData);
                    console.info("checkStatus.data----------------")
                    console.info(checkStatus.data);
                    printDajj(checkStatus.data);
                    break;
                case 'exportDajj':
                    exportDajj(checkStatus.data);
                    break;
                case 'printDajjWJ':
                    console.info(checkStatus);
                    printDajjWJ(checkStatus.data);
                    break;
                case 'printDajjJT':
                    console.info(checkStatus);
                    printDajjJT(checkStatus.data);
                    break;
                case 'printDajjLY':
                    console.info(checkStatus);
                    printDajjLY(checkStatus.data);
                    break;
                case 'delete-process':
                    workflow.deleteProcess(checkStatus, dajjdbUrl, dajjdbTableId, dajjdbCurrentId, true);
                    break;
            }
        });

        //档案交接已办头工具栏事件
        table.on('toolbar(dajjybTableFilter)', function (obj) {
            console.log('--------------'+obj.config.id);
            var checkStatus = table.checkStatus(obj.config.id);
            if (obj.event != "LAYTABLE_COLS") {
                if (checkStatus.data.length == 0) {
                    layer.msg('请选择一条数据进行操作！');
                    return false;
                }
            }
            switch (obj.event) {
                case 'fetch-process':
                    workflow.fetchPlProcess(checkStatus, dajjybUrl, dajjybTableId, dajjybCurrentId, true);
                    break;
                case 'printDajj':
                    console.info(checkStatus);
                    printDajj(checkStatus.data);
                    break;
                case 'exportDajj':
                    exportDajj(checkStatus.data);
                    break;
                case 'printDajjWJ':
                    printDajjWJ(checkStatus.data);
                    break;
                case 'printDajjJT':
                    console.info(checkStatus);
                    printDajjJT(checkStatus.data);
                    break;
                case 'printDajjLY':
                    console.info(checkStatus);
                    printDajjLY(checkStatus.data);
                    break;
            }
        });

        //档案交接认领工具栏
        table.on('toolbar(dajjrlTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var selectedNum = checkStatus.data.length;
            if (selectedNum == 0) {
                layer.msg('请选择一条数据进行操作！');
                return false;
            }
            switch (obj.event) {
                case 'claim-process':
                    var hasSameShr = checkHasSameShr(checkStatus);
                    if (hasSameShr) {
                        layer.msg('存在同一审核人，不可认领!');
                        return false;
                    }
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
                                workflow.claimProcess(checkStatus, dajjrlUrl, dajjrlTableId, dajjrlCurrentId, false);
                            }
                        });
                    } else {
                        workflow.claimProcess(checkStatus, dajjrlUrl, dajjrlTableId, dajjrlCurrentId, false);
                    }
                    break;
                case 'delete-process':
                    workflow.deleteProcess(checkStatus, dajjrlUrl, dajjrlTableId, dajjrlCurrentId, true);
                    break;
            }
        });

        // 6.5 监听行双击事件
        //监听待办 行双击
        table.on('rowDouble(waitTableFilter)', function (obj) {
            if (todoPermission === true && obj.data.ROW_ID != 1) {
                var nodePermission = getNodePermission(obj.data);
                if (nodePermission) {
                    warnMsg("当前任务不允许打开，请按序办理业务！");
                    return;
                }
            }
            if (!workflow.showHangReson(obj.data)) {
                return false;
            }
            if (lcPageEdition === "changzhou") {
                var index = $(obj.tr).attr("data-index");
                obj.data = dbTableCache[index];
                console.log(new Date() + "待办任务双击数据" + JSON.stringify(obj.data));
            }
            recordLog(obj.data.slbh, obj.data.taskId, obj.data.processInstanceId, "待办任务双击");
            workflow.lockProcess(obj.data);
            var waitCzTimer = setInterval(function () {
                clearInterval(waitCzTimer);
                var index = window.open("./new-page.html?name=" + obj.data.taskId + "&type=todo", "待办任务");
                newWin.push(index);
            }, 50);

        });

        table.on('tool(waitTableFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'openpage') {
                if (lcPageEdition === "changzhou") {
                    var index = $(obj.tr).attr("data-index");
                    obj.data = dbTableCache[index];
                    console.log(new Date() + "待办任务单击数据" + JSON.stringify(obj.data));
                }
                openWindow(obj);
            }
        });

        // 监听已办 行双击
        table.on('rowDouble(doneTableFilter)', function (obj) {
            if (!workflow.showHangReson(obj.data)) {
                return false;
            }
            if (lcPageEdition === "changzhou") {
                var index = $(obj.tr).attr("data-index");
                obj.data = ybTableCache[index];
                console.log(new Date() + "已办列表双击数据" + JSON.stringify(obj.data));
            }
            recordLog(obj.data.slbh, obj.data.taskId, obj.data.processInstanceId, "已办任务双击");
            var ybCzTimer = setInterval(function () {
                clearInterval(ybCzTimer);
                var index = window.open("./new-page.html?name=" + obj.data.taskId + "&type=done", obj.data.processDefName);
                newWin.push(index);
            }, 50);
        });

        table.on('tool(doneTableFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'openpage') {
                if (!workflow.showHangReson(obj.data)) {
                    return false;
                }
                if (lcPageEdition === "changzhou") {
                    var index = $(obj.tr).attr("data-index");
                    obj.data = ybTableCache[index];
                    console.log(new Date() + "已办列表单击数据" + JSON.stringify(obj.data));
                }
                recordLog(obj.data.slbh, obj.data.taskId, obj.data.processInstanceId, "已办任务");
                var ybCzTimer = setInterval(function () {
                    clearInterval(ybCzTimer);
                    var index = window.open("./new-page.html?name=" + obj.data.taskId + "&type=done", obj.data.slbh);
                    newWin.push(index);
                }, 50);
            }
        });
        // 监听项目列表 行双击
        table.on('rowDouble(listTableFilter)', function (obj) {
            if (!workflow.showHangReson(obj.data)) {
                return false;
            }
            if (lcPageEdition === "changzhou") {
                var index = $(obj.tr).attr("data-index");
                obj.data = xmTableCache[index];
                console.log(new Date() + "项目列表双击数据" + JSON.stringify(obj.data));
            }
            recordLog(obj.data.slbh, obj.data.taskId, obj.data.processInstanceId, "项目列表双击");
            if (lcPageEdition === 'hf') {
                var index = $(obj.tr).attr("data-index");
                obj.data = listCacheData[index];
            }
            var xmCzTimer = setInterval(function () {
                clearInterval(xmCzTimer);
                var index = window.open(getContextPath() + "/view/new-page.html?name=" + obj.data.processInstanceId + "&type=list", obj.data.slbh);
                newWin.push(index);
            }, 50);
        });

        table.on('tool(listTableFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'openpage') {
                if (lcPageEdition === "changzhou") {
                    var index = $(obj.tr).attr("data-index");
                    obj.data = xmTableCache[index];
                    console.log(new Date() + "项目列表单击数据" + JSON.stringify(obj.data));
                }
                if (lcPageEdition === 'hf') {
                    var index = $(obj.tr).attr("data-index");
                    obj.data = listCacheData[index];
                }
                if (!workflow.showHangReson(obj.data)) {
                    return false;
                }
                recordLog(obj.data.slbh, obj.data.taskId, obj.data.processInstanceId, "项目列表");
                var xmCzTimer = setInterval(function () {
                    clearInterval(xmCzTimer);
                    var index = window.open("./new-page.html?name=" + obj.data.processInstanceId + "&type=list", obj.data.slbh);
                    newWin.push(index);
                }, 50);
            }
        });

        // 监听个人项目列表 行双击
        table.on('rowDouble(grListTableFilter)', function (obj) {
            if (!workflow.showHangReson(obj.data)) {
                return false;
            }
            if (lcPageEdition === "changzhou") {
                var index = $(obj.tr).attr("data-index");
                obj.data = grTableCache[index];
                console.log(new Date() + "个人项目列表双击数据" + JSON.stringify(obj.data));
            }
            recordLog(obj.data.slbh, obj.data.taskId, obj.data.processInstanceId, "个人项目列表双击");
            var grCzTimer = setInterval(function () {
                clearInterval(grCzTimer);
                var index = window.open(getContextPath() + "/view/new-page.html?name=" + obj.data.processInstanceId + "&type=list", obj.data.slbh);
                newWin.push(index);
            }, 50);
        });

        table.on('tool(grListTableFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'openpage') {
                if (!workflow.showHangReson(obj.data)) {
                    return false;
                }
                if (lcPageEdition === "changzhou") {
                    var index = $(obj.tr).attr("data-index");
                    obj.data = grTableCache[index];
                    console.log(new Date() + "个人项目列表单击数据" + JSON.stringify(obj.data));
                }
                recordLog(obj.data.slbh, obj.data.taskId, obj.data.processInstanceId, "个人项目列表");
                var grCzTimer = setInterval(function () {
                    clearInterval(grCzTimer);
                    var index = window.open(getContextPath() + "/view/new-page.html?name=" + obj.data.processInstanceId + "&type=list", obj.data.slbh);
                    newWin.push(index);
                }, 50);
            }
        });

        //监听档案交接待办 行双击
        table.on('rowDouble(dajjdbTableFilter)', function (obj) {
            var gzlslids = [];
            gzlslids.push(obj.data.processInstanceId);
            queryYlcGzlslid(gzlslids).done(function(data){
                var dajjdbCzTimer = setInterval(function () {
                    clearInterval(dajjdbCzTimer);
                    var index = window.open("./popup-img.html?processinsid=" + data[0]);
                    newWin.push(index);
                }, 50);
            });

        });

        table.on('tool(dajjdbTableFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'openpage') {
                var gzlslids = [];
                gzlslids.push(obj.data.processInstanceId);
                queryYlcGzlslid(gzlslids).done(function(data){
                    var dajjdbCzTimer = setInterval(function () {
                        clearInterval(dajjdbCzTimer);
                        var index = window.open("./popup-img.html?processinsid=" + data[0]);
                        newWin.push(index);
                    }, 50);
                });
            }
        });

        // 监听档案交接已办 行双击
        table.on('rowDouble(dajjybTableFilter)', function (obj) {
            var gzlslids = [];
            gzlslids.push(obj.data.processInstanceId);
            queryYlcGzlslid(gzlslids).done(function(data){
                var dajjdbCzTimer = setInterval(function () {
                    clearInterval(dajjdbCzTimer);
                    var index = window.open("./popup-img.html?processinsid=" + data[0]);
                    newWin.push(index);
                }, 50);
            });
        });

        table.on('tool(dajjybTableFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'openpage') {
                var gzlslids = [];
                gzlslids.push(obj.data.processInstanceId);
                queryYlcGzlslid(gzlslids).done(function(data){
                    var dajjdbCzTimer = setInterval(function () {
                        clearInterval(dajjdbCzTimer);
                        var index = window.open("./popup-img.html?processinsid=" + data[0]);
                        newWin.push(index);
                    }, 50);
                });
            }
        });

        // 监听档案交接认领打开任务
        table.on('tool(dajjrlTableFilter)', function (obj) {
            if (obj.event === 'openpage') {
                var data = [];
                data.push(obj.data);
                obj.data = data;

                var hasSameShr = checkHasSameShr(obj);
                if (hasSameShr) {
                    layer.msg('存在同一审核人，不可认领!');
                    return false;
                }
                if(isConfirmRl){
                    rlLayerIndex = layer.open({
                        type: 1,
                        title: '认领',
                        skin: 'bdc-small-tips',
                        area: ['320px'],
                        content: '您确定要认领吗？',
                        btn: ['确定','取消'],
                        btnAlign: 'c',
                        yes: function(){
                            //确定操作
                            workflow.claimProcess(obj, dajjrlUrl, dajjrlTableId, dajjrlCurrentId, true);
                            layer.close(rlLayerIndex);
                        }
                    });
                }else {
                    workflow.claimProcess(obj, dajjrlUrl, dajjrlTableId, dajjrlCurrentId,true);
                }
            }
        });

        //认领工具栏
        table.on('toolbar(fcwqrlTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var selectedNum = checkStatus.data.length;
            if (selectedNum == 0) {
                layer.msg('请选择一条数据进行操作！');
                return false;
            }
            switch (obj.event) {
                case 'claim-process':
                    var hasSameShr = checkHasSameShr(checkStatus);
                    if (hasSameShr) {
                        layer.msg('存在同一审核人，不可认领!');
                        return false;
                    }
                    // alert("测试完毕");
                    // return false;
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
                                workflow.claimProcess(checkStatus, rlUrl, fcwqrlTableId, fcwqrlCurrentId, false);
                            }
                        });
                    } else {
                        workflow.claimProcess(checkStatus, rlUrl, rlTableId, rlCurrentId, false);
                    }
                    break;
            }
        });

        // 在认领列表中打开任务
        table.on('tool(fcwqrlTableFilter)', function (obj) {
            if (obj.event === 'openpage') {
                var data = [];
                data.push(obj.data);
                obj.data = data;

                var hasSameShr = checkHasSameShr(obj);
                if (hasSameShr) {
                    layer.msg('存在同一审核人，不可认领!');
                    return false;
                }
                // alert("测试完毕");
                // return false;
                if(isConfirmRl){
                    rlLayerIndex = layer.open({
                        type: 1,
                        title: '认领',
                        skin: 'bdc-small-tips',
                        area: ['320px'],
                        content: '您确定要认领吗？',
                        btn: ['确定','取消'],
                        btnAlign: 'c',
                        yes: function(){
                            //确定操作
                            workflow.claimProcess(obj, rlUrl, rlTableId, rlCurrentId,true);
                            layer.close(rlLayerIndex);
                        }
                    });
                }else {
                    workflow.claimProcess(obj, rlUrl, rlTableId, rlCurrentId,true);
                }
            }
        });


        //查询
        var searchObj = {
            "dbSearch": "dbTable",
            "ybSearch": "doneTableId",
            "xmSearch": "listTableId",
            "grSearch": "grListTableId",
            "rlSearch": "rlTableId",
            "wwjSearch": "wwjTableId",
            "wwsqSearch": "wwsqTableId",
            "wsywSearch": "wsywTableId",
            "dajjdbSearch": "dajjdbTable",
            "dajjybSearch": "dajjybTable",
            "dajjrlSearch": "dajjrlTable",
            "fcwqrlSearch": "fcwqrlTableId",
        };
        $('.searchBtn').on('click', function () {
            var id = this.id;
            // 获取查询内容
            var obj = getSearchParam(id);

            if (lcPageEdition == 'yancheng' && id == "wsywSearch" && obj.taskName) {
                // 重新请求
                addModel();
                table.reload(searchObj[id], {
                    where: obj
                    , page: {
                        curr: 1  //重新从第 1 页开始
                    },
                    initSort: {
                        field: 'startTime' //排序字段，对应 cols 设定的各字段名
                        ,type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
                    }
                });
                removeModal()
            } else {
            // 重新请求
                addModel();
                table.reload(searchObj[id], {
                    where: obj
                    , page: {
                        curr: 1  //重新从第 1 页开始
                    }
                });
                removeModal()
            }
            moduleAuthority.load({
                authorityCode: authorityCode
            });
        });

        var downloadParam = {
            "db-download": ["db","dbSearch"],
            "yb-download": ["yb","ybSearch"],
            "xm-download": ["xm","xmSearch"],
            "rl-download": ["rl","rlSearch"],
        };
        //下载
        //首先查询一次,获取当前条件的数据量和下载的配置项
        $('.downloadBtn').on('click', function () {
            var id = this.id;
            //download(id);
        });

        function download(id){
            // 获取查询内容
            var obj = getSearchParam(downloadParam[id][1]);
            // 重新请求
            addModel();
            $.ajax({
                type: "POST",
                url: getContextPath() + "/rest/v1.0/task/download/total/" + downloadParam[id][0],
                data: obj,
                async: false,
                success: function (res) {
                    if (res && res.hasOwnProperty("total")) {
                        if(res.total > res.maxdatanum){
                            layer.alert("数据量大于最大数据限制!");
                            return;
                        }

                        //按照数量大小分次调用
                        var param = "";
                        $.each(obj,function (key,val){
                            param = param + key + "=" + val + "&"
                        })
                        var page = 1;
                        var downDataNum = 0;
                        while (true){
                            //分页参数
                            var pageParam = "page=" + page + "&size=" + res.perfiledatanum;
                            downDataNum += res.perfiledatanum;
                            page++;
                            var fileUrl = getContextPath() + "/rest/v1.0/task/download/" + downloadParam[id][0] + "?" + param + pageParam;
                            window.open(fileUrl, "DOWNLOAD" + page);
                            if(downDataNum >= res.total){
                                break;
                            }
                        }
                    }
                }
            });
            removeModal()
        }
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
                } else if (this.tagName === 'DIV') {
                    if(inputSelectIns) {
                        $.each(inputSelectIns, function (key, valIns) {
                            if(key.split("-")[0] === id.replace("Search", "")) {
                                var searchVal = valIns.getValue();
                                if(!isNullOrEmpty(searchVal) && searchVal !== "请选择") {
                                    obj[key.split("-")[1]] = searchVal;
                                }
                            }
                        });
                    }
                } else {
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
            if(!isNullOrEmpty(obj.processCategoryName) && isNullOrEmpty(obj.processDefName)){
                //选择了分类但是没有选择具体的流程，则将当前分类下所有的流程都传递
                if(!isNullOrEmpty(categoryProcessDefNames)){
                    obj.processDefName = categoryProcessDefNames.join(",");
                }

            }
            return obj;
        }

        // 获取查询参数
        function getDajjRlSearchParam(id){
            var obj = getSearchParam(id);
            var ylcmcNot = getDajjProcessKey("ylcmc_not");
            if(isNotBlank(obj.ylcmc_in) && isNotBlank(ylcmcNot)){
                var ylcmcNotList = [];
                $.each(ylcmcNot.split(","), function(index, value){
                    if(obj.ylcmc_in.indexOf(value)< 0){
                        ylcmcNotList.push(value);
                    }
                });
                ylcmcNot = ylcmcNotList.join(",");
            }
            obj.ylcmc_not = ylcmcNot;
            obj.processDefName = getDajjProcessKey("processDefName");
            obj.lx = "dajjrlContent";
            obj.order_type = "dajjrl_order";
            return obj;
        }

        function getDajjDbSearchParam(id){
            var obj = getSearchParam(id);
            obj.processDefName = getDajjProcessKey("processDefName");
            obj.lx = "dajjdbContent";
            obj.prefix = "order_dajj_todo";
            return obj;
        }

        // 档案交接认领搜索，添加档案交接流程参数
        $('.dajjrlSearchBtn').on('click', function () {
            var id = this.id;
            // 获取查询内容
            var obj = getDajjRlSearchParam(id);
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

        // 档案交接待办搜索，添加档案交接流程参数
        $('.dajjSearchBtn').on('click', function () {
            var id = this.id;
            // 获取查询内容
            var obj = getDajjDbSearchParam(id);

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

        //点击高级查询--4的倍数
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

            if (version === 'nantong' || version === 'lianyungang') {
                // 获取待办部门下拉框数据
                getTreeData('dept_treedb', 'selectedDeptNameShow', 'selectedDeptName', "/rest/v1.0/task/dept/allnew");
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
        //点击高级查询--4的倍数
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
        //点击高级查询--4的倍数
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
        //点击高级查询--4的倍数  个人项目
        $('#seniorGrSearch').on('click', function () {
            $('.pf-senior-gr-show').toggleClass('bdc-hide');
            if (grSearch == "center") {
                $(this).parent().toggleClass('bdc-button-box-four');
            }
            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }
            changeTableHeight();
        });
        //点击高级查询，认领
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

            if (version === 'nantong' || version === 'lianyungang') {
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

        //点击高级查询，认领
        $('#seniorwwsqSearch').on('click', function () {
            $('.pf-senior-rl-show').toggleClass('bdc-hide');
            if (wwsqSearch == "center") {
                $(this).parent().toggleClass('bdc-button-box-four');
            }

            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }

            if(version=='nantong') {
                // 获取待办部门下拉框数据
                getTreeData('dept_treewwsq', 'selectedWwsqDeptNameShow', 'selectedWwsqDeptName', "/rest/v1.0/task/dept/allnew");
            }
            /**
             * 初始化组织机构下拉控件
             */
            $('.org_select_showwwsq').click(function () {
                if($('.org_select_showwwsq').text() == '选择'){
                    $('.org_select_tree').css('display','block');
                    $('.org_select_showwwsq').text('隐藏')
                }else{
                    $('.org_select_showwwsq').text('选择');
                    $('.org_select_tree').css('display','none');
                }
            });

            //下拉面板宽高位置
            (function(){
                var width = $('#selectedWwsqDeptNameShow').width + 40;
                $('.org_select_tree').css({'width': width, 'height':'200px', 'left':-66, 'top':35});
            })();
            changeTableHeight();
        });

        //点击高级查询--4的倍数 外网件
        $('#seniorwwjSearch').on('click', function () {
            $('.pf-senior-xm-show').toggleClass('bdc-hide');
            if (wwjSearch == "center") {
                $(this).parent().toggleClass('bdc-button-box-four');
            }
            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }
            changeTableHeight();
        });

        //点击高级查询，认领
        $('#seniorWsywSearch').on('click', function () {
            $('.pf-senior-rl-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');

            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }
            changeTableHeight();
        });
        //点击高级查询--4的倍数
        $('#seniorDajjybSearch').on('click', function () {
            $('.pf-senior-dajjyb-show').toggleClass('bdc-hide');
            if (dajjybSearch == "center") {
                $(this).parent().toggleClass('bdc-button-box-four');
            }
            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }
            changeTableHeight();
        });

        //点击高级查询--4的倍数
        $('#seniorDajjdbSearch').on('click', function () {
            $('.pf-senior-dajjdb-show').toggleClass('bdc-hide');
            if (dajjdbSearch == "center") {
                $(this).parent().toggleClass('bdc-button-box-four');
            }
            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }
            changeTableHeight();
        });
        //点击高级查询--4的倍数
        $('#seniorDajjrlSearch').on('click', function () {
            $('.pf-senior-dajjrl-show').toggleClass('bdc-hide');
            if (dajjrlSearch == "center") {
                $(this).parent().toggleClass('bdc-button-box-four');
            }
            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }
            changeTableHeight();
        });
        //点击高级查询，认领
        $('#seniorfcwqrlSearch').on('click', function () {
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

        loadProcessDefName();
        loadProcessCategoryName();
        loadFilterProcessDefName();
        if (lcPageEdition == 'nt') {
            // loadDeptName();
            loadQlrlx();
        }
        if (lcPageEdition == 'hf' ||lcPageEdition =='common' || lcPageEdition =='bengbu') {
            // 渲染审批来源下拉框
            loadSplyName();
        }
        if (lcPageEdition == 'yancheng') {
            loadDeptName();
            loadWsywDefName();
            loadSplyName();
            loadSlrName();
        }
        if (lcPageEdition == 'changzhou') {
            loadQxdm();
        }

        function loadWsywDefName() {
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/config/wsyw/process",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                async: false,
                success: function (data) {
                    $('#selectedWsywDefName').append(new Option("请选择", ""));
                    $.each(data, function (index, item) {
                        $('#selectedWsywDefName').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                    });
                    layui.form.render("select");
                }, error: function (e) {
                    response.fail(e, '');
                }
            });
        }

        /**
         * 渲染流程名称下拉框
         */
        function loadProcessDefName(gzldyids) {
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/task/process/all",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                async: false,
                success: function (data) {
                    $('#selectedDefName').find("option").remove();
                    $('#selectedDefName').append(new Option("请选择", ""));
                    $('#selectedDoneDefName').find("option").remove();
                    $('#selectedDoneDefName').append(new Option("请选择", ""));
                    $('#selectedXmDefName').find("option").remove();
                    $('#selectedXmDefName').append(new Option("请选择", ""));
                    $('#selectedRlDefName').find("option").remove();
                    $('#selectedRlDefName').append(new Option("请选择", ""));
                    $('#selectedGrDefName').find("option").remove();
                    $('#selectedGrDefName').append(new Option("请选择", ""));
                    $('#selectedGzldyid').find("option").remove();
                    $('#selectedGzldyid').append(new Option("请选择", ""));
                    $('#selectedWwsqDefName').find("option").remove();
                    $('#selectedWwsqDefName').append(new Option("请选择", ""));
                    $('#selectedDajjdbDefName').find("option").remove();
                    $('#selectedDajjdbDefName').append(new Option("请选择", ""));
                    $('#selectedDajjybDefName').find("option").remove();
                    $('#selectedDajjybDefName').append(new Option("请选择", ""));
                    $('#selectedDajjrlDefName').find("option").remove();
                    $('#selectedDajjrlDefName').append(new Option("请选择", ""));
                    $('#selectedfcwqrlDefName').find("option").remove();
                    $('#selectedfcwqrlDefName').append(new Option("请选择", ""));
                    $.each(data, function (index, item) {
                        if(isNullOrEmpty(gzldyids) || gzldyids.indexOf(item.processDefKey) >= 0){
                            $('#selectedDefName').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                            $('#selectedDoneDefName').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                            $('#selectedXmDefName').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                            $('#selectedRlDefName').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                            $('#selectedGrDefName').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                            $('#selectedGzldyid').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                            $('#selectedWwsqDefName').append(new Option(item.name, item.processDefKey));
                            $('#selectedDajjdbDefName').append(new Option(item.name, item.name));
                            $('#selectedDajjybDefName').append(new Option(item.name, item.name));
                            $('#selectedDajjrlDefName').append(new Option(item.name, item.name));
                            $('#selectedfcwqrlDefName').append(new Option(item.name, item.name));
                        }
                    });
                    layui.form.render("select");
                    if(isNotBlank(formSelects)){
                        formSelects.render();
                    }
                }, error: function (e) {
                    response.fail(e,'');
                }
            });
        }
        function filterCategoryProcess(data){
            if(isNotBlank(data)){
                $.each(data, function(index, value){
                    categoryProcess[value.id] =  {id: value.id, name: value.name, processList: value.processList};
                    if(value.children.length > 0){
                        filterCategoryProcess(value.children);
                    }
                });
            }
        }
        /**
         * 渲染流程分类下拉框
         */
        function loadProcessCategoryName() {
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/task/listAllCategoryProcess",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                async: false,
                success: function (data) {
                    $('#selectedCategoryName').append(new Option("请选择", ""));
                    $('#selectedDoneCategoryName').append(new Option("请选择", ""));
                    $('#selectedXmCategoryName').append(new Option("请选择", ""));
                    $('#selectedRlCategoryName').append(new Option("请选择", ""));
                    $('#selectedGrCategoryName').append(new Option("请选择", ""));
                    // $('#selectedGzldyid').append(new Option("请选择", ""));
                    // $('#selectedWwsqDefName').append(new Option("请选择", ""));
                    // $('#selectedDajjdbDefName').append(new Option("请选择", ""));
                    // $('#selectedDajjybDefName').append(new Option("请选择", ""));
                    // $('#selectedDajjrlDefName').append(new Option("请选择", ""));
                    filterCategoryProcess(data.content);
                    var ywflSelect = [];
                    for(var key in categoryProcess){
                        ywflSelect.push({"name": categoryProcess[key].name, "value": key});
                    }
                    formSelects.data('ywflSelect', 'local', {
                        arr: ywflSelect
                    });
                    $.each(ywflSelect, function (index, item) {
                        $('#selectedCategoryName').append(new Option(item.name, item.name));// 下拉菜单里添加元素
                        $('#selectedDoneCategoryName').append(new Option(item.name, item.name));// 下拉菜单里添加元素
                        $('#selectedXmCategoryName').append(new Option(item.name, item.name));// 下拉菜单里添加元素
                        $('#selectedRlCategoryName').append(new Option(item.name, item.name));// 下拉菜单里添加元素
                        $('#selectedGrCategoryName').append(new Option(item.name, item.name));// 下拉菜单里添加元素
                        // $('#selectedGzldyid').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                        // $('#selectedWwsqDefName').append(new Option(item.name, item.processDefKey));
                        // $('#selectedDajjdbDefName').append(new Option(item.name, item.name));
                        // $('#selectedDajjybDefName').append(new Option(item.name, item.name));
                        // $('#selectedDajjrlDefName').append(new Option(item.name, item.name));
                    });
                    layui.form.render("select");
                    if(isNotBlank(formSelects)){
                        formSelects.render();
                    }
                }, error: function (e) {
                    response.fail(e,'');
                }
            });
        }

        /**
         * 渲染个人流程名称下拉框
         */
        function loadFilterProcessDefName(){
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/task/process/gr",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                success: function (data) {
                    console.info(data);
                    $('#selectedFilterRoleDefName').append(new Option("请选择", ""));
                    $.each(data, function (index, item) {
                        if(isNotBlank(item)){
                            $('#selectedFilterRoleDefName').append(new Option(item.processDefName, item.processDefKey));// 下拉菜单里添加元素
                        }
                    });
                    layui.form.render("select");
                    //连云港根据流程id默认流程名称取值及入参查询
                    data.forEach(function (item) {
                        if(!isNullOrEmpty(lcid)&&item.processDefKey==lcid){
                            $("#selectedDefName").val(lcid);
                            $("#selectedDefName").parent().find("input").val(item.processDefName)
                            $("#dbSearch").click()
                        }
                    });

                }, error: function (e) {
                    response.fail(e,'');
                }
            });
        }
        /**
         * 渲染部门名称下拉框
         */
        function loadDeptName() {
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/task/dept/all",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                async: false,
                success: function (data) {
                    $('#selectedDeptName').append(new Option("请选择", ""));
                    $('#selectedDoneDeptName').append(new Option("请选择", ""));
                    $('#selectedXmDeptName').append(new Option("请选择", ""));
                    $('#selectedRlDeptName').append(new Option("请选择", ""));
                    $('#selectedGrDeptName').append(new Option("请选择", ""));
                    $('#selectedWwsqDeptName').append(new Option("请选择", ""));
                    $.each(data, function (index, item) {
                        $('#selectedDeptName').append(new Option(item.name, item.id));// 下拉菜单里添加元素
                        $('#selectedDoneDeptName').append(new Option(item.name, item.id));// 下拉菜单里添加元素
                        $('#selectedXmDeptName').append(new Option(item.name, item.id));// 下拉菜单里添加元素
                        $('#selectedRlDeptName').append(new Option(item.name, item.id));// 下拉菜单里添加元素
                        $('#selectedGrDeptName').append(new Option(item.name, item.id));// 下拉菜单里添加元素
                        $('#selectedWwsqDeptName').append(new Option(item.name, item.id));// 下拉菜单里添加元素
                    });
                    layui.form.render("select");
                }, error: function (e) {
                    response.fail(e,'');
                }
            });
        }

        function loadSlrName() {
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/user/listAllUsers",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                async: false,
                success: function (data) {
                    $('#selectedSlr').append(new Option("请选择", ""));
                    $('#selectedXmSlr').append(new Option("请选择", ""));
                    $('#selectedRlSlr').append(new Option("请选择", ""));
                    $('#selectedGrSlr').append(new Option("请选择", ""));
                    $.each(data, function (index, item) {
                        $('#selectedSlr').append(new Option(item.alias, item.alias));// 下拉菜单里添加元素
                        $('#selectedXmSlr').append(new Option(item.alias, item.alias));// 下拉菜单里添加元素
                        $('#selectedRlSlr').append(new Option(item.alias, item.alias));// 下拉菜单里添加元素
                        $('#selectedGrSlr').append(new Option(item.alias, item.alias));// 下拉菜单里添加元素
                    });
                    layui.form.render("select");
                }, error: function (e) {
                    response.fail(e,'');
                }
            });
        }

        /**
         * 渲染区县代码下拉框
         */
        function loadQxdm(){
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/task/getQxdm",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                async: false,
                success: function (data) {
                    $('#qxdm').append(new Option("请选择", ""));
                    $.each(data, function (index, item) {
                        $('#qxdm').append(new Option(item.XZMC, item.XZDM));// 下拉菜单里添加元素
                    });
                    layui.form.render("select");
                }, error: function (e) {
                    response.fail(e,'');
                }
            });
        }

        // 监听部门名称，加载受理人
        layui.form.on('select(selectedDeptName)', function(data) {
            if (lcPageEdition != 'yancheng') {
                return ;
            }
            // 当点击部门名称时，受理人下拉框清空
            $('#selectedSlr option').remove();
            if(isNullOrEmpty(data.value)) {
                //如果部门选空值，则加载所有受理人
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/user/listAllUsers",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        $('#selectedSlr').append(new Option("请选择", ""));
                        $.each(data, function (index, item) {
                            $('#selectedSlr').append(new Option(item.alias, item.alias));// 下拉菜单里添加元素
                        });
                        layui.form.render("select");
                    }, error: function (e) {
                        response.fail(e,'');
                    }
                });
            }else{
                //部门名称有值时，加载该部门下人员
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/task/dept/allusers",
                    contentType: "application/json;charset=utf-8",
                    data: {
                        deptId: data.value
                    },
                    // async: false,
                    success: function (data) {
                        // 当选择一个部门时受理人下拉框全部赋值
                        $('#selectedSlr').append(new Option("请选择", ""));
                        $.each(data, function (index, item) {
                            $('#selectedSlr').append(new Option(item.alias, item.alias));// 下拉菜单里添加元素
                        });
                        layui.form.render("select");
                    }, error: function (e) {
                        response.fail(e,'');
                    }
                });
            }

        });

        layui.form.on('select(selectedXmDeptName)', function(data) {
            if (lcPageEdition != 'yancheng') {
                return ;
            }
            // 当点击部门名称时，受理人下拉框清空
            $('#selectedXmSlr option').remove();
            if(isNullOrEmpty(data.value)) {
                //如果部门选空值，则加载所有受理人
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/user/listAllUsers",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        $('#selectedXmSlr').append(new Option("请选择", ""));
                        $.each(data, function (index, item) {
                            $('#selectedXmSlr').append(new Option(item.alias, item.alias));// 下拉菜单里添加元素
                        });
                        layui.form.render("select");
                    }, error: function (e) {
                        response.fail(e,'');
                    }
                });
            }else {
                //部门名称有值时，加载该部门下人员
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/task/dept/allusers",
                    contentType: "application/json;charset=utf-8",
                    data: {
                        deptId: data.value
                    },
                    // async: false,
                    success: function (data) {
                        $('#selectedXmSlr').append(new Option("请选择", ""));
                        $.each(data, function (index, item) {
                            $('#selectedXmSlr').append(new Option(item.alias, item.alias));// 下拉菜单里添加元素
                        });
                        layui.form.render("select");
                    }, error: function (e) {
                        response.fail(e, '');
                    }
                });
            }
        });

        layui.form.on('select(selectedGrDeptName)', function(data) {
            if (lcPageEdition != 'yancheng') {
                return ;
            }
            // 当点击部门名称时，受理人下拉框清空
            $('#selectedGrSlr option').remove();
            if(isNullOrEmpty(data.value)) {
                //如果部门选空值，则加载所有受理人
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/user/listAllUsers",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        $('#selectedGrSlr').append(new Option("请选择", ""));
                        $.each(data, function (index, item) {
                            $('#selectedGrSlr').append(new Option(item.alias, item.alias));// 下拉菜单里添加元素
                        });
                        layui.form.render("select");
                    }, error: function (e) {
                        response.fail(e,'');
                    }
                });
            }else {
                //部门名称有值时，加载该部门下人员
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/task/dept/allusers",
                    contentType: "application/json;charset=utf-8",
                    data: {
                        deptId: data.value
                    },
                    // async: false,
                    success: function (data) {
                        $('#selectedGrSlr').append(new Option("请选择", ""));
                        $.each(data, function (index, item) {
                            $('#selectedGrSlr').append(new Option(item.alias, item.alias));// 下拉菜单里添加元素
                        });
                        layui.form.render("select");
                    }, error: function (e) {
                        response.fail(e, '');
                    }
                });
            }
        });

        layui.form.on('select(selectedRlDeptName)', function(data) {
            if (lcPageEdition != 'yancheng') {
                return ;
            }
            // 当点击部门名称时，受理人下拉框清空
            $('#selectedRlSlr option').remove();
            if(isNullOrEmpty(data.value)) {
                //如果部门选空值，则加载所有受理人
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/user/listAllUsers",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        $('#selectedRlSlr').append(new Option("请选择", ""));
                        $.each(data, function (index, item) {
                            $('#selectedRlSlr').append(new Option(item.alias, item.alias));// 下拉菜单里添加元素
                        });
                        layui.form.render("select");
                    }, error: function (e) {
                        response.fail(e,'');
                    }
                });
            }else {
                //部门名称有值时，加载该部门下人员
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/task/dept/allusers",
                    contentType: "application/json;charset=utf-8",
                    data: {
                        deptId: data.value
                    },
                    // async: false,
                    success: function (data) {
                        $('#selectedRlSlr').append(new Option("请选择", ""));
                        $.each(data, function (index, item) {
                            $('#selectedRlSlr').append(new Option(item.alias, item.alias));// 下拉菜单里添加元素
                        });
                        layui.form.render("select");
                    }, error: function (e) {
                        response.fail(e, '');
                    }
                });
            }
        });

        layui.form.on('select(selectedCategoryName)', function (data) {
            var selectedCategory = data.value;
            $('#selectedCategoryName').val(selectedCategory);// 下拉菜单里添加元素
            $('#selectedDoneCategoryName').val(selectedCategory);// 下拉菜单里添加元素
            $('#selectedXmCategoryName').val(selectedCategory);// 下拉菜单里添加元素
            $('#selectedRlCategoryName').val(selectedCategory);// 下拉菜单里添加元素
            $('#selectedGrCategoryName').val(selectedCategory);// 下拉菜单里添加元素
            if(isNullOrEmpty(selectedCategory)){
                loadProcessDefName(null);
                return;
            }
            //获取分类下的工作流
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/task/list/category/processId?category="+encodeURIComponent(selectedCategory),
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                async: false,
                success: function (data) {
                    //
                    categoryProcessDefNames = data;
                    loadProcessDefName(data);
                }, error: function (e) {
                    response.fail(e,'');
                }
            });
        });

        /**
         * 渲染审批来源下拉框
         */
        function loadSplyName() {
            var keys = [];
            $.ajax({
                url: getContextPath() + "/rest/v1.0/task/zd",
                type: "GET",
                contentType: 'application/json',
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data != null && data.sply != null) {
                        splyzdx = data.sply;
                        $('#dbSply').append(new Option("请选择", ""));
                        $('#ybSply').append(new Option("请选择", ""));
                        $('#xmSply').append(new Option("请选择", ""));
                        $('#grSply').append(new Option("请选择", ""));
                        $('#rlSply').append(new Option("请选择", ""));
                        $(".ywlxSelect").append(new Option("请选择", ""));
                        $.each(data.sply, function (index, item) {
                            $('#dbSply').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
                            $('#ybSply').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
                            $('#xmSply').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
                            $('#grSply').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
                            $('#rlSply').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
                        });
                        $.each(data.ywlx, function (index, item) {
                            $('.ywlxSelect').append(new Option(item.MC, item.MC));// 下拉菜单里添加元素
                        });
                        layui.form.render("select");
                        formSelects.data('dbSplySelect','local',{arr:data.sply});
                        formSelects.data('ybSplySelect','local',{arr:data.sply});
                        formSelects.data('rlSplySelect','local',{arr:data.sply});
                        formSelects.data('xmSplySelect','local',{arr:data.sply});
                        formSelects.data('grSplySelect','local',{arr:data.sply});
                    }

                },
                error: function (e) {
                    response.fail(e, '获取字典项失败！');
                }
            });
        }

        function loadQlrlx() {
            $.ajax({
                url: getContextPath() + "/rest/v1.0/task/zd",
                type: "GET",
                contentType: 'application/json',
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data != null && data.qlrlx != null) {
                        $('#dbQlrlx').append(new Option("请选择", ""));
                        $('#xmQlrlx').append(new Option("请选择", ""));
                        $('#grQlrlx').append(new Option("请选择", ""));
                        $('#rlQlrlx').append(new Option("请选择", ""));
                        $.each(data.qlrlx, function (index, item) {
                            $('#dbQlrlx').append(new Option(item.MC, item.MC));// 下拉菜单里添加元素
                            $('#xmQlrlx').append(new Option(item.MC, item.MC));// 下拉菜单里添加元素
                            $('#grQlrlx').append(new Option(item.MC, item.MC));// 下拉菜单里添加元素
                            $('#rlQlrlx').append(new Option(item.MC, item.MC));// 下拉菜单里添加元素
                        });
                    }
                    layui.form.render("select");
                },
                error: function (e) {
                    response.fail(e, '获取字典项失败！');
                }
            });
        }

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

        // 系统默认展开常用收藏
            $('.bdc-operate-show>.layui-icon-down').trigger('click');
    });

    function renderSearchList(processDefName) {
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/task/list",
            data: {
                processDefName: processDefName
            },
            success: function (data) {
                var getSearchTpl = searchTpl.innerHTML
                    , searchView = document.getElementById('searchView');
                laytpl(getSearchTpl).render(data, function (html) {
                    searchView.innerHTML = html;
                });
                changeTableHeight();
            }, error: function (e) {
                response.fail(e,'');
            }
        });
    }



    function refreshTab(url, id, authorityCode, obj) {
        if (obj) {
            table.reload(id, {
                url: url,
                where: obj,
                done: function (rec) {
                    console.log(rec);
                    tableData = rec.data
                    changeTableHeight();
                    var reverseList = ['zl'];
                    reverseTableCell(reverseList);
                    if (isNullOrEmpty(authorityCode)) {
                        authorityLoad(moduleAuthorityLoad(authorityCode));
                    } else {
                        moduleAuthority.load({
                            authorityCode: authorityCode
                        });
                    }
                }
            });
        } else {
            table.reload(id, {
                url: url,
                done: function (rec) {
                    console.log(rec);
                    tableData = rec.data
                    changeTableHeight();
                    var reverseList = ['zl'];
                    reverseTableCell(reverseList);
                    if (isNullOrEmpty(authorityCode)) {
                        authorityLoad(moduleAuthorityLoad(authorityCode));
                    } else {
                        moduleAuthority.load({
                            authorityCode: authorityCode
                        });
                    }
                }
            });
        }

        moduleAuthority.load({
            authorityCode: authorityCode
        });
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

//连云港默认携带代码参数
    var url = window.location.search;
    //跳转的url多加了index=0以此来判断是从主页跳转来到待办事宜的
   if (url.indexOf("index=0") != -1){
        $("#seniorSearch").click()
        //获取配置项的流程id
        getpz()
    }

   function moduleAuthorityLoad(Codedata){
       moduleAuthority.load({
           authorityCode: Codedata
       });
   }
});


function refreshOpen(msg){
    newWin.forEach(function(v){
        v.layer.open({
            title: msg.msgTypeName
            , content: '您的账户已在别处登陆，您被迫下线，请核实！'
            , yes: function (index, layero) {
                v.window.location.reload();
            }, cancel: function () {
                v.window.location.reload();
            }
        });
    });
}

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

function recordLog(slbh, taskId, gzlslid, rwlb) {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/task/log",
        data: {slbh: slbh, taskId: taskId, gzlslid: gzlslid, rwlb: rwlb},
        success: function () {
            console.log("日志记录成功");
        },
        error: function (xhr) {
            delAjaxErrorMsg(xhr);
        }
    })
}

//设置模糊类型
function resetMhlx(rwlblx){
    $("." + rwlblx + "-cxtj-mhlx").each(function (i) {
        var $input =$(this).find(".layui-input");
        var searchClass =rwlblx+"Search";
        $(this).addClass("cxtj-mhlx-css");
        $(this).append('<div class="bdc-screen-inline">' +
            '<select  name=' + $input.attr("name") + 'mh'+' class="bdc-filter-select '+searchClass+'">' +
            '<option value="1">精确</option>' +
            '<option value="4">全模糊</option>' +
            '</select>' +
            '</div>'
        );
    });

}
//判断选择的数据是否同一流程
function sfytlc(checkStatus){
    var falg = true;
    var selectData = checkStatus.data;
    var gzldymc;
    // 遍历数据判断是否选择了相同的流程批量转发
    for(var i =0; i<selectData.length; i++){
        if (isNullOrEmpty(gzldymc)) {
            gzldymc = selectData[i].processKey;
        } else {
            if (gzldymc !== selectData[i].processKey) {
                falg =  false;
                break;
            }
        }
    }
    return falg;
}

//读取配置项
function getpz(){
    getReturnData("/rest/v1.0/config/pz", {}, "GET", function (data) {
        var url = "/realestate-portal-ui/view/listTasks.html?clientId=initialClientId&moduleCode=100&loadHome=true&viewType=";
        if(data && data.lcid){
            lcid = data.lcid;
        }
    },true)
}

function getNodePermission(taskData){
    var nodePermission;
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/task/nodePermission",
        async: false,
        data: {
            startTime_todo_js: taskData.startTime,
            processDefName: taskData.processKey,
            taskName: taskData.taskName
        },
        success: function (data) {
            nodePermission = data;
        }, error: function (e) {
            response.fail(e, '');
        }
    });
    return nodePermission;
}

//跳转流程页面校验
function openWindow(obj){
    layui.use(['workflow'] ,function () {
        var workflow = layui.workflow;
        if (todoPermission === true && obj.data.ROW_ID != 1) {
            var nodePermission = getNodePermission(obj.data);
            if (nodePermission) {
                warnMsg("当前任务不允许打开，请按序办理业务！");
                return;
            }
        }
        if (!workflow.showHangReson(obj.data)) {
            return false;
        }
        //锁定任务
        workflow.lockProcess(obj.data);
        recordLog(obj.data.slbh, obj.data.taskId, obj.data.processInstanceId, "待办任务");
        var waitCzTimer = setInterval(function () {
            clearInterval(waitCzTimer);
            var index = window.open("./new-page.html?name=" + obj.data.taskId + "&type=todo", "待办任务");
            newWin.push(index);
        }, 50);
    });
}

function authorityLoad(callback){
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/workflow/process-instances/module/authority",
        data: {
            moduleCode: 'rwlb',
        },
        success: function (data) {
            console.log(data);
            authorityCode = data;
            if (typeof callback === 'function') {
                //callback是一个函数，才能当回调函数使用
                callback(authorityCode);
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}