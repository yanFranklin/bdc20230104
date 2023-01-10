layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
//页面入口
var zdList;
var processInsId = getQueryString("processInsId");
var form;
var layer;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    layer = layui.layer;
    addModel();
    setTimeout("loadButtonArea('sftjd')", 10);
    setTimeout(function () {
        try {
            $.when(loadData()).done(function () {
                $.when(querySfxx()).done(function () {
                    removeModal();
                })
            })
        } catch (e) {
            removeModal();
            ERROR_CONFIRM("请求失败", e.message);
            return
        }
    }, 10);
    var isSubmit = true;
});

function loadBtn() {
    getStateById(readOnly, formStateId, "sftjd");
}

//按钮加载
function loadButtonArea() {
    layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
        laytpl(tpl).render({}, function (html) {
            view.innerHTML = html;
        });
        form.render();
        disabledAddFa();
        loadBtn();
        titleShowUiSftjd();
    });
}

function loadData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function querySfxx() {
    addModel();
    var url = getContextPath() + "/rest/v1.0/yczf/sftjdxx";
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        data: {gzlslid: processInsId},
        success: function (data) {
            if (data !== null) {
                //不存在权利人义务人收费信息默认展示权利人收费信息
                if (data.bdcQlrSftjxxDTO.bdcSftjDTOList != null && isNotBlank(data.bdcQlrSftjxxDTO.qlrlb)) {
                    generateSfxx(data.bdcQlrSftjxxDTO, data.bdcQlrSftjxxDTO.qlrlb);
                    generateSfxm(data.bdcQlrSftjxxDTO.bdcSftjDTOList, data.bdcQlrSftjxxDTO.qlrlb);
                }
                if (data.bdcYwrSftjxxDTO.bdcSftjDTOList != null && isNotBlank(data.bdcYwrSftjxxDTO.qlrlb)) {
                    generateSfxx(data.bdcYwrSftjxxDTO, data.bdcYwrSftjxxDTO.qlrlb);
                    generateSfxm(data.bdcYwrSftjxxDTO.bdcSftjDTOList, data.bdcYwrSftjxxDTO.qlrlb);
                }
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

function generateSfxx(bdcSftjxxDTO, qlrlb) {
    if (bdcSftjxxDTO !== null) {
        layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
            var form = layui.form;
            var laytpl = layui.laytpl;
            var json = {
                qlrlb: qlrlb,
                qlrmc: bdcSftjxxDTO.qlrmc,
                zjh: bdcSftjxxDTO.zjh,
                lxdh: bdcSftjxxDTO.lxdh,
            };
            var tpl, view;
            tpl = sfxxTpl.innerHTML;
            if (qlrlb === "1") {
                view = document.getElementById('qlrsfxx');
                $('#qlrsfxx').addClass('bdc-qlrsfxx-info');
            } else if (qlrlb === "2") {
                view = document.getElementById('ywrsfxx');
            }
            //渲染数据
            if (isNotBlank(view)) {
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
            }
            var ewmElem = '.ewm' + qlrlb;
            // 展现税费统缴二维码
            if (isNotBlank(bdcSftjxxDTO.bdcSlSfssDdxxDOList) && isNotBlank(bdcSftjxxDTO.bdcSlSfssDdxxDOList[0].jfurl)) {
                var url = "/realestate-accept-ui/sf/ewm?ewmnr=" + encodeURIComponent(bdcSftjxxDTO.bdcSlSfssDdxxDOList[0].jfurl);
                $('.sl-from').find(ewmElem).attr('src', url);
            }
            //点击二维码图片放大事件
            $(ewmElem).click(function () {
                var _this = $(this);//将当前的pimg元素作为_this传入函数
                imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
            });

            form.render();
            renderSelect();
            form.render('select');
            disabledAddFa();
            getStateById(readOnly, formStateId, "sftjd");
        })
    } else {
        if (qlrlb === "1") {
            $('#qlrsfxx').remove();
        } else if (qlrlb === "2") {
            $('#ywrsfxx').remove();
        }
    }
}

function generateSfxm(bdcSlSfxmDOList, qlrlb) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json;
        if (qlrlb === "1") {
            json = {
                bdcSlSfxmDOList: bdcSlSfxmDOList,
                qlrlb: qlrlb
            };
        } else {
            json = {
                bdcSlSfxmDOList: bdcSlSfxmDOList,
                zd: zdList,
                qlrlb: qlrlb
            };
        }
        var view;
        var tpl = sfxmTpl.innerHTML;
        if (qlrlb === "1") {
            view = document.getElementById('qlrsfxm');
        } else {
            view = document.getElementById('ywrsfxm');
        }
        //渲染数据
        if (isNotBlank(view)) {
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
        }
        loadButtonArea();
        form.render();
        form.render('select');
        renderSelect();
        disabledAddFa();
        countMoney('');
        removeModal();
    })
}

// 计算合计
function countMoney(elem) {
    var sfxxTable;
    if (elem) {
        sfxxTable = $(elem).parents('.bdcSfxxTable');
    } else {
        sfxxTable = $(".bdcSfxxTable");
    }
    sfxxTable.each(function () {
        var ysjeELArray = $(this).find("input[name='ysje']");
        if (ysjeELArray !== null && ysjeELArray.length > 0) {
            var ze = 0;
            for (var i = 0; i < ysjeELArray.length; i++) {
                var ysje = $(ysjeELArray[i]).val();
                if (ysje === "" || ysje === null) {
                    ysje = 0;
                }
                ze += calculateFloat(ysje);
            }
            ze = calculateFloat(ze);
            $(this).find("#hj").val(ze);
        }
    });
}

function convertSfztDmToMc(type, sfzt){
    if(isNotBlank(type) && isNotBlank(sfzt)){
        if(type == "税费"){
            if(sfzt == 1){
                return "已缴费";
            }else{
                return "未缴费"
            }
        }
        if(type == "登记费"){
            if(sfzt == 2){
                return "已缴费";
            }else{
                return "未缴费"
            }
        }
    }
    return '';
}


function titleShowUiSftjd() {
    $(".more-btn1").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#moreBtn1").show();
        setUiWidth($(this), $("#more-btn1"));
    });
    $(".more-btn1 li").click(function () {
        $("#moreBtn1").hide();
    });
    $(".more-btn2").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#moreBtn2").show();
        setUiWidth($(this), $("#more-btn2"));
    });
    $(".more-btn2 li").click(function () {
        $("#moreBtn2").hide();
    });
    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && (elem.id == 'print' || elem.id == 'moreBtn1' || elem.id == 'moreBtn2')) {
                return;
            }
            $("#print").hide();
            $("#moreBtn1").hide();
            $("#moreBtn2").hide();
            elem = elem.parentNode;
        }
    });
}
function setUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    uiElement.offset({top: X + 40, left: Y - 27});
    var uiWidth = 90;
    var liArray = uiElement.find("li");
    if (liArray.length > 0) {
        var textNumber = 0;
        for (var i = 0; i < liArray.length; i++) {
            var liEl = liArray[i];
            var liText = $(liEl).text();
            if (textNumber < liText.length) {
                textNumber = liText.length;
            }
        }
        var countWidth = (textNumber + 3) * 13;
        if (uiWidth < countWidth) {
            uiWidth = countWidth;
        }
    }
    uiElement.width(uiWidth);
}

//获取税费信息
function hqsfxx(qlrlb, nsrsbh) {
    console.log('获取税费信息');
    $("#htbh").val('');

    // 淮安新增数据归属地区
    $("#sjgsdq").empty();
    var sjgsdqSelect = '<option value="">请选择</option>';
    var sjgsdqArr = zdList['jddm'];
    if (isNotBlank(sjgsdqArr)) {
        $.each(sjgsdqArr, function (index, item) {
            sjgsdqSelect += '<option value="' + item.DM + '">' + item.MC + '</option>';
        });
    }
    $("#sjgsdq").append(sjgsdqSelect);
    form.render("select");

    var nsrsbhArr;
    if (isNotBlank(nsrsbh)) {
        nsrsbhArr = nsrsbh.split(',');
        nsrsbh = nsrsbhArr[0];
    }
    $("#nsrsbh").val(nsrsbh);
    var index = layer.open({
        type: 1,
        title: "获取税务申报信息",
        content: $('#popup'),
        area: ['1200px', '500px'],
        cancel: function () {
            // 关闭页面时移除点击事件
            $("#qureySfxxByHtbh").off('click');
            //取消
            layer.close(index);
            // 获取完银行税费后更新统缴单
            querySfxx();
        },
        success: function () {
            viewTableRender();
        }
    });
    $('#qureySfxxByHtbh').on('click', function () {
        var htbh = $("#htbh").val();
        // 淮安新增数据归属地区
        var sjgsdq = $("#sjgsdq").val();
        nsrsbh = $("#nsrsbh").val();
        if (isNullOrEmpty(htbh)) {
            ityzl_SHOW_INFO_LAYER("请输入合同编号!");
            return false;
        }
        addModel();
        $.ajax({
            type: "GET",
            url: getContextPath() + "/slym/sw/getYhsfxx",
            contentType: "application/json;charset=utf-8",
            data: {htbh: htbh, nsrsbh: nsrsbh, gzlslid: processInsId, qlrlb: qlrlb, sjgsdq: sjgsdq},
            success: function (data) {
                viewTableRender(data);
            }, error: function (e) {
                $("#qureySfxxByHtbh").off('click');
                delAjaxErrorMsg(e);
            }, complete: function () {
                removeModal();
            }
        });
    });
}

//契税税票查询
function hqsp(qlrlb) {
    // 小弹出层
    layer.open({
        title: '契税税票查询',
        type: 1,
        area: ['430px', '300px'],
        btn: ['查询'],
        skin: 'layui-layer-lan',
        content: $('#bdc-popup-qssp')
        , yes: function(index, layero){
            var htbh = $('#qssphtbh').val();
            var dzspNo = $('#dzspNo').val();
            var slfType = $('#slfType').val();
            if (!htbh) {
                layer.confirm("请输入合同编号", {title:'提示'}, function(index){
                    layer.close(index);
                });
                return;
            }
            if ('' === slfType || !slfType) {
                layer.confirm("请选择是否存量房", {title:'提示'}, function(index) {
                    layer.close(index);
                });
                return;
            }
            var wsxx = {gzlslid: processInsId, htbh: htbh, dzsphm: dzspNo, zlfclfbz: slfType};
            $('#qssphtbh').val('');
            $('#dzspNo').val('');
            $('#slfType').val('');
            form.render("select");
            $('#qssphtbh').focus();
            getReturnData("/slym/sjcl/list/swWsxx", JSON.stringify(wsxx),"POST",function (data) {
                if (data && data.length > 0) {
                    ityzl_SHOW_SUCCESS_LAYER("查询成功");
                } else {
                    ityzl_SHOW_WARN_LAYER("未查询到相关信息！");
                }
            },function (error) {
                delAjaxErrorMsg(error);
            })
        }
        , cancel: function () {
            $('#qssphtbh').val('');
            $('#dzspNo').val('');
            $('#slfType').val('');
            form.render("select");
        }
    });
}

//获取完税信息
function hqwsxx(qlrlb, nsrsbh) {

    // 淮安新增数据归属地区
    $("#hqwsxx-sjgsdq").empty();
    var sjgsdqSelect = '<option value="">请选择</option>';
    var sjgsdqArr = zdList['jddm'];
    if (isNotBlank(sjgsdqArr)) {
        $.each(sjgsdqArr, function (index, item) {
            sjgsdqSelect += '<option value="' + item.DM + '">' + item.MC + '</option>';
        });
    }
    $("#hqwsxx-sjgsdq").append(sjgsdqSelect);
    form.render("select");

    //小弹出层
    layer.open({
        title: '合同查询',
        type: 1,
        area: ['450px','350px'],
        btn: ['提交', '取消'],
        content: $('#bdc-popup-small')
        ,yes: function(index, layero){
            //提交 的回调
            var htbh = $('#wsxxhtbh').val(), nsrsbh = $('#wsxxnsrsbh').val();
            var sjgsdq = "";
            if(isNotBlank($("#hqwsxx-sjgsdq").val())){
                sjgsdq = $("#hqwsxx-sjgsdq").val();
            }
            addModel();
            $.ajax({
                type: "GET",
                url: getContextPath() + "/slym/sw/getQswspz?gxlx=0&beanName=sw_qswspzhq&gzlslid=" + processInsId + "&qlrlb=" + qlrlb
                    + "&htbh=" + htbh + "&nsrsbh=" + nsrsbh + "&sjgsdq=" + sjgsdq,
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    if(data){
                        var swxx =data;
                        if(!isNullOrEmpty(swxx.fcjyqswspzlist) &&!isNullOrEmpty(swxx.fcjyqswspzlist.fhxx)){
                            ityzl_SHOW_SUCCESS_LAYER(swxx.fcjyqswspzlist.fhxx);
                        }
                    }else{
                        ityzl_SHOW_WARN_LAYER("未查询到完税信息");
                    }
                }, error: function (e) {
                    delAjaxErrorMsg(e);
                }, complete: function () {
                    removeModal();
                }
            });
        }, success: function () {
            if(isNotBlank(nsrsbh) && nsrsbh.indexOf(",")> -1){
                nsrsbh = nsrsbh.split(",")[0];
            }
            $("#wsxxnsrsbh").val(nsrsbh);
        }
    });
}

//扫码下单
function smxd(qlrlb) {
    addModel();
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/yczf/sczfdd?lx=1&qlrlb=" + qlrlb + "&gzlslid=" + processInsId,
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            if(isNotBlank(data)){
                ityzl_SHOW_SUCCESS_LAYER("扫码下单成功");
                // var url = "/realestate-accept-ui/sf/ewm?ewmnr=" + encodeURIComponent(data.payUrl);
                // $('.sl-from').find('.ewm').attr('src', url);
                querySfxx();
            }else{
                ityzl_SHOW_WARN_LAYER("扫码下单失败");
            }

        }, error: function (e) {
            delAjaxErrorMsg(e);
        }, complete: function () {
            removeModal();
        }
    });
}

//POS下单
function posxd(qlrlb) {
    addModel();
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/yczf/sczfdd?lx=2&qlrlb=" + qlrlb + "&gzlslid=" + processInsId,
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (data && isNotBlank(data.payOrderNo)) {
                ityzl_SHOW_SUCCESS_LAYER("POS下单成功");
            }else{
                ityzl_SHOW_WARN_LAYER("POS下单失败");
            }
        }, error: function (e) {
            delAjaxErrorMsg(e);
        }, complete: function () {
            removeModal();
        }
    });
}

//推送缴款码
function tsjkm(qlrlb) {
    addModel("推送中");
    getReturnData("/rest/v1.0/yczf/tsjkm", {gzlslid: processInsId, qlrlb: qlrlb}, "GET", function (data) {
        removeModal();
        if (data && data.code == "1") {
            ityzl_SHOW_SUCCESS_LAYER("推送成功");
        } else {
            ityzl_SHOW_WARN_LAYER("推送失败" + data.message);
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    });
}

//查询缴费情况
function cxjfqk(qlrlb) {
    addModel();
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/yczf/cxjfjg?gzlslid=" + processInsId +"&qlrlb=" + qlrlb,
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            if(isNotBlank(data) && data.success == true){
                var msg = "查询缴费情况成功，缴费状态：";
                if(data.data == 2){
                    msg += "已缴费。";
                }else{
                    msg += "未缴费。";
                }
                ityzl_SHOW_SUCCESS_LAYER(msg);
            }else{
                ityzl_SHOW_WARN_LAYER("查询缴费情况失败。");
            }
            // 更新统缴单
            querySfxx();
        }, error: function (e) {
            delAjaxErrorMsg(e);
        }, complete: function () {
            removeModal();
        }
    });
}

//POS支付成功推送
function poszfcg(qlrlb) {
    addModel();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/yczf/pos/tzzr",
        type: 'POST',
        dataType: 'json',
        data: {gzlslid: processInsId, qlrlb: qlrlb},
        success: function (data) {
            removeModal();
            if(isNotBlank(data) && data.success==true){
                ityzl_SHOW_SUCCESS_LAYER("通知政融支付平台成功");
            }else{
                ityzl_SHOW_WARN_LAYER("通知政融支付平台失败");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

//打印
function printSftjd(qlrlb) {
    var ewmElem = '.ewm' + qlrlb;
    var jfurl = $('.sl-from').find(ewmElem).attr('src');
    if (!isNotBlank(jfurl)) {
        ityzl_SHOW_WARN_LAYER("未生成缴费二维码，请先生成缴费二维码！");
        return false;
    }
    addModel();
    printSfd("yczfjktzs", qlrlb);
    removeModal();
}

//银行卡支付 POS机
function yhkzf(qlrlb) {
    console.log('银行卡支付');
    /**
     * 建总行商场MIS接口 5.1 消费交易
     */
    addModel();
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/yczf/pos/yhkzf" + "?gzlslid=" + processInsId + "&qlrlb=" + qlrlb,
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (isNotBlank(data) && isNotBlank(data.input)) {
                var input = data.input;
                var output = callKeeperClient(input);
                if (isNotBlank(output)) {
                    var outputArr = output.split('|');
                    // 00表示交易成功
                    if (outputArr[1] == '00') {
                        ityzl_SHOW_SUCCESS_LAYER(outputArr[2]);
                        // 保存POS交易成功信息
                        saveJyxx(output, qlrlb);
                        // POS支付成功推送
                        poszfcg(qlrlb);
                    } else {
                        ityzl_SHOW_WARN_LAYER(outputArr[2]);
                    }
                } else {
                    ityzl_SHOW_WARN_LAYER("POS交易异常！");
                }
            } else {
                ityzl_SHOW_WARN_LAYER("未获取到POS消费交易参数！");
            }
        }, error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }, complete: function () {
            removeModal();
        }
    });
}

//付款码支付 POS机
function fkmzf(qlrlb) {
    console.log('付款码支付');
    // 采集付款码
    $("#fkm").val('');
    layer.open({
        title: '请扫描或输入付款码，扫码完成后点击确认',
        type: 1,
        area: ['430px'],
        btn: ['确认','取消'],
        content: $('#fkm-div'),
        yes: function () {
            var fkm = $("#fkm").val();
            if (isNullOrEmpty(fkm)) {
                ityzl_SHOW_INFO_LAYER("请输入付款码!");
                return false;
            }
            /**
             * 建总行商场MIS接口 5.8 广开聚合支付被扫交易
             */
            addModel();
            $.ajax({
                type: "POST",
                url: getContextPath() + "/rest/v1.0/yczf/pos/fkmzf" + "?gzlslid=" + processInsId + "&qlrlb=" + qlrlb + "&fkm=" + fkm,
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    if (data && isNotBlank(data.input)) {
                        var input = data.input;
                        var output = callKeeperClient(input);
                        if (isNotBlank(output)) {
                            var outputArr = output.split('|');
                            // 00表示交易成功
                            if (outputArr[1] == '00') {
                                ityzl_SHOW_SUCCESS_LAYER(outputArr[2]);
                                // 保存POS交易成功信息
                                saveJyxx(output, qlrlb);
                                // POS支付成功推送
                                poszfcg(qlrlb);
                            } else {
                                ityzl_SHOW_WARN_LAYER(outputArr[2]);
                            }
                        } else {
                            ityzl_SHOW_WARN_LAYER("POS交易异常！");
                        }
                    } else {
                        ityzl_SHOW_WARN_LAYER("未获取到POS消费交易参数！");
                    }
                }, error: function (e) {
                    delAjaxErrorMsg(e);
                }, complete: function () {
                    removeModal();
                }
            });
            return false;
        },
        btn2: function (index, layero) {
            layer.close(index);
        },
        cancel: function (index, layero) {
            layer.close(index);
        }
    });
    return false;
}

//银行卡撤销 POS机
function yhkcx(qlrlb) {
    console.log('银行卡撤销');
    /**
     * 建总行商场MIS接口 5.2 当日撤销交易
     */
    addModel();
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/yczf/pos/yhkcx" + "?gzlslid=" + processInsId + "&qlrlb=" + qlrlb,
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (isNotBlank(data) && isNotBlank(data.input)) {
                var input = data.input;
                var output = callKeeperClient(input);
                console.log('银行卡撤销', output);
            } else {
                ityzl_SHOW_WARN_LAYER("未获取到POS银行卡撤销参数！");
            }
        }, error: function (e) {
            delAjaxErrorMsg(e);
        }, complete: function () {
            removeModal();
        }
    });
}

//银行卡退款 POS机
function yhktk(qlrlb) {
    console.log('银行卡退款');
    /**
     * 建总行商场MIS接口 5.3 退货交易
     */
    addModel();
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/yczf/pos/yhktk" + "?gzlslid=" + processInsId + "&qlrlb=" + qlrlb,
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (isNotBlank(data) && isNotBlank(data.input)) {
                var input = data.input;
                var output = callKeeperClient(input);
                console.log('银行卡退款', output);
            } else {
                ityzl_SHOW_WARN_LAYER("未获取到POS银行卡退款参数！");
            }
        }, error: function (e) {
            delAjaxErrorMsg(e);
        }, complete: function () {
            removeModal();
        }
    });
}

//付款码退款 POS机
function fkmtk(qlrlb) {
    console.log('付款码退款');
    /**
     * 建总行商场MIS接口 5.10 广开聚合支付退货交易
     */
    addModel();
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/yczf/pos/fkmtk" + "?gzlslid=" + processInsId + "&qlrlb=" + qlrlb,
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (isNotBlank(data) && isNotBlank(data.input)) {
                var input = data.input;
                var output = callKeeperClient(input);
                console.log('付款码退款', output);
            } else {
                ityzl_SHOW_WARN_LAYER("未获取到POS付款码退款参数！");
            }
        }, error: function (e) {
            delAjaxErrorMsg(e);
        }, complete: function () {
            removeModal();
        }
    });
}

//重打小票
function cdxp(qlrlb) {
    console.log('重打小票');
    // 获取重打印交易参数
    addModel();
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/yczf/pos/cdxp" + "?gzlslid=" + processInsId + "&qlrlb=" + qlrlb,
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (isNotBlank(data) && isNotBlank(data.input)) {
                var input = data.input;
                var output = callKeeperClient(input);
                console.log('重打小票', output);
            } else {
                ityzl_SHOW_WARN_LAYER("未获取到POS重打小票参数！");
            }
        }, error: function (e) {
            delAjaxErrorMsg(e);
        }, complete: function () {
            removeModal();
        }
    });
}

//保存交易信息
function saveJyxx(output, qlrlb) {
    output = encodeURI(output);
    addModel();
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/yczf/pos/savejyxx?output=" + output + "&gzlslid=" + processInsId + "&qlrlb=" + qlrlb,
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (data) {
                querySfxx();
            }
        }, error: function (e) {
            delAjaxErrorMsg(e);
        }, complete: function () {
            removeModal();
        }
    });
}

// 调用pos机
function callKeeperClient(input) {
    var output = "";
    try {
        output = KeeperClient.misposTrans(input,"1,0,0,2");
    } catch (e) {
        removeModal();
        ityzl_SHOW_WARN_LAYER("调用pos机异常！");
    }
    return output;
}

function viewTableRender(swdata) {
    if (isNullOrEmpty(swdata)) {
        swdata = [];
    }
    layui.table.render({
        elem: '#viewTable',
        title: '',
        defaultToolbar: [],
        cols: [[
            {field: 'xh', type: 'numbers', title: '序号'},
            {field: 'nsrmc', title: '纳税人名称', minWidth: 100},
            {field: 'nsrsbh', title: '纳税人识别号', minWidth: 100},
            {field: 'dzsphm', title: '电子税票号码', minWidth: 120},
            {field: 'ybtse', title: '应补退税额', minWidth: 50},
            {field: 'skssjgdm', title: '税款所属机构', minWidth: 100},
            {field: 'kkfhxx', title: '扣款返回信息', minWidth: 100},
            {field: 'zrfcsfbz', title: '转让方承受方标志', minWidth: 100, fixed: 'right'},
        ]],
        data: swdata,
        page: false,
    });
}

function imgShow(outerdiv, innerdiv, bigimg, _this) {
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性

    /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function () {
        var windowW = $(window).width();//获取当前窗口宽度
        var windowH = $(window).height();//获取当前窗口高度
        var realWidth = this.width;//获取图片真实宽度
        var realHeight = this.height;//获取图片真实高度
        var imgWidth, imgHeight;
        imgWidth  = windowH-100;
        $(bigimg).css("width", imgWidth);//以最终的宽度对图片缩放

        var w = (windowW - imgWidth) / 2;//计算图片与窗口左边距
        var h = (windowH - imgWidth) / 2;//计算图片与窗口上边距
        var topHeight = $(".bdc-form-div").css("padding-top");
        $(innerdiv).css({"top": topHeight, "left": w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function () {//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}

/**
 * 处理小数计算问题，四舍五入保留两位小数
 * 需要引用math.js
 * @param equation  计算公式
 */
function calculateFloat(equation) {
    var result = math.format(math.evaluate(equation), 14);
    return formatFloat(parseFloat(result));
}

//处理小数问题
function formatFloat(f) {
    if((f + '').indexOf('.') != -1){
        var d, carryD, i,
            ds = f.toString().split('.'),
            len = ds[1].length,
            b0 = '', k = 0;

        if (len > 2) {
            while(k < 2 && ds[1].substring(0, ++k) == '0') {
                b0 += '0';
            }
            d = Number(ds[1].substring(0, 2));
            // 判断保留位数后一位是否需要进1
            carryD = Math.round(Number('0.' + ds[1].substring(2, len)));
            len = (d + carryD).toString().length;
            if (len > 2) {
                return Number(ds[0]) + 1;
            } else if (len == 2) {
                return Number(ds[0] + '.' + (d + carryD));
            }
            return Number(ds[0] + '.' + b0 + (d + carryD));
        }
    }
    return f;
}