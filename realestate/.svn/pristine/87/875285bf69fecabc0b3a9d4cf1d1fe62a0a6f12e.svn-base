/**
 * @author "<a href="mailto:youisyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/12/09
 * @description 锁定信息表单JS
 */
$(document).ready(function () {
    // 获取参数
    var sdxxid = $.getUrlParam('sdxxid');
    // 表单ID
    var formStateId = $.getUrlParam('formStateId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');

    /**
     * 获取锁定信息数据
     */

    $.ajax({
        url: BASE_URL + '/sdxx/' + sdxxid,
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                form.val('form', data);
            }
              // 渲染日期
            renderDate();
        }
    });



    //设置权限控制样式
    if (readOnly || !isNullOrEmpty(sdxxid)) {
        if (readOnly == true || readOnly == "true") {
            setAllElementDisabled();
        }
    }

});