layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl'], function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laytpl = layui.laytpl;

    tableInit();
    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询内容
        var obj = {};


        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            if (!isNullOrEmpty(value)) {
                obj[name] = value;
            }
        });

        if (isNullOrEmpty($('#qlr').val()) || isNullOrEmpty($('#bdcqzh').val())){
            warnMsg("必须输入权利人和不动产权证号");
            return false;
        }
        addModel();

        $.ajax({
            url: '/realestate-inquiry-ui/rest/v1.0/yhcx/search',
            type: "POST",
            data: JSON.stringify(obj),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 页面固定部分赋值
                $("#cqqlr").val(data.qlr);
                $("#zsh").val(data.bdcqzh);
                $("#djsj").val(data.djsj);
                $("#gyqk").val(data.gyfs);
                $("#sfzx").val(data.sfzx);
                $("#fj").val(data.fj);
                $("#tdqdfs").val(data.qlxz);
                $("#syqjssj").val(data.tdsyjssj);
                $("#yysx").val(data.yyxx);
                $('#sd').val(data.sdxx);

                $.each(data.bdcDyaqDOList, function (index, item) {
                    if (isNotBlank(item.zl)) {
                        item.index = index + 1;
                    }
                    if (isNotBlank(item.djsj)){
                    item.djsj = strFormatDate(item.djsj).Format("yyyy-MM-dd");
                    }
                    if (isNotBlank(item.zwlxjssj)) {
                        item.zwlxjssj = strFormatDate(item.zwlxjssj).Format("yyyy-MM-dd");
                    }
                });

                var getTpl1 = dyDataTpl.innerHTML;
                laytpl(getTpl1).render(data.bdcDyaqDOList, function (html) {
                    $("#tbody2").html(html);
                });

                $.each(data.bdcCfDOList, function (index, item) {
                    if (isNotBlank(item.zl)) {
                        item.index = index + 1;
                    }
                    if (isNotBlank(item.cfsj)) {
                        item.cfsj = strFormatDate(item.cfsj).Format("yyyy-MM-dd");
                    }
                });

                var getTpl2 = cfDataTpl.innerHTML;
                laytpl(getTpl2).render(data.bdcCfDOList, function (html) {
                    $("#tbody3").html(html);
                });

                var getTpl3 = bdcdyxxTpl.innerHTML;
                laytpl(getTpl3).render(data.bdcYhcxCqDTOList, function (html) {
                    $("#tbody-bdcdyxx").html(html);
                });

                $('input').attr("disabled", true);
                $('#qlr').removeAttr("disabled");
                $('#bdcqzh').removeAttr("disabled");
                form.render();
                sdHide();

                saveYhcxDetailLog("YHCX","银行查询", obj);

            },
            error: function (e) {
                tableInit();
                showErrorInfo(e, "查询失败");
                removeModal();
            }, complete: function () {
                removeModal();
            }
        });
    });

    /**
     * 初始化查询结果
     */
    function tableInit() {
        var bdcYhcxCqDTOList = new Array();
        var bdcYhcxCqDTO = new Object();
        bdcYhcxCqDTO.bdcdyh = '';
        bdcYhcxCqDTO.zl = '';
        bdcYhcxCqDTO.zcs = '';
        bdcYhcxCqDTO.szc = '';
        bdcYhcxCqDTO.ghyt = '';
        bdcYhcxCqDTO.fwxz = '';
        bdcYhcxCqDTO.jzmj = '';
        bdcYhcxCqDTO.tdsyqmj = '';
        bdcYhcxCqDTOList.push(bdcYhcxCqDTO);

        var bdcDyaqDOList = new Array();
        var bdcYhcxDyaDTO = new Object();
        bdcYhcxDyaDTO.zl = '';
        bdcYhcxDyaDTO.bdcqzh = '';
        bdcYhcxDyaDTO.qlr = '';
        bdcYhcxDyaDTO.dyfs = '';
        bdcYhcxDyaDTO.dyje = '';
        bdcYhcxDyaDTO.zgzqe = '';
        bdcYhcxDyaDTO.zwlxjssj = '';
        bdcYhcxDyaDTO.djsj = '';
        bdcDyaqDOList.push(bdcYhcxDyaDTO);

        var bdcCfDOList = new Array();
        var bdcCfDO = new Object();
        bdcCfDO.zl = '';
        bdcCfDO.cfjg = '';
        bdcCfDO.cfwh = '';
        bdcCfDO.cfsj = '';
        bdcCfDOList.push(bdcCfDO);

        var getTpl1 = dyDataTpl.innerHTML;
        laytpl(getTpl1).render(bdcDyaqDOList, function (html) {
            $("#tbody2").html(html);
        });

        var getTpl2 = cfDataTpl.innerHTML;
        laytpl(getTpl2).render(bdcCfDOList, function (html) {
            $("#tbody3").html(html);
        });

        var getTpl3 = bdcdyxxTpl.innerHTML;
        laytpl(getTpl3).render(bdcYhcxCqDTOList, function (html) {
            $("#tbody-bdcdyxx").html(html);
        });

        $('#yhcxjg').find('input').val('');
        $('input').attr("disabled", true);
        $('#qlr').removeAttr("disabled");
        $('#bdcqzh').removeAttr("disabled");

        form.render();
        sdHide();
    }

    // 锁定信息无数据时隐藏
    function sdHide(){
        var sd = $('#sd').val();
        if (isNotBlank(sd)) {
            $('#sd').parents('.yhcx-sd').show();
        } else {
            $('#sd').parents('.yhcx-sd').hide();
        }
    }
});

/**
 * 保存操作日志
 * @param logType 日志类型
 * @param viewTypeName 日志信息描述
 * @param data 附加日志信息内容
 */
function saveYhcxDetailLog(logType, viewTypeName, data){
    var json = JSON.parse(JSON.stringify(data));
    json.logType = logType;
    json.viewTypeName = viewTypeName;
    saveYhcxLogInfo(json);
}

/**
 * 保存日志信息
 * @param data 需要保存的日志数据
 */
function saveYhcxLogInfo(data) {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/yhcx/saveLog",
        type: "POST",
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (key) {
        },
        error: function () {
            console.debug("保存日志出错！");
        }
    });
}