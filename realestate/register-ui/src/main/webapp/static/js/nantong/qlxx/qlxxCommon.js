// 控制上下标题显示与否：true 上面隐藏、下面显示 ；false 上面显示  下面隐藏； 无 都显示
var isCrossOri = $.getUrlParam("isCrossOri");
// layui元素
var element, form, layer, table, laytpl, $;

// 是否虚拟不动产单元
var isXndyh;
//点击提交时为空的全部标红
var isSubmit = true;
var verifyMsg = "必填项不能为空!";
var xmid ;
var processInsId;
var zxlc;
// 显示保存成功的提示
var showSaveSuccessMsg = true;
var form;
//判断当前页面是否在 权利信息与单元信息组合页面
var isInZhym = $.isFunction(window.parent.qlxxIsYzEnd);
//验证建筑面积=分摊建筑面积+专有建筑面积
var yzjzmj;
layui.use(['layer', 'element', 'form', 'jquery', 'laydate', 'table', 'laytpl'], function () {
    //查询子系统调用的时候
    if (isCrossOri != true && isCrossOri != "true") {
        $(".content-title").show();
        if (isCrossOri == false || isCrossOri == "false") {
            $('.table-name').hide();
            $('.content-main').css("padding-top", "70px");
            $(".content-div").css("padding", "10px");
        }
        // 加载遮罩
        addModel();
    }

    if (isCrossOri == true || isCrossOri == "true") {
        $(".content-div").css("padding", "10px");
        $(".bdc-content-fix").css("display", "none");
    }
    form = layui.form;
    element = layui.element,
        $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        laytpl = layui.laytpl;

    // 获取表单权限参数readOnly
    var readOnly2 = $.getUrlParam('readOnly');
    // 只读设置保存与附记清空按钮不可见
    if (readOnly2 == true || readOnly2 == "true") {
        var element = document.getElementById("submitBtn");
        if (null != element) {
            element.setAttribute("style", "display:none;")
        }
        var clearFjBtn = document.getElementById("clearFjBtn");
        if (null != clearFjBtn) {
            clearFjBtn.setAttribute("style", "display:none;")
        }
    }

    $(function () {
        //滚动时头部固定
        var $contentTitle = $('.bdc-content-fix:visible');
        if ($contentTitle.length != 0) {
            defaultStyle();
        }
        //是否验证建筑面积=分摊建筑面积+专有建筑面积，默认为：true（开启） false（不开启）
        function getYzjzmj(){
            $.ajax({
                url: BASE_URL + '/getYzjzmj',
                type: "GET",
                dataType: "json",
                success: function (data) {
                    yzjzmj = data;
                }
            });
        }
        function defaultStyle() {
            if ($(window).scrollTop() > 10) {
                $contentTitle.css('top', '0');
            } else if ($(window).scrollTop() <= 10) {
                $contentTitle.css('top', '10px');
            }
        }

        $(window).resize(function () {
            if ($contentTitle.length != 0) {
                defaultStyle();
            }
        });
        $(window).on('scroll', function () {
            if ($contentTitle.length != 0) {
                if ($(this).scrollTop() > 10) {
                    $contentTitle.css('top', '0');
                } else if ($(this).scrollTop() <= 10) {
                    $contentTitle.css('top', '10px');
                }
            }
        });

        xmid = $.getUrlParam('xmid');
        processInsId = $.getUrlParam('processInsId');
        zxlc = $.getUrlParam('zxlc');

        //重新生成权利其他状况
        $(".resetqlqtzk").on('click', function () {
            showSaveSuccessMsg = true;
            // 先保存
            $('#submitBtn').click();
            reGenerateQlqtzk(xmid,"2");
        });

        //重新生成附记
        $(".resetfj").on('click', function () {
            showSaveSuccessMsg = true;
            // 先保存
            $('#submitBtn').click();
            // 再生成附记
            reGenerateFj(xmid,"3");
        });

        //点击附件里面的清空按钮
        $('#clearFjBtn').on('click', function () {
            addModel();
            var xmid = $.getUrlParam('xmid');
            $.ajax({
                url: BASE_URL + '/qlxx/' + xmid,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    removeModel();
                    if (data) {
                        if (data.qszt) {
                            warnMsg("此数据已生效，禁止清空!");
                        } else {
                            layer.msg('您确认要清空吗？', {
                                skin: 'bdc-del-bg',
                                time: 0 //不自动关闭
                                , shade: [0.4, '#000']
                                , btn: ['确定', '取消']
                                , yes: function (index) {
                                    addModel();
                                    $.ajax({
                                        url: BASE_URL + "/qlxx/delete/fj/" + xmid,
                                        type: "post",
                                        dataType: "json",
                                        success: function (data) {
                                            $('#clearFjBtn').siblings().val('');
                                            removeModel();
                                            successMsg("清空成功!");
                                        },
                                        error: function () {
                                            removeModel();
                                            warnMsg("清空失败，请重试！");
                                        }
                                    });
                                }
                            });
                        }
                    } else {
                        warnMsg("没有找到对应权利数据!");
                    }
                }
            });
        });

        //权利信息与单元信息组合大页面所需监听事件
        if($('select[name=ghyt]').length > 0 && $.isFunction(window.parent.getSelectData)){
            form.on("select(ghyt)",function(data){
                window.parent.getSelectData('ghyt',data);
            });
        }
        if($('select[name=qlxz]').length > 0 && $.isFunction(window.parent.getSelectData)){
            form.on("select(qlxz)",function(data){
                window.parent.getSelectData('qlxz',data);
            });
        }
        if($('input[name=qlxz]').length > 0 && $.isFunction(window.parent.getSelectData)){
            $('input[name=qlxz]').on('input propertychange',function(){
                var changeData = $(this).val();
                window.parent.getSelectData('qlxzInput', changeData);
            });
        }
        if ($('input[name=jzmj]').length > 0 && $.isFunction(window.parent.getSelectData)) {
            $('input[name=jzmj]').on('input propertychange', function () {
                var changeData = $(this).val();
                window.parent.getSelectData('jzmj', changeData);
            });
        }
        if ($('input[name=jyhth]').length > 0 && $.isFunction(window.parent.getSelectData)) {
            $('input[name=jyhth]').on('input propertychange', function () {
                var changeData = $(this).val();
                window.parent.getSelectData('jyhthql', changeData);
            });
        }
    });


    /**
     * 获取字典
     * @param callback 回调函数
     */
    window.getZd = function (callback) {
        var zdList = getZdList();
        if (zdList && zdList.djlx) {
            callback(zdList);
        }
    };

    /**
     * 获取权利信息数据
     */
    window.getQlxx = function (xmid) {
        $.ajax({
            url: BASE_URL + '/qlxx/' + xmid,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data) {
                    form.val('form', data);

                    // 预告登记根据预告登记种类隐藏是否转让
                    if(!isNullOrEmpty(data.ygdjzl) && data.ygdjzl != 3 && data.ygdjzl != 4 ) {
                        $('#jzzr').parents('td').prev('td').css("display", "none");
                        $('#jzzr').parents('td').css("display", "none");

                        $('#dbfw').parents('td').attr('colspan', '9');
                    }

                    //房屋性质名称根据房屋性质是否选择其他控制是否不可编辑
                    renderFwxzmc(data.fwxz);

                    //用途名称根据房屋性质是否选择其他控制是否不可编辑
                    renderYtmc(data.ghyt);

                    // 附记在为空时，自动生成
                    if(data.fj==null||data.fj==''||data.fj==undefined){
                        reGenerateFj(xmid, "3");
                    }

                    getXmxx(xmid);
                    // 调整间距,实现房地坐落可以换行
                    if ($('#zl').length){
                        var flagLen = 305;
                        var zl = $("#zl").val();
                        var obj = document.getElementById("zl");
                        if(getLenPx(zl,14.6)>305){
                            obj.style.lineHeight= "19px";
                        }
                        document.onkeydown = function (ev) {
                            var oEvent = window.event;
                            if(oEvent.keyCode===13){
                                obj.style.lineHeight= "19px";
                                flagLen = getLenPx(zl,14.6)+3;
                            }
                        }
                        $("textarea[name=zl]").live('input propertychange',function(){
                            var a = $(this).val();
                            document.onkeydown = function (ev) {
                                var oEvent = window.event;
                                if(oEvent.keyCode===13){
                                    obj.style.lineHeight= "19px";
                                    flagLen = getLenPx(a,14.6)+3;
                                }else if(getLenPx(a,14.6)>flagLen){
                                    obj.style.lineHeight= "19px";
                                }else {
                                    obj.style.lineHeight= "38px";
                                    flagLen = 305;
                                }
                            }
                            if(getLenPx(a,14.6)>flagLen){
                                obj.style.lineHeight= "19px";
                            }else {
                                obj.style.lineHeight= "38px";
                                flagLen = 305;
                            }
                        })
                    }


                    // 对于坐落字段加title
                    if (!isNullOrEmpty(data.zl) && $("[name='zl']")) {
                        $("[name='zl']").attr("title", data.zl);
                    }
                }
                resetSelectDisabledCss();

                /**
                 * 渲染日期
                 */
                renderDate();
                /**
                 * 渲染土地使用权日期
                 */
                renderDateOfTdsyqx(dateFormat, xmid);

            }
        });
    };

    /**
     * 预告登记种类下拉框联动控制预抵押信息
     */
    form.on('select(ygdjzl)', function (data) {
        if (!isNullOrEmpty(data.value)) {
            if (data.value == 3 || data.value == 4) {
                $(".ydyaxx").removeAttr("style");
                $(".ygxx").css("display", "none");
                // 避免name值冲突，保存获取不到值，重新给name赋值
                $("#bdbzzqse").attr("name", "qdjg");
                $("#qdjg").attr("name", "");
                $('#jzzr').parents('td').prev('td').css("display", "");
                $('#jzzr').parents('td').css("display", "");
                $('#dbfw').parents('td').attr('colspan', '2');
            } else {
                $(".ydyaxx").css("display", "none");
                $(".ygxx").removeAttr("style");
                // 避免name值冲突，保存获取不到值，重新给name赋值
                $("#bdbzzqse").attr("name", "bdbzzqse");
                $("#qdjg").attr("name", "qdjg");
                $('#jzzr').parents('td').prev('td').css("display", "none");
                $('#jzzr').parents('td').css("display", "none");

                $('#dbfw').parents('td').attr('colspan', '9');


                form.render('select');
            }
        }
    });

    // 查封登记监听查封类型
    form.on('select(cflx)', function (data) {
        if(!isNullOrEmpty(data.value)) {
            //查封类型为轮候查封或轮候预查封时，清空起始结束时间
            if(data.value == 2 || data.value == 4) {
                $("#cfqssj").val("");
                $("#cfjssj").val("");
            }
        }
    });

    /**
     * 房屋性质下拉框联动控制房屋性质名称是否可编辑
     */
    form.on('select(fwxz)', function (data) {
        renderFwxzmc(data.value);

    });

    /**
     * 规划用途下拉框联动控制用途名称是否可编辑
     */
    form.on('select(ghyt)', function (data) {
        renderYtmc(data.value);

    });

    /**
     * 项目内多幢规划用途下拉框联动控制用途名称是否可编辑
     */
    form.on('select(fdcqxm-ghyt)', function (data) {
        renderFdcqxmYtmc(data.value,$(data.elem));

    });

    /**
     * 获取房地产权登记信息（项目内多幢房屋）权利信息数据
     */
    window.getQlxxForCallback = function (xmid, callback) {
        // 获取基本权利信息
        $.ajax({
            url: BASE_URL + '/qlxx/' + xmid,
            type: "GET",
            dataType: "json",
            success: function (data) {
                callback(data);
                // 附记在为空时，自动生成
                if (data.fj == null || data.fj == '' || data.fj == undefined) {
                    reGenerateFj(xmid, "3");
                }
                getXmxx(xmid);
                resetSelectDisabledCss();
            }
        });
    };

    /**
     * 获取权利人信息
     */
    window.getQlr = function (xmid, callback) {
        $.ajax({
            url: BASE_URL + '/qlr/' + xmid,
            type: "GET",
            dataType: "json",
            success: function (data) {
                callback(data);
            }, complete: function (XMLHttpRequest, textStatus) {
                removeModel();
            }
        });
    };

    /**
     * 保存表单
     * @param url
     */
    window.saveForm = function (url) {
        /**
         * 表单校验（必填验证过滤虚拟不动产单元）
         */
        form.verify({
            //要求非负数字，允许为空
            number: [/^\d*(\.\d+)?$/, '请输入数字'],
            number2: [/^-?\d*$/, '请输入整数'],
            number3: [/^-?\d+$/, '请输入整数'],
            required: function (value, item) {
                if (value == '') {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                }
            }
        });
        /**
         * 表单提交
         */
        form.on('submit(submitBtn)', function (data) {
            // 验证提交数据
            dealAndCheckData(data);
            if (!isSubmit) {
                warnMsg(verifyMsg);
                isSubmit = true;
                verifyMsg = "必填项不能为空!";
                if(isInZhym){
                    window.parent.qlxxIsYzEnd('wtg');
                }
                return false;
            } else {
                if(isInZhym){
                    window.parent.qlxxIsYzEnd('tg');
                }
                // 加载遮罩
                addModel();
                // 应合肥要求，查封文号和解封文号入库都要替换为英文括号
                if (!isNullOrEmpty(data.field.cfwh)) {
                    data.field.cfwh = data.field.cfwh.replace('（', '(');
                    data.field.cfwh = data.field.cfwh.replace('）', ')');
                }
                if (!isNullOrEmpty(data.field.jfwh)) {
                    data.field.jfwh = data.field.jfwh.replace('（', '(');
                    data.field.jfwh = data.field.jfwh.replace('）', ')');
                }
                // 南通需求 房地产权土地使用权面积 自动计算
                if (url.replace("/qlxx/", "") === "fdcq"){
                    var dytdmj = 0;
                    var fttdmj = 0;
                    if (!isNaN(data.field.dytdmj)) {
                        dytdmj = Number(data.field.dytdmj);
                    }
                    if (!isNaN(data.field.fttdmj)) {
                        fttdmj = Number(data.field.fttdmj);
                    }
                    data.field.tdsyqmj = dytdmj + fttdmj;
                }
                if (url.replace("/qlxx/", "") === "jsydsyq") {
                    if (!isNaN(data.field.dytdmj)) {
                        data.field.syqmj = Number(data.field.dytdmj);
                    }
                }

                // 保存权利其他状况和附记
                saveQlqtzkAndFj(JSON.stringify(data.field));
                // saveXmfb(data.field.xmid);
                // 同时更新项目的冗余字段
                //备注字段目前仅南通查封登记需要同步
                if (url.replace("/qlxx/", "") === "cf") {
                    data.field.cfbz =data.field.bz;
                }
                updateXmxx(JSON.stringify(data.field));


                $.ajax({
                    url: BASE_URL + url,
                    type: "PUT",
                    data: JSON.stringify(data.field),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        var msg = "提交失败，请检查输入内容是否正确！";

                        // 保存成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                        if (data && $.isNumeric(data) && data > 0) {
                            msg = "权利信息保存成功！";
                            // 保存受理页面的收费信息
                            if (parent && 'function' === typeof (parent.saveSf)) {
                                parent.saveSf();
                            }
                            //保存完后重新加载权力其他状况和附记内容
                            // reGenerateQlqtzk(xmid,"2");
                            // reGenerateFj(xmid,"3");
                        }

                        removeModel();
                        if (showSaveSuccessMsg) {
                            successMsg(msg);
                        }
                        showSaveSuccessMsg = true;
                    },
                    error: function () {
                        removeModel();
                        warnMsg("提交失败，请重试！");
                    }
                });

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
        $('.xm-input').on('click', function () {
            if ($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')) {
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
        });
    };

    /**
     * 同步更新项目信息
     * @param data
     */
    window.updateXmxx = function (data) {
        var result;
        $.ajax({
            url: BASE_URL + '/qlxx/xmxx',
            type: "PUT",
            data: data,
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                result = true;
            },
            error: function () {
                warnMsg("同步项目信息失败，请重试！");
            }
        });
    };


    /**
     * 更新权利其他状况和附记
     * @param data
     */
    window.saveQlqtzkAndFj = function (data) {
        var result;
        $.ajax({
            url: BASE_URL + '/qlxx/qlqtzkAndFj',
            type: "PUT",
            data: data,
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                result = true;
            },
            error: function () {
                warnMsg("更新权利其他状况和附记，请重试！");
            }
        });
    };

    /**
     * 获取项目信息
     * @param xmid
     */
    function getXmxx(xmid) {
        // 获取当前权利的项目信息
        $.ajax({
            url: BASE_URL + '/qlxx/xmxx/' + xmid,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data) {
                    $("#bdcqzh").val(data.bdcqzh);
                    $("#ywr").val(data.ywr);
                    $("#qlxz").val(data.qlxz);

                    // 设置承包面积单位
                    if (!isNullOrEmpty(data.mjdw)){
                        if (data.mjdw === 1) {
                            $("#mjdw").text("承包（使用权）面积（平方米）");
                        } else if (data.mjdw === 3) {
                            $("#mjdw").text("承包（使用权）面积（公顷）");
                        } else {
                            $("#mjdw").text("承包（使用权）面积（亩）");
                        }
                    }

                    // 设置预告登记种类
                    var ygdjzl = $("#ygdjzl").val();
                    if (!isNullOrEmpty(ygdjzl)) {
                        if (ygdjzl == 3 || ygdjzl == 4) {
                            $("#jyhtlabel").html("抵押合同号");
                            $(".ygxx").css("display", "none");
                        } else {
                            $(".ydyaxx").css("display", "none");
                            // 避免name值冲突，保存获取不到值，重新给name赋值
                            $("#bdbzzqse").attr("name", "bdbzzqse");
                        }
                    } else {
                        $(".ygxx").css("display", "none");
                    }
                    // 设置交易合同号/抵押合同号
                    $("#jyhth").val(data.jyhth);

                    // 设置部分权利其他状况
                    $("#bfqlqtzk").val(data.bfqlqtzk);
                    $("#zdzhyt").val(data.zdzhyt);
                    form.render();
                    resetSelectDisabledCss();
                    // getXmfb(xmid);
                    //其他权利状况为空时，自动生成
                    if (isNullOrEmpty(data.bfqlqtzk)) {
                        // $(".resetqlqtzk").click();
                        reGenerateQlqtzk(xmid, "2");
                    }
                }
            }
        });
    }

    /**
     * 根据房屋性质渲染房屋性质名称
     */
    window.renderFwxzmc = function (fwxzDm){
        var fwxzmcElement =document.getElementById("fwxzmc");
        if(fwxzmcElement != null) {
            if (fwxzDm == 99) {
                //房屋性质选择其他
                fwxzmcElement.removeAttribute("disabled", "off");
            } else {
                //房屋性质非其他
                var fwxz_text =$("#fwxz").find("option:selected").text();
                $("#fwxzmc").val(fwxz_text);
                fwxzmcElement.setAttribute("disabled", "off");
            }
        }

    };

    /**
     * 根据规划用途渲染用途名称
     */
    window.renderYtmc = function (ghytDm){
        var ytmcElement =document.getElementById("ytmc");
        if(ytmcElement != null) {
            if (ghytDm == 80) {
                //规划用途选择其他
                ytmcElement.removeAttribute("disabled", "off");
            } else {
                var ytmc_text =$("#ghyt").find("option:selected").text();
                $("#ytmc").val(ytmc_text);
                //规划用途非其他
                ytmcElement.setAttribute("disabled", "off");
            }
        }


    };

    /**
     * 根据规划用途渲染用途名称(项目内多幢)
     */
    window.renderFdcqxmYtmc = function (ghytDm,$ghyt){
        var $ytmcElement =$ghyt.parents("tr").find(".ytmc");
        if($ytmcElement != null &&$ytmcElement.length >0) {
            if (ghytDm == 80) {
                //规划用途选择其他
                $ytmcElement.removeAttr("disabled", "off");
            } else {
                //规划用途非其他
                var ytmc_text =$ghyt.find("option:selected").text();
                $ytmcElement.val(ytmc_text);
                $ytmcElement.attr("disabled", "off");
            }
        }


    };

});

/**
 * 处理并验证数据
 * @param data
 * @returns {*}
 */
function dealAndCheckData(data) {
    if (isNullOrEmpty(data.field.qlid)) {
        verifyMsg = "当前项目不存在，无法保存！";
        if(isInZhym){
            window.parent.qlxxIsYzEnd('wtg');
        }
        isSubmit = false;
    }

    //修改土地性质为划拨或租赁时，权利的结束时间不可填写
    try {
        if ($('.tdjssj').length > 0) {
            // 能从父页面取到配置才做验证
            var ghytList = parent.noYzGhytList;
            var noyz = false;
            if(ghytList && ghytList.join(",").length > 0){

                var ghyt = data.field.ghyt;
                if(!ghyt){
                    ghyt = getGhytByXmid(data.field.xmid);
                }
                console.log("验证规划用途配置和当前规划用途："+ghytList+" "+ghyt);
                if(ghyt && ghytList.indexOf(ghyt) > -1) {
                    noyz = true;
                }
            }

            checkJssjGz2($("#qlxz").val(), $('.tdjssj').val(), noyz);
            checkJssjGz4($("#qlxz").val(), $('.tdjssj').val());
        }
        if ($('.tdjssj').length > 0 && $('.tdqssj').length > 0) {
            checkJssjGz3($("#qlxz").val(), $('.tdjssj').val(), $('.tdqssj').val())
        }
    } catch (e) {
        isSubmit = false;
        verifyMsg = e.message;
        if(isInZhym){
            window.parent.qlxxIsYzEnd('wtg');
        }
    }

    // 验证所在层和总层数逻辑
    var szc;
    var zcs;
    if (!isNullOrEmpty(data.field.szc)) {
        szc = parseInt(data.field.szc) * 1;
        if (szc == 0) {
            isSubmit = false;
            verifyMsg = "所在层为0，无效值！";
            $("#szc").addClass('layui-form-danger');
            if(isInZhym){
                window.parent.qlxxIsYzEnd('wtg');
            }
        }
    }
    if (!isNullOrEmpty(data.field.szc) && !isNullOrEmpty(data.field.zcs)) {
        zcs = parseInt(data.field.zcs) * 1;
        if (szc > zcs) {
            isSubmit = false;
            verifyMsg = "所在层的值不能大于总层数的值！";
            $("#szc").addClass('layui-form-danger');
            $("#zcs").addClass('layui-form-danger');
            if(isInZhym){
                window.parent.qlxxIsYzEnd('wtg');
            }
        }
    }
}
/**
 * 重新生成权利其他状况
 */
function reGenerateQlqtzk(xmid,mode){
    var $qlqtzk = $("textarea[name='bfqlqtzk']");
    if($qlqtzk.length >0) {
        //重新加载模板数据
        queryQlqtzkFjMb(xmid, $qlqtzk, mode);
    }
    return false;
}

/**
 * 重新生成附记
 */
function reGenerateFj(xmid,mode){
    var $fj = $("textarea[name='fj']");
    if($fj.length >0) {
        $fj.val("");
        //重新加载模板数据
        queryQlqtzkFjMb(xmid, $fj, mode);
    }
    return false;
}

//清空权利其他状况
function clearQlqtzk(xmid) {
    var bdcXmData = {};
    bdcXmData.bfqlqtzk = "";
    bdcXmData.xmid =xmid;
    getReturnData("/rest/v1.0/clearQlqtzk", JSON.stringify(bdcXmData), 'PATCH', function (data) {

    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);

}
//清空附记
function clearFj(xmid) {
    var qlData = {};
    qlData.fj = "";
    getReturnData("/rest/v1.0/clearFj?processInsId=" + processInsId + "&onexmid=" + xmid + "&zxlc=" + zxlc +"&xmids="+xmid, JSON.stringify(qlData), 'PATCH', function (data) {
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

/**
 *  获取权利其他状况或者附记模板信息
 */
function queryQlqtzkFjMb(xmid, $qlqtzkfj, mode) {
    getReturnData("/rest/v1.0/queryQlqtzkFjMb/nthm", {xmid: xmid, mode: mode,sfgx:true}, "GET", function (data) {
        // 模板有值时才覆盖页面值，要情况，手动删除保存
        if (isNotBlank(data)) {
            //赋值权利其他状况或者附记
            if(mode === "2") {
                $qlqtzkfj.val(data.qlqtzkmbnr);
            }
            if(mode === "3") {
                $qlqtzkfj.val(data.fjmbnr);
            }

        }
    }, function (error) {
        delAjaxErrorMsg(error);

    })
}

function getGhytByXmid(xmid){
    var ghyt = "";
    $.ajax({
        url: BASE_URL + '/qlxx/xmxx/getGhytByXmid/' + xmid,
        type: 'GET',
        async: false,
        success: function (data) {
            ghyt = data;
        },
        error: function (e) {
        }
    });
    return ghyt;
}
