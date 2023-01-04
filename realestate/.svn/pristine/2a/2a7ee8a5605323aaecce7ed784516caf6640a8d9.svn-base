var formid = $.getUrlParam('formid');
var url = $.getUrlParam('url');
var searchData;
/**
 * Created by Ypp on 2020/2/12.
 * 查询条件配置显示隐藏及顺序
 */
layui.use(['form','laytpl','layer'],function () {
    var laytpl = layui.laytpl,
        $ = layui.jquery,
        form = layui.form;

    $(function () {
        $.ajax({
            type: 'GET',
            url: '/realestate-accept-ui/rest/v1.0/gxhpz/query/form?url=' + encodeURI(url) + '&formid=' + formid,
            async: false,
            success: function (data) {
                searchData = JSON.parse(data);
            }
        });

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
        $('#save').on('click',function(){
            $.ajax({
                type: 'POST',
                url: '/realestate-accept-ui/rest/v1.0/gxhpz/save/form?url=' + encodeURI(url) + '&formid=' + formid,
                data: JSON.stringify(searchData),
                contentType: 'application/json',
                async: false
            });

            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            window.parent.location.reload()
            parent.layer.msg("保存成功");
            parent.layer.close(index);
        });
    });

});
function saveAction() {
    $.ajax({
        type: 'POST',
        url: '/realestate-accept-ui/rest/v1.0/gxhpz/save/form?url=' + encodeURI(url) + '&formid=' + formid,
        data: JSON.stringify(searchData),
        contentType: 'application/json',
        async: false
    });

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    window.parent.location.reload()
    parent.layer.msg("保存成功");
    parent.layer.close(index);
}