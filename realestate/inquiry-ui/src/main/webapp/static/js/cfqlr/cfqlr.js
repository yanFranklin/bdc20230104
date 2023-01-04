// 获取页面表单权限
var qxdata = false;
/**
 * 点击拆分按钮
 * @param obj
 * @param data
 */
function cf(obj, data) {
    if (qxdata === false){
        failMsg("没有拆分权限，请联系管理员！");
        return;
    }
    layer.open({
        title: '拆分页面',
        type: 2,
        skin: 'layui-layer-lan',
        area: ['960px', '600px'],
        content: '/realestate-inquiry-ui/view/cfqlr/cfym.html?qlrid=' + data.QLRID
    });
}

function loadComplete() {
    $.ajax({
        url: "/realestate-inquiry-ui/cfqlr/module/authority?moduleCode=cfqlr",
        type: 'GET',
        async: false,
        success: function (data) {
            if (data && !isEmptyObject(data)) {
                qxdata = (data.indexOf("update") >= 0);
            }
        }, error: function (xhr, status, error) {
            failMsg("页面权限请求设置失败，请联系管理员！");
        }
    });

    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        if ($(getTd[i]).attr('data-field') == "QLRID") {
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            if (isNotBlank(getTdCell[0].innerText)) {
                $.ajax({
                    url: "/realestate-inquiry-ui/cfqlr/cfsj/" + getTdCell[0].innerText,
                    type: "get",
                    async: false,
                    success: function (data) {
                        if (isNotBlank(data)) {
                            var tdList = getTdCell.parent().parent().children();
                            for (var j = 0; j < tdList.length; j++) {
                                if ($(tdList[j]).attr('data-field') == "CFSJ") {
                                    var tdCell = $(tdList[j]).find('.layui-table-cell');
                                    tdCell[0].innerHTML = Format(formatDate(data), "yyyy-MM-dd hh:mm:ss");
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}