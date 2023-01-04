/**
 * 关闭弹出页面
 */
window.closeWin = function(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

var bdcdyfwlx = $("#bdcdyfwlx").val();
var fwDcbIndex = $("#fwDcbIndex").val();

layHide($(".wdygj,.pjgj,.dtgj"));

layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    //form初始化
    form.render();

    // 动态构建 添加 单元号 和户数
    $("#dtgjAdd").click(function () {
        generateDtgjList([{}], $(".dtgj").length);
    });

    function initHsdtgj(){
        var itemlist = [];
        $(".dtgj").each(function (i) {
            var dyh = $(this).find(".hsdtgj-dyh").val();
            var hs = $(this).find(".hsdtgj-hs").val();
            if(dyh && hs){
                var item = {};
                item.hs = hs;
                item.dyh = dyh;
                itemlist.push(item);
            }
        });
        return JSON.stringify(itemlist);
    }


    //提交表单
    form.on("submit(buildBtn)", function (data) {
        var postData = data.field;
        if(postData.gjfs == '3'){
            postData.dtgjJson = initHsdtgj();
        }
        // loading加载
        addModel();
        $.ajax({
            url: "../ljz/build",
            dataType: "json",
            data: postData,
            success: function (data) {
                removeModal();
                layer.closeAll();
                if (parent.$("#buildLpb") && parent.$("#buildLpb").length > 0) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index)
                    parent.layer.msg("提交成功")
                } else {
                    layer.msg("提交成功")
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
        return false;
    });

    form.on('select(gjfs)', function(data){
        layHide($(".wdygj,.pjgj,.dtgj"));

        // 请选择 隐藏全部
        if(data.value == '0'){
        }
        // 无单元按户构建
        if(data.value == '1'){
            layShow($(".wdygj"));
        }
        // 按单元户数平均构建
        if(data.value == '2'){
            layShow($(".pjgj"));
        }
        // 按单元户数动态构建
        if(data.value == '3'){
            layShow($(".dtgj"));
        }
    });

    function generateDtgjList(dtgjList, listStartWhith) {
        layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
            var form = layui.form;
            var $ = layui.jquery;
            var laytpl = layui.laytpl;
            var element = layui.element;
            if (dtgjList == null) {
                dtgjList = [{}];
            }
            if (listStartWhith == null) listStartWhith = 0;
            var json = {
                start: listStartWhith,
                data: dtgjList
            };
            //获取模板
            var tpl = $("#dtgjTpl").html();
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                $("#dtgjDiv").append(html);
            });
            form.render();
            element.render();
            listStartWhith = listStartWhith + dtgjList.length;
            if (dtgjList.length == 0) {
                listStartWhith++;
            }
            return listStartWhith;
        })

    }
});
function layHide(elem) {
    elem.addClass("layui-hide");
}
function layShow(elem) {
    elem.removeClass("layui-hide");
}