/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 添加、编辑 打印数据子表配置
 */

layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response'});

layui.use(['table', 'laytpl', 'laydate', 'layer', 'form','response'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        response = layui.response,
        form = layui.form;
    var BASE_URL = getContextPath() + '/rest/v1.0';
    // 新增或者编辑标识
    var action = $.getUrlParam('action');
    var dylx = $.getUrlParam('dylx');
    var id = $.getUrlParam('id');
    $('#dylx').val(dylx);
    if(action=='edit'){
        setDysjZbData(id);
    }

    /**
     * 打印数据源验证结果
     */
    var dysjyResult = false;
    var dyzbIdResult = false;

    form.on('submit(submitBtn)', function (data) {
        if (!dysjyResult) {
            layer.msg("数据源验证不通过!")
            return false;
        }
        if (dyzbIdResult) {
            layer.msg("子表唯一值已存在!")
            return false;
        }

        var id = $('#id').val();
        $.ajax({
            url: BASE_URL + "/dysjpz/dysjzb",
            type: (id != null && id != '') ? "POST" : "PUT",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                if (data && $.isNumeric(data) && data > 0) {
                    success();
                } else {
                    fail(data);
                }
            },
            error: function (e) {
                response.fail(e,'')
            }
        });
        // 禁止提交后刷新
        return false;
    });
    /**
     * 验证sql
     */
    form.on('submit(verify)', function (data) {
        return false;
    });

    form.verify({
        required: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value == '' || value == null) {
                return '必填项不能为空！';
            }
        },
        dysj_required: function (value, item) {
            if (value == '' || value == null) {
                return '必填项不能为空！';
            }
            form.verify({
                cssj_required:function (value, item){
                    if (value == '' || value == null) {
                        return '必填项不能为空！';
                    }
                }});
            if (value != '' && value != null) {
                var datasource=$('input:radio[name="datasource"]:checked').val();
                dysjyResult = check(value, datasource);
                if (dysjyResult) {
                    $('#dysjyOk').removeAttr("hidden");
                    $('#dysjyClose').attr("hidden", true);
                } else {
                    $('#dysjyOk').attr("hidden", true);
                    $('#dysjyClose').removeAttr("hidden");
                    // return '请检查打印数据源配置！';
                }
            }
        }, dyzbid_required: function (value) {
            if (value == '' || value == null) {
                return '必填项不能为空！';
            }
            if (action == 'edit') {
                return;
            }
            dyzbIdResult = checkZbId(dylx, value);
            if (!dyzbIdResult) {
                $('#zbidOk').removeAttr("hidden");
                $('#zbidClose').attr("hidden", true);
            } else {
                $('#zbidOk').attr("hidden", true);
                $('#zbidClose').removeAttr("hidden");
                return '子表唯一值不能重复！';
            }
        }
    });
    /**
     * 编辑时填充数据
     * @param data
     */
    function setDysjZbData(data) {
        $.ajax({
            url: BASE_URL + "/dysjpz/dysjzb",
            type: "GET",
            data: {id:id},
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                data.dyzbsjy=vkbeautify.sql(data.dyzbsjy);
                form.val('dysjForm', data);
            },
            error: function (e) {
                response.fail(e,'');
            }
        });
    };

    window.check = function (sql, type) {
        var result = false;
        var checkUrl=(type=="bdcdj")?"/qlqtzkFj/check":"/dysjpz/check";
        $.ajax({
            url: BASE_URL + checkUrl,
            type: "GET",
            data: {sqls: sql, checkValue: $('#checkValue').val()},
            contentType: 'application/json',
            dataType: "json",
            async: false,
            success: function (data) {
                result = true;
            },
            error: function (e) {
                response.fail(e,'请切换数据源！')
            }
        });
        return result;
    }

    function checkZbId(dylx, zbid) {
        var result = false;
        $.ajax({
            url: BASE_URL + "/dysjpz/dysjzb/checkZbId",
            type: "POST",
            data: JSON.stringify({dylx: dylx, dyzbid: zbid}),
            contentType: 'application/json',
            dataType: 'json',
            async: false,
            success: function (data) {
                result = data;
            },
            error: function (e) {
                response.fail(e,'')
            }
        });
        return result;
    }

    /**
     * 关闭弹出页面
     */
    window.closeWin = function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };

    window.closeParentWindow = function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };

    /**
     * 提交成功提示
     */
    window.success = function () {
        layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
                time: 1000},
            function(){
                closeParentWindow();
            }
        )};

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function () {
        warnMsg("提交失败，请检查填写内容!");
    }
    /**
     * 验证提示
     */
    window.checkFail = function (type) {
        warnMsg("验证" + type + "sql失败，请检查填写内容!");
    }

    window.verify = function (id1, id2) {
        dysjyResult = false;
        $('#' + id1).attr('hidden', 'true');
        $('#' + id2).attr('hidden', 'true');
    }
});