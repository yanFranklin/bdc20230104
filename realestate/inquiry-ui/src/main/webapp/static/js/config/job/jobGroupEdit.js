/**
 * 执行器配置修改新增 js
 */
var jobGroupId =  $.getUrlParam('jobGroupId');
var jglbZd = [];
var zjzlZd = [];
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
    if (isNotBlank(id)){
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/jgpz/query",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {jgid: jgid},
            success: function (data) {
                if (moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
                form.val("searchform", data);
                form.render();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        })
    }else{
        var nf = (new Date).getFullYear();
        $("input[name='nf']").val(nf);
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
            var index = parent.layer.getFrameIndex(window.name);
            setTimeout(removeModal(), 100);
            parent.layui.table.reload('pageTable',{page:{curr:1}});
            parent.initUploadInst();

            layer.confirm('保存成功，是否添加机构默认领证人?', {
                btn: ['是', '否']
            }, function(index, layero){
                window.open("/realestate-inquiry-ui/view/jglzr/jglzr.html")
                cancel();
            }, function(index){
                cancel();
            });

        }, error: function (xhr, status, error) {
            removeModal();
        }
    });
}

// function cancel(){
//     var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
//     parent.layer.close(index); //再执行关闭
// }
function cancel() {
    window.parent.cancelEdit();
}
