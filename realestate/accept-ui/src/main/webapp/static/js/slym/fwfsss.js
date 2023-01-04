//页面入口
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
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

var zdList;

function loadData() {
    $.ajax({
        url: getContextPath()+"/bdczd",
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
                loadFwfsss(xmid);
            }
        }
    });
}

function loadFwfsss(xmid) {
    $.ajax({
        url: getContextPath()+"/slym/fwfsss/list/xm",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid},
        success: function (data) {
            generateFwfsss(data);
        },error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function generateFwfsss(data) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {
            bdcfwfsssDOList:data,
            zd:zdList
        };
        var tpl = fwfsssTpl.innerHTML, view = document.getElementById("fwfsss");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });

        form.render('select');
        disabledAddFa();
        form.on("submit(updateFwfsss)", function (data) {
            addModel();
            setTimeout(function () {
                try {
                    $.when(updateFwfsss()).done(function () {
                        removeModal();
                    })
                } catch (e) {
                    removeModal();
                    delAjaxErrorMsg(xhr,"保存失败");
                }
            }, 10);
            return false;
        })
    })
}

function updateFwfsss(){
    var fwfsssList = [];
    var fwfsss = {};
    var fwfsssFormArray = $("#fwfsssForm").serializeArray();
    if(fwfsssFormArray.length ===0){
        layer.msg("不存在数据，无法保存");
        return false;
    }
    for (var i = 0; i < fwfsssFormArray.length; i++) {
        var dataName = fwfsssFormArray[i].name;
        fwfsss[dataName] = fwfsssFormArray[i].value;
        if ((i + 1) % 5 === 0) {
            fwfsssList.push(fwfsss);
            fwfsss = {};
        }

    }
    $.ajax({
            url: getContextPath() + "/slym/fwfsss/list",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(fwfsssList),
            success: function (data) {
                if (data > 0) {
                    ityzl_SHOW_SUCCESS_LAYER("保存成功");
                }
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });




}