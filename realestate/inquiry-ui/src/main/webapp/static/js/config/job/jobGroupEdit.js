/**
 * 执行器配置修改新增 js
 */
var jobGroupId =  $.getUrlParam('jobGroupId');
var form;
var moduleCode = $.getUrlParam('moduleCode');
var isSubmit = true;
layui.use(['layer', 'table', 'jquery', 'form'], function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var table = layui.table;
    form = layui.form;

    $(function () {
        addModel();
        setTimeout(function () {
            try {
                $.when(generateJobGroup()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
                return
            }
        }, 10);

        form.on("submit(saveJobGroup)", function (data) {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                return false;
            } else {
                saveJobGroup();
                return false;
            }
        });


    });
});


function generateJobGroup(){
    if (isNotBlank(jobGroupId)){
        $.ajax({
            url: "/realestate-inquiry-ui/job/group/loadById",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {id: jobGroupId},
            success: function (data) {
                // if (moduleCode){
                //     setElementAttrByModuleAuthority(moduleCode);
                // }
                form.val("searchform", data.content);
                form.render();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        })
    }else{
      // layer.warn("id为空，缺少主键！");
    }
}

function saveJobGroup(){
    var obj = {};
    $(".search").each(function (i) {
        var name = $(this).attr('name');
        var value = $(this).val();
        if (value) {
            value = value.replace(/\s*/g, "");
        }
        obj[name] = value;
    });

    if (isNotBlank(jobGroupId)){
        obj["id"] = jobGroupId;
    }
    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/job/group/save",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(obj),
        success: function (data) {
            removeModal();
            if (data.code == "200") {

                layer.msg('更新成功', {
                    icon: 1,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                }, function(){
                    cancel();
                });
            } else {
                // layer.msg(data.msg);

                layer.msg(data.msg, {
                    icon: 1,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                }, function(){
                    //do something
                    // cancel();
                });
            }


        }, error: function (xhr, status, error) {
            removeModal();
        }
    });
}

function cancel(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}
