/**
 * 代码对比高亮显示 js
 */
/**
 * 查询修改日志
 */
function queryDbGl(processInsId) {
    getReturnData("/rest/v1.0/blxx/log/dbgl", {processInsId: processInsId}, "GET", function (data) {
        if (data) {
            for (var i = 0; i < data.length; i++) {
                var name = data[i].ymname;
                if ($('input[log="' + name + '"]').length > 0) {
                    $('input[log="' + name + '"]').parent().addClass('bdc-change-input');
                    $('input[log="' + name + '"]').css({"background-color": "#eaf4fe"})
                } else if ($('select[log="' + name + '"]').length > 0) {
                    // $('select[log="' + name + '"]').parents('.layui-input-inline').addClass('bdc-change-input');
                    // $('select[log="' + name + '"]').parents('.layui-input-inline').find('input').css({"background-color": "#eaf4fe"});
                    $('select[log="' + name + '"]').parent().addClass('bdc-change-input');
                    $('select[log="' + name + '"]').parent().find('input').css({"background-color": "#eaf4fe"});
                } else if ($('textarea[log="' + name + '"]').length > 0) {
                    $('textarea[log="' + name + '"]').parents('.layui-input-inline').addClass('bdc-change-input');
                    $('textarea[log="' + name + '"]').css({"background-color": "#eaf4fe"})
                }

            }
            // renderChangeTips(JSON.parse(data.value).tsxx);
        }
    }, function (error) {
    })
}

/**
 * 查询修改日志
 */
function queryDbGlName(processInsId) {
    getReturnData("/rest/v1.0/blxx/log/dbgl", {processInsId: processInsId}, "GET", function (data) {
        if (data) {
            for (var i = 0; i < data.length; i++) {
                var name = data[i].ymname;
                if (name.indexOf("zsid") == 0 || name.indexOf("qlid") == 0 || name.indexOf("xmid") == 0) {
                    continue;
                }
                if ($('input[name="' + name + '"]').length > 0) {
                    $('input[name="' + name + '"]').parent().addClass('bdc-change-input');
                    $('input[name="' + name + '"]').css({"background-color": "#eaf4fe"})
                } else if ($('select[name="' + name + '"]').length > 0) {
                    // $('select[name="' + name + '"]').parents('.layui-input-inline').addClass('bdc-change-input');
                    // $('select[name="' + name + '"]').parents('.layui-input-inline').find('input').css({"background-color": "#eaf4fe"});
                    $('select[name="' + name + '"]').parent().addClass('bdc-change-input');
                    $('select[name="' + name + '"]').parent().find('input').css({"background-color": "#eaf4fe"});
                } else if ($('textarea[name="' + name + '"]').length > 0) {
                    $('textarea[name="' + name + '"]').parents('.layui-input-inline').addClass('bdc-change-input');
                    $('textarea[name="' + name + '"]').css({"background-color": "#eaf4fe"})
                }

            }
            // renderChangeTips(JSON.parse(data.value).tsxx);
        }
    }, function (error) {
    })
}

/**
 * 渲染权利信息高亮
 */
function queryQlxxDbGl(processInsId) {
    getReturnData("/rest/v1.0/blxx/log/dbgl", {processInsId: processInsId}, "GET", function (data) {
        if (data) {
            for (var i = 0; i < data.length; i++) {
                var name = data[i].ymname;
                var element = window.frames["qlxx"].document.getElementById(name);
                if (element !== null) {
                    $(element).parent().addClass('bdc-change-input');
                    $(element).parent().find('input').css({"background-color": "#eaf4fe"});
                }
            }
        }
    }, function (error) {
    })
}