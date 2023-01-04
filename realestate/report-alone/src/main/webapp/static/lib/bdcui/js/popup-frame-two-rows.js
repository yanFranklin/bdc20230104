/**
 * Created by Administrator on 2019/5/30.
 */
layui.config({
    base: '../../form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
window.setData = function(data){
    if(data){
        console.log(data);
    }
};
layui.use(['form','jquery','element','formSelects','laytpl'],function () {
    var $ = layui.jquery,
        form = layui.form,
        laytpl = layui.laytpl,
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

        //监听点击新增权利人
        $('.bdc-add-qlr').on('click',function () {
            var getTpl = qlrTpl.innerHTML;
            laytpl(getTpl).render([], function(html){
                $('.bdc-add-box').append(html);
                if(nameClickTimes != 1){
                    $("select[name='qlrName']").val(nameSelect);
                }
                if(zjhClickTimes != 1){
                    $("select[name='qlrZjh']").val(zjhSelect);
                }
            });
            form.render('select');
        });

        //监听权利人名称选择
        var nameClickTimes = 1;
        var nameSelect;
        form.on('select(qlrNameFilter)', function(data){
            nameClickTimes++;
            nameSelect = data.value;
            $("select[name='qlrName']").val(nameSelect);
            form.render('select');
        });
        //监听权利人证件号选择
        var zjhClickTimes = 1;
        var zjhSelect;
        form.on('select(qlrZjhFilter)', function(data){
            zjhClickTimes++;
            zjhSelect = data.value;
            $("select[name='qlrZjh']").val(zjhSelect);
            form.render('select');
        });

        //点击删除权利人按钮
        $('#popupTwoRows').on('click','.bdc-delete-qlr',function () {
            $(this).parent().remove();
        });

        //3. 输入框删除图标
        if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9."))
        {
            //监听input点击
            $('.layui-form-item').on('click','.layui-input-inline .layui-icon-close',function () {
                $(this).siblings('.layui-input').val('');
            });

            $('.layui-form-item').on('focus','.layui-input-inline .layui-input',function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur','.layui-input-inline .layui-input',function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                },150)
            });
        }
    });
});