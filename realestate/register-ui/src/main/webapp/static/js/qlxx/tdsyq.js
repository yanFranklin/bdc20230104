/**
 * @author "<a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2018/12/13
 * @description 土地所有权登记信息表单JS
 */

$(document).ready(function () {
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
    getZd(function (data) {
        // 渲染字典项
        $.each(data.djlx, function (index, item) {
            $('#djlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.mjdw, function (index, item) {
            $('#mjdw').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.tdyt, function (index, item) {
            $('#zdzhyt').append('<option value="' + item.DM + '">' + item.MC + '</option>');
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

        if (data && zdList.zjzl) {
            // 转换证件类型字典
            for (var i = 0, len = data.length; i < len; i++) {
                for (var j = 0, zdlen = zdList.zjzl.length; j < zdlen; j++) {
                    if (zdList.zjzl[j].DM == data[i].zjzl) {
                        data[i].zjzlmc = zdList.zjzl[j].MC;
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

    //设置权限控制样式
    if (readOnly || !isNullOrEmpty(formStateId)) {
        getStateByIdQlxx(readOnly, formStateId, "tdsyq");
    }


    /**
     * 保存更新表单数据
     */
    saveForm('/qlxx/tdsyq');

    var showXgLog = $.getUrlParam("showXgLog");
    if(showXgLog){
        queryXgLog(xmid);
        renderChange("",form,"tdsyq");
        renderDate(form,"tdsyq");
    }
});


