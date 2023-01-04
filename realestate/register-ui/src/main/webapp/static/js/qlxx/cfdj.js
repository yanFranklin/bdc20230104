/**
 * @author "<a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2018/12/17
 * @description 查封登记信息表单JS
 */
$(document).ready(function(){
    // 获取参数
    var xmid = $.getUrlParam('xmid');
    // 表单ID
    var formStateId = $.getUrlParam('formStateId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');
    $('.qlqtzksdtx').attr('style','display:none');
    $('.fjsdtx').attr('style','display:none');

    /**
     * 字典
     */
    getZd(function(data){
        // 渲染字典项
        $.each(data.cflx, function(index,item) {
            $('#cflx').append('<option value="'+item.DM +'">'+item.MC +'</option>');
        });

        form.render('select');
    });

    /**
     * 获取权利信息数据
     */
    getQlxx(xmid);



    /**
     * 保存更新表单数据
     */
    saveForm('/qlxx/cf');

    //设置权限控制样式
    if (readOnly || !isNullOrEmpty(formStateId)) {
        getStateByIdQlxx(readOnly, formStateId, "cfdj");
    }
    var showXgLog = $.getUrlParam("showXgLog");
    if(showXgLog){
        queryXgLog(xmid);
        renderChange("",form,"cfdj");
        renderDate(form,"cfdj");
    }
    // 移除遮罩
    removeModel();
});
