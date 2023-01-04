/**
 * @author "<a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2018/12/14
 * @description 房地产权登记信息（项目内多幢房屋）表单JS
 */
/**
 * @author "<a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2018/12/13
 * @description 土地所有权登记信息表单JS
 */

$(document).ready(function () {
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
    getZd(function (data) {
        // 渲染字典项
        $.each(data.djlx, function (index, item) {
            $('#djlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.mjdw, function (index, item) {
            $('#mjdw').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.fwxz, function (index, item) {
            $('#fwxz').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.fwlx, function (index, item) {
            $('#fwlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        $.each(data.fwyt, function (index, item) {
            $.each($('.ghyt'), function (index, item) {
                item.append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
        });

        form.render('select');
    });

    /**
     * 获取权利人数据
     */
    getQlr(xmid, function (data) {
        // 封装权利人列表格式数据
        var qlrxx = {};
        qlrxx.title = "权利人信息";

        if (data) {
            // 转换字典
            for (var i = 0, len = data.length; i < len; i++) {
                for (var j = 0, zdlen = zdList.zjzl.length; j < zdlen; j++) {
                    if (zdList.zjzl[j].DM == data[i].zjzl) {
                        data[i].zjzlmc = zdList.zjzl[j].MC;
                    }
                }

                for (var j = 0, zdlen = zdList.qlrlx.length; j < zdlen; j++) {
                    if (zdList.qlrlx[j].DM == data[i].qlrlx) {
                        data[i].qlrlxmc = zdList.qlrlx[j].MC;
                    }
                }
            }
        }

        qlrxx.list = data;
        var getTpl = qlrTpl.innerHTML;
        laytpl(getTpl).render(qlrxx, function (html) {
            $("#tbody2").before(html)
        });
        form.render();
        resetInputDisabledCss();

    });

    /**
     * 动态生成房地产权项目信息中的下拉项
     */
    function loadFdcqxmSelect() {
        $.each(zdList.fwyt, function (index, item) {
            $('.ghyt').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });

        $.each(zdList.fwjg, function (index, item) {
            $('.fwjg').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });


        form.render('select');
    }

    /**
     * 获取权利信息数据
     */
    getQlxxForCallback(xmid, function (data) {
        if (data && data.qlid) {
            form.val('form', data);

            // 获取关联的房地产权的项目内多幢项目信息
            getFdcqXmxx(data.qlid);
        } else {
            setFdcqxmHTML();
        }
    });

    /**
     * 设置空行项目信息
     */
    function setFdcqxmHTML() {
        layer.alert("未查询到房地产权（项目内多幢）项目信息！", {title: '提示'});

        var getTpl = emptyXmxxTpl.innerHTML;
        $("#tbody3").before(getTpl);
        form.render();
        resetInputDisabledCss();
    }

    /**
     *  设置房地产权项目信息内容
     *  1、不好直接使用Layui table控件，因为官方不支持table内时间、下拉框等内容编辑
     *  2、采用动态拼接HTML内容方式，不过这种方式对于数据匹配、获取较为麻烦
     */
    function getFdcqXmxx(qlid) {
        // 如果为空只列出一空行
        if (isNullOrEmpty(qlid)) {
            $("#tbody3").before(emptyXmxxTpl.innerHTML);
            form.render();
            return;
        }

        $.ajax({
            url: BASE_URL + '/qlxx/' + qlid + '/fdcqxm',
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data && data.length > 0) {
                    // 先动态设置项目信息HTML内容
                    var getTpl = xmxxTpl.innerHTML;
                    for (var i = 0, len = data.length; i < len; i++) {
                        $("#tbody3").before(getTpl);
                    }
                    form.render();

                    // 动态生成下拉项内容
                    loadFdcqxmSelect();

                    // 再匹配数据，因为涉及同名控件，需要依次匹配
                    var fdcqxms = $(".fdcqxm");
                    var index = 0;
                    for (var i = 0, len = fdcqxms.length; i < len; i++) {
                        if (0 != i && 0 == i % 13) {
                            index += 1;
                        }

                        switch (i % 13) {
                            case 0:
                                fdcqxms[i].value = data[index].xmmc;
                                break;
                            case 1:
                                fdcqxms[i].value = data[index].zh;
                                break;
                            case 2:
                                fdcqxms[i].value = data[index].ghyt;
                                break;
                            case 3:
                                fdcqxms[i].value = data[index].ytmc;
                                break;
                            case 4:
                                fdcqxms[i].value = data[index].pzyt;
                                break;
                            case 5:
                                fdcqxms[i].value = data[index].sjyt;
                                break;
                            case 6:
                                fdcqxms[i].value = data[index].fwjg;
                                break;
                            case 7:
                                fdcqxms[i].value = data[index].fwjgmc;
                                break;
                            case 8:
                                fdcqxms[i].value = data[index].jzmj;
                                break;
                            case 9:
                                fdcqxms[i].value = data[index].jgsj;
                                break;
                            case 10:
                                fdcqxms[i].value = data[index].zcs;
                                break;
                            case 11:
                                fdcqxms[i].value = data[index].zts;
                                break;
                            case 12:
                                fdcqxms[i].value = data[index].fzid;
                                break;
                        }
                    }

                    //根据房屋结构渲染屋结构名称
                    $("[lay-filter=fdcqxm-fwjg]").each(function () {
                        renderFdcqxmFwjgmc($(this).val(), $(this));
                    });


                    //根据规划用途渲染用途名称
                    $("[lay-filter=fdcqxm-ghyt]").each(function () {
                        renderFdcqxmYtmc($(this).val(), $(this));
                    });

                    form.render('select');
                } else {
                    setFdcqxmHTML();
                }
                resetSelectDisabledCss();

                /**
                 * 渲染日期
                 */
                renderDate();

                // 渲染日期，这里要等所有组件加载完成渲染
                renderDateOfTdsyqx(dateFormat, xmid);
            },
            complete: function (XMLHttpRequest, textStatus) {
                // 关闭loading
                removeModel();
            }
        });
    }

    /**
     * 表单校验
     */
    form.verify({
        //要求非负数字，允许为空
        number: [/^\d*(\.\d+)?$/, '请输入数字'],
        required: function (value, item) {
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "必填项不能为空";
            }
        }
    });

    /**
     * 保存表单内容数据
     */
    form.on('submit(submitBtn)', function (data) {
        // 验证提交数据
        dealAndCheckData(data);
        if (!isSubmit) {
            warnMsg(verifyMsg);
            isSubmit = true;
            return false;
        } else {
            if (isNullOrEmpty(data.field.qlid)) {
                warnMsg("当前项目不存在，无法保存！");
                return false;
            }

            // 加载遮罩
            addModel();
            var sumfzmj =true;
            if(!isNullOrEmpty(data.field.bdcdyh)){
                var zdzhtzm =deleteWhitespace(data.field.bdcdyh).substring(12,14);
                if(!isNullOrEmpty(nosumfzmjZdtzm) &&nosumfzmjZdtzm.indexOf(zdzhtzm) >-1){
                    sumfzmj =false;
                }
            }
            var jzmjSum = getJzmjSum();
            if(sumfzmj) {
                if($("#jzmj").length >0){
                    var zjzmj =parseFloat($("#jzmj").val());
                    if(jzmjSum !=zjzmj.toFixed(2)){
                        removeModel();
                        warnMsg("分幢建筑面积之和与建筑面积不相等！");
                        return false;
                    }
                }
                data.field.jzmj = jzmjSum;
            }
            data.field.xmid = xmid;
            // 保存房地产权登记信息
            var fdcqResult = saveFdcq(data);
            // 保存房地产权项目信息数据
            var fdcqxmResult = saveFdcqxm();
            // 保存权利其他状况和附记
            saveQlqtzkAndFj(JSON.stringify(data.field));
            // 更新项目信息
            // 同时更新项目的冗余字段
            updateXmxx(JSON.stringify(data.field));

            if (fdcqResult && fdcqxmResult) {
                // 保存受理页面的收费信息
                if (parent && 'function' === typeof (parent.saveSf)) {
                    parent.saveSf();
                }
                if (showSaveSuccessMsg) {
                    //保存完后重新加载权力其他状况和附记内容
                    successMsg("权利信息保存成功！");
                }
                showSaveSuccessMsg = true;
            } else {
                warnMsg("提交失败，请检查输入内容是否正确！");
            }

            removeModel();

            // 禁止提交后刷新
            return false;
        }
    });
    /**
     * 必填样式清除
     */
    $('td input').on('focus', function () {
        if ($(this).hasClass('layui-form-danger')) {
            $(this).removeClass('layui-form-danger');
        }
        if ($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')) {
            $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
        }
    });

    /**
     * 保存房地产权登记信息
     * @param data
     */
    function saveFdcq(data) {
        if (!data || 0 == data.length || !data.field) {
            return true;
        }

        var result;

        $.ajax({
            url: BASE_URL + '/qlxx/fdcq',
            type: "PUT",
            async: false,
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data && $.isNumeric(data) && data > 0) {
                    result = true;
                } else {
                    result = false;
                }
            },
            error: function () {
                result = false;
            }
        });

        return result;
    }

    /**
     * 求和项目多幢信息的建筑面积之和
     */
    function getJzmjSum() {
        var $jzmj = $(".jzmj");
        var jzmjSum = 0.00;
        if ($jzmj.length > 0) {
            $.each($jzmj, function (index, item) {
                if(!isNullOrEmpty(item.value)) {
                    jzmjSum += item.value * 1.00;
                }
            });
        }
        return jzmjSum.toFixed(2);
    }

    /**
     * 保存房地产权项目信息数据
     */
    function saveFdcqxm() {
        var fdcqxms = $(".fdcqxm");
        var data = [];
        var xmxx = {};
        for (var i = 0, len = fdcqxms.length; i < len; i++) {
            switch (i % 13) {
                case 0:
                    xmxx.xmmc = fdcqxms[i].value;
                    break;
                case 1:
                    xmxx.zh = fdcqxms[i].value;
                    break;
                case 2:
                    xmxx.ghyt = fdcqxms[i].value;
                    break;
                case 3:
                    xmxx.ytmc = fdcqxms[i].value;
                    break;
                case 4:
                    xmxx.pzyt = fdcqxms[i].value;
                    break;
                case 5:
                    xmxx.sjyt = fdcqxms[i].value;
                    break;
                case 6:
                    xmxx.fwjg = fdcqxms[i].value;
                    break;
                case 7:
                    xmxx.fwjgmc = fdcqxms[i].value;
                    break;
                case 8:
                    xmxx.jzmj = fdcqxms[i].value;
                    break;
                case 9:
                    xmxx.jgsj = fdcqxms[i].value;
                    break;
                case 10:
                    xmxx.zcs = fdcqxms[i].value;
                    break;
                case 11:
                    xmxx.zts = fdcqxms[i].value;
                    break;
                case 12:
                    xmxx.fzid = fdcqxms[i].value;
                    break;
            }
            if (0 != i && (0 == (i + 1) % 13)) {
                data.push(xmxx);
                xmxx = {};
            }
        }

        // 空直接返回
        if (!data || 0 == data.length) {
            return true;
        }

        var result;
        $.ajax({
            url: BASE_URL + '/qlxx/fdcq/fdcqxm',
            type: "PUT",
            async: false,
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data && $.isNumeric(data) && data > 0) {
                    result = true;
                } else {
                    result = false;
                }
            },
            error: function () {
                result = false;
            }
        });

        return result;
    }

    //设置权限控制样式
    if (readOnly || !isNullOrEmpty(formStateId)) {
        getStateByIdQlxx(readOnly, formStateId, "fdcqXmndzfw");
    }
    var showXgLog = $.getUrlParam("showXgLog");
    if(showXgLog){
        queryXgLog(xmid);
        renderChange("",form,"fdcqXmndzfw");
        renderDate(form,"fdcqXmndzfw");
    }
});


