/**
 * 权属证明查询标准版页面JS处理
 */
// 打印的模板现场维护，此路径只是名义上的路径，具体打印时候会更改为读取配置的打印模板放置路径，但是模板名称要一致
// (土地)权属证明打印模板
var tdQszmDymb = getIP() + "/realestate-inquiry-ui/static/printModel/tdqszm.fr3";
// (房屋)权属证明打印模板
var fwQszmDymb = getIP() + "/realestate-inquiry-ui/static/printModel/fwqszm.fr3";
// 证明验证对应的组合规则标识
// 权属证明
var ZHCX_QSZM_ZHGZ = "ZHCX_QSZM_ZHGZ";

// 长度太长需要控制展示的字段
var reverseList = ['zl'];
// 高级查询控制参数
var searchData, needsearch;
// 获取字典
var zdList = getMulZdList("fwyt");
var bdclxList = getMulZdList("bdclx");
// 用户IP地址
var ipaddress;

layui.use(['form', 'jquery', 'element', 'table'], function () {
    var $ = layui.jquery, form = layui.form, table = layui.table;

    // 下拉选择添加删除按钮
    renderSelectClose(form);
    form.render();
    // 当前页数据
    var currentPageData = new Array();

    $(function () {
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');

        // 加载Table
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            title: '权属证明查询列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'qlrmc', width: 200, title: '权利人名称', sort: true,},
                {
                    field: 'bdcqzh', width: 280, title: '产权证号', sort: true,
                    templet: function (d) {
                        return showBdcqzh(d);
                    }
                },
                {field: 'zl', minWidth: 300, title: '坐落', sort: true,},
                {
                    field: 'bdcdyh', width: 280, title: '不动产单元号', sort: true,
                    templet: function (d) {
                        return formatBdcdyh(d.bdcdyh);
                    }
                },
                {field: 'slbh', width: 150, title: '受理编号', sort: true},
                {field: 'jzmj', width: 130, title: '建筑面积', sort: false, templet: '#fwmjTpl'},
                {field: 'tdsyqmj', width: 130, title: '土地权利面积', sort: false, templet: '#tdqlmjTpl'},
                {
                    field: 'qszt', width: 110, title: '权属状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatQsztWithCx(d.qszt, d.ajzt);
                    }
                }
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.xmid) {
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }

                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                setHeight();
                reverseTableCell(reverseList);
                showMoreBtn();

                // 控制按钮权限
                setElementAttrByModuleAuthority(moduleCode);
                form.render();
            }
        });

        // 获取用户IP地址
        try {
            ipaddress = $("#ipaddress").val();
            if (isNullOrEmpty(ipaddress)) {
                getUserIP(function (ip) {
                    if (ip != null && ip != undefined) {
                        ipaddress = ip;
                    }
                });
            }
        } catch (err) {
            console.info("获取IP地址出错：" + err.toString());
        }

        // 存储打印配置的sessionKey，当前页的打印类型
        var dylxArr = ["fwqszm", "tdqszm"];
        var sessionKey = "bdcqszmcx";
        setDypzSession(dylxArr, sessionKey);

        // 监听表格操作栏按钮
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'qszm':
                    qszm(checkStatus.data, obj.config.cols[0]);
                    break;
            }
        });

        // 打印权属证明 （土地和房屋合成一个按钮，根据不动产类型调用对应的模板、数据源）
        function qszm(d, cols) {
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要打印的记录！");
                return;
            }

            // 判断是否包含临时状态
            var isLszt = false;
            // 判断选择的不动产类型是否一致
            var isSameBdclx = true, bdclx;
            $.each(checkedData, function (key, value) {
                bdclx = value.bdclx;
                return false;
            });

            $.each(checkedData, function (key, value) {
                if (0 == value.qszt) {
                    isLszt = true;
                }

                if (value.bdclx != bdclx) {
                    isSameBdclx = false;
                }
            });
            if (isLszt == true) {
                warnMsg("该权证为临时状态，无法出具相关证明！");
                return;
            }
            if (isSameBdclx == false) {
                warnMsg("请选择同一种不动产类型记录出具证明！");
                return;
            }

            // 进行规则验证
            checkApplyRegistration(checkedData, ZHCX_QSZM_ZHGZ, 'QSZM').fail(function () {
                return;
            }).done(function () {
                // 格式示例：[{"bdcdyh":"1","qszt":"1","gzlslid":"1"},{"bdcdyh":"1","qszt":"1","gzlslid":"1"}]
                var paramList = new Array();
                $.each(checkedData, function (key, value) {
                    var param = new Object();
                    if (value.qszt != 2) {//非历史状态，一律只查现势的信息
                        param.qszt = 1;
                    } else {
                        param.qszt = value.qszt;
                    }
                    param.bdcdyh = value.bdcdyh;
                    param.gzlslid = value.gzlslid;
                    param.bdcqzh = value.bdcqzh;
                    paramList.push(param);
                });
                // 缓存要打印的权属证明参数信息
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/zhcx/bzb/qszm",
                    type: "POST",
                    data: JSON.stringify(paramList),
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function (data) {
                        if (data && data.redisKey) {
                            // 根据土地还是房屋类型选择对应的打印模板
                            var mburl = bdclx == "1" ? tdQszmDymb : fwQszmDymb;
                            var dylx = bdclx == "1" ? "tdqszm" : "fwqszm";

                            // 执行打印
                            var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/zhcx/bzb/qszm/" + data.redisKey + "/" + bdclx + "/xml";
                            var appName = "realestate-inquiry-ui";

                            printChoice(dylx, appName, dataUrl, mburl, false, sessionKey);

                            //print(mburl, dataUrl, false);

                            // 保存日志
                            savePrintInfo(mburl, dataUrl, {'zmbh': data.cxh, "printType": "权属证明"});
                        } else {
                            failMsg("房产档案打印出错，请重试！");
                        }
                    },
                    error: function () {
                        failMsg("房产档案打印出错，请重试！");
                    }
                });
            });
        }

        // 出证明时候进行相关验证
        // yzbs 为了规则验证时候标识，比如验证信息补录抵押，房产证明不要验证，权属要验证
        function checkApplyRegistration(data, zhbs, yzbs) {
            if (isNullOrEmpty(zhbs)) {
                warnMsg("未配置验证规则，请联系管理员！");
            }

            var deferred = $.Deferred();
            if ($.isEmptyObject(data)) {
                return deferred.resolve();
            }
            var bdcGzYzsjDTOList = new Array();
            $.each(data, function (index, value) {
                // 这里参数 djh 只是为了匹配现有的参数实体属性，本质上是为了起到标识综合查询作用
                bdcGzYzsjDTOList.push({
                    xmid: value.xmid,
                    bdcdyh: value.bdcdyh,
                    djh: yzbs
                });
            });
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhcx/dyzm/gzyz/" + zhbs,
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({
                    bdcGzYzsjDTOList: bdcGzYzsjDTOList
                }),
                success: function (data) {
                    if (data.length == 0) {
                        return deferred.resolve();
                    }
                    var content = new Array();
                    var containsWarning = false;
                    $.each(data, function (i, value) {
                        var xmid = getXmidOfGzyzxx(value);
                        var action = "";
                        if (!isNullOrEmpty(xmid)) {
                            action = "查看";
                        }
                        // 获取规则中是否存在不可忽略的规则，存在则无法进行打印
                        if (isNotBlank(value.yxj) && 3 == value.yxj) {
                            containsWarning = true;
                        }
                        content.push("<img src=\"../../static/lib/bdcui/images/warn.png\" style='width: 16px;' alt=\"\"> ");
                        content.push(value.tsxx);
                        content.push('&nbsp;&nbsp;<a class="viewbdcxm" href="javascrit:;" onclick="openXmxx(\'' + xmid + '\');return false">' + action + '</a>');
                        content.push("<br/>");
                    });
                    if (containsWarning) {
                        layer.open({
                            title: '信息',
                            area: '530px',
                            btn: [],
                            content: content.join("")
                        });
                    } else {
                        layer.confirm(content.join(""), {
                            btn: ["继续打印", "否"],
                            area: '530px'
                        }, function (index, layero) {
                            deferred.resolve();
                            layer.close(index);
                        }, function (index) {
                            deferred.reject();
                            layer.close(index);
                        });
                    }
                },
                error: function (xhr, status, error) {
                    failMsg("系统验证发生异常，请重试或联系管理员！");
                }
            });
            return deferred;
        }

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(pageTable)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;

            $.each(data, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: v.xmid, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: v.xmid, remove: true
                    });
                }
            });
        });

        // 超链接显示不动产权证号
        function showBdcqzh(data) {
            if (!isNullOrEmpty(data.bdcqzh)) {
                return '<a href="javascript:viewBdcZszm(&quot;' + data.gzlslid + '&quot;)" class="layui-table-link">' + data.bdcqzh + '</a>';
            } else {
                return "";
            }
        }

        // 重置清空查询条件
        $('#reset').on('click', function () {
            $("#bdcdyh").val(null);
            $("#ycqzh").val(null);
        });

        // 查询处理逻辑
        function search() {
            // 每次查询清除下导出缓存数据
            layui.sessionData("checkedData", null);

            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            if (0 == count) {
                warnMsg(" 请输入必要查询条件，例如产权人名称");
                return false;
            }

            // 设置默认的字段模糊类型
            var mhlx = {"bdcqzhmhlx": 0, "qlrmcmhlx": 0,};
            form.val('searchform', JSON.parse(JSON.stringify(mhlx)));

            // 获取查询参数
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });
            // 查询IP
            obj.ipaddress = ipaddress;

            addModel();
            // 重新请求
            table.reload("pageTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/zszm"
                , where: obj
                , page: {
                    curr: 1,  //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    // 查询附加信息展示
                    getZhcxFjxx(currentPageData);

                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                    showMoreBtn();

                    // 控制按钮权限
                    setElementAttrByModuleAuthority(moduleCode);
                    form.render();

                    // 单独保存查询数据到数据库供本地综合查询日志页面使用
                    saveQueryLogToDataBase(obj, currentPageData, "QSZMCX");
                }
            });
        }

        // 附加信息缓存JSON
        var fjxxDataJson = {};
        // 查询附加信息展示
        function getZhcxFjxx(pageData) {
            if (pageData && pageData.length > 0) {
                var xmidArray = new Array();
                $.each(pageData, function (index, item) {
                    xmidArray.push(item.xmid);
                });

                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/zszm/fjxx",
                    type: "POST",
                    data: JSON.stringify(xmidArray),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data && data.length > 0) {
                            $.each(data, function (index, item) {
                                var zh = isNullOrEmpty(item.zh) ? "" : item.zh;
                                var fjh = isNullOrEmpty(item.fjh) ? "" : item.fjh;
                                var jzmj = isNullOrEmpty(item.jzmj) ? "" : item.jzmj;
                                var tdsyqmj = isNullOrEmpty(item.tdsyqmj) ? "" : item.tdsyqmj;

                                $("." + item.xmid + "_zh").html('<p class="bdc-table-state-th">' + zh + '</p>');
                                $("." + item.xmid + "_fjh").html('<p class="bdc-table-state-th">' + fjh + '</p>');
                                $("." + item.xmid + "_fwmj").html('<p class="bdc-table-state-th">' + jzmj + '</p>');
                                $("." + item.xmid + "_tdqlmj").html('<p class="bdc-table-state-th">' + tdsyqmj + '</p>');
                                
                                // 将附加信息保存到缓存JSON数据中，为了导出Excel时候获取非当前页数据
                                if(!fjxxDataJson[item.xmid]) {
                                    fjxxDataJson[item.xmid] = item;
                                }
                            });
                        }
                    }
                });
            }
        }

        //监听滚动事件
        var scrollTop = 0, tableTop = 0, tableLeft = 0;
        var $nowBtnMore = '';
        //点击表格中的更多
        $('.bdc-table-box').on('click', '.bdc-more-btn', function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            event.stopPropagation();
            $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
            var $btnMore = $(this).siblings('.bdc-table-btn-more');
            if ($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight) {
                $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
            } else {
                $btnMore.css({top: tableTop - scrollTop - $btnMore.height(), left: tableLeft - 30});
            }
            $('.bdc-table-btn-more').hide();
            $btnMore.show();
        });
        //点击更多内的任一内容，隐藏
        $('.bdc-table-btn-more a').on('click', function () {
            $(this).parent().hide();
        });
        //点击页面任一空白位置，消失
        $('body').on('click', function () {
            $('.bdc-table-btn-more').hide();
            $('.bdc-table-top-more-show').hide();
        });

        showMoreBtn();

        // 显示其他证明下拉内容
        function showMoreBtn() {
            //点击表格上面的更多按钮
            $('.bdc-table-top-more>.layui-btn').on('click', function (event) {
                event.stopPropagation();
                $(this).siblings().show();
            });
            //点击更多内a标签，隐藏
            $('.bdc-table-top-more-show a').on('click', function (event) {
                event.stopPropagation();
                $(this).parent().hide();
            });
        }
    });

    // 证书证明预览
    window.viewBdcZszm = function (gzlslid) {
        if (isNullOrEmpty(gzlslid)) {
            return;
        }
        window.open("/realestate-register-ui/rest/v1.0/bdcZs?processInsId=" + gzlslid + "&zsyl=false&readOnly=true");
    }

    //身份证读卡器设置
    window.onReadIdCard = function (qlrlb) {
        try {
            var cardInfo = readIdCard();
            if (cardInfo.ReadCard()) {
                var name = cardInfo.Name;
                var number = cardInfo.ID;

                if (1 == qlrlb) {
                    $('input[name="qlrmc"]').val(name);
                    $('input[name="qlrzjh"]').val(number);
                } else {
                    $('input[name="ywrmc"]').val(name);
                    $('input[name="ywrzjh"]').val(number);
                }
            } else {
                warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
            }
        } catch (objError) {
            warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
        }
    }

    // 获取当前登录用户
    var userName = "";
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/fczm/userinfo",
        type: "GET",
        dataType: "text",
        success: function (data) {
            userName = data;
        }
    });
});

//查看项目信息
function openXmxx(xmid) {
    $.ajax({
        url: "/realestate-inquiry-ui/history/queryBdcXmDoByXmid",
        type: 'GET',
        async: true,
        contentType: 'application/json;charset=utf-8',
        data: {xmid: xmid},
        success: function (data) {
            if (isNotBlank(data) && isNotBlank(data[0])) {
                window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + data[0].gzlslid);
            } else {
                warnMsg(" 请求失败，未获取到项目信息！");
            }

        },
        error: function (err) {
            removeModal();
            layer.closeAll();
            warnMsg(" 请求失败，请检查数据连接！");
        }
    });
}

// 从规则验证结果获取XMID信息
function getXmidOfGzyzxx(data) {
    var xmid = "";
    if (data && data.xzxx) {
        var xzxx = data.xzxx;
        $.each(xzxx, function (key, value) {
            if (key != "RULERESULT") {
                // 包含XMID、BDCDYH这些信息的验证结果，不同规则验证设置的数据量变量不一样，所以动态获取
                if (value && value[0] && value[0].XMID) {
                    xmid = value[0].XMID;
                }
            }
        })
    }
    return xmid;
}