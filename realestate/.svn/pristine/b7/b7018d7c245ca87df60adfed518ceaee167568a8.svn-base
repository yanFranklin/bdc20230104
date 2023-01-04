layui.use(['jquery','layer','form','table','element'], function() {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        element = layui.element,
        laytpl = layui.laytpl,
        form = layui.form;

    var apiId = getQueryString("apiId");
    if(typeof apiId == "undefined" || apiId == null || apiId === ""){
        return;
    }

    var apiData = getApiInfo(apiId);
    if(typeof apiData == "undefined" || apiData == null || apiId === ""){
        return;
    }

    $(function () {
        //监听 配置sql 弹窗显示 单击
        $('.bdc-form-div').on('click', '.sql', function () {
            var $thisTextarea = $(this).siblings('textarea');
            var fjContent = $thisTextarea.val();
            $("#desc").prop("readonly", true);
            layer.open({
                title: '配置sql',
                type: 1,
                area: ['960px'],
                content: $('#fjPopup')
                , yes: function (index, layero) {
                    //提交 的回调
                    $thisTextarea.val($('#fjPopup textarea').val());
                    $('#fjPopup textarea').val('');
                    layer.close(index);
                }
                , btn2: function (index, layero) {
                    //取消 的回调
                    $('#fjPopup textarea').val('');
                }
                , cancel: function () {
                    //右上角关闭回调
                    $('#fjPopup textarea').val('');
                }
                , success: function () {
                    $('#fjPopup textarea').val(fjContent);
                }
            });
        });

        $('.bdc-form-div').on('click', '.requesParam', function () {
            var $thisTextarea = $(this).siblings('textarea');
            var fjContent = $thisTextarea.val();
            layer.open({
                title: '请求参数',
                type: 1,
                area: ['960px'],
                btn: ['保存'],
                content: $('#fjPopup')
                , yes: function (index, layero) {
                    //提交 的回调
                    $thisTextarea.val($('#fjPopup textarea').val());
                    $('#fjPopup textarea').val('');
                    layer.close(index);
                }
                , btn2: function (index, layero) {
                    //取消 的回调
                    $('#fjPopup textarea').val('');
                }
                , cancel: function () {
                    //右上角关闭回调
                    $('#fjPopup textarea').val('');
                }
                , success: function () {
                    $('#fjPopup textarea').val(fjContent);
                }
            });
        });

        $('.bdc-form-div').on('click', '.return', function () {
            var $thisTextarea = $(this).siblings('textarea');
            var fjContent = $thisTextarea.val();
            $("#desc").prop("readonly", true);
            layer.open({
                title: '响应内容',
                type: 1,
                area: ['960px'],
                content: $('#fjPopup')
                , yes: function (index, layero) {
                    //提交 的回调
                    $thisTextarea.val($('#fjPopup textarea').val());
                    $('#fjPopup textarea').val('');
                    layer.close(index);
                }
                , btn2: function (index, layero) {
                    //取消 的回调
                    $('#fjPopup textarea').val('');
                }
                , cancel: function () {
                    //右上角关闭回调
                    $('#fjPopup textarea').val('');
                }
                , success: function () {
                    $('#fjPopup textarea').val(fjContent);
                }
            });
        });
    })

    dataEcho(apiData);

    form.on('submit(test)', function(data){
        var param = {
            apiId:apiId,
            url:$("#url").val(),
            type: apiData.type,
            paramJson:data.field.requestParam,
            requestMethod: apiData.requestMethod,
            beanId: apiData.name
        };

        // 简单接口测试
        $.ajax({
            url: getContextPath() + "/rest/v1.0/apiManagement/api/result",
            type: 'POST',
            async: false,
            dataType: "json",
            data: JSON.stringify(param),
            contentType: "application/json",
            success: function (data) {
                if(data){
                    //将字符串转换成json对象
                    var result = JSON.stringify(data, null, 2);
                    console.log(result);
                    $("#response").val(result);
                    // document.getElementById("response").innerHTML = result;
                }
            }, error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
                return false;
            }
        });
        return false;
    })
});

/**
 * 数据回显
 * @param apiData
 */
function dataEcho(apiData){
    //加载接口消费方相关配置项
    getConsumer("gxbmbz",apiData.consumer);
    $("select[name=clientId]").val(apiData.clientId);
    $("select[name=logFlag]").val(apiData.logFlag);
    $("select[name=type]").val(apiData.type);
    $("select[name=logType]").val(apiData.logType);
    $("#name").val(apiData.name);
    layui.each($("select[name='requestMethod']", ""), function (i, item) {
        var elem = $(item);
        if(apiData.requestMethod === "POST"){
            elem.next().children().children()[0].defaultValue = "POST";
        }else if(apiData.requestMethod === "GET"){
            elem.next().children().children()[0].defaultValue = "GET";
        }else{
            elem.next().children().children()[0].defaultValue = "";
        }
    });

    layui.each($("select[name='releaseStatus']", ""), function (i, item) {
        var elem = $(item);
        if(apiData.releaseStatus === 0){
            elem.next().children().children()[0].defaultValue = "未发布";
        }else if(apiData.releaseStatus === 1){
            elem.next().children().children()[0].defaultValue = "已发布";
        }else{
            elem.next().children().children()[0].defaultValue = "";
        }
    });
    $("#url").val(apiData.url);
    $("#sql").val(apiData.sql);
    $("#description").val(apiData.description);

    if(typeof apiData.requestBodyParamJson != "undefined" && apiData.requestBodyParamJson != null && apiData.requestBodyParamJson !== ""){
        var param = JSON.stringify(apiData.requestBodyParamJson, null, 2);
        $("#requestParam").val(apiData.requestBodyParamJson);
    }
    // if(typeof apiData.queryParamJson != "undefined" && apiData.queryParamJson != null && apiData.queryParamJson !== ""){
    //     var param = JSON.stringify(apiData.queryParamJson, null, 2);
    //     $("#requestParam").val(apiData.queryParamJson);
    // }
    // if(typeof apiData.restParamJson != "undefined" && apiData.restParamJson != null && apiData.restParamJson !== ""){
    //     var param = JSON.stringify(apiData.restParamJson, null, 2);
    //     $("#requestParam").val(apiData.restParamJson);
    // }
}


/**
 * 获取url后的参数
 * @param name
 * @returns {string|null}
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (null !== r) {
        if (decodeURI(r[2]) !== "null") {
            return decodeURI(r[2]);
        } else {
            return null
        }
    }
    return null;
}

/**
 * 获取api接口详细信息
 * @param apiId
 * @returns {*}
 */
function getApiInfo(apiId) {
    var apiData = {};
    $.ajax({
        url: getContextPath() + "/rest/v1.0/apiManagement/searchById?apiId=" + apiId,
        type: 'GET',
        async: false,
        success: function (data) {
            if(data){
                apiData = data;
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
    return apiData;
}

/**
 * 获取消费方字典项
 * @param dictionary
 */
function getConsumer(dictionary,consumer) {
    $.ajax({
        url: getContextPath() + "/rest/v1.0/apiManagement/zdMul",
        dataType: "json",
        data: {
            zdNames: dictionary
        },
        async: false,
        success: function (data) {
            var gxbmbz = data.gxbmbz;
            if (typeof gxbmbz == "undefined" || gxbmbz == null) {
                $("#jkxff").val(consumer);
                return ;
            }
            for (var i = 0; i < gxbmbz.length; i++) {
                if (consumer && consumer === gxbmbz[i].DM){
                    $("#jkxff").val(gxbmbz[i].MC);
                    return ;
                }
            }
        },
        error: function (e) {
        }
    });
}
