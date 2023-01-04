/**
 * @author "<a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2018/12/14
 * @description 房地产权登记信息（独幢、层、套、间房屋）表单JS
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
        let ddom =  $('#djlx');
        $.each(data.djlx, function (index, item) {
            $('#djlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.fwxz, function (index, item) {
            $('#fwxz').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.fwlx, function (index, item) {
            $('#fwlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.fwjg, function (index, item) {
            $('#fwjg').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        var selectAppend = "";
        $.each(data.fwyt, function(index, item) {
            selectAppend += '<option value="' + item.DM + '">' + item.MC + '</option>';
        });
        $('#ghyt').append(selectAppend);
        $('#pzyt').append(selectAppend);
        $('#sjyt').append(selectAppend);
        $.each(cgList, function (index, item) {
            $('#cgList').append('<option value="' + item + '">' + item + '</option>');
        });
        $.each(data.jedw, function (index, item) {
            $('#jedw').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.tdyt, function (index, item) {
            $('#zdzhyt').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });

        form.render('select');
    });

    form.on('select(cgList)', function() {
        var selectedValue = $('select[name="cgList"] > option:selected').val();
        $('input[name="cg"]').val(selectedValue);
        form.render(select);
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

    /**
     * 保存更新表单数据
     */
    saveForm('/qlxx/fdcq');

    //设置权限控制样式
    if (readOnly || !isNullOrEmpty(formStateId)) {
        getStateByIdQlxx(readOnly, formStateId, "fdcq");
    }
    var showXgLog = $.getUrlParam("showXgLog");
    if(showXgLog){
        queryXgLog(xmid);
        renderChange("",form,"fdcq");
        renderDate(form,"fdcq");
    }
});