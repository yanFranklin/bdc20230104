var ggid = $.getUrlParam('ggid');
var zdList =getMulZdList("ggqk");
var isSubmit = true;
var form;
layui.use(['form', 'jquery', 'element', 'table', 'laydate','upload'], function () {
    form = layui.form;
    var layer = layui.layer;
    var verifyMsg = "必填项不能为空";
    var laydate = layui.laydate;
    form.verify({
    });
    laydate.render({
        elem: '#ggkssj' ,
        type: 'datetime'
    });

    laydate.render({
        elem: '#ggjssj' ,
        type: 'datetime'
    });

    addModel();
    setTimeout(function () {
        try {
            $.when(generateGgxx()).done(function () {
                removeModal();
            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e,"加载失败");
            return
        }
    }, 10);
    form.on('select(ggnr)', function (data) {

        changeGgnr(data);
    });

    /**
     * 表单数据提交
     */
    form.on('submit(saveGgxx)', function(data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            addModel();
            saveGgxx(data.field);
            return false;
        }
        // 禁止提交后刷新
        return false;
    });
})

/**
 * 渲染公告信息数据
 */
function generateGgxx(){
    if(isNullOrEmpty(ggid)){
        warnMsg("未获取到公告信息ID");
    }
    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/ggxx/" + ggid,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            removeModal();
            // 渲染字典项
            $.each(zdList.ggqk, function (index, item) {
                $('#ggxx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            form.val("searchform", data);
            form.render();
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    })
}

/**
 * 保存数据
 * @param data
 */
function saveGgxx(data){
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/ggxx/save",
        type: "POST",
        data: JSON.stringify(data),
        contentType: 'application/json',
        async: false,
        dataType: "json",
        success: function (data) {
            removeModal();
            if(data){
                successMsg("保存成功，即将刷新页面");
                setTimeout(function () {
                    parent.layui.table.reload('pageTable', {page: {curr: 1}});
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }, 1000);
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

function cancel(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

function changeGgnr(data){
    var selectText = data.elem[data.elem.selectedIndex].text;
    if(selectText == '无法公告。' || selectText == '无需公告。' || selectText == '公告有异议。'){
        $("#ggwh")[0].setAttribute('lay-verify', '');
        $("#ggkssj")[0].setAttribute('lay-verify', '');
    }else{
        $("#ggwh")[0].setAttribute('lay-verify', 'required');
        $("#ggkssj")[0].setAttribute('lay-verify', 'required');

    }
}

