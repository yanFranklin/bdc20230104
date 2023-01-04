/**
 * 证明JS代码
 */
layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer', 'util'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;
    var laytpl = layui.laytpl;

    $(function () {
        // 查询参数
        var xmid = $.getUrlParam("xmid");
        var zsid = $.getUrlParam('zsid');

        // 查询参数
        var processInsId = $.getUrlParam("processInsId");
        var parentResourceName = $.getUrlParam('parentResourceName');
        var xmids = getSingleZsXmids(xmid, processInsId, parentResourceName);


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
                        // $('.content-title-box').css("display","none");
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
                // 为证书信息模板信息赋值
                var getTpl = zsxxTpl.innerHTML;
                laytpl(getTpl).render(yldata, function (html) {
                    $("#detail").html(html);
                });

                /*
                * 渲染二维码数据
                * */
                for (var i = 0; i < yldata.length; i++) {
                    generateQrCodeById("#qrcode_" + i, yldata[i].ewmnr);
                }
                form.render();
            }
            // 关闭加载
            layer.close(index);
        }

        // 获取打印配置信息
        var appName = "realestate-register-ui";
        var dylxArr = ["zmfbyl"];
        var sessionKey = "zmPreview";
        setDypzSession(dylxArr, sessionKey);

        // 预览证明附表
        $(document).on('click', '#zmfbyl', function () {
            var key = sendXmidsToRedis(xmids);
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zszmfbyl/xml?gzlslid=" + processInsId + "&xmid=" + xmid + "&zslx=zm&key=" + key;

            printChoice("zmfbyl", appName, dataUrl, zmfbylModelUrl, false, sessionKey);
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