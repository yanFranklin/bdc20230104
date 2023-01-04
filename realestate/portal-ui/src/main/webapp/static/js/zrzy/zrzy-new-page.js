/**
 * Created by Administrator on 2019/1/21.
 * 合肥js
 */
//转发需要根据lcPageEdition判断显示哪个send.html
var lcPageEdition = 'standard';
var iSabandon = 'hide';
//  仅在受理节点撤销申请
var showAandonTaskNames = ["受理"];

/**
 * 判断当前流程是否显示撤销
 */
function isAbandon(gzlslid) {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/workflow/zrzy/process-instances/abandon?gzlslid=" + gzlslid,
        async: false,
        success: function (data) {
            if (data === 'success') {
                iSabandon = "show";
            }
        }, error: function (e) {
            //response.fail(e, '');
        }
    });
}

/**
 * 退回件显示退回意见
 */
function showThyj(processInstanceId, taskId) {
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/workflow/zrzy/process-instances/opinion",
        data: {
            processInstanceId: processInstanceId,
            taskId: taskId,
            type: "back_opinion"
        },
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                var getTpl = thyjTpl.innerHTML;
                laytpl(getTpl).render(data, function (html) {
                    $('.bdc-fj-default-content').before(html);
                });
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}
