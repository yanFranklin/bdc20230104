/**
 * 证明JS代码
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
// 查询参数
var zsid = $.getUrlParam("zsid");

// 查询参数
var processInsId = $.getUrlParam("processInsId");
// 表单ID
var formStateId = $.getUrlParam('formStateId');
// 获取表单权限参数readOnly
var readOnly = $.getUrlParam('readOnly');

var zslx;
var qxdm;
// 当前证书的已保存的印刷序列号
var zsQzysxlh;
var qszt;

// loading加载
var loadIndex;

layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer', 'util', 'response'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;
    var util = layui.util;
    var response = layui.response;

    $(function () {

        // loading加载
        loadIndex = layer.load(2);

        var screenH = document.documentElement.clientHeight;
        $(".content-main").css({'min-height': screenH - 70});

        // 滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-nav-container');

        function defaultStyle() {
            if ($cnotentTitle.length == 1) {
                $('.bdc-form-div').css('padding-top', '84px');
            }
            if ($(window).scrollTop() > 85) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '69px');
            } else if ($(window).scrollTop() <= 85) {
                $cnotentTitle.css('top', '15px');
                $navContainer.css('top', '84px');
            }
        }

        $(window).resize(function () {
            defaultStyle();
        });
        $(window).on('scroll', function () {
            if ($(this).scrollTop() > 85) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '69px');
            } else if ($(this).scrollTop() <= 85) {
                $cnotentTitle.css('top', '15px');
                $navContainer.css('top', '84px');
            }
        });

        // 正式生成证书时，后台查询证书信息展示
        renderZsData();
        // 获取缴费状态
        getJfzt(processInsId, zsid);

        // 点击打印按钮时是否要验证缴费状态
        needCheckJfzt(processInsId);

        function needCheckJfzt(processInsId){
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/needCheckJfzt",
                contentType: "application/json;charset=utf-8",
                type: "GET",
                dataType: "json",
                data: {gzlslid : processInsId},
                success: function (data){
                    if(data){
                        $('#needCheckJfzt').html('true');
                    }else {
                        $('#needCheckJfzt').html('false');
                    }
                },
                error:function (e) {
                    warnMsg("判断打印按钮是否需要验证缴费状态失败！");
                    layer.close(loadIndex);
                }
            })
        }

        //获取缴费状态
        function getJfzt(processInsId, zsid){
            querySfzt(processInsId, zsid).done(function(data){
                if(!data.sfzt){
                    warnMsg("未缴费！");
                }
            }).fail(function(){
                layer.close(loadIndex);
            });
        }

        /**
         * 正式生成证书时，后台查询证书信息展示
         */
        function renderZsData() {
            var url;
            if (isNullOrEmpty(zsid)) {
                url = "/realestate-register-ui/rest/v1.0/zsxx/" + processInsId + "/single";
            } else {
                url = "/realestate-register-ui/rest/v1.0/zsxx/" + zsid;
            }
            //获取数据
            $.ajax({
                url: url,
                contentType: "application/json;charset=utf-8",
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if (data) {
                        data.fzsj = util.toDateString(data.fzsj, 'yyyy年MM月dd日');
                        zslx = data.zslx;
                        qxdm = data.qxdm;
                        zsQzysxlh = data.qzysxlh;
                        queryQzysxlh = zsQzysxlh;
                        zsid = data.zsid;
                        qszt = data.qszt;

                        form.val('form', data);
                        $("#szrq").html(data.szrq);
                        $("#qzysxlh").html("编号NO." + data.qzysxlh);
                        $("#zl").html(data.zl);

                        // 生成二维码
                        generateQrCode(data.ewmnr);
                        // 初始化页面设置工作按钮状态
                        setZsBtnAttr(qszt, zsQzysxlh);
                        //设置权限控制样式
                        if (readOnly || !isNullOrEmpty(formStateId)) {
                            getStateById(readOnly, formStateId, "bdcZm");
                            if ((true == readOnly || "true" == readOnly) && (true == printValue || "true" == printValue)) {
                                // 设置补打时按钮的状态
                                setZsPrintState();
                                setZsBtnAttr(qszt, zsQzysxlh);
                            }
                        }
                    }
                    // 关闭加载
                    layer.close(loadIndex);
                }, error: function () {
                    warnMsg("获取数据错误！");
                    layer.close(loadIndex);
                }
            });
        }

        //监听提交
        form.on('submit(submitBtn)', function (data) {
            // loading加载
            addModel();

            var param = {};
            param["zsid"] = zsid;
            param["qlqtzk"] = data.field.qlqtzk;
            param["fj"] = data.field.fj;

            // 更新权利其他状况和附记
            updateZsQlqtzkAndFj(param);

            // 获取页面的印刷序列号
            var qzysxlh = data.field.qzysxlh;
            // 页面的印制号和当前证书已有的印制号一致时，不进行二次验证提交
            if (null != qzysxlh && '' != qzysxlh && qzysxlh != zsQzysxlh) {
                // 生成缮证信息
                var zsidArr = [];
                zsidArr.push(zsid);
                saveSzr(zsidArr, '');

                // 定义印制号的获取前置条件(在获取印制号的时候给初始值)
                var bdcYzhQO = {};
                bdcYzhQO["zslx"] = zslx;
                bdcYzhQO["syqk"] = yly;
                bdcYzhQO["qxdm"] = qxdm;
                bdcYzhQO["zsid"] = zsid;
                // 验证qzysxlh是否可用
                bdcYzhQO["qzysxlh"] = qzysxlh.trim();
                var result = checkYzhSyqk(bdcYzhQO);
                if (result) {
                    // 印制号验证通过，修改证书印制号值
                    zsQzysxlh = qzysxlh;
                    setZsBtnAttr(qszt, zsQzysxlh);
                }

                // 南通需求要求获取印制号后，文本框可编辑，这里移除disabled属性
                setQzysxlhAable();
            }

            // 关闭加载
            removeModel();
            // 禁止提交后刷新
            return false;
        });

        //监听证书预览按钮
        $(document).on('click', '#openZmView', function () {
            layer.open({
                title: ['证明预览', 'font-size:16px;font-weight: 700;'],
                type: 2,
                area: ['100%', '100%'],
                resize: true,
                closeBtn: 1,
                content: '/realestate-register-ui/view/zsxx/zmView.html?zsid=' + zsid,
                scrollbar: false,
                btn: '打印',
                yes: function (index, layero) {
                    if (qszt != qsztValid) {
                        warnMsg("证书未登簿或已注销，不予打印！");
                        return false;
                    }
                    // 获取页面的印刷序列号
                    var qzysxlh = $("#qzysxlh").val();
                    if (null == qzysxlh || isNullOrEmpty(qzysxlh)) {
                        warnMsg("印制号为空，不允许打印!");
                        return false;
                    }

                    var needCheckJfzt = $("#needCheckJfzt").html();
                    if(needCheckJfzt == ''){
                        warnMsg("打印按钮缴费验证判断值为空！");
                        return false;
                    }
                    if(needCheckJfzt === 'true'){
                        zszmDyYz(processInsId, function (data) {
                            if (data.length > 0) {
                                removeModel();
                                warnMsg(data[0].msg);
                                return false;
                            } else {
                                querySfzt(processInsId, zsid).done(function(data){
                                    if(!data.sfzt){
                                        warnMsg("未缴费不能打印证明！");
                                        return false;
                                    }
                                    if(!data.dyzt){
                                        warnMsg("发票号和备注信息为空，不能打印证明！");
                                        return false;
                                    }
                                    printZm();
                                }).fail(function(){
                                    layer.close(loadIndex);
                                });
                            }
                        }, function (err) {
                        });
                    }else{
                        printZm();
                    }
                },
                btnAlign: 'c'
            });
            // 禁止提交后刷新
            return false;
        });


        // 证书打印
        $(document).on('click', '#printBtn', function () {
            //验证印制号对应的领用人的账号
            var bdcYzhQO = {};
            bdcYzhQO["zslx"] = zslx;
            bdcYzhQO["syqk"] = ysy;
            bdcYzhQO["qxdm"] = qxdm;
            bdcYzhQO["zsid"] = zsid;
            bdcYzhQO["qzysxlh"] = $("#qzysxlh").val();
            var result = printCheckYzhSyqk(bdcYzhQO);
            if (!result) {
                warnMsg("当前用户非印制号领用用户！");
                return false;
            }
            //
            var needCheckJfzt = $("#needCheckJfzt").html();
            if(needCheckJfzt == ''){
                warnMsg("打印按钮缴费验证判断值为空！");
                return false;
            }
            if(needCheckJfzt === 'true'){
                zszmDyYz(processInsId, function (data) {
                    if (data.length > 0) {
                        removeModel();
                        warnMsg(data[0].msg);
                        return false;
                    } else {
                        querySfzt(processInsId, zsid).done(function(data){
                            if(!data.sfzt){
                                warnMsg("未缴费不能打印证明！");
                                return false;
                            }
                            if(!data.dyzt){
                                warnMsg("发票号和备注信息为空，不能打印证明！");
                                return false;
                            }
                            printZm();
                        }).fail(function(){
                            layer.close(loadIndex);
                        });
                    }
                }, function (err) {
                });
            }else{
                printZm();
            }
            // 禁止提交后刷新
            return false;
        });

        /**
         * 获取印制号
         */
        $(document).on('click', '#getYzh', function () {
            // a标签的样式是不可用时,不执行后面的事件
            if ($("#getYzh").hasClass("layui-btn-disabled")) {
                return false;
            }
            // loading加载
            addModel();

            var bdcYzhQO = {};
            bdcYzhQO.zslx = zslx;
            bdcYzhQO.syqk = yly;
            bdcYzhQO.qxdm = qxdm;
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/yzh/" + zsid,
                type: "POST",
                data: JSON.stringify(bdcYzhQO),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    if (data && data.code) {
                        warnMsg(data.msg);
                        return false;
                    }
                    if (data && data.bdcYzhsymxDOList.length > 0) {
                        $("#qzysxlh").val(data.qzysxlh);
                        // 获取成功后，同时更新全局变量
                        zsQzysxlh = data.qzysxlh;
                        getQzysxlh = zsQzysxlh;
                        getYzhid = data.yzhid;
                        // 更新按钮状态
                        setZsBtnAttr(qszt, zsQzysxlh);

                        // 生成缮证人信息
                        var zsidArr = [];
                        zsidArr.push(zsid);
                        saveSzr(zsidArr, '');

                        successMsg("获取成功！");
                    } else {
                        warnMsg("没有可用的印制号！");
                    }
                    // 南通需求要求获取印制号后，文本框可编辑，这里移除disabled属性
                    setQzysxlhAable();
                    removeModel();
                    // 关闭加载
                }, error: function () {
                    warnMsg("获取失败，请重试！");
                    removeModel();
                }
            });
            // 禁止刷新页面
            return false;
        });

        //作废印制号
        $(document).on('click', '#zfYzh', function () {
            // a标签的样式是不可用时,不执行后面的事件
            if ($("#zfYzh").hasClass("layui-btn-disabled")) {
                return false;
            }
            // 获取页面的印刷序列号
            var qzysxlh = $("#qzysxlh").val();
            // 页面的印制号和当前证书已有的印制号一致时，不进行二次验证提交
            if (null != qzysxlh && '' != qzysxlh) {
                var bdcYzhQO = {};
                bdcYzhQO["zslx"] = zslx;
                bdcYzhQO["qxdm"] = qxdm;
                bdcYzhQO["zsid"] = zsid;
                bdcYzhQO["qzysxlh"] = qzysxlh;

                var submitIndex = layer.open({
                    type: 1,
                    title: '确认作废',
                    area: ['320px'],
                    skin: 'bdc-small-tips',
                    content: '确定作废当前印制号?',
                    btn: ['确定', '取消'],
                    btnAlign: 'c',
                    yes: function () {
                        var result = zfYzh(bdcYzhQO);
                        if (result) {
                            // 作废印制号成功后，会将印制号清空
                            zsQzysxlh = null;
                            $("#qzysxlh").val(null);
                            setZsBtnAttr(qszt, zsQzysxlh);
                        }
                        layer.close(submitIndex);
                    },
                    btn2: function () {
                        //取消
                    }
                });
            }
            // 禁止提交后刷新
            return false;
        });

        //还原印制号
        $(document).on('click', '#hyYzh', function () {
            // a标签的样式是不可用时,不执行后面的事件
            if ($("#hyYzh").hasClass("layui-btn-disabled")) {
                return false;
            }
            // 获取页面的印刷序列号
            var qzysxlh = $("#qzysxlh").val();
            // 页面的印制号和当前证书已有的印制号一致时，不进行二次验证提交
            if (null != qzysxlh && '' != qzysxlh) {
                var bdcYzhQO = {};
                bdcYzhQO["zslx"] = zslx;
                bdcYzhQO["qxdm"] = qxdm;
                bdcYzhQO["zsid"] = zsid;
                bdcYzhQO["qzysxlh"] = qzysxlh;

                var submitIndex = layer.open({
                    type: 1,
                    title: '确认还原',
                    area: ['320px'],
                    skin: 'bdc-small-tips',
                    content: '确定还原当前印制号?',
                    btn: ['确定', '取消'],
                    btnAlign: 'c',
                    yes: function () {
                        var result = hyYzh(bdcYzhQO);
                        if (result) {
                            // 还原印制号成功后，会将印制号清空
                            zsQzysxlh = null;
                            $("#qzysxlh").val(null);
                            setZsBtnAttr(qszt, zsQzysxlh);
                        }
                        layer.close(submitIndex);
                    },
                    btn2: function () {
                        //取消
                    }
                });
            }
            // 禁止提交后刷新
            return false;
        });

        // 存储打印配置的sessionKey
        // 当前页的打印类型
        var dylxArr = ["zm", "zmfb"];
        var sessionKey = "bdcZm";
        setDypzSession(dylxArr, sessionKey);

        // 打印证书附表
        $(document).on('click', '#openZmfb', function () {
            var appName = "realestate-register-ui";
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zsid + "/zm/xml";
            var modelUrl = zmfbModelUrl;

            printChoice("zmfb", appName, dataUrl, modelUrl, false, sessionKey);
            //print(zmfbModelUrl, getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zsid + "/zm/xml", false);
            // 禁止提交后刷新
            return false;
        });

        /**
         * 打印证明
         */
        function printZm() {
            zsPrint(zmModelUrl, zsid, "zm", sessionKey);
        }
    })
});


/**
 * 证书证明打印规则验证
 */
function zszmDyYz(gzlslid, _fn, _errorFn) {
    addModel();
    var selectDataList = [];
    var bdcGzYzsjDTO = {};
    bdcGzYzsjDTO.gzlslid = gzlslid;
    selectDataList.push(bdcGzYzsjDTO);
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = "zszmdy";
    bdcGzYzQO.paramList = selectDataList;
    $.ajax({
        url: getContextPath() + '/rest/v1.0/blxx/bdcGzyz',
        type: 'POST',
        async: false,
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcGzYzQO),
        success: function (data) {
            removeModel();
            _fn.call(this, data, data);
        }, error: function (xhr, status, error) {
            removeModel();
            delAjaxErrorMsg(xhr);
            _errorFn.call(this);
        }
    });
}
function querySfzt(processInsId, zsid){
    var deferred = $.Deferred();
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/getSfzt",
        contentType: "application/json;charset=utf-8",
        type: "GET",
        dataType: "json",
        data: {gzlslid : processInsId, zsid: zsid},
        success: function (data) {
            console.info(data);
            if(isNotBlank(data)){
                if(data.sfzt){
                    $('#jfzt').addClass('dyzt-ydz');
                    $('#jfzt').html('已缴费');
                    $('#jfzt').data("dyzt", data.dyzt);
                    $('#jfzt').data("sfzt", data.sfzt);

                }else{
                    $('#jfzt').addClass('dyzt-wdz');
                    $('#jfzt').html('未缴费');
                    $('#jfzt').data("dyzt", data.dyzt);
                    $('#jfzt').data("sfzt", data.sfzt);
                }
                deferred.resolve(data);
            }else{
                deferred.reject();
                warnMsg("获取缴费信息失败！");
            }
        },
        error: function (e) {
            deferred.reject();
            warnMsg("获取缴费信息失败！");
        }
    });
    return deferred;
}