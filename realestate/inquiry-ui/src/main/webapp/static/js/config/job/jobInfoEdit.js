/**
 * 任务配置修改新增 js
 */
var jobInfoId =  $.getUrlParam('jobInfoId');
var jobGroupId = 1;
var form;
var moduleCode = $.getUrlParam('moduleCode');
var isSubmit = true;
layui.use(['layer', 'table', 'jquery', 'form'], function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var table = layui.table;
    form = layui.form;

    $.ajax({
        url: '/realestate-inquiry-ui/job/group/all',
        type: "GET",
        dataType: "json",
        timeout : 10000,
        async: true,
        success: function(data) {
            if(data && data.length > 0){
                // 渲染字典项
                $.each(data, function(index,item) {
                    $('#jobGroup').append('<option value="'+item.id +'">'+item.title + '</option>');
                });
            }
            $("#jobGroup").val(jobGroupId);
            form.render('select');
            // form.render();

            // 下拉选择添加删除按钮
            renderSelectClose(form);
        }
    });

    $(function () {
        addModel();
        setTimeout(function () {
            try {
                $.when(generateJobInfo()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
                return
            }
        }, 10);

        form.on("submit(saveJobInfo)", function (data) {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                return false;
            } else {
                saveJobInfo();
                return false;
            }
        });


    });
});


function generateJobInfo(){
    if (isNotBlank(jobInfoId)){
        $.ajax({
            url: "/realestate-inquiry-ui/job/info/loadById",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {id: jobInfoId},
            success: function (data) {
                // if (moduleCode){
                //     setElementAttrByModuleAuthority(moduleCode);
                // }

                form.val("searchform", data.content);
                jobGroupId = data.content.jobGroup;
                form.render();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        })
    // }else{
      // layer.warn("id为空，缺少主键！");
    }
}


/**
 * 编辑时填充数据
 * @param data
 */
// var qxdm, lqfs;
// window.setData = function(data){
//     // 这里直接用data赋值无效（原因未知），需要重新转JSON
//     form.value('searchform', JSON.parse(JSON.stringify(data)));
//     // qxdm = data.qxdm;
//
//     // 获取印制号领取方式
//     // $.ajax({
//     //     url: '/realestate-inquiry-ui/rest/v1.0/zsyzh/lqfs',
//     //     type: "GET",
//     //     dataType: "json",
//     //     success: function(text) {
//     //         lqfs = text;
//     //         if(lqfs && data.qxdm){
//     //             for(var key in lqfs){
//     //                 if(key == data.qxdm){
//     //                     if('0' == lqfs[key]){        //按照部门领取
//     //                         $("#lqbm_div").show();
//     //                         $("#lqr_div").hide();
//     //                     }else if('1' == lqfs[key]){  //按照人员领取
//     //                         $("#lqr_div").show();
//     //                         $("#lqbm_div").hide();
//     //                     }else{                       //默认按照区县代码
//     //                         $("#lqr_div").hide();
//     //                         $("#lqbm_div").hide();
//     //                     }
//     //                 }
//     //             }
//     //         }
//     //     }
//     // });
// };

function saveJobInfo(){
    var obj = {};
    $(".search").each(function (i) {
        var name = $(this).attr('name');
        var value = $(this).val();
        if (value) {
            // value = value.replace(/\s*/g, "");
        }
        obj[name] = value;
    });

    var urll = "/realestate-inquiry-ui/job/info/save";

    if (isNotBlank(jobInfoId)){
        obj["id"] = jobInfoId;
        urll = "/realestate-inquiry-ui/job/info/update"
    }

    addModel();
    $.ajax({
        url: urll,
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(obj),
        success: function (data) {
            removeModal();
            if (data.code == "200") {

                layer.msg('操作成功', {
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
