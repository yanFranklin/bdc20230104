/**
 * 南通  不动产权首次登记证明单JS代码
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer', 'response'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var response = layui.response;

    // 查询参数
    var bdcdyh = $.getUrlParam("bdcdyh");
    var processInsId = $.getUrlParam('processInsId');
    // 表单id
    var formStateId = $.getUrlParam('formStateId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');
    // 获取是否是证书预览
    var zsyl = $.getUrlParam('zsyl');
    // 证书ID
    var zsid = $.getUrlParam('zsid');
    if(!isNotBlank(zsid)){
        zsid ="";
    }

    var zslx;
    var szr;

    // loading加载
    var index = layer.load(2);

    if ($.getUrlParam('zsyl') == 'true') {
        $('.bdc-content-fix').addClass('bdc-hide');
        $('.content-div').css('padding-top', '10px');
    }

    getZdList();
    $(function () {
        //滚动时头部固定
        var $contentTitle = $('.bdc-content-fix:visible');
        console.log($contentTitle);
        if ($contentTitle.length != 0) {
            //console.log('aaa');
            defaultStyle();
        }

        function defaultStyle() {
            if ($(window).scrollTop() > 10) {
                $contentTitle.css('top', '0');
            } else if ($(window).scrollTop() <= 10) {
                $contentTitle.css('top', '10px');
            }
        }

        $(window).resize(function () {
            if ($contentTitle.length != 0) {
                defaultStyle();
            }
        });
        $(window).on('scroll', function () {
            if ($contentTitle.length != 0) {
                if ($(this).scrollTop() > 10) {
                    $contentTitle.css('top', '0');
                } else if ($(this).scrollTop() <= 10) {
                    $contentTitle.css('top', '10px');
                }
            }
        });
    });

    // 正式生成证书时，后台查询证书信息展示
    renderZmdData();

    /**
     * 正式生成证书时，后台查询证书信息展示
     */
    function renderZmdData() {
        var url;
        if (isNullOrEmpty(bdcdyh)) {
            bdcdyh = "";
        }
        if (!isNullOrEmpty(zsyl) && zsyl == "true") {
            zsyl = true;
            readOnly = true;
        } else {
            zsyl = false;
        }
        url = "/realestate-register-ui/rest/v1.0/zsxx/zmd?gzlslid=" + processInsId + "&bdcdyh=" + bdcdyh + "&zsyl=" + zsyl+"&zsid="+zsid;
        //获取数据
        $.ajax({
            url: url,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data) {
                    zslx = data.zslx;
                    zsid = data.zsid;
                    szr = data.szr;
                    console.log(data.zdzhyt);
                    console.log(zdList);
                    if (!isNullOrEmpty(data.zdzhyt) && !isEmptyObject(zdList) && !isEmptyObject(zdList.tdyt)) {
                        $.each(zdList.tdyt, function (index, item) {
                            if (data.zdzhyt == item.DM) {
                                data.zdzhyt = item.MC;
                                return false;
                            }
                        });
                    }
                    // 编号
                    if (!isNullOrEmpty(data.nf) && !isNullOrEmpty(data.zhlsh)) {
                        data.bh = data.nf + data.zhlsh;
                    }
                    if(data.sfzf != 1){
                        data.zdzhmj = '';
                    }
                    // 为基本信息赋值
                    form.val('form', data);
                    // 初始化页面设置工作按钮状态
                    setQsztBtnAttr(data.qszt);
                }
                //设置权限控制样式
                if (readOnly || !isNullOrEmpty(formStateId)) {
                    getStateById(readOnly, formStateId, "bdcSczmd");
                    if (readOnly == true || readOnly == "true") {
                        setPrintBtnAbled();
                        // 初始化页面设置工作按钮状态
                        setQsztBtnAttr(data.qszt);
                    }
                }
                // 关闭加载
                layer.close(index);
            }
        });
    }

    /**
     * 渲染时间
     */
    lay('.test-item').each(function () {
        //设置日期选择
        laydate.render({
            elem: '#djsj',
            type: 'datetime',
            format: 'yyyy年MM月dd日',
            trigger: 'click'
        });
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

    // 证书打印
    form.on('submit(printBtn)', function (data) {
        printZmd();
        // 禁止提交后刷新
        return false;
    });

    /**
     * 打印证书
     */
    function printZmd() {
        if(sczmdModel == "ckzmd"){
            zsPrint(ckzmdModelUrl, zsid, sczmdModel);
        }else{
            zsPrint(zmdModelUrl, zsid, sczmdModel);
        }
    }

});