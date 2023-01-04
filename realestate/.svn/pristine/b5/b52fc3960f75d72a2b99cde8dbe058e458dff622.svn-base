layui.use(['form', 'table', 'jquery', 'laytpl', 'element', 'laydate'], function () {
    var form = layui.form,
        laytpl = layui.laytpl;
    var djh =$("#djh").val();
    if(isNotBlank(djh)) {
        addModel();
        $.ajax({
            url: "../zdjsydlhxx/zdjsydsyb",
            dataType: "json",
            data: {
                djh: djh
            },
            success: function (data) {

                layer.closeAll();
                form.val("form", data);
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr, this);
            }
        });
        $.ajax({
            url: "../zdjsydlhxx/jsydzrz",
            dataType: "json",
            data: {
                lszd: djh
            },
            success: function (data) {
                removeModal();
                // 工程进度代码转名称
                for (var i = 0; i < data.length; i++) {
                    data[i].gcjdmc = '';
                    if (data[i].gcjd == '1') {
                        data[i].gcjdmc = "拟建";
                    }
                    if (data[i].gcjd == '2') {
                        data[i].gcjdmc = "在建";
                    }
                    if (data[i].gcjd == '3') {
                        data[i].gcjdmc = "竣工";
                    }
                    if (data[i].gcjd == '4') {
                        data[i].gcjdmc = "首登";
                    }
                    if (data[i].gcjd == '5') {
                        data[i].gcjdmc = "已入网";
                    }
                }

                var getTpl = zdjsydLhxxTpl.innerHTML
                    , view = document.getElementById('lhxx');
                laytpl(getTpl).render(data, function (html) {
                    view.innerHTML = html;
                });
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr, this);
            }
        });
    }else{
        var getTpl = zdjsydLhxxTpl.innerHTML
            , view = document.getElementById('lhxx');
        laytpl(getTpl).render([], function (html) {
            view.innerHTML = html;
        });
    }


});