function initZrzJbxx() {
    var zrzJbxxUrl = '';
    if (resourceData.links) {
        for (var i = 0; i < resourceData.links.length; i++) {
            var link = resourceData.links[i];
            if (link.rel == "zrzjbxx") {
                zrzJbxxUrl = link.href;
            }
        }
    }
    if (zrzJbxxUrl) {
        showZrzTab();
        $("#zrzTab").load(zrzJbxxUrl, {}, function () {
            layui.use(['form'], function () {
                var form = layui.form;
                var laytpl = layui.laytpl;
                var tpl = $("#DmMcTpl").html();
                $.each(zdList, function (key, value) {
                    laytpl(tpl).render(value, function (html) {
                        $("." + key).append(html);
                    });
                });
                form.render(null, 'zrzTabForm');
            });
        });
    }else{
        hideZrzTab();
    }
}

$("#zrzTabLi").click(function(){
    var lszd = $("#djh").val();
    var zrzh = $("#zrzh").val();
    if(lszd && zrzh){
        $.ajax({
            url: "../fwk/querybylszdzrzh",
            dataType: "json",
            data: {
                lszd: lszd,
                zrzh: zrzh
            },
            success: function (data) {
                //处理查询出来的数据
                layui.use(['form'], function () {
                    var form = layui.form;
                    form.val("zrzTabForm", data);
                    $("select").attr("disabled",true);
                    form.render(null, 'zrzTabForm');
                    $("input").attr("readonly",true);
                });
            },
            error: function (xhr, status, error) {
            }
        });
    }
});

function showZrzTab(){
    $("#zrzTab").removeClass("layui-hide");
    $("#zrzTabLi").removeClass("layui-hide");
}

function hideZrzTab(){
    $("#zrzTab").addClass("layui-hide");
    $("#zrzTabLi").addClass("layui-hide");
}