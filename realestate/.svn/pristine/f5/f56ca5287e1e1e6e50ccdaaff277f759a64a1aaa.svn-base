// 控制上下标题显示与否：true 上面隐藏、下面显示 ；false 上面显示  下面隐藏； 无 都显示
var isCrossOri = $.getUrlParam("isCrossOri");
// 控制权利页面展示修改记录
var showXgLog = $.getUrlParam("showXgLog")
// layui元素
var element, form, layer, table, laytpl, $;

//点击提交时为空的全部标红
var isSubmit = true;
var verifyMsg = "必填项不能为空!";
// 是否虚拟不动产单元
var isXndyh;
// 显示保存成功的提示
var showSaveSuccessMsg = true;
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
                            var emptyIndex = layer.open({
                                type: 1,
                                skin: 'bdc-small-tips',
                                title: '确认清空',
                                area: ['320px'],
                                content: '您确认要清空吗？',
                                btn: ['确定', '取消'],
                                btnAlign: 'c',
                                yes: function (index) {
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
                                    layer.close(emptyIndex);
                                },
                                btn2: function () {
                                    //取消
                                    layer.close(emptyIndex);
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
                    // 预告登记根据预告登记种类隐藏是否转让
                    if(!isNullOrEmpty(data.ygdjzl) && data.ygdjzl != 3 && data.ygdjzl != 4 ) {
                        $('#jzzr').parents('td').prev('td').css("display", "none");
                        $('#jzzr').parents('td').css("display", "none");

                        $('#dbfw').parents('td').attr('colspan', '9');
                    }
                    //房屋性质名称根据房屋性质是否选择其他控制是否不可编辑
                    renderFwxzmc(data.fwxz);

                    //房屋结构名称根据房屋结构是否选择其他控制是否不可编辑
                    renderFwjgmc(data.fwjg);

                    //用途名称根据房屋性质是否选择其他控制是否不可编辑
                    renderYtmc(data.ghyt);
                    // 获取项目信息
                    getXmxx(xmid);

                    var sfzs = document.getElementById("jzqjssj");
                    if (sfzs !== null && sfzs != undefined){
                        if (data.zsjz===1){
                            sfzs.setAttribute("disabled",true);
                            sfzs.setAttribute("readOnly",true);
                            $("#jzqjssj").val("");
                        }else {
                            sfzs.removeAttribute("readOnly");
                            sfzs.removeAttribute("disabled");
                        }
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
                return data;
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

    /**
     * 房屋性质下拉框联动控制房屋性质名称是否可编辑
     */
    form.on('select(fwxz)', function (data) {
        renderFwxzmc(data.value);

    });

    /**
     * 房屋结构下拉框联动控制房屋结构名称是否可编辑
     */
    form.on('select(fwjg)', function (data) {
        renderFwjgmc(data.value);

    });

    /**
     * 项目内多幢房屋结构下拉框联动控制房屋结构名称是否可编辑
     */
    form.on('select(fdcqxm-fwjg)', function (data) {
        renderFdcqxmFwjgmc(data.value,$(data.elem));

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
                if (isInZhym) {
                    window.parent.qlxxIsYzEnd('wtg');
                }
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

                if(isInZhym){
                    window.parent.qlxxIsYzEnd('tg');
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
     * 处理并验证数据
     * @param data
     * @returns {*}
     */
    window.dealAndCheckData = function (data) {
        // 终生居住为是时,无需填写居住期限，否则居住期限必填
        if (!isNullOrEmpty(data.field.zsjz)) {
            var zsjz = $('#zsjz').val();
            var jzqkssj = $('#jzqkssj').val();
            var jzqjssj = $('#jzqjssj').val();

            checkJzqsj(zsjz, jzqkssj, jzqjssj);
        }

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
        if (!isNullOrEmpty(data.field.szc) && !isNullOrEmpty(data.field.zcs)) {
            zcs = parseInt(data.field.zcs) * 1;
            if (szc > zcs) {
                isSubmit = false;
                verifyMsg = "所在层的值不能大于总层数的值！";
                $("#szc").addClass('layui-form-danger');
                $("#zcs").addClass('layui-form-danger');
            }
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
                $(fwxzmcElement).attr('lay-verify', 'required');
            } else {
                var fwxz_text =$("#fwxz").find("option:selected").text();
                $("#fwxzmc").val(fwxz_text);
                //房屋性质非其他
                fwxzmcElement.setAttribute("disabled", "off");
            }
        }

    };

    /**
     * 根据房屋结构渲染房屋结构名称
     */
    window.renderFwjgmc = function (fwjgDm){
        var fwjgmcElement =document.getElementById("fwjgmc");
        if(fwjgmcElement != null) {
            if (fwjgDm == 6) {
                //房屋结构选择其他
                fwjgmcElement.removeAttribute("disabled", "off");
                $(fwjgmcElement).attr('lay-verify', 'required');
            } else {
                var fwjgmc_text =$("#fwjg").find("option:selected").text();
                $("#fwjgmc").val(fwjgmc_text);
                //房屋结构选择非其他
                fwjgmcElement.setAttribute("disabled", "off");
            }
        }

    };

    /**
     * 根据房屋结构渲染房屋结构名称(项目内多幢)
     */
    window.renderFdcqxmFwjgmc = function (fwjgDm,$fwjg){
        var $fwjgmcElement =$fwjg.parents("tr").find(".fwjgmc");
        if($fwjgmcElement != null && $fwjgmcElement.length > 0) {
            if (fwjgDm == 6) {
                //房屋结构选择其他
                $fwjgmcElement.removeAttr("disabled", "off");
                $fwjgmcElement.val('')
            } else {
                //房屋结构选择非其他
                var fwhgmc_text = $fwjg.find("option:selected").text();
                $fwjgmcElement.val(fwhgmc_text);
                $fwjgmcElement.attr("disabled", "off");
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
                $(ytmcElement).attr('lay-verify', 'required');
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

/**
 * 终生居住为是时,无需填写居住期限，否则居住期限必填
 */
function checkJzqsj(zsjz, jzqkssj, jzqjssj) {
    if (parseInt(zsjz) === 1) {
        if (isNotBlank(jzqjssj) || isNotBlank(jzqkssj)) {
            verifyMsg = "选择终生居住,无需填写居住权起始时间和居住权结束时间！";
            $("#jzqkssj").addClass('layui-form-danger');
            $("#jzqjssj").addClass('layui-form-danger');
            isSubmit = false;
        }
    } else if (parseInt(zsjz) === 0) {
        if (!isNotBlank(jzqjssj)) {
            verifyMsg = "请填写居住权结束时间！";
            $("#jzqjssj").addClass('layui-form-danger');
            isSubmit = false;
        }
    }
}

function addXgLog(processInsId){
    if(showXgLog){
        var tsxx = $("#updateTips p").text();
        if (isNotBlank(tsxx)) {
            var bdcXxXgDTO = {};
            bdcXxXgDTO.tsxx = tsxx;
            var bdcXxXgZbDTOList = [];
            $(".bdc-change-input").each(function (i) {
                var bdcXxXgZbDTO = {};
                var $change = $(this);
                if ($(this).hasClass("layui-input-inline")) {
                    if ($(this).find("select").length > 0) {
                        $change = $(this).find("select");
                    } else if ($(this).find("textarea").length > 0) {
                        $change = $(this).find("textarea");
                    } else {
                        $change = $(this).find("input");
                    }
                }
                var value = $change.val();
                var name = $change.attr('name');
                bdcXxXgZbDTO.value = value;
                bdcXxXgZbDTO.name = name;
                bdcXxXgZbDTOList.push(bdcXxXgZbDTO);
            });
            bdcXxXgDTO.bdcXxXgZbDTOList = bdcXxXgZbDTOList;
            getReturnData("/rest/v1.0/blxx/addXgLog?gzlslid=" + processInsId, JSON.stringify(bdcXxXgDTO), "POST", function () {

            }, function (error) {
                delAjaxErrorMsg(error);


            })
        }
    }
}

function queryXgLog(processInsId){
    if(showXgLog){
        getReturnData("/rest/v1.0/blxx/queryXgLog", {gzlslid: processInsId}, "GET", function (data) {
            if (data && data.value) {
                var modifyData = JSON.parse(data.value).bdcXxXgZbDTOList;
                for (var i = 0; i < modifyData.length; i++) {
                    var name = modifyData[i].name;
                    if ($('input[name="' + name + '"]').length > 0) {
                        $('input[name="' + name + '"]').parent().addClass('bdc-change-input');
                        $('input[name="' + name + '"]').css({"background-color": "#eaf4fe"})
                    } else if ($('select[name="' + name + '"]').length > 0) {
                        $('select[name="' + name + '"]').parent().addClass('bdc-change-input');
                        $('select[name="' + name + '"]').css({"background-color": "#eaf4fe"})
                    } else if ($('textarea[name="' + name + '"]').length > 0) {
                        $('textarea[name="' + name + '"]').parent().addClass('bdc-change-input');
                        $('textarea[name="' + name + '"]').css({"background-color": "#eaf4fe"})
                    }

                }
                renderChangeTips(JSON.parse(data.value).tsxx);
            }
        }, function (error) {

        })
    }
}

/**
 * text 修改字段名称
 * 修改字段时同步显示下方提示
 */
function renderChangeTips(text) {
    if (isNotBlank(text)) {
        text = text.replace("*", "");
        text = " "+text+" ";
        //显示下方提示
        //$(".bdc-update-tips-box").removeClass("bdc-hide");
        //查询提示框内容，判断是否包含修改字段，不包含则增加
        //添加受理页面中打开的子页面中修改了内容，在父页面中增加提示展示
        var tiptext = "";
        if($("#updateTips p").length != 0){
            tiptext = $("#updateTips p").text();
        }else{ // 当为获取到更新tips的内容时，采用调用父页面dom来获取。
            tiptext = parent.$("#updateTips p").text();
        }
        var textArr = text.split(" ");
        textArr.forEach(function(v){
            if(tiptext.indexOf(v) < 0){
                var addText = " " + v;
                if($("#updateTips p").length != 0){
                    $("#updateTips p").append(addText);
                }else{
                    parent.$("#updateTips p").append(addText);
                }
            }
        });
        //关闭提示
        $('.bdc-update-tips-box .bdc-close').on('click', function () {
            $('.bdc-update-tips-box').addClass('bdc-hide');
        });
    }

}

function renderChange(formSelects, form, formIds) {
    $.each(formIds.split(","), function (index, formID) {
        if (isNotBlank(formID)) {
            var $input = $("#" + formID).find(".layui-table-edit");
            //监听input修改
            $input.on('change', function () {
                $(this).parent().addClass('bdc-change-input');
                var text =$(this).parent().parent().prev().html();
                renderChangeTips(text);
            });
            var $textarea = $("#" + formID).find(".change-textarea-width");
            //监听textarea修改
            $textarea.on('change', function () {
                $(this).parent().addClass('bdc-change-input');
                var text = $(this).parent().parent().prev().html();
                renderChangeTips(text);
            });
        }
    });

    //监听select下拉框内容修改
    form.on('select', function (data) {
        $.each(formIds.split(","), function (index, formID) {
            var $select = $("#" + formID).find(data.othis).find("input");
            if ($select.length > 0) {
                $(data.elem.parentElement).addClass('bdc-change-input');
                var text = $(data.elem.parentElement).prev().html();
                renderChangeTips(text);
            }
        });
    });
}



function renderDate(form, formIds) {
    var laydate = layui.laydate;
    var format = 'yyyy-MM-dd';
    lay('.date').each(function () {
        if (isNotBlank(this.value)) {
            this.value = Format(this.value, format);
        }
        laydate.render({
            elem: this,
            format: format,
            trigger: 'click'
            , done: function (value, date, endDate) {
                //监听修改
                $(this.elem).parent().addClass('bdc-change-input');
                //var text = $($date).parents(".layui-inline").find("label").text();
                var text = $(this.elem).parent().parent().prev().text();
                renderChangeTips(text);
            }
        });
    });

}

