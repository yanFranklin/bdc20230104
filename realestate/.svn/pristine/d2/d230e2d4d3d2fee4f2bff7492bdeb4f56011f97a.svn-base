/**
 * author: caolu
 * date: 2022/4/22
 * version 3.0.0
 * describe: 跳转分层分户页面
 */
//获取页面版本
layui.use(['form', 'table', 'jquery'], function () {
    var bdcdywybh = $.getUrlParam('bdcdywybh');
    if (isNullOrEmpty(bdcdywybh)){
        var json = {};
    }else{
        var httpurl = hstHttpUrl + bdcdywybh;
        var json={
            url: httpurl
        }
    }

    renderTpl('uploadTpl','hstImage',json);
});

function renderTpl(tplId,imgId,json) {
    layui.use('laytpl', function () {
        //获取模板
        var tpl = $("#"+tplId).html();
        var laytpl = layui.laytpl;
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            $("#"+imgId).html(html);
        });
    })
}
