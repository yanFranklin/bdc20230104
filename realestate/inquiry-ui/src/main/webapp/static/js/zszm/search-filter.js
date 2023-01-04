/**
 * 综合查询--查询结果二次筛选条件页面JS
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var zdList = getMulZdList("fwyt");

layui.use(['form', 'jquery', 'element', 'formSelects', 'layer', 'laytpl'], function () {
    form = layui.form;
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    var formSelects = layui.formSelects;

    $(function () {
        $($('select[disabled="disabled"]')[0]).after('<img src="../../static/lib/bdcui/images/lock.png" alt="">');
        //使用js渲染下拉框
        formSelects.data('select01', 'local', {
            arr: [
                {"name": "精确", "value": '0'},
            ]
        });

        // 渲染字典项
        $.each(zdList.fwyt, function(index,item) {
            $('#ghyt').append('<option value="'+item.DM +'">'+item.MC +'</option>');
        });

        // 下拉选择添加删除按钮
        renderSelectClose(form);

        $('.bdc-frame-close').on('click',function () {
            parent.setSearchFilterData(null, false);
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });

        //3. 输入框删除图标
        if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
            //监听input点击
            $('.layui-form-item').on('click', '.layui-input-inline .layui-icon-close', function () {
                $(this).siblings('.layui-input').val('');
            });

            $('.layui-form-item').on('focus', '.layui-input-inline .layui-input', function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur', '.layui-input-inline .layui-input', function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                }, 150)
            });
        }

        // 设置查询默认模糊值
        var mhlx = {"bdcdyhmhlx2": 3, "bdcqzhmhlx2": 3, "zhmhlx2": 3, "fjhmhlx2": 3, "yxtcqzhmhlx2": 3, "zlmhlx2":3};
        form.val('searchform', mhlx);

        // 获取登记原因
        var djyy = JSON.parse($.cookie("DJYY"));
        if(djyy && djyy.length > 0){
            $.each(djyy, function(index,item) {
                $('#djyy').append('<option value="'+ item +'">'+ item +'</option>');
            });
            form.render('select');
        }

        form.on('submit(submitBtn)', function(data) {
            var count = 0;
            $(".search").each(function(i){
                if(!isNullOrEmpty($(this).val())){
                    count += 1;
                }
            });
            if(0 == count) {
                warnMsg("请输入查询过滤条件！");
                return false;
            }

            var val = data.field;
            parent.setSearchFilterData(val, true);
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });

        //身份证读卡器设置
        window.onReadIdCard = function (qlrlb) {
            try {
                var cardInfo = readIdCard();
                if (cardInfo.ReadCard()) {
                    var name = cardInfo.Name;
                    var number = cardInfo.ID;

                    if (1 == qlrlb) {
                        $('input[name="qlrmc"]').val(name);
                        $('input[name="qlrzjh"]').val(number);
                    } else {
                        $('input[name="ywrmc"]').val(name);
                        $('input[name="ywrzjh"]').val(number);
                    }
                } else {
                    warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
                }
            } catch (objError) {
                warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
            }
        }

    });
});