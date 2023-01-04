var gzlslid = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var zdList = {a: []};
var $, form, layer, element, table, laydate, laytpl, formselects;
var btZdItem = [];
layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'table', 'laytpl'], function () {
    $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laydate = layui.laydate;
    laytpl = layui.laytpl;

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
                elem: '#ksrq',
                trigger: 'click',
            });
            laydate.render({
                elem: '#jsrq',
                trigger: 'click',
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
        form.on('submit(saveSqgg)', function (data) {
            // console.log(isSubmit);
            if (!isSubmit) {
                layer.msg('必填项不能为空', {icon: 5, time: 3000});
                isSubmit = true;
                isFirst = true;
                return false;
            } else {
                saveSqgg();
                // console.log('提交操作');
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
        loadSqgg();

    });
});

function loadSqgg() {
    addModel("加载中");
    getReturnData("/slym/sqgg", {gzlslid: gzlslid}, "GET", function (data) {
        removeModal();
        var json = {
            bdcgg: data,
            zd: zdList
        }
        var tpl = sqggTpl.innerHTML, view = document.getElementById('sqggView');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        //加载数据
        form.render();
        renderDate(form, "");
        getStateById(readOnly, formStateId, "sqgg", "", "");
        disabledAddFa();
        renderSelect();
        renderSelectClose(form)
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    })
}

function saveSqgg() {
    addModel("正在保存");
    var bdcgg = {};
    var sqggArray = $('.bdcgg');
    sqggArray.serializeArray().forEach(function (item, index) {
        bdcgg[item.name] = item.value;
    });
    bdcgg.gzlslid = gzlslid;
    getReturnData("/slym/sqgg", JSON.stringify(bdcgg), "POST", function (data) {
        removeModal();
        //重新加载局部刷新
        loadSqgg();
        ityzl_SHOW_SUCCESS_LAYER("保存成功")
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