function initLjzJbxx() {
    var ljzJbxxUrl = '';
    if (resourceData.links) {
        for (var i = 0; i < resourceData.links.length; i++) {
            var link = resourceData.links[i];
            if (link.rel == "ljzjbxx") {
                ljzJbxxUrl = link.href;
            }
        }
    }
    if(ljzJbxxUrl){
        showLjzTab();
        $("#ljzTab").load(ljzJbxxUrl, {}, function () {
            layui.use(['form', 'laydate', 'laytpl'], function () {
                var form = layui.form;
                var laytpl = layui.laytpl;
                var laydate = layui.laydate;
                form.render(null, 'ljzTabForm');
            });
        });
    }else{
        hideLjzTab();
    }
}

$("#ljzTabLi").click(function(){
    var fwDcbIndex = resourceData.info.fwDcbIndex.value;
    $.ajax({
        url: "../ljz/infoljz",
        dataType: "json",
        data: {
            fwDcbIndex: fwDcbIndex
        },
        success: function (data) {
            //处理查询出来的数据
            layui.use(['form', 'laydate','laytpl'], function () {
                var form = layui.form;

                if(data.bdcdyh){
                    data.bdcdyh = splitBdcdyh(data.bdcdyh);
                }
                var laytpl = layui.laytpl;
                var tpl = $("#DmMcTpl").html();
                $.each(zdList, function (key, value) {
                    laytpl(tpl).render(value, function (html) {
                        $("." + key).append(html);
                    });
                });
                form.val("ljzTabForm", data);
                $("select").attr("disabled",true);
                form.render(null, 'ljzTabForm');
                $("input").attr("readonly",true);
            });
        },
        error: function (xhr, status, error) {
        }
    });
});

function showLjzTab(){
    $("#ljzTab").removeClass("layui-hide");
    $("#ljzTabLi").removeClass("layui-hide");
}

function hideLjzTab(){
    $("#ljzTab").addClass("layui-hide");
    $("#ljzTabLi").addClass("layui-hide");
}