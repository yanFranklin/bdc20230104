/**
 * Created by Administrator on 2018/12/21.
 */
layui.use(['jquery', 'form', 'laytpl','element','table'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        layer = layui.layer;
    $(function () {
        // 获取参数 权利人证件号
        var param = $.getUrlParam('param');
        //宗地代码
        var zddm = $.getUrlParam('zddm');
        if(!param){
            layer.alert("获取登记簿信息失败！");
            return false;
        }

        getReturnData("/rest/v1.0/djbxx/qlxx/tdcbjyqml?qlrzjh="+param,"","GET",function (data) {
            renderAside(data,zddm);
        },function (error) {
            layer.alert("获取登记簿目录失败！");
            return false;
        });



        //渲染目录树
        function renderAside(asideData,zddm){
            var json={
                data:asideData,
                zddm:zddm
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
            $('.content-div iframe').attr('src',encodeURI(firstLi.data('src')));

        }

        //获取最外层元素的padding-left的值
        var containerLeft = 240;

        //1. 侧边栏点击效果
        //1.1  手风琴点击效果
        $(document).on('click', '.link', function () {
            var $this = $(this);
            $this.parent().siblings().removeClass('open');
            $this.next().slideToggle();
            $this.parent().toggleClass('open');

            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $this.parent().addClass('bdc-this-li');

            $('.content-div iframe').attr('src',$this.data('src'));

        });

        //2. 点击侧边栏收缩按钮
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
            reloadIframe();




        });
        $asideOpen.on('click',function () {
            containerLeft = 240;
            $(this).toggleClass('bdc-hide');
            $asideClose.toggleClass('bdc-hide');
            $zoom.animate({'left': $menuAside.width() - 1 + 'px'},100);
            $menuAside.animate({'left':'0'},100);
            $zoomLine.animate({'left': $menuAside.width() - 4 + 'px'},100);
            $container.animate({'padding-left':'240px'},100);
            reloadIframe();


        });

        //刷新子页面
        function reloadIframe(){
            var $iframe;
            var iframes = $("iframe");
            if(iframes.length > 0){
                for(var j = 0 ;j<iframes.length ;j++){
                    if($(iframes[j]).attr("src").indexOf("bdcDjbCbjyq.html") > 0){
                        $iframe = $(iframes[j]);
                    }
                }
            }
            if($iframe){
                var child = $iframe[0].contentWindow;
                child.$(".layui-icon-down").click();
            }
        }



    });
});




