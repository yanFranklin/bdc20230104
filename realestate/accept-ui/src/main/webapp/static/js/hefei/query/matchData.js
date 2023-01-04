/**
 * Created by Administrator on 2018/12/21.
 */
layui.use(['jquery', 'form', 'laytpl','element','table'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table,
        layer = layui.layer,
        element = layui.element;
    $(function () {
        var getMatchData;
        setTimeout(function () {
            if(!isNotBlank(jbxxid)){
                //jbxxid没有值，默认不是创建业务界面
                jbxxid ="";
            }
            if(sessionStorage.getItem('matchData' +jbxxid)){
                getMatchData = JSON.parse(sessionStorage.getItem('matchData' +jbxxid));
                generateMatchData(getMatchData);
            }else{
                showAlertDialog("获取虚拟数据错误，请重试");
                return false;
            }
        }, 500);

        function generateMatchData(getMatchData){
            var json={
                data:getMatchData,
                xztzlx:xztzlx
            };
            var getDataTpl = dataTpl.innerHTML
                ,dataView = document.getElementById('accordion');
            laytpl(getDataTpl).render(json, function(html){
                dataView.innerHTML = html;
            });
            // 默认选中第一个
            var firstLi = $('.accordion .bdc-outer-li:first-child a');

            firstLi.addClass('active');
            firstLi.parent().addClass('bdc-this-li');
            $('.bdc-tab-content .layui-tab-item:first-child iframe').attr('src',encodeURI(firstLi.data('src')));
            $('.bdc-tab-title li:first-child').html(firstLi.data('name'));
            var xnbdcdyh =firstLi.parent().attr("data-num");
            var dzwtzm =xnbdcdyh.substring(19,20);
            if(firstLi.data('src1') != undefined && dzwtzm !== "W"){
                $('.bdc-tab-title li:last-child').removeClass('bdc-hide').html(firstLi.data('name1'));
                $('.bdc-tab-content .layui-tab-item:last-child').removeClass('bdc-hide');
                $('.bdc-tab-content .layui-tab-item:last-child iframe').attr('src',encodeURI(firstLi.data('src1')));
            }else {
                $('.bdc-tab-title li:last-child').addClass('bdc-hide').html('');
                $('.bdc-tab-content .layui-tab-item:last-child iframe').attr('src','');
                $('.bdc-tab-content .layui-tab-item:last-child').addClass('bdc-hide');
            }
        }

        //获取最外层元素的padding-left的值
        var containerLeft = 300;

        //1.2  对于逻辑栋点击高亮
        $(document).on('click', '.bdc-outer-li a', function () {
            var $this = $(this);
            var xnbdcdyh =$this.parent().attr("data-num");
            var dzwtzm =xnbdcdyh.substring(19,20);
            $('.bdc-outer-li a').removeClass('active');
            $this.addClass('active');
            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $this.parent().addClass('bdc-this-li');

            $('.bdc-tab-title li:first-child').html($(this).data('name'));
            $('.bdc-tab-content .layui-tab-item:first-child iframe').attr('src',encodeURI($(this).data('src')));

             //左侧为非土地时，展示土地证列
            if($(this).data('src1') != undefined && dzwtzm === "F"){
                $('.bdc-tab-content .layui-tab-item:last-child').removeClass('bdc-hide');
                $('.bdc-tab-content .layui-tab-item:last-child iframe').attr('src',encodeURI($(this).data('src1')));
                $('.bdc-tab-title li:last-child').removeClass('bdc-hide').html($(this).data('name1'));
            }else {
                $('.bdc-tab-title li:last-child').addClass('bdc-hide').html('');
                $('.bdc-tab-content .layui-tab-item:last-child iframe').attr('src','');
                $('.bdc-tab-content .layui-tab-item:last-child').addClass('bdc-hide');
            }
        });

        //2. 点击侧边栏收缩按钮
        var $asideClose = $('.bdc-aside-close'),
            $asideOpen = $('.bdc-aside-open'),
            $zoom = $('.bdc-aside-zoom'),
            $menuAside = $('.bdc-menu-aside'),
            $container = $('.bdc-container');
        $asideClose.on('click',function () {
            containerLeft = 20;
            $(this).toggleClass('bdc-hide');
            $asideOpen.toggleClass('bdc-hide');
            $zoom.animate({'left':'-5px'},100);
            $menuAside.animate({'left': -($menuAside.width() + 20) + 'px'},100);
            $container.animate({'padding-left':'20px'},100);
        });
        $asideOpen.on('click',function () {
            containerLeft = 300;
            $(this).toggleClass('bdc-hide');
            $asideClose.toggleClass('bdc-hide');
            $zoom.animate({'left': $menuAside.width() - 1 + 'px'},100);
            $menuAside.animate({'left':'0'},100);
            $container.animate({'padding-left':'300px'},100);
        });
        if(isNotBlank(lx) && lx === "check") {
            var btn = document.getElementById("create");
            if(btn != null) {
                btn.setAttribute("style", "display:none");
            }
        }

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

        var tabClickIndex = 1;
        element.on('tab(xmFilter)', function(data){
            if(data.index == 1 && tabClickIndex == 1){
                tabClickIndex++;
                document.getElementById('childFrame1').contentWindow.reloadCurTab();
            }
        });
    });
});

function createXm(thisDom) {
    addModel();
    var $this = $(thisDom);
    $this.attr("disabled",true).css("pointer-events","none");
    //创建验证时从session里面获取到勾选的数据
    setTimeout(function () {
        getReturnData("/gwc/listGwcData",{jbxxid:jbxxid},"GET",function (data) {
            console.log(data);
            window.parent.cshSelectedXxSingle(window.parent.getQueryString("jbxxid"),window.parent.getQueryString("processDefKey"),true,true,xztzlx);
        },function (xhr) {
            removeModal();
            delAjaxErrorMsg(xhr);
        })
    },50);
}

function checkPplzzt(checkData, sfcj, $this) {
    getReturnData("/bdcdyh/pplzzt?xztzlx=" + xztzlx, JSON.stringify(checkData), "POST", function (data) {
        $this.removeAttr("disabled").css("pointer-events","auto");
        if (data !== null) {
            if (data.sflz === "true") {
                //已落宗未匹配提示
                if (data.sfpp === "false") {
                    showConfirmDialog("提示", "未匹配土地证号，是否创建流程", "cshSelectedXx", "'" + window.parent.getQueryString("jbxxid") + "','" + window.parent.getQueryString("processDefKey") + "'", "checkPp", "'" + JSON.stringify(checkData) + "','" + 2 + "'");
                } else if (data.sfpp === "true" || data.sfpp ===null) {
                    addModel();
                    cshSelectedXx(window.parent.getQueryString("jbxxid"),window.parent.getQueryString("processDefKey"));
                }
            } else if (data.sflz === null && (data.sfpp === null || data.sfpp === "true")) {
                cshSelectedXx(window.parent.getQueryString("jbxxid"),window.parent.getQueryString("processDefKey"));
            } else if(data.sflz === "false"){
                showConfirmDialog("提示", "请先落宗匹配再创建流程", "checkPp", "'" + JSON.stringify(checkData) + "','" + 2 + "'");
            }
        }
    }, function () {
        $this.removeAttr("disabled").css("pointer-events","auto");
        removeModal()
    });
    removeModal();
}

function cshSelectedXx(jbxxid,gzldyid) {
    $.ajax({
        url: getContextPath() + '/slxxcsh',
        type: 'GET',
        dataType: 'json',
        data: {jbxxid: jbxxid, gzldyid: gzldyid,slbh:window.parent.getQueryString("slbh")},
        success: function (data) {
            if (data.msg == "success") {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                var url = "/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId;
                removeModal();
                parent.window.location.href = url;
            } else {
                removeModal();
                ityzl_SHOW_WARN_LAYER("初始化失败");
                $("#queryBdcdyh").click();
                parent.addGwc();
            }
        }, error: function (xhr, status, error) {
            removeModal();
            $("#queryBdcdyh").click();
            delAjaxErrorMsg(xhr);
            parent.addGwc();
        }
    });
}



