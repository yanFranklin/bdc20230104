/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 查询条件所需的日期初始化、复选框初始化及高级查询
 */
layui.use(['form', 'jquery', 'laydate', 'element'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate;
    $(function () {



        //点击高级查询
        $('#seniorSearch').on('click', function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
        });

    });
});