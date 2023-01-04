$("#cbzdxx").click(function () {
    if ($("#cbzdDjh").val()) {
        return;
    }
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var bdcdyh = $("#bdcdyh").val();
        // loading加载
        addModel();
        $.ajax({
            url: "../djdcb/cbzd",
            dataType: "json",
            data: {
                bdcdyh: bdcdyh,
                qjgldm:qjgldm
            },
            success: function (data) {
                removeModal();
                // layer.closeAll();
                if (data.cbzdDcb) {
                    form.val("cbzdForm", data.cbzdDcb);
                    form.val("cbzddcxxForm", data.cbzdDcb);
                    initZd($("#cbzdForm"));
                }
                if (data.cbfAndJtcyList) {
                    generateCbfJtcyList(data.cbfAndJtcyList);
                    initZd($("#cbfxxForm"));
                }
                if (data.fbf) {
                    form.val("fbfxxForm", data.fbf);
                    initZd($("#fbfxxForm"));
                }

                // disabledAddFa();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr, this)
            }
        });
    });
})

//生成权利人list表单 qlrList：权利人列表 listStartWhith：从第几个开始生成 isFg：是否需要覆盖
function generateCbfJtcyList(cbfAndJtcyList) {
    if (!$.isEmptyObject(zdList) && !$.isEmptyObject(zdList["SZdCbyhzgxDO"]) && !$.isEmptyObject(cbfAndJtcyList)) {
        $.each(cbfAndJtcyList, function (index, cbf) {
            if (!$.isEmptyObject(cbf.nydJtcyDOList)) {
                $.each(cbf.nydJtcyDOList, function (index, item) {
                    $.each(zdList["SZdCbyhzgxDO"], function (j, jtem) {
                        if (jtem.DM === item.gx) {
                            item.gx = jtem.MC;
                            return false;
                        }
                    })
                })
            }
        })
    }
    layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laytpl = layui.laytpl;
        var element = layui.element;
        var json = {
            data: cbfAndJtcyList
        };
        //获取模板
        var tpl = $("#cbfJtcyTpl").html();
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            $("#cbfxx-jtcy").html(html);
        });
        form.render();
        element.render();
    })
}

