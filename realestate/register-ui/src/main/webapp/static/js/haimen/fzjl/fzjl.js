/**
 * 发证记录JS代码
 */
var processInsId;
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer', 'formSelects'], function () {
    var element = layui.element;
    var form = layui.form;
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var formSelects = layui.formSelects;
    // 查询参数
    processInsId = $.getUrlParam("processInsId");
    var sfhb = $.getUrlParam("sfhb");
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
    // 控制办结按钮是否可见（默认不可见）
    var endBtnVisible = $.getUrlParam('endBtnVisible');
    var xmid;

    // 加载遮罩
    addModel();

    var screenH = document.documentElement.clientHeight;
    $(".content-main").css({'min-height': screenH - 100});


    var zjzlArr = [];
    if (!isEmptyObject(zdData.zjzl)) {
        $.each(zdData.zjzl, function (index, item) {
            var zdParam = {};
            zdParam["name"] = item.MC;
            zdParam["value"] = item.DM;
            if (item.MC == "身份证") {
                zdParam["selected"] = "selected";
            }
            zjzlArr.push(zdParam);
        })
    }

    //使用js渲染下拉框
    formSelects.data('select01', 'local', {
        arr: zjzlArr
    });

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
    var zsQlr = "";
    var url;
    if (isNullOrEmpty(sfhb)) {
        url = "/realestate-register-ui/rest/v1.0/fzjl/one/" + processInsId;
    } else {
        if ('true' === sfhb || true == sfhb) {
            sfhb = true;
        } else {
            sfhb = false;
        }
        url = "/realestate-register-ui/rest/v1.0/fzjl/one/" + processInsId + '?sfhb=' + sfhb;
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
                // 为基本信息赋值
                form.val('form', data);

                // 渲染字典项
                var zdList = getZdList();

                if (zdList && zdList.zjzl && data.bdcFzjlZsDTOList) {
                    $.each(data.bdcFzjlZsDTOList, function (lzrIndex, lzr) {
                        lzr["zjzl"] = zdList.zjzl;
                        zsQlr += lzr["qlr"] + zf_ywdh;
                    });
                }
                // 为领证人模板信息赋值
                var getTpl = lzrTpl.innerHTML;
                laytpl(getTpl).render(data.bdcFzjlZsDTOList, function (html) {
                    $("#bzbody").before(html);
                });
                form.render();

                // 控制办结按钮是否可见(只对2.0遗留数据)
                if (!isNullOrEmpty(endBtnVisible) && (endBtnVisible === true || endBtnVisible === "true")) {
                    $("#endBtn").removeAttr("style");
                }

                // potal首页传过来的参数，取其中的processInstanceType
                // 如果是待办任务和认领列表的，则执行自动更新fzr信息
                // 获取发证人信息
                if (!isNullOrEmpty(processInstanceType) && (processInstanceType == processInstanceTypeTodo
                    || processInstanceType == processInstanceTypeRl) && isNullOrEmpty(data.fzr)) {
                    getFzr();
                }
                // 设置银行发证提醒
                if (data.sftsYcyh == "true" && zsQlr.indexOf("中国邮政储蓄银行股份有限公司南通市分行") > -1) {
                    layer.alert("含有邮储银行，请将证明单独存放！");
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

    /**
     * 更新领证人信息
     * @param param
     */
    function updateLzrxx(param) {
        // 保存信息时，更新发证人信息
        getFzr();
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
            }
        });

        // 保存签字图片相关信息
        saveLzrQz(xmid);
    }

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
                bdcLzr["lzr"] = deleteWhitespace(lzr, "edge");
                bdcLzr["lzrzjh"] = deleteWhitespace(lzrzjh, "edge");
                bdcLzr["lzrdh"] = lzrdh;
                bdcLzr["lzrzjzl"] = lzrzjzl;
                bdcLzr["zsidList"] = zsidList;

                bdcLzrList.push(bdcLzr);
            }
        }
        param["bz"] = bz;
        param["bdcFzjlZsDTOList"] = bdcLzrList;
        addModel();
        // 先验证领证人信息是否符合
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/fzjl/lzr/check?xmid=" + xmid,
            type: "POST",
            data: JSON.stringify(param),
            contentType: 'application/json',
            dataType: "text",
            async: false,
            success: function (data) {
                removeModel();
                if (!isNullOrEmpty(data)) {
                    var submitIndex = layer.open({
                        type: 1,
                        title: '领证人验证',
                        skin: 'bdc-small-tips',
                        area: ['320px'],
                        content: data + '，您确定要保存吗？',
                        btn: ['确定', '取消'],
                        btnAlign: 'c',
                        yes: function () {
                            //确定操作
                            updateLzrxx(param);
                            layer.close(submitIndex);
                        },
                        btn2: function () {
                            //取消
                        }
                    });
                } else {
                    updateLzrxx(param);
                }
            }, error: function (e) {
                console.info(e.responseText);
            }
        });
        removeModel();
        // 禁止提交后刷新
        return false;
    });

    //批量修改
    form.on('submit(plxgBtn)', function (data) {
        plxg(null, true);
    });

    // 存储打印配置的sessionKey
    // 当前页的打印类型
    var dylxArr = [fzjlHb, fzjl];
    var sessionKey = "fzjl";
    setDypzSession(dylxArr, sessionKey);
    var appName = "realestate-register-ui";

    //打印
    form.on('submit(printBtn)', function (data) {
        var dylx;
        if (sfhb) {
            dylx = fzjlHb;
        } else {
            dylx = fzjl;
        }
        var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/fzjl/one/xml?dylx=" + dylx + "&xmid=" + xmid + "&gzlslid=" + processInsId;
        var modelUrl = fzjlModelUrl;
        // print(modelUrl, dataUrl, false);
        printChoice(dylx, appName, dataUrl, modelUrl, false, sessionKey);
        // 禁止提交后刷新
        return false;
    });
    // 办结事件
    form.on('submit(endProcess)', function (data) {
        if (sfyjf) {
            endProcessAjax(processInsId).done(function () {
                if (endProcessSucceed) {
                    var parentIndex;
                    if (parent.queryObject && parent.fzjlIndex) {
                        parentIndex = parent;
                    }
                    if (parent.parent.queryObject && parent.parent.fzjlIndex) {
                        parentIndex = parent.parent;
                    }
                    if (parentIndex) {
                        layer.alert("办结成功，即将关闭页面！");
                        parentIndex.tableReload('pageTable', {data: JSON.stringify(parentIndex.queryObject)}, '/realestate-inquiry-ui/dtcx/list/result');
                        setTimeout(function () {
                            parentIndex.layer.close(parentIndex.fzjlIndex);
                        }, 1500);
                    }
                }
            });
        } else {
            warnMsg("未缴费，不允许办结！");
        }
        // 禁止提交后刷新
        return false;
    });

    //完成收费事件
    $(document).on('click','#sf',function(){
        var slbh = $("input[name='slbh']").val();
        if (isNullOrEmpty(slbh)) {
            warnMsg("受理编号不能为空！")
            return false;
        }
        var checkData = checkState(slbh);
        if (checkData.sfzt == true) {
            layer.confirm('已收费，是否重新收费？', {
                    btn: ["是", "否"]
                },
                function (index) {
                    sf(slbh, 0);
                    layer.close(index);
                    return false;
                }, function (index) {
                    layer.close(index);
                    return false;
                }
            );
        } else {
            sf(slbh, 1);
        }
    });
    // 获取表单控制权限
    if (readOnly || !isNullOrEmpty(formStateId)) {
        getStateById(readOnly, formStateId, "fzjl");
        if (readOnly == true || readOnly == "true") {
            setPrintBtnAbled();
        }
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

    /**
     * 领证人签字:调用签字版进行签字,获取签字图片信息
     */
    var signStreamData;
    form.on('submit(lzrqz)', function (data) {
        layer.open({
            title: '领证人签字',
            type: 2,
            area: ['700px','430px'],
            content: "/realestate-register-ui/haimen/fzjl/qzb.html"
            ,cancel: function(){
            }
            ,success: function () {
            }
            ,end: function (index, layero) {
                var signStream = layui.data('signStream');
                if(signStream && signStream.data) {
                    signStreamData = signStream.data;
                    // 批量签字后清除单独签字内容
                    signStreamData2 = {};
                    $(".lzrqz").attr("src","data:image/png;base64," + signStream.data);
                    $(".lzrqz").show();
                }
            }
        });
    });

    /**
     * 保存领证人签字信息
     * @param xmid 项目ID
     */
    function saveLzrQz(xmid) {
        if(isNullOrEmpty(xmid) || (isNullOrEmpty(signStreamData) && (isEmptyObject(signStreamData2)))) {
            // 生成发证记录PDF文件到附件，没有签字图片直接生成
            generateFzjlPdf(xmid);
            return;
        }

        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/fzjl/lzr/qzxx",
            type: "POST",
            data: JSON.stringify({"xmid": xmid, "signStreamData": signStreamData, "signStreamData2": signStreamData2}),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 生成发证记录PDF文件到附件，有签字图片需要等图片保存完
                generateFzjlPdf(xmid);
            },
            error: function (xhr, status, error) {
                failMsg("保存领证人签字信息失败,请重试");
            }
        });
    }

    /**
     * 生成发证记录PDF文件到附件
     * @param xmid 项目ID
     */
    function generateFzjlPdf(xmid) {
        if(isNullOrEmpty(xmid)) {
            return;
        }

        var dylx = sfhb == true ? fzjlHb : fzjl;

        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/fzjl/pdf",
            type: "POST",
            data: JSON.stringify({"xmid": xmid, "dylx": dylx, "gzlslid": processInsId}),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
            },
            error: function (xhr, status, error) {
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
                            $(".lzrqz").attr("src","data:image/png;base64," + item.qznr);
                            $(".lzrqz").show();
                        }
                    });

                    // 单独设置指定领证人签名
                    data.forEach(function (item, index) {
                        if((item.sxh && item.sxh > -1) || 0 === item.sxh) {
                            // 定义顺序号，说明是单独领证人签名
                            signStreamData2[item.sxh] = item.qznr;
                            $(".lzrqz" + item.sxh).attr("src","data:image/png;base64," + item.qznr);
                            $(".lzrqz" + item.sxh).show();
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