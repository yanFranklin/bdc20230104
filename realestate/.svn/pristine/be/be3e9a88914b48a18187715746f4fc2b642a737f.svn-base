$("#zdt").click(function () {
    var imgElementSrc = $("#img").attr("src");
    if (!imgElementSrc) {
        addModel();
        // 查询 户室图
        $.ajax({
            url: "../djdcb/zdt",
            dataType: "json",
            data: {
                bdcdyh: bdcdyh,
                qjgldm:qjgldm
            },
            success: function (data) {
                if (data.base64) {
                    renderTpl("uploadTpl","zdtImg",{base64: "data:image/jpg;base64," + data.base64});
                } else if(data.readTuxknr){
                    renderTux();
                }else{
                    renderTpl("uploadTpl","zdtImg",{});
                }
                removeModal();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr, this)
            }
        });
    }
});

function renderTpl(tplId,imgId,json) {
    layui.use('laytpl', function () {
        //获取模板
        var tpl = $("#"+tplId).html();
        var laytpl = layui.laytpl;
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            $("#"+imgId).html(html);
        });
    })
}

function renderTux() {
    // 查询 户室图
    $.ajax({
        url: "../tuxknrzdt/listbybdcdyh",
        dataType: "json",
        data: {
            bdcdyh: bdcdyh
        },
        success: function (data) {
            if(data && data.length > 0){
                var json = {data:data};
                layui.use('laytpl', function () {
                    //获取模板
                    var tpl = $("#tuxknrListTpl").html();
                    var laytpl = layui.laytpl;
                    //渲染数据
                    laytpl(tpl).render(json, function (html) {
                        $("#tuxknrList").html(html);
                        $("#zdtdiv").hide();
                    });
                })
            }else{
                renderTpl("uploadTpl","zdtImg",{});
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr, this)
        }
    });
}

$(function () {
    var height = $("#djdcbContent").height();
    $("#zdtdiv").css("max-height",height - 200);
});

function downTux(url) {
    // TODO 下载URL
    window.location.href = url;
}

$(window).resize(function (){
    var height = $("#djdcbContent").height();
    $("#zdtdiv").css("max-height",height - 200);
});