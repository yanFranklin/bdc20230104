/**
 * 海门打印查询页面JS处理（基本功能与综合查询相同，比综合查询拥有更少的查询条件）
 */
// 打印的模板现场维护，此路径只是名义上的路径，具体打印时候会更改为读取配置的打印模板放置路径，但是模板名称要一致
// 有房无房证明打印前缀
var yfwfzmmb = getIP() + "/realestate-inquiry-ui/static/printModel/";
// 权属证明模板
var zfxxDaUrl = getIP() + "/realestate-inquiry-ui/static/printModel/bdcZfxxDa.fr3";
// 抵押证明模板
var dyzmFr3Url = getIP() + "/realestate-inquiry-ui/static/printModel/bdcDyzm.fr3";
var reverseList = ['zl'];
var searchData, needsearch;
var zdList = getMulZdList("fwyt");
// 申请书ID
var sqsid;
// 用户IP地址
var ipaddress;
// 获取页面表单标识，用于权限控制
var moduleCode = $.getUrlParam('moduleCode');
// 当前页数据
var currentPageData = new Array();
// 有房无房证明
var T_YFWFZM = "yfwfzm";
// 迁移户口证明
var T_QHKZM = "qhkzm";

layui.use(['form', 'jquery', 'laydate', 'element', 'table', 'laytpl'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laytpl = layui.laytpl,
        laydate = layui.laydate;

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

    $(function () {

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
                {field: 'bdcqzh', width: 280, title: '不动产权证号（明）', sort: true,
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
                {field: 'djsj', width: 180, title: '登簿时间', hide: true, sort: true,
                    templet: function (d) {
                        return formatDate(d.djsj);
                    }
                },
                {field: 'djyy', width: 200, title: '登记原因', sort: true,},
                {field: 'ghyt', title: '规划用途', minWidth: 150,
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
                {field: 'qszt', width: 100, title: '权属状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatQszt(d.qszt);
                    }
                },
                {field: 'bdcdyZtDTO', width: 100, title: '限制状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatXzzt(d.bdcdyZtDTO);
                    }
                },
                {field: 'ajzt', width: 100, title: '办理状态', fixed: 'right', sort: true,hide: true,
                    templet: function (d) {
                        return formatAjzt(d.ajzt);
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
            if(isNullOrEmpty(ipaddress)) {
                getUserIP(function (ip) {
                    if (ip != null && ip != undefined) {
                        ipaddress = ip;
                    }
                });
            }
        } catch(err){
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

        // 获取配置项
        var zhcxpz;
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zhcx/pz",
            type: "GET",
            dataType: 'json',
            async: false,
            success: function (res) {
                if(res){
                    zhcxpz = res;
                }else{
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
            }
            else if (obj.event === 'lpb') {
                if(data.bdclx == 2){
                    // 只有房屋类型允许查看
                    window.open("/building-ui/lpb/view?code=analysis&bdcdyh=" + data.bdcdyh);
                    saveDetailLog("ZHCXLPB", "综合查询-查看楼盘表", data);
                } else {
                    warnMsg("当前不动产单元无楼盘表！");
                }
            }
            else if (obj.event === 'djb') {
                window.open("/realestate-register-ui/view/djbxx/bdcDjb.html?param=" + data.bdcdyh);
                saveDetailLog("ZHCXDJB", "综合查询-查看登记簿", data);
            }
            else if (obj.event === 'dady') {
                dady(data);
            }
            else if(obj.event === 'djdcb'){
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
            }
        });

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
                        var width = parseInt(cols[index].width / 100 * 15);
                        if(width <= 20){
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
                var jzmj = $("."+xmid+"_fwmj").text();
                data[i].jzmj = jzmj;
                var tdsyqmj = $("."+xmid+"_tdqlmj").text();
                data[i].tdsyqmj = tdsyqmj;
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
        }

        // 打印有房无房证明
        function yfwfzm(d, cols, zmtype) {
            var qlrmc = $("#qlrmc").val();
            var qlrzjh = $("#qlrzjh").val();
            var qlrmcArray = qlrmc.split(",");
            var qlrzjhArray = qlrzjh.split(",");
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
                        if(qlrzjhArray.length >0){
                            qlrzjhTemp = qlrzjhArray[i];
                        }
                        var qlrxx = {"qlrmc": qlrmcArray[i], "zjh": qlrzjhTemp};
                        qlrxxList.push(qlrxx);

                        if(!isNullOrEmpty(qlrzjhTemp)){
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
                    if(res && "Y" == res) {
                        warnMsg("请拆分权利人并完善证件号后再试！");
                    } else {
                        getYfwfxx(qlrxxList, qlrmc, qlrzjh, zmtype);
                    }
                },
                error: function () {
                    failMsg("打印发生错误，请重试！");
                }
            });
        }

        // 判断是否有房无房
        function getYfwfxx(qlrxxList, qlrmc, qlrzjh, zmtype) {
            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/yfwfzm/zfxx",
                type: "POST",
                data: JSON.stringify(qlrxxList),
                contentType: 'application/json',
                dataType: 'text',
                success: function (res) {
                    if(res) {
                        // 根据有房无房调用不同的打印模板
                        var fr3;

                        if(T_QHKZM == zmtype) {
                            // （海门）迁户口证明
                            if("YF" == res){
                                fr3 = yfwfzmmb + zhcxpz.zhcxzmmbQhkzmYfzm;
                            } else {
                                fr3 = yfwfzmmb + zhcxpz.zhcxzmmbQhkzmWfzm;
                            }
                        } else {
                            // （南通、海门）有房无房证明
                            if("YF" == res){
                                fr3 = yfwfzmmb + zhcxpz.zhcxzmmbYfzm;
                            } else {
                                fr3 = yfwfzmmb + zhcxpz.zhcxzmmbWfzm;
                            }
                        }

                        var printType = T_QHKZM == zmtype ? "迁户口证明" : "有房无房证明";
                        printYfwfzm(qlrmc, qlrzjh, fr3, printType, zmtype);
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
        function printYfwfzm(qlrmc, qlrzjh, fczmUrl, printType, zmtype) {
            var date=getDate();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/yfwfzm",
                type: "POST",
                data: JSON.stringify({"cxsqr": qlrmc, "sfzh": qlrzjh, "type": zmtype,"cxsd":date}),
                contentType: 'application/json',
                dataType: 'text',
                success: function (key) {
                    removeModal();
                    if(key) {
                        var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/nantong/yfwfzm/" + key + "/xml";
                        print(fczmUrl, dataUrl, false);

                        savePrintInfo(fczmUrl, dataUrl, {'zmbh': key, "printType": printType});
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
            // 需求26395 沟通后确定改为：允许名称打印但是长度要4字以上
            if(isNullOrEmpty(qlrmc) || $.trim(qlrmc).length < 4) {
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
                    if(res) {
                        // 根据有房无房调用不同的打印模板
                        var fr3;
                        if("YF" == res){
                            fr3 = yfwfzmmb + zhcxpz.zhcxzmmbYfzmqy;
                        } else {
                            fr3 = yfwfzmmb + zhcxpz.zhcxzmmbWfzmqy;
                        }

                        printYfwfzm(qlrmc, "", fr3, "有房无房证明（企业）");
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
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要打印的记录！");
                return;
            }

            // 判断是否包含临时状态
            var isLszt = false;
            $.each(checkedData, function (key, value) {
                if(0 == value.qszt){
                    isLszt = true;
                }
            });
            if(isLszt == true){
                warnMsg("该权证为临时状态，无法出具相关证明！");
                return;
            }

            //[{"bdcdyh":"1","qszt":"1","gzlslid":"1"},{"bdcdyh":"1","qszt":"1","gzlslid":"1"}]
            var qszmDyParamList = new Array();
            $.each(checkedData, function (key, value) {
                var singleParam = new Object();
                if(value.qszt != 2){//非历史状态，一律只查现势的信息
                    singleParam.qszt = 1;
                }else{
                    singleParam.qszt = value.qszt;
                }
                singleParam.bdcdyh = value.bdcdyh;
                singleParam.gzlslid = value.gzlslid;
                singleParam.bdcqzh = value.bdcqzh;
                singleParam.username = userName;
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

                        savePrintInfo(zfxxDaUrl, dataUrl, {'zmbh': data.cxh, "printType": "权属证明"});
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
        function dyzm(d, cols){
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要出具证明的数据行！");
                return;
            }

            // 判断是否包含临时状态
            var isLszt = false;
            $.each(checkedData, function (key, value) {
                if(0 == value.qszt){
                    isLszt = true;
                }
            });
            if(isLszt == true){
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

                        savePrintInfo(dyzmFr3Url, dataUrl, {'zmbh': data.cxh, "printType": "抵押证明"});
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
            if(!isNullOrEmpty(data.bdcqzh)){
                return '<a href="javascript:viewBdcZszm(&quot;' + data.gzlslid + '&quot;)" class="layui-table-link">' + data.bdcqzh + '</a>';
            }else{
                return "";
            }
        }

        // 查询处理逻辑
        function search() {
            // 每次查询清除下导出缓存数据
            layui.sessionData("checkedData", null);

            var unSearch = isNullOrEmpty($("#bdcqzh").val()) && (isNullOrEmpty($("#qlrmc").val()) || isNullOrEmpty($("#qlrzjh").val()));
            if (unSearch) {
                warnMsg(" 请输入查询条件：权利人名称和权利人证件号或不动产权号。");
                return false;
            }

            // 设置默认的字段模糊类型
            var mhlx = {"qlrmcmhlx":0, "qlrzjhmhlx":0,"bdcqzhmhlx":3};
            form.val('searchform', JSON.parse(JSON.stringify(mhlx)));

            // 获取查询参数
            var obj = getSearchData();
            // 获取复选框参数
            obj.qllx = getQllxCheckedVal();
            // 默认权属状态为 现势产权
            obj.qszt3 = 1;
            // 查询IP
            obj.ipaddress = ipaddress;

            addModel();
            // 重新请求
            table.reload("pageTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/zszm/dycx",
                where: obj,
                page: {
                    curr: 1,  //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                },
                done: function (res, curr, count) {
                    changeCheckboxBackground([{'name': 'bdc_change_red', 'color': 'red'}]);
                    removeModal();
                    if(res.code ==0){
                        currentPageData = res.data;
                        reverseTableCell(reverseList);
                        setHeight();

                        // 查询附加信息展示
                        getZhcxFjxx(currentPageData);

                        //根据权利状态、办理状态整行显色
                        var colorList = [];
                        $.ajax({
                            url: '/realestate-inquiry-ui/rest/v1.0/status/color',
                            type: "GET",
                            success: function(data) {
                                for(var i in data) {
                                    colorList.push({name:i,color:'#333',background:data[i]});
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
                    }else{
                        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + res.msg);
                    }
                }
            });
        }



        //查询条件：复选框事件绑定
        form.on('checkbox(qllx)',function(data){
            search();
        });

        //点击表格中的更多
        $('.bdc-table-box').on('click','.bdc-more-btn',function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            event.stopPropagation();
            $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
            var $btnMore = $(this).siblings('.bdc-table-btn-more');
            if($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight){
                // $btnMore.css({top: $(this).offset().top + 26 -scrollTop,right: 20});
                $btnMore.css({top: tableTop + 26 -scrollTop,left:tableLeft - 30});
            }else {
                // $btnMore.css({top: $(this).offset().top -scrollTop - $btnMore.height(),right: 20});
                $btnMore.css({top: tableTop -scrollTop - $btnMore.height(),left: tableLeft - 30});
            }
            $('.bdc-table-btn-more').hide();
            $btnMore.show();
        });
        //点击更多内的任一内容，隐藏
        $('.bdc-table-btn-more a').on('click',function () {
            $(this).parent().hide();
        });
        //点击页面任一空白位置，消失
        $('body').on('click',function () {
            $('.bdc-table-btn-more').hide();
            $('.bdc-table-top-more-show').hide();
        });

        showMoreBtn();
    });
    //获取页面复选框权利类型选中的信息
    window.getQllxCheckedVal= function(){
        var checkedArray = new Array();
        $("input:checkbox[name='qllxmc']:checked").each(function(i){
            var strArray = $(this).val().split(",");
            $.each(strArray,function(index,val){
                if(null != val && ""!= val){
                    checkedArray.push(val);
                }
            });
        });
        return checkedArray.join(",");
    }

    //获取页面复选框权属状态选中的信息
    window.getQsztCheckedVal= function(){
        var checkedArray = new Array();
        $("input:checkbox[name='qsztmc']:checked").each(function(i){
            var strArray = $(this).val().split(",");
            $.each(strArray,function(index,val){
                if(null != val && ""!= val){
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

    //身份证读卡器设置
    window.onReadIdCard = function (qlrlb) {
        try {
            var cardInfo = readIdCard();
            if (cardInfo.ReadCard()) {
                var name = $.trim(cardInfo.Name);
                var number = $.trim(cardInfo.ID);

                if (1 == qlrlb) {
                    var qlrmc = $('input[name="qlrmc"]').val();
                    if(isNotBlank(qlrmc)){
                        name = qlrmc + "," + name;
                    }
                    $('input[name="qlrmc"]').val(name);

                    var qlrzjh = $('input[name="qlrzjh"]').val();
                    if(isNotBlank(qlrzjh)){
                        number = qlrzjh + "," + number;
                    }
                    $('input[name="qlrzjh"]').val(number);
                } else {
                    var ywrmc = $('input[name="ywrmc"]').val();
                    if(isNotBlank(ywrmc)){
                        name = ywrmc + "," + name;
                    }
                    $('input[name="ywrmc"]').val(name);

                    var ywrzjh = $('input[name="ywrzjh"]').val();
                    if(isNotBlank(ywrzjh)){
                        number = ywrzjh + "," + number;
                    }
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

// 查询附加信息展示
function getZhcxFjxx(pageData){
    if(pageData && pageData.length > 0){
        var xmidArray = new Array();
        $.each(pageData, function(index,item) {
            xmidArray.push(item.xmid);
        });

        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zszm/fjxx",
            type: "POST",
            data: JSON.stringify(xmidArray),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data && data.length > 0){
                    $.each(data, function (index, item) {
                        var zh   = isNullOrEmpty(item.zh)   ? "" : item.zh;
                        var fjh  = isNullOrEmpty(item.fjh)  ? "" : item.fjh;
                        var jzmj = isNullOrEmpty(item.jzmj) ? "" : item.jzmj;
                        var tdsyqmj = isNullOrEmpty(item.tdsyqmj) ? "" : item.tdsyqmj;

                        $("."+ item.xmid + "_zh").html('<p class="bdc-table-state-th">' + zh + '</p>');
                        $("."+ item.xmid + "_fjh").html('<p class="bdc-table-state-th">' + fjh + '</p>');
                        $("."+ item.xmid + "_fwmj").html('<p class="bdc-table-state-th">' + jzmj + '</p>');
                        $("."+ item.xmid + "_tdqlmj").html('<p class="bdc-table-state-th">' + tdsyqmj + '</p>');
                    });
                }
            }
        });
    }
}

// 设置默认按钮默认隐藏
function setBtnHidden(){
    $("#excel").hide();
    $("#yfwfzm").hide();
    $("#qtzm").hide();
    $("#qszm").hide();
    $("#dyzm").hide();
    $("#yfwfzmqy").hide();
    $("#wbdcdjzlzm").hide();
}

// 根据页面版本控制“无不动产登记资料证明”按钮
function setBtnQhkzm() {
    // 海门版本“无不动产登记资料证明”按钮移出展示
    $(".wbdcdjzlzm").hide();
}

// 显示其他证明下拉内容
function showMoreBtn() {
    //点击表格上面的更多按钮
    $('.bdc-table-top-more>.layui-btn').on('click',function (event) {
        event.stopPropagation();
        $(this).siblings().show();
    });
    //点击更多内a标签，隐藏
    $('.bdc-table-top-more-show a').on('click',function (event) {
        event.stopPropagation();
        $(this).parent().hide();
    });
}

// 获取页面中查询参数
function getSearchData(){
    var obj = {};
    $(".search").each(function (i) {
        var name = $(this).attr('name');
        var value = $(this).val();
        if (value) {
            value = value.replace(/\s*/g, "");
        }
        obj[name] = value;
    });
    return obj;
}
/**
 * 查询档案调用的参数信息
 * @param xmid
 */
function tellTdFromBdc(xmid){
    var dataObj = {};
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zszm/tellTdFromBdc?xmid='+xmid,
        async: false,
        dataType: "json",
        success: function (data) {
            dataObj =  data;
        }
    });
    return dataObj;
}
/**
 * 档案调用
 */
function dady(data) {
    var dadyInfo = tellTdFromBdc(data.xmid)

    if(dadyInfo.version == "haimen") {
        // 海门版本
        if(!dadyInfo || isNullOrEmpty(dadyInfo.hmDadyUrl)) {
            failMsg("未配置档案调用地址，请联系管理员!");
            return;
        }
        window.open(dadyInfo.hmDadyUrl + data.slbh);
    } else {
        // 南通版本
        if(dadyInfo.XMLY == "2"){// 土地
            var url = dadyInfo.tdDadyUrl;
            url+="?userName="+encodeURI(dadyInfo.userName);
            url+="&qlrmc="+encodeURI(data.qlrmc?data.qlrmc:"");
            url+="&tdzl="+encodeURI(data.zl?data.zl:"");
            url+="&tdzh="+encodeURI(data.ytdzh?data.ytdzh:"");
            url+="&djh="+encodeURI(data.ybdcdyh?data.ybdcdyh:"");
            url+="&password=";
            window.open(url);
        }else{// 不动产
            var now = new Date();
            var dateStr = now.getFullYear()+"-"+(now.getMonth()+1)+'-'+now.getDate();
            var keycode = data.slbh;
            var user = dadyInfo.userName;
            var key = hex_md5((keycode+user+"VIEWPIC"+dateStr).toUpperCase()).toUpperCase();
            var url = dadyInfo.dadyUrl+"?keycode="+keycode+"&user="+user+"&key="+key;
            window.open(url);
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
function qtzm(type){
    var qlrmc = $("#qlrmc").val();
    var qlrzjh = $("#qlrzjh").val();
    var zl = $("#zl").val();

    var validZjhAndMc = ["ycszm","bwsqrsyzm","ycszm","dkzm", "zlbgzmyzl", "zlbgzmxzl", "qtzm", "wbdcdjzlzm"];
    if(validZjhAndMc.indexOf(type) > -1){
        if(isNullOrEmpty(qlrmc) || isNullOrEmpty(qlrzjh)){
            warnMsg("请输入权利人名称、证件号信息！")
            return;
        }
    }

    if("ycszm" == type || "dkzm" == type || "zlbgzmxzl" == type || "zlbgzmyzl" == type) {
        var checkedData = layui.sessionData('checkedData');
        if($.isEmptyObject(checkedData)){
            warnMsg("请选择需要出证明的数据行！");
            return;
        }
    }

    addModel();
    var checkedData = layui.sessionData('checkedData');
    if(Object.keys(checkedData).length <= 1){
        var zmxx = getZmxx();
        if(zmxx){
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

            $.cookie('fczm', JSON.stringify(zmxx),{path: '/realestate-inquiry-ui/view/zszm'});
            removeModal();
            window.open("/realestate-inquiry-ui/view/zszm/fczm.html?pageType=1&type=" + type);
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
            if(zmxx){
                item.bh = zmxx.bh;
                item.cxsd = zmxx.cxsd;
                item.zmsj = zmxx.zmsj;
            }
            // 设置证明内容
            setZmnr(type, item);
            array.push(item);
        });
        layui.data('fczmListData', {key:"data", value: JSON.stringify(array)});

        // 查询信息
        $.cookie('cxxx', JSON.stringify({"qlrmc":qlrmc, "qlrzjh":qlrzjh, "zl": zl}),{path: '/realestate-inquiry-ui/view/zszm'});
        removeModal();
        window.open("/realestate-inquiry-ui/view/zszm/fczmList.html?type=" + type);
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
    var cssj ;
    if("ycszm" == type){
        cssj = getFdcqCssj(fczm.xmid);
        if(isNullOrEmpty(cssj)){
            cssj = "  ";
        }
    }

    if("bwsqrsyzm" == type){
        fczm.zmnr = "   " + fczm.zl + "当前登记不动产权利人不是" + fczm.cxsqr + "。";
    } else if("ycszm" == type){
        fczm.zmnr = "   " + fczm.cxsqr + "原有" + fczm.zl + "，不动产权证号：" + fczm.bdcqzh + "，上述不动产已于" + cssj + "出售给他人。";
    } else if("wbdcdjzlzm" == type){
        fczm.zmnr = "   " + fczm.zl + "当前无不动产权登记资料。"
    } else if("dkzm" == type) {
        fczm.zmnr = "   " + fczm.cxsqr + "于" + formatDate2(fczm.djsj) + "向" + fczm.ywrmc + "购买" + fczm.zl + "，现不动产权证号：" + fczm.bdcqzh + "。\n" +
            "   " + fczm.ywrmc + "原不动产权证号：" + fczm.ycqzh + "。\n"
    } else if("zlbgzmyzl" == type) {
        fczm.zmnr = "   " + fczm.zl + "由" + fczm.cxsqr + "领有不动产权证，不动产权证证号为：" + fczm.bdcqzh + "。该处原发证座落为：";
    } else if("zlbgzmxzl" == type) {
        fczm.zmnr = "   " + fczm.zl + "由" + fczm.cxsqr + "领有不动产权证，不动产权证证号为：" + fczm.bdcqzh + "。该处现座落为：";
    } else if("qtzm" == type) {
        fczm.zmnr = "   ";
    }
}

// 获取房产出售时间
function getFdcqCssj(xmid){
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
    if(isNullOrEmpty(fczm.zmnr)){
        fczm.zmnr = "";
    }
    if(isNullOrEmpty(fczm.zl)){
        fczm.zl = "";
    }
    if(isNullOrEmpty(fczm.qlrmc)){
        fczm.qlrmc = "";
    }
    if(isNullOrEmpty(fczm.bdcqzh)){
        fczm.bdcqzh = "";
    }
    if(isNullOrEmpty(fczm.djsj)){
        fczm.djsj = "";
    }
    if(isNullOrEmpty(fczm.ywrmc)){
        fczm.ywrmc = "";
    }
    if(isNullOrEmpty(fczm.ycqzh)){
        fczm.ycqzh = "";
    }
}

function formatDate2(timestamp){
    if(!timestamp){
        return '';
    }
    var time = new Date(timestamp);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    return y+'年'+add0(m)+'月'+add0(d)+'日';
}

/**
 * 获取房屋价值
 * @returns {*}
 */
function getJyjg(){
    // 延迟加载
    ycjz("xmid", "jyjg", "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/fczm/fdcq/jyjg");
}


//高级查询弹出层js方法
layui.use(['form', 'jquery', 'table', 'laytpl'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laytpl = layui.laytpl;

    var moreSearchIndex;

    $(function () {
        // 高级查询
        $('#moreSearch').on('click', function () {
            // 每次查询清除下导出缓存数据
            layui.sessionData("checkedData", null);
            needsearch = false;

            var data  = getSearchData();
            var tpl = moreSearchTpl.innerHTML, view = document.getElementById("popupTwoRows");
            laytpl(tpl).render(data, function (html) {
                view.innerHTML = html;
            });
            moreSearchIndex = layer.open({
                title: '高级查询',
                type: 1,
                area: ['960px', '320px'],
                content: $("#popupTwoRows"),
                success: function(){
                    bindBtcClickEvent();
                },
                end: function () {
                    if (needsearch && searchData) {
                        addModel();
                        // 高级查询需要清空勾选缓存
                        layui.sessionData("checkedData", null);
                        // IP地址
                        searchData.ipaddress = ipaddress;

                        table.reload("pageTable", {
                            url: "/realestate-inquiry-ui/rest/v1.0/zszm/dycx"
                            , where: searchData
                            , page: {
                                curr: 1,  //重新从第 1 页开始
                                limits: [10, 50, 100, 200, 500]
                            },
                            done: function (res, curr, count) {
                                changeCheckboxBackground([{'name': 'bdc_change_red', 'color': 'red'}]);
                                removeModal();
                                if(res.code ==0){
                                    currentPageData = res.data;
                                    reverseTableCell(reverseList);
                                    setHeight();

                                    // 查询附加信息展示
                                    getZhcxFjxx(currentPageData);

                                    //根据权利状态、办理状态整行显色
                                    var colorList = [];
                                    $.ajax({
                                        url: '/realestate-inquiry-ui/rest/v1.0/status/color',
                                        type: "GET",
                                        success: function(data) {
                                            for(var i in data) {
                                                colorList.push({name:i,color:'#333',background:data[i]});
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
                                }else{
                                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + res.msg);
                                }
                            }
                        });
                    }
                }
            });
        });

        // 高级查询的查询事件
        form.on('submit(submitBtn)', function(data) {
            // 获取权利人名称与证件号信息
            var val = new Object();
            var qlrmc = new Array();
            $("#popupTwoRows").find("input[name='qlrmc']").each(function(){
                var mc = $(this).val();
                if(!isNullOrEmpty(mc)){
                    qlrmc.push(mc);
                }
            });
            if(qlrmc && qlrmc.length > 0){
                val.qlrmc = qlrmc.join();
            }

            var qlrzjh = new Array();
            $("#popupTwoRows").find("input[name='qlrzjh']").each(function(){
                var zjh = $(this).val();
                if(!isNullOrEmpty(zjh)){
                    qlrzjh.push(zjh);
                }
            });
            if(qlrzjh && qlrzjh.length > 0){
                val.qlrzjh = qlrzjh.join();
            }

            // 验证必要查询条件
            var count = 0;
            $("#popupTwoRows").find(".required").each(function(i){
                var value = $(this).val();
                if(isNullOrEmpty(value)){
                    count++;
                }
            });
            if(0 != count){
                warnMsg(" 请输入完整权利人名称和权利人证件号信息。");
                return false;
            }
            val.qllx = getQllxCheckedVal();
            val.qszt3 = 1;
            setSearch(val, true);
            layer.close(moreSearchIndex);
        });

        function bindBtcClickEvent(){
            // 高级查询监听点击新增权利人
            $('.bdc-add-qlr').on('click',function () {
                var getTpl = qlrTpl.innerHTML;
                laytpl(getTpl).render([], function(html){
                    $('.bdc-add-box').append(html);
                });
            });
            // 取消按钮绑定事件
            $('.bdc-frame-close').on('click',function () {
                setSearch(null, false);
                layer.close(moreSearchIndex);
            });
        }

        // 高级查询监听点击删除权利人按钮
        $('#popupTwoRows').on('click','.bdc-delete-qlr',function () {
            $(this).parent().remove();
        });

    });

    function setSearch(data, needSearch){
        searchData = data;
        needsearch = needSearch;
        if (searchData) {
            form.val('searchform', JSON.parse(JSON.stringify(searchData)));
        }
    }

    // 高级查询取消按钮事件
    function cancel(){
        setSearch(null, false);
        layer.close(moreSearchIndex);
    }

});
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