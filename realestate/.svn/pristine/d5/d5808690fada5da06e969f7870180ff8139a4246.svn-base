// 定义审批表打印类型
//个人(和个人批量)
var grspb = "grspb";
var grspbName = "个人审批表";

// 汇总（批量）
var plspb = "plspb";
var plspbName = "汇总审批表";

// 公司，单位（和公司批量）
var dwspb = "dwspb";
var dwspbName = "单位审批表";

// 抵押
var dyaspb = "dyaspb";
var dyaspbName = "抵押审批表";

// 首次
var scspb = "scspb";
var scspbName = "首次审批表";

// 海域审批表
var hyspb = "hyspb";
var hyspbName = "海域审批表";

var currentDylx = "";
var currentDyName = "";
var currentModelUrl = "";
var currentDjxl = "";

// 查询到的各个打印类型对应的的登记小类
var grspbDjxl;
var plspbDjxl;
var dwspbDjxl;
var scspbDjxl;
var dyaspbDjxl;
var hyspbDjxl;


// 审批表模板地址
var grspbModelUrl = getIP() + "/realestate-register-ui/static/printModel/grspb.fr3";
var plspbModelUrl = getIP() + "/realestate-register-ui/static/printModel/plspb.fr3";
var dwspbModelUrl = getIP() + "/realestate-register-ui/static/printModel/dwspb.fr3";
var dyaspbModelUrl = getIP() + "/realestate-register-ui/static/printModel/dyaspb.fr3";
var scspbModelUrl = getIP() + "/realestate-register-ui/static/printModel/scspb.fr3";
// 注销类模板
var grspbZxModelUrl = getIP() + "/realestate-register-ui/static/printModel/grspb_zx.fr3";
var dwspbZxModelUrl = getIP() + "/realestate-register-ui/static/printModel/dwspb_zx.fr3";
var dyaspbZxModelUrl = getIP() + "/realestate-register-ui/static/printModel/dyaspb_zx.fr3";
// 建筑物区分所有权共有部分模板
var fdcq3FwqdModelUrl = getIP() + "/realestate-register-ui/static/printModel/fdcq3Fwqd.fr3";
// 海域审批表
var hyspbModelUrl = getIP() + "/realestate-register-ui/static/printModel/hyspb.fr3";

// 当前审核信息ID
var currentShxxid;
// 当前节点名称
var currentJdmc;
var savePdfJdmcArrStr = "初审,审核,核定";
// 当前节点id
var currentJdid;
// 工作流实例ID
var gzlslid;

// 获取参数
gzlslid = $.getUrlParam('processInsId');
// 是否首次证明单审批表
var zsmodel = $.getUrlParam('zsmodel');
// 表单id
var formStateId = $.getUrlParam('formStateId');
// 任务Id
var taskId = $.getUrlParam('taskId');
// 获取表单权限参数readOnly
var readOnly = $.getUrlParam('readOnly');
// 注销流程参数，用于区分是不是注销流程
var zxlc = $.getUrlParam('zxlc');

// 审核信息
var shxxData;

// 存储打印配置的sessionKey
// 当前页的打印类型
var dylxArr = [grspb, grspb + "_zx", dwspb, dwspb + "_zx", plspb, scspb, dyaspb, dyaspb + "_zx", hyspb,hyspb+ "_zx"];
var sessionKey = "bdcShxx";
setDypzSession(dylxArr, sessionKey);
var appName = "realestate-register-ui";

layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer', 'table'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var screenH = document.documentElement.clientHeight;



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

    $(".content-main").css({'min-height': screenH - 100});


    // 获取默认意见，因为要返回当流程的节点信息,所以先执行该方法
    getMryj();
    // 判断要展示的审批表类型
    judgeSpbLx();

    var layerIndex = '';

    $('.bdc-kxyj-cancel').on('click', function () {
        layer.close(layerIndex);
    });

    $('.bdc-kxyj-sure-btn').on('click', function () {
        var checkStatus = table.checkStatus('checkBoxTableId');
        var backTaskDtos = checkStatus.data;
        console.log(backTaskDtos);
        if (checkStatus.data.length != 1) {
            layer.msg('请选择一条数据进行操作！');
            return false;
        }
        $("#" + currentJdid + "_shyj").val(checkStatus.data[0].yj);
        layer.close(layerIndex);
    });

    $('.shxx_yj').dblclick(function () {
        getKxyj();
    });
    var col = [[
        {type: 'radio', width: 50, fixed: 'left'},
        {field: 'mryjid', title: '', hide: true},
        {field: 'yj', title: '意见'}
    ]];


    // 保存按钮保存当前节点的审核意见
    form.on('submit(saveBtn)', function () {
        addModel();
        var param = [];
        if (!isNullOrEmpty(currentShxxid) && !isNullOrEmpty(currentJdid)) {
            var shxxTemp = {};
            var shyj = $("#" + currentJdid + "_shyj").val().trim();
            $.each(shxxData, function (index, shxx) {
                if (shxx.shxxid == currentShxxid) {
                    shxxTemp["shxxid"] = currentShxxid;
                    shxxTemp["shyj"] = shyj;
                    param.push(shxxTemp);
                }
            });
        }
        if (param.length > 0) {
            updateShxx(param);
        }
        removeModel();
        // 禁止提交后刷新
        return false;
    });

    // 证书预览
    form.on('submit(zsView)', function (data) {
        var url = BASE_URL + "/bdcZs?processInsId=" + gzlslid + "&zsmodel=" + zsmodel + "&zsyl=true&time=" + new Date().getTime();

        var width = $(window).width() + "px";
        var height = $(window).height() + "px";
        var index = layer.open({
            title: "证书预览",
            type: 2,
            content: url,
            area: [width, height],
            maxmin: true
        });
        layer.full(index);
    });

    // 证书预览
    form.on('submit(fdcq3FwqdBtn)', function (data) {
        var modelUrl = fdcq3FwqdModelUrl;
        var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/bdcdy/" + gzlslid + "/fdcq3Fwqd/xml";
        print(modelUrl, dataUrl, false);

        // 禁止提交后刷新
        return false;
    });

    /**********************************************打印开始*****************************************************/

    // 审批表pdf预览
    /*form.on('submit(spbyl)', function (data) {
    });

    // 个人审批表打印
    form.on('submit(grSpbBtn)', function (data) {
    });*/

    // 公司审批表打印
    /*form.on('submit(dwSpbBtn)', function (data) {

    });
    // 汇总（抵押汇总）
    form.on('submit(plSpbBtn)', function (data) {

    });
    // 首次审批表打印
    form.on('submit(scSpbBtn)', function (data) {
        var dylx = scspb;
        if (isNullOrEmpty(zxlc)) {
            zxlc = false;
        }
        var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslid + "/" + dylx + "/xml?zxlc=" + zxlc + "&djxlListStr=" + scspbDjxl;
        var modelUrl = scspbModelUrl;
        // print(modelUrl, dataUrl, false);
        printChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);
        // 禁止提交后刷新
        return false;
    });
    // 抵押审批表打印
    form.on('submit(dyaSpbBtn)', function (data) {

    });

    //海域审批表
    form.on('submit(hySpbBtn)', function (data) {

    });*/



    // 初始化下拉按钮和下拉选项元素 ----------------开始----------------/
    // 个人审批表
    $(".print-btn-grspb").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }

        $("#printGrspb").show();
        setUiWidth($(this), $("#printGrspb"));
    });
    $(".print-btn-grspb li").click(function () {
        $("#printGrspb").hide();
    });

    // 单位审批表
    $(".print-btn-dwspb").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }

        $("#printDwspb").show();
        setUiWidth($(this), $("#printDwspb"));
    });
    $(".print-btn-dwspb li").click(function () {
        $("#printDwspb").hide();
    });

    // 抵押审批表
    $(".print-btn-dyaspb").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }

        $("#printDyaspb").show();
        setUiWidth($(this), $("#printDwspb"));
    });
    $(".print-btn-dwspb li").click(function () {
        $("#printDwspb").hide();
    });

    // 抵押审批表
    $(".print-btn-scspb").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }

        $("#printScspb").show();
        setUiWidth($(this), $("#printScspb"));
    });
    $(".print-btn-scspb li").click(function () {
        $("#printScspb").hide();
    });

    // 汇总审批表
    $(".print-btn-plspb").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }

        $("#printPlspb").show();
        setUiWidth($(this), $("#printPlspb"));
    });
    $(".print-btn-dwspb li").click(function () {
        $("#printPlspb").hide();
    });

    // 海域审批表
    $(".print-btn-hyspb").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }

        $("#printHyspb").show();
        setUiWidth($(this), $("#printHyspb"));
    });
    $(".print-btn-dwspb li").click(function () {
        $("#printHyspb").hide();
    });

    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'print') {
                return;
            }
            elem = elem.parentNode;
        }
        $("#printGrspb").hide();
        $("#printHyspb").hide();
        $("#printDyaspb").hide();
        $("#printPlspb").hide();
        $("#printScspb").hide();
        $("#printDwspb").hide();

    });


    /**********************************************打印结束*****************************************************/

    function setUiWidth(buttonElement, uiElement) {
        var X = buttonElement.offset().top;
        var Y = buttonElement.offset().left;
        var w = buttonElement.innerWidth();
        var scrollH = $(document).scrollTop();
        if(scrollH > 0){
            uiElement.css({top: X + 42 - scrollH, right: $('body').width() - Y - w - 15});
        }else {
            uiElement.css({top: X + 32, right: $('body').width() - Y - w - 15});
        }
        var uiWidth = 0;
        var liArray = uiElement.find("li");
        if (liArray.length > 0) {
            var textNumber = 0;
            for (var i = 0; i < liArray.length; i++) {
                var liEl = liArray[i];
                var liText = $(liEl).text();
                if (textNumber < liText.length) {
                    textNumber = liText.length;
                }
            }
            var countWidth = (textNumber+2) * 13;
            if (uiWidth < countWidth) {
                uiWidth = countWidth;
            }
        }
        uiElement.css({"min-width":w,"width": uiWidth + 20});
    }

    /**
     * 判断当前流程审批表的打印模式
     */
    function judgeSpbLx() {
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/shxx/dylx/" + gzlslid + "?time=" + new Date().getTime(),
            contentType: "application/json;charset=utf-8",
            type: "GET",
            // async: false,
            success: function (data) {
                if (data.grspb && data.grspb.length > 0) {
                    $("#grSpbBtn").removeAttr("style");
                    grspbDjxl = data.grspb.join(",");
                    currentDjxl = grspbDjxl;
                    currentDylx += grspb + ",";
                    currentDyName += grspbName + ",";
                    currentModelUrl = hyspbModelUrl;
                }
                if (data.dwspb && data.dwspb.length > 0) {
                    $("#dwSpbBtn").removeAttr("style");
                    dwspbDjxl = data.dwspb.join(",");
                    currentDjxl = dwspbDjxl;

                    currentDylx += dwspb + ",";
                    currentDyName += dwspbName + ",";

                    currentModelUrl = dwspbModelUrl;
                }
                if (data.plspb && data.plspb.length > 0) {
                    $("#plSpbBtn").removeAttr("style");
                    plspbDjxl = data.plspb.join(",");
                    currentDjxl = plspbDjxl;

                    currentDylx += plspb + ",";
                    currentDyName += plspbName + ",";
                    currentModelUrl = plspbModelUrl;
                }
                if (data.scspb && data.scspb.length > 0) {
                    $("#scSpbBtn").removeAttr("style");
                    scspbDjxl = data.scspb.join(",");
                    currentDjxl = scspbDjxl;
                    currentDylx += scspb + ",";
                    currentDyName += scspbName + ",";
                    currentModelUrl = scspbModelUrl;
                }
                if (data.dyaspb && data.dyaspb.length > 0) {
                    $("#dyaSpbBtn").removeAttr("style");
                    dyaspbDjxl = data.dyaspb.join(",");
                    currentDjxl = dyaspbDjxl;

                    currentDylx += dyaspb + ",";
                    currentDyName += dyaspbName + ",";
                    currentModelUrl = dyaspb;
                }
                // 建筑物区分所有权首次登记，打印房屋清单
                if (data.fdcq3Fwqd && data.fdcq3Fwqd.length > 0) {
                    $("#fdcq3Fwqd").removeAttr("style");
                }
                //海域审批表
                if (data.hyspb && data.hyspb.length > 0) {
                    $("#hySpbBtn").removeAttr("style");
                    hyspbDjxl = data.hyspb.join(",");
                    currentDjxl = hyspbDjxl;

                    currentDylx += hyspb + ",";
                    currentDyName += hyspbName + ",";

                    currentModelUrl = hyspbModelUrl;
                }
            },
            error: function () {
                warnMsg("判断打印类型失败！");
            }
        });
    }

    /**
     * 获取可选意见
     * @returns {string}
     */
    function getKxyj() {
        var kxyj = '';
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/shxx/kxyj?time=" + new Date().getTime(),
            contentType: "application/json;charset=utf-8",
            type: "GET",
            // async: false,
            data: {taskId: taskId},
            success: function (data) {
                if (data) {
                    kxyj = data;
                } else {
                    layer.msg('未配置该节点下的可选意见！');
                    return false;
                }
                renderKxyjTable(kxyj, col);
                layerIndex = layer.open({
                    type: 1,
                    title: "可选意见",
                    content: $('#kxyjContainer'),
                    area: ['430px', 'auto'],
                    cancel: function () {
                        $("#kxyjContainer").css('display', 'none');
                    }, success: function () {
                        table.resize('checkBoxTableId');
                    }
                });
            },
            error: function (e) {
                if (e.status == 500) {
                    var responseText = JSON.parse(e.responseText);
                    layer.msg(responseText.msg);
                } else {
                    layer.msg('请求异常！');
                }
            }
        });
        return kxyj;
    }

    function renderKxyjTable(data, col) {
        table.render({
            elem: '#checkboxTable',
            id: 'checkBoxTableId',
            cols: col,
            data: data
        });
    }

    function getMryj() {
        const url = "/realestate-register-ui/rest/v1.0/shxx/mryj/" + taskId + "?time=" + new Date().getTime();
        //获取数据
        $.ajax({
            url: url,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            async: false,
            success: function (data) {
                if (data && !isNullOrEmpty(data.shxxid)) {
                    currentShxxid = taskId;
                    currentJdmc = data.jdmc;
                    currentJdid = data.jdid;

                    // 页面获取审核信息,并设置默认意见
                    getShxxList(data.shyj);
                }
            },
            error: function () {
                warnMsg("未获取到签名节点信息！");
            }
        });
        return false;
    }

    function getShxxList(mryj) {
        // 加载遮罩
        addModel();
        //获取数据
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/shxx/list?" + new Date().getTime(),
            contentType: "application/json;charset=utf-8",
            type: "GET",
            async: false,
            data: {gzlslid: gzlslid, taskId: taskId, currentJdmc: currentJdmc, onlyCurrentJd: false},
            success: function (data) {
                shxxData = data;
                // 模板渲染
                processData(data);

                // 后台做控制，如果当前没有签名意见，则获取默认意见
                if (!isNullOrEmpty(mryj)) {
                    $("#" + currentJdid + "_pre").html(mryj);
                    $("#" + currentJdid + "_shyj").val(mryj);
                }

                // 控制签名权限
                resetDisabled();

                // 设置意见行高样式（超过一行左对齐，首行空两格）
                var $text = $('.bdc-text');
                for (var i = 0; i < $text.length; i++) {
                    if ($($text[i]).height() > 32) {
                        $($text[i]).addClass('bdc-text-lines');
                    }
                }

                // 获取表单控制权限
                if (readOnly || !isNullOrEmpty(formStateId)) {
                    getStateById(readOnly, formStateId, "bdcShxx");
                }
            },
            error: function () {
                warnMsg("获取签名数据失败！");
            }
        });
        removeModel();
        // 禁止页面刷新
        return false;
    }

    function resetDisabled() {
        $.each(shxxData, function (index, item) {
            if (isNullOrEmpty(currentJdid) || item.jdid != currentJdid) {
                // 根据当前节点设置按钮权限
                $("#" + item.jdid + "_sign").removeAttr("onclick");
                $("#" + item.jdid + "_sign").addClass("prohibit_sign");
                $("#" + item.jdid + "_deleteSign").removeAttr("onclick");
                $("#" + item.jdid + "_deleteSign").addClass("prohibit_deleteSign");
                // 设置当前节点文本框权限
                $("#" + item.jdid + "_shyj").attr('disabled', 'disabled');
                $("#" + item.jdid + "_shyj").addClass("bdc-prohibit-text");
            }
        });
    }


    //模板引擎加载数据
    function processData(data) {
        var getTpl = qmyjDiv.innerHTML; //获取自己定义的模板元素
        laytpl(getTpl).render(data, function (html) {
            $("#templateFatherDiv").html(html); //加载到主元素中
            form.render();
        });
    }
});

/**
 * 获取签名
 * @param jdmc
 * @param shxxid
 * @returns {boolean}
 */
function sign(jdmc, shxxid) {
    if (isNullOrEmpty(jdmc) || isNullOrEmpty(shxxid)) {
        warnMsg("未生成该节点的信息，不允许签名!");
        return false;
    }
    if (shxxid != currentShxxid) {
        warnMsg("不是" + currentJdmc + "节点，不允许签名!");
        return false;
    }
    var shxxDO = new Object();
    shxxDO.jdmc = jdmc;
    // shxxid为当前的taskId
    shxxDO.shxxid = shxxid;
    shxxDO.shyj = $("#" + currentJdid + "_shyj").val();
    shxxDO.gzlslid = gzlslid;
    //获取数据
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/shxx/sign",
        contentType: "application/json;charset=utf-8",
        type: "PUT",
        data: JSON.stringify(shxxDO),
        success: function (data) {
            $("#" + currentJdid + "_img").attr('src', data.qmtpdz);
            $("#" + currentJdid + "_time").html(data.qmsj);

            startSaveShxxPdf();
        },
        error: function () {
            warnMsg("签名失败！");
        }
    });
}

function startSaveShxxPdf(){
    console.log("当前节点："+currentJdmc);
    // 指定节点才要保存附件
    if(currentJdmc && savePdfJdmcArrStr.indexOf(currentJdmc) > -1){
        console.log("当前打印名称："+currentDyName);
        console.log("当前打印类型："+currentDylx);
        // 点击签名 要保存当前所有类型的审批表
        if(currentDyName.indexOf(",") > 0){
            var dyName = currentDyName.substring(0,currentDyName.length-1);
            var dylx = currentDylx.substring(0,currentDylx.length-1);

            // 多个情况下 循环保存pdf
            for(var i = 0;i<dyName.split(",").length;i++){
                spbPdf(dylx.split(",")[i],dyName.split(",")[i]);
            }
        }
    }
}

/**
 * 删除签名
 * @param jdmc
 * @param shxxid
 * @returns {boolean}
 */
function deleteSign(jdmc, shxxid) {
    if (isNullOrEmpty(jdmc) || isNullOrEmpty(shxxid)) {
        warnMsg("审核信息为空!");
        return false;
    }
    if (shxxid != currentShxxid) {
        warnMsg("不是" + currentJdmc + "节点，不允许删除!");
        return false;
    }
    // 清除审核意见和签名信息
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/shxx/sign/" + shxxid,
        contentType: "application/json;charset=utf-8",
        type: "DELETE",
        // data: JSON.stringify(shxxDO),
        success: function (data) {
            $("#" + currentJdid + "_shyj").val(null);
            $("#" + currentJdid + "_pre").html(null);
            $("#" + currentJdid + "_img").attr('src', null);
            $("#" + currentJdid + "_time").html(null);
        },
        error: function () {
            warnMsg("删除签名信息失败！");
        }
    });
}

/**
 * 编辑完审核意见，更新审核意见
 * @param param
 * @returns {boolean}
 */
function updateShxx(param) {
    if (param == null || param.length == 0) {
        return false;
    }
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/shxx/shxxList",
        type: "PATCH",
        data: JSON.stringify(param),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            successMsg(currentJdmc + "意见修改成功！");
        },
        error: function () {
            warnMsg(currentJdmc + "意见修改失败！");
        }
    });
}

/**
 * 调节页面输入框的高度
 * @param event
 */
function changeContent(event) {
    var event = event || window.event;
    var target = event.target || event.srcElement;
    var $textarea = $(target);
    var $pre = $textarea.siblings('.pre');
    $pre.html($textarea.val());
}

/**
 *
 * @param dylx 打印类型
 * @param dataUrl 获取数据源地址
 * @param hiddeMode fr3打印是否隐藏控件打印预览
 */
function printPdf(dylx,dataUrl,sessionKey,dyName) {
    console.log("打印配置参数", dypzMap);
    console.log("xml数据源", dataUrl);
    if (dypzMap && !isEmptyObject(dypzMap) && dypzMap[dylx]) {
        var bdcDysjPzDO = dypzMap[dylx];
        var pdfPath = bdcDysjPzDO.pdfpath;
        //设置pdf打印参数
        var bdcPdfDyQO = {};
        bdcPdfDyQO.appName = "realestate-register-ui";
        bdcPdfDyQO.dataUrl = dataUrl;
        bdcPdfDyQO.pdfpath = pdfPath;
        bdcPdfDyQO.fileName = sessionKey;
        $.ajax({
            type: "POST",
            url: getContextPath() + "/rest/v1.0/dypz/common/pdf/param/redis",
            contentType: 'application/json',
            data: JSON.stringify(bdcPdfDyQO),
            success: function (data) {
                if (data) {
                    var pdfUrl = getIP()+ getContextPath() + "/rest/v1.0/dypz/common/pdf/" + data;
                    // 不能再从redis中取数据，redis取数据后会删除key,所以重新取大云的url
                    pdfUrl = savePdfToWjzx(pdfUrl,gzlslid,dyName);
                    //window.open(getContextPath() + '/static/lib/pdf/web/viewer.html?file=' + encodeURIComponent(pdfUrl), "PDF");
                }
            }, error: function (e) {
                warnMsg("pdf生成异常！")
            }
        });
        return;
    }
}

/**
 * 保存pdf到文件中心
 * @param pdfUrl
 * @param gzlslid
 */
function savePdfToWjzx(pdfUrl, gzlslid,dyName){
    var newPdfUrl = "";
    $.ajax({
        type: "GET",
        url: "/realestate-register-ui/rest/v1.0/shxx/savePdfToWjzx",
        async: false,
        data: {pdfUrl:pdfUrl,gzlslid:gzlslid,dyName:dyName},
        success: function (data) {
            if(data && data != ""){
                newPdfUrl = data;
                console.log("上传审批表完毕，请于附件材料中查看！！！！！")
            }else{
                warnMsg("保存审批表pdf到文件中心异常！")
            }
        }, error: function (e) {
            warnMsg("保存审批表pdf到文件中心异常！")
        }
    });
    return newPdfUrl;
}

// 个人审批表
function grSpbFr3() {
    var dylx = grspb;
    var modelUrl = grspbModelUrl;
    if (isNullOrEmpty(zxlc)) {
        zxlc = false;
    }
    if (zxlc) {
        dylx += "_zx";
        modelUrl = grspbZxModelUrl;
    }
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslid + "/" + dylx + "/xml?zxlc=" + zxlc + "&djxlListStr=" + grspbDjxl;
    //print(modelUrl, dataUrl, false);
    printChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);
    // 禁止提交后刷新
    return false;
}

// 单位审批表
function dwSpbFr3() {
    var dylx = dwspb;
    var modelUrl = dwspbModelUrl;
    if (isNullOrEmpty(zxlc)) {
        zxlc = false;
    }
    if (zxlc) {
        dylx += "_zx";
        modelUrl = dwspbZxModelUrl;
    }
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslid + "/" + dylx + "/xml?zxlc=" + zxlc + "&djxlListStr=" + dwspbDjxl;
    //print(modelUrl, dataUrl, false);
    printChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);
    // 禁止提交后刷新
    return false;
}

// 汇总审批表
function plSpbFr3() {
    var dylx = plspb;
    if (isNullOrEmpty(zxlc)) {
        zxlc = false;
    }
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslid + "/" + dylx + "/xml?zxlc=" + zxlc + "&djxlListStr=" + plspbDjxl;
    var modelUrl = plspbModelUrl;
    // print(modelUrl, dataUrl, false);
    printChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);
    // 禁止提交后刷新
    return false;
}


function dyaSpbFr3() {
    var dylx = dyaspb;
    var modelUrl = dyaspbModelUrl;
    if (isNullOrEmpty(zxlc)) {
        zxlc = false;
    }
    if (zxlc) {
        dylx += "_zx";
        modelUrl = dyaspbZxModelUrl;
    }
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslid + "/" + dylx + "/xml?zxlc=" + zxlc + "&djxlListStr=" + dyaspbDjxl;

    // print(modelUrl, dataUrl, false);
    printChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);
    // 禁止提交后刷新
    return false;
}

// 抵押审批表
function scSpbFr3() {
    var dylx = scspb;
    if (isNullOrEmpty(zxlc)) {
        zxlc = false;
    }
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslid + "/" + dylx + "/xml?zxlc=" + zxlc + "&djxlListStr=" + scspbDjxl;
    var modelUrl = scspbModelUrl;
    // print(modelUrl, dataUrl, false);
    printChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);
    // 禁止提交后刷新
    return false;
}



function hySpbFr3(){
    var dylx = hyspb;
    var modelUrl = hyspbModelUrl;
    if (isNullOrEmpty(zxlc)) {
        zxlc = false;
    }
    if (zxlc) {
        dylx += "_zx";
        // modelUrl = grspbZxModelUrl;
    }
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslid + "/" + dylx + "/xml?zxlc=" + zxlc + "&djxlListStr=" + hyspbDjxl;
    //print(modelUrl, dataUrl, false);
    printChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);
    // 禁止提交后刷新
    return false;
}

function spbPdf(dylx,dyName){

    if(dylx == grspb) {
        currentDjxl = grspbDjxl;
    }else if(dylx == dwspb){
        currentDjxl = dwspbDjxl
    }else if(dylx == hyspb){
        currentDjxl = hyspbDjxl
    }else if(dylx == dyaspb){
        currentDjxl = dyaspbDjxl
    }else if(dylx == plspb){
        currentDjxl = plspbDjxl
    }else if(dylx == scspb){
        currentDjxl = scspbDjxl
    }
    if (isNullOrEmpty(zxlc)) {
        zxlc = false;
    }
    if (zxlc) {
        dylx += "_zx";
    }
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslid + "/" + dylx + "/xml?zxlc=" + zxlc + "&djxlListStr=" + currentDjxl;
    printPdf(dylx, dataUrl, sessionKey,dyName);
    // 禁止提交后刷新
    return false;
}

/**
 * 预览pdf
 */
function ylPdf(dylx,dyName){
    $.ajax({
        type: "GET",
        url: "/realestate-register-ui/rest/v1.0/shxx/ylPdf",
        async: false,
        data: {gzlslid:gzlslid,dyName:dyName},
        success: function (data) {
            if(data && data != ""){
                window.open(getContextPath() + '/static/lib/pdf/web/viewer.html?file=' + encodeURIComponent(data), "PDF");
            }else{
                warnMsg("未生成文件，无法预览！")
            }
        }, error: function (e) {
            warnMsg("查询pdf文件异常！")
        }
    });
}


