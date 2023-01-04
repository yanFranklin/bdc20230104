/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 工作流接口编辑页面
 * @date : 2022/4/7 16:03
 */
var form, verifyMsg, formSelects;
var pzid = getQueryString("pzid");
var isSubmit = true;
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
var ywbmzd = [];
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response', formSelects: 'lib/form-select/formSelects-v4'});
layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'upload', 'formSelects'], function () {
    form = layui.form;
    var layer = layui.layer;
    formSelects = layui.formSelects;
    formSelects.config('zzlx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    form.verify({
        required: function (value, item) {
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "必填项不能为空";
            }
        }
    });
    addModel();
    setTimeout(function () {
        try {
            $.when(renderLcmc(), renderSelect(), generateCsjpz()).done(function () {
                removeModal();
            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e, "加载失败");
            return
        }
    }, 10);

    //监听是否同步，如果是异步，默认是忽略异常的
    form.on('select(gzldyid)', function (data) {
        //根据流程名称查询业务编码
        var lcmcSelectObj = document.getElementById("gzldyid");
        var index = lcmcSelectObj.selectedIndex;
        var lcmc = lcmcSelectObj.options[index].text;
        for (var i = 0; i < ywbmzd.length; i++) {
            if (ywbmzd[i].MC === lcmc) {
                $("#ywbm").val(ywbmzd[i].DM);
                break;
            }
        }
    })

    /**
     * 表单数据提交
     */
    form.on('submit(saveCsjywpz)', function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            addModel();
            saveCsjywpz(data.field);
            return false;
        }
        // 禁止提交后刷新
        return false;
    });
});

/**
 * @param null
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 组织工作流接口内容
 * @date : 2022/4/7 16:05
 */
function generateCsjpz() {
    if (isNotBlank(pzid)) {
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/csjpz/" + pzid,
            type: 'GET',
            success: function (data) {
                form.val("csjpzform", data);
                formSelects.value('zzlx', data.zzlx.split(","));
                form.render();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        })
    }
}

/**
 * @param jkxx
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 保存接口信息
 * @date : 2022/4/8 9:54
 */
function saveCsjywpz(jkxx) {
    var lcmcSelectObj = document.getElementById("gzldyid");
    var index = lcmcSelectObj.selectedIndex;
    jkxx.lcmc = lcmcSelectObj.options[index].text;
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/csjpz",
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(jkxx),
        success: function (data) {
            //保存完毕后关闭当前页并刷新父页面台账
            removeModal();
            var index = parent.layer.getFrameIndex(window.name);
            window.parent.layui.table.reload('pageTable', {page: {curr: 1}});
            parent.layer.close(index);
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    })
}


function renderLcmc() {
    $.ajax({
        url: "/realestate-inquiry-ui/bdcZhGz/process/definitions",
        type: 'GET',
        async: false,
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#gzldyid').append('<option value="' + item.processDefKey + '">' + item.name + '</option>');
                });
                form.render('select');
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    })
}

function renderSelect() {
    //获取字典值
    $.ajax({
        url: BASE_URL + "/lcpz/zdxx",
        type: "POST",
        data: 'csjzzlx,csjywbm,csjzzmc',
        contentType: 'application/json',
        async: false,
        dataType: "json",
        success: function (data) {
            if (data.csjywbm) {
                ywbmzd = data.csjywbm;
            }
            if (data.csjzzlx) {
                formSelects.data('zzlx', 'local', {arr: data.csjzzlx});
            }
            if (data.csjzzmc) {
                $.each(data.csjzzmc, function (index, item) {
                    if (item.ZZLY === "1") {
                        $('#bszzmc').append('<option value="' + item.MC + '">' + item.MC + '</option>');
                    } else {
                        $('#bzzzmc').append('<option value="' + item.MC + '">' + item.MC + '</option>');
                    }
                });
            }
            form.render('select');
        },
        error: function () {

        }
    });
}

