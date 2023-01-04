// 文档中心地址
var storageUrl = $("#storageUrl").val();

// 房屋户室主键
var fwHsIndex = $("#fwHsIndex").val();

// 户室图主键
var fwHstIndex = '';

// 查询 户室图
$.ajax({
    url: "../hst/queryfwhst",
    dataType: "json",
    data: {
        fwHsIndex: fwHsIndex
    },
    async: false,
    success: function (data) {
        if(data.fwHstIndex){
            fwHstIndex = data.fwHstIndex;
        }
        if(data.src){
            renderTpl({srcUrl:data.src});
            $("#downHst").removeClass("layui-hide");
            $("#deletHst").removeClass("layui-hide");
        }else{
            renderTpl({});
        }
    },
    error: function (xhr, status, error) {
        delAjaxErrorMsg(xhr,this)
    }
});

// 下载图片
$("#downHst").click(function(){
    var srcURL = $("#img").attr("src");
    if(srcURL && srcURL.indexOf("storage")>0){
        window.open(srcURL + "?attachment=1");
    }else{
        layer.msg("当前图片不支持下载");
    }
});

// 删除按钮
$("#deletHst").click(function(){
    // 删除户室图
    $.ajax({
        url: "../hst/delfwhst",
        dataType: "json",
        data: {
            fwHsIndex: fwHsIndex,
            hslx:"sc"
        },
        async: false,
        success: function (data) {
            if(data.success){
                renderTpl({});
                // 户室图主键清空
                fwHstIndex = "";
                layer.msg("删除成功");
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });

});


function renderTpl(json){
    layui.use('laytpl', function () {
        //获取模板
        var tpl = $("#uploadTpl").html();
        var laytpl = layui.laytpl;
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            $("#hst").html(html);
            $("#downHst").removeClass("layui-hide");
            $("#deletHst").removeClass("layui-hide");
        });
    })
}

layui.use('upload', function(){
    var $ = layui.jquery;
    var upload = layui.upload;
    //拖拽上传
    upload.render({
        elem: '#hst'
        ,url: '../hst/uploadfwhst?fwHsIndex=' + fwHsIndex + "&fwHstIndex=" + fwHstIndex
        ,accept: 'file'
        ,auto: true
        , exts: 'jpg|png|jpeg'
        ,done: function(res){
            if(res.success && res.imgId && res.fwHstIndex){
                fwHstIndex = res.fwHstIndex;
                var imgUrl = storageUrl + "/rest/files/download/" + res.imgId;
                renderTpl({srcUrl:imgUrl});
                layer.msg("上传成功");
            }else{
                layer.msg("上传失败");
            }
        }
    });
});