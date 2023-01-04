layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
//页面入口
var form;
var fieldsStr = getQueryString("fieldsStr");
var objStr = getQueryString("objStr");
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
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
    form.render('select');
});

var zdList = {};
function loadData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        }
    });
    generateYwsj();
}





function generateYwsj() {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'formSelects'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var formSelects = layui.formSelects;
        var obj = JSON.parse(decodeURIComponent(objStr));
        var json = {
            ywsj: obj,
            zd: zdList
        };
        var tpl = addQlrTmpl.innerHTML, view = document.getElementById('bdc-popup-large');
        //渲染数据

        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        fields = fieldsStr.split(",");
        for (var i = 0; i < fields.length; i++) {
            $("#"+fields[i]).parent().parent(".layui-inline").css("display","block");
        }
        form.render();
        renderDate(form);
        var result = verifyform(form);
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


        form.render("select");
        //renderSelect();
        disabledAddFa("addYwsjForm");

    })
}



function cancel() {
    window.parent.cancelEdit();
}


var callbackdata = function(){
    var obj = {};
    $(".ywsj").each(function (i) {
        var value = $(this).val();
        var name = $(this).attr('name');
        obj[name] = value;
    });
    return obj;
}





