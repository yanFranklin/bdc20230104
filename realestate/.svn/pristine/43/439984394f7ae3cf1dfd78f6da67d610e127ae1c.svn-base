layui.use(['form','jquery','element'],function () {
    var $ = layui.jquery,
        form = layui.form;
    
    $(function () {
        var qzyz = layui.sessionData('qzyz');
        if(qzyz && qzyz.data){
            var result = JSON.parse(qzyz.data);

            var html = "<b>1、以下流程未配置强制验证</b><br>";
            if(result.proList && result.proList.length > 0){
               $.each(result.proList, function (key, pro) {
                   html += "&nbsp;&nbsp;&nbsp;&nbsp;" + pro + "<br>";
               })
            }

            html += "<b>2、以下强制验证未关联基础子规则，具体情况如下：</b><br>";
            html += '<div class="layui-container" style="width: 100%;">';
            if(result.qzyzMap){
                for(var key in result.qzyzMap){
                    html += '<div class="layui-row"> <div class="layui-col-md3">' + key + '</div> <div class="layui-col-md9"> ' + result.qzyzMap[key] + ' </div> </div>';
                }
            }
            html += '</div>';

            $("#result").html(html);
        }
    });
});