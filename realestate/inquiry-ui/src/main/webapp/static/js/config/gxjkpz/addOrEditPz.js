/**
 * Created by ysy on 2020/3/9.
 * 新增配置js
 */
var action = $.getUrlParam("action");
var id = $.getUrlParam("id");
layui.use(['form','layer','table','laytpl'], function() {
    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer,
        laytpl = layui.laytpl;


    //点击提交时不为空的全部标红
    var isSubmit = true;
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '' || value == null || value == undefined) {//判断是否为空
                $(item).addClass('layui-form-danger');
                isSubmit = false;
            }
        }
    });

    //提交保存数据
    form.on('submit(submitBtn)', function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            addModel();
            var saveData =data.field;
            if(isNullOrEmpty(saveData.zt)){
                saveData.zt=1;
            }
            $.ajax({
                url: getContextPath() +"/rest/v1.0/gxjkpz/save",
                type: "PATCH",
                data: JSON.stringify(saveData),
                contentType: 'application/json',
                success: function (result) {
                    saveSuccessMsg();
                    removeModal();
                    if(action ==="add"){
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                },
                 error: function (xhr, status, error) {
                     delAjaxErrorMsg(xhr);
                     removeModal();
                 }
            });
            // 禁止提交后刷新
            return false;
        }
    });
    
    $('.bdc-form-div').on('focus','.layui-input',function () {
        if($(this).hasClass('layui-form-danger')){
            $(this).removeClass('layui-form-danger');
        }
        if($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')){
            $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
        }
    });
    $('.bdc-form-div').on('click','.xm-input',function () {
        if($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')){
            $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
        }
    });

    loadPz();
    function loadPz(){
        if(isNotBlank(id)){
            addModel();
            $.ajax({
                type: 'GET',
                url: getContextPath() +"/rest/v1.0/gxjkpz",
                contentType: 'application/json',
                dataType: "json",
                data: {id : id},
                success: function (data) {
                    removeModal();
                    form.val("addPzForm",data);
                },
                error: function(err){
                    removeModal();
                    delAjaxErrorMsg(err);
                }
            });
        }
    }
});

