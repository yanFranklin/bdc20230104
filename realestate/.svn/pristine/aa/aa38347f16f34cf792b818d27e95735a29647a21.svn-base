/**
 * 完税状态审核js
 */
/**
 * 查看附件
 */
function ckfj(obj, data) {
    window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + data.GZLSLID);
}
/**
 * 审核通过
 */
function shtg(obj, data) {
    console.info(data);
    layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认审核通过',
        area: ['320px'],
        content: '是否确认审核通过？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function (index) {
            addModel();
            console.info(data.XMID);
            var param = {
                xmid : data.XMID,
            }
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/wsztlw/wsztshtg",
                type: 'POST',
                data: JSON.stringify(param),
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    removeModal();
                    if(data > 0){
                        successMsg("修改成功");
                    }else{
                        failMsg("修改失败");
                    }
                }, error: function (xhr, status, error) {
                    removeModal();
                }
            })
            layer.close(index);
        },
        btn2: function (index) {
            layer.close(index);
        }
    });
}