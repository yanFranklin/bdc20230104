var type;
var bdcqzh = "";
var moduleCode = 'checkZsGl';
var bxysh;
var zsid;
var processInsId;
var layerIndex;
layui.use(['jquery', 'form', 'layer'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;

    var zslx;
    var qxdm;
    var zsQzysxlh;
    var bdcdyh;
    $(function () {
        $('.bdc-content').css('min-height', $('body').height() - 56);

        //获取地址栏参数
        var xmid = $.getUrlParam("xmid");
        processInsId = $.getUrlParam("processInsId");
        var qllx = $.getUrlParam("qllx");
        var bdcdyfwlx = $.getUrlParam("bdcdyfwlx");
        var ygzlslid = $.getUrlParam("ygzlslid");
        zsid = $.getUrlParam("zsid");
        type = $.getUrlParam('type');

        // 判断按钮权限
        if (isNullOrEmpty(type) || type === "check") {
            $('#end').addClass('bdc-hide');
            $('#prev').removeClass('bdc-hide').removeClass('bdc-secondary-btn').addClass('bdc-major-btn');
            $('#cxgl').addClass('bdc-hide');
        }

        //完成按钮是否直接结束变成现势
        getBxysh();

        // 加载证书信息
        if (isNullOrEmpty(xmid) || isNullOrEmpty(zsid)) {
            layer.msg("缺失必要参数");
        } else {
            loadZsxx(xmid,bdcqzh,zsid);
        }

        /**
         * 监听完成按钮操作
         */
        form.on('submit(end)', function (data) {
            addModel();
            // 更新权利其他状况、附记和产权证号
            updateZsQlqtzkFjAndCqzh(xmid,bdcqzh,zsid);
            // 关闭加载
            removeModel();
            return false;
        });

        // 监听上一步按钮,关联页面直接回到上一步页面即可
        form.on("submit(prev)", function (data) {
            var backUrl = getContextPath() + "/view/xxbl/xxblUpdateQlxx.html?qllx=" + qllx +
                "&bdcdyfwlx=" + bdcdyfwlx +
                "&xmid=" + xmid +
                "&processInsId=" + processInsId +
                "&type=" + type +
                "&ygzlslid=" + ygzlslid;
            window.location.href = backUrl;
        });

        /**
         * 监听重新关联操作操作
         */
        form.on("submit(cxgl)", function (data) {
            layer.open({
                type: 2,
                title: '关联证书',
                anim: -1,
                shadeClose: true,
                maxmin: true,
                shade: false,
                area: ['100%', '100%'],
                offset: 'auto',
                content: [ "/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=xxblglzs", 'yes'],
                success: function (layero, index) {
                    layerIndex = index;
                },
                end:function(){
                    // search();
                }
            });
            return false;
        });

    });

    //完成按钮是否直接结束变成现势
    function getBxysh(){
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/blxx/bxysh",
            async: true,
            success: function (data) {
                bxysh = data;
            },
            error: function (xhr, status, error) {
            }
        });
    }

});

function toNextZsGlPage(data) {
    layui.use(['jquery', 'form', 'layer'], function () {
        layer = layui.layer;
        layer.close(layerIndex);
        loadZsxx("", data.BDCQZH, data.ZSID);
    });
}


// 加载证书信息
function loadZsxx(xmid,bdcqzh,zsid) {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/blxx/zs?bdcqzh=" + bdcqzh + "&xmid=&zsid=" + zsid,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if (isNullOrEmpty(data)) {
                warnMsg("未获取到信息！");
            } else {
                renderZsylData(data);
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

// 组织证书信息
function renderZsylData(zsData) {
    layui.use(['jquery', 'form', 'layer'], function () {
        form = layui.form;
        if (zsData) {
            // 为基本信息赋值
            form.val('form', zsData);
            bdcdyh = zsData.bdcdyh;
            zsid = zsData.zsid;
            bdcqzh = zsData.bdcqzh;
            zslx = zsData.zslx;
            qxdm = zsData.qxdm;
            zsQzysxlh = zsData.qzysxlh;
            $("#bdcdyh").val(formatBdcdyh(bdcdyh));
            $("#zl").html(zsData.zl);
            generateQrCode(zsData.ewmnr);
            //设置全部字段不能操作
            setAllElementDisabled();
        }
    });
}

/**
 * 增加证书关联关系
 */
function updateZsQlqtzkFjAndCqzh(xmid, bdcqzh) {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/blxx/zs/zsgl",
        type: "GET",
        data: {
            xmid: xmid,
            bdcqzh: bdcqzh,
            zsid: zsid
        },
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            //结束
            if(bxysh || bxysh == 'true') {
                end();
            }else {
                if (window.opener) {
                    layer.msg('办理成功，即将关闭页面', {
                        time: 1000
                    }, function () {
                        // 父窗口重新刷新
                        window.opener.location.reload();
                        window.close();
                    });
                } else {
                    layer.msg('办理成功，即将关闭页面', {
                        time: 1000
                    }, function () {
                        window.close();
                    });
                }
            }
        }, error: function (xhr, status, error) {
            removeModel();
            delAjaxErrorMsg(xhr)
        }
    });

    //变成现势
    function end(){
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/blxx/end?gzlslid=" + processInsId,
            async: true,
            success: function () {
                removeModel();
                layer.msg('办理成功，即将关闭页面', {
                    time: 1000
                }, function () {
                    if(window.opener){
                        layer.msg('办理成功，即将关闭页面', {
                            time: 1000
                        }, function () {
                            // 父窗口重新刷新
                            window.opener.location.reload();
                            window.close();
                        });
                    }else{
                        layer.msg('办理成功');
                        window.close();
                    }
                });
            },
            error: function (xhr, status, error) {
                removeModel();
                delAjaxErrorMsg(xhr);
            }
        });
    }
}