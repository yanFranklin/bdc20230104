/**
 * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @description 调拨查询
 */
var id = $.getUrlParam('id');
var isSubmit = true;
var form;
layui.use(['form', 'jquery', 'element', 'table', 'laydate','upload'], function () {
    form = layui.form;
    var layer = layui.layer;
    var verifyMsg = "必填项不能为空";
    form.verify({
    });
    addModel();
    setTimeout(function () {
        try {
            $.when(generateZctbxx()).done(function () {
                removeModal();
            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e,"加载失败");
            return
        }
    }, 10);

    /**
     * 表单数据提交
     */
    form.on('submit(saveZctb)', function(data) {

        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            addModel();
            saveZctb(data.field);
            return false;
        }
        // 禁止提交后刷新
        return false;
    });
})

/**
 * 渲染资产调拨数据
 */
function generateZctbxx(){
    if (isNotBlank(id)){
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/tbcx",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {id: id},
            success: function (data) {
                form.val("searchform", data);
                form.render();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        })
    }
}


/**
 * 保存数据
 * @param data
 */
function saveZctb(data){
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/tbcx/save",
        type: "POST",
        data: JSON.stringify(data),
        contentType: 'application/json',
        async: false,
        dataType: "json",
        success: function (data) {
            removeModal();
            if(data){
                successMsg("保存成功！");
            } else {
                alertMsg("提交失败，请检查填写内容!");
            }
        },
        error:function(err){
            removeModal();
            delAjaxErrorMsg(err)
        }
    });
}

/**
 * 打开文件管理器 修改
 */
function clscEdit(){
    if($("#wjzxid").val()){
        viewWjglFj($("#id").val(),"clientId", $("#wjzxid").val(),'查看附件',2,true);
    }else{
        getReturnData("/rest/v1.0/fjcl/folder", {gzlslid: $("#id").val(), wjjmc: $('input[name="wh"]').val()}, 'GET', function (data) {
            if (isNotBlank(data)) {
                // 保存文件中心id至资产调拨信息表中
                saveWjzxid($("#id").val(), data.id);
                var wjglCs = getWjglCs($("#id").val(),"clientId", data.id, 2);
                if(isNullOrEmpty(wjglCs.token) ||isNullOrEmpty(wjglCs.spaceId)){
                    layer.alert("缺失附件参数");
                    return false;
                }
                var url = "/realestate-accept-ui/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(wjglCs));
                var index = parent.layer.open({
                    title: "上传附件",
                    type: 2,
                    area: ['1150px', '600px'],
                    content: url
                });
                parent.layer.full(index);
            }else{
                layer.msg("文件夹生成失败");
            }
        }, function (err) {
            delAjaxErrorMsg(err)
        }, false);
    }
}

// 更新文件中心ID
function saveWjzxid(id, wjzxid) {
    var obj = {
        id: id,
        wjzxid: wjzxid
    }
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/tbcx/save",
        type: "POST",
        data: JSON.stringify(obj),
        contentType: 'application/json',
        async: false,
        success: function (data) {
            $("#wjzxid").val(wjzxid);
        }
    })
}

function cancel(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}



