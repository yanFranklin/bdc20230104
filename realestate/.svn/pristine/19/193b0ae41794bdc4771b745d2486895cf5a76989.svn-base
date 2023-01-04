var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/';
/**
 * Created by Ypp on 2019/12/18.
 * 附件下载相关js
 */
layui.use(['form','jquery'],function () {
    var $ = layui.jquery,
        form = layui.form;
    $(function () {
        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        function defaultStyle() {
            if($cnotentTitle.length == 1){
                $('.bdc-form-div').css('padding-top','68px');
            }
            if($(window).scrollTop() > 85){
                $cnotentTitle.css('top','0');
            }else if($(window).scrollTop() <= 85){
                $cnotentTitle.css('top','10px');
            }
        }

        $(window).resize(function(){
            defaultStyle();
        });
        $(window).on('scroll',function () {
            if($(this).scrollTop() > 85){
                $cnotentTitle.css('top','0');
            }else if($(this).scrollTop() <= 85){
                $cnotentTitle.css('top','10px');
            }
        });
    });

    /**
     * 点击查封文号附件
     */
    $('#cfwhfj').on('click', function () {
        var cfwh = $("#cfwh").val();
        // 获取附件
        window.open("/realestate-inquiry-ui/rest/v1.0/fjxz/cf/fj/" + encodeURI(cfwh));
    });

    /**
     * 点击查封文号附件
     */
    $('#tdfwxxfj').on('click', function () {
        var zl = $("#zl").val();
        // 获取附件
        window.open("/realestate-inquiry-ui/rest/v1.0/fjxz/tdfwxxdb/fj/" + encodeURI(zl));
    });

});