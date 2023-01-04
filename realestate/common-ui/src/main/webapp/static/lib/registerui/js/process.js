/**
 * Created by Administrator on 2019/5/29.
 */
layui.config({
    base: '../../../form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['jquery','formSelects','form'],function () {
    var $ = layui.jquery,
        formSelects = layui.formSelects,
        form = layui.form;
    $(function () {
        $('.bdc-content').css('min-height',$('body').height()-56);
    });
});