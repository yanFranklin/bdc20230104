var laytpl;
var layer;
var $;

$(function(){
    layui.use(['form', 'jquery', 'laytpl', 'layer'], function() {
        laytpl = layui.laytpl;
        layer = layui.layer;
        $ = layui.jquery;
        var gzlslid = getQueryString("processInsId")
        getReturnData("/slym/xm/getFwzkfb", {gzlslid: gzlslid}, "GET", function (data) {
            if(data!=null){
              var json={
                    fwb:data
                }
                generateFwb(json)
            }
        }, function (error) {
            delAjaxErrorMsg(error);
        }, false);
    });
})

function generateFwb(json){

    var tpl = fwzkfbXZ.innerHTML,view = document.getElementById('fwb');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

}