/**
 * Created by Administrator on 2019/5/30.
 */
layui.config({
    base: '../../form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['form','jquery','element','formSelects'],function () {
    var $ = layui.jquery,
        form = layui.form,
        formSelects = layui.formSelects;
    
    $(function () {
        $($('select[disabled="disabled"]')[0]).after('<img src="../images/lock.png" alt="">');
        //使用js渲染下拉框
        formSelects.data('select01', 'local', {
            arr: [
                {"name": "首次登记", "value": '100'},
                {"name": "转移登记", "value": '200'},
                {"name": "变更登记", "value": '300'},
                {"name": "注销登记", "value": '400'},
                {"name": "更正登记", "value": '500'},
                {"name": "异议登记", "value": '600'}
            ]
        });

        $('.bdc-frame-close').on('click',function () {
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
    });
});