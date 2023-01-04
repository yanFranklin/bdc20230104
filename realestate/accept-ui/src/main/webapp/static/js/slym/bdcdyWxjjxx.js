var bdcdyh = getQueryString("bdcdyh");
var zdList;



$(function () {
    addModel();
    setTimeout(function () {
        try {
            $.when(loadData()).done(function () {
                removeModal();
            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e,"加载失败");
            return
        }
    }, 10);
});


//获取页面数据
function loadData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
                loadBdcdyh()
            }
        }
    });
}


function loadBdcdyh() {
    $.ajax({
        url: getContextPath() + "/slym/xm/wxjjxx",
        type: 'GET',
        dataType: 'json',
        data: {bdcdyh: bdcdyh},
        success: function (data) {
            generateBdcdy(data);
        }
    });
}


function generateBdcdy(bdcSlWxjjxx) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;

        var json = {
            bdcSlWxjjxx: bdcSlWxjjxx,
            zd: zdList,
        };
        var tpl = wxjjxxTpl.innerHTML, view = document.getElementById('bdc-popup-large');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        renderSelect();
        form.render("select");
        renderDate(form);

    })
}






