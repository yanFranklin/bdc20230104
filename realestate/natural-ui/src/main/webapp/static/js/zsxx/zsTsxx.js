var confirmSize = 0;
var alertSize = 0;
// 登簿返回的验证信息
var yzxx;
// 工作流实例ID
var gzlslid;

function loadTsxx(data,processInsId) {
    yzxx = JSON.stringify(data);
    gzlslid = processInsId;
    $.each(data, function (index, item) {
        if (item.yzlx == 1) {
            confirmSize++;
            $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + item.tsxx + '<a href="javascrit:;" onclick="remove(this);return false">忽略</a></p>');
        } else {
            alertSize++;
            $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + item.tsxx + '</p>');
        }
    });
    //当只存在提示信息时展现全部忽略按钮，存在警告不展示忽略按钮
    if (alertSize > 0) {
        $("#ignoreAll").remove();
    }
}

function generate() {
    confirmSize = 0;
    alertSize = 0;
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {};
        var tpl = tsxxTpl.innerHTML, view = document.getElementById("tsxx");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
    })
}

/**
 * 提示信息忽略方法
 * @param elem 忽略对象
 */
function remove(elem) {
    addModel();
    $(elem).parent().remove();
    confirmSize--;
    //在没有警告提示下创建
    if (alertSize === 0 && confirmSize === 0) {
        glGzyz = true;
        layer.close(warnLayer);
        // 保存登簿日志，保存验证信息
        var param = {};
        param.yzxx = yzxx;
        param.gzlslid = gzlslid;
        saveDbLog(param);

        // 重置模板信息
        generate();
        // 触发登簿按钮
        clickDbBtn();
    } else {
        removeModel();
    }
}

function removeAll() {
    glGzyz = true;
    layer.close(warnLayer);
    // 保存登簿日志，保存验证信息
    var param = {};
    param.yzxx = yzxx;
    param.gzlslid = gzlslid;
    saveDbLog(param);

    addModel();
    // 触发登簿按钮
    clickDbBtn();
}

/**
 * 触发登簿按钮
 */
function clickDbBtn() {
    // 重置点击次数
    sddbClickNum = 0;
    var $db = $("#db");
    if ($db && $db.length > 0) {
        $db.click();
    } else {
        $('#batchDb').click();
    }
}