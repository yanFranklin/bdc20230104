var type = GetQueryString("type");
var moduleCode = GetQueryString("moduleCode");
var reverseList = ['zl'];
var cgbsZd = [];
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl','laydate'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;
    // var reverseList = ['bdcdyh', 'zl', 'bdcqzh'];
    $(function () {
        //初始化日期控件
        laydate.render({
            elem: '#kssj'
            , type: 'datetime'
        });
        laydate.render({
            elem: '#jssj'
            , type: 'datetime'
        });

        // 加载下拉选项
        $.ajax({
            url: "../rest/v1.0/bdczd/mul",
            dataType: "json",
            data: {
                zdNames: "xyxx,jrbs"
            },
            async: false,
            success: function (data) {
                console.info(data);
                $('#xybm').append(new Option("请选择", ""));
                $.each(data['xyxx'], function (index, item) {
                    $('#xybm').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
                });
                $.each(data['jrbs'], function (index, item) {
                    $('#sbzt').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
                });
                cgbsZd = data['jrbs'];
                form.render("select");
            },
            error: function (e) {
            }
        });
        //点击高级查询--4的倍数
        $('#seniorSearch').on('click', function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');
            $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);

            $('.layui-table-body').height($('.bdc-table-box').height() - 129);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);
        });

        var nationaltableConfig = {
            toolbar: "#toolbarSqxx"
            // , url: '../accessLog/getAccessLogPagesJson?type=' + type
            , limits: [10, 50, 100, 200, 500]
            , cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'slbh', title: '受理编号', width: 150},
                {field: 'bdcdyh', title: '不动产单元号', width: 250},
                {
                    field: 'cgbs', title: '接入状态', width: 100
                    , templet: function (d) {
                        return jrzt(d.cgbs);
                    }
                },
                {field: 'xyzt', title: '获取响应报文状态', width: 200
                    , templet: function (d) {
                        return xyzt(d);
                    }
                },
                {field: 'xyxx', title: '响应信息', width: 250},
                {field: 'qlr', title: '权利人'},
                {field: 'ywr', title: '义务人'},
                //{field: 'SQLX', title: '申请类型'},
                {field: 'zl', title: '坐落', width: 250},
                {field: 'bdcqzh', title: '不动产权证号', width: 250},
                {field: 'bjsj', title: '办结时间', width: 150},
                {field: 'ywbwid', title: '业务报文ID', width: 200},
                {
                    title: '操作',
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#nationallogListToolBarTmpl',
                    width: 250
                }
            ]],
            data:[],
            page: true,
            done: function () {
                // reverseTableCell(reverseList);
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body').height('60px');
                    $('.layui-table-fixed .layui-table-body').height('60px');
                    $('.layui-table-body .layui-none').html('<img src="../static/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
                setElementAttrInTableByModuleAuthority(moduleCode);
            }
        };

        var provincetableConfig = {
            toolbar: "#toolbarSqxx"
            // , url: '../accessLog/getAccessLogPagesJson'
            , limits: [10, 50, 100, 200, 500]
            , cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'slbh', title: '受理编号', width: 150},
                {field: 'bdcdyh', title: '不动产单元号', width: 250},
                {
                    field: 'cgbs', title: '上报状态', width: 100
                    , templet: function (d) {
                        return changeDmToMc(d.cgbs, cgbsZd);
                    }
                },
                {field: 'xyxx', title: '响应信息', width: 250},
                {field: 'qlr', title: '权利人'},
                {field: 'ywr', title: '义务人'},
                //{field: 'SQLX', title: '申请类型'},
                {field: 'zl', title: '坐落', width: 250},
                {field: 'bdcqzh', title: '不动产权证号', width: 250},
                {field: 'bjsj', title: '办结时间', width: 150},
                {field: 'ywbwid', title: '业务报文ID', width: 200},
                {
                    title: '操作',
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#provincelogListToolBarTmpl',
                    width: 300
                }
            ]],
            data:[],
            page: true,
            done: function () {
                // reverseTableCell(reverseList);
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body').height('60px');
                    $('.layui-table-fixed .layui-table-body').height('60px');
                    $('.layui-table-body .layui-none').html('<img src="../static/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
                setElementAttrInTableByModuleAuthority(moduleCode);
            }
        };

        // $('.bdc-table-box').on('mouseenter','td',function () {
        //     if($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1){
        //         $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
        //     }
        //     $('.layui-table-grid-down').on('click',function () {
        //         setTimeout(function () {
        //             $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
        //         },20);
        //     });
        // });

        var tableConfig;
        var accessLogUrl = "";
        if (type === "national") {
            tableConfig = nationaltableConfig;
            accessLogUrl = '../accessLog/getAccessLogPagesJson?type=' + type;

        } else if (type === "province") {
            tableConfig = provincetableConfig;
            accessLogUrl = '../accessLog/getAccessLogPagesJson';
        }
        loadDataTablbeByUrl("#accessTable", tableConfig);
        //提交表单
        form.on("submit(query)", function (data) {
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            if (0 == count) {
                layer.msg('<img src="../static/lib/bdcui/images/success-small.png" alt="">请输入必要查询条件');
                return false;
            }
            layui.use(['table'], function () {
                var table = layui.table;
                table.reload("accessTable", {
                    where: data.field ,
                    page: {
                        curr: 1, //重新从第 1 页开始
                        limits: [10, 50, 100, 200, 500]
                    },
                    url:accessLogUrl
                });
            });
            return false;
        });
        //头工具栏事件
        table.on('toolbar(dataTable)', function (obj) {
            if(obj.event != "LAYTABLE_COLS"){
                var checkStatus = table.checkStatus(obj.config.id);
                var data = checkStatus.data;
                if (data && data.length > 0) {
                    if (obj.event === "access") {
                        plsb(data);
                    }
                    if (obj.event === "getBw") {
                        plGetResponse(data)
                    }
                } else {
                    layer.alert("请选择一条数据进行操作")
                }
            }
        });
        table.on('tool(dataTable)', function (obj) {
            var data = obj.data;
            if (data) {
                if (obj.event === "ywbw") {
                    if (data.jrbw) {
                        layer.open({
                            title: "业务报文",
                            area: ['960px', '450px'],
                            content: '<xmp style="white-space: pre-wrap">' + data.jrbw + '</xmp>'
                        });
                    } else {
                        layer.msg("无业务报文")
                    }
                }
                if (obj.event === "xybw") {
                    if (data.xmid) {
                        $.ajax({
                            url: "../accessLog/queryXybw",
                            data: {
                                ywh: data.xmid,
                                logType: type
                            },
                            success: function (data) {
                                layer.open({
                                    title: "响应报文",
                                    area: ['960px', '450px'],
                                    content: '<xmp style="white-space: pre-wrap">' + data+ '</xmp>'
                                });
                            }
                        });
                    } else {
                        layer.msg("项目主键为空")
                    }
                }
                if (obj.event === "sjjy") {
                    if (data.xmid) {
                        $.ajax({
                            url: "../accessLog/checkDataByXmid",
                            dataType: "json",
                            data: {
                                xmid: data.xmid
                            },
                            success: function (data) {
                                if (data) {
                                    layer.msg("校验成功");
                                } else {
                                    layer.msg("校验失败");
                                }
                            }
                        });
                    } else {
                        layer.msg("项目主键为空");
                    }
                }

                if (obj.event === "sblct") {
                    //根据xmid 查看上报数据走向
                    window.open('/realestate-report-alone/view/sblct.html?xmid=' + data.xmid);
                }
            } else {
                layer.alert("当前数据缺失，请检查数据");
                return false
            }
        });

        function xyzt(data){
            if(data.cgbs == '-1'){
                return "请求上报失败";
            }
            if(data.cgbs == '0'){
                return "等待获取响应报文";
            }
            if(data.cgbs == '1'){
                return "上报成功";
            }
            if(data.cgbs == '2'){
                return "上报失败";
            }
            return '未上报';
        }

        function plsb(data) {
            var xmidList = [];
            for (var i = 0; i < data.length; i++) {
                xmidList.push(data[i].xmid);
            }
            addModel();
            $.ajax({
                url: "../rest/v1.0/access/xmidlist",
                dataType: "json",
                data: "xmidList=" + encodeURI(xmidList),
                success: function (data) {
                    layer.msg("请求结束");
                    removeModal();
                    tableReload("accessTable",form.val('searchForm'));
                },
                error: function (xhr, status, error) {
                    layer.msg("上报失败!");
                    removeModal()
                }
            });
        }

        function plGetResponse(data) {
            var xmidList = [];
            var wsbSlbhList = [];
            var cgSlbhList = [];
            for (var i = 0; i < data.length; i++) {
                if( data[i].ywbwid == null
                    || data[i].cgbs == '-1'
                    || data[i].cgbs ==  null ){
                    wsbSlbhList.push(data[i].slbh);
                }else if(data[i].cgbs == '1'){
                    cgSlbhList.push(data[i].slbh);
                }else {
                    xmidList.push(data[i].xmid);
                }
            }

            // 未上报的受理编号
            if(wsbSlbhList.length > 0){
                layer.alert("选中记录存在未上报记录，受理编号：" + wsbSlbhList);
                return ;
            }
            // 成功上报的受理编号
            if(cgSlbhList.length > 0){
                layer.alert("选中记录存在已成功上报记录，受理编号：" + cgSlbhList);
                return ;
            }
            addModel();
            $.ajax({
                url: "../accessLog/getAccessResponse",
                data: "logType=" + type + "&xmidList=" + xmidList,
                success: function (data) {
                    layer.msg(data);
                    removeModal();
                    tableReload("accessTable",form.val('searchForm'));
                },
                error: function (xhr, status, error) {
                    layer.msg("获取失败!");
                    removeModal()
                }
            });
        }
    });
});