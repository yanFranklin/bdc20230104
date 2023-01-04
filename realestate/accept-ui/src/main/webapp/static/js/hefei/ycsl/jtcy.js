layui.use(['jquery', 'layer', 'element', 'form', 'laytpl'],function () {
    form = layui.form;
    $ = layui.jquery;
    var laytpl = layui.laytpl;

    var json = {
        jtcyList:parent.jtcyList
    };
    var tpl = jtcyTpl.innerHTML;
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        $("tbody").append(html);
    });
});