/**
 * 综合查询标准版页面JS处理
 */
// 打印的模板现场维护，此路径只是名义上的路径，具体打印时候会更改为读取配置的打印模板放置路径，但是模板名称要一致
// 房产证明打印模板
var fczmDymb = getIP() + "/realestate-inquiry-ui/static/printModel/fczm.fr3";
// 房产证明(历史)打印模板
var fczmlsDymb = getIP() + "/realestate-inquiry-ui/static/printModel/fczmls.fr3";
// (土地)权属证明打印模板
var tdQszmDymb = getIP() + "/realestate-inquiry-ui/static/printModel/tdqszm.fr3";
// (房屋)权属证明打印模板
var fwQszmDymb = getIP() + "/realestate-inquiry-ui/static/printModel/fwqszm.fr3";
// 打印清单打印模板
var dyqdDymb = getIP() + "/realestate-inquiry-ui/static/printModel/dyqd.fr3";
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
// 家庭成员信息参数
var jtcyData;
// 获取字典
var zdList = getMulZdList("fwyt");
var bdclxList = getMulZdList("bdclx");
var tdytList = getMulZdList("tdyt");
//获取页面版本
var version = getQueryString("version");
// 用户IP地址
var ipaddress;
// 当前登录用户中文名
var userName;
// 配置项信息
var zhcxpz;
// 查询结果总数量
var resultTotalElements = 0;

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
        //根据配置，控制是否显示档案调用（旧）按钮
        var dadyj;
        // 获取配置项
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/bzb/zhcx/pz",
            type: "GET",
            dataType: 'json',
            async: false,
            success: function (res) {
                if (res) {
                    zhcxpz = res;

                    if(res.cxmd) {
                        $.each(res.cxmd.split(","), function (index, item) {
                            $('#cxmd').append('<option value="' + item + '">' + item + '</option>');
                        });
                        form.render('select');
                    }
                    dadyj = res.dadyj;
                } else {
                    failMsg("发现页面未配置正确，请联系管理员解决！");
                }
            },
            error: function () {
                failMsg("发现页面未配置正确，请联系管理员解决！");
            }
        });

        if (dadyj == true) {
            //配置值为true时，显示档案调用（旧）按钮
            bar = '#barDemo1';
        } else {
            //没有配置、配置值为false时，不显示档案调用（旧）按钮
            bar = '#barDemo';
        }

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
                {
                    field: 'zdzhyt', width: 130, title: '土地用途',
                    templet: function (d) {
                        return zdTdytDmToMc('tdyt', d.zdzhyt, tdytList);
                    }
                },
                {field: 'tdzsyqmj', width: 130, title: '土地使用权面积', sort: false, templet: '#tdzsyqmjTpl'},
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
                        return formatXzzt(d.bdcdyZtDTO);
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
                resultTotalElements = res.totalElements;
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
                                $("#qllxSxpz").append('<input type="checkbox" name="qllxmc" title="'+ sxpz[0] + '" value="' + sxpz[1] + '"    lay-filter="qllx" lay-skin="primary" />');
                            }else if(sxpz.length == 3 && sxpz[2] == 'checked'){
                                $("#qllxSxpz").append('<input type="checkbox" name="qllxmc" title="'+ sxpz[0] + '" value="' + sxpz[1] + '"    lay-filter="qllx" lay-skin="primary" checked/>');
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

        //监听工具条
        table.on('tool(pageTable)', function (obj) {
            var data = obj.data;
            if (isNullOrEmpty(data.bdcdyh)) {
                warnMsg(" 没有不动产单元信息，无法查看！");
                return;
            }

            if (obj.event === 'djls') {
                var xmid = obj.data.xmid;
                window.open("/realestate-inquiry-ui/view/lsgx/lsgx.html?bdcdyh=" + data.bdcdyh + '&xmid=' + xmid);
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
                //【档案调用】按钮，调整为打开档案系统的页面地址，打开新的标签页。
                openBdcDaxt(data);
                saveDetailLog("ZHCXDADY", "综合查询-档案调用", data);
            } else if (obj.event === 'dadyj') {
                //在此处增加xmly项目来源的判断，
                //如果xmly=2(存量过渡数据)，仍旧调用openBdcDjDa(data)，展示过渡档案；如果xmly=1(不动产登记数据)，展示登记档案；
                var url = "";
                if (data.xmly == 1) {
                    url = "/realestate-portal-ui/view/popup-img.html?processinsid=" + data.gzlslid;
                    var width = $(window).width() + "px";
                    var height = $(window).height() + "px";
                    var index = layer.open({
                        title: "附件信息",
                        type: 2,
                        content: url,
                        area: [width, height],
                        maxmin: true
                    });
                    layer.full(index);
                } else {
                    openBdcDjDa(data);
                }
                saveDetailLog("ZHCXDADY", "综合查询-档案调用", data);
            } else if (obj.event === 'txdw') {
                window.open('/building-ui/omp/redirect/' + data.bdcdyh + '/' + data.bdclx);
                saveDetailLog("ZHCXTXDW", "综合查询-图形定位", data);
            } else if(obj.event === 'djdcb'){
                window.open('/building-ui/djdcb/initview?bdcdyh=' + data.bdcdyh);
                saveDetailLog("ZHCXDJDCB", "综合查询-地籍调查表", data);
            }else if(obj.event === 'hsswdw'){
                window.open('http://10.9.211.101:6060/bdcdyh/index.html#/home?bdcdyh=' + data.bdcdyh);
                saveDetailLog("ZHCXHSSWDW", "综合查询-户室三维不动产定位", data);
            }else if(obj.event === 'zdswdw'){
                window.open('http://10.9.211.101:6060/zd/index.html#/index?djh=' + data.bdcdyh.substring(0,19));
                saveDetailLog("ZHCXZDSWDW", "综合查询-宗地三维不动产定位", data);
            }else if(obj.event === 'zdthstdy'){
                zdthstdy(data);
                saveDetailLog("ZHCXZDTHSTDY", "综合查询-宗地图户室图打印", data);
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
        var dylxArr = ["fwqszm", "tdqszm","ywtdxxzm","yfwfzmls","yfwfzm","lqqszm"];
        var sessionKey = "bdcZszm";
        setDypzSession(dylxArr, sessionKey);

        // 监听表格操作栏按钮
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var searchObjData = getSearchData();
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0]);
                    break;
                case 'yfwfzm':
                    yfwfzm(checkStatus.data, obj.config.cols[0], "yfwfzm");//checkStatus选中行，obj.config.cols[0]表格格式配置
                    break;
                case 'yfwfzmls':
                    yfwfzm(checkStatus.data, obj.config.cols[0], "yfwfzmls");
                    break;
                case 'qszm':
                    qszm(checkStatus.data, obj.config.cols[0]);
                    break;
                case 'dyqd':
                    dyqd(checkStatus.data, obj.config.cols[0]);
                    break;
                case 'ywtdxxzm':
                    ywtdxxzm(checkStatus.data, obj.config.cols[0], searchObjData,jtcyData);
                    break;
            }
        });

        // 导出Excel
        // 通过隐藏form提交方式，避免ajax方式不支持下载文件
        function exportExcel(d, cols) {
            if(!isNullOrEmpty(d)) {
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

                    data[i].xh = i + 1;
                    data[i].jzmj 	= getFjxxVal(xmid, "jzmj2");
                    data[i].tdsyqmj = getFjxxVal(xmid, "tdsyqmj");
                    data[i].tdzsyqmj = getFjxxVal(xmid, "tdzsyqmj");
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


        /**
         * 打印清单功能
         */
        function dyqd(d, cols) {
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
                data.push(value);
            })
            for (var i = 0; i < data.length; i++) {
                data[i].xh = i + 1;
                data[i].djsj 	= formatDate(data[i].djsj);
                data[i].ghyt 	= zdGhytToMc('fwyt', data[i].ghyt, zdList);
                data[i].xzzt 	= formatXzztOfData(data[i].bdcdyZtDTO);
                data[i].tdyt    = zdTdytDmToMc('tdyt', data[i].zdzhyt, tdytList);
                data[i].tdzsyqmj = getFjxxVal(data[i].xmid, "tdzsyqmj");
                data[i].jzmj = getFjxxVal(data[i].xmid, "jzmj2");
            }
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhcx/dyqd/key",
                type: "POST",
                data: JSON.stringify(data),
                contentType: 'application/json',
                dataType: 'text',
                success: function (key) {
                    if (key) {
                        var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/dyqd/" + key + "/xml";
                        print(dyqdDymb, dataUrl, false);
                        var xmlStr = getXmlStr(dataUrl); //获得打印内容 xml
                        var searchObjData = getSearchData();
                        saveZhcxLogToDataBase(searchObjData, xmlStr, 1, "dyqd");
                        savePrintInfo(dyqdDymb, dataUrl, {'zmbh': key, "printType": "打印清单","zl":zl,"qlr": qlrmc}, xmlStr);
                    } else {
                        removeModal();
                        failMsg("打印清单失败，请重试！");
                    }
                },
                error: function () {
                    removeModal();
                    failMsg("打印清单失败，请重试！");
                },
                complete: function () {
                    removeModal();
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
        function yfwfzm(d, cols, type) {
            var qlrmc = $("#qlrmc").val();
            var qlrzjh = $("#qlrzjh").val();
            var zl;
            if (d.length == 0) {
                zl = $("#zl").val();
            }else{
                zl = d[0].zl;
            }

            if (isNullOrEmpty(qlrmc)) {
                warnMsg("请输入户主权利人名称！");
                return;
            }

            // 添加家庭成员权利人
            // 拼接家庭成员权利人名称和证件号信息
            if(jtcyData && jtcyData.length > 0) {
                for(var i = 0; i < jtcyData.length; i++) {
                    if (!isNullOrEmpty(jtcyData[i].jtcymc)) {
                        qlrmc = isNullOrEmpty(qlrmc) ? jtcyData[i].jtcymc : qlrmc + "," + jtcyData[i].jtcymc;
                    }
                    if (!isNullOrEmpty(jtcyData[i].jtcycym)) {
                        qlrmc = isNullOrEmpty(qlrmc) ? jtcyData[i].jtcycym : qlrmc + "," + jtcyData[i].jtcycym;
                        // 为了保证验证权利人名称和证号数量一致，这边曾用名存在时证号加一个
                        if (!isNullOrEmpty(jtcyData[i].jtcyzjh)) {
                            qlrzjh = isNullOrEmpty(qlrzjh) ? jtcyData[i].jtcyzjh : qlrzjh + "," + jtcyData[i].jtcyzjh;
                        }
                    }
                    if (!isNullOrEmpty(jtcyData[i].jtcyzjh)) {
                        qlrzjh = isNullOrEmpty(qlrzjh) ? jtcyData[i].jtcyzjh : qlrzjh + "," + jtcyData[i].jtcyzjh;
                    }
                }
            }

            //拼接曾用名
            if(searchData &&!isNullOrEmpty(searchData.qlrcym)) {
                qlrmc = isNullOrEmpty(qlrmc) ? searchData.qlrcym : qlrmc + "," + searchData.qlrcym;
            }
            if(searchData &&!isNullOrEmpty(searchData.qlrcymzjh)) {
                qlrzjh = isNullOrEmpty(qlrzjh) ? searchData.qlrcymzjh : qlrzjh + "," + searchData.qlrcymzjh;
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

            var qlrxxList = getQlrxxList(qlrmc, qlrzjh);
            if (!qlrxxList || qlrxxList.length == 0) {
                warnMsg(" 请在查询条件中输入权利人名称、证件号！");
                return;
            }

            // 先获取房产证明对应的不动产单元号等信息进行规则验证
            // 同时验证户主和家庭成员房产信息
            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhcx/yfwfzm/zfxx", //查询指定权利人对应的房产信息，返回List<BdcZfxxDTO>
                type: "POST",
                data: JSON.stringify(qlrxxList), //List<BdcQlrQO> qlrxx  权利人信息
                contentType: 'application/json',
                dataType: 'json',
                success: function (res) {
                    if (res) {
                        //checkApplyRegistration 规则验证
                        checkApplyRegistration(res, ZHCX_YFWFZM_ZHGZ, 'ZHCX').fail(function () {
                            return;
                        }).done(function () {
                            // 正式打印时候分别传参户主和家庭成员
                            printFczm($("#qlrmc").val(), $("#qlrzjh").val(), type, zl); //正式打印操作！记录日志到数据库中；
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
        function printFczm(qlrmc, qlrzjh, type, zl) {
            var qlrxxList = getQlrxxList(qlrmc, qlrzjh);
            var qlrcym ="";
            var qlrcymzjh="";
            if(searchData) {
                qlrcym =searchData.qlrcym;
                qlrcymzjh =searchData.qlrcymzjh;
            }
            var qlrcymQOList=getQlrxxList(qlrcym, qlrcymzjh);
            var cxmd = $("#cxmd").val();

            // 打印时候家庭成员需要将曾用名拼接到名称后面展示，为了避免影响原有jtcyData变量查询新建对象jtcyList
            var jtcyList = [];
            if(jtcyData && jtcyData.length) {
                for(var i = 0; i < jtcyData.length; i++) {
                    var jtcyObj = {};
                    jtcyObj.jtcymc = jtcyData[i].jtcymc;
                    if (!isNullOrEmpty(jtcyData[i].jtcycym)) {
                        jtcyObj.jtcymc = isNullOrEmpty(jtcyObj.jtcymc) ? jtcyData[i].jtcycym : jtcyObj.jtcymc + "（" + jtcyData[i].jtcycym + "）";
                    }
                    jtcyObj.jtcyzjh = jtcyData[i].jtcyzjh;
                    jtcyObj.yhzgx = jtcyData[i].yhzgx;
                    jtcyList.push(jtcyObj);
                }
            }
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/print/zfxx/qlrxx",//将住房查询证明请求的参数--权利人信息缓存到Redis（因为数据可能很多，直接GET传fr3可能出现问题）
                type: "POST",
                data: JSON.stringify({"qlrQOList":qlrxxList,"qlrcymQOList":qlrcymQOList, "jtcyDOList":jtcyList, "cxmd":cxmd, "useralias":userName, "version":"yancheng", "printType": type}),//住房信息打印查询参数
                contentType: 'application/json',
                dataType: "text",
                success: function (key) {
                    if (key) {
                        var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/zszm/print/dacx/" + key + "/" + type + "/xml";
                        var fczmmc = "yfwfzmls" == type ? fczmlsDymb : fczmDymb;
                        // print(fczmmc, dataUrl, false);
                        var fileName = "yfwfzmls" == type ? '有无土地信息证明历史' : '有无土地信息证明';
                        printForDacx(type, null, fileName, key, dataUrl, false, fczmmc, false);
                        // var pdfUrl = getContextPath() + '/rest/v1.0/zszm/print/dacx/pdf?dylx=' + type + '&fileName=' + fileName + '&modelWordUrl=' + fczmmc + '&xmlKey=' + key + '&dacxFlag=true';
                        // window.open(getContextPath() + '/static/lib/pdf/web/viewer.html?file=' + encodeURIComponent(pdfUrl), "PDF");
                        var xmlStr = getXmlStr(dataUrl); //获得打印内容 xml
                        var searchObjData = getSearchData();
                        saveZhcxLogToDataBase(searchObjData, xmlStr, 1, type);
                        savePrintInfo(fczmDymb, dataUrl, {'zmbh': key, "printType": "房产证明","zl":zl,"qlr": qlrmc }, xmlStr);
                    } else {
                        failMsg("打印失败，请重试！");
                    }
                },
                error: function () {
                    failMsg("打印失败，请重试！");
                }
            });
        }

        function getQlrxxList(qlrmc, qlrzjh) {
            var qlrxxList = new Array();
            var qlrmcArray =[];
            var qlrzjhArray =[];
            if(!isNullOrEmpty(qlrmc)) {
                qlrmcArray = qlrmc.split(",");
            }
            if(!isNullOrEmpty(qlrzjh)) {
                qlrzjhArray = qlrzjh.split(",");
            }
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
            return qlrxxList;
        }

        // 打印权属证明 （土地和房屋合成一个按钮）
        function qszm(d, cols) {
            var zl;
            var qlrmc;
            if (d.length == 0) {
                zl = $("#zl").val();
                qlrmc = $("#qlrmc").val();
            }else{
                zl = d[0].zl;
                qlrmc = d[0].qlrmc
            }
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                // 未选择记录则打印所有
                if(0 == resultTotalElements) {
                    warnMsg("无查询结果数据，无法打印！");
                    return;
                } else {
                    if(resultTotalElements >= 1000) {
                        warnMsg("当前打印记录过多，请分批打印！");
                        return;
                    }

                    // 重新执行查询，获取所有数据打印，判断查询条件是否存在，避免被清空
                    var count = 0;
                    $(".required").each(function (i) {
                        if (!isNullOrEmpty($(this).val())) {
                            count += 1;
                        }
                    });
                    if (0 == count && (!jtcyData || jtcyData.length == 0)) {
                        warnMsg("请勾选数据或输入查询条件后执行打印！");
                        return;
                    }

                    checkedData = getQszmCheckedData();
                }
            }

            // 判断是否包含临时状态
            var isLszt = false;
            $.each(checkedData, function (key, value) {
                if (0 == value.qszt) {
                    isLszt = true;
                }
            });

            if (isLszt) {
                warnMsg("选择记录存在临时状态，无法出具证明！");
                return;
            }

            // 判断选择的不动产类型是否一致
            var bdclx = 2;
            var isTdfw = false, isLq = false;
            $.each(checkedData, function (key, value) {
                bdclx = value.bdclx;

                if (value.bdclx === 1 || value.bdclx === 2) {
                    // 土地和房屋的可以一起打印
                    isTdfw = true;
                }

                if(value.bdclx === 3) {
                    // 林权
                    isLq = true;
                }
            });

            if(isTdfw && isLq) {
                warnMsg("土地房屋和林权不可同时出具证明！");
                return;
            }

            if(1 === bdclx) {
                // 土地房屋统一用房屋的 fwqszm
                bdclx = 2;
            }

            // 进行规则验证
            checkApplyRegistration(checkedData, ZHCX_QSZM_ZHGZ, 'QSZM').fail(function () {
                return;
            }).done(function () {
                // 盐城土地房屋合并成一个，默认用房屋的打印模板名称、打印数据源
                qszmdy(checkedData, bdclx, zl, qlrmc);
            });
        }

        // 查询出所有数据进行权属证明打印（为了避免数据量过大，打印时候默认最多支持1000条，这里查询默认5千条）
        function getQszmCheckedData() {
            var checkedData = {};
            var params = getSearchData();
            params.page = 1;
            params.size = 5000;
            params.loadTotal = false;

            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zszm?" + parseJsonToUrlParams(params),
                type: "GET",
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data && data.content && data.content.length > 0) {
                        $.each(data.content, function (index, item) {
                            if(!isNullOrEmpty(item.xmid)) {
                                var obj = new Object();
                                obj.qszt = item.qszt;
                                obj.bdcdyh = item.bdcdyh;
                                obj.gzlslid = item.gzlslid;
                                obj.bdcqzh = item.bdcqzh;
                                obj.xmid = item.xmid;
                                obj.bdclx = item.bdclx;
                                obj.tdzsyqmj = item.tdzsyqmj;
                                checkedData[item.xmid] = obj;
                            }
                        });
                        removeModal();
                    } else {
                        removeModal();
                        warnMsg("请勾选数据或输入查询条件后执行打印！");
                        return;
                    }
                }
            });
            return checkedData;
        }

        /**
         * 执行打印权属证明
         * @param checkedData 要打印的数据
         * @param bdclx 不动产类型
         */
        function qszmdy(checkedData, bdclx, zl, qlrmc) {
            // 格式示例：[{"bdcdyh":"1","qszt":"1","gzlslid":"1"},{"bdcdyh":"1","qszt":"1","gzlslid":"1"}]
            var paramList = new Array();
            $.each(checkedData, function (key, value) {
                if (value.qszt == 1) {//只查现势的信息
                    var param = new Object();
                    param.qszt = 1;
                    param.bdcdyh = value.bdcdyh;
                    param.gzlslid = value.gzlslid;
                    param.bdcqzh = value.bdcqzh;
                    param.xmid = value.xmid;
                    param.bdclx = value.bdclx;
                    paramList.push(param);
                }
            });
            if (paramList.length == 0) {
                warnMsg("请勾选现势数据或输入查询条件后执行打印！");
                return;
            }
            // 缓存要打印的权属证明参数信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhcx/bzb/qszm",
                type: "POST",
                data: JSON.stringify(paramList),
                contentType: 'application/json',
                dataType: 'json',
                success: function (data) {
                    if (data && data.redisKey) {
                        // 根据土地房屋、林权类型选择对应的打印模板
                        var mburl = bdclx === 3 ? lqQszmDymb : fwQszmDymb;
                        var dylx = bdclx === 3 ? "lqqszm" : "fwqszm";

                        // 执行打印
                        var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/zszm/print/dacx/" + data.redisKey + "/" + dylx + "/xml?bdclx="+bdclx;
                        printForDacx(dylx, bdclx, "权属证明", data.redisKey, dataUrl, false, mburl, data.canEdit);
                        var xmlStr = getXmlStr(dataUrl); //获得打印内容 xml
                        var searchObjData = getSearchData();
                        saveZhcxLogToDataBase(searchObjData, xmlStr, 1, "qszm");
                        // 保存日志
                        savePrintInfo(mburl, dataUrl, {'zmbh': data.cxh, "printType": "权属证明","zl":zl, "qlr": qlrmc}, xmlStr);
                    } else {
                        failMsg("房产档案打印出错，请重试！");
                    }
                },
                error: function (xhr, status, error) {
                    var msg = "房产档案打印出错，请重试！";
                    if(xhr && !isNullOrEmpty(xhr.responseText)) {
                        var responseText = JSON.parse(xhr.responseText);
                        var detail = responseText.msg;
                        if(!isNullOrEmpty(detail)) {
                            msg = detail;
                        }
                    }
                    failMsg(msg);
                }
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

            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhcx/dyzm/gzyz/" + zhbs,
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({
                    bdcGzYzsjDTOList: bdcGzYzsjDTOList
                }),
                success: function (data) {
                    removeModal();
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
                    removeModal();
                    failMsg("系统验证发生异常，请重试或联系管理员！");
                }
            });
            return deferred;
        }

        // 点击查询
        var clicktag = 0;
        $('#search').on('click', function () {
            if (clicktag == 0) {
                clicktag = 1;
                setTimeout(function() {
                    clicktag = 0;
                }, 800);
            } else {
                layer.alert("短时间内重复点击次数较多，请稍候查询！", {title: '提示'});
            }
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
            $("#zsyzh").val(null);

            $(".mhlx3").each(function (index, item) {
                $("#" + item.id).val(3);
            });
            $(".mhlx0").each(function (index, item) {
                $("#" + item.id).val(0);
            });

            // 清空家庭成员查询条件
            jtcyData = null;
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
            if (0 == count && (!jtcyData || jtcyData.length == 0)) {
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

            // 设置默认的字段模糊类型
            var mhlx = {
                "bdcdyhmhlx": 0, "bdcqzhmhlx": 3, "qlrmcmhlx": 0, "qlrzjhmhlx": 0, "ywrmcmhlx": 0,
                "ywrzjhmhlx": 0, "zlmhlx": 3, "ycqzhmhlx": 0, "zhmhlx": 0, "fjhmhlx": 0, "slbhmhlx": 0,
                "fwbhmhlx": 0, "zhlshmhlx": 0, "ybdcdyhmhlx": 0, "yxtcqzhmhlx": 0, "zsyzhmhlx": 0, "cfwhmhlx": 3
            };
            form.val('searchform', JSON.parse(JSON.stringify(mhlx)));

            addModel();
            var searchObj = getSearchData();
            table.reload("pageTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/zszm"
                , where: searchObj
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

                    // 默认按钮隐藏
                    setBtnHidden();
                    // 控制按钮权限
                    setElementAttrByModuleAuthority(moduleCode);
                    form.render();

                    // 单独保存查询数据到数据库供本地综合查询日志页面使用
                    saveQueryLogToDataBase(searchObj, currentPageData, "ZHCX");
                    //保存综合查询台账页面综合查询以及打印记录操作的日志，存档到数据库
                    saveZhcxLogToDataBase(searchObj, currentPageData, 0, null);
                }
            });
        }

        // 获取查询条件内容
        function getSearchData() {
            // 获取查询参数
            var obj = {};
            // 查询总数量
            obj.loadTotal = true;
            // 查询条件
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
            // 拼接家庭成员权利人名称和证件号信息
            if(jtcyData && jtcyData.length > 0) {
                for (var i=0;i<jtcyData.length;i++) {
                    var jtcy = jtcyData[i];
                    if(!isNullOrEmpty(jtcy.jtcymc)) {
                        obj.qlrmc = isNullOrEmpty(obj.qlrmc) ? jtcy.jtcymc : obj.qlrmc + "," + jtcy.jtcymc;
                    }
                    if(!isNullOrEmpty(jtcy.jtcycym)) {
                        obj.qlrmc = isNullOrEmpty(obj.qlrmc) ? jtcy.jtcycym : obj.qlrmc + "," + jtcy.jtcycym;
                    }
                    if(!isNullOrEmpty(jtcy.jtcyzjh)) {
                        obj.qlrzjh = isNullOrEmpty(obj.qlrzjh) ? jtcy.jtcyzjh : obj.qlrzjh + "," + jtcy.jtcyzjh;
                    }
                };
            }

            return obj;
        }

        // 高级查询
        $('#moreSearch').on('click', function () {
            needsearch = false;

            layer.open({
                title: '高级查询',
                type: 2,
                area: ['960px', '580px'],
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
                    var cxmd = $("#cxmd").val();
                    iframe.setSearchData(obj, jtcyData, cxmd, $("#dybdclx").val());
                },
                end: function () {
                    if (needsearch && searchData) {
                        addModel();
                        // 高级查询需要清空勾选缓存
                        layui.sessionData("checkedData", null);
                        // 查询总数量
                        searchData.loadTotal = true;
                        // IP地址
                        searchData.ipaddress = ipaddress;
                        // 版本标识
                        searchData.version = 'yancheng';
                        // 拼接家庭成员权利人名称和证件号信息
                        if(jtcyData && jtcyData.length > 0) {
                            for(var i = 0; i < jtcyData.length; i++) {
                                if(!isNullOrEmpty(jtcyData[i].jtcymc)) {
                                    searchData.qlrmc = isNullOrEmpty(searchData.qlrmc) ? jtcyData[i].jtcymc : searchData.qlrmc + "," + jtcyData[i].jtcymc;
                                }
                                if(!isNullOrEmpty(jtcyData[i].jtcycym)) {
                                    searchData.qlrmc = isNullOrEmpty(searchData.qlrmc) ? jtcyData[i].jtcycym : searchData.qlrmc + "," + jtcyData[i].jtcycym;
                                }
                                if(!isNullOrEmpty(jtcyData[i].jtcyzjh)) {
                                    searchData.qlrzjh = isNullOrEmpty(searchData.qlrzjh) ? jtcyData[i].jtcyzjh : searchData.qlrzjh + "," + jtcyData[i].jtcyzjh;
                                }
                            }
                        }
                        //曾用名
                        if(!isNullOrEmpty(searchData.qlrcym)) {
                            searchData.qlrmc = isNullOrEmpty(searchData.qlrmc) ? searchData.qlrcym : searchData.qlrmc + "," + searchData.qlrcym;
                        }
                        if(!isNullOrEmpty(searchData.qlrcymzjh)) {
                            searchData.qlrzjh = isNullOrEmpty(searchData.qlrzjh) ? searchData.qlrcymzjh : searchData.qlrzjh + "," + searchData.qlrcymzjh;
                        }

                        if(!isNullOrEmpty(searchData.ywrcym)) {
                            searchData.ywrmc = isNullOrEmpty(searchData.ywrmc) ? searchData.ywrcym : searchData.ywrmc + "," + searchData.ywrcym;
                        }
                        if(!isNullOrEmpty(searchData.qlrcymzjh)) {
                            searchData.ywrzjh = isNullOrEmpty(searchData.ywrzjh) ? searchData.ywrcymzjh : searchData.ywrzjh + "," + searchData.ywrcymzjh;
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
                                form.render();

                                saveZhcxLogToDataBase(searchData, currentPageData, 0, null)
                                // 单独保存查询数据到数据库供本地综合查询日志页面使用
                                saveQueryLogToDataBase(searchData, currentPageData, "ZHCX");
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
                    success: function (data) {
                        if (data && data.length > 0) {
                            $.each(data, function (index, item) {
                                var zh = isNullOrEmpty(item.zh) ? "" : item.zh;
                                var fjh = isNullOrEmpty(item.fjh) ? "" : item.fjh;
                                var jzmj = isNullOrEmpty(item.jzmj) ? "" : item.jzmj;
                                item.jzmj2 = isNullOrEmpty(item.jzmj2) ? "/" : item.jzmj;
                                var tdsyqmj = isNullOrEmpty(item.tdsyqmj) ? "" : item.tdsyqmj;
                                var tdzsyqmj = isNullOrEmpty(item.tdzsyqmj) ? "" : item.tdzsyqmj;
                                $("." + item.xmid + "_zh").html('<p class="bdc-table-state-th">' + zh + '</p>');
                                $("." + item.xmid + "_fjh").html('<p class="bdc-table-state-th">' + fjh + '</p>');
                                $("." + item.xmid + "_fwmj").html('<p class="bdc-table-state-th">' + jzmj + '</p>');
                                $("." + item.xmid + "_tdqlmj").html('<p class="bdc-table-state-th">' + tdsyqmj + '</p>');
                                $("." + item.xmid + "_tdzsyqmj").html('<p class="bdc-table-state-th">' + tdzsyqmj + '</p>');
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
                $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 50});
            } else {
                $btnMore.css({top: tableTop - scrollTop - $btnMore.height(), left: tableLeft - 50});
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
            $("#yfwfzmls").hide();
            $("#qszm").hide();
            $("#dyqd").hide();
            $("#ywtdxxzm").hide();
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

    // 设置查询目的
    window.setCxmd = function (data) {
        if(!isNullOrEmpty(data)) {
            $("#cxmd").val(data);
        }
    }

    // 设置查询抵押不动产类型
    window.setDybdclx = function (data) {
        if(!isNullOrEmpty(data)) {
            $("#dybdclx").val(data);
        }
    }

    // 添加家庭成员页面传值
    window.setJtcyData = function (data) {
        jtcyData = data;
    }

    //身份证读卡器设置
    window.onReadIdCard = function (qlrlb) {
        try {
            var cardInfo = readIdCard();
            if (cardInfo.ReadCard()) {
                var name = cardInfo.Name;
                var number = cardInfo.ID;

                if (1 == qlrlb) {
                    $('input[name="qlrmc"]').val(trimStr(name));
                    $('input[name="qlrzjh"]').val(trimStr(number));
                } else {
                    $('input[name="ywrmc"]').val(trimStr(name));
                    $('input[name="ywrzjh"]').val(trimStr(number));
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
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @param zdname 字典名 tdyt
     * @param zdDm 字典代码 1
     * @param zdListName JS中保存字典数据的变量名 默认为zdList*/
    function zdTdytDmToMc(zdname, zdDm, zdListName) {
        try {
            if (zdDm) {
                /* if (!zdListName) {
                     zdListName = "zdList"
                 }*/
                var zdVals = eval(zdListName.tdyt);
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
/**
 * 【档案调用】按钮，调整为打开档案系统的页面地址，打开新的标签页。
 * @param data
 */
function openBdcDaxt(data){
    var xmid = data.xmid;

    if(xmid){
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/url/common/bdcdaxturl?xmid="+xmid,
            type: 'get',
            success: function (data) {
                console.log("不动产档案系统url:"+data);
                if(data){
                    window.open(data);
                } else {
                    warnMsg("未挂接到档案系统，无法查看档案附件！");
                }
            },
            error: function (err) {
                layer.closeAll();
            }
        });
    }else{
        warnMsg("请求失败，该数据xmid为空！");
    }
}