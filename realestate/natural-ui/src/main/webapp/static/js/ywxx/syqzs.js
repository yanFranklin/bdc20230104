/**
 * @version 1.0, 2020/10/22
 * @description 证书页面js
 */
// 证书模板地址
var zs3ModelUrl = "C:/GTIS/zs3.fr3";
// 证书模板地址
var zsModelUrl = "C:/GTIS/zs.fr3";
var zdList = {};
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
        // 当前证书的已保存的印刷序列号
        var zsQzysxlh;


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

        // 加载字典
        loadZdList();

        // 正式生成证书时，后台查询证书信息展示
        renderZsData();

        // 当前页的打印类型
        var dylxArr = ["zs"];

        function loadZdList() {
            $.ajax({
                url: '/realestate-natural-ui/rest/v1.0/ywxx/zd',
                type: 'GET',
                dataType: 'json',
                async: false,
                success: function (data) {
                    console.log('zdList:');
                    console.log(data);
                    if (data) {
                        zdList = data;
                    }
                },
                error: function (xhr, status, error) {
                }
            });
        }



        /**
         * 正式生成证书时，后台查询证书信息展示
         */
        function renderZsData() {
            var url;
            if (isNullOrEmpty(zsid)) {
                url = "/realestate-natural-ui/rest/v1.0/zsxx/" + processInsId + "/single";
            } else {
                url = "/realestate-natural-ui/rest/v1.0/zsxx/" + zsid;
            }

            //获取数据
            $.ajax({
                url: url,
                contentType: "application/json;charset=utf-8",
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if (data) {
                        zsid = data.zsid;
                        // 为基本信息赋值
                        zsQzysxlh = data.qzysxlh;

                        // 所有权人代码转为名称
                        var syqzt = data.syqzt;
                        zdList.syqztlx.forEach(function(item, index){
                            if (item.DM == syqzt) {
                                data.syqzt = item.MC;
                            }
                        });

                        // 权利行使方式代码转为名称
                        var qlxsfs = data.qlxsfs;
                        zdList.qlxsfs.forEach(function(item, index){
                            if (item.DM == qlxsfs) {
                                data.qlxsfs = item.MC;
                            }
                        });

                        // 登记单元类型代码转为名称
                        var djdylx = data.djdylx;
                        zdList.djlx.forEach(function(item, index){
                            if (item.DM == djdylx) {
                                data.djdylx = item.MC;
                            }
                        });

                        form.val('form', data);
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

            // 获取页面的印刷序列号
            var qzysxlh = data.field.qzysxlh;
            debugger;
            // 页面的印制号和当前证书已有的印制号一致时，不进行二次验证提交
            if (!isNullOrEmpty(qzysxlh)) {
                param["qzysxlh"] = qzysxlh;
                updateZsQzysxlh(param);
            }

            // 关闭加载
            removeModel();
            // 禁止提交后刷新
            return false;
        });

        // 证书打印
        $(document).on('click', '#printBtn', function () {
            zsPrint(zsModelUrl, zsid, "zs");
        });
        // 证书预览
        $(document).on('click', '#openZsView', function () {
            layer.open({
                title: ['证书预览', 'font-size:16px;font-weight: 700;'],
                type: 2,
                area: ['100%', '100%'],
                resize: true,
                closeBtn: 1,
                content: '/realestate-natural-ui/view/ywxx/zsView.html?zsid=' + zsid+ "&qjgldm=",
                scrollbar: false,
                btn: '',
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
                    //printZs();
                },
                btnAlign: 'c'
            });
            // 禁止提交后刷新
            return false;
        });

        /**
         * 更新证书的权利其他状况和附记
         */
        function updateZsQzysxlh(param) {
            $.ajax({
                url: "/realestate-natural-ui/rest/v1.0/zsxx",
                type: "PATCH",
                data: JSON.stringify(param),
                contentType: 'application/json',
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data) {
                        // successMsg("保存成功！");
                    }
                }, error: function () {
                    warnMsg("保存失败，请重试！");
                }
            });
        }

        /**
         * 单个证书打印
         */
        function zsPrint(modelUrl, zsid, zslx) {
            var userName = "";
            var dataUrl = getIP() + "/realestate-natural-ui/rest/v1.0/print/zs/" + zsid + "/" +
                zslx + "/singleXml?userName=" + userName + "&processInsId=" + processInsId;
            print(modelUrl, dataUrl, true);
        }
    });
})
;