var gzlslid = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var zdList = {a: []};
var $, form, layer, element, table, laydate, laytpl, formselects;
var btZdItem = [];
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'table', 'laytpl', 'formSelects'], function () {
    $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laydate = layui.laydate;
    laytpl = layui.laytpl;
    formselects = layui.formSelects;

    $(function () {
        //初始化字典表
        getCommonZd(function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        });
        //初始化日期控件
        lay('.render-date').each(function () {
            laydate.render({
                elem: '#wydcrq',
                trigger: 'click',
                format: 'yyyy-MM-dd',
                value: new Date()
            });
            laydate.render({
                elem: '#qjshrq',
                trigger: 'click',
                value: new Date()
            });
        });

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

        //点击提交时不为空的全部标红
        var isSubmit = true;
        var isFirst = true;
        form.verify({
            required: function (value, item) {
                if (value == '') {
                    if (isFirst) {
                        console.log($(item).parents('.layui-tab-item'));
                        $('.layui-tab-item').removeClass('layui-show');
                        $(item).parents('.layui-tab-item').addClass('layui-show');
                        var liIndex = $(item).parents('.layui-tab-item').index() + 1;
                        console.log(liIndex);
                        console.log($('.layui-tab-title li:nth-child(' + liIndex + ')'));
                        $('.layui-tab-title li').removeClass('layui-this');
                        $('.layui-tab-title li:nth-child(' + liIndex + ')').addClass('layui-this');
                        isFirst = false;
                    }
                    $(item).addClass('layui-form-danger');
                    btZdItem.push($(item).attr("id"));
                    // isSubmit = false;
                }
            }
        });
        form.on('submit(saveFwjsztCkxx)', function (data) {
            if (!isSubmit) {
                layer.msg('必填项不能为空', {icon: 5, time: 3000});
                isSubmit = true;
                isFirst = true;
                return false;
            } else {
                saveFwjsztCkxx();
                return false;
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
        loadFwjsztCkxx();
    });
});

//表单信息加载
function loadFwjsztCkxx() {
    addModel("加载中");
    getReturnData("/slym/fwjszt", {gzlslid: gzlslid}, "GET", function (data) {
        removeModal();
        var json = {
            fwjsztck: data,
            zd: zdList
        }
        var tpl = jsztTpl.innerHTML, view = document.getElementById('jsztck');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        renderDate(form, "");
        getStateById(readOnly, formStateId, "wyck", "", "");
        disabledAddFa();
        renderSelect();
        renderSelectClose(form)
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    })
}


//表单保存
function saveFwjsztCkxx() {
    addModel("正在保存");
    var fwjsztCkArray = $('.fwjszt');
    var fwjsztck = {};
    fwjsztCkArray.serializeArray().forEach(function (item, index) {
        fwjsztck[item.name] = item.value;
    });
    fwjsztck.gzlslid = gzlslid;
    getReturnData("/slym/fwjszt", JSON.stringify(fwjsztck), "POST", function (data) {
        loadFwjsztCkxx();
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("保存成功");
        if(btZdItem && btZdItem.length > 0){
            window.setTimeout(function(){
                for(var i=0;i<btZdItem.length;i++){
                    if($("#"+btZdItem[i]) && $("#"+btZdItem[i]).val() == ""){
                        $("#"+btZdItem[i]).addClass('layui-form-danger');
                    }
                }
                btZdItem = [];
            },1500)
        }
        console.log("保存成功");
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}