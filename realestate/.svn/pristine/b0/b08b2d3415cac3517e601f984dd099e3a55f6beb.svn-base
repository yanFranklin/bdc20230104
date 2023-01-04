/**
 * @author "<a href="mailto:lixin1@gtmap.cn>lixin</a>"
 * @version 1.0, 2019/6/19
 * @description 信息补录公共 JS
 */
/**
 * 信息提示框的大小
 * @type {number}
 */
var confirmSize = 0;
var alertSize = 0;

function setAllElementDisabled() {
    // 设置输入框不可编辑
    var input = $('input');
    for (var i = 0; i < input.length; i++) {
        if (input[i].type !== "hidden") {
            input[i].setAttribute('disabled', 'off');
        }
    }
    $('select').attr('disabled', 'off');
    $('textarea').attr('disabled', 'off');
    $('checkbox').attr('disabled', 'off');
    $('radio').attr('disabled', 'off');
    // 设置按钮不可使用
    var button = $('button');
    for (var j = 0; j < button.length; j++) {
        //申请人的详细按钮和权利信息的按钮在readOnly下可点击，里面的内容不可编辑
        if (button[j].value !== "readOnly") {
            if (button[j].id === "printButton" && printValue === "true") {
                continue;
            }
            button[j].setAttribute('disabled', 'off');
            $(button[j]).addClass('layui-btn-disabled');
            $(button[j]).removeClass('bdc-major-btn');
            $(button[j]).removeClass('bdc-delete-btn');
        }
    }
    // $('.layui-btn').attr('disabled', 'off');
    // $('.layui-btn').addClass('layui-btn-disabled');
    $('a').removeAttr('onclick');
}

function disabledAddFa() {
    var disabledElArray = $(":disabled");
    if (disabledElArray !== null && disabledElArray.length > 0) {
        for (var i = 0; i < disabledElArray.length; i++) {
            var disabledEl = disabledElArray[i];
            if (disabledEl.type !== "checkbox" && disabledEl.type !== "radio") {
                if ($(disabledEl).is("input") || $(disabledEl).is("textarea") || $(disabledEl).is("select")) {
                    var imgArray = $(disabledEl).parent().find("img");
                    if (imgArray.length === 0) {
                        $(disabledEl).parent().append("<img src=\"../../static/lib/bdcui/images/lock.png\" alt=\"\">");
                        $(disabledEl).parent().addClass('bdc-date-disabled');
                    }
                }
            }
        }
    }
    // $('.layui-select-disabled i').addClass('bdc-hide');
    // var img = '<img src="../../static/lib/bdcui/images/lock.png" alt="">';
    // $('.layui-select-disabled dl').after(img);
    $('.layui-select-disabled i').removeClass('layui-edge');
    $('.layui-select-disabled i').addClass('bdc-hide');
    $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
}

/**
 * 加载提示信息
 * @param data 规则验证返回的结果
 */
function loadTsxx(data) {
    $("#confirmInfo").html('');
    $("#alertInfo").html('');
    $.each(data, function (index, item) {
        if (item.yzlx === "confirm") {
            confirmSize++;
            $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + item.msg + '<a href="javascrit:;" onclick="remove(this);return false" >忽略</a></p>');
        } else if (item.yzlx === "alert" || item.yzlx === "alert-exclude") {
            alertSize++;
            $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + item.msg + '</p>');
        }
    });
    //当只存在提示信息时展现全部忽略按钮，存在警告不展示忽略按钮
    if (alertSize > 0) {
        $("#ignoreAll").remove();
    }
}

//规则验证提示框
function gzyztsxx() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery,
            layer = layui.layer;
        warnLayer = layer.open({
            type: 1,
            title: '提示信息',
            skin: 'bdc-tips-right',
            // closeBtn: 0, //不显示关闭按钮
            shade: [0],
            area: ['600px'],
            offset: 'r', //右下角弹出
            time: 5000000, //2秒后自动关闭
            anim: 2,
            content: $('#tsxx').html(),
            success: function () {
                $('.bdc-tips-right .bdc-right-tips-box .layui-icon-close').on('click', function () {
                    layer.close(warnLayer);
                    generate();
                })
            },
            cancel: function () {
                layer.close(warnLayer);
                generate();
            }
        });
    });

}


/**
 * 渲染提示页面
 */
function generate() {
    alertSize = 0;
    confirmSize = 0;
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
        layer.close(warnLayer);
        generate();
        csh(checkData);
    } else {
        removeModel();
    }
}

/**
 * 注销权利方法
 */
window.zxQl = function (checkQlxx) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var $ = layui.jquery,
            layer = layui.layer;
        // 注销原因清空
        $('#zxyy').val("");
        layer.open({
            title: '注销权利',
            type: 1,
            area: ['960px', '300px'],
            btn: ['注销', '取消'],
            content: $('#bdc-popup-large'),
            yes: function (index, layero) {
                checkQlxx(index);
            },
            btn2: function (index, layero) {
                //取消 的回调
            },
            cancel: function () {
                //右上角关闭回调
                //return false 开启该代码可禁止点击该按钮关闭
            }
        });
    });

};


/**
 * 注销权利时选择 gzldyid
 */
function listCategoryProcess() {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/blxx/blxz/process",
        async: true,
        success: function (data) {
            // 清空下拉框
            $("#process").empty();
            // 过滤数据页面只显示注销登记的内容
            var result = [];
            var res = [];
            data.forEach(function (v) {
                if (isNotBlank(zxdjProcessId)){
                    var processList = v.processList;
                    var processIds = zxdjProcessId.split(",");
                    for (var i = 0; i < processList.length; i++) {
                        for (var j = 0; j < processIds.length; j++) {
                            if (processList[i].processDefKey == processIds[j]){
                                res[j] = processList[i];
                            }
                        }
                    }
                }else{
                    if (v.name == "注销登记") {
                        result[0] = v;
                    }
                }

            });
            // 渲染下拉框
            $('#process').append(new Option("请输入", ""));
            if (isNotBlank(zxdjProcessId)){
                $.each(res, function (index, item) {
                    $('#process').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                });
            }else{
                $.each(result[0].processList, function (index, item) {
                    $('#process').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                });
            }
            layui.form.render("select");
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

/**
 * 还原权利方法
 */
window.hyQl = function (hyCheckQlxx) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var $ = layui.jquery,
            layer = layui.layer;
        // 还原原因清空
        $('#hfyy').val("");
        layer.open({
            title: '还原权利',
            type: 1,
            area: ['960px', '300px'],
            btn: ['还原', '取消'],
            content: $('#hfql-reason'),
            yes: function (index, layero) {
                hyCheckQlxx(index);
            },
            btn2: function (index, layero) {
                //取消 的回调
            },
            cancel: function () {
                //右上角关闭回调
                //return false 开启该代码可禁止点击该按钮关闭
            }
        });
    });

};