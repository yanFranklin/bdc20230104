layui.use(['layer', 'element', 'form', 'jquery', 'laydate', 'table'], function () {
    var form = layui.form;
    var $ = layui.jquery,
        layer = layui.layer;
    var alertSize = 0;
    var confirmSize = 0;
    //验证提示信息弹出层
    var yztsxxLayer;
    //验证成功执行函数
    var successcallback;

    //页面默认加载提示信息
    generateCommonTsxx();


    function generateCommonTsxx() {
        //<!--规则提示信息弹出层-->
        var gztsHtml = '<div class="layui-hide">' +
            '<div lay-filter="tsxx" id="tsxxCommon">' +
            '<a class="layui-btn layui-btn-sm bdc-ignore-btn" id="ignoreCommonAll" onclick="removeTsxxAll(this)">忽略全部</a>' +
            '<div class="bdc-right-tips-box" id="bottomTips">' +
            '<div class="tsxx" id="alertCommonInfo"></div>' +
            '<div class="tsxx" id="confirmCommonInfo"></div>' +
            '</div>' +
            '</div>' +
            '</div>';
        $('body').append(gztsHtml);
        form.render();
        resetSelectDisabledCss();

    }


    /**
     * 调用规则验证
     * @param gzlx 规则类型：1-流程规则验证,2-其他验证
     * @param callback 验证通过执行函数
     */
    window.gzyzCommon = function (gzlx, bdcGzYzQO, callback) {
        var url = "";
        if (gzlx === 1) {
            url = "/bdcGzyz";
        } else {
            url = "/bdcGzyz/qtyz";
        }
        successcallback = callback;
        gzyz(bdcGzYzQO, url);
    };

    /*
    * 执行规则验证
    *
    * @param url 规则验证服务地址
    * @param bdcGzYzQO 规则验证参数
    * @param callback 验证通过执行的函数
    * */
    window.doGzyz = function (url, bdcGzYzQO, callback) {
        successcallback = callback;
        gzyz(bdcGzYzQO, url);
    };

    // 规则验证
    function gzyz(bdcGzYzQO, url) {
        getReturnData(url, JSON.stringify(bdcGzYzQO), "POST", function (tsdata) {
            if (tsdata.length > 0) {
                removeModal();
                loadCommonTsxx(tsdata);
            } else {
                //验证通过,执行后续
                successcallback();
            }

        }, function (error) {
            delAjaxErrorMsg(error);

        })

    }


    /**
     * 加载提示信息到页面
     */
    function loadCommonTsxx(data) {
        $("#confirmCommonInfo").html('');
        $("#alertCommonInfo").html('');
        $.each(data, function (index, item) {
            if (item.yzlx === "confirm") {
                confirmSize++;
                $("#confirmCommonInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + item.msg + '<a href="javascrit:;" onclick="removeTsxx(this);return false" >忽略</a></p>');
            } else if (item.yzlx === "alert") {
                alertSize++;
                $("#alertCommonInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + item.msg + '</p>');
            }

            //当只存在提示信息时展现全部忽略按钮，存在警告不展示忽略按钮
            if (alertSize > 0) {
                $("#ignoreCommonAll").remove();
            }
        });
        //打开弹出层
        yztsxxLayer = layer.open({
            type: 1,
            title: '提示信息',
            skin: 'bdc-tips-right',
            shade: [0],
            area: ['600px'],
            offset: 'r', //右下角弹出
            time: 5000000, //2秒后自动关闭
            anim: 2,
            content: $('#tsxxCommon').html(),
            success: function () {
                $('.layui-layer-ico .layui-layer-close .layui-layer-close1').on('click', function () {
                    layer.close(yztsxxLayer);
                    generateCommonTsxx();
                })
            },
            cancel: function () {
                layer.close(yztsxxLayer);
                generateCommonTsxx();
            }
        });


    }

    /**
     * 忽略
     */
    window.removeTsxx = function (elem) {
        //点击忽略时增加日志记录，同步处理
        var data = elem.parentElement.innerText.replace(/忽略/g, "").replace(/查看/g, "");
        addRemoveLog(data);
        addModel();
        var tsxxInfo = $(elem).parents(".tsxx");
        if (tsxxInfo.length > 0) {
            var tsxxID = tsxxInfo.attr("id");
            if (tsxxID === "alertCommonInfo") {
                alertSize--;
            } else {
                confirmSize--;
            }
        } else {
            confirmSize--;
        }
        $(elem).parent().remove();

        //在没有警告提示下创建
        if (alertSize === 0 && confirmSize === 0) {
            layer.close(yztsxxLayer);
            generateCommonTsxx();
            //执行后续
            successcallback();
        } else {
            removeModal();
        }

    };

    //忽略全部
    window.removeTsxxAll = function (elem) {
        //点击忽略时增加日志记录，同步处理
        var data = elem.nextElementSibling.innerText.replace(/忽略/g, "").replace(/查看/g, "");
        addRemoveLog(data);
        var val = $('.layui-this').val();
        //在没有警告提示下创建
        layer.close(yztsxxLayer);
        generateCommonTsxx();
        addModel();
        //执行后续
        successcallback();

    };

    //增加验证忽略日志
    function addRemoveLog(data) {
        getReturnData("/bdcGzyz/addIgnoreLog", JSON.stringify({data: data}), "POST", function () {
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        })
    }


});