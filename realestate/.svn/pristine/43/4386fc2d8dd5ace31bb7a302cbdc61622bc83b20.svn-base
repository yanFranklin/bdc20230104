/**
 * 发证记录JS代码
 */
layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer'], function () {
    var element = layui.element;
    var form = layui.form;
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    var layer = layui.layer;
    var laydate = layui.laydate;
    // 查询参数
    var zsid = $.getUrlParam("zsid");
    var processInsId = $.getUrlParam("processInsId");
    // 表单ID
    var formStateId = $.getUrlParam('formStateId');
    // 任务id
    var taskId = $.getUrlParam('taskId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');
    var processInstanceType = $.getUrlParam('processInstanceType');
    // potal首页点击的哪个列表todo:待办，list:项目列表，done:已办
    var processInstanceTypeTodo = "todo";
    var processInstanceTypeRl = "rl";

    var sfhb;
    var xmid;
    var slbh;

    // 加载遮罩
    addModel();

    var screenH = document.documentElement.clientHeight;
    $(".content-main").css({'min-height': screenH - 100});

    var $sfzt = $("#sfzt");
    if ($sfzt && $sfzt.length > 0) {
        // 获取缴费状态，未缴费给予提示
        checkJfzt(processInsId).done(function () {
            if (sfyjf) {
                $sfzt.text("已缴费");
                $sfzt.css({"color": "green", "font-weight": "900"});
            } else {
                $sfzt.text("未缴费");
                $sfzt.css({"color": "red", "font-weight": "900"});
                layer.alert("当前件未完成缴费，请确认！");
            }
        });
    }

    var url;
    if (isNullOrEmpty(zsid)) {
        // 展示流程的发证记录，多个项目，默认合并展示
        url = "/realestate-register-ui/rest/v1.0/fzjl/" + processInsId;
    } else {
        // 只展示当前证书的发证信息
        url = "/realestate-register-ui/rest/v1.0/fzjl/zs/" + zsid;
    }

    //获取数据
    $.ajax({
        url: url,
        contentType: "application/json;charset=utf-8",
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            if (data) {
                xmid = data.xmid;
                sfhb = data.sfhb;
                slbh = data.slbh;
                // 为基本信息赋值
                form.val('form', data);

                // 渲染字典项
                var zdList = getZdList();

                if (zdList && zdList.zjzl && data.bdcFzjlZsDTOList) {
                    $.each(data.bdcFzjlZsDTOList, function (lzrIndex, lzr) {
                        lzr["zjzl"] = zdList.zjzl;
                    });
                }
                // 为领证人模板信息赋值
                var getTpl = lzrTpl.innerHTML;
                laytpl(getTpl).render(data.bdcFzjlZsDTOList, function (html) {
                    $("#bzbody").before(html)
                });
                form.render();

                // 获取评价器签名信息
                // 签字信息对象值
                bdcQzxx.xmid = xmid;
                bdcQzxx.gzlslid = processInsId;
                bdcQzxx.slbh = slbh;
                bdcQzxx.bdlx = 3;
                bdcQzxx.qzrlb = 5;
                // queryPjqQmnr();

                // potal首页传过来的参数，取其中的processInstanceType
                // 如果是待办任务和认领列表的，则执行自动更新fzr信息
                // 获取发证人信息
                if (!isNullOrEmpty(processInstanceType) && (processInstanceType == processInstanceTypeTodo
                    || processInstanceType == processInstanceTypeRl) && isNullOrEmpty(data.fzr)) {
                    getFzr();
                }
                // 关闭遮罩
                removeModel();

                getLzrQzxx(xmid);
            }
        },
        error: function (err) {
            removeModel();
            delAjaxErrorMsg(err);
        }

    });

    lay('.test-item').each(function () {
        //设置日期选择
        laydate.render({
            elem: '#djsj',
            type: 'datetime',
            format: 'yyyy年MM月dd日',
            trigger: 'click'
        });
    });

    form.on('select(lzrzjzl)', function (data) {
        console.log(data.elem); //得到select原始DOM对象
        console.log(data.value); //得到被选中的值
        console.log(data.othis); //得到美化后的DOM对象
        addSfzYz(data.value, data.elem);
    });

    //在设置权限后设置判断身份证验证，防止必填冲掉身份证验证
    $("[lay-filter=lzrzjzl]").each(function () {
        addSfzYz($(this).val(), $(this));
    });

    //点击提交时不为空的全部标红
    var isSubmit = true;
    //验证提示信息
    var verifyMsg = "";
    form.verify({
        identitynew: function (value, item) {
            var yzxx = verifyIdNumber(value, item);
            if (!isEmptyObject(yzxx) && !yzxx.isSubmit) {
                $(item).addClass('layui-form-danger');
                isSubmit = yzxx.isSubmit;
                verifyMsg = yzxx.verifyMsg;
            }
        }
    });


    //监听提交
    form.on('submit(submitBtn)', function (data) {
        if (!isSubmit) {
            warnMsg(verifyMsg);
            // 初始化验证信息
            isSubmit = true;
            return false;
        }
        // loading加载
        addModel();

        var splitStr = ",";
        var bdcLzrList = [];
        var bz = data.field.bz;
        var param = {};

        if (sfhb == true) {
            // 合并显示，页面会将当前流程所有的证书ID以“,”分隔加载显示
            var zsidStr = document.getElementsByName("zsid");
            var zsidArr = zsidStr[0].value.split(splitStr);
            var lzr = $("#lzr_" + zsidArr[0]).val();
            var lzrzjh = $("#lzrzjh_" + zsidArr[0]).val();
            var lzrdh = $("#lzrdh_" + zsidArr[0]).val();
            var lzrzjzl = $("#lzrzjzl_" + zsidArr[0]).val();

            var bdcLzr = {};
            bdcLzr["lzr"] = lzr;
            bdcLzr["lzrzjh"] = lzrzjh;
            bdcLzr["lzrdh"] = lzrdh;
            bdcLzr["lzrzjzl"] = lzrzjzl;
            bdcLzr["zsidList"] = zsidArr;

            bdcLzrList.push(bdcLzr);
        } else {
            // 不合并显示时，分别持证会显示多条领证人信息
            var zsidStr = document.getElementsByName("zsid");
            for (var i = 0, j = zsidStr.length; i < j; i++) {

                var zsid = zsidStr[i].value;
                var lzr = $("#lzr_" + zsid).val();
                var lzrzjh = $("#lzrzjh_" + zsid).val();
                var lzrdh = $("#lzrdh_" + zsid).val();
                var lzrzjzl = $("#lzrzjzl_" + zsid).val();

                var zsidList = [];
                zsidList.push(zsid);

                var bdcLzr = {};
                bdcLzr["lzr"] = lzr;
                bdcLzr["lzrzjh"] = lzrzjh;
                bdcLzr["lzrdh"] = lzrdh;
                bdcLzr["lzrzjzl"] = lzrzjzl;
                bdcLzr["zsidList"] = zsidList;

                bdcLzrList.push(bdcLzr);
            }
        }
        param["bz"] = bz;
        param["bdcFzjlZsDTOList"] = bdcLzrList;

        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/fzjl/" + xmid + "/" + sfhb,
            type: "PUT",
            data: JSON.stringify(param),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data && data.code && data.code == 1) {
                    warnMsg(data.msg);
                } else if (data && data > 0) {
                    successMsg("保存成功！");
                } else {
                    warnMsg("提交失败，请重试！");
                }
                window.location.reload();
            }
        });
        // 提交时再次保存发证人信息
        getFzr();

        //更新缮证人为当前用户
        updateSzr();

        removeModel();
        // 禁止提交后刷新
        return false;
    });

    // 存储打印配置的sessionKey
    // 当前页的打印类型
    var dylxArr = [fzjlHb, fzjl];
    var sessionKey = "fzjl";
    setDypzSession(dylxArr, sessionKey);
    //打印
    form.on('submit(printBtn)', function (data) {
        var dylx;
        if (sfhb) {
            dylx = fzjlHb;
        } else {
            dylx = fzjl;
        }
        var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/fzjl/xml?dylx=" + fzjl + "&xmid=" + xmid + "&gzlslid=" + processInsId;
        var modelUrl = fzjlModelUrl;
        var appName = "realestate-register-ui";

        printChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);
        //print(modelUrl, dataUrl, false);

        // 禁止提交后刷新
        return false;
    });

    // 评价器签字
    form.on('submit(pjqBtn)', function (data) {
        var dylx;
        if (sfhb) {
            dylx = fzjlHb;
        } else {
            dylx = fzjl;
        }
        var printDto = {};
        printDto["gzlslid"] = processInsId;
        printDto["xmid"] = xmid;
        printDto["dylx"] = dylx;
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/print/fzjl/pdf/path",
            contentType: "application/json;charset=utf-8",
            type: "POST",
            data: JSON.stringify(printDto),
            async: false,
            success: function (data) {
                if (data) {
                    pjqsign(data, slbh);
                }
            }
        });
        // 禁止提交后刷新
        return false;
    });

    // 评价
    form.on('submit(evaluate)', function (data) {
        commonPj(processInsId,taskId);
    })

    // 人证对比
    form.on('submit(rzdb)', function (data) {
        commonRzdb("","",processInsId);
    })

    // 签字,摩科签字
    form.on('submit(sign)', function (data) {
        var zsidStr = document.getElementsByName("zsid");
        var zsidArr = zsidStr[0].value.split(",");
        var lzr = $("#lzr_" + zsidArr[0]).val();
        var lzrzjh = $("#lzrzjh_" + zsidArr[0]).val();
        mkqz(lzr,lzrzjh,processInsId);
    })

    // 获取表单控制权限
    if (readOnly || !isNullOrEmpty(formStateId)) {
        getStateById(readOnly, formStateId, "fzjl");
    }

    /**
     * 身份证时对证件号增加验证
     * @param zjzl
     * @param elem
     */
    function addSfzYz(zjzl, elem) {
        var zjzlid = $(elem).parent().parent().find("select")[0].id;
        var zjhid = zjzlid.replace("lzrzjzl", "lzrzjh");
        var attrVal = $("#" + zjhid).attr("lay-verify");
        if (zjzl === "1" || zjzl === 1) {
            //增加身份证验证
            if (!isNullOrEmpty(attrVal)) {
                if (attrVal.indexOf("identitynew") < 0) {
                    $("#" + zjhid).attr("lay-verify", attrVal + "|identitynew");
                }
            } else {
                $("#" + zjhid).attr("lay-verify", "identitynew");
            }
        } else {
            //移除身份证验证
            if (!isNullOrEmpty(attrVal) && attrVal.indexOf("identitynew") > -1) {
                $("#" + zjhid).attr("lay-verify", attrVal.replace("identitynew", ""));
                isSubmit = true;
            }
        }
    }

    function getFzr() {
        var url;
        if (!isNullOrEmpty(processInsId)) {
            url = "/realestate-register-ui/rest/v1.0/fzjl/fzr?gzlslid=" + processInsId;
        }
        if (!isNullOrEmpty(xmid)) {
            url = "/realestate-register-ui/rest/v1.0/fzjl/fzr?xmid=" + xmid;
        }
        $.ajax({
            // url: "/realestate-register-ui/rest/v1.0/fzjl/fzr?gzlslid=" + processInsId + "&xmid=" + xmid,
            url: url,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            async: false,
            dataType: "json",
            success: function (data) {
                if (data) {
                    $("#fzr").val(data.fzr);
                    $("#fzrq").val(data.fzrq);
                }
            }
        });

    }

    function updateSzr() {
        var url;
        if (!isNullOrEmpty(processInsId)) {
            url = "/realestate-register-ui/rest/v1.0/fzjl/updateszr?gzlslid=" + processInsId;
        }
        if (!isNullOrEmpty(xmid)) {
            url = "/realestate-register-ui/rest/v1.0/fzjl/updateszr?xmid=" + xmid;
        }
        $.ajax({
            url: url,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            async: false,
            dataType: "json",
            success: function (data) {
                if (data) {
                    $("#szr").val(data.szr);
                }
            }
        });

    }

    /**
     * 获取领证人签字信息
     */
    function getLzrQzxx(xmid) {
        if(isNullOrEmpty(xmid)) {
            return;
        }

        // 默认隐藏签字图片
        $(".lzrqz").hide();

        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/fzjl/lzr/qzxxs",
            type: "POST",
            contentType: 'application/json',
            dataType: "json",
            data: JSON.stringify({"xmid": xmid}),
            success: function (data) {
                if(data && data.length > 0) {
                    // 先设置批量签名内容
                    data.forEach(function (item, index) {
                        if(!item.sxh && 0 !== item.sxh) {
                            // 未定义顺序号，说明是批量签名
                            signStreamData = item.qznr;
                            $(".pjq-qm").attr("src","data:image/png;base64," + item.qznr);
                            $(".pjq-qm").show();
                        }
                    });

                    // 单独设置指定领证人签名
                    data.forEach(function (item, index) {
                        if((item.sxh && item.sxh > -1) || 0 === item.sxh) {
                            // 定义顺序号，说明是单独领证人签名
                            signStreamData2[item.sxh] = item.qznr;
                            $(".pjq-qm" + item.sxh).attr("src","data:image/png;base64," + item.qznr);
                            $(".pjq-qm" + item.sxh).show();
                        }
                    });

                }
            },
            error: function (xhr, status, error) {
            }
        });
    }

});

/**
 * 身份证读卡器设置
 * @param element
 */
function lzrReadIdCard(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            var pName = objTest.Name;
            var pCardNo = objTest.ID;
            // var pAddress = objTest.Address;
            var zsid = trimStr(element.id.replace("lzr_", ""));

            $("#lzrzjzl_" + zsid + " option[value='1']").attr("selected", true);
            $("dd[lay-value='1']").addClass("layui-this");
            $(".layui-select-title input").val("身份证");

            $("#lzr_" + zsid).val(trimStr(pName));
            $("#lzrzjh_" + zsid).val(trimStr(pCardNo));

        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}