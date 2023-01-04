// 当前审核信息ID
var currentShxxid;
// 当前节点名称
var currentJdmc;
// 当前节点id
var currentJdid;
// 工作流实例ID
var gzlslid;
var form ;
var $;
var laytpl;
var table;
// 是否合并打印审批表
var sfhb = $.getUrlParam('sfhb');
var zxlc = $.getUrlParam('zxlc');
layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer', 'table'], function () {
    form = layui.form;
    $ = layui.jquery;
    laytpl = layui.laytpl;
    table = layui.table;
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
    // 获取参数
    gzlslid = $.getUrlParam('processInsId');
    // 表单id
    var formStateId = $.getUrlParam('formStateId');
    // 任务Id
    var taskId = $.getUrlParam('taskId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');
    // 注销流程参数，用于区分是不是注销流程

    // 审核信息
    var shxxData;

    //判断流程类型加载页面按钮
    // setTimeout("loadButtonArea('bdcshxx')", 10);
    // 取消延时加载，防止表单中心设置权限无效，42214 【淮安市】修改
    loadButtonArea('bdcshxx')
    // 获取默认意见，因为要返回当流程的节点信息,所以先执行该方法
    getMryj();

    if (isNullOrEmpty(sfhb)) {
        judgeSpbSfhb();
    }

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

    /**
     * 判断当前流程审批表的打印模式
     */
    function judgeSpbSfhb() {
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/bdcdy/lclx/" + gzlslid,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            async: false,
            success: function (data) {
                if (data && (data == "pllc" || data == "plzh")) {
                    sfhb = true;
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
            url: "/realestate-register-ui/rest/v1.0/shxx/kxyj",
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
        const url = "/realestate-register-ui/rest/v1.0/shxx/mryj/" + taskId;
        //获取数据
        $.ajax({
            url: url,
            type: "GET",
            contentType: "application/json;charset=utf-8",
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
            error: function (XMLHttpRequest, textStatus, errorThrown) {
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
            url: "/realestate-register-ui/rest/v1.0/shxx/list?",
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
    var shxxDO = {};
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
        },
        error: function () {
            warnMsg("签名失败！");
        }
    });
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
        dataType: "json",
        contentType: 'application/json',
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


function loadButtonArea(ymlx) {
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/shxx/lclx',
        type: "GET",
        data:{gzlslid:gzlslid},
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (data) {
            if (data) {
                var tpl = buttonAreaTpl.innerHTML;
                var json={
                    lclx:data
                }
                laytpl(tpl).render(json, function (html) {
                    $("#buttonArea").html(html); //加载到主元素中
                    form.render();
                });
                titleShowUi();
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            warnMsg("加载页面按钮失败!");
        }
    });
}
function titleShowUi() {
    $(".print-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").show();
        setUiWidth($(this), $("#print"));
    });
    $(".print-btn li").click(function () {
        $("#print").hide();
    });
    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'print') {
                return;
            }
            $("#print").hide();
            elem = elem.parentNode;
        }
    });
}

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
// 审批表打印
/**
 * @param dylx 打印类型
 * @param sfzhlc 是否组合流程
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description
 * @date : 2020/6/29 14:37
 */

    // 存储打印配置的sessionKey
    // 当前页的打印类型
var dylxArr = ["bdcSqSpb", "bdcSqSpbPl"];
var sessionKey = "bdcShxx";
setDypzSession(dylxArr, sessionKey);
function printSpb(dylx,sfzhlc) {
    var dyaxmid = [];
    var cqxmid = [];
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/qlxx/sfdya',
        type: "GET",
        data:{gzlslid:gzlslid},
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (data) {
            if (data) {
                for(var i=0;i<data.length;i++) {
                    if(data[i].dydj) {
                        if(!sfzhlc) {
                            if(dylx === "bdcSqSpb") {
                                dylx = "dyabdcSqSpb";
                            }
                        }
                        dyaxmid.push(data[i].bdcXm.xmid);
                    } else {
                        cqxmid.push(data[i].bdcXm.xmid);
                    }
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            warnMsg("打印判断是否抵押失败!");
        }
    });
    if ((!isNullOrEmpty(sfhb) && sfhb === "true") || sfhb) {
        dylx = dylx+"Pl";
    }
    if (isNullOrEmpty(zxlc)) {
        zxlc = false;
    }
    // 获取受理页面选择的收件材料
    var sjclIdsStr = sessionStorage.yxsjclids;
    if (isNullOrEmpty(sjclIdsStr)) {
        sjclIdsStr = sessionStorage.sjclidLists;
    }
    var appName = "realestate-register-ui";
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslid + "/" + dylx + "/xml?zxlc=" + zxlc + "&sjclIdsStr=" + sjclIdsStr + "&dysl=1";
    var modelUrl = sqSpbModelUrl;
    if (dylx === "dyabdcSqSpb" || dylx === "dyabdcSqSpbPl") {
        dataUrl = dataUrl + "&xmid=" + dyaxmid[0];
        modelUrl = dyaSqSpbModelUrl;
    } else {
        dataUrl = dataUrl + "&xmid=" + cqxmid[0];
    }

    // 查询审核节点数目
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/print/spb/node/' + gzlslid,
        type: "GET",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (data) {
            if (data == 2) {
                if (modelUrl == sqSpbModelUrl) {
                    modelUrl = sqSpbTwoModelUrl;
                }
                if (modelUrl == dyaSqSpbModelUrl) {
                    modelUrl = dyaSqSpbTwoModelUrl;
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            warnMsg("打印判断审核节点数目失败!");
        }
    });

    // 同一个展示模板，数据源不同
    printChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);
}