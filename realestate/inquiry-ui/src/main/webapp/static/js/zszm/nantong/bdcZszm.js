/**
 * 南通综合查询页面JS处理（原证书证明查询）
 */
// 打印的模板现场维护，此路径只是名义上的路径，具体打印时候会更改为读取配置的打印模板放置路径，但是模板名称要一致
// 有房无房证明打印前缀
var yfwfzmmb = getIP() + "/realestate-inquiry-ui/static/printModel/";
// 权属证明模板
var zfxxDaUrl = getIP() + "/realestate-inquiry-ui/static/printModel/bdcZfxxDa.fr3";
// 抵押证明模板
var dyzmFr3Url = getIP() + "/realestate-inquiry-ui/static/printModel/bdcDyzm.fr3";
// 利害关系人查询证明模板
var lhgxrcxFr3Url = getIP() + "/realestate-inquiry-ui/static/printModel/lhgxrcx.fr3";
// 律师查询证明模板
var lscsFr3Url = getIP() + "/realestate-inquiry-ui/static/printModel/lscx.fr3";
var reverseList = ['zl'];
var searchData, needsearch;
var zdList = getMulZdList("fwyt");
// 申请书ID
var sqsid;
// 用户IP地址
var ipaddress;
// 当前用户名称
var useralias;
//获取页面版本
var version = getQueryString("version");
// 有房无房证明
var T_YFWFZM = "yfwfzm";
// 迁移户口证明
var T_QHKZM = "qhkzm";
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate;

    laydate.render({
        elem: '#szsjq'
        , type: 'datetime'
    });
    laydate.render({
        elem: '#szsjz'
        , type: 'datetime'
    });

    // 根据配置加载权利类型
    getQllxSxpz();
    // 下拉选择添加删除按钮
    renderSelectClose(form);
    // 当前页数据
    var currentPageData = new Array();

    $(function () {
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');

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
                {field: 'ytdzh', width: 200, title: '原土地证号', hide: true},
                {field: 'ybdcdyh', width: 200, title: '原不动产单元号', hide: true},
                {
                    field: 'bdcqzh', width: 280, title: '不动产权证号（明）', sort: true,
                    templet: function (d) {
                        return showBdcqzh(d);
                    }
                },
                {field: 'zl', minWidth: 300, title: '坐落', sort: true,},
                {field: 'jyjg', minWidth: 150, title: '房屋价值'},
                {field: 'qlrmc', width: 250, title: '权利人名称', sort: true,},
                {field: 'qlrzjh', width: 250, title: '权利人证件号', sort: true,},
                {
                    field: 'bdcdyh', width: 280, title: '不动产单元号', sort: true,
                    templet: function (d) {
                        return formatBdcdyh(d.bdcdyh);
                    }
                },
                {
                    field: 'djsj', width: 180, title: '登簿时间', hide: true, sort: true,
                    templet: function (d) {
                        return formatDate(d.djsj);
                    }
                },
                {field: 'djyy', width: 200, title: '登记原因', sort: true,},
                {
                    field: 'ghyt', title: '规划用途', minWidth: 150,
                    templet: function (d) {
                        return zdDmToMc('fwyt', d.ghyt, zdList);
                    }
                },
                {field: 'ywrmc', width: 250, title: '义务人名称', sort: true,},
                {field: 'ywrzjh', width: 250, title: '义务人证件号', sort: true,},
                {field: 'slbh', width: 150, title: '受理编号', sort: true},
                {field: 'zh', width: 150, title: '幢号', sort: false, templet: '#zhTpl'},
                {field: 'fjh', width: 150, title: '房间号', sort: false, templet: '#fjhTpl'},
                {field: 'fwbh', width: 130, title: '房屋编号', sort: true},
                {field: 'jzmj', width: 130, title: '建筑面积', sort: false, templet: '#fwmjTpl'},
                {field: 'tdsyqmj', width: 130, title: '土地权利面积', sort: false, templet: '#tdqlmjTpl'},
                {field: 'zhlsh', width: 150, title: '证号流水号', sort: true},
                {
                    field: 'qszt', width: 100, title: '权属状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatQszt(d.qszt);
                    }
                },
                {
                    field: 'bdcdyZtDTO', width: 100, title: '限制状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatXzzt(d.bdcdyZtDTO);
                    }
                },
                {
                    field: 'ajzt', width: 100, title: '办理状态', fixed: 'right', sort: true, hide: true,
                    templet: function (d) {
                        return formatAjzt(d.ajzt, d.bdcdyh, d.xmid);
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

                // 默认按钮隐藏
                setBtnHidden();
                // 控制按钮权限
                setElementAttrByModuleAuthority(moduleCode);
                // 根据版本显示按钮
                setBtnQhkzm();
                getJyjg();
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

        // 获取登录用户信息
        getCurrentUserInfo();

        // 获取配置项
        var zhcxpz;
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zhcx/pz",
            type: "GET",
            dataType: 'json',
            async: false,
            success: function (res) {
                if (res) {
                    zhcxpz = res;
                } else {
                    failMsg("发现页面未配置正确，请联系管理员解决！");
                }
            },
            error: function () {
                failMsg("发现页面未配置正确，请联系管理员解决！");
            }
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
                    window.open("/building-ui/lpb/view?code=analysis&bdcdyh=" + data.bdcdyh);
                    saveDetailLog("ZHCXLPB", "综合查询-查看楼盘表", data);
                } else {
                    warnMsg("当前不动产单元无楼盘表！");
                }
            } else if (obj.event === 'djb') {
                window.open("/realestate-register-ui/view/djbxx/bdcDjb.html?param=" + data.bdcdyh);
                saveDetailLog("ZHCXDJB", "综合查询-查看登记簿", data);
            } else if (obj.event === 'dady') {
                dady(data);
            } else if (obj.event === 'djdcb') {
                window.open('/building-ui/djdcb/initview?bdcdyh=' + data.bdcdyh);
                saveDetailLog("ZHCXDJDCB", "综合查询-地籍调查表", data);
            }
        });

        // 监听表格操作栏按钮
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0]);
                    break;
                case 'yfwfzm':
                    yfwfzm(checkStatus.data, obj.config.cols[0], T_YFWFZM);
                    break;
                case 'qszm':
                    qszm(checkStatus.data, obj.config.cols[0]);
                    break;
                case 'dyzm':
                    dyzm(checkStatus.data, obj.config.cols[0]);
                    break;
                case 'yfwfzmqy':
                    yfwfzmqy(checkStatus.data, obj.config.cols[0]);
                    break;
                case 'qhkzm':
                    // 海门迁户口证明（因为逻辑和有房无房证明类似，因此复用有房无房逻辑，只是需要做场景判断）
                    yfwfzm(checkStatus.data, obj.config.cols[0], T_QHKZM);
                    break;
                case 'lhgxrcx':
                    lhgxrlscx(checkStatus.data, obj.config.cols[0], 'lhgxrcx');
                    break;
                case 'lscx':
                    lhgxrlscx(checkStatus.data, obj.config.cols[0], 'lscx');
                    break;
            }
        });

        //监听排序事件
        table.on('sort(pageTable)', function (obj) {
            table.reload('pageTable', {
                initSort: obj
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                    showMoreBtn();

                    // 查询附加信息展示
                    getZhcxFjxx(currentPageData);

                    //根据权利状态、办理状态整行显色
                    var colorList = [];
                    $.ajax({
                        url: '/realestate-inquiry-ui/rest/v1.0/status/color',
                        type: "GET",
                        success: function (data) {
                            for (var i in data) {
                                colorList.push({name: i, color: '#333', background: data[i]});
                            }
                            changeTrBackgroundExceptRight(colorList);
                        }
                    });
                    showMoreBtn();

                    // 默认按钮隐藏
                    setBtnHidden();
                    // 控制按钮权限
                    setElementAttrByModuleAuthority(moduleCode);
                    // 根据版本显示按钮
                    setBtnQhkzm();
                    getJyjg();
                }
            });
        });
        // 导出Excel
        // 通过隐藏form提交方式，避免ajax方式不支持下载文件
        function exportExcel(d, cols) {
            if (!isNullOrEmpty(d)) {
                addModel();

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
                            var width = parseInt(cols[index].width / 100 * 15);
                            if (width <= 20) {
                                width = 20;
                            }
                            showColsWidth.push(width);
                        }
                    }
                }

                // 设置Excel基本信息
                $("#fileName").val('不动产登记综合查询信息结果导出Excel表');
                $("#sheetName").val('不动产登记综合查询信息结果导出Excel表');
                $("#cellTitle").val(showColsTitle);
                $("#cellWidth").val(showColsWidth);
                $("#cellKey").val(showColsField);

                var data = new Array();
                $.each(checkedData, function (key, value) {
                    data.push(value);
                })
                for (var i = 0; i < data.length; i++) {
                    var xmid = data[i].xmid;
                    data[i].jzmj = getFjxxVal(xmid, "jzmj");
                    data[i].tdsyqmj = getFjxxVal(xmid, "tdsyqmj");
                    data[i].zh = getFjxxVal(xmid, "zh");
                    data[i].fjh = getFjxxVal(xmid, "fjh");
                    data[i].xh = i + 1;
                    data[i].szsj = formatDate(data[i].szsj);
                    data[i].djsj = formatDate(data[i].djsj);
                    data[i].qszt = formatQsztOfData(data[i].qszt);
                    data[i].ghyt = zdDmToMc('fwyt', data[i].ghyt, zdList);
                    data[i].xzzt = formatXzztOfData(data[i].bdcdyZtDTO);
                    data[i].ajzt = formatAjzt(data[i].ajzt);
                }
                $("#data").val(JSON.stringify(data));
                $("#form").submit();

                setTimeout(function () {
                    removeModal();
                }, 2000);
            } else {
                warnMsg("请先勾选数据");
            }

        }

        /**
         * 从缓存的附加信息中获取对应属性值，不能直接通过 $("id").text() 形式获取，因为导出Excel跨页导出时候这样无法获取到非当前可视页数据
         * @param xmid 所在记录项目ID
         * @param name 属性字段名称
         */
        function getFjxxVal(xmid, name) {
            if (fjxxDataJson && fjxxDataJson[xmid] && !isNullOrEmpty(fjxxDataJson[xmid][name])) {
                return fjxxDataJson[xmid][name];
            }
            return "";
        }

        // 打印有房无房证明
        function yfwfzm(d, cols, zmtype) {
            var qlrmc = $("#qlrmc").val();
            var qlrzjh = $("#qlrzjh").val();
            var qlrmcArray = qlrmc.split(",");
            var qlrzjhArray = qlrzjh.split(",");
            var zl;
            if (d.length == 0) {
                zl = $("#zl").val();
            }else{
                zl = d[0].zl;
            }
            if (qlrmcArray.length > 0 && qlrzjhArray.length > 0 && qlrmcArray.length != qlrzjhArray.length) {
                warnMsg(" 已输入权利人名称、证件号数量应一致！");
                return;
            }

            if ((isNullOrEmpty(qlrmc) || isNullOrEmpty(qlrzjh)) && $.trim(qlrmc).length <= 5) {
                warnMsg(" 请输入权利人名称、证件号！");
                return;
            }
            // 查询有房无房
            var qlrxxList = new Array();
            var zjhxxList = new Array();
            if (qlrmcArray.length > 0) {
                for (var i = 0; i < qlrmcArray.length; i++) {
                    if (!isNullOrEmpty(qlrmcArray[i])) {
                        var qlrzjhTemp = "";
                        // 这里如果大于0，则说明两者的长度相等，因为前面已经做过长度的
                        // 的判断，所以不用担心数组下标越界，如果=0，则说明是qlrmc长度>5
                        // 的情况，则直接把qlrzjh都置空
                        if (qlrzjhArray.length > 0) {
                            qlrzjhTemp = qlrzjhArray[i];
                        }
                        var qlrxx = {"qlrmc": qlrmcArray[i], "zjh": qlrzjhTemp};
                        qlrxxList.push(qlrxx);

                        if (!isNullOrEmpty(qlrzjhTemp)) {
                            zjhxxList.push({"zjh": qlrzjhTemp});
                        }
                    }
                }
            }

            // 判断权利人是否多个拼接的
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/yfwfzm/zfxx/qlrxx",
                type: "POST",
                data: JSON.stringify(zjhxxList),
                contentType: 'application/json',
                dataType: 'text',
                success: function (res) {
                    if (res && "Y" == res) {
                        warnMsg("请拆分权利人并完善证件号后再试！");
                    } else {
                        getYfwfxx(qlrxxList, qlrmc, qlrzjh, zmtype, zl);
                    }
                },
                error: function () {
                    failMsg("打印发生错误，请重试！");
                }
            });
        }

        /**
         * 利害关系人、律师查询打印
         * @param dylx 打印类型
         */
        function lhgxrlscx(d, cols, dylx) {
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要打印的数据行！");
                return;
            }
            var zl;
            var qlrmc;
            if (d.length == 0) {
                zl = $("#zl").val();
                qlrmc = $("#qlrmc").val();
            }else{
                zl = d[0].zl;
                qlrmc = d[0].qlrmc
            }
            addModel();
            var data = new Array();
            $.each(checkedData, function (key, value) {
                data.push(value.xmid);
            });

            var param = {};
            param.lssws = $("#lssws").val();
            param.lsmc = $("#lsmc").val();
            param.lhgxr = $("#lhgxr").val();
            param.lhgxrzjh = $("#lhgxrzh").val();
            param.xmids = data;
            param.dylx = dylx;
            param.jbr = useralias;

            // 缓存打印参数数据
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/print/nantong/lhgxrlscx/param",
                type: "POST",
                data: JSON.stringify(param),
                contentType: 'application/json',
                dataType: 'json',
                success: function (data) {
                    removeModal();
                    if (data && data.redisKey) {
                        var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/nantong/lhgxrlscx/" + data.redisKey + "/xml";
                        var dymb = dylx == "lhgxrcx" ? lhgxrcxFr3Url : lscsFr3Url;
                        print(dymb, dataUrl, false);

                        savePrintInfo(dymb, dataUrl, {
                            'zmbh': data.cxbh,
                            "printType": dylx == "lhgxrcx" ? "利害关系人查询证明" : "律师查询证明", "zl": zl,
                            "qlr": qlrmc
                        });
                    } else {
                        failMsg("利害关系人、律师查询打印出错，请重试！");
                    }
                },
                error: function () {
                    removeModal();
                    failMsg("利害关系人、律师查询打印出错，请重试！");
                }
            });
        }

        // 判断是否有房无房
        function getYfwfxx(qlrxxList, qlrmc, qlrzjh, zmtype, saveZl) {
            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/yfwfzm/zfxx",
                type: "POST",
                data: JSON.stringify(qlrxxList),
                contentType: 'application/json',
                dataType: 'text',
                success: function (res) {
                    if (res) {
                        // 根据有房无房调用不同的打印模板
                        var fr3;

                        if (T_QHKZM == zmtype) {
                            // （海门）迁户口证明
                            if ("YF" == res) {
                                fr3 = yfwfzmmb + zhcxpz.zhcxzmmbQhkzmYfzm;
                            } else {
                                fr3 = yfwfzmmb + zhcxpz.zhcxzmmbQhkzmWfzm;
                            }
                        } else {
                            // （南通、海门）有房无房证明
                            if ("YF" == res) {
                                fr3 = yfwfzmmb + zhcxpz.zhcxzmmbYfzm;
                            } else {
                                fr3 = yfwfzmmb + zhcxpz.zhcxzmmbWfzm;
                            }
                        }

                        var printType = T_QHKZM == zmtype ? "迁户口证明" : "有房无房证明";
                        printYfwfzm(qlrmc, qlrzjh, fr3, printType, zmtype, saveZl);
                    } else {
                        removeModal();
                        failMsg("打印发生错误，请重试！");
                    }
                },
                error: function () {
                    removeModal();
                    failMsg("打印发生错误，请重试！");
                }
            });
        }

        // 打印有房无房证明
        function printYfwfzm(qlrmc, qlrzjh, fczmUrl, printType, zmtype, saveZl) {
            var date=getDate();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/yfwfzm", //（南通）保存有房无房证明打印需要的查询参数信息到Redis；
                type: "POST",
                data: JSON.stringify({"cxsqr": qlrmc, "sfzh": qlrzjh, "type": zmtype, "cxsd": date}),
                contentType: 'application/json',
                dataType: 'text',
                success: function (key) {
                    removeModal();
                    if (key) {
                        var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/nantong/yfwfzm/" + key + "/xml"; //获取（南通）有房无房证明打印信息 FR3打印的XML数据
                        print(fczmUrl, dataUrl, false);

                        savePrintInfo(fczmUrl, dataUrl, { //fczmUrl fr3打印模板的路径地址；dataUrl是查询参数的地址；
                            'zmbh': key,
                            "printType": printType,
                            "zl": saveZl,
                            "qlr": qlrmc,
                            "dyldTime": date //保存打印留档日志的操作时间；
                        });
                    } else {
                        failMsg("打印发生错误，请重试！");
                    }
                },
                error: function () {
                    removeModal();
                    failMsg("打印发生错误，请重试！");
                }
            });
        }

        // 打印有房无房证明（企业）
        function yfwfzmqy(d, cols) {
            var qlrmc = $("#qlrmc").val();
            var zl;
            if(d.length == 0){
                zl = $("#zl").val();
            }else{
                zl = d[0].zl;
            }
            // 需求26395 沟通后确定改为：允许名称打印但是长度要4字以上
            if (isNullOrEmpty(qlrmc) || $.trim(qlrmc).length < 4) {
                warnMsg(" 请在查询条件中输入正确的企业名称！");
                return;
            }

            // 查询有房无房
            var qlrxxList = new Array();
            qlrxxList.push({"qlrmc": qlrmc});
            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/yfwfzm/zfxx",
                type: "POST",
                data: JSON.stringify(qlrxxList),
                contentType: 'application/json',
                dataType: 'text',
                success: function (res) {
                    if (res) {
                        // 根据有房无房调用不同的打印模板
                        var fr3;
                        if ("YF" == res) {
                            fr3 = yfwfzmmb + zhcxpz.zhcxzmmbYfzmqy;
                        } else {
                            fr3 = yfwfzmmb + zhcxpz.zhcxzmmbWfzmqy;
                        }

                        printYfwfzm(qlrmc, "", fr3, "有房无房证明（企业）", "", zl);
                    } else {
                        removeModal();
                        failMsg("打印发生错误，请重试！");
                    }
                },
                error: function () {
                    removeModal();
                    failMsg("打印发生错误，请重试！");
                }
            });
        }


        // 打印权属证明
        function qszm(d, cols) {
            // 获得打印操作的日志时间和打印模板中的查阅日期；
            var date = getDate();
            var checkedData = layui.sessionData('checkedData'); //获取选中的记录内容；
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要打印的记录！");
                return;
            }
            var zl;
            var qlrmc;
            if (d.length == 0) {
                zl = $("#zl").val();
                qlrmc = $("#qlrmc").val();
            }else{
                zl = d[0].zl;
                qlrmc = d[0].qlrmc
            }
            // 判断是否包含临时状态
            var isLszt = false;
            $.each(checkedData, function (key, value) {
                if (0 == value.qszt) {
                    isLszt = true;
                }
            });
            if (isLszt == true) {
                warnMsg("该权证为临时状态，无法出具相关证明！");
                return;
            }

            //[{"bdcdyh":"1","qszt":"1","gzlslid":"1"},{"bdcdyh":"1","qszt":"1","gzlslid":"1"}]
            var qszmDyParamList = new Array();
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
                singleParam.username = userName;
                singleParam.cyrq = date;//时间添加到查询参数中；
                qszmDyParamList.push(singleParam);
            });

            // 缓存要打印的房产档案不动产单元号
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/print/nantong/qszm/bdcdyh",
                type: "POST",
                data: JSON.stringify(qszmDyParamList),
                contentType: 'application/json',
                dataType: 'json',
                success: function (data) {
                    if (data && data.redisKey) {
                        var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/nantong/qszm/" + data.redisKey + "/xml";
                        print(zfxxDaUrl, dataUrl, false);

                        savePrintInfo(zfxxDaUrl, dataUrl, {
                            'zmbh': data.cxh, "printType": "权属证明", "zl":zl,
                            "qlr": qlrmc, "dyldTime": date
                        });
                    } else {
                        failMsg("房产档案打印出错，请重试！");
                    }
                },
                error: function () {
                    failMsg("房产档案打印出错，请重试！");
                }
            });
        }

        // 打印抵押证明（后台配置打印数据源）
        function dyzm(d, cols) {
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要出具证明的数据行！");
                return;
            }
            var zl;
            var qlrmc;
            if (d.length == 0) {
                zl = $("#zl").val();
                qlrmc = $("#qlrmc").val();
            }else{
                zl = d[0].zl;
                qlrmc = d[0].qlrmc
            }
            // 判断是否包含临时状态
            var isLszt = false;
            $.each(checkedData, function (key, value) {
                if (0 == value.qszt) {
                    isLszt = true;
                }
            });
            if (isLszt == true) {
                warnMsg("该权证为临时状态，无法出具相关证明！");
                return;
            }

            var data = new Array();
            $.each(checkedData, function (key, value) {
                value.userName = userName;
                data.push(value);
            })

            // 获取并保存要打印的不动产单元号、项目ID信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/print/nantong/dyzm/csxx",
                type: "POST",
                data: JSON.stringify(data),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    if (data && data.redisKey) {
                        var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/nantong/dyzm/" + data.redisKey + "/xml";
                        print(dyzmFr3Url, dataUrl, false);

                        savePrintInfo(dyzmFr3Url, dataUrl, {
                            'zmbh': data.cxh, "printType": "抵押证明", "zl": zl,
                            "qlr": qlrmc
                        });
                    } else {
                        failMsg("请选中需出具证明记录重试");
                    }
                },
                error: function () {
                    failMsg("请选中需出具证明记录重试");
                }
            });
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
            $("#zh").val(null);
            $("#fjh").val(null);
            $("#slbh").val(null);
            $("#fwbh").val(null);
            $("#zhlsh").val(null);
            $("#ghyt").val(null);

            $(".mhlx3").each(function (index, item) {
                $("#" + item.id).val(3);
            });
            $(".mhlx0").each(function (index, item) {
                $("#" + item.id).val(0);
            });
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
                warnMsg(" 请输入必要查询条件，例如权利人名称");
                return false;
            }

            // 设置默认的字段模糊类型
            var mhlx = {
                "bdcdyhmhlx": 3, "bdcqzhmhlx": 3, "qlrmcmhlx": 3, "qlrzjhmhlx": 3, "ywrmcmhlx": 3,
                "ywrzjhmhlx": 3, "zlmhlx": 3, "ycqzhmhlx": 3, "zhmhlx": 3, "fjhmhlx": 3, "slbhmhlx": 3,
                "fwbhmhlx": 3, "zhlshmhlx": 0, "ybdcdyhmhlx": 3, "yxtcqzhmhlx": 3, "zsyzhmhlx": 0
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
            // 获取复选框参数
            obj.qllx = getQllxCheckedVal();
            // 获取复选框参数
            obj.qszt3 = getQsztCheckedVal();
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
                    changeCheckboxBackground([{'name': 'bdc_change_red', 'color': 'red'}])
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();

                    // 查询附加信息展示
                    getZhcxFjxx(currentPageData);

                    //根据权利状态、办理状态整行显色
                    var colorList = [];
                    $.ajax({
                        url: '/realestate-inquiry-ui/rest/v1.0/status/color',
                        type: "GET",
                        success: function (data) {
                            for (var i in data) {
                                if (data[i]) {
                                    colorList.push({name: i, color: '#333', background: data[i]});
                                }
                            }
                            changeTrBackgroundExceptRight(colorList);
                        }
                    });

                    showMoreBtn();

                    // 默认按钮隐藏
                    setBtnHidden();
                    // 控制按钮权限
                    setElementAttrByModuleAuthority(moduleCode);
                    // 根据版本显示按钮
                    setBtnQhkzm();
                    getJyjg();

                    // 单独保存查询数据到数据库供本地综合查询日志页面使用
                    saveQueryLogToDataBase(obj, currentPageData, "ZHCX");
                }
            });
        }

        // 高级查询
        $('#moreSearch').on('click', function () {
            needsearch = false;

            layer.open({
                title: '高级查询',
                type: 2,
                area: ['960px', '610px'],
                content: 'search.html?code=nantong',
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
                    var iframe = window['layui-layer-iframe' + index];
                    iframe.setData1(obj);
                },
                end: function () {
                    if (needsearch && searchData) {
                        addModel();
                        // 高级查询需要清空勾选缓存
                        layui.sessionData("checkedData", null);
                        // IP地址
                        searchData.ipaddress = ipaddress;

                        // 处理用途
                        if(!isNullOrEmpty(searchData.djyt)) {
                            var arr = searchData.djyt.split(":");
                            if (arr.length === 2) {
                                switch (arr[1]) {
                                    case 'fwyt':
                                        searchData.dzwyt = arr[0];
                                        break;
                                    case 'yhlxa':
                                        searchData.yhlxa = arr[0];
                                        break;
                                    case 'yhlxb':
                                        searchData.yhlxb = arr[0];
                                        break;
                                }
                            }
                        }
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
                                showMoreBtn();

                                // 查询附加信息展示
                                getZhcxFjxx(currentPageData);

                                //根据权利状态、办理状态整行显色
                                var colorList = [];
                                $.ajax({
                                    url: '/realestate-inquiry-ui/rest/v1.0/status/color',
                                    type: "GET",
                                    success: function (data) {
                                        for (var i in data) {
                                            colorList.push({name: i, color: '#333', background: data[i]});
                                        }
                                        changeTrBackgroundExceptRight(colorList);
                                    }
                                });

                                // 默认按钮隐藏
                                setBtnHidden();
                                // 控制按钮权限
                                setElementAttrByModuleAuthority(moduleCode);
                                // 根据版本显示按钮
                                setBtnQhkzm();

                                // 单独保存查询数据到数据库供本地综合查询日志页面使用
                                saveQueryLogToDataBase(searchData, currentPageData, "ZHCX");
                            }
                        });
                    }
                }
            });
        });

        //摩科接口查询身份证信息
        $("#mksfzdq").on('click', function () {
            addModel();
            getReturnData("/rest/v1.0/zszm/mkjk", {}, "GET", function (result) {
                removeModal();
                if (result) {
                    if (result.code == "1") {
                        $('input[name="qlrmc"]').val($.trim(result.data.name));
                        $('input[name="qlrzjh"]').val($.trim(result.data.cardNum));
                    } else {
                        ityzl_SHOW_WARN_LAYER("摩科接口获取权利人信息失败" + result.message)
                    }
                }
            }, function (xhr) {
                delAjaxErrorMsg(xhr)
            })
        });

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
                                if (!fjxxDataJson[item.xmid]) {
                                    fjxxDataJson[item.xmid] = item;
                                }
                            });
                        }
                    }
                });
            }
        }

        //查询条件：复选框事件绑定
        form.on('checkbox(qllx)', function (data) {
            search();
        });
        //查询条件：复选框事件绑定
        form.on('checkbox(qszt3)', function (data) {
            search();
        });

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

        // 设置默认按钮默认隐藏
        function setBtnHidden() {
            $("#excel").hide();
            $("#yfwfzm").hide();
            $("#qtzm").hide();
            $("#qszm").hide();
            $("#dyzm").hide();
            $("#yfwfzmqy").hide();
            if (version == 'haimen') {
                $("#wbdcdjzlzm").hide();
                $("#qhkzm").hide();
            }
            $("#lhgxrcx").hide();
            $("#lscx").hide();
        }

        // 根据页面版本控制“无不动产登记资料证明”按钮
        function setBtnQhkzm() {
            if (version == 'haimen') {
                // 海门版本“无不动产登记资料证明”按钮移出展示
                $(".wbdcdjzlzm").hide();
            } else {
                $("#wbdcdjzlzm").hide();
            }
        }
    });
    //获取页面复选框权利类型选中的信息
    window.getQllxCheckedVal = function () {
        var checkedArray = new Array();
        $("input:checkbox[name='qllxmc']:checked").each(function (i) {
            var strArray = $(this).val().split(",");
            $.each(strArray, function (index, val) {
                if (null != val && "" != val) {
                    checkedArray.push(val);
                }
            });
        });
        return checkedArray.join(",");
    }

    //获取页面复选框权属状态选中的信息
    window.getQsztCheckedVal = function () {
        var checkedArray = new Array();
        $("input:checkbox[name='qsztmc']:checked").each(function (i) {
            var strArray = $(this).val().split(",");
            $.each(strArray, function (index, val) {
                if (null != val && "" != val) {
                    checkedArray.push(val);
                }
            });
        });
        return checkedArray.join(",");
    }

    // 证书证明预览
    window.viewBdcZszm = function (gzlslid) {
        if (isNullOrEmpty(gzlslid)) {
            return;
        }
        window.open("/realestate-register-ui/rest/v1.0/bdcZs?processInsId=" + gzlslid + "&zsyl=false&readOnly=true");
    }

    // 高级查询传值
    window.setSearchData = function (data, flag) {
        searchData = data;
        needsearch = flag;

        if (searchData) {
            form.val('searchform', JSON.parse(JSON.stringify(searchData)));
        }
    }

    //身份证读卡器设置
    window.onReadIdCard = function (qlrlb) {
        try {
            var cardInfo = readIdCard();
            if (cardInfo.ReadCard()) {
                var name = $.trim(cardInfo.Name);
                var number = $.trim(cardInfo.ID);
                if (!version || version == 'nantong') {
                    if (1 == qlrlb) {
                        $('input[name="qlrmc"]').val(name);
                        $('input[name="qlrzjh"]').val(number);
                    } else {
                        $('input[name="ywrmc"]').val(name);
                        $('input[name="ywrzjh"]').val(number);
                    }
                } else {
                    if (1 == qlrlb) {
                        var qlrmc = $('input[name="qlrmc"]').val();
                        if (isNotBlank(qlrmc)) {
                            name = qlrmc + "," + name;
                        }
                        $('input[name="qlrmc"]').val(name);

                        var qlrzjh = $('input[name="qlrzjh"]').val();
                        if (isNotBlank(qlrzjh)) {
                            number = qlrzjh + "," + number;
                        }
                        $('input[name="qlrzjh"]').val(number);
                    } else {
                        var ywrmc = $('input[name="ywrmc"]').val();
                        if (isNotBlank(ywrmc)) {
                            name = ywrmc + "," + name;
                        }
                        $('input[name="ywrmc"]').val(name);

                        var ywrzjh = $('input[name="ywrzjh"]').val();
                        if (isNotBlank(ywrzjh)) {
                            number = ywrzjh + "," + number;
                        }
                        $('input[name="ywrzjh"]').val(number);
                    }
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
                var zdDmList = zdDm.split(",");
                var zdDmMc = new Array();
                var zdVals = eval(zdListName.fwyt);
                if (zdVals) {
                    for (var j = 0; j < zdDmList.length; j++) {
                        for (var i = 0; i < zdVals.length; i++) {
                            var zdVal = zdVals[i];
                            console.log(zdDmList[j]);
                            console.log(zdVal.DM);
                            if (zdDmList[j] == zdVal.DM) {
                                zdDmMc.push(zdVal.MC);
                            }
                        }
                    }
                    return zdDmMc.join(",");
                }
                return zdDm;
            }
            return '';
        } catch (e) {
            return "";
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

    // 获取权利类型筛选配置项
    function getQllxSxpz(){
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/getQllxSxpz",
            type: "GET",
            contentType: 'application/json',
            success: function (data) {
                if(data != null){
                    $.each(data, function(index,item){
                        var sxpz = item.split(" ");
                        if(sxpz.length == 2){
                            $("#qllxDiv").append('<input type="checkbox" name="qllxmc" title="'+ sxpz[0] + '" value="' + sxpz[1] + '"    lay-filter="qllx" lay-skin="primary" />');
                        }else if(sxpz.length == 3 && sxpz[2] == 'checked'){
                            $("#qllxDiv").append('<input type="checkbox" name="qllxmc" title="'+ sxpz[0] + '" value="' + sxpz[1] + '"    lay-filter="qllx" lay-skin="primary" checked/>');
                        }else{
                            warnMsg("权利类型筛选项配置不正确，无法加载！");
                            return false;
                        }

                    })
                    form.render();
                }else{
                    warnMsg("没有配置权利类型筛选项！");
                }
            },
            error: function () {
                failMsg("获取页面权利类型筛选项失败！");
            }
        });
    };

});

/**
 * 查询档案调用的参数信息
 * @param xmid
 */
function tellTdFromBdc(xmid) {
    var dataObj = {};
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zszm/tellTdFromBdc?xmid=' + xmid,
        async: false,
        dataType: "json",
        success: function (data) {
            dataObj = data;
        }
    });
    return dataObj;
}

/**
 * 档案调用
 */
function dady(data) {
    var dadyInfo = tellTdFromBdc(data.xmid)

    if (dadyInfo.version == "haimen") {
        // 海门版本
        if (!dadyInfo || isNullOrEmpty(dadyInfo.hmDadyUrl)) {
            failMsg("未配置档案调用地址，请联系管理员!");
            return;
        }
        window.open(dadyInfo.hmDadyUrl + data.slbh);
    } else {
        // 南通版本

        /**
         * 【32413】 【南通】档案系统需求
         * 根据当前用户的部门判断跳转的地址。例如，市区的用户点击档案调用后调取市区的档案数据，开发区的用户点击档案调用后调取开发区的档案数据
         */
        if (dadyInfo.kfqFlag) {
            var now = new Date();
            var dateStr = now.getFullYear() + "-" + (now.getMonth() + 1) + '-' + now.getDate();
            var keycode = data.slbh;
            var user = dadyInfo.userName;
            var key = hex_md5((keycode + user + "VIEWPIC" + dateStr).toUpperCase()).toUpperCase();
            var url = dadyInfo.kfqDadyUrl + "?keycode=" + keycode + "&user=" + user + "&key=" + key;
            window.open(url);
        } else {
            if (dadyInfo.XMLY == "2") {// 土地
                var url = dadyInfo.tdDadyUrl;
                url += "?userName=" + encodeURI(dadyInfo.userName);
                url += "&qlrmc=" + encodeURI(data.qlrmc ? data.qlrmc : "");
                url += "&tdzl=" + encodeURI(data.zl ? data.zl : "");
                url += "&tdzh=" + encodeURI(data.ytdzh ? data.ytdzh : "");
                url += "&djh=" + encodeURI(data.ybdcdyh ? data.ybdcdyh : "");
                url += "&password=";
                window.open(url);
            } else {// 不动产
                var now = new Date();
                var dateStr = now.getFullYear() + "-" + (now.getMonth() + 1) + '-' + now.getDate();
                var keycode = data.slbh;
                var user = dadyInfo.userName;
                var key = hex_md5((keycode + user + "VIEWPIC" + dateStr).toUpperCase()).toUpperCase();
                var url = dadyInfo.dadyUrl + "?keycode=" + keycode + "&user=" + user + "&key=" + key;
                window.open(url);
            }
        }

    }

    saveDetailLog("ZHCXDADY", "综合查询-档案调用", data);
}

/**
 *打印其他证明
 * @param type 证明类型
 *      bwsqrsyzm   不为申请人所有证明;     ycszm      已出售证明;
 *      wbdcdjzlzm  无不动产登记资料证明;   dkzm       贷款证明 ;
 *      zlbgzmyzl   座落变更证明（原座落）; zlbgzmxzl  座落变更证明（现座落）;
 *      qtzm        其他证明
 */
function qtzm(type) {
    var qlrmc = $("#qlrmc").val();
    var qlrzjh = $("#qlrzjh").val();
    var zl = $("#zl").val();

    if ("ycszm" == type || "bwsqrsyzm" == type || "wbdcdjzlzm" == type) {
        if (isNullOrEmpty(qlrmc) || isNullOrEmpty(qlrzjh) || isNullOrEmpty(zl)) {
            warnMsg("请输入权利人名称、证件号、坐落信息！")
            return;
        }
    } else if ("ycszm" == type || "dkzm" == type || "zlbgzmyzl" == type || "zlbgzmxzl" == type || "qtzm" == type) {
        if (isNullOrEmpty(qlrmc) || isNullOrEmpty(qlrzjh)) {
            warnMsg("请输入权利人名称、证件号信息！")
            return;
        }
    }

    if ("ycszm" == type || "dkzm" == type || "zlbgzmxzl" == type || "zlbgzmyzl" == type) {
        var checkedData = layui.sessionData('checkedData');
        if ($.isEmptyObject(checkedData)) {
            warnMsg("请选择需要出证明的数据行！");
            return;
        }
    }

    addModel();
    var checkedData = layui.sessionData('checkedData');
    if (Object.keys(checkedData).length <= 1) {
        var zmxx = getZmxx();
        if (zmxx) {
            zmxx.cxsqr = qlrmc;
            zmxx.sfzh = qlrzjh;
            zmxx.qlrmc = qlrmc;
            zmxx.qlrzjh = qlrzjh;
            zmxx.zl = zl;
            $.each(checkedData, function (key, value) {
                zmxx.zl = value.zl;
                zmxx.bdcqzh = value.bdcqzh;
                zmxx.djsj = value.djsj;
                zmxx.ywrmc = value.ywrmc;
                zmxx.ycqzh = value.ycqzh;
                zmxx.xmid = value.xmid;
            });
            setZmnr(type, zmxx);

            $.cookie('fczm', JSON.stringify(zmxx));
            removeModal();
            window.open("fczm.html?pageType=1&type=" + type);
        }
    } else {
        // 保存勾选的记录行数据
        var array = new Array();
        $.each(checkedData, function (key, value) {
            var item = value;
            value.LAY_CHECKED = false;
            delete item.bdcdyZtDTO;
            item.cxsqr = qlrmc;
            item.sfzh = qlrzjh;

            // 获取基本信息
            var zmxx = getZmxx();
            if (zmxx) {
                item.bh = zmxx.bh;
                item.cxsd = zmxx.cxsd;
                item.zmsj = zmxx.zmsj;
            }
            // 设置证明内容
            setZmnr(type, item);
            array.push(item);
        });
        layui.data('fczmListData', {key: "data", value: JSON.stringify(array)});

        // 查询信息
        $.cookie('cxxx', JSON.stringify({"qlrmc": qlrmc, "qlrzjh": qlrzjh, "zl": zl}));
        removeModal();
        window.open("fczmList.html?type=" + type);
    }
}

/**
 * 获取证明编号和验证码
 */
function getZmxx() {
    var res = null;
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/fczm",
        type: "GET",
        async: false,
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            res = data;
        },
        error: function () {
            failMsg("操作发生错误，请重试！");
        }
    });
    return res;
}

/**
 * 设置其他证明证明内容
 * @param type 证明类型
 * @param fczm 证明实体
 */
function setZmnr(type, fczm) {
    setNullStr(fczm);

    // 对于已出售证明，需要查找下一手的登记时间作为当前的注销时间
    var cssj;
    if ("ycszm" == type) {
        cssj = getFdcqCssj(fczm.xmid);
        if (isNullOrEmpty(cssj)) {
            cssj = "  ";
        }
    }

    if ("bwsqrsyzm" == type) {
        fczm.zmnr = "   " + fczm.zl + "当前登记不动产权利人不是" + fczm.cxsqr + "。";
    } else if ("ycszm" == type) {
        fczm.zmnr = "   " + fczm.cxsqr + "原有" + fczm.zl + "，不动产权证号：" + fczm.bdcqzh + "，上述不动产已于" + cssj + "出售给他人。";
    } else if ("wbdcdjzlzm" == type) {
        fczm.zmnr = "   " + fczm.zl + "当前无不动产权登记资料。"
    } else if ("dkzm" == type) {
        fczm.zmnr = "   " + fczm.cxsqr + "于" + formatDate2(fczm.djsj) + "向" + fczm.ywrmc + "购买" + fczm.zl + "，现不动产权证号：" + fczm.bdcqzh + "。\n" +
            "   " + fczm.ywrmc + "原不动产权证号：" + fczm.ycqzh + "。\n"
    } else if ("zlbgzmyzl" == type) {
        fczm.zmnr = "   " + fczm.zl + "由" + fczm.cxsqr + "领有不动产权证，不动产权证证号为：" + fczm.bdcqzh + "。该处原发证座落为：";
    } else if ("zlbgzmxzl" == type) {
        fczm.zmnr = "   " + fczm.zl + "由" + fczm.cxsqr + "领有不动产权证，不动产权证证号为：" + fczm.bdcqzh + "。该处现座落为：";
    } else if ("qtzm" == type) {
        fczm.zmnr = "   ";
    }
}

// 获取房产出售时间
function getFdcqCssj(xmid) {
    var djsj = null;
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/fczm/fdcq/cssj?xmid=" + xmid,
        type: "GET",
        async: false,
        dataType: 'text',
        success: function (data) {
            djsj = data;
        },
        error: function () {
            failMsg("未获取到出售时间，请重试！");
        }
    });
    return djsj;
}

function setNullStr(fczm) {
    if (isNullOrEmpty(fczm.zmnr)) {
        fczm.zmnr = "";
    }
    if (isNullOrEmpty(fczm.zl)) {
        fczm.zl = "";
    }
    if (isNullOrEmpty(fczm.qlrmc)) {
        fczm.qlrmc = "";
    }
    if (isNullOrEmpty(fczm.bdcqzh)) {
        fczm.bdcqzh = "";
    }
    if (isNullOrEmpty(fczm.djsj)) {
        fczm.djsj = "";
    }
    if (isNullOrEmpty(fczm.ywrmc)) {
        fczm.ywrmc = "";
    }
    if (isNullOrEmpty(fczm.ycqzh)) {
        fczm.ycqzh = "";
    }
}

function formatDate2(timestamp) {
    if (!timestamp) {
        return '';
    }
    var time = new Date(timestamp);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    return y + '年' + add0(m) + '月' + add0(d) + '日';
}

/**
 * 获取房屋价值
 * @returns {*}
 */
function getJyjg() {
    // 延迟加载
    ycjz("xmid", "jyjg", "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/fczm/fdcq/jyjg");
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

function getDate() {
    var date = new Date();

    var year = date.getFullYear();        //年 ,从 Date 对象以四位数字返回年份
    var month = date.getMonth() + 1;      //月 ,从 Date 对象返回月份 (0 ~ 11) ,date.getMonth()比实际月份少 1 个月
    var day = date.getDate();             //日 ,从 Date 对象返回一个月中的某一天 (1 ~ 31)

    var hours = date.getHours();          //小时 ,返回 Date 对象的小时 (0 ~ 23)
    var minutes = date.getMinutes();      //分钟 ,返回 Date 对象的分钟 (0 ~ 59)
    var seconds = date.getSeconds();      //秒 ,返回 Date 对象的秒数 (0 ~ 59)

    //修改月份格式
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }

    //修改日期格式
    if (day >= 0 && day <= 9) {
        day = "0" + day;
    }

    //修改小时格式
    if (hours >= 0 && hours <= 9) {
        hours = "0" + hours;
    }

    //修改分钟格式
    if (minutes >= 0 && minutes <= 9) {
        minutes = "0" + minutes;
    }

    //修改秒格式
    if (seconds >= 0 && seconds <= 9) {
        seconds = "0" + seconds;
    }

    //获取当前系统时间  格式(yyyy-mm-dd hh:mm:ss)
    var currentFormatDate = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;

    return currentFormatDate;

}