/**
 * author: 前端组
 * date: 2019-01-28
 * version 3.0.0
 * describe: 弹出层
 */
layui.config({
    base: '../../form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['form','jquery','element','layer','formSelects'],function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
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

        //引用内部div渲染
        $('.bdc-show-textarea').on('click',function () {
            //小弹出层
            layer.open({
                title: '规则组合修改',
                type: 1,
                area: ['430px'],
                btn: ['提交', '取消'],
                content: $('#bdc-popup-textarea')
                ,yes: function(index, layero){
                    //提交 的回调

                }
                ,btn2: function(index, layero){
                    //取消 的回调

                }
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,success: function () {

                }
            });
        });

        $('.bdc-show-small').on('click',function () {
            //小弹出层
            layer.open({
                title: '规则组合修改',
                type: 1,
                area: ['430px'],
                btn: ['提交', '取消'],
                content: $('#bdc-popup-small')
                ,yes: function(index, layero){
                    //提交 的回调
                }
                ,btn2: function(index, layero){
                    //取消 的回调

                }
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,success: function () {

                }
            });
        });

        $('.bdc-show-checkbox').on('click',function () {
            //小弹出层
            layer.open({
                title: '提示',
                type: 1,
                area: ['430px'],
                btn: ['更新', '取消'],
                content: $('#bdcPopupCheckbox')
                ,yes: function(index, layero){
                    //提交 的回调
                }
                ,btn2: function(index, layero){
                    //取消 的回调

                }
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,success: function () {

                }
            });
        });

        $('.bdc-show-large').on('click',function () {
            //大弹出层
            layer.open({
                title: '规则组合修改',
                type: 1,
                area: ['960px'],
                btn: ['提交', '取消'],
                content: $('#bdc-popup-large')
                ,yes: function(index, layero){
                    //提交 的回调

                }
                ,btn2: function(index, layero){
                    //取消 的回调

                }
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,success: function () {

                }
            });
        });

        $('.bdc-show-radio').on('click',function () {
            //单选框弹出层
            layer.open({
                title: '单选框',
                type: 1,
                area: ['430px'],
                btn: ['提交', '取消'],
                content: $('#bdc-popup-radio')
                ,yes: function(index, layero){
                    //提交 的回调
                    console.log($('#bdc-popup-radio input[name="zjh"]:checked').val());
                }
                ,btn2: function(index, layero){
                    //取消 的回调

                }
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,success: function () {

                }
            });
        });

        //引用外部iframe渲染
        $('.bdc-show-frame-small').on('click',function () {
            //小弹出层
            layer.open({
                title: '规则组合修改',
                type: 2,
                area: ['430px','360px'],
                content: './small-iframe.html'
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,success: function () {

                }
            });
        });

        $('.bdc-show-frame-large').on('click',function () {
            //小弹出层
            layer.open({
                title: '规则组合修改',
                type: 2,
                area: ['960px','400px'],
                content: './large-iframe.html'
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,success: function () {

                }
            });
        });

        $('.bdc-show-frame-filter').on('click',function () {
            //两列弹出层
            layer.open({
                title: '规则组合修改',
                type: 2,
                area: ['960px','400px'],
                content: './large-iframe-two-rows.html'
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,success: function (layero, index) {
                    var iframe = window['layui-layer-iframe' + index];
                    iframe.setData({"aaa":"aaa"});
                }
            });
        });
        $('.bdc-show-frame-table').on('click',function () {
            //表格弹出层
            layer.open({
                title: '应用详细信息',
                type: 2,
                area: ['960px','560px'],
                btn: ['保存', '取消'],
                content: './large-iframe-table.html'
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,success: function () {

                }
            });
        });
    });
});