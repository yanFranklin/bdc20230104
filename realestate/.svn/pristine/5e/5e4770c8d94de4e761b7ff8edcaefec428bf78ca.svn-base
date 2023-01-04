layui.use(['form', 'jquery', 'laytpl', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laytpl = layui.laytpl,
        table = layui.table,
        laydate = layui.laydate;
    $(function () {

        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-form-div');

        function defaultStyle() {
            if ($cnotentTitle.length == 1) {
                $('.bdc-form-div').css('padding-top', '68px');
            }
            if ($(window).scrollTop() > 68) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '63px');
            } else if ($(window).scrollTop() <= 68) {
                $cnotentTitle.css('top', '10px');
                $navContainer.css('top', '68px');
            }
        }

        $(window).resize(function () {
            defaultStyle();
        });
        $(window).on('scroll', function () {
            if ($(this).scrollTop() > 68) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '63px');
            } else if ($(this).scrollTop() <= 68) {
                $cnotentTitle.css('top', '10px');
                $navContainer.css('top', '68px');
            }
        });

        //加载数据
        loadZnshData();

        //加载数据

        function loadZnshData() {
            var xmid = $.getUrlParam("xmid");
            getReturnData("/rest/v1.0/znsh/" + xmid, {}, "GET", function (data) {

                //渲染权利人核验
                var qlrJson = data.qlr;
                var qlrNum = 0;
                if (qlrJson.qlrbdList != null) {
                    qlrNum = qlrJson.qlrbdList.length * 2;
                    qlrJson.qlrNum = qlrNum;
                }
                //拼接登记信息字符串
                var djlist = qlrJson.bdcQlrList;
                var qlrdjmc = "";
                var qlrzjh = "";
                var qlrxx = "";
                if (djlist != null) {
                    for (var i = 0; i < djlist.length; i++) {
                        if (djlist[i].qlrmc != null) {
                            qlrxx += djlist[i].qlrmc + "，";
                        }
                        if (djlist[i].zjh != null) {
                            qlrxx += djlist[i].zjh +"<br>";

                        }
                    }
                    qlrJson.djrxx = qlrxx;
                }

                //拼接比较信息字符串
                var qlrbdarr = qlrJson.qlrbdList;
                if (qlrbdarr != null) {
                    for (var i = 0; i < qlrbdarr.length; i++) {
                        var str2 = "";
                        for (var bdcqlr in qlrbdarr[i]) {


                            var ys = qlrbdarr[i][bdcqlr];
                            if (Array.isArray(ys) && ys.length > 0) {

                                for (var j = 0; j < ys.length; j++) {
                                    str2 += ys[j].qlrmc +"，"+ ys[j].zjh + "<br>";
                                }
                            }
                        }
                        //把拼成的字符串放进对象数组里
                        var endstr2 = str2.substr(0, str2.length - 4)
                        qlrbdarr[i].qlrsj = endstr2;
                    }
                }

                renderTpl(qlrJson, 'qlrmk', qlrTpl.innerHTML);

                //渲染义务人核验
                var ywrJson = data.ywr;

                var ywrNum = 0;
                if (ywrJson.qlrbdList != null) {
                    ywrNum = qlrJson.qlrbdList.length * 2;
                    ywrJson.ywrNum = ywrNum;
                }
                //拼接登记信息字符串

                var ywrdjlist = ywrJson.bdcQlrList;
                var ywrdjmc = "";
                var ywrzjh = "";
                var ywrxx = "";
                if (ywrdjlist != null) {
                    for (var i = 0; i < ywrdjlist.length; i++) {
                        if (ywrdjlist[i].qlrmc != null) {
                            ywrxx += ywrdjlist[i].qlrmc + "，";
                        }
                        if (ywrdjlist[i].zjh != null) {
                            ywrxx += ywrdjlist[i].zjh +"<br>";

                        }
                    }
                    ywrJson.djrxx = ywrxx;
                }
                //拼接比较信息字符串
                var ywrbdarr = ywrJson.qlrbdList;
                if (ywrbdarr != null) {
                    for (var i = 0; i < ywrbdarr.length; i++) {
                        var str3 = "";
                        for (var bdcqlr in ywrbdarr[i]) {
                            var ys = ywrbdarr[i][bdcqlr];
                            if (Array.isArray(ys) && ys.length > 0) {

                                for (var j = 0; j < ys.length; j++) {
                                    str3 += ys[j].qlrmc +"，"+ ys[j].zjh + "<br>";
                                }
                            }
                        }
                        //把拼成的字符串放进对象数组里
                        var endstr3 = str3.substr(0, str3.length - 4)
                        ywrbdarr[i].qlrsj = endstr3;
                    }
                }
                renderTpl(ywrJson, 'ywrmk', ywrTpl.innerHTML);

                //渲染项目信息
                var xmqlxxJson = data.xmqlxx;
                var bdNum = 0
                if(xmqlxxJson.sjlyList!=null){
                    bdNum = 2 * xmqlxxJson.sjlyList.length;
                }
                xmqlxxJson.bdNum = bdNum;

                var xxarr = xmqlxxJson.sjjyxxList;
                renderTpl(xmqlxxJson, 'xmxxmk', jyxxTpl.innerHTML);
                //改变无需比较的信息底色
                if (xxarr != null) {
                    for (var i = 0; i < xxarr.length; i++) {
                        for (var xxjy in xxarr[i]) {
                            var ys = xxarr[i][xxjy];
                            if (Array.isArray(ys) && ys.length > 0) {
                                for (var j = 0; j < ys.length; j++) {
                                    if(ys[j].sfyz == null){
                                        var index = i;//需要改变的下标
                                        var changediv = ".xxbody tr:nth-child(" + (index + 1) + ") td:last-child";
                                        $(changediv).css("background-color", "#e2e3e5");

                                    }
                                }
                            }
                        }
                    }
                }

            }, function (error) {
                delAjaxErrorMsg(error);

            });

        }

        //渲染模块数据
        function renderTpl(json, mkid, getTpl) {
            var dataView = document.getElementById(mkid);
            laytpl(getTpl).render(json, function (html) {
                dataView.innerHTML = html;
            });
        }

    });

});