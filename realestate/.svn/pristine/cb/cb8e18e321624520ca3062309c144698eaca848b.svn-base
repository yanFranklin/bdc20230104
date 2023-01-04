var qlrid = $.getUrlParam("qlrid");

layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl'], function() {
    var $ = layui.jquery,
        form = layui.form,
        element = layui.element,
        layer = layui.layer,
        laytpl = layui.laytpl;

    $.ajax({
        url: "/realestate-inquiry-ui/cfqlr/split/" + qlrid,
        type: "get",
        success: function (data) {
            if (isNotBlank(data)) {
                var dataArrary = new Array();
                $.each(data.qlrmc, function (key, item) {
                    var dataObj = new Object();
                    dataObj.qlrmc = item;
                    dataObj.zjh = data.zjh;
                    dataArrary.push(dataObj);
                })

                var getTpl = cfqlrTpl.innerHTML,
                    view = document.getElementById('qlrcfList');
                laytpl(getTpl).render(dataArrary, function(html) {
                    $("#qlrcfList").append(html);
                });
                form.render();
            }
        }
    })

    $("#save").on('click', function () {
        var index = parent.layer.getFrameIndex(window.name);
        var cfjgData = getFormRows("qlrcfForm");
        $.ajax({
            url: "/realestate-inquiry-ui/cfqlr/save?qlrid=" + qlrid,
            type: "post",
            data: JSON.stringify(cfjgData),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                setTimeout(removeModal(), 100);
                parent.layui.table.reload('pageTable',{page:{curr:1}});
                parent.layer.msg("保存成功");
                parent.layer.close(index);
            }, error: function (e) {
                showErrorInfo(e);
            }
        });

    });

    $("#cancel").on('click', function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index)
    });
});

/**
 * 将form中table的多个tr转换为数组
 * tr中的form组件需要有一致的name属性
 * @date 2019.12.23
 * @author hanyi
 * @param formId 对应form的id
 * @return 对象数组
 */
function getFormRows(formId) {
    var resultData = [];
    var formData = $('#' + formId).serializeArray();
    var tempObj = {};
    var pri = 1;
    $.each(formData, function (index, item) {
        if (!tempObj[item.name]) {
            tempObj[item.name] = item.value;
        } else {
            if (isEmptyObject(tempObj["priority"])) {
                tempObj["priority"] = pri;
            }
            resultData.push(tempObj);
            pri += 1;
            tempObj = {};
            tempObj[item.name] = item.value;
        }
    });
    if (isEmptyObject(tempObj["priority"])) {
        tempObj["priority"] = pri;
    }
    resultData.push(tempObj);
    return resultData;

}