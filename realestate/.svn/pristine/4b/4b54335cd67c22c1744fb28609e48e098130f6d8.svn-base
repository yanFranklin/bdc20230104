var $,form, laytpl;
layui.use(['jquery', 'layer', 'form', 'laytpl'], function () {
    form = layui.form;
    laytpl = layui.laytpl;
    $ = layui.jquery;
    var layer = layui.layer;

    generateHyxx();

});

function generateHyxx(){
    if(!isNotBlank(zjh)){
        ityzl_SHOW_INFO_LAYER("未获取到证件号信息。");
        return;
    }
    addModel();
    $.ajax({
        url: getContextPath()+"/rest/v1.0/queryByExchange/bengbu/hygaxx",
        type: 'GET',
        dataType: 'json',
        data: {
            qlrzjh : zjh,
            beanName : "acceptHyxx_zh"
        },
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                var tpl = hyxxTpl.innerHTML;
                var view = document.getElementById('hyxxForm');
                //渲染数据
                laytpl(tpl).render(data, function (html) {
                    view.innerHTML = html;
                });
                form.render();
            }else{
                alertMsg("未查询到婚姻信息！");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}