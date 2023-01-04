// 控制上下标题显示与否：true 上面隐藏、下面显示 ；false 上面显示  下面隐藏； 无 都显示
var isCrossOri = $.getUrlParam("isCrossOri");
// layui元素
var element, form, layer, table, laytpl, $;

//点击提交时为空的全部标红
var isSubmit = true;
var verifyMsg = "必填项不能为空!";
// 是否虚拟不动产单元
var isXndyh;
// 显示保存成功的提示
var showSaveSuccessMsg = true;
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

    element = layui.element,
        $ = layui.jquery,
        form = layui.form,
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

    //根据父页面的djyy字段来控制本页面哪些字段可以编辑
    changeColStatusBydjyy();

    $(function () {
        // 处理受理页面的登记原因字段
        changeColStatusBydjyy();

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

                    //房屋性质名称根据房屋性质是否选择其他控制是否不可编辑
                    renderFwxzmc(data.fwxz);

                    //用途名称根据房屋性质是否选择其他控制是否不可编辑
                    renderYtmc(data.ghyt);

                    // 获取项目信息
                    getXmxx(xmid);

                    //获取项目附表信息
                    getXmfbxx(xmid);

                    // 对于坐落字段加title
                    if (!isNullOrEmpty(data.zl) && $("[name='zl']")) {
                        $("[name='zl']").attr("title", data.zl);
                    }
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
            } else {
                $(".ydyaxx").css("display", "none");
                $(".ygxx").removeAttr("style");
                // 避免name值冲突，保存获取不到值，重新给name赋值
                $("#bdbzzqse").attr("name", "bdbzzqse");
                $("#qdjg").attr("name", "qdjg");
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
            // 要求非负数字，允许为空
            number: [/^\d*(\.\d+)?$/, '请输入数字'],
            // 验证可为空，整数
            number2: [/^-?\d*$/, '请输入整数'],
            // 验证不可为空，整数
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
                return false;
            } else {
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

                //如果用途为车库，且填写层高小于2.2 米，建筑面积设置为空
                if(isNotBlank(data.field.ghyt) && data.field.ghyt === "8" && isNotBlank(data.field.cg) && data.field.cg <2.2) {
                    data.field.jzmj = 0;
                }

                // 同时更新项目的冗余字段
                updateXmxx(JSON.stringify(data.field));

                // 当保存的是房地产权的时候
                // 为了解决变更抵押等批量组合流程同步抵押土地面积，这里做同步
                // 当任意一个bdcdy更新了mj，需要同步dyaq的mj
                if(url.indexOf("/qlxx/fdcq") != -1){
                    syncFdcqMjToDyaq(data.field);
                }

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
                        }

                        removeModel();
                        successMsg(msg);
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
                warnMsg("提交失败，请重试！");
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
                warnMsg("提交失败，请重试！");
            }
        });
    }

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
                    $("#jyhth").val(data.jyhth);
                    $("#zdzhyt").val(data.zdzhyt);
                    form.render();
                    resetSelectDisabledCss();
                }
            }
        });
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
     * 处理并验证数据
     * @param data
     * @returns {*}
     */
    window.dealAndCheckData = function (data) {
        if (isNullOrEmpty(data.field.qlid)) {
            verifyMsg = "当前项目不存在，无法保存！";
            isSubmit = false;
        }
        // 房地产权验证分摊建筑面积+专有建筑面积==建筑面积
        var ftjzmj = 0;
        var zyjzmj = 0;
        var jzmj = 0;
        if (!isNullOrEmpty(data.field.ftjzmj)) {
            ftjzmj = parseFloat(data.field.ftjzmj) * 100;
        }
        if (!isNullOrEmpty(data.field.zyjzmj)) {
            zyjzmj = parseFloat(data.field.zyjzmj) * 100;
        }
        if (!isNullOrEmpty(data.field.jzmj)) {
            jzmj = parseFloat(data.field.jzmj) * 100;
        }
        // 若专有和分摊面积均为 0 的情况下，不要验证 分摊+专有=建筑面积 的规则。
        var isCheck = (!(ftjzmj === 0 && zyjzmj === 0))&&yzjzmj;
        if (data.field.ftjzmj && data.field.zyjzmj && isCheck && jzmj.toFixed(0) != (ftjzmj + zyjzmj).toFixed(0)) {
            $("#ftjzmj").addClass('layui-form-danger');
            $("#zyjzmj").addClass('layui-form-danger');
            $("#jzmj").addClass('layui-form-danger');
            verifyMsg = '分摊建筑面积和专有建筑面积之和与建筑面积不相等！';
            isSubmit = false;
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
            }
        }
        //盐城不需要此验证逻辑
        // if (!isNullOrEmpty(data.field.szc) && !isNullOrEmpty(data.field.zcs)) {
        //     zcs = parseInt(data.field.zcs) * 1;
        //     if (szc > zcs) {
        //         isSubmit = false;
        //         verifyMsg = "所在层的值不能大于总层数的值！";
        //         $("#szc").addClass('layui-form-danger');
        //         $("#zcs").addClass('layui-form-danger');
        //     }
        // }
        //修改土地性质为划拨或租赁时，权利的结束时间不可填写
        try {
            if ($('.tdjssj').length > 0) {
                checkJssjGz2($("#qlxz").val(), $('.tdjssj').val(), false);
            }
        } catch (e) {
            isSubmit = false;
            verifyMsg = e.message;
        }
    }

    /**
     * 受理页面
     */
    function changeColStatusBydjyy(){
        //如果父页面有这些值 则说明父页面是受理页面
        if(parent.authorityMapByDjyy
            && (parent.$("#djyy1").val() ||parent.$("#djyy2").val() || parent.$("#djyy").val())){
            var djyy = "";
            if(parent.$("#djyy1").length > 0){
                djyy = parent.$("#djyy1").val();
            }
            if(parent.$("#djyy").length > 0){
                djyy = parent.$("#djyy").val();
            }
            if(parent.$("#djyy2").length > 0){
                djyy = parent.$("#djyy2").val();
            }
            var djyyMap = parent.authorityMapByDjyy;
            var changeColStr = djyyMap[djyy];
            if(changeColStr && changeColStr.length > 0){
                var changeColArr = changeColStr.split(",");
                setTimeout(function () {
                   for(var i=0;i<changeColArr.length;i++){
                       //设置可编辑
                       var $element = $("[name='" + changeColArr[i] + "']");
                       if ($element.length > 0) {
                           var tagName = $element[0].tagName;
                           $element.parent().removeClass("bdc-one-icon");
                           var img = $element.parent().find("img");
                           if (img.length > 0) {
                               $(img).remove();
                           }
                           $element.removeAttr("disabled");
                           if (tagName === "SELECT") {
                               form.render("select");
                               resetSelectDisabledCss();
                               // removeSelectDisabledCss();
                           }
                       }
                   }
                }, 500);
            }

        }
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
                var fwxz_text =$("#fwxz").find("option:selected").text();
                $("#fwxzmc").val(fwxz_text);
                //房屋性质非其他
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
 * 同步fdcq mj 到dyaq
 * @param data
 */
function syncFdcqMjToDyaq(data){
    if(data && data.slbh && data.bdcdyh){
        var slbh = data.slbh;
        var bdcdyh = data.bdcdyh.replace(/\s+/g, "");

        var dytdmjNum = 0;
        var fttdmjNum = 0;

        var dytdmj = data.dytdmj;
        if(dytdmj != "" && dytdmj != null){
            dytdmjNum = parseFloat(dytdmj);
        }

        var fttdmj = data.fttdmj;
        if(fttdmj != "" && fttdmj != null){
            fttdmjNum = parseFloat(fttdmj);
        }

        var tddymj = dytdmjNum + fttdmjNum;

        $.ajax({
            url: BASE_URL + '/qlxx/dyaq/updateDyaqMjByXmid?slbh=' + slbh + "&tddymj=" + tddymj+"&bdcdyh="+bdcdyh,
            type: "GET",
            dataType: "json",
            success: function (data) {
            }
        });
    }
}
