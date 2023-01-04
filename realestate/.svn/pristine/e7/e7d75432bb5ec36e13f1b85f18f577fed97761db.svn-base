/**
 * 综合查询标准版页面JS处理
 */
// 打印的模板现场维护，此路径只是名义上的路径，具体打印时候会更改为读取配置的打印模板放置路径，但是模板名称要一致
// 房产证明打印模板
var fczmDymb = getIP() + "/realestate-inquiry-ui/static/printModel/fczm.fr3";
// (土地)权属证明打印模板
var tdQszmDymb = getIP() + "/realestate-inquiry-ui/static/printModel/tdqszm.fr3";
// (房屋)权属证明打印模板
var fwQszmDymb = getIP() + "/realestate-inquiry-ui/static/printModel/fwqszm.fr3";
// (林权)权属证明打印模板
var lqQszmDymb = getIP() + "/realestate-inquiry-ui/static/printModel/lqqszm.fr3";

// 证明验证对应的组合规则标识
// 有房无房证明
var ZHCX_YFWFZM_ZHGZ = "ZHCX_YFWFZM_ZHGZ";
// 权属证明
var ZHCX_QSZM_ZHGZ = "ZHCX_QSZM_ZHGZ";

// 长度太长需要控制展示的字段
var reverseList = ['zl'];
// 高级查询控制参数
var searchData, needsearch;
// 获取字典
var zdList = getMulZdList("fwyt");
var bdclxList = getMulZdList("bdclx");
//获取页面版本
var version = getQueryString("version");
// 用户IP地址
var ipaddress;
// 当前登录用户中文名
var useralias;

var cxdhmap;

var qzhflag = false;
// 设置默认的字段模糊类型
var mhlx = {
    "bdcdyhmhlx": 0, "bdcqzhmhlx": 0, "qlrmcmhlx": 0, "qlrzjhmhlx": 0, "ywrmcmhlx": 0,
    "ywrzjhmhlx": 0, "zlmhlx": 3, "ycqzhmhlx": 0, "zhmhlx": 0, "fjhmhlx": 0, "slbhmhlx": 0,
    "fwbhmhlx": 0, "zhlshmhlx": 0, "ybdcdyhmhlx": 0, "yxtcqzhmhlx": 0, "zsyzhmhlx": 0
};
// 是否获取缓存
var sfhqhc = true;
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
        //根据version确定表格内部操作按钮
        var bar;
        if (version == 'bengbu') {
            bar = '#barDemoForBengbu';
        } else {
            bar = '#barDemo';
        }
        reloadCxtj();
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
                {field: 'ybdcdyh', width: 200, title: '原不动产单元号', hide: true},
                {
                    field: 'bdcqzh', width: 280, title: '不动产权证号（明）', sort: true,
                    templet: function (d) {
                        return showBdcqzh(d);
                    }
                },
                {field: 'zl', minWidth: 300, title: '坐落', sort: true,},
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
                        return zdGhytToMc('fwyt', d.ghyt, zdList);
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
                    field: 'bdclx', width: 120, title: '不动产类型', hide: true,
                    templet: function (d) {
                        return zdBdclx('bdclx', d.bdclx, bdclxList);
                    }
                },
                {
                    field: 'qszt', width: 110, title: '权属状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatQsztWithCx(d.qszt, d.ajzt);
                    }
                },

                {
                    field: 'bdcdyZtDTO', width: 100, title: '限制状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        // return formatXzzt(d.bdcdyZtDTO);
                        return showLimitState(d);
                    }
                },

                {
                    field: 'ajzt', width: 100, title: '办理状态', fixed: 'right', sort: true, hide: true,
                    templet: function (d) {
                        return formatAjzt(d.ajzt);
                    }
                },
                {title: '查看', fixed: 'right', toolbar: bar, width: 180}
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
                form.render();
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
                    }
                });
            }
        } catch (err) {
            console.info("获取IP地址出错：" + err.toString());
        }

        // 获取权利类型筛选配置项
        getQllxSxpz();

        // 获取配置项
        var zhcxpz;
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/bzb/zhcx/pz",
            type: "GET",
            dataType: 'json',
            async: false,
            success: function (res) {
                if (res) {
                    zhcxpz = res;
                    cxdhmap = zhcxpz.cxdhmap;
                } else {
                    failMsg("发现页面未配置正确，请联系管理员解决！");
                }
            },
            error: function () {
                failMsg("发现页面未配置正确，请联系管理员解决！");
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
                        //添加勾选全部按钮
                        $("#qllxDiv").append('<input type="checkbox" name="gxqb" title="全部" lay-filter="gxqb" lay-skin="primary" />');
                        form.render();
                        //判断换行增加table的margin 解决按钮遮盖问题
                        setTimeout(function () {
                            if($(".bdc-gxk").height()>34){
                                $(".bdc-table-box").css("margin-top","35px");
                                $("body").css("overflow","hidden");
                            }
                        },100);
                    }else{
                        warnMsg("没有配置权利类型筛选项！");
                    }
                },
                error: function () {
                    failMsg("获取页面权利类型筛选项失败！");
                }
            });
        };

        //全选全不选判断
        form.on('checkbox(gxqb)', function (data) {
            if( data.elem.checked){
                $('[name="qllxmc"]').each(function() {
                    $(this).prop("checked", true);
                });
            }else{
                $('[name="qllxmc"]').each(function() {
                    $(this).prop("checked", false);
                });
            }
            form.render('checkbox');
            search();
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
                    var flag = yzlpb(data);
                    if (flag){
                        warnMsg("当前不动产单元无楼盘表！");
                        return;
                    }
                    // 只有房屋类型允许查看
                    window.open("/building-ui/lpb/view?code=analysis&bdcdyh=" + data.bdcdyh +"&qjgldm="+data.qjgldm);
                    saveDetailLog("ZHCXLPB", "综合查询-查看楼盘表", data);
                } else {
                    warnMsg("当前不动产单元无楼盘表！");
                }
            } else if (obj.event === 'djb') {
                window.open("/realestate-register-ui/view/djbxx/bdcDjb.html?param=" + data.bdcdyh);
                saveDetailLog("ZHCXDJB", "综合查询-查看登记簿", data);
            } else if (obj.event === 'dady') {
                openBdcDjDa(data);
                saveDetailLog("ZHCXDADY", "综合查询-档案调用", data);
            } else if (obj.event === 'txdw') {
                window.open('/building-ui/omp/redirect/' + data.bdcdyh + '/' + data.bdclx);
                saveDetailLog("ZHCXTXDW", "综合查询-图形定位", data);
            } else if(obj.event === 'zdthstdy'){
                zdthstdy(data);
                saveDetailLog("ZHCXZDTHSTDY", "综合查询-宗地图户室图打印", data);
            } else if(obj.event === 'lct') {
                lct(data);
                saveDetailLog("ZHCXLCT", "综合查询-查看流程图", data);
            }
        });

        /**
         * 宗地图户室图打印
         */
        function zdthstdy(data) {
            if(!data || isNullOrEmpty(data.bdcdyh)) {
                warnMsg("当前记录无不动产单元号，无法打印");
                return;
            }

            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zs/image/zdthst/" + data.bdcdyh;
            var mburl = getIP() + "/realestate-inquiry-ui/static/printModel/zdthst.fr3";
            printChoice("zdthstdy", "realestate-inquiry-ui", dataUrl, mburl, false, "zdthstdy");
        }

        // 存储打印配置的sessionKey，当前页的打印类型
        var dylxArr = ["fwqszm", "tdqszm", "lqqszm"];
        var sessionKey = "bdcZszm";
        setDypzSession(dylxArr, sessionKey);

        // 监听表格操作栏按钮
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    if(!isNullOrEmpty(checkStatus.data)) {
                        addModel();
                    }
                    exportExcel(checkStatus.data, obj.config.cols[0],"checked");
                    break;
                case 'exportAllExcel':
                    layer.confirm("是否导出全部数据？",{
                        title: "提示",
                        btn: ["确认","取消"],
                        btn1: function (index) {
                            addModel();
                            exportAllExcel(checkStatus.data, obj.config);
                            layer.close(index);
                        },
                        btn2: function (index) {
                            obj.config.where.type = "";
                            return;
                        }
                    })
                    break;
                case 'yfwfzm':
                    yfwfzm(checkStatus.data, obj.config.cols[0]);
                    break;
                case 'qszm':
                    qszm(checkStatus.data, obj.config.cols[0]);
                    break;
            }
        });

        // 导出Excel
        // 通过隐藏form提交方式，避免ajax方式不支持下载文件
        function exportExcel(d, cols, type) {
            console.log("d-----"+d);
            console.log("cols----"+cols);
            if(!isNullOrEmpty(d)) {
                // isNullOrEmpty(d);

                var checkedData = layui.sessionData('checkedData');
                if ($.isEmptyObject(checkedData) && type == "checked") {
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
                $("#sort").val("qszt_ha")
                var data = new Array();
                if (type == "checked") {
                    $.each(checkedData, function (key, value) {
                        data.push(value);
                    })
                } else {
                    data = d;
                }

                for (var i = 0; i < data.length; i++) {
                    var xmid = data[i].xmid;

                    data[i].xh = i + 1;
                    data[i].jzmj 	= getFjxxVal(xmid, "jzmj");
                    data[i].tdsyqmj = getFjxxVal(xmid, "tdsyqmj");
                    data[i].zh 		= getFjxxVal(xmid, "zh");
                    data[i].fjh 	= getFjxxVal(xmid, "fjh");
                    data[i].szsj 	= formatDate(data[i].szsj);
                    data[i].djsj 	= formatDate(data[i].djsj);
                    data[i].qszt 	= formatQsztOfData(data[i].qszt);
                    data[i].ghyt 	= zdGhytToMc('fwyt', data[i].ghyt, zdList);
                    data[i].ajzt 	= formatAjzt(data[i].ajzt);
                    data[i].xzzt 	= formatXzztOfData(data[i].bdcdyZtDTO);
                }
                $("#data").val(JSON.stringify(data));
                $("#form").submit();

                setTimeout(function () {
                    removeModal();
                }, 2000);
            }else {
                warnMsg("请先勾选数据");
            }

        }

        function exportAllExcel(data, obj){
            var cols = obj.cols[0];
            var url = obj.url+"?page=0&&size="+zhcxpz.dcts;
            var paramData = obj.where;
            paramData["type"] = "export";
            $.ajax({
                url: url,
                dataType: "json",
                data: paramData,
                success: function (data) {
                    if(data.code == 0){//查询成功
                        if(sfhqhc){
                            var newData = fenge(data.content,500);
                            newData.forEach(function (value){
                                getZhcxFjxx(value);
                            })
                            sfhqhc = false;
                        }
                        exportExcel(data.content,cols);
                    }
                }
            });
        }

        /**
         * 从缓存的附加信息中获取对应属性值，不能直接通过 $("id").text() 形式获取，因为导出Excel跨页导出时候这样无法获取到非当前可视页数据
         * @param xmid 所在记录项目ID
         * @param name 属性字段名称
         */
        function getFjxxVal(xmid, name) {
        	if(fjxxDataJson && fjxxDataJson[xmid] && !isNullOrEmpty(fjxxDataJson[xmid][name])) {
        		return fjxxDataJson[xmid][name];
        	} 
        	return "";
        }

        // 出具房产有房、无房证明（系统接口查询数据封装XML内容打印）
        function yfwfzm(d, cols) {
            var qlrmc = $("#qlrmc").val();
            var qlrzjh = $("#qlrzjh").val();
            var zl;
            if (d.length == 0) {
                zl = $("#zl").val();
            }else{
                zl = d[0].zl;
            }
            // 出具有房无房时，姓名必填,配置证件号是否必填
            if (1 == zhcxpz.zmzjhbt) {
                if (isNullOrEmpty(qlrmc) || isNullOrEmpty(qlrzjh)) {
                    warnMsg(" 请输入权利人名称和证件号！");
                    return;
                }
            }

            if (1 != zhcxpz.zmzjhbt && isNullOrEmpty(qlrmc)) {
                warnMsg(" 请输入权利人名称！");
                return;
            }

            if (!isNullOrEmpty(qlrzjh)) {
                // 权利人证件号不为空，需要判断和名称数量是否一致
                var qlrmcArray = qlrmc.split(",");
                var qlrzjhArray = qlrzjh.split(",");
                if (qlrmcArray.length > 0 && qlrzjhArray.length > 0 && qlrmcArray.length != qlrzjhArray.length) {
                    warnMsg(" 已输入权利人名称、证件号数量应一致！");
                    return;
                }
            }

            var qlrxxList = new Array();
            var qlrmcArray = qlrmc.split(",");
            var qlrzjhArray = qlrzjh.split(",");
            if (qlrmcArray.length > 0) {
                for (var i = 0; i < qlrmcArray.length; i++) {
                    if (!isNullOrEmpty(qlrmcArray[i])) {
                        var qlrzjhTemp = "";
                        if (qlrzjhArray.length > 0) {
                            qlrzjhTemp = qlrzjhArray[i];
                        }
                        var qlrxx = {"qlrmc": qlrmcArray[i], "zjh": qlrzjhTemp};
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
                        checkApplyRegistration(res, ZHCX_YFWFZM_ZHGZ, 'ZHCX').fail(function () {
                            return;
                        }).done(function () {
                            printFczm(qlrmc, qlrzjh, zl);
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

        // 打印有房无房证明
        function printFczm(qlrmc, qlrzjh, zl) {
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhcx/bzb/yfwfzm",
                type: "POST",
                data: JSON.stringify({"qlrmc": qlrmc, "zjh": qlrzjh, "jbr": useralias}),
                contentType: 'application/json',
                dataType: "text",
                success: function (key) {
                    if (key) {
                        var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/zhcx/bzb/yfwfzm/" + key + "/xml";
                        print(fczmDymb, dataUrl, false);

                        // 记录打印有房无房数据库记录
                        saveZhcxLogToDataBase(getSearchData(), "", 1, "yfwfzm");
                        savePrintInfo(fczmDymb, dataUrl, {'zmbh': key, "printType": "有房无房证明","zl":zl, "qlr": qlrmc});
                    } else {
                        failMsg("打印失败，请重试！");
                    }
                },
                error: function () {
                    failMsg("打印失败，请重试！");
                }
            });
        }

        // 打印权属证明 （土地和房屋合成一个按钮，根据不动产类型调用对应的模板、数据源）
        function qszm(d, cols) {
            var checkedData = layui.sessionData('checkedData');
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
                    param.xmid = value.xmid;
                    param.bdclx = value.bdclx;
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
                            // 根据不动产类型选择对应的打印模板和打印数据源
                            var mburl = fwQszmDymb;
                            var dylx = "fwqszm";
                            switch (bdclx.toString()) {
                                case "1":
                                    mburl = tdQszmDymb;
                                    dylx = "tdqszm";
                                    break;
                                case "2":
                                    mburl = fwQszmDymb;
                                    dylx = "fwqszm";
                                    break;
                                case "3":
                                    mburl = lqQszmDymb;
                                    dylx = "lqqszm";
                                    break;
                            }

                            // 执行打印
                            var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/zhcx/bzb/qszm/" + data.redisKey + "/" + bdclx + "/xml";
                            var appName = "realestate-inquiry-ui";

                            printChoice(dylx, appName, dataUrl, mburl, false, sessionKey);

                            //print(mburl, dataUrl, false);

                            // 保存日志
                            savePrintInfo(mburl, dataUrl, {'zmbh': data.cxh, "printType": "权属证明", "zl":zl, "qlr": qlrmc});

                            // 记录打印权属证明数据库记录
                            saveZhcxLogToDataBase(getSearchData(), "", 1, "qszm");
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

        // 异地受理角色查询条件展示查询
        ydslcxtj();

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
            //添加已选条目数
            var checkedData = layui.sessionData('checkedData');
            var arrcheck = Object.keys(checkedData);
            var total = arrcheck.length;
            $('#choosecount').html("已选"+total+"条");
        });

        // 超链接显示不动产权证号
        function showBdcqzh(data) {
            if (!isNullOrEmpty(data.bdcqzh)) {
                return '<a href="javascript:viewBdcZszm(&quot;' + data.gzlslid + '&quot;)" class="layui-table-link">' + data.bdcqzh + '</a>';
            } else {
                return "";
            }
        }

        // 超链接显示限制状态信息
        function showLimitState(data) {
            var bdcdyZtDTO = data.bdcdyZtDTO;
            var value = "";
            if (bdcdyZtDTO != undefined) {
                if (bdcdyZtDTO.cf === true) {
                    value += '<a href="javascript:viewCf(&quot;' + bdcdyZtDTO.bdcdyh + '&quot;)"  class="layui-table-link"><span class="bdc_change_red bdc-cf" style="color:#EE0000;">查封 </span></a>'
                }
                if (bdcdyZtDTO.ycf === true) {
                    value += '<a href="javascript:viewCf(&quot;' + bdcdyZtDTO.bdcdyh + '&quot;)"  class="layui-table-link"><span class="bdc-ycf" style="color:#ee717a;">预查封 </span></a>'
                }
                if (bdcdyZtDTO.dya === true || bdcdyZtDTO.zjgcdy === true) {
                    value += '<a href="javascript:viewDya(&quot;' + bdcdyZtDTO.bdcdyh + '&quot;)"  class="layui-table-link"><span class="bdc-dya" style="color:#FF00FF;">抵押 </span></a>'
                }
                if (bdcdyZtDTO.ydya === true) {
                    value += '<a href="javascript:viewYdy(&quot;' + bdcdyZtDTO.bdcdyh + '&quot;)"  class="layui-table-link"><span class="" style="color:#aacd06;">预抵押 </span></a>'
                }
                if (bdcdyZtDTO.yy === true) {
                    value += '<a href="javascript:viewYY(&quot;' + bdcdyZtDTO.bdcdyh + '&quot;)"  class="layui-table-link"><span class="bdc-yy" style="color:#ef7106;">异议 </span></a>'
                }
                if (bdcdyZtDTO.yg === true) {
                    value += '<a href="javascript:viewYg(&quot;' + bdcdyZtDTO.bdcdyh + '&quot;)"  class="layui-table-link"><span class="bdc-yg" style="color:#ffb60c;">预告 </span></a>'
                }
                if (bdcdyZtDTO.dyi === true) {
                    value += '<a href="javascript:viewDyi(&quot;' + bdcdyZtDTO.bdcdyh + '&quot;)"  class="layui-table-link"><span class="" style="color:#855e6e;">地役 </span></a>'
                }
                if (bdcdyZtDTO.sd === true) {
                    value += '<a href="javascript:viewSd(&quot;' + data.bdcqzh + '&quot;)"  class="layui-table-link"><span class="bdc-sd" style="color:#e50971;">锁定 </span></a>'
                }
                if (bdcdyZtDTO.jzq === true) {
                    value += '<a href="javascript:viewJz(&quot;' + bdcdyZtDTO.bdcdyh + '&quot;)"  class="layui-table-link"><span class="bdc-jzq" style="color:#13b1c4;">居住 </span></a>'
                }
                if (bdcdyZtDTO.lqlzzt === true) {
                    value += '<span class="bdc-lqlzzt" style="color:#13b1c4;">流转 </span>'
                }
                if (value === '') {
                    value += '<span class="" style="color:#32b032;">正常</span>';
                }
            }
            return value;
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
            sfhqhc = true;
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

            // 如果只输入权利人名称，则通过配置控制证件号是否必填
            if (!isNullOrEmpty($("#qlrmc").val()) && count - 1 <= 0) {
                if ("1" == zhcxpz.cxzjhbt && isNullOrEmpty($("#qlrzjh").val())) {
                    warnMsg(" 请输入权利人证件号");
                    return false;
                }
            }

            form.val('searchform', JSON.parse(JSON.stringify(mhlx)));

            // 获取查询参数
            var obj = getSearchData();

            if (!isNullOrEmpty(obj.bdcqzh) && qzhflag) {
                if (obj.bdcqzh.length<5){
                    warnMsg("权证号至少需要输入5位");
                    return ;
                }
            }

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
                    //添加已选条目数
                    var checkedData = layui.sessionData('checkedData');
                    var arrcheck = Object.keys(checkedData);
                    var total = arrcheck.length;
                    $('#choosecount').html("已选"+total+"条");
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                    showMoreBtn();

                    // 默认按钮隐藏
                    setBtnHidden();
                    // 控制按钮权限
                    setElementAttrByModuleAuthority(moduleCode);
                    form.render();

                    // 单独保存查询数据到数据库供本地综合查询日志页面使用
                    saveQueryLogToDataBase(obj, currentPageData, "ZHCX");
                    //保存综合查询台账页面综合查询以及打印记录操作的日志，存档到数据库
                    saveZhcxLogToDataBase(obj, currentPageData, 0, null);
                    //添加已选条目数
                    var checkedData = layui.sessionData('checkedData');
                    var arrcheck = Object.keys(checkedData);
                    var total = arrcheck.length;
                    $('#choosecount').html("已选"+total+"条");
                }
            });
        }

        //异地受理角色展示部分查询条件
        function ydslcxtj(){
            var queryParam  = {
                cxym : "bdczszm",
            };
            $.ajax({
                url: getContextPath() + "/rest/v1.0/dya/ydslcxtj",
                type: 'GET',
                data : queryParam,
                dataType: 'text',
                async: false,
                success: function (data) {
                    if(isNotBlank(data)){
                        // 需要展示的查询条件
                        var showCxtj = data.split(",");
                        // 获取所有查询条件并且遍历
                        $(".cxtj").each(function(){
                            if(!(showCxtj.indexOf($(this).attr("id")) > -1)){
                                $(this).hide();
                            }
                        });
                        // 查询条件小于等于2时需要将下面的表格往上移动
                        if(showCxtj.length <= 3){
                            $(".bdc-zhcx-container").css("padding-top","104px");
                        }
                        // 异地受理角色隐藏高级查询按钮
                        $("#moreSearch").hide();
                    }
                },
                error: function (xhr, status, error) {
                }
            });
        }

        function getSearchData(){
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
            obj.qllxpd = true;
            obj.moduleCode=moduleCode;
            return obj;
        }

        // 高级查询
        $('#moreSearch').on('click', function () {
            needsearch = false;
            sfczhc = false;
            layer.open({
                title: '高级查询',
                type: 2,
                area: ['960px', '500px'],
                content: 'moreSearch.html?moduleCode='+moduleCode,
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

                    // 传递查询参数
                    var iframe = window['layui-layer-iframe' + index];
                    iframe.setSearchData(obj);
                },
                end: function () {
                    if (needsearch && searchData) {
                        addModel();
                        sfhqhc = true;
                        // 高级查询需要清空勾选缓存
                        layui.sessionData("checkedData", null);
                        // IP地址
                        searchData.ipaddress = ipaddress;
                        // 版本标识
                        searchData.version = 'standard';

                        searchData.qllxpd = true;
                        searchData.moduleCode=moduleCode;
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
                                //添加已选条目数
                                var checkedData = layui.sessionData('checkedData');
                                var arrcheck = Object.keys(checkedData);
                                var total = arrcheck.length;
                                $('#choosecount').html("已选"+total+"条");
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
                                form.render();

                                // 单独保存查询数据到数据库供本地综合查询日志页面使用
                                saveQueryLogToDataBase(searchData, currentPageData, "ZHCX");
                                //添加已选条目数
                                var checkedData = layui.sessionData('checkedData');
                                var arrcheck = Object.keys(checkedData);
                                var total = arrcheck.length;
                                $('#choosecount').html("已选"+total+"条");
                            }
                        });
                    }
                }
            });
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
                    async: false,
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

        //查询条件：复选框事件绑定
        form.on('checkbox(qllx)', function (data) {
            search();
        });
        //查询条件：复选框事件绑定
        form.on('checkbox(qszt3)', function (data) {
            search();
        });

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

        // 设置默认按钮默认隐藏
        function setBtnHidden() {
            $("#excel").hide();
            $("#yfwfzm").hide();
            $("#qszm").hide();
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

    // 抵押查询
    window.viewDya = function (bdcdyh) {
        window.open("/realestate-inquiry-ui/standard/dyaCx/dyacx.html?moduleCode=303&bdcdyh=" + bdcdyh);
    }
    // 异议查询
    window.viewYY = function (bdcdyh) {
        window.open("/realestate-inquiry-ui/view/bdcYy/bdcYyList.html?bdcdyh=" + bdcdyh);
    }

    // 查封查询（自定义）
    window.viewCf = function (bdcdyh,cxdh) {
        if (cxdhmap && !isNullOrEmpty(cxdhmap.cf)) {
            var cxdh = cxdhmap.cf;
            var url = "/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=" + cxdh + "&cxmc=" + encodeURI("查封查询") + "&param=" + bdcdyh;
            window.open(encodeURI(url));
        } else {
            warnMsg("缺少自定义查询(查封查询)配置！");
        }
    }
    // 锁定查询
    window.viewSd = function (bdczqh) {
        if (cxdhmap && !isNullOrEmpty(cxdhmap.sd)) {
            var cxdh = cxdhmap.sd;
            var url = "/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=" + cxdh + "&cxmc=" + encodeURI("锁定查询") + "&param=" + bdczqh;
            window.open(encodeURI(url));
        } else {
            warnMsg("缺少自定义查询(锁定查询)配置！");
        }
    }
    // 预告查询
    window.viewYg = function (bdcdyh) {
        if (cxdhmap && !isNullOrEmpty(cxdhmap.yg)) {
            var cxdh = cxdhmap.yg;
            var url = "/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=" + cxdh + "&cxmc=" + encodeURI("预告查询") + "&param=" + bdcdyh;
            window.open(encodeURI(url));
        } else {
            warnMsg("缺少自定义查询(预告查询)配置！");
        }
    }
    // 预抵押查询
    window.viewYdy = function (bdcdyh) {
        if (cxdhmap && !isNullOrEmpty(cxdhmap.ydy)) {
            var cxdh = cxdhmap.ydy;
            var url = "/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=" + cxdh + "&cxmc=" + encodeURI("预抵押查询") + "&param=" + bdcdyh;
            window.open(encodeURI(url));
        } else {
            warnMsg("缺少自定义查询(预抵押查询)配置！");
        }
    }
    // 居住查询
    window.viewJz = function (bdcdyh) {
        if (cxdhmap && !isNullOrEmpty(cxdhmap.jz)) {
            var cxdh = cxdhmap.jz;
            var url = "/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=" + cxdh + "&cxmc=" + encodeURI("居住查询") + "&param=" + bdcdyh;
            window.open(encodeURI(url));
        } else {
            warnMsg("缺少自定义查询(居住查询)配置！");
        }
    }
    // 地役查询
    window.viewDyi = function (bdcdyh) {
        if (cxdhmap && !isNullOrEmpty(cxdhmap.dyi)) {
            var cxdh = cxdhmap.dyi;
            var url = "/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=" + cxdh + "&cxmc=" + encodeURI("地役查询") + "&param=" + bdcdyh;
            window.open(encodeURI(url));
        } else {
            warnMsg("缺少自定义查询(地役查询)配置！");
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
                var zdVals = eval(zdListName.fwyt);
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

    function zdBdclx(zdname, zdDm, zdListName) {
        try {
            if (zdDm) {
                var zdVals = eval(zdListName.bdclx);
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
function yzlpb(data) {
    var status;
    if (isNullOrEmpty(data.bdcdyh)){
        return true;
    }
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zszm/yzlpb",
        data: {
            bdcdyh : data.bdcdyh
        },
        async: false,
        type: 'GET',
        success: function (res) {
            status = res;
        }
    })
    return status;
}

function reloadCxtj() {
    $.ajax({
        url: "/realestate-inquiry-ui/config/zszm/getmhlx",
        data: {},
        type: 'GET',
        success: function (res) {
            if (!isNullOrEmpty(res)) {
                $.each(res,function (key,value) {
                    var id = '[name="'+key.split("mhlx")[0]+'"]';
                    var placeholder = $(id).attr("placeholder").split(")")[1];
                    if (value === 1){
                        $(id).attr("placeholder","(左匹配)"+placeholder);
                        mhlx[key] = 1;
                    }else if (value === 2) {
                        $(id).attr("placeholder","(右匹配)"+placeholder);
                        mhlx[key] = 2;
                    }else if (value === 3){
                        $(id).attr("placeholder","(模糊)"+placeholder);
                        mhlx[key] = 3;
                        if (key.startsWith('bdcqzh')){
                            qzhflag = true;
                        }
                    }
                })
            }
        }
    })
}