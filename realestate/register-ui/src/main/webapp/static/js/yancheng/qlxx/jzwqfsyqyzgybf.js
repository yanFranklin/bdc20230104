/**
 * @author "<a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2018/12/14
 * @description 建筑物区分所有权业主共有部分登记信息表单JS
 */
$(document).ready(function () {
    // 获取参数
    var xmid = $.getUrlParam('xmid');
    // 表单ID
    var formStateId = $.getUrlParam('formStateId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');
    //获取页面版本(仅蚌埠)
    var version =$.getUrlParam('version');

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

        form.render('select');
    });

    /**
     * 获取权利信息数据
     */
    getQlxxForCallback(xmid, function (data) {
        if (data) {
            form.val('form', data);

            // 单独获取权利人信息
            setQlrxx();

            // 获取建筑物区分所有权业主共有部分登记信息_共有部分
            if (data.qlid) {
                getFdcq3Gyxx(data.qlid);
            }
        } else {
            warnMsg("未查询到数据！");
            setGyxxHTML();
        }
        removeModel();
    });

    /**
     * 设置权利人
     */
    function setQlrxx() {
        $.ajax({
            url: BASE_URL + '/qlxx/fdcq3/qlrxx?xmid=' + xmid,
            type: "GET",
            dataType: "text",
            success: function (data) {
                $("#qlr").val(data);
            }
        });
    }

    /**
     * 设置空行共有信息
     */
    function setGyxxHTML() {
        var getTpl = emptyGyxxTpl.innerHTML;
        $("#tbody2").before(getTpl);
        form.render();
    }

    /**
     * 获取项目附表信息
     * @param xmid
     */
    function getXmfbxx(xmid) {
        // 获取当前权利的项目附表信息
        $.ajax({
            url: BASE_URL + '/qlxx/xmfb/' + xmid,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data) {
                    $("#dawz").val(data.dawz);
                    form.render();
                    resetSelectDisabledCss();
                }
            }
        });
    }

    /**
     *  设置建筑物区分所有权业主共有部分登记信息_共有部分信息内容
     */
    function getFdcq3Gyxx(qlid) {
        // 如果为空只列出一空行
        if (isNullOrEmpty(qlid)) {
            setGyxxHTML();
            return;
        }

        $.ajax({
            url: BASE_URL + '/qlxx/' + qlid + '/fdcq3/gyxx',
            type: "GET",
            dataType: "json",
            success: function (data) {
                getXmfbxx(xmid)
                if (data && data.length > 0) {
                    // 先动态设置项目信息HTML内容
                    var getTpl = gyxxTpl.innerHTML;
                    for (var i = 0, len = data.length; i < len; i++) {
                        $("#tbody2").before(getTpl);
                    }

                    form.render();
                    resetInputDisabledCss();

                    // 再匹配数据，因为涉及同名控件，需要依次匹配
                    var gyxxs = $(".gyxx");
                    var index = 0;
                    for (var i = 0, len = gyxxs.length; i < len; i++) {
                        if (0 != i && 0 == i % 9) {
                            index += 1;
                        }

                        switch (i % 9) {
                            case 0:
                                gyxxs[i].value = data[index].slbh;
                                break;
                            case 1:
                                gyxxs[i].value = data[index].jgzwbh;
                                break;
                            case 2:
                                gyxxs[i].value = data[index].jgzwmc;
                                break;
                            case 3:
                                gyxxs[i].value = data[index].jgzwmj;
                                break;
                            case 4:
                                gyxxs[i].value = data[index].fttdmj;
                                break;
                            case 5:
                                gyxxs[i].value = data[index].dbr;
                                break;
                            case 6:
                                gyxxs[i].value = data[index].djsj;
                                break;
                            case 7:
                                gyxxs[i].value = data[index].fj;
                                break;
                            case 8:
                                gyxxs[i].value = data[index].gyxxid;
                                break;
                        }
                    }

                    // 蚌埠版本分摊土地面积为空时可以保存
                    if(isNotBlank(version) && version =='bengbu'){
                        $('input[name = "fttdmj"]').removeAttr('lay-verify');
                    }
                } else {
                    setGyxxHTML();
                }

                // 渲染日期，这里要等所有组件加载完成渲染
                renderDate();
                //设置权限控制样式
                if (readOnly || !isNullOrEmpty(formStateId)) {
                    getStateByIdQlxx(readOnly, formStateId, "jzwqfsyqyzgybf");
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                // 关闭loading
                removeModel();
            }
        });
    }

    /**
     * 保存表单内容数据
     */
    form.on('submit(submitBtn)', function (data) {
        // 加载遮罩
        addModel();
        if (isNullOrEmpty(data.field.qlid)) {
            warnMsg("当前项目不存在，无法保存！");
            // 移除遮罩
            removeModel();
            return false;
        }

        // 保存建筑物区分所有权业主共有部分登记信息
        var fdcq3Result = saveFdcq3(data);
        var gyxxData = getGyxxData();
        // 保存共有部分信息内容
        var fdcq3GyxxResult = saveFdcq3Gyxx(gyxxData);
        // 同步更新项目信息部分内容
        syncUpdateXmxx(data.field, gyxxData);
        // 保存权利其他状况和附记
        saveQlqtzkAndFj(JSON.stringify(data.field));

        if (fdcq3Result || fdcq3GyxxResult) {
            // layer.alert("保存成功！", {title: '提示'});
            // 保存受理页面的收费信息
            if (parent && 'function' === typeof (parent.saveSf)) {
                parent.saveSf();
            }
            if (showSaveSuccessMsg) {
                successMsg("权利信息保存成功");
            }
            showSaveSuccessMsg = true;
        } else {
            //layer.alert("提交失败，请检查输入内容是否正确！", {title: '提示'});
            warnMsg("提交失败，请检查输入内容是否正确！");
        }

        // 移除遮罩
        removeModel();

        // 禁止提交后刷新
        return false;
    });

    /**
     * 保存建筑物区分所有权业主共有部分登记信息
     * @param data
     */
    function saveFdcq3(data) {
        if (!data || 0 == data.length || !data.field) {
            return true;
        }

        var result;

        $.ajax({
            url: BASE_URL + '/qlxx/fdcq3',
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
     * 保存共有部分信息内容
     */
    function saveFdcq3Gyxx(data) {
        // 空直接返回
        if (!data || 0 == data.length) {
            return true;
        }

        var result;
        $.ajax({
            url: BASE_URL + '/qlxx/fdcq3/gyxx',
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

    /**
     * 同步共有信息中的建（构）筑物面积至项目的定着物信息
     */
    function syncUpdateXmxx(data, gyxxData){
        if(isNotBlank(xmid) && gyxxData.length > 0){
            // 同时更新项目的冗余字段
            data.jzmj = gyxxData[0].jgzwmj;
            updateXmxx(JSON.stringify(data));
        }
    }

    function getGyxxData(){
        var gyxxs = $(".gyxx");
        var data = [];
        var xmxx = {};
        for (var i = 0, len = gyxxs.length; i < len; i++) {
            switch (i % 9) {
                case 0:
                    xmxx.slbh = gyxxs[i].value;
                    break;
                case 1:
                    xmxx.jgzwbh = gyxxs[i].value;
                    break;
                case 2:
                    xmxx.jgzwmc = gyxxs[i].value;
                    break;
                case 3:
                    xmxx.jgzwmj = gyxxs[i].value;
                    break;
                case 4:
                    xmxx.fttdmj = gyxxs[i].value;
                    break;
                case 5:
                    xmxx.dbr = gyxxs[i].value;
                    break;
                case 6:
                    xmxx.djsj = gyxxs[i].value;
                    break;
                case 7:
                    xmxx.fj = gyxxs[i].value;
                    break;
                case 8:
                    xmxx.gyxxid = gyxxs[i].value;
                    break;
            }
            if (0 != i && (0 == (i + 1) % 9)) {
                data.push(xmxx);
                xmxx = {};
            }
        }
        return data;
    }


    var showXgLog = $.getUrlParam("showXgLog");
    if(showXgLog){
        queryXgLog(xmid);
        renderChange("",form,"jzwqfsyqyzgybf");
        renderDate(form,"jzwqfsyqyzgybf");
    }
});



