/**
 * @author "<a href="mailto:bianwen@gtmap.cn>bianwen</a>"
 * @version 1.0, 2018/12/18
 * @description 证书页面js
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer', 'response'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;
    var response = layui.response;

    $(function () {
        // 查询参数
        var zsid = $.getUrlParam("zsid");
        var processInsId = $.getUrlParam('processInsId');
        // 表单ID
        var formStateId = $.getUrlParam('formStateId');
        // 获取表单权限参数readOnly
        var readOnly = $.getUrlParam('readOnly');

        var zslx;
        var qxdm;
        // 当前证书的已保存的印刷序列号
        var zsQzysxlh;
        var qszt;
        var qjgldm;

        var qllx;

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
        // 判断是否验证完税
        checkSfyzws(processInsId);
        // 初始化其它按钮权限
        initQtButtonAtrr(processInsId);

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
                        // 为后面印制号的参数赋值
                        zslx = data.zslx;
                        qxdm = data.qxdm;
                        zsQzysxlh = data.qzysxlh;
                        zsid = data.zsid;
                        qszt = data.qszt;
                        qllx = data.qllx;
                        qjgldm = data.qjgldm;
                        // 为基本信息赋值
                        form.val('form', data);
                        $("#zl").html(data.zl);
                        $('#mj').attr('title', data.mj);
                        $('#syqx').attr('title', data.syqx);

                        setDyzt(data.dyzt);

                        // 生成二维码
                        generateQrCode(data.ewmnr);
                        // 初始化页面设置工作按钮状态
                        setZsBtnAttr(qszt, zsQzysxlh);
                        //设置权限控制样式
                        if (readOnly || !isNullOrEmpty(formStateId)) {
                            getStateById(readOnly, formStateId, "bdcZs");
                            // 对补打状态的页面单独权限设置
                            if ((true == printValue || "true" == printValue)) {
                                // 设置补打时按钮的状态
                                setZsPrintState();
                                setZsBtnAttr(qszt, zsQzysxlh);
                            }
                        }
                    }
                    // 关闭加载
                    layer.close(index);

                    // 当领证方式为电子证照时，自动获取虚拟印制号保存到证书记录；
                    // 否则不做处理，等用户手动录入正常印制号或者点击获取印制号按钮
                    getXnyzh(processInsId, zsid);
                }
            });
        }

        //修改坐落、面积内容较多的输入框
        $('.bdc-special-input').on('change', function () {
            $(this).attr('title', $(this).val());
        });

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
            if (!isNullOrEmpty(qzysxlh) && qzysxlh != zsQzysxlh) {
                // 生成缮证信息
                var zsidArr = [];
                zsidArr.push(zsid);
                var szxx = saveSzr(zsidArr, '');
                $("#szrq").val(szxx.szrq);

                // 定义印制号的获取前置条件(在获取印制号的时候给初始值)
                var bdcYzhQO = {};
                bdcYzhQO["zslx"] = zslx;
                bdcYzhQO["syqk"] = yly;
                bdcYzhQO["qxdm"] = qxdm;
                bdcYzhQO["zsid"] = zsid;
                // 验证qzysxlh是否可用
                bdcYzhQO["qzysxlh"] = qzysxlh.trim();

                // 如果是用于电子证照的虚拟印制号，因为不是从印制号表取所以不需要验证；其它正常获取或者手动录入的印制号需要验证
                var result;
                if(isXnyzh) {
                    result = true;
                } else {
                    result = checkYzhSyqk(bdcYzhQO);
                }

                if (result) {
                    // 印制号验证通过，修改证书印制号值
                    zsQzysxlh = qzysxlh;
                    setZsBtnAttr(qszt, zsQzysxlh);
                }
            }

            // 关闭加载
            removeModel();
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
            // 生成缮证信息
            var zsidArr = [];
            zsidArr.push(zsid);
            var szxx = saveSzr(zsidArr, '');
            $("#szrq").val(szxx.szrq);

            // loading加载
            addModel();

            // 定义印制号的获取前置条件(在获取印制号的时候给初始值)
            var bdcYzhQO = {};
            bdcYzhQO["zslx"] = zslx;
            bdcYzhQO["syqk"] = yly;
            bdcYzhQO["qxdm"] = qxdm;
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
                        // 更新按钮状态
                        setZsBtnAttr(qszt, zsQzysxlh);

                        successMsg("获取成功！");
                    } else {
                        warnMsg("没有可用的印制号！");
                    }
                    removeModel();
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
                        var result = zfYzh_hf(bdcYzhQO);
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
            if(isXnyzh) {
                warnMsg("当前印制号是虚拟印制号，请刷新页面获取！")
                return ;
            }

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

        // 手动登簿
        $('#db').click(function () {
            sddb(processInsId, response, $("#db")).done(function () {
                if (dbSucceed && qszt == qsztTempory) {
                    qszt = qsztValid;
                    if (invalidZsQszt == -1) {
                        qszt = qsztHistory;
                    } else if (invalidZsQszt) {
                        $.each(invalidZsQszt, function (index, zsxx) {
                            if (zsxx.zsid === zsid) {
                                qszt = qsztHistory;
                                return false;
                            }
                        });
                    }
                    // 单个证书页面设置权限
                    setQsztBtnAttr(qszt);
                    //sddb 方法中已设置了qszt权限按钮，这里需对印制号和权属状态都有影响的按钮也设置权限
                    setQsztAndYzhBtnAttr(qszt, zsQzysxlh);
                }
            });
            return false;
        });

        // 电子证照制证
        $('#zz').click(function () {
            /*if($("#dyzt").length  == 0 || $("#dyzt").attr('class') != "dyzt-ydz"){
                warnMsg("请先登簿打印证书后，再制证！");
                return;
            }*/
            zzDzzz(zsid);
            return false;
        });
        //监听证书预览按钮
        $(document).on('click', '#openZsView', function () {
            layer.open({
                title: ['证书预览', 'font-size:16px;font-weight: 700;'],
                type: 2,
                area: ['100%', '100%'],
                resize: true,
                closeBtn: 1,
                content: '/realestate-register-ui/view/zsxx/zsView.html?zsid=' + zsid,
                scrollbar: false,
                btn: '打印',
                yes: function (index, layero) {
                    var qzysxlh = $("#qzysxlh").val();

                    var state = checkZsViewPrintState(readOnly, qszt, qzysxlh);
                    if (state) {
                        printZs();
                    }
                },
                btnAlign: 'c'
            });
            // 禁止提交后刷新
            return false;
        });

        // 打印证书附表
        $(document).on('click', '#openZsfb', function () {
            if (9 === qllx) {
                // print(dkfbModelUrl, getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zsid + "/dkfb/xml", false);
                //嵌套地块信息列表页面
            }
            print(zsfbModelUrl, getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zsid + "/zs/xml", false);
            // 禁止提交后刷新
            return false;
        });

        //监听宗地图预览按钮
        $(document).on('click', '#openZdtView', function () {
            layer.open({
                title: ['宗地图预览', 'font-size:16px;font-weight: 700;'],
                type: 2,
                area: ['100%', '100%'],
                resize: true,
                closeBtn: 1,
                content: '/realestate-register-ui/view/zsxx/zdtView.html?zsid=' + zsid + "&qjgldm=" + qjgldm,
                scrollbar: false,
                btn: '打印',
                yes: function (index, layero) {
                    printZdtFr3();
                },
                btnAlign: 'c'
            });
            // 禁止提交后刷新
            return false;
        });

        /**
         * 建行订单查询
         */
        $(document).on('click', '#jhddcx', function () {
            jhddcx(processInsId);
        });

        /**
         * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
         * @description 打印宗地图fr3
         */
        function printZdtFr3() {
            printZdt(zsid)
        }

        // 证书打印
        $(document).on('click', '#printBtn', function () {
            printZs();
            // 禁止提交后刷新
            return false;
        });

        /**
         * 打印证书
         */
        function printZs() {
            zsPrint(zsModelUrl, zsid, "zs");
            // 打印结束，更新页面打印状态
            var searchTime = 0;
            var dyztInterval = setInterval(function () {
                console.log("查询打印状态！");
                setZsDyzt(zsid);
                searchTime++;
                if (searchTime > 6) {
                    // 查询6次后，取消循环
                    window.clearInterval(dyztInterval);
                }
            }, 5000);
        }
    });
})
;

