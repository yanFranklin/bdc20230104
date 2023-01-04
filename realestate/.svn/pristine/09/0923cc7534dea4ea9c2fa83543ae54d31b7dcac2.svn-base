/**
 * 征迁：退回操作
 */
layui.use(['form', 'jquery', 'element'], function () {
    var form = layui.form;

    var sqxxid = $.getUrlParam('sqxxid');
    $("#sqxxid").val(sqxxid);

    /**
     * 表单数据提交,编辑
     */
    form.on('submit(submitBtnEdit)', function(data) {
        if(isNullOrEmpty(data.field.thyy)) {
           warnMsg("请输入退回原因！");
           return ;
        }

        addModel();
        $.ajax({
            type: 'POST',
            url: getContextPath() + "/rest/v1.0/zq/zxsq/th",
            data: JSON.stringify(data.field),
            dataType: "text",
            contentType: "application/json",
            success: function (res) {
                if(!isNullOrEmpty(res)) {
                    successMsg('退回成功!');
                    parent.setState();
                    setTimeout(function(){parent.layer.closeAll()},1000);
                } else {
                    failMsg("退回失败，请重试！");
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
                removeModal();
            },
            complete: function () {
                removeModal();
            }
        });
        return false;
    });
});

function closeWin(){
    parent.layer.closeAll();
}
