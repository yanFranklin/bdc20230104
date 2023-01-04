/**
 * @author "<a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2018/12/17
 * @description 异议登记信息表单JS
 */
$(document).ready(function () {
    // 获取参数
    var xmid = $.getUrlParam('xmid');
    // 表单ID
    var formStateId = $.getUrlParam('formStateId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');

    /**
     * 字典
     */
    getZd(function (data) {
        // 渲染字典项
        $.each(data.djlx, function (index, item) {
            $('#djlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });

        form.render('select');
    });

    /**
     * 获取权利信息数据
     */
    getQlxx(xmid);

    /**
     * 获取权利人数据
     */
    getQlr(xmid, function (data) {
        // 封装权利人列表格式数据
        var qlrxx = {};
        qlrxx.title = "权利人信息";

        if (data) {
            // 转换字典
            for (var i = 0, len = data.length; i < len; i++) {
                for (var j = 0, zdlen = zdList.zjzl.length; j < zdlen; j++) {
                    if (zdList.zjzl[j].DM == data[i].zjzl) {
                        data[i].zjzlmc = zdList.zjzl[j].MC;
                    }
                }

                for (var j = 0, zdlen = zdList.qlrlx.length; j < zdlen; j++) {
                    if (zdList.qlrlx[j].DM == data[i].qlrlx) {
                        data[i].qlrlxmc = zdList.qlrlx[j].MC;
                    }
                }
            }
        }

        qlrxx.list = data;
        var getTpl = qlrTpl.innerHTML;
        laytpl(getTpl).render(qlrxx, function (html) {
            $("#tbody2").before(html)
        });
        form.render();
        resetInputDisabledCss();
    });

    /**
     * 保存更新表单数据
     */
    saveForm('/qlxx/yy');

    //设置权限控制样式
    if (readOnly || !isNullOrEmpty(formStateId)) {
        getStateByIdQlxx(readOnly, formStateId, "yydj");
    }
    var showXgLog = $.getUrlParam("showXgLog");
    if(showXgLog){
        queryXgLog(xmid);
        renderChange("",form,"yydj");
        renderDate(form,"yydj");
    }
});