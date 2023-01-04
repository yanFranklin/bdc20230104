layui.use(['jquery', 'element', 'form', 'laytpl', 'layer', 'table'], function () {
    var $ = layui.jquery,
        element = layui.element,
        laytpl = layui.laytpl;

    $(function () {
        // 获取参数
        var xmid = $.getUrlParam('xmid');
        var processInsId = $.getUrlParam('processInsId');
        var version = $.getUrlParam('version');
        //配置侧边栏加载顺序
        var asideValue = $.getUrlParam('asideValue');
        //打印
        var printValue = $.getUrlParam('print');
        if(!printValue) {
            printValue = false;
        }

        //默认第一个加载
        var firstAside= $.getUrlParam('firstAside');

        if(isNullOrEmpty(xmid)){
            warnMsg(" 未传入项目参数，无法查询！");
            setDefaultPage();
            return;
        }

        // 如果是蚌埠版本"1.0登记业务查询"跳转过来查看，增加 受理图片 查看菜单项
        var daxxUrl;
        if(version == "bengbu"){
            // 获取档案模型配置
            var damx = "Ywsltp";
            $.ajax({
                url: '/realestate-register-ui/rest/v1.0/url/damx',
                type: 'GET',
                dataType: 'text',
                async: false,
                success: function (data) {
                    if (!isNullOrEmpty(data)) {
                        damx = data;
                    }
                    // 获取档案查看地址
                    daxxUrl = getDjdaUrl(xmid, processInsId, damx);
                }
            });
        }

        if(!isNullOrEmpty(xmid)){
            addModel();
            $.ajax({
                url: '/realestate-register-ui/rest/v1.0/bdcdy/xmjbxx?xmid=' + xmid,
                type: "GET",
                async: false,
                dataType: "json",
                success: function (data) {
                    if(data && data.qllx){
                        var urlData =[];
                        var asideData =["jbxx","qlym","zsxx","djbxx","lct","dyawqd","bbdaxx","dady","sfxx"];
                        //侧边栏加载默认顺序
                        if(version == "changzhou"){
                            asideData = ["jbxx", "qlym", "zsxx", "djbxx", "lct", "dyawqd", "bbdaxx", "dady", "sfxx", "lsfjxx", "ylcfjxx"];
                        }


                        if(isNotBlank(asideValue)){
                            //默认读取配置
                            asideData =asideValue.split(",");
                        }else if(isNotBlank(firstAside)){
                            //优先加载配置表单
                            var newAsideData =[];
                            newAsideData.push(firstAside);
                            asideData.forEach(function (v) {
                                if(v !==firstAside){
                                    newAsideData.push(v);
                                }
                            });
                            asideData =newAsideData;
                        }

                        asideData.forEach(function (v) {
                            if(v ==="jbxx"){
                                urlData.push({
                                    name: '基本信息',
                                    mark: 'basic',
                                    url: '/realestate-register-ui/view/lsgx/xmxx.html?xmid=' + xmid,
                                    childTree: []
                                });
                            }else if(v ==="qlym"){
                                var qlym = getQlxxYm(data.qllx, data.bdcdyfwlx);
                                if(isNullOrEmpty(qlym)){
                                    warnMsg(" 未查询到权利信息！");
                                } else {
                                    var xmidParam = xmid;
                                    var name = '权利信息';
                                    // 注销显示上一手权利
                                    if(400 == data.djlx){
                                        xmidParam = data.yxmid;
                                        name = '原权利信息';
                                    }

                                    // 查封权利
                                    if(800 == data.djlx){
                                        name = '查封信息';
                                        if(1 == data.sfjf){
                                            // 解封项目
                                            xmidParam = data.yxmid;
                                        }
                                    }

                                    urlData.push({
                                        name: name,
                                        mark: 'basic',
                                        url: '/realestate-register-ui/view/qlxx/' + qlym + '.html?xmid=' + xmidParam + "&readOnly=true&isCrossOri=true",
                                        childTree: []
                                    });
                                }

                            }else if(v ==="zsxx"){
                                // 非注销、查封显示证书
                                if(400 != data.djlx && 800 != data.djlx){
                                    if(isNullOrEmpty(processInsId)){
                                        warnMsg("工作流实例ID为空，无法显示证书！");
                                        return ;
                                    }
                                    urlData.push({
                                        name: '证书信息',
                                        mark: 'basic',
                                        url: '/realestate-register-ui/rest/v1.0/bdcZs/redirect?lsxm=true&zsyl=false&readOnly=true&print='+printValue+'&zsyl=false&xmid=' + xmid+"&processInsId="+processInsId,
                                        childTree: []
                                    });
                                }
                            }else if(v ==="djbxx"){
                                urlData.push({
                                    name: '登记簿',
                                    mark: 'basic',
                                    url: '/realestate-register-ui/view/djbxx/bdcDjb.html?param=' + data.bdcdyh,
                                    childTree: []
                                });

                            }else if(v ==="lct"){
                                urlData.push({
                                    name: '流程图',
                                    mark: 'basic',
                                    url: '/realestate-register-ui/view/lsgx/lct.html?xmid=' + xmid + "&gzlslid=" + processInsId,
                                    childTree: []
                                });

                            }else if(v ==="dyawqd"){
                                // 抵押权增加抵押物清单
                                if(95 == data.qllx ||37 == data.qllx){
                                    urlData.push({
                                        name: "抵押物清单",
                                        mark: 'basic',
                                        url: "/realestate-register-ui/view/bdcdy/dyawqd.html?readOnly=true&xsdy=n&xmid=" + xmid + "&processInsId=" + processInsId,
                                        childTree: []
                                    });
                                }
                            }else if(v ==="bbdaxx"){
                                // 如果是蚌埠版本"1.0登记业务查询"跳转过来查看，增加 受理图片 查看菜单项
                                if(version == "bengbu"){
                                    urlData.push({
                                        name: "受理图片",
                                        mark: 'basic',
                                        url: daxxUrl,
                                        childTree: []
                                    });
                                }
                            }else if(version == "yancheng" && v ==="dady"){
                                var url = getDjdaUrlYc(xmid, processInsId, "");
                                urlData.push({
                                    name: "档案调用",
                                    mark: 'basic',
                                    url: url,
                                    childTree: []
                                });
                            }else if(v === "sfxx"){
                                urlData.push({
                                    name: '收费信息',
                                    mark: 'basic',
                                    url: '/realestate-accept-ui/rest/v1.0/sfxx?readOnly=true&processInsId='+ processInsId,
                                    childTree: []
                                });
                            }else if (v === "lsfjxx") {
                                urlData.push({
                                    name: '历史附件信息',
                                    mark: 'basic',
                                    url: '/realestate-etl/view/lsfj/popup-img.html?processinsid=' + processInsId,
                                    childTree: []
                                });
                            } else if (v === "ylcfjxx") {
                                urlData.push({
                                    name: '补充附件信息',
                                    mark: 'basic',
                                    url: '/realestate-etl/view/lsfj/popup-img.html?processinsid=' + processInsId + "&ymlx=ylcfjxx",
                                    childTree: []
                                });
                            }
                        });
                        showPage(urlData);
                    } else {
                        warnMsg(" 未查询到项目数据，请重试！");
                        setDefaultPage();
                    }
                }, error: function(){
                    warnMsg(" 未查询到项目数据，请重试！");
                    setDefaultPage();
                }, complete: function() {
                    removeModel();
                }
            });
        }

        function setDefaultPage(){
            var data = [{
                name: '基本信息',
                mark: 'basic',
                url: '/realestate-register-ui/view/lsgx/xmxx.html',
                childTree: []
            }];
            showPage(data);
        }

        function showPage(data){
            // 默认选中第一个表单资源
            if (data && data.length > 0) {
                $('.layui-body .layadmin-tabsbody-item.layui-show iframe').attr('src', data[0].url);
                $('.bdc-aside-tab .layui-tab-title li.layui-this').attr('lay-id', data[0].url).attr('lay-attr', data[0].url);
                $('.bdc-aside-tab .layui-tab-title li.layui-this>span').html(data[0].name);
            }
            var getAsideTpl = asideTpl.innerHTML
                , getAsideView = document.getElementById('LAY-system-side-menu');
            laytpl(getAsideTpl).render(data, function (html) {
                getAsideView.innerHTML = html;
            });
            element.render('nav', 'layadmin-system-side-menu');
        }

        //0.1 tab点击，改变iframe的高度
        $('.layui-side-menu .layui-nav-item a').on('click', function () {
            setTimeout(function () {
                $('.layadmin-iframe').height($('.layui-layout-admin .layui-body .layadmin-tabsbody-item').height());
            }, 100);
        });

        //鼠标覆盖在顶部 info 图标，展示相关信息
        $('.bdc-aside-title img').on('mouseenter', function () {
            $('.bdc-aside-title .bdc-title-tips').removeClass('bdc-hide');
        });
        $('.bdc-aside-title .bdc-title-tips').on('mouseleave', function () {
            $('.bdc-aside-title .bdc-title-tips').addClass('bdc-hide');
        });

        // 点击侧边的收缩按钮
        // 隐藏
        $('.bdc-aside-close').on('click', function () {
            $('.layui-layout-admin .layui-side').animate({left: '-220px'}, 100);
            $('.layui-layout-admin .layui-layout-left').animate({left: 0}, 100);
            $('.layui-layout-admin .layui-footer').animate({left: 0}, 100);
            $('.layui-layout-admin .layui-body').animate({left: 0}, 100);
            $('.layadmin-pagetabs').animate({left: 0}, 100);
            $('.bdc-aside-zoom').toggleClass('bdc-hide').animate({left: 0}, 300);
        });
        // 显示
        $('.bdc-aside-open').on('click', function () {
            $('.layui-layout-admin .layui-side').animate({left: '0'}, 100);

            $('.layui-layout-admin .layui-layout-left').animate({left: '220px'}, 100);
            $('.layui-layout-admin .layui-footer').animate({left: '220px'}, 100);
            $('.layui-layout-admin .layui-body').animate({left: '220px'}, 100);
            $('.layadmin-pagetabs').animate({left: '220px'}, 100);

            $('.bdc-aside-zoom').toggleClass('bdc-hide').animate({left: '206px'}, 300);
            // $('.bdc-aside-open').animate({left: '208px'},300);
        });

        //默认显示的第一个tab 删除
        $('.bdc-aside-tab li .bdc-tab-close').on('click', function () {
            var _this = $(this).parent();
            if (_this.hasClass('layui-this')) {
                if($('.bdc-aside-tab .layui-tab-title li').length > 2){
                    _this.parent().find('li:nth-child(3)').addClass('layui-this');
                    $('.layui-body .layadmin-tabsbody-item:nth-child(3)').addClass('layui-show');
                    var layId = $('.bdc-aside-tab .layui-tab-title li:nth-child(3)').attr('lay-id');
                    var $sideLi = $('.layui-side-scroll .layui-nav-tree li');
                    for(var i = 1; i< $sideLi.length; i++){
                        if($($sideLi[i]).find('a').attr('lay-href') == layId){
                            $($sideLi[i]).addClass('layui-this');
                        }
                    }

                    _this.remove();
                    $('.layui-body .layadmin-tabsbody-item:nth-child(2)').remove();
                    $('.layui-side-scroll .layui-nav-tree li:first-child').removeClass('layui-this');
                }else {
                    _this.remove();
                    $('.layui-body .layadmin-tabsbody-item:nth-child(2)').remove();
                    $('.layui-side-scroll .layui-nav-tree li:first-child').removeClass('layui-this');
                }


            } else {
                _this.remove();
                $('.layui-body .layadmin-tabsbody-item:nth-child(2)').remove();
            }

        });


        /**
         * 不动产登记档案
         * @param data
         */
        function getDjdaUrl(xmid, processInsId, damx){
            if(isNullOrEmpty(xmid)) {
                warnMsg("xmid为空，无法查看受理图片！");
                return;
            }

            // 这里查看1.0项目档案模型需要指定为 Ywsltp
            var url = "";
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/url/common/bdcda?damx=" + damx + "&xmid=" + xmid + "&gzlslid=" + processInsId,
                type: 'get',
                async: false,
                success: function (data) {
                    console.log("不动产档案url:" + data);
                    url = data;
                }
            });
            return url;
        }


        /**
         * 不动产登记档案
         * @param data
         */
        function getDjdaUrlYc(xmid, processInsId, damx){
            if(isNullOrEmpty(xmid)) {
                warnMsg("xmid为空，无法查看受理图片！");
                return;
            }

            // 这里查看1.0项目档案模型需要指定为 Ywsltp
            var url = "";
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/url/common/bdcda?damx=" + damx + "&xmid=" + xmid + "&gzlslid=" + processInsId,
                type: 'get',
                async: false,
                success: function (data) {
                    console.log("不动产档案url:" + data);
                    url = data;
                }
            });
            return url;
        }
    });
});