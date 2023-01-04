/**
 * 证明JS代码
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer', 'util', 'response'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;
    var util = layui.util;
    var response = layui.response;

    $(function () {
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
        var index = layer.load(2);

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
                        $('#qlr').html(data.qlr);
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
                    layer.close(index);
                }, error: function () {
                    warnMsg("获取数据错误！");
                    layer.close(index);
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
                //processInsId用于回写缮证人到大云平台
                saveSzr(zsidArr, processInsId);

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
                    printZm();
                },
                btnAlign: 'c'
            });
            // 禁止提交后刷新
            return false;
        });

        // 证书打印
        // form.on('submit(printBtn)', function (data) {
        $(document).on('click', '#printBtn', function () {
            printZm();
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
            var jgmcList = [];
            var sfdz = null;
            if(isNotBlank($("#qlr").html())){
                jgmcList = $("#qlr").html().split(" ");
            }
            for (var i = 0; i < jgmcList.length; i++) {
                if(jgmcList[i]){
                    sfdz=getSfdz(jgmcList[i]);
                }
                if(sfdz == 0 || sfdz == "0"){
                    break;
                }
            }
            if(sfdz == 0 || sfdz == "0"){
                var alertIndex = layer.open({
                    type: 1,
                    skin: 'bdc-small-tips',
                    title: '信息',
                    area: ['320px'],
                    content: "该银行已接入互联网+，<br/>禁止打印纸质证明",
                    btn: ['确定'],
                    btnAlign: 'c',
                    yes: function () {
                        layer.close(alertIndex);
                    }
                });
                removeModel();
                return false;
            }

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
                        saveSzr(zsidArr, processInsId);

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


        /**
         * 是否打证
         */
        function getSfdz(jgmc){
            var sfdz = null;
            getSfdzUrl = "/realestate-register-ui/rest/v1.0/zsxx/xtjg/" + jgmc;
            $.ajax({
                url: getSfdzUrl,
                contentType: "application/json;charset=utf-8",
                type: "GET",
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data) {
                        sfdz = data.sfdz;
                    }
                },
                error: function () {
                    warnMsg("获取机构信息失败，请重试！");
                }

            });
            return sfdz;
        }

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
        // 房屋清单打印
        $(document).on('click', '#fwqd', function () {
            printFwqd(processInsId, zsid, false);
            // 禁止提交后刷新
            return false;
        });
        // 房屋清单(户)打印
        $(document).on('click', '#fwqdh', function () {
            printFwqd(processInsId, zsid, true);
            // 禁止提交后刷新
            return false;
        });

        /**
         * 打印证明
         */
        function printZm() {
            zsPrint(zmModelUrl, zsid, "zm");
        }
    })
});