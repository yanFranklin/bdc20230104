/**
 * Created by Administrator on 2018/12/21.
 */
layui.use(['jquery', 'form', 'laytpl','element','table'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
    $(function () {
        // 默认选中第一个
        var firstLi = $('.accordion .bdc-outer-li:first-child a');
        firstLi.addClass('active');
        firstLi.parent().addClass('bdc-this-li');
        $('.bdc-tab-content .layui-tab-item:first-child iframe').attr('src',firstLi.data('src'));
        $('.bdc-tab-title li:first-child').html(firstLi.data('name'));

        console.log(firstLi.data('src1'));
        if(firstLi.data('src1') != undefined){
            console.log('aaa');
            $('.bdc-tab-title li:last-child').removeClass('bdc-hide').html(firstLi.data('name1'));
            $('.bdc-tab-content .layui-tab-item:last-child').removeClass('bdc-hide');
            $('.bdc-tab-content .layui-tab-item:last-child iframe').attr('src',firstLi.data('src1'));
        }else {
            $('.bdc-tab-title li:last-child').addClass('bdc-hide').html('');
            $('.bdc-tab-content .layui-tab-item:last-child iframe').attr('src','');
            $('.bdc-tab-content .layui-tab-item:last-child').addClass('bdc-hide');
        }

        //获取最外层元素的padding-left的值
        var containerLeft = 300;

        //1.2  对于逻辑栋点击高亮
        $(document).on('click', '.bdc-outer-li a', function () {
            var $this = $(this);
            $('.bdc-outer-li a').removeClass('active');
            $this.addClass('active');
            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $this.parent().addClass('bdc-this-li');

            $('.bdc-tab-title li:first-child').html($(this).data('name'));
            $('.bdc-tab-content .layui-tab-item:first-child iframe').attr('src',$(this).data('src'));

            if($(this).data('src1') != undefined){
                $('.bdc-tab-content .layui-tab-item:last-child').removeClass('bdc-hide');
                $('.bdc-tab-content .layui-tab-item:last-child iframe').attr('src',$(this).data('src1'));
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

        //3. 查询条件获取焦点
        $('.bdc-search').on('focus',function () {
            $(this).addClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size','14px');
        }).on('blur',function () {
            $(this).removeClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size','12px');
        });
    });
});