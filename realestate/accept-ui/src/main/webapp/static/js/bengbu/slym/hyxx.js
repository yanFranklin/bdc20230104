
var zdList;
var $,form, laytpl;
layui.use(['jquery', 'layer', 'form', 'laytpl'], function () {
    form = layui.form;
    laytpl = layui.laytpl;
    $ = layui.jquery;
    var layer = layui.layer;

    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    generateHyxx();

});

function generateHyxx(){
    if(!isNotBlank(zjh)){
        ityzl_SHOW_INFO_LAYER("未获取到证件号信息。");
        return;
    }
    addModel();
    $.ajax({
        url: getContextPath()+"/slym/jtcy/hygaxx",
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
                disabledAddFa();
            }else{
                ityzl_SHOW_WARN_LAYER("未查询到婚姻信息！");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}