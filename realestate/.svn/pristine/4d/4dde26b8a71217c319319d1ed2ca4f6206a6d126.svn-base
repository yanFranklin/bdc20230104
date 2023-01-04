/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 添加、编辑 打印数据源配置
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
    if(action=='edit'){
        setDysjData($.getUrlParam('id'));
    }
    /**
     * 权力其他状况验证结果
     */
    var qlqtzkResult = false;

    form.on('submit(submitBtn)', function (data) {
        if (!qlqtzkResult) {
            layer.alert("数据源验证不通过！", {title: '提示'});
            return false;
        }
        var id = $('#id').val();
        $.ajax({
            url: BASE_URL + "/dysjpz/dysj",
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
                delAjaxErrorMsg(e,'');
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
        cssj_required:function (value, item){
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
                    var cssjmc=new Array();
                    var cs=$('#cs').val();
                    var csArry=cs.split(',');
                    var cssjArray=$('#checkValue').val().split(';');
                    cssjArray.forEach(function (v) {
                        cssjmc.push(v.split('=')[0]);
                    });
                    for(var i=0;i<csArry.length;i++){
                        if(cssjmc.indexOf(csArry[i])==-1){
                            return '测试数据参数不全！';
                        }
                    }
                }});

            if (value != '' && value != null) {
                var datasource=$('input:radio[name="datasource"]:checked').val();
                qlqtzkResult = check(value,datasource);
                if (qlqtzkResult) {
                    $('#dysjyOk').removeAttr("hidden");
                    $('#dysjyClose').attr("hidden", true);
                } else {
                    $('#dysjyOk').attr("hidden", true);
                    $('#dysjyClose').removeAttr("hidden");
                    // return '请检查打印数据源配置！';
                }
            }
        },
        dyzd_required: function (value) {
            if (value == '' || value == null) {
                return '必填项不能为空！';
            }
            try {
                $.parseXML(value);
                $('#dyzdOk').removeAttr("hidden");
                $('#dyzdClose').attr("hidden", true);
            } catch (e) {
                $('#dyzdOk').attr("hidden", true);
                $('#dyzdClose').removeAttr("hidden");
                return "打印字段为xml格式！";
            }
        },
        cs_required:function (value) {

        }
    });
    /**
     * 编辑时填充数据
     * @param data
     */
     function setDysjData(id) {
        $.ajax({
            url: BASE_URL + "/dysjpz/dysj",
            type: "GET",
            data: {id:id},
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                data.dysjy=vkbeautify.sql(data.dysjy);
                data.dyzd=vkbeautify.xml(data.dyzd);
                form.val('dysjForm', data);

            },
            error: function (e) {
                delAjaxErrorMsg(e, '');
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
                delAjaxErrorMsg(e, '请切换数据源！');
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
        var index = parent.parent.layer.getFrameIndex(window.name);
        parent.parent.layer.close(index);
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
        qlqtzkResult = false;
        $('#' + id1).attr('hidden', 'true');
        $('#' + id2).attr('hidden', 'true');
    }
});