/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 工作流接口编辑页面
 * @date : 2022/4/7 16:03
 */
var form, verifyMsg;
var jkid = getQueryString("jkid");
var isSubmit = true;
layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'upload'], function () {
    form = layui.form;
    var layer = layui.layer;
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
            $.when(renderYymc(), generateGzljk()).done(function () {
                removeModal();
            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e, "加载失败");
            return
        }
    }, 10);

    //监听是否同步，如果是异步，默认是忽略异常的
    form.on('select(sftb)', function (data) {
        if (data.value === "0") {
            $("#sfhlyc").val('1');
            form.render('select');
        }
    })

    /**
     * 表单数据提交
     */
    form.on('submit(saveGzljk)', function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            addModel();
            saveGzljk(data.field);
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
function generateGzljk() {
    if (isNotBlank(jkid)) {
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/gzl/jk/" + jkid,
            type: 'GET',
            success: function (data) {
                form.val("gzljkform", data);
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
function saveGzljk(jkxx) {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/gzl/jk",
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(jkxx),
        success: function (data) {
            //保存完毕后关闭当前页并刷新父页面台账
            removeModal();
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.$('#search').click();
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    })
}


function renderYymc() {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/gzl/app",
        type: 'GET',
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#yymc').append('<option value="' + item + '">' + item + '</option>');
                });
                form.render('select');
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    })
}
