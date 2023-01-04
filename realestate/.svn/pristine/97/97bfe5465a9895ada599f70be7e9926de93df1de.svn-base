var type;
var zsid="";
var moduleCode = 'checkZs';
layui.use(['jquery', 'form', 'layer'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;

    var zslx;
    var qxdm;
    // 当前证书的已保存的印刷序列号
    var zsQzysxlh;
    var bdcdyh;
    $(function () {
        $('.bdc-content').css('min-height', $('body').height() - 56);

        //获取地址栏参数
        var xmid = $.getUrlParam("xmid");
        var qllx = $.getUrlParam("qllx");
        var processInsId = $.getUrlParam("processInsId");
        var ygzlslid = $.getUrlParam('ygzlslid');
        var bdcdyfwlx = $.getUrlParam("bdcdyfwlx");
        zsid = $.getUrlParam("zsid");
        if (isNullOrEmpty(zsid)) {
            zsid="";
        }
        type = $.getUrlParam('type');

        // 判断按钮权限
        if (isNullOrEmpty(type) || type === "check") {
            $('#end').addClass('bdc-hide');
            $('#prev').removeClass('bdc-hide').removeClass('bdc-secondary-btn').addClass('bdc-major-btn');
            $('#initZs').addClass('bdc-hide');
            $('#zxql').addClass('bdc-hide');
        }
        // 证书 id 不为空，说明是分别持证，隐藏页面的完成按钮，显示保存按钮
        if (!isNullOrEmpty(zsid)) {
            $('#end').html("保存");
            $('#prev').addClass('bdc-hide');
            // 分别持证的注销权利和重新生成证书按钮显示在台账页面
            $('#initZs').addClass('bdc-hide');
            $('#zxql').addClass('bdc-hide');
        }
        // 根据配置内容
        setElementAttrByModuleAuthority(moduleCode);
        // 加载证书信息
        if (!isNullOrEmpty(xmid)) {
            loadZsxx(xmid);
        } else {
            layer.msg("缺失必要参数");
        }

        /**
         * 监听完成按钮操作
         * 更新权利其他状况和附记
         * 保存印刷序列号
         */
        form.on('submit(end)', function (data) {
            addModel();
            // 更新权利其他状况、附记和产权证号
            updateZsQlqtzkFjAndCqzh(data.field.zsid, xmid, data.field.qlqtzk, data.field.fj, data.field.bdcqzh);
            // 关闭加载
            removeModel();
        });

        // 监听上一步按钮，在新建页面操作退回到上一步会删除证书
        form.on("submit(prev)", function (data) {
            var backUrl = getContextPath() + "/view/xxbl/xxblUpdateQlxx.html?qllx=" + qllx + "&bdcdyfwlx=" + bdcdyfwlx + "&xmid=" + xmid + "&processInsId=" + processInsId + "&type=" + type + "&ygzlslid=" + ygzlslid;
            if (!isNullOrEmpty(type) || type !== "new") {
                window.location.href = backUrl;
            } else {
                layer.confirm("退回上一步会删除证书，是否还要退回？", {
                    title: "提示",
                    btn: ["确认", "取消"],
                    btn1: function (index) {
                        deleteZs(data);
                        window.location.href = backUrl;
                    },
                    btn2: function (index) {
                        layer.close(index);
                    }
                });
            }
        });

        // 监听 获取印制号 按钮，获取印制号和缮证日期
        form.on("submit(getYzh)", function (data) {
            generateSzrq(data);

            // loading加载
            addModel();

            // 定义印制号的获取前置条件(在获取印制号的时候给初始值)
            var bdcYzhQO = {};
            bdcYzhQO["zslx"] = zslx;
            bdcYzhQO["syqk"] = yly;
            bdcYzhQO["qxdm"] = qxdm;
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/yzh/" + zsid,
                type: "POST",
                data: JSON.stringify(bdcYzhQO),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    if (data && data.code) {
                        warnMsg(data.msg);
                        return false;
                    }
                    if (data && data.bdcYzhsymxDOList.length > 0) {
                        $("#qzysxlh").val(data.qzysxlh);
                        // 获取成功后，同时更新全局变量
                        zsQzysxlh = data.qzysxlh;
                        // 更新按钮状态
                        setYzhBtnAttr(zsQzysxlh);
                        successMsg("获取成功！");
                    } else {
                        warnMsg("没有可用的印制号！");
                    }
                    removeModel();
                }, error: function () {
                    warnMsg("获取失败，请重试！");
                    removeModel();
                }
            });
            // 禁止刷新页面
            return false;
        });

        // 监听 缮证日期 按钮，生成保存缮证日期
        form.on("submit(getSzrq)", function (data) {
            generateSzrq(data);
        });

        // 生成缮证信息
        function generateSzrq(data) {
            var zsidArr = [];
            zsidArr.push(data.field.zsid);
            var szxx = saveSzr(zsidArr, '');
            $("#szrq").val(szxx.szrq);
        }

        /**
         * 监听初始化证书
         * type 为 update 时，用户选择生成证书时调用
         */
        form.on('submit(initZs)', function (data) {
            confirmZs();
        });

        function confirmZs() {
            layer.confirm("是否确认生成证书？", {
                title: "提示",
                btn: ["确认", "取消"],
                btn1: function (index) {
                    layer.close(index);
                    addModel();
                    checkQlr();
                },
                btn2: function (index) {
                    layer.close(index);
                }
            });
        }

        /**
         * 判断是否添加权利人
         */
        function checkQlr() {
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/qlr",
                type: 'GET',
                dataType: 'json',
                data: {xmid: xmid},
                success: function (data) {
                    if (!isNullOrEmpty(data)) {
                        InitZs()
                    } else {
                        removeModel();
                        warnMsg("请先添加权利人信息！")
                    }
                },
                error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        /**
         * 重新生成证书
         * @return {int} 返回zslx
         */
        function InitZs() {
            // TODO 重新生成证书是否需要还从 session 中获取
            var bdcqzh = sessionStorage.getItem("xxbl" + processInsId);
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/zs/initZs",
                type: 'GET',
                data: {
                    gzlslid: processInsId,
                    bdcqzh: bdcqzh,
                    xmid: xmid
                },
                success: function (data) {
                    window.location.reload();
                },
                error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        /**
         * 监听注销权利按钮
         * type 为 update 时，提供一键注销功能
         */
        form.on("submit(zxql)", function () {
            zxQl(checkQlxx);
            return false;
        });

        /**
         * 判断是否存现限制权利
         */
        function checkQlxx(index) {
            layer.close(index);
            addModel();
            // 去除 页面 中的空格

            var selectDataList = [];
            var bdcGzYzsjDTO = {};
            bdcGzYzsjDTO.bdcdyh = bdcdyh;
            selectDataList.push(bdcGzYzsjDTO);

            var bdcGzYzQO = {};
            bdcGzYzQO.zhbs = $("#process").val() + "_XZBDCDY";
            bdcGzYzQO.paramList = selectDataList;

            $.ajax({
                url: getContextPath() + '/rest/v1.0/blxx/bdcGzyz',
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(bdcGzYzQO),
                success: function (data) {
                    if (data.length > 0) {
                        // 未通过验证，页面提示信息
                        removeModel();
                        loadTsxx(data);
                        gzyztsxx();
                    } else {
                        // 通过验证，组织数据注销权利
                        var postObj = {};
                        postObj.xmidList = [xmid];
                        postObj.zxyy = $('#zxyy').val();
                        $.ajax({
                            type: 'POST',
                            url: "/realestate-register-ui/rest/v1.0/blxx/zxql",
                            contentType: "application/json;charset=utf-8",
                            data: JSON.stringify(postObj),
                            dataType: "text",
                            success: function (data) {
                                removeModel();
                                if (data === "history") {
                                    warnMsg("当前项目已经被注销，不可以重复注销！");
                                } else {
                                    successMsg("注销成功");
                                }
                            },
                            error: function (xhr, status, error) {
                                delAjaxErrorMsg(xhr)
                            }
                        });
                    }
                }, error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr);
                }
            });
        }

    });

    // 加载证书信息
    function loadZsxx(xmid) {
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/blxx/zs?xmid=" + xmid +"&zsid="+zsid,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (isNullOrEmpty(data)) {
                    warnMsg("未获取到信息！");
                } else {
                    zslx = data.zslx;
                    qxdm = data.qxdm;
                    zsQzysxlh = data.qzysxlh;
                    zsid = data.zsid;
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
        if (zsData) {
            // 为基本信息赋值
            form.val('form', zsData);
            bdcdyh = zsData.bdcdyh;
            $("#bdcdyh").val(formatBdcdyh(bdcdyh));
            $("#zl").html(zsData.zl);
            generateQrCode(zsData.ewmnr);
            //设置权限控制样式
            if (!isNullOrEmpty(type) && type === "check") {
                setAllElementDisabled();
            }
        }
    }

    // 删除证书信息，回退到上一步
    function deleteZs(xmid) {
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/blxx/zs?xmid=" + xmid,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                renderZsylData(data[0]);
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }

});

/**
 * 更新权利其他状况、附记和产权证号
 * @param zsid 证书 id
 * @param xmid 项目 id
 * @param qlqtzk 权利其他状况
 * @param bdcqzh 不动产权证号
 * @param fj 附记
 */
function updateZsQlqtzkFjAndCqzh(zsid, xmid, qlqtzk, fj, bdcqzh) {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/blxx/zs/zsxx",
        type: "GET",
        data: {
            zsid: zsid,
            xmid: xmid,
            qlqtzk: qlqtzk,
            fj: fj,
            bdcqzh: bdcqzh
        },
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            if (data == -1) {
                warnMsg("当前证号已经被使用！")
            } else {
                forward(xmid);
            }
        }, error: function (xhr, status, error) {
            removeModel();
            delAjaxErrorMsg(xhr)
        }

    });
}

function forward(xmid){
    var blShDO = {};
    // 修改数据补录类型为 1 修改
    blShDO.xmid = xmid;
    $.ajax({
        url: getContextPath() + '/rest/v1.0/blxx/blsh/queryBdcBlshDO',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(blShDO),
        success: function (data) {
            if (isNullOrEmpty(data.shryid)) {
                layer.open({
                    type: 2,
                    skin: 'layui-layer-lan',
                    title: '任务转发',
                    area: ['960px', '490px'],
                    content: '../xxbl/send.html?blshid=' + data.blshid
                });
            }
        }, error: function (xhr, status, error) {
            removeModel();
            delAjaxErrorMsg(xhr);
        }
    });
}