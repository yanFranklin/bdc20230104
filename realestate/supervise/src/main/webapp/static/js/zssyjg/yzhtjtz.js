/**
 * 5 证书使用监管：印制号统计台账
 */
var zdList = getZdList();
var xzqhData = zdList.xzqh;
var zslx = zdList.zslx;
var yzhzfyy = zdList.yzhzfyy;
var zssyqk = zdList.zssyqk;
// 当前所在tab
var tabid;

layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element,
        table = layui.table;

    // 默认查询当前年份
    var date = new Date()
    $("#nf").val(date.getFullYear());

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if (moduleCode) {
        setElementAttrByModuleAuthority(moduleCode);
    }

    if(zdList) {
        // 使用情况查询条件下拉项
        if (zssyqk) {
            $.each(zssyqk, function (index, item) {
                $('#syqk').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
        }

        // 证书类型查询条件下拉项
        if (zslx) {
            $.each(zslx, function (index, item) {
                $('#zslx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
        }

        // 行政区划查询条件下拉项
        if (xzqhData) {
            $.each(xzqhData, function (index, item) {
                $('#qxdm').append('<option value="' + item.qhdm + '">' + item.qhmc + '</option>');
            });
        }

        // 印制号作废原因下拉框
        if (yzhzfyy) {
            $.each(yzhzfyy, function (index, item) {
                $('#zfyy').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
        }
        form.render('select');
    }

    table.render({
        elem: '#xxlbTable',
        toolbar: '#toolbar',
        title: '证书印制号信息列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {minWidth: 80, sort: false, field: 'nf', title: '年份'},
            {minWidth: 100, sort: false, field: 'qxdm', title: '行政区划',
                templet: function (d) {
                    if (xzqhData) {
                        for (var index in xzqhData) {
                            if (xzqhData[index].qhdm == d.qxdm) {
                                return xzqhData[index].qhmc;
                            }
                        }
                        return d.qxdm;
                    } else {
                        return d.qxdm;
                    }
                }
            },
            {minWidth: 100, sort: false, field: 'zslx', title: '证书类型',
                templet: function (d) {
                    if (zslx) {
                        for (var index in zslx) {
                            if (zslx[index].DM == d.zslx) {
                                return zslx[index].MC;
                            }
                        }
                    } else {
                        return d.zslx;
                    }
                }
            },
            {minWidth: 180, sort: false, field: 'qzysxlh', title: '权证印刷序列号'},
            {minWidth: 100, sort: false, field: 'syqk', title: '使用情况',
                templet: function (d) {
                    if (zssyqk) {
                        for (var index in zssyqk) {
                            if (zssyqk[index].DM == d.syqk) {
                                if (2 == d.syqk) {
                                    return '<span style="color: red">' + zssyqk[index].MC + '</span>';
                                } else {
                                    return zssyqk[index].MC;
                                }
                            }
                        }
                    } else {
                        return d.syqk;
                    }
                }
            },
            {minWidth: 180, sort: false, field: 'zfyy', title: '作废原因'},
            {minWidth: 180, sort: false, field: 'zfxqms', title: '作废详情描述'},
            {minWidth: 200, sort: false, field: 'lqbm', title: '领取部门'},
            {minWidth: 150, sort: false, field: 'lqr', title: '领取人'},
            {minWidth: 150, sort: false, field: 'cjr', title: '创建人'},
            {minWidth: 200, sort: false, field: 'cjsj', title: '创建时间',
                templet: function (d) {
                    return formatDate(d.cjsj);
                }
            },
            {minWidth: 120, sort: false, templet: '#barAction', title: '操作', fixed: 'right'}
        ]],
        text: {
            none: '未查询到数据'
        },
        data: [],
        autoSort: false,
        page: true,
        parseData: function (res) {
            exportExcelData = res;
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');


            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    search();

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        search();
        fhtj();
        return false;
    });

    function search() {
        // 获取查询内容
        var obj = {};
        var count = 0;
        $(".search").each(function (i) {
            var value = $(this).val();
            if (!isNullOrEmpty(value)) {
                count += 1;
            }
            var name = $(this).attr('name');
            obj[name] = value;
        });

        if (0 == count) {
            if("fhtj" !== tabid) {
                warnMsg("请输入查询条件！");
            }
            return false;
        }

        if((!isNullOrEmpty(obj["yzhq"]) && !isNumber(obj["yzhq"])) || (!isNullOrEmpty(obj["yzhz"]) && !isNumber(obj["yzhz"]))) {
            warnMsg("起止印制号请输入数字！");
            return false;
        }

        // 重新请求
        table.reload("xxlbTable", {
            url: "/realestate-supervise/rest/v1.0/zssyjg/yzh"
            ,where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    }

    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            search();
            fhtj();
        }
    });


    element.on('tab(yzhtjTab)', function(data){
        tabid = $(".layui-tab-title .layui-this").attr("id");
        if("xxlb" === tabid) {

        } else if("fhtj" === tabid) {
            fhtj();
        }
    });

    /**
     * 废号使用情况统计
     */
    function fhtj() {
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });

        addModel();
        $.ajax({
            url: "/realestate-supervise/rest/v1.0/zssyjg/fqyzhtj",
            type: 'POST',
            data: JSON.stringify(obj),
            contentType: 'application/json',
            dataType: "json",
            success: function (res) {
                if(res && res.length > 0) {
                    $("#tjfxChart").show();
                    showFhtj(res);
                } else {
                    failMsg("未查询到废弃印制号信息！");
                    $("#tjfxChart").hide();
                }
            },
            error: function (xhr, status, error) {
                failMsg("未查询到废弃印制号信息！");
                $("#tjfxChart").hide();
            },
            complete: function () {
                removeModal();
            }
        });
    }

    function showFhtj(data) {
        var chartData = new Array();
        $.each(data, function (index, item) {
            chartData.push({value: item.num, name: item.syr});
        });

        var myChart = echarts.init(document.getElementById('tjfxChart'), "macarons");

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '废弃印制号数量人员统计',
                left: 'center',
                textStyle: {
                    color: '#333'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a}<br/>{b}：{c}（{d}%）"
            },
            series: [
                {
                    name: '废弃印制号数量',
                    type: 'pie',
                    radius: '70%',
                    center: ['50%', '50%'],
                    data: chartData,
                    label: {
                        normal:{
                            formatter: "{b}：{c}（{d}%）",
                            textStyle: {
                                fontWeight: 'normal',
                                fontSize: 15
                            }
                        }
                    },
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }

    //监听工具条
    table.on('tool(xxlbTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'symx') {
            symx(data.yzhid);
        }else if(obj.event === 'excel'){
            exportExcel(data);
        }
    });
    //头工具栏事件
    table.on('toolbar(xxlbTable)', function (obj) {
        var checkStatus = table.checkStatus('xxlbTable').data;
        if(obj.event === 'excel'){
            exportExcel(obj,checkStatus);
        }
    });

    /**
     * 已选数据查看
     */
    function symx(yzhid) {
        addModel();
        $.ajax({
            url: "/realestate-supervise/rest/v1.0/zssyjg/yzh/" + yzhid + "/symx",
            type: "GET",
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data && data.length > 0) {
                    layer.open({
                        type: 1,
                        title: "印制号使用明细",
                        content: $('#popup'),
                        area: ['985px', '500px'],
                        cancel: function () {
                            $("#popup").css('display', 'none');
                        },
                        success: function (layero, index) {
                            viewTableRender(data);
                        }
                    });
                } else {
                    warnMsg("未查询到当前印制号使用明细！");
                }
            }, error: function () {
                failMsg("查询印制号使用明细异常！");
            }, complete: function () {
                removeModal();
            }
        });
    }

    /**
     * 印制号使用明细
     */
    function viewTableRender(symxData) {
        table.render({
            elem: '#viewTable',
            id: 'viewTable',
            title: '印制号使用明细',
            cols: [[
                {type: 'numbers', fixed: 'left', width: 60, title: '序号'},
                {field: 'sybmmc', title: '使用部门名称', width: 130},
                {field: 'syr', title: '使用人', width: 110},
                {field: 'syyy', title: '使用原因', minWidth: 250},
                {field: 'syqk', title: '使用情况', width: 95,
                    templet: function (d) {
                        if (zssyqk) {
                            for (var index in zssyqk) {
                                if (zssyqk[index].DM == d.syqk) {
                                    if (2 == d.syqk) {
                                        return '<span style="color: red">' + zssyqk[index].MC + '</span>';
                                    } else {
                                        return zssyqk[index].MC;
                                    }
                                }
                            }
                        } else {
                            return d.syqk;
                        }
                    }
                },
                {field: 'sysj', title: '使用时间', width: 200},
            ]],
            data: symxData,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            }
        });
    }

    //点击高级查询--4的倍数
    $('#seniorSearch').on('click', function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $(this).parent().toggleClass('bdc-button-box-four');

        if ($(this).html() == '高级查询') {
            $(this).html('收起');
        } else {
            $(this).html('高级查询');
        }

        $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);
        $('.layui-table-body').height($('.bdc-table-box').height() - 129);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);
    });
});
