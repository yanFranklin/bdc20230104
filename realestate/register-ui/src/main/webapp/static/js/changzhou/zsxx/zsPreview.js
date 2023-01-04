/**
 * @author "<a href="mailto:bianwen@gtmap.cn>bianwen</a>"
 * @version 1.0, 2018/12/18
 * @description 证书预览页面js
 */
layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    // 查询参数
    var processInsId = $.getUrlParam('processInsId');
    var zsid = $.getUrlParam('zsid');
    var xmid = $.getUrlParam('xmid');
    var parentResourceName = $.getUrlParam('parentResourceName');
    // 证书相关的xmid拼接字符串
    var xmids = getSingleZsXmids(xmid, processInsId, parentResourceName);
    var qllx;

    $(function () {


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

        renderZsylData();

        /**
         * 证书预览数据
         * @param zsylData
         */
        function renderZsylData() {
            var zsylList;
            var yldata = [];

            if (!isNullOrEmpty(zsid) && !isNullOrEmpty(sessionStorage.zsylListStr)) {
                zsylList = JSON.parse(sessionStorage.zsylListStr);
            }
            if (!isEmptyObject(zsylList)) {
                // 组合等流程，从证书列表台账点击详情查看每本证书
                $.each(zsylList, function (index, item) {
                    if (item.zsid == zsid) {
                        yldata.push(item);
                        return false;
                    }
                });
            } else {
                // 从单元信息列表或者单个项目预览证书（查询单个项目的证书，xmid为空则取流程的任意一个xmid）
                var url = "/realestate-register-ui/rest/v1.0/xmZsyl/" + processInsId;
                if (!isNullOrEmpty(xmid)) {
                    url = "/realestate-register-ui/rest/v1.0/xmZsyl/" + processInsId + "?xmid=" + xmid;
                }
                //获取数据
                $.ajax({
                    url: url,
                    contentType: "application/json;charset=utf-8",
                    type: "GET",
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        if (data) {
                            yldata = data;
                        }
                    }, error: function () {
                        warnMsg("获取数据错误！");
                        layer.close(index);
                    }
                });
            }
            //修改坐落、面积内容较多的输入框
            $('.bdc-special-input').on('change', function () {
                $(this).attr('title', $(this).val());
            });
            if (yldata) {
                if (!isNullOrEmpty(yldata[0].qllx) && TDCBJYQ_QLLX == yldata[0].qllx) {
                    // 列表信息：列表展示，采用动态读取获取；数据库附记中的地块信息展示时候动态截取掉
                    if (!isNullOrEmpty(yldata[0].fj)) {
                        var start = yldata[0].fj.indexOf("地块名称");
                        var end = yldata[0].fj.indexOf(";;");
                        if (start >= 0 && start < end) {
                            yldata[0].fj = yldata[0].fj.substring(0, start) + yldata[0].fj.substring(end + 2);
                        }
                    }
                }
                // if(!isNullOrEmpty(yldata[0].qllx) && NCBDCQQDJ_QLLX == yldata[0].qllx) {
                //     // 列表信息：列表展示，采用动态读取获取；数据库附记中的家庭成员信息展示时候动态截取掉
                //     if(!isNullOrEmpty(yldata[0].fj)) {
                //         var start = yldata[0].fj.indexOf("姓名");
                //         var end = yldata[0].fj.indexOf(";;");
                //         if(start >= 0 && start < end) {
                //             yldata[0].fj = yldata[0].fj.substring(0, start) + yldata[0].fj.substring(end+2);
                //         }
                //     }
                // }
                qllx = yldata[0].qllx;
                if (qllx && qllx === 9) {
                    $('#jtcyfb').removeAttr('style');
                }

                // 动态展示地块列表
                showDklbYl(yldata[0], processInsId, 4, xmid);
                // 动态展示地块列表
                // showJtcylb(yldata[0], processInsId, '');
                // 为证书信息模板信息赋值
                var getTpl = zsxxTpl.innerHTML;
                laytpl(getTpl).render(yldata, function (html) {
                    $("#detail").html(html);
                });
                form.render();

                // if(!isNullOrEmpty(yldata[0].qllx) && TDCBJYQ_QLLX != yldata[0].qllx && NCBDCQQDJ_QLLX != yldata[0].qllx) {
                //     $("#fj").css("height", "100%");
                // }
            }
            // 关闭加载
            layer.close(index);
        }

        // 获取打印配置信息
        var appName = "realestate-register-ui";
        var dylxArr = ["zsfbyl","jtcyfb"];
        var sessionKey = "zsPreview";
        setDypzSession(dylxArr, sessionKey);

        // 预览证书附表
        $(document).on('click', '#zsfbyl', function () {
            if (qllx == 9) {
                print(dkfbModelUrl, getIP() + "/realestate-register-ui/rest/v1.0/print/dkfb/" + processInsId + "/dkfb/xml", false);
                // window.open('/realestate-register-ui/view/zsxx/bdcDkList.html?param=' + processInsId);
            } else {
                var key = sendXmidsToRedis(xmids);
                var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfbyl/xml?gzlslid="
                    + processInsId + "&key=" + key + "&zslx=zs";

                printChoice("zsfbyl", appName, dataUrl, zsfbylModelUrl, false, sessionKey);
                // 禁止提交后刷新
                return false;
            }
        });
        // 预览证书附表
        $(document).on('click', '#jtcyfb', function () {
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/jtcyfb/" + processInsId + "/xml/yl" + "?xmid="+ xmid;
            printChoice("jtcyfb", appName, dataUrl, jtcyfbModelUrl, false, sessionKey);
            // 禁止提交后刷新
            return false;
        });

        // 房屋清单预览
        $(document).on('click', '#fwqdyl', function () {
            var key = sendXmidsToRedis(xmids);
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dyfwqd/false/xmids/xml?gzlslid="
                + processInsId + "&key=" + key;
            print(fwqdModelUrl, dataUrl, false);
            // 禁止提交后刷新
            return false;
        });

        // 房屋清单（户）预览
        $(document).on('click', '#fwqdhyl', function () {
            var key = sendXmidsToRedis(xmids);
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dyfwqd/true/xmids/xml?gzlslid="
                + processInsId + "&key=" + key;
            print(fwqdModelUrl, dataUrl, false);
            // 禁止提交后刷新
            return false;
        });
    });


});