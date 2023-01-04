var jjdid = '';
var checkJjd = {};
var isDel = '';
layui.use(['table', 'laytpl','laydate', 'layer'], function () {
    var table = layui.table,
        $ = layui.jquery,
        laydate = layui.laydate,
        layer = layui.layer,
        form = layui.form;

    $(function () {
        // 当前页数据
        var currentPageData = new Array();
        // 页面刷新清除下勾选数据
        layui.sessionData("checkedData", null);

        var qssj = laydate.render({
            elem: '#qssj',
            type: 'datetime',
            trigger: 'click',
            done: function (value, date) {
                //选择的结束时间大
                if ($('#jssj').val() != '' && !completeDate($('#jssj').val(), value)) {
                    $('#jssj').val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                }
                jssj.config.min = {
                    year: date.year,
                    month: date.month - 1,
                    date: date.date,
                    hours: date.hours,
                    minutes: date.minutes,
                    seconds: date.seconds
                }
            }
        });
        var jssj = laydate.render({
            elem: '#jssj',
            type: 'datetime',
            value: new Date(),
            trigger: 'click'
        });

        zdList = getZdList();
        // 交接单状态下拉
        $.each(zdList.jjdzt, function (index, item) {
            $('#jjdzt').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        form.render('select');

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            // 每次查询清除下勾选数据
            layui.sessionData("checkedData", null);

            // 获取查询内容
            var obj = {};
            $(".search").each(function (i) {
                var value = $(this).val();
                if (!isNullOrEmpty(value)) {
                    var name = $(this).attr('name');
                    obj[name] = value;
                }
            });
            // 重新请求
            table.reload("tableOne", {
                url: BASE_URL + "/jjd/page"
                , where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }, done: function (res, curr, count) {
                    currentPageData = res.data;

                    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                    if ($('.layui-table-body>.layui-table').height() == 0) {
                        $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
                    }
                }
            });
            return false;
        });

        //触发排序事件
        table.on('sort(oneFilter)', function(obj){
            // 获取查询内容
            var data = {};
            $(".search").each(function (i) {
                var value = $(this).val();
                if (!isNullOrEmpty(value)) {
                    var name = $(this).attr('name');
                    data[name] = value;
                }
            });

            data.field = obj.field;
            data.order = obj.type;

            table.reload('tableOne', {
                url: BASE_URL + "/jjd/page",
                where: data,
                initSort: obj,
                done: function (res, curr, count) {
                    currentPageData = res.data;

                    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                    if ($('.layui-table-body>.layui-table').height() == 0) {
                        $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
                    }
                }
            });
        });

        table.render({
            elem: '#tableOne',
            toolbar: '#toolbarDemo',
            defaultToolbar: [],
            title: '交接单信息',
            even: true,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            limits: commonLimits,
            url: BASE_URL + "/jjd/page",
            cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'jjdh', title: '交接单号', width: 200, sort: true,
                    templet: function (d) {
                        return showJjdh(d);
                    }
                },
                {
                    field: 'jjdlx', title: '交接单类型', width: 200,
                    templet: function (d) {
                        if (zdList && zdList.jjdlx && !isNullOrEmpty(d.jjdlx + '')) {
                            for (var index in zdList.jjdlx) {
                                if (zdList.jjdlx[index].DM == d.jjdlx) {
                                    return zdList.jjdlx[index].MC;
                                }
                            }
                            return '';
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'zfr', title: '转发人', width: 100},
                {field: 'zfsj', title: '转发时间', width: 200},
                {field: 'zfks', title: '转发科室', width: 100},
                {field: 'jsr', title: '接收人'},
                {field: 'jssj', title: '接收时间'},
                {field: 'jsks', title: '接收科室'},
                {field: 'jjdzt', title: '交接单状态', templet: '#jjdztTpl', sort: true},
                {fixed: 'right', title: '操作', toolbar: '#barDemoOne', width: 160}
            ]],
            page: true,
            parseData: function (res) {
                res.content.forEach(function (v) {
                    if (v.zfsj) {
                        var newStartTime = new Date(v.zfsj);
                        v.zfsj = format(newStartTime);
                    }
                    if (v.jssj) {
                        var newEndTime = new Date(v.jssj);
                        v.jssj = format(newEndTime);
                    }
                });

                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.jjdid) {
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
            done: function (res, curr, count) {
                currentPageData = res.data;

                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        });

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(oneFilter)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;

            $.each(data, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: v.jjdid, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: v.jjdid, remove: true
                    });
                }
            });
        });

        var clickIndex = 0;
        window.showXmxx = function(jjdid) {
            clickIndex++;
            // 第一次选择需要渲染页面，以后重新加载就可以
            if (clickIndex == 1) {
                renderTable(jjdid);
            } else {
                reloadTable(jjdid);
            }
        }

        // 点击交接单号展示关联的项目
        function showJjdh(data) {
            if(!isNullOrEmpty(data.jjdh)){
                return '<a  style="color: #1d87d1" onclick="showXmxx(&quot;' + data.jjdid + '&quot;)">' + data.jjdh + '</a>';
            }else{
                return "";
            }
        }

        // 首次渲染表格
        function renderTable(jjdid) {
            table.render({
                elem: '#tableTwo',
                id: 'twoId',
                title: '交接单项目信息',
                toolbar: '#toolbarXmDemo',
                defaultToolbar: [],
                request: {
                    limitName: 'size' //每页数据量的参数名，默认：limit
                },
                even: true,
                limits: commonLimits,
                cols: [[
                    {type: 'checkbox', width: 50, fixed: 'left'},
                    {field: 'jjdxmid', title: '交接单项目ID', hide: true},
                    {field: 'slbh', title: '受理编号', sort: true},
                    {field: 'djlxmc', title: '登记类型名称'},
                    {field: 'gzldymc', title: '流程名称'},
                    {field: 'zl', title: '房屋地址'},
                    {field: 'qlr', title: '申请人',templet: '#qlrTpl'},
                    {field: 'cqr', title: '产权人',templet: '#ywrTpl'},
                    {fixed: 'right', title: '操作', toolbar: '#barDemoTwo', width: 80}
                ]],
                url: BASE_URL + "/jjd/xm?jjdid=" + jjdid,
                parseData: function (res) {
                    return {
                        code: res.code, //解析接口状态
                        msg: res.msg, //解析提示文本
                        count: res.totalElements, //解析数据长度
                        data: res.content //解析数据列表
                    }
                },
                done: function () {
                    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                    if ($('.layui-table-body>.layui-table').height() == 0) {
                        $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
                    }
                }
            });
        }

        // 重新加载
        function reloadTable(jjdid) {
            table.reload('twoId', {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                url: BASE_URL + "/jjd/xm?jjdid=" + jjdid
            });
        }

        function zxjjd(obj) {
            addModel();
            $.ajax({
                url: BASE_URL + "/jjd/zxjjd",
                type: 'POST',
                data: JSON.stringify(obj),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    removeModel();
                    var obj = {};
                    obj['jjdh'] = data.jjdh;
                    // 重新请求
                    table.reload("tableOne", {
                        url: BASE_URL + "/jjd/page"
                        , where: obj
                        , page: {
                            curr: 1  //重新从第 1 页开始
                        }
                    });
                },
                error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr);
                }
            });
        }

        // 交接单表格工具栏
        table.on('toolbar(oneFilter)', function (obj) {
            if (obj.event === 'zxjjd') {
                 layer.open({
                    title: '注销交接单',
                    type: 1,
                    area: ['430px'],
                    btn: ['生成', '取消'],
                    content: $('#bdc-popup-textarea')
                    ,yes: function(index, layero){
                        //提交 的回调
                        var qssj = $("#qssj").val();
                        var jssj = $("#jssj").val();
                        var obj = {};
                        obj['begin'] = qssj;
                        obj['end'] = jssj;
                        if (qssj!="" && jssj !="") {
                            layer.close(index);
                            zxjjd(obj);
                        } else {
                            warnMsg("请选择起始时间和结束时间！")
                        }
                    }
                    ,btn2: function(index, layero){
                        //取消 的回调
                    }
                    ,cancel: function(){
                        //右上角关闭回调
                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                    ,success: function () {
                    }
                });
                return false;
            }

            if (obj.event === 'print') {
                printJjd();
            } else if (obj.event === 'forward') {
                forward();
            } else if (obj.event === 'delJjd') {
                deleteJjd();
            }
        });

        /**
         * 交接单批量转发
         */
        function forward() {
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要转发的交接单！");
                return;
            }

            var data = new Array();
            $.each(checkedData, function (key, value) {
                data.push(value);
            });

            // 判断状态
            for (var i = 0; i < data.length; i++) {
                if (data[i].jjdzt == 1) {
                    // 已转发
                    if (data[i].jsrid == null) {
                        // 接收人为空表示收件方,需要先确认接收才可以转发
                        warnMsg('请先确认接收交接单，才能转发！');
                        return false;
                    } else {
                        // 接收人不为空表示发件方,不可以重复转发
                        warnMsg('存在已经转发交接单，请勿重复操作！');
                        return false;
                    }
                } else if (data[i].jjdzt == 2 && data[i].jsrid != null) {
                    // 已接收，发件方不可以再次转发
                    warnMsg('存在已经转发交接单，请勿重复操作！');
                    return false;
                } else if (data[i].jjdzt == 3 && data[i].jsrid == null) {
                    // 已拒绝，收件方不可以再次转发
                    warnMsg('存在已经转发交接单，请勿重复操作！');
                    return false;
                }
            }

            layer.open({
                type: 2,
                skin: 'layui-layer-lan',
                title: '任务转发',
                area: ['960px', '490px'],
                content: '../jjd/send.html'
            });
        }

        /**
         * 批量删除交接单（这里因为删除逻辑复杂，后台逻辑不改成批量的，直接循环调用单个删除接口）
         */
        var deleteSuccess = true, deleteMsg = "";
        function deleteJjd() {
            deleteSuccess = true;
            deleteMsg = "";

            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要删除的交接单！");
                return;
            }

            var data = new Array();
            $.each(checkedData, function (key, value) {
                data.push(value);
            });

            var deleteIndex = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: '确认删除',
                area: ['320px'],
                content: '是否确认删除？',
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function () {
                    executeDeleteJjd(data);
                    layer.close(deleteIndex);
                },
                btn2: function () {
                    //取消
                    layer.close(deleteIndex);
                }
            });
        }

        function executeDeleteJjd(data) {
            addModel();
            for (var i = 0; i < data.length; i++) {
                if (data[i].jjdzt == 0) {
                    // 交接单状态为 0 表示从未转发过，并且只有用户只可以看到自己创建的交接单
                    delJjd(data[i]);
                } else if (data[i].jjdzt == 3) {
                    checkDelJjd(data[i]);
                } else {
                    deleteMsg = "交接单" + data[i].jjdh + "已经转发，无法删除！";
                }
            }

            removeModel();
            layer.closeAll();

            if(true == deleteSuccess && isNullOrEmpty(deleteMsg)) {
                successMsg("删除成功！");
                setTimeout(function () {
                    location.reload();
                }, 1000);
            } else {
                var deleteIndex = layer.open({
                    type: 1,
                    skin: 'bdc-small-tips',
                    title: '提示',
                    area: ['320px'],
                    content: deleteMsg,
                    btn: ['确定'],
                    closeBtn: 0,
                    btnAlign: 'c',
                    yes: function () {
                        location.reload();
                        layer.close(deleteIndex);
                    }
                });
            }
        }

        // 交接单项目表格工具栏
        table.on('toolbar(twoFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var selectedNum = checkStatus.data.length;

            if (selectedNum < 1) {
                warnMsg('至少请选择一条数据进行操作！');
                return false;
            }
            if (obj.event === 'delJjdXm') {
                $.when(checkDelJjdXm()).done(function () {
                    if (isDel != 'true'){
                        return false;
                    }
                    var gzlslids = '';
                    checkStatus.data.forEach(function (v) {
                        if (!isNullOrEmpty(v.gxid)) {
                            gzlslids += v.gzlslid + ",";
                        }
                    });
                    gzlslids.substring(gzlslids.length - 1, gzlslids.length);
                    var submitIndex = layer.open({
                        type: 1,
                        skin: 'bdc-small-tips',
                        title: '确认删除',
                        area: ['320px'],
                        content: '您确定要批量删除吗？',
                        btn: ['确定', '取消'],
                        btnAlign: 'c',
                        yes: function () {
                            delXm(gzlslids);
                            layer.close(submitIndex);
                        },
                        btn2: function () {
                            //取消
                        }
                    });
                });
            }
        });

        //监听交接单的行工具事件
        table.on('tool(oneFilter)', function (obj) {
            if (obj.data.jjdzt == 1) {
                switch (obj.event) {
                    case 'accept':
                        console.info(obj.data);
                        accept(obj.data.jjdid);
                        break;
                    case 'refuse':
                        console.info(obj.data);
                        refuse(obj.data.jjdid);
                        break;
                }
            } else {
                warnMsg("此交接单状态不允许操作！");
            }
        });

        //监听交接单项目的行工具事件
        table.on('tool(twoFilter)', function (obj) {
            if (obj.event === 'del') {
                $.when(checkDelJjdXm()).done(function () {
                    if (isDel != 'true'){
                        return false;
                    }
                    var submitIndex = layer.open({
                        type: 1,
                        skin: 'bdc-small-tips',
                        title: '确认删除',
                        area: ['320px'],
                        content: '您确定要删除吗？',
                        btn: ['确定', '取消'],
                        btnAlign: 'c',
                        yes: function () {
                            //确定操作
                            obj.del();
                            delXm(obj.data.gzlslid);
                            layer.close(submitIndex);
                        },
                        btn2: function () {
                            //取消
                        }
                    });
                });
            }
        });

        /**
         * 根据已知的值删除交接单中项目信息
         */
        function delXm(gzlslids) {
            addModel();
            $.ajax({
                url: BASE_URL + "/jjd/xm?gzlslids=" + gzlslids + "&jjdid=" + jjdid,
                type: 'delete',
                success: function (data) {
                    removeModel();
                    layer.closeAll();
                    layer.alert("删除成功!");
                    location.reload();
                },
                error: function (xhr, status, error) {
                    layer.closeAll();
                    removeModel();
                    delAjaxErrorMsg(xhr);
                }
            });
        }

        /**
         * 判断能否删除交接单项目
         * 判断 A 创建的交接单转发至 B 名下，B 退回给 A 后，A 可以删除交接单
         * @param data 删除项目信息
         */
        function checkDelJjdXm() {
            isDel = "false";
            if (checkJjd.jjdzt == 0) {
                isDel = 'true';
            } else if (checkJjd.jjdzt == 3) {
                $.ajax({
                    url: BASE_URL + "/jjd/check/deljjd?jjdid=" + checkJjd.jjdid,
                    type: 'get',
                    async: false,
                    success: function (data) {
                        isDel = data;
                        if (isDel != 'true') {
                            warnMsg("不允许删除项目信息！");
                        }
                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr);
                        isDel = 'false';
                    }
                });

            } else {
                warnMsg("不允许删除项目信息！");
                isDel = 'false';
            }
        }

        /**
         * 判断能否删除交接单
         * 判断 A 创建的交接单转发至 B 名下，B 退回给 A 后，A 可以删除交接单
         * @param data 删除项目信息
         */
        function checkDelJjd(checkData) {
            console.info(checkData);
            // 删除判断逻辑相同使用同一个接口
            $.ajax({
                url: BASE_URL + "/jjd/check/deljjd?jjdid=" + checkData.jjdid,
                type: 'get',
                async: false,
                success: function (data) {
                    if (data == 'true') {
                        delJjd(checkData);
                    } else {
                        deleteMsg = "交接单" + checkData.jjdh + "不允许被删除！";
                    }
                },
                error: function (xhr, status, error) {
                    deleteSuccess = false;
                    deleteMsg = "删除失败，请重试！";
                    console.info(xhr);
                }
            });
        }

        /**
         * 根据已知的值删除交接单中项目信息
         * @param data 删除项目信息
         */
        function delJjd(data) {
            console.info(data);
            $.ajax({
                url: BASE_URL + "/jjd?jjdid=" + data.jjdid,
                type: 'delete',
                async: false,
                success: function (data) {
                },
                error: function (xhr, status, error) {
                    deleteSuccess = false;
                    deleteMsg = "删除失败，请重试！";
                    console.info(xhr);
                }
            });
        }

        /**
         * 确认接收交接单
         * @param data 接收的项目信息
         */
        function accept(jjdid) {
            addModel();
            $.ajax({
                url: BASE_URL + "/jjd/accept?jjdid=" + jjdid,
                type: 'GET',
                success: function (data) {
                    removeModel();
                    if (data === true || data === 'true') {
                        successMsg("接收成功!");
                        $('#search').click();
                    } else {
                        warnMsg("当前用户是转发人，不允许确认接收！");
                    }
                },
                error: function (xhr, status, error) {
                    layer.closeAll();
                    removeModel();
                    delAjaxErrorMsg(xhr);
                }
            });
        }


        /**
         * 拒绝接收交接单
         * @param data 拒绝的项目信息
         */
        function refuse(jjdid) {
            addModel();
            $.ajax({
                url: BASE_URL + "/jjd/back?jjdid=" + jjdid,
                type: 'GET',
                success: function (data) {
                    removeModel();
                    if (data === true || data === 'true') {
                        successMsg("退回成功!");
                        $('#search').click();
                    } else {
                        warnMsg("当前用户是转发人，不允许操作！");
                    }
                },
                error: function (xhr, status, error) {
                    layer.closeAll();
                    removeModel();
                    delAjaxErrorMsg(xhr);
                }
            });
        }

        /**
         * 打印交接单
         * @param jjdid
         * @returns {boolean}
         */
        function printJjd() {
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                warnMsg(" 请选择需要打印的交接单！");
                return;
            }

            var data = new Array();
            $.each(checkedData, function (key, value) {
                var item = {};
                item.jjdid = value.jjdid;
                data.push(item);
            });

            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/jjd/dyxx",
                type: "POST",
                data: JSON.stringify(data),
                contentType: 'application/json',
                dataType: 'text',
                success: function (key) {
                    if (!isNullOrEmpty(key)) {
                        var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/jjd/batch/" + key + "/xml";
                        print(jjdModelUrl, dataUrl, false);
                    } else {
                        failMsg("交接单打印出错，请重试！");
                    }
                },
                error: function () {
                    failMsg("交接单打印出错，请重试！");
                }
            });
        }
    });
});

// 获取项目申请人和产权人，针对组合流程中包含抵押流程的项目需要显示产权类的信息
function ddyqlr(gzlslid) {
    $.ajax({
        url: BASE_URL + "/jjd/ddyqlr?gzlslid=" + gzlslid,
        type: 'GET',
        dataType: 'json',
        async: true,
        success: function (data) {
            if (!isNullOrEmpty(data)) {
                $("."+ gzlslid +"_qlr").html('<p>'+ data.qlr + '</p>');
                $("."+ gzlslid +"_ywr").html('<p>'+ data.ywr + '</p>');
            } else {
                $("."+ gzlslid +"_qlr").html('<p></p>');
                $("."+ gzlslid +"_ywr").html('<p></p>');
            }
        }
    });
}

function completeDate(date1,date2){
    var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    if(oDate1.getTime() >= oDate2.getTime()){
        return true;
    } else {
        return false;
    }
}
