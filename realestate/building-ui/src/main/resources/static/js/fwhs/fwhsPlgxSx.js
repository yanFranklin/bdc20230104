/**
 * 关闭弹出页面
 */
window.closeWin = function(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

var checkedElem = [];
Array.prototype.remove = function (index) {
    if (index > -1) {
        this.splice(index, 1);
    }
};
var updateNullOnly = false;
var replaceFlag = false;
var zdList = {}
$.ajax({
    url: "../zd/mul",
    dataType: "json",
    data: {
        zdDoNames: "SZdFwytDO,SZdFwhxDO,SZdDldmDO,SZdHxjgDO," +
            "SZdFwlxDO,SZdSyfttdmjjsDO,SZdJczxcdDO,SZdFwxzDO,SZdTdsyqlxDO,SZdBoolDO"
    },
    async: false,
    success: function (data) {
        zdList = $.extend({}, data)
    }
});

layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    var layerIndex = parent.layer.getFrameIndex(window.name);
    form.render();

    //处理列表选择
    var tpl = $("#DmMcTpl").html();
    $.each(zdList, function (key, value) {
        laytpl(tpl).render(value, function (html) {
            $("." + key).append(html);
        });
    })
    //form初始化
    form.render("select");

    // 字段前的 checkbox 监听
    form.on('checkbox(checkbox)', function(data){
        if(data.elem.checked){
            checkedElem.push(data.elem);
        }else{
            var index = $.inArray(data.elem, checkedElem);
            checkedElem.remove(index);
        }
    });

    // 替换属性 checkbox 监听
    form.on('checkbox(replace)',function (data) {
        if(data.elem.checked){
            replaceFlag = true;
        }else{
            replaceFlag = false;
        }
    });

    // 全选checkbox 监听
    form.on('checkbox(allChoose)',function (data) {
        checkedElem = [];
        if(data.elem.checked){
            $.each($("input[lay-filter='checkbox']"),function(i){
                $(this).next().addClass("layui-form-checked");
                checkedElem.push($(this));
            });
        }else{
            $.each($("input[lay-filter='checkbox']"),function(i){
                $(this).next().removeClass("layui-form-checked");
            });
        }
    })


    // 替换的 checkbox 监听
    form.on('checkbox(checkbox)', function(data){
        if(data.elem.checked){
            checkedElem.push(data.elem);
        }else{
            var index = $.inArray(data.elem, checkedElem);
            checkedElem.remove(index);
        }
    });

    // 只刷新控制 checkbox 监听
    form.on('checkbox(updateNullOnly)', function(data){
        if(data.elem.checked){
            updateNullOnly = true;
        }else{
            updateNullOnly = false;
        }
    });

    // 提交按钮
    form.on('submit(sxSubmit)', function (data) {
        var postData = {};
        postData.fwHsIndexList = fwhsIndexList;
        postData.updateNullOnly = updateNullOnly;
        // 属性更新
        if(checkedElem.length > 0){
            var itemList = [];
            for(var i = 0 ; i < checkedElem.length ; i++){
                var name = $(checkedElem[i]).attr("columId");
                var value = $("#"+name).val();
                if(!value){
                    value = "null";
                }
                var item = name + "_" + value;
                itemList.push(item);
            }
            postData.updateParamList = itemList;
        }

        // 替换值
        if(replaceFlag){
            postData.replace = true;
            postData.replaceColumn = $("#replaceColumn").val();
            postData.replaceThz = $("#replaceThz").val();

            if(!postData.replaceColumn || !postData.replaceThz){
                layer.msg("请输入替换参数");
                return false;
            }
            var mbz = $("#replaceMbz").val();
            if(!mbz){
                mbz = "";
            }
            postData.replaceMbz = mbz;
        }
        if(fwDcbIndex){
            postData.fwDcbIndex = fwDcbIndex;
        }
        if(postData.hasOwnProperty("updateParamList") || postData.hasOwnProperty("replaceColumn") ){
            addModel();
            $.ajax({
                url: "../fwhs/batchupdate",
                dataType: "json",
                traditional: true,
                data: postData,
                success: function (data) {
                    removeModal();
                    if(data.success){
                        parent.plgxCallBack(layerIndex);
                    }else{
                        layer.msg(data.msg);
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        }else{
            layer.msg("请设置更新属性");
        }
    });

});