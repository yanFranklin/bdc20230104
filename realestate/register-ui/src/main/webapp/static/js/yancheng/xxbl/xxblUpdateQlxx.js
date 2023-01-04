layui.config({
    base: '../../static/lib/form-select/'
});

// 获取表单权限参数readOnly
var xmid;
var type;
var className;
/**
 * 错误信息提示框
 */
var warnLayer;
var moduleCode = 'xxblUpdateQlxx';
layui.use(['jquery', 'form', 'layer'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;
    $(function () {
        $('.bdc-content').css('min-height', $('body').height() - 56);

        // 0. 获取地址栏参数
        processInsId = $.getUrlParam('processInsId');
        var ygzlslid = $.getUrlParam('ygzlslid');
        type = $.getUrlParam('type');
        var qllx = $.getUrlParam('qllx');
        var bdcdyfwlx = $.getUrlParam('bdcdyfwlx');
        var sfsczs = $.getUrlParam('sfsczs');
        xmid = $.getUrlParam('xmid');
        var qllxym;

        /**
         * 1. 页面按钮权限
         * 修改操作显示生成证书按钮
         */
        if (isNullOrEmpty(type) || type == "check") {
            $('#save').addClass('bdc-hide');
            $('#finish').addClass('bdc-hide');
        }

        /**
         * 1.2 不生成证书则修改页面样式
         */
        if (!isNullOrEmpty(sfsczs)) {
            if (sfsczs === "0" || sfsczs === 0) {
                // 进度栏修改为 3 等分
                $('.bdc-header > p ').css("width", "33.3%");
                $('.bdc-header > p > span').addClass('bdc-hide');
                $('.qlimg').addClass('bdc-hide');
                // 不生成证书，则在权利页面显示
                if (type === "update") {
                    $('#zxql').removeClass('bdc-hide');
                }
                // 隐藏 next
                $('#next').addClass('bdc-hide');
                // 显示 end 按钮
                $('#finish').removeClass('bdc-hide');
                // 隐藏 4. 证书/明信息
                $('#zsm').addClass('bdc-hide');
                // 隐藏 3/4：核验权利信息
                $('#sczs').addClass('bdc-hide');
                // 将不生成证书的样式显示出来
                $('#bsczs').removeClass('bdc-hide');
                $('#bsczs-title').removeClass('bdc-hide');
            }
        }

        // 2. 渲染提示信息
        generate();


        /**
         * 根据参数处理 iframe 中权利页面的 URL
         */
        if (!isNullOrEmpty(processInsId)) {
            qllxym =getQlxxYm(qllx,bdcdyfwlx);
            if (isNullOrEmpty(qllxym)) {
                layer.msg("页面未获取到权利信息")
            }

            // 设置 iframe 的 src 属性
            if (isNullOrEmpty(type) || type === "check") {
                readOnly = true;
            } else {
                readOnly = false;
            }
            var url = getIP() + "/realestate-register-ui/view/qlxx/" + qllxym + ".html?isCrossOri=false&xmid=" + xmid + "&readOnly=" + readOnly;
            $("iframe").attr("src", url);
            setTimeout(function () {
                $("iframe").height($("iframe").contents().find(".layui-form").outerHeight(true) + 94);
                var submitBtn = qlxx.window.document.getElementById("submitBtn");
                // 隐藏 iframe 中的保存 按钮
                submitBtn.classList.add("bdc-hide");
                setWindowElementAttrByModuleAuthority(moduleCode, qlxx.window);
            }, 1000);
        }

        /**
         * 监听上一页按钮
         * 直接返回到基本信息页面
         */
        form.on("submit(prev)", function () {
            window.location.href = getContextPath() + "/view/xxbl/xxblUpdateinfo.html?processInsId=" + processInsId + "&type=" + type + "&ygzlslid=" + ygzlslid;
        });

        /**
         * 修改关联关系
         */
        form.on("submit(connect)", function () {
            var index = layer.open({
                type: 2,
                title: '关联产权',
                maxmin: true,
                area: ['1150px', '600px'],
                content: '../xxbl/xxbl_glcq.html?gzlslid=' + processInsId
            });
            layer.full(index);
        });

        function confirmZs() {
            // layer.confirm("是否确认生成证书？", {
            //     title: "提示",
            //     btn: ["确认", "取消"],
            //     btn1: function (index) {
            //         layer.close(index);
            //         addModel();
                    checkQlr();
            //     },
            //     btn2: function (index) {
            //         layer.close(index);
            //     }
            // });
        }

        /**
         * 监听下一步按钮
         * new 类型，则判断是否生成证书
         * check 和 update 先判断证书还是证明再跳转页面
         */
        form.on("submit(next)", function () {
            // 增加遮罩
            addModel();
            forward();
        });

        /**
         * 存在审核，办理完成也不会影响
         */
        form.on("submit(finish)", function () {
            layer.msg('办理成功，即将关闭页面', {
                time: 1000
            }, function () {
                window.close();
            });
        });

        /**
         * 监听初始化证书
         * type 为 update 时，用户选择生成证书时调用
         */
        form.on('submit(initZs)', function (data) {
            confirmZs();
        });

        /**
         * 判断是否存现限制权利
         */
        function checkQlxx(index) {
            layer.close(index);
            addModel();
            // 去除 页面 中的空格
            var bdcdyh = deleteWhitespace(qlxx.window.document.getElementById("bdcdyh").value);
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

        /**
         * 监听注销权利按钮
         * type 为 update 时，提供一键注销功能
         */
        form.on("submit(zxql)", function () {
            zxQl(checkQlxx);
            return false;
        });

        /**
         * 初始化表名
         */
        function initTableName(data) {
            switch (data) {
                case "tdsyq":
                    className = "BdcTdsyqDO";
                    break;
                case "cfdj":
                    className = "BdcCfDO";
                    break;
                case "dyaq":
                    className = "BdcDyaqDO";
                    break;
                case "dyiq":
                    className = "BdcDyiqDO";
                    break;
                case "fdcq":
                    className = "BdcFdcqDO";
                    break;
                case "gzwsyq":
                    className = "BdcGjzwsyqDO";
                    break;
                case "hysyq":
                    className = "BdcHysyqDO";
                    break;
                case "jsydsyq":
                    className = "BdcJsydsyqDO";
                    break;
                case "lq":
                    className = "BdcLqDO";
                    break;
                case "qtxgql":
                    className = "BdcQtxgqlDO";
                    break;
                case "tdcbjyqNyddqtsyq":
                    className = "BdcTdcbnydsyqDO";
                    break;
                case "ygdj":
                    className = "BdcYgDO";
                    break;
                case "yydj":
                    className = "BdcYyDO";
                    break;
                case "fdcqXmndzfw":
                    className = "BdcFdcqFdcqxmDO";
                    break;
                case "jzwqfsyqyzgybf":
                    className = "BdcFdcq3DO";
                    break;
                default:
            }
        }

        function saveJyhth(jyhth) {
            var bdcxm = {};
            bdcxm['xmid'] = xmid;
            bdcxm['jyhth'] = jyhth;
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/xmxx",
                type: 'POST',
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify(bdcxm),
                success: function () {
                },
                error: function (err) {
                    throw err;
                }
            });
        }

        /**
         * 监听保存权利按钮
         */
        form.on("submit(save)", function () {
            // 1. 组织 form 数据为 JSON 格式
            var form = $("#qlxx").contents().find("form").serializeArray();
            var result = "{";
            $.each(form, function (i, field) {
                if (field.name === "bdcdyh") {
                    field.value = deleteWhitespace(field.value);
                }
                if (field.name === "gydbdcdyh") {
                    field.value = deleteWhitespace(field.value);
                }
                result = result + "\"" + field.name + "\":\"" + field.value + "\"";
                if (i < form.length - 1) {
                    result = result + "\,";
                }
                if (field.name === "jyhth" && !isNullOrEmpty(field.value)) {
                    // 保存交易合同号
                    saveJyhth(field.value);
                }
            });
            result = result + "}";
            // 2. 获取权利类型
            initTableName(qllxym);
            // 3. 发起 ajax 请求，修改数据并保存相关日志
            addModel();
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/qlxx?xmid=" + xmid + "&className=" + className,
                type: "POST",
                contentType: "application/json;charset=utf-8",
                data: result,
                dataType: "json",
                success: function (data) {
                    removeModel();
                    successMsg("保存成功");
                },
                error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr)
                }
            });

        });

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
         * 生成证书
         * @return {int} 返回zslx
         */
        function InitZs() {
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
                    forward(data);
                },
                error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        function setNextDisable() {
            $("#next").addClass('layui-btn-disabled')
                .removeClass('bdc-major-btn')
                .removeClass('bdc-delete-btn');
        }

        /**
         * 判断 zslx 跳转页面
         * @param data 初始化生成证书如果有返回值，就无需要判断证书类型
         */
        function forward(data) {
            if (isNullOrEmpty(data)) {
                $.ajax({
                    url: "/realestate-register-ui/rest/v1.0/blxx/zs/zslx",
                    type: 'GET',
                    async: false,
                    data: {gzlslid: processInsId},
                    success: function (zslx) {
                        console.info(zslx);
                        removeModel();
                        if (isNullOrEmpty(zslx) || zslx == 'no') {
                            // 检查模式不允许创建证书
                            if (type === "check") {
                                layer.msg("未生成证书信息");
                                setNextDisable();
                            } else {
                                confirmZs();
                            }
                        } else {
                            judgeZslx(zslx);
                        }
                    }, error: function (e) {
                        console.error(e);
                    }
                });
            } else {
                judgeZslx(data);
            }
        }

        /**
         * 根据返回 zslx 判断页面跳转
         * @param zslx
         */
        function judgeZslx(zslx){
            var url = '';
            switch (zslx) {
                case 'zs':
                    url = getContextPath() + "/view/xxbl/xxblCheckZs.html?xmid=" + xmid + "&qllx=" + qllx + "&bdcdyfwlx=" + bdcdyfwlx + "&processInsId=" + processInsId + "&type=" + type + "&ygzlslid=" + ygzlslid;
                    break;
                case 'zm':
                    url = getContextPath() + "/view/xxbl/xxblCheckZm.html?xmid=" + xmid + "&qllx=" + qllx + "&bdcdyfwlx=" + bdcdyfwlx + "&processInsId=" + processInsId + "&type=" + type + "&ygzlslid=" + ygzlslid;
                    break;
                case 'list':
                    url = getContextPath() + "/view/xxbl/xxblZsList.html?xmid=" + xmid + "&qllx=" + qllx + "&bdcdyfwlx=" + bdcdyfwlx + "&processInsId=" + processInsId + "&type=" + type + "&ygzlslid=" + ygzlslid;
                    break;
                case 'no':
                    url = '';
                    break;
            }
            if (isNullOrEmpty(url)) {
                warnMsg("未生成证书信息");
            } else {
                window.location.href = url
            }
        }
    });
});