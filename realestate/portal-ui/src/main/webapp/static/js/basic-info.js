/**
 * Created by Administrator on 2019/1/21.
 */
layui.use(['jquery','element','laytpl','form'], function() {
    var $ = layui.jquery,
        element = layui.element,
        form = layui.form,
        response = layui.response,
        laytpl = layui.laytpl;

    $(function () {
        //获取基本信息
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/user/info",
            data: {},
            success: function (data) {
                var getBasicTpl = basicTpl.innerHTML
                    ,basicView = document.getElementById('basicView');
                laytpl(getBasicTpl).render(data, function(html){
                    basicView.innerHTML = html;
                });
            },error: function(e){
                response.fail(e,'')
            }
        });

    });
});