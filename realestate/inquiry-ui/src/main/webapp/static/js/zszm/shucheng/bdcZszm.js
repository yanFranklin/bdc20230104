/**
 * 合肥市  综合查询页面JS处理（原证书证明查询）
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var dyaCfFr3Url = getIP() + "/realestate-inquiry-ui/static/printModel/bdcDyaCfZm.fr3";
var zfxxFr3Url = getIP() + "/realestate-inquiry-ui/static/printModel/bdcZfxx.fr3";
var zfxxDaUrl = getIP() + "/realestate-inquiry-ui/static/printModel/bdcZfxxDa.fr3";
var dyfyUrl = getIP() + "/realestate-inquiry-ui/static/printModel/bdcdyList.fr3";
var dyawqdUrl = getIP() + "/realestate-inquiry-ui/static/printModel/dyawqd.fr3";
var reverseList = ['zl'];
var searchData, searchFilterData, needsearch, needFilter;
var zdList = getMulZdList("fwyt");
var bdclxList = getMulZdList("bdclx");

// 证明验证对应的组合规则标识
// 土地证明
var ZHCX_TDZM_ZHGZ = "ZHCX_TDZM_ZHGZ";
// 房产证明
var ZHCX_FCZM_ZHGZ = "ZHCX_FCZM_ZHGZ";
// 不动产详情
var ZHCX_BDCXQ_ZHGZ = "ZHCX_BDCXQ_ZHGZ";
// 附表或抵押物清单
var ZHCX_FBDYWQD_ZHGZ = "ZHCX_FBDYWQD_ZHGZ";

// 申请书ID
var sqsid;
// 用户IP地址
var ipaddress;
// 当前登录用户中文名
var useralias;

layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        laydate = layui.laydate,
        formSelects = layui.formSelects;

    // 当前页数据
    var currentPageData = new Array();
    // 是否隐藏查询内容
    var yccx = $.getUrlParam("yccx");

    laydate.render({
        elem: '#szsjq'
        , type: 'datetime'
    });
    laydate.render({
        elem: '#szsjz'
        , type: 'datetime'
    });

    // 下拉选择添加删除按钮
    renderSelectClose(form);

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    // 查询列表“更多”操作是否展示权限配置
    var authority = getElementAuthority(moduleCode);

    $(function () {
        // 页面初始化，默认打开查询申请书
        // openCxsqs();
        // 加载Table
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            title: '证书证明列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xmid', width: 120, title: '项目id', hide: true},
                {field: 'qllx', width: 120, title: '权利类型', hide: true},
                {
                    field: 'bdcqzh', width: 280, title: '不动产权证号（明）', sort: true,
                    templet: function (d) {
                        return showBdcqzh(d);
                    }
                },
                {
                    field: 'bdcdyh', width: 280, title: '不动产单元号', sort: true,
                    templet: function (d) {
                        return formatBdcdyh(d.bdcdyh);
                    }
                },
                {field: 'qlrmc', width: 200, title: '权利人名称', sort: true},
                {field: 'zl', minWidth: 300, title: '坐落', sort: true},
                {field: 'djyy', width: 200, title: '登记原因', sort: true},
                {
                    field: 'ghyt', title: '规划用途', minWidth: 150,
                    templet: function (d) {
                        return zdGhytToMc('fwyt', d.ghyt, zdList);
                    }
                },
                {field: 'qlrzjh', width: 250, title: '权利人证件号', sort: true,},
                {field: 'ywrmc', width: 250, title: '义务人名称', sort: true,},
                {field: 'ywrzjh', width: 250, title: '义务人证件号', sort: true,},
                {field: 'zh', width: 150, title: '幢号', sort: true, templet: '#zhTpl'},
                {field: 'fjh', width: 150, title: '房间号', sort: true, templet: '#fjhTpl'},
                {field: 'jzmj', width: 100, title: '房屋面积', sort: true, templet: '#fwmjTpl'},
                {
                    field: 'djsj', width: 180, title: '登记时间', sort: true,
                    templet: function (d) {
                        return formatDate(d.djsj);
                    }
                },
                {field: 'dh', width: 200, title: '权利人电话', sort: true, templet: '#dhTpl',},
                {field: 'slbh', width: 150, title: '受理编号', sort: true,},
                {field: 'fwbh', width: 250, title: '房屋编号', sort: true,},
                {field: 'zhlsh', width: 150, title: '证号流水号', sort: true,},

                {field: 'fj', width: 250, title: '附记', sort: false},
                {
                    field: 'bdclx', width: 120, title: '不动产类型', hide: true,
                    templet: function (d) {
                        return zdDmToMc('bdclx', d.bdclx, bdclxList);
                    }
                },
                {
                    field: 'qszt', width: 100, title: '权属状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatQsztWithCx(d.qszt, d.ajzt);
                    }
                },
                {
                    field: 'bdcdyZtDTO', width: 150, title: '限制状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatXzztxx(d.bdcdyZtDTO);
                    }
                },
                {title: '查看', fixed: 'right', toolbar: '#barDemo', width: 180}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        // 添加权限数据（和外层这个处理选择行没有关系）
                        v.authority = authority;
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
            }
        });

        // 获取登录用户信息
        getCurrentUserInfo();

        // 获取用户IP地址
        try {
            ipaddress = $("#ipaddress").val();
            if (isNullOrEmpty(ipaddress)) {
                getUserIP(function (ip) {
                    if (ip != null && ip != undefined) {
                        ipaddress = ip;
                        $.cookie("ipaddress", ip);
                    }
                });
            }
        } catch (err) {
            console.info("获取IP地址出错：" + err.toString());
        }

        // 数据二次筛选时候查询
        if (true == yccx || "true" == yccx) {
            // 隐藏指定查询条件
            $(".needhide").hide();
            $(".bdc-percentage-container").css("padding", "61px 10px 10px");

            var data2 = JSON.parse($.cookie('searchFilterData'));
            // 查询IP
            data2["ipaddress"] = $.cookie("ipaddress");

            if (data2) {
                addModel();
                layui.use(['table'], function () {
                    var table = layui.table;
                    table.reload("pageTable", {
                        url: "/realestate-inquiry-ui/rest/v1.0/zszm"
                        , where: data2
                        , page: {
                            curr: 1,  //重新从第 1 页开始
                            limits: [10, 50, 100, 200, 500]
                        }
                        , done: function (res, curr, count) {
                            $("#filterData").hide();
                            $("#exportExcel").removeClass("bdc-table-second-btn");
                            $("#exportExcel").addClass("bdc-major-btn");

                            currentPageData = res.data;
                            reverseTableCell(reverseList);
                            removeModal();
                            setHeight();

                            // 查询附加信息展示
                            getZhcxFjxx(currentPageData);
                        }
                    });
                });

                var qlrmc = "";
                if (!isNullOrEmpty(data2.qlrmc)) {
                    qlrmc = data2.qlrmc;
                    if (!isNullOrEmpty(data2.qlrmc2)) {
                        qlrmc = qlrmc + "," + data2.qlrmc2;
                    }
                }
                if (!isNullOrEmpty(data2.qlrmc2)) {
                    qlrmc = data2.qlrmc2;
                }
                $("#qlrmc").val(qlrmc);

                var qlrzjh = "";
                if (!isNullOrEmpty(data2.qlrzjh)) {
                    qlrzjh = data2.qlrzjh;
                    if (!isNullOrEmpty(data2.qlrzjh2)) {
                        qlrzjh = qlrzjh + "," + data2.qlrzjh2;
                    }
                }
                if (!isNullOrEmpty(data2.qlrzjh2)) {
                    qlrzjh = data2.qlrzjh2;
                }
                $("#qlrzjh").val(qlrzjh);
            }
        }

        //监听滚动事件
        var scrollTop = 0,
            scrollLeft = 0;
        var tableTop = 0, tableLeft = 0;
        var $nowBtnMore = '';
        //点击表格中的更多
        $('.bdc-table-box').on('click', '.bdc-more-btn', function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            event.stopPropagation();
            $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
            var $btnMore = $(this).siblings('.bdc-table-btn-more');
            if ($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight) {
                // $btnMore.css({top: $(this).offset().top + 26 -scrollTop,right: 20});
                $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
            } else {
                // $btnMore.css({top: $(this).offset().top -scrollTop - $btnMore.height(),right: 20});
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
        });

        //监听工具条
        table.on('tool(pageTable)', function (obj) {
            var data = obj.data;
            if (isNullOrEmpty(data.bdcdyh)) {
                warnMsg(" 没有不动产单元信息，无法查看！");
                return;
            }

            if (obj.event === 'djls') {
                window.open("/realestate-inquiry-ui/view/lsgx/lsgx.html?bdcdyh=" + data.bdcdyh);
                saveDetailLog("ZHCXLSGX", "综合查询-查看登记历史", data);
            } else if (obj.event === 'lpb') {
                if (data.bdclx == 2) {
                    // 只有房屋类型允许查看
                    window.open("/building-ui/lpb/view?code=analysis&bdcdyh=" + data.bdcdyh + "&qjgldm=" + data.qjgldm);
                    saveDetailLog("ZHCXLPB", "综合查询-查看楼盘表", data);
                } else {
                    warnMsg("当前不动产单元无楼盘表！");
                }
            } else if (obj.event === 'ompRedirect') {
                window.open('/building-ui/omp/redirect/' + data.bdcdyh + '/' + data.bdclx);
            } else if (obj.event === 'djb') {
                window.open("/realestate-register-ui/view/djbxx/bdcDjb.html?param=" + data.bdcdyh);
                saveDetailLog("ZHCXDJB", "综合查询-查看登记簿", data);
            } else if (obj.event === 'fcda') {
                //modify 2020-10-26 33502 合肥档案增加type参数，1是产权，2是抵押权
                var type = 1;
                if (data.qllx == 95 ||data.qllx == 37) {
                    type = 2;
                }
                var url = "/realestate-register-ui/view/daxx/scan.html?type=" + type + "&slbh=" + data.slbh + "&bdcqzh=";
                if (!isNullOrEmpty(data.bdcqzh)) {
                    var strs = new Array();
                    strs = data.bdcqzh.split(","); //字符分割
                    url += encodeURI(strs[0]);
                }
                window.open(url);
                saveDetailLog("ZHCXDADY", "综合查询-档案调用", data);
            } else if (obj.event === 'djdcb') {
                var bdcqzh = "";
                if (!isNullOrEmpty(data.bdcqzh)) {
                    var str = data.bdcqzh;
                    var strs = new Array();
                    strs = str.split(",");
                    bdcqzh = encodeURI(strs[0]);
                }
                window.open('/building-ui/djdcb/initview?bdcdyh=' + data.bdcdyh + "&qjgldm=" + data.qjgldm + "&type=1&bdcqzh=" + bdcqzh + "&slbh=" + data.slbh);
                saveDetailLog("ZHCXDJDCB", "综合查询-地籍调查表", data);
            } else if (obj.event === 'bdcdjda') {
                openBdcDjDa(data);
                saveDetailLog("ZHCXBDCDJDA", "综合查询-不动产登记档案", data);
            } else if (obj.event === 'xzqlxq') {
                window.open('/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=xzqlcx&param=' + data.bdcdyh);
                saveDetailLog("ZHCXXZQLXQ", "综合查询-限制权利详情", data);
            }
        });

        // 监听表格操作栏按钮
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'filterData':
                    // 数据二次筛选
                    filterData();
                    break;
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0]);
                    break;
                case 'importExcelSearch':
                    importExcelSearch();
                    break;
                case 'ExcelSearchPrint':
                    ExcelSearchPrint();
                    break;
                case 'cdzm':
                    // 土地证明（原查档证明）
                    cdzm(checkStatus.data, obj.config.cols[0]);
                    break;
                case 'fczm':
                    // 房产证明
                    fczm(checkStatus.data, obj.config.cols[0]);
                    break;
                case 'fcda':
                    // 不动产详情（原房产档案）
                    fcda(checkStatus.data, obj.config.cols[0]);
                    break;
                case 'sqs':
                    // 查询申请书（已不用，但功能保留）
                    openCxsqs();
                    break;
                case 'fb':
                    // 附表或抵押物清单
                    fbOrDywqd(checkStatus.data, obj.config.cols[0]);
                    break;
            }
        });

        function importExcelSearch() {
            $("#importExcelFile").val("");
            $("#importExcelFile").click();
        }
        function ExcelSearchPrint() {
            $("#excelSearch").val("");
            $("#excelSearch").click();
        }
        /**
         * 页面加载，默认打开查询申请书
         */
        function openCxsqs() {
            layer.open({
                title: '申请书',
                type: 2,
                area: ['960px', '500px'],
                content: '/realestate-inquiry-ui/view/zszm/bdcCxsqs.html?sqsid=' + sqsid
                , cancel: function () {
                    //右上角关闭回调
                    // if (isNullOrEmpty(sqsid)) {
                    //     warnMsg("请先填写查询申请书！");
                    //     // 开启该代码可禁止点击该按钮关闭
                    //     return false;
                    // }
                    if (!isNullOrEmpty(sqsid)) {
                        // sessionStorage.cxsqsCxnr = cxnrTitle.join(zf_yw_dh);
                        // 获取查询申请书session 的值
                        $("#qlrmc").val(sessionStorage.cxsqsQlrmc);
                        $("#qlrzjh").val(sessionStorage.cxsqsQlrzjh);
                        $('#search').click();
                    }
                }
                , success: function () {

                }
            });
        }

        // 导出Excel
        // 通过隐藏form提交方式，避免ajax方式不支持下载文件
        function exportExcel(d, cols) {
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要导出Excel的数据行！");
                return;
            }

            // 标题
            var showColsTitle = new Array();
            // 列内容
            var showColsField = new Array();
            // 列宽
            var showColsWidth = new Array();
            for (var index in cols) {
                if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
                    showColsTitle.push(cols[index].title);

                    if ('bdcdyZtDTO' == cols[index].field) {
                        showColsField.push('xzzt');
                    } else {
                        showColsField.push(cols[index].field);
                    }

                    if ('zl' == cols[index].field) {
                        showColsWidth.push(60);
                    } else {
                        showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                    }
                }
            }

            // 设置Excel基本信息
            $("#fileName").val('不动产登记综合查询信息结果导出Excel表');
            $("#sheetName").val('统计表');
            $("#cellTitle").val(showColsTitle);
            $("#cellWidth").val(showColsWidth);
            $("#cellKey").val(showColsField);

            var data = new Array();
            $.each(checkedData, function (key, value) {
                data.push(value);
            })
            for (var i = 0; i < data.length; i++) {
                data[i].xh = i + 1;
                data[i].qszt = formatQsztOfData(data[i].qszt);
                data[i].xzzt = formatXzztOfData(data[i].bdcdyZtDTO);
                data[i].ghyt = zdGhytToMc('fwyt', data[i].ghyt, zdList);
            }
            $("#data").val(JSON.stringify(data));
            $("#form").submit();
        }

        // 出具土地证明（原查档证明；后台配置打印内容，传参打印）
        function cdzm(d, cols) {
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要出具证明的数据行！");
                return;
            }

            checkApplyRegistration(checkedData, ZHCX_TDZM_ZHGZ).fail(function () {
                return;
            }).done(function () {
                var data = new Array();
                var bdcqzh = new Array();
                $.each(checkedData, function (key, value) {
                    data.push(value);

                    if (value && !isNullOrEmpty(value.bdcqzh)) {
                        bdcqzh.push(value.bdcqzh);
                    }
                })

                // 获取并保存要打印的不动产单元号
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/print/dyacfzm/bdcdyh",
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "text",
                    success: function (key) {
                        if (key) {
                            var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/dyacfzm/" + key + "/xml";
                            print(dyaCfFr3Url, dataUrl, false);

                            savePrintInfo(dyaCfFr3Url, dataUrl, {'zmbh': bdcqzh.join("、"), "printType": "土地证明"});
                        } else {
                            failMsg("请选中需出具证明记录重试");
                        }
                    },
                    error: function () {
                        failMsg("请选中需出具证明记录重试");
                    }
                });
            });
        }

        // 出具房产有房、无房证明（系统接口查询数据封装XML内容打印）
        function fczm(d, cols) {
            var qlrmc = $("#qlrmc").val();
            var qlrzjh = $("#qlrzjh").val();
            var qlrmcArray = qlrmc.split(",");
            var qlrzjhArray = qlrzjh.split(",");
            if (qlrmcArray.length > 0 && qlrzjhArray.length > 0 && qlrmcArray.length != qlrzjhArray.length) {
                warnMsg(" 已输入权利人名称、证件号数量应一致！");
                return;
            }

            var qlrxxList = new Array();
            if (qlrmcArray.length > 0 && qlrzjhArray.length > 0) {
                for (var i = 0; i < qlrmcArray.length; i++) {
                    if (!isNullOrEmpty(qlrmcArray[i]) && !isNullOrEmpty(qlrzjhArray[i])) {
                        var qlrxx = {"qlrmc": qlrmcArray[i], "zjh": qlrzjhArray[i]};
                        qlrxxList.push(qlrxx);
                    }
                }
            }

            if (!qlrxxList || qlrxxList.length == 0) {
                warnMsg(" 请在查询条件中输入权利人名称、证件号！");
                return;
            }

            // 先获取房产证明对应的不动产单元号等信息进行规则验证
            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhcx/yfwfzm/zfxx",
                type: "POST",
                data: JSON.stringify(qlrxxList),
                contentType: 'application/json',
                dataType: 'json',
                success: function (res) {
                    if (res) {
                        checkApplyRegistration(res, ZHCX_FCZM_ZHGZ).fail(function () {
                            return;
                        }).done(function () {
                            printFczm(qlrxxList);
                        });
                    } else {
                        removeModal();
                        failMsg("获取有房无房信息失败，请重试！");
                    }
                },
                error: function () {
                    removeModal();
                    failMsg("获取有房无房信息失败，请重试！");
                },
                complete: function () {
                    removeModal();
                }
            });
        }

        // 打印房产证明（合肥有房无房证明打印去除支持勾选不动产单元号，直接根据名称、证件号查询）
        function printFczm(qlrxxList) {
            var qjgldm = "341523";
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/print/zfxx/qlrxx",
                type: "POST",
                data: JSON.stringify({"qlrQOList": qlrxxList, "useralias": useralias, "qjgldm":qjgldm}),
                contentType: 'application/json',
                dataType: "text",
                success: function (key) {
                    if (key) {
                        var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/zfxx/" + key + "/xml";
                        print(zfxxFr3Url, dataUrl, false);

                        savePrintInfo(zfxxFr3Url, dataUrl, {'zmbh': key, "printType": "房产证明"});
                    } else {
                        failMsg("打印失败，请重试！");
                    }
                },
                error: function () {
                    failMsg("打印失败，请重试！");
                }
            });
        }

        // 打印不动产详情（原房产档案；系统后台配置打印数据源）
        function fcda(d, cols) {
            // 获取分页勾选的所有数据
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要打印的记录！");
                return;
            }

            checkApplyRegistration(checkedData, ZHCX_BDCXQ_ZHGZ).fail(function () {
                return;
            }).done(function () {
                var qszmDyParamList = new Array();
                var bdcqzh = new Array();
                $.each(checkedData, function (key, value) {
                    var singleParam = new Object();
                    if (value.qszt != 2) {//非历史状态，一律只查现势的信息
                        singleParam.qszt = 1;
                    } else {
                        singleParam.qszt = value.qszt;
                    }
                    singleParam.bdcdyh = value.bdcdyh;
                    singleParam.gzlslid = value.gzlslid;
                    singleParam.bdcqzh = value.bdcqzh;
                    singleParam.useralias = useralias;
                    qszmDyParamList.push(singleParam);

                    if (value && !isNullOrEmpty(value.bdcqzh)) {
                        bdcqzh.push(value.bdcqzh);
                    }
                });

                // 缓存要打印的房产档案不动产单元号
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/zfxx/fcda/bdcdyh",
                    type: "POST",
                    data: JSON.stringify(qszmDyParamList),
                    contentType: 'application/json',
                    dataType: 'text',
                    success: function (key) {
                        if (!isNullOrEmpty(key)) {
                            //在建工程抵押也要出房产证明，但是无房产数据，限制放开，现场会修改相关打印数据源
                            var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/zfxx/fcda/" + key + "/xml";
                            print(zfxxDaUrl, dataUrl, false);

                            savePrintInfo(zfxxDaUrl, dataUrl, {'zmbh': bdcqzh.join("、"), "printType": "不动产详情"});
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

        //打印附表或抵押物清单
        function fbOrDywqd() {
            // 获取分页勾选的所有数据
            var checkedData = layui.sessionData('checkedData');
            var zsid = "";
            var gzlslids = new Array();
            var bdcqzh = new Array();

            $.each(checkedData, function (key, value) {
                gzlslids.push(value.gzlslid);
                zsid = value.zsid;

                if (value && !isNullOrEmpty(value.bdcqzh)) {
                    bdcqzh.push(value.bdcqzh);
                }
            });
            if (gzlslids.length != 1) {
                warnMsg(" 请选择其中一条需要打印的记录即可！");
                return;
            }
            checkApplyRegistration(checkedData, ZHCX_FBDYWQD_ZHGZ).fail(function () {
                return;
            }).done(function () {
                var gzlslid = gzlslids[0];
                $.ajax({
                    url: '/realestate-inquiry-ui/rest/v1.0/zfxx/getFdcqOrDyaqCount?gzlslid=' + gzlslid,
                    // data: gzlslid,
                    // contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data && data == 1) {
                            //打印不动产附页数据源
                            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/bdcdy/" + gzlslid + "/bdcdyList_zs/xml?zsid=" + zsid;
                            print(dyfyUrl, dataUrl, false);

                            savePrintInfo(dyfyUrl, dataUrl, {'zmbh': bdcqzh.join("、"), "printType": "不动产附表"});
                        } else if (data && data == 2) {
                            //打印抵押清单数据源
                            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/bdcdy/" + gzlslid + "/dyawqd_zs/xml?zsid=" + zsid;
                            print(dyawqdUrl, dataUrl, false);

                            savePrintInfo(dyawqdUrl, dataUrl, {'zmbh': bdcqzh.join("、"), "printType": "抵押物清单"});
                        } else {
                            failMsg("该功能只支持一证多房数据打印，当前不是一证多房无法进行打印，请检查！");
                        }
                    },
                    error: function () {
                        failMsg("打印出错，请重试！");
                    }
                });
            });
        }

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        // 回车查询
        $('.search').on('keydown', function (event) {
            if (event.keyCode == 13 && !(true == yccx || "true" == yccx)) {
                // 回车且不是二次过滤查询页面
                search();
            }
        });

        // （外面的简单查询）查询处理逻辑
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
                warnMsg(" 请输入必要查询条件，例如权利人名称");
                return false;
            }

            // 设置默认的字段模糊类型
            var mhlx = {
                "bdcdyhmhlx": 0, "bdcqzhmhlx": 0, "qlrmcmhlx": 0, "qlrzjhmhlx": 0, "ywrmcmhlx": 0,
                "ywrzjhmhlx": 0, "zlmhlx": 3, "ycqzhmhlx": 0, "zhmhlx": 0, "fjhmhlx": 0, "slbhmhlx": 0,
                "fwbhmhlx": 0, "zslshmhlx": 0, "ybdcdyhmhlx": 0, "yxtcqzhmhlx": 0
            };
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
            //行政区划
            obj["qjgldm"] = "341523";
            // 查询IP
            obj["ipaddress"] = ipaddress;

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
                    changeCheckboxBackground([{'name': 'bdc_change_red', 'color': 'red'}])
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();

                    // 查询附加信息展示
                    getZhcxFjxx(currentPageData);

                    // 缓存登记原因
                    setDjyy(currentPageData);

                    // 单独保存查询数据到数据库供本地综合查询日志页面使用
                    saveQueryLogToDataBase(obj, currentPageData, "ZHCX");
                }
            });
        }

        // 高级查询
        $('#moreSearch').on('click', function () {
            // 每次查询清除下导出缓存数据
            layui.sessionData("checkedData", null);
            needsearch = false;

            layer.open({
                title: '高级查询',
                type: 2,
                area: ['960px', '520px'],
                content: 'search.html',
                success: function (layero, index) {
                    var obj = {};
                    $(".search").each(function (i) {
                        var name = $(this).attr('name');
                        var value = $(this).val();
                        if (value) {
                            value = value.replace(/\s*/g, "");
                        }
                        obj[name] = value;
                    });
                    obj['qjgldm'] = "341523";
                    var iframe = window['layui-layer-iframe' + index];
                    iframe.setData1(obj);
                },
                end: function () {
                    if (needsearch && searchData) {
                        // 查询IP
                        searchData.ipaddress = ipaddress;

                        addModel();
                        table.reload("pageTable", {
                            url: "/realestate-inquiry-ui/rest/v1.0/zszm"
                            , where: searchData
                            , page: {
                                curr: 1,  //重新从第 1 页开始
                                limits: [10, 50, 100, 200, 500]
                            }
                            , done: function (res, curr, count) {
                                currentPageData = res.data;
                                reverseTableCell(reverseList);
                                removeModal();
                                setHeight();

                                // 查询附加信息展示
                                getZhcxFjxx(currentPageData);

                                // 缓存登记原因
                                setDjyy(currentPageData);

                                // 单独保存查询数据到数据库供本地综合查询日志页面使用
                                saveQueryLogToDataBase(searchData, currentPageData, "ZHCX");
                            }
                        });
                    }
                }
            });
        });

        // 每页数据加载的时候，获取登记原因，缓存到cookie中，留二次数据筛选时候登记原因下拉
        function setDjyy(pageData) {
            if (pageData && pageData.length > 0) {
                var djyyArray = new Array();
                $.each(pageData, function (index, item) {
                    if (-1 == $.inArray(item.djyy, djyyArray) && !isNullOrEmpty(item.djyy)) {
                        var djyy = item.djyy;
                        if (djyy.length > 20) {
                            // 避免下拉长度过长显示问题
                            djyy = djyy.substring(0, 20) + "...";
                        }
                        djyyArray.push(djyy);
                        $.cookie("DJYY", JSON.stringify(djyyArray));
                    }
                });
            }
        }

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
                                var dh = isNullOrEmpty(item.dh) ? "" : toEncryptValue(item.dh);
                                ;

                                $("." + item.xmid + "_zh").html('<p class="bdc-table-state-th">' + zh + '</p>');
                                $("." + item.xmid + "_fjh").html('<p class="bdc-table-state-th">' + fjh + '</p>');
                                $("." + item.xmid + "_fwmj").html('<p class="bdc-table-state-th">' + jzmj + '</p>');
                                $("." + item.xmid + "_dh").html('<p class="bdc-table-state-th">' + dh + '</p>');
                            });
                        }
                    }
                });
            }
        }

        /**
         * 查询二次过滤
         */
        function filterData() {
            needFilter = false;
            // 清空之前查询条件缓存
            $.cookie('searchFilterData', null);
            // 缓存下勾选的数据，避免二次过滤的数据与当前页面冲突，等二次过滤页面关闭时候再还原
            var firstPageCheckData = layui.sessionData('checkedData');

            layer.open({
                title: '查询筛选',
                type: 2,
                area: ['960px', '450px'],
                content: 'search-filter.html',
                success: function (layero, index) {
                },
                end: function () {
                    if (needFilter && searchFilterData) {
                        // 之前的查询条件加上二次过滤的条件作为最终的查询条件
                        $(".search").each(function (i) {
                            var name = $(this).attr('name');
                            var value = $(this).val();
                            if (value) {
                                value = value.replace(/\s*/g, "");
                            }
                            searchFilterData[name] = value;
                        });
                        $.cookie('searchFilterData', JSON.stringify(searchFilterData));

                        layer.open({
                            title: '二次查询结果',
                            type: 2,
                            area: ['100%', '100%'],
                            content: 'bdcZszm.html?yccx=true&moduleCode=' + moduleCode,
                            success: function (layero, index) {
                                // 清除下勾选缓存数据
                                layui.sessionData("checkedData", null);
                            },
                            end: function () {
                                setCheckedData(firstPageCheckData);
                            }
                        });
                    }
                }
            });
        }

        // 还原之前页面勾选的数据
        function setCheckedData(data) {
            layui.sessionData("checkedData", null);
            if (!$.isEmptyObject(data)) {
                $.each(data, function (key, value) {
                    layui.sessionData('checkedData', {
                        key: key, value: value
                    });
                })
            }
        }

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
            $("#ycqzh").val(null);
            $("#zh").val(null);
            $("#fjh").val(null);
            $("#slbh").val(null);
            $("#fwbh").val(null);
            $("#zhlsh").val(null);
            $("#ghyt").val(null);
            $("#ybdcdyh").val(null);
        });

    });

    // 证书证明预览
    window.viewBdcZszm = function (gzlslid) {
        if (isNullOrEmpty(gzlslid)) {
            return;
        }
        window.open("/realestate-register-ui/rest/v1.0/bdcZs?processInsId=" + gzlslid + "&zsyl=false&readOnly=true");
    }

    // 高级查询查询条件字段值传递到查询页面缓存
    window.setSearchData = function (data, flag) {
        searchData = data;
        needsearch = flag;

        if (searchData) {
            form.val('searchform', JSON.parse(JSON.stringify(searchData)));
        }
    }

    // 查询结果二次过滤查询条件缓存
    window.setSearchFilterData = function (data, flag) {
        searchFilterData = data;
        needFilter = flag;
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

    /**
     * 前台的字典转换，代码转换为名称
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zdname 字典名 bdclx
     * @param zdDm 字典代码 1
     * @param zdListName JS中保存字典数据的变量名 默认为zdList*/
    function zdDmToMc(zdname, zdDm, zdListName) {
        try {
            if (zdDm) {
                /* if (!zdListName) {
                     zdListName = "zdList"
                 }*/
                var zdVals;
                if ("fwyt" == zdname) {
                    zdVals = eval(zdListName.fwyt);
                }
                if ("bdclx" == zdname) {
                    zdVals = eval(zdListName.bdclx);
                }
                if (zdVals) {
                    for (var i = 0; i < zdVals.length; i++) {
                        var zdVal = zdVals[i];
                        if (zdDm == zdVal.DM) {
                            return zdVal.MC;
                        }
                    }
                }
                return zdDm;
            }
            return '';
        } catch (e) {
            return "";
        }
    }
    /**
     *前台的字典转换，代码转换为名称 （项目内多幢拼接的规划用途转换）
     *  @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @param zdname 字典名
     * @param zdDm 字典代码
     * @param zdListName JS中保存字典数据的变量名 默认为zdList
     * @returns {string|*}
     */
    function zdGhytToMc(zdname, zdDm, zdListName) {
        try {
            if (zdDm) {
                var zdVals = eval(zdListName.fwyt);
                if (!zdVals) {
                    return zdDm;
                }
                var zdDmArr = zdDm.split(',');
                var dmMC = "";
                for (var j = 0; j < zdDmArr.length; j++) {
                    for (var i = 0; i < zdVals.length; i++) {
                        var zdVal = zdVals[i];
                        if (zdDmArr[j] == zdVal.DM) {
                            if (isNullOrEmpty(dmMC)) {
                                dmMC = zdVal.MC;
                            } else {
                                //以,拼接字符串
                                dmMC += "," + zdVal.MC;
                            }
                        }
                    }
                }
                if (isNullOrEmpty(dmMC)) {
                    return zdDm;
                } else {
                    return dmMC;
                }
            }
            return "";
        } catch (e) {
            return "";
        }
    }

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

// 获取当前登录用户信息
function getCurrentUserInfo() {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zszm/userinfo",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if (data && !isNullOrEmpty(data.alias)) {
                useralias = data.alias;
            }
        }
    });
}

/**
 * 动态查询文件导入
 */
function importFile(fileObj) {
    addModel();
    var cxtjCompare = [];
    $("#uploadFile").ajaxSubmit({
        type: 'post',
        url: '/realestate-inquiry-ui/rest/v1.0/zszm/importExcelSearch',
        success: function (data) {
            console.log(data)
            reloadTable('pageTable', {data: JSON.stringify(data)}, '/realestate-inquiry-ui/rest/v1.0/zszm/excelSearchResult');
            removeModal();
            layer.msg("导入成功！");
        },
        error: function (XmlHttpRequest, textStatus, errorThrown) {
            removeModal();
            layer.msg("导入失败！");
        }
    });
    return false;
}

function reloadTable(table_id,postData,url){
    addModel();
    layui.use(['table'],function () {
        var table = layui.table;
        var options = {
          where: postData,
          page: {
              curr: 1
          },
          data: undefined,
          method: 'GET',
          url :url,
          done: function (res) {
              console.log(res)
              if (res){
                  removeModal();
              }
          }
        };
        table.reload(table_id,options);
    })
}

function excelSearchPrint(file) {
    addModel();
    $("#uploadExcel").ajaxSubmit({
        type: 'post',
        url: '/realestate-inquiry-ui/rest/v1.0/zszm/importExcelSearch',
        success: function (data) {
            excelPrint(data);
            removeModal();
        },
        error: function (XmlHttpRequest, textStatus, errorThrown) {
            removeModal();
            layer.msg("导入失败！");
        }
    });
}
function excelPrint(data) {
    var qlrmc = new Array();
    var zjh = new Array();
    for (let i=0;i<data.length;i++){
        if (!isNullOrEmpty(data[i].qlrmc) && !isNullOrEmpty(data[i].zjh)){
            qlrmc.push(data[i].qlrmc);
            zjh.push(data[i].zjh);
        }
    }
    if (qlrmc.length != zjh.length){
        warnMsg("权利人名称与证件号数量不一致！");
        return;
    }
    if (qlrmc.length == 0 || zjh.length == 0 ){
        warnMsg("权利人名称、证件号不可为空！");
        return;
    }
    addModel();
    var qlrxxList = new Array();
    if (qlrmc.length>0 && zjh.length>0){
        for (var i=0;i<qlrmc.length;i++){
            if (!isNullOrEmpty(qlrmc[i]) && !isNullOrEmpty(zjh[i])){
                var qlrxx = {"qlrmc":qlrmc[i],"zjh":zjh[i]};
                qlrxxList.push(qlrxx);
            }
        }
    }
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zhcx/yfwfzm/excelSearchPrint",
        type: "POST",
        data: JSON.stringify(qlrxxList),
        contentType: 'application/json',
        dataType: 'json',
        success: function (res) {
            if (res){
                checkApplyRegistration(res,ZHCX_FCZM_ZHGZ).fail(function () {
                    return;
                }).done(function () {
                    printSearchResult(qlrxxList);
                })
            }else {
                removeModal();
                failMsg("获取信息失败,请重试！")
            }
        },
        error: function () {
            removeModal();
            failMsg("获取信息失败,请重试！")
        },
        complete: function () {
            removeModal();
        }
    })
}
function printSearchResult(qlrxx) {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/print/zfxx/qlrxx",
        type: "POST",
        data: JSON.stringify({"qlrQOList": qlrxx, "useralias": useralias, "qjgldm":"341523","sfghyt":"N"}),
        contentType: 'application/json',
        dataType: "text",
        success: function (key) {
            if (key) {
                var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/zfxx/" + key + "/xml";
                print(zfxxFr3Url, dataUrl, false);

                savePrintInfo(zfxxFr3Url, dataUrl, {'zmbh': key, "printType": "excel查询证明"});
            } else {
                failMsg("打印失败，请重试！");
            }
        },
        error: function () {
            failMsg("打印失败，请重试！");
        }
    });
}

function checkApplyRegistration(data, zhbs) {
    if (isNullOrEmpty(zhbs)) {
        warnMsg("未配置验证规则，请联系管理员！");
    }

    var deferred = $.Deferred();
    if ($.isEmptyObject(data)) {
        return deferred.resolve();
    }
    var bdcGzYzsjDTOList = new Array();
    $.each(data, function (index, value) {
        bdcGzYzsjDTOList.push({
            xmid: value.xmid,
            bdcdyh: value.bdcdyh
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
            $.each(data, function (i, value) {
                var xmid = getXmidOfGzyzxx(value);
                var action = "";
                if (!isNullOrEmpty(xmid)) {
                    action = "查看";
                }

                content.push("<img src=\"../../static/lib/bdcui/images/warn.png\" style='width: 16px;' alt=\"\">");
                content.push(value.tsxx);
                content.push('&nbsp;&nbsp;<a class="viewbdcxm" href="javascrit:;" onclick="openXmxx(\'' + xmid + '\');return false">' + action + '</a>');
                content.push("<br/>");
            });
            layer.confirm(content.join(""), {
                btn: ["继续打印", "否"],
                area: '530px'
            }, function (index, layero) {
                deferred.resolve();
                layer.close(index);
            }, function (index) {
                deferred.reject();
                layer.close(index);
            })
        },
        error: function (xhr, status, error) {
            failMsg("系统验证发生异常，请重试或联系管理员！");
        }
    });
    return deferred;
}

