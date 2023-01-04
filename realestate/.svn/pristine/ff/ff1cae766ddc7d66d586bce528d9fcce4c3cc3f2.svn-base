/**
 * Created by lixin on 2019/1/21.
 * 常州js
 */
//转发需要根据lcPageEdition判断显示哪个send.html
var lcPageEdition = 'bengbu';
var iSabandon = 'hide';
//  仅在受理节点撤销申请
var showAandonTaskNames = ["受理"];

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