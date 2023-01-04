/**
 * @version 1.0, 2020/10/22
 * @description 常州证书页面js
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
        var zsfj;
        var qjgldm;

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

        // 存储打印配置的sessionKey
        // 当前页的打印类型
        var dylxArr = ["zs", "zsfb", "hst", "zdt", "jtcyfb", "zs1", "zs2", "zs3", "zs1and2", "tdcbjyqzs1and2", "tdcbjyqzs1", "tdcbjyqzs2", "tdcbjyqzs3", "tdcbjyqzs"];
        var qllx;
        //同一流程实例ID对应不动产单元号数量
        var bdcdyhCount;
        var sessionKey = "bdcZm";
        setDypzSession(dylxArr, sessionKey);


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
                        qllx = data.qllx;
                        // 为后面印制号的参数赋值
                        zslx = data.zslx;
                        qxdm = data.qxdm;
                        zsQzysxlh = data.qzysxlh;
                        queryQzysxlh = zsQzysxlh;
                        zsid = data.zsid;
                        qszt = data.qszt;
                        qjgldm = data.qjgldm;

                        if (!isNullOrEmpty(zsid)) {
                            if (isNullOrEmpty(data.zzid)) {
                                $('#bdchint').text('无法签章，请用手工印章');
                            } else if (isNullOrEmpty(data.zzdz)) {
                                $('#bdchint').text('请签章后再打印');
                            }
                        }
                        /**
                         * 【常州】提示查看清册和附表
                         * 查询项目信息中同一工作流实例ID不动产单元号数量
                         */
                        bdcdyhCount = getBdcdyhCount(processInsId);
                        if(bdcdyhCount > 1) {
                            $('#bdcViewListHint').text('查看清册/查看附表');
                        }

                        zsfj = data.fj;
                        if(!isNullOrEmpty(data.qllx) && TDCBJYQ_QLLX == data.qllx) {
                            // 列表信息：列表展示，采用动态读取获取；数据库附记中的地块信息展示时候动态截取掉
                            if(!isNullOrEmpty(data.fj)) {
                                var start = data.fj.indexOf("地块名称");
                                var end = data.fj.indexOf(";;");
                                if (end == -1) {
                                    data.fj = data.fj.substring(0, start)
                                } else {
                                    if (start >= 0 && start < end) {
                                        data.fj = data.fj.substring(0, start) + data.fj.substring(end + 2);
                                    }
                                }

                            }
                        }
                        // if(!isNullOrEmpty(data.qllx) && NCBDCQQDJ_QLLX == data.qllx) {
                        //     // 列表信息：列表展示，采用动态读取获取；数据库附记中的地块信息展示时候动态截取掉
                        //     if(!isNullOrEmpty(data.fj)) {
                        //         var start = data.fj.indexOf("姓名");
                        //         var end = data.fj.indexOf(";;");
                        //         if(end == -1){
                        //             data.fj = data.fj.substring(0, start)
                        //         }else {
                        //             if(start >= 0 && start < end) {
                        //                 data.fj = data.fj.substring(0, start) + data.fj.substring(end+2);
                        //             }
                        //         }
                        //
                        //     }
                        // }
                        // 为基本信息赋值
                        form.val('form', data);
                        $("#zl").html(data.zl);

                        // 动态展示地块列表
                        showDklb(data, processInsId, 4, zsid);
                        //展示家庭成员
                        // showJtcylb(data, processInsId,zsid);
                        // 生成二维码
                        generateQrCode(data.ewmnr);

                        //是否展示家庭成员附表打印按钮
                        if(qllx && qllx === 9) {
                            //土地承包经营权展示家庭成员附表打印按钮
                            $('#openJtcyfb').removeAttr('style');
                        }
                        // 初始化页面设置工作按钮状态
                        // setZsBtnAttr(qszt, zsQzysxlh);
                        //设置权限控制样式
                        if (readOnly || !isNullOrEmpty(formStateId)) {
                            getStateById(readOnly, formStateId, "bdcZs");
                            if ((true == readOnly || "true" == readOnly) && (true == printValue || "true" == printValue)) {
                                // 设置补打时按钮的状态
                                setZsPrintState();
                                setZsBtnAttr(qszt, zsQzysxlh);
                            }
                        }
                    }
                    // 关闭加载
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
            param["fj"] = zsfj;

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
                        getQzysxlh = zsQzysxlh;
                        getYzhid = data.yzhid;
                        // 更新按钮状态
                        setZsBtnAttr(qszt, zsQzysxlh);

                        // 生成缮证信息
                        var zsidArr = [];
                        zsidArr.push(zsid);
                        var szxx = saveSzr(zsidArr, '');
                        $("#szrq").val(szxx.szrq);

                        successMsg("获取成功！");
                    } else {
                        warnMsg("没有可用的印制号！");
                    }
                    // 南通需求要求获取印制号后，文本框可编辑，这里移除disabled属性
                    setQzysxlhAable();
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

        //监听证书预览按钮
        $(document).on('click', '#openZsView', function () {
            layer.open({
                title: ['证书预览', 'font-size:16px;font-weight: 700;'],
                type: 2,
                area: ['100%', '100%'],
                resize: true,
                closeBtn: 1,
                content: '/realestate-register-ui/changzhou/zsxx/zsView.html?zsid=' + zsid,
                scrollbar: false,
                btn: '打印',
                yes: function (index, layero) {
                    if (qszt != qsztValid) {
                        warnMsg("证书未登簿或已注销，不予打印！");
                        return false;
                    }
                    var qzysxlh = $("#qzysxlh").val();
                    if (null == qzysxlh || isNullOrEmpty(qzysxlh)) {
                        warnMsg("印制号为空，不允许打印!");
                        return false;
                    }
                    printZs();
                },
                btnAlign: 'c'
            });
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
                    printZdt(zsid, sessionKey);
                },
                btnAlign: 'c'
            });
            // 禁止提交后刷新
            return false;
        });

        //监听户室图预览按钮
        $(document).on('click', '#openHstView', function () {
            layer.open({
                title: ['户室图预览', 'font-size:16px;font-weight: 700;'],
                type: 2,
                area: ['100%', '100%'],
                resize: true,
                closeBtn: 1,
                content: '/realestate-register-ui/view/zsxx/hstView.html?zsid=' + zsid + "&qjgldm=" + qjgldm,
                scrollbar: false,
                btn: '打印',
                yes: function (index, layero) {
                    printHst(zsid, sessionKey);
                },
                btnAlign: 'c'
            });
            // 禁止提交后刷新
            return false;
        });

        // 打印证书附表
        $(document).on('click', '#openZsfb', function () {
            if (qllx === 9) {
                var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dkfbByZsid/" + zsid + "/dkfb/xml";
                var modelUrl = dkfbModelUrl;
                print(dkfbModelUrl, dataUrl, false);
                // 禁止提交后刷新
                return false;
                // window.open('/realestate-register-ui/view/zsxx/bdcDkList.html?param=' + processInsId);
            } else {
                var appName = "realestate-register-ui";
                var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfb/" + processInsId + "/" + zsid + "/zs/xml";
                var modelUrl = zsfbModelUrl;

                printChoice("zsfb", appName, dataUrl, modelUrl, false, sessionKey);
                // print(zsfbModelUrl,dataUrl , false);
                // 禁止提交后刷新
                return false;
            }
        });

        // 打印家庭成员附表
        $(document).on('click', '#openJtcyfb', function () {
            var appName = "realestate-register-ui";
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/jtcyfb/" + processInsId + "/" + zsid + "/xml";
            printChoice("jtcyfb", appName, dataUrl, jtcyfbModelUrl, false, sessionKey);
            // print(zsfbModelUrl,dataUrl , false);
            // 禁止提交后刷新
            return false;
        });
        /**
         * 宗地图打印
         * @param source
         * @returns {string}
         */
        function printImageHtml(source) {
            var image = '';
            var explorer = window.navigator.userAgent;
            if (window.ActiveXObject || explorer.indexOf("MSIE") >= 0) {
                image += "<img src='" + source + "' />";
            } else {
                image += "<img src='" + source + "' height='170%'/>";
            }
            return "<html><head><script>function step1(){\n" +
                "setTimeout('step2()', 10);}\n" +
                "function step2(){window.print();window.close()}\n" +
                "</script></head><body onload='step1()'>\n" +
                image
                + "</body></html>";
        }

        // 证书打印
        // $(document).on('click', '#printBtn', function () {
        //     printZs(qllx);
        //     // 禁止提交后刷新
        //     return false;
        // });
        //证书分页数打印
        $(document).on('click', '#zs1', function () {
            printZs1(qllx);
            // 禁止提交后刷新
            return false;
        });
        $(document).on('click', '#zs2', function () {
            printZs2(qllx);
            // 禁止提交后刷新
            return false;
        });
        $(document).on('click', '#zs3', function () {
            printZs3(qllx);
            // 禁止提交后刷新
            return false;
        });
        $(document).on('click', '#zs1and2', function () {
            printZs1and2(qllx);
            // 禁止提交后刷新
            return false;
        });
        $(document).on('click', '#zsall', function () {
            printZs(qllx);
            // 禁止提交后刷新
            return false;
        });
        $(document).on('click', '#dzqz', function () {
            window.open(getContextPath() + "/changzhou/dzzz/priviewDzzz.html?zsid=" + zsid);
            // 保存打印数量信息
            saveZszmPrintInfo("zs", null, zsid);
            // 禁止提交后刷新
            return false;
        });

        /**
         * 打印证书
         */
        function printZs(qllx) {
            if (qllx == TDCBJYQ_QLLX) {
                zsPrint(tdcbjyqzsModelUrl, zsid, "tdcbjyqzs", sessionKey);
            }else if(qllx == NCBDCQQDJ_QLLX){
                zsPrint(ncbdcqqdjzsModelUrl, zsid, "ncbdcqqdjzs", sessionKey);
            }else {
                zsPrint(zsModelUrl, zsid, "zs", sessionKey);
            }
        }

        function printZs1(qllx) {
            if (qllx == TDCBJYQ_QLLX) {
                zsPrint(tdcbjyqzs1ModelUrl, zsid, "tdcbjyqzs1", sessionKey);
            }else if(qllx == NCBDCQQDJ_QLLX){
                zsPrint(ncbdcqqdjzsModelUrl1, zsid, "ncbdcqqdjzs1", sessionKey);
            }else {
                zsPrint(zs1ModelUrl, zsid, "zs1", sessionKey);
            }
        }

        function printZs2(qllx) {
            if (qllx == TDCBJYQ_QLLX) {
                zsPrint(tdcbjyqzs2ModelUrl, zsid, "tdcbjyqzs2", sessionKey);
            } else if(qllx == NCBDCQQDJ_QLLX){
                zsPrint(ncbdcqqdjzsModelUrl2, zsid, "ncbdcqqdjzs2", sessionKey);
            } else {
                zsPrint(zs2ModelUrl, zsid, "zs2", sessionKey);
            }
        }

        function printZs3(qllx) {
            if (qllx == TDCBJYQ_QLLX) {
                zsPrint(tdcbjyqzs3ModelUrl, zsid, "tdcbjyqzs3", sessionKey);
            } else if (qllx == NCBDCQQDJ_QLLX) {
                zsPrint(ncbdcqqdjzsModelUrl3, zsid, "ncbdcqqdjzs3", sessionKey);
            } else {
                zsPrint(zs3ModelUrl, zsid, "zs3", sessionKey);
            }
        }

        function printZs1and2(qllx) {
            if (qllx == TDCBJYQ_QLLX) {
                zsPrint(tdcbjyqzs3ModelUrl, zsid, "tdcbjyqzs1and2", sessionKey);
            } else if (qllx == NCBDCQQDJ_QLLX) {
                zsPrint(ncbdcqqdjzsModelUrl3, zsid, "ncbdcqqdjzs1and2", sessionKey);
            } else {
                zsPrint(zs3ModelUrl, zsid, "zs1and2", sessionKey);
            }
        }
    });
})
;