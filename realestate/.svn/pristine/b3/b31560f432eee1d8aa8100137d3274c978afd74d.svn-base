/**
 * @author "<a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2018/12/17
 * @description 预告登记表单JS
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
        $.each(data.ygdjzl, function (index, item) {
            $('#ygdjzl').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.fwyt, function (index, item) {
            $('#ghyt').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.fwxz, function (index, item) {
            $('#fwxz').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.jedw, function (index, item) {
            $(".jedw").append('<option value="' + item.DM + '">' + item.MC + '</option>');
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

    //获取义务人数据
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/ywr/' + xmid,
        type: "GET",
        dataType: "json",
        success: function (data) {
            // 封装权利人列表格式数据
            var qlrxx = {};
            qlrxx.title = "权利人信息";

            if (data && zdList.zjzl) {
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
            var getTpl = ywrTpl.innerHTML;
            laytpl(getTpl).render(qlrxx, function (html) {
                $("#tbody3").before(html)
            });
            form.render();
            resetInputDisabledCss();
        },
        complete: function (XMLHttpRequest, textStatus) {
        }
    });

    /**
     * 保存更新表单数据
     */
    saveForm('/qlxx/yg');

    //设置权限控制样式
    if (readOnly || !isNullOrEmpty(formStateId)) {
        getStateByIdQlxx(readOnly, formStateId, "ygdj");
    }
    var showXgLog = $.getUrlParam("showXgLog");
    if(showXgLog){
        queryXgLog(xmid);
        renderChange("",form,"ygdj");
        renderDate(form,"ygdj");
    }
});
