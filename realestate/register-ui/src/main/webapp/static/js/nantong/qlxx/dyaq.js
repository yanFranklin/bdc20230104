/**
 * @author "<a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2018/12/14
 * @description （南通）抵押权登记信息表单JS
 */

//判断当前页面是否在 权利信息与单元信息组合页面
var isInZhym = $.isFunction(window.parent.qlxxIsYzEnd);

$(document).ready(function(){
    // 获取参数
    var xmid = $.getUrlParam('xmid');
    // 表单ID
    var formStateId = $.getUrlParam('formStateId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');
    $('.qlqtzksdtx').attr('style','display:none');
    $('.fjsdtx').attr('style','display:none');

    /**
     * 字典
     */
    getZd(function(data){
        // 渲染字典项
        $.each(data.djlx, function(index,item) {
            $('#djlx').append('<option value="'+item.DM +'">'+item.MC +'</option>');
        });
        $.each(data.dyfs, function(index,item) {
            $('#dyfs').append('<option value="'+item.DM +'">'+item.MC +'</option>');
        });
        $.each(data.dybdclx, function(index,item) {
            $('#dybdclx').append('<option value="'+item.DM +'">'+item.MC +'</option>');
        });
        // 贷款方式取自common.js常量
        $.each(data.dkfs, function (index, item) {
            $('#dkfs').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.biz, function (index, item) {
            $('#biz').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.dyjelx, function (index, item) {
            $('#dyjelx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.jedw, function (index, item) {
            $('#jedw').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        form.render('select');
    });

    /**
     * 获取权利信息数据
     */
    getQlxx(xmid);

    /**
     * 获取权利人数据
     */
    getQlr(xmid, function(data){
        // 封装权利人列表格式数据
        var qlrxx = {};
        qlrxx.title = "权利人信息";

        if(data){
            // 转换字典
            for(var i = 0, len = data.length; i < len; i++){
                for(var j = 0, zdlen = zdList.zjzl.length; j < zdlen; j++){
                    if(zdList.zjzl[j].DM == data[i].zjzl){
                        data[i].zjzlmc = zdList.zjzl[j].MC;
                    }
                }

                for(var j = 0, zdlen = zdList.qlrlx.length; j < zdlen; j++){
                    if(zdList.qlrlx[j].DM == data[i].qlrlx){
                        data[i].qlrlxmc = zdList.qlrlx[j].MC;
                    }
                }
            }
        }

        qlrxx.list = data;
        var getTpl = qlrTpl.innerHTML;
        laytpl(getTpl).render(qlrxx, function(html) {
            $("#tbody2").before(html)
        });
        form.render();
        resetInputDisabledCss();
    });

    /**
     * 保存更新表单数据
     */
        //点击提交时为空的全部标红
    var isSubmit = true;
    /**
     * 表单校验
     */
    form.verify({
        //要求非负数字，允许为空
        number: [/^\d*(\.\d+)?$/, '请输入数字'],
        //要求正整数，允许为空
        number4:function (value, item) {
            //为非负整数,允许为空
            if (isNotBlank(value) && !/^[1-9]+[0-9]*]*$/.test(value)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
            }
        },
        required: function (value, item) {
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
            }
        }
    });
        //监听提交
    form.on('submit(submitBtn)', function(data){
        // console.log(isSubmit);
        if (!isSubmit) {
            layer.msg('必填项不能为空', {icon: 5});
            isSubmit = true;
            if(isInZhym){
                window.parent.qlxxIsYzEnd('wtg');
            }
            return false;
        } else {
            // 添加遮罩
            addModel();
            if (isNullOrEmpty(data.field.qlid)) {
                warnMsg("当前项目不存在，无法保存！");
                // 移除遮罩
                removeModel();
                if(isInZhym){
                    window.parent.qlxxIsYzEnd('wtg');
                }
                return false;
            }

            if(isInZhym){
                window.parent.qlxxIsYzEnd('tg');
            }

            // 对于数据库存在字典项名称字段的要同步更新值
            if (!(data.field.dyfs)) {
                data.field.dyfsmc = '';
            } else {
                for (var index in zdList.dyfs) {
                    if (parseInt(data.field.dyfs) == zdList.dyfs[index].DM) {
                        data.field.dyfsmc = zdList.dyfs[index].MC;
                    }
                }
            }
            // 保存权利其他状况和附记
            saveQlqtzkAndFj(JSON.stringify(data.field));
            // 同时更新项目的冗余字段
            updateXmxx(JSON.stringify(data.field));

            $.ajax({
                url: BASE_URL + '/qlxx/dyaq',
                type: "PUT",
                data: JSON.stringify(data.field),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    // 保存受理页面的收费信息
                    if (parent && 'function' === typeof (parent.saveSf)) {
                        parent.saveSf();
                    }
                    if (showSaveSuccessMsg) {
                        successMsg("权利信息保存成功！");
                    }
                    showSaveSuccessMsg = true;
                },
                error: function () {
                    warnMsg("提交失败，请重试！");
                }
            });

            removeModel();
            // 禁止提交后刷新
            return false;
        }
    });
    $('td input').on('focus', function () {
        if ($(this).hasClass('layui-form-danger')) {
            $(this).removeClass('layui-form-danger');
        }
        if ($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')) {
            $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
        }
    });

    //设置权限控制样式
    if (readOnly || !isNullOrEmpty(formStateId)) {
        getStateByIdQlxx(readOnly, formStateId, "dyaq");
    }
});
