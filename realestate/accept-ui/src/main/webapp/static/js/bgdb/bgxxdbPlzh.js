var ywxxDataList=[];
var xmid = getQueryString("xmid");
var processInsId = getQueryString("processInsId");
var lclx = getQueryString("lclx");
var zdList = {a:[]};
layui.use(['jquery', 'layer', 'element', 'form', 'laytpl'],function () {
    form = layui.form;
    $ = layui.jquery;
    var laytpl = layui.laytpl;

    $(function () {
        generatePage();
        //1. 侧边栏点击效果
        //1.1  手风琴点击效果
        $(document).on('click', '.link', function () {
            var $this = $(this);
            $this.parents('.accordion').find('.submenu a').removeClass('active');
            $this.parents('.accordion').find('.submenu').not($this.next()).slideUp();
            $this.parent().siblings().removeClass('open');
            $this.next().slideToggle();
            $this.parent().toggleClass('open');

            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $this.parent().addClass('bdc-this-li');

            if(lclx =='3'){
                var bdcdyList;
                var bdcdyh =  $this.parent().attr("data-num");
                for(var i = 0; i < ywxxDataList.length; i++){
                    if(ywxxDataList[i].bdcdyh == bdcdyh){
                        bdcdyList = ywxxDataList[i];
                    }
                }
                //打开前先清除缓存数据
                sessionStorage.removeItem('ywxxData');
                //数据过多,存入缓存
                sessionStorage.setItem('ywxxData', JSON.stringify(bdcdyList));
                $('.content-div iframe').attr('src','bgxxdb.html?lclx='+ lclx);
            }

        });

        //1.2  点击箭头收起效果
        $(document).on('click', '.link>.fa-caret-right', function (ev) {
            var event = ev||event;
            event.stopPropagation();
            var $this = $(this).parent();
            $this.parents('.accordion').find('.submenu').not($this.next()).slideUp();
            $this.parent().siblings().removeClass('open');
            $this.next().slideToggle();
            $this.parent().toggleClass('open');
        });

        //1.3 对于逻辑栋点击高亮
        $(document).on('click', '.submenu a', function () {
            var $this = $(this);
            $('.submenu a').removeClass('active');
            $this.addClass('active');
            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');

            if(lclx =='2' || lclx =='4'){
                var bdcdyList;
                // 当前点击的连接的不动产单元号
                var bdcdyh =  $this.parent().attr("data-num");
                // 当前点击的连接的权利类型
                var qllx = $this.attr("data-qllx");
                // 当前点击的连接的xmid
                var xmid = $this.attr("data-xmid");
                for(var i = 0; i < ywxxDataList.length; i++){
                    if(ywxxDataList[i].bdcdyh == bdcdyh){
                        for(var j = 0; j < ywxxDataList[i].list.length; j++){
                            if(ywxxDataList[i].list[j].qllx == qllx && ywxxDataList[i].list[j].bdcXm.xmid == xmid){
                                bdcdyList = ywxxDataList[i].list[j];
                            }
                        }
                    }
                }
                //打开前先清除缓存数据
                sessionStorage.removeItem('ywxxData');
                //数据过多,存入缓存
                sessionStorage.setItem('ywxxData',JSON.stringify(bdcdyList));
                $('.content-div iframe').attr('src','bgxxdb.html?lclx='+ lclx);
            }
            // console.log($this.parents('.bdc-outer-li'));
        });


        // 点击侧边栏收缩按钮
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

        //绑定需要拖拽改变大小的元素对象
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
     * 初始化加载页面数据
     */
    function generatePage(){
        //获取字典项数据
        getReturnData("/bdczd",{},"POST",function (data) {
            zdList = data;
        },function () {
        },false);

        //加载目录树
        getBgxx().done(function(data){
            // 组合和批量组合时重新组织数据
            if(lclx == '2' || lclx == '4'){
                for(var i = 0; i < data.length; i++){
                    var bdcdyList=[];
                    bdcdyList.list = [];
                    var isSame = false;
                    // 权利代码转名称
                    data[i].qlmc = getZdMc('qllx',data[i].qllx);
                    for(var j = 0; j<ywxxDataList.length;j++){
                        // 相同不动产单元号合并权利类型
                        if(data[i].bdcdyh == ywxxDataList[j].bdcdyh){
                            isSame = true;
                            ywxxDataList[j].list.push(data[i]);
                        }
                    }
                    if(!isSame){
                        bdcdyList.bdcdyh = data[i].bdcdyh;
                        bdcdyList.list.push(data[i]);
                        ywxxDataList.push(bdcdyList);
                    }
                }
            }else {
                ywxxDataList  = data;
            }
            renderAside(ywxxDataList);
            // 默认打开第一个
            $('.bdc-outer-li:first-child .link').click();
            if($('.bdc-outer-li:first-child').find(".bdc-inner-li ").length >0){
                $('.bdc-outer-li:first-child .bdc-inner-li:first-child a').click();
            }
        });
    }

    // 获取变更信息
    function getBgxx(){
        var deferred = $.Deferred();
        addModel();
        getReturnData("/rest/v1.0/bgxxdb/dbxx",{xmid : xmid, gzlslid : processInsId},"GET",function (data) {
            removeModal();
            if(isNotBlank(data)){
                deferred.resolve(data);
            }else{
                ityzl_SHOW_WARN_LAYER("未获取到变更信息。");
                deferred.reject();
            }
        },function (error) {
            removeModal();
            delAjaxErrorMsg(error);
            deferred.reject();
        });
        return deferred;
    }

    // 渲染模板数据
    function renderAside(asideData) {
        var getAsideTpl;
        if(lclx == '3'){
            getAsideTpl = bgxxPlTpl.innerHTML;
        }else {
            getAsideTpl = bgxxZhTpl.innerHTML;
        }
        var asideView = document.getElementById('accordion');
        laytpl(getAsideTpl).render(asideData, function (html) {
            asideView.innerHTML = html;
        });
    }

});





// 页面展示字典项dm转名称
function getZdMc(category, dm){
    var mc = "";
    if(!isNotBlank(dm)|| !isNotBlank(category)){
        return mc;
    }
    var categoryList = zdList[category];
    $.each(categoryList,function(index, value){
        if(value.DM == dm){
            mc = value.MC;
            return false;
        }
    });
    return mc;
}