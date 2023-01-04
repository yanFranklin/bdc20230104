/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 不动产移交打印单
 */
var onePageShowThirty = function(data){
    //只有一页时表格高度为23,当多页时，最后一页高度为23，其他页高度为29。
    if(data.length % 30 != 0){
        $(".layui-table div").css("height","29px");
    }else{
        var magicNumber = data.length / 30;
        if(magicNumber == 1){
            $(".layui-table div").css("height","23px");
        }else{
            var lastMagicNumber = magicNumber-1;
            console.info(lastMagicNumber);
            $(".layui-table").find(".data").each(function(index,value){
                console.info(index+":"+index/30);
                if(index/30 > lastMagicNumber){
                    $(value).find("div").css("height","23px");
                }else{
                    $(value).find("div").css("height","29px");
                }
            });
        }
    }
}
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form','jquery','laytpl'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        form = layui.form;

    var BASE_URL = '/realestate-portal-ui/rest/v1.0/task/bdcYjd';
    var user=queryCurrentUser();
    var data=JSON.parse(sessionStorage.getItem("yjddydata:"+user.id));
    var getTpl = yjdTableTpl.innerHTML
        ,view = document.getElementById('tableView');
    laytpl(getTpl).render(data, function(html){
        view.innerHTML = html;
    });
    //一页显示30条样式控制
    onePageShowThirty(data);

    $('#dy').click(function () {
        $('#dy').css("display","none");
        updateDyzt(data);
    });
    //关闭处理
    window.onunload = function () {
        window.opener.location.reload()
    };
    function updateDyzt(data){
        // 后台关于回写的逻辑改变，这里的实体里面的id换了属性名称
        for(var i=0;i<data.length;i++){
            data[i].procInsId = data[i].processInstanceId
        }
        $.ajax({
            url: BASE_URL + '/updateDyzt',
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data:JSON.stringify(data),
            success: function (data) {

            },error:function (e) {
                response.fail(e)

            },complete:function () {
                window.print();
            }
        });
    }


});

