/**
 * Created by Administrator on 2019/1/21.
 * 南通js
 */
//转发需要根据lcPageEdition判断显示哪个send.html
var lcPageEdition = 'haimen';
var iSabandon = 'hide';

/**
 * 退回件显示退回意见
 */
function showThyj(processInstanceId,taskId) {
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/workflow/process-instances/opinion",
        data: {
            processInstanceId: processInstanceId,
            taskId: taskId,
            type: "back_opinion"
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

/**
 * 判断是否接入互联网+
 */
function showSfjrhlw(taskId) {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/config/rl/sfjrhlw",
        data: {
            taskId: taskId,
        },
        async: false,
        success: function (data) {
            if (data == 1 || data == "1") {
                var alertIndex = layer.open({
                    type: 1,
                    skin: 'bdc-small-tips',
                    title: '信息',
                    area: ['320px'],
                    content: "该银行已接入互联网+，<br/>无需打印纸质证明",
                    btn: ['确定'],
                    btnAlign: 'c',
                    yes: function () {
                        layer.close(alertIndex);
                    }
                });
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}
