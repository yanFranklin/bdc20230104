/**
 * Created by Administrator on 2018/12/21.
 */
layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table,
        form = layui.form;
})

// 手风琴点击效果
$(document).on('click', '.link', function () {
    var $this = $(this);
    $this.parents('.accordion').find('.submenu').not($this.next()).slideUp().parent().removeClass('open');
    $this.next().slideToggle();
    $this.parent().toggleClass('open');
});

//点击侧边栏收缩按钮
function getmulushu() {
    var $asideClose = $('.bdc-aside-close'),
        $asideOpen = $('.bdc-aside-open'),
        $zoom = $('.bdc-aside-zoom'),
        $menuAside = $('.bdc-menu-aside'),
        $container = $('.bdc-container');
    $(this).toggleClass('bdc-hide');
    $asideClose.toggleClass('bdc-hide');
    $zoom.animate({'left': '259px'}, 100);
    $menuAside.animate({'left': '0'}, 100);
    $container.animate({'padding-left': '300px'}, 100, function () {
        $('.bdc-btn-list').width($('.layui-table').width() - 31);
    });
}



