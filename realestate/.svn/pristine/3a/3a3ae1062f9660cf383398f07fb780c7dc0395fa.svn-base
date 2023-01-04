/**
 * 发证记录补打 js
 */
/**
 * 办结
 */
var fzjlIndex;
function bj(obj, data) {
    if (data.SFZT != 2) {
        failMsg("未完成收费，无法办结！");
    } else {
        addModel();
        $.ajax({
            url: '/realestate-inquiry-ui/rest/v1.0/fzjlbd/bj',
            dataType: "json",
            type: "GET",
            async: false,
            data: {'gzlslid': data.GZLSLID},
            success: function (data) {
                layer.msg('办结成功');
                tableReload('pageTable', {data: JSON.stringify(queryObject)}, '/realestate-inquiry-ui/dtcx/list/result');
            }
        });
        removeModal();
    }
}

/**
 * 打开发证记录页面
 */
function fzjl(obj, data) {
    fzjlIndex = layer.open({
        type: 2,
        title: "发证记录",
        area: ['1300px', '600px'],
        fixed: false, //不固定bdcFzjl
        maxmin: true, //开启最大化最小化按钮
        content: "/realestate-register-ui/view/fzjl/fzjl.html?processInsId=" + data.GZLSLID + "&zsid=" + data.ZSID + "&endBtnVisible=true"
    });
    layer.full(fzjlIndex);
}

/**
 *  结果字段EMSDH增加颜色
 */
function loadComplete() {
    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        if ($(getTd[i]).attr('data-field') == "EMSDH") {
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            if (getTdCell[0].innerText != '') {
                var text = getTdCell[0].innerText;
                $(getTd[i]).children('div').empty();
                $(getTd[i]).children('div').append('<span class="bdc-emsdh">'+ text +'</span>');
            }
        }
    }
    changeTrBackgroundExceptRight([
        {name: 'bdc-emsdh', color: '#333'}, true]);
}