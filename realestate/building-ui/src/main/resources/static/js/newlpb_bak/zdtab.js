function initZdJbxx(){
    var zdJbxxUrl = '';
    if (resourceData.links) {
        for (var i = 0; i < resourceData.links.length; i++) {
            var link = resourceData.links[i];
            if (link.rel == "zdjbxx") {
                zdJbxxUrl = link.href;
            }
        }
    }
    if(zdJbxxUrl){
        showZdTab();
        $("#zdTab").load(zdJbxxUrl,{},function(){
            layui.use(['form', 'laydate','laytpl'], function () {
                var form = layui.form;
                var laytpl = layui.laytpl;
                var laydate = layui.laydate;
                //处理列表选择
                var tpl = $("#DmMcTpl").html();
                $.each(zdList, function (key, value) {
                    laytpl(tpl).render(value, function (html) {
                         $("." + key).append(html);
                    });
                })
                laydate.render({
                    elem: '#qsrq',
                    type: 'datetime'
                });
                laydate.render({
                    elem: '#zzrq',
                    type: 'datetime'
                });
                laydate.render({
                    elem: '#jlrq',
                    type: 'datetime'
                });
                form.render(null, 'zdTabform');
            });
        });
    }else{
        // 隐藏宗地TAB
        hideZdTab();
    }
}

$("#zdTabLi").click(function(){
    var djh = $("#djh").val();
    if(djh){
        $.ajax({
            url: "../zd/queryzddjdcb",
            dataType: "json",
            data: {
                djh: djh
            },
            success: function (data) {
                //处理查询出来的数据
                layui.use(['form'], function () {
                    var form = layui.form;
                    if(data.bdcdyh){
                        data.bdcdyh = splitBdcdyh(data.bdcdyh);
                    }
                    form.val("zdTabform", data);
                    $("select").attr("disabled",true);
                    // form.render();
                    form.render(null, 'zdTabform');
                    $("input").attr("readonly",true);
                });
            }
        });
    }
});

function showZdTab(){
    $("#zdTabLi").removeClass("layui-hide");
    $("#zdTab").removeClass("layui-hide");
}

function hideZdTab(){
    $("#zdTabLi").addClass("layui-hide");
    $("#zdTab").addClass("layui-hide");
}