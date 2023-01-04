/**
 * 综合查询：标准版高级查询条件页面JS
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

layui.use(['form', 'jquery', 'element', 'formSelects', 'layer', 'laytpl'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    var formSelects = layui.formSelects;

    $(function () {
        //使用js渲染下拉框
        $($('select[disabled="disabled"]')[0]).after('<img src="../../static/lib/bdcui/images/lock.png" alt="">');
        formSelects.data('select01', 'local', {
            arr: [
                {"name": "精确", "value": '0'}
            ]
        });
        // 下拉选择添加删除按钮
        renderSelectClose(form);
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        if (moduleCode){
            setElementAttrByModuleAuthority(moduleCode);
        }
        //监听点击新增权利人
        $('.bdc-add-qlr').on('click',function () {
            var getTpl = qlrTpl.innerHTML;
            laytpl(getTpl).render([], function(html){
                $('.bdc-add-box').append(html);
                if(nameClickTimes != 1){
                    $("select[name='qlrmcmhlx']").val(nameSelect);
                }
                if(zjhClickTimes != 1){
                    $("select[name='qlrzjhmhlx']").val(zjhSelect);
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
            $("select[name='qlrmcmhlx']").val(nameSelect);
            form.render('select');
        });
        //监听权利人证件号选择
        var zjhClickTimes = 1;
        var zjhSelect;
        form.on('select(qlrZjhFilter)', function(data){
            zjhClickTimes++;
            zjhSelect = data.value;
            $("select[name='qlrzjhmhlx']").val(zjhSelect);
            form.render('select');
        });

        //点击删除权利人按钮
        $('#popupTwoRows').on('click','.bdc-delete-qlr',function () {
            $(this).parent().remove();
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

        // 获取配置项
        var zhcxpz;
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/bzb/zhcx/pz",
            type: "GET",
            dataType: 'json',
            async: false,
            success: function (res) {
                if(res){
                    zhcxpz = res;
                }else{
                    failMsg("发现页面未配置正确，请联系管理员解决！");
                }
            },
            error: function () {
                failMsg("发现页面未配置正确，请联系管理员解决！");
            }
        });

        form.on('submit(submitBtn)', function(data) {
            var val = data.field;

            var qlrmc = new Array();
            $("input[name='qlrmc']").each(function(){
                var mc = $(this).val();
                if(mc && '' != mc){
                    qlrmc.push(mc);
                }
            });
            if(qlrmc && qlrmc.length > 0){
                val.qlrmc = qlrmc.join();
            }

            var qlrzjh = new Array();
            $("input[name='qlrzjh']").each(function(){
                var zjh = $(this).val();
                if(zjh && '' != zjh){
                    qlrzjh.push(zjh);
                }
            });
            if(qlrzjh && qlrzjh.length > 0){
                val.qlrzjh = qlrzjh.join();
            }

            // 验证必要查询条件
            var count = 0;
            $(".required").each(function(i){
                var value= $(this).val();
                if(!isNullOrEmpty(value)){
                    count += 1;
                }
            });
            if(0 == count){
                warnMsg(" 请输入必要查询条件，例如权利人名称");
                return false;
            }

            // 如果只输入权利人名称，则通过配置控制证件号是否必填
            if(1 == zhcxpz.cxzjhbt){
                // 获取其它非权利人信息字段
                var otherCount = 0;
                $(".other").each(function(i){
                    var value= $(this).val();
                    if(!isNullOrEmpty(value)){
                        otherCount += 1;
                    }
                });

                // 权利人名称或证件号有值，其它字段无值情况下
                if(count > 0 && otherCount == 0){
                    var isZjhNull = false;
                    $("input[name='qlrzjh']").each(function(){
                        var zjh = $(this).val();
                        if(isNullOrEmpty(zjh)){
                            isZjhNull = true;
                        }
                    });

                    if(isZjhNull){
                        warnMsg(" 请输入权利人证件号");
                        return false;
                    }
                }
            }

            //获取父页面中复选框权利类型的值
            val.qllx = parent.getQllxCheckedVal();
            val.qszt3 = parent.getQsztCheckedVal();

            parent.setSearchData(val, true);
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });

        //身份证读卡器设置
        window.onReadIdCard = function (qlrlb,element) {
            try {
                var cardInfo = readIdCard();
                if (cardInfo.ReadCard()) {
                    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                        var name = cardInfo.Name;
                        var number = cardInfo.ID;
                        var forms = element.parentNode.parentNode.parentNode;
                        if (1 == qlrlb) {
                            $(forms).find("input[name='qlrmc']").val(trimStr(name));
                            $(forms).find("input[name='qlrzjh']").val(trimStr(number));
                        } else {
                            $(forms).find("input[name='ywrmc']").val(trimStr(name));
                            $(forms).find("input[name='ywrzjh']").val(trimStr(number));
                        }
                        var form = layui.form;
                        form.render('select');
                    });
                } else {
                    warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
                }
            } catch (objError) {
                warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
            }
        }

        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#submitBtn").click();
            }
        });

        $('.bdc-frame-close').on('click',function () {
            parent.setSearchData(null, false);
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
    });
});

/**
 * 传递查询参数
 */
window.setSearchData = function (data) {
    // 获取外层查询参数
    layui.form.val('searchform', JSON.parse(JSON.stringify(data)));

    // 设置默认的字段模糊类型
    var mhlx = {"bdcdyhmhlx":0, "bdcqzhmhlx":0, "qlrmcmhlx":0, "qlrzjhmhlx":0, "ywrmcmhlx":0,
        "ywrzjhmhlx":0, "zlmhlx":0, "ycqzhmhlx":0, "zhlshmhlx":0, "fjhmhlx":0, "slbhmhlx":0,
        "fwbhmhlx":0, "zslshmhlx":0, "ybdcdyhmhlx":0, "yxtcqzhmhlx":0,"jtcymcmhlx": 0};
    layui.form.val('searchform', JSON.parse(JSON.stringify(mhlx)));
}