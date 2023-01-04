/**
 * 南通综合查询页面JS处理（原证书证明查询）
 */
var reverseList = ['zl'];
var searchData, needsearch;
var zdList = getMulZdList("fwyt");
// 用户IP地址
var ipaddress;

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

    // 下拉选择添加删除按钮
    renderSelectClose(form);
    // 当前页数据
    var currentPageData = new Array();

    $(function () {
        // 获取页面表单标识，用于权限控制
        //var moduleCode = $.getUrlParam('moduleCode');

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
                //setBtnHidden();
                // 控制按钮权限
                // setElementAttrByModuleAuthority(moduleCode);
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
                $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
            } else {
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
            } else if (obj.event === 'lpb') {
                window.open("/building-ui/lpb/view?code=analysis&bdcdyh=" + data.bdcdyh);
            } else if (obj.event === 'djb') {
                window.open("/realestate-register-ui/view/djbxx/bdcDjb.html?param=" + data.bdcdyh);
            } else if (obj.event === 'dady') {
                var dadyInfo = tellTdFromBdc(data.xmid)
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
        });

        // 监听表格操作栏按钮
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0]);
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
            $("#fileName").val('不动产登记受理查询信息结果导出Excel表');
            $("#sheetName").val('不动产登记受理查询信息结果导出Excel表');
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
                warnMsg(" 请输入必要查询条件，例如权利人证件号");
                return false;
            }

            // 设置默认的字段模糊类型
            var mhlx = {"bdcdyhmhlx":3, "bdcqzhmhlx":3, "qlrmcmhlx":3, "qlrzjhmhlx":3, "ywrmcmhlx":3,
                "ywrzjhmhlx":3, "zlmhlx":3, "ycqzhmhlx":3, "zhmhlx":3, "fjhmhlx":3, "slbhmhlx":3,
                "fwbhmhlx":3, "zhlshmhlx":0, "ybdcdyhmhlx":3, "yxtcqzhmhlx":3 , "zsyzhmhlx":0};
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
                        success: function(data) {
                            for(var i in data) {
                                colorList.push({name:i,color:'#333',background:data[i]});
                            }
                            changeTrBackgroundExceptRight(colorList);
                        }
                    });

                    showMoreBtn();

                    // 默认按钮隐藏
                    //setBtnHidden();
                    // 控制按钮权限
                    //setElementAttrByModuleAuthority(moduleCode);
                }
            });
        }

        // 高级查询
        $('#moreSearch').on('click', function () {
            needsearch = false;

            layer.open({
                title: '高级查询',
                type: 2,
                area: ['960px', '520px'],
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
                                    success: function(data) {
                                        for(var i in data) {
                                            colorList.push({name:i,color:'#333',background:data[i]});
                                        }
                                        changeTrBackgroundExceptRight(colorList);
                                    }
                                });

                                // 默认按钮隐藏
                                //setBtnHidden();
                                // 控制按钮权限
                                //setElementAttrByModuleAuthority(moduleCode);
                            }
                        });
                    }
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

        //查询条件：复选框事件绑定
        form.on('checkbox(qllx)',function(data){
            search();
        });
        //查询条件：复选框事件绑定
        form.on('checkbox(qszt3)',function(data){
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
                $btnMore.css({top: tableTop + 26 -scrollTop,left:tableLeft - 30});
            }else {
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

        // 设置默认按钮默认隐藏
        function setBtnHidden(){
            $("#excel").hide();
        }
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
                    //$('input[name="qlrmc"]').val(name);
                    $('input[name="qlrzjh"]').val(number);
                } else {
                    //$('input[name="ywrmc"]').val(name);
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
