
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
    generateHjxx();

});

function generateHjxx(){
    if(!isNotBlank(gzlslid)){
        ityzl_SHOW_INFO_LAYER("未获取到工作流实例ID。");
        return;
    }
    addModel();
    $.ajax({
        url: getContextPath()+"/slym/jtcy/bengbu/queryHjxx",
        type: 'POST',
        dataType: 'json',
        data: {
            gzlslid : gzlslid
        },
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                var tpl = hjxxTpl.innerHTML;
                var view = document.getElementById('hjxxTable');
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

function queryHyxx(zjh){
    if(!isNotBlank(zjh)){
        ityzl_SHOW_WARN_LAYER("未获取到证件号信息！");
        return;
    }
    var url = getContextPath()+"/bengbu/slym/hyxx.html?zjh=" + zjh ;
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        fixed: false, //不固定
        title: "婚姻信息",
        content: url,
        btnAlign: "c"
    });
}