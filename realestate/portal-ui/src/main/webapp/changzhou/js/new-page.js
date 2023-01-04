/**
 * Created by lixin on 2019/1/21.
 * 常州js
 */
//转发需要根据lcPageEdition判断显示哪个send.html
var lcPageEdition = 'changzhou';
var iSabandon = 'hide';

/**
 * 判断当前流程是否显示撤销
 */
function isAbandon(gzlslid) {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/workflow/process-instances/abandon?gzlslid=" + gzlslid,
        async: false,
        success: function (data) {
            if (data === 'success') {
                iSabandon = "show";
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}

/**
 * 退回件显示退回意见
 */
function showThyj(processInstanceId,taskId) {
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/workflow/process-instances/opinion/back",
        data: {
            processInstanceId: processInstanceId,
            taskId: taskId,
            type: "next_back_opinion"
        },
        async: false,
        success: function (data) {
            if(isNotBlank(data)){
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