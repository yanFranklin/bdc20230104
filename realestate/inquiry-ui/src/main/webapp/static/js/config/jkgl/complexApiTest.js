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
    dataEcho(apiData);
    form.render('select');
    var type = getQueryString("type");

    $(function () {
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
                    if(isJSON($('#fjPopup textarea').val())){
                        $('#fjPopup textarea').val('');
                        layer.close(index);
                    }
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

    form.on('submit(test)', function(data){
        var url = data.field.url;
        var requestMethod =  $("#requestMethod").val();
        var requestParam = data.field.requestParam;
        if(typeof url == "undefined" || url == null || url === ""){
            layer.msg("接口地址不能为空!", {icon: 5, time: 3000});
            return;
        }
        if(typeof requestMethod == "undefined" || requestMethod == null || requestMethod === ""){
            layer.msg("请求方式不能为空!", {icon: 5, time: 3000});
            return;
        }
        if(typeof requestParam == "undefined" || requestParam == null || requestParam === ""){
            layer.msg("请求参数不能为空!", {icon: 5, time: 3000});
            return;
        }

        url = url.substring(url.indexOf("/realestate-exchange"))
        var param = {
            apiId:apiId,
            url:url,
            type: type,
            paramJson:requestParam,
            requestMethod: requestMethod,
            beanId: apiData.name,
            methodName: $("#name").val()
        };
        addModel();
        var tokenUr;
        if ($("#tokenUrl").val() && $("#tokenCliendId").val() && $("#tokenClientSecret").val()){
            tokenUrl = $("#tokenUrl").val() + '?grant_type=' + apiData.tokenGrantType + '&client_id=' + $("#tokenCliendId").val() + '&client_secret=' + $("#tokenClientSecret").val();
        }else {
            tokenUrl = apiData.tokenUrl+ '?grant_type=' + apiData.tokenGrantType + '&client_id=' + apiData.tokenClientId + '&client_secret=' + apiData.tokenClientSecret;
        }
        $.ajax({
            url: tokenUrl,
            type: 'POST',
            success: function (data) {
                if (data && data.access_token){
                    $.ajax({
                        url: document.location.protocol + "//" + window.location.host + "/" + url,
                        type: requestMethod,
                        async: false,
                        dataType: "json",
                        data: requestParam,
                        contentType: "application/json",
                        beforeSend: function(request) {
                            request.setRequestHeader("Authorization", 'Bearer '+data.access_token);
                        },
                        success: function (data) {
                            $("#tokenDiv").hide();
                            removeModal();
                            if(data){
                                //将字符串转换成json对象
                                var result = JSON.stringify(data, null, 2);
                                console.log(result);
                                $("#response").val(result);
                            }
                        }, error: function (xhr, status, error) {
                            $("#tokenDiv").hide();
                            removeModal();
                            delAjaxErrorMsg(xhr);
                            $("#response").val(xhr.responseText);
                            return false;
                        }
                    });
                    saveApiInfo(param);
                }
            }, error: function (xhr, status, error) {
                removeModal();
                layer.msg('获取token信息失败，请查看配置');
                //显示获取token参数供修改
                $("#tokenDiv").show();
            }
        });
        return false;
    })
});

function saveApiInfo(param){
    $.ajax({
        url: getContextPath() + "/rest/v1.0/apiManagement/api/result",
        type: 'POST',
        async: false,
        dataType: "json",
        data: JSON.stringify(param),
        contentType: "application/json",
        success: function (data) {
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
            return false;
        }
    });
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
 * 数据回显
 * @param apiData
 */
function dataEcho(apiData){
    $("#name").val(apiData.name);
    $("#requestMethod").each(function() {
        $(this).children("option").each(function() {
            if (this.value == apiData.requestMethod) {
                $(this).attr("selected","selected");
            }
        });
    });
    $("#url").val(apiData.url);
    $("#requestParam").val(apiData.paramJson);
    $("#tokenCliendId").val(apiData.tokenClientId);
    $("#tokenClientSecret").val(apiData.tokenClientSecret);
    $("#tokenUrl").val(apiData.tokenUrl);
}

/**
 * 校验json
 * @param str
 */
function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            if(typeof obj == 'object' && obj ){
                return true;
            }else{
                layer.msg('当前输入参数非json参数');
                return false;
            }

        } catch(e) {
            console.log('error：'+str+'!!!'+e);
            if (e.message.indexOf("position ")>0){
                var lengtn = e.message.substring(e.message.indexOf("position ") + 9);
                layer.msg('当前输入参数非json格式，从这开始：' + str.substring(lengtn));
            }else if(e.message.indexOf("position:")>0){
                var lengtn = e.message.substring(e.message.indexOf("position:") + 9);
                layer.msg('当前输入参数非json格式，从这开始：' + str.substring(lengtn));
            } else {
                layer.msg('当前输入参数非json格式' + e);
            }
            return false;
        }
    }
    console.log('It is not a string!')
}

