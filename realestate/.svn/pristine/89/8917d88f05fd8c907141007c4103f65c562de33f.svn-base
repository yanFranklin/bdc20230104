/**
 * 关闭弹出页面
 */
window.closeWin = function(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};



var checkedElem = [];
Array.prototype.remove = function (index) {
    if (index > -1) {
        this.splice(index, 1);
    }
};
var updateNullOnly = false;
layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layerIndex = parent.layer.getFrameIndex(window.name);
    //form验证
    form.render();
    form.on('checkbox(checkbox)', function (data) {
        if (data.elem.checked) {
            checkedElem.push(data.elem);
        } else {
            var index = $.inArray(data.elem, checkedElem);
            checkedElem.remove(index);
        }
    });

    form.on('checkbox(allChoose)', function (data) {
        checkedElem = [];
        if (data.elem.checked) {
            $.each($("input[lay-filter='checkbox']"), function (i) {
                $(this).next().addClass("layui-form-checked");
                checkedElem.push($(this));
            });
        } else {
            $.each($("input[lay-filter='checkbox']"), function (i) {
                $(this).next().removeClass("layui-form-checked");
            });
        }
    })

    form.on('checkbox(updateNullOnly)', function (data) {
        if (data.elem.checked) {
            updateNullOnly = true;
        } else {
            updateNullOnly = false;
        }
    });

    form.on('submit(mjSubmit)', function (data) {
        if (checkedElem.length > 0) {
            var itemList = [];
            for (var i = 0; i < checkedElem.length; i++) {
                var name = $(checkedElem[i]).attr("columId");
                var value = $("#" + name).val();
                if (!value) {
                    value = "null";
                }
                var item = name + "_" + value;
                itemList.push(item);
            }
            var postData = {};
            postData.updateParamList = itemList;
            postData.fwHsIndexList = fwhsIndexList;
            postData.updateNullOnly = updateNullOnly;
            postData.fwDcbIndex = fwDcbIndex;
            //遮罩
            addModel();
            $.ajax({
                url: "../fwhs/batchupdate",
                dataType: "json",
                traditional: true,
                data: postData,
                success: function (data) {
                    removeModal();
                    if (data.success) {
                        parent.plgxCallBack(layerIndex);
                    } else {
                        layer.msg(data.msg);
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        } else {
            layer.msg("请选中需要更新的面积");
        }
    });
});