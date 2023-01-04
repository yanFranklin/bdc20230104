var laytpl;
var layer;
var $;
var zmlsessionKey = "zmldah";
var zslsessionKey = "zsldah";
$(function(){
    layui.use(['form', 'jquery', 'laytpl', 'layer'], function() {
        laytpl = layui.laytpl;
        layer = layui.layer;
        $ = layui.jquery;
    });
})
function printZmlDah(){
    setDypzSession(["zmldah"],zmlsessionKey);
    printData("zmldah");

}

function printZslDah(){
    setDypzSession(["zsldah"],zslsessionKey);
    printData("zsldah");
}