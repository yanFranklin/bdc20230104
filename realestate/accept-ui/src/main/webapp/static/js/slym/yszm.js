/**
 * Created by Ypp on 2020/4/14.
 * 遗失证明js
 */
var $, form, layer, element, table, laytpl;
/*表单状态id*/
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var gzlslid = getQueryString("processInsId");
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'],function () {
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laytpl = layui.laytpl;
    addModel();
    $(function () {
        //滚动时头部固定
        var $contentTitle = $('.bdc-content-fix:visible');
        if($contentTitle.length != 0){
            defaultStyle();
        }
        function defaultStyle() {
            if($(window).scrollTop() > 10){
                $contentTitle.css('top','0');
            }else if($(window).scrollTop() <= 10){
                $contentTitle.css('top','10px');
            }
        }

        $(window).resize(function(){
            if($contentTitle.length != 0){
                defaultStyle();
            }
        });
        $(window).on('scroll',function () {
            if($contentTitle.length != 0){
                if($(this).scrollTop() > 10){
                    $contentTitle.css('top','0');
                }else if($(this).scrollTop() <= 10){
                    $contentTitle.css('top','10px');
                }
            }
        });
        getReturnData("/slym/xm/yszm",{gzlslid:gzlslid},"GET",function (data) {
            if(data) {
                generateYsgg(data);
            }
        },function (xhr) {
            removeModal();
            delAjaxErrorMsg(xhr);
        })
    });
});

//组织遗失证明页面信息
window.generateYsgg = function (data) {
    var json = {
        bdcxmList:data.bdcXmDOList,
        zslx:data.zslx
    }
    var tpl = ysggTpl.innerHTML;
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        $('.bdc-ysgg').html(html)
    });
    form.render();
    getStateById(readOnly, formStateId, 'yszm');
    disabledAddFa();
    removeModal();
}