
var consumer = $.getUrlParam("consumer");
var principal = $.getUrlParam("principal");
var id = $.getUrlParam("id");
var editType = $.getUrlParam("editType");
var BASE_URL = getContextPath() +"/rest/v1.0/apiManagement";
layui.use(['form','layer','laytpl'], function(){
    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;

    renderSelect();
    dataEcho();
    form.render('select');
    //点击提交时不为空的全部标红
    var isSubmit = true;
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '' || value == null || value == undefined) {//判断是否为空
                $(item).addClass('layui-form-danger');
                isSubmit = false;
            }
        },
    });

    //提交保存数据
    form.on("submit(save)", function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            var url = getUrl();
            var param = {
                "id": id,
                "consumer": $("#consumer").val(),
                "principal": $("#principal").val(),
            }
            console.info(param);
            console.info(url);
            addModel();
            $.ajax({
                type: 'POST',
                url: url,
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify(param),
                success: function (data) {
                    removeModal();
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">保存成功');
                },
                error: function(err){
                    removeModal();
                    delAjaxErrorMsg(err);
                }
            });
            return false;
        }
    });

});

/**
 * 获取消费方字典项
 * @param dictionary
 */
function getConsumer(dictionary) {
    var consumer = [];
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
                return consumer;
            }
            for (var i = 0; i < gxbmbz.length; i++) {
                var data = {
                    "name": gxbmbz[i].MC,
                    "value": gxbmbz[i].DM
                };
                consumer.push(data);
            }
        },
        error: function (e) {
        }
    });
    return consumer;
}

function renderSelect() {
    // 消费方(共享部门)下拉框
    var consumer = getConsumer("gxbmbz");
    var consumerOption = "<option value=''>" + '请选择' + "</option>";
    if (consumer.length > 0) {
        consumer.forEach((v, i) => {
            consumerOption += "<option value='" + v.value + "'>" + v.name + "</option>";
        });
    }
    $("#consumer").append(consumerOption);
}

function dataEcho() {
    console.log(editType);
    console.log(consumer);
    console.log(principal);
    $("#consumer").each(function() {
        $(this).children("option").each(function() {
            if (this.value == consumer) {
                // 进行回显
                console.log(this);
                $(this).attr("selected","selected");
            }
        });
    })
    $("#principal").val(principal);
}

function getUrl(){
    var url = getContextPath() +"/rest/v1.0/apiManagement";;
    if(editType === "0"){
        url += "/contrastRelation/save";
    }
    if(editType === "1"){
        url += "/contrastRelation/update";
    }
    return url;
}
