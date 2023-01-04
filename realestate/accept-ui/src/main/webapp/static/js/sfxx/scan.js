
var processInsId = getQueryString("processInsId");
var jfsewmurl = getQueryString("jfsewmurl");
var daxxImageModelUrl = getIP() + "/realestate-accept-ui/static/printmodel/fskp.fr3";
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
layui.use(['tree','jquery','response'], function(){
    var response = layui.response,
        $ = layui.jquery;

    $(function () {
        addModel();

        $.ajax({
            url: getContextPath() + "/sf/fskp",
            type: 'GET',
            dataType: 'json',
            data: {jfsewmurl: jfsewmurl,processInsId:processInsId},
            success: function (data) {
                removeModal();
                if(data){
                    var src = data.storageUrl +"/rest/files/download/" +  data.id;
                    $('#seeImgView').html('<iframe src="' + getContextPath() + "/static/lib/pdf/web/viewer.html?file=" + encodeURIComponent(src) +'"></iframe>');
                }

            }, error: function (e) {
                response.fail(e,'');
                removeModal();
            }
        });

        //打印图片
        // $('#print').click(function(){
        //     var dataUrl = getIP() + "/realestate-accept-ui/sf/fskp/print?id="+storeageId;
        //     print(daxxImageModelUrl, dataUrl, false);
        // });

    });
});