/**
 * 关闭弹出页面
 */
window.closeWin = function () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

var fwDcbIndex = $("#fwDcbIndex").val();
var zdList = {};
var qjgldm = $("#qjgldm").val();
//获取字典
$.ajax({
    url: "../zd/mul",
    dataType: "json",
    data: {
        zdDoNames: "SZdBdcdyFwlxDO"
    },
    async: false,
    success: function (data) {
        zdList = $.extend({}, data)
    }
});

layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var element = layui.element;
    //处理列表选择
    if (zdList.SZdBdcdyFwlxDO) {
        var tpl = $("#SZdBdcdyFwlxDOTpl").html();
        laytpl(tpl).render(zdList.SZdBdcdyFwlxDO, function (html) {
            $("#yfwlx").append(html);
        })
        var tpl1 = $("#SZdBdcdyFwlxDOTpl1").html();
        laytpl(tpl1).render(zdList.SZdBdcdyFwlxDO, function (html) {
            $("#fwlx").append(html);
        });
    }

    laydate.render({
        elem: '#qsrq'
    });
    laydate.render({
        elem: '#zzrq'
    });

    //form验证
    form.render();
    //提交表单
    form.on("submit(updateFwlx)", function (data) {
        var postData = data.field;
        postData.fwDcbIndex = fwDcbIndex;
        postData.qjgldm =qjgldm;
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        var check = document.getElementById("jlbgjl").checked;
        postData.check = check;
        if (fwDcbIndex) {
            // 原类型为项目类型
            if (postData.yfwlx == "1") {
                // 如果原类型为多幢 则查询项目下是否 含有其他 逻辑幢
                var hasOtherLjz = checkXmHasOtherLjz(postData);
                // 如果含有其他逻辑幢 则提示 是否 全部变更
                if(hasOtherLjz){
                    var zhLx = postData.fwlx == '2'?"独幢":"户";
                    layer.confirm('是否将该项目中所有的房屋都转换为"' + zhLx + '"?', {
                        btn: ['是', '否']
                    }, function(index, layero){
                        //是
                        postData.changeAllXmLjz = true;
                        executeBg(postData);
                    }, function(index){
                        //否
                        executeBg(postData);
                    });
                }else{
                    executeBg(postData);
                }
            }else if (postData.fwlx != "1") {
                executeBg(postData);
            }  else if (postData.fwlx == "1") {
                toView(getIP() + '/xmxx/pickxmxx?fwDcbIndex=' + postData.fwDcbIndex + '&fwlx=' + postData.fwlx
                    + '&yfwlx=' + postData.yfwlx + '&check=' + postData.check + '&lszd='+ postData.lszd,{tabname:"挂接项目"})
            } else {
                layer.alert("变更后房屋类型为空，无法修改")
            }
        } else {
            layer.alert("逻辑幢主键为空，无法修改")
        }
        return false;
    })
});

function executeBg(postData){
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var check = document.getElementById("jlbgjl").checked;
    postData.check = check;
    // loading加载
    addModel();
    $.ajax({
        url: "../bdcdyfwlxbg/fwlxbg",
        dataType: "json",
        data: postData,
        success: function (data) {
            removeModal();
            if (parent.layer) {
                parent.layer.close(index);
                parent.layer.alert("修改成功");
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });
}

function checkXmHasOtherLjz(postData){
    var hasOtherLjz = false;
    if(postData.fwXmxxIndex){
        $.ajax({
            url: "../ljz/listbypage",
            dataType: "json",
            async: false,
            data: {fwXmxxIndex : postData.fwXmxxIndex,qjgldm:postData.qjgldm},
            success: function (data) {
                if(data && data.content.length > 1){
                    hasOtherLjz = true;
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }
    return hasOtherLjz;
}
