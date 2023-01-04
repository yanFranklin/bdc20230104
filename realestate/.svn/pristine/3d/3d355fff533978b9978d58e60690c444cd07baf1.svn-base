var cqxmid = getQueryString("cqxmid");
var htbh = getQueryString("htbh");
var zdList = {a: []};
var $, form, layer, element, table, laydate, laytpl;
layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'table', 'laytpl'], function () {
    $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laydate = layui.laydate;
    laytpl = layui.laytpl;

    $(function () {

        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-form-div');

        function defaultStyle() {
            if ($cnotentTitle.length == 1) {
                $('.bdc-form-div').css('padding-top', '68px');
            }
            if ($(window).scrollTop() > 68) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '63px');
            } else if ($(window).scrollTop() <= 68) {
                $cnotentTitle.css('top', '10px');
                $navContainer.css('top', '68px');
            }
        }

        $(window).resize(function () {
            defaultStyle();
        });
        $(window).on('scroll', function () {
            if ($(this).scrollTop() > 68) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '63px');
            } else if ($(this).scrollTop() <= 68) {
                $cnotentTitle.css('top', '10px');
                $navContainer.css('top', '68px');
            }
        });

        $('.layui-input').on('focus', function () {
            if ($(this).hasClass('layui-form-danger')) {
                $(this).removeClass('layui-form-danger');
            }
            if ($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')) {
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
        });
        $('.xm-input').on('click', function () {
            if ($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')) {
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
        });

        //监听input修改
        $('.layui-input').on('change', function () {
            $(this).addClass('bdc-change-input');
        });
        //加载页面数据
        loadZjjg();
    });
});

function loadZjjg() {
    addModel("加载中");

    // 存放基本信息、资金监管信息、收件信息接口返回的数据
    var json = {};
    var zjjgUrl = "/slym/zjjg/yth";
    //暂时用不到这个参数
    var gzlslid = "gzlslid";

    $.when(// 获取资金监管信息接口数据
        getReturnData(zjjgUrl, {processInsId: gzlslid,htbh:htbh}, "GET", function (data) {
            removeModal();
            json.zjjg = {};
            if(data && data.bdcSlZjjgxyDO){
                json.zjjg =data;
            }
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        }, false))
        .done(function(){
            // 渲染页面
            if(!isNullOrEmpty(json.zjjg.bdcSlZjjgxyDO)) {
                var tpl = zjjgTpl.innerHTML,
                    view = document.getElementById('zjjgView');
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
                form.render();
                renderDate(form, "");
                renderSelect();
                renderSelectClose(form);

                //获取资金监管附件
                $.ajax({
                    url: getContextPath() + "/slym/zjjg/ythfj",
                    type: 'GET',
                    dataType: 'json',
                    data: {htbh:htbh},
                    success: function (data) {
                        removeModal();
                        console.info(data);
                        if(data) {
                            var src = data.downUrl;
                            $('#seeImgView').html('<iframe style="width: 100%;height: 650px" src="' + getContextPath() + "/static/lib/pdf/web/viewer.html?file=" + encodeURIComponent(src) + '"></iframe>');
                        }
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr);
                        removeModal();
                    }
                });
            }else {
                ityzl_SHOW_WARN_LAYER("未获取一体化平台资金监管信息");
            }
        });
}


//查看资金监管
function showZjjgFj(jgxybh) {
    //打开文件管理器页面
    viewWjglFj(jgxybh, "clientId", null, "一体化监管附件", 2, true);
    return false;
}