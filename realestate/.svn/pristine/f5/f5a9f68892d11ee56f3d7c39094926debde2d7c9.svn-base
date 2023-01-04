/**
 * Created by Administrator on 2019/1/21.
 */
layui.use(['jquery', 'table', 'element', 'carousel', 'form', 'laytpl', 'laydate', 'layer','workflow','response', 'moduleAuthority'], function () {
    var $ = layui.jquery,
        table = layui.table,
        element = layui.element,
        laytpl = layui.laytpl,
        layer = layui.layer,
        laydate = layui.laydate,
        carousel = layui.carousel,
        response = layui.response,
        workflow = layui.workflow,
        moduleAuthority = layui.moduleAuthority;
    $(function () {
        //初始化日期控件
        loadDate();

        function loadDate() {
            lay('.test-item').each(function(){
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
                    trigger: 'click'
                });
            });
        }
        function completeDate(date1,date2){
            var oDate1 = new Date(date1);
            var oDate2 = new Date(date2);
            if(oDate1.getTime() > oDate2.getTime()){
                return true;
            } else {
                return false;
            }
        }

        var $paramArr = getIpHz();
        var clientId = $paramArr['clientId'];
        // 模块编码
        var moduleCode = $paramArr['moduleCode'];
        // 加载登记首页
        var loadHome = $paramArr['loadHome'];
        var authorityCode = '';
        // 获取子系统列表
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/user/modules/portaluiclient/subsystem",
            async :false,
            success: function (data) {
                //路径不完整
                var pathNotAll=isNullOrEmpty(clientId);
                if(data){
                    data.forEach(function (v) {
                        if (pathNotAll && v.url.indexOf("clientId=") > -1) {
                            window.location.href = v.url;
                            window.reload();
                        }
                        if (v.url.indexOf(clientId)>-1) {
                            $('.bdc_subsystem').html(v.name);
                            //没有取到权限并且路径里有
                            if(isNullOrEmpty(moduleCode) && !isNullOrEmpty(GetQueryString(v.url,"moduleCode"))){
                                window.location.href=v.url;
                                window.reload();
                            }
                        }
                    });
                }
                var getSystemTpl = systemTyl.innerHTML
                    , getSystemView = document.getElementById('systemView');
                laytpl(getSystemTpl).render(data, function (html) {
                    getSystemView.innerHTML = html;
                });
            }, error: function (e) {
                response.fail(e,'');
            }
        });

        //加载消息中心
        reloadMsg();

        //获取登记访问首页的权限
        getReturnData("/rest/v1.0/user/loadHome", {}, "GET", function (data) {
            if(data){
                $("#homeDiv").addClass("layui-show");
            }else{
                loadHome=false;
            }
        },true);

        //按钮权限
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/module/authority",
            data: {moduleCode: moduleCode},
            success: function (data) {
                console.log(data);
                authorityCode = data;
                moduleAuthority.load({
                    authorityCode: data
                });
            }, error: function (e) {
                response.fail(e,'');
            }
        });
        //点击退出
        $('.bdc-logout').on('click', function () {
            var logoutIndex = layer.confirm('您确定要退出吗？', {
                title: '提示',
                btn: ['确定', '取消'] //按钮
            }, function () {
                layer.close(logoutIndex);
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/user/logout",
                    success: function (data) {
                        var path=document.location.protocol + "//"+location.host+location.pathname;
                        var search="?t="+(new Date()).getTime();
                        for (var parami in $paramArr) {
                            //加的时间戳不处理
                            if (parami == "t") {
                                continue;
                            }
                            search += "&" + parami + "=" + $paramArr[parami]
                        }
                        window.location.href=data+path+encodeURIComponent(search);
                    }, error: function (e) {
                        response.fail(e,'');
                    }
                });
            });
        });

        //获取基本信息
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/user/info",
            data: {},
            success: function (data) {
                //消息中心推送
                webSocket(data.username);
                $('.bdc-person-nav .bdc-user-name').html(data.alias);
            }, error: function (e) {
                response.fail(e,'');
            }
        });

        //点击基本信息
        $('.bdc-message').on('click', function () {
            $(this).parents('.bdc-person-child').removeClass('layui-show');
            var $parentLi = $('.layui-side-menu .layui-nav-tree li');
            var liLength = $parentLi.length;
            $parentLi.removeClass('layui-nav-itemed');
            $($parentLi[liLength - 1]).addClass('layui-nav-itemed');
            $($parentLi[liLength - 1]).find('dd:first-child a').click();
        });
        //点击修改密码
        $('.bdc-update').on('click', function () {
            $(this).parents('.bdc-person-child').removeClass('layui-show');
            var $parentLi = $('.layui-side-menu .layui-nav-tree li');
            var liLength = $parentLi.length;
            $parentLi.removeClass('layui-nav-itemed');
            $($parentLi[liLength - 1]).addClass('layui-nav-itemed');
            $($parentLi[liLength - 1]).find('dd:last-child a').click();
        });

        // 侧边栏动态渲染
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/user/menu/" + clientId,
            data: {},
            success: function (data) {
                if(data){
                    data.push({
                        name: '设置',
                        childTree: [
                            {
                                childTree: [],
                                name: '基本资料',
                                mark: 'basic',
                                url: getContextPath() + '/view/basic-info.html'
                            },
                            {
                                childTree: [],
                                name: '修改密码',
                                mark: 'update',
                                url: getContextPath() + '/view/update-pwd.html'
                            }
                        ]
                    });
                }
                var getAsideTpl = asideTpl.innerHTML
                    , getAsideView = document.getElementById('LAY-system-side-menu');
                laytpl(getAsideTpl).render(data, function (html) {
                    getAsideView.innerHTML = html;
                });
                element.render('nav', 'layadmin-system-side-menu');
                if(loadHome!='true'){
                    var data = $('.layui-nav-tree .layui-nav-item:first-child a');
                    if(data && data.length>0){
                        for( var i=0,len=data.length; i<len;i++){
                            if($(data[i]).attr("lay-href")){
                                $(data[i]).click();
                                break;
                            }else{
                                $(data[i]).click();
                            }
                        }
                    }
                }
            },
            error: function (e) {
                response.fail(e,'');
            }
        });
        //1. 监听侧边栏点击
        $('.layui-side-menu .layui-nav-item .layui-nav-child').on('click', function () {
            $('.bdc-four-list').hide();
            // $('.breadcrumb-div cite').html($(this).siblings().find('cite').html());
        });
        $('.layui-side-menu .bdc-mime').on('click', function () {
            $('.bdc-four-list').hide();
            // $('.breadcrumb-div cite').html($(this).find('cite').html());
        });
        //1.1 监听三级菜单点击
        $('.layui-side-menu .layui-nav').on('click', '.layui-nav-child .layui-nav-child a', function (e) {
            // console.log($(this).offset().top);
            // e.stopPropagation();
            $('.bdc-four-list').hide();
            $(this).siblings().css('top', $(this).offset().top).toggle();
        });
        //1.2 点击四级菜单点击
        $('.layui-side').on('click', '.bdc-four-list p', function () {
            $(this).parent().css('display', 'none');
        });

        //2. 调整左侧滑动条的位置
        $('.layui-side-menu .layui-nav-item>a').on('click', function () {
            $('.bdc-four-list').hide();
            $('.layui-nav-bar').css({'height': '50px', 'opacity': 1, 'top': $(this).parent().index() * 50});
        });

        $('#flexible').click(function () {
            setTimeout(function () {
                renderCurrentTable();
            },300);
        });

        //3. 消息列表相关
        //3.1 消息列表高度
        $('.bdc-msg-child').height($('.layui-body').height() + 50);
        $('.bdc-msg-child .layui-tab-item').height($('.layui-body').height() - 116);
        //3.2 点击消息列表中的‘×’，关闭
        // $('.bdc-msg-title .layui-icon-close').on('click', function () {
        //     $('.bdc-msg-child').removeClass('layui-show');
        // });

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
        //加载首页的话处理
        if(loadHome=='true'){
            //4. 轮播图
            //4.0 渲染常用收藏内容
            var commonConnection = false;
            var connectionList = false;
            //监听收藏tab切
            var taskTabClick = 0;
            var taskTabListClick = 0;
            //监听收藏任务tab切换
            element.on('tab(task)', function (data) {
                if(taskTabClick == 0 && data.index == 0){
                    taskTabClick++;
                    renderCommonCollection();
                }
                if(taskTabListClick == 0 && data.index == 1){
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

            var hasMove=false,isStarClick = false;
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
                        changeTableHeight();

                        var oUl= document.getElementById("collectionContent");
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
                        function setDrag(obj){
                            obj.onmousedown = function(event){
                                hasMove=false;
                                var scrollTop = document.documentElement.scrollTop||document.body.scrollTop;
                                var scrollLeft = document.documentElement.scrollLeft||document.body.scrollLeft;
                                obj.style.zIndex = minZindex++;
                                //当鼠标按下时计算鼠标与拖拽对象的距离
                                disX = event.clientX +scrollLeft-obj.offsetLeft;
                                disY = event.clientY +scrollTop-obj.offsetTop;

                                objL = obj.style.left;
                                objL = objL.substring(0,objL.length-2);

                                objT = obj.style.top;
                                objT = objT.substring(0,objT.length-2);
                                document.onmousemove=function(event){
                                    hasMove=true;
                                    //当鼠标拖动时计算div的位置
                                    var l = event.clientX -disX +scrollLeft;
                                    var t = event.clientY -disY + scrollTop;
                                    obj.style.left = l + "px";
                                    obj.style.top = t + "px";

                                    moveL = event.clientX -disX +scrollLeft;
                                    // console.log(moveL);
                                    moveT = event.clientY -disY + scrollTop;
                                    for(var i=0;i<aLi.length;i++){
                                        aLi[i].className = "";
                                    }
                                    var oNear = findMin(obj);
                                    if(oNear){
                                        oNear.className = "active";
                                    }
                                };
                                document.onmouseup = function(){
                                    document.onmousemove = null;//当鼠标弹起时移出移动事件
                                    document.onmouseup = null;//移出up事件，清空内存
                                    if(hasMove){
                                        //console.log('moveL:'+moveL,"objL:"+objL);
                                        //console.log('moveT:'+moveT,"objT:"+objT);
                                        if(((moveL != 99999 || moveT != 99999) && (moveL != objL && moveL > objL + 10)) || ((moveL != 99999 || moveT != 99999) && (moveT != objT && moveT > objT - 10)) || ((moveL != 99999 || moveT != 99999) && (moveT != objT && moveT < objT + 10)) || ((moveL != 99999 || moveT != 99999) && (moveL != objL && moveL < objL - 10))){
                                            //console.log('aaa');
                                            //检测是否普碰上，在交换位置
                                            var oNear = findMin(obj);
                                            if(oNear){
                                                //console.log(oNear);//被移动的
                                                //console.log(obj);//移动的
                                                moveCollection(oNear, obj);
                                                oNear.className = "";
                                                oNear.style.zIndex = minZindex++;
                                                obj.style.zIndex = minZindex++;
                                                startMove(oNear,aPos[obj.index]);
                                                startMove(obj,aPos[oNear.index]);
                                                //交换index
                                                oNear.index += obj.index;
                                                obj.index = oNear.index - obj.index;
                                                oNear.index = oNear.index - obj.index;
                                            }else{

                                                startMove(obj,aPos[obj.index]);
                                            }
                                        }
                                        else {
                                            obj.style.left = objL + "px";
                                            obj.style.top = objT + "px";
                                            $('#collectionContent').find('li').removeClass('active');
                                        }
                                    }else{
                                        //console.log("没有移动鼠标松开事件,模拟click");
                                        if(!isStarClick){
                                            var useData = {
                                                processDefKey: $(obj).find('a .layui-icon').data('code'),
                                                name: $(obj).find('a .layui-icon').data('name')
                                            };
                                            workflow.startUpProcess(useData);
                                        }
                                        isStarClick = false;
                                    }
                                    hasMove=false;
                                };
                                clearInterval(obj.timer);
                                return false;//低版本出现禁止符号
                            }
                        }
                        //碰撞检测
                        function colTest(obj1,obj2){
                            var t1 = obj1.offsetTop;
                            var r1 = obj1.offsetWidth+obj1.offsetLeft;
                            var b1 = obj1.offsetHeight+obj1.offsetTop;
                            var l1 = obj1.offsetLeft;

                            var t2 = obj2.offsetTop;
                            var r2 = obj2.offsetWidth+obj2.offsetLeft;
                            var b2 = obj2.offsetHeight+obj2.offsetTop;
                            var l2 = obj2.offsetLeft;

                            if(t1>b2||r1<l2||b1<t2||l1>r2){
                                return false;
                            }else{
                                return true;
                            }
                        }
                        //勾股定理求距离
                        function getDis(obj1,obj2){
                            var a = obj1.offsetLeft-obj2.offsetLeft;
                            var b = obj1.offsetTop-obj2.offsetTop;
                            return Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
                        }
                        //找到距离最近的
                        function findMin(obj){
                            var minDis = 999999999;
                            var minIndex = -1;
                            for(var i=0;i<aLi.length;i++){
                                if(obj==aLi[i])continue;
                                if(colTest(obj,aLi[i])){
                                    var dis = getDis(obj,aLi[i]);
                                    if(dis<minDis){
                                        minDis = dis;
                                        minIndex = i;
                                    }
                                }
                            }
                            if(minIndex==-1){
                                return null;
                            }else{
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
                    var newHeight = Math.ceil($('.bdc-collection-content li').length/4) * 26;
                    $('.bdc-collection-content').css("height", newHeight+'px');
                } else {
                    $('.bdc-collection-content').css('height', '52px');
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
                        if(data && data.content){
                            var carouselData = [];
                            for (var i = 0; i < Math.ceil(data.content.length / 12); i++) {
                                carouselData.push([]);
                            }
                            data.content.forEach(function (v, i) {
                                var getIndex = i + 1;
                                carouselData[Math.ceil(getIndex / 12) - 1].push(v);
                            });
                            var getCarouselTpl = carouselTpl.innerHTML
                                , getCarouselView = document.getElementById('carouselView');
                            laytpl(getCarouselTpl).render(carouselData, function (html) {
                                getCarouselView.innerHTML = html;
                            });

                            carousel.render({
                                elem: '#buildTask',
                                width: '100%',
                                height: '100px',
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
                var lbIndex = Math.ceil(($(this).index()+1)/12);
                var lineIndex = Math.ceil(($(this).index())%6);

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
                    $('.bdc-task-tab .bdc-task-tools').css('width','310px');
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
            $('body').on('click',function(){
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
                workflow.startUpProcess(useData);
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
                workflow.startUpProcess(useData);
            });

            // $buildCollectionTask.on('click', '.bdc-collection-content>li>a', function () {
            //     var useData = {
            //         processDefKey: $(this).find('.layui-icon').data('code'),
            //         name: $(this).find('.layui-icon').data('name')
            //     };
            //     startUpProcess(useData);
            // });

            //4.5.6 点击搜索 input框
            $('.bdc-task-search-box .layui-input').on('focus', function () {
                if($('.bdc-task-content').hasClass('bdc-hide')){
                    taskSearchState = false;
                    $('.bdc-operate-show .layui-icon').toggleClass('bdc-hide');
                }

                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $('.bdc-task-tab .bdc-task-tools').animate({'width': '95%'});
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
                    if(!isNullOrEmpty(inputText)){
                        renderSearchList(inputText);
                    }
                }
            });
            $('.bdc-task-search-box .layui-input').on('focus',function () {
                isSearch = false;
            }).on('blur',function () {
                isSearch = true;
            });
            $('.bdc-table-box').on('focus','.layui-laypage-skip .layui-input',function () {
                isSearch = false;
            }).on('blur','.layui-laypage-skip .layui-input',function () {
                isSearch = true;
            });

            //表格监听回车事件
            // $('.layui-input-inline .layui-input').bind('keyup', function (event) {
            //     if (event.keyCode == "13") {
            //         $(".layui-show .bdc-search-box .searchBtn").click();
            //     }
            // });
            $(document).keydown(function (event) {
                if (event.keyCode == "13") {
                    if(isSearch){
                        $(".layui-show .bdc-search-box .searchBtn").click();
                    }
                }
            });

            //监听上一个下一个
            var upTime;
            $('.bdc-content-box').on('mousewheel DOMMouseScroll','.bdc-build-task',function(e){
                clearInterval(upTime);
                var delta = -e.originalEvent.wheelDelta || e.originalEvent.detail;//firefox使用detail:下3上-3,其他浏览器使用wheelDelta:下-120上120//下滚
                if(delta>0){
                    upTime = setTimeout(function(){
                        //console.log('下滚');
                        $('.layui-carousel-arrow[lay-type=add]').click();
                    },300);
                }
                //上滚
                if(delta<0){
                    upTime = setTimeout(function(){
                        //console.log('上滚');
                        $('.layui-carousel-arrow[lay-type=sub]').click();
                    },300);
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
                if (commonConnection) {
                    commonConnection = false;
                    renderLbList();
                }
                if (connectionList) {
                    connectionList = false;
                    renderCommonCollection();
                }

                if(!($('.bdc-task-tools').css('width') == '310px')){
                    $('.bdc-task-tab .bdc-task-tools').animate({'width': '310px'});
                    $('.bdc-task-tab>.layui-tab-title>li').css('visibility', 'visible');
                    if(taskSearchState){
                        $('.bdc-task-content').removeClass('bdc-hide');
                    }else {
                        $('.bdc-operate-show .layui-icon').toggleClass('bdc-hide');
                    }
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
            renderListTable( listTableId, listCurrentId, allToolbar);
            // 6.4 认领列表表格
            var rlUrl = getContextPath() + "/rest/v1.0/task/claim/list";
            var rlTableId = '#rlTable';
            var rlCurrentId = 'rlTableId';
            var rlTableToolbar = '#rlTableToolbar';
            renderRlTable( rlTableId, rlCurrentId, rlTableToolbar);

            var doneIndex = 0,
                listIndex = 0,
                rlindex = 0;

            //监听第一次单击任务栏tab，重构表格尺寸
            element.on('tab(listFilter)', function (data) {
                if (data.index == 0) {
                    refreshTab(waitUrl,waitCurrentId, authorityCode);
                } else if (data.index == 1) {
                    if (doneIndex == 0) {
                        doneIndex++;
                        table.resize(doneCurrentId);
                    }
                    refreshTab(doneUrl,doneCurrentId, authorityCode);
                } else if (data.index == 2) {
                    if (listIndex == 0) {
                        listIndex++;
                        table.resize(listCurrentId);
                    }
                    refreshTab(listUrl,listCurrentId, authorityCode);
                } else if (data.index == 3) {
                    if (rlindex == 0) {
                        rlindex++;
                        table.resize(rlCurrentId);
                    }
                    refreshTab(rlUrl,rlCurrentId, authorityCode);
                }
            });

            //6.4 待办任务头工具栏事件
            table.on('toolbar(waitTableFilter)', function (obj) {
                var checkStatus = table.checkStatus(obj.config.id);
                if (obj.event != "LAYTABLE_COLS") {
                    var selectedNum=checkStatus.data.length;
                    var plEvent=(obj.event == "hang-up-process" || obj.event == "active-process" || obj.event == "delete-process" || obj.event == "sign-process" || obj.event == "forward-pl-process");
                    if ((selectedNum != 1 && !plEvent) || (selectedNum ==0 && plEvent)) {
                        layer.msg('请选择一条数据进行操作！');
                        return false;
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
                        workflow.backProcess(checkData, waitUrl, waitTableId, waitCurrentId,true);
                        break;
                    case 'active-process':
                        // 激活流程
                        workflow.activeProcess(checkStatus, waitUrl, waitTableId, waitCurrentId);
                        break;
                    case 'delete-process':
                        // 删除当前任务
                        workflow.deleteProcess(checkStatus, waitUrl, waitTableId, waitCurrentId,true);
                        break;
                    case 'hang-up-process':
                        workflow.hangUpProcess(checkStatus, waitUrl, waitTableId, waitCurrentId);
                        break;
                    case 'sign-process':
                        //批量签名
                        workflow.signProcess(checkStatus, waitUrl, waitTableId, waitCurrentId);
                        break;
                    case 'forward-pl-process':
                        //批量转发
                        workflow.forwardPlProcess(checkStatus, waitUrl, waitTableId, waitCurrentId);
                        break;
                }
            });
            //认领工具栏
            table.on('toolbar(rlTableFilter)', function (obj) {
                var checkStatus = table.checkStatus(obj.config.id);
                var selectedNum = checkStatus.data.length;
                if (selectedNum == 0) {
                    layer.msg('请选择一条数据进行操作！');
                    return false;
                }
                switch (obj.event) {
                    case 'claim-process':
                        workflow.claimProcess(checkStatus, rlUrl, rlTableId, rlCurrentId);
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
                if (obj.event != "LAYTABLE_COLS") {
                    if (checkStatus.data.length != 1) {
                        layer.msg('请选择一条数据进行操作！');
                        return false;
                    }
                }
                var checkData = checkStatus.data[0];
                switch (obj.event) {
                    case 'fetch-process':
                        workflow.fetchProcess(checkData, doneUrl, doneTableId, doneCurrentId,true);
                        break;
                }
            });

            // 6.5 监听行双击事件
            //监听待办 行双击
            table.on('rowDouble(waitTableFilter)', function (obj) {
                //得到当前行数据
                var lcArray = {
                    taskId: obj.data.taskId,
                    formKey: obj.data.formKey,
                    processInstanceId: obj.data.processInstanceId,
                    processDefName: obj.data.processDefName,
                    processInstanceType: 'todo'
                };
                if(!workflow.showHangReson(obj.data)){
                    return false;
                }
                sessionStorage.setItem('lcArray' + obj.data.taskId, JSON.stringify(lcArray));
                //锁定任务
                workflow.lockProcess(obj.data);
                window.open("./new-page.html?name=" + obj.data.taskId, "待办任务");
            });

            table.on('tool(waitTableFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
                if (obj.event === 'openpage') {
                    //得到当前行数据
                    var lcArray = {
                        taskId: obj.data.taskId,
                        formKey: obj.data.formKey,
                        processInstanceId: obj.data.processInstanceId,
                        processDefName: obj.data.processDefName,
                        processInstanceType: 'todo'
                    };
                    if (!workflow.showHangReson(obj.data)) {
                        return false;
                    }
                    sessionStorage.setItem('lcArray' + obj.data.taskId, JSON.stringify(lcArray));
                    //锁定任务
                    workflow.lockProcess(obj.data);
                    window.open("./new-page.html?name=" + obj.data.taskId, "待办任务");
                }
            });

            // 监听已办 行双击
            table.on('rowDouble(doneTableFilter)', function (obj) {
                //console.log(obj.data); //得到当前行数据
                if(!workflow.showHangReson(obj.data)){
                    return false;
                }
                var lcArray = {
                    taskId: obj.data.taskId,
                    formKey: obj.data.formKey,
                    processInstanceId: obj.data.processInstanceId,
                    processDefName: obj.data.processDefName,
                    processInstanceType: 'done'
                };
                sessionStorage.setItem('lcArray' + obj.data.taskId, JSON.stringify(lcArray));
                window.open("./new-page.html?name=" + obj.data.taskId, obj.data.processDefName);
            });

            table.on('tool(doneTableFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
                if (obj.event === 'openpage') {
                    //console.log(obj.data); //得到当前行数据
                    if (!workflow.showHangReson(obj.data)) {
                        return false;
                    }
                    var lcArray = {
                        taskId: obj.data.taskId,
                        formKey: obj.data.formKey,
                        processInstanceId: obj.data.processInstanceId,
                        processDefName: obj.data.processDefName,
                        processInstanceType: 'done'
                    };
                    sessionStorage.setItem('lcArray' + obj.data.taskId, JSON.stringify(lcArray));
                    window.open("./new-page.html?name=" + obj.data.taskId, obj.data.slbh);
                }
            });
            // 监听项目列表 行双击
            table.on('rowDouble(listTableFilter)', function (obj) {
                if (!workflow.showHangReson(obj.data)) {
                    return false;
                }
                //得到当前行数据
                var listArray = {
                    processInstanceId: obj.data.processInstanceId,
                    processDefName: obj.data.processDefName,
                    processInstanceType: 'list'
                };
                sessionStorage.setItem('listArray' + obj.data.processInstanceId, JSON.stringify(listArray));
                window.open(getContextPath() + "/view/new-page.html?name=" + obj.data.processInstanceId, obj.data.slbh);
            });

            table.on('tool(listTableFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
                if (obj.event === 'openpage') {
                    if (!workflow.showHangReson(obj.data)) {
                        return false;
                    }
                    //得到当前行数据
                    var listArray = {
                        processInstanceId: obj.data.processInstanceId,
                        processDefName: obj.data.processDefName,
                        processInstanceType: 'list'
                    };
                    sessionStorage.setItem('listArray' + obj.data.processInstanceId, JSON.stringify(listArray));
                    window.open("./new-page.html?name=" + obj.data.processInstanceId, obj.data.slbh);
                }
            });

            //查询
            var searchObj = {
                "dbSearch": "dbTable",
                "ybSearch": "doneTableId",
                "xmSearch": "listTableId",
                "rlSearch": "rlTableId"
            };
            $('.searchBtn').on('click', function () {
                var id = this.id;
                // 获取查询内容
                var obj = {};
                $("." + id).each(function (i) {
                    var value = $(this).val();
                    var name = $(this).attr('name');
                    obj[name] = value;
                });
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
                $(this).parent().toggleClass('bdc-button-box-four');
                if($(this).html() == '高级查询'){
                    $(this).html('收起');
                }else {
                    $(this).html('高级查询');
                }
                changeTableHeight();
            });
            //点击高级查询--4的倍数
            $('#seniorybSearch').on('click', function () {
                $('.pf-senior-yb-show').toggleClass('bdc-hide');
                $(this).parent().toggleClass('bdc-button-box-four');

                if($(this).html() == '高级查询'){
                    $(this).html('收起');
                }else {
                    $(this).html('高级查询');
                }
                changeTableHeight();
            });
            //点击高级查询--4的倍数
            $('#seniorxmSearch').on('click', function () {
                $('.pf-senior-xm-show').toggleClass('bdc-hide');

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
                $(this).parent().toggleClass('bdc-button-box-four');

                if($(this).html() == '高级查询'){
                    $(this).html('收起');
                }else {
                    $(this).html('高级查询');
                }
                changeTableHeight();
            });

            loadProcessDefName();

            /**
             * 渲染流程名称下拉框
             */
            function loadProcessDefName() {
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/task/process/all",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        $('#selectedDefName').append(new Option("请选择", ""));
                        $('#selectedDoneDefName').append(new Option("请选择", ""));
                        $('#selectedXmDefName').append(new Option("请选择", ""));
                        $.each(data, function (index, item) {
                            $('#selectedDefName').append(new Option(item.name, item.name));// 下拉菜单里添加元素
                            $('#selectedDoneDefName').append(new Option(item.name, item.name));// 下拉菜单里添加元素
                            $('#selectedXmDefName').append(new Option(item.name, item.name));// 下拉菜单里添加元素
                        });
                        layui.form.render("select");
                    }, error: function (e) {
                        response.fail(e,'');
                    }
                });
            }
        }else{
            $("#LAY_app_tabsheader_home").attr("style","display:none!important");
            LAY_app_body.innerHTML='<div class="layadmin-tabsbody-item"></div><div class="layadmin-tabsbody-item"></div>';
            $('.layadmin-pagetabs').css('padding-left','40px');
        }

        $('.bdc-table-box').on('mouseenter','td',function () {
            if ($(this).text() && $(this).attr("data-field") == "zl") {
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>')
            }
            $('.layui-table-grid-down').on('click',function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                },20);
            });
        });

        //解决跨域问题
        $('.layui-side').on('click', '.bdc-show-msg', function () {
            setTimeout(function () {
                $('.layadmin-tabsbody-item:last-child').append('<iframe id="test2" src="" frameborder="0" width="100%" height="0"></iframe>');
            }, 1000);
        });

        //点击自己出的 删除
        $('.bdc-outer-tab').on('click', '.bdc-tab-close', function () {
            var _this = $(this).parent();
            var prevIndex = _this.index();
            var nowIndex = _this.index() + 1;
            if (_this.hasClass('layui-this')) {
                // var nextLiIndex = nowIndex + 1;
                _this.parent().find('li').removeClass('layui-this');
                _this.parent().find('li:nth-child(' + prevIndex + ')').addClass('layui-this');
                _this.remove();
                $('.layui-body .layadmin-tabsbody-item:nth-child(' + nowIndex + ')').remove();
                $('.layui-body .layadmin-tabsbody-item').removeClass('layui-show');
                $('.layui-body .layadmin-tabsbody-item:nth-child(' + prevIndex + ')').addClass('layui-show');
            } else {
                _this.remove();
                $('.layui-body .layadmin-tabsbody-item:nth-child(' + nowIndex + ')').remove();
            }

        });

        //点击刷新图标
        $('.bdc-refresh-frame').on('click', function () {
            var nowTabIndex = $('.layui-body .layadmin-tabsbody-item.layui-show').index();
            var $frame = $('.layui-body .layadmin-tabsbody-item.layui-show iframe');
            if (nowTabIndex != 0) {
                $frame.attr('src', $frame.attr('src'));
            }
        });

        // 监听tab切换
        $('.bdc-outer-tab').on('click','.layui-tab-title li',function () {
            if($(this).attr('data-refresh') == 1){
                setTimeout(function () {
                    $('.bdc-refresh-frame').click();
                },500);
                $(this).removeAttr('data-refresh');
            }
        });

        //监听消息列表中的 ×
        $('.bdc-msg-nav').on('click','.layui-tab-item .layui-icon-close',function () {
            // 删除操作
            var className=this.parentElement.parentElement.className;
            var tpl= className=='bdc-read'?readMsgTpl:'';
            var ids = [];
            ids.push(this.id);
            //console.log('ids==='+ids);
            $.ajax({
                type: "DELETE",
                url: getContextPath() + "/rest/v1.0/msg",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: JSON.stringify(ids),
                async:false,
                success: function () {
                    layer.msg('删除消息成功！');
                    reloadMsg(tpl);
                }, error: function (e) {
                    response.fail(e,'');
                }
            });
        });
        //监听消息中的 更多
        $('.bdc-msg-nav').on('click','.bdc-more-msg',function () {
            window.open('./news-msg.html?moduleCode=msgs');
        });
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

    function refreshTab(url,id, authorityCode) {
        table.reload(id, {
            url:url,
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

    //表格高度自适应
    function changeTableHeight() {
        if($('.bdc-list-tab .layui-tab-content .layui-show .layui-table-main>.layui-table').height() == 0){
            $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
            //$('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body').height('56px');
            //$('.bdc-list-tab .layui-tab-content .layui-show .layui-table-fixed .layui-table-body').height('56px');
        }else {
            $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body').height($('.bdc-content-box').height() - 206 - $('.bdc-task-tab').height() - $('.bdc-list-tab .layui-show .bdc-search-box').height());
            $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-fixed .layui-table-body').height($('.bdc-content-box').height() - 206 - $('.bdc-task-tab').height() - $('.bdc-list-tab .layui-show .bdc-search-box').height() - 17);
        }
    }

    //渲染待办表格
    function renderWaitTable(url, tableId, currentId, toolbar) {
        table.render({
            elem: tableId,
            id: currentId,
            url: url,
            toolbar: toolbar,
            title: '待办任务表格',
            method: 'post',
            even: true,
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            defaultToolbar: ['filter'],
            cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
                {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
                {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
                {field: 'qlr', minWidth: 100, title: '权利人'},
                {field: 'ywr', minWidth: 100, title: '义务人'},
                {field: 'zl', minWidth: 200, title: '坐落'},
                {field: 'procStartUserName', minWidth: 100, title: '受理人'},
                {field: 'processDefName', title: '流程名称', minWidth: 160},
                {field: 'taskName', title: '节点名称', width: 90},
                {field: 'newStartTime', title: '开始时间', width: 100, sort: true},
                {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
                {field: 'category', title: '业务类型', width: 90, hide: true},
                {field: 'claimStatusName', title: '认领状态', width: 90, hide: true},
                {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                // console.log(res);
                if (currentId == 'dbTable') {
                    if(res.totalElements > 99){
                        $('.bdc-list-num-tips').html('99+');
                    }else {
                        $('.bdc-list-num-tips').html(res.totalElements);
                    }

                }
                res.content.forEach(function (v) {
                    if (v.startTime) {
                        var newStartTime = new Date(v.startTime);
                        v.newStartTime = newStartTime.toLocaleString();
                    }
                });
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            page: true,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                changeTableHeight();
                var reverseList = ['zl'];
                reverseTableCell(reverseList);
            }
        });
    }

    //渲染已办表格
    function renderDoneTable(tableId, currentId, toolbar) {
        table.render({
            elem: tableId,
            id: currentId,
            data: [],
            toolbar: toolbar,
            title: '已办任务表格',
            method: 'post',
            even: true,
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            defaultToolbar: ['filter'],
            cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
                {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
                {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
                {field: 'qlr', minWidth: 100, title: '权利人'},
                {field: 'ywr', minWidth: 100, title: '义务人'},
                {field: 'zl', minWidth: 200, title: '坐落'},
                {field: 'processDefName', title: '流程名称', minWidth: 160},
                {field: 'taskName', title: '节点名称', width: 90},
                {field: 'newStartTime', title: '开始时间', width: 100, sort: true},
                {field: 'newEndTime', title: '结束时间', width: 100, sort: true},
                {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
                {field: 'category', title: '业务类型', width: 90, hide: true},
                {field: 'taskAssName', title: '处理人', width: 90, hide: true},
                {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                if (currentId == 'dbTable') {
                    if(res.totalElements > 99){
                        $('.bdc-list-num-tips').html('99+');
                    }else {
                        $('.bdc-list-num-tips').html(res.totalElements);
                    }
                }
                res.content.forEach(function (v) {
                    if (v.startTime) {
                        var newStartTime = new Date(v.startTime);
                        v.newStartTime = newStartTime.toLocaleString();
                    }
                    if (v.endTime) {
                        var newEndTime = new Date(v.endTime);
                        v.newEndTime = newEndTime.toLocaleString();
                    }
                });
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            page: true,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                changeTableHeight();
                var reverseList = ['zl'];
                reverseTableCell(reverseList);
            }
        });
    }

    //渲染项目列表
    function renderListTable( tableId, currentId, toolbar) {
        table.render({
            elem: tableId,
            id: currentId,
            data: [],
            toolbar: toolbar,
            title: '用户数据表',
            method: 'post',
            even: true,
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            defaultToolbar: ['filter'],
            cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'procStatus', title: '流程状态', width: 90, templet: '#flowStateTpl'},
                {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
                {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
                {field: 'qlr', minWidth: 100, title: '权利人'},
                {field: 'ywr', minWidth: 100, title: '义务人'},
                {field: 'zl', minWidth: 200, title: '坐落'},
                {field: 'procDefName', title: '流程名称', minWidth: 160},
                {field: 'startUserName', title: '受理人', minWidth: 100},
                {field: 'startTime', title: '受理时间', width: 100, sort: true},
                {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
                {field: 'categoryName', title: '业务类型', width: 90, hide: true},
                {fixed: 'right', title: '流程图', templet: '#lcTpl', width: 75},
                {fixed: 'right', field: 'procTimeoutStatus', width: 90, title: '超期状态', templet: '#overStateTpl'}
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                // console.log(res);
                if (currentId == 'dbTable') {
                    if(res.totalElements > 99){
                        $('.bdc-list-num-tips').html('99+');
                    }else {
                        $('.bdc-list-num-tips').html(res.totalElements);
                    }
                }
                res.content.forEach(function (v) {
                    if (v.startTime) {
                        var startNewTime = new Date(v.startTime);
                        v.startTime = startNewTime.toLocaleString();
                    }
                    if (v.endTime) {
                        var newEndTime = new Date(v.endTime);
                        v.endTime = newEndTime.toLocaleString();
                    }
                });
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            page: true,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                changeTableHeight();
                var reverseList = ['zl'];
                reverseTableCell(reverseList);
            }
        });
    }

    function renderRlTable( tableId, currentId, toolbar) {
        table.render({
            elem: tableId,
            id: currentId,
            data: [],
            toolbar: toolbar,
            title: '认领任务表',
            method: 'post',
            even: true,
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            defaultToolbar: ['filter'],
            cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'slbh', minWidth: 110, title: '受理编号'},
                {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
                {field: 'qlr', minWidth: 100, title: '权利人'},
                {field: 'ywr', minWidth: 100, title: '义务人'},
                {field: 'zl', minWidth: 200, title: '坐落'},
                {title: '流程名称', field: 'processDefName', minWidth: 160},
                {field: 'procStartUserName', title: '受理人', minWidth: 100},
                {field: 'newStartTime', title: '开始时间', width: 100, sort: true},
                {field: 'newEndTime', title: '结束时间', width: 100, sort: true},
                {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
                {field: 'taskName', title: '任务名称', width: 90, hide: true},
                {field: 'category', title: '业务类型', width: 90, hide: true},
                {field: 'taskAssName', title: '处理人', width: 90, hide: true},
                {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                res.content.forEach(function (v) {
                    if (v.startTime) {
                        var newStartTime = new Date(v.startTime);
                        v.newStartTime = newStartTime.toLocaleString();
                    }
                    if (v.endTime) {
                        var newEndTime = new Date(v.endTime);
                        v.newEndTime = newEndTime.toLocaleString();
                    }
                });
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            page: true,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                changeTableHeight();
                var reverseList = ['zl'];
                reverseTableCell(reverseList);
            }
        });
    }


    function webSocket(username){
        var websocket;
        // 首先判断是否 支持 WebSocket
        if('WebSocket' in window) {
            websocket = new WebSocket("ws://"+window.location.host+getContextPath()+"/wsInfo?username="+username+"&type=HOME");
        } else if('MozWebSocket' in window) {
            websocket = new MozWebSocket("ws://"+window.location.host+getContextPath()+"/wsInfo?username="+username+"&type=HOME");
        } else {
            websocket = new SockJS("ws://"+window.location.host+getContextPath()+"/wsInfo?username="+username+"&type=HOME");
        }
        // 打开连接时
        websocket.onopen = function(event) {
            console.log(" websocket.onopen");
        };
        // 收到消息时
        websocket.onmessage = function(event) {
            reloadMsg();
            var msg = $.parseJSON(event.data);
            if(msg.msgType!='CUSTOM'){
                bottomShowTips(msg.msgTypeName+"("+msg.msgTitle+")");
            }
        };
        websocket.onerror = function(event) {
            console.log("websocket.onerror");
        };
        websocket.onclose = function(event) {
            console.log("websocket.onclose");
        };
    }

    /**
     * 加载消息中心
     */
    function reloadMsg(tpl){
        // 获取消息中心列表
        $.ajax({
            type: "POST",
            url: getContextPath() + "/rest/v1.0/msg/home/list",
            success: function (data) {
                var getMsgTitleTpl = msgTitleTpl.innerHTML
                    , getMsgTpl = tpl==undefined||tpl==''?msgTpl.innerHTML:tpl.innerHTML,
                    getMsgView = document.getElementById('msgView'),
                    getMsgTitleView = document.getElementById('msgTitleView');
                laytpl(getMsgTitleTpl).render(data, function (html) {
                    getMsgTitleView.innerHTML = html;
                });
                laytpl(getMsgTpl).render(data, function (html) {
                    getMsgView.innerHTML = html;
                });
                if(data.qrList && data.qrList.length>0){
                    var dataMsg=[];
                    var msgId=[];
                    data.qrList.forEach(function (v) {
                        dataMsg.push(v.msgContent);
                        msgId.push(v.id);
                    });
                    strongTips(dataMsg,function () {
                        $.ajax({
                            type:"PUT",
                            url: getContextPath() + "/rest/v1.0/msg",
                            contentType: "application/json;charset=utf-8",
                            data: JSON.stringify(msgId),
                            success: function () {
                                reloadMsg(tpl);
                            }, error: function (e) {
                            }
                        });
                    });
                }
            }, error: function (e) {
                response.fail(e,'');
            }
        });
    }
});