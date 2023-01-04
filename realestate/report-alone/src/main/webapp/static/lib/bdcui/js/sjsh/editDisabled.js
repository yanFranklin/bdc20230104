// 验证弹窗的dom
var verifyDom = '<div class="layui-form-item"><div class="layui-inline bdc-not-null"><label class="layui-form-label"><span class="required-span"><sub>*</sub></span>用户名</label><div class="layui-input-inline"><input type="text" class="layui-input" name="username" id="username" value="" title="username"></div></div><div class="layui-inline bdc-not-null"><label class="layui-form-label"><span class="required-span"><sub>*</sub></span>密码</label><div class="layui-input-inline"><input type="password" class="layui-input" name="password" id="password" value="" title="password"></div></div></div>';
// 修改了的字段
var modifiedData = {};
// 被双击的disabled元素的dom
var disabledDom = "";
// 被双击的disabled元素外层的容器
var $thisOuterDom;
// 编辑弹窗
var editLayerLayero;
// 是否已经打开编辑弹窗
var isLayerOpened = false;
// 是否启用数据审核
var bdsjsh = false;
// 不同tab的不动产单元号是否相同
var isSameBdcdyh = true;
// 流程实例类型
var processInstanceType;
//是否受理页面
var isSlym =true;

layui.use(['layer', 'element', 'form', 'table', 'jquery', 'laydate', 'laytpl'], function () {
    var layer = layui.layer,
        element = layui.element,
        form = layui.form,
        table = layui.table,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl;

    // 用户输入的用户名和密码
    let inputUsername;
    let inputPassword;

    $(function () {
        $.ajax({
            url: getContextPath() + '/rest/v1.0/common/sjsh/pzxx',
            type: 'GET',
            dataType: 'json',
            async: false,
            success: function (data) {
                bdsjsh = data.bdsjsh;
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });

    });

    // 双击input触发判断
    $(document).on('dblclick', 'input', function () {
        processInstanceType = $.getUrlParam('processInstanceType');
        // potal首页点击的哪个列表todo:待办，list:项目列表，done:已办
        // 未启用或者非待办列表打开
        if (!bdsjsh || (!processInstanceType || (processInstanceType != "todo"
            && processInstanceType != "rl"))) {
            return false;
        }

        // 如果双击的input是disabled则触发流程
        if ($(this).attr('disabled') == 'disabled') {
            // 排除写死disabled的字段input.no-modify
            if ($(this).hasClass('no-modify')) {
                ityzl_SHOW_WARN_LAYER("该字段不可编辑！");
                return false;
            }
            //暂不支持受理table填报
            var $sltable = $(this).parents(".bdc-jt-table");
            if ($sltable.length > 0) {
                ityzl_SHOW_WARN_LAYER("该字段不可编辑！");
                return false;
            }
            $thisOuterDom = "";

            if ($(this).parents('.layui-inline').length > 0) {
                // 获取当前双击的元素外层的.layui-inline
                $thisOuterDom = $(this).parents('.layui-inline');
            } else if ($(this).parents('tr').length > 0) {
                isSlym =false;
                var $td = $(this).parents("td");
                var $child = $td.children();
                if ($td.find(".bdc-td-box").length > 0) {
                    $child = $child.children();
                }
                if ($td.length > 0 && $child.length > 0) {
                    $child = $($child[0]);
                    var title = $td.prev().text();
                    if (title) {
                        if ($child[0].tagName === "INPUT") {
                            //权利表单处理
                            $child.removeClass("layui-table-edit").addClass("layui-input");
                        }
                        // 将layer中的value写入html
                        $child.attr('value', $child.val());
                        var thisOuterDom = "<div class=\"layui-inline\"><label class=\"layui-form-label change-label-width\">" + title + "</label><div class=\"layui-input-inline\">" + $child.prop('outerHTML') + " </div></div>";
                        $thisOuterDom = $(thisOuterDom);
                    }
                }
            }

            // 对于不同tab中的相同字段，打开了一个则不能打开其他相同字段
            if ($thisOuterDom.find('select').length > 0) {
                // select
                var thisName = $thisOuterDom.find('select').attr('name');
                if ($(editLayerLayero).find('[name=' + thisName + ']').length <= 0) {
                    $thisOuterDom.addClass('joined');
                    disabledDom = disabledDom + $thisOuterDom.prop('outerHTML');
                } else {
                    ityzl_SHOW_SUCCESS_LAYER('已添加到弹窗！');
                    return false;
                }
            } else {
                // input
                var thisName = $thisOuterDom.find('input').attr('name');
                if ($(editLayerLayero).find('[name=' + thisName + ']').length <= 0) {
                    $thisOuterDom.addClass('joined');
                    disabledDom = disabledDom + $thisOuterDom.prop('outerHTML');
                } else {
                    ityzl_SHOW_SUCCESS_LAYER('已添加到弹窗！');
                    return false;
                }

            }

            // 判断不同tab里面单元信息中的单元号是否相同
            var $tabBdcdyhCollection = $('.layui-tab-content').find('[name="bdcdyh"]');
            var tabBdcdyhValues = [];

            for (let i = 0; i < $tabBdcdyhCollection.length; i++) {
                tabBdcdyhValues[i] = $tabBdcdyhCollection.eq(i).val();
            }

            // 生成了isSameBdcdyh值，写回时如果所有tab的单元号相同,将layer中修改的字段写回到页面的所有tab
            for (let i = 1; i < tabBdcdyhValues.length; i++) {
                if (tabBdcdyhValues[i] !== tabBdcdyhValues[0]) {
                    isSameBdcdyh = false;
                }

            }


            // 根据.layui-inline容器的class判断是否已经在layer中
            if ($thisOuterDom.length > 0 && !$thisOuterDom.hasClass('joined')) {
                $thisOuterDom.addClass('joined');
                disabledDom = disabledDom + $thisOuterDom.prop('outerHTML');
            } else if ($thisOuterDom.length === 0) {
                ityzl_SHOW_WARN_LAYER("不支持当前字段编辑！");
                return false;
            }

            // 如果layer已打开，重新渲染layer中的内容，阻止双击事件，解决样式问题
            if (isLayerOpened) {
                // 判断弹窗窗口化还是最小化，分情况提示信息
                if ($('.layui-layer').css('overflow') == 'visible') {
                    ityzl_SHOW_SUCCESS_LAYER('已添加到弹窗！');
                } else if ($('.layui-layer').css('overflow') == 'hidden') {
                    ityzl_SHOW_SUCCESS_LAYER('已添加到弹窗，打开即可编辑！');
                }

                // 更新弹窗的content
                editLayerLayero.content = '<div class="layui-form-item">' + disabledDom + '</div>';
                $('.thisLayerContent').html(editLayerLayero.content);

                // 阻止layer内双击input事件
                editLayerLayero.on('dblclick', 'input', function () {
                    return false;
                });

                // 解决弹层内div.layui-inline样式问题
                editLayerLayero.find('.layui-form-item .layui-inline').css('width', '310px');
                editLayerLayero.find('.layui-form-item .layui-inline.bdc-two-column').css('width', '620px');

                // 弹层打开后将弹层内字段置为可编辑，将小锁移除
                editLayerLayero.find('input').removeAttr('disabled');
                editLayerLayero.find('img').remove();
                editLayerLayero.find('select').removeAttr('disabled');
                editLayerLayero.find('div.layui-select-disabled').removeClass('layui-select-disabled');
                editLayerLayero.find('input.layui-disabled').removeClass('layui-disabled');

                // 设置弹层内按钮样式和按钮上层容器背景色
                editLayerLayero.find('.layui-layer-btn0').css({'height': '38px', 'lineHeight': '38px'});
                editLayerLayero.find('.layui-layer-btn').css({'background': '#eee'});


                // 依次渲染日期input
                var format = 'yyyy-MM-dd';
                var dateClass ="date";
                if(isSlym){
                    dateClass ="test-item";
                }
                var layeroInput = editLayerLayero.find('.'+dateClass);

                layeroInput.each(function () {
                    this.value = Format(this.value, format);
                });


                lay('.layui-layer .'+dateClass).each(function () {
                    laydate.render({
                        elem: this,
                        type: 'date',
                        trigger: 'click'
                    });
                });
                form.render("", "sjshForm");
                resetSelectDisabledCss();
                return false;
            }

            // 先关闭所有旧弹层
            layer.closeAll();

            // 打开编辑弹层
            var editLayerIndex = layer.open({
                title: '编辑',
                type: 1,
                content: '<div class="layui-form-item">' + disabledDom + '</div>',
                btn: ['确认保存'],
                fixed: true,
                area: ['960px', '575px'],
                btnAlign: 'c',
                skin: 'layui-form bdc-not-full-bg bdc-sjsh-form',
                shadeClose: true,
                shade: 0,
                maxmin: true,
                offset: 'auto',
                resize: true,
                moveOut: true,
                restore: function (layero, index) {
                    isLayerOpened = true;
                    // 对layero移除resize监听
                    $(window).unbind('resize');
                    layero.css({
                        'top': '50%',
                        'left': '50%',
                        'transform': 'translate(-50%, -50%)'
                    });
                    layero.find('.layui-layer-max').hide();
                },
                min: function (layero, index) {
                    layero.css({
                        'bottom': '0',
                        'transform': 'translateY(0%)'
                    });
                    layero.find('.layui-layer-max').show();
                },
                success: function (layero, index) {
                    var format = 'yyyy-MM-dd';
                    var dateClass ="date";
                    if(isSlym){
                        dateClass ="test-item";
                    }
                    var layeroInput = $(layero).find('.'+dateClass);

                    layeroInput.each(function () {
                        this.value = Format(this.value, format);
                    });

                    // 移除弹层内lay-key防止laydate控件失效
                    layero.find('.'+dateClass).removeAttr('lay-key');

                    // 依次渲染日期input
                    lay('.layui-layer .'+dateClass).each(function () {
                        laydate.render({
                            elem: this,
                            type: 'date',
                            trigger: 'click'
                        });
                    });
                    form.render();

                    $(window).unbind('resize');
                    isLayerOpened = true;
                    layero.css('minWidth', '330px');
                    layer.min(index);
                    if ($('.layui-layer').css('overflow') == 'visible') {
                        ityzl_SHOW_SUCCESS_LAYER('已添加到弹窗！');
                    } else if ($('.layui-layer').css('overflow') == 'hidden') {
                        ityzl_SHOW_SUCCESS_LAYER('已添加到弹窗，打开即可编辑！');
                    }

                    editLayerLayero = layero;
                    editLayerLayero.find('.layui-layer-content').addClass('thisLayerContent');

                    // 阻止layer内双击input事件
                    layero.on('dblclick', 'input', function () {
                        return false;
                    });

                    // 解决弹层内div.layui-inline样式问题
                    layero.find('.layui-form-item .layui-inline').css('width', '310px');
                    layero.find('.layui-form-item .layui-inline.bdc-two-column').css('width', '620px');

                    // 弹层打开后将弹层内字段置为可编辑，将小锁移除
                    layero.find('input').removeAttr('disabled');
                    layero.find('img').remove();
                    layero.find('select').removeAttr('disabled');
                    layero.find('div.layui-select-disabled').removeClass('layui-select-disabled');
                    layero.find('input.layui-disabled').removeClass('layui-disabled');

                    layero.find('.layui-layer-btn0').css({'height': '38px', 'lineHeight': '38px'});
                    layero.find('.layui-layer-btn').css({'background': '#eee'});

                    $(".bdc-sjsh-form")[0].setAttribute("lay-filter", "sjshForm");
                    form.render("", "sjshForm");
                    resetSelectDisabledCss();
                },
                yes: function (index, layero) {
                    // 打开验证弹窗
                    var verifyLayerIndex = layer.open({
                        title: '用户授权',
                        type: 1,
                        content: verifyDom,
                        btn: ['确定'],
                        fixed: true,
                        btnAlign: 'c',
                        skin: 'layui-form bdc-not-full-bg',
                        shadeClose: true,
                        shade: 0,
                        success: function (layero, index) {
                            // 解决弹层内div.layui-inline样式问题
                            layero.find('.layui-form-item .layui-inline').css('width', '310px');
                            layero.find('.layui-form-item .layui-inline.bdc-two-column').css('width', '620px');
                        },
                        yes: function (layero, index) {
                            // 获取用户输入的用户名和密码
                            inputUsername = $('#username').val();
                            inputPassword = $('#password').val();

                            // 判空
                            if (inputUsername && inputPassword) {
                                // 找到修改了的字段
                                var editedLayerInputFields = editLayerLayero.find('.layui-form-item').find('input');
                                var editLayerSelectFields = editLayerLayero.find('.layui-form-item').find('select');

                                editedLayerInputFields.each(function (index, element) {
                                    var getCurrentId = $(element).attr('id');
                                    if (editLayerLayero.find('#' + getCurrentId).val() != $(document).find('#' + getCurrentId).val()) {
                                        // 收集修改了的字段
                                        var $getCurrentElem = editLayerLayero.find('#' + getCurrentId);
                                        modifiedData[$getCurrentElem.attr('id')] = $getCurrentElem.attr('value');
                                    }
                                });

                                editLayerSelectFields.each(function (index, element) {
                                    var getCurrentId = $(element).attr('id');
                                    if (editLayerLayero.find('#' + getCurrentId).val() != $(document).find('#' + getCurrentId).val()) {
                                        // 收集修改了的字段
                                        var $getCurrentElem = editLayerLayero.find('#' + getCurrentId);
                                        modifiedData[$getCurrentElem.attr('id')] = $getCurrentElem.attr('value');
                                    }
                                });

                                // 验证保存
                                $.ajax({
                                    url: getContextPath() + '/rest/v1.0/common/sjsh/sjxgsh/log',
                                    type: "POST",
                                    data: JSON.stringify({
                                        "username": inputUsername,
                                        "password": window.btoa(inputPassword),
                                        "data": modifiedData
                                    }),
                                    contentType: 'application/json',
                                    success: function (result) {
                                        if (result.code == 1) {
                                            ityzl_SHOW_SUCCESS_LAYER("验证成功");
                                            layer.close(verifyLayerIndex);

                                            modifiedData = {};

                                            editedLayerInputFields.each(function (index, element) {
                                                var getCurrentId = $(element).attr('id');
                                                if (editLayerLayero.find('#' + getCurrentId).val() != $(document).find('#' + getCurrentId).val()) {
                                                    // 收集修改了的字段
                                                    var $getCurrentElem = editLayerLayero.find('#' + getCurrentId);
                                                    modifiedData[$getCurrentElem.attr('id')] = $getCurrentElem.attr('value');
                                                    // 将修改了的字段写回页面，移除页面上的disabled和锁
                                                    // $(document).find('#'+getCurrentId).removeAttr('disabled');
                                                    // $(document).find('#'+getCurrentId).next('img').remove();
                                                    $(document).find('#' + getCurrentId).val(editLayerLayero.find('#' + getCurrentId).val());
                                                    $(document).find('#' + getCurrentId).attr('value', editLayerLayero.find('#' + getCurrentId).val());
                                                    $(document).find('#' + getCurrentId).attr('title', editLayerLayero.find('#' + getCurrentId).val());
                                                }
                                            });

                                            editLayerSelectFields.each(function (index, element) {
                                                var getCurrentId = $(element).attr('id');
                                                if (editLayerLayero.find('#' + getCurrentId).val() != $(document).find('#' + getCurrentId).val()) {
                                                    // 收集修改了的字段
                                                    var $getCurrentElem = editLayerLayero.find('#' + getCurrentId);
                                                    modifiedData[$getCurrentElem.attr('id')] = $getCurrentElem.attr('value');
                                                    // 将修改了的字段写回页面，移除页面上的disabled和锁
                                                    // $(document).find('#'+getCurrentId).removeAttr('disabled');
                                                    // $(document).find('#'+getCurrentId).next('img').remove();
                                                    $(document).find('#' + getCurrentId).val(editLayerLayero.find('#' + getCurrentId).val());
                                                    $(document).find('#' + getCurrentId).attr('value', editLayerLayero.find('#' + getCurrentId).val());
                                                    $(document).find('#' + getCurrentId).attr('title', editLayerLayero.find('#' + getCurrentId).val());
                                                }
                                            });

                                            // 获取isSameBdcdyh，如果相同则将layer中的值写回所有tab
                                            if (isSameBdcdyh) {
                                                var $inputAndSelectElems = editLayerLayero.find('div.layui-input-inline>*');
                                                var currentElemName;
                                                var currentElemValue;
                                                $inputAndSelectElems.each(function (index, element) {

                                                    currentElemName = $(element).attr('name');
                                                    currentElemValue = $(element).val();
                                                    $('[name=bdcdyxx]').find('[name=' + currentElemName + ']').val(currentElemValue);
                                                    if($('[name=bdcdyxx]').find('[name=' + currentElemName + ']').length >0){
                                                        for(var i=0;i<$('[name=bdcdyxx]').find('[name=' + currentElemName + ']').length;i++) {
                                                            $(element).addClass("xmxx"+(i+1));
                                                        }
                                                    }




                                                });

                                                $('.layui-tab-content').find('[name=' + currentElemName + ']')

                                            }

                                            form.render();
                                            resetSelectDisabledCss();
                                            editLayerLayero.css({
                                                'display': 'none'
                                            });
                                            layer.close(editLayerIndex);

                                            // 触发保存
                                            $('.bdc-sjsh-save').trigger('click');

                                            // 清空layer，每次保存操作对应一个双击流程，流程结束清除layer内元素开始新的流程
                                            $(document).find('.joined').removeClass('joined');
                                            disabledDom = '';
                                            isLayerOpened = false;
                                        } else {
                                            ityzl_SHOW_WARN_LAYER(result.message);
                                        }
                                    },
                                    error: function (xhr, status, error) {
                                        ityzl_SHOW_WARN_LAYER('无法连接');
                                        console.log(error);
                                    }

                                });

                            } else {
                                ityzl_SHOW_WARN_LAYER("请输入用户名和密码");
                            }
                        },
                        cancel: function (index, layero) {
                            layer.close(index);
                        }
                    });
                },
                cancel: function (index, layero) {
                    layero.css({
                        'display': 'none'
                    });
                    layer.closeAll();
                    // 重置
                    disabledDom = '';
                    editLayerLayero = '';
                    isLayerOpened = false;
                    $thisOuterDom = '';
                    $(document).find('.joined').removeClass('joined');
                }
            });

        }
    });

    // 操作成功提示
    function ityzl_SHOW_SUCCESS_LAYER(msg) {
        layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">' + msg);
    }

    // 警告小提示
    function ityzl_SHOW_WARN_LAYER(msg) {
        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
    }

    /**
     * 设置下拉框不可编辑的样式
     */
    function resetSelectDisabledCss() {
        //select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
        $('.layui-select-disabled i').addClass("bdc-hide");
        $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
    }

    function Format(datetime, fmt) {
        if (isNotBlank(datetime)) {
            if (parseInt(datetime) == datetime) {
                if (datetime.length == 10) {
                    datetime = parseInt(datetime) * 1000;
                } else if (datetime.length == 13) {
                    datetime = parseInt(datetime);
                }
            }
            datetime = new Date(datetime.replace(/-/g, "/"));
            var o = {
                "M+": datetime.getMonth() + 1,                 //月份
                "d+": datetime.getDate(),                    //日
                "h+": datetime.getHours(),                   //小时
                "m+": datetime.getMinutes(),                 //分
                "s+": datetime.getSeconds(),                 //秒
                "q+": Math.floor((datetime.getMonth() + 3) / 3), //季度
                "S": datetime.getMilliseconds()             //毫秒
            };
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (datetime.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        }
    }

});