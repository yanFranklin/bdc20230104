layui.use(['jquery', 'element', 'form', 'laytpl', 'layer', 'table'], function () {
    var $ = layui.jquery,
        element = layui.element,
        laytpl = layui.laytpl;

    $(function () {
        // 获取参数
        var hlwxmid = $.getUrlParam('hlwxmid');
        var gzldyid = $.getUrlParam('gzldyid');
        var wwslbh = $.getUrlParam('wwslbh');

        if(isNullOrEmpty(hlwxmid)){
            warnMsg(" 未传入互联网项目参数，无法查询！");
            setDefaultPage();
            return;
        }

        if(!isNullOrEmpty(hlwxmid)){
            addModel();
            var urlData =[];
            var asideData =["sqxx","fjxx"];

            asideData.forEach(function (v) {
                if(v ==="sqxx"){
                    urlData.push({
                        name: '申请信息',
                        mark: 'basic',
                        url: '/realestate-etl/view/wwsq/sqxx.html?wwxmid='+hlwxmid+"&gzldyid="+gzldyid,
                        childTree: []
                    });
                }else if (v === "fjxx") {
                    urlData.push({
                        name: '附件信息',
                        mark: 'basic',
                        url: '/realestate-etl/view/wwsq/fjxx.html?hlwxmid=' + hlwxmid + "&wwslbh="+wwslbh,
                        childTree: []
                    });
                }
            });
            showPage(urlData);
            removeModal();
        }

        function setDefaultPage(){
            var data = [{
                name: '申请信息',
                mark: 'basic',
                url: '/realestate-etl/view/wwsq/sqxx.html',
                childTree: []
            }];
            showPage(data);
        }

        function showPage(data){
            // 默认选中第一个表单资源
            if (data && data.length > 0) {
                $('.layui-body .layadmin-tabsbody-item.layui-show iframe').attr('src', data[0].url);
                $('.bdc-aside-tab .layui-tab-title li.layui-this').attr('lay-id', data[0].url).attr('lay-attr', data[0].url);
                $('.bdc-aside-tab .layui-tab-title li.layui-this>span').html(data[0].name);
            }
            var getAsideTpl = asideTpl.innerHTML
                , getAsideView = document.getElementById('LAY-system-side-menu');
            laytpl(getAsideTpl).render(data, function (html) {
                getAsideView.innerHTML = html;
            });
            element.render('nav', 'layadmin-system-side-menu');
        }

        $('.bdc-fj-title .bdc-show-popup').on('click', function () {
            window.open('/realestate-etl/view/wwsq/fjxx.html?hlwxmid=' + hlwxmid + "&wwslbh="+wwslbh);
        })
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

        // 点击侧边的收缩按钮
        // 隐藏
        $('.bdc-aside-close').on('click', function () {
            $('.layui-layout-admin .layui-side').animate({left: '-220px'}, 100);
            $('.layui-layout-admin .layui-layout-left').animate({left: 0}, 100);
            $('.layui-layout-admin .layui-footer').animate({left: 0}, 100);
            $('.layui-layout-admin .layui-body').animate({left: 0}, 100);
            $('.layadmin-pagetabs').animate({left: 0}, 100);
            $('.bdc-aside-zoom').toggleClass('bdc-hide').animate({left: 0}, 300);
        });
        // 显示
        $('.bdc-aside-open').on('click', function () {
            $('.layui-layout-admin .layui-side').animate({left: '0'}, 100);

            $('.layui-layout-admin .layui-layout-left').animate({left: '220px'}, 100);
            $('.layui-layout-admin .layui-footer').animate({left: '220px'}, 100);
            $('.layui-layout-admin .layui-body').animate({left: '220px'}, 100);
            $('.layadmin-pagetabs').animate({left: '220px'}, 100);

            $('.bdc-aside-zoom').toggleClass('bdc-hide').animate({left: '206px'}, 300);
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

    });
});