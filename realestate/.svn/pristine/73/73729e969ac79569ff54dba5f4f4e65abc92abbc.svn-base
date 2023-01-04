/**
 * Created by Ypp on 2020/2/12.
 * 查询条件配置显示隐藏及顺序
 */
layui.use(['form','laytpl','layer'],function () {
    var laytpl = layui.laytpl,
        $ = layui.jquery,
        form = layui.form;
    $(function () {
        var searchData = window.parent.getSearchListPz();
        var getTpl = contentTpl.innerHTML;
        laytpl(getTpl).render(searchData, function(html){
            $('.layui-form-item').html(html);
            form.render();
        });

        form.on('switch(isShow)', function(data){
            searchData.forEach(function(v){
                if(v.name == $(data.elem).attr('name')){
                    v.isShow = data.elem.checked;
                }
            });
        });

        //点击保存
        $('#saveUpdate').on('click',function(){
            //在此处调接口保存修改到库里面
            console.log(searchData);
            //刷新页面
            parent.location.reload();
        });
    });
});