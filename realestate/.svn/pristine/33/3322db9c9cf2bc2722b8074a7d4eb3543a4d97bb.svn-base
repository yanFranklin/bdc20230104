/**
 * 退回意见JS代码
 */
layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer'], function () {
    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer,
        laytpl = layui.laytpl;

    // 滚动时头部固定
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

    $(window).resize(function () {
        defaultStyle();
    });
    $(window).on('scroll', function () {
        if($(this).scrollTop() > 85){
            $cnotentTitle.css('top','0');
        }else if($(this).scrollTop() <= 85){
            $cnotentTitle.css('top','10px');
        }
    });

    var gzlslid = $.getUrlParam('processInsId');
    loadThyj();

    // 加载退回意见
    function loadThyj() {
        addModel();
        //获取数据
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/czrz/tjxx/"+gzlslid,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            dataType: "json",
            success: function (data) {
                if(data &&data.length >0){
                    var getTpl = thyjTpl.innerHTML;
                    laytpl(getTpl).render(data, function (html) {
                        $('.bdc-table-box tbody').html(html);
                    });
                    form.render();

                }
                removeModel();
            }, error: function (xhr) {
                removeModel();
                delAjaxErrorMsg(xhr)
            }
        });
    }
});