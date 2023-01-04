var moduleCode = GetQueryString("moduleCode");
var zdNames = "gxbmbz,djlx,djxl,czzt";
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    $(function () {
        // 加载字典
        getMulZdList();
        var tpl = $("#DmMcTpl").html();
        if (zdList) {
            $.each(zdList, function (key, value) {
                laytpl(tpl).render(value, function (html) {
                    $("." + key).append(html);
                });
            });
        } else {
            laytpl(tpl).render(value, function (html) {
                $("").append(html);
            });
        }
        form.render();
        //初始化日期控件
        laydate.render({
            elem: '#kssj'
            , type: 'datetime'
        });
        laydate.render({
            elem: '#jssj'
            , type: 'datetime'
        });

        //点击高级查询--4的倍数
        $('#seniorSearch').on('click', function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');
            $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);

            $('.layui-table-body').height($('.bdc-table-box').height() - 129);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);
        });
        //受理号、不动产单元号、登记类型，申请类型、坐落、办结日期、推送状态（成功、失败、未推送）
        var gxtscxtableConfig = {
            toolbar: "#toolbarSqxx"
            // , url: '../accessLog/getAccessLogPagesJson'
            , limits: [10, 50, 100, 200, 500]
            , cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'SLBH', title: '受理编号', width: 150},
                {field: 'XMID', title: '项目id', hide: true},
                {field: 'RZID', title: '日志id', hide: true},
                {field: 'BDCDYH', title: '不动产单元号', width: 250},
                {
                    field: 'DJLX', title: '登记类型', width: 100
                    , templet: function (d) {
                        if (d.DJLX == null)
                        {
                            return "";
                        }
                        return convertZdDmToMc("djlx", d.DJLX);
                    }
                },
                {
                    field: 'DJXL', title: '申请类型', width: 100
                    , templet: function (d) {
                        if (d.DJXL == null)
                        {
                            return "";
                        }
                        return convertZdDmToMc("djxl", d.DJXL);
                    }
                },
                {field: 'ZL', title: '坐落'},
                {field: 'DJSJ',     title: '办结日期',
                    templet: function (d) {
                        if (d.DJSJ) {
                            return formatDate(d.DJSJ);
                        } else {
                            return '';
                        }
                    }
                },
                //{field: 'SQLX', title: '申请类型'},
                {
                    field: 'CZZT', title: '推送状态', width: 100
                    , templet: function (d) {
                        if (d.CZZT == null)
                        {
                            return "";
                        }
                        return czzt(d.CZZT);
                    }
                },
                {
                    title: '操作',
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#gxtslogListToolBarTmpl',
                    width: 150
                }
            ]],
            data: [],
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

        //台账查询地址
        var gxtsLogUrl = "../gxtsLog/getGxtsLogPagesJson";

        loadDataTablbeByUrl("#gxtscxTable", gxtscxtableConfig);
        //提交表单
        form.on("submit(query)", function (data) {
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            if (0 == count && !$('#slbhOrbdcdyh').val()) {
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">请输入必要查询条件');
                return false;
            }
            layui.use(['table'], function () {
                var table = layui.table;
                table.reload("gxtscxTable", {
                    where: data.field
                    , page: {
                        //重新从第 1 页开始
                        curr: 1
                    },
                    url: gxtsLogUrl
                });
            });
            return false;
        });

        //头工具栏事件
        table.on('toolbar(dataTable)', function (obj) {
            if (obj.event != "LAYTABLE_COLS") {
                var checkStatus = table.checkStatus(obj.config.id);
                var data = checkStatus.data;
                if (data && data.length > 0) {
                    if (obj.event === "batchSharedb") {
                        addModel();
                        var requestParam = {"bdcCzrzDOList":data}
                        $.ajax({
                            url: "../gxtsLog/batch/sharedb/xmid/for/compenstae",
                            type: 'POST',
                            contentType:'application/json',
                            data: JSON.stringify(requestParam),
                            async: false,
                            success: function (data) {
                                removeModal();
                                if (data && data.success) {
                                    layer.msg("操作成功");
                                } else {
                                    if (data.fail && data.data) {
                                        layer.msg("操作失败，部分数据推送失败,xmids:" + data.data);
                                    }else {
                                        layer.msg("操作失败");
                                    }
                                }
                                tableReload("gxtscxTable",form.val('searchForm'));
                            },
                            error: function (xhr, status, error) {
                                removeModal();
                                delAjaxErrorMsg(xhr)
                            }
                        });
                    }
                } else {
                    layer.alert("请选择一条数据进行操作")
                }
            }
        });

        table.on('tool(dataTable)', function (obj) {
            var data = obj.data;
            if (data) {
                if (obj.event === "sharedb") {
                    if (data.XMID && data.RZID) {
                        addModel();
                        $.ajax({
                            url: "../gxtsLog/sharedb/xmid/for/compenstae?xmid=" + data.XMID + "&rzid=" + data.RZID,
                            type: 'GET',
                            dataType: 'json',
                            success: function (data) {
                                removeModal();
                                if (data && data.success) {
                                    layer.msg("操作成功");
                                } else {
                                    layer.msg(data.fail.message);
                                }
                                tableReload("gxtscxTable",form.val('searchForm'));
                            },
                            error: function (xhr, status, error) {
                                removeModal();
                                delAjaxErrorMsg(xhr)
                            }
                        });
                    } else {
                        layer.msg("所选数据没有xmid信息")
                    }
                }
            } else {
                layer.alert("当前数据缺失，请检查数据");
                return false
            }
        });

        function czzt(data) {
            if (data == 0) {
                return "推送失败或未推送";
            }
            if (data == 1) {
                return "推送成功";
            }
            return '未推送';
        }

        function getMulZdList() {
            $.ajax({
                url: "../bdcDsfLog/zdMul",
                dataType: "json",
                data: {
                    zdNames: zdNames
                },
                async: false,
                success: function (data) {
                    zdList = $.extend({}, data);
                },
                error: function (e) {
                }
            });
        }

        //前台的字典转换，代码转换为名称
        function convertZdDmToMc(zdname, zdDm, zdListName) {
            if (zdDm) {
                if (!zdListName) {
                    zdListName = "zdList"
                }
                var zdVals = eval(zdListName + "." + zdname);
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
            return "";
        }

    });
});